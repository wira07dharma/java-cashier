

package com.dimata.pos.form.payment;



import com.dimata.pos.entity.payment.CashReturn;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.I_FRMInterface;
import com.dimata.qdep.form.I_FRMType;
import javax.servlet.http.HttpServletRequest;

public class FrmCashReturn extends FRMHandler implements I_FRMInterface, I_FRMType {
  private CashReturn entCashReturn;
  public static final String FRM_NAME_CASHRETURN = "FRM_NAME_CASHRETURN";
  public static final int FRM_FIELD_RETURN_ID = 0;
  public static final int FRM_FIELD_CURRENCY_ID = 1;
  public static final int FRM_FIELD_CASH_BILL_MAIN_ID = 2;
  public static final int FRM_FIELD_RATE = 3;
  public static final int FRM_FIELD_PAYMENT_STATUS = 4;
  public static final int FRM_FIELD_AMOUNT = 5;


public static String[] fieldNames = {
    "FRM_FIELD_RETURN_ID",
    "FRM_FIELD_CURRENCY_ID",
    "FRM_FIELD_CASH_BILL_MAIN_ID",
    "FRM_FIELD_RATE",
    "FRM_FIELD_PAYMENT_STATUS",
    "FRM_FIELD_AMOUNT"
};

public static int[] fieldTypes = {
    TYPE_LONG,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_FLOAT,
    TYPE_INT,
    TYPE_FLOAT
};

public FrmCashReturn() {
}

public FrmCashReturn(CashReturn entCashReturn) {
this.entCashReturn = entCashReturn;
}

public FrmCashReturn(HttpServletRequest request, CashReturn entCashReturn) {
super(new FrmCashReturn(entCashReturn), request);
this.entCashReturn = entCashReturn;
}

public String getFormName() {
return FRM_NAME_CASHRETURN;
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

public CashReturn getEntityObject() {
return entCashReturn;
}

public void requestEntityObject(CashReturn entCashReturn) {
try {
this.requestParam();
    entCashReturn.setOID(getLong(FRM_FIELD_RETURN_ID));
    entCashReturn.setCurrencyId(getLong(FRM_FIELD_CURRENCY_ID));
    entCashReturn.setBillMainId(getLong(FRM_FIELD_CASH_BILL_MAIN_ID));
    entCashReturn.setRate(getFloat(FRM_FIELD_RATE));
    entCashReturn.setPaymentStatus(getInt(FRM_FIELD_PAYMENT_STATUS));
    entCashReturn.setAmount(getFloat(FRM_FIELD_AMOUNT));
} catch (Exception e) {
System.out.println("Error on requestEntityObject : " + e.toString());
}
}

}
