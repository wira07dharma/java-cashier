<%-- 
    Document   : cashier-transfer
    Created on : Nov 13, 2018, 7:25:13 PM
    Author     : dimata005
--%>

<%@page import="java.util.Date"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="java.util.Formatter"%>
<%@page import="com.dimata.common.entity.location.PstLocation"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.common.entity.location.Location"%>
<%@page import="com.dimata.pos.entity.balance.PstCashCashier"%>
<%@page import="com.dimata.pos.entity.balance.CashCashier"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.BillMainJSON"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@include file="../main/javainit_cashier.jsp" %>
<%
    CashCashier cashCashier = new CashCashier();
    Location location = new Location();
    Vector listOpeningCashier =  PstCashCashier.listCashOpening(0, 0, 
	    "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID]+"='"+userId+"' "
	    + "AND CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID]+"='1'", "");   
    Vector openingCashier = new Vector(1,1);
    Vector listBillMain = new Vector(1,1);
    if(listOpeningCashier.size() != 0){
	openingCashier = (Vector) listOpeningCashier.get(0);
	if(openingCashier.size() != 0 ){
	    cashCashier = (CashCashier) openingCashier.get(0);
	    location = (Location) openingCashier.get(2);
	}
    }
    
%>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Transfer - Dimata Cashier</title>
    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <%@include file="cashier-css.jsp" %>
    <script type="text/javascript" src="../styles/cashier/dist/js/numberformat.js"></script>
    <script type="text/javascript" src="../styles/jquery.simplePagination.js"></script>
    <style>
        .textual:focus {
            background-color: yellow;
        }
        .button-wrapper .btn {
            margin-bottom:5px;
        }
    </style>
  </head>
  <body class="skin-queentandoor sidebar-mini wysihtml5-supported sidebar-collapse fixed">
      <input type="hidden" id="approotsystem" value="<%= approot %>"><input type="hidden" id="getPaymentType" >
    <div class="wrapper nonprint">
      <header class="main-header">
	  <%@include file="cashier-header.jsp" %>
      </header>
      <!-- Left side column. contains the logo and sidebar -->
      <aside class="main-sidebar">
	    <%
		String home = "";
		String transaction = "active";
                String log = "";
                String maintenance = "";
	    %>
	    <%@include file="cashier-sidebar.jsp" %>           
      </aside>
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>Dimata Cashier<small>Transfer</small></h1>
          <ol class="breadcrumb">
            <li><a href="<%= approot %>/cashier/cashier-home.jsp"><i class="fa fa-home"></i> Home</a></li><li class="active">Transaction</li>
          </ol>
        </section>
        <!-- Main content -->
        <section class="content">
	    <div class="row">
		<div class="col-md-12">
		    <div class="box box-primary">
			<div class="box-header with-border ">
			    <div class="col-md-3 button-wrapper">
                                    <button class="btn btn-primary newtransfer" data-location="<%=location.getOID()%>" data-type="0" type="button">
                                        <i class="fa fa-plus"></i> New Transfer (Warehouse - Store)
                                    </button>
                                    <button class="btn btn-primary newtransfer" data-location="<%=location.getOID()%>" data-type="1" type="button">
                                        <i class="fa fa-plus"></i> New Transfer (Store - Warehouse)
                                    </button>
                            </div>
                            <div class="col-md-6">
                                <div class="input-group" style="margin-bottom: 2px;">


                                    <%
                                        String whereClause = "";                                           
                                        Vector listLocation = PstLocation.listLocationStore(0, 0,""+whereClause, "");
                                        out.println("<select id='locationStore' class='form-control'>");
                                        if(listLocation.size() != 0){
                                            for(int i=0; i<listLocation.size(); i++){
                                                Location loc = (Location) listLocation.get(i);
                                                out.println("<option value='"+loc.getOID()+"'>"+loc.getName()+"</option>");
                                                /*
                                                if (loc.getParentLocationId() != 0){
                                                    try {
                                                        Location parentLoc = PstLocation.fetchExc(loc.getParentLocationId());
                                                        out.println("<option value='"+parentLoc.getOID()+"'>"+parentLoc.getName()+"</option>");
                                                    } catch (Exception exc){}
                                                }
                                                */
                                            }
                                        }
                                        out.println("</select>");
                                        out.println("<div class='input-group-addon' style='border:none;'>&nbsp;</div>");
                                    %>

                                    <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                    <input id="orSearch1" class="form-control datePicker" required="" type="text" value="<%=Formater.formatDate(new Date(), "yyyy-MM-dd")%>">
                                    <div class="input-group-addon" style="border:none;"><i class="fa fa-minus"></i></div>
                                    <div class="input-group-addon" style="border-right:none;"><i class="fa fa-calendar"></i></div>
                                    <input id="orSearch2" class="form-control datePicker" required="" type="text" value="<%=Formater.formatDate(new Date(), "yyyy-MM-dd")%>">
                                </div>
                            </div>
                            <div class="col-md-3">
                                <button id="search" type="button" class="btn btn-primary">
                                    <i class="fa fa-search"></i>
                                    Search
                                </button>
                            </div>        
			</div>
                        <div class="box-body" id="CONTENT_LOAD">
                            
                        </div>
			<div class="box-footer"></div>
			<div class="overlay" style="display:none;" id="CONTENT_ANIMATE_CHECK">
			    <i class="fa fa-refresh fa-spin"></i>
			</div>
		    </div>
		</div>
	    </div>
	</section>
      </div>
      <footer class="main-footer" style="bottom: 0;position: fixed;width: 100%">
	  <div id="CONTENT_TOTAL" style="display:none;" class="col-md-12">
              <div class="col-md-1 pull-right">&nbsp;</div>
	      <div class="col-md-4 pull-right">
	      
	      <table style="background-color: #222D32;border-radius: 5px;">
		    <tr>
			<td style="padding:4px;" width="10%"><b style="color:#a1a1a1;">GRAND TOTAL</b></td>
			<td style="padding:4px;" width="80%"><center><font size="5" id="grandTotal" color="white"></font></center></td>
			<td style="padding:4px;" width="10%"><button class="btn btn-success" id="btnPay"><b>PAY (F8)</b></button></td>
		    </tr>
		</table>
	      </div>
	  </div>    
      </footer>
    </div>
    <!-- JQUERY & BOOTSTRAP COMPONEN -->
    <%@include file="cashier-jquery-bootstrap.jsp" %>
    <script type="text/javascript" src="../styles/cashier/signature/flashcanvas.js"></script>
    <script type="text/javascript" src="../styles/cashier/signature/jSignature.min.js"></script>
    <script type="text/javascript" src="../styles/dimata-app.js"></script>
    <script src="../styles/src/moment.js"></script>  
    <script src="../styles/src/js/bootstrap-datetimepicker.min.js"></script>
    <link href="../styles/src/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/> 
    <%@include file="cashier-plugin.jsp" %>
    <script type="text/javascript">
        function addItemTransfer(barcode){
            var command = "<%= Command.SAVE%>";
            var loadtype = "saveTransferDoc";
            var matDisOid = $("#newOrderConfirmation #matDisId").val();
            var dataSend = "command="+command+"&loadtype="+loadtype+"&MATERIAL_DISPATCH_ID="+matDisOid+"&BARCODE="+barcode;
            $.ajax({
                type    : "POST",
                url	    : "<%= approot %>/TransactionCashierHandler",
                data    : dataSend,
                chace   : false,
                dataType : "json",
                success : function(data){
                }
            }).done(function(data){ //alert("done");
                $("#CONTENT_ITEM").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                var docNo = $("#newOrderConfirmation #matDisCode").val();
                $("#CONTENT_TITLE").html("Transfer No."+docNo);
                $('#searchItemTransfer').focus();
                $('#searchItemTransfer').keydown(function(e){
                    if (e.keyCode==13){
                        addItemTransfer($(this).val());
                    }
                });
                post();
                deleteItem();
                editItem();
                saveItem();
            })
        }
        function viewTransfer(){
            $(".viewDf").click(function(){
                $('#newOrderConfirmation').modal('show');
                var command = "<%= Command.NONE%>";
                var locationId = $(this).data("location");
                var dfId = $(this).data("oid");
                var loadtype = "viewTransferDoc";
                var status = $(this).data("status");
                var dataSend = "command="+command+"&loadtype="+loadtype+"&LOCATION_ID="+locationId+"&MATERIAL_DISPATCH_ID="+dfId;
                $.ajax({
		    type    : "POST",
		    url	    : "<%= approot %>/TransactionCashierHandler",
		    data    : dataSend,
		    chace   : false,
		    dataType : "json",
		    success : function(data){
		    }
		}).done(function(data){ //alert("done");
                    $("#CONTENT_ITEM").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                    var docNo = $("#newOrderConfirmation #matDisCode").val();
                    $("#CONTENT_TITLE").html("Transfer No."+docNo);
                    $('#searchItemTransfer').focus();
                    $('#searchItemTransfer').keydown(function(e){
                        if (e.keyCode==13){
                            addItemTransfer($(this).val());
                        }
                    });
                    if (status===5){
                        $("#btnPostTransaction").attr("disabled","disabled");
                    }
                    post();
                    deleteItem();
                    editItem();
                    saveItem();
		})
            });
        }
        function deleteItem(){
            $(".deleteItem").click(function(){
                var r = confirm("Delete Item ?");
                if (r == true) {
                    var command = "<%= Command.SAVE%>";
                    var dfItemId = $(this).data("oid");
                    var matDisOid = $("#newOrderConfirmation #matDisId").val();
                    var loadtype = "deleteTransferItem";
                    var dataSend = "command="+command+"&loadtype="+loadtype+"&MATERIAL_DISPATCH_ITEM_ID="+dfItemId+"&MATERIAL_DISPATCH_ID="+matDisOid;
                    $.ajax({
                        type    : "POST",
                        url	    : "<%= approot %>/TransactionCashierHandler",
                        data    : dataSend,
                        chace   : false,
                        dataType : "json",
                        success : function(data){
                        }
                    }).done(function(data){ //alert("done");
                        $("#CONTENT_ITEM").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                        var docNo = $("#newOrderConfirmation #matDisCode").val();
                        $("#CONTENT_TITLE").html("Transfer No."+docNo);
                        $('#searchItemTransfer').focus();
                        $('#searchItemTransfer').keydown(function(e){
                            if (e.keyCode==13){
                                addItemTransfer($(this).val());
                            }
                        });
                        if (status===5){
                            $("#btnPostTransaction").attr("disabled","disabled");
                        }
                        post();
                        deleteItem();
                        editItem();
                        saveItem();
                    })
                }
            });
        }
        function editItem(){
            $(".editItem").click(function(){
                var command = "<%= Command.NONE%>";
                var dfItemId = $(this).data("oid");
                var matDisOid = $("#newOrderConfirmation #matDisId").val();
                var loadtype = "viewTransferDoc";
                var dataSend = "command="+command+"&loadtype="+loadtype+"&MATERIAL_DISPATCH_ITEM_ID="+dfItemId+"&MATERIAL_DISPATCH_ID="+matDisOid;
                $.ajax({
                    type    : "POST",
                    url	    : "<%= approot %>/TransactionCashierHandler",
                    data    : dataSend,
                    chace   : false,
                    dataType : "json",
                    success : function(data){
                    }
                }).done(function(data){ //alert("done");
                    $("#CONTENT_ITEM").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                    var docNo = $("#newOrderConfirmation #matDisCode").val();
                    $("#CONTENT_TITLE").html("Transfer No."+docNo);
                    $('#searchItemTransfer').focus();
                    $('#searchItemTransfer').keydown(function(e){
                        if (e.keyCode==13){
                            addItemTransfer($(this).val());
                        }
                    });
                    if (status===5){
                        $("#btnPostTransaction").attr("disabled","disabled");
                    }
                    post();
                    deleteItem();
                    editItem();
                    saveItem();
                })
            });
        }
        function saveItem(){
            $(".saveItem").click(function(){
                var command = "<%= Command.SAVE%>";
                var dfItemId = $(this).data("oid");
                var qty = $("#itemQtyEdit").val();
                var matDisOid = $("#newOrderConfirmation #matDisId").val();
                var loadtype = "saveTransferItem";
                var dataSend = "command="+command+"&loadtype="+loadtype+"&MATERIAL_DISPATCH_ITEM_ID="+dfItemId+"&MATERIAL_DISPATCH_ID="+matDisOid+"&QTY_ITEM="+qty;
                $.ajax({
                    type    : "POST",
                    url	    : "<%= approot %>/TransactionCashierHandler",
                    data    : dataSend,
                    chace   : false,
                    dataType : "json",
                    success : function(data){
                    }
                }).done(function(data){ //alert("done");
                    $("#CONTENT_ITEM").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                    var docNo = $("#newOrderConfirmation #matDisCode").val();
                    $("#CONTENT_TITLE").html("Transfer No."+docNo);
                    $('#searchItemTransfer').focus();
                    $('#searchItemTransfer').keydown(function(e){
                        if (e.keyCode==13){
                            addItemTransfer($(this).val());
                        }
                    });
                    if (status===5){
                        $("#btnPostTransaction").attr("disabled","disabled");
                    }
                    post();
                    deleteItem();
                    editItem();
                    saveItem();
                })
            });
        }
        $('.newtransfer').click(function(){
            var r = confirm("Create Transfer Document?");
            if (r == true) {
                $('#newOrderConfirmation').modal('show');
                var command = "<%= Command.ADD%>";
                var locationId = $(this).data("location");
                var type = $(this).data("type");
                var loadtype = "createTransferDoc";
                var dataSend = "command="+command+"&loadtype="+loadtype+"&LOCATION_ID="+locationId+"&TYPE_TRANSFER="+type;
                $.ajax({
		    type    : "POST",
		    url	    : "<%= approot %>/TransactionCashierHandler",
		    data    : dataSend,
		    chace   : false,
		    dataType : "json",
		    success : function(data){
		    }
		}).done(function(data){ //alert("done");
                    $("#CONTENT_ITEM").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                    var docNo = $("#newOrderConfirmation #matDisCode").val();
                    $("#CONTENT_TITLE").html("Transfer No."+docNo);
                    $('#searchItemTransfer').focus();
                    $('#searchItemTransfer').keydown(function(e){
                        if (e.keyCode==13){
                            addItemTransfer($(this).val());
                        }
                    });
                    post();
                    deleteItem();
                    editItem();
                    saveItem();
		})
            
            } else {
                txt = "You pressed Cancel!";
            } 
        });
        function post(){
            $('#btnPostTransaction').unbind().click(function(){
                var r = confirm("Post Transfer?");
                if (r == true) {
                    $('#newOrderConfirmation').modal('show');
                    var command = "<%= Command.SAVE%>";
                    var matDisOid = $("#newOrderConfirmation #matDisId").val();
                    var loadtype = "postTransferDoc";
                    var dataSend = "command="+command+"&loadtype="+loadtype+"&MATERIAL_DISPATCH_ID="+matDisOid;
                    $.ajax({
                        type    : "POST",
                        url	    : "<%= approot %>/TransactionCashierHandler",
                        data    : dataSend,
                        chace   : false,
                        dataType : "json",
                        success : function(data){
                        }
                    }).done(function(data){ //alert("done");
                        $('#newOrderConfirmation').modal('hide');
                    })

                } else {
                    txt = "You pressed Cancel!";
                } 
            });
        }
        $('#search').click(function(){
            var command = "<%= Command.NONE%>";
            var locationId = $("#locationStore").val();
            var dateStart = $("#orSearch1").val();
            var dateEnd = $("#orSearch2").val();
            var loadtype = "searchTransferDoc";
            var dataSend = "command="+command+"&loadtype="+loadtype+"&LOCATION_ID="+locationId+"&START_DATE="+dateStart+"&END_DATE="+dateEnd;
            $.ajax({
                type    : "POST",
                url	    : "<%= approot %>/TransactionCashierHandler",
                data    : dataSend,
                chace   : false,
                dataType : "json",
                success : function(data){
                }
            }).done(function(data){ //alert("done");
                $("#CONTENT_LOAD").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                viewTransfer();
            })
        });
        $(".datePicker").datepicker({
            format: 'yyyy-mm-dd',

        }).on("changeDate", function (ev) {
            $(this).datepicker('hide');
        });
    </script>
    <div id="newOrderConfirmation" class="modal nonprint">
      <div class="modal-dialog modal-lg">
	<div class="modal-content">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	    <h4 class="modal-title" id="CONTENT_TITLE"><b>Transfer</b></h4>
	  </div>
	  <div class="modal-body" id="CONTENT_ITEM">
	  </div>
	  <div class="modal-footer">
	      <div id="cancelButton">
                  <button id="btnPostTransaction" class="btn btn-primary" type="button"><i class="fa fa-check"></i> Post</button>
	      </div>
	  </div>
	</div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div>
  </body>
</html>
