
package com.dimata.cashierweb.ajax.cashier;

import com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain;
import com.dimata.pos.entity.balance.CashCashier;
import com.dimata.pos.entity.balance.PstCashCashier;
import com.dimata.pos.entity.billing.BillMain;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.entity.masterCashier.PstCashMaster;
import com.dimata.pos.entity.payment.PstCashPayment;
import com.dimata.posbo.entity.masterdata.PstShift;
import com.dimata.posbo.entity.masterdata.PstVoucher;
import com.dimata.posbo.entity.masterdata.Shift;
import com.dimata.posbo.entity.masterdata.Voucher;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CashierPayment extends HttpServlet {
    
     //OBJECT
    private JSONObject jSONObject = new JSONObject();
    private JSONArray jSONArray = new JSONArray();
    
     //LONG
    private long oidReturn = 0;
    
    //STRING
    private String dataFor = "";
    private String approot = "";
    private String htmlReturn = "";
    private int isClear = 0;
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
        this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
        this.iCommand = FRMQueryString.requestCommand(request);
        this.isClear = 0;
        
        this.iErrCode = 0;        
        this.jSONObject = new JSONObject();
        
        switch(this.iCommand){
	    case Command.SAVE :
		commandSave(request);
	    break;
                
            case Command.LIST :
                commandList(request);
            break;
		
	    
	    default : commandNone(request);
	}
        
        try{
	    
	    this.jSONObject.put("FRM_FIELD_HTML", this.htmlReturn);
	    this.jSONObject.put("FRM_FIELD_RETURN_OID", this.oidReturn);
            this.jSONObject.put("FRM_FIELD_IS_CLEAR", this.isClear);
	}catch(JSONException jSONException){
	    jSONException.printStackTrace();
	}
        
        response.getWriter().print(this.jSONObject);
        
    }
    
    public void commandNone(HttpServletRequest request){
	if (this.dataFor.equals("checkLastOpening")){
            this.htmlReturn = checkLastOpening(request);
        }else if (this.dataFor.equals("getCountOpenBill")){
            this.htmlReturn = getCountOpenBill(request);
            this.isClear = getListTable(request);
        }
    }
    
    public void commandSave(HttpServletRequest request){
	
    }
    
    public void commandList(HttpServletRequest request){
        if (this.dataFor.equals("searchVoucherRegular")){
            this.htmlReturn = searchVoucherRegular(request);
        }
    }
    
    private String searchVoucherRegular(HttpServletRequest request){
        String htmlReturn="";
        int masterType = FRMQueryString.requestInt(request, "MASTER_FRM_FIELD_PAY_TYPE");
        
        //STRING
        String typeText = "";       
        String where="";
        
        //LONG
        long locationId = 0;
        
        //DATE
        Date dateToday = new Date();
        String dateTodayStr = Formater.formatDate(dateToday, "yyyy-MM-dd");
        
        //INTEGER
        int showPage = 1;
        int jmlShowData = 10;
        int page = 0;
        int totalData =0;
        int start=0;
        int jmlData=0;
        
        //OBJECT
        Voucher voucher = new Voucher();
        BillMain billMain = new BillMain();
        
        
        try {
            showPage = FRMQueryString.requestInt(request, "FRM_FIELD_SHOW_PAGE");
        } catch (Exception e) {
            showPage = 1;
        }
        
        try {
            typeText = FRMQueryString.requestString(request, "FRM_FIELD_TYPE_TEXT");
        } catch (Exception e) {
            typeText = "";
        }

        long oidBilMain = FRMQueryString.requestLong(request, "FRM_FIELD_BILL_MAIN");
        
        if (showPage==0){
            showPage = 1;
        }
        
        start = (showPage-1) * jmlShowData;

        try {
            billMain = PstBillMain.fetchExc(oidBilMain);
        } catch (Exception e) {
        }
        
        where = " "
        + " ("+PstVoucher.fieldNames[PstVoucher.FLD_VOUCHEROUTLET]+"='0' OR"
        + " "+PstVoucher.fieldNames[PstVoucher.FLD_VOUCHEROUTLET]+" = '"+billMain.getLocationId()+"')"
        + " AND "+PstVoucher.fieldNames[PstVoucher.FLD_VOUCHEREXPIRED]+">='"+dateTodayStr+"'"
        + " AND "+PstVoucher.fieldNames[PstVoucher.FLD_VOUCHERSTATUS]+"='0' "
        + " AND "+PstVoucher.fieldNames[PstVoucher.FLD_VOUCHERTYPE]+"='"+PstCustomBillMain.mappingToProchain[masterType]+"'";
        
        if (typeText!=""){
            where += " "
            + "AND ("+PstVoucher.fieldNames[PstVoucher.FLD_VOUCHERNO]+" like '%"+typeText+"%'"
            + " OR "+PstVoucher.fieldNames[PstVoucher.FLD_VOUCHERNAME]+" like '%"+typeText+"%'"
            + " OR "+PstVoucher.fieldNames[PstVoucher.FLD_VOUCHERNOMINAL]+" like '%"+typeText+"%')";
        }
        
        totalData = PstVoucher.getCount(where);
        Vector listData = PstVoucher.list(start, jmlShowData, where, "");
        jmlData = totalData;
        
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

        htmlReturn +=""
        + "<table class='table table-bordered table-hover table-striped'>"
            + "<thead>"
                + "<tr>"
                    + "<th>No.</th>"
                    + "<th>Voucher No.</th>"
                    + "<th>Name</th>"
                    + "<th>Nominal</th>"                   
                + "</tr>"
            + "</thead>"
            + "<tbody>";
                if (listData.size()==0){
                    htmlReturn += ""
                    + "<tr>"
                        + "<td colspan='5'>No Data</td>"
                    + "</tr>";
                }else{
                    int count=0;
                    for (int i = 0; i<listData.size();i++){
                        count = i+1;
                        voucher = (Voucher) listData.get(i);
                        htmlReturn += ""
                        + "<tr style='cursor:pointer' class='selectVoucherClick' data-nominal='"+voucher.getVoucherNominal()+"' data-voucherno='"+voucher.getVoucherNo()+"' data-voucherid='"+voucher.getOID()+"'>"
                            + "<td>"+count+"</td>"
                            + "<td>"+voucher.getVoucherNo()+"</td>"
                            + "<td>"+voucher.getVoucherName()+"</td>"
                            + "<td style='text-align:right;'>"+Formater.formatNumber(voucher.getVoucherNominal(), "###,###,##0")+"</td>"
                        + "</tr>";
                    }
                    
                }
                
            htmlReturn +=""  
            + "</tbody>"
        + "</table>";
            
        htmlReturn += ""
        + "<div class='row'>"
            + "<input type='hidden' id='totalPagingVc' value='"+Formater.formatNumber(JumlahHalaman,"")+"'>"
            + "<input type='hidden' id='paggingPlaceVc' value='"+showPage+"'>"
            + "<div class='col-md-6'>"
                + "<label>"
                    + "Page "+showPage+" of "+Formater.formatNumber(JumlahHalaman, "")+""
                + "</label>"
            + "</div>"
            + "<div class='col-md-6'>"
                + "<div data-original-title='Status' class='box-tools pull-right' title=''>" 
                    + "<div class='btn-group' data-toggle='btn-toggle'>" 
                        + "<button "+attDisFirst+" data-type='first' type='button' class='btn btn-default pagingVc' data-master-type=''>"
                            + "<i class='fa fa-angle-double-left'></i>"
                        + "</button>" 
                        + "<button id='prevPagingVc' "+attDisFirst+" data-type='prev' type='button' class='btn btn-default pagingVc' data-master-type=''>"
                            + " <i class='fa fa-angle-left'></i>"
                        + "</button>" 
                        + "<button id='nextPagingVc' "+attDisNext+" data-type='next' type='button' class='btn btn-default pagingVc' data-master-type=''>"
                            + "<i class='fa fa-angle-right'></i>"
                        + "</button>" 
                        + "<button "+attDisNext+" data-type='last' type='button' class='btn btn-default pagingVc' data-master-type=''>"
                            + " <i class='fa fa-angle-double-right'></i>"
                        + "</button>" 
                    + "</div>" 
                + "</div>"                           
            + "</div>"                          
        + "</div>";
        
        return htmlReturn;
    }
    
    private String checkLastOpening(HttpServletRequest request){
        String htmlReturn="";
        String whereCashier="";
        long userId = FRMQueryString.requestLong(request, "FRM_FIELD_USER_OID");
        if (userId!=0){
            whereCashier=""
                + " CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID]+"="+userId+" "
                + " AND CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID]+"=1";
            
            Vector vctCashCashier = PstCashCashier.listJoin(0,0,whereCashier,"");
            CashCashier oldCashCashier  = new CashCashier();
            
            if ((vctCashCashier!=null)&&(vctCashCashier.size()>0)){
                long shiftIdTmp = 0;
                long shiftIdTmpElse = 0;
                String timeNow = Formater.formatDate(new Date(), "HH:mm:ss");
                Time timeshiftNow = Time.valueOf(timeNow);
                
                Vector listShift = PstShift.list(0, 0, "", PstShift.fieldNames[PstShift.FLD_END_TIME] + " DESC ");
                for (int i = 0; i < listShift.size(); i++) {
                    Shift shift = (Shift) listShift.get(i);
                    String time = Formater.formatDate(shift.getStartTime(), "HH:mm:ss");
                    Time timeShift = Time.valueOf(time);
                    if (timeshiftNow.after(timeShift) || timeshiftNow.equals(timeShift)) {
                        shiftIdTmp = shift.getOID();
                        i = listShift.size();
                    } else {
                        shiftIdTmpElse = shift.getOID();
                    }
                }
                
                oldCashCashier=(CashCashier)vctCashCashier.get(0);
                Shift shift = new Shift();
                String strDate = "";
                try{
                      shift = PstShift.fetchExc(oldCashCashier.getShiftId());
                }catch(Exception exc){
                      shift = new Shift();
                }

                String DateNow = Formater.formatDate(new Date(), "dd MMMM yyyy");
                try{
                       strDate = Formater.formatDate(oldCashCashier.getOpenDate(),"dd MMMM yyyy");
                }catch(Exception exc){
                       strDate = "";
                }
                
                if(DateNow.equals(strDate)){
                    if(shiftIdTmp==shift.getOID()){
                        htmlReturn ="0";
                    }else{
                        htmlReturn ="1";
                    }
                  }else{
                    htmlReturn ="1";
                }
                
                
            }else{
                htmlReturn ="0";
            }
            
        }else{
            htmlReturn ="0";
        }
        
        return htmlReturn;
    }
    
    private String getCountOpenBill(HttpServletRequest request){
        String htmlReturn="";
        
        long appUserId =0;
        double total;
        
        appUserId = FRMQueryString.requestLong(request, "FRM_FIELD_APP_USER");
        total = PstBillMain.getTotalOpenBill(appUserId);
        
        htmlReturn = String.valueOf(total);

        return htmlReturn;
    }
    
    private int getListTable(HttpServletRequest request){
        String result = "";
        int showPage = 1;
        int jmlShowData = 10;
        Vector listBill = new Vector(1, 1);
        long appUserId = FRMQueryString.requestLong(request, "FRM_FIELD_APP_USER");
        long oidLocation = FRMQueryString.requestLong(request, "location");
        
        long locationId = PstCashMaster.getLocation("cc."+PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID]+"='"+appUserId+"' "
                + "AND cc."+PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID]+"='1'");

        String whereOpenTable = ""
                + " cbm." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "=0"
                + " AND cbm." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "=0"
                + " AND cbm." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "=1"
                + " AND cbm." + PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID] + "=" + locationId + "";

        int listTotal = PstBillMain.getCountTableOpenBill(0, 0, whereOpenTable, "");

        
        return listTotal;
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
