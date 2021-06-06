<%-- 
    Document   : testcard
    Created on : Nov 29, 2016, 10:18:12 AM
    Author     : Witar
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="../styles/bootstrap3.1/js/jquery.min.js"></script>
        
    </head>
    <body>
    <header>
      <h1>Get Card Swipe Test Page</h1>
      <h2>This test page should be compatible with any USB magstripe reader that emulates a standard keyboard and reads track1 data.</h2>
    </header>

    <h3>Swipe when you're ready!</h3>
    <form action="#">
      <div class="control-group">
        <label for="cc_num">Credit Card Number</label>
        <div class="controls">
          <input id="cc_num" name="cc_num" type="text">
        </div>
      </div>
      <div class="control-group">
        <label for="cc_exp_month">Exp Month</label>
        <div class="controls">
          <input id="cc_exp_month" name="cc_exp_month" type="text">
        </div>
      </div>
      <div class="control-group">
        <label for="cc_exp_year">Exp Year</label>
        <div class="controls">
          <input id="cc_exp_year" name="cc_exp_year" type="text">
        </div>
      </div>
    </form>
    <script type="text/javascript">
        $(document).ready(function() {
            var swipe = function(text){
                var track1 = "";
                var track2 = "";
                var track3 = "";
                var track1_cardholder = "";
                var track1_expmo = "";
                var track1_expyr = "";
                var track1_cvv = "";
                var track1_ccn = "";
                var track2_ccn = "";
                var track2_expmo = "";
                var track2_expyr = "";
                var track2_encpin = "";
                
                var in_track_1 = true;
                var in_track_2 = false;
                var in_track_3 = false;
                var track1_caret1_found = false;
                var track1_caret2_found = false;
                var track2_equals_found = false;
                
                var track1_leg3_count = 0;
                var track2_leg2_count = 0;
                var i =0;
                
                var count = text.length;
               
                for (i = 0; i < count; i++) {
                    var c = text.charAt(i);
                    if (in_track_1==true){
                        if (!track1_caret1_found){                               
                            if ((c != '%') && (c != 'B') && (c != '^')){
                                track1_ccn += c;
                            }
                            if (c == '^'){
                                track1_caret1_found = true;
                                track1 += c;
                                continue;
                            }    
                        }
                        if (track1_caret1_found && !track1_caret2_found){
                            if (c != '^'){
                                track1_cardholder += c;
                            }else{
                                track1_caret2_found = true;
                                track1 += c;
                                continue;
                            }
                        }
                        if (track1_caret1_found && track1_caret2_found){                       
                            if (track1_leg3_count == 0) track1_expyr += c;
                            if (track1_leg3_count == 1) track1_expyr += c;
                            if (track1_leg3_count == 2) track1_expmo += c;
                            if (track1_leg3_count == 3) track1_expmo += c;
                            if (track1_leg3_count == 22) track1_cvv += c;
                            if (track1_leg3_count == 23) track1_cvv += c;
                            if (track1_leg3_count == 24) track1_cvv += c;
                            track1_leg3_count++;
                        }
                        track1 += c;
                        if (c == '?'){
                            in_track_1 = false;
                            in_track_2 = true;
                            continue;
                        }
                        
                        
                    }
                    
                    if (in_track_2){                   
                        if (!track2_equals_found){                      
                            if ((c != ';') && (c != '=')){
                                track2_ccn += c;
                            }

                            if (c == '='){
                                track2_equals_found = true;
                                track2 += c;
                                continue;
                            }
                        }

                        if (track2_equals_found){
                            if (track2_leg2_count == 0) track2_expyr += c;
                            if (track2_leg2_count == 1) track2_expyr += c;
                            if (track2_leg2_count == 2) track2_expmo += c;
                            if (track2_leg2_count == 3) track2_expmo += c;
                            if (track2_leg2_count == 8) track2_encpin += c;
                            if (track2_leg2_count == 9) track2_encpin += c;
                            if (track2_leg2_count == 10) track2_encpin += c;
                            track2_leg2_count++;
                        }

                        track2 += c;
                        if (c == '?'){
                            in_track_2 = false;
                            in_track_3 = true;
                            continue;
                        }

                    }
                    
                    if (in_track_3){           
                        track3 += c;
                    }
                }
                alert("  Track1: " + track1);
                alert("    CCN: " + track1_ccn);
                alert("    Exp: " + track1_expmo + "/" + track1_expyr);
                alert("    CVV: " + track1_cvv);
                alert("    Cardholder: " + track1_cardholder.Trim());
                alert("  Track2: " + track2);
                alert("    CCN: " + track2_ccn);
                alert("    Exp: " + track2_expmo + "/" + track2_expyr);
                alert("    PIN: " + track2_encpin);
                alert("  Track3: " + track3);
            };
            //alert("run");
            $("#cc_num").change(function(){
                var value = $(this).val();
                swipe(value);
            });
            
        });
    </script>

  </body>
</html>
