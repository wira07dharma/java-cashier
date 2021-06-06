

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



package com.dimata.hanoman.entity.masterdata; 

 

/* package java */ 

import java.util.Date;



/* package qdep */

import com.dimata.qdep.entity.*;



public class BillingType extends Entity { 



    

    private long billingTypeId;    

    private String typeName="";

    private String description = "";    

    private long parentId;  

    private String typeCode="";

    private String address;

    private String telephone;

    private String fax;

    private String person;

    private long contactId;

    private String email;

    private String website;

    private long vendorId;

    private int type;

    private double servicePercentage;

    private double taxPercentage;

    private long departmentID;

    private int usedValue;

    private double serviceValue;

    private double taxValue;

    private double serviceValueUsd;

    private double taxValueUsd;

    private int reportGroup;
    
    private int taxSvcDefault=0;
    
    private long priceTypeId=0;
    private long standardRateId=0;
    private long companyId=0;
    private int useTable=0;
    private String npwpd="";
	/*

        private String typeCode = "";

	private String typeName = "";

	private double servicePercentage;

	private double taxPercentage;

	private String description = "";

        private long departmentID;

        private int usedValue;

        private double serviceValue;

        private double taxValue;

        private double taxValueUsd;

        private double serviceValueUsd;

        private int reportGroup;*/

        

              

    

    public long getBillingTypeId() {

        return this.billingTypeId;

    }

    

    public void setBillingTypeId(long billingTypeId) {

        this.billingTypeId = billingTypeId;

    }

    

     

    public String getTypeName() {

        return this.typeName;

    }

    

    

    public void setTypeName(String typeName) {

        if ( typeName == null ) {

			typeName = ""; 

	}

        this.typeName = typeName;

    }

    

    public String getDescription(){ 

	return description; 

    } 



    public void setDescription(String description){ 

        if ( description == null ) {

                description = ""; 

        } 

        this.description = description; 

    } 

    

    

    public long getParentId() {

        return this.parentId;

    }    

    

    public void setParentId(long parentId) {

        this.parentId = parentId;

    } 

    

    

    public String getTypeCode() {

        return this.typeCode;

    }

    

    

    public void setTypeCode(String typeCode) {

        if ( typeCode == null ) 

        {

             typeCode = ""; 

        } 

        this.typeCode = typeCode;

    }    

    

    public String getAddress() {

        return this.address;

    }

    

    public void setAddress(String address) {

        if ( address == null ) 

        {

             address = ""; 

        } 

        this.address = address;

    }

    

    public String getTelephone() {

        return this.telephone;

    }

    

    public void setTelephone(String telephone) {

        if ( telephone == null ) 

        {

             telephone = ""; 

        }

        this.telephone = telephone;

    }

    

    public String getFax() {

        return this.fax;

    }

    

   public void setFax(String fax) {

        if ( fax == null ) 

        {

             fax = ""; 

        }

        this.fax = fax;

    }

    

   public String getPerson() {

        return this.person;

    }

    

   public void setPerson(String person) {

        if ( person == null ) 

        {

             person = ""; 

        }

        this.person = person;

    }

    

   public long getContactId() {

        return this.contactId;

    }

    

   public void setContactId(long contactId) {

        this.contactId = contactId;

    }

    

   public String getEmail() {

        return this.email;

    }

    

   public void setEmail(String email) {

        if ( email == null ) 

        {

             email = ""; 

        }

        this.email = email;

    }

    

   public String getWebsite() {

        return this.website;

    }

    

   public void setWebsite(String website) {

        if ( website == null ) 

        {

             website = ""; 

        }

        this.website = website;

    }

    

    public long getVendorId() {

        return this.vendorId;

    }

    

   public void setVendorId(long vendorId) {

        this.vendorId = vendorId;

    }

    

    public int getType() {

        return this.type;

    }

    

    public void setType(int type) {

        this.type = type;

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



    public long getDepartmentID(){

        return departmentID; 

    }



    public void setDepartmentID(long departmentID){

        this.departmentID = departmentID; 

    }



    public int getUsedValue(){ 

        return usedValue; 

    }



    public void setUsedValue(int usedValue){ 

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

    



    public int getReportGroup(){ 

        return reportGroup; 

    }



    public void setReportGroup(int reportGroup){

        this.reportGroup = reportGroup; 

    }

    /**
     * @return the taxSvcDefault
     */
    public int getTaxSvcDefault() {
        return taxSvcDefault;
    }

    /**
     * @param taxSvcDefault the taxSvcDefault to set
     */
    public void setTaxSvcDefault(int taxSvcDefault) {
        this.taxSvcDefault = taxSvcDefault;
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
     * @return the standardRateId
     */
    public long getStandardRateId() {
        return standardRateId;
    }

    /**
     * @param standardRateId the standardRateId to set
     */
    public void setStandardRateId(long standardRateId) {
        this.standardRateId = standardRateId;
    }

    /**
     * @return the companyId
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * @param companyId the companyId to set
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    /**
     * @return the useTable
     */
    public int getUseTable() {
        return useTable;
    }

    /**
     * @param useTable the useTable to set
     */
    public void setUseTable(int useTable) {
        this.useTable = useTable;
    }

    /**
     * @return the npwpd
     */
    public String getNpwpd() {
        return npwpd;
    }

    /**
     * @param npwpd the npwpd to set
     */
    public void setNpwpd(String npwpd) {
        this.npwpd = npwpd;
    }
 
}

