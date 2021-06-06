/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.pos.form.billing;

/**
 *
 * @author Wiweka
 */
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

// qdep package
import com.dimata.qdep.form.*;

// project package
import com.dimata.pos.entity.masterCashier.*;
import com.dimata.pos.entity.billing.*;

public class FrmBillDetail extends FRMHandler implements I_FRMInterface, I_FRMType {

    private Billdetail billdetail;

    public static final String FRM_NAME_BILL_DETAIL = "FRM_NAME_BILL_DETAIL";

    public static final int FRM_FIELD_CASH_BILL_DETAIL_ID = 0;
    public static final int FRM_FIELD_CASH_BILL_MAIN_ID = 1;
    public static final int FRM_FIELD_UNIT_ID = 2;
    public static final int FRM_FIELD_MATERIAL_ID = 3;
    public static final int FRM_FIELD_QTY = 4;
    public static final int FRM_FIELD_ITEM_PRICE = 5;
    public static final int FRM_FIELD_DISC = 6;
    public static final int FRM_FIELD_TOTAL_PRICE = 7;
    public static final int FRM_FIELD_SKU = 8;
    public static final int FRM_FIELD_ITEM_NAME = 9;
    public static final int FRM_FIELD_DISC_TYPE = 10;
    public static final int FRM_FIELD_MATERIAL_TYPE = 11;
    public static final int FRM_FIELD_DISC_PCT = 12;
    public static final int FRM_FIELD_NOTE = 13;
    public static final int FRM_FIELD_DISCON_GLOBAL = 14;
    public static final int FRM_FIELD_STATUS = 15;
    public static final int FRM_FIELD_DISC_1 = 16;
    public static final int FRM_FIELD_DISC_2 = 17;

    /**
     * untuk total Ari_wiweka 20130701
     */
    public static final int FRM_FIELD_TOTAL_AMOUNT = 18;
    public static final int FRM_FIELD_TOTAL_DISC = 19;
    public static final int FRM_FIELD_QTY_ISSUE = 20;
    public static final int FRM_FIELD_TOTAL_SERVICE = 21;
    public static final int FRM_FIELD_TOTAL_TAX = 22;
    public static final int FRM_FIELD_PARENT_ID = 23;
    public static final int FRM_FIELD_CUSTOME_PACK_BILLING_ID = 24;
    public static final int FRM_FIELD_CUSTOME_SCHEDULE_ID = 25;
    //added by dewok 20190217
    public static final int FRM_FIELD_GUIDE_PRICE = 26;

    public static String[] fieldNames
            = {
                "FRM_FIELD_CASH_BILL_DETAIL_ID",
                "FRM_FIELD_CASH_BILL_MAIN_ID",
                "FRM_FIELD_UNIT_ID",
                "FRM_FIELD_MATERIAL_ID",
                "FRM_FIELD_QTY",
                "FRM_FIELD_ITEM_PRICE",
                "FRM_FIELD_DISC",
                "FRM_FIELD_TOTAL_PRICE",
                "FRM_FIELD_SKU",
                "FRM_FIELD_ITEM_NAME",
                "FRM_FIELD_DISC_TYPE",
                "FRM_FIELD_MATERIAL_TYPE",
                "FRM_FIELD_DISC_PCT",
                "FRM_FIELD_NOTE",
                "FRM_FIELD_DISCON_GLOBAL",
                "FRM_FIELD_STATUS",
                "FRM_FIELD_DISC_1",
                "FRM_FIELD_DISC_2",
                "FRM_FIELD_TOTAL_AMOUNT",
                "FRM_FIELD_TOTAL_DISC",
                "FRM_FIELD_QTY_ISSUE",
                "FRM_FIELD_TOTAL_SERVICE",
                "FRM_FIELD_TOTAL_TAX",
                "FRM_FIELD_PARENT_ID",
                "FRM_FIELD_CUSTOME_PACK_BILLING_ID",
                "FRM_FIELD_CUSTOME_SCHEDULE_ID",
                //added by dewok 20190217
                "FRM_FIELD_GUIDE_PRICE"

            };

    public static int[] fieldTypes
            = {
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_STRING,
                TYPE_STRING,
                TYPE_INT,
                TYPE_INT,
                TYPE_FLOAT,
                TYPE_STRING,
                TYPE_FLOAT,
                TYPE_INT,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_FLOAT,
                TYPE_LONG,
                TYPE_LONG,
                TYPE_LONG,
                //added by dewok 20190217
                TYPE_FLOAT
            };

    public FrmBillDetail() {
    }

    public FrmBillDetail(Billdetail billdetail) {
        this.billdetail = billdetail;
    }

    public FrmBillDetail(HttpServletRequest request, Billdetail billdetail) {
        super(new FrmBillDetail(billdetail), request);
        this.billdetail = billdetail;
    }

    public String getFormName() {
        return FRM_NAME_BILL_DETAIL;
    }

    public int[] getFieldTypes() {
        return fieldTypes;
    }

    public String[] getFieldNames() {
        return fieldNames;
    }

    public int getFieldSize() {
        return fieldNames.length;
    }

    public Billdetail getEntityObject() {
        return billdetail;
    }

    public void requestEntityObject(Billdetail billdetail) {
        try {
            this.requestParam();

            billdetail.setBillMainId(getLong(FRM_FIELD_CASH_BILL_MAIN_ID));
            billdetail.setUnitId(getLong(FRM_FIELD_UNIT_ID));
            billdetail.setMaterialId(getLong(FRM_FIELD_MATERIAL_ID));
            billdetail.setQty(getDouble(FRM_FIELD_QTY));
            billdetail.setItemPrice(getDouble(FRM_FIELD_ITEM_PRICE));
            billdetail.setDisc(getDouble(FRM_FIELD_DISC));
            billdetail.setTotalPrice(getDouble(FRM_FIELD_TOTAL_PRICE));
            billdetail.setSku(getString(FRM_FIELD_SKU));
            billdetail.setItemName(getString(FRM_FIELD_ITEM_NAME));
            billdetail.setDiscType(getInt(FRM_FIELD_DISC_TYPE));
            billdetail.setMaterialType(getInt(FRM_FIELD_MATERIAL_TYPE));
            billdetail.setDiscPct(getDouble(FRM_FIELD_DISC_PCT));
            billdetail.setNote(getString(FRM_FIELD_NOTE));
            billdetail.setDiscGlobal(getDouble(FRM_FIELD_DISCON_GLOBAL));
            billdetail.setUpdateStatus(getInt(FRM_FIELD_STATUS));
            billdetail.setDisc1(getDouble(FRM_FIELD_DISC_1));
            billdetail.setDisc2(getDouble(FRM_FIELD_DISC_2));
            billdetail.setTotalAmount(getDouble(FRM_FIELD_TOTAL_AMOUNT));
            billdetail.setTotalDisc(getDouble(FRM_FIELD_TOTAL_DISC));
            billdetail.setQtyIssue(getDouble(FRM_FIELD_QTY_ISSUE));
            billdetail.setTotalSvc(getDouble(FRM_FIELD_TOTAL_SERVICE));
            billdetail.setTotalTax(getDouble(FRM_FIELD_TOTAL_TAX));
            billdetail.setParentId(getLong(FRM_FIELD_PARENT_ID));
            billdetail.setCutomePackBillingId(getLong(FRM_FIELD_CUSTOME_PACK_BILLING_ID));
            billdetail.setCostumeScheduleId(getLong(FRM_FIELD_CUSTOME_SCHEDULE_ID));
            //added by dewok 20190217
            billdetail.setGuidePrice(getDouble(FRM_FIELD_GUIDE_PRICE));
        } catch (Exception e) {
            System.out.println("Error on requestEntityObject : " + e.toString());
        }
    }

}
