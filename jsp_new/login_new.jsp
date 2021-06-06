<%@page import="com.dimata.cashierweb.entity.admin.PstAppUser"%>
<%@page import="com.dimata.cashierweb.entity.admin.AppObjInfo"%>
<!DOCTYPE html>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.cashierweb.form.admin.FrmAppUser"%>
<%@page import="java.text.DateFormatSymbols"%>
<%@page import="com.dimata.common.entity.custom.PstDataCustom"%>
<%@ page language="java" %>
<%@ include file="main/javainit.jsp"%>
<%@ page import="com.dimata.printman.RemotePrintMan,
                 com.dimata.qdep.form.FRMQueryString,
                 com.dimata.cashierweb.session.admin.SessUserSession,
                 java.util.Vector,
                 com.dimata.gui.jsp.ControlCombo,
                 com.dimata.cashierweb.entity.masterdata.*" %>
<%!
 //final static int CMD_NONE =0;
 final static int CMD_LOGIN=1;
 final static int MAX_SESSION_IDLE=100000;
%>

<%
int  appObjCode = AppObjInfo.composeObjCode(AppObjInfo.G1_LOGIN, AppObjInfo.G2_LOGIN_DEFAULT_DISPLAY, AppObjInfo.OBJ_LOGIN_MOBILE); 
boolean privMobileDisplay = false;
int iCommand = Integer.parseInt((request.getParameter("command")==null) ? "0" : request.getParameter("command"));
int typeView = FRMQueryString.requestInt(request, "typeView");
String  noLog = FRMQueryString.requestString(request, "nodocument");
int deviceUse = FRMQueryString.requestInt(request,"deviceuse");

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
	String loginID = FRMQueryString.requestString(request,"login_id");
	String passwd  = FRMQueryString.requestString(request,"pass_wd");
        appLanguage  = FRMQueryString.requestInt(request,"app_language");
       
	String remoteIP = request.getRemoteAddr();
	com.dimata.cashierweb.session.admin.SessUserSession  userSess = new com.dimata.cashierweb.session.admin.SessUserSession(remoteIP );
        dologinSales = userSess.doLoginSales(loginID, passwd);
	AppUser salesUser = PstAppUser.getByLoginIDAndPassword(loginID, passwd); 
        salesCode = salesUser.getFullName();
        session.setMaxInactiveInterval(MAX_SESSION_IDLE);
        session.putValue(SessUserSession.HTTP_SESSION_SALES, salesUser);
        
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
%>
<html class="bg-black">
    <head>
        <meta charset="UTF-8">
        <title>Admin | Log in</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <!-- bootstrap 3.0.2 -->
        <link href="styles/bootstrap3.1/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <!-- font Awesome -->
        <link href="styles/bootstrap3.1/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <!-- Theme style -->
        <link href="styles/bootstrap3.1/css/AdminLTE.css" rel="stylesheet" type="text/css" />

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
        
        <script language="JavaScript">
            function cmdLogin(){
              document.frmLogin.action = "login.jsp";
              document.frmLogin.submit();
            }

            function fnTrapUserName(){
               if(event.keyCode == 13){
                            document.frmLogin.pass_wd.focus();
               }
            }

            function fnTrapPasswd(){
               if(event.keyCode == 13){
                            document.all.aLogin.focus();
                            cmdLogin();
               }
            }

            function keybrdPress(frmObj, event) {
                    if(event.keyCode == 13) {
                            switch(frmObj.name) {
                                    case 'login_id':
                                            document.all.pass_wd.focus();
                                            break;
                                    case 'pass_wd':
                                            document.all.aLogin.focus();
                                            cmdLogin();
                                            break;
                                    case 'app_language':
                                            document.all.aLogin.focus();
                                            cmdLogin();
                                            break;
                                    default:
                                            break;
                            }
                    }
            }

            
            function MM_swapImgRestore() { //v3.0
              var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
            }

            function MM_preloadImages() { //v3.0
              var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
              var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
              if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
            }

            function MM_findObj(n, d) { //v4.0
              var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
                d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
              if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
              for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
              if(!x && document.getElementById) x=document.getElementById(n); return x;
            }

            function MM_swapImage() { //v3.0
              var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
              if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
            }
            //-->
      </script>
        
    </head>
    <body class="bg-olive-">

        <div class="form-box" id="login-box">
            <div class="header"><img src="imgcompany/dimata_system_logo.png" border="none" width="250"></div>
            <form name="frmLogin" action="">
                <input type="hidden" name="command" value="<%=CMD_LOGIN%>">
                <input type="hidden" name="typeView" value="<%=typeView%>">
                <input type="hidden" name="nodocument" value="<%=noLog%>">
                <%
                     if((iCommand==CMD_LOGIN)&&(dologinSales == SessUserSession.DO_LOGIN_OK)){
                         if(typeOfBusiness.equals("3")){
                             response.sendRedirect("cashierweb/srcsalesordecashier.jsp?FRM_FIELD_SALES_CODE="+salesCode+"");
                         }else{
                             response.sendRedirect("outletonline/mainmenumobile.jsp?FRM_FIELD_SALES_CODE="+salesCode+"");
                         }
                     }
                %>
                <div class="body bg-gray">
                    <div class="form-group">
                        <input type="text" name="login_id" class="form-control" placeholder="User ID"/>
                    </div>
                    <div class="form-group">
                        <input type="password" name="pass_wd" class="form-control" placeholder="Password"/>
                    </div>
                </div>
                <div class="footer">                                                               
                    <button type="submit" class="btn bg-olive btn-block">Login</button>  
                      <%if( (iCommand==CMD_LOGIN) && (dologin != SessUserSession.DO_LOGIN_OK)||(iCommand==CMD_LOGIN) && (dologinSales != SessUserSession.DO_LOGIN_OK)) {%>
                      <font class="errfont" color="#FF0000"><%if(appLanguage==com.dimata.util.lang.I_Language.LANGUAGE_DEFAULT){%>nama atau password salah...<%}else{%>
                      username or password wrong, try again...
                      <%}%></font>
                      <%}%>
                </div>
            </form>
            <div class="margin text-center">
                <!--<span>
                    Copyright &copy; 2010 Dimata&reg; IT Solution<br>
                    Jl. Imam Bonjol, Perum Cipta Selaras no. 12, Denpasar 80119 - BALI.<br>
                    Telp. (0361) 499029, 7869752; Fax (0361) 499029<br>
                    Website : <a href="http://www.dimata.com" class="footerLink">www.dimata.com</a><br>
                    Email : <a href="mailto:marketing@dimata.com" class="footerLink">marketing@dimata.com</a>
                </span>-->
                <span>
                    Copyright &copy; 2010 Dimata&reg; IT Solution <br>
                    Version 1.0 ( Build : 2016.11.24 )<br>
                    Jl. Imam Bonjol, Perum Cipta Selaras no. 12, Denpasar 80119 - BALI.<br>
                    Telp. (0361) 499029, 7869752; Fax (0361) 499029<br>
                    Hotline Support. 081237710011 <br>
                    Website : <a href="http://www.dimata.com" class="footerLink">www.dimata.com</a><br>
                    Email : <a href="mailto:marketing@dimata.com" class="footerLink">marketing@dimata.com</a>
                </span>
            </div>
        </div>
        <!-- jQuery 2.0.2 -->
        <script src="styles/jquery.min.js"></script>
        <!-- Bootstrap -->
        <script src="styles/bootstrap3.1/js/bootstrap.min.js" type="text/javascript"></script>        
    </body>
</html>