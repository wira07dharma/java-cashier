$(document).ready(function(){
    $("form#login").submit(function(){
	var error = false;
	var username = $("#username").val();
	var password = $("#password").val();
	var approot = $("#approot").val();
	var loginok = $("#loginok").val();
	$("#result").hide();
	
	if(username.length == 0){
	    error = true;
	    $("#username_error").html("<b><i class='icon icon-warning-sign'></i> ERROR : </b>Please enter your username</b>").fadeIn("medium");
	    $("#username").focus();
	}else{
	    $("#username_error").fadeOut();
	}
	
	if(password.length == 0){
	    error = true;
	    $("#password_error").html("<b><i class='icon icon-warning-sign'></i> ERROR : <.b>Please enter your username</b>").fadeIn("medium");
	    $("#password").focus();
	}else{
	    $("#password_error").fadeOut();
	}
	
	if(error == false){
	    $("#btnLogin").html("Please wait...").attr({
		"disabled":true
	    });
	    $.ajax({
		type	: "POST",
		url	: approot+"/CashierLoginHandler",
		data	: $(this).serialize(),
		cache	: false,
		success	: function(data){
                         $("#result").removeClass("alert-error");
			if(parseInt(data)==loginok){
			    $("#result").html("<i class='icon icon-ok'></i> <b>LOGIN SUCCESS</b><br>Redirecting to Home Page").addClass("alert alert-success").fadeIn("medium");
			    window.location = approot+"/cashier/cashier-home.jsp";
			}else{
			    $("#result").html("<i class='icon icon-warning-sign'></i> <b>LOGIN FAILED</b><br>Invalid username or password").addClass("alert alert-error").fadeIn("medium");
			}
			$("#btnLogin").removeAttr("disabled").html("Sign In");
			    
		},
		error : function(){
			$("#result").html("<i class='icon icon-warning-sign'></i> <b>LOGIN FAILED</b><br>Couldn't login right now").addClass("alert alert-warning").fadeIn("medium");
		}
	    });
	}
	return false;
    });
});