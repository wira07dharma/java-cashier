

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



public class BillingRecHistory extends Entity { 



	private long billingRecId;

	private Date updateDate;

	private String history = "";



	public long getBillingRecId(){ 

		return billingRecId; 

	} 



	public void setBillingRecId(long billingRecId){ 

		this.billingRecId = billingRecId; 

	} 



	public Date getUpdateDate(){ 

		return updateDate; 

	} 



	public void setUpdateDate(Date updateDate){ 

		this.updateDate = updateDate; 

	} 



	public String getHistory(){ 

		return history; 

	} 



	public void setHistory(String history){ 

		if ( history == null ) {

			history = ""; 

		} 

		this.history = history; 

	} 



}

