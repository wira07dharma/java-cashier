 

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
//import java.util.Date;



/* package qdep */

import com.dimata.util.lang.I_Language;

import com.dimata.common.db.*;

import com.dimata.qdep.entity.*;



/* package hanoman */

//import com.dimata.qdep.db.DBHandler;

//import com.dimata.qdep.db.DBException;

//import com.dimata.qdep.db.DBLogger;

//import com.dimata.hanoman.entity.masterdata.*;

//import com.dimata.harisma.entity.masterdata.*;



//integrasi POS

import com.dimata.ObjLink.BOPos.CategoryLink;

import com.dimata.interfaces.BOPos.I_Category;



public class PstBillingItemGroup extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language, I_Category {



	//public static final  String TBL_BILLING_ITEM_GROUP = "BILLING_ITEM_GROUP";

    public static final  String TBL_BILLING_ITEM_GROUP = "pos_category";



	public static final  int FLD_BILLING_ITEM_GROUP_ID = 0;

	public static final  int FLD_GROUP_NAME = 1;        

	public static final  int FLD_CATEGORY = 2;

	public static final  int FLD_NOTE = 3;

        public static final  int FLD_GROUP_CODE = 4;

        public static final  int FLD_POIN_PRICE = 5;
        public static final  int FLD_LOCATION = 6;



	public static final  String[] fieldNames = {

		"CATEGORY_ID",

		"NAME",

		"CATEGORY",

		"DESCRIPTION",

                "CODE",

                "POINT_PRICE",
                
                "LOCATION_ID"

	 }; 



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_STRING,

		TYPE_INT,

		TYPE_STRING,

                TYPE_STRING,

                TYPE_FLOAT

	 };





    public static final int CATEGORY_FOOD = 0;

    public static final int CATEGORY_BEVERAGE = 1;

    public static final int CATEGORY_OTHER = 2;



    public static final String[] categoryStr = {"Food", "Beverage/Drink", "Other"};



	public PstBillingItemGroup(){

	}



	public PstBillingItemGroup(int i) throws DBException { 

		super(new PstBillingItemGroup()); 

	}



	public PstBillingItemGroup(String sOid) throws DBException { 

		super(new PstBillingItemGroup(0)); 

		if(!locate(sOid)) 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		else 

			return; 

	}



	public PstBillingItemGroup(long lOid) throws DBException { 

		super(new PstBillingItemGroup(0)); 

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

		return TBL_BILLING_ITEM_GROUP;

	}



	public String[] getFieldNames(){ 

		return fieldNames; 

	}



	public int[] getFieldTypes(){ 

		return fieldTypes; 

	}



	public String getPersistentName(){ 

		return new PstBillingItemGroup().getClass().getName(); 

	}



	public long fetchExc(Entity ent) throws Exception{ 

		BillingItemGroup billingitemgroup = fetchExc(ent.getOID()); 

		ent = (Entity)billingitemgroup; 

		return billingitemgroup.getOID(); 

	}



	public long insertExc(Entity ent) throws Exception{ 

		return insertExc((BillingItemGroup) ent); 

	}



	public long updateExc(Entity ent) throws Exception{ 

		return updateExc((BillingItemGroup) ent); 

	}



	public long deleteExc(Entity ent) throws Exception{ 

		if(ent==null){ 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		} 

		return deleteExc(ent.getOID()); 

	}



	public static BillingItemGroup fetchExc(long oid) throws DBException{ 

		try{ 

			BillingItemGroup billingitemgroup = new BillingItemGroup();

			PstBillingItemGroup pstBillingItemGroup = new PstBillingItemGroup(oid); 

			billingitemgroup.setOID(oid);



			billingitemgroup.setGroupName(pstBillingItemGroup.getString(FLD_GROUP_NAME));

			billingitemgroup.setCategory(pstBillingItemGroup.getInt(FLD_CATEGORY));

			billingitemgroup.setNote(pstBillingItemGroup.getString(FLD_NOTE));

                        billingitemgroup.setGroupCode(pstBillingItemGroup.getString(FLD_GROUP_CODE));

                        billingitemgroup.setPoinPrice(pstBillingItemGroup.getdouble(FLD_POIN_PRICE));



			return billingitemgroup;  

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingItemGroup(0),DBException.UNKNOWN); 

		} 

	}



	public static long insertExc(BillingItemGroup billingitemgroup) throws DBException{ 

		try{ 

			PstBillingItemGroup pstBillingItemGroup = new PstBillingItemGroup(0);



			pstBillingItemGroup.setString(FLD_GROUP_NAME, billingitemgroup.getGroupName());

			pstBillingItemGroup.setInt(FLD_CATEGORY, billingitemgroup.getCategory());

			pstBillingItemGroup.setString(FLD_NOTE, billingitemgroup.getNote());

                        pstBillingItemGroup.setString(FLD_GROUP_CODE, billingitemgroup.getGroupCode());

                        pstBillingItemGroup.setDouble(FLD_POIN_PRICE, billingitemgroup.getPoinPrice());



			pstBillingItemGroup.insert(); 

			billingitemgroup.setOID(pstBillingItemGroup.getlong(FLD_BILLING_ITEM_GROUP_ID));

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingItemGroup(0),DBException.UNKNOWN); 

		}

		return billingitemgroup.getOID();

	}



	public static long updateExc(BillingItemGroup billingitemgroup) throws DBException{ 

		try{ 

			if(billingitemgroup.getOID() != 0){ 

				PstBillingItemGroup pstBillingItemGroup = new PstBillingItemGroup(billingitemgroup.getOID());



				pstBillingItemGroup.setString(FLD_GROUP_NAME, billingitemgroup.getGroupName());

                                pstBillingItemGroup.setInt(FLD_CATEGORY, billingitemgroup.getCategory());

				pstBillingItemGroup.setString(FLD_NOTE, billingitemgroup.getNote());

                                pstBillingItemGroup.setString(FLD_GROUP_CODE, billingitemgroup.getGroupCode());

                                pstBillingItemGroup.setDouble(FLD_POIN_PRICE, billingitemgroup.getPoinPrice());



				pstBillingItemGroup.update(); 

				return billingitemgroup.getOID();



			}

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingItemGroup(0),DBException.UNKNOWN); 

		}

		return 0;

	}



	public static long deleteExc(long oid) throws DBException{ 

		try{ 

			PstBillingItemGroup pstBillingItemGroup = new PstBillingItemGroup(oid);

			pstBillingItemGroup.delete();

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingItemGroup(0),DBException.UNKNOWN); 

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

			String sql = "SELECT * FROM " + TBL_BILLING_ITEM_GROUP; 

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

				BillingItemGroup billingitemgroup = new BillingItemGroup();

				resultToObject(rs, billingitemgroup);

				lists.add(billingitemgroup);

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



	private static void resultToObject(ResultSet rs, BillingItemGroup billingitemgroup){

		try{

			billingitemgroup.setOID(rs.getLong(PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID]));

			billingitemgroup.setGroupName(rs.getString(PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_GROUP_NAME]));

			billingitemgroup.setCategory(rs.getInt(PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_CATEGORY]));

			billingitemgroup.setNote(rs.getString(PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_NOTE]));

                        billingitemgroup.setGroupCode(rs.getString(PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_GROUP_CODE]));

                        billingitemgroup.setPoinPrice(rs.getDouble(PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_POIN_PRICE]));



		}catch(Exception e){ }

	}



	public static boolean checkOID(long billingItemGroupId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_BILLING_ITEM_GROUP + " WHERE " + 

						PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID] + " = " + billingItemGroupId;



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

			String sql = "SELECT COUNT("+ PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID] + ") FROM " + TBL_BILLING_ITEM_GROUP;

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

			  	   BillingItemGroup billingitemgroup = (BillingItemGroup)list.get(ls);

				   if(oid == billingitemgroup.getOID())

					  found=true;

			  }

		  }

		}

		if((start >= size) && (size > 0))

		    start = start - recordToGet;



		return start;

	}



    //INTEGRASI POS



   

    public long insertCategory(CategoryLink catLink){

        BillingItemGroup bg = new BillingItemGroup();

        bg.setGroupName(catLink.getName());

        bg.setCategory(CATEGORY_OTHER);



        try{

            long oid = PstBillingItemGroup.insertExc(bg);

            return synchronizeOID(oid, catLink.getCategoryId());

        }

        catch(Exception e){

        }



        return 0;

    }



    public long updateCategory(CategoryLink catLink){

        BillingItemGroup bg = new BillingItemGroup();



        try{



            bg = PstBillingItemGroup.fetchExc(catLink.getCategoryId());

            bg.setGroupName(catLink.getName());



            return PstBillingItemGroup.updateExc(bg);



        }

        catch(Exception e){

        }



        return 0;

    }



    public long deleteCategory(CategoryLink catLink){

        String where = PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID]+"="+catLink.getCategoryId();

        Vector vct = PstBillingTypeItem.list(0,0, where, null);

        if(vct!=null && vct.size()>0){

            return 0;

        }

        else{

            try{

                PstBillingItemGroup.deleteExc(catLink.getCategoryId());

            }

            catch(Exception e){

            }

        }

        return catLink.getCategoryId();

    }



    public long synchronizeOID(long oldOID, long newOID){

        String sql = "UPDATE "+TBL_BILLING_ITEM_GROUP+

            " SET "+fieldNames[FLD_BILLING_ITEM_GROUP_ID]+"="+ newOID+

            " WHERE "+ fieldNames[FLD_BILLING_ITEM_GROUP_ID]+"="+ oldOID;

        try{

            DBHandler.execUpdate(sql);

        }

        catch(Exception e){



        }



        return  newOID;

	}



    //--- end

    

    

    

    public static boolean isNameExist(long oid, String name){

        DBResultSet dbrs = null;

        boolean result = false;

        try{

            String sql = "SELECT COUNT("+fieldNames[FLD_BILLING_ITEM_GROUP_ID]+") "+

                " FROM "+TBL_BILLING_ITEM_GROUP+

                " WHERE "+fieldNames[FLD_GROUP_NAME]+"='"+name+"'"+

                " AND "+fieldNames[FLD_BILLING_ITEM_GROUP_ID]+"!="+oid;

            

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

    

    



}

