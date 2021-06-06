/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.posbo.printing.warehouse;

import com.dimata.common.entity.payment.CurrencyType;
import com.dimata.posbo.entity.masterdata.Material;
import com.dimata.posbo.entity.masterdata.Unit;
import com.dimata.posbo.entity.warehouse.MatReceive;
import com.dimata.posbo.entity.warehouse.MatReceiveItem;
import com.dimata.posbo.entity.warehouse.PstMatReceive;
import com.dimata.posbo.entity.warehouse.PstMatReceiveItem;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Formater;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Generated by Together */
public class PrintBarcode extends HttpServlet {


    //public static String strURL = SessSystemProperty.PROP_APPURL;
    public void init(ServletConfig config) throws ServletException {

        super.init(config);

    }

    /** Destroys the servlet.

     */
    public void destroy() {
    }

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.

     * @param request servlet request

     * @param response servlet response

     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {
        
        long oidReceiveMaterial = FRMQueryString.requestLong(request, "hidden_receive_id");
        String whereClauseItem = PstMatReceiveItem.fieldNames[PstMatReceiveItem.FLD_RECEIVE_MATERIAL_ID] + "=" + oidReceiveMaterial;
        Vector listMatReceiveItem = PstMatReceiveItem.list(0, 0, whereClauseItem);
        int multiLanguageName = Integer.parseInt((String)com.dimata.system.entity.PstSystemProperty.getValueIntByName("NAME_MATERIAL_MULTI_LANGUAGE"));
        MatReceive matReceive = new MatReceive();
        String dateReceive = "";
        try{
            matReceive = PstMatReceive.fetchExc(oidReceiveMaterial);
            dateReceive = Formater.formatDate(matReceive.getReceiveDate(), "dd-MM-yyyy");
        }catch(Exception ex){
            
        }
        //String msgLoadString = "";
        response.setContentType("text/barcode");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            for (int i = 0; i < listMatReceiveItem.size(); i++) {
                Vector temp = (Vector) listMatReceiveItem.get(i);
                MatReceiveItem recItemx = (MatReceiveItem) temp.get(0);
                Material mat = (Material) temp.get(1);
                Unit unit = (Unit) temp.get(2);
                CurrencyType currencyType = (CurrencyType) temp.get(3);
                String[] smartPhonesSplits = mat.getName().split("\\;");
                String nameMat="";
                String kode="";
                kode=mat.getSku();
                if(multiLanguageName==1){
                    try{
                      String Str = new String(smartPhonesSplits[0]);  
                      try{
                            nameMat=Str.substring(0,8);    
                        }catch(Exception ex){
                            nameMat=Str.substring(0,4);   
                        }
                    }catch(Exception ex){
                        //rowx.add("");
                    }
                }else{
                    String Str = new String(smartPhonesSplits[0]);  
                    try{
                        nameMat=Str.substring(0,8);  
                    }catch(Exception ex){
                        nameMat=Str.substring(0,4);
                    }
                    
                }
                int qty = (int) recItemx.getQty();
                if(mat.getBarCode().length()>0){
                    for (int k = 0; k < qty; k++) {
                        out.println(mat.getBarCode()+";"+kode+";"+dateReceive);
                    }
                }
            }

        } catch (Exception exc) {
            System.out.println("" + exc);
        }

        out.flush();

    }

    /** Handles the HTTP <code>GET</code> method.

     * @param request servlet request

     * @param response servlet response

     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        processRequest(request, response);

    }

    /** Handles the HTTP <code>POST</code> method.

     * @param request servlet request

     * @param response servlet response

     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        processRequest(request, response);

    }

    /** Returns a short description of the servlet.

     */
    public String getServletInfo() {

        return "Short description";

    }


}

