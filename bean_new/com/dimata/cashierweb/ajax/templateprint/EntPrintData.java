/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.ajax.templateprint;

/**
 *
 * @author dimata005
 */

import java.util.Date;
import java.util.Vector;

/**
 *
 * @author ardiadi
 */
public class EntPrintData {

    /**
     * @return the dataAdm
     */
    public double getDataAdm() {
        return dataAdm;
    }

    /**
     * @param dataAdm the dataAdm to set
     */
    public void setDataAdm(double dataAdm) {
        this.dataAdm = dataAdm;
    }

    /**
     * @return the dataTransport
     */
    public double getDataTransport() {
        return dataTransport;
    }

    /**
     * @param dataTransport the dataTransport to set
     */
    public void setDataTransport(double dataTransport) {
        this.dataTransport = dataTransport;
    }
    
    //WHATS TEMPLALTE (INT ONLY)
    private int activeTemplate = 0;
    private String paperWidth = "210mm";
    private String paperHeight = "297mm";
    
    //PRINT HEADER
    private String compName = "";
    private String compAddress = "";
    private String compPhone = "";
    private String compFax = "";
    private String compEmail = "";
    private String compLogo = "";
    
    //DATA 
    private Vector dataList = new Vector();
    private String dataCurrency = "";
    private double dataDiscTotal = 0;
    private double dataTaxTotal = 0;
    private double dataSvcTotal = 0;
    private double dataTotal = 0;
    private String invNo = "";
    private double dataAddCharge = 0;
    private double deposit = 0;
    private double paid = 0;
    private String note = "";
    private String paymentType = "";
    private Date invDate = new Date();
    private double discPct = 0;
    private double dataAdm = 0;
    private double dataTransport = 0;
    
    //FOOTER
    private String receivedBy = "";
    private String costumer = "";
    private Date receiveDate = new Date();
    private Date dueDate = new Date();
    private String warehouse = "";
    private int isTaxInclude = 0;
    private String salesName = "";

    /**
     * @return the compName
     */
    public String getCompName() {
        return compName;
    }

    /**
     * @param compName the compName to set
     */
    public void setCompName(String compName) {
        this.compName = compName;
    }

    /**
     * @return the compAddress
     */
    public String getCompAddress() {
        return compAddress;
    }

    /**
     * @param compAddress the compAddress to set
     */
    public void setCompAddress(String compAddress) {
        this.compAddress = compAddress;
    }

    /**
     * @return the compPhone
     */
    public String getCompPhone() {
        return compPhone;
    }

    /**
     * @param compPhone the compPhone to set
     */
    public void setCompPhone(String compPhone) {
        this.compPhone = compPhone;
    }

    /**
     * @return the compFax
     */
    public String getCompFax() {
        return compFax;
    }

    /**
     * @param compFax the compFax to set
     */
    public void setCompFax(String compFax) {
        this.compFax = compFax;
    }

    /**
     * @return the compEmail
     */
    public String getCompEmail() {
        return compEmail;
    }

    /**
     * @param compEmail the compEmail to set
     */
    public void setCompEmail(String compEmail) {
        this.compEmail = compEmail;
    }

    /**
     * @return the compLogo
     */
    public String getCompLogo() {
        return compLogo;
    }

    /**
     * @param compLogo the compLogo to set
     */
    public void setCompLogo(String compLogo) {
        this.compLogo = compLogo;
    }

    /**
     * @return the dataList
     */
    public Vector getDataList() {
        return dataList;
    }

    /**
     * @param dataList the dataList to set
     */
    public void setDataList(Vector dataList) {
        this.dataList = dataList;
    }

    /**
     * @return the dataCurrency
     */
    public String getDataCurrency() {
        return dataCurrency;
    }

    /**
     * @param dataCurrency the dataCurrency to set
     */
    public void setDataCurrency(String dataCurrency) {
        this.dataCurrency = dataCurrency;
    }

    /**
     * @return the dataDiscTotal
     */
    public double getDataDiscTotal() {
        return dataDiscTotal;
    }

    /**
     * @param dataDiscTotal the dataDiscTotal to set
     */
    public void setDataDiscTotal(double dataDiscTotal) {
        this.dataDiscTotal = dataDiscTotal;
    }

    /**
     * @return the dataTaxTotal
     */
    public double getDataTaxTotal() {
        return dataTaxTotal;
    }

    /**
     * @param dataTaxTotal the dataTaxTotal to set
     */
    public void setDataTaxTotal(double dataTaxTotal) {
        this.dataTaxTotal = dataTaxTotal;
    }

    /**
     * @return the dataSvcTotal
     */
    public double getDataSvcTotal() {
        return dataSvcTotal;
    }

    /**
     * @param dataSvcTotal the dataSvcTotal to set
     */
    public void setDataSvcTotal(double dataSvcTotal) {
        this.dataSvcTotal = dataSvcTotal;
    }

    /**
     * @return the dataTotal
     */
    public double getDataTotal() {
        return dataTotal;
    }

    /**
     * @param dataTotal the dataTotal to set
     */
    public void setDataTotal(double dataTotal) {
        this.dataTotal = dataTotal;
    }

    /**
     * @return the invNo
     */
    public String getInvNo() {
        return invNo;
    }

    /**
     * @param invNo the invNo to set
     */
    public void setInvNo(String invNo) {
        this.invNo = invNo;
    }

    /**
     * @return the dataAddCharge
     */
    public double getDataAddCharge() {
        return dataAddCharge;
    }

    /**
     * @param dataAddCharge the dataAddCharge to set
     */
    public void setDataAddCharge(double dataAddCharge) {
        this.dataAddCharge = dataAddCharge;
    }

    /**
     * @return the deposit
     */
    public double getDeposit() {
        return deposit;
    }

    /**
     * @param deposit the deposit to set
     */
    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    /**
     * @return the paid
     */
    public double getPaid() {
        return paid;
    }

    /**
     * @param paid the paid to set
     */
    public void setPaid(double paid) {
        this.paid = paid;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the receivedBy
     */
    public String getReceivedBy() {
        return receivedBy;
    }

    /**
     * @param receivedBy the receivedBy to set
     */
    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }

    /**
     * @return the costumer
     */
    public String getCostumer() {
        return costumer;
    }

    /**
     * @param costumer the costumer to set
     */
    public void setCostumer(String costumer) {
        this.costumer = costumer;
    }

    /**
     * @return the receiveDate
     */
    public Date getReceiveDate() {
        return receiveDate;
    }

    /**
     * @param receiveDate the receiveDate to set
     */
    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @return the warehouse
     */
    public String getWarehouse() {
        return warehouse;
    }

    /**
     * @param warehouse the warehouse to set
     */
    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * @return the activeTemplate
     */
    public int getActiveTemplate() {
        return activeTemplate;
    }

    /**
     * @param activeTemplate the activeTemplate to set
     */
    public void setActiveTemplate(int activeTemplate) {
        this.activeTemplate = activeTemplate;
    }

    /**
     * @return the paperWidth
     */
    public String getPaperWidth() {
        return paperWidth;
    }

    /**
     * @param paperWidth the paperWidth to set
     */
    public void setPaperWidth(String paperWidth) {
        this.paperWidth = paperWidth;
    }

    /**
     * @return the paperHeight
     */
    public String getPaperHeight() {
        return paperHeight;
    }

    /**
     * @param paperHeight the paperHeight to set
     */
    public void setPaperHeight(String paperHeight) {
        this.paperHeight = paperHeight;
    }

    /**
     * @return the paymentType
     */
    public String getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    /**
     * @return the invDate
     */
    public Date getInvDate() {
        return invDate;
    }

    /**
     * @param invDate the invDate to set
     */
    public void setInvDate(Date invDate) {
        this.invDate = invDate;
    }

    /**
     * @return the isTaxInclude
     */
    public int getIsTaxInclude() {
        return isTaxInclude;
    }

    /**
     * @param isTaxInclude the isTaxInclude to set
     */
    public void setIsTaxInclude(int isTaxInclude) {
        this.isTaxInclude = isTaxInclude;
    }

    /**
     * @return the salesName
     */
    public String getSalesName() {
        return salesName;
    }

    /**
     * @param salesName the salesName to set
     */
    public void setSalesName(String salesName) {
        this.salesName = salesName;
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
    
    
}
