<%-- 
    Document   : cashier-transaction-return
    Created on : Jul 25, 2016, 10:06:28 AM
    Author     : Witar
--%>
<%@page import="com.dimata.pos.form.billing.FrmBillMain"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.common.entity.location.PstLocation"%>
<%@page import="com.dimata.common.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.common.entity.location.Location"%>
<%@page import="com.dimata.pos.entity.balance.PstCashCashier"%>
<%@page import="com.dimata.pos.entity.balance.CashCashier"%>
<%@page import="com.dimata.posbo.entity.masterdata.Shift"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Vector"%>
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
<%
    CashCashier cashCashier = new CashCashier();
    Location location = new Location();
    Vector listBalance = new Vector(1, 1);
    Shift shift = new Shift();

    Vector listOpeningCashier = PstCashCashier.listCashOpening(0, 0,
            "CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID] + "='" + userId + "' "
            + "AND CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID] + "='1'", "");
%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Transaction Return - Dimata Cashier</title>
        <%@include  file="cashier-css.jsp"%>   
        <script type="text/javascript" src="../styles/cashier/dist/js/numberformat.js"></script>
    </head>
    <body class="skin-queentandoor sidebar-mini wysihtml5-supported sidebar-collapse fixed">
        <div class="wrapper nonprint"> 
            <header class="main-header">
                <%@include file="cashier-header.jsp" %>
            </header>
            <aside class="main-sidebar">
                <%
                    String home = "";
                    String transaction = "";
                    String log = "";
                    String maintenance = "";
                    String returnTrans = "";
                %>
                <%@include file="cashier-sidebar.jsp" %>
            </aside>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        Dimata Cashier
                        <small>Transaction Return</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-home"></i> Home</a></li>
                        <li class="active">Transaction Return</li>
                    </ol>
                </section>
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box box-primary">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Transaction Return</h3>
                                </div>
                                <div class="box-body">
                                    <div class="row">
                                        <form id="searchForm">
                                            <div class="col-md-6">

                                                <div class="input-group" style="margin-bottom: 2px;">
                                                    <%
                                                        String whereClause = "";
                                                        Vector listLocation = PstLocation.listLocationStore(0, 0, "" + whereClause, "");
                                                        out.println("<select name='location' id='locationStore' class='form-control'>");
                                                        out.println("<option value='0'>All Location</option>");
                                                        if (listLocation.size() != 0) {
                                                            for (int i = 0; i < listLocation.size(); i++) {
                                                                Location entLocation = (Location) listLocation.get(i);
                                                                out.println("<option value='" + entLocation.getOID() + "'>" + entLocation.getName() + "</option>");

                                                            }
                                                        }
                                                        out.println("</select>");
                                                        out.println("<div class='input-group-addon' style='border:none;'>&nbsp;</div>");
                                                    %>

                                                    <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                                    <input id="orSearch1" name="date1" class="form-control formsearch datePicker" required="" type="text">
                                                    <div class="input-group-addon " style="border:none;"><i class="fa fa-minus"></i></div>
                                                    <div class="input-group-addon" style="border-right:none;"><i class="fa fa-calendar"></i></div>
                                                    <input id="orSearch2" name="date2" class="form-control datePicker formsearch" required="" type="text">
                                                </div>
                                            </div>
                                            <div class="col-md-3">
                                                <input type="text" name="searchText" class="form-control formsearch" placeholder="Scan or search transaction..." id="noInvoice"> 
                                            </div>
                                            <div class="col-md-3">
                                                <button id="search" type="button" class="btn btn-primary">
                                                    <i class="fa fa-search"></i>
                                                    Search
                                                </button>                                           
                                            </div>
                                        </form>
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
                <strong>Copyright &copy; 2015 <a href="#">Dimata IT Solution &REG;</a>.</strong> All rights reserved.
            </footer>  
        </div>
        <%@include file="cashier-jquery-bootstrap.jsp" %>
        <script type="text/javascript" src="../styles/dimata-app.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {                
                if(<%= listOpeningCashier.size() %> == 0){
                    window.location = "<%= approot %>/cashier/cashier-home.jsp";
                }
                
                //-------------------------------
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
                
                var  loadDataPaging = function(elementId){
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
                        loadData(showPage);
                        //loadVoucher(false,showPage);
                    });

                };
                
                var loadData = function(showPage){
                    var dataFor = "listReturnBill";
                    command = "<%= Command.LIST%>";
                    dataSend = $("#searchForm").serialize();
                    dataSend = dataSend + "&FRM_FIELD_DATA_FOR="+dataFor+"&FRM_FIELD_SHOW_PAGE="+showPage+"&command="+command+"";
                    
                    onSuccess = function(data){

                    };

                    onDone = function(data){
                        loadDataPaging(".pagingVc");
                        loadDetaiListAction(".searchBillReturnSelect");
                    };
                    
                    getDataFunction(onDone, onSuccess, approot, command, dataSend, "ReturnTransactionHandler", "#dynamicContent", true, "json");
                };
                
                var loadDataAction = function(elementId){
                    $(elementId).click(function(){
                        loadData("1");
                    });
                };
                
                var loadDetailListReturn = function(oidBillMain,showModal){
                    var dataFor = "loadDetailListReturn";
                    command = "<%= Command.LIST%>";
                    
                    dataSend = {
                        "FRM_FIELD_DATA_FOR"          : dataFor,
                        "command"		      : command,
                        "FRM_FIELD_BILL_MAIN"         : oidBillMain                      
                    };
                    
                    onSuccess = function(data){

                    };

                    onDone = function(data){
                        checkAction("#returnAll");
                    };
                    if (showModal==true){
                        $("#modalReturn").modal("show");
                    }
                    getDataFunction(onDone, onSuccess, approot, command, dataSend, "ReturnTransactionHandler", "#modalPlace", true, "json");
                };
                
                var loadDetaiListAction = function(elementId){
                    $(elementId).click(function(){
                        var oid = $(this).data("mainoid");
                        loadDetailListReturn(oid,true);
                    });
                };
                
                var checkAction = function (elementId){
                    $(elementId).change(function() {
                        if(this.checked) {
                            $('.qtyReturnHelp').each(function(){
                                var id = this.id;
                                var nilai = $('#'+id+'').val();
                                var ids = id.split("-");
                                var no = ids[1];
                                $('#qtyTemp-'+no+'').val(nilai);

                            });
                        }else{
                            $('.qtyReturnHelp').each(function(){
                                var id = this.id;
                                var nilai = $('#'+id+'').val();
                                var ids = id.split("-");
                                var no = ids[1];
                                $('#qtyTemp-'+no+'').val(0);

                            });
                            $('.qtyTemp').val('0');
                        } 
                    });
                };
                
                var saveReturnBill = function(elementId){
                    $(elementId).click(function(){
                        var qtyOri;
                        var value;
                        var canSave = false;
                        $("#returnBill .alert").remove();
                        $(".returntext").each(function(event){
                            var id = $(this).attr("id");
                            var idtemp = id.split("-");
                            var order = idtemp[1];
                            var idQtyOri = "qtyReturn-"+order+"";
                            var qtyOri = $("#"+idQtyOri+"").val();
                            var value = $(this).val();
                            //alert(value);  

                            if (value!=""){
                                if(!isNaN(value)){
                                    //number
                                    if (value>qtyOri){
                                        $(this).parent().append("<div style='margin-top: 5px;' class='alert alert-danger alertReturn'>Maximum qty is "+qtyOri+"</div>");
                                        canSave = false;
                                        return false;

                                    }else{
                                        //PROSES DI SINI
                                        canSave= true;
                                    }
                                }else{
                                    $(this).parent().append("<div style='margin-top: 5px;' class='alert alert-danger alertReturn'>Please insert number</div>");
                                    canSave = false;
                                    return false;
                                }
                            }else{                           
                                $(this).parent().append("<div style='margin-top: 5px;' class='alert alert-danger alertReturn'>Please insert value</div>");
                                canSave = false;
                                return false;
                            }

                        });
                        
                        if (canSave==true){
                            
                            dataSend = $("#returnBill").serialize();
                            dataSend = dataSend + "&appRoot="+approot+"&FRM_FIELD_CASHIER_LOGIN=<%= userId%>";
                            alert(dataSend);
                            onSuccess = function(data){
                                
                                //lert(JSON.stringify(data));
                                //alert(data.FRM_FIELD_HTML);
                            };

                            onDone = function(data){
                                $("#CONTENT_PRINT").html(data.FRM_FIELD_HTML);
                                window.print();
                                $("#modalReturn").modal('hide');
                            };
                            
                            getDataFunction(onDone, onSuccess, approot, command, dataSend, "ReturnTransactionHandler", "#modalPlace", true, "json");
                        }
                    });
                };
                
                
                //------------------------------
                saveReturnBill("#saveReturnBill");
                loadDataAction("#search");
                loadData("1");
                $(".datePicker").datepicker({
                    format: 'yyyy-mm-dd'
                }).on("changeDate", function (ev) {
                    $(this).datepicker('hide');
                });

                $(".datePicker2").datepicker({
                    format: 'yyyy-mm-dd'
                }).on("changeDate", function (ev) {
                    $(this).datepicker('hide');
                });

                //----------AUTO LOG OUT FUNCTION

                var autoLog = "<%= logOutAuto%>";
                var autoLogTime = "<%= iddleTime%>";
                var timeLogOut = parseInt(autoLogTime) * 1000;
                var autoFirstCounter = 30;
                var intervallAutoLog;

                var iddleEvent = function(autoLogTime) {
                    //CHECK IDDLE OR NO
                    var idleState = false;
                    var idleTimer = null;
                    var idleWait = autoLogTime;

                    $('*').bind('mousemove keydown scroll', function() {
                        clearTimeout(idleTimer);
                        idleState = false;
                        idleTimer = setTimeout(function() {
                            // Idle Event               
                            $("#modalAutoLogOut").modal("show");
                            intervallAutoLog = setInterval(function() {
                                checkCounter("#counterAuto");
                            }, 1000);
                            idleState = true;
                        }, idleWait);
                    });

                    $("body").trigger("mousemove");
                };

                var checkCounter = function(elementChange) {
                    if (autoFirstCounter == 1) {
                        window.location = "<%= approot%>/cashier/cashier-logout.jsp";
                    } else {
                        autoFirstCounter = autoFirstCounter - 1;
                        $(elementChange).html("" + autoFirstCounter + "");
                    }

                };

                var logOutAutoButton = function(elementId) {
                    $(elementId).click(function() {
                        window.location = "<%= approot%>/cashier/cashier-logout.jsp";
                    });
                };

                var keepLoginAuto = function(elementId) {
                    $(elementId).click(function() {
                        clearInterval(intervallAutoLog);
                        $("#modalAutoLogOut").modal("hide");
                        autoFirstCounter = 30;
                    });
                };
                
                


                if (autoLog == "1") {
                    iddleEvent(timeLogOut);
                }
                keepLoginAuto("#keepLoginAuto");
                logOutAutoButton("#logOutAuto");
                //----------AUTO LOG OUT FUNCTION

            });
        </script>
        <!-- PLUGINS COMPONEN -->
        <%@include file="cashier-plugin.jsp" %>
        <div id="print_place"></div>
    </body>
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
    <div id="modalReturn" class="modal nonprint">
        <div class="modal-dialog modal-large">
            <div class="modal-content">
                <div class="modal-header">
                    <h3 class="modal-title">RETURN BILL</h3>
                </div>
                <form id="returnBill">
                    <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID] %>" value="<%= cashCashier.getOID() %>">
                    <input type="hidden" name="FRM_FIELD_DATA_FOR" value="returnBillSave">
                    <input type="hidden" name="command" value="<%= Command.SAVE %>">
                    <div class="modal-body" id="modalPlace">
                        
                    </div>
                </form>
                <div class="modal-footer">
                    <button id="saveReturnBill" type="button" class="btn btn-primary pull-right"><i class="fa fa-save"></i> Save</button>
                    <button id="specialButton" type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Close</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div>
</html>
