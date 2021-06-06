

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
import java.util.Vector;

//update opie 13-07-2012
//kalau include di kurangi bukan ditambah

public class Reservation extends Entity { 



	private long travelAgentId ;

	private long membershipId ;

	private long roomClassId ;

	private long hotelRoomId ;

	private long customerId ;

	private long sourceOfBooking ;

        private long parentId ;

	private String sourceOfBookingSpec = "";

	private Date checkInDate;

	private Date chekOutDate;

	private int numberOfNight ;

	private int numAdult ;

	private int numChild ;

	private int numInfant ;

	private String specialRequirement = "";

	private double discountRp ;

	private double discount$ ;

	private double depositRp ;

	private double deposit$ ;

	private int reservationStatus ;

	private String cancelReason = "";

	private boolean extraBed = false;

	private int additionalChargeType ; 

	private double roomRateRp;

	private double surchargeRp ;

	private double compulsoryRp;

	private double extraBedRateRp ;

	private double additionalChargeRp ;

	private double totalAllRateRp ;

	private double roomRate$ ;

	private double surcharge$ ;

	private double compulsory$ ;

	private double extraBedRate$ ;

	private double additionalCharge$ ;

	private double totalAllRate$ ;

	private double incomeRateRp ;

	private double taxRp ;

	private double serviceRp ;

	private double incomeRate$ ;

	private double tax$ ;

	private double service$ ;

	private double paidRp ;

	private double paid$ ;

	private int incomeCalculationType ;

	private double exchangeRate ;

	private String note = "";

    private int chargeCurrency;

    private long membershipCustomerId;

    private long membershipUpgradeId;

    private String CIN = "";

    private int customerType;

    private long exchangeRciId;

    private double cancelFeeRp;

    private double cancelFeeUsd;

    private boolean takeOverFromTravel;

    private int resvClassification;

    private String invNumber;

    private long billingBreakfastId;

    private boolean includeBreakfast;

    private double breakfastPriceRp;

    private double breakfastPriceUsd;

    private boolean withCommision;

    private double commisionAmountRp;

    private double commisionAmountUsd;

    private long commisionContactId;

    private int commisionStatus;

    private boolean lateCheckIn;

    private double roomChargePerNightRp;

    private double roomChargePerNightUsd;

    private boolean brfChargeIncludeInRoom;

    private boolean useTravelContract;    

    private String resvNumber ="";

    private String guestName="";
    private Date CancelDate=null;
    private Date CancelInputOn=null;
    private int StatusDoc=0; // standard draft, final, posted, closed
    private String bookingCode="";
    /** Holds value of property rsvType. */

    private int rsvType;

    

    /** Holds value of property rsvGroupType. */

    private int rsvGroupType;

    

    /** Holds value of property rsvParentGroupId. */

    private long rsvParentGroupId;

    private long travelPackageId;    

    private long travelPackTypeId;

    private long contractId;

    private int discountType;

    private int discountScope;

    private double discountProcent;

    private int taxInclude;

    private int serviceInclude;

    private int numNightDiscount;

    private double totalDiscountRp;

    private double totalDiscountUsd;

    private int numberCounter;

    private boolean surchargeInclude;

    private boolean compulsoryInclude;

    private int numNightCompliment;

    private int complimentType;

    private int exchangeType;

    //dipakai oleh reservation SOB travel agent



    private int typeInvoice = 0;        

    private Date regDate;



    private boolean isTransfered;        

    private long customType;

    private String customTypeSpec = "";

     //edited by Yunny

    private int numberNightReported;

    private Date checkoutDateReported;

    private int statusReported;  

    private Date checkInTime;

    private Date checkOutTime;


    //add opie-eyek 03-08-2012 untuk reservasi group
    private int countRoom;
    
    private int complementRoom;

    private String totalGroup;

    private int includeAllGroup;

    //add opie-eyek 20130603
    //tambahan field untuk villa bugis dan sunari
    private long termsOfPayment;
    private String guarantedBy;
    private int statusCutOfDay;
    private int typeOfBooking;
    //end opie-eyek 20130603
    
    //update by fredy (2014-02-03)
    private String descriptionPackage;
    private String sourceOfBookingName;
    private String travelName;

    public int getTypeInvoice(){

        return typeInvoice;

    }



    public void setTypeInvoice(int typeInvoice){

        this.typeInvoice = typeInvoice;

    }



	public long getTravelAgentId(){

		return travelAgentId; 

	} 



	public void setTravelAgentId(long travelAgentId){ 

		this.travelAgentId = travelAgentId; 

	} 



	public long getMembershipId(){ 

		return membershipId; 

	} 



	public void setMembershipId(long membershipId){ 

		this.membershipId = membershipId; 

	} 



	public long getRoomClassId(){ 

		return roomClassId; 

	} 



	public void setRoomClassId(long roomClassId){ 

		this.roomClassId = roomClassId; 

	} 



	public long getHotelRoomId(){ 

		return hotelRoomId; 

	} 



	public void setHotelRoomId(long hotelRoomId){ 

		this.hotelRoomId = hotelRoomId; 

	} 



	public long getCustomerId(){ 

		return customerId; 

	} 



	public void setCustomerId(long customerId){ 

		this.customerId = customerId; 

	} 



	public long getSourceOfBooking(){ 

		return sourceOfBooking; 

	} 



	public void setSourceOfBooking(long sourceOfBooking){ 

		this.sourceOfBooking = sourceOfBooking; 

	} 



	public String getSourceOfBookingSpec(){ 

		return sourceOfBookingSpec; 

	} 



	public void setSourceOfBookingSpec(String sourceOfBookingSpec){ 

		if ( sourceOfBookingSpec == null ) {

			sourceOfBookingSpec = ""; 

		} 

		this.sourceOfBookingSpec = sourceOfBookingSpec; 

	} 



	public Date getCheckInDate(){ 

		return checkInDate; 

	} 



	public void setCheckInDate(Date checkInDate){ 

		this.checkInDate = checkInDate; 

	} 



	public Date getChekOutDate(){ 

		return chekOutDate; 

	} 



	public void setChekOutDate(Date chekOutDate){ 

		this.chekOutDate = chekOutDate; 

	} 



	public int getNumberOfNight(){ 

		return numberOfNight; 

	} 



	public void setNumberOfNight(int numberOfNight){ 

		this.numberOfNight = numberOfNight; 

	} 



	public int getNumAdult(){ 

		return numAdult; 

	} 



	public void setNumAdult(int numAdult){ 

		this.numAdult = numAdult; 

	} 



	public int getNumChild(){ 

		return numChild; 

	} 



	public void setNumChild(int numChild){ 

		this.numChild = numChild; 

	} 



	public int getNumInfant(){ 

		return numInfant; 

	} 



	public void setNumInfant(int numInfant){ 

		this.numInfant = numInfant; 

	} 



	public String getSpecialRequirement(){ 

		return specialRequirement; 

	} 



	public void setSpecialRequirement(String specialRequirement){ 

		if ( specialRequirement == null ) {

			specialRequirement = ""; 

		} 

		this.specialRequirement = specialRequirement; 

	} 



	public double getDiscountRp(){ 

		return discountRp; 

	} 



	public void setDiscountRp(double discountRp){ 

		this.discountRp = discountRp; 

	} 



	public double getDiscount$(){ 

		return discount$; 

	} 



	public void setDiscount$(double discount$){ 

		this.discount$ = discount$; 

	} 



	public double getDepositRp(){ 

		return depositRp; 

	} 



	public void setDepositRp(double depositRp){ 

		this.depositRp = depositRp; 

	} 



	public double getDeposit$(){ 

		return deposit$; 

	} 



	public void setDeposit$(double deposit$){ 

		this.deposit$ = deposit$; 

	} 



	public int getReservationStatus(){ 

		return reservationStatus; 

	} 



	public void setReservationStatus(int reservationStatus){ 

		this.reservationStatus = reservationStatus; 

	} 



	public String getCancelReason(){ 

		return cancelReason; 

	} 



	public void setCancelReason(String cancelReason){ 

		if ( cancelReason == null ) {

			cancelReason = ""; 

		} 

		this.cancelReason = cancelReason; 

	} 



	public boolean getExtraBed(){ 

		return extraBed; 

	} 



	public void setExtraBed(boolean extraBed){ 

		this.extraBed = extraBed; 

	} 



	public int getAdditionalChargeType(){ 

		return additionalChargeType; 

	} 



	public void setAdditionalChargeType(int additionalChargeType){ 

		this.additionalChargeType = additionalChargeType; 

	} 



	public double getRoomRateRp(){ 

		return roomRateRp; 

	} 



	public void setRoomRateRp(double roomRateRp){ 

		this.roomRateRp = roomRateRp; 

	} 



	public double getSurchargeRp(){ 

		return surchargeRp; 

	} 



	public void setSurchargeRp(double surchargeRp){ 

		this.surchargeRp = surchargeRp; 

	} 



	public double getCompulsoryRp(){ 

		return compulsoryRp; 

	} 



	public void setCompulsoryRp(double compulsoryRp){ 

		this.compulsoryRp = compulsoryRp; 

	} 



	public double getExtraBedRateRp(){ 

		return extraBedRateRp; 

	} 



	public void setExtraBedRateRp(double extraBedRateRp){ 

		this.extraBedRateRp = extraBedRateRp; 

	} 



	public double getAdditionalChargeRp(){ 

		return additionalChargeRp; 

	} 



	public void setAdditionalChargeRp(double additionalChargeRp){ 

		this.additionalChargeRp = additionalChargeRp; 

	} 



	public double getTotalAllRateRp(){ 

		return totalAllRateRp; 

	} 



	public void setTotalAllRateRp(double totalAllRateRp){ 

		this.totalAllRateRp = totalAllRateRp; 

	} 



	public double getRoomRate$(){ 

		return roomRate$; 

	} 



	public void setRoomRate$(double roomRate$){ 

		this.roomRate$ = roomRate$; 

	} 



	public double getSurcharge$(){ 

		return surcharge$; 

	} 



	public void setSurcharge$(double surcharge$){ 

		this.surcharge$ = surcharge$; 

	} 



	public double getCompulsory$(){ 

		return compulsory$; 

	} 



	public void setCompulsory$(double compulsory$){ 

		this.compulsory$ = compulsory$; 

	} 



	public double getExtraBedRate$(){ 

		return extraBedRate$; 

	} 



	public void setExtraBedRate$(double extraBedRate$){ 

		this.extraBedRate$ = extraBedRate$; 

	} 



	public double getAdditionalCharge$(){ 

		return additionalCharge$; 

	} 



	public void setAdditionalCharge$(double additionalCharge$){ 

		this.additionalCharge$ = additionalCharge$; 

	} 



	public double getTotalAllRate$(){ 

		return totalAllRate$; 

	} 



	public void setTotalAllRate$(double totalAllRate$){ 

		this.totalAllRate$ = totalAllRate$; 

	} 



	public double getIncomeRateRp(){ 

		return incomeRateRp; 

	} 



	public void setIncomeRateRp(double incomeRateRp){ 

		this.incomeRateRp = incomeRateRp; 

	} 



	public double getTaxRp(){ 

		return taxRp; 

	} 



	public void setTaxRp(double taxRp){ 

		this.taxRp = taxRp; 

	} 



	public double getServiceRp(){ 

		return serviceRp; 

	} 



	public void setServiceRp(double serviceRp){ 

		this.serviceRp = serviceRp; 

	} 



	public double getIncomeRate$(){ 

		return incomeRate$; 

	} 



	public void setIncomeRate$(double incomeRate$){ 

		this.incomeRate$ = incomeRate$; 

	} 



	public double getTax$(){ 

		return tax$; 

	} 



	public void setTax$(double tax$){ 

		this.tax$ = tax$; 

	} 



	public double getService$(){ 

		return service$; 

	} 



	public void setService$(double service$){ 

		this.service$ = service$; 

	} 



	public double getPaidRp(){ 

		return paidRp; 

	} 



	public void setPaidRp(double paidRp){ 

		this.paidRp = paidRp; 

	} 



	public double getPaid$(){ 

		return paid$; 

	} 



	public void setPaid$(double paid$){ 

		this.paid$ = paid$; 

	} 



	public int getIncomeCalculationType(){ 

		return incomeCalculationType; 

	} 



	public void setIncomeCalculationType(int incomeCalculationType){ 

		this.incomeCalculationType = incomeCalculationType; 

	} 



	public double getExchangeRate(){ 

		return exchangeRate; 

	} 



	public void setExchangeRate(double exchangeRate){ 

		this.exchangeRate = exchangeRate; 

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



    public long getParentId(){

		return parentId;

	} 



	public void setParentId(long parentId){

		this.parentId = parentId;

	}



    public String getReservationNum(){

		return resvNumber;

	}



	public void setReservationNum(String resvNumber){

		if ( resvNumber == null ) {

			resvNumber = "";

		}

		this.resvNumber = resvNumber;

	}



    public int getChargeCurrency(){ return chargeCurrency; }



    public void setChargeCurrency(int chargeCurrency){ this.chargeCurrency = chargeCurrency; }



    public long getMembershipCustomerId(){ return membershipCustomerId; }



    public void setMembershipCustomerId(long membershipCustomerId){ this.membershipCustomerId = membershipCustomerId; }



    public long getMembershipUpgradeId(){ return membershipUpgradeId; }



    public void setMembershipUpgradeId(long membershipUpgradeId){ this.membershipUpgradeId = membershipUpgradeId; }



    public String getCIN(){ return CIN; }



    public void setCIN(String CIN){ this.CIN = CIN; }



    public int getCustomerType(){ return customerType; }



    public void setCustomerType(int customerType){ this.customerType = customerType; }



    public long getExchangeRciId(){ return exchangeRciId; }



    public void setExchangeRciId(long exchangeRciId){ this.exchangeRciId = exchangeRciId; }

    public double getCancelFeeRp(){
        return cancelFeeRp;
    }

    public void setCancelFeeRp(double cancelFeeRp){ this.cancelFeeRp = cancelFeeRp; }

    public double getCancelFeeUsd(){
              return cancelFeeUsd;
    }


    public double getCancelFeeRpWTaxSvc(){
    if(getTaxInclude()==1){
        return cancelFeeRp;
    }else{
         return cancelFeeRp + serviceRp + taxRp;
    }
       
    }
     
    public double getCancelFeeUsdWTaxSvc(){
    if(getTaxInclude()==1){
      return cancelFeeUsd;
    }else{
     return cancelFeeUsd+ service$ + tax$;
    }
    }


    public void setCancelFeeUsd(double cancelFeeUsd){ this.cancelFeeUsd = cancelFeeUsd; }



    public boolean getTakeOverFromTravel(){ return takeOverFromTravel; }



    public void setTakeOverFromTravel(boolean takeOverFromTravel){ this.takeOverFromTravel = takeOverFromTravel; }



    public int getResvClassification(){ return resvClassification; }



    public void setResvClassification(int resvClassification){ this.resvClassification = resvClassification; }



    public String getInvNumber(){ return invNumber; }



    public void setInvNumber(String invNumber){

        if(invNumber==null){

           this.invNumber = ""; 

        }

        this.invNumber = invNumber; 

    }



    public long getBillingBreakfastId(){ return billingBreakfastId; }



    public void setBillingBreakfastId(long billingBreakfastId){ this.billingBreakfastId = billingBreakfastId; }



    public boolean getIncludeBreakfast(){ return includeBreakfast; }



    public void setIncludeBreakfast(boolean includeBreakfast){ this.includeBreakfast = includeBreakfast; }



    public double getBreakfastPriceRp(){ return breakfastPriceRp; }



    public void setBreakfastPriceRp(double breakfastPriceRp){ this.breakfastPriceRp = breakfastPriceRp; }



    public double getBreakfastPriceUsd(){ return breakfastPriceUsd; }



    public void setBreakfastPriceUsd(double breakfastPriceUsd){ this.breakfastPriceUsd = breakfastPriceUsd; }



    public boolean getWithCommision(){ return withCommision; }



    public void setWithCommision(boolean withCommision){ this.withCommision = withCommision; }



    public double getCommisionAmountRp(){ return commisionAmountRp; }



    public void setCommisionAmountRp(double commisionAmountRp){ this.commisionAmountRp = commisionAmountRp; }



    public double getCommisionAmountUsd(){ return commisionAmountUsd; }



    public void setCommisionAmountUsd(double commisionAmountUsd){ this.commisionAmountUsd = commisionAmountUsd; }



    public long getCommisionContactId(){ return commisionContactId; }



    public void setCommisionContactId(long commisionContactId){ this.commisionContactId = commisionContactId; }



    public int getCommisionStatus(){ return commisionStatus; }



    public void setCommisionStatus(int commisionStatus){ this.commisionStatus = commisionStatus; }



    public boolean getLateCheckIn(){ return lateCheckIn; }



    public void setLateCheckIn(boolean lateCheckIn){ this.lateCheckIn = lateCheckIn; }



    public double getRoomChargePerNightRp(){ return roomChargePerNightRp; }



    public void setRoomChargePerNightRp(double roomChargePerNightRp){ this.roomChargePerNightRp = roomChargePerNightRp; }



    public double getRoomChargePerNightUsd(){ return roomChargePerNightUsd; }



    public void setRoomChargePerNightUsd(double roomChargePerNightUsd){ this.roomChargePerNightUsd = roomChargePerNightUsd; }



    public boolean getBrfChargeIncludeInRoom(){ return brfChargeIncludeInRoom; }



    public void setBrfChargeIncludeInRoom(boolean brfChargeIncludeInRoom){ this.brfChargeIncludeInRoom = brfChargeIncludeInRoom; }



    public boolean getUseTravelContract(){ return useTravelContract; }



    public void setUseTravelContract(boolean useTravelContract){ this.useTravelContract = useTravelContract; }

    

    /** Getter for property rsvType.

     * @return Value of property rsvType.

     *

     */

    public int getRsvType() {

        return this.rsvType;

    }

    

    /** Setter for property rsvType.

     * @param rsvType New value of property rsvType.

     *

     */

    public void setRsvType(int rsvType) {

        this.rsvType = rsvType;

    }

    

    /** Getter for property rsvGroupType.

     * @return Value of property rsvGroupType.

     *

     */

    public int getRsvGroupType() {

        return this.rsvGroupType;

    }

    

    /** Setter for property rsvGroupType.

     * @param rsvGroupType New value of property rsvGroupType.

     *

     */

    public void setRsvGroupType(int rsvGroupType) {

        this.rsvGroupType = rsvGroupType;

    }

    

    /** Getter for property rsvParentGroupId.

     * @return Value of property rsvParentGroupId.

     *

     */

    public long getRsvParentGroupId() {

        return this.rsvParentGroupId;

    }

    

    /** Setter for property rsvParentGroupId.

     * @param rsvParentGroupId New value of property rsvParentGroupId.

     *

     */

    public void setRsvParentGroupId(long rsvParentGroupId) {

        this.rsvParentGroupId = rsvParentGroupId;

    }



    public long getTravelPackageId(){ return travelPackageId; }    



    public void setTravelPackageId(long travelPackageId){ this.travelPackageId = travelPackageId; }



    public long getTravelPackTypeId(){ return travelPackTypeId; }



    public void setTravelPackTypeId(long travelPackTypeId){ this.travelPackTypeId = travelPackTypeId; }



    public long getContractId(){ return contractId; }



    public void setContractId(long contractId){ this.contractId = contractId; }



    public int getDiscountType(){ return discountType; }



    public void setDiscountType(int discountType){ this.discountType = discountType; }



    public int getDiscountScope(){ return discountScope; }



    public void setDiscountScope(int discountScope){ this.discountScope = discountScope; }



    public double getDiscountProcent(){ return discountProcent; }



    public void setDiscountProcent(double discountProcent){ this.discountProcent = discountProcent; }



    public int getTaxInclude(){ return taxInclude; }



    public void setTaxInclude(int taxInclude){ this.taxInclude = taxInclude; }



    public int getServiceInclude(){ return serviceInclude; }



    public void setServiceInclude(int serviceInclude){ this.serviceInclude = serviceInclude; }



    public int getNumNightDiscount(){ return numNightDiscount; }



    public void setNumNightDiscount(int numNightDiscount){ this.numNightDiscount = numNightDiscount; }



    public double getTotalDiscountRp(){ return totalDiscountRp; }



    public void setTotalDiscountRp(double totalDiscountRp){ this.totalDiscountRp = totalDiscountRp; }



    public double getTotalDiscountUsd(){ return totalDiscountUsd; }



    public void setTotalDiscountUsd(double totalDiscountUsd){ this.totalDiscountUsd = totalDiscountUsd; }



    public int getNumberCounter(){ return numberCounter; }



    public void setNumberCounter(int numberCounter){ this.numberCounter = numberCounter; }



    public boolean getSurchargeInclude(){ return surchargeInclude; }



    public void setSurchargeInclude(boolean surchargeInclude){ this.surchargeInclude = surchargeInclude; }



    public boolean getCompulsoryInclude(){ return compulsoryInclude; }



    public void setCompulsoryInclude(boolean compulsoryInclude){ this.compulsoryInclude = compulsoryInclude; }



    public int getNumNightCompliment(){ return numNightCompliment; }



    public void setNumNightCompliment(int numNightCompliment){ this.numNightCompliment = numNightCompliment; }



    public int getComplimentType(){ return complimentType; }



    public void setComplimentType(int complimentType){ this.complimentType = complimentType; }



    public int getExchangeType(){ return exchangeType; }



    public void setExchangeType(int exchangeType){ this.exchangeType = exchangeType; }



    public Date getRegDate(){ return regDate; }



    public void setRegDate(Date regDate){ this.regDate = regDate; }

    

    /** Getter for property isTransfered.

     * @return Value of property isTransfered.

     *

     */

    public boolean getIsTransfered() {

        return this.isTransfered;

    }

    

    /** Setter for property isTransfered.

     * @param isTransfered New value of property isTransfered.

     *

     */

    public void setIsTransfered(boolean isTransfered) {

        this.isTransfered = isTransfered;

    }

    

    public long getCustomType(){ 

	return customType; 

    } 



    public void setCustomType(long customType){ 

        this.customType = customType; 

    } 



    public String getCustomTypeSpec(){ 

        return customTypeSpec; 

    } 



    public void setCustomTypeSpec(String customTypeSpec){ 

        if ( customTypeSpec == null ) {

                customTypeSpec = ""; 

        } 

        this.customTypeSpec = customTypeSpec; 

    } 

    

    /**

     * Getter for property numberNightReported.

     * @return Value of property numberNightReported.

     */

    public int getNumberNightReported() {

        return numberNightReported;

    }    

    

    /**

     * Setter for property numberNightReported.

     * @param numberNightReported New value of property numberNightReported.

     */

    public void setNumberNightReported(int numberNightReported) {

        this.numberNightReported = numberNightReported;

    }    

     

    /**

     * Getter for property checkoutDateReported.

     * @return Value of property checkoutDateReported.

     */

    public Date getCheckoutDateReported() {

        return checkoutDateReported;

    }    

    

    /**

     * Setter for property checkoutDateReported.

     * @param checkoutDateReported New value of property checkoutDateReported.

     */

    public void setCheckoutDateReported(Date checkoutDateReported) {

        this.checkoutDateReported = checkoutDateReported;

    }    

    

    /**

     * Getter for property statusReported.

     * @return Value of property statusReported.

     */

    public int getStatusReported() {

        return statusReported;

    }

    

    /**

     * Setter for property statusReported.

     * @param statusReported New value of property statusReported.

     */

    public void setStatusReported(int statusReported) {

        this.statusReported = statusReported;

    }    

    

     //update ayu untuk check in time

    public Date getCheckInTime(){ 

		return checkInTime; 

    } 



    public void setCheckInTime(Date checkInTime){ 

            this.checkInTime = checkInTime; 

    } 

    

     //update ayu untuk check in time

    public Date getCheckOutTime(){ 

		return checkOutTime; 

    } 



    public void setCheckOutTime(Date checkOutTime){ 

            this.checkOutTime = checkOutTime; 

    }

    /**
     * @return the guestName
     */
    public String getGuestName() {
        return guestName;
    }

    /**
     * @param guestName the guestName to set
     */
    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    /**
     * @return the CancelDate
     */
    public Date getCancelDate() {
        return CancelDate;
    }

    /**
     * @param CancelDate the CancelDate to set
     */
    public void setCancelDate(Date CancelDate) {
        this.CancelDate = CancelDate;
    }

    /**
     * @return the CancelInputOn
     */
    public Date getCancelInputOn() {
        return CancelInputOn;
    }

    /**
     * @param CancelInputOn the CancelInputOn to set
     */
    public void setCancelInputOn(Date CancelInputOn) {
        this.CancelInputOn = CancelInputOn;
    }

    /**
     * @return the StatusDoc
     */
    public int getStatusDoc() {
        return StatusDoc;
    }

    /**
     * @param StatusDoc the StatusDoc to set
     */
    public void setStatusDoc(int StatusDoc) {
        this.StatusDoc = StatusDoc;
    }

    /**
     * @return the bookingCode
     */
    public String getBookingCode() {
        return bookingCode;
    }

    /**
     * @param bookingCode the bookingCode to set
     */
    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    /**
     * @return the countRoom
     * add opie-eyek 03-08-2012 untuk reservasi group
     */
    public int getCountRoom() {
        return countRoom;
    }

    /**
     * @param countRoom the countRoom to set
     */
    public void setCountRoom(int countRoom) {
        this.countRoom = countRoom;
    }

    /**
     * @return the complementRoom
     */
    public int getComplementRoom() {
        return complementRoom;
    }

    /**
     * @param complementRoom the complementRoom to set
     */
    public void setComplementRoom(int complementRoom) {
        this.complementRoom = complementRoom;
    }

    /**
     * @return the includeAllGroup
     */
    public int getIncludeAllGroup() {
        return includeAllGroup;
    }

    /**
     * @param includeAllGroup the includeAllGroup to set
     */
    public void setIncludeAllGroup(int includeAllGroup) {
        this.includeAllGroup = includeAllGroup;
    }

    /**
     * @return the totalGroup
     */
    public String getTotalGroup() {
        return totalGroup;
    }

    /**
     * @param totalGroup the totalGroup to set
     */
    public void setTotalGroup(String totalGroup) {
        this.totalGroup = totalGroup;
    }


    /**
     * @return the guarantedBy
     */
    public String getGuarantedBy() {
        if(guarantedBy!=null && guarantedBy.length()>0){
            return guarantedBy;
        }else{
            return "";
        }
    }

    /**
     * @param guarantedBy the guarantedBy to set
     */
    public void setGuarantedBy(String guarantedBy) {
        this.guarantedBy = guarantedBy;
    }

    /**
     * @return the statusCutOfDay
     */
    public int getStatusCutOfDay() {
        return statusCutOfDay;
    }

    /**
     * @param statusCutOfDay the statusCutOfDay to set
     */
    public void setStatusCutOfDay(int statusCutOfDay) {
        this.statusCutOfDay = statusCutOfDay;
    }

    /**
     * @return the typeOfBooking
     */
    public int getTypeOfBooking() {
        return typeOfBooking;
    }

    /**
     * @param typeOfBooking the typeOfBooking to set
     */
    public void setTypeOfBooking(int typeOfBooking) {
        this.typeOfBooking = typeOfBooking;
    }

    /**
     * @return the termsOfPayment
     */
    public long getTermsOfPayment() {
        return termsOfPayment;
    }

    /**
     * @param termsOfPayment the termsOfPayment to set
     */
    public void setTermsOfPayment(long termsOfPayment) {
        this.termsOfPayment = termsOfPayment;
    }

    /**
     * @return the descriptionPackage
     */
    public String getDescriptionPackage() {
        return descriptionPackage;
}

    /**
     * @param descriptionPackage the descriptionPackage to set
     */
    public void setDescriptionPackage(String descriptionPackage) {
        this.descriptionPackage = descriptionPackage;
    }

    /**
     * @return the sourceOfBookingName
     */
    public String getSourceOfBookingName() {
        return sourceOfBookingName;
    }

    /**
     * @param sourceOfBookingName the sourceOfBookingName to set
     */
    public void setSourceOfBookingName(String sourceOfBookingName) {
        this.sourceOfBookingName = sourceOfBookingName;
    }

    /**
     * @return the travelName
     */
    public String getTravelName() {
        return travelName;
    }

    /**
     * @param travelName the travelName to set
     */
    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

}

