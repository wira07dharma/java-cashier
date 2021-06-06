<%-- 
    Document   : cashier-sales
    Created on : Nov 20, 2018, 3:54:09 PM
    Author     : dimata005
--%>

<%@page import="com.dimata.posbo.entity.masterdata.PstShift"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.dimata.common.entity.location.Location"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.BillMainJSON"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.posbo.entity.masterdata.PstMaterial"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.posbo.entity.masterdata.Sales"%>
<%@page import="com.dimata.pos.entity.billing.BillMain"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactList"%>
<%@page import="com.dimata.common.entity.contact.PstContactClass"%>
<%@page import="com.dimata.common.entity.location.Negara"%>
<%@page import="com.dimata.pos.entity.billing.Billdetail"%>
<%@page import="com.dimata.posbo.entity.masterdata.Category"%>
<%@page import="com.dimata.pos.entity.billing.PstBillDetail"%>
<%@page import="com.dimata.common.entity.payment.PstPaymentSystem"%>
<%@page import="com.dimata.pos.entity.payment.CashReturn"%>
<%@page import="com.dimata.common.entity.payment.CurrencyType"%>
<%@page import="com.dimata.pos.entity.payment.CashPayments1"%>
<%@page import="com.dimata.pos.entity.payment.PstCashPayment1"%>
<%@page import="com.dimata.pos.entity.billing.PstBillMain"%>
<%@page import="com.dimata.posbo.entity.masterdata.Shift"%>
<%@page import="com.dimata.pos.entity.balance.PstCashCashier"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.pos.entity.balance.CashCashier"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="java.util.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@include file="../main/javainit_cashier.jsp" %>

<%//
    int command = FRMQueryString.requestCommand(request);
    long oidSupplier = FRMQueryString.requestLong(request, "supplier");
    long oidShift = FRMQueryString.requestLong(request, "shift");
    String date = FRMQueryString.requestString(request, "date");
    
    if (oidShift == -1) {
        date = Formater.formatDate(new Date(), "yyyy-MM-dd");
    }
    
    String whereOpening = "";
    if (command == Command.NONE) {
        whereOpening = "CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID] + "='" + userId + "' "
                + "AND CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID] + "='1'";
    } else {
        if (oidShift == -1) {
            whereOpening = "CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID] + "='" + userId + "' "
                    + "AND CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID] + "='1'";
        } else if (oidShift == 0) {
            whereOpening = "DATE(CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE] + ") = '" + date + "'";
        } else {
            whereOpening = "SHF." + PstShift.fieldNames[PstShift.FLD_SHIFT_ID] + "='" + oidShift + "'"
                    + " AND DATE(CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE] + ") = '" + date + "'";
        }
    }
    Vector listOpeningCashier = PstCashCashier.listCashOpening(0, 0, whereOpening, "");

    CashCashier cashCashier = new CashCashier();
    Location location = new Location();
    Shift shift = new Shift();

    Vector openingCashier = new Vector(1, 1);
    String cashCashierId = "";
    if (listOpeningCashier.size() == 1) {
        openingCashier = (Vector) listOpeningCashier.get(0);
        if (openingCashier.size() != 0) {
            cashCashier = (CashCashier) openingCashier.get(0);
            location = (Location) openingCashier.get(2);
            shift = (Shift) openingCashier.get(3);
            cashCashierId = "" + cashCashier.getOID();
        }
    } else if (listOpeningCashier.size() > 1) {
        for (int i = 0; i < listOpeningCashier.size(); i++) {
            openingCashier = (Vector) listOpeningCashier.get(i);
            cashCashier = (CashCashier) openingCashier.get(0);
            //location = (Location) openingCashier.get(2);
            //shift = (Shift) openingCashier.get(3);
            cashCashierId += cashCashierId.isEmpty() ? "":",";
            cashCashierId += "" + cashCashier.getOID();
        }
    }

    String whereDate = "";
    if (date.equals("")) {
        whereDate = " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]
                + " BETWEEN '" + Formater.formatDate(new Date(), "yyyy-MM-dd") + " 00:00:00'"
                + " AND '" + Formater.formatDate(new Date(), "yyyy-MM-dd") + " 23:59:00'";
    } else {
        whereDate = " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]
                + " BETWEEN '" + date + " 00:00:00'"
                + " AND '" + date + " 23:59:00'";
    }
    String whereClause = " CBM. " + PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID] + " IN(" + cashCashierId + ") AND " + whereDate;
%>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Sales Report - Dimata Cashier</title>
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
            .thead-light {background-color: lightblue}
            .box-body td {background-color: #eaeaea}
            tbody tr th {background-color: lightgray}
        </style>
    </head>
    <body class="skin-queentandoor sidebar-mini wysihtml5-supported sidebar-collapse fixed">
        <input type="hidden" id="approotsystem" value="<%= approot%>"><input type="hidden" id="getPaymentType" >
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
                    <h1>Dimata Cashier<small>Sales Report</small></h1>
                    <ol class="breadcrumb">
                        <li><a href="<%= approot%>/cashier/cashier-home.jsp"><i class="fa fa-home"></i> Home</a></li><li class="active">Transaction</li>
                    </ol>
                </section>
                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box box-primary">
                                <div class="box-header with-border ">
                                    <form name="frm" method="post" action="" class="form-inline">
                                        <input type="hidden" name="command" value="<%= Command.LIST %>">
                                        <div class="input-group">
                                            <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                            <input id="orSearch1" class="form-control datePicker" name="date" required="" type="text" value="<%=date.equals("") ? Formater.formatDate(new Date(), "yyyy-MM-dd") : date%>">
                                        </div>
                                        <div class="input-group">
                                            <div class="input-group-addon text-bold">Shift :</div>
                                            <select class="form-control" name="shift">
                                                <option <%=(oidShift == -1 || command == Command.NONE ? "selected":"")%> value="-1">Current Shift Opening</option>
                                                <%
                                                    Vector<Shift> listShift = PstShift.list(0, 0, "", PstShift.fieldNames[PstShift.FLD_START_TIME]);
                                                    for (Shift s : listShift) {
                                                        out.print("<option " + (oidShift == s.getOID() ? "selected":"") + " value='" + s.getOID() + "'>" + s.getName() + "</option>");
                                                    }
                                                %>
                                                <option <%=(oidShift == 0 && command == Command.LIST ? "selected":"")%> value="0">All Shift</option>
                                            </select>
                                        </div>
                                        <button id="search" type="submit" class="btn btn-primary">
                                            <i class="fa fa-search"></i>
                                            Search
                                        </button>

                                        <div class="pull-right">
                                            <div class="input-group">
                                                <div class="input-group-addon text-bold">Supplier :</div>
                                            <%
                                                Vector obj_supplier = new Vector(1, 1);
                                                Vector val_supplier = new Vector(1, 1);
                                                Vector key_supplier = new Vector(1, 1);
                                                String wh_supp = PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + " = " + PstContactClass.CONTACT_TYPE_SUPPLIER 
                                                        + " AND " + PstContactList.fieldNames[PstContactList.FLD_PROCESS_STATUS] + " != " + PstContactList.DELETE;
                                                //Vector vt_supp = PstContactList.listContactByClassType(0, 0, wh_supp, PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]);
												Vector vt_supp = PstContactList.listAll();
                                                out.println("<select id='supplier' name='supplier' class='form-control'>");
                                                if (vt_supp != null && vt_supp.size() > 0) {
                                                    int maxSupp = vt_supp.size();
                                                    for (int d = 0; d < maxSupp; d++) {
                                                        ContactList cnt = (ContactList) vt_supp.get(d);
                                                        if (oidSupplier == 0 && d == 0) {
                                                            oidSupplier = cnt.getOID();
                                                        }
                                                        out.println("<option value='" + cnt.getOID() + "'>" + cnt.getPersonName() + "</option>");
                                                    }
                                                }
                                                out.println("</select>");
                                                    //whereClause += " AND PM."+PstMaterial.fieldNames[PstMaterial.FLD_SUPPLIER_ID]+"="+oidSupplier;*/
                                            %>
                                            </div>
                                            <button id="email" type="button" class="btn btn-primary">
                                                <i class="fa fa-envelope"></i>
                                                Email
                                            </button>
                                        </div>
                                    </form>
                                </div>
                                <div class="box-body" id="CONTENT_LOAD">
                                    <div class="row">
                                        <div class="col-md-4">
                                            <%
                                                //Vector listDetailPayment = PstCashPayment1.listDetailPaymentDinamisWithReturn(0, 0, whereClause);
                                                
                                                //get payment from sales
                                                Vector listDetailPayment = PstCashPayment1.listDetailPaymentSalesWithCashReturn(0, 0, whereClause);
                                                
                                                //get payment from return
                                                Vector listDetailPaymentReturn = PstCashPayment1.listDetailPaymentSalesReturnWithCashReturn(0, 0, whereClause);
                                                HashMap<Long, Double> listPaymentReturn = new HashMap();
                                                for (int i = 0; i < listDetailPaymentReturn.size(); i++) {
                                                    Vector v = (Vector) listDetailPaymentReturn.get(i);
                                                    CashPayments1 cashPayment = (CashPayments1) v.get(0);
                                                    listPaymentReturn.put(cashPayment.getPaymentType(), cashPayment.getAmount());
                                                }
                                                
                                                //get bill return hari ini dengan id opening yg berbeda dgn yg sedang opening
                                                double paymentNegasi = 0;
                                                String whereNegasi = ""
                                                        + " DATE(" + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") = '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + "'"
                                                        + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID] + " <> " + cashCashier.getOID()
                                                        //+ " AND " + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + " IN (SELECT CASH_BILL_MAIN_ID FROM cash_bill_main WHERE CASH_CASHIER_ID = " + cashCashier.getOID() + ")"
                                                        + " AND ("
                                                        + " (" + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + " = 1 "
                                                        + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + " = 0 "
                                                        + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + " = 0 "
                                                        + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + " = 1 "
                                                        + " ) OR "
                                                        + " (" + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + " = 1 "
                                                        + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + " = 1 "
                                                        + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + " = 0 "
                                                        + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + " = 1 "
                                                        + " )"
                                                        + ")"
                                                        + "";
                                                Vector<BillMain> listBillReturnNegasi = PstBillMain.list(0, 0, whereNegasi, "");
                                                for (BillMain bm : listBillReturnNegasi) {
                                                    Vector<CashPayments1> listPayment = PstCashPayment1.list(0, 0, PstCashPayment1.fieldNames[PstCashPayment1.FLD_BILL_MAIN_ID] + " = " + bm.getOID(), "");
                                                    for (CashPayments1 cp : listPayment) {
                                                        paymentNegasi += cp.getAmount();
                                                    }
                                                }
                                            %>
                                            <h4><strong>Payment Summary</strong></h4>
                                            <table class="table  table-hover">
                                                <thead class="thead-light">
                                                    <tr>
                                                        <th scope="col">Payment</th>
                                                        <th scope="col" class="text-right">Amount</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%	double total = 0;
                                                        if (listDetailPayment.size() > 0) {
                                                            for (int i = 0; i < listDetailPayment.size(); i++) {
                                                                Vector temp = (Vector) listDetailPayment.get(i);
                                                                CashPayments1 cashPayment = (CashPayments1) temp.get(0);
                                                                CurrencyType currencyType = (CurrencyType) temp.get(1);
                                                                CashReturn cashReturn = (CashReturn) temp.get(2);

                                                                String strPaymentType = PstPaymentSystem.getTypePayment(cashPayment.getPaymentType());
                                                                //double paymentCashBillReturn = SessPaymentBillReturn.getPaymentDinamisWithReturnTransaction(0,0,whereReturnBill+" AND CP.PAY_TYPE='"+cashPayment.getPaymentType()+"' ");
                                                                double dPaymentPerType = 0.0;
                                                                double amounPayment = 0.0;
                                                                dPaymentPerType = ((cashPayment.getAmount() * cashPayment.getRate()));
                                                                amounPayment = dPaymentPerType - cashReturn.getAmount()/*-paymentCashBillReturn*/;
                                                                
                                                                //kurangi nilai payment sales dengan nilai payment dari return
                                                                double salesReturnAmount = 0;
                                                                try {
                                                                    salesReturnAmount = listPaymentReturn.get(cashPayment.getPaymentType());
                                                                    amounPayment -= salesReturnAmount;
                                                                } catch (Exception e) {
                                                                    
                                                                }
                                                                total = total + amounPayment;
                                                    %>
                                                    <tr>
                                                        <td><%=strPaymentType%></td>
                                                        <td class="text-right"><%=Formater.formatNumber(amounPayment, ".2f")%></td>
                                                    </tr>
                                                    <%
                                                            }
                                                    %>
                                                    <tr>
                                                        <th>Total</th>
                                                        <th class="text-right"><%=Formater.formatNumber(total, ".2f")%></th>
                                                    </tr>
                                                    <tr>
                                                        <th>Real Total Amount</th>
                                                        <th class="text-right"><%=Formater.formatNumber(total-paymentNegasi, ".2f")%></th>
                                                    </tr>
                                                    <%
                                                        } else {
                                                            out.print("<tr class='text-center'><td colspan='2'>No data</td></tr>");
                                                        }
                                                    %>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="col-md-4">
                                            <%
                                                Vector listGroup = PstBillDetail.listBillDetailByGroup(0, 0, whereClause, "CAT.CATEGORY_ID", 0);
                                                
                                                //get return by category
                                                Vector listReturnByGroup = PstBillDetail.listBillReturnDetailByGroup(0, 0, whereClause, "CAT.CATEGORY_ID", 0);
                                                HashMap<Long, Double> listAmountReturnByCategory = new HashMap();
                                                HashMap<Long, Double> listQtyReturnByCategory = new HashMap();
                                                for (int i = 0; i < listReturnByGroup.size(); i++) {
                                                    Vector v = (Vector) listReturnByGroup.get(i);
                                                    Category category = (Category) v.get(3);
                                                    Billdetail billDetail = (Billdetail) v.get(4);
                                                    listAmountReturnByCategory.put(category.getOID(), billDetail.getTotalAmount());
                                                    listQtyReturnByCategory.put(category.getOID(), billDetail.getQty());
                                                }
                                            %>
                                            <h4><strong>Group Stock Summary (Qty Stock)</strong></h4>
                                            <table class="table  table-hover">
                                                <thead class="thead-light">
                                                    <tr>
                                                        <th scope="col">Group</th>
                                                        <th scope="col" class="text-right">Qty</th>
                                                        <th scope="col" class="text-right">Amount</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%	double totalGroup = 0;
                                                        double totalQty = 0;
                                                        if (listGroup.size() > 0) {
                                                            for (int i = 0; i < listGroup.size(); i++) {
                                                                Vector temp = (Vector) listGroup.get(i);
                                                                Category category = (Category) temp.get(3);
                                                                Billdetail billDetail = (Billdetail) temp.get(4);

                                                                double amount = billDetail.getTotalAmount();
                                                                double qty = billDetail.getQty();
                                                                //kurangi dengan nilai return
                                                                try {
                                                                    amount -= listAmountReturnByCategory.get(category.getOID());
                                                                    qty -= listQtyReturnByCategory.get(category.getOID());
                                                                } catch (Exception e) {
                                                                    
                                                                }
                                                                totalGroup = totalGroup + amount;
                                                                totalQty = totalQty + qty;
                                                    %>
                                                    <tr>
                                                        <td><%=category.getName()%></td>
                                                        <td class="text-right"><%=qty%></td>
                                                        <td class="text-right"><%=Formater.formatNumber(amount, ".2f")%></td>
                                                    </tr>
                                                    <%
                                                            }
                                                    %>
                                                    <tr>
                                                        <th>Total</th>
                                                        <th class="text-right"><%=totalQty%></th>
                                                        <th class="text-right"><%=Formater.formatNumber(totalGroup, ".2f")%></th>
                                                    </tr>
                                                    <%
                                                        } else {
                                                            out.print("<tr class='text-center'><td colspan='3'>No data</td></tr>");
                                                        }
                                                    %>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="col-md-4">
                                            <%
                                                Vector listSales = PstBillDetail.listBillDetailByGroup(0, 0, whereClause, "", 0);
                                                Vector listReturnBySalesPerson = PstBillDetail.listBillReturnDetailByGroup(0, 0, whereClause, "", 0);
                                                HashMap<Long, Double> listAmountReturnBySalesPerson = new HashMap();
                                                HashMap<Long, Double> listQtyReturnBySalesPerson = new HashMap();
                                                for (int i = 0; i < listReturnBySalesPerson.size(); i++) {
                                                    Vector v = (Vector) listReturnBySalesPerson.get(i);
                                                    Sales sales = (Sales) v.get(1);
                                                    Billdetail billDetail = (Billdetail) v.get(4);
                                                    listAmountReturnBySalesPerson.put(sales.getOID(), billDetail.getTotalAmount());
                                                    listQtyReturnBySalesPerson.put(sales.getOID(), billDetail.getQty());
                                                }
                                            %>
                                            <h4><strong>Sales Person Summary (Qty Stock)</strong></h4>
                                            <table class="table  table-hover">
                                                <thead class="thead-light">
                                                    <tr>
                                                        <th scope="col">Sales Person</th>
                                                        <th scope="col" class="text-right">Qty</th>
                                                        <th scope="col" class="text-right">Amount</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%	double totalSales = 0;
                                                        totalQty = 0;
                                                        if (listSales.size() > 0) {
                                                            for (int i = 0; i < listSales.size(); i++) {
                                                                Vector temp = (Vector) listSales.get(i);
                                                                Sales sales = (Sales) temp.get(1);
                                                                Billdetail billDetail = (Billdetail) temp.get(4);
                                                                
                                                                double amount = billDetail.getTotalAmount();
                                                                double qty = billDetail.getQty();
                                                                //kurangi dengan nilai return
                                                                try {
                                                                    amount -= listAmountReturnBySalesPerson.get(sales.getOID());
                                                                    qty -= listQtyReturnBySalesPerson.get(sales.getOID());
                                                                } catch (Exception e) {
                                                                    
                                                                }
                                                                totalSales = totalSales + amount;
                                                                totalQty = totalQty + qty;
                                                    %>
                                                    <tr>
                                                        <td><%=sales.getName()%></td>
                                                        <td class="text-right"><%=qty%></td>
                                                        <td class="text-right"><%=Formater.formatNumber(amount, ".2f")%></td>
                                                    </tr>
                                                    <%
                                                            }
                                                    %>
                                                    <tr>
                                                        <th>Total</th>
                                                        <th class="text-right"><%=totalQty%></th>
                                                        <th class="text-right"><%=Formater.formatNumber(totalSales, ".2f")%></th>
                                                    </tr>
                                                    <%
                                                        } else {
                                                            out.print("<tr class='text-center'><td colspan='3'>No data</td></tr>");
                                                        }
                                                    %>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <%
                                                Vector listCountry = PstBillDetail.listBillDetailByGroup(0, 0, whereClause, "NG.ID_NEGARA", 1);
                                                Vector listReturnByCountry = PstBillDetail.listBillReturnDetailByGroup(0, 0, whereClause, "NG.ID_NEGARA", 1);
                                                HashMap<Long, Double> listAmountReturnByCountry = new HashMap();
                                                HashMap<Long, Double> listQtyReturnByCountry = new HashMap();
                                                for (int i = 0; i < listReturnByCountry.size(); i++) {
                                                    Vector v = (Vector) listReturnByCountry.get(i);
                                                    Negara negara = (Negara) v.get(0);
                                                    Billdetail billDetail = (Billdetail) v.get(4);
                                                    listAmountReturnByCountry.put(negara.getOID(), billDetail.getTotalAmount());
                                                    listQtyReturnByCountry.put(negara.getOID(), billDetail.getQty());
                                                }
                                            %>
                                            <h4><strong>Customer Country Summary (Qty Invoices)</strong></h4>
                                            <table class="table  table-hover">
                                                <thead class="thead-light">
                                                    <tr>
                                                        <th scope="col">Country</th>
                                                        <th scope="col" class="text-right">Qty</th>
                                                        <th scope="col" class="text-right">Amount</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%	double totalCountry = 0;
                                                        totalQty = 0;
                                                        if (listGroup.size() > 0) {
                                                            for (int i = 0; i < listCountry.size(); i++) {
                                                                Vector temp = (Vector) listCountry.get(i);
                                                                Negara negara = (Negara) temp.get(0);
                                                                Billdetail billDetail = (Billdetail) temp.get(4);
                                                                
                                                                double amount = billDetail.getTotalAmount();
                                                                double qty = billDetail.getQty();
                                                                //kurangi dengan nilai return
                                                                try {
                                                                    amount -= listAmountReturnByCountry.get(negara.getOID());
                                                                    qty -= listQtyReturnByCountry.get(negara.getOID());
                                                                } catch (Exception e) {
                                                                    
                                                                }
                                                                totalCountry = totalCountry + amount;
                                                                totalQty = totalQty + qty;
                                                    %>
                                                    <tr>
                                                        <td><%=negara.getNmNegara()%></td>
                                                        <td class="text-right"><%=qty%></td>
                                                        <td class="text-right"><%=Formater.formatNumber(amount, ".2f")%></td>
                                                    </tr>
                                                    <%
                                                            }
                                                    %>
                                                    <tr>
                                                        <th>Total</th>
                                                        <th class="text-right"><%=totalQty%></th>
                                                        <th class="text-right"><%=Formater.formatNumber(totalCountry, ".2f")%></th>
                                                    </tr>
                                                    <%
                                                        } else {
                                                            out.print("<tr class='text-center'><td colspan='3'>No data</td></tr>");
                                                        }
                                                    %>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="col-md-4">
                                            <%
                                                Vector listGender = PstBillDetail.listBillDetailByGroup(0, 0, whereClause, "CBM.GENDER", 1);
                                                Vector listReturnGender = PstBillDetail.listBillReturnDetailByGroup(0, 0, whereClause, "CBM.GENDER", 1);
                                                HashMap<Integer, Double> listAmountReturnByGender = new HashMap();
                                                HashMap<Integer, Double> listQtyReturnByGender = new HashMap();
                                                for (int i = 0; i < listReturnGender.size(); i++) {
                                                    Vector v = (Vector) listReturnGender.get(i);
                                                    BillMain billMain = (BillMain) v.get(5);
                                                    Billdetail billDetail = (Billdetail) v.get(4);
                                                    listAmountReturnByGender.put(billMain.getGender(), billDetail.getTotalAmount());
                                                    listQtyReturnByGender.put(billMain.getGender(), billDetail.getQty());
                                                }
                                            %>
                                            <h4><strong>Customer Gender Summary (Qty Invoices)</strong></h4>
                                            <table class="table  table-hover">
                                                <thead class="thead-light">
                                                    <tr>
                                                        <th scope="col">Gender</th>
                                                        <th scope="col" class="text-right">Qty</th>
                                                        <th scope="col" class="text-right">Amount</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%	double totalGender = 0;
                                                        totalQty = 0;
                                                        if (listGender.size() > 0) {
                                                            for (int i = 0; i < listGender.size(); i++) {
                                                                Vector temp = (Vector) listGender.get(i);
                                                                BillMain billMain = (BillMain) temp.get(5);
                                                                Billdetail billDetail = (Billdetail) temp.get(4);

                                                                double amount = billDetail.getTotalAmount();
                                                                double qty = billDetail.getQty();
                                                                //kurangi dengan nilai return
                                                                try {
                                                                    amount -= listAmountReturnByGender.get(billMain.getGender());
                                                                    qty -= listQtyReturnByGender.get(billMain.getGender());
                                                                } catch (Exception e) {
                                                                    
                                                                }
                                                                totalGender = totalGender + amount;
                                                                totalQty = totalQty + qty;
                                                    %>
                                                    <tr>
                                                        <td><%=billMain.getGender() == 0 ? "Men" : "Ladies"%></td>
                                                        <td class="text-right"><%=qty%></td>
                                                        <td class="text-right"><%=Formater.formatNumber(amount, ".2f")%></td>
                                                    </tr>
                                                    <%
                                                            }
                                                    %>
                                                    <tr>
                                                        <th>Total</th>
                                                        <th class="text-right"><%=totalQty%></th>
                                                        <th class="text-right"><%=Formater.formatNumber(totalGender, ".2f")%></th>
                                                    </tr>
                                                    <%
                                                        } else {
                                                            out.print("<tr class='text-center'><td colspan='3'>No data</td></tr>");
                                                        }
                                                    %>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div class="col-md-4">
                                            <%
                                                Vector listPIC = PstBillDetail.listBillDetailByGroup(0, 0, whereClause, "USR.USER_ID", 1);
                                                Vector listReturnByPIC = PstBillDetail.listBillReturnDetailByGroup(0, 0, whereClause, "USR.USER_ID", 1);
                                                HashMap<Long, Double> listAmountReturnByPic = new HashMap();
                                                HashMap<Long, Double> listQtyReturnByPic = new HashMap();
                                                for (int i = 0; i < listReturnByPIC.size(); i++) {
                                                    Vector v = (Vector) listReturnByPIC.get(i);
                                                    AppUser appUser = (AppUser) v.get(2);
                                                    Billdetail billDetail = (Billdetail) v.get(4);
                                                    listAmountReturnByPic.put(appUser.getOID(), billDetail.getTotalAmount());
                                                    listQtyReturnByPic.put(appUser.getOID(), billDetail.getQty());
                                                }
                                            %>
                                            <h4><strong>PIC Summary (Qty Invoices)</strong></h4>
                                            <table class="table  table-hover">
                                                <thead class="thead-light">
                                                    <tr>
                                                        <th scope="col">Cashier</th>
                                                        <th scope="col" class="text-right">Qty</th>
                                                        <th scope="col" class="text-right">Amount</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%	double totalPIC = 0;
                                                        totalQty = 0;
                                                        if (listGroup.size() > 0) {
                                                            for (int i = 0; i < listPIC.size(); i++) {
                                                                Vector temp = (Vector) listPIC.get(i);
                                                                AppUser appUser = (AppUser) temp.get(2);
                                                                Billdetail billDetail = (Billdetail) temp.get(4);
                                                                
                                                                double amount = billDetail.getTotalAmount();
                                                                double qty = billDetail.getQty();
                                                                //kurangi dengan nilai return
                                                                try {
                                                                    amount -= listAmountReturnByPic.get(appUser.getOID());
                                                                    qty -= listQtyReturnByPic.get(appUser.getOID());
                                                                } catch (Exception e) {
                                                                    
                                                                }
                                                                totalPIC = totalPIC + amount;
                                                                totalQty = totalQty + qty;
                                                    %>
                                                    <tr>
                                                        <td><%=appUser.getFullName()%></td>
                                                        <td class="text-right"><%=qty%></td>
                                                        <td class="text-right"><%=Formater.formatNumber(amount, ".2f")%></td>
                                                    </tr>
                                                    <%
                                                            }
                                                    %>
                                                    <tr>
                                                        <th>Total</th>
                                                        <th class="text-right"><%=totalQty%></th>
                                                        <th class="text-right"><%=Formater.formatNumber(totalPIC, ".2f")%></th>
                                                    </tr>
                                                    <%
                                                        } else {
                                                            out.print("<tr class='text-center'><td colspan='3'>No data</td></tr>");
                                                        }
                                                    %>
                                                </tbody>
                                            </table>
                                        </div>
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

            $(".datePicker").datepicker({
                format: 'yyyy-mm-dd',
            }).on("changeDate", function (ev) {
                $(this).datepicker('hide');
            });

            $("#email").click(function () {
                var supplier = $("#supplier option:selected").text();
                var r = confirm("Send Email to " + supplier + "?");
                if (r == true) {
                    var command = "<%= Command.NONE%>";
                    var supplierId = $("#supplier").val();
                    var loadtype = "sendEmailSupplier";
                    var date = $("#orSearch1").val();
					var locationId = "<%= location.getOID()%>"
                    var dataSend = "command=" + command + "&loadtype=" + loadtype + "&SUPPLIER_ID=" + supplierId + "&DATE=" + date + "&LOCATION_ID=" +locationId;
                    $.ajax({
                        type: "POST",
                        url: "<%= approot%>/TransactionCashierHandler",
                        data: dataSend,
                        chace: false,
                        dataType: "json",
                        success: function (data) {
                        }
                    }).done(function (data) { //alert("done");
                        var result = data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>;
                        if (result === "Email Sucseed") {
                            alert("Email Sent");
                        } else {
                            alert(result);
                        }
                    })
                }
            });
        </script>
    </body>
</html>

