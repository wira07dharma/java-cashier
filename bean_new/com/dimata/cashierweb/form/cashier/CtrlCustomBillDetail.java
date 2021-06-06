/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.form.cashier;

import com.dimata.cashierweb.ajax.cashier.TransactionCashierHandler;
import com.dimata.cashierweb.entity.cashier.transaction.BillMainJSON;
import com.dimata.cashierweb.entity.masterdata.DiscountMapping;
import com.dimata.cashierweb.entity.masterdata.DiscountQtyMapping;
import com.dimata.cashierweb.entity.masterdata.Material;
import com.dimata.cashierweb.entity.masterdata.MemberGroup;
import com.dimata.cashierweb.entity.masterdata.MemberReg;
import com.dimata.cashierweb.entity.masterdata.PstDiscountMapping;
import com.dimata.cashierweb.entity.masterdata.PstDiscountQtyMapping;
import com.dimata.cashierweb.entity.masterdata.PstMaterial;
import com.dimata.cashierweb.entity.masterdata.PstMemberGroup;
import com.dimata.cashierweb.entity.masterdata.PstMemberReg;
import com.dimata.common.db.DBException;
import com.dimata.common.entity.custom.DataCustom;
import com.dimata.common.entity.custom.PstDataCustom;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.system.PstSystemProperty;
import com.dimata.hanoman.entity.masterdata.CustomePackBilling;
import com.dimata.hanoman.entity.masterdata.PstCustomePackBilling;
import com.dimata.pos.entity.billing.BillDetailCode;
import com.dimata.pos.entity.billing.BillMain;
import com.dimata.pos.entity.billing.Billdetail;
import com.dimata.pos.entity.billing.PstBillDetail;
import com.dimata.pos.entity.billing.PstBillDetailCode;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.form.billing.FrmBillDetail;
import com.dimata.pos.form.billing.FrmBillMain;
import com.dimata.pos.form.payment.FrmCashPayment;
import com.dimata.posbo.entity.masterdata.PstMaterialMappingType;
import com.dimata.posbo.entity.warehouse.MaterialStockCode;
import com.dimata.posbo.entity.warehouse.PstMaterialStockCode;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.qdep.system.I_DBExceptionInfo;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import com.dimata.util.lang.I_Language;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Ardiadi
 */
public class CtrlCustomBillDetail extends Control implements I_Language {

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
    private Billdetail entBillDetail;
    private PstBillDetail pstBillDetail;
    private FrmBillDetail frmBillDetail;
    private Material material;
    private BillMain billMain;
    int language = LANGUAGE_DEFAULT;

    public CtrlCustomBillDetail(HttpServletRequest request) {
        msgString = "";
        entBillDetail = new Billdetail();
        material = new Material();
        billMain = new BillMain();
        try {
            pstBillDetail = new PstBillDetail(0);
        } catch (Exception e) {;
        }
        frmBillDetail = new FrmBillDetail(request, entBillDetail);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
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

    public Billdetail getBillDetail() {
        return entBillDetail;
    }

    public FrmBillDetail getForm() {
        return frmBillDetail;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidBillDetail, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:
                if (oidBillDetail != 0) {
                    try {
                        entBillDetail = PstBillDetail.fetchExc(oidBillDetail);
                        material = PstMaterial.fetchExc(entBillDetail.getMaterialId());
                        billMain = PstBillMain.fetchExc(entBillDetail.getBillMainId());
                    } catch (Exception exc) {
                    }
                }

                frmBillDetail.requestEntityObject(entBillDetail);

                if (frmBillDetail.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                // GET PARAMETER
                int taxInc = FRMQueryString.requestInt(request, BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC]);
                double taxPct = FRMQueryString.requestDouble(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT]);
                double svcPct = FRMQueryString.requestDouble(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT]);
                int itemEdited = FRMQueryString.requestInt(request, "ITEM_EDITED_WHEN_ADD_NEW"); //add baru tapi di edit saat isi qty (tombol F12)

                // GET SERVICE, TAX,
                double taxVal = 0;
                double svcVal = 0;
                double amount = 0;
                double totalPrice = 0;
                double discGlobal = 0;
                long oidMaterialId = 0;
                
                //UPDATED BY DEWOK 20190205
                //PEMBERIAN DISKON DARI MAPPING MASTER DATA HANYA BERLAKU SAAT ITEM PERTAMA KALI DI INPUT
                if (oidBillDetail == 0 && itemEdited == 0) {
                    try {
                        billMain = PstBillMain.fetchExc(entBillDetail.getBillMainId());
                    } catch (Exception e) {
                        billMain = new BillMain();
                    }

                    if (entBillDetail.getMaterialId() == 0) {
                        oidMaterialId = material.getOID();
                    } else {
                        oidMaterialId = entBillDetail.getMaterialId();
                    }

                    long defaultDiscountTypeId = 0;
                    long defaultMemberTypeId = 0;
                    try {
                        Location l = PstLocation.fetchExc(billMain.getLocationId());
                        defaultDiscountTypeId = l.getDiscountTypeId();
                        defaultMemberTypeId = l.getMemberGroupId();
                        taxPct = l.getTaxPersen();
                        svcPct = l.getServicePersen();
                    } catch (Exception e) {
                    }

                    //CEK APAKAH BILL MEMILIKI MEMBER ID UNTUK DISKON TYPE YG BERLAKU
                    if (PstMemberReg.checkOID(billMain.getCustomerId())) {
                        //GUNAKAN SETTING DISCOUNT QTY DARI DISCOUNT TYPE YG ADA DI MEMBER
                        try {
                            MemberReg memberReg = PstMemberReg.fetchExc(billMain.getCustomerId());
                            MemberGroup memberGroup = PstMemberGroup.fetchExc(memberReg.getMemberGroupId());
                            if (memberGroup.getDiscountTypeId() > 0) {
                                defaultDiscountTypeId = memberGroup.getDiscountTypeId();
                            }
                        } catch (Exception e) {
                        }
                    } else {
                        //GUNAKAN SETTING DISCOUNT QTY DARI DISCOUNT TYPE DEFAULT DI MASTER LOCATION
                        try {
                            MemberGroup memberGroup = PstMemberGroup.fetchExc(defaultMemberTypeId);
                            if (memberGroup.getDiscountTypeId() > 0) {
                                defaultDiscountTypeId = memberGroup.getDiscountTypeId();
                            }
                        } catch (Exception e) {
                        }
                    }

                    //CARI DATA DISCOUNT
                    HashMap<String, String> discMap = getDataDiscount(oidMaterialId, entBillDetail.getItemPrice(), billMain.getLocationId(), billMain.getCurrencyId(), defaultDiscountTypeId, entBillDetail.getQty());
                    try {
                        entBillDetail.setDiscType(Integer.valueOf(discMap.get("DISCOUNT_TYPE")));
                        entBillDetail.setDiscPct(Double.valueOf(discMap.get("DISCOUNT_PCT")));
                        entBillDetail.setDisc(Double.valueOf(discMap.get("DISCOUNT_VAL")));
                    } catch (Exception e) {
                    }
                }

                // cek apakah ada parent atau tidak, jika ada maka harga di set 0
                long parentId = FRMQueryString.requestLong(request, "parentId");
                if (parentId != 0) {
                    entBillDetail.setItemPrice(0);
                    entBillDetail.setParentId(parentId);
                }

                if (entBillDetail.getCutomePackBillingId() != 0) {
                    try {
                        CustomePackBilling customePackBilling = PstCustomePackBilling.fetchExc(entBillDetail.getCutomePackBillingId());
                        if (customePackBilling.getDiscPct() > 0) {
                            entBillDetail.setDiscPct(customePackBilling.getDiscPct());
                        } else if (customePackBilling.getDisc() > 0 && customePackBilling.getPriceRp() > 0) {
                            double getDiscPct = (customePackBilling.getDisc() / customePackBilling.getPriceRp()) * 100;
                            entBillDetail.setDiscPct(getDiscPct);
                        } else if (customePackBilling.getDisc$() > 0 && customePackBilling.getPriceUsd() > 0) {
                            double getDiscPct = (customePackBilling.getDisc() / customePackBilling.getPriceUsd()) * 100;
                            entBillDetail.setDiscPct(getDiscPct);
                        }
                    } catch (Exception ex) {
                        double getDiscPct = 0;
                    }
                }

                if (entBillDetail.getOID() == 0) {
                    try {

                        Date lengthOfOrder = Formater.reFormatDate(new Date(), "yyyy-MM-dd kk:mm:ss");

                        Material m = PstMaterial.fetchExc(entBillDetail.getMaterialId());

                        switch (entBillDetail.getDiscType()) {
                            case PstDiscountQtyMapping.DISC_PERSEN_PER_ITEM_UNIT:
                                totalPrice = (entBillDetail.getItemPrice() - entBillDetail.getDisc()) * entBillDetail.getQty();
                                break;
                            case PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_UNIT:
                                totalPrice = (entBillDetail.getItemPrice() - entBillDetail.getDisc()) * entBillDetail.getQty();
                                break;
                            case PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_SKU:
                                totalPrice = (entBillDetail.getItemPrice() * entBillDetail.getQty()) - entBillDetail.getDisc();
                                break;
                        }
                        
                        if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                            amount = totalPrice / ((taxPct + svcPct + 100) / 100);
                            svcVal = amount * (svcPct / 100);
                            taxVal = amount * (taxPct / 100);
                            totalPrice = amount;
                        } else {
                            svcVal = entBillDetail.getTotalSvc();
                            taxVal = entBillDetail.getTotalTax();
                        }

                        entBillDetail.setUnitId(m.getDefaultStockUnitId());
                        entBillDetail.setTotalPrice(totalPrice);
                        entBillDetail.setSku(m.getSku());
                        entBillDetail.setCost(m.getAveragePrice());
                        entBillDetail.setLengthOrder(lengthOfOrder);
                        entBillDetail.setStatusPrint(0);
                        entBillDetail.setTotalSvc(svcVal);
                        entBillDetail.setTotalTax(taxVal);
                        
                        //ADDED BY DEWOK 20191113, FOR DISPENDA TABANAN
                        //cek tipe member group guide price
                        if (TransactionCashierHandler.checkMemberGuidePrice(billMain.getOID())) {
                            entBillDetail.setGuidePrice(entBillDetail.getItemPrice());
                        }

                        long oid = pstBillDetail.insertExc(this.entBillDetail);

                        //Gunadi, add serial
                        if (m.getRequiredSerialNumber() == 1) {
                            String[] serial = request.getParameterValues("SERIAL_CODE");
                            for (int i = 0; i < serial.length; i++) {
                                String whereStockCode = PstMaterialStockCode.fieldNames[PstMaterialStockCode.FLD_STOCK_CODE] + "=" + serial[i]
                                        + " AND " + PstMaterialStockCode.fieldNames[PstMaterialStockCode.FLD_STOCK_STATUS] + "!=" + PstMaterialStockCode.FLD_STOCK_STATUS_SOLED;
                                Vector listStock = PstMaterialStockCode.list(0, 0, whereStockCode, "");
                                double stockValue = 0;
                                if (listStock.size() > 0) {
                                    MaterialStockCode matStockCode = (MaterialStockCode) listStock.get(0);
                                    stockValue = matStockCode.getStockValue();

                                    BillDetailCode billCode = new BillDetailCode();
                                    billCode.setSaleItemId(oid);
                                    billCode.setStockCode(serial[i]);
                                    billCode.setValue(stockValue);
                                    try {
                                        PstBillDetailCode.insertExc(billCode);
                                    } catch (Exception exc) {
                                        System.out.println(exc.toString());
                                    }

                                    matStockCode.setStockStatus(PstMaterialStockCode.FLD_STOCK_STATUS_SOLED);
                                    try {
                                        PstMaterialStockCode.updateExc(matStockCode);
                                    } catch (Exception exc) {
                                        System.out.println(exc.toString());
                                    }
                                }
                            }
                        }

                        try {
                            String promotionType = PstSystemProperty.getValueByName("CASHIER_PROMOTION_GROUP_TYPE");
                            long oidMapping = PstMaterialMappingType.getSelectedTypeId(Integer.valueOf(promotionType), entBillDetail.getMaterialId());
                            if (oidMapping > 0) {
                                DataCustom dataCustom = PstDataCustom.getDataCustom(oid, "" + promotionType, "bill_detail_map_type");
                                if (dataCustom.getOID() > 0) {
                                    dataCustom.setDataValue("" + oidMapping);
                                    PstDataCustom.updateExc(dataCustom);
                                } else {
                                    dataCustom.setDataName("bill_detail_map_type");
                                    dataCustom.setDataValue("" + oidMapping);
                                    dataCustom.setLink("" + promotionType);
                                    dataCustom.setOwnerId(oid);
                                    PstDataCustom.insertExc(dataCustom);
                                }
                            }
                        } catch (Exception exc) {
                        }

                        msgString = "SUCCESS";
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {
                    try {
                        Billdetail entBilldetail2 = PstBillDetail.fetchExc(oidBillDetail);
                        entBilldetail2.setDiscType(entBillDetail.getDiscType());
                        switch (entBillDetail.getDiscType()) {
                            case PstDiscountQtyMapping.DISC_PERSEN_PER_ITEM_UNIT:
                                totalPrice = (entBillDetail.getItemPrice() - entBillDetail.getDisc()) * entBillDetail.getQty();
                                break;
                            case PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_UNIT:
                                totalPrice = (entBillDetail.getItemPrice() - entBillDetail.getDisc()) * entBillDetail.getQty();
                                break;
                            case PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_SKU:
                                totalPrice = (entBillDetail.getItemPrice() * entBillDetail.getQty()) - entBillDetail.getDisc();
                                break;
                        }

                        if (billMain.getDiscount() > 0) {
                            discGlobal = billMain.getDiscount() / entBilldetail2.getQty();
                        } else {
                            discGlobal = 0;
                        }

                        //AFTER DISCOUNT
                        totalPrice = totalPrice - entBillDetail.getDiscGlobal();

                        if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                            amount = totalPrice / ((taxPct + svcPct + 100) / 100);
                            svcVal = amount * (svcPct / 100);
                            taxVal = amount * (taxPct / 100);
                            totalPrice = amount;
                        } else {
                            svcVal = entBillDetail.getTotalSvc();
                            taxVal = entBillDetail.getTotalTax();
                        }
                        entBilldetail2.setQty(entBillDetail.getQty());
                        entBilldetail2.setItemPrice(entBillDetail.getItemPrice());
                        entBilldetail2.setDisc(entBillDetail.getDisc());
                        entBilldetail2.setTotalPrice(totalPrice);
                        entBilldetail2.setDiscPct(entBillDetail.getDiscPct());
                        entBilldetail2.setQtyStock(entBillDetail.getQty());
                        entBilldetail2.setItemPriceStock(entBillDetail.getItemPrice());
                        entBilldetail2.setTotalCost(entBillDetail.getQty() * material.getAveragePrice());
                        entBilldetail2.setDiscGlobal(discGlobal);
                        entBilldetail2.setCost(material.getAveragePrice());
                        entBilldetail2.setNote(entBillDetail.getNote());
                        entBilldetail2.setTotalSvc(svcVal);
                        entBilldetail2.setTotalTax(taxVal);
                        entBilldetail2.setItemName(entBillDetail.getItemName());
                        entBilldetail2.setGuidePrice(entBillDetail.getGuidePrice());
                        
                        long oid = pstBillDetail.updateExc(entBilldetail2);
                        msgString = "" + entBilldetail2.getBillMainId();
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
                break;

            case Command.EDIT:
                if (oidBillDetail != 0) {
                    try {
                        entBillDetail = PstBillDetail.fetchExc(oidBillDetail);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.ASK:
                if (oidBillDetail != 0) {
                    try {
                        entBillDetail = PstBillDetail.fetchExc(oidBillDetail);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;

            case Command.DELETE:
                if (oidBillDetail != 0) {
                    try {
                        long oid = PstBillDetail.deleteExc(oidBillDetail);

                        if (oid != 0) {
                            //msgString = "SUCCESS";
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

    public int actionObject(int cmd, Billdetail entBillDetail, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;

            case Command.SAVE:

                //GET PARAMETER
                int taxInc = FRMQueryString.requestInt(request, BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC]);
                double taxPct = FRMQueryString.requestDouble(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT]);
                double svcPct = FRMQueryString.requestDouble(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT]);

                //GET SERVICE, TAX,
                double discPct = 0;
                double discVal = 0;
                double serviceVal = 0;
                double taxVal = 0;
                double svcVal = 0;
                double amount = 0;
                double totalPrice = 0;
                double discGlobal = 0;
                long oidMaterialId = 0;
                Billdetail entBilldetailTemp = new Billdetail();

                double discValueTotal = 0;
                double discPctTotal = 0;
                DiscountQtyMapping entDiscountQtyMapping = new DiscountQtyMapping();

                //cek apakah material ini mendapatkan discount akumulasi atau tidak
                if (entBillDetail.getOID() == 0) {
                    try {
                        billMain = PstBillMain.fetchExc(entBillDetail.getBillMainId());
                    } catch (Exception e) {
                        billMain = new BillMain();
                    }
                }

                if (entBillDetail.getMaterialId() == 0) {
                    oidMaterialId = material.getOID();
                } else {
                    oidMaterialId = entBillDetail.getMaterialId();
                }

                String whereDiscAcc = ""
                        + "" + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_MATERIAL_ID] + "=" + oidMaterialId + ""
                        + " AND " + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_LOCATION_ID] + "=" + billMain.getLocationId() + ""
                        + " AND " + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_CURRENCY_TYPE_ID] + "=" + billMain.getCurrencyId() + ""
                        + " AND (" + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_START_QTY] + "<=" + entBillDetail.getQty() + ""
                        + " AND " + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_TO_QTY] + ">=" + entBillDetail.getQty() + ")";

                Vector listDiscAcc = PstDiscountQtyMapping.list(0, 0, whereDiscAcc, "");
                if (listDiscAcc.size() > 0) {
                    entDiscountQtyMapping = (DiscountQtyMapping) listDiscAcc.get(0);
                    if (entDiscountQtyMapping.getDiscountType() == PstDiscountQtyMapping.DISC_PERSEN_PER_ITEM_UNIT) {
                        entBillDetail.setDiscType(PstDiscountQtyMapping.DISC_PERSEN_PER_ITEM_UNIT);
                        discPctTotal = entBillDetail.getDiscPct() + entDiscountQtyMapping.getDiscountValue();
                        entBillDetail.setDiscPct(discPctTotal);
                    } else if (entDiscountQtyMapping.getDiscountType() == PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_UNIT) {
                        double remainQty = entBillDetail.getQty() - entDiscountQtyMapping.getDiscountValue();
                        double priceTotalAfterDisc = remainQty * entBillDetail.getItemPrice();
                        double singlePrice = priceTotalAfterDisc / entBillDetail.getQty();
                        double discTemp = (entBillDetail.getItemPrice() - singlePrice) / entBillDetail.getItemPrice() * 100;
                        entBillDetail.setDiscType(PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_UNIT);
                        discPctTotal = entBillDetail.getDiscPct() + discTemp;
                        entBillDetail.setDiscPct(discPctTotal);
                    }
                }

                if (entBillDetail.getOID() == 0) {
                    try {

                        Date lengthOfOrder = Formater.reFormatDate(new Date(), "yyyy-MM-dd kk:mm:ss");

                        Material material = PstMaterial.fetchExc(entBillDetail.getMaterialId());
                        discValueTotal = entBillDetail.getItemPrice() * entBillDetail.getDiscPct() / 100;
                        totalPrice = (entBillDetail.getItemPrice() - discValueTotal) * entBillDetail.getQty();
                        //totalPrice = entBillDetail.getQty()*(entBillDetail.getItemPrice()*entBillDetail.);
                        entBillDetail.setUnitId(material.getDefaultStockUnitId());
                        entBillDetail.setDisc(discValueTotal);
                        entBillDetail.setTotalPrice(totalPrice);
                        entBillDetail.setSku(material.getSku());
                        entBillDetail.setLengthOrder(lengthOfOrder);
                        entBillDetail.setStatusPrint(0);

                        long oid = pstBillDetail.insertExc(entBillDetail);
                        msgString = "SUCCESS";
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

    private double getValueByOID(Hashtable<String, String> list, long oId) {
        double result = 0;
        String temp = "";
        temp = list.get(String.valueOf(oId));
        result = Double.parseDouble(temp);
        return result;
    }

    public int actionJoin(int cmd, HttpServletRequest request) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        long oid = 0;
        switch (cmd) {
            case Command.ADD:
                break;
            case Command.CONFIRM:
                String whereDetail = "";
                long userId = FRMQueryString.requestLong(request, "userid");
                long oidBillMain = FRMQueryString.requestLong(request, "" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] + "");
                whereDetail = "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "=" + oidBillMain + " "
                        + "AND " + PstBillDetail.fieldNames[PstBillDetail.FLD_STATUS_PRINT] + "=0";
                Vector listDetail = PstBillDetail.list(0, 0, whereDetail, "");
                for (int x = 0; x < listDetail.size(); x++) {
                    Billdetail billDetail = (Billdetail) listDetail.get(x);
                    billDetail.setStatusPrint(1);
                    billDetail.setUserOrderId(userId);
                    billDetail.setUserIdSentToChecker(userId);
                    billDetail.setDateSentToChecker(new Date());
                    try {
                        oid = PstBillDetail.updateExc(billDetail);
                        rsCode = RSLT_OK;
                    } catch (Exception e) {
                        rsCode = RSLT_UNKNOWN_ERROR;
                    }

                }
                break;

            case Command.SAVE:
                String whereBillDetail = "";

                Long oidBillDestination = FRMQueryString.requestLong(request, "oidBillDestination");
                Long oidBillSource = FRMQueryString.requestLong(request, "oidBillSource");
                String valueTotal = FRMQueryString.requestString(request, "valueTotal");
                String[] temp = valueTotal.split("-");
                //masukkan nilai hastable 
                Hashtable<String, String> listTemp = new Hashtable<String, String>();
                for (int j = 0; j < temp.length; j++) {
                    String[] val = temp[j].split(":");
                    listTemp.put(val[0], val[1]);
                }

                whereBillDetail = "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "=" + oidBillSource + "";
                Vector listBillDetail = PstBillDetail.list(0, 0, whereBillDetail, "");

                for (int i = 0; i < listBillDetail.size(); i++) {
                    long oidDetilChange = 0;
                    double qtyDetilChange = 0;
                    Billdetail entBillDetail = (Billdetail) listBillDetail.get(i);
                    oidDetilChange = entBillDetail.getOID();
                    qtyDetilChange = getValueByOID(listTemp, oidDetilChange);

                    if (qtyDetilChange > 0) {
                        double remain = entBillDetail.getQty() - qtyDetilChange;
                        if (remain == 0) {
                            //Update cash bill main dari bill detail yang bersangkutan menjadi oidBillDestination
                            entBillDetail.setBillMainId(oidBillDestination);
                            try {
                                oid = PstBillDetail.updateExc(entBillDetail);
                                rsCode = RSLT_OK;
                            } catch (Exception e) {
                                rsCode = RSLT_UNKNOWN_ERROR;
                            }

                        } else {
                            //Insert sejumlah qtyDetilChange, kemudian update bill detail source, qty - qtyDetilChange
                            Billdetail entBilldetailNew = new Billdetail();
                            double discPct, discPctSource = 0;
                            double discVal, discValSource = 0;
                            double serviceVal = 0;
                            double taxVal = 0;
                            double svcVal = 0;
                            double totalPrice, totalPriceSource = 0;
                            double discGlobal = 0;
                            double newQtySource = 0;
                            try {
                                totalPrice = qtyDetilChange * entBillDetail.getItemPrice();
                                discPct = entBillDetail.getDiscPct();
                                discVal = totalPrice * discPct / 100;
                                totalPrice -= discVal;

                                entBilldetailNew.setUnitId(entBillDetail.getUnitId());
                                entBilldetailNew.setMaterialId(entBillDetail.getMaterialId());
                                entBilldetailNew.setItemPrice(entBillDetail.getItemPrice());
                                entBilldetailNew.setDisc(entBillDetail.getDisc());
                                entBilldetailNew.setSku(entBillDetail.getSku());
                                entBilldetailNew.setItemName(entBillDetail.getItemName());
                                entBilldetailNew.setDiscType(entBillDetail.getDiscType());
                                entBilldetailNew.setMaterialType(entBillDetail.getMaterialType());
                                entBilldetailNew.setDiscPct(entBillDetail.getDiscPct());
                                entBilldetailNew.setQtyStock(entBillDetail.getQtyStock());
                                entBilldetailNew.setItemPriceStock(entBillDetail.getItemPriceStock());
                                entBilldetailNew.setTotalCost(entBillDetail.getTotalCost());
                                entBilldetailNew.setDiscGlobal(entBillDetail.getDiscGlobal());
                                entBilldetailNew.setCost(entBillDetail.getCost());
                                entBilldetailNew.setNote(entBillDetail.getNote());
                                entBilldetailNew.setLengthOrder(entBillDetail.getLengthOrder());
                                entBilldetailNew.setStatus(entBillDetail.getStatus());
                                entBilldetailNew.setTotalSvc(entBillDetail.getTotalSvc());
                                entBilldetailNew.setTotalTax(entBillDetail.getTotalTax());
                                entBilldetailNew.setStatusPrint(entBillDetail.getStatusPrint());
                                entBilldetailNew.setDisc1(entBillDetail.getDisc1());
                                entBilldetailNew.setDisc2(entBillDetail.getDisc2());
                                entBilldetailNew.setTotalDisc(entBillDetail.getTotalDisc());
                                //new value
                                entBilldetailNew.setQty(qtyDetilChange);
                                entBilldetailNew.setBillMainId(oidBillDestination);
                                entBilldetailNew.setTotalPrice(totalPrice);
                                entBilldetailNew.setOID(0);

                                oid = PstBillDetail.insertExc(entBilldetailNew);
                                if (oid != 0) {
                                    newQtySource = entBillDetail.getQty() - qtyDetilChange;
                                    totalPriceSource = newQtySource * entBillDetail.getItemPrice();
                                    discPctSource = entBillDetail.getDiscPct();
                                    discValSource = totalPriceSource * discPctSource / 100;
                                    totalPriceSource -= discValSource;

                                    entBillDetail.setQty(newQtySource);
                                    entBillDetail.setTotalPrice(totalPriceSource);

                                    oid = PstBillDetail.updateExc(entBillDetail);
                                    if (oid != 0) {
                                        rsCode = RSLT_OK;
                                    } else {
                                        rsCode = RSLT_UNKNOWN_ERROR;
                                    }

                                } else {
                                    rsCode = RSLT_UNKNOWN_ERROR;
                                }

                            } catch (Exception e) {
                            }
                        }
						//cek jumlah detil dari cash bill main source, jika tidak mempunyai detail, 
                        //maka bill akan dihapus

                        Vector listBillDetailResult = PstBillDetail.list(0, 0, whereBillDetail, "");
                        if (listBillDetailResult.size() == 0 && rsCode == RSLT_OK) {
                            try {
                                oid = PstBillMain.deleteExc(oidBillSource);
                                if (oid != 0) {
                                    rsCode = RSLT_OK;
                                } else {
                                    rsCode = RSLT_UNKNOWN_ERROR;
                                }
                            } catch (Exception e) {
                                rsCode = RSLT_UNKNOWN_ERROR;
                            }

                        }
                    }

                }

                break;
            default:

        }
        return rsCode;
    }

    public HashMap getDataDiscount(long materialId, double itemPrice, long locationId, long currencyId, long discountTypeId, double qty) {
        int discountType = 0;
        double discountPct = 0;
        double discountValue = 0;
        double priceAfterDiscount = 0;

        try {
            //CEK APAKAH ADA DISKON QTY YG BERLAKU
            String whereDiscQty = ""
                    + "" + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_MATERIAL_ID] + " = " + materialId + ""
                    + " AND " + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_LOCATION_ID] + " = " + locationId + ""
                    + " AND " + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_CURRENCY_TYPE_ID] + " = " + currencyId + ""
                    + " AND " + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_DISCOUNT_TYPE_ID] + " = " + discountTypeId + ""
                    + " AND "
                    + " (" + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_START_QTY] + " <= " + qty
                    + " AND " + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_TO_QTY] + " >= " + qty
                    + " )";

            Vector<DiscountQtyMapping> listDiscQty = PstDiscountQtyMapping.list(0, 0, whereDiscQty, "");
            if (listDiscQty.size() > 0) {
                DiscountQtyMapping discMap = listDiscQty.get(0);
                discountType = discMap.getDiscountType();
                switch (discountType) {
                    case PstDiscountQtyMapping.DISC_PERSEN_PER_ITEM_UNIT:
                        discountPct = discMap.getDiscountValue();
                        discountValue = itemPrice * discountPct / 100;
                        priceAfterDiscount = itemPrice - discountValue;
                        break;
                    case PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_UNIT:
                        discountValue = discMap.getDiscountValue();
                        discountPct = discountValue / itemPrice * 100;
                        priceAfterDiscount = itemPrice - discountValue;
                        break;
                    case PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_SKU:
                        discountValue = discMap.getDiscountValue() / qty;
                        priceAfterDiscount = itemPrice - discountValue;
                        break;
                }
            } else {
                //JIKA TIDAK ADA DISKON QTY MAKA GUNAKAN DISKON REGULER
                String whereDiscMap = ""
                        + "" + PstDiscountMapping.fieldNames[PstDiscountMapping.FLD_DISCOUNT_TYPE_ID] + " = " + discountTypeId
                        + " AND " + PstDiscountMapping.fieldNames[PstDiscountMapping.FLD_CURRENCY_TYPE_ID] + " = " + currencyId
                        + " AND " + PstDiscountMapping.fieldNames[PstDiscountMapping.FLD_MATERIAL_ID] + " = " + materialId
                        + "";
                Vector<DiscountMapping> listDiscTypeMap = PstDiscountMapping.list(0, 0, whereDiscMap, "");
                if (listDiscTypeMap.size() > 0) {
                    discountPct = listDiscTypeMap.get(0).getDiscountPct();
                    discountValue = itemPrice * discountPct / 100;
                    priceAfterDiscount = itemPrice - discountValue;
                }
                discountType = PstDiscountQtyMapping.DISC_PERSEN_PER_ITEM_UNIT;
            }

        } catch (Exception e) {
        }

        HashMap<String, String> mapData = new HashMap();
        mapData.put("DISCOUNT_TYPE", "" + discountType);
        mapData.put("DISCOUNT_PCT", "" + discountPct);
        mapData.put("DISCOUNT_VAL", "" + discountValue);
        mapData.put("DISCOUNT_PRICE", "" + priceAfterDiscount);

        return mapData;
    }
}
