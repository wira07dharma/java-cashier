<%@page import="com.dimata.cashierweb.entity.admin.PstAppUser"%>
<!-- sidebar: style can be found in sidebar.less -->
<section class="sidebar">
  <!-- Sidebar user panel -->
  <div class="user-panel">
    <div class="pull-left image">
	<i class="icon icon-user"></i> &nbsp;
    </div>
    <div class="pull-left info">
      <p>Hallo <%= userName %></p>
    </div>
  </div>
  <!-- sidebar menu: : style can be found in sidebar.less -->
  <ul class="sidebar-menu">
    <li class="header">MAIN NAVIGATION</li>
    <li class="<%= home %>">
      <a href="<%= approot %>/cashier/cashier-home.jsp">
	<i class="fa fa-home"></i> <span>Cashier</span>
      </a>
    </li>
    <li class="<%= transaction %>">
      <a class="linktransaction" href="<%= approot %>/cashier/cashier-transaction.jsp">
	<i class="fa fa-dollar"></i> <span>Transaction</span>
      </a>
    </li>
    <%if(useForRaditya.equals("1")){%>
    <li class="<%= transaction %>">
      <a class="linktransaction" href="<%= approot %>/cashier/cashier_iframe.jsp?type=1">
	<i class="fa fa-calculator"></i> <span>Simulasi Kredit</span>
      </a>
    </li>
    <li class="<%= transaction %>">
      <a class="linktransaction" href="<%= approot %>/cashier/cashier_iframe.jsp?type=2">
	<i class="fa fa-money"></i> <span>Pembayaran Kredit</span>
      </a>
    </li>
    <%}%>
	<%if (useForGB.equals("1")){%>
	<li class="stock">
      <a class="linktransfer" href="<%= approot %>/cashier/cashier-stocksearch.jsp">
	<i class="fa fa-search"></i> <span>Stock Search</span>
      </a>
    </li>
    <li class="transfer">
      <a class="linktransfer" href="<%= approot %>/cashier/cashier-transfer.jsp">
	<i class="fa fa-exchange"></i> <span>Transfer</span>
      </a>
    </li>
    <li class="service">
      <a class="linkservice" href="<%= approot %>/cashier/cashier_service.jsp">
	<i class="fa fa-power-off"></i> <span>Service</span>
      </a>
    </li>
	<li class="sales">
      <a class="linkservice" href="<%= approot %>/cashier/cashier-sales.jsp">
	<i class="fa fa-file-text"></i> <span>Sales Report</span>
      </a>
    </li>
	<%}%>
    <li class="<%= log %>">
      <a href="<%= approot %>/cashier/cashier-opening-closing-log.jsp">
	<i class="fa fa-bookmark"></i> <span>Opening & Closing Reprint</span>
      </a>
    </li>
    </li>
    <li class="memberPoin">
        <a class="linkservice" href="<%= approot %>/cashier/view-member-poin.jsp">
            <i class="fa fa-table"></i> <span>Member Poin</span>
        </a>
    </li>
    <%@page import="com.dimata.posbo.entity.admin.AppObjInfo"%>
    <% //userId
        int  appObjCodeMaintenanceCashier = AppObjInfo.composeObjCode(AppObjInfo.G1_SALES, AppObjInfo.G2_KASIR, AppObjInfo.OBJ_INVOICE); 
        boolean privMaintenanceCashier = userCashier.checkPrivilege(AppObjInfo.composeCode(appObjCodeMaintenanceCashier, AppObjInfo.COMMAND_VIEW));
        int  appObjReturn = AppObjInfo.composeObjCode(AppObjInfo.G1_SALES, AppObjInfo.G2_KASIR, AppObjInfo.OBJ_RETUR); 
        boolean privCashierReturn = userCashier.checkPrivilege(AppObjInfo.composeCode(appObjReturn, AppObjInfo.COMMAND_VIEW));
        AppUser appUserSide = new AppUser();
        try{
            appUserSide = PstAppUser.fetch(userId);
        } catch(Exception ex){}
        if (privMaintenanceCashier==true){
    %>
    <li   class="<%= maintenance %>">
      <a href="<%= approot %>/cashier/cashier-transaction-maintenance.jsp">
	<i class="fa fa-wrench"></i> <span>Transaction Maintenance</span>
      </a>
    </li>
    <% }%>
    <% if (privCashierReturn==true){%>
    <li   class="<%= maintenance %>">
      <a href="<%= approot %>/cashier/cashier-transaction-return.jsp">
	<i class="fa fa-rotate-left"></i> <span>Return Transaction</span>
      </a>
    </li>
    <%}%>
  </ul>
</section>
<!-- /.sidebar -->