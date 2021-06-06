/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.hanoman.entity.masterdata;

/**
 *
 * @author dimata005
 */

import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.posbo.entity.masterdata.Material;
import com.dimata.posbo.entity.masterdata.PstMaterial;
import java.sql.*;
import java.util.*;

/* package qdep */

import com.dimata.util.lang.I_Language;

import com.dimata.common.db.*;

import com.dimata.qdep.entity.*;


public class PstCustomePackBilling extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

        public static final  String TBL_HNM_PACK_BILLING = "rsv_custome_package_billing";

	public static final  int FLD_PACK_BILLING_ID = 0;

	public static final  int FLD_BILLING_TYPE_ITEM_ID = 1;

	public static final  int FLD_BILLING_TYPE_ID = 2;

	public static final  int FLD_TRAVEL_PACK_TYPE_ID = 3;

	public static final  int FLD_QUANTITY = 4;

	public static final  int FLD_PRICE_RP = 5;

	public static final  int FLD_PRICE_USD = 6;

	public static final  int FLD_TOTAL_PRICE_RP = 7;

	public static final  int FLD_TOTAL_PRICE_USD = 8;

	public static final  int FLD_TYPE = 9;

        public static final  int FLD_PACKBILL_CONTRACT_ID = 10;

        public static final  int FLD_PACKBILL_ROOM_CLASS_ID = 11;

        public static final  int FLD_PACKBILL_USE_DEFAULT = 12;

        public static final  int FLD_DATE_TAKEN = 13;

        public static final  int FLD_TYPE_SALES = 14;

        public static final  int FLD_TYPE_COMPLIMENT = 15;
        
        public static final  int FLD_RESERVATION_ID = 16;

        public static final  int FLD_SERVICE_RP=17;

        public static final int FLD_SERVICE_USD=18;

        public static final int FLD_TAX_RP=19;

        public static final int FLD_TAX_USD=20;

        public static final int FLD_RATE=21;

        public static final int FLD_CONSUME=22;
        
        public static final int FLD_CATEGORY_ID=23;
        
        public static final int FLD_SUBCATEGORY_ID=24;
        public static final int FLD_CURRENCY_TYPE = 25;
        public static final int FLD_DISCOUNT_PERCENTAGE = 26;
        public static final int FLD_DISCOUNT_AMOUNT_RP = 27;
        public static final int FLD_DISCOUNT_AMOUNT_USD = 28;

	public static final  String[] fieldNames = {

		"CUSTOME_PACK_BILLING_ID",

		"BILLING_TYPE_ITEM_ID",

		"BILLING_TYPE_ID",

		"TRAVEL_PACK_TYPE_ID",

		"QUANTITY",

		"PRICE_RP",

		"PRICE_USD",

		"TOTAL_PRICE_RP",

		"TOTAL_PRICE_USD",

		"TYPE",

                "CONTRACT_ID",

                "ROOM_CLASS_ID",

                "USE_DEFAULT",

                "DATE_TAKEN",

                "TYPE_SALES",

                "TYPE_COMPLIMENT",

                "RESERVATION_ID",

                "SERVICE_RP",

                "SERVICE_USD",

                "TAX_RP",

                "TAX_USD",

                "RATE",
                
                "CONSUME",
                
                "CATEGORY_ID",
                
                "SUBCATEGORY_ID",
                "CURRENCY_TYPE",
                "DISCOUNT_PERCENTAGE",
                "DISCOUNT_AMOUNT_RP",
                "DISCOUNT_AMOUNT_USD"

	 };



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_LONG,

		TYPE_LONG,

		TYPE_LONG,

		TYPE_INT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_INT,

                TYPE_LONG,

                TYPE_LONG,

                TYPE_INT,

                TYPE_DATE,

                TYPE_LONG,

                TYPE_LONG,

                TYPE_LONG,

                TYPE_FLOAT,

                TYPE_FLOAT,

                TYPE_FLOAT,

                TYPE_FLOAT,

                TYPE_FLOAT,

                TYPE_INT,
                
                TYPE_LONG,
                
                TYPE_LONG,
                TYPE_LONG,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_FLOAT

	 };


    public static final int TYPE_REGULAR = 0;

    public static final int TYPE_COMPLIMENTARY = 1;

    public static final String[] strType = {"Regular", "Compliment"};


    public static final int USE_DEFAULT_INCLUDE = 0;

    public static final int USE_DEFAULT_EXCLUDE = 1;

    public static final String[] strUseDefault = {"include", "exclude"};


	public PstCustomePackBilling(){

	}



	public PstCustomePackBilling(int i) throws DBException {

		super(new PstCustomePackBilling());

	}



	public PstCustomePackBilling(String sOid) throws DBException {

		super(new PstCustomePackBilling(0));

		if(!locate(sOid))

			throw new DBException(this,DBException.RECORD_NOT_FOUND);

		else

			return;

	}



	public PstCustomePackBilling(long lOid) throws DBException {

		super(new PstCustomePackBilling(0));

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

		return TBL_HNM_PACK_BILLING;

	}



	public String[] getFieldNames(){

		return fieldNames;

	}



	public int[] getFieldTypes(){

		return fieldTypes;

	}



	public String getPersistentName(){

		return new PstCustomePackBilling().getClass().getName();

	}



	public long fetchExc(Entity ent) throws Exception{

		CustomePackBilling customePackBilling = fetchExc(ent.getOID());

		ent = (Entity)customePackBilling;

		return customePackBilling.getOID();

	}



	public long insertExc(Entity ent) throws Exception{

		return insertExc((CustomePackBilling) ent);

	}



	public long updateExc(Entity ent) throws Exception{

		return updateExc((CustomePackBilling) ent);

	}



	public long deleteExc(Entity ent) throws Exception{

		if(ent==null){

			throw new DBException(this,DBException.RECORD_NOT_FOUND);

		}

		return deleteExc(ent.getOID());

	}



	public static CustomePackBilling fetchExc(long oid) throws DBException{

		try{

			CustomePackBilling customePackBilling = new CustomePackBilling();

			PstCustomePackBilling PstCustomePackBilling = new PstCustomePackBilling(oid);

			customePackBilling.setOID(oid);



			customePackBilling.setBillingTypeItemId(PstCustomePackBilling.getlong(FLD_BILLING_TYPE_ITEM_ID));

			customePackBilling.setBillingTypeId(PstCustomePackBilling.getlong(FLD_BILLING_TYPE_ID));

			customePackBilling.setTravelPackTypeId(PstCustomePackBilling.getlong(FLD_TRAVEL_PACK_TYPE_ID));

			customePackBilling.setQuantity(PstCustomePackBilling.getInt(FLD_QUANTITY));

			customePackBilling.setPriceRp(PstCustomePackBilling.getdouble(FLD_PRICE_RP));

			customePackBilling.setPriceUsd(PstCustomePackBilling.getdouble(FLD_PRICE_USD));

			customePackBilling.setTotalPriceRp(PstCustomePackBilling.getdouble(FLD_TOTAL_PRICE_RP));

			customePackBilling.setTotalPriceUsd(PstCustomePackBilling.getdouble(FLD_TOTAL_PRICE_USD));

			customePackBilling.setType(PstCustomePackBilling.getInt(FLD_TYPE));

                        customePackBilling.setContractId(PstCustomePackBilling.getlong(FLD_PACKBILL_CONTRACT_ID));

                        customePackBilling.setRoomClassId(PstCustomePackBilling.getlong(FLD_PACKBILL_ROOM_CLASS_ID));

                        customePackBilling.setUseDefault(PstCustomePackBilling.getInt(FLD_PACKBILL_USE_DEFAULT));

                        
                        customePackBilling.setTakenDate(PstCustomePackBilling.getDate(FLD_DATE_TAKEN));
                        customePackBilling.setTypeSales(PstCustomePackBilling.getlong(FLD_TYPE_SALES));
                        customePackBilling.setTypeCompliment(PstCustomePackBilling.getlong(FLD_TYPE_COMPLIMENT));
                        customePackBilling.setReservationId(PstCustomePackBilling.getlong(FLD_RESERVATION_ID));

                        customePackBilling.setServiceRp(PstCustomePackBilling.getdouble(FLD_SERVICE_RP));
                        customePackBilling.setServiceUsd(PstCustomePackBilling.getdouble(FLD_SERVICE_USD));
                        customePackBilling.setTaxRp(PstCustomePackBilling.getdouble(FLD_TAX_RP));
                        customePackBilling.setTaxUsd(PstCustomePackBilling.getdouble(FLD_TAX_USD));

                        customePackBilling.setRate(PstCustomePackBilling.getdouble(FLD_RATE));

                        customePackBilling.setConsume(PstCustomePackBilling.getInt(FLD_CONSUME));
                        try {
                            customePackBilling.setSubcategoryId(PstCustomePackBilling.getlong(FLD_SUBCATEGORY_ID));
                        } catch (Exception e) {
                            customePackBilling.setSubcategoryId(0);
                        }
                        
                        try {
                            customePackBilling.setCategoryId(PstCustomePackBilling.getlong(FLD_CATEGORY_ID));
                        } catch (Exception e) {
                            customePackBilling.setCategoryId(0);
                        }
                        
                        customePackBilling.setCurrencyType(PstCustomePackBilling.getlong(FLD_CURRENCY_TYPE));
                        customePackBilling.setDiscPct(PstCustomePackBilling.getdouble(FLD_DISCOUNT_PERCENTAGE));
                        customePackBilling.setDisc(PstCustomePackBilling.getdouble(FLD_DISCOUNT_AMOUNT_RP));
                        customePackBilling.setDisc$(PstCustomePackBilling.getdouble(FLD_DISCOUNT_AMOUNT_USD));

			return customePackBilling;

		}catch(DBException dbe){

			throw dbe;

		}catch(Exception e){

			throw new DBException(new PstCustomePackBilling(0),DBException.UNKNOWN);

		}

	}



	public static long insertExc(CustomePackBilling customePackBilling) throws DBException{

		try{

			PstCustomePackBilling PstCustomePackBilling = new PstCustomePackBilling(0);



			PstCustomePackBilling.setLong(FLD_BILLING_TYPE_ITEM_ID, customePackBilling.getBillingTypeItemId());

			PstCustomePackBilling.setLong(FLD_BILLING_TYPE_ID, customePackBilling.getBillingTypeId());

			PstCustomePackBilling.setLong(FLD_TRAVEL_PACK_TYPE_ID, customePackBilling.getTravelPackTypeId());

			PstCustomePackBilling.setInt(FLD_QUANTITY, customePackBilling.getQuantity());

			PstCustomePackBilling.setDouble(FLD_PRICE_RP, customePackBilling.getPriceRp());

			PstCustomePackBilling.setDouble(FLD_PRICE_USD, customePackBilling.getPriceUsd());

			PstCustomePackBilling.setDouble(FLD_TOTAL_PRICE_RP, customePackBilling.getTotalPriceRp());

			PstCustomePackBilling.setDouble(FLD_TOTAL_PRICE_USD, customePackBilling.getTotalPriceUsd());

			PstCustomePackBilling.setInt(FLD_TYPE, customePackBilling.getType());



                        PstCustomePackBilling.setLong(FLD_PACKBILL_CONTRACT_ID, customePackBilling.getContractId());

                        PstCustomePackBilling.setLong(FLD_PACKBILL_ROOM_CLASS_ID, customePackBilling.getRoomClassId());

                        PstCustomePackBilling.setInt(FLD_PACKBILL_USE_DEFAULT, customePackBilling.getUseDefault());

                        PstCustomePackBilling.setDate(FLD_DATE_TAKEN, customePackBilling.getTakenDate());

                        PstCustomePackBilling.setLong(FLD_TYPE_SALES, customePackBilling.getTypeSales());

                        PstCustomePackBilling.setLong(FLD_TYPE_COMPLIMENT, customePackBilling.getTypeCompliment());

                        PstCustomePackBilling.setLong(FLD_RESERVATION_ID, customePackBilling.getReservationId());

                        PstCustomePackBilling.setDouble(FLD_SERVICE_RP, customePackBilling.getServiceRp());

                        PstCustomePackBilling.setDouble(FLD_SERVICE_USD, customePackBilling.getServiceUsd());

                        PstCustomePackBilling.setDouble(FLD_TAX_RP, customePackBilling.getTaxRp());

                        PstCustomePackBilling.setDouble(FLD_TAX_USD, customePackBilling.getTaxUsd());

                        PstCustomePackBilling.setDouble(FLD_RATE, customePackBilling.getRate());

                        PstCustomePackBilling.setInt(FLD_CONSUME, customePackBilling.getConsume());
                        
                        PstCustomePackBilling.setLong(FLD_CATEGORY_ID, customePackBilling.getCategoryId());
                        
                        PstCustomePackBilling.setLong(FLD_SUBCATEGORY_ID, customePackBilling.getSubcategoryId());
                        
			PstCustomePackBilling.setLong(FLD_CURRENCY_TYPE, customePackBilling.getCurrencyType());
                        PstCustomePackBilling.setDouble(FLD_DISCOUNT_PERCENTAGE, customePackBilling.getDiscPct());
                        PstCustomePackBilling.setDouble(FLD_DISCOUNT_AMOUNT_RP, customePackBilling.getDisc());
                        PstCustomePackBilling.setDouble(FLD_DISCOUNT_AMOUNT_USD, customePackBilling.getDisc$());
                        PstCustomePackBilling.insert();

			customePackBilling.setOID(PstCustomePackBilling.getlong(FLD_PACK_BILLING_ID));

		}catch(DBException dbe){

			throw dbe;

		}catch(Exception e){

			throw new DBException(new PstCustomePackBilling(0),DBException.UNKNOWN);

		}

		return customePackBilling.getOID();

	}



	public static long updateExc(CustomePackBilling customePackBilling) throws DBException{

		try{

			if(customePackBilling.getOID() != 0){

				PstCustomePackBilling PstCustomePackBilling = new PstCustomePackBilling(customePackBilling.getOID());



				PstCustomePackBilling.setLong(FLD_BILLING_TYPE_ITEM_ID, customePackBilling.getBillingTypeItemId());

				PstCustomePackBilling.setLong(FLD_BILLING_TYPE_ID, customePackBilling.getBillingTypeId());

				PstCustomePackBilling.setLong(FLD_TRAVEL_PACK_TYPE_ID, customePackBilling.getTravelPackTypeId());

				PstCustomePackBilling.setInt(FLD_QUANTITY, customePackBilling.getQuantity());

				PstCustomePackBilling.setDouble(FLD_PRICE_RP, customePackBilling.getPriceRp());

				PstCustomePackBilling.setDouble(FLD_PRICE_USD, customePackBilling.getPriceUsd());

				PstCustomePackBilling.setDouble(FLD_TOTAL_PRICE_RP, customePackBilling.getTotalPriceRp());

				PstCustomePackBilling.setDouble(FLD_TOTAL_PRICE_USD, customePackBilling.getTotalPriceUsd());

				PstCustomePackBilling.setInt(FLD_TYPE, customePackBilling.getType());

                                PstCustomePackBilling.setLong(FLD_PACKBILL_CONTRACT_ID, customePackBilling.getContractId());

                                PstCustomePackBilling.setLong(FLD_PACKBILL_ROOM_CLASS_ID, customePackBilling.getRoomClassId());

                                PstCustomePackBilling.setInt(FLD_PACKBILL_USE_DEFAULT, customePackBilling.getUseDefault());

                                PstCustomePackBilling.setDate(FLD_DATE_TAKEN, customePackBilling.getTakenDate());

                                PstCustomePackBilling.setLong(FLD_TYPE_SALES,customePackBilling.getTypeSales());

                                PstCustomePackBilling.setLong(FLD_TYPE_COMPLIMENT,customePackBilling.getTypeCompliment());

                                PstCustomePackBilling.setLong(FLD_RESERVATION_ID,customePackBilling.getReservationId());

                                PstCustomePackBilling.setDouble(FLD_SERVICE_RP, customePackBilling.getServiceRp());

                                PstCustomePackBilling.setDouble(FLD_SERVICE_USD, customePackBilling.getServiceUsd());

                                PstCustomePackBilling.setDouble(FLD_TAX_RP, customePackBilling.getTaxRp());

                                PstCustomePackBilling.setDouble(FLD_TAX_USD, customePackBilling.getTaxUsd());
                                
                                PstCustomePackBilling.setDouble(FLD_RATE, customePackBilling.getRate());

                                PstCustomePackBilling.setInt(FLD_CONSUME, customePackBilling.getConsume());
                                
                                PstCustomePackBilling.setLong(FLD_CATEGORY_ID, customePackBilling.getCategoryId());
                        
                                PstCustomePackBilling.setLong(FLD_SUBCATEGORY_ID, customePackBilling.getSubcategoryId());
                                
				PstCustomePackBilling.setLong(FLD_CURRENCY_TYPE, customePackBilling.getCurrencyType());
                                PstCustomePackBilling.setDouble(FLD_DISCOUNT_PERCENTAGE, customePackBilling.getDiscPct());
                                PstCustomePackBilling.setDouble(FLD_DISCOUNT_AMOUNT_RP, customePackBilling.getDisc());
                                PstCustomePackBilling.setDouble(FLD_DISCOUNT_AMOUNT_USD, customePackBilling.getDisc$());
                                PstCustomePackBilling.update();

				return customePackBilling.getOID();

			}

		}catch(DBException dbe){

			throw dbe;

		}catch(Exception e){

			throw new DBException(new PstCustomePackBilling(0),DBException.UNKNOWN);

		}

		return 0;

	}



	public static long deleteExc(long oid) throws DBException{

		try{

			PstCustomePackBilling PstCustomePackBilling = new PstCustomePackBilling(oid);

			PstCustomePackBilling.delete();

		}catch(DBException dbe){

			throw dbe;

		}catch(Exception e){

			throw new DBException(new PstCustomePackBilling(0),DBException.UNKNOWN);

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

			String sql = "SELECT * FROM " + TBL_HNM_PACK_BILLING;

			if(whereClause != null && whereClause.length() > 0)

				sql = sql + " WHERE " + whereClause;

			if(order != null && order.length() > 0)

				sql = sql + " ORDER BY " + order;

			if(limitStart == 0 && recordToGet == 0)

				sql = sql + "";

			else

				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;



                        System.out.println("ini sql dari customePackBilling yah");

                        System.out.println(sql);

                        System.out.println("====");

			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) {

				CustomePackBilling customePackBilling = new CustomePackBilling();

				resultToObject(rs, customePackBilling);

				lists.add(customePackBilling);

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


        public static Vector listJoin(int limitStart,int recordToGet, String whereClause, String order){

		Vector lists = new Vector();

		DBResultSet dbrs = null;

		try {
			String sql = " SELECT lc.NAME, lc.LOCATION_ID, pm.NAME, pm.MATERIAL_ID, bg.NAME AS CATEGORY_NAME, prk.NAME as SUBCATEGORY_NAME, cpb.* FROM " + TBL_HNM_PACK_BILLING+" AS cpb "+
                                     " INNER JOIN "+PstLocation.TBL_P2_LOCATION+" AS lc "+
                                     " ON lc."+PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]+"=cpb."+fieldNames[FLD_BILLING_TYPE_ID]+
                                     " LEFT JOIN "+PstMaterial.TBL_MATERIAL+" AS pm "+
                                     " ON pm."+PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]+"=cpb."+fieldNames[FLD_BILLING_TYPE_ITEM_ID]+
                                     " LEFT JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" AS bg "+
                                     " ON bg."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID]+"=cpb."+fieldNames[FLD_CATEGORY_ID]+
                                     " LEFT JOIN "+PstMerk.TBL_MAT_MERK+" AS prk "+
                                     " ON prk."+PstMerk.fieldNames[PstMerk.FLD_MERK_ID]+"=cpb."+fieldNames[FLD_SUBCATEGORY_ID];

			if(whereClause != null && whereClause.length() > 0)

				sql = sql + " WHERE " + whereClause;

			if(order != null && order.length() > 0)

				sql = sql + " ORDER BY " + order;

			if(limitStart == 0 && recordToGet == 0)

				sql = sql + "";

			else

				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;



                        System.out.println("ini sql dari customePackBilling yah");

                        System.out.println(sql);

                        System.out.println("====");

			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) {
                                Vector custome = new Vector();
                                
				CustomePackBilling customePackBilling = new CustomePackBilling();
                                Material material = new Material();
                                Location location = new Location();
                                BillingItemGroup category =  new BillingItemGroup();
                                Merk subCategory = new Merk();
                                
                                
                                resultToObject(rs, customePackBilling);

                                material.setOID(rs.getLong("pm."+PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]));
                                material.setName(rs.getString("pm."+PstMaterial.fieldNames[PstMaterial.FLD_NAME]));

                                location.setOID(rs.getLong("lc."+PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]));
                                location.setName(rs.getString("lc."+PstLocation.fieldNames[PstLocation.FLD_NAME]));
                                
                                category.setGroupName(rs.getString("CATEGORY_NAME"));
                                subCategory.setName(rs.getString("SUBCATEGORY_NAME"));
                                
                                custome.add(customePackBilling);
                                custome.add(material);
                                custome.add(location);
                                custome.add(category);
                                custome.add(subCategory);
                                
				lists.add(custome);

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
        
        public static Vector listJoinSchedule(int limitStart,int recordToGet, String whereClause, String order){

            Vector lists = new Vector();

            DBResultSet dbrs = null;

            try {
                    String sql = " SELECT lc.NAME, lc.LOCATION_ID, pm.NAME, pm.MATERIAL_ID, bg.NAME AS CATEGORY_NAME, prk.NAME as SUBCATEGORY_NAME, cpb.*, cpbs."+PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_CUSTOM_SCHEDULE_ID]+" FROM " + TBL_HNM_PACK_BILLING+" AS cpb "+
                                 " INNER JOIN "+PstLocation.TBL_P2_LOCATION+" AS lc "+
                                 " ON lc."+PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]+"=cpb."+fieldNames[FLD_BILLING_TYPE_ID]+
                                 " LEFT JOIN "+PstMaterial.TBL_MATERIAL+" AS pm "+
                                 " ON pm."+PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]+"=cpb."+fieldNames[FLD_BILLING_TYPE_ITEM_ID]+
                                 " LEFT JOIN "+PstBillingItemGroup.TBL_BILLING_ITEM_GROUP+" AS bg "+
                                 " ON bg."+PstBillingItemGroup.fieldNames[PstBillingItemGroup.FLD_BILLING_ITEM_GROUP_ID]+"=cpb."+fieldNames[FLD_CATEGORY_ID]+
                                 " LEFT JOIN "+PstMerk.TBL_MAT_MERK+" AS prk "+
                                 " ON prk."+PstMerk.fieldNames[PstMerk.FLD_MERK_ID]+"=cpb."+fieldNames[FLD_SUBCATEGORY_ID] +
                                 " LEFT JOIN "+PstCustomePackageSchedule.TBL_CUSTOME_PACKAGE_SCHEDULE+" AS cpbs "
                                 + " ON cpbs."+PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_CUSTOME_PACK_BILLING_ID]+" = cpb."+fieldNames[FLD_PACK_BILLING_ID]+"";

                    if(whereClause != null && whereClause.length() > 0)

                            sql = sql + " WHERE " + whereClause;

                    if(order != null && order.length() > 0)

                            sql = sql + " ORDER BY " + order;

                    if(limitStart == 0 && recordToGet == 0)

                            sql = sql + "";

                    else

                            sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;



                    System.out.println("ini sql dari customePackBilling yah");

                    System.out.println(sql);

                    System.out.println("====");

                    dbrs = DBHandler.execQueryResult(sql);

                    ResultSet rs = dbrs.getResultSet();

                    while(rs.next()) {
                            Vector custome = new Vector();

                            CustomePackBilling customePackBilling = new CustomePackBilling();
                            Material material = new Material();
                            Location location = new Location();
                            BillingItemGroup category =  new BillingItemGroup();
                            Merk subCategory = new Merk();
                            CustomePackageSchedule customePackageSchedule = new CustomePackageSchedule();

                            resultToObject(rs, customePackBilling);

                            material.setOID(rs.getLong("pm."+PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]));
                            material.setName(rs.getString("pm."+PstMaterial.fieldNames[PstMaterial.FLD_NAME]));

                            location.setOID(rs.getLong("lc."+PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]));
                            location.setName(rs.getString("lc."+PstLocation.fieldNames[PstLocation.FLD_NAME]));

                            category.setGroupName(rs.getString("CATEGORY_NAME"));
                            subCategory.setName(rs.getString("SUBCATEGORY_NAME"));
                            customePackageSchedule.setOID(rs.getLong(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_CUSTOM_SCHEDULE_ID]));
                            try {
                                customePackageSchedule.setStartDate(rs.getString(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_START_DATE]));
                                customePackageSchedule.setEndDate(rs.getString(PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_END_DATE]));
                            } catch (Exception e) {
                            }
                            

                            custome.add(customePackBilling);
                            custome.add(material);
                            custome.add(location);
                            custome.add(category);
                            custome.add(subCategory);
                            custome.add(customePackageSchedule);
                            lists.add(custome);

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

	private static void resultToObject(ResultSet rs, CustomePackBilling customePackBilling){

		try{

			customePackBilling.setOID(rs.getLong(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACK_BILLING_ID]));

			customePackBilling.setBillingTypeItemId(rs.getLong(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_BILLING_TYPE_ITEM_ID]));

			customePackBilling.setBillingTypeId(rs.getLong(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_BILLING_TYPE_ID]));

			customePackBilling.setTravelPackTypeId(rs.getLong(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_TRAVEL_PACK_TYPE_ID]));

			customePackBilling.setQuantity(rs.getInt(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_QUANTITY]));

			customePackBilling.setPriceRp(rs.getDouble(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PRICE_RP]));

			customePackBilling.setPriceUsd(rs.getDouble(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PRICE_USD]));

			customePackBilling.setTotalPriceRp(rs.getDouble(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_TOTAL_PRICE_RP]));

			customePackBilling.setTotalPriceUsd(rs.getDouble(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_TOTAL_PRICE_USD]));

			customePackBilling.setType(rs.getInt(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_TYPE]));

                        customePackBilling.setContractId(rs.getLong(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACKBILL_CONTRACT_ID]));

                        customePackBilling.setRoomClassId(rs.getLong(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACKBILL_ROOM_CLASS_ID]));

                        customePackBilling.setUseDefault(rs.getInt(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACKBILL_USE_DEFAULT]));

                        customePackBilling.setTakenDate(rs.getDate(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_DATE_TAKEN]));

                        customePackBilling.setTypeSales(rs.getLong(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_TYPE_SALES]));

                        customePackBilling.setTypeCompliment(rs.getLong(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_TYPE_COMPLIMENT]));

                        customePackBilling.setReservationId(rs.getLong(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_RESERVATION_ID]));

                        customePackBilling.setServiceRp(rs.getDouble(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_SERVICE_RP]));

                        customePackBilling.setServiceUsd(rs.getDouble(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_SERVICE_USD]));

                        customePackBilling.setTaxRp(rs.getDouble(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_TAX_RP]));

                        customePackBilling.setTaxUsd(rs.getDouble(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_TAX_USD]));
                        
                        customePackBilling.setRate(rs.getDouble(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_RATE]));

                        customePackBilling.setConsume(rs.getInt(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_CONSUME]));

                        customePackBilling.setCurrencyType(rs.getLong(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_CURRENCY_TYPE]));
                        customePackBilling.setDiscPct(rs.getDouble(fieldNames[FLD_DISCOUNT_PERCENTAGE]));
                        customePackBilling.setDisc(rs.getDouble(fieldNames[FLD_DISCOUNT_AMOUNT_RP]));
                        customePackBilling.setDisc$(rs.getDouble(fieldNames[FLD_DISCOUNT_AMOUNT_USD]));
                }catch(Exception e){ }

	}



	public static boolean checkOID(long packBillingId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_HNM_PACK_BILLING + " WHERE " +

						PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACK_BILLING_ID] + " = " + packBillingId;



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

			String sql = "SELECT COUNT("+ PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACK_BILLING_ID] + ") FROM " + TBL_HNM_PACK_BILLING;

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


	public static double getTotalBillRp(long contractId, long roomClassId){

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT SUM("+ PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_TOTAL_PRICE_RP] + ") FROM " + TBL_HNM_PACK_BILLING+
                                " WHERE "+PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACKBILL_CONTRACT_ID]+"='"+contractId+"' AND "+
                                        PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACKBILL_ROOM_CLASS_ID]+"='"+roomClassId+"'";


			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

			double totalBill = 0.0;

			while(rs.next()) { totalBill = rs.getDouble(1); }

			rs.close();

			return totalBill;

		}catch(Exception e) {

			return 0;

		}finally {

			DBResultSet.close(dbrs);
		}
	}



	public static double getTotalBillUsd(long contractId, long roomClassId){

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT SUM("+ PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_TOTAL_PRICE_USD] + ") FROM " + TBL_HNM_PACK_BILLING+
                                " WHERE "+PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACKBILL_CONTRACT_ID]+"='"+contractId+"' AND "+
                                        PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACKBILL_ROOM_CLASS_ID]+"='"+roomClassId+"'";


			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

			double totalBill = 0.0;

			while(rs.next()) { totalBill = rs.getDouble(1); }

			rs.close();

			return totalBill;

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

			  	   CustomePackBilling customePackBilling = (CustomePackBilling)list.get(ls);

				   if(oid == customePackBilling.getOID())

					  found=true;

			  }

		  }

		}

		if((start >= size) && (size > 0))

		    start = start - recordToGet;



		return start;

	}





        public static long insertPackBillingByContract(long newContract, long parentContract){

            if(newContract!=0 && parentContract!=0){

                String where = PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACKBILL_CONTRACT_ID]+"="+parentContract;

                Vector temp = PstCustomePackBilling.list(0,0, where, null);

                if(temp!=null && temp.size()>0){

                    for(int i=0; i<temp.size(); i++){

                        try{

                            CustomePackBilling pb = (CustomePackBilling)temp.get(i);

                            pb.setContractId(newContract);

                            PstCustomePackBilling.insertExc(pb);

                        }

                        catch(Exception e){

                            System.out.println(e.toString());

                        }

                    }

                }

            }



            return newContract;

        }



        public static long deletePackBillingByContract(long cntrOID){

            if(cntrOID!=0){

                try{

                    String sql = "DELETE FROM "+PstCustomePackBilling.TBL_HNM_PACK_BILLING+

                        " WHERE "+PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_PACKBILL_CONTRACT_ID]+"="+cntrOID;
                    
                    DBHandler.execUpdate(sql);

                }

                catch(Exception ex){

                    System.out.println(ex.toString());

                }



            }



            return cntrOID;

        }



        public static long deleteIn(String where){


                try{

                    String sql = "DELETE FROM "+PstCustomePackBilling.TBL_HNM_PACK_BILLING+

                        " WHERE " + where;

                    DBHandler.execUpdate(sql);

                }

                catch(Exception ex){

                    System.out.println(ex.toString());

                }

            return 0;

        }


         public static Vector listBillingToAdjusment(int limitStart,int recordToGet, String whereClause, String order){

		Vector lists = new Vector();

		DBResultSet dbrs = null;

		try {
			String sql = " SELECT BILLING_TYPE_ID, RATE, SUM(TOTAL_PRICE_RP) AS TOTAL_RP, SUM(SERVICE_RP) AS SERVICE_RP, SUM(TAX_RP) AS TAX_RP, SUM(TOTAL_PRICE_USD) AS TOTAL_USD, SUM(SERVICE_USD) AS SERVICE_USD, SUM(TAX_USD) AS TAX_USD "+
                                     " FROM rsv_custome_package_billing ";

			if(whereClause != null && whereClause.length() > 0)

				sql = sql + " WHERE " + whereClause;

			if(order != null && order.length() > 0)

				sql = sql + " ORDER BY " + order;

			if(limitStart == 0 && recordToGet == 0)

				sql = sql + "";

			else

				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

                        sql=sql+" GROUP BY BILLING_TYPE_ID ";

			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) {
                            
                            CustomePackBilling customePackBilling = new CustomePackBilling();
                            
                            customePackBilling.setBillingTypeId(rs.getLong("BILLING_TYPE_ID"));
                            customePackBilling.setRate(rs.getDouble("RATE"));
                            customePackBilling.setTotalPriceRp(rs.getDouble("TOTAL_RP"));
                            customePackBilling.setServiceRp(rs.getDouble("SERVICE_RP"));
                            customePackBilling.setTaxRp(rs.getDouble("TAX_RP"));

                            customePackBilling.setTotalPriceUsd(rs.getDouble("TOTAL_USD"));
                            customePackBilling.setServiceUsd(rs.getDouble("SERVICE_USD"));
                            customePackBilling.setTaxUsd(rs.getDouble("TAX_USD"));

                            lists.add(customePackBilling);

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

