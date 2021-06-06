/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.entity.cashier.transaction;

import com.dimata.common.db.DBException;
import com.dimata.common.db.DBHandler;
import com.dimata.common.db.DBResultSet;
import com.dimata.common.db.I_DBInterface;
import com.dimata.common.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author Ardiadi
 */
public class PstQueensLocation extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_QUUENSLOCATION = "location";
   public static final int FLD_LOCATION_ID = 0;
   public static final int FLD_NAME = 1;
   public static final int FLD_CONTACT_ID = 2;
   public static final int FLD_DESCRIPTION = 3;
   public static final int FLD_CODE = 4;
   public static final int FLD_ADDRESS = 5;
   public static final int FLD_TELEPHONE = 6;
   public static final int FLD_FAX = 7;
   public static final int FLD_PERSON = 8;
   public static final int FLD_EMAIL = 9;
   public static final int FLD_TYPE = 10;
   public static final int FLD_WEBSITE = 11;
   public static final int FLD_LOC_INDEX = 12;
   public static final int FLD_PARENT_ID = 13;
   public static final int FLD_SERVICE_PERCENTAGE = 14;
   public static final int FLD_TAX_PERCENTAGE = 15;
   public static final int FLD_DEPARTMENT_ID = 16;
   public static final int FLD_USED_VALUE = 17;
   public static final int FLD_SERVICE_VALUE = 18;
   public static final int FLD_TAX_VALUE = 19;
   public static final int FLD_SERVICE_VALUE_USD = 20;
   public static final int FLD_TAX_VALUE_USD = 21;
   public static final int FLD_REPORT_GROUP = 22;
   public static final int FLD_TAX_SVC_DEFAULT = 23;
   public static final int FLD_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER = 24;
   public static final int FLD_COMPANY_ID = 25;
   public static final int FLD_PRICE_TYPE_ID = 26;
   public static final int FLD_STANDART_RATE_ID = 27;

   public static String[] fieldNames = {
      "LOCATION_ID",
      "NAME",
      "CONTACT_ID",
      "DESCRIPTION",
      "CODE",
      "ADDRESS",
      "TELEPHONE",
      "FAX",
      "PERSON",
      "EMAIL",
      "TYPE",
      "WEBSITE",
      "LOC_INDEX",
      "PARENT_ID",
      "SERVICE_PERCENTAGE",
      "TAX_PERCENTAGE",
      "DEPARTMENT_ID",
      "USED_VALUE",
      "SERVICE_VALUE",
      "TAX_VALUE",
      "SERVICE_VALUE_USD",
      "TAX_VALUE_USD",
      "REPORT_GROUP",
      "TAX_SVC_DEFAULT",
      "PERSENTASE_DISTRIBUTION_PURCHASE_ORDER",
      "COMPANY_ID",
      "PRICE_TYPE_ID",
      "STANDART_RATE_ID"
   };

   public static int[] fieldTypes = {
      TYPE_LONG+TYPE_PK+TYPE_ID,
      TYPE_STRING,
      TYPE_LONG,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_INT,
      TYPE_STRING,
      TYPE_INT,
      TYPE_LONG,
      TYPE_FLOAT,
      TYPE_FLOAT,
      TYPE_LONG,
      TYPE_FLOAT,
      TYPE_FLOAT,
      TYPE_FLOAT,
      TYPE_FLOAT,
      TYPE_FLOAT,
      TYPE_LONG,
      TYPE_INT,
      TYPE_FLOAT,
      TYPE_LONG,
      TYPE_LONG,
      TYPE_LONG
   };

   public PstQueensLocation() {
   }

   public PstQueensLocation(int i) throws DBException {
      super(new PstQueensLocation());
   }

   public PstQueensLocation(String sOid) throws DBException {
      super(new PstQueensLocation(0));
      if(!locate(sOid))
         throw new DBException(this, DBException.RECORD_NOT_FOUND);
      else
         return;
   }

   public PstQueensLocation(long lOid) throws DBException {
      super(new PstQueensLocation(0));
      String sOid = "0";
      try {
         sOid = String.valueOf(lOid);
      }catch(Exception e) {
         throw new DBException(this, DBException.RECORD_NOT_FOUND);
      }
      if(!locate(sOid))
         throw new DBException(this, DBException.RECORD_NOT_FOUND);
      else
         return;
   }

   public int getFieldSize() {
      return fieldNames.length;
   }

   public String getTableName() {
      return TBL_QUUENSLOCATION;
   }

   public String[] getFieldNames() {
      return fieldNames;
   }

   public int[] getFieldTypes() {
      return fieldTypes;
   }

   public String getPersistentName() {
      return new PstQueensLocation().getClass().getName();
   }

   public static QueensLocation fetchExc(long oid) throws DBException {
      try {
         QueensLocation entQuuensLocation = new QueensLocation();
         PstQueensLocation pstQuuensLocation = new PstQueensLocation(oid);
         entQuuensLocation.setOID(oid);
         entQuuensLocation.setName(pstQuuensLocation.getString(FLD_NAME));
         entQuuensLocation.setContactId(pstQuuensLocation.getlong(FLD_CONTACT_ID));
         entQuuensLocation.setDescription(pstQuuensLocation.getString(FLD_DESCRIPTION));
         entQuuensLocation.setCode(pstQuuensLocation.getString(FLD_CODE));
         entQuuensLocation.setAddress(pstQuuensLocation.getString(FLD_ADDRESS));
         entQuuensLocation.setTelephone(pstQuuensLocation.getString(FLD_TELEPHONE));
         entQuuensLocation.setFax(pstQuuensLocation.getString(FLD_FAX));
         entQuuensLocation.setPerson(pstQuuensLocation.getString(FLD_PERSON));
         entQuuensLocation.setEmail(pstQuuensLocation.getString(FLD_EMAIL));
         entQuuensLocation.setType(pstQuuensLocation.getInt(FLD_TYPE));
         entQuuensLocation.setWebsite(pstQuuensLocation.getString(FLD_WEBSITE));
         entQuuensLocation.setLocIndex(pstQuuensLocation.getInt(FLD_LOC_INDEX));
         entQuuensLocation.setParentId(pstQuuensLocation.getlong(FLD_PARENT_ID));
         entQuuensLocation.setServicePercentage(pstQuuensLocation.getdouble(FLD_SERVICE_PERCENTAGE));
         entQuuensLocation.setTaxPercentage(pstQuuensLocation.getdouble(FLD_TAX_PERCENTAGE));
         entQuuensLocation.setDepartementId(pstQuuensLocation.getlong(FLD_DEPARTMENT_ID));
         entQuuensLocation.setUsedValue(pstQuuensLocation.getdouble(FLD_USED_VALUE));
         entQuuensLocation.setServiceValue(pstQuuensLocation.getdouble(FLD_SERVICE_VALUE));
         entQuuensLocation.setTaxValue(pstQuuensLocation.getdouble(FLD_TAX_VALUE));
         entQuuensLocation.setServiceValueUsd(pstQuuensLocation.getdouble(FLD_SERVICE_VALUE_USD));
         entQuuensLocation.setTaxValueUsd(pstQuuensLocation.getdouble(FLD_TAX_VALUE_USD));
         entQuuensLocation.setReportGroup(pstQuuensLocation.getlong(FLD_REPORT_GROUP));
         entQuuensLocation.setTaxSvcDefault(pstQuuensLocation.getInt(FLD_TAX_SVC_DEFAULT));
         entQuuensLocation.setPctPurchaseOrder(pstQuuensLocation.getdouble(FLD_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER));
         entQuuensLocation.setCompanyId(pstQuuensLocation.getlong(FLD_COMPANY_ID));
         entQuuensLocation.setPriceTypeId(pstQuuensLocation.getlong(FLD_PRICE_TYPE_ID));
         entQuuensLocation.setStandartRateId(pstQuuensLocation.getlong(FLD_STANDART_RATE_ID));
         return entQuuensLocation;
      } catch (DBException dbe) {
         throw dbe;
      } catch (Exception e) {
         throw new DBException(new PstQueensLocation(0), DBException.UNKNOWN);
      }
   }

   public long fetchExc(Entity entity) throws Exception {
      QueensLocation entQuuensLocation = fetchExc(entity.getOID());
      entity = (Entity) entQuuensLocation;
      return entQuuensLocation.getOID();
   }

   public static synchronized long updateExc(QueensLocation entQueensLocation) throws DBException {
      try {
         if (entQueensLocation.getOID() != 0) {
            PstQueensLocation pstQueensLocation = new PstQueensLocation(entQueensLocation.getOID());
            pstQueensLocation.setString(FLD_NAME, entQueensLocation.getName());
            pstQueensLocation.setLong(FLD_CONTACT_ID, entQueensLocation.getContactId());
            pstQueensLocation.setString(FLD_DESCRIPTION, entQueensLocation.getDescription());
            pstQueensLocation.setString(FLD_CODE, entQueensLocation.getCode());
            pstQueensLocation.setString(FLD_ADDRESS, entQueensLocation.getAddress());
            pstQueensLocation.setString(FLD_TELEPHONE, entQueensLocation.getTelephone());
            pstQueensLocation.setString(FLD_FAX, entQueensLocation.getFax());
            pstQueensLocation.setString(FLD_PERSON, entQueensLocation.getPerson());
            pstQueensLocation.setString(FLD_EMAIL, entQueensLocation.getEmail());
            pstQueensLocation.setInt(FLD_TYPE, entQueensLocation.getType());
            pstQueensLocation.setString(FLD_WEBSITE, entQueensLocation.getWebsite());
            pstQueensLocation.setInt(FLD_LOC_INDEX, entQueensLocation.getLocIndex());
            pstQueensLocation.setLong(FLD_PARENT_ID, entQueensLocation.getParentId());
            pstQueensLocation.setDouble(FLD_SERVICE_PERCENTAGE, entQueensLocation.getServicePercentage());
            pstQueensLocation.setDouble(FLD_TAX_PERCENTAGE, entQueensLocation.getTaxPercentage());
            pstQueensLocation.setLong(FLD_DEPARTMENT_ID, entQueensLocation.getDepartementId());
            pstQueensLocation.setDouble(FLD_USED_VALUE, entQueensLocation.getUsedValue());
            pstQueensLocation.setDouble(FLD_SERVICE_VALUE, entQueensLocation.getServiceValue());
            pstQueensLocation.setDouble(FLD_TAX_VALUE, entQueensLocation.getTaxValue());
            pstQueensLocation.setDouble(FLD_SERVICE_VALUE_USD, entQueensLocation.getServiceValueUsd());
            pstQueensLocation.setDouble(FLD_TAX_VALUE_USD, entQueensLocation.getTaxValueUsd());
            pstQueensLocation.setLong(FLD_REPORT_GROUP, entQueensLocation.getReportGroup());
            pstQueensLocation.setInt(FLD_TAX_SVC_DEFAULT, entQueensLocation.getTaxSvcDefault());
            pstQueensLocation.setDouble(FLD_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER, entQueensLocation.getPctPurchaseOrder());
            pstQueensLocation.setLong(FLD_COMPANY_ID, entQueensLocation.getCompanyId());
            pstQueensLocation.setLong(FLD_PRICE_TYPE_ID, entQueensLocation.getPriceTypeId());
            pstQueensLocation.setLong(FLD_STANDART_RATE_ID, entQueensLocation.getStandartRateId());
            pstQueensLocation.update();
            return entQueensLocation.getOID();
         }
      } catch (DBException dbe) {
         throw dbe;
      } catch (Exception e) {
         throw new DBException(new PstQueensLocation(0), DBException.UNKNOWN);
      }
      return 0;
   }

   public long updateExc(Entity entity) throws Exception {
      return updateExc((QueensLocation) entity);
   }

   public static synchronized long deleteExc(long oid) throws DBException {
   try {
   PstQueensLocation pstQueensLocation = new PstQueensLocation(oid);
   pstQueensLocation.delete();
   } catch (DBException dbe) {
   throw dbe;
   } catch (Exception e) {
   throw new DBException(new PstQueensLocation(0), DBException.UNKNOWN);
   }
   return oid;
   }

   public long deleteExc(Entity entity) throws Exception {
   if (entity == null) {   throw new DBException(this, DBException.RECORD_NOT_FOUND);
   }   return deleteExc(entity.getOID());
   }

   public static synchronized long insertExc(QueensLocation entQueensLocation) throws DBException
   {
   try {
   PstQueensLocation pstQueensLocation = new PstQueensLocation(0);
            pstQueensLocation.setString(FLD_NAME, entQueensLocation.getName());
            pstQueensLocation.setLong(FLD_CONTACT_ID, entQueensLocation.getContactId());
            pstQueensLocation.setString(FLD_DESCRIPTION, entQueensLocation.getDescription());
            pstQueensLocation.setString(FLD_CODE, entQueensLocation.getCode());
            pstQueensLocation.setString(FLD_ADDRESS, entQueensLocation.getAddress());
            pstQueensLocation.setString(FLD_TELEPHONE, entQueensLocation.getTelephone());
            pstQueensLocation.setString(FLD_FAX, entQueensLocation.getFax());
            pstQueensLocation.setString(FLD_PERSON, entQueensLocation.getPerson());
            pstQueensLocation.setString(FLD_EMAIL, entQueensLocation.getEmail());
            pstQueensLocation.setInt(FLD_TYPE, entQueensLocation.getType());
            pstQueensLocation.setString(FLD_WEBSITE, entQueensLocation.getWebsite());
            pstQueensLocation.setInt(FLD_LOC_INDEX, entQueensLocation.getLocIndex());
            pstQueensLocation.setLong(FLD_PARENT_ID, entQueensLocation.getParentId());
            pstQueensLocation.setDouble(FLD_SERVICE_PERCENTAGE, entQueensLocation.getServicePercentage());
            pstQueensLocation.setDouble(FLD_TAX_PERCENTAGE, entQueensLocation.getTaxPercentage());
            pstQueensLocation.setLong(FLD_DEPARTMENT_ID, entQueensLocation.getDepartementId());
            pstQueensLocation.setDouble(FLD_USED_VALUE, entQueensLocation.getUsedValue());
            pstQueensLocation.setDouble(FLD_SERVICE_VALUE, entQueensLocation.getServiceValue());
            pstQueensLocation.setDouble(FLD_TAX_VALUE, entQueensLocation.getTaxValue());
            pstQueensLocation.setDouble(FLD_SERVICE_VALUE_USD, entQueensLocation.getServiceValueUsd());
            pstQueensLocation.setDouble(FLD_TAX_VALUE_USD, entQueensLocation.getTaxValue());
            pstQueensLocation.setLong(FLD_REPORT_GROUP, entQueensLocation.getReportGroup());
            pstQueensLocation.setInt(FLD_TAX_SVC_DEFAULT, entQueensLocation.getTaxSvcDefault());
            pstQueensLocation.setDouble(FLD_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER, entQueensLocation.getPctPurchaseOrder());
            pstQueensLocation.setLong(FLD_COMPANY_ID, entQueensLocation.getCompanyId());
            pstQueensLocation.setLong(FLD_PRICE_TYPE_ID, entQueensLocation.getPriceTypeId());
            pstQueensLocation.setLong(FLD_STANDART_RATE_ID, entQueensLocation.getStandartRateId());
   pstQueensLocation.insert();
   entQueensLocation.setOID(pstQueensLocation.getLong(FLD_LOCATION_ID));
   } catch (DBException dbe) {
   throw dbe;
   } catch (Exception e) {
   throw new DBException(new PstQueensLocation(0), DBException.UNKNOWN);
   }
   return entQueensLocation.getOID();
   }
   public long insertExc(Entity entity) throws Exception {
   return insertExc((QueensLocation) entity);
   }
   public static void resultToObject(ResultSet rs, QueensLocation entQueensLocation) {
   try {
	    entQueensLocation.setOID(rs.getLong(fieldNames[FLD_LOCATION_ID]));
            entQueensLocation.setName(rs.getString(fieldNames[FLD_NAME]));
            entQueensLocation.setContactId(rs.getLong(fieldNames[FLD_CONTACT_ID]));
            entQueensLocation.setDescription(rs.getString(fieldNames[FLD_DESCRIPTION]));
            entQueensLocation.setCode(rs.getString(fieldNames[FLD_CODE]));
            entQueensLocation.setAddress(rs.getString(fieldNames[FLD_ADDRESS]));
            entQueensLocation.setTelephone(rs.getString(fieldNames[FLD_TELEPHONE]));
            entQueensLocation.setFax(rs.getString(fieldNames[FLD_FAX]));
            entQueensLocation.setPerson(rs.getString(fieldNames[FLD_PERSON]));
            entQueensLocation.setEmail(rs.getString(fieldNames[FLD_EMAIL]));
            entQueensLocation.setType(rs.getInt(fieldNames[FLD_TYPE]));
            entQueensLocation.setWebsite(rs.getString(fieldNames[FLD_WEBSITE]));
            entQueensLocation.setLocIndex(rs.getInt(fieldNames[FLD_LOC_INDEX]));
            entQueensLocation.setParentId(rs.getLong(fieldNames[FLD_PARENT_ID]));
            entQueensLocation.setServicePercentage(rs.getDouble(fieldNames[FLD_SERVICE_PERCENTAGE]));
            entQueensLocation.setTaxPercentage(rs.getDouble(fieldNames[FLD_TAX_PERCENTAGE]));
            entQueensLocation.setDepartementId(rs.getLong(fieldNames[FLD_DEPARTMENT_ID]));
            entQueensLocation.setUsedValue(rs.getDouble(fieldNames[FLD_USED_VALUE]));
            entQueensLocation.setServiceValue(rs.getDouble(fieldNames[FLD_SERVICE_VALUE]));
            entQueensLocation.setTaxValue(rs.getDouble(fieldNames[FLD_TAX_VALUE]));
            entQueensLocation.setServiceValueUsd(rs.getDouble(fieldNames[FLD_SERVICE_VALUE_USD]));
            entQueensLocation.setTaxValueUsd(rs.getDouble(fieldNames[FLD_TAX_VALUE_USD]));
            entQueensLocation.setReportGroup(rs.getLong(fieldNames[FLD_REPORT_GROUP]));
            entQueensLocation.setTaxSvcDefault(rs.getInt(fieldNames[FLD_TAX_SVC_DEFAULT]));
            entQueensLocation.setPctPurchaseOrder(rs.getDouble(fieldNames[FLD_PERSENTASE_DISTRIBUTION_PURCHASE_ORDER]));
            entQueensLocation.setCompanyId(rs.getLong(fieldNames[FLD_COMPANY_ID]));
            entQueensLocation.setPriceTypeId(rs.getLong(fieldNames[FLD_PRICE_TYPE_ID]));
            entQueensLocation.setStandartRateId(rs.getLong(fieldNames[FLD_STANDART_RATE_ID]));
   } catch (Exception e) {
   }
   }
public static Vector listAll() {return list(0, 500, "", "");
}

public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
Vector lists = new Vector();
    DBResultSet dbrs = null;
try {
String sql = "SELECT * FROM " + TBL_QUUENSLOCATION;
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
QueensLocation entQuuensLocation = new QueensLocation();
resultToObject(rs, entQuuensLocation);
lists.add(entQuuensLocation);
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

public static boolean checkOID(long entQueensLocationId) {
DBResultSet dbrs = null;
boolean result = false;
try {
String sql = "SELECT * FROM " + TBL_QUUENSLOCATION + " WHERE "
+ PstQueensLocation.fieldNames[PstQueensLocation.FLD_LOCATION_ID] + " = " + entQueensLocationId;
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
            String sql = "SELECT COUNT(" + fieldNames[FLD_LOCATION_ID] + ") FROM " + TBL_QUUENSLOCATION;
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
QueensLocation entQueensLocation = (QueensLocation) list.get(ls);
if (oid == entQueensLocation.getOID()) {
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
