/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.hanoman.entity.cashier;

/**
 *
 * @author ktanjana
 */

import java.util.Vector;
public class GuestBillGroup {
    Vector billList = new Vector();
    private BillingRec billSummaryRp = new BillingRec();
    private BillingRec billSummaryUSD = new BillingRec();
    public void addBillRec(BillingRec bill){
        billList.add(bill);
    }

    public int getBillRecSize(){
        if(billList!=null){
         return billList.size();
        } else {
            return 0;
        }
    }

    public BillingRec getBillRec(int idx){
        if(billList!=null && idx>=0 && idx< billList.size()){
         return (BillingRec) billList.get(idx);
        } else {
            return null;
        }
    }

    /**
     * @return the billSummaryRp
     */
    public BillingRec getBillSummaryRp() {
        return billSummaryRp;
    }

    /**
     * @param billSummaryRp the billSummaryRp to set
     */
    public void setBillSummaryRp(BillingRec billSummaryRp) {
        this.billSummaryRp = billSummaryRp;
    }

    /**
     * @return the billSummaryUSD
     */
    public BillingRec getBillSummaryUSD() {
        return billSummaryUSD;
    }

    /**
     * @param billSummaryUSD the billSummaryUSD to set
     */
    public void setBillSummaryUSD(BillingRec billSummaryUSD) {
        this.billSummaryUSD = billSummaryUSD;
    }

}
