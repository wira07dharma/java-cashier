/* Created on 	:  [date] [time] AM/PM
 *
 * @author  	:  lkarunia
 * @version  	:  01
 * @update by   :  eka (melakukan modulisasi untuk pengimputan payment rec)
 */
/*******************************************************************
 * Class Description 	: [project description ...]
 * Imput Parameters 	: [input parameter ...]
 * Output 				: [output ...]
 *******************************************************************/
package com.dimata.cashierweb.entity.cashier.transaction;

/* package java */
import java.io.*;

import java.sql.*;

import java.util.*;

import java.util.Date;



/* package qdep */

import com.dimata.util.lang.I_Language;

import com.dimata.util.*;

import com.dimata.common.db.*;

import com.dimata.qdep.entity.*;



/* package hanoman */

//import com.dimata.hanoman.entity.cashier.*;

//import com.dimata.hanoman.entity.reservation.*;

import com.dimata.hanoman.entity.masterdata.*;
import com.dimata.harisma.entity.masterdata.*;

//import com.dimata.hanoman.entity.accon.*;

//import com.dimata.hanoman.entity.system.*;

//import com.dimata.hanoman.session.admin.*;

//import com.dimata.hanoman.session.reservation.*;



import com.dimata.ObjLink.BOCashier.*;



import com.dimata.pos.entity.payment.*;

public class PstPaymentRec  extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    //public static final  String TBL_PAYMENT_REC = "PAYMENT_REC";
    public static final String TBL_PAYMENT_REC = "payment_rec";
    public static final int FLD_PAYMENT_REC_ID = 0;
    public static final int FLD_PAYMENT_TYPE_ID = 1;
    public static final int FLD_RESERVATION_ID = 2;
    public static final int FLD_PAYMENT_DATE = 3;
    public static final int FLD_AMOUNT_RP = 4;
    public static final int FLD_AMOUNT_$ = 5;
    public static final int FLD_AMOUNT_TEXT = 6;
    public static final int FLD_RECEIVED_BY = 7;
    public static final int FLD_REC_NUMBER = 8;
    public static final int FLD_EXCHANGE_RATE = 9;
    public static final int FLD_DESCRIPTION = 10;
    public static final int FLD_PAYMENT_PURPOSE = 11;
    public static final int FLD_CC_ISSUING_BANK = 12;
    public static final int FLD_CC_NUMBER = 13;
    public static final int FLD_CC_EXPIRED_DATE = 14;
    public static final int FLD_CC_HOLDER_NAME = 15;
    public static final int FLD_BILL_ADDR_STREET = 16;
    public static final int FLD_BILL_ADDR_CITY = 17;
    public static final int FLD_BILL_ADDR_STATE = 18;
    public static final int FLD_BILL_ADDR_ZIP_CODE = 19;
    public static final int FLD_BILL_ADDR_COUNTRY = 20;
    public static final int FLD_BILL_ADDR_PHONE_NUMBER = 21;
    public static final int FLD_PAYMENT_TYPE = 22;
    public static final int FLD_ADD_OR_SUBSTRACT = 23;
    public static final int FLD_CURRENCY_USED = 24;
    public static final int FLD_RECEIVED_BY_ID = 25;
    public static final int FLD_COVER_BILLING_ID = 26;//cash_bill_main
    public static final int FLD_TELEPHONE_CALL_ID = 27;
    public static final int FLD_INSTALLMENT_PAYMENT_ID = 28;
    public static final int FLD_MAINTENANCE_FEE_PAYMENT_ID = 29;
    public static final int FLD_CUSTOMER_ID = 30;
    public static final int FLD_CASH_CASHIER_ID = 31;
    public static final int FLD_RESERVATION_COMMISSION_ID = 32;
    public static final int FLD_NUMBER = 33;
    public static final int FLD_COUNTER = 34;
    public static final int FLD_PAY_OUT = 35;
    public static final int FLD_STATUS = 36;
    public static final int FLD_DATE = 37;
    public static final int FLD_PAYMENT_SYSTEM_ID = 38;
    public static final int FLD_CLEARED_TO_ID = 39;
    public static final int FLD_DUE_DATE = 40;
    public static final String[] fieldNames = {
        "PAYMENT_REC_ID", "CC_TYPE_ID", "RESERVATION_ID",
        "PAYMENT_DATE", "AMOUNT_RP", "AMOUNT_$", //5
        "AMOUNT_TEXT", "RECEIVED_BY", "REC_NUMBER", //8
        "EXCHANGE_RATE", "DESCRIPTION", "PAYMENT_PURPOSE", //11
        "CC_ISSUING_BANK", "CC_NUMBER", "CC_EXPIRED_DATE", // 14
        "CC_HOLDER_NAME", "BILL_ADDR_STREET", "BILL_ADDR_CITY", //17
        "BILL_ADDR_STATE", "BILL_ADDR_ZIP_CODE", "BILL_ADDR_COUNTRY", //20
        "BILL_ADDR_PHONE_NUMBER", "PAYMENT_TYPE", "ADD_OR_SUBSTRACT",  //23
        "CURRENCY_USED", "RECEIVED_BY_ID", "CASH_BILL_MAIN_ID", // 26
        "TELEPHONE_CALL_ID", "INSTALLMENT_PAYMENT_ID", "MAINTENANCE_FEE_PAYMENT_ID",
        "CUSTOMER_ID", "CASH_CASHIER_ID",
        "RESERVATION_COMMISSION_ID",
        "NUMBER", "COUNTER", "PAY_OUT",
        "STATUS", "DATE",
        "PAYMENT_SYSTEM_ID", "CLEARED_TO_ID", "DUE_DATE"
    , 

      

                                                    };
    public static final  int   [] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,		
      TYPE_LONG,		
      TYPE_LONG,
        TYPE_DATE,		
        TYPE_FLOAT,		
        TYPE_FLOAT,
        TYPE_STRING,		
        TYPE_STRING,		
        TYPE_STRING,
        TYPE_FLOAT,		
        TYPE_STRING,		
        TYPE_INT ,
        TYPE_STRING, 
        TYPE_STRING, 
        TYPE_DATE,
        TYPE_STRING, 
        TYPE_STRING, 
        TYPE_STRING,
        TYPE_STRING, 
        TYPE_STRING, 
        TYPE_STRING,
        TYPE_STRING, 
        TYPE_INT, 
        TYPE_LONG,
        TYPE_LONG + TYPE_FK, 
        TYPE_LONG, 
        TYPE_LONG,
        TYPE_LONG, 
        TYPE_LONG,  
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,       
        TYPE_LONG,
        TYPE_STRING, 
        TYPE_INT, 
        TYPE_BOOL,
        TYPE_INT, 
        TYPE_DATE,        
        TYPE_LONG, 
        TYPE_LONG, 
        TYPE_DATE,
     
    };





    public static final int COUNT_INVOICE 	=       4;
    public static final int PAY_DEPOSIT = 0;
    public static final int PAY_ROOM_RSV = 1;
    public static final int PAY_BILLING = 2;
    public static final int PAY_TELP = 3;
    public static final int PAY_OUTLET = 4;
    public static final int PAY_ALL = 5;
    public static final int PAY_INSTALLMENT = 6;
    public static final int PAY_MAINTENANCE_FEE = 7;
    public static final int PAY_ADJUSTMENT = 8;
    public static final int PAY_COMMISSION = 9;
    public static final String[] paymentPurpose = {
        "Deposit", "Room Reservation", "Billing", "Telephone", "Outlet", "All Payment", "Installment", "Maintenence Fee",
        "Adjustment", "Commission"
    };
    public static final String[] paymentAt = {"Deposit", "Invoice", "Billing", "Telephone", "Outlet", "", "Installment",
        "Maintenance Fee", "Invoice"
    };
    public static final int PAYMENT_TYPE_CASH = 0;
    public static final int PAYMENT_TYPE_CC = 1;
    public static final int PAYMENT_TYPE_BANK_TRANSFER = 2;
    public static final int PAYMENT_TYPE_OUTLET_OTHER = 3;    //no 3 & 4 hanya digunakan untuk outlet system
    public static final int PAYMENT_TYPE_INCLUDE_TO_ROOM_BILL = 4;
    public static final int PAYMENT_TYPE_CITY_LEDGER = 5;
    public static final int PAYMENT_TYPE_DEBET_CARD = 6;
    public static final int PAYMENT_TYPE_CHEQUE = 7;
    public static final String[] paymentStr = {"Cash", "Credit Card", "Bank Transfer", "Outlet Other", "Include To Room Bill", "City Ledger",
        "Debet Card", "Cheque"
    };
    public static final int TYPE_ADD = 0;
    public static final int TYPE_SUBSTRACT = 1;
    public static final String[] addSubstract = {"Add", "Substract"};
    //public static final int CURRENCY_RP = Integer.parseInt(PstSystemProperty.getValueByName("CURRENCY_RP")); //1
   // public static final int CURRENCY_USD = Integer.parseInt(PstSystemProperty.getValueByName("CURRENCY_USD"));//2
   // public static final String strForeignCurrencyDefault = AppValue.getValueByKey("EXCHANGE_RATE");
    //public static final String strLocalCurrencyDefault = AppValue.getValueByKey("LOCAL_CURRENCY_DEFAULT");
   // public static final String[] currencyStr = {"", strLocalCurrencyDefault, strForeignCurrencyDefault};
    public static final int PAID_FOR_INVOICE = 0;
    public static final int PAID_FOR_DEPOSIT = 1;
    public static final int PAID_FOR_DEPOSIT_PAYMENT = 2;
    public static final int PAID_FOR_DEPOSIT_REFUND = 3;
    public static final int PAID_FOR_OTHER_INCOME = 4;
    public static final int PAID_FOR_PAYMENT_REFUND=5;
    public static final int PAID_FOR_EXPENSE = 6;
    public static final String[] strPaidFor = {"Payment", "Deposit", "Deposit Payment", "Deposit Refund", "Other Income","Payment Refund"};//, "Loss/Expense"};
    public PstPaymentRec() {

    }

    public PstPaymentRec(int i) throws DBException {

        super(new PstPaymentRec());

    }

    public PstPaymentRec(String sOid) throws DBException {

        super(new PstPaymentRec(0));

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public PstPaymentRec(long lOid) throws DBException {

        super(new PstPaymentRec(0));

        String sOid = "0";

        try {

            sOid = String.valueOf(lOid);

        } catch (Exception e) {

            throw new DBException(this, DBException.RECORD_NOT_FOUND);

        }

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public int getFieldSize() {

        return fieldNames.length;

    }

    public String getTableName() {

        return TBL_PAYMENT_REC;

    }

    public String[] getFieldNames() {

        return fieldNames;

    }

    public int[] getFieldTypes() {

        return fieldTypes;

    }

    public String getPersistentName() {

        return new PstPaymentRec().getClass().getName();

    }

    public long fetchExc(Entity ent) throws Exception {

        PaymentRec paymentrec = fetchExc(ent.getOID());

        ent = (Entity) paymentrec;

        return paymentrec.getOID();

    }

    public long insertExc(Entity ent) throws Exception {

        return insertExc((PaymentRec) ent);

    }

    public long updateExc(Entity ent) throws Exception {

        return updateExc((PaymentRec) ent);

    }

    public long deleteExc(Entity ent) throws Exception {

        if (ent == null) {

            throw new DBException(this, DBException.RECORD_NOT_FOUND);

        }

        return deleteExc(ent.getOID());

    }

    public static PaymentRec fetchExc(long oid) throws DBException {

        try {

            PaymentRec paymentrec = new PaymentRec();

            PstPaymentRec pstPaymentRec = new PstPaymentRec(oid);

            paymentrec.setOID(oid);



            paymentrec.setPaymentTypeId(pstPaymentRec.getlong(FLD_PAYMENT_TYPE_ID));

            paymentrec.setReservationId(pstPaymentRec.getlong(FLD_RESERVATION_ID));

            paymentrec.setPaymentDate(pstPaymentRec.getDate(FLD_PAYMENT_DATE));

            paymentrec.setAmountRp(pstPaymentRec.getdouble(FLD_AMOUNT_RP));

            paymentrec.setAmount$(pstPaymentRec.getdouble(FLD_AMOUNT_$));



            paymentrec.setAmountText(pstPaymentRec.getString(FLD_AMOUNT_TEXT));

            paymentrec.setReceivedBy(pstPaymentRec.getString(FLD_RECEIVED_BY));

            paymentrec.setRecNumber(pstPaymentRec.getString(FLD_REC_NUMBER));

            paymentrec.setExchangeRate(pstPaymentRec.getdouble(FLD_EXCHANGE_RATE));

            paymentrec.setDescription(pstPaymentRec.getString(FLD_DESCRIPTION));

            paymentrec.setPaymentPurpose(pstPaymentRec.getInt(FLD_PAYMENT_PURPOSE));



            paymentrec.setCcIssuingBank(pstPaymentRec.getString(FLD_CC_ISSUING_BANK));

            paymentrec.setCcNumber(pstPaymentRec.getString(FLD_CC_NUMBER));

            paymentrec.setCcExpiredDate(pstPaymentRec.getDate(FLD_CC_EXPIRED_DATE));

            paymentrec.setCcHolderName(pstPaymentRec.getString(FLD_CC_HOLDER_NAME));

            paymentrec.setBillAddrCity(pstPaymentRec.getString(FLD_BILL_ADDR_CITY));

            paymentrec.setBillAddrCountry(pstPaymentRec.getString(FLD_BILL_ADDR_COUNTRY));

            paymentrec.setBillAddrPhoneNumber(pstPaymentRec.getString(FLD_BILL_ADDR_PHONE_NUMBER));

            paymentrec.setBillAddrState(pstPaymentRec.getString(FLD_BILL_ADDR_STATE));

            paymentrec.setBillAddrStreet(pstPaymentRec.getString(FLD_BILL_ADDR_STREET));

            paymentrec.setBillAddrZipCode(pstPaymentRec.getString(FLD_BILL_ADDR_ZIP_CODE));

            paymentrec.setPaymentType(pstPaymentRec.getInt(FLD_PAYMENT_TYPE));

            paymentrec.setAddOrSubstract(pstPaymentRec.getInt(FLD_ADD_OR_SUBSTRACT));

            paymentrec.setCurrencyUsed(pstPaymentRec.getlong(FLD_CURRENCY_USED));

            paymentrec.setReceivedById(pstPaymentRec.getlong(FLD_RECEIVED_BY_ID));

            paymentrec.setCoverBillingId(pstPaymentRec.getlong(FLD_COVER_BILLING_ID));

            paymentrec.setTelephoneCallId(pstPaymentRec.getlong(FLD_TELEPHONE_CALL_ID));

            paymentrec.setCustomerId(pstPaymentRec.getlong(FLD_CUSTOMER_ID));

            //paymentrec.setShiftId(pstPaymentRec.getlong(FLD_SHIFT_ID));

            paymentrec.setCashierId(pstPaymentRec.getlong(FLD_CASH_CASHIER_ID));



            paymentrec.setReservationCommissionId(pstPaymentRec.getlong(FLD_RESERVATION_COMMISSION_ID));

            paymentrec.setNumber(pstPaymentRec.getString(FLD_NUMBER));

            paymentrec.setCounter(pstPaymentRec.getInt(FLD_COUNTER));

            paymentrec.setPayOut(pstPaymentRec.getboolean(FLD_PAY_OUT));

            paymentrec.setStatus(pstPaymentRec.getInt(FLD_STATUS));

            paymentrec.setDate(pstPaymentRec.getDate(FLD_DATE));



            paymentrec.setPaymentSystemId(pstPaymentRec.getlong(FLD_PAYMENT_SYSTEM_ID));

            paymentrec.setClearedToId(pstPaymentRec.getlong(FLD_CLEARED_TO_ID));

            paymentrec.setDueDate(pstPaymentRec.getDate(FLD_DUE_DATE));



            return paymentrec;

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstPaymentRec(0), DBException.UNKNOWN);

        }

    }

    public static long insertExc(PaymentRec paymentrec) throws DBException {

        try {



            PstPaymentRec pstPaymentRec = new PstPaymentRec(0);



            pstPaymentRec.setLong(FLD_PAYMENT_TYPE_ID, paymentrec.getPaymentTypeId());

            pstPaymentRec.setLong(FLD_RESERVATION_ID, paymentrec.getReservationId());

            pstPaymentRec.setDate(FLD_PAYMENT_DATE, paymentrec.getPaymentDate());

            pstPaymentRec.setDouble(FLD_AMOUNT_RP, paymentrec.getAmountRp());

            pstPaymentRec.setDouble(FLD_AMOUNT_$, paymentrec.getAmount$());

            pstPaymentRec.setString(FLD_AMOUNT_TEXT, paymentrec.getAmountText());

            pstPaymentRec.setString(FLD_RECEIVED_BY, paymentrec.getReceivedBy());

            pstPaymentRec.setString(FLD_REC_NUMBER, paymentrec.getRecNumber());

            pstPaymentRec.setDouble(FLD_EXCHANGE_RATE, paymentrec.getExchangeRate());

            pstPaymentRec.setString(FLD_DESCRIPTION, paymentrec.getDescription());

            pstPaymentRec.setInt(FLD_PAYMENT_PURPOSE, paymentrec.getPaymentPurpose());



            pstPaymentRec.setDate(FLD_CC_EXPIRED_DATE, paymentrec.getCcExpiredDate());

            pstPaymentRec.setString(FLD_CC_HOLDER_NAME, paymentrec.getCcHolderName());

            pstPaymentRec.setString(FLD_CC_ISSUING_BANK, paymentrec.getCcIssuingBank());

            pstPaymentRec.setString(FLD_CC_NUMBER, paymentrec.getCcNumber());

            pstPaymentRec.setString(FLD_BILL_ADDR_CITY, paymentrec.getBillAddrCity());

            pstPaymentRec.setString(FLD_BILL_ADDR_COUNTRY, paymentrec.getBillAddrCountry());

            pstPaymentRec.setString(FLD_BILL_ADDR_PHONE_NUMBER, paymentrec.getBillAddrPhoneNumber());

            pstPaymentRec.setString(FLD_BILL_ADDR_STATE, paymentrec.getBillAddrState());

            pstPaymentRec.setString(FLD_BILL_ADDR_STREET, paymentrec.getBillAddrStreet());

            pstPaymentRec.setString(FLD_BILL_ADDR_ZIP_CODE, paymentrec.getBillAddrZipCode());

            pstPaymentRec.setInt(FLD_PAYMENT_TYPE, paymentrec.getPaymentType());

            pstPaymentRec.setInt(FLD_ADD_OR_SUBSTRACT, paymentrec.getAddOrSubstract());

            pstPaymentRec.setLong(FLD_CURRENCY_USED, paymentrec.getCurrencyUsed());

            pstPaymentRec.setLong(FLD_RECEIVED_BY_ID, paymentrec.getReceivedById());

            pstPaymentRec.setLong(FLD_COVER_BILLING_ID, paymentrec.getCoverBillingId());

            pstPaymentRec.setLong(FLD_TELEPHONE_CALL_ID, paymentrec.getTelephoneCallId());

            pstPaymentRec.setLong(FLD_CUSTOMER_ID, paymentrec.getCustomerId());

            pstPaymentRec.setLong(FLD_CASH_CASHIER_ID, paymentrec.getCashierId());



            pstPaymentRec.setLong(FLD_RESERVATION_COMMISSION_ID, paymentrec.getReservationCommissionId());

            pstPaymentRec.setString(FLD_NUMBER, paymentrec.getNumber());

            pstPaymentRec.setboolean(FLD_PAY_OUT, paymentrec.getPayOut());

            pstPaymentRec.setInt(FLD_COUNTER, paymentrec.getCounter());

            pstPaymentRec.setInt(FLD_STATUS, paymentrec.getStatus());

            pstPaymentRec.setDate(FLD_DATE, paymentrec.getDate());



            pstPaymentRec.setLong(FLD_PAYMENT_SYSTEM_ID, paymentrec.getPaymentSystemId());

            pstPaymentRec.setLong(FLD_CLEARED_TO_ID, paymentrec.getClearedToId());

            pstPaymentRec.setDate(FLD_DUE_DATE, paymentrec.getDueDate());



            pstPaymentRec.insert();

            paymentrec.setOID(pstPaymentRec.getlong(FLD_PAYMENT_REC_ID));



        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstPaymentRec(0), DBException.UNKNOWN);

        }

        return paymentrec.getOID();

    }

    public static long updateExc(PaymentRec paymentrec) throws DBException {

        try {

            if (paymentrec.getOID() != 0) {

                PstPaymentRec pstPaymentRec = new PstPaymentRec(paymentrec.getOID());



                pstPaymentRec.setLong(FLD_PAYMENT_TYPE_ID, paymentrec.getPaymentTypeId());

                pstPaymentRec.setLong(FLD_RESERVATION_ID, paymentrec.getReservationId());

                pstPaymentRec.setDate(FLD_PAYMENT_DATE, paymentrec.getPaymentDate());

                pstPaymentRec.setDouble(FLD_AMOUNT_RP, paymentrec.getAmountRp());

                pstPaymentRec.setDouble(FLD_AMOUNT_$, paymentrec.getAmount$());



                pstPaymentRec.setString(FLD_AMOUNT_TEXT, paymentrec.getAmountText());

                pstPaymentRec.setString(FLD_RECEIVED_BY, paymentrec.getReceivedBy());

                pstPaymentRec.setString(FLD_REC_NUMBER, paymentrec.getRecNumber());

                pstPaymentRec.setDouble(FLD_EXCHANGE_RATE, paymentrec.getExchangeRate());

                pstPaymentRec.setString(FLD_DESCRIPTION, paymentrec.getDescription());

                pstPaymentRec.setInt(FLD_PAYMENT_PURPOSE, paymentrec.getPaymentPurpose());



                pstPaymentRec.setDate(FLD_CC_EXPIRED_DATE, paymentrec.getCcExpiredDate());

                pstPaymentRec.setString(FLD_CC_HOLDER_NAME, paymentrec.getCcHolderName());

                pstPaymentRec.setString(FLD_CC_ISSUING_BANK, paymentrec.getCcIssuingBank());

                pstPaymentRec.setString(FLD_CC_NUMBER, paymentrec.getCcNumber());

                pstPaymentRec.setString(FLD_BILL_ADDR_CITY, paymentrec.getBillAddrCity());

                pstPaymentRec.setString(FLD_BILL_ADDR_COUNTRY, paymentrec.getBillAddrCountry());

                pstPaymentRec.setString(FLD_BILL_ADDR_PHONE_NUMBER, paymentrec.getBillAddrPhoneNumber());

                pstPaymentRec.setString(FLD_BILL_ADDR_STATE, paymentrec.getBillAddrState());

                pstPaymentRec.setString(FLD_BILL_ADDR_STREET, paymentrec.getBillAddrStreet());

                pstPaymentRec.setString(FLD_BILL_ADDR_ZIP_CODE, paymentrec.getBillAddrZipCode());

                pstPaymentRec.setInt(FLD_PAYMENT_TYPE, paymentrec.getPaymentType());

                pstPaymentRec.setInt(FLD_ADD_OR_SUBSTRACT, paymentrec.getAddOrSubstract());

                pstPaymentRec.setLong(FLD_CURRENCY_USED, paymentrec.getCurrencyUsed());

                pstPaymentRec.setLong(FLD_RECEIVED_BY_ID, paymentrec.getReceivedById());

                pstPaymentRec.setLong(FLD_COVER_BILLING_ID, paymentrec.getCoverBillingId());

                pstPaymentRec.setLong(FLD_TELEPHONE_CALL_ID, paymentrec.getTelephoneCallId());

                pstPaymentRec.setLong(FLD_CUSTOMER_ID, paymentrec.getCustomerId());

                // pstPaymentRec.setLong(FLD_SHIFT_ID, paymentrec.getShiftId());

                pstPaymentRec.setLong(FLD_CASH_CASHIER_ID, paymentrec.getCashierId());



                pstPaymentRec.setLong(FLD_RESERVATION_COMMISSION_ID, paymentrec.getReservationCommissionId());

                pstPaymentRec.setString(FLD_NUMBER, paymentrec.getNumber());

                pstPaymentRec.setboolean(FLD_PAY_OUT, paymentrec.getPayOut());

                pstPaymentRec.setInt(FLD_COUNTER, paymentrec.getCounter());

                pstPaymentRec.setInt(FLD_STATUS, paymentrec.getStatus());

                pstPaymentRec.setDate(FLD_DATE, paymentrec.getDate());



                pstPaymentRec.setLong(FLD_PAYMENT_SYSTEM_ID, paymentrec.getPaymentSystemId());

                pstPaymentRec.setLong(FLD_CLEARED_TO_ID, paymentrec.getClearedToId());

                pstPaymentRec.setDate(FLD_DUE_DATE, paymentrec.getDueDate());



                pstPaymentRec.update();

                return paymentrec.getOID();



            }

        } catch (DBException dbe) {

            System.out.println("dbe : " + dbe.toString());

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstPaymentRec(0), DBException.UNKNOWN);

        }

        return 0;

    }

    public static long deleteExc(long oid) throws DBException {

        try {

            PstPaymentRec pstPaymentRec = new PstPaymentRec(oid);

            pstPaymentRec.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstPaymentRec(0), DBException.UNKNOWN);

        }

        return oid;

    }

    public static long deleteBillPayments(long oidMain) {

        long oid = 0;

        DBResultSet dbrs = null;

        try {

            String sql = " DELETE FROM " + TBL_PAYMENT_REC+

                    " WHERE " + fieldNames[FLD_COVER_BILLING_ID] + "='" + oidMain + "'";

            DBHandler.execUpdate(sql);

        } catch (Exception e) {

            System.out.println(e);

        } finally {

            DBResultSet.close(dbrs);

        }

        return oidMain;

    }

    public static Vector listAll() {

        return list(0, 500, "", "");

    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT * FROM " + TBL_PAYMENT_REC;

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

                PaymentRec paymentrec = new PaymentRec();

                resultToObject(rs, paymentrec);

                lists.add(paymentrec);

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

    public static void resultToObject(ResultSet rs, PaymentRec paymentrec) {

        try {

            paymentrec.setOID(rs.getLong(PstPaymentRec.fieldNames[PstPaymentRec.FLD_PAYMENT_REC_ID]));

            paymentrec.setPaymentTypeId(rs.getLong(PstPaymentRec.fieldNames[PstPaymentRec.FLD_PAYMENT_TYPE_ID]));

            paymentrec.setReservationId(rs.getLong(PstPaymentRec.fieldNames[PstPaymentRec.FLD_RESERVATION_ID]));

            paymentrec.setPaymentDate(rs.getDate(PstPaymentRec.fieldNames[PstPaymentRec.FLD_PAYMENT_DATE]));

            paymentrec.setAmountRp(rs.getDouble(PstPaymentRec.fieldNames[PstPaymentRec.FLD_AMOUNT_RP]));

            paymentrec.setAmount$(rs.getDouble(PstPaymentRec.fieldNames[PstPaymentRec.FLD_AMOUNT_$]));



            paymentrec.setAmountText(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_AMOUNT_TEXT]));

            paymentrec.setReceivedBy(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_RECEIVED_BY]));

            paymentrec.setRecNumber(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_REC_NUMBER]));

            paymentrec.setExchangeRate(rs.getDouble(PstPaymentRec.fieldNames[PstPaymentRec.FLD_EXCHANGE_RATE]));

            paymentrec.setDescription(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_DESCRIPTION]));

            paymentrec.setPaymentPurpose(rs.getInt(PstPaymentRec.fieldNames[PstPaymentRec.FLD_PAYMENT_PURPOSE]));





            paymentrec.setCcExpiredDate(rs.getDate(PstPaymentRec.fieldNames[PstPaymentRec.FLD_CC_EXPIRED_DATE]));

            paymentrec.setCcHolderName(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_CC_HOLDER_NAME]));

            paymentrec.setCcIssuingBank(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_CC_ISSUING_BANK]));

            paymentrec.setCcNumber(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_CC_NUMBER]));

            paymentrec.setBillAddrCity(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_BILL_ADDR_CITY]));

            paymentrec.setBillAddrCountry(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_BILL_ADDR_COUNTRY]));

            paymentrec.setBillAddrPhoneNumber(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_BILL_ADDR_PHONE_NUMBER]));

            paymentrec.setBillAddrState(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_BILL_ADDR_STATE]));

            paymentrec.setBillAddrStreet(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_BILL_ADDR_STREET]));

            paymentrec.setBillAddrZipCode(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_BILL_ADDR_ZIP_CODE]));

            paymentrec.setPaymentType(rs.getInt(PstPaymentRec.fieldNames[PstPaymentRec.FLD_PAYMENT_TYPE]));

            paymentrec.setAddOrSubstract(rs.getInt(PstPaymentRec.fieldNames[PstPaymentRec.FLD_ADD_OR_SUBSTRACT]));

            paymentrec.setCurrencyUsed(rs.getLong(PstPaymentRec.fieldNames[PstPaymentRec.FLD_CURRENCY_USED]));

            paymentrec.setReceivedById(rs.getLong(PstPaymentRec.fieldNames[PstPaymentRec.FLD_RECEIVED_BY_ID]));

            paymentrec.setCoverBillingId(rs.getLong(PstPaymentRec.fieldNames[PstPaymentRec.FLD_COVER_BILLING_ID]));

            paymentrec.setTelephoneCallId(rs.getLong(PstPaymentRec.fieldNames[PstPaymentRec.FLD_TELEPHONE_CALL_ID]));

            paymentrec.setCustomerId(rs.getLong(PstPaymentRec.fieldNames[PstPaymentRec.FLD_CUSTOMER_ID]));

            paymentrec.setCashierId(rs.getLong(PstPaymentRec.fieldNames[PstPaymentRec.FLD_CASH_CASHIER_ID]));



            paymentrec.setReservationCommissionId(rs.getLong(PstPaymentRec.fieldNames[PstPaymentRec.FLD_RESERVATION_COMMISSION_ID]));

            paymentrec.setNumber(rs.getString(PstPaymentRec.fieldNames[PstPaymentRec.FLD_NUMBER]));

            paymentrec.setCounter(rs.getInt(PstPaymentRec.fieldNames[PstPaymentRec.FLD_COUNTER]));

            paymentrec.setPayOut(rs.getBoolean(PstPaymentRec.fieldNames[PstPaymentRec.FLD_PAY_OUT]));

            paymentrec.setStatus(rs.getInt(PstPaymentRec.fieldNames[PstPaymentRec.FLD_STATUS]));

            paymentrec.setDate(rs.getDate(PstPaymentRec.fieldNames[PstPaymentRec.FLD_DATE]));



            paymentrec.setPaymentSystemId(rs.getLong(PstPaymentRec.fieldNames[PstPaymentRec.FLD_PAYMENT_SYSTEM_ID]));

            paymentrec.setClearedToId(rs.getLong(PstPaymentRec.fieldNames[PstPaymentRec.FLD_CLEARED_TO_ID]));

            paymentrec.setDueDate(rs.getDate(PstPaymentRec.fieldNames[PstPaymentRec.FLD_DUE_DATE]));



        } catch (Exception e) {
        }

    }

    public static boolean checkOID(long paymentRecId) {

        DBResultSet dbrs = null;

        boolean result = false;

        try {

            String sql = "SELECT * FROM " + TBL_PAYMENT_REC + " WHERE " +
                    PstPaymentRec.fieldNames[PstPaymentRec.FLD_PAYMENT_REC_ID] + " = " + paymentRecId;



            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();



            while (rs.next()) {
                result = true;
            }

            rs.close();

        } catch (Exception e) {

            System.out.println("err : " + e.toString());

        } finally {

            DBResultSet.close(dbrs);

            return result;

        }

    }

    public static int getCount(String whereClause) {

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT COUNT(" + PstPaymentRec.fieldNames[PstPaymentRec.FLD_PAYMENT_REC_ID] + ") FROM " + TBL_PAYMENT_REC;

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

    /* This method used to find current data */
    public static int findLimitStart(long oid, int recordToGet, String whereClause) {

        String order = "";

        int size = getCount(whereClause);

        int start = 0;

        boolean found = false;

        for (int i = 0; (i < size) && !found; i = i + recordToGet) {

            Vector list = list(i, recordToGet, whereClause, order);

            start = i;

            if (list.size() > 0) {

                for (int ls = 0; ls < list.size(); ls++) {

                    PaymentRec paymentrec = (PaymentRec) list.get(ls);

                    if (oid == paymentrec.getOID()) {
                        found = true;
                    }

                }

            }

        }

        if ((start >= size) && (size > 0)) {
            start = start - recordToGet;
        }



        return start;

    }
    

}
