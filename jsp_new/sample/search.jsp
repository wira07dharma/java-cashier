<%-- 
    Document   : addorder
    Created on : Dec 22, 2014, 4:02:26 PM
    Author     : dimata005
--%>

<%@page import="com.dimata.cashierweb.entity.masterdata.PstRoom"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.Room"%>
<%@page import="com.dimata.gui.jsp.ControlCombo"%>
<%@page import="com.dimata.pos.form.billing.FrmBillMain"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@ include file = "../main/javainit.jsp" %>
<%
 int iCommand = FRMQueryString.requestCommand(request);
 long roomId = FRMQueryString.requestLong(request, FrmBillMain.fieldNames[FrmBillMain.FRM_FIELD_ROOM_ID]);
 
 Vector val_locationid = new Vector(1,1);
 Vector key_locationid = new Vector(1,1);
 String whereRoom = PstLocation.fieldNames[PstLocation.FLD_LOCATION_ID]+"='"+locationSales+"'";
 Vector vt_loc = PstRoom.list(0,0,whereRoom, PstLocation.fieldNames[PstLocation.FLD_CODE]);
 val_locationid.add("0");
 key_locationid.add("All Room");
 for(int d=0;d<vt_loc.size();d++){
        Room room = (Room)vt_loc.get(d);
        val_locationid.add(""+room.getOID()+"");
        key_locationid.add(room.getName());
 }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Taking Order - Queens Bali</title>
<link href="../styles/takingorder/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="../styles/takingorder/css/bootstrap_002.css" rel="stylesheet" type="text/css">
<link href="../styles/takingorder/css/customestyle.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
            function cmdChangeLocationx(idRoom){
                $("#posts").html("");
                    $.ajax({
                        url  : "<%=approot%>/servlet/com.dimata.cashierweb.ajax.CheckTableFromRoom?roomId="+idRoom+"",
                        type : "POST",
                        data : {"roomId":idRoom},
                        async : false,
                        cache: false,
                        success : function(data) {
                                $("#posts").html(data);
                        }
                    });
            }
            
            function cmdChangeLocation(idRoom){
                $("#posts").html("");
                    $.ajax({
                        url  : "<%=approot%>/servlet/com.dimata.cashierweb.ajax.outlet.AjaxCtrlBillMain",
                        type : "POST",
                        data : {"roomId":idRoom,
                                "command":"7"},
                        async : false,
                        cache: false,
                        dataType: "json",
                        success : function(data) {
                                //$("#posts").html(data);
                                 //var names = data
                                    $('#posts').html(data);
                                
                                /*var json_obj = $.parseJSON(data);//parse JSON
                                alert("1"+json_obj);
                                var output="<ul>";
                                for (var i in json_obj) 
                                {
                                    output+="<li>" + json_obj[i].id + ",  " + json_obj[i].lokasi + "</li>";
                                }
                                output+="</ul>";
                                alert("2"+output);
                                $('#posts').html(output);*/
                        } 
                    });
            }
</script>    
</head>
<body>
<%@ include file = "../outletonline/header.jsp" %>
<div class="container">
    <form name="frmsrcsalesorder" method ="post" action="" role="form">
        <input type="hidden" name="command" value="<%=iCommand%>">
            <div style="margin-top:20px;">
            <button name="button" class="btn btn-danger btn-md btn-block" onclick="javascript:cmdBack()" type="button">Back to Main Menu</button>
                <hr style="margin-top:10px;" />
                <div class=row>
                    <div class="col-md-12" style="margin-bottom:10px;">
                        <form method="post" class="form-horizontal" role="form" id="validate">
                           <div class="form-group">
                               <div class=row>
                                <div class="col-md-12"> 
                                        <div class="input-group">
                                            <input name="location_name" id="guestname" type="text" class="form-control" placeholder="Insert guest name.." required>
                                            <div class="input-group-btn">
                                                <button class="search btn btn-flat" type="button"><i class="glyphicon glyphicon-search"></i></button>
                                            </div>
                                        </div>
                                 </div>
                                 <div class="col-md-12">   
                                    <div class="form-group">
                                       <div class=row>
                                           <div class="col-md-12">
                                           <%=ControlCombo.drawBoostrap("ruangan","form-control",null,""+roomId, val_locationid, key_locationid, "onChange=\"javascript:cmdChangeLocation(this.value)\"")%>
                                           </div>
                                       </div>
                                    </div>
                                 </div> 
                                 <div class="col-md-12">           
                                    <div class="form-group">
                                        <div class=row>
                                            <div class="col-md-12" id="posts"></div>
                                        </div>
                                    </div>
                                 </div>   
                               </div>               
                           </div>
                            <div class="form-group">
                                <div class=row>
                                    <div class="col-md-12">
                                        <button class="btn btn-primary btn-md btn-block" id="print" type="button" >Print</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <hr/>
            <div style="margin:20px 0px; text-align:center;">
            </div>
            <div id="myModal" class="modal fade" tabindex="-1">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title" id="modal-title"></h4>
                            </div>
                            
                            <div class="modal-body" id="modal-body">
                            </div>
                            <div class="modal-footer">
                                <button type="button" data-dismiss="modal" class="btn btn-danger">Close</button>
                                <button type="button" id="buttonSave" class="btn btn-primary">Select</button>
                            </div>
                        </div>
                    </div>
                </div>  
        </div>
    </form>
</div>
<script type="text/javascript" src="../styles/jquery.min.js"></script>
<!-- jQuery UI 1.10.3 -->
<script src="../styles/bootstrap3.1/js/jquery-ui-1.10.3.min.js" type="text/javascript"></script>
<!-- Bootstrap -->
<script src="../styles/bootstrap3.1/js/bootstrap.min.js" type="text/javascript"></script>
<script language="JavaScript">
    $(document).ready(function(){
        
        function ajaxScriptParentPage(pageTarget, titlePage, pageShow, modalTemplate, oid, titleId, bodyId){
            $(titleId).html(titlePage);
            $(modalTemplate).modal("show");
            $(bodyId).html("Harap tunggu");
            var idNo = $("#guestname").val();
            $.ajax({
                type	: "POST",
                url	: pageTarget,
                data	: {"searchType":"parent", 
                            "pageShow":pageShow,
                            "studentName":idNo,
                            "oid":oid
                        },
                cache	: false,
                success	: function(data){
                    $(bodyId).html(data);

                },
                error : function(){
                    $(bodyId).html("Data not found");
                }
            });
        }
        
        //agar modal tidak close saat are di luar form di klik
        $("#myModal").modal({
                        backdrop:"static",
                        keyboard:false,
                        show:false
        });

        //fungsi jika tombol search di klik, maka akan menampilakn data
        $(".search").click(function(){
            ajaxScriptParentPage("data_ajax.jsp","Search Member",1,"#myModal",0, "#modal-title", "#modal-body");
        });
        
        
        $("#print").click(function(){
             // ajaxScriptParentPage("data_ajax.jsp","Print",2,"#myModal",0, "#modal-title", "#modal-body");
            alert("hello")  ;
            $("#posts").html("");
                    $.ajax({
                        url  : "<%=approot%>/servlet/com.dimata.cashierweb.ajax.outlet.AjaxCtrlBillMain",
                        type : "POST",
                        data : {"command":"7"},
                        async : false,
                        cache: false,
                        dataType: "json",
                        success : function(data) {
                                //$("#posts").html(data);
                                alert("test"+data);
                                console.log(data);
                                
                                var obj = jQuery.parseJSON( data );
                                alert(obj.val1);
                                
                                /*var json_obj = $.parseJSON(data);//parse JSON
                                alert("1"+json_obj);
                                var output="<ul>";
                                for (var i in json_obj) 
                                {
                                    output+="<li>" + json_obj[i].id + ",  " + json_obj[i].lokasi + "</li>";
                                }
                                output+="</ul>";
                                alert("2"+output);
                                $('#posts').html(output);*/
                        } 
                    });
        });
      
        //dokument yang 
        function searchList(){
            //alert("hello");
            $("form#searchList").submit(function(){
                $("#resultSearch").html("Harap Tunggu").fadeIn("medium");
                //$("#resultFirst").hide();
                $.ajax({
                    type	: "POST",
                    url		: "data_ajax.jsp",
                    data	: $(this).serialize(),
                    cache	: false,
                    success	: function(data){
                        $("#resultSearch").html(data).fadeIn("medium");
                    },
                    error : function(){

                        alert("error");
                    }
                }).done(function(){
                selectGuest();
            });
                return false;
            });
            selectGuest();
        }
        
        //event modal, di tambahkan agar pada saat search, tetap berada di modal
        $("#myModal").on("shown.bs.modal",function (e){
            ajaxFunctionChildPage(searchList());

        });
        
        function ajaxFunctionChildPage(ajaxFunction){
            return ajaxFunction;
        }
        
        function selectGuest(){
            $(".selected").click(function(){
                var checkedData = $(this);

                var dataName = checkedData.data("options").name;
                document.frmsrcsalesorder.location_name.value=dataName;

                $("#myModal").modal("hide");
            });
        }
        
        
   });
   
 
</script>        
</body>
</body>
</html>
