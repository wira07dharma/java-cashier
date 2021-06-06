/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.ajax.cashier;

import com.dimata.cashierweb.ajax.templateprint.*;
import com.dimata.cashierweb.entity.admin.*;
import com.dimata.cashierweb.entity.cashier.printtemplate.PrintTemplate;
import com.dimata.cashierweb.entity.cashier.transaction.*;
import com.dimata.cashierweb.entity.masterdata.*;
import com.dimata.cashierweb.form.admin.FrmAppUser;
import com.dimata.cashierweb.form.cashier.*;
import com.dimata.cashierweb.session.cashier.SessUserCashierSession;
import com.dimata.common.db.DBException;
import com.dimata.common.db.DBHandler;
import com.dimata.common.db.DBResultSet;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.custom.DataCustom;
import com.dimata.common.entity.custom.PstDataCustom;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.logger.LogSysHistory;
import com.dimata.common.entity.logger.PstLogSysHistory;
import com.dimata.common.entity.payment.CurrencyType;
import com.dimata.common.entity.payment.PaymentSystem;
import com.dimata.common.entity.payment.PstCurrencyType;
import com.dimata.common.entity.payment.PstPaymentSystem;
import com.dimata.common.entity.system.PstSystemProperty;
import com.dimata.common.form.contact.FrmContactList;
import com.dimata.common.session.email.SessEmail;
import com.dimata.gui.jsp.ControlCombo;
import com.dimata.gui.jsp.ControlList;
import com.dimata.hanoman.entity.masterdata.*;
import com.dimata.hanoman.form.masterdata.FrmContactClass;
import com.dimata.harisma.entity.employee.Employee;
import com.dimata.harisma.entity.employee.PstEmployee;
import com.dimata.harisma.entity.masterdata.PstMarital;
import com.dimata.pos.entity.balance.*;
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
import com.dimata.pos.form.payment.CtrlCashCreditPaymentMain;
import com.dimata.pos.form.payment.FrmCashCreditCard;
import com.dimata.pos.form.payment.FrmCashPayment;
import com.dimata.posbo.entity.masterdata.Color;
import com.dimata.posbo.entity.masterdata.Company;
import com.dimata.posbo.entity.masterdata.MaterialStock;
import com.dimata.posbo.entity.masterdata.MaterialTypeMapping;
import com.dimata.posbo.entity.masterdata.PersonalDiscount;
import com.dimata.posbo.entity.masterdata.PstColor;
import com.dimata.posbo.entity.masterdata.PstCompany;
import com.dimata.posbo.entity.masterdata.PstMaterialMappingType;
import com.dimata.posbo.entity.masterdata.PstMaterialStock;
import com.dimata.posbo.entity.masterdata.PstPersonalDiscount;
import com.dimata.posbo.entity.masterdata.PstVoucher;
import com.dimata.posbo.entity.masterdata.Unit;
import com.dimata.posbo.entity.masterdata.Voucher;
import com.dimata.posbo.entity.warehouse.*;
import com.dimata.posbo.session.warehouse.SessMatCostingStokFisik;
import com.dimata.posbo.session.warehouse.SessMatDispatch;
import com.dimata.posbo.session.warehouse.SessMatReceive;
import com.dimata.qdep.entity.I_DocStatus;
import com.dimata.qdep.entity.I_DocType;
import com.dimata.qdep.entity.I_PstDocType;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.sedana.entity.Anggota;
import com.dimata.sedana.entity.Angsuran;
import com.dimata.sedana.entity.JadwalAngsuran;
import com.dimata.sedana.entity.Pinjaman;
import com.dimata.sedana.entity.PstAnggota;
import com.dimata.sedana.entity.PstAngsuran;
import com.dimata.sedana.entity.PstJadwalAngsuran;
import com.dimata.sedana.entity.PstPinjaman;
import com.dimata.sedana.entity.PstTransaksi;
import com.dimata.sedana.entity.Transaksi;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    long reservationId = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_RESERVATION_ID]);
    long cashCashierId = FRMQueryString.requestLong(request, "CASH_CASHIER_ID");

    //STRING TYPE
    String oidData = FRMQueryString.requestString(request, FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]);
    long oidDataSelectedGuest = FRMQueryString.requestLong(request, FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] + "_GUEST");
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
    String editItemSpv = PstSystemProperty.getValueByName("CASHIER_EDIT_ITEM_USING_SUPERVISOR");
    String oidCustomerTypeGuide = PstSystemProperty.getValueByName("OID_TIPE_CUSTOMER_GUIDE");
    String hidePoinReward = PstSystemProperty.getValueByName("HIDE_POIN_REWARD");

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
    Vector listSpv = new Vector(1, 1);

    //SESSION
    boolean isLoggedIn = false;

    SessUserCashierSession userCashier = (SessUserCashierSession) session.getValue(SessUserCashierSession.HTTP_SESSION_CASHIER);
    String loginId = "";
    String userLoginName = "";
    long userId = 0;
    try {
      if (userCashier == null) {
        userCashier = new SessUserCashierSession();
      } else {
        if (userCashier.isLoggedIn() == true) {
          isLoggedIn = true;
          AppUser appUser = userCashier.getAppUser();
          loginId = appUser.getLoginId();
          userId = appUser.getOID();
          userLoginName = appUser.getFullName();
        }
      }
    } catch (Exception exc) {
      System.out.println(" >>> Exception during check login");
    }
    try {
      design = Integer.parseInt((String) PstSystemProperty.getValueByName("DESIGN_MATERIAL_FOR"));
      listSpv = PstAppUser.listPartObj(0, 0,
              PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID] + "='" + spvId + "' "
              + "AND " + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD] + "='" + spvPassword + "' "
              + "AND " + PstAppUser.fieldNames[PstAppUser.FLD_USER_GROUP_NEW] + "='1'", "");
    } catch (Exception e) {
      design = 0;
    }

////////////////////////////
/////////DEFAULT RETURN DATA
    //STRING TYPE
    String html = "";
    String displayTotal = "";
    String shortMessage = "";
    int numberOfError = 0;

    //DOUBLE TYPE
    double balanceValue = 0;
    double creditCardCharge = 0;
    double paidValue = 0;
    String whereSql2 = "";
    if (iCommand == Command.NONE) {
      try {

        if (loadtype.equals("getListBillItemGrid")) {

          html = getListBillItemGrid(request);

        } else if (loadtype.equals("searchItemByCategory")) {

          html = getItemListGridViewWithImage(request);

          String searchText = FRMQueryString.requestString(request, "search");
          returnData.put("SEARCH_TEXT", "" + searchText);

        } else if (loadtype.equals("addMultiPayExchange")) {

          int currentIndex = FRMQueryString.requestInt(request, "currentIndex");
          String paymentSystemId = FRMQueryString.requestString(request, "FRM_MULTI_EXC_PAYMENT_ID");
          String cardName = FRMQueryString.requestString(request, "FRM_MULTI_EXC_CARD_NAME");
          String cardNumber = FRMQueryString.requestString(request, "FRM_MULTI_EXC_CARD_NUMBER");
          String bankName = FRMQueryString.requestString(request, "FRM_MULTI_EXC_BANK_NAME");
          String expiredDate = FRMQueryString.requestString(request, "FRM_MULTI_EXC_EXPIRED_DATE");
          String payValue = FRMQueryString.requestString(request, "FRM_MULTI_EXC_PAYMENT_VALUE");

          try {
            PaymentSystem ps = PstPaymentSystem.fetchExc(Long.valueOf(paymentSystemId));

            int newIndex = currentIndex + 1;
            html = ""
                    + "<tr id='rowPayExc_" + newIndex + "'>"
                    + "     <td class='text-center'><span style='cursor: pointer' class='text-red deletePayExc' data-index='" + newIndex + "'><i class='fa fa-close'></i></span></td>"
                    + "     <td>" + ps.getPaymentSystem()
                    + "         <input type='hidden' value='" + paymentSystemId + "' name='FRM_PAYMENT_SYSTEM_ID' class='form-control input-sm'>"
                    + "         <input type='hidden' value='" + cardName + "' name='FRM_PAYMENT_CARD_NAME' class='form-control input-sm'>"
                    + "         <input type='hidden' value='" + cardNumber + "' name='FRM_PAYMENT_CARD_NUMBER' class='form-control input-sm'>"
                    + "         <input type='hidden' value='" + bankName + "' name='FRM_PAYMENT_BANK_NAME' class='form-control input-sm'>"
                    + "         <input type='hidden' value='" + expiredDate + "' name='FRM_PAYMENT_EXPIRED_DATE' class='form-control input-sm'>"
                    + "     </td>"
                    + "     <td><input type='text' size='5' value='" + payValue + "' name='FRM_PAYMENT_VALUE' class='form-control input-sm payVal'></td>"
                    + "</tr>"
                    + "";
          } catch (NumberFormatException | DBException e) {
            numberOfError = 1;
            shortMessage = e.getMessage();
          }

        } else if (loadtype.equals("searchItemExchange")) {

          HashMap<String, Object> mapResult = searchItemExchange(request, cashCashierId);
          numberOfError = (Integer) mapResult.get("ERROR");
          shortMessage = mapResult.get("MESSAGE").toString();
          html = mapResult.get("HTML").toString();

        } else if (loadtype.equals("getFormReturnExchange")) {

          try {
            long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
            String whereBill = "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oidBillMain + "'";
            Vector<Billdetail> listItemReturn = PstCustomBillMain.listItemOpenBill(0, 0, whereBill, "");

            String sizeType = PstSystemProperty.getValueByName("CASHIER_SIZE_GROUP_TYPE");

            //DRAW FORM ITEM RETURN
            String htmlListReturn = ""
                    + "<label>Item List :</label>"
                    + "<table class='table table-striped' style='font-size: 12px'>"
                    + "     <tr style='background-color: #ececec'>"
                    + "         <th style='width: 1%'>No.</th>"
                    + "         <th style='width: 20%'>Name</th>"
                    + "         <th>Size</th>"
                    + "         <th>Qty</th>"
                    + "         <th>Price</th>"
                    + "         <th>Disc</th>"
                    + "         <th>Total</th>"
                    + "         <th class='text-nowrap' style='width: 1%'>Qty Return</th>"
                    + "         <th class='text-nowrap'>Total Return</th>"
                    + "     </tr>"
                    + "";

            int no = 0;
            for (Billdetail billdetail : listItemReturn) {
              //GET SIZE
              long oidMappingSize = PstMaterialMappingType.getSelectedTypeId(Integer.valueOf(sizeType), billdetail.getMaterialId());
              String size = "-";
              try {
                MasterType type = PstMasterType.fetchExc(oidMappingSize);
                size = type.getMasterName();
              } catch (Exception exc) {
              }

              //cek apakah item ini sudah pernah di return sebelumnya
              String whereSumQty = PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_RETURN_ID] + "='" + billdetail.getOID() + "'";
              double sumQty = PstBillDetail.getSumTotalItemReturn(whereSumQty);
              double remainItem = billdetail.getQty() - sumQty;

              int checkReturn = PstCustomBillMain.checkReturn(oidBillMain, billdetail.getOID());

              no++;
              htmlListReturn += ""
                      + "<tr>"
                      + "     <td>" + no + ".</td>"
                      + "     <td>" + billdetail.getItemName()
                      + "         <input type='hidden' value='" + billdetail.getMaterialId() + "' name='FRM_ITEM_RETURN_ID'>"
                      + "     </td>"
                      + "     <td>" + size + "</td>"
                      + "     <td>" + billdetail.getQty() + "</td>"
                      + "     <td class='text-right'>" + Formater.formatNumber(billdetail.getItemPrice(), "#,###") + "</td>"
                      + "     <td class='text-right'>" + Formater.formatNumber(billdetail.getDisc(), "#,##") + "</td>"
                      + "     <td class='text-right'>" + Formater.formatNumber(billdetail.getTotalPrice(), "#,###") + "</td>"
                      + "     <td>" + (remainItem == 0 ? "<div class='text-red text-bold'>Returned</div>" : "<input type='text' value='0' name='FRM_ITEM_RETURN_QTY' class='form-control input-sm itemExc' data-row='" + no + "' data-remain='" + remainItem + "' data-price='" + billdetail.getItemPrice() + "' data-discount='" + billdetail.getDisc() + "'>") + "</td>"
                      + "     <td>" + (remainItem == 0 ? "<div class='text-red text-bold'>Returned</div>" : "<div class='text-right' id='totalExc_" + no + "'>0</div>") + "</td>"
                      + "</tr>";
            }

            htmlListReturn += ""
                    + "     <tr style='font-size: 14px'>"
                    + "         <td colspan='4'>"
                    + "             <div class='checkbox' style='margin: 0px;'>"
                    + "                 <label><input type='checkbox' id='exchangeAll'> Exchange all item</label>"
                    + "             </div>"
                    + "         </td>"
                    + "         <td colspan='5' class='text-right text-bold'>TOTAL RETURN : <span id='grandTotalExc'>0</span></td>"
                    + "     </tr>"
                    + "</table>"
                    + "<div id='errorMsg' class='label-danger text-center'></div>"
                    + "";

            //DRAW FORM ITEM EXCHANGE
            String htmlListExchange = ""
                    + "<label>Item Exchange :</label>"
                    + "<input type='hidden' id='indexItemExc' value=0>"
                    + "<table id='tabelItemExchange' class='table table-striped' style='font-size: 12px'>"
                    + "     <tr style='background-color: #ececec'>"
                    + "         <th style='width: 1%; color: lightgrey'><i class='fa fa-gear'></i></th>"
                    + "         <th>Name</th>"
                    + "         <th>Size</th>"
                    + "         <th class='text-nowrap' style='width: 1%'>Qty Exc</th>"
                    + "         <th>Price</th>"
                    + "         <th>Promo</th>"
                    + "         <th style='width: 25%'>Disc</th>"
                    + "         <th>Total</th>"
                    + "     </tr>"
                    + "</table>"
                    + "<div class='form-inline'>"
                    + "     <span>Search item :</span>"
                    + "     <div class='input-group'>"
                    + "         <input type='text' id='searchItemExchange' placeholder='Scan barcode' class='form-control input-sm'>"
                    + "         <span class='input-group-addon btn btn-primary btnSearchItemExchange'><i class='fa fa-search'></i></span>"
                    + "     </div>"
                    + "     <label class='pull-right'>TOTAL EXCHANGE : <span id='grandTotalExcPrice'>0</span></label>"
                    + "</div>"
                    + "";

            //DRAW FORM MULTI PAYMENT
            String htmlListPayment = ""
                    + "<label>Payment :</label>"
                    + "<input type='hidden' id='indexPaymentExc' value=0>"
                    + "<table id='tabelPaymentExchange' class='table table-striped' style='font-size: 12px'>"
                    + "     <tr style='background-color: #ececec'>"
                    + "         <th style='width: 1%; color: lightgrey'><i class='fa fa-gear'></i></th>"
                    + "         <th class='text-nowrap'>Payment Type</th>"
                    + "         <th>Nominal</th>"
                    + "     </tr>";

            //set payment default
            long oidPaymentSystemReturn = 0;
            Vector<PaymentSystem> listPaymentReturn = PstPaymentSystem.list(0, 0, PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + " = 3 ", "");
            if (!listPaymentReturn.isEmpty()) {
              oidPaymentSystemReturn = listPaymentReturn.get(0).getOID();
              htmlListPayment += ""
                      + "<tr>"
                      + "     <td class='text-center' style='color: grey'><i class='fa fa-lock'></i></td>"
                      + "     <td>" + listPaymentReturn.get(0).getPaymentSystem()
                      + "         <input type='hidden' value='" + oidPaymentSystemReturn + "' name='FRM_PAYMENT_SYSTEM_ID' class='form-control input-sm'>"
                      + "         <input type='hidden' value='' name='FRM_PAYMENT_CARD_NAME' class='form-control input-sm'>"
                      + "         <input type='hidden' value='' name='FRM_PAYMENT_CARD_NUMBER' class='form-control input-sm'>"
                      + "         <input type='hidden' value='' name='FRM_PAYMENT_BANK_NAME' class='form-control input-sm'>"
                      + "         <input type='hidden' value='' name='FRM_PAYMENT_EXPIRED_DATE' class='form-control input-sm'>"
                      + "     </td>"
                      + "     <td><input type='text' readonly size='5' value='0' id='payValDefault' name='FRM_PAYMENT_VALUE' class='form-control input-sm payVal'></td>"
                      + "</tr>"
                      + "";
            }

            htmlListPayment += ""
                    + "</table>"
                    + "<button type='button' id='btnAddMultiPaymentRetExc' class='btn btn-sm btn-primary'><i class='fa fa-plus'></i> Add Payment</button>"
                    + ""
                    + "<div class='pull-right'>"
                    + "     <label>TOTAL PAYMENT : <span id='grandTotalPayment'>0</span></label>"
                    + "     <div class='text-right text-bold'><small id='paymentExcRemain'></small></div>"
                    + "</div>"
                    + "";

            //COMBINE ALL HTML
            //cek tgl bill
            String msg = "";
            BillMain bm = PstBillMain.fetchExc(oidBillMain);
            Date billDate = Formater.reFormatDate(bm.getBillDate(), "yyyy-MM-dd");
            Date now = Formater.reFormatDate(new Date(), "yyyy-MM-dd");
            if (billDate.before(now)) {
              long difference = (now.getTime() - billDate.getTime()) / (1000 * 60 * 60 * 24);
              if (difference > 1) {
                msg = "<h4 class='text-red text-center' style=''><i class='fa fa-warning'></i> " + difference + " days (" + Formater.formatDate(bm.getBillDate(), "dd MMM yyyy") + ")</h4>";
              }
            }
            html = ""
                    + "<form id='FORM_RETURN_EXCHANGE'>"
                    + "     <input type='hidden' name='oidBillMain' value='" + oidBillMain + "' id='oidBillMain'>"
                    + "     <input type='hidden' name='loadtype' value='saveReturnExchange'>"
                    + "     <input type='hidden' name='command' value='" + Command.SAVE + "'>"
                    + msg
                    + "     <div class='row'>"
                    + "         <div class='col-sm-6'>" + htmlListReturn + "</div>"
                    + "         <div class='col-sm-6'>" + htmlListExchange + "<br><br>" + htmlListPayment + "</div>"
                    + "     </div>"
                    + "</form>"
                    + "";
          } catch (NumberFormatException | DBException e) {
            numberOfError = 1;
            shortMessage = e.getMessage();
          }

        } else if (loadtype.equals("loadbill")) {
////////////////////////////////
//////////////////LOAD BILL LIST
          //balanceValue = getBalance;

          int isPack = 0;
          String packIs = "0";
          //mengecek apakah pada bill detail ada yang mengandung package atau tidak
          String whereCheckPack = ""
                  + " billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oid + "' "
                  + " AND billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID] + "<>'0'";

          isPack = PstCustomBillMain.getTotalPackage(0, 0, whereCheckPack, "");
          if (isPack > 0) {
            packIs = "1";
          }

          String whereBill = "";
          String creditType = PstSystemProperty.getValueByName("CREDIT_TYPE");
          if (creditType.equals("1")) {
            whereBill = " OR (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                    + ")";
          }

          String whereSql = ""
                  + " ((billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oid + "' "
                  + " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                  + " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                  + " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                  + " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1')" + whereBill + ")";

          if (isPack > 0) {
            whereSql2 = ""
                    + " AND billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID] + "=0";
          }

          String whereSql3 = "";
          whereSql3 = ""
                  + " AND billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_STATUS] + " <> '3'";

          Vector listBill = PstCustomBillMain.listItemOpenBill(0, 0, whereSql, "");
          Vector listBill2 = PstCustomBillMain.listItemOpenBill(0, 0, whereSql + whereSql2, "");
          Vector listBill3 = PstCustomBillMain.listItemOpenBill(0, 0, whereSql + whereSql2 + whereSql3, "");
          BillMain billMain = new BillMain();
          try {
            billMain = PstBillMain.fetchExc(oid);
          } catch (Exception ex) {

          }

                    boolean useGuidePrice = checkMemberGuidePrice(billMain.getOID());
                    String guestNameWrite="";
          MemberReg member = new MemberReg();
          if (billMain.getCustomerId() != 0) {
            Contact entContact = new Contact();
            try {
               entContact = PstContact.fetchExc(billMain.getCustomerId());
              //cek tipe member group
              member = PstMemberReg.fetchExc(billMain.getCustomerId());
            } catch (Exception e) {
            }
            if (entContact.getPersonName().length() == 0) {
              guestNameWrite = entContact.getCompName();
            } else {
              guestNameWrite = entContact.getPersonName() + " " + entContact.getPersonLastname();
            }
          } else {
            guestNameWrite = billMain.getGuestName();
          }

          String tableWrite = "";
          if (billMain.getTableId() != 0) {
            TableRoom entTableRoom = new TableRoom();
            entTableRoom = PstTableRoom.fetchExc(billMain.getTableId());
            if (entTableRoom.getTableNumber().length() > 0) {
              tableWrite = "" + entTableRoom.getTableNumber() + "";
            }
          }

          statusOrder = listBill3.size();

          html = ""
                  + "<div class='row'>"
                  + "<div class=''>"
                  + "<div class='col-md-6'>"
                  + "<span>" + listBill.size() + " Items in Transaction </span>";

          String textMember = (guestNameWrite.length() > 0) ? guestNameWrite : "Add Member";
          html += " of &nbsp;<span class='label label-warning bill-profile' style='cursor: pointer; font-family: Century Gothic; font-size: 16px;' data-id='" + member.getOID() + "' data-code='" + member.getContactCode() + "' data-name='" + guestNameWrite + "' data-phone='" + member.getTelpMobile() + "' data-membergroup='" + member.getMemberGroupId() + "'><i class='fa fa-pencil'></i> " + textMember + "</span>";

          if (tableWrite.length() > 0) {
            html += ""
                    + "&nbsp; Table <span class='label label-danger' style='font-family: Century Gothic; font-size: 16px;'>" + tableWrite + "  " + billMain.getTableName() + "</span>";
          }

          html += ""
                  + "</div>"
                            
                  + "<div class='col-md-6 pull-right'>"
                  + "<h4 style='text-align:right;'>Transaction Number #" + billMain.getInvoiceNo() + "</h4>"
                  + "</div>"
                  + "</div>"
                  + "</div>"
                  + "<div class='row'>"
                  + "<div class='col-md-12' id='mainTransaction'>";
          html += drawList(iCommand, listBill2, oid, null, packIs, useGuidePrice);
          if (hidePoinReward.equals("0")) {
            html += !listBill2.isEmpty() && PstMemberReg.checkOID(billMain.getCustomerId()) ? PstBillMain.getHtmlDetailPoin(oid) : "";
          }
          html += "</div>"
                  + "</div>";

////////////////// MULTI PAYMENT
        } else if (loadtype.equals("loadlistmultipayment")) {
          int countMultiPayment = 0;

          try {
            countMultiPayment = FRMQueryString.requestInt(request, "countMultiPayment");
          } catch (Exception e) {
            countMultiPayment = 0;
          }
          String type = FRMQueryString.requestString(request, "type");

          if (countMultiPayment == 0) {
            // SAMA SEKALI BELUM ADA PAYMENT
            if (type.equals("purelist")) {
              html += ""
                      + "<input type='hidden' id='countMultiPayment' name='countMultiPayment' value='" + countMultiPayment + "'>"
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
            } else if (type.equals("addNew")) {
              int nexCount = countMultiPayment + 1;
              //GET REQUEST DATA
              long cashBillMainId = FRMQueryString.requestLong(request, "multiPayCashBillMainId");
              int masterFieldPayType = FRMQueryString.requestInt(request, "MASTER_FRM_FIELD_PAY_TYPE_MULTI");
              long payTypeMulti = FRMQueryString.requestLong(request, "FRM_FIELD_PAY_TYPE_MULTI");
              double totalAmount = FRMQueryString.requestDouble(request, "totalAmount");
              String ccNameMulti = "";
              String ccNumberMulti = "";
              String debitBankNameMulti = "";
              String expiredDateMulti = "";
              double payAmountMulti = 0;
              PaymentSystem paymentSystemMulti = new PaymentSystem();
              long voucherId = 0;

              //SEMUA DENGAN TRY CATCH
              try {
                ccNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NAME_MULTI");
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
                payAmountMulti = FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI");
              } catch (Exception e) {
              }
              try {
                paymentSystemMulti = PstPaymentSystem.fetchExc(payTypeMulti);
              } catch (Exception e) {
              }

              try {
                voucherId = FRMQueryString.requestLong(request, "FRM_FIELD_VOUCHER_ID_MULTI");
              } catch (Exception e) {

              }

              totalAmount = totalAmount - payAmountMulti;
              html += ""
                      + "<input type='hidden' id='countMultiPayment' name='countMultiPayment' value='" + nexCount + "'>"
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
                      + "<input value='" + masterFieldPayType + "' type='hidden' id='MASTER_FRM_FIELD_PAY_TYPE_MULTI_1' name='MASTER_FRM_FIELD_PAY_TYPE_MULTI_1'>"
                      + "<input value='" + payTypeMulti + "' type='hidden' id='FRM_FIELD_PAY_TYPE_MULTI_1' name='FRM_FIELD_PAY_TYPE_MULTI_1'>"
                      + "<input value='" + ccNameMulti + "' type='hidden' id='FRM_FIELD_CC_NAME_MULTI_1' name='FRM_FIELD_CC_NAME_MULTI_1'>"
                      + "<input value='" + ccNumberMulti + "' type='hidden' id='FRM_FIELD_CC_NUMBER_MULTI_1' name='FRM_FIELD_CC_NUMBER_MULTI_1'>"
                      + "<input value='" + debitBankNameMulti + "' type='hidden' id='FRM_FIELD_DEBIT_BANK_NAME_MULTI_1' name='FRM_FIELD_DEBIT_BANK_NAME_MULTI_1'>"
                      + "<input value='" + expiredDateMulti + "' type='hidden' id='FRM_FIELD_EXPIRED_DATE_MULTI_1' name='FRM_FIELD_EXPIRED_DATE_MULTI_1'>"
                      + "<input value='" + payAmountMulti + "' type='hidden' id='FRM_FIELD_PAY_AMOUNT_MULTI_1' name='FRM_FIELD_PAY_AMOUNT_MULTI_1'>"
                      + "<input value='" + voucherId + "' type='hidden' id='FRM_FIELD_VOUCHER_ID_MULTI_1' name='FRM_FIELD_VOUCHER_ID_MULTI_1'>"
                      + "<td>" + nexCount + "</td>"
                      + "<td>" + paymentSystemMulti.getPaymentSystem() + "</td>"
                      + "<td style='text-align:right;'>" + Formater.formatNumber(payAmountMulti, "#,###.##") + "</td>"
                      + "<td><button type='button' data-index='1' class='btn btn-danger deleteMultiPayment'><i class='fa fa-close'></i></button></td>"
                      + "</tr>"
                      + "</tbody>"
                      + "</table>"
                      + "</div>"
                      + "</div>";

              displayTotal = Formater.formatNumber(totalAmount, "#,###.##");
              costumBalance = totalAmount;
            }

          } else {
            if (type.equals("purelist")) {
              html += ""
                      + "<input type='hidden' id='countMultiPayment' name='countMultiPayment' value='" + countMultiPayment + "'>"
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
              for (int i = 1; i <= countMultiPayment; i++) {
                long cashBillMainId = FRMQueryString.requestLong(request, "multiPayCashBillMainId");
                int masterFieldPayType = FRMQueryString.requestInt(request, "MASTER_FRM_FIELD_PAY_TYPE_MULTI");
                long payTypeMulti = FRMQueryString.requestLong(request, "FRM_FIELD_PAY_TYPE_MULTI");
                String ccNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NAME_MULTI");
                String ccNumberMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NUMBER_MULTI");
                String debitBankNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_DEBIT_BANK_NAME_MULTI");
                String expiredDateMulti = FRMQueryString.requestString(request, "FRM_FIELD_EXPIRED_DATE_MULTI");
                double payAmountMulti = FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI");
                PaymentSystem paymentSystemMulti = new PaymentSystem();

                try {
                  paymentSystemMulti = PstPaymentSystem.fetchExc(payTypeMulti);
                } catch (Exception e) {
                }

                html += ""
                        + "<tr>"
                        + "<input value='" + masterFieldPayType + "' type='hidden' id='MASTER_FRM_FIELD_PAY_TYPE_MULTI_" + i + "' name='MASTER_FRM_FIELD_PAY_TYPE_MULTI_" + i + "'>"
                        + "<input value='" + payTypeMulti + "' type='hidden' id='FRM_FIELD_PAY_TYPE_MULTI_" + i + "' name='FRM_FIELD_PAY_TYPE_MULTI_" + i + "'>"
                        + "<input value='" + ccNameMulti + "' type='hidden' id='FRM_FIELD_CC_NAME_MULTI_" + i + "' name='FRM_FIELD_CC_NAME_MULTI_" + i + "'>"
                        + "<input value='" + ccNumberMulti + "' type='hidden' id='FRM_FIELD_CC_NUMBER_MULTI_" + i + "' name='FRM_FIELD_CC_NUMBER_MULTI_" + i + "'>"
                        + "<input value='" + debitBankNameMulti + "' type='hidden' id='FRM_FIELD_DEBIT_BANK_NAME_MULTI_" + i + "' name='FRM_FIELD_DEBIT_BANK_NAME_MULTI_" + i + "'>"
                        + "<input value='" + expiredDateMulti + "' type='hidden' id='FRM_FIELD_EXPIRED_DATE_MULTI_" + i + "' name='FRM_FIELD_EXPIRED_DATE_MULTI_" + i + "'>"
                        + "<input value='" + payAmountMulti + "' type='hidden' id='FRM_FIELD_PAY_AMOUNT_MULTI_" + i + "' name='FRM_FIELD_PAY_AMOUNT_MULTI_" + i + "'>"
                        + "<td>" + i + "</td>"
                        + "<td>" + paymentSystemMulti.getPaymentSystem() + "</td>"
                        + "<td style='text-align:right;'>" + Formater.formatNumber(payAmountMulti, "#,###.##") + "</td>"
                        + "<td><button type='button' data-index='" + i + "' class='btn btn-danger deleteMultiPayment'><i class='fa fa-close'></i></button></td>"
                        + "</tr>";
              }

              html += ""
                      + "</tbody>"
                      + "</table>"
                      + "</div>"
                      + "</div>";
            } else if (type.equals("addNew")) {
              int nexCount = countMultiPayment + 1;
              double totalAmount = FRMQueryString.requestDouble(request, "totalAmount");
              double remainsAmount = 0;
              html += ""
                      + "<input type='hidden' id='countMultiPayment' name='countMultiPayment' value='" + nexCount + "'>"
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
              for (int i = 1; i <= countMultiPayment; i++) {
                long cashBillMainId = FRMQueryString.requestLong(request, "multiPayCashBillMainId");
                int masterFieldPayType = FRMQueryString.requestInt(request, "MASTER_FRM_FIELD_PAY_TYPE_MULTI_" + i + "");
                long payTypeMulti = FRMQueryString.requestLong(request, "FRM_FIELD_PAY_TYPE_MULTI_" + i + "");
                String ccNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NAME_MULTI_" + i + "");
                String ccNumberMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NUMBER_MULTI_" + i + "");
                String debitBankNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_DEBIT_BANK_NAME_MULTI_" + i + "");
                String expiredDateMulti = FRMQueryString.requestString(request, "FRM_FIELD_EXPIRED_DATE_MULTI_" + i + "");
                double payAmountMulti = FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI_" + i + "");
                PaymentSystem paymentSystemMulti = new PaymentSystem();
                long voucherId = FRMQueryString.requestLong(request, "FRM_FIELD_VOUCHER_ID_MULTI_" + i + "");

                try {
                  paymentSystemMulti = PstPaymentSystem.fetchExc(payTypeMulti);
                } catch (Exception e) {
                }

                totalAmount = totalAmount - payAmountMulti;

                html += ""
                        + "<tr>"
                        + "<input value='" + masterFieldPayType + "' type='hidden' id='MASTER_FRM_FIELD_PAY_TYPE_MULTI_" + i + "' name='MASTER_FRM_FIELD_PAY_TYPE_MULTI_" + i + "'>"
                        + "<input value='" + payTypeMulti + "' type='hidden' id='FRM_FIELD_PAY_TYPE_MULTI_" + i + "' name='FRM_FIELD_PAY_TYPE_MULTI_" + i + "'>"
                        + "<input value='" + ccNameMulti + "' type='hidden' id='FRM_FIELD_CC_NAME_MULTI_" + i + "' name='FRM_FIELD_CC_NAME_MULTI_" + i + "'>"
                        + "<input value='" + ccNumberMulti + "' type='hidden' id='FRM_FIELD_CC_NUMBER_MULTI_" + i + "' name='FRM_FIELD_CC_NUMBER_MULTI_" + i + "'>"
                        + "<input value='" + debitBankNameMulti + "' type='hidden' id='FRM_FIELD_DEBIT_BANK_NAME_MULTI_" + i + "' name='FRM_FIELD_DEBIT_BANK_NAME_MULTI_" + i + "'>"
                        + "<input value='" + expiredDateMulti + "' type='hidden' id='FRM_FIELD_EXPIRED_DATE_MULTI_" + i + "' name='FRM_FIELD_EXPIRED_DATE_MULTI_" + i + "'>"
                        + "<input value='" + payAmountMulti + "' type='hidden' id='FRM_FIELD_PAY_AMOUNT_MULTI_" + i + "' name='FRM_FIELD_PAY_AMOUNT_MULTI_" + i + "'>"
                        + "<input value='" + voucherId + "' type='hidden' id='FRM_FIELD_VOUCHER_ID_MULTI_" + i + "' name='FRM_FIELD_VOUCHER_ID_MULTI_" + i + "'>"
                        + "<td>" + i + "</td>"
                        + "<td>" + paymentSystemMulti.getPaymentSystem() + "</td>"
                        + "<td style='text-align:right;'>" + Formater.formatNumber(payAmountMulti, "#,###.##") + "</td>"
                        + "<td><button type='button' data-index='" + i + "' class='btn btn-danger deleteMultiPayment'><i class='fa fa-close'></i></button></td>"
                        + "</tr>";
              }

              //GET REQUEST DATA
              long cashBillMainId = FRMQueryString.requestLong(request, "multiPayCashBillMainId");
              int masterFieldPayType = FRMQueryString.requestInt(request, "MASTER_FRM_FIELD_PAY_TYPE_MULTI");
              long payTypeMulti = FRMQueryString.requestLong(request, "FRM_FIELD_PAY_TYPE_MULTI");
              String ccNameMulti = "";
              String ccNumberMulti = "";
              String debitBankNameMulti = "";
              String expiredDateMulti = "";
              double payAmountMulti = 0;
              PaymentSystem paymentSystemMulti = new PaymentSystem();
              long voucherId = 0;
              //SEMUA DENGAN TRY CATCH
              try {
                ccNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NAME_MULTI");
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
                payAmountMulti = FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI");
              } catch (Exception e) {
              }
              try {
                paymentSystemMulti = PstPaymentSystem.fetchExc(payTypeMulti);
              } catch (Exception e) {
              }
              try {
                voucherId = FRMQueryString.requestLong(request, "FRM_FIELD_VOUCHER_ID_MULTI");
              } catch (Exception e) {

              }
              totalAmount = totalAmount - payAmountMulti;

              html += ""
                      + "<tr>"
                      + "<input value='" + masterFieldPayType + "' type='hidden' id='MASTER_FRM_FIELD_PAY_TYPE_MULTI_" + nexCount + "' name='MASTER_FRM_FIELD_PAY_TYPE_MULTI_" + nexCount + "'>"
                      + "<input value='" + payTypeMulti + "' type='hidden' id='FRM_FIELD_PAY_TYPE_MULTI_" + nexCount + "' name='FRM_FIELD_PAY_TYPE_MULTI_" + nexCount + "'>"
                      + "<input value='" + ccNameMulti + "' type='hidden' id='FRM_FIELD_CC_NAME_MULTI_" + nexCount + "' name='FRM_FIELD_CC_NAME_MULTI_" + nexCount + "'>"
                      + "<input value='" + ccNumberMulti + "' type='hidden' id='FRM_FIELD_CC_NUMBER_MULTI_" + nexCount + "' name='FRM_FIELD_CC_NUMBER_MULTI_" + nexCount + "'>"
                      + "<input value='" + debitBankNameMulti + "' type='hidden' id='FRM_FIELD_DEBIT_BANK_NAME_MULTI_" + nexCount + "' name='FRM_FIELD_DEBIT_BANK_NAME_MULTI_" + nexCount + "'>"
                      + "<input value='" + expiredDateMulti + "' type='hidden' id='FRM_FIELD_EXPIRED_DATE_MULTI_" + nexCount + "' name='FRM_FIELD_EXPIRED_DATE_MULTI_" + nexCount + "'>"
                      + "<input value='" + payAmountMulti + "' type='hidden' id='FRM_FIELD_PAY_AMOUNT_MULTI_" + nexCount + "' name='FRM_FIELD_PAY_AMOUNT_MULTI_" + nexCount + "'>"
                      + "<input value='" + voucherId + "' type='hidden' id='FRM_FIELD_VOUCHER_ID_MULTI_" + nexCount + "' name='FRM_FIELD_VOUCHER_ID_MULTI_" + nexCount + "'>"
                      + "<td>" + nexCount + "</td>"
                      + "<td>" + paymentSystemMulti.getPaymentSystem() + "</td>"
                      + "<td style='text-align:right;'>" + Formater.formatNumber(payAmountMulti, "#,###.##") + "</td>"
                      + "<td><button type='button' data-index='" + nexCount + "' class='btn btn-danger deleteMultiPayment'><i class='fa fa-close'></i></button></td>"
                      + "</tr>";
              html += ""
                      + "</tbody>"
                      + "</table>"
                      + "</div>"
                      + "</div>";
              displayTotal = Formater.formatNumber(totalAmount, "#,###.##");
              costumBalance = totalAmount;
            } else if (type.equals("deleteOnce")) {
              int index = FRMQueryString.requestInt(request, "index");
              int newCount = countMultiPayment - 1;
              double totalAmount = FRMQueryString.requestDouble(request, "totalAmount");
              int j = 1;
              html += ""
                      + "<input type='hidden' id='countMultiPayment' name='countMultiPayment' value='" + newCount + "'>"
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
              for (int i = 1; i <= countMultiPayment; i++) {
                if (i != index) {
                  long cashBillMainId = FRMQueryString.requestLong(request, "multiPayCashBillMainId");
                  int masterFieldPayType = FRMQueryString.requestInt(request, "MASTER_FRM_FIELD_PAY_TYPE_MULTI_" + j + "");
                  long payTypeMulti = FRMQueryString.requestLong(request, "FRM_FIELD_PAY_TYPE_MULTI_" + j + "");
                  String ccNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NAME_MULTI_" + j + "");
                  String ccNumberMulti = FRMQueryString.requestString(request, "FRM_FIELD_CC_NUMBER_MULTI_" + j + "");
                  String debitBankNameMulti = FRMQueryString.requestString(request, "FRM_FIELD_DEBIT_BANK_NAME_MULTI_" + j + "");
                  String expiredDateMulti = FRMQueryString.requestString(request, "FRM_FIELD_EXPIRED_DATE_MULTI_" + j + "");
                  double payAmountMulti = FRMQueryString.requestDouble(request, "FRM_FIELD_PAY_AMOUNT_MULTI_" + j + "");
                  long voucherId = FRMQueryString.requestLong(request, "FRM_FIELD_VOUCHER_ID_MULTI_" + j + "");
                  PaymentSystem paymentSystemMulti = new PaymentSystem();

                  try {
                    paymentSystemMulti = PstPaymentSystem.fetchExc(payTypeMulti);
                  } catch (Exception e) {
                  }

                  html += ""
                          + "<tr>"
                          + "<input value='" + masterFieldPayType + "' type='hidden' id='MASTER_FRM_FIELD_PAY_TYPE_MULTI_" + j + "' name='MASTER_FRM_FIELD_PAY_TYPE_MULTI_" + j + "'>"
                          + "<input value='" + payTypeMulti + "' type='hidden' id='FRM_FIELD_PAY_TYPE_MULTI_" + j + "' name='FRM_FIELD_PAY_TYPE_MULTI_" + j + "'>"
                          + "<input value='" + ccNameMulti + "' type='hidden' id='FRM_FIELD_CC_NAME_MULTI_" + j + "' name='FRM_FIELD_CC_NAME_MULTI_" + j + "'>"
                          + "<input value='" + ccNumberMulti + "' type='hidden' id='FRM_FIELD_CC_NUMBER_MULTI_" + j + "' name='FRM_FIELD_CC_NUMBER_MULTI_" + j + "'>"
                          + "<input value='" + debitBankNameMulti + "' type='hidden' id='FRM_FIELD_DEBIT_BANK_NAME_MULTI_" + j + "' name='FRM_FIELD_DEBIT_BANK_NAME_MULTI_" + j + "'>"
                          + "<input value='" + expiredDateMulti + "' type='hidden' id='FRM_FIELD_EXPIRED_DATE_MULTI_" + j + "' name='FRM_FIELD_EXPIRED_DATE_MULTI_" + j + "'>"
                          + "<input value='" + payAmountMulti + "' type='hidden' id='FRM_FIELD_PAY_AMOUNT_MULTI_" + j + "' name='FRM_FIELD_PAY_AMOUNT_MULTI_" + j + "'>"
                          + "<input value='" + voucherId + "' type='hidden' id='FRM_FIELD_VOUCHER_ID_MULTI_" + j + "' name='FRM_FIELD_VOUCHER_ID_MULTI_" + j + "'>"
                          + "<td>" + j + "</td>"
                          + "<td>" + paymentSystemMulti.getPaymentSystem() + "</td>"
                          + "<td style='text-align:right;'>" + Formater.formatNumber(payAmountMulti, "#,###.##") + "</td>"
                          + "<td><button type='button' data-index='" + i + "' class='btn btn-danger deleteMultiPayment'><i class='fa fa-close'></i></button></td>"
                          + "</tr>";
                  totalAmount = totalAmount - payAmountMulti;
                  j = j + 1;
                }

              }
              displayTotal = Formater.formatNumber(totalAmount, "#,###.##");
              costumBalance = totalAmount;
              html += ""
                      + "</tbody>"
                      + "</table>"
                      + "</div>"
                      + "</div>";
            }
          }

////////////////// LOAD BILL RETURN
        } else if (loadtype.equals("loadbillreturn")) {
          Vector listBill = PstCustomBillMain.listItemOpenBill(0, 0,
                  "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oid + "'", "");

          BillMain billMain = new BillMain();
          try {
            billMain = PstBillMain.fetchExc(oid);
          } catch (Exception ex) {

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

          html += drawListReturn(iCommand, listBill, oid, null);
          //>>> ADDED BY DEWOK 20190222 FORM EXCHANGE PRODUCT
          html += "<a id='btnReturnExchange' class='btn btn-warning' data-oidbill='" + oid + "' data-billnumber='" + billMain.getInvoiceNumber() + "'><i class='fa fa-refresh'></i> Exchange Product</a>";
          //<<<
          html += ""
                  + "<input type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] + "' value='" + oid + "'>"
                  + "</div>"
                  + "</div>";

/////////////////////////////////			
/////////////////LOAD SEARCH LIST
        } else if (loadtype.equals("loadsearch")) {
          //balanceValue = getBalance;
          String showTime = PstSystemProperty.getValueByName("CASHIER_SHOW_DATE_TIME_LOAD_BILL");
          String date1 = "";
          String date2 = "";
          long location = 0;
          String tableName = "";
          int showPage = 1;
          int jmlShowData = 15;
          try {
            date1 = FRMQueryString.requestString(request, "date1");
          } catch (Exception e) {
            date1 = "";
          }
          try {
            date2 = FRMQueryString.requestString(request, "date2");
          } catch (Exception e) {
            date2 = "";
          }

          try {
            location = FRMQueryString.requestLong(request, "location");
          } catch (Exception e) {
            location = 0;
          }

          try {
            tableName = FRMQueryString.requestString(request, "table");
          } catch (Exception e) {

          }
          try {
            showPage = FRMQueryString.requestInt(request, "showPage");
          } catch (Exception e) {
            showPage = 1;
          }
          if (showPage == 0) {
            showPage = 1;
          }

          int start = (showPage - 1) * jmlShowData;

          String whereClause = "";
          /*if(oidData.length() != 0){
			whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+"='"+oidData+"' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+"='"+oidData+"' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+"='"+oidData+"' "
                                + "OR Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") = '"+oidData+"' "
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+"='"+oidData+"')";
		    }*/
          if (oidData.length() != 0 || date1.length() != 0 || date2.length() != 0) {
            if (oidData.length() != 0) {
              if (date1.length() != 0 && date2.length() != 0) {
                whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%" + oidData + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " like '%" + oidData + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + oidData + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " like '%" + oidData + "%')";
                if (showTime.equals("0")) {
                  whereClause += " AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "')";
                } else {
                  whereClause += " AND (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " >= '" + date1 + "' AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + "<= '" + date2 + "') ";
                }

              } else if (date1.length() != 0 && date2.length() == 0) {
                whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%" + oidData + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " like '%" + oidData + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + oidData + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " like '%" + oidData + "%')";
                if (showTime.equals("0")) {
                  whereClause += " AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date1 + "') ";
                } else {
                  whereClause += " AND (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " >= '" + date1 + "' AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + "<= '" + date1 + "') ";
                }
              } else if (date1.length() == 0 && date2.length() != 0) {
                whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%" + oidData + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " like '%" + oidData + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + oidData + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " like '%" + oidData + "%')";
                if (showTime.equals("0")) {
                  whereClause += " AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date2 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') ";
                } else {
                  whereClause += " AND (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " >= '" + date2 + "' AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + "<= '" + date2 + "') ";
                }
              } else {
                whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%" + oidData + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " like '%" + oidData + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + oidData + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " like '%" + oidData + "%')";
              }
            } else {
              if (date1.length() != 0 && date2.length() != 0) {
                whereClause = "AND (";
                if (showTime.equals("0")) {
                  whereClause += " (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') ";
                } else {
                  whereClause += " (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " >= '" + date1 + "' AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + "<= '" + date2 + "') ";
                }
                //+ 
                whereClause += ""
                        + ")";
              } else if (date1.length() != 0 && date2.length() == 0) {
                whereClause = "AND (";
                if (showTime.equals("0")) {
                  whereClause += " (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date1 + "') ";
                } else {
                  whereClause += " (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " >= '" + date1 + "' AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " <= '" + date1 + "') ";
                }
                //
                whereClause += ""
                        + " )";
              } else if (date1.length() == 0 && date2.length() != 0) {
                whereClause = "AND (";
                if (showTime.equals("0")) {
                  whereClause += " ( Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date2 + "' AND date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") <= '" + date2 + "') ";
                } else {
                  whereClause += " (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " >= '" + date2 + "' AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " <= '" + date2 + "') ";
                }
                whereClause += ""
                        + " )";
              } else {
                whereClause = "";
              }
            }
          }

          if (location != 0) {
            whereClause += " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID] + "=" + location + "";
          }

          if (!tableName.equals("")) {
            whereClause += " AND meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + tableName + "%' ";
          }

          String whereBill = "";
          String creditType = PstSystemProperty.getValueByName("CREDIT_TYPE");
          String useProduction = PstSystemProperty.getValueByName("USE_PRODUCTION");
          if (creditType.equals("1") || useProduction.equals("1")) {
            whereBill = " OR (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                    + ")";
          }

                    String order = "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS] + " desc";
          Vector listBillMain = PstCustomBillMain.listOpenBill(start, jmlShowData,
                  "((billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1')" + whereBill + ") "
                  + whereClause, order);
          Vector listCount = PstCustomBillMain.listOpenBill(0, 0,
                  "((billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1')" + whereBill + ") "
                  + whereClause, order);
          html = drawListSearch(iCommand, listBillMain, oid, "loadsearch");

          int totalData = listCount.size();
          double tamp = Math.floor(totalData / jmlShowData);
          double tamp2 = totalData % jmlShowData;
          if (tamp2 > 0) {
            tamp2 = 1;
          }
          double JumlahHalaman = tamp2 + tamp;

          String attDisFirst = "";
          String attDisNext = "";

          if (showPage == 1) {
            attDisFirst = "disabled";
          }

          if (showPage == JumlahHalaman) {
            attDisNext = "disabled";
          }

          if (listBillMain.size() > 0) {
            html += ""
                    + "<div class='row'>"
                    + "<input type='hidden' id='totalPagingBill' value='" + Formater.formatNumber(JumlahHalaman, "") + "'>"
                    + "<input type='hidden' id='paggingPlaceBIll' value='" + showPage + "'>"
                    + "<input type='hidden' id='nextPageBIll' value='0'>"
                    + "<div class='col-md-6'>"
                    + "<label>"
                    + "Page " + showPage + " of " + Formater.formatNumber(JumlahHalaman, "") + ""
                    + "</label>"
                    + "</div>"
                    + "<div class='col-md-6'>"
                    + "<div data-original-title='Status' class='box-tools pull-right' title=''>"
                    + "<div class='btn-group' data-toggle='btn-toggle'>"
                    + "<button " + attDisFirst + " data-type='first' type='button' class='btn btn-default pagingBill'>"
                    + "<i class='fa fa-angle-double-left'></i>"
                    + "</button>"
                    + "<button id='prevPaging' " + attDisFirst + " data-type='prev' type='button' class='btn btn-default pagingBill'>"
                    + " <i class='fa fa-angle-left'></i>"
                    + "</button>"
                    + "<button id='nextPaging' " + attDisNext + " data-type='next' type='button' class='btn btn-default pagingBill'>"
                    + "<i class='fa fa-angle-right'></i>"
                    + "</button>"
                    + "<button " + attDisNext + " data-type='last' type='button' class='btn btn-default pagingBill'>"
                    + " <i class='fa fa-angle-double-right'></i>"
                    + "</button>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "</div>";
          }

/////////////////////////////////			
/////////////////LOAD SEARCH LIST
        } else if (loadtype.equals("loaditemlist")) {
          long oidLocation = 0;
          String priceType = "0";
          QueensLocation queensLocation;
          int showPage = 1;
          int jmlShowData = 10;
          String research = "";
          String research2 = "";
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

          if (itemsearch.length() == 0) {
            itemsearch = research;
          }

          if (research2.length() > 0) {
            itemsearch = research2;
          }

          if (showPage == 0) {
            showPage = 1;
          }

          int start = (showPage - 1) * jmlShowData;

          if (multilocation.equals("1")) {
            try {
              queensLocation = PstQueensLocation.fetchExc(oidLocationParent);
            } catch (Exception ex) {
              queensLocation = new QueensLocation();
            }

            oidLocation = queensLocation.getOID();
            priceType = "" + queensLocation.getPriceTypeId();
          } else {

            oidLocation = Long.parseLong(defaultOidLocation);
            priceType = defaultOidPriceType;

            try {
              queensLocation = PstQueensLocation.fetchExc(oidLocation);
            } catch (Exception ex) {
              queensLocation = new QueensLocation();
            }
          }

          //>>> added by dewok 20190130, for price type mapping
          long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
          if (PstBillMain.checkOID(oidBillMain)) {
            try {
              BillMain billMain = PstBillMain.fetchExc(oidBillMain);
              if (PstMemberReg.checkOID(billMain.getCustomerId())) {
                MemberReg memberReg = PstMemberReg.fetchExc(billMain.getCustomerId());
                MemberGroup memberGroup = PstMemberGroup.fetchExc(memberReg.getMemberGroupId());
                priceType = "" + memberGroup.getPriceTypeId();
              } else {
                oidLocation = billMain.getLocationId();
                queensLocation = PstQueensLocation.fetchExc(oidLocation);
                priceType = "" + queensLocation.getPriceTypeId();
              }
            } catch (Exception e) {
            }
          }
          //<<<

          String cashierMenuGrid = PstSystemProperty.getValueByName("CASHIER_MENU_GRID");

          if (cashierMenuGrid.equals("0")) {
            String whereClause = " p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL] + " != 4";

            if (itemsearch.length() > 0) {
              whereClause += " AND ("
                      + "p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE] + "='" + itemsearch + "' "
                      //+ "p."+PstMaterial.fieldNames[PstMaterial.FLD_SKU]+"='"+itemsearch+"' "
                      //+ "OR p."+PstMaterial.fieldNames[PstMaterial.FLD_NAME]+" LIKE '%"+itemsearch+"%'"
                      + ")";
            }

            Vector listItem = PstMaterial.listShopingCartUseLocation(start, jmlShowData, whereClause, "", "" + queensLocation.getStandartRateId(), priceType, design, oidLocation);
            html = drawListItem(iCommand, listItem, start, cashCashierId);

            int jmlData = PstMaterial.countShopingCartUseLocation(whereClause, "", "" + queensLocation.getStandartRateId(), priceType, design, oidLocation);

            double tamp = Math.floor(jmlData / jmlShowData);
            double tamp2 = jmlData % jmlShowData;
            if (tamp2 > 0) {
              tamp2 = 1;
            }
            double JumlahHalaman = tamp2 + tamp;
            if (JumlahHalaman == 0) {
              showPage = 0;
            }

            String attDisFirst = "";
            String attDisNext = "";

            if (showPage <= 1) {
              attDisFirst = "disabled";
            }

            if (showPage == JumlahHalaman) {
              attDisNext = "disabled";
            }

            html += ""
                    + "<div class='row'>"
                    + "<input type='hidden' id='totalPaging' value='" + Formater.formatNumber(JumlahHalaman, "") + "'>"
                    + "<input type='hidden' id='paggingPlace' value='" + showPage + "'>"
                    + "<div class='col-md-6'>"
                    + "<label>"
                    + "Page " + showPage + " of " + Formater.formatNumber(JumlahHalaman, "") + ""
                    + "</label>"
                    + "</div>"
                    + "<div class='col-md-6'>"
                    + "<div data-original-title='Status' class='box-tools pull-right' title=''>"
                    + "<div class='btn-group' data-toggle='btn-toggle'>"
                    + "<button " + attDisFirst + " data-type='first' type='button' class='btn btn-default pagings'>"
                    + "<i class='fa fa-angle-double-left'></i>"
                    + "</button>"
                    + "<button id='prevPaging' " + attDisFirst + " data-type='prev' type='button' class='btn btn-default pagings'>"
                    + " <i class='fa fa-angle-left'></i>"
                    + "</button>"
                    + "<button id='nextPaging' " + attDisNext + " data-type='next' type='button' class='btn btn-default pagings'>"
                    + "<i class='fa fa-angle-right'></i>"
                    + "</button>"
                    + "<button " + attDisNext + " data-type='last' type='button' class='btn btn-default pagings'>"
                    + " <i class='fa fa-angle-double-right'></i>"
                    + "</button>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "</div>";
            taxInc = listItem.size();
          } else {
            String diff = FRMQueryString.requestString(request, "diff");

            if (diff.equals("1")) {
              String whereClause = "";

              //>>> added by dewok 20190202 for non active item not included
              whereClause += " p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL] + " != 4";
              //<<<

              if (itemsearch.length() > 0) {
                whereClause += " AND ("
                        + "p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE] + "='" + itemsearch + "' "
                        //+ "p."+PstMaterial.fieldNames[PstMaterial.FLD_SKU]+"='"+itemsearch+"' "
                        //+ "OR p."+PstMaterial.fieldNames[PstMaterial.FLD_NAME]+" LIKE '%"+itemsearch+"%'"
                        + ")";
              }

              Vector listItem = PstMaterial.listShopingCartUseLocation(start, jmlShowData, whereClause, "", "" + queensLocation.getStandartRateId(), priceType, design, oidLocation);
              html = drawListItem(iCommand, listItem, start, cashCashierId);

              int jmlData = PstMaterial.countShopingCartUseLocation(whereClause, "", "" + queensLocation.getStandartRateId(), priceType, design, oidLocation);

              double tamp = Math.floor(jmlData / jmlShowData);
              double tamp2 = jmlData % jmlShowData;
              if (tamp2 > 0) {
                tamp2 = 1;
              }
              double JumlahHalaman = tamp2 + tamp;

              String attDisFirst = "";
              String attDisNext = "";

              if (showPage == 1) {
                attDisFirst = "disabled";
              }

              if (showPage == JumlahHalaman) {
                attDisNext = "disabled";
              }

              html += ""
                      + "<div class='row'>"
                      + "<input type='hidden' id='totalPaging' value='" + Formater.formatNumber(JumlahHalaman, "") + "'>"
                      + "<input type='hidden' id='paggingPlace' value='" + showPage + "'>"
                      + "<div class='col-md-6'>"
                      + "<label>"
                      + "Page " + showPage + " of " + Formater.formatNumber(JumlahHalaman, "") + ""
                      + "</label>"
                      + "</div>"
                      + "<div class='col-md-6'>"
                      + "<div data-original-title='Status' class='box-tools pull-right' title=''>"
                      + "<div class='btn-group' data-toggle='btn-toggle'>"
                      + "<button " + attDisFirst + " data-type='first' type='button' class='btn btn-default pagings'>"
                      + "<i class='fa fa-angle-double-left'></i>"
                      + "</button>"
                      + "<button id='prevPaging' " + attDisFirst + " data-type='prev' type='button' class='btn btn-default pagings'>"
                      + " <i class='fa fa-angle-left'></i>"
                      + "</button>"
                      + "<button id='nextPaging' " + attDisNext + " data-type='next' type='button' class='btn btn-default pagings'>"
                      + "<i class='fa fa-angle-right'></i>"
                      + "</button>"
                      + "<button " + attDisNext + " data-type='last' type='button' class='btn btn-default pagings'>"
                      + " <i class='fa fa-angle-double-right'></i>"
                      + "</button>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>";
              taxInc = listItem.size();
            } else {
              String oidSpesialRequestFood = PstSystemProperty.getValueByName("SPESIAL_REQUEST_FOOD");
              String oidSpesialRequestBeverage = PstSystemProperty.getValueByName("SPESIAL_REQUEST_BEVERAGE");

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
                      + " " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + "='" + priceType + "'"
                      + " AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + "='" + queensLocation.getStandartRateId() + "'"
                      + " AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + "='" + Long.parseLong(oidSpesialRequestFood) + "'";

              String whereSpBeverage = ""
                      + " " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + "='" + priceType + "'"
                      + " AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + "='" + queensLocation.getStandartRateId() + "'"
                      + " AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + "='" + Long.parseLong(oidSpesialRequestFood) + "'";

              Vector listSpFood = PstPriceTypeMapping.list(0, 1, whereSpFood, "");
              Vector listSpBeverage = PstPriceTypeMapping.list(0, 1, whereSpBeverage, "");

              PriceTypeMapping entPriceTypeMappingSpF = new PriceTypeMapping();
              PriceTypeMapping entPriceTypeMappingSpB = new PriceTypeMapping();

              try {
                entPriceTypeMappingSpF = (PriceTypeMapping) listSpFood.get(0);
              } catch (Exception e) {
              }

              try {
                entPriceTypeMappingSpB = (PriceTypeMapping) listSpBeverage.get(0);
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
                      + "<button type='button' data-oid='" + materialSpFood.getOID() + "' data-name='Special Food' data-price='" + entPriceTypeMappingSpF.getPrice() + "'  class='btn btn-block btn-primary specialOrder col-md-6 btn-flat btn-lg FRM_FIELD_ITEM_NAME'><strong>Special Food</strong></button>"
                      + "</div>"
                      + "<div class='col-md-6'>"
                      + "<button type='button' data-oid='" + materialSpFood.getOID() + "' data-name='Special Beverage' data-price='" + entPriceTypeMappingSpF.getPrice() + "'  class='btn btn-block btn-primary specialOrder col-md-6 btn-flat btn-lg FRM_FIELD_ITEM_NAME' ><strong>Special Beverage</strong></button>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-top:10px;'>"
                      + "<div class='col-md-6'>"
                      + "<button type='button' data-menu='1' data-type='0' data-oidcategory='0' data-design='" + design + "' data-pricetype='" + priceType + "' data-location='" + oidLocation + "' data-parent='" + oidLocationParent2 + "' class='btn btn-block btn-primary col-md-6 btn-flat btn-lg mainMenu' data-for='loadMenu'><strong>Food</strong></button>"
                      + "</div>"
                      + "<div class='col-md-6'>"
                      + "<button type='button' data-menu='1' data-type='1' data-oidcategory='0' data-design='" + design + "' data-pricetype='" + priceType + "' data-location='" + oidLocation + "' data-parent='" + oidLocationParent2 + "' class='btn btn-block btn-primary col-md-6 btn-flat btn-lg mainMenu' data-for='loadMenu'><strong>Beverage</strong></button>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "";
            }
          }

        } else if (loadtype.equals("loaditemlist2")) {
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

          if (showPage == 0) {
            showPage = 1;
          }

          int start = (showPage - 1) * jmlShowData;

          if (multilocation.equals("1")) {
            try {
              queensLocation = PstQueensLocation.fetchExc(oidLocationParent);
            } catch (Exception ex) {
              queensLocation = new QueensLocation();
            }

            oidLocation = queensLocation.getOID();
            priceType = "" + queensLocation.getPriceTypeId();
          } else {

            oidLocation = Long.parseLong(defaultOidLocation);
            priceType = defaultOidPriceType;

            try {
              queensLocation = PstQueensLocation.fetchExc(oidLocation);
            } catch (Exception ex) {
              queensLocation = new QueensLocation();
            }
          }

          //>>> added by dewok 20190130, for price type mapping
          long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
          if (PstBillMain.checkOID(oidBillMain)) {
            try {
              BillMain billMain = PstBillMain.fetchExc(oidBillMain);
              if (PstMemberReg.checkOID(billMain.getCustomerId())) {
                MemberReg memberReg = PstMemberReg.fetchExc(billMain.getCustomerId());
                MemberGroup memberGroup = PstMemberGroup.fetchExc(memberReg.getMemberGroupId());
                priceType = "" + memberGroup.getPriceTypeId();
              } else {
                oidLocation = billMain.getLocationId();
                queensLocation = PstQueensLocation.fetchExc(oidLocation);
                priceType = "" + queensLocation.getPriceTypeId();
              }
            } catch (Exception e) {
            }
          }
          //<<<

          String whereClause = "";

          //>>> added by dewok 20190202 for non active item not included
          whereClause += " p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL] + " != 4";
          //<<<

          if (itemsearch.length() > 0) {
            whereClause += " AND ("
                    + " p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE] + "='" + itemsearch + "' "
                    + " OR p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU] + "='" + itemsearch + "' "
                    + " OR p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME] + " LIKE '%" + itemsearch + "%'"
                    + ")";
          }

          if (categoryId != 0) {
            whereClause += ""
                    + " AND p." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID] + "='" + categoryId + "'";
          }

          Vector listItem = PstMaterial.listShopingCartUseLocation(start, jmlShowData, whereClause, "", "" + queensLocation.getStandartRateId(), priceType, design, oidLocation);
          html = "";

          String fbWrite = "";
          if (type == 0) {
            fbWrite = "Food";
          } else {
            fbWrite = "Beverage";
          }

          String cashierMenuGrid = PstSystemProperty.getValueByName("CASHIER_MENU_GRID");

          if (cashierMenuGrid.equals("1")) {
            if (categoryId != 0) {

              html = ""
                      + "<div id='dynamicPlaceForMenu'>"
                      + "<div class='row' style='margin-bottom:10px;'>"
                      + "<div class='col-md-12'>"
                      + "<div class='btn-group'>"
                      + "<button type ='button' class='btn btn-default mainMenuOutlet' data-for='loadMenu'><i class='fa fa-home'></i>&nbsp;</button>"
                      + "<button data-menu='1' data-type='" + type + "' data-oidcategory='0' data-design='" + design + "' data-pricetype='" + priceType + "' data-location='" + oidLocation + "' data-parent='" + oidLocationParent2 + "' data-for='loadMenu' type ='button' class='btn btn-default mainCategoryOutlet '><i class='fa fa-gg-circle'></i> " + fbWrite + "</button>";
              Vector anotherMenu = new Vector(1, 1);
              anotherMenu = parentMenu(entCategory.getOID(), anotherMenu, oidLocation, priceType, "" + design, type);
              if (anotherMenu.size() > 0) {
                for (int i = anotherMenu.size() - 2; i >= 1; i--) {
                  String getHtml = (String) anotherMenu.get(i);
                  html += getHtml;
                }
              }
              html += ""
                      + "<button type ='button' class='btn btn-default active'><i class='fa fa-gg-circle'></i> " + entCategory.getName() + "</button>"
                      + "</div>"
                      + "</div>"
                      + "</div>";
            } else {
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

          html += drawListItem(iCommand, listItem, start, cashCashierId);

          int jmlData = PstMaterial.countShopingCartUseLocation(whereClause, "", "" + queensLocation.getStandartRateId(), priceType, design, oidLocation);

          double tamp = Math.floor(jmlData / jmlShowData);
          double tamp2 = jmlData % jmlShowData;
          if (tamp2 > 0) {
            tamp2 = 1;
          }
          double JumlahHalaman = tamp2 + tamp;
          if (JumlahHalaman == 0) {
            showPage = 0;
          }

          String attDisFirst = "";
          String attDisNext = "";

          if (showPage <= 1) {
            attDisFirst = "disabled";
          }

          if (showPage == JumlahHalaman) {
            attDisNext = "disabled";
          }

          html += ""
                  + "<div class='row'>"
                  + "<input type='hidden' id='totalPaging' value='" + Formater.formatNumber(JumlahHalaman, "") + "'>"
                  + "<input type='hidden' id='paggingPlace' value='" + showPage + "'>"
                  + "<div class='col-md-6'>"
                  + "<label>"
                  + "Page " + showPage + " of " + Formater.formatNumber(JumlahHalaman, "") + ""
                  + "</label>"
                  + "</div>"
                  + "<div class='col-md-6'>"
                  + "<div data-original-title='Status' class='box-tools pull-right' title=''>"
                  + "<div class='btn-group' data-toggle='btn-toggle'>"
                  + "<button " + attDisFirst + " data-type='first' type='button' class='btn btn-default pagingsSearch'>"
                  + "<i class='fa fa-angle-double-left'></i>"
                  + "</button>"
                  + "<button id='prevPaging' " + attDisFirst + " data-type='prev' type='button' class='btn btn-default pagingsSearch'>"
                  + " <i class='fa fa-angle-left'></i>"
                  + "</button>"
                  + "<button id='nextPaging' " + attDisNext + " data-type='next' type='button' class='btn btn-default pagingsSearch'>"
                  + "<i class='fa fa-angle-right'></i>"
                  + "</button>"
                  + "<button " + attDisNext + " data-type='last' type='button' class='btn btn-default pagingsSearch'>"
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
        } else if (loadtype.equals("loadsearchreturn")) {
          //balanceValue = getBalance;
          String whereClause = "";
          String date1 = "";
          String date2 = "";
          long location = 0;
          String tableName = "";
          int showPage = 1;
          int jmlShowData = 15;

          try {
            date1 = FRMQueryString.requestString(request, "date1");
          } catch (Exception e) {
            date1 = "";
          }
          try {
            date2 = FRMQueryString.requestString(request, "date2");
          } catch (Exception e) {
            date2 = "";
          }

          try {
            location = FRMQueryString.requestLong(request, "location");
          } catch (Exception e) {
            location = 0;
          }

          try {
            tableName = FRMQueryString.requestString(request, "table");
          } catch (Exception e) {

          }

          try {
            showPage = FRMQueryString.requestInt(request, "showPage");
          } catch (Exception e) {
            showPage = 1;
          }

          if (showPage == 0) {
            showPage = 1;
          }

          int start = (showPage - 1) * jmlShowData;

          /*if(oidData.length() != 0){
			whereClause = "AND ("
				+ "billMain."+PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO]+"='"+oidData+"' "
				+ "OR billMain."+PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME]+" LIKE '%"+oidData+"%' "
				+ "OR meja."+ PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER]+"='"+oidData+"' "
				+ "OR room."+ PstRoom.fieldNames[PstRoom.FLD_NAME]+" LIKE '%"+oidData+"%'"
                                + "OR Date(billMain."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+") = '"+oidData+"') "
				+ "AND billMain."+PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]+"='0'";
		    }*/
          if (oidData.length() != 0 || date1.length() != 0 || date2.length() != 0) {
            if (oidData.length() != 0) {
              if (date1.length() != 0 && date2.length() != 0) {
                whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '%" + oidData + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " LIKE '%" + oidData + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " LIKE '%" + oidData + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " LIKE '%" + oidData + "%')"
                        + "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")>= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<='" + date2 + "')"
                        + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
              } else if (date1.length() != 0 && date2.length() == 0) {
                whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '%" + oidData + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " LIKE '%" + oidData + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " LIKE '%" + oidData + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " LIKE '%" + oidData + "%')"
                        + "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")>= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<='" + date1 + "')"
                        + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
              } else if (date1.length() == 0 && date2.length() != 0) {
                whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '%" + oidData + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " LIKE '%" + oidData + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " LIKE '%" + oidData + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " LIKE '%" + oidData + "%')"
                        + "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")>= '" + date2 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<='" + date2 + "')"
                        + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
              } else {
                whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '%" + oidData + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " LIKE '%" + oidData + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " LIKE '%" + oidData + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " LIKE '%" + oidData + "%')"
                        + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
              }
            } else {
              if (date1.length() != 0 && date2.length() != 0) {
                whereClause = ""
                        + "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")>= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<='" + date2 + "')"
                        + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
              } else if (date1.length() != 0 && date2.length() == 0) {
                whereClause = ""
                        + "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")>= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<='" + date1 + "')"
                        + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
              } else if (date1.length() == 0 && date2.length() != 0) {
                whereClause = ""
                        + "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")>= '" + date2 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<='" + date2 + "')"
                        + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
              } else {
                whereClause = "";
              }
            }
          }

          if (location != 0) {
            whereClause += " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID] + "=" + location + "";
          }

          if (!tableName.equals("")) {
            whereClause += " AND meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + tableName + "%' ";
          }
            whereClause += " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS] + " <> 0";

          String order = "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " desc";
          Vector listBillMain = PstCustomBillMain.listOpenBill(start, jmlShowData,
                  "("
                  + "("
                  + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                  + ") OR ("
                  + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                  + ") OR ("
                  + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                  + ")"
                  + ")"
                  + whereClause, order);
          Vector listCount = PstCustomBillMain.listOpenBill(0, 0,
                  "("
                  + "("
                  + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                  + ") OR ("
                  + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                  + ") OR ("
                  + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                  + ")"
                  + ")"
                  + whereClause, order);
          html = drawListSearch(iCommand, listBillMain, oid, "loadsearchreturn");
          int totalData = listCount.size();
          double tamp = Math.floor(totalData / jmlShowData);
          double tamp2 = totalData % jmlShowData;
          if (tamp2 > 0) {
            tamp2 = 1;
          }
          double JumlahHalaman = tamp2 + tamp;
          String attDisFirst = "";
          String attDisNext = "";

          if (showPage == 1) {
            attDisFirst = "disabled";
          }

          if (showPage == JumlahHalaman) {
            attDisNext = "disabled";
          }

          if (listBillMain.size() > 0) {
            html += ""
                    + "<div class='row'>"
                    + "<input type='hidden' id='totalPagingBill' value='" + Formater.formatNumber(JumlahHalaman, "") + "'>"
                    + "<input type='hidden' id='paggingPlaceBIll' value='" + showPage + "'>"
                    + "<input type='hidden' id='nextPageBIll' value='0'>"
                    + "<div class='col-md-6'>"
                    + "<label>"
                    + "Page " + showPage + " of " + Formater.formatNumber(JumlahHalaman, "") + ""
                    + "</label>"
                    + "</div>"
                    + "<div class='col-md-6'>"
                    + "<div data-original-title='Status' class='box-tools pull-right' title=''>"
                    + "<div class='btn-group' data-toggle='btn-toggle'>"
                    + "<button " + attDisFirst + " data-type='first' type='button' class='btn btn-default pagingBill'>"
                    + "<i class='fa fa-angle-double-left'></i>"
                    + "</button>"
                    + "<button id='prevPaging' " + attDisFirst + " data-type='prev' type='button' class='btn btn-default pagingBill'>"
                    + " <i class='fa fa-angle-left'></i>"
                    + "</button>"
                    + "<button id='nextPaging' " + attDisNext + " data-type='next' type='button' class='btn btn-default pagingBill'>"
                    + "<i class='fa fa-angle-right'></i>"
                    + "</button>"
                    + "<button " + attDisNext + " data-type='last' type='button' class='btn btn-default pagingBill'>"
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
        } else if (loadtype.equals("loadListReprintUp")) {
          Vector location_keys = new Vector(1, 1);
          Vector location_values = new Vector(1, 1);

          //ini adalah variabel untuk menampung tipe bussines dari sistem ini (Retail,Restaurant,atau Retail DIstribution)
          String bussinessType = PstSystemProperty.getValueByName("TYPE_OF_BUSINESS");
          long locationId = FRMQueryString.requestLong(request, "location");
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
          html = "<div class='row'>";
          if (multilocation.equals("1")) {
            html += ""
                    + "<div class='col-md-4'>"
                    + ControlCombo.drawBootsratap(FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] + "_REPRINT", null, ""+locationId, location_keys, location_values, "required='required'", "form-control")
                    + "</div>";
          }

          if (multilocation.equals("1")) {
            html += "<div class='col-md-8'>";
          } else {
            html += ""
                    + "<div class='col-md-12'>"
                    + "<input value='0' type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] + "_REPRINT' id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID] + "_OPEN'>";
          }
          html += ""
                  + "<div class='input-group' style='margin-bottom: 2px;'>"
                  + "<div class='input-group-addon'><i class='fa fa-calendar'></i></div>"
                  + "<input id='orSearch1' class='form-control datePicker3' required='' type='text'>"
                  + "<div class='input-group-addon' style='border:none;'><i class='fa fa-minus'></i></div>"
                  + "<div class='input-group-addon' style='border-right:none;'><i class='fa fa-calendar'></i></div>"
                  + "<input id='orSearch2' class='form-control datePicker3' required='' type='text'>"
                  + "</div>"
                  + "</div>";

          html += ""
                  + "</div>"
                  + "<div class='row'>";

          if (bussinessType.equals("2")) {
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
          } else {
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
          html += ""
                  + "</div>";

///////////////////////////////////
/////////////////LOAD LIST REPRINT                     
        } else if (loadtype.equals("loadListReprint")) {
          String typeText = FRMQueryString.requestString(request, "typeText");
          int typeReprint = FRMQueryString.requestInt(request, "typeReprint");
          String showTime = PstSystemProperty.getValueByName("CASHIER_SHOW_DATE_TIME_LOAD_BILL");
          String date1 = "";
          String date2 = "";
          long location = 0;
          String table = "";
          int showPage = 1;
          int jmlShowData = 15;
          try {
            date1 = FRMQueryString.requestString(request, "date1");
          } catch (Exception e) {
            date1 = "";
          }
          try {
            date2 = FRMQueryString.requestString(request, "date2");
          } catch (Exception e) {
            date2 = "";
          }

          try {
            location = FRMQueryString.requestLong(request, "location");
          } catch (Exception e) {
            location = 0;
          }

          try {
            table = FRMQueryString.requestString(request, "table");
          } catch (Exception e) {
            table = "";
          }

          try {
            showPage = FRMQueryString.requestInt(request, "showPage");
          } catch (Exception e) {
            showPage = 1;
          }

          if (showPage == 0) {
            showPage = 1;
          }

          int start = (showPage - 1) * jmlShowData;

          String whereClause = "";

          if (typeReprint < 2) {
            if (typeText.length() != 0 || date1.length() != 0 || date2.length() != 0) {
              if (typeText.length() != 0) {
                if (date1.length() != 0 && date2.length() != 0) {
                  whereClause = "AND ("
                          + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '%" + typeText + "%' "
                          + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " LIKE '%" + typeText + "%' "
                          + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " LIKE '%" + typeText + "%' "
                          + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " LIKE '%" + typeText + "%' )";
                  if (showTime.equals("0")) {
                    whereClause += "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")>= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') ";
                  } else {
                    whereClause += "AND (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " >= '" + date1 + "' AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " <= '" + date2 + "') ";
                  }
                  whereClause += ""
                          + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
                } else if (date1.length() != 0 && date2.length() == 0) {
                  whereClause = "AND ("
                          + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '" + typeText + "' "
                          + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " LIKE '%" + typeText + "%' "
                          + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " LIKE '%" + typeText + "%' "
                          + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " LIKE '%" + typeText + "%' )";
                  if (showTime.equals("0")) {
                    whereClause += "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")>= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date1 + "') ";
                  } else {
                    whereClause += "AND (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ">= '" + date1 + "' AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + "<= '" + date1 + "') ";
                  }
                  whereClause += ""
                          + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
                } else if (date1.length() == 0 && date2.length() != 0) {
                  whereClause = "AND ("
                          + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '%" + typeText + "%' "
                          + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " LIKE '%" + typeText + "%' "
                          + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " LIKE '%" + typeText + "%' "
                          + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " LIKE '%" + typeText + "%' )";
                  if (showTime.equals("0")) {
                    whereClause += "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")>= '" + date2 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') ";
                  } else {
                    whereClause += "AND (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ">= '" + date2 + "' AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + "<= '" + date2 + "') ";
                  }
                  whereClause += ""
                          + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
                } else {
                  whereClause = "AND ("
                          + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '%" + typeText + "%' "
                          + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " LIKE '%" + typeText + "%' "
                          + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " LIKE '%" + typeText + "%' "
                          + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " LIKE '%" + typeText + "%' )"
                          + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
                }
              } else {
                if (date1.length() != 0 && date2.length() != 0) {
                  whereClause = "";
                  if (showTime.equals("0")) {
                    whereClause += "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")>= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') ";
                  } else {
                    whereClause += "AND (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ">= '" + date1 + "' AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + "<= '" + date2 + "') ";
                  }
                  whereClause += " "
                          + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
                } else if (date1.length() != 0 && date2.length() == 0) {
                  whereClause = "";
                  if (showTime.equals("0")) {
                    whereClause += "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")>= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date1 + "') ";
                  } else {
                    whereClause += "AND (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ">= '" + date1 + "' AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + "<= '" + date1 + "') ";
                  }
                  whereClause += " "
                          + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
                } else if (date1.length() == 0 && date2.length() != 0) {
                  whereClause = "";
                  if (showTime.equals("0")) {
                    whereClause += "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")>= '" + date2 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') ";
                  } else {
                    whereClause += "AND (billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ">= '" + date2 + "' AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + "<= '" + date2 + "') ";
                  }
                  whereClause += " "
                          + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='0'";
                } else {
                  whereClause = "";
                }
              }
            }

            if (location != 0) {
              whereClause += " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID] + "=" + location + "";
            }

            if (!table.equals("")) {
              whereClause += "AND meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + table + "%'";
            }

            String order = "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " desc";
            Vector listBillMain = PstCustomBillMain.listOpenBill(start, jmlShowData,
                    "("
                    + "("
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='" + typeReprint + "' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                    + ") OR ("
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='" + typeReprint + "' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                    + ") OR ("
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='" + typeReprint + "' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                    + ")"
                    + ")"
                    + whereClause, order);
            Vector listCount = PstCustomBillMain.listOpenBill(0, 0,
                    "("
                    + "("
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='" + typeReprint + "' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                    + ") OR ("
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='" + typeReprint + "' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                    + ") OR ("
                    + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='" + typeReprint + "' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                    + ")"
                    + ")"
                    + whereClause, order);
            html = drawListReprint(iCommand, listBillMain, "loadsearchreturn", oid);
            int totalData = listCount.size();

            double tamp = Math.floor(totalData / jmlShowData);
            double tamp2 = totalData % jmlShowData;
            if (tamp2 > 0) {
              tamp2 = 1;
            }
            double JumlahHalaman = tamp2 + tamp;

            String attDisFirst = "";
            String attDisNext = "";

            if (showPage == 1) {
              attDisFirst = "disabled";
            }

            if (showPage == JumlahHalaman) {
              attDisNext = "disabled";
            }
            if (listBillMain.size() > 0) {
              html += ""
                      + "<div class='row' style='margin-bottom:10px;'>"
                      + "<input type='hidden' id='totalPagingRe' value='" + Formater.formatNumber(JumlahHalaman, "") + "'>"
                      + "<input type='hidden' id='paggingPlaceRe' value='" + showPage + "'>"
                      + "<input type='hidden' id='nextPaggingRe' value='" + showPage + "'>"
                      + "<div class='col-md-6'>"
                      + "<label>"
                      + "Page " + showPage + " of " + Formater.formatNumber(JumlahHalaman, "") + ""
                      + "</label>"
                      + "</div>"
                      + "<div class='col-md-6'>"
                      + "<div data-original-title='Status' class='box-tools pull-right' title=''>"
                      + "<div class='btn-group' data-toggle='btn-toggle'>"
                      + "<button " + attDisFirst + " data-type='first' type='button' class='btn btn-default pagingRe'>"
                      + "<i class='fa fa-angle-double-left'></i>"
                      + "</button>"
                      + "<button id='prevPaging' " + attDisFirst + " data-type='prev' type='button' class='btn btn-default pagingRe'>"
                      + " <i class='fa fa-angle-left'></i>"
                      + "</button>"
                      + "<button id='nextPaging' " + attDisNext + " data-type='next' type='button' class='btn btn-default pagingRe'>"
                      + "<i class='fa fa-angle-right'></i>"
                      + "</button>"
                      + "<button " + attDisNext + " data-type='last' type='button' class='btn btn-default pagingRe'>"
                      + " <i class='fa fa-angle-double-right'></i>"
                      + "</button>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "";
            }
          }else if(typeReprint == 3){
            String whereData = "t."+PstTransaksi.fieldNames[PstTransaksi.FLD_USECASE_TYPE]+" = "+Transaksi.USECASE_TYPE_KREDIT_ANGSURAN_PAYMENT;
            whereData += " AND ("
                    + "t." + PstTransaksi.fieldNames[PstTransaksi.FLD_KODE_BUKTI_TRANSAKSI] + " LIKE '%" + typeText + "%' "
                    + "OR p." + PstPinjaman.fieldNames[PstPinjaman.FLD_NO_KREDIT] + " LIKE '%" + typeText + "%' "
                    + "OR p." + PstPinjaman.fieldNames[PstPinjaman.FLD_JUMLAH_PINJAMAN] + " LIKE '%" + typeText + "%' "
                    + "OR a." + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + typeText + "%' )";
            if (!date1.isEmpty() || !date2.isEmpty()) {
              whereData += " AND (t." + PstTransaksi.fieldNames[PstTransaksi.FLD_TANGGAL_TRANSAKSI] + " BETWEEN '" + date1 + "' AND '" + date2 + "') ";
            } 
            Vector listData = PstTransaksi.getListTransaksiKredit(0, 0, whereData, "");
            html = drawListReprintPaymentCredit(listData);
          } else {
            if (typeText.length() != 0 || date1.length() != 0 || date2.length() != 0) {
              if (typeText.length() != 0) {
                if (date1.length() != 0 && date2.length() != 0) {
                  whereClause = "AND ("
                          + " pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_CODE] + " like '%" + typeText + "%' "
                          + " OR loc." + PstLocation.fieldNames[PstLocation.FLD_NAME] + " like '%" + typeText + "%' "
                          + " OR cl." + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " like '%" + typeText + "%' "
                          + " AND (Date(pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + ")>='" + date1 + "' "
                          + " AND Date(pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + ")<='" + date2 + "'))";

                } else if (date1.length() != 0 && date2.length() == 0) {
                  whereClause = "AND ("
                          + " pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_CODE] + " like '%" + typeText + "%' "
                          + " OR loc." + PstLocation.fieldNames[PstLocation.FLD_NAME] + " like '%" + typeText + "%' "
                          + " OR cl." + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " like '%" + typeText + "%' "
                          + " AND (Date(pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + ")>='" + date1 + "' "
                          + " AND Date(pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + ")<='" + date1 + "'))";
                } else if (date1.length() == 0 && date2.length() != 0) {
                  whereClause = "AND ("
                          + " pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_CODE] + " like '%" + typeText + "%' "
                          + " OR loc." + PstLocation.fieldNames[PstLocation.FLD_NAME] + " like '%" + typeText + "%' "
                          + " OR cl." + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " like '%" + typeText + "%' "
                          + " AND (Date(pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + ")>='" + date2 + "' "
                          + " AND Date(pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + ")<='" + date2 + "'))";
                } else {
                  whereClause = "AND ("
                          + " pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_CODE] + " like '%" + typeText + "%' "
                          + " OR loc." + PstLocation.fieldNames[PstLocation.FLD_NAME] + " like '%" + typeText + "%' "
                          + " OR cl." + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " like '%" + typeText + "%' )";

                }
              } else {
                if (date1.length() != 0 && date2.length() != 0) {
                  whereClause = ""
                          + " AND (Date(pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + ")>='" + date1 + "' "
                          + " AND Date(pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + ")<='" + date2 + "')";
                } else if (date1.length() != 0 && date2.length() == 0) {
                  whereClause = ""
                          + " AND (Date(pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + ")>='" + date1 + "' "
                          + " AND Date(pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + ")<='" + date1 + "')";
                } else if (date1.length() == 0 && date2.length() != 0) {
                  whereClause = ""
                          + " AND (Date(pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + ")>='" + date2 + "' "
                          + " AND Date(pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + ")<='" + date2 + "')";
                } else {
                  whereClause = "";
                }

              }
            }

            if (location != 0) {
              whereClause += " AND pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_LOCATION_ID] + "=" + location + "";
            }

            if (!table.equals("")) {
              whereClause += "AND meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + table + "%'";
            }

            String order = "pcm." + PstMatCosting.fieldNames[PstMatCosting.FLD_COSTING_DATE] + " desc";
            String where = "" + PstMatCosting.fieldNames[PstMatCosting.FLD_CASH_CASHIER_ID] + "<>0" + " " + whereClause;

            Vector listBillMain = PstMatCosting.listJoinContactLocation(start, jmlShowData, where, order);
            Vector listCount = PstMatCosting.listJoinContactLocation(0, 0, where, order);
            html += drawListReprintFOC(listBillMain);
            html += "";
            int totalData = listCount.size();

            double tamp = Math.floor(totalData / jmlShowData);
            double tamp2 = totalData % jmlShowData;
            if (tamp2 > 0) {
              tamp2 = 1;
            }
            double JumlahHalaman = tamp2 + tamp;

            String attDisFirst = "";
            String attDisNext = "";

            if (showPage == 1) {
              attDisFirst = "disabled";
            }

            if (showPage == JumlahHalaman) {
              attDisNext = "disabled";
            }
            if (listBillMain.size() > 0) {
              html += ""
                      + "<div class='row' style='margin-bottom:10px;'>"
                      + "<input type='hidden' id='totalPagingRe' value='" + Formater.formatNumber(JumlahHalaman, "") + "'>"
                      + "<input type='hidden' id='paggingPlaceRe' value='" + showPage + "'>"
                      + "<input type='hidden' id='nextPaggingRe' value='" + showPage + "'>"
                      + "<div class='col-md-6'>"
                      + "<label>"
                      + "Page " + showPage + " of " + Formater.formatNumber(JumlahHalaman, "") + ""
                      + "</label>"
                      + "</div>"
                      + "<div class='col-md-6'>"
                      + "<div data-original-title='Status' class='box-tools pull-right' title=''>"
                      + "<div class='btn-group' data-toggle='btn-toggle'>"
                      + "<button " + attDisFirst + " data-type='first' type='button' class='btn btn-default pagingRe'>"
                      + "<i class='fa fa-angle-double-left'></i>"
                      + "</button>"
                      + "<button id='prevPaging' " + attDisFirst + " data-type='prev' type='button' class='btn btn-default pagingRe'>"
                      + " <i class='fa fa-angle-left'></i>"
                      + "</button>"
                      + "<button id='nextPaging' " + attDisNext + " data-type='next' type='button' class='btn btn-default pagingRe'>"
                      + "<i class='fa fa-angle-right'></i>"
                      + "</button>"
                      + "<button " + attDisNext + " data-type='last' type='button' class='btn btn-default pagingRe'>"
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
        } else if (loadtype.equals("loadListJoinUp")) {
          html = "";
          html += "<div class='input-group' style='margin-bottom: 2px;'>"
                  + "<div class='input-group-addon'><i class='fa fa-calendar'></i></div>"
                  + "<input id='ojSearch1' class='form-control datePicker4' required='' type='text'>"
                  + "<div class='input-group-addon' style='border:none;'><i class='fa fa-minus'></i></div>"
                  + "<div class='input-group-addon' style='border-right:none;'><i class='fa fa-calendar'></i></div>"
                  + "<input id='ojSearch2' class='form-control datePicker4' required='' type='text'>"
                  + "</div>";
          html += "<div class='input-group'>"
                  + "<input type='text' class='form-control' placeholder='Scan or Search Transactions' id='dataSearchJoin'/>"
                  + "<div class='input-group-addon btn btn-primary' id='btnSearchJoinBill'>"
                  + "<i class='fa fa-search'></i>"
                  + "</div>"
                  + "</div>";
          html += "<input type='hidden' id='oidBillA' value='" + oid + "'>";

///////////////////////////////////
/////////////////////LOAD LIST JOIN
        } else if (loadtype.equals("loadListJoin")) {
          String typeText = FRMQueryString.requestString(request, "typeText");
          long oidBillA = FRMQueryString.requestLong(request, "oidBillA");
          String date1 = "";
          String date2 = "";

          try {
            date1 = FRMQueryString.requestString(request, "date1");
          } catch (Exception e) {
            date1 = "";
          }
          try {
            date2 = FRMQueryString.requestString(request, "date2");
          } catch (Exception e) {
            date2 = "";
          }
          String whereClause = "";
          if (typeText.length() != 0 || date1.length() != 0 || date2.length() != 0) {
            if (typeText.length() != 0) {
              if (date1.length() != 0 && date2.length() != 0) {
                whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%" + typeText + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " like '%" + typeText + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + typeText + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " like '%" + typeText + "%')"
                        + "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') ";
              } else if (date1.length() != 0 && date2.length() == 0) {
                whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%" + typeText + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " like '%" + typeText + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + typeText + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " like '%" + typeText + "%')"
                        + "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date1 + "') ";
              } else if (date1.length() == 0 && date2.length() != 0) {
                whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%" + typeText + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " like '%" + typeText + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + typeText + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " like '%" + typeText + "%')"
                        + "AND (Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date2 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') ";
              } else {
                whereClause = "AND ("
                        + "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%" + typeText + "%' "
                        + "OR billMain." + PstBillMain.fieldNames[PstBillMain.FLD_GUEST_NAME] + " like '%" + typeText + "%' "
                        + "OR meja." + PstTableRoom.fieldNames[PstTableRoom.FLD_TABLE_NUMBER] + " like '%" + typeText + "%' "
                        + "OR room." + PstRoom.fieldNames[PstRoom.FLD_NAME] + " like '%" + typeText + "%')";
              }
            } else {
              if (date1.length() != 0 && date2.length() != 0) {
                whereClause = "AND ("
                        + "(Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') "
                        + ")";
              } else if (date1.length() != 0 && date2.length() == 0) {
                whereClause = "AND ("
                        + "(Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date1 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date1 + "') "
                        + " )";
              } else if (date1.length() == 0 && date2.length() != 0) {
                whereClause = "AND ("
                        + "(Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ") >= '" + date2 + "' AND Date(billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ")<= '" + date2 + "') "
                        + " )";
              } else {
                whereClause = "";
              }
            }
          }
          String order = "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " desc";
          Vector listBillMain = PstCustomBillMain.listOpenBill(0, 15,
                  "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "<>" + oidBillA + " "
                  + whereClause, order);

          html = drawListJoin(iCommand, listBillMain, "loadListJoin", oid);
///////////////////////////////////
/////////////////LOAD LIST JOIN DETAIL
        } else if (loadtype.equals("loadListJoinDetail")) {
          long oidBillA = FRMQueryString.requestLong(request, "oidBillA");

          Vector listBill = PstCustomBillMain.listItemOpenBill(0, 0,
                  "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oid + "'", "");

          BillMain billMain = new BillMain();
          try {
            billMain = PstBillMain.fetchExc(oid);
          } catch (Exception ex) {

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

          html += drawListJoinDetail(iCommand, listBill, oid, null);
          html += ""
                  + "<input type='hidden' id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] + "' value='" + oid + "'>"
                  + "<input type='hidden' id='oidBillA' value='" + oidBillA + "'>"
                  + "</div>"
                  + "</div>";
///////////////////////////////////
/////////////////CHECK PRINT STATUS
        } else if (loadtype.equals("checkPrintStatus")) {
          String where = "";
          Vector listBillPrint = new Vector(1, 1);

          where = "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "=" + oid + " and "
                  + "" + PstBillDetail.fieldNames[PstBillDetail.FLD_STATUS_PRINT] + "=0";
          listBillPrint = PstBillDetail.list(0, 0, where, "");
          html = "" + listBillPrint.size();
///////////////////////////////////
/////////////////LOAD SEARCH MEMBER
        } else if (loadtype.equals("loadsearchmember") || loadtype.equals("loadsearchmembernewbill") || loadtype.equals("loadsearchnewmember")) {
          String strQuery = "";
          String innerJoin = "";
          Vector listMember = new Vector(1, 1);
          Vector listMemberNoLimit = new Vector(1, 1);
          int recortToGet = 10;
          int start = FRMQueryString.requestInt(request, "start");
          String memberCode = FRMQueryString.requestString(request, "filterMemberCode");
          String memberName = FRMQueryString.requestString(request, "filterMemberName");
          String whereClause = "";
          boolean inHouseGuest = false;
          if (cashierString.indexOf("#") != -1) {
            inHouseGuest = true;
            cashierString = cashierString.replace("#", "");
          }

          String enableDutyFree = PstSystemProperty.getValueByName("ENABLE_DUTY_FREE");

          switch (Integer.parseInt(cashierString)) {

            case PstContactClass.CONTACT_TYPE_DOT_COM_COMPANY:
              if (oidData.length() != 0) {
                whereClause = "AND (" + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + oidData + "%' OR " + PstContactList.fieldNames[PstContactList.FLD_COMP_NAME] + " like '%" + oidData + "%' OR " + PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE] + " like '%" + oidData + "%')";
              }
              listMember = PstCustomBillMain.listMemberCredit(0, 15,
                      "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_DOT_COM_COMPANY + "' "
                      + whereClause, "");
              break;

            case PstContactClass.CONTACT_TYPE_COMPANY:
              if (oidData.length() != 0) {
                whereClause = "AND (" + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + oidData + "%' OR " + PstContactList.fieldNames[PstContactList.FLD_COMP_NAME] + " like '%" + oidData + "%' OR " + PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE] + " like '%" + oidData + "%')";
              }
              listMember = PstCustomBillMain.listMemberCredit(0, 15,
                      "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_COMPANY + "' "
                      + whereClause, "");
              break;

            case PstContactClass.CONTACT_TYPE_EMPLOYEE:
              if (oidData.length() != 0) {
                whereClause = "AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + oidData + "%'";
              }
              listMember = PstCustomBillMain.listEmployee(0, 15,
                      "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_EMPLOYEE + "' "
                      + whereClause, "");
              break;

            //GUEST DILEVERY NOT AVAILABLE
            case PstContactClass.CONTACT_TYPE_GUIDE:
              if (oidData.length() != 0) {
                whereClause = "AND (" + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + oidData + "%' OR " + PstContactList.fieldNames[PstContactList.FLD_COMP_NAME] + " like '%" + oidData + "%' OR " + PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE] + " like '%" + oidData + "%')";
              }
              listMember = PstCustomBillMain.listMemberCredit(0, 15,
                      "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_GUIDE + "' "
                      + whereClause, "");
              break;

            case PstContactClass.CONTACT_TYPE_MEMBER:

              if (inHouseGuest == true) {
                if (oidData.length() != 0) {
                  whereClause = "AND (m." + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + oidData + "%' "
                          + "OR k." + PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER] + " LIKE '%" + oidData + "%' "
                          + "OR n." + PstContactList.fieldNames[PstContactList.FLD_COMP_NAME] + " LIKE '%" + oidData + "%')";
                }
                listMember = PstCustomBillMain.listInHouseGuest(0, 15, whereClause, "");
              } else {
                if (oidData.length() != 0) {
                  whereClause = "AND (" + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + oidData + "%' OR " + PstContactList.fieldNames[PstContactList.FLD_COMP_NAME] + " like '%" + oidData + "%' OR " + PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE] + " like '%" + oidData + "%' )";
                }
                String filter = "";
                if (memberCode.length() > 0) {
                  filter += " CONT." + PstContactList.fieldNames[PstContactList.FLD_CONTACT_CODE] + " LIKE '%" + memberCode + "%'";
                }
                if (memberName.length() > 0) {
                  filter += (filter.length() > 0) ? " OR " : "";
                  filter += " CONT." + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + memberName + "%'";
                }
                whereClause += (filter.length() > 0) ? " AND (" + filter + ")" : "";
                listMember = PstCustomBillMain.listMemberCredit(0, 15,
                        "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_MEMBER + "' "
                        + whereClause, "");
              }

              break;

            case PstContactClass.CONTACT_TYPE_TRAVEL_AGENT:
              if (oidData.length() != 0) {
                whereClause = "AND " + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + oidData + "%'";
              }
              listMember = PstCustomBillMain.listMemberCredit(0, 15,
                      "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_TRAVEL_AGENT + "' "
                      + whereClause, "");
              break;

            case PstContactClass.CONTACT_TYPE_DONOR:
              if (oidData.length() != 0) {
                whereClause = "AND ("
                        + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + oidData + "%' "
                        + " OR " + PstContactList.fieldNames[PstContactList.FLD_COMP_NAME] + " LIKE '%" + oidData + "%' "
                        + " OR " + PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE] + " LIKE '%" + oidData + "%' ";
                if (enableDutyFree.equals("1")) {
                  whereClause += " OR " + PstContactList.fieldNames[PstContactList.FLD_PASSPORT_NO] + " LIKE '%" + oidData + "%' ";
                }
                whereClause += ")";
              }
              listMember = PstCustomBillMain.listMemberCredit(0, 15,
                      "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_DONOR + "' "
                      + whereClause, "");
              break;

            //VENDOR NOT AVAILABLE
            default:
              //Cek apakah ada room class atau nggak. Jika ada, berarti sistem kasir integrasi dengan hanoman.
              // Maka yang di select adalah guest
              int getRoomClass = 0;
//              int getRoomClass = PstCustomBillMain.getCount("");
              if (getRoomClass > 0) {
                if (!oidData.equals("OUTLET RSV")) {
                  whereClause = ""
                          + "AND (m." + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + oidData + "%' "
                          + "OR k." + PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER] + " LIKE '%" + oidData + "%' "
                          + "OR n." + PstContactList.fieldNames[PstContactList.FLD_COMP_NAME] + " LIKE '%" + oidData + "%')";
                } else {
                  whereClause = ""
                          + "AND k." + PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER] + " IS NULL ";
                }

                listMember = PstCustomBillMain.listInHouseGuest(start, recortToGet, whereClause, "");
                listMemberNoLimit = PstCustomBillMain.listInHouseGuest(0, 0, whereClause, "");
                inHouseGuest = true;
                searchtype = "guest";
              } else {
                listMember = PstCustomBillMain.listMemberCredit(0, 15,
                        "" + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + oidData + "%' OR " + PstContactList.fieldNames[PstContactList.FLD_COMP_NAME] + " like '%" + oidData + "%' OR " + PstContactList.fieldNames[PstContactList.FLD_TELP_MOBILE] + " like '%" + oidData + "%'", "");
              }

              break;

          }

          if (inHouseGuest == true) {
            double totalPage = Math.ceil(listMemberNoLimit.size() / recortToGet);
            html = drawListSearchInHouseGuest(iCommand, listMember, oid, null, searchtype);

            String firstActive = "";
            String lastActive = "";
            if (start == 0) {
              firstActive = "active";
            }
            html += ""
                    + "<div class='row hidden'>"
                    + "<div class='col-md-12'>"
                    + "<ul class='pagination pull-right'>"
                    + "<li><a href=''><i class='fa fa-angle-double-left'></i>&nbsp;</a></li>"
                    + "<li><a href=''><i class='fa fa-angle-left'></i>&nbsp;</a></li>";

            for (int i = 1; i <= totalPage; i++) {
              int pageActive = (start / recortToGet) + 1;
              String active = "";
              if (i == pageActive) {
                active = "active";
              }
              html += ""
                      + "<li class='" + active + "'><a href=''>" + i + "</a></li>";
            }
            html += ""
                    + "<li><a href=''><i class='fa fa-angle-right'></i>&nbsp;</a></li>"
                    + "<li><a href=''><i class='fa fa-angle-double-right'></i>&nbsp;</a></li>"
                    + "</ul>"
                    + "</div>"
                    + "</div>";
          } else {
            html += "<div class='table-responsive'>";
            html += drawListSearchMember(iCommand, listMember, oidDataSelectedGuest, null, loadtype);
            html += "</div>";
          }
//////////////////////////////////
/////////////////DRAW RE PRINT
        } else if (loadtype.equals("reprint")) {
          String appRoot = FRMQueryString.requestString(request, "appRoot");
          long oidMain = FRMQueryString.requestLong(request, "oidMain");
          int full = FRMQueryString.requestInt(request, "full");
          html = "";
          String isDynamicTemplate = PstSystemProperty.getValueByName("PRINT_DYNAMIC_TEMPLATE");
          String isCashInvoiceSmall = PstSystemProperty.getValueByName("PRINT_INVOICE_CASH_ALWAYS_SMALL");
          BillMain billMain = new BillMain();
          try {
            billMain = PstBillMain.fetchExc(oidMain);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
          if (isDynamicTemplate.equals("1")) {
            if (billMain.getDocType() == 0 && billMain.getTransactionStatus() == 0 && billMain.getTransctionType() == 0) {
              if (isCashInvoiceSmall.equals("1")) {
                html += drawRePrint(oidMain, appRoot, oidMember, full);
              } else {
                html = templateHandler(billMain);
              }
            } else {
              html = templateHandler(billMain);
            }
          } else {
            html += drawRePrint(oidMain, appRoot, oidMember, full);
          }
        } else if (loadtype.equals("reprintGuidePrice")) {
          String appRoot = FRMQueryString.requestString(request, "appRoot");
          long oidMain = FRMQueryString.requestLong(request, "oidMain");
          int full = FRMQueryString.requestInt(request, "full");
          html = "";
          String isDynamicTemplate = PstSystemProperty.getValueByName("PRINT_DYNAMIC_TEMPLATE");
          String isCashInvoiceSmall = PstSystemProperty.getValueByName("PRINT_INVOICE_CASH_ALWAYS_SMALL");
          BillMain billMain = new BillMain();
          try {
            billMain = PstBillMain.fetchExc(oidMain);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
          if (isDynamicTemplate.equals("1")) {
            if (billMain.getDocType() == 0 && billMain.getTransactionStatus() == 0 && billMain.getTransctionType() == 0) {
              if (isCashInvoiceSmall.equals("1")) {
                html += drawRePrintGuidePrice(oidMain, appRoot, oidMember, full);
              } else {
                html = templateHandler(billMain);
              }
            } else {
              html = templateHandler(billMain);
            }
          } else {
            html += drawRePrintGuidePrice(oidMain, appRoot, oidMember, full);
          }
//////////////////////////////////
//////////////////DRAW RE PRINT FOC
        } else if (loadtype.equals("reprintBillFoc")) {
          String appRoot = FRMQueryString.requestString(request, "appRoot");
          long oidMain = FRMQueryString.requestLong(request, "oidMain");
          int full = FRMQueryString.requestInt(request, "full");
          MatCosting matCosting = new MatCosting();
          matCosting = PstMatCosting.fetchExc(oidMain);
          html = "";
          html += drawRePrintFOC(matCosting, appRoot, full);
//////////////////////////////////
/////////////////DRAW RE PRINT PAYMENT
        } else if (loadtype.equals("reprintCreditPayment")) {
          long oidTransaksi = FRMQueryString.requestLong(request, "oidTransaksi");
          html = "";
          html += getPrintPaymentCredit(oidTransaksi);
//////////////////////////////////
/////////////////LOAD PAYMENT FORM
        }else if (loadtype.equals("loadpay")) {

        String useForRaditya = PstSystemProperty.getValueByName("USE_FOR_RADITYA");
          //Cek apakah ada item mandatory yang harus ada
          Vector listMandatoryItem = PstMaterial.list(0, 0, PstMaterial.fieldNames[PstMaterial.FLD_SALES_RULE] + "=" + PstMaterial.SALES_MANDATORY, "");
          Vector listMandatoryItem2 = PstMaterial.list(0, 0, PstMaterial.fieldNames[PstMaterial.FLD_SALES_RULE] + "=" + PstMaterial.SALES_WITH_CONDITION, "");

          boolean canPay = true;
          String oidMandatoryItemNotSelected = "";
          String oidMandatoryItemNotSelected2 = "";
          if (listMandatoryItem.size() > 0) {
            for (int i = 0; i < listMandatoryItem.size(); i++) {
              Material material = (Material) listMandatoryItem.get(i);
              long billDetail = PstBillDetail.getCashBillDetailId(oid, material.getOID());
              if (billDetail == 0) {
                canPay = false;
                oidMandatoryItemNotSelected += "," + material.getOID();
              }
            }
            if (oidMandatoryItemNotSelected.length() > 0) {
              oidMandatoryItemNotSelected = oidMandatoryItemNotSelected.substring(1);
            }
          }

          if (listMandatoryItem2.size() > 0) {
            boolean adaItem = false;
            for (int i = 0; i < listMandatoryItem2.size(); i++) {
              Material material = (Material) listMandatoryItem2.get(i);
              long billDetail = PstBillDetail.getCashBillDetailId(oid, material.getOID());
              if (billDetail != 0) {
                adaItem = true;
              } else {
                oidMandatoryItemNotSelected2 += "," + material.getOID();
              }
            }
            if (!adaItem) {
              canPay = false;
            }
            if (oidMandatoryItemNotSelected2.length() > 0) {
              oidMandatoryItemNotSelected2 = oidMandatoryItemNotSelected2.substring(1);
            }
          }
          if (!canPay) {
            html += "<input type='hidden' value='1' id='mandatory'>"
                    + "<div class='row'>"
                    + "<div class='col-md-12'>";
            if (!oidMandatoryItemNotSelected.equals("")) {
              html += "<h2>All of This Item Must Included in Bill</h2><ul>";
              try {
                String[] arrMandatory = oidMandatoryItemNotSelected.split(",");
                if (arrMandatory.length > 0) {
                  for (int i = 0; i < arrMandatory.length; i++) {
                    try {
                      Material mat = PstMaterial.fetchExc(Long.valueOf(arrMandatory[i]));
                      html += "<li>" + mat.getFullName() + "</li>";
                    } catch (Exception exc) {

                    }
                  }
                  html += "</ul>";
                }
              } catch (Exception exc) {

              }
            }
            if (!oidMandatoryItemNotSelected2.equals("")) {
              html += "<h2>One of This Item Must Included in Bill</h2><ul>";
              try {
                String[] arrMandatory = oidMandatoryItemNotSelected2.split(",");
                if (arrMandatory.length > 0) {
                  for (int i = 0; i < arrMandatory.length; i++) {
                    try {
                      Material mat = PstMaterial.fetchExc(Long.valueOf(arrMandatory[i]));
                      html += "<li>" + mat.getFullName() + "</li>";
                    } catch (Exception exc) {

                    }
                  }
                  html += "</ul>";
                }
              } catch (Exception exc) {

              }
            }
            html += "</div>"
                    + "</div>";
          } else {
            double totalPrice = PstCustomBillMain.getTotalPrice(0, 0,
                    "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oid + "' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'", "");

            Vector listTaxService = PstCustomBillMain.listTaxService(0, 0,
                    "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oid + "'", "");

            Vector listCustomer = PstCustomBillMain.listCustomer(0, 0,
                    "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oid + "' "
                    + "AND (contactClass." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_EMPLOYEE + "' "
                    + "OR contactClass." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_MEMBER + "' "
                    + "OR contactClass." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_DONOR + "')", "");

            if (design == 1 && listCustomer.size() == 0) {
              listCustomer = PstCustomBillMain.listCustomerHanoman(0, 0,
                      "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oid + "' "
                      + " AND (contact.CONTACT_TYPE ='" + PstContactClass.CONTACT_TYPE_EMPLOYEE + "' "
                      + " OR contact.CONTACT_TYPE ='" + PstContactClass.CONTACT_TYPE_MEMBER + "' "
                      + " OR contact.CONTACT_TYPE ='" + PstContactClass.CONTACT_TYPE_DONOR + "')", "");
            }

            int listSpecialItem = PstBillDetail.getCount(PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + oid + "' "
                    + "AND " + PstBillDetail.fieldNames[PstBillDetail.FLD_ITEM_PRICE] + "='1'");
            String customerName = "";
            long customerOid = 0;
            long classId = 0;
            int classType = 0;
            String classIdString = "";

            if (listCustomer.size() != 0) {
              BillMainCostum billMainCostum = (BillMainCostum) listCustomer.get(0);
              classId = billMainCostum.getClassId();
              classIdString = "" + classId;
              customerName = billMainCostum.getGuestName();
              customerOid = billMainCostum.getOID();
              classType = billMainCostum.getClassType();

              //PENGECEKAN APAKAH GUEST IN HOUSE ATAU MEMBER JIKA TERINTEGRASI DENGAN HANOMAN
              if (design == 1) {
                if (classType == PstContactClass.CONTACT_TYPE_MEMBER) {
                  Vector listInHouseTest = PstCustomBillMain.listInHouseGuest(0, 0,
                          " AND m." + PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID] + "='" + customerOid + "'", "");

                  if (listInHouseTest.size() > 0) {
                    classIdString = "#" + classIdString + "";
                  }
                }
              }

              if (classType == PstContactClass.CONTACT_TYPE_EMPLOYEE) {
                try {
                  Employee employee = PstEmployee.fetchExc(billMainCostum.getEmployeeId());
                  customerName = employee.getFullName();
                } catch (Exception ex) {

                }
              }
            }

            BillMainCostum billMain = new BillMainCostum();

            //ADDED BY DEWOK 20191113 FOR GUIDE PRICE
                            if (checkMemberGuidePrice(oid)) {
                  totalPrice = PstCustomBillMain.getTotalGuidePrice(0, 0, " billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + " = '" + oid + "'", "");
                }
            double price = 0;
            double otherExpense = 0;
            double roundValue = 1;
            if (useForRaditya.equals("1")) {
              BillMain bm = PstBillMain.fetchExc(oid);
              otherExpense = bm.getAdminFee() + bm.getShippingFee();
              Category cat = new Category();
              Material mat = new Material();
                totalPrice = 0;
              Vector bd = PstBillDetail.list(0, 0, PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+" = "+bm.getOID(), "");
              for(int i = 0; i < bd.size(); i++){
                Billdetail billdetail = (Billdetail) bd.get(i);
//                mat = PstMaterial.fetchExc(billdetail.getMaterialId());
//                cat = PstCategory.fetchExc(mat.getCategoryId());
//                String formulaCash = PstSystemProperty.getValueByName("CASH_FORMULA");
//                if (checkString(formulaCash, "HPP") > -1) {
//                  formulaCash = formulaCash.replaceAll("HPP", "" + mat.getAveragePrice());
//                }
////                if (checkString(formulaCash, "INCREASE") > -1) {
////                  formulaCash = formulaCash.replaceAll("INCREASE", "" + cat.getKenaikanHarga());
////                }
//                 price = getValue(formulaCash);
//                 price = Math.round(price);
                 price = billdetail.getTotalTax()+ billdetail.getTotalPrice();
                totalPrice += price;
            }
              totalPrice = totalPrice + otherExpense;
            }
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
            if (listTaxService.size() != 0) {
              billMain = (BillMainCostum) listTaxService.get(0);

              discGlobal = (billMain.getDiscPct() / 100) * totalPrice;
              if (billMain.getTaxInc() == PstBillMain.INC_NOT_CHANGEABLE || billMain.getTaxInc() == PstBillMain.INC_CHANGEABLE) {
                //mencari dpp
                double totalPriceExTaxService = 0;
                totalPriceExTaxService = (totalPrice - discGlobal) / ((100 + billMain.getServicePct() + billMain.getTaxPct()) / 100);

                amountService = totalPriceExTaxService * (billMain.getServicePct() / 100);
                amountTax = totalPriceExTaxService * (billMain.getTaxPct() / 100);
              } else {
                amountService = (totalPrice - discGlobal) * (billMain.getServicePct() / 100);
                amountTax = (totalPrice - discGlobal) * (billMain.getTaxPct() / 100);
              }

              taxInc = billMain.getTaxInc();
              if (billMain.getTaxInc() == PstBillMain.INC_NOT_CHANGEABLE) {
                balance = balance - discGlobal;
                readonlyService = "readonly='readonly'";
                readonlyTax = "readonly='readonly'";
                readonlyDisc = "readonly='readonly'";
                incTaxService = "<small>(include tax & service)</small>";
                openDiscunt = "<button type='button' class='btn btn-xs authorize' data-title='OPEN DISCOUNT' data-load-type='opendiscount' style='margin-top:4px;'>Open discount</button>";
              } else if (billMain.getTaxInc() == PstBillMain.NOT_INC_NOT_CHANGEABLE) {
                balance = (totalPrice - discGlobal) + amountService + amountTax;
                readonlyService = "readonly='readonly'";
                readonlyTax = "readonly='readonly'";
                readonlyDisc = "readonly='readonly'";
                incTaxService = "<small>(exclude tax & service)</small>";
                openDiscunt = "<button type='button' class='btn btn-xs authorize' data-title='OPEN DISCOUNT' data-load-type='opendiscount' style='margin-top:4px;'>Open discount</button>";
              } else if (billMain.getTaxInc() == PstBillMain.INC_CHANGEABLE) {
                balance = balance - discGlobal;
                incTaxService = "<small>(include tax & service)</small>";
                openDiscunt = "<button type='button' class='btn btn-xs authorize' data-title='OPEN DISCOUNT' data-load-type='opendiscount' style='margin-top:4px;'>Open discount</button>";
              } else {
                balance = (totalPrice - discGlobal) + amountService + amountTax;
                incTaxService = "<small>(exclude tax & service)</small>";
                readonlyDisc = "readonly='readonly'";
                openDiscunt = "<button type='button' class='btn btn-xs authorize' data-title='OPEN DISCOUNT' data-load-type='opendiscount' style='margin-top:4px;'>Open discount</button>";
              }

              balanceValue = totalPrice;
              //balance = totalPrice+amountTax+amountService;
            }

            String header = "";
            int intType = 0;
            switch (classType) {

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

            if (listSpecialItem == 0) {
              html += ""
                      + "<input type='hidden' name='SIGNATURE_PATH' value='" + defaultPathSignature + "' id='SIGNATURE_PATH'>"
                      + "<input type='hidden' name='IMAGE_STRING' id='IMAGE_STRING'>"
                      + "<input  type='hidden' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] + "' id='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID] + "' value='" + billMain.getOID() + "'>"
                      + "<div class='row'>"
                      + "<div class='col-md-6'>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>";
                      if(useForRaditya.equals("1")){
              html += ""
                      + "<div class='col-md-4'><b>Other Expense</b></div>"
                      + "<div class='col-md-8' style='text-align:right;'>" + Formater.formatNumber(otherExpense, "#,###") + "</div>";
                      }
              html += ""
                      + "<div class='col-md-4'><b>Total</b></div>"
                      + "<div class='col-md-8' style='text-align:right;'>" + incTaxService + " " + Formater.formatNumber(totalPrice, "#,###") + "</div>"
                      + "<input type='hidden' name='dpp' value='0'>"
                      + "</div>"
                      + "</div>"
                      + "</div>";
              html += ""
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-4'><b>Discount</b></div>"
                      + "<div class='col-md-4'>"
                      + "<div class='input-group'>"
                      + "<input type='text' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_DISC_PCT] + "' class='form-control charge' value='" + billMain.getDiscPct() + "' id='discountpercentage' " + readonlyDisc + ">"
                      + "<div class='input-group-addon'>"
                      + "%"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='col-md-4'>"
                      + "<input type='text' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_DISC_GLOBAL] + "' class='form-control chargeImp money' value='" + Formater.formatNumber(discGlobal, "#,###") + "' id='discountnominal' " + readonlyDisc + ">"
                      + openDiscunt
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>";
              double totalsPrice = totalPrice - discGlobal;
              double totalCharge = totalsPrice;
              if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                totalsPrice = totalPrice - discGlobal - amountService - amountTax;
                //totalCharge=totalsPrice+amountService+amountTax;
              }
              html += ""
                      + "<div class='col-md-4'><b>Balance</b></div>"
                      + "<div class='col-md-8' style='text-align:right;' id='balance'>" + Formater.formatNumber(totalsPrice, "#,###") + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-4'><b>Service</b></div>"
                      + "<div class='col-md-4'>"
                      + "<div class='input-group'>"
                      + "<input style='font-size:12px;' type='text' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT] + "' value='" + billMain.getServicePct() + "' class='form-control charge' " + readonlyService + " id='service'>"
                      + "<input  type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_VALUE] + "' value='" + amountService + "' id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_VALUE] + "'>"
                      + "<div class='input-group-addon'>"
                      + "%"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='col-md-4' style='text-align:right;' id='serviceVal'>"
                      + Formater.formatNumber(amountService, "#,###") + ""
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'><b>Total</b></div>"
                      + "<div class='col-md-6' style='text-align:right;' id='totalAfterService'>" + Formater.formatNumber(totalsPrice + amountService, "#,###") + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-4'><b>Tax</b></div>"
                      + "<div class='col-md-4'>"
                      + "<div class='input-group'>"
                      + "<input style='font-size:12px;' type='text' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT] + "' value='" + billMain.getTaxPct() + "' class='form-control charge' " + readonlyTax + " id='tax'>"
                      + "<input type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_VALUE] + "' value='" + amountTax + "' id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_DISC_GLOBAL] + "'>"
                      + "<div class='input-group-addon'>"
                      + "%"
                      + "</div>"
                      + "<input type='hidden' value='" + (balance) + "' id='tempCostumBalance'>"
                      + "</div>"
                      + "</div>"
                      + "<div class='col-md-4' style='text-align:right;' id='taxVal'>"
                      + "" + Formater.formatNumber(amountTax, "#,###") + ""
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'><b>Total</b></div>"
                      + "<div class='col-md-6' style='text-align:right;' id='totalAfterTax'>"
                      + "" + Formater.formatNumber(totalsPrice + amountService + amountTax, "#,###") + ""
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-4'><b>Credit Card Charge</b></div>"
                      + "<div class='col-md-4'>"
                      + "<div class='input-group'>"
                      + "<input type='text' name='creditcardchargepercent' value='0' class='form-control charge' readonly='readonly' id='ccCharge'>"
                      + "<input type='hidden' name='creditcardcharge' value='0' id='creditcardcharge'>"
                      + "<div class='input-group-addon'>"
                      + "%"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='col-md-4' style='text-align:right;' id='ccChargeVal'>"
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
                      + "<b>" + Formater.formatNumber(balance, "#,###") + "</b>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-12'><b>Transaction Note</b></div>"
                      + "<div class='col-md-12'>"
                      + "<textarea name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_NOTES] + "' class='form-control'>" + billMain.getNotes() + "</textarea>"
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
              Vector customer_key = new Vector(1, 1);
              Vector customer_val = new Vector(1, 1);

              customer_key.add("");
              customer_val.add("-- Select --");
              customer_key.add("0");
              customer_val.add("NONE");

              Vector listClass = PstContactClass.list(0, 0,
                      "" + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='7' "
                      + "OR " + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='4' "
                      + "OR " + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='10'",
                      PstContactClass.fieldNames[PstContactClass.FLD_CLASS_NAME] + " ASC");

              int getRoomClass = PstCustomBillMain.getCount("");
              if (listClass.size() != 0) {
                for (int i = 0; i < listClass.size(); i++) {
                  ContactClass contactClass = (ContactClass) listClass.get(i);

                  if (contactClass.getClassType() == 7) {
                    if (design != 0) {
                      customer_key.add("" + contactClass.getOID());
                      customer_val.add("MEMBER");
                      if (getRoomClass > 0) {
                        customer_key.add("#" + contactClass.getOID());
                        customer_val.add("IN HOUSE GUEST");
                      }

                    } else {
                      customer_key.add("" + contactClass.getOID());
                      customer_val.add("" + contactClass.getClassName());
                    }

                  } else {
                    customer_key.add("" + contactClass.getOID());
                    customer_val.add("" + contactClass.getClassName());
                  }

                }
              }
              if (classIdString.length() == 0) {
                classIdString = "0";
              }
              html += ControlCombo.drawBootsratap("member", null, "" + classIdString, customer_key, customer_val, "required='true'", "form-control");
              html += ""
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<input type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] + "' id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CUSTOMER_ID] + "' value='" + customerOid + "'>"
                      + "<input type='hidden' name='CUSTOMER_NAME' id='CUSTOMER_NAME' value='" + customerName + "'>"
                      + "<div id='CONTENT_SUB_PAYMENT_SYSTEM'>";

              if (classType > 0) {
                html += ""
                        + "<div class='row'>"
                        + "<div class='box-body'>"
                        + "<div class='col-md-12'>"
                        + "<div class='col-md-6'><b>" + header + "</b></div>"
                        + "<div class='col-md-6'>"
                        + "<input type='hidden' name='" + FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE] + "' id='" + FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE] + "' value='" + intType + "'>"
                        + "<input type='hidden' name='searchtype' id='searchtype' value='guest'>"
                        + ""
                        + "<div class='input-group'>"
                        + "<input type='text' class='form-control' name='employee' id='employee' required='required' value='" + customerName + "'>"
                        + "<div  id='btnOpenSearchMember' class='input-group-addon btn btn-primary'>"
                        + "<i class='fa fa-search'></i>"
                        + "</div>"
                        + "</div>"
                        + "</div>"
                        + "</div>"
                        + "</div>"
                        + "</div>";
              }

              int transTypeAvail = 7; //value ini harusnya di dapat dari sys prop  TRANSSACTION_TYPE_AVAILABLE

              final int TRANS_TYPE_CASH = 1;
              final int TRANS_TYPE_CREDIT = 2;
              final int TRANS_TYPE_FOC = 4;

              int countAvailable = 0;
              String htmlPayment = "";
              if (TRANS_TYPE_CASH == (transTypeAvail & TRANS_TYPE_CASH)) {
                htmlPayment += "<option value='0' selected >CASH / CC / DEBIT </option>";
                countAvailable += 1;
              }
              if (TRANS_TYPE_CREDIT == (transTypeAvail & TRANS_TYPE_CREDIT)) {
                htmlPayment += "<option value='1'>CREDIT</option>";
                countAvailable += 1;
              }
              if (TRANS_TYPE_FOC == (transTypeAvail & TRANS_TYPE_FOC)) {
                htmlPayment += "<option value='2'>FOC</option>";
                countAvailable += 1;
              }

              html += "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>"
                      + "<b>Transaction Type</b>"
                      + "</div>"
                      + "<div class='col-md-6'>"
                      + "<select name='transaction_type' id='transaction_type' class='form-control'>";

              if (countAvailable > 1) {
                html += "<option value=''>-- Select --</option>";
              }

              html += htmlPayment;

              html += "</select>"
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
                      + "<div id='result' style='display:none;'></div>"
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
                      + "<div id='resultSave' style='display:none;'></div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>";
            } else {
              html = "SPECIAL";
            }

            displayTotal = Formater.formatNumber(balance, "#,###");
            costumBalance = balance;

          }

          //added by dewok 20190220, for guide price
                        if (checkMemberGuidePrice(oid)) {
                returnData.put("RETURN_USE_GUIDE_PRICE", true);
              }

/////////////////////////////////////////////////
/////////////////LOAD PAYMENT TYPE OR MEMBER TYPE
        } else if (loadtype.equals("loadpayment")) {
          balanceValue = getBalance;
          creditCardCharge = 0;
          displayTotal = Formater.formatNumber(costumBalance, "#,###");
          String creditType = PstSystemProperty.getValueByName("CREDIT_TYPE");

          boolean memberCredit = false;
          if (creditType.equals("1")) {
            memberCredit = true;
          }

          if (oidData.equals("0") || oidData.equals("2") || memberCredit) {
            Vector paymentType_key = new Vector(1, 1);
            Vector paymentType_value = new Vector(1, 1);

            paymentType_key.add("");
            paymentType_value.add("-- Select --");

            //ADDED BY DEWOK 20190315, untuk default payment type agar payment type bisa otomatis tanpa dipilih manual
            String defaultPaymentType = "";

            if (oidData.equals("0") || memberCredit) {
              paymentType_key.add("" + PstCustomBillMain.PAYMENT_TYPE_CASH);
              paymentType_value.add("" + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CASH]);

              paymentType_key.add("" + PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD);
              paymentType_value.add("" + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD]);

              //paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_BG);
              //paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_BG]);
              //paymentType_key.add(""+PstCustomBillMain.PAYMENT_TYPE_CHECK);
              //paymentType_value.add(""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CHECK] + " OR " + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_BG]);
              paymentType_key.add("" + PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD);
              paymentType_value.add("" + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD]);

              paymentType_key.add("" + PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR);
              paymentType_value.add("" + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR]);

              paymentType_key.add("" + PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT);
              paymentType_value.add("" + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT]);

              //ADDED BY DEWOK 20190315, untuk default payment type agar payment type bisa otomatis tanpa dipilih manual
              defaultPaymentType = "" + PstCustomBillMain.PAYMENT_TYPE_CASH;
            } else if (oidData.equals("2")) {
              paymentType_key.add("" + PstCustomBillMain.PAYMENT_TYPE_FOC);
              paymentType_value.add("" + PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_FOC]);

              //ADDED BY DEWOK 20190315, untuk default payment type agar payment type bisa otomatis tanpa dipilih manual
              defaultPaymentType = "" + PstCustomBillMain.PAYMENT_TYPE_FOC;
            }

            html = ""
                    + "<div class='col-md-6'>"
                    + "<b>Payment Type</b>"
                    + "</div>"
                    + "<div class='col-md-6'>"
                    + ControlCombo.drawBootsratap("MASTER_" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE], null, "" + defaultPaymentType, paymentType_key, paymentType_value, "required='true'", "form-control")
                    + "</div>";
          }

//////////////////////////////////
/////////////////LOAD LIST PAYMENT TYPE
        } else if (loadtype.equals("loadlistpayment")) {
          balanceValue = getBalance;
          creditCardCharge = 0;
          displayTotal = Formater.formatNumber(costumBalance, "#,###");
          String description = "";
          //if(oidData.equals("0")){
          Vector paymentType_key = new Vector(1, 1);
          Vector paymentType_value = new Vector(1, 1);

          paymentType_key.add("");
          paymentType_value.add("-- Select --");

          String whereClause = "";
          switch (Integer.parseInt(oidData)) {
            case PstCustomBillMain.PAYMENT_TYPE_CASH:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='1'";
              description = "Payment Detail";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='1' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='0'";
              description = "Type Card";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_BG:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='1' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='2'";
              description = "Payment Detail";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_CHECK:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='1' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='0'";
              description = "Payment Detail";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='2'";
              description = "Bank Name";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='4'";

              description = "Payment Detail";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='5'";
              description = "Payment Detail";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_FOC:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='6'";
              description = "Payment Detail";
              break;
          }

          Vector listPaymentSystem = PstPaymentSystem.list(0, 0, whereClause, "");
          if (listPaymentSystem.size() != 0) {
            for (int i = 0; i < listPaymentSystem.size(); i++) {
              PaymentSystem paymentSystem = (PaymentSystem) listPaymentSystem.get(i);
              paymentType_key.add("" + paymentSystem.getOID());
              paymentType_value.add("" + paymentSystem.getPaymentSystem());
            }
          }

          //ADDED BY DEWOK 20190201, untuk default payment type agar payment type bisa otomatis tanpa dipilih manual
          String defaultPaymentSystem = "";
          if (listPaymentSystem.size() == 1) {
            PaymentSystem paymentSystem = (PaymentSystem) listPaymentSystem.get(0);
            defaultPaymentSystem = "" + paymentSystem.getOID();
            returnData.put("RETURN_PAYMENT_AVAILABLE", "1");
          }

          html = ""
                  + "<div class='col-md-6'>"
                  + "<b>" + description + "</b>"
                  + "</div>"
                  + "<div class='col-md-6'>"
                  + ControlCombo.drawBootsratap("" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE], null, "" + defaultPaymentSystem, paymentType_key, paymentType_value, "required='true'", "form-control")
                  + "</div>";
          //}
//////////////////////////////////
/////////////////LOAD LIST PAYMENT FOR MULTI PAYMENT
        } else if (loadtype.equals("loadsubpaymentmulti")) {
          double remain = FRMQueryString.requestDouble(request, "remainMulti");
          String oidPayment = FRMQueryString.requestString(request, "oidMultiPayment");
          creditCardCharge = 0;
          String displayTotalMulti = Formater.formatNumber(remain, "#,###");
          String description = "";
          Vector paymentType_key = new Vector(1, 1);
          Vector paymentType_value = new Vector(1, 1);

          paymentType_key.add("");
          paymentType_value.add("-- Select --");

          String whereClause = "";
          switch (Integer.parseInt(oidPayment)) {
            case PstCustomBillMain.PAYMENT_TYPE_CASH:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='1'";
              description = "Payment Detail";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='1' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='0'";
              description = "Type Card";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_BG:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='1' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='2'";
              description = "Payment Detail";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_CHECK:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='1' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='0'";
              description = "Payment Detail";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='2'";
              description = "Bank Name";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_VOUCHER_REGULAR:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='4'";

              description = "Payment Detail";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_VOUCHER_COMPLIMENT:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='5'";
              break;

            case PstCustomBillMain.PAYMENT_TYPE_FOC:
              whereClause = "" + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "='0' "
                      + "AND " + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "='6'";
              description = "Payment Detail";
              break;
          }

          Vector listPaymentSystem = PstPaymentSystem.list(0, 0, whereClause, "");
          if (listPaymentSystem.size() != 0) {
            for (int i = 0; i < listPaymentSystem.size(); i++) {
              PaymentSystem paymentSystem = (PaymentSystem) listPaymentSystem.get(i);
              paymentType_key.add("" + paymentSystem.getOID());
              paymentType_value.add("" + paymentSystem.getPaymentSystem());
            }
          }
          html = ""
                  + "<div class='col-md-3'>"
                  + "" + description + ""
                  + "</div>"
                  + "<div class='col-md-9'>"
                  + ControlCombo.drawBootsratap("" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_TYPE] + "_MULTI", null, "", paymentType_key, paymentType_value, "required='true'", "form-control payTypes2")
                  + "</div>";

//////////////////////////////////
/////////////////LOAD PAYMENT TYPE
        } else if (loadtype.equals("loadsubpayment")) {
          balanceValue = getBalance;

          try {
            PaymentSystem paymentSystem = PstPaymentSystem.fetchExc(oid);
            creditCardCharge = paymentSystem.getBankCostPercent();
            String cardReader = FRMQueryString.requestString(request, "cardReader");
            double paid = costumBalance + (costumBalance * (creditCardCharge / 100));
            String cssReader = "";
            if (cardReader.equals("0")) {
              cssReader = "";
            } else {
              cssReader = "readonly='readonly'";
            }

            //ADDED BY DEWOK 20191127 FOR GUIDE PRICE
            long billMainId = FRMQueryString.requestLong(request, "BILL_MAIN_ID");
                        boolean useGuidePrice = checkMemberGuidePrice(billMainId);
            double realPay = 0;
                        if (useGuidePrice) {
                            Vector<Billdetail> listDetail = PstBillDetail.list(0, 0, PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + " = " + billMainId, "");
                    for (Billdetail b : listDetail) {
                      realPay += b.getTotalPrice() + b.getTotalSvc() + b.getTotalTax();
                    }
                  }

            if (paymentSystem.isCardInfo() == true
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.getPaymentType() == 0
                    && paymentSystem.isCheckBGInfo() == false) {

              paidValue = paid;
              Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
              Vector currency_key = new Vector(1, 1);
              Vector currency_value = new Vector(1, 1);
              Vector data_rate = new Vector(1, 1);

              if (listCurrency.size() != 0) {
                for (int i = 0; i < listCurrency.size(); i++) {
                  CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                  currency_key.add("" + currencyType.getOID());
                  currency_value.add("" + currencyType.getCode());
                  double rate = PstCurrencyType.getExchangeRate(" CURRENCY_TYPE_ID ='" + currencyType.getOID() + "'");
                  data_rate.add("" + rate + "");
                }
              }

              html = ""
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>Card Name</div>"
                      + "<div class='col-md-6'>"
                      + "<div class='input-group'>"
                      + "<input type='text' class='form-control getaccount' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] + "' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME].trim() + "'  required>"
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
                      + "<div class='col-md-6'><input type='text' class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "'  required></div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>Bank</div>"
                      + "<div class='col-md-6'><input type='text'  class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "' value='" + paymentSystem.getBankName() + "' required></div>"
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
                      + "<b style='font-size: 36px;'>" + Formater.formatNumber(costumBalance, "#,###.##") + "</b>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>"
                      + ControlCombo.drawBootsratapData(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control", data_rate, "rate")
                      + "</div>"
                      + "<div class='col-md-6'>";
              if (useGuidePrice) {
                html += "<input style='font-size: 26px; height: 46px;' type='text' id='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_GUIDE_PRICE] + "' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_GUIDE_PRICE] + "' class='form-control money calc' value='" + Formater.formatNumber(paid, "#,###.##") + "'>";
                html += "<input type='hidden' id='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' value='" + realPay + "'>";
              } else {
                html += "<input style='font-size: 26px; height: 46px;' type='text' id='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' class='form-control money calc' value='" + Formater.formatNumber(paid, "#,###.##") + "'>";
              }
              html += "</div>"
                      + "</div>"
                      + "</div>";

              /////////////////////
              /////////CASH PAYMENT	   
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.getPaymentType() == 1
                    && paymentSystem.isCheckBGInfo() == false) {

              paidValue = 0;
              Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
              Vector currency_key = new Vector(1, 1);
              Vector currency_value = new Vector(1, 1);
              Vector data_rate = new Vector(1, 1);

              if (listCurrency.size() != 0) {
                for (int i = 0; i < listCurrency.size(); i++) {
                  CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                  currency_key.add("" + currencyType.getOID());
                  currency_value.add("" + currencyType.getCode());
                  double rate = PstCurrencyType.getExchangeRate(" CURRENCY_TYPE_ID ='" + currencyType.getOID() + "'");
                  data_rate.add("" + rate + "");
                }
              }
              html += ""
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>"
                      + "</div>"
                      + "<div class='col-md-6'>"
                      + "<b style='font-size: 36px;'>" + Formater.formatNumber(costumBalance, "#,###.##") + "</b>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-2'>"
                      + paymentSystem.getPaymentSystem()
                      + "</div>"
                      + "<div class='col-md-3'>"
                      + ControlCombo.drawBootsratapData(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control", data_rate, "rate")
                      + "</div>"
                      + "<div class='col-md-7'>";
              if (useGuidePrice) {
                html += "<input style='font-size: 26px; height: 46px;' type='text' id='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_GUIDE_PRICE] + "' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_GUIDE_PRICE] + "' class='form-control calc money'>";
                html += "<input type='hidden' id='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' value='" + realPay + "'>";
              } else {
                html += "<input style='font-size: 26px; height: 46px;' type='text' id='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' class='form-control calc money'>";
              }
              html += "</div>"
                      + "</div>"
                      + "</div>";

              /////////////////////////
              //////////VOUCHER PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && (paymentSystem.getPaymentType() == 4 || paymentSystem.getPaymentType() == 5)
                    && paymentSystem.isCheckBGInfo() == false) {

              balanceValue = getBalance;
              paidValue = paidValue;
              html
                      += "<div class='row'>"
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
                      + "<div class='col-md-6'>";
              if (useGuidePrice) {
                html += "<input type='text' style='font-size: 26px; height: 46px;' readonly='readonly' id='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_GUIDE_PRICE] + "' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_GUIDE_PRICE] + "' class='form-control' value='0'>";
                html += "<input type='hidden' id='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' value='" + realPay + "'>";
              } else {
                html += "<input type='text' style='font-size: 26px; height: 46px;' readonly='readonly' id='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' class='form-control' value='0'>";
              }
              html += "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>";

              //////////////////
              ////////PAYMENT BG
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == true
                    && paymentSystem.getPaymentType() == 2
                    && paymentSystem.isCheckBGInfo() == false) {

              paidValue = paid;
              Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
              Vector currency_key = new Vector(1, 1);
              Vector currency_value = new Vector(1, 1);
              Vector data_rate = new Vector(1, 1);

              if (listCurrency.size() != 0) {
                for (int i = 0; i < listCurrency.size(); i++) {
                  CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                  currency_key.add("" + currencyType.getOID());
                  currency_value.add("" + currencyType.getCode());
                  double rate = PstCurrencyType.getExchangeRate(" CURRENCY_TYPE_ID ='" + currencyType.getOID() + "'");
                  data_rate.add("" + rate + "");
                }
              }

              html = ""
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>Bank Name</div>"
                      + "<div class='col-md-6'><input type='text' class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "' value='" + paymentSystem.getBankName() + "' required></div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>Bank Account Number</div>"
                      + "<div class='col-md-6'><input type='text' class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] + "' required></div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>BG Number</div>"
                      + "<div class='col-md-6'><input type='text'  class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "' required></div>"
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
                      + "<b style='font-size: 36px;'>" + Formater.formatNumber(costumBalance, "#,###.##") + "</b>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>"
                      + ControlCombo.drawBootsratapData(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control", data_rate, "rate")
                      + "</div>"
                      + "<div class='col-md-6'>";
              if (useGuidePrice) {
                html += "<input style='font-size: 26px; height: 46px;' type='text' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_GUIDE_PRICE] + "' class='form-control money calc' value='" + Formater.formatNumber(paid, "#,###.##") + "'>";
                html += "<input type='hidden' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' value='" + realPay + "'>";
              } else {
                html += "<input style='font-size: 26px; height: 46px;' type='text' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' class='form-control money calc' value='" + Formater.formatNumber(paid, "#,###.##") + "'>";
              }
              html += "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>";

              //////////////////////
              /////////PAYMENT CHECK
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.getPaymentType() == 0
                    && paymentSystem.isCheckBGInfo() == true) {

              paidValue = paid;
              Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
              Vector currency_key = new Vector(1, 1);
              Vector currency_value = new Vector(1, 1);
              Vector data_rate = new Vector(1, 1);

              if (listCurrency.size() != 0) {
                for (int i = 0; i < listCurrency.size(); i++) {
                  CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                  currency_key.add("" + currencyType.getOID());
                  currency_value.add("" + currencyType.getCode());
                  double rate = PstCurrencyType.getExchangeRate(" CURRENCY_TYPE_ID ='" + currencyType.getOID() + "'");
                  data_rate.add("" + rate + "");
                }
              }

              html = ""
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>Bank Name</div>"
                      + "<div class='col-md-6'><input type='text' class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "' value='" + paymentSystem.getBankName() + "' required></div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>Bank Account Number</div>"
                      + "<div class='col-md-6'><input type='text' class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] + "' required></div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>Cheque Number</div>"
                      + "<div class='col-md-6'><input type='text'  class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "' required></div>"
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
                      + "<b style='font-size: 36px;'>" + Formater.formatNumber(costumBalance, "#,###.##") + "</b>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>"
                      + ControlCombo.drawBootsratapData(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control", data_rate, "rate")
                      + "</div>"
                      + "<div class='col-md-6'>";
              if (useGuidePrice) {
                html += "<input type='text' style='font-size: 26px; height: 46px;' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_GUIDE_PRICE] + "' class='form-control money calc' value='" + Formater.formatNumber(paid, "#,###.##") + "'>";
                html += "<input type='hidden' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' value='" + realPay + "'>";
              } else {
                html += "<input type='text' style='font-size: 26px; height: 46px;' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' class='form-control money calc' value='" + Formater.formatNumber(paid, "#,###.##") + "'>";
              }
              html += "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>";

              ///////////////////////
              //////// FOC PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.getPaymentType() == 6
                    && paymentSystem.isCheckBGInfo() == false) {

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
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.getPaymentType() == 2
                    && paymentSystem.isCheckBGInfo() == false) {

              paidValue = paid;
              Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
              Vector currency_key = new Vector(1, 1);
              Vector currency_value = new Vector(1, 1);
              Vector data_rate = new Vector(1, 1);

              if (listCurrency.size() != 0) {
                for (int i = 0; i < listCurrency.size(); i++) {
                  CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                  currency_key.add("" + currencyType.getOID());
                  currency_value.add("" + currencyType.getCode());
                  double rate = PstCurrencyType.getExchangeRate(" CURRENCY_TYPE_ID ='" + currencyType.getOID() + "'");
                  data_rate.add("" + rate + "");
                }
              }
              //cek system property
              int cardInfo = 0;
              String cardInfoStr = PstSystemProperty.getValueByName("CASHIER_SHOW_DEBIT_CARD_INFO");
              try {
                cardInfo = Integer.parseInt(cardInfoStr);
              } catch (Exception ex) {
                cardInfo = 0;
              }
              html = "";

              if (cardInfo == 1) {

                html += ""
                        + "<div class='row'>"
                        + "<div class='box-body'>"
                        + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Bank Name</div>"
                        + "<div class='col-md-6'><input type='text' class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "' value='" + paymentSystem.getBankName() + "' required></div>"
                        + "</div>"
                        + "</div>"
                        + "</div>"
                        + "<div class='row'>"
                        + "<div class='box-body'>"
                        + "<div class='col-md-12'>"
                        + "<div class='col-md-6'>Card Number</div>"
                        + "<div class='col-md-6'>"
                        + "<div class='input-group'>"
                        + "<input type='text' class='form-control getaccountDebit' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "' required>"
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
                        + "<div class='input-group'>"
                        + "<div class='input-group-addon'>"
                        + "<i class='fa fa-calendar'></i>"
                        + "</div>"
                        + "<input type='text' class='form-control datePicker' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE] + "' readonly='readonly'  required>"
                        + "</div>"
                        + "</div>"
                        + "</div>"
                        + "</div>"
                        + "</div>";
              } else {
                html += ""
                        + " <input type='hidden' value='-' class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "' value='" + paymentSystem.getBankName() + "' required>"
                        + " <input type='hidden' value='-' class='form-control getaccountDebit' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "' required>"
                        + " <input type='hidden' value='" + Formater.formatDate(new Date(), "yyyy-MM-dd") + "' class='form-control datePicker' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE] + "' readonly='readonly'  required>"
                        + "";
              }

              html += ""
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>"
                      + "</div>"
                      + "<div class='col-md-6'>"
                      + "<b style='font-size: 36px;'>" + Formater.formatNumber(costumBalance, "#,###.##") + "</b>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-12'>"
                      + "<div class='col-md-6'>"
                      + ControlCombo.drawBootsratapData(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID], null, "", currency_key, currency_value, "", "form-control", data_rate, "rate")
                      + "</div>"
                      + "<div class='col-md-6'>";
              if (useGuidePrice) {
                html += "<input style='font-size: 26px; height: 46px;' type='text' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_AMOUNT_GUIDE_PRICE] + "' class='form-control money calc' value='" + Formater.formatNumber(paid, "#,###.##") + "'>";
                html += "<input type='hidden' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' value='" + realPay + "'>";
              } else {
                html += "<input style='font-size: 26px; height: 46px;' type='text' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "' class='form-control money calc' value='" + Formater.formatNumber(paid, "#,###.##") + "'>";
              }
              html += "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>";
            }

          } catch (Exception ex) {
            //ex.printStackTrace();
          }
///////////////////////////////////////
/////////////////LOAD SUB MULTI PAYMENT
        } else if (loadtype.equals("loadsubmultipayment")) {
          double remain = FRMQueryString.requestDouble(request, "remainMulti");
          long oidPayment = FRMQueryString.requestLong(request, "oidMultiPayment");
          balanceValue = remain;

          try {
            PaymentSystem paymentSystem = PstPaymentSystem.fetchExc(oidPayment);
            creditCardCharge = paymentSystem.getBankCostPercent();
            //String cardReader = FRMQueryString.requestString(request, "cardReader");
            String cardReader = PstSystemProperty.getValueByName("CASHIER_USE_READERCARD");
            double paid = costumBalance + (costumBalance * (creditCardCharge / 100));
            remain = remain + (remain * (creditCardCharge / 100));
            String cssReader = "";
            if (cardReader.equals("0")) {
              cssReader = "";
            } else {
              cssReader = "readonly='readonly'";
            }
            if (paymentSystem.isCardInfo() == true
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.getPaymentType() == 0
                    && paymentSystem.isCheckBGInfo() == false) {
              paidValue = paid;
              Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
              Vector currency_key = new Vector(1, 1);
              Vector currency_value = new Vector(1, 1);

              if (listCurrency.size() != 0) {
                for (int i = 0; i < listCurrency.size(); i++) {
                  CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                  currency_key.add("" + currencyType.getOID());
                  currency_value.add("" + currencyType.getCode());
                }
              }

              html = ""
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>Card Name</div>"
                      + "<div class='col-md-9'>"
                      + "<div class='input-group'>"
                      + "<input type='text' class='form-control getaccount' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] + "_MULTI' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME].trim() + "'  required>"
                      + "<div class='btn btn-primary input-group-addon' id='ccReScan''>"
                      + "<i class='fa fa-refresh'></i>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>Card No</div>"
                      + "<div class='col-md-9'><input type='text' class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "_MULTI' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "_MULTI'  required></div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>Bank</div>"
                      + "<div class='col-md-9'><input type='text'  class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "_MULTI' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "_MULTI' value='" + paymentSystem.getBankName() + "' required></div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>Validity</div>"
                      + "<div class='col-md-9'>"
                      + "<div class='input-group'>"
                      + "<div class='input-group-addon'>"
                      + "<i class='fa fa-calendar'></i>"
                      + "</div>"
                      + "<input type='text' class='form-control datePicker' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE] + "_MULTI' readonly='readonly' required>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-9'>"
                      + "<b>" + Formater.formatNumber(remain, "#,###.##") + "</b>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-2'>"
                      + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID] + "_MULTI", null, "", currency_key, currency_value, "", "form-control")
                      + "</div>"
                      + "<div class='col-md-7'>"
                      + "<input type='text' style='font-size: 26px; height: 46px;' id='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "_MULTI' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "_MULTI' class='form-control calcMoney' value='" + Formater.formatNumber(paid, "#,###.##") + "'>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-9'>"
                      + "<input type='text' readonly id='calcAmount' name='calcAmount' class='form-control money calc' value='" + Formater.formatNumber(remain, "#,###.##") + "'>"
                      + "</div>"
                      + "</div>";
              /////////////////////
              /////////CASH PAYMENT	   
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.getPaymentType() == 1
                    && paymentSystem.isCheckBGInfo() == false) {
              paidValue = 0;
              Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
              Vector currency_key = new Vector(1, 1);
              Vector currency_value = new Vector(1, 1);

              if (listCurrency.size() != 0) {
                for (int i = 0; i < listCurrency.size(); i++) {
                  CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                  currency_key.add("" + currencyType.getOID());
                  currency_value.add("" + currencyType.getCode());
                }
              }
              html += ""
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-9'>"
                      + "<b>" + Formater.formatNumber(remain, "#,###.##") + "</b>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-2'>"
                      + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID] + "_MULTI", null, "", currency_key, currency_value, "required='true'", "form-control")
                      + "</div>"
                      + "<div class='col-md-7'>"
                      + "<input type='text' id='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "_MULTI' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "_MULTI' class='form-control calcMoney'>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-9'>"
                      + "<input type='text' readonly id='calcAmount' name='calcAmount' class='form-control money calc' value='" + Formater.formatNumber(remain, "#,###.##") + "'>"
                      + "</div>"
                      + "</div>";

              /////////////////////////
              //////////VOUCHER PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && (paymentSystem.getPaymentType() == 4 || paymentSystem.getPaymentType() == 5)
                    && paymentSystem.isCheckBGInfo() == false) {
              balanceValue = getBalance;
              paidValue = paidValue;
              html
                      += "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
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
                      + "<input readonly='readonly' id='FRM_FIELD_PAY_AMOUNT_MULTI' name='FRM_FIELD_PAY_AMOUNT_MULTI' class='form-control calcMoney' value='0' type='text'>"
                      + "</div>"
                      + "</div>";

              //////////////////
              ////////PAYMENT BG
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == true
                    && paymentSystem.getPaymentType() == 2
                    && paymentSystem.isCheckBGInfo() == false) {
              paidValue = paid;
              Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
              Vector currency_key = new Vector(1, 1);
              Vector currency_value = new Vector(1, 1);

              if (listCurrency.size() != 0) {
                for (int i = 0; i < listCurrency.size(); i++) {
                  CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                  currency_key.add("" + currencyType.getOID());
                  currency_value.add("" + currencyType.getCode());
                }
              }

              html = ""
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>Bank Name</div>"
                      + "<div class='col-md-9'><input type='text' class='form-control' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "_MULTI' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "_MULTI' value='" + paymentSystem.getBankName() + "' required></div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>Bank Account Number</div>"
                      + "<div class='col-md-9'><input type='text' class='form-control' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] + "_MULTI' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] + "_MULTI' required></div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>BG Number</div>"
                      + "<div class='col-md-9'><input type='text'  class='form-control' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "_MULTI' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "_MULTI' required></div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>Expired Date</div>"
                      + "<div class='col-md-9'>"
                      + "<div class='input-group'>"
                      + "<div class='input-group-addon'>"
                      + "<i class='fa fa-calendar'></i>"
                      + "</div>"
                      + "<input type='text' class='form-control datePicker' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE] + "_MULTI' required>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-9'>"
                      + "<b>" + Formater.formatNumber(remain, "#,###.##") + "</b>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-2'>"
                      + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID] + "_MULTI", null, "", currency_key, currency_value, "", "form-control")
                      + "</div>"
                      + "<div class='col-md-7'>"
                      + "<input type='text' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "_MULTI' class='form-control calcMoney' value='" + Formater.formatNumber(paid, "#,###.##") + "'>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-9'>"
                      + "<input type='text' readonly id='calcAmount' name='calcAmount' class='form-control money calc' value='" + Formater.formatNumber(remain, "#,###.##") + "'>"
                      + "</div>"
                      + "</div>";
              //////////////////////
              /////////PAYMENT CHECK
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.getPaymentType() == 0
                    && paymentSystem.isCheckBGInfo() == true) {
              paidValue = paid;
              Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
              Vector currency_key = new Vector(1, 1);
              Vector currency_value = new Vector(1, 1);

              if (listCurrency.size() != 0) {
                for (int i = 0; i < listCurrency.size(); i++) {
                  CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                  currency_key.add("" + currencyType.getOID());
                  currency_value.add("" + currencyType.getCode());
                }
              }

              html = ""
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>Bank Name</div>"
                      + "<div class='col-md-9'><input type='text' class='form-control' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "_MULTI' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "_MULTI' value='" + paymentSystem.getBankName() + "' required></div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>Bank Account Number</div>"
                      + "<div class='col-md-9'><input type='text' class='form-control cName' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] + "_MULTI' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NAME] + "_MULTI' required></div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>Cheque Number</div>"
                      + "<div class='col-md-9'><input type='text'  class='form-control' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "_MULTI' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "_MULTI' required></div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>Expired Date</div>"
                      + "<div class='col-md-9'>"
                      + "<div class='input-group'>"
                      + "<div class='input-group-addon'>"
                      + "<i class='fa fa-calendar'></i>"
                      + "</div>"
                      + "<input type='text' class='form-control datePicker' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE] + "_MULTI' required>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-9'>"
                      + "<b>" + Formater.formatNumber(remain, "#,###.##") + "</b>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-2'>"
                      + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID] + "_MULTI", null, "", currency_key, currency_value, "", "form-control")
                      + "</div>"
                      + "<div class='col-md-7'>"
                      + "<input type='text' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "_MULTI' class='form-control calcMoney' value='" + Formater.formatNumber(paid, "#,###.##") + "'>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-9'>"
                      + "<input type='text' readonly id='calcAmount' name='calcAmount' class='form-control money calc' value='" + Formater.formatNumber(remain, "#,###.##") + "'>"
                      + "</div>"
                      + "</div>";

              ///////////////////////
              //////// FOC PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.getPaymentType() == 6
                    && paymentSystem.isCheckBGInfo() == false) {

              html = ""
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>Remark</div>"
                      + "<div class='col-md-9'><textarea name='remarkFoc' class='form-control' style='width:100%'></textarea></div>"
                      + "</div>";
              ///////////////////////
              ///////// DEBIT PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.getPaymentType() == 2
                    && paymentSystem.isCheckBGInfo() == false) {
              paidValue = paid;
              Vector listCurrency = PstCurrencyType.list(0, 0, "", "");
              Vector currency_key = new Vector(1, 1);
              Vector currency_value = new Vector(1, 1);

              if (listCurrency.size() != 0) {
                for (int i = 0; i < listCurrency.size(); i++) {
                  CurrencyType currencyType = (CurrencyType) listCurrency.get(i);
                  currency_key.add("" + currencyType.getOID());
                  currency_value.add("" + currencyType.getCode());
                }
              }
              int cardInfo = 0;
              String cardInfoStr = PstSystemProperty.getValueByName("CASHIER_SHOW_DEBIT_CARD_INFO");
              try {
                cardInfo = Integer.parseInt(cardInfoStr);
              } catch (Exception ex) {
                cardInfo = 0;
              }
              html = "";
              if (cardInfo == 1) {
                html += ""
                        + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                        + "<div class='col-md-3'>Bank Name</div>"
                        + "<div class='col-md-9'><input type='text' class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "_MULTI' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "' value='" + paymentSystem.getBankName() + "' required></div>"
                        + "</div>"
                        + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                        + "<div class='col-md-3'>Card Number</div>"
                        + "<div class='col-md-9'>"
                        + "<div class='input-group'>"
                        + "<input type='text' class='form-control getaccountDebit' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "_MULTI' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "' required>"
                        + "<div class='btn btn-primary input-group-addon' id='debitReScan'>"
                        + "<i class='fa fa-refresh'></i>"
                        + "</div>"
                        + "</div>"
                        + "</div>"
                        + "</div>"
                        + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                        + "<div class='col-md-3'>Expired Date</div>"
                        + "<div class='col-md-9'>"
                        + "<div class='input-group'>"
                        + "<div class='input-group-addon'>"
                        + "<i class='fa fa-calendar'></i>"
                        + "</div>"
                        + "<input type='text' class='form-control datePicker' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE] + "_MULTI' readonly='readonly'  required>"
                        + "</div>"
                        + "</div>"
                        + "</div>";
              } else {
                html += ""
                        + "<input type='hidden' class='form-control' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "_MULTI' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_DEBIT_BANK_NAME] + "' value='-' required>"
                        + "<input type='hidden' class='form-control getaccountDebit' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "_MULTI' id='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_CC_NUMBER] + "' value='-' required>"
                        + "<input type='hidden' class='form-control datePicker' name='" + FrmCashCreditCard.fieldNames[FrmCashCreditCard.FRM_FIELD_EXPIRED_DATE] + "_MULTI' readonly='readonly' value='" + Formater.formatDate(new Date(), "yyyy-MM-dd") + "'  required>"
                        + "";
              }

              html += ""
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-9'>"
                      + "<b >" + Formater.formatNumber(remain, "#,###.##") + "</b>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-2'>"
                      + ControlCombo.drawBootsratap(FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CURR_ID] + "_MULTI", null, "", currency_key, currency_value, "", "form-control")
                      + "</div>"
                      + "<div class='col-md-7'>"
                      + "<input type='text' name='" + FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_PAY_AMOUNT] + "_MULTI' class='form-control calcMoney' value='" + Formater.formatNumber(paid, "#,###.##") + "'>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row' style='margin-left: 0px; margin-right: 0px; margin-top: 7px;'>"
                      + "<div class='col-md-3'>"
                      + "</div>"
                      + "<div class='col-md-9'>"
                      + "<input type='text' readonly id='calcAmount' name='calcAmount' class='form-control money calc' value='" + Formater.formatNumber(remain, "#,###.##") + "'>"
                      + "</div>"
                      + "</div>";
            }
          } catch (Exception ex) {
            //ex.printStackTrace();
          }

//////////////////////////////////////
/////////////////LOAD SUB MEMBER TYPE
        } else if (loadtype.equals("loadsubmembertype")) {
          displayTotal = Formater.formatNumber(costumBalance, "#,###");
          balanceValue = getBalance;
          paidValue = 0;
          creditCardCharge = creditCardCharge;
          String crash = "";
          if (oidData.indexOf("#") != -1) {
            oid = Long.parseLong(oidData.replace("#", ""));
            crash = "#";
          } else {
            oid = oid;
          }

          try {
            ContactClass contactClass = PstContactClass.fetchExc(oid);
            String header = "";
            int intType = 0;
            switch (contactClass.getClassType()) {

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

                if (oidData.indexOf("#") != -1) {
                  header = "IN HOUSE GUEST";
                } else {
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
            html += ""
                    + "<div class='row'>"
                    + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                    + "<div class='col-md-6'><b>" + header + "</b></div>"
                    + "<div class='col-md-6'>"
                    + "<input type='hidden' name='" + FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE] + "' id='" + FrmContactClass.fieldNames[FrmContactClass.FRM_FIELD_CLASS_TYPE] + "' value='" + crash + intType + "'>"
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

          } catch (Exception ex) {

          }

//////////////////////////////////////////
/////////////////CHECK MEMBER CREDIT LIMIT
        } else if (loadtype.equals("checkmemberlimit")) {
          try {
            Contact contact = PstContact.fetchExc(oid);
            String dateNow = Formater.formatDate(new Date(), "yyyy-MM-dd");

            double amountCredit = PstCustomBillMain.checkTotalCredit(0, 0, "contact." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + "='" + oid + "' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1'", "");

            int checkmember = PstCustomBillMain.listMember(0, 0,
                    "contact." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + "='" + oid + "' "
                    + "AND memberReg." + PstMemberRegistrationHistory.fieldNames[PstMemberRegistrationHistory.FLD_VALID_START_DATE] + "<='" + dateNow + "' "
                    + "AND memberReg." + PstMemberRegistrationHistory.fieldNames[PstMemberRegistrationHistory.FLD_VALID_EXPIRED_DATE] + ">='" + dateNow + "'", "");

            if (contact.getMemberCreditLimit() > (costumBalance + amountCredit) && checkmember > 0) {
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
                      + "" + Formater.formatNumber(contact.getMemberCreditLimit(), "#,###")
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
                      + "" + Formater.formatNumber(amountCredit, "#,###")
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
                      + "" + Formater.formatNumber(contact.getMemberCreditLimit() - amountCredit, "#,###")
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
                      + "<input type='text' class='form-control' value='" + contact.getCompName() + "' readonly='readonly'>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "";
            } else {
              html = "FAILED";
            }
          } catch (Exception ex) {

          }

/////////////////////////////////////////////////////
/////////////////CHECK MEMBER CREDIT LIMIT NON MEMBER
        } else if (loadtype.equals("checkmemberlimitnonmember")) {
          try {
            Contact contact = PstContact.fetchExc(oid);
            String dateNow = Formater.formatDate(new Date(), "yyyy-MM-dd");

            double amountCredit = PstCustomBillMain.checkTotalCredit(0, 0, "contact." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + "='" + oid + "' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1'", "");

            int checkmember = PstCustomBillMain.listMember(0, 0,
                    "contact." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + "='" + oid + "' "
                    + "AND memberReg." + PstMemberRegistrationHistory.fieldNames[PstMemberRegistrationHistory.FLD_VALID_START_DATE] + "<='" + dateNow + "' "
                    + "AND memberReg." + PstMemberRegistrationHistory.fieldNames[PstMemberRegistrationHistory.FLD_VALID_EXPIRED_DATE] + ">='" + dateNow + "'", "");

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
                    + "" + Formater.formatNumber(amountCredit, "#,###")
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

          } catch (Exception ex) {

          }
////////////////////////////////////////
/////////////////CEK IN HOUSE GUEST
        } else if (loadtype.equals("checkcreditinhouseguest")) {
          Contact contact = PstContact.fetchExc(oid);
          String dateNow = Formater.formatDate(new Date(), "yyyy-MM-dd");
          String company = contact.getCompName();
          if (company == null || company == "") {
            company = "-";
          }
          double amountCredit = PstCustomBillMain.checkTotalCredit(0, 0, "contact." + PstContact.fieldNames[PstContact.FLD_CONTACT_ID] + "='" + oid + "' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1' "
                  + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1'", "");

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
                  + "" + Formater.formatNumber(amountCredit, "#,###")
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
                  + "<input type='text' class='form-control' value='" + company + "' readonly='readonly'>"
                  + "</div>"
                  + "</div>"
                  + "</div>"
                  + "</div>"
                  + "";
////////////////////////////////////////		    
/////////////////AUTHORIZE OPEN DISCOUNT
        } else if (loadtype.equals("opendiscount")) {

          if (listSpv.size() != 0) {
            html = "SUCCESS";
          } else {
            html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
          }

////////////////////////////////////////////
/////////////////AUTHORIZE CREDIT NON MEMBER
        } else if (loadtype.equals("authorizecredit")) {

          if (listSpv.size() != 0) {
            html = "SUCCESS";
          } else {
            html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
          }

////////////////////////////////////////////
/////////////////AUTHORIZE RETURN BILL
        } else if (loadtype.equals("authorizereturn")) {

          if (listSpv.size() != 0) {
            html = "SUCCESS";
          } else {
            html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
          }
////////////////////////////////////
/////////////////AUTHORIZE PAYMENT USING PASSWORD		
        } else if (loadtype.equals("authPass")) {
          AppUser appUser = new AppUser();
          String userName = FRMQueryString.requestString(request, "username");
          String password = FRMQueryString.requestString(request, "password");
          long oidDoc = FRMQueryString.requestLong(request, "oidDoc");
          String base = FRMQueryString.requestString(request, "base");
          long err = 0;

          String whereAppUser = " " + PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID] + "='" + userName + "' and "
                  + " " + PstAppUser.fieldNames[PstAppUser.FLD_PASSWORD] + "='" + password + "'";

          Vector listAppUser = PstAppUser.listFullObj(0, 0, whereAppUser, "");

          String coverNumber = "";
          try {
            BillMain billMain = PstBillMain.fetchExc(oidDoc);
            coverNumber = billMain.getCoverNumber();
          } catch (Exception ex) {

          }
          if (listAppUser.size() > 0) {
            LogSysHistory logSysHistory = new LogSysHistory();
            PstLogSysHistory pstLogSysHistory = new PstLogSysHistory();
            appUser = (AppUser) listAppUser.get(0);
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

            if (err != 0) {
              html = "0";
            } else {
              html = "1";
            }

          } else {
            html = "1";
          }
          shortMessage = coverNumber;

////////////////////////////////////
/////////////////PRINT BILL TO GUEST
        } else if (loadtype.equals("printguest")) {
          double service = FRMQueryString.requestDouble(request, "FRM_FIELD_SERVICE_PCT");
          double tax = FRMQueryString.requestDouble(request, "FRM_FIELD_TAX_PCT");
          double discPct = FRMQueryString.requestDouble(request, "FRM_FIELD_DISC_PCT");
          String printOpenBillGenerateInvoice = PstSystemProperty.getValueByName("CASHIER_PRINT_OPEN_BILL_TO_INVOICE");
          String note = FRMQueryString.requestString(request, "FRM_FIELD_NOTES");
          String isDynamicTemplate = PstSystemProperty.getValueByName("PRINT_DYNAMIC_TEMPLATE");

          BillMain billMain = new BillMain();

          updateDisc(discPct, request);
          String coverNumber = "";
          if (printOpenBillGenerateInvoice.equals("1")) {
            BillMain entBillMain = new BillMain();
            try {
              entBillMain = PstBillMain.fetchExc(oid);
              coverNumber = entBillMain.getCoverNumber();
              billMain = entBillMain;
            } catch (Exception e) {
            }

            if (entBillMain.getInvoiceNumber().toLowerCase().contains("x")) {
              String newInvoice = "";
              int counter = PstCustomBillMain.getCounterBill(0, 0, "", "") + 1;

              String invoiceNumb = "";
              int counterPlus = 0;
              int countInvoiceNumber = 0;
              int cashierNumber = 0;
              long cashCashier = 0;

              if (entBillMain.getCashCashierId() == 1) {
                cashCashier = FRMQueryString.requestLong(request, "cashcashier");
              } else {
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
              String cashierNumberFormat = "00" + entCashMaster.getCashierNumber() + "." + Formater.formatDate(new Date(), "yyyyMMdd");
              //invoiceNumb = generateInvoiceNumber(cashierNumberFormat);
              //String invcNumber = "";
              Vector listLastInvcNumber = PstBillMain.list(0, 1, "" + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '%" + cashierNumberFormat + "%' "
                      + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " NOT LIKE '%C'", PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " DESC");
              if (listLastInvcNumber.size() > 0) {
                BillMain billMainInv = (BillMain) listLastInvcNumber.get(0);
                String[] number = billMainInv.getInvoiceNo().split("\\.");
                int newNumber = Integer.parseInt(number[2]) + 1;
                String lastNumber = "000" + newNumber;
                lastNumber = lastNumber.substring(lastNumber.length() - 3);
                invoiceNumb = cashierNumberFormat + "." + lastNumber;
              } else {
                invoiceNumb = cashierNumberFormat + ".001";
              }
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

          if (isDynamicTemplate.equals("0")) {
            html = drawPrintNew(transactionType, oid, "" + loginId,
                    taxInc, paymentType, ccName, ccNo, ccBank,
                    ccValid, ccCharge, payAmount, oidCashier,
                    "printguest", request.getContextPath(), oidMember, tax, service, discPct);
          } else {

            html = templateHandler(billMain);
          }

        } else if (loadtype.equals("printguide")) {
          double service = FRMQueryString.requestDouble(request, "FRM_FIELD_SERVICE_PCT");
          double tax = FRMQueryString.requestDouble(request, "FRM_FIELD_TAX_PCT");
          double discPct = FRMQueryString.requestDouble(request, "FRM_FIELD_DISC_PCT");
          String printOpenBillGenerateInvoice = PstSystemProperty.getValueByName("CASHIER_PRINT_OPEN_BILL_TO_INVOICE");
          String note = FRMQueryString.requestString(request, "FRM_FIELD_NOTES");
          String isDynamicTemplate = PstSystemProperty.getValueByName("PRINT_DYNAMIC_TEMPLATE");

          BillMain billMain = new BillMain();

          updateDisc(discPct, request);
          String coverNumber = "";
          if (printOpenBillGenerateInvoice.equals("1")) {
            BillMain entBillMain = new BillMain();
            try {
              entBillMain = PstBillMain.fetchExc(oid);
              coverNumber = entBillMain.getCoverNumber();
              billMain = entBillMain;
            } catch (Exception e) {
            }

            if (entBillMain.getInvoiceNumber().toLowerCase().contains("x")) {
              String newInvoice = "";
              int counter = PstCustomBillMain.getCounterBill(0, 0, "", "") + 1;

              String invoiceNumb = "";
              int counterPlus = 0;
              int countInvoiceNumber = 0;
              int cashierNumber = 0;
              long cashCashier = 0;

              if (entBillMain.getCashCashierId() == 1) {
                cashCashier = FRMQueryString.requestLong(request, "cashcashier");
              } else {
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
              String cashierNumberFormat = "00" + entCashMaster.getCashierNumber() + "." + Formater.formatDate(new Date(), "yyyyMMdd");
              //invoiceNumb = generateInvoiceNumber(cashierNumberFormat);
              //String invcNumber = "";
              Vector listLastInvcNumber = PstBillMain.list(0, 1, "" + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " LIKE '%" + cashierNumberFormat + "%' "
                      + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " NOT LIKE '%C'", PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " DESC");
              if (listLastInvcNumber.size() > 0) {
                BillMain billMainInv = (BillMain) listLastInvcNumber.get(0);
                String[] number = billMainInv.getInvoiceNo().split("\\.");
                int newNumber = Integer.parseInt(number[2]) + 1;
                String lastNumber = "000" + newNumber;
                lastNumber = lastNumber.substring(lastNumber.length() - 3);
                invoiceNumb = cashierNumberFormat + "." + lastNumber;
              } else {
                invoiceNumb = cashierNumberFormat + ".001";
              }
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

          if (isDynamicTemplate.equals("0")) {
            html = drawPrintGuidePrice(transactionType, oid, "" + loginId,
                    taxInc, paymentType, ccName, ccNo, ccBank,
                    ccValid, ccCharge, payAmount, oidCashier,
                    "printguest", request.getContextPath(), oidMember, tax, service, discPct);
          } else {

            html = templateHandler(billMain);
          }

///////////////////////////
//////////////////EDIT ITEM
        } else if (loadtype.equals("edititem")) {
          if (listSpv.size() != 0 || editItemSpv.equals("0")) {
            try {
              String promotionType = PstSystemProperty.getValueByName("CASHIER_PROMOTION_GROUP_TYPE");
              Billdetail billdetail = PstBillDetail.fetchExc(oid);

              Vector listTaxService = PstCustomBillMain.listTaxService(0, 0, "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + billdetail.getBillMainId() + "'", "");

              double balance = (billdetail.getItemPrice() - billdetail.getDisc()) * billdetail.getQty();
              String incTaxService = "";
              double amountService = 0;
              double amountTax = 0;
              double afterService = 0;
              double afterTax = 0;
              double withoutTaxService = 0;
              boolean showPriceBeforeTaxService = false;

              String readonly = "";

              /* cek stok */
              BillMain billMain = new BillMain();
              Material material = new Material();
              try {
                billMain = PstBillMain.fetchExc(billdetail.getBillMainId());
                material = PstMaterial.fetchExc(billdetail.getMaterialId());
              } catch (Exception exc) {
              }

              CashMaster cashMaster = new CashMaster();
              try {
                cashMaster = PstCashMaster.fetchExc(PstCashCashier.fetchExc(billMain.getCashCashierId()).getCashMasterId());
              } catch (Exception exc) {

              }
              double qtyStockStockCard = PstMaterialStock.cekQtyStock(0, billdetail.getMaterialId(), cashMaster.getLocationId());
              double qtyStockRealTime = SessMatCostingStokFisik.qtyMaterialBasedOnOpeningCashier(Long.valueOf(billdetail.getMaterialId()), new Date());
              double qtyStock = qtyStockStockCard - qtyStockRealTime + billdetail.getQty();

              BillMainCostum billMainCostum;
              try {
                billMainCostum = (BillMainCostum) listTaxService.get(0);

                //UPDATED BY DEWOK 20191030, FOR INCLUDE/EXCLUDE TAX & SERVICE
                if (taxInc == PstBillMain.INC_CHANGEABLE || taxInc == PstBillMain.INC_NOT_CHANGEABLE) {
                  double amount = balance / ((billMainCostum.getTaxPct() + billMainCostum.getServicePct() + 100) / 100);
                  amountService = amount * (billMainCostum.getServicePct() / 100);
                  afterService = amount + amountService;
                  amountTax = amount * (billMainCostum.getTaxPct() / 100);
                  afterTax = amount + amountService + amountTax;
                  withoutTaxService = amount;

                  if (billMainCostum.getServicePct() > 0 || billMainCostum.getTaxPct() > 0) {
                    showPriceBeforeTaxService = true;
                  }
                } else {
                  amountService = balance * (billMainCostum.getServicePct() / 100);
                  afterService = balance + amountService;
                  amountTax = balance * (billMainCostum.getTaxPct() / 100);
                  afterTax = balance + amountService + amountTax;
                }

                if (billMainCostum.getTaxInc() == PstBillMain.INC_NOT_CHANGEABLE) {
                  readonly = "readonly='readonly'";
                  incTaxService = "<small>(include tax & service)</small>";
                } else if (billMainCostum.getTaxInc() == PstBillMain.NOT_INC_NOT_CHANGEABLE) {
                  readonly = "readonly='readonly'";
                  incTaxService = "<small>(exclude tax & service)</small>";
                } else if (billMainCostum.getTaxInc() == PstBillMain.INC_CHANGEABLE) {
                  incTaxService = "<small>(include tax & service)</small>";
                } else if (billMainCostum.getTaxInc() == PstBillMain.NOT_INC_CHANGEABLE) {
                  incTaxService = "<small>(exclude tax & service)</small>";
                }

              } catch (Exception ex) {
                billMainCostum = new BillMainCostum();
              }

              String readonlyPrice = "";
              String oidSpesialRequestFood = PstSystemProperty.getValueByName("SPESIAL_REQUEST_FOOD");
              String oidSpesialRequestBeverage = PstSystemProperty.getValueByName("SPESIAL_REQUEST_BEVERAGE");
              String useSpecialRequestSettingBaseTypeMaterial = PstSystemProperty.getValueByName("SPESIAL_REQUEST_SETTING_BASE_TYPE_MATERIAL");
              String oidMaterialIdString = "" + billdetail.getMaterialId();

              if (oidMaterialIdString.equals(oidSpesialRequestBeverage) || oidMaterialIdString.equals(oidSpesialRequestFood)) {
                readonlyPrice = "";
              } else {
                readonlyPrice = "readonly='true'";
              }

              if (useSpecialRequestSettingBaseTypeMaterial.equals("1")) {
                if (material.getEditMaterial() == PstMaterial.EDIT_HARGA || material.getEditMaterial() == PstMaterial.EDIT_HARGA_NAME) {
                  readonlyPrice = "";
                }
              }

              Vector vValue = new Vector(1, 1);
              Vector vKey = new Vector(1, 1);

              vValue.add("0");
              vKey.add("-");

              DataCustom dataCustom = PstDataCustom.getDataCustom(billdetail.getOID(), "" + promotionType, "bill_detail_map_type");
              Vector listType = PstMasterType.list(0, 0,
                      PstMasterType.fieldNames[PstMasterType.FLD_TYPE_GROUP] + "=" + promotionType,
                      PstMasterType.fieldNames[PstMasterType.FLD_MASTER_NAME]);
              if (listType.size() > 0) {
                for (int x = 0; x < listType.size(); x++) {
                  MasterType masterType = (MasterType) listType.get(x);
                  vValue.add("" + masterType.getOID());
                  vKey.add(masterType.getMasterName());
                }
              }

              //cek tipe member group
                            boolean useGuidePrice = checkMemberGuidePrice(billMain.getOID());

              html = ""
                      + "<form id='" + FrmBillDetail.FRM_NAME_BILL_DETAIL + "' name='" + FrmBillDetail.FRM_NAME_BILL_DETAIL + "'>"
                      + "<input type='hidden' name='loadtype' id='loadtype' value='" + loadtype + "'>"
                      + "<input type='hidden' name='command' value='" + Command.SAVE + "'>"
                      + "<input type='hidden' id='stock' value='" + qtyStock + "'>"
                      + "<input type='hidden' id='stockCheck' value='" + cashMaster.getCashierStockMode() + "'>"
                      + "<input type='hidden' id='billMainId' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] + "' value='" + oid + "'>"
                      + "<input type='hidden' name='" + billMainJSON.fieldNames[billMainJSON.FLD_TAX_INC] + "' id='taxInc' value='" + billMainCostum.getTaxInc() + "'>"
                      + "<input type='hidden' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CUSTOME_PACK_BILLING_ID] + "' value='" + billdetail.getCutomePackBillingId() + "'>"
                      + "<input type='hidden' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CUSTOME_SCHEDULE_ID] + "' value='" + billdetail.getCostumeScheduleId() + "'>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'>Item Name</div>"
                      + "<div class='col-md-9'><input id='materialNames' type='text' name ='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] + "' value='" + billdetail.getItemName() + "' class='form-control' " + readonlyPrice + "></div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'>Quantity</div>"
                      + "<div class='col-md-9'><input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] + "' id='FRM_FIELD_QTY2' value='" + billdetail.getQty() + "' class='form-control itemChange'></div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'>Item Price</div>"
                      + "<div class='col-md-9'>"
                      + incTaxService + "<br>"
                      + "<input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] + "' id='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] + "' value='" + billdetail.getItemPrice() + "' class='form-control itemChange' " + readonlyPrice + ">"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'></div>"
                      + "<div class='col-md-4'>Total</div>"
                      + "<div class='col-md-5' style='text-align:right;' id='totalPrice'>"
                      + Formater.formatNumber((billdetail.getItemPrice() * billdetail.getQty()), "#,###")
                      + "</div>"
                      + "</div>"
                      + "</div>";

              if (useGuidePrice) {
                html += ""
                        + "<div class='row'>"
                        + "<div class='box-body'>"
                        + "<div class='col-md-3'>Guide Price</div>"
                        + "<div class='col-md-9'>"
                        + "<input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_GUIDE_PRICE] + "' class='form-control' value='" + billdetail.getGuidePrice() + "'>"
                        + "</div>"
                        + "</div>"
                        + "</div>";
              }

              html += ""
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'>Promotion</div>"
                      + "<div class='col-md-9'>" + ControlCombo.draw("PROMOTION_BILL_DETAIL", "form-control", null, "" + dataCustom.getDataValue(), vValue, vKey, null) + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'>Discount Type</div>"
                      + "<input type='hidden' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_TYPE] + "' id='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_TYPE] + "' value='" + billdetail.getDiscType() + "'>"
                      + "<div class='col-md-9'><span id='discountTypeText'>" + PstDiscountQtyMapping.typeDiscount[billdetail.getDiscType()] + "</span></div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'>Item Discount</div>"
                      + "<div class='col-md-3'>"
                      + "<div class='input-group'>"
                      + "<input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] + "' id='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] + "' value='" + billdetail.getDiscPct() + "' class='form-control itemChange'>"
                      + "<div class='input-group-addon'>%</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='col-md-3'>"
                      + "<div class='input-group'>"
                      + "<div class='input-group-addon'>Rp</div>"
                      + "<input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] + "' id='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] + "' value='" + billdetail.getDisc() + "' class='form-control itemChange '>"
                      + "</div>"
                      + "</div>"
                      + "<div class='col-md-3'>"
                      + "<div class='input-group'>"
                      + "<div class='input-group-addon'>~</div>"
                      + "<input type='text' readonly id='jumlahDiskon' value='" + billdetail.getDisc() * billdetail.getQty() + "' class='form-control itemChange '>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'></div>"
                      + "<div class='col-md-4'>Total</div>"
                      + "<div class='col-md-5' style='text-align:right;' id='afterDiscount'>"
                      + Formater.formatNumber(balance, "#,###")
                      + "</div>"
                      + "</div>"
                      + "</div>";

              if (showPriceBeforeTaxService) {
                html += ""
                        + "<div class='row'>"
                        + "<div class='box-body'>"
                        + "<div class='col-md-3'></div>"
                        + "<div class='col-md-4'>Total without tax & service</div>"
                        + "<div class='col-md-5' style='text-align:right;' id='withoutTaxServis'>" + Formater.formatNumber(withoutTaxService, "#,###") + "</div>"
                        + "</div>"
                        + "</div>";
              }

              html += ""
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'>Item Service</div>"
                      + "<div class='col-md-4'>"
                      + "<div class='input-group'>"
                      + "<input type='text' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT] + "' id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT] + "' value='" + billMainCostum.getServicePct() + "' class='form-control itemChange' " + readonly + ">"
                      + "<div class='input-group-addon'>%</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='col-md-5'>"
                      + "<input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_SERVICE] + "' id='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_SERVICE] + "' value='" + amountService + "' class='form-control' readonly='readonly'>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'></div>"
                      + "<div class='col-md-4'>Total</div>"
                      + "<div class='col-md-5' style='text-align:right;' id='afterService'>"
                      + Formater.formatNumber(afterService, "#,###")
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'>Item Tax</div>"
                      + "<div class='col-md-4'>"
                      + "<div class='input-group'>"
                      + "<input type='text' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT] + "' id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT] + "' value='" + billMainCostum.getTaxPct() + "' class='form-control itemChange' " + readonly + ">"
                      + "<div class='input-group-addon'>%</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='col-md-5'>"
                      + "<input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_TAX] + "' id='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_TAX] + "' value='" + amountTax + "' class='form-control' readonly='readonly'>"
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'></div>"
                      + "<div class='col-md-4'>Total</div>"
                      + "<div class='col-md-5' style='text-align:right;' id='total'>"
                      + Formater.formatNumber(afterTax, "#,###")
                      + "</div>"
                      + "</div>"
                      + "</div>"
                      + "<div class='row'>"
                      + "<div class='box-body'>"
                      + "<div class='col-md-3'>Note</div>"
                      + "<div class='col-md-9'>"
                      + "<textarea class='form-control' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_NOTE] + "'>" + billdetail.getNote() + "</textarea>"
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
            } catch (Exception ex) {
              html = "" + ex.getMessage().toString();
            }
          } else {
            html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
          }
          ////////////////////
          /////////EDIT ITEM 2
        } else if (loadtype.equals("edititem2")) {
          html = getEditItemForm(request, billMainJSON);
          ////////////////////
          /////////MOVE BILL
        } else if (loadtype.equals("movebill")) {
          if (listSpv.size() != 0) {
            String randNumber = Formater.formatDate(new Date(), "yyyyMMddkkmmss");

            BillMain billMain;
            try {
              billMain = PstBillMain.fetchExc(oid);
            } catch (Exception ex) {
              billMain = new BillMain();
            }

            html = ""
                    + "<div id='itemmovebill'>"
                    + "<input type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID] + "' id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_CASHIER_ID] + "' value='" + oidCashier + "'>"
                    + "<input type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] + "' id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] + "' value='" + oid + "'>"
                    + "<input type='hidden' name='loadtype' id='loadtype'>"
                    + "<input type='hidden' name='command' value='" + Command.SAVE + "'>"
                    + "<input type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME] + "' id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_GUEST_NAME] + "' value='" + billMain.getGuestName() + " " + randNumber + "'>";

            Vector listBill = PstCustomBillMain.listItemOpenBill(0, 0,
                    "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oid + "' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                    + "AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'", "");

            html += drawListCancel(iCommand, listBill, oidCashier, null);
            html += "</div>";

          } else {
            html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
          }

          ////////////////////////////
          /////////////////CANCEL ITEM
        } else if (loadtype.equals("cancelbill")) {
          if (listSpv.size() != 0) {
            BillMain billMain;
            try {
              billMain = PstBillMain.fetchExc(oid);
            } catch (Exception ex) {
              billMain = new BillMain();
            }
            html = ""
                    + "<form id='" + FrmBillMain.FRM_NAME_BILL_MAIN + "_CANCEL'>"
                    + "<input type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] + "' value='" + oid + "'>"
                    + "<input type='hidden' name='loadtype' value='" + loadtype + "'>"
                    + "<input type='hidden' name='command' value='" + Command.SAVE + "'>"
                    + "<div class='row'>"
                    + "<div class='box-body'>"
                    + "<div class='col-md-12'>"
                    + "Notes"
                    + "<textarea name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_NOTES] + "' class='form-control'>" + billMain.getNotes() + "</textarea>"
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
          } else {
            html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
          }

          ////////////////////////////
          /////////////////DELETE ITEM (SHOW NOTE)
        } else if (loadtype.equals("delitem")) {
          if (listSpv.size() != 0) {
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
            appUser = (AppUser) listSpv.get(0);
            html = ""
                    + "<form id='" + FrmBillDetail.FRM_NAME_BILL_DETAIL + "' name='" + FrmBillDetail.FRM_NAME_BILL_DETAIL + "'>"
                    + "<input type='hidden' name='loadtype' value='delitemproccess'>"
                    + "<input type='hidden' name='command' value='" + Command.SAVE + "'>"
                    + "<input type='hidden' name='spvName' value='" + appUser.getLoginId() + "'>"
                    + "<input type='hidden' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] + "' value='" + oid + "'>"
                    + "<div class='row'>"
                    + "<div class='box-body'>"
                    + "<div class='col-md-3'>Item Name</div>"
                    + "<div class='col-md-9'><b>" + billdetail.getItemName() + "</b></div>"
                    + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                    + "<div class='box-body'>"
                    + "<div class='col-md-3'>Note</div>"
                    + "<div class='col-md-9'>"
                    + "<textarea id='noteDeleteItem' required='required' class='form-control' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_NOTE] + "'></textarea>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "<div class='row'></div>"
                    + "<div class='row'>"
                    + "<div class='box-body'>"
                    + "<div class='col-md-6'>"
                    + "<button type='button' class='btn btn-danger' data-dismiss='modal'>"
                    + "<i class='fa fa-close'></i> Cancel"
                    + "</button>"
                    + "</div>"
                    + "<div class='col-md-6'>"
                    + "<button type='submit' class='btn btn-primary pull-right' id='btnDeleteItem'>"
                    + "<i class='fa fa-check'></i> Continue"
                    + "</button>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "</form>";

          } else {
            html = "<i class='fa fa-exclamation'></i> Invalid Supervisor Username or Password";
          }
        } else if (loadtype.equals("getguestdetail")) {
          long oidBills = FRMQueryString.requestLong(request, "oidBillMain");
          BillMain entBillMain = new BillMain();
          ContactList contactList = new ContactList();
          try {
            entBillMain = PstBillMain.fetchExc(oidBills);
            contactList = PstContactList.fetchExc(entBillMain.getCustomerId());
          } catch (Exception e) {
          }
          String noTelp = "";
          if (contactList.getTelpMobile().length() > 0) {
            noTelp = contactList.getTelpMobile();
          } else if (contactList.getHomeMobilePhone().length() > 0) {
            noTelp = contactList.getHomeMobilePhone();
          }
          html = "" + entBillMain.getGuestName() + "-" + noTelp + "";

        } else if (loadtype.equals("checktable")) {
          Vector listTable = PstTableRoom.list(0, 0,
                  "" + PstTableRoom.fieldNames[PstTableRoom.FLD_ROOM_ID] + "='" + oidRoom + "'", "");

          if (listTable.size() != 0) {
            for (int i = 0; i < listTable.size(); i++) {
              TableRoom tableRoom = (TableRoom) listTable.get(i);
              String whereTableBill = PstBillMain.fieldNames[PstBillMain.FLD_TABLE_ID] + "='" + tableRoom.getOID() + "'";
              int totalOpenBill = PstBillMain.getCountOpenBillFromTable(whereTableBill);
              if (totalOpenBill == 0) {
                html += "<option value='" + tableRoom.getOID() + "'>" + tableRoom.getTableNumber() + "</option>";
              }

            }
          }
        } else if (loadtype.equals("checkroom")) {
          //cek dulu, apakah lokasi ini menggunakan table 
          String whereLocation = " " + PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID] + "=" + oidMultiLocation + "";
          Vector listLocation = PstLocation.list(0, 0, whereLocation, "");
          Location location = new Location();
          long oidRoomFirst = 0;
          if (listLocation.size() > 0) {
            location = (Location) listLocation.get(0);
          }
          if (location.getLocationUsedTable() != 0) {
            Vector listRoom = PstRoom.list(0, 0, PstRoom.fieldNames[PstRoom.FLD_LOCATION_ID] + "='" + oidMultiLocation + "'", "");
            if (listRoom.size() != 0) {
              html += "<option value='0'>--Select--</option>";
              for (int i = 0; i < listRoom.size(); i++) {
                Room room = (Room) listRoom.get(i);
                if (oidRoomFirst == 0) {
                  oidRoomFirst = room.getOID();
                }
                html += "<option value='" + room.getOID() + "'>" + room.getName() + "</option>";
              }
            }

          } else {
            html += "0";
          }

        } else if (loadtype.equals("ctListTool")) {
          html = "";
          html = drawCustomerCreditTool();
        } else if (loadtype.equals("checklocation")) {
          long idBill = cashier;
          BillMain billMain = new BillMain();
          try {
            billMain = PstBillMain.fetchExc(idBill);
          } catch (Exception e) {
          }
          html = String.valueOf(billMain.getLocationId());
        } else if (loadtype.equals("checkDepartementOutlet")) {
          long oIdDepartement = FRMQueryString.requestLong(request, "oidDepartement");
          long oIdOutlet = FRMQueryString.requestLong(request, "oidOutlet");

          Location entLocation = new Location();
          entLocation = PstLocation.fetchExc(oIdOutlet);
          long depOutlet = entLocation.getDepartmentId();

          if (depOutlet == oIdDepartement) {
            html = "1";
          } else {
            html = "0";
          }
        } else if (loadtype.equals("ctList")) {
          html = "";
          String typeText = FRMQueryString.requestString(request, "typeText");

          Vector listMember = new Vector(1, 1);
          String whereClause = "";
          boolean inHouseGuest = false;
          if (cashierString.indexOf("#") != -1) {
            inHouseGuest = true;
            cashierString = cashierString.replace("#", "");
          }

          switch (Integer.parseInt(cashierString)) {

            case PstContactClass.CONTACT_TYPE_DOT_COM_COMPANY:
              if (oidData.length() != 0) {
                whereClause = "AND " + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + typeText + "%'";
              }
              listMember = PstCustomBillMain.listMemberCredit(0, 15,
                      "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_DOT_COM_COMPANY + "' "
                      + whereClause, "");
              break;

            case PstContactClass.CONTACT_TYPE_COMPANY:
              if (oidData.length() != 0) {
                whereClause = "AND " + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + typeText + "%'";
              }
              listMember = PstCustomBillMain.listMemberCredit(0, 15,
                      "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_COMPANY + "' "
                      + whereClause, "");
              break;

            case PstContactClass.CONTACT_TYPE_EMPLOYEE:
              if (oidData.length() != 0) {
                whereClause = "AND " + PstEmployee.fieldNames[PstEmployee.FLD_FULL_NAME] + " LIKE '%" + typeText + "%'";
              }
              listMember = PstCustomBillMain.listEmployee(0, 15,
                      "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_EMPLOYEE + "' "
                      + whereClause, "");
              break;

            //GUEST DILEVERY NOT AVAILABLE
            case PstContactClass.CONTACT_TYPE_GUIDE:
              if (oidData.length() != 0) {
                whereClause = "AND " + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + typeText + "%'";
              }
              listMember = PstCustomBillMain.listMemberCredit(0, 15,
                      "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_GUIDE + "' "
                      + whereClause, "");
              break;

            case PstContactClass.CONTACT_TYPE_MEMBER:

              if (inHouseGuest == true) {
                if (oidData.length() != 0) {
                  whereClause = "AND (m." + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + typeText + "%' "
                          + "OR k." + PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER] + " LIKE '%" + typeText + "%' "
                          + "OR n." + PstContactList.fieldNames[PstContactList.FLD_COMP_NAME] + " LIKE '%" + typeText + "%')";
                }
                listMember = PstCustomBillMain.listInHouseGuest(0, 15, whereClause, "");
              } else {
                if (oidData.length() != 0) {
                  whereClause = "AND " + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + typeText + "%'";
                }
                listMember = PstCustomBillMain.listMemberCredit(0, 15,
                        "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_MEMBER + "' "
                        + whereClause, "");
              }

              break;

            case PstContactClass.CONTACT_TYPE_TRAVEL_AGENT:
              if (oidData.length() != 0) {
                whereClause = "AND " + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + typeText + "%'";
              }
              listMember = PstCustomBillMain.listMemberCredit(0, 15,
                      "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_TRAVEL_AGENT + "' "
                      + whereClause, "");
              break;

            case PstContactClass.CONTACT_TYPE_DONOR:
              if (oidData.length() != 0) {
                whereClause = "AND " + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + typeText + "%'";
              }
              listMember = PstCustomBillMain.listMemberCredit(0, 15,
                      "CLS." + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "='" + PstContactClass.CONTACT_TYPE_DONOR + "' "
                      + whereClause, "");
              break;

            //VENDOR NOT AVAILABLE
            default:
              whereClause = "" + PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME] + " LIKE '%" + typeText + "%'";
              listMember = PstCustomBillMain.listMemberCredit(0, 15, whereClause, "");

              break;

          }
          html = drawCustomerCreditTemporary(listMember);
        } else if (loadtype.equals("searchTransferDoc")) {
          String startDate = FRMQueryString.requestString(request, "START_DATE");
          String endDate = FRMQueryString.requestString(request, "END_DATE");

          Location loc = new Location();
          try {
            loc = PstLocation.fetchExc(oidLocationParent);
          } catch (Exception exc) {
          }

          String whereClause = PstMatDispatch.fieldNames[PstMatDispatch.FLD_DISPATCH_DATE] + " BETWEEN '" + startDate + " 00:00:00' AND "
                  + "'" + endDate + " 23:59:00' AND (" + PstMatDispatch.fieldNames[PstMatDispatch.FLD_LOCATION_ID] + "=" + loc.getParentLocationId()
                  + " OR " + PstMatDispatch.fieldNames[PstMatDispatch.FLD_LOCATION_ID] + "=" + loc.getOID() + ")";
          Vector listDf = PstMatDispatch.list(0, 0, whereClause, "");
          html = drawTransferList(listDf);
        } else if (loadtype.equals("viewTransferDoc")) {
          long oidMatDisItem = FRMQueryString.requestLong(request, "MATERIAL_DISPATCH_ITEM_ID");
          long oidMatDispatch = FRMQueryString.requestLong(request, "MATERIAL_DISPATCH_ID");
          MatDispatch dis = new MatDispatch();
          try {
            dis = PstMatDispatch.fetchExc(oidMatDispatch);
          } catch (Exception exc) {

          }
          Vector listMatDispatchItem = PstMatDispatchItem.list(0, 0, oidMatDispatch);
          html = "<input type='hidden' id='matDisCode' value='" + dis.getDispatchCode() + "'>"
                  + "<input type='hidden' id='matDisId' value='" + oidMatDispatch + "'>";
          html += drawTransferItem(listMatDispatchItem, Command.ADD, oidLocationParent, dis.getDispatchStatus(), oidMatDisItem);
        } else if (loadtype.equals("sendEmailSupplier")) {
          String shoppingBagCategory = PstSystemProperty.getValueByName("CASHIER_SHOPPING_BAG_CATEGORY_ID");
          long supplierId = FRMQueryString.requestLong(request, "SUPPLIER_ID");
          String date = FRMQueryString.requestString(request, "DATE");
          String strLocationId = FRMQueryString.requestString(request, "LOCATION_ID");
          SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
          Date date1 = formatter1.parse(date);

          ContactList contact = new ContactList();
          try {
            contact = PstContactList.fetchExc(supplierId);
          } catch (Exception exc) {

          }

          Location location = new Location();
          try {
            long locationId = Long.valueOf(strLocationId);
            location = PstLocation.fetchExc(locationId);
          } catch (Exception exc) {

          }

          String whereGroupDaily = " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " BETWEEN '"
                  + date + " 00:00:00' AND '" + date + " 23:59:00' AND CAT."
                  + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + " != " + shoppingBagCategory
                  + " AND CL." + PstContactList.fieldNames[PstContactList.FLD_CONTACT_ID] + "=" + supplierId;
          Vector listSupplierDaily = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupDaily, "CBM.CASH_BILL_MAIN_ID", 0);
          double totalAmount = 0;
          double totalQty = 0;
          if (listSupplierDaily.size() > 0) {
            for (int i = 0; i < listSupplierDaily.size(); i++) {
              Vector temp = (Vector) listSupplierDaily.get(i);
              Billdetail billDetail = (Billdetail) temp.get(4);

              totalAmount = totalAmount + billDetail.getTotalAmount();
              totalQty = totalQty + billDetail.getQty();
            }
          }
          html = "<table style=\"border: 0\">"
                  + "<tr><td colspan='3'>Dear All,</td></tr>"
                  + "<tr><td colspan='3'>Berikut saya kirimkan Daily Sales Report hari ini:</td></tr>"
                  + "<tr><td colspan='3'>&nbsp;</td></tr>"
                  + "<tr>"
                  + "<td>Date</td>"
                  + "<td>:</td>"
                  + "<td>" + Formater.formatDate(date1, "dd MMM yyyy") + "<td>"
                  + "</tr>"
                  + "<tr>"
                  + "<td>Store Name</td>"
                  + "<td>:</td>"
                  + "<td>" + location.getName() + "<td>"
                  + "</tr>"
                  + "<tr>"
                  + "<td>Sales Qty</td>"
                  + "<td>:</td>"
                  + "<td>" + String.format("%.0f", totalQty) + " PCS<td>"
                  + "</tr>"
                  + "<tr>"
                  + "<td>Transactions</td>"
                  + "<td>:</td>"
                  + "<td>" + listSupplierDaily.size() + " Transactions<td>"
                  + "</tr>"
                  + "<tr>"
                  + "<td>Sales Amount</td>"
                  + "<td>:</td>"
                  + "<td>Rp. " + Formater.formatNumber(totalAmount, ".2f") + ",-<td>"
                  + "</tr>"
                  + "<tr>"
                  + "<td>Reported By</td>"
                  + "<td>:</td>"
                  + "<td>" + loginId + "<td>"
                  + "</tr>"
                  + "<tr><td colspan='3'>&nbsp;</td></tr>"
                  + "<tr><td colspan='3'>Thank You & Best Regards</td></tr>"
                  + "<tr><td colspan='3'>" + loginId + "</td></tr>"
                  + "<tr><td colspan='3'>&nbsp;</td></tr>"
                  + "<tr><td colspan='3'>" + location.getName() + "</td></tr>"
                  + "</table>";
          SessEmail sessEmail = new SessEmail();
          String resultEmail = sessEmail.sendEamil(contact.getCompEmail(), "Daily Sales - " + contact.getPersonName() + " (" + Formater.formatDate(date1, "yyyy-MMM-dd") + ")", html, contact.getHomeEmail());
          html = resultEmail;
        } else if (loadtype.equals("generateSerial")) {
          html = generateSerial(request);
          if (html.equals("")) {
            numberOfError = 1;
            shortMessage = "Serial Not Found or Already Sold!";
            html += "<div class=\"input-group\"><input type=\"text\" name=\"SERIAL_CODE\" class=\"form-control serialFrom\" placeholder=\"Serial Code\" id=\"serial\" required=\"required\">"
                    + "<div class=\"input-group-addon btn btn-primary\" id=\"btnGenerateSerial\" title=\"Generate Sequential Serial\"><i class=\"fa fa-refresh\"></i></div>"
                    + "<div class=\"input-group-addon btn btn-danger btnDeleteAllSerial\" title=\"Clear All Field\"><i class=\"fa fa-undo\"></i></div></div>";
          }
        }
      } catch (Exception ex) {
        //ex.printStackTrace();
      }
    } else if (iCommand == Command.SAVE) {
      int iErrCode = FRMMessage.NONE;
      String frmMsg = "";

      if (loadtype.equals("saveReturnExchange")) {

        try {
          long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
          String itemReturnId[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_RETURN_ID");
          String itemReturnQty[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_RETURN_QTY");
          String itemExchangeId[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_EXCHANGE_ID");
          String itemExchangeQty[] = FRMQueryString.requestStringValues(request, "FRM_ITEM_EXCHANGE_QTY");
          String paymentSystemId[] = FRMQueryString.requestStringValues(request, "FRM_PAYMENT_SYSTEM_ID");

          //cek validasi data bill
          BillMain bm = PstBillMain.fetchExc(oidBillMain);
          Date billDate = Formater.reFormatDate(bm.getBillDate(), "yyyy-MM-dd");
          Date now = Formater.reFormatDate(new Date(), "yyyy-MM-dd");
          Location location = PstLocation.fetchExc(bm.getLocationId());
          if (billDate.before(now)) {
            long difference = (now.getTime() - billDate.getTime()) / (1000 * 60 * 60 * 24);
            if (difference > location.getMaxExchangeDay()) {
              numberOfError = 1;
              shortMessage = "Tanggal bill lewat dari " + location.getMaxExchangeDay() + " hari";
            }
          }

          //cek validasi item return qty
          if (numberOfError == 0) {
            if (itemReturnQty == null || itemReturnQty.length == 0) {
              numberOfError = 1;
              shortMessage = "Pastikan qty item return diisi dengan benar";
            } else {
              double totalQty = 0;
              for (String s : itemReturnQty) {
                if (s.isEmpty() || s.equals("0")) {
                  continue;
                }
                totalQty += Double.valueOf(s);
              }
              if (totalQty == 0) {
                numberOfError = 1;
                shortMessage = "Qty minimal item return adalah 1";
              }
            }
          }

          //cek validasi item return id
          if (numberOfError == 0) {
            if (itemReturnId == null || itemReturnId.length == 0) {
              numberOfError = 1;
              shortMessage = "Item return tidak diketahui";
            }
          }

          //cek validasi item exchange qty
          if (numberOfError == 0) {
            if (itemExchangeQty == null || itemExchangeQty.length == 0) {
              numberOfError = 1;
              shortMessage = "Pastikan qty item exchange diisi dengan benar";
            } else {
              double totalQty = 0;
              for (String s : itemExchangeQty) {
                if (s.isEmpty() || s.equals("0")) {
                  continue;
                }
                totalQty += Double.valueOf(s);
              }
              if (totalQty == 0) {
                numberOfError = 1;
                shortMessage = "Qty minimal item exchange adalah 1";
              }
            }
          }

          //cek validasi item exchange id
          if (numberOfError == 0) {
            if (itemExchangeId == null || itemExchangeId.length == 0) {
              numberOfError = 1;
              shortMessage = "Item exchange tidak diketahui";
            } else {
              for (int i = 0; i < itemExchangeId.length; i++) {
                Material m = PstMaterial.fetchExc(Long.valueOf(itemExchangeId[i]));
                double qtyExc = Double.valueOf(itemExchangeQty[i]);

                CashMaster cashMaster = PstCashMaster.fetchExc(PstCashCashier.fetchExc(cashCashierId).getCashMasterId());
                if (cashMaster.getCashierStockMode() != PstCashMaster.STOCK_MODE_NO_STOCK_CHECKING) {
                  double qtyStockStockCard = SessMatCostingStokFisik.qtyMaterialBasedOnStockCard(m.getOID(), cashMaster.getLocationId(), new Date(), 0);
                  //double qtyStockRealTime = SessMatCostingStokFisik.qtyMaterialBasedOnOpeningCashier(m.getOID(), new Date());
                  double qtyStock = qtyStockStockCard/* - qtyStockRealTime*/;
                  if (qtyStock < qtyExc) {
                    numberOfError = 1;
                    shortMessage = (qtyStock == 0) ? "Stock for item " + m.getName() + " is empty" : "Stock available for item " + m.getName() + " is " + qtyStock;
                  }
                }
              }
            }
          }

          //cek validasi data payment
          if (numberOfError == 0) {
            if (paymentSystemId == null || paymentSystemId.length == 0) {
              numberOfError = 1;
              shortMessage = "Pastikan tipe payment diisi dengan benar";
            }
          }

          //execute return exchange
          if (numberOfError == 0) {
            CtrlReturn ctrlReturn = new CtrlReturn(request);
            numberOfError = ctrlReturn.actionExchange(iCommand, oidBillMain, request);
            shortMessage = ctrlReturn.getMessage();
            BillMain billMainExc = ctrlReturn.getBillMain();

            String whereCashPayment = PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID] + " = " + billMainExc.getOID();
            Vector<CashPayments> listCashPayment = PstCashPayment.list(0, 0, whereCashPayment, "");
            if (listCashPayment.size() == 1) {
              paymentType = listCashPayment.get(0).getPaymentTypeLong();
              Vector<CustomCashCreditCard> listCreditCards = PstCustomCashCreditCard.list(0, 0, PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CASH_PAYMENT_ID] + " = " + listCashPayment.get(0).getOID(), "");
              if (!listCreditCards.isEmpty()) {
                ccName = listCreditCards.get(0).getCcName();
                ccNo = listCreditCards.get(0).getCcNumber();
                ccBank = listCreditCards.get(0).getDebitBankName();
                ccValid = Formater.formatDate(listCreditCards.get(0).getExpiredDate(), "yyyy-MM-dd");
              }
            }

            if (numberOfError == 0) {
              frmMsg = drawPrint(transactionType, billMainExc.getOID(), userLoginName,
                      taxInc, paymentType, ccName, ccNo, ccBank, ccValid,
                      ccCharge, payAmount, cashCashierId, "printpay", request.getContextPath(), oidMember);
            }
          }

        } catch (Exception e) {
          numberOfError = 1;
          shortMessage = e.getMessage();
        }

      } else if (loadtype.equals("savenewcustomer")) {

        try {
          long newMemberLocation = FRMQueryString.requestLong(request, FrmContactList.fieldNames[FrmContactList.FRM_FIELD_LOCATION_ID]);
          String newMemberCode = FRMQueryString.requestString(request, FrmContactList.fieldNames[FrmContactList.FRM_FIELD_CONTACT_CODE]);
          String newMemberName = FRMQueryString.requestString(request, FrmContactList.fieldNames[FrmContactList.FRM_FIELD_PERSON_NAME]);
          String newMemberTelp = FRMQueryString.requestString(request, FrmContactList.fieldNames[FrmContactList.FRM_FIELD_TELP_MOBILE]);
          long newMemberGroup = FRMQueryString.requestLong(request, "FRM_FIELD_MEMBER_GROUP");

          if (PstMemberGroup.checkOID(newMemberGroup)) {
            //cari contact class tipe member
            String whereContactClass = " " + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + "=" + PstContactClass.CONTACT_TYPE_MEMBER + "";
            Vector listContactClass = PstContactClass.list(0, 0, whereContactClass, "");
            ContactClass contactClass = (ContactClass) listContactClass.get(0);

            //buat member baru
            MemberReg memberReg = new MemberReg();
            memberReg.setContactCode(newMemberCode);
            memberReg.setPersonName(newMemberName);
            memberReg.setTelpMobile(newMemberTelp);
            memberReg.setMemberGroupId(newMemberGroup);
            long oidNewMember = PstMemberReg.insertExc(memberReg);

            //buat class assign
            ContactClassAssign contactClassAssign = new ContactClassAssign();
            contactClassAssign.setContactClassId(contactClass.getOID());
            contactClassAssign.setContactId(oidNewMember);
            long contactClassAsignOid = PstContactClassAssign.insertExc(contactClassAssign);

            shortMessage = "New member has been saved";

            //kirim nilai
            returnData.put("RETURN_NEW_MEMBER_OID", "" + memberReg.getOID());
            returnData.put("RETURN_NEW_MEMBER_CODE", "" + memberReg.getContactCode());
            returnData.put("RETURN_NEW_MEMBER_NAME", "" + memberReg.getPersonName());
            returnData.put("RETURN_NEW_MEMBER_TELP", "" + memberReg.getTelpMobile());
            returnData.put("RETURN_NEW_MEMBER_GROUP", "" + memberReg.getMemberGroupId());

          } else {
            shortMessage = "Unknown member group!";
            numberOfError += 1;
          }

        } catch (Exception e) {
          shortMessage = "Error : " + e.getMessage();
          numberOfError += 1;
        }

      } else if (loadtype.equals("updateCustomerId")) {
        //>>> added by dewok 20190131, for update oid customer
        long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
        long oidCustomer = FRMQueryString.requestLong(request, "oidCustomer");
        try {
          BillMain bm = PstBillMain.fetchExc(oidBillMain);
          MemberReg memberReg = new MemberReg();
          if (PstMemberReg.checkOID(oidCustomer)) {
            memberReg = PstMemberReg.fetchExc(oidCustomer);
          }
          bm.setCustomerId(oidCustomer);
          bm.setGuestName("");
          bm.setGender(memberReg.getMemberSex());
          PstBillMain.updateExc(bm);

          //update harga member
          Location location = PstLocation.fetchExc(bm.getLocationId());
          long priceTypeId = location.getPriceTypeId();
          long discountTypeId = location.getDiscountTypeId();

          //cek apakah member menggunakan guide price
                    boolean useGuidePrice = checkMemberGuidePrice(bm.getOID());

          //cek apakah bill punya member id dan punya diskon mapping
          if (memberReg.getOID() > 0) {
            //gunakan price type dan diskon mapping dari member
            MemberGroup memberGroup = PstMemberGroup.fetchExc(memberReg.getMemberGroupId());
            priceTypeId = memberGroup.getPriceTypeId();
            discountTypeId = memberGroup.getDiscountTypeId();
          }

          Vector<Billdetail> listBilldetail = PstBillDetail.list(0, 0, PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + " = " + oidBillMain, null);
          for (Billdetail bd : listBilldetail) {

            //CARI PRICE TYPE MAPPING
            String wherePrice = PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " = " + bd.getMaterialId()
                    + " AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = " + priceTypeId
                    + " AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = " + location.getStandardRateId()
                    + "";
            Vector<PriceTypeMapping> listPrice = PstPriceTypeMapping.list(0, 0, wherePrice, "");
            if (listPrice.size() > 0) {
              bd.setItemPrice(listPrice.get(0).getPrice());
            }

            //CARI DISKON MAPPING
            CtrlCustomBillDetail ccbd = new CtrlCustomBillDetail(request);
            HashMap<String, String> discMap = ccbd.getDataDiscount(bd.getMaterialId(), bd.getItemPrice(), bm.getLocationId(), bm.getCurrencyId(), discountTypeId, bd.getQty());
            try {
              bd.setDiscType(Integer.valueOf(discMap.get("DISCOUNT_TYPE")));
              bd.setDiscPct(Double.valueOf(discMap.get("DISCOUNT_PCT")));
              bd.setDisc(Double.valueOf(discMap.get("DISCOUNT_VAL")));
            } catch (Exception e) {
            }

            //CEK TAX & SERVICE
            double balance = (bd.getItemPrice() - bd.getDisc()) * bd.getQty();
            double totalPrice = 0;
            double amountService = 0;
            double amountTax = 0;
            double afterService = 0;
            double afterTax = 0;
            double withoutTaxService = 0;

            Vector listTaxService = PstCustomBillMain.listTaxService(0, 0, "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + bd.getBillMainId() + "'", "");
            if (listTaxService.size() > 0) {
              BillMainCostum billMainCostum = (BillMainCostum) listTaxService.get(0);
              if (location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE) {
                double amount = balance / ((billMainCostum.getTaxPct() + billMainCostum.getServicePct() + 100) / 100);
                amountService = amount * (billMainCostum.getServicePct() / 100);
                afterService = amount + amountService;
                amountTax = amount * (billMainCostum.getTaxPct() / 100);
                afterTax = amount + amountService + amountTax;
                withoutTaxService = amount;
                totalPrice = withoutTaxService;
              } else {
                amountService = balance * (billMainCostum.getServicePct() / 100);
                afterService = balance + amountService;
                amountTax = balance * (billMainCostum.getTaxPct() / 100);
                afterTax = balance + amountService + amountTax;
                totalPrice = balance;
              }
              bd.setTotalSvc(amountService);
              bd.setTotalTax(amountTax);
              bd.setTotalPrice(totalPrice);
            }

            if (useGuidePrice) {
              bd.setGuidePrice(bd.getItemPrice());
            } else {
              bd.setGuidePrice(0);
            }

            PstBillDetail.updateExc(bd);
          }

        } catch (Exception e) {
          frmMsg = e.getMessage();
        }

      } else if (loadtype.equals("covernumber")) {
        frmMsg = "failed";
        oid = FRMQueryString.requestLong(request, "oid");
        String coverNumber = FRMQueryString.requestString(request, "covernumber");
        String master_event = FRMQueryString.requestString(request, "master_event");
        String master_group = FRMQueryString.requestString(request, "master_group");
        String master_payment = FRMQueryString.requestString(request, "master_payment");
        String master_guide = FRMQueryString.requestString(request, "master_guide");
        try {
          BillMain billMain = PstBillMain.fetchExc(oid);
          billMain.setCoverNumber(coverNumber);
          long oidx = PstBillMain.updateExc(billMain);
          try {
            if (oid != 0) {
              long xx = PstBillMain.updateEventName(oid, master_event + "|" + master_group + "|" + master_payment + "|" + master_guide);
            }
          } catch (Exception ex) {
          }
          frmMsg = "success";
        } catch (Exception ex) {

        }
      } else if (loadtype.equals("edititem")) {
        try {
          String promotionType = PstSystemProperty.getValueByName("CASHIER_PROMOTION_GROUP_TYPE");
          CtrlCustomBillDetail ctrlBillDetail = new CtrlCustomBillDetail(request);
          iErrCode = ctrlBillDetail.action(iCommand, oid, request);
          long oidValue = FRMQueryString.requestLong(request, "PROMOTION_BILL_DETAIL");
          DataCustom dataCustom = PstDataCustom.getDataCustom(oid, "" + promotionType, "bill_detail_map_type");
          if (dataCustom.getOID() > 0) {
            dataCustom.setDataValue("" + oidValue);
            PstDataCustom.updateExc(dataCustom);
          } else {
            dataCustom.setDataName("bill_detail_map_type");
            dataCustom.setDataValue("" + oidValue);
            dataCustom.setLink("" + promotionType);
            dataCustom.setOwnerId(oid);
            PstDataCustom.insertExc(dataCustom);
          }
          frmMsg = ctrlBillDetail.getMessage();
        } catch (Exception ex) {
          frmMsg = "FAILED";
        }
        //EDIT ITEM PROCCESS
      } else if (loadtype.equals("editItemProc")) {
        int fromEditable = 0;
        try {
          fromEditable = FRMQueryString.requestInt(request, "fromEditable");
        } catch (Exception ex) {
          fromEditable = 0;
        }
        try {

          long oidDetail = FRMQueryString.requestLong(request, "" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID] + "");
          CtrlCustomBillDetail ctrlBillDetail = new CtrlCustomBillDetail(request);
          iErrCode = ctrlBillDetail.action(iCommand, oidDetail, request);
          if (iErrCode == 0 && fromEditable == 1) {
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
        } catch (Exception ex) {
          frmMsg = "FAILED";
        }
        //DELETE ITEM PROCESS 
      } else if (loadtype.equals("delitemproccess")) {

        String spvName = FRMQueryString.requestString(request, "spvName");
        String noteVoid = FRMQueryString.requestString(request, "" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_NOTE] + "");

        int command = Command.DELETE;
        noteVoid = " reason : " + noteVoid + " - authorize void :  " + spvName;
        Long oidBillDetail = oid;
        Billdetail billDetails = new Billdetail();
        try {
          billDetails = PstBillDetail.fetchExc(oidBillDetail);
        } catch (Exception e) {
        }

        int status = 0;
        try {
          CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
          if (cashierItemErrorCantDelete.equals("0")) {
            //DELETE KEMUDIAN DI TAMBAHKAN DI TABEL DETAIL VOID
            iErrCode = ctrlBillMain.actionUpdateItem(command, oidBillDetail, status, noteVoid, 0);
            frmMsg = String.valueOf(billDetails.getBillMainId());
          } else {
            //DELETE HANYA MENGGANTI HARGA DENGAN 0
            iErrCode = ctrlBillMain.actionUpdateItem2(command, oidBillDetail, status, noteVoid, 0);
            frmMsg = String.valueOf(billDetails.getBillMainId());
          }

        } catch (Exception ex) {
          frmMsg = "FAILED";
        }

        //CANCEL BILL
      } else if (loadtype.equals("cancelbill")) {
        try {
          CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
          iErrCode = ctrlBillMain.actionCancel(iCommand, oid, request);
          frmMsg = ctrlBillMain.getMessage();
        } catch (Exception ex) {
          frmMsg = "FAILED";
        }

        ///PAYMENT MOVE BILL
      } else if (loadtype.equals("movebill")) {
        try {
          CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
          iErrCode = ctrlBillMain.actionMove(iCommand, oid, request);
          frmMsg = ctrlBillMain.getMessage();
          displayTotal = String.valueOf(ctrlBillMain.getOidBillMove());
        } catch (Exception ex) {
          frmMsg = "FAILED";
        }

        ///CLOSING 
      } else if (loadtype.equals("closing")) {
        if (listSpv.size() > 0) {
          AppUser appUser = (AppUser) listSpv.get(0);
          CtrlCloseCashCashier ctrlCloseCashCashier = new CtrlCloseCashCashier(request);
          iErrCode = ctrlCloseCashCashier.action(iCommand, oidCashier, request, appUser.getOID());
          //oidCashier = 504404711506452304L;
          Vector listCashier = PstCashCashier.listCashOpening(0, 0, "CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID] + "='" + oidCashier + "'", "");
          Vector listCashierData = new Vector(1, 1);
          CashCashier cashCashier = new CashCashier();
          CashMaster cashMaster = new CashMaster();
          Location location = new Location();
          Company comp = new Company();
          if (!listCashier.isEmpty()) {
            listCashierData = (Vector) listCashier.get(0);
            cashCashier = (CashCashier) listCashierData.get(0);
            try {
              cashCashier = PstCashCashier.fetchExc(cashCashier.getOID());
            } catch (Exception exc) {
            }
            location = (Location) listCashierData.get(2);
            try {
              location = PstLocation.fetchExc(location.getOID());
              comp = PstCompany.fetchExc(location.getCompanyId());
            } catch (Exception exc) {
            }

          }

          String closeTemplate = PstSystemProperty.getValueByName("CASHIER_CLOSING_PRINT_TEMPLATE");
          if (closeTemplate.equals("1")) {
            frmMsg = drawPrintClosing(listCashierData, loginId);
            String emailMsg = drawPrintClosingDetail(listCashierData, loginId);
            SessEmail sessEmail = new SessEmail();
            String resultEmail = sessEmail.sendEamil(comp.getEmailCompany(), "Daily Sales - " + location.getName() + " (" + Formater.formatDate(cashCashier.getCloseDate(), "yyyy-MMM-dd") + ")", emailMsg, location.getAcountingEmail());

            shortMessage = getShortMessage(request, listCashier);
          } else {
            frmMsg = drawPrintClosing(listCashierData, loginId);
            shortMessage = getShortMessage(request, listCashier);
          }
        } else {
          frmMsg = "FAILED";
        }

        //RETURN BILL
      } else if (loadtype.equals("returnbill")) {
        CtrlReturn ctrlReturn = new CtrlReturn(request);
        iErrCode = ctrlReturn.action(iCommand, oid, request);
        frmMsg = drawPrintReturn(oid, loginId, request.getContextPath(), "");
        //JOIN BILL
      } else if (loadtype.equals("saveJoin")) {
        CtrlCustomBillDetail ctrlCustomBillDetail = new CtrlCustomBillDetail(request);
        iErrCode = ctrlCustomBillDetail.actionJoin(Command.SAVE, request);
        frmMsg = ctrlCustomBillDetail.getMessage();
        //CONFIRM PRINT STATUS
      } else if (loadtype.equals("confirmPrintStatus")) {
        CtrlCustomBillDetail ctrlCustomBillDetail = new CtrlCustomBillDetail(request);
        iErrCode = ctrlCustomBillDetail.actionJoin(Command.CONFIRM, request);
        frmMsg = ctrlCustomBillDetail.getMessage();
      } else if (loadtype.equals("additem")) {
        int fromEditable = 0;
        try {
          fromEditable = FRMQueryString.requestInt(request, "fromEditable");
        } catch (Exception ex) {
          fromEditable = 0;
        }
        CtrlCustomBillDetail ctrlCustomBillDetail = new CtrlCustomBillDetail(request);
        iErrCode = ctrlCustomBillDetail.action(iCommand, 0, request);

        if (iErrCode == 0 && fromEditable == 1) {
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
      } else if (loadtype.equals("newmember")) {
        CtrlQueensContactList ctrlContactList = new CtrlQueensContactList(request);
        iErrCode = ctrlContactList.action(iCommand, 0);

        frmMsg = ctrlContactList.getMessage();
      } else if (loadtype.equals("neworder")) {
        CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
        iErrCode = ctrlBillMain.action(iCommand, 0, request);
        frmMsg = ctrlBillMain.getMessage();
      } else if (loadtype.equals("newctmember")) {
        CtrlQueensContactList ctrlContactList = new CtrlQueensContactList(request);
        iErrCode = ctrlContactList.action(iCommand, 0);
        frmMsg = ctrlContactList.getMessage();
      } else if (loadtype.equals("confirmorder")) {
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
      } else if (loadtype.equals("saveTransferDoc")) {
        long oidMatDispatch = FRMQueryString.requestLong(request, "MATERIAL_DISPATCH_ID");
        long oidMatItem = FRMQueryString.requestLong(request, "MATERIAL_ITEM_ID");
        String barcode = FRMQueryString.requestString(request, "BARCODE");

        Material material = new Material();
        try {
          material = PstMaterial.fetchBySkuBarcode(barcode);
        } catch (Exception exc) {
        }

        MatDispatch dis = new MatDispatch();
        try {
          dis = PstMatDispatch.fetchExc(oidMatDispatch);
        } catch (Exception exc) {

        }

        double sellPrice = PstPriceTypeMapping.getSellPrice(material.getOID(), PstPriceTypeMapping.getOidStandartRate(), PstPriceTypeMapping.getOidPriceType());
        //double qty = PstMaterialStock.cekQtyStock(oidMatDispatch,material.getOID(),dis.getLocationId());
        double qtyStockStockCardStore = SessMatCostingStokFisik.qtyMaterialBasedOnStockCard(material.getOID(), dis.getLocationId(), new Date(), 0);
        //double qtyStockRealTimeStore = SessMatCostingStokFisik.qtyMaterialBasedOnOpeningCashier(Long.valueOf(material.getMaterialId()), new Date());
        double qty = qtyStockStockCardStore;
        if (material.getOID() != 0) {
          if (oidMatItem == 0 && PstMatDispatchItem.materialExist(material.getOID(), oidMatDispatch)) {
            MatDispatchItem disItem = new MatDispatchItem();

            disItem = PstMatDispatchItem.getObjectDispatchItem(material.getOID(), oidMatDispatch);
            if (disItem.getOID() != 0) {
              disItem.setQty(disItem.getQty() + 1);
              disItem.setResidueQty(disItem.getQty() + 1);

              if (qty < (disItem.getQty())) {
                frmMsg = "<input type='hidden' id='matDisCode' value='" + dis.getDispatchCode() + "'>"
                        + "<input type='hidden' id='matDisId' value='" + oidMatDispatch + "'>";
                Vector listMatDispatchItem = PstMatDispatchItem.list(0, 0, oidMatDispatch);
                frmMsg += drawTransferItem(listMatDispatchItem, Command.ADD, dis.getLocationId(), dis.getDispatchStatus(), 0);
                frmMsg += "<div class=\"row\">"
                        + "<div class=\"col-md-12\" style=\"text-align: center;background-color: yellow;\">Qty stok tidak cukup!</div>"
                        + "</div>";
              } else {
                try {
                  PstMatDispatchItem.updateExc(disItem);
                  frmMsg = "<input type='hidden' id='matDisCode' value='" + dis.getDispatchCode() + "'>"
                          + "<input type='hidden' id='matDisId' value='" + oidMatDispatch + "'>";
                  Vector listMatDispatchItem = PstMatDispatchItem.list(0, 0, oidMatDispatch);
                  frmMsg += drawTransferItem(listMatDispatchItem, Command.ADD, dis.getLocationId(), dis.getDispatchStatus(), 0);
                } catch (Exception exc) {
                }
              }
            }
          } else {
            if (qty >= 1) {
              MatDispatchItem dispatchItem = new MatDispatchItem();
              dispatchItem.setDispatchMaterialId(oidMatDispatch);
              dispatchItem.setMaterialId(material.getOID());
              dispatchItem.setUnitId(material.getDefaultStockUnitId());
              dispatchItem.setQty(1);
              dispatchItem.setHpp(sellPrice);
              dispatchItem.setResidueQty(1);
              try {
                PstMatDispatchItem.insertExc(dispatchItem);
                frmMsg = "<input type='hidden' id='matDisCode' value='" + dis.getDispatchCode() + "'>"
                        + "<input type='hidden' id='matDisId' value='" + oidMatDispatch + "'>";
                Vector listMatDispatchItem = PstMatDispatchItem.list(0, 0, oidMatDispatch);
                frmMsg += drawTransferItem(listMatDispatchItem, Command.ADD, dis.getLocationId(), dis.getDispatchStatus(), 0);
              } catch (Exception exc) {
                System.out.println(exc.toString());
              }
            } else {
              frmMsg = "<input type='hidden' id='matDisCode' value='" + dis.getDispatchCode() + "'>"
                      + "<input type='hidden' id='matDisId' value='" + oidMatDispatch + "'>";
              Vector listMatDispatchItem = PstMatDispatchItem.list(0, 0, oidMatDispatch);
              frmMsg += drawTransferItem(listMatDispatchItem, Command.ADD, dis.getLocationId(), dis.getDispatchStatus(), 0);
              frmMsg += "<div class=\"row\">"
                      + "<div class=\"col-md-12\" style=\"text-align: center;background-color: yellow;\">Qty stok tidak cukup!</div>"
                      + "</div>";
            }
          }
        } else {
          frmMsg = "<input type='hidden' id='matDisCode' value='" + dis.getDispatchCode() + "'>"
                  + "<input type='hidden' id='matDisId' value='" + oidMatDispatch + "'>";
          Vector listMatDispatchItem = PstMatDispatchItem.list(0, 0, oidMatDispatch);
          frmMsg += drawTransferItem(listMatDispatchItem, Command.ADD, dis.getLocationId(), dis.getDispatchStatus(), 0);
          frmMsg += "<div class=\"row\">"
                  + "<div class=\"col-md-12\" style=\"text-align: center;background-color: yellow;\">Item not found!</div>"
                  + "</div>";
        }
      } else if (loadtype.equals("postTransferDoc")) {
        try {
          long oidMatDispatch = FRMQueryString.requestLong(request, "MATERIAL_DISPATCH_ID");
          MatDispatch matDispatch = new MatDispatch();
          try {
            matDispatch = PstMatDispatch.fetchExc(oidMatDispatch);
          } catch (Exception exc) {
          }

          matDispatch.setDispatchStatus(2);

          String autoRec = "";
          String receiveOutoFinalByDf = "";
          try {
            autoRec = PstSystemProperty.getValueByName("AUTO_REC_FA");
          } catch (Exception exc) {
            System.out.println(exc);
            autoRec = "Y";
          }

          try {
            receiveOutoFinalByDf = PstSystemProperty.getValueByName("RECEIVE_AUTO_FINAL_BY_DF");
          } catch (Exception exc) {
            System.out.println(exc);
            receiveOutoFinalByDf = "1";
          }

          long oidRecMaterial = PstMatReceive.getOidReceiveMaterial(oidMatDispatch);
          if (matDispatch.getDispatchStatus() == I_DocStatus.DOCUMENT_STATUS_FINAL && matDispatch.getLocationType() != PstMatDispatch.FLD_TYPE_TRANSFER_UNIT && autoRec.equals("Y") && oidRecMaterial == 0) {
            MatReceive matReceive = new MatReceive();
            matReceive.setReceiveDate(new java.util.Date());
            matReceive.setReceiveFrom(matDispatch.getLocationId());
            matReceive.setLocationId(matDispatch.getDispatchTo());
            matReceive.setLocationType(PstLocation.TYPE_LOCATION_STORE);
            if (receiveOutoFinalByDf.equals("1")) {
              matReceive.setReceiveStatus(I_DocStatus.DOCUMENT_STATUS_CLOSED);
            } else {
              matReceive.setReceiveStatus(I_DocStatus.DOCUMENT_STATUS_DRAFT);
            }
            matReceive.setReceiveSource(PstMatReceive.SOURCE_FROM_DISPATCH);
            matReceive.setDispatchMaterialId(matDispatch.getOID());
            matReceive.setRemark("Automatic Receive process from transfer number : " + matDispatch.getDispatchCode());
            int docType = -1;
            try {
              I_PstDocType i_pstDocType = (I_PstDocType) Class.forName(I_DocType.DOCTYPE_CLASSNAME).newInstance();
              docType = i_pstDocType.composeDocumentType(I_DocType.SYSTEM_MATERIAL, I_DocType.MAT_DOC_TYPE_LMRR);
            } catch (Exception e) {
            }
            matReceive.setRecCodeCnt(SessMatReceive.getIntCode(matReceive, matReceive.getReceiveDate(), 0, docType, 0, true));
            matReceive.setRecCode(SessMatReceive.getCodeReceive(matReceive));

            oid = PstMatReceive.insertExc(matReceive);

            Vector listDispatchItem = new Vector();
            listDispatchItem = PstMatDispatchItem.list(0, 0, matDispatch.getOID());
            if (listDispatchItem != null && listDispatchItem.size() > 0) {
              for (int x = 0; x < listDispatchItem.size(); x++) {
                Vector temp = (Vector) listDispatchItem.get(x);
                MatDispatchItem dfItem = (MatDispatchItem) temp.get(0);
                MatReceiveItem matReceiveItem = new MatReceiveItem();
                matReceiveItem.setUnitId(dfItem.getUnitId());
                matReceiveItem.setQty(dfItem.getQty());
                matReceiveItem.setResidueQty(dfItem.getQty());
                matReceiveItem.setMaterialId(dfItem.getMaterialId());
                matReceiveItem.setReceiveMaterialId(oid);
                matReceiveItem.setCost(dfItem.getHpp());
                matReceiveItem.setCurrBuyingPrice(dfItem.getHpp());
                matReceiveItem.setTotal(matReceiveItem.getQty() * matReceiveItem.getCost());

                long oidInsertOtomatic = PstMatReceiveItem.insertExc(matReceiveItem);
                long oidStock = PstMaterialStock.fetchByMaterialLocation(dfItem.getMaterialId(), matDispatch.getLocationId());
                MaterialStock matStock = new MaterialStock();
                try {
                  matStock = PstMaterialStock.fetchExc(oidStock);
                  matStock.setQty(matStock.getQty() - dfItem.getQty());
                  PstMaterialStock.updateExc(matStock);
                } catch (Exception exc) {
                }

                long oidStockNew = PstMaterialStock.fetchByMaterialLocation(dfItem.getMaterialId(), matDispatch.getDispatchTo());
                MaterialStock matStockNew = new MaterialStock();
                try {
                  matStockNew = PstMaterialStock.fetchExc(oidStockNew);
                  matStockNew.setQty(dfItem.getQty());
                  PstMaterialStock.updateExc(matStockNew);
                } catch (Exception exc) {
                }

                //add opie-eyek 20131206
                //untuk insert otomatis serial code dari transfer
                if (oidInsertOtomatic != 0) {
                  Vector listSerialCode = new Vector();
                  listSerialCode = PstDispatchStockCode.checkListStockCodeDispatch(dfItem.getOID());
                  for (int c = 0; c < listSerialCode.size(); c++) {
                    DispatchStockCode dispatchStockCode = (DispatchStockCode) listSerialCode.get(c);
                    ReceiveStockCode receiveStockCode = new ReceiveStockCode();
                    receiveStockCode.setReceiveMaterialItemId(oidInsertOtomatic);
                    receiveStockCode.setStockCode(dispatchStockCode.getStockCode());
                    receiveStockCode.setStockValue(dispatchStockCode.getStockValue());
                    receiveStockCode.setReceiveMaterialId(oid);
                    try {
                      PstReceiveStockCode.insertExc(receiveStockCode);
                    } catch (Exception e) {
                    }
                  }
                }
              }
            }

          }
          matDispatch.setDispatchStatus(5);

          PstMatDispatch.updateExc(matDispatch);
        } catch (Exception exc) {

        }
      } else if (loadtype.equals("deleteTransferItem")) {
        long oidMatDispatch = FRMQueryString.requestLong(request, "MATERIAL_DISPATCH_ID");
        long oidMatDispatchItem = FRMQueryString.requestLong(request, "MATERIAL_DISPATCH_ITEM_ID");

        MatDispatch dis = new MatDispatch();
        try {
          dis = PstMatDispatch.fetchExc(oidMatDispatch);
        } catch (Exception exc) {
        }

        try {
          PstMatDispatchItem.deleteExc(oidMatDispatchItem);
        } catch (Exception exc) {
        }

        frmMsg = "<input type='hidden' id='matDisCode' value='" + dis.getDispatchCode() + "'>"
                + "<input type='hidden' id='matDisId' value='" + oidMatDispatch + "'>";
        Vector listMatDispatchItem = PstMatDispatchItem.list(0, 0, oidMatDispatch);
        frmMsg += drawTransferItem(listMatDispatchItem, Command.ADD, dis.getLocationId(), dis.getDispatchStatus(), 0);

      } else if (loadtype.equals("saveTransferItem")) {
        long oidMatDispatch = FRMQueryString.requestLong(request, "MATERIAL_DISPATCH_ID");
        long oidMatDispatchItem = FRMQueryString.requestLong(request, "MATERIAL_DISPATCH_ITEM_ID");
        int qtyInput = FRMQueryString.requestInt(request, "QTY_ITEM");

        MatDispatchItem disItem = new MatDispatchItem();
        try {
          disItem = PstMatDispatchItem.fetchExc(oidMatDispatchItem);
        } catch (Exception exc) {
        }

        Material material = new Material();
        try {
          material = PstMaterial.fetchExc(disItem.getMaterialId());
        } catch (Exception exc) {
        }

        MatDispatch dis = new MatDispatch();
        try {
          dis = PstMatDispatch.fetchExc(oidMatDispatch);
        } catch (Exception exc) {

        }

        double qty = SessMatCostingStokFisik.qtyMaterialBasedOnStockCard(material.getOID(), dis.getLocationId(), new Date(), 0);
        if (disItem.getOID() != 0) {
          disItem.setQty(qtyInput);
          if (qty < (disItem.getQty())) {
            frmMsg = "<input type='hidden' id='matDisCode' value='" + dis.getDispatchCode() + "'>"
                    + "<input type='hidden' id='matDisId' value='" + oidMatDispatch + "'>";
            Vector listMatDispatchItem = PstMatDispatchItem.list(0, 0, oidMatDispatch);
            frmMsg += drawTransferItem(listMatDispatchItem, Command.ADD, dis.getLocationId(), dis.getDispatchStatus(), 0);
            frmMsg += "<div class=\"row\">"
                    + "<div class=\"col-md-12\" style=\"text-align: center;background-color: yellow;\">Qty stok tidak cukup!</div>"
                    + "</div>";
          } else {
            try {
              PstMatDispatchItem.updateExc(disItem);
              frmMsg = "<input type='hidden' id='matDisCode' value='" + dis.getDispatchCode() + "'>"
                      + "<input type='hidden' id='matDisId' value='" + oidMatDispatch + "'>";
              Vector listMatDispatchItem = PstMatDispatchItem.list(0, 0, oidMatDispatch);
              frmMsg += drawTransferItem(listMatDispatchItem, Command.ADD, dis.getLocationId(), dis.getDispatchStatus(), 0);
            } catch (Exception exc) {
            }
          }
        }

      } else if (loadtype.equals("production")) {
        long billMainId = FRMQueryString.requestLong(request, FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]);
        double admin = FRMQueryString.requestLong(request, "admin");
        double transport = FRMQueryString.requestLong(request, "transport");
        double NewPrice  = admin + transport;
        try {
          BillMain billMain = PstBillMain.fetchExc(billMainId);
          billMain.setStatus(PstBillMain.PETUGAS_DELIVERY_STATUS_ON_PRODUCTION);
//          billMain.setBillStatus(PstBillMain.PETUGAS_DELIVERY_STATUS_ON_PRODUCTION);
          PstBillMain.updateExc(billMain);
        } catch (Exception exc) {
        }
      } else {

        //CHECK COVER NUMBER
        long billMainId = FRMQueryString.requestLong(request, FrmCashPayment.fieldNames[FrmCashPayment.FRM_FIELD_CASH_BILL_MAIN_ID]);

        //added by dewok 20191130 for guide price
                boolean useGuidePrice = checkMemberGuidePrice(billMainId);

        try {
          CtrlCustomPayment ctrlCustomPayment = new CtrlCustomPayment(request);
          String isContinuesPayment = PstSystemProperty.getValueByName("PRINT_CONTINUES_PAYMENT");
          try {
            BillMain billMainCheck = PstBillMain.fetchExc(billMainId);
            if (transactionType != 0) { //jika kredit
              iErrCode = ctrlCustomPayment.action(iCommand, paymentType, request);
              frmMsg = ctrlCustomPayment.getMessage();
              if (frmMsg == "SUCCESS") {
                int separate = FRMQueryString.requestInt(request, "separateprint");
                //jika separate = 0, maka sistem akan mencetak full bill, jika 1 
                //maka sistem hanya akan mencetak payment only 
                String isDynamicTemplate = PstSystemProperty.getValueByName("PRINT_DYNAMIC_TEMPLATE");
                if (useGuidePrice == false) {
                  if (isDynamicTemplate.equals("1")) {
                    BillMain billMain = new BillMain();
                    try {
                      billMain = PstBillMain.fetchExc(oid);
                    } catch (Exception ex) {
                      ex.printStackTrace();
                    }
                    frmMsg = templateHandler(billMain);
                  } else {
                    if (separate == 0) {
                      frmMsg = drawPrint(transactionType, oid, "" + loginId,
                              taxInc, paymentType, ccName, ccNo, ccBank, ccValid, ccCharge,
                              payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);
                    } else {
                      if (isContinuesPayment.equals("1")) {
                        frmMsg = drawPrintContinuesPayment(transactionType, oid, "" + loginId,
                                taxInc, paymentType, ccName, ccNo, ccBank, ccValid, ccCharge,
                                payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);
                      } else {
                        frmMsg = drawPrintPaymentOnly(transactionType, oid, "" + loginId,
                                taxInc, paymentType, ccName, ccNo, ccBank, ccValid,
                                ccCharge, payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);
                      }
                    }
                  }
                }

                if (transactionType == 2) {
                  CtrlBillMain ctrlBillMain = new CtrlBillMain(request);
                  CtrlBillDetail ctrlBillDetail = new CtrlBillDetail(request);
                  iErrCode = ctrlBillMain.actionCancel(Command.DELETE, oid, request);
                  Vector listBillDetail = PstBillDetail.list(0, 0,
                          "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + oid + "'",
                          "");
                  if (listBillDetail.size() != 0) {
                    for (int i = 0; i < listBillDetail.size(); i++) {
                      Billdetail billdetail = (Billdetail) listBillDetail.get(i);
                      iErrCode = ctrlBillDetail.action2(Command.DELETE, billdetail.getOID(), oid, billdetail.getParentId());
                    }
                  }
                }

                autoCheckOut(request, billMainId);
              }
            } else {

              iErrCode = ctrlCustomPayment.action(iCommand, oid, request);
              int poin = PstBillMain.getPoin(billMainId);
              if(poin != 0){
                MemberReg mem = new MemberReg();
                BillMain bm = new BillMain();
                try {
                bm = PstBillMain.fetchExc(billMainId);
                mem = PstMemberReg.fetchExc(bm.getCustomerId());
                MemberPoin mp = new MemberPoin();
                mp.setCashBillMainId(billMainId);
                mp.setMemberId(mem.getOID());
                mp.setCredit(poin);
                mp.setTransactionDate(new Date());
                PstMemberPoin.insertExc(mp);
                   
                } catch (Exception e) {
                }
              }
              frmMsg = ctrlCustomPayment.getMessage();
              if (frmMsg == "SUCCESS") {

                //DISC GLOBAL
                String getDiscPct = FRMQueryString.requestString(request, "FRM_FIELD_DISC_PCT");
                String getDiscNominal = FRMQueryString.requestString(request, "FRM_FIELD_DISC_GLOBAL");
                if (getDiscPct != null) {
                  getDiscPct = getDiscPct.replace(",", "");
                }
                if (getDiscNominal != null) {
                  getDiscNominal = getDiscNominal.replace(",", "");
                }
                double discPct = Double.parseDouble(getDiscPct);
                double discNominal = Double.parseDouble(getDiscNominal);
                updateDiscDetail(oid, discPct, discNominal);

                int separate = FRMQueryString.requestInt(request, "separateprint");
                //jika separate = 0, maka sistem akan mencetak full bill, jika 1 
                //maka sistem hanya akan mencetak payment only 
                String isDynamicTemplate = PstSystemProperty.getValueByName("PRINT_DYNAMIC_TEMPLATE");
                String isCashSmall = PstSystemProperty.getValueByName("PRINT_INVOICE_CASH_ALWAYS_SMALL");
                if (useGuidePrice == false) {
                  if (isDynamicTemplate.equals("1")) {
                    if (isCashSmall.equals("1")) {
                      if (separate == 0) {
                        frmMsg = drawPrint(transactionType, oid, "" + loginId,
                                taxInc, paymentType, ccName, ccNo, ccBank, ccValid,
                                ccCharge, payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);
                      } else {
                        frmMsg = drawPrintPaymentOnly(transactionType, oid, "" + loginId,
                                taxInc, paymentType, ccName, ccNo, ccBank, ccValid,
                                ccCharge, payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);

                      }
                    } else {
                      BillMain billMain = new BillMain();
                      try {
                        billMain = PstBillMain.fetchExc(oid);
                      } catch (Exception ex) {
                        ex.printStackTrace();
                      }
                      frmMsg = templateHandler(billMain);
                    }
                  } else {
                    if (separate == 0) {
                      frmMsg = drawPrint(transactionType, oid, "" + loginId,
                              taxInc, paymentType, ccName, ccNo, ccBank, ccValid,
                              ccCharge, payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);
                    } else {
                      //disini ubah habis makan siang
                      if (isContinuesPayment.equals("1")) {
                        frmMsg = drawPrintContinuesPayment(transactionType, oid, "" + loginId,
                                taxInc, paymentType, ccName, ccNo, ccBank, ccValid,
                                ccCharge, payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);
                      } else {
                        frmMsg = drawPrintPaymentOnly(transactionType, oid, "" + loginId,
                                taxInc, paymentType, ccName, ccNo, ccBank, ccValid,
                                ccCharge, payAmount, oidCashier, "printpay", request.getContextPath(), oidMember);
                      }
                    }
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

            if (frmMsg.equals("SUCCESS") && useGuidePrice == true) {
              double service = FRMQueryString.requestDouble(request, "FRM_FIELD_SERVICE_PCT");
              double tax = FRMQueryString.requestDouble(request, "FRM_FIELD_TAX_PCT");
              double discPct = FRMQueryString.requestDouble(request, "FRM_FIELD_DISC_PCT");
              //draw print guide price
              frmMsg = drawPrintGuidePrice(transactionType, oid, "" + loginId,
                      taxInc, paymentType, ccName, ccNo, ccBank, ccValid, ccCharge, payAmount,
                      oidCashier, "printpay", request.getContextPath(), oidMember, tax, service, discPct);
            }
          } catch (Exception ex) {

          }

        } catch (Exception ex) {
          //ex.printStackTrace();
        }
      }
      html = frmMsg;
    } else if (iCommand == Command.ADD) {
      if (loadtype.equals("createTransferDoc")) {
        int type = FRMQueryString.requestInt(request, "TYPE_TRANSFER");
        Location loc = new Location();
        try {
          loc = PstLocation.fetchExc(oidLocationParent);
        } catch (Exception exc) {
        }

        Location parentLoc = new Location();
        try {
          parentLoc = PstLocation.fetchExc(loc.getParentLocationId());
        } catch (Exception exc) {
        }

        MatDispatch matDispatch = new MatDispatch();
        if (type == 1) {
          matDispatch.setLocationId(oidLocationParent);
          matDispatch.setDispatchTo(parentLoc.getOID());
        } else {
          matDispatch.setLocationId(parentLoc.getOID());
          matDispatch.setDispatchTo(oidLocationParent);
        }

        matDispatch.setDispatchDate(new Date());
        SessMatDispatch sessDispatch = new SessMatDispatch();
        int maxCounter = sessDispatch.getMaxDispatchCounter(matDispatch.getDispatchDate(), matDispatch);
        maxCounter = maxCounter + 1;
        matDispatch.setDispatchCodeCounter(maxCounter);
        matDispatch.setDispatchCode(sessDispatch.generateDispatchCode(matDispatch));
        matDispatch.setLast_update(new Date());
        matDispatch.setLocationType(PstMatDispatch.FLD_TYPE_DISPATCH_LOCATION_WAREHOUSE);
        long oidMatDispatch = 0;
        try {
          oidMatDispatch = PstMatDispatch.insertExc(matDispatch);
        } catch (Exception exc) {
        }

        Vector listMatDispatchItem = PstMatDispatchItem.list(0, 0, oidMatDispatch);
        html = "<input type='hidden' id='matDisCode' value='" + matDispatch.getDispatchCode() + "'>"
                + "<input type='hidden' id='matDisId' value='" + oidMatDispatch + "'>";
        html += drawTransferItem(listMatDispatchItem, iCommand, loc.getParentLocationId(), 0, 0);
      }
    }

    try {
      returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_HTML], html);
      returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_DISPLAY_TOTAL], displayTotal);
      returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_BALANCE_VALUE], balanceValue);
      returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_CREDIT_CARD_CHARGE], creditCardCharge);
      returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_PAID_VALUE], paidValue);
      returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_TAX_INC], taxInc);
      returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_COSTUM_BALANCE], costumBalance);
      returnData.put(billMainJSON.fieldNames[billMainJSON.FLD_STATUS_ORDER], statusOrder);
      returnData.put("FRM_FIELD_SHORT_MESSAGE", shortMessage);
      returnData.put("FRM_FIELD_NUMBER_OF_ERROR", "" + numberOfError);
    } catch (Exception ex) {

    }
    response.getWriter().println(returnData);
  }
  
    public static int checkString(String strObject, String toCheck) {
        if (toCheck == null || strObject == null) {
            return -1;
        }
        if (strObject.startsWith("=")) {
            strObject = strObject.substring(1);
        }

        String[] parts = strObject.split(" ");
        if (parts.length > 0) {
            for (int i = 0; i < parts.length; i++) {
                String p = parts[i];
                if (toCheck.trim().equalsIgnoreCase(p.trim())) {
                    return i;
                };
            }
        }
        return -1;
    }
    
    public static double getValue(String formula) {
        DBResultSet dbrs = null;
        double compValueX = 0;
        try {
            String sql = "SELECT (" + formula + ")";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                compValueX = rs.getDouble(1);
            }

            rs.close();
            return compValueX;
        } catch (Exception e) {
            return 0;            
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    public static double rounding(int scale, double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(scale, RoundingMode.UP);
        return bDecimal.doubleValue();
    }
    
    public int convertInteger(int scale, double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(scale, RoundingMode.HALF_UP);
        return bDecimal.intValue();
    }

  public String drawList(int iCommand, Vector objectClass, long oidPaymentRec, FrmBillMain frmObject, String isPack, boolean useGuidePrice) {
    //2
    ControlList ctrlist = new ControlList();
    String showColor = PstSystemProperty.getValueByName("CASHIER_SHOW_COLOR");
    String showSize = PstSystemProperty.getValueByName("CASHIER_SHOW_SIZE");
    String showPromotion = PstSystemProperty.getValueByName("CASHIER_SHOW_PROMOTION");
    String sizeType = PstSystemProperty.getValueByName("CASHIER_SIZE_GROUP_TYPE");
    String promotionType = PstSystemProperty.getValueByName("CASHIER_PROMOTION_GROUP_TYPE");

    ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
    ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
    ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
    ctrlist.setCellStyle("cellStyle");
    ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
    ctrlist.addHeader("No", "1%");
    ctrlist.addHeader("Name", "10%");
    if (showColor.equals("1")) {
      ctrlist.addHeader("Color", "10%");
    }
    if (showSize.equals("1")) {
      ctrlist.addHeader("Size", "10%");
    }
    if (showPromotion.equals("1")) {
      ctrlist.addHeader("Promotion", "10%");
    }
    ctrlist.addHeader("Quantity", "5%");
    ctrlist.addHeader("Price", "5%");
    ctrlist.addHeader("Discount", "5%");
    ctrlist.addHeader("Total", "5%");
    ctrlist.addHeader("Action", "1%");

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
    String addClass = "";
    int specialItem = 0;
    String fieldSpecial = "";

    //SISTEM PROPERTY UNTUK MENGECEK APAKAH KASIR SUPPORT PRODUKSI
    String cashierSupportProduction = PstSystemProperty.getValueByName("CASHIER_SUPPORT_PRODUCTION");
    String useProduction = PstSystemProperty.getValueByName("USE_PRODUCTION");
    //SISTEM PROPERTY UNTUK MENGECEK, APAKAH CHECK OUT VIA OUTLET
    String checkOutViaOutlet = PstSystemProperty.getValueByName("CHECKOUT_ITEM_VIA_OUTLET");
    if (objectClass.size() > 0) {
      for (int i = 0; i < objectClass.size(); i++) {

        //long famOid = 0;
        Billdetail billdetail = (Billdetail) objectClass.get(i);
        if (billdetail.getCutomePackBillingId() != 0) {
          isPack = "1";
        } else {
          isPack = "0";
        }
        BillMain billMain = new BillMain();
        try {
          billMain = PstBillMain.fetchExc(billdetail.getBillMainId());
        } catch (Exception exc) {
        }
        String bold = "";
        if (isPack.equals("1") && billdetail.getParentId() == 0) {
          bold = "font-weight:bold;";
        } else {
          bold = "";
        }

        double itemPrice = billdetail.getItemPrice();
        String realPrice = "";
        String priceStyle = "";
        if (useGuidePrice) {
          itemPrice = billdetail.getGuidePrice();
          realPrice = "" + Formater.formatNumber(billdetail.getItemPrice(), "#,###");
          priceStyle = "text-red text-bold";
        }
        String useForRaditya = PstSystemProperty.getValueByName("USE_FOR_RADITYA");
        String formulaCash = PstSystemProperty.getValueByName("CASH_FORMULA");
        Category cat = new Category();
        Material mat = new Material();
        try {
        mat = PstMaterial.fetchExc(billdetail.getMaterialId());
        cat = PstCategory.fetchExc(mat.getCategoryId());
        } catch (Exception e) {
        }
        double roundValue = 1;
        double total = billdetail.getTotalPrice() + billdetail.getTotalTax() + billdetail.getTotalSvc();
        if(useForRaditya.equals("1")){
//        if (checkString(formulaCash, "HPP") > -1) {
//          formulaCash = formulaCash.replaceAll("HPP", "" + mat.getAveragePrice()); 
//        }
////        if (checkString(formulaCash, "INCREASE") > -1) {
////          formulaCash = formulaCash.replaceAll("INCREASE", "" + cat.getKenaikanHarga());
////        }
//        double price = getValue(formulaCash);
//         price = Math.round(price);
//        itemPrice = price;
        total = billdetail.getTotalPrice()+ billdetail.getTotalTax();
        }
        double diskonValue = billdetail.getDisc();

        String realTotal = "";
        if (useGuidePrice) {
          total = (itemPrice - diskonValue) * billdetail.getQty();
          realTotal = Formater.formatNumber(billdetail.getTotalPrice() + billdetail.getTotalTax() + billdetail.getTotalSvc(), "#,###");
        }
        grandTotal += (itemPrice - diskonValue - billdetail.getDiscGlobal()) * billdetail.getQty();
        int countClass = 0;

        Color color = new Color();
        try {
          color = PstColor.fetchExc(mat.getPosColor());
        } catch (Exception exc) {
        }

        long oidMappingSize = PstMaterialMappingType.getSelectedTypeId(Integer.valueOf(sizeType), billdetail.getMaterialId());
        long oidPromotion = PstMaterialMappingType.getSelectedTypeId(Integer.valueOf(promotionType), billdetail.getMaterialId());

        DataCustom dataCustom = PstDataCustom.getDataCustom(billdetail.getOID(), "" + promotionType, "bill_detail_map_type");
        if (dataCustom.getOID() > 0) {
          oidPromotion = Long.valueOf(dataCustom.getDataValue());
        }

        String size = "-";
        String promotion = "-";

        try {
          MasterType type = PstMasterType.fetchExc(oidMappingSize);
          size = type.getMasterName();
        } catch (Exception exc) {
        }

        try {
          MasterType type = PstMasterType.fetchExc(oidPromotion);
          promotion = type.getMasterName();
        } catch (Exception exc) {
        }

        rowx = new Vector(1, 1);
        rowx.add("" + (i + 1) + ".");
        if (countClass == 0) {
          addClass = "firstFocus";
        } else {
          addClass = "";
        }
        rowx.add("<input style='" + bold + "' type='text' readonly='readonly' id='textual3_" + countClass + "' class='form-control textual3 " + addClass + "' value='" + billdetail.getItemName() + "'>");
        if (showColor.equals("1")) {
          rowx.add("" + color.getColorName());
        }
        if (showSize.equals("1")) {
          rowx.add("" + size);
        }
        if (showPromotion.equals("1")) {
          rowx.add("" + promotion);
        }
        rowx.add("" + billdetail.getQty());
        rowx.add("<div class='" + priceStyle + "' title='" + realPrice + "'>" + Formater.formatNumber(itemPrice, "#,###") + "</div>");
        rowx.add("" + Formater.formatNumber(diskonValue, "#,###") + "");
        if ((i + 1) == objectClass.size()) {
          addInner += "<input type='hidden' id='grandTotalData' value='" + Formater.formatNumber(grandTotal, "#,###") + "'>";
        }
        rowx.add("<div class='" + priceStyle + "' title='" + realTotal + "'>" + Formater.formatNumber(total, "#,###") + addInner + "</div>");
        String tempRow = ""
                + "<div class='btn-group'>"
                + "<button class='btn btn-primary dropdown-toggle' type='button' data-toggle='dropdown'>"
                + "Actions <span class='fa fa-caret-right'></span>"
                + "</button>"
                + "<ul class='dropdown-menu dropdown-menu-right' role='menu' style='position: absolute;top:-100%;'>"
                + "<li role='presentation'><a role='menuitem' tabindex='-1' href='#' data-load-type='edititem' class='authorize editmainitem' id='editmainitem_" + i + "' data-title='EDIT ITEM' data-oid='" + billdetail.getOID() + "' data-consume-id='" + billdetail.getCutomePackBillingId() + "' data-schedule-id='" + billdetail.getCostumeScheduleId() + "'><i class='fa fa-pencil'></i> Edit</a></li>"
                + "<li role='presentation'><a role='menuitem' tabindex='-1' href='#' data-load-type='delitem' class='authorize deletemainitem' id='deletemainitem_" + i + "'  data-title='DELETE ITEM' data-oid='" + billdetail.getOID() + "'><i class='fa fa-remove'></i>Delete</a></li>"
                + "</ul>"
                + "";
        if (cashierSupportProduction.equals("1") && checkOutViaOutlet.equals("1")) {
          if (billdetail.getStatus() == 0 && billdetail.getStatusPrint() == 1) {
            tempRow += ""
                    + "&nbsp; <button data-billdetail='" + billdetail.getOID() + "'  type='button' class='btn btn-success confirmorder'>&nbsp; <i class='fa fa-check-circle'></i> &nbsp;</button> ";
          } else if (billdetail.getStatus() == 3 && billdetail.getStatusPrint() == 1) {
            tempRow += ""
                    + "&nbsp; <button type='button' class='btn btn-warning'>&nbsp;<i class='fa fa-star'></i> &nbsp; </button> ";
          }

        }
        tempRow += "</div>";
        if (useProduction.equals("1")) {
          if (billMain.getStatus() == PstBillMain.PETUGAS_DELIVERY_STATUS_DRAFT) {
            rowx.add("<div class='text-center'>" + tempRow + "</div>");
          } else {
            rowx.add("");
          }
        } else {
          rowx.add("<div class='text-center'>" + tempRow + "</div>");
        }
        lstData.add(rowx);
        countClass += 1;

        //get the child of these package
        String whereChild = ""
                + " billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + billdetail.getBillMainId() + "' "
                + " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                + " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                + " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                + " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                + " AND billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_STATUS] + " <> '3'"
                + " AND billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID] + "='" + billdetail.getOID() + "'";

        Vector listChild = PstCustomBillMain.listItemOpenBill(0, 0, whereChild, "");
        for (int j = 0; j < listChild.size(); j++) {
          Billdetail entBillDetail = new Billdetail();
          entBillDetail = (Billdetail) listChild.get(j);
          rowx = new Vector(1, 1);
          rowx.add("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + (j + 1));
          if (countClass == 0) {
            addClass = "firstFocus";
          } else {
            addClass = "";
          }
          rowx.add("<input style='padding-left: 35px;' type='text' readonly='readonly' id='textual3_" + countClass + "' class='form-control textual3 " + addClass + "' value='" + entBillDetail.getItemName() + "'>");
          rowx.add("" + entBillDetail.getQty());
          rowx.add("" + Formater.formatNumber(entBillDetail.getItemPrice(), "#,###"));
          rowx.add("" + Formater.formatNumber(entBillDetail.getDisc(), "#,###"));
          rowx.add("" + Formater.formatNumber(entBillDetail.getTotalPrice(), "#,###") + addInner);
          String tempRow2 = ""
                  + "<div class='btn-group'>"
                  //+ "<div class='dropdown'>"
                  + "<button class='btn btn-primary dropdown-toggle' type='button' data-toggle='dropdown'>Actions "
                  + "<span class='fa fa-caret-right'></span></button>"
                  + "<ul class='dropdown-menu dropdown-menu-right' role='menu' style='position: absolute;top:-100%;'>"
                  + "<li role='presentation'><a role='menuitem' tabindex='-1' href='#' data-load-type='edititem' class='authorize editmainitem' id='editmainitem_" + i + "' data-title='EDIT ITEM' data-oid='" + entBillDetail.getOID() + "'><i class='fa fa-pencil'></i> Edit</a></li>"
                  + "<li role='presentation'><a role='menuitem' tabindex='-1' href='#' data-load-type='delitem' class='authorize deletemainitem' id='deletemainitem_" + i + "'  data-title='DELETE ITEM' data-oid='" + entBillDetail.getOID() + "'><i class='fa fa-remove'></i>Delete</a></li>"
                  + "</ul>"
                  //+ "</div>"
                  + "";
          if (cashierSupportProduction.equals("1") && checkOutViaOutlet.equals("1")) {
            if (entBillDetail.getStatus() == 0 && entBillDetail.getStatusPrint() == 1) {
              tempRow2 += ""
                      + "&nbsp; <button data-billdetail='" + entBillDetail.getOID() + "'  type='button' class='btn btn-success confirmorder'>&nbsp; <i class='fa fa-check-circle'></i> &nbsp;</button> ";
            } else if (entBillDetail.getStatus() == 3 && entBillDetail.getStatusPrint() == 1) {
              tempRow2 += ""
                      + "&nbsp; <button type='button' class='btn btn-warning'>&nbsp;<i class='fa fa-star'></i> &nbsp; </button> ";
            }

          }
          tempRow2 += "</div>";
          rowx.add(tempRow2);
          lstData.add(rowx);
          countClass += 1;
        }

        if (isPack.equals("1") && billdetail.getParentId() == 0) {
          rowx = new Vector(1, 1);

          rowx.add("&nbsp;");
          rowx.add("&nbsp;");
          rowx.add("&nbsp;");
          rowx.add("&nbsp;");
          rowx.add("&nbsp;");
          rowx.add("&nbsp;");
          rowx.add(""
                  + "<button data-parentId='" + billdetail.getOID() + "' class='btn btn-primary btnAddPackageItem' > "
                  + "Add Item Package"
                  + "</button>"
                  + "");
          lstData.add(rowx);
        }

      }
    } else {
      rowx = new Vector(1, 1);
      rowx.add("");
      rowx.add("");
      rowx.add("");
      rowx.add("");
      rowx.add("");
      addInner += "<input type='hidden' id='grandTotalData' value='0'>";
      rowx.add("" + addInner);
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
    ctrlist.addHeader("No", "1%");
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
    String addClass = "";
    Vector rowx = new Vector(1, 1);
    double grandTotal = 0;
    String addInner = "";
    int counterHelp = 0;
    for (int i = 0; i < objectClass.size(); i++) {
      //long famOid = 0;

      //firstFocus class
      if (i == 0) {
        addClass = "firstFocus";
      } else if (i == (objectClass.size() - 1)) {
        addClass = "lastFocus";
      } else {
        addClass = "";
      }

      Billdetail billdetail = (Billdetail) objectClass.get(i);
      BillMain billMain = new BillMain();

      //cek apakah item ini sudah pernah di return sebelumnya
      String whereSumQty = ""
              + " " + PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_RETURN_ID] + "='" + billdetail.getOID() + "'";
      double sumQty = PstBillDetail.getSumTotalItemReturn(whereSumQty);
      double remainItem = billdetail.getQty() - sumQty;

      try {
        billMain = PstBillMain.fetchExc(billdetail.getBillMainId());
      } catch (Exception e) {
      }

      int checkReturn = PstCustomBillMain.checkReturn(oidPaymentRec, billdetail.getOID());

      if (checkReturn > 0) {
        if (remainItem > 0) {
          rowx = new Vector(1, 1);
          grandTotal += billdetail.getTotalPrice();

          rowx.add("" + (counterHelp + 1) + ".");
          rowx.add("" + billdetail.getItemName());
          //rowx.add(""+billdetail.getQty());
          rowx.add("" + remainItem);
          rowx.add("" + Formater.formatNumber(billdetail.getItemPrice(), "#,###"));
          rowx.add("" + Formater.formatNumber(billdetail.getDisc(), "#,###"));
          if ((i + 1) == objectClass.size()) {
            addInner += "<input type='hidden' id='grandTotalData' value='" + Formater.formatNumber(grandTotal, "#,###") + "'>";
          }
          double priceShow = 0;
          //priceShow = (billdetail.getItemPrice()-billdetail.getDisc())*billdetail.getQty();
          priceShow = (billdetail.getItemPrice() - billdetail.getDisc()) * remainItem;
          rowx.add("" + Formater.formatNumber(priceShow, "#,###") + addInner);
          rowx.add("<input  type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] + "_" + i + "' value='0' id='qtyTemp-" + i + "' class='form-control qtytemp returntext textual6 " + addClass + " ' style='width:100px;'> "
                  + "<input type='hidden' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID] + "' value='" + billdetail.getOID() + "'>"
                  + "<input type='hidden' class='qtyReturnHelp' id='qtyReturn-" + i + "' value='" + billdetail.getQty() + "'>");
          lstData.add(rowx);
          counterHelp += 1;
        } else {
          rowx = new Vector(1, 1);
          rowx.add("");
          rowx.add("");
          //rowx.add(""+billdetail.getQty());
          rowx.add("");
          rowx.add("");
          rowx.add("");
          if ((i + 1) == objectClass.size()) {
            addInner += "<input type='hidden' id='grandTotalData' value='" + Formater.formatNumber(grandTotal, "#,###") + "'>";
          }
          rowx.add("");
          rowx.add("<input  type='hidden' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] + "_" + i + "' value='0' id='qtyTemp-" + i + "' class='form-control qtytemp returntext textual6 " + addClass + " ' style='width:100px;'> "
                  + "<input type='hidden' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID] + "' value='" + billdetail.getOID() + "'>"
                  + "<input type='hidden' class='qtyReturnHelp' id='qtyReturn-" + i + "' value='" + billdetail.getQty() + "'>");
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
    String addClass = "";
    Vector rowx = new Vector(1, 1);
    double grandTotal = 0;
    String addInner = "";
    for (int i = 0; i < objectClass.size(); i++) {
      //long famOid = 0;

      //firstFocus class
      if (i == 0) {
        addClass = "firstFocus";
      } else if (i == (objectClass.size() - 1)) {
        addClass = "lastFocus";
      } else {
        addClass = "";
      }

      Billdetail billdetail = (Billdetail) objectClass.get(i);

      int checkReturn = PstCustomBillMain.checkReturn(oidPaymentRec, billdetail.getOID());

      if (checkReturn > 0) {
        rowx = new Vector(1, 1);
        grandTotal += billdetail.getTotalPrice();

        rowx.add("" + (i + 1));
        rowx.add("" + billdetail.getItemName());
        rowx.add("" + billdetail.getQty());
        rowx.add("" + Formater.formatNumber(billdetail.getItemPrice(), "#,###"));
        rowx.add("" + Formater.formatNumber(billdetail.getDisc(), "#,###"));
        if ((i + 1) == objectClass.size()) {
          addInner += "<input type='hidden' id='grandTotalData' value='" + Formater.formatNumber(grandTotal, "#,###") + "'>";
        }
        rowx.add("" + Formater.formatNumber(billdetail.getTotalPrice(), "#,###") + addInner);
        rowx.add("<input data-billdetiloid='" + billdetail.getOID() + "'  type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] + "_" + i + "' value='0' id='qtyJoinTemp-" + i + "' class='form-control qtyjointemp textual10 " + addClass + " ' style='width:100px;'> "
                + "<input type='hidden' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID] + "' value='" + billdetail.getOID() + "'>"
                + "<input type='hidden' class='qtyJoinHelp' id='qtyJoin-" + i + "' value='" + billdetail.getQty() + "'>");
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
    String addClass = "";
    for (int i = 0; i < objectClass.size(); i++) {
      //long famOid = 0;
      //First and Last Focus
      if (i == 0) {
        addClass = "firstFocus";
      } else if (i == (objectClass.size() - 1)) {
        addClass = "lastFocus";
      } else {
        addClass = "";
      }

      Billdetail billdetail = (Billdetail) objectClass.get(i);

      rowx = new Vector(1, 1);
      grandTotal += billdetail.getTotalPrice();

      rowx.add("" + (i + 1));
      rowx.add("" + billdetail.getItemName());
      rowx.add("" + billdetail.getQty());
      rowx.add("" + Formater.formatNumber(billdetail.getItemPrice(), "#,###"));
      rowx.add("" + Formater.formatNumber(billdetail.getDisc(), "#,###"));
      if ((i + 1) == objectClass.size()) {
        addInner += "<input type='hidden' id='grandTotalData' value='" + Formater.formatNumber(grandTotal, "#,###") + "'>";
      }
      rowx.add("" + Formater.formatNumber(billdetail.getTotalPrice(), "#,###") + addInner);
      rowx.add("<input type='hidden' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID] + "' value='" + billdetail.getOID() + "'>"
              + "<input type='hidden' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] + "_" + billdetail.getOID() + "' value='" + billdetail.getItemPrice() + "'>"
              + "<input type='hidden' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] + "_" + billdetail.getOID() + "' value='" + billdetail.getDisc() + "'>"
              + "<input type='text' id='moveBill-" + i + "'  class='form-control " + addClass + " textual7 " + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] + "' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] + "_" + billdetail.getOID() + "' value='0' size='1' data-max='" + billdetail.getQty() + "' data-min='0'>");
      lstData.add(rowx);
    }
    return ctrlist.drawBootstrapStripted();
  }

  public String drawListSearch(int iCommand, Vector objectClass, long oidPaymentRec, String loadtype) {
    //2

    /*System Property*/
    String useProduction = PstSystemProperty.getValueByName("USE_PRODUCTION");
    int showRoomOnBill = 0;
    int showTotalOnBill = 0;
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
    ctrlist.addHeader("Bill Number", "27%");
    ctrlist.addHeader("Location", "");
    ctrlist.addHeader("Date", "");
    if (showTotalOnBill == 1) {
      ctrlist.addHeader("Amount", "");
    }
    ctrlist.addHeader("Name", "");
    if (showRoomOnBill == 1) {
      ctrlist.addHeader("Room", "");
      ctrlist.addHeader("Table Number", "");
    }
    if (useProduction.equals("1")) {
      ctrlist.addHeader("Jenis Transaksi", "");
      ctrlist.addHeader("Status", "");
    }

    //membuat link dirow 0
    ctrlist.setLinkRow(0);
    ctrlist.setLinkSufix("");
    Vector lstData = ctrlist.getData();
    //membuat link menuju ke edit

    ctrlist.reset();

    int index = -1;
    String addClass = "";
    Vector rowx = new Vector(1, 1);
    if (objectClass.size() > 0) {
      for (int i = 0; i < objectClass.size(); i++) {
        //long famOid = 0;
        //untuk focus pertama di grid
        if (i == 0) {
          addClass = "firstFocus";
        } else {
          addClass = "";
        }
        BillMainCostum billMain = (BillMainCostum) objectClass.get(i);

        String guestNameTemp = "";

        BillMain entBillMain = new BillMain();
        try {
          entBillMain = PstBillMain.fetchExc(billMain.getOID());
        } catch (Exception e) {
        }
        if (entBillMain.getCustomerId() != 0 && entBillMain.getGuestName().length() == 0) {
          Contact contact = new Contact();
          try {
            contact = PstContact.fetchExc(entBillMain.getCustomerId());
          } catch (Exception e) {
          }
          if (contact.getPersonName().length() == 0) {
            guestNameTemp = contact.getCompName();
          } else {
            guestNameTemp = contact.getPersonName() + " " + contact.getPersonLastname();
          }
        } else {
          guestNameTemp = billMain.getGuestName();
        }

        int checkReturn;

        if (loadtype.equals("loadsearchreturn")) {
          checkReturn = PstCustomBillMain.checkReturn(billMain.getOID());
        } else {
          checkReturn = 1;
        }

        if (checkReturn > 0) {
          rowx = new Vector(1, 1);
          if (loadtype.equals("loadsearchreturn")) {
            rowx.add("<input style='cursor:pointer;color:#428bca' title='Return Bill' data-toggle='tooltip' type='text' readonly='readonly' class='form-control textual5 searchBillReturnSelect " + addClass + "' data-mainoid='" + billMain.getOID() + "'  value='" + billMain.getInvoiceNo() + "'>");
            rowx.add("" + billMain.getLocationName() + "");
            rowx.add("" + Formater.formatDate(billMain.getBillDate(), "yyyy-MM-dd kk:mm") + "");
            if (showTotalOnBill == 1) {
              //GET TOTAL PER BILL MAIN
              double totalAmount = PstBillDetail.getTotalAmount(billMain.getOID());
              double totalTax = PstBillDetail.getTotalAmountTax(billMain.getOID());
              double totalService = PstBillDetail.getTotalAmountService(billMain.getOID());
              rowx.add("<div style='text-align:right;'>" + Formater.formatNumber((totalAmount + totalTax + totalService), "###,###,##0") + "</div>");
            }

            rowx.add("" + guestNameTemp + "");
            if (showRoomOnBill == 1) {
              if (billMain.getRoomName() == null) {
                rowx.add("-");
              } else {
                rowx.add("" + billMain.getRoomName() + "");
              }
              if (billMain.getTableNumber() == null) {
                rowx.add("-");
              } else {
                rowx.add("" + billMain.getTableNumber() + "");
              }
            }
            if (useProduction.equals("1")) {
              rowx.add(PstBillMain.payType[entBillMain.getTransctionType()]);
              rowx.add(PstBillMain.produksiDeliveryStatus[entBillMain.getStatus()]);
            }

          } else {
            //cek apakah bill ini mengandung package 
            String whereClause = ""
                    + " billDetail." + PstBillDetail.fieldNames[PstBillDetail.FLD_CUSTOME_PACK_BILLING_ID] + "<>'0'"
                    + " AND billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'";
            int count = PstCustomBillMain.getTotalPackage(0, 0, whereClause, "");
            int dataPackage = 0;
            if (count > 0) {
              dataPackage = 1;
            }
            rowx.add("<input style='cursor:pointer;color:#428bca' title='Open Bill' data-toggle='tooltip' type='text' data-package='" + dataPackage + "' readonly='readonly' class='form-control textual2 searchBillSelect " + addClass + "' data-mainoid='" + billMain.getOID() + "' data-transtype='" + entBillMain.getTransctionType() + "' data-reservationid='" + billMain.getReservationId() + "' data-status='" + entBillMain.getStatus() + "' value='" + billMain.getInvoiceNo() + "'>");
            rowx.add("" + billMain.getLocationName() + "");
            rowx.add("" + Formater.formatDate(billMain.getBillDate(), "yyyy-MM-dd kk:mm") + "");
            if (showTotalOnBill == 1) {
              //GET TOTAL PER BILL MAIN
              double totalAmount = PstBillDetail.getTotalAmount(billMain.getOID());
              double totalTax = PstBillDetail.getTotalAmountTax(billMain.getOID());
              double totalService = PstBillDetail.getTotalAmountService(billMain.getOID());
              rowx.add("<div style='text-align:right;'>" + Formater.formatNumber((totalAmount + totalTax + totalService), "###,###,##0") + "</div>");
            }
            rowx.add("" + guestNameTemp + "");
            if (showRoomOnBill == 1) {
              if (billMain.getRoomName() == null) {
                rowx.add("-");
              } else {
                rowx.add("" + billMain.getRoomName() + "");
              }
              if (billMain.getTableNumber() == null) {
                rowx.add("-");
              } else {
                rowx.add("" + billMain.getTableNumber() + "");
              }
            }
            if (useProduction.equals("1")) {
              rowx.add(PstBillMain.payType[entBillMain.getTransctionType()]);
              rowx.add(PstBillMain.produksiDeliveryStatus[entBillMain.getStatus()]);
            }

          }

          lstData.add(rowx);
        }

      }
    } else {
      rowx = new Vector(1, 1);
      rowx.add("There is no bill left");
      rowx.add("");
      rowx.add("");
      if (showTotalOnBill == 1) {
        rowx.add("");
      }
      rowx.add("");
      if (showRoomOnBill == 1) {
        rowx.add("");
        rowx.add("");
      }
      if (useProduction.equals("1")) {
        rowx.add("");
        rowx.add("");
      }
      lstData.add(rowx);
    }

    return ctrlist.drawBootstrapStriptedClass2("navigateable");
  }

  public String drawListReprint(int iCommand, Vector objectClass, String loadtype, long oidPaymentRec) {
    /*System Property*/
    int showTables = 0;
    int showTotalOnBill = 0;
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
    ctrlist.addHeader("Bill Number", "20%");
    ctrlist.addHeader("Location", "20%");
    ctrlist.addHeader("Date", "20%");
    if (showTotalOnBill == 1) {
      ctrlist.addHeader("Amount", "20%");
    }
    ctrlist.addHeader("Name", "20%");
    if (showTables == 1) {
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

    String addClass = "";

    Vector rowx = new Vector(1, 1);
    if (objectClass.size() > 0) {
      for (int i = 0; i < objectClass.size(); i++) {
        //long famOid = 0;

        //Untuk keperluan first focus
        if (i == 0) {
          addClass = "firstFocus";
        } else {
          addClass = "";
        }

        BillMainCostum billMain = (BillMainCostum) objectClass.get(i);
        String guestNameTemp = "";

        BillMain entBillMain = new BillMain();
        try {
          entBillMain = PstBillMain.fetchExc(billMain.getOID());
        } catch (Exception e) {
        }
        if (entBillMain.getCustomerId() != 0 && entBillMain.getGuestName().length() == 0) {
          Contact contact = new Contact();
          try {
            contact = PstContact.fetchExc(entBillMain.getCustomerId());
          } catch (Exception e) {
          }
          if (contact.getPersonName().length() == 0) {
            guestNameTemp = contact.getCompName();
          } else {
            guestNameTemp = contact.getPersonName() + " " + contact.getPersonLastname();
          }
        } else {
          guestNameTemp = billMain.getGuestName();
        }

        //added by dewok 20190218, for guide price (kalo pake)
                boolean useGuidePrice = checkMemberGuidePrice(billMain.getOID());
        String reprintGuidePrice = "";
                if (useGuidePrice) {
                    reprintGuidePrice += "<p></p><a class='reprint_guideprice' data-oid='"+billMain.getOID()+"' value='"+billMain.getInvoiceNo()+"' style='cursor:pointer; white-space: nowrap; font-size:14px;'>Print Bill Guide</a>";
                    reprintGuidePrice += "<br><a class='print_guidecommission' data-oid='"+billMain.getOID()+"' value='"+billMain.getInvoiceNo()+"' style='cursor:pointer; white-space: nowrap; font-size:14px;'>Print Komisi Guide</a>";
            }

                int checkReturn=0;
        rowx = new Vector(1, 1);

        rowx.add("<input type='text' class='form-control reprintBillSelect textual4 " + addClass + "' data-oid='" + billMain.getOID() + "' style='cursor:pointer; color:#428bca' data-oidmember='' readonly='readonly' title='Print Bill' data-toggle='tooltip' value='" + billMain.getInvoiceNo() + "'>" + reprintGuidePrice);
        rowx.add("" + billMain.getLocationName() + "");
        rowx.add("" + Formater.formatDate(billMain.getBillDate(), "yyyy-MM-dd kk:mm") + "");
        if (showTotalOnBill == 1) {
          //GET TOTAL PER BILL MAIN
          if (useGuidePrice) {
            double totalGuidePrice = PstBillDetail.getTotalGuidePrice(billMain.getOID());
            rowx.add("<div class='text-red' style='text-align:right;'>" + Formater.formatNumber(totalGuidePrice, "###,###,##0") + "</div>");
          } else {
            double totalAmount = PstBillDetail.getTotalAmount(billMain.getOID());
            double totalTax = PstBillDetail.getTotalAmountTax(billMain.getOID());
            double totalService = PstBillDetail.getTotalAmountService(billMain.getOID());
            rowx.add("<div style='text-align:right;'>" + Formater.formatNumber((totalAmount + totalTax + totalService), "###,###,##0") + "</div>");
          }
        }
        rowx.add("" + guestNameTemp + "");
        if (showTables == 1) {
          if (billMain.getRoomName() == null) {
            rowx.add("-");
          } else {
            rowx.add("" + billMain.getRoomName() + "");
          }
          if (billMain.getTableNumber() == null) {
            rowx.add("-");
          } else {
            rowx.add("" + billMain.getTableNumber() + "");
          }

        }

        lstData.add(rowx);
      }
    } else {
      rowx = new Vector(1, 1);
      rowx.add("There is no bill left");
      rowx.add("");
      rowx.add("");

      if (showTotalOnBill == 1) {
        rowx.add("");
      }
      rowx.add("");
      if (showTables == 1) {
        rowx.add("");
        rowx.add("");
      }
      lstData.add(rowx);
    }

    return ctrlist.drawBootstrapStriptedClass("navigateable");
  }
  public String drawListReprintPaymentCredit(Vector objectClass){
    ControlList ctrlist = new ControlList();

    ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
    ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
    ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
    ctrlist.setCellStyle("cellStyle");
    ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
    ctrlist.addHeader("No", "20%");
    ctrlist.addHeader("Credit Number", "20%");
    ctrlist.addHeader("Transaction Number", "40%");
    ctrlist.addHeader("Amount", "20%");
    ctrlist.addHeader("Name", "20%");

    //membuat link dirow 0
    ctrlist.setLinkRow(0);
    ctrlist.setLinkSufix("");
    Vector lstData = ctrlist.getData();
    //membuat link menuju ke edit
    ctrlist.reset();
    int index = -1;
    String addClass = "";

    Vector rowx = new Vector(1, 1);
    for (int i = 0; i < objectClass.size(); i++) {
    Transaksi t = (Transaksi) objectClass.get(i);
      Pinjaman p = new Pinjaman();
      ContactList a = new ContactList();
    try {
        p = PstPinjaman.fetchExc(t.getPinjamanId());
        a = PstContactList.fetchExc(p.getAnggotaId());
    } catch (Exception e) {
        System.out.println(e);
    }
      
      if (i == 0) {
        addClass = "firstFocus";
      } else {
        addClass = "";
      }
      int start = i +1;
      double nilaiTransaksi = p.getJumlahPinjaman();
      rowx = new Vector(1, 1);
      rowx.add(""+start);
      rowx.add("<input type='text' class='form-control reprintPayment textual4 " + addClass + "' data-oid='" + t.getOID() + "' style='cursor:pointer; color:#428bca' data-oidmember='' readonly='readonly' title='Reprint Payment' data-toggle='tooltip' value='" + p.getNoKredit() + "'>");
      rowx.add(""+t.getKodeBuktiTransaksi());
      rowx.add(""+Formater.formatNumber((p.getJumlahPinjaman()), "###,###,##0"));
      rowx.add(""+a.getPersonName());
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
    String addClass = "";

    Vector rowx = new Vector(1, 1);
    for (int i = 0; i < objectClass.size(); i++) {

      if (i == 0) {
        addClass = "firstFocus";
      } else {
        addClass = "";
      }

      MatCosting matCosting = (MatCosting) objectClass.get(i);
      if (matCosting.getPersonName() == null) {
        matCosting.setPersonName("");
      }
      int checkReturn = 0;
      rowx = new Vector(1, 1);
      rowx.add("<input type='text' class='form-control reprintBillFocSelect textual4 " + addClass + "' data-oid='" + matCosting.getOID() + "' style='cursor:pointer' readonly='readonly' value='" + matCosting.getCostingCode() + "'>");
      rowx.add("" + matCosting.getCostingDate() + "");
      rowx.add("" + matCosting.getPersonName() + "");
      rowx.add("" + matCosting.getLocationName() + "");
      lstData.add(rowx);
    }
    return ctrlist.drawBootstrapStriptedClass("navigateable");
  }

  public String drawListJoin(int iCommand, Vector objectClass, String loadtype, long oidPaymentRec) {
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

    String addClass = "";

    Vector rowx = new Vector(1, 1);
    for (int i = 0; i < objectClass.size(); i++) {
      //long famOid = 0;

      //Untuk keperluan first focus
      if (i == 0) {
        addClass = "firstFocus";
      } else {
        addClass = "";
      }

      BillMainCostum billMain = (BillMainCostum) objectClass.get(i);

      int checkReturn = 0;
      rowx = new Vector(1, 1);

      rowx.add("<input type='text' class='form-control jointBillSelect textual9 " + addClass + "' data-oid='" + billMain.getOID() + "' style='cursor:pointer' data-oidmember='' readonly='readonly' value='" + billMain.getInvoiceNo() + "'>");
      rowx.add("" + billMain.getBillDate() + "");
      rowx.add("" + billMain.getGuestName() + "");

      if (billMain.getRoomName() == null) {
        rowx.add("-");
      } else {
        rowx.add("" + billMain.getRoomName() + "");
      }
      if (billMain.getTableNumber() == null) {
        rowx.add("-");
      } else {
        rowx.add("" + billMain.getTableNumber() + "");
      }

      lstData.add(rowx);

    }
    return ctrlist.drawBootstrapStriptedClass("navigateable");
  }

  public String drawListSearchMember(int iCommand, Vector objectClass, long cashBillMainId, FrmBillMain frmObject, String loadtype) {
    //add opie-eyek 20170721
    //coba di cek disini, jika sudah di buatkan discount, tidak usah di tambahkan lagi

    String enableDutyFree = PstSystemProperty.getValueByName("ENABLE_DUTY_FREE");

    String useForRaditya = PstSystemProperty.getValueByName("USE_FOR_RADITYA");
    ControlList ctrlist = new ControlList();

    ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
    ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
    ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
    ctrlist.setCellStyle("cellStyle");
    ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
    ctrlist.addHeader("Name", "20%");
    ctrlist.addHeader("Phone Number", "20%");
    if(useForRaditya.equals("1")){
    ctrlist.addHeader("Address", "20%");
    }else{
    ctrlist.addHeader("Company", "20%");
   } if (enableDutyFree.equals("1")) {
      ctrlist.addHeader("Passport", "20%");
    }

    //membuat link dirow 0
    ctrlist.setLinkRow(0);
    ctrlist.setLinkSufix("");
    Vector lstData = ctrlist.getData();
    //membuat link menuju ke edit

    ctrlist.reset();

    int index = -1;

    BillMain billMain = new BillMain();
    if (cashBillMainId != 0) {
      try {
        billMain = PstBillMain.fetchExc(cashBillMainId);
      } catch (Exception ex) {
      }
    }

    Vector rowx = new Vector(1, 1);
    for (int i = 0; i < objectClass.size(); i++) {
      //long famOid = 0;
      ContactList contact = (ContactList) objectClass.get(i);
      PersonalDiscount personalDiscount = new PersonalDiscount();
      Vector listPersonDisc = PstPersonalDiscount.list(0, 0, "" + PstPersonalDiscount.fieldNames[PstPersonalDiscount.FLD_MATERIAL_ID] + "='0' "
              + "AND " + PstPersonalDiscount.fieldNames[PstPersonalDiscount.FLD_CONTACT_ID] + "='" + contact.getOID() + "'", "");
      if (listPersonDisc.size() != 0) {
        personalDiscount = (PersonalDiscount) listPersonDisc.get(0);
      } else {
        if (billMain.getDiscPct() != 0) {
          personalDiscount.setPersDiscPct(billMain.getDiscPct());
        }
        if (billMain.getDiscount() != 0) {
          personalDiscount.setPersDiscVal(billMain.getDiscount());
        }
      }
      MemberReg memberReg = new MemberReg();
      try {
        contact = PstContactList.fetchExc(contact.getOID());
        memberReg = PstMemberReg.fetchExc(contact.getOID());
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }

      rowx = new Vector(1, 1);

      rowx.add("<a class='selectGuest' data-loadtype='" + loadtype + "' data-oid='" + contact.getOID() + "' data-code='" + contact.getContactCode() + "' data-name='" + contact.getPersonName() + "' data-disc-pct='" + personalDiscount.getPersDiscPct() + "' data-disc-val='" + personalDiscount.getPersDiscVal() + "' data-phone-number='" + contact.getTelpMobile() + "' data-region='" + contact.getHomeCountry() + "' data-gender='" + contact.getMemberSex() + "' data-address='" + contact.getHomeAddress() + "' data-group='" + memberReg.getMemberGroupId() + "' style='cursor:pointer;'>" + contact.getPersonName() + "</a>");
      rowx.add("<a class='selectGuest' data-loadtype='" + loadtype + "' data-oid='" + contact.getOID() + "' data-code='" + contact.getContactCode() + "' data-name='" + contact.getPersonName() + "' data-disc-pct='" + personalDiscount.getPersDiscPct() + "' data-disc-val='" + personalDiscount.getPersDiscVal() + "' data-phone-number='" + contact.getTelpMobile() + "' data-region='" + contact.getHomeCountry() + "' data-gender='" + contact.getMemberSex() + "' data-address='" + contact.getHomeAddress() + "' data-group='" + memberReg.getMemberGroupId() + "' style='cursor:pointer;'>" + contact.getTelpMobile() + "</a>");
    if(useForRaditya.equals("1")){
      rowx.add("<a class='selectGuest' data-loadtype='" + loadtype + "' data-oid='" + contact.getOID() + "' data-code='" + contact.getContactCode() + "' data-name='" + contact.getPersonName() + "' data-disc-pct='" + personalDiscount.getPersDiscPct() + "' data-disc-val='" + personalDiscount.getPersDiscVal() + "' data-phone-number='" + contact.getTelpMobile() + "' data-region='" + contact.getHomeCountry() + "' data-gender='" + contact.getMemberSex() + "' data-address='" + contact.getHomeAddress() + "' data-group='" + memberReg.getMemberGroupId() + "' style='cursor:pointer;'>" + contact.getHomeAddress()+ "</a>");
    }else{
      rowx.add("<a class='selectGuest' data-loadtype='" + loadtype + "' data-oid='" + contact.getOID() + "' data-code='" + contact.getContactCode() + "' data-name='" + contact.getPersonName() + "' data-disc-pct='" + personalDiscount.getPersDiscPct() + "' data-disc-val='" + personalDiscount.getPersDiscVal() + "' data-phone-number='" + contact.getTelpMobile() + "' data-region='" + contact.getHomeCountry() + "' data-gender='" + contact.getMemberSex() + "' data-address='" + contact.getHomeAddress() + "' data-group='" + memberReg.getMemberGroupId() + "' style='cursor:pointer;'>" + contact.getCompName() + "</a>");
    }  if (enableDutyFree.equals("1")) {
        rowx.add("<a class='selectGuest' data-loadtype='" + loadtype + "' data-oid='" + contact.getOID() + "' data-code='" + contact.getContactCode() + "' data-name='" + contact.getPersonName() + "' data-disc-pct='" + personalDiscount.getPersDiscPct() + "' data-disc-val='" + personalDiscount.getPersDiscVal() + "' data-phone-number='" + contact.getTelpMobile() + "' data-region='" + contact.getHomeCountry() + "' data-gender='" + contact.getMemberSex() + "' data-address='" + contact.getHomeAddress() + "' data-group='" + memberReg.getMemberGroupId() + "' style='cursor:pointer;'>" + contact.getPassportNo() + "</a>");
      }
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
    ctrlist.addHeader("Room Number", "20%");
    ctrlist.addHeader("Check Out", "20%");

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

      if (listData.size() != 0) {
        contactList = (ContactList) listData.get(0);
        hotelRoom = (HotelRoom) listData.get(1);
        reservation = (Reservation) listData.get(2);
        contact = (Contact) listData.get(3);
      } else {
        contactList = new ContactList();
        hotelRoom = new HotelRoom();
        reservation = new Reservation();
        contact = new Contact();
      }

      rowx = new Vector(1, 1);
      if (searchtype.equals("guest")) {
        if (hotelRoom.getRoomNumber().length() == 0) {
          hotelRoom.setRoomNumber("OUTLET RSV");
        }
        rowx.add("<a class='selectGuest' data-oid='" + contactList.getOID() + "' data-oidreservasi='" + reservation.getOID() + "' data-name='" + contactList.getPersonName() + "' data-disc-pct='" + 0 + "' data-disc-val='" + 0 + "' style='cursor:pointer;'>" + contactList.getPersonName() + "</a>");
        rowx.add("<a class='selectGuest' data-oid='" + contactList.getOID() + "' data-oidreservasi='" + reservation.getOID() + "' data-name='" + contactList.getPersonName() + "' data-disc-pct='" + 0 + "' data-disc-val='" + 0 + "' style='cursor:pointer;'>" + hotelRoom.getRoomNumber() + "</a>");
        rowx.add("<a class='selectGuest' data-oid='" + contactList.getOID() + "' data-oidreservasi='" + reservation.getOID() + "' data-name='" + contactList.getPersonName() + "' data-disc-pct='" + 0 + "' data-disc-val='" + 0 + "' style='cursor:pointer;'>" + reservation.getChekOutDate() + "</a>");

      } else {
        if (reservation.getTravelAgentId() != 0) {
          rowx.add("<a class='selectGuest' data-oid='" + reservation.getTravelAgentId() + "' data-oidreservasi='" + reservation.getOID() + "' data-name='" + contactList.getPersonName() + "' data-disc-pct='" + 0 + "' data-disc-val='" + 0 + "' style='cursor:pointer;'>" + contactList.getPersonName() + " / " + contact.getCompName() + "</a>");
          rowx.add("<a class='selectGuest' data-oid='" + reservation.getTravelAgentId() + "' data-oidreservasi='" + reservation.getOID() + "' data-name='" + contactList.getPersonName() + "' data-disc-pct='" + 0 + "' data-disc-val='" + 0 + "' style='cursor:pointer;'>" + hotelRoom.getRoomNumber() + "</a>");
          rowx.add("<a class='selectGuest' data-oid='" + reservation.getTravelAgentId() + "' data-oidreservasi='" + reservation.getOID() + "' data-name='" + contactList.getPersonName() + "' data-disc-pct='" + 0 + "' data-disc-val='" + 0 + "' style='cursor:pointer;'>" + reservation.getChekOutDate() + "</a>");

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
          String approot, String oidMember) {

    String displayPrint = "";
    String transactionName = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
    String displayPaymentSystem = "";
    String displayDiscPct = "";
    String invoiceNumb = "";
    String paymentType = "";
    BillMainCostum billMainCostum;
    MatCosting matCosting;
    try {
      billMainCostum = PstCustomBillMain.fetchByCashierID(cashierId);
      Vector listMatCosting = PstMatCosting.list(0, 0,
              "" + PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER] + "='" + oidBillMain + "'", "");
      if (listMatCosting.size() != 0) {
        matCosting = (MatCosting) listMatCosting.get(0);
      } else {
        matCosting = new MatCosting();
      }

    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
      matCosting = new MatCosting();
    }

    if (printType.equals("printguest")) {
      transactionName = "";
    } else {
      if (transactionType == 1) {
        transactionName = "CREDIT";
        displayPaymentSystem = "CREDIT";
      } else if (transactionType == 0) {
        transactionName = "CASH";
      } else if (transactionType == 2) {
        transactionName = "COMPLIMENT";
      }
    }

    BillMain billMain = new BillMain();
    TableRoom tableRoom = new TableRoom();
    Vector listItem = new Vector(1, 1);
    CurrencyType currencyType = new CurrencyType();
    PaymentSystem paymentSystem = new PaymentSystem();
    int totalQty = 0;
    double totalPrice = 0;
    double service = 0;
    double tax = 0;
    double total = 0;

    try {
      billMain = PstBillMain.fetchExc(oidBillMain);
      if (taxinc == PstBillMain.INC_CHANGEABLE || taxinc == PstBillMain.INC_NOT_CHANGEABLE) {
        service = 0;
        tax = 0;
      } else {
        service = billMain.getServiceValue();
        tax = billMain.getTaxValue();
      }
    } catch (Exception ex) {
    }

    try {
      tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
    } catch (Exception ex) {
    }

    try {
      listItem = PstBillDetail.list(0, 0, "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'", "");
    } catch (Exception ex) {
    }

    try {
      paymentSystem = PstPaymentSystem.fetchExc(oidPayment);
      displayPaymentSystem = paymentSystem.getPaymentSystem();
    } catch (Exception ex) {
    }

    try {
      currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
    } catch (Exception ex) {
    }

    //opie 20171125 untuk proses log print dan berapa kali pernah di print.
    generateLogHistory(oidBillMain);
    String whereLog = PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DOCUMENT_ID] + "='" + billMain.getOID() + "' "
            + " AND " + PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DETAIL] + "='Print out bill'";
    int count = PstLogSysHistory.getCount(whereLog);

    String nameInvoice = "";
    if (printType == "printpay") {
      if (transactionType == 2) {
        nameInvoice = "COMPLIMENT";
        paymentType = "Compliment";
        invoiceNumb = matCosting.getCostingCode() + " / " + count;
      } else {
        paymentType = "Payment";
        nameInvoice = transactionName + " INVOICE";
        invoiceNumb = billMain.getInvoiceNumber() + " / " + count;
      }

    } else {
      nameInvoice = "BILL<br>";
      paymentType = "Payment";
      invoiceNumb = billMain.getInvoiceNumber() + " / " + count;
    }

    if (billMain.getDiscPct() > 0) {
      displayDiscPct = "(" + billMain.getDiscPct() + "%) ";
    }

    PrintTemplate printTemplate = new PrintTemplate();
    if (transactionType == 2) {
      //FOC
      displayPrint += printTemplate.PrintTemplateCompliment(billMainCostum, nameInvoice,
              invoiceNumb, cashierName, billMain, tableRoom, printType,
              paymentType, displayPaymentSystem, payAmount, oidPayment, ccName,
              ccNumber, ccValid, ccBank, ccCharge, approot, oidMember);
    } else {
      displayPrint += printTemplate.PrintTemplate(billMainCostum, nameInvoice,
              invoiceNumb, cashierName, billMain, tableRoom, printType,
              paymentType, displayPaymentSystem, payAmount, oidPayment, ccName,
              ccNumber, ccValid, ccBank, ccCharge, approot, oidMember);
    }

    return displayPrint;
  }

  public String drawPrintContinuesPayment(int transactionType, long oidBillMain, String cashierName,
          int taxinc, long oidPayment, String ccName, String ccNumber,
          String ccBank, String ccValid, double ccCharge,
          double payAmount, long cashierId, String printType,
          String approot, String oidMember) {

    String displayPrint = "";
    String transactionName = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
    String displayPaymentSystem = "";
    String displayDiscPct = "";
    String invoiceNumb = "";
    String paymentType = "";
    BillMainCostum billMainCostum;
    MatCosting matCosting;
    try {
      billMainCostum = PstCustomBillMain.fetchByCashierID(cashierId);
      Vector listMatCosting = PstMatCosting.list(0, 0,
              "" + PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER] + "='" + oidBillMain + "'", "");
      if (listMatCosting.size() != 0) {
        matCosting = (MatCosting) listMatCosting.get(0);
      } else {
        matCosting = new MatCosting();
      }

    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
      matCosting = new MatCosting();
    }

    if (printType.equals("printguest")) {
      transactionName = "";
    } else {
      if (transactionType == 1) {
        transactionName = "";
        displayPaymentSystem = "";
      } else if (transactionType == 0) {
        transactionName = "";
      } else if (transactionType == 2) {
        transactionName = "";
      }
    }

    BillMain billMain = new BillMain();
    TableRoom tableRoom = new TableRoom();
    Vector listItem = new Vector(1, 1);
    CurrencyType currencyType = new CurrencyType();
    PaymentSystem paymentSystem = new PaymentSystem();
    int totalQty = 0;
    double totalPrice = 0;
    double service = 0;
    double tax = 0;
    double total = 0;

    try {
      billMain = PstBillMain.fetchExc(oidBillMain);
      if (taxinc == PstBillMain.INC_CHANGEABLE || taxinc == PstBillMain.INC_NOT_CHANGEABLE) {
        service = 0;
        tax = 0;
      } else {
        service = billMain.getServiceValue();
        tax = billMain.getTaxValue();
      }
    } catch (Exception ex) {
    }

    try {
      tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
    } catch (Exception ex) {
    }

    try {
      listItem = PstBillDetail.list(0, 0, "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'", "");
    } catch (Exception ex) {
    }

    try {
      paymentSystem = PstPaymentSystem.fetchExc(oidPayment);
      displayPaymentSystem = paymentSystem.getPaymentSystem();
    } catch (Exception ex) {
    }

    try {
      currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
    } catch (Exception ex) {
    }

    //opie 20171125 untuk proses log print dan berapa kali pernah di print.
    generateLogHistory(oidBillMain);
    String whereLog = PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DOCUMENT_ID] + "='" + billMain.getOID() + "' "
            + " AND " + PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DETAIL] + "='Print out bill'";
    int count = PstLogSysHistory.getCount(whereLog);

    String nameInvoice = "";
    if (printType == "printpay") {
      if (transactionType == 2) {
        nameInvoice = "";
        paymentType = "";
        invoiceNumb = matCosting.getCostingCode() + " / " + count;
      } else {
        paymentType = "";
        nameInvoice = transactionName + " ";
        invoiceNumb = billMain.getInvoiceNumber() + " / " + count;
      }

    } else {
      nameInvoice = "BILL<br>";
      paymentType = "Payment";
      invoiceNumb = billMain.getInvoiceNumber() + " / " + count;
    }

    if (billMain.getDiscPct() > 0) {
      displayDiscPct = "(" + billMain.getDiscPct() + "%) ";
    }

    PrintTemplate printTemplate = new PrintTemplate();
    if (transactionType == 2) {
      displayPrint += printTemplate.PrintTemplateCompliment(billMainCostum, nameInvoice,
              invoiceNumb, cashierName, billMain, tableRoom, printType,
              paymentType, displayPaymentSystem, payAmount, oidPayment, ccName,
              ccNumber, ccValid, ccBank, ccCharge, approot, oidMember);
    } else {
      displayPrint += printTemplate.PrintTemplateContinuesPayment(billMainCostum, nameInvoice,
              invoiceNumb, cashierName, billMain, tableRoom, printType,
              paymentType, displayPaymentSystem, payAmount, oidPayment, ccName,
              ccNumber, ccValid, ccBank, ccCharge, approot, oidMember);
    }

    return displayPrint;
  }

  public String drawPrintPaymentOnly(int transactionType, long oidBillMain, String cashierName,
          int taxinc, long oidPayment, String ccName, String ccNumber,
          String ccBank, String ccValid, double ccCharge,
          double payAmount, long cashierId, String printType,
          String approot, String oidMember) {

    String displayPrint = "";
    String transactionName = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
    String displayPaymentSystem = "";
    String displayDiscPct = "";
    String invoiceNumb = "";
    String paymentType = "";
    BillMainCostum billMainCostum;
    MatCosting matCosting;
    try {
      billMainCostum = PstCustomBillMain.fetchByCashierID(cashierId);
      Vector listMatCosting = PstMatCosting.list(0, 0,
              "" + PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER] + "='" + oidBillMain + "'", "");
      if (listMatCosting.size() != 0) {
        matCosting = (MatCosting) listMatCosting.get(0);
      } else {
        matCosting = new MatCosting();
      }

    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
      matCosting = new MatCosting();
    }

    if (printType.equals("printguest")) {
      transactionName = "";
    } else {
      if (transactionType == 1) {
        transactionName = "CREDIT";
        displayPaymentSystem = "CREDIT";
      } else if (transactionType == 0) {
        transactionName = "CASH";
      } else if (transactionType == 2) {
        transactionName = "COMPLIMENT";
      }
    }

    BillMain billMain = new BillMain();
    TableRoom tableRoom = new TableRoom();
    Vector listItem = new Vector(1, 1);
    CurrencyType currencyType = new CurrencyType();
    PaymentSystem paymentSystem = new PaymentSystem();
    int totalQty = 0;
    double totalPrice = 0;
    double service = 0;
    double tax = 0;
    double total = 0;

    try {
      billMain = PstBillMain.fetchExc(oidBillMain);
      if (taxinc == PstBillMain.INC_CHANGEABLE || taxinc == PstBillMain.INC_NOT_CHANGEABLE) {
        service = 0;
        tax = 0;
      } else {
        service = billMain.getServiceValue();
        tax = billMain.getTaxValue();
      }
    } catch (Exception ex) {
    }

    try {
      tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
    } catch (Exception ex) {
    }

    try {
      listItem = PstBillDetail.list(0, 0, "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'", "");
    } catch (Exception ex) {
    }

    try {
      paymentSystem = PstPaymentSystem.fetchExc(oidPayment);
      displayPaymentSystem = paymentSystem.getPaymentSystem();
    } catch (Exception ex) {
    }

    try {
      currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
    } catch (Exception ex) {
    }

    //add opie-eyek 20171125 untuk log history print dan jumlah berapa kali print
    generateLogHistory(oidBillMain);
    String whereLog = PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DOCUMENT_ID] + "='" + billMain.getOID() + "' "
            + " AND " + PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DETAIL] + "='Print out bill'";
    int count = PstLogSysHistory.getCount(whereLog);

    String nameInvoice = "";
    if (printType == "printpay") {
      if (transactionType == 2) {
        nameInvoice = "COMPLIMENT";
        paymentType = "Compliment";
        invoiceNumb = matCosting.getCostingCode() + " / " + count;
      } else {
        paymentType = "Payment";
        nameInvoice = transactionName + " INVOICE";
        invoiceNumb = billMain.getInvoiceNumber() + " / " + count;
      }

    } else {
      nameInvoice = "BILL";
      paymentType = "Payment";
      invoiceNumb = billMain.getInvoiceNumber() + " / " + count;
    }

    if (billMain.getDiscPct() > 0) {
      displayDiscPct = "(" + billMain.getDiscPct() + "%) ";
    }

    PrintTemplate printTemplate = new PrintTemplate();
    if (transactionType == 2) {
      //FOC

      displayPrint += printTemplate.PrintTemplateComplimentPaymentOnly(billMainCostum, nameInvoice,
              invoiceNumb, cashierName, billMain, tableRoom, printType,
              paymentType, displayPaymentSystem, payAmount, oidPayment, ccName,
              ccNumber, ccValid, ccBank, ccCharge, approot, oidMember);
    } else {
      displayPrint += printTemplate.PrintTemplatePaymentOnly(billMainCostum, nameInvoice,
              invoiceNumb, cashierName, billMain, tableRoom, printType,
              paymentType, displayPaymentSystem, payAmount, oidPayment, ccName,
              ccNumber, ccValid, ccBank, ccCharge, approot, oidMember);
    }

    return displayPrint;
  }

  public String drawRePrint(long oidBillMain, String approot, String oidMember, int full) {
    String displayPrint = "";
    String transactionName = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
    String displayPaymentSystem = "";
    String displayDiscPct = "";
    String invoiceNumb = "";
    String paymentType = "";
    String cashierName = "";
    BillMainCostum billMainCostum;
    Location location;
    MatCosting matCosting;
    BillMain billMain;
    BillMain lastBillMain;
    Vector listBillMain = PstBillMain.list(0, 0, "" + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oidBillMain + "'", "" + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + " DESC");
    if (listBillMain.size() != 0) {
      billMain = (BillMain) listBillMain.get(0);

    } else {
      billMain = new BillMain();
    }
    AppUser entCashier = new AppUser();
    try {
      entCashier = PstAppUser.fetch(billMain.getAppUserId());
    } catch (Exception e) {
    }
    //cashierName = billMain.getLocationId();
    cashierName = entCashier.getFullName();
    try {
      lastBillMain = PstBillMain.fetchExc(oidBillMain);
      billMainCostum = PstCustomBillMain.fetchByCashierID(billMain.getCashCashierId());
      location = PstLocation.fetchExc(billMain.getLocationId());
      Vector listMatCosting = PstMatCosting.list(0, 0, "" + PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER] + "='" + oidBillMain + "'", "");
      if (listMatCosting.size() != 0) {
        matCosting = (MatCosting) listMatCosting.get(0);
      } else {
        matCosting = new MatCosting();
      }

    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
      matCosting = new MatCosting();
      location = new Location();
      lastBillMain = new BillMain();
    }

    transactionName = "REPRINT";

    TableRoom tableRoom = new TableRoom();
    Vector listItem = new Vector(1, 1);
    CurrencyType currencyType = new CurrencyType();
    PaymentSystem paymentSystem = new PaymentSystem();
    int totalQty = 0;
    double totalPrice = 0;
    double service = 0;
    double tax = 0;
    double total = 0;

    try {
      //billMain = PstBillMain.fetchExc(oidBillMain);
      if (location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE) {
        service = 0;
        tax = 0;
      } else {
        service = billMain.getServiceValue();
        tax = billMain.getTaxValue();
      }
    } catch (Exception ex) {
    }

    try {
      tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
    } catch (Exception ex) {
    }

    try {
      listItem = PstBillDetail.list(0, 0, "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'", "");
    } catch (Exception ex) {
    }

    Vector listCashPayment = PstCashPayment.list(0, 0, "" + PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'", "");
    CashPayments cashPayments;
    if (listCashPayment.size() != 0) {
      cashPayments = (CashPayments) listCashPayment.get(0);
    } else {
      cashPayments = new CashPayments();
    }
    try {
      paymentSystem = PstPaymentSystem.fetchExc(cashPayments.getPaymentTypeLong());
      displayPaymentSystem = paymentSystem.getPaymentSystem();
    } catch (Exception ex) {
    }

    try {
      currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
    } catch (Exception ex) {
    }

    String nameInvoice = "";
    //opie-eyek 20171125 tambahkan log re print dan berapa kali melakukan print
    generateLogHistory(oidBillMain);
    String whereLog = PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DOCUMENT_ID] + "='" + billMain.getOID() + "' "
            + " AND " + PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DETAIL] + "='Print out bill'";
    int count = PstLogSysHistory.getCount(whereLog);

    paymentType = "Reprint Bill";
    nameInvoice = transactionName + " Invoice";
    invoiceNumb = billMain.getInvoiceNumber() + " / " + count;

    if (billMain.getDiscPct() > 0) {
      displayDiscPct = "(" + billMain.getDiscPct() + "%) ";
    }

    CustomCashCreditCard customCashCreditCard;
    Vector listCreditCard = PstCustomCashCreditCard.list(0, 0, "" + PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CASH_PAYMENT_ID] + "='" + cashPayments.getOID() + "'", "");
    if (listCreditCard.size() != 0) {
      customCashCreditCard = (CustomCashCreditCard) listCreditCard.get(0);
    } else {
      customCashCreditCard = new CustomCashCreditCard();
    }

    String ccName = "";
    String ccNumber = "";
    String ccBank = "";
    double ccCharge = 0;
    String ccValid = "";

    if (paymentSystem.isCardInfo() == true
            && paymentSystem.getPaymentType() == 0
            && paymentSystem.isBankInfoOut() == false
            && paymentSystem.isCheckBGInfo() == false) {

      ccName = customCashCreditCard.getCcName();
      ccNumber = customCashCreditCard.getCcNumber();
      ccBank = customCashCreditCard.getDebitBankName();
      ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
      ccCharge = customCashCreditCard.getBankCost();

      /////BG PAYMENT
    } else if (paymentSystem.isCardInfo() == false
            && paymentSystem.getPaymentType() == 2
            && paymentSystem.isBankInfoOut() == true
            && paymentSystem.isCheckBGInfo() == false) {

      ccName = customCashCreditCard.getChequeAccountName();
      ccNumber = customCashCreditCard.getCcNumber();
      ccBank = customCashCreditCard.getChequeBank();
      ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
      ccCharge = customCashCreditCard.getBankCost();

      /////CHECK PAYMENT
    } else if (paymentSystem.isCardInfo() == true
            && paymentSystem.getPaymentType() == 0
            && paymentSystem.isBankInfoOut() == false
            && paymentSystem.isCheckBGInfo() == false) {

      ccName = customCashCreditCard.getChequeAccountName();
      ccNumber = customCashCreditCard.getCcNumber();
      ccBank = customCashCreditCard.getChequeBank();
      ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
      ccCharge = customCashCreditCard.getBankCost();

      /////DEBIT PAYMENT
    } else if (paymentSystem.isCardInfo() == false
            && paymentSystem.getPaymentType() == 2
            && paymentSystem.isBankInfoOut() == false
            && paymentSystem.isCheckBGInfo() == false) {

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
            ccNumber, ccValid, ccBank, ccCharge, approot, oidMember, full);

    return displayPrint;
  }

  public String drawRePrintGuidePrice(long oidBillMain, String approot, String oidMember, int full) {
    String displayPrint = "";
    String transactionName = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
    String displayPaymentSystem = "";
    String displayDiscPct = "";
    String invoiceNumb = "";
    String paymentType = "";
    String cashierName = "";
    BillMainCostum billMainCostum;
    Location location;
    MatCosting matCosting;
    BillMain billMain;
    BillMain lastBillMain;
    Vector listBillMain = PstBillMain.list(0, 0,
            "" + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oidBillMain + "'",
            "" + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + " DESC");
    if (listBillMain.size() != 0) {
      billMain = (BillMain) listBillMain.get(0);

    } else {
      billMain = new BillMain();
    }
    AppUser entCashier = new AppUser();
    try {
      entCashier = PstAppUser.fetch(billMain.getAppUserId());
    } catch (Exception e) {
    }
    //cashierName = billMain.getLocationId();
    cashierName = entCashier.getFullName();
    try {
      lastBillMain = PstBillMain.fetchExc(oidBillMain);
      billMainCostum = PstCustomBillMain.fetchByCashierID(billMain.getCashCashierId());
      location = PstLocation.fetchExc(billMain.getLocationId());
      Vector listMatCosting = PstMatCosting.list(0, 0,
              "" + PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER] + "='" + oidBillMain + "'", "");
      if (listMatCosting.size() != 0) {
        matCosting = (MatCosting) listMatCosting.get(0);
      } else {
        matCosting = new MatCosting();
      }

    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
      matCosting = new MatCosting();
      location = new Location();
      lastBillMain = new BillMain();
    }

    transactionName = "REPRINT";

    TableRoom tableRoom = new TableRoom();
    Vector listItem = new Vector(1, 1);
    CurrencyType currencyType = new CurrencyType();
    PaymentSystem paymentSystem = new PaymentSystem();
    int totalQty = 0;
    double totalPrice = 0;
    double service = 0;
    double tax = 0;
    double total = 0;

    try {
      //billMain = PstBillMain.fetchExc(oidBillMain);
      if (location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE) {
        service = 0;
        tax = 0;
      } else {
        service = billMain.getServiceValue();
        tax = billMain.getTaxValue();
      }
    } catch (Exception ex) {
    }

    try {
      tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
    } catch (Exception ex) {
    }

    try {
      listItem = PstBillDetail.list(0, 0, "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'", "");
    } catch (Exception ex) {
    }

    Vector listCashPayment = PstCashPayment.list(0, 0,
            "" + PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'",
            "");
    CashPayments cashPayments;
    if (listCashPayment.size() != 0) {
      cashPayments = (CashPayments) listCashPayment.get(0);
    } else {
      cashPayments = new CashPayments();
    }
    try {
      paymentSystem = PstPaymentSystem.fetchExc(cashPayments.getPaymentTypeLong());
      displayPaymentSystem = paymentSystem.getPaymentSystem();
    } catch (Exception ex) {
    }

    try {
      currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
    } catch (Exception ex) {
    }

    String nameInvoice = "";
    //opie-eyek 20171125 tambahkan log re print dan berapa kali melakukan print
    generateLogHistory(oidBillMain);
    String whereLog = PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DOCUMENT_ID] + "='" + billMain.getOID() + "' "
            + " AND " + PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DETAIL] + "='Print out bill'";
    int count = PstLogSysHistory.getCount(whereLog);

    paymentType = "Reprint Bill";
    nameInvoice = transactionName + " Invoice";
    invoiceNumb = billMain.getInvoiceNumber() + " / " + count;

    if (billMain.getDiscPct() > 0) {
      displayDiscPct = "(" + billMain.getDiscPct() + "%) ";
    }

    CustomCashCreditCard customCashCreditCard;
    Vector listCreditCard = PstCustomCashCreditCard.list(0, 0,
            "" + PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CASH_PAYMENT_ID] + "='" + cashPayments.getOID() + "'",
            "");
    if (listCreditCard.size() != 0) {
      customCashCreditCard = (CustomCashCreditCard) listCreditCard.get(0);
    } else {
      customCashCreditCard = new CustomCashCreditCard();
    }

    String ccName = "";
    String ccNumber = "";
    String ccBank = "";
    double ccCharge = 0;
    String ccValid = "";

    if (paymentSystem.isCardInfo() == true
            && paymentSystem.getPaymentType() == 0
            && paymentSystem.isBankInfoOut() == false
            && paymentSystem.isCheckBGInfo() == false) {

      ccName = customCashCreditCard.getCcName();
      ccNumber = customCashCreditCard.getCcNumber();
      ccBank = customCashCreditCard.getDebitBankName();
      ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
      ccCharge = customCashCreditCard.getBankCost();

      /////BG PAYMENT
    } else if (paymentSystem.isCardInfo() == false
            && paymentSystem.getPaymentType() == 2
            && paymentSystem.isBankInfoOut() == true
            && paymentSystem.isCheckBGInfo() == false) {

      ccName = customCashCreditCard.getChequeAccountName();
      ccNumber = customCashCreditCard.getCcNumber();
      ccBank = customCashCreditCard.getChequeBank();
      ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
      ccCharge = customCashCreditCard.getBankCost();

      /////CHECK PAYMENT
    } else if (paymentSystem.isCardInfo() == true
            && paymentSystem.getPaymentType() == 0
            && paymentSystem.isBankInfoOut() == false
            && paymentSystem.isCheckBGInfo() == false) {

      ccName = customCashCreditCard.getChequeAccountName();
      ccNumber = customCashCreditCard.getCcNumber();
      ccBank = customCashCreditCard.getChequeBank();
      ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
      ccCharge = customCashCreditCard.getBankCost();

      /////DEBIT PAYMENT
    } else if (paymentSystem.isCardInfo() == false
            && paymentSystem.getPaymentType() == 2
            && paymentSystem.isBankInfoOut() == false
            && paymentSystem.isCheckBGInfo() == false) {

      ccName = customCashCreditCard.getChequeAccountName();
      ccNumber = customCashCreditCard.getCcNumber();
      ccBank = customCashCreditCard.getDebitBankName();
      ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
      ccCharge = customCashCreditCard.getBankCost();

    }

    PrintTemplate printTemplate = new PrintTemplate();
    displayPrint += printTemplate.PrintTemplateReprintGuidePrice(billMainCostum, nameInvoice,
            invoiceNumb, String.valueOf(cashierName), billMain, tableRoom, "printpay",
            paymentType, displayPaymentSystem, total, paymentSystem.getOID(), ccName,
            ccNumber, ccValid, ccBank, ccCharge, approot, oidMember, full);

    return displayPrint;
  }

  public String drawRePrintFOC(MatCosting matCosting, String approot, int full) {

    String displayPrint = "";
    String transactionName = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
    String displayPaymentSystem = "";
    String displayDiscPct = "";
    String invoiceNumb = "";
    String paymentType = "";
    String nameInvoice = "";
    String cashierName = "";
    Location location;
    OpeningCashCashier openingCashCashier = new OpeningCashCashier();

    transactionName = "REPRINT";
    paymentType = "FOC";
    nameInvoice = transactionName + " Invoice";
    invoiceNumb = matCosting.getCostingCode();

    String whereCashCashier = " cc." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID] + "=" + matCosting.getCashCashierId() + "";
    Vector listCashCashier = PstCashCashier.listOpeningCashier(0, 1, whereCashCashier, "");
    if (listCashCashier.size() > 0) {
      openingCashCashier = (OpeningCashCashier) listCashCashier.get(0);
    }

    cashierName = openingCashCashier.getNameUser();
    PrintTemplate printTemplate = new PrintTemplate();
    displayPrint = printTemplate.PrintTemplateFOC(matCosting, approot, full, transactionName, paymentType, nameInvoice, invoiceNumb, cashierName);
    return displayPrint;
  }

    public String getPrintPaymentCredit(long oidTransaksi) {
        String namaCustomer = "-";
        String namaSales = "-";
        String nomorInduk = "-";
        Date tglPengambilan = null;
        String namaItem = "-";//bisa multiple dengan pemisah tanda plus (+)
        Date jatuhTempo = null;
        int angsuranKe = 0;
        double jumlahAngsuran = 0;
        double saldo = 0;
        String namaKolektor = "-";
        String namaAnalis = "-";
        String tglSetoran = "-";
        String tglCetak = Formater.formatDate(new Date(), "dd MMMM yyyy");
        String namaManager = "-";
        long oidPinjaman = 0;
        String alamat = "";
        double totalAngsuran = 0;
        double pokok = 0;
        double dibayar = 0;
        Vector listMaterial = new Vector();
        Vector angsuranTotal = new Vector();
        DecimalFormat df = new DecimalFormat("#.##");
        String spasiTTD = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
        Transaksi t = new Transaksi();
        int roundValue = 1;

        try {
            //
            t = PstTransaksi.fetchExc(oidTransaksi);
            nomorInduk = t.getKodeBuktiTransaksi();
            tglSetoran = Formater.formatDate(t.getTanggalTransaksi(), "dd-MM-yyyy");
            //
            Anggota a = PstAnggota.fetchExc(t.getIdAnggota());
            namaCustomer = a.getName();
            alamat = a.getAddressPermanent();
            //
            Pinjaman p = PstPinjaman.fetchExc(t.getPinjamanId());
            oidPinjaman = p.getOID();
            tglPengambilan = p.getTglRealisasi();
            //
            long oidKol = p.getCollectorId();
            long oidAo = p.getAccountOfficerId();
            Anggota kolektor = PstAnggota.fetchExc(oidKol);
            namaKolektor = kolektor.getName(); 
            Anggota ao = PstAnggota.fetchExc(oidAo);
            namaAnalis = ao.getName();
            //
            Vector<Angsuran> listAngsuran = PstAngsuran.list(0, 0, PstAngsuran.fieldNames[PstAngsuran.FLD_TRANSAKSI_ID] + " = " + t.getOID(), "");
            for (Angsuran angsuran : listAngsuran) {
                jumlahAngsuran += angsuran.getJumlahAngsuran();
                totalAngsuran = (Math.floor((jumlahAngsuran + (roundValue - 1)) / roundValue)) * roundValue;
                JadwalAngsuran jadwal = PstJadwalAngsuran.fetchExc(angsuran.getJadwalAngsuranId());
                jatuhTempo = jadwal.getTanggalAngsuran();
            }
            //
            //cari total dan sisa angsuran
            double totalPokok = PstAngsuran.getTotalAngsuran(p.getOID(), Transaksi.TIPE_DOC_KREDIT_NASABAH_POS_PIUTANG_POKOK);
            double pokokDibayar = PstAngsuran.getTotalAngsuranDibayar(p.getOID(), Transaksi.TIPE_DOC_KREDIT_NASABAH_POS_PIUTANG_POKOK);
            saldo = (Math.floor(((totalPokok - pokokDibayar) + (roundValue - 1)) / roundValue)) * roundValue;
            
            BillMain bm = PstBillMain.fetchExc(p.getBillMainId());
//            namaSales = bm.getSalesCode();

//            com.dimata.posbo.entity.masterdata.Sales sales = com.dimata.posbo.entity.masterdata.PstSales.getObjectSales(bm.getSalesCode());
            AppUser sales = PstAppUser.fetch(bm.getAppUserId());
            namaSales = sales.getFullName();

            listMaterial = PstBillDetail.list(0, 0, PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] +"="+ bm.getOID(), "");

            //
            angsuranKe = PstAngsuran.getAngsuranKe(oidPinjaman);
            
        } catch (DBException e) {
        }
        
        String html = ""
                + "<div class=\"col-md-12\">"
                + "<span style=\"font-size: 20px; border-bottom: 2px solid\">PT. RADITYA DEWATA PERKASA</span>"
                + "            <div style=\"font-size: 20px\">ELECTRONIC, MESIN, FURNITURE</div>"
                + "            <div style=\"font-size: 16px\">Jl.Gn.Sangiang No.17 R. Padang Sambian Telp.(0361) 430461 - fax 430462</div>"
                + ""
                + "            <br>"
                + ""
                + "            <div class=\"text-center\"><h4><b>BUKTI PEMBAYARAN SEWA BELI</b></h4></div>"
                + "            <p style=\"font-size: 16px\">"
                + "                <span>Sudah terima dari : " + namaCustomer + "</span>"
                + "                <span class=\"pull-right\">Sales : " + namaSales + "</span>"
                + "            </p>"
                + ""
                + "            <table class=\"table table-bordered tabel_data\">"
                + "                <tr class=\"\">"
                + "                    <td style=\"text-align: center;\">No. Induk</td>"
                + "                    <td style=\"text-align: center;\">Tgl. Pengambilan</td>"
                + "                    <td style=\"text-align: center;\" colspan=\"2\">Jenis Barang</td>"
                + "                </tr>"
                + "                <tr>"
                + "                    <td style=\"text-align: center;\" ><br>" + nomorInduk + "<br><br></td>"
                + "                    <td style=\"text-align: center;\" ><br>" + tglPengambilan + "<br><br></td>"
                + "                    <td  style=\"text-align: center;\" colspan=\"2\">";
                for(int i = 0; i < listMaterial.size(); i++){
                Billdetail bd = (Billdetail) listMaterial.get(i);
                namaItem = bd.getItemName();
                html+= "           <br>" + namaItem + "<br>";
                }
                html+= "         </td>"
                + "                 </tr>"
                + "                <tr class=\"\">"
                + "                    <td style=\"text-align: center;\">Jatuh Tempo</td>"
                + "                    <td style=\"text-align: center;\">Angsuran ke</td>"
                + "                    <td style=\"text-align: center;\">Jumlah Angsuran</td>"
                + "                    <td style=\"text-align: center;\">Saldo</td>"
                + "                </tr>"
                + "                <tr>"
                + "                    <td style=\"text-align: center;\"><br>" + jatuhTempo + "<br><br></td>"
                + "                    <td style=\"text-align: center;\"><br>" + angsuranKe + "<br><br></td>"
                + "                    <td style=\"text-align: center;\"><br>Rp. " + FRMHandler.userFormatStringDecimal(jumlahAngsuran) + "<br><br></td>"
                + "                    <td style=\"text-align: center;\"><br>Rp. " + FRMHandler.userFormatStringDecimal(saldo) + "<br><br></td>"
                + "                </tr>"
                + "                <tr class=\"\">"
                + "                    <td style=\"text-align: center;\">Nama Kolektor</td>"
                + "                    <td style=\"text-align: center;\">Nama Analis</td>"
                + "                    <td style=\"text-align: center;\">TTD Kolektor</td>"
                + "                    <td style=\"text-align: center;\">Tgl. Setoran</td>"
                + "                </tr>"
                + "                <tr>"
                + "                    <td style=\"text-align: center;\"><br>" + namaKolektor + "<br><br></td>"
                + "                    <td style=\"text-align: center;\"><br>" + namaAnalis + "<br><br></td>"
                + "                    <td></td>"
                + "                    <td style=\"text-align: center;\"><br>" + tglSetoran + "<br><br></td>"
                + "                </tr>"
                + "            </table>"
                + ""
                + "            <div style=\"font-size: 16px;\">"
                + "                <div  class=\"pull-left\" style=\"width: 50%; text-align: center;\">Alamat : "+ alamat +"</div>"
                + "                <div  class=\"pull-right\" style=\"width: 50%; text-align: center;\">Denpasar, " + tglCetak + "</div><br>"
                + "                <div  class=\"pull-right\" style=\"width: 50%; text-align: center;\">PT. RADITYA DEWATA PERKASA</div>"
                + "            </div>"
                + "             <br><br><br><br><br>"
                + "            <div style=\"font-size: 16px;\">"
                  + "            <div style=\"font-size: 16px; width: 50%; text-align: center;\" class=\"pull-left\">"
                  + "                <div style=\"white-space: nowrap;\">(&nbsp; <u>" + namaCustomer + "</u> &nbsp;)</div>"
                  + "                <div style=\"white-space: nowrap;\">Konsumen</div>"
                  + "            </div>"
                  + "            <div style=\"font-size: 16px; width: 50%; text-align: center;\" class=\"pull-right\">"
                  + "                <div style=\"white-space: nowrap;\">(&nbsp; <u>" + namaManager + "</u> &nbsp;)</div>"
                  + "                <div style=\"white-space: nowrap;\">Manager</div>"
                  + "            </div>"
                  + "            </div>"
                + "            <br>"
                + "          </div>"
                + "";
        return html;
    }

  public String drawPrintNew(int transactionType, long oidBillMain, String cashierName,
          int taxinc, long oidPayment, String ccName, String ccNumber,
          String ccBank, String ccValid, double ccCharge,
          double payAmount, long cashierId, String printType,
          String approot, String oidMember, double taxs, double services, double discPct) {

    String displayPrint = "";
    String transactionName = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
    String displayPaymentSystem = "";
    String displayDiscPct = "";
    String invoiceNumb = "";
    String paymentType = "";
    BillMainCostum billMainCostum;
    MatCosting matCosting;
    try {
      billMainCostum = PstCustomBillMain.fetchByCashierID(cashierId);
      Vector listMatCosting = PstMatCosting.list(0, 0,
              "" + PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER] + "='" + oidBillMain + "'", "");
      if (listMatCosting.size() != 0) {
        matCosting = (MatCosting) listMatCosting.get(0);
      } else {
        matCosting = new MatCosting();
      }

    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
      matCosting = new MatCosting();
    }

    if (printType.equals("printguest")) {
      transactionName = "";
    } else {
      if (transactionType == 1) {
        transactionName = "CREDIT";
        displayPaymentSystem = "CREDIT";
      } else if (transactionType == 0) {
        transactionName = "CASH";
      } else if (transactionType == 2) {
        transactionName = "COMPLIMENT";
      }
    }

    //opie-eyek 20171125 tambahkan log re print dan berapa kali melakukan print
    generateLogHistory(oidBillMain);

    BillMain billMain = new BillMain();
    TableRoom tableRoom = new TableRoom();
    Vector listItem = new Vector(1, 1);
    CurrencyType currencyType = new CurrencyType();
    PaymentSystem paymentSystem = new PaymentSystem();
    int totalQty = 0;
    double totalPrice = 0;
    double service = 0;
    double tax = 0;
    double total = 0;

    try {
      billMain = PstBillMain.fetchExc(oidBillMain);
      if (taxinc == PstBillMain.INC_CHANGEABLE || taxinc == PstBillMain.INC_NOT_CHANGEABLE) {
        service = 0;
        tax = 0;
      } else {
        service = services;
        tax = taxs;
      }
    } catch (Exception ex) {
    }

    try {
      tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
    } catch (Exception ex) {
    }

    try {
      listItem = PstBillDetail.list(0, 0, "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'", "");
    } catch (Exception ex) {
    }

    try {
      paymentSystem = PstPaymentSystem.fetchExc(oidPayment);
      displayPaymentSystem = paymentSystem.getPaymentSystem();
    } catch (Exception ex) {
    }

    try {
      currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
    } catch (Exception ex) {
    }

    String nameInvoice = "";
    if (printType == "printpay") {
      if (transactionType == 2) {
        nameInvoice = "COMPLIMENT";
        paymentType = "Compliment";
        invoiceNumb = matCosting.getCostingCode();
      } else {
        paymentType = "Payment";
        nameInvoice = transactionName + " INVOICE";
        invoiceNumb = billMain.getInvoiceNumber();
      }

    } else {
      nameInvoice = "BILL";
      paymentType = "Payment";
      invoiceNumb = billMain.getInvoiceNumber();
    }

    if (billMain.getDiscPct() > 0) {
      displayDiscPct = "(" + discPct + "%) ";
    }

    PrintTemplate printTemplate = new PrintTemplate();
    displayPrint += printTemplate.PrintTemplateNew(billMainCostum, nameInvoice,
            invoiceNumb, cashierName, billMain, tableRoom, printType,
            paymentType, displayPaymentSystem, payAmount, oidPayment, ccName,
            ccNumber, ccValid, ccBank, ccCharge, approot, oidMember, tax, service, discPct);

    return displayPrint;
  }

  public String drawPrintGuidePrice(int transactionType, long oidBillMain, String cashierName,
          int taxinc, long oidPayment, String ccName, String ccNumber,
          String ccBank, String ccValid, double ccCharge,
          double payAmount, long cashierId, String printType,
          String approot, String oidMember, double taxs, double services, double discPct) {

    String displayPrint = "";
    String transactionName = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");
    String displayPaymentSystem = "";
    String displayDiscPct = "";
    String invoiceNumb = "";
    String paymentType = "";
    BillMainCostum billMainCostum;
    MatCosting matCosting;
    try {
      billMainCostum = PstCustomBillMain.fetchByCashierID(cashierId);
      Vector listMatCosting = PstMatCosting.list(0, 0,
              "" + PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER] + "='" + oidBillMain + "'", "");
      if (listMatCosting.size() != 0) {
        matCosting = (MatCosting) listMatCosting.get(0);
      } else {
        matCosting = new MatCosting();
      }

    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
      matCosting = new MatCosting();
    }

    if (printType.equals("printguest")) {
      transactionName = "";
    } else {
      if (transactionType == 1) {
        transactionName = "CREDIT";
        displayPaymentSystem = "CREDIT";
      } else if (transactionType == 0) {
        transactionName = "CASH";
      } else if (transactionType == 2) {
        transactionName = "COMPLIMENT";
      }
    }

    //opie-eyek 20171125 tambahkan log re print dan berapa kali melakukan print
    generateLogHistory(oidBillMain);

    BillMain billMain = new BillMain();
    TableRoom tableRoom = new TableRoom();
    Vector listItem = new Vector(1, 1);
    CurrencyType currencyType = new CurrencyType();
    PaymentSystem paymentSystem = new PaymentSystem();
    int totalQty = 0;
    double totalPrice = 0;
    double service = 0;
    double tax = 0;
    double total = 0;

    try {
      billMain = PstBillMain.fetchExc(oidBillMain);
      if (taxinc == PstBillMain.INC_CHANGEABLE || taxinc == PstBillMain.INC_NOT_CHANGEABLE) {
        service = 0;
        tax = 0;
      } else {
        service = services;
        tax = taxs;
      }
    } catch (Exception ex) {
    }

    try {
      tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
    } catch (Exception ex) {
    }

    try {
      listItem = PstBillDetail.list(0, 0, "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'", "");
    } catch (Exception ex) {
    }

    try {
      paymentSystem = PstPaymentSystem.fetchExc(oidPayment);
      displayPaymentSystem = paymentSystem.getPaymentSystem();
    } catch (Exception ex) {
    }

    try {
      currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
    } catch (Exception ex) {
    }

    String nameInvoice = "";
    if (printType == "printpay") {
      if (transactionType == 2) {
        nameInvoice = "COMPLIMENT";
        paymentType = "Compliment";
        invoiceNumb = matCosting.getCostingCode();
      } else {
        paymentType = "Payment";
        nameInvoice = transactionName + " INVOICE";
        invoiceNumb = billMain.getInvoiceNumber();
      }

    } else {
      nameInvoice = "BILL";
      paymentType = "Payment";
      invoiceNumb = billMain.getInvoiceNumber();
    }

    if (billMain.getDiscPct() > 0) {
      displayDiscPct = "(" + discPct + "%) ";
    }

    PrintTemplate printTemplate = new PrintTemplate();
    displayPrint += printTemplate.PrintTemplateGuidePrice(billMainCostum, nameInvoice,
            invoiceNumb, cashierName, billMain, tableRoom, printType,
            paymentType, displayPaymentSystem, payAmount, oidPayment, ccName,
            ccNumber, ccValid, ccBank, ccCharge, approot, oidMember, tax, service, discPct);

    return displayPrint;
  }

  public String drawPrintReturn(long oidBillMain, String cashierName, String approot, String oidMember) {

    String displayPrint = "";
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
            "" + PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] + "='" + oidBillMain + "'",
            "" + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + " DESC");
    if (listBillMain.size() != 0) {
      billMain = (BillMain) listBillMain.get(0);

    } else {
      billMain = new BillMain();
    }

    try {
      lastBillMain = PstBillMain.fetchExc(oidBillMain);
      billMainCostum = PstCustomBillMain.fetchByCashierID(billMain.getCashCashierId());
      location = PstLocation.fetchExc(billMain.getLocationId());
      Vector listMatCosting = PstMatCosting.list(0, 0,
              "" + PstMatCosting.fieldNames[PstMatCosting.FLD_INVOICE_SUPPLIER] + "='" + oidBillMain + "'", "");
      if (listMatCosting.size() != 0) {
        matCosting = (MatCosting) listMatCosting.get(0);
      } else {
        matCosting = new MatCosting();
      }

    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
      matCosting = new MatCosting();
      location = new Location();
      lastBillMain = new BillMain();
    }

    transactionName = "RETURN";

    TableRoom tableRoom = new TableRoom();
    Vector listItem = new Vector(1, 1);
    CurrencyType currencyType = new CurrencyType();
    PaymentSystem paymentSystem = new PaymentSystem();
    int totalQty = 0;
    double totalPrice = 0;
    double service = 0;
    double tax = 0;
    double total = 0;

    try {
      //billMain = PstBillMain.fetchExc(oidBillMain);
      if (location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE) {
        service = 0;
        tax = 0;
      } else {
        service = billMain.getServiceValue();
        tax = billMain.getTaxValue();
      }
    } catch (Exception ex) {
    }

    try {
      tableRoom = PstTableRoom.fetchExc(billMain.getTableId());
    } catch (Exception ex) {
    }

    try {
      listItem = PstBillDetail.list(0, 0, "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'", "");
    } catch (Exception ex) {
    }

    Vector listCashPayment = PstCashPayment.list(0, 0,
            "" + PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'",
            "");
    CashPayments cashPayments;
    if (listCashPayment.size() != 0) {
      cashPayments = (CashPayments) listCashPayment.get(0);
    } else {
      cashPayments = new CashPayments();
    }
    try {
      paymentSystem = PstPaymentSystem.fetchExc(cashPayments.getPaymentTypeLong());
      displayPaymentSystem = paymentSystem.getPaymentSystem();
    } catch (Exception ex) {
    }

    try {
      currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
    } catch (Exception ex) {
    }

    String nameInvoice = "";

    paymentType = "Return";
    nameInvoice = transactionName + " Invoice";
    invoiceNumb = billMain.getInvoiceNumber();

    if (billMain.getDiscPct() > 0) {
      displayDiscPct = "(" + billMain.getDiscPct() + "%) ";
    }

    CustomCashCreditCard customCashCreditCard;
    Vector listCreditCard = PstCustomCashCreditCard.list(0, 0,
            "" + PstCustomCashCreditCard.fieldNames[PstCustomCashCreditCard.FLD_CASH_PAYMENT_ID] + "='" + cashPayments.getOID() + "'",
            "");
    if (listCreditCard.size() != 0) {
      customCashCreditCard = (CustomCashCreditCard) listCreditCard.get(0);
    } else {
      customCashCreditCard = new CustomCashCreditCard();
    }

    String ccName = "";
    String ccNumber = "";
    String ccBank = "";
    double ccCharge = 0;
    String ccValid = "";

    if (paymentSystem.isCardInfo() == true
            && paymentSystem.getPaymentType() == 0
            && paymentSystem.isBankInfoOut() == false
            && paymentSystem.isCheckBGInfo() == false) {

      ccName = customCashCreditCard.getCcName();
      ccNumber = customCashCreditCard.getCcNumber();
      ccBank = customCashCreditCard.getDebitBankName();
      ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
      ccCharge = customCashCreditCard.getBankCost();

      /////BG PAYMENT
    } else if (paymentSystem.isCardInfo() == false
            && paymentSystem.getPaymentType() == 2
            && paymentSystem.isBankInfoOut() == true
            && paymentSystem.isCheckBGInfo() == false) {

      ccName = customCashCreditCard.getChequeAccountName();
      ccNumber = customCashCreditCard.getCcNumber();
      ccBank = customCashCreditCard.getChequeBank();
      ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
      ccCharge = customCashCreditCard.getBankCost();

      /////CHECK PAYMENT
    } else if (paymentSystem.isCardInfo() == true
            && paymentSystem.getPaymentType() == 0
            && paymentSystem.isBankInfoOut() == false
            && paymentSystem.isCheckBGInfo() == false) {

      ccName = customCashCreditCard.getChequeAccountName();
      ccNumber = customCashCreditCard.getCcNumber();
      ccBank = customCashCreditCard.getChequeBank();
      ccValid = Formater.formatDate(customCashCreditCard.getExpiredDate(), "yyyy-MM-dd");
      ccCharge = customCashCreditCard.getBankCost();

      /////DEBIT PAYMENT
    } else if (paymentSystem.isCardInfo() == false
            && paymentSystem.getPaymentType() == 2
            && paymentSystem.isBankInfoOut() == false
            && paymentSystem.isCheckBGInfo() == false) {

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

  public String drawPrintClosingDetail(Vector listCashier, String userName) {

    String displayPrint = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");

    CashCashier cashCashier;
    CashMaster cashMaster;
    Location location;

    if (listCashier.size() != 0) {
      cashCashier = (CashCashier) listCashier.get(0);
      try {
        cashCashier = PstCashCashier.fetchExc(cashCashier.getOID());
      } catch (Exception ex) {

      }
      cashMaster = (CashMaster) listCashier.get(1);
      try {
        cashMaster = PstCashMaster.fetchExc(cashMaster.getOID());
      } catch (Exception ex) {

      }
      location = (Location) listCashier.get(2);

    } else {
      cashCashier = new CashCashier();
      cashMaster = new CashMaster();
      location = new Location();
    }

    BillMainCostum billMainCostum;
    try {
      billMainCostum = PstCustomBillMain.fetchByCashierID(cashCashier.getOID());
    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
    }

    PrintTemplate printTemplate = new PrintTemplate();
    displayPrint += printTemplate.PrintTemplateGB(userName, cashCashier,
            billMainCostum, location, cashMaster);

    return displayPrint;
  }

  public String drawPrintClosing(Vector listCashier, String userName) {

    String useForGB = PstSystemProperty.getValueByName("USE_FOR_GREENBOWL");
    String displayPrint = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");

    CashCashier cashCashier;
    CashMaster cashMaster;
    Location location;

    if (listCashier.size() != 0) {
      cashCashier = (CashCashier) listCashier.get(0);
      try {
        cashCashier = PstCashCashier.fetchExc(cashCashier.getOID());
      } catch (Exception ex) {

      }
      cashMaster = (CashMaster) listCashier.get(1);
      try {
        cashMaster = PstCashMaster.fetchExc(cashMaster.getOID());
      } catch (Exception ex) {

      }
      location = (Location) listCashier.get(2);

    } else {
      cashCashier = new CashCashier();
      cashMaster = new CashMaster();
      location = new Location();
    }

    BillMainCostum billMainCostum;
    try {
      billMainCostum = PstCustomBillMain.fetchByCashierID(cashCashier.getOID());
    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
    }

    if (useForGB.equals("1")) {
      PrintTemplate printTemplate = new PrintTemplate();
      displayPrint += printTemplate.PrintTemplateGBClosing(userName, cashCashier,
              billMainCostum, location, cashMaster);
    } else {
      PrintTemplate printTemplate = new PrintTemplate();
      displayPrint += printTemplate.PrintTemplateClosing(userName, cashCashier,
              billMainCostum, location);
    }

    return displayPrint;
  }

  public String drawPrintClosing2(Vector listCashier, String userName, int type) {

    String useForGB = PstSystemProperty.getValueByName("USE_FOR_GREENBOWL");
    String displayPrint = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");

    CashCashier cashCashier;
    CashMaster cashMaster;
    Location location;

    if (listCashier.size() != 0) {
      cashCashier = (CashCashier) listCashier.get(0);
      try {
        cashCashier = PstCashCashier.fetchExc(cashCashier.getOID());
      } catch (Exception ex) {

      }
      cashMaster = (CashMaster) listCashier.get(1);
      try {
        cashMaster = PstCashMaster.fetchExc(cashMaster.getOID());
      } catch (Exception ex) {

      }
      location = (Location) listCashier.get(2);

    } else {
      cashCashier = new CashCashier();
      cashMaster = new CashMaster();
      location = new Location();
    }

    BillMainCostum billMainCostum;
    try {
      billMainCostum = PstCustomBillMain.fetchByCashierID(cashCashier.getOID());
    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
    }

    if (useForGB.equals("1")) {
      PrintTemplate printTemplate = new PrintTemplate();
      displayPrint += printTemplate.PrintTemplateGBClosing(userName, cashCashier,
              billMainCostum, location, cashMaster);
    } else {
      PrintTemplate printTemplate = new PrintTemplate();
      displayPrint += printTemplate.PrintTemplateClosing2(userName, cashCashier,
              billMainCostum, location, type);
    }

    return displayPrint;
  }

  public String drawPrintClosing3(Vector listCashier, String userName, int type) {

    String displayPrint = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");

    CashCashier cashCashier;
    Location location;

    if (listCashier.size() != 0) {
      cashCashier = (CashCashier) listCashier.get(0);
      try {
        cashCashier = PstCashCashier.fetchExc(cashCashier.getOID());
      } catch (Exception ex) {

      }
      location = (Location) listCashier.get(2);

    } else {
      cashCashier = new CashCashier();
      location = new Location();
    }

    BillMainCostum billMainCostum;
    try {
      billMainCostum = PstCustomBillMain.fetchByCashierID(cashCashier.getOID());
    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
    }

    PrintTemplate printTemplate = new PrintTemplate();
    displayPrint += printTemplate.PrintTemplateClosing3(userName, cashCashier,
            billMainCostum, location, type);

    return displayPrint;
  }

  public String drawListItem(int iCommand, Vector objectClass, int start, long cashCashierId) {
    //2
    ControlList ctrlist = new ControlList();

    ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
    ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
    ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
    ctrlist.setCellStyle("cellStyle");
    ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
    ctrlist.addHeader("No", "1%");
    ctrlist.addHeader("SKU", "5%");
    ctrlist.addHeader("Barcode", "5%");
    ctrlist.addHeader("Name", "20%");
    ctrlist.addHeader("Price", "5%");

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
    int counter = start + 1;
    String addClass = "";
    String addClassSpecial = "";
    String oidSpesialRequestFood = PstSystemProperty.getValueByName("SPESIAL_REQUEST_FOOD");
    String oidSpesialRequestBeverage = PstSystemProperty.getValueByName("SPESIAL_REQUEST_BEVERAGE");
    String useSpecialRequestSettingBaseTypeMaterial = PstSystemProperty.getValueByName("SPESIAL_REQUEST_SETTING_BASE_TYPE_MATERIAL");

    CashMaster cashMaster = new CashMaster();
    Location location = new Location();
    try {
      cashMaster = PstCashMaster.fetchExc(PstCashCashier.fetchExc(cashCashierId).getCashMasterId());
      location = PstLocation.fetchExc(cashMaster.getLocationId());
    } catch (Exception exc) {

    }

    for (int i = 0; i < objectClass.size(); i++) {
      //long famOid = 0;
      Vector listItem = (Vector) objectClass.get(i);

      if (listItem.size() != 0) {

        Material material = (Material) listItem.get(0);
        PriceTypeMapping priceTypeMapping = (PriceTypeMapping) listItem.get(1);
        String[] splits = material.getName().split(";");

        //cari nilai diskon
        int discType = 0;
        double discPct = 0;
        double discVal = 0;

        boolean reqSerial = false;
        boolean canBuy = false;
        if (cashMaster.getCashierStockMode() == PstCashMaster.STOCK_MODE_NO_STOCK_CHECKING) {
          canBuy = true;
        } else {
          double qtyStockStockCard = SessMatCostingStokFisik.qtyMaterialBasedOnStockCard(Long.valueOf(material.getMaterialId()), cashMaster.getLocationId(), new Date(), 0);
          double qtyStockRealTime = SessMatCostingStokFisik.qtyMaterialBasedOnOpeningCashier(Long.valueOf(material.getMaterialId()), new Date());
          double qtyStock = qtyStockStockCard - qtyStockRealTime;
          if (qtyStock > 0) {
            canBuy = true;
          }
        }
        double itemPrice = 0;
        double roundValue = 1;
        itemPrice = priceTypeMapping.getPrice();
        String useForRaditya = PstSystemProperty.getValueByName("USE_FOR_RADITYA");
        String formulaCash = PstSystemProperty.getValueByName("CASH_FORMULA");
        Category cat = new Category();
        try {
        cat = PstCategory.fetchExc(material.getCategoryId());
        } catch (Exception e) {
        }
        if (material.getRequiredSerialNumber() == 1) {
          reqSerial = true;
        }
         if(useForRaditya.equals("1")){
        if (checkString(formulaCash, "HPP") > -1) {
          formulaCash = formulaCash.replaceAll("HPP", "" + material.getAveragePrice()); 
        }
//        if (checkString(formulaCash, "INCREASE") > -1) {
//          formulaCash = formulaCash.replaceAll("INCREASE", "" + cat.getKenaikanHarga());
//        }
        double price = getValue(formulaCash);
        price = rounding(-3, price);
        itemPrice = price;
        }

        if (i == 0) {
          addClass = "firstFocus";
        } else {
          addClass = "";
        }

        if (oidSpesialRequestFood.equals("" + material.getMaterialId() + "") || oidSpesialRequestBeverage.equals("" + material.getMaterialId() + "")) {
          addClassSpecial = "specialOrder";
        }

        if (useSpecialRequestSettingBaseTypeMaterial.equals("1")) {
          if (material.getEditMaterial() == PstMaterial.EDIT_HARGA || material.getEditMaterial() == PstMaterial.EDIT_HARGA_NAME) {
            addClassSpecial = "specialOrder";
          }
        }

        rowx = new Vector(1, 1);
        rowx.add("<input type='hidden' class='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] + "' value='" + splits[0] + "'>"
                + "<input type='hidden' class='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] + "' value='" + material.getMaterialId() + "'>"
                + "<input type='hidden' class='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] + "' value='" + itemPrice + "'>"
                + "" + counter + ".");
        rowx.add("" + material.getSku() + "");
        rowx.add("" + material.getBarcode() + "");
        rowx.add("<input readonly='readonly' style='cursor:pointer;' class='textual form-control FRM_FIELD_ITEM_NAME " + addClassSpecial + " " + addClass + "' data-oid='" + material.getMaterialId() + "' data-name='" + splits[0] + "'  data-price='" + itemPrice + "' data-discType='" + discType + "' data-discPct='" + discPct + "' data-discVal='" + discVal + "' data-canbuy='" + canBuy + "' data-useSerial='" + reqSerial + "' type='text' value='" + splits[0] + "'>");
        rowx.add("<div class='text-right'>" + Formater.formatNumber(itemPrice, "#,###") + "</div>");
        lstData.add(rowx);
        counter++;
      }

    }
    return ctrlist.drawBootstrapStripted();
  }

  public String drawCustomerCreditTool() {
    String html = "";

    html = "<div class='input-group'>"
            + "<input type='text' id='searchCtCustomer' class='form-control' placeholder='Search...'/>"
            + "<div class='input-group-addon btn btn-primary' id='btnSearchCtCustomer'>"
            + "<i class='fa fa-search'></i>"
            + "</div>"
            + "<div class='input-group-addon btn btn-primary' id='btnAddCtCustomer'>"
            + "<i class='fa fa-plus'></i>"
            + "</div>"
            + "</div>";

    return html;
  }

  public String drawCustomerCreditTemporary(Vector objectClass) {
    ControlList ctrlist = new ControlList();

    ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
    ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
    ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
    ctrlist.setCellStyle("cellStyle");
    ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
    ctrlist.addHeader("Name", "20%");
    ctrlist.addHeader("Mobile Phone", "20%");
    ctrlist.addHeader("Company", "20%");

    ctrlist.setLinkRow(0);
    ctrlist.setLinkSufix("");
    Vector lstData = ctrlist.getData();
    //membuat link menuju ke edit
    ctrlist.reset();

    int index = -1;

    Vector rowx = new Vector(1, 1);
    Vector listItem = new Vector(1, 1);
    double grandTotal = 0;
    String addInner = "";
    int specialItem = 0;
    String fieldSpecial = "";
    int counter = 1;

    for (int i = 0; i < objectClass.size(); i++) {
      //long famOid = 0;
      ContactList contact = (ContactList) objectClass.get(i);

      rowx = new Vector(1, 1);

      rowx.add("<a class='selectCtCustomer' style='cursor:pointer' data-oId='" + contact.getOID() + "' data-personName='" + contact.getPersonName() + "' data-telp ='" + contact.getTelpMobile() + "' data-company='" + contact.getCompName() + "'>" + contact.getPersonName() + "</a>");
      rowx.add("<a class='selectCtCustomer' style='cursor:pointer' data-oId='" + contact.getOID() + "' data-personName='" + contact.getPersonName() + "' data-telp ='" + contact.getTelpMobile() + "' data-company='" + contact.getCompName() + "'>" + contact.getTelpMobile() + "</a>");
      rowx.add("<a class='selectCtCustomer' style='cursor:pointer' data-oId='" + contact.getOID() + "' data-personName='" + contact.getPersonName() + "' data-telp ='" + contact.getTelpMobile() + "' data-company='" + contact.getCompName() + "'>" + contact.getCompName() + "</a>");
      lstData.add(rowx);
      counter++;

    }
    return ctrlist.drawBootstrapStripted();
  }

  public String drawTransferItem(Vector objectClass, int command, long locationId, int status, long oidMatDisItem) {
    ControlList ctrlist = new ControlList();

    ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
    ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
    ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
    ctrlist.setCellStyle("cellStyle");
    ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
    ctrlist.addHeader("No", "20%");
    ctrlist.addHeader("Barcode", "20%");
    ctrlist.addHeader("Nama Barang", "20%");
    ctrlist.addHeader("Unit", "10%");
    ctrlist.addHeader("Qty", "10%");
    ctrlist.addHeader("Harga Jual", "20%");
    ctrlist.addHeader("Action", "30%");

    ctrlist.setLinkRow(0);
    ctrlist.setLinkSufix("");
    Vector lstData = ctrlist.getData();
    //membuat link menuju ke edit
    ctrlist.reset();

    int index = -1;

    Vector rowx = new Vector(1, 1);
    Vector listItem = new Vector(1, 1);
    double grandTotal = 0;
    double totalQty = 0;
    String addInner = "";
    int specialItem = 0;
    String fieldSpecial = "";
    int counter = 1;

    for (int i = 0; i < objectClass.size(); i++) {
      //long famOid = 0;
      Vector temp = (Vector) objectClass.get(i);
      MatDispatchItem dfItem = (MatDispatchItem) temp.get(0);
      com.dimata.posbo.entity.masterdata.Material mat = (com.dimata.posbo.entity.masterdata.Material) temp.get(1);
      Unit unit = (Unit) temp.get(2);

      rowx = new Vector(1, 1);
      String button = "";
      String whereClause = PstMaterialStock.fieldNames[PstMaterialStock.FLD_MATERIAL_UNIT_ID] + "=" + dfItem.getMaterialId();
      whereClause += " AND " + PstMaterialStock.fieldNames[PstMaterialStock.FLD_LOCATION_ID] + "=" + locationId;
      Vector vMaterialStock = PstMaterialStock.list(0, 0, whereClause, "");
      MaterialStock objMaterialStock = new MaterialStock();
      if (vMaterialStock.size() > 0) {
        objMaterialStock = (MaterialStock) vMaterialStock.get(0);
      }
      rowx.add("" + (counter));
      rowx.add("" + mat.getBarCode());
      rowx.add("" + mat.getName());
      rowx.add("" + unit.getCode());
      if (oidMatDisItem == dfItem.getOID()) {
        rowx.add("<input id='itemQtyEdit' type='text' value='" + dfItem.getQty() + "' class='form-control' size='5'>");
        button = "<button type=\"button\" class=\"btn btn-success saveItem btn-xs\" data-oid='" + dfItem.getOID() + "'><i class=\"fa fa-check\"></i></button>";
      } else {
        rowx.add("" + dfItem.getQty());
        button = "<button type=\"button\" class=\"btn btn-warning editItem btn-xs\" data-oid='" + dfItem.getOID() + "'><i class=\"fa fa-pencil\"></i></button>";
      }
      rowx.add("" + Formater.formatNumber(dfItem.getHpp(), ".2f"));
      totalQty = totalQty + dfItem.getQty();
      grandTotal = grandTotal + dfItem.getHpp();

      if (status == 5) {
        rowx.add("");
      } else {
        rowx.add(button + "<button type=\"button\" class=\"btn btn-danger deleteItem btn-xs\" data-oid='" + dfItem.getOID() + "'><i class=\"fa fa-trash\"></i></button>");
      }
      lstData.add(rowx);
      counter++;
    }
    rowx = new Vector();
    rowx.add("" + (counter));
    rowx.add("<input id='searchItemTransfer' type='text' name='matItem' value='' class='form-control' placeholder='Scan Barcode' autofocus>");
    rowx.add("<input id='itemName' type='text' value='' class='form-control' readonly='readonly'>");
    rowx.add("<input id='itemUnit' type='text' value='' class='form-control' readonly='readonly'>");
    rowx.add("<input id='itemQty' type='text' value='' class='form-control' readonly='readonly'>");
    rowx.add("<input id='itemHargaJual' type='text' value='' class='form-control' readonly='readonly'>");
    rowx.add("");
    lstData.add(rowx);

    rowx = new Vector();
    rowx.add("");
    rowx.add("");
    rowx.add("");
    rowx.add("<strong>Total</strong>");
    rowx.add("" + totalQty);
    rowx.add("");
    rowx.add("");
    lstData.add(rowx);

    return ctrlist.drawBootstrapStripted();
  }

  public String drawTransferList(Vector objectClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
    ControlList ctrlist = new ControlList();

    ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
    ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
    ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
    ctrlist.setCellStyle("cellStyle");
    ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
    ctrlist.addHeader("No", "20%");
    ctrlist.addHeader("Nomor", "20%");
    ctrlist.addHeader("Tanggal", "20%");
    ctrlist.addHeader("Lokasi Asal", "20%");
    ctrlist.addHeader("Lokasi Tujuan", "20%");
    ctrlist.addHeader("Status", "20%");
    ctrlist.addHeader("Action", "20%");

    ctrlist.setLinkRow(0);
    ctrlist.setLinkSufix("");
    Vector lstData = ctrlist.getData();
    //membuat link menuju ke edit
    ctrlist.reset();

    int index = -1;
    Vector rowx = new Vector(1, 1);
    int counter = 1;

    for (int i = 0; i < objectClass.size(); i++) {
      MatDispatch df = (MatDispatch) objectClass.get(i);

      Location loc1 = new Location();
      Location loc2 = new Location();

      try {
        loc1 = PstLocation.fetchExc(df.getLocationId());
      } catch (Exception e) {
      }

      try {
        loc2 = PstLocation.fetchExc(df.getDispatchTo());
      } catch (Exception e) {
      }

      rowx = new Vector(1, 1);

      rowx.add("" + (counter));
      rowx.add("" + df.getDispatchCode());
      rowx.add("" + Formater.formatDate(df.getDispatchDate(), "dd-MM-yyyy"));
      rowx.add("" + loc1.getName());
      rowx.add("" + loc2.getName());
      rowx.add("" + PstMatDispatch.fieldTypeStatus[df.getDispatchStatus()]);
      rowx.add("<button type=\"button\" class=\"btn btn-warning viewDf\" data-oid='" + df.getOID() + "' data-location='" + df.getLocationId() + "' data-status='" + df.getDispatchStatus() + "'><i class=\"fa fa-search\"></i>View</button>");
      lstData.add(rowx);
      counter++;
    }

    return ctrlist.drawBootstrapStripted();
  }

  public Vector parentMenu(long oid, Vector data, long location, String priceType, String design, int type) {
    if (oid != 0) {
      //CEK is sub category
      int count = PstCategory.getCount(PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID] + "='" + oid + "'");
      String whereClause = PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + "='" + oid + "'";

      Vector listCategory = PstCategory.list(0, 0, whereClause, "");
      if (listCategory.size() > 0) {
        for (int i = 0; i < listCategory.size(); i++) {
          Category category = (Category) listCategory.get(i);
          String html = "<button data-menu='1' data-type='" + category.getCategoryId() + "' data-oidcategory='0' data-design='" + design + "' data-pricetype='" + priceType + "' data-location='" + location + "' data-parent='" + category.getOID() + "' data-for='loadMenuDinamis' type='button' class='btn btn-default mainCategoryOutlet '><i class='fa fa-gg-circle'></i> " + category.getName() + "</button>";
          data.add(html);
          data = parentMenu(category.getCatParentId(), data, location, priceType, design, type);
        }
      }
    }

    return data;
  }

  public void updateDisc(double disc, HttpServletRequest request) {
    long oidBillMain = FRMQueryString.requestLong(request, "FRM_FIELD_CASH_BILL_MAIN_ID");
    if (oidBillMain != 0) {
      try {
        BillMain billMain = PstBillMain.fetchExc(oidBillMain);
        billMain.setDiscPct(disc);
        long oidUpdate = PstBillMain.updateExc(billMain);
      } catch (Exception ex) {

      }
    }
  }

  public String getShortMessage(HttpServletRequest request, Vector listCashier) {
    String returnData = "CLS-";
    CashCashier cashCashier = new CashCashier();
    double cl = 0;
    if (listCashier.size() > 0) {
      Vector getData = (Vector) listCashier.get(0);
      cashCashier = (CashCashier) getData.get(0);
    }

    if (cashCashier.getOID() != 0) {
      try {
        CashCashier cashCashier1 = PstCashCashier.fetchExc(cashCashier.getOID());
        CashMaster cashMaster = PstCashMaster.fetchExc(cashCashier1.getCashMasterId());
        Location location = PstLocation.fetchExc(cashMaster.getLocationId());
        returnData += location.getCode() + " " + Formater.formatDate(new Date(), "yyyyMMdd") + ":";
      } catch (Exception ex) {
        returnData += "";
      }
    }
    try {
      double creditReturnAmount = PstCustomBillMain.getSummarySalesCreditReturn(cashCashier.getOID());
      double creditAmount = PstCustomBillMain.getSummarySalesCredit(cashCashier.getOID());
      cl = creditAmount - creditReturnAmount;
    } catch (Exception ex) {
    }
    double cashReturnAmount = PstCustomBillMain.getSummaryCashReturn(cashCashier.getOID());
    double totalReturnAmount = cashReturnAmount;//+creditReturnAmount;

    Vector listBalance = PstBalance.list(0, 0,
            "" + PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID] + "='" + cashCashier.getOID() + "' "
            + "AND " + PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE] + "='1' "
            + "AND " + PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID] + "='1'"
            + "", "");
    Vector listPaymentSystem = PstPaymentSystem.listAll();
    for (int j = 0; j < listBalance.size(); j++) {
      //PAYMENT DETAIL DATA
      double cashPayment = 0;
      double ccPayment = 0;
      double debitPayment = 0;
      double bgPayment = 0;
      double checkPayment = 0;
      Balance entBalance = new Balance();
      entBalance = (Balance) listBalance.get(j);
      for (int i = 0; i < listPaymentSystem.size(); i++) {
        PaymentSystem paymentSystem = (PaymentSystem) listPaymentSystem.get(i);
        double payment = PstCashPayment.getSumListDetailBayar2("CBM." + PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID] + "='" + cashCashier.getOID() + "' "
                + "AND CP." + PstCashPayment.fieldNames[PstCashPayment.FLD_PAY_TYPE] + "='" + paymentSystem.getOID() + "' "
                + "AND CP." + PstCashPayment.fieldNames[PstCashPayment.FLD_CURRENCY_ID] + "='1'"
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1' ");
        if (paymentSystem.isBankInfoOut() == false
                && paymentSystem.isCardInfo() == false
                && paymentSystem.isCheckBGInfo() == false
                && paymentSystem.getPaymentType() == 1) {
          double pay = 0;
          double totpay = 0;
          pay = payment;
          double change = PstCashReturn.getReturnSummary2(cashCashier.getOID(), entBalance.getCurrencyOid());
          totpay = pay - change - totalReturnAmount;
          cashPayment += totpay;
          //returnData+=" \n CASH:"+Formater.formatNumber(pay,"#,###");
        } else if (paymentSystem.isBankInfoOut() == false
                && paymentSystem.isCardInfo() == true
                && paymentSystem.isCheckBGInfo() == false
                && paymentSystem.getPaymentType() == 0) {
          double pay = 0;
          pay = payment;
          ccPayment += payment;
          //returnData+=" \n CC:"+Formater.formatNumber(pay,"#,###");
        } else if (paymentSystem.isBankInfoOut() == true
                && paymentSystem.isCardInfo() == false
                && paymentSystem.isCheckBGInfo() == false
                && paymentSystem.getPaymentType() == 2) {
          double pay = 0;
          pay = payment;
          bgPayment += payment;
          //returnData+=" \n BG:"+Formater.formatNumber(pay,"#,###");
        } else if (paymentSystem.isBankInfoOut() == false
                && paymentSystem.isCardInfo() == false
                && paymentSystem.isCheckBGInfo() == true
                && paymentSystem.getPaymentType() == 0) {
          checkPayment += payment;
          double pay = 0;
          pay = payment;
          //returnData+=" \n CHEQUE:"+Formater.formatNumber(pay,"#,###");
        } else if (paymentSystem.isBankInfoOut() == false
                && paymentSystem.isCardInfo() == false
                && paymentSystem.isCheckBGInfo() == false
                && paymentSystem.getPaymentType() == 2) {
          debitPayment += payment;
          double pay = 0;
          pay = payment;
          //returnData+=" \n DEBITCARD:"+Formater.formatNumber(pay,"#,###");
        }
      }

      returnData += "|CASH:" + Formater.formatNumber(cashPayment, "#,###");
      returnData += "|CC:" + Formater.formatNumber(ccPayment, "#,###");
      returnData += "|BG:" + Formater.formatNumber(bgPayment, "#,###");
      returnData += "|CEQ:" + Formater.formatNumber(checkPayment, "#,###");
      returnData += "|DC:" + Formater.formatNumber(debitPayment, "#,###");
      double subTotal = cashPayment + ccPayment + debitPayment + bgPayment + checkPayment;
      returnData += "|TOT. PAY:" + Formater.formatNumber(subTotal, "#,###");
      returnData += "|CL : " + Formater.formatNumber(cl, "#,###");

    }

    return returnData;
  }

  public void updateDiscDetail(long billId, double discPct, double discNomintal) {
    BillMain billMain = new BillMain();
    Location location = new Location();
    if (billId != 0) {
      try {
        billMain = PstBillMain.fetchExc(billId);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    if (billMain.getLocationId() != 0) {
      try {
        location = PstLocation.fetchExc(billMain.getLocationId());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    Vector listBillDetail = PstBillDetail.list(0, 0, "" + PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + billId + "'", "");
    double totalQty = PstBillDetail.getQty(billId);

    if (discPct == 0) {
      discNomintal = discNomintal / totalQty;
    }
    if (listBillDetail.size() > 0) {
      for (int i = 0; i < listBillDetail.size(); i++) {
        Billdetail billdetail = (Billdetail) listBillDetail.get(i);
        double totalPrice = billdetail.getTotalPrice();
        double totalTax = 0;
        double totalService = 0;
        if (discPct > 0) {
          discNomintal = billdetail.getItemPrice() * (discPct / 100);
        }

        if (location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE) {
          double dpp = (billdetail.getItemPrice() - billdetail.getDisc() - discNomintal) / ((100 + location.getTaxPersen() + location.getServicePersen()) / 100);
          totalPrice = (dpp + discNomintal) * billdetail.getQty();
          totalTax = ((billMain.getTaxPercentage() / 100) * dpp) * billdetail.getQty();
          totalService = ((billMain.getServicePct() / 100) * dpp) * billdetail.getQty();
        } else {
          double dpp = (billdetail.getItemPrice() - billdetail.getDisc());//-discNomintal;
          totalPrice = dpp * billdetail.getQty();
          totalTax = ((billMain.getTaxPercentage() / 100) * dpp) * billdetail.getQty();
          totalService = ((billMain.getServicePct() / 100) * dpp) * billdetail.getQty();
        }

        billdetail.setTotalPrice(totalPrice);
        billdetail.setTotalTax(totalTax);
        billdetail.setTotalSvc(totalService);
        try {
          long oid = PstBillDetail.updateExc(billdetail);
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    }
  }

  public void autoCheckOut(HttpServletRequest request, long billMainId) {
    long reservationId = FRMQueryString.requestLong(request, "FRM_FIELD_RESERVATION_ID");
    if (reservationId != 0) {
      //CHECK IS PACKAGE
      int isPackage = PstCustomePackBilling.getCount(PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_RESERVATION_ID] + "='" + reservationId + "'");
      int noSchedule = PstCustomePackageSchedule.getCountJoin("billing." + PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_RESERVATION_ID] + "='" + reservationId + "' "
              + "AND (schedule." + PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_STATUS] + "='0')");
      if (noSchedule == 0 || noSchedule == 1) {
        noSchedule = PstCustomePackageSchedule.getCountJoin("billing." + PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_RESERVATION_ID] + "='" + reservationId + "' "
                + "AND schedule." + PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_STATUS] + "='3'");
        if (noSchedule == 1) {
          noSchedule = 0;
        }
      }

      //CHECKOUT SCHEDULE
      Vector listBillDetail = PstBillDetail.list(0, 0, PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + billMainId + "'", "");
      if (listBillDetail.size() > 0) {
        for (int i = 0; i < listBillDetail.size(); i++) {
          Billdetail billdetail = (Billdetail) listBillDetail.get(i);
          if (billdetail.getCostumeScheduleId() != 0) {
            try {
              CustomePackageSchedule customePackageSchedule = PstCustomePackageSchedule.fetchExc(billdetail.getCostumeScheduleId());
              customePackageSchedule.setStatus(4);
              long oid = PstCustomePackageSchedule.updateExc(customePackageSchedule);
            } catch (Exception ex) {

            }
          }
        }
      }

      if (isPackage > 0 && noSchedule == 0) {
        //FETCH RESERVATION
        Reservation reservation = new Reservation();
        try {
          reservation = PstReservation.fetchExc(reservationId);
        } catch (Exception ex) {

        };

        if (reservation.getHotelRoomId() == 0) {
          //CHECK OUT RESERVATION
          reservation.setReservationStatus(4);
          try {
            long oid = PstReservation.updateExc(reservation);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }
    }
  }

  /*public synchronized static String generateInvoiceNumber(String cashierNumber){
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
    }*/
  public String templateHandler(BillMain billMain) {
    String getActiveTemplate = PstSystemProperty.getValueByName("PRINT_ACTIVE_TEMPLATE");
    String printPaymentType = "Cash";
    BillMainCostum headerInfo = new BillMainCostum();
    AppUser ap = new AppUser();

    int activeTemplate = 0;
    try {
      activeTemplate = Integer.parseInt(getActiveTemplate);
      ap = PstAppUser.fetch(billMain.getAppUserSalesId());
    } catch (Exception ex) {
      activeTemplate = 0;
    }

    if (billMain.getTransactionStatus() == 0 && billMain.getTransctionType() == 0 && billMain.getDocType() == 0) {
      printPaymentType = "Cash";
    } else if (billMain.getTransactionStatus() == 1 && billMain.getTransctionType() == 1 && billMain.getDocType() == 0) {
      printPaymentType = "Credit";
    }

    headerInfo = PstCustomBillMain.fetchByCashierID(billMain.getCashCashierId());
    headerInfo.setInvoiceNo(billMain.getInvoiceNo());

    Vector billDetail = PstBillDetail.listJoinUnit(0, 0, PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'", "");
    double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
    Location location = new Location();
    if (billMain.getLocationId() != 0) {
      try {
        location = PstLocation.fetchExc(billMain.getLocationId());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    if (location.getTaxSvcDefault() == PstLocation.TSD_INCLUDE_CHANGABLE || location.getTaxSvcDefault() == PstLocation.TSD_INCLUDE_NOTCHANGABLE) {
      billMain.setTaxValue(0);
    } else {
      double tax = 0;
      if (billMain.getTaxPercentage() == 0) {
        tax = totalPrice * (location.getTaxPersen() / 100);
      } else {
        tax = totalPrice * (billMain.getTaxPercentage() / 100);
      }
      double svc = 0;
      if (billMain.getServicePct() == 0) {
        svc = totalPrice * (location.getServicePersen() / 100);
      } else {
        svc = totalPrice * (billMain.getServicePct() / 100);
      }
      billMain.setTaxValue(svc + tax);
    }

    //CUSTOMER
    ContactList costumer = new ContactList();
    int dayOfDueDate = 0;
    try {
      costumer = PstContactList.fetchExc(billMain.getCustomerId());
      dayOfDueDate = costumer.getDayOfPayment();
    } catch (Exception ex) {
      costumer = new ContactList();
    }

    double totalQty = 0;
    try {
      totalQty = PstBillDetail.getQty(billMain.getOID());
    } catch (Exception ex) {
      totalQty = 0;
    }

    //PAYMENT
    CashPayments payment = new CashPayments();
    try {
      Vector listPayment = PstCashPayment.listDetailBayar(0, 0, "CBM." + PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID] + "='" + billMain.getOID() + "'");
      Vector data = (Vector) listPayment.get(0);
      payment = (CashPayments) data.get(0);
    } catch (Exception ex) {

    }

    //DUMMY DATA START
    PrintDynamicTemplate printDynamicTemplate = new PrintDynamicTemplate();
    EntPrintData entPrintData = new EntPrintData();
    entPrintData.setCompLogo("../imgcompany/dimata_system_logo.png");
    entPrintData.setCompAddress(headerInfo.getLocationAddress());
    entPrintData.setCompPhone(headerInfo.getLocationTelp());
    entPrintData.setCompFax(headerInfo.getLocationFax());
    entPrintData.setCompEmail(headerInfo.getLocationEmail());
    entPrintData.setCompName(headerInfo.getCompanyName());
    entPrintData.setActiveTemplate(activeTemplate);
    Vector dataList = new Vector();
    if (billDetail != null) {
      for (int i = 0; i < billDetail.size(); i++) {
        EntDataList entDataList = new EntDataList();
        Billdetail entBilldetail = (Billdetail) billDetail.get(i);
        double disc = 0;
        try {
          disc = entBilldetail.getDisc() + (billMain.getDiscount() / totalQty);
        } catch (Exception ex) {
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
        entDataList.setItemId("" + entBilldetail.getSku());
        entDataList.setItemQtyType(entBilldetail.getUnitName());
        dataList.add(entDataList);
      }
    }
    for (int i = 0; i < 5; i++) {

    }
    entPrintData.setDataList(dataList);
    entPrintData.setInvNo(headerInfo.getInvoiceNo());
    entPrintData.setPaymentType(printPaymentType);
    entPrintData.setDataTaxTotal(billMain.getTaxValue());
    entPrintData.setIsTaxInclude(location.getTaxSvcDefault());
    entPrintData.setReceivedBy(ap.getFullName());
    entPrintData.setPaid(payment.getAmount());
    entPrintData.setCostumer(costumer.getPersonName());
    entPrintData.setDataDiscTotal(billMain.getDiscount());
    entPrintData.setDiscPct(billMain.getDiscPct());
    entPrintData.setDataAdm(billMain.getAdminFee());
    entPrintData.setDataTransport(billMain.getShippingFee());

    //cek
    try {
      Date dayOfDueDatex = new Date();
      Date startDateN = getWeekStart(dayOfDueDatex, dayOfDueDate);
      entPrintData.setDueDate(startDateN);
    } catch (Exception ex) {
    }

    //END OF DUMMY DATA
    return printDynamicTemplate.showData(entPrintData);
  }

  private double getRealPrice(Location location, double price, double discount) {
    double result = 0;
    price = price - discount;
    if (location.getTaxSvcDefault() == PstLocation.TSD_INCLUDE_CHANGABLE || location.getTaxSvcDefault() == PstLocation.TSD_INCLUDE_NOTCHANGABLE) {
      double dpp = 100 / (100 + location.getTaxPersen() + location.getServicePersen());
      result = price * dpp;
    } else {
      result = price;
    }
    return result;
  }

  public static Date getWeekStart(Date date, int weekStart) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DATE, weekStart);
    //startOfDay(calendar);
    return calendar.getTime();
  }

  public synchronized String generateLogHistory(long oidBill) {
    String message = "";
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

  public double getItemPriceFromPriceTypeMapping(long materialId, long priceTypeId, long standardRateId) {
    //CARI PRICE TYPE MAPPING
    String wherePrice = PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_MATERIAL_ID] + " = '" + materialId + "'"
            + " AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_PRICE_TYPE_ID] + " = '" + priceTypeId + "'"
            + " AND " + PstPriceTypeMapping.fieldNames[PstPriceTypeMapping.FLD_STANDART_RATE_ID] + " = '" + standardRateId + "'"
            + "";
    Vector<PriceTypeMapping> listPrice = PstPriceTypeMapping.list(0, 0, wherePrice, "");
    if (listPrice.size() > 0) {
      return listPrice.get(0).getPrice();
    }
    return 0;
  }

  public HashMap getItemDiscountRegulerOrDiscountQty(long materialId, long locationId, long currencyId, long discountTypeId, double qty, double itemPrice) {
    double discountPct = 0;
    double discountVal = 0;
    //CARI DISKON MAPPING
    String whereDiscQtyMap = ""
            + "" + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_MATERIAL_ID] + " = '" + materialId + "'"
            + " AND " + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_LOCATION_ID] + " = '" + locationId + "'"
            + " AND " + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_CURRENCY_TYPE_ID] + " = '" + currencyId + "'"
            + " AND " + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_DISCOUNT_TYPE_ID] + " = '" + discountTypeId + "'"
            + " AND (" + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_START_QTY] + " <= " + qty + ""
            + " AND " + PstDiscountQtyMapping.fieldNames[PstDiscountQtyMapping.FLD_TO_QTY] + " >= " + qty + ")";

    Vector listDiscQty = PstDiscountQtyMapping.list(0, 0, whereDiscQtyMap, "");
    if (listDiscQty.size() > 0) {

      DiscountQtyMapping dqm = (DiscountQtyMapping) listDiscQty.get(0);

      switch (dqm.getDiscountType()) {
        case PstDiscountQtyMapping.DISC_PERSEN_PER_ITEM_UNIT:
          discountPct = dqm.getDiscountValue();
          discountVal = itemPrice * dqm.getDiscountValue() / 100;
          break;
        case PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_UNIT:
          discountVal = dqm.getDiscountValue();
          break;
        case PstDiscountQtyMapping.DISC_NOMINAL_PER_ITEM_SKU:
          discountVal = dqm.getDiscountValue();
          break;
      }

    } else {

      //jika tidak ada diskon qty maka gunakan diskon reguler
      String whereDiscMap = ""
              + "" + PstDiscountMapping.fieldNames[PstDiscountMapping.FLD_DISCOUNT_TYPE_ID] + " = '" + discountTypeId + "'"
              + " AND " + PstDiscountMapping.fieldNames[PstDiscountMapping.FLD_CURRENCY_TYPE_ID] + " = '" + currencyId + "'"
              + " AND " + PstDiscountMapping.fieldNames[PstDiscountMapping.FLD_MATERIAL_ID] + " = '" + materialId + "'"
              + "";

      Vector<DiscountMapping> listDiscTypeMap = PstDiscountMapping.list(0, 0, whereDiscMap, "");
      if (listDiscTypeMap.size() > 0) {
        discountPct = listDiscTypeMap.get(0).getDiscountPct();
        discountVal = itemPrice * discountPct / 100;
      }
    }

    HashMap<String, Object> returnMap = new HashMap();
    returnMap.put("DISC_PCT", discountPct);
    returnMap.put("DISC_VAL", discountVal);

    return returnMap;
  }

  public String drawPrintClosingSummary(Vector listCashier, String userName, int type) {

    String displayPrint = "";
    String datePrint = Formater.formatDate(new Date(), "yyyy-MM-dd");
    String timePrint = Formater.formatDate(new Date(), "kk:mm:ss");

    CashCashier cashCashier;
    Location location;

    if (listCashier.size() != 0) {
      cashCashier = (CashCashier) listCashier.get(0);
      try {
        cashCashier = PstCashCashier.fetchExc(cashCashier.getOID());
      } catch (Exception ex) {

      }
      location = (Location) listCashier.get(2);

    } else {
      cashCashier = new CashCashier();
      location = new Location();
    }

    BillMainCostum billMainCostum;
    try {
      billMainCostum = PstCustomBillMain.fetchByCashierID(cashCashier.getOID());
    } catch (Exception ex) {
      billMainCostum = new BillMainCostum();
    }

    PrintTemplate printTemplate = new PrintTemplate();
    displayPrint += printTemplate.PrintTemplateClosing2(userName, cashCashier,
            billMainCostum, location, type);

    return displayPrint;
  }

  public String getItemListGridViewWithImage(HttpServletRequest request) {
    long oidBillMain = FRMQueryString.requestLong(request, "idBillMain");
    long oidCategory = FRMQueryString.requestLong(request, "idCategory");
    String approot = FRMQueryString.requestString(request, "approot");
    String itemsearch = FRMQueryString.requestString(request, "search");
    long cashCashierId = FRMQueryString.requestLong(request, "CASH_CASHIER_ID");

    int jmlShowData = FRMQueryString.requestInt(request, "dataShow");
    int showPage = FRMQueryString.requestInt(request, "showPage");

    if (jmlShowData == 0) {
      jmlShowData = 8;
    }
    if (showPage == 0) {
      showPage = 1;
    }

    int start = (showPage - 1) * jmlShowData;

    long oidLocation = 0;
    long oidPriceType = 0;
    long oidStandardRate = 0;
    String categoryName = "";

    if (PstBillMain.checkOID(oidBillMain)) {
      try {
        BillMain billMain = PstBillMain.fetchExc(oidBillMain);
        Location location = PstLocation.fetchExc(billMain.getLocationId());
        oidLocation = location.getOID();
        oidStandardRate = location.getStandardRateId();
        if (PstMemberReg.checkOID(billMain.getCustomerId())) {
          MemberReg memberReg = PstMemberReg.fetchExc(billMain.getCustomerId());
          MemberGroup memberGroup = PstMemberGroup.fetchExc(memberReg.getMemberGroupId());
          oidPriceType = memberGroup.getPriceTypeId();
        } else {
          oidPriceType = location.getPriceTypeId();
        }
        if (oidCategory > 0) {
          categoryName = PstCategory.fetchExc(oidCategory).getName();
        }
      } catch (Exception e) {
      }
    }

    String whereClause = " p." + PstMaterial.fieldNames[PstMaterial.FLD_EDIT_MATERIAL] + " != 4 ";
    if (itemsearch.length() > 0) {
      whereClause += " AND ("
              + " p." + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE] + " LIKE '%" + itemsearch + "%'"
              + " OR p." + PstMaterial.fieldNames[PstMaterial.FLD_SKU] + " LIKE '%" + itemsearch + "%'"
              + " OR p." + PstMaterial.fieldNames[PstMaterial.FLD_NAME] + " LIKE '%" + itemsearch + "%'"
              + ")";
    }
    if (oidCategory > 0) {
      whereClause += " AND p." + PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID] + " = " + oidCategory;
    }

    Vector listItem = PstMaterial.listShopingCartUseLocation(start, jmlShowData, whereClause, "", "" + oidStandardRate, "" + oidPriceType, 1, oidLocation);
    String html = "";
    if (listItem.isEmpty()) {
      html = "<div class='text-center'>Item " + (itemsearch.isEmpty() ? "" : "\"" + itemsearch + "\" ") + "not found " + (categoryName.isEmpty() ? "" : " in category \"" + categoryName + "\"") + "</div>";
    }

    int countPanel = 1;
    String htmlPanel = "";
    for (int i = 0; i < listItem.size(); i++) {
      Vector v = (Vector) listItem.get(i);
      Material material = (Material) v.get(0);
      PriceTypeMapping priceTypeMapping = (PriceTypeMapping) v.get(1);

      boolean canBuy = false;
      CashMaster cashMaster = new CashMaster();
      try {
        cashMaster = PstCashMaster.fetchExc(PstCashCashier.fetchExc(cashCashierId).getCashMasterId());
      } catch (Exception exc) {

      }
      if (cashMaster.getCashierStockMode() == PstCashMaster.STOCK_MODE_NO_STOCK_CHECKING) {
        canBuy = true;
      } else {
        double qtyStockStockCard = SessMatCostingStokFisik.qtyMaterialBasedOnStockCard(Long.valueOf(material.getMaterialId()), cashMaster.getLocationId(), new Date(), 0);
        double qtyStockRealTime = SessMatCostingStokFisik.qtyMaterialBasedOnOpeningCashier(Long.valueOf(material.getMaterialId()), new Date());
        double qtyStock = qtyStockStockCard - qtyStockRealTime;
        if (qtyStock > 0) {
          canBuy = true;
        }
      }

      //String path = approot + "/imgcache/" + material.getMaterialImage();
      String prochainUrl = PstSystemProperty.getValueByName("PROCHAIN_URL");
      String imgCache = PstSystemProperty.getValueByName("IMGCACHE");
      String imageLocation = PstSystemProperty.getValueByName("PATH_IMAGE");
      String path = imageLocation + "/" + material.getMaterialImage();
      //String path = prochainUrl + "/" + imgCache + "/" + material.getSku() + ".jpg";

      if (countPanel == 1) {
        htmlPanel += "<div class='row'>";
        htmlPanel += "<div class='col-sm-12'>";
      }

      String no = "<span class='label bg-yellow'>" + (start + i + 1) + "</span>&nbsp;";

      htmlPanel += ""
              + " <div class='col-sm-3' style='padding-right: 0px; padding-top: 15px;'>"
              + "     <div class='panel panel-success panel_item' data-oid='" + material.getMaterialId() + "' data-name='" + material.getName().toUpperCase() + "' data-price='" + priceTypeMapping.getPrice() + "' data-canbuy='" + canBuy + "' style='cursor: pointer; margin-bottom: 0px'>"
              + "         <div class='panel-heading'>" + no + material.getName().toUpperCase() + "</div>"
              + "         <div class='panel-body' style='width: fit-content'>"
              + "             <img style='max-height: 100px; width: 100%; object-fit: contain' alt='No image' src='" + path + "'>"
              + "         </div>"
              + "         <div class='panel-footer text-bold'>Rp. " + Formater.formatNumber(priceTypeMapping.getPrice(), "#,###") + "</div>"
              + "     </div>"
              + " </div>"
              + "";

      if (countPanel == 4 || i == listItem.size() - 1) {
        htmlPanel += "</div>";
        htmlPanel += "</div>";
        html += htmlPanel;
        htmlPanel = "";
        countPanel = 1;
      } else {
        countPanel++;
      }

    }

    //PAGINATION
    int jmlData = PstMaterial.countShopingCartUseLocation(whereClause, "", "" + oidStandardRate, "" + oidPriceType, 1, oidLocation);
    int jmlDataLimit = listItem.size();
    if (jmlData > 0) {
      double tamp = Math.floor(jmlData / jmlShowData);
      double tamp2 = jmlData % jmlShowData;
      if (tamp2 > 0) {
        tamp2 = 1;
      }
      double JumlahHalaman = tamp2 + tamp;

      String attDisFirst = "";
      String attDisNext = "";

      if (showPage == 1) {
        attDisFirst = "disabled";
      }

      if (showPage == JumlahHalaman) {
        attDisNext = "disabled";
      }

      int dataStart = start + 1;
      //int dataEnd = (jmlDataLimit < jmlShowData) ? (showPage * jmlShowData) - (jmlShowData - jmlDataLimit) : showPage * jmlShowData;
      int dataEnd = start + jmlDataLimit;

      html += ""
              + "<div class='row'>"
              + " <div class='col-sm-12' style='padding-right: 0px; padding-top: 15px; padding-bottom: 15px'>"
              + "     <input type='hidden' id='totalPage' value='" + String.format("%.0f", JumlahHalaman) + "'>"
              + "     <div class='col-sm-6'>"
              + "         <span>"
              + "             Page " + Formater.formatNumber(showPage, "") + " of " + Formater.formatNumber(JumlahHalaman, "")
              + "             &nbsp;|&nbsp; Showing " + Formater.formatNumber(dataStart, "") + " to " + Formater.formatNumber(dataEnd, "") + " from " + Formater.formatNumber(jmlData, "") + " data"
              + "         </span>"
              + "     </div>"
              + "     <div class='col-sm-6'>"
              + "         <div data-original-title='Status' class='box-tools pull-right'>"
              + "             <div class='btn-group' data-toggle='btn-toggle'>"
              + "                 <button " + attDisFirst + " data-type='first' data-page='" + showPage + "' type='button' class='btn btn-default btn_pagination'>"
              + "                     <i class='fa fa-angle-double-left'></i> First"
              + "                 </button>"
              + "                 <button " + attDisFirst + " data-type='prev' data-page='" + showPage + "' type='button' class='btn btn-default btn_pagination'>"
              + "                     <i class='fa fa-angle-left'></i> Prev"
              + "                 </button>"
              + "                 <button " + attDisNext + " data-type='next' data-page='" + showPage + "' type='button' class='btn btn-default btn_pagination'>"
              + "                     Next <i class='fa fa-angle-right'></i>"
              + "                 </button>"
              + "                 <button " + attDisNext + " data-type='last' data-page='" + showPage + "' type='button' class='btn btn-default btn_pagination'>"
              + "                     Last <i class='fa fa-angle-double-right'></i>"
              + "                 </button>"
              + "             </div>"
              + "         </div>"
              + "     </div>"
              + " </div>"
              + "</div>";
    }

    return html;
  }

  public String getListBillItemGrid(HttpServletRequest request) {
    long oidBillMain = FRMQueryString.requestLong(request, "idBillMain");
    String where = "billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + " = '" + oidBillMain + "'";
    Vector<Billdetail> listBillDetail = PstCustomBillMain.listItemOpenBill(0, 0, where, "");

    String html = "";
    double grandTotal = 0;
    for (Billdetail bd : listBillDetail) {
      grandTotal += bd.getTotalPrice();
      html += ""
              + "<tr>"
              + "     <td>" + bd.getItemName() + "</td>"
              + "     <td class='text-right'>" + Formater.formatNumber(bd.getItemPrice(), "#,###") + "</td>"
              + "     <td class='text-right'>" + Formater.formatNumber(bd.getDisc(), "#,###") + "</td>"
              + "     <td class='text-right'>" + bd.getQty() + "</td>"
              + "     <td class='text-right'>" + Formater.formatNumber(bd.getTotalPrice(), "#,###") + "</td>"
              + "</tr>";
    }

    if (listBillDetail.isEmpty()) {
      html = "<tr><td colspan='5' class='text-center label-default'>No item</td></tr>";
    } else {
      html += ""
              + "<tr>"
              + "     <th colspan='4' class='text-right'>GRAND TOTAL :</th>"
              + "     <th class='text-right'>" + Formater.formatNumber(grandTotal, "#,###") + "</th>"
              + "</tr>";
    }
    return html;
  }

  public String generateSerial(HttpServletRequest request) {
    String html = "";

    String initateSerial = FRMQueryString.requestString(request, "currentSerial");
    long oidLocation = FRMQueryString.requestLong(request, "oidLocation");
    int qty = FRMQueryString.requestInt(request, "qty");
    if (qty == 0) {
      html += "<div class=\"input-group\"><input type=\"text\" name=\"SERIAL_CODE\" class=\"form-control serialFrom\" placeholder=\"Serial Code\" id=\"serial\" value='" + initateSerial + "' required=\"required\">"
              + "<div class=\"input-group-addon btn btn-primary\" id=\"btnGenerateSerial\" title=\"Generate Sequential Serial\"><i class=\"fa fa-refresh\"></i></div>"
              + "<div class=\"input-group-addon btn btn-danger btnDeleteAllSerial\" title=\"Clear All Field\"><i class=\"fa fa-undo\"></i></div></div>";
    }
    for (int i = 0; i < qty; i++) {
      String whereClauseSerial = PstMaterialStockCode.fieldNames[PstMaterialStockCode.FLD_STOCK_CODE] + " > '" + initateSerial + "'"
              + " AND " + PstMaterialStockCode.fieldNames[PstMaterialStockCode.FLD_STOCK_STATUS] + "=" + PstMaterialStockCode.FLD_STOCK_STATUS_GOOD
              + " AND " + PstMaterialStockCode.fieldNames[PstMaterialStockCode.FLD_LOCATION_ID] + "=" + oidLocation;
      if (i == 0) {
        String whereClause = PstMaterialStockCode.fieldNames[PstMaterialStockCode.FLD_STOCK_CODE] + " = '" + initateSerial + "'"
                + " AND " + PstMaterialStockCode.fieldNames[PstMaterialStockCode.FLD_STOCK_STATUS] + "=" + PstMaterialStockCode.FLD_STOCK_STATUS_GOOD
                + " AND " + PstMaterialStockCode.fieldNames[PstMaterialStockCode.FLD_LOCATION_ID] + "=" + oidLocation;;
        Vector listSerial = PstMaterialStockCode.list(0, 1, whereClause, PstMaterialStockCode.fieldNames[PstMaterialStockCode.FLD_STOCK_CODE] + " ASC");
        if (listSerial.size() > 0) {
          MaterialStockCode matStockCode = (MaterialStockCode) listSerial.get(0);
          html += "<div class=\"input-group\"><input TYPE='text' NAME='SERIAL_CODE' class='form-control serialFrom' placeholder='Serial Code' id='serial' value='" + matStockCode.getStockCode() + "' required=\"required\">"
                  + "<div class=\"input-group-addon btn btn-primary\" id=\"btnGenerateSerial\" title=\"Generate Sequential Serial\"><i class=\"fa fa-refresh\"></i></div>"
                  + "<div class=\"input-group-addon btn btn-danger btnDeleteAllSerial\" title=\"Clear All Field\"><i class=\"fa fa-undo\"></i></div></div>";
          initateSerial = matStockCode.getStockCode();
        } else {
          listSerial = PstMaterialStockCode.list(0, 1, whereClauseSerial, PstMaterialStockCode.fieldNames[PstMaterialStockCode.FLD_STOCK_CODE] + " ASC");
          if (listSerial.size() > 0) {
            MaterialStockCode matStockCode = (MaterialStockCode) listSerial.get(0);
            html += "<div class=\"input-group\"><input TYPE='text' NAME='SERIAL_CODE' class='form-control serialFrom' placeholder='Serial Code' id='serial' value='" + matStockCode.getStockCode() + "' required=\"required\">"
                    + "<div class=\"input-group-addon btn btn-primary\" id=\"btnGenerateSerial\" title=\"Generate Sequential Serial\"><i class=\"fa fa-refresh\"></i></div>"
                    + "<div class=\"input-group-addon btn btn-danger btnDeleteAllSerial\" title=\"Clear All Field\" ><i class=\"fa fa-undo\"></i></div></div>";
            initateSerial = matStockCode.getStockCode();
          }
        }
      } else {
        Vector listSerial = PstMaterialStockCode.list(0, 1, whereClauseSerial, PstMaterialStockCode.fieldNames[PstMaterialStockCode.FLD_STOCK_CODE] + " ASC");
        if (listSerial.size() > 0) {
          MaterialStockCode matStockCode = (MaterialStockCode) listSerial.get(0);
          html += "<div class=\"input-group inputGroupSerial\" id='inputGroupSerial" + (i + 1) + "'><input TYPE='text' NAME='SERIAL_CODE' class='form-control serialFrom' placeholder='Serial Code' id='serial" + (i + 1) + "' value='" + matStockCode.getStockCode() + "' required=\"required\">"
                  + "<div class=\"input-group-addon btn btn-danger btnClearSerial\" title=\"Clear Field\" data-idx='" + (i + 1) + "'><i class=\"fa fa-eraser\"></i></div>"
                  + "<div class=\"input-group-addon btn btn-danger btnDeleteSerial\" title=\"Delete Field\" data-idx='" + (i + 1) + "'><i class=\"fa fa-trash\"></i></div></div>";
          initateSerial = matStockCode.getStockCode();
        }
      }
    }

    return html;
  }

  public HashMap searchItemExchange(HttpServletRequest request, long cashCashierId) {
    int numberOfError = 0;
    String shortMessage = "";
    String html = "";

    String itemSearch = FRMQueryString.requestString(request, "dataSearch");
    int currentIndex = FRMQueryString.requestInt(request, "currentIndex");
    long idBillMain = FRMQueryString.requestLong(request, "oidBillMain");

    if (itemSearch.isEmpty()) {
      numberOfError = 1;
      shortMessage = "Item not found";

    } else {
      String whereItem = PstMaterial.fieldNames[PstMaterial.FLD_SKU] + " LIKE '%" + itemSearch + "%'"
              + " OR " + PstMaterial.fieldNames[PstMaterial.FLD_BARCODE] + " LIKE '%" + itemSearch + "%'"
              + " OR " + PstMaterial.fieldNames[PstMaterial.FLD_NAME] + " LIKE '%" + itemSearch + "%'"
              + "";
      Vector<Material> listItem = PstMaterial.list(0, 0, whereItem, "");
      if (listItem.isEmpty()) {

        numberOfError = 1;
        shortMessage = "Item not found";

      } else if (listItem.size() > 0) {
        try {
          Material m = listItem.get(0);
          //cek stok
          boolean canBuy = false;
          CashMaster cashMaster = PstCashMaster.fetchExc(PstCashCashier.fetchExc(cashCashierId).getCashMasterId());
          if (cashMaster.getCashierStockMode() == PstCashMaster.STOCK_MODE_NO_STOCK_CHECKING) {
            canBuy = true;
          } else {
            double qtyStockStockCard = SessMatCostingStokFisik.qtyMaterialBasedOnStockCard(m.getOID(), cashMaster.getLocationId(), new Date(), 0);
            //double qtyStockRealTime = SessMatCostingStokFisik.qtyMaterialBasedOnOpeningCashier(m.getOID(), new Date());
            double qtyStock = qtyStockStockCard/* - qtyStockRealTime*/;
            if (qtyStock > 0) {
              canBuy = true;
            } else {
              numberOfError = 1;
              shortMessage = "Stock for this item is empty";
            }
          }

          if (canBuy) {
            String sizeType = PstSystemProperty.getValueByName("CASHIER_SIZE_GROUP_TYPE");
            long oidMappingSize = PstMaterialMappingType.getSelectedTypeId(Integer.valueOf(sizeType), m.getOID());
            String size = "-";
            try {
              MasterType type = PstMasterType.fetchExc(oidMappingSize);
              size = type.getMasterName();
            } catch (Exception exc) {
            }
            BillMain bm = PstBillMain.fetchExc(idBillMain);
            Location l = PstLocation.fetchExc(bm.getLocationId());

            double itemPrice = getItemPriceFromPriceTypeMapping(m.getOID(), l.getPriceTypeId(), l.getStandardRateId());
            HashMap<String, Object> mapResult = getItemDiscountRegulerOrDiscountQty(m.getOID(), l.getOID(), bm.getCurrencyId(), l.getDiscountTypeId(), 1, itemPrice);
            double discPct = (Double) mapResult.get("DISC_PCT");
            double discVal = (Double) mapResult.get("DISC_VAL");

            String promotionType = PstSystemProperty.getValueByName("CASHIER_PROMOTION_GROUP_TYPE");
            Vector<MasterType> listType = PstMasterType.list(0, 0,
                    PstMasterType.fieldNames[PstMasterType.FLD_TYPE_GROUP] + " = '" + promotionType + "'",
                    PstMasterType.fieldNames[PstMasterType.FLD_MASTER_NAME]);

            long oidMapping = PstMaterialMappingType.getSelectedTypeId(Integer.valueOf(promotionType), m.getOID());
            String optionPromo = "";
            for (MasterType masterType : listType) {
              optionPromo += "<option " + (oidMapping == masterType.getOID() ? "selected" : "") + " value='" + masterType.getOID() + "'>" + masterType.getMasterName() + "</option>";
            }

            int newIndex = currentIndex + 1;
            html = ""
                    + "<tr id='rowItemExc_" + newIndex + "'>"
                    + "     <td class='text-center'><span style='cursor: pointer' class='text-red deleteItemExc' data-index='" + newIndex + "'><i class='fa fa-close'></i></span></td>"
                    + "     <td>" + m.getName()
                    + "         <input type='hidden' value='" + m.getOID() + "' name='FRM_ITEM_EXCHANGE_ID' class='form-control input-sm'>"
                    + "     </td>"
                    + "     <td>" + size + "</td>"
                    + "     <td>"
                    + "         <input type='text' value='1' name='FRM_ITEM_EXCHANGE_QTY' class='form-control input-sm qtyItemExchange' data-index='" + newIndex + "' id='qtyItemExc_" + newIndex + "'>"
                    + "     </td>"
                    + "     <td>"
                    + "         <span>" + Formater.formatNumber(itemPrice, "#,###") + "</span>"
                    + "         <input type='hidden' readonly value='" + itemPrice + "' name='FRM_ITEM_EXCHANGE_PRICE' class='form-control input-sm' id='itemPriceExc_" + newIndex + "'>"
                    + "     </td>"
                    + "     <td>"
                    + "         <select class='form-control input-sm' name='FRM_ITEM_EXCHANGE_PROMO'>"
                    + "             <option value='0'>-</option>" + optionPromo
                    + "         </select>"
                    + "     </td>"
                    + "     <td>"
                    + "         <div class='input-group'>"
                    + "             <input type='text' value='" + discPct + "' name='FRM_ITEM_EXCHANGE_DISC_PCT' class='form-control input-sm discItemExchangePct' data-index='" + newIndex + "' id='discItemExcPct_" + newIndex + "'>"
                    + "             <span class='input-group-addon'>%</span>"
                    + "         </div>"
                    + "         <div class='input-group'>"
                    + "             <span class='input-group-addon' style='font-size: 12px'>Rp</span>"
                    + "             <input type='text' value='" + discVal + "' name='FRM_ITEM_EXCHANGE_DISC_VAL' class='form-control input-sm discItemExchangeVal' data-index='" + newIndex + "' id='discItemExcVal_" + newIndex + "'>"
                    + "         </div>"
                    + "     </td>"
                    + "     <td>"
                    + "         <span id='totalPriceExcShow_" + newIndex + "'>" + Formater.formatNumber(itemPrice - discVal, "#,###") + "</span>"
                    + "         <input type='hidden' readonly value='" + (itemPrice - discVal) + "' name='FRM_ITEM_EXCHANGE_TOTAL_PRICE' class='form-control input-sm totalPriceExchange' id='totalPriceExc_" + newIndex + "'>"
                    + "     </td>"
                    + "</tr>"
                    + "";

          }

        } catch (DBException | NumberFormatException e) {
          numberOfError = 1;
          shortMessage = e.getMessage();
        }

      }
    }

    HashMap<String, Object> returnMap = new HashMap();
    returnMap.put("ERROR", numberOfError);
    returnMap.put("MESSAGE", shortMessage);
    returnMap.put("HTML", html);

    return returnMap;
  }

  public String getEditItemForm(HttpServletRequest request, BillMainJSON billMainJSON) {
    String html = "";
    try {
      //get Material Data
      long oIdMaterial = FRMQueryString.requestLong(request, "oidMaterial");
      long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
      double materialPrice = FRMQueryString.requestDouble(request, "materialPrice");
      String materialName = FRMQueryString.requestString(request, "materialName");
      int qty = FRMQueryString.requestInt(request, "qty");

      if (qty == 0) {
        qty = 1;
      }

      BillMain bm = PstBillMain.fetchExc(oidBillMain);
      Location l = PstLocation.fetchExc(bm.getLocationId());
      //CEK APAKAH BILL MEMILIKI MEMBER ID UNTUK DISKON TYPE YG BERLAKU
      long discountTypeId = l.getDiscountTypeId();
      if (PstMemberReg.checkOID(bm.getCustomerId())) {
        //GUNAKAN SETTING DISCOUNT QTY DARI DISCOUNT TYPE YG ADA DI MEMBER
        try {
          MemberReg memberReg = PstMemberReg.fetchExc(bm.getCustomerId());
          MemberGroup memberGroup = PstMemberGroup.fetchExc(memberReg.getMemberGroupId());
          if (memberGroup.getDiscountTypeId() > 0) {
            discountTypeId = memberGroup.getDiscountTypeId();
          }
        } catch (Exception e) {
        }
      } else {
        //GUNAKAN SETTING DISCOUNT QTY DARI MEMBER DEFAULT DI MASTER LOCATION
        try {
          MemberGroup memberGroup = PstMemberGroup.fetchExc(l.getMemberGroupId());
          if (memberGroup.getDiscountTypeId() > 0) {
            discountTypeId = memberGroup.getDiscountTypeId();
          }
        } catch (Exception e) {
        }
      }

      //CEK TIPE DISKON
      CtrlCustomBillDetail ccbd = new CtrlCustomBillDetail(request);
      HashMap<String, String> discMap = ccbd.getDataDiscount(oIdMaterial, materialPrice, bm.getLocationId(), bm.getCurrencyId(), discountTypeId, qty);
      int discountType = 0;
      double discountPct = 0;
      double discountVal = 0;
      try {
        discountType = Integer.valueOf(discMap.get("DISCOUNT_TYPE"));
        discountPct = Double.valueOf(discMap.get("DISCOUNT_PCT"));
        discountVal = Double.valueOf(discMap.get("DISCOUNT_VAL"));
      } catch (Exception e) {
      }

      Vector listTaxService = PstCustomBillMain.listTaxService(0, 0, " billMain." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + "='" + oidBillMain + "'", "");
      BillMainCostum billMainCostum;

      double balance = (materialPrice - discountVal) * qty;
      String readonly = "";
      String incTaxService = "";

      double amountService = 0;
      double amountTax = 0;
      double afterService = 0;
      double afterTax = 0;
      double withoutTaxService = 0;
      boolean showPriceBeforeTaxService = false;

      Material materialItem = new Material();
      try {
        if (oIdMaterial != 0) {
          materialItem = PstMaterial.fetchExc(oIdMaterial);
        }
      } catch (Exception ex) {

      }

      try {
        billMainCostum = (BillMainCostum) listTaxService.get(0);

        if (billMainCostum.getTaxInc() == PstBillMain.INC_CHANGEABLE || billMainCostum.getTaxInc() == PstBillMain.INC_NOT_CHANGEABLE) {
          double amount = balance / ((billMainCostum.getTaxPct() + billMainCostum.getServicePct() + 100) / 100);
          amountService = amount * (billMainCostum.getServicePct() / 100);
          afterService = amount + amountService;
          amountTax = amount * (billMainCostum.getTaxPct() / 100);
          afterTax = amount + amountService + amountTax;
          withoutTaxService = amount;

          if (billMainCostum.getServicePct() > 0 || billMainCostum.getTaxPct() > 0) {
            showPriceBeforeTaxService = true;
          }
        } else {
          amountService = balance * (billMainCostum.getServicePct() / 100);
          afterService = balance + amountService;
          amountTax = balance * (billMainCostum.getTaxPct() / 100);
          afterTax = balance + amountService + amountTax;
        }

        if (billMainCostum.getTaxInc() == PstBillMain.INC_NOT_CHANGEABLE) {
          readonly = "readonly='readonly'";
          incTaxService = "<small>(include tax & service)</small>";
        } else if (billMainCostum.getTaxInc() == PstBillMain.NOT_INC_NOT_CHANGEABLE) {
          readonly = "readonly='readonly'";
          incTaxService = "<small>(exclude tax & service)</small>";
        } else if (billMainCostum.getTaxInc() == PstBillMain.INC_CHANGEABLE) {
          incTaxService = "<small>(include tax & service)</small>";
        } else if (billMainCostum.getTaxInc() == PstBillMain.NOT_INC_CHANGEABLE) {
          incTaxService = "<small>(exclude tax & service)</small>";
        }
      } catch (Exception ex) {
        billMainCostum = new BillMainCostum();
      }

      String readonlyPrice = "";
      String oidSpesialRequestFood = PstSystemProperty.getValueByName("SPESIAL_REQUEST_FOOD");
      String oidSpesialRequestBeverage = PstSystemProperty.getValueByName("SPESIAL_REQUEST_BEVERAGE");
      String useSpecialRequestSettingBaseTypeMaterial = PstSystemProperty.getValueByName("SPESIAL_REQUEST_SETTING_BASE_TYPE_MATERIAL");
      String oidMaterialIdString = "" + oIdMaterial;

      if (oidMaterialIdString.equals(oidSpesialRequestBeverage) || oidMaterialIdString.equals(oidSpesialRequestFood)) {
        readonlyPrice = "";
      } else {
        readonlyPrice = "readonly='true'";
      }

      if (useSpecialRequestSettingBaseTypeMaterial.equals("1")) {
        if (materialItem.getEditMaterial() == PstMaterial.EDIT_HARGA || materialItem.getEditMaterial() == PstMaterial.EDIT_HARGA_NAME) {
          readonlyPrice = "";
        }
      }

      //cek tipe member group
            boolean useGuidePrice = checkMemberGuidePrice(bm.getOID());

      html = ""
              + "<form id='" + FrmBillDetail.FRM_NAME_BILL_DETAIL + "' name='" + FrmBillDetail.FRM_NAME_BILL_DETAIL + "'>"
              + "     <input type='hidden' name='loadtype' id='loadtype' value='editItemProc'>"
              + "     <input type='hidden' name='command' value='" + Command.SAVE + "'>"
              + "     <input type='hidden' name='ITEM_EDITED_WHEN_ADD_NEW' value='1'>"
              + "     <input type='hidden' name ='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_CASH_BILL_DETAIL_ID] + "' value='0'>"
              + "     <input type='hidden' name ='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID] + "' value='" + oIdMaterial + "'>"
              + "     <input type='hidden' id ='billMainId' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_CASH_BILL_MAIN_ID] + "' value='" + oidBillMain + "'>"
              + "     <input type='hidden' name='" + billMainJSON.fieldNames[billMainJSON.FLD_TAX_INC] + "' id='taxInc' value='" + billMainCostum.getTaxInc() + "'>"
              + "     <div class='row'>"
              + "         <div class='box-body'>"
              + "             <div class='col-md-3'>Item Name</div>"
              + "             <div class='col-md-9'><input id='materialNames' type='text' value='" + materialName + "' class='form-control' " + readonlyPrice + " name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME] + "'></div>"
              + "         </div>"
              + "     </div>"
              + "     <div class='row'>"
              + "         <div class='box-body'>"
              + "             <div class='col-md-3'>Quantity</div>"
              + "             <div class='col-md-9'><input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_QTY] + "' id='FRM_FIELD_QTY2' value='" + qty + "' class='form-control itemChange'></div>"
              + "         </div>"
              + "     </div>"
              + "     <div class='row'>"
              + "         <div class='box-body'>"
              + "             <div class='col-md-3'>Item Price</div>"
              + "             <div class='col-md-9'>" + incTaxService + "<br>"
              + "                 <input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] + "' id='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE] + "' value='" + materialPrice + "' class='form-control itemChange' " + readonlyPrice + ">"
              + "             </div>"
              + "         </div>"
              + "     </div>"
              + "     <div class='row'>"
              + "         <div class='box-body'>"
              + "             <div class='col-md-3'></div>"
              + "             <div class='col-md-4'>Total</div>"
              + "             <div class='col-md-5' style='text-align:right;' id='totalPrice'>" + Formater.formatNumber(materialPrice * qty, "#,###") + "</div>"
              + "         </div>"
              + "     </div>";

      if (useGuidePrice) {
        html += ""
                + "<div class='row'>"
                + "     <div class='box-body'>"
                + "         <div class='col-md-3'>Guide Price</div>"
                + "         <div class='col-md-9'>"
                + "             <input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_GUIDE_PRICE] + "' class='form-control' value='" + materialPrice + "'>"
                + "         </div>"
                + "     </div>"
                + "</div>";
      }

      html += ""
              + "<div class='row'>"
              + "     <div class='box-body'>"
              + "         <div class='col-md-3'>Discount Type</div>"
              + "         <input type='hidden' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_TYPE] + "' id='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_TYPE] + "' value='" + discountType + "'>"
              + "         <div class='col-md-9'><span id='discountTypeText'>" + PstDiscountQtyMapping.typeDiscount[discountType] + "</span></div>"
              + "     </div>"
              + "</div>"
              + "<div class='row'>"
              + "     <div class='box-body'>"
              + "         <div class='col-md-3'>Item Discount</div>"
              + "         <div class='col-md-3'>"
              + "             <div class='input-group'>"
              + "                 <input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] + "' id='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC_PCT] + "' value='" + discountPct + "' class='form-control itemChange'>"
              + "                 <div class='input-group-addon'>%</div>"
              + "             </div>"
              + "         </div>"
              + "         <div class='col-md-3'>"
              + "             <div class='input-group'>"
              + "                 <div class='input-group-addon'>Rp</div>"
              + "                 <input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] + "' id='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_DISC] + "' value='" + discountVal + "' class='form-control itemChange '>"
              + "             </div>"
              + "         </div>"
              + "         <div class='col-md-3'>"
              + "             <div class='input-group'>"
              + "                 <div class='input-group-addon'>~</div>"
              + "                 <input type='text' readonly id='jumlahDiskon' value='" + discountVal * qty + "' class='form-control itemChange '>"
              + "             </div>"
              + "         </div>"
              + "     </div>"
              + "</div>"
              + "<div class='row'>"
              + "     <div class='box-body'>"
              + "         <div class='col-md-3'></div>"
              + "         <div class='col-md-4'>Total</div>"
              + "         <div class='col-md-5' style='text-align:right;' id='afterDiscount'>" + Formater.formatNumber(balance, "#,###") + "</div>"
              + "     </div>"
              + "</div>";

      if (showPriceBeforeTaxService) {
        html += ""
                + "<div class='row'>"
                + "<div class='box-body'>"
                + "<div class='col-md-3'></div>"
                + "<div class='col-md-4'>Total without tax & service</div>"
                + "<div class='col-md-5' style='text-align:right;' id='withoutTaxServis'>" + Formater.formatNumber(withoutTaxService, "#,###") + "</div>"
                + "</div>"
                + "</div>";
      }
      html += ""
              + "<div class='row'>"
              + "     <div class='box-body'>"
              + "         <div class='col-md-3'>Item Service</div>"
              + "         <div class='col-md-4'>"
              + "             <div class='input-group'>"
              + "                 <input type='text' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT] + "' id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_SERVICE_PCT] + "' value='" + billMainCostum.getServicePct() + "' class='form-control itemChange' " + readonly + ">"
              + "                 <div class='input-group-addon'>%</div>"
              + "             </div>"
              + "         </div>"
              + "         <div class='col-md-5'>"
              + "             <input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_SERVICE] + "' id='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_SERVICE] + "' value='" + amountService + "' class='form-control' readonly='readonly'>"
              + "         </div>"
              + "     </div>"
              + "</div>"
              + "<div class='row'>"
              + "     <div class='box-body'>"
              + "         <div class='col-md-3'></div>"
              + "         <div class='col-md-4'>Total</div>"
              + "         <div class='col-md-5' style='text-align:right;' id='afterService'>" + Formater.formatNumber(afterService, "#,###") + "</div>"
              + "     </div>"
              + "</div>"
              + "<div class='row'>"
              + "     <div class='box-body'>"
              + "         <div class='col-md-3'>Item Tax</div>"
              + "         <div class='col-md-4'>"
              + "             <div class='input-group'>"
              + "                 <input type='text' name='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT] + "' id='" + FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_TAX_PCT] + "' value='" + billMainCostum.getTaxPct() + "' class='form-control itemChange' " + readonly + ">"
              + "                 <div class='input-group-addon'>%</div>"
              + "             </div>"
              + "         </div>"
              + "         <div class='col-md-5'>"
              + "             <input type='text' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_TAX] + "' id='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_TOTAL_TAX] + "' value='" + amountTax + "' class='form-control' readonly='readonly'>"
              + "         </div>"
              + "     </div>"
              + "</div>"
              + "<div class='row'>"
              + "     <div class='box-body'>"
              + "         <div class='col-md-3'></div>"
              + "         <div class='col-md-4'>Total</div>"
              + "         <div class='col-md-5' style='text-align:right;' id='total'>" + Formater.formatNumber(afterTax, "#,###") + "</div>"
              + "     </div>"
              + "</div>"
              + "<div class='row'>"
              + "     <div class='box-body'>"
              + "         <div class='col-md-3'>Note</div>"
              + "         <div class='col-md-9'>"
              + "             <textarea class='form-control' name='" + FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_NOTE] + "'></textarea>"
              + "         </div>"
              + "     </div>"
              + "</div>"
              + "<div class='row'>"
              + "     <div class='box-body'>"
              + "         <div class='col-md-6'>"
              + "             <button type='button' class='btn btn-danger' data-dismiss='modal'><i class='fa fa-close'></i> Cancel</button>"
              + "         </div>"
              + "         <div class='col-md-6'>"
              + "             <button type='submit' class='btn btn-primary pull-right' id='btnSaveBillDetail'><i class='fa fa-check'></i> Save</button>"
              + "         </div>"
              + "     </div>"
              + "</div>"
              + "</form>";
    } catch (Exception ex) {
      html = "" + ex.getMessage().toString();
    }
    return html;
  }

    public static boolean checkMemberGuidePrice(long billMainId) {
        boolean result = false;
        try {
            String oidCustomerTypeGuide = com.dimata.common.entity.system.PstSystemProperty.getValueByName("OID_TIPE_CUSTOMER_GUIDE");
            if (oidCustomerTypeGuide.isEmpty() || oidCustomerTypeGuide.equals("0") || billMainId == 0) {
                return false;
            }
            BillMain billMain = PstBillMain.fetchExc(billMainId);
            if (billMain.getCustomerId() != 0) {
                MemberReg memberReg = PstMemberReg.fetchExc(billMain.getCustomerId());
                if (memberReg.getMemberGroupId() != 0) {
                    if (memberReg.getMemberGroupId() == Long.valueOf(oidCustomerTypeGuide)) {
                        return true;
                    }
                }
            }
        } catch (DBException | NumberFormatException e) {
            result = false;
        }
        return result;
    }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
  /**
   * Handles the HTTP <code>GET</code> method.
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
   * Handles the HTTP <code>POST</code> method.
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



