

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



public class OtherPayment extends Entity { 



	private String payName = "";

	private String description = "";



	public String getPayName(){ 

		return payName; 

	} 



	public void setPayName(String payName){ 

		if ( payName == null ) {

			payName = ""; 

		} 

		this.payName = payName; 

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



}

