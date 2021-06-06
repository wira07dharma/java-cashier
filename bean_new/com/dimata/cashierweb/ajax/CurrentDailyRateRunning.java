/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dimata.cashierweb.ajax;

import com.dimata.common.entity.payment.PstDailyRate;
import com.dimata.common.entity.payment.PstStandartRate;
import com.dimata.common.entity.payment.StandartRate;
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
public class CurrentDailyRateRunning extends HttpServlet{
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
            /*double qtyInput = FRMQueryString.requestLong(request,"FRM_FIELD_QTY_INPUT");*/
            //apakah cek standart rate apa daily rate
            int typeCheckCurrency = FRMQueryString.requestInt(request,"typeCheckCurrency");
            
            long oidCurrencyId = FRMQueryString.requestLong(request,"FRM_FIELD_RATE");
            //create fungsi cek Nilai Konversi

            double resultKonversi = 0.0;
            if(typeCheckCurrency==1){
                //resultKonversi = PstDailyRate.getCurrentDailyRateSales(oidCurrencyId);
                String whereClause = PstStandartRate.fieldNames[PstStandartRate.FLD_CURRENCY_TYPE_ID]+" = "+oidCurrencyId;
                    whereClause += " AND "+PstStandartRate.fieldNames[PstStandartRate.FLD_STATUS]+" = "+PstStandartRate.ACTIVE;
                    Vector listStandardRate = PstStandartRate.list(0, 1, whereClause, "");
                    StandartRate objStandartRate = (StandartRate)listStandardRate.get(0);
                    resultKonversi = objStandartRate.getSellingRate();
            }else{
                resultKonversi = PstDailyRate.getCurrentDailyRateSales(oidCurrencyId);
            }
            
            
            response.getWriter().println(resultKonversi);

         }catch(Exception ex){
            ex.printStackTrace();
         }
    }
}
