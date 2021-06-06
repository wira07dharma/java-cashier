 

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



public class PstCover extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 



	//public static final  String TBL_COVER = "COVER";

    public static final  String TBL_COVER = "cover";



	public static final  int FLD_COVER_ID = 0;

	public static final  int FLD_NUMBER = 1;

	public static final  int FLD_DESCRIPTION = 2;

	public static final  int FLD_STATUS = 3;

	public static final  int FLD_SEAT_CAPACITY = 4;

    public static final  int FLD_DEPARTMENT_ID = 5;

    public static final  int FLD_BILLING_TYPE_ID = 6;



	public static final  String[] fieldNames = {

		"COVER_ID",

		"NUMBER",

		"DESCRIPTION",

		"STATUS",

		"SEAT_CAPACITY",

        "DEPARTMENT_ID",

        "BILLING_TYPE_ID"

	 }; 



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_STRING,

		TYPE_STRING,

		TYPE_INT,

		TYPE_INT,

        TYPE_LONG,

        TYPE_LONG

	 };



    public static final int STATUS_OPEN	= 0;

    public static final int STATUS_CLOSED = 1;



    public static final String[] statusStr = {"Open", "Closed"};



	public PstCover(){

	}



	public PstCover(int i) throws DBException { 

		super(new PstCover()); 

	}



	public PstCover(String sOid) throws DBException { 

		super(new PstCover(0)); 

		if(!locate(sOid)) 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		else 

			return; 

	}



	public PstCover(long lOid) throws DBException { 

		super(new PstCover(0)); 

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

		return TBL_COVER;

	}



	public String[] getFieldNames(){ 

		return fieldNames; 

	}



	public int[] getFieldTypes(){ 

		return fieldTypes; 

	}



	public String getPersistentName(){ 

		return new PstCover().getClass().getName(); 

	}



	public long fetchExc(Entity ent) throws Exception{ 

		Cover cover = fetchExc(ent.getOID()); 

		ent = (Entity)cover; 

		return cover.getOID(); 

	}



	public long insertExc(Entity ent) throws Exception{ 

		return insertExc((Cover) ent); 

	}



	public long updateExc(Entity ent) throws Exception{ 

		return updateExc((Cover) ent); 

	}



	public long deleteExc(Entity ent) throws Exception{ 

		if(ent==null){ 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		} 

		return deleteExc(ent.getOID()); 

	}



	public static Cover fetchExc(long oid) throws DBException{ 

		try{ 

			Cover cover = new Cover();

			PstCover pstCover = new PstCover(oid); 

			cover.setOID(oid);



			cover.setNumber(pstCover.getString(FLD_NUMBER));

			cover.setDescription(pstCover.getString(FLD_DESCRIPTION));

			cover.setStatus(pstCover.getInt(FLD_STATUS));

			cover.setSeatCapacity(pstCover.getInt(FLD_SEAT_CAPACITY));

            cover.setDepartmentId(pstCover.getlong(FLD_DEPARTMENT_ID));

            cover.setBillingTypeId(pstCover.getlong(FLD_BILLING_TYPE_ID));



			return cover; 

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstCover(0),DBException.UNKNOWN); 

		} 

	}



	public static long insertExc(Cover cover) throws DBException{ 

		try{ 

			PstCover pstCover = new PstCover(0);



			pstCover.setString(FLD_NUMBER, cover.getNumber());

			pstCover.setString(FLD_DESCRIPTION, cover.getDescription());

			pstCover.setInt(FLD_STATUS, cover.getStatus());

			pstCover.setInt(FLD_SEAT_CAPACITY, cover.getSeatCapacity());

            pstCover.setLong(FLD_DEPARTMENT_ID, cover.getDepartmentId());

            pstCover.setLong(FLD_BILLING_TYPE_ID, cover.getBillingTypeId());



			pstCover.insert(); 

			cover.setOID(pstCover.getlong(FLD_COVER_ID));

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstCover(0),DBException.UNKNOWN); 

		}

		return cover.getOID();

	}



	public static long updateExc(Cover cover) throws DBException{ 

		try{ 

			if(cover.getOID() != 0){ 

				PstCover pstCover = new PstCover(cover.getOID());



				pstCover.setString(FLD_NUMBER, cover.getNumber());

				pstCover.setString(FLD_DESCRIPTION, cover.getDescription());

				pstCover.setInt(FLD_STATUS, cover.getStatus());

				pstCover.setInt(FLD_SEAT_CAPACITY, cover.getSeatCapacity());

                pstCover.setLong(FLD_DEPARTMENT_ID, cover.getDepartmentId());

                pstCover.setLong(FLD_BILLING_TYPE_ID, cover.getBillingTypeId());



				pstCover.update(); 

				return cover.getOID();



			}

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstCover(0),DBException.UNKNOWN); 

		}

		return 0;

	}



	public static long deleteExc(long oid) throws DBException{ 

		try{ 

			PstCover pstCover = new PstCover(oid);

			pstCover.delete();

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstCover(0),DBException.UNKNOWN); 

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

			String sql = "SELECT * FROM " + TBL_COVER; 

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

				Cover cover = new Cover();

				resultToObject(rs, cover);

				lists.add(cover);

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



	public static void resultToObject(ResultSet rs, Cover cover){

		try{

			cover.setOID(rs.getLong(PstCover.fieldNames[PstCover.FLD_COVER_ID]));

			cover.setNumber(rs.getString(PstCover.fieldNames[PstCover.FLD_NUMBER]));

			cover.setDescription(rs.getString(PstCover.fieldNames[PstCover.FLD_DESCRIPTION]));

			cover.setStatus(rs.getInt(PstCover.fieldNames[PstCover.FLD_STATUS]));

			cover.setSeatCapacity(rs.getInt(PstCover.fieldNames[PstCover.FLD_SEAT_CAPACITY]));

            cover.setDepartmentId(rs.getLong(PstCover.fieldNames[PstCover.FLD_DEPARTMENT_ID]));

            cover.setBillingTypeId(rs.getLong(PstCover.fieldNames[PstCover.FLD_BILLING_TYPE_ID]));





		}catch(Exception e){ }

	}



	public static boolean checkOID(long coverId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_COVER + " WHERE " + 

						PstCover.fieldNames[PstCover.FLD_COVER_ID] + " = " + coverId;



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

			String sql = "SELECT COUNT("+ PstCover.fieldNames[PstCover.FLD_COVER_ID] + ") FROM " + TBL_COVER;

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

			  	   Cover cover = (Cover)list.get(ls);

				   if(oid == cover.getOID())

					  found=true;

			  }

		  }

		}

		if((start >= size) && (size > 0))

		    start = start - recordToGet;



		return start;

	}

}

