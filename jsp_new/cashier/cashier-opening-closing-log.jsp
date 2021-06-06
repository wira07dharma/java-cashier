<%@page import="java.util.Calendar"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dimata.common.entity.system.PstSystemProperty"%>
<%@page import="com.dimata.posbo.entity.masterdata.Shift"%>
<%@page import="java.util.Date"%>
<%@page import="java.sql.Time"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="com.dimata.posbo.entity.masterdata.PstShift"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.common.entity.location.Location"%>
<%@page import="com.dimata.common.entity.location.PstLocation"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="com.dimata.pos.entity.balance.OpeningCashCashier"%>
<%@page import="com.dimata.pos.entity.balance.PstCashCashier"%>
<%@page import="com.dimata.pos.form.balance.FrmCashCashier"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="../main/javainit_cashier.jsp" %>
<%
    String multilocation = PstSystemProperty.getValueByName("OUTLET_MULTILOCATION");
    long defaultOidLocation = Long.parseLong(PstSystemProperty.getValueByName("OUTLET_DEFAULT_LOCATION"));
    
    //System Property untuk mengecek, apakah menggunakan log out otomatis atau tidak
    String logOutAuto = PstSystemProperty.getValueByName("LOGOUT_OUTOMATIC");
    
    //System Property untuk mengecek, iddle time yang digunakan
    String iddleTime = PstSystemProperty.getValueByName("LOGOUT_OUTOMATIC_IDDLE_LIMIT");
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Open Cashier - Dimata Cashier</title>
    <%@include  file="cashier-css.jsp"%>   
    <script type="text/javascript" src="../styles/cashier/dist/js/numberformat.js"></script>
    <style></style>
</head>
<body class="skin-queentandoor sidebar-mini wysihtml5-supported sidebar-collapse fixed">
    <div class="wrapper nonprint"> 
        <header class="main-header">
            <%@include file="cashier-header.jsp" %>
        </header>
    <aside class="main-sidebar">
        <%
            String home = "";
            String transaction = "";
            String log = "active";
            String maintenance = "";
        %>
        <%@include file="cashier-sidebar.jsp" %>
    </aside>
    <div class="content-wrapper nonprint">
        <section class="content-header">
            <h1>
                Dimata Cashier
                <small>Opening & Closing Reprint</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-home"></i> Home</a></li>
                <li class="active">Opening & Closing Reprint</li>
            </ol>
        </section>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Opening & Closing Reprint</h3>
                        </div>
                        <div class="box-body">
                            <div class="row">
                                <div class="col-md-9">
                                    <div class="input-group" style="margin-bottom: 2px;">
                                        
                                            
                                        <%
                                            if (multilocation.equals("1")){                                                    
                                                String whereClause = "";                                           
                                                Vector listLocation = PstLocation.listLocationStore(0, 0,""+whereClause, "");
                                                out.println("<select id='locationStore' class='form-control'>");
                                                out.println("<option value='0'>All Location</option>");
                                                if(listLocation.size() != 0){
                                                    for(int i=0; i<listLocation.size(); i++){
                                                        Location location = (Location) listLocation.get(i);
                                                        out.println("<option value='"+location.getOID()+"'>"+location.getName()+"</option>");

                                                    }
                                                }
                                                out.println("</select>");
                                                out.println("<div class='input-group-addon' style='border:none;'>&nbsp;</div>");
                                            }else {
                                                out.println("<input id='locationStore'  type='hidden' value='0'>");
                                            }
                                        %>

                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                        <input id="orSearch1" class="form-control datePicker" required="" type="text">
                                        <div class="input-group-addon" style="border:none;"><i class="fa fa-minus"></i></div>
                                        <div class="input-group-addon" style="border-right:none;"><i class="fa fa-calendar"></i></div>
                                        <input id="orSearch2" class="form-control datePicker" required="" type="text">
                                    </div>
                                </div>
                                <div class="col-md-3">
                                    <button id="search" type="button" class="btn btn-primary">
                                        <i class="fa fa-search"></i>
                                        Search
                                    </button>
                                </div>
                            </div>
                            <div class="row" style="margin-top:20px;">
                                <div class="col-md-12" id="dynamicContent">
                                    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>   
        </section>
    </div>
    <footer class="main-footer nonprint">
        <div class="pull-right hidden-xs">
            <b>Version</b> 1.0
        </div>
        <strong>Copyright &copy; 2015 <a href="#">Dimata IT Solution &REG;</a>.</strong> All rights reserved.
    </footer>  
    </div>
    <%@include file="cashier-jquery-bootstrap.jsp" %>
    
    <script type="text/javascript">       
        $(document).ready(function (){   
            var base = "<%= approot %>";
            function ajaxOpenCloseCashier(url,data,type,appendTo,another,optional,optional2){
                $.ajax({
                    url : ""+url+"",
                    data: ""+data+"",
                    type : ""+type+"",
                    async : false,
                    cache: false,
                    success : function(data) {                      
                        $(''+appendTo+'').html(data);
                        if (another==="printData"){
                            $("#CONTENT_PRINT").html(data);
                            window.print();
                            $("#openprintpreview").modal('show');
                        } else if (another==="email"){
							alert(data);
						}
                    },
                    error : function(data){
                        //alert('error');
                    }
                }).done(function(data){
                    if (another=="loadData"){
                        opening();
                        closing();
						email();
                    }
                    
                });
            }
            
            function loadData(){
                var url = ""+base+"/OpenCashierHandler";
                var date1 = $("#orSearch1").val();
                var date2 = $("#orSearch2").val();
                var location = $("#locationStore").val();
                var data = "command=<%=Command.NONE%>&loadtype=loadlistopenclose&date1="+date1+"&date2="+date2+"&location="+location+"";
                ajaxOpenCloseCashier(url,data,"POST","#dynamicContent","loadData","","");
            }
            
            function opening(){
                $('.opening').click(function(){
                    var url = ""+base+"/OpenCashierHandler";
                    //alert('test');
                    var oid = $(this).data('oid');
                    var oidcash = $(this).data('oidcash');
                    var type = 0;
                    var data = "command=<%=Command.NONE%>&loadtype=print&oid="+oid+"&oidcash="+oidcash+"&type="+type+"";
                    ajaxOpenCloseCashier(url,data,"POST","#CONTENT_PRINT_PREVIEW","printData","","");
                });
            }
			
            function closing(){
                $('.closing').click(function(){
                    var url = ""+base+"/OpenCashierHandler";
                    //alert('test');
                    var oid = $(this).data('oid');
                    var oidcash = $(this).data('oidcash');
                    var type = 1;
                    var data = "command=<%=Command.NONE%>&loadtype=print&oid="+oid+"&oidcash="+oidcash+"&type="+type+"";
                    ajaxOpenCloseCashier(url,data,"POST","#CONTENT_PRINT_PREVIEW","printData","","");
                });
            }
			
			function email(){
				$('.email').click(function(){
                    var url = ""+base+"/OpenCashierHandler";
                    //alert('test');
                    var oid = $(this).data('oid');
                    var oidcash = $(this).data('oidcash');
                    var type = 0;
                    var data = "command=<%=Command.NONE%>&loadtype=email&oid="+oid+"&oidcash="+oidcash+"&type="+type+"";
                     ajaxOpenCloseCashier(url,data,"POST",null,"email","","");
                });
			}
            
            $("#search").click(function(){
                loadData();
            });
            
            //
            loadData();
            //opening();
            //closing();
            
            $(".datePicker").datepicker({
                format: 'yyyy-mm-dd',

            }).on("changeDate", function (ev) {
                $(this).datepicker('hide');
            });
            $("#print").click(function(){
                window.print();
            });
            
            //----------AUTO LOG OUT FUNCTION
            
            var autoLog = "<%= logOutAuto%>";
            var autoLogTime = "<%= iddleTime%>";
            var timeLogOut = parseInt(autoLogTime)*1000;
            var autoFirstCounter = 30; 
            var intervallAutoLog;
            
            var iddleEvent = function(autoLogTime){
                //CHECK IDDLE OR NO
                var idleState = false;
                var idleTimer = null;
                var idleWait = autoLogTime;

                $('*').bind('mousemove keydown scroll', function () {       
                    clearTimeout(idleTimer);                                            
                    idleState = false;           
                    idleTimer = setTimeout(function () {                
                        // Idle Event               
                        $("#modalAutoLogOut").modal("show");
                        intervallAutoLog =  setInterval(function() {
                            checkCounter("#counterAuto");
                        },1000);
                    idleState = true; }, idleWait);
                });

                $("body").trigger("mousemove");
            };
            
            var checkCounter = function(elementChange){
                if (autoFirstCounter==1){
                    window.location = "<%= approot%>/cashier/cashier-logout.jsp";
                }else{
                    autoFirstCounter = autoFirstCounter-1;
                    $(elementChange).html(""+autoFirstCounter+"");
                }
                 
            };
            
            var logOutAutoButton = function(elementId){
                $(elementId).click(function(){
                    window.location = "<%= approot%>/cashier/cashier-logout.jsp";
                });
            };
            
            var keepLoginAuto = function(elementId){
                $(elementId).click(function(){
                    clearInterval(intervallAutoLog);
                    $("#modalAutoLogOut").modal("hide");
                    autoFirstCounter = 30;                  
                });
            };
             
            
            if (autoLog=="1"){
                iddleEvent(timeLogOut);
            }
            keepLoginAuto("#keepLoginAuto");
            logOutAutoButton("#logOutAuto");
            //----------AUTO LOG OUT FUNCTION

        });
    </script>
    <!-- PLUGINS COMPONEN -->
    <%@include file="cashier-plugin.jsp" %>
    <!---print modal-->
    <div id="openprintpreview" class="modal nonprint">
      <div class="modal-dialog">
	<div class="modal-content">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	    <h4 class="modal-title"><b>PRINT PREVIEW</b></h4>
	  </div>
	  <div class="modal-body">
	      <div  id="CONTENT_PRINT_PREVIEW">
		  
	      </div>
	      
	  </div>
	  <div class="modal-footer">
	      <button type="button" class="btn btn-primary" id="print">
		  <i class="fa fa-print"></i> Print
	      </button>
	  </div>
	</div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div>
    <div id="modalAutoLogOut" class="modal nonprint">
      <div class="modal-dialog">
	<div class="modal-content">
	  <div class="modal-header">
	    
	    <h4 class="modal-title"><i class="fa fa-exclamation-triangle"></i> We detected no activity </h4>
	  </div>
	  <div class="modal-body">
              You will be logged out automatically in <b><span id="counterAuto">30</span></b> second (s) <br>
              Are you still continuing ?
	  </div>
	  <div class="modal-footer">
            <button type="button" id="keepLoginAuto" class="btn btn-default">Yes, Keep Login</button>
	    <button type="button" id="logOutAuto" class="btn btn-danger">No, Log Out</button>
	  </div>
	</div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div>
  </body>
</html>
