

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
import java.util.Vector;



/* package qdep */

import com.dimata.qdep.entity.*;



public class BillingRec extends Entity {

    

    private long billingTypeId;

    private long reservationId;

    private String number = "";

    private Date date;

    private double amount;

    private double discount;

    private double paid;

    private String note = "";

    private double exchangeRate;

    private boolean complementary;

    private int billingCurrency;

    private double totalTax;

    private double totalService;

    

    /** Holds value of property customerId. */

    private long customerId;

    private String butlerName;

    private long reviseRefId;

    private boolean taxInclude;

    private boolean serviceInclude;

    private boolean houseUse;

    private int type;

    private long cashierId;

    private long shiftId;
    
    private Vector billRecItem= null;
    private String coverNumber="";
    private String guestName="";

    private String remarkName="";

    /** Holds value of property paymentStatus. */

    private int paymentStatus;

    

    /** Holds value of property status. */

    private int status;

    

    /**

     * Holds value of property transactionType.

     */

    //add opie-eyek 20121018

    private int docType;

    private int transactionType;

    

    /**

     * Holds value of property transactionStatus.

     */

    private int transactionStatus;


    private String outletLocationName="";

    //total qty
    private int totalQty=0;

    private double totalCost=0;

    public long getBillingTypeId(){

        return billingTypeId;

    }

    

    public void setBillingTypeId(long billingTypeId){

        this.billingTypeId = billingTypeId;

    }

    

    public long getReservationId(){

        return reservationId;

    }

    

    public void setReservationId(long reservationId){

        this.reservationId = reservationId;

    }

    

    public String getNumber(){

        return number;

    }

    

    public void setNumber(String number){

        if ( number == null ) {

            number = "";

        }

        this.number = number;

    }

    

    public Date getDate(){

        return date;

    }

    

    public void setDate(Date date){

        this.date = date;

    }

    

    public double getAmount(){

        return amount;

    }

    

    public void setAmount(double amount){

        this.amount = amount;

    }

    

    public double getDiscount(){

        return discount;

    }

    

    public void setDiscount(double discount){

        this.discount = discount;

    }

    

    public double getPaid(){

        return paid;

    }

    

    public void setPaid(double paid){

        this.paid = paid;

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

    

    public double getExchangeRate(){

        return exchangeRate;

    }

    

    public void setExchangeRate(double exchangeRate){

        this.exchangeRate = exchangeRate;

    }

    

    public boolean getComplementary(){ return complementary; }

    

    public void setComplementary(boolean complementary){ this.complementary = complementary; }

    

    public int getBillingCurrency(){ return billingCurrency; }

    

    public void setBillingCurrency(int billingCurrency){ this.billingCurrency = billingCurrency; }

    

    public double getTotalTax(){ return totalTax; }

    

    public void setTotalTax(double totalTax){ this.totalTax = totalTax; }

    

    public double getTotalService(){ return totalService; }

    

    public void setTotalService(double totalService){ this.totalService = totalService; }

    

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

    

    public String getButlerName(){ return butlerName; }

    

    public void setButlerName(String butlerName){ this.butlerName = butlerName; }

    

    public long getReviseRefId(){ return reviseRefId; }

    

    public void setReviseRefId(long reviseRefId){ this.reviseRefId = reviseRefId; }

    

    public boolean getTaxInclude(){ return taxInclude; }

    

    public void setTaxInclude(boolean taxInclude){ this.taxInclude = taxInclude; }

    

    public boolean getServiceInclude(){ return serviceInclude; }

    

    public void setServiceInclude(boolean serviceInclude){ this.serviceInclude = serviceInclude; }

    

    public boolean getHouseUse(){ return houseUse; }

    

    public void setHouseUse(boolean houseUse){ this.houseUse = houseUse; }

    

    public int getType(){ return type; }

    

    public void setType(int type){ this.type = type; }

    

    public long getCashierId(){

        return cashierId;

    }

    

    public void setCashierId(long cashierId){

        this.cashierId = cashierId;

    }

    

    /** Getter for property paymentStatus.

     * @return Value of property paymentStatus.

     *

     */

    public int getPaymentStatus() {

        return this.paymentStatus;

    }

    

    /** Setter for property paymentStatus.

     * @param paymentStatus New value of property paymentStatus.

     *

     */

    public void setPaymentStatus(int paymentStatus) {

        this.paymentStatus = paymentStatus;

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

    

    /** Getter for property customerId.

     * @return Value of property customerId.

     *

     */

    public long getShiftId() {

        return this.shiftId;

    }

    

    /** Setter for property customerId.

     * @param customerId New value of property customerId.

     *

     */

    public void setShiftId(long shiftId) {

        this.shiftId = shiftId;

    }

    

    /**

     * Getter for property transactionType.

     * @return Value of property transactionType.

     */

    public int getTransactionType() {

        return this.transactionType;

    }

    

    /**

     * Setter for property transactionType.

     * @param transactionType New value of property transactionType.

     */

    public void setTransactionType(int transactionType) {

        this.transactionType = transactionType;

    }

    

    /**

     * Getter for property transactionStatus.

     * @return Value of property transactionStatus.

     */

    public int getTransactionStatus() {

        return this.transactionStatus;

    }

    

    /**

     * Setter for property transactionStatus.

     * @param transactionStatus New value of property transactionStatus.

     */

    public void setTransactionStatus(int transactionStatus) {

        this.transactionStatus = transactionStatus;

    }

    public Vector getBillRecItem() {
        return billRecItem;
    }

    public void setBillRecItem(Vector billRecItem) {
        this.billRecItem = billRecItem;
    }

    public String getCoverNumber() {
        return coverNumber;
    }

    public void setCoverNumber(String coverNumber) {
        this.coverNumber = coverNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    /**
     * @return the outletLocationName
     */
    public String getOutletLocationName() {
        return outletLocationName;
    }

    /**
     * @param outletLocationName the outletLocationName to set
     */
    public void setOutletLocationName(String outletLocationName) {
        this.outletLocationName = outletLocationName;
    }

    /**
     * @return the remarkName
     */
    public String getRemarkName() {
        return remarkName;
    }

    /**
     * @param remarkName the remarkName to set
     */
    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    /**
     * @return the docType
     */
    public int getDocType() {
        return docType;
    }

    /**
     * @param docType the docType to set
     */
    public void setDocType(int docType) {
        this.docType = docType;
    }

    /**
     * @return the totalQty
     */
    public int getTotalQty() {
        return totalQty;
    }

    /**
     * @param totalQty the totalQty to set
     */
    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    /**
     * @return the totalCost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * @param totalCost the totalCost to set
     */
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

   

    

}

