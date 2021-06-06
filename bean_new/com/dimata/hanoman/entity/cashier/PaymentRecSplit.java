

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



public class PaymentRecSplit extends Entity { 



	private long reservationId;

	private long installmentPaymentId;

	private long maintenanceFeePaymentId;

	private long paymentRecId;

	private long coverBillingId;

	private long telephoneCallId;

	private double amountRp;

	private double amountUsd;

	private long currencyUsed;

	private int addOrSubstract;

	private double exchangeRate;

	private int paymentPurpose;

	private Date paymentDate;

          private long cashierId;



          /** Holds value of property number. */

          private String number;

          

          /** Holds value of property counter. */

          private int counter;

          

          /** Holds value of property reservationCommissionId. */

          private long reservationCommissionId;

          

          /** Holds value of property status. */

          private int status;

          

          /** Holds value of property payOut. */

          private boolean payOut;

          

          /** Holds value of property date. */

          private Date date;

          

          /** Holds value of property reservationChargeId. */

          private long reservationChargeId;

          

          /** Holds value of property docRefNumber. */

          private String docRefNumber;

          

          /** Holds value of property adjustmentId. */

          private long adjustmentId;

          

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

          

	public long getReservationId(){ 

		return reservationId; 

	} 



	public void setReservationId(long reservationId){ 

		this.reservationId = reservationId; 

	} 



	public long getInstallmentPaymentId(){ 

		return installmentPaymentId; 

	} 



	public void setInstallmentPaymentId(long installmentPaymentId){ 

		this.installmentPaymentId = installmentPaymentId; 

	} 



	public long getMaintenanceFeePaymentId(){ 

		return maintenanceFeePaymentId; 

	} 



	public void setMaintenanceFeePaymentId(long maintenanceFeePaymentId){ 

		this.maintenanceFeePaymentId = maintenanceFeePaymentId; 

	} 



	public long getPaymentRecId(){ 

		return paymentRecId; 

	} 



	public void setPaymentRecId(long paymentRecId){ 

		this.paymentRecId = paymentRecId; 

	} 



	public long getCoverBillingId(){ 

		return coverBillingId; 

	} 



	public void setCoverBillingId(long coverBillingId){ 

		this.coverBillingId = coverBillingId; 

	} 



	public long getTelephoneCallId(){ 

		return telephoneCallId; 

	} 



	public void setTelephoneCallId(long telephoneCallId){ 

		this.telephoneCallId = telephoneCallId; 

	} 



	public double getAmountRp(){ 

		return amountRp; 

	} 



	public void setAmountRp(double amountRp){ 

		this.amountRp = amountRp; 

	} 



	public double getAmountUsd(){ 

		return amountUsd; 

	} 



	public void setAmountUsd(double amountUsd){ 

		this.amountUsd = amountUsd; 

	} 



	public long getCurrencyUsed(){

		return currencyUsed; 

	} 



	public void setCurrencyUsed(long currencyUsed){

		this.currencyUsed = currencyUsed; 

	} 



	public int getAddOrSubstract(){ 

		return addOrSubstract; 

	} 



	public void setAddOrSubstract(int addOrSubstract){ 

		this.addOrSubstract = addOrSubstract; 

	} 



	public double getExchangeRate(){ 

		return exchangeRate; 

	} 



	public void setExchangeRate(double exchangeRate){ 

		this.exchangeRate = exchangeRate; 

	} 



	public int getPaymentPurpose(){ 

		return paymentPurpose; 

	} 



	public void setPaymentPurpose(int paymentPurpose){ 

		this.paymentPurpose = paymentPurpose; 

	} 



	public Date getPaymentDate(){ 

		return paymentDate; 

	} 



	public void setPaymentDate(Date paymentDate){ 

		this.paymentDate = paymentDate; 

	} 

        

             public long getCashierId(){

        return cashierId;

    }

    

    public void setCashierId(long cashierId){

        this.cashierId = cashierId;

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

    

    /** Getter for property reservationChargeId.

     * @return Value of property reservationChargeId.

     *

     */

    public long getReservationChargeId() {

        return this.reservationChargeId;

    }

    

    /** Setter for property reservationChargeId.

     * @param reservationChargeId New value of property reservationChargeId.

     *

     */

    public void setReservationChargeId(long reservationChargeId) {

        this.reservationChargeId = reservationChargeId;

    }

    

    /** Getter for property docRefNumber.

     * @return Value of property docRefNumber.

     *

     */

    public String getDocRefNumber() {

        return this.docRefNumber;

    }

    

    /** Setter for property docRefNumber.

     * @param docRefNumber New value of property docRefNumber.

     *

     */

    public void setDocRefNumber(String docRefNumber) {

        this.docRefNumber = docRefNumber;

    }

    

    /** Getter for property adjustmentId.

     * @return Value of property adjustmentId.

     *

     */

    public long getAdjustmentId() {

        return this.adjustmentId;

    }

    

    /** Setter for property adjustmentId.

     * @param adjustmentId New value of property adjustmentId.

     *

     */

    public void setAdjustmentId(long adjustmentId) {

        this.adjustmentId = adjustmentId;

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

    

}

