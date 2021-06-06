<%@page import="com.dimata.pos.entity.billing.PstBillMain"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain"%>
<%@page import="com.dimata.pos.entity.balance.PstBalance"%>
<%@page import="com.dimata.pos.entity.balance.CashCashier"%>
<%@page import="com.dimata.pos.form.billing.FrmBillMain"%>
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
    
    CashCashier cashCashier = new CashCashier();
    Location location = new Location();
    Vector listBalance = new Vector(1, 1);
    Shift shift = new Shift();
    Vector listOpeningCashier = PstCashCashier.listCashOpening(0, 0,
            "CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID] + "='" + userId + "' "
            + "AND CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID] + "='1'", "");
    Vector openingCashier = new Vector(1, 1);
    Vector listBillMain = new Vector(1, 1);
    if (listOpeningCashier.size() != 0) {
        openingCashier = (Vector) listOpeningCashier.get(0);
        if (openingCashier.size() != 0) {
            cashCashier = (CashCashier) openingCashier.get(0);
            location = (Location) openingCashier.get(2);
            shift = (Shift) openingCashier.get(3);
            listBalance = PstBalance.list(0, 0, "" + PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE] + "='0' "
                    + "AND " + PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID] + "='" + cashCashier.getOID() + "'", "");
        }
        listBillMain = PstCustomBillMain.listOpenBill(0, 0,
                "" + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'", "");
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Open Cashier - Dimata Cashier</title>
    <%@include  file="cashier-css.jsp"%>   
    <script src="../styles/cashier/plugins/jQuery/jQuery-2.1.4.min.js"></script>
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
        %>
        <%@include file="cashier-sidebar.jsp" %>
    </aside>
    <div class="content-wrapper nonprint">
        <section class="content-header">
            <h1>
                Dimata Cashier
                <small>Member Poin</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-home"></i> Home</a></li>
                <li class="active">Member Poin</li>
            </ol>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Member Poin</h3>
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="input-group" style="margin-bottom: 2px;">
                                        <input type="text" class="form-control" placeholder="Scan or Search Member(F7)" id="datasearch"/>
                                        <div class="input-group-addon btn btn-primary" id="search">
                                            <i class="fa fa-search"></i>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row" style="margin-top:20px;">
                                <div class="col-md-12" id="conTable">
                                    <table class="table table-striped table-bordered" id="tbMemberPoin" style="width: 100%">
                                        <thead>
                                            <tr>
                                                <th>No Member</th>
                                                <th>Barcode</th>
                                                <th>Name</th>
                                                <th>Poin</th>
                                                <th>Expired Member</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                    </table>
                                </div>
                                <center><img style="display: none" id="conLoading" src="../images/loadingGif.gif"></center>
                            </div>
                        </div>
                    </div>
                </div>
            </div>   
        </section>
    </div>
    <footer class="main-footer nonprint">
        <div class="pull-right hidden-xs">
            <b>Version</b> 1.0
        </div>
        <strong>Copyright &copy; 2015 <a href="#">Dimata IT Solution &REG;</a>.</strong> All rights reserved.
    </footer>  
    </div>
    <%@include file="cashier-jquery-bootstrap.jsp" %>
    
    <script type="text/javascript">       
        $(document).ready(function (){  
            var base = "<%= approot %>";
            
            $("#datasearch").focus();
            $(".memberPoin").addClass("active");
            
            dataTableListMember("");
            function dataTableListMember(dataSearch){//list member
                $('#conTable').hide();
                $('#conLoading').show();
                $('#tbMemberPoin').dataTable({
                   "destroy": true,
                   "bJQueryUI" : true,
                   "sPaginationType" : "full_numbers",
                   "iDisplayLength":10,
                   "bProcessing" : false,
                   "bServerSide" : true,
                   "searching": false,

                   "sAjaxSource" : "<%= approot%>/ajaxMemberPoin?command=<%= Command.LOAD%>&table=1&user=<%=userId%>&dataSearch="+dataSearch+"",
                   "aoColumns" :   [{"sWidth" : "10%","bSearchable" : true, "bVisible" : true, "asSorting" : [ "desc" ] },
                                   {"sWidth" : "10%","bSortable" : true },//1
                                   {"sWidth" : "10%","bSortable" : true },//2
                                   {"sWidth" : "10%","bSortable" : true },//3
                                   {"sWidth" : "10%","bSortable" : true },//4
                                   {"sWidth" : "10%","bSortable" : true }//5
                                   ],
                           "initComplete": function(settings, json) {
                                $('#conTable').show();
                                $('#conLoading').hide();
                           },
                           "fnDrawCallback": function( oSettings ) {
                           } 
               }); 
            }
            $('#memberpoin').on( 'processing.dt', function ( e, settings, processing ) {
                if(processing){
                    $('#conTable').hide();
                    $('#conLoading').show();
                }else{
                    $('#conTable').show();
                    $('#conLoading').hide();
                }
            }).dataTable();
            
            
            function dataTableListItemEx(poin){//list item exchange
                $('#tbItemListExchange').dataTable({
                   "destroy": true,
                   "bJQueryUI" : true,
                   "sPaginationType" : "full_numbers",
                   "iDisplayLength":10,
                   "bProcessing" : false,
                   "bServerSide" : true,
                   "searching": true,

                   "sAjaxSource" : "<%= approot%>/ajaxMemberPoin?command=<%= Command.LOAD%>&table=2&user=<%=userId%>",
                   "aoColumns" :   [{"sWidth" : "10%","bSearchable" : true, "bVisible" : true, "asSorting" : [ "desc" ] },
                                   {"sWidth" : "10%","bSortable" : true },//1
                                   {"sWidth" : "10%","bSortable" : true },//2
                                   {"sWidth" : "10%","bSortable" : true },//3
                                   {"sWidth" : "10%","bSortable" : true },//4
                                   {"sWidth" : "10%","bSortable" : true }//5
                                   ],
                           "initComplete": function(settings, json) {
                           },
                           "fnDrawCallback": function( oSettings ) {
                           } 
               }); 
            }

            
            
            $("#search").click(function (){//click
                var dataSearch = $("#datasearch").val();
                dataTableListMember(dataSearch);
            });
            $("#datasearch").keydown(function(e){//shorcut
                if(e.keyCode == 118){
                    var dataSearch = $("#datasearch").val();
                    dataTableListMember(dataSearch);
                }
            });
            
            $(document).keydown(function(e){//close all modal
                if(e.keyCode == 27){
                    $(".modal").modal("hide");
                }
            });
            
            $("#conTable").on("click","#tukarPoin",function (){//tombol tukar poin
                $("#mdlTukarPoin").modal("show");
                var oidMember = $(this).val();
                $.ajax({
                    type	    : "POST",
                    url             : "<%= approot %>/ajaxMemberPoin?action=tampilMemberInfo" ,
                    data	    :{"oidMember" : oidMember},
                    dataType        : 'json',
                    cache	    : false,
                    success         : function(data){
                        if(data.status == "succes"){
                            $(".memberInfo").html(""+data.massage);
                            excItemSearch(oidMember);
                        }else{
                            $(".memberInfo").html(""+data.status+"=>"+data.massage);
                        }
                    },
                    error : function(data){
                            alert(data);
                    }
                }).done(function(){
                    exchangeItemBtn();
                });
            });
           
            function excItemSearch(oidMember){//show search item exchange
                $.ajax({
                    type	    : "POST",
                    url             : "<%= approot %>/ajaxMemberPoin?action=searchItemChange" ,
                    data	    :{"oidMember" : oidMember},
                    dataType        : 'json',
                    cache	    : false,
                    success         : function(data){
                        if(data.status == "succes"){
                            $(".itemExchange").html(""+data.massage);
                        }else{
                            $(".itemExchange").html(""+data.status+"=>"+data.massage);
                        }
                        $("#itemSearchExchange").focus();
                    },
                    error : function(data){
                            alert(data);
                    }
                }).done(function(){
                });
            }
            
            function exchangeItemBtn(){//cari barang
                var dtOidMember = $("#dtOidMember").val();
                var dtMemberPoin = $("#dtMemberpoit").val();
                $("#mdlTukarPoin").on("click","#btnSearchItemExchange",function(){//click
                    $("#mdlListItemExchange").modal("show");
                    contentItemListExchange(dtOidMember,dtMemberPoin);
                });
                $("#mdlTukarPoin").on("keydown","#itemSearchExchange",function(e){//shorcut
                    if(e.keyCode == 120){
                        $("#mdlListItemExchange").modal("show");
                        contentItemListExchange(dtOidMember,dtMemberPoin);
                    }
                });
            }
            
            function contentItemListExchange(oidMember,poin){//show content list item excngae
                $.ajax({
                    type	    : "POST",
                    url             : "<%= approot %>/ajaxMemberPoin?action=listItemExchange" ,
                    data	    :{"oidMember" : oidMember},
                    dataType        : 'json',
                    cache	    : false,
                    success         : function(data){
                        if(data.status == "succes"){
                            $("#itemListExchange").html(""+data.massage);
                        }else{
                            $("#itemListExchange").html(""+data.status+"=>"+data.massage);
                        }
                    },
                    error : function(data){
                            alert(data);
                    }
                }).done(function(){
                    dataTableListItemEx(poin);
                });
            }
            
//            catchKey();
            function catchKey(){
                $(document).keydown(function(e){
                    var id ="";
                    try {
                        id = $(e.target).attr("id");
                    }
                    catch(err) {
                        id = "";
                    } 
                    if(typeof id === 'undefined'){
                        id="";
                    };
                    
                    var index=0;
                    var getKeyCode="";
                    getKeyCode = String(e.keyCode);
//                    try { //cek apakah event tersebut di terkunci
//                        index = lockKeyCode.indexOf(getKeyCode);
//                    } catch(err) {
//                        index = -1;
//                    }
                    alert(getKeyCode);
                }); 
            }
            
        });
    </script>
    <!-- PLUGINS COMPONEN -->
    <%@include file="cashier-plugin.jsp" %>
    <!--data make cbn main-->
    <form id="<%= FrmBillMain.FRM_NAME_BILL_MAIN%>_NEWORDER">
        <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]%>" value="<%= cashCashier.getOID()%>">
        <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIFT_ID]%>" value="<%= shift.getOID()%>">
        <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]%>" value="<%= cashCashier.getOID()%>">
        <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_APP_USER_ID]%>" value="<%= userId%>">
        <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]%>" value="<%=location.getOID()%>">
        <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME]%>" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME]%>" value="">
        <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_MOBILE_NUMBER]%>" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_MOBILE_NUMBER]%>" value="">
        <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_ADDRESS]%>" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_ADDRESS]%>" value="">
        <input type="hidden" id="oidReservationOrder"  name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_RESERVATION_ID]%>" value="0">
        <input type="hidden" name="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ID_NEGARA]%>" id="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ID_NEGARA]%>" value="">
        <input type="hidden" name="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GENDER]%>" id="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GENDER]%>" value="">
        <input type="hidden" name="loadtype" value="neworder">
        <input type="hidden" name="command" value="<%= Command.SAVE%>">
    </form>
    
    <!---print modal-->
    <div id="mdlTukarPoin" class="modal nonprint">
      <div class="modal-dialog modal-lg">
	<div class="modal-content">
	  <div class="modal-header">
	    <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
	    <h4 class="modal-title"><b>TUKAR POIN</b></h4>
	  </div>
	  <div class="modal-body">
            <div class="row memberInfo">
                <!--conten member info-->
            </div>
            <hr>
            <div class="itemExchange">
            <!--conten search item will exchange--> 
            </div>
	  </div>
	  <div class="modal-footer">
	      <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
	  </div>
	</div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div>
    
    <!---print modal item-->
    <div id="mdlListItemExchange" class="modal nonprint">
      <div class="modal-dialog modal-lg">
	<div class="modal-content">
            <div class="modal-header">
                <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
                <h4 class="modal-title"><b>Item List</b></h4>
            </div>
            <div class="modal-body">
                <div class="col-md-12" id="conTable">
                    <div id="itemListExchange">
                        
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
            </div>
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
    
    <!--datatable-->
        <link type="text/css" href="../styles/datatable/datatables.min.css">
        <link type="text/css" href="../styles/datatable/DataTables-1.10.21/css/dataTables.bootstrap.min.css">
        <script type="text/javascript" src="../styles/datatable/datatables.min.js"></script>
        <script type="text/javascript" src="../styles/datatable/DataTables-1.10.21/js/dataTables.bootstrap.min.js"></script>
  </body>
</html>
