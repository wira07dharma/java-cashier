/* 
 * Ctrl Name  		:  CtrlBackUpService.java 
 * Created on 	:  [date] [time] AM/PM 
 * 
 * @author  		:  [authorName] 
 * @version  		:  [version] 
 */

/*******************************************************************
 * Class Description 	: [project description ... ] 
 * Imput Parameters 	: [input parameter ...] 
 * Output 		: [output ...] 
 *******************************************************************/

package com.dimata.posbo.form.admin.service;

/* java package */ 
import com.dimata.common.db.DBException;
import com.dimata.gui.jsp.*;

/* qdep package */
import com.dimata.common.db.*;

import com.dimata.posbo.entity.admin.service.*;
import com.dimata.qdep.form.*;
import com.dimata.qdep.system.*;
import com.dimata.util.*;
import com.dimata.util.lang.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CtrlBackUpService extends Control implements I_Language 
{
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
	private BackUpService backUpService;
	private PstBackUpService pstBackUpService;
	private FrmBackUpService frmBackUpService;
	int language = LANGUAGE_DEFAULT;

	public CtrlBackUpService(HttpServletRequest request){
		msgString = "";
		backUpService = new BackUpService();
		try{
			pstBackUpService = new PstBackUpService(0);
		}catch(Exception e){;}
		frmBackUpService = new FrmBackUpService(request, backUpService);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmBackUpService.addError(frmBackUpService.FRM_FIELD_BACK_UP_CONF_ID, resultText[language][RSLT_EST_CODE_EXIST] );
				return resultText[language][RSLT_EST_CODE_EXIST];
			default:
				return resultText[language][RSLT_UNKNOWN_ERROR]; 
		}
	}

	private int getControlMsgId(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				return RSLT_EST_CODE_EXIST;
			default:
				return RSLT_UNKNOWN_ERROR;
		}
	}

	public int getLanguage(){ return language; }

	public void setLanguage(int language){ this.language = language; }

	public BackUpService getBackUpService() { return backUpService; } 

	public FrmBackUpService getForm() { return frmBackUpService; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidBackUpService, HttpServletRequest request){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidBackUpService != 0){
					try{
						backUpService = PstBackUpService.fetchExc(oidBackUpService);
					}catch(Exception exc){
					}
				}

				frmBackUpService.requestEntityObject(backUpService);

                Date startTime = ControlDate.getTime(FrmBackUpService.fieldNames[FrmBackUpService.FRM_FIELD_START_TIME], request);

				if(frmBackUpService.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

                backUpService.setStartTime(startTime);

				if(backUpService.getOID()==0){
					try{
						long oid = pstBackUpService.insertExc(this.backUpService);
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
						return getControlMsgId(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
						return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
					}

				}else{
					try {
						long oid = pstBackUpService.updateExc(this.backUpService);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidBackUpService != 0) {
					try {
						backUpService = PstBackUpService.fetchExc(oidBackUpService);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidBackUpService != 0) {
					try {
						backUpService = PstBackUpService.fetchExc(oidBackUpService);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidBackUpService != 0){
					try{
						long oid = PstBackUpService.deleteExc(oidBackUpService);
						if(oid!=0){
							msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
							excCode = RSLT_OK;
						}else{
							msgString = FRMMessage.getMessage(FRMMessage.ERR_DELETED);
							excCode = RSLT_FORM_INCOMPLETE;
						}
					}catch(DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch(Exception exc){	
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			default :
                if (oidBackUpService != 0) {
					try {
						backUpService = PstBackUpService.fetchExc(oidBackUpService);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

		}
		return rsCode;
	}
}
