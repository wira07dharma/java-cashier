
package com.dimata.pos.form.payment;

/**
 *
 * @author Witar
 */
import com.dimata.pos.entity.payment.CashCreditCard;
import com.dimata.pos.entity.payment.PstCashCreditCard;
import com.dimata.common.db.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import static com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT;
import javax.servlet.http.*;

public class CtrlCashCreditCard {
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
    private CashCreditCard entCashCreditCard;
    private PstCashCreditCard pstCashCreditCard;
    private FrmCashCreditCard frmCashCreditCard;
    int language = LANGUAGE_DEFAULT;

    public CtrlCashCreditCard(HttpServletRequest request) {
        msgString = "";
        entCashCreditCard = new CashCreditCard();
        try {
            pstCashCreditCard = new PstCashCreditCard(0);
        } catch (Exception e) {;
        }
        frmCashCreditCard = new FrmCashCreditCard(request, entCashCreditCard);
    }

    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmCashCreditCard.addError(frmCashCreditCard.FRM_FIELD_CC_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public CashCreditCard getCashCreditCard() {
        return entCashCreditCard;
    }

    public FrmCashCreditCard getForm() {
        return frmCashCreditCard;
    }

    public String getMessage() {
        return msgString;
    }

    public int getStart() {
        return start;
    }

    public int action(int cmd, long oidCashCreditCard) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
            break;

            case Command.SAVE:
                if (oidCashCreditCard != 0) {
                    try {
                        entCashCreditCard = PstCashCreditCard.fetchExc(oidCashCreditCard);
                    } catch (Exception exc) {
                    }
                }

                frmCashCreditCard.requestEntityObject(entCashCreditCard);

                if (frmCashCreditCard.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }

                if (entCashCreditCard.getOID() == 0) {
                    try {
                        long oid = pstCashCreditCard.insertExc(this.entCashCreditCard);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }

                } else {
                    try {
                    long oid = pstCashCreditCard.updateExc(this.entCashCreditCard);
                    } catch (Exception exc) {
                    msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }

                }
            break;

            case Command.EDIT:
                if (oidCashCreditCard != 0) {
                    try {
                        entCashCreditCard = PstCashCreditCard.fetchExc(oidCashCreditCard);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
            break;

            case Command.ASK:
                if (oidCashCreditCard != 0) {
                    try {
                        entCashCreditCard = PstCashCreditCard.fetchExc(oidCashCreditCard);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
            break;

            case Command.DELETE:
                if (oidCashCreditCard != 0) {
                    try {
                        long oid = PstCashCreditCard.deleteExc(oidCashCreditCard);
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
