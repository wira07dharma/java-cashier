/* Generated by Together */

package com.dimata.pos.session.billing;
import java.util.Date;

public class makeInvoiceNo {

public static String setInvoiceNumber(){
    Date tgl = new Date();
    String kode = "";
    String tglDate = "";
    String tglMonth = "";
    String tglhour="";
    String tglMin="";
    String tglsec="";
    tglMonth = ""+(tgl.getMonth()+1);
    if (tglMonth.length()==1)
         tglMonth="0"+tglMonth;
    tglDate = ""+tgl.getDate();
    if (tglDate.length()==1)
         tglDate="0"+tglDate;
    tglhour = ""+tgl.getHours();
    if (tglhour.length()==1)
         tglhour="0"+tglhour;
    tglMin = ""+tgl.getMinutes();
    if (tglMin.length()==1)
         tglMin="0"+tglMin;
    tglsec = ""+tgl.getSeconds();
    if (tglsec.length()==1)
         tglsec="0"+tglsec;

    kode = ""+(tgl.getYear()+1900)+tglMonth+tglDate+tglhour+tglMin+tglsec+"";
    System.out.println(kode);
    return kode;
 }
 
 public static String setTgl(){
    Date tgl = new Date();
    String kode = "";
    String tglDate = "";
    String tglMonth = "";
    String tglhour="";
    String tglMin="";
    String tglsec="";
    tglMonth = ""+(tgl.getMonth()+1);
    if (tglMonth.length()==1)
         tglMonth="0"+tglMonth;
    tglDate = ""+tgl.getDate();
    if (tglDate.length()==1)
         tglDate="0"+tglDate;
   

    kode = tglDate+"/"+tglMonth+"/"+(tgl.getYear()+1900);
    System.out.println(kode);
    return kode;
 }
 
 public static String setTime(){
    Date tgl = new Date();
    String kode = "";
    String tglDate = "";
    String tglMonth = "";
    String tglhour="";
    String tglMin="";
    String tglsec="";
    tglMonth = ""+(tgl.getMonth()+1);
    if (tglMonth.length()==1)
         tglMonth="0"+tglMonth;
    tglDate = ""+tgl.getDate();
    if (tglDate.length()==1)
         tglDate="0"+tglDate;
    tglhour = ""+tgl.getHours();
    if (tglhour.length()==1)
         tglhour="0"+tglhour;
    tglMin = ""+tgl.getMinutes();
    if (tglMin.length()==1)
         tglMin="0"+tglMin;
    tglsec = ""+tgl.getSeconds();
    if (tglsec.length()==1)
         tglsec="0"+tglsec;

    kode = ""+tglhour+":"+tglMin+":"+tglsec+"";
    System.out.println(kode);
    return kode;
 }
 
 public static void main(String args[]){
    String invoiceNo=setInvoiceNumber();
    System.out.println("nomor invoice   "+invoiceNo);
 }
}