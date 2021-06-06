

/* Created on 	:  [date] [time] AM/PM 

 * 

 * @author	 :

 * @version	 :

 */



/*******************************************************************

 * Class Description 	: [project description ... ] 

 * Imput Parameters 	: [input parameter ...] 

 * Output 		: [output ...] 

 *******************************************************************/



package com.dimata.cashierweb.entity.cashier.transaction; 



import com.dimata.common.entity.payment.PstPaymentSystem;

/* package java */ 

import java.io.*

;

import java.sql.*

;import java.util.*

;import java.util.Date;

import com.dimata.util.*;



/* package qdep */

import com.dimata.util.lang.I_Language;

import com.dimata.common.db.*;

import com.dimata.qdep.entity.*;





public class PstPaymentRecSplit extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language { 



	//public static final  String TBL_PAYMENT_REC_SPLIT = "PAYMENT_REC_SPLIT";

        public static final  String TBL_PAYMENT_REC_SPLIT = "payment_rec_split";



	public static final  int FLD_PAYMENT_REC_SPLIT_ID       = 0;

	public static final  int FLD_RESERVATION_ID             = 1;

	public static final  int FLD_INSTALLMENT_PAYMENT_ID     = 2;

	public static final  int FLD_MAINTENANCE_FEE_PAYMENT_ID = 3;

	public static final  int FLD_PAYMENT_REC_ID             = 4;

	public static final  int FLD_COVER_BILLING_ID           = 5;

	public static final  int FLD_TELEPHONE_CALL_ID          = 6;

	public static final  int FLD_AMOUNT_RP                  = 7;

	public static final  int FLD_AMOUNT_USD                 = 8;

	public static final  int FLD_CURRENCY_USED              = 9;

	public static final  int FLD_ADD_OR_SUBSTRACT           = 10;

	public static final  int FLD_EXCHANGE_RATE              = 11;

	public static final  int FLD_PAYMENT_PURPOSE            = 12;

	public static final  int FLD_PAYMENT_DATE               = 13;

        public static final  int FLD_CASH_CASHIER_ID            = 14;



        public static final  int FLD_RESERVATION_COMMISSION_ID  = 15;

        public static final  int FLD_NUMBER                     = 16;

        public static final  int FLD_COUNTER                    = 17;

        public static final  int FLD_PAY_OUT                    = 18;

        public static final  int FLD_STATUS                     = 19;

        public static final  int FLD_DATE                       = 20;

        public static final  int FLD_RESERVATION_CHARGE_ID      = 21;

        public static final  int FLD_DOC_REF_NUMBER             = 22;

        public static final  int FLD_ADJUSTMENT_ID              = 23;

        

        public static final  int FLD_PAYMENT_SYSTEM_ID          = 24;

        public static final  int FLD_CLEARED_TO_ID              = 25;

        public static final  int FLD_DUE_DATE                   = 25;

        

        //public static final  int FLD_DP_REF_ID                  = 27;

        //public static final  int FLD_DP_REF_NUMBER              = 28;

      



	public static final  String[] fieldNames = {

		"PAYMENT_REC_SPLIT_ID",

		"RESERVATION_ID",

		"INSTALLMENT_PAYMENT_ID",

		"MAINTENANCE_FEE_PAYMENT_ID",

		"PAYMENT_REC_ID",

		"CASH_BILL_MAIN_ID",

		"TELEPHONE_CALL_ID",

		"AMOUNT_RP",

		"AMOUNT_USD",

		"CURRENCY_USED",

		"ADD_OR_SUBSTRACT",

		"EXCHANGE_RATE",

		"PAYMENT_PURPOSE",

		"PAYMENT_DATE",

                "CASH_CASHIER_ID", 

        

                "RESERVATION_COMMISSION_ID",

                "NUMBER", "COUNTER", "PAY_OUT", 

                "STATUS", "DATE",

                "RESERVATION_CHARGE_ID",

                "DOC_REF_NUMBER",

                "ADJUSTMENT_ID",

                

                "PAYMENT_SYSTEM_ID", 

                "CLEARED_TO_ID",

                "DUE_DATE"

                

                //"DP_REF_ID",

                //"DP_REF_NUMBER"

                

	 }; 



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_LONG,

		TYPE_LONG,

		TYPE_LONG,

		TYPE_LONG,

		TYPE_LONG,

		TYPE_LONG,

		TYPE_FLOAT,

		TYPE_FLOAT,

		TYPE_INT,

		TYPE_INT,

		TYPE_FLOAT,

		TYPE_INT,

		TYPE_DATE,

                TYPE_LONG,

        

                TYPE_LONG,

                TYPE_STRING, 

                TYPE_INT, 

                TYPE_BOOL,

                TYPE_INT, 

                TYPE_DATE,

                TYPE_LONG, 

                TYPE_STRING,

                TYPE_LONG,

                

                TYPE_LONG, 

                TYPE_LONG,

                TYPE_DATE,

                

                //TYPE_LONG,

                //TYPE_STRING





	 };



    /*public static final int PAY_DEPOSIT 	 = 0;

    public static final int PAY_ROOM_RSV 	 = 1;*/

    public static final int PAY_ROOM_RSV 	 = 0;

    public static final int PAY_DEPOSIT 	 = 1;     

    public static final int PAY_BILLING 	 = 2;

    public static final int PAY_TELP 		 = 3;

    public static final int PAY_OUTLET		 = 4;

    public static final int PAY_ALL 		 = 5;

    public static final int PAY_INSTALLMENT      = 6;

    public static final int PAY_MAINTENANCE_FEE  = 7;

    public static final int PAY_ADJUSTMENT       = 8;

    public static final int PAY_COMMISSION       = 9;



    public static final String[] paymentPurpose = {

        "Deposit", "Room Reservation", "Billing", "Telephone", "Outlet","All Payment", "Installment", "Maintenence Fee", "Adjustment", "Commission"};



    public static final int TYPE_ADD	= 0;

    public static final int TYPE_SUBSTRACT = 1;



    public static final String[] addSubstract = {"Add", "Substract"};



    public static final String[] currencyStr = {"", "Rp.", "US$"};

    

    public static final int PAID_FOR_INVOICE		= 0;

    public static final int PAID_FOR_DEPOSIT 		= 1;

    public static final int PAID_FOR_DEPOSIT_PAYMENT 	= 2;

    public static final int PAID_FOR_DEPOSIT_REFUND 	= 3;

    public static final int PAID_FOR_OTHER_INCOME	= 4;

    public static final int PAID_FOR_EXPENSE		= 5;

    

    public static final String[] strPaidFor = {"Payment", "Deposit", "Deposit Payment", "Deposit Refund", "Other Income"};

 



	public PstPaymentRecSplit(){

	}



	public PstPaymentRecSplit(int i) throws DBException { 

		super(new PstPaymentRecSplit()); 

	}



	public PstPaymentRecSplit(String sOid) throws DBException { 

		super(new PstPaymentRecSplit(0)); 

		if(!locate(sOid)) 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		else 

			return; 

	}



	public PstPaymentRecSplit(long lOid) throws DBException { 

		super(new PstPaymentRecSplit(0)); 

		String sOid = "0"; 

		try { 

			sOid = String.valueOf(lOid); 

		}catch(Exception e) { 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		} 

		if(!locate(sOid)) 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		else 

			return; 

	} 



	public int getFieldSize(){ 

		return fieldNames.length; 

	}



	public String getTableName(){ 

		return TBL_PAYMENT_REC_SPLIT;

	}



	public String[] getFieldNames(){ 

		return fieldNames; 

	}



	public int[] getFieldTypes(){ 

		return fieldTypes; 

	}



	public String getPersistentName(){ 

		return new PstPaymentRecSplit().getClass().getName(); 

	}



	public long fetchExc(Entity ent) throws Exception{ 

		PaymentRecSplit paymentrecsplit = fetchExc(ent.getOID()); 

		ent = (Entity)paymentrecsplit; 

		return paymentrecsplit.getOID(); 

	}



	public long insertExc(Entity ent) throws Exception{ 

		return insertExc((PaymentRecSplit) ent); 

	}



	public long updateExc(Entity ent) throws Exception{ 

		return updateExc((PaymentRecSplit) ent); 

	}



	public long deleteExc(Entity ent) throws Exception{ 

		if(ent==null){ 

			throw new DBException(this,DBException.RECORD_NOT_FOUND); 

		} 

		return deleteExc(ent.getOID()); 

	}



	public static PaymentRecSplit fetchExc(long oid) throws DBException{ 

		try{ 

			PaymentRecSplit paymentrecsplit = new PaymentRecSplit();

			PstPaymentRecSplit pstPaymentRecSplit = new PstPaymentRecSplit(oid); 

			paymentrecsplit.setOID(oid);



			paymentrecsplit.setReservationId(pstPaymentRecSplit.getlong(FLD_RESERVATION_ID));

			paymentrecsplit.setInstallmentPaymentId(pstPaymentRecSplit.getlong(FLD_INSTALLMENT_PAYMENT_ID));

			paymentrecsplit.setMaintenanceFeePaymentId(pstPaymentRecSplit.getlong(FLD_MAINTENANCE_FEE_PAYMENT_ID));

			paymentrecsplit.setPaymentRecId(pstPaymentRecSplit.getlong(FLD_PAYMENT_REC_ID));

			paymentrecsplit.setCoverBillingId(pstPaymentRecSplit.getlong(FLD_COVER_BILLING_ID));

			paymentrecsplit.setTelephoneCallId(pstPaymentRecSplit.getlong(FLD_TELEPHONE_CALL_ID));

			paymentrecsplit.setAmountRp(pstPaymentRecSplit.getdouble(FLD_AMOUNT_RP));

			paymentrecsplit.setAmountUsd(pstPaymentRecSplit.getdouble(FLD_AMOUNT_USD));

			paymentrecsplit.setCurrencyUsed(pstPaymentRecSplit.getInt(FLD_CURRENCY_USED));

			paymentrecsplit.setAddOrSubstract(pstPaymentRecSplit.getInt(FLD_ADD_OR_SUBSTRACT));

			paymentrecsplit.setExchangeRate(pstPaymentRecSplit.getdouble(FLD_EXCHANGE_RATE));

			paymentrecsplit.setPaymentPurpose(pstPaymentRecSplit.getInt(FLD_PAYMENT_PURPOSE));

			paymentrecsplit.setPaymentDate(pstPaymentRecSplit.getDate(FLD_PAYMENT_DATE));

                        paymentrecsplit.setCashierId(pstPaymentRecSplit.getlong(FLD_CASH_CASHIER_ID));

                        

                        paymentrecsplit.setReservationCommissionId(pstPaymentRecSplit.getlong(FLD_RESERVATION_COMMISSION_ID));

                        paymentrecsplit.setNumber(pstPaymentRecSplit.getString(FLD_NUMBER));

                        paymentrecsplit.setCounter(pstPaymentRecSplit.getInt(FLD_COUNTER));

                        paymentrecsplit.setPayOut(pstPaymentRecSplit.getboolean(FLD_PAY_OUT));

                        paymentrecsplit.setStatus(pstPaymentRecSplit.getInt(FLD_STATUS));

                        paymentrecsplit.setDate(pstPaymentRecSplit.getDate(FLD_DATE));

                        paymentrecsplit.setReservationChargeId(pstPaymentRecSplit.getlong(FLD_RESERVATION_CHARGE_ID));

                        paymentrecsplit.setDocRefNumber(pstPaymentRecSplit.getString(FLD_DOC_REF_NUMBER));

                        paymentrecsplit.setAdjustmentId(pstPaymentRecSplit.getlong(FLD_ADJUSTMENT_ID));

                        

                        paymentrecsplit.setPaymentSystemId(pstPaymentRecSplit.getlong(FLD_PAYMENT_SYSTEM_ID));

                        paymentrecsplit.setClearedToId(pstPaymentRecSplit.getlong(FLD_CLEARED_TO_ID));

                        paymentrecsplit.setDueDate(pstPaymentRecSplit.getDate(FLD_DUE_DATE));

                        

                        //paymentrecsplit.setDpRefId(pstPaymentRecSplit.getlong(FLD_DP_REF_ID));

                        //paymentrecsplit.setDpRefNumber(pstPaymentRecSplit.getString(FLD_DP_REF_NUMBER));



			return paymentrecsplit; 

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstPaymentRecSplit(0),DBException.UNKNOWN); 

		} 

	}



	public static long insertExc(PaymentRecSplit paymentrecsplit) throws DBException{ 

		try{ 

			PstPaymentRecSplit pstPaymentRecSplit = new PstPaymentRecSplit(0);



			pstPaymentRecSplit.setLong(FLD_RESERVATION_ID, paymentrecsplit.getReservationId());

			pstPaymentRecSplit.setLong(FLD_INSTALLMENT_PAYMENT_ID, paymentrecsplit.getInstallmentPaymentId());

			pstPaymentRecSplit.setLong(FLD_MAINTENANCE_FEE_PAYMENT_ID, paymentrecsplit.getMaintenanceFeePaymentId());

			pstPaymentRecSplit.setLong(FLD_PAYMENT_REC_ID, paymentrecsplit.getPaymentRecId());

			pstPaymentRecSplit.setLong(FLD_COVER_BILLING_ID, paymentrecsplit.getCoverBillingId());

			pstPaymentRecSplit.setLong(FLD_TELEPHONE_CALL_ID, paymentrecsplit.getTelephoneCallId());

			pstPaymentRecSplit.setDouble(FLD_AMOUNT_RP, paymentrecsplit.getAmountRp());

			pstPaymentRecSplit.setDouble(FLD_AMOUNT_USD, paymentrecsplit.getAmountUsd());

			pstPaymentRecSplit.setLong(FLD_CURRENCY_USED, paymentrecsplit.getCurrencyUsed());

			pstPaymentRecSplit.setInt(FLD_ADD_OR_SUBSTRACT, paymentrecsplit.getAddOrSubstract());

			pstPaymentRecSplit.setDouble(FLD_EXCHANGE_RATE, paymentrecsplit.getExchangeRate());

			pstPaymentRecSplit.setInt(FLD_PAYMENT_PURPOSE, paymentrecsplit.getPaymentPurpose());

			pstPaymentRecSplit.setDate(FLD_PAYMENT_DATE, paymentrecsplit.getPaymentDate());

                        pstPaymentRecSplit.setLong(FLD_CASH_CASHIER_ID, paymentrecsplit.getCashierId());

                        

                        pstPaymentRecSplit.setLong(FLD_RESERVATION_COMMISSION_ID, paymentrecsplit.getReservationCommissionId());

                        pstPaymentRecSplit.setString(FLD_NUMBER, paymentrecsplit.getNumber());

                        pstPaymentRecSplit.setboolean(FLD_PAY_OUT, paymentrecsplit.getPayOut());

                        pstPaymentRecSplit.setInt(FLD_COUNTER, paymentrecsplit.getCounter());

                        pstPaymentRecSplit.setInt(FLD_STATUS, paymentrecsplit.getStatus());

                        pstPaymentRecSplit.setDate(FLD_DATE, paymentrecsplit.getDate());

                        

                        pstPaymentRecSplit.setLong(FLD_RESERVATION_CHARGE_ID, paymentrecsplit.getReservationChargeId());

                        pstPaymentRecSplit.setString(FLD_DOC_REF_NUMBER, paymentrecsplit.getDocRefNumber());                        

                        pstPaymentRecSplit.setLong(FLD_ADJUSTMENT_ID, paymentrecsplit.getAdjustmentId());                        

                        pstPaymentRecSplit.setLong(FLD_PAYMENT_SYSTEM_ID, paymentrecsplit.getPaymentSystemId());

                        pstPaymentRecSplit.setLong(FLD_CLEARED_TO_ID, paymentrecsplit.getClearedToId());

                        pstPaymentRecSplit.setDate(FLD_DUE_DATE, paymentrecsplit.getDueDate());                        

                        

                        //pstPaymentRecSplit.setLong(FLD_DP_REF_ID, paymentrecsplit.getDpRefId());                        

                        //pstPaymentRecSplit.setString(FLD_DP_REF_NUMBER, paymentrecsplit.getDpRefNumber());                        

                        

			pstPaymentRecSplit.insert(); 

			paymentrecsplit.setOID(pstPaymentRecSplit.getlong(FLD_PAYMENT_REC_SPLIT_ID));

                        

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstPaymentRecSplit(0),DBException.UNKNOWN); 

		}

		return paymentrecsplit.getOID();

	}



	public static long updateExc(PaymentRecSplit paymentrecsplit) throws DBException{ 

		try{ 

			if(paymentrecsplit.getOID() != 0){ 

				PstPaymentRecSplit pstPaymentRecSplit = new PstPaymentRecSplit(paymentrecsplit.getOID());



				pstPaymentRecSplit.setLong(FLD_RESERVATION_ID, paymentrecsplit.getReservationId());

				pstPaymentRecSplit.setLong(FLD_INSTALLMENT_PAYMENT_ID, paymentrecsplit.getInstallmentPaymentId());

				pstPaymentRecSplit.setLong(FLD_MAINTENANCE_FEE_PAYMENT_ID, paymentrecsplit.getMaintenanceFeePaymentId());

				pstPaymentRecSplit.setLong(FLD_PAYMENT_REC_ID, paymentrecsplit.getPaymentRecId());

				pstPaymentRecSplit.setLong(FLD_COVER_BILLING_ID, paymentrecsplit.getCoverBillingId());

				pstPaymentRecSplit.setLong(FLD_TELEPHONE_CALL_ID, paymentrecsplit.getTelephoneCallId());

				pstPaymentRecSplit.setDouble(FLD_AMOUNT_RP, paymentrecsplit.getAmountRp());

				pstPaymentRecSplit.setDouble(FLD_AMOUNT_USD, paymentrecsplit.getAmountUsd());

				pstPaymentRecSplit.setLong(FLD_CURRENCY_USED, paymentrecsplit.getCurrencyUsed());

				pstPaymentRecSplit.setInt(FLD_ADD_OR_SUBSTRACT, paymentrecsplit.getAddOrSubstract());

				pstPaymentRecSplit.setDouble(FLD_EXCHANGE_RATE, paymentrecsplit.getExchangeRate());

				pstPaymentRecSplit.setInt(FLD_PAYMENT_PURPOSE, paymentrecsplit.getPaymentPurpose());

				pstPaymentRecSplit.setDate(FLD_PAYMENT_DATE, paymentrecsplit.getPaymentDate());

                                pstPaymentRecSplit.setLong(FLD_CASH_CASHIER_ID, paymentrecsplit.getCashierId());

                                

                                pstPaymentRecSplit.setLong(FLD_RESERVATION_COMMISSION_ID, paymentrecsplit.getReservationCommissionId());

                                pstPaymentRecSplit.setString(FLD_NUMBER, paymentrecsplit.getNumber());

                                pstPaymentRecSplit.setboolean(FLD_PAY_OUT, paymentrecsplit.getPayOut());

                                pstPaymentRecSplit.setInt(FLD_COUNTER, paymentrecsplit.getCounter());

                                pstPaymentRecSplit.setInt(FLD_STATUS, paymentrecsplit.getStatus());

                                pstPaymentRecSplit.setDate(FLD_DATE, paymentrecsplit.getDate());

                                

                                pstPaymentRecSplit.setLong(FLD_RESERVATION_CHARGE_ID, paymentrecsplit.getReservationChargeId());

                                pstPaymentRecSplit.setString(FLD_DOC_REF_NUMBER, paymentrecsplit.getDocRefNumber());                        

                                pstPaymentRecSplit.setLong(FLD_ADJUSTMENT_ID, paymentrecsplit.getAdjustmentId());                        

                                

                                pstPaymentRecSplit.setLong(FLD_PAYMENT_SYSTEM_ID, paymentrecsplit.getPaymentSystemId());

                                pstPaymentRecSplit.setLong(FLD_CLEARED_TO_ID, paymentrecsplit.getClearedToId());

                                pstPaymentRecSplit.setDate(FLD_DUE_DATE, paymentrecsplit.getDueDate());                        

                                

                                //pstPaymentRecSplit.setLong(FLD_DP_REF_ID, paymentrecsplit.getDpRefId());                        

                                //pstPaymentRecSplit.setString(FLD_DP_REF_NUMBER, paymentrecsplit.getDpRefNumber());                        

                                

				pstPaymentRecSplit.update(); 

				return paymentrecsplit.getOID();



			}

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstPaymentRecSplit(0),DBException.UNKNOWN); 

		}

		return 0;

	}



	public static long deleteExc(long oid) throws DBException{ 

		try{ 

			PstPaymentRecSplit pstPaymentRecSplit = new PstPaymentRecSplit(oid);

			pstPaymentRecSplit.delete();

		}catch(DBException dbe){ 

			throw dbe; 

		}catch(Exception e){ 

			throw new DBException(new PstPaymentRecSplit(0),DBException.UNKNOWN); 

		}

		return oid;

	}

        public static long deleteBillPayments(long oidMain) {

        long oid = 0;

        DBResultSet dbrs = null;

        try {

            String sql = " DELETE FROM " + TBL_PAYMENT_REC_SPLIT+

                    " WHERE " + fieldNames[FLD_COVER_BILLING_ID] + "='" + oidMain + "'";

            DBHandler.execUpdate(sql);

        } catch (Exception e) {

            System.out.println(e);

        } finally {

            DBResultSet.close(dbrs);

        }

        return oidMain;

    }

        /**
         *
         * delete payment rec split berdasarkan payment rec id
         * @param paymentRecId
         * @return
         */
        public static long deletePaymentRecSplitWithPaymentRec(long paymentRecId) {

        long oid = 0;

        DBResultSet dbrs = null;

        try {

            String sql = " DELETE FROM " + TBL_PAYMENT_REC_SPLIT+

                    " WHERE " + fieldNames[FLD_PAYMENT_REC_ID] + "='" + paymentRecId + "'";

            DBHandler.execUpdate(sql);

        } catch (Exception e) {

            System.out.println(e);

        } finally {

            DBResultSet.close(dbrs);

        }

        return paymentRecId;

    }


	public static Vector listAll(){ 

		return list(0, 500, "",""); 

	}



	public static Vector list(int limitStart,int recordToGet, String whereClause, String order){

		Vector lists = new Vector(); 

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT * FROM " + TBL_PAYMENT_REC_SPLIT; 

			if(whereClause != null && whereClause.length() > 0)

				sql = sql + " WHERE " + whereClause;

			if(order != null && order.length() > 0)

				sql = sql + " ORDER BY " + order;

			if(limitStart == 0 && recordToGet == 0)

				sql = sql + "";

			else

				sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

                        

                        System.out.println(sql);

			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();

			while(rs.next()) {

				PaymentRecSplit paymentrecsplit = new PaymentRecSplit();

				resultToObject(rs, paymentrecsplit);

				lists.add(paymentrecsplit);

			}

			rs.close();

			return lists;



		}catch(Exception e) {

			System.out.println(e);

		}finally {

			DBResultSet.close(dbrs);

		}

			return new Vector();

	}



	public static void resultToObject(ResultSet rs, PaymentRecSplit paymentrecsplit){

		try{

			paymentrecsplit.setOID(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_PAYMENT_REC_SPLIT_ID]));

			paymentrecsplit.setReservationId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_RESERVATION_ID]));

			paymentrecsplit.setInstallmentPaymentId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_INSTALLMENT_PAYMENT_ID]));

			paymentrecsplit.setMaintenanceFeePaymentId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_MAINTENANCE_FEE_PAYMENT_ID]));

			paymentrecsplit.setPaymentRecId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_PAYMENT_REC_ID]));

			paymentrecsplit.setCoverBillingId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_COVER_BILLING_ID]));

			paymentrecsplit.setTelephoneCallId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_TELEPHONE_CALL_ID]));

			paymentrecsplit.setAmountRp(rs.getDouble(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_AMOUNT_RP]));

			paymentrecsplit.setAmountUsd(rs.getDouble(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_AMOUNT_USD]));

			paymentrecsplit.setCurrencyUsed(rs.getInt(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_CURRENCY_USED]));

			paymentrecsplit.setAddOrSubstract(rs.getInt(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_ADD_OR_SUBSTRACT]));

			paymentrecsplit.setExchangeRate(rs.getDouble(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_EXCHANGE_RATE]));

			paymentrecsplit.setPaymentPurpose(rs.getInt(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_PAYMENT_PURPOSE]));

			paymentrecsplit.setPaymentDate(rs.getDate(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_PAYMENT_DATE]));

                        paymentrecsplit.setCashierId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_CASH_CASHIER_ID]));

                        

                        paymentrecsplit.setReservationCommissionId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_RESERVATION_COMMISSION_ID]));

                        paymentrecsplit.setNumber(rs.getString(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_NUMBER]));

                        paymentrecsplit.setCounter(rs.getInt(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_COUNTER]));

                        paymentrecsplit.setPayOut(rs.getBoolean(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_PAY_OUT]));

                        paymentrecsplit.setStatus(rs.getInt(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_STATUS]));

                        paymentrecsplit.setDate(rs.getDate(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_DATE]));

                        

                        paymentrecsplit.setReservationChargeId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_RESERVATION_CHARGE_ID]));

                        paymentrecsplit.setDocRefNumber(rs.getString(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_DOC_REF_NUMBER]));

                        paymentrecsplit.setAdjustmentId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_ADJUSTMENT_ID]));

                        

                        paymentrecsplit.setPaymentSystemId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_PAYMENT_SYSTEM_ID]));

                        paymentrecsplit.setClearedToId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_CLEARED_TO_ID]));

                        paymentrecsplit.setDueDate(rs.getDate(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_DUE_DATE]));

                        

                        //paymentrecsplit.setDpRefId(rs.getLong(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_DP_REF_ID]));

                        //paymentrecsplit.setDpRefNumber(rs.getString(PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_DP_REF_NUMBER]));

                        

		}catch(Exception e){ }

	}



	public static boolean checkOID(long paymentRecSplitId){

		DBResultSet dbrs = null;

		boolean result = false;

		try{

			String sql = "SELECT * FROM " + TBL_PAYMENT_REC_SPLIT + " WHERE " + 

						PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_PAYMENT_REC_SPLIT_ID] + " = " + paymentRecSplitId;



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();



			while(rs.next()) { result = true; }

			rs.close();

		}catch(Exception e){

			System.out.println("err : "+e.toString());

		}finally{

			DBResultSet.close(dbrs);

			return result;

		}

	}



	public static int getCount(String whereClause){

		DBResultSet dbrs = null;

		try {

			String sql = "SELECT COUNT("+ PstPaymentRecSplit.fieldNames[PstPaymentRecSplit.FLD_PAYMENT_REC_SPLIT_ID] + ") FROM " + TBL_PAYMENT_REC_SPLIT;

			if(whereClause != null && whereClause.length() > 0)

				sql = sql + " WHERE " + whereClause;



			dbrs = DBHandler.execQueryResult(sql);

			ResultSet rs = dbrs.getResultSet();



			int count = 0;

			while(rs.next()) { count = rs.getInt(1); }



			rs.close();

			return count;

		}catch(Exception e) {

			return 0;

		}finally {

			DBResultSet.close(dbrs);

		}

	}





	/* This method used to find current data */

	public static int findLimitStart( long oid, int recordToGet, String whereClause){

		String order = "";

		int size = getCount(whereClause);

		int start = 0;

		boolean found =false;

		for(int i=0; (i < size) && !found ; i=i+recordToGet){

			 Vector list =  list(i,recordToGet, whereClause, order); 

			 start = i;

			 if(list.size()>0){

			  for(int ls=0;ls<list.size();ls++){ 

			  	   PaymentRecSplit paymentrecsplit = (PaymentRecSplit)list.get(ls);

				   if(oid == paymentrecsplit.getOID())

					  found=true;

			  }

		  }

		}

		if((start >= size) && (size > 0))

		    start = start - recordToGet;



		return start;

	}



}

