<%-- 
    Document   : cashier_iframe
    Created on : Nov 20, 2019, 4:44:23 PM
    Author     : Dimata IT Solutions
--%>

<%@page import="com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain"%>
<%@page import="com.dimata.pos.entity.balance.PstBalance"%>
<%@page import="com.dimata.common.entity.location.Location"%>
<%@page import="com.dimata.pos.entity.balance.PstCashCashier"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.pos.entity.balance.CashCashier"%>
<%@page import="com.dimata.posbo.entity.masterdata.Shift"%>
<%@page import="com.dimata.pos.entity.billing.PstBillMain"%>
<%@page import="com.dimata.pos.entity.billing.BillMain"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="../main/javainit_cashier.jsp" %>
<!DOCTYPE html>
<%
   String sedanaUrl = PstSystemProperty.getValueByName("SEDANA_APP_URL");
//   String sedanaUrl = PstSystemProperty.getValueByName("SEDANA_URL");
   long billId = FRMQueryString.requestLong(request, "billId");
   int type = FRMQueryString.requestInt(request, "type");
   int catPengajuan = FRMQueryString.requestInt(request, "TIPE_PENGAJUAN");
    
    Shift shift = new Shift();
    CashCashier cashCashier = new CashCashier();
    Location location = new Location();
    Vector listOpeningCashier = PstCashCashier.listCashOpening(0, 0,
            "CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID] + "='" + userId + "' "
            + "AND CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID] + "='1'", "");
    Vector openingCashier = new Vector(1, 1);
    Vector listBalance = new Vector(1, 1);
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
   
       
   String url = "";
       if (type == 0) {
               url = "http://localhost:8080/sedana_dimata/sedana/transaksikredit/transaksi_kredit.jsp?autologin=1&command=" + Command.ADD + "&TIPE_PENGAJUAN=" + catPengajuan
                       + "&billId=" + billId + "&username=" + userCashier.getAppUser().getLoginId() + "&password=" + userCashier.getAppUser().getPassword();
       } else if (type == 1) {
               url = sedanaUrl + "/sedana/transaksikredit/simulasi_kredit.jsp?autologin=1&USER_ID="+userId+"&SHIFT_ID="+shift.getOID()+"&CASH_CASHIER_ID="+cashCashier.getOID()
                       +"&LOCATION_ID="+location.getOID()+ "&username=" + userCashier.getAppUser().getLoginId() + "&password=" + userCashier.getAppUser().getPassword();
       }else if(type == 2){
           url = sedanaUrl + "/sedana/transaksikredit/form_pembayaran_kredit.jsp?autologin=1&username=" + userCashier.getAppUser().getLoginId() + "&password=" + userCashier.getAppUser().getPassword();
       }
    

%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Open Cashier - Dimata Cashier</title>
        <%@include  file="cashier-css.jsp"%>   
        <script type="text/javascript" src="../styles/cashier/dist/js/numberformat.js"></script>

<!--        <style>
            .holds-the-iframe {
                background:url(../images/loading2.gif) center center no-repeat;
              }
        </style>-->
        <style>
          .spinner {
                    width: 40px;
                    height: 40px;
                    background-color: #f9821f;
                     position: fixed;
                     bottom: 10px;
                     right: 40px;
                    -webkit-animation : sk-rotateplane 1.2s infinite ease-in-out;
                    animation: sk-rotateplane 1.2s infinite ease-in-out;
                  }

                  @-webkit-keyframes sk-rotateplane {
                    0% { -webkit-transform: perspective(120px) }
                    50% { -webkit-transform: perspective(120px) rotateY(180deg) }
                    100% { -webkit-transform: perspective(120px) rotateY(180deg)  rotateX(180deg) }
                  }

                  @keyframes sk-rotateplane {
                    0% { 
                      transform: perspective(120px) rotateX(0deg) rotateY(0deg);
                      -webkit-transform: perspective(120px) rotateX(0deg) rotateY(0deg) 
                    } 50% { 
                      transform: perspective(120px) rotateX(-180.1deg) rotateY(0deg);
                      -webkit-transform: perspective(120px) rotateX(-180.1deg) rotateY(0deg) 
                    } 100% { 
                      transform: perspective(120px) rotateX(-180deg) rotateY(-179.9deg);
                      -webkit-transform: perspective(120px) rotateX(-180deg) rotateY(-179.9deg);
                    }
                  }
                  .load {
                        background-color: #9898986b;
                        width: 100%;
                        height: 100%;
                        position: fixed;
                    }
        </style>
    </head>
    <body onload="loader()" class="skin-queentandoor sidebar-mini wysihtml5-supported sidebar-collapse fixed" style="margin:0px;padding:0px;overflow:hidden; background-color: #FFFFFF" >
    <div class="wrapper nonprint"> 
        <header class="main-header">
            <%@include file="cashier-header.jsp" %>
        </header>
    <aside class="main-sidebar">
        <%
            String home = "";
            String transaction = "";
            String log = "active";
            String maintenance = "";
        %>
        <%@include file="cashier-sidebar.jsp" %>
    </aside>
    <div id="spinner" class="load">
    <div class="spinner"></div>
    </div>
    <div class="content-wrapper" id="content">
        <div class="holds-the-iframe">
            <iframe src="<%=url%>" frameborder="0" style="height: 95vh; width: 98vw; display: block"></iframe>
        </div>
    </div>
    <footer class="main-footer nonprint">
        <div class="pull-right hidden-xs">
            <b>Version</b> 1.0
        </div>
        <strong>Copyright &copy; 2015 <a href="#">Dimata IT Solution &REG;</a>.</strong> All rights reserved.
    </footer>  
    </div>
    <%@include file="cashier-jquery-bootstrap.jsp" %>
    
    <!-- PLUGINS COMPONEN -->
    <%@include file="cashier-plugin.jsp" %>
    <script>
      var Start;
      function loader(){
        Start = setTimeout(showpage,2500);
      }
      function showpage(){
        document.getElementById("spinner").style.display="none";
        document.getElementById("content").style.display="block";
      }
    </script> 
  </body>
</html>
