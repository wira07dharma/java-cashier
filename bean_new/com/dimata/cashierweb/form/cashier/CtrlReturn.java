/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.form.cashier;

import com.dimata.cashierweb.entity.cashier.transaction.CustomCashCreditCard;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomCashCreditCard;
import com.dimata.cashierweb.entity.masterdata.Material;
import com.dimata.cashierweb.entity.masterdata.MemberPoin;
import com.dimata.cashierweb.entity.masterdata.MemberReg;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.payment.PaymentSystem;
import com.dimata.common.entity.payment.PstDailyRate;
import com.dimata.common.entity.payment.PstPaymentSystem;
import com.dimata.pos.entity.billing.BillMain;
import com.dimata.pos.entity.billing.Billdetail;
import com.dimata.pos.entity.billing.PstBillDetail;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.form.billing.FrmBillDetail;
import com.dimata.pos.form.billing.FrmBillMain;
import com.dimata.common.db.DBException;
import com.dimata.pos.entity.balance.CashCashier;
import com.dimata.pos.entity.balance.PstCashCashier;
import com.dimata.pos.entity.payment.CashCreditCard;
import com.dimata.pos.entity.payment.CashPayments;
import com.dimata.pos.entity.payment.CashPayments1;
import com.dimata.pos.entity.payment.PstCashCreditCard;
import com.dimata.pos.entity.payment.PstCashPayment;
import com.dimata.pos.entity.payment.PstCashPayment1;
import com.dimata.cashierweb.entity.masterdata.PstMaterial;
import com.dimata.cashierweb.entity.masterdata.PstMemberPoin;
import com.dimata.cashierweb.entity.masterdata.PstMemberReg;
import com.dimata.common.entity.custom.DataCustom;
import com.dimata.common.entity.custom.PstDataCustom;
import com.dimata.common.entity.payment.DailyRate;
import com.dimata.pos.entity.payment.CashReturn;
import com.dimata.pos.entity.payment.PstCashReturn;
import com.dimata.posbo.entity.masterdata.Category;
import com.dimata.posbo.entity.masterdata.PstCategory;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.util.Date;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Ardiadi
 */
public class CtrlReturn extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText = {
        {"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap"},
        {"Succes", "Can not process", "Estimation code exist", "Data incomplete"}
    };
    private int start;
    private String msgString;

    //BILL MAIN
    private BillMain entBillMain;
    private PstBillMain pstBillMain;
    private FrmBillMain frmBillMain;

    //BILL DETAIL
    private Billdetail entBilldetail;
    private PstBillDetail pstBillDetail;
    private FrmBillDetail frmBillDetail;

    int language = LANGUAGE_DEFAULT;

    public CtrlReturn(HttpServletRequest request) {
        msgString = "";
        entBillMain = new BillMain();
        entBilldetail = new Billdetail();

        try {
            pstBillMain = new PstBillMain(0);
            pstBillDetail = new PstBillDetail(0);
        } catch (Exception e) {;
        }

        frmBillMain = new FrmBillMain(request, entBillMain);
        frmBillDetail = new FrmBillDetail(request, entBilldetail);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmBillMain.addError(frmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID, resultText[language][RSLT_EST_CODE_EXIST]);
                this.frmBillDetail.addError(frmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public BillMain getBillMain() {
        return entBillMain;
    }

    public Billdetail getBilldetail() {
        return entBilldetail;
    }

    public FrmBillMain getFormBillMain() {
        return frmBillMain;
    }

    public FrmBillDetail getFormBillDetail() {
        return frmBillDetail;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidReturn, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidReturn != 0) {
                    try {
                        entBillMain = PstBillMain.fetchExc(oidReturn);
                        //entBilldetail = PstBillDetail.fetchExc(oidReturn);
                    } catch (Exception exc) {
                    }
                }

                frmBillMain.requestEntityObject(entBillMain);
                frmBillDetail.requestEntityObject(entBilldetail);

                if (frmBillMain.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (frmBillDetail.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entBillMain.getOID() == 0) {
                    try {
                        long oid = pstBillMain.insertExc(this.entBillMain);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {

                    String dateNow = new Date().toString();
                    try {
                        BillMain billMain = PstBillMain.fetchExc(oidReturn);
                        BillMain billMainUpdate = billMain;
                        Location location = PstLocation.fetchExc(billMain.getLocationId());

                        billMain.setTaxInclude(location.getTaxSvcDefault());
                        billMain.setTaxPercentage(location.getTaxPersen());
                        billMain.setServicePct(location.getServicePersen());
                        billMain.setParentId(oidReturn);

                        int counter = PstCustomBillMain.getCounterReturn(0, 0, "", "") + 1;
                        String zeroLoop = "";
                        for (int i = 1; i <= 3 - String.valueOf(counter).length(); i++) {
                            zeroLoop += "0";
                        }
                        int cashierNumber = PstCustomBillMain.getCashierNumber(billMain.getCashCashierId());
                        String invoiceNumb = "00" + cashierNumber + "." + Formater.formatDate(new Date(), "yyyyMMdd") + "." + zeroLoop + counter;
                        billMain.setInvoiceNo(invoiceNumb);
                        billMain.setInvoiceNumber(invoiceNumb);
                        ////////CASH PAYMENT
                        if (billMain.getDocType() == 0
                                && billMain.getTransctionType() == 0
                                && billMain.getTransactionStatus() == 0
                                && billMain.getStatusInv() == 1) {

                            billMain.setDocType(1);
                            billMain.setTransctionType(0);
                            billMain.setTransactionStatus(0);
                            billMain.setStatusInv(1);

                            ////////CREDIT PAYMENT
                        } else if (billMain.getDocType() == 0
                                && billMain.getTransctionType() == 1
                                && billMain.getTransactionStatus() == 1
                                && billMain.getStatusInv() == 1) {

                            billMain.setDocType(1);
                            billMain.setTransctionType(1);
                            billMain.setTransactionStatus(0);
                            billMain.setStatusInv(1);
                        }
			//PROSES DULU BILL YANG BARU SEBELUM DIADD

                        /////////INSERT BILL MAIN
                        long oid = pstBillMain.insertExc(billMain);

                        /////////UPDATE BILL MAIN
                        //billMainUpdate.setParentId(oid);
                        //long oidUpdate = pstBillMain.updateExc(billMainUpdate);
                        Vector listBillDetail = PstBillDetail.list(0, 0,
                                PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + oidReturn + "'", "");

                        double totalReturn = 0;
                        double totalReturnInclude = 0;
                        double totalDisc = 0;
                        double totalReturnInclude2 = 0;
                        if (listBillDetail.size() != 0) {
                            for (int i = 0; i < listBillDetail.size(); i++) {
                                double qty = FRMQueryString.requestDouble(request, FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] + "_" + i);
                                if (qty > 0) {
                                    Billdetail billdetail = (Billdetail) listBillDetail.get(i);
                                    double totalPrice = billdetail.getTotalPrice() / billdetail.getQty();
                                    double disc = billdetail.getDisc();
                                    
                                    //Insert Debet Member Poin *(Return)
                                    Material mat = new Material();
                                    Category cat = new Category();
                                    MemberReg mem = new MemberReg();
                                    BillMain bm = new BillMain();
                                    mat = PstMaterial.fetchExc(billdetail.getMaterialId());
                                    cat = PstCategory.fetchExc(mat.getCategoryId());
                                    bm = PstBillMain.fetchExc(oid);
                                    mem = PstMemberReg.fetchExc(bm.getCustomerId());
                                    double priceTotal = totalPrice * qty;
                                    int point = (int) Math.floor(priceTotal / cat.getPointPrice());
                                    MemberPoin mp = new MemberPoin();
                                    mp.setCashBillMainId(oid);
                                    mp.setMemberId(mem.getOID());
                                    mp.setDebet(point);
                                    mp.setTransactionDate(new Date()); 
                                    PstMemberPoin.insertExc(mp);
                                    
                                    //Set Bill Detail New Value
                                    billdetail.setBillMainId(oid);
                                    billdetail.setQty(qty);
                                    billdetail.setTotalPrice(totalPrice * qty);
                                    billdetail.setDisc(disc);
                                    billdetail.setParentReturnId(billdetail.getOID());
                                    long oidBilLDetail = pstBillDetail.insertExc(billdetail);
                                    totalReturn += billdetail.getTotalPrice()/*-billdetail.getDiscGlobal()*/;
                                    totalReturnInclude += (billdetail.getTotalPrice()/*-billdetail.getDiscGlobal()*/);
                                    totalReturnInclude2 += (billdetail.getTotalPrice());
                                    totalDisc += billdetail.getDiscGlobal();
                                }
                            }
                        }

                        double hpp = 0;
                        if (location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE) {
                            //hpp = totalReturn/((billMain.getTaxPercentage()+billMain.getServicePct()+100)/100);
                            hpp = totalReturnInclude;
                        } else {
                            hpp = totalReturn;
                        }

                        double serviceValue = hpp * (location.getServicePersen() / 100);
                        double taxValue = hpp * (location.getTaxPersen() / 100);

                        totalReturn += serviceValue + taxValue;
                        billMain.setTaxValue(taxValue);
                        billMain.setServiceValue(serviceValue);
                        if (location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE) {
                            billMain.setAmount(totalReturnInclude2);
                        } else {
                            billMain.setAmount(totalReturnInclude2);
                        }

                        billMain.setDiscount(totalDisc);
                        oid = pstBillMain.updateExc(billMain);
                        double totalPayment = 0;
                        totalPayment = (billMain.getAmount() - billMain.getDiscount() + billMain.getTaxValue() + billMain.getServiceValue());
                        ////////CASH PAYMENT
                        if (billMain.getDocType() == 1
                                && billMain.getTransctionType() == 0
                                && billMain.getTransactionStatus() == 0
                                && billMain.getStatusInv() == 1) {

                            try {
                                CashPayments1 cashPayments = new CashPayments1();
                                CustomCashCreditCard cashCreditCard;
                                Vector listCashPayments = PstCashPayment1.list(0, 0,
                                        "" + PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID] + "='" + oidReturn + "'", "");
                                if (listCashPayments.size() != 0) {
                                    cashPayments = (CashPayments1) listCashPayments.get(0);
                                } else {
                                    cashPayments = new CashPayments1();
                                }

                                Vector listCashCreditCard = PstCustomCashCreditCard.list(0, 0,
                                        "" + PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CASH_PAYMENT_ID] + "='" + cashPayments.getOID() + "'", "");

                                if (listCashCreditCard.size() != 0) {
                                    cashCreditCard = (CustomCashCreditCard) listCashCreditCard.get(0);
                                } else {
                                    cashCreditCard = new CustomCashCreditCard();
                                }
                                PaymentSystem paymentSystem = PstPaymentSystem.fetchExc(cashPayments.getPaymentType());

                                cashPayments.setAmount(totalPayment);
                                cashPayments.setBillMainId(billMain.getOID());
                                cashPayments.setPayDateTime(Formater.reFormatDate(new Date(), "yyyy-MM-dd kk:mm:ss"));
                                long oidCashPayment = PstCashPayment1.insertExc(cashPayments);

                                double ccCharge = (hpp + taxValue + serviceValue) * (paymentSystem.getChargeToCustomerPercent() / 100);
                                cashCreditCard.setBankCost(ccCharge);
                                cashCreditCard.setCashPaymentId(oidCashPayment);
                                long oidCashCreditCard = PstCustomCashCreditCard.insertExc(cashCreditCard);

                                int design = 0;
                                try {
                                    design = Integer.parseInt((String) com.dimata.system.entity.PstSystemProperty.getValueByName("DESIGN_MATERIAL_FOR"));
                                } catch (Exception e) {
                                    design = 0;
                                }

                                if (design != 0) {
                                    double rate = PstDailyRate.getCurrentDailyRateSales(2);
                                    CashCashier cashCashier = new CashCashier();
                                    cashCashier = PstCashCashier.fetchExc(billMain.getCashCashierId());
                                    long appUserId = cashCashier.getAppUserId();
                                    CtrlCustomPayment.insertIntegrationHanoman(billMain.getCustomerId(), 0, 0, appUserId, rate, billMain, request, cashPayments, "1", 1);
                                } else {

                                }

                            } catch (Exception ex) {
                                CashPayments cashPayments = new CashPayments();
                            }

                            ////////CREDIT PAYMENT
                        } else if (billMain.getDocType() == 1
                                && billMain.getTransctionType() == 1
                                && billMain.getTransactionStatus() == 0
                                && billMain.getStatusInv() == 1) {

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
                if (oidReturn != 0) {
                    try {
                        entBillMain = PstBillMain.fetchExc(oidReturn);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidReturn != 0) {
                    try {
                        entBillMain = PstBillMain.fetchExc(oidReturn);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidReturn != 0) {
                    try {
                        long oid = PstBillMain.deleteExc(oidReturn);
                        if (oid != 0) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                        } else {
                            msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
                            excCode = RSLT_FORM_INCOMPLETE;
                        }
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            default:

        }
        return rsCode;
    }

    public int actionExchange(int cmd, long oidBillMain, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;

        try {
            //========== CREATE BILL RETURN ==========

            BillMain billMain = PstBillMain.fetchExc(oidBillMain);
            Location location = PstLocation.fetchExc(billMain.getLocationId());

            BillMain billReturn = PstBillMain.fetchExc(oidBillMain);
            billReturn.setBillDate(new Date());
            billReturn.setNotes("Bill return for invoice " + billMain.getInvoiceNumber());
            billReturn.setTaxInclude(location.getTaxSvcDefault());
            billReturn.setTaxPercentage(location.getTaxPersen());
            billReturn.setServicePct(location.getServicePersen());
            billReturn.setParentId(oidBillMain);

            int cashierNumber = PstCustomBillMain.getCashierNumber(billReturn.getCashCashierId());
            String billNumber = generateBillNumber(cashierNumber);
            billReturn.setInvoiceNo(billNumber);
            billReturn.setInvoiceNumber(billNumber);
			billReturn.setBillStatus(2);

            if (billMain.getDocType() == 0
                    && billMain.getTransctionType() == 0
                    && billMain.getTransactionStatus() == 0
                    && billMain.getStatusInv() == 1) {
                //////// TRANSACTION TYPE CASH
                billReturn.setDocType(1);
                billReturn.setTransctionType(0);
                billReturn.setTransactionStatus(0);
                billReturn.setStatusInv(1);

            } else if (billMain.getDocType() == 0
                    && billMain.getTransctionType() == 1
                    && billMain.getTransactionStatus() == 1
                    && billMain.getStatusInv() == 1) {
                //////// TRANSACTION TYPE CREDIT
                billReturn.setDocType(1);
                billReturn.setTransctionType(1);
                billReturn.setTransactionStatus(0);
                billReturn.setStatusInv(1);
            }
            long oidBillReturn = pstBillMain.insertExc(billReturn);

            //CREATE BILL DETAIL RETURN
            String itemReturnId[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_RETURN_ID");
            String itemReturnQty[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_RETURN_QTY");

            double totalPriceItemReturn = 0;
            if (itemReturnId != null && itemReturnId.length > 0) {
                for (int i = 0; i < itemReturnId.length; i++) {
                    if (itemReturnQty[i].equals("") || itemReturnQty[i].equals("0")) {
                        continue;
                    }
                    String whereBillDetail = PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + " = '" + oidBillMain + "'"
                            + " AND " + PstBillDetail.fieldNames[PstBillDetail.FLD_MATERIAL_ID] + " = '" + itemReturnId[i] + "'";
                    
                    Vector<Billdetail> listBillDetail = PstBillDetail.list(0, 0, whereBillDetail, "");
                    if (!listBillDetail.isEmpty()) {
                        Billdetail billdetail = (Billdetail) listBillDetail.get(0);
                        double newTotalPrice = (billdetail.getItemPrice() - billdetail.getDisc()) * billdetail.getQty();
                        //save as new bill detail
                        billdetail.setBillMainId(oidBillReturn);
                        billdetail.setQty(Double.valueOf(itemReturnQty[i]));
                        billdetail.setTotalPrice(newTotalPrice);
                        billdetail.setParentReturnId(billdetail.getOID());
                        long oidBilldetail = PstBillDetail.insertExc(billdetail);
                        //
                        totalPriceItemReturn += billdetail.getTotalPrice();
                    }
                }
            }
            billReturn.setAmount(totalPriceItemReturn);
            billReturn.setPaidAmount(totalPriceItemReturn);
            pstBillMain.updateExc(billReturn);

            //CREATE PAYMENT SYSTEM FOR BILL RETURN
            long oidPaymentSystemReturn = 0;
            Vector<PaymentSystem> listPaymentReturn = PstPaymentSystem.list(0, 0, PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + " = 3 ", "");
            if (!listPaymentReturn.isEmpty()) {
                oidPaymentSystemReturn = listPaymentReturn.get(0).getOID();
            }

            Vector listDailyRate = PstDailyRate.getCurrentDailyRate();
            DailyRate dailyRate = new DailyRate();
            for (Object listDailyRate1 : listDailyRate) {
                DailyRate dailyRateData = (DailyRate) listDailyRate1;
                if (billReturn.getCurrencyId() == dailyRateData.getCurrencyTypeId()) {
                    dailyRate = dailyRateData;
                }
            }

            CashPayments1 cp = new CashPayments1();
            cp.setBillMainId(oidBillReturn);
            cp.setPaymentType(oidPaymentSystemReturn);
            cp.setAmount(totalPriceItemReturn);
            cp.setPayDateTime(new Date());
            cp.setCurrencyId(billReturn.getCurrencyId());
            cp.setRate(dailyRate.getSellingRate());
            long oidCashPayments = PstCashPayment1.insertExc(cp);

            //CREATE CASH RETURN
            CtrlCustomPayment.insertReturnPayment(cp, billReturn, oidBillReturn, dailyRate);
            
            
            //========== CREATE BILL EXCHANGE ==========
            long cashCashierId = FRMQueryString.requestLong(request, "CASH_CASHIER_ID");
            
            BillMain billExchange = new BillMain();
            String billExchangeNumber = generateBillNumber(cashierNumber);
            billExchange.setInvoiceNumber(billExchangeNumber);
            billExchange.setInvoiceNo(billExchangeNumber);
            billExchange.setDocType(0);
            billExchange.setTransctionType(0);
            billExchange.setTransactionStatus(0);
            billExchange.setStatusInv(1);
            billExchange.setBillDate(new Date());
            billExchange.setNotes("Bill exchange for invoice bill " + billMain.getInvoiceNumber());
            billExchange.setParentId(oidBillMain);
            billExchange.setCashCashierId(cashCashierId);
            billExchange.setLocationId(billMain.getLocationId());
            billExchange.setAppUserId(billMain.getAppUserId());
            billExchange.setShiftId(billMain.getShiftId());
            billExchange.setTaxInclude(location.getTaxSvcDefault());
            billExchange.setTaxPercentage(location.getTaxPersen());
            billExchange.setServicePct(location.getServicePersen());
            billExchange.setBillStatus(2);
            billExchange.setGuestName(billMain.getGuestName());
            billExchange.setCustomerId(billMain.getCustomerId());
            billExchange.setCurrencyId(billMain.getCurrencyId());
            billExchange.setRate(billMain.getRate());
            billExchange.setNegaraId(billMain.getNegaraId());
            billExchange.setGender(billMain.getGender());
            billExchange.setParentId(oidBillMain);
            billExchange.setDateTermOfPayment(new Date());
            billExchange.setSalesCode(billMain.getSalesCode());
            long oidBillExchange = PstBillMain.insertExc(billExchange);

            //CREATE BILL DETAIL EXCHANGE
            String itemExchangeId[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_EXCHANGE_ID");
            String itemExchangeQty[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_EXCHANGE_QTY");
            String itemExchangePrice[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_EXCHANGE_PRICE");
            String itemExchangePromo[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_EXCHANGE_PROMO");
            String itemExchangeDiscPct[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_EXCHANGE_DISC_PCT");
            String itemExchangeDiscVal[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_EXCHANGE_DISC_VAL");
            String itemExchangeTotalPrice[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_EXCHANGE_TOTAL_PRICE");
            
            double totalPriceItemExchange = 0;
            if (itemExchangeId != null && itemExchangeId.length > 0) {
                for (int i = 0; i < itemExchangeId.length; i++) {
                    Material m = PstMaterial.fetchExc(Long.valueOf(itemExchangeId[i]));
                    Billdetail billdetail = new Billdetail();
                    billdetail.setBillMainId(oidBillExchange);
                    billdetail.setMaterialId(m.getOID());
                    billdetail.setSku(m.getSku());
                    billdetail.setItemName(m.getName());
                    billdetail.setQty(Double.valueOf(itemExchangeQty[i]));
                    billdetail.setUnitId(m.getDefaultStockUnitId());
                    billdetail.setItemPrice(Double.valueOf(itemExchangePrice[i]));
                    billdetail.setDiscPct(Double.valueOf(itemExchangeDiscPct[i]));
                    billdetail.setDisc(Double.valueOf(itemExchangeDiscVal[i]));
                    billdetail.setTotalPrice(Double.valueOf(itemExchangeTotalPrice[i]));
                    long oidBilldetail = PstBillDetail.insertExc(billdetail);
                    
                    //CREATE DATA CUSTOM FOR PROMOTION
                    if (!itemExchangePromo[i].equals("") && !itemExchangePromo[i].equals("0")) {
                        DataCustom dataCustom = new DataCustom();
                        dataCustom.setDataName("bill_detail_map_type");
                        dataCustom.setDataValue(""+itemExchangePromo[i]);
                        dataCustom.setLink("5");
                        dataCustom.setOwnerId(oidBilldetail);
                        PstDataCustom.insertExc(dataCustom);
                    }
                    //
                    totalPriceItemExchange += billdetail.getTotalPrice();
                }
            }
            billExchange.setAmount(totalPriceItemExchange);
            billExchange.setPaidAmount(totalPriceItemExchange);
            PstBillMain.updateExc(billExchange);
            this.entBillMain = billExchange;

            //CREATE PAYMENT SYSTEM FOR BILL EXCHANGE
            String paymentSystemId[] = FRMQueryString.requestStringValues(request, "FRM_PAYMENT_SYSTEM_ID");
            String paymentValue[] = FRMQueryString.requestStringValues(request, "FRM_PAYMENT_VALUE");
            
            double totalPaymentExchange = 0;
            if (paymentSystemId != null && paymentSystemId.length > 0) {
                for (int i = 0; i < paymentSystemId.length; i++) {
                    CashPayments1 cashPaymentExchange = new CashPayments1();
                    cashPaymentExchange.setBillMainId(oidBillExchange);
                    cashPaymentExchange.setPaymentType(Long.valueOf(paymentSystemId[i]));
                    cashPaymentExchange.setAmount(Double.valueOf(paymentValue[i]));
                    cashPaymentExchange.setPayDateTime(new Date());
                    cashPaymentExchange.setCurrencyId(billExchange.getCurrencyId());
                    cashPaymentExchange.setRate(dailyRate.getSellingRate());
                    long oidCashPayments1 = PstCashPayment1.insertExc(cashPaymentExchange);
                    
                    //save credit/debit card info
                    int err = savePaymentInfo(request, cashPaymentExchange, i);
                    
                    totalPaymentExchange += Double.valueOf(paymentValue[i]);
                }
            }
            
            //CREATE CASH RETURN PAYMENT EXCHANGE
            CashReturn cashReturn = new CashReturn();
            cashReturn.setBillMainId(oidBillExchange);
            cashReturn.setCurrencyId(billExchange.getCurrencyId());
            cashReturn.setRate(billExchange.getRate());
            cashReturn.setAmount(totalPaymentExchange - billExchange.getAmount());
            long oidCashReturn = PstCashReturn.insertExc(cashReturn);
            
            msgString = "Return exchange succeed";
        } catch (DBException dbexc) {
            excCode = dbexc.getErrorCode();
            msgString = getSystemMessage(excCode);
        } catch (NumberFormatException exc) {
            msgString = exc.getMessage();
        }
        return excCode;
    }

    public String generateBillNumber(int cashierNumber) {
        int counter = 1 + PstCustomBillMain.getCounterReturn(0, 0, "", "");
        String invoiceNumb = "";
        boolean ok = false;
        while (ok == false) {
            String zeroLoop = "";
            for (int i = 1; i <= 3 - String.valueOf(counter).length(); i++) {
                zeroLoop += "0";
            }
            invoiceNumb = "00" + cashierNumber + "." + Formater.formatDate(new Date(), "yyyyMMdd") + "." + zeroLoop + counter;
            String whereCheck = PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NUMBER] + " = '" + invoiceNumb + "'"
                    + " OR " + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " = '" + invoiceNumb + "'";
            int check = PstBillMain.getCount(whereCheck);
            if (check == 0) {
                ok = true;
            } else {
                counter += 1;
            }
        }
        return invoiceNumb;
    }
    
    public int savePaymentInfo(HttpServletRequest request, CashPayments1 cashPayments1, int index) {
        String cardName[] = FRMQueryString.requestStringValues(request, "FRM_PAYMENT_CARD_NAME");
        String cardNumber[] = FRMQueryString.requestStringValues(request, "FRM_PAYMENT_CARD_NUMBER");
        String bank[] = FRMQueryString.requestStringValues(request, "FRM_PAYMENT_BANK_NAME");
        String expiredDate[] = FRMQueryString.requestStringValues(request, "FRM_PAYMENT_EXPIRED_DATE");

        try {
            CustomCashCreditCard cashCreditCard = new CustomCashCreditCard();
            //cek payment type
            PaymentSystem paymentSystem = PstPaymentSystem.fetchExc(cashPayments1.getPaymentType());
            if (paymentSystem.isCardInfo() == true
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false
                    && paymentSystem.getPaymentType() == 0) {
                
                //payment type credit card
                cashCreditCard.setCashPaymentId(cashPayments1.getOID());
                cashCreditCard.setCcName(cardName[index]);
                cashCreditCard.setCcNumber(cardNumber[index]);
                cashCreditCard.setDebitBankName(bank[index]);
                cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                cashCreditCard.setRate(cashPayments1.getRate());
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate[index], "yyyy-MM-dd"));
                long oid = PstCustomCashCreditCard.insertExc(cashCreditCard);
                
                System.out.println("");

            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false
                    && paymentSystem.getPaymentType() == 2) {

                //payment type debit card
                cashCreditCard.setCashPaymentId(cashPayments1.getOID());
                cashCreditCard.setDebitCardName(cardName[index]);
                cashCreditCard.setCcNumber(cardNumber[index]);
                cashCreditCard.setDebitBankName(bank[index]);
                cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                cashCreditCard.setRate(cashPayments1.getRate());
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate[index], "yyyy-MM-dd"));
                long oid = PstCustomCashCreditCard.insertExc(cashCreditCard);
                
                System.out.println("");
            }
        } catch (Exception e) {
            return 1;
        }
        return 0;
    }

}
