/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.hanoman.entity.masterdata;

import java.sql.*;
import com.dimata.util.lang.I_Language;
import com.dimata.common.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.Command;
import java.util.Vector;

public class PstConsumePackage extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_CONSUMEPACKAGE = "rsv_consume_package";
    public static final int FLD_RSV_CONSUME_PACKAGE = 0;
    public static final int FLD_CONSUME_PACKAGE_BILLING_ID = 1;
    public static final int FLD_CASH_BILL_DETAIL_ID = 2;

    public static String[] fieldNames = {
        "RSV_CONSUME_PACKAGE_ID",
        "CONSUME_PACKAGE_BILLING_ID",
        "CASH_BILL_DETAIL_ID"
    };

    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG
    };

    public PstConsumePackage() {
    }

    public PstConsumePackage(int i) throws DBException {
        super(new PstConsumePackage());
    }

    public PstConsumePackage(String sOid) throws DBException {
        super(new PstConsumePackage(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstConsumePackage(long lOid) throws DBException {
        super(new PstConsumePackage(0));
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
        return TBL_CONSUMEPACKAGE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstConsumePackage().getClass().getName();
    }

    public static ConsumePackage fetchExc(long oid) throws DBException {
        try {
            ConsumePackage entConsumePackage = new ConsumePackage();
            PstConsumePackage pstConsumePackage = new PstConsumePackage(oid);
            entConsumePackage.setOID(oid);
            entConsumePackage.setConsumePackageBillingId(pstConsumePackage.getLong(FLD_CONSUME_PACKAGE_BILLING_ID));
            entConsumePackage.setBillDetailId(pstConsumePackage.getLong(FLD_CASH_BILL_DETAIL_ID));
            return entConsumePackage;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstConsumePackage(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        ConsumePackage entConsumePackage = fetchExc(entity.getOID());
        entity = (Entity) entConsumePackage;
        return entConsumePackage.getOID();
    }

    public static synchronized long updateExc(ConsumePackage entConsumePackage) throws DBException {
        try {
            if (entConsumePackage.getOID() != 0) {
                PstConsumePackage pstConsumePackage = new PstConsumePackage(entConsumePackage.getOID());
                pstConsumePackage.setLong(FLD_CONSUME_PACKAGE_BILLING_ID, entConsumePackage.getConsumePackageBillingId());
                pstConsumePackage.setLong(FLD_CASH_BILL_DETAIL_ID, entConsumePackage.getBillDetailId());
                pstConsumePackage.update();
                return entConsumePackage.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstConsumePackage(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((ConsumePackage) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstConsumePackage pstConsumePackage = new PstConsumePackage(oid);
            pstConsumePackage.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstConsumePackage(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(ConsumePackage entConsumePackage) throws DBException {
        try {
            PstConsumePackage pstConsumePackage = new PstConsumePackage(0);
            pstConsumePackage.setLong(FLD_CONSUME_PACKAGE_BILLING_ID, entConsumePackage.getConsumePackageBillingId());
            pstConsumePackage.setLong(FLD_CASH_BILL_DETAIL_ID, entConsumePackage.getBillDetailId());
            pstConsumePackage.insert();
            entConsumePackage.setOID(pstConsumePackage.getlong(FLD_RSV_CONSUME_PACKAGE));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstConsumePackage(0), DBException.UNKNOWN);
        }
        return entConsumePackage.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((ConsumePackage) entity);
    }

    public static void resultToObject(ResultSet rs, ConsumePackage entConsumePackage) {
        try {
            entConsumePackage.setOID(rs.getLong(PstConsumePackage.fieldNames[PstConsumePackage.FLD_RSV_CONSUME_PACKAGE]));
            entConsumePackage.setConsumePackageBillingId(rs.getLong(PstConsumePackage.fieldNames[PstConsumePackage.FLD_CONSUME_PACKAGE_BILLING_ID]));
            entConsumePackage.setBillDetailId(rs.getLong(PstConsumePackage.fieldNames[PstConsumePackage.FLD_CASH_BILL_DETAIL_ID]));
        } catch (Exception e) {
        }
    }

    public static Vector listAll() {
        return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_CONSUMEPACKAGE;
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
                ConsumePackage entConsumePackage = new ConsumePackage();
                resultToObject(rs, entConsumePackage);
                lists.add(entConsumePackage);
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

    public static boolean checkOID(long entConsumePackageId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_CONSUMEPACKAGE + " WHERE "
                    + PstConsumePackage.fieldNames[PstConsumePackage.FLD_RSV_CONSUME_PACKAGE] + " = " + entConsumePackageId;
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
            String sql = "SELECT COUNT(" + PstConsumePackage.fieldNames[PstConsumePackage.FLD_RSV_CONSUME_PACKAGE] + ") FROM " + TBL_CONSUMEPACKAGE;
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

    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
        int size = getCount(whereClause);
        int start = 0;
        boolean found = false;
        for (int i = 0; (i < size) && !found; i = i + recordToGet) {
            Vector list = list(i, recordToGet, whereClause, orderClause);
            start = i;
            if (list.size() > 0) {
                for (int ls = 0; ls < list.size(); ls++) {
                    ConsumePackage entConsumePackage = (ConsumePackage) list.get(ls);
                    if (oid == entConsumePackage.getOID()) {
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

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
        int cmd = Command.LIST;
        int mdl = vectSize % recordToGet;
        vectSize = vectSize + (recordToGet - mdl);
        if (start == 0) {
            cmd = Command.FIRST;
        } else {
            if (start == (vectSize - recordToGet)) {
                cmd = Command.LAST;
            } else {
                start = start + recordToGet;
                if (start <= (vectSize - recordToGet)) {
                    cmd = Command.NEXT;
                    System.out.println("next.......................");
                } else {
                    start = start - recordToGet;
                    if (start > 0) {
                        cmd = Command.PREV;
                        System.out.println("prev.......................");
                    }
                }
            }
        }
        return cmd;
    }
}
