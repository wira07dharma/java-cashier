$(document).ready(function(){
    $("#savecovernumber").click(function(){
        var approotsys = $("#approotsystem").val();
        var covernumber = $("#covernumber").val();
        var master_event = $("#master_event").val();
        var master_group = $("#master_group").val();
        var master_payment = $("#master_payment").val();
        var master_guide = $("#master_guide").val();
        var oidDocSys = $('#mainBillIdSource').val();
        var separatePrint = $("#separateprint").val();
        var typecustomerselected = $("#member").val();
        var from = $("#btnfrom").val();
        if(covernumber.length > 0){
            if(typecustomerselected=='10002'){
                if(master_event.length <=0 || master_group.length <=0 || master_payment.length <=0){
                    alert("Invoice Kredit, Please select Event, Payment and Group Booking");
                    return false;
                }
            }
            $(this).html("Saving..").attr("disabled",true);
            if(covernumber.length > 0){
                $.ajax({
                    type    : "POST",
                    url     : approotsys+"/TransactionCashierHandler",
                    data    : {
                        "command" : 4,
                        "loadtype" : "covernumber",
                        "oid" : oidDocSys,
                        "covernumber" : covernumber,
                        "master_event": master_event,
                        "master_group": master_group,
                        "master_payment":master_payment,
                        "master_guide":master_guide
                    },
                    dataType : "json",
                    success: function(data) {
                        $("#savecovernumber").removeAttr("disabled").html("Save");
                        if(data.FRM_FIELD_HTML == "success"){
                            $("#modalCoverNumber").modal("hide");
                            //alert(separatePrint);
                            if(separatePrint == 0){
                                if(from == 1){
                                    $("#btnSaveMultiPayment").trigger("click");
                                }else{
                                    $("#btnPaySave").trigger("click");
                                }
                                
                            }else if(separatePrint == 1){
                                if(from == 1){
                                    $("#btnSaveMultiPayment").trigger("click");
                                }else{
                                    $("#btnPaySave").trigger("click");
                                }
                                $("#multiPayment").modal("hide");
                            }else if(separatePrint == 99){
                                $("#separateprint").val("0");
                                if(from == 1){
                                    $("#btnSaveMultiPayment").trigger("click");
                                }else{
                                    $("#btnPaySave").trigger("click");
                                }
                                $("#multiPayment").modal("hide");
                            }
                        }
                    }
                });
            }else{

            }
        }else{
            alert("Please input bill number");
        }
    })
})

