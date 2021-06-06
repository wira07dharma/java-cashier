<%@page import="com.dimata.posbo.entity.admin.MappingUserGroup"%>
<%@page import="com.dimata.common.entity.custom.DataCustom"%>
<%@page import="com.dimata.common.entity.contact.ContactList"%>
<%@page import="com.dimata.harisma.entity.masterdata.Vocation"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstVocation"%>
<%@page import="com.dimata.harisma.entity.masterdata.Marital"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstMarital"%>
<%@page import="com.dimata.harisma.entity.masterdata.Position"%>
<%@page import="com.dimata.harisma.entity.masterdata.PstPosition"%>
<%@page import="com.dimata.hanoman.entity.masterdata.PstContactClass"%>
<%@page import="com.dimata.pos.entity.billing.BillMain"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="com.dimata.cashierweb.session.masterdata.CatalogManager"%>
<%@page import="com.dimata.cashierweb.session.masterdata.BillManager"%>
<%@page import="com.dimata.cashierweb.session.masterdata.StockCheckManager"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.PstCategory"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.Category"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.MemberGroup"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.PstMemberGroup"%>
<%@page import="com.dimata.pos.entity.payment.PstCashPayment1"%>
<%@page import="com.dimata.pos.entity.masterCashier.PstCashMaster"%>
<%@page import="com.dimata.cashierweb.entity.cashier.assigndiscount.PstAssignDiscount"%>
<%@page import="com.dimata.common.entity.payment.PstCurrencyType"%>
<%@page import="com.dimata.common.entity.payment.CurrencyType"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.Sales"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.PstSales"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.QueensLocation"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.PstQueensLocation"%>
<%@page import="com.dimata.common.form.contact.FrmContactList"%>
<%@page import="com.dimata.hanoman.form.masterdata.FrmContact"%>
<%@page import="com.dimata.common.entity.location.PstLocation"%>
<%@page import="com.dimata.common.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.posbo.entity.masterdata.PstRoom"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.TableRoom"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.PstTableRoom"%>
<%@page import="com.dimata.posbo.form.masterdata.FrmRoom"%>
<%@page import="com.dimata.posbo.entity.masterdata.Room"%>
<%@page import="com.dimata.common.form.location.FrmNegara"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.common.entity.location.Negara"%>
<%@page import="com.dimata.common.entity.location.PstNegara"%>
<%@page import="com.dimata.pos.entity.balance.PstBalance"%>
<%@page import="com.dimata.pos.entity.balance.Balance"%>
<%@page import="com.dimata.common.entity.payment.PaymentSystem"%>
<%@page import="com.dimata.pos.form.balance.FrmCashCashier"%>
<%@page import="com.dimata.posbo.entity.masterdata.Shift"%>
<%@page import="com.dimata.common.entity.location.Location"%>
<%@page import="java.util.Date"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.common.entity.payment.PstPaymentSystem"%>
<%@page import="com.dimata.qdep.form.FRMMessage"%>
<%@page import="com.dimata.pos.form.billing.FrmBillDetail"%>
<%@page import="com.dimata.cashierweb.form.admin.FrmAppUser"%>
<%@page import="com.dimata.pos.form.payment.FrmCashCreditCard"%>
<%@page import="com.dimata.hanoman.form.masterdata.FrmContactClass"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.BillMainJSON"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.pos.form.payment.FrmCashPayment"%>
<%@page import="com.dimata.pos.entity.billing.PstBillMain"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain"%>
<%@page import="com.dimata.pos.entity.balance.PstCashCashier"%>
<%@page import="com.dimata.pos.entity.balance.CashCashier"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.BillMainCostum"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.pos.form.billing.FrmBillMain"%>
<%@page import="com.dimata.common.form.logger.FrmDocLogger" %>
<%@page import="java.util.Vector"%>
<%@include file="../main/javainit_cashier.jsp" %>

<%!
    
      public static final String tipePengajuanKey[] = {"Umum", "Agen"};
      public static final String tipePengajuanValue[] = {"0", "1"};
    
    public String drawListClosing(Vector listBalance, String verification, CashCashier cashCashier) {
        ControlList ctrlist = new ControlList();
        int countOn = 1;
        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        
        ctrlist.addHeader("SUPERVISOR CONFIRMATION", "20%");
        String cashierAutoFill = PstSystemProperty.getValueByName("CASHIER_AUTO_FILL_CLOSING_BALANCE");
        String whereClause = "CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_PAY_DATETIME] + " BETWEEN '"
                + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd HH:mm:ss") + "' AND '"
                + Formater.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "' ";
        
        for (int j = 0; j < listBalance.size(); j++) {
            Balance entBalance = (Balance) listBalance.get(j);
            CurrencyType entCurrencyType = new CurrencyType();
            try {
                entCurrencyType = PstCurrencyType.fetchExc(entBalance.getCurrencyOid());
            } catch (Exception ex) {
            }
            ctrlist.addHeader("" + entCurrencyType.getCode() + "", "20%");
        }
        
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        ctrlist.reset();
        int index = -1;
        
        Balance balance = new Balance();
        Vector rowx = new Vector(1, 1);
        rowx.add("&nbsp;");
        int countTemp = 1;
        for (int i = 0; i < listBalance.size(); i++) {
            balance = (Balance) listBalance.get(i);
            rowx.add(""
                    + "<input type='hidden' type='text' class='form-control money' name='" + FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_AMOUNT1] + "_" + countTemp + "' value='" + balance.getBalanceValue() + "'>"
                    + "<input type='hidden' class='form-control' name='" + FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CURRENCY_ID] + "_" + countTemp + "' value='" + balance.getCurrencyOid() + "'>");
            countTemp += 1;
        }
        lstData.add(rowx);
        
        rowx = new Vector(1, 1);
        rowx.add("<b>CASH</b>");
        for (int i = 0; i < listBalance.size(); i++) {
            balance = (Balance) listBalance.get(i);
            /*String whereCash = whereClause + " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"= 0"
             + " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"= 0 "
             + " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"= 0"
             + " AND PS."+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"= 1"
             + " AND CP."+PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID]+" = "+balance.getCurrencyOid();
             double sumPayment = PstCashPayment1.getSumSystemPayment(whereCash);*/
            rowx.add("<input style='text-align:right;' type='text' name='CASH_PAYMENT_" + balance.getCurrencyOid() + "' class='form-control text_" + balance.getCurrencyOid() + " amountCloseClear textClose money' id='closingText_" + countOn + "' data-index='" + countOn + "'>");
            countOn += 1;
        }
        lstData.add(rowx);
        
        rowx = new Vector(1, 1);
        rowx.add("<b>CREDIT CARD</b>");
        for (int i = 0; i < listBalance.size(); i++) {
            balance = (Balance) listBalance.get(i);
            rowx.add("<input style='text-align:right;' type='text' name='CREDIT_CARD_PAYMENT_" + balance.getCurrencyOid() + "' class='form-control text_" + balance.getCurrencyOid() + " amountCloseClear textClose money' id='closingText_" + countOn + "' data-index='" + countOn + "'>");
            countOn += 1;
        }
        lstData.add(rowx);
        
        /* UPDATED BY DEWOK 20190321, PAYMENT BG TIDAK DIPAKAI LAGI
        rowx = new Vector(1, 1);
        rowx.add("<b>BG</b>");
        for (int i = 0; i < listBalance.size(); i++) {
            balance = (Balance) listBalance.get(i);
            rowx.add("<input style='text-align:right;' type='text' name='BG_PAYMENT_" + balance.getCurrencyOid() + "' class='form-control text_" + balance.getCurrencyOid() + " amountCloseClear textClose money' id='closingText_" + countOn + "' data-index='" + countOn + "'>");
            countOn += 1;
        }
        lstData.add(rowx);
        */
        
        /* UPDATED BY DEWOK 20190321, PAYMENT CHEQUE TIDAK DIPAKAI LAGI
        rowx = new Vector(1, 1);
        rowx.add("<b>CHEQUE</b>");
        for (int i = 0; i < listBalance.size(); i++) {
            balance = (Balance) listBalance.get(i);
            rowx.add("<input style='text-align:right;' type='text' name='CHECK_PAYMENT_" + balance.getCurrencyOid() + "' class='form-control text_" + balance.getCurrencyOid() + " amountCloseClear textClose money' id='closingText_" + countOn + "' data-index='" + countOn + "'>");
            countOn += 1;
        }
        lstData.add(rowx);
        */
        
        rowx = new Vector(1, 1);
        int jumlah = listBalance.size();
        rowx.add("<b>DEBIT CARD</b>");
        for (int i = 0; i < listBalance.size(); i++) {
            balance = (Balance) listBalance.get(i);
            if (i == (jumlah - 1)) {
                rowx.add("<input style='text-align:right;' type='text' name='DEBIT_CARD_PAYMENT_" + balance.getCurrencyOid() + "' class='form-control text_" + balance.getCurrencyOid() + " amountCloseClear money lastClosing textClose' id='closingText_" + countOn + "' data-index='" + countOn + "'>");
                countOn += 1;
            } else {
                rowx.add("<input style='text-align:right;' type='text' name='DEBIT_CARD_PAYMENT_" + balance.getCurrencyOid() + "' class='form-control amountCloseClear text_" + balance.getCurrencyOid() + " money textClose' id='closingText_" + countOn + "' data-index='" + countOn + "'>");
                countOn += 1;
            }
        }
        lstData.add(rowx);
        
        rowx = new Vector(1, 1);
        rowx.add("<b>VOUCHER</b>");
        for (int i = 0; i < listBalance.size(); i++) {
            balance = (Balance) listBalance.get(i);
            rowx.add("<input style='text-align:right;' type='text' name='VOUCHER_" + balance.getCurrencyOid() + "' class='form-control text_" + balance.getCurrencyOid() + " amountCloseClear textClose money' id='closingText_" + countOn + "' data-index='" + countOn + "'>");
            countOn += 1;
        }
        lstData.add(rowx);
        
        rowx = new Vector(1, 1);
        rowx.add("<b>TOTAL CLOSING AMOUNT</b>");
        int countOns = 1;
        for (int i = 0; i < listBalance.size(); i++) {
            balance = (Balance) listBalance.get(i);
            if (i == (jumlah - 1)) {
                rowx.add("<input readonly style='text-align:right; font-weight:bold;' type='text' name='" + FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_BALANCE_VALUE] + "_" + balance.getCurrencyOid() + "' class='form-control money totalCloseText textCloseFor_" + balance.getCurrencyOid() + " lastTotalClosing totalClosingText_" + countOns + " amountCloseClear' data-index='" + countOns + "'>");
                countOns += 1;
            } else {
                rowx.add("<input readonly style='text-align:right; font-weight:bold;' type='text' name='" + FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_BALANCE_VALUE] + "_" + balance.getCurrencyOid() + "' class='form-control money totalClosingText_" + countOns + " textCloseFor_" + balance.getCurrencyOid() + " totalCloseText amountCloseClear' data-index='" + countOns + "'>");
                countOns += 1;
            }
        }
        lstData.add(rowx);
        
        rowx = new Vector(1, 1);
        rowx.add("&nbsp;");
        for (int i = 0; i < listBalance.size(); i++) {
            rowx.add("&nbsp;");
        }
        lstData.add(rowx);
        
        if (verification.equals("1") && listBalance.size() > 0) {
            rowx = new Vector(1, 1);
            rowx.add("<b>SUPERVISOR</b>");//1
            rowx.add("<input id='supervisorName' type='text' name='" + FrmAppUser.fieldNames[FrmAppUser.FRM_LOGIN_ID] + "' class='form-control' placeholder='Supervisor Username' required='required'>");
            rowx.add(""); //2
            lstData.add(rowx);
            rowx = new Vector(1, 1);
            rowx.add("");
            rowx.add("<div id='dynamicPlace2'></div>");
            rowx.add("");
            lstData.add(rowx);
            rowx = new Vector(1, 1);
            rowx.add("");
            rowx.add("<input id='passwordSpv' type='hidden' name='" + FrmAppUser.fieldNames[FrmAppUser.FRM_PASSWORD] + "' class='form-control' placeholder='Supervisor Password' required='required'>"
                    + "<input type='hidden' class='insert-total'>"
                    + "<input type='hidden' class='insert-cost' value='0'>"
                    + "<input type='hidden' name='" + FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_TYPE] + "' value='1'");
            rowx.add("");
            lstData.add(rowx);
        } else if (listBalance.size() > 0) {
            rowx = new Vector(1, 1);
            rowx.add("<b>SUPERVISOR</b>");//1
            rowx.add("<input id='supervisorName' type='text' name='" + FrmAppUser.fieldNames[FrmAppUser.FRM_LOGIN_ID] + "' class='form-control' placeholder='Supervisor Username' required='required'>");
            lstData.add(rowx);
            rowx = new Vector(1, 1);
            rowx.add("");//1
            rowx.add("");//2
            lstData.add(rowx);
            rowx = new Vector(1, 1);
            rowx.add("<b>PASSWORD</b>");
            rowx.add("<input id='supervisorPassword' type='password' name='" + FrmAppUser.fieldNames[FrmAppUser.FRM_PASSWORD] + "' autocomplete='off' class='form-control' placeholder='Supervisor Password' required='required'>"
                    + "<input type='hidden' class='insert-total'>"
                    + "<input type='hidden' class='insert-cost' value='0'>"
                    + "<input type='hidden' name='" + FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_TYPE] + "' value='1'");
            lstData.add(rowx);
            rowx = new Vector(1, 1);
            rowx.add("<b>CLOSE NOTE</b>");//1
            rowx.add("<textarea id='closeNote' name='" + FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CLOSE_NOTE] + "' class='form-control' placeholder='Note' required='required' rows='5'></textarea>");
            lstData.add(rowx);
        }
        return ctrlist.drawBootstrapStripted();
    }
%>

<%    CashCashier cashCashier = new CashCashier();
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
    FrmBillMain frmBillMain = new FrmBillMain();
    String multilocation = PstSystemProperty.getValueByName("OUTLET_MULTILOCATION");
    String showOpenBill = PstSystemProperty.getValueByName("CASHIER_SHOW_OPEN_BILL_BUTTON");
    long defaultOidLocation = Long.parseLong(PstSystemProperty.getValueByName("OUTLET_DEFAULT_LOCATION"));
    String defaultOidPriceType = PstSystemProperty.getValueByName("OUTLET_DEFAULT_PRICE_TYPE");
    String displayLocation = "";
    //ini adalah fungsi pengecekan apakah menggunakan approval finger atau tidak
    String verificationType = PstSystemProperty.getValueByName("KASIR_LOGIN_TYPE");
    String cssDisplay = "";
    if (verificationType.equals("1")) {
        cssDisplay = "display:none;";
    } else {
        cssDisplay = "display:block;";
    }
    String cssDisplay2 = "";
    if (verificationType.equals("1")) {
        cssDisplay2 = "display:none;";
    } else {
        cssDisplay2 = "";
    }
    //ini adalah fungsi pengecekan apakah perlu guest atau tidak
    boolean useSignature = false;
    String defineGuest = PstSystemProperty.getValueByName("CASHIER_USE_GUEST_ON_NEW_ORDER");
    String cashierPath = PstSystemProperty.getValueByName("CASHIER_PATH_SIGNATURE");
    if (!cashierPath.equals("0")) {
        useSignature = true;
    }
    String signatureLokasiSimpan = getServletContext().getRealPath("signature");
    //ini adalah fungsi untuk mengecek apakah menggunakan card reader atau tidak;
    String cardReader = PstSystemProperty.getValueByName("CASHIER_USE_READERCARD");
    //ini adalah fungsi untuk mengecek apakah system menampilkan print open bill atau tidak
    String allowOpenBill = PstSystemProperty.getValueByName("CASHIER_USE_REPRINT_OPEN_BILL");
    //ini adalah fungsi untuk mengecek apakah system memrlukan verifikasi ketika proses payment
    String paymentNeedVerifikasi = PstSystemProperty.getValueByName("CASHIER_PAYMENT_NEED_LOGIN_VERIFIKASI");
    //ini adalah fungsi untuk mengecek apakah sistem ada fasilitas untuk mencetak bill dengan payment only atau tidak
    String printOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");
    //ini adalah fungsi untuk mengecek apakah sistem menggunakan bill continous atau custom
    String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
    //ini adalah fungsi untuk mengecek, apakah sistem kasir mendukung production
    String cashierSupportProduction = PstSystemProperty.getValueByName("CASHIER_SUPPORT_PRODUCTION");
    String checkOutViaOutlet = PstSystemProperty.getValueByName("CHECKOUT_ITEM_VIA_OUTLET");
    //ini adalah Variabel untuk menampung, apakah sistem mengirim sms pada event tertentu
    String cashierLinkSms = PstSystemProperty.getValueByName("CASHIER_LINK_SMS");
    String cashierSmsSender = PstSystemProperty.getValueByName("CASHIER_SMS_SENDER");
    //ini adalah fungsi untuk mengecek, apakah sistem kasir perlu sales
    String cashierNeedSales = PstSystemProperty.getValueByName("CASHIER_SHOW_SALES_PERSON");
    //ini adalah variabel untuk menampung tipe bussines dari sistem ini (Retail,Restaurant,atau Retail DIstribution)
    String bussinessType = PstSystemProperty.getValueByName("TYPE_OF_BUSINESS");
    //System Property untuk mengecek, apakah menggunakan log out otomatis atau tidak
    String logOutAuto = PstSystemProperty.getValueByName("LOGOUT_OUTOMATIC");
    //System Property untuk mengecek, iddle time yang digunakan
    String iddleTime = PstSystemProperty.getValueByName("LOGOUT_OUTOMATIC_IDDLE_LIMIT");
    //System Property untuk mengecek, apakah closing balance bisa dilakukan jika masih ada transaksi open bill
    String enableClosing = PstSystemProperty.getValueByName("CASHIER_CLOSING_ON_EXIST_OPEN_BILL");
    String showDateTime = PstSystemProperty.getValueByName("CASHIER_SHOW_DATE_TIME_LOAD_BILL");
    String enableNewOrder = PstSystemProperty.getValueByName("CASHIER_ENABLE_NEW_ORDER_ON_EXIST_BILL");
    String cancelBillSpv = PstSystemProperty.getValueByName("CASHIER_CANCEL_BILL_USING_SUPERVISOR");
    String cashierShowTrans = PstSystemProperty.getValueByName("CASHIER_SHOW_RETURN_ON_TRANSACTION");
    String enableDutyFree = PstSystemProperty.getValueByName("ENABLE_DUTY_FREE");
    String useCountry = PstSystemProperty.getValueByName("CASHIER_NOT_SHOW_COUNTRY_ON_NEW_ORDER");
    String cssReturn = "";
    int enableClose = 0;
    try {
        enableClose = Integer.parseInt(enableClosing);
    } catch (Exception ex) {
    }
    String cashierShowPackage = PstSystemProperty.getValueByName("CASHIER_SHOW_PACKAGE_MENU");
    String cssPackage = "";
    if (cashierShowPackage.equals("0")) {
        cssPackage = "display:none;";
    }
    if (cashierShowTrans.equals("1")) {
        cssReturn = "display:none;";
    }
    String cashierDiscountBillUsingSupervisor = PstSystemProperty.getValueByName("CASHIER_DISCOUNT_BILL_USING_SUPERVISOR");
    String printWhenClosing = PstSystemProperty.getValueByName("CASHIER_PRINT_WHEN_CLOSING");
    String kasirViewOutlet = PstSystemProperty.getValueByName("CASHIER_VIEW_OUTLET_FOR_INPUT_ITEM");
    String disableQty = PstSystemProperty.getValueByName("CASHIER_DISABLE_QTY_PACKAGE");
    String btnMultiPayment = PstSystemProperty.getValueByName("CASHIER_BUTTON_MULTIPAYMENT");
    String btnPrintPayment = PstSystemProperty.getValueByName("CASHIER_BUTTON_PRINT_PAYMENT");
    String hideMulti = "";
    String hidePrintPay = "";
    if (btnMultiPayment.equals("0")) {
        hideMulti = "display:none;";
    }
    if (btnPrintPayment.equals("0")) {
        hidePrintPay = "display:none;";
    }
    String useCoverNumber = PstSystemProperty.getValueByName("CASHIER_USING_COVER_NUMBER");
    String usingDiscAssign = PstSystemProperty.getValueByName("CASHIER_USING_DISCOUNT_ASSIGN");
	String sedanaUrl = PstSystemProperty.getValueByName("SEDANA_URL");
	
	String autoStartService = PstSystemProperty.getValueByName("AUTO_START_SERVICE");
	if (autoStartService.equals("1")){
		StockCheckManager stockCheckManager = new StockCheckManager();
		BillManager billManager = new BillManager();
		CatalogManager catManager = new CatalogManager();
		boolean isBillRunning = billManager.getStatus();
		boolean isCatRunning = catManager.getStatus();
		boolean isStockRunning = stockCheckManager.getStatus();
		stockCheckManager.setUserId(userId);
		catManager.setUserId(userId);
		if (!isBillRunning){
			billManager.startCheck();
		}
		if (!isCatRunning){
			catManager.startCheck();
		}
		if (!isStockRunning){
			stockCheckManager.startCheck();
		}
	}
        ContactList contact = new ContactList();
%>

<%
    String ordGroup = "" + PstMemberGroup.fieldNames[PstMemberGroup.FLD_GROUP_TYPE];
    Vector<MemberGroup> listGroup = PstMemberGroup.list(0, 0, "", ordGroup);
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Transaction - Dimata Cashier</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <%@include file="cashier-css.jsp" %>
        <script type="text/javascript" src="../styles/cashier/dist/js/numberformat.js"></script>
        <script type="text/javascript" src="../styles/jquery.simplePagination.js"></script>
        <style>
            .textual:focus {
                background-color: yellow;
            }
            .modal {overflow-y: auto}
            .modal-header, .modal-footer {border-color: lightgray}
            #opensearchmember, #opennewcustomer {z-index: 2000}
            #modalReturnExchange td {vertical-align: middle}
            .addNum {margin-top: 5px}
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
                    <h1>Dimata Cashier
                        <small>
                            <b>
                                <%= Formater.formatDate(new Date(), "dd MMM yyy") %>
                                &nbsp;|&nbsp; Location : <a><%= location.getName().toUpperCase() %></a>
                                &nbsp;|&nbsp; Cashier Number : <a><%= PstCustomBillMain.getCashierNumber(cashCashier.getOID()) %></a>
                                &nbsp;|&nbsp; Shift : <a><%= shift.getName().toUpperCase() %></a>
                                &nbsp;|&nbsp; Person : <a><%= userName.toUpperCase() %></a>
                            </b>
                        </small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="<%= approot%>/cashier/cashier-home.jsp"><i class="fa fa-home"></i> Home</a></li><li class="active">Transaction</li>
                    </ol>
                </section>
                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box box-primary">
                                <div class="box-header with-border">
                                    <div class="row">
                                        <div class="col-md-2">
                                            <input type="hidden" id="helpMessageCustomerName" value=""> <input type="hidden" id="helpMessageCustomerTelp" value=""><input type="hidden" id="guestNameSource" value="">
                                            <input type="hidden" id="cashierLink" value="<%= cashierLinkSms%>"> <input type="hidden" id="cashierSmsSender" value="<%= cashierSmsSender%>"> <input type="hidden" id="parentPackage" value="0">
                                            <input type="hidden" id="printOutType" value="<%=printOutType%>"> <input type="hidden" id="notificationCount" value="0">
                                            <input type="hidden" id="printPapertType" value="<%= printPapertType%>"><input type="hidden" id="productionStatus" value="0">
                                            <input type="hidden" id="mainBillIdSource" value="0"><input type="hidden" id="reservationIdSource" value="0"><input type="hidden" id="transactionPackage" value="0">
                                            <input type="hidden" id="locationBillIdSource" value="0"><input type="hidden" id="cashierSupportProduction" value="<%=cashierSupportProduction%>"><input type="hidden" id="checkOutItemViaOutlet" value="<%=checkOutViaOutlet%>"><input type="hidden" name="maxdisc" id="maxDisc">
                                            <input type="hidden" id="cashCashierId" value="<%= cashCashier.getOID()%>">
                                            <button class="btn btn-primary" id="neworder" style="display:none;">
                                                <i class="fa fa-plus"></i> New Order
                                            </button>
                                            <button class="btn btn-primary" id="neworder2">
                                                <i class="fa fa-plus"></i> New Order (F5)
                                            </button>
                                        </div>
                                        <div class="col-md-3">
                                            <form class="additem">
                                                <%
                                                    String locationActive = "";
                                                    if (multilocation.equals("0")) {
                                                        locationActive = displayLocation;
                                                    } else {
                                                        locationActive = "" + location.getOID();
                                                    }
                                                %>
                                                <div class="input-group">
                                                    <input type="hidden" id="categoryMenuId" name="categoryMenuId" value="0">
                                                    <input type="hidden" name="loadtype" value="loaditemlist">
                                                    <input type="hidden" name="LOCATION_ID" value="<%= locationActive%>">
                                                    <input id="searchBarcode" type="text" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME]%>" class="form-control itemsearch" placeholder="Scan or Search Items (F7)" readonly="true"/>
                                                    <div class="input-group-addon" style="padding:0px;border:none;">
                                                        <button class="btn btn-primary input-group-addon itemsearchbutton" id="searchItemFront" style="padding-bottom: 9px;padding-top: 9px;padding-right: 25px;border-right: 1px solid #D2D6DE" disabled="true">
                                                            <i class="fa fa-search"></i>
                                                        </button>
                                                    </div>
                                                </div><!-- /.input group -->
                                            </form>
                                        </div>			    
                                        <div class="col-md-7">
                                            <div class="pull-right">
                                                <% if (showOpenBill.equals("1")) {%>
                                                <button id="openBillSearch" class="btn btn-primary btnOpenBill" data-load-type="loadsearch">
                                                    <i class="fa fa-folder-open"></i> Open Bill (F6)
                                                    <span class="badge bg-yellow" id="newBillNotification"></span>
                                                </button><% }%>
                                                <button style="<%= cssReturn%>" id="btnReturnBill" class="btn btn-primary btnOpenBill" data-load-type="loadsearchreturn">
                                                    <i class="fa fa-folder-open"></i> Return Bill (F4)
                                                </button>
                                                <button class="btn btn-primary " id ='btnReprintBill'>
                                                    <i class="fa fa-print"></i> Reprint Bill (F3)
                                                </button>
                                                <button class="btn btn-success" id="btnClosingBalance">
                                                    <i class="fa fa-close"></i> Closing Balance (F11)
                                                </button>
                                                <button style="<%= cssPackage%>"   class="btn btn-warning" id="btnConsumePack">
                                                    &nbsp;<i class="fa fa-delicious"></i>&nbsp;
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-body" id="CONTENT_LOAD"></div>
                                <div class="box-footer">
                                    
                                </div>
                                <div class="overlay" style="display:none;" id="CONTENT_ANIMATE_CHECK">
                                    <i class="fa fa-refresh fa-spin"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
            <div id="CONTENT_TOTAL" style="display:none;">
                <footer class="main-footer" style="bottom: 0;position: fixed; width: -moz-available">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-7">
                                <button class="btn btn-warning" style="display:none;">HOLD</button>
                                <button class="btn btn-primary authorize" id="btnMove" data-title="SPLIT BILL" data-load-type="movebill">
                                    <i class="fa fa-scissors"></i> Split Bill (F9)
                                </button>
                                <button style="display: none" class="btn btn-primary" id="btnJoinBill" data-title="JOIN BILL" data-load-type="joinbill">
                                    <i class="fa fa-copy"></i> Join Bill 
                                </button>
                                <button class="btn btn-primary authorize" id="btnJoinBillTemp" data-title="JOIN BILL" data-load-type="joinbill">
                                    <i class="fa fa-copy"></i> Join Bill (F10)
                                </button>
                                <button class="btn btn-danger authorizeCancel" id="btnCancel" data-title="CANCEL BILL" data-load-type="cancelbill">
                                    <i class="fa fa-close"></i><% if (cancelBillSpv.equals("1")) {%> Cancel Bill (F2)<% } else {%> Skip Bill (F2)<%}%>
                                </button>
                                <button style="display:none" class="btn btn-info" id="btnConfirm" data-title="CONFIRM" data-load-type="confirmBill">
                                    <i class="fa fa-check"></i> Confirm Order
                                </button>
                                <button class="btn btn-primary" style="display:none;">
                                    %
                                </button>
                                <button class="btn btn-primary" style="display:none;">
                                    <i class="fa fa-user"></i>&nbsp;
                                </button>
                            </div>
                            <div class="col-md-5">
                                <table style="background-color: #222D32;border-radius: 5px;">
                                    <tr>
                                        <td style="padding:4px;" width="10%"><b style="color:#a1a1a1;">GRAND TOTAL</b></td>
                                        <td style="padding:4px;" width="80%"><center><font size="5" id="grandTotal" color="white"></font></center></td>
                                        <td style="padding:4px;" width="10%">
                                            <button class="btn btn-success" id="btnPay" style="display: none"><b>PAY (F8)</b></button>
                                            <button class="btn btn-success" id="btnBiayaTambahan" style="display: none"><b>TO PRODUCTION</b></button>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <!-- JQUERY & BOOTSTRAP COMPONEN -->
        <%@include file="cashier-jquery-bootstrap.jsp" %>
        <script type="text/javascript" src="../styles/cashier/signature/flashcanvas.js"></script>
        <script type="text/javascript" src="../styles/cashier/signature/jSignature.min.js"></script>
        <script type="text/javascript" src="../styles/dimata-app.js"></script>
        <script src="../styles/src/moment.js"></script>  
        <script src="../styles/src/js/bootstrap-datetimepicker.min.js"></script>
        <link href="../styles/src/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/> 
        <script type="text/javascript">
            $(document).ready(function () {
            <% if (showDateTime.equals("0")) { %>
                $('.datePicker2').datetimepicker({
                    format: "YYYY/MM/DD"
                });
            <% } else {%>
                $('.datePicker2').datetimepicker({
                    format: "YYYY/MM/DD HH:mm:ss"
                });
            <%}%>
            });
        </script>
        <script type="text/javascript">
            jQuery.tableNavigation();
        </script>
        <script async defer src="https://maps.googleapis.com/maps/api/js?v=3&key=AIzaSyCVHaChwTJ1045ZRf57k4waY28m7M7mXjQ&callback=setContent&libraries=geometry"></script>
        <jsp:include page="cashier-transaction-script.jsp"/>
        <!-- PAYMENT FORM  -->
        <div id="openpay" class="modal nonprint" tabindex="-1" style="overflow-y: scroll;">
            <div class="modal-dialog modal-large">
                <div class="modal-content">
                    <form id="<%= FrmCashPayment.FRM_NAME_CASH_PAYMENT%>">
                        <input type="hidden" name="btnfrom" id="btnfrom">
                        <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_APP_USER_ID]%>" value="<%= userId%>">
                        <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIFT_ID]%>" value="<%= shift.getOID()%>">
                        <input type='hidden' name='command' value='<%= Command.SAVE%>' id="command">
                        <input type="hidden" name="loadtype" value="" id="loadtype">
                        <input type='hidden' name='<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]%>' value="<%= cashCashier.getOID()%>">
                        <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_RESERVATION_ID]%>" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_RESERVATION_ID]%>" value="0">
                        <%--<input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] %>" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] %>">--%>
                        <input type="hidden" name="signaturelokasi" value="<%=signatureLokasiSimpan%>" id="signaturelokasi">
                        <input type="hidden" name="separateprint" id="separateprint" value="0">
                        <div class="modal-header">
                            <div class="row">
                                <div class="col-md-6">
                                    <h4 class="modal-title"><b>TRANSACTION PAYMENT</b></h4>
                                </div>
                                <div class="col-md-6" style="text-align: right;display: none;" id="CONTENT_INFO">
                                    <div class="col-md-12" style="background-color: #222D32;border-radius: 5px;color:#fff;">
                                        <h2 class='modal-title col-md-4 pull-left' style='font-weight: bold;color:#a1a1a1;' id="info">TOTAL</h2> <h2 class='modal-title col-md-8 pull-right' id="nominal"></h2>
                                    </div>
                                    <input type="hidden" name="<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN]%>" value="0" id="<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN]%>">
                                </div>
                            </div>
                        </div>
                        <div class="modal-body" id="CONTENT_PAY">
                        </div>
                        <div class="modal-footer">
                            <input type="hidden" name="<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE]%>" value='0' id='<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE]%>'>
                            <input type='hidden' name='<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE]%>'  id='<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE]%>' value='0'>
                            <input type='hidden' name='<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE]%>' id='<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE]%>' value='0'>
                            <input type='hidden' name='<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE]%>' id='<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE]%>' value='0'>
                            <input type='hidden' name='<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC]%>' id='<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC]%>' value='0'>
                            <input type="hidden" name='cansave' value="false" id="cansave">
                            <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Close</button>
                            <button type="button" class="btn  btn-info" id="printnotaGuide" style="display:none;">
                                <i class="fa fa-print"></i> Print Bill Guide Price
                            </button>
                            <button type="button" class="btn  btn-primary" id="printnota" style="display:none;">
                                <i class="fa fa-print"></i> Print Open Bill
                            </button>
                            <button type="button" class="btn btn-success" id="btnPayFinger" style="display:none;">Pay With Full Print</button>
                            <div class="cover" style="float: right; margin-left: 5px; display: none">
                                <button type="button" class="btn btn-success" id="btnPaySave" style="display:none;">Pay</button>
                            </div>
                            <div class="doublePay" style="float: right; margin-left: 5px; <%= hideMulti%>">
                                <button type="button" class="btn btn-success" id="btnDoublePayment">Multi Payment</button>
                            </div>
                            <div class="printSeparate" style="float: right; margin-left: 5px; <%= hidePrintPay%>">
                                <button type="button" class="btn btn-warning" id="btnPrintSeparate">Pay and Print Payment Only</button>
                            </div>
                        </div>
                    </form>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <!-- /.PAYMENT FORM  -->
        <!-- OPEN BILL -->
        <div id="openbill" class="modal nonprint" >
            <div class="modal-dialog modal-lg" style="width:1000px;">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title"><b>OPEN BILL</b></h4>
                        <div class="row" style="margin-bottom: 2px; margin-top: 10px">
                            <%if (multilocation.equals("1")) {%>
                            <div class="col-md-4">
                                <%
                                    Vector location_keys = new Vector(1, 1);
                                    Vector location_values = new Vector(1, 1);
                                    Vector listLocation = PstLocation.listLocationStore(0, 0, "", "");
                                    if (listLocation.size() != 0) {
                                        location_keys.add("0");
                                        location_values.add("ALL LOCATION");
                                        for (int i = 0; i < listLocation.size(); i++) {
                                            Location location1 = (Location) listLocation.get(i);
                                            location_keys.add("" + location1.getOID());
                                            location_values.add("" + location1.getName());
                                        }
                                    }
                                %>
                                <%= ControlCombo.drawBootsratap(FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] + "_OPEN", null, "" + location.getOID(), location_keys, location_values, "required='required'", "form-control")%>
                            </div>
                            <%}%>
                            <%if (multilocation.equals("1")) {%>
                            <div class="col-md-8">
                                <%} else {%>
                                <div class="col-md-12">
                                    <input value="0" type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]%>_OPEN" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]%>_OPEN">
                                    <%}%>
                                    <div class="input-group" sty>
                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                        <input id="obSearch1" class="form-control datePicker2" required="" type="text">
                                        <div class="input-group-addon" style="border:none;"><i class="fa fa-minus"></i></div>
                                        <div class="input-group-addon" style="border-right:none;"><i class="fa fa-calendar"></i></div>
                                        <input id="obSearch2" class="form-control datePicker2" required="" type="text">
                                    </div>
                                </div>
                            </div> 
                            <div class="row">
                                <%
                                    if (bussinessType.equals("2")) {
                                %>
                                <div class="col-md-4">
                                    <input type="text" class="form-control" placeholder="Type Table Name.." id="tableName" name ="tableName">
                                </div>
                                <div class="col-md-8">
                                    <div class="input-group">
                                        <input type="text" class="form-control" placeholder="Scan or Search Transactions" id="datasearch"/>
                                        <div class="input-group-addon btn btn-primary" id="btnSearch">
                                            <i class="fa fa-search"></i>
                                        </div>
                                    </div>
                                </div>
                                <%} else {%>               
                                <input type="hidden" class="form-control" placeholder="Type Table Name.." id="tableName" name ="tableName">
                                <div class="col-md-12">
                                    <div class="input-group">
                                        <input type="text" class="form-control" placeholder="Scan or Search Transactions" id="datasearch"/>
                                        <div class="input-group-addon btn btn-primary" id="btnSearch">
                                            <i class="fa fa-search"></i>
                                        </div>
                                    </div>
                                </div>
                                <%}%>
                            </div>
                        </div>
                        <form id="returnBill">
                            <div class="modal-body" id="CONTENT_SEARCH">
                            </div>
                            <div class="modal-footer">
                                <input value="0" id="selectedIdBill" type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]%>">
                                <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]%>" value="<%= cashCashier.getOID()%>">
                                <input type="hidden" name="loadtype" id="loadtypereturn">
                                <input type="hidden" name="command" value="<%= Command.NONE%>" id="commandreturn">
                                <button id="specialButton" type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Close</button>
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. OPEN BILL -->
            <!-- OPEN BILL REPRINT -->
            <div id="openbillreprint" class="modal nonprint" >
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title"><b>REPRINT BILL</b></h4>

                        </div>
                        <div class="modal-body">
                            <div  id="REPRINT_UP"></div>
                            <br>
                            <div  id="REPRINT_LOW"></div>
                            <div class='row'>
                                <div class='col-md-12'>
                                  <%if(useForRaditya.equals("1")){%>
                                    <button id='btnCreditSelect' type='button' class='btn btn-default btn-flat pull-right'>
                                        <i class='fa fa-money'></i> Credit Payment
                                    </button>
                                    <%}%>
                                    <button id='btnFOCSelect' type='button' class='btn btn-default btn-flat pull-right'>
                                        <i class='fa fa-check-square-o'></i> FOC Bill
                                    </button>
                                    <button id='btnReturnedSelect' type='button' class='btn btn-default btn-flat pull-right'>
                                        <i class='fa fa-retweet'></i> Returned Bill
                                    </button>
                                    <button id='btnPaidSelect' type='button' class='btn btn-default btn-flat pull-right active'>
                                        <i class='fa fa-calculator'></i> Paid Bill
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. OPEN BILL REPRINT-->
            <!-- OPEN BILL JOIN -->
            <div id="openbilljoin" class="modal nonprint" >
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title"><b>JOIN BILL</b></h4>

                        </div>
                        <div class="modal-body">
                            <div  id="JOINBILL_UP"></div>
                            <br>
                            <div  id="JOINBILL_LOW"></div>
                            <div class='row'>
                                <div class='col-md-12'>

                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>

                            <button id="btnJoinSave" style="display:none" type="button" class="btn btn-primary pull-right"><i class="fa fa-save"></i> Save</button>
                            <button id="btnJoinBack" style="display:none"type="button" class="btn btn-success pull-right"><i class="fa fa-long-arrow-left"></i> Back</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. OPEN BILL REPRINT-->
            <!-- LIST ITEM -->
            <div id="openitemlist" class="modal nonprint" style="overflow-y: scroll;">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title"><b>ITEM LIST</b></h4>
                            <form class="additem">
                                <input type="hidden" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]%>" value="<%= cashCashier.getOID()%>">
                                <%
                                    if (multilocation.equals("0")) {
                                %>
                                <input type="hidden" name="LOCATION_ID" id="LOCATION_ID" value="<%= defaultOidLocation%>">
                                <%
                                } else {
                                    Vector location_key = new Vector(1, 1);
                                    Vector location_value = new Vector(1, 1);

                                    Vector listLocation = PstQueensLocation.list(0, 0,
                                            "", "");
                                    if (listLocation.size() != 0) {
                                        for (int i = 0; i < listLocation.size(); i++) {
                                            QueensLocation queensLocation = (QueensLocation) listLocation.get(i);
                                            location_key.add("" + queensLocation.getOID());
                                            location_value.add("" + queensLocation.getName());
                                        }
                                    }
                                %>
                                <%= ControlCombo.drawBootsratap("LOCATION_ID", "Select Location", "" + location.getOID(), location_key, location_value, "required='required'", "form-control LOCATION_ID")%>
                                <%
                                    }
                                %>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="input-group">
                                            <input type="hidden" name="loadtype" value="loaditemlist">
                                            <input id="searchItemModal" type="text" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME]%>_BARCODE" class="form-control itemsearch" placeholder="Scan Barcode" readonly="true"/>
                                            <div class="input-group-addon" style="padding:0px;border:none;">
                                                <button  id="searchitemlocation" class="btn btn-primary input-group-addon itemsearchbutton"  style="padding-bottom: 9px;padding-top: 9px;padding-right: 25px;border-right: 1px solid #D2D6DE" disabled="true">
                                                    <i class="fa fa-search"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="input-group">
                                            <input type="hidden" name="loadtype" value="loaditemlist">
                                            <input id="searchItemNew" type="text" name="searchItemNew" class="form-control itemsearch" placeholder="Search Items" readonly="true"/>

                                            <div class="input-group-addon" style="padding:0px;border:none;">
                                                <button id="searchItemNewButton" type="button" class="btn btn-primary input-group-addon itemsearchbutton"  style="padding-bottom: 9px;padding-top: 9px;padding-right: 25px;border-right: 1px solid #D2D6DE" disabled="true">
                                                    <i class="fa fa-search"></i>
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>

                        </div>
                        <div class="modal-body" id="CONTENT_ITEM">
                        </div>
                        <div class="modal-footer">               
                        </div>

                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. LIST ITEM -->

            <div id="modal-newcustomer" class="modal fade" role="dialog">
                <div class="modal-dialog modal-lg">                
                    <!-- Modal content-->
                    <div class="modal-content">

                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Tambah</h4>
                        </div>

                        <form id="addCustomer">
                            <input type="hidden" name="command" value="<%= Command.SAVE%>">
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <input type="hidden" class="form-control" name="<%=FrmContactList.fieldNames[FrmContactList.FRM_FIELD_CONTACT_CODE] %>" value="Auto-Number">
                                        <input type="hidden" name="FRM_FIELD_CLASS_TYPE" value="<%=PstContactClass.CONTACT_TYPE_MEMBER%>">
                                        <input type="hidden" name="FRM_FIELD_DATA_FOR" value="saveNewCustomer">
                                        <input type="hidden" name="command" value="<%=Command.SAVE %>">
                                        <div class="form-group">
                                            <label>Nama</label>
                                            <input type="text" required="" class="form-control" id="custName" name="<%=FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_NAME] %>" placeholder="Input nama">
                                        </div>
                                        <div class="form-group">
                                            <label>No Hp</label>
                                            <input type="text" class="form-control" id="custPhone" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE]%>" placeholder="Input no hp">
                                        </div>
                                        <div class="form-group">
                                            <label>No KTP</label>
                                            <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_KTP_NO]%>" placeholder="Input no ktp">
                                        </div>
                                        <div class="form-group">
                                            <label>Alamat Rumah</label>
                                            <textarea class="form-control" id="custAddress" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_HOME_ADDRESS]%>"></textarea>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label>Pekerjaan</label>
                                            <select class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_MEMBER_VOCATION_ID]%>">
                                                <%
                                                    Vector vVocation = PstVocation.list(0, 0, "", PstVocation.fieldNames[PstVocation.FLD_VOCATION_NAME]);
                                                    for (int i = 0; i < vVocation.size(); i++) {
                                                        Vocation vocation = (Vocation) vVocation.get(i);
                                                %>
                                                <option value="<%=vocation.getOID()%>"><%=vocation.getVocationName()%></option>
                                                <%
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Jabatan</label>
                                            <select class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_POSITION_ID]%>">
                                                <%
                                                    Vector vPosition = PstPosition.list(0, 0, "", PstPosition.fieldNames[PstPosition.FLD_POSITION]);
                                                    for (int i = 0; i < vPosition.size(); i++) {
                                                        Position position = (Position) vPosition.get(i);
                                                %>
                                                <option value="<%=position.getOID()%>"><%=position.getPosition()%></option>
                                                <%
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Status</label>
                                            <select class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_MARITAL_ID]%>">
                                                <% 
                                                    Vector vMarital = PstMarital.list(0, 0, "", PstMarital.fieldNames[PstMarital.FLD_MARITAL_STATUS]);
                                                    for (int i = 0; i < vMarital.size(); i++) {
                                                        Marital marital = (Marital) vMarital.get(i);
                                                %>
                                                <option value="<%=marital.getOID()%>"><%=marital.getMaritalStatus()%></option>
                                                <%
                                                    }
                                                %>
                                            </select>
                                        </div>
                                        <div class="form-group">
                                            <label>Alamat Kantor</label>
                                            <textarea name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_COMP_ADDRESS]%>" class="form-control"></textarea>
                                        </div>
                                        <div class="form-group">
                                            <label>Lokasi</label>
                                            <input type="hidden" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_LATITUDE]%>" id="latitude">
                                            <input type="hidden" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_LONGITUDE]%>" id="longitude">
                                            <div class="form-control" id="map_canvas" style="height: 200px"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="modal-footer">
                                <button type="submit" class="btn btn-sm btn-success"><i class="fa fa-floppy-o"></i> &nbsp; Simpan</button>
                                <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><i class="fa fa-close"></i> &nbsp; Batal</button>
                            </div>
                        </form>
                    </div>

                </div>
            </div>
                                            
            <div id="modal-biayatambahan" class="modal fade" role="dialog">
                <div class="modal-dialog">                
                    <!-- Modal content-->
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">Biaya Tambahan</h4>
                        </div>
                      <form id="addBiayaTambahan" method="post">
                            <input type="hidden" name="command" value="<%= Command.SAVE%>">
                            
                            <div class="modal-body">
                                <div class="row">
                                  <div class="col-md-12">
                                  <div class="form-group">
                                    <label class="col-sm-4">Administrasi</label>
                                    <div class="input-group col-sm-8">
                                      <input type="text" class="form-control input-sm money numberright"  id="adminFee" name="adminFee" required="" value="0">
                                    </div>
                                  </div>
                                  <div class="form-group">
                                    <label class="col-sm-4">Transportasi</label>
                                    <div class="input-group col-sm-8">
                                      <input type="text" class="form-control input-sm money numberright" id="shippingFee" name="shippingFee" required="" value="0">
                                    </div>
                                  </div>
                                  </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                              <button type="button" class="btn btn-sm btn-success" id="btnToProduction"><i class="fa fa-floppy-o"></i> &nbsp; Simpan</button>
                                <button type="button" class="btn btn-sm btn-default" data-dismiss="modal"><i class="fa fa-close"></i> &nbsp; Batal</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

            <!-- CSS FOR FINGER PRINT -->
            <style>
                .finger{
                    width:20%; 
                    height:auto;
                    padding : 2%;
                    float:left;
                }
                .finger_spot{
                    width:90%;
                    height: 90px;
                    background-color :#e5e5e5;
                    border : thin solid #c5c5c5;
                    font-size: 14px;
                    font-family:calibri;
                    text-align:center;
                    color :#FFF;
                    border-radius: 3px;
                }


                .green{
                    background-color : #5CB85C;
                    border : thin solid #4CAE4C;
                }
            </style>
            <!-- OPEN AUTORIZE -->
            <div id="openauthorize" class="modal nonprint">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="AUTHORIZE_TITLE"><b>SUPERVISOR AUTHORIZE</b></h4>
                        </div>
                        <div class="modal-body">
                            <div  id="CONTENT_AUTHORIZE">

                            </div>
                            <form id="<%= FrmAppUser.FRM_APP_USER%>">
                                <input type="hidden" name="loadtype" id="loadtypeauthorize">
                                <input type="hidden" name="command" value="<%= Command.NONE%>">
                                <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]%>" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]%>_AUTHORIZE">
                                <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]%>" value="<%= cashCashier.getOID()%>">
                                <div style="<%=cssDisplay%>" class="row">
                                    <div class="box-body">
                                        <div class="col-md-6 col-md-offset-3">
                                            <input id="spvName" type="text" class="form-control authorizeText" placeholder="Supervisor Username" name="<%= FrmAppUser.fieldNames[FrmAppUser.FRM_LOGIN_ID]%>">
                                        </div>
                                    </div>   
                                </div>
                                <div style="<%=cssDisplay%>" class="row">
                                    <div class="box-body">
                                        <div class="col-md-6 col-md-offset-3">
                                            <input id="password" type="password" autocomplete="off" class="form-control authorizeText" placeholder="Supervisor Password" name="<%= FrmAppUser.fieldNames[FrmAppUser.FRM_PASSWORD]%>">
                                        </div>
                                    </div>   
                                </div>
                                <div style="<%=cssDisplay%>" class="row">
                                    <div class="box-body">
                                        <div class="col-md-6 col-md-offset-3">
                                            <input type="submit" id="btnAuthorize" class="form-control btn btn-primary" value="Authorize">
                                        </div>
                                    </div>   
                                </div>
                            </form>
                            <%
                                if (verificationType.equals("1")) {
                                    //apabila pada sistem property menggunakan verifikasi sidik jari
                            %>
                            <div id="verFInger">
                                <div class="row">

                                    <div class="box-body">
                                        <div class="col-md-6 col-md-offset-3">
                                            <input type="text" id="spvFinger" class="form-control" placeholder="Supervisor Username" name="">
                                        </div>
                                    </div>  
                                </div>
                                <div class="row">
                                    <div class="box-body" id='dynamicPlace'>

                                    </div>  
                                </div> 
                            </div>   
                            <%}%>

                        </div>
                        <div class="modal-footer">
                            <div id="cancelButton" >
                                <button class="btn btn-danger pull-left" type="button" data-dismiss="modal"><i class="fa fa-close"></i> Close</button>
                                <button class="btn btn-primary buttonCancel" type="button" id="btnMove" data-load-type="movebill"><i class="fa fa-share"></i> Move</button>
                            </div>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. OPEN AUTORIZE -->
            <!--OPEN AUTORIZE FINGER-->
            <!-- OPEN PRINT PREVIEW -->
            <div id="openprintpreview" class="modal nonprint">
                <div class="modal-dialog" style="width: 800px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title"><b>PRINT PREVIEW</b></h4>
                        </div>
                        <div class="modal-body">
                            <div  id="CONTENT_PRINT_PREVIEW"></div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" id="print">
                                <i class="fa fa-print"></i> Print
                            </button>
                            <button type="button" data-dismiss="modal" class="btn btn-danger pull-left">
                                <i class="fa fa-close"></i> Close
                            </button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. OPEN AUTORIZE -->
            <!-- OPEN ADD ITEM -->
            <div id="stockCheck" class="modal nonprint" style="overflow-y:visible">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">           
                            <h4 class="modal-title"><b>ADD ITEM</b> - <b id="CONTENT_STOCK_TITLE"></b></h4>
                        </div>	             
                        <div class="modal-body" id="modal-body-stock"></div>         
                        <div class="modal-footer">                              
                            <button type="button" class="btn btn-danger pull-left btn-sm" data-dismiss="modal"><i class="fa fa-close"></i> Close</button>
                        </div>
                    </div>
                </div>
            </div>
            <div id="salesPackage" class="modal nonprint" style="overflow-y:visible">
                <div class="modal-dialog " style="width:1028px;"><input type='hidden' name='FRM_FIELD_OID_RESERVATION' id='FRM_FIELD_OID_RESERVATION'>
                    <div class="modal-content">
                        <div class="modal-header">           
                            <h4 class="modal-title"><b>PACKAGE</b></h4> 
                        </div>	             
                        <div class="modal-body" id="modal-body-package"></div>         
                        <div class="modal-footer">                              
                            <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Close</button>
                        </div>
                    </div>
                </div>
            </div>
            <div id="openadditem" class="modal nonprint">
                <div class="modal-dialog modal-sm">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title"><b>ADD ITEM</b> - <b id="CONTENT_ITEM_TITLE"></b></h4>
                        </div>
                        <form id="<%= FrmBillDetail.FRM_NAME_BILL_DETAIL%>_ADD_ITEM">
                            <div class="modal-body">
                                <div>
                                    <div class="row">
                                        <div class="box-body">
                                            <div class='col-md-12'>
                                                <input type="hidden" name="loadtype" value="additem"> <input type="hidden" id="isPackage" value="0">
                                                <input type="hidden" name="command" value="<%= Command.SAVE%>">
                                                <input type="hidden" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CUSTOME_PACK_BILLING_ID]%>" id="customePackBillingId"><input type="hidden" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CUSTOME_SCHEDULE_ID]%>" id="scheduleId">
                                                <input type='hidden' name='<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_MAIN_ID]%>' id='<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_MAIN_ID]%>_ADD_ITEM'>
                                                <input type="hidden" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID]%>" id="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID]%>">
                                                <input type="hidden" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE]%>" id="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE]%>">
                                                <input type="hidden" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME]%>" id="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME]%>">
                                                <div class='input-group'> 
                                                    <input type="number" class="form-control" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]%>" id="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]%>" placeholder="Quantity" step="any" required="required">

                                                    <div class='input-group-addon btn btn-primary' id='btnDetailEditor'>
                                                        <i class='fa fa-cog'></i> F12
                                                    </div> 
                                                </div>
                                                <div class="input-group" id='hiddenKeyBoard' style='margin-top:5px; display:none;'>
                                                    <button type="button" data-value='1' class="btn btn-default addNum">&nbsp;1&nbsp;</button>
                                                    <button type="button" data-value='2' class="btn btn-default addNum">&nbsp;2&nbsp;</button>
                                                    <button type="button" data-value='3' class="btn btn-default addNum">&nbsp;3&nbsp;</button>
                                                    <button type="button" data-value='4' class="btn btn-default addNum">&nbsp;4&nbsp;</button>
                                                    <button type="button" data-value='5' class="btn btn-default addNum">&nbsp;5&nbsp;</button>
                                                    <button type="button" data-value='6' class="btn btn-default addNum">&nbsp;6&nbsp;</button>
                                                    <button type="button" data-value='7' class="btn btn-default addNum">&nbsp;7&nbsp;</button>
                                                    <button type="button" data-value='8' class="btn btn-default addNum">&nbsp;8&nbsp;</button>
                                                    <button type="button" data-value='9' class="btn btn-default addNum">&nbsp;9&nbsp;</button>
                                                    <button type="button" data-value='0' class="btn btn-default addNum">&nbsp;0&nbsp;</button>
                                                    <button type="button" data-value='-1' class="btn btn-danger addNum">Clear</button>
                                                </div>
                                            </div>
											<div class='col-md-12'>
												&nbsp;
											</div>
											<div class='col-md-12' id="groupSerial" style="display: none">
												
											</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary form-control" id="btnsaveitem">
                                    <i class="fa fa-plus"></i> Add Item
                                </button>
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. OPEN ADD ITEM -->
            <!-- OPEN GUEST DETAIL -->
            <div id="openguestdetail" class="modal nonprint">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title"><b>GUEST DETAIL</b></h4>
                        </div>
                        <div class="modal-body">
                            <form id="<%= FrmBillMain.FRM_NAME_BILL_MAIN%>">
                                <div class="row">
                                    <div class="col-md-6">
                                        Guest Name
                                    </div>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME]%>" required="required">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        Pax Number
                                    </div>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER]%>" required="required">
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" id="print">
                                <i class="fa fa-print"></i> Print
                            </button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. OPEN GUEST DETAIL -->
            <!-- OPEN ITEM LIST -->
            <div id="openmovesetguest" class="modal nonprint">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title"><b>GUEST DETAIL</b></h4>
                        </div>
                        <form id="<%= FrmBillMain.FRM_NAME_BILL_MAIN%>">
                            <div class="modal-body">
                                <input type='hidden' name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]%>" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]%>">
                                <input type='hidden' name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]%>" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]%>">
                                <input type='hidden' name='loadtype' id='loadtype'>
                                <input type="hidden" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]%>" id="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]%>">
                                <input type="hidden" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE]%>" id="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE]%>">
                                <input type="hidden" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC]%>" id="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC]%>">
                                <input type="hidden" name="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID]%>" id="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID]%>">
                                <input type='hidden' name='command' value='<%= Command.SAVE%>'>		  
                                <div class="row">
                                    <div class="box-body">
                                        <div class="col-md-4">
                                            Guest Name
                                        </div>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME]%>"  id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME]%>" required="required"> 
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="box-body">
                                        <div class="col-md-4">
                                            Pax Number
                                        </div>
                                        <div class="col-md-8">
                                            <input type="text" class="form-control" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER]%>" required="required"> 
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="alert alert-danger" id="FRM_MSG" style="display: none;"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-danger pull-left" type="button" data-dismiss="modal">
                                    <i class="fa fa-close"></i> Cancel
                                </button>
                                <button class="btn btn-primary" type="submit" id="btnSaveMove">
                                    <i class="fa fa-share"></i> Move
                                </button>
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. OPEN ITEM LIST -->
            <!-- CLOSING BALANCE -->
            <div id="openclosingbalance" class="modal nonprint">
                <div class="modal-dialog  modal-lg">
                    <div class="modal-content">	  
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title"><b>CLOSING BALANCE</b></h4>
                        </div>
                        <div class="modal-body" id="CONTENT_CLOSING">	     
                        </div>
                        <form id="closing" style='display:none;'>
                            <div class='modal-body'>
                                <input type="hidden" name="loadtype" value="closing"><input type="hidden" id="isclear" value="0">
                                <input type="hidden" name="command" value="<%= Command.SAVE%>">
                                <input type="hidden" name="<%= FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CASH_CASHIER_ID]%>" value="<%= cashCashier.getOID()%>">
                                <div class="row">
                                    <div class="box-body">
                                        <div class="col-md-2">
                                            <b>Cashier Name</b>
                                        </div>
                                        <div class="col-md-2">
                                            : <%= userName%>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="box-body">
                                        <div class="col-md-2">
                                            <b>Date</b>
                                        </div>
                                        <div class="col-md-2">
                                            : <%= Formater.formatDate(cashCashier.getCashDate(), "yyyy-MM-dd")%>
                                        </div>
                                        <div class="col-md-2">
                                            <b>Start Time</b>
                                        </div>
                                        <div class="col-md-2">
                                            : <%= Formater.formatDate(cashCashier.getCashDate(), "kk:mm:ss")%>
                                        </div>
                                        <div class="col-md-2">
                                            <b>Closing Time</b>
                                        </div>
                                        <div class="col-md-2" id="closingBillTime">
                                            : <%= Formater.formatDate(new Date(), "kk:mm:ss")%>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="box-body">
                                        <div class="col-md-2">
                                            <b>Location</b>
                                        </div>
                                        <div class="col-md-6">
                                            : <%= location.getName()%>
                                        </div>
                                        <div class="col-md-2">
                                            <b>Shift</b>
                                        </div>
                                        <div class="col-md-2">
                                            : <%= shift.getName()%>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-md-12">
                                        <%= drawListClosing(listBalance, verificationType, cashCashier)%>
                                    </div>
                                </div>		
                                <div id="RESULT_ERROR">		    
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal">
                                    <i class="fa fa-close"></i> Cancel
                                </button>
                                <button type="submit" class="btn btn-success" id="closingButton">
                                    <i class="fa fa-check"></i> Closing Balance
                                </button>
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. OPEN AUTORIZE -->
            <!-- OPEN customerAddress -->
            <div id="openneworder" class="modal nonprint">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title"><b>NEW ORDER</b></h4>
                        </div>
                        <form id="<%= FrmBillMain.FRM_NAME_BILL_MAIN%>_NEWORDER">
                            <div class="modal-body">

                                <%
                                    if (cashierNeedSales.equals("1")) {
                                        if (defineGuest.equals("1")) {
                                            String optionSalesLoc = "";
                                            Vector<Location> listLocation = PstLocation.list(0, 0, "", PstLocation.fieldNames[PstLocation.FLD_NAME]);
                                            for (Location l : listLocation) {
                                                optionSalesLoc += "<option value='" + l.getOID() + "'>" + l.getName() + "</option>";
                                            }
                                %>
                                
                                <div class="form-group">
                                    <select required="" id="SALES_LOCATION" class="form-control">
                                        <option value="">-- Select Sales Location --</option>
                                        <%=optionSalesLoc%>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <select required="" class="form-control" name="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_APP_USER_SALES_ID]%>" id="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_APP_USER_SALES_ID]%>">
                                        <option value="">-- Select Sales Person --</option>
                                    </select>
                                </div>
                                    
                                <%
                                        } else {
                                            out.println("<input id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_APP_USER_SALES_ID] + "' type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_APP_USER_SALES_ID] + "' value='" + userId + "'>");
                                        }
                                    } else {
                                        out.println("<input id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_APP_USER_SALES_ID] + "' type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_APP_USER_SALES_ID] + "' value='" + userId + "'>");
                                    }
                                %>

                                <%
                                    Vector location_key = new Vector(1, 1);
                                    Vector location_value = new Vector(1, 1);
                                    location_key.add("");
                                    location_value.add(" -- Select Transaction Location --");
                                    Vector listLocation = PstLocation.listLocationStore(0, 0, "", "");
                                    if (listLocation.size() != 0) {
                                        for (int i = 0; i < listLocation.size(); i++) {
                                            Location location1 = (Location) listLocation.get(i);
                                            location_key.add("" + location1.getOID());
                                            location_value.add("" + location1.getName());
                                        }
                                    }
                                    if (multilocation.equals("0")) {
                                %>
                                <input type="hidden" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]%>" name="<%= frmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]%>" value="<%= "" + defaultOidLocation%>">
                                <%
                                } else {
                                %>
                                <%= ControlCombo.drawBootsratap(FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID], null, "" + location.getOID(), location_key, location_value, "required='required'", "form-control")%>
                                <%
                                    }
                                %>

                                <div class="form-group">
                                    <div class="input-group">
                                        <input type="text" class="form-control" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME]%>" placeholder="Guest Name"  readonly="readonly" id="employee" required='required'>
                                        <input type="hidden" name="<%= FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE]%>" id="<%= FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE]%>" value="99">
                                        <span class="btn btn-primary input-group-addon" id="searchallmemberbtn">
                                            <i class="fa fa-search"></i>
                                        </span>
                                        <span class="btn btn-success input-group-addon" id="addnewmember">
                                            <i class="fa fa-plus"></i>
                                        </span>
                                    </div>
                                    <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID]%>" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID]%>">
                                </div>

                                <div class="form-group">
                                    <input type="text" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_MOBILE_NUMBER]%>" placeholder="Phone Number" class="form-control" readonly="readonly" id="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE]%>">
                                </div>
                                
                                 <div class="form-group">
                                    <input type="text" placeholder="Address" class="form-control" readonly="readonly" id="customerAddress">
                                </div>
                                 <%if(useForRaditya.equals("1")){%>
                                <div class="form-group">            
                                        <select required="" class="form-control" name="TIPE_PENGAJUAN" id="TIPE_PENGAJUAN">
                                            <%
                                                for (int i = 0; i < tipePengajuanKey.length; i++) {
                                                    long oidTipe = Long.parseLong(tipePengajuanValue[i]);
                                            %>
                                            <option value="<%=tipePengajuanValue[i]%>"><%=tipePengajuanKey[i]%></option>
                                            <%}
                                            %>
                                        </select>
                                </div>
                                <%}%>
                                <%
                                    Vector country_key = new Vector(1, 1);
                                    Vector country_value = new Vector(1, 1);

                                    Vector listCountry = PstNegara.listAll();
                                    if (listCountry.size() != 0) {
                                        for (int i = 0; i < listCountry.size(); i++) {
                                            Negara negara = (Negara) listCountry.get(i);
                                            country_key.add("" + negara.getNmNegara());
                                            country_value.add("" + negara.getNmNegara());
                                        }
                                    }
                                    if (useCountry.equals("1")){
                                %>
                                <%= ControlCombo.drawBootsratap(FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_COUNTRY], null, "", country_key, country_value, "required='required'", "form-control")%>

                                <%} if (enableDutyFree.equals("1")) {%>
                                <!-- ADDED BY DEWOK 20191002 FOR DUTY FREE -->
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <input type="text" placeholder="Flight Number" required="" class="form-control" name="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_FLIGHT_NUMBER]%>" id="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_FLIGHT_NUMBER]%>">
                                        </div>
                                    </div>
                                </div>
                                        
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <input type="text" placeholder="Flight Date Time" required="" autocomplete="off" class="form-control datePicker3" name="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_DATE]%>" id="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_DATE]%>">
                                        </div>
                                    </div>
                                </div>
                                        
                                <%}%>
                                
                                <%
                                    Vector type_key = new Vector(1, 1);
                                    Vector type_value = new Vector(1, 1);

                                    type_key.add("0");
                                    type_value.add("Cash");
                                    
                                    type_key.add("1");
                                    type_value.add("Credit");
                                %>
                                <%= ControlCombo.drawBootsratap(FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TRANS_TYPE], null, "", type_key, type_value, "required='required'", "form-control")%>

                                <div class="form-group">
                                    <input type="text" class="form-control" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER]%>" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER]%>" placeholder="Pax Number" required='required'>
                                    <div class="input-group" id='neworderKeyBoard' style='margin-top:5px; display:none;'>
                                        <button type="button" data-value='1' class="btn btn-default addNum">&nbsp;1&nbsp;</button>
                                        <button type="button" data-value='2' class="btn btn-default addNum">&nbsp;2&nbsp;</button>
                                        <button type="button" data-value='3' class="btn btn-default addNum">&nbsp;3&nbsp;</button>
                                        <button type="button" data-value='4' class="btn btn-default addNum">&nbsp;4&nbsp;</button>
                                        <button type="button" data-value='5' class="btn btn-default addNum">&nbsp;5&nbsp;</button>
                                        <button type="button" data-value='6' class="btn btn-default addNum">&nbsp;6&nbsp;</button>
                                        <button type="button" data-value='7' class="btn btn-default addNum">&nbsp;7&nbsp;</button>
                                        <button type="button" data-value='8' class="btn btn-default addNum">&nbsp;8&nbsp;</button>
                                        <button type="button" data-value='9' class="btn btn-default addNum">&nbsp;9&nbsp;</button>
                                        <button type="button" data-value='0' class="btn btn-default addNum">&nbsp;0&nbsp;</button>
                                        <button type="button" data-value='-1' class="btn btn-danger addNum">Clear</button>
                                    </div>
                                </div>

                                <%
                                    Vector room_key = new Vector(1, 1);
                                    Vector room_value = new Vector(1, 1);
                                    room_key.add("");
                                    room_value.add("All Room");
                                    Vector listRoom = PstRoom.list(0, 0, "" + PstRoom.fieldNames[PstRoom.FLD_LOCATION_ID] + "='" + defaultOidLocation + "'", "");
                                    if (listRoom.size() != 0) {
                                        for (int i = 0; i < listRoom.size(); i++) {
                                            Room room = (Room) listRoom.get(i);
                                            room_key.add("" + room.getOID());
                                            room_value.add("" + room.getName());
                                        }
                                    }
                                %>
                                <!-- MAKE CONTROL COMBO HERE -->
                                <%= ControlCombo.drawBootsratap(FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID], null, "", room_key, room_value, "required='required'", "form-control")%>

                                <div class="form-group">
                                    <select name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID]%>" id="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID]%>" class="form-control" required='required'>			
                                    </select>
                                </div>
                                    
                            </div>
                            <div class="modal-footer">
                                <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]%>" value="<%= cashCashier.getOID()%>">
                                <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIFT_ID]%>" value="<%= shift.getOID()%>">
                                <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]%>" value="<%= cashCashier.getOID()%>">
                                <input type="hidden" name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_APP_USER_ID]%>" value="<%= userId%>">
                                <input type="hidden" id="oidReservationOrder"  name="<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_RESERVATION_ID]%>" value="0">
                                <input type="hidden" name="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ID_NEGARA]%>" id="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ID_NEGARA]%>" value="">
                                <input type="hidden" name="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GENDER]%>" id="<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GENDER]%>" value="">
                                <input type="hidden" name="loadtype" value="neworder">
                                <input type="hidden" name="command" value="<%= Command.SAVE%>">
                                <button type="submit" class="btn btn-primary form-control" id="btnneworder">
                                    <i class="fa fa-check"></i> Save Order
                                </button>
                            </div>	      
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. OPEN NEW ORDER -->
            <!-- OPEN NEW MEMBER -->
            <div id="opennewmember" class="modal nonprint">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title"><b>NEW MEMBER</b></h4>
                        </div>
                        <form id="<%= FrmContactList.FRM_NAME_CONTACTLIST%>">
                            <input type="hidden" name="command" value="<%= Command.SAVE%>">
                            <input type="hidden" name="loadtype" value="newmember">
                            <input type="hidden" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_LOCATION_ID]%>" id="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_LOCATION_ID]%>">
                            <%%>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_NAME]%>" id="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_NAME]%>" placeholder="Guest Name" required="required">
                                        </div>
                                    
                                        <div class="form-group">
                                            <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE]%>" id="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE]%>" placeholder="Phone Number" required="required">
                                        </div>
                                        
                                        <%if (enableDutyFree.equals("1")) {%>
                                        <div class="form-group">
                                            <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PASSPORT_NO]%>" id="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PASSPORT_NO]%>" placeholder="Passport Number" required="required">
                                        </div>
                                        
                                        <div class="form-group">
                                            <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_MEMBER_ID_CARD_NUMBER]%>" id="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_MEMBER_ID_CARD_NUMBER]%>" placeholder="Control Number">
                                        </div>
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
                                <button type="submit" class="btn btn-primary" id="btnaddnewmember"><i class="fa fa-check"></i> Save</button> 
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <div id="opennewcustomer" class="modal nonprint">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title"><b>ADD NEW MEMBER</b></h4>
                        </div>
                        <form id="FRM_NEW_MEMBER">
                            <input type="hidden" name="command" value="<%= Command.SAVE%>">
                            <input type="hidden" name="loadtype" value="savenewcustomer">
                            <input type="hidden" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_LOCATION_ID]%>" id="">
                            <div class="modal-body">
                                <div class="row">
                                    <div class="box-body">
                                        <div class="col-md-12">
                                            <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_CONTACT_CODE]%>" id="" placeholder="Member Code" required="required">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="box-body">
                                        <div class="col-md-12">
                                            <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_NAME]%>" id="" placeholder="Member Name" required="required">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="box-body">
                                        <div class="col-md-12">
                                            <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE]%>" id="" placeholder="Phone Number" required="required">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="box-body">
                                        <div class="col-md-12">
                                            <select class="form-control" required="" name="FRM_FIELD_MEMBER_GROUP">
                                                <% for (MemberGroup mg : listGroup) {%>
                                                <option value="<%= mg.getOID()%>"><%= mg.getName()%></option>
                                                <% }%>
                                            </select>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
                                <button type="submit" class="btn btn-primary" id="saveNewMember"><i class="fa fa-check"></i> Save New Member</button> 
                            </div>
                        </form>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. OPEN NEW MEMBER -->
            <!-- OPEN SEARCH MEMBER -->
            <div id="opensearchmember" class="modal nonprint">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title"><b>SEARCH</b></h4>
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="Search..." id="searchitem"/>
                                <div class="input-group-addon btn btn-primary" id="btnSearchMember">
                                    <i class="fa fa-search"></i>
                                </div>
                            </div>
                        </div>
                        <div class="modal-body" id="CONTENT_SEARCH_MEMBER">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
                            <div id="buttonInHouseGuest" class="btn-group" style="display:none;">		
                                <button class="btn btn-default btnsearchtype" type="button" id="btnGuest" data-search-type="guest"><i class="fa fa-user"></i> Guest</button>
                                <button class="btn btn-default btnsearchtype" type="button" id="btnTravelagent" data-search-type="travelagent"><i class="fa fa-building"></i> Travel Agent</button>
                            </div> 
                            <!--
                            <button type="button" id="btnSelect" class="btn btn-primary"><i class="fa fa-check"></i> Open</button> 
                            -->
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- /. OPEN SEARCH MEMBER -->
            <!-- OPEN AUTORIZE PAYMENT -->
            <div id="openAuthorizePayment" class="modal nonprint">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title" id="AUTHORIZE_TITLE"><b>AUTHORIZE</b></h4>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="box-body">
                                    <div class="col-md-6 col-md-offset-3">
                                        <input type="text" id="spvFingerPayment" class="form-control" placeholder="Username" name="">
                                    </div>
                                </div> 
                            </div>
                            <div class="row">
                                <div class="box-body" id='dynamicPlacePayment'>                   
                                </div>  
                            </div>       
                        </div>
                        <div class="modal-footer">
                            <div id="cancelButton" style="display:none;">
                                <button class="btn btn-danger pull-left" type="button" data-dismiss="modal"><i class="fa fa-close"></i> Close</button
                            </div>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
        </div>
        <!-- /. OPEN AUTORIZE PAYMENT-->
        <!-- OPEN AUTHORIZE PAYMENT (NOT FINGER)-->
        <div id="openAuthorizePaymentNoFinger" class="modal nonprint">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="AUTHORIZE_TITLE"><b>AUTHORIZE</b></h4>
                    </div>
                    <div class="modal-body">
                        <form id="verificationPassword">
                            <div class="row">
                                <div class="box-body">
                                    <div class="col-md-6 col-md-offset-3">
                                        <input type="text" id="spvPayment"  class="form-control required" placeholder="Username" title="Username" name="">
                                    </div>
                                </div> 
                            </div>
                            <div class="row">
                                <div class="box-body">
                                    <div class="col-md-6 col-md-offset-3">
                                        <input type="password" id="spvPaymentPass" class="form-control required" placeholder="Password" title="Password" name="">
                                    </div>
                                </div> 
                            </div>
                            <div class="row">
                                <div class="box-body">
                                    <div class="col-md-6 col-md-offset-3">
                                        <button type="button" id="btnAuthorizePaymentPass" class="form-control btn btn-primary" value="Authorize">Authorize</button>
                                    </div>
                                </div> 
                            </div> 
                        </form>
                    </div>
                    <div class="modal-footer"></div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
    </div>
    <!-- OPEN AUTHORIZE PAYMENT (NOT FINGER)-->
    <!-- SEARCH CUSTOMER BY KOYO -->
    <div id="openCtCustomer" class="modal nonprint">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="AUTHORIZE_TITLE"><b>SEARCH</b></h4>
                    <div class='ctBodyUp'></div>
                </div>
                <div class="modal-body">
                    <div class="ctBodyDown"></div>
                </div>
                <div class="modal-footer">
                    <div id="cancelButton">
                        <button class="btn btn-danger pull-left" type="button" data-dismiss="modal"><i class="fa fa-close"></i> Close</button
                    </div>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
</div>
<!--END OF SEARCH CUSTOMER -->
<!-- ADD NEW CT CUSTOMER KOYO -->
<div id="addCtCustomer" class="modal nonprint">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="AUTHORIZE_TITLE"><b>NEW CUSTOMER</b></h4>
            </div>
            <form id="<%= FrmContactList.FRM_NAME_CONTACTLIST%>2">
                <input type="hidden" name="command" value="<%= Command.SAVE%>">
                <input type="hidden" name="loadtype" value="newctmember">
                <input type="hidden" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_LOCATION_ID]%>" id="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_LOCATION_ID]%>">	    
                <div class="modal-body">
                    <div class="row">
                        <div class="box-body">
                            <div class="col-md-12">
                                <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_NAME]%>" id="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_NAME]%>" placeholder="Guest Name" required="required">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="box-body">
                            <div class="col-md-12">
                                <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE]%>" id="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE]%>" placeholder="Phone Number" required="required">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="box-body">
                            <div class="col-md-12">
                                <input type="text" class="form-control" name="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_COMP_NAME]%>" id="<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_COMP_NAME]%>" placeholder="Company" required="required">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" id="btnAddCtCustomer"><i class="fa fa-check"></i> Save</button> 
                    <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>	    
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>
<!-- END OF CT CUSTOMER -->
<!-- NEW ORDER CONFIRMATION -->
<div id="newOrderConfirmation" class="modal nonprint">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="AUTHORIZE_TITLE"><b>Confirmation</b></h4>
            </div>
            <div class="modal-body">
                The older transaction is already exist. 
                <% if (enableNewOrder.equals("1")) {%>
                Do you want to continue ?
                <% }%>
            </div>
            <div class="modal-footer">
                <div id="cancelButton">
                    <% if (enableNewOrder.equals("1")) {%>
                    <button id="btnNewOrderClick" class="btn btn-primary" type="button" data-dismiss="modal"><i class="fa fa-check"></i> Yes</button>
                    <button class="btn btn-danger" type="button" data-dismiss="modal"><i class="fa fa-close"></i> No</button>
                    <% }%>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>
<!--END OF SEARCH CUSTOMER -->
<!-- REPRINT CONFIRMATION -->
<div id="reprintConfirmation" class="modal nonprint">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="AUTHORIZE_TITLE"><b>Confirmation</b></h4>
                <input type="hidden" id="oidBilllx" val="0">
                <input type="hidden" id="oidMemberx" val="0">
                <input type="hidden" id="appRootX" value="">
            </div>
            <div class="modal-body">
                Please select the kind of bill that you want to reprint! 
            </div>
            <div class="modal-footer">
                <div id="cancelButton">
                    <button id="btnFullPrint" data-type="0" class="btn btn-info printOption" type="button" ><i class="fa fa-star-half-o"></i> Full</button>
                    <button id="btnPaymentOnly" data-type="1" class="btn btn-primary printOption" type="button" ><i class="fa fa-star-half"></i> Payment Only</button>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</div>
<!--END OF REPRINT -->
<!-- MODAL MULTI PAYMENT -->
<div id="multiPayment" class="modal nonprint">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <div class="row">
                    <div class="col-md-6">
                        <h4 class="modal-title"><b>MULTI PAYMENT</b></h4>
                    </div>
                    <div class="col-md-6" style="text-align: right;" id="CONTENT_INFO">
                        <div class="col-md-12" style="background-color: #222D32;border-radius: 5px;color:#fff;">
                            <h2 class="modal-title col-md-4 pull-left" style="font-weight: bold;color:#a1a1a1;" id="info">REMAIN</h2> <h2 class="modal-title col-md-8 pull-right" id="remainMulti">114,310</h2>
                        </div>		    
                    </div>
                </div>
            </div>
            <form id="multiPaymentForm">
                <input type="hidden" id="remainMultiPayment" name="remainMultiPayment">
                <input type="hidden" id="multiPayCashBillMainId" name="multiPayCashBillMainId">
                <div class="modal-body" id="multiPaymentBody">
                </div>
            </form>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
                <button style="display:none" type="button" class="btn btn-warning" id="btnPrintSeparateMulti"><i class="fa fa-check"></i> Save & Print Payment Only</button> 
                <button style="display:none" type="button" class="btn btn-primary" id="btnSaveMultiPayment"><i class="fa fa-check"></i> Save</button> 
                <button style="display:none" type="button" class="btn btn-success" id="btnSaveMultiPaymentX"><i class="fa fa-check"></i> Pay</button>
                <button type="button" class="btn btn-primary" id="btnAddMultiPayment"><i class="fa fa-plus"></i> Add Payment</button> 
            </div>
        </div>
    </div>
</div>
<!-- END OF MODAL MULTI PAYMENT -->
<!-- MODAL ADD MULTI PAYMENT -->
<div id="addMultiPayment" class="modal nonprint">
    <div class="modal-dialog modal-lg" style="width: 700px;">
        <div class="modal-content">
            <div class="modal-header">           
                <h4 class="modal-title"><b>ADD PAYMENT</b></h4> 
            </div>	  
            <form id="multiPaymentFormAdd">
                <input type="hidden" id="multiPayCashBillMainId2" name="multiPayCashBillMainId">
                <div class="modal-body" id="addMultiPaymentBody">
                    <div class="row">
                        <div class="col-md-3">Transaction Type</div>
                        <input type="hidden" name="multiTransactionType" id="multiTransactionType" value="0">
                        <div class="col-md-9"><input type="text" class="form-control" readonly value="CASH"></div>
                    </div>
                    <div class="row" style="margin-top:7px;">
                        <div class="form-group">
                            <%
                                Vector paymentType_key = new Vector(1, 1);
                                Vector paymentType_value = new Vector(1, 1);
                                paymentType_key.add("");
                                paymentType_value.add("-- Select --");
                                paymentType_key.add("" + PstCustomBillMain.PAYMENT_TYPE_CASH);
                                paymentType_value.add("" + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CASH]);
                                paymentType_key.add("" + PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD);
                                paymentType_value.add("" + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD]);
                                paymentType_key.add("" + PstCustomBillMain.PAYMENT_TYPE_CHECK);
                                paymentType_value.add("" + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CHECK] + " OR " + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_BG]);
                                paymentType_key.add("" + PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD);
                                paymentType_value.add("" + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD]);
                                paymentType_key.add("" + PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR);
                                paymentType_value.add("" + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR]);
                                paymentType_key.add("" + PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT);
                                paymentType_value.add("" + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT]);
                            %>
                            <div class="col-md-3">Payment Type</div>                   
                            <div class="col-md-9"><%=ControlCombo.drawBootsratap("MASTER_" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE] + "_MULTI", null, "", paymentType_key, paymentType_value, "required='true'", "form-control payTypes")%></div>
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
<div id="salesModal" class="modal nonprint">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">           
                <h4 class="modal-title"><b>SALES LIST</b></h4> 
            </div>	  
            <%
                Vector salesPerson_key = new Vector(1, 1);
                Vector salesPerson_value = new Vector(1, 1);
                Vector userSales = PstAppUser.getSalesUserByLocation(location.getOID());
                salesPerson_key.add("");
                salesPerson_value.add("-- Select --");
                
                for (int sp = 0; sp < userSales.size(); sp++) {
                Vector newList = (Vector) userSales.get(sp);
                AppUser appUser = (AppUser) newList.get(0);
                MappingUserGroup mg = (MappingUserGroup) newList.get(1);
                DataCustom dc = (DataCustom) newList.get(2);
                salesPerson_key.add("" + appUser.getOID()+ "");
                salesPerson_value.add("" + appUser.getFullName()+ "");
                  
                }

               // String where = PstSales.fieldNames[PstSales.FLD_ASSIGN_LOCATION_WAREHOUSE]+" = "+location.getOID()+
               //                 " AND STATUS = 0";
               // Vector listSalesPerson = PstSales.list(0, 0, where, "");
               // for (int sp = 0; sp < listSalesPerson.size(); sp++) {
              //      Sales matSales = (Sales) listSalesPerson.get(sp);
              //      salesPerson_key.add("" + matSales.getCode() + "");
             //       salesPerson_value.add("" + matSales.getName() + "");
             //   }

                Vector listNegara = PstNegara.list(0, 0, "", "");
                Vector negaraKey = new Vector(1, 1);
                Vector negaraVal = new Vector(1, 1);
                negaraVal.add("");
                negaraKey.add("--Select--");
                for (int ng = 0; ng < listNegara.size(); ng++) {
                    Negara negara = (Negara) listNegara.get(ng);
                    negaraKey.add(negara.getNmNegara());
                    negaraVal.add(String.valueOf(negara.getOID()));
                }
            %>
            <div class="modal-body">
                <div class="row" style="margin-top:7px;">
                    <div class="col-md-3">Sales Person</div>                   
                    <div class="col-md-9">
                        <%=ControlCombo.drawBootsratap("FRM_SALES", null, "", salesPerson_key, salesPerson_value, "required='true'", "form-control payTypes")%>
                    </div>
                    <% if (defineGuest.equals("1")) { %>
                    <div class="col-sm-12">
                        <hr style="border-color: lightgray">
                    </div>
                    <div class="col-md-3">Member Code</div>                   
                    <div class="col-md-9">
                        <div class="input-group">
                            <input type="text" id="memberCode" placeholder="Search member code" class="form-control">
                            <span class="input-group-addon btn btnOpenSearchMember2"><i class="fa fa-search"></i></span>
                        </div>
                    </div>
                    <div class="col-md-3">Name</div>                   
                    <div class="col-md-9">
                        <div class="input-group">
                            <input type="text" id="memberName" placeholder="Search member name" class="form-control">
                            <span class="input-group-addon btn btnOpenSearchMember2"><i class="fa fa-search"></i></span>
                        </div>
                    </div>
                    <div class="col-md-3">Phone</div>                   
                    <div class="col-md-9">
                        <input type="text" id="memberPhone" class="form-control">
                    </div>
                    <% } %>
                    
                    <% if (useForGB.equals("1")){ %>
                    <div class="col-sm-12">
                        <hr style="border-color: lightgray">
                    </div>
                    <div class="col-md-3">Customer Region</div>                   
                    <div class="col-md-9">
                        <%=ControlCombo.drawBootsratap("FRM_REGION", null, "", negaraVal, negaraKey, "required='true'", "form-control")%>
                    </div>
                    <div class="col-md-3">Customer Gender</div> 
                    <div class="col-md-4">
                        <label class="radio-inline"><input type="radio" id="genderMale" name="FRM_GENDER" value="0">Male</label>
                    </div>
                    <div class="col-md-4">
                        <label class="radio-inline"><input type="radio" id="genderFemale" name="FRM_GENDER" value="1">Female</label>
                    </div>
                    <% } %>
                </div>   
            </div>         
            <div class="modal-footer">
                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
                <button type="button" class="btn btn-primary" id="btnSaveSalesModal"><i class="fa fa-check"></i> Save</button>                 
            </div>
        </div>
    </div>
</div>
<!-- END OF MODAL MULTI PAYMENT -->
<div id="updatesalesModal" class="modal nonprint">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">           
                <h4 class="modal-title"><b>UPDATE BILL CUSTOMER</b></h4> 
            </div>	  
            <div class="modal-body">
                <div class="hidden">
                    <div class="input-group">
                        <input type="text" placeholder="Search member" class="form-control">
                        <span class="input-group-addon btn btnOpenSearchNewMember"><i class="fa fa-search"></i></span>
                    </div>
                </div>

                <button type="button" class="btn btn-default" id="addNewCustomer"><i class="fa fa-plus"></i>&nbsp; New Member</button>
                <button type="button" class="btn btn-default btnOpenSearchNewMember pull-right"><i class="fa fa-search"></i>&nbsp; Search Member</button>
                <hr>
                <div class="row" style="margin-top:7px;">
                    <input type="hidden" id="newMemberOid" value="">
                    <div class="col-md-3">Member Code</div>
                    <div class="col-md-9">
                        <div class="">
                            <input type="text" readonly="" id="newMemberCode" class="form-control">
                            <!--span class="input-group-addon btn btnOpenSearchNewMember"><i class="fa fa-search"></i></span-->
                        </div>
                    </div>
                    <div class="col-md-3">Name</div>                   
                    <div class="col-md-9">
                        <div class="">
                            <input type="text" readonly="" id="newMemberName" class="form-control">
                            <!--span class="input-group-addon btn btnOpenSearchNewMember"><i class="fa fa-search"></i></span-->
                        </div>
                    </div>
                    <div class="col-md-3">Phone</div>                   
                    <div class="col-md-9">
                        <input type="text" readonly="" id="newMemberPhone" class="form-control">
                    </div>
                    <div class="col-md-3">Member Group</div>
                    <div class="col-md-9">
                        <select id="newMemberGroup" class="form-control" disabled="">
                            <option value="0">-</option>
                            <% for (MemberGroup mg : listGroup) {%>
                            <option value="<%= mg.getOID()%>"><%= mg.getName()%></option>
                            <% }%>
                        </select>
                    </div>
                </div>   
            </div>         
            <div class="modal-footer">
                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
                <button type="button" class="btn btn-danger" id="btnDeleteMember"><i class="fa fa-close"></i> Remove Member</button>
                <button type="button" class="btn btn-primary" id="btnUpdateSalesModal"><i class="fa fa-check"></i> Save</button>
            </div>
        </div>
    </div>
</div>
<div id="reservationPackage" class="modal nonprint">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title"><b>RESERVATION LIST</b></h4>
                <div class="input-group">
                    <input type="text" class="form-control" placeholder="Search..." id="searchReservationInput"/>
                    <div class="input-group-addon btn btn-primary" id="btnSearchReservation">
                        <i class="fa fa-search"></i>
                    </div>
                </div>
            </div>	             
            <div class="modal-body" id="modal-body-package-reservation">
                <div class="row"></div>
            </div>         
            <div class="modal-footer">                              
                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Close</button>
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
<div id="modalCoverNumber" class="modal nonprint">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Input Bill Number </h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-md-12">
                        <textarea class="form-control" name="covernumber" id="covernumber"></textarea>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-3"> <label>Event</label>
                                <select class="form-control" name="master_event"  id="master_event">
                                    <option value="">Select</option><option value="Lunch">Lunch</option><option value="Dinner">Dinner</option><option value="High Tea">High Tea</option>
                                    <option value="Breakfast">Breakfast</option><option value="Gala Dinner">Gala Dinner</option><option value="Pack Lunch">Pack Lunch</option>
                                    <option value="Ghari">Ghari</option><option value="Mahendi Dinner">Mahendi Dinner</option><option value="Mahendi Lunch">Mahendi Lunch</option>
                                    <option value="Receptions">Receptions</option><option value="Welcome Dinner">Welcome Dinner</option><option value="Others">Others</option>
                                </select> 
                            </div>
                            <div class="col-md-3"><label>Group</label>
                                <select class="form-control" name="master_group" id="master_group"><option value="">Select</option><option value="FIT">FIT</option><option value="GIT">GIT</option></select>
                            </div>
                            <div class="col-md-3"><label>Payment</label><select class="form-control" name="master_payment" id="master_payment"><option value="">Select</option><option value="CRD">Credit</option><option value="CSH">Cash</option><option value="TRF">Bank Transfer</option></select>
                            </div>
                            <div class="col-md-3"><label>Guide</label>
                                <input type="text" name ="master_guide" id="master_guide" value="" class="form-control">
                            </div>
                        </div>
                    </div>    
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" id="savecovernumber" class="btn btn-primary">Save</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- END OF SALES MODAL tempat cover number-->

<div id="modalReturnExchange" class="modal nonprint">
    <div class="modal-dialog modal-lg" style="width: 1300px">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">RETURN EXCHANGE</h4>
            </div>
            <div class="modal-body">
                <div id="modalBodyReturnExchange">

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
                <button type="button" class="btn btn-success" id="btnPayReturnExchange"><i class="fa fa-check"></i> Pay</button>
            </div>
        </div>
    </div>
</div>

<div id="modalMultiPaymentReturnExchange" class="modal nonprint">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">ADD PAYMENT</h4>
            </div>
            <div class="modal-body">
                <div class="form-horizontal">
                    <form id="FRM_MULTI_PAYMENT_EXCHANGE">
                        <input type="hidden" name="command" value="<%= Command.NONE%>">
                        <input type="hidden" name="loadtype" value="addMultiPayExchange">
                        <div class="form-group">
                            <label class="col-sm-4">Transaction Type</label>
                            <div class="col-sm-8">
                                <input type="text" readonly="" value="CASH" class="form-control" id="FRM_MULTI_EXC_TRANSACTION_TYPE">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4">Payment Type</label>
                            <div class="col-sm-8">
                                <select class="form-control" name="FRM_MULTI_EXC_PAYMENT_ID" id="FRM_MULTI_EXC_PAYMENT_ID">
                                    <%
                                        Vector<PaymentSystem> listPayment = PstPaymentSystem.list(0, 0, "", PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_SYSTEM]);
                                        for (PaymentSystem ps : listPayment) {
                                    %>
                                    <option value="<%= ps.getOID()%>" data-type="<%= ps.getPaymentType()%>"><%= ps.getPaymentSystem()%></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-4">Payment Value</label>
                            <div class="col-sm-8">
                                <input type="text" value="0" class="form-control" name="FRM_MULTI_EXC_PAYMENT_VALUE" id="FRM_MULTI_EXC_PAYMENT_VALUE">
                            </div>
                        </div>

                        <div class="form-group hidden">
                            <label class="col-sm-4">Card Name</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="FRM_MULTI_EXC_CARD_NAME" id="FRM_MULTI_EXC_CARD_NAME">
                            </div>
                        </div>

                        <div class="form-group hidden">
                            <label class="col-sm-4">Card Number</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="FRM_MULTI_EXC_CARD_NUMBER" id="FRM_MULTI_EXC_CARD_NUMBER">
                            </div>
                        </div>

                        <div class="form-group hidden">
                            <label class="col-sm-4">Bank</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" name="FRM_MULTI_EXC_BANK_NAME" id="FRM_MULTI_EXC_BANK_NAME">
                            </div>
                        </div>

                        <div class="form-group hidden">
                            <label class="col-sm-4">Validity / Expired Date</label>
                            <div class="col-sm-8">
                                <div class="input-group">
                                    <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                    <input type="text" class="form-control date-picker" name="FRM_MULTI_EXC_EXPIRED_DATE" id="FRM_MULTI_EXC_EXPIRED_DATE">
                                </div>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger pull-left" data-dismiss="modal"><i class="fa fa-close"></i> Cancel</button>
                <button type="button" class="btn btn-success" id="btnAddMultiPayExc"><i class="fa fa-check"></i> Ok</button>
            </div>
        </div>
    </div>
</div>

<div id="modalItemListImage" class="modal nonprint">
    <div class="modal-dialog" style="width: 1300px">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">ITEM VIEW</h4>
            </div>
            <div class="modal-body">
                <label>CATEGORY :</label>
                <input type="hidden" id="selectedCategory" value="0">
                <div class="table-responsive" style="background-color: lightgrey; padding-left: 10px">
                    <table>
                        <tr>
                            <%
                                Vector<Category> listCategory = PstCategory.list(0, 0, "", PstCategory.fieldNames[PstCategory.FLD_NAME]);
                                for (Category c : listCategory) {
                            %>

                            <td style="padding: 10px; padding-left: 0px">
                                <div class="panel panel-info panel_category" data-category="<%= c.getOID() %>" style="cursor: pointer; margin-bottom: 0px">
                                    <div class="panel-heading text-nowrap"><%= c.getName().toUpperCase()%></div>
                                </div>
                            </td>

                            <%
                                }
                            %>
                            
                            <%
                                if(listCategory.isEmpty()) {
                            %>
                            
                            <td style="padding: 10px 0px"><i class="fa fa-exclamation-circle text-red"></i> No category found</td>
                            
                            <%
                                }
                            %>
                        </tr>
                    </table>
                </div>

                <br>

                <div class="row">
                    <div class="col-sm-8" style="padding-right: 0px">
                        <div class="form-group">
                            <div class="form-inline">
                                <div class="input-group">
                                    <span class="input-group-addon text-bold">Data show :</span>
                                    <select class="form-control" id="dataShow">
                                        <option value="4">4</option>
                                        <option value="8">8</option>
                                        <option value="16">16</option>
                                        <option value="32">32</option>
                                    </select>
                                </div>
                                <div class="input-group pull-right">
                                    <span class="input-group-addon text-bold">Search item :</span>
                                    <input type="text" size="50" id="searchItemImg" class="form-control" placeholder="Type item name / sku or scan barcode">
                                    <span class="input-group-addon btn" id="btnSearchItemImg"><i class="fa fa-search"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <label style="padding-top: 18px">Item selected :</label>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-8" style="max-height: 580px; overflow-y: auto">
                        <div class="row" style="background-color: lightgrey; margin-left: 1px; padding-right: 15px;">
                            <div id="CONTENT_GRID_ITEM_IMG">
                                
                            </div>
                        </div>
                    </div>
                        
                    <div class="col-sm-4">
                        <table class="table" style="font-size: 14px; border-style: solid; border-width: thin;">
                            <thead>
                                <tr style="background-color: #d9edf7; color: #31708f">
                                    <th>Name</th>
                                    <th>Price</th>
                                    <th>Disc</th>
                                    <th>Qty</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody id="CONTENT_LIST_BILL_DETAIL">
                                
                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-close"></i> Close</button>
            </div>
        </div>
    </div>
</div> 
<!-- Form ke sedana -->
<form id="frmPengajuanKredit" method="post" action="cashier_iframe.jsp">
    <input type="hidden" name="command" value="<%=Command.ADD%>">
    <input type="hidden" name="autologin" value="1">
    <input type="hidden" name="username" value="<%=userCashier.getAppUser().getLoginId()%>">
    <input type="hidden" name="password" value="<%=userCashier.getAppUser().getPassword()%>">
    <input type="hidden" name="billId" id="billId" value="0">
    <input type="hidden" name="TIPE_PENGAJUAN" id="CATEGORY_PENGAJUAN" value="0">
</form>
<%@include file="cashier-plugin.jsp" %>
</body>
</html>
