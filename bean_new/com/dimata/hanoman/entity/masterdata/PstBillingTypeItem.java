

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



import com.dimata.harisma.entity.masterdata.*;

import com.dimata.hanoman.entity.search.SrcBillingTypeItem;

import com.dimata.hanoman.entity.cashier.*;



//implementasi integrasi dengan cashier dan pos

import com.dimata.interfaces.BOPos.I_Catalog;

import com.dimata.ObjLink.BOPos.CatalogLink;

//import com.dimata.common.entity.currency.CurrencyType;

//import com.dimata.common.entity.currency.PstCurrencyType;

import com.dimata.common.entity.payment.CurrencyType;

import com.dimata.common.entity.payment.PriceType;

import com.dimata.common.entity.payment.PstCurrencyType;

import com.dimata.common.entity.payment.PstPriceType;

import com.dimata.common.entity.payment.PstStandartRate;

import com.dimata.common.entity.payment.StandartRate;

import com.dimata.hanoman.entity.system.PstSystemProperty;

import com.dimata.posbo.entity.masterdata.PriceTypeMapping;

import com.dimata.posbo.entity.masterdata.PstPriceTypeMapping;





public class PstBillingTypeItem extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language, I_Catalog {



	//public static final  String TBL_BILLING_TYPE_ITEM = "BILLING_TYPE_ITEM";

    public static final  String TBL_BILLING_TYPE_ITEM = "pos_material";



	public static final  int FLD_BILLING_TYPE_ITEM_ID = 0;

	public static final  int FLD_BILLING_TYPE_ID = 1;

	public static final  int FLD_ITEM_CODE = 2;

	public static final  int FLD_ITEM_NAME = 3;

	public static final  int FLD_SELLING_PRICE = 4;

	public static final  int FLD_ITEM_COST = 5;

	public static final  int FLD_DESCRIPTION = 6;

        public static final  int FLD_BILLING_ITEM_GROUP_ID = 7;

        public static final  int FLD_SELLING_PRICE_USD = 8;

        public static final  int FLD_ITEM_COST_USD = 9;

        public static final  int FLD_TYPE = 10;

        public static final  int FLD_MERK_ID = 11;

        public static final  int FLD_SUB_CATEGORY_ID = 12;

        public static final  int FLD_DEFAULT_SELL_UNIT_ID = 13;

        public static final  int FLD_DEFAULT_PRICE = 14;

        public static final  int FLD_DEFAULT_PRICE_CURRENCY_ID = 15;

        public static final  int FLD_SUPPLIER_ID = 16;

        public static final  int FLD_DEFAULT_SUPPLIER_TYPE = 17;

        public static final  int FLD_BUY_UNIT_ID = 18;        
        



	public static final  String[] fieldNames = {

		"MATERIAL_ID",

		"LOCATION_ID",

		"SKU",        

                "NAME",

		"SELLING_PRICE",

		"AVERAGE_PRICE",

		"DESCRIPTION",

                "CATEGORY_ID",

                "DEFAULT_COST_CURRENCY_ID",

                "ITEM_COST_USD",

                "ITEM_TYPE",

                "MERK_ID",

                "SUB_CATEGORY_ID",

                "DEFAULT_STOCK_UNIT_ID",

                "DEFAULT_PRICE",

                "DEFAULT_PRICE_CURRENCY_ID",

                "SUPPLIER_ID",

                "DEFAULT_SUPPLIER_TYPE",

                "BUY_UNIT_ID"
	 }; 



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_LONG,

		TYPE_STRING,

		TYPE_STRING,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_STRING,

                TYPE_LONG,

                TYPE_FLOAT,

                TYPE_FLOAT,

                TYPE_INT,

                TYPE_LONG, 

                TYPE_LONG,

                TYPE_LONG,

                TYPE_INT,

                TYPE_LONG,

                TYPE_LONG,

                TYPE_INT,

                TYPE_LONG

	 };



    public static final int TYPE_REGULAR 		= 0;

    public static final int TYPE_ADJUSTMENT  	= 1;

    public static final int TYPE_INCLUDE		= 2;//breakfast



	public static final String[] typeStr = {"Regular", "Adjustment", "Include In Reservation"};



	public PstBillingTypeItem(){

	}



	public PstBillingTypeItem(int i) throws DBException { 

		super(new PstBillingTypeItem()); 

	}



	public PstBillingTypeItem(String sOid) throws DBException { 

		super(new PstBillingTypeItem(0)); 

		if(!locate(sOid)) 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		else 

			return; 

	}



	public PstBillingTypeItem(long lOid) throws DBException { 

		super(new PstBillingTypeItem(0)); 

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

		return TBL_BILLING_TYPE_ITEM;

	}



	public String[] getFieldNames(){ 

		return fieldNames; 

	}



	public int[] getFieldTypes(){ 

		return fieldTypes; 

	}



	public String getPersistentName(){ 

		return new PstBillingTypeItem().getClass().getName(); 

	}



	public long fetchExc(Entity ent) throws Exception{ 

		BillingTypeItem billingtypeitem = fetchExc(ent.getOID()); 

		ent = (Entity)billingtypeitem; 

		return billingtypeitem.getOID(); 

	}



	public long insertExc(Entity ent) throws Exception{ 

		return insertExc((BillingTypeItem) ent); 

	}



	public long updateExc(Entity ent) throws Exception{ 

		return updateExc((BillingTypeItem) ent); 

	}



	public long deleteExc(Entity ent) throws Exception{ 

		if(ent==null){ 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		} 

		return deleteExc(ent.getOID()); 

	}



	public static BillingTypeItem fetchExc(long oid) throws DBException{ 

		try{ 

			BillingTypeItem billingtypeitem = new BillingTypeItem();

			PstBillingTypeItem pstBillingTypeItem = new PstBillingTypeItem(oid); 

			billingtypeitem.setOID(oid);



			billingtypeitem.setBillingTypeId(pstBillingTypeItem.getlong(FLD_BILLING_TYPE_ID));

			billingtypeitem.setItemCode(pstBillingTypeItem.getString(FLD_ITEM_CODE));

			billingtypeitem.setItemName(pstBillingTypeItem.getString(FLD_ITEM_NAME));

			billingtypeitem.setSellingPrice(pstBillingTypeItem.getdouble(FLD_SELLING_PRICE));

			billingtypeitem.setItemCost(pstBillingTypeItem.getdouble(FLD_ITEM_COST));

			billingtypeitem.setDescription(pstBillingTypeItem.getString(FLD_DESCRIPTION));

                        billingtypeitem.setBillingItemGroupId(pstBillingTypeItem.getlong(FLD_BILLING_ITEM_GROUP_ID));

                        billingtypeitem.setSellingPriceUsd(pstBillingTypeItem.getdouble(FLD_SELLING_PRICE_USD));

                        billingtypeitem.setItemCostUsd(pstBillingTypeItem.getdouble(FLD_ITEM_COST_USD));

                        billingtypeitem.setType(pstBillingTypeItem.getInt(FLD_TYPE));

                        

                        billingtypeitem.setMerkId(pstBillingTypeItem.getlong(FLD_MERK_ID));

                        billingtypeitem.setSubCategoryId(pstBillingTypeItem.getlong(FLD_SUB_CATEGORY_ID));

                        billingtypeitem.setDefaultSellUnitId(pstBillingTypeItem.getlong(FLD_DEFAULT_SELL_UNIT_ID));

                        billingtypeitem.setDefaultPrice(pstBillingTypeItem.getInt(FLD_DEFAULT_PRICE));

                        billingtypeitem.setDefaultPriceCurrencyId(pstBillingTypeItem.getlong(FLD_DEFAULT_PRICE_CURRENCY_ID));

                        billingtypeitem.setSupplierId(pstBillingTypeItem.getlong(FLD_SUPPLIER_ID));

                        billingtypeitem.setDefaultSupplierType(pstBillingTypeItem.getInt(FLD_DEFAULT_SUPPLIER_TYPE));

                        billingtypeitem.setBuyUnitId(pstBillingTypeItem.getlong(FLD_BUY_UNIT_ID));

                        





			return billingtypeitem; 

                        

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingTypeItem(0),DBException.UNKNOWN); 

		} 

	}



	public static long insertExc(BillingTypeItem billingtypeitem) throws DBException{ 

		try{ 

			PstBillingTypeItem pstBillingTypeItem = new PstBillingTypeItem(0);



			pstBillingTypeItem.setLong(FLD_BILLING_TYPE_ID, billingtypeitem.getBillingTypeId());

			pstBillingTypeItem.setString(FLD_ITEM_CODE, billingtypeitem.getItemCode());

			pstBillingTypeItem.setString(FLD_ITEM_NAME, billingtypeitem.getItemName());

			pstBillingTypeItem.setDouble(FLD_SELLING_PRICE, billingtypeitem.getSellingPrice());

			pstBillingTypeItem.setDouble(FLD_ITEM_COST, billingtypeitem.getItemCost());

			pstBillingTypeItem.setString(FLD_DESCRIPTION, billingtypeitem.getDescription());

                        pstBillingTypeItem.setLong(FLD_BILLING_ITEM_GROUP_ID, billingtypeitem.getBillingItemGroupId());

                        pstBillingTypeItem.setDouble(FLD_SELLING_PRICE_USD, billingtypeitem.getSellingPriceUsd());

                        pstBillingTypeItem.setDouble(FLD_ITEM_COST_USD, billingtypeitem.getItemCostUsd());

                        pstBillingTypeItem.setInt(FLD_TYPE, billingtypeitem.getType());

                        

                        pstBillingTypeItem.setLong(FLD_MERK_ID, billingtypeitem.getMerkId());

                        pstBillingTypeItem.setLong(FLD_SUB_CATEGORY_ID, billingtypeitem.getSubCategoryId());

                        pstBillingTypeItem.setLong(FLD_DEFAULT_SELL_UNIT_ID, billingtypeitem.getDefaultSellUnitId());

                        pstBillingTypeItem.setInt(FLD_DEFAULT_PRICE, billingtypeitem.getDefaultPrice());

                        pstBillingTypeItem.setLong(FLD_DEFAULT_PRICE_CURRENCY_ID, billingtypeitem.getDefaultPriceCurrencyId());

                        pstBillingTypeItem.setLong(FLD_SUPPLIER_ID, billingtypeitem.getSupplierId());

                        pstBillingTypeItem.setInt(FLD_DEFAULT_SUPPLIER_TYPE, billingtypeitem.getDefaultSupplierType());

                        pstBillingTypeItem.setLong(FLD_BUY_UNIT_ID, billingtypeitem.getBuyUnitId());

			pstBillingTypeItem.insert(); 

			billingtypeitem.setOID(pstBillingTypeItem.getlong(FLD_BILLING_TYPE_ITEM_ID));

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingTypeItem(0),DBException.UNKNOWN); 

		}

		return billingtypeitem.getOID();

	}



	public static long updateExc(BillingTypeItem billingtypeitem) throws DBException{ 

		try{ 

			if(billingtypeitem.getOID() != 0){ 

				PstBillingTypeItem pstBillingTypeItem = new PstBillingTypeItem(billingtypeitem.getOID());



				pstBillingTypeItem.setLong(FLD_BILLING_TYPE_ID, billingtypeitem.getBillingTypeId());

				pstBillingTypeItem.setString(FLD_ITEM_CODE, billingtypeitem.getItemCode());

				pstBillingTypeItem.setString(FLD_ITEM_NAME, billingtypeitem.getItemName());

				pstBillingTypeItem.setDouble(FLD_SELLING_PRICE, billingtypeitem.getSellingPrice());

				pstBillingTypeItem.setDouble(FLD_ITEM_COST, billingtypeitem.getItemCost());

				pstBillingTypeItem.setString(FLD_DESCRIPTION, billingtypeitem.getDescription());

                                pstBillingTypeItem.setLong(FLD_BILLING_ITEM_GROUP_ID, billingtypeitem.getBillingItemGroupId());

                                pstBillingTypeItem.setDouble(FLD_SELLING_PRICE_USD, billingtypeitem.getSellingPriceUsd());

                                pstBillingTypeItem.setDouble(FLD_ITEM_COST_USD, billingtypeitem.getItemCostUsd());

                                pstBillingTypeItem.setInt(FLD_TYPE, billingtypeitem.getType());

                                

                                pstBillingTypeItem.setLong(FLD_MERK_ID, billingtypeitem.getMerkId());

                                pstBillingTypeItem.setLong(FLD_SUB_CATEGORY_ID, billingtypeitem.getSubCategoryId());

                                pstBillingTypeItem.setLong(FLD_DEFAULT_SELL_UNIT_ID, billingtypeitem.getDefaultSellUnitId());

                                pstBillingTypeItem.setInt(FLD_DEFAULT_PRICE, billingtypeitem.getDefaultPrice());

                                pstBillingTypeItem.setLong(FLD_DEFAULT_PRICE_CURRENCY_ID, billingtypeitem.getDefaultPriceCurrencyId());

                                pstBillingTypeItem.setLong(FLD_SUPPLIER_ID, billingtypeitem.getSupplierId());

                                pstBillingTypeItem.setInt(FLD_DEFAULT_SUPPLIER_TYPE, billingtypeitem.getDefaultSupplierType());

                                pstBillingTypeItem.setLong(FLD_BUY_UNIT_ID, billingtypeitem.getBuyUnitId());



				pstBillingTypeItem.update(); 

				return billingtypeitem.getOID();



			}

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingTypeItem(0),DBException.UNKNOWN); 

		}

		return 0;

	}



	public static long deleteExc(long oid) throws DBException{ 

		try{ 

			PstBillingTypeItem pstBillingTypeItem = new PstBillingTypeItem(oid);

			pstBillingTypeItem.delete();

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstBillingTypeItem(0),DBException.UNKNOWN); 

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

			String sql = "SELECT * FROM " + TBL_BILLING_TYPE_ITEM; 

			if(whereClause != null && whereClause.length() > 0)

				sql = sql + " WHERE " + whereClause;

			if(order != null && order.length() > 0)

				sql = sql + " ORDER BY " + order;

			if(limitStart == 0 && recordToGet == 0)

				sql = sql + "";

			else

				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;



                        System.out.println(".......sql......"+sql);



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) {

				BillingTypeItem billingtypeitem = new BillingTypeItem();

				resultToObject(rs, billingtypeitem);

				lists.add(billingtypeitem);

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



    public static Vector listBillItem(int limitStart,int recordToGet, String whereClause, String order){

		Vector lists = new Vector();

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT * FROM " + TBL_BILLING_TYPE_ITEM;

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

				BillingTypeItem billingtypeitem = new BillingTypeItem();



                resultToObject(rs, billingtypeitem);

				lists.add(billingtypeitem);

			}

            //System.out.println(".......sql......"+sql);

                rs.close();

			return lists;



		}catch(Exception e) {

			System.out.println(e);

		}finally {

			DBResultSet.close(dbrs);

		}

			return new Vector();

	}



    public static int countBiliingList(){

        DBResultSet dbrs = null;



        int result = 0;



        try{

            String sql = "SELECT COUNT("+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE]+") FROM "+PstBillingTypeItem.TBL_BILLING_TYPE_ITEM+" AS BTI ";

            System.out.println(sql);



            dbrs = DBHandler.execQueryResult(sql);



            ResultSet rs = dbrs.getResultSet();



            while(rs.next()){

                result = rs.getInt(1);

            }



            rs.close();

        }

        catch(Exception e){

			DBResultSet.close(dbrs);

        }

        finally{

            DBResultSet.close(dbrs);

        }



        return result;



    }



    public static Vector listBilling(SrcBillingTypeItem srcBillType, int limitStart,int recordToGet, String whereClause, String order){

		Vector lists = new Vector();

		DBResultSet dbrs = null;

		try {

			//String sql = "SELECT * FROM " + TBL_BILLING_TYPE_ITEM;

                        String sql = "SELECT DISTINCT BTI.*,BIG.* FROM "+PstBillingTypeItem.TBL_BILLING_TYPE_ITEM+" AS BTI "+

                                     "INNER JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" AS BIG "+

                                     " WHERE BTI."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID]+

                                     " = BIG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID];



                        if(whereClause != null && whereClause.length() > 0)

                                            sql = sql + " AND " + whereClause;

                        if(srcBillType.getGroup().length()>0){

                        sql = sql + " AND (BIG."+ PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_GROUP_NAME]+" LIKE \"%"+srcBillType.getGroup()+"%\")";

                        }

                        if(srcBillType.getItemCode().length()>0){

                        sql = sql + " AND (BTI."+ PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE]+" LIKE \"%"+srcBillType.getItemCode()+"%\")";

                        }

                        if(srcBillType.getItemNane().length()>0){

                        sql = sql + " AND (BTI."+ PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_NAME]+" LIKE \"%"+srcBillType.getItemNane()+"%\")";

                        }





			if(order != null && order.length() > 0)

				sql = sql + " ORDER BY " + order;

			if(limitStart == 0 && recordToGet == 0)

				sql = sql + "";

			else

				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) {

				BillingTypeItem billingtypeitem = new BillingTypeItem();

				resultToObject(rs, billingtypeitem);

				lists.add(billingtypeitem);

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



    public static Vector listBillingItem(int limitStart,int recordToGet, String strcode, String strname, long lngcat, String whereClause, String order){

		Vector lists = new Vector();

		DBResultSet dbrs = null;

		try {

                    String sql = "SELECT DISTINCT BTI.*,BIG.* FROM "+PstBillingTypeItem.TBL_BILLING_TYPE_ITEM+" AS BTI "+

                                 "INNER JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" AS BIG "+

                                 " ON BTI."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID]+

                                 " = BIG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID];



                    String sqlcode = "";

                    if (strcode != null && strcode.length() > 0) {

                        sqlcode = " (BTI." + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE] + " LIKE \"%" + strcode + "%\")";

                    }



                    String sqlname = "";

                    if (strname != null && strname.length() > 0) {

                       sqlname = "(BTI." + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_NAME] + " LIKE \"%" + strname + "%\")";

                    }



                    String sqlcat = "";

                    if (lngcat != 0) {

                        sqlcat = " ( BIG." + PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID] + " = " + lngcat + ")";

                    }





                    if (sqlcode.length() > 0 && sqlname.length() > 0 && sqlcat.length() > 0) {

                        sql = sql + " WHERE ( " + sqlcode +" AND " +sqlname + " AND " + sqlcat + " )";

                    } else {

                        if (sqlcode.length() > 0) {

                            sql = sql + " WHERE " + sqlcode;

                        } else {

                            if (sqlname.length() > 0) {

                                sql = sql + " WHERE " + sqlname;

                            } else{

                                if(sqlcat.length() > 0){

                                    sql = sql + " WHERE " + sqlcat;

                                }

                            }

                        }

                    }



                    if(whereClause != null && whereClause.length() > 0){

                        if (sqlcode.length() > 0 || sqlname.length() > 0 || sqlcat.length() > 0) {

                                                sql = sql + " AND " + whereClause;

                            }

                        else{

                            sql = sql + " WHERE " + whereClause;

                        }

                        }



                                if(order != null && order.length() > 0)

                                        sql = sql + " ORDER BY " + order;

                                if(limitStart == 0 && recordToGet == 0)

                                        sql = sql + "";

                                else

                                        sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;



                    System.out.println(sql);



                                dbrs = DBHandler.execQueryResult(sql);

                                ResultSet rs = dbrs.getResultSet();

                                while(rs.next()) {

                                        BillingTypeItem billingtypeitem = new BillingTypeItem();

                                        resultToObject(rs, billingtypeitem);

                                        lists.add(billingtypeitem);

                                }

                    System.out.println(".....sql billing :"+sql);

                                rs.close();

                                return lists;



                        }catch(Exception e) {

                                System.out.println(e);

                        }finally {

                                DBResultSet.close(dbrs);

                        }

                                return new Vector();

	}

    public static Vector listBillingItemSellItemOnly(int limitStart,int recordToGet, String strcode, String strname, long lngcat, String whereClause, String order){

		Vector lists = new Vector();

		DBResultSet dbrs = null;

		try {

                    String sql = "SELECT DISTINCT BTI.*,BIG.* FROM "+PstBillingTypeItem.TBL_BILLING_TYPE_ITEM+" AS BTI "+

                                 " INNER JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" AS BIG "+

                                 " ON BTI."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID]+

                                 " = BIG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID]+

                                 " INNER JOIN "+PstItemOutlet.TBL_ITEM_OUTLET+" AS PL "+

                                 " ON PL."+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID]+

                                 " = BTI."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID];

                    String sqlcode = "";

                    if (strcode != null && strcode.length() > 0) {

                        sqlcode = " (BTI." + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE] + " LIKE \"%" + strcode + "%\")";

                    }



                    String sqlname = "";

                    if (strname != null && strname.length() > 0) {

                       sqlname = "(BTI." + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_NAME] + " LIKE \"%" + strname + "%\")";

                    }



                    String sqlcat = "";

                    if (lngcat != 0) {

                        sqlcat = " ( BIG." + PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID] + " = " + lngcat + ")";

                    }





                    if (sqlcode.length() > 0 && sqlname.length() > 0 && sqlcat.length() > 0) {

                        sql = sql + " WHERE ( " + sqlcode +" AND " +sqlname + " AND " + sqlcat + " )";

                    } else {

                        if (sqlcode.length() > 0) {

                            sql = sql + " WHERE " + sqlcode;

                        } else {

                            if (sqlname.length() > 0) {

                                sql = sql + " WHERE " + sqlname;

                            } else{

                                if(sqlcat.length() > 0){

                                    sql = sql + " WHERE " + sqlcat;

                                }

                            }

                        }

                    }



                    if(whereClause != null && whereClause.length() > 0){

                        if (sqlcode.length() > 0 || sqlname.length() > 0 || sqlcat.length() > 0) {

                                                sql = sql + " AND " + whereClause;

                            }

                        else{

                            sql = sql + " WHERE " + whereClause;

                        }

                        }



                                if(order != null && order.length() > 0)

                                        sql = sql + " ORDER BY " + order;

                                if(limitStart == 0 && recordToGet == 0)

                                        sql = sql + "";

                                else

                                        sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;



                    System.out.println(sql);



                                dbrs = DBHandler.execQueryResult(sql);

                                ResultSet rs = dbrs.getResultSet();

                                while(rs.next()) {

                                        BillingTypeItem billingtypeitem = new BillingTypeItem();

                                        resultToObject(rs, billingtypeitem);

                                        lists.add(billingtypeitem);

                                }

                    System.out.println(".....sql billing :"+sql);

                                rs.close();

                                return lists;



                        }catch(Exception e) {

                                System.out.println(e);

                        }finally {

                                DBResultSet.close(dbrs);

                        }

                                return new Vector();

	}

    public static int getCountBillingItem(String strcode,String strname, long lngcat, String whereClause){

		DBResultSet dbrs = null;

                int count = 0;

		try {

                    String sql = "SELECT COUNT("+ PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID] + ") "+

                         " FROM "+PstBillingTypeItem.TBL_BILLING_TYPE_ITEM+" AS BTI "+

                         " INNER JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" AS BIG "+

                         " ON BTI."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID]+

                         " = BIG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID];



                        

                        String sqlcode = "";

                        if (strcode != null && strcode.length() > 0) {

                            sqlcode = " (BTI." + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE] + " LIKE \"%" + strcode + "%\")";

                        }



                        String sqlname = "";

                        if (strname != null && strname.length() > 0) {

                           sqlname = "(BTI." + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_NAME] + " LIKE \"%" + strname + "%\")";

                        }



                        String sqlcat = "";

                        if (lngcat != 0) {

                           sqlcat = "( BIG." + PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID] + " = " + lngcat + ")";

                        }



                        if (sqlcode.length() > 0 && sqlname.length() > 0 && sqlcat.length() > 0) {

                            sql = sql + " WHERE ( " + sqlcode +" AND " +sqlname +"AND " +sqlcat + " )";

                        } else {

                            if (sqlcode.length() > 0) {

                                sql = sql + " WHERE " + sqlcode;

                            } else {

                                if (sqlname.length() > 0) {

                                    sql = sql + " WHERE " + sqlname;

                                }else{

                                    if (sqlcat.length() > 0) {

                                        sql = sql + " WHERE " + sqlcat;

                                    }

                                }

                            }

                        }



                        if(whereClause != null && whereClause.length() > 0){

                            if (sqlcode.length() > 0 || sqlname.length() > 0 || sqlcat.length() > 0) {

                                                    sql = sql + " AND " + whereClause;

                                }

                            else{

                                sql = sql + " WHERE " + whereClause;

                            }

                        }



                                    //if(order != null && order.length() > 0)

                                    //	sql = sql + " ORDER BY " + order;

                                    //if(limitStart == 0 && recordToGet == 0)

                            //		sql = sql + "";

                            //	else

                            //		sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;



                        System.out.println(sql);



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();



			//int count = 0;

			while(rs.next()) { 

                            count = rs.getInt(1); 

                        }

                        

			rs.close();

			

		}catch(Exception e) {

			return 0;

		}finally {

			DBResultSet.close(dbrs);

		}

                

              return count;  

                

	}

public static int getCountBillingItemSellItemOnly(String strcode,String strname, long lngcat, String whereClause){

		DBResultSet dbrs = null;

                int count = 0;

		try {

                    String sql = "SELECT COUNT( DISTINCT BTI."+ PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID] + ") "+

                         " FROM "+PstBillingTypeItem.TBL_BILLING_TYPE_ITEM+" AS BTI "+

                         " INNER JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" AS BIG "+

                         " ON BTI."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID]+

                         " = BIG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID]+

                         " INNER JOIN "+PstItemOutlet.TBL_ITEM_OUTLET+" AS PL "+

                         " ON PL."+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID]+

                         " = BTI."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID];





                        String sqlcode = "";

                        if (strcode != null && strcode.length() > 0) {

                            sqlcode = " (BTI." + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE] + " LIKE \"%" + strcode + "%\")";

                        }



                        String sqlname = "";

                        if (strname != null && strname.length() > 0) {

                           sqlname = "(BTI." + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_NAME] + " LIKE \"%" + strname + "%\")";

                        }



                        String sqlcat = "";

                        if (lngcat != 0) {

                           sqlcat = "( BIG." + PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID] + " = " + lngcat + ")";

                        }



                        if (sqlcode.length() > 0 && sqlname.length() > 0 && sqlcat.length() > 0) {

                            sql = sql + " WHERE ( " + sqlcode +" AND " +sqlname +"AND " +sqlcat + " )";

                        } else {

                            if (sqlcode.length() > 0) {

                                sql = sql + " WHERE " + sqlcode;

                            } else {

                                if (sqlname.length() > 0) {

                                    sql = sql + " WHERE " + sqlname;

                                }else{

                                    if (sqlcat.length() > 0) {

                                        sql = sql + " WHERE " + sqlcat;

                                    }

                                }

                            }

                        }



                        if(whereClause != null && whereClause.length() > 0){

                            if (sqlcode.length() > 0 || sqlname.length() > 0 || sqlcat.length() > 0) {

                                                    sql = sql + " AND " + whereClause;

                                }

                            else{

                                sql = sql + " WHERE " + whereClause;

                            }

                        }



                                    //if(order != null && order.length() > 0)

                                    //	sql = sql + " ORDER BY " + order;

                                    //if(limitStart == 0 && recordToGet == 0)

                            //		sql = sql + "";

                            //	else

                            //		sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;



                        System.out.println(sql);



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();



			//int count = 0;

			while(rs.next()) {

                            count = rs.getInt(1);

                        }



			rs.close();



		}catch(Exception e) {

			return 0;

		}finally {

			DBResultSet.close(dbrs);

		}



              return count;



	}


    /*

     * updateted by eka, 

     * 03-01-07

     */

    public static Vector listBillingItemByOutlet(int limitStart,int recordToGet, String strcode, String strname,long oidOutlet){

		Vector lists = new Vector();

		DBResultSet dbrs = null;

		try {

                      String sql = /*"SELECT DISTINCT BTI.*,BIG.* FROM "+PstBillingTypeItem.TBL_BILLING_TYPE_ITEM+" AS BTI "+

                         "INNER JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" AS BIG "+

                         " ON BTI."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID]+

                         " = BIG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID];

                         */

               		 " SELECT IT.* "+

                         " FROM "+TBL_BILLING_TYPE_ITEM+" IT "+

                         " INNER JOIN "+PstItemOutlet.TBL_ITEM_OUTLET+" IO "+

                         " ON IO."+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID]+" = IT."+fieldNames[FLD_BILLING_TYPE_ITEM_ID]+

                         " INNER JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" IG "+

                         " ON IT."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID]+"= "+

                         " IG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID];



                        String whereClause = " IO."+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ID]+" = "+ oidOutlet;



                        String sqlcode = "";

                        if (strcode != null && strcode.length() > 0) {

                            sqlcode = " (IT." + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE] + " LIKE \"%" + strcode + "%\")";

                        }



                        String sqlname = "";

                        if (strname != null && strname.length() > 0) {

                           sqlname = "(IT." + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_NAME] + " LIKE \"%" + strname + "%\")";

                        }



                        if (sqlcode.length() > 0 && sqlname.length() > 0) {

                            sql = sql + " WHERE ( " + sqlcode +" AND " +sqlname + " )";

                        } else {

                            if (sqlcode.length() > 0) {

                                sql = sql + " WHERE " + sqlcode;

                            } else {

                                if (sqlname.length() > 0) {

                                    sql = sql + " WHERE " + sqlname;

                                }

                            }

                        }



                        if(whereClause != null && whereClause.length() > 0){

                            if (sqlcode.length() > 0 || sqlname.length() > 0) {

                                                    sql = sql + " AND " + whereClause;

                                }

                            else{

                                sql = sql + " WHERE " + whereClause;

                            }

                        }



			//if(order != null && order.length() > 0)

			sql = sql + " ORDER BY IG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_GROUP_NAME]+", IT."+fieldNames[FLD_ITEM_CODE]+", IT." + fieldNames[FLD_ITEM_NAME];



			if(limitStart == 0 && recordToGet == 0)

				sql = sql + "";

			else

				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;



                        System.out.println(sql);



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) {

				BillingTypeItem billingtypeitem = new BillingTypeItem();

				resultToObject(rs, billingtypeitem);

				lists.add(billingtypeitem);

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



    public static int getCountBillingItemByOutlet(String strcode, String strname,long oidOutlet){



		DBResultSet dbrs = null;

		try {

                    String sql = /*"SELECT DISTINCT BTI.*,BIG.* FROM "+PstBillingTypeItem.TBL_BILLING_TYPE_ITEM+" AS BTI "+

                                 "INNER JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" AS BIG "+

                                 " ON BTI."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID]+

                                 " = BIG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID];

                                 */

                                         " SELECT COUNT(IT."+fieldNames[FLD_BILLING_TYPE_ITEM_ID]+")"+

                                                         " FROM "+TBL_BILLING_TYPE_ITEM+" IT "+

                                                         " INNER JOIN "+PstItemOutlet.TBL_ITEM_OUTLET+" IO "+

                                 " ON IO."+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID]+" = IT."+fieldNames[FLD_BILLING_TYPE_ITEM_ID];





                    String whereClause = " IO."+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ID]+" = "+ oidOutlet;



                    String sqlcode = "";

                    if (strcode != null && strcode.length() > 0) {

                        sqlcode = " (" + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE] + " LIKE \"%" + strcode + "%\")";

                    }



                    String sqlname = "";

                    if (strname != null && strname.length() > 0) {

                       sqlname = "(" + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_NAME] + " LIKE \"%" + strname + "%\")";

                    }



                    if (sqlcode.length() > 0 && sqlname.length() > 0) {

                        sql = sql + " WHERE ( " + sqlcode +" AND " +sqlname + " )";

                    } else {

                        if (sqlcode.length() > 0) {

                            sql = sql + " WHERE " + sqlcode;

                        } else {

                            if (sqlname.length() > 0) {

                                sql = sql + " WHERE " + sqlname;

                            }

                        }

                    }



                    if(whereClause != null && whereClause.length() > 0){

                        if (sqlcode.length() > 0 || sqlname.length() > 0) {

                                                sql = sql + " AND " + whereClause;

                            }

                        else{

                            sql = sql + " WHERE " + whereClause;

                        }

                        }



                                //if(order != null && order.length() > 0)

                                /*sql = sql + " ORDER BY IT." + fieldNames[FLD_ITEM_NAME];



                                if(limitStart == 0 && recordToGet == 0)

                                        sql = sql + "";

                                else

                                        sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

                    */



                    System.out.println(sql);



                                dbrs = DBHandler.execQueryResult(sql);

                                ResultSet rs = dbrs.getResultSet();

                    int count = 0;

                                while(rs.next()) {

                        count = rs.getInt(1);

                                }



                                rs.close();

                                return count;



                        }catch(Exception e) {

                                System.out.println(e);

                        }finally {

                                DBResultSet.close(dbrs);

                        }

                        return 0;

	}







	public static void resultToObject(ResultSet rs, BillingTypeItem billingtypeitem){

		try{

			billingtypeitem.setOID(rs.getLong(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID]));

			billingtypeitem.setBillingTypeId(rs.getLong(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ID]));

			billingtypeitem.setItemCode(rs.getString(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE]));

			billingtypeitem.setItemName(rs.getString(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_NAME]));

			billingtypeitem.setSellingPrice(rs.getDouble(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_SELLING_PRICE]));

			billingtypeitem.setItemCost(rs.getDouble(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_COST]));

			billingtypeitem.setDescription(rs.getString(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_DESCRIPTION]));

                        billingtypeitem.setBillingItemGroupId(rs.getLong(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID]));

                        billingtypeitem.setSellingPriceUsd(rs.getDouble(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_SELLING_PRICE_USD]));

                        billingtypeitem.setItemCostUsd(rs.getDouble(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_COST_USD]));

                        billingtypeitem.setType(rs.getInt(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_TYPE]));

                        billingtypeitem.setMerkId(rs.getLong(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_MERK_ID]));

                        billingtypeitem.setSubCategoryId(rs.getLong(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_SUB_CATEGORY_ID]));

                        billingtypeitem.setDefaultSellUnitId(rs.getLong(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_DEFAULT_SELL_UNIT_ID]));

                        billingtypeitem.setDefaultPrice(rs.getInt(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_DEFAULT_PRICE]));

                        billingtypeitem.setDefaultPriceCurrencyId(rs.getLong(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_DEFAULT_PRICE_CURRENCY_ID]));

                        billingtypeitem.setSupplierId(rs.getLong(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_SUPPLIER_ID]));

                        billingtypeitem.setDefaultSupplierType(rs.getInt(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_DEFAULT_SUPPLIER_TYPE]));

                        billingtypeitem.setBuyUnitId(rs.getLong(PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BUY_UNIT_ID]));



		}catch(Exception e){ }

	}



	public static boolean checkOID(long billingTypeItemId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_BILLING_TYPE_ITEM + " WHERE " + 

						PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID] + " = " + billingTypeItemId;



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

			String sql = "SELECT COUNT("+ PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID] + ") FROM " + TBL_BILLING_TYPE_ITEM;



                        if(whereClause != null && whereClause.length() > 0)

                                            sql = sql + " WHERE " + whereClause;



                                    dbrs = DBHandler.execQueryResult(sql);

                                    ResultSet rs = dbrs.getResultSet();



                                    int count = 0;

                                    while(rs.next()) { count = rs.getInt(1); }



                                    rs.close();

                                    return count;

                }

                catch(Exception e) 

                {

                    return 0;

                }finally 

                {      

                     DBResultSet.close(dbrs);

                }

	}



    public static int getCountList(SrcBillingTypeItem srcBillType,String whereClause){

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT COUNT("+ PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID] + ") "+

                         " FROM "+PstBillingTypeItem.TBL_BILLING_TYPE_ITEM+" AS BTI "+

                         " INNER JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" AS BIG "+

                         " WHERE BTI."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID]+

                         " = BIG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID];



                        if(whereClause != null && whereClause.length() > 0)

                                            sql = sql + " AND " + whereClause;

                        if(srcBillType.getGroup().length()>0){

                        sql = sql + " AND (BIG."+ PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_GROUP_NAME]+" LIKE \"%"+srcBillType.getGroup()+"%\")";

                        }

                        if(srcBillType.getItemCode().length()>0){

                        sql = sql + " AND (BTI."+ PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE]+" LIKE \"%"+srcBillType.getItemCode()+"%\")";

                        }

                        if(srcBillType.getItemNane().length()>0){

                        sql = sql + " AND (BTI."+ PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_NAME]+" LIKE \"%"+srcBillType.getItemNane()+"%\")";

                        }

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



    public static Vector getBillingType(long billTypeItemOid)

    {

        DBResultSet dbrs = null;

		try {

			String sql = " SELECT BT."+PstBillingType.fieldNames[PstBillingType.FLD_BILLING_TYPE_ID]+

                		 ", BT."+PstBillingType.fieldNames[PstBillingType.FLD_USED_VALUE]+

                         ", BT."+PstBillingType.fieldNames[PstBillingType.FLD_SERVICE_PERCENTAGE]+

                         ", BT."+PstBillingType.fieldNames[PstBillingType.FLD_SERVICE_VALUE]+

                         ", BT."+PstBillingType.fieldNames[PstBillingType.FLD_TAX_PERCENTAGE]+

                         ", BT."+PstBillingType.fieldNames[PstBillingType.FLD_TAX_VALUE]+

                         " FROM "+PstBillingType.TBL_BILLING_TYPE + " BT "+

                         //" INNER JOIN "+PstBillingTypeItem.TBL_BILLING_TYPE_ITEM+  " BTI "+

                         //" ON BT."+PstBillingType.fieldNames[PstBillingType.FLD_BILLING_TYPE_ID]+

                         //" = BTI."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ID]+

                         " INNER JOIN "+PstItemOutlet.TBL_ITEM_OUTLET+" IO "+

                         " ON IO."+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ID]+"= BT."+PstBillingType.fieldNames[PstBillingType.FLD_BILLING_TYPE_ID]+

                         " WHERE IO."+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID]+

                         " = "+billTypeItemOid;

                        

                        System.out.println(sql);



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();



			Vector result = new Vector(1,1);

			while(rs.next()) {

                        BillingType billingType = new BillingType();



                        billingType.setOID(rs.getLong(PstBillingType.fieldNames[PstBillingType.FLD_BILLING_TYPE_ID]));

			billingType.setServicePercentage(rs.getDouble(PstBillingType.fieldNames[PstBillingType.FLD_SERVICE_PERCENTAGE]));

			billingType.setTaxPercentage(rs.getDouble(PstBillingType.fieldNames[PstBillingType.FLD_TAX_PERCENTAGE]));

                        billingType.setUsedValue(rs.getInt(PstBillingType.fieldNames[PstBillingType.FLD_USED_VALUE]));

                        billingType.setServiceValue(rs.getDouble(PstBillingType.fieldNames[PstBillingType.FLD_SERVICE_VALUE]));

                        billingType.setTaxValue(rs.getDouble(PstBillingType.fieldNames[PstBillingType.FLD_TAX_VALUE]));



                result.add(billingType);



            }



			return result;

		}catch(Exception e) {

			return new Vector(1,1);

		}finally {

			DBResultSet.close(dbrs);

		}

    }





    /*

    author : Eka

    implementasi interface utk integrasi hanoman catalog dengan pos catalog

    */

    public long insertCatalog(CatalogLink catLink){

        

        System.out.println("-");

        System.out.println("-");

        System.out.println("-");

        System.out.println("in inserting hanoman item");        



        BillingTypeItem item = new BillingTypeItem();

        item.setBillingItemGroupId(catLink.getItemCategoryId());

        item.setDescription(catLink.getDescription());

        item.setItemName(catLink.getName());

        

        System.out.println("in inserting hanoman item 1");        

        

        item.setItemCode(catLink.getCode());

        if(catLink.getCostUsd()>0 && catLink.getCostRp()==0){

            item.setItemCost(catLink.getCostUsd()*PstStandardRate.getStandardRate());

        }

        else{

            item.setItemCost(catLink.getCostRp());

        }        

        

        System.out.println("in inserting hanoman item 2");        

        

        if(catLink.getCostUsd()==0 && catLink.getCostRp()>0){

            item.setItemCostUsd(catLink.getCostRp()/PstStandardRate.getStandardRate());

        }

        else{

            item.setItemCostUsd(catLink.getCostUsd());

        }

        

        System.out.println("in inserting hanoman item 3");        

        

        if(catLink.getPriceRp()==0 && catLink.getPriceUsd()>0){

            item.setSellingPrice(catLink.getPriceUsd()*PstStandardRate.getStandardRate());

        }

        else{

            item.setSellingPrice(catLink.getPriceRp());

        }

        

        System.out.println("in inserting hanoman item 4");        

        

        if(catLink.getPriceRp()>0 && catLink.getPriceUsd()==0){

            item.setSellingPriceUsd(catLink.getPriceRp()/PstStandardRate.getStandardRate());

        }

        else{

            item.setSellingPriceUsd(catLink.getPriceUsd());

        }

        item.setType(TYPE_REGULAR);

        

        System.out.println("in inserting hanoman item 5");        



        long oid = 0;

        try{

			oid = insertExc(item);

                        System.out.println("catalog step 4 .. success insert catalog : "+oid);



                        //set up price mapping

                        if(oid!=0){

                           

                            //set up price mapping

                            if(oid!=0){

                                String strInit = PstSystemProperty.getValueByName("INTEGRATION_CURR_RP");	

                                String where = PstCurrencyType.fieldNames[PstCurrencyType.FLD_CODE]+"='"+strInit+"'";

                                long oidRp = 0;

                                Vector vct = PstCurrencyType.list(0,0, where, null);

                                if(vct!=null && vct.size()>0){

                                    CurrencyType unit = (CurrencyType)vct.get(0);

                                    oidRp = unit.getOID();

                                }                                       

                        

                            //default USD ambil dari master

                            strInit = PstSystemProperty.getValueByName("INTEGRATION_CURR_USD");	

                            where = PstCurrencyType.fieldNames[PstCurrencyType.FLD_CODE]+"='"+strInit+"'";

                            long oidUsd = 0;

                            vct = PstCurrencyType.list(0,0, where, null);

                            if(vct!=null && vct.size()>0){

                                CurrencyType unit = (CurrencyType)vct.get(0);

                                oidUsd = unit.getOID();

                            }



                            System.out.println("catalog step 5 .. process price mapping ");

                            System.out.println("catalog step 6 .. oidUsd :  "+oidUsd);



                            //default price map untuk pembayaran cash

                            strInit = PstSystemProperty.getValueByName("INTEGRATION_PRICE_MAP");	

                            where = PstPriceType.fieldNames[PstPriceType.FLD_CODE]+"='"+strInit+"'";

                            long oidPrcType = 0;

                            vct = PstPriceType.list(0,0, where, null);

                            if(vct!=null && vct.size()>0){

                                PriceType pt = (PriceType)vct.get(0);

                                oidPrcType = pt.getOID();

                            }



                            //default price map untuk pembayaran credit

                            strInit = PstSystemProperty.getValueByName("INTEGRATION_PRICE_MAP_CREDIT");	

                            where = PstPriceType.fieldNames[PstPriceType.FLD_CODE]+"='"+strInit+"'";

                            long oidPrcTypeCredit = 0;

                            vct = PstPriceType.list(0,0, where, null);

                            if(vct!=null && vct.size()>0){

                                PriceType pt = (PriceType)vct.get(0);

                                oidPrcTypeCredit = pt.getOID();

                            }



                            System.out.println("catalog step 7 .. oidPrcType :  "+oidPrcType);



                            StandartRate stRateRp = PstStandartRate.getActiveStandardRate(oidRp);

                            StandartRate stRateUsd = PstStandartRate.getActiveStandardRate(oidUsd);





                            //setup price mapping untuk pembayaran cash

                            try{

                                PriceTypeMapping ptm = new PriceTypeMapping();

                                ptm.setPrice(item.getSellingPrice());

                                ptm.setPriceTypeId(oidPrcType);

                                ptm.setMaterialId(oid);

                                ptm.setStandartRateId(stRateRp.getOID());



                                PstPriceTypeMapping.insertExc(ptm);



                                System.out.println("catalog step 8 .. inserting mapping rp :  ");

                            }

                            catch(Exception e){

                            }



                            try{

                                PriceTypeMapping ptm = new PriceTypeMapping();

                                ptm.setPrice(item.getSellingPriceUsd());

                                ptm.setPriceTypeId(oidPrcType);

                                ptm.setMaterialId(oid);

                                ptm.setStandartRateId(stRateUsd.getOID());



                                PstPriceTypeMapping.insertExc(ptm);



                                System.out.println("catalog step 9 .. inserting mapping usd :  ");

                            }

                            catch(Exception e){

                            }



                            //setup price mapping untuk pembayaran credit

                            try{

                                PriceTypeMapping ptm = new PriceTypeMapping();

                                ptm.setPrice(item.getSellingPrice());

                                ptm.setPriceTypeId(oidPrcTypeCredit);

                                ptm.setMaterialId(oid);

                                ptm.setStandartRateId(stRateRp.getOID());



                                PstPriceTypeMapping.insertExc(ptm);



                                System.out.println("catalog step 8 .. inserting mapping rp :  ");

                            }

                            catch(Exception e){

                            }



                            try{

                                PriceTypeMapping ptm = new PriceTypeMapping();

                                ptm.setPrice(item.getSellingPriceUsd());

                                ptm.setPriceTypeId(oidPrcTypeCredit);

                                ptm.setMaterialId(oid);

                                ptm.setStandartRateId(stRateUsd.getOID());



                                PstPriceTypeMapping.insertExc(ptm);



                                System.out.println("catalog step 9 .. inserting mapping usd :  ");

                                }

                                catch(Exception e){

                                }

                               

                            }

                        }

                        

        }

            catch(Exception e){

            System.out.println("excep : "+e.toString());

        }

        

        oid = synchronizeOID(oid, catLink.getCatalogId());

        

        System.out.println("in inserting hanoman item 6 = oid : "+oid);        



        return insertOutlet(catLink.getStores(), oid);



    }



    public static long insertOutlet(Vector vctOutlet, long oid){

        

        System.out.println("in inserting outlet ");        

        

        if(vctOutlet!=null && vctOutlet!=null){

            for(int i=0; i<vctOutlet.size(); i++){

                long outletOID = Long.parseLong((String)vctOutlet.get(i));

                if(!isExistOnMapping(outletOID, oid)){

                    ItemOutlet io = new ItemOutlet();

                    io.setBillingTypeId(outletOID);

                    io.setBillingTypeItemId(oid);



                    try{

                        PstItemOutlet.insertExc(io);

                    }

                    catch(Exception e){

                    }

                }

            }

        }

        

        System.out.println("in inserting outlet sukses ...");        



        return oid;

    }



    public static long updateOutlets(Vector tempOutlet, long itemOID){



            //deleting outlet item

            deleteItemOutlet(itemOID);

            //inserting outlet item

            return insertOutlet(tempOutlet, itemOID);



        }

        

        public static void deleteItemOutlet(long item){

            String where  = "DELETE FROM "+PstItemOutlet.TBL_ITEM_OUTLET+

                " WHERE "+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID]+"="+item;

            

            try{

                DBHandler.execUpdate(where);

            }

            catch(Exception e){

                System.out.println("exception e : "+ e.toString());

            }



        }



	public static boolean isExistOnMapping(long outletOID, long oid){

        String where = PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ID]+"="+outletOID+

            " AND "+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID]+"="+oid;



        int i = PstItemOutlet.getCount(where);

        if(i>0){

            return true;

        }



        return false;

    }



    public long updateCatalog(CatalogLink catLink){

        

        System.out.println("-");

        System.out.println("-");

        System.out.println("in update item hanoman");

                        



	long oid = 0;



        if(catLink.getCatalogId()!=0){



	    BillingTypeItem item = new BillingTypeItem();

            try{

                item = this.fetchExc(catLink.getCatalogId());

            }

            catch(Exception e){

            }



	        item.setBillingItemGroupId(catLink.getItemCategoryId());

	        item.setDescription(catLink.getDescription());

	        item.setItemName(catLink.getName());

	        item.setItemCode(catLink.getCode());

	        /*item.setItemCost(catLink.getCostRp());

	        item.setItemCostUsd(catLink.getCostUsd());

	        item.setSellingPrice(catLink.getPriceRp());

	        item.setSellingPriceUsd(catLink.getPriceUsd());

                 */

                

                if(catLink.getCostUsd()>0 && catLink.getCostRp()==0){

                    item.setItemCost(catLink.getCostUsd()*PstStandardRate.getStandardRate());

                }

                else{

                    item.setItemCost(catLink.getCostRp());

                }        

                

                if(catLink.getCostUsd()==0 && catLink.getCostRp()>0){

                    item.setItemCostUsd(catLink.getCostRp()/PstStandardRate.getStandardRate());

                }

                else{

                    item.setItemCostUsd(catLink.getCostUsd());

                }

                

                if(catLink.getPriceRp()==0 && catLink.getPriceUsd()>0){

                    item.setSellingPrice(catLink.getPriceUsd()*PstStandardRate.getStandardRate());

                }

                else{

                    item.setSellingPrice(catLink.getPriceRp());

                }

                

                if(catLink.getPriceRp()>0 && catLink.getPriceUsd()==0){

                    item.setSellingPriceUsd(catLink.getPriceRp()/PstStandardRate.getStandardRate());

                }

                else{

                    item.setSellingPriceUsd(catLink.getPriceUsd());

                }

                

                System.out.println("atLink.getPriceRp() : "+catLink.getPriceRp());

                System.out.println("atLink.getPriceUsd() : "+catLink.getPriceUsd());

                System.out.println("item.setSellingPrice() : "+item.getSellingPrice());

                System.out.println("item.getSellingPriceUsd() : "+item.getSellingPriceUsd());

                

	        item.setType(TYPE_REGULAR);



	        try{

				oid = updateExc(item);

	        }

	        catch(Exception e){

	            System.out.println("excep : "+e.toString());

	        }



            updateOutlets(catLink.getStores(), oid);



        }



        return oid;



    }



    public long deleteCatalog(CatalogLink catLink){



        long oid = catLink.getCatalogId();



        if(oid!=0){



            String where  = PstBillingRecItem.fieldNames[PstBillingRecItem.FLD_BILLING_TYPE_ITEM_ID]+"="+oid;

			Vector vct = PstBillingRecItem.list(0,0, where, null);



            if(vct==null || vct.size()<1){

                try{

                    PstBillingTypeItem.deleteExc(oid);

                }

                catch(Exception e){

                    oid = 0;

                }

            }

            else{

                oid = 0;

            }

        }



        return oid;

    }



    public long synchronizeOID(long oidOld, long oidNew){

        String sql = "UPDATE "+TBL_BILLING_TYPE_ITEM+

            " SET "+fieldNames[FLD_BILLING_TYPE_ITEM_ID]+"="+oidNew+

            " WHERE "+fieldNames[FLD_BILLING_TYPE_ITEM_ID]+"="+oidOld;



        try{

            DBHandler.execUpdate(sql);

        }

        catch(Exception e){

            oidNew = 0;

        }



        return oidNew;

    }



    //end implentation integrasi

    

    public static boolean isCodeExist(long oid, String code){

        DBResultSet dbrs = null;

        boolean result = false;

        try{

            String sql = "SELECT COUNT("+fieldNames[FLD_BILLING_TYPE_ITEM_ID]+") "+

                " FROM "+TBL_BILLING_TYPE_ITEM+

                " WHERE "+fieldNames[FLD_ITEM_CODE]+"='"+code+"'"+

                " AND "+fieldNames[FLD_BILLING_TYPE_ITEM_ID]+"!="+oid;

            

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

            String sql = "SELECT COUNT("+fieldNames[FLD_BILLING_TYPE_ITEM_ID]+") "+

                " FROM "+TBL_BILLING_TYPE_ITEM+

                " WHERE "+fieldNames[FLD_ITEM_NAME]+"='"+name+"'"+

                " AND "+fieldNames[FLD_BILLING_TYPE_ITEM_ID]+"!="+oid;

            

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

    

    /*ayu

     *try to find outlet item with paramater CurrencyTypeId and StandarRateId Rupiah

     */

    

    public static long getStandartRateRp(){

        DBResultSet dbrs = null;



        long result = 0;

        

        int rp = Integer.parseInt(PstSystemProperty.getValueByName("CURRENCY_RP"));



        try{

            String sql = "SELECT "+PstStandartRate.fieldNames[PstStandartRate.FLD_STANDART_RATE_ID]+" FROM "+PstStandartRate.TBL_POS_STANDART_RATE+

                         " WHERE "+PstStandartRate.fieldNames[PstStandartRate.FLD_CURRENCY_TYPE_ID]+" = "+rp+" AND "+PstStandartRate.fieldNames[PstStandartRate.FLD_STATUS]+" = 1 ";

            System.out.println(sql);



            dbrs = DBHandler.execQueryResult(sql);



            ResultSet rs = dbrs.getResultSet();



            while(rs.next()){

               StandartRate standar = new StandartRate();



               result = rs.getLong(PstStandartRate.fieldNames[PstStandartRate.FLD_STANDART_RATE_ID]);	



            }



            rs.close();

        }

        catch(Exception e){

			DBResultSet.close(dbrs);

        }

        finally{

            DBResultSet.close(dbrs);

        }



        return result;



    }

    

    public static long getStandartRateUsd(){

        DBResultSet dbrs = null;



        long result = 0;

        

        int usd = Integer.parseInt(PstSystemProperty.getValueByName("CURRENCY_USD"));



        try{

            String sql = "SELECT "+PstStandartRate.fieldNames[PstStandartRate.FLD_STANDART_RATE_ID]+" FROM "+PstStandartRate.TBL_POS_STANDART_RATE+
                         " WHERE "+PstStandartRate.fieldNames[PstStandartRate.FLD_CURRENCY_TYPE_ID]+" = "+usd+
                         " AND "+PstStandartRate.fieldNames[PstStandartRate.FLD_STATUS]+" = 1 ";

            System.out.println(sql);
            dbrs = DBHandler.execQueryResult(sql);

            ResultSet rs = dbrs.getResultSet();



            while(rs.next()){

                StandartRate standar = new StandartRate();



               result = rs.getLong(PstStandartRate.fieldNames[PstStandartRate.FLD_STANDART_RATE_ID]);	

            }



            rs.close();

        }

        catch(Exception e){

			DBResultSet.close(dbrs);

        }

        finally{

            DBResultSet.close(dbrs);

        }



        return result;



    }

    

    public static Vector listBillingItemByOutlet2(int limitStart,int recordToGet, String strcode, String strname,long oidMaterial){

            Vector lists = new Vector();

            DBResultSet dbrs = null;

            

            String pricetype = PstSystemProperty.getValueByName("INTEGRATION_PRICE_MAP");

            try {

                  

                  String sql = 

                     " SELECT PM.*"+

                     " , PTRP."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]+" AS SELLING_PRICE_POS"+

                     " , PTUS."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]+" AS SELLING_PRICE_POS_USD"+

                     " FROM "+TBL_BILLING_TYPE_ITEM+" PM "+

                     " INNER JOIN "+PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING +" PTRP "+

                     " ON PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID]+" = PTRP."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]+

                     " AND PTRP."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID]+" = "+PstBillingTypeItem.getStandartRateRp()+            

                     //" AND PPT."+PstPriceType.fieldNames[PstPriceType.FLD_CODE]+" = '"+pricetype+"'"+

                     " INNER JOIN "+PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING +" PTUS "+

                     " ON PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID]+" = PTUS."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]+

                     " AND PTUS."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID]+" = "+PstBillingTypeItem.getStandartRateUsd()+

                     //" AND PPT."+PstPriceType.fieldNames[PstPriceType.FLD_CODE]+" = '"+pricetype+"'"+

                     " INNER JOIN "+PstPriceType.TBL_POS_PRICE_TYPE +" PPT "+

                     " ON PTUS."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID]+" = PPT."+PstPriceType.fieldNames[PstPriceType.FLD_PRICE_TYPE_ID]+

                     " AND PTRP."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID]+" = PPT."+PstPriceType.fieldNames[PstPriceType.FLD_PRICE_TYPE_ID];

                                                           

                    String whereClause = "";//" PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ID]+" = "+ oidMaterial;

                    

                    if(oidMaterial!=0){

                        whereClause = " (PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID]+" = "+ oidMaterial+")";

                    }



                    String sqlcode = "";

                    if (strcode != null && strcode.length() > 0) {

                        sqlcode = " (" + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE] + " LIKE \"%" + strcode + "%\")";

                    }



                    String sqlname = "";

                    if (strname != null && strname.length() > 0) {

                       sqlname = "(" + PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_NAME] + " LIKE \"%" + strname + "%\")";

                    }



                    if (sqlcode.length() > 0 && sqlname.length() > 0) {

                        sql = sql + " WHERE ( " + sqlcode +" AND " +sqlname + " )";

                    } else {

                        if (sqlcode.length() > 0) {

                            sql = sql + " WHERE " + sqlcode;

                        } else {

                            if (sqlname.length() > 0) {

                                sql = sql + " WHERE " + sqlname;

                            }

                        }

                    }



                    /*

                    if(whereClause != null && whereClause.length() > 0){

                        if (sqlcode.length() > 0 || sqlname.length() > 0) {

                                                sql = sql + " AND " + whereClause;

                            }

                        else{

                            sql = sql + " WHERE " + whereClause;

                        }

                    }

                    */

                    

                    if(whereClause != null && whereClause.length() > 0){

                        sql = sql + " WHERE " + whereClause;

                    }



                    //if(order != null && order.length() > 0)

                    // sql = sql + " ORDER BY IG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_GROUP_NAME]+", IT."+fieldNames[FLD_ITEM_CODE]+", IT." + fieldNames[FLD_ITEM_NAME];



                    if(limitStart == 0 && recordToGet == 0)

                            sql = sql + "";

                    else

                            sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

                   
                    System.out.println("\n\n"+sql+"\n");

                    dbrs = DBHandler.execQueryResult(sql);

                    ResultSet rs = dbrs.getResultSet();                    

                    double sellingP = 0;

                    while(rs.next()) {

                            BillingTypeItem billingtypeitem = new BillingTypeItem();

                            resultToObject(rs, billingtypeitem);

                            sellingP = rs.getDouble("SELLING_PRICE_POS");

                            billingtypeitem.setSellingPrice(sellingP);

                            sellingP = rs.getDouble("SELLING_PRICE_POS_USD");

                            billingtypeitem.setSellingPriceUsd(sellingP);

                            //billingtypeitem.setSellingPrice(rs.getDouble(PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]));

                            lists.add(billingtypeitem);

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

    

    

    public static Vector listBillingItemByOutlet2(int limitStart,int recordToGet, long oidLocation){
            Vector lists = new Vector();
            DBResultSet dbrs = null;

            String pricetype = PstSystemProperty.getValueByName("INTEGRATION_PRICE_MAP");
            String sortItem = PstSystemProperty.getValueByName("SORT_OUTLET_ITEM_BY");
            try {

                  

                  String sql = 

                     " SELECT PM.*"+

                     " , PTRP."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]+" AS SELLING_PRICE_POS"+

                     " , PTUS."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]+" AS SELLING_PRICE_POS_USD"+

                     " FROM "+TBL_BILLING_TYPE_ITEM+" PM "+

                     " INNER JOIN "+PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING +" PTRP "+

                     " ON PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID]+" = PTRP."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]+

                     " AND PTRP."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID]+" = "+PstBillingTypeItem.getStandartRateRp()+            

                     //" AND PPT."+PstPriceType.fieldNames[PstPriceType.FLD_CODE]+" = '"+pricetype+"'"+

                     " INNER JOIN "+PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING +" PTUS "+

                     " ON PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID]+" = PTUS."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]+

                     " AND PTUS."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID]+" = "+PstBillingTypeItem.getStandartRateUsd()+

                     //" AND PPT."+PstPriceType.fieldNames[PstPriceType.FLD_CODE]+" = '"+pricetype+"'"+

                     " INNER JOIN "+PstPriceType.TBL_POS_PRICE_TYPE +" PPT "+

                     " ON PTUS."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID]+" = PPT."+PstPriceType.fieldNames[PstPriceType.FLD_PRICE_TYPE_ID]+

                     " AND PTRP."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID]+" = PPT."+PstPriceType.fieldNames[PstPriceType.FLD_PRICE_TYPE_ID]+

                     " AND PPT."+PstPriceType.fieldNames[PstPriceType.FLD_CODE]+" = '"+pricetype+"'"+

                     " INNER JOIN "+PstItemOutlet.TBL_ITEM_OUTLET+" IL "+

                     " ON IL."+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID]+" = PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID]+
                     " INNER JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" IG ON IG."+
                     PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID]+"= PM."+
                     PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID];

                                                           

                    String whereClause = "";//" PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ID]+" = "+ oidMaterial;

                    

                    if(oidLocation!=0){

                        whereClause = " (IL."+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ID]+" = "+ oidLocation+")";

                    }

                    

                    if(whereClause != null && whereClause.length() > 0){

                        sql = sql + " WHERE " + whereClause+
                                    " AND PTRP.PRICE > 0";

                    }
                    
                    if(sortItem!=null && sortItem.equals("1")){  
                        // order by item group
                        sql = sql + " ORDER BY IG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_GROUP_NAME]
                                +", PM."+fieldNames[FLD_ITEM_CODE]+", PM." + fieldNames[FLD_ITEM_NAME];                    
                        
                    } else if (sortItem!=null && sortItem.equals("2")){ // order by code
                        sql = sql + " ORDER BY PM."+fieldNames[FLD_ITEM_CODE]+
                                ",PM."+fieldNames[FLD_ITEM_NAME];
                    }  else if (sortItem!=null && sortItem.equals("3")){ // order by group, name, code {  
                        // order by item group
                        sql = sql + " ORDER BY IG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_GROUP_NAME]
                                +", PM."+fieldNames[FLD_ITEM_NAME]+", PM." + fieldNames[FLD_ITEM_CODE];                                            
                    } else{
                        // order by name
                        sql = sql + " ORDER BY PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_NAME]+
                        ",PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE];
                    } 

                    if(limitStart == 0 && recordToGet == 0)

                            sql = sql + "";

                    else

                            sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

                    System.out.println("\n\n"+sql+"\n");

                    dbrs = DBHandler.execQueryResult(sql);

                    ResultSet rs = dbrs.getResultSet();                    

                    double sellingP = 0;

                    while(rs.next()) {

                            BillingTypeItem billingtypeitem = new BillingTypeItem();

                            resultToObject(rs, billingtypeitem);

                            sellingP = rs.getDouble("SELLING_PRICE_POS");

                            billingtypeitem.setSellingPrice(sellingP);

                            sellingP = rs.getDouble("SELLING_PRICE_POS_USD");

                            billingtypeitem.setSellingPriceUsd(sellingP);

                            //billingtypeitem.setSellingPrice(rs.getDouble(PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]));

                            lists.add(billingtypeitem);

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


    public static Vector listBillingItemToVoid(int limitStart,int recordToGet, long oidLocation){
            Vector lists = new Vector();
            DBResultSet dbrs = null;

            String pricetype = PstSystemProperty.getValueByName("INTEGRATION_PRICE_MAP");
            String sortItem = PstSystemProperty.getValueByName("SORT_OUTLET_ITEM_BY");
            try {

                  String sql =

                     " SELECT PM.*"+

                     " , PTRP."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]+" AS SELLING_PRICE_POS"+

                     " , PTUS."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]+" AS SELLING_PRICE_POS_USD"+

                     " FROM "+TBL_BILLING_TYPE_ITEM+" PM "+

                     " INNER JOIN "+PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING +" PTRP "+

                     " ON PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID]+" = PTRP."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]+

                     " AND PTRP."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID]+" = "+PstBillingTypeItem.getStandartRateRp()+

                     " INNER JOIN "+PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING +" PTUS "+

                     " ON PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID]+" = PTUS."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]+

                     " AND PTUS."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID]+" = "+PstBillingTypeItem.getStandartRateUsd()+

                     //" AND PPT."+PstPriceType.fieldNames[PstPriceType.FLD_CODE]+" = '"+pricetype+"'"+

                     " INNER JOIN "+PstPriceType.TBL_POS_PRICE_TYPE +" PPT "+

                     " ON PTUS."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID]+" = PPT."+PstPriceType.fieldNames[PstPriceType.FLD_PRICE_TYPE_ID]+

                     " AND PTRP."+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID]+" = PPT."+PstPriceType.fieldNames[PstPriceType.FLD_PRICE_TYPE_ID]+

                     " AND PPT."+PstPriceType.fieldNames[PstPriceType.FLD_CODE]+" = '"+pricetype+"'"+

                     " INNER JOIN "+PstItemOutlet.TBL_ITEM_OUTLET+" IL "+

                     " ON IL."+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ITEM_ID]+" = PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_TYPE_ITEM_ID]+
                     " INNER JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" IG ON IG."+
                     PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID]+"= PM."+
                     PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_BILLING_ITEM_GROUP_ID];

                    String whereClause = "";

                    if(oidLocation!=0){

                        whereClause = " (IL."+PstItemOutlet.fieldNames[PstItemOutlet.FLD_BILLING_TYPE_ID]+" = "+ oidLocation+")";

                    }

                    if(whereClause != null && whereClause.length() > 0){

                        sql = sql + " WHERE " + whereClause;

                    }

                    if(sortItem!=null && sortItem.equals("1")){
                        // order by item group
                        sql = sql + " ORDER BY IG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_GROUP_NAME]
                                +", PM."+fieldNames[FLD_ITEM_CODE]+", PM." + fieldNames[FLD_ITEM_NAME];

                    } else if (sortItem!=null && sortItem.equals("2")){ // order by code
                        sql = sql + " ORDER BY PM."+fieldNames[FLD_ITEM_CODE]+
                                ",PM."+fieldNames[FLD_ITEM_NAME];
                    }  else if (sortItem!=null && sortItem.equals("3")){ // order by group, name, code {
                        // order by item group
                        sql = sql + " ORDER BY IG."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_GROUP_NAME]
                                +", PM."+fieldNames[FLD_ITEM_NAME]+", PM." + fieldNames[FLD_ITEM_CODE];
                    } else{
                        // order by name
                        sql = sql + " ORDER BY PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_NAME]+
                        ",PM."+PstBillingTypeItem.fieldNames[PstBillingTypeItem.FLD_ITEM_CODE];
                    }

                    if(limitStart == 0 && recordToGet == 0)

                            sql = sql + "";

                    else

                            sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

                    System.out.println("\n\n"+sql+"\n");

                    dbrs = DBHandler.execQueryResult(sql);

                    ResultSet rs = dbrs.getResultSet();

                    double sellingP = 0;

                    while(rs.next()) {

                            BillingTypeItem billingtypeitem = new BillingTypeItem();

                            resultToObject(rs, billingtypeitem);

                            sellingP = rs.getDouble("SELLING_PRICE_POS");

                            billingtypeitem.setSellingPrice(sellingP);

                            sellingP = rs.getDouble("SELLING_PRICE_POS_USD");

                            billingtypeitem.setSellingPriceUsd(sellingP);

                            lists.add(billingtypeitem);

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



}

