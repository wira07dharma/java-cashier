/* 

 * Form Name  	:  FrmPaymentRecSplit.java 

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

import com.dimata.cashierweb.entity.cashier.transaction.PaymentRecSplit;
import java.io.*; 

import java.util.*; 

import javax.servlet.*;

import javax.servlet.http.*; 

/* qdep package */ 

import com.dimata.qdep.form.*;

/* project package */




public class FrmPaymentRecSplit extends FRMHandler implements I_FRMInterface, I_FRMType 

{

	private PaymentRecSplit paymentRecSplit;



	public static final String FRM_NAME_PAYMENTRECSPLIT		=  "FRM_NAME_PAYMENTRECSPLIT" ;



	public static final int FRM_FIELD_PAYMENT_REC_SPLIT_ID			=  0 ;

	public static final int FRM_FIELD_RESERVATION_ID			=  1 ;

	public static final int FRM_FIELD_INSTALLMENT_PAYMENT_ID			=  2 ;

	public static final int FRM_FIELD_MAINTENANCE_FEE_PAYMENT_ID			=  3 ;

	public static final int FRM_FIELD_PAYMENT_REC_ID			=  4 ;

	public static final int FRM_FIELD_COVER_BILLING_ID			=  5 ;

	public static final int FRM_FIELD_TELEPHONE_CALL_ID			=  6 ;

	public static final int FRM_FIELD_AMOUNT_RP			=  7 ;

	public static final int FRM_FIELD_AMOUNT_USD			=  8 ;

	public static final int FRM_FIELD_CURRENCY_USED			=  9 ;

	public static final int FRM_FIELD_ADD_OR_SUBSTRACT			=  10 ;

	public static final int FRM_FIELD_EXCHANGE_RATE			=  11 ;

	public static final int FRM_FIELD_PAYMENT_PURPOSE			=  12 ;

	public static final int FRM_FIELD_PAYMENT_DATE			=  13 ;

        public static final  int FLD_FIELD_CASH_CASHIER_ID				= 14;

        public static final  int FLD_FIELD_PAYMENT_SYSTEM_ID				= 15;

          



	public static String[] fieldNames = {

		"FRM_FIELD_PAYMENT_REC_SPLIT_ID",  "FRM_FIELD_RESERVATION_ID",

		"FRM_FIELD_INSTALLMENT_PAYMENT_ID",  "FRM_FIELD_MAINTENANCE_FEE_PAYMENT_ID",

		"FRM_FIELD_PAYMENT_REC_ID",  "FRM_FIELD_COVER_BILLING_ID",

		"FRM_FIELD_TELEPHONE_CALL_ID",  "FRM_FIELD_AMOUNT_RP",

		"FRM_FIELD_AMOUNT_USD",  "FRM_FIELD_CURRENCY_USED",

		"FRM_FIELD_ADD_OR_SUBSTRACT",  "FRM_FIELD_EXCHANGE_RATE",

		"FRM_FIELD_PAYMENT_PURPOSE",  "FRM_FIELD_PAYMENT_DATE",

                "FLD_FIELD_CASH_CASHIER_ID", "FLD_FIELD_PAYMENT_SYSTEM_ID"

	} ;



	public static int[] fieldTypes = {

		TYPE_LONG,  TYPE_LONG,

		TYPE_LONG,  TYPE_LONG,

		TYPE_LONG,  TYPE_LONG,

		TYPE_LONG,  TYPE_FLOAT,

		TYPE_FLOAT,  TYPE_INT,

		TYPE_INT,  TYPE_FLOAT,

		TYPE_INT,  TYPE_DATE,  

                TYPE_LONG, TYPE_LONG

	} ;



	public FrmPaymentRecSplit(){

	}

	public FrmPaymentRecSplit(PaymentRecSplit paymentRecSplit){

		this.paymentRecSplit = paymentRecSplit;

	}



	public FrmPaymentRecSplit(HttpServletRequest request, PaymentRecSplit paymentRecSplit){

		super(new FrmPaymentRecSplit(paymentRecSplit), request);

		this.paymentRecSplit = paymentRecSplit;

	}



	public String getFormName() { return FRM_NAME_PAYMENTRECSPLIT; } 



	public int[] getFieldTypes() { return fieldTypes; }



	public String[] getFieldNames() { return fieldNames; } 



	public int getFieldSize() { return fieldNames.length; } 



	public PaymentRecSplit getEntityObject(){ return paymentRecSplit; }



	public void requestEntityObject(PaymentRecSplit paymentRecSplit) {

		try{

			this.requestParam();

			paymentRecSplit.setReservationId(getLong(FRM_FIELD_RESERVATION_ID));

			paymentRecSplit.setInstallmentPaymentId(getLong(FRM_FIELD_INSTALLMENT_PAYMENT_ID));

			paymentRecSplit.setMaintenanceFeePaymentId(getLong(FRM_FIELD_MAINTENANCE_FEE_PAYMENT_ID));

			paymentRecSplit.setPaymentRecId(getLong(FRM_FIELD_PAYMENT_REC_ID));

			paymentRecSplit.setCoverBillingId(getLong(FRM_FIELD_COVER_BILLING_ID));

			paymentRecSplit.setTelephoneCallId(getLong(FRM_FIELD_TELEPHONE_CALL_ID));

			paymentRecSplit.setAmountRp(getDouble(FRM_FIELD_AMOUNT_RP));

			paymentRecSplit.setAmountUsd(getDouble(FRM_FIELD_AMOUNT_USD));

			paymentRecSplit.setCurrencyUsed(getInt(FRM_FIELD_CURRENCY_USED));

			paymentRecSplit.setAddOrSubstract(getInt(FRM_FIELD_ADD_OR_SUBSTRACT));

			paymentRecSplit.setExchangeRate(getDouble(FRM_FIELD_EXCHANGE_RATE));

			paymentRecSplit.setPaymentPurpose(getInt(FRM_FIELD_PAYMENT_PURPOSE));

			paymentRecSplit.setPaymentDate(getDate(FRM_FIELD_PAYMENT_DATE));

                        paymentRecSplit.setCashierId(getLong(FLD_FIELD_CASH_CASHIER_ID)); 

                        

                        paymentRecSplit.setPaymentSystemId(getLong(FLD_FIELD_PAYMENT_SYSTEM_ID)); 

                        

		}catch(Exception e){

			System.out.println("Error on requestEntityObject : "+e.toString());

		}

	}

}

