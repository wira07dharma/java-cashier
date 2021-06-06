<%-- 
    Document   : cashier-logout
    Created on : Sep 28, 2015, 8:44:28 AM
    Author     : Ardiadi
--%>
<%@page import="com.dimata.cashierweb.session.cashier.SessUserCashierSession" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../main/javainit_cashier.jsp" %>
<!DOCTYPE html>
<%
try{
	if(userCashier.isLoggedIn()==true){
	    userCashier.printAppUser();
	    userCashier.doLogout(); 
	    session.removeValue(SessUserCashierSession.HTTP_SESSION_CASHIER);
	}
        if(userCashier!=null){
            session.removeValue(SessUserCashierSession.HTTP_SESSION_CASHIER);
        }
}catch (Exception exc){
  System.out.println(" ==> Exception during logout user");
}
%>
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
		width: 500px;
		background: none;
		border:none;
		box-shadow: none;
		color: #404040;
	    }
	</style>
    </head>

    <body>

	
	<div style="margin-top:80px;"><center><img src="../styles/cashier/img/cropped-cropped-queens-logo11.png"></center></div>
	
        <div style="margin-top:10px;" class="account-container">
            <div class="error-container">
		
		<center>
		    <h2>You are successfully logout</h2>

			<div class="error-details">
				Thank you for using our system.

			</div> <!-- /error-details -->

			<div class="error-actions">
				<a href="<%= approot %>/cashier/login.jsp" class="btn btn-large btn-primary">
					<i class="icon-chevron-left"></i>
					&nbsp;
					Back to login page						
				</a>



			</div> <!-- /error-actions -->	
		</center>

            </div> <!-- /content -->

        </div> <!-- /account-container -->

    </body>

</html>
