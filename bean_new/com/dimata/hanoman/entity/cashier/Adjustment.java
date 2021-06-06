

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
import java.text.DecimalFormat;



public class Adjustment extends Entity { 



	private long userId;

	private long ownerId;

	private Date date;

	private double amount;

	private int currencyUsed;
        private String currencySymbol="";

	private String note = "";

        private int type;

        private long locationId=0;



        /** Holds value of property number. */

        private String number;

        

        /** Holds value of property tax. */

        private double tax;

        

        /** Holds value of property service. */

        private double service;

        

        /** Holds value of property taxInclude. */

        private boolean taxInclude;

        

        /** Holds value of property exchangeRate. */

        private double exchangeRate;

        

        /** Holds value of property status. */

        private int status;

        

        /** Holds value of property counter. */

        private int counter;

        

        /** Holds value of property customerId. */

        private long customerId;

        

        /** Holds value of property paymentStatus. */

        private int paymentStatus;

        //update opie-eyek 10-10-2012 agar hasilnya sama dengan format 
        
        DecimalFormat formatDecimal = new DecimalFormat("#.##");

	public long getUserId(){ 

		return userId; 

	} 



	public void setUserId(long userId){ 

		this.userId = userId; 

	} 



	public long getOwnerId(){ return ownerId; } 



	public void setOwnerId(long ownerId){ this.ownerId = ownerId; }



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



	public int getCurrencyUsed(){ 

		return currencyUsed; 

	} 



	public void setCurrencyUsed(int currencyUsed){ 

		this.currencyUsed = currencyUsed; 

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

        

        /** Getter for property tax.

         * @return Value of property tax.

         *

         */

        public double getTax() {

            return this.tax;

        }

        

        /** Setter for property tax.

         * @param tax New value of property tax.

         *

         */

        public void setTax(double tax) {

            this.tax = tax;

        }

        

        /** Getter for property service.

         * @return Value of property service.

         *

         */

        public double getService() {

            return this.service;

        }

        

        /** Setter for property service.

         * @param service New value of property service.

         *

         */

        public void setService(double service) {

            this.service = service;

        }

        

        /** Getter for property taxInclude.

         * @return Value of property taxInclude.

         *

         */

        public boolean getTaxInclude() {

            return this.taxInclude;

        }

        

        /** Setter for property taxInclude.

         * @param taxInclude New value of property taxInclude.

         *

         */

        public void setTaxInclude(boolean taxInclude) {

            this.taxInclude = taxInclude;

        }

        

        /** Getter for property exchangeRate.

         * @return Value of property exchangeRate.

         *

         */

        public double getExchangeRate() {

            return this.exchangeRate;

        }

        

        /** Setter for property exchangeRate.

         * @param exchangeRate New value of property exchangeRate.

         *

         */

        public void setExchangeRate(double exchangeRate) {

            this.exchangeRate = exchangeRate;

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

        public int getType() {

            return this.type;

        }

        

        /** Setter for property status.

         * @param status New value of property status.

         *

         */

        public void setType(int type) {

            this.type = type;

        }

    /**
     * @return the currencySymbol
     */
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    /**
     * @param currencySymbol the currencySymbol to set
     */
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public double getSubTotalUsd(){
        double subTotalUsd = Double.valueOf(formatDecimal.format(amount+service+tax));
         if(this.currencyUsed==2){
            return (subTotalUsd);
        } else{
            return (subTotalUsd)/exchangeRate;
        }
    }


    public double getSubTotalUsdWoTaxService(){
        double subTotalUsd = Double.valueOf(formatDecimal.format(amount));
         if(this.currencyUsed==2){
            return (subTotalUsd);
        } else{
            return (subTotalUsd)/exchangeRate;
        }
    }

    public double getSubTotalRp(){
        double subTotalRp = Double.valueOf(formatDecimal.format(amount+service+tax));
        if(this.currencyUsed==1){
            return (subTotalRp);
        } else{
            return (subTotalRp)*exchangeRate;
        }
    }
    

    public double getSubTotalRpWoTaxService(){
        double subTotalRp = Double.valueOf(formatDecimal.format(amount));
        if(this.currencyUsed==1){
            return (subTotalRp);
        } else{
            return (subTotalRp)*exchangeRate;
        }
    }

    public double getSubTotalUsdWoTaxNSvc(){
        double subTotalUsdWoTaxNSvc = Double.valueOf(formatDecimal.format(amount));
        if(this.currencyUsed==2){
            return (subTotalUsdWoTaxNSvc);
        } else{
            return (subTotalUsdWoTaxNSvc)/exchangeRate;
        }
    }

    public double getSubTotalRpWoTaxNSvc(){
        double subTotalRpWoTaxNSvc = Double.valueOf(formatDecimal.format(amount));
        if(this.currencyUsed==1){
            return (subTotalRpWoTaxNSvc);
        } else{
            return (subTotalRpWoTaxNSvc)*exchangeRate;
        }
    }

    /**
     * @return the locationId
     */
    public long getLocationId() {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

}

