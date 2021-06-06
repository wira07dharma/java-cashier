/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.hanoman.entity.cashier;
/* package java */

import java.util.Date;



/* package qdep */

import com.dimata.qdep.entity.*;
import java.text.DecimalFormat;
/**
 *
 * @author dimata005
 */
public class BillAdjustment extends Entity {

    private double amountRp;

    private double taxRp;

    private double serviceRp;

    private double amount$;

    private double tax$;

    private double service$;

    /**
     * @return the amountRp
     */
    public double getAmountRp() {
        return amountRp;
    }

    /**
     * @param amountRp the amountRp to set
     */
    public void setAmountRp(double amountRp) {
        this.amountRp = amountRp;
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
     * @return the amount$
     */
    public double getAmount$() {
        return amount$;
    }

    /**
     * @param amount$ the amount$ to set
     */
    public void setAmount$(double amount$) {
        this.amount$ = amount$;
    }

    /**
     * @return the tax$
     */
    public double getTax$() {
        return tax$;
    }

    /**
     * @param tax$ the tax$ to set
     */
    public void setTax$(double tax$) {
        this.tax$ = tax$;
    }

    /**
     * @return the service$
     */
    public double getService$() {
        return service$;
    }

    /**
     * @param service$ the service$ to set
     */
    public void setService$(double service$) {
        this.service$ = service$;
    }



}
