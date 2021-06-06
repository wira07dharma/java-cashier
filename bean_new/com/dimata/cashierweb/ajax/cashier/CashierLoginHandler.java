/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.ajax.cashier;

import com.dimata.cashierweb.entity.admin.AppObjInfo;
import com.dimata.cashierweb.entity.admin.AppUser;
import com.dimata.cashierweb.entity.admin.PstAppUser;
import com.dimata.cashierweb.form.admin.FrmAppUser;
import com.dimata.cashierweb.session.cashier.SessAppUserCashier;
import com.dimata.cashierweb.session.cashier.SessUserCashierSession;
import com.dimata.common.entity.system.PstSystemProperty;
import com.dimata.pos.entity.balance.OpeningCashCashier;
import com.dimata.pos.entity.balance.PstCashCashier;
import com.dimata.qdep.form.FRMQueryString;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Ardiadi
 */
public class CashierLoginHandler extends HttpServlet {

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
	
	final int CMD_LOGIN=1;
	final int MAX_SESSION_IDLE=100000;
	int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_LOGIN_DEFAULT_DISPLAY, AppObjInfo.OBJ_LOGIN_MOBILE); 
	boolean privMobileDisplay = false;
	int iCommand = Integer.parseInt((request.getParameter("command")==null) ? "0" : request.getParameter("command"));
	int typeView = FRMQueryString.requestInt(request, "typeView");
	String  noLog = FRMQueryString.requestString(request, "nodocument");
	int deviceUse = FRMQueryString.requestInt(request,"deviceuse");
	HttpSession session = request.getSession();
        int spvLogin = Integer.parseInt(PstSystemProperty.getValueByName("CASHIER_CAN_LOGIN_WITH_ID_SUPERVISOR"));

	int dologin = 0;
	int dologinSales = 0;
	int appLanguage  =  0;
	String salesCode="";
	boolean dayAssign=false;
	Date dateNow = new Date();
	int strDate = Integer.parseInt(String.valueOf(dateNow.getDate()));
	int strhourNow = Integer.parseInt(String.valueOf(dateNow.getHours()));
	int opie=0;

	// Then get the day of week from the Date based on specific locale.
	String nameDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dateNow);
	//System.out.println("xx XXXXXX"+nameDay);
	String numDay = FrmAppUser.getSerchValue(nameDay);
	int userGroup = -1;

	if(iCommand==CMD_LOGIN){
            String loginID = FRMQueryString.requestString(request,"username");
            String passwd  = FRMQueryString.requestString(request,"password");
            appLanguage  = FRMQueryString.requestInt(request,"app_language");
            AppUser salesUser = new AppUser();
            String remoteIP = request.getRemoteAddr();
            SessUserCashierSession  userSess = new SessUserCashierSession(remoteIP);

            if (spvLogin==0){
                dologinSales = userSess.doLogin(loginID, passwd);
                salesUser = SessAppUserCashier.getByLoginIDAndPassword(loginID, passwd); 
            }else{
                dologinSales = userSess.doLogin2(loginID, passwd);
                salesUser = SessAppUserCashier.getByLoginIDAndPasswordAllowSpv(loginID, passwd); 
                
                //Cek jika ada kasir yang open atau nggak
                if (dologinSales==SessUserCashierSession.DO_LOGIN_OK){
                    //MEMASTIKAN BENAR2 SUPERVISOR
                    if (salesUser.getUserGroupNew()==1){
                        OpeningCashCashier openingCashCashier = new OpeningCashCashier();
                        String whereCashCashier = " "+PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID]+"=1";
                        Vector listCashCashier =  PstCashCashier.listOpeningCashier(0, 0, whereCashCashier, "");

                        if (listCashCashier.size()>0){
                            openingCashCashier = (OpeningCashCashier)listCashCashier.get(0);
                            AppUser appUsers = new AppUser();
                            appUsers = PstAppUser.fetch(openingCashCashier.getAppUserId());
                            
                                dologinSales = userSess.doLogin(appUsers.getLoginId(), appUsers.getPassword());
                                salesUser = SessAppUserCashier.getByLoginIDAndPassword(appUsers.getLoginId(), appUsers.getPassword());

                        }
                    }
                }

            }

            //salesCode = salesUser.getCode();
            session.setMaxInactiveInterval(MAX_SESSION_IDLE);
            session.putValue(SessUserCashierSession.HTTP_SESSION_CASHIER, userSess);

	}


	/** cek integrasi */
	int INTEGRASI_POS = 0;
	int INTEGRASI_HANOMAN = 1;

	int pos_integ = 0;
	try {
		String designMat = String.valueOf(PstSystemProperty.getValueByName("DESIGN_MATERIAL_FOR"));
		pos_integ = Integer.parseInt(designMat);
	}
	catch (Exception e) {
		pos_integ = 0;
	}
	
	response.getWriter().println(dologinSales);
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
