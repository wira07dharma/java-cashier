 

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



package com.dimata.hanoman.entity.cashier; 



/* package java */ 

import java.io.*

;

import java.sql.*

;import java.util.*

;import java.util.Date;



/* package qdep */

import com.dimata.util.lang.I_Language;

import com.dimata.common.db.*;

import com.dimata.qdep.entity.*;



/* package hanoman */

//import com.dimata.qdep.db.DBHandler;

//import com.dimata.qdep.db.DBException;

//import com.dimata.qdep.db.DBLogger;

import com.dimata.hanoman.entity.cashier.*; 



public class PstBillingRecHistory extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 



	//public static final  String TBL_BILLING_REC_HISTORY = "BILLING_REC_HISTORY";

    public static final  String TBL_BILLING_REC_HISTORY = "billing_rec_history";



	public static final  int FLD_BILLING_REC_HISTORY_ID = 0;

	public static final  int FLD_BILLING_REC_ID = 1;

	public static final  int FLD_UPDATE_DATE = 2;

	public static final  int FLD_HISTORY = 3;



	public static final  String[] fieldNames = {

		"BILLING_REC_HISTORY_ID",

		"COVER_BILLING_ID",

		"UPDATE_DATE",

		"HISTORY"

	 }; 



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_LONG,

		TYPE_DATE,

		TYPE_STRING

	 }; 



	public PstBillingRecHistory(){

	}



	public PstBillingRecHistory(int i) throws DBException { 

		super(new PstBillingRecHistory()); 

	}



	public PstBillingRecHistory(String sOid) throws DBException { 

		super(new PstBillingRecHistory(0)); 

		if(!locate(sOid)) 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		else 

			return; 

	}



	public PstBillingRecHistory(long lOid) throws DBException { 

		super(new PstBillingRecHistory(0)); 

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

		return TBL_BILLING_REC_HISTORY;

	}



	public String[] getFieldNames(){ 

		return fieldNames; 

	}



	public int[] getFieldTypes(){ 

		return fieldTypes; 

	}



	public String getPersistentName(){ 

		return new PstBillingRecHistory().getClass().getName(); 

	}



	public long fetchExc(Entity ent) throws Exception{ 

		BillingRecHistory billingrechistory = fetchExc(ent.getOID()); 

		ent = (Entity)billingrechistory; 

		return billingrechistory.getOID(); 

	}



	public long insertExc(Entity ent) throws Exception{ 

		return insertExc((BillingRecHistory) ent); 

	}



	public long updateExc(Entity ent) throws Exception{ 

		return updateExc((BillingRecHistory) ent); 

	}



	public long deleteExc(Entity ent) throws Exception{ 

		if(ent==null){ 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		} 

		return deleteExc(ent.getOID()); 

	}



	public static BillingRecHistory fetchExc(long oid) throws DBException{ 

		try{ 

			BillingRecHistory billingrechistory = new BillingRecHistory();

			PstBillingRecHistory pstBillingRecHistory = new PstBillingRecHistory(oid); 

			billingrechistory.setOID(oid);



			billingrechistory.setBillingRecId(pstBillingRecHistory.getlong(FLD_BILLING_REC_ID));

			billingrechistory.setUpdateDate(pstBillingRecHistory.getDate(FLD_UPDATE_DATE));

			billingrechistory.setHistory(pstBillingRecHistory.getString(FLD_HISTORY));



			return billingrechistory; 

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingRecHistory(0),DBException.UNKNOWN); 

		} 

	}



	public static long insertExc(BillingRecHistory billingrechistory) throws DBException{ 

		try{ 

			PstBillingRecHistory pstBillingRecHistory = new PstBillingRecHistory(0);



			pstBillingRecHistory.setLong(FLD_BILLING_REC_ID, billingrechistory.getBillingRecId());

			pstBillingRecHistory.setDate(FLD_UPDATE_DATE, billingrechistory.getUpdateDate());

			pstBillingRecHistory.setString(FLD_HISTORY, billingrechistory.getHistory());



			pstBillingRecHistory.insert(); 

			billingrechistory.setOID(pstBillingRecHistory.getlong(FLD_BILLING_REC_HISTORY_ID));

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingRecHistory(0),DBException.UNKNOWN); 

		}

		return billingrechistory.getOID();

	}



	public static long updateExc(BillingRecHistory billingrechistory) throws DBException{ 

		try{ 

			if(billingrechistory.getOID() != 0){ 

				PstBillingRecHistory pstBillingRecHistory = new PstBillingRecHistory(billingrechistory.getOID());



				pstBillingRecHistory.setLong(FLD_BILLING_REC_ID, billingrechistory.getBillingRecId());

				pstBillingRecHistory.setDate(FLD_UPDATE_DATE, billingrechistory.getUpdateDate());

				pstBillingRecHistory.setString(FLD_HISTORY, billingrechistory.getHistory());



				pstBillingRecHistory.update(); 

				return billingrechistory.getOID();



			}

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingRecHistory(0),DBException.UNKNOWN); 

		}

		return 0;

	}



	public static long deleteExc(long oid) throws DBException{ 

		try{ 

			PstBillingRecHistory pstBillingRecHistory = new PstBillingRecHistory(oid);

			pstBillingRecHistory.delete();

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingRecHistory(0),DBException.UNKNOWN); 

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

			String sql = "SELECT * FROM " + TBL_BILLING_REC_HISTORY; 

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

				BillingRecHistory billingrechistory = new BillingRecHistory();

				resultToObject(rs, billingrechistory);

				lists.add(billingrechistory);

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



	private static void resultToObject(ResultSet rs, BillingRecHistory billingrechistory){

		try{

			billingrechistory.setOID(rs.getLong(PstBillingRecHistory.fieldNames[PstBillingRecHistory.FLD_BILLING_REC_HISTORY_ID]));

			billingrechistory.setBillingRecId(rs.getLong(PstBillingRecHistory.fieldNames[PstBillingRecHistory.FLD_BILLING_REC_ID]));

			billingrechistory.setUpdateDate(rs.getDate(PstBillingRecHistory.fieldNames[PstBillingRecHistory.FLD_UPDATE_DATE]));

			billingrechistory.setHistory(rs.getString(PstBillingRecHistory.fieldNames[PstBillingRecHistory.FLD_HISTORY]));



		}catch(Exception e){ }

	}



	public static boolean checkOID(long billingRecHistoryId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_BILLING_REC_HISTORY + " WHERE " + 

						PstBillingRecHistory.fieldNames[PstBillingRecHistory.FLD_BILLING_REC_HISTORY_ID] + " = " + billingRecHistoryId;



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

			String sql = "SELECT COUNT("+ PstBillingRecHistory.fieldNames[PstBillingRecHistory.FLD_BILLING_REC_HISTORY_ID] + ") FROM " + TBL_BILLING_REC_HISTORY;

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

			  	   BillingRecHistory billingrechistory = (BillingRecHistory)list.get(ls);

				   if(oid == billingrechistory.getOID())

					  found=true;

			  }

		  }

		}

		if((start >= size) && (size > 0))

		    start = start - recordToGet;



		return start;

	}





  

}

