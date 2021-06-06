/**
 *
 * @author Witar
 */
package com.dimata.cashierweb.ajax.cashier;

import com.dimata.cashierweb.entity.cashier.assigndiscount.PstAssignDiscount;
import com.dimata.common.entity.logger.LogSysHistory;
import com.dimata.common.entity.logger.PstLogSysHistory;
import com.dimata.posbo.entity.admin.AppUser;
import com.dimata.posbo.entity.admin.FingerPatern;
import com.dimata.posbo.entity.admin.PstAppUser;
import com.dimata.posbo.entity.admin.PstFingerPatern;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.util.Command;
import com.lowagie.text.pdf.codec.Base64;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;


public class FingerHandler extends HttpServlet {
    
    JSONObject jSONObject;
    String result;
    double maxDisc;
    public static final String textListFinger[][]={
        {"Kelingking Kiri","Jari Manis Kiri","Jari Tengah Kiri","Telunjuk Kiri","Ibu Jari Kiri","Ibu Jari Kanan","Telunjuk Kanan","Jari Tengah Kanan","Jari Manis Kanan","Kelingking Kanan"},
        {"Left Little Finger","Left Ring Finger","Left Middle Finger","Left Fore Finger","Left Thumb","Right Thumb","Right Fore Finger","Right Middle Finger","Right Ring Finger","Right Little Finger"}   
    };

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        jSONObject = new JSONObject();
        result ="";
        maxDisc = 0;
        int command = FRMQueryString.requestInt(request,"command");
        response.setContentType("text/html;charset=UTF-8");
        
        switch (command) {
            case Command.ASK :
                result= checkUser(request);
                break;
            case Command.LIST :
                //response.getWriter().write(result);
                //break;
            case Command.SEARCH:
                result = String.valueOf(checkStatusUser(request));
                break;
            case Command.DETAIL:
                result = getPassword(request);
                break;
             case Command.ASSIGN:
                result = String.valueOf(saveHistoryVerificationFinger(request));
                break;
            default:

        }
        
        try{
            jSONObject.put("result", result);
            jSONObject.put("maxDisc", maxDisc);
        }catch(JSONException ex){
            ex.printStackTrace();
        }
        
        response.getWriter().print(this.jSONObject);
        
    }
    
    // Custom function
    private String checkUser(HttpServletRequest request){
        String result ="";
        String whereClause="";
        String whereFinger="";
        String whereFinger2="";
        String group ="0";
        int typeVerification =1;
        
        Hashtable<Integer, Boolean> fingerType = new Hashtable<Integer, Boolean>();
        AppUser appUser= new AppUser();
        
        String login = FRMQueryString.requestString(request,"login");
        int language = FRMQueryString.requestInt(request, "language");
        String base = FRMQueryString.requestString(request, "base");
        
        try {
            typeVerification = FRMQueryString.requestInt(request, "type");
        } catch (Exception e) {
        }
        
        
        try {
            group = FRMQueryString.requestString(request, "group");
        } catch (Exception e) {
            group = "0";
        }
        
        //get base url
        if (group.equals("1")){
            whereClause = " "+PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]+"='"+login+"'  AND "+PstAppUser.fieldNames[PstAppUser.FLD_USER_GROUP_NEW]+"='1' ";
        }else{
            whereClause = " "+PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]+"='"+login+"'";
        }
        
        Vector listUser = PstAppUser.listFullObj(0, 0, whereClause, "");
        if (listUser.size()>0){
            appUser = (AppUser) listUser.get(0);
            //update status user menjadi 0
            appUser.setUserStatus(0);
            PstAppUser.update(appUser);
            //dapatkan data jari yang sudah didaftarkan oleh user
            whereFinger = " "+PstFingerPatern.fieldNames[PstFingerPatern.FLD_EMPLOYEE_ID]+"="+appUser.getEmployeeId()+"";
            Vector listFinger = PstFingerPatern.list(0, 0, whereFinger, "");
            
            //masukkan ke hash table
            for (int i = 0; i<listFinger.size();i++){
                FingerPatern fingerPatern = (FingerPatern)listFinger.get(i);
                fingerType.put(fingerPatern.getFingerType(), true);  
            }
            
            //buat sepuluh kotak
            for (int j= 0; j<10;j++){
                Boolean found = false;
                try{
                    if (fingerType.size()>0){
                        found = fingerType.containsKey(j);
                    }

                }catch(Exception ex){
                    found= false;
                }
                if (found==true){
                    //jika jari tersebut sudah didaftarkan, backgorund kotak hijau, dan langsung berisi link verification
   
                    result +="<div class=\"finger\">";
                    //memberi link untuk masing-masing kotak jari
                    whereFinger2 =" "+PstFingerPatern.fieldNames[PstFingerPatern.FLD_EMPLOYEE_ID]+"= "+appUser.getEmployeeId()+" and "+PstFingerPatern.fieldNames[PstFingerPatern.FLD_FINGER_TYPE]+"="+j+"";
                    Vector listFinger2 = PstFingerPatern.list(0, 0, whereFinger2, "");
                    FingerPatern fingerPatern = (FingerPatern) listFinger2.get(0);
                    //String urlVerification =""+base+"verification.php?user_id="+appUser.getEmployeeId()+"="+j+"="+base+"";
                    
                    //setiap link di enkripsi dengan metode base64
                    //byte[] urlByte = urlVerification.getBytes();
                    //String urlBase= new String(Base64.encodeBytes(urlByte));
                    String urlBase = "verification&"+appUser.getEmployeeId()+"&"+fingerPatern.getFingerPatern()+"&"+base+"cashier/process_verification.jsp";
                    if (typeVerification==1){
                        result +="<a class='loginFinger' href='findspot:findspot protocol;"+urlBase+"'><div class='finger_spot green' >"+textListFinger[1][j]+"</div></a>";
                    }else{
                        result +="<a class='loginFinger2' href='findspot:findspot protocol;"+urlBase+"'><div class='finger_spot green' >"+textListFinger[1][j]+"</div></a>";
                    
                    }
                    result +="</div>";
                  
                }else{
                    //jika jari tersebut belum didaftarkan, background kotak putih, dan link verifikasi tidak ada
                    result +="<div class=\"finger\">";
                    result +="<div class=\"finger_spot\"></div>";
                    result +="</div>";
                }
            }
            
            
        }else{
            for (int j= 0; j<10;j++){
                result +="<div class=\"finger\">";
                result +="<div class=\"finger_spot\"></div>";
                result +="</div>"; 
            }
        }
        
        return result;
    }
    private int checkStatusUser(HttpServletRequest request){
        int result=0;
        String whereClause="";
        
        String loginId = FRMQueryString.requestString(request,"loginId");
        whereClause = " "+PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]+"='"+loginId+"'";
        Vector listAppUser = PstAppUser.listFullObj(0, 0, whereClause, "");
        if(listAppUser.size()>0){
            AppUser appUser = (AppUser)listAppUser.get(0);
            result = appUser.getUserStatus();
        }
        
        return result;
    }
    private String getPassword(HttpServletRequest request){
        String result="";
        String whereClause="";
        
        String loginId = FRMQueryString.requestString(request,"login");
        whereClause = " "+PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]+"='"+loginId+"'";
        Vector listAppUser = PstAppUser.listFullObj(0, 0, whereClause, "");
        if(listAppUser.size()>0){
            AppUser appUser = (AppUser)listAppUser.get(0);
            result = appUser.getPassword();
        }
        
        return result;
    }
    private int saveHistoryVerificationFinger(HttpServletRequest request){
        int result =0;
        long err = 0;
        String whereClause="";
        AppUser appUser= new AppUser();
        LogSysHistory logSysHistory = new LogSysHistory();
        PstLogSysHistory pstLogSysHistory = new PstLogSysHistory();
        
        String loginId = FRMQueryString.requestString(request, "login");
        long oidDoc = FRMQueryString.requestLong(request, "oidDoc");
        String base = FRMQueryString.requestString(request, "base");
                
        whereClause = " "+PstAppUser.fieldNames[PstAppUser.FLD_LOGIN_ID]+"='"+loginId+"'";
        Vector listAppUser = PstAppUser.listFullObj(0, 0, whereClause, "");
        appUser = (AppUser)listAppUser.get(0);
        
        //set objek log sys history
        logSysHistory.setLogUserId(appUser.getOID());
        logSysHistory.setLogDocumentId(oidDoc);
        logSysHistory.setLogLoginName(appUser.getFullName());
        logSysHistory.setLogDocumentNumber("");
        logSysHistory.setLogDocumentType("Bill");
        logSysHistory.setLogUserAction("Verification");
        logSysHistory.setLogOpenUrl(base);
        logSysHistory.setLogUpdateDate(new Date());
        logSysHistory.setLogApplication("Prochain");
        logSysHistory.setLogDetail("Payment verification using finger print");
        
        try {
            err = pstLogSysHistory.insertExc(logSysHistory);
        } catch (Exception e) {
        }
        
        if (err!=0){
            maxDisc = PstAssignDiscount.getMaxDiscount(PstAssignDiscount.fieldNames[PstAssignDiscount.FLD_EMPLOYEE_ID]+"='"+appUser.getEmployeeId()+"'");
            result=1;
        }
        
        return result;
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
