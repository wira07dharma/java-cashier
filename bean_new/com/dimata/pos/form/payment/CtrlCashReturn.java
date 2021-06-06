
package com.dimata.pos.form.payment;

/**
 *
 * @author Witar
 */

import com.dimata.pos.entity.payment.CashReturn;
import com.dimata.pos.entity.payment.PstCashReturn;
import com.dimata.common.db.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import javax.servlet.http.*;

public class CtrlCashReturn extends Control implements I_Language {
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
private CashReturn entCashReturn;
private PstCashReturn pstCashReturn;
private FrmCashReturn frmCashReturn;
int language = LANGUAGE_DEFAULT;

public CtrlCashReturn(HttpServletRequest request) {
msgString = "";
entCashReturn = new CashReturn();
try {
pstCashReturn = new PstCashReturn(0);
} catch (Exception e) {;
}
frmCashReturn = new FrmCashReturn(request, entCashReturn);
}

private String getSystemMessage(int msgCode) {
switch (msgCode) {
case I_DBExceptionInfo.MULTIPLE_ID:
this.frmCashReturn.addError(frmCashReturn.FRM_FIELD_RETURN_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

public CashReturn getCashReturn() {
return entCashReturn;
}

public FrmCashReturn getForm() {
return frmCashReturn;
}

public String getMessage() {
return msgString;
}

public int getStart() {
return start;
}

public int action(int cmd, long oidCashReturn) {
msgString = "";
int excCode = I_DBExceptionInfo.NO_EXCEPTION;
int rsCode = RSLT_OK;
switch (cmd) {
case Command.ADD:
break;

case Command.SAVE:
if (oidCashReturn != 0) {
try {
entCashReturn = PstCashReturn.fetchExc(oidCashReturn);
} catch (Exception exc) {
}
}

frmCashReturn.requestEntityObject(entCashReturn);

if (frmCashReturn.errorSize() > 0) {
msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
return RSLT_FORM_INCOMPLETE;
}

if (entCashReturn.getOID() == 0) {
try {
long oid = pstCashReturn.insertExc(this.entCashReturn);
} catch (Exception exc) {
msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
}

} else {
try {
long oid = pstCashReturn.updateExc(this.entCashReturn);
} catch (Exception exc) {
msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
}

}
break;

case Command.EDIT:
if (oidCashReturn != 0) {
try {
entCashReturn = PstCashReturn.fetchExc(oidCashReturn);
} catch (Exception exc) {
msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
}
}
break;

case Command.ASK:
if (oidCashReturn != 0) {
try {
entCashReturn = PstCashReturn.fetchExc(oidCashReturn);
} catch (Exception exc) {
msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
}
}
break;

case Command.DELETE:
if (oidCashReturn != 0) {
try {
long oid = PstCashReturn.deleteExc(oidCashReturn);
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
}
