package com.dimata.cashierweb.entity.masterdata;

import com.dimata.common.entity.system.*;
import com.dimata.ObjLink.BOPos.CatalogLink;
import com.dimata.interfaces.BOPos.I_Catalog;
import com.dimata.interfaces.BOCashier.I_BillingDetail;
import com.dimata.common.db.*;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.qdep.entity.Entity;
import com.dimata.util.lang.I_Language;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.payment.*;
import com.dimata.posbo.entity.masterdata.PstMatMappLocation;
import java.util.Vector;
import java.util.Date;
import java.sql.ResultSet;

public class PstMaterial extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language, I_Catalog {

    //public static final String TBL_MATERIAL = "POS_MATERIAL";
    public static final String TBL_MATERIAL = "pos_material";

    public static final int FLD_MATERIAL_ID = 0;
    public static final int FLD_SKU = 1;
    public static final int FLD_BARCODE = 2;
    public static final int FLD_NAME = 3;
    public static final int FLD_MERK_ID = 4;
    public static final int FLD_CATEGORY_ID = 5;
    public static final int FLD_SUB_CATEGORY_ID = 6;
    public static final int FLD_DEFAULT_STOCK_UNIT_ID = 7;
    public static final int FLD_DEFAULT_PRICE = 8;
    public static final int FLD_DEFAULT_PRICE_CURRENCY_ID = 9;
    public static final int FLD_DEFAULT_COST = 10;
    public static final int FLD_DEFAULT_COST_CURRENCY_ID = 11;
    public static final int FLD_DEFAULT_SUPPLIER_TYPE = 12;
    public static final int FLD_SUPPLIER_ID = 13;
    public static final int FLD_PRICE_TYPE_01 = 14;
    public static final int FLD_PRICE_TYPE_02 = 15;
    public static final int FLD_PRICE_TYPE_03 = 16;
    public static final int FLD_MATERIAL_TYPE = 17;

    // NEW
    public static final int FLD_LAST_DISCOUNT = 18;
    public static final int FLD_LAST_VAT = 19;
    public static final int FLD_CURR_BUY_PRICE = 20;
    public static final int FLD_EXPIRED_DATE = 21;
    public static final int FLD_BUY_UNIT_ID = 22;

    public static final int FLD_PROFIT = 23;
    public static final int FLD_CURR_SELL_PRICE_RECOMENTATION = 24;

    public static final int FLD_AVERAGE_PRICE = 25;
    public static final int FLD_MINIMUM_POINT = 26;
    public static final int FLD_LAST_UPDATE = 27;
    public static final int FLD_REQUIRED_SERIAL_NUMBER = 28;
    public static final int FLD_PROCESS_STATUS = 29;
    public static final int FLD_CONSIGMENT_TYPE = 30;
    public static final int FLD_GONDOLA_CODE = 31;
    //For update sell price
    public static final int FLD_UPDATE_DATE = 32;
    public static final int FLD_LAST_COST_CARGO = 33;

    //new opie 12-06-2012
    //for edit material in cashier
    public static final int FLD_EDIT_MATERIAL = 34;

    //add opie-eyek 20130903
    public static final int FLD_POINT_SALES = 35;
    public static final int FLD_MATERIAL_DESCRIPTION = 36;
    public static final int FLD_COLOR_ID = 37;
    public static final int FLD_SALES_RULE = 38;
    public static final int FLD_RETURN_RULE = 39;

    //added by dewok 20190318 for item picture
    public static final int FLD_MATERIAL_IMAGE = 40;

    public static final String[] fieldNames = {
        "MATERIAL_ID",
        "SKU",
        "BARCODE",
        "NAME",
        "MERK_ID",
        "CATEGORY_ID",
        "SUB_CATEGORY_ID",
        "DEFAULT_STOCK_UNIT_ID",
        "DEFAULT_PRICE",
        "DEFAULT_PRICE_CURRENCY_ID",
        "DEFAULT_COST",
        "DEFAULT_COST_CURRENCY_ID",
        "DEFAULT_SUPPLIER_TYPE",
        "SUPPLIER_ID",
        "PRICE_TYPE_01",
        "PRICE_TYPE_02",
        "PRICE_TYPE_03",
        "MATERIAL_TYPE",
        "LAST_DISCOUNT",
        "LAST_VAT",
        "CURR_BUY_PRICE",
        "EXPIRED_DATE",
        "BUY_UNIT_ID",
        "PROFIT",
        "CURR_SELL_PRICE_RECOMENTATION",
        "AVERAGE_PRICE",
        "MINIMUM_POINT",
        "LAST_UPDATE",
        "REQUIRED_SERIAL_NUMBER",
        "PROCESS_STATUS",
        "CONSIGMENT_TYPE",
        "GONDOLA_CODE",
        //for updated catalog
        "UPDATE_DATE",
        //for last cost cargo
        "LAST_COST_CARGO",
        //for edit material
        "EDIT_MATERIAL",
        "POINT_SALES_PERSON",
        "MATERIAL_DESCRIPTION",
        "COLOR_ID",
        "SALES_RULE",
        "RETURN_RULE",
        "MATERIAL_IMAGE"
    };

    public static final int[] fieldTypes = {
        TYPE_LONG + TYPE_PK + TYPE_ID,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_LONG,
        TYPE_INT,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_FLOAT,
        TYPE_INT,
        TYPE_DATE,
        TYPE_INT,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING,
        //Updated catalog
        TYPE_DATE,
        //last cost cargo
        TYPE_FLOAT,
        //edit material
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING,
        TYPE_LONG,
        TYPE_INT,
        TYPE_INT,
        TYPE_STRING
    };

    public static final int NOT_REQUIRED = 0;
    public static final int REQUIRED = 1;

    public static final String requiredNames[][] = {
        {"Tidak Diperlukan", "Diperlukan"},
        {"Not Required", "Required"}
    };

    //add opie eyek 12-06-2012 untuk edit material
    public static final int NO_EDIT = 0;
    public static final int EDIT_NAME = 1;
    public static final int EDIT_HARGA = 2;
    public static final int EDIT_HARGA_NAME = 3;
    public static final int EDIT_NON_AKTIVE = 4;

    public static final String editNames[][] = {
        {"Tidak Diedit", "Edit Name", "Edit Harga", "Edit Name dan Harga", "Non Aktif"},
        {"No Edit", "Edit Name", "Edit Price", "Edit Name and Price", "Non Aktive"}
    };

    public static final int MAT_TYPE_REGULAR = 0;
    public static final int MAT_TYPE_COMPOSITE = 1;
    public static final int MAT_TYPE_SERVICE = 2;
    public static final String strMaterialType[][] = {
        {"Barang", "Composite", "Jasa"},
        {"Regular", "Composite", "Service"}
    };

    public static final int INSERT = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;

    public static final int CATALOG_TYPE_OTHER = 0;
    public static final int CATALOG_TYPE_CONSIGMENT = 1;
    public static final String strTypeCatalogConsigment[][] = {
        {"Tidak", "Ya"},
        {"No", "Yes"}
    };

    /**
     * variable ini di gunakan untukmembedakan harga yang dipakai pafa saat
     * retur,dispatch
     */
    public static int STATUS_WITH_HPP = 0;
    public static int STATUS_WITH_SELLING_PRICE = 1;
    public static int STATUS_PRICE_CONDITION = STATUS_WITH_SELLING_PRICE;

    //use barcode or sku by mirahu 20120424
    public static int USE_SKU = 0;
    public static int USE_BARCODE = 1;

    public static int SALES_NOT_MANDATORY = 0;
    public static int SALES_MANDATORY = 1;
    public static int SALES_WITH_CONDITION = 2;
    public static final String strSalesRule[][] = {
        {"Tidak Wajib", "Wajib Ada", "Tidak wajib jika sudah ada item wajib lainnya"},
        {"Not Mandatory", "Mandatory", "Not mandatory if another mandatory item already selected"}
    };

    public static int CAN_RETURN = 0;
    public static int CANNOT_RETURN = 1;
    public static final String strSalesReturn[][] = {
        {"Bisa di retur", "Tidak bisa di retur"},
        {"Can Returned", "Can't Returned"}
    };

    public PstMaterial() {
    }

    public PstMaterial(int i) throws DBException {
        super(new PstMaterial());
    }

    public PstMaterial(String sOid) throws DBException {
        super(new PstMaterial(0));
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public PstMaterial(long lOid) throws DBException {
        super(new PstMaterial(0));
        String sOid = "0";
        try {
            sOid = String.valueOf(lOid);
        } catch (Exception e) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        if (!locate(sOid)) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        } else {
            return;
        }
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public String getTableName() {
        return TBL_MATERIAL;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String getPersistentName() {
        return new PstMaterial().getClass().getName();
    }

    public long fetchExc(Entity ent) throws Exception {
        Material material = fetchExc(ent.getOID());
        ent = (Entity) material;
        return material.getOID();
    }

    public long insertExc(Entity ent) throws Exception {
        return insertExc((Material) ent);
    }

    public long updateExc(Entity ent) throws Exception {
        return updateExc((Material) ent);
    }

    public long deleteExc(Entity ent) throws Exception {
        if (ent == null) {
            throw new DBException(this, DBException.RECORD_NOT_FOUND);
        }
        return deleteExc(ent.getOID());
    }

    public static Material fetchExc(long oid) throws DBException {
        try {
            Material material = new Material();
            PstMaterial pstMaterial = new PstMaterial(oid);
            material.setOID(oid);

            material.setSku(pstMaterial.getString(FLD_SKU));
            material.setBarCode(pstMaterial.getString(FLD_BARCODE));
            material.setName(pstMaterial.getString(FLD_NAME));
            material.setMerkId(pstMaterial.getlong(FLD_MERK_ID));
            material.setCategoryId(pstMaterial.getlong(FLD_CATEGORY_ID));

            material.setSubCategoryId(pstMaterial.getlong(FLD_SUB_CATEGORY_ID));
            material.setDefaultStockUnitId(pstMaterial.getlong(FLD_DEFAULT_STOCK_UNIT_ID));
            material.setDefaultPrice(pstMaterial.getdouble(FLD_DEFAULT_PRICE));

            material.setDefaultPriceCurrencyId(pstMaterial.getlong(FLD_DEFAULT_PRICE_CURRENCY_ID));
            material.setDefaultCost(pstMaterial.getdouble(FLD_DEFAULT_COST));
            material.setDefaultCostCurrencyId(pstMaterial.getlong(FLD_DEFAULT_COST_CURRENCY_ID));
            material.setDefaultSupplierType(pstMaterial.getInt(FLD_DEFAULT_SUPPLIER_TYPE));
            material.setSupplierId(pstMaterial.getlong(FLD_SUPPLIER_ID));
            try {
                material.setPriceType01(pstMaterial.getdouble(FLD_PRICE_TYPE_01));
                material.setPriceType02(pstMaterial.getdouble(FLD_PRICE_TYPE_02));
                material.setPriceType03(pstMaterial.getdouble(FLD_PRICE_TYPE_03));
            } catch (Exception exc) {
                material.setPriceType01(0.0);
                material.setPriceType02(0.0);
                material.setPriceType03(0.0);
            }

            try {
                material.setMaterialType(pstMaterial.getInt(FLD_MATERIAL_TYPE));
            } catch (Exception exc) {
            }
            try {
                material.setLastDiscount(pstMaterial.getdouble(FLD_LAST_DISCOUNT));
            } catch (Exception exc) {
            }
            try {
                material.setLastVat(pstMaterial.getdouble(FLD_LAST_VAT));
            } catch (Exception exc) {
            }
            try {
                material.setCurrBuyPrice(pstMaterial.getdouble(FLD_CURR_BUY_PRICE));
            } catch (Exception exc) {
            }
            try {
                material.setExpiredDate(pstMaterial.getDate(FLD_EXPIRED_DATE));
            } catch (Exception exc) {
            }
            try {
                material.setBuyUnitId(pstMaterial.getlong(FLD_BUY_UNIT_ID));
            } catch (Exception exc) {
            }
            try {
                material.setProfit(pstMaterial.getdouble(FLD_PROFIT));
            } catch (Exception exc) {
            }
            try {
                material.setCurrSellPriceRecomentation(pstMaterial.getdouble(FLD_CURR_SELL_PRICE_RECOMENTATION));
            } catch (Exception exc) {
            }
            try {
                material.setAveragePrice(pstMaterial.getdouble(FLD_AVERAGE_PRICE));
            } catch (Exception exc) {
            }
            try {
                material.setMinimumPoint(pstMaterial.getInt(FLD_MINIMUM_POINT));
            } catch (Exception exc) {
            }
            try {
                material.setRequiredSerialNumber(pstMaterial.getInt(FLD_REQUIRED_SERIAL_NUMBER));
            } catch (Exception exc) {
            }
            try {
                material.setLastUpdate(pstMaterial.getDate(FLD_LAST_UPDATE));
            } catch (Exception exc) {
            }
            try {
                material.setProcessStatus(pstMaterial.getInt(FLD_PROCESS_STATUS));
            } catch (Exception exc) {
            }
            try {
                material.setMatTypeConsig(pstMaterial.getInt(FLD_CONSIGMENT_TYPE));
            } catch (Exception exc) {
            }
            try {
                material.setGondolaCode(pstMaterial.getString(FLD_GONDOLA_CODE));
            } catch (Exception exc) {
            }
            //for update date
            // try{material.setUpdateDate(pstMaterial.getDate(FLD_UPDATE_DATE));
            //}catch(Exception exc){}
            //for last cost cargo
            try {
                material.setLastCostCargo(pstMaterial.getdouble(FLD_LAST_COST_CARGO));
            } catch (Exception exc) {
            }
            //for edit material opie 11-06-2012
            try {
                material.setEditMaterial(pstMaterial.getInt(FLD_EDIT_MATERIAL));
            } catch (Exception exc) {
            }

            //for edit material opie 20130903
            try {
                material.setPointSales(pstMaterial.getInt(FLD_POINT_SALES));
            } catch (Exception exc) {
            }

            material.setMaterialDescription(pstMaterial.getString(FLD_MATERIAL_DESCRIPTION));
            material.setPosColor(pstMaterial.getlong(FLD_COLOR_ID));
            material.setSalesRule(pstMaterial.getInt(FLD_SALES_RULE));
            material.setReturnRule(pstMaterial.getInt(FLD_RETURN_RULE));

            //added by dewok 20190318 for item picture
            material.setMaterialImage(pstMaterial.getString(FLD_MATERIAL_IMAGE));

            return material;
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("err : " + e.toString());
            throw new DBException(new PstMaterial(0), DBException.UNKNOWN);
        }
    }

    public static long insertExc(Material material) throws DBException {
        try {
            PstMaterial pstMaterial = new PstMaterial(0);

            pstMaterial.setString(FLD_SKU, material.getSku());
            pstMaterial.setString(FLD_BARCODE, material.getBarCode());
            pstMaterial.setString(FLD_NAME, material.getName());
            pstMaterial.setLong(FLD_MERK_ID, material.getMerkId());
            pstMaterial.setLong(FLD_CATEGORY_ID, material.getCategoryId());

            pstMaterial.setLong(FLD_SUB_CATEGORY_ID, material.getSubCategoryId());
            pstMaterial.setLong(FLD_DEFAULT_STOCK_UNIT_ID, material.getDefaultStockUnitId());
            pstMaterial.setDouble(FLD_DEFAULT_PRICE, material.getDefaultPrice());

            pstMaterial.setLong(FLD_DEFAULT_PRICE_CURRENCY_ID, material.getDefaultPriceCurrencyId());
            pstMaterial.setDouble(FLD_DEFAULT_COST, material.getDefaultCost());
            pstMaterial.setLong(FLD_DEFAULT_COST_CURRENCY_ID, material.getDefaultCostCurrencyId());
            pstMaterial.setInt(FLD_DEFAULT_SUPPLIER_TYPE, material.getDefaultSupplierType());
            pstMaterial.setLong(FLD_SUPPLIER_ID, material.getSupplierId());
            pstMaterial.setDouble(FLD_PRICE_TYPE_01, material.getPriceType01());
            pstMaterial.setDouble(FLD_PRICE_TYPE_02, material.getPriceType02());
            pstMaterial.setDouble(FLD_PRICE_TYPE_03, material.getPriceType03());
            pstMaterial.setInt(FLD_MATERIAL_TYPE, material.getMaterialType());

            // NEW
            pstMaterial.setDouble(FLD_LAST_DISCOUNT, material.getLastDiscount());
            pstMaterial.setDouble(FLD_LAST_VAT, material.getLastVat());
            pstMaterial.setDouble(FLD_CURR_BUY_PRICE, material.getCurrBuyPrice());
            pstMaterial.setDate(FLD_EXPIRED_DATE, material.getExpiredDate());
            pstMaterial.setLong(FLD_BUY_UNIT_ID, material.getBuyUnitId());
            pstMaterial.setDouble(FLD_PROFIT, material.getProfit());
            pstMaterial.setDouble(FLD_CURR_SELL_PRICE_RECOMENTATION, material.getCurrSellPriceRecomentation());

            pstMaterial.setDouble(FLD_AVERAGE_PRICE, material.getAveragePrice());
            pstMaterial.setInt(FLD_MINIMUM_POINT, material.getMinimumPoint());
            pstMaterial.setInt(FLD_REQUIRED_SERIAL_NUMBER, material.getRequiredSerialNumber());
            pstMaterial.setDate(FLD_LAST_UPDATE, material.getLastUpdate());
            pstMaterial.setInt(FLD_PROCESS_STATUS, material.getProcessStatus());
            pstMaterial.setInt(FLD_CONSIGMENT_TYPE, material.getMatTypeConsig());
            pstMaterial.setString(FLD_GONDOLA_CODE, material.getGondolaCode());
            //for update date
            //pstMaterial.setDate(FLD_UPDATE_DATE, material.getUpdateDate());
            //for last cost cargo
            pstMaterial.setDouble(FLD_LAST_COST_CARGO, material.getLastCostCargo());

            //for edit material add opie 12-06-2012
            pstMaterial.setInt(FLD_EDIT_MATERIAL, material.getEditMaterial());

            //for point sales 20130903
            pstMaterial.setInt(FLD_POINT_SALES, material.getPointSales());

            pstMaterial.setString(FLD_MATERIAL_DESCRIPTION, material.getMaterialDescription());
            pstMaterial.setLong(FLD_COLOR_ID, material.getPosColor());
            pstMaterial.setInt(FLD_SALES_RULE, material.getSalesRule());
            pstMaterial.setInt(FLD_RETURN_RULE, material.getReturnRule());
            //pstMaterial.setData_sync(true);

            //added by dewok 20190318 for item picture
            pstMaterial.setString(FLD_MATERIAL_IMAGE, material.getMaterialImage());

            pstMaterial.insert();

            long oidDataSync = PstDataSyncSql.insertExc(pstMaterial.getInsertSQL());
            PstDataSyncStatus.insertExc(oidDataSync);

            material.setOID(pstMaterial.getlong(FLD_MATERIAL_ID));
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMaterial(0), DBException.UNKNOWN);
        }
        return material.getOID();
    }

    public static long updateExc(Material material) throws DBException {
        try {
            if (material.getOID() != 0) {
                PstMaterial pstMaterial = new PstMaterial(material.getOID());

                pstMaterial.setString(FLD_SKU, material.getSku());
                pstMaterial.setString(FLD_BARCODE, material.getBarCode());
                pstMaterial.setString(FLD_NAME, material.getName());
                pstMaterial.setLong(FLD_MERK_ID, material.getMerkId());
                pstMaterial.setLong(FLD_CATEGORY_ID, material.getCategoryId());

                pstMaterial.setLong(FLD_SUB_CATEGORY_ID, material.getSubCategoryId());
                pstMaterial.setLong(FLD_DEFAULT_STOCK_UNIT_ID, material.getDefaultStockUnitId());
                pstMaterial.setDouble(FLD_DEFAULT_PRICE, material.getDefaultPrice());

                pstMaterial.setLong(FLD_DEFAULT_PRICE_CURRENCY_ID, material.getDefaultPriceCurrencyId());
                pstMaterial.setDouble(FLD_DEFAULT_COST, material.getDefaultCost());
                pstMaterial.setLong(FLD_DEFAULT_COST_CURRENCY_ID, material.getDefaultCostCurrencyId());
                pstMaterial.setInt(FLD_DEFAULT_SUPPLIER_TYPE, material.getDefaultSupplierType());
                pstMaterial.setLong(FLD_SUPPLIER_ID, material.getSupplierId());
                pstMaterial.setDouble(FLD_PRICE_TYPE_01, material.getPriceType01());
                pstMaterial.setDouble(FLD_PRICE_TYPE_02, material.getPriceType02());
                pstMaterial.setDouble(FLD_PRICE_TYPE_03, material.getPriceType03());
                pstMaterial.setInt(FLD_MATERIAL_TYPE, material.getMaterialType());

                // NEW
                pstMaterial.setDouble(FLD_LAST_DISCOUNT, material.getLastDiscount());
                pstMaterial.setDouble(FLD_LAST_VAT, material.getLastVat());
                pstMaterial.setDouble(FLD_CURR_BUY_PRICE, material.getCurrBuyPrice());
                pstMaterial.setDate(FLD_EXPIRED_DATE, material.getExpiredDate());
                pstMaterial.setLong(FLD_BUY_UNIT_ID, material.getBuyUnitId());
                pstMaterial.setDouble(FLD_PROFIT, material.getProfit());
                pstMaterial.setDouble(FLD_CURR_SELL_PRICE_RECOMENTATION, material.getCurrSellPriceRecomentation());

                pstMaterial.setDouble(FLD_AVERAGE_PRICE, material.getAveragePrice());
                pstMaterial.setInt(FLD_MINIMUM_POINT, material.getMinimumPoint());
                pstMaterial.setInt(FLD_REQUIRED_SERIAL_NUMBER, material.getRequiredSerialNumber());
                pstMaterial.setDate(FLD_LAST_UPDATE, material.getLastUpdate());
                pstMaterial.setInt(FLD_PROCESS_STATUS, material.getProcessStatus());
                pstMaterial.setInt(FLD_CONSIGMENT_TYPE, material.getMatTypeConsig());
                pstMaterial.setString(FLD_GONDOLA_CODE, material.getGondolaCode());
                //for updated catalog
                //pstMaterial.setDate(FLD_UPDATE_DATE, material.getUpdateDate());

                //last Cost cargo
                pstMaterial.setDouble(FLD_LAST_COST_CARGO, material.getLastCostCargo());

                //for edit material 12-06-2012
                pstMaterial.setInt(FLD_EDIT_MATERIAL, material.getEditMaterial());

                //for edit material 20130903
                pstMaterial.setInt(FLD_POINT_SALES, material.getPointSales());
                //pstMaterial.setData_sync(true);
                pstMaterial.setString(FLD_MATERIAL_DESCRIPTION, material.getMaterialDescription());
                pstMaterial.setLong(FLD_COLOR_ID, material.getPosColor());
                pstMaterial.setInt(FLD_SALES_RULE, material.getSalesRule());
                pstMaterial.setInt(FLD_RETURN_RULE, material.getReturnRule());

                //added by dewok 20190318 for item picture
                pstMaterial.setString(FLD_MATERIAL_IMAGE, material.getMaterialImage());

                pstMaterial.update();
                long oidDataSync = PstDataSyncSql.insertExc(pstMaterial.getUpdateSQL());
                PstDataSyncStatus.insertExc(oidDataSync);
                return material.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMaterial(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long updateExcWithUpdateDate(Material material) throws DBException {
        try {
            if (material.getOID() != 0) {
                PstMaterial pstMaterial = new PstMaterial(material.getOID());

                pstMaterial.setString(FLD_SKU, material.getSku());
                pstMaterial.setString(FLD_BARCODE, material.getBarCode());
                //pstMaterial.setString(FLD_NAME, material.getName());
                pstMaterial.setLong(FLD_MERK_ID, material.getMerkId());
                pstMaterial.setLong(FLD_CATEGORY_ID, material.getCategoryId());

                pstMaterial.setLong(FLD_SUB_CATEGORY_ID, material.getSubCategoryId());
                pstMaterial.setLong(FLD_DEFAULT_STOCK_UNIT_ID, material.getDefaultStockUnitId());
                pstMaterial.setDouble(FLD_DEFAULT_PRICE, material.getDefaultPrice());

                pstMaterial.setLong(FLD_DEFAULT_PRICE_CURRENCY_ID, material.getDefaultPriceCurrencyId());
                pstMaterial.setDouble(FLD_DEFAULT_COST, material.getDefaultCost());
                pstMaterial.setLong(FLD_DEFAULT_COST_CURRENCY_ID, material.getDefaultCostCurrencyId());
                pstMaterial.setInt(FLD_DEFAULT_SUPPLIER_TYPE, material.getDefaultSupplierType());
                pstMaterial.setLong(FLD_SUPPLIER_ID, material.getSupplierId());
                pstMaterial.setDouble(FLD_PRICE_TYPE_01, material.getPriceType01());
                pstMaterial.setDouble(FLD_PRICE_TYPE_02, material.getPriceType02());
                pstMaterial.setDouble(FLD_PRICE_TYPE_03, material.getPriceType03());
                pstMaterial.setInt(FLD_MATERIAL_TYPE, material.getMaterialType());

                // NEW
                pstMaterial.setDouble(FLD_LAST_DISCOUNT, material.getLastDiscount());
                pstMaterial.setDouble(FLD_LAST_VAT, material.getLastVat());
                pstMaterial.setDouble(FLD_CURR_BUY_PRICE, material.getCurrBuyPrice());
                pstMaterial.setDate(FLD_EXPIRED_DATE, material.getExpiredDate());
                pstMaterial.setLong(FLD_BUY_UNIT_ID, material.getBuyUnitId());
                pstMaterial.setDouble(FLD_PROFIT, material.getProfit());
                pstMaterial.setDouble(FLD_CURR_SELL_PRICE_RECOMENTATION, material.getCurrSellPriceRecomentation());

                pstMaterial.setDouble(FLD_AVERAGE_PRICE, material.getAveragePrice());
                pstMaterial.setInt(FLD_MINIMUM_POINT, material.getMinimumPoint());
                pstMaterial.setInt(FLD_REQUIRED_SERIAL_NUMBER, material.getRequiredSerialNumber());
                pstMaterial.setDate(FLD_LAST_UPDATE, material.getLastUpdate());
                pstMaterial.setInt(FLD_PROCESS_STATUS, material.getProcessStatus());
                pstMaterial.setInt(FLD_CONSIGMENT_TYPE, material.getMatTypeConsig());
                pstMaterial.setString(FLD_GONDOLA_CODE, material.getGondolaCode());
                //for updated catalog
                pstMaterial.setDate(FLD_UPDATE_DATE, material.getUpdateDate());

                //for last cost cargo
                pstMaterial.setDouble(FLD_LAST_COST_CARGO, material.getLastCostCargo());

                //for edit material 12-06-2012
                pstMaterial.setInt(FLD_EDIT_MATERIAL, material.getEditMaterial());

                //for edit material 20130903
                pstMaterial.setInt(FLD_POINT_SALES, material.getPointSales());
                pstMaterial.setString(FLD_MATERIAL_DESCRIPTION, material.getMaterialDescription());
                pstMaterial.setLong(FLD_COLOR_ID, material.getPosColor());
                pstMaterial.setInt(FLD_SALES_RULE, material.getSalesRule());
                pstMaterial.setInt(FLD_RETURN_RULE, material.getReturnRule());

                //added by dewok 20190318 for item picture
                pstMaterial.setString(FLD_MATERIAL_IMAGE, material.getMaterialImage());

                pstMaterial.update();
                long oidDataSync = PstDataSyncSql.insertExc(pstMaterial.getUpdateSQL());
                PstDataSyncStatus.insertExc(oidDataSync);
                return material.getOID();
            }
        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMaterial(0), DBException.UNKNOWN);
        }
        return 0;
    }

    public static long deleteExc(long oid) throws DBException {
        try {
//            PstMinMaxStock.deleteByMaterial(oid);
//
//            PstMaterialComposit.deleteByMaterial(oid);

            PstMaterial pstMaterial = new PstMaterial(oid);

            pstMaterial.delete();

            long oidDataSync = PstDataSyncSql.insertExc(pstMaterial.getDeleteSQL());
            PstDataSyncStatus.insertExc(oidDataSync);

        } catch (DBException dbe) {
            throw dbe;
        } catch (Exception e) {
            throw new DBException(new PstMaterial(0), DBException.UNKNOWN);
        }
        return oid;
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * FROM " + TBL_MATERIAL;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            System.out.println(sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Material material = new Material();
                resultToObject(rs, material);
                lists.add(material);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    //Overload this function to make it work!!
    public static Vector list(int limitStart, int recordToGet, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT MAT." + fieldNames[FLD_MATERIAL_ID]
                    + " ,MAT." + fieldNames[FLD_SKU]
                    + " ,MAT." + fieldNames[FLD_BARCODE]
                    + " ,MAT." + fieldNames[FLD_NAME]
                    + " ,MAT." + fieldNames[FLD_DEFAULT_SUPPLIER_TYPE]
                    + " ,MAT." + fieldNames[FLD_DEFAULT_PRICE]
                    + " ,MAT." + fieldNames[FLD_DEFAULT_COST]
                    + " ,CAT." + PstCategory.fieldNames[PstCategory.FLD_NAME]
                    + " ,CAT." + PstCategory.fieldNames[PstCategory.FLD_CODE]
                    + " ,SCAT." + PstSubCategory.fieldNames[PstSubCategory.FLD_NAME]
                    + " ,SCAT." + PstSubCategory.fieldNames[PstSubCategory.FLD_CODE]
                    + " ,CNT." + PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]
                    + " ,CNT." + PstContactList.fieldNames[PstContactList.FLD_CONTACT_CODE]
                    + " FROM ((" + TBL_MATERIAL
                    + " MAT LEFT JOIN " + PstCategory.TBL_CATEGORY
                    + " CAT ON MAT.CATEGORY_ID"
                    + " = CAT.CATEGORY_ID)"
                    + " LEFT JOIN " + PstSubCategory.TBL_SUB_CATEGORY
                    + " SCAT ON MAT.SUB_CATEGORY_ID"
                    + " = SCAT.SUB_CATEGORY_ID"
                    + " ) LEFT JOIN contact_list CNT"
                    + " ON MAT.SUPPLIER_ID = CNT.CONTACT_ID";

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector temp = new Vector();
                Material material = new Material();
                Category category = new Category();
                SubCategory subCategory = new SubCategory();
                ContactList cnt = new ContactList();

                material.setOID(rs.getLong(1));
                material.setSku(rs.getString(2));
                material.setBarCode(rs.getString(3));
                material.setName(rs.getString(4));
                material.setDefaultSupplierType(rs.getInt(5));
                material.setDefaultPrice(rs.getDouble(6));
                material.setDefaultCost(rs.getDouble(7));
                temp.add(material);

                category.setName(rs.getString(8));
                category.setCode(rs.getString(9));
                temp.add(category);

                subCategory.setName(rs.getString(10));
                subCategory.setCode(rs.getString(11));
                temp.add(subCategory);

                cnt.setCompName(rs.getString(12));
                cnt.setContactCode(rs.getString(13));
                temp.add(cnt);

                lists.add(temp);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static void resultToObject(ResultSet rs, Material material) {
        try {
            material.setOID(rs.getLong(PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]));
            material.setSku(rs.getString(PstMaterial.fieldNames[PstMaterial.FLD_SKU]));
            material.setBarCode(rs.getString(PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]));
            material.setName(rs.getString(PstMaterial.fieldNames[PstMaterial.FLD_NAME]));
            material.setMerkId(rs.getLong(PstMaterial.fieldNames[PstMaterial.FLD_MERK_ID]));
            material.setCategoryId(rs.getLong(PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]));

            material.setSubCategoryId(rs.getLong(PstMaterial.fieldNames[PstMaterial.FLD_SUB_CATEGORY_ID]));
            material.setDefaultStockUnitId(rs.getLong(PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]));
            material.setDefaultPrice(rs.getDouble(PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_PRICE]));

            material.setDefaultPriceCurrencyId(rs.getLong(PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_PRICE_CURRENCY_ID]));
            material.setDefaultCost(rs.getDouble(PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_COST]));
            material.setDefaultCostCurrencyId(rs.getLong(PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_COST_CURRENCY_ID]));
            material.setDefaultSupplierType(rs.getInt(PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_SUPPLIER_TYPE]));
            material.setSupplierId(rs.getLong(PstMaterial.fieldNames[PstMaterial.FLD_SUPPLIER_ID]));
            material.setPriceType01(rs.getDouble(PstMaterial.fieldNames[PstMaterial.FLD_PRICE_TYPE_01]));
            material.setPriceType02(rs.getDouble(PstMaterial.fieldNames[PstMaterial.FLD_PRICE_TYPE_02]));
            material.setPriceType03(rs.getDouble(PstMaterial.fieldNames[PstMaterial.FLD_PRICE_TYPE_03]));
            material.setMaterialType(rs.getInt(PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]));

            // NEW
            material.setLastDiscount(rs.getDouble(PstMaterial.fieldNames[PstMaterial.FLD_LAST_DISCOUNT]));
            material.setLastVat(rs.getDouble(PstMaterial.fieldNames[PstMaterial.FLD_LAST_VAT]));
            material.setCurrBuyPrice(rs.getDouble(PstMaterial.fieldNames[PstMaterial.FLD_CURR_BUY_PRICE]));
            material.setExpiredDate(rs.getDate(PstMaterial.fieldNames[PstMaterial.FLD_EXPIRED_DATE]));
            material.setBuyUnitId(rs.getLong(PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID]));
            material.setProfit(rs.getDouble(PstMaterial.fieldNames[PstMaterial.FLD_PROFIT]));
            material.setCurrSellPriceRecomentation(rs.getDouble(PstMaterial.fieldNames[PstMaterial.FLD_CURR_SELL_PRICE_RECOMENTATION]));

            material.setAveragePrice(rs.getDouble(PstMaterial.fieldNames[PstMaterial.FLD_AVERAGE_PRICE]));
            material.setMinimumPoint(rs.getInt(PstMaterial.fieldNames[PstMaterial.FLD_MINIMUM_POINT]));
            material.setRequiredSerialNumber(rs.getInt(PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]));
            material.setLastUpdate(rs.getDate(PstMaterial.fieldNames[PstMaterial.FLD_LAST_UPDATE]));
            material.setProcessStatus(rs.getInt(PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS]));

            material.setMatTypeConsig(rs.getInt(PstMaterial.fieldNames[PstMaterial.FLD_CONSIGMENT_TYPE]));
            material.setGondolaCode(rs.getString(PstMaterial.fieldNames[PstMaterial.FLD_GONDOLA_CODE]));

            //for updated catalog by Mirah'
            material.setUpdateDate(rs.getTimestamp(PstMaterial.fieldNames[PstMaterial.FLD_UPDATE_DATE]));

            //for last cost cargo
            material.setLastCostCargo(rs.getDouble(PstMaterial.fieldNames[PstMaterial.FLD_LAST_COST_CARGO]));

            //for edit material opie 12-06-2012
            material.setEditMaterial(rs.getInt(PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]));

            //for opie 20130903
            material.setPointSales(rs.getInt(PstMaterial.fieldNames[PstMaterial.FLD_POINT_SALES]));
            material.setMaterialDescription(rs.getString(PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_DESCRIPTION]));
            material.setPosColor(rs.getLong(PstMaterial.fieldNames[PstMaterial.FLD_COLOR_ID]));
            material.setSalesRule(rs.getInt(PstMaterial.fieldNames[PstMaterial.FLD_SALES_RULE]));
            material.setReturnRule(rs.getInt(PstMaterial.fieldNames[PstMaterial.FLD_RETURN_RULE]));

            //added by dewok 20190318 for item picture
            material.setMaterialImage(rs.getString(PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_IMAGE]));

        } catch (Exception e) {
        }
    }

    public static boolean checkOID(long catalogId) {
        DBResultSet dbrs = null;
        boolean result = false;
        try {
            String sql = "SELECT * FROM " + TBL_MATERIAL
                    + " WHERE " + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                    + " = " + catalogId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            while (rs.next()) {
                result = true;
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    /*
     * Fungsi ini digunakan untuk mengecek material code
     * create by: gwawan@dimata 20 Juli 2007
     * @param code Kode dari material yang dicari
     * @return OID Material
     */
    public static long checkMaterialCode(String code) {
        Material material = new Material();
        try {
            String where = "";
            where += PstMaterial.fieldNames[PstMaterial.FLD_SKU] + " = '" + code + "'";
            where += " AND " + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
            Vector list = PstMaterial.list(0, 0, where, "");
            if (list != null && list.size() > 0) {
                material = (Material) list.get(0);
            }
        } catch (Exception e) {
            return 0;
        }
        return material.getOID();
    }

    //Untuk create new PO
    public static Material fetchBySkuBarcode(String skuBarcode) {
        DBResultSet dbrs = null;
        Material mat = new Material();
        try {
            String sql = "SELECT * FROM " + TBL_MATERIAL
                    + " WHERE (" + fieldNames[FLD_SKU]
                    + " = '" + skuBarcode + "' or " + fieldNames[FLD_BARCODE]
                    + " = '" + skuBarcode + "')";
            System.out.println(sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                resultToObject(rs, mat);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return mat;
    }

    public static Material fetchBySkuBarcodeAndAveragePrice(String skuBarcode) {
        DBResultSet dbrs = null;
        Material mat = new Material();
        try {
            String sql = "SELECT " + fieldNames[FLD_MATERIAL_ID]
                    + " , " + fieldNames[FLD_SKU]
                    + " , " + fieldNames[FLD_BARCODE]
                    + " , " + fieldNames[FLD_NAME]
                    + " , " + fieldNames[FLD_DEFAULT_STOCK_UNIT_ID]
                    + " , " + fieldNames[FLD_AVERAGE_PRICE]
                    + " , MIN(" + fieldNames[FLD_DEFAULT_COST] + ") AS MIN_AVERAGE_PRICE "
                    + " FROM " + TBL_MATERIAL
                    + " WHERE (" + fieldNames[FLD_SKU]
                    + " = '" + skuBarcode + "' or " + fieldNames[FLD_BARCODE]
                    + " = '" + skuBarcode + "')"
                    + " GROUP BY " + fieldNames[FLD_BARCODE];
            System.out.println(sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                resultToObject(rs, mat);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return mat;
    }

    public static Material fetchBySkuBarcodeAndSellPrice(String skuBarcode) {
        DBResultSet dbrs = null;
        Material mat = new Material();
        try {
            String sql = "SELECT MAT." + fieldNames[FLD_MATERIAL_ID]
                    + " , MAT. " + fieldNames[FLD_SKU]
                    + " , MAT. " + fieldNames[FLD_BARCODE]
                    + " , MAT. " + fieldNames[FLD_NAME]
                    + " , MAT. " + fieldNames[FLD_DEFAULT_STOCK_UNIT_ID]
                    + " , MAT. " + fieldNames[FLD_AVERAGE_PRICE]
                    + " FROM " + TBL_MATERIAL + " MAT "
                    + " INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING + " PR"
                    + " ON PR. " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]
                    + " = MAT. " + fieldNames[FLD_MATERIAL_ID]
                    + " WHERE (MAT. " + fieldNames[FLD_SKU]
                    + " LIKE '" + skuBarcode + "%' or MAT. " + fieldNames[FLD_BARCODE]
                    + " LIKE '" + skuBarcode + "%')"
                    + " AND PR. " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                    + " = (SELECT MIN(PR1." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE] + ")"
                    + " FROM " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING + " PR1 "
                    + " , " + TBL_MATERIAL + " MAT1 "
                    + " WHERE PR1. " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]
                    + " = MAT1. " + fieldNames[FLD_MATERIAL_ID]
                    + " AND (MAT1. " + fieldNames[FLD_SKU]
                    + " LIKE '" + skuBarcode + "%' or MAT1. " + fieldNames[FLD_BARCODE]
                    + " LIKE '" + skuBarcode + "%'))"
                    + " AND (MAT. " + fieldNames[FLD_NAME] + " != ''"
                    + " OR MAT. " + fieldNames[FLD_NAME] + " != '-' )";
            //" GROUP BY MAT." + fieldNames[FLD_BARCODE];

            System.out.println(sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                resultToObject(rs, mat);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return mat;
    }

    //Vector Average Price
    public static Vector listFetchBarcodeAndSellPrice(int limitStart, int recordToGet, String order, String skuBarcode) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT MAT." + fieldNames[FLD_MATERIAL_ID]
                    + " , MAT. " + fieldNames[FLD_SKU]
                    + " , MAT. " + fieldNames[FLD_BARCODE]
                    + " , MAT. " + fieldNames[FLD_NAME]
                    + " , MAT. " + fieldNames[FLD_DEFAULT_STOCK_UNIT_ID]
                    + " , MAT. " + fieldNames[FLD_AVERAGE_PRICE]
                    + " FROM " + TBL_MATERIAL + " MAT "
                    + " INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING + " PR"
                    + " ON PR. " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]
                    + " = MAT. " + fieldNames[FLD_MATERIAL_ID]
                    + " WHERE (MAT. " + fieldNames[FLD_SKU]
                    + " LIKE '" + skuBarcode + "%' or MAT. " + fieldNames[FLD_BARCODE]
                    + " LIKE '" + skuBarcode + "%')"
                    + " AND PR. " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                    + " = (SELECT MIN(PR1." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE] + ")"
                    + " FROM " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING + " PR1 "
                    + " , " + TBL_MATERIAL + " MAT1 "
                    + " WHERE PR1. " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]
                    + " = MAT1. " + fieldNames[FLD_MATERIAL_ID]
                    + " AND (MAT1. " + fieldNames[FLD_SKU]
                    + " LIKE '" + skuBarcode + "%' or MAT1. " + fieldNames[FLD_BARCODE]
                    + " LIKE '" + skuBarcode + "%')"
                    + " AND MAT1. " + fieldNames[FLD_NAME] + " != ''"
                    + " AND MAT1. " + fieldNames[FLD_NAME] + " != '-') "
                    + " AND MAT. " + fieldNames[FLD_NAME] + " != ''"
                    + " AND MAT. " + fieldNames[FLD_NAME] + " != '-' ";

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                //Vector temp = new Vector();
                Material material = new Material();

                material.setOID(rs.getLong(1));
                material.setSku(rs.getString(2));
                material.setBarCode(rs.getString(3));
                material.setName(rs.getString(4));
                material.setDefaultStockUnitId(rs.getLong(5));
                material.setAveragePrice(rs.getDouble(6));

                //resultToObject(rs, material);
                //temp.add(material);
                lists.add(material);

            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static int getCount(String whereClause) {
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(" + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID] + ") FROM " + TBL_MATERIAL;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();

            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            return count;
        } catch (Exception e) {
            return 0;
        } finally {
            DBResultSet.close(dbrs);
        }
    }

    //Supplier Type Combo
    public static final int SUPPLIER_CONSIGNMENT = 0;
    public static final int SUPPLIER_CASH = 1;
    public static final int SUPPLIER_ELSE = 2;

    public static final String SpTypeSourceKey[] = {"Consignment", "Cash", "Else"};
    public static final int SpTypeSourceValue[] = {0, 1, 2};

    public static Vector listSpTypeSourceTypeKey() {
        Vector result = new Vector(1, 1);
        for (int s = 0; s < SpTypeSourceKey.length; s++) {
            result.add(SpTypeSourceKey[s]);
        }
        return result;
    }

    public static Vector listSpTypeSourceTypeValue() {
        Vector result = new Vector(1, 1);
        for (int s = 0; s < SpTypeSourceValue.length; s++) {
            result.add("" + SpTypeSourceValue[s]);
        }
        return result;
    }

    //Material Type Combo
    public static final int MATERIAL_TYPE_REGULAR = 0;
    public static final int MATERIAL_TYPE_COMPOSITE = 1;

    public static final String MatTypeSourceKey[] = {"Regular", "Composite"};
    public static final int MatTypeSourceValue[] = {0, 1};

    public static Vector listMatTypeKey() {
        Vector result = new Vector(1, 1);
        for (int s = 0; s < MatTypeSourceKey.length; s++) {
            result.add(MatTypeSourceKey[s]);
        }
        return result;
    }

    public static Vector listMatTypeValue() {
        Vector result = new Vector(1, 1);
        for (int s = 0; s < MatTypeSourceValue.length; s++) {
            result.add("" + MatTypeSourceValue[s]);
        }
        return result;
    }

    /**
     * gadnyana untuk mencari material berdasarkan kode material
     *
     * @param code
     * @return
     */
    public static Material getMaterialByCode(String code) {
        DBResultSet dbrs = null;
        Material material = new Material();
        try {
            String sql = "SELECT * FROM " + TBL_MATERIAL
                    + " WHERE " + fieldNames[FLD_SKU] + "='" + code + "'";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                resultToObject(rs, material);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println("");
        } finally {
            DBResultSet.close(dbrs);
        }
        return material;
    }

    /**
     * this method used to list material data
     *
     * @param objMaterial
     * @param limitStart --> starting index of displaying result
     * @param recordToGet --> amount of displaying result
     * @return Vector of material
     * @update by Edhy
     */
    public static Vector getListMaterialItem(long oidVendor, Material objMaterial,
            int limitStart, int recordToGet, String orderBy) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT "
                    + " MAT." + fieldNames[FLD_MATERIAL_ID]
                    + " , MAT." + fieldNames[FLD_SKU]
                    + " , MAT." + fieldNames[FLD_NAME]
                    + " , MAT." + fieldNames[FLD_CATEGORY_ID]
                    + " , CAT." + PstCategory.fieldNames[PstCategory.FLD_NAME]
                    + " , MAT." + fieldNames[FLD_DEFAULT_STOCK_UNIT_ID]
                    + " , UNT." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                    + " , MAT." + fieldNames[FLD_DEFAULT_COST_CURRENCY_ID];
            if (oidVendor != 0) {
                sql = sql + " , CURR." + PstCurrencyType.fieldNames[PstCurrencyType.FLD_CODE];
            } else {
                sql = sql + " , MAT." + fieldNames[FLD_DEFAULT_COST_CURRENCY_ID];
            }
            sql = sql + " , MAT." + fieldNames[FLD_DEFAULT_COST]
                    + " , MAT." + fieldNames[FLD_SUB_CATEGORY_ID]
                    + " , MAT." + fieldNames[FLD_DEFAULT_PRICE]
                    + " , UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                    + " , MAT." + fieldNames[FLD_CURR_BUY_PRICE];
            if (oidVendor != 0) {
                sql = sql + " , VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_ORG_BUYING_PRICE]
                        + " , VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_LAST_DISCOUNT]
                        + " , VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_CURR_BUYING_PRICE];
            }

            sql = sql + " FROM " + TBL_MATERIAL
                    + " MAT "
                    + " LEFT JOIN " + PstCategory.TBL_CATEGORY
                    + " CAT ON MAT." + fieldNames[FLD_CATEGORY_ID]
                    + " = CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID];

            if (oidVendor != 0) {
                sql = sql + " INNER JOIN " + PstMatVendorPrice.TBL_MATERIAL_VENDOR_PRICE + " AS VDR ON "
                        + " MAT." + fieldNames[FLD_MATERIAL_ID]
                        + " = VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_MATERIAL_ID]
                        + " LEFT JOIN " + PstCurrencyType.TBL_POS_CURRENCY_TYPE
                        + " CURR ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY]
                        + " = CURR." + PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID]
                        + " LEFT JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_BUYING_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            } else {
                sql = sql + " LEFT JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON MAT." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            }
//            String strGroup = "";
//            if (objMaterial.getCategoryId() > 0) {
//                strGroup = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID] +
//                        " = " + objMaterial.getCategoryId();
//            }

            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
                //buatkan seperti
                strGroup = " ( MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();

                String where = PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID] + "='" + objMaterial.getCategoryId() + "' OR "
                        + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + "='" + objMaterial.getCategoryId() + "'";

                Vector masterCatAcak = PstCategory.list(0, 0, where, PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]);

                if (masterCatAcak.size() > 1) {
                    //Vector materGroup = PstCategory.structureList(masterCatAcak) ;
                    //Vector<Long> levelParent = new Vector<Long>();
                    for (int i = 0; i < masterCatAcak.size(); i++) {
                        Category mGroup = (Category) masterCatAcak.get(i);
                        strGroup = strGroup + " OR MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                                + " = " + mGroup.getOID();
                    }
                }

                strGroup = strGroup + ")";

            }

            String strSubCategory = "";
            if (objMaterial.getSubCategoryId() > 0) {
                strSubCategory = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SUB_CATEGORY_ID]
                        + " = " + objMaterial.getSubCategoryId();
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strBarcode = "";
            if (objMaterial.getBarCode() != "" && objMaterial.getBarCode().length() > 0) {
                strBarcode = "(MAT." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " LIKE '%" + objMaterial.getBarCode() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String strCosCurrency = "";
            if (objMaterial.getDefaultCostCurrencyId() > 0) {
                strCosCurrency = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY] + " = " + objMaterial.getDefaultCostCurrencyId();
            }

            String whereClause = "";
            if (oidVendor != 0) {
                whereClause = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_VENDOR_ID] + " = " + oidVendor;
            }

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (oidVendor != 0) {
                if (strCosCurrency.length() > 0) {
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCosCurrency;
                    } else {
                        whereClause = whereClause + strCosCurrency;
                    }
                }
            }

            if (strSubCategory.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSubCategory;
                } else {
                    whereClause = whereClause + strSubCategory;
                }
            }

            if (strCode.length() > 0) {
                if (strBarcode.length() > 0) { // kondisi pencarian berdasarkan code/barcode material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND (" + strCode + " OR " + strBarcode + ")";
                    } else {
                        whereClause = whereClause + "(" + strCode + " OR " + strBarcode + ")";
                    }
                } else { // kondisi pencarian hanya berdasarkan code material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCode;
                    } else {
                        whereClause = whereClause + strCode;
                    }
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (whereClause.length() > 0) {
                whereClause = whereClause + " AND MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
                whereClause = whereClause + " AND MAT." + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            } else {
                whereClause = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
                whereClause = whereClause + " AND MAT." + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " ORDER BY " + orderBy;

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;
                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            System.out.println("list item " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                Material material = new Material();
                Category cat = new Category();
                Unit matUnit = new Unit();
                MatCurrency matCurr = new MatCurrency();
                MatVendorPrice matVendorPrice = new MatVendorPrice();

                material.setOID(rs.getLong(1));
                material.setSku(rs.getString(2));
                material.setName(rs.getString(3));
                material.setCategoryId(rs.getLong(4));
                material.setDefaultStockUnitId(rs.getLong(6));
                material.setDefaultCostCurrencyId(rs.getLong(8));
                material.setDefaultCost(rs.getDouble(10));
                material.setSubCategoryId(rs.getLong(11));
                material.setDefaultPrice(rs.getDouble(12));
                material.setCurrBuyPrice(rs.getDouble(14));
                if (oidVendor == 0) {
                    material.setDefaultCostCurrencyId(rs.getLong(9));
                }
                vt.add(material);

                cat.setName(rs.getString(5));
                vt.add(cat);

                matUnit.setOID(rs.getLong(13));
                matUnit.setCode(rs.getString(7));
                vt.add(matUnit);
                if (oidVendor != 0) {
                    matCurr.setCode(rs.getString(9));
                    matVendorPrice.setOrgBuyingPrice(rs.getDouble(PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_ORG_BUYING_PRICE]));
                    matVendorPrice.setLastDiscount(rs.getDouble(PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_LAST_DISCOUNT]));
                    matVendorPrice.setCurrBuyingPrice(rs.getDouble(PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_CURR_BUYING_PRICE]));
                }
                vt.add(matCurr);
                vt.add(matVendorPrice);

                result.add(vt);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    public static Vector getListMaterialItemWithSellLocation(long oidVendor, Material objMaterial, int limitStart, int recordToGet, String orderBy) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT "
                    + " MAT." + fieldNames[FLD_MATERIAL_ID]
                    + " , MAT." + fieldNames[FLD_SKU]
                    + " , MAT." + fieldNames[FLD_NAME]
                    + " , MAT." + fieldNames[FLD_CATEGORY_ID]
                    + " , CAT." + PstCategory.fieldNames[PstCategory.FLD_NAME]
                    + " , MAT." + fieldNames[FLD_DEFAULT_STOCK_UNIT_ID]
                    + " , UNT." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                    + " , MAT." + fieldNames[FLD_DEFAULT_COST_CURRENCY_ID];
            if (oidVendor != 0) {
                sql = sql + " , CURR." + PstCurrencyType.fieldNames[PstCurrencyType.FLD_CODE];
            } else {
                sql = sql + " , MAT." + fieldNames[FLD_DEFAULT_COST_CURRENCY_ID];
            }
            sql = sql + " , MAT." + fieldNames[FLD_DEFAULT_COST]
                    + " , MAT." + fieldNames[FLD_SUB_CATEGORY_ID]
                    + " , MAT." + fieldNames[FLD_DEFAULT_PRICE]
                    + " , UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                    + " , MAT." + fieldNames[FLD_CURR_BUY_PRICE];
            if (oidVendor != 0) {
                sql = sql + " , VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_ORG_BUYING_PRICE]
                        + " , VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_LAST_DISCOUNT]
                        + " , VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_CURR_BUYING_PRICE];
            }

            sql = sql + " FROM " + TBL_MATERIAL
                    + " MAT "
                    + " LEFT JOIN " + PstCategory.TBL_CATEGORY
                    + " CAT ON MAT." + fieldNames[FLD_CATEGORY_ID]
                    + " = CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID];

            if (oidVendor != 0) {
                sql = sql + " INNER JOIN " + PstMatVendorPrice.TBL_MATERIAL_VENDOR_PRICE + " AS VDR ON "
                        + " MAT." + fieldNames[FLD_MATERIAL_ID]
                        + " = VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_MATERIAL_ID]
                        + " LEFT JOIN " + PstCurrencyType.TBL_POS_CURRENCY_TYPE
                        + " CURR ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY]
                        + " = CURR." + PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID]
                        + " LEFT JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_BUYING_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            } else {
                sql = sql + " LEFT JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON MAT." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            }

            if (objMaterial.getUseSellLocation() != 0) {
                sql = sql + " INNER JOIN product_location as PL "
                        + " ON PL.PRODUCT_ID "
                        + " = MAT." + fieldNames[FLD_MATERIAL_ID];
            }

            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
                //buatkan seperti
                strGroup = " ( MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();

                String where = PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID] + "='" + objMaterial.getCategoryId() + "' OR "
                        + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + "='" + objMaterial.getCategoryId() + "'";

                Vector masterCatAcak = PstCategory.list(0, 0, where, PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]);

                if (masterCatAcak.size() > 1) {
                    //Vector materGroup = PstCategory.structureList(masterCatAcak) ;
                    //Vector<Long> levelParent = new Vector<Long>();
                    for (int i = 0; i < masterCatAcak.size(); i++) {
                        Category mGroup = (Category) masterCatAcak.get(i);
                        strGroup = strGroup + " OR MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                                + " = " + mGroup.getOID();
                    }
                }

                strGroup = strGroup + ")";

            }

            String strSubCategory = "";
            if (objMaterial.getSubCategoryId() > 0) {
                strSubCategory = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SUB_CATEGORY_ID]
                        + " = " + objMaterial.getSubCategoryId();
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strBarcode = "";
            if (objMaterial.getBarCode() != "" && objMaterial.getBarCode().length() > 0) {
                strBarcode = "(MAT." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " LIKE '%" + objMaterial.getBarCode() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String strCosCurrency = "";
            if (objMaterial.getDefaultCostCurrencyId() > 0) {
                strCosCurrency = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY] + " = " + objMaterial.getDefaultCostCurrencyId();
            }

            String productLocation = "";
            if (objMaterial.getUseSellLocation() != 0) {
                productLocation = " PL.LOCATION_ID ='" + objMaterial.getUseSellLocation() + "'";
            }

            String whereClause = "";
            if (oidVendor != 0) {
                whereClause = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_VENDOR_ID] + " = " + oidVendor;
            }

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (oidVendor != 0) {
                if (strCosCurrency.length() > 0) {
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCosCurrency;
                    } else {
                        whereClause = whereClause + strCosCurrency;
                    }
                }
            }

            if (strSubCategory.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSubCategory;
                } else {
                    whereClause = whereClause + strSubCategory;
                }
            }

            if (strCode.length() > 0) {
                if (strBarcode.length() > 0) { // kondisi pencarian berdasarkan code/barcode material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND (" + strCode + " OR " + strBarcode + ")";
                    } else {
                        whereClause = whereClause + "(" + strCode + " OR " + strBarcode + ")";
                    }
                } else { // kondisi pencarian hanya berdasarkan code material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCode;
                    } else {
                        whereClause = whereClause + strCode;
                    }
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (productLocation.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + productLocation;
                } else {
                    whereClause = whereClause + productLocation;
                }

            }

            if (whereClause.length() > 0) {
                whereClause = whereClause + " AND MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
                whereClause = whereClause + " AND MAT." + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            } else {
                whereClause = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
                whereClause = whereClause + " AND MAT." + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " ORDER BY " + orderBy;

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;
                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            System.out.println("list item " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                Material material = new Material();
                Category cat = new Category();
                Unit matUnit = new Unit();
                MatCurrency matCurr = new MatCurrency();
                MatVendorPrice matVendorPrice = new MatVendorPrice();

                material.setOID(rs.getLong(1));
                material.setSku(rs.getString(2));
                material.setName(rs.getString(3));
                material.setCategoryId(rs.getLong(4));
                material.setDefaultStockUnitId(rs.getLong(6));
                material.setDefaultCostCurrencyId(rs.getLong(8));
                material.setDefaultCost(rs.getDouble(10));
                material.setSubCategoryId(rs.getLong(11));
                material.setDefaultPrice(rs.getDouble(12));
                material.setCurrBuyPrice(rs.getDouble(14));
                if (oidVendor == 0) {
                    material.setDefaultCostCurrencyId(rs.getLong(9));
                }
                vt.add(material);

                cat.setName(rs.getString(5));
                vt.add(cat);

                matUnit.setOID(rs.getLong(13));
                matUnit.setCode(rs.getString(7));
                vt.add(matUnit);
                if (oidVendor != 0) {
                    matCurr.setCode(rs.getString(9));
                    matVendorPrice.setOrgBuyingPrice(rs.getDouble(PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_ORG_BUYING_PRICE]));
                    matVendorPrice.setLastDiscount(rs.getDouble(PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_LAST_DISCOUNT]));
                    matVendorPrice.setCurrBuyingPrice(rs.getDouble(PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_CURR_BUYING_PRICE]));
                }
                vt.add(matCurr);
                vt.add(matVendorPrice);

                result.add(vt);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    /**
     * Untuk list material cashier Ari_wiweka 20130628
     */
    public static Vector getListMaterialItemCashier(long priceType2, Material objMaterial,
            int limitStart, int recordToGet, String orderBy, long oidPriceType, long oidCurrency) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        try {
            String sql = "";
            if (priceType2 == 0) {
                sql = " SELECT DISTINCT "
                        + " p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]
                        + " , pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]
                        + " , unt." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                        + " FROM " + TBL_MATERIAL + " AS p  INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                        + " AS pm ON  pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " =  p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT + " AS unt ON p." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID] + " = unt." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                        + " WHERE p." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " !=2 AND LENGTH(p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " > 0 AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + oidCurrency
                        + " AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + oidPriceType;

            } else {
                sql = " SELECT DISTINCT "
                        + " p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]
                        + " , pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]
                        + " , unt." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                        + " FROM " + TBL_MATERIAL + " AS p  INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                        + " AS pm ON  pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " =  p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT + " AS unt ON p." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID] + " = unt." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                        + " WHERE p." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " !=2 AND LENGTH(p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " > 0 AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + oidCurrency
                        + " AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + priceType2;

                sql = "SELECT * FROM (" + sql + ") AS tb1 WHERE NOT EXISTS (SELECT * FROM( SELECT DISTINCT "
                        + " p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]
                        + " , pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]
                        + " , unt." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                        + " FROM " + TBL_MATERIAL + " AS p  INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                        + " AS pm ON  pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " =  p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT + " AS unt ON p." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID] + " = unt." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                        + " WHERE p." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " !=2 AND LENGTH(p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " > 0 AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + oidCurrency
                        + " AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + oidPriceType + ") AS tb2"
                        + " WHERE tb1." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID] + " = tb2." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID] + ")";

                sql = sql + " UNION  SELECT DISTINCT "
                        + " p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]
                        + " , pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]
                        + " , unt." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                        + " FROM " + TBL_MATERIAL + " AS p  INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                        + " AS pm ON  pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " =  p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT + " AS unt ON p." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID] + " = unt." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                        + " WHERE p." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " !=2 AND LENGTH(p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " > 0 AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + oidCurrency
                        + " AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + oidPriceType;

            }

            sql = "SELECT * FROM (" + sql + ") as tmp";

            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
                strGroup = PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();
            }

            String strSubCategory = "";
            if (objMaterial.getSubCategoryId() > 0) {
                strSubCategory = PstMaterial.fieldNames[PstMaterial.FLD_SUB_CATEGORY_ID]
                        + " = " + objMaterial.getSubCategoryId();
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (" + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strBarcode = "";
            if (objMaterial.getBarCode() != "" && objMaterial.getBarCode().length() > 0) {
                strBarcode = "(" + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " LIKE '%" + objMaterial.getBarCode() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (" + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String strCosCurrency = "";
            if (objMaterial.getDefaultCostCurrencyId() > 0) {
                strCosCurrency = PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY] + " = " + objMaterial.getDefaultCostCurrencyId();
            }

            String whereClause = "";

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (strSubCategory.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSubCategory;
                } else {
                    whereClause = whereClause + strSubCategory;
                }
            }

            if (strCode.length() > 0) {
                if (strBarcode.length() > 0) { // kondisi pencarian berdasarkan code/barcode material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND (" + strCode + " OR " + strBarcode + ")";
                    } else {
                        whereClause = whereClause + "(" + strCode + " OR " + strBarcode + ")";
                    }
                } else { // kondisi pencarian hanya berdasarkan code material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCode;
                    } else {
                        whereClause = whereClause + strCode;
                    }
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            sql = sql + " ORDER BY " + orderBy;

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;
                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            System.out.println("list item " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                Material material = new Material();
                PriceTypeMapping priceTypeMapping = new PriceTypeMapping();
                Unit unit = new Unit();

                material.setOID(rs.getLong(1));
                material.setSku(rs.getString(2));
                material.setName(rs.getString(3));
                material.setBarCode(rs.getString(4));
                material.setRequiredSerialNumber(rs.getInt(5));
                material.setEditMaterial(rs.getInt(6));
                material.setMaterialType(rs.getInt(7));
                material.setDefaultStockUnitId(rs.getLong(9));

                vt.add(material);

                priceTypeMapping.setPrice(rs.getDouble(8));
                vt.add(priceTypeMapping);

                unit.setCode(rs.getString(10));
                vt.add(unit);

                result.add(vt);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    /**
     * semua yang diambil di konvert ke rupiah
     *
     * @param priceType2
     * @param objMaterial
     * @param limitStart
     * @param recordToGet
     * @param orderBy
     * @param oidPriceType
     * @param oidCurrency
     * @return
     */
    public static Vector getListMaterialItemCashierConvertToRupiah(long priceType2, Material objMaterial,
            int limitStart, int recordToGet, String orderBy, long oidPriceType, long oidCurrency) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        try {
            String sql = "";
            if (priceType2 == 0) {
                sql = " SELECT DISTINCT "
                        + " p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]
                        + " , pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]
                        + " , unt." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                        + " , st." + PstStandartRate.fieldNames[PstStandartRate.FLD_CURRENCY_TYPE_ID]
                        + " FROM " + TBL_MATERIAL + " AS p  INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                        + " AS pm ON  pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " =  p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT + " AS unt ON p." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID] + " = unt." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                        + " INNER JOIN " + PstStandartRate.TBL_POS_STANDART_RATE + " AS st ON st." + PstStandartRate.fieldNames[PstStandartRate.FLD_STANDART_RATE_ID] + " = pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID]
                        + " WHERE p." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " !=2 AND LENGTH(p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " > 1 "
                        + " AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + oidPriceType;

            } else {
                sql = " SELECT DISTINCT "
                        + " p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]
                        + " , pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]
                        + " , unt." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                        + " FROM " + TBL_MATERIAL + " AS p  INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                        + " AS pm ON  pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " =  p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT + " AS unt ON p." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID] + " = unt." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                        + " WHERE p." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " !=2 AND LENGTH(p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " > 0 AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + oidCurrency
                        + " AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + priceType2;

                sql = "SELECT * FROM (" + sql + ") AS tb1 WHERE NOT EXISTS (SELECT * FROM( SELECT DISTINCT "
                        + " p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]
                        + " , pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]
                        + " , unt." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                        + " FROM " + TBL_MATERIAL + " AS p  INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                        + " AS pm ON  pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " =  p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT + " AS unt ON p." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID] + " = unt." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                        + " WHERE p." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " !=2 AND LENGTH(p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " > 0 AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + oidCurrency
                        + " AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + oidPriceType + ") AS tb2"
                        + " WHERE tb1." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID] + " = tb2." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID] + ")";

                sql = sql + " UNION  SELECT DISTINCT "
                        + " p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]
                        + " , pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]
                        + " , unt." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                        + " FROM " + TBL_MATERIAL + " AS p  INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                        + " AS pm ON  pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " =  p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT + " AS unt ON p." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID] + " = unt." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                        + " WHERE p." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " !=2 AND LENGTH(p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " > 0 AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + oidCurrency
                        + " AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + oidPriceType;

            }

            sql = "SELECT * FROM (" + sql + ") as tmp";

            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
                strGroup = PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();
            }

            String strSubCategory = "";
            if (objMaterial.getSubCategoryId() > 0) {
                strSubCategory = PstMaterial.fieldNames[PstMaterial.FLD_SUB_CATEGORY_ID]
                        + " = " + objMaterial.getSubCategoryId();
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (" + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strBarcode = "";
            if (objMaterial.getBarCode() != "" && objMaterial.getBarCode().length() > 0) {
                strBarcode = "(" + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " LIKE '%" + objMaterial.getBarCode() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (" + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String strCosCurrency = "";
            if (objMaterial.getDefaultCostCurrencyId() > 0) {
                strCosCurrency = PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY] + " = " + objMaterial.getDefaultCostCurrencyId();
            }

            String whereClause = "";

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (strSubCategory.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSubCategory;
                } else {
                    whereClause = whereClause + strSubCategory;
                }
            }

            if (strCode.length() > 0) {
                if (strBarcode.length() > 0) { // kondisi pencarian berdasarkan code/barcode material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND (" + strCode + " OR " + strBarcode + ")";
                    } else {
                        whereClause = whereClause + "(" + strCode + " OR " + strBarcode + ")";
                    }
                } else { // kondisi pencarian hanya berdasarkan code material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCode;
                    } else {
                        whereClause = whereClause + strCode;
                    }
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            sql = sql + " ORDER BY " + orderBy;

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;
                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            System.out.println("list item " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                Material material = new Material();
                PriceTypeMapping priceTypeMapping = new PriceTypeMapping();
                Unit unit = new Unit();
                StandartRate standartRate = new StandartRate();

                material.setOID(rs.getLong(1));
                material.setSku(rs.getString(2));
                material.setName(rs.getString(3));
                material.setBarCode(rs.getString(4));
                material.setRequiredSerialNumber(rs.getInt(5));
                material.setEditMaterial(rs.getInt(6));
                material.setMaterialType(rs.getInt(7));
                material.setDefaultStockUnitId(rs.getLong(9));

                vt.add(material);

                priceTypeMapping.setPrice(rs.getDouble(8));
                vt.add(priceTypeMapping);

                unit.setCode(rs.getString(10));
                vt.add(unit);

                standartRate.setCurrencyTypeId(rs.getLong(PstStandartRate.fieldNames[PstStandartRate.FLD_CURRENCY_TYPE_ID]));
                vt.add(standartRate);

                result.add(vt);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    /**
     * Untuk Count material cashier Ari_wiweka 20130628
     */
    public static int getCountMaterialItemCashier(long priceType2, Material objMaterial, String orderBy, long oidPriceType, long oidCurrency) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "";
            if (priceType2 == 0) {
                sql = " SELECT DISTINCT "
                        + " p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]
                        + " , pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]
                        + " , unt." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                        + " FROM " + TBL_MATERIAL + " AS p  INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                        + " AS pm ON  pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " =  p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT + " AS unt ON p." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID] + " = unt." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                        + " WHERE p." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " !=2 AND LENGTH(p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " > 0 AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + oidCurrency
                        + " AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + oidPriceType;

            } else {
                sql = " SELECT DISTINCT "
                        + " p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]
                        + " , pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]
                        + " , unt." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                        + " FROM " + TBL_MATERIAL + " AS p  INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                        + " AS pm ON  pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " =  p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT + " AS unt ON p." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID] + " = unt." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                        + " WHERE p." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " !=2 AND LENGTH(p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " > 0 AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + oidCurrency
                        + " AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + priceType2;

                sql = "SELECT * FROM (" + sql + ") AS tb1 WHERE NOT EXISTS (SELECT * FROM( SELECT DISTINCT "
                        + " p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]
                        + " , pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]
                        + " , unt." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                        + " FROM " + TBL_MATERIAL + " AS p  INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                        + " AS pm ON  pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " =  p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT + " AS unt ON p." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID] + " = unt." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                        + " WHERE p." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " !=2 AND LENGTH(p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " > 0 AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + oidCurrency
                        + " AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + oidPriceType + ") AS tb2"
                        + " WHERE tb1." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID] + " = tb2." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID] + ")";

                sql = sql + " UNION  SELECT DISTINCT "
                        + " p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_REQUIRED_SERIAL_NUMBER]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_TYPE]
                        + " , pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " , p." + PstMaterial.fieldNames[PstMaterial.FLD_DEFAULT_STOCK_UNIT_ID]
                        + " , unt." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                        + " FROM " + TBL_MATERIAL + " AS p  INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                        + " AS pm ON  pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " =  p." + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT + " AS unt ON p." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID] + " = unt." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                        + " WHERE p." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " !=2 AND LENGTH(p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                        + " > 0 AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + oidCurrency
                        + " AND pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + oidPriceType;

            }

            sql = "SELECT COUNT(*) FROM (" + sql + ") as tmp";

            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
                strGroup = PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();
            }

            String strSubCategory = "";
            if (objMaterial.getSubCategoryId() > 0) {
                strSubCategory = PstMaterial.fieldNames[PstMaterial.FLD_SUB_CATEGORY_ID]
                        + " = " + objMaterial.getSubCategoryId();
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (" + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strBarcode = "";
            if (objMaterial.getBarCode() != "" && objMaterial.getBarCode().length() > 0) {
                strBarcode = "(" + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " LIKE '%" + objMaterial.getBarCode() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (" + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String strCosCurrency = "";
            if (objMaterial.getDefaultCostCurrencyId() > 0) {
                strCosCurrency = PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY] + " = " + objMaterial.getDefaultCostCurrencyId();
            }

            String whereClause = "";

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (strSubCategory.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSubCategory;
                } else {
                    whereClause = whereClause + strSubCategory;
                }
            }

            if (strCode.length() > 0) {
                if (strBarcode.length() > 0) { // kondisi pencarian berdasarkan code/barcode material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND (" + strCode + " OR " + strBarcode + ")";
                    } else {
                        whereClause = whereClause + "(" + strCode + " OR " + strBarcode + ")";
                    }
                } else { // kondisi pencarian hanya berdasarkan code material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCode;
                    } else {
                        whereClause = whereClause + strCode;
                    }
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            sql = sql + " ORDER BY " + orderBy;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    public static Vector getListMaterialItem(long oidVendor, Material objMaterial,
            int limitStart, int recordToGet, String orderBy, int typeConsig) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT "
                    + " MAT." + fieldNames[FLD_MATERIAL_ID]
                    + " , MAT." + fieldNames[FLD_SKU]
                    + " , MAT." + fieldNames[FLD_NAME]
                    + " , MAT." + fieldNames[FLD_CATEGORY_ID]
                    + " , CAT." + PstCategory.fieldNames[PstCategory.FLD_NAME]
                    + " , MAT." + fieldNames[FLD_DEFAULT_STOCK_UNIT_ID]
                    + " , UNT." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                    + " , MAT." + fieldNames[FLD_DEFAULT_COST_CURRENCY_ID];
            if (oidVendor != 0) {
                sql = sql + " , CURR." + PstCurrencyType.fieldNames[PstCurrencyType.FLD_CODE];
            } else {
                sql = sql + " , MAT." + fieldNames[FLD_DEFAULT_COST_CURRENCY_ID];
            }
            sql = sql + " , MAT." + fieldNames[FLD_DEFAULT_COST]
                    + " , MAT." + fieldNames[FLD_SUB_CATEGORY_ID]
                    + " , MAT." + fieldNames[FLD_DEFAULT_PRICE]
                    + " , UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                    + " , MAT." + fieldNames[FLD_CURR_BUY_PRICE];
            if (oidVendor != 0) {
                sql = sql + " , VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_ORG_BUYING_PRICE]
                        + " , VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_LAST_DISCOUNT]
                        + " , VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_CURR_BUYING_PRICE];
            }

            sql = sql + " FROM " + TBL_MATERIAL
                    + " MAT "
                    + " INNER JOIN " + PstCategory.TBL_CATEGORY
                    + " CAT ON MAT." + fieldNames[FLD_CATEGORY_ID]
                    + " = CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID];

            if (oidVendor != 0) {
                sql = sql + " INNER JOIN " + PstMatVendorPrice.TBL_MATERIAL_VENDOR_PRICE + " AS VDR ON "
                        + " MAT." + fieldNames[FLD_MATERIAL_ID]
                        + " = VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstCurrencyType.TBL_POS_CURRENCY_TYPE
                        + " CURR ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY]
                        + " = CURR." + PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_BUYING_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            } else {
                sql = sql + " INNER JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON MAT." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            }
            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
                strGroup = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();
            }

            String strSubCategory = "";
            if (objMaterial.getSubCategoryId() > 0) {
                strSubCategory = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SUB_CATEGORY_ID]
                        + " = " + objMaterial.getSubCategoryId();
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String strCosCurrency = "";
            if (objMaterial.getDefaultCostCurrencyId() > 0) {
                strCosCurrency = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY] + " = " + objMaterial.getDefaultCostCurrencyId();
            }

            String whereClause = "";
            if (oidVendor != 0) {
                whereClause = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_VENDOR_ID] + " = " + oidVendor;
            }

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (oidVendor != 0) {
                if (strCosCurrency.length() > 0) {
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCosCurrency;
                    } else {
                        whereClause = whereClause + strCosCurrency;
                    }
                }
            }

            if (strSubCategory.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSubCategory;
                } else {
                    whereClause = whereClause + strSubCategory;
                }
            }

            if (strCode.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCode;
                } else {
                    whereClause = whereClause + strCode;
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (whereClause.length() > 0) {
                whereClause = whereClause + " AND MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE
                        + " AND MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CONSIGMENT_TYPE] + "=" + typeConsig;
                sql = sql + " WHERE " + whereClause;
            } else {
                whereClause = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE
                        + " AND MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CONSIGMENT_TYPE] + "=" + typeConsig;
                sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " ORDER BY " + orderBy;

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;
                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            System.out.println("list item " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                Material material = new Material();
                Category cat = new Category();
                Unit matUnit = new Unit();
                MatCurrency matCurr = new MatCurrency();
                MatVendorPrice matVendorPrice = new MatVendorPrice();

                material.setOID(rs.getLong(1));
                material.setSku(rs.getString(2));
                material.setName(rs.getString(3));
                material.setCategoryId(rs.getLong(4));
                material.setDefaultStockUnitId(rs.getLong(6));
                material.setDefaultCostCurrencyId(rs.getLong(8));
                material.setDefaultCost(rs.getDouble(10));
                material.setSubCategoryId(rs.getLong(11));
                material.setDefaultPrice(rs.getDouble(12));
                material.setCurrBuyPrice(rs.getDouble(14));
                if (oidVendor == 0) {
                    material.setDefaultCostCurrencyId(rs.getLong(9));
                }
                vt.add(material);

                cat.setName(rs.getString(5));
                vt.add(cat);

                matUnit.setOID(rs.getLong(13));
                matUnit.setCode(rs.getString(7));
                vt.add(matUnit);
                if (oidVendor != 0) {
                    matCurr.setCode(rs.getString(9));
                    matVendorPrice.setOrgBuyingPrice(rs.getDouble(PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_ORG_BUYING_PRICE]));
                    matVendorPrice.setLastDiscount(rs.getDouble(PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_LAST_DISCOUNT]));
                    matVendorPrice.setCurrBuyingPrice(rs.getDouble(PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_CURR_BUYING_PRICE]));
                }
                vt.add(matCurr);
                vt.add(matVendorPrice);

                result.add(vt);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    public static Vector getListMaterial(long oidVendor, Material objMaterial,
            int limitStart, int recordToGet, String orderBy) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT * "
                    + " FROM " + TBL_MATERIAL
                    + " MAT ";
            if (oidVendor != 0) {
                sql = sql + " INNER JOIN " + PstMatVendorPrice.TBL_MATERIAL_VENDOR_PRICE + " AS VDR ON "
                        + " MAT." + fieldNames[FLD_MATERIAL_ID]
                        + " = VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_MATERIAL_ID];
            }

            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
//                strGroup = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID] +
//                        " = " + objMaterial.getCategoryId();
                strGroup = " ( MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();

                String where = PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID] + "='" + objMaterial.getCategoryId() + "' OR "
                        + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + "='" + objMaterial.getCategoryId() + "'";

                Vector masterCatAcak = PstCategory.list(0, 0, where, PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]);

                if (masterCatAcak.size() > 1) {
                    Vector materGroup = PstCategory.structureList(masterCatAcak);
                    Vector<Long> levelParent = new Vector<Long>();
                    for (int i = 0; i < materGroup.size(); i++) {
                        Category mGroup = (Category) materGroup.get(i);
                        strGroup = strGroup + " OR MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                                + " = " + mGroup.getOID();
                    }
                }
                strGroup = strGroup + ")";
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String whereClause = "";
            if (oidVendor != 0) {
                whereClause = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_VENDOR_ID] + " = " + oidVendor;
            }

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (strCode.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCode;
                } else {
                    whereClause = whereClause + strCode;
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (whereClause.length() > 0) {
                whereClause = whereClause + " AND " + fieldNames[FLD_PROCESS_STATUS] + "!=" + DELETE;
                whereClause = whereClause + " AND " + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            } else {
                whereClause = fieldNames[FLD_PROCESS_STATUS] + "!=" + DELETE;
                whereClause = whereClause + " AND " + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " ORDER BY " + orderBy;

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;
                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Material material = new Material();
                resultToObject(rs, material);
                result.add(material);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    /*
     * List untuk search componen composit
     * By Mirahu
     * 10 Juni 2011
     */
    public static Vector getListMaterialItemComposit(long oidVendor, Material objMaterial,
            int limitStart, int recordToGet, String orderBy) {
        Vector result = new Vector(1, 1);
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT DISTINCT "
                    + " MAT." + fieldNames[FLD_MATERIAL_ID]
                    + " , MAT." + fieldNames[FLD_SKU]
                    + " , MAT." + fieldNames[FLD_NAME]
                    + " , MAT." + fieldNames[FLD_CATEGORY_ID]
                    + " , CAT." + PstCategory.fieldNames[PstCategory.FLD_NAME]
                    + " , MAT." + fieldNames[FLD_DEFAULT_STOCK_UNIT_ID]
                    + " , UNT." + PstUnit.fieldNames[PstUnit.FLD_CODE]
                    + " , MAT." + fieldNames[FLD_DEFAULT_COST_CURRENCY_ID];
            if (oidVendor != 0) {
                sql = sql + " , CURR." + PstCurrencyType.fieldNames[PstCurrencyType.FLD_CODE];
            } else {
                sql = sql + " , MAT." + fieldNames[FLD_DEFAULT_COST_CURRENCY_ID];
            }
            sql = sql + " , MAT." + fieldNames[FLD_DEFAULT_COST]
                    + " , MAT." + fieldNames[FLD_SUB_CATEGORY_ID]
                    + " , MAT." + fieldNames[FLD_DEFAULT_PRICE]
                    + " , UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]
                    + " , MAT." + fieldNames[FLD_CURR_BUY_PRICE];
            if (oidVendor != 0) {
                sql = sql + " , VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_ORG_BUYING_PRICE]
                        + " , VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_LAST_DISCOUNT]
                        + " , VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_CURR_BUYING_PRICE];
            }
            sql = sql + ", MAT." + fieldNames[FLD_AVERAGE_PRICE];

            sql = sql + " FROM " + TBL_MATERIAL
                    + " MAT "
                    + " LEFT JOIN " + PstCategory.TBL_CATEGORY
                    + " CAT ON MAT." + fieldNames[FLD_CATEGORY_ID]
                    + " = CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID];

            if (oidVendor != 0) {
                sql = sql + " INNER JOIN " + PstMatVendorPrice.TBL_MATERIAL_VENDOR_PRICE + " AS VDR ON "
                        + " MAT." + fieldNames[FLD_MATERIAL_ID]
                        + " = VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_MATERIAL_ID]
                        + " LEFT JOIN " + PstCurrencyType.TBL_POS_CURRENCY_TYPE
                        + " CURR ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY]
                        + " = CURR." + PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID]
                        + " LEFT JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_BUYING_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            } else {
                sql = sql + " LEFT JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON MAT." + PstMaterial.fieldNames[PstMaterial.FLD_BUY_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            }
            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
                strGroup = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();
            }

            String strSubCategory = "";
            if (objMaterial.getSubCategoryId() > 0) {
                strSubCategory = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SUB_CATEGORY_ID]
                        + " = " + objMaterial.getSubCategoryId();
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strBarcode = "";
            if (objMaterial.getBarCode() != "" && objMaterial.getBarCode().length() > 0) {
                strBarcode = "(MAT." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " LIKE '%" + objMaterial.getBarCode() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String strCosCurrency = "";
            if (objMaterial.getDefaultCostCurrencyId() > 0) {
                strCosCurrency = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY] + " = " + objMaterial.getDefaultCostCurrencyId();
            }

            String whereClause = "";
            if (oidVendor != 0) {
                whereClause = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_VENDOR_ID] + " = " + oidVendor;
            }

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (oidVendor != 0) {
                if (strCosCurrency.length() > 0) {
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCosCurrency;
                    } else {
                        whereClause = whereClause + strCosCurrency;
                    }
                }
            }

            if (strSubCategory.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSubCategory;
                } else {
                    whereClause = whereClause + strSubCategory;
                }
            }

            if (strCode.length() > 0) {
                if (strBarcode.length() > 0) { // kondisi pencarian berdasarkan code/barcode material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND (" + strCode + " OR " + strBarcode + ")";
                    } else {
                        whereClause = whereClause + "(" + strCode + " OR " + strBarcode + ")";
                    }
                } else { // kondisi pencarian hanya berdasarkan code material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCode;
                    } else {
                        whereClause = whereClause + strCode;
                    }
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (whereClause.length() > 0) {
                whereClause = whereClause + " AND MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
                //whereClause = whereClause + " AND MAT." + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            } else {
                whereClause = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
                //whereClause = whereClause + " AND MAT." + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            }

            sql = sql + " ORDER BY " + orderBy;

            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;
                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            System.out.println("list item " + sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector vt = new Vector(1, 1);
                Material material = new Material();
                Category cat = new Category();
                Unit matUnit = new Unit();
                MatCurrency matCurr = new MatCurrency();
                MatVendorPrice matVendorPrice = new MatVendorPrice();

                material.setOID(rs.getLong(1));
                material.setSku(rs.getString(2));
                material.setName(rs.getString(3));
                material.setCategoryId(rs.getLong(4));
                material.setDefaultStockUnitId(rs.getLong(6));
                material.setDefaultCostCurrencyId(rs.getLong(8));
                material.setDefaultCost(rs.getDouble(10));
                material.setSubCategoryId(rs.getLong(11));
                material.setDefaultPrice(rs.getDouble(12));
                material.setCurrBuyPrice(rs.getDouble(14));
                if (oidVendor == 0) {
                    material.setDefaultCostCurrencyId(rs.getLong(9));
                }
                material.setAveragePrice(rs.getDouble(15));
                vt.add(material);

                cat.setName(rs.getString(5));
                vt.add(cat);

                matUnit.setOID(rs.getLong(13));
                matUnit.setCode(rs.getString(7));
                vt.add(matUnit);
                if (oidVendor != 0) {
                    matCurr.setCode(rs.getString(9));
                    matVendorPrice.setOrgBuyingPrice(rs.getDouble(PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_ORG_BUYING_PRICE]));
                    matVendorPrice.setLastDiscount(rs.getDouble(PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_LAST_DISCOUNT]));
                    matVendorPrice.setCurrBuyingPrice(rs.getDouble(PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_CURR_BUYING_PRICE]));
                }
                vt.add(matCurr);
                vt.add(matVendorPrice);

                result.add(vt);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    /**
     * this method used to list material data
     *
     * @param objMaterial
     * @return amount of Vector of material
     */
    public static int getCountMaterialItem(long oidVendor, Material objMaterial) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT( DISTINCT MAT." + fieldNames[FLD_MATERIAL_ID] + ") AS CNT"
                    + " FROM " + TBL_MATERIAL
                    + " MAT "
                    + " LEFT JOIN " + PstCategory.TBL_CATEGORY
                    + " CAT ON MAT." + fieldNames[FLD_CATEGORY_ID]
                    + " = CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID];
            if (oidVendor != 0) {
                sql = sql + " INNER JOIN " + PstMatVendorPrice.TBL_MATERIAL_VENDOR_PRICE + " AS VDR ON "
                        + " MAT." + fieldNames[FLD_MATERIAL_ID]
                        + " = VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_MATERIAL_ID]
                        + " LEFT JOIN " + PstCurrencyType.TBL_POS_CURRENCY_TYPE
                        + " CURR ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY]
                        + " = CURR." + PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID]
                        + " LEFT JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_BUYING_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            } else {
                sql = sql + " LEFT JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON MAT." + fieldNames[FLD_BUY_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            }

//            String strGroup = "";
//            if (objMaterial.getCategoryId() > 0) {
//                
//                strGroup = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID] +
//                        " = " + objMaterial.getCategoryId();
//                
//            }
            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
                //buatkan seperti
                strGroup = " ( MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();

                String where = PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID] + "='" + objMaterial.getCategoryId() + "' OR "
                        + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + "='" + objMaterial.getCategoryId() + "'";

                //Vector masterCatAcak = PstCategory.list(0,0,where,PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]);
//                
//                if(masterCatAcak.size()>1){
//                    Vector materGroup = PstCategory.structureList(masterCatAcak) ;
//                    Vector<Long> levelParent = new Vector<Long>();
//                    for(int i=0; i<materGroup.size(); i++) {
//                         Category mGroup = (Category)materGroup.get(i);
//                         strGroup=strGroup + " OR MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID] +
//                            " = " + mGroup.getOID();
//                    }
//                }
                Vector masterCatAcak = PstCategory.list(0, 0, where, PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]);

                if (masterCatAcak.size() > 1) {
                    //Vector materGroup = PstCategory.structureList(masterCatAcak) ;
                    for (int i = 0; i < masterCatAcak.size(); i++) {
                        Category mGroup = (Category) masterCatAcak.get(i);
                        strGroup = strGroup + " OR MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                                + " = " + mGroup.getOID();
                    }
                }

                strGroup = strGroup + ")";

            }

            String strSubCategory = "";
            if (objMaterial.getSubCategoryId() > 0) {
                strSubCategory = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SUB_CATEGORY_ID]
                        + " = " + objMaterial.getSubCategoryId();
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strBarcode = "";
            if (objMaterial.getBarCode() != "" && objMaterial.getBarCode().length() > 0) {
                strBarcode = "(MAT." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " LIKE '%" + objMaterial.getBarCode() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String strCostCurrency = "";
            if (objMaterial.getDefaultCostCurrencyId() > 0) {
                strCostCurrency = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY] + " = " + objMaterial.getDefaultCostCurrencyId();
            }

            String whereClause = "";
            if (oidVendor != 0) {
                whereClause = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_VENDOR_ID] + " = " + oidVendor;
            }

            if (oidVendor != 0) {
                if (strCostCurrency.length() > 0) {
                    if (whereClause.length() > 0) {
                        whereClause += " AND " + strCostCurrency;
                    } else {
                        whereClause = strCostCurrency;
                    }
                }
            }

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (strSubCategory.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSubCategory;
                } else {
                    whereClause = whereClause + strSubCategory;
                }
            }

            if (strCode.length() > 0) {
                if (strBarcode.length() > 0) { // kondisi pencarian berdasarkan code/barcode material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND (" + strCode + " OR " + strBarcode + ")";
                    } else {
                        whereClause = whereClause + "(" + strCode + " OR " + strBarcode + ")";
                    }
                } else { // kondisi pencarian hanya berdasarkan code material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCode;
                    } else {
                        whereClause = whereClause + strCode;
                    }
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (whereClause.length() > 0) {
                whereClause = whereClause + " AND MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
                whereClause = whereClause + " AND MAT." + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            } else {
                whereClause = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
                whereClause = whereClause + " AND MAT." + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            }

            System.out.println(sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    /**
     * inner joint with sell location
     *
     * @param oidVendor
     * @param objMaterial
     * @return
     */
    public static int getCountMaterialItemWithSellLocation(long oidVendor, Material objMaterial) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT( DISTINCT MAT." + fieldNames[FLD_MATERIAL_ID] + ") AS CNT"
                    + " FROM " + TBL_MATERIAL
                    + " MAT "
                    + " LEFT JOIN " + PstCategory.TBL_CATEGORY
                    + " CAT ON MAT." + fieldNames[FLD_CATEGORY_ID]
                    + " = CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID];
            if (oidVendor != 0) {
                sql = sql + " INNER JOIN " + PstMatVendorPrice.TBL_MATERIAL_VENDOR_PRICE + " AS VDR ON "
                        + " MAT." + fieldNames[FLD_MATERIAL_ID]
                        + " = VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_MATERIAL_ID]
                        + " LEFT JOIN " + PstCurrencyType.TBL_POS_CURRENCY_TYPE
                        + " CURR ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY]
                        + " = CURR." + PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID]
                        + " LEFT JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_BUYING_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            } else {
                sql = sql + " LEFT JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON MAT." + fieldNames[FLD_BUY_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            }

            if (objMaterial.getUseSellLocation() != 0) {
                sql = sql + " INNER JOIN product_location as PL "
                        + " ON PL.PRODUCT_ID "
                        + " = MAT." + fieldNames[FLD_MATERIAL_ID];
            }

            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
                //buatkan seperti
                strGroup = " ( MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();

                String where = PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID] + "='" + objMaterial.getCategoryId() + "' OR "
                        + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + "='" + objMaterial.getCategoryId() + "'";

                Vector masterCatAcak = PstCategory.list(0, 0, where, PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]);

                if (masterCatAcak.size() > 1) {
                    Vector materGroup = PstCategory.structureList(masterCatAcak);
                    Vector<Long> levelParent = new Vector<Long>();
                    for (int i = 0; i < materGroup.size(); i++) {
                        Category mGroup = (Category) materGroup.get(i);
                        strGroup = strGroup + " OR MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                                + " = " + mGroup.getOID();
                    }
                }

                strGroup = strGroup + ")";

            }

            String strSubCategory = "";
            if (objMaterial.getSubCategoryId() > 0) {
                strSubCategory = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SUB_CATEGORY_ID]
                        + " = " + objMaterial.getSubCategoryId();
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strBarcode = "";
            if (objMaterial.getBarCode() != "" && objMaterial.getBarCode().length() > 0) {
                strBarcode = "(MAT." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " LIKE '%" + objMaterial.getBarCode() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String strCostCurrency = "";
            if (objMaterial.getDefaultCostCurrencyId() > 0) {
                strCostCurrency = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY] + " = " + objMaterial.getDefaultCostCurrencyId();
            }

            String productLocation = "";
            if (objMaterial.getUseSellLocation() != 0) {
                productLocation = " PL.LOCATION_ID ='" + objMaterial.getUseSellLocation() + "'";
            }

            String whereClause = "";
            if (oidVendor != 0) {
                whereClause = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_VENDOR_ID] + " = " + oidVendor;
            }

            if (oidVendor != 0) {
                if (strCostCurrency.length() > 0) {
                    if (whereClause.length() > 0) {
                        whereClause += " AND " + strCostCurrency;
                    } else {
                        whereClause = strCostCurrency;
                    }
                }
            }

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (strSubCategory.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSubCategory;
                } else {
                    whereClause = whereClause + strSubCategory;
                }
            }

            if (strCode.length() > 0) {
                if (strBarcode.length() > 0) { // kondisi pencarian berdasarkan code/barcode material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND (" + strCode + " OR " + strBarcode + ")";
                    } else {
                        whereClause = whereClause + "(" + strCode + " OR " + strBarcode + ")";
                    }
                } else { // kondisi pencarian hanya berdasarkan code material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCode;
                    } else {
                        whereClause = whereClause + strCode;
                    }
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (productLocation.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + productLocation;
                } else {
                    whereClause = whereClause + productLocation;
                }

            }

            if (whereClause.length() > 0) {
                whereClause = whereClause + " AND MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
                whereClause = whereClause + " AND MAT." + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            } else {
                whereClause = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
                whereClause = whereClause + " AND MAT." + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            }

            System.out.println(sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    public static int getCountMaterialItem(long oidVendor, Material objMaterial, int typeConsig) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT( DISTINCT MAT." + fieldNames[FLD_MATERIAL_ID] + ") AS CNT"
                    + " FROM " + TBL_MATERIAL
                    + " MAT "
                    + " INNER JOIN " + PstCategory.TBL_CATEGORY
                    + " CAT ON MAT." + fieldNames[FLD_CATEGORY_ID]
                    + " = CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID];
            if (oidVendor != 0) {
                sql = sql + " INNER JOIN " + PstMatVendorPrice.TBL_MATERIAL_VENDOR_PRICE + " AS VDR ON "
                        + " MAT." + fieldNames[FLD_MATERIAL_ID]
                        + " = VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_MATERIAL_ID]
                        + " INNER JOIN " + PstCurrencyType.TBL_POS_CURRENCY_TYPE
                        + " CURR ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY]
                        + " = CURR." + PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID]
                        + " INNER JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_BUYING_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            } else {
                sql = sql + " INNER JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON MAT." + fieldNames[FLD_BUY_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            }
            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
                strGroup = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();
            }

            String strSubCategory = "";
            if (objMaterial.getSubCategoryId() > 0) {
                strSubCategory = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SUB_CATEGORY_ID]
                        + " = " + objMaterial.getSubCategoryId();
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String strCostCurrency = "";
            if (objMaterial.getDefaultCostCurrencyId() > 0) {
                strCostCurrency = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY] + " = " + objMaterial.getDefaultCostCurrencyId();
            }

            String whereClause = "";
            if (oidVendor != 0) {
                whereClause = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_VENDOR_ID] + " = " + oidVendor;
            }

            if (oidVendor != 0) {
                if (strCostCurrency.length() > 0) {
                    if (whereClause.length() > 0) {
                        whereClause += " AND " + strCostCurrency;
                    } else {
                        whereClause = strCostCurrency;
                    }
                }
            }

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (strSubCategory.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSubCategory;
                } else {
                    whereClause = whereClause + strSubCategory;
                }
            }

            if (strCode.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCode;
                } else {
                    whereClause = whereClause + strCode;
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (whereClause.length() > 0) {
                whereClause = whereClause + " AND MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE
                        + " AND MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CONSIGMENT_TYPE] + "=" + typeConsig;
                sql = sql + " WHERE " + whereClause;
            } else {
                whereClause = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE
                        + " AND MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CONSIGMENT_TYPE] + "=" + typeConsig;
                sql = sql + " WHERE " + whereClause;
            }

            System.out.println(sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    public static int getCountMaterial(long oidVendor, Material objMaterial) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT(MAT." + fieldNames[FLD_MATERIAL_ID] + ") AS CNT"
                    + " FROM " + TBL_MATERIAL + " MAT ";
            if (oidVendor != 0) {
                sql = sql + " INNER JOIN " + PstMatVendorPrice.TBL_MATERIAL_VENDOR_PRICE + " AS VDR ON "
                        + " MAT." + fieldNames[FLD_MATERIAL_ID]
                        + " = VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_MATERIAL_ID];
            }
            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
//                strGroup = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID] +
//                        " = " + objMaterial.getCategoryId();
                //buatkan seperti
                strGroup = "( MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();

                String where = PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID] + "='" + objMaterial.getCategoryId() + "' OR "
                        + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + "='" + objMaterial.getCategoryId() + "'";

                Vector masterCatAcak = PstCategory.list(0, 0, where, PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]);

                if (masterCatAcak.size() > 1) {
                    Vector materGroup = PstCategory.structureList(masterCatAcak);
                    Vector<Long> levelParent = new Vector<Long>();
                    for (int i = 0; i < materGroup.size(); i++) {
                        Category mGroup = (Category) materGroup.get(i);
                        strGroup = strGroup + " OR MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                                + " = " + mGroup.getOID();
                    }
                }
                strGroup = strGroup + ")";
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String whereClause = "";
            if (oidVendor != 0) {
                whereClause = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_VENDOR_ID] + " = " + oidVendor;
            }

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (strCode.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strCode;
                } else {
                    whereClause = whereClause + strCode;
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (whereClause.length() > 0) {
                whereClause = whereClause + " AND " + fieldNames[FLD_PROCESS_STATUS] + "!=" + DELETE;
                whereClause = whereClause + " AND " + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            } else {
                whereClause = fieldNames[FLD_PROCESS_STATUS] + "!=" + DELETE;
                whereClause = whereClause + " AND " + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    /**
     * this method used to list material data composit by Mirahu
     *
     * @param objMaterial
     * @return amount of Vector of material
     */
    public static int getCountMaterialItemComposit(long oidVendor, Material objMaterial) {
        int result = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT COUNT( DISTINCT MAT." + fieldNames[FLD_MATERIAL_ID] + ") AS CNT"
                    + " FROM " + TBL_MATERIAL
                    + " MAT "
                    + " LEFT JOIN " + PstCategory.TBL_CATEGORY
                    + " CAT ON MAT." + fieldNames[FLD_CATEGORY_ID]
                    + " = CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID];
            if (oidVendor != 0) {
                sql = sql + " INNER JOIN " + PstMatVendorPrice.TBL_MATERIAL_VENDOR_PRICE + " AS VDR ON "
                        + " MAT." + fieldNames[FLD_MATERIAL_ID]
                        + " = VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_MATERIAL_ID]
                        + " LEFT JOIN " + PstCurrencyType.TBL_POS_CURRENCY_TYPE
                        + " CURR ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY]
                        + " = CURR." + PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID]
                        + " LEFT JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_BUYING_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            } else {
                sql = sql + " LEFT JOIN " + PstUnit.TBL_P2_UNIT
                        + " UNT ON MAT." + fieldNames[FLD_BUY_UNIT_ID]
                        + " = UNT." + PstUnit.fieldNames[PstUnit.FLD_UNIT_ID];
            }
            String strGroup = "";
            if (objMaterial.getCategoryId() > 0) {
                strGroup = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]
                        + " = " + objMaterial.getCategoryId();
            }

            String strSubCategory = "";
            if (objMaterial.getSubCategoryId() > 0) {
                strSubCategory = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SUB_CATEGORY_ID]
                        + " = " + objMaterial.getSubCategoryId();
            }

            String strCode = "";
            if (objMaterial.getSku() != "" && objMaterial.getSku().length() > 0) {
                strCode = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_SKU]
                        + " LIKE '%" + objMaterial.getSku() + "%')";
            }

            String strBarcode = "";
            if (objMaterial.getBarCode() != "" && objMaterial.getBarCode().length() > 0) {
                strBarcode = "(MAT." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]
                        + " LIKE '%" + objMaterial.getBarCode() + "%')";
            }

            String strName = "";
            if (objMaterial.getName() != "" && objMaterial.getName().length() > 0) {
                strName = " (MAT." + PstMaterial.fieldNames[PstMaterial.FLD_NAME]
                        + " LIKE '%" + objMaterial.getName() + "%')";
            }

            String strCostCurrency = "";
            if (objMaterial.getDefaultCostCurrencyId() > 0) {
                strCostCurrency = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_PRICE_CURRENCY] + " = " + objMaterial.getDefaultCostCurrencyId();
            }

            String whereClause = "";
            if (oidVendor != 0) {
                whereClause = " VDR." + PstMatVendorPrice.fieldNames[PstMatVendorPrice.FLD_VENDOR_ID] + " = " + oidVendor;
            }

            if (oidVendor != 0) {
                if (strCostCurrency.length() > 0) {
                    if (whereClause.length() > 0) {
                        whereClause += " AND " + strCostCurrency;
                    } else {
                        whereClause = strCostCurrency;
                    }
                }
            }

            if (strGroup.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause += " AND " + strGroup;
                } else {
                    whereClause = strGroup;
                }
            }

            if (strSubCategory.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strSubCategory;
                } else {
                    whereClause = whereClause + strSubCategory;
                }
            }

            if (strCode.length() > 0) {
                if (strBarcode.length() > 0) { // kondisi pencarian berdasarkan code/barcode material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND (" + strCode + " OR " + strBarcode + ")";
                    } else {
                        whereClause = whereClause + "(" + strCode + " OR " + strBarcode + ")";
                    }
                } else { // kondisi pencarian hanya berdasarkan code material
                    if (whereClause.length() > 0) {
                        whereClause = whereClause + " AND " + strCode;
                    } else {
                        whereClause = whereClause + strCode;
                    }
                }
            }

            if (strName.length() > 0) {
                if (whereClause.length() > 0) {
                    whereClause = whereClause + " AND " + strName;
                } else {
                    whereClause = whereClause + strName;
                }
            }

            if (whereClause.length() > 0) {
                whereClause = whereClause + " AND MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
                //whereClause = whereClause + " AND MAT." + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            } else {
                whereClause = " MAT." + PstMaterial.fieldNames[PstMaterial.FLD_PROCESS_STATUS] + " != " + PstMaterial.DELETE;
                //whereClause = whereClause + " AND MAT." + fieldNames[FLD_MATERIAL_TYPE] + "=" + MAT_TYPE_REGULAR;
                sql = sql + " WHERE " + whereClause;
            }

            System.out.println(sql);

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return result;
    }

    //Untuk create new PO
    public static String getNameBySku(String sku) {
        String hasil = "";
        DBResultSet dbrs = null;
        try {
            String sql_str = " SELECT " + fieldNames[FLD_NAME]
                    + " FROM " + TBL_MATERIAL
                    + " WHERE " + fieldNames[FLD_SKU]
                    + " = '" + sku + "'";
            dbrs = DBHandler.execQueryResult(sql_str);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                hasil = rs.getString(1);
            }
            rs.close();
        } catch (Exception exc) {
            System.out.println(exc);
        } finally {
            DBResultSet.close(dbrs);
        }
        return hasil;
    }

    //Untuk create new PO
    public static Material fetchBySku(String sku) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        Material mat = new Material();
        try {
            String sql = "SELECT * FROM " + TBL_MATERIAL
                    + " WHERE " + fieldNames[FLD_SKU]
                    + " = '" + sku + "'";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                resultToObject(rs, mat);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return mat;
    }

    /**
     *
     */
    public static void prosesUpdateCatalog() {
        Vector listBrg = PstMaterial.list(0, 0, "", "");
        if (listBrg.size() > 0) {
            for (int k = 0; k < listBrg.size(); k++) {
                Material material = (Material) listBrg.get(k);

                material.setProses(Material.IS_PROCESS_INSERT_UPDATE);
                String goodName = material.getName();
                if (material.getMaterialSwitchType() == Material.WITH_USE_SWITCH_MERGE_AUTOMATIC) {
                    try {
                        Merk merk = PstMerk.fetchExc(material.getMerkId());
                        goodName = merk.getName() + Material.getSeparate() + goodName;
                    } catch (Exception e) {
                    }
                    try {
                        Category categ = PstCategory.fetchExc(material.getCategoryId());
                        goodName = categ.getName() + Material.getSeparate() + goodName;
                    } catch (Exception e) {
                    }
                }
                material.setName(goodName);
                try {
                    PstMaterial.updateExc(material);
                } catch (Exception e) {
                }
            }
        }
    }

    /*
     public static void checkSales(long oid){
     String whereClause = PstBillDetail.fieldNames[PstBillDetail.FLD_MATERIAL_ID]+"="+oid;
     Vector listMaterial = PstBillDetail.list(0,0,whereClause,"");
     if(listMaterial==null || listMaterial.size()==0){
     long result = 0;
     try{
     PstMaterial pstMaterial = new PstMaterial();
     result = pstMaterial.deleteExc(oid);
     System.out.println("Deleted material oid = "+result);
     }catch(Exception e){
     System.out.println("Error when delete material");
     }
     }else{
     System.out.println("No material oid "+oid+" in sales process");
     }
     }

     public static void checkSku(String Sku){
     String whereClause = PstMaterial.fieldNames[PstMaterial.FLD_SKU]+"='"+Sku+"'";
     String orderBy = PstMaterial.fieldNames[PstMaterial.FLD_SKU];
     Vector listMaterial = PstMaterial.list(0,0,whereClause,orderBy);
     if(listMaterial!=null && listMaterial.size()>1){
     int maxMaterial = listMaterial.size();
     for(int i=0; i<maxMaterial; i++){
     Material material = (Material)listMaterial.get(i);
     try{
     checkSales(material.getOID());
     }catch(Exception e){
     System.out.println("Error when check data to sales process");
     }
     }
     }else{
     System.out.println("Material with sku = "+Sku+" only one available");
     }
     }

     public static void main(String args[]){
     String whereClause = PstMaterial.fieldNames[PstMaterial.FLD_SKU] + " LIKE '0%'";
     String orderBy = PstMaterial.fieldNames[PstMaterial.FLD_SKU];

     Vector listMaterial = PstMaterial.list(0,0,whereClause,orderBy);
     if(listMaterial!=null && listMaterial.size()>0){
     int maxMaterial = listMaterial.size();
     System.out.println("max SKU : "+maxMaterial);
     for(int i=0; i<maxMaterial; i++){
     Material material = (Material)listMaterial.get(i);
     try{
     System.out.println("iterate ke : "+(i+1));
     checkSku(material.getSku());
     }catch(Exception e){
     System.out.println("Err when check Sku : "+e.toString());
     }
     }
     }
     }
     */
    public long updateCatalog(CatalogLink catLink) {

        //System.out.println("-");
        //System.out.println("-");
        //System.out.println("updating material on POS");
        //System.out.println("catLink.getCatalogId() : " + catLink.getCatalogId());
        long oid = 0;

        if (catLink.getCatalogId() != 0) {

            Material material = new Material();
            try {
                material = fetchExc(catLink.getCatalogId());
            } catch (Exception e) {
                System.out.println("exception update mat : " + e.toString());
            }

            //System.out.println(" POS ..  ");
            //System.out.println(" update catalog ");
            String matName = "";

            material.setSku(catLink.getCode());
            //material.setName(catLink.getName());
            material.setCategoryId(catLink.getItemCategoryId());

            if (material.getCategoryId() != 0) {
                try {
                    Category cat = PstCategory.fetchExc(material.getCategoryId());
                    matName = cat.getName();
                } catch (Exception e) {
                }
            }

            //merek .. set no merek
            //System.out.println("catalog step 1 ..");
            //pieces as default
            String strInit = PstSystemProperty.getValueByName("INTEGRATION_UNIT");
            String where = PstUnit.fieldNames[PstUnit.FLD_CODE] + "='" + strInit + "'";
            oid = 0;
            Vector vct = PstUnit.list(0, 0, where, null);
            if (vct != null && vct.size() > 0) {
                Unit unit = (Unit) vct.get(0);
                oid = unit.getOID();
            }

            //System.out.println("catalog step 2 .. oid unit : " + oid);
            //unit pcs
            //material.setDefaultStockUnitId(oid);
            //material.setBuyUnitId(oid);
            material.setDefaultCost(catLink.getCostRp());
            material.setAveragePrice(catLink.getCostRp());

            //pieces as default
            strInit = PstSystemProperty.getValueByName("INTEGRATION_NOMERK");
            where = PstMerk.fieldNames[PstMerk.FLD_NAME] + "='" + strInit + "'";
            oid = 0;
            vct = PstMerk.list(0, 0, where, null);
            if (vct != null && vct.size() > 0) {
                Merk merk = (Merk) vct.get(0);
                matName = matName + Material.getSeparate() + merk.getName();
                oid = merk.getOID();
            }

            matName = matName + Material.getSeparate() + catLink.getName();
            //System.out.println("catalog step 2 .. matName : " + matName);
            material.setName(matName);

            //System.out.println("catalog step 2 .. oid merk : " + oid);
            material.setMerkId(oid);

            //default IDR ambil dari master
            strInit = PstSystemProperty.getValueByName("INTEGRATION_CURR_RP");
            where = PstCurrencyType.fieldNames[PstCurrencyType.FLD_CODE] + "='" + strInit + "'";
            long oidRp = 0;
            vct = PstCurrencyType.list(0, 0, where, null);
            if (vct != null && vct.size() > 0) {
                CurrencyType unit = (CurrencyType) vct.get(0);
                oidRp = unit.getOID();
            }
            //material.setDefaultCostCurrencyId(oidRp);

            //System.out.println("catalog step 3 .. oidRp : "+oidRp);
            //barang
            //material.setMaterialType(MAT_TYPE_REGULAR);
            //new date
            material.setLastUpdate(new Date());

            oid = 0;
            try {
                oid = updateExc(material);

                //set up price mapping
                if (oid != 0) {
                    //default USD ambil dari master
                    strInit = PstSystemProperty.getValueByName("INTEGRATION_CURR_USD");
                    where = PstCurrencyType.fieldNames[PstCurrencyType.FLD_CODE] + "='" + strInit + "'";
                    long oidUsd = 0;
                    vct = PstCurrencyType.list(0, 0, where, null);
                    if (vct != null && vct.size() > 0) {
                        CurrencyType unit = (CurrencyType) vct.get(0);
                        oidUsd = unit.getOID();
                    }

                    //System.out.println("catalog step 5 .. process price mapping ");
                    //System.out.println("catalog step 6 .. oidUsd :  " + oidUsd);
                    //default USD ambil dari master
                    strInit = PstSystemProperty.getValueByName("INTEGRATION_PRICE_MAP");
                    where = PstPriceType.fieldNames[PstPriceType.FLD_CODE] + "='" + strInit + "'";
                    long oidPrcType = 0;
                    vct = PstPriceType.list(0, 0, where, null);
                    if (vct != null && vct.size() > 0) {
                        PriceType pt = (PriceType) vct.get(0);
                        oidPrcType = pt.getOID();
                    }

                    strInit = PstSystemProperty.getValueByName("INTEGRATION_PRICE_MAP_CREDIT");
                    where = PstPriceType.fieldNames[PstPriceType.FLD_CODE] + "='" + strInit + "'";
                    long oidPrcTypeCredit = 0;
                    vct = PstPriceType.list(0, 0, where, null);
                    if (vct != null && vct.size() > 0) {
                        PriceType pt = (PriceType) vct.get(0);
                        oidPrcTypeCredit = pt.getOID();
                    }

                    //System.out.println("catalog step 7 .. oidPrcType :  " + oidPrcType);
                    StandartRate stRateRp = PstStandartRate.getActiveStandardRate(oidRp);
                    StandartRate stRateUsd = PstStandartRate.getActiveStandardRate(oidUsd);

                    try {
                        PriceTypeMapping ptm = new PriceTypeMapping();

                        ptm = PstPriceTypeMapping.fetchExc(oidPrcType, oid, stRateRp.getOID());

                        ptm.setPrice(catLink.getPriceRp());

                        PstPriceTypeMapping.updateExc(ptm);

                        //for credit price
                        try {
                            PriceTypeMapping ptmC = new PriceTypeMapping();
                            ptmC = PstPriceTypeMapping.fetchExc(oidPrcTypeCredit, oid, stRateRp.getOID());
                            ptmC.setPrice(catLink.getPriceRp());
                            PstPriceTypeMapping.updateExc(ptmC);
                        } catch (Exception ex) {
                            PriceTypeMapping ptmC = new PriceTypeMapping();
                            ptmC.setPrice(catLink.getPriceRp());
                            ptmC.setPriceTypeId(oidPrcTypeCredit);
                            ptmC.setMaterialId(oid);
                            ptmC.setStandartRateId(stRateRp.getOID());
                            PstPriceTypeMapping.insertExc(ptmC);
                        }

                        //System.out.println("catalog step 8 .. inserting mapping rp :  ");
                    } catch (Exception e) {
                        System.out.println("error step 8 .. inserting mapping rp :  ");
                    }

                    try {
                        PriceTypeMapping ptm = new PriceTypeMapping();
                        ptm = PstPriceTypeMapping.fetchExc(oidPrcType, oid, stRateUsd.getOID());
                        ptm.setPrice(catLink.getPriceUsd());

                        PstPriceTypeMapping.updateExc(ptm);

                        //credit
                        try {
                            PriceTypeMapping ptmC = new PriceTypeMapping();
                            ptmC = PstPriceTypeMapping.fetchExc(oidPrcTypeCredit, oid, stRateUsd.getOID());
                            ptmC.setPrice(catLink.getPriceUsd());
                            PstPriceTypeMapping.updateExc(ptmC);
                        } catch (Exception ex) {
                            PriceTypeMapping ptmC = new PriceTypeMapping();
                            ptmC.setPrice(catLink.getPriceUsd());
                            ptmC.setPriceTypeId(oidPrcTypeCredit);
                            ptmC.setMaterialId(oid);
                            ptmC.setStandartRateId(stRateUsd.getOID());
                            PstPriceTypeMapping.insertExc(ptmC);
                        }

                        //System.out.println("catalog step 9 .. inserting mapping usd :  ");
                    } catch (Exception e) {
                        System.out.println("error step 9 .. inserting mapping usd :  ");
                    }

                    //oid = updateOutlet(catLink.getStores(), oid);
                }

            } catch (Exception e) {
                System.out.println("excep : " + e.toString());
            }

        }

        return oid;

    }

    public long deleteCatalog(CatalogLink catLink) {

        long oid = catLink.getCatalogId();

        if (oid != 0) {
            try {
                I_BillingDetail i_detail = (I_BillingDetail) Class.forName(I_BillingDetail.strBillingDetailCashierName).newInstance();
                if (!i_detail.isCatalogUsed(oid)) {
                    PstMaterial.deleteExc(oid);
                } else {
                    oid = 0;
                }
            } catch (Exception e) {
                oid = 0;
            }
        }

        return oid;
    }

    public long synchronizeOID(long oidOld, long oidNew) {
        String sql = "UPDATE " + TBL_MATERIAL
                + " SET " + fieldNames[FLD_MATERIAL_ID] + "=" + oidNew
                + " WHERE " + fieldNames[FLD_MATERIAL_ID] + "=" + oidOld;

        try {
            DBHandler.execUpdate(sql);
        } catch (Exception e) {
            oidNew = 0;
        }

        return oidNew;
    }

    public static int getCounter(Date dateNow) {
        int max = 0;
        DBResultSet dbrs = null;
        Date date = new Date();
        try {
            String sql = "SELECT COUNT(" + fieldNames[FLD_MATERIAL_ID]
                    + ") AS PMAX FROM " + TBL_MATERIAL;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                max = rs.getInt("PMAX") + 1;
            }

        } catch (Exception e) {
            //System.out.println("!!!!!SessOrderMaterial.getIntCode() err : " + e.toString());
        } finally {
            DBResultSet.close(dbrs);
        }
        return max;
    }

    public static String getCounter(int counter) {
        String strCounter = "";
        String str = String.valueOf(counter);
        switch (str.length()) {
            case 1:
                strCounter = "00000" + counter;
                break;
            case 2:
                strCounter = "0000" + counter;
                break;
            case 3:
                strCounter = "000" + counter;
                break;
            case 4:
                strCounter = "00" + counter;
                break;
            case 5:
                strCounter = "0" + counter;
                break;
            case 6:
                strCounter = "" + counter;
                break;
            default:
                strCounter = "" + counter;
        }
        return strCounter;
    }

    public static Vector fetchShopingCart(String oId) {

        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String priceType = PstSystemProperty.getValueByName("PRICE_TYPE_SHOPPING_CHART");
            String standartRate = PstSystemProperty.getValueByName("ID_STANDART_RATE");
            String sql;
            sql = "SELECT DISTINCT p." + fieldNames[FLD_MATERIAL_ID]
                    + ", p." + fieldNames[FLD_SKU]
                    + ", p." + fieldNames[FLD_NAME]
                    + ", p." + fieldNames[FLD_BARCODE]
                    + ", p." + fieldNames[FLD_REQUIRED_SERIAL_NUMBER]
                    + ", p." + fieldNames[FLD_EDIT_MATERIAL]
                    + ", p." + fieldNames[FLD_MATERIAL_TYPE]
                    + ", p." + fieldNames[FLD_CATEGORY_ID]
                    + ", p." + fieldNames[FLD_MERK_ID]
                    + ", pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                    + ", p." + fieldNames[FLD_DEFAULT_STOCK_UNIT_ID]
                    + " FROM " + TBL_MATERIAL
                    + " AS p INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                    + " AS pm ON pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]
                    + " = p." + fieldNames[FLD_MATERIAL_ID]
                    + " WHERE p." + fieldNames[FLD_PROCESS_STATUS]
                    + "!=2 "
                    + " AND LENGTH(p." + fieldNames[FLD_NAME]
                    + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                    + "> 0 AND pm.STANDART_RATE_ID =" + standartRate
                    + " AND pm.PRICE_TYPE_ID=" + priceType
                    + " AND p." + fieldNames[FLD_MATERIAL_ID] + "=" + oId;

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector temp = new Vector();
                Material material = new Material();
                PriceTypeMapping priceTypeMapping = new PriceTypeMapping();

                material.setMaterialId(rs.getString(1));
                material.setSku(rs.getString(2));
                material.setName(rs.getString(3));
                material.setBarcode(rs.getString(4));
                material.setRequiredSerialNumber(rs.getInt(5));
                material.setEditMaterial(rs.getInt(6));
                material.setMaterialType(rs.getInt(7));
                material.setDefaultStockUnitId(rs.getLong(11));
                material.setCategoryId(rs.getLong(8));
                material.setMerkId(rs.getLong(9));
                temp.add(material);

                priceTypeMapping.setPrice(rs.getDouble(10));
                temp.add(priceTypeMapping);
                lists.add(temp);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Vector listShopingCart(int limitStart, int recordToGet, String whereClause, String order, String standartRate, String priceType) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {

            //String standartRate = PstSystemProperty.getValueByName("ID_STANDART_RATE");
            String sql;
            sql = "SELECT DISTINCT p." + fieldNames[FLD_MATERIAL_ID]
                    + ", p." + fieldNames[FLD_SKU]
                    + ", p." + fieldNames[FLD_NAME]
                    + ", p." + fieldNames[FLD_BARCODE]
                    + ", p." + fieldNames[FLD_REQUIRED_SERIAL_NUMBER]
                    + ", p." + fieldNames[FLD_EDIT_MATERIAL]
                    + ", p." + fieldNames[FLD_MATERIAL_TYPE]
                    + ", p." + fieldNames[FLD_CATEGORY_ID]
                    + ", p." + fieldNames[FLD_MERK_ID]
                    + ", pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE]
                    + ", p." + fieldNames[FLD_DEFAULT_STOCK_UNIT_ID]
                    + ", p." + fieldNames[FLD_AVERAGE_PRICE]
                    + ", pc.NAME AS CAT_NAME "
                    + " FROM " + TBL_MATERIAL
                    + " AS p INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING
                    + " AS pm ON pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]
                    + " = p." + fieldNames[FLD_MATERIAL_ID]
                    + " INNER JOIN pos_category AS pc ON pc.CATEGORY_ID=p.CATEGORY_ID "
                    + " WHERE p." + fieldNames[FLD_PROCESS_STATUS] + "!=2 "
                    + " AND LENGTH(p." + fieldNames[FLD_NAME] + ") > 2 AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE] + " > 0 AND pm.STANDART_RATE_ID =" + standartRate
                    + " AND pm.PRICE_TYPE_ID=" + priceType + "";

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " AND " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY p." + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector temp = new Vector();
                Material material = new Material();
                PriceTypeMapping priceTypeMapping = new PriceTypeMapping();
                Category cat = new Category();
                material.setMaterialId(rs.getString(1));
                material.setSku(rs.getString(2));
                material.setName(rs.getString(3));
                material.setBarcode(rs.getString(4));
                material.setRequiredSerialNumber(rs.getInt(5));
                material.setEditMaterial(rs.getInt(6));
                material.setMaterialType(rs.getInt(7));
                material.setDefaultStockUnitId(rs.getLong(11));
                material.setCategoryId(rs.getLong(8));
                material.setMerkId(rs.getLong(9));
                material.setAveragePrice(rs.getDouble(12));
                temp.add(material);

                priceTypeMapping.setPrice(rs.getDouble(10));
                temp.add(priceTypeMapping);

                cat.setName(rs.getString("CAT_NAME"));
                temp.add(cat);

                lists.add(temp);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Vector listHastableAutoComplate(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT " + PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID] + "," + PstMaterial.fieldNames[PstMaterial.FLD_NAME] + " FROM " + TBL_MATERIAL;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            System.out.println(sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Material material = new Material();
                material.setOID(rs.getLong(PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]));
                material.setName(rs.getString(PstMaterial.fieldNames[PstMaterial.FLD_NAME]));
                //resultToObject(rs, material);
                lists.add(material);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Vector listHastableObjAutoComplate(int limitStart, int recordToGet, String whereClause, String order) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT *  FROM " + TBL_MATERIAL;
            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " WHERE " + whereClause;
            }
            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY " + order;
            }
            switch (DBHandler.DBSVR_TYPE) {
                case DBHandler.DBSVR_MYSQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
                    break;
                case DBHandler.DBSVR_POSTGRESQL:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + recordToGet + " OFFSET " + limitStart;
                    }
                    break;
                case DBHandler.DBSVR_SYBASE:
                    break;
                case DBHandler.DBSVR_ORACLE:
                    break;
                case DBHandler.DBSVR_MSSQL:
                    break;

                default:
                    if (limitStart == 0 && recordToGet == 0) {
                        sql = sql + "";
                    } else {
                        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
                    }
            }

            System.out.println(sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Material material = new Material();
                //material.setOID(rs.getLong(PstMaterial.fieldNames[PstMaterial.FLD_MATERIAL_ID]));
                //material.setName(rs.getString(PstMaterial.fieldNames[PstMaterial.FLD_NAME]));
                resultToObject(rs, material);
                lists.add(material);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    @Override
    public long insertCatalog(CatalogLink catLink) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static int countShopingCartUseLocation(String whereClause, String order, String standartRate, String priceType, int integrasi, long location) {
        int count = 0;
        DBResultSet dbrs = null;
        try {
            String sql = "";
            sql = " SELECT"
                    + " COUNT(p." + fieldNames[FLD_MATERIAL_ID] + ")"
                    + " FROM " + TBL_MATERIAL + " AS p INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING + " AS pm"
                    + " ON pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " = p." + fieldNames[FLD_MATERIAL_ID] + " "
                    + " INNER JOIN " + PstCategory.TBL_CATEGORY + " AS pc"
                    + " ON pc." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + "=p." + fieldNames[FLD_CATEGORY_ID] + "";

            if (integrasi == 1) {
                sql = sql + " INNER JOIN " + PstMatMappLocation.TBL_POS_MAT_LOCATION + " as il ON il." + PstMatMappLocation.fieldNames[PstMatMappLocation.FLD_MATERIAL_ID] + " = pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + "";
            }

            sql = sql + " WHERE p." + fieldNames[FLD_PROCESS_STATUS] + "!=2  AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE] + " > 0"
                    + " and pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + standartRate + " AND"
                    + " pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + "=" + priceType + "";

            if (integrasi == 1) {
                sql = sql + " and il." + PstMatMappLocation.fieldNames[PstMatMappLocation.FLD_LOCATION_ID] + " = " + location + "";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " AND " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY p." + order;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            
            while (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            return count;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return count;
    }

    public static Vector listShopingCartUseLocation(int limitStart, int recordToGet, String whereClause, String order, String standartRate, String priceType, int integrasi, long location) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "";
            sql = " SELECT"
                    + " p." + fieldNames[FLD_MATERIAL_ID] + ","
                    + " p." + fieldNames[FLD_SKU] + ","
                    + " p." + fieldNames[FLD_NAME] + ","
                    + " p." + fieldNames[FLD_BARCODE] + ","
                    + " p." + fieldNames[FLD_REQUIRED_SERIAL_NUMBER] + ","
                    + " p." + fieldNames[FLD_EDIT_MATERIAL] + ","
                    + " p." + fieldNames[FLD_MATERIAL_TYPE] + ","
                    + " p." + fieldNames[FLD_CATEGORY_ID] + ","
                    + " p." + fieldNames[FLD_MERK_ID] + ","
                    + " pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE] + ","
                    + " p." + fieldNames[FLD_DEFAULT_STOCK_UNIT_ID] + ","
                    + " p." + fieldNames[FLD_AVERAGE_PRICE] + ","
                    + " pc." + PstCategory.fieldNames[PstCategory.FLD_NAME] + " AS CAT_NAME"
                    + ", p." + fieldNames[FLD_MATERIAL_IMAGE]
                    + " FROM " + TBL_MATERIAL + " AS p INNER JOIN " + PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING + " AS pm"
                    + " ON pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " = p." + fieldNames[FLD_MATERIAL_ID] + " "
                    + " INNER JOIN " + PstCategory.TBL_CATEGORY + " AS pc"
                    + " ON pc." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + "=p." + fieldNames[FLD_CATEGORY_ID] + "";

            if (integrasi == 1) {
                sql = sql + " INNER JOIN " + PstMatMappLocation.TBL_POS_MAT_LOCATION + " as il ON il." + PstMatMappLocation.fieldNames[PstMatMappLocation.FLD_MATERIAL_ID] + " = pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + "";
            }

            sql = sql + " WHERE p." + fieldNames[FLD_PROCESS_STATUS] + "!=2  "
                    + " and pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + standartRate + " AND"
                    + " pm." + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + "=" + priceType + "";
            
            String useForRaditya = PstSystemProperty.getValueByName("USE_FOR_RADITYA");
            if (useForRaditya.equals("0")){
                sql = sql + " AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE] + " > 0";
            }

            if (integrasi == 1) {
                sql = sql + " and il." + PstMatMappLocation.fieldNames[PstMatMappLocation.FLD_LOCATION_ID] + " = " + location + "";
            }

            if (whereClause != null && whereClause.length() > 0) {
                sql = sql + " AND " + whereClause;
            }

            if (order != null && order.length() > 0) {
                sql = sql + " ORDER BY p." + order;
            }

            if (limitStart == 0 && recordToGet == 0) {
                sql = sql + "";
            } else {
                sql = sql + " LIMIT " + limitStart + "," + recordToGet;
            }

            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector temp = new Vector();
                Material material = new Material();
                PriceTypeMapping priceTypeMapping = new PriceTypeMapping();
                Category cat = new Category();

                material.setMaterialId(rs.getString(1));
                material.setSku(rs.getString(2));
                material.setName(rs.getString(3));
                material.setBarcode(rs.getString(fieldNames[FLD_BARCODE]));
                material.setRequiredSerialNumber(rs.getInt(5));
                material.setEditMaterial(rs.getInt(6));
                material.setMaterialType(rs.getInt(7));
                material.setDefaultStockUnitId(rs.getLong(11));
                material.setCategoryId(rs.getLong(8));
                material.setMerkId(rs.getLong(9));
                material.setAveragePrice(rs.getDouble(12));
                material.setMaterialImage(rs.getString("p." + fieldNames[FLD_MATERIAL_IMAGE]));
                temp.add(material);

                priceTypeMapping.setPrice(rs.getDouble(10));
                temp.add(priceTypeMapping);

                cat.setName(rs.getString("CAT_NAME"));
                temp.add(cat);

                lists.add(temp);
            }
            rs.close();
            return lists;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

    public static Vector listJoinMasterType(int typeGroup, String typeName, 
			long standartRate, long priceType, int type, long colorId) {
        Vector lists = new Vector();
        DBResultSet dbrs = null;
        try {
            String sql = "SELECT "
                    + "PM.MATERIAL_ID,"
                    + "  PM.SKU,"
                    + "  PM.BARCODE,"
                    + "  PM.NAME,"
                    + "  M.NAME,"
                    + "  CAT.NAME,"
                    + "  SC.NAME,"
                    + "  TM.PRICE, "
                    + "  PM.COLOR_ID "
                    + "FROM " + TBL_MATERIAL + " PM "
                    + "INNER JOIN pos_material_type_mapping MAP "
                    + "ON PM.MATERIAL_ID = MAP.MATERIAL_ID "
                    + "INNER JOIN master_type MT "
                    + "ON MAP.TYPE_ID = MT.MASTER_TYPE_ID "
                    + "INNER JOIN pos_price_type_mapping TM "
                    + "ON PM.MATERIAL_ID = TM.MATERIAL_ID "
                    + "INNER JOIN pos_merk M"
                    + "    ON PM.MERK_ID = M.MERK_ID"
                    + "  INNER JOIN pos_category CAT"
                    + "    ON PM.CATEGORY_ID = CAT.CATEGORY_ID"
                    + "  INNER JOIN pos_sub_category SC"
                    + "    ON PM.SUB_CATEGORY_ID = SC.SUB_CATEGORY_ID ";
            if (type == 2) {
                sql += "WHERE MT.TYPE_GROUP=" + typeGroup + " AND MT.MASTER_NAME = '" + typeName + "' "
                        + "AND TM.PRICE_TYPE_ID = " + priceType + " AND TM.STANDART_RATE_ID=" + standartRate;
            } else if (type == 1) {
                sql += "WHERE MT.TYPE_GROUP=" + typeGroup + " AND MT.MASTER_NAME = '" + typeName + "' "
                        + "AND TM.PRICE_TYPE_ID = " + priceType + " AND TM.STANDART_RATE_ID=" + standartRate
						+ " AND PM.COLOR_ID = "+colorId;
            } else {
                sql += "WHERE PM.BARCODE='" + typeName + "' "
                        + "AND TM.PRICE_TYPE_ID = " + priceType + " AND TM.STANDART_RATE_ID=" + standartRate;
            }
            
            sql += " GROUP BY PM.MATERIAL_ID";
            sql += " ORDER BY PM.COLOR_ID";

            System.out.println(sql);
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                Vector temp = new Vector();
                Material material = new Material();
                PriceTypeMapping priceTypeMapping = new PriceTypeMapping();
                Category cat = new Category();
                Merk merk = new Merk();
                SubCategory sc = new SubCategory();

                material.setMaterialId(rs.getString(1));
                material.setSku(rs.getString(2));
                material.setName(rs.getString(4));
                material.setBarcode(rs.getString(3));
                material.setPosColor(rs.getLong(9));
                temp.add(material);

                merk.setName(rs.getString(5));
                temp.add(merk);

                cat.setName(rs.getString(6));
                temp.add(cat);

                sc.setName(rs.getString(7));
                temp.add(sc);

                priceTypeMapping.setPrice(rs.getDouble(8));
                temp.add(priceTypeMapping);

                lists.add(temp);
            }
            rs.close();
            return lists;

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            DBResultSet.close(dbrs);
        }
        return new Vector();
    }

}
