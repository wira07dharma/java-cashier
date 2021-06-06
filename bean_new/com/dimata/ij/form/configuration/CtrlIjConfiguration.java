/* 
 * Ctrl Name  		:  CtrlIjConfiguration.java 
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

package com.dimata.ij.form.configuration;

/* java package */ 
import java.util.*; 
import javax.servlet.*;
import javax.servlet.http.*; 

// dimata package
import com.dimata.util.*;
import com.dimata.util.lang.*;

// qdep package
import com.dimata.qdep.system.*;
import com.dimata.qdep.form.*;

// project package
import com.dimata.common.db.*;
import com.dimata.ij.entity.configuration.*;

public class CtrlIjConfiguration extends Control implements I_Language 
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
	private IjConfiguration ijConfiguration;
	private PstIjConfiguration pstIjConfiguration;
	private FrmIjConfiguration frmIjConfiguration;
	int language = LANGUAGE_DEFAULT;

	public CtrlIjConfiguration(HttpServletRequest request){
		msgString = "";
		ijConfiguration = new IjConfiguration();
		try{
			pstIjConfiguration = new PstIjConfiguration(0);
		}catch(Exception e){;}
		frmIjConfiguration = new FrmIjConfiguration(request, ijConfiguration);
	}

	private String getSystemMessage(int msgCode){
		switch (msgCode){
			case I_DBExceptionInfo.MULTIPLE_ID :
				this.frmIjConfiguration.addError(frmIjConfiguration.FRM_FIELD_IJ_CONFIGURATION_ID, resultText[language][RSLT_EST_CODE_EXIST] );
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

	public IjConfiguration getIjConfiguration() { return ijConfiguration; } 

	public FrmIjConfiguration getForm() { return frmIjConfiguration; }

	public String getMessage(){ return msgString; }

	public int getStart() { return start; }

	public int action(int cmd , long oidIjConfiguration){
		msgString = "";
		int excCode = I_DBExceptionInfo.NO_EXCEPTION;
		int rsCode = RSLT_OK;
		switch(cmd){
			case Command.ADD :
				break;

			case Command.SAVE :
				if(oidIjConfiguration != 0){
					try{
						ijConfiguration = PstIjConfiguration.fetchExc(oidIjConfiguration);
					}catch(Exception exc){
					}
				}

				frmIjConfiguration.requestEntityObject(ijConfiguration);

				if(frmIjConfiguration.errorSize()>0) {
					msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
					return RSLT_FORM_INCOMPLETE ;
				}

				if(ijConfiguration.getOID()==0){
					try{
						long oid = pstIjConfiguration.insertExc(this.ijConfiguration);
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
						long oid = pstIjConfiguration.updateExc(this.ijConfiguration);
					}catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					}catch (Exception exc){
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN); 
					}

				}
				break;

			case Command.EDIT :
				if (oidIjConfiguration != 0) {
					try {
						ijConfiguration = PstIjConfiguration.fetchExc(oidIjConfiguration);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.ASK :
				if (oidIjConfiguration != 0) {
					try {
						ijConfiguration = PstIjConfiguration.fetchExc(oidIjConfiguration);
					} catch (DBException dbexc){
						excCode = dbexc.getErrorCode();
						msgString = getSystemMessage(excCode);
					} catch (Exception exc){ 
						msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
					}
				}
				break;

			case Command.DELETE :
				if (oidIjConfiguration != 0){
					try{
						long oid = PstIjConfiguration.deleteExc(oidIjConfiguration);
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

		}
		return rsCode;
	}
}
