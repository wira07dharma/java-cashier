/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.ajax.cashier;

import com.dimata.cashierweb.entity.admin.AppUser;
import com.dimata.cashierweb.entity.admin.PstAppUser;
import com.dimata.cashierweb.entity.cashier.openingcashier.PstCustomCashCashier;
import com.dimata.cashierweb.entity.cashier.printtemplate.PrintTemplate;
import com.dimata.cashierweb.entity.cashier.transaction.BillMainCostum;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain;
import com.dimata.cashierweb.session.cashier.SessUserCashierSession;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.system.PstSystemProperty;
import com.dimata.common.session.email.SessEmail;
import com.dimata.gui.jsp.ControlCombo;
import com.dimata.pos.entity.balance.Balance;
import com.dimata.pos.entity.balance.CashCashier;
import com.dimata.pos.entity.balance.PstBalance;
import com.dimata.pos.entity.balance.PstCashCashier;
import com.dimata.pos.entity.masterCashier.CashMaster;
import com.dimata.pos.entity.masterCashier.PstCashMaster;
import com.dimata.pos.form.balance.CtrlCashCashier;
import com.dimata.pos.form.balance.FrmCashCashier;
import com.dimata.posbo.entity.masterdata.Company;
import com.dimata.posbo.entity.masterdata.PstCompany;
import com.dimata.qdep.form.FRMMessage;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.io.PrintWriter;
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
public class OpenCashierHandler extends HttpServlet {

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
	
	//GET PARAMETER
	int iCommand = FRMQueryString.requestCommand(request);
	long oidLocation = FRMQueryString.requestLong(request, FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_LOCATION]);
	long oidCashier = FRMQueryString.requestLong(request, FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CASH_CASHIER_ID]);
	long userId = FRMQueryString.requestLong(request, FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SPV_OID]);
	String loadtype = FRMQueryString.requestString(request, "loadtype");
	
	String applicantDeleteStatus = FRMQueryString.requestString(request, "applicantDelete");
	String spvUsername = FRMQueryString.requestString(request, "SUPERVISOR_USERNAME");
	String spvPassword = FRMQueryString.requestString(request, "SUPERVISOR_PASSWORD");
	
	
	long oidCashierId = 0;
	int iErrCode = FRMMessage.NONE;
	CtrlCashCashier ctrlCashCashier = new CtrlCashCashier(request);
	
	
	
	if(iCommand == Command.DELETE && iErrCode == FRMMessage.NONE) {
	    iCommand = Command.NONE;
	}
	
	//CHECK OPENING CASHIER
	int listOpeningCashier = PstCustomCashCashier.getCountPerCashOpening("CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID]+"='"+userId+"' "
		+ "AND CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID]+"='1'");
	
	
	String returnData = "";
	if(loadtype.equals("checkopencashier")){
	    returnData = String.valueOf(listOpeningCashier);
	}else if(loadtype.equals("checkcashiernumb")){
	    
	    Vector cashierNumber_key = new Vector(1,1);
	    Vector cashierNumber_value = new Vector(1,1);
	    cashierNumber_key.add("");
	    cashierNumber_value.add("-- Select --");

	    Vector listCashierNumber = PstCashMaster.list(0,0,PstCashMaster.fieldNames[PstCashMaster.FLD_LOCATION_ID]+"='"+oidLocation+"'","");


	    if(listCashierNumber.size() != 0){
		for(int i=0; i<listCashierNumber.size(); i++){
		    CashMaster cashMaster = (CashMaster) listCashierNumber.get(i);
                    String whereCashCashier = " "+PstCashCashier.fieldNames[PstCashCashier.FLD_CASHMASTER_ID]+"="+cashMaster.getOID()+" and "+PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID]+"=1";
                    Vector listCashCashier = PstCashCashier.list(0, 0, whereCashCashier, "");
                    if (listCashCashier.size()<=0){
                        cashierNumber_key.add(""+cashMaster.getOID());
                        cashierNumber_value.add(""+cashMaster.getCashierNumber());
                    }
		    
		}
	    }
								
	    returnData = ControlCombo.drawBootsratap(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CASH_MASTER_ID],null,"",cashierNumber_key,cashierNumber_value," required=\"required\"","form-control enterPress");
	}else if(loadtype.equals("opencashier")){
	    if(iCommand == Command.SAVE){
		long oidSupervisor = 0;
		int userType = 0;
		try{
		    AppUser oidSupervisorData = PstAppUser.getByLoginIDAndPassword(spvUsername, spvPassword);
		    oidSupervisor = oidSupervisorData.getOID();
		    userType = oidSupervisorData.getUserGroupNew();

		}catch(Exception ex){
		    ex.printStackTrace();
		}
                
                HttpSession session = request.getSession();
                SessUserCashierSession userCashier = (SessUserCashierSession) session.getValue(SessUserCashierSession.HTTP_SESSION_CASHIER);
                String loginId = "";
                boolean isLoggedIn = false;
                long userIds = 0;
                try{
                    if(userCashier==null){
                        userCashier= new SessUserCashierSession();
                    }else{
                        if(userCashier.isLoggedIn()==true){
                            isLoggedIn  = true;
                            AppUser appUser = userCashier.getAppUser();
                            loginId = appUser.getLoginId();
                            userIds = appUser.getOID();
                        }
                    }
                }catch (Exception exc){
                    System.out.println(" >>> Exception during check login");
                }
                


		if(oidSupervisor != 0 && userType == 1){
		    iErrCode = ctrlCashCashier.action(iCommand, oidCashier, request, oidSupervisor);
                    int type = 0;  
                    CashCashier cashCashier = ctrlCashCashier.getCashCashier();
                    long oidcash = cashCashier.getOID();
                    String whereClause =""
                        + "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID]+"='"+oidcash+"'";
           
                    Vector listCash = PstCashCashier.listCashOpening(0, 0,whereClause,"");
                    Vector listCashierData = new Vector(1,1);
                    if(listCash.size() != 0){
                        listCashierData = (Vector) listCash.get(0);
                    }
                    TransactionCashierHandler transactionCashierHandler = new TransactionCashierHandler();
                    String result = transactionCashierHandler.drawPrintClosing3(listCashierData,loginId,type);
		    returnData = "0*"+result+"";
		}else{
		    returnData = "1*0";
                    
            
            
		}

	    }
	}else if (loadtype.equals("loadlistopenclose")){
            String result ="";
            String whereClause = "";
            long location = 0;
            
            String date1= FRMQueryString.requestString(request, "date1");
            String date2= FRMQueryString.requestString(request, "date2");
            location = FRMQueryString.requestLong(request, "location");
            
            //CEK APAKAH MENDUKUNG MULTI LOCATION
            String multilocation = PstSystemProperty.getValueByName("OUTLET_MULTILOCATION");
            long defaultOidLocation = Long.parseLong(PstSystemProperty.getValueByName("OUTLET_DEFAULT_LOCATION"));
            
            if (date1!="" && date2==""){
                date2=date1;
            }else if (date1=="" && date2!=""){
                date1=date2;
            }
            
            whereClause += ""
                + " 1=1";
            
            if (date1!=""){
                whereClause += ""
                + " AND DATE(cc."+PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE]+")>='"+date1+"'"
                + " AND DATE(cc."+PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE]+")<='"+date2+"'"; 
            }
            
            if (multilocation.equals("0")){
                whereClause +=""
                    + " AND cm."+PstCashMaster.fieldNames[PstCashMaster.FLD_LOCATION_ID]+"="+defaultOidLocation+"";
            }else{
                if (location!=0){
                    whereClause +=""
                        + " AND cm."+PstCashMaster.fieldNames[PstCashMaster.FLD_LOCATION_ID]+"="+location+"";
                }
            }
            
            String order= ""+PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE]+" DESC"; 
            
            Vector listCashCashier = PstCashCashier.listDistinct(0, 20, whereClause, "");

            result += ""
                + "<table id='tableOpenClose' class='table table-bordered table-striped'>"
                    + "<thead>"
                        + "<th style='width:5%'>No</th>"
                        + "<th>Status</th>"
                        + "<th>Time</th>"
                        + "<th>User</th>"
                        + "<th>Supervisor</th>"                     
                        + "<th style='width:20%'>Action</th>"
                    + "</thead>"
                    + "<tbody>";
                    if (listCashCashier.size()>0){
                        for (int i = 0; i<listCashCashier.size();i++){
                            CashCashier cashCashier = (CashCashier)listCashCashier.get(i);
                            result += ""
                                + "<tr>"
                                    + "<td colspan='6'><b>"+cashCashier.getOpenDate()+"</b></td>"
                                + "</tr>";
                            String whereSubOpen = ""
                                + " cb."+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+" =1"
                                + " AND DATE(cc."+PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE]+")='"+cashCashier.getOpenDate()+"'";
                            Vector listSubOpen = PstBalance.listJoinCashCashiermaster(0, 0, whereSubOpen, "");
                            int k = 0;
                            for (int j = 0; j<listSubOpen.size();j++){
                                Vector temp = (Vector)listSubOpen.get(j);                               
                                Balance balance = (Balance)temp.get(0);
                                CashCashier cashCashier2 = (CashCashier)temp.get(1);
                                Location location1 = (Location)temp.get(2);
                                AppUser appUser = (AppUser)temp.get(3);
                                CashMaster cashMaster2 = (CashMaster)temp.get(4);
                                
                                String cashStatus ="";
                                String time="";
                                String spvName = "";
                                String addClass="";
                                if (balance.getBalanceType()==0){
                                    cashStatus = "OPENING";
                                    time= Formater.formatDate(cashCashier2.getOpenDate(), "kk:mm:ss");
                                    spvName = cashCashier2.getSpvName();
                                    addClass = "opening";
                                }else{
                                    cashStatus = "CLOSING";
                                    time= Formater.formatDate(cashCashier2.getCloseDate(), "kk:mm:ss");
                                    spvName = cashCashier2.getSpvCloseName();
                                    addClass = "closing";
                                }
                                
                                k+=1;
                                
                                result += ""
                                    + "<tr>"
                                    + "<td>"+k+"</td>"
                                    + "<td>"+cashStatus+"</td>"
                                    + "<td>"+time+"</td>"
                                    + "<td>"+appUser.getFullName()+"</td>"
                                    + "<td>"+spvName+"</td>"  
                                    + "<td>"
                                        + "<button data-oidcash='"+cashCashier2.getOID()+"' data-oid='"+balance.getOID()+"' type='button' class='btn btn-success "+addClass+"'>"
                                            + "<i class='fa fa-print'></i> Print"
                                        + "</button> "
										+ "<button data-oidcash='"+cashCashier2.getOID()+"' data-oid='"+balance.getOID()+"' type='button' class='btn btn-warning email'>"
                                            + "<i class='fa fa-envelope'></i> Resend Email"
                                        + "</button> "
                                    + "</td>"
                                    + "</tr>";
               
                            }
                        }
                        
                    }else{
                        result += ""
                            + "<tr>"
                                + "<td colspan='6'><center>No Data</center></td>"
                            + "</tr>";
                    }
            
            result +=""
                    + "</tbody>"
                + "</table>";
            
            returnData =result;
        }else if (loadtype.equals("print")){
            HttpSession session = request.getSession();
            SessUserCashierSession userCashier = (SessUserCashierSession) session.getValue(SessUserCashierSession.HTTP_SESSION_CASHIER);
            String loginId = "";
            boolean isLoggedIn = false;
            long userIds = 0;
            try{
                if(userCashier==null){
                    userCashier= new SessUserCashierSession();
                }else{
                    if(userCashier.isLoggedIn()==true){
                        isLoggedIn  = true;
                        AppUser appUser = userCashier.getAppUser();
                        loginId = appUser.getLoginId();
                        userIds = appUser.getOID();
                    }
                }
            }catch (Exception exc){
                System.out.println(" >>> Exception during check login");
            }

            
            int type = FRMQueryString.requestInt(request, "type");
            long oid = FRMQueryString.requestLong(request, "oid");
            long oidcash = FRMQueryString.requestLong(request, "oidcash");
            
            String whereClause =""
                + "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID]+"='"+oidcash+"'";
           
            Vector listCash = PstCashCashier.listCashOpening(0, 0,whereClause,"");
            Vector listCashierData = new Vector(1,1);
            if(listCash.size() != 0){
                listCashierData = (Vector) listCash.get(0);
            }
            TransactionCashierHandler transactionCashierHandler = new TransactionCashierHandler();
            String result = transactionCashierHandler.drawPrintClosing2(listCashierData,loginId,type);
            
            returnData = result;
           
        }else if(loadtype.equals("email")){
		
		long oidcash = FRMQueryString.requestLong(request, "oidcash");
		String whereClause =""
                + "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID]+"='"+oidcash+"'";
           
		Vector listCash = PstCashCashier.listCashOpening(0, 0,whereClause,"");
		Vector listCashierData = new Vector(1,1);
		if(listCash.size() != 0){
                listCashierData = (Vector) listCash.get(0);
            }
		CashCashier cashCashier;
		CashMaster cashMaster;
		Location location= new Location();
		Location locationGet;
		Company comp = new Company();

		if(listCashierData.size() != 0){
			cashCashier = (CashCashier) listCashierData.get(0);
			try{
			cashCashier = PstCashCashier.fetchExc(cashCashier.getOID());
			}catch(Exception ex){

			}
			cashMaster = (CashMaster) listCashierData.get(1);
			try{
			cashMaster = PstCashMaster.fetchExc(cashMaster.getOID());
			}catch(Exception ex){

			}
			locationGet= (Location) listCashierData.get(2);
			 
			try{
				location = PstLocation.fetchExc(locationGet.getOID());
				comp = PstCompany.fetchExc(location.getCompanyId());
			} catch (Exception exc){
				
			}

		}else{
			cashCashier = new CashCashier();
			cashMaster = new CashMaster();
			location = new Location();
			locationGet = new Location();
		}
		
		BillMainCostum billMainCostum;
		try{
			billMainCostum = PstCustomBillMain.fetchByCashierID(cashCashier.getOID());
		}catch(Exception ex){
			billMainCostum = new BillMainCostum();
		}
		
		HttpSession session = request.getSession();
		SessUserCashierSession userCashier = (SessUserCashierSession) session.getValue(SessUserCashierSession.HTTP_SESSION_CASHIER);
		String loginId = "";
		boolean isLoggedIn = false;
		long userIds = 0;
		try{
			if(userCashier==null){
				userCashier= new SessUserCashierSession();
			}else{
				if(userCashier.isLoggedIn()==true){
					isLoggedIn  = true;
					AppUser appUser = userCashier.getAppUser();
					loginId = appUser.getLoginId();
					userIds = appUser.getOID();
				}
			}
		}catch (Exception exc){
			System.out.println(" >>> Exception during check login");
		}
			
		PrintTemplate printTemplate = new PrintTemplate();
			String emailMsg = printTemplate.PrintTemplateGB(loginId, cashCashier, 
		billMainCostum, location, cashMaster);
		SessEmail sessEmail = new SessEmail();
		String resultEmail = sessEmail.sendEamil(comp.getEmailCompany(), "Daily Sales - "+location.getName()+" ("+Formater.formatDate(cashCashier.getCloseDate(), "yyyy-MMM-dd")+")", emailMsg, location.getAcountingEmail());
		returnData = resultEmail;
			
		}
	
	response.getWriter().println(returnData);
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
