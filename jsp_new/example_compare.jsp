/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.ajax.cashier;

import com.dimata.cashierweb.ajax.print_template.EntDataList;
import com.dimata.cashierweb.ajax.print_template.EntPrintData;
import com.dimata.cashierweb.ajax.print_template.PrintDynamicTemplate;
import com.dimata.cashierweb.entity.admin.AppUser;
import com.dimata.cashierweb.entity.admin.PstAppUser;
import com.dimata.cashierweb.entity.cashier.printtemplate.PrintTemplate;
import com.dimata.cashierweb.entity.cashier.transaction.BillMainCostum;
import com.dimata.cashierweb.entity.cashier.transaction.BillMainJSON;
import com.dimata.cashierweb.entity.cashier.transaction.CustomCashCreditCard;
import com.dimata.cashierweb.entity.cashier.transaction.HotelRoom;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomCashCreditCard;
import com.dimata.cashierweb.entity.cashier.transaction.PstHotelRoom;
import com.dimata.cashierweb.entity.cashier.transaction.PstQueensLocation;
import com.dimata.cashierweb.entity.cashier.transaction.PstReservation;
import com.dimata.cashierweb.entity.cashier.transaction.QueensLocation;
import com.dimata.cashierweb.entity.cashier.transaction.Reservation;
import com.dimata.cashierweb.entity.masterdata.Category;
import com.dimata.cashierweb.entity.masterdata.Material;
import com.dimata.cashierweb.entity.masterdata.PriceTypeMapping;
import com.dimata.cashierweb.entity.masterdata.PstCategory;
import com.dimata.cashierweb.entity.masterdata.PstMaterial;
import com.dimata.cashierweb.entity.masterdata.PstMemberRegistrationHistory;
import com.dimata.cashierweb.entity.masterdata.PstPriceTypeMapping;
import com.dimata.cashierweb.entity.masterdata.PstRoom;
import com.dimata.cashierweb.entity.masterdata.PstSales;
import com.dimata.cashierweb.entity.masterdata.PstTableRoom;
import com.dimata.cashierweb.entity.masterdata.Room;
import com.dimata.cashierweb.entity.masterdata.Sales;
import com.dimata.cashierweb.entity.masterdata.TableRoom;
import com.dimata.cashierweb.form.admin.FrmAppUser;
import com.dimata.cashierweb.form.cashier.CtrlBillMain;
import com.dimata.cashierweb.form.cashier.CtrlCustomBillDetail;
import com.dimata.cashierweb.form.cashier.CtrlCustomPayment;
import com.dimata.cashierweb.form.cashier.CtrlQueensContactList;
import com.dimata.cashierweb.form.cashier.CtrlReturn;
import com.dimata.cashierweb.session.cashier.SessUserCashierSession;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.logger.LogSysHistory;
import com.dimata.common.entity.logger.PstLogSysHistory;
import com.dimata.common.entity.payment.CurrencyType;
import com.dimata.common.entity.payment.PaymentSystem;
import com.dimata.common.entity.payment.PstCurrencyType;
import com.dimata.common.entity.payment.PstPaymentSystem;
import com.dimata.common.entity.system.PstSystemProperty;
import com.dimata.gui.jsp.ControlCombo;
import com.dimata.gui.jsp.ControlList;
import com.dimata.hanoman.entity.masterdata.Contact;
import com.dimata.hanoman.entity.masterdata.ContactClass;
import com.dimata.hanoman.entity.masterdata.CustomePackageSchedule;
import com.dimata.hanoman.entity.masterdata.PstContact;
import com.dimata.hanoman.entity.masterdata.PstContactClass;
import com.dimata.hanoman.entity.masterdata.PstCustomePackBilling;
import com.dimata.hanoman.entity.masterdata.PstCustomePackageSchedule;
import com.dimata.hanoman.form.masterdata.FrmContactClass;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.pos.entity.balance.Balance;
import com.dimata.pos.entity.balance.CashCashier;
import com.dimata.pos.entity.balance.OpeningCashCashier;
import com.dimata.pos.entity.balance.PstBalance;
import com.dimata.pos.entity.balance.PstCashCashier;
import com.dimata.pos.entity.billing.BillMain;
import com.dimata.pos.entity.billing.Billdetail;
import com.dimata.pos.entity.billing.PstBillDetail;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.entity.masterCashier.CashMaster;
import com.dimata.pos.entity.masterCashier.PstCashMaster;
import com.dimata.pos.entity.payment.CashPayments;
import com.dimata.pos.entity.payment.PstCashPayment;
import com.dimata.pos.entity.payment.PstCashReturn;
import com.dimata.pos.form.balance.CtrlCloseCashCashier;
import com.dimata.pos.form.billing.CtrlBillDetail;
import com.dimata.pos.form.billing.FrmBillDetail;
import com.dimata.pos.form.billing.FrmBillMain;
import com.dimata.pos.form.payment.FrmCashCreditCard;
import com.dimata.pos.form.payment.FrmCashPayment;
import com.dimata.posbo.entity.masterdata.PersonalDiscount;
import com.dimata.posbo.entity.masterdata.PstPersonalDiscount;
import com.dimata.posbo.entity.masterdata.PstVoucher;
import com.dimata.posbo.entity.masterdata.Voucher;
import com.dimata.posbo.entity.warehouse.MatCosting;
import com.dimata.posbo.entity.warehouse.PstMatCosting;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

/**
 *
 * @author Ardiadi
 */
public class TransactionCashierHandler extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");
	
	
//////////////////////
/////////GET PARAMETER
	
	//INT TYPE
	int iCommand = FRMQueryString.requestCommand(request);
	int transactionType = FRMQueryString.requestInt(request, "transaction_type");
	int design = 0;
	int taxInc = FRMQueryString.requestInt(request, BillMainJSON.fieldNames[BillMainJSON.FLD_TAX_INC]);
	int statusOrder = 0;
	
	//LONG TYPE
	long cashier = FRMQueryString.requestLong(request, "cashier");
	long oid = FRMQueryString.requestLong(request, FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]);
	long paymentType = FRMQueryString.requestLong(request, FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE]);
	long oidCashier = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]);
	long oidLocationParent = FRMQueryString.requestLong(request, "LOCATION_ID");
	long oidRoom = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID]);
	long oidMultiLocation = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]);
	long reservationId = FRMQueryString.requestLong(request,FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_RESERVATION_ID]);
	
	//STRING TYPE
	String oidData = FRMQueryString.requestString(request, FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]);
        long oidDataSelectedGuest = FRMQueryString.requestLong(request, FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]+"_GUEST");
	String loadtype = FRMQueryString.requestString(request, "loadtype");
	String cashierString = FRMQueryString.requestString(request, "cashier");
	String voucherCode = FRMQueryString.requestString(request, "VOUCHER_CODE");
	String ccName = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]);
	String ccNo = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]);
	String ccBank = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]);
	String ccValid = FRMQueryString.requestString(request, FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]);
	String spvId = FRMQueryString.requestString(request, FrmAppUser.fieldNames[FrmAppUser.FRM_LOGIN_ID]);
	String spvPassword = FRMQueryString.requestString(request, FrmAppUser.fieldNames[FrmAppUser.FRM_PASSWORD]);
	String searchtype = FRMQueryString.requestString(request, "searchType");
	String itemsearch = FRMQueryString.requestString(request, FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME]);
	String multilocation = PstSystemProperty.getValueByName("OUTLET_MULTILOCATION");
	String defaultOidLocation = PstSystemProperty.getValueByName("OUTLET_DEFAULT_LOCATION");
	String defaultOidPriceType = PstSystemProperty.getValueByName("OUTLET_DEFAULT_PRICE_TYPE");
        String cashierItemErrorCantDelete = PstSystemProperty.getValueByName("CASHIER_ITEM_ERROR_CANT_DELETE");
        String cashierPrintOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");
        
	
        String signaturelokasi = FRMQueryString.requestString(request, "signaturelokasi");
        
        String defaultPathSignature = signaturelokasi;//PstSystemProperty.getValueByName("CASHIER_PATH_SIGNATURE");
        String oidMember = FRMQueryString.requestString(request, "member");
	
	//DOUBLE TYPE
	double costumBalance = FRMQueryString.requestDouble(request, BillMainJSON.fieldNames[BillMainJSON.FLD_COSTUM_BALANCE]);
	double getBalance = FRMQueryString.requestDouble(request, BillMainJSON.fieldNames[BillMainJSON.FLD_BALANCE_VALUE]);
	double getDiscountPct = FRMQueryString.requestDouble(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_DISC_PCT]);
	double getDiscountVal = FRMQueryString.requestDouble(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_DISC_GLOBAL]);
	double ccCharge = FRMQueryString.requestDouble(request, "creditcardcharge");
	double payAmount = FRMQueryString.requestDouble(request, FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]);
	
	//OBJECT
	HttpSession session = request.getSession();
	JSONObject returnData = new JSONObject();
	BillMainJSON billMainJSON = new BillMainJSON();
	Vector listSpv = new Vector(1,1);
	
	
	//SESSION
	boolean isLoggedIn = false;
	
	SessUserCashierSession userCashier = (SessUserCashierSession) session.getValue(SessUserCashierSession.HTTP_SESSION_CASHIER);
	String loginId = "";
	long userId = 0;
	try{
	    if(userCashier==null){
		userCashier= new SessUserCashierSession();
	    }else{
		if(userCashier.isLoggedIn()==true){
		    isLoggedIn  = true;
		    AppUser appUser = userCashier.getAppUser();
		    loginId = appUser.getLoginId();
		    userId = appUser.getOID();
		}
	    }
	}catch (Exception exc){
	    System.out.println(" >>> Exception during check login");
	}
	try {
	    design = Integer.parseInt((String) PstSystemProperty.getValueByName("DESIGN_MATERIAL_FOR"));
	    listSpv = PstAppUser.listPartObj(0, 0, 
			    PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]+"='"+spvId+"' "
			    + "AND "+PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD]+"='"+spvPassword+"' "
			    + "AND "+PstAppUser.fieldNames[PstAppUser.FLD_USER_GROUP_NEW]+"='1'", "");
	} catch (Exception e) {
	    design = 0;
	}
	
	
////////////////////////////
/////////DEFAULT RETURN DATA
	
	//STRING TYPE
	String html = "";
	String displayTotal = "";
        String shortMessage = "";
	
	//DOUBLE TYPE
	double balanceValue = 0;
	double creditCardCharge = 0;
	double paidValue = 0;
        String whereSql2="";
	if(iCommand == Command.NONE){
            try {
////////////////////////////////
//////////////////LOAD BILL LIST
		if(loadtype.equals("loadbill")){
		    //balanceValue = getBalance;
                    
                    int isPack = 0;
                    String packIs = "0";
                    //mengecek apakah pada bill detail ada yang mengandung package atau tidak
                    String whereCheckPack = ""
                        + " billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+oid+"' "
                        + " AND billDetail."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"<>'0'";
                    
                    isPack = PstCustomBillMain.getTotalPackage(0, 0, whereCheckPack, "");
                    if (isPack>0){
                        packIs = "1";
                    }
                    
                    String whereSql = ""
                        + " billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+oid+"' "
                        + " AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                        + " AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
                        + " AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
                        + " AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'";
                    
                    if (isPack>0){
                        whereSql2 = ""
                            + " AND billDetail."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"=0";
                    }
                    
                    String whereSql3 = "";
                    whereSql3 = ""
                        + " AND billDetail."+PstBillDetail.fieldNames[PstBillDetail.FLD_STATUS]+" <> '3'";
                    
		    Vector listBill = PstCustomBillMain.listItemOpenBill(0, 0, whereSql, "");
                    Vector listBill2 = PstCustomBillMain.listItemOpenBill(0, 0, whereSql + whereSql2, "");
                    Vector listBill3 =  PstCustomBillMain.listItemOpenBill(0, 0, whereSql + whereSql2 + whereSql3, "");
		    BillMain billMain = new BillMain();
		    try{
			billMain = PstBillMain.fetchExc(oid);
		    }catch(Exception ex){

		    }
                    
                    String guestNameWrite="";

                    if (billMain.getCustomerId()!=0 && billMain.getGuestName().length()==0){
                       Contact entContact = new Contact();
                        try {
                            entContact = PstContact.fetchExc(billMain.getCustomerId());
                        } catch (Exception e) {
                        }
                        if (entContact.getPersonName().length()==0){
                            guestNameWrite = entContact.getCompName();
                        }else{
                            guestNameWrite = entContact.getPersonName() + " " + entContact.getPersonLastname();
                        }
                    }else{
                        guestNameWrite = billMain.getGuestName();
                    }
                    
                    String tableWrite = "";
                    if (billMain.getTableId()!=0){
                        TableRoom entTableRoom = new TableRoom();
                        entTableRoom = PstTableRoom.fetchExc(billMain.getTableId());
                        if (entTableRoom.getTableNumber().length()>0){
                            tableWrite = ""+entTableRoom.getTableNumber()+"";
                        }
                    }
                    
                    statusOrder = listBill3.size();

		    html = ""
		    +"<div class='row'>"
			+ "<div class='col-md-12'>"
			    + "<div class='col-md-6'>"
				+ "<span>"+listBill.size()+" Items in Transaction </span>";
                                if (guestNameWrite.length()>0){
                                    html +=""
                                    +"of <span class='label label-warning' style='font-family: Century Gothic; font-size: 16px;'>"+guestNameWrite+"</span>";
                                }
                                if (tableWrite.length()>0){
                                    html +=""
                                    +"&nbsp; Table <span class='label label-danger' style='font-family: Century Gothic; font-size: 16px;'>"+tableWrite+"  "+billMain.getTableName()+"</span>";
                                }
                                
                                html +=""
			    + "</div>"
                            
			    + "<div class='col-md-6 pull-right'>"
				+ "<h4 style='text-align:right;'>Transaction #"+billMain.getInvoiceNo()+"</h4>"
			    + "</div>"
			+ "</div>"
		    + "</div>"
		    + "<div class='row'>"
			+ "<div class='col-md-12' id='mainTransaction'>";
			    html+=drawList(iCommand, listBill2, oid, null,packIs);
			html+="</div>"
		    + "</div>";
                   
////////////////// MULTI PAYMENT
                }else if (loadtype.equals("loadlistmultipayment")){
                    int countMultiPayment=0;
                    
                    try {
                        countMultiPayment = FRMQueryString.requestInt(request, "countMultiPayment");
                    } catch (Exception e) {
                        countMultiPayment=0;
                    }
                    String type = FRMQueryString.requestString(request, "type");
                                       
                    if (countMultiPayment==0){
                        // SAMA SEKALI BELUM ADA PAYMENT
                        if (type.equals("purelist")){
                            html += ""
                            + "<input type='hidden' id='countMultiPayment' name='countMultiPayment' value='"+countMultiPayment+"'>"
                            + "<div class='row'>"
                                + "<div class='col-md-12'>"
                                    + "<table class='table table-bordered table-striped'>"
                                        + "<tbody>"
                                            + "<tr>"
                                                + "<th>No</th>"
                                                + "<th>Payment Type</th>"
                                                + "<th>Amount</th>"
                                                + "<th>Action</th>"
                                            + "</tr>"
                                            + "<tr>"
                                                + "<td colspan='4'><center>No List Payment</center></td>"
                                            + "</tr>"
                                        + "</tbody>"                                    
                                    + "</table>"
                                + "</div>"
                            + "</div>";
                        }else if (type.equals("addNew")){
                            int nexCount = countMultiPayment + 1;
                            //GET REQUEST DATA
                            long cashBillMainId = FRMQueryString.requestLong(request, "multiPayCashBillMainId");
                            int masterFieldPayType = FRMQueryString.requestInt(request, "MASTER_FRM_FIELD_PAY_TYPE_MULTI");
                            long payTypeMulti = FRMQueryString.requestLong(request, "FRM_FIELD_PAY_TYPE_MULTI");
                            double totalAmount = FRMQueryString.requestDouble(request, "totalAmount");
                            String ccNameMulti = "";
                            String ccNumberMulti ="";
                            String debitBankNameMulti ="";
                            String expiredDateMulti="";
                            double payAmountMulti = 0;
                            PaymentSystem paymentSystemMulti = new PaymentSystem();
                            long voucherId = 0;
                            
                            
                            //SEMUA DENGAN TRY CATCH
                            try {
                               ccNameMulti =  FRMQueryString.requestString(request, "FRM_FIELD_CC_NAME_MULTI");
                            } catch (Exception e) {
                            }
                            try {
                              ccNumberMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NUMBER_MULTI");  
                            } catch (Exception e) {
                            }
                            try {
                               debitBankNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_DEBIT_BANK_NAME_MULTI");  
                            } catch (Exception e) {
                            }
                            try {
                               expiredDateMulti = FRMQueryString.requestString(request, "FRM_FIELD_EXPIRED_DATE_MULTI");  
                            } catch (Exception e) {
                            }
                            try {
                               payAmountMulti =  FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI");
                            } catch (Exception e) {
                            }
                            try {
                                paymentSystemMulti = PstPaymentSystem.fetchExc(payTypeMulti);
                            } catch (Exception e) {
                            }
                            
                            try{
                                voucherId = FRMQueryString.requestLong(request, "FRM_FIELD_VOUCHER_ID_MULTI");
                            }catch(Exception e){
                            
                            }
                            
                           totalAmount=  totalAmount- payAmountMulti;
                            html += ""
                            + "<input type='hidden' id='countMultiPayment' name='countMultiPayment' value='"+nexCount+"'>"
                            + "<div class='row'>"
                                + "<div class='col-md-12'>"
                                    + "<table class='table table-bordered table-striped'>"
                                        + "<tbody>"
                                            + "<tr>"
                                                + "<th>No</th>"
                                                + "<th>Payment Type</th>"
                                                + "<th>Amount</th>"
                                                + "<th>Action</th>"
                                            + "</tr>"
                                            + "<tr>"
                                                + "<input value='"+masterFieldPayType+"' type='hidden' id='MASTER_FRM_FIELD_PAY_TYPE_MULTI_1' name='MASTER_FRM_FIELD_PAY_TYPE_MULTI_1'>"
                                                + "<input value='"+payTypeMulti+"' type='hidden' id='FRM_FIELD_PAY_TYPE_MULTI_1' name='FRM_FIELD_PAY_TYPE_MULTI_1'>"
                                                + "<input value='"+ccNameMulti+"' type='hidden' id='FRM_FIELD_CC_NAME_MULTI_1' name='FRM_FIELD_CC_NAME_MULTI_1'>"
                                                + "<input value='"+ccNumberMulti+"' type='hidden' id='FRM_FIELD_CC_NUMBER_MULTI_1' name='FRM_FIELD_CC_NUMBER_MULTI_1'>"
                                                + "<input value='"+debitBankNameMulti+"' type='hidden' id='FRM_FIELD_DEBIT_BANK_NAME_MULTI_1' name='FRM_FIELD_DEBIT_BANK_NAME_MULTI_1'>"
                                                + "<input value='"+expiredDateMulti+"' type='hidden' id='FRM_FIELD_EXPIRED_DATE_MULTI_1' name='FRM_FIELD_EXPIRED_DATE_MULTI_1'>"
                                                + "<input value='"+payAmountMulti+"' type='hidden' id='FRM_FIELD_PAY_AMOUNT_MULTI_1' name='FRM_FIELD_PAY_AMOUNT_MULTI_1'>"   
                                                + "<input value='"+voucherId+"' type='hidden' id='FRM_FIELD_VOUCHER_ID_MULTI_1' name='FRM_FIELD_VOUCHER_ID_MULTI_1'>"  
                                                + "<td>"+nexCount+"</td>"
                                                + "<td>"+paymentSystemMulti.getPaymentSystem()+"</td>"
                                                + "<td style='text-align:right;'>"+Formater.formatNumber(payAmountMulti,"#,###.##")+"</td>"
                                                + "<td><button type='button' data-index='1' class='btn btn-danger deleteMultiPayment'><i class='fa fa-close'></i></button></td>"
                                            + "</tr>"
                                        + "</tbody>"                                    
                                    + "</table>"
                                + "</div>"
                            + "</div>";
                            
                            displayTotal = Formater.formatNumber(totalAmount, "#,###.##");
                            costumBalance = totalAmount;
                        }
                        
                    }else{
                        if (type.equals("purelist")){
                            html += ""
                            + "<input type='hidden' id='countMultiPayment' name='countMultiPayment' value='"+countMultiPayment+"'>"
                            + "<div class='row'>"
                                + "<div class='col-md-12'>"
                                    + "<table class='table table-bordered table-striped'>"
                                        + "<tbody>"
                                            + "<tr>"
                                                + "<th>No</th>"
                                                + "<th>Payment Type</th>"
                                                + "<th>Amount</th>"
                                                + "<th>Action</th>"
                                            + "</tr>";
                                            for (int i=1;i<=countMultiPayment;i++){
                                                long cashBillMainId = FRMQueryString.requestLong(request, "multiPayCashBillMainId");
                                                int masterFieldPayType = FRMQueryString.requestInt(request, "MASTER_FRM_FIELD_PAY_TYPE_MULTI");
                                                long payTypeMulti = FRMQueryString.requestLong(request, "FRM_FIELD_PAY_TYPE_MULTI");
                                                String ccNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NAME_MULTI");
                                                String ccNumberMulti =FRMQueryString.requestString(request, "FRM_FIELD_CC_NUMBER_MULTI"); 
                                                String debitBankNameMulti =FRMQueryString.requestString(request, "FRM_FIELD_DEBIT_BANK_NAME_MULTI");  
                                                String expiredDateMulti=FRMQueryString.requestString(request, "FRM_FIELD_EXPIRED_DATE_MULTI");
                                                double payAmountMulti = FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI");
                                                PaymentSystem paymentSystemMulti = new PaymentSystem();
                       
                                                try {
                                                    paymentSystemMulti = PstPaymentSystem.fetchExc(payTypeMulti);
                                                } catch (Exception e) {
                                                }
                                                
                                                html+=""
                                                + "<tr>"
                                                    + "<input value='"+masterFieldPayType+"' type='hidden' id='MASTER_FRM_FIELD_PAY_TYPE_MULTI_"+i+"' name='MASTER_FRM_FIELD_PAY_TYPE_MULTI_"+i+"'>"
                                                    + "<input value='"+payTypeMulti+"' type='hidden' id='FRM_FIELD_PAY_TYPE_MULTI_"+i+"' name='FRM_FIELD_PAY_TYPE_MULTI_"+i+"'>"
                                                    + "<input value='"+ccNameMulti+"' type='hidden' id='FRM_FIELD_CC_NAME_MULTI_"+i+"' name='FRM_FIELD_CC_NAME_MULTI_"+i+"'>"
                                                    + "<input value='"+ccNumberMulti+"' type='hidden' id='FRM_FIELD_CC_NUMBER_MULTI_"+i+"' name='FRM_FIELD_CC_NUMBER_MULTI_"+i+"'>"
                                                    + "<input value='"+debitBankNameMulti+"' type='hidden' id='FRM_FIELD_DEBIT_BANK_NAME_MULTI_"+i+"' name='FRM_FIELD_DEBIT_BANK_NAME_MULTI_"+i+"'>"
                                                    + "<input value='"+expiredDateMulti+"' type='hidden' id='FRM_FIELD_EXPIRED_DATE_MULTI_"+i+"' name='FRM_FIELD_EXPIRED_DATE_MULTI_"+i+"'>"
                                                    + "<input value='"+payAmountMulti+"' type='hidden' id='FRM_FIELD_PAY_AMOUNT_MULTI_"+i+"' name='FRM_FIELD_PAY_AMOUNT_MULTI_"+i+"'>"                                               
                                                    + "<td>"+i+"</td>"
                                                    + "<td>"+paymentSystemMulti.getPaymentSystem()+"</td>"
                                                    + "<td style='text-align:right;'>"+Formater.formatNumber(payAmountMulti,"#,###.##")+"</td>"
                                                    + "<td><button type='button' data-index='"+i+"' class='btn btn-danger deleteMultiPayment'><i class='fa fa-close'></i></button></td>"
                                                + "</tr>";
                                            } 
               
                                        html+= ""                                        
                                        + "</tbody>"                                    
                                    + "</table>"
                                + "</div>"
                            + "</div>";
                        }else if (type.equals("addNew")){
                            int nexCount = countMultiPayment + 1;
                            double totalAmount = FRMQueryString.requestDouble(request, "totalAmount");
                            double remainsAmount=0;
                            html += ""
                            + "<input type='hidden' id='countMultiPayment' name='countMultiPayment' value='"+nexCount+"'>"
                            + "<div class='row'>"
                                + "<div class='col-md-12'>"
                                    + "<table class='table table-bordered table-striped'>"
                                        + "<tbody>"
                                            + "<tr>"
                                                + "<th>No</th>"
                                                + "<th>Payment Type</th>"
                                                + "<th>Amount</th>"
                                                + "<th>Action</th>"
                                            + "</tr>";
                                            for (int i=1;i<=countMultiPayment;i++){
                                                long cashBillMainId = FRMQueryString.requestLong(request, "multiPayCashBillMainId");
                                                int masterFieldPayType = FRMQueryString.requestInt(request, "MASTER_FRM_FIELD_PAY_TYPE_MULTI_"+i+"");
                                                long payTypeMulti = FRMQueryString.requestLong(request, "FRM_FIELD_PAY_TYPE_MULTI_"+i+"");
                                                String ccNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NAME_MULTI_"+i+"");
                                                String ccNumberMulti =FRMQueryString.requestString(request, "FRM_FIELD_CC_NUMBER_MULTI_"+i+""); 
                                                String debitBankNameMulti =FRMQueryString.requestString(request, "FRM_FIELD_DEBIT_BANK_NAME_MULTI_"+i+"");  
                                                String expiredDateMulti=FRMQueryString.requestString(request, "FRM_FIELD_EXPIRED_DATE_MULTI_"+i+"");
                                                double payAmountMulti = FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI_"+i+"");
                                                PaymentSystem paymentSystemMulti = new PaymentSystem();
                                                long voucherId = FRMQueryString.requestLong(request, "FRM_FIELD_VOUCHER_ID_MULTI_"+i+"");
                                                        
                                                try {
                                                    paymentSystemMulti = PstPaymentSystem.fetchExc(payTypeMulti);
                                                } catch (Exception e) {
                                                }
                                                
                                                totalAmount=  totalAmount- payAmountMulti; 
                                                
                                                html+=""
                                                + "<tr>"
                                                    + "<input value='"+masterFieldPayType+"' type='hidden' id='MASTER_FRM_FIELD_PAY_TYPE_MULTI_"+i+"' name='MASTER_FRM_FIELD_PAY_TYPE_MULTI_"+i+"'>"
                                                    + "<input value='"+payTypeMulti+"' type='hidden' id='FRM_FIELD_PAY_TYPE_MULTI_"+i+"' name='FRM_FIELD_PAY_TYPE_MULTI_"+i+"'>"
                                                    + "<input value='"+ccNameMulti+"' type='hidden' id='FRM_FIELD_CC_NAME_MULTI_"+i+"' name='FRM_FIELD_CC_NAME_MULTI_"+i+"'>"
                                                    + "<input value='"+ccNumberMulti+"' type='hidden' id='FRM_FIELD_CC_NUMBER_MULTI_"+i+"' name='FRM_FIELD_CC_NUMBER_MULTI_"+i+"'>"
                                                    + "<input value='"+debitBankNameMulti+"' type='hidden' id='FRM_FIELD_DEBIT_BANK_NAME_MULTI_"+i+"' name='FRM_FIELD_DEBIT_BANK_NAME_MULTI_"+i+"'>"
                                                    + "<input value='"+expiredDateMulti+"' type='hidden' id='FRM_FIELD_EXPIRED_DATE_MULTI_"+i+"' name='FRM_FIELD_EXPIRED_DATE_MULTI_"+i+"'>"
                                                    + "<input value='"+payAmountMulti+"' type='hidden' id='FRM_FIELD_PAY_AMOUNT_MULTI_"+i+"' name='FRM_FIELD_PAY_AMOUNT_MULTI_"+i+"'>"   
                                                    + "<input value='"+voucherId+"' type='hidden' id='FRM_FIELD_VOUCHER_ID_MULTI_"+i+"' name='FRM_FIELD_VOUCHER_ID_MULTI_"+i+"'>"   
                                                    + "<td>"+i+"</td>"
                                                    + "<td>"+paymentSystemMulti.getPaymentSystem()+"</td>"
                                                    + "<td style='text-align:right;'>"+Formater.formatNumber(payAmountMulti,"#,###.##")+"</td>"
                                                    + "<td><button type='button' data-index='"+i+"' class='btn btn-danger deleteMultiPayment'><i class='fa fa-close'></i></button></td>"
                                                + "</tr>";
                                            } 
                                            
                                            //GET REQUEST DATA
                                            long cashBillMainId = FRMQueryString.requestLong(request, "multiPayCashBillMainId");
                                            int masterFieldPayType = FRMQueryString.requestInt(request, "MASTER_FRM_FIELD_PAY_TYPE_MULTI");
                                            long payTypeMulti = FRMQueryString.requestLong(request, "FRM_FIELD_PAY_TYPE_MULTI");
                                            String ccNameMulti = "";
                                            String ccNumberMulti ="";
                                            String debitBankNameMulti ="";
                                            String expiredDateMulti="";
                                            double payAmountMulti = 0;
                                            PaymentSystem paymentSystemMulti = new PaymentSystem();
                                            long voucherId =0;
                                            //SEMUA DENGAN TRY CATCH
                                            try {
                                               ccNameMulti =  FRMQueryString.requestString(request, "FRM_FIELD_CC_NAME_MULTI");
                                            } catch (Exception e) {
                                            }
                                            try {
                                              ccNumberMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NUMBER_MULTI");  
                                            } catch (Exception e) {
                                            }
                                            try {
                                               debitBankNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_DEBIT_BANK_NAME_MULTI");  
                                            } catch (Exception e) {
                                            }
                                            try {
                                               expiredDateMulti = FRMQueryString.requestString(request, "FRM_FIELD_EXPIRED_DATE_MULTI");  
                                            } catch (Exception e) {
                                            }
                                            try {
                                               payAmountMulti =  FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI");
                                            } catch (Exception e) {
                                            }
                                            try {
                                                paymentSystemMulti = PstPaymentSystem.fetchExc(payTypeMulti);
                                            } catch (Exception e) {
                                            }
                                            try{
                                                voucherId = FRMQueryString.requestLong(request, "FRM_FIELD_VOUCHER_ID_MULTI");
                                            }catch(Exception e){

                                            }
                                            totalAmount=  totalAmount- payAmountMulti; 
                                            
                                            html+=""
                                            + "<tr>"
                                                + "<input value='"+masterFieldPayType+"' type='hidden' id='MASTER_FRM_FIELD_PAY_TYPE_MULTI_"+nexCount+"' name='MASTER_FRM_FIELD_PAY_TYPE_MULTI_"+nexCount+"'>"
                                                + "<input value='"+payTypeMulti+"' type='hidden' id='FRM_FIELD_PAY_TYPE_MULTI_"+nexCount+"' name='FRM_FIELD_PAY_TYPE_MULTI_"+nexCount+"'>"
                                                + "<input value='"+ccNameMulti+"' type='hidden' id='FRM_FIELD_CC_NAME_MULTI_"+nexCount+"' name='FRM_FIELD_CC_NAME_MULTI_"+nexCount+"'>"
                                                + "<input value='"+ccNumberMulti+"' type='hidden' id='FRM_FIELD_CC_NUMBER_MULTI_"+nexCount+"' name='FRM_FIELD_CC_NUMBER_MULTI_"+nexCount+"'>"
                                                + "<input value='"+debitBankNameMulti+"' type='hidden' id='FRM_FIELD_DEBIT_BANK_NAME_MULTI_"+nexCount+"' name='FRM_FIELD_DEBIT_BANK_NAME_MULTI_"+nexCount+"'>"
                                                + "<input value='"+expiredDateMulti+"' type='hidden' id='FRM_FIELD_EXPIRED_DATE_MULTI_"+nexCount+"' name='FRM_FIELD_EXPIRED_DATE_MULTI_"+nexCount+"'>"
                                                + "<input value='"+payAmountMulti+"' type='hidden' id='FRM_FIELD_PAY_AMOUNT_MULTI_"+nexCount+"' name='FRM_FIELD_PAY_AMOUNT_MULTI_"+nexCount+"'>"  
                                                + "<input value='"+voucherId+"' type='hidden' id='FRM_FIELD_VOUCHER_ID_MULTI_"+nexCount+"' name='FRM_FIELD_VOUCHER_ID_MULTI_"+nexCount+"'>"   
                                                + "<td>"+nexCount+"</td>"
                                                + "<td>"+paymentSystemMulti.getPaymentSystem()+"</td>"
                                                + "<td style='text-align:right;'>"+Formater.formatNumber(payAmountMulti,"#,###.##")+"</td>"
                                                + "<td><button type='button' data-index='"+nexCount+"' class='btn btn-danger deleteMultiPayment'><i class='fa fa-close'></i></button></td>"
                                            + "</tr>";
                                        html+= ""                                        
                                        + "</tbody>"                                    
                                    + "</table>"
                                + "</div>"
                            + "</div>";
                            displayTotal = Formater.formatNumber(totalAmount, "#,###.##");      
                            costumBalance = totalAmount;
                        }else if (type.equals("deleteOnce")){
                            int index = FRMQueryString.requestInt(request, "index");
                            int newCount = countMultiPayment -1;
                             double totalAmount = FRMQueryString.requestDouble(request, "totalAmount");
                            int j = 1;
                            html += ""
                            + "<input type='hidden' id='countMultiPayment' name='countMultiPayment' value='"+newCount+"'>"
                            + "<div class='row'>"
                                + "<div class='col-md-12'>"
                                    + "<table class='table table-bordered table-striped'>"
                                        + "<tbody>"
                                            + "<tr>"
                                                + "<th>No</th>"
                                                + "<th>Payment Type</th>"
                                                + "<th>Amount</th>"
                                                + "<th>Action</th>"
                                            + "</tr>";
                                            for (int i=1;i<=countMultiPayment;i++){
                                                if (i!=index){
                                                    long cashBillMainId = FRMQueryString.requestLong(request, "multiPayCashBillMainId");
                                                    int masterFieldPayType = FRMQueryString.requestInt(request, "MASTER_FRM_FIELD_PAY_TYPE_MULTI_"+j+"");
                                                    long payTypeMulti = FRMQueryString.requestLong(request, "FRM_FIELD_PAY_TYPE_MULTI_"+j+"");
                                                    String ccNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NAME_MULTI_"+j+"");
                                                    String ccNumberMulti =FRMQueryString.requestString(request, "FRM_FIELD_CC_NUMBER_MULTI_"+j+""); 
                                                    String debitBankNameMulti =FRMQueryString.requestString(request, "FRM_FIELD_DEBIT_BANK_NAME_MULTI_"+j+"");  
                                                    String expiredDateMulti=FRMQueryString.requestString(request, "FRM_FIELD_EXPIRED_DATE_MULTI_"+j+"");
                                                    double payAmountMulti = FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI_"+j+"");
                                                    long voucherId = FRMQueryString.requestLong(request, "FRM_FIELD_VOUCHER_ID_MULTI_"+j+"");
                                                    PaymentSystem paymentSystemMulti = new PaymentSystem();

                                                    try {
                                                        paymentSystemMulti = PstPaymentSystem.fetchExc(payTypeMulti);
                                                    } catch (Exception e) {
                                                    }
                                                    
                                                    html+=""
                                                    + "<tr>"
                                                        + "<input value='"+masterFieldPayType+"' type='hidden' id='MASTER_FRM_FIELD_PAY_TYPE_MULTI_"+j+"' name='MASTER_FRM_FIELD_PAY_TYPE_MULTI_"+j+"'>"
                                                        + "<input value='"+payTypeMulti+"' type='hidden' id='FRM_FIELD_PAY_TYPE_MULTI_"+j+"' name='FRM_FIELD_PAY_TYPE_MULTI_"+j+"'>"
                                                        + "<input value='"+ccNameMulti+"' type='hidden' id='FRM_FIELD_CC_NAME_MULTI_"+j+"' name='FRM_FIELD_CC_NAME_MULTI_"+j+"'>"
                                                        + "<input value='"+ccNumberMulti+"' type='hidden' id='FRM_FIELD_CC_NUMBER_MULTI_"+j+"' name='FRM_FIELD_CC_NUMBER_MULTI_"+j+"'>"
                                                        + "<input value='"+debitBankNameMulti+"' type='hidden' id='FRM_FIELD_DEBIT_BANK_NAME_MULTI_"+j+"' name='FRM_FIELD_DEBIT_BANK_NAME_MULTI_"+j+"'>"
                                                        + "<input value='"+expiredDateMulti+"' type='hidden' id='FRM_FIELD_EXPIRED_DATE_MULTI_"+j+"' name='FRM_FIELD_EXPIRED_DATE_MULTI_"+j+"'>"
                                                        + "<input value='"+payAmountMulti+"' type='hidden' id='FRM_FIELD_PAY_AMOUNT_MULTI_"+j+"' name='FRM_FIELD_PAY_AMOUNT_MULTI_"+j+"'>"   
                                                        + "<input value='"+voucherId+"' type='hidden' id='FRM_FIELD_VOUCHER_ID_MULTI_"+j+"' name='FRM_FIELD_VOUCHER_ID_MULTI_"+j+"'>" 
                                                        + "<td>"+j+"</td>"
                                                        + "<td>"+paymentSystemMulti.getPaymentSystem()+"</td>"
                                                        + "<td style='text-align:right;'>"+Formater.formatNumber(payAmountMulti,"#,###.##")+"</td>"
                                                        + "<td><button type='button' data-index='"+i+"' class='btn btn-danger deleteMultiPayment'><i class='fa fa-close'></i></button></td>"
                                                    + "</tr>";
                                                    totalAmount=  totalAmount- payAmountMulti; 
                                                    j = j +1;
                                                }
          
                                            } 
                                            displayTotal = Formater.formatNumber(totalAmount, "#,###.##");      
                                            costumBalance = totalAmount;
                                        html+= ""                                        
                                        + "</tbody>"                                    
                                    + "</table>"
                                + "</div>"
                            + "</div>";
                        }
                    }
			
			
////////////////// LOAD BILL RETURN
		}else if(loadtype.equals("loadbillreturn")){
		    Vector listBill = PstCustomBillMain.listItemOpenBill(0, 0, 
			    "billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+oid+"'", "");

		    BillMain billMain = new BillMain();
		    try{
			billMain = PstBillMain.fetchExc(oid);
		    }catch(Exception ex){

		    }
		    html = ""
                            
                    + "<div class='row'>"
			+ "<div class='col-md-12'>"
                            + "<div class='checkbox' id='returnToAll' style='float: right;font-weight:bold;'>" 
                                + "<label style='font-weight:bold;'><input type='checkbox' id='returnAll'  value=''>Return All</label>"
                            + "</div>"
			+ "</div>"
		    + "</div>"
		   
		    + "<div class='row'>"
			+ "<div class='col-md-12'>";
                            
			    html+=drawListReturn(iCommand, listBill, oid, null);
			html+=""
			+ "<input type='hidden' name='"+ FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]+"' value='"+oid+"'>"
			+ "</div>"
		    + "</div>";
		    
		    
		    
/////////////////////////////////			
/////////////////LOAD SEARCH LIST
		}else if(loadtype.equals("loadsearch")){
		    //balanceValue = getBalance;
                    String showTime = PstSystemProperty.getValueByName("CASHIER_SHOW_DATE_TIME_LOAD_BILL");
                    String date1 = "";
                    String date2="";
                    long location = 0;
                    String tableName = "";
                    int showPage = 1;
                    int jmlShowData = 15;
                    try {
                        date1= FRMQueryString.requestString(request, "date1");
                    } catch (Exception e) {
                        date1="";
                    }
                    try {
                        date2 = FRMQueryString.requestString(request, "date2");
                    } catch (Exception e) {
                        date2="";
                    }
                    
                    try {
                        location = FRMQueryString.requestLong(request, "location");
                    } catch (Exception e) {
                        location=0;
                    }
                    
                    try{
                        tableName = FRMQueryString.requestString(request,"table");
                    } catch(Exception e){
                    
                    }
                    try {
                        showPage = FRMQueryString.requestInt(request, "showPage");
                    } catch (Exception e) {
                        showPage = 1;
                    }
                    if (showPage==0){
                        showPage = 1;
                    }

                    int start = (showPage-1) * jmlShowData;
                    
                    
                    
                   
		    String whereClause = "";
		    /*if(oidData.length() != 0){
			whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+"='"+oidData+"' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+"='"+oidData+"' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+"='"+oidData+"' "
                                + "OR Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") = '"+oidData+"' "
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+"='"+oidData+"')";
		    }*/
                    if (oidData.length() != 0 || date1.length()!=0 || date2.length()!=0){
                        if (oidData.length()!=0){
                            if (date1.length()!=0 && date2.length()!=0){
                                whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" like '%"+oidData+"%' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" like '%"+oidData+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" like '%"+oidData+"%' "
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" like '%"+oidData+"%')";
                                if (showTime.equals("0")){
                                    whereClause += " AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") >= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date2+"')";
                                }else{
                                    whereClause += " AND (billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" >= '"+date1+"' AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+"<= '"+date2+"') ";
                                }
                              
                            }else if (date1.length()!=0 && date2.length()==0){
                                whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" like '%"+oidData+"%' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" like '%"+oidData+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" like '%"+oidData+"%' "
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" like '%"+oidData+"%')";
                                if (showTime.equals("0")){
                                    whereClause += " AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") >= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date1+"') ";
                                }else{
                                    whereClause += " AND (billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" >= '"+date1+"' AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+"<= '"+date1+"') ";
                                }
                            }else if (date1.length()==0 && date2.length()!=0){
                                whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" like '%"+oidData+"%' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" like '%"+oidData+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" like '%"+oidData+"%' "                  
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" like '%"+oidData+"%')";
                                if (showTime.equals("0")){
                                    whereClause += " AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") >= '"+date2+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date2+"') ";
                                }else{
                                    whereClause += " AND (billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" >= '"+date2+"' AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+"<= '"+date2+"') ";
                                }   
                            }else{
                                whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" like '%"+oidData+"%' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" like '%"+oidData+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" like '%"+oidData+"%' "                  
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" like '%"+oidData+"%')";
                            }
                        }else{
                            if (date1.length()!=0 && date2.length()!=0){
                                whereClause = "AND (";
                                if (showTime.equals("0")){
                                    whereClause += " (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") >= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date2+"') "; 
                                }else{
                                    whereClause += " (billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" >= '"+date1+"' AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+"<= '"+date2+"') ";
                                }
				//+ 
                                whereClause+= ""
				+ ")";
                            }else if (date1.length()!=0 && date2.length()==0){
                                whereClause = "AND (";
                                if (showTime.equals("0")){
                                    whereClause += " (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") >= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date1+"') ";
                                }else{
                                    whereClause += " (billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" >= '"+date1+"' AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" <= '"+date1+"') ";
                                }
                                //
				whereClause+= ""
				+ " )";
                            }else if (date1.length()==0 && date2.length()!=0){
                                whereClause = "AND (";
                                if (showTime.equals("0")){
                                    whereClause += " ( Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") >= '"+date2+"' AND date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") <= '"+date2+"') ";
                                }else{
                                    whereClause += " (billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" >= '"+date2+"' AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" <= '"+date2+"') ";
                                }
				whereClause += ""
				+ " )";
                            }else{
                                whereClause ="";
                            }
                        }
                    }
                    
                    if (location!=0){
                        whereClause += " AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID]+"="+location+"";
                    }
                    
                    if (!tableName.equals("")){
                        whereClause += " AND meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" like '%"+tableName+"%' ";
                    }
                    
                    String order = "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" desc";
		    Vector listBillMain = PstCustomBillMain.listOpenBill(start, jmlShowData, 
			"billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
			+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
			+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
			+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' "
			+whereClause, order);
                    Vector listCount = PstCustomBillMain.listOpenBill(0, 0, 
			"billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
			+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
			+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
			+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' "
			+whereClause, order);
		    html = drawListSearch(iCommand, listBillMain, oid, "loadsearch");
		    
                    int totalData = listCount.size();
                    double tamp =Math.floor(totalData/jmlShowData);
                    double tamp2 = totalData % jmlShowData;
                    if (tamp2>0){
                        tamp2= 1;	
                    } 
                    double JumlahHalaman = tamp2 + tamp;

                    String attDisFirst ="";
                    String attDisNext ="";

                    if (showPage==1){
                        attDisFirst= "disabled";
                    }

                    if (showPage==JumlahHalaman){
                        attDisNext ="disabled";
                    }
                    
                    if (listBillMain.size()>0){
                        html +=""
                        + "<div class='row'>"
                            + "<input type='hidden' id='totalPagingBill' value='"+Formater.formatNumber(JumlahHalaman,"")+"'>"
                            + "<input type='hidden' id='paggingPlaceBIll' value='"+showPage+"'>"
                            + "<input type='hidden' id='nextPageBIll' value='0'>"
                            + "<div class='col-md-6'>"
                                + "<label>"
                                    + "Page "+showPage+" of "+Formater.formatNumber(JumlahHalaman, "")+""
                                + "</label>"
                            + "</div>"
                            + "<div class='col-md-6'>"
                                + "<div data-original-title='Status' class='box-tools pull-right' title=''>" 
                                    + "<div class='btn-group' data-toggle='btn-toggle'>" 
                                        + "<button "+attDisFirst+" data-type='first' type='button' class='btn btn-default pagingBill'>"
                                            + "<i class='fa fa-angle-double-left'></i>"
                                        + "</button>" 
                                        + "<button id='prevPaging' "+attDisFirst+" data-type='prev' type='button' class='btn btn-default pagingBill'>"
                                            + " <i class='fa fa-angle-left'></i>"
                                        + "</button>" 
                                        + "<button id='nextPaging' "+attDisNext+" data-type='next' type='button' class='btn btn-default pagingBill'>"
                                            + "<i class='fa fa-angle-right'></i>"
                                        + "</button>" 
                                        + "<button "+attDisNext+" data-type='last' type='button' class='btn btn-default pagingBill'>"
                                            + " <i class='fa fa-angle-double-right'></i>"
                                        + "</button>" 
                                    + "</div>" 
                                + "</div>"                           
                            + "</div>"                          
                        + "</div>";
                    }

		    
/////////////////////////////////			
/////////////////LOAD SEARCH LIST
		}else if(loadtype.equals("loaditemlist")){
		    long oidLocation = 0;
		    String priceType = "0";
		    QueensLocation queensLocation;
                    int showPage = 1;
                    int jmlShowData = 10;
                    String research = "";
                    String research2="";
                    long oidLocationParent2 = 0;
                     
                    try {
                        oidLocationParent2 = FRMQueryString.requestLong(request, "LOCATION_BILL_PLACE");
                    } catch (Exception e) {
                        oidLocationParent2 = 0;
                    }
                    
                    oidLocationParent = oidLocationParent2;
                            
                    
                    try {
                        showPage = FRMQueryString.requestInt(request, "showPage");
                    } catch (Exception e) {
                        showPage = 1;
                    }
                    
                    try {
                        research = FRMQueryString.requestString(request, "search");
                    } catch (Exception e) {
                        research = "";
                    }
                    
                    try {
                        research2 = FRMQueryString.requestString(request, "FRM_FIELD_ITEM_NAME_BARCODE");
                    } catch (Exception e) {
                        research2 = "";
                    }
                    
                    if (itemsearch.length()==0){
                        itemsearch = research;
                    }
                    
                    if (research2.length()>0){
                        itemsearch = research2;
                    }
                    
                    if (showPage==0){
                        showPage = 1;
                    }
                    
                    int start = (showPage-1) * jmlShowData;
			
		    
		    if(multilocation.equals("1")){
			try{
			    queensLocation = PstQueensLocation.fetchExc(oidLocationParent);
			}catch(Exception ex){
			    queensLocation = new QueensLocation();
			}
			
			oidLocation = queensLocation.getOID();
			priceType = ""+queensLocation.getPriceTypeId();
		    }else{
			
			oidLocation = Long.parseLong(defaultOidLocation);
			priceType = defaultOidPriceType;
			
			
			try{
			    queensLocation = PstQueensLocation.fetchExc(oidLocation);
			}catch(Exception ex){
			    queensLocation = new QueensLocation();
			}
		    }
                    
                    String cashierMenuGrid = PstSystemProperty.getValueByName("CASHIER_MENU_GRID");
                    
                    if (cashierMenuGrid.equals("0")){
                        String whereClause = "";
                        Vector listItemCount = PstMaterial.listShopingCartUseLocation(0,0, 
                                "("
                                + "p."+PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]+"='"+itemsearch+"' "
                                //+ "p."+PstMaterial.fieldNames[PstMaterial.FLD_SKU]+"='"+itemsearch+"' "
                                //+ "OR p."+PstMaterial.fieldNames[PstMaterial.FLD_NAME]+" LIKE '%"+itemsearch+"%'"
                                + ")", "", ""+queensLocation.getStandartRateId(), priceType, design, oidLocation);

                        //

                        Vector listItem = PstMaterial.listShopingCartUseLocation(start,jmlShowData, 
                                "("
                                //+ "p."+PstMaterial.fieldNames[PstMaterial.FLD_SKU]+"='"+itemsearch+"' "
                                + "p."+PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]+"='"+itemsearch+"' "
                                //+ "OR p."+PstMaterial.fieldNames[PstMaterial.FLD_NAME]+" LIKE '%"+itemsearch+"%'"
                                + ")", "", ""+queensLocation.getStandartRateId(), priceType, design, oidLocation);
                        
                        
                        html = drawListItem(iCommand, listItem,start);
                        int jmlData =listItemCount.size();

                        double tamp =Math.floor(jmlData/jmlShowData);
                        double tamp2 = jmlData % jmlShowData;
                        if (tamp2>0){
                            tamp2= 1;	
                        } 
                        double JumlahHalaman = tamp2 + tamp;

                        String attDisFirst ="";
                        String attDisNext ="";

                        if (showPage==1){
                            attDisFirst= "disabled";
                        }

                        if (showPage==JumlahHalaman){
                            attDisNext ="disabled";
                        }

                        html += ""
                            + "<div class='row'>"
                                + "<input type='hidden' id='totalPaging' value='"+Formater.formatNumber(JumlahHalaman,"")+"'>"
                                + "<input type='hidden' id='paggingPlace' value='"+showPage+"'>"
                                + "<div class='col-md-6'>"
                                    + "<label>"
                                        + "Page "+showPage+" of "+Formater.formatNumber(JumlahHalaman, "")+""
                                    + "</label>"
                                + "</div>"
                                + "<div class='col-md-6'>"
                                    + "<div data-original-title='Status' class='box-tools pull-right' title=''>" 
                                        + "<div class='btn-group' data-toggle='btn-toggle'>" 
                                            + "<button "+attDisFirst+" data-type='first' type='button' class='btn btn-default pagings'>"
                                                + "<i class='fa fa-angle-double-left'></i>"
                                            + "</button>" 
                                            + "<button id='prevPaging' "+attDisFirst+" data-type='prev' type='button' class='btn btn-default pagings'>"
                                                + " <i class='fa fa-angle-left'></i>"
                                            + "</button>" 
                                            + "<button id='nextPaging' "+attDisNext+" data-type='next' type='button' class='btn btn-default pagings'>"
                                                + "<i class='fa fa-angle-right'></i>"
                                            + "</button>" 
                                            + "<button "+attDisNext+" data-type='last' type='button' class='btn btn-default pagings'>"
                                                + " <i class='fa fa-angle-double-right'></i>"
                                            + "</button>" 
                                        + "</div>" 
                                    + "</div>"                           
                                + "</div>"                          
                            + "</div>";
                        taxInc = listItem.size();
                    }else{
                        String diff = FRMQueryString.requestString(request, "diff");
                        
                        if (diff.equals("1")){
                            String whereClause = "";
                            Vector listItemCount = PstMaterial.listShopingCartUseLocation(0,0, 
                                    "("
                                    + "p."+PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]+"='"+itemsearch+"' "
                                    //+ "p."+PstMaterial.fieldNames[PstMaterial.FLD_SKU]+"='"+itemsearch+"' "
                                    //+ "OR p."+PstMaterial.fieldNames[PstMaterial.FLD_NAME]+" LIKE '%"+itemsearch+"%'"
                                    + ")", "", ""+queensLocation.getStandartRateId(), priceType, design, oidLocation);

                            //

                            Vector listItem = PstMaterial.listShopingCartUseLocation(start,jmlShowData, 
                                    "("
                                    //+ "p."+PstMaterial.fieldNames[PstMaterial.FLD_SKU]+"='"+itemsearch+"' "
                                    + "p."+PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]+"='"+itemsearch+"' "
                                    //+ "OR p."+PstMaterial.fieldNames[PstMaterial.FLD_NAME]+" LIKE '%"+itemsearch+"%'"
                                    + ")", "", ""+queensLocation.getStandartRateId(), priceType, design, oidLocation);
                            html = drawListItem(iCommand, listItem,start);


                            int jmlData =listItemCount.size();

                            double tamp =Math.floor(jmlData/jmlShowData);
                            double tamp2 = jmlData % jmlShowData;
                            if (tamp2>0){
                                tamp2= 1;	
                            } 
                            double JumlahHalaman = tamp2 + tamp;

                            String attDisFirst ="";
                            String attDisNext ="";

                            if (showPage==1){
                                attDisFirst= "disabled";
                            }

                            if (showPage==JumlahHalaman){
                                attDisNext ="disabled";
                            }

                            html += ""
                                + "<div class='row'>"
                                    + "<input type='hidden' id='totalPaging' value='"+Formater.formatNumber(JumlahHalaman,"")+"'>"
                                    + "<input type='hidden' id='paggingPlace' value='"+showPage+"'>"
                                    + "<div class='col-md-6'>"
                                        + "<label>"
                                            + "Page "+showPage+" of "+Formater.formatNumber(JumlahHalaman, "")+""
                                        + "</label>"
                                    + "</div>"
                                    + "<div class='col-md-6'>"
                                        + "<div data-original-title='Status' class='box-tools pull-right' title=''>" 
                                            + "<div class='btn-group' data-toggle='btn-toggle'>" 
                                                + "<button "+attDisFirst+" data-type='first' type='button' class='btn btn-default pagings'>"
                                                    + "<i class='fa fa-angle-double-left'></i>"
                                                + "</button>" 
                                                + "<button id='prevPaging' "+attDisFirst+" data-type='prev' type='button' class='btn btn-default pagings'>"
                                                    + " <i class='fa fa-angle-left'></i>"
                                                + "</button>" 
                                                + "<button id='nextPaging' "+attDisNext+" data-type='next' type='button' class='btn btn-default pagings'>"
                                                    + "<i class='fa fa-angle-right'></i>"
                                                + "</button>" 
                                                + "<button "+attDisNext+" data-type='last' type='button' class='btn btn-default pagings'>"
                                                    + " <i class='fa fa-angle-double-right'></i>"
                                                + "</button>" 
                                            + "</div>" 
                                        + "</div>"                           
                                    + "</div>"                          
                                + "</div>";
                            taxInc = listItem.size();
                        }else{
                            String oidSpesialRequestFood=PstSystemProperty.getValueByName("SPESIAL_REQUEST_FOOD");
                            String oidSpesialRequestBeverage=PstSystemProperty.getValueByName("SPESIAL_REQUEST_BEVERAGE");
                            
                            Material materialSpFood = new Material();
                            Material materialSpBeve = new Material();
                            try {
                                materialSpFood = PstMaterial.fetchExc(Long.parseLong(oidSpesialRequestFood));
                            } catch (Exception e) {
                            }
                            try {
                                materialSpBeve = PstMaterial.fetchExc(Long.parseLong(oidSpesialRequestBeverage));
                            } catch (Exception e) {
                            }
                            //get price for special food
                            
                            String whereSpFood = ""
                                + " "+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID]+"='"+priceType+"'"
                                + " AND "+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID]+"='"+queensLocation.getStandartRateId()+"'"
                                + " AND "+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]+"='"+Long.parseLong(oidSpesialRequestFood)+"'";
                            
                            String whereSpBeverage = ""
                                + " "+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID]+"='"+priceType+"'"
                                + " AND "+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID]+"='"+queensLocation.getStandartRateId()+"'"
                                + " AND "+PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID]+"='"+Long.parseLong(oidSpesialRequestFood)+"'";
                            
                            Vector listSpFood = PstPriceTypeMapping.list(0, 1, whereSpFood, "");
                            Vector listSpBeverage = PstPriceTypeMapping.list(0, 1, whereSpBeverage, "");
                            
                            PriceTypeMapping entPriceTypeMappingSpF = new PriceTypeMapping();
                            PriceTypeMapping entPriceTypeMappingSpB = new PriceTypeMapping();
                            
                            try {
                                entPriceTypeMappingSpF = (PriceTypeMapping)listSpFood.get(0);
                            } catch (Exception e) {
                            }
                            
                            try {
                                entPriceTypeMappingSpB = (PriceTypeMapping)listSpBeverage.get(0);
                            } catch (Exception e) {
                            }
                            
                            html += ""
                            
                            + "<div id='dynamicPlaceForMenu'>"
                                + "<div class='row' style='margin-bottom:10px;'>"
                                    + "<div class='col-md-12'>"
                                        + "<div class='btn-group'>"
                                            + "<button type ='button' class='btn btn-default active mainMenuOutlet' data-for='loadMenu'><i class='fa fa-home'></i>&nbsp;</button>"
                                        + "</div>"
                                    + "</div>"
                                + "</div>"
                                + "<div class='row'>"
                                    + "<div class='col-md-6'>"
                                        + "<button type='button' data-oid='"+materialSpFood.getOID()+"' data-name='Special Food' data-price='"+entPriceTypeMappingSpF.getPrice()+"'  class='btn btn-block btn-primary specialOrder col-md-6 btn-flat btn-lg FRM_FIELD_ITEM_NAME'><strong>Special Food</strong></button>"
                                    + "</div>"
                                    + "<div class='col-md-6'>"
                                        + "<button type='button' data-oid='"+materialSpFood.getOID()+"' data-name='Special Beverage' data-price='"+entPriceTypeMappingSpF.getPrice()+"'  class='btn btn-block btn-primary specialOrder col-md-6 btn-flat btn-lg FRM_FIELD_ITEM_NAME' ><strong>Special Beverage</strong></button>"
                                    + "</div>"                              
                                + "</div>"
                                + "<div class='row' style='margin-top:10px;'>"
                                    + "<div class='col-md-6'>"
                                        + "<button type='button' data-menu='1' data-type='0' data-oidcategory='0' data-design='"+design+"' data-pricetype='"+priceType+"' data-location='"+oidLocation+"' data-parent='"+oidLocationParent2+"' class='btn btn-block btn-primary col-md-6 btn-flat btn-lg mainMenu' data-for='loadMenu'><strong>Food</strong></button>"
                                    + "</div>"
                                    + "<div class='col-md-6'>"
                                        + "<button type='button' data-menu='1' data-type='1' data-oidcategory='0' data-design='"+design+"' data-pricetype='"+priceType+"' data-location='"+oidLocation+"' data-parent='"+oidLocationParent2+"' class='btn btn-block btn-primary col-md-6 btn-flat btn-lg mainMenu' data-for='loadMenu'><strong>Beverage</strong></button>"
                                    + "</div>"                              
                                + "</div>"
                            + "</div>"
                            + "";
                        }
                    }

                }else if(loadtype.equals("loaditemlist2")){
		    long oidLocation = 0;
		    String priceType = "0";
		    QueensLocation queensLocation;
                    int showPage = 1;
                    int jmlShowData = 10;
                    String research = "";
                    long categoryId = 0;
                    long oidLocationParent2 = 0;
                    int type = 0;
                    try {
                        oidLocationParent2 = FRMQueryString.requestLong(request, "LOCATION_BILL_PLACE");
                    } catch (Exception e) {
                        oidLocationParent2 = 0;
                    }
                    
                    oidLocationParent = oidLocationParent2;
                            
                    
                    try {
                        showPage = FRMQueryString.requestInt(request, "showPage");
                    } catch (Exception e) {
                        showPage = 1;
                    }
                    
                    try {
                        research = FRMQueryString.requestString(request, "typeText");
                    } catch (Exception e) {
                        research = "";
                    }
                    
                    try {
                        categoryId = FRMQueryString.requestLong(request, "categoryMenuId");
                    } catch (Exception e) {
                        categoryId = 0;
                    }
                    
                    Category entCategory = new Category();
                    
                    try {
                        entCategory = PstCategory.fetchExc(categoryId);
                    } catch (Exception e) {
                    }
                    
                    itemsearch = research;
                    
                    type = entCategory.getCategoryId();
                    
                    if (showPage==0){
                        showPage = 1;
                    }
                    
                    int start = (showPage-1) * jmlShowData;
			
		    
		    if(multilocation.equals("1")){
			try{
			    queensLocation = PstQueensLocation.fetchExc(oidLocationParent);
			}catch(Exception ex){
			    queensLocation = new QueensLocation();
			}
			
			oidLocation = queensLocation.getOID();
			priceType = ""+queensLocation.getPriceTypeId();
		    }else{
			
			oidLocation = Long.parseLong(defaultOidLocation);
			priceType = defaultOidPriceType;
			
			
			try{
			    queensLocation = PstQueensLocation.fetchExc(oidLocation);
			}catch(Exception ex){
			    queensLocation = new QueensLocation();
			}
		    }
		    
		    String whereClause = ""
                    + "("
                    + " p."+PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]+"='"+itemsearch+"' "
                    + " OR p."+PstMaterial.fieldNames[PstMaterial.FLD_SKU]+"='"+itemsearch+"' "
                    + " OR p."+PstMaterial.fieldNames[PstMaterial.FLD_NAME]+" LIKE '%"+itemsearch+"%'"
                    + ")";
                    
                    if (categoryId!=0){
                        whereClause += ""
                        + " AND p."+PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+categoryId+"'";
                    }
                   
                    Vector listItemCount = PstMaterial.listShopingCartUseLocation(0,0,whereClause, "", ""+queensLocation.getStandartRateId(), priceType, design, oidLocation);                   
		    Vector listItem = PstMaterial.listShopingCartUseLocation(start,jmlShowData,whereClause, "", ""+queensLocation.getStandartRateId(), priceType, design, oidLocation);
		    html="";
                    
                    String fbWrite = "";
                    if (type==0){
                        fbWrite = "Food";
                    }else{
                        fbWrite = "Beverage";
                    }
                    
                    String cashierMenuGrid = PstSystemProperty.getValueByName("CASHIER_MENU_GRID");
                    
                    if (cashierMenuGrid.equals("1")){
                        if (categoryId!=0){

                            html = ""
                            + "<div id='dynamicPlaceForMenu'>"
                            + "<div class='row' style='margin-bottom:10px;'>"
                                + "<div class='col-md-12'>"
                                    + "<div class='btn-group'>"

                                        + "<button type ='button' class='btn btn-default mainMenuOutlet' data-for='loadMenu'><i class='fa fa-home'></i>&nbsp;</button>"
                                        + "<button data-menu='1' data-type='"+type+"' data-oidcategory='0' data-design='"+design+"' data-pricetype='"+priceType+"' data-location='"+oidLocation+"' data-parent='"+oidLocationParent2+"' data-for='loadMenu' type ='button' class='btn btn-default mainCategoryOutlet '><i class='fa fa-gg-circle'></i> "+fbWrite+"</button>";
                                        Vector anotherMenu = new Vector(1,1);
                                        anotherMenu = parentMenu(entCategory.getOID(), anotherMenu, oidLocation, priceType, ""+design, type);
                                        if(anotherMenu.size() > 0){
                                            for(int i = anotherMenu.size()-2; i >=1; i--){
                                                String getHtml = (String) anotherMenu.get(i);
                                                html += getHtml;
                                            }
                                        }
                                        html+=""
                                        + "<button type ='button' class='btn btn-default active'><i class='fa fa-gg-circle'></i> "+entCategory.getName()+"</button>"
                                    + "</div>"
                                + "</div>"
                            + "</div>";
                        }else{
                            html = ""
                            + "<div id='dynamicPlaceForMenu'>"
                            + "<div class='row' style='margin-bottom:10px;'>"
                                + "<div class='col-md-12'>"
                                    + "<div class='btn-group'>"
                                        + "<button type ='button' class='btn btn-default mainMenuOutlet' data-for='loadMenu'><i class='fa fa-building'></i> Grid View</button>"
                                    + "</div>"
                                + "</div>"
                            + "</div>";
                        }
                    }
                    

                    html += drawListItem(iCommand, listItem,start);
                    int jmlData =listItemCount.size();
                    
                    double tamp =Math.floor(jmlData/jmlShowData);
                    double tamp2 = jmlData % jmlShowData;
                    if (tamp2>0){
                        tamp2= 1;	
                    } 
                    double JumlahHalaman = tamp2 + tamp;
                                      
                    String attDisFirst ="";
                    String attDisNext ="";
                   
                    if (showPage==1){
                        attDisFirst= "disabled";
                    }
                    
                    if (showPage==JumlahHalaman){
                        attDisNext ="disabled";
                    }
                                      
                    html += ""
                        + "<div class='row'>"
                            + "<input type='hidden' id='totalPaging' value='"+Formater.formatNumber(JumlahHalaman,"")+"'>"
                            + "<input type='hidden' id='paggingPlace' value='"+showPage+"'>"
                            + "<div class='col-md-6'>"
                                + "<label>"
                                    + "Page "+showPage+" of "+Formater.formatNumber(JumlahHalaman, "")+""
                                + "</label>"
                            + "</div>"
                            + "<div class='col-md-6'>"
                                + "<div data-original-title='Status' class='box-tools pull-right' title=''>" 
                                    + "<div class='btn-group' data-toggle='btn-toggle'>" 
                                        + "<button "+attDisFirst+" data-type='first' type='button' class='btn btn-default pagingsSearch'>"
                                            + "<i class='fa fa-angle-double-left'></i>"
                                        + "</button>" 
                                        + "<button id='prevPaging' "+attDisFirst+" data-type='prev' type='button' class='btn btn-default pagingsSearch'>"
                                            + " <i class='fa fa-angle-left'></i>"
                                        + "</button>" 
                                        + "<button id='nextPaging' "+attDisNext+" data-type='next' type='button' class='btn btn-default pagingsSearch'>"
                                            + "<i class='fa fa-angle-right'></i>"
                                        + "</button>" 
                                        + "<button "+attDisNext+" data-type='last' type='button' class='btn btn-default pagingsSearch'>"
                                            + " <i class='fa fa-angle-double-right'></i>"
                                        + "</button>" 
                                    + "</div>" 
                                + "</div>"                           
                            + "</div>"                          
                        + "</div>"
                    + "</div>";
		    taxInc = listItem.size();

		
////////////////////////////////////////
/////////////////LOAD SEARCH RETURN BILL
		}else if(loadtype.equals("loadsearchreturn")){
		    //balanceValue = getBalance;
                    String whereClause = "";
                    String date1 = "";
                    String date2 = "";
                    long location = 0;
                    String tableName ="";
                    int showPage = 1;
                    int jmlShowData = 15;
                    
                    try {
                        date1= FRMQueryString.requestString(request, "date1");
                    } catch (Exception e) {
                        date1="";
                    }
                    try {
                        date2 = FRMQueryString.requestString(request, "date2");
                    } catch (Exception e) {
                        date2="";
                    }
                    
                    try {
                        location = FRMQueryString.requestLong(request, "location");
                    } catch (Exception e) {
                        location=0;
                    }
                    
                    try{
                        tableName = FRMQueryString.requestString(request,"table");
                    } catch(Exception e){
                    
                    }
                    
                    try {
                        showPage = FRMQueryString.requestInt(request, "showPage");
                    } catch (Exception e) {
                        showPage = 1;
                    }
                    
                    if (showPage==0){
                        showPage = 1;
                    }

                    int start = (showPage-1) * jmlShowData;
                    
		   
		    /*if(oidData.length() != 0){
			whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+"='"+oidData+"' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" LIKE '%"+oidData+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+"='"+oidData+"' "
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" LIKE '%"+oidData+"%'"
                                + "OR Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") = '"+oidData+"') "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
		    }*/
                    if (oidData.length() != 0 || date1.length()!=0 || date2.length()!=0){
                        if (oidData.length()!=0){
                            if (date1.length()!=0 && date2.length()!=0){
                                whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" LIKE '%"+oidData+"%' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" LIKE '%"+oidData+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" LIKE '%"+oidData+"%' "
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" LIKE '%"+oidData+"%')"
                                + "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")>= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<='"+date2+"')"
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                            }else if (date1.length()!=0 && date2.length()==0){
                                whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" LIKE '%"+oidData+"%' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" LIKE '%"+oidData+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" LIKE '%"+oidData+"%' "
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" LIKE '%"+oidData+"%')"
                                + "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")>= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<='"+date1+"')"
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                            }else if (date1.length()==0 && date2.length()!=0){
                                whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" LIKE '%"+oidData+"%' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" LIKE '%"+oidData+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" LIKE '%"+oidData+"%' "
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" LIKE '%"+oidData+"%')"
                                + "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")>= '"+date2+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<='"+date2+"')"
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                            }else{
                                whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" LIKE '%"+oidData+"%' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" LIKE '%"+oidData+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" LIKE '%"+oidData+"%' "
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" LIKE '%"+oidData+"%')"
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                            }
                        }else{
                            if (date1.length()!=0 && date2.length()!=0){
                                whereClause = ""
				+ "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")>= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<='"+date2+"')"
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                            }else if (date1.length()!=0 && date2.length()==0){
                                whereClause = ""
				+ "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")>= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<='"+date1+"')"
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                            }else if (date1.length()==0 && date2.length()!=0){
                                whereClause = ""
				+ "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")>= '"+date2+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<='"+date2+"')"
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                            }else{
                                whereClause = "";
                            }
                        }
                    }
                    
                    if (location!=0){
                        whereClause += " AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID]+"="+location+"";
                    }
                    
                    if (!tableName.equals("")){
                        whereClause += " AND meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" like '%"+tableName+"%' ";
                    }
                    
                    String order = "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" desc";
		    Vector listBillMain = PstCustomBillMain.listOpenBill(start, jmlShowData, 
			"("
			    + "("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
				+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
			    + ") OR ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
				+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
			    + ") OR ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
				+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
			    + ")"
			+ ")"
			+whereClause, order);
                    Vector listCount = PstCustomBillMain.listOpenBill(0, 0, 
			"("
			    + "("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
				+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
			    + ") OR ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
				+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
			    + ") OR ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
				+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
			    + ")"
			+ ")"
			+whereClause, order);
		    html = drawListSearch(iCommand, listBillMain, oid, "loadsearchreturn");
                    int totalData = listCount.size();
                    double tamp =Math.floor(totalData/jmlShowData);
                    double tamp2 = totalData % jmlShowData;
                    if (tamp2>0){
                        tamp2= 1;	
                    } 
                    double JumlahHalaman = tamp2 + tamp;
                    String attDisFirst ="";
                    String attDisNext ="";

                    if (showPage==1){
                        attDisFirst= "disabled";
                    }

                    if (showPage==JumlahHalaman){
                        attDisNext ="disabled";
                    }
                    
                    if (listBillMain.size()>0){
                        html +=""
                        + "<div class='row'>"
                            + "<input type='hidden' id='totalPagingBill' value='"+Formater.formatNumber(JumlahHalaman,"")+"'>"
                            + "<input type='hidden' id='paggingPlaceBIll' value='"+showPage+"'>"
                            + "<input type='hidden' id='nextPageBIll' value='0'>"
                            + "<div class='col-md-6'>"
                                + "<label>"
                                    + "Page "+showPage+" of "+Formater.formatNumber(JumlahHalaman, "")+""
                                + "</label>"
                            + "</div>"
                            + "<div class='col-md-6'>"
                                + "<div data-original-title='Status' class='box-tools pull-right' title=''>" 
                                    + "<div class='btn-group' data-toggle='btn-toggle'>" 
                                        + "<button "+attDisFirst+" data-type='first' type='button' class='btn btn-default pagingBill'>"
                                            + "<i class='fa fa-angle-double-left'></i>"
                                        + "</button>" 
                                        + "<button id='prevPaging' "+attDisFirst+" data-type='prev' type='button' class='btn btn-default pagingBill'>"
                                            + " <i class='fa fa-angle-left'></i>"
                                        + "</button>" 
                                        + "<button id='nextPaging' "+attDisNext+" data-type='next' type='button' class='btn btn-default pagingBill'>"
                                            + "<i class='fa fa-angle-right'></i>"
                                        + "</button>" 
                                        + "<button "+attDisNext+" data-type='last' type='button' class='btn btn-default pagingBill'>"
                                            + " <i class='fa fa-angle-double-right'></i>"
                                        + "</button>" 
                                    + "</div>" 
                                + "</div>"                           
                            + "</div>"                          
                        + "</div>"
                    + "";
                }
                    
///////////////////////////////////
/////////////////LOAD LIST REPRINT UP
                }else if (loadtype.equals("loadListReprintUp")){
                    Vector location_keys = new Vector(1,1);
                    Vector location_values = new Vector(1,1);
                    
                    //ini adalah variabel untuk menampung tipe bussines dari sistem ini (Retail,Restaurant,atau Retail DIstribution)
                    String bussinessType = PstSystemProperty.getValueByName("TYPE_OF_BUSINESS");

                    Vector listLocation = PstLocation.listLocationStore(0,0,"","");
                    if(listLocation.size() != 0){
                        location_keys.add("0");
                        location_values.add("ALL LOCATION");
                        for(int i = 0; i<listLocation.size(); i++){
                            Location location1 = (Location) listLocation.get(i);
                            location_keys.add(""+location1.getOID());
                            location_values.add(""+location1.getName());
                        }
                    }
                    html="<div class='row'>";
                    if (multilocation.equals("1")){
                        html += ""
                            + "<div class='col-md-4'>"
                                + ControlCombo.drawBootsratap(FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]+"_REPRINT", null, "0", location_keys, location_values, "required='required'", "form-control")    
                            + "</div>";
                    }
                    
                    if (multilocation.equals("1")){
                        html +="<div class='col-md-8'>";              
                    }else{
                        html += ""
                            + "<div class='col-md-12'>"
                                + "<input value='0' type='hidden' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]+"_REPRINT' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]+"_OPEN'>";
                    }
                    html += ""
                            + "<div class='input-group' style='margin-bottom: 2px;'>"
                                + "<div class='input-group-addon'><i class='fa fa-calendar'></i></div>"
                                + "<input id='orSearch1' class='form-control datePicker3' required='' type='text'>"
                                + "<div class='input-group-addon' style='border:none;'><i class='fa fa-minus'></i></div>"
                                + "<div class='input-group-addon' style='border-right:none;'><i class='fa fa-calendar'></i></div>"
                                + "<input id='orSearch2' class='form-control datePicker3' required='' type='text'>"
                            +"</div>"
                        + "</div>";
                    
                    html+=""
                        + "</div>"
                        + "<div class='row'>";
                    
                        if (bussinessType.equals("2")){
                            html += ""
                            + "<div class='col-md-4'>"
                                + "<input type='text' class='form-control' placeholder='Type Table Name..' id='tableNameRe' name ='tableNameRe'>"
                            + "</div>"
                            + "<div class='col-md-8'>"
                                + "<div class='input-group'>"
                                    + "<input type='text' class='form-control' placeholder='Scan or Search Transactions' id='dataSearchReprint'/>"
                                    + "<div class='input-group-addon btn btn-primary' id='btnSearchReprintBill'>"     
                                        + "<i class='fa fa-search'></i>" 
                                    + "</div>"
                                + "</div>"
                                + "<input type='hidden' id='reprintSelect' value='0'>"
                            + "</div>";
                        }else{
                             html += ""
                            + "<div class='col-md-12'>"
                                + "<input type='hidden' class='form-control' placeholder='Type Table Name..' id='tableNameRe' name ='tableNameRe'>"
                                + "<div class='input-group'>"
                                    + "<input type='text' class='form-control' placeholder='Scan or Search Transactions' id='dataSearchReprint'/>"
                                    + "<div class='input-group-addon btn btn-primary' id='btnSearchReprintBill'>"     
                                        + "<i class='fa fa-search'></i>" 
                                    + "</div>"
                                + "</div>"
                                + "<input type='hidden' id='reprintSelect' value='0'>"
                            + "</div>";
                        }
                    html+=""        
                        + "</div>";
                        
     
///////////////////////////////////
/////////////////LOAD LIST REPRINT                     
                }else if (loadtype.equals("loadListReprint")){
                    String typeText = FRMQueryString.requestString(request, "typeText");
                    int typeReprint = FRMQueryString.requestInt(request, "typeReprint");
                    String showTime = PstSystemProperty.getValueByName("CASHIER_SHOW_DATE_TIME_LOAD_BILL");
                    String date1="";
                    String date2="";
                    long location = 0;
                    String table="";
                    int showPage = 1;
                    int jmlShowData = 15;
                    try {
                        date1 = FRMQueryString.requestString(request,"date1");
                    } catch (Exception e) {
                        date1 ="";
                    }
                    try {
                        date2 = FRMQueryString.requestString(request,"date2");
                    } catch (Exception e) {
                        date2 =""; 
                    }
                    
                    try {
                        location = FRMQueryString.requestLong(request,"location");
                    } catch (Exception e) {
                        location =0; 
                    }
                    
                    try{
                        table = FRMQueryString.requestString(request, "table");
                    } catch(Exception e){
                        table="";
                    }
                    
                    try {
                        showPage = FRMQueryString.requestInt(request, "showPage");
                    } catch (Exception e) {
                        showPage = 1;
                    }
                    
                    if (showPage==0){
                        showPage = 1;
                    }

                    int start = (showPage-1) * jmlShowData;
                    
                    String whereClause = "";
		    
                    if (typeReprint<2){
                        if (typeText.length()!=0 || date1.length()!=0 || date2.length()!=0){
                            if (typeText.length()!=0){
                                if (date1.length()!=0 && date2.length()!=0){
                                    whereClause = "AND ("
                                    + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" LIKE '%"+typeText+"%' "
                                    + "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" LIKE '%"+typeText+"%' "
                                    + "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" LIKE '%"+typeText+"%' "
                                    + "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" LIKE '%"+typeText+"%' )";
                                    if (showTime.equals("0")){
                                        whereClause += "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")>= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date2+"') ";
                                    }else{
                                        whereClause += "AND (billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" >= '"+date1+"' AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" <= '"+date2+"') ";
                                    }
                                    whereClause += ""
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                                }else if (date1.length()!=0 && date2.length()==0){
                                    whereClause = "AND ("
                                    + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" LIKE '"+typeText+"' "
                                    + "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" LIKE '%"+typeText+"%' "
                                    + "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" LIKE '%"+typeText+"%' "
                                    + "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" LIKE '%"+typeText+"%' )";
                                    if (showTime.equals("0")){
                                        whereClause += "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")>= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date1+"') ";
                                    }else{
                                        whereClause += "AND (billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+">= '"+date1+"' AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+"<= '"+date1+"') ";
                                    }
                                    whereClause += ""
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                                }else if (date1.length()==0 && date2.length()!=0){
                                    whereClause = "AND ("
                                    + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" LIKE '%"+typeText+"%' "
                                    + "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" LIKE '%"+typeText+"%' "
                                    + "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" LIKE '%"+typeText+"%' "
                                    + "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" LIKE '%"+typeText+"%' )";
                                    if (showTime.equals("0")){
                                        whereClause += "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")>= '"+date2+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date2+"') ";
                                    }else{
                                        whereClause += "AND (billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+">= '"+date2+"' AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+"<= '"+date2+"') ";
                                    }
                                    whereClause += ""
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                                }else{
                                    whereClause = "AND ("
                                    + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" LIKE '%"+typeText+"%' "
                                    + "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" LIKE '%"+typeText+"%' "
                                    + "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" LIKE '%"+typeText+"%' "
                                    + "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" LIKE '%"+typeText+"%' )"
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                                }
                            }else{
                                if (date1.length()!=0 && date2.length()!=0){
                                    whereClause = "";
                                    if (showTime.equals("0")){
                                        whereClause += "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")>= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date2+"') ";
                                    }else{
                                        whereClause += "AND (billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+">= '"+date1+"' AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+"<= '"+date2+"') ";
                                    }
                                    whereClause += " "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                                }else if (date1.length()!=0 && date2.length()==0){
                                    whereClause = "";
                                    if (showTime.equals("0")){
                                        whereClause += "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")>= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date1+"') ";
                                    }else{
                                        whereClause += "AND (billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+">= '"+date1+"' AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+"<= '"+date1+"') ";
                                    }
                                    whereClause += " "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                                }else if (date1.length()==0 && date2.length()!=0){
                                    whereClause = "";
                                    if (showTime.equals("0")){
                                        whereClause += "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")>= '"+date2+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date2+"') ";
                                    }else{
                                        whereClause += "AND (billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+">= '"+date2+"' AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+"<= '"+date2+"') ";
                                    }
                                    whereClause += " "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
                                }else{
                                    whereClause = "";
                                }
                            }
                        }
                    
                        if (location!=0){
                            whereClause += " AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID]+"="+location+"";
                        }
                        
                        if (!table.equals("")){
                            whereClause += "AND meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" like '%"+table+"%'";
                        }
		    
                        String order = "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" desc";
                        Vector listBillMain = PstCustomBillMain.listOpenBill(start, jmlShowData, 
                            "("
                                + "("
                                    + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='"+typeReprint+"' "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                    + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ") OR ("
                                    + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='"+typeReprint+"' "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
                                    + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ") OR ("
                                    + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='"+typeReprint+"' "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                    + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ")"
                            + ")"
                            +whereClause, order);
                        Vector listCount = PstCustomBillMain.listOpenBill(0, 0, 
                            "("
                                + "("
                                    + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='"+typeReprint+"' "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                    + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ") OR ("
                                    + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='"+typeReprint+"' "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
                                    + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ") OR ("
                                    + "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='"+typeReprint+"' "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
                                    + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                                + ")"
                            + ")"
                            +whereClause, order);
                       html = drawListReprint(iCommand, listBillMain,"loadsearchreturn", oid );
                       int totalData = listCount.size();
        
                        double tamp =Math.floor(totalData/jmlShowData);
                        double tamp2 = totalData % jmlShowData;
                        if (tamp2>0){
                            tamp2= 1;	
                        } 
                        double JumlahHalaman = tamp2 + tamp;

                        String attDisFirst ="";
                        String attDisNext ="";

                        if (showPage==1){
                            attDisFirst= "disabled";
                        }

                        if (showPage==JumlahHalaman){
                            attDisNext ="disabled";
                        }
                        if (listBillMain.size()>0){
                            html +=""
                            + "<div class='row' style='margin-bottom:10px;'>"
                                + "<input type='hidden' id='totalPagingRe' value='"+Formater.formatNumber(JumlahHalaman,"")+"'>"
                                + "<input type='hidden' id='paggingPlaceRe' value='"+showPage+"'>"
                                + "<input type='hidden' id='nextPaggingRe' value='"+showPage+"'>"
                                + "<div class='col-md-6'>"
                                    + "<label>"
                                        + "Page "+showPage+" of "+Formater.formatNumber(JumlahHalaman, "")+""
                                    + "</label>"
                                + "</div>"
                                + "<div class='col-md-6'>"
                                    + "<div data-original-title='Status' class='box-tools pull-right' title=''>" 
                                        + "<div class='btn-group' data-toggle='btn-toggle'>" 
                                            + "<button "+attDisFirst+" data-type='first' type='button' class='btn btn-default pagingRe'>"
                                                + "<i class='fa fa-angle-double-left'></i>"
                                            + "</button>" 
                                            + "<button id='prevPaging' "+attDisFirst+" data-type='prev' type='button' class='btn btn-default pagingRe'>"
                                                + " <i class='fa fa-angle-left'></i>"
                                            + "</button>" 
                                            + "<button id='nextPaging' "+attDisNext+" data-type='next' type='button' class='btn btn-default pagingRe'>"
                                                + "<i class='fa fa-angle-right'></i>"
                                            + "</button>" 
                                            + "<button "+attDisNext+" data-type='last' type='button' class='btn btn-default pagingRe'>"
                                                + " <i class='fa fa-angle-double-right'></i>"
                                            + "</button>" 
                                        + "</div>" 
                                    + "</div>"                           
                                + "</div>"                          
                            + "</div>"
                        + "";
                        }
                    }else{
                        if (typeText.length()!=0 || date1.length()!=0 || date2.length()!=0){
                            if (typeText.length()!=0){
                                if (date1.length()!=0 && date2.length()!=0){
                                    whereClause = "AND ("
                                    + " pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_CODE]+" like '%"+typeText+"%' "
                                    + " OR loc."+PstLocation.fieldNames[PstLocation.FLD_NAME]+" like '%"+typeText+"%' "
                                    + " OR cl."+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" like '%"+typeText+"%' "
                                    + " AND (Date(pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+")>='"+date1+"' "
                                    + " AND Date(pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+")<='"+date2+"'))";
                                
                                }else if (date1.length()!=0 && date2.length()==0){
                                   whereClause = "AND ("
                                    + " pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_CODE]+" like '%"+typeText+"%' "
                                    + " OR loc."+PstLocation.fieldNames[PstLocation.FLD_NAME]+" like '%"+typeText+"%' "     
                                    + " OR cl."+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" like '%"+typeText+"%' "
                                    + " AND (Date(pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+")>='"+date1+"' "
                                    + " AND Date(pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+")<='"+date1+"'))";
                                }else if (date1.length()==0 && date2.length()!=0){
                                    whereClause = "AND ("
                                    + " pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_CODE]+" like '%"+typeText+"%' "
                                    + " OR loc."+PstLocation.fieldNames[PstLocation.FLD_NAME]+" like '%"+typeText+"%' "
                                    + " OR cl."+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" like '%"+typeText+"%' "
                                    + " AND (Date(pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+")>='"+date2+"' "
                                    + " AND Date(pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+")<='"+date2+"'))";
                                }else{
                                    whereClause = "AND ("
                                    + " pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_CODE]+" like '%"+typeText+"%' "
                                    + " OR loc."+PstLocation.fieldNames[PstLocation.FLD_NAME]+" like '%"+typeText+"%' "
                                    + " OR cl."+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" like '%"+typeText+"%' )";
                                   
                                }
                            }else{
                                if (date1.length()!=0 && date2.length()!=0){
                                    whereClause = ""                             
                                    + " AND (Date(pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+")>='"+date1+"' "
                                    + " AND Date(pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+")<='"+date2+"')";
                                }else if (date1.length()!=0 && date2.length()==0){
                                    whereClause = ""                             
                                    + " AND (Date(pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+")>='"+date1+"' "
                                    + " AND Date(pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+")<='"+date1+"')";
                                }else if (date1.length()==0 && date2.length()!=0){
                                    whereClause = ""                             
                                    + " AND (Date(pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+")>='"+date2+"' "
                                    + " AND Date(pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+")<='"+date2+"')";
                                }else{
                                    whereClause = "";
                                }
                                
                                
                                
                            }
                        }
                        
                        if (location!=0){
                            whereClause += " AND pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_LOCATION_ID]+"="+location+"";
                        }
                        
                        if (!table.equals("")){
                            whereClause += "AND meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" like '%"+table+"%'";
                        }
                        
                        String order = "pcm."+PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE]+" desc";
                        String where = ""+PstMatCosting.fieldNames[PstMatCosting.FLD_CASH_CASHIER_ID]+"<>0" + " " + whereClause;

                        Vector listBillMain = PstMatCosting.listJoinContactLocation(start, jmlShowData, where, order);
                        Vector listCount = PstMatCosting.listJoinContactLocation(0, 0, where, order);
                        html += drawListReprintFOC(listBillMain);
                        html += "";
                        int totalData = listCount.size();
        
                        double tamp =Math.floor(totalData/jmlShowData);
                        double tamp2 = totalData % jmlShowData;
                        if (tamp2>0){
                            tamp2= 1;	
                        } 
                        double JumlahHalaman = tamp2 + tamp;

                        String attDisFirst ="";
                        String attDisNext ="";

                        if (showPage==1){
                            attDisFirst= "disabled";
                        }

                        if (showPage==JumlahHalaman){
                            attDisNext ="disabled";
                        }
                        if (listBillMain.size()>0){
                            html +=""
                            + "<div class='row' style='margin-bottom:10px;'>"
                                + "<input type='hidden' id='totalPagingRe' value='"+Formater.formatNumber(JumlahHalaman,"")+"'>"
                                + "<input type='hidden' id='paggingPlaceRe' value='"+showPage+"'>"
                                + "<input type='hidden' id='nextPaggingRe' value='"+showPage+"'>"
                                + "<div class='col-md-6'>"
                                    + "<label>"
                                        + "Page "+showPage+" of "+Formater.formatNumber(JumlahHalaman, "")+""
                                    + "</label>"
                                + "</div>"
                                + "<div class='col-md-6'>"
                                    + "<div data-original-title='Status' class='box-tools pull-right' title=''>" 
                                        + "<div class='btn-group' data-toggle='btn-toggle'>" 
                                            + "<button "+attDisFirst+" data-type='first' type='button' class='btn btn-default pagingRe'>"
                                                + "<i class='fa fa-angle-double-left'></i>"
                                            + "</button>" 
                                            + "<button id='prevPaging' "+attDisFirst+" data-type='prev' type='button' class='btn btn-default pagingRe'>"
                                                + " <i class='fa fa-angle-left'></i>"
                                            + "</button>" 
                                            + "<button id='nextPaging' "+attDisNext+" data-type='next' type='button' class='btn btn-default pagingRe'>"
                                                + "<i class='fa fa-angle-right'></i>"
                                            + "</button>" 
                                            + "<button "+attDisNext+" data-type='last' type='button' class='btn btn-default pagingRe'>"
                                                + " <i class='fa fa-angle-double-right'></i>"
                                            + "</button>" 
                                        + "</div>" 
                                    + "</div>"                           
                                + "</div>"                          
                            + "</div>"
                        + "";
                        }
                    }
                    
                   
///////////////////////////////////
/////////////////LOAD LIST JOIN UP
                }else if (loadtype.equals("loadListJoinUp")){
                    html="";
                    html += "<div class='input-group' style='margin-bottom: 2px;'>"
                                + "<div class='input-group-addon'><i class='fa fa-calendar'></i></div>"
                                + "<input id='ojSearch1' class='form-control datePicker4' required='' type='text'>"
                                + "<div class='input-group-addon' style='border:none;'><i class='fa fa-minus'></i></div>"
                                + "<div class='input-group-addon' style='border-right:none;'><i class='fa fa-calendar'></i></div>"
                                + "<input id='ojSearch2' class='form-control datePicker4' required='' type='text'>"
                            +"</div>";
                    html += "<div class='input-group'>" 
                                + "<input type='text' class='form-control' placeholder='Scan or Search Transactions' id='dataSearchJoin'/>"
                                + "<div class='input-group-addon btn btn-primary' id='btnSearchJoinBill'>" 
                                    + "<i class='fa fa-search'></i>" 
                                + "</div>" 
                          + "</div>";                   
                    html += "<input type='hidden' id='oidBillA' value='"+oid+"'>";
                    
///////////////////////////////////
/////////////////////LOAD LIST JOIN
                }else if(loadtype.equals("loadListJoin")){
                    String typeText = FRMQueryString.requestString(request, "typeText");
                    long oidBillA = FRMQueryString.requestLong(request, "oidBillA");
                    String date1="";
                    String date2="";

                    try {
                        date1 = FRMQueryString.requestString(request,"date1");
                    } catch (Exception e) {
                        date1 ="";
                    }
                    try {
                        date2 = FRMQueryString.requestString(request,"date2");
                    } catch (Exception e) {
                        date2 =""; 
                    }                   
                    String whereClause = "";
                    if (typeText.length() != 0 || date1.length()!=0 || date2.length()!=0){
                        if (typeText.length()!=0){
                            if (date1.length()!=0 && date2.length()!=0){
                                whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" like '%"+typeText+"%' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" like '%"+typeText+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" like '%"+typeText+"%' "
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" like '%"+typeText+"%')"
                                + "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") >= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date2+"') ";
                            }else if (date1.length()!=0 && date2.length()==0){
                                whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" like '%"+typeText+"%' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" like '%"+typeText+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" like '%"+typeText+"%' "
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" like '%"+typeText+"%')"
                                + "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") >= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date1+"') ";
                            }else if (date1.length()==0 && date2.length()!=0){
                                whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" like '%"+typeText+"%' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" like '%"+typeText+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" like '%"+typeText+"%' "                  
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" like '%"+typeText+"%')"
                                + "AND (Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") >= '"+date2+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date2+"') ";
                            }else{
                                whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" like '%"+typeText+"%' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" like '%"+typeText+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+" like '%"+typeText+"%' "                  
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" like '%"+typeText+"%')";
                            }
                        }else{
                            if (date1.length()!=0 && date2.length()!=0){
                                whereClause = "AND ("
				+ "(Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") >= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date2+"') "
				+ ")";
                            }else if (date1.length()!=0 && date2.length()==0){
                                whereClause = "AND ("
				+ "(Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") >= '"+date1+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date1+"') "
				+ " )";
                            }else if (date1.length()==0 && date2.length()!=0){
                                whereClause = "AND ("
				+ "(Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") >= '"+date2+"' AND Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+")<= '"+date2+"') "
				+ " )";
                            }else{
                                whereClause ="";
                            }
                        }
                    }
                    String order = "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" desc";
		    Vector listBillMain = PstCustomBillMain.listOpenBill(0, 15, 
			"billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
			+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
			+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
			+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' "
                        + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"<>"+oidBillA+" "
			+whereClause, order);
                    
                    html = drawListJoin(iCommand, listBillMain,"loadListJoin", oid );
///////////////////////////////////
/////////////////LOAD LIST JOIN DETAIL
                } else if (loadtype.equals("loadListJoinDetail")){
                    long oidBillA = FRMQueryString.requestLong(request, "oidBillA");
                    
                    Vector listBill = PstCustomBillMain.listItemOpenBill(0, 0, 
			    "billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+oid+"'", "");

		    BillMain billMain = new BillMain();
		    try{
			billMain = PstBillMain.fetchExc(oid);
		    }catch(Exception ex){

		    }
		    html = ""
                    + "<div class='row'>"
			+ "<div class='col-md-12'>"
                            + "<div class='checkbox' id='joinToAll' style='float: right;font-weight:bold;'>" 
                                + "<label style='font-weight:bold;'><input type='checkbox' id='joinAll'  value=''>Join All</label>"
                            + "</div>"
			+ "</div>"
		    + "</div>"
		   
		    + "<div class='row'>"
			+ "<div class='col-md-12'>";
                            
			    html+=drawListJoinDetail(iCommand, listBill, oid, null);
			html+=""
			+ "<input type='hidden' id='"+ FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]+"' value='"+oid+"'>"
                        + "<input type='hidden' id='oidBillA' value='"+oidBillA+"'>"        
			+ "</div>"
		    + "</div>";
///////////////////////////////////
/////////////////CHECK PRINT STATUS
                }else if (loadtype.equals("checkPrintStatus")){
                    String where="";
                    Vector listBillPrint = new Vector(1,1);
                    
                    where = ""+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"="+oid+" and "
                            +""+PstBillDetail.fieldNames[PstBillDetail.FLD_STATUS_PRINT]+"=0";
                    listBillPrint = PstBillDetail.list(0, 0, where, "");
                    html = ""+listBillPrint.size();
///////////////////////////////////
/////////////////LOAD SEARCH MEMBER
		}else if(loadtype.equals("loadsearchmember")){
		    String strQuery = "";
		    String innerJoin = "";
		    Vector listMember = new Vector(1,1);
                    Vector listMemberNoLimit = new Vector(1,1);    
                    int recortToGet = 10;
                    int start = FRMQueryString.requestInt(request, "start");
		    String whereClause = "";
		    boolean inHouseGuest = false;
		    if(cashierString.indexOf("#") != -1){
			inHouseGuest = true;
			cashierString = cashierString.replace("#", "");
		    }
		    
		    switch(Integer.parseInt(cashierString)){
			    
			case PstContactClass.CONTACT_TYPE_DOT_COM_COMPANY: 
			    if(oidData.length() != 0){
				whereClause = "AND ("+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+oidData+"%' OR "+PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" like '%"+oidData+"%' OR "+PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE]+" like '%"+oidData+"%')";
			    }
			    listMember = PstCustomBillMain.listMemberCredit(0, 15, 
				    "CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_DOT_COM_COMPANY+"' "
				    + whereClause, "");
			break;

			case PstContactClass.CONTACT_TYPE_COMPANY: 
			    if(oidData.length() != 0){
				whereClause = "AND ("+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+oidData+"%' OR "+PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" like '%"+oidData+"%' OR "+PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE]+" like '%"+oidData+"%')";
			    }
			    listMember = PstCustomBillMain.listMemberCredit(0, 15, 
				    "CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_COMPANY+"' "
				    + whereClause, "");
			break;

			case PstContactClass.CONTACT_TYPE_EMPLOYEE:
			    if(oidData.length() != 0){
				whereClause = "AND "+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE '%"+oidData+"%'";
			    }
			    listMember = PstCustomBillMain.listEmployee(0, 15, 
				    "CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_EMPLOYEE+"' "
				    + whereClause, "");
			break;

			//GUEST DILEVERY NOT AVAILABLE

			case PstContactClass.CONTACT_TYPE_GUIDE:
			    if(oidData.length() != 0){
				whereClause = "AND ("+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+oidData+"%' OR "+PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" like '%"+oidData+"%' OR "+PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE]+" like '%"+oidData+"%')";
			    }
			    listMember = PstCustomBillMain.listMemberCredit(0, 15, 
				    "CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_GUIDE+"' "
				    + whereClause, "");
			break;

			case PstContactClass.CONTACT_TYPE_MEMBER:
			    
			    if(inHouseGuest == true){
				if(oidData.length() != 0){
				    whereClause = "AND (m."+ PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+oidData+"%' "
					    + "OR k."+ PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER]+" LIKE '%"+oidData+"%' "
					    + "OR n."+ PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" LIKE '%"+oidData+"%')";
				}
				listMember = PstCustomBillMain.listInHouseGuest(0, 15, whereClause, "");
			    }else{
				if(oidData.length() != 0){
				    whereClause = "AND ("+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+oidData+"%' OR "+PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" like '%"+oidData+"%' OR "+PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE]+" like '%"+oidData+"%' )";
				}
				listMember = PstCustomBillMain.listMemberCredit(0, 15, 
					"CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_MEMBER+"' "
					+ whereClause, "");
			    }
			    
			break;

			case PstContactClass.CONTACT_TYPE_TRAVEL_AGENT:
			    if(oidData.length() != 0){
				whereClause = "AND "+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+oidData+"%'";
			    }
			    listMember = PstCustomBillMain.listMemberCredit(0, 15, 
				    "CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_TRAVEL_AGENT+"' "
				    + whereClause, "");
			break;
			    
			case PstContactClass.CONTACT_TYPE_DONOR:
			    if(oidData.length() != 0){
				whereClause = "AND ("+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+oidData+"%' OR "+PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" LIKE '%"+oidData+"%' OR "+PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE]+" LIKE '"+oidData+"' )";
			    }
			    listMember = PstCustomBillMain.listMemberCredit(0, 15, 
				    "CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_DONOR+"' "
				    + whereClause, "");
			break;

			//VENDOR NOT AVAILABLE

			default:
                            //Cek apakah ada room class atau nggak. Jika ada, berarti sistem kasir integrasi dengan hanoman.
                            // Maka yang di select adalah guest
                            int getRoomClass = PstCustomBillMain.getCount("");
                            if (getRoomClass>0){
                                if(!oidData.equals("OUTLET RSV")){
                                    whereClause = ""
                                        + "AND (m."+ PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+oidData+"%' "
                                        + "OR k."+ PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER]+" LIKE '%"+oidData+"%' "
                                        + "OR n."+ PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" LIKE '%"+oidData+"%')";
                                }else{
                                    whereClause = ""
                                        + "AND k."+ PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER]+" IS NULL ";
                                }
                                
				listMember = PstCustomBillMain.listInHouseGuest(start, recortToGet, whereClause, "");
                                listMemberNoLimit = PstCustomBillMain.listInHouseGuest(0, 0, whereClause, "");
                                inHouseGuest=true;
                                searchtype ="guest";
                            }else{
                                listMember = PstCustomBillMain.listMemberCredit(0, 15, 
				    ""+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+oidData+"%' OR "+PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" like '%"+oidData+"%' OR "+PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE]+" like '%"+oidData+"%'", "");
                            }
			    
			break;

		    }
		    
		    if(inHouseGuest == true){
                        double totalPage = Math.ceil(listMemberNoLimit.size()/recortToGet);
			html = drawListSearchInHouseGuest(iCommand, listMember, oid, null, searchtype);
                        
                        String firstActive = "";
                        String lastActive = "";
                        if(start == 0){
                            firstActive = "active";
                        }
                        html+=""
                        + "<div class='row hidden'>"
                            + "<div class='col-md-12'>"
                                + "<ul class='pagination pull-right'>"
                                    + "<li><a href=''><i class='fa fa-angle-double-left'></i>&nbsp;</a></li>"
                                    + "<li><a href=''><i class='fa fa-angle-left'></i>&nbsp;</a></li>";
                        
                                    for(int i = 1; i<=totalPage; i++){
                                        int pageActive = (start/recortToGet)+1;
                                        String active="";
                                        if(i == pageActive){
                                            active = "active";
                                        }
                                        html+=""
                                        + "<li class='"+active+"'><a href=''>"+i+"</a></li>";
                                    }
                                    html+=""
                                    + "<li><a href=''><i class='fa fa-angle-right'></i>&nbsp;</a></li>"
                                    + "<li><a href=''><i class='fa fa-angle-double-right'></i>&nbsp;</a></li>"
                                + "</ul>"
                            + "</div>"
                        + "</div>";
		    }else{
			html = drawListSearchMember(iCommand, listMember, oidDataSelectedGuest, null);
		    }
//////////////////////////////////
/////////////////DRAW RE PRINT
                }else if (loadtype.equals("reprint")){
                    String appRoot = FRMQueryString.requestString(request, "appRoot");
                    long  oidMain = FRMQueryString.requestLong(request, "oidMain");
                    int full = FRMQueryString.requestInt(request, "full");
		    html ="";
                    String isDynamicTemplate = PstSystemProperty.getValueByName("PRINT_DYNAMIC_TEMPLATE");
                    BillMain billMain = new BillMain();
                    try{
                        billMain = PstBillMain.fetchExc(oidMain);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                    if(isDynamicTemplate.equals("1")){
                        html = templateHandler(billMain);
                    }else{
                        html += drawRePrint(oidMain, appRoot, oidMember,full);
                    }
//////////////////////////////////
//////////////////DRAW RE PRINT FOC
                }else if (loadtype.equals("reprintBillFoc")){
                    String appRoot = FRMQueryString.requestString(request, "appRoot");
                    long  oidMain = FRMQueryString.requestLong(request, "oidMain");
                    int full = FRMQueryString.requestInt(request, "full");
                    MatCosting matCosting = new MatCosting();
                    matCosting = PstMatCosting.fetchExc(oidMain);
		    html ="";
                    html += drawRePrintFOC(matCosting,appRoot,full);
//////////////////////////////////
/////////////////LOAD PAYMENT FORM
		}else if(loadtype.equals("loadpay")){

		    double totalPrice = PstCustomBillMain.getTotalPrice(0, 0, 
			    "billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+oid+"' "
			    + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
			    + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
			    + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
			    + "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'", "");

		    Vector listTaxService = PstCustomBillMain.listTaxService(0, 0, 
			"billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
			+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
			+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
			+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' "
			+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+oid+"'", "");
		    
		    Vector listCustomer = PstCustomBillMain.listCustomer(0, 0, 
			    "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+oid+"' "
			    + "AND (contactClass."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_EMPLOYEE+"' "
			    + "OR contactClass."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_MEMBER+"' "
			    + "OR contactClass."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_DONOR+"')", "");
		    
                    if (design==1 && listCustomer.size()==0){
                        listCustomer = PstCustomBillMain.listCustomerHanoman(0, 0, 
                        "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+oid+"' "
                        + " AND (contact.CONTACT_TYPE ='"+PstContactClass.CONTACT_TYPE_EMPLOYEE+"' "
                        + " OR contact.CONTACT_TYPE ='"+PstContactClass.CONTACT_TYPE_MEMBER+"' "
                        + " OR contact.CONTACT_TYPE ='"+PstContactClass.CONTACT_TYPE_DONOR+"')", "");
                    }
                    
		    int listSpecialItem = PstBillDetail.getCount(PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+oid+"' "
			    + "AND "+PstBillDetail.fieldNames[PstBillDetail.FLD_ITEM_PRICE]+"='1'");
		    String customerName ="";
		    long customerOid = 0;
		    long classId = 0;
		    int classType = 0;
                    String classIdString ="";
		    
		    if(listCustomer.size() != 0){
			BillMainCostum billMainCostum = (BillMainCostum) listCustomer.get(0);
			classId = billMainCostum.getClassId();
                        classIdString = ""+classId; 
			customerName = billMainCostum.getGuestName();
			customerOid = billMainCostum.getOID();
			classType = billMainCostum.getClassType();
                        
                        //PENGECEKAN APAKAH GUEST IN HOUSE ATAU MEMBER JIKA TERINTEGRASI DENGAN HANOMAN
                        if (design==1){
                            if (classType == PstContactClass.CONTACT_TYPE_MEMBER){
                                Vector listInHouseTest = PstCustomBillMain.listInHouseGuest(0, 0, 
                                " AND m."+PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID]+"='"+customerOid+"'", "");
                                
                                if (listInHouseTest.size()>0){
                                    classIdString = "#"+classIdString+"";
                                }
                            }
                        }
			
			if(classType == PstContactClass.CONTACT_TYPE_EMPLOYEE){
			    try{
				Employee employee = PstEmployee.fetchExc(billMainCostum.getEmployeeId());
				customerName = employee.getFullName();
			    }catch(Exception ex){
				
			    }
			}
		    }

		    BillMainCostum billMain = new BillMainCostum();
                    
		    double amountTax = 0;
		    double amountService = 0;
		    double balance = totalPrice;
		    double discVal = 0;
		    String readonlyTax = "";
		    String readonlyService = "";
		    String incTaxService = "";
		    String readonlyDisc = "";
		    String openDiscunt = "";
                    double discGlobal = 0;
		    if(listTaxService.size() != 0){
			billMain = (BillMainCostum) listTaxService.get(0);
                        
                        discGlobal = (billMain.getDiscPct()/100)*totalPrice;
                        if(billMain.getTaxInc() == PstBillMain.INC_NOT_CHANGEABLE || billMain.getTaxInc() == PstBillMain.INC_CHANGEABLE){
                            double totalPriceExTaxService = 0;
                            totalPriceExTaxService = (totalPrice-discGlobal)/ ((100+billMain.getServicePct()+billMain.getTaxPct())/100);
                            amountService = totalPriceExTaxService*(billMain.getServicePct()/100);
                            amountTax = totalPriceExTaxService*(billMain.getTaxPct()/100);
                        }else{
                            amountService = (totalPrice-discGlobal)*(billMain.getServicePct()/100);
                            amountTax = (totalPrice-discGlobal)*(billMain.getTaxPct()/100);
                        }
			

                        

			taxInc = billMain.getTaxInc();
			if(billMain.getTaxInc() == PstBillMain.INC_NOT_CHANGEABLE){
			    balance = balance-discGlobal;
			    readonlyService="readonly='readonly'";
			    readonlyTax="readonly='readonly'";
			    readonlyDisc="readonly='readonly'";
			    incTaxService = "<small>(include tax & service)</small>";
			    openDiscunt = "<button type='button' class='btn btn-xs authorize' data-title='OPEN DISCOUNT' data-load-type='opendiscount' style='margin-top:4px;'>Open discount</button>";
			}else if(billMain.getTaxInc() == PstBillMain.NOT_INC_NOT_CHANGEABLE){
			    balance = (totalPrice-discGlobal)+amountService+amountTax;
			    readonlyService="readonly='readonly'";
			    readonlyTax="readonly='readonly'";
			    readonlyDisc="readonly='readonly'";
			    incTaxService = "<small>(exclude tax & service)</small>";
			    openDiscunt = "<button type='button' class='btn btn-xs authorize' data-title='OPEN DISCOUNT' data-load-type='opendiscount' style='margin-top:4px;'>Open discount</button>";
			}else if(billMain.getTaxInc() == PstBillMain.INC_CHANGEABLE){
			    balance = balance-discGlobal;
			    incTaxService = "<small>(include tax & service)</small>";
                            openDiscunt = "<button type='button' class='btn btn-xs authorize' data-title='OPEN DISCOUNT' data-load-type='opendiscount' style='margin-top:4px;'>Open discount</button>";
			}else{
			    balance = (totalPrice-discGlobal)+amountService+amountTax;
			    incTaxService = "<small>(exclude tax & service)</small>";
                            readonlyDisc="readonly='readonly'";
                            openDiscunt = "<button type='button' class='btn btn-xs authorize' data-title='OPEN DISCOUNT' data-load-type='opendiscount' style='margin-top:4px;'>Open discount</button>";
			}

			balanceValue = totalPrice;
			//balance = totalPrice+amountTax+amountService;
		    }
		    
		    
		    String header = "";
		    int intType = 0;
		    switch(classType){
			    
			case PstContactClass.CONTACT_TYPE_DOT_COM_COMPANY: 
			    intType = PstContactClass.CONTACT_TYPE_DOT_COM_COMPANY;
			    header = ".COM COMPANY";
			break;

			case PstContactClass.CONTACT_TYPE_COMPANY: 
			    intType = PstContactClass.CONTACT_TYPE_COMPANY;
			    header = "CORPORATE";
			break;

			case PstContactClass.CONTACT_TYPE_EMPLOYEE:
			    intType = PstContactClass.CONTACT_TYPE_EMPLOYEE;
			    header = "EMPLOYEE";
			break;

			//GUEST DILEVERY NOT AVAILABLE

			case PstContactClass.CONTACT_TYPE_GUIDE:
			    intType = PstContactClass.CONTACT_TYPE_GUIDE;
			    header = "GUIDE";
			break;

			case PstContactClass.CONTACT_TYPE_MEMBER:
			    intType = PstContactClass.CONTACT_TYPE_MEMBER;
			    header = "MEMBER";
			break;


			case PstContactClass.CONTACT_TYPE_TRAVEL_AGENT: 
			    header = "TRAVEL AGENT";
			    intType = PstContactClass.CONTACT_TYPE_TRAVEL_AGENT;
			break;
			    
			case PstContactClass.CONTACT_TYPE_DONOR: 
			    header = "NON MEMBER";
			    intType = PstContactClass.CONTACT_TYPE_DONOR;
			break;

			//VENDOR NOT AVAILABLE

			default:
			    header = "NOT AVAILABLE";
			break;

		    }
		    
		    if(listSpecialItem == 0){
			html+=""
			+ "<input type='hidden' name='SIGNATURE_PATH' value='"+defaultPathSignature+"' id='SIGNATURE_PATH'>"
			+ "<input type='hidden' name='IMAGE_STRING' id='IMAGE_STRING'>"
			+ "<input  type='hidden' name='"+ FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]+"' id='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]+"' value='"+billMain.getOID()+"'>"
			    + "<div class='row'>"
				+ "<div class='col-md-6'>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+ "<div class='col-md-6'><b>Total</b></div>"
						+ "<div class='col-md-6' style='text-align:right;'>"+incTaxService+" "+Formater.formatNumber(totalPrice,"#,###")+"</div>"
						+ "<input type='hidden' name='dpp' value='0'>"
					    + "</div>"
					+ "</div>"
				    + "</div>";
                                    html+=""
				     + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+ "<div class='col-md-6'><b>Discount</b></div>"
						+ "<div class='col-md-3'>"
						    + "<div class='input-group'>"
							+ "<input type='text' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_DISC_PCT]+"' class='form-control charge' value='"+billMain.getDiscPct()+"' id='discountpercentage' "+readonlyDisc+">"
							+ "<div class='input-group-addon'>"
							    + "%"
							+ "</div>"
						    + "</div>"
						+ "</div>"
						+ "<div class='col-md-3'>"
						    + "<input type='text' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_DISC_GLOBAL]+"' class='form-control chargeImp money' value='"+Formater.formatNumber(discGlobal, "#,###")+"' id='discountnominal' "+readonlyDisc+">"
						    + openDiscunt
						+ "</div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>";
                                                double totalsPrice = totalPrice-discGlobal;
                                                double totalCharge = totalsPrice;
                                                if(taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE){
                                                    totalsPrice = totalPrice-discGlobal-amountService-amountTax;
                                                    //totalCharge=totalsPrice+amountService+amountTax;
                                                }
                                                html += ""
						+ "<div class='col-md-6'><b>Balance</b></div>"
						+ "<div class='col-md-6' style='text-align:right;' id='balance'>"+Formater.formatNumber(totalsPrice,"#,###")+"</div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+ "<div class='col-md-6'><b>Service</b></div>"
						+ "<div class='col-md-3'>"
						    + "<div class='input-group'>"
							+ "<input style='font-size:12px;' type='text' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT]+"' value='"+billMain.getServicePct()+"' class='form-control charge' "+readonlyService+" id='service'>"
							    + "<input  type='hidden' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_VALUE]+"' value='"+amountService+"' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_VALUE]+"'>"
							    + "<div class='input-group-addon'>"
							    + "%"
							+ "</div>"
						    + "</div>"
						+ "</div>"
						+ "<div class='col-md-3' style='text-align:right;' id='serviceVal'>"
						    +Formater.formatNumber(amountService,"#,###")+""
						+ "</div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+ "<div class='col-md-6'><b>Total</b></div>"
						+ "<div class='col-md-6' style='text-align:right;' id='totalAfterService'>"+Formater.formatNumber(totalsPrice+amountService,"#,###")+"</div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+ "<div class='col-md-6'><b>Tax</b></div>"
						+ "<div class='col-md-3'>"
						    + "<div class='input-group'>"
							+ "<input style='font-size:12px;' type='text' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT]+"' value='"+billMain.getTaxPct()+"' class='form-control charge' "+readonlyTax+" id='tax'>"
							+ "<input type='hidden' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_VALUE]+"' value='"+amountTax+"' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_DISC_GLOBAL]+"'>"
							+ "<div class='input-group-addon'>"
							    + "%"
							+ "</div>"
							+ "<input type='hidden' value='"+(balance)+"' id='tempCostumBalance'>"
						    + "</div>"
						+ "</div>"
						+ "<div class='col-md-3' style='text-align:right;' id='taxVal'>"
						    + ""+Formater.formatNumber(amountTax,"#,###")+""
						+ "</div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+ "<div class='col-md-6'><b>Total</b></div>"
						+ "<div class='col-md-6' style='text-align:right;' id='totalAfterTax'>"
						    + ""+Formater.formatNumber(totalsPrice+amountService+amountTax,"#,###")+""
						+ "</div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+ "<div class='col-md-6'><b>Credit Card Charge</b></div>"
						+ "<div class='col-md-3'>"
						    + "<div class='input-group'>"
							+ "<input type='text' name='creditcardchargepercent' value='0' class='form-control charge' readonly='readonly' id='ccCharge'>"
							+ "<input type='hidden' name='creditcardcharge' value='0' id='creditcardcharge'>"
							+ "<div class='input-group-addon'>"
							    + "%"
							+ "</div>"
						    + "</div>"
						+ "</div>"
						+ "<div class='col-md-3' style='text-align:right;' id='ccChargeVal'>"
						    + "0"
						+ "</div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+ "<div class='col-md-6'><b>Total Charge</b></div>"
						+ "<div class='col-md-6' style='text-align:right;' id='totalCharge'>"
						    + "<b>"+Formater.formatNumber(balance,"#,###")+"</b>"
						+ "</div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+ "<div class='col-md-12'>Transaction Note</div>"
						+ "<div class='col-md-12'>"
						    + "<textarea name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_NOTES]+"' class='form-control'>"+billMain.getNotes()+"</textarea>"
						+ "</div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				+ "</div>"
				+ "<div class='col-md-6'>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+ "<div class='col-md-6'>"
						    + "<b>Customer Type</b>"
						+ "</div>"
						+ "<div class='col-md-6'>";
						    Vector customer_key = new Vector(1,1);
						    Vector customer_val = new Vector(1,1);

						    customer_key.add("");
						    customer_val.add("-- Select --");
                                                    customer_key.add("0");
						    customer_val.add("NONE");

						    Vector listClass = PstContactClass.list(0, 0, 
							    ""+ PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='7' "
							    + "OR "+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='4' "
							    + "OR "+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='10'", 
							PstContactClass.fieldNames[PstContactClass.FLD_CLASS_NAME]+" ASC");

						    int getRoomClass = PstCustomBillMain.getCount("");
						    if(listClass.size() != 0){
							for(int i = 0; i < listClass.size(); i++){
							    ContactClass contactClass = (ContactClass) listClass.get(i);

							    if(contactClass.getClassType() == 7){
								if(design != 0){
								    customer_key.add(""+contactClass.getOID());
								    customer_val.add("MEMBER");
								    if(getRoomClass > 0){
									customer_key.add("#"+contactClass.getOID());
									customer_val.add("IN HOUSE GUEST");
								    }

								}else{
								    customer_key.add(""+contactClass.getOID());
								    customer_val.add(""+contactClass.getClassName());
								}

							    }else{
								customer_key.add(""+contactClass.getOID());
								customer_val.add(""+contactClass.getClassName());
							    }

							}
						    }
                                                    if (classIdString.length()==0){
                                                        classIdString = "0";
                                                    }
						    html+= ControlCombo.drawBootsratap("member", null, ""+classIdString, customer_key, customer_val, "required='true'", "form-control");
						html+= ""
						+ "</div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<input type='hidden' name='"+ FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] +"' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID]+"' value='"+customerOid+"'>"
				    + "<input type='hidden' name='CUSTOMER_NAME' id='CUSTOMER_NAME' value='"+customerName+"'>"
				    +"<div id='CONTENT_SUB_PAYMENT_SYSTEM'>";

					if(classType > 0){
					    html+= ""
					    + "<div class='row'>"
						+ "<div class='box-body'>"
						    + "<div class='col-md-12'>"
							+ "<div class='col-md-6'><b>"+header+"</b></div>"
							+ "<div class='col-md-6'>"
							    + "<input type='hidden' name='"+ FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE]+"' id='"+ FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE]+"' value='"+intType+"'>"
							    + "<input type='hidden' name='searchtype' id='searchtype' value='guest'>"
							    + ""
							    + "<div class='input-group'>"
							
								+ "<input type='text' class='form-control' name='employee' id='employee' required='required' value='"+customerName+"'>"
								+ "<div  id='btnOpenSearchMember' class='input-group-addon btn btn-primary'>"
								    + "<i class='fa fa-search'></i>"
								+ "</div>"
							    + "</div>"
							+ "</div>"
						    + "</div>"
						+ "</div>"
					    + "</div>";
					}

				    html+= "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+ "<div class='col-md-6'>"
						    + "<b>Transaction Type</b>"
						+ "</div>"
						+ "<div class='col-md-6'>"
						    + "<select name='transaction_type' id='transaction_type' class='form-control'>"
							    + "<option value=''>-- Select --</option>"
							    + "<option value='0'>CASH / CC / DEBIT </option>"
							    + "<option value='1'>CREDIT</option>"
							    + "<option value='2'>FOC</option>"
						    + "</select>"
						+ "</div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12' id='CONTENT_PAYMENT_SYSTEM'>"

					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12' id='CONTENT_SUB_PAYMENT_SYSTEM_CASH'>"

					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12' id='CONTENT_PAYMENT_TYPE'>"

					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+"<div id='result' style='display:none;'></div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12' style='display:none;' id='signatureArea'>"
						+ "Guest Signature<br>"
						+ "<div id='signature'></div>"
						+ "<button type='button' class='form-control btn btn-primary' id='resetsignature'>"
						    + "<i class='fa fa-refresh'></fa> Reset"
						+ "</button>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='row'>"
					+ "<div class='box-body'>"
					    + "<div class='col-md-12'>"
						+"<div id='resultSave' style='display:none;'></div>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				    
				+ "</div>"
			+ "</div>";
		    }else{
			html = "SPECIAL";
		    }
		    
		    displayTotal = Formater.formatNumber(balance,"#,###");
		    costumBalance = balance;

		    
		    
		    
/////////////////////////////////////////////////
/////////////////LOAD PAYMENT TYPE OR MEMBER TYPE
		}else if(loadtype.equals("loadpayment")){
		    balanceValue = getBalance;
		    creditCardCharge = 0;
		    displayTotal = Formater.formatNumber(costumBalance,"#,###");
		    if(oidData.equals("0") || oidData.equals("2")){
			Vector paymentType_key = new Vector(1,1);
			Vector paymentType_value = new Vector(1,1);

			paymentType_key.add("");
			paymentType_value.add("-- Select --");
			
			if(oidData.equals("0")){
			    paymentType_key.add(""+ PstCustomBillMain.PAYMENT_TYPE_CASH);
			    paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CASH]);

			    paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD);
			    paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD]);

			    //paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_BG);
			    //paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_BG]);

			    paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_CHECK);
			    paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CHECK] + " OR " + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_BG]);

			    paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD);
			    paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD]);

			    paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR);
			    paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR]);

			    paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT);
			    paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT]);
			}else{
			    paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_FOC);
			    paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_FOC]);
			}
			
			
			
			html = ""
			+ "<div class='col-md-6'>"
			    + "<b>Payment Type</b>"
			+ "</div>"
			+ "<div class='col-md-6'>"
			    + ControlCombo.drawBootsratap("MASTER_"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE], null, "", paymentType_key, paymentType_value, "required='true'", "form-control")
			+ "</div>";
		    }


		    
//////////////////////////////////
/////////////////LOAD LIST PAYMENT TYPE
		}else if(loadtype.equals("loadlistpayment")){
		    balanceValue = getBalance;
		    creditCardCharge = 0;
		    displayTotal = Formater.formatNumber(costumBalance,"#,###");
		    String description = "";
		    //if(oidData.equals("0")){
			Vector paymentType_key = new Vector(1,1);
			Vector paymentType_value = new Vector(1,1);

			paymentType_key.add("");
			paymentType_value.add("-- Select --");
			
			String whereClause = "";
			switch(Integer.parseInt(oidData)){
			    case PstCustomBillMain.PAYMENT_TYPE_CASH :
				whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='1'";
				description = "Payment Detail";
			    break;
				
			    case PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD :
				whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='1' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='0'";
				description = "Type Card";
			    break;
				
			    case PstCustomBillMain.PAYMENT_TYPE_BG :
				whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='1' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='2'";
				description = "Payment Detail";
			    break;
				
			    case PstCustomBillMain.PAYMENT_TYPE_CHECK :
				whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='1' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='0'";
				description = "Payment Detail";
			    break;
				
			    case PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD :
				whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='2'";
				description = "Bank Name";
			    break;
				
			    case PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR :
				whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='4'";
				
				description = "Payment Detail";
			    break;
				
			    case PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT :
				whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='5'";
                                description = "Payment Detail";
                            break;
				
			    case PstCustomBillMain.PAYMENT_TYPE_FOC :
				whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
					+ "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='6'";
				description = "Payment Detail";
			    break;
			}
			
			Vector listPaymentSystem = PstPaymentSystem.list(0, 0, whereClause, "");
			if(listPaymentSystem.size() != 0){
			    for(int i=0;i<listPaymentSystem.size();i++){
				PaymentSystem paymentSystem = (PaymentSystem) listPaymentSystem.get(i);
				paymentType_key.add(""+paymentSystem.getOID());
				paymentType_value.add(""+paymentSystem.getPaymentSystem());
			    }
			}
			html = ""
			+ "<div class='col-md-6'>"
			    + "<b>"+description+"</b>"
			+ "</div>"
			+ "<div class='col-md-6'>"
			    + ControlCombo.drawBootsratap(""+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE], null, "", paymentType_key, paymentType_value, "required='true'", "form-control")
			+ "</div>";
		    //}
//////////////////////////////////
/////////////////LOAD LIST PAYMENT FOR MULTI PAYMENT
                } else if (loadtype.equals("loadsubpaymentmulti")){
		    double remain = FRMQueryString.requestDouble(request, "remainMulti");
                    String oidPayment = FRMQueryString.requestString(request, "oidMultiPayment");
                    creditCardCharge = 0;
		    String displayTotalMulti = Formater.formatNumber(remain,"#,###");
		    String description = "";
                    Vector paymentType_key = new Vector(1,1);
                    Vector paymentType_value = new Vector(1,1);

                    paymentType_key.add("");
                    paymentType_value.add("-- Select --");

                    String whereClause = "";
                    switch(Integer.parseInt(oidPayment)){
                        case PstCustomBillMain.PAYMENT_TYPE_CASH :
                            whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='1'";
                            description = "Payment Detail";
                        break;

                        case PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD :
                            whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='1' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='0'";
                            description = "Type Card";
                        break;

                        case PstCustomBillMain.PAYMENT_TYPE_BG :
                            whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='1' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='2'";
                            description = "Payment Detail";
                        break;

                        case PstCustomBillMain.PAYMENT_TYPE_CHECK :
                            whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='1' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='0'";
                            description = "Payment Detail";
                        break;

                        case PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD :
                            whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='2'";
                            description = "Bank Name";
                        break;

                        case PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR :
                            whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='4'";

                            description = "Payment Detail";
                        break;

                        case PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT :
                            whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='5'";
                        break;

                        case PstCustomBillMain.PAYMENT_TYPE_FOC :
                            whereClause = ""+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO]+"='0' "
                                    + "AND "+PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE]+"='6'";
                            description = "Payment Detail";
                        break;
                    }

                    Vector listPaymentSystem = PstPaymentSystem.list(0, 0, whereClause, "");
                    if(listPaymentSystem.size() != 0){
                        for(int i=0;i<listPaymentSystem.size();i++){
                            PaymentSystem paymentSystem = (PaymentSystem) listPaymentSystem.get(i);
                            paymentType_key.add(""+paymentSystem.getOID());
                            paymentType_value.add(""+paymentSystem.getPaymentSystem());
                        }
                    }
                    html = ""
                    + "<div class='col-md-3'>"
                        + ""+description+""
                    + "</div>"
                    + "<div class='col-md-9'>"
                        + ControlCombo.drawBootsratap(""+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE]+"_MULTI", null, "", paymentType_key, paymentType_value, "required='true'", "form-control payTypes2")
                    + "</div>";
                        
//////////////////////////////////
/////////////////LOAD PAYMENT TYPE
		}else if(loadtype.equals("loadsubpayment")){
		    balanceValue = getBalance;


		    try{
			PaymentSystem paymentSystem = PstPaymentSystem.fetchExc(oid);
			creditCardCharge = paymentSystem.getBankCostPercent();
                        String cardReader = FRMQueryString.requestString(request, "cardReader");
			double paid = costumBalance+(costumBalance*(creditCardCharge/100));
                        String cssReader="";
                        if (cardReader.equals("0")){
                            cssReader="";
                        }else{
                            cssReader="readonly='readonly'";
                        }
			if(paymentSystem.isCardInfo() == true 
				&& paymentSystem.isBankInfoOut() == false 
				&& paymentSystem.getPaymentType() == 0 
				&& paymentSystem.isCheckBGInfo() == false){
			    paidValue = paid;
			    Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
			    Vector currency_key = new Vector(1,1);
			    Vector currency_value = new Vector(1,1);
                            Vector data_rate = new Vector(1,1);

			    if(listCurrency.size() != 0){
				for(int i = 0;i<listCurrency.size(); i++){
				    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
				    currency_key.add(""+currencyType.getOID());
				    currency_value.add(""+currencyType.getCode());
                                    double rate = PstCurrencyType.getExchangeRate(" CURRENCY_TYPE_ID ='"+currencyType.getOID()+"'");
                                    data_rate.add(""+rate+"");
				}
			    }

			    html = ""
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>Card Name</div>"
					+ "<div class='col-md-6'>"
					    + "<div class='input-group'>"
						+ "<input type='text' class='form-control getaccount' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]+"' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME].trim()+"'  required>"
						+ "<div class='btn btn-primary input-group-addon' id='ccReScan''>"
						    + "<i class='fa fa-refresh'></i>"
						+ "</div>"
					    + "</div>"
					    
					+"</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				 + "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>Card No</div>"
					+ "<div class='col-md-6'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"'  required></div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				 + "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>Bank</div>"
					+ "<div class='col-md-6'><input type='text'  class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' value='"+paymentSystem.getBankName()+"' required></div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				 + "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>Validity</div>"
					+ "<div class='col-md-6'>"
					    + "<div class='input-group'>" +
						"<div class='input-group-addon'>" +
						    "<i class='fa fa-calendar'></i>" +
						"</div>"
						+ "<input type='text' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"' readonly='readonly' required>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
                            + "<div class='row'>"
				+ "<div class='box-body'>"
				   + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    
					+ "</div>"
					+ "<div class='col-md-6'>"
					    + "<b style='font-size: 36px;'>"+Formater.formatNumber(costumBalance,"#,###.##")+"</b>"
					+ "</div>"
				   + "</div>"
			       + "</div>"
                            + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				   + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + ControlCombo.drawBootsratapData(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control",data_rate,"rate")
					+ "</div>"
					+ "<div class='col-md-6'>"
					    +"<input style='font-size: 26px; height: 46px;' type='text' id='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' class='form-control money calc' value='"+Formater.formatNumber(paid,"#,###.##")+"'>"
					+ "</div>"
				   + "</div>"
			       + "</div>"
			   + "</div>";


			    
		/////////////////////
		/////////CASH PAYMENT	   
			}else if(paymentSystem.isCardInfo() == false 
				&& paymentSystem.isBankInfoOut() == false 
				&& paymentSystem.getPaymentType() == 1 
				&& paymentSystem.isCheckBGInfo() == false){
			    paidValue = 0;
			    Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
			    Vector currency_key = new Vector(1,1);
			    Vector currency_value = new Vector(1,1);
                            Vector data_rate = new Vector(1,1);

			    if(listCurrency.size() != 0){
				for(int i = 0;i<listCurrency.size(); i++){
				    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
				    currency_key.add(""+currencyType.getOID());
				    currency_value.add(""+currencyType.getCode());
                                    double rate = PstCurrencyType.getExchangeRate(" CURRENCY_TYPE_ID ='"+currencyType.getOID()+"'");
                                    data_rate.add(""+rate+"");
				}
			    }
			    html+=""
                                + "<div class='row'>"
                                    + "<div class='box-body'>"
                                       + "<div class='col-md-12'>"
                                            + "<div class='col-md-6'>"

                                            + "</div>"
                                            + "<div class='col-md-6'>"
                                                + "<b style='font-size: 36px;'>"+Formater.formatNumber(costumBalance,"#,###.##")+"</b>"
                                            + "</div>"
                                       + "</div>"
                                   + "</div>"
                                + "</div>"
				+ "<div class='row'>"
				    + "<div class='box-body'>"
				       + "<div class='col-md-12'>"
					    + "<div class='col-md-2'>"
						+paymentSystem.getPaymentSystem()
					    + "</div>"
					    + "<div class='col-md-3'>"
                                                + ControlCombo.drawBootsratapData(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control",data_rate,"rate")
						
					    + "</div>"
					    + "<div class='col-md-7'>"
						+"<input style='font-size: 26px; height: 46px;' type='text' id='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' class='form-control calc money'>"
					    + "</div>"
				       + "</div>"
				   + "</div>"
			       + "</div>";
			    
			    
			    
		/////////////////////////
		//////////VOUCHER PAYMENT
			}else if(paymentSystem.isCardInfo() == false 
				&& paymentSystem.isBankInfoOut() == false 
				&& (paymentSystem.getPaymentType() == 4 || paymentSystem.getPaymentType() == 5) 
				&& paymentSystem.isCheckBGInfo() == false){
			    balanceValue = getBalance;
			    paidValue = paidValue;
			     html+= 
				"<div class='row'>"
				    + "<div class='box-body'>"
				       + "<div class='col-md-12'>"
					    + "<div class='col-md-6'>"
						+ "Voucher Code / Barcode"
					    + "</div>"
					    + "<div class='col-md-6'>"
                                                + "<div class='input-group'>"
                                                    + "<input type='hidden' id='VOUCHER_ID' name='VOUCHER_NAME'>"
                                                    + "<input type='text' readonly='readonly' id='voucherCodeInput' name='VOUCHER_CODE' class='form-control' placeholder='Search voucher'>" 
                                                    + "<span class='input-group-btn'>" 
                                                        + " <button id='searchVoucher' data-source='1' class='btn btn-default' type='button'><i class='fa fa-search'></i> &nbsp;</button>" 
                                                    + "</span>" 
                                                + "</div>"
						//+"<input type='text'  name='VOUCHER_CODE' class='form-control' required='required'>"
					    + "</div>"
				       + "</div>"
				   + "</div>"
			       + "</div>"
                               + "<div class='row'>"
				    + "<div class='box-body'>"
				       + "<div class='col-md-12'>"
					    + "<div class='col-md-6'>"
						+ "Voucher Value"
					    + "</div>"
					    + "<div class='col-md-6'>"                                                                                
                                                + "<input type='text' style='font-size: 26px; height: 46px;' readonly='readonly' id='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' class='form-control' value='0'>"        
					    + "</div>"
				       + "</div>"
				   + "</div>"
			       + "</div>";
			     
			     
			     
		//////////////////
		////////PAYMENT BG
			}else if(paymentSystem.isCardInfo() == false 
				&& paymentSystem.isBankInfoOut() == true 
				&& paymentSystem.getPaymentType() == 2
				&& paymentSystem.isCheckBGInfo() == false){
			    paidValue = paid;
			    Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
			    Vector currency_key = new Vector(1,1);
			    Vector currency_value = new Vector(1,1);
                            Vector data_rate = new Vector(1,1);

			    if(listCurrency.size() != 0){
				for(int i = 0;i<listCurrency.size(); i++){
				    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
				    currency_key.add(""+currencyType.getOID());
				    currency_value.add(""+currencyType.getCode());
                                    double rate = PstCurrencyType.getExchangeRate(" CURRENCY_TYPE_ID ='"+currencyType.getOID()+"'");
                                    data_rate.add(""+rate+"");
				}
			    }

			    html = ""
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>Bank Name</div>"
					+ "<div class='col-md-6'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' value='"+paymentSystem.getBankName()+"' required></div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				 + "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>Bank Account Number</div>"
					+ "<div class='col-md-6'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]+"' required></div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				 + "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>BG Number</div>"
					+ "<div class='col-md-6'><input type='text'  class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] +"' required></div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				 + "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>Expired Date</div>"
					+ "<div class='col-md-6'>"
					    + "<div class='input-group'>" +
						"<div class='input-group-addon'>" +
						    "<i class='fa fa-calendar'></i>" +
						"</div>"
						+ "<input type='text' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"' required>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
                            + "<div class='row'>"
				+ "<div class='box-body'>"
				   + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    
					+ "</div>"
					+ "<div class='col-md-6'>"
					    + "<b style='font-size: 36px;'>"+Formater.formatNumber(costumBalance,"#,###.##")+"</b>"
					+ "</div>"
				   + "</div>"
			       + "</div>"
                            + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				   + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + ControlCombo.drawBootsratapData(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control",data_rate,"rate")
					+ "</div>"
					+ "<div class='col-md-6'>"
					    +"<input style='font-size: 26px; height: 46px;' type='text' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' class='form-control money calc' value='"+Formater.formatNumber(paid,"#,###.##")+"'>"
					+ "</div>"
				   + "</div>"
			       + "</div>"
			   + "</div>";
			
			
			
		//////////////////////
		/////////PAYMENT CHECK
			}else if(paymentSystem.isCardInfo() == false 
				&& paymentSystem.isBankInfoOut() == false 
				&& paymentSystem.getPaymentType() == 0
				&& paymentSystem.isCheckBGInfo() == true){
			    paidValue = paid;
			    Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
			    Vector currency_key = new Vector(1,1);
			    Vector currency_value = new Vector(1,1);
                            Vector data_rate = new Vector(1,1);

			    if(listCurrency.size() != 0){
				for(int i = 0;i<listCurrency.size(); i++){
				    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
				    currency_key.add(""+currencyType.getOID());
				    currency_value.add(""+currencyType.getCode());
                                    double rate = PstCurrencyType.getExchangeRate(" CURRENCY_TYPE_ID ='"+currencyType.getOID()+"'");
                                    data_rate.add(""+rate+"");
				}
			    }

			    html = ""
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>Bank Name</div>"
					+ "<div class='col-md-6'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' value='"+paymentSystem.getBankName()+"' required></div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				 + "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>Bank Account Number</div>"
					+ "<div class='col-md-6'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]+"' required></div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				 + "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>Cheque Number</div>"
					+ "<div class='col-md-6'><input type='text'  class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"' required></div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				 + "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>Expired Date</div>"
					+ "<div class='col-md-6'>"
					    + "<div class='input-group'>" +
						"<div class='input-group-addon'>" +
						    "<i class='fa fa-calendar'></i>" +
						"</div>"
						+ "<input type='text' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"' required>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
                            + "<div class='row'>"
				+ "<div class='box-body'>"
				   + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    
					+ "</div>"
					+ "<div class='col-md-6'>"
					    + "<b style='font-size: 36px;'>"+Formater.formatNumber(costumBalance,"#,###.##")+"</b>"
					+ "</div>"
				   + "</div>"
			       + "</div>"
                            + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				   + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + ControlCombo.drawBootsratapData(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control",data_rate,"rate")
					+ "</div>"
					+ "<div class='col-md-6'>"
					    +"<input type='text' style='font-size: 26px; height: 46px;' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' class='form-control money calc' value='"+Formater.formatNumber(paid,"#,###.##")+"'>"
					+ "</div>"
				   + "</div>"
			       + "</div>"
			   + "</div>";
			   
                ///////////////////////
                //////// FOC PAYMENT
                        }else if(paymentSystem.isCardInfo() == false 
				&& paymentSystem.isBankInfoOut() == false 
				&& paymentSystem.getPaymentType() == 6
				&& paymentSystem.isCheckBGInfo() == false){
                           
                            html = "" 
                            + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-12'>Remark</div>"
					+ "<div class='col-md-12'><textarea name='remarkFoc' class='form-control' style='width:100%'></textarea></div>"
				    + "</div>"
				+ "</div>"
			    + "</div>";
		///////////////////////
		///////// DEBIT PAYMENT
			}else if(paymentSystem.isCardInfo() == false 
				&& paymentSystem.isBankInfoOut() == false 
				&& paymentSystem.getPaymentType() == 2
				&& paymentSystem.isCheckBGInfo() == false){
			    paidValue = paid;
			    Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
			    Vector currency_key = new Vector(1,1);
			    Vector currency_value = new Vector(1,1);
                            Vector data_rate = new Vector(1,1);

			    if(listCurrency.size() != 0){
				for(int i = 0;i<listCurrency.size(); i++){
				    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
				    currency_key.add(""+currencyType.getOID());
				    currency_value.add(""+currencyType.getCode());
                                    double rate = PstCurrencyType.getExchangeRate(" CURRENCY_TYPE_ID ='"+currencyType.getOID()+"'");
                                    data_rate.add(""+rate+"");
				}
			    }
                            //cek system property
                            int cardInfo = 0;
                            String cardInfoStr = PstSystemProperty.getValueByName("CASHIER_SHOW_DEBIT_CARD_INFO");
                            try{
                                cardInfo = Integer.parseInt(cardInfoStr);
                            }catch(Exception ex){
                                cardInfo=0;
                            }
			    html = "";
			   
                            if (cardInfo==1){
                                
                                html +=""
                                + "<div class='row'>"
                                    + "<div class='box-body'>"
                                        + "<div class='col-md-12'>"
                                            + "<div class='col-md-6'>Bank Name</div>"
                                            + "<div class='col-md-6'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' value='"+paymentSystem.getBankName()+"' required></div>"
                                        + "</div>"
                                    + "</div>"
                                + "</div>"
                                + "<div class='row'>"
                                     + "<div class='box-body'>"
                                        + "<div class='col-md-12'>"
                                            + "<div class='col-md-6'>Card Number</div>"
                                            + "<div class='col-md-6'>"
                                                + "<div class='input-group'>"
                                                    + "<input type='text' class='form-control getaccountDebit' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"' id='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"' required>"
                                                    + "<div class='btn btn-primary input-group-addon' id='debitReScan'>"
                                                         + "<i class='fa fa-refresh'></i>"
                                                    + "</div>"
                                            + "</div>"
                                        + "</div>"
                                    + "</div>"
                                + "</div>"
                                + "<div class='row'>"
                                     + "<div class='box-body'>"
                                        + "<div class='col-md-12'>"
                                            + "<div class='col-md-6'>Expired Date</div>"
                                            + "<div class='col-md-6'>"
                                                + "<div class='input-group'>" +
                                                    "<div class='input-group-addon'>" +
                                                        "<i class='fa fa-calendar'></i>" +
                                                    "</div>"
                                                    + "<input type='text' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"' readonly='readonly'  required>"
                                                + "</div>"
                                            + "</div>"
                                        + "</div>"
                                    + "</div>"
                                + "</div>";
                            }else{
                                html+= ""
                                + " <input type='hidden' value='-' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' value='"+paymentSystem.getBankName()+"' required>"
                                + " <input type='hidden' value='-' class='form-control getaccountDebit' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"' id='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"' required>"
                                + " <input type='hidden' value='"+Formater.formatDate(new Date(),"yyyy-MM-dd") +"' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"' readonly='readonly'  required>"
                                + "";
                            }
                            
                            html+=""
                            + "<div class='row'>"
				+ "<div class='box-body'>"
				   + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    
					+ "</div>"
					+ "<div class='col-md-6'>"
					    + "<b style='font-size: 36px;'>"+Formater.formatNumber(costumBalance,"#,###.##")+"</b>"
					+ "</div>"
				   + "</div>"
			       + "</div>"
                            + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				   + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + ControlCombo.drawBootsratapData(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control",data_rate,"rate")
					+ "</div>"
					+ "<div class='col-md-6'>"
					    +"<input style='font-size: 26px; height: 46px;' type='text' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' class='form-control money calc' value='"+Formater.formatNumber(paid,"#,###.##")+"'>"
					+ "</div>"
				   + "</div>"
			       + "</div>"
			   + "</div>";
			}


		    }catch(Exception ex){
		       //ex.printStackTrace();
		    }
///////////////////////////////////////
/////////////////LOAD SUB MULTI PAYMENT
                }else if (loadtype.equals("loadsubmultipayment")){
                    double remain = FRMQueryString.requestDouble(request, "remainMulti");
                    long oidPayment = FRMQueryString.requestLong(request, "oidMultiPayment");
                    balanceValue= remain;


		    try{
			PaymentSystem paymentSystem = PstPaymentSystem.fetchExc(oidPayment);
			creditCardCharge = paymentSystem.getBankCostPercent();
                        //String cardReader = FRMQueryString.requestString(request, "cardReader");
                        String cardReader = PstSystemProperty.getValueByName("CASHIER_USE_READERCARD");
			double paid = costumBalance+(costumBalance*(creditCardCharge/100));
                        remain = remain + (remain*(creditCardCharge/100));
                        String cssReader="";
                        if (cardReader.equals("0")){
                            cssReader="";
                        }else{
                            cssReader="readonly='readonly'";
                        }
			if(paymentSystem.isCardInfo() == true 
				&& paymentSystem.isBankInfoOut() == false 
				&& paymentSystem.getPaymentType() == 0 
				&& paymentSystem.isCheckBGInfo() == false){
			    paidValue = paid;
			    Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
			    Vector currency_key = new Vector(1,1);
			    Vector currency_value = new Vector(1,1);

			    if(listCurrency.size() != 0){
				for(int i = 0;i<listCurrency.size(); i++){
				    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
				    currency_key.add(""+currencyType.getOID());
				    currency_value.add(""+currencyType.getCode());
				}
			    }

			    html = ""
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"                               
                                + "<div class='col-md-3'>Card Name</div>"
                                + "<div class='col-md-9'>"
                                    + "<div class='input-group'>"
                                        + "<input type='text' class='form-control getaccount' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]+"_MULTI' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME].trim()+"'  required>"
                                        + "<div class='btn btn-primary input-group-addon' id='ccReScan''>"
                                            + "<i class='fa fa-refresh'></i>"
                                        + "</div>"
                                    + "</div>"
                                +"</div>"                               				
			    + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>Card No</div>"
                                + "<div class='col-md-9'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"_MULTI' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"_MULTI'  required></div>"				    
			    + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>Bank</div>"
                                + "<div class='col-md-9'><input type='text'  class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"_MULTI' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"_MULTI' value='"+paymentSystem.getBankName()+"' required></div>"				   
			    + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>Validity</div>"
                                + "<div class='col-md-9'>"
                                    + "<div class='input-group'>" +
                                        "<div class='input-group-addon'>" +
                                            "<i class='fa fa-calendar'></i>" +
                                        "</div>"
                                        + "<input type='text' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"_MULTI' readonly='readonly' required>"
                                    + "</div>"
                                + "</div>"				    
			    + "</div>"
                            + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>"					    
                                + "</div>"
                                + "<div class='col-md-9'>"
                                    + "<b>"+Formater.formatNumber(remain,"#,###.##")+"</b>"
                                + "</div>"				   
                            + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"	
                                + "<div class='col-md-3'>"
                                    
                                + "</div>"
                                + "<div class='col-md-2'>"
                                    + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID]+"_MULTI", null, "", currency_key, currency_value, "", "form-control")
                                + "</div>"
                                + "<div class='col-md-7'>"
                                    +"<input type='text' style='font-size: 26px; height: 46px;' id='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"_MULTI' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"_MULTI' class='form-control calcMoney' value='"+Formater.formatNumber(paid,"#,###.##")+"'>"
                                + "</div>"				  
                            + "</div>"	
                            + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"	
                                + "<div class='col-md-3'>"                                   
                                + "</div>"                             
                                + "<div class='col-md-9'>"
                                    +"<input type='text' readonly id='calcAmount' name='calcAmount' class='form-control money calc' value='"+Formater.formatNumber(remain,"#,###.##")+"'>"
                                + "</div>"				  
			   + "</div>";
		/////////////////////
		/////////CASH PAYMENT	   
			}else if(paymentSystem.isCardInfo() == false 
				&& paymentSystem.isBankInfoOut() == false 
				&& paymentSystem.getPaymentType() == 1 
				&& paymentSystem.isCheckBGInfo() == false){
			    paidValue = 0;
			    Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
			    Vector currency_key = new Vector(1,1);
			    Vector currency_value = new Vector(1,1);

			    if(listCurrency.size() != 0){
				for(int i = 0;i<listCurrency.size(); i++){
				    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
				    currency_key.add(""+currencyType.getOID());
				    currency_value.add(""+currencyType.getCode());
				}
			    }
			    html+=""
                                + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"                                  
                                    + "<div class='col-md-3'>"
                                    + "</div>"
                                    + "<div class='col-md-9'>"
                                        + "<b>"+Formater.formatNumber(remain,"#,###.##")+"</b>"
                                    + "</div>"                                      
                                + "</div>"
				+ "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				  
                                    + "<div class='col-md-3'>"
                                       
                                    + "</div>"
                                    + "<div class='col-md-2'>"
                                        + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID]+"_MULTI", null, "", currency_key, currency_value, "required='true'", "form-control")
                                    + "</div>"
                                    + "<div class='col-md-7'>"
                                        +"<input type='text' id='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"_MULTI' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"_MULTI' class='form-control calcMoney'>"
                                    + "</div>"				       
                                + "</div>"
                                + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"	
                                    + "<div class='col-md-3'>"                                   
                                    + "</div>"                             
                                    + "<div class='col-md-9'>"
                                        +"<input type='text' readonly id='calcAmount' name='calcAmount' class='form-control money calc' value='"+Formater.formatNumber(remain,"#,###.##")+"'>"
                                    + "</div>"				  
                               + "</div>";
			    			    			    
		/////////////////////////
		//////////VOUCHER PAYMENT
			}else if(paymentSystem.isCardInfo() == false 
				&& paymentSystem.isBankInfoOut() == false 
				&& (paymentSystem.getPaymentType() == 4 || paymentSystem.getPaymentType() == 5) 
				&& paymentSystem.isCheckBGInfo() == false){
			    balanceValue = getBalance;
			    paidValue = paidValue;
			     html+= 
				"<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				    
                                    + "<div class='col-md-3'>"
                                        + "Voucher Code / Barcode"
                                    + "</div>"
                                    + "<div class='col-md-9'>"
                                        + "<div class='input-group'>"
                                            + "<input type='hidden' id='FRM_FIELD_VOUCHER_ID_MULTI' name='FRM_FIELD_VOUCHER_ID_MULTI'>"
                                            + "<input type='text' readonly='readonly' id='voucherCodeInput2' name='VOUCHER_CODE' class='form-control' placeholder='Search voucher'>" 
                                            + "<span class='input-group-btn'>" 
                                                    + " <button id='searchVoucher2' data-source='2' class='btn btn-default' type='button'><i class='fa fa-search'></i> &nbsp;</button>" 
                                            + "</span>" 
                                        + "</div>"
                                        //+"<input type='text' class='voucherCode' name='VOUCHER_CODE' class='form-control' required='required'>"
                                    + "</div>"				       
			       + "</div>"
                               + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				    
                                    + "<div class='col-md-3'>"
                                        + "Voucher Value"
                                    + "</div>"
                                    + "<div class='col-md-9'>"
                                        +"<input readonly='readonly' id='FRM_FIELD_PAY_AMOUNT_MULTI' name='FRM_FIELD_PAY_AMOUNT_MULTI' class='form-control calcMoney' value='0' type='text'>"                                       
                                    + "</div>"				       
			       + "</div>";
			     			     			     
		//////////////////
		////////PAYMENT BG
			}else if(paymentSystem.isCardInfo() == false 
				&& paymentSystem.isBankInfoOut() == true 
				&& paymentSystem.getPaymentType() == 2
				&& paymentSystem.isCheckBGInfo() == false){
			    paidValue = paid;
			    Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
			    Vector currency_key = new Vector(1,1);
			    Vector currency_value = new Vector(1,1);

			    if(listCurrency.size() != 0){
				for(int i = 0;i<listCurrency.size(); i++){
				    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
				    currency_key.add(""+currencyType.getOID());
				    currency_value.add(""+currencyType.getCode());
				}
			    }

			    html = ""
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>Bank Name</div>"
                                + "<div class='col-md-9'><input type='text' class='form-control' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"_MULTI' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"_MULTI' value='"+paymentSystem.getBankName()+"' required></div>"				    
			    + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>Bank Account Number</div>"
                                + "<div class='col-md-9'><input type='text' class='form-control' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]+"_MULTI' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]+"_MULTI' required></div>"				    
			    + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>BG Number</div>"
                                + "<div class='col-md-9'><input type='text'  class='form-control' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] +"_MULTI' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] +"_MULTI' required></div>"				    
			    + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>Expired Date</div>"
                                + "<div class='col-md-9'>"
                                    + "<div class='input-group'>" +
                                        "<div class='input-group-addon'>" +
                                            "<i class='fa fa-calendar'></i>" +
                                        "</div>"
                                        + "<input type='text' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"_MULTI' required>"
                                    + "</div>"
                                + "</div>"				    
			    + "</div>"
                            + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>"

                                + "</div>"
                                + "<div class='col-md-9'>"
                                    + "<b>"+Formater.formatNumber(remain,"#,###.##")+"</b>"
                                + "</div>"				  
                            + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"	
                                + "<div class='col-md-3'>"
                                    
                                + "</div>"
                                + "<div class='col-md-2'>"
                                    + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID]+"_MULTI", null, "", currency_key, currency_value, "", "form-control")
                                + "</div>"
                                + "<div class='col-md-7'>"
                                    +"<input type='text' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"_MULTI' class='form-control calcMoney' value='"+Formater.formatNumber(paid,"#,###.##")+"'>"
                                + "</div>"				   
                            + "</div>"
                            + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"	
                                + "<div class='col-md-3'>"                                   
                                + "</div>"                             
                                + "<div class='col-md-9'>"
                                    +"<input type='text' readonly id='calcAmount' name='calcAmount' class='form-control money calc' value='"+Formater.formatNumber(remain,"#,###.##")+"'>"
                                + "</div>"				  
			   + "</div>";
		//////////////////////
		/////////PAYMENT CHECK
			}else if(paymentSystem.isCardInfo() == false 
				&& paymentSystem.isBankInfoOut() == false 
				&& paymentSystem.getPaymentType() == 0
				&& paymentSystem.isCheckBGInfo() == true){
			    paidValue = paid;
			    Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
			    Vector currency_key = new Vector(1,1);
			    Vector currency_value = new Vector(1,1);

			    if(listCurrency.size() != 0){
				for(int i = 0;i<listCurrency.size(); i++){
				    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
				    currency_key.add(""+currencyType.getOID());
				    currency_value.add(""+currencyType.getCode());
				}
			    }

			    html = ""
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>Bank Name</div>"
                                + "<div class='col-md-9'><input type='text' class='form-control' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"_MULTI' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"_MULTI' value='"+paymentSystem.getBankName()+"' required></div>"				  
			    + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>Bank Account Number</div>"
                                + "<div class='col-md-9'><input type='text' class='form-control cName' id='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]+"_MULTI' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME]+"_MULTI' required></div>"				    
			    + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				 
                                + "<div class='col-md-3'>Cheque Number</div>"
                                + "<div class='col-md-9'><input type='text'  class='form-control' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"_MULTI' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"_MULTI' required></div>"				   
			    + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"			
                                + "<div class='col-md-3'>Expired Date</div>"
                                + "<div class='col-md-9'>"
                                    + "<div class='input-group'>" +
                                        "<div class='input-group-addon'>" +
                                            "<i class='fa fa-calendar'></i>" +
                                        "</div>"
                                        + "<input type='text' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"_MULTI' required>"
                                    + "</div>"
                                + "</div>"				    
			    + "</div>"
                            + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>"

                                + "</div>"
                                + "<div class='col-md-9'>"
                                    + "<b>"+Formater.formatNumber(remain,"#,###.##")+"</b>"
                                + "</div>"				  
                            + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                                + "<div class='col-md-3'>"
                                    
                                + "</div>"
                                + "<div class='col-md-2'>"
                                    + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID]+"_MULTI", null, "", currency_key, currency_value, "", "form-control")
                                + "</div>"
                                + "<div class='col-md-7'>"
                                    +"<input type='text' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"_MULTI' class='form-control calcMoney' value='"+Formater.formatNumber(paid,"#,###.##")+"'>"
                                + "</div>"				  
			   + "</div>"
                            + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"	
                                + "<div class='col-md-3'>"                                   
                                + "</div>"                             
                                + "<div class='col-md-9'>"
                                    +"<input type='text' readonly id='calcAmount' name='calcAmount' class='form-control money calc' value='"+Formater.formatNumber(remain,"#,###.##")+"'>"
                                + "</div>"				  
			   + "</div>";
			   
                ///////////////////////
                //////// FOC PAYMENT
                        }else if(paymentSystem.isCardInfo() == false 
				&& paymentSystem.isBankInfoOut() == false 
				&& paymentSystem.getPaymentType() == 6
				&& paymentSystem.isCheckBGInfo() == false){
                           
                            html = "" 
                            + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"			
                                + "<div class='col-md-3'>Remark</div>"
                                + "<div class='col-md-9'><textarea name='remarkFoc' class='form-control' style='width:100%'></textarea></div>"				    
			    + "</div>";
		///////////////////////
		///////// DEBIT PAYMENT
			}else if(paymentSystem.isCardInfo() == false 
				&& paymentSystem.isBankInfoOut() == false 
				&& paymentSystem.getPaymentType() == 2
				&& paymentSystem.isCheckBGInfo() == false){
			    paidValue = paid;
			    Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
			    Vector currency_key = new Vector(1,1);
			    Vector currency_value = new Vector(1,1);

			    if(listCurrency.size() != 0){
				for(int i = 0;i<listCurrency.size(); i++){
				    CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
				    currency_key.add(""+currencyType.getOID());
				    currency_value.add(""+currencyType.getCode());
				}
			    }
                            int cardInfo = 0;
                            String cardInfoStr = PstSystemProperty.getValueByName("CASHIER_SHOW_DEBIT_CARD_INFO");
                            try{
                                cardInfo = Integer.parseInt(cardInfoStr);
                            }catch(Exception ex){
                                cardInfo=0;
                            }
			    html = "";
                            if (cardInfo==1){
                                html += ""
                                + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                    + "<div class='col-md-3'>Bank Name</div>"
                                    + "<div class='col-md-9'><input type='text' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"_MULTI' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' value='"+paymentSystem.getBankName()+"' required></div>"				    
                                + "</div>"
                                + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"			
                                    + "<div class='col-md-3'>Card Number</div>"
                                    + "<div class='col-md-9'>"
                                        + "<div class='input-group'>"
                                            + "<input type='text' class='form-control getaccountDebit' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"_MULTI' id='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"' required>"
                                            + "<div class='btn btn-primary input-group-addon' id='debitReScan'>"
                                                 + "<i class='fa fa-refresh'></i>"
                                            + "</div>"
                                        + "</div>"
                                    + "</div>"				   
                                + "</div>"
                                + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				 
                                    + "<div class='col-md-3'>Expired Date</div>"
                                    + "<div class='col-md-9'>"
                                        + "<div class='input-group'>" +
                                            "<div class='input-group-addon'>" +
                                                "<i class='fa fa-calendar'></i>" +
                                            "</div>"
                                            + "<input type='text' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"_MULTI' readonly='readonly'  required>"
                                        + "</div>"
                                    + "</div>"				    
                                + "</div>";
                            }else{
                                html += ""
                                + "<input type='hidden' class='form-control' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"_MULTI' id='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME]+"' value='-' required>"
                                + "<input type='hidden' class='form-control getaccountDebit' name='"+ FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"_MULTI' id='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER]+"' value='-' required>"
                                + "<input type='hidden' class='form-control datePicker' name='"+FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE]+"_MULTI' readonly='readonly' value='"+Formater.formatDate(new Date(),"yyyy-MM-dd")+"'  required>"
                                +"";
                            }
                            
                            html += ""
                            + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"				
                                + "<div class='col-md-3'>"

                                + "</div>"
                                + "<div class='col-md-9'>"
                                    + "<b >"+Formater.formatNumber(remain,"#,###.##")+"</b>"
                                + "</div>"				 
                            + "</div>"
			    + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"	
                                + "<div class='col-md-3'>"
                                    
                                + "</div>"
                                + "<div class='col-md-2'>"
                                    + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID]+"_MULTI", null, "", currency_key, currency_value, "", "form-control")
                                + "</div>"
                                + "<div class='col-md-7'>"
                                    +"<input type='text' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"_MULTI' class='form-control calcMoney' value='"+Formater.formatNumber(paid,"#,###.##")+"'>"
                                + "</div>"				   
                            + "</div>"
                            + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"	
                                + "<div class='col-md-3'>"                                   
                                + "</div>"                             
                                + "<div class='col-md-9'>"
                                    +"<input type='text' readonly id='calcAmount' name='calcAmount' class='form-control money calc' value='"+Formater.formatNumber(remain,"#,###.##")+"'>"
                                + "</div>"				  
			   + "</div>";
			}
		    }catch(Exception ex){
		       //ex.printStackTrace();
		    }
		    
//////////////////////////////////////
/////////////////LOAD SUB MEMBER TYPE
		}else if(loadtype.equals("loadsubmembertype")){
		    displayTotal = Formater.formatNumber(costumBalance,"#,###");
		    balanceValue = getBalance;
		    paidValue = 0;
		    creditCardCharge = creditCardCharge;
		    String crash="";
		    if(oidData.indexOf("#") != -1){
			oid = Long.parseLong(oidData.replace("#", ""));
			crash = "#";
		    }else{
			oid = oid;
		    }
		    
		    try{
			ContactClass contactClass = PstContactClass.fetchExc(oid);
			String header = "";
			int intType = 0;
			switch(contactClass.getClassType()){
			    
			    case PstContactClass.CONTACT_TYPE_DOT_COM_COMPANY: 
				intType = PstContactClass.CONTACT_TYPE_DOT_COM_COMPANY;
				header = ".COM COMPANY";
			    break;
				
			    case PstContactClass.CONTACT_TYPE_COMPANY: 
				intType = PstContactClass.CONTACT_TYPE_COMPANY;
				header = "CORPORATE";
			    break;
				
			    case PstContactClass.CONTACT_TYPE_EMPLOYEE:
				intType = PstContactClass.CONTACT_TYPE_EMPLOYEE;
				header = "EMPLOYEE";
			    break;
			    
			    //GUEST DILEVERY NOT AVAILABLE
				
			    case PstContactClass.CONTACT_TYPE_GUIDE:
				intType = PstContactClass.CONTACT_TYPE_GUIDE;
				header = "GUIDE";
			    break;
			    
			    case PstContactClass.CONTACT_TYPE_MEMBER:
				intType = PstContactClass.CONTACT_TYPE_MEMBER;
				
				if(oidData.indexOf("#") != -1){
				    header = "IN HOUSE GUEST";
				}else{
				    header = "MEMBER";
				}
				
			    break;
			    
			    
			    case PstContactClass.CONTACT_TYPE_TRAVEL_AGENT: 
				header = "TRAVEL AGENT";
				intType = PstContactClass.CONTACT_TYPE_TRAVEL_AGENT;
			    break;
			    
			    case PstContactClass.CONTACT_TYPE_DONOR:
				header = "NON MEMBER";
				intType = PstContactClass.CONTACT_TYPE_DONOR;
			    break;
			    //VENDOR NOT AVAILABLE
				
			    default:
				header = "NOT AVAILABLE";
			    break;
				
			}
			html+= ""
			+ "<div class='row'>"
			    + "<div class='box-body'>"
				+ "<div class='col-md-12'>"
				    + "<div class='col-md-6'><b>"+header+"</b></div>"
				    + "<div class='col-md-6'>"
					+ "<input type='hidden' name='"+ FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE]+"' id='"+ FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE]+"' value='"+crash+intType+"'>"
					+ "<input type='hidden' name='searchtype' id='searchtype' value='guest'>"
					+ ""
					+ "<div class='input-group'>"
					    + "<input type='text' class='form-control' name='employee' id='employee' required='required'>"
					    + "<div  id='btnOpenSearchMember' class='input-group-addon btn btn-primary'>"
						+ "<i class='fa fa-search'></i>"
					    + "</div>"
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			+ "</div>";
			
		    }catch(Exception ex){
			
		    }
		    
		    
		    
		
		
//////////////////////////////////////////
/////////////////CHECK MEMBER CREDIT LIMIT
		}else if(loadtype.equals("checkmemberlimit")){
		    try{
			Contact contact = PstContact.fetchExc(oid);
			String dateNow = Formater.formatDate(new Date(), "yyyy-MM-dd");
			
			double amountCredit = PstCustomBillMain.checkTotalCredit(0, 0, "contact."+ PstContact.fieldNames[PstContact.FLD_CONTACT_ID]+"='"+oid+"' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1'", "");
			
			int checkmember = PstCustomBillMain.listMember(0, 0, 
				"contact."+PstContact.fieldNames[PstContact.FLD_CONTACT_ID]+"='"+oid+"' "
				+ "AND memberReg."+ PstMemberRegistrationHistory.fieldNames[PstMemberRegistrationHistory.FLD_VALID_START_DATE]+"<='"+dateNow+"' "
				+ "AND memberReg."+ PstMemberRegistrationHistory.fieldNames[PstMemberRegistrationHistory.FLD_VALID_EXPIRED_DATE]+">='"+dateNow+"'", "");
			
			if(contact.getMemberCreditLimit() > (costumBalance+amountCredit) && checkmember > 0){
			    html = ""
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<b>CREDIT INFORMATION</b>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + "Credit Limit"
					+ "</div>"
					+ "<div class='col-md-6'>"
					    + ""+Formater.formatNumber(contact.getMemberCreditLimit(),"#,###")
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + "Previous Credits"
					+ "</div>"
					+ "<div class='col-md-6'>"
					    + ""+Formater.formatNumber(amountCredit,"#,###")
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + "Credits Can Be Used"
					+ "</div>"
					+ "<div class='col-md-6'>"
					    + ""+Formater.formatNumber(contact.getMemberCreditLimit()-amountCredit,"#,###")
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
                            + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + "Company"
					+ "</div>"
					+ "<div class='col-md-6'>"
					    + "<input type='text' class='form-control' value='"+contact.getCompName()+"' readonly='readonly'>"
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "";
			}else{
			    html="FAILED";
			}
		    }catch(Exception ex){
			
		    }
		    
		    
		    
		    
/////////////////////////////////////////////////////
/////////////////CHECK MEMBER CREDIT LIMIT NON MEMBER
		}else if(loadtype.equals("checkmemberlimitnonmember")){
		    try{
			Contact contact = PstContact.fetchExc(oid);
			String dateNow = Formater.formatDate(new Date(), "yyyy-MM-dd");
			
			double amountCredit = PstCustomBillMain.checkTotalCredit(0, 0, "contact."+ PstContact.fieldNames[PstContact.FLD_CONTACT_ID]+"='"+oid+"' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1'", "");
			
			int checkmember = PstCustomBillMain.listMember(0, 0, 
				"contact."+PstContact.fieldNames[PstContact.FLD_CONTACT_ID]+"='"+oid+"' "
				+ "AND memberReg."+ PstMemberRegistrationHistory.fieldNames[PstMemberRegistrationHistory.FLD_VALID_START_DATE]+"<='"+dateNow+"' "
				+ "AND memberReg."+ PstMemberRegistrationHistory.fieldNames[PstMemberRegistrationHistory.FLD_VALID_EXPIRED_DATE]+">='"+dateNow+"'", "");
			
			
			    html = ""
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<b>CREDIT INFORMATION</b>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    /*+ "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + "Credit Limit"
					+ "</div>"
					+ "<div class='col-md-6'>"
					    + ""+Formater.formatNumber(contact.getMemberCreditLimit(),"#,###")
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"*/
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + "Previous Credits"
					+ "</div>"
					+ "<div class='col-md-6'>"
					    + ""+Formater.formatNumber(amountCredit,"#,###")
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
                                    
                            + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + "Contact Name"
					+ "</div>"
					+ "<div class='col-md-6'>"
					    + "<div class='input-group'>"
                                                + "<input type='hidden' name='ctIdCustomer' id='ctIdCustomer'>"
                                                + "<input name='ctCustomerName' id='ctCustomerName' readonly='readonly' type='text' class='form-control'>"
                                                + "<div id='ctOpenListCustomer' class='input-group-addon btn btn-primary'>"
                                                    + "<i class='fa fa-search'></i>"
                                                + "</div>"
                                            + "</div>"
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
                            + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + "Mobile Phone"
					+ "</div>"
					+ "<div class='col-md-6'>"
                                            + "<input name='ctMobilePhone' id='ctMobilePhone' type='text' readonly='readonly' class='form-control'>"
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
                            + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + "Company"
					+ "</div>"
					+ "<div class='col-md-6'>"
                                            + "<input name='ctCompany' id='ctCompany' type='text' readonly='readonly' class='form-control'>"
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    /*+ "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "<div class='col-md-6'>"
					    + "Credits Can Be Used"
					+ "</div>"
					+ "<div class='col-md-6'>"
					    + ""+Formater.formatNumber(contact.getMemberCreditLimit()-amountCredit,"#,###")
					+ "</div>"
				    + "</div>"
				+ "</div>"
			    + "</div>"*/
			    + "";
			
		    }catch(Exception ex){
			
		    }	
////////////////////////////////////////
/////////////////CEK IN HOUSE GUEST
                }else if (loadtype.equals("checkcreditinhouseguest")){
                    Contact contact = PstContact.fetchExc(oid);
                    String dateNow = Formater.formatDate(new Date(), "yyyy-MM-dd");
		    String company = contact.getCompName();
                    if (company==null || company==""){
                        company="-";
                    }
                    double amountCredit = PstCustomBillMain.checkTotalCredit(0, 0, "contact."+ PstContact.fieldNames[PstContact.FLD_CONTACT_ID]+"='"+oid+"' "
                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' "
                    + "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1'", "");

                    html = ""
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-12'>"
                                    + "<b>CREDIT INFORMATION</b>"
                                + "</div>"
                            + "</div>"
                        + "</div>"
           
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-12'>"
                                    + "<div class='col-md-6'>"
                                        + "Previous Credits"
                                    + "</div>"
                                    + "<div class='col-md-6'>"
                                        + ""+Formater.formatNumber(amountCredit,"#,###")
                                    + "</div>"
                                + "</div>"
                            + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-12'>"
                                    + "<div class='col-md-6'>"
                                        + "Company"
                                    + "</div>"
                                    + "<div class='col-md-6'>"
                                        + "<input type='text' class='form-control' value='"+company+"' readonly='readonly'>"
                                    + "</div>"
                                + "</div>"
                            + "</div>"
                        + "</div>"
                        + "";
////////////////////////////////////////		    
/////////////////AUTHORIZE OPEN DISCOUNT
		}else if(loadtype.equals("opendiscount")){
		    
		    if(listSpv.size() != 0){
			html = "SUCCESS";
		    }else{
			html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
		    }
		    
////////////////////////////////////////////
/////////////////AUTHORIZE CREDIT NON MEMBER
		}else if(loadtype.equals("authorizecredit")){
		    
		    if(listSpv.size() != 0){
			html = "SUCCESS";
		    }else{
			html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
		    }
		
		
		
		
		
		
////////////////////////////////////////////
/////////////////AUTHORIZE RETURN BILL
		}else if(loadtype.equals("authorizereturn")){
		    
		    if(listSpv.size() != 0){
			html = "SUCCESS";
		    }else{
			html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
		    }
////////////////////////////////////
/////////////////AUTHORIZE PAYMENT USING PASSWORD		
                }else if (loadtype.equals("authPass")){
                    AppUser appUser= new AppUser();
                    String userName = FRMQueryString.requestString(request, "username");
                    String password = FRMQueryString.requestString(request, "password");
                    long oidDoc = FRMQueryString.requestLong(request, "oidDoc");
                    String base = FRMQueryString.requestString(request, "base");
                    long err = 0;
                    
                    String whereAppUser = " "+PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]+"='"+userName+"' and "
                                         +" "+PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD]+"='"+password+"'";
                    
                    Vector listAppUser = PstAppUser.listFullObj(0,0,whereAppUser,"");
                    
                    String coverNumber = "";
                    try{
                        BillMain billMain = PstBillMain.fetchExc(oidDoc);
                        coverNumber = billMain.getCoverNumber();
                    }catch(Exception ex){
                        
                    }
                    if (listAppUser.size()>0){
                        LogSysHistory logSysHistory = new LogSysHistory();
                        PstLogSysHistory pstLogSysHistory = new PstLogSysHistory();
                        appUser = (AppUser)listAppUser.get(0);
                        //set objek log sys history
                        logSysHistory.setLogUserId(appUser.getOID());
                        logSysHistory.setLogDocumentId(oidDoc);
                        logSysHistory.setLogLoginName(appUser.getFullName());
                        logSysHistory.setLogDocumentNumber("");
                        logSysHistory.setLogDocumentType("Bill");
                        logSysHistory.setLogUserAction("Verification");
                        logSysHistory.setLogOpenUrl(base);
                        logSysHistory.setLogUpdateDate(new Date());
                        logSysHistory.setLogApplication("Prochain");
                        logSysHistory.setLogDetail("Payment verification using login id and password");
                        try {
                            err = pstLogSysHistory.insertExc(logSysHistory);
                        } catch (Exception e) {
                        }

                        if (err!=0){
                            html = "0";
                        }else{
                            html = "1";
                        }
                        
                    }else{
                        html = "1";
                    }
                    shortMessage = coverNumber;
		    
		    
		
////////////////////////////////////
/////////////////PRINT BILL TO GUEST
		}else if(loadtype.equals("printguest")){
                    double service = FRMQueryString.requestDouble(request, "FRM_FIELD_SERVICE_PCT");
                    double tax = FRMQueryString.requestDouble(request, "FRM_FIELD_TAX_PCT");
                    double discPct = FRMQueryString.requestDouble(request, "FRM_FIELD_DISC_PCT");
                    String printOpenBillGenerateInvoice = PstSystemProperty.getValueByName("CASHIER_PRINT_OPEN_BILL_TO_INVOICE");
                    String note = FRMQueryString.requestString(request, "FRM_FIELD_NOTES");
                    String isDynamicTemplate = PstSystemProperty.getValueByName("PRINT_DYNAMIC_TEMPLATE");
                    
                    BillMain billMain = new BillMain();
                    
                    
                    
                    updateDisc(discPct, request);
                    String coverNumber = "";
                    if (printOpenBillGenerateInvoice.equals("1")){
                        BillMain entBillMain = new BillMain();
                        try {
                            entBillMain = PstBillMain.fetchExc(oid);
                            coverNumber = entBillMain.getCoverNumber();
                            billMain = entBillMain;
                        } catch (Exception e) {
                        }
                        
                        
                        
                        if (entBillMain.getInvoiceNumber().toLowerCase().contains("x")){
                            String newInvoice="";
                            int counter = PstCustomBillMain.getCounterBill(0, 0, "", "")+1;

                            String invoiceNumb ="";
                            int counterPlus=0;
                            int countInvoiceNumber = 0;
                            int cashierNumber = 0;
                            long cashCashier = 0;
                            
                            if (entBillMain.getCashCashierId()==1){
                                cashCashier = FRMQueryString.requestLong(request, "cashcashier");
                            }else{
                                cashCashier = entBillMain.getCashCashierId();
                            }
                            
                            //get cash_cashier 
                            CashCashier entCashCashier = new CashCashier();
                            try {
                                entCashCashier = PstCashCashier.fetchExc(cashCashier);
                            } catch (Exception e) {
                            }
                            
                            //get cash master 
                            CashMaster entCashMaster = new CashMaster();
                            try {
                                entCashMaster = PstCashMaster.fetchExc(entCashCashier.getCashMasterId());
                            } catch (Exception e) {
                            }
                            String cashierNumberFormat = "00"+entCashMaster.getCashierNumber()+"."+Formater.formatDate(new Date(),"yyyyMMdd");
                            invoiceNumb = generateInvoiceNumber(cashierNumberFormat);
                            /*do {
                                int countAfter = counter + counterPlus;
                                String zeroCount = "000"+ countAfter;
                                String zeroCount2 = zeroCount.substring(zeroCount.length()-3); 
                                invoiceNumb = "00"+entCashMaster.getCashierNumber()+"."+Formater.formatDate(new Date(), "yyyyMMdd")+"."+zeroCount2;

                                String where= ""+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NUMBER]+"='"+invoiceNumb+"'";
                                Vector list = PstBillMain.list(0, 0, where, "");
                                countInvoiceNumber = list.size();
                                counterPlus += 1; 
                            } while (countInvoiceNumber>0);*/
                            
                            //update 
                            entBillMain.setInvoiceNumber(invoiceNumb);
                            entBillMain.setInvoiceNo(invoiceNumb);
                            entBillMain.setInvoiceCounter(counterPlus);
                            entBillMain.setCashCashierId(cashCashier);
                            //update opie-eyek 20170801
                            entBillMain.setNotes(note);
                            
                            CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
                            int iError = ctrlBillMain.actionObject(Command.SAVE, entBillMain, request);
                            
                        }
                        
                    }
                    
                    if(isDynamicTemplate.equals("0")){
                        html = drawPrintNew(transactionType, oid, ""+loginId, 
                                taxInc, paymentType, ccName, ccNo, ccBank, 
				    ccValid, ccCharge, payAmount, oidCashier, 
				    "printguest", request.getContextPath(), oidMember,tax,service,discPct);
                    }else{
                        
                        html = templateHandler(billMain);
                    }
                        
		    
                    
		    
		    
///////////////////////////
//////////////////EDIT ITEM
		}else if(loadtype.equals("edititem")){
		    if(listSpv.size() != 0){
			try{
			    Billdetail billdetail = PstBillDetail.fetchExc(oid);

			    Vector listTaxService = PstCustomBillMain.listTaxService(0, 0, 
				"billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+billdetail.getBillMainId()+"'", "");
			    BillMainCostum billMainCostum;


			    double balance = (billdetail.getQty()*billdetail.getItemPrice())-(billdetail.getDisc()*billdetail.getQty());
			    String readonly = "";
			    String incTaxService = "";

			    double amountService = 0;
			    double amountTax = 0;
			    double afterService = 0;
			    double afterTax = 0;

			    try{
				billMainCostum = (BillMainCostum) listTaxService.get(0);

				amountService = balance*(billMainCostum.getServicePct()/100);
				amountTax = balance*(billMainCostum.getTaxPct()/100);
				afterService = balance+amountService;
				afterTax = balance+amountService+amountTax;

				if(billMainCostum.getTaxInc() == 0){
				    balance = balance;
				    readonly="readonly='readonly'";
				    incTaxService = "<small>(include tax & service)</small>";
				}else if(billMainCostum.getTaxInc() == 1){
				    balance = (balance)+amountService+amountTax;
				    readonly="readonly='readonly'";
				    incTaxService = "<small>(exclude tax & service)</small>";
				}else if(billMainCostum.getTaxInc() == 2){
				    balance = balance;
				    incTaxService = "<small>(include tax & service)</small>";
				}else{
				    balance = (balance)+amountService+amountTax;
				    incTaxService = "<small>(exclude tax & service)</small>";
				}
			    }catch(Exception ex){
				billMainCostum = new BillMainCostum();
			    }
			    
			    
			    String readonlyPrice = "";
			    
			    
			    String oidSpesialRequestFood=PstSystemProperty.getValueByName("SPESIAL_REQUEST_FOOD");
			    String oidSpesialRequestBeverage=PstSystemProperty.getValueByName("SPESIAL_REQUEST_BEVERAGE");
			    String oidMaterialIdString = ""+billdetail.getMaterialId();
			    
			    if(oidMaterialIdString.equals(oidSpesialRequestBeverage) || oidMaterialIdString.equals(oidSpesialRequestFood)){
				readonlyPrice = "";
			    }else{
				readonlyPrice = "readonly='true'";
			    }

			    html = ""
			    + "<form id='"+FrmBillDetail.FRM_NAME_BILL_DETAIL+"' name='"+FrmBillDetail.FRM_NAME_BILL_DETAIL+"'>"
			    + "<input type='hidden' name='loadtype' id='loadtype' value='"+loadtype+"'>"
			    + "<input type='hidden' name='command' value='"+Command.SAVE+"'>"
			    + "<input type='hidden' id='billMainId' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]+"' value='"+oid+"'>"
			    + "<input type='hidden' name='"+billMainJSON.fieldNames[billMainJSON.FLD_TAX_INC]+"' id='taxInc' value='"+billMainCostum.getTaxInc()+"'>"
                            + "<input type='hidden' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CUSTOME_PACK_BILLING_ID]+"' value='"+billdetail.getCutomePackBillingId()+"'>"
                                    + "<input type='hidden' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CUSTOME_SCHEDULE_ID]+"' value='"+billdetail.getCostumeScheduleId()+"'>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-3'>Item Name</div>"
				    + "<div class='col-md-9'><input id='materialNames' type='text' name ='"+ FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME]+"' value='"+billdetail.getItemName()+"' class='form-control' "+readonlyPrice+"></div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-3'>Quantity</div>"
				    + "<div class='col-md-9'><input type='text' name='"+ FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]+"' id='FRM_FIELD_QTY2' value='"+billdetail.getQty()+"' class='form-control itemChange'></div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-3'>Item Price</div>"
				    + "<div class='col-md-9'>"
					+ incTaxService+"<br>"
					+ "<input type='text' name='"+ FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] +"' id='"+ FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] +"' value='"+billdetail.getItemPrice()+"' class='form-control itemChange' "+readonlyPrice+">"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-3'>Item Discount</div>"
				    + "<div class='col-md-4'>"
					+ "<div class='input-group'>"
					    + "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT]+"' value='"+billdetail.getDiscPct()+"' class='form-control itemChange'>"
					    + "<div class='input-group-addon'>%</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='col-md-5'>"
					+ "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC]+"' value='"+billdetail.getDisc()*billdetail.getQty()+"' class='form-control itemChange '>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-3'>Total</div>"
				    + "<div class='col-md-9' style='text-align:right;' id='afterDiscount'>"
					    +Formater.formatNumber((billdetail.getItemPrice()*billdetail.getQty())-(billdetail.getDisc()*billdetail.getQty()),"#,###")
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-3'>Item Service</div>"
				    + "<div class='col-md-4'>"
					+ "<div class='input-group'>"
					    + "<input type='text' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT]+"' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT]+"' value='"+billMainCostum.getServicePct()+"' class='form-control itemChange' "+readonly+">"
					    + "<div class='input-group-addon'>%</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='col-md-5'>"
					+ "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_SERVICE]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_SERVICE]+"' value='"+amountService+"' class='form-control' readonly='readonly'>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-3'>Total</div>"
				    + "<div class='col-md-9' style='text-align:right;' id='afterService'>"
					    +Formater.formatNumber(afterService,"#,###")
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-3'>Item Tax</div>"
				    + "<div class='col-md-4'>"
					+ "<div class='input-group'>"
					    + "<input type='text' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT]+"' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT]+"' value='"+billMainCostum.getTaxPct()+"' class='form-control itemChange' "+readonly+">"
					    + "<div class='input-group-addon'>%</div>"
					+ "</div>"
				    + "</div>"
				    + "<div class='col-md-5'>"
					+ "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_TAX]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_TAX]+"' value='"+amountTax+"' class='form-control' readonly='readonly'>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    +"<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-3'>Total</div>"
				    + "<div class='col-md-9' style='text-align:right;' id='total'>"
				    
					    +Formater.formatNumber(balance,"#,###")
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-3'>Note</div>"
				    + "<div class='col-md-9'>"
					+"<textarea class='form-control' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_NOTE]+"'>"+billdetail.getNote()+"</textarea>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-6'>"
					+ "<button type='button' class='btn btn-danger' data-dismiss='modal'><i class='fa fa-close'></i> Cancel</button>"
				    + "</div>"
				    + "<div class='col-md-6'>"
					+ "<button type='submit' class='btn btn-primary pull-right' id='btnSaveBillDetail'><i class='fa fa-check'></i> Save</button>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "</form>";
			}catch(Exception ex){
			    html = ""+ex.getMessage().toString();
			}
		    }else{
			html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
		    }
	////////////////////
        /////////EDIT ITEM 2
                }else if(loadtype.equals("edititem2")){
                    try{
                        long oidLocation = 0;
                        String priceType = "0";
                        
                        //get Material Data
                        long oIdMaterial = FRMQueryString.requestLong(request, "oidMaterial");
                        long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
                        double materialPrice = FRMQueryString.requestDouble(request, "materialPrice");
                        String materialName = FRMQueryString.requestString(request, "materialName");
                        int qty = 0;
                        qty = FRMQueryString.requestInt(request, "qty");
                        
                        if (qty==0){
                            qty = 1;
                        }
                        
                        Vector listTaxService = PstCustomBillMain.listTaxService(0, 0, 
                            "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+oidBillMain+"'", "");
                        BillMainCostum billMainCostum;

                        double balance = (qty*materialPrice)-0;
                        String readonly = "";
                        String incTaxService = "";

                        double amountService = 0;
                        double amountTax = 0;
                        double afterService = 0;
                        double afterTax = 0;

                        try{
                            billMainCostum = (BillMainCostum) listTaxService.get(0);

                            amountService = balance*(billMainCostum.getServicePct()/100);
                            amountTax = balance*(billMainCostum.getTaxPct()/100);
                            afterService = balance+amountService;
                            afterTax = balance+amountService+amountTax;

                            if(billMainCostum.getTaxInc() == 0){
                                balance = balance;
                                readonly="readonly='readonly'";
                                incTaxService = "<small>(include tax & service)</small>";
                            }else if(billMainCostum.getTaxInc() == 1){
                                balance = (balance)+amountService+amountTax;
                                readonly="readonly='readonly'";
                                incTaxService = "<small>(exclude tax & service)</small>";
                            }else if(billMainCostum.getTaxInc() == 2){
                                balance = balance;
                                incTaxService = "<small>(include tax & service)</small>";
                            }else{
                                balance = (balance)+amountService+amountTax;
                                incTaxService = "<small>(exclude tax & service)</small>";
                            }
                        }catch(Exception ex){
                            billMainCostum = new BillMainCostum();
                        }


                        String readonlyPrice = "";


                        String oidSpesialRequestFood=PstSystemProperty.getValueByName("SPESIAL_REQUEST_FOOD");
                        String oidSpesialRequestBeverage=PstSystemProperty.getValueByName("SPESIAL_REQUEST_BEVERAGE");
                        String oidMaterialIdString = ""+oIdMaterial;

                        if(oidMaterialIdString.equals(oidSpesialRequestBeverage) || oidMaterialIdString.equals(oidSpesialRequestFood)){
                            readonlyPrice = "";
                        }else{
                            readonlyPrice = "readonly='true'";
                        }

                        html = ""
                        + "<form id='"+FrmBillDetail.FRM_NAME_BILL_DETAIL+"' name='"+FrmBillDetail.FRM_NAME_BILL_DETAIL+"'>"
                        + "<input type='hidden' name='loadtype' id='loadtype' value='editItemProc'>"
                        + "<input type='hidden' name='command' value='"+Command.SAVE+"'>"
                        
                        + "<input type='hidden' name ='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID]+"' value='0'>"
                        + "<input type='hidden' name ='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID]+"' value='"+oIdMaterial+"'>"
                        + "<input type='hidden' id ='billMainId' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]+"' value='"+oidBillMain+"'>"
                        + "<input type='hidden' name='"+billMainJSON.fieldNames[billMainJSON.FLD_TAX_INC]+"' id='taxInc' value='"+billMainCostum.getTaxInc()+"'>"
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-3'>Item Name</div>"
                                + "<div class='col-md-9'><input id='materialNames' type='text' value='"+materialName+"' class='form-control' "+readonlyPrice+" name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME]+"'></div>"
                            + "</div>"
                        + "</div>"
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-3'>Quantity</div>"
                                + "<div class='col-md-9'><input type='text' name='"+ FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]+"' id='FRM_FIELD_QTY2' value='"+qty+"' class='form-control itemChange'></div>"
                            + "</div>"
                        + "</div>"
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-3'>Item Price</div>"
                                + "<div class='col-md-9'>"
                                    + incTaxService+"<br>"
                                    + "<input type='text' name='"+ FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] +"' id='"+ FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] +"' value='"+materialPrice+"' class='form-control itemChange' "+readonlyPrice+">"
                                + "</div>"
                            + "</div>"
                        + "</div>"
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-3'>Item Discount</div>"
                                + "<div class='col-md-4'>"
                                    + "<div class='input-group'>"
                                        + "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT]+"' value='0' class='form-control itemChange'>"
                                        + "<div class='input-group-addon'>%</div>"
                                    + "</div>"
                                + "</div>"
                                + "<div class='col-md-5'>"
                                    + "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC]+"' value='0' class='form-control itemChange '>"
                                + "</div>"
                            + "</div>"
                        + "</div>"
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-3'>Total</div>"
                                + "<div class='col-md-9' style='text-align:right;' id='afterDiscount'>"
                                        +Formater.formatNumber(materialPrice,"#,###")
                                + "</div>"
                            + "</div>"
                        + "</div>"
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-3'>Item Service</div>"
                                + "<div class='col-md-4'>"
                                    + "<div class='input-group'>"
                                        + "<input type='text' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT]+"' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT]+"' value='"+billMainCostum.getServicePct()+"' class='form-control itemChange' "+readonly+">"
                                        + "<div class='input-group-addon'>%</div>"
                                    + "</div>"
                                + "</div>"
                                + "<div class='col-md-5'>"
                                    + "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_SERVICE]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_SERVICE]+"' value='"+amountService+"' class='form-control' readonly='readonly'>"
                                + "</div>"
                            + "</div>"
                        + "</div>"
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-3'>Total</div>"
                                + "<div class='col-md-9' style='text-align:right;' id='afterService'>"
                                        +Formater.formatNumber(afterService,"#,###")
                                + "</div>"
                            + "</div>"
                        + "</div>"
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-3'>Item Tax</div>"
                                + "<div class='col-md-4'>"
                                    + "<div class='input-group'>"
                                        + "<input type='text' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT]+"' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT]+"' value='"+billMainCostum.getTaxPct()+"' class='form-control itemChange' "+readonly+">"
                                        + "<div class='input-group-addon'>%</div>"
                                    + "</div>"
                                + "</div>"
                                + "<div class='col-md-5'>"
                                    + "<input type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_TAX]+"' id='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_TAX]+"' value='"+amountTax+"' class='form-control' readonly='readonly'>"
                                + "</div>"
                            + "</div>"
                        + "</div>"
                        +"<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-3'>Total</div>"
                                + "<div class='col-md-9' style='text-align:right;' id='total'>"

                                        +Formater.formatNumber(balance,"#,###")
                                + "</div>"
                            + "</div>"
                        + "</div>"
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-3'>Note</div>"
                                + "<div class='col-md-9'>"
                                    +"<textarea class='form-control' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_NOTE]+"'></textarea>"
                                + "</div>"
                            + "</div>"
                        + "</div>"
                        + "<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-6'>"
                                    + "<button type='button' class='btn btn-danger' data-dismiss='modal'><i class='fa fa-close'></i> Cancel</button>"
                                + "</div>"
                                + "<div class='col-md-6'>"
                                    + "<button type='submit' class='btn btn-primary pull-right' id='btnSaveBillDetail'><i class='fa fa-check'></i> Save</button>"
                                + "</div>"
                            + "</div>"
                        + "</div>"
                        + "</form>";
                    }catch(Exception ex){
                        html = ""+ex.getMessage().toString();
                    }
		   
		    
	
	////////////////////
	/////////MOVE BILL
		}else if(loadtype.equals("movebill")){
		    if(listSpv.size() != 0){
			String randNumber = Formater.formatDate(new Date(),"yyyyMMddkkmmss");
			
			BillMain billMain;
			try{
			    billMain = PstBillMain.fetchExc(oid);
			}catch(Exception ex){
			    billMain = new BillMain();
			}
			
			html = ""
			+ "<div id='itemmovebill'>"
			    + "<input type='hidden' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]+"' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID]+"' value='"+oidCashier+"'>"
			    + "<input type='hidden' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]+"' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]+"' value='"+oid+"'>"
			    + "<input type='hidden' name='loadtype' id='loadtype'>"
			    + "<input type='hidden' name='command' value='"+Command.SAVE+"'>"
			    + "<input type='hidden' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME]+"' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME]+"' value='"+billMain.getGuestName()+" "+randNumber+"'>";
				
			    Vector listBill = PstCustomBillMain.listItemOpenBill(0, 0, 
				"billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+oid+"' "
				+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
				+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
				+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
				+ "AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'", "");
			    
			    
			    
			    html += drawListCancel(iCommand, listBill, oidCashier, null);
			    html += "</div>";
			
		    }else{
			html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
		    }
		    
			    
			    
			    
			    
	////////////////////////////
	/////////////////CANCEL ITEM
		}else if(loadtype.equals("cancelbill")){
		    if(listSpv.size() != 0){
			BillMain billMain;
			try{
			    billMain = PstBillMain.fetchExc(oid);
			}catch(Exception ex){
			    billMain = new BillMain();
			}
			html = ""
			+ "<form id='"+FrmBillMain.FRM_NAME_BILL_MAIN+"_CANCEL'>"
			    + "<input type='hidden' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]+"' value='"+oid+"'>"
			    + "<input type='hidden' name='loadtype' value='"+loadtype+"'>"
			    + "<input type='hidden' name='command' value='"+Command.SAVE+"'>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					+ "Notes"
					+ "<textarea name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_NOTES]+"' class='form-control'>"+billMain.getNotes()+"</textarea>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			    + "<div class='row'>"
				+ "<div class='box-body'>"
				    + "<div class='col-md-12'>"
					    + "<button type='submit' id='confirmCancel' class='btn btn-primary form-control'>"
						+ "<i class='fa fa-close'></i> Cancel Bill"
					    + "</button>"
				    + "</div>"
				+ "</div>"
			    + "</div>"
			+ "</form>";
		    }else{
			html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
		    }
		    
			    
			    
			    
			    
	////////////////////////////
	/////////////////DELETE ITEM (SHOW NOTE)
		}else if(loadtype.equals("delitem")){
		     if(listSpv.size() != 0){
			/*int iErrCode = FRMMessage.NONE;
			String frmMsg= "";
			iCommand = Command.DELETE;
			CtrlCustomBillDetail ctrlBillDetail = new CtrlCustomBillDetail(request);
			iErrCode = ctrlBillDetail.action(iCommand,oid,request);
			frmMsg = ctrlBillDetail.getMessage();
			html = frmMsg;*/
                        //GET MATERIAL DETAIL
                        Billdetail billdetail = PstBillDetail.fetchExc(oid);
                        Material material = PstMaterial.fetchExc(billdetail.getMaterialId());
                        //GET SPV NAME
                        AppUser appUser = new AppUser();
                        appUser = (AppUser)listSpv.get(0);
                        html = ""
                        + "<form id='"+FrmBillDetail.FRM_NAME_BILL_DETAIL+"' name='"+FrmBillDetail.FRM_NAME_BILL_DETAIL+"'>"
                        + "<input type='hidden' name='loadtype' value='delitemproccess'>"
                        + "<input type='hidden' name='command' value='"+Command.SAVE+"'>"
                        + "<input type='hidden' name='spvName' value='"+appUser.getLoginId()+"'>"
                        + "<input type='hidden' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID]+"' value='"+oid+"'>"
                        +"<div class='row'>"
                            +"<div class='box-body'>"
                                + "<div class='col-md-3'>Item Name</div>"
                                + "<div class='col-md-9'><b>"+billdetail.getItemName()+"</b></div>"
                            +"</div>"
                        +"</div>"
                        +"<div class='row'>"
                            +"<div class='box-body'>"
                                + "<div class='col-md-3'>Note</div>"
                                + "<div class='col-md-9'>"
                                    + "<textarea id='noteDeleteItem' required='required' class='form-control' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_NOTE]+"'></textarea>"
                                + "</div>"
                            +"</div>"
                        +"</div>"
                        +"<div class='row'></div>"
                        +"<div class='row'>"
                            + "<div class='box-body'>"
                                + "<div class='col-md-6'>"
                                    + "<button type='button' class='btn btn-danger' data-dismiss='modal'>"
                                        + "<i class='fa fa-close'></i> Cancel"
                                    + "</button>"
                                + "</div>"
                                + "<div class='col-md-6'>"
                                    + "<button type='submit' class='btn btn-primary pull-right' id='btnDeleteItem'>"
                                        + "<i class='fa fa-check'></i> Save"
                                    + "</button>"
                                + "</div>"
                            + "</div>"
                        + "</div>"
                        + "</form>";

		     }else{
			html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
		     }
                }else if (loadtype.equals("getguestdetail")){
                    long oidBills = FRMQueryString.requestLong(request, "oidBillMain");
                    BillMain entBillMain = new BillMain();
                    ContactList contactList = new ContactList();
                    try {
                        entBillMain = PstBillMain.fetchExc(oidBills);
                        contactList = PstContactList.fetchExc(entBillMain.getCustomerId());                        
                    } catch (Exception e) {
                    }
                    String noTelp ="";
                    if (contactList.getTelpMobile().length()>0){
                        noTelp = contactList.getTelpMobile();
                    }else if (contactList.getHomeMobilePhone().length()>0){
                        noTelp = contactList.getHomeMobilePhone();
                    }
                    html = ""+entBillMain.getGuestName()+"-"+noTelp+"";
                    
		}else if(loadtype.equals("checktable")){
		    Vector listTable = PstTableRoom.list(0, 0, 
			    ""+PstTableRoom.fieldNames[PstTableRoom.FLD_ROOM_ID]+"='"+oidRoom+"'", "");
		    
		    if(listTable.size() != 0){
			for(int i = 0; i < listTable.size(); i++){
			    TableRoom tableRoom = (TableRoom) listTable.get(i);
                            String whereTableBill=PstBillMain.fieldNames[PstBillMain.FLD_TABLE_ID]+"='"+tableRoom.getOID()+"'";
                            int totalOpenBill = PstBillMain.getCountOpenBillFromTable(whereTableBill);
                            if (totalOpenBill==0){
                                html+="<option value='"+tableRoom.getOID()+"'>"+tableRoom.getTableNumber()+"</option>";
                            }
			    
			}
		    }
		}else if(loadtype.equals("checkroom")){
                    //cek dulu, apakah lokasi ini menggunakan table 
                    String whereLocation = " "+PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]+"="+oidMultiLocation+"";
                    Vector listLocation = PstLocation.list(0, 0, whereLocation, "");
                    Location location = new Location();
                    long oidRoomFirst=0;
                    if (listLocation.size()>0){
                        location = (Location)listLocation.get(0);
                    }
                    if (location.getLocationUsedTable()!=0){
                        Vector listRoom = PstRoom.list(0, 0, PstRoom.fieldNames[PstRoom.FLD_LOCATION_ID]+"='"+oidMultiLocation+"'", "");
                        if(listRoom.size() != 0){
                                html+="<option value='0'>--Select--</option>";
                            for(int i = 0; i < listRoom.size(); i++){
                                Room room = (Room) listRoom.get(i);
                                if(oidRoomFirst==0){
                                    oidRoomFirst=room.getOID();
                                }
                                html+="<option value='"+room.getOID()+"'>"+room.getName()+"</option>";
                            }
                        }
                        
                    }else{
                        html += "0";
                    }
                    
		    
		}else if (loadtype.equals("ctListTool")){
                    html ="";
                    html = drawCustomerCreditTool();
                }else if(loadtype.equals("checklocation")){
                    long idBill = cashier;
                    BillMain billMain = new BillMain();
                    try {
                        billMain = PstBillMain.fetchExc(idBill);
                    } catch (Exception e) {
                    }
                    html = String.valueOf(billMain.getLocationId());
                }else if (loadtype.equals("checkDepartementOutlet")){
                    long oIdDepartement = FRMQueryString.requestLong(request, "oidDepartement");
                    long oIdOutlet = FRMQueryString.requestLong(request, "oidOutlet");
                    
                    Location entLocation = new Location();
                    entLocation = PstLocation.fetchExc(oIdOutlet);
                    long depOutlet = entLocation.getDepartmentId();
                    
                    if (depOutlet==oIdDepartement){
                        html = "1";
                    }else{
                        html = "0";
                    }
                }else if (loadtype.equals("ctList")){
                    html = "";
                    String typeText = FRMQueryString.requestString(request, "typeText");
    
		    Vector listMember = new Vector(1,1);
		    String whereClause = "";
		    boolean inHouseGuest = false;
		    if(cashierString.indexOf("#") != -1){
			inHouseGuest = true;
			cashierString = cashierString.replace("#", "");
		    }
		    
		    switch(Integer.parseInt(cashierString)){
			    
			case PstContactClass.CONTACT_TYPE_DOT_COM_COMPANY: 
			    if(oidData.length() != 0){
				whereClause = "AND "+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+typeText+"%'";
			    }
			    listMember = PstCustomBillMain.listMemberCredit(0, 15, 
				    "CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_DOT_COM_COMPANY+"' "
				    + whereClause, "");
			break;

			case PstContactClass.CONTACT_TYPE_COMPANY: 
			    if(oidData.length() != 0){
				whereClause = "AND "+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+typeText+"%'";
			    }
			    listMember = PstCustomBillMain.listMemberCredit(0, 15, 
				    "CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_COMPANY+"' "
				    + whereClause, "");
			break;

			case PstContactClass.CONTACT_TYPE_EMPLOYEE:
			    if(oidData.length() != 0){
				whereClause = "AND "+PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME]+" LIKE '%"+typeText+"%'";
			    }
			    listMember = PstCustomBillMain.listEmployee(0, 15, 
				    "CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_EMPLOYEE+"' "
				    + whereClause, "");
			break;

			//GUEST DILEVERY NOT AVAILABLE

			case PstContactClass.CONTACT_TYPE_GUIDE:
			    if(oidData.length() != 0){
				whereClause = "AND "+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+typeText+"%'";
			    }
			    listMember = PstCustomBillMain.listMemberCredit(0, 15, 
				    "CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_GUIDE+"' "
				    + whereClause, "");
			break;

			case PstContactClass.CONTACT_TYPE_MEMBER:
			    
			    if(inHouseGuest == true){
				if(oidData.length() != 0){
				    whereClause = "AND (m."+ PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+typeText+"%' "
					    + "OR k."+ PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER]+" LIKE '%"+typeText+"%' "
					    + "OR n."+ PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" LIKE '%"+typeText+"%')";
				}
				listMember = PstCustomBillMain.listInHouseGuest(0, 15, whereClause, "");
			    }else{
				if(oidData.length() != 0){
				    whereClause = "AND "+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+typeText+"%'";
				}
				listMember = PstCustomBillMain.listMemberCredit(0, 15, 
					"CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_MEMBER+"' "
					+ whereClause, "");
			    }
			    
			break;

			case PstContactClass.CONTACT_TYPE_TRAVEL_AGENT:
			    if(oidData.length() != 0){
				whereClause = "AND "+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+typeText+"%'";
			    }
			    listMember = PstCustomBillMain.listMemberCredit(0, 15, 
				    "CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_TRAVEL_AGENT+"' "
				    + whereClause, "");
			break;
			    
			case PstContactClass.CONTACT_TYPE_DONOR:
			    if(oidData.length() != 0){
				whereClause = "AND "+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+typeText+"%'";
			    }
			    listMember = PstCustomBillMain.listMemberCredit(0, 15, 
				    "CLS."+PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE]+"='"+PstContactClass.CONTACT_TYPE_DONOR+"' "
				    + whereClause, "");
			break;

			//VENDOR NOT AVAILABLE

			default:
                            whereClause =""+PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+typeText+"%'";
			    listMember =  PstCustomBillMain.listMemberCredit(0, 15,whereClause,""); 
                           
			break;
                            
                        
                    }
                    html = drawCustomerCreditTemporary(listMember);
                }
	    }catch(Exception ex){
		//ex.printStackTrace();
	    }
	}else if(iCommand == Command.SAVE){
	    int iErrCode = FRMMessage.NONE;
	    String frmMsg= "";
	    
	    
	    ///EDIT ITEM
            if(loadtype.equals("covernumber")){
                frmMsg = "failed";
                oid = FRMQueryString.requestLong(request, "oid");
                String coverNumber = FRMQueryString.requestString(request, "covernumber");
                String master_event = FRMQueryString.requestString(request, "master_event");
                String master_group = FRMQueryString.requestString(request, "master_group");
                try{
                    BillMain billMain = PstBillMain.fetchExc(oid);
                    billMain.setCoverNumber(coverNumber);
                    long oidx = PstBillMain.updateExc(billMain);
                    try{
                        if(oid!=0){
                            long xx = PstBillMain.updateEventName(oid,master_event+"|"+master_group);
                        }
                    }catch(Exception ex){}
                    frmMsg = "success";
                }catch(Exception ex){
                   
                }
            }else if(loadtype.equals("edititem")){
		try{
		    CtrlCustomBillDetail ctrlBillDetail = new CtrlCustomBillDetail(request);
		    iErrCode = ctrlBillDetail.action(iCommand,oid,request);
		    frmMsg = ctrlBillDetail.getMessage();
		}catch(Exception ex){
		    frmMsg = "FAILED";
		}
	    //EDIT ITEM PROCCESS
            }else if (loadtype.equals("editItemProc")){
                int fromEditable =0;
                try {
                    fromEditable = FRMQueryString.requestInt(request, "fromEditable");
                }catch(Exception ex){
                    fromEditable = 0;
                }
                try{
                    
                    long oidDetail = FRMQueryString.requestLong(request, ""+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID]+"");
		    CtrlCustomBillDetail ctrlBillDetail = new CtrlCustomBillDetail(request);
		    iErrCode = ctrlBillDetail.action(iCommand,oidDetail,request);
                    if (iErrCode==0 && fromEditable==1){
                        //JIKA ASAL REQUEST DARI TRANSACTION MAINTENANCE, MAKA MASUK KE BAGIAN INI
                        BillMain billMain = new BillMain();
                        long oidBillMain = FRMQueryString.requestLong(request, "FRM_FIELD_CASH_BILL_MAIN_ID");
                        try {
                            billMain = PstBillMain.fetchExc(oidBillMain);
                            double total = PstBillDetail.getTotalPrice2(oidBillMain);
                            billMain.setAmount(total);
                            CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
                            iErrCode = ctrlBillMain.actionObject(Command.SAVE, billMain, request);
                        } catch (Exception e) {
                            frmMsg = "FAILED";
                        }
                    }
                    
                    
		    frmMsg = ctrlBillDetail.getMessage();
		}catch(Exception ex){
		    frmMsg = "FAILED";
		}
            //DELETE ITEM PROCESS 
	    }else if(loadtype.equals("delitemproccess")){
                    
                String spvName = FRMQueryString.requestString(request,"spvName");
                String noteVoid = FRMQueryString.requestString(request,""+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_NOTE]+"");
                
                int command = Command.DELETE;
                noteVoid=" reason : "+noteVoid+" - authorize void :  "+spvName;
                Long oidBillDetail  = oid;
                Billdetail billDetails = new Billdetail();
                try {
                    billDetails = PstBillDetail.fetchExc(oidBillDetail); 
                } catch (Exception e) {
                }
                
                int status=0;
                try {
                    CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
                    if (cashierItemErrorCantDelete.equals("0")){
                        //DELETE KEMUDIAN DI TAMBAHKAN DI TABEL DETAIL VOID
                        iErrCode = ctrlBillMain.actionUpdateItem(command, oidBillDetail, status, noteVoid,0);
                        frmMsg = String.valueOf(billDetails.getBillMainId());
                    }else{
                        //DELETE HANYA MENGGANTI HARGA DENGAN 0
                        iErrCode = ctrlBillMain.actionUpdateItem2(command, oidBillDetail, status, noteVoid,0);
                        frmMsg = String.valueOf(billDetails.getBillMainId());
                    }
                    
                } catch (Exception ex) {
                    frmMsg = "FAILED";
                }
                
	    //CANCEL BILL
	    }else if(loadtype.equals("cancelbill")){
		try{
		    CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
		    iErrCode = ctrlBillMain.actionCancel(iCommand, oid, request);
		    frmMsg = ctrlBillMain.getMessage();
		}catch(Exception ex){
		    frmMsg = "FAILED";
		}
		
	    ///PAYMENT MOVE BILL
	    }else if(loadtype.equals("movebill")){
		try{
		    CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
		    iErrCode = ctrlBillMain.actionMove(iCommand, oid, request);
		    frmMsg = ctrlBillMain.getMessage();
                    displayTotal = String.valueOf(ctrlBillMain.getOidBillMove());
		}catch(Exception ex){
		    frmMsg = "FAILED";
		}
		
		
	    ///CLOSING 
	    }else if(loadtype.equals("closing")){
		if(listSpv.size() > 0){
		    AppUser appUser = (AppUser) listSpv.get(0);
		    CtrlCloseCashCashier ctrlCloseCashCashier = new CtrlCloseCashCashier(request);
		    iErrCode = ctrlCloseCashCashier.action(iCommand, oidCashier, request, appUser.getOID());
		    Vector listCashier = PstCashCashier.listCashOpening(0, 0, 
			    "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID]+"='"+oidCashier+"'", "");
		    Vector listCashierData = new Vector(1,1);
		    if(listCashier.size() != 0){
			listCashierData = (Vector) listCashier.get(0);
		    }
		    frmMsg = drawPrintClosing(listCashierData, loginId);
                    shortMessage = getShortMessage(request,listCashier);
		}else{
		    frmMsg = "FAILED";
		}
		
		
	    //RETURN BILL
	    }else if(loadtype.equals("returnbill")){
		CtrlReturn ctrlReturn = new CtrlReturn(request);
		iErrCode = ctrlReturn.action(iCommand, oid, request);
		frmMsg = drawPrintReturn(oid, loginId, request.getContextPath(),"");
            //JOIN BILL
            }else if (loadtype.equals("saveJoin")){   
                CtrlCustomBillDetail ctrlCustomBillDetail = new CtrlCustomBillDetail(request);
                iErrCode = ctrlCustomBillDetail.actionJoin(Command.SAVE, request);
                frmMsg = ctrlCustomBillDetail.getMessage();
            //CONFIRM PRINT STATUS
            }else if(loadtype.equals("confirmPrintStatus")){ 
                CtrlCustomBillDetail ctrlCustomBillDetail = new CtrlCustomBillDetail(request);
                iErrCode = ctrlCustomBillDetail.actionJoin(Command.CONFIRM, request);
                frmMsg = ctrlCustomBillDetail.getMessage();
	    }else if(loadtype.equals("additem")){
                int fromEditable =0;
                try {
                    fromEditable = FRMQueryString.requestInt(request, "fromEditable");
                }catch(Exception ex){
                    fromEditable = 0;
                }
		CtrlCustomBillDetail ctrlCustomBillDetail = new CtrlCustomBillDetail(request);
		iErrCode = ctrlCustomBillDetail.action(iCommand, 0, request);
                
                if (iErrCode==0 && fromEditable==1){
                    //JIKA ASAL REQUEST DARI TRANSACTION MAINTENANCE, MAKA MASUK KE BAGIAN INI
                    BillMain billMain = new BillMain();
                    long oidBillMain = FRMQueryString.requestLong(request, "FRM_FIELD_CASH_BILL_MAIN_ID");
                    try {
                        billMain = PstBillMain.fetchExc(oidBillMain);
                        double total = PstBillDetail.getTotalPrice2(oidBillMain);
                        billMain.setAmount(total);
                        CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
                        iErrCode = ctrlBillMain.actionObject(Command.SAVE, billMain, request);
                    } catch (Exception e) {

                    }
                }
                
		frmMsg = ctrlCustomBillDetail.getMessage();
	    }else if(loadtype.equals("newmember")){
		CtrlQueensContactList ctrlContactList = new CtrlQueensContactList(request);
		iErrCode = ctrlContactList.action(iCommand, 0);
		
		frmMsg = ctrlContactList.getMessage();
	    }else if(loadtype.equals("neworder")){
		CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
		iErrCode = ctrlBillMain.action(iCommand, 0, request);
		frmMsg = ctrlBillMain.getMessage();
            }else if (loadtype.equals("newctmember")){
                CtrlQueensContactList ctrlContactList = new CtrlQueensContactList(request);
		iErrCode = ctrlContactList.action(iCommand, 0);
		frmMsg = ctrlContactList.getMessage(); 
            }else if (loadtype.equals("confirmorder")){
                Billdetail billDetail = new Billdetail();
                long oidBillDetail = FRMQueryString.requestLong(request, "oid");
                long oidUser = FRMQueryString.requestLong(request, "userid");
                try {
                    billDetail = PstBillDetail.fetchExc(oidBillDetail);
                } catch (Exception e) {
                }
                billDetail.setStatus(3);
                billDetail.setUserIdToServed(oidUser);
                billDetail.setDateUserToServed(new Date());
                billDetail.setLengthFinishOrder(new Date());
                CtrlBillDetail ctrlBillDetail = new CtrlBillDetail(request);
                iErrCode = ctrlBillDetail.actionObject(Command.SAVE, billDetail, billDetail.getBillMainId());
                //frmMsg = ctrlBillDetail.getMessage();
	    }else{
                
                //CHECK COVER NUMBER
                long billMainId = FRMQueryString.requestLong(request, FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]);
                String isDynamicTemplate = PstSystemProperty.getValueByName("PRINT_DYNAMIC_TEMPLATE");
               
		try{
		    
		    CtrlCustomPayment ctrlCustomPayment = new CtrlCustomPayment(request);
		    
		    try{
                        BillMain billMainCheck = PstBillMain.fetchExc(billMainId);
                            if(transactionType != 0){
                                iErrCode = ctrlCustomPayment.action(iCommand, paymentType, request);
                                frmMsg = ctrlCustomPayment.getMessage();
                                if(frmMsg == "SUCCESS"){
                                    int separate = FRMQueryString.requestInt(request, "separateprint");
                                    //jika separate = 0, maka sistem akan mencetak full bill, jika 1 
                                    //maka sistem hanya akan mencetak payment only 
                                    if(isDynamicTemplate.equals("1")){
                                        BillMain billMain = new BillMain();
                                        try{
                                            billMain = PstBillMain.fetchExc(oid);
                                        }catch(Exception ex){
                                            ex.printStackTrace();
                                        }
                                        frmMsg = templateHandler(billMain);
                                    }else{
                                        if (separate==0){
                                            frmMsg = drawPrint(transactionType, oid, ""+loginId, 
                                                taxInc, paymentType, ccName, ccNo, ccBank, ccValid, ccCharge, 
                                                payAmount, oidCashier,"printpay", request.getContextPath(),oidMember);
                                        }else{
                                            frmMsg = drawPrintPaymentOnly(transactionType, oid, ""+loginId, 
                                                taxInc, paymentType, ccName, ccNo, ccBank, ccValid, 
                                                ccCharge, payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);
                                        }
                                    }
                                    

                                    if(transactionType == 2){

                                        CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
                                        CtrlBillDetail ctrlBillDetail = new CtrlBillDetail(request);
                                        iErrCode = ctrlBillMain.actionCancel(Command.DELETE, oid, request);
                                        Vector listBillDetail = PstBillDetail.list(0, 0, 
                                                ""+ PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+oid+"'", 
                                                "");
                                        if(listBillDetail.size() != 0){
                                            for(int i=0;i<listBillDetail.size();i++){
                                                Billdetail billdetail = (Billdetail) listBillDetail.get(i);
                                                iErrCode = ctrlBillDetail.action2(Command.DELETE, billdetail.getOID(), oid,billdetail.getParentId());
                                            }
                                        }
                                    }

                                    autoCheckOut(request, billMainId);
                                }
                            }else{

                                iErrCode = ctrlCustomPayment.action(iCommand, oid, request);
                                frmMsg = ctrlCustomPayment.getMessage();
                                if(frmMsg == "SUCCESS"){

                                    //DISC GLOBAL
                                    String getDiscPct = FRMQueryString.requestString(request, "FRM_FIELD_DISC_PCT");
                                    String getDiscNominal = FRMQueryString.requestString(request, "FRM_FIELD_DISC_GLOBAL");
                                    if(getDiscPct!=null){
                                        getDiscPct = getDiscPct.replace(",", "");
                                    }
                                    if(getDiscNominal!=null){
                                        getDiscNominal =getDiscNominal.replace(",","");
                                    }
                                    double discPct = Double.parseDouble(getDiscPct);
                                    double discNominal = Double.parseDouble(getDiscNominal);
                                    updateDiscDetail(oid, discPct, discNominal);

                                    int separate = FRMQueryString.requestInt(request, "separateprint");
                                    //jika separate = 0, maka sistem akan mencetak full bill, jika 1 
                                    //maka sistem hanya akan mencetak payment only 
                                    if(isDynamicTemplate.equals("1")){
                                        BillMain billMain = new BillMain();
                                        try{
                                            billMain = PstBillMain.fetchExc(oid);
                                        }catch(Exception ex){
                                            ex.printStackTrace();
                                        }
                                        frmMsg = templateHandler(billMain);
                                    }else{
                                        
                                        if (separate==0){
                                            frmMsg = drawPrint(transactionType, oid, ""+loginId, 
                                                taxInc, paymentType, ccName, ccNo, ccBank, ccValid, 
                                                ccCharge, payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);
                                        }else{
                                            frmMsg = drawPrintPaymentOnly(transactionType, oid, ""+loginId, 
                                                taxInc, paymentType, ccName, ccNo, ccBank, ccValid, 
                                                ccCharge, payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);

                                        }
                                    }
                                    autoCheckOut(request, billMainId);
                                }


                                //DI COMMENT SEMENTARA BY DE KOYO, KARENA MULTI PAYMENT MENGGUNAKAN VOUCHER SLL DOUBLE
                                /*PaymentSystem paymentSystem = PstPaymentSystem.fetchExc(paymentType);
                                if(paymentSystem.getPaymentType() == 4){
                                    Vector listVoucher = PstVoucher.list(0, 0, 
                                            "("+PstVoucher.fieldNames[PstVoucher.FLD_VOUCHER_BARCODE]+"='"+voucherCode+"' "
                                            + "OR "+PstVoucher.fieldNames[PstVoucher.FLD_VOUCHERNO]+"='"+voucherCode+"') "
                                            + "AND "+PstVoucher.fieldNames[PstVoucher.FLD_VOUCHERSTATUS]+"='0'","");
                                    if(listVoucher.size() != 0){
                                        for(int i = 0; i<listVoucher.size();i++){
                                            Voucher voucher = (Voucher) listVoucher.get(i);
                                            if(voucher.getVoucherType() == 0){
                                                iErrCode = ctrlCustomPayment.action(iCommand, oid, request);
                                                frmMsg = ctrlCustomPayment.getMessage();
                                                if(frmMsg == "SUCCESS"){
                                                    frmMsg = drawPrint(transactionType, oid, ""+loginId, 
                                                            taxInc, paymentType, ccName, ccNo, ccBank, ccValid, 
                                                            ccCharge, payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);
                                                }else{
                                                    Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
                                                    Vector currency_key = new Vector(1,1);
                                                    Vector currency_value = new Vector(1,1);
                                                    Vector data_rate = new Vector(1,1);

                                                    if(listCurrency.size() != 0){
                                                        for(int iCurrency = 0;iCurrency<listCurrency.size(); iCurrency++){
                                                            CurrencyType currencyType = (CurrencyType) listCurrency.get(iCurrency);
                                                            currency_key.add(""+currencyType.getOID());
                                                            currency_value.add(""+currencyType.getCode());
                                                            //CEK RATE DARI CURRENCY INI
                                                            double exChangeRate = PstCurrencyType.getExchangeRate(" CURRENCY_TYPE_ID ='"+currencyType.getOID()+"'");
                                                            data_rate.add(""+exChangeRate+"");
                                                        }
                                                    }
                                                    html = ""
                                                    + "<div class='row'>"
                                                        + "<div class='box-body'>"
                                                           + "<div class='col-md-12'>"
                                                                + "<div class='col-md-6'>"

                                                                + "</div>"
                                                                + "<div class='col-md-6'>"
                                                                    + "<b>"+Formater.formatNumber(costumBalance,"#,###.##")+"</b>"
                                                                + "</div>"
                                                           + "</div>"
                                                       + "</div>"
                                                    + "</div>"
                                                    + "<div class='row'>"
                                                        + "<div class='box-body'>"
                                                           + "<div class='col-md-12'>"
                                                                + "<div class='col-md-6'>"
                                                                    + ControlCombo.drawBootsratapData(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control",data_rate,"rate")
                                                                + "</div>"
                                                                + "<div class='col-md-6'>"
                                                                    +"<input type='text' name='"+FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT]+"' class='form-control calc money' value='"+Formater.formatNumber(Double.valueOf(frmMsg), "#,###.##")+"'>"
                                                                    + "<input type='hidden' name='cashpaid' value='"+frmMsg+"' id='cashpaid'>"
                                                                    + "<input type='hidden' name='vouchervalid' value='true'>"
                                                                + "</div>"
                                                           + "</div>"
                                                       + "</div>"
                                                   + "</div>";
                                                }
                                            }else{
                                                //JIKA AMOUNT KURANG DARI VOUCHER MAKA TAMBAHKAN ITEM KE ADD DETAIL


                                                iErrCode = ctrlCustomPayment.action(iCommand, oid, request);
                                                frmMsg = ctrlCustomPayment.getMessage();
                                                if(frmMsg == "SUCCESS"){
                                                    frmMsg = drawPrint(transactionType, oid, ""+loginId, 
                                                            taxInc, paymentType, ccName, ccNo, ccBank, ccValid, 
                                                            ccCharge, payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);
                                                }
                                            }
                                        }
                                    }else{
                                        frmMsg = "FAILED";
                                    }
                                }else{

                                    iErrCode = ctrlCustomPayment.action(iCommand, oid, request);
                                    frmMsg = drawPrint(transactionType, oid, ""+loginId, 
                                            taxInc, paymentType, ccName, ccNo, ccBank, ccValid, 
                                            ccCharge, payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);
                                }*/
                            }
                        
		    }catch(Exception ex){
			
		    }
		    
		}catch(Exception ex){
		    //ex.printStackTrace();
		}
	    }
	    html = frmMsg;
	}
	
	try{
	    returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_HTML], html);
	    returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_DISPLAY_TOTAL], displayTotal);
	    returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_BALANCE_VALUE], balanceValue);
	    returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_CREDIT_CARD_CHARGE], creditCardCharge);
	    returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_PAID_VALUE],paidValue);
	    returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_TAX_INC],taxInc);
	    returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_COSTUM_BALANCE], costumBalance);
	    returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_STATUS_ORDER], statusOrder);
            returnData.put("FRM_FIELD_SHORT_MESSAGE", shortMessage);
	}catch(Exception ex){

	}
	response.getWriter().println(returnData);
    }
    
    public String drawList(int iCommand, Vector objectClass, long oidPaymentRec, FrmBillMain frmObject,String isPack) {
        //2
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("No", "20%");
        ctrlist.addHeader("Name", "20%");
        ctrlist.addHeader("Quantity");
	ctrlist.addHeader("Price");
	ctrlist.addHeader("Discount");
	ctrlist.addHeader("Total");
	ctrlist.addHeader("Action");

        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit

        ctrlist.reset();

        int index = -1;

        Vector rowx = new Vector(1, 1);
	double grandTotal = 0;
	String addInner = "";
        String addClass="";
	int specialItem = 0;
	String fieldSpecial = "";
        
        //SISTEM PROPERTY UNTUK MENGECEK APAKAH KASIR SUPPORT PRODUKSI
        String cashierSupportProduction= PstSystemProperty.getValueByName("CASHIER_SUPPORT_PRODUCTION");
        //SISTEM PROPERTY UNTUK MENGECEK, APAKAH CHECK OUT VIA OUTLET
        String checkOutViaOutlet= PstSystemProperty.getValueByName("CHECKOUT_ITEM_VIA_OUTLET");
        if (objectClass.size()>0){
            for (int i = 0; i < objectClass.size(); i++) {

                //long famOid = 0;
                Billdetail billdetail = (Billdetail) objectClass.get(i);
                if (billdetail.getCutomePackBillingId()!=0){
                    isPack="1";
                }else{
                    isPack="0";
                }
                String bold="";
                if (isPack.equals("1") && billdetail.getParentId()==0){
                    bold = "font-weight:bold;";
                }else{
                    bold = "";
                }
                rowx = new Vector(1, 1);
                grandTotal+=(billdetail.getItemPrice()-billdetail.getDisc()-billdetail.getDiscGlobal())*billdetail.getQty();
                int countClass = 0;
                
                
                
                
                rowx.add(""+(i+1));
                if (countClass==0){
                    addClass = "firstFocus";
                }else{
                    addClass="";
                }
                rowx.add("<input style='"+bold+"' type='text' readonly='readonly' id='textual3_"+countClass+"' class='form-control textual3 "+addClass+"' value='"+billdetail.getItemName()+"'>");
                rowx.add(""+billdetail.getQty());
                rowx.add(""+Formater.formatNumber(billdetail.getItemPrice(),"#,###"));
                rowx.add(""+Formater.formatNumber(billdetail.getDisc(),"#,###"));
                if((i+1) == objectClass.size()){
                    addInner +="<input type='hidden' id='grandTotalData' value='"+ Formater.formatNumber(grandTotal,"#,###")+"'>";
                }
                rowx.add(""+Formater.formatNumber((billdetail.getItemPrice()-billdetail.getDisc()-billdetail.getDiscGlobal())*billdetail.getQty(),"#,###")+addInner);
                String tempRow = ""
                    + "<div class='btn-group'>"
                    //+ "<div class='dropdown'>"
                        + "<button class='btn btn-primary dropdown-toggle' type='button' data-toggle='dropdown'>Actions "
                        + "<span class='fa fa-caret-right'></span></button>"
                        + "<ul class='dropdown-menu dropdown-menu-right' role='menu' style='position: absolute;top:-100%;'>"
                            + "<li role='presentation'><a role='menuitem' tabindex='-1' href='#' data-load-type='edititem' class='authorize editmainitem' id='editmainitem_"+i+"' data-title='EDIT ITEM' data-oid='"+billdetail.getOID()+"' data-consume-id='"+billdetail.getCutomePackBillingId()+"' data-schedule-id='"+billdetail.getCostumeScheduleId()+"'><i class='fa fa-pencil'></i> Edit</a></li>"
                            + "<li role='presentation'><a role='menuitem' tabindex='-1' href='#' data-load-type='delitem' class='authorize deletemainitem' id='deletemainitem_"+i+"'  data-title='DELETE ITEM' data-oid='"+billdetail.getOID()+"'><i class='fa fa-remove'></i>Delete</a></li>"
                        + "</ul>"
                    //+ "</div>"
                    + "";
                if (cashierSupportProduction.equals("1") && checkOutViaOutlet.equals("1")){
                    if (billdetail.getStatus()==0 && billdetail.getStatusPrint()==1){
                        tempRow += ""
                        + "&nbsp; <button data-billdetail='"+billdetail.getOID()+"'  type='button' class='btn btn-success confirmorder'>&nbsp; <i class='fa fa-check-circle'></i> &nbsp;</button> ";
                    }else if(billdetail.getStatus()==3 && billdetail.getStatusPrint()==1){
                        tempRow += ""
                        + "&nbsp; <button type='button' class='btn btn-warning'>&nbsp;<i class='fa fa-star'></i> &nbsp; </button> ";
                    }
                    
                }
                tempRow += "</div>";
                rowx.add(tempRow);
                lstData.add(rowx);
                countClass += 1;
                
                //get the child of these package
                String whereChild = ""
                    + " billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+billdetail.getBillMainId()+"' "
                    + " AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
                    + " AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
                    + " AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
                    + " AND billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'"
                    + " AND billDetail."+PstBillDetail.fieldNames[PstBillDetail.FLD_STATUS]+" <> '3'"
                    + " AND billDetail."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='"+billdetail.getOID()+"'";
                
                
                Vector listChild = PstCustomBillMain.listItemOpenBill(0, 0, whereChild, "");
                for (int j=0; j<listChild.size();j++){
                    Billdetail entBillDetail = new Billdetail();
                    entBillDetail = (Billdetail) listChild.get(j);
                    rowx = new Vector(1, 1);
                    rowx.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+(j+1));
                    if (countClass==0){
                        addClass = "firstFocus";
                    }else{
                        addClass="";
                    }
                    rowx.add("<input style='padding-left: 35px;' type='text' readonly='readonly' id='textual3_"+countClass+"' class='form-control textual3 "+addClass+"' value='"+entBillDetail.getItemName()+"'>");
                    rowx.add(""+entBillDetail.getQty());
                    rowx.add(""+Formater.formatNumber(entBillDetail.getItemPrice(),"#,###"));
                    rowx.add(""+Formater.formatNumber(entBillDetail.getDisc(),"#,###"));
                    rowx.add(""+Formater.formatNumber(entBillDetail.getTotalPrice(),"#,###")+addInner);
                    String tempRow2 = ""
                        + "<div class='btn-group'>"
                        //+ "<div class='dropdown'>"
                            + "<button class='btn btn-primary dropdown-toggle' type='button' data-toggle='dropdown'>Actions "
                            + "<span class='fa fa-caret-right'></span></button>"
                            + "<ul class='dropdown-menu dropdown-menu-right' role='menu' style='position: absolute;top:-100%;'>"
                                + "<li role='presentation'><a role='menuitem' tabindex='-1' href='#' data-load-type='edititem' class='authorize editmainitem' id='editmainitem_"+i+"' data-title='EDIT ITEM' data-oid='"+entBillDetail.getOID()+"'><i class='fa fa-pencil'></i> Edit</a></li>"
                                + "<li role='presentation'><a role='menuitem' tabindex='-1' href='#' data-load-type='delitem' class='authorize deletemainitem' id='deletemainitem_"+i+"'  data-title='DELETE ITEM' data-oid='"+entBillDetail.getOID()+"'><i class='fa fa-remove'></i>Delete</a></li>"
                            + "</ul>"
                        //+ "</div>"
                        + "";
                    if (cashierSupportProduction.equals("1") && checkOutViaOutlet.equals("1")){
                        if (entBillDetail.getStatus()==0 && entBillDetail.getStatusPrint()==1){
                            tempRow2 += ""
                            + "&nbsp; <button data-billdetail='"+entBillDetail.getOID()+"'  type='button' class='btn btn-success confirmorder'>&nbsp; <i class='fa fa-check-circle'></i> &nbsp;</button> ";
                        }else if(entBillDetail.getStatus()==3 && entBillDetail.getStatusPrint()==1){
                            tempRow2 += ""
                            + "&nbsp; <button type='button' class='btn btn-warning'>&nbsp;<i class='fa fa-star'></i> &nbsp; </button> ";
                        }

                    }
                    tempRow2 += "</div>";
                    rowx.add(tempRow2);
                    lstData.add(rowx);
                    countClass += 1;
                }
                
                if (isPack.equals("1") && billdetail.getParentId()==0){
                    rowx = new Vector(1, 1);
                    
                    rowx.add("&nbsp;");
                    rowx.add("&nbsp;");
                    rowx.add("&nbsp;");
                    rowx.add("&nbsp;");
                    rowx.add("&nbsp;");
                    rowx.add("&nbsp;");
                    rowx.add(""
                    + "<button data-parentId='"+billdetail.getOID()+"' class='btn btn-primary btnAddPackageItem' > " 
                        + "Add Item Package" 
                    + "</button>"
                    + "");
                    lstData.add(rowx);
                }
                
            }
        }else{
            rowx = new Vector(1, 1);
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            addInner +="<input type='hidden' id='grandTotalData' value='0'>";
            rowx.add(""+addInner);
            rowx.add("");
            lstData.add(rowx);
        }
        
        return ctrlist.drawBootstrapStripted();
    }
    
    public String drawListReturn(int iCommand, Vector objectClass, long oidPaymentRec, FrmBillMain frmObject) {
        //2
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("No", "20%");
        ctrlist.addHeader("Name", "20%");
        ctrlist.addHeader("Quantity");
	ctrlist.addHeader("Price");
	ctrlist.addHeader("Discount");
	ctrlist.addHeader("Total");
	ctrlist.addHeader("Return Qty");

        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit

        ctrlist.reset();

        int index = -1;
        String addClass="";
        Vector rowx = new Vector(1, 1);
	double grandTotal = 0;
	String addInner = "";
        int counterHelp = 0;
        for (int i = 0; i < objectClass.size(); i++) {
            //long famOid = 0;
            
            //firstFocus class
            if (i==0){
                addClass="firstFocus";
            }else if (i==(objectClass.size()-1)){
                addClass="lastFocus";
            }else{
                addClass="";
            }

            Billdetail billdetail = (Billdetail) objectClass.get(i);
            BillMain billMain = new BillMain();
            
            //cek apakah item ini sudah pernah di return sebelumnya
            String whereSumQty = ""
                + " "+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_RETURN_ID]+"='"+billdetail.getOID()+"'";
            double sumQty = PstBillDetail.getSumTotalItemReturn(whereSumQty);
            double remainItem = billdetail.getQty()-sumQty;
            
            try {
                billMain = PstBillMain.fetchExc(billdetail.getBillMainId());
            } catch (Exception e) {
            }
            
            int checkReturn = PstCustomBillMain.checkReturn(oidPaymentRec, billdetail.getOID());
	    
	    if(checkReturn > 0){
                if (remainItem>0){
                    rowx = new Vector(1, 1);
                    grandTotal+=billdetail.getTotalPrice();

                    rowx.add(""+(counterHelp+1));
                    rowx.add(""+billdetail.getItemName());
                    //rowx.add(""+billdetail.getQty());
                    rowx.add(""+remainItem);
                    rowx.add(""+Formater.formatNumber(billdetail.getItemPrice(),"#,###"));
                    rowx.add(""+Formater.formatNumber(billdetail.getDisc(),"#,###"));
                    if((i+1) == objectClass.size()){
                        addInner +="<input type='hidden' id='grandTotalData' value='"+ Formater.formatNumber(grandTotal,"#,###")+"'>";
                    }
                    double priceShow = 0;
                    //priceShow = (billdetail.getItemPrice()-billdetail.getDisc())*billdetail.getQty();
                    priceShow = (billdetail.getItemPrice()-billdetail.getDisc())*remainItem;
                    rowx.add(""+Formater.formatNumber(priceShow,"#,###")+addInner);
                    rowx.add("<input  type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]+"_"+i+"' value='0' id='qtyTemp-"+i+"' class='form-control qtytemp returntext textual6 "+addClass+" ' style='width:100px;'> "
                            + "<input type='hidden' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID]+"' value='"+billdetail.getOID()+"'>"
                            + "<input type='hidden' class='qtyReturnHelp' id='qtyReturn-"+i+"' value='"+billdetail.getQty()+"'>");
                    lstData.add(rowx);
                    counterHelp +=1;
                }else{
                    rowx = new Vector(1, 1);
                    rowx.add("");
                    rowx.add("");
                    //rowx.add(""+billdetail.getQty());
                    rowx.add("");
                    rowx.add("");
                    rowx.add("");
                    if((i+1) == objectClass.size()){
                        addInner +="<input type='hidden' id='grandTotalData' value='"+ Formater.formatNumber(grandTotal,"#,###")+"'>";
                    }
                    rowx.add("");
                    rowx.add("<input  type='hidden' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]+"_"+i+"' value='0' id='qtyTemp-"+i+"' class='form-control qtytemp returntext textual6 "+addClass+" ' style='width:100px;'> "
                            + "<input type='hidden' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID]+"' value='"+billdetail.getOID()+"'>"
                            + "<input type='hidden' class='qtyReturnHelp' id='qtyReturn-"+i+"' value='"+billdetail.getQty()+"'>");
                    lstData.add(rowx);
                }
		
	    }
            
        }
        return ctrlist.drawBootstrapStripted();
    }
    
    public String drawListJoinDetail(int iCommand, Vector objectClass, long oidPaymentRec, FrmBillMain frmObject) {
        //2
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("No", "20%");
        ctrlist.addHeader("Name", "20%");
        ctrlist.addHeader("Quantity");
	ctrlist.addHeader("Price");
	ctrlist.addHeader("Discount");
	ctrlist.addHeader("Total");
	ctrlist.addHeader("Join Qty");

        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit

        ctrlist.reset();

        int index = -1;
        String addClass="";
        Vector rowx = new Vector(1, 1);
	double grandTotal = 0;
	String addInner = "";
        for (int i = 0; i < objectClass.size(); i++) {
            //long famOid = 0;
            
            //firstFocus class
            if (i==0){
                addClass="firstFocus";
            }else if (i==(objectClass.size()-1)){
                addClass="lastFocus";
            }else{
                addClass="";
            }

            Billdetail billdetail = (Billdetail) objectClass.get(i);

            int checkReturn = PstCustomBillMain.checkReturn(oidPaymentRec, billdetail.getOID());
	    
	    if(checkReturn > 0){
		rowx = new Vector(1, 1);
		grandTotal+=billdetail.getTotalPrice();

		rowx.add(""+(i+1));
		rowx.add(""+billdetail.getItemName());
		rowx.add(""+billdetail.getQty());
		rowx.add(""+Formater.formatNumber(billdetail.getItemPrice(),"#,###"));
		rowx.add(""+Formater.formatNumber(billdetail.getDisc(),"#,###"));
		if((i+1) == objectClass.size()){
		    addInner +="<input type='hidden' id='grandTotalData' value='"+ Formater.formatNumber(grandTotal,"#,###")+"'>";
		}
		rowx.add(""+Formater.formatNumber(billdetail.getTotalPrice(),"#,###")+addInner);
		rowx.add("<input data-billdetiloid='"+billdetail.getOID()+"'  type='text' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]+"_"+i+"' value='0' id='qtyJoinTemp-"+i+"' class='form-control qtyjointemp textual10 "+addClass+" ' style='width:100px;'> "
			+ "<input type='hidden' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID]+"' value='"+billdetail.getOID()+"'>"
                        + "<input type='hidden' class='qtyJoinHelp' id='qtyJoin-"+i+"' value='"+billdetail.getQty()+"'>");
		lstData.add(rowx);
	    }
            
        }
        return ctrlist.drawBootstrapStripted();
    }
    
    
    public String drawListCancel(int iCommand, Vector objectClass, long oidPaymentRec, FrmBillMain frmObject) {
        //2
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("No", "20%");
        ctrlist.addHeader("Name", "20%");
        ctrlist.addHeader("Quantity");
	ctrlist.addHeader("Price");
	ctrlist.addHeader("Discount");
	ctrlist.addHeader("Total");
	ctrlist.addHeader("Moved QTY");

        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit

        ctrlist.reset();

        int index = -1;

        Vector rowx = new Vector(1, 1);
	double grandTotal = 0;
	String addInner = "";
        String addClass="";
        for (int i = 0; i < objectClass.size(); i++) {
            //long famOid = 0;
            //First and Last Focus
            if (i==0){
                addClass="firstFocus";
            }else if (i==(objectClass.size()-1)){
                addClass="lastFocus";
            }else{
                addClass="";
            }
            
            Billdetail billdetail = (Billdetail) objectClass.get(i);

            
            rowx = new Vector(1, 1);
	    grandTotal+=billdetail.getTotalPrice();
	    
	    rowx.add(""+(i+1));
	    rowx.add(""+billdetail.getItemName());
	    rowx.add(""+billdetail.getQty());
	    rowx.add(""+Formater.formatNumber(billdetail.getItemPrice(),"#,###"));
	    rowx.add(""+Formater.formatNumber(billdetail.getDisc(),"#,###"));
	    if((i+1) == objectClass.size()){
		addInner +="<input type='hidden' id='grandTotalData' value='"+ Formater.formatNumber(grandTotal,"#,###")+"'>";
	    }
	    rowx.add(""+Formater.formatNumber(billdetail.getTotalPrice(),"#,###")+addInner);
	    rowx.add("<input type='hidden' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID]+"' value='"+billdetail.getOID()+"'>"
                    + "<input type='hidden' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE]+"_"+billdetail.getOID()+"' value='"+billdetail.getItemPrice()+"'>"
                    + "<input type='hidden' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC]+"_"+billdetail.getOID()+"' value='"+billdetail.getDisc()+"'>"
                    
		    + "<input type='text' id='moveBill-"+i+"'  class='form-control "+addClass+" textual7 "+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]+"' name='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY]+"_"+billdetail.getOID()+"' value='0' size='1' data-max='"+billdetail.getQty()+"' data-min='0'>");
            lstData.add(rowx);
        } 
        return ctrlist.drawBootstrapStripted();
    }
    
    public String drawListSearch(int iCommand, Vector objectClass, long oidPaymentRec, String loadtype) {
        //2
        
        /*System Property*/
        
        int showRoomOnBill = 0;
        int showTotalOnBill =0;
        try {
            showRoomOnBill = Integer.parseInt(PstSystemProperty.getValueByName("CASHIER_SHOW_ROOM_ON_BILL_LOAD"));
        } catch (Exception e) {
        }
        
        try {
            showTotalOnBill = Integer.parseInt(PstSystemProperty.getValueByName("CASHIER_SHOW_TOTAL_ON_BILL_LOAD"));
        } catch (Exception e) {
        }
        
        
        ControlList ctrlist = new ControlList();
        
        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("No", "27%");
        ctrlist.addHeader("Location", "");
        ctrlist.addHeader("Date", "");
        if (showTotalOnBill==1){
            ctrlist.addHeader("Amount", "");
        }
        ctrlist.addHeader("Name", "");
        if (showRoomOnBill==1){
            ctrlist.addHeader("Room", "");
            ctrlist.addHeader("Table Number", "");
        }
	
        

        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit

        ctrlist.reset();

        int index = -1;
        String addClass="";
        Vector rowx = new Vector(1, 1);
        if (objectClass.size()>0){
            for (int i = 0; i < objectClass.size(); i++) {
                //long famOid = 0;
                //untuk focus pertama di grid
                if (i==0){
                    addClass="firstFocus";
                }else{
                    addClass ="";
                }
                BillMainCostum billMain = (BillMainCostum) objectClass.get(i);

                String guestNameTemp="";

                BillMain entBillMain = new BillMain();
                try {
                    entBillMain = PstBillMain.fetchExc(billMain.getOID());
                } catch (Exception e) {
                }
                if (entBillMain.getCustomerId()!=0 && entBillMain.getGuestName().length()==0){
                    Contact contact = new Contact();
                    try {
                        contact = PstContact.fetchExc(entBillMain.getCustomerId());
                    } catch (Exception e) {
                    }
                    if (contact.getPersonName().length()==0){
                        guestNameTemp = contact.getCompName();
                    }else{
                        guestNameTemp = contact.getPersonName() + " " + contact.getPersonLastname();
                    }
                }else{
                    guestNameTemp = billMain.getGuestName();
                }



                int checkReturn;

                if(loadtype.equals("loadsearchreturn")){
                    checkReturn = PstCustomBillMain.checkReturn(billMain.getOID());
                }else{
                    checkReturn = 1;
                }

                if(checkReturn > 0){
                    rowx = new Vector(1, 1);
                    if(loadtype.equals("loadsearchreturn")){
                        rowx.add("<input style='cursor:pointer;' type='text' readonly='readonly' class='form-control textual5 searchBillReturnSelect "+addClass+"' data-mainoid='"+billMain.getOID()+"'  value='"+billMain.getInvoiceNo()+"'>");
                        rowx.add(""+billMain.getLocationName()+"");
                        rowx.add(""+Formater.formatDate(billMain.getBillDate(), "yyyy-MM-dd kk:mm")+"");
                        if (showTotalOnBill==1){
                            //GET TOTAL PER BILL MAIN
                            double totalAmount = PstBillDetail.getTotalAmount(billMain.getOID());
                            double totalTax = PstBillDetail.getTotalAmountTax(billMain.getOID());
                            double totalService = PstBillDetail.getTotalAmountService(billMain.getOID());
                            rowx.add("<div style='text-align:right;'>"+Formater.formatNumber((totalAmount+totalTax+totalService), "###,###,##0")+"</div>");
                        }


                        rowx.add(""+guestNameTemp+"");
                        if (showRoomOnBill==1){
                            if (billMain.getRoomName() == null){
                                rowx.add("-");
                            }else{
                                rowx.add(""+billMain.getRoomName()+"");
                            }
                            if (billMain.getTableNumber()== null){
                                rowx.add("-"); 
                            }else{
                                rowx.add(""+billMain.getTableNumber()+"");
                            }
                        }

                    }else{
                        //cek apakah bill ini mengandung package 
                        String whereClause =""
                            + " billDetail."+PstBillDetail.fieldNames[PstBillDetail.FLD_CUSTOME_PACK_BILLING_ID]+"<>'0'"
                            + " AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'";
                        int count = PstCustomBillMain.getTotalPackage(0,0,whereClause,"");
                        int dataPackage = 0;
                        if (count>0){
                            dataPackage = 1;
                        }
                        rowx.add("<input style='cursor:pointer;' type='text' data-package='"+dataPackage+"' readonly='readonly' class='form-control textual2 searchBillSelect "+addClass+"' data-mainoid='"+billMain.getOID()+"' data-reservationid='"+billMain.getReservationId()+"' value='"+billMain.getInvoiceNo()+"'>");
                        rowx.add(""+billMain.getLocationName()+"");
                        rowx.add(""+Formater.formatDate(billMain.getBillDate(), "yyyy-MM-dd kk:mm")+"");
                        if (showTotalOnBill==1){
                            //GET TOTAL PER BILL MAIN
                            double totalAmount = PstBillDetail.getTotalAmount(billMain.getOID());
                            double totalTax = PstBillDetail.getTotalAmountTax(billMain.getOID());
                            double totalService = PstBillDetail.getTotalAmountService(billMain.getOID());
                            rowx.add("<div style='text-align:right;'>"+Formater.formatNumber((totalAmount+totalTax+totalService), "###,###,##0")+"</div>");
                        }
                        rowx.add(""+guestNameTemp+"");
                        if (showRoomOnBill==1){
                            if (billMain.getRoomName() == null){
                                rowx.add("-");
                            }else{
                                rowx.add(""+billMain.getRoomName()+"");
                            }
                            if (billMain.getTableNumber()== null){
                                rowx.add("-"); 
                            }else{
                                rowx.add(""+billMain.getTableNumber()+"");
                            }
                        }

                    }



                    lstData.add(rowx);
                }

            }
        }else{
            rowx = new Vector(1, 1);
            rowx.add("");
            rowx.add("");
            rowx.add("There is no bill left");
            if (showTotalOnBill==1){
                rowx.add("");
            }
            rowx.add("");
            if (showRoomOnBill==1){
                rowx.add("");
                rowx.add("");
            }
            lstData.add(rowx);
        }
        
        return ctrlist.drawBootstrapStriptedClass2("navigateable");
    }
    
    public String drawListReprint(int iCommand, Vector objectClass, String loadtype,long oidPaymentRec) {
        /*System Property*/
        int showTables =0;
        int showTotalOnBill=0;
        try {
            showTables = Integer.parseInt(PstSystemProperty.getValueByName("CASHIER_SHOW_ROOM_ON_BILL_LOAD"));
        } catch (Exception e) {
        }
        try {
            showTotalOnBill = Integer.parseInt(PstSystemProperty.getValueByName("CASHIER_SHOW_TOTAL_ON_BILL_LOAD"));
        } catch (Exception e) {
        }
        //2
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("No", "20%");
        ctrlist.addHeader("Location", "20%");
        ctrlist.addHeader("Date", "20%");
        if (showTotalOnBill==1){
            ctrlist.addHeader("Amount","20%");
        }
        ctrlist.addHeader("Name", "20%");
        if (showTables==1){
            ctrlist.addHeader("Room", "20%");
            ctrlist.addHeader("Table Number", "20%");
        }
	
        

        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit

        ctrlist.reset();

        int index = -1;
        
        String addClass="";

        Vector rowx = new Vector(1, 1);
        if (objectClass.size()>0){
            for (int i = 0; i < objectClass.size(); i++) {
                //long famOid = 0;

                //Untuk keperluan first focus
                if (i==0){
                    addClass ="firstFocus";
                }else{
                    addClass ="";
                }

                BillMainCostum billMain = (BillMainCostum) objectClass.get(i);
                String guestNameTemp="";

                BillMain entBillMain = new BillMain();
                try {
                    entBillMain = PstBillMain.fetchExc(billMain.getOID());
                } catch (Exception e) {
                }
                if (entBillMain.getCustomerId()!=0 && entBillMain.getGuestName().length()==0){
                    Contact contact = new Contact();
                    try {
                        contact = PstContact.fetchExc(entBillMain.getCustomerId());
                    } catch (Exception e) {
                    }
                    if (contact.getPersonName().length()==0){
                        guestNameTemp = contact.getCompName();
                    }else{
                        guestNameTemp = contact.getPersonName() + " " + contact.getPersonLastname();
                    }
                }else{
                    guestNameTemp = billMain.getGuestName();
                }

                int checkReturn=0;
                rowx = new Vector(1, 1);

                rowx.add("<input type='text' class='form-control reprintBillSelect textual4 "+addClass+"' data-oid='"+billMain.getOID()+"' style='cursor:pointer' data-oidmember='' readonly='readonly' value='"+billMain.getInvoiceNo()+"'>");
                rowx.add(""+billMain.getLocationName()+"");
                rowx.add(""+Formater.formatDate(billMain.getBillDate(), "yyyy-MM-dd kk:mm")+"");
                if (showTotalOnBill==1){
                    //GET TOTAL PER BILL MAIN
                    double totalAmount = PstBillDetail.getTotalAmount(billMain.getOID());
                    double totalTax = PstBillDetail.getTotalAmountTax(billMain.getOID());
                    double totalService = PstBillDetail.getTotalAmountService(billMain.getOID());
                    rowx.add("<div style='text-align:right;'>"+Formater.formatNumber((totalAmount+totalTax+totalService), "###,###,##0")+"</div>");
                }
                rowx.add(""+guestNameTemp+"");
                if (showTables==1){
                    if (billMain.getRoomName() == null){
                        rowx.add("-");
                    }else{
                        rowx.add(""+billMain.getRoomName()+"");
                    }
                    if (billMain.getTableNumber()== null){
                        rowx.add("-"); 
                    }else{
                        rowx.add(""+billMain.getTableNumber()+"");
                    }

                }

                lstData.add(rowx);	                
            }
        }else{
            rowx = new Vector(1, 1);
            rowx.add("");
            rowx.add("");
            rowx.add("There is no bill left");

            if (showTotalOnBill==1){
                rowx.add("");
            }
            rowx.add("");
            if (showTables==1){
                rowx.add("");
                rowx.add("");
            }
            lstData.add(rowx);
        }
        
        return ctrlist.drawBootstrapStriptedClass("navigateable");
    }
    
    public String drawListReprintFOC(Vector objectClass) {
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("No", "20%");
        ctrlist.addHeader("Date", "20%");
        ctrlist.addHeader("Name", "40%");
	ctrlist.addHeader("Location", "20%");
           
        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit
        ctrlist.reset();
        int index = -1;       
        String addClass="";

        Vector rowx = new Vector(1, 1);
        for (int i = 0; i < objectClass.size(); i++) {
           
            if (i==0){
                addClass ="firstFocus";
            }else{
                addClass ="";
            }
            
            
            
            MatCosting matCosting = (MatCosting) objectClass.get(i);
            if(matCosting.getPersonName()==null){
                matCosting.setPersonName("");
            }
	    int checkReturn=0;
            rowx = new Vector(1, 1);               
            rowx.add("<input type='text' class='form-control reprintBillFocSelect textual4 "+addClass+"' data-oid='"+matCosting.getOID()+"' style='cursor:pointer' readonly='readonly' value='"+matCosting.getCostingCode()+"'>");
            rowx.add(""+matCosting.getCostingDate()+"");
            rowx.add(""+matCosting.getPersonName()+"");
            rowx.add(""+matCosting.getLocationName()+"");
            lstData.add(rowx);    
        }
        return ctrlist.drawBootstrapStriptedClass("navigateable");
    }
    
    public String drawListJoin(int iCommand, Vector objectClass, String loadtype,long oidPaymentRec) {
        //2
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("No", "20%");
        ctrlist.addHeader("Date", "20%");
        ctrlist.addHeader("Name", "20%");
	ctrlist.addHeader("Room", "20%");
        ctrlist.addHeader("Table Number", "20%");
        

        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit

        ctrlist.reset();

        int index = -1;
        
        String addClass="";

        Vector rowx = new Vector(1, 1);
        for (int i = 0; i < objectClass.size(); i++) {
            //long famOid = 0;
            
            //Untuk keperluan first focus
            if (i==0){
                addClass ="firstFocus";
            }else{
                addClass ="";
            }
            
            BillMainCostum billMain = (BillMainCostum) objectClass.get(i);
	    
	    int checkReturn=0;
            rowx = new Vector(1, 1);
                
            rowx.add("<input type='text' class='form-control jointBillSelect textual9 "+addClass+"' data-oid='"+billMain.getOID()+"' style='cursor:pointer' data-oidmember='' readonly='readonly' value='"+billMain.getInvoiceNo()+"'>");
            rowx.add(""+billMain.getBillDate()+"");
            rowx.add(""+billMain.getGuestName()+"");
            
            if (billMain.getRoomName() == null){
                rowx.add("-");
            }else{
                rowx.add(""+billMain.getRoomName()+"");
            }
            if (billMain.getTableNumber()== null){
                rowx.add("-"); 
            }else{
                rowx.add(""+billMain.getTableNumber()+"");
            }
	
            lstData.add(rowx);
	    
            
        }
        return ctrlist.drawBootstrapStriptedClass("navigateable");
    }
    
    public String drawListSearchMember(int iCommand, Vector objectClass, long cashBillMainId, FrmBillMain frmObject) {
        //add opie-eyek 20170721
        //coba di cek disini, jika sudah di buatkan discount, tidak usah di tambahkan lagi
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("Name", "20%");
	ctrlist.addHeader("Phone Number","20%");
        ctrlist.addHeader("Company","20%");
        

        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit

        ctrlist.reset();

        int index = -1;
        
        BillMain billMain = new BillMain();
        if(cashBillMainId!=0){
            try{
                billMain = PstBillMain.fetchExc(cashBillMainId);
            }catch(Exception ex){
            }
        }

        Vector rowx = new Vector(1, 1);
        for (int i = 0; i < objectClass.size(); i++) {
            //long famOid = 0;
            ContactList contact = (ContactList) objectClass.get(i);
	    PersonalDiscount personalDiscount = new PersonalDiscount();
	    Vector listPersonDisc = PstPersonalDiscount.list(0, 0, ""+PstPersonalDiscount.fieldNames[PstPersonalDiscount.FLD_MATERIAL_ID]+"='0' "
		    + "AND "+PstPersonalDiscount.fieldNames[PstPersonalDiscount.FLD_CONTACT_ID]+"='"+contact.getOID()+"'", "");
	    if(listPersonDisc.size() != 0){
		personalDiscount = (PersonalDiscount) listPersonDisc.get(0);
	    }else{
                if(billMain.getDiscPct()!=0){
                    personalDiscount.setPersDiscPct(billMain.getDiscPct());
                }
                if(billMain.getDiscount()!=0){
                    personalDiscount.setPersDiscVal(billMain.getDiscount());
                }
            }
            
            rowx = new Vector(1, 1);
	    
	    rowx.add("<a class='selectGuest' data-oid='"+contact.getOID()+"' data-name='"+contact.getPersonName()+"' data-disc-pct='"+personalDiscount.getPersDiscPct()+"' data-disc-val='"+personalDiscount.getPersDiscVal()+"' data-phone-number='"+contact.getTelpMobile()+"' style='cursor:pointer;'>"+contact.getPersonName()+"</a>");
	    rowx.add("<a class='selectGuest' data-oid='"+contact.getOID()+"' data-name='"+contact.getPersonName()+"' data-disc-pct='"+personalDiscount.getPersDiscPct()+"' data-disc-val='"+personalDiscount.getPersDiscVal()+"' data-phone-number='"+contact.getTelpMobile()+"' style='cursor:pointer;'>"+contact.getTelpMobile()+"</a>");
            rowx.add("<a class='selectGuest' data-oid='"+contact.getOID()+"' data-name='"+contact.getPersonName()+"' data-disc-pct='"+personalDiscount.getPersDiscPct()+"' data-disc-val='"+personalDiscount.getPersDiscVal()+"' data-phone-number='"+contact.getTelpMobile()+"' style='cursor:pointer;'>"+contact.getCompName()+"</a>");
	   
            lstData.add(rowx);
        }
        return ctrlist.drawBootstrapStripted();
    }
    
    public String drawListSearchInHouseGuest(int iCommand, Vector objectClass, long oidPaymentRec, FrmBillMain frmObject, String searchtype) {
        //2
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("Name", "20%");
        ctrlist.addHeader("Room Number","20%");
	ctrlist.addHeader("Check Out","20%");
	

        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit

        ctrlist.reset();

        int index = -1;

        Vector rowx = new Vector(1, 1);
        for (int i = 0; i < objectClass.size(); i++) {
            //long famOid = 0;
	    
	    Vector listData = (Vector) objectClass.get(i);
	    ContactList contactList;
	    HotelRoom hotelRoom;
	    Reservation reservation;
	    Contact contact;
	    
	    if(listData.size() != 0){
		contactList = (ContactList) listData.get(0);
		hotelRoom = (HotelRoom) listData.get(1);
		reservation = (Reservation) listData.get(2);
		contact = (Contact) listData.get(3);
	    }else{
		contactList = new ContactList();
		hotelRoom = new HotelRoom();
		reservation = new Reservation();
		contact = new Contact();
	    }
            
            rowx = new Vector(1, 1);
	    if(searchtype.equals("guest")){
                if(hotelRoom.getRoomNumber().length() == 0){
                    hotelRoom.setRoomNumber("OUTLET RSV");
                }
		rowx.add("<a class='selectGuest' data-oid='"+contactList.getOID()+"' data-oidreservasi='"+reservation.getOID()+"' data-name='"+contactList.getPersonName()+"' data-disc-pct='"+0+"' data-disc-val='"+0+"' style='cursor:pointer;'>"+contactList.getPersonName()+"</a>");
		rowx.add("<a class='selectGuest' data-oid='"+contactList.getOID()+"' data-oidreservasi='"+reservation.getOID()+"' data-name='"+contactList.getPersonName()+"' data-disc-pct='"+0+"' data-disc-val='"+0+"' style='cursor:pointer;'>"+hotelRoom.getRoomNumber()+"</a>");
		rowx.add("<a class='selectGuest' data-oid='"+contactList.getOID()+"' data-oidreservasi='"+reservation.getOID()+"' data-name='"+contactList.getPersonName()+"' data-disc-pct='"+0+"' data-disc-val='"+0+"' style='cursor:pointer;'>"+reservation.getChekOutDate()+"</a>");
		
	    }else{
		if(reservation.getTravelAgentId() != 0){
		    rowx.add("<a class='selectGuest' data-oid='"+reservation.getTravelAgentId()+"' data-oidreservasi='"+reservation.getOID()+"' data-name='"+contactList.getPersonName()+"' data-disc-pct='"+0+"' data-disc-val='"+0+"' style='cursor:pointer;'>"+contactList.getPersonName()+" / "+contact.getCompName()+"</a>");
		    rowx.add("<a class='selectGuest' data-oid='"+reservation.getTravelAgentId()+"' data-oidreservasi='"+reservation.getOID()+"' data-name='"+contactList.getPersonName()+"' data-disc-pct='"+0+"' data-disc-val='"+0+"' style='cursor:pointer;'>"+hotelRoom.getRoomNumber()+"</a>");
		    rowx.add("<a class='selectGuest' data-oid='"+reservation.getTravelAgentId()+"' data-oidreservasi='"+reservation.getOID()+"' data-name='"+contactList.getPersonName()+"' data-disc-pct='"+0+"' data-disc-val='"+0+"' style='cursor:pointer;'>"+reservation.getChekOutDate()+"</a>");
		    
		}
	    }
	    
            lstData.add(rowx);
        }
        return ctrlist.drawBootstrapStripted();
    }
    
    public String drawPrint(int transactionType, long oidBillMain, String cashierName, 
	    int taxinc, long oidPayment, String ccName, String ccNumber,
	    String ccBank, String ccValid, double ccCharge, 
	    double payAmount, long cashierId, String printType, 
	    String approot, String oidMember){
	
	
	String displayPrint="";
	String transactionName = "";
	String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
	String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
	String displayPaymentSystem = "";
	String displayDiscPct = "";
	String invoiceNumb = "";
	String paymentType = "";
	BillMainCostum billMainCostum;
	MatCosting matCosting;
	try{
	    billMainCostum = PstCustomBillMain.fetchByCashierID(cashierId);
	    Vector listMatCosting = PstMatCosting.list(0, 0, 
		    ""+PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER]+"='"+oidBillMain+"'","");
	    if(listMatCosting.size() != 0){
		matCosting = (MatCosting) listMatCosting.get(0);
	    }else{
		matCosting = new MatCosting();
	    }
	    
	}catch(Exception ex){
	    billMainCostum = new BillMainCostum();
	    matCosting = new MatCosting();
	}
	
	
	if(printType.equals("printguest")){
	    transactionName = "";
	}else{
	    if (transactionType == 1){
		transactionName = "CREDIT";
		displayPaymentSystem = "CREDIT";
	    }else if(transactionType == 0){
		transactionName = "CASH";
	    }else if(transactionType == 2){
		transactionName = "COMPLIMENT";
	    }
	}
	
	
	
	
	BillMain billMain = new BillMain();
	TableRoom tableRoom = new TableRoom();
	Vector listItem = new Vector(1,1);
	CurrencyType currencyType = new CurrencyType();
	PaymentSystem paymentSystem = new PaymentSystem();
	int totalQty = 0;
	double totalPrice = 0;
	double service = 0;
	double tax =0;
	double total = 0;
        
        
	
	
	try{
	    billMain = PstBillMain.fetchExc(oidBillMain);
	    if(taxinc == PstBillMain.INC_CHANGEABLE || taxinc == PstBillMain.INC_NOT_CHANGEABLE){
		service = 0;
		tax = 0;
	    }else{
		service = billMain.getServiceValue();
		tax = billMain.getTaxValue();
	    }
	}catch(Exception ex){
	}
	
	try{
	    tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
	}catch(Exception ex){
	}
	
	try{
	    listItem = PstBillDetail.list(0, 0, ""+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'", "");
	}catch(Exception ex){
	}
	
	try{	    
	    paymentSystem = PstPaymentSystem.fetchExc(oidPayment);
	    displayPaymentSystem = paymentSystem.getPaymentSystem();
	}catch(Exception ex){
	}
	
	try{
	    currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
	}catch(Exception ex){
	}
	
	String nameInvoice = "";
	if(printType == "printpay"){
	    if(transactionType == 2){
		nameInvoice = "COMPLIMENT";
		paymentType = "Compliment";
		invoiceNumb = matCosting.getCostingCode();
	    }else{
		paymentType = "Payment";
		nameInvoice = transactionName+" INVOICE";
		invoiceNumb = billMain.getInvoiceNumber();
	    }
	    
	}else{
	    nameInvoice = "OPEN BILL";
	    paymentType = "Payment";
	    invoiceNumb = billMain.getInvoiceNumber();
	}
	
	if(billMain.getDiscPct() > 0){
	    displayDiscPct = "("+billMain.getDiscPct()+"%) ";
	}
	
	PrintTemplate printTemplate = new PrintTemplate();
        if (transactionType==2){
            //FOC
            
            displayPrint += printTemplate.PrintTemplateCompliment(billMainCostum, nameInvoice, 
            invoiceNumb, cashierName, billMain, tableRoom, printType, 
            paymentType, displayPaymentSystem, payAmount, oidPayment, ccName, 
            ccNumber, ccValid, ccBank, ccCharge, approot, oidMember);
        }else{
            displayPrint += printTemplate.PrintTemplate(billMainCostum, nameInvoice, 
            invoiceNumb, cashierName, billMain, tableRoom, printType, 
            paymentType, displayPaymentSystem, payAmount, oidPayment, ccName, 
            ccNumber, ccValid, ccBank, ccCharge, approot, oidMember);
        }
	

	return displayPrint;
    }
    
    public String drawPrintPaymentOnly (int transactionType, long oidBillMain, String cashierName, 
        int taxinc, long oidPayment, String ccName, String ccNumber,
        String ccBank, String ccValid, double ccCharge, 
        double payAmount, long cashierId, String printType, 
        String approot, String oidMember){
        
        String displayPrint="";
	String transactionName = "";
	String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
	String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
	String displayPaymentSystem = "";
	String displayDiscPct = "";
	String invoiceNumb = "";
	String paymentType = "";
	BillMainCostum billMainCostum;
	MatCosting matCosting;
	try{
	    billMainCostum = PstCustomBillMain.fetchByCashierID(cashierId);
	    Vector listMatCosting = PstMatCosting.list(0, 0, 
		    ""+PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER]+"='"+oidBillMain+"'","");
	    if(listMatCosting.size() != 0){
		matCosting = (MatCosting) listMatCosting.get(0);
	    }else{
		matCosting = new MatCosting();
	    }
	    
	}catch(Exception ex){
	    billMainCostum = new BillMainCostum();
	    matCosting = new MatCosting();
	}
	
	
	if(printType.equals("printguest")){
	    transactionName = "";
	}else{
	    if (transactionType == 1){
		transactionName = "CREDIT";
		displayPaymentSystem = "CREDIT";
	    }else if(transactionType == 0){
		transactionName = "CASH";
	    }else if(transactionType == 2){
		transactionName = "COMPLIMENT";
	    }
	}
	
	
	
	
	BillMain billMain = new BillMain();
	TableRoom tableRoom = new TableRoom();
	Vector listItem = new Vector(1,1);
	CurrencyType currencyType = new CurrencyType();
	PaymentSystem paymentSystem = new PaymentSystem();
	int totalQty = 0;
	double totalPrice = 0;
	double service = 0;
	double tax =0;
	double total = 0;
        
        
	
	
	try{
	    billMain = PstBillMain.fetchExc(oidBillMain);
	    if(taxinc == PstBillMain.INC_CHANGEABLE || taxinc == PstBillMain.INC_NOT_CHANGEABLE){
		service = 0;
		tax = 0;
	    }else{
		service = billMain.getServiceValue();
		tax = billMain.getTaxValue();
	    }
	}catch(Exception ex){
	}
	
	try{
	    tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
	}catch(Exception ex){
	}
	
	try{
	    listItem = PstBillDetail.list(0, 0, ""+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'", "");
	}catch(Exception ex){
	}
	
	try{	    
	    paymentSystem = PstPaymentSystem.fetchExc(oidPayment);
	    displayPaymentSystem = paymentSystem.getPaymentSystem();
	}catch(Exception ex){
	}
	
	try{
	    currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
	}catch(Exception ex){
	}
	
	String nameInvoice = "";
	if(printType == "printpay"){
	    if(transactionType == 2){
		nameInvoice = "COMPLIMENT";
		paymentType = "Compliment";
		invoiceNumb = matCosting.getCostingCode();
	    }else{
		paymentType = "Payment";
		nameInvoice = transactionName+" INVOICE";
		invoiceNumb = billMain.getInvoiceNumber();
	    }
	    
	}else{
	    nameInvoice = "OPEN BILL";
	    paymentType = "Payment";
	    invoiceNumb = billMain.getInvoiceNumber();
	}
	
	if(billMain.getDiscPct() > 0){
	    displayDiscPct = "("+billMain.getDiscPct()+"%) ";
	}
	
	PrintTemplate printTemplate = new PrintTemplate();
        if (transactionType==2){
            //FOC
            
            displayPrint += printTemplate.PrintTemplateComplimentPaymentOnly(billMainCostum, nameInvoice, 
            invoiceNumb, cashierName, billMain, tableRoom, printType, 
            paymentType, displayPaymentSystem, payAmount, oidPayment, ccName, 
            ccNumber, ccValid, ccBank, ccCharge, approot, oidMember);
        }else{
            displayPrint += printTemplate.PrintTemplatePaymentOnly(billMainCostum, nameInvoice, 
            invoiceNumb, cashierName, billMain, tableRoom, printType, 
            paymentType, displayPaymentSystem, payAmount, oidPayment, ccName, 
            ccNumber, ccValid, ccBank, ccCharge, approot, oidMember);
        }
	

	return displayPrint;
    }
    
    public String drawRePrint(long oidBillMain, String approot, String oidMember,int full){
	String displayPrint="";
	String transactionName = "";
	String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
	String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
	String displayPaymentSystem = "";
	String displayDiscPct = "";
	String invoiceNumb = "";
	String paymentType = "";
        String cashierName="";
	BillMainCostum billMainCostum;
	Location location;
	MatCosting matCosting;
	BillMain billMain;
	BillMain lastBillMain;
	Vector listBillMain = PstBillMain.list(0, 0, 
		""+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+"='"+oidBillMain+"'", 
		""+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+" DESC");
	if(listBillMain.size() != 0){
	    billMain = (BillMain) listBillMain.get(0);

	}else{
	    billMain = new BillMain();
	}
	AppUser entCashier = new AppUser();
        try {
            entCashier = PstAppUser.fetch(billMain.getAppUserId());
        } catch (Exception e) {
        }
	//cashierName = billMain.getLocationId();
        cashierName = entCashier.getFullName();
	try{
	    lastBillMain = PstBillMain.fetchExc(oidBillMain);
	    billMainCostum = PstCustomBillMain.fetchByCashierID(billMain.getCashCashierId());
	    location = PstLocation.fetchExc(billMain.getLocationId());
	    Vector listMatCosting = PstMatCosting.list(0, 0, 
		    ""+PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER]+"='"+oidBillMain+"'","");
	    if(listMatCosting.size() != 0){
		matCosting = (MatCosting) listMatCosting.get(0);
	    }else{
		matCosting = new MatCosting();
	    }
	    
	}catch(Exception ex){
	    billMainCostum = new BillMainCostum();
	    matCosting = new MatCosting();
	    location = new Location();
	    lastBillMain = new BillMain();
	}
	
	
	transactionName="REPRINT";

	TableRoom tableRoom = new TableRoom();
	Vector listItem = new Vector(1,1);
	CurrencyType currencyType = new CurrencyType();
	PaymentSystem paymentSystem = new PaymentSystem();
	int totalQty = 0;
	double totalPrice = 0;
	double service = 0;
	double tax =0;
	double total = 0;
	
	
	try{
	    //billMain = PstBillMain.fetchExc(oidBillMain);
	    if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
		service = 0;
		tax = 0;
	    }else{
		service = billMain.getServiceValue();
		tax = billMain.getTaxValue();
	    }
	}catch(Exception ex){
	}
	
	try{
	    tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
	}catch(Exception ex){
	}
	
	try{
	    listItem = PstBillDetail.list(0, 0, ""+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'", "");
	}catch(Exception ex){
	}
	
	Vector listCashPayment = PstCashPayment.list(0, 0, 
		""+PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'", 
		"");
	CashPayments cashPayments;
	if(listCashPayment.size() != 0){
	    cashPayments = (CashPayments) listCashPayment.get(0);
	}else{
	    cashPayments = new CashPayments();
	}
	try{	    
	    paymentSystem = PstPaymentSystem.fetchExc(cashPayments.getPaymentTypeLong());
	    displayPaymentSystem = paymentSystem.getPaymentSystem();
	}catch(Exception ex){
	}
	
	try{
	    currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
	}catch(Exception ex){
	}
	
	String nameInvoice = "";

	paymentType = "Reprint Bill";
	nameInvoice = transactionName+" Invoice";
	invoiceNumb = billMain.getInvoiceNumber();
	   
	
	if(billMain.getDiscPct() > 0){
	    displayDiscPct = "("+billMain.getDiscPct()+"%) ";
	}
	
	CustomCashCreditCard customCashCreditCard;
	Vector listCreditCard = PstCustomCashCreditCard.list(0, 0, 
		""+PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CASH_PAYMENT_ID]+"='"+cashPayments.getOID()+"'", 
		"");
	if(listCreditCard.size() != 0){
	    customCashCreditCard = (CustomCashCreditCard) listCreditCard.get(0);
	}else{
	    customCashCreditCard = new CustomCashCreditCard();
	}
	
	String ccName = "";
	String ccNumber = "";
	String ccBank = "";
	double ccCharge = 0;
	String ccValid = "";
	
	if(paymentSystem.isCardInfo() == true 
			&& paymentSystem.getPaymentType() == 0
			&& paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCheckBGInfo() == false){
		    
		    
		    
	    ccName = customCashCreditCard.getCcName();
	    ccNumber = customCashCreditCard.getCcNumber();
	    ccBank = customCashCreditCard.getDebitBankName();
	    ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
	    ccCharge = customCashCreditCard.getBankCost();
		    
		    
    /////BG PAYMENT
	}else if(paymentSystem.isCardInfo() == false 
		&& paymentSystem.getPaymentType() == 2
		&& paymentSystem.isBankInfoOut() == true 
		&& paymentSystem.isCheckBGInfo() == false){

	    ccName = customCashCreditCard.getChequeAccountName();
	    ccNumber = customCashCreditCard.getCcNumber();
	    ccBank = customCashCreditCard.getChequeBank();
	    ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
	    ccCharge = customCashCreditCard.getBankCost();
		


    /////CHECK PAYMENT
	}else if(paymentSystem.isCardInfo() == true 
		&& paymentSystem.getPaymentType() == 0
		&& paymentSystem.isBankInfoOut() == false 
		&& paymentSystem.isCheckBGInfo() == false){
	    
	    ccName = customCashCreditCard.getChequeAccountName();
	    ccNumber = customCashCreditCard.getCcNumber();
	    ccBank = customCashCreditCard.getChequeBank();
	    ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
	    ccCharge = customCashCreditCard.getBankCost();

    /////DEBIT PAYMENT
	}else if(paymentSystem.isCardInfo() == false 
		&& paymentSystem.getPaymentType() == 2
		&& paymentSystem.isBankInfoOut() == false 
		&& paymentSystem.isCheckBGInfo() == false){
	    
	    
	    ccName = customCashCreditCard.getChequeAccountName();
	    ccNumber = customCashCreditCard.getCcNumber();
	    ccBank = customCashCreditCard.getDebitBankName();
	    ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
	    ccCharge = customCashCreditCard.getBankCost();
	    
	}
	
	PrintTemplate printTemplate = new PrintTemplate();
	displayPrint += printTemplate.PrintTemplateReprint(billMainCostum, nameInvoice,
		invoiceNumb, String.valueOf(cashierName), billMain, tableRoom, "printpay", 
		paymentType, displayPaymentSystem, total, paymentSystem.getOID(), ccName,
		ccNumber, ccValid, ccBank, ccCharge, approot, oidMember,full);
	
	
	
	
	return displayPrint;
    }
    public String drawRePrintFOC(MatCosting matCosting, String approot,int full){
	
        String displayPrint="";
	String transactionName = "";
	String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
	String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
	String displayPaymentSystem = "";
	String displayDiscPct = "";
	String invoiceNumb = "";
	String paymentType = "";
        String nameInvoice ="";
        String cashierName="";
	Location location;
        OpeningCashCashier openingCashCashier = new OpeningCashCashier();
	
        transactionName = "REPRINT";
        paymentType = "FOC";
	nameInvoice = transactionName+" Invoice";
	invoiceNumb = matCosting.getCostingCode();
        
        String whereCashCashier = " cc."+PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID]+"="+matCosting.getCashCashierId()+"";
        Vector listCashCashier = PstCashCashier.listOpeningCashier(0, 1, whereCashCashier, "");
        if (listCashCashier.size()>0){
            openingCashCashier = (OpeningCashCashier)listCashCashier.get(0);
        }
        
        cashierName = openingCashCashier.getNameUser();
        PrintTemplate printTemplate = new PrintTemplate();
        displayPrint = printTemplate.PrintTemplateFOC(matCosting,approot,full,transactionName,paymentType,nameInvoice,invoiceNumb,cashierName);
	return displayPrint;
    }
    public String drawPrintNew(int transactionType, long oidBillMain, String cashierName, 
	    int taxinc, long oidPayment, String ccName, String ccNumber,
	    String ccBank, String ccValid, double ccCharge, 
	    double payAmount, long cashierId, String printType, 
	    String approot, String oidMember,double taxs,double services, double discPct){
	
	
	String displayPrint="";
	String transactionName = "";
	String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
	String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
	String displayPaymentSystem = "";
	String displayDiscPct = "";
	String invoiceNumb = "";
	String paymentType = "";
	BillMainCostum billMainCostum;
	MatCosting matCosting;
	try{
	    billMainCostum = PstCustomBillMain.fetchByCashierID(cashierId);
	    Vector listMatCosting = PstMatCosting.list(0, 0, 
		    ""+PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER]+"='"+oidBillMain+"'","");
	    if(listMatCosting.size() != 0){
		matCosting = (MatCosting) listMatCosting.get(0);
	    }else{
		matCosting = new MatCosting();
	    }
	    
	}catch(Exception ex){
	    billMainCostum = new BillMainCostum();
	    matCosting = new MatCosting();
	}
	
	
	if(printType.equals("printguest")){
	    transactionName = "";
	}else{
	    if (transactionType == 1){
		transactionName = "CREDIT";
		displayPaymentSystem = "CREDIT";
	    }else if(transactionType == 0){
		transactionName = "CASH";
	    }else if(transactionType == 2){
		transactionName = "COMPLIMENT";
	    }
	}
	
	
	
	
	BillMain billMain = new BillMain();
	TableRoom tableRoom = new TableRoom();
	Vector listItem = new Vector(1,1);
	CurrencyType currencyType = new CurrencyType();
	PaymentSystem paymentSystem = new PaymentSystem();
	int totalQty = 0;
	double totalPrice = 0;
	double service = 0;
	double tax =0;
	double total = 0;
        
        try{
	    billMain = PstBillMain.fetchExc(oidBillMain);
	    if(taxinc == PstBillMain.INC_CHANGEABLE || taxinc == PstBillMain.INC_NOT_CHANGEABLE){
		service = 0;
		tax = 0;
	    }else{
		service = services;
		tax = taxs;
	    }
	}catch(Exception ex){
	}
        
	try{
	    tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
	}catch(Exception ex){
	}
	
	try{
	    listItem = PstBillDetail.list(0, 0, ""+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'", "");
	}catch(Exception ex){
	}
	
	try{	    
	    paymentSystem = PstPaymentSystem.fetchExc(oidPayment);
	    displayPaymentSystem = paymentSystem.getPaymentSystem();
	}catch(Exception ex){
	}
	
	try{
	    currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
	}catch(Exception ex){
	}
	
	String nameInvoice = "";
	if(printType == "printpay"){
	    if(transactionType == 2){
		nameInvoice = "COMPLIMENT";
		paymentType = "Compliment";
		invoiceNumb = matCosting.getCostingCode();
	    }else{
		paymentType = "Payment";
		nameInvoice = transactionName+" INVOICE";
		invoiceNumb = billMain.getInvoiceNumber();
	    }
	    
	}else{
	    nameInvoice = "OPEN BILL";
	    paymentType = "Payment";
	    invoiceNumb = billMain.getInvoiceNumber();
	}
	
	if(billMain.getDiscPct() > 0){
	    displayDiscPct = "("+discPct+"%) ";
	}
	
	
	PrintTemplate printTemplate = new PrintTemplate();
	displayPrint += printTemplate.PrintTemplateNew(billMainCostum, nameInvoice, 
		invoiceNumb, cashierName, billMain, tableRoom, printType, 
		paymentType, displayPaymentSystem, payAmount, oidPayment, ccName, 
		ccNumber, ccValid, ccBank, ccCharge, approot, oidMember,tax,service,discPct);
	
	
	
	
	return displayPrint;
    }
    
    
    
    public String drawPrintReturn(long oidBillMain, String cashierName, String approot, String oidMember){
	
	
	String displayPrint="";
	String transactionName = "";
	String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
	String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
	String displayPaymentSystem = "";
	String displayDiscPct = "";
	String invoiceNumb = "";
	String paymentType = "";
	BillMainCostum billMainCostum;
	Location location;
	MatCosting matCosting;
	BillMain billMain;
	BillMain lastBillMain;
	Vector listBillMain = PstBillMain.list(0, 0, 
		""+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='"+oidBillMain+"'", 
		""+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+" DESC");
	if(listBillMain.size() != 0){
	    billMain = (BillMain) listBillMain.get(0);

	}else{
	    billMain = new BillMain();
	}
	
	
	try{
	    lastBillMain = PstBillMain.fetchExc(oidBillMain);
	    billMainCostum = PstCustomBillMain.fetchByCashierID(billMain.getCashCashierId());
	    location = PstLocation.fetchExc(billMain.getLocationId());
	    Vector listMatCosting = PstMatCosting.list(0, 0, 
		    ""+PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER]+"='"+oidBillMain+"'","");
	    if(listMatCosting.size() != 0){
		matCosting = (MatCosting) listMatCosting.get(0);
	    }else{
		matCosting = new MatCosting();
	    }
	    
	}catch(Exception ex){
	    billMainCostum = new BillMainCostum();
	    matCosting = new MatCosting();
	    location = new Location();
	    lastBillMain = new BillMain();
	}
	
	
	transactionName="RETURN";
	
	
	
	
	TableRoom tableRoom = new TableRoom();
	Vector listItem = new Vector(1,1);
	CurrencyType currencyType = new CurrencyType();
	PaymentSystem paymentSystem = new PaymentSystem();
	int totalQty = 0;
	double totalPrice = 0;
	double service = 0;
	double tax =0;
	double total = 0;
	
	
	try{
	    //billMain = PstBillMain.fetchExc(oidBillMain);
	    if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
		service = 0;
		tax = 0;
	    }else{
		service = billMain.getServiceValue();
		tax = billMain.getTaxValue();
	    }
	}catch(Exception ex){
	}
	
	try{
	    tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
	}catch(Exception ex){
	}
	
	try{
	    listItem = PstBillDetail.list(0, 0, ""+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'", "");
	}catch(Exception ex){
	}
	
	Vector listCashPayment = PstCashPayment.list(0, 0, 
		""+PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'", 
		"");
	CashPayments cashPayments;
	if(listCashPayment.size() != 0){
	    cashPayments = (CashPayments) listCashPayment.get(0);
	}else{
	    cashPayments = new CashPayments();
	}
	try{	    
	    paymentSystem = PstPaymentSystem.fetchExc(cashPayments.getPaymentTypeLong());
	    displayPaymentSystem = paymentSystem.getPaymentSystem();
	}catch(Exception ex){
	}
	
	try{
	    currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
	}catch(Exception ex){
	}
	
	String nameInvoice = "";

	paymentType = "Return";
	nameInvoice = transactionName+" Invoice";
	invoiceNumb = billMain.getInvoiceNumber();
	   
	
	if(billMain.getDiscPct() > 0){
	    displayDiscPct = "("+billMain.getDiscPct()+"%) ";
	}
	
	CustomCashCreditCard customCashCreditCard;
	Vector listCreditCard = PstCustomCashCreditCard.list(0, 0, 
		""+PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CASH_PAYMENT_ID]+"='"+cashPayments.getOID()+"'", 
		"");
	if(listCreditCard.size() != 0){
	    customCashCreditCard = (CustomCashCreditCard) listCreditCard.get(0);
	}else{
	    customCashCreditCard = new CustomCashCreditCard();
	}
	
	String ccName = "";
	String ccNumber = "";
	String ccBank = "";
	double ccCharge = 0;
	String ccValid = "";
	
	if(paymentSystem.isCardInfo() == true 
			&& paymentSystem.getPaymentType() == 0
			&& paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCheckBGInfo() == false){
		    
		    
		    
	    ccName = customCashCreditCard.getCcName();
	    ccNumber = customCashCreditCard.getCcNumber();
	    ccBank = customCashCreditCard.getDebitBankName();
	    ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
	    ccCharge = customCashCreditCard.getBankCost();
		    
		    
    /////BG PAYMENT
	}else if(paymentSystem.isCardInfo() == false 
		&& paymentSystem.getPaymentType() == 2
		&& paymentSystem.isBankInfoOut() == true 
		&& paymentSystem.isCheckBGInfo() == false){

	    ccName = customCashCreditCard.getChequeAccountName();
	    ccNumber = customCashCreditCard.getCcNumber();
	    ccBank = customCashCreditCard.getChequeBank();
	    ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
	    ccCharge = customCashCreditCard.getBankCost();
		


    /////CHECK PAYMENT
	}else if(paymentSystem.isCardInfo() == true 
		&& paymentSystem.getPaymentType() == 0
		&& paymentSystem.isBankInfoOut() == false 
		&& paymentSystem.isCheckBGInfo() == false){
	    
	    ccName = customCashCreditCard.getChequeAccountName();
	    ccNumber = customCashCreditCard.getCcNumber();
	    ccBank = customCashCreditCard.getChequeBank();
	    ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
	    ccCharge = customCashCreditCard.getBankCost();

    /////DEBIT PAYMENT
	}else if(paymentSystem.isCardInfo() == false 
		&& paymentSystem.getPaymentType() == 2
		&& paymentSystem.isBankInfoOut() == false 
		&& paymentSystem.isCheckBGInfo() == false){
	    
	    
	    ccName = customCashCreditCard.getChequeAccountName();
	    ccNumber = customCashCreditCard.getCcNumber();
	    ccBank = customCashCreditCard.getDebitBankName();
	    ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
	    ccCharge = customCashCreditCard.getBankCost();
	    
	}
	
	PrintTemplate printTemplate = new PrintTemplate();
	displayPrint += printTemplate.PrintTemplate(billMainCostum, nameInvoice,
		invoiceNumb, cashierName, billMain, tableRoom, "printreturn", 
		paymentType, displayPaymentSystem, total, paymentSystem.getOID(), ccName,
		ccNumber, ccValid, ccBank, ccCharge, approot, oidMember);
	
	
	
	
	return displayPrint;
    }
    
    public String drawPrintClosing(Vector listCashier, String userName){
	
	
	String displayPrint="";
	String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
	String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");

	
	CashCashier cashCashier;
	Location location;
	
	if(listCashier.size() != 0){
	    cashCashier = (CashCashier) listCashier.get(0);
	    try{
		cashCashier = PstCashCashier.fetchExc(cashCashier.getOID());
	    }catch(Exception ex){
		
	    }
	    location = (Location) listCashier.get(2);
	    
	}else{
	    cashCashier = new CashCashier();
	    location = new Location();
	}
	
	
	
	BillMainCostum billMainCostum;
	try{
	    billMainCostum = PstCustomBillMain.fetchByCashierID(cashCashier.getOID());
	}catch(Exception ex){
	    billMainCostum = new BillMainCostum();
	}
	
	PrintTemplate printTemplate = new PrintTemplate();
	displayPrint+= printTemplate.PrintTemplateClosing(userName, cashCashier, 
		billMainCostum, location);

	return displayPrint;
    }
    
    public String drawPrintClosing2(Vector listCashier, String userName,int type){
	
	
	String displayPrint="";
	String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
	String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");

	
	CashCashier cashCashier;
	Location location;
	
	if(listCashier.size() != 0){
	    cashCashier = (CashCashier) listCashier.get(0);
	    try{
		cashCashier = PstCashCashier.fetchExc(cashCashier.getOID());
	    }catch(Exception ex){
		
	    }
	    location = (Location) listCashier.get(2);
	    
	}else{
	    cashCashier = new CashCashier();
	    location = new Location();
	}
	
	
	
	BillMainCostum billMainCostum;
	try{
	    billMainCostum = PstCustomBillMain.fetchByCashierID(cashCashier.getOID());
	}catch(Exception ex){
	    billMainCostum = new BillMainCostum();
	}
	
	PrintTemplate printTemplate = new PrintTemplate();
	displayPrint+= printTemplate.PrintTemplateClosing2(userName, cashCashier, 
		billMainCostum, location,type);
	

		
	
	
	return displayPrint;
    }
    
    public String drawPrintClosing3(Vector listCashier, String userName,int type){
	
	
	String displayPrint="";
	String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
	String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");

	
	CashCashier cashCashier;
	Location location;
	
	if(listCashier.size() != 0){
	    cashCashier = (CashCashier) listCashier.get(0);
	    try{
		cashCashier = PstCashCashier.fetchExc(cashCashier.getOID());
	    }catch(Exception ex){
		
	    }
	    location = (Location) listCashier.get(2);
	    
	}else{
	    cashCashier = new CashCashier();
	    location = new Location();
	}
	
	
	
	BillMainCostum billMainCostum;
	try{
	    billMainCostum = PstCustomBillMain.fetchByCashierID(cashCashier.getOID());
	}catch(Exception ex){
	    billMainCostum = new BillMainCostum();
	}
	
	PrintTemplate printTemplate = new PrintTemplate();
	displayPrint+= printTemplate.PrintTemplateClosing3(userName, cashCashier, 
		billMainCostum, location,type);
	

		
	
	
	return displayPrint;
    }
    
    public String drawListItem(int iCommand, Vector objectClass,int start) {
        //2
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("No", "20%");
        ctrlist.addHeader("SKU", "20%");
        ctrlist.addHeader("Barcode", "20%");
        ctrlist.addHeader("Name", "20%");
	ctrlist.addHeader("Price","20%");

        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit

        ctrlist.reset();

        int index = -1;

        Vector rowx = new Vector(1, 1);
	double grandTotal = 0;
	String addInner = "";
	int specialItem = 0;
	String fieldSpecial = "";
	int counter = start+1;
        String addClass="";
        String addClassSpecial ="";
        String oidSpesialRequestFood=PstSystemProperty.getValueByName("SPESIAL_REQUEST_FOOD");
	String oidSpesialRequestBeverage=PstSystemProperty.getValueByName("SPESIAL_REQUEST_BEVERAGE");
        
        for (int i = 0; i < objectClass.size(); i++) {
            //long famOid = 0;
	    Vector listItem = (Vector) objectClass.get(i);
	    
	    if(listItem.size() != 0){
		
		Material material = (Material) listItem.get(0);
		PriceTypeMapping priceTypeMapping = (PriceTypeMapping)listItem.get(1);
		String[] splits = material.getName().split(";");
		rowx = new Vector(1, 1);
                if (i==0){
                    addClass = "firstFocus";
                    
                }else{
                    addClass="";
                }
                
                if (oidSpesialRequestFood.equals(""+material.getMaterialId()+"")||oidSpesialRequestBeverage.equals(""+material.getMaterialId()+"")){
                    addClassSpecial="specialOrder";
                }
                
		rowx.add("<input type='hidden' class='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME]+"' value='"+splits[0]+"'>"
			+ "<input type='hidden' class='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID]+"' value='"+material.getMaterialId()+"'>"
			+ "<input type='hidden' class='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE]+"' value='"+priceTypeMapping.getPrice()+"'>"
			+ ""+counter+"");
                rowx.add(""+material.getSku()+"");
                rowx.add(""+material.getBarcode()+"");
                rowx.add("<input readonly='readonly' style='cursor:pointer;' class='textual form-control FRM_FIELD_ITEM_NAME "+addClassSpecial+" "+addClass+"' data-oid='"+material.getMaterialId()+"' data-name='"+splits[0]+"'  data-price='"+priceTypeMapping.getPrice()+"' type='text' value='"+splits[0]+"'>");
		//rowx.add("<a href='javascript:' class='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME]+"' data-oid='"+material.getMaterialId()+"' data-name='"+splits[0]+"' data-price='"+priceTypeMapping.getPrice()+"'>"+splits[0]+"</a>");
		rowx.add(""+Formater.formatNumber(priceTypeMapping.getPrice(),"#,###")+"");
		lstData.add(rowx);
		counter++;
	    }
	    
            
            
        }
        return ctrlist.drawBootstrapStripted();
    }
    
    public String drawCustomerCreditTool(){
        String html="";
        
        html =  "<div class='input-group'>" 
                    + "<input type='text' id='searchCtCustomer' class='form-control' placeholder='Search...'/>" 
                    + "<div class='input-group-addon btn btn-primary' id='btnSearchCtCustomer'>" 
                        + "<i class='fa fa-search'></i>" 
                    + "</div>" 
                    + "<div class='input-group-addon btn btn-primary' id='btnAddCtCustomer'>" 
                        + "<i class='fa fa-plus'></i>" 
                    + "</div>"
             +  "</div>";
        
        return html;
    }
    
    public String drawCustomerCreditTemporary(Vector objectClass){
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("Name", "20%");
        ctrlist.addHeader("Mobile Phone", "20%");
	ctrlist.addHeader("Company","20%");
        
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit
        ctrlist.reset();

        int index = -1;

        Vector rowx = new Vector(1, 1);
        Vector listItem = new Vector(1,1);
	double grandTotal = 0;
	String addInner = "";
	int specialItem = 0;
	String fieldSpecial = "";
	int counter = 1;
        
        for (int i = 0; i < objectClass.size(); i++) {
            //long famOid = 0;
            ContactList contact = (ContactList)objectClass.get(i);
            
            rowx = new Vector(1, 1);

            rowx.add("<a class='selectCtCustomer' style='cursor:pointer' data-oId='"+contact.getOID()+"' data-personName='"+contact.getPersonName()+"' data-telp ='"+contact.getTelpMobile()+"' data-company='"+contact.getCompName()+"'>"+contact.getPersonName()+"</a>");
            rowx.add("<a class='selectCtCustomer' style='cursor:pointer' data-oId='"+contact.getOID()+"' data-personName='"+contact.getPersonName()+"' data-telp ='"+contact.getTelpMobile()+"' data-company='"+contact.getCompName()+"'>"+contact.getTelpMobile()+"</a>");
            rowx.add("<a class='selectCtCustomer' style='cursor:pointer' data-oId='"+contact.getOID()+"' data-personName='"+contact.getPersonName()+"' data-telp ='"+contact.getTelpMobile()+"' data-company='"+contact.getCompName()+"'>"+contact.getCompName()+"</a>");
            lstData.add(rowx);
            counter++;
	 
            
        }
        return ctrlist.drawBootstrapStripted();
    }
    
    public Vector parentMenu(long oid, Vector data, long location, String priceType, String design, int type){
        if(oid != 0){
            //CEK is sub category
            int count = PstCategory.getCount(PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='"+oid+"'");
            String whereClause = PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID]+"='"+oid+"'";
            
            Vector listCategory = PstCategory.list(0, 0, whereClause, "");
            if(listCategory.size() > 0){
                for(int i = 0; i < listCategory.size(); i++){
                    Category category = (Category) listCategory.get(i);
                    String html = "<button data-menu='1' data-type='"+category.getCategoryId()+"' data-oidcategory='0' data-design='"+design+"' data-pricetype='"+priceType+"' data-location='"+location+"' data-parent='"+category.getOID()+"' data-for='loadMenuDinamis' type='button' class='btn btn-default mainCategoryOutlet '><i class='fa fa-gg-circle'></i> "+category.getName()+"</button>";
                    data.add(html);
                    data = parentMenu(category.getCatParentId(), data, location, priceType, design, type);
                }
            }
        }
        
        return data;
    }
    
    public void updateDisc(double disc, HttpServletRequest request){
        long oidBillMain = FRMQueryString.requestLong(request, "FRM_FIELD_CASH_BILL_MAIN_ID");
        if(oidBillMain != 0){
            try{
                BillMain billMain = PstBillMain.fetchExc(oidBillMain);
                billMain.setDiscPct(disc);
                long oidUpdate = PstBillMain.updateExc(billMain);
            }catch(Exception ex){
                
            }
        }
    }
    
    public String getShortMessage(HttpServletRequest request, Vector listCashier){
        String returnData = "CLS-";
        CashCashier cashCashier = new CashCashier();
        double cl = 0;
        if(listCashier.size() > 0){
            Vector getData = (Vector) listCashier.get(0);
            cashCashier = (CashCashier) getData.get(0);
        }
        
        if(cashCashier.getOID() != 0){
            try{
                CashCashier cashCashier1 = PstCashCashier.fetchExc(cashCashier.getOID());
                CashMaster cashMaster = PstCashMaster.fetchExc(cashCashier1.getCashMasterId());
                Location location = PstLocation.fetchExc(cashMaster.getLocationId());
                returnData+=location.getCode()+" "+Formater.formatDate(new Date(),"yyyyMMdd")+":";
            }catch(Exception ex){
                returnData +="";
            }
        }
        try{
            double creditReturnAmount  = PstCustomBillMain.getSummarySalesCreditReturn(cashCashier.getOID());
            double creditAmount  = PstCustomBillMain.getSummarySalesCredit(cashCashier.getOID());
            cl=creditAmount-creditReturnAmount;
        }catch(Exception ex){
        }
        double cashReturnAmount = PstCustomBillMain.getSummaryCashReturn(cashCashier.getOID());
        double totalReturnAmount =  cashReturnAmount;//+creditReturnAmount;
        
        Vector listBalance = PstBalance.list(0, 0, 
		  ""+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		  + "AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='1' "
                  + "AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"='1'"
		  + "" , "");
        Vector listPaymentSystem = PstPaymentSystem.listAll();
        for (int j = 0; j<listBalance.size();j++){
            //PAYMENT DETAIL DATA
            double cashPayment = 0;
            double ccPayment = 0;
            double debitPayment = 0;
            double bgPayment = 0;
            double checkPayment = 0;
            Balance entBalance = new Balance();
            entBalance = (Balance)listBalance.get(j);
            for(int i=0; i<listPaymentSystem.size();i++){
		PaymentSystem paymentSystem = (PaymentSystem) listPaymentSystem.get(i);
		double payment = PstCashPayment.getSumListDetailBayar2("CBM."+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
			+ "AND CP."+PstCashPayment.fieldNames[PstCashPayment.FLD_PAY_TYPE]+"='"+paymentSystem.getOID()+"' "
                        + "AND CP."+PstCashPayment.fieldNames[PstCashPayment.FLD_CURRENCY_ID]+"='1'"
			+ "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
			+ "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
			+ "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
			+ "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' ");
		if(paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCardInfo() == false 
			&& paymentSystem.isCheckBGInfo() == false 
			&& paymentSystem.getPaymentType() == 1){
                    double pay=0;
                    double totpay=0;
                    pay=payment;
                    double change = PstCashReturn.getReturnSummary2(cashCashier.getOID(),entBalance.getCurrencyOid());
                    totpay = pay-change-totalReturnAmount;
		    cashPayment += totpay;
                    //returnData+=" \n CASH:"+Formater.formatNumber(pay,"#,###");
		}else if(paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCardInfo() == true 
			&& paymentSystem.isCheckBGInfo() == false 
			&& paymentSystem.getPaymentType() == 0){
                    double pay=0;
                    pay=payment;
		    ccPayment += payment;
                    //returnData+=" \n CC:"+Formater.formatNumber(pay,"#,###");
		}else if(paymentSystem.isBankInfoOut() == true 
			&& paymentSystem.isCardInfo() == false 
			&& paymentSystem.isCheckBGInfo() == false 
			&& paymentSystem.getPaymentType() == 2){
                     double pay=0;
                    pay=payment;
		    bgPayment += payment;
                    //returnData+=" \n BG:"+Formater.formatNumber(pay,"#,###");
		}else if(paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCardInfo() == false 
			&& paymentSystem.isCheckBGInfo() == true 
			&& paymentSystem.getPaymentType() == 0){
		    checkPayment += payment;
                    double pay=0;
                    pay=payment;
                    //returnData+=" \n CHEQUE:"+Formater.formatNumber(pay,"#,###");
		}else if(paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCardInfo() == false 
			&& paymentSystem.isCheckBGInfo() == false 
			&& paymentSystem.getPaymentType() == 2){
		    debitPayment += payment;
                     double pay=0;
                    pay=payment;
                    //returnData+=" \n DEBITCARD:"+Formater.formatNumber(pay,"#,###");
		}
	    }   
            
            
            returnData+="|CASH:"+Formater.formatNumber(cashPayment,"#,###");
            returnData+="|CC:"+Formater.formatNumber(ccPayment,"#,###");
            returnData+="|BG:"+Formater.formatNumber(bgPayment,"#,###");
            returnData+="|CEQ:"+Formater.formatNumber(checkPayment,"#,###");
            returnData+="|DC:"+Formater.formatNumber(debitPayment,"#,###");
            double subTotal = cashPayment+ccPayment+debitPayment+bgPayment+checkPayment;
            returnData+="|TOT. PAY:"+Formater.formatNumber(subTotal,"#,###");
            returnData+="|CL : "+Formater.formatNumber(cl,"#,###");
            
            
        }
        
        return returnData;
    }
    
    public void updateDiscDetail(long billId, double discPct, double discNomintal){
        BillMain billMain = new BillMain();
        Location location = new Location();
        if(billId != 0){
            try{
                billMain = PstBillMain.fetchExc(billId);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        if(billMain.getLocationId() != 0){
            try{
                location = PstLocation.fetchExc(billMain.getLocationId());
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        Vector listBillDetail = PstBillDetail.list(0, 0, ""+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billId+"'", "");
        double totalQty = PstBillDetail.getQty(billId);
        
        if(discPct == 0){
            discNomintal = discNomintal/totalQty;
        }
        if(listBillDetail.size() > 0){
            for(int i = 0; i < listBillDetail.size(); i++){
                Billdetail billdetail = (Billdetail) listBillDetail.get(i);
                double totalPrice = billdetail.getTotalPrice();
                double totalTax = 0;
                double totalService = 0;
                if(discPct > 0){
                    discNomintal = billdetail.getItemPrice()*(discPct/100);
                }
                
                if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
                    double dpp = (billdetail.getItemPrice()-billdetail.getDisc()-discNomintal)/((100+location.getTaxPersen()+location.getServicePersen())/100);
                    totalPrice = (dpp+discNomintal)*billdetail.getQty();
                    totalTax = ((billMain.getTaxPercentage()/100)*dpp)*billdetail.getQty();
                    totalService = ((billMain.getServicePct()/100)*dpp)*billdetail.getQty();
                }else{
                    double dpp = (billdetail.getItemPrice()-billdetail.getDisc());//-discNomintal;
                    totalPrice = dpp*billdetail.getQty();
                    totalTax = ((billMain.getTaxPercentage()/100)*dpp)*billdetail.getQty();
                    totalService = ((billMain.getServicePct()/100)*dpp)*billdetail.getQty();
                }
                
                billdetail.setTotalPrice(totalPrice);
                billdetail.setTotalTax(totalTax);
                billdetail.setTotalSvc(totalService);
                try{
                    long oid = PstBillDetail.updateExc(billdetail);
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void autoCheckOut(HttpServletRequest request, long billMainId){
        long reservationId = FRMQueryString.requestLong(request, "FRM_FIELD_RESERVATION_ID");
        if(reservationId != 0){
            //CHECK IS PACKAGE
            int isPackage = PstCustomePackBilling.getCount(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_RESERVATION_ID]+"='"+reservationId+"'");
            int noSchedule = PstCustomePackageSchedule.getCountJoin("billing."+PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_RESERVATION_ID]+"='"+reservationId+"' "
                    + "AND (schedule."+PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_STATUS]+"='0')");
            if(noSchedule == 0 || noSchedule == 1){
                noSchedule = PstCustomePackageSchedule.getCountJoin("billing."+PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_RESERVATION_ID]+"='"+reservationId+"' "
                    + "AND schedule."+PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_STATUS]+"='3'");
                if(noSchedule == 1){
                    noSchedule = 0;
                }
            }
            
            //CHECKOUT SCHEDULE
            Vector listBillDetail = PstBillDetail.list(0, 0, PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMainId+"'", "");
            if(listBillDetail.size() > 0){
                for(int i  = 0; i < listBillDetail.size(); i++){
                    Billdetail billdetail = (Billdetail) listBillDetail.get(i);
                    if(billdetail.getCostumeScheduleId() != 0){
                        try{
                            CustomePackageSchedule customePackageSchedule = PstCustomePackageSchedule.fetchExc(billdetail.getCostumeScheduleId());
                            customePackageSchedule.setStatus(4);
                            long oid = PstCustomePackageSchedule.updateExc(customePackageSchedule);
                        }catch(Exception ex){
                            
                        }
                    }
                }
            }
            
            if(isPackage > 0 && noSchedule == 0){
                //FETCH RESERVATION
                Reservation reservation = new Reservation();
                try{
                    reservation = PstReservation.fetchExc(reservationId);
                }catch(Exception ex){
                    
                };
                
                if(reservation.getHotelRoomId() == 0){
                    //CHECK OUT RESERVATION
                    reservation.setReservationStatus(4);
                    try{
                        long oid = PstReservation.updateExc(reservation);
                    }catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
    
    public synchronized String generateInvoiceNumber(String cashierNumber){
        String invcNumber = "";
        Vector listLastInvcNumber = PstBillMain.list(0, 1, ""+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" LIKE '%"+cashierNumber+"%' "
                + "AND "+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" NOT LIKE '%C'", PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+" DESC");
        if(listLastInvcNumber.size() > 0){
            BillMain billMain = (BillMain) listLastInvcNumber.get(0);
            String[] number = billMain.getInvoiceNo().split("\\.");
            int newNumber = Integer.parseInt(number[2])+1;
            String lastNumber = "000"+newNumber;
            lastNumber = lastNumber.substring(lastNumber.length()-3);
            invcNumber = cashierNumber+"."+lastNumber;
        }else{
            invcNumber = cashierNumber+".001";
        }
        return invcNumber;
    }
    
    public String templateHandler(BillMain billMain){
        String getActiveTemplate = PstSystemProperty.getValueByName("PRINT_ACTIVE_TEMPLATE");
        String printPaymentType = "Cash";
        BillMainCostum headerInfo = new BillMainCostum();
                    
        int activeTemplate = 0;
        try{
            activeTemplate = Integer.parseInt(getActiveTemplate);
        }catch(Exception ex){
            activeTemplate = 0;
        }

        if(billMain.getTransactionStatus() == 0 && billMain.getTransctionType() == 0 && billMain.getDocType() == 0){
            printPaymentType = "Cash";
        }else if(billMain.getTransactionStatus() == 1 && billMain.getTransctionType() == 1 && billMain.getDocType() == 0){
            printPaymentType = "Credit";
        }

        headerInfo = PstCustomBillMain.fetchByCashierID(billMain.getCashCashierId());
        headerInfo.setInvoiceNo(billMain.getInvoiceNo());
        
        Vector billDetail = PstBillDetail.listJoinUnit(0, 0, PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'", "");
        double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
        Location location = new Location();
        if(billMain.getLocationId() != 0){
            try{
                location = PstLocation.fetchExc(billMain.getLocationId());
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        
        if(location.getTaxSvcDefault() == PstLocation.TSD_INCLUDE_CHANGABLE || location.getTaxSvcDefault() == PstLocation.TSD_INCLUDE_NOTCHANGABLE){
            billMain.setTaxValue(0);
        }else{
            double tax = 0;
            if(billMain.getTaxPercentage() == 0){
                tax = totalPrice*(location.getTaxPersen()/100);
            }else{
                tax = totalPrice*(billMain.getTaxPercentage()/100);
            }
            double svc = 0;
            if(billMain.getServicePct() == 0){
                svc = totalPrice*(location.getServicePersen()/100);
            }else{
                svc = totalPrice*(billMain.getServicePct()/100);
            }
            billMain.setTaxValue(svc+tax);
        }
        
        //CUSTOMER
        ContactList costumer = new ContactList();
        try{
            costumer = PstContactList.fetchExc(billMain.getCustomerId());
        }catch(Exception ex){
            costumer = new ContactList();
        }
        
        
        double totalQty = 0;
        try{
            totalQty = PstBillDetail.getQty(billMain.getOID());
        }catch(Exception ex){
            totalQty = 0;
        }
        
        //PAYMENT
        CashPayments payment = new CashPayments();
        try{
            Vector listPayment = PstCashPayment.listDetailBayar(0, 0, "CBM."+PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'");
            Vector data = (Vector) listPayment.get(0);
            payment = (CashPayments) data.get(0);
        }catch(Exception ex){
            
        }
        
        //DUMMY DATA START
        PrintDynamicTemplate printDynamicTemplate = new PrintDynamicTemplate();
        EntPrintData entPrintData = new EntPrintData();
        entPrintData.setCompLogo("../imgcompany/litama.jpg");
        entPrintData.setCompAddress(headerInfo.getLocationAddress());
        entPrintData.setCompPhone(headerInfo.getLocationTelp());
        entPrintData.setCompFax(headerInfo.getLocationFax());
        entPrintData.setCompEmail(headerInfo.getLocationEmail());
        entPrintData.setCompName(headerInfo.getCompanyName());
        entPrintData.setActiveTemplate(activeTemplate);
        Vector dataList = new Vector();
        if(billDetail != null){
            for(int i = 0; i < billDetail.size(); i++){
                EntDataList entDataList = new EntDataList();
                Billdetail entBilldetail = (Billdetail) billDetail.get(i);
                
                double disc = 0;
                try{
                    disc = entBilldetail.getDisc()+(billMain.getDiscount()/totalQty);
                }catch(Exception ex){
                    disc = 0;
                }
                double itemPrice = getRealPrice(location, entBilldetail.getItemPrice(), disc);
                
                entDataList.setItemNonte(entBilldetail.getNote());
                entDataList.setItemName(entBilldetail.getItemName());
                entDataList.setItemWeight(0);
                entDataList.setItemKadar(0);
                entDataList.setItemQty(entBilldetail.getQty());
                entDataList.setItemPrice(entBilldetail.getItemPrice());
                entDataList.setItemDisc(entBilldetail.getDisc());
                entDataList.setItemId(""+entBilldetail.getMaterialId());
                entDataList.setItemQtyType(entBilldetail.getUnitName());
                dataList.add(entDataList);
            }
        }
        for(int i = 0; i < 5; i++){
            
        }
        entPrintData.setDataList(dataList);
        entPrintData.setInvNo(headerInfo.getInvoiceNo());
        entPrintData.setPaymentType(printPaymentType);
        entPrintData.setDataTaxTotal(billMain.getTaxValue());
        entPrintData.setIsTaxInclude(location.getTaxSvcDefault());
        entPrintData.setReceivedBy(billMain.getSalesCode());
        entPrintData.setPaid(payment.getAmount());
        entPrintData.setCostumer(costumer.getPersonName());
        entPrintData.setDataDiscTotal(billMain.getDiscount());
        entPrintData.setDiscPct(billMain.getDiscPct());
        //END OF DUMMY DATA
        return printDynamicTemplate.showData(entPrintData);
    }
    
    private double getRealPrice(Location location, double price, double discount){
        double result = 0;
        price = price - discount;
        if(location.getTaxSvcDefault() == PstLocation.TSD_INCLUDE_CHANGABLE || location.getTaxSvcDefault() == PstLocation.TSD_INCLUDE_NOTCHANGABLE){
            double dpp = 100/(100+location.getTaxPersen()+location.getServicePersen());
            result = price*dpp;
        }else{
            result = price;
        }
        return result;
    }
    
    
    public synchronized String generateLogHistory(long oidBill){
        String message="";
            LogSysHistory logSysHistory = new LogSysHistory();
            PstLogSysHistory pstLogSysHistory = new PstLogSysHistory();
            //appUser = (AppUser)listAppUser.get(0);
            //set objek log sys history
            logSysHistory.setLogUserId(0);
            logSysHistory.setLogDocumentId(oidBill);
            logSysHistory.setLogLoginName("");
            logSysHistory.setLogDocumentNumber("");
            logSysHistory.setLogDocumentType("Bill");
            logSysHistory.setLogUserAction("Print Out");
            logSysHistory.setLogOpenUrl("");
            logSysHistory.setLogUpdateDate(new Date());
            logSysHistory.setLogApplication("Cashier");
            logSysHistory.setLogDetail("Print out bill");
            try {
                long err = pstLogSysHistory.insertExc(logSysHistory);
            } catch (Exception e) {
            }
        
        return message;
    }
    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>
}
