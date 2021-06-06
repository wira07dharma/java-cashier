/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.entity.cashier.transaction;

import com.dimata.common.db.DBException;
import com.dimata.common.db.DBHandler;
import com.dimata.common.db.DBResultSet;
import com.dimata.common.db.I_DBInterface;
import com.dimata.common.db.I_DBType;
import com.dimata.qdep.entity.Entity;
import com.dimata.qdep.entity.I_PersintentExc;
import com.dimata.util.Command;
import com.dimata.util.lang.I_Language;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author Ardiadi
 */
public class PstCustomCashCreditCard extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

   public static final String TBL_CUSTOMCASHCREDITCARD = "cash_credit_card";
   public static final int FLD_CC_ID = 0;
   public static final int FLD_CASH_PAYMENT_ID = 1;
   public static final int FLD_CC_NAME = 2;
   public static final int FLD_CC_NUMBER = 3;
   public static final int FLD_EXPIRED_DATE = 4;
   public static final int FLD_HOLDER_NAME = 5;
   public static final int FLD_DEBIT_BANK_NAME = 6;
   public static final int FLD_DEBIT_CARD_NAME = 7;
   public static final int FLD_CHEQUE_ACCOUNT_NAME = 8;
   public static final int FLD_CHEQUE_DUE_DATE = 9;
   public static final int FLD_CURRENCY_ID = 10;
   public static final int FLD_RATE = 11;
   public static final int FLD_AMOUNT = 12;
   public static final int FLD_CHEQUE_BANK = 13;
   public static final int FLD_BANK_COST = 14;

   public static String[] fieldNames = {
      "CC_ID",
      "CASH_PAYMENT_ID",
      "CC_NAME",
      "CC_NUMBER",
      "EXPIRED_DATE",
      "HOLDER_NAME",
      "DEBIT_BANK_NAME",
      "DEBIT_CARD_NAME",
      "CHEQUE_ACCOUNT_NAME",
      "CHEQUE_DUE_DATE",
      "CURRENCY_ID",
      "RATE",
      "AMOUNT",
      "CHEQUE_BANK",
      "BANK_COST"
   };

   public static int[] fieldTypes = {
      TYPE_LONG+TYPE_PK+TYPE_ID,
      TYPE_LONG,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_DATE,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_STRING,
      TYPE_DATE,
      TYPE_LONG,
      TYPE_FLOAT,
      TYPE_FLOAT,
      TYPE_STRING,
      TYPE_FLOAT
   };

   public PstCustomCashCreditCard() {
   }

   public PstCustomCashCreditCard(int i) throws DBException {
      super(new PstCustomCashCreditCard());
   }

   public PstCustomCashCreditCard(String sOid) throws DBException {
      super(new PstCustomCashCreditCard(0));
      if(!locate(sOid))
         throw new DBException(this, DBException.RECORD_NOT_FOUND);
      else
         return;
   }

   public PstCustomCashCreditCard(long lOid) throws DBException {
      super(new PstCustomCashCreditCard(0));
      String sOid = "0";
      try {
         sOid = String.valueOf(lOid);
      }catch(Exception e) {
         throw new DBException(this, DBException.RECORD_NOT_FOUND);
      }
      if(!locate(sOid))
         throw new DBException(this, DBException.RECORD_NOT_FOUND);
      else
         return;
   }

   public int getFieldSize() {
      return fieldNames.length;
   }

   public String getTableName() {
      return TBL_CUSTOMCASHCREDITCARD;
   }

   public String[] getFieldNames() {
      return fieldNames;
   }

   public int[] getFieldTypes() {
      return fieldTypes;
   }

   public String getPersistentName() {
      return new PstCustomCashCreditCard().getClass().getName();
   }

   public static CustomCashCreditCard fetchExc(long oid) throws DBException {
      try {
         CustomCashCreditCard entCustomCashCreditCard = new CustomCashCreditCard();
         PstCustomCashCreditCard pstCustomCashCreditCard = new PstCustomCashCreditCard(oid);
         entCustomCashCreditCard.setOID(oid);
         entCustomCashCreditCard.setCashPaymentId(pstCustomCashCreditCard.getlong(FLD_CASH_PAYMENT_ID));
         entCustomCashCreditCard.setCcName(pstCustomCashCreditCard.getString(FLD_CC_NAME));
         entCustomCashCreditCard.setCcNumber(pstCustomCashCreditCard.getString(FLD_CC_NUMBER));
         entCustomCashCreditCard.setExpiredDate(pstCustomCashCreditCard.getDate(FLD_EXPIRED_DATE));
         entCustomCashCreditCard.setHolderName(pstCustomCashCreditCard.getString(FLD_HOLDER_NAME));
         entCustomCashCreditCard.setDebitBankName(pstCustomCashCreditCard.getString(FLD_DEBIT_BANK_NAME));
         entCustomCashCreditCard.setDebitCardName(pstCustomCashCreditCard.getString(FLD_DEBIT_CARD_NAME));
         entCustomCashCreditCard.setChequeAccountName(pstCustomCashCreditCard.getString(FLD_CHEQUE_ACCOUNT_NAME));
         entCustomCashCreditCard.setChequeDueDate(pstCustomCashCreditCard.getDate(FLD_CHEQUE_DUE_DATE));
         entCustomCashCreditCard.setCurrencyId(pstCustomCashCreditCard.getlong(FLD_CURRENCY_ID));
         entCustomCashCreditCard.setRate(pstCustomCashCreditCard.getdouble(FLD_RATE));
         entCustomCashCreditCard.setAmount(pstCustomCashCreditCard.getdouble(FLD_AMOUNT));
         entCustomCashCreditCard.setChequeBank(pstCustomCashCreditCard.getString(FLD_CHEQUE_BANK));
         entCustomCashCreditCard.setBankCost(pstCustomCashCreditCard.getdouble(FLD_BANK_COST));
         return entCustomCashCreditCard;
      } catch (DBException dbe) {
         throw dbe;
      } catch (Exception e) {
         throw new DBException(new PstCustomCashCreditCard(0), DBException.UNKNOWN);
      }
   }

   public long fetchExc(Entity entity) throws Exception {
      CustomCashCreditCard entCustomCashCreditCard = fetchExc(entity.getOID());
      entity = (Entity) entCustomCashCreditCard;
      return entCustomCashCreditCard.getOID();
   }

   public static synchronized long updateExc(CustomCashCreditCard entCustomCashCreditCard) throws DBException {
      try {
         if (entCustomCashCreditCard.getOID() != 0) {
            PstCustomCashCreditCard pstCustomCashCreditCard = new PstCustomCashCreditCard(entCustomCashCreditCard.getOID());
            pstCustomCashCreditCard.setLong(FLD_CASH_PAYMENT_ID, entCustomCashCreditCard.getCashPaymentId());
            pstCustomCashCreditCard.setString(FLD_CC_NAME, entCustomCashCreditCard.getCcName());
            pstCustomCashCreditCard.setString(FLD_CC_NUMBER, entCustomCashCreditCard.getCcNumber());
            pstCustomCashCreditCard.setDate(FLD_EXPIRED_DATE, entCustomCashCreditCard.getExpiredDate());
            pstCustomCashCreditCard.setString(FLD_HOLDER_NAME, entCustomCashCreditCard.getHolderName());
            pstCustomCashCreditCard.setString(FLD_DEBIT_BANK_NAME, entCustomCashCreditCard.getDebitBankName());
            pstCustomCashCreditCard.setString(FLD_DEBIT_CARD_NAME, entCustomCashCreditCard.getDebitCardName());
            pstCustomCashCreditCard.setString(FLD_CHEQUE_ACCOUNT_NAME, entCustomCashCreditCard.getChequeAccountName());
            pstCustomCashCreditCard.setDate(FLD_CHEQUE_DUE_DATE, entCustomCashCreditCard.getChequeDueDate());
            pstCustomCashCreditCard.setLong(FLD_CURRENCY_ID, entCustomCashCreditCard.getCurrencyId());
            pstCustomCashCreditCard.setDouble(FLD_RATE, entCustomCashCreditCard.getRate());
            pstCustomCashCreditCard.setDouble(FLD_AMOUNT, entCustomCashCreditCard.getAmount());
            pstCustomCashCreditCard.setString(FLD_CHEQUE_BANK, entCustomCashCreditCard.getChequeBank());
            pstCustomCashCreditCard.setDouble(FLD_BANK_COST, entCustomCashCreditCard.getBankCost());
            pstCustomCashCreditCard.update();
            return entCustomCashCreditCard.getOID();
         }
      } catch (DBException dbe) {
         throw dbe;
      } catch (Exception e) {
         throw new DBException(new PstCustomCashCreditCard(0), DBException.UNKNOWN);
      }
      return 0;
   }

   public long updateExc(Entity entity) throws Exception {
      return updateExc((CustomCashCreditCard) entity);
   }

   public static synchronized long deleteExc(long oid) throws DBException {
   try {
   PstCustomCashCreditCard pstCustomCashCreditCard = new PstCustomCashCreditCard(oid);
   pstCustomCashCreditCard.delete();
   } catch (DBException dbe) {
   throw dbe;
   } catch (Exception e) {
   throw new DBException(new PstCustomCashCreditCard(0), DBException.UNKNOWN);
   }
   return oid;
   }

   public long deleteExc(Entity entity) throws Exception {
   if (entity == null) {   throw new DBException(this, DBException.RECORD_NOT_FOUND);
   }   return deleteExc(entity.getOID());
   }

   public static synchronized long insertExc(CustomCashCreditCard entCustomCashCreditCard) throws DBException
   {
   try {
	PstCustomCashCreditCard pstCustomCashCreditCard = new PstCustomCashCreditCard(0);
	pstCustomCashCreditCard.setLong(FLD_CASH_PAYMENT_ID, entCustomCashCreditCard.getCashPaymentId());
	pstCustomCashCreditCard.setString(FLD_CC_NAME, entCustomCashCreditCard.getCcName());
	pstCustomCashCreditCard.setString(FLD_CC_NUMBER, entCustomCashCreditCard.getCcNumber());
	pstCustomCashCreditCard.setDate(FLD_EXPIRED_DATE, entCustomCashCreditCard.getExpiredDate());
	pstCustomCashCreditCard.setString(FLD_HOLDER_NAME, entCustomCashCreditCard.getHolderName());
	pstCustomCashCreditCard.setString(FLD_DEBIT_BANK_NAME, entCustomCashCreditCard.getDebitBankName());
	pstCustomCashCreditCard.setString(FLD_DEBIT_CARD_NAME, entCustomCashCreditCard.getDebitCardName());
	pstCustomCashCreditCard.setString(FLD_CHEQUE_ACCOUNT_NAME, entCustomCashCreditCard.getChequeAccountName());
	pstCustomCashCreditCard.setDate(FLD_CHEQUE_DUE_DATE, entCustomCashCreditCard.getChequeDueDate());
	pstCustomCashCreditCard.setLong(FLD_CURRENCY_ID, entCustomCashCreditCard.getCurrencyId());
	pstCustomCashCreditCard.setDouble(FLD_RATE, entCustomCashCreditCard.getRate());
	pstCustomCashCreditCard.setDouble(FLD_AMOUNT, entCustomCashCreditCard.getAmount());
	pstCustomCashCreditCard.setString(FLD_CHEQUE_BANK, entCustomCashCreditCard.getChequeBank());
	pstCustomCashCreditCard.setDouble(FLD_BANK_COST, entCustomCashCreditCard.getBankCost());
	pstCustomCashCreditCard.insert();
	entCustomCashCreditCard.setOID(pstCustomCashCreditCard.getlong(FLD_CC_ID));
   } catch (DBException dbe) {
	throw dbe;
   } catch (Exception e) {
	throw new DBException(new PstCustomCashCreditCard(0), DBException.UNKNOWN);
   }
	return entCustomCashCreditCard.getOID();
   }
   public long insertExc(Entity entity) throws Exception {
	return insertExc((CustomCashCreditCard) entity);
   }
   public static void resultToObject(ResultSet rs, CustomCashCreditCard entCustomCashCreditCard) {
	try {
	      entCustomCashCreditCard.setOID(rs.getLong(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CC_ID]));
		 entCustomCashCreditCard.setCashPaymentId(rs.getLong(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CASH_PAYMENT_ID]));
		 entCustomCashCreditCard.setCcName(rs.getString(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CC_NAME]));
		 entCustomCashCreditCard.setCcNumber(rs.getString(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CC_NUMBER]));
		 entCustomCashCreditCard.setExpiredDate(rs.getDate(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_EXPIRED_DATE]));
		 entCustomCashCreditCard.setHolderName(rs.getString(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_HOLDER_NAME]));
		 entCustomCashCreditCard.setDebitBankName(rs.getString(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_DEBIT_BANK_NAME]));
		 entCustomCashCreditCard.setDebitCardName(rs.getString(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_DEBIT_CARD_NAME]));
		 entCustomCashCreditCard.setChequeAccountName(rs.getString(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CHEQUE_ACCOUNT_NAME]));
		 entCustomCashCreditCard.setChequeDueDate(rs.getDate(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CHEQUE_DUE_DATE]));
		 entCustomCashCreditCard.setCurrencyId(rs.getLong(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CURRENCY_ID]));
		 entCustomCashCreditCard.setRate(rs.getDouble(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_RATE]));
		 entCustomCashCreditCard.setAmount(rs.getDouble(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_AMOUNT]));
		 entCustomCashCreditCard.setChequeBank(rs.getString(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CHEQUE_BANK]));
		 entCustomCashCreditCard.setBankCost(rs.getDouble(PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_BANK_COST]));
	} catch (Exception e) {
	}
   }
    public static Vector listAll() {
	return list(0, 500, "", "");
    }

    public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
    Vector lists = new Vector();
    DBResultSet dbrs = null;
    try {
    String sql = "SELECT * FROM " + TBL_CUSTOMCASHCREDITCARD;
    if (whereClause != null && whereClause.length() > 0) {
    sql = sql + " WHERE " + whereClause;
    }
    if (order != null && order.length() > 0) {
    sql = sql + " ORDER BY " + order;
    }
    if (limitStart == 0 && recordToGet == 0) {
    sql = sql + "";
    } else {
    sql = sql + " LIMIT " + limitStart + "," + recordToGet;
    }
    dbrs = DBHandler.execQueryResult(sql);
    ResultSet rs = dbrs.getResultSet();
    while (rs.next()) {
    CustomCashCreditCard entCustomCashCreditCard = new CustomCashCreditCard();
    resultToObject(rs, entCustomCashCreditCard);
    lists.add(entCustomCashCreditCard);
    }
    rs.close();
    return lists;
    } catch (Exception e) {
    System.out.println(e);
    } finally {
    DBResultSet.close(dbrs);
    }
    return new Vector();
    }

    public static boolean checkOID(long entCustomCashCreditCardId) {
    DBResultSet dbrs = null;
    boolean result = false;
    try {
    String sql = "SELECT * FROM " + TBL_CUSTOMCASHCREDITCARD + " WHERE "
    + PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CC_ID] + " = " + entCustomCashCreditCardId;
    dbrs = DBHandler.execQueryResult(sql);
    ResultSet rs = dbrs.getResultSet();
    while (rs.next()) {
    result = true;
    }
    rs.close();
    } catch (Exception e) {
    System.out.println("err : " + e.toString());
    } finally {
    DBResultSet.close(dbrs);
    return result;
    }
    }

    public static int getCount(String whereClause) {
    DBResultSet dbrs = null;
    try {
    String sql = "SELECT COUNT(" + PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CC_ID] + ") FROM " + TBL_CUSTOMCASHCREDITCARD;
    if (whereClause != null && whereClause.length() > 0) {
    sql = sql + " WHERE " + whereClause;
    }
    dbrs = DBHandler.execQueryResult(sql);
    ResultSet rs = dbrs.getResultSet();
    int count = 0;
    while (rs.next()) {
    count = rs.getInt(1);
    }
    rs.close();
    return count;
    } catch (Exception e) {
    return 0;
    } finally {
    DBResultSet.close(dbrs);
    }
    }

    public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
    int size = getCount(whereClause);
    int start = 0;
    boolean found = false;
    for (int i = 0; (i < size) && !found; i = i + recordToGet) {
    Vector list = list(i, recordToGet, whereClause, orderClause);
    start = i;
    if (list.size() > 0) {
    for (int ls = 0; ls < list.size(); ls++) {
    CustomCashCreditCard entCustomCashCreditCard = (CustomCashCreditCard) list.get(ls);
    if (oid == entCustomCashCreditCard.getOID()) {
    found = true;
    }
    }
    }
    }
    if ((start >= size) && (size > 0)) {
    start = start - recordToGet;
    }
    return start;
    }

    public static int findLimitCommand(int start, int recordToGet, int vectSize) {
    int cmd = Command.LIST;
    int mdl = vectSize % recordToGet;
    vectSize = vectSize + (recordToGet - mdl);
    if (start == 0) {
    cmd = Command.FIRST;
    } else {
    if (start == (vectSize - recordToGet)) {
    cmd = Command.LAST;
    } else {
    start = start + recordToGet;
    if (start <= (vectSize - recordToGet)) {
    cmd = Command.NEXT;
    System.out.println("next.......................");
    } else {
    start = start - recordToGet;
    if (start > 0) {
    cmd = Command.PREV;
    System.out.println("prev.......................");
    }
    }
    }
    }
    return cmd;
    }
}
