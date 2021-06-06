

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



package com.dimata.hanoman.entity.cashier; 



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

//import com.dimata.qdep.db.DBHandler;

//import com.dimata.qdep.db.DBException;

//import com.dimata.qdep.db.DBLogger;

import com.dimata.hanoman.entity.cashier.*; 



public class PstOutlet extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 



	//public static final  String TBL_OUTLET = "OUTLET";

    public static final  String TBL_OUTLET = "outlet";



	public static final  int FLD_OUTLET_ID = 0;

	public static final  int FLD_STATUS = 1;

	public static final  int FLD_TYPE = 2;

	public static final  int FLD_OPERATOR_ID = 3;

	public static final  int FLD_BILLING_TYPE_ID = 4;

	public static final  int FLD_DEPARTMENT_ID = 5;

	public static final  int FLD_ADD_ORDER = 6;

	public static final  int FLD_START_DATE_TIME = 7;

	public static final  int FLD_END_DATE_TIME = 8;

	public static final  int FLD_OPENING_BALANCE_RP = 9;

	public static final  int FLD_OPENING_BALANCE_USD = 10;

	public static final  int FLD_CFM_AMOUNT_CACH_RP = 11;

	public static final  int FLD_CFM_AMOUNT_CASH_USD = 12;

	public static final  int FLD_NOTE = 13;



	public static final  String[] fieldNames = {

		"OUTLET_ID",

		"STATUS",

		"TYPE",

		"OPERATOR_ID",

		"BILLING_TYPE_ID",

		"DEPARTMENT_ID",

		"ADD_ORDER",

		"START_DATE_TIME",

		"END_DATE_TIME",

		"OPENING_BALANCE_RP",

		"OPENING_BALANCE_USD",

		"CFM_AMOUNT_CACH_RP",

		"CFM_AMOUNT_CASH_USD",

		"NOTE"

	 }; 



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_INT,

		TYPE_INT,

		TYPE_LONG,

		TYPE_LONG,

		TYPE_LONG,

		TYPE_BOOL,

		TYPE_DATE,

		TYPE_DATE,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_STRING

	 };



    public static final int STATUS_CASHIER_OPEN 	= 0;

    public static final int STATUS_CASHIER_CLOSED 	= 1;



    public static final String[] statusStr = {"Open", "Closed"};



	public PstOutlet(){

	}



	public PstOutlet(int i) throws DBException { 

		super(new PstOutlet()); 

	}



	public PstOutlet(String sOid) throws DBException { 

		super(new PstOutlet(0)); 

		if(!locate(sOid)) 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		else 

			return; 

	}



	public PstOutlet(long lOid) throws DBException { 

		super(new PstOutlet(0)); 

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

		return TBL_OUTLET;

	}



	public String[] getFieldNames(){ 

		return fieldNames; 

	}



	public int[] getFieldTypes(){ 

		return fieldTypes; 

	}



	public String getPersistentName(){ 

		return new PstOutlet().getClass().getName(); 

	}



	public long fetchExc(Entity ent) throws Exception{ 

		Outlet outlet = fetchExc(ent.getOID()); 

		ent = (Entity)outlet; 

		return outlet.getOID(); 

	}



	public long insertExc(Entity ent) throws Exception{ 

		return insertExc((Outlet) ent); 

	}



	public long updateExc(Entity ent) throws Exception{ 

		return updateExc((Outlet) ent); 

	}



	public long deleteExc(Entity ent) throws Exception{ 

		if(ent==null){ 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		} 

		return deleteExc(ent.getOID()); 

	}



	public static Outlet fetchExc(long oid) throws DBException{ 

		try{ 

			Outlet outlet = new Outlet();

			PstOutlet pstOutlet = new PstOutlet(oid); 

			outlet.setOID(oid);



			outlet.setStatus(pstOutlet.getInt(FLD_STATUS));

		//	outlet.setType(pstOutlet.getInt(FLD_TYPE));

			outlet.setOperatorId(pstOutlet.getlong(FLD_OPERATOR_ID));

			outlet.setBillingTypeId(pstOutlet.getlong(FLD_BILLING_TYPE_ID));

			outlet.setDepartmentId(pstOutlet.getlong(FLD_DEPARTMENT_ID));

			outlet.setAddOrder(pstOutlet.getboolean(FLD_ADD_ORDER));

			outlet.setStartDateTime(pstOutlet.getDate(FLD_START_DATE_TIME));

			outlet.setEndDateTime(pstOutlet.getDate(FLD_END_DATE_TIME));

			outlet.setOpeningBalanceRp(pstOutlet.getdouble(FLD_OPENING_BALANCE_RP));

			outlet.setOpeningBalanceUsd(pstOutlet.getdouble(FLD_OPENING_BALANCE_USD));

			outlet.setCfmAmountCachRp(pstOutlet.getdouble(FLD_CFM_AMOUNT_CACH_RP));

			outlet.setCfmAmountCashUsd(pstOutlet.getdouble(FLD_CFM_AMOUNT_CASH_USD));

			outlet.setNote(pstOutlet.getString(FLD_NOTE));



			return outlet; 

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstOutlet(0),DBException.UNKNOWN); 

		} 

	}



	public static long insertExc(Outlet outlet) throws DBException{ 

		try{ 

			PstOutlet pstOutlet = new PstOutlet(0);



			pstOutlet.setInt(FLD_STATUS, outlet.getStatus());

		//	pstOutlet.setInt(FLD_TYPE, outlet.getType());

			pstOutlet.setLong(FLD_OPERATOR_ID, outlet.getOperatorId());

			pstOutlet.setLong(FLD_BILLING_TYPE_ID, outlet.getBillingTypeId());

			pstOutlet.setLong(FLD_DEPARTMENT_ID, outlet.getDepartmentId());

			pstOutlet.setboolean(FLD_ADD_ORDER, outlet.getAddOrder());

			pstOutlet.setDate(FLD_START_DATE_TIME, outlet.getStartDateTime());

			pstOutlet.setDate(FLD_END_DATE_TIME, outlet.getEndDateTime());

			pstOutlet.setDouble(FLD_OPENING_BALANCE_RP, outlet.getOpeningBalanceRp());

			pstOutlet.setDouble(FLD_OPENING_BALANCE_USD, outlet.getOpeningBalanceUsd());

			pstOutlet.setDouble(FLD_CFM_AMOUNT_CACH_RP, outlet.getCfmAmountCachRp());

			pstOutlet.setDouble(FLD_CFM_AMOUNT_CASH_USD, outlet.getCfmAmountCashUsd());

			pstOutlet.setString(FLD_NOTE, outlet.getNote());



			pstOutlet.insert(); 

			outlet.setOID(pstOutlet.getlong(FLD_OUTLET_ID));

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstOutlet(0),DBException.UNKNOWN); 

		}

		return outlet.getOID();

	}



	public static long updateExc(Outlet outlet) throws DBException{ 

		try{ 

			if(outlet.getOID() != 0){ 

				PstOutlet pstOutlet = new PstOutlet(outlet.getOID());



				pstOutlet.setInt(FLD_STATUS, outlet.getStatus());

			//	pstOutlet.setInt(FLD_TYPE, outlet.getType());

				pstOutlet.setLong(FLD_OPERATOR_ID, outlet.getOperatorId());

				pstOutlet.setLong(FLD_BILLING_TYPE_ID, outlet.getBillingTypeId());

				pstOutlet.setLong(FLD_DEPARTMENT_ID, outlet.getDepartmentId());

				pstOutlet.setboolean(FLD_ADD_ORDER, outlet.getAddOrder());

				pstOutlet.setDate(FLD_START_DATE_TIME, outlet.getStartDateTime());

				pstOutlet.setDate(FLD_END_DATE_TIME, outlet.getEndDateTime());

				pstOutlet.setDouble(FLD_OPENING_BALANCE_RP, outlet.getOpeningBalanceRp());

				pstOutlet.setDouble(FLD_OPENING_BALANCE_USD, outlet.getOpeningBalanceUsd());

				pstOutlet.setDouble(FLD_CFM_AMOUNT_CACH_RP, outlet.getCfmAmountCachRp());

				pstOutlet.setDouble(FLD_CFM_AMOUNT_CASH_USD, outlet.getCfmAmountCashUsd());

				pstOutlet.setString(FLD_NOTE, outlet.getNote());



				pstOutlet.update(); 

				return outlet.getOID();



			}

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstOutlet(0),DBException.UNKNOWN); 

		}

		return 0;

	}



	public static long deleteExc(long oid) throws DBException{ 

		try{ 

			PstOutlet pstOutlet = new PstOutlet(oid);

			pstOutlet.delete();

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstOutlet(0),DBException.UNKNOWN); 

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

			String sql = "SELECT * FROM " + TBL_OUTLET; 

			if(whereClause != null && whereClause.length() > 0)

				sql = sql + " WHERE " + whereClause;

			if(order != null && order.length() > 0)

				sql = sql + " ORDER BY " + order;

			if(limitStart == 0 && recordToGet == 0)

				sql = sql + "";

			else

				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) {

				Outlet outlet = new Outlet();

				resultToObject(rs, outlet);

				lists.add(outlet);

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



	private static void resultToObject(ResultSet rs, Outlet outlet){

		try{

			outlet.setOID(rs.getLong(PstOutlet.fieldNames[PstOutlet.FLD_OUTLET_ID]));

			outlet.setStatus(rs.getInt(PstOutlet.fieldNames[PstOutlet.FLD_STATUS]));

	//		outlet.setType(rs.getInt(PstOutlet.fieldNames[PstOutlet.FLD_TYPE]));

			outlet.setOperatorId(rs.getLong(PstOutlet.fieldNames[PstOutlet.FLD_OPERATOR_ID]));

			outlet.setBillingTypeId(rs.getLong(PstOutlet.fieldNames[PstOutlet.FLD_BILLING_TYPE_ID]));

			outlet.setDepartmentId(rs.getLong(PstOutlet.fieldNames[PstOutlet.FLD_DEPARTMENT_ID]));

			outlet.setAddOrder(rs.getBoolean(PstOutlet.fieldNames[PstOutlet.FLD_ADD_ORDER]));

			outlet.setStartDateTime(rs.getDate(PstOutlet.fieldNames[PstOutlet.FLD_START_DATE_TIME]));

			outlet.setEndDateTime(rs.getDate(PstOutlet.fieldNames[PstOutlet.FLD_END_DATE_TIME]));

			outlet.setOpeningBalanceRp(rs.getDouble(PstOutlet.fieldNames[PstOutlet.FLD_OPENING_BALANCE_RP]));

			outlet.setOpeningBalanceUsd(rs.getDouble(PstOutlet.fieldNames[PstOutlet.FLD_OPENING_BALANCE_USD]));

			outlet.setCfmAmountCachRp(rs.getDouble(PstOutlet.fieldNames[PstOutlet.FLD_CFM_AMOUNT_CACH_RP]));

			outlet.setCfmAmountCashUsd(rs.getDouble(PstOutlet.fieldNames[PstOutlet.FLD_CFM_AMOUNT_CASH_USD]));

			outlet.setNote(rs.getString(PstOutlet.fieldNames[PstOutlet.FLD_NOTE]));



		}catch(Exception e){ }

	}



	public static boolean checkOID(long outletId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_OUTLET + " WHERE " + 

						PstOutlet.fieldNames[PstOutlet.FLD_OUTLET_ID] + " = " + outletId;



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();



			while(rs.next()) { result = true; }

			rs.close();

		}catch(Exception e){

			System.out.println("err : "+e.toString());

		}finally{

			DBResultSet.close(dbrs);

			return result;

		}

	}



	public static int getCount(String whereClause){

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT COUNT("+ PstOutlet.fieldNames[PstOutlet.FLD_OUTLET_ID] + ") FROM " + TBL_OUTLET;

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





	/* This method used to find current data */

	public static int findLimitStart( long oid, int recordToGet, String whereClause){

		String order = "";

		int size = getCount(whereClause);

		int start = 0;

		boolean found =false;

		for(int i=0; (i < size) && !found ; i=i+recordToGet){

			 Vector list =  list(i,recordToGet, whereClause, order); 

			 start = i;

			 if(list.size()>0){

			  for(int ls=0;ls<list.size();ls++){ 

			  	   Outlet outlet = (Outlet)list.get(ls);

				   if(oid == outlet.getOID())

					  found=true;

			  }

		  }

		}

		if((start >= size) && (size > 0))

		    start = start - recordToGet;



		return start;

	}

}

