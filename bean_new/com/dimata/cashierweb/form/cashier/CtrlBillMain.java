/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.form.cashier;

import com.dimata.cashierweb.ajax.cashier.TransactionCashierHandler;
import com.dimata.cashierweb.entity.admin.AppUser;
import com.dimata.cashierweb.entity.admin.PstAppUser;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain;
import com.dimata.cashierweb.entity.cashier.transaction.PstQueensLocation;
import com.dimata.cashierweb.entity.cashier.transaction.QueensLocation;
import com.dimata.cashierweb.entity.masterdata.PstTableRoom;
import com.dimata.cashierweb.entity.masterdata.TableRoom;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.payment.PstStandartRate;
import com.dimata.common.entity.payment.StandartRate;
import com.dimata.common.db.DBException;
import com.dimata.common.entity.system.PstSystemProperty;
import com.dimata.pos.entity.balance.CashCashier;
import com.dimata.pos.entity.balance.PstCashCashier;
import com.dimata.pos.entity.billing.BillMain;
import com.dimata.pos.entity.billing.Billdetail;
import com.dimata.pos.entity.billing.PstBillDetail;
import com.dimata.pos.entity.billing.PstBillDetailVoid;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.form.billing.FrmBillDetail;
import com.dimata.pos.form.billing.FrmBillMain;
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
public class CtrlBillMain extends Control implements I_Language {

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
    private BillMain entBillMain;
    private PstBillMain pstBillMain;
    private FrmBillMain frmBillMain;
    private long oidBillMove = 0;
    int language = LANGUAGE_DEFAULT;

    public CtrlBillMain(HttpServletRequest request) {
        msgString = "";
        entBillMain = new BillMain();
        try {
            pstBillMain = new PstBillMain(0);
        } catch (Exception e) {;
        }
        frmBillMain = new FrmBillMain(request, entBillMain);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
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

    public BillMain getBillMain() {
        return entBillMain;
    }

    public FrmBillMain getForm() {
        return frmBillMain;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public void setOidBillMove(long oidBill) {
        this.oidBillMove = oidBill;
    }

    public long getOidBillMove() {
        return oidBillMove;
    }

    public int actionMove(int cmd, long oidBillMain, HttpServletRequest request) {

        long cashCashier = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]);
        int cashierNumber = PstCustomBillMain.getCashierNumber(cashCashier);
        String billDetailId = FRMQueryString.requestString(request, FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID]);
        String billQty = FRMQueryString.requestString(request, FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]);
        String guestName = FRMQueryString.requestString(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME]);
        int paxNumber = FRMQueryString.requestInt(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER]);
        String billPrice = FRMQueryString.requestString(request, FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE]);
        String billDisc = FRMQueryString.requestString(request, FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC]);

        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidBillMain != 0) {
                    try {
                        entBillMain = PstBillMain.fetchExc(oidBillMain);

                    } catch (Exception exc) {
                    }
                }

                frmBillMain.requestEntityObject(entBillMain);

                if (frmBillMain.errorSize() > 0) {
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
                    try {
                        BillMain oldEntBillMain = PstBillMain.fetchExc(oidBillMain);

                        long oid = 0;
                        int counter = PstCustomBillMain.getCounterOpenBill(0, 0, "", "") + 1;
                        String zeroLoop = "";
                        for (int i = 1; i <= 6 - String.valueOf(counter).length(); i++) {
                            zeroLoop += "0";
                        }
                        AppUser ap = new AppUser();
                        String frontHead = "";
                        try {
                            ap = PstAppUser.fetch(oldEntBillMain.getAppUserSalesId());
                        } catch (Exception e) {
                        }
                        if (oldEntBillMain.getAppUserSalesId() != 0) {
                            frontHead = ap.getFullName();
                        } else {
                            frontHead = "Sales";
                        }

                        String invoiceNumb = "" + frontHead + "." + Formater.formatDate(new Date(), "yyyyMMdd") + "." + Formater.formatDate(new Date(), "kkmmss") + zeroLoop + counter + "X";

                        //if((oldEntBillMain.getPaxNumber() >= paxNumber || oldEntBillMain.getPaxNumber()==1)){
                        if (oldEntBillMain.getPaxNumber() == 1) {
                            paxNumber = 1;
                        } else if (oldEntBillMain.getPaxNumber() >= 1) {
                            paxNumber = oldEntBillMain.getPaxNumber() - paxNumber;
                        } else {
                            paxNumber = 0;
                        }
                        oldEntBillMain.setInvoiceNumber(invoiceNumb);
                        oldEntBillMain.setCashCashierId(cashCashier);
                        oldEntBillMain.setStatusInv(1);
                        oldEntBillMain.setTransactionStatus(1);
                        oldEntBillMain.setTransctionType(0);
                        oldEntBillMain.setDocType(0);
                        oldEntBillMain.setInvoiceNo(invoiceNumb);
                        oldEntBillMain.setGuestName(guestName);
                        oldEntBillMain.setPaxNumber(paxNumber);

                        String[] splits = billDetailId.split(",");
                        String[] splitsQty = billQty.split(",");
                        String[] splitsPrice = billPrice.split(",");
                        String[] splitsDisc = billDisc.split(",");

                        if (splits.length != 0) {
                            oid = pstBillMain.insertExc(oldEntBillMain);
                            this.oidBillMove = oid;
                            for (int i = 0; i < splits.length; i++) {
                                try {
                                    long oldOidBillDetail = Long.parseLong(splits[i]);
                                    Billdetail billdetail = PstBillDetail.fetchExc(oldOidBillDetail);
                                    double validQty = 0;
                                    double validPrice = 0;
                                    double validDisc = 0;
                                    double subTotal = 0;
                                    try {
                                        validQty = Double.parseDouble(splitsQty[i]);
                                    } catch (Exception ex) {
                                        validQty = 0;
                                    }

                                    try {
                                        validPrice = Double.parseDouble(splitsPrice[i]);
                                    } catch (Exception ex) {
                                        validPrice = 0;
                                    }

                                    try {
                                        validDisc = Double.parseDouble(splitsDisc[i]);
                                    } catch (Exception ex) {
                                        validDisc = 0;
                                    }

                                    double checkQty = billdetail.getQty() - validQty;
                                    long oldOidBillMain = billdetail.getBillMainId();
                                    billdetail.setBillMainId(oid);
                                    billdetail.setQty(validQty);
                                    billdetail.setItemPrice(validPrice);
                                    billdetail.setDisc(validDisc);
                                    billdetail.setStatus(3);
                                    //set subtotal
                                    subTotal = (validPrice - validDisc) * validQty;
                                    billdetail.setTotalPrice(subTotal);

                                    if (validQty > 0) {
                                        long oidBillDetal = PstBillDetail.insertExc(billdetail);
                                        if (checkQty <= 0) {
                                            long oidDelete = PstBillDetail.deleteExc(oldOidBillDetail);
                                            msgString = "[SUCCESS] The bill has been moved";
                                        } else {
                                            billdetail.setQty(checkQty);
                                            //set harga
                                            billdetail.setItemPrice(validPrice);
                                            billdetail.setDisc(validDisc);
                                            //set subtotal
                                            subTotal = (validPrice - validDisc) * checkQty;
                                            billdetail.setTotalPrice(subTotal);
                                            //set quantity
                                            billdetail.setBillMainId(oldOidBillMain);
                                            billdetail.setOID(oldOidBillDetail);
                                            oidBillDetal = PstBillDetail.updateExc(billdetail);
                                            msgString = "[SUCCESS] The bill has been moved";
                                        }
                                    }
                                    //command sementara masih bingung knp di buatkan else seperti ini
//                                        else{
//					    msgString = "[FAILED] No Bill Moved, Please Check Quantity or Pax Number";
//					}

                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }

                        } else {
                            msgString = "[FAILED] No Bill Moved, Please Check Quantity or Pax Number";
                        }
                        //}else{
                        // msgString = "[FAILED] The Pax Number Shoult not exceed "+oldEntBillMain.getPaxNumber()+" Pax";
                        //}

                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidBillMain != 0) {
                    try {
                        entBillMain = PstBillMain.fetchExc(oidBillMain);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidBillMain != 0) {
                    try {
                        entBillMain = PstBillMain.fetchExc(oidBillMain);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidBillMain != 0) {
                    try {
                        long oid = PstBillMain.deleteExc(oidBillMain);
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

    public synchronized int action(int cmd, long oidBillMain, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidBillMain != 0) {
                    try {
                        entBillMain = PstBillMain.fetchExc(oidBillMain);

                    } catch (Exception exc) {
                    }
                }
                String useProduction = PstSystemProperty.getValueByName("USE_PRODUCTION");
                long cashier = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]);
                frmBillMain.requestEntityObject(entBillMain);

                if (frmBillMain.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entBillMain.getOID() == 0) {
                    try {
                        Date dateNow = new Date();
                        String invoiceNumb = "";
                        int counter = PstCustomBillMain.getCounterOpenBill(0, 0, "", "") + 1;
                        String zeroLoop = "";
                        for (int i = 1; i <= 6 - String.valueOf(counter).length(); i++) {
                            zeroLoop += "0";
                        }

                        String useForRaditya = com.dimata.system.entity.PstSystemProperty.getValueByName("USE_FOR_RADITYA");
                        int cashierNumber = PstCustomBillMain.getCashierNumber(cashier);
                        if (useForRaditya.equals("1")) {
                            invoiceNumb = "00" + cashierNumber + "." + Formater.formatDate(new Date(), "yyyyMMdd") + ".00" + Formater.formatDate(new Date(), "ks");
                        } else {
                            invoiceNumb = "Sales." + Formater.formatDate(new Date(), "yyyyMMdd") + "." + Formater.formatDate(new Date(), "kkmmss") + zeroLoop + counter + "X";
                        }

                        QueensLocation queensLocation = PstQueensLocation.fetchExc(entBillMain.getLocationId());
                        StandartRate standartRate = PstStandartRate.fetchExc(queensLocation.getStandartRateId());

                        //PENGECEKAN APAH DATA BERASAL DARI TRANSACTION EDIT ATAU TIDAK
                        int cekTrans = 0;
                        try {
                            cekTrans = FRMQueryString.requestInt(request, "edittrans");
                        } catch (Exception e) {
                            cekTrans = 0;
                        }

                        //JIKA BERASAL DARI TRANSACTION EDIT, MAKA BILL DATE MENGGUNAKAN TANGGAL SESUAI CASH CASHIER
                        if (cekTrans == 1) {
                            try {
                                long cashCashierId = entBillMain.getCashCashierId();
                                CashCashier cashCashier = PstCashCashier.fetchExc(cashCashierId);
                                entBillMain.setBillDate(Formater.reFormatDate(cashCashier.getCashDate(), "yyyy-MM-dd kk:mm:ss"));
                            } catch (Exception e) {
                            }
                            //JIKA TIDAK, MAKA BILL DATE MENGGUNAKAN TANGGAL HARI INI  
                        } else {
                            entBillMain.setBillDate(Formater.reFormatDate(dateNow, "yyyy-MM-dd kk:mm:ss"));
                        }

                        entBillMain.setInvoiceNumber(invoiceNumb);
                        entBillMain.setInvoiceNo(invoiceNumb);
                        entBillMain.setInvoiceCounter(counter);
                        entBillMain.setDocType(0);
                        if (useProduction.equals("0")) {
                            entBillMain.setTransctionType(0);
                        }
                        entBillMain.setTransactionStatus(1);
                        entBillMain.setCurrencyId(standartRate.getCurrencyTypeId());
                        entBillMain.setRate(standartRate.getSellingRate());
                        entBillMain.setStockLocationId(entBillMain.getLocationId());
                        entBillMain.setStatusInv(1);
                        entBillMain.setDateTermOfPayment(Formater.reFormatDate(dateNow, "yyyy-MM-dd kk:mm:ss"));

                        //CEK UNTU RESERVATION ID DARI 
                        long oid = pstBillMain.insertExc(this.entBillMain);
                        try {
                            if (entBillMain.getTableId() != 0) {
                                TableRoom tableRoom = new TableRoom();
                                tableRoom = PstTableRoom.fetchExc(entBillMain.getTableId());
                                tableRoom.setStatusTable(1);
                                PstTableRoom.updateExc(tableRoom);
                            }
                        } catch (Exception e) {
                        }
                        msgString = "" + oid;
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
                        long oid = pstBillMain.updateExc(entBillMain);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidBillMain != 0) {
                    try {
                        entBillMain = PstBillMain.fetchExc(oidBillMain);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidBillMain != 0) {
                    try {
                        entBillMain = PstBillMain.fetchExc(oidBillMain);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidBillMain != 0) {
                    try {
                        long oid = PstBillMain.deleteExc(oidBillMain);
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

    public int actionObject(int cmd, BillMain billMain, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (billMain.getOID() != 0) {
                    try {
                        long oid = pstBillMain.updateExc(billMain);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                } else {
                    try {
                        Date dateNow = new Date();

                        int counter = PstCustomBillMain.getCounterOpenBill(0, 0, "", "") + 1;
                        String zeroLoop = "";
                        for (int i = 1; i <= 6 - String.valueOf(counter).length(); i++) {
                            zeroLoop += "0";
                        }
                        String invoiceNumb = "Sales." + Formater.formatDate(new Date(), "yyyyMMdd") + "." + Formater.formatDate(new Date(), "kkmmss") + zeroLoop + counter + "X";

                        QueensLocation queensLocation = PstQueensLocation.fetchExc(entBillMain.getLocationId());
                        StandartRate standartRate = PstStandartRate.fetchExc(queensLocation.getStandartRateId());

                        billMain.setBillDate(Formater.reFormatDate(dateNow, "yyyy-MM-dd kk:mm:ss"));
                        billMain.setInvoiceNumber(invoiceNumb);
                        billMain.setInvoiceNo(invoiceNumb);
                        billMain.setInvoiceCounter(counter);
                        billMain.setDocType(0);
                        billMain.setTransctionType(0);
                        billMain.setTransactionStatus(1);
                        billMain.setCurrencyId(standartRate.getCurrencyTypeId());
                        billMain.setRate(standartRate.getSellingRate());
                        billMain.setStockLocationId(entBillMain.getLocationId());
                        billMain.setStatusInv(1);
                        billMain.setDateTermOfPayment(Formater.reFormatDate(dateNow, "yyyy-MM-dd kk:mm:ss"));

                        long oid = pstBillMain.insertExc(billMain);
                        msgString = "" + oid;
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
            default:

        }
        return rsCode;
    }

    public int actionCancel(int cmd, long oidBillMain, HttpServletRequest request) {

        long cashCashier = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]);
        if (cashCashier == 0 && oidBillMain != 0) {
            BillMain entBillMainHelp = new BillMain();
            try {
                entBillMainHelp = PstBillMain.fetchExc(oidBillMain);
                cashCashier = entBillMainHelp.getCashCashierId();
            } catch (Exception e) {
            }
        }
        int cashierNumber = PstCustomBillMain.getCashierNumber(cashCashier);

        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidBillMain != 0) {
                    try {
                        entBillMain = PstBillMain.fetchExc(oidBillMain);

                    } catch (Exception exc) {
                    }
                }

                frmBillMain.requestEntityObject(entBillMain);

                if (frmBillMain.errorSize() > 0) {
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
                    try {
                        BillMain oldEntBillMain = PstBillMain.fetchExc(oidBillMain);
                        long oid = 0;
                        int counter = PstCustomBillMain.getCounterCancel(0, 0, "", "") + 1;
//			String zeroLoop = "";
//			for(int i = 1; i <= 3-String.valueOf(counter).length();i++){
//			    zeroLoop+="0";
//			}
//			String invoiceNumb = "00"+cashierNumber+"."+Formater.formatDate(new Date(), "yyyyMMdd")+"."+zeroLoop+counter+"C";
                        String invoiceNumb = "";
                        if (oldEntBillMain.getInvoiceNumber().toLowerCase().contains("x")) {
                            TransactionCashierHandler transactionCashierHandler = new TransactionCashierHandler();
                            String cashierNumberFormat = "00" + cashierNumber + "." + Formater.formatDate(new Date(), "yyyyMMdd");
                            //invoiceNumb = transactionCashierHandler.generateInvoiceNumber(cashierNumberFormat);
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
                        } else {
                            invoiceNumb = oldEntBillMain.getInvoiceNumber();
                        }

                        oldEntBillMain.setInvoiceNumber(invoiceNumb);
                        oldEntBillMain.setCashCashierId(cashCashier);
                        oldEntBillMain.setStatusInv(1);
                        oldEntBillMain.setTransactionStatus(2);
                        oldEntBillMain.setTransctionType(0);
                        oldEntBillMain.setDocType(0);
                        oldEntBillMain.setInvoiceNo(invoiceNumb);
                        oldEntBillMain.setNotes(entBillMain.getNotes());
                        oid = pstBillMain.updateExc(oldEntBillMain);
                        updateTable(oldEntBillMain);
                        msgString = "The bill has been canceled";
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidBillMain != 0) {
                    try {
                        entBillMain = PstBillMain.fetchExc(oidBillMain);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidBillMain != 0) {
                    try {
                        entBillMain = PstBillMain.fetchExc(oidBillMain);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidBillMain != 0) {
                    try {
                        long oid = PstBillMain.deleteExc(oidBillMain);
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

    //QUEEN TANDOOR
    public int actionUpdateItem2(int cmd, long oidBillDetail, int status, String note, int qytErr) {
        int sucsess = 0;
        switch (cmd) {
            case Command.DELETE:
                try {
                    Billdetail billdetail = PstBillDetail.fetchExc(oidBillDetail);
                    billdetail.setItemPrice(0);
                    billdetail.setDiscPct(0);
                    billdetail.setDisc(0);
                    billdetail.setTotalPrice(qytErr * billdetail.getItemPrice());
                    if (note != "") {
                        billdetail.setNote(billdetail.getNote() + " / " + note);
                    } else {
                        billdetail.setNote(billdetail.getNote());
                    }

                    long oid = PstBillDetail.updateExc(billdetail);
                } catch (Exception ex) {
                }
                break;

            default:
        }
        return sucsess;
    }

    //
    public int actionUpdateItem(int cmd, long oidBillDetail, int status, String note, int qytErr) {
        int sucsess = 0;
        switch (cmd) {
            case Command.DELETE:
                try {
                    Billdetail billdetail = PstBillDetail.fetchExc(oidBillDetail);
                    billdetail.setNote(note);
                    billdetail.setLengthFinishOrder(new Date());
                    long oid = PstBillDetailVoid.insertExc(billdetail);
                    if (oid != 0) {
                        long xxy = PstBillDetail.deleteExc(oidBillDetail);
                    }
                } catch (Exception ex) {
                }
                break;
            case Command.EDIT:
                try {
                    Billdetail billdetail = PstBillDetail.fetchExc(oidBillDetail);
                    billdetail.setQty(qytErr);
                    billdetail.setTotalPrice(qytErr * billdetail.getItemPrice());
                    if (note != "") {
                        billdetail.setNote(billdetail.getNote() + " / " + note);
                    } else {
                        billdetail.setNote(billdetail.getNote());
                    }

                    long oid = PstBillDetail.updateExc(billdetail);
                } catch (Exception ex) {
                }
                break;

            default:
        }
        return sucsess;
    }

    //UPDATE TABLE
    public static long updateTable(BillMain billMain) throws DBException {

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
}
