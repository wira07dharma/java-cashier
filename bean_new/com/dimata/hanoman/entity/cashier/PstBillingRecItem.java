 

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

import com.dimata.hanoman.entity.masterdata.*;



public class PstBillingRecItem extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 



	//public static final  String TBL_BILLING_REC_ITEM = "COVER_BILLING_ITEM";

    public static final  String TBL_BILLING_REC_ITEM = "cash_bill_detail";



	public static final  int FLD_BILLING_REC_ITEM_ID = 0;

	public static final  int FLD_BILLING_TYPE_ITEM_ID = 1;

	public static final  int FLD_BILLING_REC_ID = 2;

	public static final  int FLD_QUANTITY = 3;

	public static final  int FLD_SELLING_PRICE = 4;

	public static final  int FLD_AMOUNT = 5;

	public static final  int FLD_ITEM_CODE = 6;

	public static final  int FLD_ITEM_NAME = 7;

	//public static final  int FLD_ITEM_COST = 8; //tidak terpakai

	public static final  int FLD_DESCRIPTION = 8;

        public static final  int FLD_TOTAL_SERVICE = 9;

        public static final  int FLD_TOTAL_TAX = 10;

        public static final  int FLD_TOTAL_ALL_RATE = 11;

        public static final  int FLD_TYPE = 12;

        public static final  int FLD_COST_PER_ITEM = 13;  

        public static final  int FLD_TOTAL_COST = 14;

        public static final  int FLD_BILLING_TYPE_ID = 15;

        public static final  int FLD_DISCOUNT = 16;

	public static final  String[] fieldNames = {

		"CASH_BILL_DETAIL_ID",

		"MATERIAL_ID",

		"CASH_BILL_MAIN_ID",

		"QTY",

		"ITEM_PRICE",

		"TOTAL_PRICE",

		"SKU",

		"ITEM_NAME",

		//"PRICE_UNIT_RP", //tidak terpakai

		"DESCRIPTION",

                "TOTAL_SERVICE",

                "TOTAL_TAX",

                "TOTAL_ALL_RATE",

                "TYPE",

                "COST",

                "TOTAL_COST",

                "LOCATION_ID",

                "DISC"

	 };



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_LONG,

		TYPE_LONG,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_STRING,

		TYPE_STRING,

		//TYPE_FLOAT,

		TYPE_STRING,

                TYPE_FLOAT,

                TYPE_FLOAT,

                TYPE_FLOAT,

                TYPE_INT,

                TYPE_FLOAT,

                TYPE_FLOAT,

                TYPE_LONG,

                TYPE_FLOAT

	 };



    public static final int TYPE_REGULAR 		= 0;

    public static final int TYPE_ADJUSTMENT  	= 1;



	public static final String[] typeStr = {"Regular", "Adjustment"};





	public PstBillingRecItem(){

	}



	public PstBillingRecItem(int i) throws DBException { 

		super(new PstBillingRecItem()); 

	}



	public PstBillingRecItem(String sOid) throws DBException { 

		super(new PstBillingRecItem(0)); 

		if(!locate(sOid)) 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		else 

			return; 

	}



	public PstBillingRecItem(long lOid) throws DBException { 

		super(new PstBillingRecItem(0)); 

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

		return TBL_BILLING_REC_ITEM;

	}



	public String[] getFieldNames(){ 

		return fieldNames; 

	}



	public int[] getFieldTypes(){ 

		return fieldTypes; 

	}



	public String getPersistentName(){ 

		return new PstBillingRecItem().getClass().getName(); 
	}



	public long fetchExc(Entity ent) throws Exception{ 

		BillingRecItem billingrecitem = fetchExc(ent.getOID()); 

		ent = (Entity)billingrecitem; 

		return billingrecitem.getOID(); 

	}



	public long insertExc(Entity ent) throws Exception{ 

		return insertExc((BillingRecItem) ent); 

	}



	public long updateExc(Entity ent) throws Exception{ 

		return updateExc((BillingRecItem) ent); 

	}



	public long deleteExc(Entity ent) throws Exception{ 

		if(ent==null){ 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		} 

		return deleteExc(ent.getOID()); 

	}



	public static BillingRecItem fetchExc(long oid) throws DBException{ 

		try{ 

			BillingRecItem billingrecitem = new BillingRecItem();

			PstBillingRecItem pstBillingRecItem = new PstBillingRecItem(oid); 

			billingrecitem.setOID(oid);



			billingrecitem.setBillingTypeItemId(pstBillingRecItem.getlong(FLD_BILLING_TYPE_ITEM_ID));

			billingrecitem.setBillingRecId(pstBillingRecItem.getlong(FLD_BILLING_REC_ID));

			billingrecitem.setQuantity(pstBillingRecItem.getdouble(FLD_QUANTITY));

			billingrecitem.setSellingPrice(pstBillingRecItem.getdouble(FLD_SELLING_PRICE));

			billingrecitem.setAmount(pstBillingRecItem.getdouble(FLD_AMOUNT));

			billingrecitem.setItemCode(pstBillingRecItem.getString(FLD_ITEM_CODE));

			billingrecitem.setItemName(pstBillingRecItem.getString(FLD_ITEM_NAME));

			//billingrecitem.setItemCost(pstBillingRecItem.getdouble(FLD_ITEM_COST));

			billingrecitem.setDescription(pstBillingRecItem.getString(FLD_DESCRIPTION));

                        billingrecitem.setTotalService(pstBillingRecItem.getdouble(FLD_TOTAL_SERVICE));

                        billingrecitem.setTotalTax(pstBillingRecItem.getdouble(FLD_TOTAL_TAX));

                        billingrecitem.setTotalAllRate(pstBillingRecItem.getdouble(FLD_TOTAL_ALL_RATE));

			billingrecitem.setType(pstBillingRecItem.getInt(FLD_TYPE));

                        billingrecitem.setCostPerItem(pstBillingRecItem.getdouble(FLD_COST_PER_ITEM));

                        billingrecitem.setTotalCost(pstBillingRecItem.getdouble(FLD_TOTAL_COST));

                        billingrecitem.setBillingTypeId(pstBillingRecItem.getlong(FLD_BILLING_TYPE_ID));

                        billingrecitem.setSellingPrice(pstBillingRecItem.getdouble(FLD_SELLING_PRICE));



			return billingrecitem;

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingRecItem(0),DBException.UNKNOWN); 

		} 

	}



	public static long insertExc(BillingRecItem billingrecitem) throws DBException{ 

		try{ 

			PstBillingRecItem pstBillingRecItem = new PstBillingRecItem(0);



			pstBillingRecItem.setLong(FLD_BILLING_TYPE_ITEM_ID, billingrecitem.getBillingTypeItemId());

			pstBillingRecItem.setLong(FLD_BILLING_REC_ID, billingrecitem.getBillingRecId());

			pstBillingRecItem.setDouble(FLD_QUANTITY, billingrecitem.getQuantity());

			pstBillingRecItem.setDouble(FLD_SELLING_PRICE, billingrecitem.getSellingPrice());

			pstBillingRecItem.setDouble(FLD_AMOUNT, billingrecitem.getAmount());

			pstBillingRecItem.setString(FLD_ITEM_CODE, billingrecitem.getItemCode());

			pstBillingRecItem.setString(FLD_ITEM_NAME, billingrecitem.getItemName());

			//pstBillingRecItem.setDouble(FLD_ITEM_COST, billingrecitem.getItemCost());

			pstBillingRecItem.setString(FLD_DESCRIPTION, billingrecitem.getDescription());

                        pstBillingRecItem.setDouble(FLD_TOTAL_SERVICE, billingrecitem.getTotalService());

                        pstBillingRecItem.setDouble(FLD_TOTAL_TAX, billingrecitem.getTotalTax());

                        pstBillingRecItem.setDouble(FLD_TOTAL_ALL_RATE, billingrecitem.getTotalAllRate());

                        pstBillingRecItem.setInt(FLD_TYPE, billingrecitem.getType());

                        pstBillingRecItem.setDouble(FLD_COST_PER_ITEM, billingrecitem.getCostPerItem());

                        pstBillingRecItem.setDouble(FLD_TOTAL_COST, billingrecitem.getTotalCost());

                        pstBillingRecItem.setLong(FLD_BILLING_TYPE_ID, billingrecitem.getBillingTypeId());



			pstBillingRecItem.insert();

			billingrecitem.setOID(pstBillingRecItem.getlong(FLD_BILLING_REC_ITEM_ID));

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingRecItem(0),DBException.UNKNOWN); 

		}

		return billingrecitem.getOID();

	}



	public static long updateExc(BillingRecItem billingrecitem) throws DBException{ 

		try{ 

			if(billingrecitem.getOID() != 0){ 

				PstBillingRecItem pstBillingRecItem = new PstBillingRecItem(billingrecitem.getOID());



				pstBillingRecItem.setLong(FLD_BILLING_TYPE_ITEM_ID, billingrecitem.getBillingTypeItemId());

				pstBillingRecItem.setLong(FLD_BILLING_REC_ID, billingrecitem.getBillingRecId());

				pstBillingRecItem.setDouble(FLD_QUANTITY, billingrecitem.getQuantity());

				pstBillingRecItem.setDouble(FLD_SELLING_PRICE, billingrecitem.getSellingPrice());

				pstBillingRecItem.setDouble(FLD_AMOUNT, billingrecitem.getAmount());

				pstBillingRecItem.setString(FLD_ITEM_CODE, billingrecitem.getItemCode());

				pstBillingRecItem.setString(FLD_ITEM_NAME, billingrecitem.getItemName());

				//pstBillingRecItem.setDouble(FLD_ITEM_COST, billingrecitem.getItemCost());

				pstBillingRecItem.setString(FLD_DESCRIPTION, billingrecitem.getDescription());

                                pstBillingRecItem.setDouble(FLD_TOTAL_SERVICE, billingrecitem.getTotalService());

                                pstBillingRecItem.setDouble(FLD_TOTAL_TAX, billingrecitem.getTotalTax());

                                pstBillingRecItem.setDouble(FLD_TOTAL_ALL_RATE, billingrecitem.getTotalAllRate());

                                pstBillingRecItem.setInt(FLD_TYPE, billingrecitem.getType());

                                pstBillingRecItem.setDouble(FLD_COST_PER_ITEM, billingrecitem.getCostPerItem());

                                pstBillingRecItem.setDouble(FLD_TOTAL_COST, billingrecitem.getTotalCost());

                                pstBillingRecItem.setLong(FLD_BILLING_TYPE_ID, billingrecitem.getBillingTypeId());



				pstBillingRecItem.update();

				return billingrecitem.getOID();



			}

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingRecItem(0),DBException.UNKNOWN); 

		}

		return 0;

	}



	public static long deleteExc(long oid) throws DBException{ 

		try{ 

			PstBillingRecItem pstBillingRecItem = new PstBillingRecItem(oid);

			pstBillingRecItem.delete();

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingRecItem(0),DBException.UNKNOWN); 

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

			String sql = "SELECT * FROM " + TBL_BILLING_REC_ITEM; 

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

				BillingRecItem billingrecitem = new BillingRecItem();

				resultToObject(rs, billingrecitem);

				lists.add(billingrecitem);

			}

            System.out.println(".....sql Rec...."+sql);

			rs.close();

			return lists;



		}catch(Exception e) {

			System.out.println(e);

		}finally {

			DBResultSet.close(dbrs);

		}

			return new Vector();

	}



	public static void resultToObject(ResultSet rs, BillingRecItem billingrecitem){

		try{

			billingrecitem.setOID(rs.getLong(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_BILLING_REC_ITEM_ID]));

			billingrecitem.setBillingTypeItemId(rs.getLong(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_BILLING_TYPE_ITEM_ID]));

			billingrecitem.setBillingRecId(rs.getLong(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_BILLING_REC_ID]));

			billingrecitem.setQuantity(rs.getDouble(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_QUANTITY]));

			billingrecitem.setSellingPrice(rs.getDouble(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_SELLING_PRICE]));

			billingrecitem.setAmount(rs.getDouble(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_AMOUNT]));

			billingrecitem.setItemCode(rs.getString(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_ITEM_CODE]));

			billingrecitem.setItemName(rs.getString(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_ITEM_NAME]));

			//billingrecitem.setItemCost(rs.getDouble(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_ITEM_COST]));

			billingrecitem.setDescription(rs.getString(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_DESCRIPTION]));

                        billingrecitem.setTotalService(rs.getDouble(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_TOTAL_SERVICE]));

                        billingrecitem.setTotalTax(rs.getDouble(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_TOTAL_TAX]));

                        billingrecitem.setTotalAllRate(rs.getDouble(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_TOTAL_ALL_RATE]));

                        billingrecitem.setType(rs.getInt(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_TYPE]));

                        billingrecitem.setCostPerItem(rs.getDouble(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_COST_PER_ITEM]));

                        billingrecitem.setTotalCost(rs.getDouble(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_TOTAL_COST]));

                        billingrecitem.setBillingTypeId(rs.getLong(PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_BILLING_TYPE_ID]));



		}catch(Exception e){ }

	}



	public static boolean checkOID(long billingRecItemId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_BILLING_REC_ITEM + " WHERE " + 

						PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_BILLING_REC_ITEM_ID] + " = " + billingRecItemId;



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

			String sql = "SELECT COUNT("+ PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_BILLING_REC_ITEM_ID] + ") FROM " + TBL_BILLING_REC_ITEM;

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

			  	   BillingRecItem billingrecitem = (BillingRecItem)list.get(ls);

				   if(oid == billingrecitem.getOID())

					  found=true;

			  }

		  }

		}

		if((start >= size) && (size > 0))

		    start = start - recordToGet;



		return start;

	}





    public static double countAllAmount(long billingRecId){

    	DBResultSet dbrs = null;

		try{

			String sql = "SELECT SUM("+fieldNames[FLD_AMOUNT]+") FROM " + TBL_BILLING_REC_ITEM + " WHERE " +

						 fieldNames[FLD_BILLING_REC_ID] + " = " + billingRecId;



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

            double count = 0;

			while(rs.next()) {

                count = rs.getDouble(1);

            }



			rs.close();

            return count;

		}catch(Exception e){

			System.out.println("err : "+e.toString());

		}finally{

			DBResultSet.close(dbrs);

		}

        return 0;

    }

//update opie-eyek 15-08-2012
    public static double countAllPrice(long billingRecId){

    	DBResultSet dbrs = null;

		try{

			String sql = "SELECT SUM("+fieldNames[FLD_SELLING_PRICE]+"*"+fieldNames[FLD_QUANTITY]+") FROM " + TBL_BILLING_REC_ITEM + " WHERE " +

						 fieldNames[FLD_BILLING_REC_ID] + " = " + billingRecId;



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

            double count = 0;

			while(rs.next()) {

                count = rs.getDouble(1);

            }



			rs.close();

            return count;

		}catch(Exception e){

			System.out.println("err : "+e.toString());

		}finally{

			DBResultSet.close(dbrs);

		}

        return 0;

    }

    

    public static double countAllTax(long billingRecId){

    	DBResultSet dbrs = null;

		try{

			String sql = "SELECT SUM("+fieldNames[FLD_TOTAL_TAX]+") FROM " + TBL_BILLING_REC_ITEM + " WHERE " +

						 fieldNames[FLD_BILLING_REC_ID] + " = " + billingRecId;



			dbrs = DBHandler.execQueryResult(sql);

                        System.out.println("sql dari countAllTax ::::::::::::::::::::"+sql);

                        

			ResultSet rs = dbrs.getResultSet();

                        double count = 0;

			while(rs.next()) {

                        count = rs.getDouble(1);

            }



			rs.close();

            return count;

		}catch(Exception e){

			System.out.println("err : "+e.toString());

		}finally{

			DBResultSet.close(dbrs);

		}

        return 0;

    }

    

    public static double countAllService(long billingRecId){

    	DBResultSet dbrs = null;

		try{

			String sql = "SELECT SUM("+fieldNames[FLD_TOTAL_SERVICE]+") FROM " + TBL_BILLING_REC_ITEM + " WHERE " +

						 fieldNames[FLD_BILLING_REC_ID] + " = " + billingRecId;



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

            double count = 0;

			while(rs.next()) {

                count = rs.getDouble(1);

            }



			rs.close();

            return count;

		}catch(Exception e){

			System.out.println("err : "+e.toString());

		}finally{

			DBResultSet.close(dbrs);

		}

        return 0;

    }



    public static int countAllQty(long billingRecId){

    	DBResultSet dbrs = null;

		try{

			String sql = "SELECT SUM("+fieldNames[FLD_QUANTITY]+") FROM " + TBL_BILLING_REC_ITEM + " WHERE " +

						 fieldNames[FLD_BILLING_REC_ID] + " = " + billingRecId;



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

            int count = 0;

			while(rs.next()) {

                count = rs.getInt(1);

            }



			rs.close();

            return count;

		}catch(Exception e){

			System.out.println("err : "+e.toString());

		}finally{

			DBResultSet.close(dbrs);

		}

        return 0;

    }



    public static void deleteItemByBillingRec(long billRecOid)

    {

    	try{

        	String sql = " DELETE FROM "+TBL_BILLING_REC_ITEM+

                		 " 	WHERE "+fieldNames[FLD_BILLING_REC_ID]+

						 " = "+billRecOid;



        	DBHandler.execUpdate(sql);



    	}catch(Exception exc){

        	System.out.println("delete error  "+exc.toString());

    	}

    }





    public static boolean isItemUsed(long itemId){

        String where  = fieldNames[FLD_BILLING_TYPE_ITEM_ID]+"="+itemId;

        int count = getCount(where);

        if(count>0){

            return true;

        }

        return false;

    }

    

    

    public static void updateAllItemAmountToZero(long billingRecOID){


    }

    

    public static BillingRec restoreAllItemAmountToDefault(BillingRec billingRec){

        return new BillingRec();

    }


    public static double sumDiscountPerQty(long oidCashBillMain) {

        DBResultSet dbrs = null;

        double discSum = 0;
        double qtySum=0;
        double result=0;
        try {

            String sql = "";

            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {

                discSum = rs.getDouble(1);

            }

            if(discSum!=0){
                sql =   "SELECT SUM(" + PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_QUANTITY] + ") FROM " + PstBillingRecItem.TBL_BILLING_REC_ITEM +
                        " WHERE " + PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_BILLING_REC_ID] + "=" + oidCashBillMain;
                dbrs = DBHandler.execQueryResult(sql);
                rs = dbrs.getResultSet();

                while (rs.next()) {
                    qtySum = rs.getDouble(1);
                }

                if(qtySum!=0){
                    result=discSum/qtySum;
                }

            }

        } catch (Exception e) {

            System.out.println("Exception e : " + e.toString());

        } finally {

            DBResultSet.close(dbrs);

        }

        return result;

    }




}

