<%-- 
    Document   : javainit
    Created on : Feb 23, 2014, 4:56:31 PM
    Author     : dimata005
--%>


<%@page import="com.dimata.common.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.cashierweb.entity.admin.AppUser"%>
<%@page import="com.dimata.cashierweb.session.cashier.SessUserCashierSession"%>
<%
// application path
String approot=request.getContextPath();
String printrootx=approot+"/servlet/com.dimata.dtaxintegration";

String test = "CLS-KTSEMINYAK 20170117: #cash:1.000.000.000 #creditcard:1.000.000.000 #bg:1.000.000.000 #cheque:1.000.000.000 #debitcard:1.000.000.000 #total:1.000.000.000.";

String useForRaditya = PstSystemProperty.getValueByName("USE_FOR_RADITYA");
int SETTING_DEFAULT = 0;
int SETTING_COLONIAS = 1;
int specsetting = SETTING_COLONIAS;

/** Variabel jenis Menu */
int MENU_DEFAULT = 0;
int MENU_PER_TRANS = 1;
int MENU_ICON = 2;

// user is logging in or not
boolean isLoggedIn = false;
int userGroupNewStatus = -1;
String userName = "";
long userId = 0;
String userlogin = "";

//add session 20120711 by mirahu 
session.setMaxInactiveInterval(60 * 60 * 2);
SessUserCashierSession userCashier = (SessUserCashierSession) session.getValue(SessUserCashierSession.HTTP_SESSION_CASHIER);
try{
    if(userCashier==null){
        userCashier= new SessUserCashierSession();
	response.sendRedirect(approot);
    }else{
        if(userCashier.isLoggedIn()==true){
            isLoggedIn  = true;
            AppUser appUser = userCashier.getAppUser();
            userGroupNewStatus = appUser.getUserGroupNew();
            userId = appUser.getOID();
            userCashier.doLogin(appUser.getLoginId(), appUser.getPassword());
	    userName = appUser.getFullName();
            userlogin = appUser.getLoginId();
        }
    }
}catch (Exception exc){
}
/**
 * set language
 */
String strLanguage = "";
if (session.getValue("APPLICATION_LANGUAGE") != null) {
    strLanguage = String.valueOf(session.getValue("APPLICATION_LANGUAGE"));
}
String useForGB = PstSystemProperty.getValueByName("USE_FOR_GREENBOWL");
int langDefault = com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT;
int langForeign = com.dimata.util.lang.I_Language.LANGUAGE_FOREIGN;
int SESS_LANGUAGE = (strLanguage!=null && strLanguage.length() > 0) ? Integer.parseInt(strLanguage) : 0;
//base url 
String urlC = request.getRequestURL().toString();
String baseURL = urlC.substring(0, urlC.length() - request.getRequestURI().length()) + request.getContextPath() + "/";

%>
<script type="text/javascript">
    console.log("<%= test.length() %>");
</script>