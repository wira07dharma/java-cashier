/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.entity.cashier.transaction;

import com.dimata.qdep.entity.Entity;
import java.util.Date;

/**
 *
 * @author Ardiadi
 */
public class CustomCashCreditCard extends Entity {

    private long ccId = 0;
    private long cashPaymentId = 0;
    private String ccName = "";
    private String ccNumber = "";
    private Date expiredDate = null;
    private String holderName = "";
    private String debitBankName = "";
    private String debitCardName = "";
    private String chequeAccountName = "";
    private Date chequeDueDate = null;
    private long currencyId = 0;
    private double rate = 0;
    private double amount = 0;
    private String chequeBank = "";
    private double bankCost = 0;

    public long getCcId(){
	return ccId;
    }

    public void setCcId(long ccId){
	this.ccId = ccId;
    }

    public long getCashPaymentId(){
	return cashPaymentId;
    }

    public void setCashPaymentId(long cashPaymentId){
	this.cashPaymentId = cashPaymentId;
    }

    public String getCcName(){
	return ccName;
    }

    public void setCcName(String ccName){
	this.ccName = ccName;
    }

    public String getCcNumber(){
	return ccNumber;
    }

    public void setCcNumber(String ccNumber){
	this.ccNumber = ccNumber;
    }

    public Date getExpiredDate(){
	return expiredDate;
    }

    public void setExpiredDate(Date expiredDate){
	this.expiredDate = expiredDate;
    }

    public String getHolderName(){
	return holderName;
    }

    public void setHolderName(String holderName){
	this.holderName = holderName;
    }

    public String getDebitBankName(){
	return debitBankName;
    }

    public void setDebitBankName(String debitBankName){
	this.debitBankName = debitBankName;
    }

    public String getDebitCardName(){
	return debitCardName;
    }

    public void setDebitCardName(String debitCardName){
	this.debitCardName = debitCardName;
    }

    public String getChequeAccountName(){
	return chequeAccountName;
    }

    public void setChequeAccountName(String chequeAccountName){
	this.chequeAccountName = chequeAccountName;
    }

    public Date getChequeDueDate(){
	return chequeDueDate;
    }

    public void setChequeDueDate(Date chequeDueDate){
	this.chequeDueDate = chequeDueDate;
    }

    public long getCurrencyId(){
	return currencyId;
    }

    public void setCurrencyId(long currencyId){
	this.currencyId = currencyId;
    }

    public double getRate(){
	return rate;
    }

    public void setRate(double rate){
	this.rate = rate;
    }

    public double getAmount(){
	return amount;
    }

    public void setAmount(double amount){
	this.amount = amount;
    }

    public String getChequeBank(){
	return chequeBank;
    }

    public void setChequeBank(String chequeBank){
	this.chequeBank = chequeBank;
    }

    public double getBankCost(){
	return bankCost;
    }

    public void setBankCost(double bankCost){
	this.bankCost = bankCost;
    }

}
