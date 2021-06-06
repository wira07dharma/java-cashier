

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



public class Merk extends Entity 

{ 



	private String name = "";



	public String getName(){ 

		return name; 

	} 



	public void setName(String name){ 

		if ( name == null ) {

			name = ""; 

		} 

		this.name = name; 

	} 



}

