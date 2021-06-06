
package com.dimata.cashierweb.ajax.cashier;

import com.dimata.cashierweb.entity.admin.AppUser;
import com.dimata.cashierweb.entity.admin.PstAppUser;
import com.dimata.cashierweb.entity.cashier.transaction.BillMainCostum;
import com.dimata.cashierweb.entity.cashier.transaction.PaymentRec;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain;
import com.dimata.cashierweb.entity.cashier.transaction.PstPaymentRec;
import com.dimata.cashierweb.entity.cashier.transaction.Reservation;
import com.dimata.cashierweb.entity.masterdata.Material;
import com.dimata.cashierweb.entity.masterdata.PstMaterial;
import com.dimata.cashierweb.form.cashier.CtrlBillMain;
import com.dimata.cashierweb.form.cashier.CtrlCustomBillDetail;
import com.dimata.cashierweb.form.cashier.CtrlCustomPayment;
import com.dimata.cashierweb.session.cashier.SessUserCashierSession;
import com.dimata.common.db.DBException;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.custom.DataCustom;
import com.dimata.common.entity.custom.PstDataCustom;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.logger.LogSysHistory;
import com.dimata.common.entity.logger.PstLogSysHistory;
import com.dimata.common.entity.payment.CurrencyType;
import com.dimata.common.entity.payment.DailyRate;
import com.dimata.common.entity.payment.PaymentSystem;
import com.dimata.common.entity.payment.PstCurrencyType;
import com.dimata.common.entity.payment.PstDailyRate;
import com.dimata.common.entity.payment.PstPaymentSystem;
import com.dimata.common.entity.system.PstSystemProperty;
import com.dimata.gui.jsp.ControlCombo;
import com.dimata.hanoman.entity.masterdata.MasterType;
import com.dimata.hanoman.entity.masterdata.PstContact;
import com.dimata.hanoman.entity.masterdata.PstMasterType;
import com.dimata.pos.entity.balance.Balance;
import com.dimata.pos.entity.balance.CashCashier;
import com.dimata.pos.entity.balance.PstBalance;
import com.dimata.pos.entity.balance.PstCashCashier;
import com.dimata.pos.entity.billing.BillDetailCode;
import com.dimata.pos.entity.billing.BillMain;
import com.dimata.pos.entity.billing.Billdetail;
import com.dimata.pos.entity.billing.PstBillDetail;
import com.dimata.pos.entity.billing.PstBillDetailCode;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.entity.masterCashier.CashMaster;
import com.dimata.pos.entity.masterCashier.PstCashMaster;
import com.dimata.pos.entity.payment.CashCreditCard;
import com.dimata.pos.entity.payment.CashPayments;
import com.dimata.pos.entity.payment.CashPayments1;
import com.dimata.pos.entity.payment.CashReturn;
import com.dimata.pos.entity.payment.PstCashCreditCard;
import com.dimata.pos.entity.payment.PstCashPayment;
import com.dimata.pos.entity.payment.PstCashPayment1;
import com.dimata.pos.entity.payment.PstCashReturn;
import com.dimata.pos.form.balance.CtrlCloseCashCashier;
import com.dimata.pos.form.billing.CtrlBillDetail;
import com.dimata.pos.form.billing.FrmBillDetail;
import com.dimata.pos.form.billing.FrmBillMain;
import com.dimata.pos.form.payment.CtrlCashCreditCard;
import com.dimata.pos.form.payment.CtrlCashPayment;
import com.dimata.pos.form.payment.CtrlCashReturn;
import com.dimata.pos.form.payment.FrmCashCreditCard;
import com.dimata.pos.form.payment.FrmCashPayment;
import com.dimata.posbo.entity.masterdata.PstMaterialStock;
import com.dimata.posbo.entity.masterdata.Shift;
import com.dimata.posbo.session.warehouse.SessMatCostingStokFisik;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CashierMaintenance extends HttpServlet {
    
    String loginIds = "";
    long userIds = 0;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String result ="";
        PrintWriter out = response.getWriter();
        int command = FRMQueryString.requestCommand(request);
        String loadType = FRMQueryString.requestString(request, "loadType");
        
        HttpSession session = request.getSession();
        SessUserCashierSession userCashier = (SessUserCashierSession) session.getValue(SessUserCashierSession.HTTP_SESSION_CASHIER);
	
	try{
	    if(userCashier==null){
		userCashier= new SessUserCashierSession();
	    }else{
		if(userCashier.isLoggedIn()==true){		    
		    AppUser appUser = userCashier.getAppUser();
		    loginIds = appUser.getLoginId();
		    userIds = appUser.getOID();
		}
	    }
	}catch (Exception exc){
	    System.out.println(" >>> Exception during check login");
	}
        
        if (command==Command.LIST){
            if (loadType.equals("listTransaction")){
                result = listTransaction(request);                
            }else if (loadType.equals("listDetailTransaction")){
                result = listDetailTransaction(request);
            }else if (loadType.equals("listPaymentTransaction")){
                result = listPaymentTransaction(request);
            }else if (loadType.equals("loadListCashCashier")){
                result =loadListCashCashier(request);
            }
            out.print(result);
        }else if (command==Command.LOAD){
            if (loadType.equals("editItem")){
                result = loadEditBillDetail(request);
            }else if (loadType.equals("deleteItem")){
                result = loadDeleteBillDetail(request);
            } else if (loadType.equals("loadEditPayment")){
                result = loadEditPayment(request);
            }
            out.print(result);
        }else if (command==Command.SAVE){
            if (loadType.equals("editItemProc")){
                int fromEditable =0;
                try {
                    fromEditable = FRMQueryString.requestInt(request, "fromEditable");
                }catch(Exception ex){
                    fromEditable = 0;
                }
                try{
                    long oidBillDetail = FRMQueryString.requestLong(request, "billDetailId");
                    CtrlCustomBillDetail ctrlBillDetail = new CtrlCustomBillDetail(request);
                    int iErrCode = ctrlBillDetail.action(command,oidBillDetail,request);
                    
                    if (iErrCode==0 && fromEditable==1){
                        //JIKA ASAL REQUEST DARI TRANSACTION MAINTENANCE, MAKA MASUK KE BAGIAN INI
                        BillMain billMain = new BillMain();
                        long oidBillMain = FRMQueryString.requestLong(request, "FRM_FIELD_CASH_BILL_MAIN_ID");
                        try {
                            billMain = PstBillMain.fetchExc(oidBillMain);
                            double total = PstBillDetail.getTotalPrice2(oidBillMain);
                            billMain.setAmount(total);
                            CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
                            iErrCode = ctrlBillMain.actionObject(Command.SAVE, billMain, request);
                        } catch (Exception e) {
                            
                        }
                    }

                    if (iErrCode==0){
                        //SIMPAN KE HISTORY
                        long oidBillMain = FRMQueryString.requestLong(request, "FRM_FIELD_CASH_BILL_MAIN_ID");
                        insertHistory(userIds, loginIds, command, oidBillMain, "EDIT EXISTING ITEM IN THE BILL");
                    }

                    String frmMsg = ctrlBillDetail.getMessage();
                }catch(Exception ex){

                }
            }else if (loadType.equals("delitemproccess")){
                deleteProccess(request);
            }else if (loadType.equals("deleteItemPayment")){
                deletePaymentProcess(request);
            }else if (loadType.equals("saveAddItemProc")){
                result = savePayment(request);
            }else if (loadType.equals("cashCashierEdit")){
                cashCashierEdit(request);
            }else if (loadType.equals("saveHistory")){
                saveHistory(request);
            }else if (loadType.equals("newOrder")){
                newOrder(request);               
            }else if (loadType.equals("deleteTransaction")){
                deleteTransaction(request);
            }else if (loadType.equals("openTransaction")){
                openTransaction(request);
            }else if (loadType.equals("cancelTransaction")){
                cancelTransaction(request);
            }
            out.print(result);
        }
    }
    
    public String newOrder(HttpServletRequest request){
        String result = "";
        
        CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
        BillMain billMain = new BillMain();
        
        billMain = ctrlBillMain.getBillMain();
        billMain.setDocType(0);
        billMain.setTransactionStatus(0);
        billMain.setTransctionType(0);
        
        
        return result;
    }
    
    public String listTransaction(HttpServletRequest request){
        String container = "";
        String where = "";
        int counter = 0;

        int showPage = 1;
        int jmlShowData = 10;
        String research = "";
        int c = 0;

        try {
            showPage = FRMQueryString.requestInt(request, "showPage");
        } catch (Exception e) {
            showPage = 1;
        }

        try {
            research = FRMQueryString.requestString(request, "search");
        } catch (Exception e) {
            research = "";
        }

        if (showPage == 0) {
            showPage = 1;
        }

        int start = (showPage - 1) * jmlShowData;

        String startDate = FRMQueryString.requestString(request, "startDate");
        String endDate = FRMQueryString.requestString(request, "endDate");
        long oidLocation = FRMQueryString.requestLong(request, "oidLocation");
        String noInvoice = FRMQueryString.requestString(request, "noInvoice");

        if (endDate == "" && startDate != "") {
            endDate = startDate;
        } else if (endDate != "" && startDate == "") {
            startDate = endDate;
        } else if (endDate == "" && startDate == "") {
            startDate = "";
        }

        where = PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "=" + PstBillMain.TYPE_INVOICE + "";
        
        if (!startDate.equals("")) {
            where += ""
                    + " AND DATE(" + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + startDate + "'"
                    + " AND DATE(" + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") <= '" + endDate + "'";
        }

        if (oidLocation != 0) {
            where += " AND " + PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID] + "=" + oidLocation + "";
        }

        if (noInvoice != "") {
            where += " AND " + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NUMBER] + " LIKE '%" + noInvoice + "%'";
        }

        String useForGB = PstSystemProperty.getValueByName("USE_FOR_GREENBOWL");
        if (useForGB.equals("1")) {
            where += where.isEmpty() ? "" : " AND ";
            where += PstBillMain.fieldNames[PstBillMain.FLD_BILL_STATUS] + " < " + I_DocStatus.DOCUMENT_STATUS_CLOSED;
        }

        String order = "" + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " DESC";

        Vector list = PstBillMain.list(start, jmlShowData, where, order);
        int jmlData = PstBillMain.getCount(where);
                    
        double tamp =Math.floor(jmlData/jmlShowData);
        double tamp2 = jmlData % jmlShowData;
        if (tamp2>0){
            tamp2= 1;	
        } 
        double JumlahHalaman = tamp2 + tamp;

        String attDisFirst ="";
        String attDisNext ="";
        
        if (showPage==1){
            attDisFirst= "disabled";
        }

        if (showPage==JumlahHalaman){
            attDisNext ="disabled";
        }
        
        c = start+1;
        
        container += ""
        + "<table class='table table-bordered table-striped'>"
            + "<thead>"
                + "<tr>"
                    + "<th style='width: 4%;'>No</th>"
                    + "<th style='width: 15%;'>Invoice</th>"
                    + "<th>Location</th>"
                    + "<th style='width: 10%;'>Date</th>"
                    + "<th style='width: 12%;'>Note</th>"
                    + "<th style='width: 13%;'>Customer Name</th>"
                    + "<th>Bill Status</th>"
                    + "<th style='width:1%'>Action</th>"
                + "</tr>"
            + "</thead>"
            + "<tbody>";
        
            if (list.size()>0){
                for (int i=0;i<list.size();i++){
                    counter = i + 1;
                    BillMain billMain = (BillMain) list.get(i);
                    Location location = new Location();
                    ContactList contactList = new ContactList();
                    try {
                        location = PstLocation.fetchExc(billMain.getLocationId());
                    } catch (Exception e) {
                    }
                    try {
                        contactList = PstContactList.fetchExc(billMain.getCustomerId());
                    } catch (Exception e) {
                    }
                    
                    String buttonEdit = " <button data-oid='"+billMain.getOID()+"' type='button' class='btn btn-warning editTransaction'><i class='fa fa-pencil'></i> Edit</button>";
                    String buttonCancel = " <button data-oid='"+billMain.getOID()+"' type='button' class='btn btn-danger cancelTransaction'><i class='fa fa-remove'></i> Cancel</button>";
                    //-----> !!! WARNING !!! <-----
                    //TOMBOL DELETE TIDAK DIPAKAI LAGI, DIGANTI DENGAN TOMBOL CANCEL, KARENA BERESIKO KALAU MENGHAPUS DATA BILL
                    String buttonDelete = " <button data-oid='"+billMain.getOID()+"' type='button' class='btn btn-danger deleteTransaction'><i class='fa fa-trash'></i> Delete</button>";
                    
                    if (billMain.getBillStatus() == I_DocStatus.DOCUMENT_STATUS_CLOSED || billMain.getBillStatus() == I_DocStatus.DOCUMENT_STATUS_POSTED) {
                        buttonEdit = "";
                        buttonCancel = "";
                    }
                    
                    //BILL YG DI CANCEL TIDAK DAPAT DI EDIT
                    if (billMain.getDocType() == PstBillMain.TYPE_INVOICE && billMain.getTransType() == PstBillMain.TRANS_TYPE_CASH && billMain.getTransactionStatus() == PstBillMain.TRANS_STATUS_DELETED && billMain.getStatusInv() == PstBillMain.INVOICING_ON_PROSES) {
                        buttonEdit = "";
                        buttonCancel = "";
                        billMain.setBillStatus(I_DocStatus.DOCUMENT_STATUS_CANCELLED);
                    }
                    
                    container += ""
                    + "<tr>"
                        + "<td>"+c+"</td> "
                        + "<td>"+billMain.getInvoiceNumber()+"</td> "
                        + "<td>"+location.getName()+"</td> "
                        + "<td>"+Formater.formatDate(billMain.getBillDate(), "MM/dd/yyyy")+"</td> "
                        + "<td>"+billMain.getNotes()+"</td> "
                        + "<td>"+contactList.getPersonName()+"</td> "
                        + "<td>"+I_DocStatus.fieldDocumentStatus[billMain.getBillStatus()]+"</td> "
                        + "<td style='white-space: nowrap'>";
                            if (billMain.getBillStatus() == I_DocStatus.DOCUMENT_STATUS_CLOSED) {
                                container += ""
                                    + " <button data-oid='"+billMain.getOID()+"' type='button' class='btn btn-success openTransaction'><i class='fa fa-folder-open'></i> Open</button>";
                            }
                            container += buttonEdit + buttonCancel
                        + "</td> "
                    + "</tr>";
                    
                    c++;
                }       
            }else{
                container += ""
                    + "<tr>"
                        + "<td colspan='8'><center>No Data</center></td> "
                    + "</tr>";
            }
            
        container += ""
            + "</tbody>"
            + "<tfoot>"
                + "<tr>"
                    + "<td colspan='7'>&nbsp;</td>"
                + "</tr>"
            + "</tfoot>"
        + "</table>"
        + "<div class='row'>"
            + "<input type='hidden' id='totalPaging' value='"+Formater.formatNumber(JumlahHalaman,"")+"'>"
            + "<input type='hidden' id='paggingPlace' value='"+showPage+"'>"
            + "<div class='col-md-6'>"
                + "<label>"
                    + "Page "+showPage+" of "+Formater.formatNumber(JumlahHalaman, "")+""
                + "</label>"
            + "</div>"
            + "<div class='col-md-6'>"
                + "<div data-original-title='Status' class='box-tools pull-right' title=''>" 
                    + "<div class='btn-group' data-toggle='btn-toggle'>" 
                        + "<button "+attDisFirst+" data-type='first' type='button' class='btn btn-default pagings'>"
                            + "<i class='fa fa-angle-double-left'></i>"
                        + "</button>" 
                        + "<button id='prevPaging' "+attDisFirst+" data-type='prev' type='button' class='btn btn-default pagings'>"
                            + " <i class='fa fa-angle-left'></i>"
                        + "</button>" 
                        + "<button id='nextPaging' "+attDisNext+" data-type='next' type='button' class='btn btn-default pagings'>"
                            + "<i class='fa fa-angle-right'></i>"
                        + "</button>" 
                        + "<button "+attDisNext+" data-type='last' type='button' class='btn btn-default pagings'>"
                            + " <i class='fa fa-angle-double-right'></i>"
                        + "</button>" 
                    + "</div>" 
                + "</div>"                           
            + "</div>"                          
        + "</div>"
        + "";
          
        return container;
    }
    
    public String listDetailTransaction(HttpServletRequest request){
        String container = "";
        double totalTrans = 0;
        long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
        BillMain billMain = new BillMain();
        
        try {
            billMain = PstBillMain.fetchExc(oidBillMain);
        } catch (Exception e) {
        }
        
        String sWhereClause = PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + " = " + oidBillMain;
        Vector records = PstBillDetail.list(0, 0, sWhereClause, "");
        
        container +=""
            + "<table class='table table-bordered table-striped'>"
                + "<thead>"
                    + "<tr class='info'>"
                        + "<th style='width: 1%;'>No</th>"
                        + "<th>SKU</th>"
                        + "<th>Barcode</th>"
                        + "<th>Item Name</th>"
                        + "<th>Serial No.</th>"
                        + "<th>Price</th>"
                        + "<th>Disc.</th>"
                        + "<th>Qty</th>"
                        + "<th>Total</th>"
                        + "<th style='width: 1%;'>Action</th>"
                    + "</tr>"
                + "</thead>"
                + "<tbody>";
        
                double returBruto = 0;
                if(records!=null && records.size()>0){
                    int maxDetail = records.size();	
                    int counter = 0;
                    for(int i=0; i<maxDetail; i++){
                        Billdetail objBillDetail = (Billdetail) records.get(i);										
                        BillDetailCode objBillDetailCode = PstBillDetailCode.getBillDetailCode(objBillDetail.getOID());
                        returBruto = returBruto + objBillDetail.getTotalPrice();
                        if (objBillDetail.getMaterialId() != 0) {
                            try {
                                objBillDetail.setBarcodeMat(PstMaterial.fetchExc(objBillDetail.getMaterialId()).getBarCode());
                            } catch (Exception e) {
                                
                            }
                        }
                        counter = i + 1;
                        container += ""
                        + "<tr>"
                            + "<td>"+counter+"</td>"
                            + "<td>"+objBillDetail.getSku()+"</td>"
                            + "<td>"+objBillDetail.getBarcodeMat()+"</td>"
                            + "<td>"+objBillDetail.getItemName()+"</td>"
                            + "<td>"+objBillDetailCode.getStockCode()+"</td>"
                            + "<td style='text-align:right;'>"+FRMHandler.userFormatStringDecimal(objBillDetail.getItemPrice())+"</td>"
                            + "<td style='text-align:right;'>"+FRMHandler.userFormatStringDecimal(objBillDetail.getDisc())+"</td>"
                            + "<td style='text-align:right;'>"+objBillDetail.getQty()+"</td>"
                            + "<td style='text-align:right;'>"+FRMHandler.userFormatStringDecimal(objBillDetail.getTotalPrice())+"</td>"
                            + "<td>"
                                + "<div class='dropdown'>"
                                    + "<button class='btn btn-primary dropdown-toggle' type='button' data-toggle='dropdown'>Actions "
                                    + "<span class='fa fa-caret-right'></span></button>"
                                    + "<ul class='dropdown-menu dropdown-menu-right' role='menu' style='position: absolute;top:-100%;'>"
                                        + "<li role='presentation'><a role='menuitem' tabindex='-1' href='#' data-load-type='editItem' class='authorize editmainitem' id='editmainitem_"+i+"' data-title='EDIT ITEM' data-billmainoid='"+billMain.getOID()+"' data-oid='"+objBillDetail.getOID()+"' data-materialoid='"+objBillDetail.getMaterialId()+"'><i class='fa fa-pencil'></i> Edit</a></li>"
                                        + "<li role='presentation'><a role='menuitem' tabindex='-1' href='#' data-load-type='delItem' class='authorize deletemainitem' id='deletemainitem_"+i+"'  data-title='DELETE ITEM' data-billmainoid='"+billMain.getOID()+"' data-oid='"+objBillDetail.getOID()+"' data-materialoid='"+objBillDetail.getMaterialId()+"'><i class='fa fa-remove'></i>Delete</a></li>"
                                    + "</ul>"
                                + "</div>"
                            + "</td>"
                        + "</tr>";                       
                    }
                    container += ""
                    + "<tr>"
                        + "<td colspan='7'>&nbsp;</td>"
                        + "<td style='text-align:right;'><b>Total Transaction</b></td>"
                        + "<td style='text-align:right;'>"+FRMHandler.userFormatStringDecimal(returBruto)+"</td>"
                        + "<td>&nbsp;</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td colspan='7'>&nbsp;</td>"
                        + "<td style='text-align:right;'><b>Total Disc</b></td>"
                        + "<td style='text-align:right;'>"+FRMHandler.userFormatStringDecimal(billMain.getDiscount())+"</td>"
                        + "<td>&nbsp;</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td colspan='7'>&nbsp;</td>"
                        + "<td style='text-align:right;'><b>Tax</b></td>"
                        + "<td style='text-align:right;'>"+FRMHandler.userFormatStringDecimal(billMain.getTaxValue())+"</td>"
                        + "<td>&nbsp;</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td colspan='7'>&nbsp;</td>"
                        + "<td style='text-align:right;'><b>Service</b></td>"
                        + "<td style='text-align:right;'>"+FRMHandler.userFormatStringDecimal(billMain.getServiceValue())+"</td>"
                        + "<td>&nbsp;</td>"
                    + "</tr>";
                    double netTrans = returBruto - billMain.getDiscount() + billMain.getTaxValue() + billMain.getServiceValue(); 
                    totalTrans = netTrans;
                    container += ""                 
                    + "<tr>"
                        + "<td colspan='7'>&nbsp;</td>"
                        + "<td style='text-align:right;'><b>Net</b></td>"
                        + "<td style='text-align:right;'>"+FRMHandler.userFormatStringDecimal(netTrans)+"</td>"
                        + "<td>&nbsp;</td>"
                    + "</tr>";
                }else{
                    container += ""
                    + "<tr><td colspan='10'><center><b>No Data</b></center></td></tr>";
                }
        
        container +=""
                + "</tbody>"
            + "</table>"
            + "<input type='hidden' id='totalTransactionTemp' value='"+totalTrans+"'>"
            + "<button id='addItem' type='button' class='btn btn-primary pull-right'><i class='fa fa-plus'></i> Add Item</button>";
    
        return container;
    } 
    
    public String listPaymentTransaction(HttpServletRequest request){
        String result ="";
        
        long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
        BillMain billMain = new BillMain();
        
        try {
            billMain = PstBillMain.fetchExc(oidBillMain);
        } catch (Exception e) {
        }
        
        int paymentDinamis = Integer.parseInt(PstSystemProperty.getValueByName("USING_PAYMENT_DYNAMIC"));
        String strTemp = "";
        Vector vResult = new Vector();
        
        if (oidBillMain!=0 && billMain.getTransType()==PstCashPayment.CASH){
            PstCashPayment objPstCashPayment = new PstCashPayment();
            PstCashPayment1 objPstCashPaymentDinamis = new PstCashPayment1();
            if(paymentDinamis == PstPaymentSystem.USING_PAYMENT_DINAMIS) {
                vResult = objPstCashPaymentDinamis.getListPaymentDinamis2(oidBillMain);
                strTemp = drawListPaymentDynamic(vResult,oidBillMain); 
            }else{
                vResult = objPstCashPayment.getListPayment(oidBillMain);
                strTemp = drawListPayment(vResult);
            }
            result +=""
            + "<div class='row'>"
                + "<div class='col-md-12'>"
                    + "<div class='box box-primary'>"
                        + "<div class='box-header with-border'>"
                            + "<h3 class='box-title'>Payment List</h3>"
                        + "</div>"
                        + "<div class='box-body' >"
                            + strTemp
                            + "<br>"
                            + "<button id='addItemPayment' type='button' class='btn btn-primary pull-right'><i class='fa fa-plus'></i> Add Payment</button>"
                        + "</div>"
                    + "</div>"
                + "</div>"
            + "</div>";        
        }
        
        

        return result;
    }
    
    public String loadEditBillDetail(HttpServletRequest request){
        String container ="";
        
        try{
            long oidLocation = 0;
            String priceType = "0";
            //get Material Data
            long billMainOid = FRMQueryString.requestLong(request, "billMainOid");
            long billDetailOid = FRMQueryString.requestLong(request, "billDetailOid");
            long materialOid = FRMQueryString.requestLong(request, "materialOid");
            
            Billdetail billdetail = PstBillDetail.fetchExc(billDetailOid);
            Vector listTaxService = PstCustomBillMain.listTaxService(0, 0, 
                "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+billdetail.getBillMainId()+"'", "");
            BillMainCostum billMainCostum;
            
            double balance = (billdetail.getQty()*billdetail.getItemPrice())-billdetail.getDisc();
            String readonly = "";
            String incTaxService = "";

            double amountService = 0;
            double amountTax = 0;
            double afterService = 0;
            double afterTax = 0;

            /* cek stok */
            BillMain billMain = new BillMain();
            try {
                billMain = PstBillMain.fetchExc(billdetail.getBillMainId());
            } catch (Exception exc){}
            CashMaster cashMaster = new CashMaster();
            try {
                cashMaster = PstCashMaster.fetchExc(PstCashCashier.fetchExc(billMain.getCashCashierId()).getCashMasterId());
            } catch (Exception exc){

            }
            double qtyStockStockCard = PstMaterialStock.cekQtyStock(0,billdetail.getMaterialId(),cashMaster.getLocationId());
            double qtyStockRealTime = SessMatCostingStokFisik.qtyMaterialBasedOnOpeningCashier(Long.valueOf(billdetail.getMaterialId()), new Date());
            double qtyStock = qtyStockStockCard - qtyStockRealTime + billdetail.getQty();
                            
            try{
                billMainCostum = (BillMainCostum) listTaxService.get(0);

                amountService = balance*(billMainCostum.getServicePct()/100);
                amountTax = balance*(billMainCostum.getTaxPct()/100);
                afterService = balance+amountService;
                afterTax = balance+amountService+amountTax;

                if(billMainCostum.getTaxInc() == 0){
                    balance = balance;
                    readonly="readonly='readonly'";
                    incTaxService = "<small>(include tax & service)</small>";
                }else if(billMainCostum.getTaxInc() == 1){
                    balance = (balance)+amountService+amountTax;
                    readonly="readonly='readonly'";
                    incTaxService = "<small>(exclude tax & service)</small>";
                }else if(billMainCostum.getTaxInc() == 2){
                    balance = balance;
                    incTaxService = "<small>(include tax & service)</small>";
                }else{
                    balance = (balance)+amountService+amountTax;
                    incTaxService = "<small>(exclude tax & service)</small>";
                }
            }catch(Exception ex){
                billMainCostum = new BillMainCostum();
            }
            
            String readonlyPrice = "";
			    			    
            String oidSpesialRequestFood=PstSystemProperty.getValueByName("SPESIAL_REQUEST_FOOD");
            String oidSpesialRequestBeverage=PstSystemProperty.getValueByName("SPESIAL_REQUEST_BEVERAGE");
            String oidMaterialIdString = ""+billdetail.getMaterialId();

            if(oidMaterialIdString.equals(oidSpesialRequestBeverage) || oidMaterialIdString.equals(oidSpesialRequestFood)){
                readonlyPrice = "";
            }else{
                readonlyPrice = "readonly='true'";
            }
            
            String promotionType = PstSystemProperty.getValueByName("CASHIER_PROMOTION_GROUP_TYPE");
            DataCustom dataCustom = PstDataCustom.getDataCustom(billdetail.getOID(), "" + promotionType, "bill_detail_map_type");
            Vector listType = PstMasterType.list(0, 0,
                    PstMasterType.fieldNames[PstMasterType.FLD_TYPE_GROUP] + "=" + promotionType,
                    PstMasterType.fieldNames[PstMasterType.FLD_MASTER_NAME]);
            
            Vector vValue = new Vector(1,1);
            Vector vKey = new Vector(1,1);

            vValue.add("0");
            vKey.add("-");
            
            if (listType.size() > 0) {
                for (int x = 0; x < listType.size(); x++) {
                    MasterType masterType = (MasterType) listType.get(x);
                    vValue.add("" + masterType.getOID());
                    vKey.add(masterType.getMasterName());
                }
            }
            
            container = ""
            + "<form id='"+FrmBillDetail.FRM_NAME_BILL_DETAIL+"' name='"+FrmBillDetail.FRM_NAME_BILL_DETAIL+"'>"
                + "<input type='hidden' name='loadType' id='loadType' value='editItemProc'>"
                + "<input type='hidden' name='command' value='"+Command.SAVE+"'>"
                + "<input type='hidden' id='stock' value='"+qtyStock+"'>"
                + "<input type='hidden' id='stockCheck' value='"+cashMaster.getCashierStockMode()+"'>"
                + "<input type='hidden' id='billDetailId' name='billDetailId' value='"+billDetailOid+"'>"
                + "<input type='hidden' id='billMainId' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]+"' value='"+billMainOid+"'>"
                + "<input type='hidden' name='TAX_INC' id='taxInc' value='"+billMainCostum.getTaxInc()+"'>"
                + "<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-3'>Item Name</div>"
                        + "<div class='col-md-9'><input id='materialNames' type='text' name ='"+ FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME]+"' value='"+billdetail.getItemName()+"' class='form-control' "+readonlyPrice+"></div>"
                    + "</div>"
                + "</div>"
                + "<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-3'>Quantity</div>"
                        + "<div class='col-md-9'><input type='text' name='"+ FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]+"' id='FRM_FIELD_QTY2' value='"+billdetail.getQty()+"' class='form-control itemChange'></div>"
                    + "</div>"
                + "</div>"
                + "<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-3'>Item Price</div>"
                        + "<div class='col-md-9'>"
                            + incTaxService+"<br>"
                            + "<input type='text' name='"+ FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] +"' id='"+ FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] +"' value='"+billdetail.getItemPrice()+"' class='form-control itemChange' "+readonlyPrice+">"
                        + "</div>"
                    + "</div>"
                + "</div>"
                + "<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-3'></div>"
                        + "<div class='col-md-4'>Total</div>"
                        + "<div class='col-md-5' style='text-align:right;' id='totalPrice'>"
                                +Formater.formatNumber((billdetail.getItemPrice()*billdetail.getQty()),"#,###")
                        + "</div>"
                    + "</div>"
                + "</div>"
                + "<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-3'>Promotion</div>"
                        + "<div class='col-md-9'>"+ControlCombo.draw("PROMOTION_BILL_DETAIL","form-control", null, ""+dataCustom.getDataValue(), vValue, vKey, null)+"</div>"
                    + "</div>"
                + "</div>"
                + "<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-3'>Item Discount</div>"
                        + "<div class='col-md-3'>"
                            + "<div class='input-group'>"
                                + "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT]+"' value='"+billdetail.getDiscPct()+"' class='form-control itemChange'>"
                                + "<div class='input-group-addon'>%</div>"
                            + "</div>"
                        + "</div>"
                        + "<div class='col-md-3'>"
                            + "<div class='input-group'>"
                                + "<div class='input-group-addon'>Rp</div>"
                                + "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC]+"' value='"+billdetail.getDisc()+"' class='form-control itemChange '>"
                            + "</div>"
                        + "</div>"
                        + "<div class='col-md-3'>"
                            + "<div class='input-group'>"
                                + "<div class='input-group-addon'>~</div>"
                                + "<input type='text' name='' id='jumlahDiskon' value='"+billdetail.getDisc()*billdetail.getQty()+"' class='form-control itemChange '>"
                            + "</div>"
                        + "</div>"
                    + "</div>"
                + "</div>"
//                + "<div class='row'>"
//                    + "<div class='box-body'>"
//                        + "<div class='col-md-3'>Item Discount</div>"
//                        + "<div class='col-md-4'>"
//                            + "<div class='input-group'>"
//                                + "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT]+"' value='"+billdetail.getDiscPct()+"' class='form-control itemChange'>"
//                                + "<div class='input-group-addon'>%</div>"
//                            + "</div>"
//                        + "</div>"
//                        + "<div class='col-md-5'>"
//                            + "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC]+"' value='"+(billdetail.getDisc()*billdetail.getQty())+"' class='form-control itemChange '>"
//                        + "</div>"
//                    + "</div>"
//                + "</div>"
                + "<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-3'></div>"
                        + "<div class='col-md-4'>Total</div>"
                        + "<div class='col-md-5' style='text-align:right;' id='afterDiscount'>"
                                +Formater.formatNumber((billdetail.getItemPrice()*billdetail.getQty())-billdetail.getDisc(),"#,###")
                        + "</div>"
                    + "</div>"
                + "</div>"
                + "<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-3'>Item Service</div>"
                        + "<div class='col-md-4'>"
                            + "<div class='input-group'>"
                                + "<input type='text' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT]+"' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT]+"' value='"+billMainCostum.getServicePct()+"' class='form-control itemChange' "+readonly+">"
                                + "<div class='input-group-addon'>%</div>"
                            + "</div>"
                        + "</div>"
                        + "<div class='col-md-5'>"
                            + "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_SERVICE]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_SERVICE]+"' value='"+amountService+"' class='form-control' readonly='readonly'>"
                        + "</div>"
                    + "</div>"
                + "</div>"
                + "<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-3'></div>"
                        + "<div class='col-md-4'>Total</div>"
                        + "<div class='col-md-5' style='text-align:right;' id='afterService'>"
                                +Formater.formatNumber(afterService,"#,###")
                        + "</div>"
                    + "</div>"
                + "</div>"
                + "<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-3'>Item Tax</div>"
                        + "<div class='col-md-4'>"
                            + "<div class='input-group'>"
                                + "<input type='text' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT]+"' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT]+"' value='"+billMainCostum.getTaxPct()+"' class='form-control itemChange' "+readonly+">"
                                + "<div class='input-group-addon'>%</div>"
                            + "</div>"
                        + "</div>"
                        + "<div class='col-md-5'>"
                            + "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_TAX]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_TAX]+"' value='"+amountTax+"' class='form-control' readonly='readonly'>"
                        + "</div>"
                    + "</div>"
                + "</div>"
                +"<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-3'></div>"
                        + "<div class='col-md-4'>Total</div>"
                        + "<div class='col-md-5' style='text-align:right;' id='total'>"
                                +Formater.formatNumber(balance,"#,###")
                        + "</div>"
                    + "</div>"
                + "</div>"
                + "<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-3'>Note</div>"
                        + "<div class='col-md-9'>";
                        String note = "";
                        if (billdetail.getNote()!=null){
                            note = billdetail.getNote();
                        }
            container +=""
                            +"<textarea class='form-control' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_NOTE]+"'>"+note+"</textarea>"
                        + "</div>"
                    + "</div>"
                + "</div>"
                + "<div class='row'>"
                    + "<div class='box-body'>"
                        + "<div class='col-md-6'>"
                            + "<button type='button' class='btn btn-danger' data-dismiss='modal'><i class='fa fa-close'></i> Cancel</button>"
                        + "</div>"
                        + "<div class='col-md-6'>"
                            + "<button type='button' class='btn btn-primary pull-right' id='btnSaveBillDetail'><i class='fa fa-check'></i> Save</button>"
                        + "</div>"
                    + "</div>"
                + "</div>"
            + "</form>";           
        }catch(Exception ex){}
        
        return container;
    }
    
    public String loadDeleteBillDetail(HttpServletRequest request){
        String result ="";
        
        long billDetailOid = FRMQueryString.requestLong(request, "billDetailOid");
        Billdetail billdetail = new Billdetail();
        Material material = new Material();
        try {
           billdetail = PstBillDetail.fetchExc(billDetailOid);
        } catch (Exception e) {
        }
        
        try {
            material = PstMaterial.fetchExc(billdetail.getMaterialId());
        } catch(Exception ex){
        }
        
        result +=""
        + "<form id='"+FrmBillDetail.FRM_NAME_BILL_DETAIL+"' name='"+FrmBillDetail.FRM_NAME_BILL_DETAIL+"'>"
        + "<input type='hidden' name='loadType' value='delitemproccess'>"
        + "<input type='hidden' name='command' value='"+Command.SAVE+"'>"
        + "<input type='hidden' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID]+"' value='"+billDetailOid+"'>"
        + "<input type='hidden' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]+"' value='"+billdetail.getBillMainId()+"'>"
        +"<div class='row'>"
            +"<div class='box-body'>"
                + "<div class='col-md-3'>Item Name</div>"
                + "<div class='col-md-9'><b>"+billdetail.getItemName()+"</b></div>"
            +"</div>"
        +"</div>"
        +"<div class='row'>"
            +"<div class='box-body'>"
                + "<div class='col-md-3'>Note</div>"
                + "<div class='col-md-9'>"
                    + "<textarea id='noteDeleteItem' required='required' class='form-control' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_NOTE]+"'></textarea>"
                + "</div>"
            +"</div>"
        +"</div>"
        +"<div class='row'></div>"
        +"<div class='row'>"
            + "<div class='box-body'>"
                + "<div class='col-md-6'>"
                    + "<button type='button' class='btn btn-danger' data-dismiss='modal'>"
                        + "<i class='fa fa-close'></i> Cancel"
                    + "</button>"
                + "</div>"
                + "<div class='col-md-6'>"
                    + "<button type='button' class='btn btn-primary pull-right' id='btnDeleteItem'>"
                        + "<i class='fa fa-check'></i> Save"
                    + "</button>"
                + "</div>"
            + "</div>"
        + "</div>"
        + "</form>";
        
        return result;
    }
    
    public String drawListPaymentDynamic(Vector vResult,long billMainOid){
        String result ="";
        if(vResult!=null && vResult.size()>0){
            CurrencyType currencyType = PstCurrencyType.getDefaultCurrencyType();
            
            Vector vCurrType = PstCurrencyType.list(0,0,"","");
            Hashtable hashCurrType = new Hashtable();
            if(vCurrType!=null && vCurrType.size()>0){
                int iCurrTypeCount = vCurrType.size();
                for(int i=0; i<iCurrTypeCount; i++){
                    CurrencyType objCurrencyType = (CurrencyType) vCurrType.get(i);
                    hashCurrType.put(""+objCurrencyType.getOID(), objCurrencyType.getName()+"("+objCurrencyType.getCode()+")");
                }
            }
            
            String strContent = "";
            double dTotalPayment = 0;
            int iListPaymentCount = vResult.size();
            
            result +=""
            + "<table class='table table-bordered table-striped'>"
                + "<thead>"
                    + "<tr class='info'>"
                        + "<th style='width: 1%;'>No</th>"
                        + "<th>Currency</th>"
                        + "<th>Amount</th>"
                        + "<th>Rate</th>"
                        + "<th>Total ( "+currencyType.getCode()+" )</th>"
                        + "<th style='width: 1%;'>Action</th>"
                    + "</tr>"
                + "</thead>"
                + "<tbody>";
                
                for(int i=0; i<iListPaymentCount; i++){
                    Vector vObj = (Vector) vResult.get(i);
                    if(vObj!=null && vObj.size()>0){
                        double dPaymentPerType = 0;
                        String strPaymentTypeName = "";
                        for(int j=0; j<vObj.size(); j++){
                            CashPayments1 objCashPayment = (CashPayments1) vObj.get(j);
                            String strPaymentType = "";
                            if (objCashPayment.getPaymentType() != 0) {
                                strPaymentType = PstPaymentSystem.getTypePayment(objCashPayment.getPaymentType());
                            } else {
                                strPaymentType = "Change";
                            }
                            String strNumber = String.valueOf(j+1);
                            String strCurrency = String.valueOf(hashCurrType.get(""+objCashPayment.getCurrencyId()));
                            String strAmount = FRMHandler.userFormatStringDecimal( (objCashPayment.getAmount()));
                            String strRate = FRMHandler.userFormatStringDecimal(objCashPayment.getRate());
                            String strTotal = FRMHandler.userFormatStringDecimal(objCashPayment.getAmount() * objCashPayment.getRate());
                            dPaymentPerType = dPaymentPerType + (objCashPayment.getAmount() * objCashPayment.getRate());
			    
                            result += ""
                            + "<tr>"
                                + "<td colspan='5'><b>"+strPaymentType.toUpperCase()+"</b></td>"
                                + "<td>";
                                    if (objCashPayment.getOID()!=0){
                                        result += ""
                                        + "<button data-oid='"+objCashPayment.getOID()+"' data-billmainid='"+billMainOid+"' data-load-type='delitem' class='btn btn-danger dropdown-toggle deletepaymentitem' type='button' data-toggle='dropdown'>"
                                            + "<i class='fa fa-remove'></i> Delete"
                                        + "</button>";
                                    }
                            result +="</td>"
                            + "</tr>"
                            + "<tr>"
                                + "<td>"+strNumber+"</td>"
                                + "<td>"+strCurrency+"</td>"
                                + "<td style='text-align:right'>"+strAmount+"</td>"
                                + "<td style='text-align:right'>"+strRate+"</td>"
                                + "<td style='text-align:right'>"+strTotal+"</td>"
                                + "<td style='text-align:right'></td>"
                            + "</tr>";
                        }
                        
                        result += ""
                        + "<tr>"
                            + "<td colspan='4' class=''><b>TOTAL</b></td>"
                            + "<td style='text-align:right'>"+FRMHandler.userFormatStringDecimal(dPaymentPerType)+"</td>"
                            + "<td></td>"
                        + "</tr>"
                        + "<tr>"
                            + "<td colspan='5'>&nbsp;</td>"       
                        + "</tr>";
                        dTotalPayment = dTotalPayment + dPaymentPerType;
                    }
                    
                    
                }
                            
		String defaultCurrency = currencyType.getCode();
           
                result += ""
                        + "<tr>"
                            + "<td colspan='4'><b>TOTAL PAYMENT</b></td>"
                            + "<input type='hidden' id='totalsPayment' value='"+dTotalPayment+"'>"
                            + "<td style='text-align:right'>"+FRMHandler.userFormatStringDecimal(dTotalPayment)+"</td>"
                        + "<td></td>"
                        + "</tr>"
                    + "</tbody>"
                + "</table>";
        
        }
        
        return result ;
    }
    
    public String drawListPayment(Vector vResult){
        String result ="";
        
        if(vResult!=null && vResult.size()>0){
            Vector vCurrType = PstCurrencyType.list(0,0,"","");
            Hashtable hashCurrType = new Hashtable();
            if(vCurrType!=null && vCurrType.size()>0){	
                int iCurrTypeCount = vCurrType.size();
                for(int i=0; i<iCurrTypeCount; i++){
                    CurrencyType objCurrencyType = (CurrencyType) vCurrType.get(i);
                    hashCurrType.put(""+objCurrencyType.getOID(), objCurrencyType.getName()+"("+objCurrencyType.getCode()+")");
                }
            }
            double dTotalPayment = 0;
            int iListPaymentCount = vResult.size();
            
            CurrencyType defaultCurrency = PstCurrencyType.getDefaultCurrencyType();
            
            result +=""
            + "<table class='table table-bordered table-striped'>"
            + "<thead>"
                + "<tr class='info'>"
                    + "<th style='width: 1%;'>No</th>"
                    + "<th>Currency</th>"
                    + "<th>Amount</th>"
                    + "<th>Rate</th>"
                    + "<th>Total ( "+defaultCurrency.getCode()+" )</th>"
                + "</tr>"
            + "</thead>"
            + "<tbody>";

            for(int i=0; i<iListPaymentCount; i++){
                Vector vObj = (Vector) vResult.get(i);
                if(vObj!=null && vObj.size()>0){
                    double dPaymentPerType = 0;
                    String strPaymentTypeName = "";
                    for(int j=0; j<vObj.size(); j++){
                        CashPayments objCashPayment = (CashPayments) vObj.get(j);					
                        String strPaymentType = PstCashPayment.paymentType[objCashPayment.getPaymentType()];
                        String strNumber = String.valueOf(j+1);
                        String strCurrency = String.valueOf(hashCurrType.get(""+objCashPayment.getCurrencyId()));
                        String strAmount = FRMHandler.userFormatStringDecimal( (objCashPayment.getAmount()));
                        String strRate = FRMHandler.userFormatStringDecimal(objCashPayment.getRate());
                        String strTotal = FRMHandler.userFormatStringDecimal(objCashPayment.getAmount() * objCashPayment.getRate());					
                        dPaymentPerType = dPaymentPerType + (objCashPayment.getAmount() * objCashPayment.getRate());
					
                        if(j==0){
                            result +=  ""
                            + "<tr>"
                                + "<td colspan='5'>"+strPaymentType.toUpperCase()+"</td>"
                            + "</tr>";
                            strPaymentTypeName = strPaymentType.toUpperCase();
                        }										
                        result += ""
                        + "<tr>"
                            + "<td>"+strNumber+"</td>"
                            + "<td>"+strCurrency+"</td>"
                            + "<td style='text-align:right'>"+strAmount+"</td>"
                            + "<td style='text-align:right'>"+strRate+"</td>"
                            + "<td style='text-align:right'>"+strTotal+"</td>"
                        + "</tr>";
                    }
                    result += ""
                    + "<tr>"
                        + "<td colspan='4'><b>TOTAL "+strPaymentTypeName+"</b></td>"
                        + "<td style='text-align:right'>"+FRMHandler.userFormatStringDecimal(dPaymentPerType)+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td colspan='5'>&nbsp;</td>"       
                    + "</tr>";
                    dTotalPayment = dTotalPayment + dPaymentPerType;
                    
                }
            }
            result += ""
                    + "<tr>"
                        + "<td colspan='4'><b>TOTAL PAYMENT</b></td>"
                        + "<td style='text-align:right'>"+FRMHandler.userFormatStringDecimal(dTotalPayment)+"</td>"
                    + "</tr>"
                + "</tbody>"
            + "</table>";
        }
        
        return result ;
    }
    
    public String deleteProccess(HttpServletRequest request){
        String result ="";
        int fromEditable =0;
        try {
            fromEditable = FRMQueryString.requestInt(request, "fromEditable");
        }catch(Exception ex){
            fromEditable = 0;
        }
        
        HttpSession session = request.getSession();
        //SESSION
        boolean isLoggedIn = false;

        SessUserCashierSession userCashier = (SessUserCashierSession) session.getValue(SessUserCashierSession.HTTP_SESSION_CASHIER);
        String loginId = "";
        long userId = 0;
        try{
            if(userCashier==null){
                userCashier= new SessUserCashierSession();
            }else{
                if(userCashier.isLoggedIn()==true){
                    isLoggedIn  = true;
                    AppUser appUser = userCashier.getAppUser();
                    loginId = appUser.getLoginId();
                    userId = appUser.getOID();
                }
            }
        }catch (Exception exc){
            System.out.println(" >>> Exception during check login");
        }
        
        String spvName = loginId;
        long oidBillMain = FRMQueryString.requestLong(request, ""+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]+"");
        long oidBillDetail = FRMQueryString.requestLong(request, ""+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID]+"");
        String noteVoid = FRMQueryString.requestString(request,""+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_NOTE]+"");
        
        int command = Command.DELETE;
        noteVoid=" reason : "+noteVoid+" - authorize void :  "+spvName;

        Billdetail billDetails = new Billdetail();
        try {
            billDetails = PstBillDetail.fetchExc(oidBillDetail); 
        } catch (Exception e) {
        }
        
        int status=0;
        try {
            CtrlBillMain ctrlBillMain = new CtrlBillMain(request);          
            int iErrCode = ctrlBillMain.actionUpdateItem(command, oidBillDetail, status, noteVoid,0);
            String frmMsg = String.valueOf(billDetails.getBillMainId());
            result = String.valueOf(iErrCode);
            
            if (iErrCode==0 && fromEditable==1){
                //JIKA ASAL REQUEST DARI TRANSACTION MAINTENANCE, MAKA MASUK KE BAGIAN INI
                BillMain billMain = new BillMain();               
                try {
                    billMain = PstBillMain.fetchExc(oidBillMain);
                    double total = PstBillDetail.getTotalPrice2(oidBillMain);
                    billMain.setAmount(total);
                    CtrlBillMain ctrlBillMain2 = new CtrlBillMain(request);
                    iErrCode = ctrlBillMain2.actionObject(Command.SAVE, billMain, request);
                } catch (Exception e) {

                }
            }
            
            if (iErrCode==0){
                //MASUKKAN KE HISTORY
                insertHistory(userIds, loginIds, command, oidBillMain, "DELETE ITEM IN CASH BILL DETAIL");
            }
            

        } catch (Exception ex) {
            
        }
        
        return result;
    }
    
    public String deletePaymentProcess(HttpServletRequest request){
        String result ="";
        int iError = 0;
        int command = Command.DELETE;
        long oidTemp=0;
        PstCashCreditCard pstCashCreditCard = new PstCashCreditCard();
        PstCashReturn pstCashReturn = new PstCashReturn();
        CashReturn cashReturn = new CashReturn();
        CashPayments1 entCashPayments1 = new CashPayments1();
        
        BillMain billMain = new BillMain();
        Balance cashBalanceDest = new Balance();
        Balance cashBalanceDest2 = new Balance();
        
        long oidBillMain = FRMQueryString.requestLong(request, "billMainId");
        long paymentId = FRMQueryString.requestLong(request, "paymentOid");
        
        try {
            billMain = PstBillMain.fetchExc(oidBillMain);
        } catch (Exception e) {
        }
        
        try {
            entCashPayments1 = PstCashPayment1.fetchExc(paymentId);
        } catch (Exception e) {
        }
        
        //jumlah payment untuk bill tersebut
        String whereCashPayment=""
        + ""+PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID]+"="+oidBillMain+"";
        Vector listPayment = PstCashPayment.list(0, 0, whereCashPayment, "");
        int jmlPayment = listPayment.size();
        //jika jmlPayment cuma satu, maka hapus juga cash return payment
        
        //cek juga apkah payment dengan id tersebut ada di cash credit card
        //jika ada maka hapus juga ke sana
        CashCreditCard cashCreditCard = new CashCreditCard();
        try {
            cashCreditCard = PstCashCreditCard.fetchExc(paymentId);
        } catch (Exception e) {
        }
        
        CtrlCashPayment ctrlCashPayment = new CtrlCashPayment(request);
        
        iError = ctrlCashPayment.action(command, paymentId, oidBillMain, 0);
        
        //HISTORY
        insertHistory(userIds, loginIds, command, oidBillMain, "DELETE PAYMENT FROM CASH BILL MAIN");
        
        //Jika payment terdapat pada cash credit 
        if (cashCreditCard.getOID()!=0 && iError == 0){
            try {
                oidTemp = pstCashCreditCard.deleteExc(cashCreditCard);
            } catch (Exception e) {
                
            }
            
        }
        
        //jika jumlah payment cuma satu, maka langsung dihapus ke cash return payment 
        if (jmlPayment==1 && iError==0){
            String whereCashReturn = ""
                + ""+PstCashReturn.fieldNames[PstCashReturn.FLD_BILLMAIN_ID]+"="+oidBillMain+"";
            Vector listCashReturn = PstCashReturn.list(0, 0, whereCashReturn, "");
            cashReturn = (CashReturn) listCashReturn.get(0);
            try {
                oidTemp = pstCashReturn.deleteExc(cashReturn);
            } catch (Exception e) {
            }
            
        }
        
        //sesuaikan cash balance 
        String whereCashDest = ""
            + " "+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"="+billMain.getCashCashierId()+""
            + " AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"=1 "
            + " AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"="+billMain.getCurrencyId()+"";
        
        String whereCashDest2 = ""
            + " "+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"="+billMain.getCashCashierId()+""
            + " AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"=0 "
            + " AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"="+billMain.getCurrencyId()+"";
        
        Vector listCashDest = PstBalance.list(0, 1, whereCashDest, "");
        Vector listCashDest2 = PstBalance.list(0, 1, whereCashDest2, "");
        
        try {
           cashBalanceDest = (Balance) listCashDest.get(0); 
           cashBalanceDest2 = (Balance) listCashDest2.get(0); 
        } catch (Exception e) {
        }
        
        if (listCashDest.size()>0 && iError==0){
            CtrlCloseCashCashier ctrlCloseCashCashier = new CtrlCloseCashCashier(request);
            iError = ctrlCloseCashCashier.actionObject2(Command.SAVE, cashBalanceDest,cashBalanceDest2, request);
        }
        
        //menghapus payment rec jika integrasi dengan hanoman
        int design = 0;
        try {
            design = Integer.parseInt(PstSystemProperty.getValueByName("DESIGN_MATERIAL_FOR"));
        } catch (Exception e) {
            design = 0;
        }
        
        if (design==1){
            String wherePaymentRec = ""
            + " "+PstPaymentRec.fieldNames[PstPaymentRec.FLD_PAYMENT_SYSTEM_ID]+"='"+entCashPayments1.getPaymentType()+"'"
            + " AND "+PstPaymentRec.fieldNames[PstPaymentRec.FLD_COVER_BILLING_ID]+"='"+oidBillMain+"'";
            
            Vector listPaymentRec = PstPaymentRec.list(0, 1, wherePaymentRec, "");
            if (listPaymentRec.size()>0){               
                PaymentRec entPaymentRec = new PaymentRec();
                entPaymentRec = (PaymentRec) listPaymentRec.get(0);
                long oidPayRec = 0;
                try {
                    oidPayRec = PstPaymentRec.deleteExc(entPaymentRec.getOID());
                } catch (Exception e) {
                    oidPayRec=0;
                }
                
            }
                
            
        }
        
        
        
        
        
        return result;
    }
    
    public String savePayment(HttpServletRequest request){
        String result ="";
        long oidTemp =0;
        double remains=FRMQueryString.requestDouble(request, "remains");
        long multiPayCashBillMainId = FRMQueryString.requestLong(request, "multiPayCashBillMainId");
        long multiTransactionType = FRMQueryString.requestLong(request, "multiTransactionType");
        long masterPayType = FRMQueryString.requestLong(request, "MASTER_FRM_FIELD_PAY_TYPE_MULTI");
        long payType =FRMQueryString.requestLong(request, "FRM_FIELD_PAY_TYPE_MULTI");
        long curencyId = FRMQueryString.requestLong(request, "FRM_FIELD_CURR_ID_MULTI");
        double payAmount = FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI");
        String ccNameMulti = "";
        String ccNumberMulti ="";
        String debitBankNameMulti ="";
        String expiredDateMulti="";
        double payAmountMulti = 0;
        PaymentSystem paymentSystemMulti = new PaymentSystem();
        
        BillMain billMain = new BillMain();
        Balance cashBalanceDest = new Balance();
        Balance cashBalanceDest2 = new Balance();
        
        try {
            billMain = PstBillMain.fetchExc(multiPayCashBillMainId);
        } catch (Exception e) {
        }
        
        try {
            ccNameMulti =  FRMQueryString.requestString(request, "FRM_FIELD_CC_NAME_MULTI");
        } catch (Exception e) {
        }
        try {
            ccNumberMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NUMBER_MULTI");  
        } catch (Exception e) {
        }
        try {
            debitBankNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_DEBIT_BANK_NAME_MULTI");  
        } catch (Exception e) {
        }
        try {
            expiredDateMulti = FRMQueryString.requestString(request, "FRM_FIELD_EXPIRED_DATE_MULTI");  
        } catch (Exception e) {
        }
        try {
            payAmountMulti =  FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI");
        } catch (Exception e) {
        }
        try {
            paymentSystemMulti = PstPaymentSystem.fetchExc(payType);
        } catch (Exception e) {
        }
        
        
        double totalRemainPayment =  payAmount-remains;
        
        CashPayments1 cashPayments = new CashPayments1();
        CashPayments1 entCashPayments = new CashPayments1();
        CashReturn cashReturn = new CashReturn();
        CashCreditCard cashCreditCard = new CashCreditCard();
        
        cashPayments.setPaymentStatus(0);
        cashPayments.setCurrencyId(curencyId);
        cashPayments.setBillMainId(multiPayCashBillMainId);
        cashPayments.setPaymentType(payType);
        cashPayments.setRate(billMain.getRate());
        cashPayments.setPayDateTime(new Date());
        cashPayments.setAmount(payAmount);
        
        CtrlCashPayment ctrlCashPayment = new CtrlCashPayment(request);
        int iError = ctrlCashPayment.actionObject(Command.SAVE, cashPayments);
        long oidNewCash = ctrlCashPayment.getOid();
        try {
            entCashPayments = PstCashPayment1.fetchExc(oidNewCash);
        } catch (Exception e) {
        }
        
        long oidCashPayment = ctrlCashPayment.getOid();
        
        String whereCashReturn = ""
            + ""+PstCashReturn.fieldNames[PstCashReturn.FLD_BILLMAIN_ID]+"="+multiPayCashBillMainId+"";
        
        Vector listCashReturn = PstCashReturn.list(0,0,whereCashReturn,"");
        if (listCashReturn.size()>0){
            cashReturn = (CashReturn) listCashReturn.get(0);
            //apakah cashReturn yang sebelumnya amountnya 0 atau bukan
            if (cashReturn.getAmount()==0){
                try {
                    oidTemp = PstCashReturn.deleteExc(cashReturn.getOID());
                } catch (Exception e) {
                }
                if (oidTemp!=0){
                    CashReturn cashReturn1 = new CashReturn();
                    cashReturn1.setCurrencyId(curencyId);
                    cashReturn1.setBillMainId(multiPayCashBillMainId);
                    cashReturn1.setRate(billMain.getRate());
                    cashReturn1.setPaymentStatus(0);
                    if (totalRemainPayment<0){
                        cashReturn1.setAmount(0);
                    }else{
                        cashReturn1.setAmount(totalRemainPayment);
                    }
                    
                    try {
                        oidTemp = PstCashReturn.insertExc(cashReturn1);
                    } catch (Exception e) {
                    }
                }
                
            }
        }else{
            //simpan ke cash return
            if (iError==0){
                cashReturn.setCurrencyId(curencyId);
                cashReturn.setBillMainId(multiPayCashBillMainId);
                cashReturn.setRate(billMain.getRate());
                cashReturn.setPaymentStatus(0);
                if (totalRemainPayment<0){
                    cashReturn.setAmount(0);
                }else{
                    cashReturn.setAmount(totalRemainPayment);
                }

                try {
                    oidTemp = PstCashReturn.insertExc(cashReturn);
                } catch (Exception e) {
                }
            }
            
        }
        
        //jika billMain masih berstatus open bill maka update billMain
        if (billMain.getDocType()==0 && billMain.getTransType()==0 && billMain.getTransactionStatus()==1){
            CashMaster cashMaster = new CashMaster();
            CashCashier cashCashier = new CashCashier();
            
            try {
                cashCashier = PstCashCashier.fetchExc(billMain.getCashCashierId());
            } catch (Exception e) {
            }
            
            try {                
                cashMaster = PstCashMaster.fetchExc(cashCashier.getCashMasterId());
            }catch(Exception e){
            }
            
            Vector listDailyRate = PstDailyRate.getCurrentDailyRate();
            DailyRate dailyRate = new DailyRate();
            try{
                if(listDailyRate.size() != 0){
                    for(int i = 0; i<listDailyRate.size();i++){
                        DailyRate dailyRateData = (DailyRate) listDailyRate.get(i);
                        if(cashPayments.getCurrencyId() != 0){
                            if(cashPayments.getCurrencyId() == dailyRateData.getCurrencyTypeId()){
                                dailyRate = dailyRateData;
                            }
                        }else if(billMain.getCurrencyId() == dailyRateData.getCurrencyTypeId()){
                            dailyRate = dailyRateData;
                        }
                    }
                }

            }catch(Exception ex){
                dailyRate = new DailyRate();
            }
            
            try {
               long oidTemps = CtrlCustomPayment.updatebillMain2(cashPayments, billMain, cashPayments.getOID(),dailyRate,cashMaster.getCashierNumber(),cashCashier.getOID(),0,0); 
            } catch (Exception e) {
            }
            
            result += "UPDATE";
            
        }
        
        
        //untuk tipe card
        CashPayments1 cashPayments1 = new CashPayments1();
        CtrlCustomPayment ctrlCustomPayment = new CtrlCustomPayment(request);
        
        cashPayments1.setPaymentStatus(0);
        cashPayments1.setCurrencyId(curencyId);
        cashPayments1.setBillMainId(multiPayCashBillMainId);
        cashPayments1.setPaymentType(payType);
        cashPayments1.setRate(billMain.getRate());
        cashPayments1.setPayDateTime(new Date());
        
        long oidTemps = 0;
         
        try {
            oidTemps = ctrlCustomPayment.insertCreditCard2(cashPayments1, billMain, oidCashPayment, request,0);
        } catch (Exception e) {
        }
        
        //PROSES PENYESUAIAN CASH BALANCE
        String whereCashDest = ""
            + " "+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"="+billMain.getCashCashierId()+""
            + " AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"=1 "
            + " AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"="+billMain.getCurrencyId()+"";
        
        String whereCashDest2 = ""
            + " "+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"="+billMain.getCashCashierId()+""
            + " AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"=0 "
            + " AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"="+billMain.getCurrencyId()+"";
        
        Vector listCashDest = PstBalance.list(0, 1, whereCashDest, "");
        Vector listCashDest2 = PstBalance.list(0, 1, whereCashDest2, "");
        
        try {
           cashBalanceDest = (Balance) listCashDest.get(0); 
           cashBalanceDest2 = (Balance) listCashDest2.get(0); 
        } catch (Exception e) {
        }
        
        if (listCashDest.size()>0){
            CtrlCloseCashCashier ctrlCloseCashCashier = new CtrlCloseCashCashier(request);
            iError = ctrlCloseCashCashier.actionObject2(Command.SAVE, cashBalanceDest,cashBalanceDest2, request);
        }

        int command = FRMQueryString.requestInt(request, "command");
        //HISTORY
        insertHistory(userIds, loginIds, command, multiPayCashBillMainId, "ADD NEW PAYMENT");
        
        //INSERT KE PAYMENT REC APABILA TERINTEGRASI DENGAN HANOMAN
        int design = 0;
        try {
            design = Integer.parseInt(PstSystemProperty.getValueByName("DESIGN_MATERIAL_FOR"));
        } catch (Exception e) {
            design = 0;
        }
        if (design==1){
            Location location;
            AppUser appUser;
            try{
                location = PstLocation.fetchExc(billMain.getLocationId());
                appUser = PstAppUser.fetch(userIds);
            }catch(Exception ex){
                location = new Location();
                appUser = new AppUser();
            }
            
            PaymentRec paymentRec = new PaymentRec();
            
            Date dateNow = Formater.reFormatDate(new Date(), "yyyy-MM-dd");
            String dateNumber = Formater.formatDate(new Date(),"yyyyMMdd");
            
            Vector listDataReservation = PstCustomBillMain.listInHouseGuestPayment(0, 0, 
                "(m."+ PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]+"='"+billMain.getCustomerId()+"' "
                + "OR n."+PstContact.fieldNames[PstContact.FLD_CONTACT_ID]+"='"+billMain.getCustomerId()+"')", "");
            
            ContactList contactList;
            Reservation reservation;
            if(listDataReservation.size() != 0){
                Vector listReservation = (Vector) listDataReservation.get(0);
                if(listReservation.size() != 0){
                     contactList = (ContactList) listReservation.get(0);
                     reservation = (Reservation) listReservation.get(1);
                }else{
                    contactList = new ContactList();
                    reservation = new Reservation();
                }
            }else{
                contactList = new ContactList();
                reservation = new Reservation();
            }

            int counter = PstPaymentRec.getCount("");
            String displayCounter="";
            String zeroLoop="";
            for(int i=1;i<=4-String.valueOf(counter).length();i++){
                zeroLoop +="0";
            }

            String recNumber = "CASH-"+location.getCode()+"-"+dateNumber+"-"+zeroLoop+""+counter;
           
            paymentRec.setReservationId(reservation.getOID());
            double amountRp = entCashPayments.getAmount() - entCashPayments.getAmountReturn();
            double amountDollar = 0;
            String currencyId = String.valueOf(entCashPayments.getCurrencyId());

            //PstStandartRate.getStandardRate();
            double rate =PstDailyRate.getCurrentDailyRateSales(2);
            if(amountRp>0){
                amountDollar=amountRp/rate;
            }else{
                amountDollar=0;
            }   
            
            Vector listDailyRateConvert = PstDailyRate.list(0, 0, 
                ""+PstDailyRate.fieldNames[PstDailyRate.FLD_CURRENCY_TYPE_ID]+"='2' "
                + "AND "+PstDailyRate.fieldNames[PstDailyRate.FLD_ROSTER_DATE]+"<=(NOW())"
                + "", "");
            DailyRate dailyRate1;
            if(listDailyRateConvert.size() != 0){
                dailyRate1 = (DailyRate) listDailyRateConvert.get(0);
            }else{
                dailyRate1 = new DailyRate();
            }
            
            paymentRec.setPaymentDate(dateNow);
            paymentRec.setAmountRp(amountRp);
            paymentRec.setAmount$(amountDollar);
            paymentRec.setReceivedBy(appUser.getLoginId());
            paymentRec.setExchangeRate(dailyRate1.getSellingRate());
            paymentRec.setRecNumber(recNumber);
            paymentRec.setPaymentPurpose(0);
            paymentRec.setCcIssuingBank(debitBankNameMulti);
            paymentRec.setCcNumber(ccNumberMulti);
            paymentRec.setCcExpiredDate(Formater.formatDate(expiredDateMulti, "yyyy-MM-dd"));
            paymentRec.setCcHolderName(ccNameMulti);
            if(cashPayments1.getCurrencyId() != 0){
                paymentRec.setCurrencyUsed(Integer.parseInt(currencyId));
            }else{
                paymentRec.setCurrencyUsed(Integer.parseInt(currencyId));
            }

            paymentRec.setReceivedById(appUser.getOID());
            paymentRec.setBillMainId(billMain.getOID());
            paymentRec.setCoverBillingId(billMain.getOID());
            paymentRec.setCustomerId(billMain.getCustomerId());
            paymentRec.setCashierId(billMain.getCashCashierId());
            paymentRec.setNumber(billMain.getInvoiceNumber());
            paymentRec.setCounter(counter);
            paymentRec.setStatus(0);
            paymentRec.setPaymentSystemId(cashPayments1.getPaymentType());
            paymentRec.setDueDate(dateNow);
            paymentRec.setDate(dateNow);
            
            try {
                long oid = PstPaymentRec.insertExc(paymentRec);
            } catch (Exception e) {
            }
            
        }

        return result;
    }
    
    public String loadEditPayment(HttpServletRequest request){
        String result ="";
        
        long oidPayment = FRMQueryString.requestLong(request, "oidPayment");
        long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
        
        CashPayments1 cashPayments1 = new CashPayments1();
        PaymentSystem paymentSystem = new PaymentSystem();
        CashCreditCard cashCreditCard = new CashCreditCard();
        
        
        
        try {
            cashPayments1 = PstCashPayment1.fetchExc(oidPayment);
        } catch (Exception e) {
        }
        try {
            paymentSystem = PstPaymentSystem.fetchExc(cashPayments1.getPaymentType());
        } catch (Exception e) {
        }
        
        String whereCashCreditCard = " "+PstCashCreditCard.fieldNames[PstCashCreditCard.FLD_PAYMENT_ID]+"="+oidPayment+"";
        Vector listCashCreditCard = PstCashCreditCard.list(0, 0, whereCashCreditCard, "");
        if (listCashCreditCard.size()>0){
            cashCreditCard = (CashCreditCard) listCashCreditCard.get(0);
        }
        
        result += ""
        + "<input id='editPayBillmainId' value='"+cashPayments1.getBillMainId()+"' name='editPayBillmainId' type='hidden'>"
        + "<input id='editPayCashPaymentId' name ='editPayCashPaymentId' value='"+cashPayments1.getOID()+"'>"
        + "<div class='row'>"
            + "<div class='col-md-3'>Transaction Type</div>"
            + "<input name='multiTransactionType' id='multiTransactionType' value='0' type='hidden'>"
            + "<div class='col-md-9'>"
                + "<input class='form-control' readonly='' value='CASH' type='text'>"
            + "</div>"
        + "</div>";
        int masterPaymentValue=0;
        String masterPaymentText ="";
        String desMasterPayment="";
        
        if(paymentSystem.isBankInfoOut()==false && paymentSystem.isCardInfo()==false && paymentSystem.isCheckBGInfo()==false && paymentSystem.getPaymentType()==0){
            masterPaymentValue = PstCustomBillMain.PAYMENT_TYPE_CASH;
            masterPaymentText = PstCustomBillMain.fieldNames[PstCustomBillMain.PAYMENT_TYPE_CASH];
            desMasterPayment = "Payment Detail";
        }else if (paymentSystem.isBankInfoOut()==false && paymentSystem.isCardInfo()==true && paymentSystem.isCheckBGInfo()==false && paymentSystem.getPaymentType()==0){
            masterPaymentValue = PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD;
            masterPaymentText = PstCustomBillMain.fieldNames[PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD];
            desMasterPayment = "Type Card";
        }else if (paymentSystem.isBankInfoOut()==true && paymentSystem.isCardInfo()==false && paymentSystem.isCheckBGInfo()==false && paymentSystem.getPaymentType()==2){
            masterPaymentValue = PstCustomBillMain.PAYMENT_TYPE_BG;
            masterPaymentText = PstCustomBillMain.fieldNames[PstCustomBillMain.PAYMENT_TYPE_BG];
            desMasterPayment = "Payment Detail";
        }else if (paymentSystem.isBankInfoOut()==false && paymentSystem.isCardInfo()==false && paymentSystem.isCheckBGInfo()==true && paymentSystem.getPaymentType()==0){
            masterPaymentValue = PstCustomBillMain.PAYMENT_TYPE_CHECK;
            masterPaymentText = PstCustomBillMain.fieldNames[PstCustomBillMain.PAYMENT_TYPE_CHECK];
            desMasterPayment = "Payment Detail";
        }else if (paymentSystem.isBankInfoOut()==false && paymentSystem.isCardInfo()==false && paymentSystem.isCheckBGInfo()==false && paymentSystem.getPaymentType()==2){
            masterPaymentValue = PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD;
            masterPaymentText = PstCustomBillMain.fieldNames[PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD];
            desMasterPayment = "Bank Name";
        }else if (paymentSystem.isBankInfoOut()==false && paymentSystem.isCardInfo()==false && paymentSystem.isCheckBGInfo()==false && paymentSystem.getPaymentType()==4){
            masterPaymentValue = PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR;
            masterPaymentText = PstCustomBillMain.fieldNames[PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR];
            desMasterPayment = "Payment Detail";
        }else if (paymentSystem.isBankInfoOut()==false && paymentSystem.isCardInfo()==false && paymentSystem.isCheckBGInfo()==false && paymentSystem.getPaymentType()==5){
            masterPaymentValue = PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT;
            masterPaymentText = PstCustomBillMain.fieldNames[PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT];
            desMasterPayment = "Payment Detail";
        }else if (paymentSystem.isBankInfoOut()==false && paymentSystem.isCardInfo()==false && paymentSystem.isCheckBGInfo()==false && paymentSystem.getPaymentType()==6){
            masterPaymentValue = PstCustomBillMain.PAYMENT_TYPE_FOC;    
            masterPaymentText = PstCustomBillMain.fieldNames[PstCustomBillMain.PAYMENT_TYPE_FOC];
            desMasterPayment = "Payment Detail";
        }
        
            
        result+=""
        + "<div class='row'>"
            + "<div class='col-md-3'>Payment Type</div>"
            + "<input name='MASTER_FRM_FIELD_PAY_TYPE_MULTI' id='MASTER_FRM_FIELD_PAY_TYPE_MULTI' value='"+masterPaymentValue+"' type='hidden'>"
            + "<div class='col-md-9'>"
                + "<input class='form-control' readonly='' value='"+masterPaymentText+"' type='text'>"
            + "</div>"
        + "</div>"
        + "<div class='row'>"
            + "<div class='col-md-3'>"+desMasterPayment+"</div>"
            + "<input name='FRM_FIELD_PAY_TYPE_MULTI' id='FRM_FIELD_PAY_TYPE_MULTI' value='"+paymentSystem.getOID()+"' type='hidden'>"
            + "<div class='col-md-9'>"
                + "<input class='form-control' readonly='' value='"+paymentSystem.getPaymentSystem()+"' type='text'>"
            + "</div>"
        + "</div>";
        
        if (masterPaymentValue == PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD){
            Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
            Vector currency_key = new Vector(1,1);
            Vector currency_value = new Vector(1,1);

            if(listCurrency.size() != 0){
                for(int i = 0;i<listCurrency.size(); i++){
                    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                    currency_key.add(""+currencyType.getOID());
                    currency_value.add(""+currencyType.getCode());
                }
            }
            result += ""
            + "<div class='row'>"
                + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Card Name</div>"
                        + "<div class='col-md-6'>"
                            + "<div class='input-group'>"
                                + "<input value='"+cashCreditCard.getCcName()+"' type='text' class='form-control getaccount' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]+"' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME].trim()+"'  required>"
                                + "<div class='btn btn-primary input-group-addon' id='ccReScan''>"
                                    + "<i class='fa fa-refresh'></i>"
                                + "</div>"
                            + "</div>"
                        +"</div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                 + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Card No</div>"
                        + "<div class='col-md-6'><input value='"+cashCreditCard.getCcNumber()+"' type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"'  required></div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                 + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Bank</div>"
                        + "<div class='col-md-6'><input type='text' value='"+cashCreditCard.getDebitBankName()+"'  class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' value='"+paymentSystem.getBankName()+"' required></div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                 + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Validity</div>"
                        + "<div class='col-md-6'>"
                            + "<div class='input-group'>" +
                                "<div class='input-group-addon'>" +
                                    "<i class='fa fa-calendar'></i>" +
                                "</div>"
                                + "<input type='text' value='"+Formater.formatDate(cashCreditCard.getExpiredDate(), "MM/dd/yyyy")+"' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"' readonly='readonly' required>"
                            + "</div>"
                        + "</div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='box-body'>"
                   + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>"

                        + "</div>"
                        + "<div class='col-md-6'>"
                            //+ "<b>"+Formater.formatNumber(costumBalance,"#,###.##")+"</b>"
                        + "</div>"
                   + "</div>"
               + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='box-body'>"
                   + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>"
                            + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control")
                        + "</div>"
                        + "<div class='col-md-6'>"
                            //+"<input type='text' id='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' class='form-control money calc' value='"+Formater.formatNumber(paid,"#,###.##")+"'>"
                        + "</div>"
                   + "</div>"
               + "</div>"
           + "</div>";
        }else if (masterPaymentValue == PstCustomBillMain.PAYMENT_TYPE_CASH){
            Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
            Vector currency_key = new Vector(1,1);
            Vector currency_value = new Vector(1,1);

            if(listCurrency.size() != 0){
                for(int i = 0;i<listCurrency.size(); i++){
                    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                    currency_key.add(""+currencyType.getOID());
                    currency_value.add(""+currencyType.getCode());
                }
            }
            result+=""
            + "<div class='row'>"
                + "<div class='box-body'>"
                   + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>"

                        + "</div>"
                        + "<div class='col-md-6'>"
                            //+ "<b>"+Formater.formatNumber(costumBalance,"#,###.##")+"</b>"
                        + "</div>"
                   + "</div>"
               + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='box-body'>"
                   + "<div class='col-md-12'>"
                        + "<div class='col-md-2'>"
                            +paymentSystem.getPaymentSystem()
                        + "</div>"
                        + "<div class='col-md-3'>"
                            + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "required='true'", "form-control")
                        + "</div>"
                        + "<div class='col-md-7'>"
                            +"<input type='text' id='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' class='form-control calc money'>"
                        + "</div>"
                   + "</div>"
               + "</div>"
           + "</div>"; 
        }else if (masterPaymentValue == PstCustomBillMain.PAYMENT_TYPE_BG){
            Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
            Vector currency_key = new Vector(1,1);
            Vector currency_value = new Vector(1,1);

            if(listCurrency.size() != 0){
                for(int i = 0;i<listCurrency.size(); i++){
                    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                    currency_key.add(""+currencyType.getOID());
                    currency_value.add(""+currencyType.getCode());
                }
            }
            
            result = ""
            + "<div class='row'>"
                + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Bank Name</div>"
                        + "<div class='col-md-6'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' value='"+paymentSystem.getBankName()+"' required></div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                 + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Bank Account Number</div>"
                        + "<div class='col-md-6'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]+"' required></div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                 + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>BG Number</div>"
                        + "<div class='col-md-6'><input type='text'  class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] +"' required></div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                 + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Expired Date</div>"
                        + "<div class='col-md-6'>"
                            + "<div class='input-group'>" +
                                "<div class='input-group-addon'>" +
                                    "<i class='fa fa-calendar'></i>" +
                                "</div>"
                                + "<input type='text' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"' required>"
                            + "</div>"
                        + "</div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='box-body'>"
                   + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>"

                        + "</div>"
                        + "<div class='col-md-6'>"
                            //+ "<b>"+Formater.formatNumber(costumBalance,"#,###.##")+"</b>"
                        + "</div>"
                   + "</div>"
               + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='box-body'>"
                   + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>"
                            + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control")
                        + "</div>"
                        + "<div class='col-md-6'>"
                            //+"<input type='text' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' class='form-control money calc' value='"+Formater.formatNumber(paid,"#,###.##")+"'>"
                        + "</div>"
                   + "</div>"
               + "</div>"
           + "</div>";
        }else if (masterPaymentValue == PstCustomBillMain.PAYMENT_TYPE_CHECK){
            Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
            Vector currency_key = new Vector(1,1);
            Vector currency_value = new Vector(1,1);

            if(listCurrency.size() != 0){
                for(int i = 0;i<listCurrency.size(); i++){
                    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                    currency_key.add(""+currencyType.getOID());
                    currency_value.add(""+currencyType.getCode());
                }
            }

            result = ""
            + "<div class='row'>"
                + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Bank Name</div>"
                        + "<div class='col-md-6'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' value='"+paymentSystem.getBankName()+"' required></div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                 + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Bank Account Number</div>"
                        + "<div class='col-md-6'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]+"' required></div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                 + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Cheque Number</div>"
                        + "<div class='col-md-6'><input type='text'  class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"' required></div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                 + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Expired Date</div>"
                        + "<div class='col-md-6'>"
                            + "<div class='input-group'>" +
                                "<div class='input-group-addon'>" +
                                    "<i class='fa fa-calendar'></i>" +
                                "</div>"
                                + "<input type='text' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"' required>"
                            + "</div>"
                        + "</div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='box-body'>"
                   + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>"

                        + "</div>"
                        + "<div class='col-md-6'>"
                           // + "<b>"+Formater.formatNumber(costumBalance,"#,###.##")+"</b>"
                        + "</div>"
                   + "</div>"
               + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='box-body'>"
                   + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>"
                            + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control")
                        + "</div>"
                        + "<div class='col-md-6'>"
                            //+"<input type='text' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' class='form-control money calc' value='"+Formater.formatNumber(paid,"#,###.##")+"'>"
                        + "</div>"
                   + "</div>"
               + "</div>"
           + "</div>";
        }else if (masterPaymentValue == PstCustomBillMain.PAYMENT_TYPE_FOC){
            result = "" 
            + "<div class='row'>"
                + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-12'>Remark</div>"
                        + "<div class='col-md-12'><textarea name='remarkFoc' class='form-control' style='width:100%'></textarea></div>"
                    + "</div>"
                + "</div>"
            + "</div>";
        }else if (masterPaymentValue == PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD){
            Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
            Vector currency_key = new Vector(1,1);
            Vector currency_value = new Vector(1,1);

            if(listCurrency.size() != 0){
                for(int i = 0;i<listCurrency.size(); i++){
                    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                    currency_key.add(""+currencyType.getOID());
                    currency_value.add(""+currencyType.getCode());
                }
            }

            result = ""
            + "<div class='row'>"
                + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Bank Name</div>"
                        + "<div class='col-md-6'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' value='"+paymentSystem.getBankName()+"' required></div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                 + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Card Number</div>"
                        + "<div class='col-md-6'>"
                            + "<div class='input-group'>"
                                + "<input type='text' class='form-control getaccountDebit' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"' id='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"' required>"
                                + "<div class='btn btn-primary input-group-addon' id='debitReScan'>"
                                     + "<i class='fa fa-refresh'></i>"
                                + "</div>"
                        + "</div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                 + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Expired Date</div>"
                        + "<div class='col-md-6'>"
                            + "<div class='input-group'>" +
                                "<div class='input-group-addon'>" +
                                    "<i class='fa fa-calendar'></i>" +
                                "</div>"
                                + "<input type='text' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"' readonly='readonly'  required>"
                            + "</div>"
                        + "</div>"
                    + "</div>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='box-body'>"
                   + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>"

                        + "</div>"
                        + "<div class='col-md-6'>"
                            //+ "<b>"+Formater.formatNumber(costumBalance,"#,###.##")+"</b>"
                        + "</div>"
                   + "</div>"
               + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='box-body'>"
                   + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>"
                            + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control")
                        + "</div>"
                        + "<div class='col-md-6'>"
                            //+"<input type='text' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' class='form-control money calc' value='"+Formater.formatNumber(paid,"#,###.##")+"'>"
                        + "</div>"
                   + "</div>"
               + "</div>"
           + "</div>";
        
        }
        
                    
                    
                    
                
        
        
        return result;
    }
    
    public String loadListCashCashier(HttpServletRequest request){
        String container ="";
        
        int showPage = 1;
        int jmlShowData = 10;
        String research = "";
        int c = 0;
        
        try {
            showPage = FRMQueryString.requestInt(request, "showPage");
        } catch (Exception e) {
            showPage = 1;
        }
                    
        try {
            research = FRMQueryString.requestString(request, "search");
        } catch (Exception e) {
            research = "";
        }
                                                          
        if (showPage==0){
            showPage = 1;
        }
                    
        int start = (showPage-1) * jmlShowData;
        
        String cashCashierDate1 = FRMQueryString.requestString(request, "cashCashierDate1");
        String cashCashierDate2 = FRMQueryString.requestString(request, "cashCashierDate2");
        long locationId = FRMQueryString.requestLong(request, "locationId");
             
        //DATE
        if (cashCashierDate1!="" && cashCashierDate2==""){
            cashCashierDate2 = cashCashierDate1;
        }else if (cashCashierDate1=="" && cashCashierDate2!=""){
            cashCashierDate1 = cashCashierDate2;
        }
        
        String whereCashCashier = ""
            + " LOC."+PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]+"="+locationId+" ";
        
        if (cashCashierDate1!=""){
            if (cashCashierDate1==cashCashierDate2){
                whereCashCashier += ""
                + " AND DATE(CC."+PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE]+")='"+cashCashierDate1+"'"
                + "";
            }else{
                whereCashCashier += ""
                + " AND (DATE(CC."+PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE]+")>='"+cashCashierDate1+"'"
                + " AND DATE(CC."+PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE]+" )<= '"+cashCashierDate2+"')";
            }
            
        }
        
        String order = " CC."+PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE]+" DESC";
        
        Vector listCashCashier = PstCashCashier.listDataClosingCashier(start, jmlShowData, whereCashCashier, "");
        Vector listCashcashierTotal = PstCashCashier.listDataClosingCashier(0, 0, whereCashCashier, "");
        
        int totalData = listCashcashierTotal.size();
        
        double tamp =Math.floor(totalData/jmlShowData);
        double tamp2 = totalData % jmlShowData;
        if (tamp2>0){
            tamp2= 1;	
        } 
        double JumlahHalaman = tamp2 + tamp;
        
        String attDisFirst ="";
        String attDisNext ="";

        if (showPage==1){
            attDisFirst= "disabled";
        }

        if (showPage==JumlahHalaman){
            attDisNext ="disabled";
        }
        
        container += ""
        + "<table class='table table-bordered table-striped'>"
            + "<thead>"
                + "<tr>"
                    + "<th>No</th>"
                    + "<th style='width;20%'>Date</th>"
                    + "<th>Shift</th>"
                    + "<th>Location</th>"
                    + "<th>User</th>"
                + "</tr>"
            + "</thead>"
            + "<tbody>";
            if (listCashCashier.size()>0){
                int counter = start+1;
                for (int i = 0; i<listCashCashier.size();i++){                  
                    Vector listTemp = (Vector)listCashCashier.get(i);
                    CashCashier cashCashier = (CashCashier) listTemp.get(0);
                    com.dimata.posbo.entity.admin.AppUser appUser = (com.dimata.posbo.entity.admin.AppUser) listTemp.get(1);
                    Location location = (Location) listTemp.get(2);
                    Shift shift = (Shift) listTemp.get(3);
                    
                    container += ""
                    + "<tr>"
                        + "<td>"+counter+"</td>"             
                        + "<td><input style='cursor:pointer' type='text' data-shift='"+shift.getOID()+"' data-cashoid='"+cashCashier.getOID()+"' readonly value='"+Formater.formatDate(cashCashier.getOpenDate(), "MM/dd/yyy")+"' class='form-control clickCashCashier'></td>"
                        + "<td>"+shift.getName()+"</td>"
                        + "<td>"+location.getName()+"</td>"
                        + "<td>"+appUser.getFullName()+"</td>"
                    + "</tr>";  
                    
                    counter ++;
                }
            }else{
                container += ""
                + "<tr>"
                    + "<td colspan='5'><center>No Data</center></td>"
                + "</tr>";
            }
        container +=""    
            + "</tbody>"
        + "</table>";
        
        if (listCashCashier.size()>0){
            container +=""
            + "<div class='row'>"
                + "<input type='hidden' id='totalPagings' value='"+Formater.formatNumber(JumlahHalaman,"")+"'>"
                + "<input type='hidden' id='paggingPlaces' value='"+showPage+"'>"
                + "<div class='col-md-6'>"
                    + "<label>"
                        + "Page "+showPage+" of "+Formater.formatNumber(JumlahHalaman, "")+""
                    + "</label>"
                + "</div>"
                + "<div class='col-md-6'>"
                    + "<div data-original-title='Status' class='box-tools pull-right' title=''>" 
                        + "<div class='btn-group' data-toggle='btn-toggle'>" 
                            + "<button "+attDisFirst+" data-type='first' type='button' class='btn btn-default pagings2'>"
                                + "<i class='fa fa-angle-double-left'></i>"
                            + "</button>" 
                            + "<button id='prevPaging' "+attDisFirst+" data-type='prev' type='button' class='btn btn-default pagings2'>"
                                + " <i class='fa fa-angle-left'></i>"
                            + "</button>" 
                            + "<button id='nextPaging' "+attDisNext+" data-type='next' type='button' class='btn btn-default pagings2'>"
                                + "<i class='fa fa-angle-right'></i>"
                            + "</button>" 
                            + "<button "+attDisNext+" data-type='last' type='button' class='btn btn-default pagings2'>"
                                + " <i class='fa fa-angle-double-right'></i>"
                            + "</button>" 
                        + "</div>" 
                    + "</div>"                           
                + "</div>"                          
            + "</div>"
        + "";
    }
        
        return container;
    }
    
    public String cashCashierEdit(HttpServletRequest request){
        String result ="";
        
        long oidCashCashier = FRMQueryString.requestLong(request, "oidCashCashier");
        long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
        
        BillMain billMain = new BillMain();
        CashCashier cashCashier = new CashCashier();
        Balance cashBalanceSource = new Balance();
        Balance cashBalanceDest = new Balance();
        
        
        
        
        try {
            billMain = PstBillMain.fetchExc(oidBillMain);
        } catch (Exception e) {
        }
        
        try {
            cashCashier = PstCashCashier.fetchExc(oidCashCashier);
        } catch (Exception e) {
        }
        
        String whereCashSource = ""
            + " "+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"="+billMain.getCashCashierId()+""
            + " AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"=1 "
            + " AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"="+billMain.getCurrencyId()+"";
        
        String whereCashDest = ""
            + " "+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"="+oidCashCashier+""
            + " AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"=1 "
            + " AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"="+billMain.getCurrencyId()+"";
        
        Vector listCashSource = PstBalance.list(0, 1, whereCashSource, "");
        
        Vector listCashDest = PstBalance.list(0, 1, whereCashDest, "");
        try {
            cashBalanceSource = (Balance) listCashSource.get(0);
        } catch (Exception e) {
        }
        
        try {
           cashBalanceDest = (Balance) listCashDest.get(0); 
        } catch (Exception e) {
        }
       
        //UPDATE CASH BILL MAIN DULU
        String invString = billMain.getInvoiceNo();
        if (isContain(invString, "REV")==false){
            invString += "-REV";
        }
        billMain.setInvoiceNo(invString);
        billMain.setInvoiceNumber(invString);
        billMain.setBillDate(cashCashier.getCashDate());
        billMain.setCashCashierId(oidCashCashier);
        
        CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
        CtrlCloseCashCashier ctrlCloseCashCashier = new CtrlCloseCashCashier(request);
        
        int iError = ctrlBillMain.actionObject(Command.SAVE, billMain, request);
        
        if (iError==0){
            //JIKA BERHASIL UPDATE BILL MAIN, MAKA LANJUTKAN DENGAN UPDATE CASH BALANCE SOURCE DAN DESTINATION
            if (listCashSource.size()>0){
                iError = ctrlCloseCashCashier.actionObject(Command.SAVE, cashBalanceSource, request);
            }
            
            if (iError==0){
                if (listCashDest.size()>0){
                    iError = ctrlCloseCashCashier.actionObject(Command.SAVE, cashBalanceDest, request);
                }
                
            }
        }
        
        if (iError==0){
            //INSERT HISTORY
            int command = FRMQueryString.requestInt(request, "command");
            insertHistory(userIds, loginIds, command, oidBillMain, "EDIT CASH CASHIER OF THE BILL");
        }
        
        result = String.valueOf(iError);
        
        return result;
    }
    
    private static boolean isContain(String source, String subItem){
         String pattern = "\\b"+subItem+"\\b";
         Pattern p=Pattern.compile(pattern);
         Matcher m=p.matcher(source);
         return m.find();
    }
    
    public  void insertHistory(long userID, String nameUser, int cmd, long oid,String description){
        
        BillMain billMain = new BillMain();
        long oid2 = 0;
        try {
            billMain = PstBillMain.fetchExc(oid);
        } catch (Exception e) {
        }
        
        try{
           LogSysHistory logSysHistory = new LogSysHistory();
           logSysHistory.setLogUserId(userID);
           logSysHistory.setLogLoginName(nameUser);
           logSysHistory.setLogApplication("Cashier Web");
           logSysHistory.setLogOpenUrl("cashier/cashier-maintenance-edit.jsp?oid="+oid+"");
           logSysHistory.setLogUpdateDate(new Date());
           logSysHistory.setLogDocumentType("Transaction Mintenance");
           logSysHistory.setLogUserAction(Command.commandString[cmd]);
           logSysHistory.setLogDocumentNumber(billMain.getInvoiceNo());
           logSysHistory.setLogDocumentId(oid);
           logSysHistory.setLogDetail(description);

           if(!logSysHistory.getLogDetail().equals("") || cmd==Command.DELETE)
           {
                try {
                   oid2 = PstLogSysHistory.insertLog(logSysHistory);
                } catch (Exception e) {
                }
                
           }        
        }catch(Exception e){
        System.out.println("error "+e);
      }
    }
    
    public String saveHistory(HttpServletRequest request){
        String result ="";
        
        int command = FRMQueryString.requestInt(request,"commandHistory");
        String des = FRMQueryString.requestString(request, "des");
        long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
        
        insertHistory(userIds, loginIds, command, oidBillMain,des);
        
        return result;
    }
    
    public String deleteTransaction(HttpServletRequest request){
        String result = "";
        String whereBillDetail,whereCashPayment,whereCashReturn,whereCashCreditCard = "";
        int iError = 0;
        BillMain billMain = new BillMain();
        Billdetail billDetail = new Billdetail();
        CashPayments1 cashPayments = new CashPayments1();
        CashReturn cashReturn = new CashReturn();
        CashCreditCard cashCreditCard = new CashCreditCard();
        Balance cashBalanceDest = new Balance();
        Balance cashBalanceDest2 = new Balance();
       
        
        long oid = FRMQueryString.requestLong(request, "oid");
        try {
            billMain = PstBillMain.fetchExc(oid);
            
            if (billMain.getOID()!=0){
                
                //TRANSAKSI TUNAI
                if (billMain.getDocType()==0 && billMain.getTransType()==0 && billMain.getTransactionStatus()==0 && billMain.getStatusInv()==1){
                    
                    //MENGHAPUS BILL DETAIL
                    whereBillDetail = ""+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"="+oid+"";          
                    Vector listBillDetail = PstBillDetail.list(0, 0, whereBillDetail, "");
                    if (listBillDetail.size()>0){
                        for (int i = 0; i<listBillDetail.size();i++){
                            billDetail = (Billdetail) listBillDetail.get(i);
                            CtrlBillDetail ctrlBillDetail = new CtrlBillDetail(request);
                            iError = ctrlBillDetail.action(Command.DELETE, billDetail.getOID(), billMain.getOID());
                        }
                    }
                    
                    //MENGHAPUS CASH PAYMENT
                    
                    whereCashPayment = ""+PstCashPayment1.fieldNames[PstCashPayment1.FLD_BILL_MAIN_ID]+"="+oid+"";
                    Vector listCashPayment = PstCashPayment1.list(0, 0, whereCashPayment, "");
                    
                    if (iError==0 && listCashPayment.size()>0){                       
                        for (int j = 0; j<listCashPayment.size();j++){
                            cashPayments = (CashPayments1) listCashPayment.get(j);
                            //CEK APAKAH MEMILIKI CASH CREDIT CARD
                            try {
                                cashCreditCard = PstCashCreditCard.fetchExc(cashPayments.getOID());
                            } catch (Exception e) {
                            }

                            //HAPUS CASH PAYMENT
                            CtrlCashPayment ctrlCashPayment = new CtrlCashPayment(request);
                            iError = ctrlCashPayment.action(Command.DELETE, cashPayments.getOID(), cashPayments.getBillMainId(), iError);

                            //JIKA BERHASIL DAN JIKA CASH CREDIT CARD, MAKA DILANJUTKAN DENGAN MENGHAPUS CASH CREDIT CARD
                            if (iError==0 && cashCreditCard.getOID()!=0){
                                CtrlCashCreditCard ctrlCashCreditCard = new CtrlCashCreditCard(request);
                                iError = ctrlCashCreditCard.action(Command.DELETE, cashCreditCard.getOID());
                            }
                        }
                       
                    }
                    
                    //MENGHAPUS CASH RETURN 
                    whereCashReturn = ""+PstCashReturn.fieldNames[PstCashReturn.FLD_BILLMAIN_ID]+"="+oid+"";
                    Vector listCashReturn = PstCashReturn.list(0, 0, whereCashReturn, "");
                    
                    if (iError == 0 && listCashReturn.size()>0){
                        for (int k = 0; k<listCashReturn.size();k++){
                            cashReturn = (CashReturn) listCashReturn.get(k);
                            CtrlCashReturn ctrlCashReturn = new CtrlCashReturn(request);
                            iError = ctrlCashReturn.action(Command.DELETE, cashReturn.getOID());
                        }
                    }
                    
                    //HAPUS BILL MAIN
                    
                    if (iError==0){
                        CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
                        iError = ctrlBillMain.action(Command.DELETE, billMain.getOID(), request);
                    }
                    
                    // REBALANCING CASH BALANCE                   
                    String whereCashDest = ""
                        + " "+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"="+billMain.getCashCashierId()+""
                        + " AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"=1 "
                        + " AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"="+billMain.getCurrencyId()+"";
        
                    String whereCashDest2 = ""
                        + " "+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"="+billMain.getCashCashierId()+""
                        + " AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"=0 "
                        + " AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"="+billMain.getCurrencyId()+"";
        
                    Vector listCashDest = PstBalance.list(0, 1, whereCashDest, "");
                    Vector listCashDest2 = PstBalance.list(0, 1, whereCashDest2, "");
                    
                    try {
                        cashBalanceDest = (Balance) listCashDest.get(0); 
                        cashBalanceDest2 = (Balance) listCashDest2.get(0); 
                    } catch (Exception e) {
                    }

                    if (listCashDest.size()>0 && iError==0){
                        CtrlCloseCashCashier ctrlCloseCashCashier = new CtrlCloseCashCashier(request);
                        iError = ctrlCloseCashCashier.actionObject2(Command.SAVE, cashBalanceDest,cashBalanceDest2, request);
                    }
                    
                    
                    //result = String.va
                    
                    
                //OPEN BILL
                }else if (billMain.getDocType()==0 && billMain.getTransType()==0 && billMain.getTransactionStatus()==1){
                    //MENGHAPUS BILL DETAIL
                    whereBillDetail = ""+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"="+oid+"";          
                    Vector listBillDetail = PstBillDetail.list(0, 0, whereBillDetail, "");
                    if (listBillDetail.size()>0){
                        for (int i = 0; i<listBillDetail.size();i++){
                            billDetail = (Billdetail) listBillDetail.get(i);
                            CtrlBillDetail ctrlBillDetail = new CtrlBillDetail(request);
                            iError = ctrlBillDetail.action(Command.DELETE, billDetail.getOID(), billMain.getOID());
                        }
                    }
                    
                    if (iError==0){
                        CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
                        iError = ctrlBillMain.action(Command.DELETE, billMain.getOID(), request);
                    }
                    
                    
                }
                
                
                
            }
  
        } catch (Exception e) {
        }
        
        
        return result;
    }
    
    public String openTransaction(HttpServletRequest request){
        String htmlReturn = "";
        
        long oidBillMain = FRMQueryString.requestLong(request, "oid");
        String description = "OPEN THE CLOSED DOCUMENT";
        
        BillMain entBillMain = new BillMain();
        try {
            entBillMain = PstBillMain.fetchExc(oidBillMain);
        } catch (Exception e) {
        }
        entBillMain.setBillStatus(2);
        CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
        int iError = ctrlBillMain.actionObject(Command.SAVE,entBillMain , request);
        if (iError==0){
            insertHistory(userIds, loginIds, Command.SAVE, oidBillMain,description);
        }
        
        
        return htmlReturn;
    }
    
    public void cancelTransaction(HttpServletRequest request) {
        try {
            long oidBillMain = FRMQueryString.requestLong(request, "BILL_MAIN_ID");
            BillMain billMain = PstBillMain.fetchExc(oidBillMain);
            int cashierNumber = PstCustomBillMain.getCashierNumber(billMain.getCashCashierId());

            String invoiceNumb = "";
            if (billMain.getInvoiceNumber().toLowerCase().contains("x")) {
                String cashierNumberFormat = "00" + cashierNumber + "." + Formater.formatDate(new Date(), "yyyyMMdd");
                Vector listLastInvcNumber = PstBillMain.list(0, 1, "" + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '%" + cashierNumberFormat + "%' "
                        + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " NOT LIKE '%C'", PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " DESC");
                if (listLastInvcNumber.size() > 0) {
                    BillMain billMainInv = (BillMain) listLastInvcNumber.get(0);
                    String[] number = billMainInv.getInvoiceNo().split("\\.");
                    int newNumber = Integer.parseInt(number[2]) + 1;
                    String lastNumber = "000" + newNumber;
                    lastNumber = lastNumber.substring(lastNumber.length() - 3);
                    invoiceNumb = cashierNumberFormat + "." + lastNumber;
                } else {
                    invoiceNumb = cashierNumberFormat + ".001";
                }
            } else {
                invoiceNumb = billMain.getInvoiceNumber();
            }

            billMain.setInvoiceNumber(invoiceNumb);
            billMain.setCashCashierId(billMain.getCashCashierId());
            billMain.setDocType(PstBillMain.TYPE_INVOICE);
            billMain.setTransctionType(PstBillMain.TRANS_TYPE_CASH);
            billMain.setTransactionStatus(PstBillMain.TRANS_STATUS_DELETED);
            billMain.setStatusInv(PstBillMain.INVOICING_ON_PROSES);
            billMain.setInvoiceNo(invoiceNumb);
            billMain.setNotes(billMain.getNotes() + "; cancel by " + this.loginIds);
            PstBillMain.updateExc(billMain);
            CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
            ctrlBillMain.updateTable(billMain);
        } catch (DBException | NumberFormatException e) {
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
