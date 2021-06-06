<%@page import="com.dimata.pos.form.payment.FrmCashPayment"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.BillMainJSON"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.Room"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.PstRoom"%>
<%@page import="com.dimata.common.entity.location.Negara"%>
<%@page import="com.dimata.common.entity.location.PstNegara"%>
<%@page import="com.dimata.common.form.contact.FrmContactClass"%>
<%@page import="com.dimata.pos.form.billing.FrmBillMain"%>
<%@page import="com.dimata.common.form.contact.FrmContactList"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.QueensLocation"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.PstQueensLocation"%>
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
    
    //System Property untuk mengecek, apakah menggunakan log out otomatis atau tidak
    String logOutAuto = PstSystemProperty.getValueByName("LOGOUT_OUTOMATIC");
    
    //System Property untuk mengecek, iddle time yang digunakan
    String iddleTime = PstSystemProperty.getValueByName("LOGOUT_OUTOMATIC_IDDLE_LIMIT");
%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Transaction Maintenance - Dimata Cashier</title>
    <%@include  file="cashier-css.jsp"%>   
    <script type="text/javascript" src="../styles/cashier/dist/js/numberformat.js"></script>
</head>
<body class="skin-queentandoor sidebar-mini wysihtml5-supported sidebar-collapse fixed">
    <div class="wrapper"> 
        <header class="main-header">
            <%@include file="cashier-header.jsp" %>
        </header>
    <aside class="main-sidebar">
        <%
            String home = "";
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
                <div class="col-md-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Transaction Maintenance</h3>
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="input-group" style="margin-bottom: 2px;">
                                        <%
                                            String whereClause = "";                                           
                                            Vector listLocation = PstLocation.listLocationStore(0, 0,""+whereClause, "");
                                            out.println("<select id='locationStore' class='form-control'>");
                                            out.println("<option value='0'>All Location</option>");
                                            if(listLocation.size() != 0){
                                                for(int i=0; i<listLocation.size(); i++){
                                                    Location location = (Location) listLocation.get(i);
                                                    out.println("<option value='"+location.getOID()+"'>"+location.getName()+"</option>");

                                                }
                                            }
                                            out.println("</select>");
                                            out.println("<div class='input-group-addon' style='border:none;'>&nbsp;</div>");
                                        %>

                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                        <input id="orSearch1" class="form-control formsearch datePicker" required="" type="text" placeholder="Start Date">
                                        <div class="input-group-addon " style="border:none;"><i class="fa fa-minus"></i></div>
                                        <div class="input-group-addon" style="border-right:none;"><i class="fa fa-calendar"></i></div>
                                        <input id="orSearch2" class="form-control datePicker formsearch" required="" type="text" placeholder="End Date">
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <input type="text" class="form-control formsearch" placeholder="Invoice Number" id="noInvoice"> 
                                </div>
                                <div class="col-md-3">
                                    <button id="search" type="button" class="btn btn-primary">
                                        <i class="fa fa-search"></i>
                                        Search
                                    </button>
                                    <button id="newTransaction" type="button" class="btn btn-success">
                                        <i class="fa fa-plus-circle"></i>
                                        New Transaction
                                    </button>
                                </div>
                            </div>
                            <div class="row" style="margin-top:20px;">
                                <div class="col-md-12">
                                    <div id="dynamicContent"></div>
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>   
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
                        editTransaction();
                        loadDataPaging();
                        deleteTransaction();
                        cancelTransaction();
                        openTransaction();
                    }else if (another=="getListCashCashier"){
                        loadDataPagings2();
                        clickCashCashier();
                    }else if (another=="deleteTransaction"){
                        $("#confirmationOk").removeAttr("disabled");
                        loadData();
                        $("#confirmationModal").modal("hide");
                    }else if (another=="cancelTransaction"){
                        $("#confirmationModal").modal("hide");
                        $("#confirmationOk").removeAttr("disabled");
                        loadData();
                    }else if (another=="openTransaction"){
                        $("#confirmationOk").removeAttr("disabled");
                        loadData();
                        $("#confirmationModal").modal("hide");
                    }
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
    
                    },
                    error : function(data){
                        alert("error");
                    }
                }).done(function(data){
                    if (another=="newOrder"){
                        var oid = data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>;
                        $("#confirmationOk").removeAttr("disabled");
                       
                        if (oid!=0){
                            window.location = "<%=approot%>/cashier/cashier-maintenance-edit.jsp?oid="+oid+"";
                        }
                       
                    }
                });
            }
            
            function loadData(){
                var url = ""+base+"/CashierMaintenance";
                var loadType = "listTransaction";
                var startDate = $("#orSearch1").val();
                var endDate = $("#orSearch2").val();
                var oidLocation = $("#locationStore").val();
                var noInvoice = $("#noInvoice").val();
                var data = "command=<%=Command.LIST%>&loadType="+loadType+"&startDate="+startDate+"&endDate="+endDate+"&oidLocation="+oidLocation+"&noInvoice="+noInvoice+"";
                ajaxMaintenanceTransaction(url,data,"POST","#dynamicContent","loadData","","");               
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
                    
                    
                    var url = ""+base+"/CashierMaintenance";
                    var loadType = "listTransaction";
                    var startDate = $("#orSearch1").val();
                    var endDate = $("#orSearch2").val();
                    var oidLocation = $("#locationStore").val();
                    var noInvoice = $("#noInvoice").val();
                    var data = "command=<%=Command.LIST%>&loadType="+loadType+"&startDate="+startDate+"&endDate="+endDate+"&oidLocation="+oidLocation+"&noInvoice="+noInvoice+"&showPage="+showPage+"";
                    ajaxMaintenanceTransaction(url,data,"POST","#dynamicContent","loadData","",""); 
                    
                });
            }

            function editTransaction(){
                $('.editTransaction').click(function(){
                    var oid = $(this).data('oid');
                    window.location = ""+base+"/cashier/cashier-maintenance-edit.jsp?oid="+oid+"";
                });
            }
            
            var oidDelete=0;
            
            function deleteTransaction(){
                $('.deleteTransaction').click(function(){
                    var oid = $(this).data('oid');
                    oidDelete = oid;
                    var after = "deleteTransactionProc";
                    var message = "Are you sure to delete the bill ?";
                    showConfirmation(message,after);
                });
            }
            
            function deleteTransactionProc(oidDelete){
                var url = ""+base+"/CashierMaintenance";
                var loadType = "deleteTransaction";              
                var data = "command=<%=Command.SAVE%>&oid="+oidDelete+"&loadType="+loadType+"";
                ajaxMaintenanceTransaction(url,data,"POST","","deleteTransaction","","");
            }
            
            var oidCancel = 0;
            function cancelTransaction(){
                $('.cancelTransaction').click(function(){
                    var oid = $(this).data('oid');
                    oidCancel = oid;
                    var after = "cancelTransactionProc";
                    var message = "Are you sure to cancel the bill ?";
                    showConfirmation(message,after);
                });
            }
            
            function cancelTransactionProc(oidCancel){
                var url = ""+base+"/CashierMaintenance";
                var loadType = "cancelTransaction";
                var data = "command=<%=Command.SAVE%>&BILL_MAIN_ID="+oidCancel+"&loadType="+loadType+"";
                ajaxMaintenanceTransaction(url,data,"POST","","cancelTransaction","","");
            }
            
            function showConfirmation(message,after){
                $("#modalActAfter").val(after);
                $("#messageContent").html(message);
                $("#confirmationModal").modal("show");
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
            
            loadData();
            
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
            
            var cashCashierId=0;
            var shiftId =0;
            function clickCashCashier(){
                $('.clickCashCashier').click(function(){
                    cashCashierId = $(this).data("cashoid");
                    shiftId = $(this).data("shift");
                    var after = "clickCashCashierProccess";
                    var message = "Are you sure to add a new transaction for this cash cashier?";
                    showConfirmation(message,after);
                });
            }
            
            function clickCashCashierProccess(cashCashierId,shiftId){
                $("#shiftId").val(shiftId);
                $("#cashCashierId").val(cashCashierId);
                //alert(shiftId);
                //alert(cashCashierId);
                //hilangkan atribut required 
                $("#openneworder #employee").removeAttr("required");
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>").removeAttr("required");
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID] %>").removeAttr("required");
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID] %>").removeAttr("required");
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_COUNTRY] %>").removeAttr("required");

                //trigger change 
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] %>").trigger('change');

                //submit form
                var data = $("#<%= FrmBillMain.FRM_NAME_BILL_MAIN %>_NEWORDER").serialize();
                var url = ""+base+"/TransactionCashierHandler";
                
                ajaxTransactionHandler(url,data,"POST","","newOrder","","");
                
            }
            
            function showConfirmation(message,after){
                $("#modalActAfter").val(after);
                $("#messageContent").html(message);
                $("#confirmationModal").modal("show");
            }
            
            function openTransaction (){
                $(".openTransaction").click(function(){
                    var oid = $(this).data('oid');
                    oidDelete = oid;
                    var after = "openTransactionProccess";
                    var message = "Are you sure to change bill status to open ?";
                    showConfirmation(message,after);
                });
            };
            
            function openTransactionProccess(){
                var url = ""+base+"/CashierMaintenance";
                var data = "command=<%=Command.SAVE%>&loadType=openTransaction&oid="+oidDelete+"";
                //alert(data);
                ajaxMaintenanceTransaction(url,data,"POST","","openTransaction",false,"");
            }
            
            //------------------------------------------
            
            
            $("#confirmationOk").click(function(){
                var after = $("#modalActAfter").val();
                $(this).attr("disabled","disabled");
                if (after.length>0){
                    if (after=="deleteTransactionProc"){
                        deleteTransactionProc(oidDelete);
                    }else if (after=="clickCashCashierProccess"){
                        clickCashCashierProccess(cashCashierId,shiftId);  
                    }else if (after=="openTransactionProccess"){
                        openTransactionProccess();
                    }else if (after=="cancelTransactionProc"){
                        cancelTransactionProc(oidCancel);
                    }
                }
            });
            
            $("#newTransaction").click(function(){
                getListCashCashier();
            });
            
            $(".formsearch").keyup(function(){
               loadData();
            });
            
            $(".formsearch").change(function(){
               loadData();
            });
            
            $('#search').click(function(){
                loadData();
            });
            
            $(".datePicker").datepicker({
                format: 'yyyy-mm-dd',
                todayHighlight : true
            }).on("changeDate", function (ev) {
                $(this).datepicker('hide');
            });
            
            $(".datePicker2").datepicker({
                format: 'yyyy-mm-dd'
            }).on("changeDate", function (ev) {
                $(this).datepicker('hide');
            });
            
           
            
            datePicker2();
            
            
            //----------AUTO LOG OUT FUNCTION
            
            var autoLog = "<%= logOutAuto%>";
            var autoLogTime = "<%= iddleTime%>";
            var timeLogOut = parseInt(autoLogTime)*1000;
            var autoFirstCounter = 30; 
            var intervallAutoLog;
            
            var iddleEvent = function(autoLogTime){
                //CHECK IDDLE OR NO
                var idleState = false;
                var idleTimer = null;
                var idleWait = autoLogTime;

                $('*').bind('mousemove keydown scroll', function () {       
                    clearTimeout(idleTimer);                                            
                    idleState = false;           
                    idleTimer = setTimeout(function () {                
                        // Idle Event               
                        $("#modalAutoLogOut").modal("show");
                        intervallAutoLog =  setInterval(function() {
                            checkCounter("#counterAuto");
                        },1000);
                    idleState = true; }, idleWait);
                });

                $("body").trigger("mousemove");
            };
            
            var checkCounter = function(elementChange){
                if (autoFirstCounter==1){
                    window.location = "<%= approot%>/cashier/cashier-logout.jsp";
                }else{
                    autoFirstCounter = autoFirstCounter-1;
                    $(elementChange).html(""+autoFirstCounter+"");
                }
                 
            };
            
            var logOutAutoButton = function(elementId){
                $(elementId).click(function(){
                    window.location = "<%= approot%>/cashier/cashier-logout.jsp";
                });
            };
            
            var keepLoginAuto = function(elementId){
                $(elementId).click(function(){
                    clearInterval(intervallAutoLog);
                    $("#modalAutoLogOut").modal("hide");
                    autoFirstCounter = 30;                  
                });
            };
             
            
            if (autoLog=="1"){
                iddleEvent(timeLogOut);
            }
            keepLoginAuto("#keepLoginAuto");
            logOutAutoButton("#logOutAuto");
            //----------AUTO LOG OUT FUNCTION
            
        });
    </script>
    <!-- PLUGINS COMPONEN -->
    <%@include file="cashier-plugin.jsp" %>
    
  </body>
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

                                Vector listLocations = PstQueensLocation.list(0, 0, 
                                        "", "");
                                if(listLocations.size() != 0){
                                    for(int i = 0; i<listLocations.size(); i++){
                                        QueensLocation queensLocation = (QueensLocation) listLocations.get(i);
                                        location_key.add(""+queensLocation.getOID());
                                        location_value.add(""+queensLocation.getName());
                                    }
                                }
                        %>
                        <%= ControlCombo.drawBootsratap("LOCATION_ID", "Select Location", "", location_key, location_value, "required='required'", "form-control LOCATION_ID") %>
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
    
    <!-- OPEN NEW ORDER -->
    <div id="openneworder" class="modal nonprint">
      <div class="modal-dialog">
	<div class="modal-content">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	    <h4 class="modal-title"><b>NEW ORDER</b></h4>
	  </div>
	    <form id="<%= FrmBillMain.FRM_NAME_BILL_MAIN %>_NEWORDER">
	  <div class="modal-body">
		
	      <div class="row">
		  <div class="box-body">
		      <div class="col-md-12">
			  <%
			      Vector location_key = new Vector(1,1);
			      Vector location_value = new Vector(1,1);

			      location_key.add("");
			      location_value.add(" -- Select --");

			      Vector listLocation2 = PstLocation.listLocationStore(0,0,"","");

			      if(listLocation2.size() != 0){
				  for(int i = 0; i<listLocation2.size(); i++){
				      Location location1 = (Location) listLocation2.get(i);
				      location_key.add(""+location1.getOID());
				      location_value.add(""+location1.getName());
				  }
			      }
			      
			      
			      if(multilocation.equals("0")){
			  %>
			    <input type="hidden" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] %>" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] %>" value="<%= ""+defaultOidLocation %>">
			  <%
			      }else{
			  %>
				<%= ControlCombo.drawBootsratap(FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID], null, "", location_key, location_value, "required='required'", "form-control") %>
			  <%
			      }
			  %>
			  
			  
		      </div>
		  </div>
	      </div>
	      <div class="row">
		  <div class="box-body">
		      <div class="col-md-12">
			  <div class="input-group">
			      <input type="text" class="form-control" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME] %>" placeholder="Guest Name"  readonly="readonly" id="employee" required='required'>
			      <input type="hidden" name="<%= FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE] %>" id="<%= FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE] %>" value="99">

                              <div class="btn btn-primary input-group-addon" id="searchallmemberbtn">
				  <i class="fa fa-search"></i>
			      </div>
			      <div class="btn btn-success input-group-addon" id="addnewmember">
				  <i class="fa fa-plus"></i>
			      </div>
			  </div>
			  <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] %>" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] %>">
		      </div>
		  </div>
	      </div>
	     <div class="row">
		 <div class="box-body">
		     <div class="col-md-12">
			 <input type="text" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_MOBILE_NUMBER] %>" placeholder="Phone Number" class="form-control" readonly="readonly" id="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE] %>">
		     </div>
		 </div>
	     </div>
	     <div class="row">
		 <div class="box-body">
		     <div class="col-md-12">
			 <%
			     Vector country_key = new Vector(1,1);
			     Vector country_value = new Vector(1,1);
			     
			     Vector listCountry = PstNegara.listAll();
			     if(listCountry.size() != 0){
				 for(int i = 0; i < listCountry.size(); i++){
				     Negara negara = (Negara) listCountry.get(i);
				     country_key.add(""+negara.getNmNegara());
				     country_value.add(""+negara.getNmNegara());
				 }
			     }
			 %>
			 <%= ControlCombo.drawBootsratap(FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_COUNTRY], null, "", country_key, country_value, "required='required'", "form-control") %>
		     </div>
		 </div>
	     </div>
	     <div class="row">
		 <div class="box-body">
		     <div class="col-md-12">
			 <input type="text" class="form-control" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>" placeholder="Pax Number" required='required'>
		     </div>
		 </div>
	     </div>
	    
	    <div class="row">
		<div class="box-body">
		    <div class="col-md-12">
			<%
			    Vector room_key = new Vector(1,1);
			    Vector room_value = new Vector(1,1);
			    
			    room_key.add("");
			    room_value.add("All Room");
			    Vector listRoom = PstRoom.list(0,0,""+PstRoom.fieldNames[PstRoom.FLD_LOCATION_ID]+"='"+defaultOidLocation+"'","");
			    if(listRoom.size() != 0){
				for(int i = 0; i<listRoom.size(); i++){
				    Room room = (Room) listRoom.get(i);
				    room_key.add(""+room.getOID());
				    room_value.add(""+room.getName());
				}
			    }
			   
			%>
			<!-- MAKE CONTROL COMBO HERE -->
			<%= ControlCombo.drawBootsratap(FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID], null, "", room_key, room_value, "required='required'", "form-control") %>
		    </div>
		</div>
	    </div>
	    <div class="row">
		<div class="box-body">
		    <div class="col-md-12">
			<select name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID] %>" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID] %>" class="form-control" required='required'>
			
			</select>
		    </div>
		</div>
	    </div>
	  </div>
	  <div class="modal-footer">
	      
	      <input type="hidden" id="shiftId" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIFT_ID] %>" value="">
	      <input type="hidden" id="cashCashierId" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID] %>" value="">
	      <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_APP_USER_ID] %>" value="<%= userId %>">
	      <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SALES_CODE] %>" value="<%= userName %>">
	      <input type="hidden" name="loadtype" value="neworder">
              <input type="hidden" name="edittrans" value="1">
	      <input type="hidden" name="command" value="<%= Command.SAVE %>">
	      <button type="submit" class="btn btn-primary form-control" id="btnneworder">
		  <i class="fa fa-check"></i> Save Order
	      </button>
	  </div>
	      
	</form>
	</div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div>
    
    <div id="modalAutoLogOut" class="modal nonprint">
      <div class="modal-dialog">
	<div class="modal-content">
	  <div class="modal-header">
	    
	    <h4 class="modal-title"><i class="fa fa-exclamation-triangle"></i> We detected no activity </h4>
	  </div>
	  <div class="modal-body">
              You will be logged out automatically in <b><span id="counterAuto">30</span></b> second (s) <br>
              Are you still continuing ?
	  </div>
	  <div class="modal-footer">
            <button type="button" id="keepLoginAuto" class="btn btn-default">Yes, Keep Login</button>
	    <button type="button" id="logOutAuto" class="btn btn-danger">No, Log Out</button>
	  </div>
	</div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div>
              
    
</html>
