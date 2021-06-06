package com.dimata.posbo.form.warehouse;

/* java package */
import java.util.*;
import javax.servlet.http.*;

/* dimata package */
import com.dimata.util.*;
import com.dimata.util.lang.*;

/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.entity.*;
import com.dimata.common.db.*;
/* project package */

import com.dimata.posbo.entity.warehouse.*;

import com.dimata.common.entity.logger.LogSysHistory;
import com.dimata.common.entity.logger.PstLogSysHistory;
import com.dimata.gui.jsp.ControlDate;

public class CtrlMatStockOpname extends Control implements I_Language {

    public static int RSLT_OK = 0;
    public static int RSLT_UNKNOWN_ERROR = 1;
    public static int RSLT_EST_CODE_EXIST = 2;
    public static int RSLT_FORM_INCOMPLETE = 3;
    public static String[][] resultText =
	    {
	{"Berhasil", "Tidak dapat diproses", "NoPerkiraan sudah ada", "Data tidak lengkap"},
	{"Succes", "Can not process", "Estimation code exist", "Data incomplete"}};
    private int start;
    private String msgString;
    private MatStockOpname matStockOpname;
    MatStockOpname prevMatStockOpname=null;
    private PstMatStockOpname pstMatStockOpname;
    private FrmMatStockOpname frmMatStockOpname;
    Date dateLog = new Date();
    private HttpServletRequest req;
    int language = LANGUAGE_DEFAULT;

    public CtrlMatStockOpname(HttpServletRequest request) {
	msgString = "";
	matStockOpname = new MatStockOpname();
	try {
	    pstMatStockOpname = new PstMatStockOpname(0);
	} catch (Exception e) {	    
	}
	req = request;
	frmMatStockOpname = new FrmMatStockOpname(request, matStockOpname);
    }

    private String getSystemMessage(int msgCode) {
	switch (msgCode) {
	    case I_DBExceptionInfo.MULTIPLE_ID:
		this.frmMatStockOpname.addError(frmMatStockOpname.FRM_FIELD_STOCK_OPNAME_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public MatStockOpname getMatStockOpname() {
	return matStockOpname;
    }

    public FrmMatStockOpname getForm() {
	return frmMatStockOpname;
    }

    public String getMessage() {
	return msgString;
    }

    public int getStart() {
	return start;
    }

    public int action(int cmd, long oidMatStockOpname) {
        return action(cmd,oidMatStockOpname,"",0);
    }

    public int action(int cmd, long oidMatStockOpname, String nameUser, long userID) {
	msgString = "";
	int excCode = I_DBExceptionInfo.NO_EXCEPTION;
	int rsCode = RSLT_OK;
	switch (cmd) {
	    case Command.ADD:
		break;

	    case Command.SAVE:
		if (oidMatStockOpname != 0) {
		    try {
			matStockOpname = PstMatStockOpname.fetchExc(oidMatStockOpname);
		    } catch (Exception exc) {
		    }

//                    by dyas - 20131127
//                    tambah try catch untuk simpen nilai di variabel prevMatStockOpname
                    try {
                        prevMatStockOpname = PstMatStockOpname.fetchExc(oidMatStockOpname);
                    } catch (Exception exc) {
		}
		}

		frmMatStockOpname.requestEntityObject(matStockOpname);
		Date date = ControlDate.getDateTime(frmMatStockOpname.fieldNames[FrmMatStockOpname.FRM_FIELD_STOCK_OPNAME_DATE], req);
		matStockOpname.setStockOpnameDate(date);

		if (frmMatStockOpname.errorSize() > 0) {
		    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
		    return RSLT_FORM_INCOMPLETE;
		}

		if (matStockOpname.getOID() == 0) {
		    try {
			long oid = pstMatStockOpname.insertExc(this.matStockOpname);

                        //dyas 20131127
                        //tambah kondisi untuk manggil methods insertHistory
                        if(oid!=0){
                            insertHistory(userID, nameUser, cmd, oid);
                        }
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
			long oid = pstMatStockOpname.updateExc(this.matStockOpname);
                        int cmdHistory = Command.UPDATE;

                        //dyas 20131127
                        //tambah kondisi untuk manggil methods insertHistory
                        if(oid!=0){
                            insertHistory(userID, nameUser, cmd, oid);
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
		if (oidMatStockOpname != 0) {
		    try {
			matStockOpname = PstMatStockOpname.fetchExc(oidMatStockOpname);
		    } catch (DBException dbexc) {
			excCode = dbexc.getErrorCode();
			msgString = getSystemMessage(excCode);
		    } catch (Exception exc) {
			msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
		    }
		}
		break;

	    case Command.ASK:
		if (oidMatStockOpname != 0) {
		    try {
			matStockOpname = PstMatStockOpname.fetchExc(oidMatStockOpname);
		    } catch (DBException dbexc) {
			excCode = dbexc.getErrorCode();
			msgString = getSystemMessage(excCode);
		    } catch (Exception exc) {
			msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
		    }
		}
		break;

	    case Command.DELETE:
		if (oidMatStockOpname != 0) {
		    try {
			String whereClause = PstMatStockOpnameItem.fieldNames[PstMatStockOpnameItem.FLD_STOCK_OPNAME_ID] + "=" + oidMatStockOpname;
			Vector vect = PstMatStockOpnameItem.list(0, 0, whereClause, "");
			if (vect != null && vect.size() > 0) {
			    for (int k = 0; k < vect.size(); k++) {
				MatStockOpnameItem matDispatchItem = (MatStockOpnameItem) vect.get(k);
				CtrlMatStockOpnameItem ctrlMatDpsItm = new CtrlMatStockOpnameItem();
				ctrlMatDpsItm.action(Command.DELETE, matDispatchItem.getOID(), oidMatStockOpname);
			    }
			}

                        //dyas 20131127
                        //tambah try catch untuk ngambil data berdasarkan id yang dibawa
                        try{
                            prevMatStockOpname = PstMatStockOpname.fetchExc(oidMatStockOpname);
                        }catch(Exception e){
                        };

			long oid = PstMatStockOpname.deleteExc(oidMatStockOpname);
			if (oid != 0) {
			    msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
			    excCode = RSLT_OK;

                            if(oid!=0){
                                insertHistory(userID, nameUser, cmd, oid);
                            }
			} else {
			    msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
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
	return rsCode;
    }

    public  void insertHistory(long userID, String nameUser, int cmd, long oid)
    {
        try
        {
           LogSysHistory logSysHistory = new LogSysHistory();
           logSysHistory.setLogUserId(userID);
           logSysHistory.setLogLoginName(nameUser);
           logSysHistory.setLogApplication("Prochain");
           logSysHistory.setLogOpenUrl("material/stock/mat_opname_edit.jsp");
           logSysHistory.setLogUpdateDate(dateLog);
           logSysHistory.setLogDocumentType(I_DocType.documentTypeNames[0][1]);
           logSysHistory.setLogUserAction(Command.commandString[cmd]);
           logSysHistory.setLogDocumentNumber(matStockOpname.getStockOpnameNumber());
           logSysHistory.setLogDocumentId(oid);
           logSysHistory.setLogDetail(this.matStockOpname.getLogDetail(prevMatStockOpname));

          if(!logSysHistory.getLogDetail().equals("") || cmd==Command.DELETE)
           {
                long oid2 = PstLogSysHistory.insertLog(logSysHistory);
}
            }
      catch(Exception e)
      {

      }
    }
}
