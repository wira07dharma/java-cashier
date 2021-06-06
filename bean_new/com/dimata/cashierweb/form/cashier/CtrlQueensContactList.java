/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.form.cashier;

import com.dimata.common.db.DBException;
import com.dimata.common.entity.contact.ContactClass;
import com.dimata.common.entity.contact.ContactClassAssign;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactClass;
import com.dimata.common.entity.contact.PstContactClassAssign;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.form.contact.FrmContactList;
import com.dimata.qdep.form.Control;
import com.dimata.qdep.form.FRMMessage;
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
public class CtrlQueensContactList extends Control implements I_Language {
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
    private ContactList entContactList;
    private PstContactList pstContactList;
    private FrmContactList frmContactList;
    int language = LANGUAGE_DEFAULT;

    public CtrlQueensContactList(HttpServletRequest request) {
	msgString = "";
	entContactList = new ContactList();
	try {
	    pstContactList = new PstContactList(0);
	} catch (Exception e) {;
	}
	frmContactList = new FrmContactList(request, entContactList);
    }

    private String getSystemMessage(int msgCode) {
	switch (msgCode) {
	    case I_DBExceptionInfo.MULTIPLE_ID:
		this.frmContactList.addError(frmContactList.FRM_FIELD_CONTACT_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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

    public ContactList getContactList() {
	return entContactList;
    }

    public FrmContactList getForm() {
	return frmContactList;
    }

    public String getMessage() {
	return msgString;
    }

    public int getStart() {
	return start;
    }

    public int action(int cmd, long oidContactList) {
	msgString = "";
	int excCode = I_DBExceptionInfo.NO_EXCEPTION;
	int rsCode = RSLT_OK;
	switch (cmd) {
	    case Command.ADD:
	    break;

	    case Command.SAVE:
		if (oidContactList != 0) {
		    try {
			entContactList = PstContactList.fetchExc(oidContactList);
		    } catch (Exception exc) {
		    }
		}	

		frmContactList.requestEntityObject(entContactList);

		/*if (frmContactList.errorSize() > 0) {
		    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
		    return RSLT_FORM_INCOMPLETE;
		}*/

		if (entContactList.getOID() == 0) {
		    try {
			
			String dateNow = Formater.formatDate(new Date(),"yyyyMM");
			Date regDate = Formater.reFormatDate(new Date(), "yyyy-MM-dd kk:mm:ss");
			Location location = PstLocation.fetchExc(entContactList.getLocationId());
			String contactCode = "CST-"+location.getCode();
			int lastNumber = PstContactList.getCount(""+PstContactList.fieldNames[PstContactList.FLD_CONTACT_CODE]+" LIKE '%"+contactCode+"%'")+1;
			String zeroLoop = "";
			for(int i = 1; i<=9-String.valueOf(lastNumber).length();i++){
			    zeroLoop += "0";
			}
			contactCode+="-"+zeroLoop+""+lastNumber;
			entContactList.setContactCode(contactCode);
			entContactList.setContactType(10);
			entContactList.setRegdate(regDate);
                        
                        //get contact class untuk type 10 (customer)
                        String whereContactClass = " "+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"="+entContactList.getContactType()+"";
                        Vector listContactClass = PstContactClass.list(0, 0, whereContactClass, "");
                        ContactClass contactClass = (ContactClass) listContactClass.get(0);
                        
			//insert ke tabel contact_list
			long oid = pstContactList.insertExc(this.entContactList);
                        
                        //buat object class Assign
                        ContactClassAssign contactClassAssign = new ContactClassAssign();
                        contactClassAssign.setContactClassId(contactClass.getOID());
                        contactClassAssign.setContactId(oid);
                        
                        //get contact class asign sebelumnya
                        String where =" "+PstContactClassAssign.fieldNames[PstContactClassAssign.FLD_CONTACT_ID]+"="+oid+"";
                        Vector listContactCLassAsign = PstContactClassAssign.list(0, 0, where, "");
                        
                        if (listContactCLassAsign.size()>0){
                            //jika sebelumnya sudah ada maka dihapus dulu
                            ContactClassAssign entContactClassAssign = (ContactClassAssign)listContactCLassAsign.get(0);
                            
                            //delete data di contact class asign sebelumnya
                            PstContactClassAssign.deleteClassAssign(entContactClassAssign.getOID());
                            
                        }
                        
                        //insert ke contact class assign
                        
                        long contactClassAsignOid = PstContactClassAssign.insertExc(contactClassAssign);
    
			msgString = ""+oid;
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
		long oid = pstContactList.updateExc(this.entContactList);
		} catch (DBException dbexc) {
		excCode = dbexc.getErrorCode();
		msgString = getSystemMessage(excCode);
		} catch (Exception exc) {
		msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
		}

		}
	break;

	case Command.EDIT:
	if (oidContactList != 0) {
	try {
	entContactList = PstContactList.fetchExc(oidContactList);
	} catch (DBException dbexc) {
	excCode = dbexc.getErrorCode();
	msgString = getSystemMessage(excCode);
	} catch (Exception exc) {
	msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
	}
	}
	break;

	case Command.ASK:
	if (oidContactList != 0) {
	try {
	entContactList = PstContactList.fetchExc(oidContactList);
	} catch (DBException dbexc) {
	excCode = dbexc.getErrorCode();
	msgString = getSystemMessage(excCode);
	} catch (Exception exc) {
	msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
	}
	}
	break;

	case Command.DELETE:
	if (oidContactList != 0) {
	try {
	long oid = PstContactList.deleteExc(oidContactList);
	if (oid != 0) {
	msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
	excCode = RSLT_OK;
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
}
