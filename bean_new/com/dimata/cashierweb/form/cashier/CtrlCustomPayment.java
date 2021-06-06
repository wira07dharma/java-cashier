/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.form.cashier;

import com.dimata.cashierweb.ajax.cashier.SignatureImage;
import com.dimata.cashierweb.ajax.cashier.TransactionCashierHandler;
import com.dimata.cashierweb.entity.admin.AppUser;
import com.dimata.cashierweb.entity.admin.PstAppUser;
import com.dimata.cashierweb.entity.cashier.assigndiscount.AssignDiscount;
import com.dimata.cashierweb.entity.cashier.assigndiscount.PstAssignDiscount;
import com.dimata.cashierweb.entity.cashier.transaction.BillMainJSON;
import com.dimata.cashierweb.entity.cashier.transaction.CustomCashCreditCard;
import com.dimata.cashierweb.entity.cashier.transaction.PaymentRec;
import com.dimata.cashierweb.entity.cashier.transaction.PaymentRecSplit;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomCashCreditCard;
import com.dimata.cashierweb.entity.cashier.transaction.PstPaymentRec;
import com.dimata.cashierweb.entity.cashier.transaction.PstPaymentRecSplit;
import com.dimata.cashierweb.entity.cashier.transaction.PstReservation;
import com.dimata.cashierweb.entity.cashier.transaction.Reservation;
import com.dimata.cashierweb.entity.masterdata.Material;
import com.dimata.cashierweb.entity.masterdata.MemberReg;
import com.dimata.cashierweb.entity.masterdata.PstMaterial;
import com.dimata.cashierweb.entity.masterdata.PstMemberReg;
import com.dimata.cashierweb.entity.masterdata.PstMemberRegistrationHistory;
import com.dimata.cashierweb.entity.masterdata.PstTableRoom;
import com.dimata.cashierweb.entity.masterdata.TableRoom;
import com.dimata.common.entity.contact.ContactClass;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactClass;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.payment.DailyRate;
import com.dimata.common.entity.payment.PaymentSystem;
import com.dimata.common.entity.payment.PstDailyRate;
import com.dimata.common.entity.payment.PstPaymentSystem;
import com.dimata.common.entity.payment.PstStandartRate;
import com.dimata.hanoman.entity.masterdata.ConsumePackage;
import com.dimata.hanoman.entity.masterdata.Contact;
import com.dimata.hanoman.entity.masterdata.PstConsumePackage;
import com.dimata.hanoman.entity.masterdata.PstContact;
import com.dimata.common.db.DBException;
import com.dimata.pos.entity.billing.BillMain;
import com.dimata.pos.entity.billing.Billdetail;
import com.dimata.pos.entity.billing.PstBillDetail;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.entity.payment.CashCreditCard;
import com.dimata.pos.entity.payment.CashCreditPayments;
import com.dimata.pos.entity.payment.CashCreditPaymentsDinamis;
import com.dimata.pos.entity.payment.CashPayments1;
import com.dimata.pos.entity.payment.CashReturn;
import com.dimata.pos.entity.payment.PstCashCreditCard;
import com.dimata.pos.entity.payment.PstCashPayment1;
import com.dimata.pos.entity.payment.PstCashReturn;
import com.dimata.pos.form.billing.FrmBillMain;
import com.dimata.pos.form.payment.FrmCashCreditCard;
import com.dimata.pos.form.payment.FrmCashPayment;
import com.dimata.posbo.entity.masterdata.MaterialComposit;
import com.dimata.posbo.entity.masterdata.PstMaterialComposit;
import com.dimata.posbo.entity.masterdata.PstVoucher;
import com.dimata.posbo.entity.masterdata.Voucher;
import com.dimata.posbo.entity.warehouse.MatCosting;
import com.dimata.posbo.entity.warehouse.MatCostingItem;
import com.dimata.posbo.entity.warehouse.PstMatCosting;
import com.dimata.posbo.entity.warehouse.PstMatCostingItem;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.system.entity.PstSystemProperty;
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
public class CtrlCustomPayment extends Control implements I_Language {

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
    private CashPayments1 cashPayments;
    private PstCashPayment1 pstCashPayment;
    private CashReturn cashReturn;
    private PstCashReturn pstCashReturn;
    private FrmCashPayment frmCashPayment;
    private FrmBillMain frmBillMain;
    private BillMain billMain;
    private PstBillMain pstBillMain;
    int language = LANGUAGE_DEFAULT;
    String useForRaditya = PstSystemProperty.getValueByName("USE_FOR_RADITYA"); 

    public CtrlCustomPayment(HttpServletRequest request) {
        msgString = "";
        cashPayments = new CashPayments1();
        cashReturn = new CashReturn();
        billMain = new BillMain();

        try {
            pstCashPayment = new PstCashPayment1(0);
        } catch (Exception e) {;
        }
        frmCashPayment = new FrmCashPayment(request, cashPayments);
        frmBillMain = new FrmBillMain(request, billMain);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmCashPayment.addError(frmCashPayment.FRM_FIELD_CASH_PAYMENT_ID, resultText[language][RSLT_EST_CODE_EXIST]);
                this.frmBillMain.addError(frmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public CashPayments1 getCustomPayment() {
        return cashPayments;
    }

    public FrmCashPayment getForm() {
        return frmCashPayment;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidCustomPayment, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;

        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidCustomPayment != 0) {
                    try {
                        cashPayments = PstCashPayment1.fetchExc(oidCustomPayment);
                    } catch (Exception exc) {
                    }
                }

                frmCashPayment.requestEntityObject(cashPayments);
                frmBillMain.requestEntityObject(billMain);

                if (frmCashPayment.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                long appUserIdGet = FRMQueryString.requestLong(request, "FRM_FIELD_APP_USER_ID");
                double discGlobal = FRMQueryString.requestDouble(request, "FRM_FIELD_DISC_GLOBAL");
                double discAssign = checkDiscount(appUserIdGet, discGlobal);

                if (cashPayments.getOID() == 0 && discAssign == 0) {
                    try {

                        long newCustomerOID = 0;

                        //LONG DATA
                        long cashCashier = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]);
                        long paymentType = FRMQueryString.requestLong(request, FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE]);
                        long appUserId = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_APP_USER_ID]);
                        long shiftId = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIFT_ID]);
                        long creditMemberId = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID]);
                        try {
                            newCustomerOID = FRMQueryString.requestLong(request, "ctIdCustomer");
                            if (newCustomerOID == 0) {
                                newCustomerOID = creditMemberId;
                            }
                        } catch (Exception e) {
                        }

                        long reservationId = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_RESERVATION_ID]);

                        //INT DATA
                        int cashierNumber = PstCustomBillMain.getCashierNumber(cashCashier);
                        int taxInc = FRMQueryString.requestInt(request, BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC]);
                        int transactionType = FRMQueryString.requestInt(request, "transaction_type");
                        int member = FRMQueryString.requestInt(request, "member");

                        //DOUBLE DATA
                        double amountCash = FRMQueryString.requestDouble(request, FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]);
                        double balance = FRMQueryString.requestDouble(request, "balance");
                        double amountBalance = FRMQueryString.requestDouble(request, BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE]);
                        double ccPct = FRMQueryString.requestDouble(request, "creditcardchargepercent");
                        double customBalance = FRMQueryString.requestDouble(request, BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE]);

                        double totalExpanse = PstBillDetail.getTotalExpense(cashPayments.getBillMainId());
                        amountBalance = amountBalance - totalExpanse;
                        //STRING DATA
                        String oidVoucher = FRMQueryString.requestString(request, "VOUCHER_CODE");
                        String memberName = FRMQueryString.requestString(request, "CUSTOMER_NAME");
                        String remarkFoc = FRMQueryString.requestString(request, "remarkFoc");
                        String customerType = FRMQueryString.requestString(request, "member");
                        String path = FRMQueryString.requestString(request, "signaturelokasi");
                        String imgString = FRMQueryString.requestString(request, "IMAGE_STRING");
                        String[] imgStringSplit = imgString.split(",");
                        String isPackage = FRMQueryString.requestString(request, "transactionPackage");
                        if (imgStringSplit.length != 0) {
                            try {
                                imgString = imgStringSplit[1];
                            } catch (Exception ex) {
                                imgString = "";
                            }

                        }

                        //UPDATE BILL DETAIL JIKA VOUCHER PAYMENT
                        PaymentSystem payemSystemTemp = new PaymentSystem();
                        try {
                            payemSystemTemp = PstPaymentSystem.fetchExc(cashPayments.getPaymentType());
                        } catch (Exception e) {
                        }

                        //PROSES PENGECEKAAN APAKAH AMOUNT BALANCE MASIH LEBIH KECIL DARI TOTAL VOUCHER
                        Voucher voucherTemp = new Voucher();
                        Vector listVectorVoucher = PstVoucher.list(0, 0, " " + PstVoucher.fieldNames[PstVoucher.FLD_VOUCHERNO] + "='" + oidVoucher + "'", "");
                        if (listVectorVoucher.size() > 0) {
                            voucherTemp = (Voucher) listVectorVoucher.get(0);
                        }

                        int countMultiPaymentTemp = 0;
                        try {
                            countMultiPaymentTemp = FRMQueryString.requestInt(request, "countMultiPayment");
                        } catch (Exception e) {
                            countMultiPaymentTemp = 0;
                        }

                        if (countMultiPaymentTemp == 0) {
                            if (payemSystemTemp.isBankInfoOut() == false
                                    && payemSystemTemp.isCardInfo() == false
                                    && payemSystemTemp.isCheckBGInfo() == false
                                    && payemSystemTemp.getPaymentType() == 4) {

                                //CHECK AMOUNT NYA, JIKA SUDAH TERMASUK TAX SERVICE LANGSUNG DIGUNAKAN, TAPI JIKA BELUM TAMBAHKAN
                                double amountBalanceVoc = 0;

                                if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                                    amountBalanceVoc = amountBalance;
                                } else {
                                    amountBalanceVoc = amountBalance * ((billMain.getServicePct() + billMain.getTaxPercentage() + 100) / 100);
                                }

                                if (amountBalanceVoc < voucherTemp.getVoucherNominal()) {
                                    //JIKA YA MAKA AKAN DILAKUKAN INSERT KE CASH BILL DETAIL SEHARGA ITEM YANG MENGALAMI KEKURANGAN
                                    double priceForDetail = 0;
                                    //PROSES PERHITUNGAN HARGA UNTUK ITEM MAPPING
                                    //CEK APAKAH TAX INCLUDE ATAU EXCLUDE
                                    if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                                        //JIKA INCLUDE MAKA HARGA BARANG LANGSUNG NOMINALNYA SESUAI SELISIH
                                        priceForDetail = voucherTemp.getVoucherNominal() - amountBalanceVoc;
                                    } else {
                                        //JIKA EXCLUDE, MAKA HARGA BARANG DI CARI DULU HPPNYA (DIKELUARKAN TAX SERVICENYA)
                                        //priceForDetail = (voucherTemp.getVoucherNominal()-amountBalanceVoc)/((billMain.getServicePct()+billMain.getTaxPercentage()+100)/100);
                                        priceForDetail = (voucherTemp.getVoucherNominal() - amountBalanceVoc) / ((billMain.getServicePct() + billMain.getTaxPercentage() + 100) / 100);
                                    }
                                        priceForDetail = Math.round(priceForDetail);
                                    //INSERT KE TABEL DETAIL, SESUAI ITEM YANG DI MAPPING
                                    String oidFoodMapVoucher = PstSystemProperty.getValueByName("MAPPING_VOUCHER_ITEM_LESS_SALE");
                                    long oidItemItemMapp = Long.parseLong(oidFoodMapVoucher);
                                    Material matMappVoucher = new Material();
                                    try {
                                        matMappVoucher = PstMaterial.fetchExc(oidItemItemMapp);
                                    } catch (Exception e) {
                                    }

                                    if (matMappVoucher.getOID() != 0) {
                                        Billdetail entBillDetailMap = new Billdetail();
                                        //SET BILL DETAIL YANG AKAN DI INSERT
                                        entBillDetailMap.setBillMainId(oidCustomPayment);
                                        entBillDetailMap.setUnitId(matMappVoucher.getDefaultStockUnitId());
                                        entBillDetailMap.setMaterialId(oidItemItemMapp);
                                        entBillDetailMap.setQty(1);
                                        entBillDetailMap.setItemPrice(priceForDetail);
                                        entBillDetailMap.setDisc(0);
                                        entBillDetailMap.setTotalPrice(priceForDetail);
                                        entBillDetailMap.setSku(matMappVoucher.getSku());
                                        entBillDetailMap.setItemName(matMappVoucher.getName());
                                        entBillDetailMap.setLengthOrder(new Date());

                                        //INSERT KE TABEL DETAIL
                                        CtrlCustomBillDetail ctrlBillDetail = new CtrlCustomBillDetail(request);
                                        int iVoInsert = ctrlBillDetail.actionObject(Command.SAVE, entBillDetailMap, request);
                                        if (iVoInsert == 0) {
                                            amountBalance = amountBalance + priceForDetail;
                                        }

                                    }

                                }

                            }

                        }

                        //DATE DATA
                        Date dateNow = Formater.reFormatDate(new Date(), "yyyy-MM-dd");

                        //GET SERVICE, TAX,
                        double discPct = 0;
                        double discVal = 0;
                        double servicePct = 0;
                        double serviceVal = 0;
                        double taxPct = 0;
                        double taxVal = 0;
                        double ccVal = 0;
                        double amount = 0;
                        double dppAmount = 0; //DASAR PENGENALAN PAJAK

                        //CHECK DISCOUNT
                        if (billMain.getDiscPct() != 0) {
                            discPct = billMain.getDiscPct();
                            discVal = amountBalance * (discPct / 100);
                        } else {
                            discPct = 0;
                            discVal = billMain.getDiscount();
                        }

                        double totalSebelumDisc = amountBalance;
                        amountBalance -= discVal;

                        //CHECK INCLUDE OR EXCLUDE TAX SERVICE
                        //INCLUDE TAX SERVICE
                        if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                            double totalIncludeTaxService = 0;
                            totalIncludeTaxService = amountBalance;
                            //DPP
                            amountBalance = amountBalance / ((billMain.getServicePct() + billMain.getTaxPercentage() + 100) / 100);

                            //dppAmount=amountBalance+(discVal/((billMain.getServicePct()+billMain.getTaxPercentage()+100)/100));
                            amount = amountBalance;

                            taxPct = billMain.getTaxPercentage();
                            taxVal = amount * (taxPct / 100);
                            //amountBalance+=taxVal;

                            servicePct = billMain.getServicePct();
                            serviceVal = amount * (servicePct / 100);
                            amountBalance += serviceVal + taxVal;

                            dppAmount = totalIncludeTaxService + discVal - (taxVal + serviceVal);

                            ccVal = amountBalance * (ccPct / 100);

                            //EXCLUDE TAX SERVICE
                        } else {

                            amount = amountBalance;
                            servicePct = billMain.getServicePct();
                            serviceVal = amountBalance * (servicePct / 100);
                            //amountBalance+=serviceVal;
                            dppAmount = amountBalance + discVal;
                            taxPct = billMain.getTaxPercentage();
                            taxVal = amountBalance * (taxPct / 100);
                            amountBalance += taxVal + serviceVal;

                            ccVal = amountBalance * (ccPct / 100);
                        }

                        PaymentSystem paymentSystem = new PaymentSystem();
                        Voucher voucher = new Voucher();

                        billMain = PstBillMain.fetchExc(cashPayments.getBillMainId());
                        billMain.setDiscPct(discPct);
                        billMain.setDiscount(discVal);
                        billMain.setServicePct(servicePct);
                        billMain.setServiceValue(serviceVal);
                        billMain.setTaxPercentage(taxPct);
                        billMain.setTaxValue(taxVal);
                        billMain.setDiscPct(discPct);
                        billMain.setDiscount(discVal);
                        if (billMain.getDiscPct() == 0 && billMain.getDiscount() > 0) {
                            double pctDiscNew = billMain.getDiscount() / totalSebelumDisc * 100;
                            billMain.setDiscPct(pctDiscNew);
                        }
                        billMain.setShiftId(shiftId);
                        billMain.setAppUserId(appUserId);
                        billMain.setReservasiId(reservationId);
                        if (newCustomerOID != 0) {
                            billMain.setCustomerId(newCustomerOID);
                        }
                        billMain.setBillStatus(2);

                        //GET DATA
                        Vector listDailyRate = PstDailyRate.getCurrentDailyRate();
                        DailyRate dailyRate = new DailyRate();
                        try {
                            if (listDailyRate.size() != 0) {
                                for (int i = 0; i < listDailyRate.size(); i++) {
                                    DailyRate dailyRateData = (DailyRate) listDailyRate.get(i);
                                    if (cashPayments.getCurrencyId() != 0) {
                                        if (cashPayments.getCurrencyId() == dailyRateData.getCurrencyTypeId()) {
                                            dailyRate = dailyRateData;
                                        }
                                    } else if (billMain.getCurrencyId() == dailyRateData.getCurrencyTypeId()) {
                                        dailyRate = dailyRateData;
                                    }
                                }
                            }

                        } catch (Exception ex) {
                            dailyRate = new DailyRate();
                        }

                        double amountDollar = 0;
                        double amountRp = 0;

                        Vector listDailyRateConvert = PstDailyRate.list(0, 0,
                                "" + PstDailyRate.fieldNames[PstDailyRate.FLD_CURRENCY_TYPE_ID] + "='2' "
                                + "AND " + PstDailyRate.fieldNames[PstDailyRate.FLD_ROSTER_DATE] + "<=(NOW())"
                                + "", "");
                        DailyRate dailyRate1;
                        if (listDailyRateConvert.size() != 0) {
                            dailyRate1 = (DailyRate) listDailyRateConvert.get(0);
                        } else {
                            dailyRate1 = new DailyRate();
                        }

                        long voucherId = 0;
                        boolean canSave = true;

                        double totalQty = PstCustomBillMain.getTotalQty(billMain.getOID());
                        double averageDisc = discVal / totalQty;

                        //GET BILL DETAIL LIST
                        Vector listBillDetail = PstBillDetail.list(0, 0,
                                "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'",
                                "");

                        if (isPackage.equals("1")) {
                            for (int a = 0; a < listBillDetail.size(); a++) {
                                Billdetail entBilldetail = new Billdetail();
                                entBilldetail = (Billdetail) listBillDetail.get(a);

                                if (entBilldetail.getParentId() == 0) {
                                    ConsumePackage entConsumePackage = new ConsumePackage();
                                    entConsumePackage.setConsumePackageBillingId(entBilldetail.getCutomePackBillingId());
                                    entConsumePackage.setBillDetailId(entBilldetail.getOID());
                                    long oidConsume = PstConsumePackage.insertExc(entConsumePackage);
                                }
                            }
                        }
                        ///////////////////////////
                        //////////CASH PAYMENT TYPE
                        if (transactionType == 0 || transactionType == 2) {

                            //////////////////////////////    
                            /////CHECK VOUCHER *Unfinished
                            try {
                                paymentSystem = PstPaymentSystem.fetchExc(cashPayments.getPaymentType());

                                /////////////////////
                                /////VOUCHER PAYMENT
                                if (paymentSystem.isBankInfoOut() == false
                                        && paymentSystem.isCardInfo() == false
                                        && paymentSystem.isCheckBGInfo() == false
                                        && paymentSystem.getPaymentType() == 4) {
                                    if (countMultiPaymentTemp == 0) {
                                        Vector listVoucher = PstVoucher.list(0, 0,
                                                "(" + PstVoucher.fieldNames[PstVoucher.FLD_VOUCHER_BARCODE] + "='" + oidVoucher + "' "
                                                + "OR " + PstVoucher.fieldNames[PstVoucher.FLD_VOUCHERNO] + "='" + oidVoucher + "')", "");
                                        if (listVoucher.size() != 0) {
                                            voucher = (Voucher) listVoucher.get(0);
                                            if (voucher.getVoucherType() == 0 && amountCash == 0) {
                                                cashPayments.setAmount(voucher.getVoucherNominal());
                                                voucherId = voucher.getOID();
                                                if ((voucher.getVoucherNominal() - balance) < 0) {
                                                    canSave = false;
                                                    msgString = String.valueOf(balance - voucher.getVoucherNominal());
                                                } else {
                                                    msgString = "SUCCESS";
                                                }
                                            } else {
                                                cashPayments.setAmount(voucher.getVoucherNominal());
                                                voucherId = voucher.getOID();
                                                if ((voucher.getVoucherNominal() - balance) < 0) {
                                                    canSave = false;
                                                    msgString = String.valueOf(balance - voucher.getVoucherNominal());
                                                } else {
                                                    msgString = "SUCCESS";
                                                }
                                            }
                                        }
                                    }

                                    ///////////////////	    
                                    ////////FOC PAYMENT OR COSTING
                                } else if (paymentSystem.isBankInfoOut() == false
                                        && paymentSystem.isCardInfo() == false
                                        && paymentSystem.isCheckBGInfo() == false && paymentSystem.getPaymentType() == 6) {

                                    //GET LOCATION, COUNTER & GENERATE COSTING CODE
                                    int counter = PstMatCosting.getCount("") + 1;
                                    Location location = PstLocation.fetchExc(billMain.getLocationId());
                                    String costingCode = "CSTSLS-" + location.getCode() + "-001-" + counter;

                                    /////INSERT MATERIAL COSTING
                                    String newRemark = "" + memberName + ";" + remarkFoc + ";" + billMain.getOID() + ";" + billMain.getInvoiceNumber();
                                    long oidMatCosting = updateMatCosting(billMain, dateNow, newRemark, costingCode,
                                            counter, cashCashier, creditMemberId, paymentSystem, location);
                                    if (listBillDetail.size() != 0) {
                                        for (int i = 0; i < listBillDetail.size(); i++) {
                                            Billdetail billdetail = (Billdetail) listBillDetail.get(i);
                                            Vector listComposit = new Vector(1, 1);

                                            if (billdetail.getMaterialType() == 1) {
                                                listComposit = PstMaterialComposit.ListComponentComposit(billdetail.getMaterialId());
                                            }

                                            double hpp = 0;
                                            if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {

                                                hpp = (billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/) / ((billMain.getTaxPercentage() + billMain.getServicePct() + 100) / 100);
                                            } else {
                                                hpp = billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/;
                                            }

                                            /////INSERT MATERIAL COSTING ITEM
                                            updateBillDetail(billdetail, hpp, billMain, averageDisc, taxInc);

                                            //HPP DIAMBIL DARI AVERAGE PRICE
                                            Material entMaterial = new Material();
                                            try {
                                                entMaterial = PstMaterial.fetchExc(billdetail.getMaterialId());
                                            } catch (Exception e) {
                                            }
                                            hpp = entMaterial.getAveragePrice();

                                            if (listComposit.size() != 0) {
                                                for (int iComposit = 0; iComposit < listComposit.size(); iComposit++) {
                                                    Vector listTemp = (Vector) listComposit.get(iComposit);
                                                    MaterialComposit materialComposit = new MaterialComposit();
                                                    if (listTemp.size() > 0) {
                                                        materialComposit = (MaterialComposit) listTemp.get(0);
                                                        updateMatCostingItemComposit(oidMatCosting, billdetail, hpp);
                                                    }

                                                    ////INSERT COSTING COMPOSIT
                                                    updateMatCostingComposit(oidMatCosting, materialComposit, billdetail);
                                                }
                                            } else {
                                                Material entMaterialTemp = new Material();
                                                try {
                                                    entMaterialTemp = PstMaterial.fetchExc(billdetail.getMaterialId());
                                                } catch (Exception e) {
                                                }
                                                if (entMaterialTemp.getMaterialType() == 0) {
                                                    updateMatCostingItem(oidMatCosting, billdetail, hpp);
                                                } else {
                                                    updateMatCostingItemComposit(oidMatCosting, billdetail, hpp);
                                                }

                                            }

                                        }
                                    }
                                } else if (paymentSystem.isBankInfoOut() == false
                                        && paymentSystem.isCardInfo() == false
                                        && paymentSystem.isCheckBGInfo() == false && paymentSystem.getPaymentType() == 5) {

                                    if (countMultiPaymentTemp == 0) {
                                        Vector listVoucher = PstVoucher.list(0, 0,
                                                "(" + PstVoucher.fieldNames[PstVoucher.FLD_VOUCHER_BARCODE] + "='" + oidVoucher + "' "
                                                + "OR " + PstVoucher.fieldNames[PstVoucher.FLD_VOUCHERNO] + "='" + oidVoucher + "')", "");
                                        if (listVoucher.size() != 0) {
                                            voucher = (Voucher) listVoucher.get(0);
                                            if (voucher.getVoucherType() == 0 && amountCash == 0) {
                                                cashPayments.setAmount(voucher.getVoucherNominal());
                                                voucherId = voucher.getOID();
                                                if ((voucher.getVoucherNominal() - balance) < 0) {
                                                    canSave = false;
                                                    msgString = String.valueOf(balance - voucher.getVoucherNominal());
                                                } else {
                                                    voucherCompliment(memberName, remarkFoc, dateNow, cashCashier, creditMemberId, paymentSystem, listBillDetail, taxInc, averageDisc);
                                                    msgString = "SUCCESS";
                                                }
                                            } else {
                                                cashPayments.setAmount(voucher.getVoucherNominal());
                                                voucherId = voucher.getOID();
                                                if ((voucher.getVoucherNominal() - balance) < 0) {
                                                    canSave = false;
                                                    msgString = String.valueOf(balance - voucher.getVoucherNominal());
                                                } else {
                                                    voucherCompliment(memberName, remarkFoc, dateNow, cashCashier, creditMemberId, paymentSystem, listBillDetail, taxInc, averageDisc);
                                                    msgString = "SUCCESS";
                                                }
                                            }
                                        }
                                    }

                                    /////////////////////
                                    /////////CASH PAYMENT
                                } else if (paymentSystem.isBankInfoOut() == false
                                        && paymentSystem.isCardInfo() == false
                                        && paymentSystem.isCheckBGInfo() == false
                                        && paymentSystem.getPaymentType() == 1) {

                                    if (listBillDetail.size() != 0) {
                                        for (int i = 0; i < listBillDetail.size(); i++) {
                                            Billdetail billdetail = (Billdetail) listBillDetail.get(i);

                                            Vector listComposit = PstMaterialComposit.ListComponentComposit(billdetail.getMaterialId());

                                            double hpp = 0;
                                            if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                                                hpp = (billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/) / ((billMain.getTaxPercentage() + billMain.getServicePct() + 100) / 100);
                                            } else {
                                                hpp = billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/;
                                            }

                                            /////INSERT MATERIAL COSTING ITEM
                                            updateBillDetail(billdetail, hpp, billMain, averageDisc, taxInc);
                                        }
                                    }
                                } else if (paymentSystem.isBankInfoOut() == false
                                        && paymentSystem.isCardInfo() == true
                                        && paymentSystem.isCheckBGInfo() == false
                                        && paymentSystem.getPaymentType() == 0) {

                                    if (listBillDetail.size() != 0) {
                                        for (int i = 0; i < listBillDetail.size(); i++) {
                                            Billdetail billdetail = (Billdetail) listBillDetail.get(i);

                                            Vector listComposit = PstMaterialComposit.ListComponentComposit(billdetail.getMaterialId());
                                            double hpp = 0;
                                            if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                                                hpp = (billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/) / ((billMain.getTaxPercentage() + billMain.getServicePct() + 100) / 100);
                                            } else {
                                                hpp = billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/;
                                            }

                                            /////INSERT MATERIAL COSTING ITEM
                                            updateBillDetail(billdetail, hpp, billMain, averageDisc, taxInc);
                                        }
                                    }
                                } else if (paymentSystem.isBankInfoOut() == false
                                        && paymentSystem.isCardInfo() == false
                                        && paymentSystem.isCheckBGInfo() == false
                                        && paymentSystem.getPaymentType() == 2) {

                                    if (listBillDetail.size() != 0) {
                                        for (int i = 0; i < listBillDetail.size(); i++) {
                                            Billdetail billdetail = (Billdetail) listBillDetail.get(i);

                                            Vector listComposit = PstMaterialComposit.ListComponentComposit(billdetail.getMaterialId());
                                            double hpp = 0;
                                            if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                                                hpp = (billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/) / ((billMain.getTaxPercentage() + billMain.getServicePct() + 100) / 100);
                                            } else {
                                                hpp = billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/;
                                            }

                                            /////INSERT MATERIAL COSTING ITEM
                                            updateBillDetail(billdetail, hpp, billMain, averageDisc, taxInc);
                                        }
                                    }
                                }
                            } catch (Exception ex) {

                            }

                            //////////////////////////
                            //APAKAH MENGGUNAKAN MULTI PAYMENT ATAU TIDAK
                            int countMultiPayment = 0;
                            try {
                                countMultiPayment = FRMQueryString.requestInt(request, "countMultiPayment");
                            } catch (Exception e) {
                                countMultiPayment = 0;
                            }

                            if (countMultiPayment == 0) {
                                //////////////////////////
                                /////SET DATA CASH PAYMENT
                                cashPayments.setPayDateTime(Formater.reFormatDate(new Date(), "yyyy-MM-dd kk:mm:ss"));
                                cashPayments.setRate(dailyRate.getSellingRate());
                                if (cashPayments.getCurrencyId() == 0) {
                                    cashPayments.setCurrencyId(billMain.getCurrencyId());
                                }

                                String whereCashPayment = " " + PstCashPayment1.fieldNames[PstCashPayment1.FLD_BILL_MAIN_ID] + "=" + cashPayments.getBillMainId() + "";
                                int countCashPayment = PstCashPayment1.getCount(whereCashPayment);

                                if (countCashPayment == 0) {
                                    ////////////////////////
                                    /////INSERT CASH PAYMENT
                                    
                                    //ADDED BY DEWOK 20191128 FOR GUIDE PRICE
                                    double payAmount = this.cashPayments.getAmount();
                                    if (TransactionCashierHandler.checkMemberGuidePrice(billMain.getOID())) {
                                        payAmount = this.cashPayments.getAmountGuidePrice();
                                    }

                                    if (payAmount >= amountBalance) {

                                        long oid = pstCashPayment.insertExc(this.cashPayments);

                                        ///////////////////////
                                        /////INSERT CREDIT CARD
                                        insertCreditCard(cashPayments, billMain, oid, request, dailyRate, ccVal);

                                        ///////////////////    
                                        /////INSERT VOUCHER
                                        updateVoucher(cashPayments, billMain, oidCustomPayment, dailyRate, request, voucherId);

                                        //////////////////////////////
                                        /////INPUT CASH PAYMENT RETURN
                                        insertReturnPayment(cashPayments, billMain, oidCustomPayment, dailyRate);
                                    } else {
                                        canSave = false;
                                    }
                                }
                            } else {
                                //MULTI PAYMENT
                                for (int k = 1; k <= countMultiPayment; k++) {
                                    //REQUEST LOOP FORM
                                    long cashBillMainIds = FRMQueryString.requestLong(request, "multiPayCashBillMainId");
                                    int masterFieldPayType = FRMQueryString.requestInt(request, "MASTER_FRM_FIELD_PAY_TYPE_MULTI_" + k + "");
                                    long payTypeMulti = FRMQueryString.requestLong(request, "FRM_FIELD_PAY_TYPE_MULTI_" + k + "");
                                    String ccNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NAME_MULTI_" + k + "");
                                    String ccNumberMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NUMBER_MULTI_" + k + "");
                                    String debitBankNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_DEBIT_BANK_NAME_MULTI_" + k + "");
                                    String expiredDateMulti = FRMQueryString.requestString(request, "FRM_FIELD_EXPIRED_DATE_MULTI_" + k + "");
                                    double payAmountMulti = FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI_" + k + "");
                                    long voucherIdMulti = FRMQueryString.requestLong(request, "FRM_FIELD_VOUCHER_ID_MULTI_" + k + "");
                                    PaymentSystem paymentSystemMulti = new PaymentSystem();

                                    try {
                                        paymentSystemMulti = PstPaymentSystem.fetchExc(payTypeMulti);
                                    } catch (Exception e) {
                                    }

                                    CashPayments1 entCashPayments1 = new CashPayments1();
                                    entCashPayments1.setBillMainId(cashBillMainIds);
                                    entCashPayments1.setPaymentStatus(0);
                                    entCashPayments1.setPayDateTime(Formater.reFormatDate(new Date(), "yyyy-MM-dd kk:mm:ss"));
                                    entCashPayments1.setRate(dailyRate.getSellingRate());
                                    if (entCashPayments1.getCurrencyId() == 0) {
                                        entCashPayments1.setCurrencyId(billMain.getCurrencyId());
                                    }
                                    entCashPayments1.setPaymentType(payTypeMulti);
                                    entCashPayments1.setAmount(payAmountMulti);

                                    //UNTUK VOUCHER
                                    if (paymentSystemMulti.isBankInfoOut() == false
                                            && paymentSystemMulti.isCardInfo() == false
                                            && paymentSystemMulti.isCheckBGInfo() == false
                                            && paymentSystemMulti.getPaymentType() == 4) {

                                        Vector listVoucher = PstVoucher.list(0, 0,
                                                "(" + PstVoucher.fieldNames[PstVoucher.FLD_VOUCHER_BARCODE] + "='" + voucherIdMulti + "' "
                                                + "OR " + PstVoucher.fieldNames[PstVoucher.FLD_VOUCHERNO] + "='" + voucherIdMulti + "')", "");
                                        if (listVoucher.size() != 0) {
                                            voucher = (Voucher) listVoucher.get(0);
                                            if (voucher.getVoucherType() == 0 && amountCash == 0) {
                                                entCashPayments1.setAmount(voucher.getVoucherNominal());
                                                voucherId = voucher.getOID();
                                                /*if((voucher.getVoucherNominal() - balance) < 0){
                                                 canSave = false;
                                                 msgString = String.valueOf(balance-voucher.getVoucherNominal());
                                                 }else{
                                                 msgString = "SUCCESS";
                                                 }*/
                                            } else {
                                                entCashPayments1.setAmount(voucher.getVoucherNominal());
                                                voucherId = voucher.getOID();
                                                /*if((voucher.getVoucherNominal() - balance) < 0){
                                                 canSave = false;
                                                 msgString = String.valueOf(balance-voucher.getVoucherNominal());
                                                 }else{
                                                 msgString = "SUCCESS";
                                                 }*/
                                            }
                                        }
                                    }
                                    ///AKHIR VOUCHER
                                    ////////////////////////
                                    /////INSERT CASH PAYMENT
                                    long oid = pstCashPayment.insertExc(entCashPayments1);

                                    ///////////////////////
                                    /////INSERT CREDIT CARD
                                    insertCreditCardMulti(entCashPayments1, billMain, oid, request, dailyRate, ccVal, k);

                                    ///////////////////    
                                    /////INSERT VOUCHER
                                    updateVoucher(entCashPayments1, billMain, oidCustomPayment, dailyRate, request, voucherIdMulti);

                                    //////////////////////////////
                                    /////INPUT CASH PAYMENT RETURN
                                    if (k == 1) {
                                        insertReturnPayment(entCashPayments1, billMain, oidCustomPayment, dailyRate);
                                    }

                                }
                            }

                            ////////////////////
                            /////SET STATUS BILL
                            billMain.setDocType(0);
                            billMain.setTransactionStatus(0);
                            billMain.setTransctionType(0);

                            ////////////////////////////
                            /////////CREDIT PAYMENT TYPE
                        } else {

                            try {

                                double amountCredit = PstCustomBillMain.checkTotalCredit(0, 0, "contact." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + "='" + creditMemberId + "' "
                                        + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                                        + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                                        + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1' "
                                        + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1'", "");
                                int checkMember = PstCustomBillMain.listMember(0, 0,
                                        "contact." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + "='" + creditMemberId + "' "
                                        + "AND memberReg." + PstMemberRegistrationHistory.fieldNames[PstMemberRegistrationHistory.FLD_VALID_EXPIRED_DATE] + ">='" + dateNow + "' "
                                        + "AND memberReg." + PstMemberRegistrationHistory.fieldNames[PstMemberRegistrationHistory.FLD_VALID_START_DATE] + "<='" + dateNow + "'", "");
                                double checkCredit = 0;
                                customBalance = customBalance;
                                Contact contact = PstContact.fetchExc(creditMemberId);
                                checkCredit = contact.getMemberCreditLimit() - (amountCredit + (amountBalance - discVal));
                                if (checkCredit >= 0 && checkCredit > 0) {
                                    canSave = true;
                                    billMain.setDocType(0);
                                    billMain.setTransctionType(1);
                                    billMain.setTransactionStatus(1);
                                    billMain.setStatusInv(1);
                                    billMain.setCustomerId(creditMemberId);
                                    billMain.setPaidAmount(0);

                                    //PROCESS BILL DETAIL FIRST
                                    if (listBillDetail.size() != 0) {
                                        for (int i = 0; i < listBillDetail.size(); i++) {
                                            Billdetail billdetail = (Billdetail) listBillDetail.get(i);

                                            Vector listComposit = PstMaterialComposit.ListComponentComposit(billdetail.getMaterialId());

                                            double hpp = 0;
                                            if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                                                hpp = (billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/) / ((billMain.getTaxPercentage() + billMain.getServicePct() + 100) / 100);
                                            } else {
                                                hpp = billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/;
                                            }

                                            /////INSERT MATERIAL COSTING ITEM
                                            updateBillDetail(billdetail, hpp, billMain, averageDisc, taxInc);
                                        }
                                    }
                                } else {
                                    if (member == 10003 || customerType.indexOf("#") != -1) {
                                        canSave = true;
                                        billMain.setDocType(0);
                                        billMain.setTransctionType(1);
                                        billMain.setTransactionStatus(1);
                                        billMain.setStatusInv(1);
                                        billMain.setCustomerId(newCustomerOID);
                                        billMain.setPaidAmount(0);

                                        //PROCESS BILL DETAIL FIRST
                                        if (listBillDetail.size() != 0) {
                                            for (int i = 0; i < listBillDetail.size(); i++) {
                                                Billdetail billdetail = (Billdetail) listBillDetail.get(i);

                                                Vector listComposit = PstMaterialComposit.ListComponentComposit(billdetail.getMaterialId());

                                                double hpp = 0;
                                                if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                                                    hpp = (billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/) / ((billMain.getTaxPercentage() + billMain.getServicePct() + 100) / 100);
                                                } else {
                                                    hpp = billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/;
                                                }

                                                /////INSERT MATERIAL COSTING ITEM
                                                updateBillDetail(billdetail, hpp, billMain, averageDisc, taxInc);
                                            }
                                        }

                                    } else {
                                        canSave = false;
                                    }

                                }
                            } catch (Exception ex) {
                                canSave = false;
                            }
                        }

                        //UPDATE BILL MAIN
                        if (canSave == true) {

                            updatebillMain(cashPayments, billMain, oidCustomPayment, dailyRate, cashierNumber, cashCashier, reservationId, dppAmount);

                            //update opie-eyek, kalau path = 0 tidak perlu ada tanda tangan
                            String pathx = PstSystemProperty.getValueByName("CASHIER_PATH_SIGNATURE");
                            if (!pathx.equals("0")) {
                                try {
                                    SignatureImage signatureImage = new SignatureImage(imgString, path, "png", "" + cashPayments.getBillMainId());
                                } catch (Exception ex) {
                                }
                            }

                            //UPDATE TABLE
                            updateTable(cashPayments, billMain, oidCustomPayment, dailyRate);

                            int design = 0;
                            try {
                                design = Integer.parseInt((String) com.dimata.system.entity.PstSystemProperty.getValueByName("DESIGN_MATERIAL_FOR"));
                            } catch (Exception e) {
                                design = 0;
                            }

                            if (cashPayments.getCurrencyId() != 0) {
                                if (cashPayments.getCurrencyId() == 1) {
                                    amountRp = cashPayments.getAmount();
                                    amountDollar = cashPayments.getAmount() / dailyRate1.getSellingRate();

                                } else {
                                    amountRp = cashPayments.getAmount() * dailyRate1.getSellingRate();
                                    amountDollar = cashPayments.getAmount();
                                }
                            } else if (billMain.getCurrencyId() != 0) {
                                if (billMain.getCurrencyId() == 1) {
                                    amountRp = cashPayments.getAmount();
                                    amountDollar = cashPayments.getAmount() / dailyRate1.getSellingRate();

                                } else {
                                    amountRp = cashPayments.getAmount() * dailyRate1.getSellingRate();
                                    amountDollar = cashPayments.getAmount();
                                }
                            } else {
                                amountRp = cashPayments.getAmount();
                                amountDollar = cashPayments.getAmount();
                            }

                            if (design != 0 && transactionType == 0) {
                                insertIntegrationHanoman(creditMemberId, amountRp, amountDollar,
                                        appUserId, dailyRate.getSellingRate(), billMain, request, cashPayments, customerType, 0);
                            } else {

                            }
                            msgString = "SUCCESS";
                        } else {
                            msgString = "Cannot Processed. Please Try Again";
                        }

                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else if (cashPayments.getOID() != 0 && discAssign == 0) {
                    try {
                        long oid = pstCashPayment.updateExc(this.cashPayments);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {
                    msgString = "Maximum discount is " + Formater.formatNumber(discAssign, "#.###");
                }
                break;

            case Command.EDIT:
                if (oidCustomPayment != 0) {
                    try {
                        cashPayments = PstCashPayment1.fetchExc(oidCustomPayment);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidCustomPayment != 0) {
                    try {
                        cashPayments = PstCashPayment1.fetchExc(oidCustomPayment);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidCustomPayment != 0) {
                    try {
                        long oid = PstCashPayment1.deleteExc(oidCustomPayment);
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

    public int actionReturnBill(int cmd, long oidCustomPayment, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidCustomPayment != 0) {
                    try {
                        cashPayments = PstCashPayment1.fetchExc(oidCustomPayment);
                    } catch (Exception exc) {
                    }
                }

                frmCashPayment.requestEntityObject(cashPayments);
                frmBillMain.requestEntityObject(billMain);
                if (frmCashPayment.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (cashPayments.getOID() == 0) {
                    try {

                        long oid = PstCashPayment1.insertExc(this.cashPayments);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {
                    try {
                        long oid = PstCashPayment1.updateExc(this.cashPayments);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidCustomPayment != 0) {
                    try {
                        cashPayments = PstCashPayment1.fetchExc(oidCustomPayment);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidCustomPayment != 0) {
                    try {
                        cashPayments = PstCashPayment1.fetchExc(oidCustomPayment);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidCustomPayment != 0) {
                    try {
                        long oid = PstCashPayment1.deleteExc(oidCustomPayment);
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

//INSERT CREDIT CARD PAYMENT
    public static long insertCreditCard(CashPayments1 cashPayments1,
            BillMain billMain, long oidCashPayment,
            HttpServletRequest request,
            DailyRate dailyRate,
            double ccCharge) throws DBException {

        long oid = 0;
        //CREDIT CARD DATA
        String ccName = "";
        String ccNumber = "";
        String expiredDate = "";
        String debitBank = "";

        //CEK APAKAH MENGGUNAKAN MULTIPAYMENT ATAU TIDAK
        int countMultiPayment = 0;
        try {
            countMultiPayment = FRMQueryString.requestInt(request, "countMultiPayment");
        } catch (Exception e) {
            countMultiPayment = 0;
        }

        if (countMultiPayment == 0) {
            ccName = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]);
            ccNumber = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]);
            expiredDate = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]);
            debitBank = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]);
        } else {
            ccName = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] + "_MULTI");
            ccNumber = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "_MULTI");
            expiredDate = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE] + "_MULTI");
            debitBank = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "_MULTI");
        }

        CustomCashCreditCard cashCreditCard = new CustomCashCreditCard();
        try {
            PaymentSystem paymentSystem = PstPaymentSystem.fetchExc(cashPayments1.getPaymentType());
            if (paymentSystem.isCardInfo() == true
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false
                    && paymentSystem.getPaymentType() == 0) {
                cashCreditCard.setCashPaymentId(oidCashPayment);
                cashCreditCard.setCcName(ccName);
                cashCreditCard.setCcNumber(ccNumber);
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                cashCreditCard.setDebitBankName(debitBank);

                if (cashPayments1.getCurrencyId() != 0) {
                    cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                } else {
                    cashCreditCard.setCurrencyId(billMain.getCurrencyId());
                }

                cashCreditCard.setRate(dailyRate.getSellingRate());
                //cashCreditCard.setAmount(cashPayments.getAmount());
                cashCreditCard.setBankCost(ccCharge);

                oid = PstCustomCashCreditCard.insertExc(cashCreditCard);

                /////BG PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == true
                    && paymentSystem.isCheckBGInfo() == false
                    && paymentSystem.getPaymentType() == 2) {

                cashCreditCard.setChequeBank(debitBank);
                cashCreditCard.setChequeAccountName(ccName);
                cashCreditCard.setCcNumber(ccNumber);
                cashCreditCard.setRate(dailyRate.getSellingRate());
                cashCreditCard.setBankCost(ccCharge);
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                cashCreditCard.setChequeDueDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                if (cashPayments1.getCurrencyId() != 0) {
                    cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                } else {
                    cashCreditCard.setCurrencyId(billMain.getCurrencyId());
                }

                oid = PstCustomCashCreditCard.insertExc(cashCreditCard);

/////////CHECK PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == true
                    && paymentSystem.getPaymentType() == 0) {

                cashCreditCard.setChequeBank(debitBank);
                cashCreditCard.setChequeAccountName(ccName);
                cashCreditCard.setCcNumber(ccNumber);
                cashCreditCard.setRate(dailyRate.getSellingRate());
                cashCreditCard.setBankCost(ccCharge);
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                cashCreditCard.setChequeDueDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                if (cashPayments1.getCurrencyId() != 0) {
                    cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                } else {
                    cashCreditCard.setCurrencyId(billMain.getCurrencyId());
                }

                oid = PstCustomCashCreditCard.insertExc(cashCreditCard);

/////////DEBIT PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false
                    && paymentSystem.getPaymentType() == 2) {

                cashCreditCard.setDebitBankName(debitBank);
                cashCreditCard.setCcNumber(ccNumber);
                cashCreditCard.setRate(dailyRate.getSellingRate());
                cashCreditCard.setBankCost(ccCharge);
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                cashCreditCard.setDebitCardName(debitBank);
                if (cashPayments1.getCurrencyId() != 0) {
                    cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                } else {
                    cashCreditCard.setCurrencyId(billMain.getCurrencyId());
                }

                oid = PstCustomCashCreditCard.insertExc(cashCreditCard);
            }
        } catch (Exception ex) {
        }

        return oid;
    }

    public static long insertCreditCard2(CashPayments1 cashPayments1,
            BillMain billMain, long oidCashPayment,
            HttpServletRequest request,
            double ccCharge) throws DBException {

        long oid = 0;
        //CREDIT CARD DATA
        String ccName = "";
        String ccNumber = "";
        String expiredDate = "";
        String debitBank = "";

        //CEK APAKAH MENGGUNAKAN MULTIPAYMENT ATAU TIDAK
        int countMultiPayment = 0;
        try {
            countMultiPayment = FRMQueryString.requestInt(request, "countMultiPayment");
        } catch (Exception e) {
            countMultiPayment = 0;
        }

        ccName = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] + "_MULTI");
        ccNumber = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "_MULTI");
        expiredDate = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE] + "_MULTI");
        debitBank = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "_MULTI");

        CustomCashCreditCard cashCreditCard = new CustomCashCreditCard();
        try {
            PaymentSystem paymentSystem = PstPaymentSystem.fetchExc(cashPayments1.getPaymentType());
            if (paymentSystem.isCardInfo() == true
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false
                    && paymentSystem.getPaymentType() == 0) {
                cashCreditCard.setCashPaymentId(oidCashPayment);
                cashCreditCard.setCcName(ccName);
                cashCreditCard.setCcNumber(ccNumber);
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                cashCreditCard.setDebitBankName(debitBank);

                if (cashPayments1.getCurrencyId() != 0) {
                    cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                } else {
                    cashCreditCard.setCurrencyId(billMain.getCurrencyId());
                }

                cashCreditCard.setRate(billMain.getRate());
                //cashCreditCard.setAmount(cashPayments.getAmount());
                cashCreditCard.setBankCost(ccCharge);

                oid = PstCustomCashCreditCard.insertExc(cashCreditCard);

                /////BG PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == true
                    && paymentSystem.isCheckBGInfo() == false
                    && paymentSystem.getPaymentType() == 2) {

                cashCreditCard.setChequeBank(debitBank);
                cashCreditCard.setChequeAccountName(ccName);
                cashCreditCard.setCcNumber(ccNumber);
                cashCreditCard.setRate(billMain.getRate());
                cashCreditCard.setBankCost(ccCharge);
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                cashCreditCard.setChequeDueDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                if (cashPayments1.getCurrencyId() != 0) {
                    cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                } else {
                    cashCreditCard.setCurrencyId(billMain.getCurrencyId());
                }

                oid = PstCustomCashCreditCard.insertExc(cashCreditCard);

/////////CHECK PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == true
                    && paymentSystem.getPaymentType() == 0) {

                cashCreditCard.setChequeBank(debitBank);
                cashCreditCard.setChequeAccountName(ccName);
                cashCreditCard.setCcNumber(ccNumber);
                cashCreditCard.setRate(billMain.getRate());
                cashCreditCard.setBankCost(ccCharge);
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                cashCreditCard.setChequeDueDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                if (cashPayments1.getCurrencyId() != 0) {
                    cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                } else {
                    cashCreditCard.setCurrencyId(billMain.getCurrencyId());
                }

                oid = PstCustomCashCreditCard.insertExc(cashCreditCard);

/////////DEBIT PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false
                    && paymentSystem.getPaymentType() == 2) {

                cashCreditCard.setDebitBankName(debitBank);
                cashCreditCard.setCcNumber(ccNumber);
                cashCreditCard.setRate(billMain.getRate());
                cashCreditCard.setBankCost(ccCharge);
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                cashCreditCard.setDebitCardName(debitBank);
                if (cashPayments1.getCurrencyId() != 0) {
                    cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                } else {
                    cashCreditCard.setCurrencyId(billMain.getCurrencyId());
                }

                oid = PstCustomCashCreditCard.insertExc(cashCreditCard);
            }
        } catch (Exception ex) {
        }

        return oid;
    }

//INSERT CREDIT CARD PAYMENT MULTI
    public static long insertCreditCardMulti(CashPayments1 cashPayments1,
            BillMain billMain, long oidCashPayment,
            HttpServletRequest request,
            DailyRate dailyRate,
            double ccCharge, int index) throws DBException {

        long oid = 0;
        //CREDIT CARD DATA
        String ccName = "";
        String ccNumber = "";
        String expiredDate = "";
        String debitBank = "";

        //CEK APAKAH MENGGUNAKAN MULTIPAYMENT ATAU TIDAK
        int countMultiPayment = 0;
        try {
            countMultiPayment = FRMQueryString.requestInt(request, "countMultiPayment");
        } catch (Exception e) {
            countMultiPayment = 0;
        }

        if (countMultiPayment == 0) {
            ccName = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]);
            ccNumber = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]);
            expiredDate = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]);
            debitBank = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]);
        } else {
            ccName = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] + "_MULTI_" + index + "");
            ccNumber = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "_MULTI_" + index + "");
            expiredDate = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE] + "_MULTI_" + index + "");
            debitBank = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "_MULTI_" + index + "");
        }

        CustomCashCreditCard cashCreditCard = new CustomCashCreditCard();
        try {
            PaymentSystem paymentSystem = PstPaymentSystem.fetchExc(cashPayments1.getPaymentType());
            if (paymentSystem.isCardInfo() == true
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false
                    && paymentSystem.getPaymentType() == 0) {
                cashCreditCard.setCashPaymentId(oidCashPayment);
                cashCreditCard.setCcName(ccName);
                cashCreditCard.setCcNumber(ccNumber);
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                cashCreditCard.setDebitBankName(debitBank);

                if (cashPayments1.getCurrencyId() != 0) {
                    cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                } else {
                    cashCreditCard.setCurrencyId(billMain.getCurrencyId());
                }

                cashCreditCard.setRate(dailyRate.getSellingRate());
                //cashCreditCard.setAmount(cashPayments.getAmount());
                cashCreditCard.setBankCost(ccCharge);

                oid = PstCustomCashCreditCard.insertExc(cashCreditCard);

                /////BG PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == true
                    && paymentSystem.isCheckBGInfo() == false
                    && paymentSystem.getPaymentType() == 2) {

                cashCreditCard.setChequeBank(debitBank);
                cashCreditCard.setChequeAccountName(ccName);
                cashCreditCard.setCcNumber(ccNumber);
                cashCreditCard.setRate(dailyRate.getSellingRate());
                cashCreditCard.setBankCost(ccCharge);
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                cashCreditCard.setChequeDueDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                if (cashPayments1.getCurrencyId() != 0) {
                    cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                } else {
                    cashCreditCard.setCurrencyId(billMain.getCurrencyId());
                }

                oid = PstCustomCashCreditCard.insertExc(cashCreditCard);

/////////CHECK PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == true
                    && paymentSystem.getPaymentType() == 0) {
                cashCreditCard.setCashPaymentId(oidCashPayment);
                cashCreditCard.setChequeBank(debitBank);
                cashCreditCard.setChequeAccountName(ccName);
                cashCreditCard.setCcNumber(ccNumber);
                cashCreditCard.setRate(dailyRate.getSellingRate());
                cashCreditCard.setBankCost(ccCharge);
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                cashCreditCard.setChequeDueDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                if (cashPayments1.getCurrencyId() != 0) {
                    cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                } else {
                    cashCreditCard.setCurrencyId(billMain.getCurrencyId());
                }

                oid = PstCustomCashCreditCard.insertExc(cashCreditCard);

/////////DEBIT PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false
                    && paymentSystem.getPaymentType() == 2) {
                cashCreditCard.setCashPaymentId(oidCashPayment);
                cashCreditCard.setDebitBankName(debitBank);
                cashCreditCard.setCcNumber(ccNumber);
                cashCreditCard.setRate(dailyRate.getSellingRate());
                cashCreditCard.setBankCost(ccCharge);
                cashCreditCard.setExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
                cashCreditCard.setDebitCardName(debitBank);
                if (cashPayments1.getCurrencyId() != 0) {
                    cashCreditCard.setCurrencyId(cashPayments1.getCurrencyId());
                } else {
                    cashCreditCard.setCurrencyId(billMain.getCurrencyId());
                }

                oid = PstCustomCashCreditCard.insertExc(cashCreditCard);
            }
        } catch (Exception ex) {
        }

        return oid;
    }

//INSERT RETURN PAYMENT
    public static long insertReturnPayment(CashPayments1 cashPayments1,
            BillMain billMain, long oidCashPayment,
            DailyRate dailyRate) throws DBException {

        long oid = 0;
        CashReturn cashReturn = new CashReturn();
        PaymentSystem paymentSystem = new PaymentSystem();
        try {
            paymentSystem = PstPaymentSystem.fetchExc(cashPayments1.getPaymentType());
        } catch (Exception e) {
        }

        if (cashPayments1.getCurrencyId() != 0) {
            //cashReturn.setCurrencyId(cashPayments1.getCurrencyId());
            cashReturn.setCurrencyId(billMain.getCurrencyId());
        } else {
            cashReturn.setCurrencyId(billMain.getCurrencyId());
        }

        cashReturn.setBillMainId(cashPayments1.getBillMainId());
        //cashReturn.setRate(dailyRate.getSellingRate());
        cashReturn.setRate(billMain.getRate());
        cashReturn.setAmount(cashPayments1.getAmountReturn());
        oid = PstCashReturn.insertExc(cashReturn);

        return oid;
    }

//UPDATE THE BILL MAIN
    public static long updatebillMain(CashPayments1 cashPayments1,
            BillMain billMain, long oidCashPayment,
            DailyRate dailyRate, int cashierNumber,
            long cashCashier, long reservationId, double dppAmount) throws DBException {
        Date newDate = new Date();
        String guestName = "";
        ContactList contact = new ContactList();
        if (billMain.getCustomerId() != 0) {
            try {
                contact = PstContactList.fetchExc(billMain.getCustomerId());
                if (contact.getContactType() == 7 || contact.getContactType() == 10) {
                    String guestNameSelected = com.dimata.common.entity.system.PstSystemProperty.getValueByName("CASHIER_GUEST_NAME_INPUT");
                    if (guestNameSelected.equals("0")) {
                        guestName = contact.getPersonName();
                    } else {
                        guestName = billMain.getGuestName();
                    }
                } else {
                    guestName = contact.getCompName();
                }

            } catch (Exception ex) {
            }
        }
        String newInvoice = "";
        long oid = 0;
        int counter = PstCustomBillMain.getCounterBill(0, 0, "", "") + 1;

        String invoiceNumb = "";
        int counterPlus = 0;
        int countInvoiceNumber = 0;
        /*do {
         int countAfter = counter=counter + counterPlus;
         String zeroCount = "000"+ countAfter;
         String zeroCount2 = zeroCount.substring(zeroCount.length()-3); 
         invoiceNumb = "00"+cashierNumber+"."+Formater.formatDate(new Date(), "yyyyMMdd")+"."+zeroCount2;
        
         String where= ""+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NUMBER]+"="+invoiceNumb+"";
         Vector list = PstBillMain.list(0, 0, where, "");
         countInvoiceNumber = list.size();
         counterPlus += 1; 
         } while (countInvoiceNumber>0);*/
        String useForRaditya = PstSystemProperty.getValueByName("USE_FOR_RADITYA"); 
        String cashierNumberFormat = "00" + cashierNumber + "." + Formater.formatDate(new Date(), "yyyyMMdd");
        //invoiceNumb = generateInvoiceNumber(cashierNumberFormat);
        Vector listLastInvcNumber = PstBillMain.list(0, 1, "" + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '%" + cashierNumberFormat + "%' "
                + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " NOT LIKE '%C'", PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " DESC");
        if (listLastInvcNumber.size() > 0) {
            BillMain billMainInv = (BillMain) listLastInvcNumber.get(0);
            String[] number = billMainInv.getInvoiceNo().split("\\.");
            int newNumber = Integer.parseInt(number[2]) + 1;
            String lastNumber = "000" + newNumber;
            lastNumber = lastNumber.substring(lastNumber.length() - 3);
            if(useForRaditya.equals("1")){
            invoiceNumb = billMain.getInvoiceNumber();
            }else{
            invoiceNumb = cashierNumberFormat + "." + lastNumber;
            }
        } else {
            invoiceNumb = cashierNumberFormat + ".001";
        }
        billMain.setGuestName(guestName);
        billMain.setBillDate(newDate);
        billMain.setCashCashierId(cashCashier);
        String printOpenBillgenerate = PstSystemProperty.getValueByName("CASHIER_PRINT_OPEN_BILL_TO_INVOICE");
        if (useForRaditya.equals("0")){
            if (printOpenBillgenerate.equals("1")) {
                if (billMain.getInvoiceNumber().toLowerCase().contains("x")) {
                    billMain.setInvoiceNumber(invoiceNumb);
                    billMain.setInvoiceNo(invoiceNumb);
                }
            } else {
                billMain.setInvoiceNumber(invoiceNumb);
                billMain.setInvoiceNo(invoiceNumb);
            }
        }


        billMain.setAmount(dppAmount);
        billMain.setReservasiId(reservationId);
        billMain.setPaidAmount(billMain.getAmount() + billMain.getTaxValue() + billMain.getServiceValue() - billMain.getDiscount());
        if (billMain.getDocType() == 0 && billMain.getTransctionType() == 1 && billMain.getTransactionStatus() == 1) {
            billMain.setPaidAmount(0);
        }
        oid = PstBillMain.updateExc(billMain);

        return oid;
    }

    public static long updatebillMain2(CashPayments1 cashPayments1,
            BillMain billMain, long oidCashPayment,
            DailyRate dailyRate, int cashierNumber,
            long cashCashier, long reservationId, double dppAmount) throws DBException {

        Date newDate = new Date();
        String guestName = "";
        ContactList contact = new ContactList();
        if (billMain.getCustomerId() != 0) {
            try {
                contact = PstContactList.fetchExc(billMain.getCustomerId());
                if (contact.getContactType() == 7) {
                    guestName = contact.getPersonName();
                } else {
                    guestName = contact.getCompName();
                }

            } catch (Exception ex) {
            }
        }
        String newInvoice = "";
        long oid = 0;
        int counter = PstCustomBillMain.getCounterBill(0, 0, "", "") + 1;

        String invoiceNumb = "";
        int counterPlus = 0;
        int countInvoiceNumber = 0;

//    do {
//        int countAfter = counter=counter + counterPlus;
//        String zeroCount = "000"+ countAfter;
//        String zeroCount2 = zeroCount.substring(zeroCount.length()-3); 
//        invoiceNumb = "00"+cashierNumber+"."+Formater.formatDate(new Date(), "yyyyMMdd")+"."+zeroCount2+"-REV";
//        
//        String where= ""+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NUMBER]+"="+invoiceNumb+"";
//        Vector list = PstBillMain.list(0, 0, where, "");
//        countInvoiceNumber = list.size();
//        counterPlus += 1; 
//    } while (countInvoiceNumber>0);
        String cashierNumberFormat = "00" + cashierNumber + "." + Formater.formatDate(new Date(), "yyyyMMdd");
        //invoiceNumb = generateInvoiceNumber(cashierNumberFormat);
        Vector listLastInvcNumber = PstBillMain.list(0, 1, "" + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '%" + cashierNumberFormat + "%' "
                + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " NOT LIKE '%C'", PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " DESC");
        if (listLastInvcNumber.size() > 0) {
            BillMain billMainInv = (BillMain) listLastInvcNumber.get(0);
            String[] number = billMainInv.getInvoiceNo().split("\\.");
            int newNumber = Integer.parseInt(number[2]) + 1;
            String lastNumber = "000" + newNumber;
            lastNumber = lastNumber.substring(lastNumber.length() - 3);
            invoiceNumb = cashierNumberFormat + "." + lastNumber;
        } else {
            invoiceNumb = cashierNumberFormat + ".001";
        }
        invoiceNumb = invoiceNumb + "-REV";

        billMain.setGuestName(guestName);
        billMain.setTransactionStatus(0);
        //billMain.setBillDate(newDate);
        billMain.setCashCashierId(cashCashier);
        String printOpenBillGenerateInvoice = PstSystemProperty.getValueByName("CASHIER_PRINT_OPEN_BILL_TO_INVOICE");
        if (!printOpenBillGenerateInvoice.equals("1")) {
            billMain.setInvoiceNumber(invoiceNumb);
            billMain.setInvoiceNo(invoiceNumb);
        }
        billMain.setAmount(dppAmount);
        billMain.setReservasiId(reservationId);
        billMain.setPaidAmount(billMain.getAmount() + billMain.getTaxValue() + billMain.getServiceValue() - billMain.getDiscount());

        oid = PstBillMain.updateExc(billMain);

        return oid;
    }

//INSERT MATERIAL COSTING
    public static long updateMatCosting(BillMain billMain, Date dateNow,
            String memberName, String costingCode, int counter, long cashCashier,
            long creditMemberId, PaymentSystem paymentSystem, Location location) {

        long oidMatCosting = 0;
        MatCosting matCosting = new MatCosting();
        matCosting.setLocationId(billMain.getLocationId());
        matCosting.setLocationType(location.getType());
        matCosting.setCostingDate(new Date());
        matCosting.setRemark(memberName);
        matCosting.setCostingCode(costingCode);
        matCosting.setCostingCodeCounter(counter);
        matCosting.setCostingTo(billMain.getLocationId());
        matCosting.setInvoiceSupplier("");
        matCosting.setTransferStatus(0);
        matCosting.setCashCashierId(cashCashier);
        matCosting.setEnableStockFisik(0);
        matCosting.setContactId(creditMemberId);
        matCosting.setCostingId(paymentSystem.getCostingTo());
        matCosting.setInvoiceSupplier(String.valueOf(billMain.getOID()));
        matCosting.setReservationId(billMain.getReservasiId());
        try {
            oidMatCosting = PstMatCosting.insertExc(matCosting);
        } catch (Exception ex) {
            oidMatCosting = 0;
        }

        return oidMatCosting;
    }

//UPDATE MATERIAL COSTING ITEM
    public static void updateMatCostingItem(long oidMatCosting,
            Billdetail billdetail, double hpp) {
        MatCostingItem matCostingItem = new MatCostingItem();
        matCostingItem.setCostingMaterialId(oidMatCosting);
        matCostingItem.setMaterialId(billdetail.getMaterialId());
        matCostingItem.setUnitId(billdetail.getUnitId());
        matCostingItem.setQty(billdetail.getQty());
        matCostingItem.setHpp(hpp);
        matCostingItem.setResidueQty(billdetail.getQty());
        matCostingItem.setBalanceQty(0);
        matCostingItem.setQtyComposite(0);
        matCostingItem.setParentId(0);
        matCostingItem.setSpesialNote(billdetail.getItemName());
        try {
            long oidMatCostItem = PstMatCostingItem.insertExc(matCostingItem);
        } catch (Exception ex) {

        }

    }

//UPDATE MAT COSTING ITEM COMPOSIT
    public static void updateMatCostingItemComposit(long oidMatCosting,
            Billdetail billdetail, double hpp) {
        MatCostingItem matCostingItem = new MatCostingItem();
        matCostingItem.setCostingMaterialId(oidMatCosting);
        matCostingItem.setMaterialId(billdetail.getMaterialId());
        matCostingItem.setUnitId(billdetail.getUnitId());
        matCostingItem.setQty(0);
        matCostingItem.setHpp(hpp);
        matCostingItem.setResidueQty(billdetail.getQty());
        matCostingItem.setBalanceQty(0);
        matCostingItem.setQtyComposite(billdetail.getQty());
        matCostingItem.setParentId(0);
        matCostingItem.setSpesialNote(billdetail.getItemName());
        try {
            long oidMatCostItem = PstMatCostingItem.insertExc(matCostingItem);
        } catch (Exception ex) {

        }

    }

//UPDATE COSTING COMPOSIT
    public static void updateMatCostingComposit(long oidMatCosting,
            MaterialComposit materialComposit, Billdetail billdetail) {
        MatCostingItem matCostingItem = new MatCostingItem();

        //GET MATERIAL
        Material material;
        try {
            material = PstMaterial.fetchExc(materialComposit.getMaterialComposerId());
        } catch (Exception ex) {
            material = new Material();
        }

        matCostingItem.setCostingMaterialId(oidMatCosting);
        matCostingItem.setMaterialId(materialComposit.getMaterialComposerId());
        matCostingItem.setUnitId(materialComposit.getUnitId());

        double qtyAll = billdetail.getQty() * materialComposit.getQty();
        matCostingItem.setQty(qtyAll);
        matCostingItem.setHpp(material.getAveragePrice());//UNKNOWN MUST BE ENTRY *temp data default 0
        matCostingItem.setResidueQty(materialComposit.getQty());
        matCostingItem.setBalanceQty(0);
        matCostingItem.setQtyComposite(0);
        matCostingItem.setParentId(materialComposit.getMaterialId());

        try {
            long oidComposit = PstMatCostingItem.insertExc(matCostingItem);
        } catch (Exception ex) {

        }

    }

//UPDATE BILL DETAIL
    public static void updateBillDetail(Billdetail billDetail, double hpp,
            BillMain billMain, double averageDisc, int taxInc) {
        double totalTax = 0;
        double totalService = 0;

        //UPDATE BY ARI 20160409
        Material material = new Material();
        if (billDetail.getMaterialId() != 0) {
            try {
                material = PstMaterial.fetchExc(billDetail.getMaterialId());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    ///////////////////////////

        try {

            if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                //perhitungan bayangan 
                double priceMaterial = 0;
                double priceMatAftDiscItem = 0;
                double priceMatAftDiscGlobal = 0;
                double totalPriceInTaxService = 0;
                double totalPriceExTaxService = 0;
                double divTaxService = 0;
                double totalPriceSave = 0;
                double totalServiceVal = 0;
                double totalTaxVal = 0;
                double discPerItem = 0;

                //pembagi tax service
                divTaxService = (100 + billMain.getTaxPercentage() + billMain.getServicePct()) / 100;
                //harga awal dari item
                priceMaterial = billDetail.getItemPrice();
                //harga awal kemudian dikurangi dengan disc per item
                priceMatAftDiscItem = priceMaterial - billDetail.getDisc();
                //selanjutnya harga setelah discount item dikurangi dengan disc global
                priceMatAftDiscGlobal = priceMatAftDiscItem - (priceMatAftDiscItem * billMain.getDiscPct() / 100);
            //setelah itu harga setelah disc global dikalikan jumlah item yang dibeli untuk mendapat harga total
                //total price include tax service
                totalPriceInTaxService = priceMatAftDiscGlobal * billDetail.getQty();
                //mendapatkan total price exclude tax service
                totalPriceExTaxService = (priceMatAftDiscGlobal * billDetail.getQty()) / divTaxService;
                //mendapatkan total price yang akan di save
                totalPriceSave = totalPriceExTaxService + ((priceMatAftDiscItem * billMain.getDiscPct() / 100) * billDetail.getQty());
                //mendapatkan nila tax yang akan disimpan, yaitu diambil dari total Price yang exclude tax service
                totalTaxVal = billMain.getTaxPercentage() / 100 * totalPriceExTaxService;
                //mendapatkan nila service yang akan disimpan, yaitu diambil dari total Price yang exclude tax service
                totalServiceVal = billMain.getServicePct() / 100 * totalPriceExTaxService;

                //set object
                billDetail.setTotalSvc(totalServiceVal);
                billDetail.setTotalTax(totalTaxVal);
                billDetail.setTotalPrice(totalPriceSave);
                billDetail.setDiscGlobal((priceMatAftDiscItem * billMain.getDiscPct() / 100) * billDetail.getQty()); //DIRUBAH AGAR DIJUMLAHKAN DENGAN QUANTITY
                billDetail.setCost(material.getAveragePrice());
                billDetail.setItemPriceStock(material.getAveragePrice());
                billDetail.setTotalCost(material.getAveragePrice() * billDetail.getQty());
                /////////////////////////////

                long oidBillDetail = PstBillDetail.updateExc(billDetail);

            } else {
                totalTax = hpp * (billMain.getTaxPercentage() / 100);
                totalService = hpp * (billMain.getServicePct() / 100);

                /*if(taxInc == PstBillMain.INC_CHANGEABLE && taxInc == PstBillMain.INC_NOT_CHANGEABLE){
                 hpp = hpp;//-totalTax-totalService
                 }else{
                 hpp = hpp;
                 }*/
                hpp = hpp;
                billDetail.setTotalSvc(totalService * billDetail.getQty());
                billDetail.setTotalTax(totalTax * billDetail.getQty());
                //billDetail.setDiscGlobal(averageDisc);

                billDetail.setTotalPrice(hpp * billDetail.getQty());
                double discToBe = ((billDetail.getTotalPrice() * billMain.getDiscPct() / 100) * billDetail.getQty());
                billDetail.setDiscGlobal(discToBe);
                //UPDATE BY ARI 20160409
                billDetail.setCost(material.getAveragePrice());
                billDetail.setItemPriceStock(material.getAveragePrice());
                billDetail.setTotalCost(material.getAveragePrice() * billDetail.getQty());
                /////////////////////////////

                long oidBillDetail = PstBillDetail.updateExc(billDetail);
            }

        } catch (Exception ex) {

        }
    }

//UPDATE TABLE
    public static long updateTable(CashPayments1 cashPayments1,
            BillMain billMain, long oidCashPayment,
            DailyRate dailyRate) throws DBException {

        long oid = 0;
        //UPDATE TABLE
        try {
            // cek ada berapa pax di bill tersebut
            String whereTable = PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_ID] + "='" + billMain.getTableId() + "'";
            int totalPaxFromTable = PstBillMain.getSumPaxOpenBillFromTable(whereTable);
            //cek status meja apakah masih half atau sudah penuh
            TableRoom tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
            int jumlah_akhir = 0;
            int statusTablePax = 0;
            int statusTableCondition = 0;
            jumlah_akhir = tableRoom.getCapacity() - totalPaxFromTable;
            if (jumlah_akhir == 0 || jumlah_akhir < 0) {
                statusTablePax = PstTableRoom.TABLE_STATUS_FULL;
                statusTableCondition = PstTableRoom.TABLE_CONDITION_DIRTY;
            } else {
                if (totalPaxFromTable == 0) {
                    statusTablePax = PstTableRoom.TABLE_STATUS_FREE;
                    statusTableCondition = PstTableRoom.TABLE_CONDITION_DIRTY;
                } else {
                    statusTableCondition = PstTableRoom.TABLE_CONDITION_DIRTY;
                    statusTablePax = PstTableRoom.TABLE_STATUS_HALF;
                }
            }

            try {
                //update kondisi table
                PstTableRoom.updateStatusCapacitynCondition(billMain.getTableId(), statusTablePax, statusTableCondition);
            } catch (Exception ex) {

            }

        } catch (Exception ex) {

        }

        return oid;
    }

//UPDATE VOUCHER PAYMENT
    public static long updateVoucher(CashPayments1 cashPayments1,
            BillMain billMain, long oidCashPayment,
            DailyRate dailyRate,
            HttpServletRequest request,
            long voucherId) throws DBException {

        long oid = 0;
        //UPDATE TABLE
        try {
            PaymentSystem paymentSystem = PstPaymentSystem.fetchExc(cashPayments1.getPaymentType());

            if (paymentSystem.getPaymentType() == 4) {
                Voucher voucher = PstVoucher.fetchExc(voucherId);
                CashCreditCard cashCreditCard = new CashCreditCard();

                voucher.setVoucherStatus(2);
                oid = PstVoucher.updateExc(voucher);

                cashCreditCard.setCcNumber(voucher.getVoucherNo());
                cashCreditCard.setPaymentId(oidCashPayment);
                oid = PstCashCreditCard.insertExc(cashCreditCard);
            } else if (paymentSystem.getPaymentType() == 5) {
                Voucher voucher = PstVoucher.fetchExc(voucherId);
                CashCreditCard cashCreditCard = new CashCreditCard();

                voucher.setVoucherStatus(5);
                oid = PstVoucher.updateExc(voucher);
            }

        } catch (Exception ex) {

        }

        return oid;
    }

    /**
     *
     * @param customerID
     * @param amountRp
     * @param amountDollar
     * @param loginId
     * @param dailyRate
     * @param billMain
     * @param request
     * @param cashPayments1
     * @param customerType
     * @return update opie-eyek 20151205 kwon yuri birthday - set amount yang di
     * set adalah amount setelah jumlah bayar - return - set cash bill main agar
     * di report muncul
     */
    public static long insertIntegrationHanoman(long customerID, double amountRp,
            double amountDollar, long loginId, double dailyRate, BillMain billMain,
            HttpServletRequest request, CashPayments1 cashPayments1, String customerType, int purposePayment) {

        int countMultiPaymentTemp = 0;
        try {
            countMultiPaymentTemp = FRMQueryString.requestInt(request, "countMultiPayment");
        } catch (Exception e) {
            countMultiPaymentTemp = 0;
        }
        if (countMultiPaymentTemp == 0) {
            String ccName = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]);
            String ccNumber = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]);
            String expiredDate = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]);
            String debitBank = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]);

            Location location;
            AppUser appUser;
            try {
                location = PstLocation.fetchExc(billMain.getLocationId());
                appUser = PstAppUser.fetch(loginId);
            } catch (Exception ex) {
                location = new Location();
                appUser = new AppUser();
            }
            PaymentRec paymentRec = new PaymentRec();
            PaymentRecSplit paymentRecSplit = new PaymentRecSplit();

            Date dateNow = Formater.reFormatDate(new Date(), "yyyy-MM-dd");
            String dateNumber = Formater.formatDate(new Date(), "yyyyMMdd");

            Vector listDataReservation = PstCustomBillMain.listInHouseGuestPayment(0, 0,
                    "(m." + PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID] + "='" + customerID + "' "
                    + "OR n." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + "='" + customerID + "')", "");

            ContactList contactList;
            Reservation reservation;
            if (listDataReservation.size() != 0) {
                Vector listReservation = (Vector) listDataReservation.get(0);
                if (listReservation.size() != 0) {
                    contactList = (ContactList) listReservation.get(0);
                    reservation = (Reservation) listReservation.get(1);
                } else {
                    contactList = new ContactList();
                    reservation = new Reservation();
                }
            } else {
                contactList = new ContactList();
                reservation = new Reservation();
            }

            int counter = PstPaymentRec.getCount("");
            String displayCounter = "";
            String zeroLoop = "";
            for (int i = 1; i <= 4 - String.valueOf(counter).length(); i++) {
                zeroLoop += "0";
            }

            String recNumber = "CASH-" + location.getCode() + "-" + dateNumber + "-" + zeroLoop + "" + counter;

            if (customerType.indexOf("#") != -1) {
                paymentRec.setReservationId(reservation.getOID());
                paymentRecSplit.setReservationId(reservation.getOID());
            } else {
                paymentRec.setReservationId(0);
                paymentRecSplit.setReservationId(0);
            }

            amountRp = cashPayments1.getAmount() - cashPayments1.getAmountReturn();

            String currencyId = String.valueOf(cashPayments1.getCurrencyId());

            //PstStandartRate.getStandardRate();
            double rate = PstDailyRate.getCurrentDailyRateSales(2);
            if (amountRp > 0) {
                amountDollar = amountRp / rate;
            } else {
                amountDollar = 0;
            }

            paymentRec.setPaymentDate(dateNow);
            paymentRec.setAmountRp(amountRp);
            paymentRec.setAmount$(amountDollar);
            paymentRec.setReceivedBy(appUser.getLoginId());
            paymentRec.setExchangeRate(dailyRate);
            paymentRec.setRecNumber(recNumber);
            paymentRec.setPaymentPurpose(purposePayment);
            paymentRec.setCcIssuingBank(debitBank);
            paymentRec.setCcNumber(ccNumber);
            paymentRec.setCcExpiredDate(Formater.formatDate(expiredDate, "yyyy-MM-dd"));
            paymentRec.setCcHolderName(ccName);
            if (cashPayments1.getCurrencyId() != 0) {
                paymentRec.setCurrencyUsed(cashPayments1.getCurrencyId());
            } else {
                paymentRec.setCurrencyUsed(cashPayments1.getCurrencyId());
            }

            paymentRec.setReceivedById(appUser.getOID());
            paymentRec.setBillMainId(billMain.getOID());
            paymentRec.setCoverBillingId(billMain.getOID());
            paymentRec.setCustomerId(customerID);
            paymentRec.setCashierId(billMain.getCashCashierId());
            paymentRec.setNumber(billMain.getInvoiceNumber());
            paymentRec.setCounter(counter);
            paymentRec.setStatus(0);
            paymentRec.setPaymentSystemId(cashPayments1.getPaymentType());
            paymentRec.setDueDate(dateNow);
            paymentRec.setDate(dateNow);

            try {
                long oid = PstPaymentRec.insertExc(paymentRec);

                paymentRecSplit.setPaymentRecId(oid);
                paymentRecSplit.setCoverBillingId(billMain.getOID());
                paymentRecSplit.setAmountRp(amountRp);
                paymentRecSplit.setAmountUsd(amountDollar);
                paymentRecSplit.setCurrencyUsed(cashPayments1.getCurrencyId());
                paymentRecSplit.setExchangeRate(dailyRate);
                paymentRecSplit.setCashierId(billMain.getCashCashierId());
                paymentRecSplit.setPaymentPurpose(0);
                paymentRecSplit.setPaymentDate(dateNow);
                paymentRecSplit.setNumber(billMain.getInvoiceNumber());
                paymentRecSplit.setCounter(counter);
                paymentRecSplit.setStatus(0);
                paymentRecSplit.setDate(dateNow);
                paymentRecSplit.setPaymentSystemId(cashPayments1.getPaymentType());
                paymentRecSplit.setDueDate(dateNow);

                oid = PstPaymentRecSplit.insertExc(paymentRecSplit);
            } catch (Exception ex) {
                long oid = 0;
            }
        } else {
            //GET CASH PAYMENT BY BILL MAIN ID
            String whereCashPayment = ""
                    + "" + PstCashPayment1.fieldNames[PstCashPayment1.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'";
            Vector listCashPayment = PstCashPayment1.list(0, 0, whereCashPayment, "");

            for (int i = 0; i < listCashPayment.size(); i++) {
                CashPayments1 cashPayments = new CashPayments1();
                cashPayments = (CashPayments1) listCashPayment.get(i);

                CashCreditCard entCashCreditCard = new CashCreditCard();
                try {
                    entCashCreditCard = PstCashCreditCard.fetchExc(cashPayments.getOID());
                } catch (Exception e) {
                }

                String ccName = entCashCreditCard.getCcName();
                String ccNumber = entCashCreditCard.getCcNumber();
                Date expiredDate = entCashCreditCard.getExpiredDate();
                String debitBank = entCashCreditCard.getDebitBankName();

                Location location = new Location();
                AppUser appUser = new AppUser();
                try {
                    location = PstLocation.fetchExc(billMain.getLocationId());
                    appUser = PstAppUser.fetch(loginId);
                } catch (Exception ex) {
                    location = new Location();
                    appUser = new AppUser();
                }
                PaymentRec paymentRec = new PaymentRec();
                PaymentRecSplit paymentRecSplit = new PaymentRecSplit();

                Date dateNow = Formater.reFormatDate(new Date(), "yyyy-MM-dd");
                String dateNumber = Formater.formatDate(new Date(), "yyyyMMdd");

                Vector listDataReservation = PstCustomBillMain.listInHouseGuestPayment(0, 0,
                        "(m." + PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID] + "='" + customerID + "' "
                        + "OR n." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + "='" + customerID + "')", "");

                ContactList contactList;
                Reservation reservation;
                if (listDataReservation.size() != 0) {
                    Vector listReservation = (Vector) listDataReservation.get(0);
                    if (listReservation.size() != 0) {
                        contactList = (ContactList) listReservation.get(0);
                        reservation = (Reservation) listReservation.get(1);
                    } else {
                        contactList = new ContactList();
                        reservation = new Reservation();
                    }
                } else {
                    contactList = new ContactList();
                    reservation = new Reservation();
                }

                int counter = PstPaymentRec.getCount("");
                String displayCounter = "";
                String zeroLoop = "";
                for (int j = 1; j <= 4 - String.valueOf(counter).length(); j++) {
                    zeroLoop += "0";
                }

                String recNumber = "CASH-" + location.getCode() + "-" + dateNumber + "-" + zeroLoop + "" + counter;

                if (customerType.indexOf("#") != -1) {
                    paymentRec.setReservationId(reservation.getOID());
                    paymentRecSplit.setReservationId(reservation.getOID());
                } else {
                    paymentRec.setReservationId(0);
                    paymentRecSplit.setReservationId(0);
                }

                amountRp = cashPayments.getAmount() - cashPayments.getAmountReturn();

                String currencyId = String.valueOf(cashPayments.getCurrencyId());

                //PstStandartRate.getStandardRate();
                double rate = PstDailyRate.getCurrentDailyRateSales(2);
                if (amountRp > 0) {
                    amountDollar = amountRp / rate;
                } else {
                    amountDollar = 0;
                }

                paymentRec.setPaymentDate(dateNow);
                paymentRec.setAmountRp(amountRp);
                paymentRec.setAmount$(amountDollar);
                paymentRec.setReceivedBy(appUser.getLoginId());
                paymentRec.setExchangeRate(dailyRate);
                paymentRec.setRecNumber(recNumber);
                paymentRec.setPaymentPurpose(purposePayment);
                paymentRec.setCcIssuingBank(debitBank);
                paymentRec.setCcNumber(ccNumber);
                paymentRec.setCcExpiredDate(expiredDate);
                paymentRec.setCcHolderName(ccName);
                if (cashPayments.getCurrencyId() != 0) {
                    paymentRec.setCurrencyUsed(cashPayments1.getCurrencyId());
                } else {
                    paymentRec.setCurrencyUsed(cashPayments1.getCurrencyId());
                }

                paymentRec.setReceivedById(appUser.getOID());
                paymentRec.setBillMainId(billMain.getOID());
                paymentRec.setCoverBillingId(billMain.getOID());
                paymentRec.setCustomerId(customerID);
                paymentRec.setCashierId(billMain.getCashCashierId());
                paymentRec.setNumber(billMain.getInvoiceNumber());
                paymentRec.setCounter(counter);
                paymentRec.setStatus(0);
                paymentRec.setPaymentSystemId(cashPayments.getPaymentType());
                paymentRec.setDueDate(dateNow);
                paymentRec.setDate(dateNow);

                try {
                    long oid = PstPaymentRec.insertExc(paymentRec);
                } catch (Exception ex) {
                    long oid = 0;
                }
            }

        }

        return 0;
    }

//    public synchronized static String generateInvoiceNumber(String cashierNumber){
//        String invcNumber = "";
//        Vector listLastInvcNumber = PstBillMain.list(0, 1, ""+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" LIKE '%"+cashierNumber+"%' "
//                + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" NOT LIKE '%C'", PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" DESC");
//        if(listLastInvcNumber.size() > 0){
//            BillMain billMain = (BillMain) listLastInvcNumber.get(0);
//            String[] number = billMain.getInvoiceNo().split("\\.");
//            int newNumber = Integer.parseInt(number[2])+1;
//            String lastNumber = "000"+newNumber;
//            lastNumber = lastNumber.substring(lastNumber.length()-3);
//            invcNumber = cashierNumber+"."+lastNumber;
//        }else{
//            invcNumber = cashierNumber+".001";
//        }
//        return invcNumber;
//    }
    public synchronized void voucherCompliment(String memberName, String remarkFoc,
            Date dateNow, long cashCashier, long creditMemberId, PaymentSystem paymentSystem,
            Vector listBillDetail, int taxInc, double averageDisc) {
        //GET LOCATION, COUNTER & GENERATE COSTING CODE
        try {
            int counter = PstMatCosting.getCount("") + 1;
            Location location = PstLocation.fetchExc(billMain.getLocationId());
            String costingCode = "CSTSLS-" + location.getCode() + "-001-" + counter;

            /////INSERT MATERIAL COSTING
            String newRemark = "" + memberName + ";" + remarkFoc + ";" + billMain.getOID() + ";" + billMain.getInvoiceNumber();
            long oidMatCosting = updateMatCosting(billMain, dateNow, newRemark, costingCode,
                    counter, cashCashier, creditMemberId, paymentSystem, location);
            if (listBillDetail.size() != 0) {
                for (int i = 0; i < listBillDetail.size(); i++) {
                    Billdetail billdetail = (Billdetail) listBillDetail.get(i);
                    Vector listComposit = new Vector(1, 1);

                    if (billdetail.getMaterialType() == 1) {
                        listComposit = PstMaterialComposit.ListComponentComposit(billdetail.getMaterialId());
                    }

                    double hpp = 0;
                    if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {

                        hpp = (billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/) / ((billMain.getTaxPercentage() + billMain.getServicePct() + 100) / 100);
                    } else {
                        hpp = billdetail.getItemPrice() - (billdetail.getDisc()/*/billdetail.getQty()*/)/*-averageDisc*/;
                    }

                /////INSERT MATERIAL COSTING ITEM
                    updateBillDetail(billdetail, hpp, billMain, averageDisc, taxInc);

                    //HPP DIAMBIL DARI AVERAGE PRICE
                    Material entMaterial = new Material();
                    try {
                        entMaterial = PstMaterial.fetchExc(billdetail.getMaterialId());
                    } catch (Exception e) {
                    }
                    hpp = entMaterial.getAveragePrice();

                    if (listComposit.size() != 0) {
                        for (int iComposit = 0; iComposit < listComposit.size(); iComposit++) {
                            Vector listTemp = (Vector) listComposit.get(iComposit);
                            MaterialComposit materialComposit = new MaterialComposit();
                            if (listTemp.size() > 0) {
                                materialComposit = (MaterialComposit) listTemp.get(0);
                                updateMatCostingItemComposit(oidMatCosting, billdetail, hpp);
                            }

                            ////INSERT COSTING COMPOSIT
                            updateMatCostingComposit(oidMatCosting, materialComposit, billdetail);
                        }
                    } else {
                        Material entMaterialTemp = new Material();
                        try {
                            entMaterialTemp = PstMaterial.fetchExc(billdetail.getMaterialId());
                        } catch (Exception e) {
                        }
                        if (entMaterialTemp.getMaterialType() == 0) {
                            updateMatCostingItem(oidMatCosting, billdetail, hpp);
                        } else {
                            updateMatCostingItemComposit(oidMatCosting, billdetail, hpp);
                        }

                    }

                }
            }
        } catch (Exception ex) {

        }
    }

    public double checkDiscount(long appUserId, double discGlobal) {
        double result = 0;
        String getDataAssign = PstSystemProperty.getValueByName("CASHIER_USING_DISCOUNT_ASSIGN");
        int usingDisc = 0;
        if (getDataAssign.length() > 0) {
            try {
                usingDisc = Integer.parseInt(getDataAssign);
            } catch (Exception ex) {

            }
        }
        if (discGlobal > 0 && usingDisc == 1) {
            if (appUserId != 0) {
                try {
                    AppUser appUser = PstAppUser.fetch(appUserId);

                    //CHECK ASSIGN DISCOUNT
                    Vector listDiscount = PstAssignDiscount.list(0, 0, "" + PstAssignDiscount.fieldNames[PstAssignDiscount.FLD_EMPLOYEE_ID] + "='" + appUser.getEmployeeId() + "'", "");
                    if (listDiscount.size() > 0) {
                        for (int i = 0; i < listDiscount.size(); i++) {
                            AssignDiscount assignDiscount = (AssignDiscount) listDiscount.get(i);
                            if (discGlobal > assignDiscount.getMaxDisc()) {
                                return assignDiscount.getMaxDisc();
                            }
                        }
                    } else {
                        result = 1;
                    }

                } catch (Exception ex) {

                }
            }
        }
        return result;
    }
    
}
