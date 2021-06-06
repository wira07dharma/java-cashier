 

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



package com.dimata.cashierweb.entity.cashier.transaction; 

 

/* package java */ 

import java.util.Date;



/* package qdep */

import com.dimata.qdep.entity.*;



public class HotelRoom extends Entity { 



	private long roomClassId = 0;

	private String roomNumber = "";

	private String phoneExtension = "";

	private String note = "";

        private int newStatus;

        private int lastNightStatus;

        private Date regDate;
        
        private long parentRoomId=0;

        //add opie 20121110
        //private String roomNote="";
        private String roomDescription="";
        
        private String roomFacilities="";

        private double  roomRate$=0;

        private double roomRateRp=0;

        private double extraBed$ =0;

        private double extraBedRp=0;

        private int includeBreakfast=0;

        private double tax=0;

        private double service=0;


    /** Holds value of property sortPosition. */

    private int sortPosition;

    

	public long getRoomClassId(){ 

		return roomClassId; 

	} 



	public void setRoomClassId(long roomClassId){ 

		this.roomClassId = roomClassId; 

	} 



	public String getRoomNumber(){ 

		return roomNumber; 

	} 



	public void setRoomNumber(String roomNumber){ 

		if ( roomNumber == null ) {

			roomNumber = ""; 

		} 

		this.roomNumber = roomNumber; 

	}



	public String getPhoneExtension(){ 

		return phoneExtension; 

	} 



	public void setPhoneExtension(String phoneExtension){ 

		this.phoneExtension = phoneExtension; 

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



    public int getNewStatus(){ return newStatus; }



    public void setNewStatus(int newStatus){ this.newStatus = newStatus; }



    public int getLastNightStatus(){ return lastNightStatus; }



    public void setLastNightStatus(int lastNightStatus){ this.lastNightStatus = lastNightStatus; }



    public Date getRegDate(){ return regDate; }



    public void setRegDate(Date regDate){ this.regDate = regDate; }

    

    /** Getter for property sortPosition.

     * @return Value of property sortPosition.

     *

     */

    public int getSortPosition() {

        return this.sortPosition;

    }

    

    /** Setter for property sortPosition.

     * @param sortPosition New value of property sortPosition.

     *

     */

    public void setSortPosition(int sortPosition) {

        this.sortPosition = sortPosition;

    }

    public long getParentRoomId() {
        return parentRoomId;
    }

    public void setParentRoomId(long parentRoomId) {
        this.parentRoomId = parentRoomId;
    }

    /**
     * @return the roomDescription
     */
    public String getRoomDescription() {
        return roomDescription;
    }

    /**
     * @param roomDescription the roomDescription to set
     */
    public void setRoomDescription(String roomDescription) {
        if (roomDescription== null ) {

			roomDescription = "";

		}
        this.roomDescription = roomDescription;
    }

    /**
     * @return the roomFacilities
     */
    public String getRoomFacilities() {
        
        return roomFacilities;
    }

    /**
     * @param roomFacilities the roomFacilities to set
     */
    public void setRoomFacilities(String roomFacilities) {
        if ( roomFacilities== null ) {

			roomFacilities = ""; 

		}
        this.roomFacilities = roomFacilities;
    }

    /**
     * @return the roomRate$
     */
    public double getRoomRate$() {
        return roomRate$;
    }

    /**
     * @param roomRate$ the roomRate$ to set
     */
    public void setRoomRate$(double roomRate$) {
        this.roomRate$ = roomRate$;
    }

    /**
     * @return the roomRateRp
     */
    public double getRoomRateRp() {
        return roomRateRp;
    }

    /**
     * @param roomRateRp the roomRateRp to set
     */
    public void setRoomRateRp(double roomRateRp) {
        this.roomRateRp = roomRateRp;
    }

    /**
     * @return the extraBed$
     */
    public double getExtraBed$() {
        return extraBed$;
    }

    /**
     * @param extraBed$ the extraBed$ to set
     */
    public void setExtraBed$(double extraBed$) {
        this.extraBed$ = extraBed$;
    }

    /**
     * @return the extraBedRp
     */
    public double getExtraBedRp() {
        return extraBedRp;
    }

    /**
     * @param extraBedRp the extraBedRp to set
     */
    public void setExtraBedRp(double extraBedRp) {
        this.extraBedRp = extraBedRp;
    }

    /**
     * @return the includeBreakfast
     */
    public int getIncludeBreakfast() {
        return includeBreakfast;
    }

    /**
     * @param includeBreakfast the includeBreakfast to set
     */
    public void setIncludeBreakfast(int includeBreakfast) {
        this.includeBreakfast = includeBreakfast;
    }

    /**
     * @return the tax
     */
    public double getTax() {
        return tax;
    }

    /**
     * @param tax the tax to set
     */
    public void setTax(double tax) {
        this.tax = tax;
    }

    /**
     * @return the service
     */
    public double getService() {
        return service;
    }

    /**
     * @param service the service to set
     */
    public void setService(double service) {
        this.service = service;
    }

    

}

