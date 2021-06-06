<%@page import="com.dimata.pos.entity.masterCashier.PstCashMaster"%>
<%@page import="com.dimata.pos.form.payment.FrmCashPayment"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.QueensLocation"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.PstQueensLocation"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.BillMainJSON"%>
<%@page import="com.dimata.pos.form.billing.FrmBillMain"%>
<%@page import="com.dimata.pos.form.billing.FrmBillDetail"%>
<%@page import="com.dimata.qdep.form.FRMHandler"%>
<%@page import="com.dimata.pos.entity.billing.PstBillDetailCode"%>
<%@page import="com.dimata.pos.entity.billing.BillDetailCode"%>
<%@page import="com.dimata.pos.entity.billing.Billdetail"%>
<%@page import="com.dimata.pos.entity.billing.PstBillDetail"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.common.entity.payment.CurrencyType"%>
<%@page import="com.dimata.common.entity.payment.PstCurrencyType"%>
<%@page import="com.dimata.posbo.entity.masterdata.PstSales"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.pos.entity.billing.PstBillMain"%>
<%@page import="com.dimata.pos.entity.billing.BillMain"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.common.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.posbo.entity.masterdata.Shift"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Time"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.posbo.entity.masterdata.PstShift"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.common.entity.location.Location"%>
<%@page import="com.dimata.common.entity.location.PstLocation"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.pos.entity.balance.OpeningCashCashier"%>
<%@page import="com.dimata.pos.entity.balance.PstCashCashier"%>
<%@page import="com.dimata.pos.form.balance.FrmCashCashier"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="../main/javainit_cashier.jsp" %>

<%
    String multilocation = PstSystemProperty.getValueByName("OUTLET_MULTILOCATION");
    long defaultOidLocation = Long.parseLong(PstSystemProperty.getValueByName("OUTLET_DEFAULT_LOCATION"));
    
    long oidBillMain = FRMQueryString.requestLong(request, "oid");
    
    BillMain billMain = new BillMain();
    AppUser ap = new AppUser();
    try {
        billMain = PstBillMain.fetchExc(oidBillMain);
        ap = PstAppUser.fetch(billMain.getAppUserId());
    }
    catch(Exception e) {
        System.out.println("Exc when get bill detail : " + e.toString());
    }
    
    String sWhereClause = PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + " = " + oidBillMain;
    Vector records = PstBillDetail.list(0, 0, sWhereClause, "");
    
    String strCurrencyType = "";
    if(billMain.getCurrencyId() != 0) {
	String whereClause = PstCurrencyType.fieldNames[PstCurrencyType.FLD_CURRENCY_TYPE_ID]+"="+billMain.getCurrencyId();
	Vector listCurrencyType = PstCurrencyType.list(0, 0, whereClause, "");
	CurrencyType currencyType = (CurrencyType)listCurrencyType.get(0);
	strCurrencyType = currencyType.getCode();
    }
    
    
    
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Open Cashier - Dimata Cashier</title>
    <%@include  file="cashier-css.jsp"%>   
    <script type="text/javascript" src="../styles/cashier/dist/js/numberformat.js"></script>
    <style>
        .textual:focus {
            background-color: yellow;
        }
        
    </style>
</head>
<body class="skin-queentandoor sidebar-mini wysihtml5-supported sidebar-collapse fixed">
    <div class="wrapper"> 
        <header class="main-header">
            <%@include file="cashier-header.jsp" %>
        </header>
        <aside class="main-sidebar">
            <%            String home = "";
                String transaction = "";
                String log = "";
                String maintenance = "active";
            %>
            <%@include file="cashier-sidebar.jsp" %>
        </aside>
        <div class="content-wrapper">
            <section class="content-header">
                <h1>
                    Dimata Cashier
                    <small>Transaction Maintenance</small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="#"><i class="fa fa-home"></i> Home</a></li>
                    <li class="active">Transaction Maintenance</li>
                </ol>
            </section>
            <section class="content">
                <div class="row">
                    <input type="hidden" name="locationBillIdSource" id="locationBillIdSource">
                    <div class="col-md-12">
                        <div class="box box-primary">
                            <div class="box-header with-border">
                                <h3 class="box-title">Transaction Maintenance</h3>
                            </div>
                            <div class="box-body">
                                <div class="row" style="margin-top: 10px;">
                                    <div class="col-md-4">
                                        <div class="col-md-4"><label>Invoice</label></div>
                                        <div class="col-md-8"><input value="<%=billMain.getInvoiceNumber()%>" readonly type="text" class="form-control"></div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="col-md-4"><label>Customer</label></div>
                                        <%
                                            String custName = "";
                                            String custCode = "";
                                            ContactList contactlist = new ContactList();
                                            try {
                                                contactlist = PstContactList.fetchExc(billMain.getCustomerId());
                                                custName = contactlist.getPersonName();
                                                custCode = contactlist.getContactCode();
                                            } catch (Exception e) {
                                                System.out.println("Contact not found ...");
                                            }
                                        %>
                                        <div class="col-md-8"><input value="<%=custName%>" readonly type="text" class="form-control"></div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="col-md-4"><label>Sales</label></div>
                                        <div class="col-md-8"><input value="<%=ap.getFullName() %>" readonly type="text" class="form-control"></div>
                                    </div>
                                </div>    
                                <div class="row" style="margin-top: 10px;">
                                    <div class="col-md-4">
                                        <div class="col-md-4"><label>Date</label></div>
                                        <div class="col-md-8"><input value="<%=Formater.formatDate(billMain.getBillDate(), "dd MMM yyyy  kk:mm:ss")%>" readonly type="text" class="form-control"></div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="col-md-4"><label>Customer Code</label></div>
                                        <div class="col-md-8"><input value="<%=custCode%>" readonly type="text" class="form-control"></div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="col-md-4"><label>Note</label></div>
                                        <div class="col-md-8"><input value="<%=billMain.getNotes()%>" readonly type="text" class="form-control"></div>
                                    </div>
                                </div> 
                                <div class="row" style="margin-top: 10px;">
                                    <div class="col-md-4">
                                        <div class="col-md-4"><label>Currency</label></div>
                                        <div class="col-md-8"><input value="<%=strCurrencyType%>" readonly type="text" class="form-control"></div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="col-md-4"><label>Location</label></div>
                                        <%
                                            String locationname = "";
                                            Location location = new Location();
                                            try {
                                                location = PstLocation.fetchExc(billMain.getLocationId());
                                                locationname = location.getName();
                                            } catch (Exception e) {

                                            }
                                        %>
                                        <div class="col-md-8"><input value="<%= locationname%>" readonly type="text" class="form-control"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="box-footer">
                                <a class="btn btn-primary" href="<%=approot%>/cashier/cashier-transaction-maintenance.jsp"><i class="fa fa-undo"></i> Back</a>
                                <button style="" id="editTransaction" type="button" class="btn btn-warning pull-right"><i class="fa fa-pencil"></i> Edit Cash Cashier</button>                                                                              
                                <!--button style="width:49%" id="deleteTransaction" type="button" class="btn btn-danger pull-right"><i class="fa fa-trash"></i> Delete</button-->
                            </div>
                        </div>                   
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="box box-primary">
                            <div class="box-header with-border">
                                <h3 class="box-title">Item List</h3>
                            </div>
                            <div class="box-body">
                                <div class="row" style="">
                                    <div class="col-md-12" id="dynamicDetail">

                                    </div>
                                </div>
                                <div class="row" style="">
                                    <div class="col-md-12" id="dynamicTotal">

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="paymentPlace"></div>

            </section>
        </div>
    <footer class="main-footer">
        <div class="pull-right hidden-xs">
            <b>Version</b> 1.0
        </div>
        <strong>Copyright &copy; 2015 <a href="http://almsaeedstudio.com">Dimata IT Solution &REG;</a>.</strong> All rights reserved.
    </footer>  
    </div>
    <%@include file="cashier-jquery-bootstrap.jsp" %>
    <script type="text/javascript" src="../styles/dimata-app.js"></script>
    <script type="text/javascript">       
        $(document).ready(function (){   
            var base = "<%= approot %>";
            function ajaxMaintenanceTransaction(url,data,type,appendTo,another,optional,optional2){
                $.ajax({
                    url : ""+url+"",
                    data: ""+data+"",
                    type : ""+type+"",
                    async : false,
                    cache: false,
                    success : function(data) {                      
                        $(''+appendTo+'').html(data);
                        if (another=="getListCashCashier"){
                            if (optional==true){
                                $("#cashcashierlist").modal("show");
                            }
                        }
                    },
                    error : function(data){
                        //alert('error');
                    }
                }).done(function(data){
                    if (another=="loadData"){
                        editItem();
                        deleteItem();
                        showAddModal();
                       
                    }else if (another=="loadEditItem"){
                        //alert(data);
                        if (optional==true){
                            $("#editItemModal").modal("show");
                            itemChange();
                            saveEditItem();
                        }
                        
                    }else if (another=="saveEdit"){
                        $("#editItemModal").modal("hide");
                        loadData();
                    }else if (another=="loadDeleteItem"){
                        if (optional==true){
                            $("#editItemModal").modal("show");
                        }
                        deleteItemProc();
                    }else if (another=="deleteProccess"){
                        $("#editItemModal").modal("hide");
                        loadData();
                    }else if (another=="getLocationBill"){                       
                        $('#locationBillIdSource').val(data);
                    }else if (another=="loadDataPayment"){
                        deletePaymentItem();
                        editpaymentitem();
                        addItemPayemnt();
                    }else if (another=="deletePaymentProccess"){
                        loadDataPayment();
                    }else if (another=="getListCashCashier"){
                        loadDataPagings2();
                        clickCashCashier();

                    }else if (another=="cashCashierEdit"){
                        //alert("test");
                        window.location.reload();
                    }else if (another=="saveAddItemProc"){
                        //alert(data);
                        if (data=="UPDATE"){
                            window.location.reload();
                        }else{
                            $("#btnSaveAddMultiPayment").removeAttr("disabled");
                            loadDataPayment();
                            $("#confirmationModal").modal('hide');
                            $("#addMultiPayment").modal('hide');
                        }
                        
                        
                    }else if (another=="deleteTransaction"){
                        window.location=""+base+"/cashier/cashier-transaction-maintenance.jsp";
                    }
                    $("#btnSaveAddMultiPayment").removeAttr("disabled");
                    $("#confirmationOk").removeAttr("disabled");
                });
            }
            
            function ajaxTransactionHandler(url,data,type,appendTo,another,optional,optional2){
                $.ajax({
                    url : ""+url+"",
                    data: ""+data+"",
                    type : ""+type+"",
                    dataType:"json",
                    async : false,
                    cache: false,
                    success : function(data) { 
                        var html = data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>;
                        $(''+appendTo+'').html(html);
                        if (another=="editItem"){
                            $('#openauthorize #AUTHORIZE_TITLE').html('EDIT ITEM');
                            $('#openauthorize #APP_USER').hide();
                            $('#openauthorize').css('overflow-y','auto');
                            $("#cancelButton").hide();
                            $('#openauthorize').modal('show');
                            $('#openadditem').modal('hide');                           
                        }else if (another=="payTypes2"){
                            $(".datePicker").datepicker({
                                format: 'yyyy-mm-dd',
                            }).on("changeDate", function (ev) {
                                $(this).datepicker('hide');
                            });
                            searchVoucher("#searchVoucher2");
                        }else if (another=="showAddModal"){
                            if (optional==true){
                                $("#openitemlist").modal("show");
                                
                            }
                        }
                        $("#confirmationOk").removeAttr("disabled");
                        
                    },
                    error : function(data){
                        alert("error");
                    }
                }).done(function(data){
                    //alert(another);
                    if (another=="getLocationBill"){                       
                        $('#locationBillIdSource').val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
                    }else if (another=="showAddModal"){
                        $("#searchItemModal").removeAttr("readonly");
                        $("#searchitemlocation").removeAttr("disabled");
                        $("#searchitemlocation").attr("type","button");
                        if (optional==true){
                            
                            loadDataPaging();
                            searchitemlocation();
                            clickItemList();
                        }
                        textual();
                    }else if (another=="loadItemListPage"){
                        loadDataPaging();
                        textual();
                    }else if (another=="saveItemAdd"){
                        $("#btnsaveitem").removeAttr("disabled").html("<i class='fa fa-plus'></i> Add Item");
                        $("#openadditem").modal("hide");
                        loadData();
                        saveHistory("<%=Command.SAVE%>","ADD NEW ITEM TO THE BILL");
                    }else if (another=="editItem"){
                        $("#btnSaveBillDetail").removeAttr("type");
                        $("#btnSaveBillDetail").attr("type","button");
                        itemChange();
                        saveItemDetail();
                    }else if (another=="saveItemAddDetail"){
                        $("#openauthorize").modal("hide");
                        saveHistory("<%=Command.SAVE%>","ADD NEW ITEM TO THE BILL");
                        loadData();
                    }else if (another=="payTypes"){
                        payTypes2();
                    }else if (another=="payTypes2"){
                        calcMoney();
                    }
                });
            }
            
            function formatNumber(number){
		return number.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
	    }
            
            function loadData(){
                var url = ""+base+"/CashierMaintenance";
                var oidBillMain = "<%=oidBillMain%>";
                var data = "command=<%=Command.LIST%>&loadType=listDetailTransaction&oidBillMain="+oidBillMain+"";
                
                ajaxMaintenanceTransaction(url,data,"POST","#dynamicDetail","loadData","","");               
            }
            
            function loadDataPayment(){
                var url = ""+base+"/CashierMaintenance";
                var oidBillMain = "<%=oidBillMain%>";
                var data = "command=<%=Command.LIST%>&loadType=listPaymentTransaction&oidBillMain="+oidBillMain+"";
                ajaxMaintenanceTransaction(url,data,"POST","#paymentPlace","loadDataPayment","",""); 
            }
            
            function editItem(){
                $('.editmainitem').click(function(){
                    var billMainOid = $(this).data("billmainoid");
                    var billDetailOid= $(this).data("oid");
                    var materialOid = $(this).data("materialoid");
                    var url = ""+base+"/CashierMaintenance";
                    $("#headTitle").html("<b>EDIT ITEM</b>")
                    var data = "command=<%=Command.LOAD%>&loadType=editItem&billMainOid="+billMainOid+"&billDetailOid="+billDetailOid+"&materialOid="+materialOid+"";
                    ajaxMaintenanceTransaction(url,data,"POST","#dynamicContentEdit","loadEditItem",true,"");
                });
            }
            
            function deleteItem(){
                $(".deletemainitem").click(function(){
                    $("#headTitle").html("<b>DELETE ITEM</b>");
                    var billDetailOid= $(this).data("oid");
                    var url = ""+base+"/CashierMaintenance";
                    var data = "command=<%=Command.LOAD%>&loadType=deleteItem&billDetailOid="+billDetailOid+"";
                    //alert(data);
                    ajaxMaintenanceTransaction(url,data,"POST","#dynamicContentEdit","loadDeleteItem",true,"");
                });
            }
            
            function deleteItemProc(){
                $("#btnDeleteItem").click(function(){
                    var after = "deleteProccess";
                    var message = "Are you sure to delete the item ?";
                    showConfirmation(message,after);
                });
            }
            
            function deleteProccess(){               
                var url = ""+base+"/CashierMaintenance";
                var data = $("#FRM_NAME_BILL_DETAIL").serialize();
                data = data + "&fromEditable=1";
                //alert(data);
                ajaxMaintenanceTransaction(url,data,"POST","","deleteProccess","","");
            }
            
            function saveEditItem(){
                $("#btnSaveBillDetail").click(function(){
                    var after = "saveEdit";
                    var message = "Are you sure to edit these item ?";
                    showConfirmation(message,after);
                });
            }
            
            function saveEdit(){
                var url = ""+base+"/CashierMaintenance";
                var data = $("#FRM_NAME_BILL_DETAIL").serialize();
                data = data + "&fromEditable=1";
                ajaxMaintenanceTransaction(url,data,"POST","","saveEdit","","");
            }
            
            function itemChange(){
		$(".itemChange").keyup(function(){
                    //alert("test");
		    var checkVal = $(this).val();
                    var ids = $(this).attr('id');
		    if(checkVal.length == 0){                        
			$(this).val(0);
		    }
		    var taxInc = $("#taxInc").val();
		    var qty = parseFloat($("#FRM_FIELD_QTY2").val());
                    var qtyStock = parseFloat($("#stock").val());
                    var stockMode = parseFloat($("#stockCheck").val());
		    var itemPrice = parseFloat($("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val());
		    var discPct = parseFloat($("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] %>").val());
		    var discNominal = parseFloat($("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>").val());
                    var jumlahDiskon = 0;
		    if ($("#jumlahDiskon").length > 0){
                        jumlahDiskon = parseFloat($("#jumlahDiskon").val());	
                    }
		    
		    var servicePct = parseFloat($("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT] %>").val());
		    var taxPct = parseFloat($("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT] %>").val());
		    var totalPrice = qty*itemPrice;                   
		    var total = 0;
                    
                    //alert(discNominal);
                    if (ids=="jumlahDiskon"){
                        $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>").val(0);
                        $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] %>").val(0);
                        discNominal = jumlahDiskon / qty;
                        discPct = discNominal/itemPrice*100;

                    }else if (ids=="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT]%>"){
                        $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>").val(0);
			discNominal = itemPrice*(discPct/100);
                        jumlahDiskon = discNominal * qty;
                        
                    }else if (ids=="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>"){
                        $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] %>").val(0);
                        discPct = discNominal/itemPrice*100;
                        jumlahDiskon = itemPrice*(discPct/100) * qty;
                        
                    }else if (ids=="FRM_FIELD_QTY2"){
                        $("#FRM_FIELD_QTY2").val(qty);
                        if (stockMode != <%=PstCashMaster.STOCK_MODE_NO_STOCK_CHECKING%>){
                            var inputQty = parseFloat($(this).val());
                            if (inputQty > qtyStock){
                                alert("Qty Exceed Stock! Current Stock is "+qtyStock);
                                $("#FRM_FIELD_QTY2").val(qtyStock);
                                return false;
                            }
                        }
                        if(discPct > 0){
                            $("#jumlahDiskon").val(0);
                            jumlahDiskon = itemPrice*(discPct/100) * qty;
                        }

                    }else{
                        if(discPct > 0){
                            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>").val(0);
                            discNominal = itemPrice*(discPct/100);                         
                        }else{
                            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] %>").val(0);
                            discPct = discNominal/itemPrice*100;    
                        }
                    }
		    var afterDiscount = totalPrice-jumlahDiskon;
		    var serviceValue = afterDiscount*(servicePct/100);
		    var afterService = afterDiscount+serviceValue;
		    var taxValue = afterDiscount*(taxPct/100);
		   
		    if(taxInc == <%= PstBillMain.INC_CHANGEABLE %> || taxInc == <%= PstBillMain.INC_NOT_CHANGEABLE %>){
			total = afterDiscount;
		    }else{
			total = afterDiscount+serviceValue+taxValue;
		    }
		    
                    //alert(total);
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] %>").val(discPct);
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>").val(discNominal);
		    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_SERVICE] %>").val(serviceValue);
		    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_TAX] %>").val(taxValue);
                    $("#jumlahDiskon").val(jumlahDiskon);
		    $("#afterDiscount").html(formatNumber(afterDiscount));
		    $("#afterService").html(formatNumber(afterService));
		    $("#total").html(formatNumber(total));
		});
	    }
            
            function showConfirmation(message,after){
                $("#modalActAfter").val(after);
                $("#messageContent").html(message);
                $("#confirmationModal").modal("show");
            }
            
            function getLocationByCashBillMain(){
                var url = ""+base+"/TransactionCashierHandler";
                var oidBillMain = "<%=oidBillMain%>";
                var data = "loadtype=checklocation&command=<%=Command.NONE%>&cashier="+oidBillMain+"";
                //alert(data);
                ajaxTransactionHandler(url,data,"POST","","getLocationBill","","");
            }
            
            function showAddModal(){
                $("#addItem").click(function(){
                    var url = ""+base+"/TransactionCashierHandler";
                    var location = $("#locationBillIdSource").val();
                    var data = "loadtype=loaditemlist&LOCATION_ID="+location+"&FRM_FIELD_ITEM_NAME="+"&oidBillMain=<%=billMain.getOID() %>";
                    //alert(data);
                    ajaxTransactionHandler(url,data,"POST","#CONTENT_ITEM","showAddModal",true,"");
                });
            }
            
            function searchitemlocation(){
                $("#searchitemlocation").click(function(){
                    var url = ""+base+"/TransactionCashierHandler"; 
                    var location = $("#locationBillIdSource").val();
                    var cashCashier = $("#cashCashierId").val();
                    var data = "loadtype=loaditemlist&LOCATION_ID="+location+"&FRM_FIELD_ITEM_NAME=&CASH_CASHIER_ID="+cashCashier+"&oidBillMain=<%=billMain.getOID() %>";
                    var search = $('#searchItemModal').val();
                    data = data + "&search="+search+"";
                    ajaxTransactionHandler(url,data,"POST","#CONTENT_ITEM","loadItemListPage","","");
                });
            }
            
            function loadDataPaging(){
                $('.pagings').click(function(){
                    var showPage=0;
                    var type = $(this).data('type');
                    var curentPage = $("#paggingPlace").val();
                    var total = $("#totalPaging").val();
                    
                    if (type==="first"){
                        showPage = 1;
                    }else if (type==="prev"){
                        showPage = Number(curentPage)-1;
                    }else if (type==="next"){
                        showPage = Number(curentPage)+1;
                    }else if (type=="last"){
                        showPage = total;
                    }
                    
                    var location = $("#locationBillIdSource").val();
                    var data = "loadtype=loaditemlist&LOCATION_ID="+location+"&FRM_FIELD_ITEM_NAME=";
                    var search = $('#searchItemModal').val();
                    data = data + "&showPage="+showPage+"&search="+search+"";
                    
                    var url = ""+base+"/TransactionCashierHandler";                
                    ajaxTransactionHandler(url,data,"POST","#CONTENT_ITEM","loadItemListPage","","");
                    
                });
            }
            
            function clickItemList(){
                $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").click(function(){
			var itemName = $(this).data("name");
			var itemOid = $(this).data("oid");
			var itemPrice = $(this).data("price");
                        if ( $( this ).hasClass( "specialOrder" ) ) {
                            var billMainId = "<%=oidBillMain%>";
                            var url = ""+base+"/TransactionCashierHandler";
                            var data = "loadtype=edititem2&command=<%=Command.NONE%>&oidBillMain="+billMainId+"&oidMaterial="+itemOid+"&materialName="+itemName+"&materialPrice="+itemPrice+"&qty=1";                             
                            ajaxTransactionHandler(url,data,"POST","#openauthorize #CONTENT_AUTHORIZE","editItem","","");
                        }else{
                            $("#openadditem").modal("show");
                            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").val('');
                            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                            $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(itemOid);
                            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(itemName);
                            $("#CONTENT_ITEM_TITLE").html(itemName);
                        }
		    });
            }
            
            function saveItemAdd(){
                $("#btnsaveitem").click(function(){
                    var after = "saveItemAddProc";
                    var message = "Are you sure to add new item the bill ?";
                    showConfirmation(message,after); 
                });
            }
            
            function saveItemAddProc(){
                var qty = $("#FRM_FIELD_QTY").val();
                    if (qty>0){
                        $("#btnsaveitem").html("Please wait").attr({"disabled":true});
                        var billMainId = $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_MAIN_ID] %>_ADD_ITEM").val();
                        var url = ""+base+"/TransactionCashierHandler";
                        var data = $("#<%= FrmBillDetail.FRM_NAME_BILL_DETAIL %>_ADD_ITEM").serialize();
                        data = data +  "&fromEditable=1";
                        ajaxTransactionHandler(url,data,"POST","","saveItemAdd","","");
                    }
            }
            
            function viewDetailEditor(){
                $('#btnDetailEditor').click(function(){
                    var oidBillMain = $('#FRM_NAME_BILL_DETAIL_ADD_ITEM #FRM_FIELD_CASH_BILL_MAIN_ID_ADD_ITEM').val();
                    var oidMaterial = $('#FRM_NAME_BILL_DETAIL_ADD_ITEM #FRM_FIELD_MATERIAL_ID').val();
                    var materialName = $('#FRM_NAME_BILL_DETAIL_ADD_ITEM #FRM_FIELD_ITEM_NAME').val();
                    var materialPrice = $('#FRM_NAME_BILL_DETAIL_ADD_ITEM #FRM_FIELD_ITEM_PRICE').val();
                    var qty = $('#FRM_NAME_BILL_DETAIL_ADD_ITEM #FRM_FIELD_QTY').val();
                    var url = ""+base+"/TransactionCashierHandler";
                    var typeText = $('#searchCtCustomer').val();
                    var member = $("#member").val();
                    
                    var data = "loadtype=edititem2&command=<%=Command.NONE%>&oidBillMain="+oidBillMain+"&oidMaterial="+oidMaterial+"&materialName="+materialName+"&materialPrice="+materialPrice+"&qty="+qty+"";
                    
                    ajaxTransactionHandler(url,data,"POST","#openauthorize #CONTENT_AUTHORIZE","editItem","","");
                });
            }
            
            function saveItemDetail(){
                $("#btnSaveBillDetail").click(function(){
                    var after = "saveItemDetailProc";
                    var message = "Are you sure to add new item to the bill ?";
                    showConfirmation(message,after); 
                });
            }
            
            function saveItemDetailProc(){
                var url = ""+base+"/TransactionCashierHandler";
                var data = $("#FRM_NAME_BILL_DETAIL").serialize();
                data = data + "&fromEditable=1";
                //alert(data);
                ajaxTransactionHandler(url,data,"POST","","saveItemAddDetail","","");
            }
            
            var paymentItemId="";
            
            function deletePaymentItem(){
                $('.deletepaymentitem').click(function(){
                    paymentItemId= $(this).data('oid');
                    var after = "deletePaymentItemProc";
                    var message = "Are you sure to delete the payment ?";
                    showConfirmation(message,after); 
                    
                });
            }
            
            function deletePaymentItemProc(){
                var paymentOid= paymentItemId;
                var billMainId = "<%=oidBillMain%>";
                var url = ""+base+"/CashierMaintenance";
                var data = "command=<%=Command.SAVE%>&loadType=deleteItemPayment&paymentOid="+paymentOid+"&billMainId="+billMainId+"";
                //alert(data);
                ajaxMaintenanceTransaction(url,data,"POST","","deletePaymentProccess","","");
            }
            function addItemPayemnt(){
                $("#addItemPayment").click(function(){
                    $(".payTypes").val("");
                    $(".dynamicMultiSubPayment").html("");
                    $(".contentMultiSubPayment").html("");
                    var totalPayment = $("#totalsPayment").val();
                    //alert(totalPayment);
                    var totalTrans=$("#totalTransactionTemp").val();
                    //alert(totalTrans);
                    var remainMulti = Number(totalTrans)-Number(totalPayment);
                    //alert(remainMulti);
                    $("#remains").val(remainMulti);
                    $("#addMultiPayment").modal("show");
                });
            }
            
            function payTypes(){
                $('.payTypes').change(function(){
                    $(".contentMultiSubPayment").html("");
                    var url = ""+base+"/TransactionCashierHandler";
                    var totalPayment = $("#totalsPayment").val();
                    var totalTrans=$("#totalTransactionTemp").val();
                    var remainMulti = totalTrans-Number(totalPayment);
                    var oidMultiPayment = $("#MASTER_FRM_FIELD_PAY_TYPE_MULTI").val();
                    var data = "command=<%=Command.NONE%>&loadtype=loadsubpaymentmulti&remainMulti="+remainMulti+"&oidMultiPayment="+oidMultiPayment+"";
                  
                    ajaxTransactionHandler(url,data,"POST",".dynamicMultiSubPayment","payTypes","","");
                });
            }
            
            function payTypes2(){
                $('.payTypes2').change(function(){
                    var url = ""+base+"/TransactionCashierHandler";
                    var totalPayment = $("#totalsPayment").val();
                    var totalTrans=$("#totalTransactionTemp").val();
                    var remainMulti = totalTrans-Number(totalPayment);
                    var oidMultiPayment = $(this).val();
                    var data = "command=<%=Command.NONE%>&loadtype=loadsubmultipayment&remainMulti="+remainMulti+"&oidMultiPayment="+oidMultiPayment+"";
                    //alert(data);
                    ajaxTransactionHandler(url,data,"POST",".contentMultiSubPayment","payTypes2","","");
                });
            }
            
            function calcMoney(){
                $(".calcMoney").keyup(function(){
                    var totalPayment = $("#totalsPayment").val();
                    var totalTrans=$("#totalTransactionTemp").val();
                    var remainMulti = totalTrans-Number(totalPayment);
                    var numberValue = 0;
                    var tempValue = $(this).val();
                    if (tempValue.length>0){
                        numberValue = tempValue;
                    }else {
                        numberValue = 0;
                    }
                    var newRemain = remainMulti- numberValue;
                    $("#calcAmount").val(newRemain);
                });
            }
            
            function saveAddItem(){
                $("#btnSaveAddMultiPayment").click(function(){
                    var after = "saveAddItemProc";
                    var message = "Are you sure to add new payment to the bill ?";
                    showConfirmation(message,after);
                });
            }
            
            function saveAddItemProc(){
                var url = ""+base+"/CashierMaintenance";
                var data = $("#multiPaymentFormAdd").serialize();
                //alert(data);
                var add = "&command=<%= Command.SAVE%>&loadType=saveAddItemProc";
                data = data + add;
                $("#btnSaveAddMultiPayment").attr("disabled","disabled");
                //alert(data);
                ajaxMaintenanceTransaction(url,data,"POST","","saveAddItemProc","","");
            }
            
            function editpaymentitem(){
                $('.editpaymentitem').click(function(){
                    var oidPayment = $(this).data('oid');
                    var oidBillMain = $(this).data('billmainid');
                    var url = ""+base+"/CashierMaintenance";
                    var data = "command=<%=Command.LOAD%>&oidPayment="+oidPayment+"&oidBillMain="+oidBillMain+"&loadType=loadEditPayment";
                    ajaxMaintenanceTransaction(url,data,"POST","#multiPaymentFormEdit","deletePaymentProccess","","");
                });
            }
            
            function editMasterTransaction(){
                $("#editTransaction").click(function(){
                    getListCashCashier();
                });
            }
            
            function getListCashCashier(){
                var cashCashierDate1 = $("#cashCashierDate1").val();
                var cashCashierDate2 = $("#cashCashierDate2").val();
                var locationId = $("#LOCATION_ID").val();
                
                var url = ""+base+"/CashierMaintenance";
                var data = "command=<%=Command.LIST%>&locationId="+locationId+"&cashCashierDate1="+cashCashierDate1+"&cashCashierDate2="+cashCashierDate2+"&loadType=loadListCashCashier";
                //alert(data);
                ajaxMaintenanceTransaction(url,data,"POST","#CONTENT_ITEM_CASH_CASHIER","getListCashCashier",true,"");
            }
            
            function datePicker2(){
                $(".datePicker2").change(function(){
                    var cashCashierDate1 = $("#cashCashierDate1").val();
                    var cashCashierDate2 = $("#cashCashierDate2").val();
                    var locationId = $("#LOCATION_ID").val();

                    var url = ""+base+"/CashierMaintenance";
                    var data = "command=<%=Command.LIST%>&locationId="+locationId+"&cashCashierDate1="+cashCashierDate1+"&cashCashierDate2="+cashCashierDate2+"&loadType=loadListCashCashier";
                    //alert(data);
                    ajaxMaintenanceTransaction(url,data,"POST","#CONTENT_ITEM_CASH_CASHIER","getListCashCashier",true,"");
                });
            }
            
            function loadDataPagings2(){
                $('.pagings2').click(function(){
                    var showPage=0;
                    var type = $(this).data('type');
                    var curentPage = $("#paggingPlaces").val();
                    var total = $("#totalPagings").val();
                    
                    if (type==="first"){
                        showPage = 1;
                    }else if (type==="prev"){
                        showPage = Number(curentPage)-1;
                    }else if (type==="next"){
                        showPage = Number(curentPage)+1;
                    }else if (type=="last"){
                        showPage = total;
                    }
                    
                    var cashCashierDate1 = $("#cashCashierDate1").val();
                    var cashCashierDate2 = $("#cashCashierDate2").val();
                    var locationId = $("#LOCATION_ID").val();

                    var url = ""+base+"/CashierMaintenance";
                    var data = "command=<%=Command.LIST%>&locationId="+locationId+"&cashCashierDate1="+cashCashierDate1+"&cashCashierDate2="+cashCashierDate2+"&loadType=loadListCashCashier&showPage="+showPage+"";
                    //alert(data);
                    ajaxMaintenanceTransaction(url,data,"POST","#CONTENT_ITEM_CASH_CASHIER","getListCashCashier",false,"");
                    
                    
                });
            }
            
            var cashCashierId=0;
            function clickCashCashier(){
                $('.clickCashCashier').click(function(){
                    cashCashierId = $(this).data("cashoid");
                    var after = "clickCashCashierProccess";
                    var message = "Are you sure to change Cashier Cash for this bill?";
                    showConfirmation(message,after);
                });
            }
            
            function clickCashCashierProccess(){
                var oidCashCashier = cashCashierId;
                var oidBillMain = "<%=oidBillMain%>";             
                var url = ""+base+"/CashierMaintenance";
                var data = "command=<%=Command.SAVE%>&loadType=cashCashierEdit&oidCashCashier="+oidCashCashier+"&oidBillMain="+oidBillMain+"";
                //alert(data);
                ajaxMaintenanceTransaction(url,data,"POST","","cashCashierEdit","","");
            }
            
            function saveHistory(commandHistory,description){
                var url = ""+base+"/CashierMaintenance";
                var oidBillMain = "<%=oidBillMain%>"; 
                var data = "command=<%=Command.SAVE%>&loadType=saveHistory&commandHistory="+commandHistory+"&oidBillMain="+oidBillMain+"&des="+description+"";
                ajaxMaintenanceTransaction(url,data,"POST","","saveHistory","","");
            }
            
            function deleteTransaction(){
                $("#deleteTransaction").click(function(){
                    var after = "deleteTransactionProc";
                    var message = "Are you sure to delete the bill ?";
                    showConfirmation(message,after);
                });
            }
            
            function deleteTransactionProc(){
                var oidDelete = "<%= oidBillMain%>";
                var url = ""+base+"/CashierMaintenance";
                var loadType = "deleteTransaction";              
                var data = "command=<%=Command.SAVE%>&oid="+oidDelete+"&loadType="+loadType+"";
                ajaxMaintenanceTransaction(url,data,"POST","","deleteTransaction","","");
            }
            
            function textual(){
                $('.textual').keydown(function(e){
                    if (e.keyCode==40){
                        $(this).parent().parent().next('tr').find('input').focus();  
                    }else if (e.keyCode==38){  
                        if ($(this).hasClass('firstFocus')){
                            $('#openitemlist #searchItemModal').focus();
                        }else{
                            $(this).parent().parent().prev('tr').find('input').focus();
                        }
                        
                    }else if (e.keyCode==13){
                        $(this).trigger('click');
                    }else if (e.keyCode==37){
                        idInputFocus = $(this).attr("id");
                        loadDataPagingEx("prev");
                    }else if (e.keyCode==39){
                        idInputFocus = $(this).attr("id");
                        loadDataPagingEx("next");
                    }
                });
            }
            
            function searchItemModal(){
                $('#searchItemModal').keydown(function(e){
                    if (e.keyCode==40){ 
                        $('#openitemlist .firstFocus').focus();
                    }else if (e.keyCode==37){
                        //left                       
                        loadDataPagingEx("prev");
                    }else if (e.keyCode==39){                       
                        //right
                        loadDataPagingEx("next");
                        //alert('testke');
                        
                    }else if (e.keyCode==13){
                        
                        $("#searchitemlocation").trigger("click");
                        e.preventDefault();
                    }
                });
            }
            
            var dataSend;
            var command;
            var dataFor;
            var approot = "<%= approot%>";
            
            
            var modalSetting = function(elementId, backdrop, keyboard, show){
		$(elementId).modal({
		    backdrop	: backdrop,
		    keyboard	: keyboard,
		    show	: show
		});
	    };
            
            var getDataFunction = function(onDone, onSuccess, approot, command, dataSend, servletName, dataAppendTo, notification, dataType){		
		$(this).getData({
		   onDone	: function(data){
		       onDone(data);
		   },
		   onSuccess	: function(data){
			onSuccess(data);
		   },
		   approot	: approot,
		   dataSend	: dataSend,
		   servletName	: servletName,
		   dataAppendTo	: dataAppendTo,
		   notification : notification,
		   ajaxDataType	: dataType
		});
	    };
            
            var searchVoucher = function(elementId){
                $(elementId).click(function(){
                    //alert("test");
                    var source = $(this).data("source");
                    $("#sourceSelectVoucher").val(source);
                    loadVoucher(true,0);  
                });
            };
            
            var loadVoucher=function(showModal,showPage){
                var dataFor = "searchVoucherRegular";
                command = "<%= Command.LIST%>";
                var typeText = $("#searchVoucherInput").val();
                var oidBillMain = $("#mainBillIdSource").val();
                dataSend = {
                    "FRM_FIELD_DATA_FOR"          : dataFor,
                    "FRM_FIELD_SHOW_PAGE"         : showPage,
                    "command"		      : command,
                    "FRM_FIELD_BILL_MAIN"         : oidBillMain,
                    "FRM_FIELD_TYPE_TEXT"         : typeText
                };
                onSuccess = function(data){

                };

                onDone = function(data){
                    loadDataPagingVc(".pagingVc");
                    voucherSelect(".selectVoucherClick");
                };
                if (showModal==true){
                    $("#modalVoucher").modal("show");
                }

                getDataFunction(onDone, onSuccess, approot, command, dataSend, "CashierPayment", "#modal-body-voucher", true, "json");
            };
            
            var  loadDataPagingVc = function(elementId){
                $(elementId).click(function(){
                    var showPage=0;                   
                    var curentPage = $("#paggingPlaceVc").val();
                    var total = $("#totalPagingVc").val();
                    var type = $(this).data("type");
                    
                    if (type==="first"){
                        showPage = 1;
                    }else if (type==="prev"){
                        showPage = Number(curentPage)-1;
                    }else if (type==="next"){
                        showPage = Number(curentPage)+1;
                    }else if (type=="last"){
                        showPage = total;
                    }
                    
                    loadVoucher(false,showPage);
                });
  
            };
            
            var voucherSelect = function(elementId){
                $(elementId).click(function(){
                    if (confirm('Are you sure to choose this voucher?')) {
                        //yes!
                        var voucherNo = $(this).data("voucherno");
                        var voucherId = $(this).data("voucherid");
                        var nominal = $(this).data("nominal");
                        var source = $("#sourceSelectVoucher").val();
                        
                        if (source=="1"){
                            $("#voucherCodeInput").val(voucherNo);
                            $("#VOUCHER_ID").val(voucherId);
                            $("#<%=FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]%>").val(nominal);
                        }else if (source=="2"){
                            $("#voucherCodeInput2").val(voucherNo);
                            $("#FRM_FIELD_VOUCHER_ID_MULTI").val(voucherId);
                            $("#FRM_FIELD_PAY_AMOUNT_MULTI").val(nominal);
                        }

                        $("#modalVoucher").modal("hide");
                    } 
                });
            };
            
            //--------------------------------------
            
            $("#confirmationOk").click(function(){
                var after = $("#modalActAfter").val();
                $(this).attr("disabled","disabled");
                if (after.length>0){
                    if (after=="saveEdit"){
                        saveEdit();
                    }else if (after=="deleteProccess"){
                        deleteProccess();
                    }else if (after=="saveItemAddProc"){
                        saveItemAddProc();
                    }else if (after=="saveItemDetailProc"){
                        saveItemDetailProc();
                    }else if (after=="deletePaymentItemProc"){
                        deletePaymentItemProc();
                    }else if (after=="saveAddItemProc"){
                        saveAddItemProc();
                    }else if (after=="clickCashCashierProccess"){
                        clickCashCashierProccess();
                    }else if (after=="deleteTransactionProc"){
                        deleteTransactionProc();
                    }
                }
            });
            
            
            
            $(".datePicker2").datepicker({
                format: 'yyyy-mm-dd',
            }).on("changeDate", function (ev) {
                $(this).datepicker('hide');
            });
            
            $("form.additem").submit(function(){
                $("#searchitemlocation").trigger("click");
		return false;
	    });
            
            $("form#FRM_NAME_BILL_DETAIL_ADD_ITEM").submit(function(){
                $("#btnsaveitem").trigger("click");
                return false;
            });
            
            $("#openadditem #FRM_FIELD_QTY").keydown(function(e){
                if (e.keyCode==123){ 
                    $('#openadditem #btnDetailEditor').trigger("click");
                }
            });
            
            $(".modal").on('shown.bs.modal', function() {
                var id = $(this).attr('id');
                if (id=="openitemlist"){
                    $("#searchItemModal").focus();
                }else if (id=="openadditem"){
                    $("#openadditem #FRM_FIELD_QTY").focus();
                }else if (id=="openauthorize"){
                    $("#openauthorize #FRM_FIELD_QTY2").focus();
                }else if (id=="confirmationModal"){
                    setTimeout(function(){
                         $("#confirmationModal #confirmationOk").focus();
                    }, 200);
                   
                }
                
            });
            
            $('.modal').on('hidden.bs.modal', function () {
                var id = $(this).attr('id');
                if (id=="openadditem"){                   
                    setTimeout(function(){
                        $('#searchItemModal').focus();
                    }, 200);
                }

            });
            
            datePicker2();
            saveAddItem();
            payTypes();
            viewDetailEditor();
            saveItemAdd();
            loadData();
            loadDataPayment();
            getLocationByCashBillMain();
            editMasterTransaction();
            deleteTransaction();
            searchItemModal();
    
        });
    </script>
    <!-- PLUGINS COMPONEN -->
    <%@include file="cashier-plugin.jsp" %>
    <div id="editItemModal" class="modal nonprint">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="headTitle"><b>EDIT ITEM</b></h4>
                </div>
                <div class="modal-body">
                    <div  id="dynamicContentEdit">
		  
                    </div>
	      
                </div>
                <div class="modal-footer">
                    
                </div>
            </div>
        </div>
    </div>
    <!--LOAD ITEM-->
    <div id="openitemlist" class="modal nonprint" style="overflow-y: scroll;">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><b>ITEM LIST</b></h4>
                <form method="POST"  class="additem">
		<%
		    if(multilocation.equals("0")){
		%>
                    <input type="hidden" name="LOCATION_ID" id="LOCATION_ID" value="<%= defaultOidLocation %>">
		<%
		    }else{
			Vector location_key = new Vector(1,1);
			Vector location_value = new Vector(1,1);

			Vector listLocation = PstQueensLocation.list(0, 0, 
				"", "");
			if(listLocation.size() != 0){
			    for(int i = 0; i<listLocation.size(); i++){
				QueensLocation queensLocation = (QueensLocation) listLocation.get(i);
				location_key.add(""+queensLocation.getOID());
				location_value.add(""+queensLocation.getName());
			    }
			}
		%>
		<%= ControlCombo.drawBootsratap("LOCATION_ID", "Select Location", ""+location.getOID(), location_key, location_value, "required='required'", "form-control LOCATION_ID") %>
		<%
		    }
		%>
		
		<div class="input-group">
                    <input type="hidden" name="oid" value="<%=oidBillMain%>">
		    <input type="hidden" name="loadtype" value="loaditemlist">
		    <input id="searchItemModal" type="text" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>" class="form-control itemsearch" placeholder="Scan or Search Items" readonly="true"/>
		    <div class="input-group-addon" style="padding:0px;border:none;">
                        <button type="button" id="searchitemlocation" class="btn btn-primary input-group-addon itemsearchbutton"  style="padding-bottom: 9px;padding-top: 9px;padding-right: 25px;border-right: 1px solid #D2D6DE" disabled="true">
			    <i class="fa fa-search"></i>
			</button>
		    </div>
		    
		</div>
                </form>
                </div>
                <div class="modal-body" id="CONTENT_ITEM">
                </div>
                <div class="modal-footer">               
                </div>
	  
            </div>
      </div>
    </div>
    <!-- OPEN ADD ITEM -->
    <div id="openadditem" class="modal nonprint">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title"><b>ADD ITEM</b> - <b id="CONTENT_ITEM_TITLE"></b></h4>
                </div>
                <form id="<%= FrmBillDetail.FRM_NAME_BILL_DETAIL %>_ADD_ITEM">
                <div class="modal-body">		   
                    <div class="row">
                        <div class="box-body">
                            <div class='col-md-12'>
                                <input type="hidden" name="loadtype" value="additem">                                    
                                <input type="hidden" name="command" value="<%= Command.SAVE %>">
                                <input type='hidden' value="<%=oidBillMain%>" name='<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_MAIN_ID] %>' id='<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_MAIN_ID] %>_ADD_ITEM'>
                                <input type="hidden" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>" id="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>">
                                <input type="hidden" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>" id="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>">
                                <input type="hidden" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>" id="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>">
                                <div class='input-group'> 
                                    <input type="number" class="form-control" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>" id="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>" placeholder="Quantity" required="required">
                                    <div class='input-group-addon btn btn-primary' id='btnDetailEditor'>
                                        <i class='fa fa-cog'></i> F12
                                    </div> 
                                </div>                  
                            </div>
                        </div>
                    </div>		
		</div>
		<div class="modal-footer">
		    <button type="button" class="btn btn-primary form-control" id="btnsaveitem">
			<i class="fa fa-plus"></i> Add Item
		    </button>
		</div>
                </form>
            </div>
        </div>
    </div>
    <!-- /. OPEN ADD ITEM --> 
    <div id="openauthorize" class="modal nonprint">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="AUTHORIZE_TITLE"><b>SUPERVISOR AUTHORIZE</b></h4>
                </div>
            <div class="modal-body">
                <div  id="CONTENT_AUTHORIZE"></div>	                      
            </div>
            <div class="modal-footer">
                <div id="cancelButton" >
                    <button class="btn btn-danger pull-left" type="button" data-dismiss="modal"><i class="fa fa-close"></i> Close</button>
                    <button class="btn btn-primary buttonCancel" type="button" id="btnMove" data-load-type="movebill"><i class="fa fa-share"></i> Move</button>
                </div>
            </div>
	</div>
      </div>
    </div>
    <div id="addMultiPayment" class="modal nonprint">
      <div class="modal-dialog modal-lg" style="width: 700px;">
	<div class="modal-content">
	  <div class="modal-header">           
            <h4 class="modal-title"><b>ADD PAYMENT</b></h4> 
          </div>	  
          <form id="multiPaymentFormAdd">
              <input type="hidden" id="remains" name="remains">
              <input type="hidden" id="multiPayCashBillMainId2" value="<%=oidBillMain%>" name="multiPayCashBillMainId">
            <div class="modal-body" id="addMultiPaymentBody">
                <div class="row">
                    <div class="col-md-3">Transaction Type</div>
                    <input type="hidden" name="multiTransactionType" id="multiTransactionType" value="0">
                    <div class="col-md-9"><input type="text" class="form-control" readonly value="CASH"></div>
                </div>
                <div class="row" style="margin-top:7px;">
                    <div class="form-group">
                    <%
                        Vector paymentType_key = new Vector(1,1);
			Vector paymentType_value = new Vector(1,1);

			paymentType_key.add("");
			paymentType_value.add("-- Select --");
                        paymentType_key.add(""+ PstCustomBillMain.PAYMENT_TYPE_CASH);
                        paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CASH]);
                        paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD);
                        paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD]);
                        paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_CHECK);
                        paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CHECK] + " OR " + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_BG]);
                        paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD);
                        paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD]);
                        paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR);
                        paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR]);
                        paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT);
                        paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT]);
                    %>
                    
                    <div class="col-md-3">Payment Type</div>                   
                    <div class="col-md-9"><%=ControlCombo.drawBootsratap("MASTER_"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE]+"_MULTI", null, "", paymentType_key, paymentType_value, "required='true'", "form-control payTypes")%></div>
                    </div>
                </div>
                <div class="row" style="margin-top:7px;">
                    <div class="dynamicMultiSubPayment"></div>
                </div>
                <div class="row" style="margin-top:7px;">
                    <div class="contentMultiSubPayment"></div>
                </div>
            </div>
          </form>
	  <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="btnSaveAddMultiPayment"><i class="fa fa-check"></i> Save</button>                 
                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
	  </div>
	</div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div>
    <!-- END OF MODAL MULTI PAYMENT -->
    <!-- EDIT PAYMENT MODAL -->
    <div id="editMultiPayment" class="modal nonprint">
      <div class="modal-dialog modal-lg" style="width: 700px;">
	<div class="modal-content">
	  <div class="modal-header">           
            <h4 class="modal-title"><b>EDIT PAYMENT</b></h4> 
          </div>	  
          <form id="multiPaymentFormEdit">
              
          </form>
	  <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="btnSaveAddMultiPayment"><i class="fa fa-check"></i> Save</button>                 
                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
	  </div>
	</div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div>
    <!-- END OF EDIT  Payment-->
    <!-- MODAL CASH CASHIER -->
    <div id="cashcashierlist" class="modal nonprint" style="overflow-y: scroll;">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><b>CASH CASHIER LIST</b></h4>
                <form class="cashcashierfilter">
                <div class="row">
                    <div class="col-md-12">
                        <%
                            if(multilocation.equals("0")){
                        %>
                            <input type="hidden" name="LOCATION_ID" id="LOCATION_ID" value="<%= defaultOidLocation %>">
                        <%
                            }else{
                                Vector location_key = new Vector(1,1);
                                Vector location_value = new Vector(1,1);

                                Vector listLocation = PstQueensLocation.list(0, 0, 
                                        "", "");
                                if(listLocation.size() != 0){
                                    for(int i = 0; i<listLocation.size(); i++){
                                        QueensLocation queensLocation = (QueensLocation) listLocation.get(i);
                                        location_key.add(""+queensLocation.getOID());
                                        location_value.add(""+queensLocation.getName());
                                    }
                                }
                        %>
                        <%= ControlCombo.drawBootsratap("LOCATION_ID", "Select Location", ""+location.getOID(), location_key, location_value, "required='required'", "form-control LOCATION_ID") %>
                        <%
                            }
                        %>
                    </div>
                </div>
                <div class="row" style="margin-top:5px;">
                    <div class="col-md-12">
                        <div class="input-group">
                            <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                            <input id="cashCashierDate1" class="form-control datePicker2" required="" type="text">
                                <div class="input-group-addon" style="border:none;"><i class="fa fa-minus"></i></div>
                            <div class="input-group-addon" style="border-right:none;"><i class="fa fa-calendar"></i></div>
                            <input id="cashCashierDate2" class="form-control datePicker2" required="" type="text">
                        </div>
                    </div>
                </div>

                </form>
                </div>
                <div class="modal-body" id="CONTENT_ITEM_CASH_CASHIER">
                </div>
                <div class="modal-footer">               
                </div>
	  
            </div>
      </div>
    </div>
    <!-- END OF MODAL CASH CASHIER --> 
    <!-- CONFIRMATION MODAL -->
    <div id="confirmationModal" class="modal nonprint">
        <input type="hidden" id="modalActAfter" name="modalActAfter">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="AUTHORIZE_TITLE"><b>Confirmation</b></h4>
                </div>
                <div class="modal-body" id="messageContent" >

                </div>
                <div class="modal-footer">
                    <div id="cancelButton">
                        <button id="confirmationOk" class="btn btn-primary" type="button" data-dismiss="modal"><i class="fa fa-check"></i> Yes</button>
                        <button class="btn btn-danger" type="button" data-dismiss="modal"><i class="fa fa-close"></i> No</button>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div id="modalVoucher" class="modal nonprint">
      <div class="modal-dialog">
	<div class="modal-content">
	  <div class="modal-header">	    
	    <h4 class="modal-title">Voucher List</h4>
	  </div>
	  <div class="modal-body" >
              <div class="row">
                  <div class="col-md-12">
                    <div class="input-group ">
                        <input type="hidden" name ="sourceSelectVoucher" id="sourceSelectVoucher">
                        <input class="form-control" name="searchVoucherInput" id="searchVoucherInput" type="text">
                        <span class="input-group-btn">
                            <button class="btn btn-info btn-flat" id="searchVoucherButton" type="button"><i class='fa fa-search'></i> &nbsp;</button>
                        </span>
                    </div>
                  </div>
              </div>
              <div class="row" style="margin-top:10px;">
                  <div class="col-md-12" id="modal-body-voucher"></div>
              </div>
	  </div>
	  <div class="modal-footer">
	    <button type="button" data-dismiss="modal" class="btn btn-danger pull-left"><i class="fa fa-close"></i> Close</button>
	  </div>
	</div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div>
    
    
    
  </body>
</html>
