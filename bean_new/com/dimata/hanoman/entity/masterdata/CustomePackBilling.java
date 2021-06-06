/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.hanoman.entity.masterdata;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author dimata005
 */

public class CustomePackBilling extends Entity{

        private long billingTypeItemId;

	private long billingTypeId;

	private long travelPackTypeId;

	private int quantity;

	private double priceRp;

	private double priceUsd;

        private double serviceRp;

        private double serviceUsd;

        private double taxRp;

        private double taxUsd;

	private double totalPriceRp;

	private double totalPriceUsd;

        private long contractId;

        private long roomClassId;

        private int useDefault; //include package or exclude on reservasi

        private Date takenDate= new Date();

        private int type;

        private long typeSales;

        private long typeCompliment;

        private long reservationId;

        private double rate;

        private int consume=0;

        private double totalServiceRp=0.0;
        private double totalServiceUSD=0.0;
        private double totalTaxRp=0.0;
        private double totalTaxUSD=0.0;
        private double totalSubRp=0.0;
        private double totalSubUSD=0.0;
        
        //update opie-eyek 20151218
        //penambahan category dan subcategory
        private long categoryId=0;
        private long subcategoryId=0;
        private long currencyType = 0;
        private double discPct = 0;
        private double disc = 0;
        private double disc$ = 0;
        
        

    /**
     * @return the billingTypeItemId
     */
    public long getBillingTypeItemId() {
        return billingTypeItemId;
    }

    /**
     * @param billingTypeItemId the billingTypeItemId to set
     */
    public void setBillingTypeItemId(long billingTypeItemId) {
        this.billingTypeItemId = billingTypeItemId;
    }

    /**
     * @return the billingTypeId
     */
    public long getBillingTypeId() {
        return billingTypeId;
    }

    /**
     * @param billingTypeId the billingTypeId to set
     */
    public void setBillingTypeId(long billingTypeId) {
        this.billingTypeId = billingTypeId;
    }

    /**
     * @return the travelPackTypeId
     */
    public long getTravelPackTypeId() {
        return travelPackTypeId;
    }

    /**
     * @param travelPackTypeId the travelPackTypeId to set
     */
    public void setTravelPackTypeId(long travelPackTypeId) {
        this.travelPackTypeId = travelPackTypeId;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the priceRp
     */
    public double getPriceRp() {
        return priceRp;
    }

    /**
     * @param priceRp the priceRp to set
     */
    public void setPriceRp(double priceRp) {
        this.priceRp = priceRp;
    }

    /**
     * @return the priceUsd
     */
    public double getPriceUsd() {
        return priceUsd;
    }

    /**
     * @param priceUsd the priceUsd to set
     */
    public void setPriceUsd(double priceUsd) {
        this.priceUsd = priceUsd;
    }

    /**
     * @return the totalPriceRp
     */
    public double getTotalPriceRp() {
        return totalPriceRp;
    }

    /**
     * @param totalPriceRp the totalPriceRp to set
     */
    public void setTotalPriceRp(double totalPriceRp) {
        this.totalPriceRp = totalPriceRp;
    }

    /**
     * @return the totalPriceUsd
     */
    public double getTotalPriceUsd() {
        return totalPriceUsd;
    }

    /**
     * @param totalPriceUsd the totalPriceUsd to set
     */
    public void setTotalPriceUsd(double totalPriceUsd) {
        this.totalPriceUsd = totalPriceUsd;
    }

    /**
     * @return the contractId
     */
    public long getContractId() {
        return contractId;
    }

    /**
     * @param contractId the contractId to set
     */
    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    /**
     * @return the roomClassId
     */
    public long getRoomClassId() {
        return roomClassId;
    }

    /**
     * @param roomClassId the roomClassId to set
     */
    public void setRoomClassId(long roomClassId) {
        this.roomClassId = roomClassId;
    }

    /**
     * @return the useDefault
     */
    public int getUseDefault() {
        return useDefault;
    }

    /**
     * @param useDefault the useDefault to set
     */
    public void setUseDefault(int useDefault) {
        this.useDefault = useDefault;
    }

    /**
     * @return the takenDate
     */
    public Date getTakenDate() {
        return takenDate;
    }

    /**
     * @param takenDate the takenDate to set
     */
    public void setTakenDate(Date takenDate) {
        this.takenDate = takenDate;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @return the typeSales
     */
    public long getTypeSales() {
        return typeSales;
    }

    /**
     * @param typeSales the typeSales to set
     */
    public void setTypeSales(long typeSales) {
        this.typeSales = typeSales;
    }

    /**
     * @return the typeCompliment
     */
    public long getTypeCompliment() {
        return typeCompliment;
    }

    /**
     * @param typeCompliment the typeCompliment to set
     */
    public void setTypeCompliment(long typeCompliment) {
        this.typeCompliment = typeCompliment;
    }

    /**
     * @return the reservationId
     */
    public long getReservationId() {
        return reservationId;
    }

    /**
     * @param reservationId the reservationId to set
     */
    public void setReservationId(long reservationId) {
        this.reservationId = reservationId;
    }

    /**
     * @return the serviceRp
     */
    public double getServiceRp() {
        return serviceRp;
    }

    /**
     * @param serviceRp the serviceRp to set
     */
    public void setServiceRp(double serviceRp) {
        this.serviceRp = serviceRp;
    }

    /**
     * @return the serviceUsd
     */
    public double getServiceUsd() {
        return serviceUsd;
    }

    /**
     * @param serviceUsd the serviceUsd to set
     */
    public void setServiceUsd(double serviceUsd) {
        this.serviceUsd = serviceUsd;
    }

    /**
     * @return the taxRp
     */
    public double getTaxRp() {
        return taxRp;
    }

    /**
     * @param taxRp the taxRp to set
     */
    public void setTaxRp(double taxRp) {
        this.taxRp = taxRp;
    }

    /**
     * @return the taxUsd
     */
    public double getTaxUsd() {
        return taxUsd;
    }

    /**
     * @param taxUsd the taxUsd to set
     */
    public void setTaxUsd(double taxUsd) {
        this.taxUsd = taxUsd;
    }

    /**
     * @return the rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * @return the consume
     */
    public int getConsume() {
        return consume;
    }

    /**
     * @param consume the consume to set
     */
    public void setConsume(int consume) {
        this.consume = consume;
    }

    /**
     * @return the totalServiceRp
     */
    public double getTotalServiceRp() {
        return totalServiceRp;
    }

    /**
     * @param totalServiceRp the totalServiceRp to set
     */
    public void setTotalServiceRp(double totalServiceRp) {
        this.totalServiceRp = totalServiceRp;
    }

    /**
     * @return the totalServiceUSD
     */
    public double getTotalServiceUSD() {
        return totalServiceUSD;
    }

    /**
     * @param totalServiceUSD the totalServiceUSD to set
     */
    public void setTotalServiceUSD(double totalServiceUSD) {
        this.totalServiceUSD = totalServiceUSD;
    }

    /**
     * @return the totalTaxRp
     */
    public double getTotalTaxRp() {
        return totalTaxRp;
    }

    /**
     * @param totalTaxRp the totalTaxRp to set
     */
    public void setTotalTaxRp(double totalTaxRp) {
        this.totalTaxRp = totalTaxRp;
    }

    /**
     * @return the totalTaxUSD
     */
    public double getTotalTaxUSD() {
        return totalTaxUSD;
    }

    /**
     * @param totalTaxUSD the totalTaxUSD to set
     */
    public void setTotalTaxUSD(double totalTaxUSD) {
        this.totalTaxUSD = totalTaxUSD;
    }

    /**
     * @return the totalSubRp
     */
    public double getTotalSubRp() {
        return totalSubRp;
    }

    /**
     * @param totalSubRp the totalSubRp to set
     */
    public void setTotalSubRp(double totalSubRp) {
        this.totalSubRp = totalSubRp;
    }


    /**
     * @return the totalSubUSD
     */
    public double getTotalSubUSD() {
        return totalSubUSD;
    }

    /**
     * @param totalSubUSD the totalSubUSD to set
     */
    public void setTotalSubUSD(double totalSubUSD) {
        this.totalSubUSD = totalSubUSD;
    }

    /**
     * @return the categoryId
     */
    public long getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * @return the subcategoryId
     */
    public long getSubcategoryId() {
        return subcategoryId;
    }

    /**
     * @param subcategoryId the subcategoryId to set
     */
    public void setSubcategoryId(long subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    /**
     * @return the currencyType
     */
    public long getCurrencyType() {
        return currencyType;
    }

    /**
     * @param currencyType the currencyType to set
     */
    public void setCurrencyType(long currencyType) {
        this.currencyType = currencyType;
    }

    /**
     * @return the discPct
     */
    public double getDiscPct() {
        return discPct;
    }

    /**
     * @param discPct the discPct to set
     */
    public void setDiscPct(double discPct) {
        this.discPct = discPct;
    }

    /**
     * @return the disc
     */
    public double getDisc() {
        return disc;
    }

    /**
     * @param disc the disc to set
     */
    public void setDisc(double disc) {
        this.disc = disc;
    }

    /**
     * @return the disc$
     */
    public double getDisc$() {
        return disc$;
    }

    /**
     * @param disc$ the disc$ to set
     */
    public void setDisc$(double disc$) {
        this.disc$ = disc$;
    }


        

}
