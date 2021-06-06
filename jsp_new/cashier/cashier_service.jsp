<%-- 
    Document   : cashier_service
    Created on : Nov 15, 2018, 11:42:13 AM
    Author     : dimata005
--%>


<%@page import="com.dimata.cashierweb.session.masterdata.CatalogManager"%>
<%@page import="com.dimata.cashierweb.session.masterdata.BillManager"%>
<%@page import="com.dimata.cashierweb.session.masterdata.StockCheckManager"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.posbo.utility.ServiceStockCheck"%>
<%@page import="com.dimata.posbo.utility.Service"%>
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
		int iCommand = FRMQueryString.requestCommand(request);
		int serviceType = FRMQueryString.requestInt(request, "type");
		
		StockCheckManager stockCheckManager = new StockCheckManager();
		BillManager billManager = new BillManager();
		CatalogManager catManager = new CatalogManager();
		stockCheckManager.setUserId(userId);
		catManager.setUserId(userId);
		if (serviceType == 1){
			switch (iCommand) {

				case Command.START:

					stockCheckManager.startCheck();

					break;



				case Command.STOP:

					stockCheckManager.stopMonitor();

					break;

			}
		} else if (serviceType == 2){
			switch (iCommand) {

				case Command.START:

					billManager.startCheck();

					break;



				case Command.STOP:

					billManager.stopMonitor();

					break;

			}
		} else if (serviceType == 3){
			switch (iCommand) {

				case Command.START:

					catManager.startCheck();

					break;



				case Command.STOP:

					catManager.stopMonitor();

					break;

			}
		}
		
		boolean isStockRunning = stockCheckManager.getStatus();
		boolean isBillRunning = billManager.getStatus();
		boolean isCatRunning = catManager.getStatus();
	    %>
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
          <h1>Dimata Cashier<small>Service</small></h1>
          <ol class="breadcrumb">
            <li><a href="<%= approot %>/cashier/cashier-home.jsp"><i class="fa fa-home"></i> Home</a></li><li class="active">Service</li>
          </ol>
        </section>
        <!-- Main content -->
        <section class="content">
	    <div class="row">
		<div class="col-md-12">
			<form name="frm_service" method="post" action="">
			<input type="hidden" name="command" value="">
			<input type="hidden" name="type" value="">
		    <div class="box box-primary">
				<div class="box-header with-border ">
					<div class="col-md-3">
					<h3 class="box-title">Stock Manager Service</h3>
					</div>
				</div>
                        <div class="box-body" id="CONTENT_LOAD">
							<div class="form-inline">
								<button type="button" id="btnStart" onClick="javascript:cmdStartService()" class="btn btn-success btn_service" data-status="<%= Service.SERVICE_ON%>" <%=(isStockRunning ? "disabled" : "")%>><i class="fa fa-play"></i>&nbsp; Start</button>
								<button type="button" id="btnStop" onClick="javascript:cmdStopService()" class="btn btn-danger btn_service" data-status="<%= Service.SERVICE_OFF%>" <%=(!isStockRunning ? "disabled" : "")%>><i class="fa fa-stop"></i>&nbsp; Stop</button>
							</div>
							<div class="col-md-12">
								<%=stockCheckManager.getProses()%>
							</div>
							<div class="col-md-12">
								<%=stockCheckManager.getDataProses()%>
							</div>
                        </div>
				<div class="box-footer"></div>
				<div class="overlay" style="display:none;" id="CONTENT_ANIMATE_CHECK">
					<i class="fa fa-refresh fa-spin"></i>
				</div>
		    </div>
			<div class="box box-primary">
				<div class="box-header with-border ">
					<div class="col-md-3">
					<h3 class="box-title">Sales Manager Service</h3>
					</div>
				</div>
                        <div class="box-body" id="CONTENT_LOAD">
							<div class="form-inline">
								<button type="button" id="btnStart" onClick="javascript:cmdStartSalesService()" class="btn btn-success btn_service" data-status="<%= Service.SERVICE_ON%>" <%=(isBillRunning ? "disabled" : "")%>><i class="fa fa-play"></i>&nbsp; Start</button>
								<button type="button" id="btnStop" onClick="javascript:cmdStopSalesService()" class="btn btn-danger btn_service" data-status="<%= Service.SERVICE_OFF%>" <%=(!isBillRunning ? "disabled" : "")%>><i class="fa fa-stop"></i>&nbsp; Stop</button>
							</div>   
							<div class="col-md-12">
								<%=billManager.getProses()%>
							</div>
							<div class="col-md-12">
								<%=billManager.getDataProses()%>
							</div>
                        </div>
				<div class="box-footer"></div>
				<div class="overlay" style="display:none;" id="CONTENT_ANIMATE_CHECK">
					<i class="fa fa-refresh fa-spin"></i>
				</div>
		    </div>
			<div class="box box-primary">
				<div class="box-header with-border ">
					<div class="col-md-3">
					<h3 class="box-title">Catalog Manager Service</h3>
					</div>
				</div>
                        <div class="box-body" id="CONTENT_LOAD">
							<div class="form-inline">
								<button type="button" id="btnStart" onClick="javascript:cmdStartCatalogService()" class="btn btn-success btn_service" data-status="<%= Service.SERVICE_ON%>" <%=(isCatRunning ? "disabled" : "")%>><i class="fa fa-play"></i>&nbsp; Start</button>
								<button type="button" id="btnStop" onClick="javascript:cmdStopCatalogService()" class="btn btn-danger btn_service" data-status="<%= Service.SERVICE_OFF%>" <%=(!isCatRunning ? "disabled" : "")%>><i class="fa fa-stop"></i>&nbsp; Stop</button>
							</div>   
							<div class="col-md-12">
								<%=catManager.getProses()%>
							</div>
							<div class="col-md-12">
								<%=catManager.getDataProses()%>
							</div>
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
    <script language="JavaScript">
        function cmdStopService()
        {
          document.frm_service.command.value="<%=String.valueOf(Command.STOP)%>"; 
          document.frm_service.action="cashier_service.jsp";
		  document.frm_service.type.value=1; 
          document.frm_service.submit();  
        }

        function cmdStartService()
        {
          document.frm_service.command.value="<%= String.valueOf(Command.START) %>";	  
          document.frm_service.action="cashier_service.jsp";   
		  document.frm_service.type.value=1; 
          document.frm_service.submit();  
        }
		function cmdStopSalesService()
        {
          document.frm_service.command.value="<%=String.valueOf(Command.STOP)%>"; 
          document.frm_service.action="cashier_service.jsp";
		  document.frm_service.type.value=2; 
          document.frm_service.submit();  
        }

        function cmdStartSalesService()
        {
          document.frm_service.command.value="<%= String.valueOf(Command.START) %>";	  
          document.frm_service.action="cashier_service.jsp";   
		  document.frm_service.type.value=2; 
          document.frm_service.submit();  
        }
		function cmdStopCatalogService()
        {
          document.frm_service.command.value="<%=String.valueOf(Command.STOP)%>"; 
          document.frm_service.action="cashier_service.jsp";
		  document.frm_service.type.value=3; 
          document.frm_service.submit();  
        }

        function cmdStartCatalogService()
        {
          document.frm_service.command.value="<%= String.valueOf(Command.START) %>";	  
          document.frm_service.action="cashier_service.jsp";   
		  document.frm_service.type.value=3; 
          document.frm_service.submit();  
        }
		<% if (isStockRunning || isBillRunning || isCatRunning ) {%>
		setTimeout(function () { 
			window.location.href = window.location.href;
		  }, 5 * 1000);
		  <% } %>
    </script>
  </body>
</html>