<%-- 
    Document   : login
    Created on : Aug 22, 2015, 8:54:47 AM
    Author     : dimata005
--%>
<%@page import="java.util.Locale"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.common.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.cashierweb.session.cashier.SessUserCashierSession"%>
<%@page import="com.dimata.cashierweb.session.cashier.SessAppUserCashier"%>

<%
    String verificationType = PstSystemProperty.getValueByName("KASIR_LOGIN_TYPE");
    if (verificationType.equals("1")){
        response.sendRedirect("login_finger.jsp");
    }
    
    Locale locale = Locale.getDefault();
    String alert = locale.getISO3Language();
            
%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>Login - Dimata Cashier</title>

        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
        <meta name="apple-mobile-web-app-capable" content="yes"> 

        <link href="../styles/cashier/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css" />

        <link href="../styles/cashier/css/font-awesome.css" rel="stylesheet">
        <link href="../styles/font-google.css" rel="stylesheet">
	<link href="../styles/cashier/img/logo.png" rel="shortcut icon" />
        <link href="../styles/cashier/css/style.css" rel="stylesheet" type="text/css">
        <link href="../styles/cashier/css/pages/signin.css" rel="stylesheet" type="text/css">
	<style type="text/css">
	    body{
		background-image: url("../styles/cashier/img/vector-22.jpg");
	    }
	    .account-container{
		box-shadow: 0px 0px 2px #693F33 , 0px -3px 0px #693F33  inset;
	    }
	    .alert{
		box-shadow: 0px 0px 2px #693F33;
	    }
	</style>
    </head>

    <body>

	
	<div style="margin-top:80px;"><center><img src="../styles/cashier/img/cropped-cropped-queens-logo11.png"></center></div>
        <div class="account-container" style="margin-top:10px;">
	    
            <div class="content clearfix">

                <form id="login">
		    <input type="hidden" name="loginok" value="<%= SessUserCashierSession.DO_LOGIN_OK %>" id="loginok">
		    <input type="hidden" name="approot" value="<%= request.getContextPath() %>" id="approot">
		    <input type="hidden" name="command" value="<%= 1 %>">
                    <center><h1>Sign In</h1></center>		

                    <div class="login-fields">

                        <p>Please provide your details</p>

                        <div class="field">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username" value="" placeholder="Username" class="login username-field" tabindex="1" />
			    <div class="alert alert-error" style="display:none;margin-top: 5px;" id="username_error"></div>
                        </div> <!-- /field -->

                        <div class="field">
                            <label for="password">Password:</label>
                            <input autocomplete="off" type="password" id="password" name="password" value="" placeholder="Password" class="login password-field" tabindex="2"/>
			    <div class="alert alert-error" style="display:none;margin-top: 5px;" id="password_error"></div>
                        </div> <!-- /password -->

                    </div> <!-- /login-fields -->

                    <div class="login-actions">

                        <button class="button btn btn-success btn-large" tabindex="2" id="btnLogin">Sign In</button>

                    </div> <!-- .actions -->



                </form>

            </div> <!-- /content -->

        </div> <!-- /account-container -->

	

        <div class="login-extra">
	    <div id="result" style="display:none;"></div>
	    <center>Copyright &copy; 2010 Dimata&REG; IT Solution<br>
	    Jl. Imam Bonjol, Perum Cipta Selaras no. 12, Denpasar 80119 - BALI.<br>
	    Telp. (0361) 499029, 7869752; Fax (0361) 499029<br>
	    Website : www.dimata.com<br>
	    Email : marketing@dimata.com<br></center>
        </div> <!-- /login-extra -->


        <script src="../styles/cashier/js/jquery-1.7.2.min.js"></script>
        <script src="../styles/cashier/js/bootstrap.js"></script>

        <script src="../styles/cashier/js/signin.js"></script>

    </body>

</html>
