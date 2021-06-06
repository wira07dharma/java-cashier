

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
import com.dimata.common.entity.payment.PaymentSystem;



public class PaymentRec extends Entity { 



	private long paymentTypeId;

	private long reservationId;

	private Date paymentDate;

	private double amountRp;

	private double amount$;

	private String amountText = "";

	private String receivedBy = "";

	private String recNumber = "";

	private double exchangeRate;

	private String description = "";

	private int paymentPurpose;

    private String ccIssuingBank;

    private String ccNumber;

    private Date ccExpiredDate;

    private String ccHolderName;

    private String billAddrStreet;

    private String billAddrCity;

    private String billAddrState;

    private String billAddrZipCode;

    private String billAddrCountry;

    private String billAddrPhoneNumber;

    private int paymentType;

    private int addOrSubstract;

    private long currencyUsed;

    private long receivedById;

    private long coverBillingId;

    private long telephoneCallId;

    private long installmentPaymentId;

    private long maintenanceFeePaymentId;

    private boolean setRsvToOut;

    private long shift_id;

    private long cashierId;

    private PaymentSystem paySystem = null;
    private String paySystemName = "";
    private String locationName ="";

    



    /** Holds value of property customerId. */

    private long customerId;

    

    /** Holds value of property reservationCommissionId. */

    private long reservationCommissionId;

    

    /** Holds value of property number. */

    private String number;

    

    /** Holds value of property counter. */

    private int counter;

    

    /** Holds value of property status. */

    private int status;

    

    /** Holds value of property payOut. */

    private boolean payOut;

    

    /** Holds value of property date. */

    private Date date;

    

    /** Holds value of property paymentSystemId. */

    private long paymentSystemId;

    

    /** Holds value of property clearedToId. */

    private long clearedToId;

    

    /** Holds value of property dueDate. */

    private Date dueDate;

    

    /**

     * Holds value of property dpRefId.

     */

    private long dpRefId;

    

    /**

     * Holds value of property dpRefNumber.

     */

    private String dpRefNumber;

    

	public long getPaymentTypeId(){ 

		return paymentTypeId; 

	} 



	public void setPaymentTypeId(long paymentTypeId){ 

		this.paymentTypeId = paymentTypeId; 

	} 



	public long getReservationId(){ 

		return reservationId; 

	} 



	public void setReservationId(long reservationId){ 

		this.reservationId = reservationId; 

	} 



	public Date getPaymentDate(){ 

		return paymentDate; 

	} 



	public void setPaymentDate(Date paymentDate){ 

		this.paymentDate = paymentDate; 

	} 



	public double getAmountRp(){ 
           // if(amount$>0.00){
           //     return amount$*exchangeRate + amountRp;
            //} else {
                return amountRp;
            //}
	} 



	public void setAmountRp(double amountRp){ 

		this.amountRp = amountRp; 

	} 



	public double getAmount$(){ 
               // if(amount$>0.00){
                    return amount$;// + amountRp/exchangeRate;
               // } else {
               //     return amountRp/exchangeRate;
                //}
	} 



	public void setAmount$(double amount$){ 

		this.amount$ = amount$; 

	} 



	public String getAmountText(){ 

		return amountText; 

	} 



	public void setAmountText(String amountText){ 

		if ( amountText == null ) {

			amountText = ""; 

		} 

		this.amountText = amountText; 

	} 



	public String getReceivedBy(){ 

		return receivedBy; 

	} 



	public void setReceivedBy(String receivedBy){ 

		if ( receivedBy == null ) {

			receivedBy = ""; 

		} 

		this.receivedBy = receivedBy; 

	} 



	public String getRecNumber(){ 

		return recNumber; 

	} 



	public void setRecNumber(String recNumber){ 

		if ( recNumber == null ) {

			recNumber = ""; 

		} 

		this.recNumber = recNumber; 

	} 



	public double getExchangeRate(){ 

		return exchangeRate; 

	} 



	public void setExchangeRate(double exchangeRate){ 

		this.exchangeRate = exchangeRate; 

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



	public int getPaymentPurpose(){ 

		return paymentPurpose; 

	} 



	public void setPaymentPurpose(int paymentPurpose){ 

		this.paymentPurpose = paymentPurpose; 

	}



    public String getCcIssuingBank(){ return ccIssuingBank; }



    public void setCcIssuingBank(String ccIssuingBank){ this.ccIssuingBank = ccIssuingBank; }



    public String getCcNumber(){ return ccNumber; }



    public void setCcNumber(String ccNumber){ this.ccNumber = ccNumber; }



    public Date getCcExpiredDate(){ return ccExpiredDate; }



    public void setCcExpiredDate(Date ccExpiredDate){ this.ccExpiredDate = ccExpiredDate; }



    public String getCcHolderName(){ return ccHolderName; }



    public void setCcHolderName(String ccHolderName){ this.ccHolderName = ccHolderName; }



    public String getBillAddrStreet(){ return billAddrStreet; }



    public void setBillAddrStreet(String billAddrStreet){ this.billAddrStreet = billAddrStreet; }



    public String getBillAddrCity(){ return billAddrCity; }



    public void setBillAddrCity(String billAddrCity){ this.billAddrCity = billAddrCity; }



    public String getBillAddrState(){ return billAddrState; }



    public void setBillAddrState(String billAddrState){ this.billAddrState = billAddrState; }



    public String getBillAddrZipCode(){ return billAddrZipCode; }



    public void setBillAddrZipCode(String billAddrZipCode){ this.billAddrZipCode = billAddrZipCode; }



    public String getBillAddrCountry(){ return billAddrCountry; }



    public void setBillAddrCountry(String billAddrCountry){ this.billAddrCountry = billAddrCountry; }



    public String getBillAddrPhoneNumber(){ return billAddrPhoneNumber; }



    public void setBillAddrPhoneNumber(String billAddrPhoneNumber){ this.billAddrPhoneNumber = billAddrPhoneNumber; }



    public int getPaymentType(){ return paymentType; }



    public void setPaymentType(int paymentType){ this.paymentType = paymentType; }



    public int getAddOrSubstract(){ return addOrSubstract; }



    public void setAddOrSubstract(int addOrSubstract){ this.addOrSubstract = addOrSubstract; }



    public long getCurrencyUsed(){ return currencyUsed; }



    public void setCurrencyUsed(int currencyUsed){ this.currencyUsed = currencyUsed; }



    public long getReceivedById(){ return receivedById; }



    public void setReceivedById(long receivedById){ this.receivedById = receivedById; }



    public long getCoverBillingId(){ return coverBillingId; }



    public void setCoverBillingId(long coverBillingId){ this.coverBillingId = coverBillingId; }



    public long getTelephoneCallId(){ return telephoneCallId; }



    public void setTelephoneCallId(long telephoneCallId){ this.telephoneCallId = telephoneCallId; }



    public long getInstallmentPaymentId(){ return installmentPaymentId; }



    public void setInstallmentPaymentId(long installmentPaymentId){ this.installmentPaymentId = installmentPaymentId; }



    public long getMaintenanceFeePaymentId(){ return maintenanceFeePaymentId; }



    public void setMaintenanceFeePaymentId(long maintenanceFeePaymentId){ this.maintenanceFeePaymentId = maintenanceFeePaymentId; }



    public boolean getSetRsvToOut(){ return setRsvToOut; }



    public void setSetRsvToOut(boolean setRsvToOut){ this.setRsvToOut = setRsvToOut; }

    

    /** Getter for property customerId.

     * @return Value of property customerId.

     *

     */

    public long getCustomerId() {

        return this.customerId;

    }

    

    /** Setter for property customerId.

     * @param customerId New value of property customerId.

     *

     */

    public void setCustomerId(long customerId) {

        this.customerId = customerId;

    }



    public long getShiftId(){

        return this.getShiftId();

    }

    

    public void setShiftId(long shift_Id){

        this.shift_id = shift_Id;

    }

    

       public long getCashierId(){

        return cashierId;

    }

    

    public void setCashierId(long cashierId){

        this.cashierId = cashierId;

    }



    /** Getter for property reservationCommissionId.

     * @return Value of property reservationCommissionId.

     *

     */

    public long getReservationCommissionId() {

        return this.reservationCommissionId;

    }

    

    /** Setter for property reservationCommissionId.

     * @param reservationCommissionId New value of property reservationCommissionId.

     *

     */

    public void setReservationCommissionId(long reservationCommissionId) {

        this.reservationCommissionId = reservationCommissionId;

    }

    

    /** Getter for property number.

     * @return Value of property number.

     *

     */

    public String getNumber() {

        return this.number;

    }

    

    /** Setter for property number.

     * @param number New value of property number.

     *

     */

    public void setNumber(String number) {

        this.number = number;

    }

    

    /** Getter for property counter.

     * @return Value of property counter.

     *

     */

    public int getCounter() {

        return this.counter;

    }

    

    /** Setter for property counter.

     * @param counter New value of property counter.

     *

     */

    public void setCounter(int counter) {

        this.counter = counter;

    }

    

    /** Getter for property status.

     * @return Value of property status.

     *

     */

    public int getStatus() {

        return this.status;

    }

    

    /** Setter for property status.

     * @param status New value of property status.

     *

     */

    public void setStatus(int status) {

        this.status = status;

    }

    

    /** Getter for property payOut.

     * @return Value of property payOut.

     *

     */

    public boolean getPayOut() {

        return this.payOut;

    }

    

    /** Setter for property payOut.

     * @param payOut New value of property payOut.

     *

     */

    public void setPayOut(boolean payOut) {

        this.payOut = payOut;

    }

    

    /** Getter for property date.

     * @return Value of property date.

     *

     */

    public Date getDate() {

        return this.date;

    }

    

    /** Setter for property date.

     * @param date New value of property date.

     *

     */

    public void setDate(Date date) {

        this.date = date;

    }

    

    /** Getter for property paymentSystemId.

     * @return Value of property paymentSystemId.

     *

     */

    public long getPaymentSystemId() {

        return this.paymentSystemId;

    }

    

    /** Setter for property paymentSystemId.

     * @param paymentSystemId New value of property paymentSystemId.

     *

     */

    public void setPaymentSystemId(long paymentSystemId) {

        this.paymentSystemId = paymentSystemId;

    }

    

    /** Getter for property clearedToId.

     * @return Value of property clearedToId.

     *

     */

    public long getClearedToId() {

        return this.clearedToId;

    }

    

    /** Setter for property clearedToId.

     * @param clearedToId New value of property clearedToId.

     *

     */

    public void setClearedToId(long clearedToId) {

        this.clearedToId = clearedToId;

    }

    

    /** Getter for property dueDate.

     * @return Value of property dueDate.

     *

     */

    public Date getDueDate() {

        return this.dueDate;

    }

    

    /** Setter for property dueDate.

     * @param dueDate New value of property dueDate.

     *

     */

    public void setDueDate(Date dueDate) {

        this.dueDate = dueDate;

    }  

    

    /**

     * Getter for property dpRefId.

     * @return Value of property dpRefId.

     */

    public long getDpRefId() {

        return this.dpRefId;

    }

    

    /**

     * Setter for property dpRefId.

     * @param dpRefId New value of property dpRefId.

     */

    public void setDpRefId(long dpRefId) {

        this.dpRefId = dpRefId;

    }

    

    /**

     * Getter for property dpRefNumber.

     * @return Value of property dpRefNumber.

     */

    public String getDpRefNumber() {

        return this.dpRefNumber;

    }

    

    /**

     * Setter for property dpRefNumber.

     * @param dpRefNumber New value of property dpRefNumber.

     */

    public void setDpRefNumber(String dpRefNumber) {

        this.dpRefNumber = dpRefNumber;

    }

    /**
     * @return the paySystem
     */
    public PaymentSystem getPaySystem() {
        return paySystem;
    }

    /**
     * @param paySystem the paySystem to set
     */
    public void setPaySystem(PaymentSystem paySystem) {
        this.paySystem = paySystem;
    }

    /**
     * @return the paySystemName
     */
    public String getPaySystemName() {
        return paySystemName;
    }

    /**
     * @param paySystemName the paySystemName to set
     */
    public void setPaySystemName(String paySystemName) {
        this.paySystemName = paySystemName;
    }

    /**
     * @return the locationName
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * @param locationName the locationName to set
     */
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
/*
    public double getAmount(long currencyID){
        if((long)getCurrencyUsed()==currencyID){
            return amountRp;
        } else {
            if((long)getCurrencyUsed()==currencyID){
             return amount$;
            } else {
                return 0.0;
            }
        }
    }
 * 
 */
}

