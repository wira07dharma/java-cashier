/*

 * Form Name  	:  FrmPaymentRec.java

 * Created on 	:  [date] [time] AM/PM

 *

 * @author  	:  [authorName]

 * @version  	:  [version]

 */



/*******************************************************************

 * Class Description 	: [project description ... ]

 * Imput Parameters 	: [input parameter ...]

 * Output 		: [output ...]

 *******************************************************************/



package com.dimata.cashierweb.form.cashier;



/* java package */

import com.dimata.cashierweb.entity.cashier.transaction.PaymentRec;
import java.io.*;

import java.util.*;

import javax.servlet.*;

import javax.servlet.http.*;

/* qdep package */

import com.dimata.qdep.form.*;





public class FrmPaymentRec extends FRMHandler implements I_FRMInterface, I_FRMType {

    private PaymentRec paymentRec;

    

    public static final String FRM_NAME_PAYMENTREC		=  "FRM_NAME_PAYMENTREC" ;

    

    public static final int FRM_FIELD_PAYMENT_REC_ID			=  0 ;

    public static final int FRM_FIELD_PAYMENT_TYPE_ID			=  1 ;

    public static final int FRM_FIELD_AMOUNT_RP					=  2 ;

    public static final int FRM_FIELD_AMOUNT_$					=  3 ;

    public static final int FRM_FIELD_AMOUNT_TEXT				=  4 ;

    public static final int FRM_FIELD_RECEIVED_BY				=  5 ;

    public static final int FRM_FIELD_EXCHANGE_RATE				=  6 ;

    public static final int FRM_FIELD_DESCRIPTION				=  7 ;

    public static final int FRM_FIELD_PAYMENT_PURPOSE			=  8 ;

    

    public static final  int FRM_FIELD_CC_ISSUING_BANK 				= 9;

    public static final  int FRM_FIELD_CC_NUMBER 						= 10;

    public static final  int FRM_FIELD_CC_EXPIRED_DATE 				= 11;

    public static final  int FRM_FIELD_CC_HOLDER_NAME 				= 12;

    public static final  int FRM_FIELD_BILL_ADDR_STREET 				= 13;

    public static final  int FRM_FIELD_BILL_ADDR_CITY 				= 14;

    public static final  int FRM_FIELD_BILL_ADDR_STATE 				= 15;

    public static final  int FRM_FIELD_BILL_ADDR_ZIP_CODE 			= 16;

    public static final  int FRM_FIELD_BILL_ADDR_COUNTRY 				= 17;

    public static final  int FRM_FIELD_BILL_ADDR_PHONE_NUMBER 		= 18;

    public static final  int FRM_FIELD_PAYMENT_TYPE					= 19;

    public static final  int FRM_FIELD_ADD_OR_SUBSTRACT				= 	20;

    public static final  int FLD_FIELD_CURRENCY_USED				= 21;

    public static final  int FLD_FIELD_SET_RSV_TO_OUT				= 22;

    public static final  int FLD_FIELD_CASH_CASHIER_ID				= 23;

    

    public static final  int FRM_FIELD_PAYMENT_SYSTEM_ID				= 24;

    

    

    

    

    public static String[] fieldNames = {

        "FRM_FIELD_PAYMENT_REC_ID",  "FRM_FIELD_PAYMENT_TYPE_ID",

        "FRM_FIELD_AMOUNT_RP",  "FRM_FIELD_AMOUNT_$",

        "FRM_FIELD_AMOUNT_TEXT",  "FRM_FIELD_RECEIVED_BY",

        "FRM_FIELD_EXCHANGE_RATE",  "FRM_FIELD_DESCRIPTION",

        "FRM_FIELD_PAYMENT_PURPOSE", "FRM_FIELD_CC_ISSUING_BANK",

        "FRM_FIELD_CC_NUMBER", "FRM_FIELD_CC_EXPIRED_DATE",

        "FRM_FIELD_CC_HOLDER_NAME", "FRM_FIELD_BILL_ADDR_STREET",

        "FRM_FIELD_BILL_ADDR_CITY", "FRM_FIELD_BILL_ADDR_STATE",

        "FRM_FIELD_BILL_ADDR_ZIP_CODE", "FRM_FIELD_BILL_ADDR_COUNTRY",

        "FRM_FIELD_BILL_ADDR_PHONE_NUMBER", "FRM_FIELD_PAYMENT_TYPE",

        "FRM_FIELD_ADD_OR_SUBSTRACT", "FLD_FIELD_CURRENCY_USED",

        "FLD_FIELD_SET_RSV_TO_OUT","FLD_FIELD_CASH_CASHIER_ID",

        "FLD_FIELD_PAYMENT_SYSTEM_ID",

    } ;

    

    public static int[] fieldTypes = {

        TYPE_LONG,  TYPE_LONG,

        TYPE_FLOAT,  TYPE_FLOAT,

        TYPE_STRING,  TYPE_STRING,

        TYPE_FLOAT,  TYPE_STRING,

        TYPE_INT, TYPE_STRING,

        TYPE_STRING, TYPE_DATE,

        TYPE_STRING, TYPE_STRING,

        TYPE_STRING, TYPE_STRING,

        TYPE_STRING, TYPE_STRING,

        TYPE_STRING, TYPE_INT,

        TYPE_INT, TYPE_INT,

        TYPE_BOOL, TYPE_LONG,

        

        TYPE_LONG, 

        

    } ;

    

    public FrmPaymentRec(){

    }

    public FrmPaymentRec(PaymentRec paymentRec){

        this.paymentRec = paymentRec;

    }

    

    public FrmPaymentRec(HttpServletRequest request, PaymentRec paymentRec){

        super(new FrmPaymentRec(paymentRec), request);

        this.paymentRec = paymentRec;

    }

    

    public String getFormName() { return FRM_NAME_PAYMENTREC; }

    

    public int[] getFieldTypes() { return fieldTypes; }

    

    public String[] getFieldNames() { return fieldNames; }

    

    public int getFieldSize() { return fieldNames.length; }

    

    public PaymentRec getEntityObject(){ return paymentRec; }

    

    public void requestEntityObject(PaymentRec paymentRec) {

        try{

            this.requestParam();

            paymentRec.setPaymentTypeId(getLong(FRM_FIELD_PAYMENT_TYPE_ID));

            paymentRec.setAmountRp(getDouble(FRM_FIELD_AMOUNT_RP));

            paymentRec.setAmount$(getDouble(FRM_FIELD_AMOUNT_$));

            paymentRec.setAmountText(getString(FRM_FIELD_AMOUNT_TEXT));

            paymentRec.setReceivedBy(getString(FRM_FIELD_RECEIVED_BY));

            paymentRec.setExchangeRate(getDouble(FRM_FIELD_EXCHANGE_RATE));

            paymentRec.setDescription(getString(FRM_FIELD_DESCRIPTION));

            paymentRec.setPaymentPurpose(getInt(FRM_FIELD_PAYMENT_PURPOSE));

            //System.out.println("-------------- exc xjbxjbvjxb tidak");

            paymentRec.setCcExpiredDate(getDate(FRM_FIELD_CC_EXPIRED_DATE));

            //System.out.println("-------------- exc xjbxjbvjxb tidak");

            paymentRec.setCcHolderName(getString(FRM_FIELD_CC_HOLDER_NAME));

            paymentRec.setCcIssuingBank(getString(FRM_FIELD_CC_ISSUING_BANK));

            paymentRec.setCcNumber(getString(FRM_FIELD_CC_NUMBER));

            paymentRec.setBillAddrCity(getString(FRM_FIELD_BILL_ADDR_CITY));

            //System.out.println("-------------- exc xjbxjbvjxb tidak");

            paymentRec.setBillAddrCountry(getString(FRM_FIELD_BILL_ADDR_COUNTRY));

            paymentRec.setBillAddrPhoneNumber(getString(FRM_FIELD_BILL_ADDR_PHONE_NUMBER));

            paymentRec.setBillAddrState(getString(FRM_FIELD_BILL_ADDR_STATE));

            paymentRec.setBillAddrStreet(getString(FRM_FIELD_BILL_ADDR_STREET));

            //System.out.println("-------------- exc xjbxjbvjxb tidak");

            paymentRec.setBillAddrZipCode(getString(FRM_FIELD_BILL_ADDR_ZIP_CODE));

            paymentRec.setPaymentType(getInt(FRM_FIELD_PAYMENT_TYPE));

            paymentRec.setAddOrSubstract(getInt(FRM_FIELD_ADD_OR_SUBSTRACT));

            //System.out.println("-------------- exc xjbxjbvjxb tidak");

            paymentRec.setCurrencyUsed(getInt(FLD_FIELD_CURRENCY_USED));

            paymentRec.setSetRsvToOut(getBoolean(FLD_FIELD_SET_RSV_TO_OUT));

            

            paymentRec.setCashierId(getLong(FLD_FIELD_CASH_CASHIER_ID));

            

            paymentRec.setPaymentSystemId(getLong(FRM_FIELD_PAYMENT_SYSTEM_ID));

            

            

        }catch(Exception e){

            System.out.println("Error on requestEntityObject : "+e.toString());

        }

    }

}

