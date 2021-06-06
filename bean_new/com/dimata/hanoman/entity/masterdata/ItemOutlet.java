 

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



package com.dimata.hanoman.entity.masterdata; 

 

/* package java */ 

import java.util.Date;



/* package qdep */

import com.dimata.qdep.entity.*;



public class ItemOutlet extends Entity { 





	public long getBillingTypeId(){ 

		return this.getOID(0); 

	} 



	public void setBillingTypeId(long billingTypeId){ 

		this.setOID(0, billingTypeId); 

	} 



	public long getBillingTypeItemId(){ 

		return this.getOID(1); 

	} 



	public void setBillingTypeItemId(long billingTypeItemId){ 

		this.setOID(1, billingTypeItemId); 

	} 



}

