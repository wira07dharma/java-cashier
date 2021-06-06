 

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

import java.util.Date;



/* package qdep */

import com.dimata.qdep.entity.*;



public class BillingTypeItem extends Entity { 



	private long billingTypeId;

	private String itemCode = "";

	private String itemName = "";

	private double sellingPrice;

	private double itemCost;

	private String description = "";

        private long billingItemGroupId;

        private double sellingPriceUsd;

        private double itemCostUsd;

        private int type;

        

        /**

         * Holds value of property merkId.

         */

        private long merkId;        



        /**

         * Holds value of property subCategoryId.

         */

        private long subCategoryId;

        

        /**

         * Holds value of property defaultSellUnitId.

         */

        private long defaultSellUnitId;

        

        /**

         * Holds value of property defaultPrice.

         */

        private int defaultPrice;

        

        /**

         * Holds value of property defaultPriceCurrencyId.

         */

        private long defaultPriceCurrencyId;

        

        /**

         * Holds value of property supplierId.

         */

        private long supplierId;

        

        /**

         * Holds value of property defaultSupplierType.

         */

        private int defaultSupplierType;

        

        /**

         * Holds value of property buyUnitId.

         */

        private long buyUnitId;

        

	public long getBillingTypeId(){ 

		return billingTypeId; 

	} 



	public void setBillingTypeId(long billingTypeId){ 

		this.billingTypeId = billingTypeId; 

	} 



	public String getItemCode(){ 

		return itemCode; 

	} 



	public void setItemCode(String itemCode){ 

		if ( itemCode == null ) {

			itemCode = ""; 

		} 

		this.itemCode = itemCode; 

	} 



	public String getItemName(){ 

		return itemName; 

	} 



	public void setItemName(String itemName){ 

		if ( itemName == null ) {

			itemName = ""; 

		} 

		this.itemName = itemName; 

	} 



	public double getSellingPrice(){ 

		return sellingPrice; 

	} 



	public void setSellingPrice(double sellingPrice){ 

		this.sellingPrice = sellingPrice; 

	} 



	public double getItemCost(){ 

		return itemCost; 

	} 



	public void setItemCost(double itemCost){ 

		this.itemCost = itemCost; 

	} 



	public String getDescription(){ 

		return description; 

	} 



	public void setDescription(String description){ 

		if ( description == null ) {

			description = ""; 

		} 

		this.description = description; 

	}



    public long getBillingItemGroupId(){ return billingItemGroupId; }



    public void setBillingItemGroupId(long billingItemGroupId){ this.billingItemGroupId = billingItemGroupId; }



    public double getSellingPriceUsd(){ return sellingPriceUsd; }



    public void setSellingPriceUsd(double sellingPriceUsd){ this.sellingPriceUsd = sellingPriceUsd; }



    public double getItemCostUsd(){ return itemCostUsd; }



    public void setItemCostUsd(double itemCostUsd){ this.itemCostUsd = itemCostUsd; }



    public int getType(){ return type; }



    public void setType(int type){ this.type = type; }

    

    /**

     * Getter for property merkId.

     * @return Value of property merkId.

     */

    public long getMerkId() {

        return this.merkId;

    }

    

    /**

     * Setter for property merkId.

     * @param merkId New value of property merkId.

     */

    public void setMerkId(long merkId) {

        this.merkId = merkId;

    }

    

    /**

     * Getter for property subCategoryId.

     * @return Value of property subCategoryId.

     */

    public long getSubCategoryId() {

        return this.subCategoryId;

    }

    

    /**

     * Setter for property subCategoryId.

     * @param subCategoryId New value of property subCategoryId.

     */

    public void setSubCategoryId(long subCategoryId) {

        this.subCategoryId = subCategoryId;

    }

    

    /**

     * Getter for property defaultSellUnitId.

     * @return Value of property defaultSellUnitId.

     */

    public long getDefaultSellUnitId() {

        return this.defaultSellUnitId;

    }

    

    /**

     * Setter for property defaultSellUnitId.

     * @param defaultSellUnitId New value of property defaultSellUnitId.

     */

    public void setDefaultSellUnitId(long defaultSellUnitId) {

        this.defaultSellUnitId = defaultSellUnitId;

    }

    

    /**

     * Getter for property defaultPrice.

     * @return Value of property defaultPrice.

     */

    public int getDefaultPrice() {

        return this.defaultPrice;

    }

    

    /**

     * Setter for property defaultPrice.

     * @param defaultPrice New value of property defaultPrice.

     */

    public void setDefaultPrice(int defaultPrice) {

        this.defaultPrice = defaultPrice;

    }

    

    /**

     * Getter for property defaultPriceCurrencyId.

     * @return Value of property defaultPriceCurrencyId.

     */

    public long getDefaultPriceCurrencyId() {

        return this.defaultPriceCurrencyId;

    }

    

    /**

     * Setter for property defaultPriceCurrencyId.

     * @param defaultPriceCurrencyId New value of property defaultPriceCurrencyId.

     */

    public void setDefaultPriceCurrencyId(long defaultPriceCurrencyId) {

        this.defaultPriceCurrencyId = defaultPriceCurrencyId;

    }

    

    /**

     * Getter for property supplierId.

     * @return Value of property supplierId.

     */

    public long getSupplierId() {

        return this.supplierId;

    }

    

    /**

     * Setter for property supplierId.

     * @param supplierId New value of property supplierId.

     */

    public void setSupplierId(long supplierId) {

        this.supplierId = supplierId;

    }

    

    /**

     * Getter for property defaultSupplierType.

     * @return Value of property defaultSupplierType.

     */

    public int getDefaultSupplierType() {

        return this.defaultSupplierType;

    }

    

    /**

     * Setter for property defaultSupplierType.

     * @param defaultSupplierType New value of property defaultSupplierType.

     */

    public void setDefaultSupplierType(int defaultSupplierType) {

        this.defaultSupplierType = defaultSupplierType;

    }

    

    /**

     * Getter for property buyUnitId.

     * @return Value of property buyUnitId.

     */

    public long getBuyUnitId() {

        return this.buyUnitId;

    }

    

    /**

     * Setter for property buyUnitId.

     * @param buyUnitId New value of property buyUnitId.

     */

    public void setBuyUnitId(long buyUnitId) {

        this.buyUnitId = buyUnitId;

    }

    

}

