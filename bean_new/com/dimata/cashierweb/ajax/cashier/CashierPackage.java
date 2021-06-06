/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.cashierweb.ajax.cashier;

import com.dimata.cashierweb.entity.cashier.transaction.HotelRoom;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain;
import com.dimata.cashierweb.entity.cashier.transaction.PstHotelRoom;
import com.dimata.cashierweb.entity.cashier.transaction.PstReservation;
import com.dimata.cashierweb.entity.cashier.transaction.Reservation;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.gui.jsp.ControlCombo;
import com.dimata.hanoman.entity.masterdata.BillingItemGroup;
import com.dimata.hanoman.entity.masterdata.Contact;
import com.dimata.hanoman.entity.masterdata.CustomePackBilling;
import com.dimata.hanoman.entity.masterdata.CustomePackageSchedule;
import com.dimata.hanoman.entity.masterdata.PstCustomePackBilling;
import com.dimata.hanoman.entity.masterdata.PstCustomePackageSchedule;
import com.dimata.pos.entity.billing.Billdetail;
import com.dimata.pos.entity.billing.PstBillDetail;
import com.dimata.pos.form.billing.FrmBillMain;
import com.dimata.posbo.entity.masterdata.Material;
import com.dimata.posbo.entity.masterdata.Merk;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author Witar
 */
public class CashierPackage extends HttpServlet {
    
    //OBJECT
    private JSONObject jSONObject = new JSONObject();
    private JSONArray jSONArray = new JSONArray();
    
     //LONG
    private long oidReturn = 0;
    
    //STRING
    private String dataFor = "";
    private String approot = "";
    private String htmlReturn = "";
    
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
        this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
        this.iCommand = FRMQueryString.requestCommand(request);
        
        this.iErrCode = 0;        
        this.jSONObject = new JSONObject();
        
        switch(this.iCommand){
	    case Command.SAVE :
		//commandSave(request);
	    break;
                
            case Command.LIST :
                //commandList(request);
            break;
		
	    
	    default : commandNone(request);
	}
        
        try{
	    
	    this.jSONObject.put("FRM_FIELD_HTML", this.htmlReturn);
	    this.jSONObject.put("FRM_FIELD_RETURN_OID", this.oidReturn);
	}catch(JSONException jSONException){
	    jSONException.printStackTrace();
	}
        
        response.getWriter().print(this.jSONObject);
        
    }
    
    public void commandNone(HttpServletRequest request){
	if (this.dataFor.equals("showFilterFormPackage")){
            this.htmlReturn = showFilterFormPackage(request);
        }else if (this.dataFor.equals("showReservationPackage")){
            this.htmlReturn = showReservationPackage(request);
        }else if (this.dataFor.equals("showBillPackage")){
            this.htmlReturn = listPackageItem(request);
        }
    }
    
    
    
    private String showFilterFormPackage(HttpServletRequest request){
        String htmlReturn="";
        
        String outletMultiLocation = FRMQueryString.requestString(request, "FRM_FIELD_MULTI_LOCATION");
        String defaultLocation = FRMQueryString.requestString(request, "FRM_FIELD_DEFAULT_LOCATION");
        
        Vector location_key = new Vector(1,1);
        Vector location_value = new Vector(1,1);
        
        Vector listLocation = PstLocation.listLocationStore(0,0,"","");
        if(listLocation.size() != 0){
            for(int i = 0; i<listLocation.size(); i++){
                Location location1 = (Location) listLocation.get(i);
                location_key.add(""+location1.getOID());
                location_value.add(""+location1.getName());
            }
        }

        location_key.add("0");
        location_value.add(" -- All Location --");
        
        htmlReturn +=""
        + "<div class='row'>"
            + "" 
            + "<div class='box-body'>" 
                + "<div class='col-md-12'>"; 
                    if (outletMultiLocation.equals("1")){
                        htmlReturn += ""
                        + "<div class='form-group'>"+ControlCombo.drawBootsratap(FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]+"_PACK", null, "", location_key, location_value, "required='required'", "form-control")+"</div>";                       
                    }else{
                        htmlReturn += ""
                        + "<input type='hidden' id='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]+"' name='"+FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_LOCATION_ID]+"' value='"+defaultLocation+"'>";
                    }
                    
                htmlReturn +=""
                + "</div>"
            + "</div>"
        + "</div>"
        + "<div class='row'>"
            + "<div class='box-body'>"
                + "<div class='col-md-6'>"
                    + "<label class='control-label'>Start Date</label>"
                    + "<div class='input-group'>"
                        + "<input type='text' class='form-control datepicker' name='startdate' id='startdate' value='"+Formater.formatDate(new Date(),"yyyy-MM-dd")+"'>"
                        + "<div class='input-group-addon'>"
                            + "<i class='fa fa-calendar'></i> "
                        + "</div>"
                    + "</div>"
                + "</div>"
                + "<div class='col-md-6'>"
                     + "<label class='control-label'>End Date</label>"
                        + "<div class='input-group'>"
                        + "<input type='text' class='form-control datepicker' name='enddate' id='enddate' value='"+Formater.formatDate(new Date(),"yyyy-MM-dd")+"'>"
                        + "<div class='input-group-addon'>"
                            + "<i class='fa fa-calendar'></i> "
                        + "</div>"
                    + "</div>"
                + "</div>"
            + "</div>"
        + "</div>"
        
        + "<div class='row' style='display:none'>" 
            + "<div class='box-body'>" 
                + "<div class='col-md-12'>"
                    + "<div class=input-group>" 
                        + " <input placeholder='Select Reservation....' class=form-control type='text' name='reservationSearch' id='reservationSearch'>" 
                        + " <span class=input-group-btn>" 
                            + " <button id='searchReservation' class='btn btn-primary' type='button'>"
                                + "&nbsp;<i class='fa  fa-search'></i>&nbsp;"
                            + "</button>" 
                        + " </span>" 
                    + "</div>"                    
                + "</div>"
            + "</div>"
        + "</div>"
        
        + "<div class='row'>" 
            + "<div class='box-body'>" 
                + "<input type='hidden' name='schedulePackValue' id='schedulePackValue' value='1'>"
                + "<div class='col-md-12'>"                  
                    + "<div class='btn-group' role='group' aria-label='...'>" 
                        + " <button id='schedulePack' class='btn btn-default active selectSchedule' data-sch='1' type='button'>Schedule</button>" 
                        + " <button id='nonSchedulePack' type='button' class='btn btn-default selectSchedule' data-sch='0'>Non Schedule</button>" 
                    + "</div>"                                   
                + "</div>"
            + "</div>"
        + "</div>"
        + "<div class='row'>" 
            + "<div class='box-body'>" 
                + "<div class='col-md-12'>"                  
                    + "<button style='width:100%' id='loadPackageBill' class='btn btn-primary' type='button'>"
                        + "Load Package"
                    + "</button>"                                   
                + "</div>"
            + "</div>"
        + "</div>"
        + "<div class='row'>" 
            + "<div class='box-body'>" 
                + "<div class='col-md-12 table-responsive' id='packageBillListPlace'>"
                                     
                + "</div>"
            + "</div>"
        + "</div>";
                        
        
        return htmlReturn;
    }
    
    private String showReservationPackage(HttpServletRequest request){
        String htmlReturn = "";
        
        String typeText = FRMQueryString.requestString(request, "typeText");
        String where ="";
        
        if (typeText.length()>0){
            where = ""
            + "AND (m."+ PstContactList.fieldNames[PstContactList.FLD_PERSON_NAME]+" LIKE '%"+typeText+"%' "
            + "OR k."+ PstHotelRoom.fieldNames[PstHotelRoom.FLD_ROOM_NUMBER]+" LIKE '%"+typeText+"%' "
            + "OR ca."+ PstReservation.fieldNames[PstReservation.FLD_RESERVATION_NUM]+" LIKE '%"+typeText+"%' "
            + "OR n."+ PstContactList.fieldNames[PstContactList.FLD_COMP_NAME]+" LIKE '%"+typeText+"%')";
        }
        
        Vector listReservation = PstCustomBillMain.listInHouseGuest(0, 15, where, "");
        
        htmlReturn += ""
        + "<table class='table table-bordered table-striped'>"
            + "<thead>"
                + "<tr>"
                    + "<th>No</th>"
                    + "<th>Reservation No</th>"
                    + "<th>Guest</th>"
                    + "<th>Room No</th>"                   
                + "</tr>"
            + "</thead>"
            + "<tbody>";
                int no = 0;
                for (int i=0; i<listReservation.size();i++){
                    no = no +1;
                    Vector listData = (Vector) listReservation.get(i);
                    ContactList contactList = (ContactList) listData.get(0);
                    HotelRoom hotelRoom = (HotelRoom) listData.get(1);
                    Reservation reservation = (Reservation) listData.get(2);
                    Contact contact = (Contact) listData.get(3);
                    
                    htmlReturn+=""
                    + "<tr style='cursor:pointer' class='clickReservationPack' data-oidreservation='"+reservation.getOID()+"' data-reservationnum='"+reservation.getReservationNum()+"'>"
                        + "<td>"+no+"</td>"
                        + "<td>"+reservation.getReservationNum()+"</td>"
                        + "<td>"+contactList.getPersonName()+"</td>"
                        + "<td>"+hotelRoom.getRoomNumber()+"</td>"
                    + "</tr>";
                }              
        htmlReturn+="" 
            + "</tbody>"
        + "</table>";
        
        return htmlReturn; 
    }
    
    private String listPackageItem (HttpServletRequest request){
        String htmlReturn="";
        
        long oidLocation = FRMQueryString.requestLong(request, "location");
        long oidReservation = FRMQueryString.requestLong(request, "oidReservation");
        long oidBillMain = FRMQueryString.requestLong(request, "oidBillMain");
        int sch = FRMQueryString.requestInt(request, "sch");
        String startDate = FRMQueryString.requestString(request, "startdate");
        String endDate = FRMQueryString.requestString(request, "enddate");
        Vector listBillCustome = new Vector();
        
        String whereCustom ="";
        
        if (sch==1){
            whereCustom = ""
                + " cpb."+PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_RESERVATION_ID]+"='"+oidReservation+"'"
                + " AND (DATE(cpbs."+PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_START_DATE]+") BETWEEN '"+startDate+"' AND '"+endDate+"') "
                + " AND cpbs."+PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_STATUS]+"!='4'";           

        }else{
            whereCustom = ""
                + " cpb."+PstCustomePackBilling.fieldNames[PstCustomePackBilling.FLD_RESERVATION_ID]+"='"+oidReservation+"'"
                + " AND cpbs."+PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_START_DATE]+" IS NULL "
                + " AND cpbs."+PstCustomePackageSchedule.fieldNames[PstCustomePackageSchedule.FLD_STATUS]+"!='4'";              
        }
        
        if (oidLocation!=0){
            whereCustom += " AND lc."+PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]+"='"+oidLocation+"'";   
        }
        
        listBillCustome = PstCustomePackBilling.listJoinSchedule(0,0, whereCustom, "");
        
        htmlReturn += ""
        + "<table class='table table-bordered table-striped table-hover'>"
            + "<tbody>"
                + "<tr>"
                    + "<th>No</th>"
                    + "<th>Location</th>"
                    + "<th>Category</th>"
                    + "<th>Sub Category</th>"
                    + "<th>Item</th>"
                    + "<th>Qty</th>"
                    + "<th>Amount</th>"
                    + "<th>Sub Total</th>"
                    + "<th>Service</th>"
                    + "<th>Tax</th>"
                    + "<th>Total</th>"                       
                    + "<th>Type</th>"
                    + "<th>Used</th>"                       
                + "</tr>";
            if (listBillCustome.size()>0){
                int number=0;
                for (int i = 0; i < listBillCustome.size(); i++) {
                    Material material = new Material();
                    Merk subCategory = new Merk();
                    BillingItemGroup category = new BillingItemGroup();
                    Vector custome = (Vector) listBillCustome.get(i);
                    CustomePackBilling customePackBilling = (CustomePackBilling) custome.get(0);
                    
                    double discPct = customePackBilling.getDiscPct();
                    double discNominal = customePackBilling.getDisc();
                    double discNominal$ = customePackBilling.getDisc$();
                    if(discPct > 0){
                        discNominal = customePackBilling.getPriceRp()*(discPct/100);
                        discNominal$ = customePackBilling.getPriceUsd()*(discPct/100);
                    }else{
                        discPct = (discNominal/customePackBilling.getPriceRp())*100;
                    }
                    try {
                        material = (Material) custome.get(1);
                    } catch (Exception e) {
                    }
                    
                    Location location = (Location) custome.get(2);
                    try {
                        
                    } catch (Exception e) {
                    }
                    try {
                        category =  (BillingItemGroup) custome.get(3);
                    } catch (Exception e) {
                    }
                    
                    try {
                        subCategory = (Merk) custome.get(4);
                    } catch (Exception e) {
                    }
                    
                    CustomePackageSchedule customePackageSchedule= (CustomePackageSchedule) custome.get(5);  
                    
                    number=number+1;
                    
                    double qtyPrint = 0;
                    if (oidBillMain!=0){
                        String whereClause = ""
                            + " "+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+oidBillMain+"'"
                            + " AND "+PstBillDetail.fieldNames[PstBillDetail.FLD_CUSTOME_PACK_BILLING_ID]+"='"+customePackBilling.getOID()+"'"
                            + " AND "+PstBillDetail.fieldNames[PstBillDetail.FLD_MATERIAL_ID]+"='"+material.getOID()+"'";
                        
                        Vector listBillDetail = PstBillDetail.list(0, 0, whereClause, "");
                        if (listBillDetail.size()>0){
                            Billdetail entBillDetail = new Billdetail();
                            entBillDetail = (Billdetail) listBillDetail.get(0);
                            qtyPrint = customePackBilling.getQuantity() - entBillDetail.getQty();
                        }else{
                            qtyPrint = customePackBilling.getQuantity();
                        }
                    }else{
                        qtyPrint = customePackBilling.getQuantity();
                    }
                    
                        
                    if (customePackBilling.getRate()==1) { 
                        double subAmounts=customePackBilling.getPriceRp()*customePackBilling.getQuantity(); 
                        htmlReturn += ""
                    + "<tr style='cursor:pointer;' data-remain='"+qtyPrint+"' data-oid='"+customePackBilling.getOID()+"' data-amount='"+subAmounts+"' data-price='"+customePackBilling.getPriceRp()+"' data-oidlocation='"+oidLocation+"' data-oidreservation='"+oidReservation+"' class='consumeBill' data-oidmat='"+material.getOID()+"' data-matname='"+material.getName()+"' data-qty='"+qtyPrint+"' data-disc-pct='"+discPct+"' data-disc-rp='"+discNominal+"' data-disc-usd='"+discNominal$+"' data-schedule-id='"+customePackageSchedule.getOID()+"'>";
                     
                    }  else{
                        double subAmounts=customePackBilling.getPriceUsd()*customePackBilling.getQuantity();   
                        htmlReturn += ""
                    + "<tr style='cursor:pointer;' data-remain='"+qtyPrint+"' data-oid='"+customePackBilling.getOID()+"' data-amount='"+subAmounts+"' data-price='"+customePackBilling.getPriceUsd()+"' data-oidlocation='"+oidLocation+"' data-oidreservation='"+oidReservation+"' class='consumeBill' data-oidmat='"+material.getOID()+"' data-matname='"+material.getName()+"' data-disc-pct='"+discPct+"' data-disc-rp='"+discNominal+"' data-disc-usd='"+discNominal$+"' data-schedule-id='"+customePackageSchedule.getOID()+"'>";
                     
                    } 
                    
                    
                    if (qtyPrint>0){
                        htmlReturn += ""
                        + "<td>"+number+"</td>"
                        + "<td>"+location.getName()+"</td>"
                        + "<td>"+category.getGroupName()+"</td>"
                        + "<td>"+subCategory.getName()+"</td>"
                        + "<td>"+material.getName()+"</td>"
                        + "<td>"+qtyPrint+"</td>";

                        if (customePackBilling.getCurrencyType()==1) {                      
                            double subAmount=customePackBilling.getPriceRp()*customePackBilling.getQuantity();                                                
                            htmlReturn += "<td>"+Formater.formatNumber(customePackBilling.getPriceRp(), "#,###.#")+"</td>";
                            htmlReturn += "<td>"+Formater.formatNumber(subAmount, "#,###.#")+"</td>";
                            htmlReturn += "<td>"+Formater.formatNumber(customePackBilling.getServiceRp(), "#,###.#")+"</td>";
                            htmlReturn += "<td>"+Formater.formatNumber(customePackBilling.getTaxRp(), "#,###.#")+"</td>";
                            htmlReturn += "<td>"+Formater.formatNumber(customePackBilling.getTotalPriceRp(), "#,###.#")+"</td>";
                        }else{                                                      
                            htmlReturn +="<td>"+Formater.formatNumber(customePackBilling.getPriceUsd(), "#,###.##")+"</td>";
                            double subAmount=customePackBilling.getPriceUsd()*customePackBilling.getQuantity();                       
                            htmlReturn += "<td>"+Formater.formatNumber(subAmount, "#,###.#")+"</td>";                                                          
                            htmlReturn += "<td>"+Formater.formatNumber(customePackBilling.getServiceUsd(), "#,###.##")+"</td>";                        
                            htmlReturn += "<td>"+Formater.formatNumber(customePackBilling.getTaxUsd(), "#,###.##")+"</td>";                                                     
                            htmlReturn += "<td>"+Formater.formatNumber(customePackBilling.getTotalPriceUsd()+customePackBilling.getServiceUsd(), "#,###.##")+"</td>"; 
                        }
                        htmlReturn += ""                               
                            + "<td>"+PstCustomePackBilling.strType[customePackBilling.getType()]+"</td>"
                            + "<td>"+PstCustomePackBilling.strUseDefault[customePackBilling.getUseDefault()]+"</td>"
                        + "</tr>";
                    }
                    
                }
            }else{
                htmlReturn +=""
                + "<tr>"
                    + "<td colspan='13'><center>No Data</center></td>"
                + "</tr>";
            }
            htmlReturn += ""
                + "</tbody>"
            + "</table>"
            + "";
        
        return htmlReturn;
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
