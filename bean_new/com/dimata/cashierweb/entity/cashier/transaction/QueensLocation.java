/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.entity.cashier.transaction;

import com.dimata.qdep.entity.Entity;

/**
 *
 * @author Ardiadi
 */
public class QueensLocation extends Entity {

    private String name = "";
    private long contactId = 0;
    private String description = "";
    private String code = "";
    private String address = "";
    private String telephone = "";
    private String fax = "";
    private String person = "";
    private String email = "";
    private int type = 0;
    private String website = "";
    private int locIndex = 0;
    private long parentId = 0;
    private double servicePercentage = 0;
    private double taxPercentage = 0;
    private long departementId = 0;
    private double usedValue = 0;
    private double serviceValue = 0;
    private double taxValue = 0;
    private double serviceValueUsd = 0;
    private double taxValueUsd = 0;
    private long reportGroup = 0;
    private int taxSvcDefault = 0;
    private double pctPurchaseOrder = 0;
    private long companyId = 0;
    private long priceTypeId = 0;
    private long standartRateId = 0;

    public String getName(){
	return name;
    }

    public void setName(String name){
	this.name = name;
    }

    public long getContactId(){
	return contactId;
    }

    public void setContactId(long contactId){
	this.contactId = contactId;
    }

    public String getDescription(){
	return description;
    }

    public void setDescription(String description){
	this.description = description;
    }

    public String getCode(){
	return code;
    }

    public void setCode(String code){
	this.code = code;
    }

    public String getAddress(){
	return address;
    }

    public void setAddress(String address){
	this.address = address;
    }

    public String getTelephone(){
	return telephone;
    }

    public void setTelephone(String telephone){
	this.telephone = telephone;
    }

    public String getFax(){
	return fax;
    }

    public void setFax(String fax){
	this.fax = fax;
    }

    public String getPerson(){
	return person;
    }

    public void setPerson(String person){
	this.person = person;
    }

    public String getEmail(){
	return email;
    }

    public void setEmail(String email){
	this.email = email;
    }

    public int getType(){
	return type;
    }

    public void setType(int type){
	this.type = type;
    }

    public String getWebsite(){
	return website;
    }

    public void setWebsite(String website){
	this.website = website;
    }

    public int getLocIndex(){
	return locIndex;
    }

    public void setLocIndex(int locIndex){
	this.locIndex = locIndex;
    }

    public long getParentId(){
	return parentId;
    }

    public void setParentId(long parentId){
	this.parentId = parentId;
    }

    public double getServicePercentage(){
	return servicePercentage;
    }

    public void setServicePercentage(double servicePercentage){
	this.servicePercentage = servicePercentage;
    }

    public double getTaxPercentage(){
	return taxPercentage;
    }

    public void setTaxPercentage(double taxPercentage){
	this.taxPercentage = taxPercentage;
    }

    public long getDepartementId(){
	return departementId;
    }

    public void setDepartementId(long departementId){
	this.departementId = departementId;
    }

    public double getUsedValue(){
	return usedValue;
    }

    public void setUsedValue(double usedValue){
	this.usedValue = usedValue;
    }

    public double getServiceValue(){
	return serviceValue;
    }

    public void setServiceValue(double serviceValue){
	this.serviceValue = serviceValue;
    }

    public double getTaxValue(){
	return taxValue;
    }

    public void setTaxValue(double taxValue){
	this.taxValue = taxValue;
    }

    public double getServiceValueUsd(){
	return serviceValueUsd;
    }

    public void setServiceValueUsd(double serviceValueUsd){
	this.serviceValueUsd = serviceValueUsd;
    }

    public double getTaxValueUsd(){
	return taxValueUsd;
    }

    public void setTaxValueUsd(double taxValueUsd){
	this.taxValueUsd = taxValueUsd;
    }

    public long getReportGroup(){
	return reportGroup;
    }

    public void setReportGroup(long reportGroup){
	this.reportGroup = reportGroup;
    }

    public int getTaxSvcDefault(){
	return taxSvcDefault;
    }

    public void setTaxSvcDefault(int taxSvcDefault){
	this.taxSvcDefault = taxSvcDefault;
    }

    public double getPctPurchaseOrder(){
	return pctPurchaseOrder;
    }

    public void setPctPurchaseOrder(double pctPurchaseOrder){
	this.pctPurchaseOrder = pctPurchaseOrder;
    }

    public long getCompanyId(){
	return companyId;
    }

    public void setCompanyId(long companyId){
    this.companyId = companyId;
    }

    public long getPriceTypeId(){
	return priceTypeId;
    }

    public void setPriceTypeId(long priceTypeId){
	this.priceTypeId = priceTypeId;
    }

    public long getStandartRateId(){
	return standartRateId;
    }

    public void setStandartRateId(long standartRateId){
	this.standartRateId = standartRateId;
    }

}
