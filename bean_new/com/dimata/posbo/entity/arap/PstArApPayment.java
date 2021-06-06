// Generated by Together

package com.dimata.posbo.entity.arap;

import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.util.Formater;
import com.dimata.common.db.*;

import java.util.Vector;
import java.sql.ResultSet;

public class PstArApPayment extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc {

    public static final String TBL_ARAP_PAYMENT = "aiso_arap_payment";
    public static final int FLD_ARAP_PAYMENT_ID = 0;
    public static final int FLD_PAYMENT_NO = 1;
    public static final int FLD_PAYMENT_DATE = 2;
    public static final int FLD_ID_PERKIRAAN_PAYMENT = 3;
    public static final int FLD_ID_CURRENCY = 4;
    public static final int FLD_COUNTER = 5;
    public static final int FLD_RATE = 6;
    public static final int FLD_AMOUNT = 7;
    public static final int FLD_PAYMENT_STATUS = 8;
    public static final int FLD_ARAP_MAIN_ID = 9;
    public static final int FLD_CONTACT_ID = 10;
    public static final int FLD_RECEIVE_AKTIVA_ID = 11;
    public static final int FLD_SELLING_AKTIVA_ID = 12;
    public static final int FLD_LEFT_TO_PAY = 13;
    public static final int FLD_ARAP_TYPE = 14;

    public static String[] fieldNames = {
        "ARAP_PAYMENT_ID",
        "PAYMENT_NO",
        "PAYMENT_DATE",
        "ID_PERKIRAAN_PAYMENT",
        "ID_CURRENCY",
        "COUNTER",
        "RATE",
        "AMOUNT",
        "PAYMENT_STATUS",
        "ARAP_MAIN_ID",
        "CONTACT_ID",
        "RECEIVE_AKTIVA_ID",
        "SELLING_AKTIVA_ID",
        "LEFT_TO_PAY",
        "ARAP_TYPE"

    };

    public static int[] fieldTypes = {
        TYPE_PK + TYPE_ID + TYPE_LONG,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_INT
    };

    // document status untuk ArAp Main
    public static final int STATUS_OPEN = 0;
    public static final int STATUS_CLOSED = 1;

    public PstArApPayment() {
    }

    public PstArApPayment(int i) throws DBException {
        super(new PstArApPayment());
    }

    public PstArApPayment(String sOid) throws DBException {
        super(new PstArApPayment(0));
        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public PstArApPayment(long lOid) throws DBException {
        super(new PstArApPayment(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }

        if (!locate(sOid))
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        else
            return;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_ARAP_PAYMENT;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstArApPayment().getClass().getName();
    }

    public long fetchExc(Entity ent) throws DBException {
        ArApPayment arap = fetchExc(ent.getOID());
        ent = (Entity) arap;
        return arap.getOID();
    }

    public long insertExc(Entity ent) throws DBException {
        return insertExc((ArApPayment) ent);
    }

    public long updateExc(Entity ent) throws DBException {
        return updateExc((ArApPayment) ent);
    }

    public long deleteExc(Entity ent) throws DBException {
        if (ent == null) {
            throw  new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static ArApPayment fetchExc(long Oid) throws DBException {
        try {
            ArApPayment arap = new ArApPayment();
            PstArApPayment pstArApPayment = new PstArApPayment(Oid);
            arap.setOID(Oid);

            arap.setPaymentNo(pstArApPayment.getString(FLD_PAYMENT_NO));
            arap.setPaymentDate(pstArApPayment.getDate(FLD_PAYMENT_DATE));
            arap.setIdPerkiraanPayment(pstArApPayment.getlong(FLD_ID_PERKIRAAN_PAYMENT));
            arap.setIdCurrency(pstArApPayment.getlong(FLD_ID_CURRENCY));
            arap.setCounter(pstArApPayment.getInt(FLD_COUNTER));
            arap.setRate(pstArApPayment.getdouble(FLD_RATE));
            arap.setAmount(pstArApPayment.getdouble(FLD_AMOUNT));
            arap.setPaymentStatus(pstArApPayment.getInt(FLD_PAYMENT_STATUS));
            arap.setArapMainId(pstArApPayment.getlong(FLD_ARAP_MAIN_ID));
            arap.setContactId(pstArApPayment.getlong(FLD_CONTACT_ID));
            arap.setReceiveAktivaId(pstArApPayment.getlong(FLD_RECEIVE_AKTIVA_ID));
            arap.setSellingAktivaId(pstArApPayment.getlong(FLD_SELLING_AKTIVA_ID));
            arap.setLeftToPay(pstArApPayment.getdouble(FLD_LEFT_TO_PAY));
            arap.setArApType(pstArApPayment.getInt(FLD_ARAP_TYPE));

            return arap;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstArApPayment(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(ArApPayment arap) throws DBException {
        try {
            PstArApPayment pstArApPayment = new PstArApPayment(0);

            pstArApPayment.setString(FLD_PAYMENT_NO, arap.getPaymentNo());
            pstArApPayment.setDate(FLD_PAYMENT_DATE, arap.getPaymentDate());
            pstArApPayment.setLong(FLD_ID_PERKIRAAN_PAYMENT, arap.getIdPerkiraanPayment());
            pstArApPayment.setLong(FLD_ID_CURRENCY, arap.getIdCurrency());
            pstArApPayment.setInt(FLD_COUNTER, arap.getCounter());
            pstArApPayment.setDouble(FLD_RATE, arap.getRate());
            pstArApPayment.setDouble(FLD_AMOUNT, arap.getAmount());
            pstArApPayment.setInt(FLD_PAYMENT_STATUS, arap.getPaymentStatus());
            pstArApPayment.setLong(FLD_ARAP_MAIN_ID, arap.getArapMainId());
            pstArApPayment.setLong(FLD_CONTACT_ID, arap.getContactId());
            pstArApPayment.setLong(FLD_RECEIVE_AKTIVA_ID, arap.getReceiveAktivaId());
            pstArApPayment.setLong(FLD_SELLING_AKTIVA_ID, arap.getSellingAktivaId());
            pstArApPayment.setDouble(FLD_LEFT_TO_PAY, arap.getLeftToPay());
            pstArApPayment.setInt(FLD_ARAP_TYPE, arap.getArApType());

            pstArApPayment.insert();
            arap.setOID(pstArApPayment.getlong(FLD_ARAP_PAYMENT_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstArApPayment(0), DBException.UNKNOWN);
        }
        return arap.getOID();
    }

    public static long updateExc(ArApPayment arap) throws DBException {
        try {
            if (arap != null && arap.getOID() != 0) {
                PstArApPayment pstArApPayment = new PstArApPayment(arap.getOID());

                pstArApPayment.setString(FLD_PAYMENT_NO, arap.getPaymentNo());
                pstArApPayment.setDate(FLD_PAYMENT_DATE, arap.getPaymentDate());
                pstArApPayment.setLong(FLD_ID_PERKIRAAN_PAYMENT, arap.getIdPerkiraanPayment());
                pstArApPayment.setLong(FLD_ID_CURRENCY, arap.getIdCurrency());
                pstArApPayment.setInt(FLD_COUNTER, arap.getCounter());
                pstArApPayment.setDouble(FLD_RATE, arap.getRate());
                pstArApPayment.setDouble(FLD_AMOUNT, arap.getAmount());
                pstArApPayment.setInt(FLD_PAYMENT_STATUS, arap.getPaymentStatus());
                pstArApPayment.setLong(FLD_ARAP_MAIN_ID, arap.getArapMainId());
                pstArApPayment.setLong(FLD_CONTACT_ID, arap.getContactId());
                pstArApPayment.setLong(FLD_RECEIVE_AKTIVA_ID, arap.getReceiveAktivaId());
                pstArApPayment.setLong(FLD_SELLING_AKTIVA_ID, arap.getSellingAktivaId());
                pstArApPayment.setDouble(FLD_LEFT_TO_PAY, arap.getLeftToPay());
                pstArApPayment.setInt(FLD_ARAP_TYPE, arap.getArApType());

                pstArApPayment.update();
                return arap.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstArApPayment(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long Oid) throws DBException {
        try {
            PstArApPayment pstArApPayment = new PstArApPayment(Oid);
            pstArApPayment.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstArApPayment(0), DBException.UNKNOWN);
        }
        return Oid;
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_ARAP_PAYMENT + " ";
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;

            if (order != null && order.length() > 0)
                sql = sql + " ORDER BY " + order;

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0)
                        sql = sql + "";
                    else
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    break;

                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0)
                        sql = sql + "";
                    else
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;

                    break;

                case DBHandler.DBSVR_SYBASE:
                    break;

                case DBHandler.DBSVR_ORACLE:
                    break;

                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    break;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                ArApPayment arap = new ArApPayment();
                resultToObject(rs, arap);
                lists.add(arap);
            }
        } catch (Exception error) {
            System.out.println(".:: " + new PstArApPayment().getClass().getName() + ".list() : " + error.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return lists;
    }

    private static void resultToObject(ResultSet rs, ArApPayment arap) {
        try {
            arap.setOID(rs.getLong(PstArApPayment.fieldNames[PstArApPayment.FLD_ARAP_PAYMENT_ID]));
            arap.setPaymentNo(rs.getString(PstArApPayment.fieldNames[PstArApPayment.FLD_PAYMENT_NO]));
            arap.setPaymentDate(rs.getDate(PstArApPayment.fieldNames[PstArApPayment.FLD_PAYMENT_DATE]));
            arap.setIdPerkiraanPayment(rs.getLong(PstArApPayment.fieldNames[PstArApPayment.FLD_ID_PERKIRAAN_PAYMENT]));
            arap.setIdCurrency(rs.getLong(PstArApPayment.fieldNames[PstArApPayment.FLD_ID_CURRENCY]));
            arap.setCounter(rs.getInt(PstArApPayment.fieldNames[PstArApPayment.FLD_COUNTER]));
            arap.setRate(rs.getDouble(PstArApPayment.fieldNames[PstArApPayment.FLD_RATE]));
            arap.setAmount(rs.getDouble(PstArApPayment.fieldNames[PstArApPayment.FLD_AMOUNT]));
            arap.setPaymentStatus(rs.getInt(PstArApPayment.fieldNames[PstArApPayment.FLD_PAYMENT_STATUS]));
            arap.setArapMainId(rs.getLong(PstArApPayment.fieldNames[PstArApPayment.FLD_ARAP_MAIN_ID]));
            arap.setContactId(rs.getLong(PstArApPayment.fieldNames[PstArApPayment.FLD_CONTACT_ID]));
            arap.setReceiveAktivaId(rs.getLong(PstArApPayment.fieldNames[PstArApPayment.FLD_RECEIVE_AKTIVA_ID]));
            arap.setSellingAktivaId(rs.getLong(PstArApPayment.fieldNames[PstArApPayment.FLD_SELLING_AKTIVA_ID]));
            arap.setLeftToPay(rs.getLong(PstArApPayment.fieldNames[PstArApPayment.FLD_LEFT_TO_PAY]));
            arap.setArApType(rs.getInt(PstArApPayment.fieldNames[PstArApPayment.FLD_ARAP_TYPE]));

        } catch (Exception e) {
            System.out.println("resultToObject() " + e.toString());
        }
    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstArApPayment.fieldNames[PstArApPayment.FLD_ARAP_PAYMENT_ID] + ") " +
                    " FROM " + TBL_ARAP_PAYMENT;
            if (whereClause != null && whereClause.length() > 0)
                sql = sql + " WHERE " + whereClause;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            return count;
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }


    /**
     * untuk pembuatan nomor doc arap
     */
    public static ArApPayment createOrderNomor(ArApPayment arApMain) {
        String nmr = "";
        try {
            nmr = Formater.formatDate(arApMain.getPaymentDate(), "yyMM");
            String s = PstArApPayment.fieldNames[PstArApPayment.FLD_PAYMENT_DATE];
            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    s = "DATE_FORMAT(" + PstArApPayment.fieldNames[PstArApPayment.FLD_PAYMENT_DATE] + ", '%Y-%m')";
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    s = "TO_CHAR(" + PstArApPayment.fieldNames[PstArApPayment.FLD_PAYMENT_DATE] + ", 'YYYY-MM')";
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;
                default:
                    s = "DATE_FORMAT(" + PstArApPayment.fieldNames[PstArApPayment.FLD_PAYMENT_DATE] + ", '%Y-%m')";
            }
            String where = s + " = '" + Formater.formatDate(arApMain.getPaymentDate(), "yyyy-MM") + "'";
            int cnt = getCount(where) + 1;
            arApMain.setCounter(cnt);
            switch (String.valueOf(cnt).length()) {
                case 1:
                    nmr = nmr + "-00" + cnt;
                    break;
                case 2:
                    nmr = nmr + "-0" + cnt;
                    break;
                default:
                    nmr = nmr + "-" + cnt;
            }
            arApMain.setPaymentNo(nmr);
        } catch (Exception e) {
            System.out.println(e);
            arApMain.setPaymentNo("0000-000");
        }
        return arApMain;
    }

}
