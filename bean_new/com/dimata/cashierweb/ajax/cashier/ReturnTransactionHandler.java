/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.cashierweb.ajax.cashier;

import com.dimata.cashierweb.entity.cashier.printtemplate.PrintTemplate;
import com.dimata.cashierweb.entity.cashier.transaction.BillMainCostum;
import com.dimata.cashierweb.entity.cashier.transaction.CustomCashCreditCard;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomCashCreditCard;
import com.dimata.cashierweb.entity.masterdata.PstRoom;
import com.dimata.cashierweb.entity.masterdata.PstTableRoom;
import com.dimata.cashierweb.entity.masterdata.TableRoom;
import com.dimata.cashierweb.form.cashier.CtrlReturn;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.payment.CurrencyType;
import com.dimata.common.entity.payment.PaymentSystem;
import com.dimata.common.entity.payment.PstCurrencyType;
import com.dimata.common.entity.payment.PstPaymentSystem;
import com.dimata.gui.jsp.ControlList;
import com.dimata.pos.entity.billing.BillMain;
import com.dimata.pos.entity.billing.Billdetail;
import com.dimata.pos.entity.billing.PstBillDetail;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.entity.payment.CashPayments;
import com.dimata.pos.entity.payment.PstCashPayment;
import com.dimata.pos.form.billing.FrmBillDetail;
import com.dimata.pos.form.billing.FrmBillMain;
import com.dimata.posbo.entity.warehouse.MatCosting;
import com.dimata.posbo.entity.warehouse.PstMatCosting;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Witar
 */
public class ReturnTransactionHandler extends HttpServlet {
    
     //OBJECT
    private JSONObject jSONObject = new JSONObject();
    private JSONArray jSONArray = new JSONArray();
    
     //LONG
    private long oidReturn = 0;
    
    //STRING
    private String dataFor = "";
    private String approot = "";
    private String htmlReturn = "";
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
        this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
        this.iCommand = FRMQueryString.requestCommand(request);
        
        this.iErrCode = 0;        
        this.jSONObject = new JSONObject();
        
        switch(this.iCommand){
	    case Command.SAVE :
		commandSave(request);
	    break;
                
            case Command.LIST :
                commandList(request);
            break;
		
	    
	    default : //commandNone(request);
	}
        
        try{
	    
	    this.jSONObject.put("FRM_FIELD_HTML", this.htmlReturn);
	    this.jSONObject.put("FRM_FIELD_RETURN_OID", this.oidReturn);
	}catch(JSONException jSONException){
	    jSONException.printStackTrace();
	}
        
        response.getWriter().print(this.jSONObject);
    }
    
    public void commandNone(HttpServletRequest request){
	
    }
    
    public void commandSave(HttpServletRequest request){
	if (this.dataFor.equals("returnBillSave")){
            this.htmlReturn = saveReturnBill(request);
        }
    }
    
    public void commandList(HttpServletRequest request){
        if (this.dataFor.equals("listReturnBill")){
            this.htmlReturn = listReturnBill(request);
        }else if (this.dataFor.equals("loadDetailListReturn")){
            this.htmlReturn = loadDetailListReturn(request);
        }
    }
    
    private String listReturnBill(HttpServletRequest request){
        String returnHtml="";
        
        String date1 = "";
        String date2="";
        String searchType ="";
        String addClass="";
        long location = 0;
        
        //INTEGER
        int showPage = 1;
        int jmlShowData = 10;
        int page = 0;
        int totalData =0;
        int start=0;
        int jmlData=0;
        
        try {
            date1= FRMQueryString.requestString(request, "date1");
        } catch (Exception e) {
            date1="";
        }
        
        try {
            date2 = FRMQueryString.requestString(request, "date2");
        } catch (Exception e) {
            date2="";
        }
                    
        try {
            location = FRMQueryString.requestLong(request, "location");
        } catch (Exception e) {
            location=0;
        }
        
        try {
            searchType = FRMQueryString.requestString(request, "searchText");
        } catch (Exception e) {
        }
        
        try {
            showPage = FRMQueryString.requestInt(request, "FRM_FIELD_SHOW_PAGE");
        } catch (Exception e) {
            showPage = 1;
        }
        
        if (showPage==0){
            showPage = 1;
        }
        
        start = (showPage-1) * jmlShowData;
        
        String whereClause = "";
        if (searchType.length() != 0 || date1.length() != 0 || date2.length() != 0) {
            if (searchType.length() != 0) {
                if (date1.length() != 0 && date2.length() != 0) {
                    whereClause = " AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%" + searchType + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " like '%" + searchType + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + searchType + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " like '%" + searchType + "%')"
                        + "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') ";
                } else if (date1.length() != 0 && date2.length() == 0) {
                    whereClause = " AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%" + searchType + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " like '%" + searchType + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + searchType + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " like '%" + searchType + "%')"
                        + "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date1 + "') ";
                } else if (date1.length() == 0 && date2.length() != 0) {
                    whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%" + searchType + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " like '%" + searchType + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + searchType + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " like '%" + searchType + "%')"
                        + "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date2 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') ";
                } else {
                    whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%" + searchType + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " like '%" + searchType + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + searchType + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " like '%" + searchType + "%')";
                }
            } else {
                if (date1.length() != 0 && date2.length() != 0) {
                    whereClause = "AND ("
                        + "(Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') "
                        + ")";
                } else if (date1.length() != 0 && date2.length() == 0) {
                    whereClause = "AND ("
                        + "(Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date1 + "') "
                        + " )";
                } else if (date1.length() == 0 && date2.length() != 0) {
                    whereClause = "AND ("
                        + "(Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date2 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') "
                        + " )";
                } else {
                    whereClause = "";
                }
            }   
        }
        
        if (location!=0){
            whereClause += " AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID]+"="+location+"";
        }
        
        String order = "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" desc";
        Vector listBillMain = PstCustomBillMain.listOpenBill(start, jmlShowData, 
        "("
            + "("
                + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
                + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
            + ") OR ("
                + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
                + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
            + ") OR ("
                + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
            + ")"
        + ")"
        + " " +whereClause, order);
        
        Vector listTotalBillMain = PstCustomBillMain.listOpenBill(0, 0, 
        "("
            + "("
                + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
                + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
            + ") OR ("
                + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
                + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
            + ") OR ("
                + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
            + ")"
        + ")"
        + " " +whereClause, order);
        
        jmlData = listTotalBillMain.size();
        
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
        
        returnHtml +=""
        + "<table class='table table-bordered table-hover table-striped'>"
        + "<thead>"
            + "<tr>"
                + "<th>No.</th>"
                + "<th>Location</th>"
                + "<th>Date</th>"
                + "<th>Name</th>"   
                + "<th>Room</th>"
                + "<th>Table No.</th>"
            + "</tr>"
        + "</thead>"
        + "<tbody>";
            if (listBillMain.size()==0){
                returnHtml += ""
                + "<tr>"
                    + "<td colspan='6'>No Data</td>"
                + "</tr>";
            }else{
                int count=0;
                for (int i = 0; i<listBillMain.size();i++){
                    count = i+1;
                    if (i==0){
                        addClass="firstFocus";
                    }else{
                        addClass ="";
                    }
                    BillMainCostum billMain = (BillMainCostum) listBillMain.get(i);
                    
                    String roomName="";
                    String tableName="";
                    if (billMain.getRoomName() == null){
                        roomName ="-";
                    }else{
                        roomName= billMain.getRoomName();
                    }
                    if (billMain.getTableNumber()== null){
                        tableName ="";
                    }else{
                        tableName = billMain.getTableNumber();
                    }
                    returnHtml += ""
                    + "<tr>"
                        + "<td><input style='cursor:pointer;' type='text' readonly='readonly' class='form-control textual5 searchBillReturnSelect "+addClass+"' data-mainoid='"+billMain.getOID()+"' value='"+billMain.getInvoiceNo()+"'></td>"
                        + "<td>"+billMain.getLocationName()+"</td>"
                        + "<td>"+billMain.getBillDate()+"</td>"
                        + "<td>"+billMain.getGuestName()+"</td>"
                        + "<td>"+roomName+"</td>"                       
                        + "<td>"+tableName+"</td>"
                    + "</tr>";
                }
            }
            
            returnHtml +=""  
            + "</tbody>"
        + "</table>";
            
        returnHtml += ""
        + "<div class='row'>"
            + "<input type='hidden' id='totalPagingVc' value='"+Formater.formatNumber(JumlahHalaman,"")+"'>"
            + "<input type='hidden' id='paggingPlaceVc' value='"+showPage+"'>"
            + "<div class='col-md-6'>"
                + "<label>"
                    + "Page "+showPage+" of "+Formater.formatNumber(JumlahHalaman, "")+""
                + "</label>"
            + "</div>"
            + "<div class='col-md-6'>"
                + "<div data-original-title='Status' class='box-tools pull-right' title=''>" 
                    + "<div class='btn-group' data-toggle='btn-toggle'>" 
                        + "<button "+attDisFirst+" data-type='first' type='button' class='btn btn-default pagingVc'>"
                            + "<i class='fa fa-angle-double-left'></i>"
                        + "</button>" 
                        + "<button id='prevPagingVc' "+attDisFirst+" data-type='prev' type='button' class='btn btn-default pagingVc'>"
                            + " <i class='fa fa-angle-left'></i>"
                        + "</button>" 
                        + "<button id='nextPagingVc' "+attDisNext+" data-type='next' type='button' class='btn btn-default pagingVc'>"
                            + "<i class='fa fa-angle-right'></i>"
                        + "</button>" 
                        + "<button "+attDisNext+" data-type='last' type='button' class='btn btn-default pagingVc'>"
                            + " <i class='fa fa-angle-double-right'></i>"
                        + "</button>" 
                    + "</div>" 
                + "</div>"                           
            + "</div>"                          
        + "</div>";
        
        return returnHtml;
    }
    
    public String drawListReturn(int iCommand, Vector objectClass, long oidPaymentRec, FrmBillMain frmObject) {
        //2
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("No", "20%");
        ctrlist.addHeader("Name", "20%");
        ctrlist.addHeader("Quantity");
	ctrlist.addHeader("Price");
	ctrlist.addHeader("Discount");
	ctrlist.addHeader("Total");
	ctrlist.addHeader("Return Qty");

        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit

        ctrlist.reset();

        int index = -1;
        String addClass="";
        Vector rowx = new Vector(1, 1);
	double grandTotal = 0;
	String addInner = "";
        for (int i = 0; i < objectClass.size(); i++) {
            //long famOid = 0;
            
            //firstFocus class
            if (i==0){
                addClass="firstFocus";
            }else if (i==(objectClass.size()-1)){
                addClass="lastFocus";
            }else{
                addClass="";
            }

            Billdetail billdetail = (Billdetail) objectClass.get(i);

            int checkReturn = PstCustomBillMain.checkReturn(oidPaymentRec, billdetail.getOID());
	    
	    if(checkReturn > 0){
		rowx = new Vector(1, 1);
		grandTotal+=billdetail.getTotalPrice();

		rowx.add(""+(i+1));
		rowx.add(""+billdetail.getItemName());
		rowx.add(""+billdetail.getQty());
		rowx.add(""+Formater.formatNumber(billdetail.getItemPrice(),"#,###"));
		rowx.add(""+Formater.formatNumber(billdetail.getDisc(),"#,###"));
		if((i+1) == objectClass.size()){
		    addInner +="<input type='hidden' id='grandTotalData' value='"+ Formater.formatNumber(grandTotal,"#,###")+"'>";
		}
		rowx.add(""+Formater.formatNumber(billdetail.getTotalPrice(),"#,###")+addInner);
		rowx.add("<input  type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]+"_"+i+"' value='0' id='qtyTemp-"+i+"' class='form-control qtytemp returntext textual6 "+addClass+" ' style='width:100px;'> "
			+ "<input type='hidden' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID]+"' value='"+billdetail.getOID()+"'>"
                        + ""
                        + "<input type='hidden' class='qtyReturnHelp' id='qtyReturn-"+i+"' value='"+billdetail.getQty()+"'>");
		lstData.add(rowx);
	    }
            
        }
        return ctrlist.drawBootstrapStripted();
    }
    
    private String loadDetailListReturn(HttpServletRequest request){
        String htmlReturn = "";
        long oid = 0;
        
        oid = FRMQueryString.requestLong(request, "FRM_FIELD_BILL_MAIN");
        
        Vector listBill = PstCustomBillMain.listItemOpenBill(0, 0, 
            "billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+oid+"'", "");

        BillMain billMain = new BillMain();
        try{
            billMain = PstBillMain.fetchExc(oid);
        }catch(Exception ex){

        }
        htmlReturn = ""
        + "<div class='row'>"
            + "<div class='col-md-12'>"
                + "<div class='checkbox' id='returnToAll' style='float: right;font-weight:bold;'>"
                    + "<label style='font-weight:bold;'><input type='checkbox' id='returnAll'  value=''>Return All</label>"
                + "</div>"
            + "</div>"
        + "</div>"
        + "<div class='row'>"
            + "<div class='col-md-12'>";

        htmlReturn += drawListReturn(iCommand, listBill, oid, null);
        htmlReturn += ""
                + "<input type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] + "' value='" + oid + "'>"
            + "</div>"
        + "</div>";
        
        return  htmlReturn;
    }
    
    private String saveReturnBill(HttpServletRequest request){
        String returnHtml="";
        
        long oid = FRMQueryString.requestLong(request, "FRM_FIELD_CASH_BILL_MAIN_ID");
        String oidCashierLogin = FRMQueryString.requestString(request, "FRM_FIELD_CASHIER_LOGIN");
        String appRoot = FRMQueryString.requestString(request, "approot");
        
        
        CtrlReturn ctrlReturn = new CtrlReturn(request);
        iErrCode = ctrlReturn.action(this.iCommand, oid, request);
        returnHtml = drawPrintReturn(oid, oidCashierLogin,appRoot ,"");
        
        return returnHtml;
    }
    
    public String drawPrintReturn(long oidBillMain, String cashierName, String approot, String oidMember){
	
	
	String displayPrint="";
	String transactionName = "";
	String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
	String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
	String displayPaymentSystem = "";
	String displayDiscPct = "";
	String invoiceNumb = "";
	String paymentType = "";
	BillMainCostum billMainCostum;
	Location location;
	MatCosting matCosting;
	BillMain billMain;
	BillMain lastBillMain;
	Vector listBillMain = PstBillMain.list(0, 0, 
		""+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='"+oidBillMain+"'", 
		""+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+" DESC");
	if(listBillMain.size() != 0){
	    billMain = (BillMain) listBillMain.get(0);

	}else{
	    billMain = new BillMain();
	}
	
	
	try{
	    lastBillMain = PstBillMain.fetchExc(oidBillMain);
	    billMainCostum = PstCustomBillMain.fetchByCashierID(billMain.getCashCashierId());
	    location = PstLocation.fetchExc(billMain.getLocationId());
	    Vector listMatCosting = PstMatCosting.list(0, 0, 
		    ""+PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER]+"='"+oidBillMain+"'","");
	    if(listMatCosting.size() != 0){
		matCosting = (MatCosting) listMatCosting.get(0);
	    }else{
		matCosting = new MatCosting();
	    }
	    
	}catch(Exception ex){
	    billMainCostum = new BillMainCostum();
	    matCosting = new MatCosting();
	    location = new Location();
	    lastBillMain = new BillMain();
	}
	
	
	transactionName="RETURN";
	
	
	
	
	TableRoom tableRoom = new TableRoom();
	Vector listItem = new Vector(1,1);
	CurrencyType currencyType = new CurrencyType();
	PaymentSystem paymentSystem = new PaymentSystem();
	int totalQty = 0;
	double totalPrice = 0;
	double service = 0;
	double tax =0;
	double total = 0;
	
	
	try{
	    //billMain = PstBillMain.fetchExc(oidBillMain);
	    if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
		service = 0;
		tax = 0;
	    }else{
		service = billMain.getServiceValue();
		tax = billMain.getTaxValue();
	    }
	}catch(Exception ex){
	}
	
	try{
	    tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
	}catch(Exception ex){
	}
	
	try{
	    listItem = PstBillDetail.list(0, 0, ""+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'", "");
	}catch(Exception ex){
	}
	
	Vector listCashPayment = PstCashPayment.list(0, 0, 
		""+PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'", 
		"");
	CashPayments cashPayments;
	if(listCashPayment.size() != 0){
	    cashPayments = (CashPayments) listCashPayment.get(0);
	}else{
	    cashPayments = new CashPayments();
	}
	try{	    
	    paymentSystem = PstPaymentSystem.fetchExc(cashPayments.getPaymentTypeLong());
	    displayPaymentSystem = paymentSystem.getPaymentSystem();
	}catch(Exception ex){
	}
	
	try{
	    currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
	}catch(Exception ex){
	}
	
	String nameInvoice = "";

	paymentType = "Return";
	nameInvoice = transactionName+" Invoice";
	invoiceNumb = billMain.getInvoiceNumber();
	   
	
	if(billMain.getDiscPct() > 0){
	    displayDiscPct = "("+billMain.getDiscPct()+"%) ";
	}
	
	CustomCashCreditCard customCashCreditCard;
	Vector listCreditCard = PstCustomCashCreditCard.list(0, 0, 
		""+PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CASH_PAYMENT_ID]+"='"+cashPayments.getOID()+"'", 
		"");
	if(listCreditCard.size() != 0){
	    customCashCreditCard = (CustomCashCreditCard) listCreditCard.get(0);
	}else{
	    customCashCreditCard = new CustomCashCreditCard();
	}
	
	String ccName = "";
	String ccNumber = "";
	String ccBank = "";
	double ccCharge = 0;
	String ccValid = "";
	
	if(paymentSystem.isCardInfo() == true 
			&& paymentSystem.getPaymentType() == 0
			&& paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCheckBGInfo() == false){
		    
		    
		    
	    ccName = customCashCreditCard.getCcName();
	    ccNumber = customCashCreditCard.getCcNumber();
	    ccBank = customCashCreditCard.getDebitBankName();
	    ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
	    ccCharge = customCashCreditCard.getBankCost();
		    
		    
    /////BG PAYMENT
	}else if(paymentSystem.isCardInfo() == false 
		&& paymentSystem.getPaymentType() == 2
		&& paymentSystem.isBankInfoOut() == true 
		&& paymentSystem.isCheckBGInfo() == false){

	    ccName = customCashCreditCard.getChequeAccountName();
	    ccNumber = customCashCreditCard.getCcNumber();
	    ccBank = customCashCreditCard.getChequeBank();
	    ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
	    ccCharge = customCashCreditCard.getBankCost();
		


    /////CHECK PAYMENT
	}else if(paymentSystem.isCardInfo() == true 
		&& paymentSystem.getPaymentType() == 0
		&& paymentSystem.isBankInfoOut() == false 
		&& paymentSystem.isCheckBGInfo() == false){
	    
	    ccName = customCashCreditCard.getChequeAccountName();
	    ccNumber = customCashCreditCard.getCcNumber();
	    ccBank = customCashCreditCard.getChequeBank();
	    ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
	    ccCharge = customCashCreditCard.getBankCost();

    /////DEBIT PAYMENT
	}else if(paymentSystem.isCardInfo() == false 
		&& paymentSystem.getPaymentType() == 2
		&& paymentSystem.isBankInfoOut() == false 
		&& paymentSystem.isCheckBGInfo() == false){
	    
	    
	    ccName = customCashCreditCard.getChequeAccountName();
	    ccNumber = customCashCreditCard.getCcNumber();
	    ccBank = customCashCreditCard.getDebitBankName();
	    ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
	    ccCharge = customCashCreditCard.getBankCost();
	    
	}
	
	PrintTemplate printTemplate = new PrintTemplate();
	displayPrint += printTemplate.PrintTemplate(billMainCostum, nameInvoice,
		invoiceNumb, cashierName, billMain, tableRoom, "printreturn", 
		paymentType, displayPaymentSystem, total, paymentSystem.getOID(), ccName,
		ccNumber, ccValid, ccBank, ccCharge, approot, oidMember);
	
	
	
	
	return displayPrint;
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
