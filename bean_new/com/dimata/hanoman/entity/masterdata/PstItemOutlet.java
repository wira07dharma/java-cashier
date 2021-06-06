 

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

import com.dimata.hanoman.entity.masterdata.*; 



public class PstItemOutlet extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 



	public static final  String TBL_ITEM_OUTLET = "product_location";



	public static final  int FLD_BILLING_TYPE_ID = 0;

	public static final  int FLD_BILLING_TYPE_ITEM_ID = 1;



	public static final  String[] fieldNames = {

		"LOCATION_ID",

		"PRODUCT_ID"

	 }; 



	public static final  int[] fieldTypes = {

		TYPE_PK + TYPE_FK + TYPE_LONG,

		TYPE_PK + TYPE_FK + TYPE_LONG

	 }; 



	public PstItemOutlet(){

	}



	public PstItemOutlet(int i) throws DBException { 

		super(new PstItemOutlet()); 

	}



	public PstItemOutlet(String sOid) throws DBException { 

		super(new PstItemOutlet(0)); 

		if(!locate(sOid)) 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		else 

			return; 

	}



	public PstItemOutlet(long billingTypeId, long billingTypeItemId) throws DBException { 

		super(new PstItemOutlet(0)); 

		if(!locate(billingTypeId, billingTypeItemId))

			throw new DBException(this,DBException.RECORD_NOT_FOUND);

		else 

			return; 

	} 



	public int getFieldSize(){ 

		return fieldNames.length; 

	}



	public String getTableName(){ 

		return TBL_ITEM_OUTLET;

	}



	public String[] getFieldNames(){ 

		return fieldNames; 

	}



	public int[] getFieldTypes(){ 

		return fieldTypes; 

	}



	public String getPersistentName(){ 

		return new PstItemOutlet().getClass().getName(); 

	}



	public long fetchExc(Entity ent) throws Exception{ 

		ItemOutlet itemoutlet = fetchExc(ent.getOID(0), ent.getOID(1) ); 

		ent = (Entity)itemoutlet; 

		return itemoutlet.getOID(0); 

	}



	public long insertExc(Entity ent) throws Exception{ 

		return insertExc((ItemOutlet) ent); 

	}



	public long updateExc(Entity ent) throws Exception{ 

		return updateExc((ItemOutlet) ent); 

	}



	public long deleteExc(Entity ent) throws Exception{ 

		if(ent==null){ 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		} 

		return deleteExc(ent); 

	}



	public static ItemOutlet fetchExc(long billingTypeId, long billingTypeItemId) throws DBException{ 

		try{ 

			ItemOutlet itemoutlet = new ItemOutlet();

			PstItemOutlet pstItemOutlet = new PstItemOutlet(billingTypeId, billingTypeItemId); 

			itemoutlet.setBillingTypeId(billingTypeId);

			itemoutlet.setBillingTypeItemId(billingTypeItemId);





			return itemoutlet; 

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstItemOutlet(0),DBException.UNKNOWN); 

		} 

	}



	public static long insertExc(ItemOutlet itemoutlet) throws DBException{ 

		try{ 

			PstItemOutlet pstItemOutlet = new PstItemOutlet(0);



			pstItemOutlet.setLong(FLD_BILLING_TYPE_ID, itemoutlet.getBillingTypeId());

			pstItemOutlet.setLong(FLD_BILLING_TYPE_ITEM_ID, itemoutlet.getBillingTypeItemId());



			pstItemOutlet.insert(); 

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstItemOutlet(0),DBException.UNKNOWN); 

		}

		return itemoutlet.getOID();

	}



	public static long updateExc(ItemOutlet itemoutlet) throws DBException{ 

		try{ 

			if(itemoutlet.getOID() != 0){ 

				PstItemOutlet pstItemOutlet = new PstItemOutlet(itemoutlet.getBillingTypeId(), itemoutlet.getBillingTypeItemId());





				pstItemOutlet.update(); 

				return itemoutlet.getBillingTypeId();

			}

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstItemOutlet(0),DBException.UNKNOWN); 

		}

		return 0;

	}



	public static long deleteExc(long billingTypeId, long billingTypeItemId) throws DBException{ 

		try{ 

			PstItemOutlet pstItemOutlet = new PstItemOutlet(billingTypeId, billingTypeItemId);

			pstItemOutlet.delete();

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstItemOutlet(0),DBException.UNKNOWN); 

		}

		return billingTypeId;

	}



	public static Vector listAll(){ 

		return list(0, 500, "",""); 

	}



	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){

		Vector lists = new Vector(); 

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT * FROM " + TBL_ITEM_OUTLET; 

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

				ItemOutlet itemoutlet = new ItemOutlet();

				resultToObject(rs, itemoutlet);

				lists.add(itemoutlet);

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



	private static void resultToObject(ResultSet rs, ItemOutlet itemoutlet){

		try{

			itemoutlet.setBillingTypeId(rs.getLong(PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ID]));

			itemoutlet.setBillingTypeItemId(rs.getLong(PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID]));



		}catch(Exception e){ }

	}



	public static boolean checkOID(long billingTypeId, long billingTypeItemId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_ITEM_OUTLET + " WHERE " + 

			PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ID] + " = " + billingTypeId + " AND " + 

			PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID] + " = " + billingTypeItemId;



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

			String sql = "SELECT COUNT("+ PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID] + ") FROM " + TBL_ITEM_OUTLET;

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





	public static long deleteByBillingTypeId (long billingTypeId) {

		 try {

			 String sql = "DELETE FROM " + TBL_ITEM_OUTLET+

				 " WHERE " +PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ID] +

				 " = " + billingTypeId ;



			 DBHandler.execUpdate(sql);

                         

			 return billingTypeId;

		 }catch(Exception exc) { 

		    System.out.println(" exception delete by Owner ID "+exc.toString()); 

		 }

		 return 0;  

	}

        

	public static long deleteByBillingTypeItemId (long billingTypeItemId) {

		 try {

			 String sql = "DELETE FROM " + TBL_ITEM_OUTLET+

				 " WHERE "+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID] +

				 " = " + billingTypeItemId ;



			 DBHandler.execUpdate(sql);

                         

			 return billingTypeItemId;

		 }catch(Exception exc) { 

		    System.out.println(" exception delete by Owner ID "+exc.toString()); 

		 }

		 return 0;  

	}

	/* This method used to find current data */

	/*public static int findLimitStart(long billingTypeId, long billingTypeItemId, int recordToGet, String whereClause){

		String order = "";

		int size = getCount(whereClause);

		int start = 0;

		boolean found =false;

		for(int i=0; (i < size) && !found ; i=i+recordToGet){

			 Vector list =  list(i,recordToGet, whereClause, order); 

			 start = i;

			 if(list.size()>0){

			  for(int ls=0;ls<list.size();ls++){ 

			  	   ItemOutlet itemoutlet = (ItemOutlet)list.get(ls);

				   if(oid == itemoutlet.getOID())

					  found=true;

			  }

		  }

		}

		if((start >= size) && (size > 0))

		    start = start - recordToGet;



		return start;

	}*/

}

