/* Generated by Together */

package com.dimata.posbo.form.search;

/* java package */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
/* qdep package */
import com.dimata.qdep.form.*;
/* project package */
import com.dimata.posbo.entity.search.*;

public class FrmSrcAccPayable extends FRMHandler implements I_FRMInterface, I_FRMType {
    private SrcAccPayable srcAccPayable;
    
    public static final String FRM_NAME_SRC_PAYABLE = "FRM_NAME_SRCPAYABLE";
    
    public static final int FRM_FIELD_SUPPLIER_NAME = 0;
    public static final int FRM_FIELD_INVOICE_NUMBER = 1;
    public static final int FRM_FIELD_INVOICE_DATE_STATUS = 2;
    public static final int FRM_FIELD_START_DATE = 3;
    public static final int FRM_FIELD_END_DATE = 4;
    public static final int FRM_FIELD_CURRENCY_ID = 5;
    public static final int FRM_FIELD_SORTBY = 6;
    //for status summary rekap hutang
    public static final int FRM_FIELD_AP_STATUS = 7;
    //for search payment date
    public static final int FRM_FIELD_INVOICE_PAYMENT_DATE_STATUS = 8;
    public static final int FRM_FIELD_PAYMENT_START_DATE = 9;
    public static final int FRM_FIELD_PAYMENT_END_DATE = 10;
    public static final int FRM_FIELD_LOCATION = 11;
    
    public static String[] fieldNames = {
        "FRM_FIELD_SUPPLIER_NAME",
        "FRM_FIELD_INVOICE_NUMBER",
        "FRM_FIELD_INVOICE_DATE_STATUS",
        "FRM_FIELD_START_DATE",
        "FRM_FIELD_END_DATE",
        "FRM_FIELD_CURRENCY_ID",
        "FRM_FIELD_SORTBY",
        //for status summary rekap hutang
        "FRM_FIELD_AP_STATUS",
        //for search payment date
        "FRM_FIELD_INVOICE_PAYMENT_DATE_STATUS",
        "FRM_FIELD_PAYMENT_START_DATE",
        "FRM_FIELD_PAYMENT_END_DATE",
        "FRM_FIELD_LOCATION"
    };
    
    public static int[] fieldTypes = {
        TYPE_STRING,
        TYPE_STRING,
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG,
        TYPE_INT,
        //for status summary rekap hutang
        TYPE_INT,
        //for search payment date
        TYPE_INT,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_LONG
    };
    
    public FrmSrcAccPayable() {
    }
    
    public FrmSrcAccPayable(SrcAccPayable srcAccPayable) {
        this.srcAccPayable = srcAccPayable;
    }
    
    public FrmSrcAccPayable(HttpServletRequest request, SrcAccPayable srcAccPayable) {
        super(new FrmSrcAccPayable(srcAccPayable), request);
        this.srcAccPayable = srcAccPayable;
    }
    
    public String getFormName() {
        return FRM_NAME_SRC_PAYABLE;
    }
    
    public int[] getFieldTypes() {
        return fieldTypes;
    }
    
    public String[] getFieldNames() {
        return fieldNames;
    }
    
    public int getFieldSize() {
        return fieldNames.length;
    }
    
    public SrcAccPayable getEntityObject() {
        return srcAccPayable;
    }
    
    public void requestEntityObject(SrcAccPayable srcAccPayable) {
        try {
            this.requestParam();
            srcAccPayable.setSupplierName(getString(FRM_FIELD_SUPPLIER_NAME));
            srcAccPayable.setInvoiceNumber(getString(FRM_FIELD_INVOICE_NUMBER));
            srcAccPayable.setInvoiceDateStatus(getInt(FRM_FIELD_INVOICE_DATE_STATUS));
            srcAccPayable.setStartDate(getDate(FRM_FIELD_START_DATE));
            srcAccPayable.setEndDate(getDate(FRM_FIELD_END_DATE));
            srcAccPayable.setCurrencyId(getLong(FRM_FIELD_CURRENCY_ID));
            srcAccPayable.setSortBy(getInt(FRM_FIELD_SORTBY));

            //for search payment date
            srcAccPayable.setInvoicePaymentDateStatus(getInt(FRM_FIELD_INVOICE_PAYMENT_DATE_STATUS));
            srcAccPayable.setPaymentStartDate(getDate(FRM_FIELD_PAYMENT_START_DATE));
            srcAccPayable.setPaymentEndDate(getDate(FRM_FIELD_PAYMENT_END_DATE));
            
            srcAccPayable.setLocationFrom(getLong(FRM_FIELD_LOCATION));
            
        }
        catch(Exception e) {
            System.out.println("Error on requestEntityObject : "+e.toString());
        }
    }
}
