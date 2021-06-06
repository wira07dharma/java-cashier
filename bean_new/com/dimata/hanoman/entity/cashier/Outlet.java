

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

import java.util.Date;

 

/* package qdep */

import com.dimata.qdep.entity.*;



public class Outlet extends Entity { 



	private int status;

	private long operatorId;

	private long billingTypeId;

	private long departmentId;

	private boolean addOrder = false;

	private Date startDateTime;

	private Date endDateTime;

	private double openingBalanceRp;

	private double openingBalanceUsd;

	private double cfmAmountCachRp;

	private double cfmAmountCashUsd;

	private String note = "";



	public int getStatus(){ 

		return status; 

	} 



	public void setStatus(int status){ 

		this.status = status; 

	}



	public long getOperatorId(){ 

		return operatorId; 

	} 



	public void setOperatorId(long operatorId){ 

		this.operatorId = operatorId; 

	} 



	public long getBillingTypeId(){ 

		return billingTypeId; 

	} 



	public void setBillingTypeId(long billingTypeId){ 

		this.billingTypeId = billingTypeId; 

	} 



	public long getDepartmentId(){ 

		return departmentId; 

	} 



	public void setDepartmentId(long departmentId){ 

		this.departmentId = departmentId; 

	} 



	public boolean getAddOrder(){ 

		return addOrder; 

	} 



	public void setAddOrder(boolean addOrder){ 

		this.addOrder = addOrder; 

	} 



	public Date getStartDateTime(){ 

		return startDateTime; 

	} 



	public void setStartDateTime(Date startDateTime){ 

		this.startDateTime = startDateTime; 

	} 



	public Date getEndDateTime(){ 

		return endDateTime; 

	} 



	public void setEndDateTime(Date endDateTime){ 

		this.endDateTime = endDateTime; 

	} 



	public double getOpeningBalanceRp(){ 

		return openingBalanceRp; 

	} 



	public void setOpeningBalanceRp(double openingBalanceRp){ 

		this.openingBalanceRp = openingBalanceRp; 

	} 



	public double getOpeningBalanceUsd(){ 

		return openingBalanceUsd; 

	} 



	public void setOpeningBalanceUsd(double openingBalanceUsd){ 

		this.openingBalanceUsd = openingBalanceUsd; 

	} 



	public double getCfmAmountCachRp(){ 

		return cfmAmountCachRp; 

	} 



	public void setCfmAmountCachRp(double cfmAmountCachRp){ 

		this.cfmAmountCachRp = cfmAmountCachRp; 

	} 



	public double getCfmAmountCashUsd(){ 

		return cfmAmountCashUsd; 

	} 



	public void setCfmAmountCashUsd(double cfmAmountCashUsd){ 

		this.cfmAmountCashUsd = cfmAmountCashUsd; 

	} 



	public String getNote(){ 

		return note; 

	} 



	public void setNote(String note){ 

		if ( note == null ) {

			note = ""; 

		} 

		this.note = note; 

	} 



}

