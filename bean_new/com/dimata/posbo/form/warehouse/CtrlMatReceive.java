package com.dimata.posbo.form.warehouse;

/* java package */

import com.dimata.common.entity.logger.LogSysHistory;
import com.dimata.common.entity.payment.CurrencyType;
import com.dimata.common.entity.payment.PstCurrencyType;
import java.util.*;
import javax.servlet.http.*;

/* dimata package */
import com.dimata.util.*;
import com.dimata.util.lang.*;

/* qdep package */
import com.dimata.qdep.system.*;
import com.dimata.qdep.entity.*;
import com.dimata.qdep.form.*;
import com.dimata.common.db.*;

/* project package */
import com.dimata.posbo.entity.warehouse.*;
import com.dimata.posbo.session.warehouse.*;
import com.dimata.gui.jsp.ControlDate;
import com.dimata.common.entity.logger.PstDocLogger;
import com.dimata.common.entity.logger.PstLogSysHistory;

/*import payment terms */
/*import VendorPrice */
import com.dimata.posbo.entity.masterdata.PstMatVendorPrice;
import com.dimata.posbo.entity.masterdata.MatVendorPrice;

/**import system Property **/
import com.dimata.system.entity.*;

public class CtrlMatReceive extends Control implements I_Language {
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
    private MatReceive matReceive;
    private MatReceive prevMatReceive;
    private PstMatReceive pstMatReceive;
    private FrmMatReceive frmMatReceive;
    private HttpServletRequest req;
    Date dateLog = new  Date();
    int language = LANGUAGE_DEFAULT;

    public CtrlMatReceive() {
    }
    
    public CtrlMatReceive(HttpServletRequest request) {
        msgString = "";
        matReceive = new MatReceive();
        try {
            pstMatReceive = new PstMatReceive(0);
        } catch (Exception e) {
            ;
        }
        req = request;
        frmMatReceive = new FrmMatReceive(request, matReceive);
    }
    
    private String getSystemMessage(int msgCode) {
        switch (msgCode) {
            case I_DBExceptionInfo.MULTIPLE_ID:
                this.frmMatReceive.addError(frmMatReceive.FRM_FIELD_RECEIVE_MATERIAL_ID, resultText[language][RSLT_EST_CODE_EXIST]);
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
    
    public MatReceive getMatReceive() {
        return matReceive;
    }
    
    public FrmMatReceive getForm() {
        return frmMatReceive;
    }
    
    public String getMessage() {
        return msgString;
    }
    
    public int getStart() {
        return start;
    }
    
    public int action(int cmd, long oidMatReceive) {
        return action(cmd,oidMatReceive,"",0);
    }

    public int action(int cmd, long oidMatReceive, String nameUser, long userID) {
        msgString = "";
        int excCode = I_DBExceptionInfo.NO_EXCEPTION;
        int rsCode = RSLT_OK;
        switch (cmd) {
            case Command.ADD:
                break;
            case Command.SAVE:
                boolean incrementAllReceiveType = true;
                Date rDate = new Date();
                int recType = 0;
                int counter = 0;
                if (oidMatReceive != 0) {
                    try {
                        matReceive = PstMatReceive.fetchExc(oidMatReceive);
                        rDate = matReceive.getReceiveDate();
                        recType = matReceive.getReceiveStatus();
                        counter = matReceive.getRecCodeCnt();
                    } catch (Exception exc) {
                        System.out.println("Exc. when fetch  MatReceive: "+exc.toString());
                    }
                    try {
                        prevMatReceive = PstMatReceive.fetchExc(oidMatReceive);
                    } catch (Exception exc) {
                }
                
                }

                frmMatReceive.requestEntityObject(matReceive);
                Date date = ControlDate.getDateTime(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_DATE],req);
                matReceive.setReceiveDate(date);
                
                int docType = -1;
                try {
                    I_PstDocType i_pstDocType = (I_PstDocType) Class.forName(I_DocType.DOCTYPE_CLASSNAME).newInstance();
                    docType = i_pstDocType.composeDocumentType(I_DocType.SYSTEM_MATERIAL, I_DocType.MAT_DOC_TYPE_LMRR);
                } catch (Exception e) {
                    System.out.println("Exc : "+e.toString());
                }
                
                if (frmMatReceive.errorSize() > 0) {
                    msgString = FRMMessage.getMsg(FRMMessage.MSG_INCOMPLATE);
                    return RSLT_FORM_INCOMPLETE;
                }
                
                // set currency id untuk penerimaan antar lokasi
                CurrencyType defaultCurrencyType = new CurrencyType();
                if(this.matReceive.getSupplierId() == 0 && this.matReceive.getCurrencyId() == 0) {
                    defaultCurrencyType = PstCurrencyType.getDefaultCurrencyType();
                    this.matReceive.setCurrencyId(defaultCurrencyType.getOID());
                }
                
                /** Untuk mendapatkan besarnya standard rate per satuan default (:currency rate = 1)
                 * yang digunakan untuk nilai pada transaksi rate
                 * create by gwawan@dimata 16 Agutus 2007
                 * di disable oleh opie, karena untuk rate sudah di tentukan di jsp nya
                 */
                /*if(this.matReceive.getCurrencyId() != 0) {
                    String whereClause = PstStandartRate.fieldNames[PstStandartRate.FLD_CURRENCY_TYPE_ID]+" = "+this.matReceive.getCurrencyId();
                    whereClause += " AND "+PstStandartRate.fieldNames[PstStandartRate.FLD_STATUS]+" = "+PstStandartRate.ACTIVE;
                    Vector listStandardRate = PstStandartRate.list(0, 1, whereClause, "");
                    StandartRate objStandartRate = (StandartRate)listStandardRate.get(0);
                    this.matReceive.setTransRate(objStandartRate.getSellingRate());
                }
                else {
                    this.matReceive.setTransRate(0);
                }*/
                
                if (matReceive.getOID() == 0) {
                    try {
                        this.matReceive.setRecCodeCnt(SessMatReceive.getIntCode(matReceive, rDate, oidMatReceive, docType, counter, incrementAllReceiveType));
                        this.matReceive.setRecCode(SessMatReceive.getCodeReceive(matReceive));
                        matReceive.setLastUpdate(new Date());
                        //otomatis include ppn
                        double defaultPpn = Double.parseDouble(PstSystemProperty.getValueByName("POS_PPN_DEFAULT"));
                        if(defaultPpn !=0){
                           matReceive.setIncludePpn(1);
                        }
                        long oid = pstMatReceive.insertExc(this.matReceive);

                        //PROSES UNTUK MENYIMPAN HISTORY JIKA OID DARI ORDER PO =! 0
                        if(oid!=0)
                        {
                            insertHistory(userID, nameUser, cmd, oid);
                        }
//
//
//                        /**
//                         * gadnyana
//                         * untuk insert ke doc logger
//                         * jika tidak di perlukan uncomment
//                         */
//                        //PstDocLogger.insertDataBo_toDocLogger(matReceive.getRecCode(), I_IJGeneral.DOC_TYPE_PURCHASE_ON_LGR, matReceive.getLastUpdate(), matReceive.getRemark());
//                        //--- end
//                        System.out.println("action comlpete....");
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                        return getControlMsgId(excCode);
                    } catch (Exception exc) {
                        System.out.println("exception: "+exc.toString());
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                        return getControlMsgId(I_DBExceptionInfo.UNKNOWN);
                    }
                    
                } else {
                    try {
                        matReceive.setLastUpdate(new Date());
                        
                        long oid = pstMatReceive.updateExc(this.matReceive);
                        
//                        //proses posting
//                        int typeOfBussiness = Integer.parseInt(PstSystemProperty.getValueByName("TYPE_OF_BUSINESS"));
//                        
//                        //typer retail
//                            if(typeOfBussiness==I_ApplicationType.APPLICATION_DISTRIBUTIONS){
//                               SessPosting sessPosting = new SessPosting();
//                               switch (matReceive.getReceiveStatus()) {
//                                    case I_DocStatus.DOCUMENT_STATUS_APPROVED:
//                                            boolean isOKP =  sessPosting.postedQtyReceiveOnlyDoc(oid);
//                                            if(isOKP){
//                                               matReceive.setReceiveStatus(I_DocStatus.DOCUMENT_STATUS_APPROVED);     
//                                            }
//                                        break;
//                                     case I_DocStatus.DOCUMENT_STATUS_FINAL:
//                                            boolean isOK = sessPosting.postedValueReceiveOnlyDoc(oid);
//                                            if(isOK){
//                                               matReceive.setReceiveStatus(I_DocStatus.DOCUMENT_STATUS_CLOSED);     
//                                            }
//                                        break;
//
//                                    default:
//                                       // break;
//                               }
//                            //maka statusnya final = posting value
//                            }else{
//                                //type kecuali retail, klo final langsung posting
//                                if(matReceive.getReceiveStatus()==I_DocStatus.DOCUMENT_STATUS_FINAL){
//                                      SessPosting sessPosting = new SessPosting();
//                                      boolean isOK = sessPosting.postedReceiveDoc(oid);
//                                      if(isOK){
//                                               matReceive.setReceiveStatus(I_DocStatus.DOCUMENT_STATUS_CLOSED);     
//                                      }
//                                }
//                            }
                        
                        
                        if(oid!=0)
                        {
                            insertHistory(userID, nameUser, cmd, oid);
                        }

                        double ppn = matReceive.getTotalPpn();
                        int includePpn = matReceive.getIncludePpn();
                        
                        //disini update jika new supplier ga perlu di update
                        long mappingNewSupplier = 0;
                        try{
                          mappingNewSupplier=  Long.parseLong(PstSystemProperty.getValueByName("MAPPING_NEW_SUPPLIER_ID"));
                        }catch(Exception ex){}
                        
                        if(mappingNewSupplier!=matReceive.getSupplierId()){
                            if(matReceive.getReceiveStatus()==I_DocStatus.DOCUMENT_STATUS_CLOSED){
                            
                            //update Ppn di Vendor Price
                            CtrlMatReceiveItem ctrlMatReceiveItem = new CtrlMatReceiveItem();
                            Vector ListMatReceiveItem = new Vector();
                            String whereClauseItem = ""+PstMatReceiveItem.fieldNames[PstMatReceiveItem.FLD_RECEIVE_MATERIAL_ID]+"="+oid;
                            ListMatReceiveItem = PstMatReceiveItem.list(0, 0, whereClauseItem, "");
                            for(int l=0; l<ListMatReceiveItem.size(); l++) {
                                MatReceiveItem matReceiveItem = new MatReceiveItem();
                                matReceiveItem = (MatReceiveItem)ListMatReceiveItem.get(l);
                                   long oidMatReceivePpn = matReceiveItem.getReceiveMaterialId();
                                   long oidMatReceiveItemPpn = matReceiveItem.getOID();
                                   //long oidMaterial = matReceiveItem.getMaterialId();

                                    double newCost = matReceiveItem.getCost();
                                    double lastDisc = matReceiveItem.getDiscount();
                                    double lastDisc2 = matReceiveItem.getDiscount2();
                                    double lastDiscNom = matReceiveItem.getDiscNominal();
                                    double newForwarderCost = matReceiveItem.getForwarderCost();
                                    //+ppn
                                    //double Ppn = matReceive.getTotalPpn();

                                    MatVendorPrice matVdrPrc = new MatVendorPrice();
                                    matVdrPrc.setVendorId(matReceive.getSupplierId());
                                    matVdrPrc.setPriceCurrency(matReceive.getCurrencyId());
                                    matVdrPrc.setMaterialId(matReceiveItem.getMaterialId());
                                    matVdrPrc.setBuyingUnitId(matReceiveItem.getUnitId());
                                    matVdrPrc.setOrgBuyingPrice(matReceiveItem.getCost());
                                    //matVdrPrc.setCurrBuyingPrice(matReceiveItem.getCost());
                                    matVdrPrc.setLastDiscount(matReceiveItem.getDiscNominal());

                                    //set Discount1 & Discount2, ForwarderCost
                                    matVdrPrc.setLastDiscount1(lastDisc);
                                    matVdrPrc.setLastDiscount2(lastDisc2);
                                    matVdrPrc.setLastCostCargo(newForwarderCost);

                                    //set Total Ppn by Mirahu 25 Mei 2011
                                    matVdrPrc.setLastVat(ppn);


                                    //calculate hpp & discount
                                    // By Mirahu 25 Februari2011
                                    double totalDiscount = newCost * lastDisc/100;
                                    double totalMinus = newCost - totalDiscount;
                                    double totalDiscount2 = totalMinus * lastDisc2/100;
                                    double totalCost = (totalMinus - totalDiscount2)-lastDiscNom;
                                     //update cost + PPn by Mirahu 25 Mei 2011
                                      //double totalCostAll = totalCost + newForwarderCost;
                                      //matVdrPrc.setCurrBuyingPrice(totalCostAll);

                                     //include or not include
                                        double valuePpn = 0.0;
                                        double totalCostAll = 0.0;

                                        if(includePpn == PstMatReceive.INCLUDE_PPN){
                                            valuePpn =totalCost - (totalCost /1.1);
                                            totalCostAll = totalCost +  newForwarderCost;
                                            matVdrPrc.setLastVat(0);
                                        }
                                        else if(includePpn== PstMatReceive.EXCLUDE_PPN){
                                            valuePpn = totalCost * ppn / 100;
                                            double totalCostAllPpn = totalCost + valuePpn;
                                            totalCostAll = totalCostAllPpn + newForwarderCost;
                                        }
                                      matVdrPrc.setCurrBuyingPrice(totalCostAll);

                                    PstMatVendorPrice.insertUpdateExc(matVdrPrc);

                            }
                          }
                        }     
                        
                        //update ppn di VendorPrice
                        /**
                         * gadnyana
                         * untuk insert ke doc logger
                         * jika tidak di perlukan uncomment
                         */
                        PstDocLogger.updateDataBo_toDocLogger(matReceive.getRecCode(), I_IJGeneral.DOC_TYPE_PURCHASE_ON_LGR, matReceive.getLastUpdate(), matReceive.getRemark());
                        //--- end
                        
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                
                /** set status pada forwarder info dengan value terkini! */
                try {
                    String whereClause = ""+PstForwarderInfo.fieldNames[PstForwarderInfo.FLD_RECEIVE_ID]+"="+this.matReceive.getOID();
                    Vector vctListFi = PstForwarderInfo.list(0, 0, whereClause, "");
                    ForwarderInfo forwarderInfo = new ForwarderInfo();
                    for(int j=0; j<vctListFi.size(); j++) {
                        forwarderInfo = (ForwarderInfo)vctListFi.get(j);
                        forwarderInfo.setStatus(this.matReceive.getReceiveStatus());
                        long oid = PstForwarderInfo.updateExc(forwarderInfo);
                    }
                }
                catch(Exception e) {
                    System.out.println("Exc in update status, forwarder_info >>> "+e.toString());
                }
                
                break;
                
            case Command.EDIT:
                if (oidMatReceive != 0) {
                    try {
                        matReceive = PstMatReceive.fetchExc(oidMatReceive);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.ASK:
                if (oidMatReceive != 0) {
                    try {
                        matReceive = PstMatReceive.fetchExc(oidMatReceive);
                    } catch (DBException dbexc) {
                        excCode = dbexc.getErrorCode();
                        msgString = getSystemMessage(excCode);
                    } catch (Exception exc) {
                        msgString = getSystemMessage(I_DBExceptionInfo.UNKNOWN);
                    }
                }
                break;
                
            case Command.DELETE:
                if (oidMatReceive != 0) {
                    try {
                        // memproses item penerimaan barang
                        CtrlMatReceiveItem objCtlItem = new CtrlMatReceiveItem();
                        MatReceiveItem objItem = new MatReceiveItem();
                        String stWhereClose = PstMatReceiveItem.fieldNames[PstMatReceiveItem.FLD_RECEIVE_MATERIAL_ID] + " = " + oidMatReceive;
                        Vector vListItem = PstMatReceiveItem.list(0, 0, stWhereClose, "");
                        if (vListItem != null && vListItem.size() > 0) {
                            for (int i = 0; i < vListItem.size(); i++) {
                                objItem = (MatReceiveItem) vListItem.get(i);
                                objCtlItem.action(Command.DELETE, objItem.getOID(), oidMatReceive);

                                //delete serial code penerimaan dari toko/gudang jika statusnya di balik dari final ke draft
                                
                            }
                        }
                        
                        /** gadnyan
                         * proses penghapusan di doc logger
                         * jika tidak di perlukan uncoment perintah ini
                         */
                        matReceive = PstMatReceive.fetchExc(oidMatReceive);
                        PstDocLogger.deleteDataBo_inDocLogger(matReceive.getRecCode(),I_DocType.MAT_DOC_TYPE_LMRR);
                        // -- end
                        
                        
                        /** delete forwarder information berdasarkan dok. receive */
                        String whereClause = ""+PstForwarderInfo.fieldNames[PstForwarderInfo.FLD_RECEIVE_ID]+"="+matReceive.getOID();
                        Vector vctListFi = PstForwarderInfo.list(0, 0, whereClause, "");
                        ForwarderInfo forwarderInfo = new ForwarderInfo();
                        CtrlForwarderInfo ctrlForwarderInfo = new CtrlForwarderInfo();
                        for(int j=0; j<vctListFi.size(); j++) {
                            forwarderInfo = (ForwarderInfo)vctListFi.get(j);
                            ctrlForwarderInfo.action(Command.DELETE, forwarderInfo.getOID());
                        }
                        
                        /** delete receive */
                        long oid = PstMatReceive.deleteExc(oidMatReceive);
                        if (oid != 0) {
                            msgString = FRMMessage.getMessage(FRMMessage.MSG_DELETED);
                            excCode = RSLT_OK;
                            try{
                                insertHistory(userID, nameUser, cmd, oid);
                            }catch(Exception e){

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
                
            default :
                
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
           logSysHistory.setLogOpenUrl("material/receive/receive_wh_supp_po_material_edit.jsp");
           logSysHistory.setLogUpdateDate(dateLog);
           logSysHistory.setLogDocumentType(I_DocType.documentTypeNames[0][1]);
           logSysHistory.setLogUserAction(Command.commandString[cmd]);
           logSysHistory.setLogDocumentNumber(matReceive.getRecCode());
           logSysHistory.setLogDocumentId(oid);
           logSysHistory.setLogDetail(this.matReceive.getLogDetail(prevMatReceive));

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
