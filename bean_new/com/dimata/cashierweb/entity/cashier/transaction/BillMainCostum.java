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
public class BillMainCostum extends Entity{
    private String guestName = "";
    private String invoiceNo = "";
    private String roomName = "";
    private String tableNumber = "";
    private long priceTypeId = 0;
    private long classId = 0;
    private String className = "";
    private String priceTypeName ="";
    private double taxPct = 0;
    private double taxValue = 0;
    private double servicePct = 0;
    private double serviceValue = 0;
    private String notes = "";
    private int taxInc = 0;
    private String companyName = "";
    private String locationName = "";
    private String locationAddress = "";
    private String locationTelp = "";
    private String locationFax = "";
    private String locationWeb = "";
    private String locationEmail = "";
    private int classType = 0;
    private long employeeId = 0;
    private double amountCosting = 0;
    private String costingName = "";
    private double costingQty = 0;
    private long reservationId=0;
    private double discPct = 0;
    private int editMaterial=0;
    
    //added by de Koyo
    private Date billDate = null ;
    
    /**
     * @return the guestName
     */
    public String getGuestName() {
	return guestName;
    }

    /**
     * @param guestName the guestName to set
     */
    public void setGuestName(String guestName) {
	this.guestName = guestName;
    }

    /**
     * @return the invoiceNo
     */
    public String getInvoiceNo() {
	return invoiceNo;
    }

    /**
     * @param invoiceNo the invoiceNo to set
     */
    public void setInvoiceNo(String invoiceNo) {
	this.invoiceNo = invoiceNo;
    }

    /**
     * @return the roomName
     */
    public String getRoomName() {
	return roomName;
    }

    /**
     * @param roomName the roomName to set
     */
    public void setRoomName(String roomName) {
	this.roomName = roomName;
    }

    /**
     * @return the tableNumber
     */
    public String getTableNumber() {
	return tableNumber;
    }

    /**
     * @param tableNumber the tableNumber to set
     */
    public void setTableNumber(String tableNumber) {
	this.tableNumber = tableNumber;
    }

    /**
     * @return the priceTypeId
     */
    public long getPriceTypeId() {
	return priceTypeId;
    }

    /**
     * @param priceTypeId the priceTypeId to set
     */
    public void setPriceTypeId(long priceTypeId) {
	this.priceTypeId = priceTypeId;
    }

    /**
     * @return the classId
     */
    public long getClassId() {
	return classId;
    }

    /**
     * @param classId the classId to set
     */
    public void setClassId(long classId) {
	this.classId = classId;
    }

    /**
     * @return the className
     */
    public String getClassName() {
	return className;
    }

    /**
     * @param className the className to set
     */
    public void setClassName(String className) {
	this.className = className;
    }

    /**
     * @return the priceTypeName
     */
    public String getPriceTypeName() {
	return priceTypeName;
    }

    /**
     * @param priceTypeName the priceTypeName to set
     */
    public void setPriceTypeName(String priceTypeName) {
	this.priceTypeName = priceTypeName;
    }

    /**
     * @return the taxPct
     */
    public double getTaxPct() {
	return taxPct;
    }

    /**
     * @param taxPct the taxPct to set
     */
    public void setTaxPct(double taxPct) {
	this.taxPct = taxPct;
    }

    /**
     * @return the taxValue
     */
    public double getTaxValue() {
	return taxValue;
    }

    /**
     * @param taxValue the taxValue to set
     */
    public void setTaxValue(double taxValue) {
	this.taxValue = taxValue;
    }

    /**
     * @return the servicePct
     */
    public double getServicePct() {
	return servicePct;
    }

    /**
     * @param servicePct the servicePct to set
     */
    public void setServicePct(double servicePct) {
	this.servicePct = servicePct;
    }

    /**
     * @return the serviceValue
     */
    public double getServiceValue() {
	return serviceValue;
    }

    /**
     * @param serviceValue the serviceValue to set
     */
    public void setServiceValue(double serviceValue) {
	this.serviceValue = serviceValue;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
	return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
	this.notes = notes;
    }

    /**
     * @return the taxInc
     */
    public int getTaxInc() {
	return taxInc;
    }

    /**
     * @param taxInc the taxInc to set
     */
    public void setTaxInc(int taxInc) {
	this.taxInc = taxInc;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
	return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
	this.companyName = companyName;
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

    /**
     * @return the locationAddress
     */
    public String getLocationAddress() {
	return locationAddress;
    }

    /**
     * @param locationAddress the locationAddress to set
     */
    public void setLocationAddress(String locationAddress) {
	this.locationAddress = locationAddress;
    }

    /**
     * @return the locationTelp
     */
    public String getLocationTelp() {
	return locationTelp;
    }

    /**
     * @param locationTelp the locationTelp to set
     */
    public void setLocationTelp(String locationTelp) {
	this.locationTelp = locationTelp;
    }

    /**
     * @return the locationFax
     */
    public String getLocationFax() {
	return locationFax;
    }

    /**
     * @param locationFax the locationFax to set
     */
    public void setLocationFax(String locationFax) {
	this.locationFax = locationFax;
    }

    /**
     * @return the locationWeb
     */
    public String getLocationWeb() {
	return locationWeb;
    }

    /**
     * @param locationWeb the locationWeb to set
     */
    public void setLocationWeb(String locationWeb) {
	this.locationWeb = locationWeb;
    }

    /**
     * @return the locationEmail
     */
    public String getLocationEmail() {
	return locationEmail;
    }

    /**
     * @param locationEmail the locationEmail to set
     */
    public void setLocationEmail(String locationEmail) {
	this.locationEmail = locationEmail;
    }

    /**
     * @return the classType
     */
    public int getClassType() {
	return classType;
    }

    /**
     * @param classType the classType to set
     */
    public void setClassType(int classType) {
	this.classType = classType;
    }

    /**
     * @return the employeeId
     */
    public long getEmployeeId() {
	return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(long employeeId) {
	this.employeeId = employeeId;
    }

    /**
     * @return the amountCosting
     */
    public double getAmountCosting() {
	return amountCosting;
    }

    /**
     * @param amountCosting the amountCosting to set
     */
    public void setAmountCosting(double amountCosting) {
	this.amountCosting = amountCosting;
    }

    /**
     * @return the costingName
     */
    public String getCostingName() {
	return costingName;
    }

    /**
     * @param costingName the costingName to set
     */
    public void setCostingName(String costingName) {
	this.costingName = costingName;
    }

    /**
     * @return the costingQty
     */
    public double getCostingQty() {
	return costingQty;
    }

    /**
     * @param costingQty the costingQty to set
     */
    public void setCostingQty(double costingQty) {
	this.costingQty = costingQty;
    }

    /**
     * @return the billDate
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * @param billDate the billDate to set
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
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
     * @return the editMaterial
     */
    public int getEditMaterial() {
        return editMaterial;
    }

    /**
     * @param editMaterial the editMaterial to set
     */
    public void setEditMaterial(int editMaterial) {
        this.editMaterial = editMaterial;
    }
    
    
}
