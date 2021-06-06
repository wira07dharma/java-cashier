

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

import java.util.Date;



/* package qdep */

import com.dimata.qdep.entity.*;



public class BillingRecItem extends Entity { 



	private long billingTypeItemId;

	private long billingRecId;

	private double quantity;

	private double sellingPrice;

	private double amount;

	private String itemCode = "";

	private String itemName = "";

	private double itemCost;

	private String description = "";

    private double totalService;

    private double totalTax;

    private double totalAllRate;

    private int type;

    private double costPerItem;

    private double totalCost;

    private long billingTypeId;



    public long getBillingTypeId(){

        return billingTypeId;

    }



    public void setBillingTypeId(long billingTypeId){

        this.billingTypeId = billingTypeId;

    }





    public long getBillingTypeItemId(){

		return billingTypeItemId; 

	} 



	public void setBillingTypeItemId(long billingTypeItemId){ 

		this.billingTypeItemId = billingTypeItemId; 

	} 



	public long getBillingRecId(){ 

		return billingRecId; 

	} 



	public void setBillingRecId(long billingRecId){ 

		this.billingRecId = billingRecId; 

	} 



	public double getQuantity(){ 

		return quantity; 

	} 



	public void setQuantity(double quantity){ 

		this.quantity = quantity; 

	} 



	public double getSellingPrice(){ 

		return sellingPrice; 

	} 



	public void setSellingPrice(double sellingPrice){ 

		this.sellingPrice = sellingPrice; 

	} 



	public double getAmount(){ 

		return amount; 

	} 



	public void setAmount(double amount){ 

		this.amount = amount; 

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



    public double getTotalService(){ return totalService; }



    public void setTotalService(double totalService){ this.totalService = totalService; }



    public double getTotalTax(){ return totalTax; }



    public void setTotalTax(double totalTax){ this.totalTax = totalTax; }



    public double getTotalAllRate(){ return totalAllRate; }



    public void setTotalAllRate(double totalAllRate){ this.totalAllRate = totalAllRate; }



    public int getType(){ return type; }



    public void setType(int type){ this.type = type; }



    public double getCostPerItem(){ return costPerItem; }



    public void setCostPerItem(double costPerItem){ this.costPerItem = costPerItem; }



    public double getTotalCost(){ return totalCost; }



    public void setTotalCost(double totalCost){ this.totalCost = totalCost; }

}

