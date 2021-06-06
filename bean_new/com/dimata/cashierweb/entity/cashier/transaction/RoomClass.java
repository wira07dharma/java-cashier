 

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



public class RoomClass extends Entity { 
        public final static int BREAKFAST_NUMBER_AS_PERSON_NUMBER = 0;

	private long hotelProfileId = 0;

	private String roomClass = "";

	private int numberOfRoom = 0;
        private int sortIndex =0;

	private double roomRateRp = 0;

	private double roomExtraBedRateRp = 0;

	private double roomDayUseRateRp = 0;

	private double roomQuarterRateRp = 0;

	private double roomRate$ = 0;

	private double roomExtraBedRate$ = 0;

	private double roomDayUseRate$ = 0;

	private double roomQuanrterRate$ = 0;

	private int roomWithBreakFast;

	private double servicePercentage = 0;

	private double taxPercentage = 0;

	private String description = "";

	private String facilities = "";

    private long breakfastId;

    private boolean breakfastIncludeInRoom;

    private boolean breakfastChangable;
    private int breakfastNumber=0;
    private int roomClassType=0;

    /** Holds value of property priceType. */

    private int priceType;

    

    /** Holds value of property singlePaxPriceRp. */

    private double singlePaxPriceRp;

    

    /** Holds value of property doublePaxPriceRp. */

    private double doublePaxPriceRp;

    

    /** Holds value of property triplePaxPriceRp. */

    private double triplePaxPriceRp;

    

    /** Holds value of property singlePaxPriceUsd. */

    private double singlePaxPriceUsd;

    

    /** Holds value of property doublePaxPriceUsd. */

    private double doublePaxPriceUsd;

    

    /** Holds value of property triplePaxPriceUsd. */

    private double triplePaxPriceUsd;

    

	public long getHotelProfileId(){ 

		return hotelProfileId; 

	} 



	public void setHotelProfileId(long hotelProfileId){ 

		this.hotelProfileId = hotelProfileId; 

	} 



	public String getRoomClass(){ 

		return roomClass; 

	} 



	public void setRoomClass(String roomClass){ 

		if ( roomClass == null ) {

			roomClass = ""; 

		} 

		this.roomClass = roomClass; 

	} 



	public int getNumberOfRoom(){ 

		return numberOfRoom; 

	} 



	public void setNumberOfRoom(int numberOfRoom){ 

		this.numberOfRoom = numberOfRoom; 

	} 



	public double getRoomRateRp(){ 

		return roomRateRp; 

	} 



	public void setRoomRateRp(double roomRateRp){ 

		this.roomRateRp = roomRateRp; 

	} 



	public double getRoomExtraBedRateRp(){ 

		return roomExtraBedRateRp; 

	} 



	public void setRoomExtraBedRateRp(double roomExtraBedRateRp){ 

		this.roomExtraBedRateRp = roomExtraBedRateRp; 

	} 



	public double getRoomDayUseRateRp(){ 

		return roomDayUseRateRp; 

	} 



	public void setRoomDayUseRateRp(double roomDayUseRateRp){ 

		this.roomDayUseRateRp = roomDayUseRateRp; 

	} 



	public double getRoomQuarterRateRp(){ 

		return roomQuarterRateRp; 

	} 



	public void setRoomQuarterRateRp(double roomQuarterRateRp){ 

		this.roomQuarterRateRp = roomQuarterRateRp; 

	} 



	public double getRoomRate$(){ 

		return roomRate$; 

	} 



	public void setRoomRate$(double roomRate$){ 

		this.roomRate$ = roomRate$; 

	} 



	public double getRoomExtraBedRate$(){ 

		return roomExtraBedRate$; 

	} 



	public void setRoomExtraBedRate$(double roomExtraBedRate$){ 

		this.roomExtraBedRate$ = roomExtraBedRate$; 

	} 



	public double getRoomDayUseRate$(){ 

		return roomDayUseRate$; 

	} 



	public void setRoomDayUseRate$(double roomDayUseRate$){ 

		this.roomDayUseRate$ = roomDayUseRate$; 

	} 



	public double getRoomQuanrterRate$(){ 

		return roomQuanrterRate$; 

	} 



	public void setRoomQuanrterRate$(double roomQuanrterRate$){ 

		this.roomQuanrterRate$ = roomQuanrterRate$; 

	} 



	public int getRoomWithBreakFast(){ 

		return roomWithBreakFast; 

	} 



	public void setRoomWithBreakFast(int roomWithBreakFast){

        this.roomWithBreakFast = roomWithBreakFast;

	} 



	public double getServicePercentage(){ 

		return servicePercentage; 

	} 



	public void setServicePercentage(double servicePercentage){ 

		this.servicePercentage = servicePercentage; 

	} 



	public double getTaxPercentage(){

            return taxPercentage;

        } 



	public void setTaxPercentage(double taxPercentage){

            this.taxPercentage = taxPercentage;

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



	public String getFacilities(){ 

		return facilities; 

	} 



	public void setFacilities(String facilities){ 

		if ( facilities == null ) {

			facilities = ""; 

		} 

		this.facilities = facilities; 

	} 



    public long getBreakfastId(){ return breakfastId; }



    public void setBreakfastId(long breakfastId){ this.breakfastId = breakfastId; }



    public boolean getBreakfastIncludeInRoom(){ return breakfastIncludeInRoom; }



    public void setBreakfastIncludeInRoom(boolean breakfastIncludeInRoom){ this.breakfastIncludeInRoom = breakfastIncludeInRoom; }



    public boolean getBreakfastChangable(){ return breakfastChangable; }



    public void setBreakfastChangable(boolean breakfastChangable){ this.breakfastChangable = breakfastChangable; }

    

    /** Getter for property priceType.

     * @return Value of property priceType.

     *

     */

    public int getPriceType() {

        return this.priceType;

    }

    

    /** Setter for property priceType.

     * @param priceType New value of property priceType.

     *

     */

    public void setPriceType(int priceType) {

        this.priceType = priceType;

    }

    

    /** Getter for property singlePaxPrice.

     * @return Value of property singlePaxPrice.

     *

     */

    public double getSinglePaxPriceRp() {

        return this.singlePaxPriceRp;

    }

    

    /** Setter for property singlePaxPrice.

     * @param singlePaxPrice New value of property singlePaxPrice.

     *

     */

    public void setSinglePaxPriceRp(double singlePaxPriceRp) {

        this.singlePaxPriceRp = singlePaxPriceRp;

    }

    

    /** Getter for property doublePaxPrice.

     * @return Value of property doublePaxPrice.

     *

     */

    public double getDoublePaxPriceRp() {

        return this.doublePaxPriceRp;

    }

    

    /** Setter for property doublePaxPrice.

     * @param doublePaxPrice New value of property doublePaxPrice.

     *

     */

    public void setDoublePaxPriceRp(double doublePaxPriceRp) {

        this.doublePaxPriceRp = doublePaxPriceRp;

    }

    

    /** Getter for property triplePaxPrice.

     * @return Value of property triplePaxPrice.

     *

     */

    public double getTriplePaxPriceRp() {

        return this.triplePaxPriceRp;

    }

    

    /** Setter for property triplePaxPrice.

     * @param triplePaxPrice New value of property triplePaxPrice.

     *

     */

    public void setTriplePaxPriceRp(double triplePaxPriceRp) {

        this.triplePaxPriceRp = triplePaxPriceRp;

    }

    

    /** Getter for property singlePaxPriceUsd.

     * @return Value of property singlePaxPriceUsd.

     *

     */

    public double getSinglePaxPriceUsd() {

        return this.singlePaxPriceUsd;

    }

    

    /** Setter for property singlePaxPriceUsd.

     * @param singlePaxPriceUsd New value of property singlePaxPriceUsd.

     *

     */

    public void setSinglePaxPriceUsd(double singlePaxPriceUsd) {

        this.singlePaxPriceUsd = singlePaxPriceUsd;

    }

    

    /** Getter for property doublePaxPriceUsd.

     * @return Value of property doublePaxPriceUsd.

     *

     */

    public double getDoublePaxPriceUsd() {

        return this.doublePaxPriceUsd;

    }

    

    /** Setter for property doublePaxPriceUsd.

     * @param doublePaxPriceUsd New value of property doublePaxPriceUsd.

     *

     */

    public void setDoublePaxPriceUsd(double doublePaxPriceUsd) {

        this.doublePaxPriceUsd = doublePaxPriceUsd;

    }

    

    /** Getter for property triplePaxPriceUsd.

     * @return Value of property triplePaxPriceUsd.

     *

     */

    public double getTriplePaxPriceUsd() {
        return this.triplePaxPriceUsd;
    }
    
    /** Setter for property triplePaxPriceUsd.
     * @param triplePaxPriceUsd New value of property triplePaxPriceUsd.
     *
     */
    public void setTriplePaxPriceUsd(double triplePaxPriceUsd) {
        this.triplePaxPriceUsd = triplePaxPriceUsd;
    }

    public int getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(int sortIndex) {
        this.sortIndex = sortIndex;
    }

    /**
     * @return the breakfastNumber
     */
    public int getBreakfastNumber() {
        return breakfastNumber;
    }

    /**
     * @param breakfastNumber the breakfastNumber to set
     */
    public void setBreakfastNumber(int breakfastNumber) {
        this.breakfastNumber = breakfastNumber;
    }

    /**
     * @return the roomClassType
     */
    public int getRoomClassType() {
        return roomClassType;
    }

    /**
     * @param roomClassType the roomClassType to set
     */
    public void setRoomClassType(int roomClassType) {
        this.roomClassType = roomClassType;
    }

  

}

