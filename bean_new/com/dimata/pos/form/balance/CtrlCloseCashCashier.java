/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.pos.form.balance;

/**
 *
 * @author Ari Wiweka
 * 11/06/2013
 */
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// dimata package
import com.dimata.util.*;
import com.dimata.util.lang.*;

// qdep package
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
//import com.dimata.qdep.db.*;

// project package
import com.dimata.pos.entity.balance.*;
import com.dimata.common.db.*;
import com.dimata.pos.session.masterCashier.SessMasterCashier;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.payment.PaymentSystem;
import com.dimata.common.entity.payment.PstPaymentSystem;
import com.dimata.common.form.location.FrmLocation;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.entity.payment.PstCashPayment;
import com.dimata.pos.entity.payment.PstCashReturn;
import com.dimata.posbo.entity.admin.*;

public class CtrlCloseCashCashier extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "Code Material Sales sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Material Sales Code already exist", "Data incomplete"}
    };
    private int start;
    private String msgString;
    private CashCashier cashCashier;
    private OpeningCashCashier openingCashCashier;
    private PstCashCashier pstCashCashier;
    private FrmCashCashier frmCashCashier;
    //untuk cash_balance
    private Balance balance;
    private PstBalance pstBalance;
    int language = LANGUAGE_FOREIGN;

    public CtrlCloseCashCashier(HttpServletRequest request) {
        msgString = "";
        cashCashier = new CashCashier();
        try {
            pstCashCashier = new PstCashCashier(0);
        } catch (Exception e) {
            ;
        }
        frmCashCashier = new FrmCashCashier(request, cashCashier);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmCashCashier.addError(frmCashCashier.FRM_FIELD_CASH_CASHIER_ID, resultText[language][RSLT_EST_CODE_EXIST]);
                return resultText[language][RSLT_EST_CODE_EXIST];
            default:
                return resultText[language][RSLT_UNKNOWN_ERROR];
        }
    }

    private int getControlMsgId(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                return RSLT_EST_CODE_EXIST;
            default:
                return RSLT_UNKNOWN_ERROR;
        }
    }

    public int getLanguage() {
        return language;
    }

    public void setLanguage(int language) {
        this.language = language;
    }

    public CashCashier getCashCashier() {
        return cashCashier;
    }

    public OpeningCashCashier getOpeningCashCashier() {
        return openingCashCashier;
    }

    public FrmCashCashier getForm() {
        return frmCashCashier;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidCashCashier, HttpServletRequest request, long oidSupervisor) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidCashCashier != 0) {
                    try {
                        cashCashier = PstCashCashier.fetchExc(oidCashCashier);

                    } catch (Exception exc) {
                    }
                }



                frmCashCashier.requestEntityObject(cashCashier);
                cashCashier.setOID(oidCashCashier);
                cashCashier.setSpvCloseOid(oidSupervisor);
                cashCashier.setCloseDate(new Date());

                String supervisorName = "";
                PstAppUser pstAppUser = new PstAppUser();
                AppUser appUser = pstAppUser.fetch(oidSupervisor);
                supervisorName = appUser.getFullName();
                cashCashier.setSpvCloseName(supervisorName);


                if (frmCashCashier.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (cashCashier.getOID() == 0) {
                    try {
                        long oid = pstCashCashier.insertExc(this.cashCashier);


                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {
                    try {


                        long oid = pstCashCashier.updateForClosing(this.cashCashier);
                        
                        String whereTemp =""
                            + " "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='0' "
                            + " AND "+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"'";
                        Vector listTheBalance = PstBalance.list(0, 0, whereTemp, "");
                            
                        for(int j = 0; j<listTheBalance.size();j++){
                            Balance entBalanceTemp = (Balance) listTheBalance.get(j);
                            
                            double cashPayment = 0;
                            double ccPayment = 0;
                            double debitPayment = 0;
                            double bgPayment = 0;
                            double checkPayment = 0;
                            Vector listPaymentSystem = PstPaymentSystem.listAll();
                            
                            if(listPaymentSystem.size() != 0){
                                for(int i=0; i<listPaymentSystem.size();i++){
                                    PaymentSystem paymentSystem = (PaymentSystem) listPaymentSystem.get(i);
                                    double payment = PstCashPayment.getSumListDetailBayar2("CBM."+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
                                            + "AND CP."+PstCashPayment.fieldNames[PstCashPayment.FLD_PAY_TYPE]+"='"+paymentSystem.getOID()+"' "
                                            + "AND CP."+PstCashPayment.fieldNames[PstCashPayment.FLD_CURRENCY_ID]+"='"+entBalanceTemp.getCurrencyOid()+"'"
                                            + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                                            + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
                                            + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                            + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' ");
                                    if(paymentSystem.isBankInfoOut() == false 
                                            && paymentSystem.isCardInfo() == false 
                                            && paymentSystem.isCheckBGInfo() == false 
                                            && paymentSystem.getPaymentType() == 1){
                                        cashPayment += payment;
                                    }else if(paymentSystem.isBankInfoOut() == false 
                                            && paymentSystem.isCardInfo() == true 
                                            && paymentSystem.isCheckBGInfo() == false 
                                            && paymentSystem.getPaymentType() == 0){
                                        ccPayment += payment;
                                    }else if(paymentSystem.isBankInfoOut() == true 
                                            && paymentSystem.isCardInfo() == false 
                                            && paymentSystem.isCheckBGInfo() == false 
                                            && paymentSystem.getPaymentType() == 2){
                                        bgPayment += payment;
                                    }else if(paymentSystem.isBankInfoOut() == false 
                                            && paymentSystem.isCardInfo() == false 
                                            && paymentSystem.isCheckBGInfo() == true 
                                            && paymentSystem.getPaymentType() == 0){
                                        checkPayment += payment;
                                    }else if(paymentSystem.isBankInfoOut() == false 
                                            && paymentSystem.isCardInfo() == false 
                                            && paymentSystem.isCheckBGInfo() == false 
                                            && paymentSystem.getPaymentType() == 2){
                                        debitPayment += payment;
                                    }
                                }
                            }
                            
                            //CHANGE
                            
                            double cashReturnAmount = 0;//PstCustomBillMain.getSummaryCashReturn(cashCashier.getOID());
                            double creditReturnAmount  = 0;//PstCustomBillMain.getSummarySalesCreditReturn(cashCashier.getOID());
                            double change = PstCashReturn.getReturnSummary2(cashCashier.getOID(),entBalanceTemp.getCurrencyOid());
                            double subTotal = cashPayment+ccPayment+debitPayment+bgPayment+checkPayment-change-cashReturnAmount-creditReturnAmount;
                            
                            if (oid != 0) {                           
                                //proses insert cash_balance
            
                                double opening = FRMQueryString.requestDouble(request, "FRM_FIELD_AMOUNT1_"+entBalanceTemp.getCurrencyOid()+"");
                                double balanceValue = FRMQueryString.requestDouble(request, "FRM_FIELD_BALANCE_VALUE_"+entBalanceTemp.getCurrencyOid()+"");
                                double sub = subTotal + opening;
                                //subTotal += opening;
                                Balance balance = new Balance();
                                balance.setCashCashierId(cashCashier.getOID());
                                balance.setBalanceValue(balanceValue);
                                balance.setCurrencyOid(entBalanceTemp.getCurrencyOid());
                                balance.setBalanceDate(new Date());
                                balance.setBalanceType(cashCashier.getType());
                                balance.setShouldValue(sub);
                                oid = pstBalance.insertExc(balance);
                               
                                /*double opening1 = FRMQueryString.requestDouble(request, "FRM_FIELD_AMOUNT1");
                                Balance balance = new Balance();
                                balance.setCashCashierId(cashCashier.getOID());
                                subTotal += opening1;
                                balance.setBalanceValue(cashCashier.getBalanceValue());
                                balance.setCurrencyOid(cashCashier.getCurrencyId());
                                balance.setBalanceDate(new Date());
                                balance.setBalanceType(cashCashier.getType());
                                balance.setShouldValue(subTotal);


                                oid = pstBalance.insertExc(balance);

                                if(cashCashier.getCurrencyId2()!= 0){
                                double opening2 = FRMQueryString.requestDouble(request, "FRM_FIELD_AMOUNT2");
                                double subTotalTemp = cashCashier.getSubTotal2()+opening2;
                                Balance balanceOther = new Balance();
                                balanceOther.setCashCashierId(cashCashier.getOID());

                                balanceOther.setBalanceValue(subTotalTemp);
                                balanceOther.setCurrencyOid(cashCashier.getCurrencyId2());
                                balanceOther.setBalanceDate(new Date());
                                balanceOther.setBalanceType(cashCashier.getType());

                                oid = pstBalance.insertExc(balanceOther);
                                }*/                               
                            }
                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidCashCashier != 0) {
                    try {
                        cashCashier = PstCashCashier.fetchExc(oidCashCashier);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidCashCashier != 0) {
                    try {
                        cashCashier = PstCashCashier.fetchExc(oidCashCashier);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidCashCashier != 0) {
                    try {
                        long oid = 0;
                        if (SessMasterCashier.readyDataToDelete(oidCashCashier)) {
                            oid = PstCashCashier.deleteExc(oidCashCashier);
                        }
                        if (oid != 0) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        } else {
                            cashCashier = PstCashCashier.fetchExc(oidCashCashier);
                            frmCashCashier.addError(FrmCashCashier.FRM_FIELD_CASH_CASHIER_ID, "");
                            msgString = "Hapus data gagal, data sudah terpakai di modul lain."; // FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            default:

        }
        return excCode;
    }
    
    public int actionObject(int cmd, Balance cashBalance, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (cashBalance.getOID()!=0){
                    double cashPayment = 0;
                    double ccPayment = 0;
                    double debitPayment = 0;
                    double bgPayment = 0;
                    double checkPayment = 0;
                    Vector listPaymentSystem = PstPaymentSystem.listAll();
                    if(listPaymentSystem.size() != 0){
                        for(int i=0; i<listPaymentSystem.size();i++){
                            PaymentSystem paymentSystem = (PaymentSystem) listPaymentSystem.get(i);
                            double payment = PstCashPayment.getSumListDetailBayar("CBM."+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashBalance.getCashCashierId()+"' "
                                    + "AND CP."+PstCashPayment.fieldNames[PstCashPayment.FLD_PAY_TYPE]+"='"+paymentSystem.getOID()+"' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' ");
                            if(paymentSystem.isBankInfoOut() == false 
                                    && paymentSystem.isCardInfo() == false 
                                    && paymentSystem.isCheckBGInfo() == false 
                                    && paymentSystem.getPaymentType() == 1){
                                cashPayment += payment;
                            }else if(paymentSystem.isBankInfoOut() == false 
                                    && paymentSystem.isCardInfo() == true 
                                    && paymentSystem.isCheckBGInfo() == false 
                                    && paymentSystem.getPaymentType() == 0){
                                ccPayment += payment;
                            }else if(paymentSystem.isBankInfoOut() == true 
                                    && paymentSystem.isCardInfo() == false 
                                    && paymentSystem.isCheckBGInfo() == false 
                                    && paymentSystem.getPaymentType() == 2){
                                bgPayment += payment;
                            }else if(paymentSystem.isBankInfoOut() == false 
                                    && paymentSystem.isCardInfo() == false 
                                    && paymentSystem.isCheckBGInfo() == true 
                                    && paymentSystem.getPaymentType() == 0){
                                checkPayment += payment;
                            }else if(paymentSystem.isBankInfoOut() == false 
                                    && paymentSystem.isCardInfo() == false 
                                    && paymentSystem.isCheckBGInfo() == false 
                                    && paymentSystem.getPaymentType() == 2){
                                debitPayment += payment;
                            }
                        }
                    }
                    
                    //CHANGE
                    double cashReturnAmount = 0;
                    double creditReturnAmount  = 0;
                    double change = PstCashReturn.getReturnSummary(cashBalance.getCashCashierId());
                    double subTotal = cashPayment+ccPayment+debitPayment+bgPayment+checkPayment-change-cashReturnAmount-creditReturnAmount;
                    
                    cashBalance.setShouldValue(subTotal);
                    long oid =0;
                    try {
                        oid = PstBalance.updateExc(cashBalance);
                        excCode = 0;
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }    
                }
                break;
                
            default:
        }    
        return excCode;
    }
    
    public int actionObject2(int cmd, Balance cashBalance,Balance cashBalance2, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (cashBalance.getOID()!=0){
                    double cashPayment = 0;
                    double ccPayment = 0;
                    double debitPayment = 0;
                    double bgPayment = 0;
                    double checkPayment = 0;
                    Vector listPaymentSystem = PstPaymentSystem.listAll();
                    if(listPaymentSystem.size() != 0){
                        for(int i=0; i<listPaymentSystem.size();i++){
                            PaymentSystem paymentSystem = (PaymentSystem) listPaymentSystem.get(i);
                            double payment = PstCashPayment.getSumListDetailBayar("CBM."+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashBalance.getCashCashierId()+"' "
                                    + "AND CP."+PstCashPayment.fieldNames[PstCashPayment.FLD_PAY_TYPE]+"='"+paymentSystem.getOID()+"' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' ");
                            if(paymentSystem.isBankInfoOut() == false 
                                    && paymentSystem.isCardInfo() == false 
                                    && paymentSystem.isCheckBGInfo() == false 
                                    && paymentSystem.getPaymentType() == 1){
                                cashPayment += payment;
                            }else if(paymentSystem.isBankInfoOut() == false 
                                    && paymentSystem.isCardInfo() == true 
                                    && paymentSystem.isCheckBGInfo() == false 
                                    && paymentSystem.getPaymentType() == 0){
                                ccPayment += payment;
                            }else if(paymentSystem.isBankInfoOut() == true 
                                    && paymentSystem.isCardInfo() == false 
                                    && paymentSystem.isCheckBGInfo() == false 
                                    && paymentSystem.getPaymentType() == 2){
                                bgPayment += payment;
                            }else if(paymentSystem.isBankInfoOut() == false 
                                    && paymentSystem.isCardInfo() == false 
                                    && paymentSystem.isCheckBGInfo() == true 
                                    && paymentSystem.getPaymentType() == 0){
                                checkPayment += payment;
                            }else if(paymentSystem.isBankInfoOut() == false 
                                    && paymentSystem.isCardInfo() == false 
                                    && paymentSystem.isCheckBGInfo() == false 
                                    && paymentSystem.getPaymentType() == 2){
                                debitPayment += payment;
                            }
                        }
                    }
                    
                    //CHANGE
                    double cashReturnAmount = 0;
                    double creditReturnAmount  = 0;
                    double change = PstCashReturn.getReturnSummary(cashBalance.getCashCashierId());
                    double subTotal = cashPayment+ccPayment+debitPayment+bgPayment+checkPayment-change-cashReturnAmount-creditReturnAmount;
                    subTotal = subTotal + cashBalance2.getBalanceValue();
                    cashBalance.setShouldValue(subTotal);
                    long oid =0;
                    try {
                        oid = PstBalance.updateExc(cashBalance);
                        excCode = 0;
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }    
                }
                break;
                
            default:
        }    
        return excCode;
    }
}
