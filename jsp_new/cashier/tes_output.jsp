<%-- 
    Document   : tes_output
    Created on : Dec 3, 2018, 9:37:59 AM
    Author     : dimata005
--%>

<%@page import="com.dimata.pos.entity.masterCashier.CashMaster"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.posbo.entity.masterdata.Sales"%>
<%@page import="com.dimata.posbo.entity.masterdata.Material"%>
<%@page import="com.dimata.posbo.entity.masterdata.PstCategory"%>
<%@page import="com.dimata.posbo.entity.masterdata.Category"%>
<%@page import="com.dimata.pos.entity.billing.PstBillDetail"%>
<%@page import="com.dimata.common.entity.payment.PstPaymentSystem"%>
<%@page import="com.dimata.pos.entity.payment.PstCashPayment1"%>
<%@page import="com.dimata.pos.entity.billing.PstBillMain"%>
<%@page import="java.util.Date"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain"%>
<%@page import="com.dimata.pos.entity.balance.PstBalance"%>
<%@page import="com.dimata.pos.entity.balance.PstCashCashier"%>
<%@page import="com.dimata.posbo.entity.masterdata.Shift"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.common.entity.location.Location"%>
<%@page import="com.dimata.pos.entity.balance.CashCashier"%>
<%@page import="com.dimata.pos.entity.billing.Billdetail"%>
<%@page import="com.dimata.common.entity.system.PstSystemProperty"%>
<%@include file="../main/javainit_cashier.jsp" %>
<%
    CashCashier cashCashier = new CashCashier();
	CashMaster cashMaster = new CashMaster();
    Location location = new Location();
    Vector listBalance = new Vector(1,1);
    Shift shift = new Shift();    
    Vector listOpeningCashier =  PstCashCashier.listCashOpening(0, 0, 
	    "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID]+"='"+userId+"' "
	    + "AND CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID]+"='1'", "");   
    Vector openingCashier = new Vector(1,1);
    Vector listBillMain = new Vector(1,1);
    if(listOpeningCashier.size() != 0){
	openingCashier = (Vector) listOpeningCashier.get(0);
	if(openingCashier.size() != 0 ){
	    cashCashier = (CashCashier) openingCashier.get(0);
		cashMaster = (CashMaster) openingCashier.get(1);
	    location = (Location) openingCashier.get(2);
	    shift = (Shift) openingCashier.get(3);
	    listBalance = PstBalance.list(0, 0, ""+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='0' "
		    + "AND "+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+504404725914748890L+"'", "");
	}
    }
	String shoppingBagCategory = PstSystemProperty.getValueByName("CASHIER_SHOPPING_BAG_CATEGORY_ID"); 
	/* per shift */
    String whereClause = "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID]+"="+504404725914748890L
                        +" AND ("
                                + "("
                                    + "CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                    + "AND CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ") OR ("
                                    + "CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
                                    + "AND CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ") OR ("
                                    + "CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                    + "AND CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ")"
                            + ")";
    int invoicesPerShift = PstBillMain.getCountPerCashier(whereClause);
    
    String wherePerShift = "CP."+PstCashPayment1.fieldNames[PstCashPayment1.FLD_PAY_DATETIME]+" BETWEEN '"
                        + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd HH:mm:ss") + "' AND '"
                        + Formater.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") +"'";
    String whereCash = "PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"= 0"
			+ " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"= 0 "
			+ " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"= 0"
			+ " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"= 1"
			+ " AND CP."+PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID]+" = 1";
    String whereCC = "PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"= 0"
			+ " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"= 1 "
			+ " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"= 0"
			+ " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"= 0"
			+ " AND CP."+PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID]+" = 1";
    String whereDebit = "PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"= 0"
			+ " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"= 0 "
			+ " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"= 0"
			+ " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"= 2"
			+ " AND CP."+PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID]+" = 1";
	String whereRefund = "PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"= 0"
			+ " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"= 0 "
			+ " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"= 0"
			+ " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"= 3"
			+ " AND CP."+PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID]+" = 1";
    double cashPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift+" AND "+whereCash);
    double ccPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift+" AND "+whereCC);
    double debitPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift+" AND "+whereDebit);
    double voucherPerShift = 0;
	double refundPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift+" AND "+whereRefund);;
    double totalPerShift = cashPerShift+ccPerShift+debitPerShift+refundPerShift;
    String whereGroupPerShift = " CBM."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" BETWEEN '"+Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd HH:mm:ss")
				+"' AND '"+Formater.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")+"' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] +" != "+shoppingBagCategory;
    Vector listGroupPerShift = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupPerShift, "CAT.CATEGORY_ID", 0);
	String whereGroupPerShiftShoppingBag = " CBM."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" BETWEEN '"+Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd HH:mm:ss")
				+"' AND '"+Formater.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")+"' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] +" = "+shoppingBagCategory;
    Vector listGroupPerShiftShoppingBag = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupPerShiftShoppingBag, "PM.NAME", 0);
	
	/* end per shift */
	
	/* daily report */
	
	String whereCashCashier = PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE] + " BETWEEN '"+Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd")+" 00:00:00' "
								+ " AND '"+Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd")+" 23:59:00' "
								+ " AND "+PstCashCashier.fieldNames[PstCashCashier.FLD_CASHMASTER_ID]+"="+cashMaster.getOID();
	Vector listDailyCashCashier = PstCashCashier.list(0, 0, whereCashCashier, "");
	String inCashCashierDaily = "";
	if (listDailyCashCashier.size() > 0){
		for (int i = 0; i < listDailyCashCashier.size();i++){
			CashCashier cashCashier1 = (CashCashier) listDailyCashCashier.get(i);
			inCashCashierDaily += ",'"+cashCashier1.getOID()+"'";
		}
		if (inCashCashierDaily.length() > 0){
			inCashCashierDaily = inCashCashierDaily.substring(1);
		}
	}
	String whereClauseDaily = "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID]+" IN ("+inCashCashierDaily+")"
                        +" AND ("
                                + "("
                                    + "CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                    + "AND CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ") OR ("
                                    + "CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
                                    + "AND CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ") OR ("
                                    + "CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                    + "AND CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ")"
                            + ")";
    int invoicesPerShiftDaily = PstBillMain.getCountPerCashier(whereClauseDaily);
	String whereDaily = "CP."+PstCashPayment1.fieldNames[PstCashPayment1.FLD_PAY_DATETIME]+" BETWEEN '"
                        + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 00:00:00' AND '"
                        + Formater.formatDate(new Date(), "yyyy-MM-dd") +" 23:59:00'";
    double cashDaily = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereDaily+" AND "+whereCash);
    double ccDaily = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereDaily+" AND "+whereCC);
    double debitDaily = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereDaily+" AND "+whereDebit);
    double voucherDaily = 0;
    double totalDaily = cashDaily+ccDaily+debitDaily;
	String whereGroupDaily = " CBM."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" BETWEEN '"+Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd")
				+" 00:00:00' AND '"+Formater.formatDate(new Date(), "yyyy-MM-dd")+" 23:59:00' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] +" != "+shoppingBagCategory;
    Vector listGroupDaily = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupDaily, "CAT.CATEGORY_ID", 0);
	String whereGroupDailyShoppingBag = " CBM."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" BETWEEN '"+Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd")
				+" 00:00:00' AND '"+Formater.formatDate(new Date(), "yyyy-MM-dd")+" 23:59:00' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] +" = "+shoppingBagCategory;
    Vector listGroupDailyShoppingBag = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupDailyShoppingBag, "PM.NAME", 0);
	Vector listSalesDaily = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupDaily, "SLS.SALES_CODE", 0);
	Vector listSupplierDaily = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupDaily, "CL.PERSON_NAME", 0);
	
	/* end daily report */
	
	/* mtd report */
	
	String whereCashCashierMTD = PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE] + " BETWEEN '"+Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM")+"-01 00:00:00' "
								+ " AND '"+Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd")+" 23:59:00' "
								+ " AND "+PstCashCashier.fieldNames[PstCashCashier.FLD_CASHMASTER_ID]+"="+cashMaster.getOID();
	Vector listDailyCashCashierMTD = PstCashCashier.list(0, 0, whereCashCashierMTD, "");
	String inCashCashierDailyMTD = "";
	if (listDailyCashCashierMTD.size() > 0){
		for (int i = 0; i < listDailyCashCashierMTD.size();i++){
			CashCashier cashCashier1 = (CashCashier) listDailyCashCashierMTD.get(i);
			inCashCashierDailyMTD += ",'"+cashCashier1.getOID()+"'";
		}
		if (inCashCashierDaily.length() > 0){
			inCashCashierDailyMTD = inCashCashierDailyMTD.substring(1);
		}
	}
	String whereClauseMTD = "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID]+" IN ("+inCashCashierDailyMTD+")"
                        +" AND ("
                                + "("
                                    + "CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                    + "AND CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ") OR ("
                                    + "CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
                                    + "AND CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ") OR ("
                                    + "CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                                    + "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                    + "AND CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ")"
                            + ")";
    int invoicesMTD = PstBillMain.getCountPerCashier(whereClauseMTD);
	String whereMTD = "CP."+PstCashPayment1.fieldNames[PstCashPayment1.FLD_PAY_DATETIME]+" BETWEEN '"
                        + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM") + "-01 00:00:00' AND '"
                        + Formater.formatDate(new Date(), "yyyy-MM-dd") +" 23:59:00'";
    double cashMTD = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereMTD+" AND "+whereCash);
    double ccMTD = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereMTD+" AND "+whereCC);
    double debitMTD = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereMTD+" AND "+whereDebit);
    double voucherMTD = 0;
    double totalMTD = cashMTD+ccMTD+debitMTD;
	String whereGroupMTD = " CBM."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" BETWEEN '"+Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM")
				+"-01 00:00:00' AND '"+Formater.formatDate(new Date(), "yyyy-MM-dd")+" 23:59:00' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] +" != "+shoppingBagCategory;
    Vector listGroupMTD = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupMTD, "CAT.CATEGORY_ID", 0);
	String whereGroupMTDShoppingBag = " CBM."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" BETWEEN '"+Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM")
				+"-01 00:00:00' AND '"+Formater.formatDate(new Date(), "yyyy-MM-dd")+" 23:59:00' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] +" = "+shoppingBagCategory;
    Vector listGroupMTDShoppingBag = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupMTDShoppingBag, "PM.NAME", 0);
	Vector listSalesMTD = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupMTD, "SLS.SALES_CODE", 0);
	Vector listSupplierMTD = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupMTD, "CL.PERSON_NAME", 0);
	
	/* end daily report */
	
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table style="border: 0"><tr><td>*** Daily Sales ***</td></tr><tr><td>&nbsp;</td></tr><tr><td><div>**** SHIFT REPORT ****</div></td></tr><tr><td><div>(1) - Kartika</div></td></tr><tr><td>Sales Time : 10:07:33 - 22:01:08</td></tr><tr><td>Total Invoices : 10</td></tr><tr><td>&nbsp;</td></tr><tr><td>Payment Summary:</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Cash&nbsp;&nbsp;:&nbsp;&nbsp;5,614,500</td></tr><tr><td>Credit Card&nbsp;&nbsp;:&nbsp;&nbsp;0</td></tr><tr><td>Debit Card&nbsp;&nbsp;:&nbsp;&nbsp;1,893,100</td></tr><tr><td>Voucher&nbsp;&nbsp;:&nbsp;&nbsp;0</td></tr><tr><td>Exchange Value&nbsp;&nbsp;:&nbsp;&nbsp;0</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Total Payment&nbsp;&nbsp;:&nbsp;&nbsp;7,507,600</td></tr><tr><td>Total Real Payment&nbsp;&nbsp;:&nbsp;&nbsp;7,507,600</td></tr><tr><td>&nbsp;</td></tr><tr><td>Product Category Summary :</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>FOOTWEAR&nbsp;&nbsp;:&nbsp;&nbsp;(5)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;5,420,100</td></tr><tr><td>-&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;174,500</td></tr><tr><td> MEN L/S TSHIRT&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;470,000</td></tr><tr><td>Man Surfwear&nbsp;&nbsp;:&nbsp;&nbsp;(2)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;548,000</td></tr><tr><td>MWS&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;895,000</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Total Product Qty&nbsp;&nbsp;:&nbsp;&nbsp;10</td></tr><tr><td>Total Product Amount&nbsp;&nbsp;:&nbsp;&nbsp;7,507,600</td></tr><tr><td>Average Sales&nbsp;&nbsp;:&nbsp;&nbsp;750,760</td></tr><tr><td>&nbsp;</td></tr><tr><td>Shopping Bag Summary:</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>SHOPPING BAG L&nbsp;&nbsp;:&nbsp;&nbsp;4</td></tr><tr><td>SHOPPING BAG M&nbsp;&nbsp;:&nbsp;&nbsp;1</td></tr><tr><td>SHOPPING BAG S&nbsp;&nbsp;:&nbsp;&nbsp;5</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Total Shopping Bag Qty&nbsp;&nbsp;:&nbsp;&nbsp;10</td></tr><tr><td>&nbsp;</td></tr><tr><td>***********************************************</td></tr><tr><td>&nbsp;</td></tr><tr><td><div>**** DAILY REPORT ****</div></td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>&nbsp;</td></tr><tr><td>Total Invoices : 10</td></tr><tr><td>&nbsp;</td></tr><tr><td>Cash&nbsp;&nbsp;:&nbsp;&nbsp;5,614,500</td></tr><tr><td>Credit Card&nbsp;&nbsp;:&nbsp;&nbsp;0</td></tr><tr><td>Debit Card&nbsp;&nbsp;:&nbsp;&nbsp;1,893,100</td></tr><tr><td>Voucher&nbsp;&nbsp;:&nbsp;&nbsp;0</td></tr><tr><td>Exchange Value&nbsp;&nbsp;:&nbsp;&nbsp;0</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Total Payment&nbsp;&nbsp;:&nbsp;&nbsp;7,507,600</td></tr><tr><td>Total Real Payment&nbsp;&nbsp;:&nbsp;&nbsp;7,507,600</td></tr><tr><td>&nbsp;</td></tr><tr><td>Daily Product Category Summary :</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>FOOTWEAR&nbsp;&nbsp;:&nbsp;&nbsp;(5)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;5,420,100</td></tr><tr><td>-&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;174,500</td></tr><tr><td> MEN L/S TSHIRT&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;470,000</td></tr><tr><td>Man Surfwear&nbsp;&nbsp;:&nbsp;&nbsp;(2)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;548,000</td></tr><tr><td>MWS&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;895,000</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Total Product Qty&nbsp;&nbsp;:&nbsp;&nbsp;10</td></tr><tr><td>Total Product Amount&nbsp;&nbsp;:&nbsp;&nbsp;7,507,600</td></tr><tr><td>Average Daily Sales&nbsp;&nbsp;:&nbsp;&nbsp;750,760</td></tr><tr><td>&nbsp;</td></tr><tr><td>Shopping Bag Summary:</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>SHOPPING BAG L&nbsp;&nbsp;:&nbsp;&nbsp;4</td></tr><tr><td>SHOPPING BAG M&nbsp;&nbsp;:&nbsp;&nbsp;1</td></tr><tr><td>SHOPPING BAG S&nbsp;&nbsp;:&nbsp;&nbsp;5</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Total Daily Shopping Bag Qty&nbsp;&nbsp;:&nbsp;&nbsp;10</td></tr><tr><td>&nbsp;</td></tr><tr><td>Daily Sales Person Summary:</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Ngurah&nbsp;&nbsp;:&nbsp;&nbsp;(2)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;2,394,000</td></tr><tr><td>Sam&nbsp;&nbsp;:&nbsp;&nbsp;(5)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;3,120,100</td></tr><tr><td>Anggi&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;174,500</td></tr><tr><td>Renal&nbsp;&nbsp;:&nbsp;&nbsp;(2)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;1,819,000</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Total Sales Person Qty&nbsp;&nbsp;:&nbsp;&nbsp;10</td></tr><tr><td>Total Sales Person&nbsp;&nbsp;:&nbsp;&nbsp;7,507,600</td></tr><tr><td>&nbsp;</td></tr><tr><td>Daily Sales Time Summary :</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td> Time / Inv# / Qty / Total</td></tr><tr><td>14:00 - 15:00 :&nbsp;&nbsp;1&nbsp;&nbsp;/&nbsp;&nbsp;1&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;470,000</td></tr><tr><td>16:00 - 17:00 :&nbsp;&nbsp;2&nbsp;&nbsp;/&nbsp;&nbsp;2&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;1,523,500</td></tr><tr><td>20:00 - 21:00 :&nbsp;&nbsp;2&nbsp;&nbsp;/&nbsp;&nbsp;2&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;2,394,000</td></tr><tr><td>21:00 - 22:00 :&nbsp;&nbsp;6&nbsp;&nbsp;/&nbsp;&nbsp;6&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;3,619,100</td></tr><tr><td>&nbsp;</td></tr><tr><td>Daily Sales Supplier Summary :</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>DEUS&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;470,000</td></tr><tr><td>QUIKSILVER&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;499,000</td></tr><tr><td>RIP CURL&nbsp;&nbsp;:&nbsp;&nbsp;(3)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;722,500</td></tr><tr><td>VANS&nbsp;&nbsp;:&nbsp;&nbsp;(4)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;4,921,100</td></tr><tr><td>VOLCOM&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;895,000</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Total Supplier Qty&nbsp;&nbsp;:&nbsp;&nbsp;10</td></tr><tr><td>Total Supplier Amount&nbsp;&nbsp;:&nbsp;&nbsp;7,507,600</td></tr><tr><td>&nbsp;</td></tr><tr><td>***********************************************</td></tr><tr><td>&nbsp;</td></tr><tr><td><div>**** MONTH TO DATE REPORT ****</div></td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>&nbsp;</td></tr><tr><td>MTD Invoices : 74</td></tr><tr><td>&nbsp;</td></tr><tr><td>Cash&nbsp;&nbsp;:&nbsp;&nbsp;36,938,800</td></tr><tr><td>Credit Card&nbsp;&nbsp;:&nbsp;&nbsp;3,461,000</td></tr><tr><td>Debit Card&nbsp;&nbsp;:&nbsp;&nbsp;82,378,700</td></tr><tr><td>Voucher&nbsp;&nbsp;:&nbsp;&nbsp;0</td></tr><tr><td>Exchange Value&nbsp;&nbsp;:&nbsp;&nbsp;699,000</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Total MTD Payment&nbsp;&nbsp;:&nbsp;&nbsp;123,477,500</td></tr><tr><td>Total Real MTD Payment&nbsp;&nbsp;:&nbsp;&nbsp;122,778,500</td></tr><tr><td>&nbsp;</td></tr><tr><td>MTD Product Category Summary :</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>FOOTWEAR&nbsp;&nbsp;:&nbsp;&nbsp;(58)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;66,517,100</td></tr><tr><td>Accessories&nbsp;&nbsp;:&nbsp;&nbsp;(9)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;2,983,300</td></tr><tr><td>Apparel&nbsp;&nbsp;:&nbsp;&nbsp;(17)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;3,280,600</td></tr><tr><td>Watches&nbsp;&nbsp;:&nbsp;&nbsp;(2)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;2,298,000</td></tr><tr><td>-&nbsp;&nbsp;:&nbsp;&nbsp;(41)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;14,893,000</td></tr><tr><td>MEN GRAPHIC TSHIRT&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;470,000</td></tr><tr><td> MEN GRAPHIC TSHIRT&nbsp;&nbsp;:&nbsp;&nbsp;(10)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;4,050,000</td></tr><tr><td> MEN CLASSIC TSHIRT&nbsp;&nbsp;:&nbsp;&nbsp;(8)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;2,960,000</td></tr><tr><td> MEN LONG PANT&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;750,000</td></tr><tr><td> MEN MOTO JERSEY&nbsp;&nbsp;:&nbsp;&nbsp;(2)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;1,360,000</td></tr><tr><td> KIDS GRAPHIC T-SHIRT&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;270,000</td></tr><tr><td> KIDS CLASSIC TSHIRT&nbsp;&nbsp;:&nbsp;&nbsp;(8)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;2,160,000</td></tr><tr><td> TRUCKER CAP&nbsp;&nbsp;:&nbsp;&nbsp;(5)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;1,545,000</td></tr><tr><td> MEN L/S TSHIRT&nbsp;&nbsp;:&nbsp;&nbsp;(3)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;1,410,000</td></tr><tr><td> MEN SWEAT SHIRT&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;720,000</td></tr><tr><td>Eyeglasses&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;650,000</td></tr><tr><td>Man Surfwear&nbsp;&nbsp;:&nbsp;&nbsp;(5)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;2,145,500</td></tr><tr><td>Girl Surfwear&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;399,000</td></tr><tr><td>SANDAL&nbsp;&nbsp;:&nbsp;&nbsp;(6)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;855,000</td></tr><tr><td>SWEATER&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;92,400</td></tr><tr><td>TOP&nbsp;&nbsp;:&nbsp;&nbsp;(8)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;736,600</td></tr><tr><td>POUCH&CASES&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;128,000</td></tr><tr><td>MBM&nbsp;&nbsp;:&nbsp;&nbsp;(3)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;1,245,000</td></tr><tr><td>MSH&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;525,000</td></tr><tr><td>MSN&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;445,000</td></tr><tr><td>MWS&nbsp;&nbsp;:&nbsp;&nbsp;(3)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;2,555,000</td></tr><tr><td>REGULAR TEES&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;395,000</td></tr><tr><td>SLIM FIT&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;425,000</td></tr><tr><td>CUSTOM TEES&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;495,000</td></tr><tr><td>SOCK&nbsp;&nbsp;:&nbsp;&nbsp;(2)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;270,000</td></tr><tr><td>WALKSHORT &nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;1,150,000</td></tr><tr><td>BELT&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;325,000</td></tr><tr><td>SHORT SHORT&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;298,000</td></tr><tr><td>TRUCKER CAP&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;299,000</td></tr><tr><td>SS TEE&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;599,000</td></tr><tr><td>WOMEN GRAPHIC TSHIRT&nbsp;&nbsp;:&nbsp;&nbsp;(2)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;840,000</td></tr><tr><td> BEANIE&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;295,000</td></tr><tr><td>MLS&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;525,000</td></tr><tr><td> MEN POCKET TSHIRT&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;420,000</td></tr><tr><td>ESS LADIES HAT&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;198,000</td></tr><tr><td> WOMEN KNITWEAR&nbsp;&nbsp;:&nbsp;&nbsp;(2)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;1,500,000</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>MTD Product Qty&nbsp;&nbsp;:&nbsp;&nbsp;216</td></tr><tr><td>MTD Product Amount&nbsp;&nbsp;:&nbsp;&nbsp;123,477,500</td></tr><tr><td>Average MTD Sales&nbsp;&nbsp;:&nbsp;&nbsp;10,289,792</td></tr><tr><td>&nbsp;</td></tr><tr><td>MTD Shopping Bag Summary:</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>SHOPPING BAG L&nbsp;&nbsp;:&nbsp;&nbsp;60</td></tr><tr><td>SHOPPING BAG M&nbsp;&nbsp;:&nbsp;&nbsp;29</td></tr><tr><td>SHOPPING BAG S&nbsp;&nbsp;:&nbsp;&nbsp;64</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Total MTD Shopping Bag Qty&nbsp;&nbsp;:&nbsp;&nbsp;153</td></tr><tr><td>&nbsp;</td></tr><tr><td>MTD Sales Person Summary:</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>Ngurah&nbsp;&nbsp;:&nbsp;&nbsp;(26)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;15,687,400</td></tr><tr><td>Ayu&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;999,000</td></tr><tr><td>Sam&nbsp;&nbsp;:&nbsp;&nbsp;(24)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;15,262,900</td></tr><tr><td>Anggi&nbsp;&nbsp;:&nbsp;&nbsp;(50)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;20,703,600</td></tr><tr><td>Arie&nbsp;&nbsp;:&nbsp;&nbsp;(38)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;20,297,300</td></tr><tr><td>Kartika&nbsp;&nbsp;:&nbsp;&nbsp;(40)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;22,589,000</td></tr><tr><td>Renal&nbsp;&nbsp;:&nbsp;&nbsp;(37)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;27,938,300</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>MTD Sales Person Qty&nbsp;&nbsp;:&nbsp;&nbsp;216</td></tr><tr><td>MTD Sales Person&nbsp;&nbsp;:&nbsp;&nbsp;123,477,500</td></tr><tr><td>&nbsp;</td></tr><tr><td>MTD Sales Time Summary :</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td> Time / Inv# / Qty / Total</td></tr><tr><td>10:00 - 11:00 :&nbsp;&nbsp;1&nbsp;&nbsp;/&nbsp;&nbsp;1&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;370,000</td></tr><tr><td>11:00 - 12:00 :&nbsp;&nbsp;3&nbsp;&nbsp;/&nbsp;&nbsp;3&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;2,957,000</td></tr><tr><td>12:00 - 13:00 :&nbsp;&nbsp;3&nbsp;&nbsp;/&nbsp;&nbsp;3&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;1,561,400</td></tr><tr><td>13:00 - 14:00 :&nbsp;&nbsp;4&nbsp;&nbsp;/&nbsp;&nbsp;8&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;2,227,300</td></tr><tr><td>14:00 - 15:00 :&nbsp;&nbsp;8&nbsp;&nbsp;/&nbsp;&nbsp;13&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;5,302,000</td></tr><tr><td>15:00 - 16:00 :&nbsp;&nbsp;7&nbsp;&nbsp;/&nbsp;&nbsp;9&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;3,051,700</td></tr><tr><td>16:00 - 17:00 :&nbsp;&nbsp;8&nbsp;&nbsp;/&nbsp;&nbsp;8&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;5,734,500</td></tr><tr><td>17:00 - 18:00 :&nbsp;&nbsp;6&nbsp;&nbsp;/&nbsp;&nbsp;7&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;6,464,800</td></tr><tr><td>18:00 - 19:00 :&nbsp;&nbsp;8&nbsp;&nbsp;/&nbsp;&nbsp;9&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;4,755,000</td></tr><tr><td>19:00 - 20:00 :&nbsp;&nbsp;7&nbsp;&nbsp;/&nbsp;&nbsp;12&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;5,261,500</td></tr><tr><td>20:00 - 21:00 :&nbsp;&nbsp;9&nbsp;&nbsp;/&nbsp;&nbsp;10&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;10,212,000</td></tr><tr><td>21:00 - 22:00 :&nbsp;&nbsp;11&nbsp;&nbsp;/&nbsp;&nbsp;12&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;8,006,100</td></tr><tr><td>&nbsp;</td></tr><tr><td>MTD Sales Supplier Summary :</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>CHAMPIONS&nbsp;&nbsp;:&nbsp;&nbsp;(4)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;3,196,000</td></tr><tr><td>DEUS&nbsp;&nbsp;:&nbsp;&nbsp;(46)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;18,750,000</td></tr><tr><td>LIIVE&nbsp;&nbsp;:&nbsp;&nbsp;(1)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;650,000</td></tr><tr><td>QUIKSILVER&nbsp;&nbsp;:&nbsp;&nbsp;(26)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;10,506,900</td></tr><tr><td>RIP CURL&nbsp;&nbsp;:&nbsp;&nbsp;(45)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;16,539,500</td></tr><tr><td>SOCIAL1&nbsp;&nbsp;:&nbsp;&nbsp;(8)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;249,000</td></tr><tr><td>SURFER GIRL&nbsp;&nbsp;:&nbsp;&nbsp;(18)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;2,308,000</td></tr><tr><td>VANS&nbsp;&nbsp;:&nbsp;&nbsp;(52)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;62,923,100</td></tr><tr><td>VOLCOM&nbsp;&nbsp;:&nbsp;&nbsp;(16)&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;8,355,000</td></tr><tr><td>= = = = = = = = = = = = = = = = = =</td></tr><tr><td>MTD Supplier Qty&nbsp;&nbsp;:&nbsp;&nbsp;216</td></tr><tr><td>MTD Supplier Amount&nbsp;&nbsp;:&nbsp;&nbsp;123,477,500</td></tr><tr><td>&nbsp;</td></tr><tr><td>Extra Messages:</td></tr><tr><td>Traffic hari ini tidak terlalu ramai.  Terdapat 10 transaksi dengan 10 pcs product yang sold, dengan buying power pada masing-masing transaksi sebesar Rp.750.760 Semoga penjualan besok lebih baik lagi, selamat malam dan terimakasih.</td></tr><tr><td>&nbsp;</td></tr><tr><td>Reported By&nbsp;&nbsp;&nbsp;:&nbsp;</td></tr><tr><td>&nbsp;</td></tr><tr><td>Thank You & Best Regards</td></tr><tr><td></td></tr><tr><td>SOCIAL 1 TSM</td></tr></table>
        
    </body>
</html>
