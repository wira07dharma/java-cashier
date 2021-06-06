/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.posbo.ajax;

import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.posbo.entity.masterdata.PstUnit;
import com.dimata.qdep.form.FRMQueryString;
import java.util.Vector;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author dimata005
 */
public class CheckCustomer extends HttpServlet{
     /* Generated by Together */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /** Destroys the servlet.
     */
    public void destroy() {

    }

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

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try{
            //String buffer="false";

            /**
             * create fungsi check qty stock
             */
            
            String resultKonversi="";
            Vector listCustomer = PstContactList.listAll();
            
            for(int i =0 ; i<listCustomer.size(); i++){
                ContactList contactList= (ContactList) listCustomer.get(i);
                resultKonversi=resultKonversi+" "+contactList.getPersonName()+"<br>";
            }
            
            response.getWriter().println(resultKonversi);

         }catch(Exception ex){
            ex.printStackTrace();
         }
    }
}
