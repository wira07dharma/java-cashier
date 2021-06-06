

<%@page import="com.dimata.common.entity.payment.PstCurrencyType"%>
<%@page import="com.dimata.common.entity.payment.CurrencyType"%>
<%@page import="com.dimata.hanoman.entity.masterdata.StandardRate"%>
<%@page import="com.dimata.hanoman.entity.masterdata.PstStandardRate"%>
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
<!DOCTYPE html> 
<%@include file="../main/javainit_cashier.jsp" %>
<%  
    //ini adalah fungsi pengecekan apakah menggunakan approval finger atau tidak
    String verificationType = PstSystemProperty.getValueByName("KASIR_LOGIN_TYPE");
    String cssDisplay = "";
    if (verificationType.equals("1")){
        cssDisplay = "display:none;";
    }else{
        cssDisplay = "display:block;";
    } 
    
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
    <!-- CSS FOR FINGER PRINT -->
    <style>
        .finger{
            width:20%; 
            height:auto;
            padding : 2%;
            float:left;
         }
        .finger_spot{
            width:90%;
            height: 80px;
            background-color :#e5e5e5;
            border : thin solid #c5c5c5;
            font-size: 0.8em;
            font-family:calibri;
            text-align:center;
            color :#FFF;
            border-radius: 3px;
        }


        .green{
           background-color : #5CB85C;
           border : thin solid #4CAE4C;
        }
    </style>
    <script type="text/javascript" src="../styles/cashier/dist/js/numberformat.js"></script>
  </head>
  <body class="skin-queentandoor sidebar-mini wysihtml5-supported sidebar-collapse fixed">
    <div class="wrapper nonprint">
      
      <header class="main-header">
	  <%@include file="cashier-header.jsp" %>
      </header>
      <!-- Left side column. contains the logo and sidebar -->
      <aside class="main-sidebar">
	    <%
		String home = "active";
		String transaction = "";
                String log = "";
                String maintenance = "";
	    %>
	    <%@include file="cashier-sidebar.jsp" %>
      </aside>

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Dimata Cashier
            <small>Open Cashier</small>
          </h1>
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i> Home</a></li>
            <li class="active">Open Cashier</li>
          </ol>
        </section>

        <!-- Main content -->
        <section class="content">
	    <div class="row">
		<div class="col-md-12">
		    <div class="box box-primary">
			<div class="box-header with-border">
			    <h3 class="box-title">Open Cashier</h3>
			</div>
			<div class="box-body">
			    <div id="CONTENT_<%= FrmCashCashier.FRM_NAME_CASH_CASHIER %>" style="display:none;">
				<form  id="<%= FrmCashCashier.FRM_NAME_CASH_CASHIER %>">
				    <input type="hidden" name="<%= FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SPV_CLOSE_OID] %>" value="1">
				    <input type="hidden" name="command" id="command" value="<%= Command.SAVE %>">
				    <input type="hidden" name="loadtype" value="opencashier">
                                    <div class="col-md-6">
                                        <div class="row">
                                            <div class="col-md-3">Cashier Name</div>
                                            <div class="col-md-1">:</div>
                                            <div class="col-md-8">
                                                <input type="hidden" name="<%= FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_APP_USER_ID] %>" value="<%= userId %>">
                                                
                                                <div class="form-control"><%= userName %></div>
                                                <br>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3">Location</div>
                                            <div class="col-md-1">:</div>
                                            <div class="col-md-8">
                                                <%
                                                    String defaultOidLocation = PstSystemProperty.getValueByName("OUTLET_DEFAULT_LOCATION");
                                                    String multiLocation = PstSystemProperty.getValueByName("OUTLET_MULTILOCATION");

                                                    Vector location_key = new Vector(1,1);
                                                    Vector location_value = new Vector(1,1);
                                                    location_key.add("");
                                                    location_value.add("-- Select --");
                                                    
                                                    //Jika User Group == SUPERVISOR
                                                    if (userGroupNewStatus!=1){
                                                    
                                                        String whereClause = "";
                                                        if(multiLocation.equals("0")){
                                                            whereClause = "lc."+PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]+"='"+defaultOidLocation+"'";
                                                        }

                                                        Vector listLocation = PstLocation.listLocationStore(0, 0,""+whereClause, "");

                                                        if(listLocation.size() != 0){
                                                            for(int i=0; i<listLocation.size(); i++){
                                                                Location location = (Location) listLocation.get(i);

                                                                location_key.add(""+location.getOID());
                                                                location_value.add(""+location.getName());
                                                            }
                                                        }
                                                    }
                                                %>
                                                <%=ControlCombo.drawBootsratap(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_LOCATION],null,String.valueOf(0),location_key,location_value," required='required'","form-control enterPress")%>
                                                
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3">Shift</div>
                                            <div class="col-md-1">:</div>
                                            <div class="col-md-8">
                                                <%
                                                    Vector shift_key = new Vector(1,1);
                                                    Vector shift_value = new Vector(1,1);

                                                    Vector listShift = PstShift.list(0, 0, "", PstShift.fieldNames[PstShift.FLD_START_TIME]+" DESC");

                                                    //String newDateFormat = Formater.formatDate(new Date(), "HH:mm:ss");
                                                    //Time getTime = Time.valueOf(newDateFormat);
                                                    long shiftIdTmp = 0;
                                                    long shiftIdTmpElse = 0;
                                                    long shiftId  = 0;
                                                    String timeNow = Formater.formatDate(new Date(), "HH:mm:ss");
                                                    Time timeshiftNow = Time.valueOf(timeNow);
                                                    if(listShift.size() != 0){
                                                        for(int i=0; i<listShift.size(); i++){
                                                            Shift shift = (Shift) listShift.get(i);

                                                            /*String formatedStart = Formater.formatDate(shift.getStartTime(), "HH:mm:ss");
                                                            String formatedEnd = Formater.formatDate(shift.getEndTime(), "HH:mm:ss");
                                                            String displayStart = Formater.formatDate(shift.getStartTime(), "kk:mm:ss");
                                                            String displayEnd = Formater.formatDate(shift.getEndTime(), "kk:mm:ss");
                                                            if((getTime.after(Time.valueOf(formatedStart)) || getTime.equals(Time.valueOf(formatedStart))) && (getTime.before(Time.valueOf(formatedEnd)) || getTime.equals(Time.valueOf(formatedEnd)))){


                                                                shift_key.add(""+shift.getOID());
                                                                shift_value.add(""+shift.getRemark()+" ["+displayStart+" - "+displayEnd+"]");
                                                            }*/
                                                            String time = Formater.formatDate(shift.getStartTime(), "HH:mm:ss");
                                                            Time timeShift = Time.valueOf(time);
                                                            if (timeshiftNow.after(timeShift) || timeshiftNow.equals(timeShift)) {
                                                                shiftIdTmp = shift.getOID();
                                                                i = listShift.size();
                                                            } else {
                                                                shiftIdTmpElse = shift.getOID();
                                                            }
                                                        }
                                                    }
                                                    if (shiftIdTmp != 0) {
                                                        shiftId = shiftIdTmp;
                                                    } else {
                                                        shiftId = shiftIdTmpElse;
                                                    }
                                                    String where = PstShift.fieldNames[PstShift.FLD_SHIFT_ID]+" = '"+shiftId+"'";
                                                    listShift = PstShift.list(0, 0, where, PstShift.fieldNames[PstShift.FLD_END_TIME] + " DESC ");
                                                    if(listShift!=null && listShift.size()>0){

                                                        for(int b=0;b<listShift.size();b++){

                                                            Shift shift = (Shift) listShift.get(b);
                                                            shift_key.add(""+shift.getOID());
                                                            String startTime = Formater.formatDate(shift.getStartTime(),"kk:mm:ss");
                                                            String endTime = Formater.formatDate(shift.getEndTime(),"kk:mm:ss");
                                                            shift_value.add(""+shift.getRemark()+" ["+startTime+" - "+endTime+"]");
                                                        }

                                                    }
                                                %>
                                                <%=ControlCombo.drawBootsratap(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SHIFT_ID],null,"",shift_key,shift_value," required='required'","form-control enterPress")%>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-3">Cashier Number</div>
                                            <div class="col-md-1">:</div>
                                            <div class="col-md-8" id="LOAD_<%= FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CASH_MASTER_ID] %>">
                                                <div class="form-control">Please select location</div>
                                                <br>
                                            </div>
                                        </div>
                                        
                                    </div>
                                    <div class="col-md-6">
                                        <%
                                            //GET STANDART RATE,
                                            String whereStandardRate = ""
                                                + " "+PstStandardRate.fieldNames[PstStandardRate.FLD_RATE]+"='1' AND "
                                                + " "+PstStandardRate.fieldNames[PstStandardRate.FLD_STATUS]+"='1'";
                                            Vector listStandardRate = PstStandardRate.list(0, 1, whereStandardRate, "");

                                            //GET SECOND RATE 
                                            String whereSecondRate = ""
                                                + " "+PstStandardRate.fieldNames[PstStandardRate.FLD_RATE]+"<>'1' AND"
                                                + " "+PstStandardRate.fieldNames[PstStandardRate.FLD_STATUS]+"='1'";
                                            Vector listSecondRate = PstStandardRate.list(0, 0, whereSecondRate, "");
                                            
                                            StandardRate entStandardRate = new StandardRate();
                                            try{
                                                entStandardRate = (StandardRate)listStandardRate.get(0);
                                            }catch(Exception ex){
                                                
                                            }
                                            CurrencyType entCurrencyType = new CurrencyType();
                                            try{
                                                entCurrencyType = PstCurrencyType.fetchExc(entStandardRate.getCurrencyTypeId());
                                            }catch(Exception ex){
                                                
                                            }
                                        %>
                                        
                                        <div class="row">
                                            <div class="col-md-3">Opening</div>
                                            <div class="col-md-1">:</div>
                                           <div class="col-md-8">
                                                <div class="input-group">
                                                    <span class="input-group-addon" id="basic-addon1"><%= entCurrencyType.getCode() %></span>
                                                    <input data-index="1" type="text" id ="<%= FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SUBTOTAL1] %>_1" name="<%= FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SUBTOTAL1] %>_1" class="form-control money enterPress subOpening"  required="required">
                                                    <input type="hidden" name="<%= FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CURRENCY_ID] %>_1" value="<%= entStandardRate.getCurrencyTypeId() %>">                                           
                                                </div>
                                           </div>                                         
                                        </div>
                                        <%
                                            int counts = 1;
                                            for (int i = 0; i<listSecondRate.size(); i++){
                                                StandardRate entStandardRates = new StandardRate();
                                                entStandardRates = (StandardRate)listSecondRate.get(i);
                                                counts += 1;
                                                CurrencyType entCurrencyTypes = new CurrencyType();
                                                try {
                                                    entCurrencyTypes = PstCurrencyType.fetchExc(entStandardRates.getCurrencyTypeId());
                                                }catch(Exception ex){
                                                
                                                }
                                                String extraClass="";
                                                if (i == (listSecondRate.size()-1)){
                                                    extraClass = "lastOpening";
                                                }
                                                out.println("<div class='row' style='margin-top:5px;'>");
                                                    out.println("<div class='col-md-3'>&nbsp;</div>");
                                                    out.println("<div class='col-md-1'>&nbsp;</div>");
                                                    out.println("<div class='col-md-8'>");
                                                        out.println("<div class='input-group'>");
                                                            out.println("<span class='input-group-addon' id='basic-addon1'>"+entCurrencyTypes.getCode()+"</span>");
                                                            out.println("<input type='text' id ='"+ FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SUBTOTAL1]+"_"+counts+"' data-index='"+counts+"' name='"+FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SUBTOTAL1]+"_"+counts+"' class='form-control money enterPress "+extraClass+" subOpening'  required='required'>");                                               
                                                            out.println("<input type='hidden' name='"+FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CURRENCY_ID]+"_"+counts+"' value='"+ entStandardRates.getCurrencyTypeId()+"'>");
                                                        out.println("</div>");
                                                    out.println("</div>");
                                                out.println("</div>");
                                                
                                            }
                                            
                                            out.println("<input type='hidden' name='FRM_FIELD_CURRENCY_COUNT' value='"+counts+"'>");
                                            out.println("<br>");
                                        %>
                                        <div class="row" style="<%=cssDisplay%>">
                                            <div class="col-md-3">Supervisor ID</div>
                                            <div class="col-md-1">:</div>
                                            <div class="col-md-8">
                                                <input type="text" id="SUPERVISOR_USERNAME" name="SUPERVISOR_USERNAME" class="form-control enterPress" required="required">
                                                <br>
                                            </div>
                                        </div>
                                        <div class="row" style="<%=cssDisplay%>">
                                            <div class="col-md-3">Supervisor Password</div>
                                            <div class="col-md-1">:</div>
                                            <div class="col-md-8">
                                                <input type="password" id="SUPERVISOR_PASSWORD" name="SUPERVISOR_PASSWORD" class="form-control enterPress" required="required">
                                                <br>
                                            </div>
                                        </div>
                                        <div class="row" style="<%=cssDisplay%>">
                                            <div class="col-md-3">&nbsp;</div>
                                            <div class="col-md-1">&nbsp;</div>
                                            <div class="col-md-8">
                                                <input type="submit" value="Open Cashier" class="btn btn-primary" id="btnOpen" style='width:100%'>
                                            </div>
                                        </div>
                                    
				</form>
                                    <%
                                    if (verificationType.equals("1")){
                                    %>
                                    <div class="row">
                                        <div class="col-md-3">Supervisor ID</div>
                                        <div class="col-md-1">:</div>
                                        <div class="col-md-8">
                                            <input type="text" id="spvFinger" required="required" class="form-control" placeholder="Supervisor Username" name="">
                                        </div>
                                       
                                    </div>
                                    <div class="row">   
                                        <div class="col-md-3">&nbsp;</div>
                                        <div class="col-md-1 ">&nbsp;</div>
                                        <div class="col-md-8">
                                            <div id="dynamicPlace"></div>
                                        </div>   
                                    </div>
                                    <%}%>
                                    
                                <br></br>
			    </div>
			    <div id="CONTENT_LOAD_TEXT">
				<h3 class="box-title">
				    <!--Checking open cashier-->
				</h3>
			    </div>
			</div>
			<div class="overlay" id="CONTENT_ANIMATE_CHECK">
			    <i class="fa fa-refresh fa-spin"></i>
			</div>
                            &nbsp; &nbsp; &nbsp;
		    </div>
		</div>
	    </div>
            </div>
            
            <!--OPENED CASHIR -->
            <div class="row">
                <div class="col-md-12">
                    <ul class="timeline">
                        <li class="time-label">
                            <span class="bg-red">
                              Cashier Log
                            </span>
                        </li>
                        <%
                            String whereCashOpening =" cc."+PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID]+"=0"; 
                            Vector listCashOpening = PstCashCashier.listOpeningCashier(0, 0, whereCashOpening, "");
                            OpeningCashCashier openingCashCashier = new OpeningCashCashier();
                            if (listCashOpening.size()<=0){
                                String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime());
                                out.println("<li>");
                                out.println("<i class='fa fa-envelope bg-blue'></i>");
                                out.println("<div class='timeline-item'>");
                                out.println("<span class='time'><i class='fa fa-clock-o'></i> "+timeStamp+"</span>");
                                out.println("<h3 class='timeline-header'>System</h3>");
                                out.println("<div class='timeline-body'>");
                                out.println("<i class='fa fa-info-circle'></i> No cashier open...");
                                out.println("</div>");
                                out.println("</div>");
                                out.println("</li>");
                            }else{
                               for (int i = 0; i<listCashOpening.size();i++){
                                   
                                    openingCashCashier = (OpeningCashCashier) listCashOpening.get(i);
                                    String newTime = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(openingCashCashier.getOpenDate());
                                    out.println("<li>");
                                    out.println("<i class='fa fa-user bg-aqua'></i>");
                                    out.println("<div class='timeline-item'>");
                                    out.println("<span class='time'><i class='fa fa-clock-o'></i> "+newTime+"</span>");
                                    out.println("<h3 class='timeline-header'><a style='cursor:pointer'>"+openingCashCashier.getNameUser()+"</a> logged in at "+openingCashCashier.getLocation()+"</h3>");
                                    out.println("<div class='timeline-body'>");
                                    out.println("Supervised by <b>"+openingCashCashier.getNameSupervisor()+"</b>");
                                    out.println("</div>");
                                    out.println("</div>");
                                    out.println("</li>");
                                }
                                
                            }
                        %>
                    </ul>                  
                </div>
            </div>
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      <footer class="main-footer">
        <div class="pull-right hidden-xs">
          <b>Version</b> 1.0
        </div>
	  <strong>Copyright &copy; 2015 <a href="http://almsaeedstudio.com">Dimata IT Solution &REG;</a>.</strong> All rights reserved.
      </footer>
      
      
    </div><!-- ./wrapper -->
    
    <!--JQUERY & BOOTSTRAP COMPONEN -->
    <%@include file="cashier-jquery-bootstrap.jsp" %>
    
     <script type="text/javascript">
	 $(document).ready(function (){
	 
	    function  OpenCashier(datasend, loadplace, usedataload){
		$("#CONTENT_ANIMATE_CHECK").fadeIn("medium");
		$("#CONTENT_LOAD_TEXT").show();
		
		if(usedataload == true){
		    $.ajax({
			type    : "POST",
			url	    : "<%= approot %>/OpenCashierHandler",
			data    : datasend,
			cache   : false,
			success : function(data){
			    $("#CONTENT_ANIMATE_CHECK").hide();
			    $("#CONTENT_LOAD_TEXT").hide();
			    $("#"+loadplace).html(data).fadeIn("medium");
			    
			},
			error : function(){
			    //alert("error");
			}
                    }).done(function(data){
                        enterPress();
		    });
		}else{
                    //alert("test");
		    $.ajax({
			type    : "POST",
			url	    : "<%= approot %>/OpenCashierHandler",
			data    : datasend,
			cache   : false,
			success : function(data){
			    $("#CONTENT_ANIMATE_CHECK").hide();
			    $("#CONTENT_LOAD_TEXT").hide();

			    if(data > 0){
				$("#resultMessage").html("<b>CASHIER ALREADY OPENED</b><br>Redirecting to Transaction Page").show();
				$("#result").addClass("modal-info");
				$("#result").modal("show");
				window.location = "<%= approot %>/cashier/cashier-transaction.jsp";
			    }else{
				$("#"+loadplace).fadeIn("medium");
			    }
			},
			error : function(){
			    //alert("error");
			}
                    }).done(function(data){
                        enterPress();
		    });
		}
	    }
	    
	    //LOAD DATA
	    var loaddata = {
		"loadtype":"checkopencashier", 
		"<%= FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SPV_OID] %>":"<%= userId %>",
		"command":"<%= Command.NONE %>"
	    };
	    OpenCashier(loaddata, "CONTENT_<%= FrmCashCashier.FRM_NAME_CASH_CASHIER %>", false);
	    
	    $("#<%= FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_LOCATION] %>").change(function(){
		
		var loaddata = {
		    "loadtype":"checkcashiernumb",
		    "command":"<%= Command.NONE %>",
		    "<%= FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_LOCATION] %>":$(this).val()
		};
		
		OpenCashier(loaddata,"LOAD_<%= FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CASH_MASTER_ID] %>", true);
	    });
            
            //ON CLOSE MODAL
            $(".modal").on('hidden.bs.modal', function() {
                var id = $(this).attr('id');
                if (id=="openprintpreview"){
                    $("#resultMessage").html("<b>OPEN CASHIER SUCCESS</b><br>Redirecting to Transaction Page").show();
                    $(".modal").addClass("modal-success");
                    $("#result").modal("show");
                    window.location = "<%= approot %>/cashier/cashier-transaction.jsp";
                }
            });
	    
	    //OPEN CASHIER
	    $("form#<%= FrmCashCashier.FRM_NAME_CASH_CASHIER %>").submit(function(){
               
		$("#btnOpen").attr({"value":"Please wait..","disabled":true});
		$.ajax({
		    type    : "POST",
		    url	    : "<%= approot %>/OpenCashierHandler",
		    data    : $(this).serialize(),
		    cache   : false,
		    success : function(data){
                        /* !!!!! -----> WARNING <----- !!!!!
                        //BUTTON "OPENING" TIDAK PERLU DIAKTIFKAN LAGI AGAR TIDAK DITEKAN LAGI, MENCEGAH OPENING 2 KALI
			//$("#btnOpen").removeAttr("disabled").attr({"value":"Open Cashier"});
                        */
			$("#CONTENT_ANIMATE_CHECK").hide();
			$("#CONTENT_LOAD_TEXT").hide();
			
                        var temp = data.split("*");
			if(temp[0] > 0){
			    $("#resultMessage").html("<b>OPEN CASHIER FAILED</b><br>Invalid supervisor username or password").show();
			    $(".modal").addClass("modal-danger");
			    $("#result").modal("show");
			}else{
                            $("#CONTENT_PRINT_PREVIEW").html(temp[1]);
                            $("#CONTENT_PRINT").html(temp[1]);
                            $("#openprintpreview").modal('show');
   
                            window.print();
			    //$("#resultMessage").html("<b>OPEN CASHIER SUCCESS</b><br>Redirecting to Transaction Page").show();
			    //$(".modal").addClass("modal-success");
			    //$("#result").modal("show");
			    //window.location = "<%= approot %>/cashier/cashier-transaction.jsp";
			}
			
			
		    },
		    error : function(){
			//alert("error");
		    }
		});
		return false;
	    });
	    
	    //MODAL OPTION
	    $("#result").modal({
		"backdrop" : "static",
		"keyboard" : false,
		"show" : false
	    });
            
            //
            var base = "<%=baseURL%>";
            var interval =0;
            
            function keyPressProcessing(controlId){
                var listControl = [
                    "FRM_FIELD_LOCATION", 
                    "FRM_FIELD_SHIFT_ID", 
                    "FRM_FIELD_CASH_MASTER_ID",
                    "FRM_FIELD_SUBTOTAL1",
                    "FRM_FIELD_SUBTOTAL2",
                    "SUPERVISOR_USERNAME",
                    "SUPERVISOR_PASSWORD"
                ];
                
                var listDestination = [
                    "FRM_FIELD_SHIFT_ID",
                    "FRM_FIELD_CASH_MASTER_ID",
                    "FRM_FIELD_SUBTOTAL1_1",
                    "FRM_FIELD_SUBTOTAL2",
                    "SUPERVISOR_USERNAME",
                    "SUPERVISOR_PASSWORD",
                    "SUBMIT"
                ];
                
                var index = listControl.indexOf(controlId); 
                var destination = listDestination[index];
                
                return destination;
            }
            
            function ajaxFingerHandler(url,data,type,appendTo,another,optional,optional2){
                $.ajax({
                    url : ""+url+"",
                    data: ""+data+"",
                    type : ""+type+"",
                    async : false,
                    cache: false,
                    dataType: "json",
                    success : function(data) {
                        if (another=="checkUser"){
                            $(''+appendTo+'').html(data.result);
                        }

                    },
                    error : function(data){
                        //alert('error');
                    }
                }).done(function(data){
                    if (another=="checkUser"){
                        fingerClick();  
                        getCode();    
                    }else if (another=="getCode"){
                        $('#SUPERVISOR_PASSWORD').val(data.result);
                    }else if (another=="checkStatusUser"){

                        if (data.result==1){
                            clearInterval(interval);
                            var userName = $('#spvFinger').val();
                            $('#SUPERVISOR_USERNAME').val(userName);
                            alert('Verification success...');
                            $('#<%= FrmCashCashier.FRM_NAME_CASH_CASHIER %>').submit();
                        }
                    }
                });
            }

            function checkUser(){
                var url = ""+base+"/FingerHandler";
                var loginId = $('#spvFinger').val();
                var data="command=<%=Command.ASK%>&login="+loginId+"&base="+base+"&group=0&type=1";
                ajaxFingerHandler(url,data,"POST","#dynamicPlace","checkUser","","");
            }

            function checkStatusUser(userId){
                var url = ""+base+"/FingerHandler";
                var data="command=<%=Command.SEARCH%>&loginId="+userId+"&group=0&type=1";
                ajaxFingerHandler(url,data,"POST","","checkStatusUser","","");
            }

            function getCode(){
                var url = ""+base+"/FingerHandler";
                var loginId = $('#spvFinger').val();
                var data="command=<%=Command.DETAIL%>&login="+loginId+"";
                ajaxFingerHandler(url,data,"POST","","getCode","","");
            }

            function fingerClick(){
                $('.loginFinger').click(function(){
                    var loginId= $('#spvFinger').val();
                    clearInterval(interval);
                    interval =  setInterval(function() {
                        checkStatusUser(loginId);
                    },5000);

                });
            }

            $('#spvFinger').keyup(function(){
                checkUser();
            });
            
            //$(".money").maskMoney({allowZero:true,decimal:'.'});
            $('.money').keyup(function(){
                var value = $(this).val();
                var conValue = parserNumber(value,false,0,'');
                $(this).val(conValue);
            });
            
            setTimeout(function(){
                $('#FRM_FIELD_LOCATION').focus();
            }, 200);
            
            function enterPress(){
                $('.enterPress').keydown(function(e){ 
                    if (e.keyCode == 13) {
                        var id =$(this).attr('id');
                        var dest = keyPressProcessing(id);
                        if (dest!="SUBMIT"){
                            $('#'+dest+'').focus();
                            e.preventDefault();
                            return false; 
                        }

                    }
                });
            }
            $("#print").click(function(){
                window.print();
            });
            
            $(".subOpening").keydown(function(e){
                if (e.keyCode==13){
                    if ($(this).hasClass('lastOpening')){
                        $("#SUPERVISOR_USERNAME").focus();
                    }else{
                        var index = $(this).data("index"); var next = index +1;
                        $("#FRM_FIELD_SUBTOTAL1_"+next+"").focus();
                    }
                    e.preventDefault(); return false;
                } 
            });
            
            $('.modal').on('hidden.bs.modal', function () {
                var id = $(this).attr('id');
                if (id=="result"){
                    $(".modal").removeClass("modal-danger");
                    //$(".modal").removeClass("modal-primary");
                }
            });
            
            enterPress();
            checkUser();
            
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
     <!-- BAGIAN INI ADALAH SCRIPT UNTUK FINGER -->
    
    <div id="result" class="modal">
      <div class="modal-dialog">
	<div class="modal-content">
	  <div class="modal-header">
	    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	    <h4 class="modal-title">INFO</h4>
	  </div>
	  <div class="modal-body" id="resultMessage">
	  </div>
	  <div class="modal-footer">
	    <button type="button" class="btn btn-outline" data-dismiss="modal">Close</button>
	  </div>
	</div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <!-- PLUGINS COMPONEN -->
    <%@include file="cashier-plugin.jsp" %>
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