package com.dimata.hanoman.entity.masterdata;

/**
 *
 * @author Witar
 */
//import com.dimata.hanoman.entity.reservation.PstReservation;
import java.sql.*;
import com.dimata.util.lang.I_Language;
import com.dimata.common.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.util.Vector;

public class PstCustomePackageSchedule extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

    public static final String TBL_CUSTOME_PACKAGE_SCHEDULE = "rsv_custome_package_schedule";
    public static final int FLD_CUSTOM_SCHEDULE_ID = 0;
    public static final int FLD_CUSTOME_PACK_BILLING_ID = 1;
    public static final int FLD_ROOM_ID = 2;
    public static final int FLD_TABLE_ID = 3;
    public static final int FLD_START_DATE = 4;
    public static final int FLD_END_DATE = 5;
    //public static final int FLD_PIC = 6;
    public static final int FLD_STATUS = 6;

    public static String[] fieldNames = {
        "CUSTOM_SCHEDULE_ID",
        "CUSTOME_PACK_BILLING_ID",
        "ROOM_ID",
        "TABLE_ID",
        "START_DATE",
        "END_DATE",
        //"PIC",
        "STATUS"
    };

    public static int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_STRING,
        TYPE_STRING,
        //TYPE_LONG,
        TYPE_INT
    };

    public PstCustomePackageSchedule() {
    }

    public PstCustomePackageSchedule(int i) throws DBException {
        super(new PstCustomePackageSchedule());
    }

    public PstCustomePackageSchedule(String sOid) throws DBException {
        super(new PstCustomePackageSchedule(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstCustomePackageSchedule(long lOid) throws DBException {
        super(new PstCustomePackageSchedule(0));
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
        return TBL_CUSTOME_PACKAGE_SCHEDULE;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstCustomePackageSchedule().getClass().getName();
    }

    public static CustomePackageSchedule fetchExc(long oid) throws DBException {
        try {
            CustomePackageSchedule entCustomePackageSchedule = new CustomePackageSchedule();
            PstCustomePackageSchedule pstCustomePackageSchedule = new PstCustomePackageSchedule(oid);
            entCustomePackageSchedule.setOID(oid);
            entCustomePackageSchedule.setCustomPackBillingId(pstCustomePackageSchedule.getlong(FLD_CUSTOME_PACK_BILLING_ID));
            entCustomePackageSchedule.setRoomId(pstCustomePackageSchedule.getlong(FLD_ROOM_ID));
            entCustomePackageSchedule.setTableId(pstCustomePackageSchedule.getlong(FLD_TABLE_ID));
            entCustomePackageSchedule.setStartDate(pstCustomePackageSchedule.getString(FLD_START_DATE));
            entCustomePackageSchedule.setEndDate(pstCustomePackageSchedule.getString(FLD_END_DATE));
            //entCustomePackageSchedule.setPic(pstCustomePackageSchedule.getlong(FLD_PIC));
            try {
               entCustomePackageSchedule.setStatus(pstCustomePackageSchedule.getInt(FLD_STATUS)); 
            } catch (Exception e) {
            }
            
            return entCustomePackageSchedule;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCustomePackageSchedule(0), DBException.UNKNOWN);
        }
    }

    public long fetchExc(Entity entity) throws Exception {
        CustomePackageSchedule entCustomePackageSchedule = fetchExc(entity.getOID());
        entity = (Entity) entCustomePackageSchedule;
        return entCustomePackageSchedule.getOID();
    }

    public static synchronized long updateExc(CustomePackageSchedule entCustomePackageSchedule) throws DBException {
        try {
            if (entCustomePackageSchedule.getOID() != 0) {
                PstCustomePackageSchedule pstCustomePackageSchedule = new PstCustomePackageSchedule(entCustomePackageSchedule.getOID());
                pstCustomePackageSchedule.setLong(FLD_CUSTOME_PACK_BILLING_ID, entCustomePackageSchedule.getCustomPackBillingId());
                pstCustomePackageSchedule.setLong(FLD_ROOM_ID, entCustomePackageSchedule.getRoomId());
                pstCustomePackageSchedule.setLong(FLD_TABLE_ID, entCustomePackageSchedule.getTableId());
                pstCustomePackageSchedule.setString(FLD_START_DATE, entCustomePackageSchedule.getStartDate());
                pstCustomePackageSchedule.setString(FLD_END_DATE, entCustomePackageSchedule.getEndDate());
                //pstCustomePackageSchedule.setLong(FLD_PIC, entCustomePackageSchedule.getPic());
                pstCustomePackageSchedule.setInt(FLD_STATUS, entCustomePackageSchedule.getStatus());
                pstCustomePackageSchedule.update();
                return entCustomePackageSchedule.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCustomePackageSchedule(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public long updateExc(Entity entity) throws Exception {
        return updateExc((CustomePackageSchedule) entity);
    }

    public static synchronized long deleteExc(long oid) throws DBException {
        try {
            PstCustomePackageSchedule pstCustomePackageSchedule = new PstCustomePackageSchedule(oid);
            pstCustomePackageSchedule.delete();
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCustomePackageSchedule(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public long deleteExc(Entity entity) throws Exception {
        if (entity == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(entity.getOID());
    }

    public static synchronized long insertExc(CustomePackageSchedule entCustomePackageSchedule) throws DBException {
        try {
            PstCustomePackageSchedule pstCustomePackageSchedule = new PstCustomePackageSchedule(0);
            pstCustomePackageSchedule.setLong(FLD_CUSTOME_PACK_BILLING_ID, entCustomePackageSchedule.getCustomPackBillingId());
            pstCustomePackageSchedule.setLong(FLD_ROOM_ID, entCustomePackageSchedule.getRoomId());
            pstCustomePackageSchedule.setLong(FLD_TABLE_ID, entCustomePackageSchedule.getTableId());
            pstCustomePackageSchedule.setString(FLD_START_DATE, Formater.formatDate(entCustomePackageSchedule.getStartDate(), "dd/MM/yyyy hh:mm:ss", "yyyy/MM/dd hh:mm:ss"));
            pstCustomePackageSchedule.setString(FLD_END_DATE, Formater.formatDate(entCustomePackageSchedule.getEndDate(),"dd/MM/yyyyy hh:mm:ss","yyyy/MM/dd hh:mm:ss"));
            //pstCustomePackageSchedule.setLong(FLD_PIC, entCustomePackageSchedule.getPic());
            pstCustomePackageSchedule.setInt(FLD_STATUS, entCustomePackageSchedule.getStatus());
            pstCustomePackageSchedule.insert();
            entCustomePackageSchedule.setOID(pstCustomePackageSchedule.getlong(FLD_CUSTOM_SCHEDULE_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstCustomePackageSchedule(0), DBException.UNKNOWN);
        }
        return entCustomePackageSchedule.getOID();
    }

    public long insertExc(Entity entity) throws Exception {
        return insertExc((CustomePackageSchedule) entity);
    }

    public static void resultToObject(ResultSet rs, CustomePackageSchedule entCustomePackageSchedule) {
        try {
            entCustomePackageSchedule.setOID(rs.getLong(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_CUSTOM_SCHEDULE_ID]));
            entCustomePackageSchedule.setCustomPackBillingId(rs.getLong(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_CUSTOME_PACK_BILLING_ID]));
            entCustomePackageSchedule.setRoomId(rs.getLong(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_ROOM_ID]));
            entCustomePackageSchedule.setTableId(rs.getLong(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_TABLE_ID]));
            entCustomePackageSchedule.setStartDate(rs.getString(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_START_DATE]));
            entCustomePackageSchedule.setEndDate(rs.getString(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_END_DATE]));
            //entCustomePackageSchedule.setPic(rs.getLong(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_PIC]));
            entCustomePackageSchedule.setStatus(rs.getInt(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_STATUS]));
        } catch (Exception e) {
        }
    }
    
    public static void resultToObject2(ResultSet rs, CustomePackageSchedule entCustomePackageSchedule) {
        try {
            entCustomePackageSchedule.setOID(rs.getLong(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_CUSTOM_SCHEDULE_ID]));
            entCustomePackageSchedule.setCustomPackBillingId(rs.getLong(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_CUSTOME_PACK_BILLING_ID]));
            entCustomePackageSchedule.setRoomId(rs.getLong(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_ROOM_ID]));
            entCustomePackageSchedule.setTableId(rs.getLong(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_TABLE_ID]));
            entCustomePackageSchedule.setStartDate(rs.getString(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_START_DATE]));
            entCustomePackageSchedule.setEndDate(rs.getString(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_END_DATE]));
            //entCustomePackageSchedule.setPic(rs.getLong(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_PIC]));
            entCustomePackageSchedule.setStatus(rs.getInt(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_STATUS]));
            entCustomePackageSchedule.setLocationID(rs.getLong(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_BILLING_TYPE_ID]));
            //entCustomePackageSchedule.setReservationNum(rs.getString(PstReservation.fieldNames[PstReservation.FLD_RESERVATION_NUM]));
            //entCustomePackageSchedule.setSalutation(rs.getString(PstCustomer.fieldNames[PstCustomer.FLD_SALUTATION]));
            //entCustomePackageSchedule.setPersonName(rs.getString(PstCustomer.fieldNames[PstCustomer.FLD_FULL_NAME]));
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
            String sql = "SELECT * FROM " + TBL_CUSTOME_PACKAGE_SCHEDULE;
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
                CustomePackageSchedule entCustomePackageSchedule = new CustomePackageSchedule();
                resultToObject(rs, entCustomePackageSchedule);
                lists.add(entCustomePackageSchedule);
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
    
    public static Vector listJoinPackBilling(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        String sql="";
        try {
            /*String sql = ""
                + " SELECT "
                + " rps."+fieldNames[FLD_CUSTOM_SCHEDULE_ID]+", "
                + " rps."+fieldNames[FLD_CUSTOME_PACK_BILLING_ID]+", "
                + " rps."+fieldNames[FLD_ROOM_ID]+", "
                + " rps."+fieldNames[FLD_TABLE_ID]+", "
                + " rps."+fieldNames[FLD_START_DATE]+", "
                + " rps."+fieldNames[FLD_END_DATE]+", "
                + " rps."+fieldNames[FLD_PIC]+", "
                + " rps."+fieldNames[FLD_STATUS]+","
                + " rpb."+PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_BILLING_TYPE_ID]+", "
                + " rsv."+PstReservation.fieldNames[PstReservation.FLD_RESERVATION_NUM]+", "
                + " cl."+PstCustomer.fieldNames[PstCustomer.FLD_SALUTATION]+", "
                + " cl."+PstCustomer.fieldNames[PstCustomer.FLD_FULL_NAME]+""
                + " FROM "+TBL_CUSTOME_PACKAGE_SCHEDULE+" AS rps "
                + " INNER JOIN "+PstCustomePackBilling.TBL_HNM_PACK_BILLING+" AS rpb "
                + " ON rps."+fieldNames[FLD_CUSTOME_PACK_BILLING_ID]+" = rpb."+PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACK_BILLING_ID]+""
                + " INNER JOIN "+PstReservation.TBL_RESERVATION+" AS rsv "
                + " ON rsv."+PstReservation.fieldNames[PstReservation.FLD_RESERVATION_ID]+" = rpb."+PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_RESERVATION_ID]+" "
                + " INNER JOIN "+PstCustomer.TBL_CUSTOMER+" AS cl "
                + " ON rsv."+PstReservation.fieldNames[PstReservation.FLD_CUSTOMER_ID]+" = cl."+PstCustomer.fieldNames[PstCustomer.FLD_CUSTOMER_ID]+"";
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
            }*/
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                CustomePackageSchedule entCustomePackageSchedule = new CustomePackageSchedule();
                resultToObject2(rs, entCustomePackageSchedule);
                lists.add(entCustomePackageSchedule);
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

    public static boolean checkOID(long entCustomePackageScheduleId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_CUSTOME_PACKAGE_SCHEDULE + " WHERE "
                    + PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_CUSTOM_SCHEDULE_ID] + " = " + entCustomePackageScheduleId;
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
            String sql = "SELECT COUNT(" + PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_CUSTOM_SCHEDULE_ID] + ") FROM " + TBL_CUSTOME_PACKAGE_SCHEDULE;
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
    
    public static int getCountJoin(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(schedule." + PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_CUSTOM_SCHEDULE_ID] + ") "
                    + "FROM " + TBL_CUSTOME_PACKAGE_SCHEDULE+" AS schedule "
                    + "INNER JOIN "+PstCustomePackBilling.TBL_HNM_PACK_BILLING+" AS billing "
                    + "ON schedule."+fieldNames[FLD_CUSTOME_PACK_BILLING_ID]+"=billing."+PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACK_BILLING_ID];
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
                    CustomePackageSchedule entCustomePackageSchedule = (CustomePackageSchedule) list.get(ls);
                    if (oid == entCustomePackageSchedule.getOID()) {
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
