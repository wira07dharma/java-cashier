

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



public class Cover extends Entity { 



	private String number = "";

	private String description = "";

	private int status;

	private int seatCapacity;

    private long departmentId;

    private long billingTypeId;

    private int type;



	public String getNumber(){ 

		return number; 

	} 



	public void setNumber(String number){ 

		if ( number == null ) {

			number = ""; 

		} 

		this.number = number; 

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



	public int getStatus(){ 

		return status; 

	} 



	public void setStatus(int status){ 

		this.status = status; 

	} 



	public int getSeatCapacity(){ 

		return seatCapacity; 

	} 



	public void setSeatCapacity(int seatCapacity){ 

		this.seatCapacity = seatCapacity; 

	} 



    public long getDepartmentId(){ return departmentId; }



    public void setDepartmentId(long departmentId){ this.departmentId = departmentId; }



    public long getBillingTypeId(){ return billingTypeId; }



    public void setBillingTypeId(long billingTypeId){ this.billingTypeId = billingTypeId; }



    public int getType(){ return type; }



    public void setType(int type){ this.type = type; }

}

