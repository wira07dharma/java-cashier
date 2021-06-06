/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.entity.cashier.transaction;

/**
 *
 * @author Ardiadi
 */
public class BillMainJSON {
    public static final int FLD_HTML = 0;
    public static final int FLD_DISPLAY_TOTAL = 1;
    public static final int FLD_BALANCE_VALUE = 2;
    public static final int FLD_CREDIT_CARD_CHARGE = 3;
    public static final int FLD_PAID_VALUE = 4;
    public static final int FLD_COSTUM_BALANCE = 5;
    public static final int FLD_TAX_INC = 6;
    public static final int FLD_STATUS_ORDER = 7;
    
    public static String[] fieldNames = {
	"FRM_FIELD_HTML",
	"FRM_FIELD_DISPLAY_TOTAL",
	"FRM_FIELD_BALANCE_VALUE",
	"FRM_FIELD_CC_CHARGE",
	"FRM_FIELD_PAID_VALUE",
	"FRM_FIELD_COSTUM_BALANCE",
	"FRM_FIELD_TAX_INC",
        "FLD_STATUS_ORDER"
    };
}
