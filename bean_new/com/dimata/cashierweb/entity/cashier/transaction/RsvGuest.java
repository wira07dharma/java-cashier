

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



package com.dimata.cashierweb.entity.cashier.transaction; 

 

/* package java */ 

import java.util.Date;
import com.dimata.hanoman.entity.masterdata.Contact;


/* package qdep */

import com.dimata.qdep.entity.*;



public class RsvGuest extends Entity { 



	private long customerId;

	private long reservationId;

        private String extension;

	private int type;
        
        private Contact guest=null;



	public long getCustomerId(){ 

		return customerId; 

	} 



	public void setCustomerId(long customerId){ 

		this.customerId = customerId; 

	} 



	public long getReservationId(){ 

		return reservationId; 

	} 



	public void setReservationId(long reservationId){ 

		this.reservationId = reservationId; 

	}



    public String getExtension(){

        return extension;

    }



    public void setExtension(String extension){

        this.extension = extension;

    }



	public int getType(){

		return type; 

	} 



	public void setType(int type){ 

		this.type = type; 

	}

    public Contact getGuest () {
        return guest;
    }

    public void setGuest(Contact guest) {
        this.guest = guest;
    }



}

