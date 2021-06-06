/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.ajax.templateprint;

/**
 *
 * @author dimata005
 */

import com.dimata.util.Formater;
import java.util.Date;

/**
 *
 * @author ardiadi
 */
public class PrintDynamicTemplate extends EntDataList{
    private EntPrintData entPrintData;
    public String showData(EntPrintData entPrintData){
        this.entPrintData = entPrintData;
        String html = "";
        html += ""
        + "<div style='backround-repeat:no-repeat;background-size:"+entPrintData.getPaperWidth()+";"+entPrintData.getPaperHeight()+";width:100%;padding:0.1cm;width:"+entPrintData.getPaperWidth()+";'>"
            + printHeader()
            + printBody()
            + printFooter()
        + "</div>";
        return html;
    }
    
    private String printHeader(){
        switch(this.entPrintData.getActiveTemplate()){
            case 1 :
                return header1();
            default: 
                return header();
        }
    }
    
    private String printBody(){
        switch(this.entPrintData.getActiveTemplate()){
            case 1 :
                return body1();
            default: 
                return body();
        }
    }
    
    private String printFooter(){
        switch(this.entPrintData.getActiveTemplate()){
            case 1 :
                return footer1();
            default: 
                return footer();
        }
    }
    
    private String header(){
        String html = "";
        html +=""
        + "<div class='row'>"
            + "<div class='col-md-12' style='font-size:11px;'>"
                + "<div style='position:absolute;right:0px;'>"
                    + "<table style='font-size:11px;'>"
                        + "<tr>"
                            + "<td style='padding:5px;'>Nama</td>"
                            + "<td style='border-bottom:1px dotted #000;'>"
                                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "</td>"
                        + "</tr>"
                        + "<tr>"
                            + "<td style='padding:5px;'>Alamat</td>"
                            + "<td style='border-bottom:1px dotted #000;'>"
                                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "</td>"
                        + "</tr>"
                        + "<tr>"
                            + "<td style='padding:5px;'>Telp</td>"
                            + "<td style='border-bottom:1px dotted #000;'>"
                                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                                + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "</td>"
                        + "</tr>"
                    + "</table><br>"
                    + "<table style='border:2px solid #000;font-size:11px;'>"
                        + "<tr>"
                            + "<th style='padding:5px;border-bottom:2px solid #000;border-right:2px solid #000;'>Terima Tanggal</th>"
                            + "<th style='padding:5px;5px;border-bottom:2px solid #000;'>Selesai Tanggal</th>"
                        + "</tr>"
                        + "<tr>"
                            + "<td style='padding:5px;border-right:2px solid #000;'>"+Formater.formatDate(entPrintData.getReceiveDate(), "dd/MM/yyyy")+"</td>"
                            + "<td style='padding:5px;'>"+Formater.formatDate(entPrintData.getDueDate(), "dd/MM/yyyy")+"</td>"
                        + "</tr>"
                    + "</table>"
                + "</div>"
                + "<p align='center'>"
                    + "<img src='"+entPrintData.getCompLogo()+"' style='align:center' width='100'><br>"
                    + entPrintData.getCompAddress()+" Telp. "+entPrintData.getCompPhone()+" Fax. "+entPrintData.getCompFax()+"<br>"
                    + "Email : "+entPrintData.getCompEmail()
                + "</p>"
                + "<div>"
                    + "<h4>NO "+entPrintData.getInvNo()+"</h4>"
                + "</div>"
            + "</div>"
        + "</div>";
        return html;
    }
    
    private String header1(){
        String html = "";
        html +=""
        + "<div class='row'>"
            + "<div class='col-md-12' style='font-size:11px; padding-top: 0px;padding-bottom: 0px;'>"
                + "<h4>FAKTUR PENJUALAN</h4>"
                + "<table class='table' style='font-size:11px;border:none;'>"
                    + "<tr>"
                        + "<td colspan='3' style='border:none; padding-top: 0px;padding-bottom: 0px;'>"+entPrintData.getCompName()+"</td>"
                        + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;'>Pelanggan : </td>"
                        + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;'>"+entPrintData.getCostumer()+" / Inv :"+entPrintData.getPaymentType()+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;'>Addr : "+entPrintData.getCompAddress()+"</td>"
                        + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;'>No. </td>"
                        + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;'>"+entPrintData.getInvNo()+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;'>Telp : "+entPrintData.getCompPhone()+"</td>"
                        + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;'>Tanggal. </td>"
                        + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;'>"+Formater.formatDate(entPrintData.getInvDate(), "dd/MM/yyyy")+"</td>"
                    + "</tr>"
                + "</table>"
            + "</div>"
        + "</div>";
        return html;
    }
    
    private String body(){
        String html = "";
        html +=""
        + "<div class='row'>"
            + "<div class='col-md-12'>"
                + "<table class='table table-bordered' style='font-size:11px;'>"
                    + "<tr>"
                        + "<th>No</th>"
                        + "<th>Keterangan</th>"
                        + "<th>Nama Barang</th>" 
                        + "<th>Berat</th>"
                        + "<th>Kadar %</th>"
                        + "<th>Pcs</th>"
                        + "<th>Harga</th>"
                        + "<th>Total</th>"
                    + "</tr>";
                    
                    double subtotal = 0;
                    if(!entPrintData.getDataList().isEmpty()){
                        for(int i = 0; i < entPrintData.getDataList().size(); i++){
                            EntDataList entDataList = (EntDataList) entPrintData.getDataList().get(i);
                            double total = entDataList.getItemPrice()*entDataList.getItemQty();
                            subtotal+=total;
                            html +=""
                            + "<tr>"
                                + "<td>"+(i+1)+"</td>"
                                + "<td>"+entDataList.getItemNonte()+"</td>"
                                + "<td>"+entDataList.getItemName()+"</td>" 
                                + "<td>"+Formater.formatNumber(entDataList.getItemWeight(), "#,###")+"</td>"
                                + "<td>"+Formater.formatNumber(entDataList.getItemKadar(), "#,###")+"</td>"
                                + "<td>"+entDataList.getItemQty()+"</td>"
                                + "<td align='right'>"+Formater.formatNumber(entDataList.getItemPrice(),"#,###")+"</td>"
                                + "<td align='right'>"+Formater.formatNumber(total, "#,###")+"</td>"
                            + "</tr>";
                        }
                    }
                    
                    double grandTotal = subtotal + 2000000 + entPrintData.getDataAddCharge();
                    html+=""
                    + "<tr>"
                        + "<td colspan='6' style='border:none;' rowspan='6'>"
                            + "<table width='100%' border='0' style='font-size:11px;border:0px solid #fff;'>"
                                + "<tr>"
                                    + "<td align='center' style='norder:none;'>"
                                        + "Customer<br><br><br><br><br><br><br><br><br><br><br>"
                                        + "(_______________________)"
                                    + "</td>"
                                    + "<td align='center' style='norder:none;'>"
                                        + "Penerima<br><br><br><br><br><br><br><br><br><br><br>"
                                        + "(_______________________)"
                                    + "</td>"
                                + "</tr>"
                            + "</table>"
                        + "</td>"
                        + "<td align='right' style='border:none;'>Jumlah</td>"
                        + "<td align='right'>"+Formater.formatNumber(subtotal, "#,###")+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td align='right' style='border:none;'>Ongkos</td>"
                        + "<td align='right'>"+Formater.formatNumber(2000000, "#,###")+"</td>" //CARA DAPETIN ONGKOS BAGAIMANA?
                    + "</tr>"
                    + "<tr>"
                        + "<td align='right' style='border:none;'>Uang Muka</td>"
                        + "<td align='right'>"+Formater.formatNumber(entPrintData.getDeposit(), "#,###")+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td align='right' style='border:none;'>Additional Charge</td>"
                        + "<td align='right'>"+Formater.formatNumber(entPrintData.getDataAddCharge(), "#,###")+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td align='right' style='border:none;'>Total</td>"
                        + "<td align='right'>"+Formater.formatNumber(grandTotal, "#,###")+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td align='right' style='border:none;'>Sisa</td>"
                        + "<td align='right'>"+Formater.formatNumber(grandTotal-entPrintData.getDeposit(), "#,###")+"</td>"
                    + "</tr>";
                html += ""
                + "</table>"
            + "</div>"
        + "</div>";
        return html;
    }
    
    private String body1(){
        String html = "";
        html +=""
        + "<div class='row'>"
            + "<div class='col-md-12'>"
                + "<table class='table' style='font-size:11px;'>"
                    + "<tr>"
                        + "<th>No</th>"
                        + "<th>ID Barang</th>"
                        + "<th>Nama Barang</th>" 
                        + "<th>Satuan</th>"
                        + "<th>Harga</th>"
                        + "<th>Jumlah</th>"
                        + "<th>Total</th>"
                        + "<th>Diskon</th>"
                        + "<th>Total Net</th>"
                    + "</tr>";
                    
                    double subtotal = 0;
                    double totalQty = 0;
                    if(!entPrintData.getDataList().isEmpty()){
                        for(int i = 0; i < entPrintData.getDataList().size(); i++){
                            EntDataList entDataList = (EntDataList) entPrintData.getDataList().get(i);
                            double total = (entDataList.getItemPrice()*entDataList.getItemQty())-(entDataList.getItemDisc()*entDataList.getItemQty());
                            subtotal+=total;
                            totalQty += entDataList.getItemQty();
                            html +=""
                            + "<tr>"
                                + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;'>"+(i+1)+"</td>"
                                + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;'>"+entDataList.getItemId()+"</td>"
                                + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;' >"+entDataList.getItemName()+"</td>" 
                                + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;' >"+entDataList.getItemQtyType()+"</td>"
                                + "<td align='right' style='border:none; padding-top: 0px;padding-bottom: 0px;'>"+Formater.formatNumber(entDataList.getItemPrice(), "#,###")+"</td>"
                                + "<td aling='right' style='border:none; padding-top: 0px;padding-bottom: 0px;'>"+entDataList.getItemQty()+"</td>"
                                + "<td align='right' style='border:none; padding-top: 0px;padding-bottom: 0px;' >"+Formater.formatNumber(entDataList.getItemPrice()*entDataList.getItemQty(),"#,###")+"</td>"
                                + "<td align='right' style='border:none; padding-top: 0px;padding-bottom: 0px;'>"+Formater.formatNumber(entDataList.getItemDisc(), "#,###")+"</td>"
                                + "<td align='right' style='border:none; padding-top: 0px;padding-bottom: 0px;'>"+Formater.formatNumber(total, "#,###")+"</td>"
                            + "</tr>";
                        }
                    }
                    
                    double grandTotal = subtotal /*- entPrintData.getDataDiscTotal()*/ + entPrintData.getDataAdm() + entPrintData.getDataTransport() + entPrintData.getDataTaxTotal();
                    double discTotal = 0;
					// dibalik aja ya, kadang yg persen aneh
					
                    /*if(entPrintData.getDiscPct() > 0){
                        discTotal = grandTotal*(entPrintData.getDiscPct()/100);
                    }else{
                        discTotal = entPrintData.getDataDiscTotal();
                    }*/
					
					if(entPrintData.getDataDiscTotal() > 0){
						discTotal = entPrintData.getDataDiscTotal();
                    }else{
                        discTotal = grandTotal*(entPrintData.getDiscPct()/100);
                    }
                    grandTotal -=discTotal;
					double sisa = grandTotal-entPrintData.getPaid();
                    html+=""
                    + "<tr>"
                        + "<td colspan='4' style='border:none; padding-top: 0px;padding-bottom: 0px;' >"
                        + "</td>"
                        + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;' >"
                            + "Total"
                        + "</td>"
                        + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;' >"+totalQty+"</td>"
                        + "<td align='right' colspan='3' style='border:none; padding-top: 0px;padding-bottom: 0px;' >"+Formater.formatNumber(subtotal, "#,###")+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td colspan='7' rowspan='7' style='border:none; padding-top: 0px;padding-bottom: 0px;'>"
//                            + "Tanggal Jatuh Tempo : "+Formater.formatDate(entPrintData.getDueDate(), "dd/MM/yyyy")+"<br>"
                            + "Keterangan : "+entPrintData.getNote()
                        + "</td>"
                        + "<td style='border:none; padding-top: 0px;padding-bottom: 0px;' >Diskon</td>"
                        + "<td align='right' style='border:none; padding-top: 0px;padding-bottom: 0px;' >"+Formater.formatNumber(discTotal, "#,###")+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;'>PPn</td>"
                        + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px; ' align='right'>"+Formater.formatNumber(entPrintData.getDataTaxTotal(), "#,###")+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;'>Admin Fee</td>"
                        + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px; ' align='right'>"+Formater.formatNumber(entPrintData.getDataAdm(), "#,###")+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;'>Transport Fee</td>"
                        + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px; ' align='right'>"+Formater.formatNumber(entPrintData.getDataTransport(), "#,###")+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;'><b>Grand Total</b></td>"
                        + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;' align='right'><b>"+Formater.formatNumber(grandTotal, "#,###")+"</b></td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;'>Pembayaran</td>"
                        + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;' align='right'>"+Formater.formatNumber(entPrintData.getPaid(), "#,###")+"</td>"
                    + "</tr>"
                    + "<tr>"
                        + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;'>"+(sisa < 0 ? "Kembalian" : "Sisa")+"</td>"
                        + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;' align='right'>"+(sisa < 0 ? Formater.formatNumber(entPrintData.getPaid()-grandTotal, "#,###") : Formater.formatNumber(grandTotal-entPrintData.getPaid(), "#,###"))+"</td>"
                    + "</tr>";
                html += ""
                + "</table>"
            + "</div>"
        + "</div>";
        return html;
    }
    
    private String footer(){
        String html = "";
        return html;
    }
    
    private String footer1(){
        String html = "";
        html+=""
        + "<table class='table' style='font-size:11px;  padding-top: 0px;padding-bottom: 0px;'>"
            + "<tr>"
                + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;' align='center'>"
                    + "Penerima<br><br><br><br>"
                    + entPrintData.getCostumer()
                + "</td>"
                + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;' align='center'>"
                    + "Sales<br><br><br><br>"
                    + entPrintData.getReceivedBy()
                + "</td>"
                + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;' align='center'>"
                    + "Gudang<br><br><br><br>"
                    + entPrintData.getWarehouse()
                + "</td>"
                + "<td style='border:none;  padding-top: 0px;padding-bottom: 0px;' align='center'>"
                    + "<br><br><br><br>"
                    + "Dicetak Tanggal : "+Formater.formatDate(new Date(), "dd MMMM yyyy")
                + "</td>"
            + "</tr>"
        + "</table>";
        return html;
    }
}