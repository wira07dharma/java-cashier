/**

 * Created by IntelliJ IDEA.

 * User: eka

 * Date: Sep 14, 2004

 * Time: 10:24:24 AM

 * To change this template use Options | File Templates.

 */

package com.dimata.hanoman.entity.cashier;



import com.dimata.qdep.entity.Entity;



public class CashClosing extends Entity {



    private long cashCashierId;

    private long cashClosingId; 

    private int payType;

    private double amountRp;

    private double amountUSD;



    public long getCashCashierId(){

		return cashCashierId;

	}



	public void setCashCashierId(long cashCashierId){

		this.cashCashierId = cashCashierId;

	}



    public long getCashClosingId(){

        return cashClosingId;

    }

    public void setCashClosingId(long cashClosingId){

        this.cashClosingId = cashClosingId;

    }

    public int getPayType(){

        return payType;

    }

    public void setpayType(int payType){

        this.payType = payType;

    }



    public double getAmountRp(){

        return amountRp;

    }

    public void setAmountRp(double amountRp){

        this.amountRp = amountRp;

    }

     public double getAmountUSD(){

        return amountUSD;

    }

    public void setAmountUSD(double amountUSD){

        this.amountUSD = amountUSD;

    }





}

