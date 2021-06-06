/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.entity.cashier.transaction;

import com.dimata.cashierweb.entity.masterdata.PstMemberGroup;
import com.dimata.cashierweb.entity.masterdata.PstMemberRegistrationHistory;
import com.dimata.cashierweb.entity.masterdata.PstRoom;
import com.dimata.cashierweb.entity.masterdata.PstTableRoom;
import com.dimata.common.db.DBException;
import com.dimata.common.db.DBHandler;
import com.dimata.common.db.DBResultSet;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.payment.PaymentSystem;
import com.dimata.common.entity.payment.PstPaymentSystem;
import com.dimata.common.entity.payment.PstPriceType;
import com.dimata.common.entity.system.PstSystemProperty;
import com.dimata.hanoman.entity.masterdata.Contact;
import com.dimata.hanoman.entity.masterdata.PstContact;
import com.dimata.hanoman.entity.masterdata.PstContactClass;
import com.dimata.hanoman.entity.masterdata.PstContactClassAssign;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.pos.entity.balance.CashCashier;
import com.dimata.pos.entity.balance.PstCashCashier;
import com.dimata.pos.entity.billing.BillMain;
import com.dimata.pos.entity.billing.Billdetail;
import com.dimata.pos.entity.billing.PstBillDetail;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.entity.masterCashier.PstCashMaster;
import com.dimata.posbo.entity.admin.PstAppGroup;
import com.dimata.posbo.entity.masterdata.Company;
import com.dimata.posbo.entity.masterdata.PstCompany;
import com.dimata.posbo.entity.masterdata.PstCosting;
import com.dimata.posbo.entity.masterdata.PstPersonalDiscount;
import com.dimata.posbo.entity.warehouse.PstMatCosting;
import com.dimata.posbo.entity.warehouse.PstMatCostingItem;
import com.dimata.util.Formater;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Ardiadi
 */
public class PstCustomBillMain extends DBHandler {

    public static final String TABLE_ROOM_CLASS = "room_class";
    public static final int PAYMENT_TYPE_CASH = 0;
    public static final int PAYMENT_TYPE_CREDIT_CARD = 1;
    public static final int PAYMENT_TYPE_BG = 2;
    public static final int PAYMENT_TYPE_CHECK = 3;
    public static final int PAYMENT_TYPE_DEBIT_CARD = 4;
    public static final int PAYMENT_TYPE_VOUCHER_REGULAR = 5;
    public static final int PAYMENT_TYPE_VOUCHER_COMPLIMENT = 6;
    public static final int PAYMENT_TYPE_FOC = 7;

    public static final int[] mappingToProchain = {
        0,
        0,
        0,
        0,
        0,
        1,
        2,
        0
    };

    public static final String[] paymentTypeNames = {
        "CASH",
        "CREDIT CARD",
        "BG",
        "CHECK",
        "DEBIT CARD",
        "VOUCHER REGULAR",
        "VOUCHER_COMPLIMENT",
        "FOC/CO"
    };

    public static final int FLD_TAX_INCLUDE = 0;
    public static final int FLD_SERVICE_INCLUDE = 1;
    public static final String[] fieldNames = {
        "TAX_INCLUDE",
        "SERVICE_INCLUDE"
    };

    public static final int[] fieldTypes = {
        TYPE_INT,
        TYPE_INT
    };

    public static int listMember(int limitStart, int recordToGet, String whereClause, String order) {
        int lists = 0;

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT COUNT(*) JUMLAH FROM " + PstContact.TBL_CONTACT + " AS contact "
                    + "INNER JOIN " + PstMemberRegistrationHistory.TBL_MEMBER_REGISTRATION_HISTORY + " AS memberReg "
                    + "ON contact." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + "=memberReg." + PstMemberRegistrationHistory.fieldNames[PstMemberRegistrationHistory.FLD_MEMBER_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                lists = rs.getInt("JUMLAH");
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    public static Vector listItemOpenBill(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = ""
                    + " SELECT billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_DETAIL_ID]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_ITEM_NAME]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_ITEM_PRICE]
                    //>>> added by dewok 20190204 for re calculate discount value by discount type
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_DISC_TYPE]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_DISC_PCT]
                    //<<<
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_DISC]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_CUSTOME_PACK_BILLING_ID]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_STATUS]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_STATUS_PRINT]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_MATERIAL_ID]
                    //>>> added by dewok 20190217
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_CUSTOM_SCHEDULE_ID]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_GUIDE_PRICE]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_TAX]
                    + " ,billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_SERVICE]
                    + " FROM " + PstBillMain.TBL_CASH_BILL_MAIN + " AS billMain "
                    + " INNER JOIN " + PstBillDetail.TBL_CASH_BILL_DETAIL + " AS billDetail "
                    + " ON billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "=billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                Billdetail billdetail = new Billdetail();
                resultToObject(rs, billdetail);
                lists.add(billdetail);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Vector listCosting(int limitStart, int recordToGet, String whereClause, String groupBy) {
        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT SUM(costingItem." + PstMatCostingItem.fieldNames[PstMatCostingItem.FLD_HPP] + "*costingItem." + PstMatCostingItem.fieldNames[PstMatCostingItem.FLD_QTY] + ") AS " + PstMatCostingItem.fieldNames[PstMatCostingItem.FLD_HPP] + ", "
                    + "costing." + PstCosting.fieldNames[PstCosting.FLD_NAME] + ", "
                    + "COUNT(DISTINCT costingMaterial." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_MATERIAL_ID] + ") AS " + PstMatCostingItem.fieldNames[PstMatCostingItem.FLD_QTY] + " "
                    + "FROM " + PstCosting.TBL_COSTING + " AS costing "
                    + "INNER JOIN " + PstMatCosting.TBL_COSTING + " AS costingMaterial "
                    + "ON costing." + PstCosting.fieldNames[PstCosting.FLD_COSTING_ID] + "=costingMaterial." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_ID] + " "
                    + "INNER JOIN " + PstMatCostingItem.TBL_MAT_COSTING_ITEM + " AS costingItem "
                    + "ON costingMaterial." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_MATERIAL_ID] + "=costingItem." + PstMatCostingItem.fieldNames[PstMatCostingItem.FLD_COSTING_MATERIAL_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (groupBy != null && groupBy.length() > 0) {
                sql = sql + " GROUP BY " + groupBy;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                BillMainCostum billMainCostum = new BillMainCostum();
                resultToObjectCosting(rs, billMainCostum);
                lists.add(billMainCostum);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Vector listTaxService(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + ", "
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_TAX_PERCENT] + ", "
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_TAX_VALUE] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_SERVICE_PERCENT] + ", "
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_SERVICE_VAL] + ","
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_NOTES] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_TAX_SVC_DEFAULT] + ","
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DISC_PCT] + " "
                    + "FROM " + PstBillMain.TBL_CASH_BILL_MAIN + " AS billMain "
                    + "INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS location "
                    + "ON billMain." + PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID] + "=location." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                BillMainCostum billMain = new BillMainCostum();
                resultToObjectTaxService(rs, billMain);
                lists.add(billMain);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Vector listTaxServiceItem(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + ", "
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_TAX_PERCENT] + ", "
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_TAX_VALUE] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_SERVICE_PERCENT] + ", "
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_SERVICE_VAL] + ","
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_NOTES] + ","
                    + "billMain." + fieldNames[FLD_TAX_INCLUDE] + " AS " + PstLocation.fieldNames[PstLocation.FLD_TAX_SVC_DEFAULT] + ","
                    + "billMain." + fieldNames[FLD_SERVICE_INCLUDE] + " "
                    + "FROM " + PstBillMain.TBL_CASH_BILL_MAIN + " AS billMain "
                    + "INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS location "
                    + "ON billMain." + PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID] + "=location." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                BillMainCostum billMain = new BillMainCostum();
                resultToObjectTaxService(rs, billMain);
                lists.add(billMain);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static double checkTotalCredit(int limitStart, int recordToGet, String whereClause, String order) {
        double amountCredit = 0;

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT SUM(" + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE] + ") AS " + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE] + " "
                    + "FROM " + PstBillDetail.TBL_CASH_BILL_DETAIL + " AS billDetail "
                    + "INNER JOIN  " + PstBillMain.TBL_CASH_BILL_MAIN + " AS billMain "
                    + "ON billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "=billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + " "
                    + "INNER JOIN " + PstContact.TBL_CONTACT + " AS contact "
                    + "ON billMain." + PstBillMain.fieldNames[PstBillMain.FLD_CUSTOMER_ID] + "=contact." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                amountCredit = rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]);
            }
            rs.close();
            return amountCredit;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return amountCredit;
    }

    public static double limitCredit(int limitStart, int recordToGet, String whereClause, String order) {
        double amountCredit = 0;

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT " + PstContact.fieldNames[PstContact.FLD_MEMBER_CREDIT_LIMIT];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                amountCredit = rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]);
            }
            rs.close();
            return amountCredit;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return amountCredit;
    }

    public static double getTotalPrice(int limitStart, int recordToGet, String whereClause, String order) {
        double totalPrice = 0;

        DBResultSet dbrs = null;

        try {
            //OLD : SUM("+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+")
            String sql = "SELECT SUM((billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_ITEM_PRICE] + "-billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_DISC] + ")*billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY] + ") AS " + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE] + " "
                    + "FROM " + PstBillMain.TBL_CASH_BILL_MAIN + " AS billMain "
                    + "INNER JOIN " + PstBillDetail.TBL_CASH_BILL_DETAIL + " AS billDetail "
                    + "ON billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "=billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                totalPrice = rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]);
            }
            rs.close();
            return totalPrice;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return totalPrice;
    }

    public static double getTotalGuidePrice(int limitStart, int recordToGet, String whereClause, String order) {
        double totalPrice = 0;

        DBResultSet dbrs = null;

        try {
            //OLD : SUM("+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+")
            String sql = "SELECT SUM((billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_GUIDE_PRICE] + "-billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_DISC] + ")*billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY] + ") AS " + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE] + " "
                    + "FROM " + PstBillMain.TBL_CASH_BILL_MAIN + " AS billMain "
                    + "INNER JOIN " + PstBillDetail.TBL_CASH_BILL_DETAIL + " AS billDetail "
                    + "ON billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "=billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                totalPrice = rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]);
            }
            rs.close();
            return totalPrice;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return totalPrice;
    }

    public static int getCounter(int limitStart, int recordToGet, String whereClause, String order) {
        int counter = 0;

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT COUNT(*) AS COUNTER FROM "
                    + "" + PstBillMain.TBL_CASH_BILL_MAIN + " ";
            /*+ "WHERE "
             + "("
             + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
             + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
             + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
             + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
             + ") OR ("
             + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
             + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
             + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
             + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
             + ") OR ("
             + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='1' "
             + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
             + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
             + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
             + ") OR ("
             + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='1' "
             + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
             + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
             + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
             + ")"; */

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                counter = rs.getInt("COUNTER");
            }
            rs.close();
            return counter;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return counter;
    }

    public static int getTotalPackage(int limitStart, int recordToGet, String whereClause, String order) {
        int counter = 0;

        DBResultSet dbrs = null;

        try {

            String sql = ""
                    + " SELECT COUNT(billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_DETAIL_ID] + ") AS COUNT "
                    + " FROM " + PstBillMain.TBL_CASH_BILL_MAIN + " AS billMain "
                    + " INNER JOIN " + PstBillDetail.TBL_CASH_BILL_DETAIL + " AS billDetail "
                    + " ON billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "=billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID];

            if (whereClause.length() > 0) {
                sql += " WHERE " + whereClause;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                counter = rs.getInt("COUNT");
            }
            rs.close();
            return counter;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return counter;
    }

    public static int getCounterBill(int limitStart, int recordToGet, String whereClause, String order) {
        int counter = 0;

        DBResultSet dbrs = null;
        Date newDate = new Date();
        try {
            String openBillGenerate = PstSystemProperty.getValueByName("CASHIER_PRINT_OPEN_BILL_TO_INVOICE");
            String sql = "SELECT COUNT(*) AS COUNTER FROM "
                    + "" + PstBillMain.TBL_CASH_BILL_MAIN + " "
                    + "WHERE "
                    + "(("
                    + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                    + ") OR ("
                    + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                    + ") OR ("
                    + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                    + ") OR ("
                    + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                    + ") OR ("
                    + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' ";
            if (openBillGenerate.equals("1")) {
                sql += ""
                        + ") OR ("
                        + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                        + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                        + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                        + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NUMBER] + " NOT LIKE '%X%'"
                        + " ";
            }
            sql += ""
                    + ")) AND " + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " LIKE '%" + Formater.formatDate(newDate, "yyyy-MM-dd") + "%'";

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                counter = rs.getInt("COUNTER");
            }
            rs.close();
            return counter;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return counter;
    }

    public static int getCounterReturn(int limitStart, int recordToGet, String whereClause, String order) {
        int counter = 0;

        DBResultSet dbrs = null;
        Date newDate = new Date();
        try {

            String sql = "SELECT COUNT(*) AS COUNTER FROM "
                    + "" + PstBillMain.TBL_CASH_BILL_MAIN + " "
                    + "WHERE "
                    + "(("
                    + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                    + ") OR ("
                    + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                    + ") OR ("
                    + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                    + ") OR ("
                    + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                    + ") OR ("
                    + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                    + ")) AND " + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " LIKE '%" + Formater.formatDate(newDate, "yyyy-MM-dd") + "%'";

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                counter = rs.getInt("COUNTER");
            }
            rs.close();
            return counter;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return counter;
    }

    public static int getCounterCancel(int limitStart, int recordToGet, String whereClause, String order) {
        int counter = 0;

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT COUNT(*) AS COUNTER FROM "
                    + "" + PstBillMain.TBL_CASH_BILL_MAIN + " "
                    + "WHERE "
                    + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='2' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'";

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                counter = rs.getInt("COUNTER");
            }
            rs.close();
            return counter;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return counter;
    }

    public static int getCounterOpenBill(int limitStart, int recordToGet, String whereClause, String order) {
        int counter = 0;

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT COUNT(*) AS COUNTER FROM "
                    + "" + PstBillMain.TBL_CASH_BILL_MAIN + " "
                    + "WHERE "
                    + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'";

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                counter = rs.getInt("COUNTER");
            }
            rs.close();
            return counter;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return counter;
    }

    public static int getCashierNumber(long cashCashierId) {
        int counter = 0;

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT cashMaster." + PstCashMaster.fieldNames[PstCashMaster.FLD_CASHIER_NUMBER] + " "
                    + "FROM " + PstCashCashier.TBL_CASH_CASHIER + " AS cashCashier "
                    + "INNER JOIN " + PstCashMaster.TBL_CASH_MASTER + " AS cashMaster "
                    + "ON cashCashier." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASHMASTER_ID] + "=cashMaster." + PstCashMaster.fieldNames[PstCashMaster.FLD_CASH_MASTER_ID] + " "
                    + "WHERE cashCashier." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID] + "='" + cashCashierId + "'";

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                counter = rs.getInt(PstCashMaster.fieldNames[PstCashMaster.FLD_CASHIER_NUMBER]);
            }
            rs.close();
            return counter;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return counter;
    }

    public static Vector listOpenBill(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + ", "
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + ", "
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + ", "
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ", "
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_RESERVATION_ID] + ", "
                    + "room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + ", "
                    + "meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + ", "
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_NAME] + " AS location_name "
                    + "FROM " + PstBillMain.TBL_CASH_BILL_MAIN + " AS billMain "
                    + "LEFT JOIN " + PstRoom.TBL_P2_ROOM + " AS room "
                    + "ON billMain." + PstBillMain.fieldNames[PstBillMain.FLD_ROOM_ID] + "=room." + PstRoom.fieldNames[PstRoom.FLD_ROOM_ID] + "  "
                    + "LEFT JOIN " + PstTableRoom.TBL_P2_TABLE + " AS meja "
                    + "ON billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TABLE_ID] + "=meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_ID] + " "
                    + "INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS location "
                    + "ON location." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=billMain." + PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID] + " ";

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                BillMainCostum billMain = new BillMainCostum();
                resultToObjectTransaction(rs, billMain);
                lists.add(billMain);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static void resultToObject(ResultSet rs, Billdetail billdetail) {

        try {
            billdetail.setOID(rs.getLong(PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_DETAIL_ID]));
            billdetail.setBillMainId(rs.getLong(PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]));
            billdetail.setItemName(rs.getString(PstBillDetail.fieldNames[PstBillDetail.FLD_ITEM_NAME]));
            billdetail.setItemPrice(rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_ITEM_PRICE]));
            //>>> added by dewok 20190204 for re calculate discount value by discount type
            billdetail.setDiscType(rs.getInt(PstBillDetail.fieldNames[PstBillDetail.FLD_DISC_TYPE]));
            billdetail.setDiscPct(rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_DISC_PCT]));
            //<<<
            billdetail.setDisc(rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_DISC]));
            billdetail.setQty(rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY]));
            billdetail.setTotalPrice(rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]));
            billdetail.setParentId(rs.getLong(PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]));
            billdetail.setCutomePackBillingId(rs.getLong(PstBillDetail.fieldNames[PstBillDetail.FLD_CUSTOME_PACK_BILLING_ID]));
            billdetail.setStatus(rs.getInt(PstBillDetail.fieldNames[PstBillDetail.FLD_STATUS]));
            billdetail.setStatusPrint(rs.getInt(PstBillDetail.fieldNames[PstBillDetail.FLD_STATUS_PRINT]));
            billdetail.setMaterialId(rs.getLong(PstBillDetail.fieldNames[PstBillDetail.FLD_MATERIAL_ID]));
            billdetail.setCostumeScheduleId(rs.getLong(PstBillDetail.fieldNames[PstBillDetail.FLD_CUSTOM_SCHEDULE_ID]));
            billdetail.setGuidePrice(rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_GUIDE_PRICE]));
            billdetail.setTotalTax(rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_TAX]));
            billdetail.setTotalSvc(rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_SERVICE]));
        } catch (Exception e) {
        }

    }

    public static void resultToObjectCosting(ResultSet rs, BillMainCostum billMainCostum) {

        try {
            billMainCostum.setAmountCosting(rs.getDouble(PstMatCostingItem.fieldNames[PstMatCostingItem.FLD_HPP]));
            billMainCostum.setCostingName(rs.getString(PstCosting.fieldNames[PstCosting.FLD_NAME]));
            billMainCostum.setCostingQty(rs.getDouble(PstMatCostingItem.fieldNames[PstMatCostingItem.FLD_QTY]));
        } catch (Exception e) {
        }

    }

    public static void resultToObjectTaxService(ResultSet rs, BillMainCostum billMain) {

        try {
            billMain.setOID(rs.getLong(PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]));
            billMain.setTaxPct(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_TAX_PERCENT]));
            billMain.setTaxValue(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_TAX_VALUE]));
            billMain.setServicePct(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_SERVICE_PERCENT]));
            billMain.setServiceValue(rs.getDouble(PstLocation.fieldNames[PstLocation.FLD_SERVICE_VAL]));
            billMain.setNotes(rs.getString(PstBillMain.fieldNames[PstBillMain.FLD_NOTES]));
            billMain.setTaxInc(rs.getInt(PstLocation.fieldNames[PstLocation.FLD_TAX_SVC_DEFAULT]));
            billMain.setDiscPct(rs.getDouble(PstBillMain.fieldNames[PstBillMain.FLD_DISC_PCT]));
        } catch (Exception e) {
        }

    }

    public static void resultToObjectTransaction(ResultSet rs, BillMainCostum billMain) {

        try {
            billMain.setInvoiceNo(rs.getString(PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]));
            billMain.setGuestName(rs.getString(PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]));
            billMain.setTableNumber(rs.getString(PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]));
            billMain.setRoomName(rs.getString(PstRoom.fieldNames[PstRoom.FLD_NAME]));
            billMain.setOID(rs.getLong(PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]));
            billMain.setBillDate(rs.getTimestamp(PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]));
            billMain.setLocationName(rs.getString("location_name"));
            billMain.setReservationId(rs.getLong(PstBillMain.fieldNames[PstBillMain.FLD_RESERVATION_ID]));
        } catch (Exception ex) {

        }

    }

    public static void resultToObjectMember(ResultSet rs, BillMainCostum billMain) {

        try {
            billMain.setPriceTypeId(rs.getLong(PstPriceType.fieldNames[PstPriceType.FLD_PRICE_TYPE_ID]));
            billMain.setClassId(rs.getLong(PstContactClass.fieldNames[PstContactClass.FLD_CONTACT_CLASS_ID]));
            billMain.setPriceTypeName(rs.getString(PstPriceType.fieldNames[PstPriceType.FLD_NAME]));
            billMain.setClassName(rs.getString(PstContactClass.fieldNames[PstContactClass.FLD_CLASS_NAME]));

        } catch (Exception e) {
        }

    }

    public static Vector listEmployee(int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT CONT." + PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID] + ", "
                    + "CONT." + PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE] + ", "
                    + "employee." + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " AS " + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + ", "
                    + "CONT." + PstContactList.fieldNames[PstContactList.FLD_MEMBER_CONSIGMENT_LIMIT] + " "
                    + " FROM " + PstContactList.TBL_CONTACT_LIST + " AS CONT "
                    + " INNER JOIN " + PstContactClassAssign.TBL_CNT_CLS_ASSIGN + " AS ASS "
                    + " ON CONT." + PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]
                    + " = ASS." + PstContactClassAssign.fieldNames[PstContactClassAssign.FLD_CONTACT_ID]
                    + " INNER JOIN " + PstContactClass.TBL_CONTACT_CLASS + " AS CLS "
                    + " ON CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CONTACT_CLASS_ID]
                    + " = ASS." + PstContactClassAssign.fieldNames[PstContactClassAssign.FLD_CNT_CLS_ID] + " "
                    + "INNER JOIN " + PstEmployee.TBL_HR_EMPLOYEE + " AS employee "
                    + "ON CONT." + PstContactList.fieldNames[PstContactList.FLD_EMPLOYEE_ID] + "=employee." + PstEmployee.fieldNames[PstEmployee.FLD_EMPLOYEE_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("sql : " + sql);

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                ContactList contact = new ContactList();

                resultToObject(rs, contact);

                lists.add(contact);

            }

            rs.close();

            return lists;

        } catch (Exception e) {

            System.out.println(e);

        } finally {

            DBResultSet.close(dbrs);

        }

        return new Vector();

    }

    public static Vector listInHouseGuestPayment(int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT m.*, "
                    + "ca." + PstReservation.fieldNames[PstReservation.FLD_CHECKOUT_DATE_REPORTED] + " as VALID_EXPIRED_DATE,"
                    + "ca." + PstReservation.fieldNames[PstReservation.FLD_RESERVATION_ID] + ","
                    + "ca." + PstReservation.fieldNames[PstReservation.FLD_RESERVATION_STATUS] + ","
                    + "ca." + PstReservation.fieldNames[PstReservation.FLD_TRAVEL_AGENT_ID] + ", "
                    + "ca." + PstReservation.fieldNames[PstReservation.FLD_RESERVATION_NUM] + ", "
                    + "n." + PstContact.fieldNames[PstContact.FLD_COMP_NAME] + ", "
                    + "k." + PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER] + " as room_number, "
                    + "m." + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " "
                    + "from " + PstContactList.TBL_CONTACT_LIST + " as m "
                    + "inner join " + PstRsvGuest.TBL_RSV_GUEST + " as rg "
                    + "on rg." + PstRsvGuest.fieldNames[PstRsvGuest.FLD_CUSTOMER_ID] + "=m." + PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID] + " "
                    + "inner join " + PstReservation.TBL_RESERVATION + " as ca "
                    + "on rg." + PstRsvGuest.fieldNames[PstRsvGuest.FLD_RESERVATION_ID] + "=ca." + PstReservation.fieldNames[PstReservation.FLD_RESERVATION_ID] + " "
                    + "LEFT JOIN " + PstContact.TBL_CONTACT + " AS n "
                    + "ON ca." + PstReservation.fieldNames[PstReservation.FLD_TRAVEL_AGENT_ID] + "=n." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + " "
                    + "left join " + PstHotelRoom.TBL_HOTEL_ROOM + " as k "
                    + "on k." + PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID] + "=ca." + PstReservation.fieldNames[PstReservation.FLD_HOTEL_ROOM_ID] + " "
                    + "inner join " + PstRoomClass.TBL_ROOM_CLASS + " as rc "
                    + "on rc." + PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS_ID] + "=k." + PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_CLASS_ID] + " "
                    + "where m." + PstContactList.fieldNames[PstContactList.FLD_PROCESS_STATUS] + "!=2 "
                    + "and (ca." + PstReservation.fieldNames[PstReservation.FLD_RESERVATION_STATUS] + "=3 "
                    + "or ca." + PstReservation.fieldNames[PstReservation.FLD_RESERVATION_STATUS] + "=7)";

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " AND " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("sql : " + sql);

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                Vector temp = new Vector();
                ContactList contactList = new ContactList();
                Reservation reservation = new Reservation();

                contactList.setOID(rs.getLong(PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]));

                temp.add(contactList);

                reservation.setOID(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_RESERVATION_ID]));
                reservation.setReservationNum(rs.getString(PstReservation.fieldNames[PstReservation.FLD_RESERVATION_NUM]));
                temp.add(reservation);

                lists.add(temp);

            }

            rs.close();

            return lists;

        } catch (Exception e) {

            System.out.println(e);

        } finally {

            DBResultSet.close(dbrs);

        }

        return new Vector();

    }

    public static Vector listInHouseGuest(int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT m.*, "
                    + "ca." + PstReservation.fieldNames[PstReservation.FLD_CHEK_OUT_DATE] + " as VALID_EXPIRED_DATE,"
                    + "ca." + PstReservation.fieldNames[PstReservation.FLD_RESERVATION_ID] + ","
                    + "ca." + PstReservation.fieldNames[PstReservation.FLD_RESERVATION_STATUS] + ","
                    + "ca." + PstReservation.fieldNames[PstReservation.FLD_TRAVEL_AGENT_ID] + ","
                    + "ca." + PstReservation.fieldNames[PstReservation.FLD_RESERVATION_NUM] + ","
                    + "n." + PstContact.fieldNames[PstContact.FLD_COMP_NAME] + ", "
                    + "k." + PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER] + " as room_number, "
                    + "m." + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + ", "
                    + "m." + PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE] + " "
                    + "from " + PstContactList.TBL_CONTACT_LIST + " as m "
                    + "inner join " + PstRsvGuest.TBL_RSV_GUEST + " as rg "
                    + "on rg." + PstRsvGuest.fieldNames[PstRsvGuest.FLD_CUSTOMER_ID] + "=m." + PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID] + " "
                    + "inner join " + PstReservation.TBL_RESERVATION + " as ca "
                    + "on rg." + PstRsvGuest.fieldNames[PstRsvGuest.FLD_RESERVATION_ID] + "=ca." + PstReservation.fieldNames[PstReservation.FLD_RESERVATION_ID] + " "
                    + "LEFT JOIN " + PstContact.TBL_CONTACT + " AS n "
                    + "ON ca." + PstReservation.fieldNames[PstReservation.FLD_TRAVEL_AGENT_ID] + "=n." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + " "
                    + "left join " + PstHotelRoom.TBL_HOTEL_ROOM + " as k "
                    + "on k." + PstHotelRoom.fieldNames[PstHotelRoom.FLD_HOTEL_ROOM_ID] + "=ca." + PstReservation.fieldNames[PstReservation.FLD_HOTEL_ROOM_ID] + " "
                    + "left join " + PstRoomClass.TBL_ROOM_CLASS + " as rc "
                    + "on rc." + PstRoomClass.fieldNames[PstRoomClass.FLD_ROOM_CLASS_ID] + "=k." + PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_CLASS_ID] + " "
                    + "where m." + PstContactList.fieldNames[PstContactList.FLD_PROCESS_STATUS] + "!=2 "
                    + "and (ca." + PstReservation.fieldNames[PstReservation.FLD_RESERVATION_STATUS] + "=3 "
                    + "or ca." + PstReservation.fieldNames[PstReservation.FLD_RESERVATION_STATUS] + "=7)";

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("sql : " + sql);

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                Vector listData = new Vector(1, 1);
                ContactList contactList = new ContactList();
                HotelRoom hotelRoom = new HotelRoom();
                Reservation reservation = new Reservation();
                Contact contact = new Contact();

                contactList.setOID(rs.getLong(PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]));
                contactList.setPersonName(rs.getString(PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]));
                contactList.setTelpMobile(rs.getString(PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE]));
                listData.add(contactList);

                hotelRoom.setRoomNumber(rs.getString(PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER]));
                listData.add(hotelRoom);

                reservation.setOID(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_RESERVATION_ID]));
                reservation.setTravelAgentId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_TRAVEL_AGENT_ID]));
                reservation.setChekOutDate(rs.getDate("VALID_EXPIRED_DATE"));
                reservation.setReservationNum(rs.getString(PstReservation.fieldNames[PstReservation.FLD_RESERVATION_NUM]));
                listData.add(reservation);

                contact.setOID(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_TRAVEL_AGENT_ID]));
                contact.setCompName(rs.getString(PstContact.fieldNames[PstContact.FLD_COMP_NAME]));
                listData.add(contact);

                lists.add(listData);

            }

            rs.close();

            return lists;

        } catch (Exception e) {

            System.out.println(e);

        } finally {

            DBResultSet.close(dbrs);

        }

        return new Vector();

    }

    public static Vector listMemberCredit(int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT CONT." + PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID] + ", "
                    + "CONT." + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + ", "
                    + "CONT." + PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE] + ", "
                    + "CONT." + PstContactList.fieldNames[PstContactList.FLD_COMP_NAME] + " "
                    + " FROM " + PstContactList.TBL_CONTACT_LIST + " AS CONT "
                    + " INNER JOIN " + PstContactClassAssign.TBL_CNT_CLS_ASSIGN + " AS ASS "
                    + " ON CONT." + PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]
                    + " = ASS." + PstContactClassAssign.fieldNames[PstContactClassAssign.FLD_CONTACT_ID]
                    + " INNER JOIN " + PstContactClass.TBL_CONTACT_CLASS + " AS CLS "
                    + " ON CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CONTACT_CLASS_ID]
                    + " = ASS." + PstContactClassAssign.fieldNames[PstContactClassAssign.FLD_CNT_CLS_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("sql : " + sql);

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                ContactList contact = new ContactList();

                resultToObject(rs, contact);

                lists.add(contact);

            }

            rs.close();

            return lists;

        } catch (Exception e) {

            System.out.println(e);

        } finally {

            DBResultSet.close(dbrs);

        }

        return new Vector();

    }

    public static void resultToObject(ResultSet rs, ContactList contact) {

        try {

            contact.setOID(rs.getLong(PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]));
            contact.setPersonName(rs.getString(PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]));
            contact.setTelpMobile(rs.getString(PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE]));
            contact.setCompName(rs.getString(PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]));

        } catch (Exception e) {

            System.out.println("exception 123 : " + e.toString());

        }

    }

    public static void resultToObjectInHouseGuest(ResultSet rs, Contact contact) {

        try {

            contact.setOID(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_RESERVATION_ID]));
            contact.setPersonName(rs.getString(PstContact.fieldNames[PstContact.FLD_PERSON_NAME]));

        } catch (Exception e) {

            System.out.println("exception 123 : " + e.toString());

        }

    }

    public static BillMainCostum fetchByCashierID(long oid) {
        BillMainCostum billMainCostum = new BillMainCostum();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT company." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_NAME] + ", "
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_NAME] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_ADDRESS] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_WEBSITE] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_TELEPHONE] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_EMAIL] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_FAX] + " "
                    + "FROM " + PstCashCashier.TBL_CASH_CASHIER + " AS cashier "
                    + "INNER JOIN " + PstCashMaster.TBL_CASH_MASTER + " AS cashMaster "
                    + "ON cashier." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASHMASTER_ID] + "=cashMaster." + PstCashMaster.fieldNames[PstCashMaster.FLD_CASH_MASTER_ID] + " "
                    + "INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS location "
                    + "ON cashMaster." + PstCashMaster.fieldNames[PstCashMaster.FLD_LOCATION_ID] + "=location." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + " "
                    + "INNER JOIN " + PstCompany.TBL_AISO_COMPANY + " AS company "
                    + "ON location." + PstLocation.fieldNames[PstLocation.FLD_COMPANY_ID] + "=company." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + " "
                    + "WHERE cashier." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID] + "='" + oid + "'";

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                billMainCostum = new BillMainCostum();
                billMainCostum.setCompanyName(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY_NAME]));
                billMainCostum.setLocationName(rs.getString(PstLocation.fieldNames[PstLocation.FLD_NAME]));
                billMainCostum.setLocationAddress(rs.getString(PstLocation.fieldNames[PstLocation.FLD_ADDRESS]));
                billMainCostum.setLocationEmail(rs.getString(PstLocation.fieldNames[PstLocation.FLD_EMAIL]));
                billMainCostum.setLocationFax(rs.getString(PstLocation.fieldNames[PstLocation.FLD_FAX]));
                billMainCostum.setLocationTelp(rs.getString(PstLocation.fieldNames[PstLocation.FLD_TELEPHONE]));
                billMainCostum.setLocationWeb(rs.getString(PstLocation.fieldNames[PstLocation.FLD_WEBSITE]));
            }

            rs.close();

            return billMainCostum;

        } catch (Exception e) {

            System.out.println(e);

        } finally {

            DBResultSet.close(dbrs);

        }

        return new BillMainCostum();
    }

    public static BillMainCostum fetchByOid(long oid) {
        BillMainCostum billMainCostum = new BillMainCostum();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT company." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_NAME] + ", "
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_NAME] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_ADDRESS] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_WEBSITE] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_TELEPHONE] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_EMAIL] + ","
                    + "location." + PstLocation.fieldNames[PstLocation.FLD_FAX] + " "
                    + "FROM " + PstCashCashier.TBL_CASH_CASHIER + " AS cashier "
                    + "INNER JOIN " + PstCashMaster.TBL_CASH_MASTER + " AS cashMaster "
                    + "ON cashier." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASHMASTER_ID] + "=cashMaster." + PstCashMaster.fieldNames[PstCashMaster.FLD_CASH_MASTER_ID] + " "
                    + "INNER JOIN " + PstLocation.TBL_P2_LOCATION + " AS location "
                    + "ON cashMaster." + PstCashMaster.fieldNames[PstCashMaster.FLD_LOCATION_ID] + "=location." + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + " "
                    + "INNER JOIN " + PstCompany.TBL_AISO_COMPANY + " AS company "
                    + "ON location." + PstLocation.fieldNames[PstLocation.FLD_COMPANY_ID] + "=company." + PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID] + " "
                    + "WHERE cashier." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID] + "='" + oid + "'";

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                billMainCostum = new BillMainCostum();
                billMainCostum.setCompanyName(rs.getString(PstCompany.fieldNames[PstCompany.FLD_COMPANY_NAME]));
                billMainCostum.setLocationName(rs.getString(PstLocation.fieldNames[PstLocation.FLD_NAME]));
                billMainCostum.setLocationAddress(rs.getString(PstLocation.fieldNames[PstLocation.FLD_ADDRESS]));
                billMainCostum.setLocationEmail(rs.getString(PstLocation.fieldNames[PstLocation.FLD_EMAIL]));
                billMainCostum.setLocationFax(rs.getString(PstLocation.fieldNames[PstLocation.FLD_FAX]));
                billMainCostum.setLocationTelp(rs.getString(PstLocation.fieldNames[PstLocation.FLD_TELEPHONE]));
                billMainCostum.setLocationWeb(rs.getString(PstLocation.fieldNames[PstLocation.FLD_WEBSITE]));
            }

            rs.close();

            return billMainCostum;

        } catch (Exception e) {

            System.out.println(e);

        } finally {

            DBResultSet.close(dbrs);

        }

        return new BillMainCostum();
    }

    public static Vector listPaymentSystemCustom(int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT DISTINCT(" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + ") AS experiment, "
                    + PstPaymentSystem.TBL_P2_PAYMENT_SYSTEM + ".* FROM " + PstPaymentSystem.TBL_P2_PAYMENT_SYSTEM;

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("sql : " + sql);

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                PaymentSystem paymentSystem = new PaymentSystem();

                resultToObject(rs, paymentSystem);

                lists.add(paymentSystem);

            }

            rs.close();

            return lists;

        } catch (Exception e) {

            System.out.println(e);

        } finally {

            DBResultSet.close(dbrs);

        }

        return new Vector();

    }

    private static void resultToObject(ResultSet rs, PaymentSystem paymentsystem) {
        try {
            paymentsystem.setOID(rs.getLong(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_SYSTEM_ID]));
            paymentsystem.setPaymentSystem(rs.getString(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_SYSTEM]));
            paymentsystem.setDescription(rs.getString(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_DESCRIPTION]));
            paymentsystem.setAccountName(rs.getString(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_ACCOUNT_NAME]));
            paymentsystem.setAccountNumber(rs.getString(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_ACCOUNT_NUMBER]));
            paymentsystem.setBankAddress(rs.getString(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_ADDRESS]));
            paymentsystem.setBankInfoIn(rs.getBoolean(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_IN]));
            paymentsystem.setBankInfoOut(rs.getBoolean(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]));
            paymentsystem.setBankName(rs.getString(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_NAME]));
            paymentsystem.setCardInfo(rs.getBoolean(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]));
            paymentsystem.setCheckBGInfo(rs.getBoolean(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]));
            paymentsystem.setDays(rs.getInt(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_DAYS]));
            paymentsystem.setDueDateInfo(rs.getBoolean(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_DUE_DATE_INFO]));
            paymentsystem.setSwiftCode(rs.getString(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_SWIFT_CODE]));
            paymentsystem.setFixed(rs.getBoolean(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_FIXED]));
            paymentsystem.setClearedRefId(rs.getLong(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CLEARED_REF_ID]));

            //adding payment_type
            //by mirahu 12032012
            paymentsystem.setPaymentType(rs.getInt(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]));

            //add opie-eyek 20022013untuk setting cost bank and charge to customer percent
            paymentsystem.setChargeToCustomerPercent(rs.getDouble(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHARGE_TO_CUSTOMER_PERCENT]));
            paymentsystem.setBankCostPercent(rs.getDouble(PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_COST_PERCENT]));

        } catch (Exception e) {
        }
    }

    public static double getTotalQty(long oidBillMain) {
        double counter = 0;

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT SUM(" + PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY] + ") AS " + PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY] + " "
                    + "FROM " + PstBillDetail.TBL_CASH_BILL_DETAIL + " "
                    + "WHERE " + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + oidBillMain + "'";

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                counter = rs.getDouble(PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY]);
            }
            rs.close();
            return counter;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return counter;
    }

    public static Vector listCustomer(int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT contact." + PstContact.fieldNames[PstContact.FLD_PERSON_NAME] + ", "
                    + "contact." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + ","
                    + "contactClass." + PstContactClass.fieldNames[PstContactClass.FLD_CONTACT_CLASS_ID] + ", "
                    + "contactClass." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + ", "
                    + "contact." + PstContact.fieldNames[PstContact.FLD_EMPLOYEE_ID] + " "
                    + "FROM " + PstBillMain.TBL_CASH_BILL_MAIN + " AS billMain "
                    + "INNER JOIN " + PstContact.TBL_CONTACT + " AS contact "
                    + "ON billMain." + PstBillMain.fieldNames[PstBillMain.FLD_CUSTOMER_ID] + "=contact." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + " "
                    + "LEFT JOIN " + PstContactClassAssign.TBL_CNT_CLS_ASSIGN + " AS classAsign "
                    + "ON contact." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + "=classAsign." + PstContactClassAssign.fieldNames[PstContactClassAssign.FLD_CONTACT_ID] + " "
                    + "LEFT JOIN " + PstContactClass.TBL_CONTACT_CLASS + " AS contactClass "
                    + "ON classAsign." + PstContactClassAssign.fieldNames[PstContactClassAssign.FLD_CNT_CLS_ID] + "=contactClass." + PstContactClass.fieldNames[PstContactClass.FLD_CONTACT_CLASS_ID];

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("sql : " + sql);

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                BillMainCostum billMainCostum = new BillMainCostum();

                resultToObjectCustomer(rs, billMainCostum);

                lists.add(billMainCostum);

            }

            rs.close();

            return lists;

        } catch (Exception e) {

            System.out.println(e);

        } finally {

            DBResultSet.close(dbrs);

        }

        return new Vector();

    }

    public static Vector listCustomerHanoman(int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = ""
                    + " SELECT contact.PERSON_NAME,contact.CONTACT_ID, contactClass.CONTACT_CLASS_ID ,"
                    + " contact.CONTACT_TYPE AS CLASS_TYPE, contact.EMPLOYEE_ID"
                    + " FROM cash_bill_main AS billMain INNER JOIN "
                    + " contact_list AS contact "
                    + " ON billMain.CUSTOMER_ID=contact.CONTACT_ID INNER JOIN "
                    + " contact_class AS contactClass ON contactClass.class_type = contact.CONTACT_TYPE";

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            System.out.println("sql : " + sql);

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                BillMainCostum billMainCostum = new BillMainCostum();

                resultToObjectCustomer(rs, billMainCostum);

                lists.add(billMainCostum);

            }

            rs.close();

            return lists;

        } catch (Exception e) {

            System.out.println(e);

        } finally {

            DBResultSet.close(dbrs);

        }

        return new Vector();

    }

    public static void resultToObjectCustomer(ResultSet rs, BillMainCostum billMainCostum) {

        try {

            billMainCostum.setOID(rs.getLong(PstContact.fieldNames[PstContact.FLD_CONTACT_ID]));
            billMainCostum.setGuestName(rs.getString(PstContact.fieldNames[PstContact.FLD_PERSON_NAME]));
            billMainCostum.setClassId(rs.getLong(PstContactClass.fieldNames[PstContactClass.FLD_CONTACT_CLASS_ID]));
            billMainCostum.setClassType(rs.getInt(PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]));
            billMainCostum.setEmployeeId(rs.getLong(PstContact.fieldNames[PstContact.FLD_EMPLOYEE_ID]));

        } catch (Exception e) {

            System.out.println("exception 123 : " + e.toString());

        }

    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(*) FROM " + TABLE_ROOM_CLASS;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    //Sales Credit
    public static double getSummarySalesCredit(long oidCashCashier) {
        double count = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT  SUM(CBD." + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE] + ")" + " AS AMOUNT_" + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]
                    + //adding tax, service
                    //by mirahu 26122011
                    ", CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TAX_VALUE] + " AS TAX_VALUE"
                    + ", CBM." + PstBillMain.fieldNames[PstBillMain.FLD_SERVICE_VALUE] + " AS SERVICE_VALUE"
                    + ", CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DISCOUNT] + " AS DISCOUNT_VALUE"
                    + " FROM " + PstBillMain.TBL_CASH_BILL_MAIN + " CBM"
                    + " INNER JOIN " + PstBillDetail.TBL_CASH_BILL_DETAIL + " CBD"
                    + " ON CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]
                    + " = CBD." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]
                    + " WHERE CBM." + PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]
                    + " = '" + oidCashCashier + "'"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + " =0"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "=1"
                    + //" AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "=1" +
                    " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "=1 "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "=1 "
                    + //group by cash bill main id
                    //by mirahu 26122011
                    " GROUP BY CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]
                    + " ORDER BY " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS];

            //if (whereClause != null && whereClause.length() > 0)
            // sql = sql + " WHERE " + whereClause;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                //count = rs.getDouble(1);
                count += (rs.getDouble(1) + rs.getDouble("TAX_VALUE") + rs.getDouble("SERVICE_VALUE")) - rs.getDouble("DISCOUNT_VALUE");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return count;

    }

    //SUMMERY CREDIT RETURN
    public static double getSummarySalesCreditReturn(long oidCashCashier) {
        double count = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT  SUM(CBD." + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE] + ")" + " AS AMOUNT_" + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]
                    + //adding tax, service
                    //by mirahu 26122011
                    ", CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TAX_VALUE] + " AS TAX_VALUE"
                    + ", CBM." + PstBillMain.fieldNames[PstBillMain.FLD_SERVICE_VALUE] + " AS SERVICE_VALUE"
                    + ", CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DISCOUNT] + " AS DISCOUNT_VALUE"
                    + " FROM " + PstBillMain.TBL_CASH_BILL_MAIN + " CBM"
                    + " INNER JOIN " + PstBillDetail.TBL_CASH_BILL_DETAIL + " CBD"
                    + " ON CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]
                    + " = CBD." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]
                    + " WHERE CBM." + fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]
                    + " = " + oidCashCashier
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + " =1"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "=1"
                    + //" AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "=1" +
                    " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "=0 "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "=1 "
                    + //group by cash bill main id
                    //by mirahu 26122011
                    " GROUP BY CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]
                    + " ORDER BY " + fieldNames[PstBillMain.FLD_TRANSACTION_STATUS];

            //if (whereClause != null && whereClause.length() > 0)
            // sql = sql + " WHERE " + whereClause;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                //count = rs.getDouble(1);
                count += (rs.getDouble(1) + rs.getDouble("TAX_VALUE") + rs.getDouble("SERVICE_VALUE")) - rs.getDouble("DISCOUNT_VALUE");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return count;

    }

    public static double getSummaryCash(long oidCashCashier) {
        double count = 0;
        double countPrice = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT  SUM(CBD." + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE] + ")" + " AS AMOUNT_" + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]
                    + //adding tax, service
                    //by mirahu 26122011
                    //adding disc
                    //add opie  19-06-2012
                    ", CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TAX_VALUE] + " AS TAX_VALUE"
                    + ", CBM." + PstBillMain.fieldNames[PstBillMain.FLD_SERVICE_VALUE] + " AS SERVICE_VALUE"
                    + ", CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DISCOUNT] + " AS DISC_VALUE"
                    + " FROM " + PstBillMain.TBL_CASH_BILL_MAIN + " CBM"
                    + " INNER JOIN " + PstBillDetail.TBL_CASH_BILL_DETAIL + " CBD"
                    + " ON CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]
                    + " = CBD." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]
                    + " WHERE CBM." + PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]
                    + " = " + oidCashCashier
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + " =0"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "=0"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "=0 "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "=1"
                    + //group by cash bill main id
                    //by mirahu 26122011
                    " GROUP BY CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]
                    + " ORDER BY " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS];

            //if (whereClause != null && whereClause.length() > 0)
            // sql = sql + " WHERE " + whereClause;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                //count = rs.getDouble(1);
                count += rs.getDouble(1) + rs.getDouble("TAX_VALUE") + rs.getDouble("SERVICE_VALUE") - rs.getDouble("DISC_VALUE");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return count;

    }

    //SUMMERY CASH RETURN
    public static double getSummaryCashReturn(long oidCashCashier) {
        double count = 0;
        double countPrice = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT  SUM(CBD." + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE] + ")" + " AS AMOUNT_" + PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]
                    + //adding tax, service
                    //by mirahu 26122011
                    //adding disc
                    //add opie  19-06-2012
                    ", CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TAX_VALUE] + " AS TAX_VALUE"
                    + ", CBM." + PstBillMain.fieldNames[PstBillMain.FLD_SERVICE_VALUE] + " AS SERVICE_VALUE"
                    + ", CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DISCOUNT] + " AS DISC_VALUE"
                    + " FROM " + PstBillMain.TBL_CASH_BILL_MAIN + " CBM"
                    + " INNER JOIN " + PstBillDetail.TBL_CASH_BILL_DETAIL + " CBD"
                    + " ON CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]
                    + " = CBD." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]
                    + " WHERE CBM." + PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]
                    + " = " + oidCashCashier
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + " =1"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "=0"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "=0 "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "=1"
                    + //group by cash bill main id
                    //by mirahu 26122011
                    " GROUP BY CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]
                    + " ORDER BY " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS];

            //if (whereClause != null && whereClause.length() > 0)
            // sql = sql + " WHERE " + whereClause;
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                //count = rs.getDouble(1);
                count += rs.getDouble(1) + rs.getDouble("TAX_VALUE") + rs.getDouble("SERVICE_VALUE") - rs.getDouble("DISC_VALUE");
            }
            rs.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return count;

    }

    public static int checkReturn(long oidBillMain) {
        int lists = 0;

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT SUM(" + PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY] + ") - "
                    + "("
                    + "SELECT IFNULL(SUM(billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY] + "),0) "
                    + "FROM " + PstBillDetail.TBL_CASH_BILL_DETAIL + " AS billDetail "
                    + "INNER JOIN " + PstBillMain.TBL_CASH_BILL_MAIN + " AS billMain "
                    + "ON billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "=billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + " "
                    + "WHERE billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='" + oidBillMain + "' "
                    + " AND ("
                    + " ("
                    + "" + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + " = '1'"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + " = '0'"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + " = '0'"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + " = '1'"
                    + " ) OR ("
                    + "" + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + " = '1'"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + " = '1'"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + " = '0'"
                    + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + " = '1'"
                    + ")"
                    + ")"
                    + ") AS " + PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY] + " "
                    + "FROM " + PstBillDetail.TBL_CASH_BILL_DETAIL + " "
                    + "WHERE " + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + oidBillMain + "' ";

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                lists = rs.getInt(PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY]);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    public static int checkReturn(long oidBillMain, long oidBillDetail) {
        int lists = 0;

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT IFNULL(SUM(" + PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY] + "),0) - "
                    + "("
                    + "SELECT IFNULL(SUM(billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY] + "),0) "
                    + "FROM " + PstBillDetail.TBL_CASH_BILL_DETAIL + " AS billDetail "
                    + "INNER JOIN " + PstBillMain.TBL_CASH_BILL_MAIN + " AS billMain "
                    + "ON billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "=billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + " "
                    + "WHERE billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='" + oidBillMain + "' "
                    + "AND billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_DETAIL_ID] + "='" + oidBillDetail + "'"
                    + ") AS " + PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY] + " "
                    + "FROM " + PstBillDetail.TBL_CASH_BILL_DETAIL + " "
                    + "WHERE " + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + oidBillMain + "' "
                    + "AND " + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_DETAIL_ID] + "='" + oidBillDetail + "'";

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                lists = rs.getInt(PstBillDetail.fieldNames[PstBillDetail.FLD_QUANTITY]);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

}
