/**

 * Created by IntelliJ IDEA.

 * User: eka

 * Date: Sep 14, 2004

 * Time: 10:38:55 AM

 * To change this template use Options | File Templates.

 */

package com.dimata.hanoman.entity.cashier;



import com.dimata.common.db.*;

import com.dimata.qdep.entity.I_PersintentExc; 

import com.dimata.qdep.entity.Entity;

import com.dimata.util.lang.I_Language;



import java.util.Vector;

import java.sql.ResultSet;



public class PstCashClosing extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {



    public static final  String TBL_CASH_CLOSING = "cash_closing";



	public static final  int FLD_CASH_CLOSING_ID = 0;

	public static final  int FLD_CASH_CASHIER_ID = 1;

	public static final  int FLD_PAYMENT_TYPE = 2;

	public static final  int FLD_AMOUNT_RP = 3;

	public static final  int FLD_AMOUNT_USD = 4;



	public static final  String[] fieldNames = {

		"CASH_CLOSING_ID",

		"CASH_CASHIER_ID",

		"PAYMENT_TYPE",

		"AMOUNT_RP",

		"AMOUNT_USD"

	 };



	public static final  int[] fieldTypes = {

		TYPE_LONG + TYPE_PK + TYPE_ID,

		TYPE_LONG,

		TYPE_INT,

        TYPE_FLOAT,

        TYPE_FLOAT

	 };





    public PstCashClosing(){

	}



	public PstCashClosing(int i) throws DBException {

		super(new PstCashClosing());

	}



	public PstCashClosing(String sOid) throws DBException {

		super(new PstCashClosing(0));

		if(!locate(sOid))

			throw new DBException(this,DBException.RECORD_NOT_FOUND);

		else

			return;

	}



	public PstCashClosing(long lOid) throws DBException {

		super(new PstCashClosing(0));

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







    public int getFieldSize() {

        return fieldNames.length;

    }



    public String getTableName() {

        return TBL_CASH_CLOSING;

    }



    public String[] getFieldNames() {

        return fieldNames;

    }



    public int[] getFieldTypes() {

        return fieldTypes;

    }



    public String getPersistentName() {

        return new PstCashClosing().getClass().getName();

    }



    public long fetchExc(Entity ent) throws Exception {

        CashClosing objCashClose = fetchExc(ent.getOID());

		ent = (Entity)objCashClose;

		return objCashClose.getOID();

    }



    public long updateExc(Entity ent) throws Exception {

       return updateExc((CashClosing) ent);

    }



    public long deleteExc(Entity ent) throws Exception {

        if(ent==null){

			throw new DBException(this,DBException.RECORD_NOT_FOUND);

		}

		return deleteExc(ent.getOID());

    }



    public long insertExc(Entity ent) throws Exception {

        return insertExc((CashClosing) ent);

    }

    public static CashClosing fetchExc(long oid) throws DBException{

		try{

			CashClosing objCashClose = new CashClosing();

			PstCashClosing objPstCashClose = new PstCashClosing(oid);

			objCashClose.setOID(oid);

			objCashClose.setCashCashierId(objPstCashClose.getlong(FLD_CASH_CASHIER_ID));

			objCashClose.setpayType(objPstCashClose.getInt(FLD_PAYMENT_TYPE));

			objCashClose.setAmountRp(objPstCashClose.getdouble(FLD_AMOUNT_RP));

			objCashClose.setAmountUSD(objPstCashClose.getdouble(FLD_AMOUNT_USD));



			return objCashClose;

		}catch(DBException dbe){

			throw dbe;

		}catch(Exception e){

			throw new DBException(new PstCashClosing(0),DBException.UNKNOWN);

		}

	}



    public static long insertExc(CashClosing objCashClose) throws DBException{

            try{

                PstCashClosing objPstCashClose = new PstCashClosing(0);

                objPstCashClose.setLong(FLD_CASH_CASHIER_ID, objCashClose.getCashCashierId());

                objPstCashClose.setInt(FLD_PAYMENT_TYPE, objCashClose.getPayType());

                objPstCashClose.setDouble(FLD_AMOUNT_RP, objCashClose.getAmountRp());

                objPstCashClose.setDouble(FLD_AMOUNT_USD, objCashClose.getAmountUSD());



                objPstCashClose.insert();

                objCashClose.setOID(objPstCashClose.getlong(FLD_CASH_CLOSING_ID));

            }catch(DBException dbe){

                throw dbe;

            }catch(Exception e){

                throw new DBException(new PstCashClosing(0),DBException.UNKNOWN);

            }

            return objCashClose.getOID();

        }

     public static long updateExc(CashClosing objCashClose) throws DBException{

		try{

			if(objCashClose.getOID() != 0){

				PstCashClosing objPstCashClose = new PstCashClosing(0);

                objPstCashClose.setLong(FLD_CASH_CASHIER_ID, objCashClose.getCashCashierId());

                objPstCashClose.setInt(FLD_PAYMENT_TYPE, objCashClose.getPayType());

                objPstCashClose.setDouble(FLD_AMOUNT_RP, objCashClose.getAmountRp());

                objPstCashClose.setDouble(FLD_AMOUNT_USD, objCashClose.getAmountUSD());



				objPstCashClose.update();

				return objCashClose.getOID();



			}

		}catch(DBException dbe){

			throw dbe;

		}catch(Exception e){

			throw new DBException(new PstCashClosing(0),DBException.UNKNOWN);

		}

		return 0;

	}



    public static long deleteExc(long oid) throws DBException{

            try{

                PstCashClosing objPstCashClose = new PstCashClosing(oid);

                objPstCashClose.delete();

            }catch(DBException dbe){

                throw dbe;

            }catch(Exception e){

                throw new DBException(new PstCashClosing(0),DBException.UNKNOWN);

            }

            return oid;

        }





    public static Vector listAll(){

            return list(0, 500, "","");

        }



   public static Vector list(int limitStart,int recordToGet, String whereClause, String order){

            Vector lists = new Vector();

            DBResultSet dbrs = null;

            try {

                String sql = "SELECT * FROM " + TBL_CASH_CLOSING;

                if(whereClause != null && whereClause.length() > 0)

                    sql = sql + " WHERE " + whereClause;

                if(order != null && order.length() > 0)

                    sql = sql + " ORDER BY " + order;

                if(limitStart == 0 && recordToGet == 0)

                    sql = sql + "";

                else

                    sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

                dbrs = DBHandler.execQueryResult(sql);

                ResultSet rs = dbrs.getResultSet();

                while(rs.next()) {

                    CashClosing objCashClose = new CashClosing();

                    resultToObject(rs, objCashClose);

                    lists.add(objCashClose);

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

    public static void resultToObject(ResultSet rs, CashClosing objCashClose){

		try{

			objCashClose.setOID(rs.getLong(PstCashClosing.fieldNames[PstCashClosing.FLD_CASH_CLOSING_ID]));

			objCashClose.setCashCashierId(rs.getLong(PstCashClosing.fieldNames[PstCashClosing.FLD_CASH_CASHIER_ID]));

			objCashClose.setpayType(rs.getInt(PstCashClosing.fieldNames[PstCashClosing.FLD_PAYMENT_TYPE]));

			objCashClose.setAmountRp(rs.getDouble(PstCashClosing.fieldNames[PstCashClosing.FLD_AMOUNT_RP]));

			objCashClose.setAmountUSD(rs.getDouble(PstCashClosing.fieldNames[PstCashClosing.FLD_AMOUNT_USD]));

		}catch(Exception e){ }

	}





}

