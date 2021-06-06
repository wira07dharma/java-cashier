 

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



public class PstOtherPayment extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 



	//public static final  String TBL_OTHER_PAYMENT = "OTHER_PAYMENT";

    public static final  String TBL_OTHER_PAYMENT = "other_payment";



	public static final  int FLD_OTHER_PAY_ID = 0;

	public static final  int FLD_PAY_NAME = 1;

	public static final  int FLD_DESCRIPTION = 2;



	public static final  String[] fieldNames = {

		"OTHER_PAY_ID",

		"PAY_NAME",

		"DESCRIPTION"

	 }; 



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_STRING,

		TYPE_STRING

	 }; 



	public PstOtherPayment(){

	}



	public PstOtherPayment(int i) throws DBException { 

		super(new PstOtherPayment()); 

	}



	public PstOtherPayment(String sOid) throws DBException { 

		super(new PstOtherPayment(0)); 

		if(!locate(sOid)) 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		else 

			return; 

	}



	public PstOtherPayment(long lOid) throws DBException { 

		super(new PstOtherPayment(0)); 

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

		return TBL_OTHER_PAYMENT;

	}



	public String[] getFieldNames(){ 

		return fieldNames; 

	}



	public int[] getFieldTypes(){ 

		return fieldTypes; 

	}



	public String getPersistentName(){ 

		return new PstOtherPayment().getClass().getName(); 

	}



	public long fetchExc(Entity ent) throws Exception{ 

		OtherPayment otherpayment = fetchExc(ent.getOID()); 

		ent = (Entity)otherpayment; 

		return otherpayment.getOID(); 

	}



	public long insertExc(Entity ent) throws Exception{ 

		return insertExc((OtherPayment) ent); 

	}



	public long updateExc(Entity ent) throws Exception{ 

		return updateExc((OtherPayment) ent); 

	}



	public long deleteExc(Entity ent) throws Exception{ 

		if(ent==null){ 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		} 

		return deleteExc(ent.getOID()); 

	}



	public static OtherPayment fetchExc(long oid) throws DBException{ 

		try{ 

			OtherPayment otherpayment = new OtherPayment();

			PstOtherPayment pstOtherPayment = new PstOtherPayment(oid); 

			otherpayment.setOID(oid);



			otherpayment.setPayName(pstOtherPayment.getString(FLD_PAY_NAME));

			otherpayment.setDescription(pstOtherPayment.getString(FLD_DESCRIPTION));



			return otherpayment; 

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstOtherPayment(0),DBException.UNKNOWN); 

		} 

	}



	public static long insertExc(OtherPayment otherpayment) throws DBException{ 

		try{ 

			PstOtherPayment pstOtherPayment = new PstOtherPayment(0);



			pstOtherPayment.setString(FLD_PAY_NAME, otherpayment.getPayName());

			pstOtherPayment.setString(FLD_DESCRIPTION, otherpayment.getDescription());



			pstOtherPayment.insert(); 

			otherpayment.setOID(pstOtherPayment.getlong(FLD_OTHER_PAY_ID));

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstOtherPayment(0),DBException.UNKNOWN); 

		}

		return otherpayment.getOID();

	}



	public static long updateExc(OtherPayment otherpayment) throws DBException{ 

		try{ 

			if(otherpayment.getOID() != 0){ 

				PstOtherPayment pstOtherPayment = new PstOtherPayment(otherpayment.getOID());



				pstOtherPayment.setString(FLD_PAY_NAME, otherpayment.getPayName());

				pstOtherPayment.setString(FLD_DESCRIPTION, otherpayment.getDescription());



				pstOtherPayment.update(); 

				return otherpayment.getOID();



			}

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstOtherPayment(0),DBException.UNKNOWN); 

		}

		return 0;

	}



	public static long deleteExc(long oid) throws DBException{ 

		try{ 

			PstOtherPayment pstOtherPayment = new PstOtherPayment(oid);

			pstOtherPayment.delete();

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstOtherPayment(0),DBException.UNKNOWN); 

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

			String sql = "SELECT * FROM " + TBL_OTHER_PAYMENT; 

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

				OtherPayment otherpayment = new OtherPayment();

				resultToObject(rs, otherpayment);

				lists.add(otherpayment);

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



	private static void resultToObject(ResultSet rs, OtherPayment otherpayment){

		try{

			otherpayment.setOID(rs.getLong(PstOtherPayment.fieldNames[PstOtherPayment.FLD_OTHER_PAY_ID]));

			otherpayment.setPayName(rs.getString(PstOtherPayment.fieldNames[PstOtherPayment.FLD_PAY_NAME]));

			otherpayment.setDescription(rs.getString(PstOtherPayment.fieldNames[PstOtherPayment.FLD_DESCRIPTION]));



		}catch(Exception e){ }

	}



	public static boolean checkOID(long otherPayId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_OTHER_PAYMENT + " WHERE " + 

						PstOtherPayment.fieldNames[PstOtherPayment.FLD_OTHER_PAY_ID] + " = " + otherPayId;



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

			String sql = "SELECT COUNT("+ PstOtherPayment.fieldNames[PstOtherPayment.FLD_OTHER_PAY_ID] + ") FROM " + TBL_OTHER_PAYMENT;

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

			  	   OtherPayment otherpayment = (OtherPayment)list.get(ls);

				   if(oid == otherpayment.getOID())

					  found=true;

			  }

		  }

		}

		if((start >= size) && (size > 0))

		    start = start - recordToGet;



		return start;

	}

}

