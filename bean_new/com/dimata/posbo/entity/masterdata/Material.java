package com.dimata.posbo.entity.masterdata;

/* package java */
import com.dimata.common.entity.logger.I_LogHistory;
import com.dimata.common.entity.payment.CurrencyType;
import com.dimata.common.entity.payment.PstCurrencyType;
import java.util.Date;

/* package qdep */
import com.dimata.qdep.entity.*;
import com.dimata.common.db.DBException;

public class Material extends Entity implements I_LogHistory{

    /**
     * ini di gunakan untuk membedakan nama di gabung dengan kategori dan merk
     * atau kebalikan, input secara manual atau tidak
     */
    public static final int WITH_USE_SWITCH_MERGE_AUTOMATIC = 0;
    public static final int WITH_USE_SWITCH_MERGE_MANUAL = 1;
    private static int materialSwitchType=WITH_USE_SWITCH_MERGE_MANUAL;

    public static int getMaterialSwitchType() {
	return materialSwitchType;
    }

    public static void setMaterialSwitchType(int tp) {
	materialSwitchType=tp;
    }

    public static String getSeparate() {
	return "/";
    }
    
    public static String getSeparateLanguage() {
	return ";";
    }

    public static String getReplaceSeparate() {
	return " ";
    }
    //end ----------------
    /**
     * ini di gunakan unruk membedakan pas get barang
     * nanti proses insert/update/ view saja
     */
    public static final int IS_PROCESS_VIEW = 0;
    public static final int IS_PROCESS_INSERT_UPDATE = 1;
    public static final int IS_PROCESS_SHORT_VIEW = 2;
    private int isProses = IS_PROCESS_SHORT_VIEW;

    public int getProses() {
	return isProses;
    }

    public void setProses(int proses) {
	isProses = proses;
    }
    //end ----------------
    private String sku = "";
    private String barCode = "";
    private String name = "";
    private long merkId = 0;
    private long categoryId = 0;
    private long subCategoryId = 0;
    private long defaultStockUnitId = 0;
    private double defaultPrice = 0.00;
    private long defaultPriceCurrencyId = 0;
    private double defaultCost = 0.00;
    private long defaultCostCurrencyId = 0;
    private int defaultSupplierType = 0;
    private long supplierId = 0;
    private double priceType01 = 0.00;
    private double priceType02 = 0.00;
    private double priceType03 = 0.00;
    private int materialType = 0;
    // NEW
    private double lastDiscount = 0.00;
    private double lastVat = 0.00;
    private double currBuyPrice = 0.00;
    private long buyUnitId = 0;
    private Date expiredDate = new Date();
    private double profit = 0.0;
    private double currSellPriceRecomentation = 0.0;
    /** Holds value of property averagePrice. */
    private double averagePrice = 0;
    /** Holds value of property minimumPoint. */
    private int minimumPoint = 0;
    /** Holds value of property requiredSerialNumber. */
    private int requiredSerialNumber = 0;
    /** Holds value of property lastUpdate. */
    private Date lastUpdate;
    /** Holds value of property processStatus. */
    private int processStatus = 0;
    // new 
    // this use for diff catalog is consigment or no
    private int matTypeConsig = 0;
    
    // for request SMU
    private String gondolaCode = "";

    // for updated catalog
    private java.util.Date updateDate;

    // for update last cost cargo
    private double lastCostCargo;

    //for update edit material
    //add opie 12-06-2012
    private int editMaterial=0;

    //add opie-eyek 20130903
    private int pointSales=0;
    
    private String materialDescription="";
    private String Barcode;
    private String MaterialId;
    
    private long useSellLocation=0;
     
    public String getGondolaCode() {
        return gondolaCode;
    }

    public void setGondolaCode(String gondolaCode) {
        this.gondolaCode = gondolaCode;
    }

    public int getMatTypeConsig() {
	return matTypeConsig;
    }

    public void setMatTypeConsig(int matTypeConsig) {
	this.matTypeConsig = matTypeConsig;
    }

    public double getCurrSellPriceRecomentation() {
	return currSellPriceRecomentation;
    }

    public void setCurrSellPriceRecomentation(double currSellPriceRecomentation) {
	this.currSellPriceRecomentation = currSellPriceRecomentation;
    }

    public double getProfit() {
	return profit;
    }

    public void setProfit(double profit) {
	this.profit = profit;
    }

    public long getBuyUnitId() {
	return buyUnitId;
    }

    public void setBuyUnitId(long buyUnitId) {
	this.buyUnitId = buyUnitId;
    }

    public Date getExpiredDate() {
	return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
	this.expiredDate = expiredDate;
    }

    public double getLastDiscount() {
	return lastDiscount;
    }

    public void setLastDiscount(double lastDiscount) {
	this.lastDiscount = lastDiscount;
    }

    public double getLastVat() {
	return lastVat;
    }

    public void setLastVat(double lastVat) {
	this.lastVat = lastVat;
    }

    public double getCurrBuyPrice() {
	return currBuyPrice;
    }

    public void setCurrBuyPrice(double currBuyPrice) {
	this.currBuyPrice = currBuyPrice;
    }

    public String getSku() {
	return sku;
    }

    public void setSku(String sku) {
	if (sku == null) {
	    sku = "";
	}
	this.sku = sku; 
    }

    public String getBarCode() {
	return barCode;
    }

    public void setBarCode(String barCode) {
	this.barCode = barCode;
    }

    public String getName() {
	switch (getMaterialSwitchType()) {
	    case WITH_USE_SWITCH_MERGE_AUTOMATIC:
		if (getProses() == IS_PROCESS_VIEW) {
		    name = name.replaceAll(getSeparate(), getReplaceSeparate());
		} else if (getProses() == IS_PROCESS_SHORT_VIEW) {
		    if (name.lastIndexOf(getSeparate()) != -1) {
			name = name.substring(name.lastIndexOf(getSeparate()) + 1, name.length());
		    }
		}
	    case WITH_USE_SWITCH_MERGE_MANUAL:
		break;
	}
	return name;
    }

    public void setName(String name) {
	if (name == null) {
	    name = "";
	}
	this.name = name;
    }

    public long getMerkId() {
	return merkId;
    }

    public void setMerkId(long merkId) {
	this.merkId = merkId;
    }

    public long getCategoryId() {
	return categoryId;
    }

    public void setCategoryId(long categoryId) {
	this.categoryId = categoryId;
    }

    public long getSubCategoryId() {
	return subCategoryId;
    }

    public void setSubCategoryId(long subCategoryId) {
	this.subCategoryId = subCategoryId;
    }

    public long getDefaultStockUnitId() {
	return defaultStockUnitId;
    }

    public void setDefaultStockUnitId(long defaultSellUnitId) {
	this.defaultStockUnitId = defaultSellUnitId;
    }

    public double getDefaultPrice() {
	return defaultPrice;
    }

    public void setDefaultPrice(double defaultPrice) {
	this.defaultPrice = defaultPrice;
    }

    public long getDefaultPriceCurrencyId() {
	return defaultPriceCurrencyId;
    }

    public void setDefaultPriceCurrencyId(long defaultPriceCurrencyId) {
	this.defaultPriceCurrencyId = defaultPriceCurrencyId;
    }

    public double getDefaultCost() {
	return defaultCost;
    }

    public void setDefaultCost(double defaultCost) {
	this.defaultCost = defaultCost;
    }

    public long getDefaultCostCurrencyId() {
	return defaultCostCurrencyId;
    }

    public void setDefaultCostCurrencyId(long defaultCostCurrencyId) {
	this.defaultCostCurrencyId = defaultCostCurrencyId;
    }

    public int getDefaultSupplierType() {
	return defaultSupplierType;
    }

    public void setDefaultSupplierType(int defaultSupplierType) {
	this.defaultSupplierType = defaultSupplierType;
    }

    public long getSupplierId() {
	return supplierId;
    }

    public void setSupplierId(long supplierId) {
	this.supplierId = supplierId;
    }

    public double getPriceType01() {
	return priceType01;
    }

    public void setPriceType01(double priceType01) {
	this.priceType01 = priceType01;
    }

    public double getPriceType02() {
	return priceType02;
    }

    public void setPriceType02(double priceType02) {
	this.priceType02 = priceType02;
    }

    public double getPriceType03() {
	return priceType03;
    }

    public void setPriceType03(double priceType03) {
	this.priceType03 = priceType03;
    }

    public int getMaterialType() {
	return materialType;
    }

    public void setMaterialType(int materialType) {
	this.materialType = materialType;
    }

    /** Getter for property averagePrice.
     * @return Value of property averagePrice.
     *
     */
    public double getAveragePrice() {
	return this.averagePrice;
    }

    /** Setter for property averagePrice.
     * @param averagePrice New value of property averagePrice.
     *
     */
    public void setAveragePrice(double averagePrice) {
	this.averagePrice = averagePrice;
    }

    /** Getter for property minimumPoint.
     * @return Value of property minimumPoint.
     *
     */
    public int getMinimumPoint() {
	return this.minimumPoint;
    }

    /** Setter for property minimumPoint.
     * @param minimumPoint New value of property minimumPoint.
     *
     */
    public void setMinimumPoint(int minimumPoint) {
	this.minimumPoint = minimumPoint;
    }

    /** Getter for property requiredSerialNumber.
     * @return Value of property requiredSerialNumber.
     *
     */
    public int getRequiredSerialNumber() {
	return this.requiredSerialNumber;
    }

    /** Setter for property requiredSerialNumber.
     * @param requiredSerialNumber New value of property requiredSerialNumber.
     *
     */
    public void setRequiredSerialNumber(int requiredSerialNumber) {
	this.requiredSerialNumber = requiredSerialNumber;
    }

    /** Getter for property lastUpdate.
     * @return Value of property lastUpdate.
     *
     */
    public Date getLastUpdate() {
	return this.lastUpdate;
    }

    /** Setter for property lastUpdate.
     * @param lastUpdate New value of property lastUpdate.
     *
     */
    public void setLastUpdate(Date lastUpdate) {
	this.lastUpdate = lastUpdate;
    }

    /** Getter for property processStatus.
     * @return Value of property processStatus.
     *
     */
    public int getProcessStatus() {
	return this.processStatus;
    }

    /** Setter for property processStatus.
     * @param processStatus New value of property processStatus.
     *
     */
    public void setProcessStatus(int processStatus) {
	this.processStatus = processStatus;
    }

    public String getFullName() {
	String fullName = "";
	Material nameMaterial = new Material();
	Category nameCategory = new Category();
	Merk nameMerk = new Merk();
	SubCategory nameSubCategory = new SubCategory();
	try {
	    nameMaterial = PstMaterial.fetchExc(this.getOID());

	} catch (Exception dbe) {
	}
	if (nameMaterial.getOID() > 0) {
	    try {
		nameCategory = PstCategory.fetchExc(nameMaterial.getCategoryId());
	    } catch (Exception dbe) {
	    }
	    try {
		nameMerk = PstMerk.fetchExc(nameMaterial.getMerkId());
	    } catch (Exception dbe) {
	    }
	//nameSubCategory = PstSubCategory.fetchExc (nameMaterial.getSubCategoryId ()); 

	}
	fullName = nameCategory.getName().toUpperCase() + " " + nameMerk.getName().toUpperCase() + " " + nameMaterial.getName().toUpperCase() + " ";
	//System.out.println(fullName);  
	return fullName;
    }

    /**
     * @return the updateDate
     */
    public java.util.Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(java.util.Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return the lastCostCargo
     */
    public double getLastCostCargo() {
        return lastCostCargo;
    }

    /**
     * @param lastCostCargo the lastCostCargo to set
     */
    public void setLastCostCargo(double lastCostCargo) {
        this.lastCostCargo = lastCostCargo;
    }

    /**
     * @return the editMaterial
     */
    public int getEditMaterial() {
        return editMaterial;
}

    /**
     * @param editMaterial the editMaterial to set
     */
    public void setEditMaterial(int editMaterial) {
        this.editMaterial = editMaterial;
    }

    /**
     * @return the pointSales
     */
    public int getPointSales() {
        return pointSales;
    }

    /**
     * @param pointSales the pointSales to set
     */
    public void setPointSales(int pointSales) {
        this.pointSales = pointSales;
    }

    /**
     * @return the materialDescription
     */
    public String getMaterialDescription() {
        return materialDescription;
    }

    /**
     * @param materialDescription the materialDescription to set
     */
    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

//    update by Fitra 01-05-2014
    public String getLogDetail(Entity prevDoc) {
        String includePpnWord = "";
        Material prevMat = (Material)prevDoc;
        //DiscountType discountType = null;
        DiscountQtyMapping discountQtyMapping = new DiscountQtyMapping();
        DiscountMapping discountMapping = new DiscountMapping();
        MaterialStock materialStock = new MaterialStock();
        MaterialStock prevMatStock;
        DiscountMapping prevDiscount;
        Merk merk =new Merk();
        Unit unit = new Unit();
        
         PriceTypeMapping priceTypeMapping = new PriceTypeMapping();
        
        Category category = new Category();
    
        SubCategory subCategory = new SubCategory();
        PstCategory pstCategory = new PstCategory();
        try{
    
    
          if(this.getCategoryId()!=0)
          {
            category = PstCategory.fetchExc(getCategoryId());
    }

        }catch(Exception exc){
            
    }

        
      
        
        
         try{
         if(this.getSubCategoryId()!=0)
          {
            subCategory = PstSubCategory.fetchExc(getSubCategoryId());
    }

        }catch(Exception exc){
            
    }

         
         
         try{
         if(this.getMerkId()!=0)
          {
            merk = PstMerk.fetchExc(getMerkId());
    }
    
        }catch(Exception exc){
    
        }
    
    
        try{
         if(this.getDefaultStockUnitId()!=0)
          {
            unit = PstUnit.fetchExc(getDefaultStockUnitId());
}
         
        }catch(Exception exc){
            
        } 
      
 CurrencyType currencyType = new CurrencyType();
        
    try{
         if(this.getDefaultCostCurrencyId()!=0)
          {
            currencyType = PstCurrencyType.fetchExc(getDefaultCostCurrencyId());
          }
         
        }catch(Exception exc){
            
        }      
        
        
         
          try{
         if(this.getMerkId()!=0)
          {
            merk = PstMerk.fetchExc(getMerkId());
          }
         
        }catch(Exception exc){
            
        }

        return  
                 

                
                
                (prevMat == null ||  prevMat.getOID()==0 ||  prevMat.getMaterialType()!=this.getMaterialType() && this.getMaterialType() == 0 ?
                ("Group : Barang ;" ) : "" ) +
                
                     (prevMat == null ||  prevMat.getOID()==0 ||  prevMat.getMaterialType()!=this.getMaterialType() && this.getMaterialType() == 1 ?
                ("Group : Jasa ;" ) : "" ) +
                
                      (prevMat == null ||  prevMat.getOID()==0 ||  prevMat.getMaterialType()!=this.getMaterialType() && this.getMaterialType() == 2 ?
                ("Group : Composite ;" ) : "" ) +
                
                

                (prevMat == null ||  prevMat.getOID()==0 || prevMat.getMatTypeConsig()!=this.getMatTypeConsig() && this.getMatTypeConsig() == 0 ?
                (" Konsinyasi : Tidak ;" ) : "") +
                
                     (prevMat == null ||  prevMat.getOID()==0 || prevMat.getMatTypeConsig()!=this.getMatTypeConsig() && this.getMatTypeConsig() == 1 ?
                (" Konsinyasi : Iya ;" ) : "") +
                

                (prevMat == null ||  prevMat.getOID()==0 || prevMat.getBarCode().compareToIgnoreCase(this.getBarCode())!=0 ?
                (" Barcode : "+ this.getBarCode() +" ;" ) : "") +
                
                 (prevMat == null ||  prevMat.getOID()==0 || prevMat.getCategoryId()!=this.getCategoryId()?
                (" Category : " + category.getName() +" ;" ) : "") +
                
                 (prevMat == null ||  prevMat.getOID()==0 || prevMat.getSubCategoryId()!=this.getSubCategoryId()?
                (" SubCategory : " + subCategory.getName() +" ;" ) : "") +
                
                 (prevMat == null ||  prevMat.getOID()==0 || prevMat.getMerkId()!=this.getMerkId()?
                (" Merk : " + merk.getName() +" ;" ) : "") +
                
                
                
                
                (prevMat == null ||  prevMat.getOID()==0 || prevMat.getName().compareToIgnoreCase(this.getName())!=0 ?
                ("  Name : " + this.getName() +" ;") : "")+
                
                 (prevMat == null ||  prevMat.getOID()==0 || prevMat.getEditMaterial()!=this.getEditMaterial()?
                (" Edit Material : " + this.getEditMaterial() +" ;" ) : "") +
                
                
                
                 (prevMat == null ||  prevMat.getOID()==0 || prevMat.getMaterialDescription().compareToIgnoreCase(this.getMaterialDescription())!=0 ?
                (" Description : " + this.getMaterialDescription()+" ;") : "")+
                
               (prevMat == null ||  prevMat.getOID()==0 || prevMat.getRequiredSerialNumber()!=this.getRequiredSerialNumber() && this.getRequiredSerialNumber() == 0 ?
                (" Serial Number : Tidak Diperlukan ;" ) : "") +
                
                   (prevMat == null ||  prevMat.getOID()==0 || prevMat.getRequiredSerialNumber()!=this.getRequiredSerialNumber() && this.getRequiredSerialNumber() == 1 ?
                (" Serial Number :  Diperlukan ;" ) : "") +
                
                
                
                
                
                (prevMat == null ||  prevMat.getOID()==0 || prevMat.getMinimumPoint()!=this.getMinimumPoint()?
                (" Minimum Point : " + this.getMinimumPoint() +" ;" ) : "") +
                
                (prevMat == null ||  prevMat.getOID()==0 || prevMat.getPointSales()!=this.getPointSales()?
                ("  Point Sales : " + this.getPointSales() +" ;" ) : "") +
                
                
                  (prevMat == null ||  prevMat.getOID()==0 || prevMat.getDefaultStockUnitId()!=this.getDefaultStockUnitId()?
                ("  Unit Stok : " + unit.getName() +" ;" ) : "") +  
                
                
                  (prevMat == null ||  prevMat.getOID()==0 || prevMat.getDefaultCostCurrencyId()!=this.getDefaultCostCurrencyId()?
                ("  Jenis Mata Uang : " + currencyType.getCode() +" ;" ) : "") +
                
                (prevMat == null ||  prevMat.getOID()==0 || prevMat.getBuyUnitId()!=this.getBuyUnitId()?
                ("   : " + this.getBuyUnitId() +" ;" ) : "") +
                
                 (prevMat == null ||  prevMat.getOID()==0 || prevMat.getDefaultCost()!=this.getDefaultCost()?
                ("  Default Cost : " + this.getDefaultCost() +" ;" ) : "") +
                
                (prevMat == null ||  prevMat.getOID()==0 || prevMat.getLastVat()!=this.getLastVat()?
                ("  PPN Terakhir : " + this.getLastVat() +" ;" ) : "") +
                
                             
                (prevMat == null ||  prevMat.getOID()==0 || prevMat.getLastCostCargo()!=this.getLastCostCargo()?
                ("  Ongkos Kirim Terakhir: " + this.getLastCostCargo() +" ;" ) : "") +  
                
                (prevMat == null ||  prevMat.getOID()==0 || prevMat.getCurrBuyPrice()!=this.getCurrBuyPrice()?
                ("  Harga Beli Terakhir + PPN : " + this.getCurrBuyPrice() +" ;" ) : "") +
                
                
                
                (prevMat == null ||  prevMat.getOID()==0 || prevMat.getProfit()!=this.getProfit()?
                ("  Profit : " + this.getProfit() +" ;" ) : "") +
                
                
                (prevMat == null ||  prevMat.getOID()==0 || prevMat.getAveragePrice()!=this.getAveragePrice()?
                ("  Nilai Stock : " + this.getAveragePrice() +" ;" ) : "");
        
        
        
        
    }

    /**
     * @return the MaterialId
     */
    public String getMaterialId() {
        return MaterialId;
    }

    /**
     * @param MaterialId the MaterialId to set
     */
    public void setMaterialId(String MaterialId) {
        this.MaterialId = MaterialId;
    }

    /**
     * @return the Barcode
     */
    public String getBarcode() {
        return Barcode;
    }

    /**
     * @param Barcode the Barcode to set
     */
    public void setBarcode(String Barcode) {
        this.Barcode = Barcode;
    }

    /**
     * @return the useSellLocation
     */
    public long getUseSellLocation() {
        return useSellLocation;
    }

    /**
     * @param useSellLocation the useSellLocation to set
     */
    public void setUseSellLocation(long useSellLocation) {
        this.useSellLocation = useSellLocation;
    }
                
    
    
    
}
