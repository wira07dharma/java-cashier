 

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



public class BillingItemGroup extends Entity { 



	private String groupName = "";

        private String groupCode = "";

	private int category;

	private String note = "";



        /**

         * Holds value of property poinPrice.

         */

        private double poinPrice;

        

	public String getGroupName(){ 

		return groupName; 

	} 



	public void setGroupName(String groupName){ 

		if ( groupName == null ) {

			groupName = ""; 

		} 

		this.groupName = groupName; 

	} 

        

        public String getGroupCode(){ 

		return groupCode; 

	} 



	public void setGroupCode(String groupCode){ 

		if ( groupCode == null ) {

			groupCode = ""; 

		} 

		this.groupCode = groupCode; 

	} 





	public int getCategory(){ 

		return category; 

	} 



	public void setCategory(int category){ 

		this.category = category; 

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



        /**

         * Getter for property poinPrice.

         * @return Value of property poinPrice.

         */

        public double getPoinPrice() {

            return this.poinPrice;

        }

        

        /**

         * Setter for property poinPrice.

         * @param poinPrice New value of property poinPrice.

         */

        public void setPoinPrice(double poinPrice) {

            this.poinPrice = poinPrice;

        }

        

}

