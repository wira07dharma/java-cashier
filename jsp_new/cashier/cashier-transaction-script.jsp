<%@page import="com.dimata.cashierweb.entity.masterdata.PstDiscountQtyMapping"%>
<%@page import="com.dimata.hanoman.entity.masterdata.PstContactClass"%>
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

<%//
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
    String editItemSpv = PstSystemProperty.getValueByName("CASHIER_EDIT_ITEM_USING_SUPERVISOR");
    String cashierShowTrans = PstSystemProperty.getValueByName("CASHIER_SHOW_RETURN_ON_TRANSACTION");
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
    String itemListView = PstSystemProperty.getValueByName("ITEM_LIST_VIEW");
    String useExchangeAsDefault = PstSystemProperty.getValueByName("USE_EXCHANGE_AS_DEFAULT");
    String enableDutyFree = PstSystemProperty.getValueByName("ENABLE_DUTY_FREE");
    String creditType = PstSystemProperty.getValueByName("CREDIT_TYPE");
    String useProduction = PstSystemProperty.getValueByName("USE_PRODUCTION");
%>

<script type="text/javascript">
    $(document).ready(function(){
        var maxDisc;
        var autoFocusCash = true;
        
        $('#modalCoverNumber').modal({
            backdrop: 'static',
            keyboard: false,
            show : false
        });
        
        function coverNumber(separateprint){
            $("#modalCoverNumber").modal("show");
            $("#covernumber").val("");
        }
        
        var idInputFocus = "";
        var creditPass = 0;
        
        if(<%= listOpeningCashier.size() %> == 0){
            window.location = "<%= approot %>/cashier/cashier-home.jsp";
        }
        
        var kasirViewOutlet = "<%= kasirViewOutlet%>";
        var cashierSupportProduction = $('#cashierSupportProduction').val();
        var bases = "<%=baseURL%>";
        var printWhenClosing = "<%= printWhenClosing%>";
        
        var modalSetting = {
            "backdrop":"static",
            "keyboard":false,
            "show":false
        };
        
        var modalSetting2 = {
            "backdrop":"static",
            "keyboard":true,
            "show":false
        };
        
        $("#openpay").modal(modalSetting2);
        $("#openbill").modal(modalSetting2);
        $("#opensearchmember").modal(modalSetting);
        $("#openauthorize").modal(modalSetting2);
        $("#openprintpreview").modal(modalSetting2);
        $("#openclosingbalance").modal(modalSetting2);
        $("#openitemlist").modal(modalSetting2);
        $("#openmovesetguest").modal(modalSetting);
        $("#openadditem").modal(modalSetting);
        $("#openneworder").modal(modalSetting);
        $("#opennewmember").modal(modalSetting);
        //PREPARE 
        $('#mainBillIdSource').val('');
        $('#searchBarcode').val('');
        $('#searchItemModal').val('');
        if ($('#mainBillIdSource').val().length==0){
            $('#searchItemFront').attr('disabled','true');
        }else{
            $('#searchItemFront').removeAttr()('disabled');
        }
        
        //TABLE NAVIGATION
        function tableNav(){
            jQuery.tableNavigation();
        }
        
        var lockKeyCode=[
            "112","113","114","115","116","117","118","119","120","121","122","123","124","125","126","33","13"    //all of function
        ];
        
        var openDiscUsingSpv= "<%= cashierDiscountBillUsingSupervisor%>";
        
        function globalKeyboard(e){
            var listControl = ["116","117","118","114","122","115","113","120","121"];
            var listEvent = ["click","click","click","click","click","click","click","click","click"];
            var listDestination = [
                "neworder2","openBillSearch","searchItemFront","btnReprintBill","btnClosingBalance","btnReturnBill","btnCancel","btnMove","btnJoinBillTemp"
            ];
            var eventCode = String(e.keyCode);
            var index = listControl.indexOf(eventCode); 
            var event = listEvent[index];
            var destination = listDestination[index];
            return event + ":" + destination;
        }
        
        function specificKeyboard(param){
            var listEventControl = [
                "btnNewOrderClick:13", //0
                "searchBarcode:13", //1
                "searchBarcode:116", //2
                "searchBarcode:117", //3
                "searchBarcode:118", //4
                "spvName:13", //5
                "password:13", //6
                "searchBarcode:119", //7
                "member:13", //8
                "transaction_type:13", //9
                "MASTER_FRM_FIELD_PAY_TYPE:13", //10
                "FRM_FIELD_PAY_TYPE:13", //11
                "FRM_FIELD_CURR_ID:13",
                "FRM_FIELD_PAY_AMOUNT:13",
                "searchBarcode:114",
                "searchBarcode:122",
                "supervisorName:13",
                "supervisorPassword:13",
                "searchBarcode:115",
                "password:38",
                "searchBarcode:113",
                "searchBarcode:120",
                "FRM_FIELD_ITEM_PRICE:13",
                "FRM_FIELD_DISC_PCT:13",
                "FRM_FIELD_DISC:13",
                "FRM_FIELD_SERVICE_PCT:13",
                "FRM_FIELD_TOTAL_SERVICE:13",
                "FRM_FIELD_TAX_PCT:13",
                "btnNewOrderClick:13",
                "FRM_FIELD_QTY2:13",
                "FRM_FIELD_QTY:123",
                "materialNames:13",
                "spvPayment:13",
                "spvPaymentPass:13",
                "searchBarcode:121","FRM_SALES:13","employee"
            ];
            var listEventDestination = [
                "neworder:click",
                "searchItemFront:click",
                "neworder2:click",
                "openBillSearch:click",
                "searchItemFront:click",
                "password:focus",
                "btnAuthorize:focus",
                "btnPay:click",
                "transaction_type:focus",
                "MASTER_FRM_FIELD_PAY_TYPE:focus",
                "FRM_FIELD_PAY_TYPE:focus",
                "FRM_FIELD_CURR_ID:focus",
                "FRM_FIELD_PAY_AMOUNT:focus",
                "btnPaySave:click",
                "btnReprintBill:click",
                "btnClosingBalance:click",                    
                "supervisorPassword:focus",
                "closingButton:click",
                "btnReturnBill:click",
                "spvName:focus",
                "btnCancel:click",
                "btnMove:click",
                "FRM_FIELD_DISC_PCT:focus",
                "FRM_FIELD_DISC:focus",
                "FRM_FIELD_SERVICE_PCT:focus",
                "FRM_FIELD_TOTAL_SERVICE:focus",
                "FRM_FIELD_TAX_PCT:focus",
                "FRM_FIELD_TOTAL_TAX:focus",
                "btnNewOrderClick:click",
                "FRM_FIELD_ITEM_PRICE:focus",
                "btnDetailEditor:click",
                "FRM_FIELD_QTY2:focus",
                "spvPaymentPass:focus",
                "btnAuthorizePaymentPass:focus",
                "btnJoinBillTemp:click","btnSaveSalesModal:click","FRM_FIELD_SHIPPING_COUNTRY:focus"
            ];
            var index = listEventControl.indexOf(param);
            var event = listEventDestination[index];
            return event;
        }
        
        function keyPressProcess(e,id){
            var index=0;
            var getKeyCode="";
            getKeyCode = String(e.keyCode);
            try { //cek apakah event tersebut di terkunci
                index = lockKeyCode.indexOf(getKeyCode);
            }
            catch(err) {
                index = -1;
            }
            if (index!=-1){
                var event = "";
                var destination = "";
                if (id==""){//cek apakah lokasi terjadinya event memiliki id
                    var result1 = globalKeyboard(e);//jika tidak maka jalankan function global keyboard
                    var temp1 = result1.split(":");
                    destination = temp1[1];
                    event = temp1[0];
                }else{
                    var param = ""+id+":"+String(e.keyCode)+"";//jika ya maka jalankan function specific keyboard
                    var result2 = specificKeyboard(param);
                    var temp2 = result2.split(":");
                    destination = temp2[0];
                    event = temp2[1];
                }
                if (event == "click"){
                    $('#'+destination+'')[0].click();
                }else if (event == "focus"){
                    $('#'+destination+'').focus();
                }
                e.preventDefault();
                return false;
            }
        }
        
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
                keyPressProcess(e,id);
            }); 
        }
        
        $(document).keydown(function(e) {
            if (e.keyCode === 27) { 
                $(".modal").modal("hide");
            } 
        });
        
        function serialFrom(){
            $(".serialFrom").keypress(function(e){
                if (e.keyCode==13){
                    $(".serialFrom").unbind('keypress');
                    var qty = $("#FRM_FIELD_QTY").val();
                    var count = $('input[name*="SERIAL_CODE"]').length;
                    var ids = $(this).attr('id').replace("serial","");
                    var availElement = +ids+1;
                    if ($("#serial"+availElement).length>0){
                        $("#serial"+availElement).focus();
                    } else if (count < qty){
                        $("#groupSerial").append("<div class=\"input-group inputGroupSerial\" id='inputGroupSerial"+count+"'>\n\
                                                                        <input type='text' name='SERIAL_CODE' class='form-control serialFrom' placeholder='Serial Code' \n\
                                                                        id='serial"+count+"' required='required'><div class=\"input-group-addon btn btn-danger btnClearSerial\" \n\
                                                                        title=\"Clear Field\" data-idx='"+count+"'><i class=\"fa fa-eraser\"></i></div>\n\
                                                                        <div class=\"input-group-addon btn btn-danger btnDeleteSerial\" title=\"Delete Field\" data-idx='"+count+"'><i class=\"fa fa-trash\"></i></div></div>");
                        $("#serial"+count).focus();
                    }
                    clearSerial();
                    deleteSerial();
                    clearAllSerial();
                    serialFrom();
                    e.preventDefault();
                    return false;
                }
            });
        }
        
        $(".textClose").keydown(function(e){
            if (e.keyCode==13){
                if ($(this).hasClass('lastClosing')){
                    $(".totalClosingText_1").focus();
                }else{
                    var index = $(this).data("index");
                    var next = index +1;
                    $("#closingText_"+next+"").focus();
                }
                e.preventDefault();
                return false;
            } 
        });
        
        $(".totalCloseText").keydown(function(e){
            if (e.keyCode==13){ 
                if ($(this).hasClass('lastTotalClosing')){
                    $("#supervisorName").focus();
                }else{
                    var index = $(this).data("index");
                    var next = index+1;
                    $(".totalClosingText_"+next+"").focus();
                }e.preventDefault();
                return false;
            }
        });
        
        catchKey();
        
        function textual(){
            $('.textual').keydown(function(e){
                if (e.keyCode==40){
                    $(this).parent().parent().next('tr').find('input').focus();  
                }else if (e.keyCode==38){  
                    if ($(this).hasClass('firstFocus')){
                        if (kasirViewOutlet=="1"){
                            $('#openitemlist #searchItemNew').focus();
                        }else{
                            $('#openitemlist #searchItemModal').focus();
                        } 
                    }else{
                        $(this).parent().parent().prev('tr').find('input').focus();
                    }
                }else if (e.keyCode==13){
                    $(this).trigger('click');
                }else if (e.keyCode==37){
                    idInputFocus = $(this).attr("id");
                    loadDataPagingEx("prev");
                }else if (e.keyCode==39){
                    idInputFocus = $(this).attr("id");
                    loadDataPagingEx("next");
                }
            });
        }
        
        function textual2(){
            $('.textual2').keydown(function(e){
                if (e.keyCode==40){
                    $(this).parent().parent().next('tr').find('input').focus();  
                }else if (e.keyCode==38){  
                    if ($(this).hasClass('firstFocus')){
                        $('#openbill #datasearch').focus();
                    }else{
                        $(this).parent().parent().prev('tr').find('input').focus();
                    } 
                }else if (e.keyCode==13){
                    $(this).trigger('click');
                }
            });
        }
        
        function textual3(){
            $('.textual3').keydown(function(e){
                if (e.keyCode==40){
                    $(this).parent().parent().next('tr').find('input').focus();  
                }else if (e.keyCode==38){  
                    if ($(this).hasClass('firstFocus')){
                        $('#searchBarcode').focus();
                    }else{
                        $(this).parent().parent().prev('tr').find('input').focus();
                    } 
                }else if (e.keyCode==69){
                    var thisId= $(this).attr('id');
                    var temp = thisId.split("_");
                    var id = temp[1];
                    $('#editmainitem_'+id+'').trigger('click');
                }else if (e.keyCode==68){
                    var thisId= $(this).attr('id');
                    var temp = thisId.split("_");
                    var id = temp[1];
                    $('#deletemainitem_'+id+'').trigger('click');
                }else if (e.keyCode==119){
                    $('#btnPay').trigger('click');
                }
            });
        }
        
        function textual4(){
            $('.textual4').keydown(function(e){
                if (e.keyCode==40){
                    $(this).parent().parent().next('tr').find('input').focus();  
                }else if (e.keyCode==38){  
                    if ($(this).hasClass('firstFocus')){
                        $('#openbillreprint #dataSearchReprint').focus();
                    }else{
                        $(this).parent().parent().prev('tr').find('input').focus();
                    }
                }else if (e.keyCode==13){
                    $(this).trigger('click');
                }
            });
        }
        
        function textual5(){
            $('.textual5').keydown(function(e){
                if (e.keyCode==40){
                    $(this).parent().parent().next('tr').find('input').focus();  
                }else if (e.keyCode==38){  
                    if ($(this).hasClass('firstFocus')){
                        $('#openbill #datasearch').focus();
                    }else{
                        $(this).parent().parent().prev('tr').find('input').focus();
                    }
                }else if (e.keyCode==13){
                    $(this).trigger('click');
                    setTimeout(function(){
                        $('#openauthorize #spvName').focus();
                    }, 200);
                    return false;   
                }
            });
        }
        
        function textual6(){
            $('.textual6').keydown(function(e){
                var id = $(this).attr('id');
                var temp = id.split("-");
                var ids  = temp[1];
                if (e.keyCode==40){
                    if ($(this).hasClass('lastFocus')){
                        $('#openbill .firstFocus').focus();
                    }else{
                        var idNext = parseInt(ids)+1;
                        $('#openbill #qtyTemp-'+idNext+'').focus();
                    }
                }else if (e.keyCode==38){  
                    if ($(this).hasClass('firstFocus')){
                        $('#openbill .lastFocus').focus();
                    }else{
                        var idPrev = parseInt(ids)-1;
                        $('#openbill #qtyTemp-'+idPrev+'').focus();
                    }

                }else if (e.keyCode==13){
                    if ($(this).hasClass('lastFocus')){
                        $('#openbill #btnOpenReturn').trigger('click');
                    }else{
                        var idNext = parseInt(ids)+1;
                        $('#openbill #qtyTemp-'+idNext+'').focus();
                        e.preventDefault();
                    }
                }
            });
        }
        
        function textual7(){
            $('.textual7').keydown(function(e){
                var id = $(this).attr('id');
                var temp = id.split("-");
                var ids  = temp[1];
                if (e.keyCode==40){
                    if ($(this).hasClass('lastFocus')){
                        $('#openauthorize .firstFocus').focus();
                    }else{
                        var idNext = parseInt(ids)+1;
                        $('#openauthorize #moveBill-'+idNext+'').focus();
                    }
                }else if (e.keyCode==38){  
                    if ($(this).hasClass('firstFocus')){
                        $('#openauthorize .lastFocus').focus();
                    }else{
                        var idPrev = parseInt(ids)-1;
                        $('#openauthorize #moveBill-'+idPrev+'').focus();
                    }                       
                }else if (e.keyCode==13){
                    if ($(this).hasClass('lastFocus')){
                        $('#openauthorize #btnMove').focus();
                    }else{
                        var idNext = parseInt(ids)+1;
                        $('#openauthorize #moveBill-'+idNext+'').focus();
                        e.preventDefault();
                    }
                }                    
            });
        }
        
        function textual9(){
            $('.textual9').keydown(function(e){
                if (e.keyCode==40){
                    $(this).parent().parent().next('tr').find('input').focus();  
                }else if (e.keyCode==38){  
                    if ($(this).hasClass('firstFocus')){
                        $('#openbilljoin #dataSearchJoin').focus();
                    }else{
                        $(this).parent().parent().prev('tr').find('input').focus();
                    }
                }else if (e.keyCode==13){
                    $(this).trigger('click');
                    return false;    
                }
            });
        }
        
        function textual10(){
            $('.textual10').keydown(function(e){
                var id = $(this).attr('id');
                var temp = id.split("-");
                var ids  = temp[1];                   
                if (e.keyCode==40){
                    if ($(this).hasClass('lastFocus')){
                        $('#openbilljoin .firstFocus').focus();
                    }else{
                        var idNext = parseInt(ids)+1;
                        $('#openbilljoin #qtyJoinTemp-'+idNext+'').focus();
                    }
                }else if (e.keyCode==38){  
                    if ($(this).hasClass('firstFocus')){
                        $('#openbilljoin .lastFocus').focus();
                    }else{
                        var idPrev = parseInt(ids)-1;
                        $('#openbilljoin #qtyJoinTemp-'+idPrev+'').focus();
                    }
                }else if (e.keyCode==13){
                    if ($(this).hasClass('lastFocus')){
                        $('#openbilljoin #btnJoinSave').trigger('click');
                    }else{
                        var idNext = parseInt(ids)+1;
                        $('#openbilljoin #qtyJoinTemp-'+idNext+'').focus();
                        e.preventDefault();
                    }
                }
            });
        }
        
        $(".textClose").keyup(function(){
            <% for (int i= 0;i<listBalance.size();i++){ Balance entBalance = (Balance)listBalance.get(i); %>
                var amount_<%= entBalance.getCurrencyOid() %> = 0; 
                var amountStr_<%= entBalance.getCurrencyOid()%> ="";
                $(".text_<%= entBalance.getCurrencyOid() %>").each(function(i){
                    var value = $(this).val();
                    amountStr_<%= entBalance.getCurrencyOid()%> = value;
                    try {
                        amountStr_<%= entBalance.getCurrencyOid()%> = amountStr_<%= entBalance.getCurrencyOid()%>.split(",").join("");    
                    } catch(err) {
                        amountStr_<%= entBalance.getCurrencyOid()%> = "0";
                    }
                    if (amountStr_<%= entBalance.getCurrencyOid()%>.length<=0){
                        amountStr_<%= entBalance.getCurrencyOid()%> = "0";
                    }
                    var totalInNum= parseFloat(amountStr_<%= entBalance.getCurrencyOid()%>);
                    amount_<%= entBalance.getCurrencyOid() %> = amount_<%= entBalance.getCurrencyOid() %> + totalInNum;
                });
                var jml_<%= entBalance.getCurrencyOid() %> = parserNumber(String(amount_<%= entBalance.getCurrencyOid() %> ),false,0,'');
                $(".textCloseFor_<%= entBalance.getCurrencyOid()%>").val(jml_<%= entBalance.getCurrencyOid() %>);
            <%}%>    
        });
        
        searchItemModal();
        
        function searchItemModal(){
            $('#searchItemModal').keydown(function(e){
                if (e.keyCode==40){ 
                    $('#openitemlist .firstFocus').focus();
                }else if (e.keyCode==37){                     
                    loadDataPagingEx("prev");
                }else if (e.keyCode==39){                       
                    loadDataPagingEx("next");    
                }
            });
            $('#searchItemNew').keydown(function(e){
                if (e.keyCode==40){
                    $('#openitemlist .firstFocus').focus();
                }else if (e.keyCode==37){
                    loadDataPagingEx("prev");
                }else if (e.keyCode==39){
                    loadDataPagingEx("next");
                }
            });
        }
        
        function datasearch(){
            $('#openbill #datasearch').keydown(function(e){
                if (e.keyCode==40){
                    $('#openbill .firstFocus').focus();
                }else if (e.keyCode==38){
                    $('#openbill #obSearch1').focus();
                }
            });
            $('#openbill #obSearch1').keydown(function(e){
               if (e.keyCode==13){
                    $('#openbill #obSearch2').focus();
                }
            });
            $('#openbill #obSearch2').keydown(function(e){
               if (e.keyCode==13){
                    $('#openbill #datasearch').focus();
                }
            });
        }
        
        function dataSearchReprint(){
            $('#dataSearchReprint').keydown(function(e){        
                if (e.keyCode==40){ 
                    $('#openbillreprint .firstFocus').focus();
                }
            });    
        }
        
        function dataSearchJoin(){
            $('#dataSearchJoin').keydown(function(e){       
                if (e.keyCode==40){ 
                    $('#openbilljoin .firstFocus').focus();
                }else if (e.keyCode==13){
                    $('#openbilljoin #btnSearchJoinBill').trigger('click');
                }
            });
            $('#dataSearchJoin').keyup(function(){
                loadListJoin();
            });
            $('#openbilljoin #ojSearch1').keydown(function(e){
               if (e.keyCode==13){
                    $('#openbilljoin #ojSearch2').focus();
                }
            });
            $('#openbilljoin #ojSearch2').keydown(function(e){
               if (e.keyCode==13){
                    $('#openbilljoin #dataSearchJoin').focus();
                }
            });   
        }
        
        $('#searchBarcode').keydown(function(e){
            if (e.keyCode==40){ 
                $('#mainTransaction .firstFocus').focus();
            }
        });
        
        //ALL ABOUT KEYBOARD
        $(".modal").on('shown.bs.modal', function() {
            var id = $(this).attr('id');
            if (id =="openitemlist"){
               if (kasirViewOutlet=="1"){
                   $("#searchItemNew").focus();
               }else{
                   $("#openitemlist #searchItemModal").focus();
               }
            } else if (id=="openadditem"){
                $('#openadditem #FRM_FIELD_QTY').focus();
            }else if(id=="openpay"){
                setTimeout(function(){
                    $('#openpay #member').focus();
                }, 200);
            }else if (id=="openclosingbalance"){
                setTimeout(function(){
                    $('#openclosingbalance #closingText_1').focus();
                }, 200);
            }else if (id=="newOrderConfirmation"){
                setTimeout(function(){
                    $('#newOrderConfirmation #btnNewOrderClick').focus();
                }, 200);
            }else if (id=="openbill"){
                setTimeout(function(){
                    $('#openbill #datasearch').focus();
                }, 200); 
            }else if (id=="openbillreprint"){
                setTimeout(function(){
                    $('#openbillreprint #dataSearchReprint').focus();
                }, 200); 
            }else if (id=="openbilljoin"){
                setTimeout(function(){
                    $('#openbilljoin #dataSearchJoin').focus();
                }, 200);
            }else if (id=="salesModal"){
                setTimeout(function(){
                    $('#FRM_SALES').focus();
                }, 200);
            }else{
                $(this).find('button:visible:first').focus();
                $(this).find('input:text:visible:first').focus();
            }    
        });
        
        $('.modal').on('hidden.bs.modal', function () {
            var id = $(this).attr('id');
            if (id=="openadditem"){
                if (kasirViewOutlet=="1"){ 
                   $("#openitemlist #searchItemNew").focus(); 
                }else{
                    $('#openitemlist #searchItemModal').focus();
                }
            }else if (id =="openbill" ||id=="openauthorize" || id=="openpay" || id=="openclosingbalance"){
                $('#searchBarcode').focus(); 
                if (creditPass==1){
                    $("#transaction_type").val("");
                }
            }else if (id=="openitemlist"){  
                $("#parentPackage").val("");
                $('#searchBarcode').focus();
                if (creditPass==1){
                    $("#transaction_type").val("");
                }
            }
        });
        
        //BUTTON CLICK
        $("#btnOpen").click(function(){
            var checkedData = $("input:radio[name=<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] %>]:checked").val();
            if(checkedData > 0){		    
                $("#CONTENT_ANIMATE_CHECK").fadeIn("medium");		
                $("#openbill").modal("hide");		    
                $("#btnPay").data("oid",checkedData);
                $('#mainBillIdSource').val(checkedData);
                getLocationByCashBillMain(checkedData);
                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_MAIN_ID] %>_ADD_ITEM").val(checkedData);
                $("#btnCancel").data("oid",checkedData);
                $("#btnMove").data("oid",checkedData);
                loadTransaction(checkedData,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                $(".itemsearch").removeAttr("readonly");
                $(".itemsearchbutton").removeAttr("disabled");
            }else{
                alert("Please select transaction");
            }
        });
        
        //SELECT BILL ON CLICK ON THE GRID
        function selectBillToOpen(){
            $(".searchBillSelect").click(function(){		
                var checkedData = $(this).data("mainoid"); 
                var status = $(this).data("transtype");
                if (status==1){
                    $("#frmPengajuanKredit #billId").val(checkedData);
                    $( "#frmPengajuanKredit" ).submit();
                } else {
                    if(checkedData > 0){
                        $("#CONTENT_ANIMATE_CHECK").fadeIn("medium");
                        $("#openbill").modal("hide");
                        $("#btnPay").data("oid",checkedData);
                        $('#mainBillIdSource').val(checkedData);
                        getLocationByCashBillMain(checkedData);
                        $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_MAIN_ID] %>_ADD_ITEM").val(checkedData);
                        $("#btnCancel").data("oid",checkedData);
                        $("#btnMove").data("oid",checkedData);
                        var reservationId = $(this).data("reservationid");
                        var status = $(this).data("status");
                        $("#reservationIdSource").val(reservationId);
                        $("#productionStatus").val(status);
                        loadTransaction(checkedData,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                        var package = $(this).data("package");
                        if (package=="1"){
                            $(".itemsearch").removeAttr("readonly"); 
                            $(".itemsearchbutton").removeAttr("disabled");                          
                            $("#searchBarcode").attr("readonly","readonly"); 
                            $("#searchItemFront").attr("disabled","disbaled");
                        }else{
                            $(".itemsearch").removeAttr("readonly");
                            $(".itemsearchbutton").removeAttr("disabled");  
                        }
                    }else{
                        alert("Please select transaction");
                    }
                }
            });
        }
        
        function searchBillToReturn(){
            $('.searchBillReturnSelect').click(function(){
                var checkedData = $(this).data("mainoid");
                $('#selectedIdBill').val(checkedData);
                $("form#returnBill").trigger('submit');
            });
        }
        
        $("#btnSelect").click(function(){
            var checkedData = $("input[name=employee]:checked").val();
            var personName = $("input[name=employee]:checked").data("name");
            var personDiscPct = $("input[name=employee]:checked").data("discPct");
            var personDiscVal = $("input[name=employee]:checked").data("discVal");
            var phoneNumber = $("input[name=employee]:checked").data("phoneNumber");
            if(checkedData > 0){
                $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] %>").val(checkedData);
                $("#employee").val(personName);
                $("#CUSTOMER_NAME").val(personName);
                $("#<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE] %>").val(phoneNumber);
                $("#opensearchmember").modal("hide");
                $("#transaction_type").val("");
                $("#result").fadeOut("fast");
                $("#resultSave").fadeOut("fast");
                $("#CONTENT_PAYMENT_TYPE").fadeOut("fast");			
                $("#CONTENT_SUB_PAYMENT_SYSTEM_CASH").fadeOut("fast");
                $("#CONTENT_PAYMENT_SYSTEM").fadeOut("fast");
                $("#transaction_type").val("");
                $("#discountpercentage").val(personDiscPct);
                if(personDiscPct > 0){
                    var getBalanceVal = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val();
                    personDiscVal = getBalanceVal*(personDiscPct/100);
                    personDiscVal = formatNumber(personDiscVal);
                }else{
                    personDiscVal = personDiscVal;		    
                }
                $("#discountnominal").val(personDiscVal);
                balanceCalc();
            }else{
                alert("Please select transaction");
            }
        });
        
        //SELECT GUEST WHEN CLICK GUEST LIST IN THE POP UP GRID
        function clickSelectGuest(){
            $(".selectGuest").click(function(){
                var checkedData = $(this).data("oid");
                var personName = $(this).data("name");
                var personDiscPct = $(this).data("discPct");
                var personDiscVal = $(this).data("discVal");
                var phoneNumber = $(this).data("phoneNumber");
                var reservationId = $(this).data("oidreservasi");
                var code = $(this).data("code");
                var gender = $(this).data("gender");
                var group = $(this).data("group");
                var address = $(this).data("address");
                var loadtype = $(this).data("loadtype");
                if(checkedData > 0){
                    if (loadtype === "loadsearchnewmember" || loadtype === "loadsearchmember") {
                        $('#newMemberOid').val(checkedData);
                        $('#newMemberCode').val(code);
                        $('#newMemberName').val(personName);
                        $('#newMemberPhone').val(phoneNumber);
                        $('#newMemberGroup').val(group);
                    }
                    if (loadtype === "loadsearchmembernewbill" || loadtype === "loadsearchmember") {
                        $('#memberCode').val(code);
                        $('#memberName').val(personName);
                        $('#memberPhone').val(phoneNumber);
                        if (gender === 0 || gender === "0") {
                            $('#genderMale').attr({"checked":true});
                            $('#genderFemale').attr({"checked":false});
                        } else {
                            $('#genderFemale').attr({"checked":true});
                            $('#genderMale').attr({"checked":false});
                        }
                    }
                    $("#openneworder #oidReservationOrder").val(reservationId);
                    $("#helpMessageCustomerName").val(personName);
                    $("#customerAddress").val(address);
                    $("#helpMessageCustomerTelp").val(phoneNumber);
                    $("#employee").val(personName);
                    $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] %>").val(checkedData);
                    $("#CUSTOMER_NAME").val(personName);
                    $("#<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE] %>").val(phoneNumber);
                    $("#opensearchmember").modal("hide");
                    $("#transaction_type").val("");
                    $("#result").fadeOut("fast");
                    $("#resultSave").fadeOut("fast");
                    $("#CONTENT_PAYMENT_TYPE").fadeOut("fast");			
                    $("#CONTENT_SUB_PAYMENT_SYSTEM_CASH").fadeOut("fast");
                    $("#CONTENT_PAYMENT_SYSTEM").fadeOut("fast");
                    $("#transaction_type").val("");
                    $("#<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_RESERVATION_ID]%>").val(reservationId);
                    $("#discountpercentage").val(personDiscPct);
                    if(personDiscPct > 0){
                        var getBalanceVal = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val();
                        personDiscVal = getBalanceVal*(personDiscPct/100);
                        personDiscVal = formatNumber(personDiscVal);
                    }else{
                        personDiscVal = personDiscVal;		    
                    }
                    $("#discountnominal").val(personDiscVal);
                    balanceCalc();
                }else{
                    alert("Please select transaction");
                }
            });
        }
        
        //BUTTON SEARCH
        $("#btnSearch").click(function(){
            var datasearch = $("#datasearch").val();
            var date1 = $("#obSearch1").val();
            var date2 = $("#obSearch2").val();
            var tableName = $("#tableName").val();
            var loadtype = $(this).data("loadType");
            var location = $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]%>_OPEN").val();
            var showPage = $("#nextPageBIll").val();               
            loadTransaction2(datasearch,loadtype,<%= Command.NONE %>,"<%= cashCashier.getOID() %>","CONTENT_SEARCH",date1,date2,location,tableName,showPage);
        });
        
        function pageUniv (elementAction,elementTotal,elementPage,elementNextPage,elementTriger){
            $(elementAction).click(function(){
                var showPage=0;
                var type = $(this).data('type');
                var curentPage = $(elementPage).val();
                var total = $().val(elementTotal);                   
                if (type==="first"){
                    showPage = 1;
                }else if (type==="prev"){
                    showPage = Number(curentPage)-1;
                }else if (type==="next"){
                    showPage = Number(curentPage)+1;
                }else if (type=="last"){
                    showPage = total;
                }
                $(elementNextPage).val(showPage);
                $(elementTriger).trigger("click");
            });
        }
        
        $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]%>_OPEN").change(function(){
           $("#btnSearch").trigger('click'); 
        });
        
        //BUTTON SEARCH MEMBER
        $("#btnSearchMember").click(function(){
            var datasearch = $("#searchitem").val();
            var classType = $("#<%= FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE] %>").val();
            if ("<%=enableDutyFree%>" === "1") {
                classType = "<%=PstContactClass.CONTACT_TYPE_DONOR%>";
            }
            var searchtype = $("#searchtype").val();
            var oidcashbillmainguest = $("#btnPay").data("oid");
            searchMember(datasearch,"loadsearchmember",<%= Command.NONE %>,classType,"CONTENT_SEARCH_MEMBER", searchtype,oidcashbillmainguest);
        });
        
        //BUTTON PAY
        $("#btnPay").click(function(){
            var oidPay = $("#mainBillIdSource").val();
            $("#separateprint").val("0");
            loadTransaction(oidPay,"loadpay",<%= Command.NONE %>,0,"CONTENT_PAY");	  
            $("#openpay").modal("show");
            $('#btnPaySave').removeAttr('disabled').html('Pay');
            $("#CONTENT_PAY").html("Please wait...").fadeIn("medium");
        });

            
            $('#btnToProduction').click(function(){
                var oidPay = $("#mainBillIdSource").val();
                var admin = $("#adminFee").val();
                var transport = $("#shippingFee").val();
//                var oid = $(this).data('oid');
                command = "<%= Command.SAVE%>";
                var dataFor = "production";
                dataSend = {
                    "FRM_FIELD_DATA_FOR": dataFor,
                    "command": command,
                    "BILL_MAIN_ID": oidPay,
                    "BILL_ADMIN_PRICE": admin,
                    "BILL_TRANSPORT_PRICE": transport
                };
                onDone = function(data){
                  alert(data.FRM_FIELD_RETURN_MESSAGE);
                  window.location.href = "<%= approot %>/cashier/cashier-transaction.jsp";
                };
                onSuccess = function(data){
//                  alert("Data to Production Success");
                }; 
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "TransactionCashierHandlerNew", "", true, "json");
            });
        
        
        $("#btnBiayaTambahan").click(function(){
            $("#modal-biayatambahan").modal("show");  
        });
        
        $("#btnPrintSeparate").click(function(){
            $("#separateprint").val("1");
            $("#btnfrom").val("0");
            $("#btnPayFinger").trigger("click");
        });
        
        //BUTTON OPEN BILL
        $(".btnOpenBill").click(function(){
            var loadType = $(this).data("loadType");
            $("#btnOpenReturn").html("<i class='fa fa-check'></i> Open");
            $("#btnSearch").data("loadType",loadType);		
            $("#commandreturn").val(<%= Command.NONE %>);
            $("#openbill .modal-dialog").addClass("modal-lg");	
            $("#openbill .input-group").show();
            $("#openbill #obSearch1").val("");
            $("#openbill #obSearch2").val("");  
            if(loadType == "loadsearch"){
                $("#openbill .modal-title").html("<b>OPEN BILL</b>");
                $("#btnOpen").show();
                $("#btnOpenReturn").hide();    
                $("#openbill #FRM_FIELD_LOCATION_ID_OPEN").show();
                $("#openbill #tableName").show();              
            }else{
                $("#openbill .modal-title").html("<b>RETURN BILL</b>");
                $("#btnOpen").hide();
                $("#btnOpenReturn").show();
                $("#openbill #FRM_FIELD_LOCATION_ID_OPEN").show();
                $("#openbill #tableName").show();
                $("#loadtypereturn").val("loadbillreturn");
            }
            $("#openbill").modal("show");
            $("#btnSearch").trigger('click'); 
        });
        
        //BUTTON PRINT
        $("#print").click(function(){
            window.print();
        });
        //BUTTON PRINT PAYMENT CREDIT
        $("#printPayment").click(function(){
            window.print();
        });
        
        function moneyFormat(){
            $('.money').keyup(function(){
                var value = $(this).val();
                var conValue = parserNumber(value,false,0,'');
                $(this).val(conValue);
            });
        }
        
        //FORM PAYMENT SUBMIT
        $("#btnPaySave").click(function(){
            $("#loadtype").val("");
            $("#command").val("<%= Command.SAVE %>");
            $("#btnPaySave").attr({"value":"Please wait...","disabled":"true"});
            $("#btnPayFinger").attr({"value":"Please wait...","disabled":"true"});
            maxDisc = $("#maxDisc").val();
            var datapaircheck = "";
            var signaturpath = "";
            var cansave = false;
            var ccNumber  = "";
            var ccName  = "";
            var ccDate  = "";
            var returnCash = 0;
            var masterPayType = 0;
            var typeCustomer = -1;
            var idFocus ="";
            datapaircheck = $("#signature").jSignature("getData", "native") ;
            signaturpath = $("#SIGNATURE_PATH").val();
            cansave = $("#cansave").val();
            ccNumber  = $("#<%= FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] %>").val();
            ccName  = $("#<%= FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] %>").val();
            ccDate  = $(".datePicker").val();
            returnCash = $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN] %>").val();
            masterPayType = $("#MASTER_<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE] %>").val();
            typeCustomer = $('#member option:selected').text();  
            var msg ="";
            if (cansave==false || cansave == "false"){
                if ($('#transaction_type').val()==""){
                    msg ="Please select Transaction Type";
                    idFocus = "#transaction_type";
                }else if ($('#MASTER_FRM_FIELD_PAY_TYPE').val()==""){
                    msg ="Please select Payment Type";
                    idFocus = "#MASTER_FRM_FIELD_PAY_TYPE";
                }else if ($('#FRM_FIELD_PAY_TYPE').val()==""){
                    var payType = $('#MASTER_FRM_FIELD_PAY_TYPE').val();
                    if (payType==<%=PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD%>){
                        msg ="Please select Type Card";
                    }else if (payType==<%=PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD%>){
                        msg ="Please select Bank Name";
                    }else{
                        msg ="Please select Payment Detail";
                    }
                    idFocus ="#FRM_FIELD_PAY_TYPE";
                }else{
                    var transactionTypess = $("#transaction_type").val();
                    if (transactionTypess!=1){
                        var voucherOrNo = $("#MASTER_FRM_FIELD_PAY_TYPE").val();
                        if (voucherOrNo==5 || voucherOrNo == 6){
                            //voucher
                            var voucherId = $("#VOUCHER_ID").val();//Cek apakah Voucher Code sudah ada
                            if (voucherId==""){
                                msg ="Please select Voucher";
                                idFocus = "#voucherCodeInput";
                            }else{
                                var amountVoucher = $("#FRM_FIELD_PAY_AMOUNT").val();//JIKA VOUCHER ID TIDAK KOSONG, CEK JUMLAHNYA, APAKAH SUDAH SAMA DENGAN ATAU LEBIH DARI TOTAL
                                var totalAmount = $("#totalCharge").html();                                   
                                totalAmount= totalAmount.split(",").join("");
                                var totals = Number(totalAmount);
                                if (amountVoucher>= totals){
                                    $("#cansave").val(true);
                                }else{
                                    msg ="Voucher nominal less than Total Amount. Use Multi Payment";
                                    idFocus = "#voucherCodeInput";
                                }
                            }
                        }else{
                            msg ="Please insert Payment Amount";
                            idFocus = "#FRM_FIELD_PAY_AMOUNT";
                        }                           
                    }else{
                        $("#cansave").val(true);
                    }   
                }
            }else{
                msg ="";
            }
            cansave = $("#cansave").val();
            try {
                if(masterPayType == <%= PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD %>){
                    if(ccNumber.length == 0){
                        cansave = false;
                        msg ="Please insert Debit Card Number";
                        idFocus = "#<%= FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] %>";
                    }else if(ccDate.length == 0){
                        cansave = false;
                        msg ="Please insert Debit Card Date";
                        idFocus = ".datePicker";
                    }
                }else if(masterPayType == <%= PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD %>){
                    if(ccName.length == 0){
                        cansave = false;
                        msg ="Please insert Credit Card Name";
                        idFocus = "#<%= FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] %>";
                    }else if(ccNumber.length == 0){
                        cansave = false;
                        msg ="Please insert Credit Card Number";
                        idFocus = "#<%= FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] %>";
                    }else if(ccDate.length == 0){
                        cansave = false;
                        msg ="Please insert Credit Card Date";
                        idFocus = ".datePicker";
                    }
                }else if(<%=useSignature%> && (typeCustomer=="MEMBER" || typeCustomer=="EMPLOYEE" || typeCustomer=="IN HOUSE GUEST")){
                    if(datapaircheck.length == 0){
                        cansave = false;
                        msg ="Please insert Signature";
                    }
                }
            }catch(ex){
                cansave = false;
            }
            var getDiscount = $("#discountnominal").val();
            getDiscount = getDiscount.replace(/,/g, "");
            if(maxDisc < getDiscount && <%= usingDiscAssign %> == 1){
                cansave = false;
                msg = "Maximum Discount is "+maxDisc;
            }
            if(cansave == false || cansave == "false"){
                $("#btnPaySave").removeAttr("disabled").attr({"value":"Pay"});
                $("#btnPayFinger").removeAttr("disabled").attr({"value":"Pay"});
                $("#resultSave").html("<i class='fa fa-exclamation'></i> Cant't Save, "+msg+"").addClass("alert alert-danger").fadeIn("medium");
                setTimeout(function(){
                    $('#openpay '+idFocus+'').focus();
                }, 200); 
            }else{
                var transactionPackage = $("#transactionPackage").val();
                var dataSend = $("form#<%= FrmCashPayment.FRM_NAME_CASH_PAYMENT %>").serialize();
                dataSend = dataSend + "&transactionPackage="+transactionPackage+"";
                $.ajax({
                    type    : "POST",
                    url	    : "<%= approot %>/TransactionCashierHandler",
                    data    : dataSend,
                    cache   : false,
                    dataType: "json",
                    success : function(data){
                        $("#btnPaySave").removeAttr("disabled").attr({"value":"Pay"});
                        $("#btnPayFinger").removeAttr("disabled").attr({"value":"Pay"});
                        $("#CONTENT_PAY").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>).fadeIn("medium");
                        $("#CONTENT_PRINT").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                        var noTelGuest = $("#helpMessageCustomerTelp").val();//SMS JIKA NO TELP GUEST TIDAK KOSONG
                        var cashierLinkSms= $('#cashierLink').val();
                        if (cashierLinkSms.length>0 && noTelGuest.length>10){
                            sendShortMessage(cashierLinkSms,noTelGuest);                           
                        }
                        $("#printnotaGuide").hide();
                        $("#printnota").hide();
                        $("#btnPayFinger").hide();
                        window.print();
                        //$("#openpay").modal("hide");
                    },
                    error : function(){
                    }
                }).done(function(data){
                    $('#mainBillIdSource').val(0);
                    //alert("Print Success");
                    $('#openpay').on('hidden.bs.modal', function () {
                        window.location.reload(true);
                    });
                });
            }
            return false;
        });
        
        function sendShortMessage(link,no, getMessage){
            var message = getMessage;
            if(getMessage === undefined || getMessage === "undefined"){
                message = "Thanks for your visit..., Queen Tandoor";
            }
            var url = link;
            var data = "nomor="+no+"&msg="+message+"&send=1";
            ajaxTransactionHandler(url,data,"GET","","sendSms","","");
        }
        
        //FORM AUTHORIZE
        $("form#<%= FrmAppUser.FRM_APP_USER %>").submit(function(){
            $("#btnAuthorize").attr({"disabled":"true","value":"Please wait..."});
            var loadtype = $("#loadtypeauthorize").val();
            var result = "";
            $.ajax({
                type	: "POST",
                url		: "<%= approot %>/TransactionCashierHandler",
                data	: $(this).serialize(),
                cache	: false,
                dataType	: "json",
                success	: function(data){

                    $("#btnAuthorize").removeAttr("disabled").attr({"value":"Authorize"});
                    result = data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>;

                    if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %> == "SUCCESS"){
                        if(loadtype == "opendiscount"){
                            autoFocusCash = false;
                            $("#discountnominal").removeAttr("readonly");
                            $("#discountpercentage").removeAttr("readonly");
                        }else if(loadtype == "authorizecredit"){ creditPass=0;
                            var customerId = $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] %>").val();
                            searchMember(customerId,"checkmemberlimitnonmember",<%= Command.NONE %>,0,"CONTENT_SUB_PAYMENT_SYSTEM_CASH","");
                        }else if(loadtype == "authorizereturn"){
                            $.ajax({
                                type	: "POST",
                                url		: "<%= approot %>/TransactionCashierHandler",
                                data	: $("#returnBill").serialize(),
                                cache	: false,
                                dataType	: "json",
                                success	: function(data){

                                    $("#btnOpenReturn").removeAttr("disabled").html("<i class='fa fa-check'></i> Return");
                                    $("#commandreturn").val(<%= Command.SAVE %>);
                                    $("#openbill .input-group").hide();
                                    $("#openbill #FRM_FIELD_LOCATION_ID_OPEN").hide();
                                    $("#openbill #tableName").hide();

                                    $("#openbill .modal-dialog").addClass("modal-lg");
                                    $("#CONTENT_SEARCH").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                                    var checkelement = $('#specialButton').parent().find("#btnOpenReturn").length;
                                    if (checkelement==0){
                                       $('#specialButton').parent().append('<button type="submit" id="btnOpenReturn" class="btn btn-primary"><i class="fa fa-check"></i> Return</button>');
                                    }
                                }
                            }).done(function(){
                            <% if (useExchangeAsDefault.equals("1")) {%>
                                    $("#btnReturnExchange").trigger("click");	
                            <% } %>

                                $("#loadtypereturn").val("returnbill");
                                $('#returnAll').change(function() {
                                    if(this.checked) {
                                        $('.qtyReturnHelp').each(function(i){
                                            var id = this.id;
                                            var nilai = $('#'+id+'').val();
                                            var ids = id.split("-");
                                            var no = ids[1];
                                            $('#qtyTemp-'+no+'').val(nilai);
                                        });
                                    }else{
                                        $('.qtyTemp').val('0');
                                    }    
                                });
                                $('#openbill .firstFocus').focus();
                                textual6();
                            });
                        }
                        $("#openauthorize").modal("hide");
                    }else{
                        $("#<%= FrmAppUser.FRM_APP_USER %>").hide();
                        $("#CONTENT_AUTHORIZE").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                        setTimeout(function(){
                            $('#CONTENT_AUTHORIZE #materialNames').focus();
                        }, 200); 
                        if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %> == "<%= FRMMessage.getMessage(FRMMessage.MSG_DELETED) %>"){
                            $("#openauthorize").modal("hide");
                            var oid = $("#btnPay").data("oid");
                            loadTransaction(oid,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                        }
                        if(loadtype == "movebill"){
                            $("#openauthorize #btnMove").show();
                            $("#cancelButton").show();
                            $("#openauthorize .modal-dialog").addClass("modal-lg");
                            $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").keyup(function(){
                               var maxNumb = $(this).data("max");
                               var minNumb = $(this).data("min");
                               if($(this).val() > maxNumb){
                                   $(this).val(maxNumb);
                               }else if($(this).val() < minNumb || isNaN($(this).val())){
                                   $(this).val(minNumb);
                               }
                            });
                        }else if(loadtype == "cancelbill"){
                            
                        }else if (loadtype=="joinbill"){
                            $('#openauthorize').modal('hide');
                            $('#btnJoinBill').trigger('click');
                        }
                    }
                }
            }).done(function(){
                if(result == "SUCCESS" && loadtype == "authorizecredit"){
                    $("#cansave").val("true");
                }
                moneyFormat();
                innerFunction();
                if(loadtype == "movebill"){
                    setTimeout(function(){
                        $('#openauthorize .firstFocus').focus();
                    }, 200);
                    textual7();
                }else if (loadtype=="edititem"){
                    setTimeout(function(){
                        $('#openauthorize #FRM_NAME_BILL_DETAIL #FRM_FIELD_QTY').focus();
                    }, 800);
                    catchKey();
                }else if (loadtype=="delitem"){
                    setTimeout(function(){
                        $('#openauthorize #FRM_NAME_BILL_DETAIL #noteDeleteItem').focus();
                    }, 400);
                }
            });
            return false;
        });
        
        function itemChange(){
            $(".itemChange").keyup(function(){
                //GET VALUE
                var checkVal = $(this).val();
                var ids = $(this).attr('id');
                if(checkVal.length == 0){
                    $(this).val(0);
                }
                var taxInc = $("#taxInc").val();
                var qty = parseFloat($("#FRM_FIELD_QTY2").val());
                var qtyStock = parseFloat($("#stock").val());
                var stockMode = parseFloat($("#stockCheck").val());
                var itemPrice = parseFloat($("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val());
                var discPct = parseFloat($("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] %>").val());
                var discNominal = parseFloat($("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>").val());
                var jumlahDiskon = 0;
                if ($("#jumlahDiskon").length > 0){
                    jumlahDiskon = parseFloat($("#jumlahDiskon").val());	
                }
                var servicePct = parseFloat($("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT] %>").val());
                var taxPct = parseFloat($("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT] %>").val());
                var totalPrice = qty * itemPrice;
                var total = 0;

                if (ids=="jumlahDiskon"){
                    $('#discountTypeText').html("<%=PstDiscountQtyMapping.typeDiscount[PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_SKU]%>");
                    var type = "<%=PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_SKU%>";
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_TYPE] %>").val(type);
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>").val(0);
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] %>").val(0);
                    discNominal = jumlahDiskon / qty;
                    discPct = discNominal/itemPrice*100;

                }else if (ids=="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT]%>"){
                    $('#discountTypeText').html("<%=PstDiscountQtyMapping.typeDiscount[PstDiscountQtyMapping.DISC_PERSEN_PER_ITEM_UNIT]%>");
                    var type = "<%=PstDiscountQtyMapping.DISC_PERSEN_PER_ITEM_UNIT%>";
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_TYPE] %>").val(type);
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>").val(0);
                    $("#jumlahDiskon").val(0);
                    discNominal = itemPrice*(discPct/100);
                    jumlahDiskon = discNominal * qty;

                }else if (ids=="<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>"){
                    $('#discountTypeText').html("<%=PstDiscountQtyMapping.typeDiscount[PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_UNIT]%>");
                    var type = "<%=PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_UNIT%>";
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_TYPE] %>").val(type);
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] %>").val(0);
                    $("#jumlahDiskon").val(0);
                    discPct = discNominal/itemPrice*100;
                    jumlahDiskon = itemPrice*(discPct/100) * qty;

                }else if (ids=="FRM_FIELD_QTY2"){
                    $("#FRM_FIELD_QTY2").val(qty);
                    if (stockMode != <%=PstCashMaster.STOCK_MODE_NO_STOCK_CHECKING%>){
                        var inputQty = parseFloat($(this).val());
                        if (inputQty > qtyStock){
                            alert("Qty Exceed Stock! Current Stock is "+qtyStock);
                            $("#FRM_FIELD_QTY2").val(qtyStock);
                            return false;
                        }
                    }
                    if(discPct > 0){
                        $("#jumlahDiskon").val(0);
                        jumlahDiskon = itemPrice*(discPct/100) * qty;
                    }
                }else{
                    if(discPct > 0){
                        $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>").val(0);
                        discNominal = itemPrice*(discPct/100);
                    }else{
                        $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] %>").val(0);
                        discPct = discNominal/itemPrice*100;
                    }
                }

                var afterDiscount = totalPrice-jumlahDiskon;
                var serviceValue = 0;
                var afterService = 0;
                var taxValue = 0;

                if(taxInc == <%= PstBillMain.INC_CHANGEABLE %> || taxInc == <%= PstBillMain.INC_NOT_CHANGEABLE %>){
                    var amount = afterDiscount / ((taxPct + servicePct + 100) / 100);
                    serviceValue = amount * (servicePct / 100);
                    afterService = amount + serviceValue;
                    taxValue = amount *(taxPct / 100);
                    total = afterDiscount;
                    $("#withoutTaxServis").html(formatNumber(afterDiscount - serviceValue - taxValue));
                }else{
                    serviceValue = afterDiscount*(servicePct/100);
                    afterService = afterDiscount+serviceValue;
                    taxValue = afterDiscount*(taxPct/100);
                    total = afterDiscount+serviceValue+taxValue;
                }
                
                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] %>").val(discPct);
                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>").val(discNominal);
                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_SERVICE] %>").val(serviceValue);
                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_TAX] %>").val(taxValue);
                $("#jumlahDiskon").val(jumlahDiskon);
                $("#totalPrice").html(formatNumber(totalPrice));
                $("#afterDiscount").html(formatNumber(afterDiscount));
                $("#afterService").html(formatNumber(afterService));
                $("#total").html(formatNumber(total));
            });
        }

        $("#printnota").click(function(){
            $("#printnota").attr({"disabled":"true"});
            $("#command").val("<%= Command.NONE %>");
            $("#loadtype").val("printguest");
            var data = $("#<%= FrmCashPayment.FRM_NAME_CASH_PAYMENT %>").serialize();
            data = data + "&cashcashier=<%= cashCashier.getOID()%>";
            $.ajax({
                type	: "POST",
                url	: "<%= approot %>/TransactionCashierHandler",
                data	: data,
                cache	: false,
                dataType: "json",
                success	: function(data){
                    $("#printnota").removeAttr("disabled");
                    $("#CONTENT_PRINT").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
                    $("#CONTENT_PRINT_PREVIEW").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
                    $("#openprintpreview").modal("show");
                }
            });
        });

        $("body").on("click","#printnotaGuide",function(){
            $("#printnotaGuide").attr({"disabled":"true"});
            $("#command").val("<%= Command.NONE %>");
            $("#loadtype").val("printguide");
            var data = $("#<%= FrmCashPayment.FRM_NAME_CASH_PAYMENT %>").serialize();
            data = data + "&cashcashier=<%= cashCashier.getOID()%>";
            $.ajax({
                type	: "POST",
                url	: "<%= approot %>/TransactionCashierHandler",
                data	: data,
                cache	: false,
                dataType: "json",
                success	: function(data){
                    $("#printnotaGuide").removeAttr("disabled");
                    $("#CONTENT_PRINT").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
                    $("#CONTENT_PRINT_PREVIEW").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
                    $("#openprintpreview").modal("show");
                }
            });
        });

        $(".buttonCancel").click(function(){
            $("#openmovesetguest").modal(modalSetting);
            var oid= "";
            var cancelType = $(this).data("loadType");
            var textConfirm = "";
            var confirmed = false;
            var check = false;
            if(cancelType == "movebill"){
                $("#itemmovebill #loadtype").val("movebill");
                textConfirm = "Are you sure to move this bill?";
                var guestName = $("#itemmovebill #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME] %>").val();
                var cashbillmain = $("#itemmovebill #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] %>").val();
                var cashcashier = $("#itemmovebill #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID] %>").val();
                var loadtype = $("#itemmovebill #loadtype").val();
                oid = cashbillmain;
                check = true;
                var valOid = [];
                var valQty = [];
                var valPrice =[];
                var valDisc = [];

                $("#itemmovebill input[name=<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID] %>").each(function(i){
                    valOid[i] = $(this).val(); 
                    valQty[i] = $("input[name=<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>_"+valOid[i]+"]").val();
                    valPrice[i]= $("input[name=<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>_"+valOid[i]+"]").val();
                    valDisc[i]= $("input[name=<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>_"+valOid[i]+"]").val();
                });

                $("#<%= FrmBillMain.FRM_NAME_BILL_MAIN %> #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME] %>").val(guestName);
                $("#<%= FrmBillMain.FRM_NAME_BILL_MAIN %> #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] %>").val(cashbillmain);
                $("#<%= FrmBillMain.FRM_NAME_BILL_MAIN %> #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID] %>").val(cashcashier);
                $("#<%= FrmBillMain.FRM_NAME_BILL_MAIN %> #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").val(valQty);
                $("#<%= FrmBillMain.FRM_NAME_BILL_MAIN %> #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(valPrice);
                $("#<%= FrmBillMain.FRM_NAME_BILL_MAIN %> #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] %>").val(valDisc);
                $("#<%= FrmBillMain.FRM_NAME_BILL_MAIN %> #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID] %>").val(valOid);
                $("#<%= FrmBillMain.FRM_NAME_BILL_MAIN %> #loadtype").val(loadtype);		    
            }

            $("#openmovesetguest").modal("show");
            $("#FRM_MSG").hide();
            $(".buttonCancel").attr({"disabled":"true"});		    
            $("form#<%= FrmBillMain.FRM_NAME_BILL_MAIN %>").submit(function(e){
                confirmed = confirm(textConfirm);
                if(confirmed == true && check == true){
                    $("#FRM_MSG").hide();
                    $("#btnSaveMove").attr({"disabled":"true"});
                    $.ajax({
                        type	: "POST",
                        url	: "<%= approot %>/TransactionCashierHandler",
                        data	: $(this).serialize(),
                        cache	: false,
                        dataType    : "json",
                        success	: function(data){
                            $("#btnSaveMove").removeAttr("disabled");
                            $('#mainBillIdSource').val(0);
                            
                            if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>.substr(0,9) == "[SUCCESS]"){
                                var billOid = data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_DISPLAY_TOTAL] %> ;
                                $('#mainBillIdSource').val(billOid);
                                loadTransaction(billOid,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                                $('#openmovesetguest').modal('hide');
                                $('#openauthorize').modal('hide');
                                $("form#<%= FrmBillMain.FRM_NAME_BILL_MAIN %>").unbind('submit');
                            }else{
                                $("#FRM_MSG").html("<i class='fa fa-exclamaton'></i> "+data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                            }
                        }
                    });
                }
                return false;
            });
            $(".buttonCancel").removeAttr("disabled");		    
        });

        //OPEN AUTHORIZE DISC
        $(".authorizeCancel").click(function(){
            var useSpv = "<%= cancelBillSpv%>";
            var verification = <%=verificationType%>;
            if (useSpv=="1"){
                if (verification=="1"){
                    $('#verFInger').show();
                }
                var loadtype = $(this).data("loadType");
                var oid = $("#mainBillIdSource").val();
                var modalTitle = $(this).data("title");
                var confirmed = false;
                $("#openauthorize .modal-lg").removeClass("modal-lg");
                $("#cancelButton").hide();
                $("#AUTHORIZE_TITLE").html(modalTitle);
                $("#loadtypeauthorize").val(loadtype);
                $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] %>_AUTHORIZE").val(oid);
                $("#CONTENT_AUTHORIZE").hide();
                $("#<%= FrmAppUser.FRM_APP_USER %>").show();
                if(loadtype == "cancelbill"){
                    if($('#CONTENT_TOTAL').css('display') == 'none'){
                        confirmed = false;
                    }else{
                       confirmed = confirm("Are you sure to cancel all item in this bill?"); 
                    }
                }else{
                    confirmed = true;
                }
                if(confirmed == true){
                    checkUser();
                    $("#openauthorize").modal("show");
                    $(':input','#<%= FrmAppUser.FRM_APP_USER %>').not(':button, :submit, :reset, :hidden').val('');
                }$(':input','#<%= FrmAppUser.FRM_APP_USER %>').not(':button, :submit, :reset, :hidden').val('');
                checkUser();
            }else{
                var oid = $("#mainBillIdSource").val();
                var command = "<%= Command.SAVE%>";
                var note="cancel by <%= userName%>"; 
                var dataSend = "<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]%>="+oid+"&loadtype=cancelbill&command="+command+"&<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_NOTES]%>="+note+"";
                $.ajax({
                    type    : "POST",
                    url     : "<%= approot %>/TransactionCashierHandler",
                    data    : dataSend,
                    cache   : false,
                    dataType: "json",
                    success : function(data){
                        if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %> != "FAILED"){
                            window.location.reload();
                        }else{
                            alert("Failed to Cancel");
                        }
                    }
                });
                return false;
            }
        });

        $('.amountClosing').on('keyup', function() {
            var sumRp = 0;
            var sumUsd = 0;
            var totalRpString="";
            var totalUsString ="";
            $(".amountClosing").each(function(i){
                var value = $(this).val();
                if ( $( this ).hasClass( "amountRp" ) ) {
                    totalRpString = value;
                    try {
                        totalRpString = totalRpString.split(",").join("");    
                    } catch(err) {
                        totalRpString = "0";
                    }
                    if (totalRpString.length<=0){
                        totalRpString = "0";
                    }
                    var totalRp= parseFloat(totalRpString);
                    sumRp = sumRp + totalRp;
                }else{
                    totalUsString = value;
                    try {
                        totalUsString = totalUsString.split(",").join("");    
                    } catch(err) {
                        totalUsString = "0";
                    }
                    if (totalUsString.length<=0){
                        totalUsString = "0";
                    }
                    var totalUs= parseFloat(totalUsString);
                    sumUsd = sumUsd + totalUs;
                }
            });               
            var jmlRp = parserNumber(String(sumRp),false,0,'');
            var jmlUs = parserNumber(String(sumUsd),false,0,''); 
            $('.totalCloseSubtotal1').val(jmlRp); $('.totalCloseSubtotal2').val(jmlUs);
            $("#<%=FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SUBTOTAL1]%>").val(sumRp);
            $("#<%=FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SUBTOTAL2]%>").val(sumUsd);
        });
        
        $("form#closing").submit(function(){
            var isClear = $("#isClear").val();
            var confirmText="There are still list orders at outlet. Are you sure to continue?";

            //UPDATED BY DEWOK 20190322, konfirmasi closing muncul kalo ada bill yg belum selesai
            var openBill = 0;
            if (openBill != 0) {
                if(confirm(confirmText)){
                    $("#closingButton").attr({"disabled":"true"});
                    $.ajax({
                        type	: "POST",
                        url	: "<%= approot %>/TransactionCashierHandler",
                        data	: $(this).serialize(),
                        cache	: false,
                        dataType: "json",
                        success	: function(data){
                            var temp = data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>;
                            $("#closingButton").removeAttr("disabled");
                            if(temp != "FAILED"){
                                if (printWhenClosing=="0"){
                                    $("#closing").hide();
                                    var message = data.FRM_FIELD_SHORT_MESSAGE;
                                    var no = $("#cashierSmsSender").val();
                                    var cashierLinkSms= $('#cashierLink').val();
                                    if(message.length > 0 && cashierLinkSms.length > 0 && no.length > 0){
                                        sendShortMessage(cashierLinkSms,no, message);
                                    }
                                    $("#closingButton").hide();
                                    $("#CONTENT_CLOSING").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).show();
                                    $("#CONTENT_PRINT").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                                    window.print();
                                    alert("Print Success");
                                }
                                window.location = "<%= approot %>/cashier/cashier-home.jsp";
                            }else{
                                $("#closingButton").show();
                                $("#RESULT_ERROR").html("<div class='row'><div class='box-body'><div class='col-md-12'><i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password!</div></div></div>").fadeIn("medium");
                            }
                        }
                    }).done(function(){

                    });
                }

            } else {
                $("#closingButton").attr({"disabled":"true"});
                $.ajax({
                    type	: "POST",
                    url		: "<%= approot %>/TransactionCashierHandler",
                    data	: $(this).serialize(),
                    cache	: false,
                    dataType	: "json",
                    success	: function(data){
                        var temp = data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>;
                        $("#closingButton").removeAttr("disabled");
                        if(temp != "FAILED"){
                            if (printWhenClosing=="0"){
                                $("#closing").hide();
                                var message = data.FRM_FIELD_SHORT_MESSAGE;
                                var no = $("#cashierSmsSender").val();
                                var cashierLinkSms= $('#cashierLink').val();
                                if(message.length > 0 && cashierLinkSms.length > 0 && no.length > 0){
                                    sendShortMessage(cashierLinkSms,no, message);
                                }
                                $("#closingButton").hide();
                                $("#CONTENT_CLOSING").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).show();
                                $("#CONTENT_PRINT").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                                window.print();
                                alert("Print Success");
                            }
                            window.location = "<%= approot %>/cashier/cashier-home.jsp";
                        }else{
                            $("#closingButton").show();
                            $("#RESULT_ERROR").html("<div class='row'><div class='box-body'><div class='col-md-12'><i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password!</div></div></div>").fadeIn("medium");
                        }
                    }
                }).done(function(){

                });
            }
            return false;
        });

        $("form#returnBill").submit(function(event){
            <% if (useExchangeAsDefault.equals("1")) {%>
                    $("#btnReturnExchange").trigger("click");	
            <% } %>
            var loadtypereturn = $("#loadtypereturn").val();
            if(loadtypereturn == "loadbillreturn"){
                $("#openauthorize #btnMove").hide();
                $("#openauthorize").modal("show");
                $("#CONTENT_AUTHORIZE").hide();
                $("#<%= FrmAppUser.FRM_APP_USER %>").show();
                $("#loadtypeauthorize").val("authorizereturn");
                $(':input','#<%= FrmAppUser.FRM_APP_USER %>').not(':button, :submit, :reset, :hidden').val('');
                checkUser();
            }else{
                $("#openauthorize #btnMove").show();
                $("#btnOpenReturn").attr({"disabled":"true"});
                var qtyOri;
                var value ;
                var canSave = false;
                $("#returnBill .alert").remove();
                $(".returntext").each(function(event){
                    var id = $(this).attr("id");
                    var idtemp = id.split("-");
                    var order = idtemp[1];
                    var idQtyOri = "qtyReturn-"+order+"";
                    var qtyOri = $("#"+idQtyOri+"").val();
                    var value = $(this).val();

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
                    $.ajax({
                        type	: "POST",
                        url	: "<%= approot %>/TransactionCashierHandler",
                        data	: $("form#returnBill").serialize(),
                        cache	: false,
                        dataType: "json",
                        success	: function(data){

                            $("#btnOpenReturn").removeAttr("disabled").html("<i class='fa fa-check'></i> Return");
                            $("#commandreturn").val(<%= Command.SAVE %>);
                            $("#openbill .input-group").hide();
                            $("#openbill .modal-dialog").addClass("modal-lg");
                            $("#CONTENT_SEARCH").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                            $("#CONTENT_PRINT").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                            window.print();
                            alert("Print Success");
                            window.location = "<%= approot %>/cashier/cashier-transaction.jsp";
                        }
                    }).done(function(){
                        $("#btnOpenReturn").removeAttr("disabled");
                    });
                }else{
                    $("#btnOpenReturn").removeAttr("disabled");
                }                   
            }
            return false;
        });
        //-- BUTTON CLICK END

        //FORMAT NUMBER
        function formatNumber(number){
            return number.toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,");
        }

        //FUNCTION BALANCE CALCULATION
        function balanceCalc(){
            var paymentType = $("#MASTER_<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE] %>").val();
            var discountPct = $("#discountpercentage").val();
            var discountVal = $("#discountnominal").val();
            discountVal= discountVal.split(",").join("");

            var service = parseFloat($("#service").val())/100;
            var tax = parseFloat($("#tax").val())/100;
            var balance = parseFloat($("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val());
            var ccCharge = parseFloat($("#ccCharge").val())/100;
            var taxInc = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>").val();
            var paidValue =  parseFloat($("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE] %>").val());
            var transactionType = $("#transaction_type").val();
            var cansave = $("#cansave").val();
            var returnVal = 0;
            var discount = 0;
            var serviceVal =0;
            var taxVal = 0;
            var ccChargeVal = 0;
            var balanceDisplay = 0;
            var totalAfterService = 0;
            var totalAfterTax = 0;
            var totalCharge = balance;

            if(discountPct.length > 0 && discountPct > 0){
               discount = balance*parseFloat(discountPct/100);
               balanceDisplay = balance-discount;
               $("#discountnominal").val(formatNumber(discount));

            }else{
                $("#discountpercentage").val(0);

                var check = $("#discountnominal").val();
                check= check.split(",").join("");
                if(check.length > 0){
                    discountVal = parseFloat(check);
                    discount = parseFloat(check);
                }else{
                    discountVal = 0;
                    discount = 0;
                }
                var discPct = discountVal/balance*100;
                balanceDisplay = balance-discountVal;                  
                $("#discountnominal").removeAttr("readonly");
            }				
            if(taxInc == <%= PstBillMain.INC_CHANGEABLE %> || taxInc == <%= PstBillMain.INC_NOT_CHANGEABLE %>){
                var balanceDisplays = balanceDisplay / (1+service+tax);
                balanceDisplay = balanceDisplays;
                serviceVal = balanceDisplay*service;
                totalAfterService = balanceDisplay+serviceVal;
                taxVal = balanceDisplay*tax;
                totalAfterTax = balanceDisplay+taxVal+serviceVal;
                ccChargeVal = totalAfterTax*ccCharge;
                totalCharge = totalAfterTax+ccChargeVal;
                
                $("#tempCostumBalance").val(totalAfterTax);
                $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>").val(totalAfterTax);
            }else{
                serviceVal = balanceDisplay*service;
                totalAfterService = balanceDisplay+serviceVal;
                taxVal = balanceDisplay*tax;
                totalAfterTax = balanceDisplay+taxVal+serviceVal;
                ccChargeVal = totalAfterTax*ccCharge;
                totalCharge = totalAfterTax+ccChargeVal;
                $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>").val(totalAfterTax);
                $("#tempCostumBalance").val(totalAfterTax);
            }
            $("#balance").html(formatNumber(balanceDisplay)).show();
            $("#serviceVal").html(formatNumber(serviceVal));
            $("#totalAfterService").html(formatNumber(totalAfterService));
            $("#taxVal").html(formatNumber(taxVal));
            $("#totalAfterTax").html(formatNumber(totalAfterTax));
            $("#ccChargeVal").html(formatNumber(ccChargeVal));
            $("#creditcardcharge").val(ccChargeVal);
            $("#totalCharge").html(formatNumber(totalCharge));

            if(paymentType == <%= PstCustomBillMain.PAYMENT_TYPE_FOC %>){
                $("#info").html("RETURN");
                $("#cansave").val("true");
                $("#nominal").html(0);
                $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN] %>").val(0);
            }else{
                if(paidValue-totalCharge >= 0){
                    returnVal = paidValue-totalCharge;
                    $("#info").html("RETURN");
                    $("#cansave").val("true");
                    $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN] %>").val(returnVal);
                }else{
                    returnVal = totalCharge-paidValue;
                    $("#info").html("TOTAL");
                    if(transactionType != 0){
                        $("#cansave").val(cansave);
                    }else{
                        $("#cansave").val("false");
                    }
                    $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN] %>").val(0)
                }
                $("#nominal").html(formatNumber(returnVal));
            }
            $("#transaction_type").trigger("change");
        }

        //FUNCTION BALANCE CALCULATION 2
        function balanceCalc2(){
            var paymentType = $("#MASTER_<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE] %>").val();
            var discountPct = $("#discountpercentage").val();
            var discountVal = $("#discountnominal").val();
            discountVal= discountVal.split(",").join("");
            var service = parseFloat($("#service").val())/100;
            var tax = parseFloat($("#tax").val())/100;
            var balance = parseFloat($("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val());
            var ccCharge = parseFloat($("#ccCharge").val())/100;
            var taxInc = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>").val();
            var paidValue =  parseFloat($("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE] %>").val());
            var transactionType = $("#transaction_type").val();
            var cansave = $("#cansave").val();
            var returnVal = 0;
            var discount = 0;
            var serviceVal =0;
            var taxVal = 0;
            var ccChargeVal = 0;
            var balanceDisplay = 0;
            var totalAfterService = 0;
            var totalAfterTax = 0;
            var totalCharge = balance;
            if(discountPct.length > 0 && discountPct > 0){
               discount = balance*parseFloat(discountPct/100);
               balanceDisplay = balance-discount;
               $("#discountnominal").val(formatNumber(discount));
            }else{
                $("#discountpercentage").val(0);
                var check = $("#discountnominal").val();
                check= check.split(",").join("");
                if(check.length > 0){
                    discountVal = check;
                    discount = parseFloat(check);
                }else{
                    discountVal = 0;
                    discount = 0;
                }                  
                balanceDisplay = balance-discountVal;
            }

            if(taxInc == <%= PstBillMain.INC_CHANGEABLE %> || taxInc == <%= PstBillMain.INC_NOT_CHANGEABLE %>){
                var balanceDisplays = balanceDisplay / (1+service+tax);
                balanceDisplay = balanceDisplays;
                serviceVal = balanceDisplay*service;
                totalAfterService = balanceDisplay+serviceVal;
                taxVal = balanceDisplay*tax;
                totalAfterTax = balanceDisplay+taxVal+serviceVal;
                ccChargeVal = balanceDisplay*ccCharge;
                totalCharge = balanceDisplay+ccChargeVal;
                $("#tempCostumBalance").val(totalAfterTax);
                $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>").val(totalAfterTax);
            }else{
                serviceVal = balanceDisplay*service;
                totalAfterService = balanceDisplay+serviceVal;
                taxVal = balanceDisplay*tax;
                totalAfterTax = balanceDisplay+taxVal+serviceVal;
                ccChargeVal = totalAfterTax*ccCharge;
                totalCharge = totalAfterTax+ccChargeVal;
                $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>").val(totalAfterTax);
                $("#tempCostumBalance").val(totalAfterTax);
            }

            $("#balance").html(formatNumber(balanceDisplay)).show();
            $("#serviceVal").html(formatNumber(serviceVal));
            $("#totalAfterService").html(formatNumber(totalAfterService));
            $("#taxVal").html(formatNumber(taxVal));
            $("#totalAfterTax").html(formatNumber(totalAfterTax));
            $("#ccChargeVal").html(formatNumber(ccChargeVal));
            $("#creditcardcharge").val(ccChargeVal);
            $("#totalCharge").html(formatNumber(totalCharge));

            if(paymentType == <%= PstCustomBillMain.PAYMENT_TYPE_FOC %>){
                $("#info").html("RETURN");
                $("#cansave").val("true");
                $("#nominal").html(0);
                $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN] %>").val(0);
            }else{
                if(paidValue-totalCharge >= 0){
                    returnVal = paidValue-totalCharge;
                    $("#info").html("RETURN");
                    $("#cansave").val("true");
                    $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN] %>").val(returnVal);
                }else{
                    returnVal = totalCharge-paidValue;
                    $("#info").html("TOTAL");
                    if(transactionType != 0){
                        $("#cansave").val(cansave);
                    }else{
                        $("#cansave").val("false");
                    }
                    $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN] %>").val(0)
                }
                $("#nominal").html(formatNumber(returnVal));
            }

        }

        //FUNCTION LOAD THE FUNCTION
        function innerFunction(){
            itemChange();
            $("form#<%= FrmBillDetail.FRM_NAME_BILL_DETAIL %>").submit(function(){
               $("#btnDeleteItem").attr({"disabled":"true"}).html("Please Wait...");
               $("#btnSaveBiillDetail").attr({"disabled":"true"}).html("Please Wait...");
               var load = $('#FRM_NAME_BILL_DETAIL #loadtype').val();
               var billMainId = $('#FRM_NAME_BILL_DETAIL #billMainId').val();

               $.ajax({
                    type    : "POST",
                    url     : "<%= approot %>/TransactionCashierHandler",
                    data    : $(this).serialize(),
                    cache   : false,
                    dataType: "json",
                    success : function(data){
                        $("#btnSaveBillDetail").removeAttr("disabled").html("Save");
                        $("#btnDeleteItem").removeAttr("disabled").html("Continue");
                        if (load!="editItemProc"){
                            if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %> != "FAILED"){
                                $("#openauthorize").modal("hide");
                                loadTransaction(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                            }else{
                                $("#CONTENT_AUTHORIZE").html("FAILED");
                            }
                        }else{

                            if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %> != "FAILED"){
                                $("#openauthorize").modal("hide");
                                loadTransaction(billMainId,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                                setTimeout(function(){ 
                                    $('#openitemlist #searchItemModal').focus();
                                }, 200);
                            }else{
                                $("#CONTENT_LOAD").html("FAILED");
                            }
                        }
                    }
                });
                return false;
            });

            $("form#<%= FrmBillMain.FRM_NAME_BILL_MAIN %>_CANCEL").submit(function(){
               $("#btnSaveBiillDetail").attr({"disabled":"true"}).html("Please Wait...");
               $.ajax({
                    type    : "POST",
                    url     : "<%= approot %>/TransactionCashierHandler",
                    data    : $(this).serialize(),
                    cache   : false,
                    dataType: "json",
                    success : function(data){
                        $("#btnSaveBillDetail").removeAttr("disabled").html("Save");
                        if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %> != "FAILED"){
                            $("#openauthorize").modal("hide");
                            $("#CONTENT_AUTHORIZE").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                            window.location.reload();
                        }else{
                            $("#CONTENT_AUTHORIZE").html("FAILED");
                        }
                    }
                });
                return false;
            });
        };

        //FUNTION LOAD PAGE
        function loadTransactionWithoutDone(oidTransaction, loadtype, command, cashier, elementLoad){
            var costumBalance = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>").val();
            var balanceVal = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val();
            var taxInc = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>").val();
            if(loadtype != "loadbill"){
                $("#"+elementLoad).html("Please wait...").fadeIn("medium");
            }
            $.ajax({
                type    : "POST",
                url     : "<%= approot %>/TransactionCashierHandler",
                data    : {
                    "loadtype":loadtype,
                    "<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] %>":oidTransaction,
                    "command":command,
                    "cashier":cashier,
                    "<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>":costumBalance,
                    "<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE]%>":balanceVal,
                    "<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>":taxInc
                },
                dataType : "json",
                cache   : false,
                success : function(data){

                    if(loadtype == "loadbill"){
                        $("#CONTENT_ANIMATE_CHECK").fadeOut("medium");
                        $("#CONTENT_TOTAL").fadeIn("medium");
                    }
                    $("#"+elementLoad).html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium")
                    $("#nominal").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_DISPLAY_TOTAL] %>).show();
                    $("#info").html("TOTAL");
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE] %>);
                },
                error : function(){
                    
                }
            }).done(function(){
                $("#btnOpenSearchMember").click(function(){
                    var searchtype = $("#searchtype").val();
                    var classType = $("#<%= FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE] %>").val();
                    var member = $("#member").val();
                    $("#buttonInHouseGuest").hide();
                    var oidcashbillmainguest = $("#btnPay").data("oid");
                    if(searchtype == "guest"){
                        $("#btnGuest").attr({"disabled":true});
                        $("#btnTravelagent").removeAttr("disabled");
                    }else{
                        $("#btnTravelagent").attr({"disabled":true});
                        $("#btnGuest").removeAttr("disabled");
                    }

                    $("#opensearchmember").modal("show");

                    if(member.indexOf("#") != -1){
                        $("#buttonInHouseGuest").show();
                    }
                    searchMember("","loadsearchmember",<%= Command.NONE %>,classType,"CONTENT_SEARCH_MEMBER",searchtype,oidcashbillmainguest);
                });

                //MASTER PAYMENT TYPE CHANGE
                $("#MASTER_<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE] %>").change(function(){
                    $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN] %>").val(0);
                    $("#cansave").val("false");
                    $("#result").fadeOut("fast");
                    $("#resultSave").fadeOut("fast");
                    $("#CONTENT_PAYMENT_TYPE").fadeOut("fast");

                    if($(this).val() == <%= PstCustomBillMain.PAYMENT_TYPE_FOC %>){
                        $("#cansave").val("true");
                    }
                    var balance = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val();
                    paymentSystem($(this).val(),"loadlistpayment",<%= Command.NONE %>,<%= cashCashier.getOID() %>,"CONTENT_SUB_PAYMENT_SYSTEM_CASH");

                });
                //added by dewok for ato select payment if only one available
                $("#MASTER_<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE] %>").trigger("change");
            });	
        }
        
        function loadTransaction(oidTransaction, loadtype, command, cashier, elementLoad){
            var costumBalance = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>").val();
            var balanceVal = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val();
            var taxInc = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>").val();
            var allowOpen = "<%= allowOpenBill%>";
            if(loadtype !== "loadbill"){
                $("#"+elementLoad).html("Please wait...").fadeIn("medium");
            }
            var isPack = $("#transactionPackage").val();
            $.ajax({
                type    : "POST",
                url     : "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                data    : {
                    "loadtype":loadtype,
                    "<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] %>":oidTransaction,
                    "command":command,
                    "cashier":cashier,
                    "<%=BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE]%>":costumBalance,
                    "<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE]%>":balanceVal,
                    "<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>":taxInc, "isPack": isPack
                },
                cache   : false,
                success : function(data){
                    if(loadtype == "loadbill"){
                        $("#CONTENT_ANIMATE_CHECK").fadeOut("medium");
                        $("#CONTENT_TOTAL").fadeIn("medium");
                        $("#searchBarcode").focus();
                        var checkOut = $("#checkOutItemViaOutlet").val();
                        var support = $("#cashierSupportProduction").val();               
                        if (Number(data.FLD_STATUS_ORDER)>0 && Number(checkOut)==1 && Number(support)==1){
                            $("#btnPay").fadeOut();
                        }else{
                            $("#btnPay").fadeIn();
                        }
                        <%if (useProduction.equals("1")){%>
                            var status = $("#productionStatus").val();
                            $('#btnPay').css('display', 'none');
                            $('#btnBiayaTambahan').css('display', 'none');
                            if (status == <%=PstBillMain.PETUGAS_DELIVERY_STATUS_DRAFT%>){
                                $('#btnBiayaTambahan').css('display', 'block');
                            } else if (status == <%=PstBillMain.PETUGAS_DELIVERY_STATUS_DITERIMA %> || status == <%=PstBillMain.PETUGAS_DELIVERY_STATUS_DIAMBIL_LANGSUNG%>){
                                $('#btnPay').css('display', 'block');
                                $('#btnBiayaTambahan').css('display', 'none');
                            } 
                        <% } %>
                    }
                    //cek unit produksi
                    if (cashierSupportProduction=="1"){
                        var oidCheck = $('#mainBillIdSource').val();
                        checkPrintStatus(oidCheck);
                    }
                    if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %> == "SPECIAL"){ 
                        $("#CONTENT_INFO").hide();
                        $("#btnPaySave").hide();
                        $('#btnPayFinger').hide();
                        $("#printnota").hide();
                        $("#"+elementLoad).html("<i class='fa fa-exclamation'></i> Can not make the payment because the price of special items has not been entered. <br>Please give a special item price first").fadeIn("medium");
                    }else{
                        $("#CONTENT_INFO").show();
                        $("#btnPaySave").show();
                        $('#btnPayFinger').show();
                        $("#printnota").show();
                        $("#"+elementLoad).html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                    }
                    if (allowOpen=="0"){
                        $("#printnota").hide();
                    }
                    if (data.RETURN_USE_GUIDE_PRICE) {
                        $("#printnotaGuide").show();
                    } else {
                        $("#printnotaGuide").hide();
                    }
                    $("#nominal").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_DISPLAY_TOTAL] %>).show();
                    $("#info").html("TOTAL");
                    $("#ccCharge").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>);
                    $("#totalCharge").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_DISPLAY_TOTAL] %>);
                    var ccChargeVal = parseFloat(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>)*(parseFloat(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>)/100);
                    $("#ccChargeVal").html(formatNumber(ccChargeVal));
                    $("#creditcardcharge").val(ccChargeVal);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>);
                 },
                 error : function(){
                    
                 }
            }).done(function(){
                var grandTotal = $("#grandTotalData").val();
                $("#grandTotal").html(grandTotal);
                moneyFormat();
                if(loadtype == "loadpay"){
                    var $sigdiv = $("#signature").jSignature({
                        'background-color': 'transparent',
                        'decor-color': 'transparent',
                        'height':'112'
                    });
                    $("#signature").change(function(){
                        var datapair = $(this).jSignature("getData", "default");
                        $("#IMAGE_STRING").val(datapair);
                    });
                    $("#resetsignature").click(function(){
                       $sigdiv.jSignature("reset"); 
                    });
                    var typeCustomer = $('#member option:selected').text();
                    if( (<%=useSignature%>)&& (typeCustomer=="MEMBER" || typeCustomer=="EMPLOYEE" || typeCustomer=="IN HOUSE GUEST")){
                        $("#signatureArea").fadeIn("medium");
                    }else{
                        $("#signatureArea").fadeOut("fast");
                    } 
                }else if (loadtype=="loadsearch" || loadtype=="loadsearchreturn"){
                   tableNav();
                   selectBillToOpen();
                   searchBillToReturn();
                   textual2();
                   datasearch();
                   textual5();
                }else if (loadtype=="loadbill"){
                   addItemPackage(".btnAddPackageItem");
                   textual3();  
                   $(".confirmorder").click(function(){
                        var id = $(this).data("billdetail");
                        var url = ""+base+"/TransactionCashierHandler";
                        var data = "command=<%= Command.SAVE%>&oid="+id+"&loadtype=confirmorder&userid=<%= userId%>";
                        ajaxTransactionHandler(url,data,"POST","","confirmorder","","");
                   });
                }
//                else if (loadtype =="production"){
//                    window.location.reload(true);
//                }
                $("#member").change(function(){
                    $("#result").fadeOut("fast");
                    $("#resultSave").fadeOut("fast");
                    $("#CONTENT_PAYMENT_TYPE").fadeOut("fast");			
                    $("#CONTENT_SUB_PAYMENT_SYSTEM_CASH").fadeOut("fast");
                    $("#CONTENT_PAYMENT_SYSTEM").fadeOut("fast");
                    $("#transaction_type").val("");
                    $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] %>").val("");
                    var typeCustomer = $('#member option:selected').text();
                    if( ((<%=useSignature%>)&&(typeCustomer=="MEMBER" || typeCustomer=="EMPLOYEE" || typeCustomer=="IN HOUSE GUEST" ))){
                        $("#signatureArea").fadeIn("medium");
                    }else{
                        $("#signatureArea").fadeOut("fast");
                    }
                    loadTransactionWithoutDone($(this).val(),"loadsubmembertype",<%= Command.NONE %>,<%= cashCashier.getOID() %>,"CONTENT_SUB_PAYMENT_SYSTEM");
                });

                //TRANSACTION TYPE CHANGE
                $("#transaction_type").change(function(){
                    $("#result").fadeOut("fast");
                    $("#CONTENT_SUB_PAYMENT_SYSTEM_CASH").fadeOut("fast");
                    $("#CONTENT_PAYMENT_SYSTEM").fadeOut("fast");
                    $("#CONTENT_PAYMENT_TYPE").fadeOut("fast");
                    var searchtype = $("#searchtype").val();
                    var member = $("#member").val();
                    var memberText = $("#member option:selected").text();
                    var customerId = $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] %>").val();
                    var billMain = $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] %>").val();
					
					var creditType = <%=creditType%>;
					
                    if($(this).val() == 1 && member.length > 0){ 
                        if(memberText=="NONE"){ creditPass=0;
                            alert("Only for customers as a member");
                            $(this).val("");
                        }else{
                            if(customerId.length != ""){
                                if(memberText == "CUSTOMER"){
                                    $("#loadtypeauthorize").val("authorizecredit");
                                    creditPass=1;
                                    $("#spvFinger").val('');
                                    checkUser();
                                    $("#openauthorize #btnMove").fadeOut("fast");
                                    $("#openauthorize").modal("show");                                      
                                    $("#CONTENT_AUTHORIZE").hide();
                                     $("#<%= FrmAppUser.FRM_APP_USER %>").show();
                                    $(':input','#<%= FrmAppUser.FRM_APP_USER %>').not(':button, :submit, :reset, :hidden').val('');
                                    checkUser();
                                }else if(member.indexOf("#") != -1){ creditPass=0;
                                    searchMember(customerId,"checkcreditinhouseguest",<%= Command.NONE %>,0,"CONTENT_SUB_PAYMENT_SYSTEM_CASH","");
                                }else if(creditType===1){
									loadTransactionWithoutDone($(this).val(),"loadpayment",<%= Command.NONE %>,billMain,"CONTENT_PAYMENT_SYSTEM");
								}else{ creditPass=0;
                                    searchMember(customerId,"checkmemberlimit",<%= Command.NONE %>,0,"CONTENT_SUB_PAYMENT_SYSTEM_CASH", searchtype);
                                }
                            }else{
                                alert("Please select customer");
                                $(this).val("");
                            }
                        }

                    }else if(($(this).val() == 0 || $(this).val() == 2) && member.length > 0){
						if(member == 0){
                            loadTransactionWithoutDone($(this).val(),"loadpayment",<%= Command.NONE %>,billMain,"CONTENT_PAYMENT_SYSTEM");
                        }else{
                            if(customerId.length != ""){
                               loadTransactionWithoutDone($(this).val(),"loadpayment",<%= Command.NONE %>,billMain,"CONTENT_PAYMENT_SYSTEM");
                            }else{
                               alert("Please select customer");
                               $(this).val("");
                            }
                        }
                    }else{
                        alert("Please select customer type");
                        $(this).val("");
                    }
                });

                //DISC, TAX, SERVICE CHANGED
                $(".charge").keyup(function(){
                    balanceCalc();
                });

                $(".chargeImp").keyup(function(){
                    balanceCalc2();
                });

                //DISCOUNT PCT CHANGED
                $("#discountpercentage").keyup(function(){
                    var thisVal = parseFloat($(this).val());
                    if(thisVal == 0){
                        $("#discountnominal").val(0);
                    }
                    balanceCalc();
                });

                //DISCOUNT NOMINAL CHANGE
                $("#discountnominal").keyup(function(){
                    var thisVal = parseFloat($(this).val());
                    if(thisVal == 0){
                        $("#discountpercentage").val(0);
                    }
                    balanceCalc();
                });
                 //AUTHORIZE
                $(".authorize").click(function(){
                    var verification = <%=verificationType%>;
                    if (verification=="1"){
                       $('#verFInger').show(); 
                    }
                    var loadtype = $(this).data("loadType");
                    if (loadtype=="edititem" || loadtype=="delitem" ){
                        var oid = $(this).data("oid"); 
                    }else{
                        var oid = $('#mainBillIdSource').val();
                    }
                    var modalTitle = $(this).data("title");
                    var confirmed = true;
                    $("#openauthorize .modal-lg").removeClass("modal-lg");
                    $("#cancelButton").hide();
                    $("#AUTHORIZE_TITLE").html(modalTitle);
                    $("#loadtypeauthorize").val(loadtype);
                    $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] %>_AUTHORIZE").val(oid);
                    $("#CONTENT_AUTHORIZE").hide();
                    $("#<%= FrmAppUser.FRM_APP_USER %>").show();
                    if (loadtype=="movebill"){
                        if($('#CONTENT_TOTAL').css('display') == 'none'){
                            confirmed=false;
                        }else{
                            confirmed=true;
                        }
                    }
                    if(confirmed == true){
                        if (loadtype=="opendiscount"){
                            if (openDiscUsingSpv=="0"){
                                $("#openauthorize").modal("show");
                                $(':input','#<%= FrmAppUser.FRM_APP_USER %>').not(':button, :submit, :reset, :hidden').val('');
                                checkUser();
                            }else{
                                $("#discountnominal").removeAttr("readonly");
                                $("#discountpercentage").removeAttr("readonly");
                                $("#discountpercentage").focus();
                            }
                        }else{ 
                            $("#openauthorize").modal("show");
                            $(':input','#<%= FrmAppUser.FRM_APP_USER %>').not(':button, :submit, :reset, :hidden').val('');
                            checkUser();
                        }        
                    }       
                    var useSpv = "<%= editItemSpv%>";
                    if (loadtype=="edititem" && useSpv=="0"){
                        $('#btnAuthorize').trigger('click')
                    }	
                });

                //OPEN MODAL TO SEARCH MEMBER OF CREDIT
                $("#btnOpenSearchMember").click(function(){
                    var classType = $("#<%= FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE] %>").val();
                    var searchtype = $("#searchtype").val();
                    var member = $("#member").val();
                    $("#buttonInHouseGuest").hide();
                    if(searchtype == "guest"){
                        $("#btnGuest").attr({"disabled":true});
                        $("#btnTravelagent").removeAttr("disabled");
                    }else{
                        $("#btnTravelagent").attr({"disabled":true});
                        $("#btnGuest").removeAttr("disabled");
                    }
                    $("#opensearchmember").modal("show");
                    if(member.indexOf("#") != -1){
                        $("#buttonInHouseGuest").show();
                    }
                    searchMember("","loadsearchmember",<%= Command.NONE %>,classType,"CONTENT_SEARCH_MEMBER",searchtype);
                });

                $(".btnsearchtype").click(function(){
                    var searchtype = $(this).data("searchType");
                    var classType = $("#<%= FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE] %>").val();
                    $(".btnsearchtype").removeAttr("disabled");
                    $("#searchtype").val(searchtype);
                    $(this).attr({"disabled":true});
                    searchMember("","loadsearchmember",<%= Command.NONE %>,classType,"CONTENT_SEARCH_MEMBER",searchtype);
                });
                innerFunction();

                //PENAMBAHAN FUNGSI UNTUK PERSIAPAN SMS
                loadGuestDetailByBillMain(oidTransaction);

                $('.bill-profile').click(function(){
                    var oid = $(this).data('id');
                    var code = $(this).data('code');
                    var name = $(this).data('name');
                    var phone = $(this).data('phone');
                    var group = $(this).data('membergroup');
                    $('#newMemberOid').val(oid);
                    $('#newMemberCode').val(code);
                    $('#newMemberName').val(name);
                    $('#newMemberPhone').val(phone);
                    $('#newMemberGroup').val(group);

                    if (oid === 0 || oid === "0") {
                        $('#btnDeleteMember').hide();
                    } else {
                        $('#btnDeleteMember').show();
                    }
                    $("#updatesalesModal").modal(modalSetting2);
                    $("#updatesalesModal").modal("show");
                });

                $("#transaction_type").trigger("change");
             });
         }

        function loadGuestDetailByBillMain(oidBillMain){
            var url = ""+bases+"/TransactionCashierHandler";
            var data = "loadtype=getguestdetail&command=<%=Command.NONE%>&oidBillMain="+oidBillMain+""; 
            ajaxTransactionHandler(url,data,"POST","","getguestdetail","","");
        }

        function loadTransaction2(oidTransaction, loadtype, command, cashier, elementLoad,date1,date2,location,tableNumber, showPage){
            if(loadtype != "loadbill"){
                $("#"+elementLoad).html("Please wait...").fadeIn("medium");
            }
            $.ajax({
                type    : "POST",
                url     : "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                data    : {
                    "loadtype":loadtype,
                    "<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] %>":oidTransaction,
                    "command":command,"cashier":cashier,"date1":date1,"date2":date2,"location":location,"table": tableNumber,"showPage": showPage    
                },		
                cache   : false,
                success : function(data){
                    $("#"+elementLoad).html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");   
                },
                error : function(){
                }
            }).done(function(){
                if (loadtype=="loadsearch" || loadtype=="loadsearchreturn"){
                    pageUniv (".pagingBill","#totalPagingBill","#paggingPlaceBIll","#nextPageBIll","#btnSearch");
                    tableNav();
                    selectBillToOpen();
                    searchBillToReturn();
                    textual2();
                    datasearch();
                    textual5();  
                }
            });
        }
        
        function paymentSystem(oidTransaction, loadtype, command, cashier, elementLoad){
            var costumBalance = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>").val();
            var balanceVal = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val();
            var taxInc = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>").val();
            if(loadtype != "loadbill"){
                $("#"+elementLoad).html("Please wait...").fadeIn("medium");
            }

            $.ajax({
                type    : "POST",
                url     : "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                data    : {
                    "loadtype":loadtype,
                    "<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] %>":oidTransaction,
                    "command":command,
                    "cashier":cashier,
                    "cardReader": <%= cardReader%>,
                    "<%=BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE]%>":costumBalance,
                    "<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE]%>":balanceVal,
                    "<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>":taxInc
                },
                cache   : false,
                success : function(data){

                    if(loadtype == "loadbill"){
                        $("#CONTENT_ANIMATE_CHECK").fadeOut("medium");
                        $("#CONTENT_TOTAL").fadeIn("medium");
                    }
                    $("#"+elementLoad).html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                    $("#nominal").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_DISPLAY_TOTAL] %>).show();
                    $("#info").html("TOTAL");

                    $("#ccCharge").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>);
                    $("#totalCharge").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_DISPLAY_TOTAL] %>);

                    var ccChargeVal = parseFloat(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>)*(parseFloat(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>)/100);
                    $("#ccChargeVal").html(formatNumber(ccChargeVal));
                    $("#creditcardcharge").val(ccChargeVal);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>);
                },
                error : function(){
                    alert("error");
                }
            }).done(function(data){
                //PAYMENT TYPE CHANGE
                $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE] %>").change(function(){
                    $("#result").fadeOut("fast");
                    $("#resultSave").fadeOut("fast");
                    var balance = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val();
                    subPaymentSystem($(this).val(),"loadsubpayment",<%= Command.NONE %>,<%= cashCashier.getOID() %>,"CONTENT_PAYMENT_TYPE");
                });

                //added by dewok for ato select payment if only one available
                if (data.RETURN_PAYMENT_AVAILABLE === "1") {
                    $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE] %>").trigger("change");
                }
            });
        }

        function subPaymentSystem(oidTransaction, loadtype, command, cashier, elementLoad){
            var costumBalance = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>").val();
            var balanceVal = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val();
            var taxInc = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>").val();
            var billMainId = $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] %>").val();
            var transType = $("#transaction_type").val();
            if(loadtype != "loadbill"){
                $("#"+elementLoad).html("Please wait...").fadeIn("medium");
            }

            $.ajax({
                type    : "POST",
                url     : "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                data    : {
                    "loadtype":loadtype,
                    "<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] %>":oidTransaction,
                    "command":command,
                    "cashier":cashier,
                    "cardReader": <%=cardReader%>,
                    "<%=BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE]%>":costumBalance,
                    "<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE]%>":balanceVal,
                    "<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>":taxInc,
                    "BILL_MAIN_ID":billMainId,
                    "transaction_type":transType
                },
                cache   : false,
                success : function(data){

                    if(loadtype == "loadbill"){
                        $("#CONTENT_ANIMATE_CHECK").fadeOut("medium");
                        $("#CONTENT_TOTAL").fadeIn("medium");
                    }
                    $("#"+elementLoad).html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                    $("#nominal").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_DISPLAY_TOTAL] %>).show();
                    $("#info").html("TOTAL");

                    $("#ccCharge").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>);
                    $("#totalCharge").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_DISPLAY_TOTAL] %>);

                    var ccChargeVal = parseFloat(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>)*(parseFloat(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>)/100);
                    $("#ccChargeVal").html(formatNumber(ccChargeVal));
                    $("#creditcardcharge").val(ccChargeVal);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>);
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>);
                },
                error : function(){
                    
                }
            }).done(function(data){
                $(".datePicker").datepicker({
                    format: 'yyyy-mm-dd'
                }).on("changeDate", function (ev) {
                    $(this).datepicker('hide');
                });

                moneyFormat();

                $(".getaccount").on("change",function(e){
                    var material = $(".getaccount").val();
                    var splitMaterial = material.split("^");
                    var materialNumber = splitMaterial[0].replace("%B","").trim();
                    materialNumber = materialNumber.substring(4,materialNumber.length);
                    materialNumber = "xxxx" + materialNumber;
                    var materialName = splitMaterial[1].trim();
                    var materialExpDate = splitMaterial[2];
                    var dateNow = new Date();

                    var year = dateNow.getFullYear().toString();
                    var limitGetNumber = year.length-2;
                    var displayExpDate = year.substring(0,limitGetNumber)+""+materialExpDate.substring(0,2)+"-"+materialExpDate.substring(2,4)+"-01";
                    $("#<%= FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] %>").val(materialNumber);
                    $('.datepicker').val(displayExpDate);
                    $('.datepicker').datepicker('update', displayExpDate);
                    $(this).val(materialName);
                    $(this).attr({"readonly":"readonly"});
                });
                $(".getaccountDebit").on("change",function(e){
                    var materialDebit = $(".getaccountDebit").val();
                    var splitMaterialDebit = materialDebit.split("^");
                    var materialNumberDebit = splitMaterialDebit[0].replace("%B","").trim();
                    materialNumberDebit = materialNumberDebit.substring(4,materialNumberDebit.length);
                    materialNumberDebit = "xxxx" + materialNumberDebit;
                    var materialExpDateDebit = splitMaterialDebit[2];
                    var dateNow = new Date();
                    var year = dateNow.getFullYear().toString();
                    var limitGetNumber = year.length-2;
                    var displayExpDate = year.substring(0,limitGetNumber)+""+materialExpDateDebit.substring(0,2)+"-"+materialExpDateDebit.substring(2,4)+"-01";
                    $('.datepicker').val(displayExpDate);
                    $('.datepicker').datepicker('update', displayExpDate);
                    $(this).val(materialNumberDebit);
                    $(this).attr({"readonly":"readonly"});
                });

                $("#ccReScan").click(function(){
                    $(".getaccount").removeAttr("readonly").val('');
                    $('.datepicker').val('');
                    $('.datepicker').datepicker('update', '');
                    $("#<%= FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] %>").val('');
                });
                $("#debitReScan").click(function(){
                    $(".getaccountDebit").removeAttr("readonly").val('');
                    $('.datepicker').val('');
                    $('.datepicker').datepicker('update', '');
                });
                var paidVal = parseFloat($("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE] %>").val());
                var ccCharge = parseFloat($("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>").val());
                var balanceVal = parseFloat($("#tempCostumBalance").val());
                var balanceVal2 = parseFloat($("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val());
                var taxInc = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>").val();
                var discPct = parseFloat($("#discountpercentage").val());
                var discNominal =$("#discountnominal").val();
                discNominal = discNominal.split(",").join("");
                discNominal = parseFloat(discNominal);
                var paymentType = $("#MASTER_<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE] %>").val();
                if(discPct > 0){
                    balanceVal2 = balanceVal2-(balanceVal2*(discPct/100));
                }else{
                    balanceVal2 = balanceVal2-discNominal;
                }
                $("#ccCharge").val(ccCharge);
                var ccChargeVal = 0;
                if(taxInc == 0 || taxInc == 2){
                    ccChargeVal = balanceVal2*(ccCharge/100);
                    var totalCharge = (balanceVal2)+ccChargeVal;
                }else{
                    ccChargeVal = balanceVal*(ccCharge/100);
                    var totalCharge = (balanceVal)+ccChargeVal;
                }
                var returnVal = 0;
                $("#ccChargeVal").html(formatNumber(ccChargeVal));
                $("#creditcardcharge").val(ccChargeVal);
                $("#totalCharge").html(formatNumber(totalCharge));

                if (paymentType == "<%= PstCustomBillMain.PAYMENT_TYPE_CASH %>" && autoFocusCash){
                    $("#<%=FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]%>").focus();
                    $("#<%=FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]%>").one("focusout", function(){
                        $("#<%=FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]%>").focus();
                    });
                }

                if(paymentType != <%= PstCustomBillMain.PAYMENT_TYPE_FOC %>){
                    if((paidVal-totalCharge) >= 0){
                        returnVal = paidVal-totalCharge;
                        $("#nominal").html(formatNumber(returnVal));
                        $("#info").html("RETURN");
                        $("#cansave").val("true");
                    }else{
                        returnVal = totalCharge-paidVal;
                        $("#nominal").html(formatNumber(returnVal));
                        $("#info").html("TOTAL");
                        $("#cansave").val("false");
                    }
                }else{
                    $("#nominal").html(formatNumber(0));
                    $("#info").html("RETURN");
                    $("#cansave").val("true");
                }

                $(".calc").keyup(function(){
                    var rate = 1;
                    if($(this).val().length == 0 || $(this).val() == 0){
                        paidVal = 0;
                    }else{
                        paidVal = $(this).val();
                        //CEK RATE NYA 
                        try {
                            var rateTemp = $("#FRM_FIELD_CURR_ID option:selected").data("rate");
                            rate = Number(rateTemp);
                        }
                        catch(err) {
                            rate=1;
                        }
                        paidVal = paidVal.split(",").join("");
                        paidVal = paidVal * rate;
                    }
                    $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_PAID_VALUE] %>").val(paidVal);

                    ccCharge = parseFloat($("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_CREDIT_CARD_CHARGE] %>").val());
                    balanceVal = parseFloat($("#tempCostumBalance").val());
                    totalCharge = balanceVal+ccChargeVal;

                    if(paymentType == <%= PstCustomBillMain.PAYMENT_TYPE_FOC %>){
                        $("#cansave").val("true");
                        $("#info").html("CHANGES");
                        $("#nominal").val(0);
                        $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN] %>").val(0)
                    }else{
                        if((paidVal-totalCharge) >= 0){
                            returnVal = paidVal-totalCharge;
                            $("#nominal").html(formatNumber(returnVal));
                            $("#info").html("CHANGES");
                            $("#cansave").val("true");
                            $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN] %>").val(returnVal);
                        }else{
                            returnVal = totalCharge-paidVal;
                            $("#nominal").html(formatNumber(returnVal));
                            $("#info").html("TOTAL");
                            $("#cansave").val("false");
                            $("#<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_RETURN] %>").val(0);
                        }
                    }

                });
                innerFunction();

                //Select Voucher
                searchVoucher("#searchVoucher");
            });
        }
        
        function searchMember(oidTransaction, loadtype, command, cashier, elementLoad, searchType,oidTransactionforguest){
            var costumBalance = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>").val();
            var balanceVal = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE] %>").val();
            var taxInc = $("#<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>").val();
            var memberCode = "";
            var memberName = "";
            if (loadtype === "loadsearchmembernewbill") {
                
            }
            if (loadtype === "loadsearchnewmember") {
                
            }
            
            $.ajax({
                type    : "POST",
                url     : "<%= approot %>/TransactionCashierHandler",
                data    : {
                    "loadtype":loadtype,
                    "<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] %>":oidTransaction,
                    "<%= FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] %>_GUEST":oidTransactionforguest,
                    "command":command,
                    "cashier":cashier,
                    "<%=BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE]%>":costumBalance,
                    "<%= BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE]%>":balanceVal,
                    "<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %>":taxInc,
                    "searchType":searchType,
                    "filterMemberCode":memberCode,
                    "filterMemberName":memberName
                },
                dataType : "json",
                cache   : false,
                success : function(data){

                    if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %> == "FAILED"){
                        $("#"+elementLoad).html("<div class='row'><div class='box-body'><div class='col-mod-12'>Your credits have exceeded credits limit or expired</div></div></div>").fadeIn("medium");
                        $("#cansave").val("false");
                    }else{
                        if(loadtype == "checkmemberlimitnonmember" || loadtype=="checkcreditinhouseguest"){
                            $("#cansave").val("true");
                        }else{
                            $("#cansave").val("false");
                        }
                        $("#"+elementLoad).html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                    }
                },
                error : function(){
                    
                }
            }).done(function(){
                if (loadtype="checkmemberlimitnonmember"){
                    $('#ctOpenListCustomer').click(function(){
                        loadCtCustomer();
                    });
                }
                clickSelectGuest();
                catchKey();
            });
        }
        
        $("#searchItemNew").keyup(function(){
            $("#searchItemNewButton").trigger("click")
        });
        
        $("#searchItemNewButton").click(function(){
            $("#categoryMenuId").val("0");
            loadItemList2();		
        });
        
        function loadItemList2(){
            var locationId = $('#locationBillIdSource').val();
            $("#searchItemModal").val("");
            var cashCashierId = $("#cashCashierId").val();
            $('#openitemlist #LOCATION_ID').val(locationId);
            $('#openitemlist #LOCATION_ID').css('display','none');
            var typeText = $("#searchItemNew").val();
            var categoryMenuId = $("#categoryMenuId").val();
            var loadtype = "loaditemlist2";
            var command = "<%= Command.NONE%>";
            var dataSend = "command="+command+"&loadtype="+loadtype+"&typeText="+typeText+"&LOCATION_BILL_PLACE="+locationId+"&categoryMenuId="+categoryMenuId+"&CASH_CASHIER_ID="+cashCashierId;

            //>>> added by dewok 20190130
            var billMainId = $('#mainBillIdSource').val();
            dataSend += "&oidBillMain="+billMainId;
            //<<<

            $("#CONTENT_ITEM").html("Please wait...");
            $.ajax({
                type    : "POST",
                url	    : "<%= approot %>/TransactionCashierHandler",
                data    : dataSend,
                chace   : false,
                dataType : "json",
                success : function(data){
                }

            }).done(function(data){
                onDoneSearchItem(data);
            });
        }
        
        function addItemForm(formId,package){
            var locationId = $('#locationBillIdSource').val();
            //set location sesuai cash bill main
            $('#openitemlist #LOCATION_ID').val(locationId);
            //hidden combobox location
            $('#openitemlist #LOCATION_ID').css('display','none');
            //submit form
            $("#openitemlist").modal("show");
            $("#openitemlist #searchItemNew").val("");
            $('#openitemlist #searchitemlocation').trigger("click");
            var locationPlace = $("#locationBillIdSource").val();
            var cashCashierId = $("#cashCashierId").val();
            var dataSend = $(formId).serialize();
            dataSend = dataSend + "&LOCATION_BILL_PLACE="+locationPlace+"&CASH_CASHIER_ID="+cashCashierId;

            //>>> added by dewok 20190130
            var billMainId = $('#mainBillIdSource').val();
            dataSend += "&oidBillMain="+billMainId;
            //<<<

            $("#CONTENT_ITEM").html("Please wait...");
            $.ajax({
                type    : "POST",
                url	: "<%= approot %>/TransactionCashierHandler",
                data    : dataSend,
                chace   : false,
                dataType: "json",
                success : function(data){	
                }    
            }).done(function(data){
                $("#CONTENT_ITEM").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %> == 1){
                    setTimeout(function(){
                        $('#CONTENT_ITEM .firstFocus').trigger('click');
                    }, 50);
                }
                loadMenuClick(".mainMenu"); 
                var itemName = $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val();
                var itemOid = $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val();
                var itemPrice = $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val();
                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(itemOid);
                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(itemName);
                $("#CONTENT_ITEM_TITLE").html(itemName);
                $("#openitemlist #searchItemModal").val("");
                
                $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").click(function(){
                    var itemName = $(this).data("name");
                    var itemOid = $(this).data("oid");
                    var itemPrice = $(this).data("price");
                    var canBuy = $(this).data("canbuy");
                    var serial = $(this).data("useserial");
                    if (serial === true){
                        $('#groupSerial').css('display', 'block');
                        $("#groupSerial").html('<div class="input-group"><input type="text" name="SERIAL_CODE" class="form-control serialFrom" placeholder="Serial Code" id="serial" required="required"><div class="input-group-addon btn btn-primary" id="btnGenerateSerial" title="Generate Sequential Serial"><i class="fa fa-refresh"></i></div><div class="input-group-addon btn btn-danger btnDeleteAllSerial" title="Clear All Field"><i class="fa fa-undo"></i></div></div>');
                        serialFrom();
                        generateSerial();
                    } else {
                        $('#groupSerial').css('display', 'none');
                        $("#groupSerial").html("");
                    }
                    
                    if (canBuy === false){
                       $("#stockCheck").modal("show");
                        $("#CONTENT_STOCK_TITLE").html(itemName);
                        $("#modal-body-stock").html("<div class=''><p>Sorry, the stock was out/empty. You can't add this item.</p></div>");
                    } else if ( $( this ).hasClass( "specialOrder" ) ) {
                        var billMainId = $('#mainBillIdSource').val(); 
                        var url = ""+bases+"/TransactionCashierHandler";
                        var data = "loadtype=edititem2&command=<%=Command.NONE%>&oidBillMain="+billMainId+"&oidMaterial="+itemOid+"&materialName="+itemName+"&materialPrice="+itemPrice+"&qty=1";                             
                        ajaxTransactionHandler(url,data,"POST","#openauthorize #CONTENT_AUTHORIZE","editItem","","");
                    }else{
                        if (<%= disableQty%> == 0) {
                            var package = $("#isPackage").val();
                            $("#openadditem").modal("show");
                            if (package=="0"){
                                $("#parentPackage").val("0");
                            }
                            $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").val('');
                            $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                            $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(itemOid);
                            $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(itemName);
                            $("#CONTENT_ITEM_TITLE").html(itemName);
                        } else {
                            $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").val('1');
                            $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                            $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(itemOid);
                            $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(itemName);
                            $("#CONTENT_ITEM_TITLE").html(itemName);
                            $("#<%= FrmBillDetail.FRM_NAME_BILL_DETAIL %>_ADD_ITEM #btnsaveitem").trigger("click");
                        }
                    }
                });
                textual();
                loadDataPaging();	  
            });
        }
        
        var addItemPackage = function(elementId){
            $(elementId).click(function(){
                var parentId = $(this).data("parentid");
                $("#isPackage").val("1");
                $("#parentPackage").val(parentId);
                addItemForm(".additem","1");
            });
        };
        
        $(".additem").submit(function(){
            if ("<%= itemListView %>" === "0") {
                addItemForm(".additem","0");
            } else if ("<%= itemListView %>" === "1") {
                showItemListWithPicture();
            }
            return false;
        });
        
        function onDoneSearchItem(data){
            $("#CONTENT_ITEM").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");    
            var itemName = $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val();
            var itemOid = $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val();
            var itemPrice = $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val();
            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(itemOid);
            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(itemName);
            $("#CONTENT_ITEM_TITLE").html(itemName);
            mainMenuOutletClick(".mainMenuOutlet");
            loadMenuClick(".mainCategoryOutlet");
                
            $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").click(function(){    
                var itemName = $(this).data("name");
                var itemOid = $(this).data("oid"); 
                var itemPrice ="0";
                var transactionPackage = $("#transactionPackage").val();
                if (transactionPackage=="1"){
                    itemPrice="0";
                }else{
                    itemPrice = $(this).data("price");
                }
                var canBuy = $(this).data("canbuy");
                    var serial = $(this).data("useserial");
                    if (serial === true){
                        $('#groupSerial').css('display', 'block');
                        $("#groupSerial").html('<div class="input-group"><input type="text" name="SERIAL_CODE" class="form-control serialFrom" placeholder="Serial Code" id="serial" required="required"><div class="input-group-addon btn btn-primary" id="btnGenerateSerial" title="Generate Sequential Serial"><i class="fa fa-refresh"></i></div><div class="input-group-addon btn btn-danger btnDeleteAllSerial" title="Clear All Field"><i class="fa fa-undo"></i></div></div>');
                        serialFrom();
                        generateSerial();
                    } else {
                        $('#groupSerial').css('display', 'none');
                        $("#groupSerial").html("");
                    }
                if (canBuy === false){
                    $("#stockCheck").modal("show");
                    $("#CONTENT_STOCK_TITLE").html(itemName);
                    $("#modal-body-stock").html("<div class=''><p>Sorry, the stock was out/empty. You can't add this item.</p></div>");
                } else if ( $( this ).hasClass( "specialOrder" ) ) {
                    var billMainId = $('#mainBillIdSource').val();
                    var url = ""+bases+"/TransactionCashierHandler";
                    var data = "loadtype=edititem2&command=<%=Command.NONE%>&oidBillMain="+billMainId+"&oidMaterial="+itemOid+"&materialName="+itemName+"&materialPrice="+itemPrice+"&qty=1";                             
                    ajaxTransactionHandler(url,data,"POST","#openauthorize #CONTENT_AUTHORIZE","editItem","","");
                }else{
                    $("#openadditem").modal("show");
                    $("#isPackage").val("0");
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").val('');
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                    $("#scheduleId").val("0");
                    $("#customePackBillingId").val("0");
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(itemOid);
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(itemName);
                    $("#CONTENT_ITEM_TITLE").html(itemName);
                }
            });
            textual();
            loadDataPaging();
            loadDataPagingSearch();
        }
        
        $(".LOCATION_ID").change(function(){
            $("#openitemlist").modal("show");
            $("#CONTENT_ITEM").html("Please wait...");
            $.ajax({
                type    : "POST",
                url	: "<%= approot %>/TransactionCashierHandler",
                data    : $("#openitemlist .additem").serialize(),
                chace   : false,
                dataType: "json",
                success : function(data){
                    $("#CONTENT_ITEM").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                    if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %> == 1){
                        $("#openadditem").modal("show");
                        $("#isPackage").val("0");
                        $("#customePackBillingId").val("0");
                        $("#scheduleId").val("0");
                        $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").val('');
                    }
                }

            }).done(function(){
                var itemName = $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val();
                var itemOid = $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val();
                var itemPrice = $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val();
                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(itemOid);
                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(itemName);
                $("#CONTENT_ITEM_TITLE").html(itemName);
                $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").click(function(){
                    var itemName = $(this).data("name");
                    var itemOid = $(this).data("oid");
                    var serial = $(this).data("useserial");
                    if (serial === true){
                        $('#groupSerial').css('display', 'block');
                        $("#groupSerial").html('<div class="input-group"><input type="text" name="SERIAL_CODE" class="form-control serialFrom" placeholder="Serial Code" id="serial" required="required"><div class="input-group-addon btn btn-primary" id="btnGenerateSerial" title="Generate Sequential Serial"><i class="fa fa-refresh"></i></div><div class="input-group-addon btn btn-danger btnDeleteAllSerial" title="Clear All Field"><i class="fa fa-undo"></i></div></div>');
                        serialFrom();
                        generateSerial();
                    } else {
                        $('#groupSerial').css('display', 'none');
                        $("#groupSerial").html("");
                    }
                    var itemPrice = "0";
                    var transactionPackage= $("#transactionPackage").val();
                    if (transactionPackage=="1"){
                        itemPrice="0";
                    }else{
                        itemPrice= $(this).data("price");
                    }
                    $("#openadditem").modal("show");
                    $("#isPackage").val("0");
                    $("#customePackBillingId").val("0");
                    $("#scheduleId").val("0");
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").val('');
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(itemOid);
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(itemName);
                    $("#CONTENT_ITEM_TITLE").html(itemName);
                });
            });
            return false;
        });
        
        function submitItem(elementId){
            var parentPackage = $("#parentPackage").val();
            var dataSend = $(elementId).serialize();
            dataSend = dataSend + "&parentId="+parentPackage+""; 
            var billMainId = $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_MAIN_ID] %>_ADD_ITEM").val();
            $.ajax({
                type    : "POST",
                url	: "<%= approot %>/TransactionCashierHandler",
                data    : dataSend,
                chace   : false,
                dataType : "json",
                success : function(data){
                    if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %> == "SUCCESS"){
                        var isPack = $("#isPackage").val();
                        if (isPack=="1"){
                            $("#transactionPackage").val("1");
                            $("#searchBarcode").attr("disabled","disabled");
                            $("#searchItemFront").attr("disabled","disabled");
                        }
                        $("#loadPackageBill").trigger("click");
                        $("#btnsaveitem").removeAttr("disabled").html("<i class='fa fa-plus'></i> Add Item");
                        $("#openadditem").modal("hide");
                        $("#isPackage").val("0");
                        loadTransaction(billMainId,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                        $("#searchItemModal").val("");
                        $("#searchBarcode").val("");
                        $("#searchItemNew").val("");

                        if ($('#modalItemListImage').is(':visible')) {
                            getListBillItemGrid();
                            searchItemByCategory();
                        }
                    }
                }
            });
        }
        
        $("form#<%= FrmBillDetail.FRM_NAME_BILL_DETAIL %>_ADD_ITEM").submit(function(){
            $("#btnsaveitem").html("Please wait").attr({"disabled":true});
            var isPackage = $("#isPackage").val();
            var parentPackage= $("#parentPackage").val();
            var qtyInput = $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #FRM_FIELD_QTY").val();
            if (isPackage=="1"){ 
                if (parentPackage.length==0){
                    if (Number(qtyInput)<=Number(qtyHelp)){
                        submitItem("form#<%= FrmBillDetail.FRM_NAME_BILL_DETAIL %>_ADD_ITEM");
                    }else{
                        alert("Max qty is "+qtyHelp+"");
                    }
                }else{
                    submitItem("form#<%= FrmBillDetail.FRM_NAME_BILL_DETAIL %>_ADD_ITEM");
                }
            }else{
                submitItem("form#<%= FrmBillDetail.FRM_NAME_BILL_DETAIL %>_ADD_ITEM");
            }
            return false;
        });
        
        $("#searchallmemberbtn").click(function(){
            $("#opensearchmember").modal("show");
            $("#searchitem").val("");
            var classType = "99";
            if ("<%=enableDutyFree%>" === "1") {
                classType = "<%=PstContactClass.CONTACT_TYPE_DONOR%>";
            } else if ("<%=useProduction%>" === "1"){
                classType = "<%=PstContactClass.CONTACT_TYPE_MEMBER%>";
            }
            searchMember("","loadsearchmember",<%= Command.NONE %>,classType,"CONTENT_SEARCH_MEMBER","");
        });
        
        $("#searcInhouseGuest").click(function(){
            var classType = "#7";
            var searchtype = "guest";
            if(searchtype == "guest"){
                $("#btnGuest").attr({"disabled":true});
                $("#btnTravelagent").removeAttr("disabled");
            }else{
                $("#btnTravelagent").attr({"disabled":true});
                $("#btnGuest").removeAttr("disabled");
            }
            $("#opensearchmember").modal("show");
            $("#buttonInHouseGuest").show();
            searchMember("","loadsearchmember",<%= Command.NONE %>,classType,"CONTENT_SEARCH_MEMBER",searchtype);
        });
        
        $("#addnewmember").click(function(){
            var locationId = $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] %>").val();
            if(locationId !=0 && !isNaN(locationId)){
                if(<%=useForRaditya%> == "1") {
                    var zindexParent = $("#openneworder").css("z-index");
                    var zindexChild = parseInt(zindexParent) + 1;
                    $("#modal-newcustomer").modal(modalSetting2);
                    initializeGMap();
                    $("#modal-newcustomer").css({"z-index":zindexChild}).modal("show");
                } else {
                    $("#opennewmember").modal("show");
                    $("#opennewmember #<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_LOCATION_ID] %>").val(locationId);
                }
            }else{
                alert("Please select location");
            }
        });
        
        //FUNGSI UNTUK MENGECEK APAKAH OUTLET TERLETAK DI DEPARTEMENT FB ATAU TIDAK
        $("#neworder").click(function(){
            var defGuest = <%=defineGuest%>;
            var needSales = <%= cashierNeedSales%>;
            //apakah transaksi ini memerlukan guest atau tidak, 
            if (defGuest=="1"){
                //transaksi memerlukan define guest
                //clean up form
                $("#openneworder #employee").val('');
                $("#openneworder #<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>").val('1');
                $("#openneworder #FRM_FIELD_TELP_MOBILE").val('');
                $("#openneworder #oidReservationOrder").val("0");
                //tambahkan atribute required
                $("#openneworder #employee").attr("required","required");
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>").attr("required","required");
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID] %>").attr("required","required");
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID] %>").attr("required","required");
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_COUNTRY] %>").attr("required","required");
                var location = $('#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] %>').val();
                $("#transactionPackage").val("0");
                $("#openneworder").modal("show");
                <% if(showDateTime.equals("0")){ %>
                    $('.datePicker3').datetimepicker({
                        format : "YYYY/MM/DD" 
                    });
                <% }else{%>
                    $('.datePicker3').datetimepicker({
                        format : "YYYY/MM/DD HH:mm:ss" 
                    });
                <%}%>
                //trigger change 
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] %>").trigger('change');
            }else{
                //jika tidak perlu maka langsung create transaksi baru
                //apakah memerlukan sales atau tidak 
                //hilangkan atribut required 
                $("#openneworder #oidReservationOrder").val("0");
                $("#openneworder #employee").removeAttr("required");
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>").removeAttr("required");
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID] %>").removeAttr("required");
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID] %>").removeAttr("required");
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SHIPPING_COUNTRY] %>").removeAttr("required");
                //trigger change 
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] %>").trigger('change');                
                //submit form
                if (needSales=="1"){
                    $("#newOrderConfirmation").modal("hide");
                    $("#salesModal").modal("show");
                    return false;
                }else{
                    $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SALES_CODE] %>").val("<%= userlogin %>");
                    $("#openneworder #btnneworder").trigger('click');
                }
            }
            var oidOutlet = $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] %>").val();
            checkLocationDepartement(oidOutlet);
        });
        
        $("form#<%= FrmContactList.FRM_NAME_CONTACTLIST %>").submit(function(){
            var guestName = $("#<%= FrmContactList.FRM_NAME_CONTACTLIST %> #<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_NAME] %>").val();
            var phoneNumber = $("#<%= FrmContactList.FRM_NAME_CONTACTLIST %> #<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE] %>").val();

            $("#btnaddnewmember").attr({"disabled":"true"});
            $.ajax({
                type    : "POST",
                data    : $(this).serialize(),
                url	    : "<%= approot %>/TransactionCashierHandler",
                dataType : "json",
                cache   : false,
                success : function(data){
                    $("#btnaddnewmember").removeAttr("disabled");
                    if(!isNaN(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>)){
                        $("#opennewmember").modal("hide");
                        $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_DISPLAY_TOTAL] %>);
                    }
                }
            }).done(function(data){
                $("#openneworder #employee").val(guestName);
                $("#openneworder #<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE] %>").val(phoneNumber);
                $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] %>").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
            });
            return false;
        });

        $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID] %>").change(function(){
            var roomId = $(this).val();
            $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID] %>").html("<option value=''>Please wait...</option>");
            $.ajax({
                type    : "POST",
                data    : {
                    "loadtype":"checktable",
                    "command":"<%= Command.NONE %>",
                    "<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID] %>":roomId
                },
                url	: "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                cache   : false,
                success : function(data){
                    $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID] %>").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
                }
            });
        });
        
        $("#<%= FrmBillMain.FRM_NAME_BILL_MAIN %>_NEWORDER").submit(function(){
            $("#btnneworder").html("Please wait...").attr({"disabled":"true"});
            var oidReservation = $("#oidReservationOrder").val();
            $("#reservationIdSource").val(oidReservation);
            var tipePengajuan = $("#TIPE_PENGAJUAN").val();
            var guestName = $("#employee").val();
            $("#guestNameSource").val(guestName);
            $.ajax({
                type    : "POST",
                data    : $(this).serialize(),
                url	: "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                cache   : false,
                success : function(data){
                    $('#mainBillIdSource').val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
                    getLocationByCashBillMain(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
                    $("#btnneworder").removeAttr("disabled").html("<i class='fa fa-check'></i> Save Order");
                    if(!isNaN(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>)){
                        if ("<%=useProduction%>" === "1"){
                            var docType = $("#FRM_FIELD_TRANS_TYPE").val();
                            if (docType == 1){
                                $("#openneworder").modal("hide");
                                $("#frmPengajuanKredit #billId").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
                                $("#frmPengajuanKredit #CATEGORY_PENGAJUAN").val(tipePengajuan);
                                $( "#frmPengajuanKredit" ).submit();
                            } else {
                                $("#openneworder").modal("hide");
                                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_MAIN_ID] %>_ADD_ITEM").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
                                loadTransaction(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                                $(".itemsearch").removeAttr("readonly");
                                $(".itemsearchbutton").removeAttr("disabled");
                            }
                        } else {
                            $("#openneworder").modal("hide");
                            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_MAIN_ID] %>_ADD_ITEM").val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
                            loadTransaction(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                            $(".itemsearch").removeAttr("readonly");
                            $(".itemsearchbutton").removeAttr("disabled");
                        }
                    }
                }
            });
            return false;
        });
        
        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] %>").change(function(){
            var locationId = $(this).val();
            $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID] %>").html("<option value=''>Please wait...</option>");
            $.ajax({
                type    : "POST",
                data    : {
                    "loadtype":"checkroom",
                    "command":"<%= Command.NONE %>",
                    "<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] %>":locationId
                },
                url	: "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                cache   : false,
                success : function(data){
                    //cek apakah location ini menggunakan table, jika tidak maka pilihan table tidak usah ditampilkan
                    if (data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>==0){
                        //jika table tidak ditampilkan maka, combo box table akan disembunyikan, attribute required juga dihilangkan, pax juga hilang
                        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID] %>").css('display','none');
                        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID] %>").css('display','none');
                        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID] %>").removeAttr("required");
                        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID] %>").removeAttr("required");
                        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>").val('1');
                        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>").fadeOut();
                    }else{
                        //jika table ditampilkan maka combobox table akan ditampilkan, dan ditambahkan attribut required
                        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID] %>").css('display','block');
                        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID] %>").css('display','block');
                        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID] %>").attr("required","required");
                        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TABLE_ID] %>").attr("required","required");
                        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>").val('');
                        $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>").fadeIn();
                        $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID] %>").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>);
                    }
                    checkLocationDepartement(locationId);
                }
            });
        });
        
        //JQUERY UNTUK FINGER PRINT
        var base = "<%=baseURL%>";
        var interval =0;
        var interval2 =0;
        var interval3 =0;
        
        function ajaxFingerHandler(url,data,type,appendTo,another,optional,optional2){
            $.ajax({
                url : ""+url+"",data: ""+data+"",type : ""+type+"", async : false,cache: false,dataType:"json",
                success : function(data) {
                    if (another=="checkUser" || another=="checkUser2" || another=="checkUserPayment"){
                        $(''+appendTo+'').html(data.result);
                    }
                }, error : function(data){}
            }).done(function(data){
                if (another=="checkUser"){
                    fingerClick();  
                    getCode();  
                }else if(another=="checkUser2"){
                    fingerClick2();  
                    getCode2();
                }else if(another=="checkUserPayment"){
                    fingerClickPayment(); 
                }else if (another=="getCode"){
                    $('#password').val(data.result);
                }else if (another=="getCode2"){
                    $('#passwordSpv').val(data.result);
                }else if (another=="checkStatusUser"){
                    if (data.result==1){
                        clearInterval(interval);
                        clearInterval(interval2);
                        clearInterval(interval3);
                        var loginId = $('#spvFinger').val();
                        $('#spvName').val(loginId);
                        alert('Verification success...');
                        $('#<%= FrmAppUser.FRM_APP_USER %>').submit();
                        $('#verFInger').hide();
                    }
                }else if (another=="checkStatusUser2"){
                    if (data.result==1){
                        clearInterval(interval);
                        clearInterval(interval2);
                        clearInterval(interval3);
                        alert('Verification success...');
                        $('#closing').submit();
                    }
                }else if(another=="checkStatusUserPayment"){
                    if (data.result==1){
                        clearInterval(interval);
                        clearInterval(interval2);
                        clearInterval(interval3);
                        //update history
                        saveHistory();
                    }
                }else if (another=="saveHistory"){
                    if (data.result==1){$("#maxDisc").val(data.maxDisc);
                        alert('Verification Payment success...');
                        //tekan tombol payment konvensional
                        if(<%= useCoverNumber %> == 0){$('#openAuthorizePayment').modal('hide');
                            $('#btnPaySave').trigger('click');
                        }else{
                            $('#openAuthorizePayment').modal('hide');
                            coverNumber();
                        }
                    }                        
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
                    $(''+appendTo+'').html(html);
                    if (another=="ctListTool"){
                        $('#openCtCustomer').modal({
                            backdrop: 'static',
                            keyboard: false
                        });
                    }else if (another=="getLocationBill"){
                        $('#locationBillIdSource').val(html);
                    }else if (another=="generateSerial"){
                        var error = data.FRM_FIELD_NUMBER_OF_ERROR;
                        if (error > 0){
                            alert(data.FRM_FIELD_SHORT_MESSAGE);
                            $("serial").focus();
                            serialFrom();
                            generateSerial();
                        }
                    }else if (another=="checkDept"){
                        if (html=="1"){
                           $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>").val('');
                           $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>").fadeIn();
                        }else{
                           $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>").val('1');
                           $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>").fadeOut(); 
                        }
                    }else if (another=="loadListReprintUp"){
                        $('#openbillreprint').modal('show'); 
                        <% if(showDateTime.equals("0")){ %>
                            $('.datePicker3').datetimepicker({
                                format : "YYYY/MM/DD" 
                            });
                        <% }else{%>
                            $('.datePicker3').datetimepicker({
                                format : "YYYY/MM/DD HH:mm:ss" 
                            });
                        <%}%>
                        $('#btnPaidSelect').addClass('active');
                        $('#btnReturnedSelect').removeClass('active');
                        $('#btnFOCSelect').removeClass('active');
                    }else if (another=="loadListJoinUp"){
                        if (optional=="1"){
                           $('#openbilljoin').modal('show'); 
                        }
                        $(".datePicker4").datepicker({
                            format: 'yyyy-mm-dd',
                        }).on("changeDate", function (ev) {
                            $(this).datepicker('hide');
                        });
                        $('#btnJoinSave').hide();
                        $('#btnJoinBack').hide();
                    }else if (another=="editItem"){
                        $('#openauthorize #AUTHORIZE_TITLE').html('EDIT ITEM');
                        $('#openauthorize #APP_USER').hide();
                        $('#openauthorize').css('overflow-y','auto');
                        $("#cancelButton").hide();
                        $('#openauthorize').modal('show');
                        $('#openadditem').modal('hide');
                    }else if (another=="loadListJoinDetail"){
                        $('#openbilljoin #JOINBILL_UP').html('');
                        $('#btnJoinSave').show();
                        $('#btnJoinBack').show();   
                    }else if (another=="loadListMultiPayment"){
                        if (optional==="true"){
                            $("#multiPayment").modal('show');
                        }else{
                            $('#remainMulti').html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_DISPLAY_TOTAL] %>);
                            $('#remainMultiPayment').val(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE] %>); 
                        } 
                    }else if (another=="payTypes2"){
                        $(".datePicker").datepicker({
                            format: 'yyyy-mm-dd',
                        }).on("changeDate", function (ev) {
                            $(this).datepicker('hide');
                        });
                    }else if (another=="saveTransaction"){
                        $("#btnfrom").val("1");
                        $("#btnSaveMultiPaymentX").removeAttr("disabled").attr({"value":"Pay"});
                        $("#CONTENT_PAY").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>).fadeIn("medium");
                        $("#CONTENT_PRINT").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                        $("#multiPayment").modal("hide");
                        window.print();
                        alert("Print Success");
                        window.location.reload(true);
                        $("#openpay").modal("hide");
                    }  
                },
                error : function(data){}
            }).done(function(data){
                if (another=="ctListTool"){
                    loadCtListCustomer();
                    searchCtKeyUp();
                    newCtCustomer();
                }else if (another=="loadItemListPageSearch"){
                    onDoneSearchItem(data);
                }else if (another=="ctList"){
                    selectCtCustomer();
                }else if (another=="addCtList"){
                    var name = $("#<%= FrmContactList.FRM_NAME_CONTACTLIST %>2 #<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_NAME] %>").val();
                    $('#searchCtCustomer').val(name);
                    $('#addCtCustomer').modal('hide');
                    loadCtListCustomer();
                }else if (another=="loadListReprintUp"){
                    loadListReprint();
                    btnSearchReprintBill();
                    dataSearchReprint();                       
                    selectReprintType();
                    $('#FRM_FIELD_LOCATION_ID_REPRINT').change(function(){
                        loadListReprint();
                    });
                    $('#dataSearchReprint').keydown(function(e){
                        if (e.keyCode==40){ 
                            $('#openbillreprint .firstFocus').focus();
                        }else if (e.keyCode==38){
                            $('#openbillreprint #orSearch1').focus();
                        }
                    });
                    $('#orSearch1').keydown(function(e){                           
                        if (e.keyCode==13){ 
                            $('#openbillreprint #orSearch2').focus();
                        }
                    });
                    $('#orSearch2').keydown(function(e){                           
                        if (e.keyCode==13){ 
                            $('#openbillreprint #dataSearchReprint').focus();
                        }
                    });
                }else if (another=="loadListReprint"){
                    reprintBillSelect();
                    pageUniv (".pagingRe","#totalPagingRe","#paggingPlaceRe","#nextPaggingRe","#btnSearchReprintBill");
                    textual4();
                }else if (another=="reprintBill"){
                    $('#CONTENT_PRINT_PREVIEW').html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                    $('#openprintpreview').modal('show');
                    window.print();
                }else if (another=="reprintBillFoc"){
                    $('#CONTENT_PRINT_PREVIEW').html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                    $('#openprintpreview').modal('show');
                    window.print();
                }else if (another=="reprintCreditPayment"){
                    $('#CONTENT_PRINT_PREVIEW_PAYMENT').html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                    $('#openprintpreviewpayment').modal('show');
                    window.print();
                }else if (another=="reprintBillOption"){
                    $('#reprintConfirmation').modal('hide');
                    $('#CONTENT_PRINT_PREVIEW').html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                    $('#openprintpreview').modal('show');
                    window.print();
                }else if (another=="editItem"){
                    innerFunction();
                    setTimeout(function(){
                        $('#openauthorize #materialNames').focus();
                    }, 200);

                }else if (another=="authPass"){
                    var result = data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>;
                    $("#maxDisc").val(data.MAX_DISC);
                    if (result=="0"){
                        if(<%= useCoverNumber %> == 0 || (<%= useCoverNumber %> == 1 && data.FRM_FIELD_SHORT_MESSAGE.length > 0)){$('#btnPaySave').trigger('click');
                            $('#openAuthorizePaymentNoFinger').modal('hide');
                        }else{$('#openAuthorizePaymentNoFinger').modal('hide');
                            coverNumber();
                        }
                    }else{
                        $('#openAuthorizePaymentNoFinger #spvPaymentPass').parent().append("<div style='margin-top:5px;' class='alert alert-danger'>Username and Password is not valid..</div>");
                        setTimeout(function(){
                            $('#spvPayment').focus();
                        }, 300);
                    }
                }else if (another=="loadListJoinUp"){
                    loadListJoin();
                    btnSearchJoinBill();
                    dataSearchJoin();
                }else if (another=="loadListJoin"){
                    jointBillSelect();
                    textual9();
                    setTimeout(function(){
                        $('#openbilljoin #dataSearchJoin').focus();
                    }, 200);
                }else if (another=="loadListJoinDetail"){
                    joinAll();
                    textual10();
                    setTimeout(function(){
                        $('#openbilljoin .firstFocus').focus();
                    }, 200);
                }else if (another=="saveJoin"){
                    $("#btnJoinSave").html("<i class='fa fa-save'></i> Save").removeAttr('disabled');
                    if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>==""){
                        var destination = $('#mainBillIdSource').val();
                        loadTransaction(destination,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                        $('#openbilljoin').modal('hide'); 
                    }else{
                        alert('Sorry there are some problem...');
                    }
                }else if (another=="checkPrintStatus"){
                    if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>!=0){
                        if (cashierSupportProduction=="1"){
                            $('#btnConfirm').show();
                        }
                    }else{
                        $('#btnConfirm').hide();
                    }
                }else if (another=="confirmPrintStatus"){
                    $('#btnConfirm').html("<i class='fa fa-check'></i> Confirm Order").removeAttr('disabled');
                    $('#btnConfirm').hide();
                    var oid = $('#mainBillIdSource').val();
                    loadTransaction(oid,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                }else if (another=="loadListMultiPayment"){
                    btnAddMultiPayment();
                    deleteMultiPayment();
                    var remainPayment = $("#remainMultiPayment").val();
                    var countMultiPayment = $("#countMultiPayment").val();
                    if (remainPayment == 0 && countMultiPayment > 0){
                        $("#btnSaveMultiPaymentX").show();
                        if ("<%= btnPrintPayment %>" !== "0") {
                            $("#btnPrintSeparateMulti").show();
                        }
                        $("#btnAddMultiPayment").hide();
                    }else{
                        $("#btnSaveMultiPaymentX").hide();
                        $("#btnPrintSeparateMulti").hide();
                        $("#btnAddMultiPayment").show();
                    }
                }else if (another=="payTypes"){
                    payTypes2();
                }else if (another=="payTypes2"){
                    calcMoney();
                    searchVoucher("#searchVoucher2", "#MASTER_FRM_FIELD_PAY_TYPE_MULTI");
                }else if (another=="saveTransaction"){
                    $('#mainBillIdSource').val(0);
                    window.location.reload();
                }else if (another=="loadItemListPage"){
                    $("#CONTENT_ITEM").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML] %>).fadeIn("medium");
                    if(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC] %> == 1){
                        setTimeout(function(){
                            $('#CONTENT_ITEM .firstFocus').trigger('click');
                        }, 50);
                    }
                    var itemName = $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val();
                    var itemOid = $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val();
                    var itemPrice = $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val();
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(itemOid);
                    $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(itemName);
                    $("#CONTENT_ITEM_TITLE").html(itemName);
                    $(".<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").click(function(){
                        var itemName = $(this).data("name");
                        var itemOid = $(this).data("oid");
                        var itemPrice = $(this).data("price");
                        var serial = $(this).data("useserial");
                        if (serial === true){
                            $('#groupSerial').css('display', 'block');
                            $("#groupSerial").html('<div class="input-group"><input type="text" name="SERIAL_CODE" class="form-control serialFrom" placeholder="Serial Code" id="serial" required="required"><div class="input-group-addon btn btn-primary" id="btnGenerateSerial" title="Generate Sequential Serial"><i class="fa fa-refresh"></i></div><div class="input-group-addon btn btn-danger btnDeleteAllSerial" title="Clear All Field"><i class="fa fa-undo"></i></div></div>');
                            serialFrom();
                            generateSerial();
                        } else {
                            $('#groupSerial').css('display', 'none');
                            $("#groupSerial").html("");
                        }
                        if ( $( this ).hasClass( "specialOrder" ) ) {
                            var billMainId = $('#mainBillIdSource').val(); 
                            var url = ""+bases+"/TransactionCashierHandler";
                            var data = "loadtype=edititem2&command=<%=Command.NONE%>&oidBillMain="+billMainId+"&oidMaterial="+itemOid+"&materialName="+itemName+"&materialPrice="+itemPrice+"&qty=1";                             
                            ajaxTransactionHandler(url,data,"POST","#openauthorize #CONTENT_AUTHORIZE","editItem","","");
                        }else{
                            $("#openadditem").modal("show");
                            $("#isPackage").val("0");
                            $("#customePackBillingId").val("0");
                            $("#scheduleId").val("0");
                            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").val('');
                            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                            $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(itemOid);
                            $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(itemName);
                            $("#CONTENT_ITEM_TITLE").html(itemName);
                        }
                    });
                    textual();
                    loadDataPaging();
                }else if (another=="getguestdetail"){
                    var tempData = data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>;
                    var datas = tempData.split("-");
                    $("#helpMessageCustomerName").val(datas[0]);
                    $("#helpMessageCustomerTelp").val(datas[1]); 
                }else if (another=="confirmorder"){
                    var oid = $('#mainBillIdSource').val();
                    loadTransaction(oid,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                }
            });
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
                var data = $('.addItem').serialize();
                var search = $('#searchItemModal').val();
                data = data + "&showPage="+showPage+"&search="+search+"";
                var locationPlace = $("#locationBillIdSource").val();
                data = data + "&LOCATION_BILL_PLACE="+locationPlace+"";
                var oidBillMain = $("#mainBillIdSource").val();
                data = data + "&oidBillMain="+oidBillMain+"";
                var url = ""+base+"/TransactionCashierHandler";                
                ajaxTransactionHandler(url,data,"POST","#CONTENT_ITEM","loadItemListPage","","");
            });
        }
        
        function loadDataPagingSearch(){
            $('.pagingsSearch').click(function(){
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
                var locationId = $('#locationBillIdSource').val();
                $("#searchItemModal").val("");
                $("#searchBarcode").val("");
                $('#openitemlist #LOCATION_ID').val(locationId);
                $('#openitemlist #LOCATION_ID').css('display','none');
                var typeText = $("#searchItemNew").val();
                var loadtype = "loaditemlist2";
                var categoryMenuId = $("#categoryMenuId").val();
                var command = "<%= Command.NONE%>";
                var data = "command="+command+"&loadtype="+loadtype+"&typeText="+typeText+"";
                var search = $('#searchItemNew').val();
                data = data + "&showPage="+showPage+"&search="+search+"&categoryMenuId="+categoryMenuId+"";
                var locationPlace = $("#locationBillIdSource").val();
                data = data + "&LOCATION_BILL_PLACE="+locationPlace+"";
                var url = ""+base+"/TransactionCashierHandler";                
                ajaxTransactionHandler(url,data,"POST","#CONTENT_ITEM","loadItemListPageSearch","","");
            });
        }
        
        function loadDataPagingEx(type){
            var showPage=0;                   
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
            var data = $('.addItem').serialize();
            var search = $('#searchItemModal').val();
            data = data + "&showPage="+showPage+"&search="+search+"";
            var url = ""+base+"/TransactionCashierHandler";                
            ajaxTransactionHandler(url,data,"POST","#CONTENT_ITEM","loadItemListPage","","");   
        }
        
        function checkUser(){
            var url = ""+base+"/FingerHandler";
            var loginId = $('#spvFinger').val();
            var data="command=<%=Command.ASK%>&login="+loginId+"&base="+base+"&group=1&type=1";
            ajaxFingerHandler(url,data,"POST","#dynamicPlace","checkUser","","");
        }
        
        function checkUser2(){
            var url = ""+base+"/FingerHandler";
            var loginId = $('#supervisorName').val();
            var data="command=<%=Command.ASK%>&login="+loginId+"&base="+base+"&group=1&type=2";
            ajaxFingerHandler(url,data,"POST","#dynamicPlace2","checkUser2","","");
        }
        
        function checkUserPayment(){
            var url = ""+base+"/FingerHandler";
            var loginId = $('#spvFingerPayment').val();
            var data="command=<%=Command.ASK%>&login="+loginId+"&base="+base+"&group=0&type=1";
            ajaxFingerHandler(url,data,"POST","#dynamicPlacePayment","checkUserPayment","","");
        }
        
        function checkStatusUser(userId){
            var url = ""+base+"/FingerHandler";
            var data="command=<%=Command.SEARCH%>&loginId="+userId+"";
            ajaxFingerHandler(url,data,"POST","","checkStatusUser","","");
        }
        
        function checkStatusUser2(userId){
            var url = ""+base+"/FingerHandler";
            var data="command=<%=Command.SEARCH%>&loginId="+userId+"";
            ajaxFingerHandler(url,data,"POST","","checkStatusUser2","","");
        }
        
        function checkStatusUserPayment(userId){
            var url = ""+base+"/FingerHandler";
            var data="command=<%=Command.SEARCH%>&loginId="+userId+"";
            ajaxFingerHandler(url,data,"POST","","checkStatusUserPayment","","");
        }
        
        function getCode(){
            var url = ""+base+"/FingerHandler";
            var loginId = $('#spvFinger').val();
            var data="command=<%=Command.DETAIL%>&login="+loginId+"";
            ajaxFingerHandler(url,data,"POST","","getCode","","");
        }
        
        function getCode2(){
            var url = ""+base+"/FingerHandler";
            var loginId = $('#supervisorName').val();
            var data="command=<%=Command.DETAIL%>&login="+loginId+"";
            ajaxFingerHandler(url,data,"POST","","getCode2","","");
        }
        
        function loadListReprintUp(){
            var url = ""+base+"/TransactionCashierHandler";
            var data = "loadtype=loadListReprintUp&command=<%=Command.NONE%>&location=<%=location.getOID()%>";
            $('#REPRINT_UP').html("Please wait...");
            ajaxTransactionHandler(url,data,"POST","#openbillreprint #REPRINT_UP","loadListReprintUp","","");
        }
        
        function loadListReprint(){
            var url = ""+base+"/TransactionCashierHandler";
            var typeText = $('#dataSearchReprint').val();
            var typeReprint = $('#reprintSelect').val();
            var date1 = $('#orSearch1').val();
            var date2 = $('#orSearch2').val();
            var tableNameRe = $("#tableNameRe").val();
            var location = $('#<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]%>_REPRINT').val();
            var nextPage = $("#nextPaggingRe").val();
            var data ="loadtype=loadListReprint&command=<%=Command.NONE%>&typeText="+typeText+"&date1="+date1+"&date2="+date2+"&typeReprint="+typeReprint+"&location="+location+"&table="+tableNameRe+"&showPage="+nextPage+"";
            $('#REPRINT_LOW').html("Please wait...");
            ajaxTransactionHandler(url,data,"POST","#openbillreprint #REPRINT_LOW","loadListReprint","","");
        }
        
        function loadJoinUp(){
            var oidBillA = $('#mainBillIdSource').val();
            var url = ""+base+"/TransactionCashierHandler";
            var data = "loadtype=loadListJoinUp&command=<%=Command.NONE%>&<%=FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]%>="+oidBillA+"";
            ajaxTransactionHandler(url,data,"POST","#openbilljoin #JOINBILL_UP","loadListJoinUp","1","");
        }
        
        function loadListJoin(){
            var url = ""+base+"/TransactionCashierHandler";
            var typeText = $('#dataSearchJoin').val();
            var oidBillA = $('#oidBillA').val();
            var date1 = $('#ojSearch1').val();
            var date2 = $('#ojSearch2').val();
            var data ="loadtype=loadListJoin&command=<%=Command.NONE%>&typeText="+typeText+"&date1="+date1+"&date2="+date2+"&oidBillA="+oidBillA+"";
            ajaxTransactionHandler(url,data,"POST","#openbilljoin #JOINBILL_LOW","loadListJoin","","");
        }
        
        function jointBillSelect(){
            $('.jointBillSelect').click(function(){
                var oid= $(this).data('oid');
                var oidBillA = $('#oidBillA').val();
                var url = ""+base+"/TransactionCashierHandler";
                var data = "loadtype=loadListJoinDetail&command=<%=Command.NONE%>&<%=FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]%>="+oid+"&oidBillA="+oidBillA+"";
                ajaxTransactionHandler(url,data,"POST","#openbilljoin #JOINBILL_LOW","loadListJoinDetail","","");
            });
        }
        
        function btnJoinBack(){
            $('#btnJoinBack').click(function(){
                var oidBillA = $('#mainBillIdSource').val();
                var url = ""+base+"/TransactionCashierHandler";
                var data = "loadtype=loadListJoinUp&command=<%=Command.NONE%>&<%=FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]%>="+oidBillA+"";
                ajaxTransactionHandler(url,data,"POST","#openbilljoin #JOINBILL_UP","loadListJoinUp","0","");
            });
        }
        
        function joinAll(){
            $('#joinAll').change(function() {
                if(this.checked) {
                    $('.qtyJoinHelp').each(function(i){
                        var id = this.id;
                        var nilai = $('#'+id+'').val();
                        var ids = id.split("-");
                        var no = ids[1];
                        $('#qtyJoinTemp-'+no+'').val(nilai);

                    });
                }else{
                    $('.qtyjointemp').val('0');
                }    
            });
        }
        
        function validateSaveJoin(){
            var error = false;
            $('.qtyjointemp').parent().find('.alert').remove();
            $('.qtyjointemp').each(function(i){
                var id = this.id;
                var ids = id.split("-");
                var no = ids[1];
                var nilaiInput = $(this).val();
                var nilaiCurent =  $('#qtyJoin-'+no+'').val();
                if (nilaiInput>nilaiCurent ){
                    error = true;
                    $(this).parent().append("<div style='width:100px; margin-top:5px; margin-bottom: 1px;font-size: 12px;' class='alert alert-danger' role='alert'>Maximum qty = "+nilaiCurent+"</div>");
                }else if (nilaiInput<0){
                    error = true;
                    $(this).parent().append("<div style='width:100px; margin-top:5px; margin-bottom: 1px;font-size: 12px;' class='alert alert-danger' role='alert'>Invalid value</div>");                   
                }
            });
            return error;
        }

        function btnJoinSave(){
            $('#btnJoinSave').click(function(){
                if (validateSaveJoin()==false){
                    var valueTotal ="";
                    try {
                        var size = $('.qtyjointemp').size(); 
                        if (size>0){
                            $('.qtyjointemp').each(function(i){                           
                                var nilaiInput = $(this).val();
                                var billDetilOid = $(this).data('billdetiloid');
                                if (i==0){
                                    valueTotal = valueTotal + ""+billDetilOid+":"+nilaiInput+"" ;
                                }else{
                                    valueTotal = valueTotal + "-"+billDetilOid+":"+nilaiInput+"" ;
                                }
                            });
                            var oidBillDestination = $('#openbilljoin #oidBillA').val();
                            var oidBillSource = $('#openbilljoin #FRM_FIELD_CASH_BILL_MAIN_ID').val();
                            var url = ""+base+"/TransactionCashierHandler";
                            var data = "loadtype=saveJoin&command=<%=Command.SAVE%>&oidBillDestination="+oidBillDestination+"&oidBillSource="+oidBillSource+"&valueTotal="+valueTotal+"";
                            $(this).html('Please wait...').attr('disabled','disabled');
                            ajaxTransactionHandler(url,data,"POST","","saveJoin","0","");
                        }else{
                            alert('No data to join...');
                        }
                    }catch(ex){
                        alert('err');
                    }   
                }
            }); 
        }
        
        function reprintBillSelect(){
            $('.reprintBillSelect').click(function(){
                var printOutType = $('#printOutType').val();
                var printPapertType = $('#printPapertType').val();
                var confirmText = "Are you sure you want to re-print this bill?";
                var oid = $(this).data('oid');
                var oidMember = $(this).data('oid');
                var appRoot = "<%= approot%>";
                if (printOutType=="1" && printPapertType=="1"){
                    //Show Modal
                    $('#reprintConfirmation #oidBilllx').val(oid);
                    $('#reprintConfirmation #oidMemberx').val(oidMember);
                    $('#reprintConfirmation #appRootX').val(appRoot);
                    $('#reprintConfirmation').modal('show');
                }else{
                    if(confirm(confirmText)) {
                        var url = ""+base+"/TransactionCashierHandler"; 
                        var data ="loadtype=reprint&command=<%=Command.NONE%>&member="+oidMember+"&appRoot="+appRoot+"&oidMain="+oid+"&full=0";
                        ajaxTransactionHandler(url,data,"POST","#CONTENT_PRINT","reprintBill","","");
                    }
                    return false; 
                } 
            });
            
            $('.reprint_guideprice').click(function(){
                var printOutType = $('#printOutType').val();
                var printPapertType = $('#printPapertType').val();
                var confirmText = "Are you sure you want to re-print this bill?";
                var oid = $(this).data('oid');
                var oidMember = $(this).data('oid');
                var appRoot = "<%= approot%>";
                if (printOutType=="1" && printPapertType=="1"){
                    //Show Modal
                    $('#reprintConfirmation #oidBilllx').val(oid);
                    $('#reprintConfirmation #oidMemberx').val(oidMember);
                    $('#reprintConfirmation #appRootX').val(appRoot);
                    $('#reprintConfirmation').modal('show');
                }else{
                    if(confirm(confirmText)) {
                        var url = ""+base+"/TransactionCashierHandler"; 
                        var data ="loadtype=reprintGuidePrice&command=<%=Command.NONE%>&member="+oidMember+"&appRoot="+appRoot+"&oidMain="+oid+"&full=0";
                        ajaxTransactionHandler(url,data,"POST","#CONTENT_PRINT","reprintBill","","");
                    }
                    return false; 
                } 
            });
            
            $('.print_guidecommission').click(function(){
                var oid = $(this).data('oid');
                command = "<%= Command.NONE%>";
                var dataFor = "printGuideCommission";
                dataSend = {
                    "FRM_FIELD_DATA_FOR": dataFor,
                    "command": command,
                    "BILL_MAIN_ID": oid
                };
                onSuccess = function(data){}; 
                onDone = function(data){
                    $('#CONTENT_PRINT_PREVIEW').html(data.FRM_FIELD_HTML);
                    $('#CONTENT_PRINT').html(data.FRM_FIELD_HTML);
                    $('#openprintpreview').modal('show');
                    window.print();
                };
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "TransactionCashierHandlerNew", "", true, "json");
            });
            
            $('.reprintBillFocSelect').click(function(){
                var oid = $(this).data('oid');
                var appRoot = "<%= approot%>";
                var printOutType = $('#printOutType').val();
                var printPapertType = $('#printPapertType').val();
                var confirmText = "Are you sure you want to re-print this bill?";
                if (printOutType=="1" && printPapertType=="1"){
                    //Show Modal
                    $('#reprintConfirmation #oidBilllx').val(oid);
                    $('#reprintConfirmation #appRootX').val(appRoot);
                    $('#reprintConfirmation').modal('show');
                }else{
                    if(confirm(confirmText)) {
                        var url = ""+base+"/TransactionCashierHandler"; 
                        var data ="loadtype=reprintBillFoc&command=<%=Command.NONE%>&appRoot="+appRoot+"&oidMain="+oid+"&full=0";
                        ajaxTransactionHandler(url,data,"POST","#CONTENT_PRINT","reprintBillFoc","","");
                    }
                return false; 
                }
            });
            
            $('.reprintPayment').click(function(){
                var oid = $(this).data('oid');
                var appRoot = "<%= approot%>";
                var confirmText = "Are you sure you want to re-print this payment?";
                    if(confirm(confirmText)) {
                        var url = ""+base+"/TransactionCashierHandler"; 
                        var data ="loadtype=reprintCreditPayment&command=<%=Command.NONE%>&appRoot="+appRoot+"&oidTransaksi="+oid+"&full=0";
                        ajaxTransactionHandler(url,data,"POST","#CONTENT_PRINT","reprintCreditPayment","","");
                    }
                return false; 
            });
        }
        
        function btnPrintOption(){
            $('.printOption').click(function(){
                var type = $(this).data('type');
                var oid = $('#reprintConfirmation #oidBilllx').val();
                var oidMember = $('#reprintConfirmation #oidMemberx').val();
                var appRoot = $('#reprintConfirmation #appRootX').val();
                var url = ""+base+"/TransactionCashierHandler"; 
                var data ="loadtype=reprint&command=<%=Command.NONE%>&member="+oidMember+"&appRoot="+appRoot+"&oidMain="+oid+"&full="+type+"";
                ajaxTransactionHandler(url,data,"POST","#CONTENT_PRINT","reprintBillOption","","");  
            });
        }
        
        function btnSearchReprintBill(){
            $('#btnSearchReprintBill').click(function(){
                loadListReprint();
            });
        }
        
        function btnSearchJoinBill(){
            $('#btnSearchJoinBill').click(function(){
                loadListJoin();
            });
        }
        
        function dataSearchReprint(){
            $('#dataSearchReprint').keyup(function(){
                loadListReprint();
            });
        }
        
        function selectReprintType(){
            $('#btnPaidSelect').click(function(){
                $(this).addClass('active');
                $('#btnReturnedSelect').removeClass('active');
                $('#btnFOCSelect').removeClass('active');
                $('#btnCreditSelect').removeClass('active');
                $('#reprintSelect').val(0);
                loadListReprint();
            });
            $('#btnReturnedSelect').click(function(){
                $(this).addClass('active');
                $('#btnPaidSelect').removeClass('active');
                $('#btnFOCSelect').removeClass('active');
                $('#btnCreditSelect').removeClass('active');
                $('#reprintSelect').val(1);
                loadListReprint();
            });               
            $('#btnFOCSelect').click(function(){
                $(this).addClass('active');
                $('#btnPaidSelect').removeClass('active');
                $('#btnReturnedSelect').removeClass('active');
                $('#btnCreditSelect').removeClass('active');
                $('#reprintSelect').val(2);
                loadListReprint();
            });       
            $('#btnCreditSelect').click(function(){
                $(this).addClass('active');
                $('#btnPaidSelect').removeClass('active');
                $('#btnReturnedSelect').removeClass('active');
                $('#btnFOCSelect').removeClass('active');
                $('#reprintSelect').val(3);
                loadListReprint();
            });
        }
        
        function fingerClick(){
            $('.loginFinger').click(function(){
                var loginId= $('#spvFinger').val();
                clearInterval(interval);
                interval =  setInterval(function() {
                    checkStatusUser(loginId);
                },5000);
            });
        }
        
        function fingerClick2(){
            $('.loginFinger2').click(function(){
                var loginId= $('#supervisorName').val();
                clearInterval(interval2);
                interval2 =  setInterval(function() {
                    checkStatusUser2(loginId);
                },5000);
            });
        }
        
        function fingerClickPayment(){
            $('.loginFinger').click(function(){
                var loginId= $('#spvFingerPayment').val();
                clearInterval(interval3);
                interval3 =  setInterval(function() { 
                    checkStatusUserPayment(loginId);
                },5000);
            });
        }
        
        function saveHistory(){
            var oIdDoc = $('#<%=FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]%>').val();
            var loginId = $('#spvFingerPayment').val();
            var url = ""+base+"/FingerHandler";
            var data="command=<%=Command.ASSIGN%>&login="+loginId+"&oidDoc="+oIdDoc+"&base="+base+"cashier/cashier-transaction.jsp";
            ajaxFingerHandler(url,data,"POST","","saveHistory","","");    
        }
        
        function loadCtCustomer(){
            var url = ""+base+"/TransactionCashierHandler";
            var data = "loadtype=ctListTool&command=<%=Command.NONE%>";
            ajaxTransactionHandler(url,data,"POST",".ctBodyUp","ctListTool","","");
        }
        
        function loadCtListCustomer(){
            var url = ""+base+"/TransactionCashierHandler";
            var typeText = $('#searchCtCustomer').val();
            var member = $("#member").val();
            var data = "loadtype=ctList&command=<%=Command.NONE%>&typeText="+typeText+"&cashier="+member+"";
            ajaxTransactionHandler(url,data,"POST",".ctBodyDown","ctList","","");
        }
        
        function searchCtKeyUp(){
            $('#searchCtCustomer').keyup(function(){
                loadCtListCustomer();
            });
        }
        
        function searchCtBtn(){
            $('#btnSearchCtCustomer').click(function(){
                loadCtListCustomer();
            });
        }
        
        function selectCtCustomer(){
            $('.selectCtCustomer').click(function(){
                var oId = $(this).data('oid');
                var personName = $(this).data('personname');
                var telpMobile = $(this).data('telp');
                var compName = $(this).data('company');
                $('#ctIdCustomer').val(oId);
                $('#ctCustomerName').val(personName);
                $('#ctMobilePhone').val(telpMobile);
                $('#ctCompany').val(compName);
                $('#openCtCustomer').modal('hide');
            });
        }
        
        function newCtCustomer(){
            var locationId = $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] %>").val();
            $("#addCtCustomer #<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_LOCATION_ID] %>").val(locationId);
            $('#btnAddCtCustomer').click(function(){
                //clear form new customer 
                $("#<%= FrmContactList.FRM_NAME_CONTACTLIST %>2 #<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_NAME] %>").val('');
                $("#<%= FrmContactList.FRM_NAME_CONTACTLIST %>2 #<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_COMP_NAME] %>").val('');
                $("#<%= FrmContactList.FRM_NAME_CONTACTLIST %>2 #<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE] %>").val('');

                $('#addCtCustomer').modal({
                    backdrop: 'static',
                    keyboard: false
                });
            }); 
        }
        
        function viewDetailEditor(){
            $('#btnDetailEditor').click(function(){
                var oidBillMain = $('#FRM_NAME_BILL_DETAIL_ADD_ITEM #FRM_FIELD_CASH_BILL_MAIN_ID_ADD_ITEM').val();
                var oidMaterial = $('#FRM_NAME_BILL_DETAIL_ADD_ITEM #FRM_FIELD_MATERIAL_ID').val();
                var materialName = $('#FRM_NAME_BILL_DETAIL_ADD_ITEM #FRM_FIELD_ITEM_NAME').val();
                var materialPrice = $('#FRM_NAME_BILL_DETAIL_ADD_ITEM #FRM_FIELD_ITEM_PRICE').val();
                var qty = $('#FRM_NAME_BILL_DETAIL_ADD_ITEM #FRM_FIELD_QTY').val();
                var url = ""+base+"/TransactionCashierHandler";
                var typeText = $('#searchCtCustomer').val();
                var member = $("#member").val();                   
                var data = "loadtype=edititem2&command=<%=Command.NONE%>&oidBillMain="+oidBillMain+"&oidMaterial="+oidMaterial+"&materialName="+materialName+"&materialPrice="+materialPrice+"&qty="+qty+"";                    
                ajaxTransactionHandler(url,data,"POST","#openauthorize #CONTENT_AUTHORIZE","editItem","","");
            });
        }
        
        function generateSerial(){
            $('#btnGenerateSerial').click(function(){
                var locationId = $("#locationBillIdSource").val();
                var qty = $("#FRM_FIELD_QTY").val();
                var serial = $("#serial").val();
                var url = ""+base+"/TransactionCashierHandler";
                var data = "loadtype=generateSerial&command=<%=Command.NONE%>&oidLocation="+locationId+"&currentSerial="+serial+"&qty="+qty;                    
                ajaxTransactionHandler(url,data,"POST","#groupSerial","generateSerial","","");
                clearSerial();
                deleteSerial();
                clearAllSerial();
                generateSerial();
            });
        }
        
        function clearSerial(){
            $('.btnClearSerial').click(function(){
                var count = $(this).data("idx");
                $("#serial"+count).val("");
                serialFrom();
            });
        }
        
        function clearAllSerial(){
            $('.btnDeleteAllSerial').unbind('click').click(function(){
                if (confirm("Hapus semua Serial?")) {
                    $(".inputGroupSerial").remove();
                    $("#serial").val("");
                    $("#serial").focus();
                }
            });
        }
        
        function deleteSerial(){
            $('.btnDeleteSerial').unbind('click').click(function(){
                var count = $(this).data("idx");
                var qty = $("#FRM_FIELD_QTY").val();
                $("#inputGroupSerial"+count).remove();
                $("#FRM_FIELD_QTY").val(qty-1);
            });
        }
        
        function validatex(formId){
            var errValidate = false;
            var errorIndex = -1;
            var idError = "";
            $(''+formId+' .required').each(function(i){
                var id = this.id;
                var value = $(this).val();
                var title = $(this).attr('title');
                $(this).parent().find('.alert').remove();
                if ( $( this ).hasClass( "number" ) ) {
                    if ($( this ).hasClass( "range" ) ){
                        var min = $(this).data('min');
                        var max = $(this).data('max');
                        if (value>min && value <max){
                        }else{
                            errValidate = true;
                            $(this).parent().append("<div style='margin-top:5px;' class='alert alert-danger'>"+title+" is between "+min+"-"+max+". Please select the correct value...</div>"); 
                            if (errorIndex==-1){
                                errorIndex = i;
                                idError = $(this).attr('id');
                            }
                        }
                    }else{
                        if (value<=0){
                            errValidate = true;
                            $(this).parent().append("<div style='margin-top:5px;' class='alert alert-danger'>"+title+" is less than 0. Please select the correct value...</div>");
                            if (errorIndex==-1){
                                errorIndex = i;
                                idError = $(this).attr('id');
                            }
                        }
                    }
                }else{
                    if (value.length==0 || value ==0){
                        errValidate = true;
                        $(this).parent().append("<div style='margin-top:5px;' class='alert alert-danger'>"+title+" is still empty. Please fill it...</div>");
                        if (errorIndex==-1){
                            errorIndex = i;
                            idError = $(this).attr('id');
                        }
                    }
                }
            });
            setTimeout(function(){
                $('#'+idError+'').focus();
            }, 300);
            return errValidate;
        }
        
        function getLocationByCashBillMain(cashBillMain){
            var url = ""+base+"/TransactionCashierHandler";
            var data = "loadtype=checklocation&command=<%=Command.NONE%>&cashier="+cashBillMain+"";
            ajaxTransactionHandler(url,data,"POST","","getLocationBill","","");
        }
        
        function clearForm(formId){
            $(''+formId+' input[type=text]').val('');
            $(''+formId+' input[type=password]').val('');
            $(''+formId+'').find('.alert').remove();
        }
        
        function checkPrintStatus(oidBillMain){
            var url = ""+base+"/TransactionCashierHandler";
            var data = "loadtype=checkPrintStatus&command=<%=Command.NONE%>&<%=FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]%>="+oidBillMain+"";
            ajaxTransactionHandler(url,data,"POST","","checkPrintStatus","","");
        }
        
        function confirmPrintStatus(){
            $('#btnConfirm').click(function(){
                var oid = $('#mainBillIdSource').val();
                var url = ""+base+"/TransactionCashierHandler";
                var data = "loadtype=confirmPrintStatus&command=<%=Command.SAVE%>&<%=FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]%>="+oid+"&userid=<%= userId %>";
                $('#btnConfirm').html('Please wait...').attr('disabled','disabled');
                ajaxTransactionHandler(url,data,"POST","","confirmPrintStatus","","");
            });
        }
        
        function doublePay(){
            $("#btnDoublePayment").click(function(){
                var total = $("#FRM_FIELD_COSTUM_BALANCE").val();
                var totalString = $("#totalCharge").html();
                var cashBillId = $("#mainBillIdSource").val();
                $("#multiPaymentBody").html("");
                $('#remainMulti').html(totalString);
                $('#remainMultiPayment').val(total);
                $("#multiPayCashBillMainId").val(cashBillId);
                loadListMultiPayment();
            });
        }
        
        function loadListMultiPayment(){
            var url = ""+base+"/TransactionCashierHandler";
            var data = $("#multiPaymentForm").serialize();
            data = data + "&command=<%=Command.NONE%>&loadtype=loadlistmultipayment&type=purelist";
            ajaxTransactionHandler(url,data,"POST","#multiPaymentBody","loadListMultiPayment","true","");
        }
        
        function btnAddMultiPayment(){
            $("#btnAddMultiPayment").click(function(){
                $(".payTypes").val("");
                $(".dynamicMultiSubPayment").html("");
                $(".contentMultiSubPayment").html("");
                $("#addMultiPayment").modal("show");
            });
        }
        
        function payTypes(){
            $('.payTypes').change(function(){
                $(".contentMultiSubPayment").html("");
                var url = ""+base+"/TransactionCashierHandler";
                var remainMulti = $('#remainMultiPayment').val();
                var oidMultiPayment = $("#MASTER_FRM_FIELD_PAY_TYPE_MULTI").val();
                var data = "command=<%=Command.NONE%>&loadtype=loadsubpaymentmulti&remainMulti="+remainMulti+"&oidMultiPayment="+oidMultiPayment+"";
                ajaxTransactionHandler(url,data,"POST",".dynamicMultiSubPayment","payTypes","","");
            });
        }
        
        function payTypes2(){
            $('.payTypes2').change(function(){
                var url = ""+base+"/TransactionCashierHandler";
                var remainMulti = $('#remainMultiPayment').val();
                var oidMultiPayment = $(this).val();
                var data = "command=<%=Command.NONE%>&loadtype=loadsubmultipayment&remainMulti="+remainMulti+"&oidMultiPayment="+oidMultiPayment+"";
                ajaxTransactionHandler(url,data,"POST",".contentMultiSubPayment","payTypes2","","");
            });
        }
        
        function calcMoney(){
            $(".calcMoney").keyup(function(){
                var remainMulti = $('#remainMultiPayment').val();
                var numberValue = 0;
                var tempValue = $(this).val();
                if (tempValue.length>0){
                    numberValue = tempValue;
                }else {
                    numberValue = 0;
                }
                var newRemain = remainMulti- numberValue;
                $("#calcAmount").val(newRemain);
            });
        }
        
        function btnSaveAddMultiPayment(){
            $("#btnSaveAddMultiPayment").click(function(){
                var payTypes = $(".payTypes").val();
                var cansave = true;
                if (payTypes=="<%=PstCustomBillMain.PAYMENT_TYPE_CASH%>"){
                    var payTypes2 = $(".payTypes2").val();
                    var paymentValue = 0;
                    var tempValue = $(".calcMoney").val();
                    var calcAmount = $("#calcAmount").val();
                    if (payTypes2==""){
                        alert("Please Select Payment Details...");
                        cansave = false;
                    }else if (tempValue.length=0 || tempValue==0){
                        alert("Please Insert Amount...");
                        cansave = false;
                    }                   
                }else if (payTypes=="<%=PstCustomBillMain.PAYMENT_TYPE_CHECK%>"){
                    var payTypes2 = $(".payTypes2").val();
                    var paymentValue = 0;
                    var tempValue = $(".calcMoney").val();
                    var bankName = $(".contentMultiSubPayment #FRM_FIELD_DEBIT_BANK_NAME_MULTI").val();
                    var bankAccNumber = $(".contentMultiSubPayment .cName").val();
                    var ccNumber = $(".contentMultiSubPayment #FRM_FIELD_CC_NUMBER_MULTI").val(); 
                    var datePicker = $(".contentMultiSubPayment .datePicker").val();                         
                    var calcAmount = $("#calcAmount").val();
                    if (payTypes2==""){
                        alert("Please Payment Detail...");
                        cansave = false;
                    }else if (bankName.length==0 || bankName==""){
                        alert("Please Insert Bank Name...");
                        cansave = false;
                    }else if (bankAccNumber.length==0|| bankAccNumber==""){
                        alert("Please Insert Bank Account Number...");
                        cansave = false;
                    }else if (ccNumber.length==0 || ccNumber==""){
                        alert("Please Insert Cheque Number...");
                        cansave = false;
                    }else if (datePicker.length==0 || datePicker==""){
                        alert("Please Insert Expired Date...");
                        cansave = false;
                    }else if (tempValue.length==0 || tempValue==0){
                        alert("Please Insert Amount...");
                        cansave = false;
                    } 
                }else if (payTypes=="<%=PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD%>"){
                    var payTypes2 = $(".payTypes2").val();
                    var paymentValue = 0;
                    var tempValue = $(".calcMoney").val();
                    var bankName = $(".contentMultiSubPayment #FRM_FIELD_DEBIT_BANK_NAME").val();
                    var ccNumber = $(".contentMultiSubPayment #FRM_FIELD_CC_NUMBER").val(); 
                    var datePicker = $(".contentMultiSubPayment .datePicker").val();                         
                    var calcAmount = $("#calcAmount").val();                     
                    if (payTypes2==""){
                        alert("Please Select Bank Name...");
                        cansave = false;
                    }else if (bankName.length==0 || bankName==""){
                        alert("Please Insert Bank Name...");
                        cansave = false;
                    }else if (ccNumber.length==0 || ccNumber==""){
                        alert("Please Insert Card Number...");
                        cansave = false;
                    }else if (datePicker.length==0 || datePicker==""){
                        alert("Please Insert Expired Date...");
                        cansave = false;
                    }else if (tempValue.length==0 || tempValue==0){
                        alert("Please Insert Amount...");
                        cansave = false;
                    }
                }else if (payTypes=="<%=PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD%>"){
                    var payTypes2 = $(".payTypes2").val();
                    var paymentValue = 0;
                    var tempValue = $(".calcMoney").val();
                    var bankName = $(".contentMultiSubPayment #FRM_FIELD_DEBIT_BANK_NAME_MULTI").val();
                    var cardName = $(".contentMultiSubPayment #FRM_FIELD_CC_NAME").val();
                    var ccNumber = $(".contentMultiSubPayment #FRM_FIELD_CC_NUMBER_MULTI").val(); 
                    var datePicker = $(".contentMultiSubPayment .datePicker").val();                         
                    var calcAmount = $("#calcAmount").val();  
                    if (payTypes2==""){
                        alert("Please Select Type of Card...");
                        cansave = false;
                    }else if (cardName.length==0){
                        alert("Please Insert Card Name...");
                        cansave = false;
                    }else if (bankName.length==0){
                        alert("Please Insert Bank Name...");
                        cansave = false;
                    }else if (ccNumber.length==0){
                        alert("Please Insert Card Number...");
                        cansave = false;
                    }else if (datePicker.length==0){
                        alert("Please Insert Expired Date...");
                        cansave = false;
                    }else if (tempValue.length==0 || tempValue==0){
                        alert("Please Insert Amount...");
                        cansave = false;
                    }  
                }else if (payTypes=="<%=PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR%>"){
                    var payTypes2 = $(".payTypes2").val();
                    var paymentValue = 0;
                    var voucherCode = $("#voucherCodeInput2").val();
                    if (payTypes2==""){
                        alert("Please Select Payment Detail...");
                        cansave = false;
                    }else if (voucherCode.length==0){
                        alert("Please Insert Voucher Code...");
                        cansave = false;
                    }
                }else if (payTypes=="<%=PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT%>"){
                    var payTypes2 = $(".payTypes2").val();
                    if (payTypes2==""){
                       alert("Please Select Payment Detail...");
                       cansave = false;
                    }
                }  
                if (cansave==true){
                    var form1 = $("#multiPaymentFormAdd").serialize();
                    var form2 = $("#multiPaymentForm").serialize();
                    var totalAmount = $("#FRM_FIELD_COSTUM_BALANCE").val();
                    var url = ""+base+"/TransactionCashierHandler";
                    var data = "command=<%=Command.NONE%>&loadtype=loadlistmultipayment&type=addNew&totalAmount="+totalAmount+"";
                    data = data + "&" + form1 + "&" + form2;
                    ajaxTransactionHandler(url,data,"POST","#multiPaymentBody","loadListMultiPayment","false","");
                    $("#addMultiPayment").modal("hide");
                }
            });
        }
        
        function deleteMultiPayment(){
            $('.deleteMultiPayment').click(function(){
                var index = $(this).data('index');
                var form2 = $("#multiPaymentForm").serialize();
                var totalAmount = $("#FRM_FIELD_COSTUM_BALANCE").val();
                var url = ""+base+"/TransactionCashierHandler";
                var data = "command=<%=Command.NONE%>&loadtype=loadlistmultipayment&type=deleteOnce&index="+index+"&totalAmount="+totalAmount+"&"+form2+"";
                ajaxTransactionHandler(url,data,"POST","#multiPaymentBody","loadListMultiPayment","false","");
            });
        }
        
        function btnSaveMultiPayment(){
            $("#btnSaveMultiPayment").click(function(){
                maxDisc = $("#maxDisc").val();
                var getDiscount = $("#discountnominal").val();
                getDiscount = getDiscount.replace(/,/g, "");
                if(maxDisc < getDiscount && <%= usingDiscAssign %> == 1){
                    alert("Maximum Discount is "+maxDisc);
                }else{
                    $("#loadtype").val("");
                    $("#command").val("<%= Command.SAVE %>");
                    var formPayment = $("#multiPaymentForm").serialize();
                    var mainBill = $("form#<%= FrmCashPayment.FRM_NAME_CASH_PAYMENT %>").serialize();
                    var payType= $("#FRM_FIELD_PAY_TYPE_MULTI_1").val();
                    var separateprint = $("#separateprint").val();
                    var data = mainBill +"&"+formPayment+"&"+"transaction_type=0&<%=FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE]%>="+payType+"&separateprint="+separateprint+"";
                    $(this).attr({"value":"Please wait...","disabled":"true"});
                    var url = ""+base+"/TransactionCashierHandler";
                    ajaxTransactionHandler(url,data,"POST","","saveTransaction","","");
                    $("#btnSaveMultiPayment").unbind('click');
                }
            });
        }

        $("#btnPrintSeparateMulti").click(function(){
            $("#btnfrom").val("1");
            $("#separateprint").val("1");
            if(<%= useCoverNumber %> == 0){
                $("#btnSaveMultiPayment").trigger("click");
            }else{
                coverNumber();
            }
        });
        
        btnSaveMultiPayment();
        
        $("#btnSaveMultiPaymentX").click(function(){
            $("#btnSaveMultiPaymentX").attr({"disabled":true});
            $("#btnfrom").val("1");
            $("#separateprint").val("0");
            if(<%= useCoverNumber %> == 0){
                $("#btnSaveMultiPayment").trigger("click");
            }else{
                coverNumber()
            }
        });
        
        btnSaveAddMultiPayment();
        doublePay();
        payTypes();
        
        $('#btnReprintBill').click(function(){
            loadListReprintUp();
        });
        
        $('#spvFinger').keyup(function(){
            checkUser();
        });
        
        $('#supervisorName').keyup(function(){
            var verify = "<%= verificationType%>";
            if (verify=="1"){
                checkUser2();
            }
        });
        
        $('#opensearchmember #searchitem').keyup(function(){
            $('#opensearchmember #btnSearchMember').trigger('click');
        });
        
        $('#btnPayFinger').click(function(){
            $("#btnfrom").val("0");
            var needVerification = <%= paymentNeedVerifikasi%>;
            var verificationType = <%= verificationType%>;
            var userName = "<%= userlogin%>";
            //Cek apakah memerlukan verifikasi atau tidak
            if (needVerification=="1"){
                //Payment memerlukan verifikasi, maka selanjutnya di cek, 
                //apakah verifikasi menggunakan finger atau password biasa
                if (verificationType=="1"){
                    //verifikasi menggunakan finger print
                    $('#spvFingerPayment').val(userName);
                    checkUserPayment();
                    $('#openAuthorizePayment').modal({
                        backdrop: 'static',
                        keyboard: false
                    });
                }else{
                    //verifikasi menggunakan password biasa
                    clearForm('#verificationPassword');
                    $('#openAuthorizePaymentNoFinger').modal({
                        backdrop: 'static',
                        keyboard: true
                    });                        
                }
            }else{
                //Payment tidak memerlukan verifikasi
                $('#btnPaySave').trigger('click');
            }               
        });
        
        $('#spvFingerPayment').keyup(function(){
            checkUserPayment();
        });
        
        $("form#<%= FrmContactList.FRM_NAME_CONTACTLIST %>2").submit(function(){
            var url = ""+base+"/TransactionCashierHandler";
            var data = $(this).serialize();
            ajaxTransactionHandler(url,data,"POST","","addCtList","","");
            return false;
        });

        $('#neworder2').click(function(){
            var mainBill = $('#mainBillIdSource').val();
            if (mainBill=="0" || mainBill.length==0){
                $('#neworder').trigger('click');
            }else{
                $('#newOrderConfirmation').modal('show');
            }
        });
        
        $('#btnNewOrderClick').click(function(){
            $('#neworder').trigger('click');
        });
        
        $('#btnAuthorizePaymentPass').click(function(){
            if (validatex('#verificationPassword')==false){
                //cek apakah user name dan password
                var url = ""+base+"/TransactionCashierHandler";
                var username = $('#spvPayment').val();
                var password = $('#spvPaymentPass').val();
                var oidDoc = $('#mainBillIdSource').val();
                var data = "base="+base+"&oidDoc="+oidDoc+"&username="+username+"&password="+password+"&loadtype=authPass&command=<%=Command.NONE%>";
                ajaxTransactionHandler(url,data,"POST","","authPass","","");
            }
        });

        $('#btnJoinBill').click(function(){
            loadJoinUp();
        });
        
        //////////////////////////////////////
        var dataSend;
        var command;
        var dataFor;
        var approot = "<%= approot%>";
        var modalSetting = function(elementId, backdrop, keyboard, show){
            $(elementId).modal({
                backdrop: backdrop,
                keyboard: keyboard,
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
               approot      : approot,
               dataSend     : dataSend,
               servletName  : servletName,
               dataAppendTo : dataAppendTo,
               notification : notification,
               ajaxDataType : dataType
            });
        };

        var consumePack = function(elementId){
            $(elementId).click(function(){
                var mainBill = $("#mainBillIdSource").val();
                if (mainBill==0){
                    alert("Please make new order first...");
                }else{
                    $("#salesPackage").modal("show");
                    var oidReservation = $("#reservationIdSource").val();
                    $("#salesPackage #FRM_FIELD_OID_RESERVATION").val(oidReservation);
                    var guestName = $('#guestNameSource').val();
                    $("#salesPackage #reservationSearch").val(guestName);
                    var multiLocation ="<%=multilocation %>";
                    var defaultOidLocation = "<%= defaultOidLocation%>";
                    var dataFor = "showFilterFormPackage";
                    command = "<%= Command.NONE%>"; 

                    dataSend = {
                        "FRM_FIELD_DATA_FOR"          : dataFor,                       
                        "FRM_FIELD_MULTI_LOCATION"    : multiLocation,
                        "FRM_FIELD_DEFAULT_LOCATION"  : defaultOidLocation,
                        "command"		      : command
                    };

                    onSuccess = function(data){

                    };

                    onDone = function(data){
                        loadReservationPackage("#searchReservation","1");
                        selectSchedule(".selectSchedule");
                        loadBillPackage("#loadPackageBill");
                        datePickerSetting(".datepicker", "yyyy-mm-dd");
                    };
                    getDataFunction(onDone, onSuccess, approot, command, dataSend, "CashierPackage", "#modal-body-package", true, "json");
                }
            });
        };
        
        var qtyHelp=0;
        
        var consumeBill = function (elementId){
            $(elementId).click(function(){
                var scheduleId = $(this).data("scheduleId");
                var oidCustome = $(this).data("oid");
                $("#scheduleId").val(scheduleId);
                $("#customePackBillingId").val(oidCustome);
                qtyHelp= $(this).data("remain");
                $("#parentPackage").val("");
                var oidMaterial = $(this).data("oidmat");
                var price = $(this).data("price");
                var oidBillMain = $("#mainBillIdSource").val();
                var qty = $(this).data("qty");
                var getDiscPct = $(this).data("discPct");
                var getDiscRp = $(this).data("discRp");
                var getDiscUsd = $(this).data("discUsd");
                $("#isPackage").val("1");
                var matName = $(this).data("matname");
                $("#<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_MAIN_ID] %>_ADD_ITEM").val(oidBillMain);
                $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(price);
                $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(oidMaterial);
                $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(matName);
                $("#CONTENT_ITEM_TITLE").html(matName);
                if(<%= disableQty %> == 0){$("#openadditem").modal("show");
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").val('');
                }else{
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").val(qty);
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #btnsaveitem").trigger("click");
                }
            });
        };
        
        var loadReservationPackage = function(elementId,showModal) {
            $(elementId).click(function(){
                if (showModal==="1"){
                    $("#reservationPackage").modal("show");
                    $("#searchReservationInput").val("");
                }                   
                var dataFor = "showReservationPackage";
                command = "<%= Command.NONE%>"; 
                var typeText = $("#searchReservationInput").val();
                dataSend = {
                    "FRM_FIELD_DATA_FOR" : dataFor,                                              
                    "command"            : command,
                    "typeText"           : typeText
                };
                onSuccess = function(data){

                };

                onDone = function(data){
                    selectReservationPackage(".clickReservationPack");
                };
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "CashierPackage", "#modal-body-package-reservation", true, "json");

            });
        };

        var selectReservationPackage = function(elementId){
            $(elementId).click(function(){
                var oidReservation = $(this).data("oidreservation");
                var reservationNum = $(this).data("reservationnum");
                $("#reservationSearch").val(reservationNum);
                $("#FRM_FIELD_OID_RESERVATION").val(oidReservation);
                $("#reservationPackage").modal("hide");
            });
        };

        var selectSchedule = function (elementId){
            $(elementId).click(function(){
                var id = $(this).attr("id");
                var sch = $(this).data("sch");
                $("#schedulePackValue").val(sch);
                $(this).addClass("active");
                if (id==="schedulePack"){
                    $('#nonSchedulePack').removeClass("active");
                }else{
                    $('#schedulePack').removeClass("active");
                }
            });
        };

        var loadBillPackage = function(elementId){
            $(elementId).click(function(){
                var location = $("#FRM_FIELD_LOCATION_ID_PACK").val();
                var startDate = $("#startdate").val();
                var endDate = $("#enddate").val();
                var oidReservation = $("#FRM_FIELD_OID_RESERVATION").val();
                var oidBillMain = $("#mainBillIdSource").val();
                var sch = $("#schedulePackValue").val();
                var dataFor = "showBillPackage"; command = "<%= Command.NONE%>";
                dataSend = {
                    "FRM_FIELD_DATA_FOR": dataFor,
                    "command"           : command,
                    "oidReservation"    : oidReservation,
                    "sch"               : sch,
                    "location"          : location,
                    "oidBillMain"       :oidBillMain,
                    "startdate"         :startDate,
                    "enddate"           :endDate
                };
                onSuccess = function(data){
                    
                };
                onDone = function(data){
                    consumeBill(".consumeBill");
                };
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "CashierPackage", "#packageBillListPlace", true, "json");
            });
        };

        var loadVoucher=function(showModal,showPage){
            var dataFor = "searchVoucherRegular";
            var getPaymentType = $("#getPaymentType").val();
            command = "<%= Command.LIST%>";
            var typeText = $("#searchVoucherInput").val();
            var oidBillMain = $("#mainBillIdSource").val();
            var masterType = $(getPaymentType).val();
            dataSend = {
                "FRM_FIELD_DATA_FOR"    : dataFor,
                "FRM_FIELD_SHOW_PAGE"   : showPage,
                "command"               : command,
                "FRM_FIELD_BILL_MAIN"   : oidBillMain,
                "FRM_FIELD_TYPE_TEXT"   : typeText,
                "MASTER_FRM_FIELD_PAY_TYPE" : masterType
            };
            onSuccess = function(data){

            };

            onDone = function(data){
                loadDataPagingVc(".pagingVc");
                voucherSelect(".selectVoucherClick");
            };
            if (showModal==true){
                $("#modalVoucher").modal("show");
            }
            getDataFunction(onDone, onSuccess, approot, command, dataSend, "CashierPayment", "#modal-body-voucher", true, "json");
        };

        var loadDataPagingVc = function(elementId){
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
                loadVoucher(false,showPage);
            });
        };

        var voucherSelect = function(elementId){
            $(elementId).click(function(){
                if (confirm('Are you sure to choose this voucher?')) {
                    //yes!
                    var voucherNo = $(this).data("voucherno");
                    var voucherId = $(this).data("voucherid");
                    var nominal = $(this).data("nominal");
                    var source = $("#sourceSelectVoucher").val();

                    if (source=="1"){
                        $("#voucherCodeInput").val(voucherNo);
                        $("#VOUCHER_ID").val(voucherId);
                        $("#<%=FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]%>").val(nominal);
                    }else if (source=="2"){
                        $("#voucherCodeInput2").val(voucherNo);
                        $("#FRM_FIELD_VOUCHER_ID_MULTI").val(voucherId);
                        $("#FRM_FIELD_PAY_AMOUNT_MULTI").val(nominal);
                    }
                    $("#modalVoucher").modal("hide");
                } 
            });
        };
        
        var checkLastOpening = function(){
            var oidLogin = "<%= userId%>";
            var dataFor = "checkLastOpening";
            command = "<%= Command.NONE%>";
            dataSend = {
                "FRM_FIELD_DATA_FOR": dataFor,
                "command"           : command,
                "FRM_FIELD_USER_OID": oidLogin
            };
            onSuccess = function(data){};
            onDone = function(data){
                var result = data.FRM_FIELD_HTML;
                if (result==="1"){
                    $("#btnClosingBalance").trigger("click");
                }
            };
            getDataFunction(onDone, onSuccess, approot, command, dataSend, "CashierPayment", "", true, "json");
        };

        $("#searchVoucherButton").click(function(){
            loadVoucher(false,0);
        });
        
        loadReservationPackage("#btnSearchReservation","0");
        consumePack("#btnConsumePack");
        btnJoinSave();
        btnPrintOption();
        checkUser();
        checkUser2();
        viewDetailEditor();
        generateSerial();
        btnJoinBack();
        confirmPrintStatus();
        checkLastOpening();
        modalSetting("#salesPackage", "static", false, false);
        
        $('.money').keyup(function(){
            var value = $(this).val();
            var conValue = parserNumber(value,false,0,'');
            $(this).val(conValue);
        });

        $("#btnSaveSalesModal").click(function(){
            var sales= $("#FRM_SALES").val();
            var negara = $("#FRM_REGION").val();
            var genders = document.getElementsByName('FRM_GENDER');
            var gender = "";
            for(var i = 0; i < genders.length; i++){
                if(genders[i].checked){
                    gender = genders[i].value;
                    break;
                }
            }
            <%if (useForGB == "1"){%>
                if (sales==""){
                    alert("Please select the sales first");
                    setTimeout(function(){
                        $('#FRM_SALES').focus();
                    }, 300);
                } else if (negara==""){
                    alert("Please select region");
                    setTimeout(function(){
                        $('#FRM_SALES').focus();
                    }, 300);
                } else if (gender==""){
                    alert("Please select gender");
                    setTimeout(function(){
                        $('#FRM_SALES').focus();
                    }, 300);
                } else{
                    $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SALES_CODE] %>").val(sales);
                    $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ID_NEGARA] %>").val(negara);
                    $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GENDER] %>").val(gender);
                    $("#openneworder #btnneworder").trigger('click');
                    $("#salesModal").modal("hide");
                }	
            <%} else {%>		
                alert("Masuk sini");
                if (sales==""){
                    alert("Please select the sales first");
                    setTimeout(function(){
                        $('#FRM_SALES').focus();
                    }, 300);
                } else{
                    $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SALES_CODE] %>").val(sales);
                    $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ID_NEGARA] %>").val(negara);
                    $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GENDER] %>").val(gender);
                    $("#openneworder #btnneworder").trigger('click');
                    $("#salesModal").modal("hide");
                }	
            <%}%>
        });
        
        //BUTTON CLOSING BALANCE
        $("#btnClosingBalance").click(function(){
            var enable = "<%= enableClose%>";
            var d = new Date();
            var curentTime = ""+(d.getHours()<10?'0':'') + d.getHours()+":"+(d.getMinutes()<10?'0':'') + d.getMinutes()+":"+(d.getSeconds()<10?'0':'') + d.getSeconds()+"" ;
            $("#closingBillTime").html(curentTime);
            if (enable ==="0"){
                $("#openclosingbalance .amountCloseClear").val('');
                $("#supervisorName").val("");
                $("#supervisorPassword").val("");
                $("#openclosingbalance").modal("show");
                $("#CONTENT_CLOSING").hide();
                $("form#closing").show();
            }else{
                var appUser = "<%= userId%>";
                command = "<%= Command.NONE%>";
                var dataFor= "getCountOpenBill";
                var dataSend = {"FRM_FIELD_DATA_FOR" : dataFor, "command": command,"FRM_FIELD_APP_USER" : appUser};
                onSuccess = function(data){};
                onDone = function(data){
                    var result = Number(data.FRM_FIELD_HTML);
                    if (result == "0"){
                        $("#openclosingbalance .amountCloseClear").val('');
                        $("#openclosingbalance").modal("show");
                        $("#CONTENT_CLOSING").hide();
                        $("form#closing").show();
                    }else{
                        alert("Please process the incomplete bill first..");
                    }
                    $("#isclear").val(data.FRM_FIELD_IS_CLEAR);
                };
                getDataFunction(onDone, onSuccess, approot, command, dataSend, "CashierPayment", "", true, "json");
            }		
        });
        
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
                    idleState = true;
                }, idleWait);
            });
            $("body").trigger("mousemove");
        };
        
        var intervalNotif;
        intervalNotif =  setInterval(function() {
            checkOpenBill();
        },1000);
        
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
            $(elementId).click(function(){  clearInterval(intervallAutoLog);
                $("#modalAutoLogOut").modal("hide");
                autoFirstCounter = 30;                  
            });
        };
        
        var loadMenu = function(menu,type,oidcategory,design,pricetype,location,parent, datafor){
            command = "<%= Command.NONE%>";
            var dataFor = datafor;
            if(datafor === "undefined" || datafor === undefined){
                dataFor = "loadMenu";
            }
            dataSend = {
                "FRM_FIELD_DATA_FOR": dataFor,"command": command, "FRM_FIELD_MENU": menu, "FRM_FIELD_TYPE" : type, "FRM_FIELD_CATEGORY": oidcategory, "FRM_FIELD_DESIGN" :design, "FRM_FIELD_PRICE_TYPE" :pricetype, "FRM_FIELD_LOCATION" :location, "FRM_FIELD_PARENT" :parent
            };
            onSuccess = function(data){};
            onDone = function(data){
                loadMenuClick(".categoryMenu");
                mainMenuOutletClick(".mainMenuOutlet");
                categoryMenuClick(".menuClick");
            };
            getDataFunction(onDone, onSuccess, approot, command, dataSend, "TransactionCashierHandlerNew", "#dynamicPlaceForMenu", true, "json");
        };
        
        var loadMenuClick = function(elementId){
            $(elementId).click(function(){ 
                var menu = $(this).data("menu");
                var type = $(this).data("type");
                var oidCategory =$(this).data("oidcategory");
                var design = $(this).data("design");
                var priceType = $(this).data("pricetype");
                var location = $(this).data("location");
                var parent = $(this).data("parent");
                var dataFor = $(this).data("for");
                loadMenu(menu,type,oidCategory,design,priceType,location,parent,dataFor);
            });    
        }
        
        var categoryMenuClick = function(elementId){
            $(elementId).click(function(){
                var oidCategory =$(this).data("oidcategory");
                $("#categoryMenuId").val(oidCategory);
                loadItemList2();
            });
        }
        
        var mainMenuOutletClick= function(elementId){
            $(elementId).click(function(){
                $(".additem").submit();
            });
        }
        
        var checkOpenBill = function(){
            var locationId = "<%= location.getOID()%>";
            command = "<%= Command.NONE%>";
            var dataFor = "checkOpenBill";
            dataSend = {
                "FRM_FIELD_DATA_FOR": dataFor,
                "command": command,
                "FRM_FIELD_LOCATION_ID": locationId
            };
            onSuccess = function(data){}; 
            onDone = function(data){
                var newCount = Number(data.FRM_FIELD_RETURN_OID);
                var lastCount = $("#notificationCount").val();
                var lasts = Number(lastCount);
                var number = Number(data.FRM_FIELD_HTML);
                if (number>0){$("#newBillNotification").html(""+number+"");
                }else{
                    $("#newBillNotification").html("");
                }
                if (lastCount!=newCount){
                    var audio = new Audio('../sound/soft-bells_u8adNqH5.mp3');
                    audio.play();
                    $("#notificationCount").val(newCount);
                }
            };
            getDataFunction(onDone, onSuccess, approot, command, dataSend, "TransactionCashierHandlerNew", "", true, "json");
        };
        
        checkOpenBill();
        
        function addNum(elementAppend){
            $(".addNum").click(function(){
                var value = $(this).data("value");
                if (value==-1){
                    $(elementAppend).val("");
                }else{
                    var lastVal = $(elementAppend).val();
                    if (value==0 && lastVal.length==0){
                        $(elementAppend).val("");
                    }else{
                        var newVal = lastVal +""+value+"";
                        $(elementAppend).val(newVal);
                    }
                }
                $(elementAppend).focus();
            });
        }
        
        function showNumKeyboard(elementId, keyBoardId){
            if(keyBoardId === undefined || keyBoardId === "undefined"){
                keyBoardId = "#hiddenKeyBoard";
            }
            $(elementId).focus(function(){
                var width = $(window).width();
                if (width>=800){
                    $(keyBoardId).fadeIn("fast");
                }
            });
            $(elementId).focusout(function(){
                $(keyBoardId).fadeOut("fast");
            });
        }
        
        if (autoLog=="1"){
            iddleEvent(timeLogOut);
        }
        
        keepLoginAuto("#keepLoginAuto");
        logOutAutoButton("#logOutAuto");
        showNumKeyboard("#openadditem #FRM_FIELD_QTY");
        addNum("#openadditem #FRM_FIELD_QTY");
        showNumKeyboard("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>", "#neworderKeyBoard");
        addNum("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_PAX_NUMBER] %>");
        
        //----------AUTO LOG OUT FUNCTION
        function datePickerSetting(selector, formatDate){
            $(selector).datepicker({
                format: formatDate
            }).on("changeDate", function (ev) {
                $(this).datepicker('hide');
            });
        }

        $(".btnOpenSearchMember2").click(function(){
            $("#opensearchmember").modal("show");
            var classType = "7";
            var searchtype = "guest";
            searchMember("","loadsearchmembernewbill",<%= Command.NONE %>,classType,"CONTENT_SEARCH_MEMBER",searchtype);
        });

        $(".btnOpenSearchNewMember").click(function(){
            $("#opensearchmember").modal("show");
            var classType = "7";
            var searchtype = "guest";
            searchMember("","loadsearchnewmember",<%= Command.NONE %>,classType,"CONTENT_SEARCH_MEMBER",searchtype);
        });

        function updateMember(oidBillMain,oidCustomer){
            $.ajax({
                type    : "POST",
                data    : {
                    "loadtype":"updateCustomerId",
                    "command":"<%= Command.SAVE %>",
                    "oidBillMain":oidBillMain,
                    "oidCustomer":oidCustomer
                },
                url	: "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                cache   : false,
                success : function(data) {
                    $("#updatesalesModal").modal("hide");
                    $("#CONTENT_ANIMATE_CHECK").fadeIn("medium");
                    loadTransaction(oidBillMain,"loadbill",<%= Command.NONE %>,0,"CONTENT_LOAD");
                }, error : function(data){

                }
            }).done(function(data){

            });
        }

        $("#btnUpdateSalesModal").click(function(){
            var oidBillMain = $('#mainBillIdSource').val();
            var oidCustomer = $('#newMemberOid').val();
            updateMember(oidBillMain,oidCustomer);
        });

        $("#btnDeleteMember").click(function(){
            $('#newMemberOid').val("");
            $('#newMemberCode').val("");
            $('#newMemberName').val("");
            $('#newMemberPhone').val("");
            $('#newMemberGroup').val("0");
        });

        $("#addNewCustomer").click(function(){
            var locationId = $("#openneworder #<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] %>").val();
            $("#opennewcustomer input[name=<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_LOCATION_ID]%>]").val(locationId);
            $("#opennewcustomer input[name=<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_CONTACT_CODE]%>]").val("");
            $("#opennewcustomer input[name=<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_NAME]%>]").val("");
            $("#opennewcustomer input[name=<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE]%>]").val("");
            $("#opennewcustomer input[name=FRM_FIELD_MEMBER_GROUP]").val("");
            $("#opennewcustomer").modal(modalSetting2);
            $("#opennewcustomer").modal("show");
        });

        $("#FRM_NEW_MEMBER").submit(function() {
            $("#saveNewMember").attr({"disabled":true});
            $.ajax({
                type    : "POST",
                data    : $(this).serialize(),
                url	: "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                cache   : false,
                success : function(data) {

                    $("#saveNewMember").removeAttr("disabled");
                    alert(data.FRM_FIELD_SHORT_MESSAGE);

                    if (data.FRM_FIELD_NUMBER_OF_ERROR === "0") {
                        $("#opennewcustomer").modal("hide");
                        $('#newMemberOid').val(data.RETURN_NEW_MEMBER_OID);
                        $('#newMemberCode').val(data.RETURN_NEW_MEMBER_CODE);
                        $('#newMemberName').val(data.RETURN_NEW_MEMBER_NAME);
                        $('#newMemberPhone').val(data.RETURN_NEW_MEMBER_TELP);
                        $('#newMemberGroup').val(data.RETURN_NEW_MEMBER_GROUP);
                    }

                }, error : function(data){

                }
            }).done(function(data){

            });
            return false;
        });

        function countItemReturnExc() {
            $(".itemExc").keyup(function() {
                var qtyExc = 1 * $(this).val();
                var qtyItem = 1 * $(this).data('remain');
                if (qtyExc > qtyItem) {
                    $("#errorMsg").html("<i class='fa fa-exclamation-circle'></i> Maximum return for this item is " + qtyItem);
                    qtyExc = 0;
                    $(this).val("");
                } else {
                    $("#errorMsg").html("");
                }
                var itemPrice = $(this).data('price');
                var itemDiscount = $(this).data('discount');
                var totalExc = qtyExc * (itemPrice - itemDiscount);

                var row = $(this).data('row');
                $("#totalExc_"+row).html(totalExc.toLocaleString());

                //count total
                countGrandTotalPriceReturn();
                countGrandTotalPriceExchange();
            });
        }

        function countGrandTotalPriceReturn() {
            var grandTotalExc = 0;
            $(".itemExc").each(function() {
                var row = $(this).data('row');
                var total = 1 * $("#totalExc_"+row).html().replace(/,/g , "");
                grandTotalExc += total;
            });
            $("#grandTotalExc").html(grandTotalExc.toLocaleString());
            $("#payValDefault").val(grandTotalExc);
            countGrandTotalPaymentExchange();
        }

        function exchangeAllItem(checked) {
            $(".itemExc").each(function() {
                var qtyItem = checked ? 1 * $(this).data('remain') : 0;
                var itemPrice = $(this).data('price');
                var itemDiscount = $(this).data('discount');
                var totalExc = qtyItem * (itemPrice - itemDiscount);
                var row = $(this).data('row');
                $(this).val(qtyItem);
                $("#totalExc_"+row).html(totalExc.toLocaleString());
            });
            countGrandTotalPriceReturn();
        }

        function countTotalPriceItemExc(index) {
            var qtyExc = 1 * $("#qtyItemExc_"+index).val();
            var priceExc = 1 * $("#itemPriceExc_"+index).val();
            var discExc = 1 * $("#discItemExcVal_"+index).val();
            var total = (priceExc - discExc) * qtyExc;
            $("#totalPriceExc_"+index).val(total);
            $("#totalPriceExcShow_"+index).html(total.toLocaleString());

            countGrandTotalPriceExchange();
        }

        function countGrandTotalPriceExchange() {
            var total = 0;
            $(".totalPriceExchange").each(function() {
                total += 1 * $(this).val();
            });
            $("#grandTotalExcPrice").html(total.toLocaleString());

            var totalReturn = 1 * $("#grandTotalExc").html().replace(/,/g , "");
            if (totalReturn > total) {
                $("#grandTotalExcPrice").css({"color":"red"});
            } else {
                $("#grandTotalExcPrice").css({"color":"black"});
            }

            countGrandTotalPaymentExchange();
        }

        function countGrandTotalPaymentExchange() {
            var totalPay = 0;
            $(".payVal").each(function() {
                totalPay += 1 * $(this).val();
            });
            $("#grandTotalPayment").html(totalPay.toLocaleString());

            var totalExchange = 1 * $("#grandTotalExcPrice").html().replace(/,/g , "");
            if (totalExchange > totalPay) {
                $("#grandTotalPayment").css({"color":"red"});
            } else {
                $("#grandTotalPayment").css({"color":"black"});
            }

            var remain = totalPay - totalExchange;
            if (remain !== 0) {
                $("#paymentExcRemain").html(remain.toLocaleString()).css({"color":"red"});
            } else {
                $("#paymentExcRemain").html("");
            }
        }

        function searchItemExchange(dataSearch) {
            var currentIndex = 1 * $("#indexItemExc").val();
            var oidBillMain = $("#FORM_RETURN_EXCHANGE #oidBillMain").val();
            var cashCashierId = $("#cashCashierId").val();
            var data = {
                "loadtype"          : "searchItemExchange",
                "command"           : "<%= Command.NONE %>",
                "oidBillMain"       : oidBillMain,
                "currentIndex"      : currentIndex,
                "dataSearch"        : dataSearch,
                "CASH_CASHIER_ID"   : cashCashierId
            };

            $("#searchItemExchange").val("").focus();
            var btnHtml = $(".btnSearchItemExchange").html();
            $(".btnSearchItemExchange").html("<i class='fa fa-spinner fa-pulse'></i>").attr({"disabled":true});

            $.ajax({
                type    : "POST",
                data    : data,
                url	: "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                cache   : false,
                success : function(data) {
                    if (data.FRM_FIELD_NUMBER_OF_ERROR !== "0") {
                        alert(data.FRM_FIELD_SHORT_MESSAGE);
                    } else {
                        $("#tabelItemExchange").append(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                        $("#indexItemExc").val(currentIndex + 1);
                    }

                    $("#searchItemExchange").val("").focus();
                    $(".btnSearchItemExchange").html(btnHtml).removeAttr("disabled");

                    countGrandTotalPriceExchange();

                    $(".qtyItemExchange").unbind().keyup(function() {
                        var index = $(this).data('index');
                        countTotalPriceItemExc(index);
                    });

                    $(".discItemExchangePct").unbind().keyup(function() {
                        var index = $(this).data('index');
                        var priceExc = 1 * $("#itemPriceExc_"+index).val();
                        var discPct = 1 * $(this).val();
                        var discVal = priceExc * (discPct/100);
                        $("#discItemExcVal_"+index).val(discVal);
                        countTotalPriceItemExc(index);
                    });

                    $(".discItemExchangeVal").unbind().keyup(function() {
                        var index = $(this).data('index');
                        var priceExc = 1 * $("#itemPriceExc_"+index).val();
                        var discVal = 1 * $(this).val();
                        var discPct = discVal / priceExc * 100;
                        $("#discItemExcPct_"+index).val(discPct);
                        countTotalPriceItemExc(index);
                    });

                    $(".deleteItemExc").unbind().click(function() {
                        var index = $(this).data('index');
                        $("#rowItemExc_"+index).remove();
                        $("#searchItemExchange").val("").focus();
                        countTotalPriceItemExc(index);
                    });

                }, error : function(data){

                }
            }).done(function(data){

            });
        }

        function getFormPaymentExc() {
            var data = $("#FRM_MULTI_PAYMENT_EXCHANGE").serialize();
            var currentIndex = 1 * $("#indexPaymentExc").val();
            data += "&currentIndex=" + currentIndex;
            $.ajax({
                type    : "POST",
                data    : data,
                url	    : "<%= approot %>/TransactionCashierHandler",
                dataType : "json",
                cache   : false,
                success : function(data) {

                    if (data.FRM_FIELD_NUMBER_OF_ERROR !== "0") {
                        alert(data.FRM_FIELD_SHORT_MESSAGE);
                    } else {
                        $("#tabelPaymentExchange").append(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                        $("#modalMultiPaymentReturnExchange").modal("hide");
                        $("#indexPaymentExc").val(currentIndex + 1);
                    }

                    countGrandTotalPaymentExchange();

                    $(".payVal").unbind().keyup(function() {
                        countGrandTotalPaymentExchange();
                    });

                    $(".deletePayExc").unbind().click(function() {
                        var index = $(this).data('index');
                        $("#rowPayExc_"+index).remove();
                        countGrandTotalPaymentExchange();
                    });

                }, error : function(data){

                }
            }).done(function(data){

            });
        }

        function getFormReturnExchange(oidBillMain) {
            $("#modalBodyReturnExchange").html("Please wait...");
            $.ajax({
                type    : "POST",
                data    : {
                    "loadtype":"getFormReturnExchange",
                    "command":"<%= Command.NONE %>",
                    "oidBillMain":oidBillMain
                },
                url     : "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                cache   : false,
                success : function(data) {

                    $("#modalBodyReturnExchange").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);

                    countItemReturnExc();

                    $("#resetExc").unbind().click(function() {
                        getFormReturnExchange(oidBillMain);
                    });

                    $("#exchangeAll").unbind().click(function() {
                        var checked = $(this).is(":checked");
                        exchangeAllItem(checked);
                    });

                    $(".btnSearchItemExchange").unbind().click(function() {
                        searchItemExchange($("#searchItemExchange").val());
                    });

                    $("#searchItemExchange").unbind().keydown(function(e) {
                        if (e.keyCode == 13){
                            searchItemExchange($(this).val());
                        }
                    });

                    $("#btnAddMultiPayExc").unbind().click(function() {
                        getFormPaymentExc();
                    });

                    $("#btnAddMultiPaymentRetExc").unbind().click(function() {
                        $("#modalMultiPaymentReturnExchange input[type=text], textarea").val("");
                        $("#modalMultiPaymentReturnExchange #FRM_MULTI_EXC_TRANSACTION_TYPE").val("CASH");

                        //hide input credit/debit card
                        $("#FRM_MULTI_EXC_CARD_NAME").closest(".form-group").addClass("hidden");
                        $("#FRM_MULTI_EXC_CARD_NUMBER").closest(".form-group").addClass("hidden");
                        $("#FRM_MULTI_EXC_BANK_NAME").closest(".form-group").addClass("hidden");
                        $("#FRM_MULTI_EXC_EXPIRED_DATE").closest(".form-group").addClass("hidden");
                        var today = new Date();
                        var dd = today.getDate();
                        var mm = today.getMonth() + 1; //January is 0!
                        var yyyy = today.getFullYear();
                        if (dd < 10) {
                            dd = '0' + dd;
                        }
                        if (mm < 10) {
                            mm = '0' + mm;
                        }
                        today = yyyy+"-"+mm+"-"+dd;
                        $("#FRM_MULTI_EXC_EXPIRED_DATE").val(today);

                        $("#modalMultiPaymentReturnExchange").modal(modalSetting2);
                        $("#modalMultiPaymentReturnExchange").modal("show");
                        $("#FRM_MULTI_EXC_PAYMENT_VALUE").focus();

                        $(".date-picker").datepicker({
                            format : "yyyy-mm-dd"
                        });

                    });

                }, error : function(data){

                }
            }).done(function(data){

            });
        }

        $("body").on("change","#FRM_MULTI_EXC_PAYMENT_ID", function(){
            var paymentType =  $(this).find('option:selected').data('type');

            if (paymentType == 0) {
                $("#FRM_MULTI_EXC_CARD_NAME").closest(".form-group").removeClass("hidden");
            } else {
                $("#FRM_MULTI_EXC_CARD_NAME").closest(".form-group").addClass("hidden");
            }

            if (paymentType == 0 || paymentType == 2) {

                $("#FRM_MULTI_EXC_CARD_NUMBER").closest(".form-group").removeClass("hidden");
                $("#FRM_MULTI_EXC_BANK_NAME").closest(".form-group").removeClass("hidden");
                $("#FRM_MULTI_EXC_EXPIRED_DATE").closest(".form-group").removeClass("hidden");

            } else {

                $("#FRM_MULTI_EXC_CARD_NUMBER").closest(".form-group").addClass("hidden");
                $("#FRM_MULTI_EXC_BANK_NAME").closest(".form-group").addClass("hidden");
                $("#FRM_MULTI_EXC_EXPIRED_DATE").closest(".form-group").addClass("hidden");

            }
        });

        $("body").on("click","#btnReturnExchange", function(){
            var oidBill = $("#btnReturnExchange").data("oidbill");
            var btnRefresh = "&nbsp;<a id='resetExc' title='Reset' data-toggle='tooltip' data-placement='right' style='cursor: pointer; color:#e3e3e3'><i class='fa fa-refresh'></i></a>";
            var billNumber = "<span class='pull-right'>BILL NUMBER : <a>" + $("#btnReturnExchange").data("billnumber") + "</a></span>";
            getFormReturnExchange(oidBill);
            $("#openbill").modal("hide");
            $("#modalReturnExchange").modal(modalSetting2);
            $("#modalReturnExchange .modal-title").html("RETURN EXCHANGE " + btnRefresh + billNumber);
            $("#modalReturnExchange").modal("show");
        });

        $("#btnPayReturnExchange").click(function() {
            var totalReturn = 1 * $("#grandTotalExc").html().replace(/,/g , "");
            var totalExchange = 1 * $("#grandTotalExcPrice").html().replace(/,/g , "");
            var totalPayment = 1 * $("#grandTotalPayment").html().replace(/,/g , "");

            if (totalReturn > totalExchange) {
                alert("Nilai exchange harus sama atau lebih besar dari nilai return !");
                return false;
            }

            if (totalReturn > totalPayment) {
                alert("Nilai payment harus sama atau lebih besar dari nilai return !");
                return false;
            }

            if (totalExchange > totalPayment) {
                alert("Nilai payment harus sama atau lebih besar dari nilai exchange !");
                return false;
            }

            $("#btnPayReturnExchange").attr({"disabled":true});
            var data = $("#FORM_RETURN_EXCHANGE").serialize();
            data += "&CASH_CASHIER_ID=" + $("#cashCashierId").val();
            $.ajax({
                type    : "POST",
                data    : data,
                url	    : "<%= approot %>/TransactionCashierHandler",
                dataType : "json",
                cache   : false,
                success : function(data) {
                    
                    if (data.FRM_FIELD_NUMBER_OF_ERROR !== "0") {
                        alert(data.FRM_FIELD_SHORT_MESSAGE);
                    } else {
                        $("#modalReturnExchange").modal("hide");
                        $("#openprintpreview").modal("show");
                        $("#CONTENT_PRINT_PREVIEW").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                        $("#CONTENT_PRINT").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                        window.print();
                    }
                    
                }, error : function(data){
                
                }
            }).done(function(data){
                $("#btnPayReturnExchange").removeAttr("disabled");
            });
        });

        $('[data-toggle="tooltip"]').tooltip();

        function refreshPanelCategory() {
            $(".panel_category").each(function() {
                if ($(this).hasClass("panel-primary")) {
                    $(this).removeClass("panel-primary");
                    $(this).addClass("panel-info");
                }
            });
        }

        function searchItemByCategory(showPage) {
            var data = {
                "iCommand"          : "<%= Command.NONE %>",
                "loadtype"          : "searchItemByCategory",
                "idCategory"        : $("#selectedCategory").val(),
                "idBillMain"        : $('#mainBillIdSource').val(),
                "approot"           : "<%= approot %>",
                "search"            : $("#searchItemImg").val(),
                "dataShow"          : $("#dataShow").val(),
                "showPage"          : showPage,
                "CASH_CASHIER_ID"   : $("#cashCashierId").val()
            };
            //alert(JSON.stringify(data));
            $("#CONTENT_GRID_ITEM_IMG").html("<div class='text-center'><i class='fa fa-refresh fa-spin'></i> Please wait...</div>");
            $.ajax({
                type    : "POST",
                data    : data,
                url     : "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                cache   : false,
                success : function(data) {
                    
                    $("#CONTENT_GRID_ITEM_IMG").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                    if ("<%= disableQty %>" === "1") {
                        $("#searchItemImg").val("");
                    }
                    $("#searchItemImg").focus();
                    
                    //CHECK IF DATA IS A RESULT FROM SEARCHING / SCANNING BARCODE
                    if (data.SEARCH_TEXT !== "") {
                        var itemFound = 0;
                        $("#CONTENT_GRID_ITEM_IMG .panel_item").each(function() {
                            itemFound++;
                        });
                        
                        //IF SEARCH RESULT ONLY ONE ITEM THEN AUTOMATIC SELECT
                        if (itemFound === 1) {
                            $("#CONTENT_GRID_ITEM_IMG .panel_item").trigger("click");
                        }
                    }
                    
                }, error : function(data){
                    
                }
            }).done(function(data){
            
            });
        }

        function getListBillItemGrid() {
            $("#CONTENT_LIST_BILL_DETAIL").html("<tr><td colspan='5' class='text-center'><i class='fa fa-refresh fa-spin'></i> Please wait...</td></tr>");
            $.ajax({
                type    : "POST",
                data    : {
                    "iCommand"  : "<%= Command.NONE %>",
                    "loadtype"  : "getListBillItemGrid",
                    "idBillMain": $('#mainBillIdSource').val()
                },
                url     : "<%= approot %>/TransactionCashierHandler",
                dataType: "json",
                cache   : false,
                success : function(data) {
                    
                    $("#CONTENT_LIST_BILL_DETAIL").html(data.<%= BillMainJSON.fieldNames[BillMainJSON.FLD_HTML]%>);
                    
                }, error : function(data){
                    
                }
            }).done(function(data){
            
            });
        }

        $("body").on("click",".panel_category", function(){
            if ($(this).hasClass("panel-primary")) {
                $(this).removeClass("panel-primary");
                $(this).addClass("panel-info");
                $("#selectedCategory").val(0);
            } else {
                refreshPanelCategory();
                $(this).removeClass("panel-info");
                $(this).addClass("panel-primary");
                $("#selectedCategory").val($(this).data('category'));
            }
            searchItemByCategory();
        });

        $("body").on("click",".panel_item", function(){
            var itemName = $(this).data("name");
            var itemOid = $(this).data("oid");
            var itemPrice = $(this).data("price");
            var canBuy = $(this).data("canbuy");
            if (canBuy === false) {
                var zindexParent = $("#modalItemListImage").css("z-index");
                var zindexChild = parseInt(zindexParent) + 1;
                $("#stockCheck").css({"z-index":zindexChild});
                $("#stockCheck").modal("show");
                $("#CONTENT_STOCK_TITLE").html(itemName);
                $("#modal-body-stock").html("<div class=''><p>Sorry, the stock was out/empty. You can't add this item.</p></div>");
            } else {
                if ("<%= disableQty %>" === "0") {
                    var package = $("#isPackage").val();
                    var zindexParent2 = $("#modalItemListImage").css("z-index");
                    var zindexChild2 = parseInt(zindexParent2) + 1;
                    $("#openadditem").css({"z-index":zindexChild2});
                    $("#openadditem").modal("show");
                    if (package === "0"){
                        $("#parentPackage").val("0");
                    }
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").val('');
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(itemOid);
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(itemName);
                    $("#CONTENT_ITEM_TITLE").html(itemName);
                } else {
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] %>").val('1');
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] %>").val(itemPrice);
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] %>").val(itemOid);
                    $("#FRM_NAME_BILL_DETAIL_ADD_ITEM #<%= FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] %>").val(itemName);
                    $("#CONTENT_ITEM_TITLE").html(itemName);
                    $("#<%= FrmBillDetail.FRM_NAME_BILL_DETAIL %>_ADD_ITEM #btnsaveitem").trigger("click");
                }
            }
        });

        $("body").on("keyup","#searchItemImg", function(e){
            if (e.keyCode === 13){
                searchItemByCategory();
            } else if (e.keyCode === 27){
                $("#modalItemListImage").modal("hide");
            }
        });

        $("body").on("click","#btnSearchItemImg", function(){
            searchItemByCategory();
        });

        $("body").on("click",".btn_pagination", function(){
            var showPage = 0;
            var type = $(this).data('type');
            var curentPage = $(this).data('page');
            var total = $("#totalPage").val();
            if (type === "first"){
                showPage = 1;
            }else if (type === "prev"){
                showPage = Number(curentPage)-1;
            }else if (type === "next"){
                showPage = Number(curentPage)+1;
            }else if (type === "last"){
                showPage = total;
            }
            searchItemByCategory(showPage);
        });

        $("#dataShow").change(function() {
            searchItemByCategory();
        });

        function showItemListWithPicture() {
            var searchFront = $("#searchBarcode").val();
            if (searchFront !== "") {
                $("#searchItemImg").val(searchFront);
            } else {
                $("#searchItemImg").val("");
            }
            $("#dataShow").val(8);
            $("#modalItemListImage").modal(modalSetting2);
            $("#modalItemListImage").modal("show");
            getListBillItemGrid();
            searchItemByCategory();
        }
        
        function getSalesPerson() {
            $.ajax({
                type    : "POST",
                data    : {
                    "command"  : "<%= Command.NONE %>",
                    "FRM_FIELD_DATA_FOR"  : "getUserSalesPerson",
                    "FRM_FIELD_LOCATION_ID": $('#SALES_LOCATION').val()
                },
                url     : "<%= approot %>/TransactionCashierHandlerNew",
                dataType: "json",
                cache   : false,
                success : function(data) {
                    
                    $("#<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_APP_USER_SALES_ID]%>").html(data.FRM_FIELD_HTML);
                    
                }, error : function(data){
                    
                }
            }).done(function(data){
            
            });
        }
        
        $('#SALES_LOCATION').change(function() {
            $("#<%=FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SALES_CODE]%>").val("");
            getSalesPerson();
        });
        
        $('#addCustomer').submit(function() {
            $.ajax({
                type    : "POST",
                data    : $(this).serialize(),
                url     : "<%= approot %>/TransactionCashierHandlerNew",
                dataType: "json",
                cache   : false,
                success : function(data) {
                    if(data.FRM_FIELD_RETURN_ERROR == 0){
                        var oid = data.RETURN_OID_CONTACT;
                        var name = $('#addCustomer #custName').val();
                        var phone = $('#addCustomer #custPhone').val();
                        var addr = $('#addCustomer #custAddress').val();
                        $("#<%= FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] %>").val(oid);
                        $("#employee").val(name);
                        $("#<%= FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE] %>").val(phone);
                        $("#customerAddress").val(addr);
                        $('#modal-newcustomer').modal("hide");
                    } else {
                        alert(data.FRM_FIELD_RETURN_MESSAGE);
                    }
                    
                }, error : function(data){
                    alert(JSON.stringify(data));
                }
            }).done(function(data){
            
            });
            return false;
        });
        
        var map = null;
        var myMarker;
        var myLatlng;

        function initializeGMap() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function (position) {
                    var lat = position.coords.latitude;
                    var lng = position.coords.longitude;
                    document.getElementById("latitude").value = lat;
                    document.getElementById("longitude").value = lng;
                    myLatlng = new google.maps.LatLng(lat, lng);

                    var myOptions = {
                        zoom: 12,
                        zoomControl: true,
                        center: myLatlng,
                        mapTypeId: google.maps.MapTypeId.ROADMAP
                    };

                    map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

                    myMarker = new google.maps.Marker({
                        position: myLatlng,
                        draggable: true
                    });

                    google.maps.event.addListener(myMarker, 'dragend', function (marker) {
                        var latLng = marker.latLng;
                        var currentLatitude = latLng.lat();
                        var currentLongitude = latLng.lng();
                        document.getElementById("latitude").value = currentLatitude;
                        document.getElementById("longitude").value = currentLongitude;
                    });

                    myMarker.setMap(map);
                });
            }
        }

        var searchVoucher = function(elementId, getPaymentType = "#MASTER_FRM_FIELD_PAY_TYPE"){
            getPaymentType = (typeof getPaymentType !== 'undefined') ? getPaymentType : "#MASTER_FRM_FIELD_PAY_TYPE";
            $("#getPaymentType").val(getPaymentType);
            $(elementId).click(function(){
                var source = $(this).data("source");
                $("#sourceSelectVoucher").val(source);
                loadVoucher(true,0);  
            });
        };

    });
</script>