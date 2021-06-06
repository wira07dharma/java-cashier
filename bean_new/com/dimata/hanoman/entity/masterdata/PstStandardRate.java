/* Created on 	:  [date] [time] AM/PM 
 * 
 * @author	 :
 * @version	 :
 */
/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/
package com.dimata.hanoman.entity.masterdata;

/* package java */
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/* package qdep */
import com.dimata.util.lang.I_Language;
import com.dimata.common.db.*;
import com.dimata.qdep.entity.*;

/* package hanoman */
import com.dimata.util.Formater;

public class PstStandardRate extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_STANDARD_RATE = "standard_rate";
    public static final int FLD_STANDARD_RATE_ID = 0;
    public static final int FLD_EMPLOYEE_ID = 1;
    public static final int FLD_COUNTRY_ID = 2;
    public static final int FLD_CREATE_DATE = 3;
    public static final int FLD_HISTORY_DATE = 4;
    public static final int FLD_RATE = 5;
    public static final int FLD_DESCRIPTION = 6;
    public static final int FLD_STATUS = 7;
    public static final int FLD_CURRENCY_TYPE_ID = 8; //updated by gwawan@dimata 20080508
    
    public static final String[] fieldNames = {
        "STANDARD_RATE_ID",
        "EMPLOYEE_ID",
        "COUNTRY_ID",
        "START_DATE",
        "END_DATE",
        "RATE",
        "DESCRIPTION",
        "STATUS",
        "CURRENCY_TYPE_ID"
    };
    
    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_FLOAT,
        TYPE_STRING,
        TYPE_INT,
        TYPE_LONG
    };
    
    public static final int STATUS_VALID = 1;
    public static final int STATUS_HISTORY = 0;

    public PstStandardRate() {

    }

    public PstStandardRate(int i) throws DBException {

        super(new PstStandardRate());

    }

    public PstStandardRate(String sOid) throws DBException {

        super(new PstStandardRate(0));

        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }

    }

    public PstStandardRate(long lOid) throws DBException {

        super(new PstStandardRate(0));

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

        return TBL_STANDARD_RATE;

    }

    public String[] getFieldNames() {

        return fieldNames;

    }

    public int[] getFieldTypes() {

        return fieldTypes;

    }

    public String getPersistentName() {

        return new PstStandardRate().getClass().getName();

    }

    public long fetchExc(Entity ent) throws Exception {

        StandardRate standardrate = fetchExc(ent.getOID());

        ent = (Entity) standardrate;

        return standardrate.getOID();

    }

    public long insertExc(Entity ent) throws Exception {

        return insertExc((StandardRate) ent);

    }

    public long updateExc(Entity ent) throws Exception {

        return updateExc((StandardRate) ent);

    }

    public long deleteExc(Entity ent) throws Exception {

        if (ent == null) {

            throw new DBException(this, DBException.RECORD_NOT_FOUND);

        }

        return deleteExc(ent.getOID());

    }

    public static StandardRate fetchExc(long oid) throws DBException {

        try {

            StandardRate standardrate = new StandardRate();

            PstStandardRate pstStandardRate = new PstStandardRate(oid);

            standardrate.setOID(oid);



            standardrate.setEmployeeId(pstStandardRate.getlong(FLD_EMPLOYEE_ID));

            standardrate.setCountryId(pstStandardRate.getlong(FLD_COUNTRY_ID));

            standardrate.setCreateDate(pstStandardRate.getDate(FLD_CREATE_DATE));

            standardrate.setHistoryDate(pstStandardRate.getDate(FLD_HISTORY_DATE));

            standardrate.setRate(pstStandardRate.getdouble(FLD_RATE));

            standardrate.setDescription(pstStandardRate.getString(FLD_DESCRIPTION));

            standardrate.setStatus(pstStandardRate.getInt(FLD_STATUS));
            
            standardrate.setCurrencyTypeId(pstStandardRate.getlong(FLD_CURRENCY_TYPE_ID));

            return standardrate;

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstStandardRate(0), DBException.UNKNOWN);

        }

    }

    public static long insertExc(StandardRate standardrate) throws DBException {

        try {

            PstStandardRate pstStandardRate = new PstStandardRate(0);



            pstStandardRate.setLong(FLD_EMPLOYEE_ID, standardrate.getEmployeeId());

            pstStandardRate.setLong(FLD_COUNTRY_ID, standardrate.getCountryId());

            pstStandardRate.setDate(FLD_CREATE_DATE, standardrate.getCreateDate());

            pstStandardRate.setDate(FLD_HISTORY_DATE, standardrate.getHistoryDate());

            pstStandardRate.setDouble(FLD_RATE, standardrate.getRate());

            pstStandardRate.setString(FLD_DESCRIPTION, standardrate.getDescription());

            pstStandardRate.setInt(FLD_STATUS, standardrate.getStatus());
            
            pstStandardRate.setLong(FLD_CURRENCY_TYPE_ID, standardrate.getCurrencyTypeId());



            pstStandardRate.insert();

            standardrate.setOID(pstStandardRate.getlong(FLD_STANDARD_RATE_ID));

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstStandardRate(0), DBException.UNKNOWN);

        }

        return standardrate.getOID();

    }

    public static long updateExc(StandardRate standardrate) throws DBException {

        try {

            if (standardrate.getOID() != 0) {

                PstStandardRate pstStandardRate = new PstStandardRate(standardrate.getOID());



                pstStandardRate.setLong(FLD_EMPLOYEE_ID, standardrate.getEmployeeId());

                pstStandardRate.setLong(FLD_COUNTRY_ID, standardrate.getCountryId());

                pstStandardRate.setDate(FLD_CREATE_DATE, standardrate.getCreateDate());

                pstStandardRate.setDate(FLD_HISTORY_DATE, standardrate.getHistoryDate());

                pstStandardRate.setDouble(FLD_RATE, standardrate.getRate());

                pstStandardRate.setString(FLD_DESCRIPTION, standardrate.getDescription());

                pstStandardRate.setInt(FLD_STATUS, standardrate.getStatus());
                
                pstStandardRate.setLong(FLD_CURRENCY_TYPE_ID, standardrate.getCurrencyTypeId());



                pstStandardRate.update();

                return standardrate.getOID();



            }

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstStandardRate(0), DBException.UNKNOWN);

        }

        return 0;

    }

    public static long deleteExc(long oid) throws DBException {

        try {

            PstStandardRate pstStandardRate = new PstStandardRate(oid);

            pstStandardRate.delete();

        } catch (DBException dbe) {

            throw dbe;

        } catch (Exception e) {

            throw new DBException(new PstStandardRate(0), DBException.UNKNOWN);

        }

        return oid;

    }

    public static Vector listAll() {

        return list(0, 500, "", "");

    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {

        Vector lists = new Vector();

        DBResultSet dbrs = null;

        try {

            String sql = "SELECT * FROM " + TBL_STANDARD_RATE;

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



            //System.out.println("sql query dari PstStandardRate.getList() ::::::::::::: ");

            //System.out.println(sql);



            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                StandardRate standardrate = new StandardRate();

                resultToObject(rs, standardrate);

                lists.add(standardrate);

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

    private static void resultToObject(ResultSet rs, StandardRate standardrate) {

        try {

            standardrate.setOID(rs.getLong(PstStandardRate.fieldNames[PstStandardRate.FLD_STANDARD_RATE_ID]));

            standardrate.setEmployeeId(rs.getLong(PstStandardRate.fieldNames[PstStandardRate.FLD_EMPLOYEE_ID]));

            standardrate.setCountryId(rs.getLong(PstStandardRate.fieldNames[PstStandardRate.FLD_COUNTRY_ID]));

            standardrate.setCreateDate(rs.getDate(PstStandardRate.fieldNames[PstStandardRate.FLD_CREATE_DATE]));

            standardrate.setHistoryDate(rs.getDate(PstStandardRate.fieldNames[PstStandardRate.FLD_HISTORY_DATE]));

            standardrate.setRate(rs.getDouble(PstStandardRate.fieldNames[PstStandardRate.FLD_RATE]));

            standardrate.setDescription(rs.getString(PstStandardRate.fieldNames[PstStandardRate.FLD_DESCRIPTION]));

            standardrate.setStatus(rs.getInt(PstStandardRate.fieldNames[PstStandardRate.FLD_STATUS]));
            
            standardrate.setCurrencyTypeId(rs.getLong(PstStandardRate.fieldNames[PstStandardRate.FLD_CURRENCY_TYPE_ID]));

        } catch (Exception e) {
        }

    }

    public static boolean checkOID(long standardRateId) {

        DBResultSet dbrs = null;

        boolean result = false;

        try {

            String sql = "SELECT * FROM " + TBL_STANDARD_RATE + " WHERE " +
                    PstStandardRate.fieldNames[PstStandardRate.FLD_STANDARD_RATE_ID] + " = " + standardRateId;



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

            String sql = "SELECT COUNT(" + PstStandardRate.fieldNames[PstStandardRate.FLD_STANDARD_RATE_ID] + ") FROM " + TBL_STANDARD_RATE;

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

                    StandardRate standardrate = (StandardRate) list.get(ls);

                    if (oid == standardrate.getOID()) {
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

    public static double getStandardRate() {

       // String where = fieldNames[FLD_STATUS] + "=" + STATUS_VALID;
        String where = fieldNames[FLD_STATUS] + "=" + STATUS_VALID + " AND "+fieldNames[FLD_CURRENCY_TYPE_ID]+"='2'" ;
        String order = fieldNames[FLD_CURRENCY_TYPE_ID] + " DESC ";

        Vector v = list(0, 0, where, order);

        double result = 0;

        if (v != null && v.size() > 0) {

            StandardRate sr = (StandardRate) v.get(0);

            result = sr.getRate();

        }

        return result;
    }

    public static double getStandardRate(Date startDate, Date endDate) {

        String where = "(TO_DAYS(" + PstStandardRate.fieldNames[PstStandardRate.FLD_CREATE_DATE] + ")>=TO_DAYS('" + Formater.formatDate(startDate, "yyyy-MM-dd") + "') " +
                " AND TO_DAYS(" + PstStandardRate.fieldNames[PstStandardRate.FLD_HISTORY_DATE] + ")<=TO_DAYS('" + Formater.formatDate(endDate, "yyyy-MM-dd") + "')) ";





        Vector v = list(0, 0, where, null);

        double result = 0;



        if (v != null && v.size() > 0) {

            StandardRate sr = (StandardRate) v.get(0);

            result = sr.getRate();

        }



        return result;



    }

    public static double getStandardRate(Date endDate) {

        String where = "(TO_DAYS(" + PstStandardRate.fieldNames[PstStandardRate.FLD_CREATE_DATE] + ")<=TO_DAYS('" + Formater.formatDate(endDate, "yyyy-MM-dd") + "')) ";

        Vector list = list(0, 0, where, fieldNames[FLD_HISTORY_DATE] + " ASC");

        double result = 0;

        if (list != null && list.size() > 0) {

            StandardRate sr = (StandardRate) list.get(0);

            result = sr.getRate();

        }

        return result;

    }

    public static void main(String args[]) {

        double rate = 0;

        try {

            rate = PstStandardRate.getStandardRate(com.dimata.util.DateCalc.getDate(""));

        } catch (Exception e) {

            e.printStackTrace();

        }

        //System.out.println("vector result sizenya ::: " + rate);

    }
}

