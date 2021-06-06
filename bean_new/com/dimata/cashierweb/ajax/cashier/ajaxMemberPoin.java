/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.ajax.cashier;

import com.dimata.posbo.entity.masterdata.Material;
import com.dimata.posbo.entity.masterdata.MemberPoin;
import com.dimata.posbo.entity.masterdata.PstMaterial;
import com.dimata.posbo.entity.masterdata.PstMemberPoin;
import com.dimata.posbo.entity.masterdata.PstMemberReg;
import com.dimata.posbo.entity.masterdata.PstMemberRegistrationHistory;
import com.dimata.posbo.session.masterdata.SessMemberReg;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Asus
 */
public class ajaxMemberPoin extends HttpServlet {
    
    private String searchTerm;
    private String colName;
    private int colOrder;
    private String dir;
    private int start;
    private int amount;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        
        //massage staus respon
        JSONObject Obmassage = new JSONObject();
        JSONArray JsStatus = new JSONArray();
        JSONArray JsRespon = new JSONArray();
        
        String patterns = "###,###.###";
        DecimalFormat rupiah = new DecimalFormat(patterns);
        
        String action = FRMQueryString.requestString(request,"action");
        if(action.equals("tampilMemberInfo")){
            Long oidMember = FRMQueryString.requestLong(request, "oidMember");
             try {
                //get member info
                String wrClause = "";
                int point = 0;
                wrClause = "MBR."+PstMemberReg.fieldNames[PstMemberReg.FLD_CONTACT_ID]+"="+oidMember+" AND ";
                Vector memberPoin = SessMemberReg.memberPoin(0, 0,wrClause,"");
                HashMap<String,String> data = new HashMap<>();
                if(memberPoin.size()>0){
                    for (int i =0 ; i<memberPoin.size();i++){
                        data = (HashMap) memberPoin.get(i);
                    }
                    //get member poin
                    MemberPoin entMemberPoin = PstMemberPoin.getTotalPoint(Long.valueOf(data.get("CONTACT_ID")));
                    point = entMemberPoin.getCredit() - entMemberPoin.getDebet();
                    
                }
            
                String expiredmember = data.get("VALID_EXPIRED_DATE");
                Date date1 = new  SimpleDateFormat ( "yyyy-mm-dd" ) .parse (expiredmember);
                
                SimpleDateFormat DateFor = new SimpleDateFormat("dd - MM - yyyy");
                String stringDate= DateFor.format(date1);
                
                String htmlContent = "";
                htmlContent ="<div class=\"box-body\">\n" +
            "                   <input type='hidden' id='dtOidMember' value='"+oidMember+"'>"+
            "                   <input type='hidden' id='dtMemberpoit' value='"+point+"'>"+
            "                    <div class=\"col-md-2\">\n" +
            "                        <b>Member Name</b>\n" +
            "                    </div>\n" +
            "                    <div class=\"col-md-4\">\n" +
            "                        : "+data.get("PERSON_NAME")+"\n" +
            "                    </div>\n" +
            "                    <div class=\"col-md-2\">\n" +
            "                        <b>Expired Date</b>\n" +
            "                    </div>\n" +
            "                    <div class=\"col-md-2\">\n" +
            "                        : "+stringDate+"\n" +
            "                    </div>\n" +
            "                </div>\n" +
            "                <div class=\"box-body\">\n" +
            "                    <div class=\"col-md-2\">\n" +
            "                        <b>Current Poin</b>\n" +
            "                    </div>\n" +
            "                    <div class=\"col-md-2\">\n" +
            "                        : "+rupiah.format(point)+"\n" +
            "                    </div>\n" +
            "                </div>";
                
                JsRespon.put(htmlContent);
                JsStatus.put("succes");
        
                Obmassage.put("massage", JsRespon);
                Obmassage.put("status", JsStatus);
            } catch (JSONException ex) {
                try {
                    Obmassage.put("massage", ex.getMessage());
                    Obmassage.put("status", "error");
                } catch (Exception e) {
                }
            } catch (ParseException ex) {
                Logger.getLogger(ajaxMemberPoin.class.getName()).log(Level.SEVERE, null, ex);
            }

            out.print(Obmassage);
        }else if(action.equals("searchItemChange")){
            Long oidMember = FRMQueryString.requestLong(request, "oidMember");
            String wrClause = "";
            int point = 0;
            SimpleDateFormat dateParse = new SimpleDateFormat("yyyy-mm-dd");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
            
            try {
                wrClause = "MBR."+PstMemberReg.fieldNames[PstMemberReg.FLD_CONTACT_ID]+"="+oidMember+" AND ";
                Vector memberPoin = SessMemberReg.memberPoin(0, 0,wrClause,"");
                HashMap<String,String> data = new HashMap<>();
                if(memberPoin.size()>0){
                    for (int i =0 ; i<memberPoin.size();i++){
                        data = (HashMap) memberPoin.get(i);
                    }
                    //get member poin
                    MemberPoin entMemberPoin = PstMemberPoin.getTotalPoint(Long.valueOf(data.get("CONTACT_ID")));
                    point = entMemberPoin.getCredit() - entMemberPoin.getDebet();
                }
            
                //mengecek expired member 
                String expiredmember = data.get("VALID_EXPIRED_DATE");
                Date dateNow = new Date();  
                Date expired = dateParse.parse(expiredmember);
                String dateFromat = dateFormat.format(expired);
                int checkExpiredMember = expired.compareTo(dateNow);
                 
                String htmlContent = "";
                if(checkExpiredMember>=0){
                htmlContent ="<div class=\"input-group\">\n" +
"                    <input id=\"itemSearchExchange\" type=\"text\" name=\"\" class=\"form-control itemSearchExchange\" placeholder=\"Scan or Search Items (F9)\"/>\n" +
"                    <div class=\"input-group-addon\" style=\"padding:0px;border:none;\">\n" +
"                        <button class=\"btn btn-primary input-group-addon btnItemsearchExchange\" id=\"btnSearchItemExchange\" style=\"padding-bottom: 9px;padding-top: 9px;padding-right: 25px;border-right: 1px solid #D2D6DE\">\n" +
"                            <i class=\"fa fa-search\"></i>\n" +
"                        </button>\n" +
"                    </div>\n" +
"                </div>\n" +
"                <br>\n" +
"                <div>\n" +
"                    <table class=\"table table-striped table-bordered\" id=\"itemExchange\" style=\"width: 100%\">\n" +
"                        <thead>\n" +
"                            <tr>\n" +
"                                <th>No Member</th>\n" +
"                                <th>Barcode</th>\n" +
"                                <th>Nama</th>\n" +
"                                <th>Jumlah Poin</th>\n" +
"                                <th>Action</th>\n" +
"                            </tr>\n" +
"                        </thead>\n" +
"                    </table>\n" +
"                </div>";
                }else{
                    htmlContent = "<div class='errorMassage'>Masa Berlaku Bember Anda Telah Sabis Sejak "+dateFromat+"</div>";
                }
                JsRespon.put(htmlContent);
                JsStatus.put("succes");
        
                Obmassage.put("massage", JsRespon);
                Obmassage.put("status", JsStatus);
            } catch (JSONException ex) {
                try {
                    Obmassage.put("massage", ex.getMessage());
                    Obmassage.put("status", "error");
                } catch (Exception e) {
                }
            } catch (ParseException ex) { 
                Logger.getLogger(ajaxMemberPoin.class.getName()).log(Level.SEVERE, null, ex);
            }

            out.print(Obmassage);
        }else if(action.equals("listItemExchange")){
            Long oidMember = FRMQueryString.requestLong(request, "oidMember");
            try {
                String htmlContent = "";
                htmlContent =" <table class=\"table table-striped table-bordered\" id=\"tbItemListExchange\" style=\"width: 100%\">\n" +
"                                        <thead>\n" +
"                                            <tr>\n" +
"                                                <th>No</th>\n" +
"                                                <th>Kode</th>\n" +
"                                                <th>Name</th>\n" +
"                                                <th>Barcode</th>\n" +
"                                                <th>Poin</th>\n" +
"                                                <th>Action</th>\n" +
"                                            </tr>\n" +
"                                        </thead>\n" +
"                                    </table>";
                
                JsRespon.put(htmlContent);
                JsStatus.put("succes");
        
                Obmassage.put("massage", JsRespon);
                Obmassage.put("status", JsStatus);
            } catch (JSONException ex) {
                try {
                    Obmassage.put("massage", ex.getMessage());
                    Obmassage.put("status", "error");
                } catch (Exception e) {
                }
            }

            out.print(Obmassage);
        }
        
        
    }

    public JSONObject listMemeberPoin(HttpServletRequest request, HttpServletResponse response,String user){
        JSONObject result = new JSONObject();
        String dataSearch = FRMQueryString.requestString(request,"dataSearch");
        this.searchTerm = dataSearch;
        
        String[] cols = {""+PstMemberReg.fieldNames[PstMemberReg.FLD_MEMBER_BARCODE]+"",
                         ""+PstMemberReg.fieldNames[PstMemberReg.FLD_MEMBER_BARCODE]+"", 
                         ""+PstMemberReg.fieldNames[PstMemberReg.FLD_PERSON_NAME]+"", 
                         ""+PstMemberReg.fieldNames[PstMemberReg.FLD_PERSON_NAME]+"",
                        ""+PstMemberRegistrationHistory.fieldNames[PstMemberRegistrationHistory.FLD_VALID_EXPIRED_DATE]+""};

        int amount = 10;
        int start = 0;
        int col = 0;
        String dir = "asc";
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");

        if (sStart != null) {
            start = Integer.parseInt(sStart);
            if (start < 0) {
                start = 0;
            }
        }
        if (sAmount != null) {
            amount = Integer.parseInt(sAmount);
            if (amount < 10) {
                amount = 10;
            }
        }
        if (sCol != null) {
            col = Integer.parseInt(sCol);
            if (col < 0)
                col = 0;
        }
        if (sdir != null) {
            if (!sdir.equals("asc"))
            dir = "desc";
        }

        String whereClause = "";

        if(!user.equals("0")){
            whereClause = "";
            if (this.searchTerm == null){
                whereClause += "";                  
            }else{
                whereClause +=" ("+PstMemberReg.fieldNames[PstMemberReg.FLD_MEMBER_BARCODE]+" like '%"+this.searchTerm+"%'";
                whereClause +=" OR "+PstMemberReg.fieldNames[PstMemberReg.FLD_PERSON_NAME]+" like '%"+this.searchTerm+"%'";
                whereClause += ") AND ";
            }
        }else{
            if (this.searchTerm == null){
                whereClause += "";                  
            }else{
                whereClause +=" AND ("+PstMemberReg.fieldNames[PstMemberReg.FLD_MEMBER_BARCODE]+" like '%"+this.searchTerm+"%'";
                whereClause +=" OR "+PstMemberReg.fieldNames[PstMemberReg.FLD_PERSON_NAME]+" like '%"+this.searchTerm+"%'";
                whereClause += ") AND";

            }
        }

        int total = -1;
        Vector memberPoin = SessMemberReg.memberPoin(0,0, whereClause,"");
        total = memberPoin.size();
        String colName = cols[col];
        this.amount = amount;
        this.searchTerm = request.getParameter("sSearch");
        this.colName = colName;
        this.dir = dir;
        this.start = start;
        this.colOrder = col;

        try {
            result = getMemberUser(total, request, user, whereClause);
        } catch(Exception ex){
            System.out.println(ex);
        }

       return result;
    }

    public JSONObject getMemberUser(int total, HttpServletRequest request,String user, String whereClause) throws JSONException{

        int totalAfterFilter = total;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String approot = request.getParameter("approot");
        String order ="";

        SimpleDateFormat dateParse = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");    
        
        String patterns = "###,###.###";
        DecimalFormat rupiah = new DecimalFormat(patterns);

        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
        Vector memberPoin = SessMemberReg.memberPoin(start, amount,whereClause,order);
        int no = start+1;
        for (int i =0 ; i<=memberPoin.size()-1;i++){
            HashMap<String,String> data = (HashMap) memberPoin.get(i);
            JSONArray ja = new JSONArray();
            try{
                //get member poin
                MemberPoin entMemberPoin = PstMemberPoin.getTotalPoint(Long.valueOf(data.get("CONTACT_ID")));
                int point = entMemberPoin.getCredit() - entMemberPoin.getDebet();
                ja.put(no);
                ja.put(data.get("CONTACT_CODE"));
                ja.put(data.get("PERSON_NAME"));
                ja.put(rupiah.format(point));
                
                String expiredmember = data.get("VALID_EXPIRED_DATE");
                Date dateNow = new Date();  
                Date expired = dateParse.parse(expiredmember);
                String dateFromat = dateFormat.format(expired);
                int checkExpiredMember = expired.compareTo(dateNow);
                String style = "class='nonExpiredMember'";
                if(checkExpiredMember<=0){
                    style = "class='expiredMember'";
                }
                ja.put("<div "+style+">"+dateFromat+"</div>");
                ja.put("<button id=\"tukarPoin\" type=\"button\" value='"+data.get("CONTACT_ID")+"' class=\"btn btn-primary\">\n" +
"                                        <i class=\"fa fa-refresh\"></i>\n" +
"                                        Tukar\n" +
"                                    </button>");
                no++;
                array.put(ja);
            }catch(Exception ex){

            }

        }

        totalAfterFilter = total;

        try {
            result.put("iTotalRecords", total);
            result.put("iTotalDisplayRecords", totalAfterFilter);
            result.put("aaData", array);
        } catch (Exception e) {

        }

        return result;
    }
    
    public JSONObject listItemList(HttpServletRequest request, HttpServletResponse response,String user){
        JSONObject result = new JSONObject();
        
        String[] cols = {""+PstMaterial.fieldNames[PstMaterial.FLD_SKU]+"",
                         ""+PstMaterial.fieldNames[PstMaterial.FLD_SKU]+"",
                         ""+PstMaterial.fieldNames[PstMaterial.FLD_NAME]+"", 
                         ""+PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]+"", 
                         ""+PstMaterial.fieldNames[PstMaterial.FLD_MINIMUM_POINT]+""};

        int amount = 10;
        int start = 0;
        int col = 0;
        String dir = "asc";
        String sStart = request.getParameter("iDisplayStart");
        String sAmount = request.getParameter("iDisplayLength");
        String sCol = request.getParameter("iSortCol_0");
        String sdir = request.getParameter("sSortDir_0");

        if (sStart != null) {
            start = Integer.parseInt(sStart);
            if (start < 0) {
                start = 0;
            }
        }
        if (sAmount != null) {
            amount = Integer.parseInt(sAmount);
            if (amount < 10) {
                amount = 10;
            }
        }
        if (sCol != null) {
            col = Integer.parseInt(sCol);
            if (col < 0)
                col = 0;
        }
        if (sdir != null) {
            if (!sdir.equals("asc"))
            dir = "desc";
        }

        String whereClause = "";

        if(!user.equals("0")){
            whereClause = "";
            if (this.searchTerm == null){
                whereClause += "";                  
            }else{
                whereClause +=" ("+PstMaterial.fieldNames[PstMaterial.FLD_SKU]+" like '%"+this.searchTerm+"%'";
                whereClause +=" OR "+PstMaterial.fieldNames[PstMaterial.FLD_NAME]+" like '%"+this.searchTerm+"%'";
                whereClause +=" OR "+PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]+" like '%"+this.searchTerm+"%'";
                whereClause +=" OR "+PstMaterial.fieldNames[PstMaterial.FLD_MINIMUM_POINT]+" like '%"+this.searchTerm+"%'";
                whereClause += ")";
            }
        }else{
            if (this.searchTerm == null){
                whereClause += "";                  
            }else{
                whereClause +=" AND ("+PstMaterial.fieldNames[PstMaterial.FLD_SKU]+" like '%"+this.searchTerm+"%'";
                whereClause +=" OR "+PstMaterial.fieldNames[PstMaterial.FLD_NAME]+" like '%"+this.searchTerm+"%'";
                whereClause +=" OR "+PstMaterial.fieldNames[PstMaterial.FLD_BARCODE]+" like '%"+this.searchTerm+"%'";
                whereClause +=" OR "+PstMaterial.fieldNames[PstMaterial.FLD_MINIMUM_POINT]+" like '%"+this.searchTerm+"%'";
                whereClause += ")";

            }
        }

        int total = -1;
        Vector memberPoin = PstMaterial.list(0,0, whereClause,"");
        total = memberPoin.size();
        String colName = cols[col];
        this.amount = amount;
        this.searchTerm = request.getParameter("sSearch");
        this.colName = colName;
        this.dir = dir;
        this.start = start;
        this.colOrder = col;

        try {
            result = getItemList(total, request, user, whereClause);
        } catch(Exception ex){
            System.out.println(ex);
        }

       return result;
    }

    public JSONObject getItemList(int total, HttpServletRequest request,String user, String whereClause) throws JSONException{

        int totalAfterFilter = total;
        JSONObject result = new JSONObject();
        JSONArray array = new JSONArray();
        String approot = request.getParameter("approot");
        String order ="";  
        
        String patterns = "###,###.###";
        DecimalFormat rupiah = new DecimalFormat(patterns);

        if (this.colOrder>=0){
            order += ""+colName+" "+dir+"";
        }
        Vector memberPoin = PstMaterial.list(start, amount,whereClause,order);
        int no = start+1;
        for (int i =0 ; i<=memberPoin.size()-1;i++){
            Material material = (Material) memberPoin.get(i);
            JSONArray ja = new JSONArray();
            try{
                ja.put(no);
                ja.put(material.getSku());
                ja.put(material.getName());
                ja.put(material.getBarCode());
                ja.put(rupiah.format(material.getMinimumPoint()));
                ja.put("<button id=\"tukarPoin\" type=\"button\" value='"+material.getOID()+"' class=\"btn btn-primary\">\n" +
"                                        <i class=\"fa fa-refresh\"></i>\n" +
"                                        Tukar\n" +
"                                    </button>");
                no++;
                array.put(ja);
            }catch(Exception ex){

            }

        }

        totalAfterFilter = total;

        try {
            result.put("iTotalRecords", total);
            result.put("iTotalDisplayRecords", totalAfterFilter);
            result.put("aaData", array);
        } catch (Exception e) {

        }

        return result;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        int iCommand = Integer.parseInt(request.getParameter("command"));
        if (iCommand==Command.LOAD){
            int table = Integer.parseInt(request.getParameter("table"));
            if (table == 1){
                //load kabupaten
                JSONObject result = new JSONObject();
                String user = request.getParameter("user");
                result = listMemeberPoin(request, response,user);
                response.setContentType("application/json");
                response.setHeader("Cache-Control", "no-store");
                PrintWriter out = response.getWriter();
                out.print(result);
            }else if (table == 2){
                //load kabupaten
                JSONObject result = new JSONObject();
                String user = request.getParameter("user");
                result = listItemList(request, response,user);
                response.setContentType("application/json");
                response.setHeader("Cache-Control", "no-store");
                PrintWriter out = response.getWriter();
                out.print(result);
            }
        }else{
            processRequest(request, response);
        }
    }

   
    
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
