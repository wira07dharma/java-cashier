 

/* Created on 	:  [date] [time] AM/PM 

 * 

 * @author  	:  [authorName] 

 * @version  	:  [version] 

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



/* package qdep */

import com.dimata.util.lang.I_Language;

import com.dimata.common.db.*;

import com.dimata.qdep.entity.*;



/* package hanoman */

////import com.dimata.qdep.db.DBHandler;

////import com.dimata.qdep.db.DBException;

////import com.dimata.qdep.db.DBLogger;

import com.dimata.hanoman.entity.masterdata.*;

//import com.dimata.harisma.entity.masterdata.*;

import com.dimata.hanoman.entity.system.*;



//integrasi hanoman vs bo

import com.dimata.ObjLink.BOPos.OutletLink;

import com.dimata.interfaces.BOPos.I_Outlet;



public class PstBillingType extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language, I_Outlet {



	//public static final  String TBL_BILLING_TYPE = "BILLING_TYPE";

        public static final  String TBL_BILLING_TYPE = "location";



	public static final  int FLD_BILLING_TYPE_ID    = 0;

	public static final  int FLD_TYPE_NAME          = 1;

        public static final  int FLD_DESCRIPTION        = 2;

        public static final  int FLD_PARENT_ID          = 3;

	public static final  int FLD_TYPE_CODE          = 4;

        public static final  int FLD_ADDRESS            = 5;

        public static final  int FLD_TELEPHONE          = 6;

        public static final  int FLD_FAX                = 7;

        public static final  int FLD_PERSON             = 8;

        public static final  int FLD_CONTACT_ID         = 9;

        public static final  int FLD_EMAIL              = 10;

        public static final  int FLD_WEBSITE            = 11;

        public static final  int FLD_VENDOR_ID          = 12;

        public static final  int FLD_TYPE               = 13;        

	public static final  int FLD_SERVICE_PERCENTAGE = 14;

	public static final  int FLD_TAX_PERCENTAGE     = 15;

        public static final  int FLD_DEPARTMENT_ID      = 16;

        public static final  int FLD_USED_VALUE         = 17;

        public static final  int FLD_SERVICE_VALUE      = 18;

        public static final  int FLD_TAX_VALUE          = 19;

        public static final  int FLD_SERVICE_VALUE_USD  = 20;

        public static final  int FLD_TAX_VALUE_USD      = 21;	

        public static final  int FLD_REPORT_GROUP	= 22;
        
        public static final  int FLD_TAX_SVC_DEFAULT    = 23;
        
        //addd opie-eyek 20160104
        public static final  int FLD_COMPANY_ID    = 24;
        public static final  int FLD_PRICE_TYPE_ID    = 25;
        public static final  int FLD_STANDART_RATE_ID    = 26;
        public static final  int FLD_LOCATION_USED_TABLE    = 27;
        public static final int FLD_NPWPD=28;

	public static final  String[] fieldNames = {

		"LOCATION_ID",

		"NAME",

		"DESCRIPTION",

		"PARENT_ID",

		"CODE",

		"ADDRESS",

                "TELEPHONE",

                "FAX",

                "PERSON",     

                "CONTACT_ID",

                "EMAIL",

                "WEBSITE",

                "VENDOR_ID",

                "TYPE",

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
                
                "COMPANY_ID",
                
                "PRICE_TYPE_ID",
                
                "STANDART_RATE_ID",
                
                "LOCATION_USED_TABLE",
                "NPWPD"

	 }; 



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_STRING,

		TYPE_STRING,

                TYPE_LONG,

                TYPE_STRING,

                TYPE_STRING,

                TYPE_STRING,

                TYPE_STRING,

                TYPE_STRING,

                TYPE_LONG,

                TYPE_STRING,

                TYPE_STRING,

                TYPE_LONG,

                TYPE_INT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_LONG,

                TYPE_INT,                

                TYPE_FLOAT,

		TYPE_FLOAT,

                TYPE_FLOAT,

		TYPE_FLOAT,
                
                TYPE_INT,
                
                TYPE_INT,
                
                TYPE_LONG,
                
                TYPE_LONG,
                
                TYPE_LONG,
                
                TYPE_INT,
                TYPE_STRING
	 };





    public static final int REPORT_GROUP_DEFAULT 		= 0;

    public static final int REPORT_GROUP_FOOD_BEVERAGE 		= 1;

    public static final int REPORT_GROUP_OTHER_MISCELENOUSE     = 2;

    public static final int REPORT_GROUP_BOTH     = 3;

    public static final String[] reportGroupStr = {"Default/Shown On Report", "Food & Beverage", "Other Outlet ", " Not Shown On Report DRR"};


    public static final int TAX_SVC_DEFAULT_INCLUDE_NOT_CHANGEABLE =0;
    public static final int TAX_SVC_DEFAULT_NOT_INCLUDE_NOT_CHANGEABLE =1;
    public static final int TAX_SVC_DEFAULT_INCLUDE_CHANGEABLE =2;
    public static final int TAX_SVC_DEFAULT_NOT_INCLUDE_CHANGEABLE =3;
    public static final String[] taxSvcDefault={"Include - Not Changeable","Not Include - Not Changeable","Include - Changeable","Not Include - Changeable"};

    public static final int USED_PERCENTAGE      = 0;

    public static final int USED_VALUE  	 = 1;



    public static final String[] usedKey= {"Percentage","Amount"};



    public static final int FB_TYPE_NONE 		= 0;

    public static final int FB_TYPE_FOOD 		= 1;

    public static final int FB_TYPE_DRINK 		= 2;



    public static final String[] fbStr = {"None", "Food", "Beverage(Drink)"};



    public static final String FL_ROOM_CHARGE = "Room Charge";

    public static final String FL_PHONE       = "Phone";

    

    

    public static final int TYPE_WAREHOUSE = 0;

    public static final int TYPE_STORE = 1;



    public PstBillingType(){

	}



    public PstBillingType(int i) throws DBException { 

            super(new PstBillingType()); 

    }



    public PstBillingType(String sOid) throws DBException { 

            super(new PstBillingType(0)); 

            if(!locate(sOid)) 

                    throw new DBException(this,DBException.RECORD_NOT_FOUND); 

            else 

                    return; 

    }



    public PstBillingType(long lOid) throws DBException { 

            super(new PstBillingType(0)); 

            String sOid = "0"; 

            try { 

                    sOid = String.valueOf(lOid); 

            }catch(Exception e) { 

                    throw new DBException(this,DBException.RECORD_NOT_FOUND); 

            } 

            if(!locate(sOid)) 

                    throw new DBException(this,DBException.RECORD_NOT_FOUND); 

            else 

                    return; 

    } 



    public int getFieldSize(){ 

            return fieldNames.length; 

    }



    public String getTableName(){ 

            return TBL_BILLING_TYPE;

    }



    public String[] getFieldNames(){ 

            return fieldNames; 

    }



    public int[] getFieldTypes(){ 

            return fieldTypes; 

    }



    public String getPersistentName(){ 

            return new PstBillingType().getClass().getName(); 

    }



    public long fetchExc(Entity ent) throws Exception{ 

            BillingType billingtype = fetchExc(ent.getOID()); 

            ent = (Entity)billingtype; 

            return billingtype.getOID(); 

    }



    public long insertExc(Entity ent) throws Exception{ 

            return insertExc((BillingType) ent); 

    }



    public long updateExc(Entity ent) throws Exception{ 

            return updateExc((BillingType) ent); 

    }



    public long deleteExc(Entity ent) throws Exception{ 

            if(ent==null){ 

                    throw new DBException(this,DBException.RECORD_NOT_FOUND); 

            } 

            return deleteExc(ent.getOID()); 

    }



    public static BillingType fetchExc(long oid) throws DBException{ 

            try{ 

                    BillingType billingtype = new BillingType();

                    PstBillingType pstBillingType = new PstBillingType(oid); 

                    billingtype.setOID(oid);

                    

                    billingtype.setTypeName(pstBillingType.getString(FLD_TYPE_NAME));

                    billingtype.setDescription(pstBillingType.getString(FLD_DESCRIPTION));

                    billingtype.setParentId(pstBillingType.getlong(FLD_PARENT_ID));

                    billingtype.setTypeCode(pstBillingType.getString(FLD_TYPE_CODE));    

                    billingtype.setAddress(pstBillingType.getString(FLD_ADDRESS));

                    billingtype.setTelephone(pstBillingType.getString(FLD_TELEPHONE));

                    billingtype.setFax(pstBillingType.getString(FLD_FAX));

                    billingtype.setPerson(pstBillingType.getString(FLD_PERSON));

                    billingtype.setContactId(pstBillingType.getlong(FLD_CONTACT_ID));

                    billingtype.setEmail(pstBillingType.getString(FLD_EMAIL));

                    billingtype.setWebsite(pstBillingType.getString(FLD_WEBSITE));

                    billingtype.setVendorId(pstBillingType.getlong(FLD_VENDOR_ID));

                    billingtype.setType(pstBillingType.getInt(FLD_TYPE));

                    billingtype.setServicePercentage(pstBillingType.getdouble(FLD_SERVICE_PERCENTAGE));

                    billingtype.setTaxPercentage(pstBillingType.getdouble(FLD_TAX_PERCENTAGE));                    

                    billingtype.setDepartmentID(pstBillingType.getlong(FLD_DEPARTMENT_ID));

                    billingtype.setUsedValue(pstBillingType.getInt(FLD_USED_VALUE));

                    billingtype.setServiceValue(pstBillingType.getdouble(FLD_SERVICE_VALUE));

                    billingtype.setTaxValue(pstBillingType.getdouble(FLD_TAX_VALUE));

                    billingtype.setServiceValueUsd(pstBillingType.getdouble(FLD_SERVICE_VALUE_USD));

                    billingtype.setTaxValueUsd(pstBillingType.getdouble(FLD_TAX_VALUE_USD));

                    billingtype.setReportGroup(pstBillingType.getInt(FLD_REPORT_GROUP));
                    
                    billingtype.setTaxSvcDefault(pstBillingType.getInt(FLD_TAX_SVC_DEFAULT));
                    
                    //add opie-eyek 20150104
                    billingtype.setCompanyId(pstBillingType.getlong(FLD_COMPANY_ID));
                    
                    billingtype.setStandardRateId(pstBillingType.getlong(FLD_STANDART_RATE_ID));
                    
                    billingtype.setPriceTypeId(pstBillingType.getlong(FLD_PRICE_TYPE_ID));
                    
                    billingtype.setUseTable(pstBillingType.getInt(FLD_LOCATION_USED_TABLE));
                    
                    billingtype.setNpwpd(pstBillingType.getString(FLD_NPWPD));

                    return billingtype; 

            }catch(DBException dbe){ 

                    throw dbe; 

            }catch(Exception e){ 

                    throw new DBException(new PstBillingType(0),DBException.UNKNOWN); 

            } 

    }



	public static long insertExc(BillingType billingtype) throws DBException{ 

		try{ 

			PstBillingType pstBillingType = new PstBillingType(0);



			pstBillingType.setString(FLD_TYPE_NAME, billingtype.getTypeName());

                        pstBillingType.setString(FLD_DESCRIPTION, billingtype.getDescription());

                        pstBillingType.setLong(FLD_PARENT_ID, billingtype.getParentId());                        

                        pstBillingType.setString(FLD_TYPE_CODE, billingtype.getTypeCode());

                        pstBillingType.setString(FLD_ADDRESS, billingtype.getAddress());

                        pstBillingType.setString(FLD_TELEPHONE, billingtype.getTelephone());

                        pstBillingType.setString(FLD_FAX, billingtype.getFax());

                        pstBillingType.setString(FLD_PERSON, billingtype.getPerson());

                        pstBillingType.setLong(FLD_CONTACT_ID, billingtype.getContactId());

                        pstBillingType.setString(FLD_EMAIL, billingtype.getEmail());

                        pstBillingType.setString(FLD_WEBSITE, billingtype.getWebsite());

                        pstBillingType.setLong(FLD_VENDOR_ID, billingtype.getVendorId());

                        pstBillingType.setInt(FLD_TYPE, billingtype.getType());			

			pstBillingType.setDouble(FLD_SERVICE_PERCENTAGE, billingtype.getServicePercentage());

			pstBillingType.setDouble(FLD_TAX_PERCENTAGE, billingtype.getTaxPercentage());			

                        pstBillingType.setLong(FLD_DEPARTMENT_ID, billingtype.getDepartmentID());

                        pstBillingType.setDouble(FLD_USED_VALUE, billingtype.getUsedValue());

                        pstBillingType.setDouble(FLD_SERVICE_VALUE, billingtype.getServiceValue());

                        pstBillingType.setDouble(FLD_TAX_VALUE, billingtype.getTaxValue());

                        pstBillingType.setDouble(FLD_SERVICE_VALUE_USD, billingtype.getServiceValueUsd());

                        pstBillingType.setDouble(FLD_TAX_VALUE_USD, billingtype.getTaxValueUsd());

                        pstBillingType.setInt(FLD_REPORT_GROUP, billingtype.getReportGroup());
                        
                        pstBillingType.setInt(FLD_TAX_SVC_DEFAULT, billingtype.getTaxSvcDefault());
                        
                        //add opie-eyek 20150104
                        pstBillingType.setLong(FLD_COMPANY_ID, billingtype.getCompanyId());

                        pstBillingType.setLong(FLD_STANDART_RATE_ID, billingtype.getStandardRateId());

                        pstBillingType.setLong(FLD_PRICE_TYPE_ID, billingtype.getPriceTypeId());

                        pstBillingType.setInt(FLD_LOCATION_USED_TABLE, billingtype.getUseTable());
                        
                        pstBillingType.setString(FLD_NPWPD,billingtype.getNpwpd());

			pstBillingType.insert(); 

			billingtype.setOID(pstBillingType.getlong(FLD_BILLING_TYPE_ID));

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingType(0),DBException.UNKNOWN); 

		}

		return billingtype.getOID();

	}



	public static long updateExc(BillingType billingtype) throws DBException{ 

		try{ 

			if(billingtype.getOID() != 0){ 

				PstBillingType pstBillingType = new PstBillingType(billingtype.getOID());



				pstBillingType.setString(FLD_TYPE_NAME, billingtype.getTypeName());

                                pstBillingType.setString(FLD_DESCRIPTION, billingtype.getDescription());

                                pstBillingType.setLong(FLD_PARENT_ID, billingtype.getParentId());                        

                                pstBillingType.setString(FLD_TYPE_CODE, billingtype.getTypeCode());

                                pstBillingType.setString(FLD_ADDRESS, billingtype.getAddress());

                                pstBillingType.setString(FLD_TELEPHONE, billingtype.getTelephone());

                                pstBillingType.setString(FLD_FAX, billingtype.getFax());

                                pstBillingType.setString(FLD_PERSON, billingtype.getPerson());

                                pstBillingType.setLong(FLD_CONTACT_ID, billingtype.getContactId());

                                pstBillingType.setString(FLD_EMAIL, billingtype.getEmail());

                                pstBillingType.setString(FLD_WEBSITE, billingtype.getWebsite());

                                pstBillingType.setLong(FLD_VENDOR_ID, billingtype.getVendorId());

                                pstBillingType.setInt(FLD_TYPE, billingtype.getType());	

				pstBillingType.setDouble(FLD_SERVICE_PERCENTAGE, billingtype.getServicePercentage());

				pstBillingType.setDouble(FLD_TAX_PERCENTAGE, billingtype.getTaxPercentage());				

                                pstBillingType.setLong(FLD_DEPARTMENT_ID, billingtype.getDepartmentID());

                                pstBillingType.setInt(FLD_USED_VALUE, billingtype.getUsedValue());

                                pstBillingType.setDouble(FLD_SERVICE_VALUE, billingtype.getServiceValue());

                                pstBillingType.setDouble(FLD_TAX_VALUE, billingtype.getTaxValue());

                                pstBillingType.setDouble(FLD_SERVICE_VALUE_USD, billingtype.getServiceValueUsd());

                                pstBillingType.setDouble(FLD_TAX_VALUE_USD, billingtype.getTaxValueUsd());

                                pstBillingType.setInt(FLD_REPORT_GROUP, billingtype.getReportGroup());
                                
                                pstBillingType.setInt(FLD_TAX_SVC_DEFAULT, billingtype.getTaxSvcDefault());
                                
                                //add opie-eyek 20150104
                                pstBillingType.setLong(FLD_COMPANY_ID, billingtype.getCompanyId());

                                pstBillingType.setLong(FLD_STANDART_RATE_ID, billingtype.getStandardRateId());

                                pstBillingType.setLong(FLD_PRICE_TYPE_ID, billingtype.getPriceTypeId());

                                pstBillingType.setInt(FLD_LOCATION_USED_TABLE, billingtype.getUseTable());
                                
                                pstBillingType.setString(FLD_NPWPD, billingtype.getNpwpd());

				pstBillingType.update(); 
                                
				return billingtype.getOID();
			}
		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 
			throw new DBException(new PstBillingType(0),DBException.UNKNOWN); 
		}

		return 0;

	}



	public static long deleteExc(long oid) throws DBException{ 

		try{ 

			PstBillingType pstBillingType = new PstBillingType(oid);

			pstBillingType.delete();

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingType(0),DBException.UNKNOWN); 

		}

		return oid;

	}



	public static Vector listAll(){ 

		return list(0, 500, "",""); 

	}



	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){

		Vector lists = new Vector(); 

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT * FROM " + TBL_BILLING_TYPE; 

			if(whereClause != null && whereClause.length() > 0)

				sql = sql + " WHERE " + whereClause;

			if(order != null && order.length() > 0)

				sql = sql + " ORDER BY " + order;

			if(limitStart == 0 && recordToGet == 0)

				sql = sql + "";

			else

				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

                        

                        System.out.println("ini sql dari billing typenya :");

                        System.out.println(sql);

                        System.out.println("-----------");

                        

			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) {

				BillingType billingtype = new BillingType();

				resultToObject(rs, billingtype);

				lists.add(billingtype);

			}

			rs.close();

			return lists;



		}catch(Exception e) {

			System.out.println(e);

		}finally {

			DBResultSet.close(dbrs);

		}

			return new Vector();

	}



	public static void resultToObject(ResultSet rs, BillingType billingtype){

		try{

			billingtype.setOID(rs.getLong(PstBillingType.fieldNames[PstBillingType.FLD_BILLING_TYPE_ID]));

                        billingtype.setTypeName(rs.getString(PstBillingType.fieldNames[PstBillingType.FLD_TYPE_NAME]));

                        billingtype.setDescription(rs.getString(PstBillingType.fieldNames[PstBillingType.FLD_DESCRIPTION]));

                        billingtype.setParentId(rs.getLong(PstBillingType.fieldNames[PstBillingType.FLD_PARENT_ID]));

                        billingtype.setTypeCode(rs.getString(PstBillingType.fieldNames[PstBillingType.FLD_TYPE_CODE]));

                        billingtype.setAddress(rs.getString(PstBillingType.fieldNames[PstBillingType.FLD_ADDRESS]));

			billingtype.setTelephone(rs.getString(PstBillingType.fieldNames[PstBillingType.FLD_TELEPHONE]));

                        billingtype.setFax(rs.getString(PstBillingType.fieldNames[PstBillingType.FLD_FAX]));

                        billingtype.setPerson(rs.getString(PstBillingType.fieldNames[PstBillingType.FLD_PERSON]));

                        billingtype.setContactId(rs.getLong(PstBillingType.fieldNames[PstBillingType.FLD_CONTACT_ID]));

                        billingtype.setEmail(rs.getString(PstBillingType.fieldNames[PstBillingType.FLD_EMAIL]));

                        billingtype.setWebsite(rs.getString(PstBillingType.fieldNames[PstBillingType.FLD_WEBSITE]));

                        billingtype.setVendorId(rs.getLong(PstBillingType.fieldNames[PstBillingType.FLD_VENDOR_ID]));

                        billingtype.setType(rs.getInt(PstBillingType.fieldNames[PstBillingType.FLD_TYPE]));			

			billingtype.setServicePercentage(rs.getDouble(PstBillingType.fieldNames[PstBillingType.FLD_SERVICE_PERCENTAGE]));

			billingtype.setTaxPercentage(rs.getDouble(PstBillingType.fieldNames[PstBillingType.FLD_TAX_PERCENTAGE]));			

                        billingtype.setDepartmentID(rs.getLong(PstBillingType.fieldNames[PstBillingType.FLD_DEPARTMENT_ID]));

                        billingtype.setUsedValue(rs.getInt(PstBillingType.fieldNames[PstBillingType.FLD_USED_VALUE]));

                        billingtype.setServiceValue(rs.getDouble(PstBillingType.fieldNames[PstBillingType.FLD_SERVICE_VALUE]));

                        billingtype.setTaxValue(rs.getDouble(PstBillingType.fieldNames[PstBillingType.FLD_TAX_VALUE]));

                        billingtype.setServiceValueUsd(rs.getDouble(PstBillingType.fieldNames[PstBillingType.FLD_SERVICE_VALUE_USD]));

                        billingtype.setTaxValueUsd(rs.getDouble(PstBillingType.fieldNames[PstBillingType.FLD_TAX_VALUE_USD]));

                        billingtype.setReportGroup(rs.getInt(PstBillingType.fieldNames[PstBillingType.FLD_REPORT_GROUP]));
                        
                        billingtype.setTaxSvcDefault(rs.getInt(PstBillingType.fieldNames[PstBillingType.FLD_TAX_SVC_DEFAULT]));
                        
                        //update opie-eyek 20160104
                        billingtype.setCompanyId(rs.getLong(PstBillingType.fieldNames[PstBillingType.FLD_COMPANY_ID]));
                        billingtype.setStandardRateId(rs.getLong(PstBillingType.fieldNames[PstBillingType.FLD_STANDART_RATE_ID]));
                        billingtype.setPriceTypeId(rs.getLong(PstBillingType.fieldNames[PstBillingType.FLD_PRICE_TYPE_ID]));
                        billingtype.setUseTable(rs.getInt(PstBillingType.fieldNames[PstBillingType.FLD_LOCATION_USED_TABLE]));
                        billingtype.setNpwpd(rs.getString(PstBillingType.fieldNames[PstBillingType.FLD_NPWPD]));

		}catch(Exception e){ }

	}



	public static boolean checkOID(long billingTypeId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_BILLING_TYPE + " WHERE " + 

			PstBillingType.fieldNames[PstBillingType.FLD_BILLING_TYPE_ID] + " = " + billingTypeId;



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();



			while(rs.next()) { result = true; }

			rs.close();

		}catch(Exception e){

			System.out.println("err : "+e.toString());

		}finally{

			DBResultSet.close(dbrs);

		}

                

                return result;

	}



	public static int getCount(String whereClause){

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT COUNT("+ PstBillingType.fieldNames[PstBillingType.FLD_BILLING_TYPE_ID] + ") FROM " + TBL_BILLING_TYPE;

			if(whereClause != null && whereClause.length() > 0)

				sql = sql + " WHERE " + whereClause;



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();



			int count = 0;

			while(rs.next()) { count = rs.getInt(1); }



			rs.close();

			return count;

		}catch(Exception e) {

			return 0;

		}finally {

			DBResultSet.close(dbrs);

		}

	}





    //INTEGRASI HANOMAN VS BO



    public long insertOutlet(OutletLink outletLink){

        

        System.out.println("inserting billing type on hanoman from pos  ");

        

        BillingType bt = new BillingType();

        bt.setTypeCode(outletLink.getCode());

        bt.setTypeName(outletLink.getName());

        bt.setDescription(outletLink.getDescription());

        bt.setTaxPercentage(outletLink.getTaxPercent());

        bt.setServicePercentage(outletLink.getSvcPercent());

        bt.setUsedValue(PstBillingType.USED_PERCENTAGE);

        bt.setReportGroup(PstBillingType.REPORT_GROUP_DEFAULT);
        bt.setTaxSvcDefault(PstBillingType.REPORT_GROUP_DEFAULT);


        String strInit = PstSystemProperty.getValueByName("FO_OID");

		long FO = 0;

		try{

			FO = Long.parseLong(strInit);

		}

		catch(Exception e){

			FO = 0;

			System.out.println("*** java init FO_OID not set on system property : "+e.toString());

		}



        bt.setDepartmentID(FO);



        try{

            long oid =  PstBillingType.insertExc(bt);

            

            System.out.println("success inserting billing type on hanoman from pos : "+oid);

            

            return synchronizeOID(oid, outletLink.getOutletId());

        }

        catch(Exception e){

            System.out.println("Exception on hanoman insertOutlet e : "+e.toString());

        }



        return 0;



    }



    public long updateOutlet(OutletLink outletLink){

        

        System.out.println(" ");

        System.out.println("updating --- billing type on hanoman");

        System.out.println("updating --- billing type on hanoman");



        BillingType bt = new BillingType();

        if(outletLink.getOutletId()!=0){

            try{

            	bt = PstBillingType.fetchExc(outletLink.getOutletId());

            }

            catch(Exception e){

            }

        }

        else{

            return 0;

        }



        bt.setTypeCode(outletLink.getCode());

        bt.setTypeName(outletLink.getName());

        bt.setDescription(outletLink.getDescription());

        bt.setTaxPercentage(outletLink.getTaxPercent());

        bt.setServicePercentage(outletLink.getSvcPercent());

        

        System.out.println("updating --- > selesai setup xxx ");



        try{

            return PstBillingType.updateExc(bt);

        }

        catch(Exception e){

            System.out.println("exeception xx : "+e.toString());

        }



        System.out.println("updating --- > terjadi error ");

        

        return 0;



    }



    public long deleteOutlet(OutletLink outletLink){

        String where  = PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ID] +"="+outletLink.getOutletId();

        Vector vct = PstItemOutlet.list(0,0, where, null);

        if(vct==null || vct.size()<1){

            try{

                PstBillingType.deleteExc(outletLink.getOutletId());

                return outletLink.getOutletId();

            }

            catch(Exception e){

            }

        }



        return 0;

    }



    public long synchronizeOID(long oldOID, long newOID){



        String sql = "UPDATE "+PstBillingType.TBL_BILLING_TYPE+

            " SET "+ PstBillingType.fieldNames[PstBillingType.FLD_BILLING_TYPE_ID]+"="+newOID+

            " WHERE "+PstBillingType.fieldNames[PstBillingType.FLD_BILLING_TYPE_ID]+"="+oldOID;



        try{

       	 	DBHandler.execUpdate(sql);

			return newOID;

        }

        catch(Exception e){

            return 0;

        }



    }

    

    

    public static boolean isCodeExist(long oid, String code){

        DBResultSet dbrs = null;

        boolean result = false;

        try{

            String sql = "SELECT COUNT("+fieldNames[FLD_BILLING_TYPE_ID]+") "+

                " FROM "+TBL_BILLING_TYPE+

                " WHERE "+fieldNames[FLD_TYPE_CODE]+"='"+code+"'"+

                " AND "+fieldNames[FLD_BILLING_TYPE_ID]+"!="+oid;

            

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            int count = 0;

            while(rs.next()){

                count = rs.getInt(1);

            }

            if(count>0){

                result = true;

            }

        }

        catch(Exception e){

            System.out.println("exception e : "+e.toString());

        }

        finally{

            DBResultSet.close(dbrs);

        }

        

        return result;

    }

    

    public static boolean isNameExist(long oid, String name){

        DBResultSet dbrs = null;

        boolean result = false;

        try{

            String sql = "SELECT COUNT("+fieldNames[FLD_BILLING_TYPE_ID]+") "+

                " FROM "+TBL_BILLING_TYPE+

                " WHERE "+fieldNames[FLD_TYPE_NAME]+"='"+name+"'"+

                " AND "+fieldNames[FLD_BILLING_TYPE_ID]+"!="+oid;

            

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            int count = 0;

            while(rs.next()){

                count = rs.getInt(1);

            }

            if(count>0){

                result = true;

            }

        }

        catch(Exception e){

            System.out.println("exception e : "+e.toString());

        }

        finally{

            DBResultSet.close(dbrs);

        }

        

        return result;

    }



    //--------- END INTGRASI ----------



}

