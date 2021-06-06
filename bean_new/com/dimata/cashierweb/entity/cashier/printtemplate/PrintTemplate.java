/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.cashierweb.entity.cashier.printtemplate;

import com.dimata.cashierweb.ajax.cashier.TransactionCashierHandler;
import com.dimata.cashierweb.entity.cashier.transaction.BillMainCostum;
import com.dimata.cashierweb.entity.cashier.transaction.HotelRoom;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain;
import com.dimata.cashierweb.entity.cashier.transaction.PstHotelRoom;
import com.dimata.cashierweb.entity.cashier.transaction.PstReservation;
import com.dimata.cashierweb.entity.cashier.transaction.Reservation;
import com.dimata.cashierweb.entity.masterdata.Category;
import com.dimata.cashierweb.entity.masterdata.Material;
import com.dimata.cashierweb.entity.masterdata.MemberReg;
import com.dimata.cashierweb.entity.masterdata.PstCategory;
import com.dimata.cashierweb.entity.masterdata.PstMaterial;
import com.dimata.cashierweb.entity.masterdata.PstMemberGroup;
import com.dimata.cashierweb.entity.masterdata.PstMemberReg;
import com.dimata.cashierweb.entity.masterdata.TableRoom;
import com.dimata.common.db.DBHandler;
import com.dimata.common.db.DBResultSet;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.common.entity.logger.PstLogSysHistory;
import com.dimata.common.entity.payment.CurrencyType;
import com.dimata.common.entity.payment.PaymentSystem;
import com.dimata.common.entity.payment.PstCurrencyType;
import com.dimata.common.entity.payment.PstPaymentSystem;
import com.dimata.hanoman.entity.masterdata.Contact;
import com.dimata.hanoman.entity.masterdata.PstContact;
import com.dimata.hanoman.entity.masterdata.PstStandardRate;
import com.dimata.hanoman.entity.masterdata.StandardRate;
import com.dimata.pos.entity.balance.Balance;
import com.dimata.pos.entity.balance.CashCashier;
import com.dimata.pos.entity.balance.PstBalance;
import com.dimata.pos.entity.balance.PstCashCashier;
import com.dimata.pos.entity.billing.BillMain;
import com.dimata.pos.entity.billing.Billdetail;
import com.dimata.pos.entity.billing.PstBillDetail;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.entity.masterCashier.CashMaster;
import com.dimata.pos.entity.payment.CashCreditCard;
import com.dimata.pos.entity.payment.CashPayments;
import com.dimata.pos.entity.payment.CashPayments1;
import com.dimata.pos.entity.payment.PstCashCreditCard;
import com.dimata.pos.entity.payment.PstCashPayment;
import com.dimata.pos.entity.payment.PstCashPayment1;
import com.dimata.pos.entity.payment.PstCashReturn;
import com.dimata.posbo.entity.admin.AppUser;
import com.dimata.posbo.entity.admin.PstAppUser;
import com.dimata.posbo.entity.masterdata.Company;
//import com.dimata.posbo.entity.masterdata.Material;
import com.dimata.posbo.entity.masterdata.NotaSetting;
import com.dimata.posbo.entity.masterdata.PstCompany;
import com.dimata.posbo.entity.masterdata.PstCosting;
import com.dimata.posbo.entity.masterdata.PstNotaSetting;
import com.dimata.posbo.entity.masterdata.Sales;
import com.dimata.posbo.entity.warehouse.MatCosting;
import com.dimata.posbo.entity.warehouse.MatCostingItem;
import com.dimata.posbo.entity.warehouse.PstMatCosting;
import com.dimata.posbo.entity.warehouse.PstMatCostingItem;
import com.dimata.system.entity.PstSystemProperty;
import com.dimata.util.Formater;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

/**
 *
 * @author Ardiadi
 */
public class PrintTemplate {

    private double heightTotalRow = 0;
    private double heightTotalRowItem = 0;
    private double heightRow = 0.3;
    private double paperHeights = 10;
    private long oidPay = 0;
    private String paymentTypes = "";
    private String displayPaymentTypes = "";
    private String ccNames = "";
    private String ccNumbers = "";
    private String ccBanks = "";
    private String ccValids = "";
    private double ccCharges = 0;
    private double payAmounts = 0;
    private String printTypes = "";

    //PRINT PAYMENT / RETURN / COSTING
    public String PrintTemplate(BillMainCostum billMainCostum, String nameInvoice, String invoiceNumb, String cashierName, BillMain billMain, TableRoom tableRoom, String printType, String paymentType, String displayPaymentSystem, double payAmount, long oidPaymentSystem, String ccName, String ccNumber, String ccValid, String ccBank, double ccCharge, String approot, String oidMember) {
        //DATE TYPE
        Date dateNow = new Date();

        this.oidPay = oidPaymentSystem;
        this.paymentTypes = paymentType;
        this.displayPaymentTypes = displayPaymentSystem;
        this.ccNames = ccName;
        this.ccNumbers = ccNumber;
        this.ccBanks = ccBank;
        this.ccValids = ccValid;
        this.ccCharges = ccCharge;
        this.payAmounts = payAmount;
        this.printTypes = printType;

        //STRING TYPE
        String datePrint = Formater.formatDate(dateNow, "yyyy-MM-dd");
        String timePrint = Formater.formatDate(dateNow, "kk:mm:ss");
        String drawPrint = "";

        //CHECK SYSTEM PROPERTY :: PAPER TYPE
        String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");

        //CHECK SYSTEM PROPERTY :: ITEM LINE
        String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");

        //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
        String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");

        //CHECK SYSTEM PROPERTY :: PAPER WIDTH
        String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");

        //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
        String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");

        //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
        String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");

        //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
        String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");

        //CHECK SYSTEM PROPERTY :: PRINT OUT TYPE
        String printOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");

             //this.paperHeights = Double.parseDouble(printPaperHeight);
        String paperWidth = "";
        String paperHeight = "";
        String headerHeight = "";
        String footerHeight = "";
        String bodyHeight = "";
        String background = "";
        //WIDTH SETTING
        if (printPaperWidth.equals("0")) {
            paperWidth = "width:7.5cm;padding:0.1cm;";
        } else {
            paperWidth = "width:" + printPaperWidth + "cm;padding:0.5cm;";
        }

        //HEIGHT SETTING
        if (printPaperHeight.equals("0")) {
            paperHeight = "height:auto;";
        } else {
            paperHeight = "height:" + printPaperHeight + "cm;";
        }

        //HEADER HEIGHT SETTING
        if (printHeaderHeight.equals("0")) {
            headerHeight = "auto";
        } else {
            headerHeight = "" + printHeaderHeight + "cm";
        }

        //FOOTER HEIGHT SETTING
        if (printFooterHeight.equals("0")) {
            footerHeight = "auto";
        } else {
            footerHeight = "" + printFooterHeight + "cm";
        }

        if (printPapertType.equals("0")) {
            background = "";
        } else {
            background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url('" + approot + "/styles/cashier/img/bill.jpg');";
        }
        //String paperSetting = "style='"+background+""+paperWidth+""+paperHeight+"'";
        String paperSetting = " style=\"" + background + " " + paperWidth + " " + paperHeight + " \"";
	     //background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");

        //OBJECT TYPE
        Location location;
        try {
            location = PstLocation.fetchExc(billMain.getLocationId());
        } catch (Exception ex) {
            location = new Location();
        }

        //PRINT DEFAULT
        if (printPapertType.equals("0")) {

            drawPrint += ""
                    + "<div class='page' " + paperSetting + ">"
                    + "<div class='subPage'>";

			//HEADER CONTENT
            //check if invoice  exchange
            if (billMain.getParentId() != 0) {
                drawPrint += drawHeaderExchange(billMainCostum, nameInvoice, invoiceNumb, cashierName, datePrint, timePrint, billMain, tableRoom);
            } else {
                drawPrint += drawHeaderDefault(billMainCostum, nameInvoice, invoiceNumb, cashierName, datePrint, timePrint, billMain, tableRoom);
            }

			//BODY CONTENT
            //DEFAULT :: WITHOUT SHORT BY CATEGORY
            if (printCategoryItem.equals("0")) {

                drawPrint += drawContentDefault(billMain, printItemLine, location);

                //CUSTOM :: SHORT BY CATEGORY
            } else {

                drawPrint += drawContentCustom(billMain, printItemLine, location);

            }

            if (printType.equals("printpay") || printType.equals("printreturn")) {
                PaymentSystem paymentSystem;
                try {
                    paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
                } catch (Exception ex) {
                    paymentSystem = new PaymentSystem();
                }

                drawPrint += drawPayment(paymentType, displayPaymentSystem, paymentSystem, ccName, ccNumber, ccBank, ccValid, ccCharge, payAmount, billMain, location, printType);
            }

            //FOOTER CONTENT
            drawPrint += drawFootrDefault(billMain, approot, oidMember);

            drawPrint += ""
                    + "</div>"
                    + "</div>";

            //PRINT CUSTOM
        } else {

            drawPrint += ""
                    + "<div class='page' " + paperSetting + ">"
                    + "<div class='subPage'>";
            //HEADER CONTENT
            drawPrint += ""
                    + "<div style='height:" + headerHeight + ";'>";
            //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
            if (printOutType.equals("0")) {
                drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, invoiceNumb, cashierName, datePrint, timePrint, billMain, tableRoom);
            }
            drawPrint += ""
                    + "</div>";

            drawPrint += ""
                    + "<div style='height:10cm;'>";
            //DEFAULT :: WITHOUT SHORT BY CATEGORY
            if (printCategoryItem.equals("0")) {
                drawPrint += drawContentDefault(billMain, printItemLine, location);
                //CUSTOM :: SHORT BY CATEGORY
            } else {
                drawPrint += drawContentCustom(billMain, printItemLine, location);
            }
            drawPrint += ""
                    + "</div>";

            drawPrint += ""
                    + "<div style='height:" + footerHeight + ";'>";
            //FOOTER CONTENT
            drawPrint += drawFootrCustom(billMain);

            drawPrint += ""
                    + "</div>";

            drawPrint += ""
                    + "</div>"
                    + "</div>";

            if (printType.equals("printpay") || printType.equals("printreturn")) {
                ///PAYMENT 
                double remind = this.paperHeights - heightTotalRow;
                if (remind < 5) {
                    drawPrint += ""
                            + "<div class='page' " + paperSetting + ">"
                            + "<div class='subPage'>"
                            + "<div style='height:" + headerHeight + ";'>";
                    //HEADER CONTENT
                    drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, invoiceNumb, cashierName, datePrint, timePrint, billMain, tableRoom);
                    drawPrint += ""
                            + "</div>";

                    PaymentSystem paymentSystem;
                    try {
                        paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
                    } catch (Exception ex) {
                        paymentSystem = new PaymentSystem();
                    }

                    drawPrint += ""
                            + "<div style='height:10cm;'>";

                    drawPrint += drawPayment(paymentType, displayPaymentSystem, paymentSystem, ccName, ccNumber, ccBank, ccValid, ccCharge, payAmount, billMain, location, printType);
                    drawPrint += ""
                            + "</div>";

                    drawPrint += ""
                            + "<div style='height:" + footerHeight + "'>";
                    //FOOTER CONTENT
                    drawPrint += drawFootrCustom(billMain);
                    drawPrint += ""
                            + "</div>";

                    drawPrint += ""
                            + "</div>"
                            + "</div>";
                }
            }
        }
        return drawPrint;
    }

     
     public String PrintTemplateContinuesPayment(BillMainCostum billMainCostum, 
	     String nameInvoice, String invoiceNumb, String cashierName, 
	     BillMain billMain, TableRoom tableRoom, String printType,
	     String paymentType, String displayPaymentSystem, double payAmount, 
	     long oidPaymentSystem, String ccName, String ccNumber, String ccValid, 
	     String ccBank, double ccCharge, String approot, String oidMember){
	     
	     //DATE TYPE
	     Date dateNow = new Date();
             
             this.oidPay = oidPaymentSystem;
             this.paymentTypes = paymentType;
             this.displayPaymentTypes = displayPaymentSystem;
             this.ccNames= ccName;  
             this.ccNumbers = ccNumber;
             this.ccBanks = ccBank;
             this.ccValids = ccValid;
             this.ccCharges = ccCharge;
             this.payAmounts = payAmount;
             this.printTypes = printType;
	     
	     //STRING TYPE
	     String datePrint = Formater.formatDate(dateNow, "yyyy-MM-dd");
	     String timePrint = Formater.formatDate(dateNow, "kk:mm:ss");
	     String drawPrint = "";
	     
	     //CHECK SYSTEM PROPERTY :: PAPER TYPE
	     String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
	     
	     //CHECK SYSTEM PROPERTY :: ITEM LINE
	     String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");
	     
	     //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
	     String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");
	     
	     //CHECK SYSTEM PROPERTY :: PAPER WIDTH
	     String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");
	     
	     //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
	     String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");
             
             //CHECK SYSTEM PROPERTY :: PRINT OUT TYPE
             String printOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");

             //this.paperHeights = Double.parseDouble(printPaperHeight);
	     
	     String paperWidth = "";
	     String paperHeight = "";
	     String headerHeight = "";
	     String footerHeight = "";
	     String bodyHeight = "";
	     String background = "";
	     //WIDTH SETTING
	     if(printPaperWidth.equals("0")){
		 paperWidth = "width:7.5cm;padding:0.1cm;";
	     }else{
		 paperWidth = "width:"+printPaperWidth+"cm;padding:0.5cm;";
	     }
	     
	     //HEIGHT SETTING
	     if(printPaperHeight.equals("0")){
		 paperHeight = "height:auto;";
	     }else{
		 paperHeight = "height:"+printPaperHeight+"cm;";
	     }
	     
	     //HEADER HEIGHT SETTING
	     if(printHeaderHeight.equals("0")){
		 headerHeight = "auto";
	     }else{
		 headerHeight = ""+printHeaderHeight+"cm";
	     }
	     
	     //FOOTER HEIGHT SETTING
	     if(printFooterHeight.equals("0")){
		 footerHeight = "auto";
	     }else{
		 footerHeight = ""+printFooterHeight+"cm";
	     }
	     
	     
	     if(printPapertType.equals("0")){
		 background = "";
	     }else{
		 background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url('"+approot+"/styles/cashier/img/bill.jpg');";
	     }
	     //String paperSetting = "style='"+background+""+paperWidth+""+paperHeight+"'";
             String paperSetting = " style=\""+background+" "+paperWidth+" "+paperHeight+" \"";
	     //background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");
	     
	     //OBJECT TYPE
	     Location location;
	     try{
		 location = PstLocation.fetchExc(billMain.getLocationId());
	     }catch(Exception ex){
		 location = new Location();
	     }
	     
	     //PRINT DEFAULT
	     if(printPapertType.equals("0")){
		 //disini
		 drawPrint+= ""
		    + "<div class='page' "+paperSetting+">"
			+ "<div class='subPage'>";
		 
			//HEADER CONTENT
			drawPrint+=drawHeaderDefaultContinuesPayment(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);


			//BODY CONTENT
			//DEFAULT :: WITHOUT SHORT BY CATEGORY
			if(printCategoryItem.equals("0")){


			    drawPrint += drawContentDefaultContinuesPayment(billMain, printItemLine, location);

		       //CUSTOM :: SHORT BY CATEGORY
			}else{

			    drawPrint += drawContentCustomContinuesPayment(billMain, printItemLine, location);

			}
			
			if(printType.equals("printpay") || printType.equals("printreturn")){
			    PaymentSystem paymentSystem;
			    try{
				paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
			    }catch(Exception ex){
				paymentSystem = new PaymentSystem();
			    }
			    
			    drawPrint+=drawPaymentContinues(paymentType, displayPaymentSystem, 
				    paymentSystem, ccName, ccNumber, ccBank, 
				    ccValid, ccCharge, payAmount, billMain, 
				    location, printType);
			}


			//FOOTER CONTENT
			drawPrint+=drawFootrDefault(billMain, approot, oidMember);
			
			
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		 
		 
	    //PRINT CUSTOM
	     }else{
		 
		 drawPrint+=""
		 + "<div class='page' "+paperSetting+">"
		    + "<div class='subPage'>";
			//HEADER CONTENT
			drawPrint += ""
			+ "<div style='height:"+headerHeight+";'>";
                        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
                        if (printOutType.equals("0")){
                            drawPrint += drawHeaderCustomContinuesPayment(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);
                        }
                        drawPrint +=""
			+ "</div>";

                        drawPrint +=""
                        + "<div style='height:10cm;'>";
                        //DEFAULT :: WITHOUT SHORT BY CATEGORY
                        if(printCategoryItem.equals("0")){
                            drawPrint += drawContentDefaultContinuesPayment(billMain, printItemLine, location);
                       //CUSTOM :: SHORT BY CATEGORY
                        }else{
                            drawPrint += drawContentCustomContinuesPayment(billMain, printItemLine, location);
                        }
                        drawPrint +=""
                        + "</div>";

                        drawPrint += ""
			+ "<div style='height:"+footerHeight+";'>";			
			//FOOTER CONTENT
			drawPrint += drawFootrCustom(billMain);
			
			drawPrint +=""
			+ "</div>";
	     
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		    
		if(printType.equals("printpay") || printType.equals("printreturn")){  
		    ///PAYMENT 
                    double remind= this.paperHeights-heightTotalRow;
                    if (remind<5){
                        drawPrint+=""
                        + "<div class='page' "+paperSetting+">"
                           + "<div class='subPage'>"
                               + "<div style='height:"+headerHeight+";'>";
                                   //HEADER CONTENT
                                   drawPrint += drawHeaderCustomContinuesPayment(billMainCostum, nameInvoice, 
                                           invoiceNumb, cashierName, datePrint, timePrint, 
                                           billMain, tableRoom);
                               drawPrint += ""
                               + "</div>";


                               PaymentSystem paymentSystem;
                               try{
                                   paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
                               }catch(Exception ex){
                                   paymentSystem = new PaymentSystem();
                               }

                               drawPrint+=""
                               + "<div style='height:10cm;'>";

                                   drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
                                           paymentSystem, ccName, ccNumber, ccBank, 
                                           ccValid, ccCharge, payAmount, billMain, 
                                           location, printType);
                               drawPrint+=""
                               + "</div>";


                               drawPrint+=""
                               + "<div style='height:"+footerHeight+"'>";
                                   //FOOTER CONTENT
                                   drawPrint += drawFootrCustom(billMain);
                               drawPrint +=""
                               + "</div>";


                           drawPrint+=""
                           + "</div>"
                       + "</div>";
                    }
		    
		}
	    }
		    
	    return drawPrint;
     }
     
     
     public String PrintTemplatePaymentOnly(BillMainCostum billMainCostum, 
	     String nameInvoice, String invoiceNumb, String cashierName, 
	     BillMain billMain, TableRoom tableRoom, String printType,
	     String paymentType, String displayPaymentSystem, double payAmount, 
	     long oidPaymentSystem, String ccName, String ccNumber, String ccValid, 
	     String ccBank, double ccCharge, String approot, String oidMember){
	     
	     //DATE TYPE
	     Date dateNow = new Date();
             
             this.oidPay = oidPaymentSystem;
             this.paymentTypes = paymentType;
             this.displayPaymentTypes = displayPaymentSystem;
             this.ccNames= ccName;  
             this.ccNumbers = ccNumber;
             this.ccBanks = ccBank;
             this.ccValids = ccValid;
             this.ccCharges = ccCharge;
             this.payAmounts = payAmount;
             this.printTypes = printType;
	     
	     //STRING TYPE
	     String datePrint = Formater.formatDate(dateNow, "yyyy-MM-dd");
	     String timePrint = Formater.formatDate(dateNow, "kk:mm:ss");
	     String drawPrint = "";
	     
	     //CHECK SYSTEM PROPERTY :: PAPER TYPE
	     String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
	     
	     //CHECK SYSTEM PROPERTY :: ITEM LINE
	     String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");
	     
	     //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
	     String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");
	     
	     //CHECK SYSTEM PROPERTY :: PAPER WIDTH
	     String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");
	     
	     //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
	     String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");
             
             //CHECK SYSTEM PROPERTY :: PRINT OUT TYPE
             String printOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");

             //this.paperHeights = Double.parseDouble(printPaperHeight);
	     
	     String paperWidth = "";
	     String paperHeight = "";
	     String headerHeight = "";
	     String footerHeight = "";
	     String bodyHeight = "";
	     String background = "";
	     //WIDTH SETTING
	     if(printPaperWidth.equals("0")){
		 paperWidth = "width:7.5cm;padding:0.1cm;";
	     }else{
		 paperWidth = "width:"+printPaperWidth+"cm;padding:0.5cm;";
	     }
	     
	     //HEIGHT SETTING
	     if(printPaperHeight.equals("0")){
		 paperHeight = "height:auto;";
	     }else{
		 paperHeight = "height:"+printPaperHeight+"cm;";
	     }
	     
	     //HEADER HEIGHT SETTING
	     if(printHeaderHeight.equals("0")){
		 headerHeight = "auto";
	     }else{
		 headerHeight = ""+printHeaderHeight+"cm";
	     }
	     
	     //FOOTER HEIGHT SETTING
	     if(printFooterHeight.equals("0")){
		 footerHeight = "auto";
	     }else{
		 footerHeight = ""+printFooterHeight+"cm";
	     }
	     
	     
	     if(printPapertType.equals("0")){
		 background = "";
	     }else{
		 background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url('"+approot+"/styles/cashier/img/bill.jpg');";
	     }
	     //String paperSetting = "style='"+background+""+paperWidth+""+paperHeight+"'";
             String paperSetting = " style=\""+background+" "+paperWidth+" "+paperHeight+" \"";
	     //background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");
	     
	     //OBJECT TYPE
	     Location location;
	     try{
		 location = PstLocation.fetchExc(billMain.getLocationId());
	     }catch(Exception ex){
		 location = new Location();
	     }
	     
	     //PRINT DEFAULT
	     if(printPapertType.equals("0")){
		 
		 drawPrint+= ""
		    + "<div class='page' "+paperSetting+">"
			+ "<div class='subPage'>";
                       
			//HEADER CONTENT
			drawPrint+=drawHeaderDefault(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);


			//BODY CONTENT


			//DEFAULT :: WITHOUT SHORT BY CATEGORY
			if(printCategoryItem.equals("0")){


			    //drawPrint += drawContentDefault(billMain, printItemLine, location);

		       //CUSTOM :: SHORT BY CATEGORY
			}else{

			    //drawPrint += drawContentCustom(billMain, printItemLine, location);

			}
			
			if(printType.equals("printpay") || printType.equals("printreturn")){
			    PaymentSystem paymentSystem;
			    try{
				paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
			    }catch(Exception ex){
				paymentSystem = new PaymentSystem();
			    }
			    
			    drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
				    paymentSystem, ccName, ccNumber, ccBank, 
				    ccValid, ccCharge, payAmount, billMain, 
				    location, printType);
			}


			//FOOTER CONTENT
			drawPrint+=drawFootrDefault(billMain, approot, oidMember);
			
			
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		 
		 
	    //PRINT CUSTOM
	     }else{
		 
		 drawPrint+=""
		 + "<div class='page' "+paperSetting+">"
		    + "<div class='subPage'>";
                        //HEADER CONTENT
			drawPrint += ""
			+ "<div style='height:"+headerHeight+";'>";
                        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
                        if (printOutType.equals("0")){
                            drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);
                        }
                        drawPrint +=""
			+ "</div>";
                        
                        if(printType.equals("printpay") || printType.equals("printreturn")){  
                            ///PAYMENT 
                            double remind= this.paperHeights-heightTotalRow;
                            //if (remind<5){
                                drawPrint+="";
                                //+ "<div class='page' "+paperSetting+">"
                                   //+ "<div class='subPage'>";
                                       //+ "<div style='height:"+headerHeight+";'>";
                                           //HEADER CONTENT
                                           //drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
                                                   //invoiceNumb, cashierName, datePrint, timePrint, 
                                                   //billMain, tableRoom);
                                       //drawPrint += ""
                                       //+ "</div>";


                                       PaymentSystem paymentSystem;
                                       try{
                                           paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
                                       }catch(Exception ex){
                                           paymentSystem = new PaymentSystem();
                                       }

                                       drawPrint+=""
                                       + "<div style='height:10cm;'>";

                                           drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
                                                   paymentSystem, ccName, ccNumber, ccBank, 
                                                   ccValid, ccCharge, payAmount, billMain, 
                                                   location, printType);
                                       drawPrint+=""
                                       + "</div>";


                                       drawPrint+=""
                                       + "<div style='height:"+footerHeight+"'>";
                                           //FOOTER CONTENT
                                           drawPrint += drawFootrCustom(billMain);
                                       drawPrint +=""
                                       + "</div>";


                                   drawPrint+="";
                                  
                           // }

                        }
                        
			//HEADER CONTENT
			//drawPrint += ""
			//+ "<div style='height:"+headerHeight+";'>";
                        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
                        //if (printOutType.equals("0")){
                            //drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
				//invoiceNumb, cashierName, datePrint, timePrint, 
				//billMain, tableRoom);
                        //}
                        //drawPrint +=""
			//+ "</div>";

                        /*drawPrint +=""
                        + "<div style='height:10cm;'>";
                        //DEFAULT :: WITHOUT SHORT BY CATEGORY
                        if(printCategoryItem.equals("0")){
                            //drawPrint += drawContentDefault(billMain, printItemLine, location);
                       //CUSTOM :: SHORT BY CATEGORY
                        }else{
                            //drawPrint += drawContentCustom(billMain, printItemLine, location);
                        }
                        drawPrint +=""
                        + "</div>";*/
                        /*
                        drawPrint += ""
			+ "<div style='height:"+footerHeight+";'>";			
			//FOOTER CONTENT
			drawPrint += drawFootrCustom(billMain);
			
			drawPrint +=""
			+ "</div>";
                        */
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		    
		
	    }
		    
	    return drawPrint;
     }
     public String PrintTemplateCompliment(BillMainCostum billMainCostum, 
	     String nameInvoice, String invoiceNumb, String cashierName, 
	     BillMain billMain, TableRoom tableRoom, String printType,
	     String paymentType, String displayPaymentSystem, double payAmount, 
	     long oidPaymentSystem, String ccName, String ccNumber, String ccValid, 
	     String ccBank, double ccCharge, String approot, String oidMember){
	     
	     //DATE TYPE
	     Date dateNow = new Date();
             
             this.oidPay = oidPaymentSystem;
             this.paymentTypes = paymentType;
             this.displayPaymentTypes = displayPaymentSystem;
             this.ccNames= ccName;  
             this.ccNumbers = ccNumber;
             this.ccBanks = ccBank;
             this.ccValids = ccValid;
             this.ccCharges = ccCharge;
             this.payAmounts = payAmount;
             this.printTypes = printType;
	     
	     //STRING TYPE
	     String datePrint = Formater.formatDate(dateNow, "yyyy-MM-dd");
	     String timePrint = Formater.formatDate(dateNow, "kk:mm:ss");
	     String drawPrint = "";
	     
	     //CHECK SYSTEM PROPERTY :: PAPER TYPE
	     String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
	     
	     //CHECK SYSTEM PROPERTY :: ITEM LINE
	     String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");
	     
	     //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
	     String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");
	     
	     //CHECK SYSTEM PROPERTY :: PAPER WIDTH
	     String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");
	     
	     //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
	     String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");
             
             //CHECK SYSTEM PROPERTY :: PRINT OUT TYPE
             String printOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");

             //this.paperHeights = Double.parseDouble(printPaperHeight);
	     
	     String paperWidth = "";
	     String paperHeight = "";
	     String headerHeight = "";
	     String footerHeight = "";
	     String bodyHeight = "";
	     String background = "";
	     //WIDTH SETTING
	     if(printPaperWidth.equals("0")){
		 paperWidth = "width:7.5cm;padding:0.1cm;";
	     }else{
		 paperWidth = "width:"+printPaperWidth+"cm;padding:0.5cm;";
	     }
	     
	     //HEIGHT SETTING
	     if(printPaperHeight.equals("0")){
		 paperHeight = "height:auto;";
	     }else{
		 paperHeight = "height:"+printPaperHeight+"cm;";
	     }
	     
	     //HEADER HEIGHT SETTING
	     if(printHeaderHeight.equals("0")){
		 headerHeight = "auto";
	     }else{
		 headerHeight = ""+printHeaderHeight+"cm";
	     }
	     
	     //FOOTER HEIGHT SETTING
	     if(printFooterHeight.equals("0")){
		 footerHeight = "auto";
	     }else{
		 footerHeight = ""+printFooterHeight+"cm";
	     }
	     
	     
	     if(printPapertType.equals("0")){
		 background = "";
	     }else{
		 background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url('"+approot+"/styles/cashier/img/bill.jpg');";
	     }
	     //String paperSetting = "style='"+background+""+paperWidth+""+paperHeight+"'";
             String paperSetting = " style=\""+background+" "+paperWidth+" "+paperHeight+" \"";
	     //background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");
	     
	     //OBJECT TYPE
	     Location location;
	     try{
		 location = PstLocation.fetchExc(billMain.getLocationId());
	     }catch(Exception ex){
		 location = new Location();
	     }
	     
	     //PRINT DEFAULT
	     if(printPapertType.equals("0")){
		 
		 drawPrint+= ""
		    + "<div class='page' "+paperSetting+">"
			+ "<div class='subPage'>";
		 
			//HEADER CONTENT
			drawPrint+=drawHeaderDefault(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);


			//BODY CONTENT


			//DEFAULT :: WITHOUT SHORT BY CATEGORY
			if(printCategoryItem.equals("0")){


			    drawPrint += drawContentDefaultCompliment(billMain, printItemLine, location);

		       //CUSTOM :: SHORT BY CATEGORY
			}else{

			    drawPrint += drawContentCustom(billMain, printItemLine, location);

			}
			
			if(printType.equals("printpay") || printType.equals("printreturn")){
			    PaymentSystem paymentSystem;
			    try{
				paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
			    }catch(Exception ex){
				paymentSystem = new PaymentSystem();
			    }
			    
			    //drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
				    //paymentSystem, ccName, ccNumber, ccBank, 
				    //ccValid, ccCharge, payAmount, billMain, 
				    //location, printType);
                            drawPrint+=""
                            + "<div class='row'>"
                                + "<div class='col-xs-5'>"
                                    + "Payment Type"
                                + "</div>"
                                + "<div class='col-xs-2' style='text-align:right;'>"
                                    + ": "
                                + "</div>"
                                + "<div class='col-xs-5' style='text-align:right;'>"
                                    + " "+paymentSystem.getPaymentSystem()
                                 + "</div>"
                            + "</div>"
                            + "<div class='row'>"
                                + "<div class='col-xs-5'>"
                                    + "Paid"
                                + "</div>"
                                + "<div class='col-xs-2' style='text-align:right;'>"
                                    + ": "
                                + "</div>"
                                + "<div class='col-xs-5' style='text-align:right;'>"
                                    + "0"
                                 + "</div>"
                            + "</div>"
                            + "<div class='row'>"
                                + "<div class='col-xs-5'>"
                                    + "Amount"
                                + "</div>"
                                + "<div class='col-xs-2' style='text-align:right;'>"
                                    + ": "
                                + "</div>"
                                + "<div class='col-xs-5' style='text-align:right;'>"
                                    + "0"
                                 + "</div>"
                            + "</div>"
                            + "<div class='row'>"
                                + "<div class='col-xs-5'>"
                                    + "Change"
                                + "</div>"
                                + "<div class='col-xs-2' style='text-align:right;'>"
                                    + ": "
                                + "</div>"
                                + "<div class='col-xs-5' style='text-align:right;'>"
                                    + "0"
                                 + "</div>"
                            + "</div>";
                            
			}


			//FOOTER CONTENT
			drawPrint+=drawFootrDefault(billMain, approot, oidMember);
			
			
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		 
		 
	    //PRINT CUSTOM
	     }else{
		 
		 drawPrint+=""
		 + "<div class='page' "+paperSetting+">"
		    + "<div class='subPage'>";
			//HEADER CONTENT
			drawPrint += ""
			+ "<div style='height:"+headerHeight+";'>";
                        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
                        if (printOutType.equals("0")){
                            drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);
                        }
                        drawPrint +=""
			+ "</div>";

                        drawPrint +=""
                        + "<div style='height:10cm;'>";
                        //DEFAULT :: WITHOUT SHORT BY CATEGORY
                        if(printCategoryItem.equals("0")){
                            drawPrint += drawContentDefault(billMain, printItemLine, location);
                       //CUSTOM :: SHORT BY CATEGORY
                        }else{
                            drawPrint += drawContentCustom(billMain, printItemLine, location);
                        }
                        drawPrint +=""
                        + "</div>";

                        drawPrint += ""
			+ "<div style='height:"+footerHeight+";'>";			
			//FOOTER CONTENT
			drawPrint += drawFootrCustom(billMain);
			
			drawPrint +=""
			+ "</div>";
	     
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		    
		if(printType.equals("printpay") || printType.equals("printreturn")){  
		    ///PAYMENT 
                    double remind= this.paperHeights-heightTotalRow;
                    if (remind<5){
                        drawPrint+=""
                        + "<div class='page' "+paperSetting+">"
                           + "<div class='subPage'>"
                               + "<div style='height:"+headerHeight+";'>";
                                   //HEADER CONTENT
                                   drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
                                           invoiceNumb, cashierName, datePrint, timePrint, 
                                           billMain, tableRoom);
                               drawPrint += ""
                               + "</div>";


                               PaymentSystem paymentSystem;
                               try{
                                   paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
                               }catch(Exception ex){
                                   paymentSystem = new PaymentSystem();
                               }

                               drawPrint+=""
                               + "<div style='height:10cm;'>";

                                   drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
                                           paymentSystem, ccName, ccNumber, ccBank, 
                                           ccValid, ccCharge, payAmount, billMain, 
                                           location, printType);
                               drawPrint+=""
                               + "</div>";


                               drawPrint+=""
                               + "<div style='height:"+footerHeight+"'>";
                                   //FOOTER CONTENT
                                   drawPrint += drawFootrCustom(billMain);
                               drawPrint +=""
                               + "</div>";


                           drawPrint+=""
                           + "</div>"
                       + "</div>";
                    }
		    
		}
	    }
		    
	    return drawPrint;
     }
     public String PrintTemplateComplimentPaymentOnly(BillMainCostum billMainCostum, 
	     String nameInvoice, String invoiceNumb, String cashierName, 
	     BillMain billMain, TableRoom tableRoom, String printType,
	     String paymentType, String displayPaymentSystem, double payAmount, 
	     long oidPaymentSystem, String ccName, String ccNumber, String ccValid, 
	     String ccBank, double ccCharge, String approot, String oidMember){
	     
	     //DATE TYPE
	     Date dateNow = new Date();
             
             this.oidPay = oidPaymentSystem;
             this.paymentTypes = paymentType;
             this.displayPaymentTypes = displayPaymentSystem;
             this.ccNames= ccName;  
             this.ccNumbers = ccNumber;
             this.ccBanks = ccBank;
             this.ccValids = ccValid;
             this.ccCharges = ccCharge;
             this.payAmounts = payAmount;
             this.printTypes = printType;
	     
	     //STRING TYPE
	     String datePrint = Formater.formatDate(dateNow, "yyyy-MM-dd");
	     String timePrint = Formater.formatDate(dateNow, "kk:mm:ss");
	     String drawPrint = "";
	     
	     //CHECK SYSTEM PROPERTY :: PAPER TYPE
	     String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
	     
	     //CHECK SYSTEM PROPERTY :: ITEM LINE
	     String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");
	     
	     //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
	     String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");
	     
	     //CHECK SYSTEM PROPERTY :: PAPER WIDTH
	     String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");
	     
	     //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
	     String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");
             
             //CHECK SYSTEM PROPERTY :: PRINT OUT TYPE
             String printOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");

             //this.paperHeights = Double.parseDouble(printPaperHeight);
	     
	     String paperWidth = "";
	     String paperHeight = "";
	     String headerHeight = "";
	     String footerHeight = "";
	     String bodyHeight = "";
	     String background = "";
	     //WIDTH SETTING
	     if(printPaperWidth.equals("0")){
		 paperWidth = "width:7.5cm;padding:0.1cm;";
	     }else{
		 paperWidth = "width:"+printPaperWidth+"cm;padding:0.5cm;";
	     }
	     
	     //HEIGHT SETTING
	     if(printPaperHeight.equals("0")){
		 paperHeight = "height:auto;";
	     }else{
		 paperHeight = "height:"+printPaperHeight+"cm;";
	     }
	     
	     //HEADER HEIGHT SETTING
	     if(printHeaderHeight.equals("0")){
		 headerHeight = "auto";
	     }else{
		 headerHeight = ""+printHeaderHeight+"cm";
	     }
	     
	     //FOOTER HEIGHT SETTING
	     if(printFooterHeight.equals("0")){
		 footerHeight = "auto";
	     }else{
		 footerHeight = ""+printFooterHeight+"cm";
	     }
	     
	     
	     if(printPapertType.equals("0")){
		 background = "";
	     }else{
		 background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url('"+approot+"/styles/cashier/img/bill.jpg');";
	     }
	     //String paperSetting = "style='"+background+""+paperWidth+""+paperHeight+"'";
             String paperSetting = " style=\""+background+" "+paperWidth+" "+paperHeight+" \"";
	     //background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");
	     
	     //OBJECT TYPE
	     Location location;
	     try{
		 location = PstLocation.fetchExc(billMain.getLocationId());
	     }catch(Exception ex){
		 location = new Location();
	     }
	     
	     //PRINT DEFAULT
	     if(printPapertType.equals("0")){
		 
		 drawPrint+= ""
		    + "<div class='page' "+paperSetting+">"
			+ "<div class='subPage'>";
		 
			//HEADER CONTENT
			drawPrint+=drawHeaderDefault(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);


			//BODY CONTENT


			//DEFAULT :: WITHOUT SHORT BY CATEGORY
			if(printCategoryItem.equals("0")){


			    //drawPrint += drawContentDefaultCompliment(billMain, printItemLine, location);

		       //CUSTOM :: SHORT BY CATEGORY
			}else{

			    //drawPrint += drawContentCustom(billMain, printItemLine, location);

			}
			
			if(printType.equals("printpay") || printType.equals("printreturn")){
			    PaymentSystem paymentSystem;
			    try{
				paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
			    }catch(Exception ex){
				paymentSystem = new PaymentSystem();
			    }
			    
			    //drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
				    //paymentSystem, ccName, ccNumber, ccBank, 
				    //ccValid, ccCharge, payAmount, billMain, 
				    //location, printType);
                            drawPrint+=""
                            + "<div class='row'>"
                                + "<div class='col-xs-5'>"
                                    + "Payment Type"
                                + "</div>"
                                + "<div class='col-xs-2' style='text-align:right;'>"
                                    + ": "
                                + "</div>"
                                + "<div class='col-xs-5' style='text-align:right;'>"
                                    + " "+paymentSystem.getPaymentSystem()
                                 + "</div>"
                            + "</div>"
                            + "<div class='row'>"
                                + "<div class='col-xs-5'>"
                                    + "Paid"
                                + "</div>"
                                + "<div class='col-xs-2' style='text-align:right;'>"
                                    + ": "
                                + "</div>"
                                + "<div class='col-xs-5' style='text-align:right;'>"
                                    + "0"
                                 + "</div>"
                            + "</div>"
                            + "<div class='row'>"
                                + "<div class='col-xs-5'>"
                                    + "Amount"
                                + "</div>"
                                + "<div class='col-xs-2' style='text-align:right;'>"
                                    + ": "
                                + "</div>"
                                + "<div class='col-xs-5' style='text-align:right;'>"
                                    + "0"
                                 + "</div>"
                            + "</div>"
                            + "<div class='row'>"
                                + "<div class='col-xs-5'>"
                                    + "Change"
                                + "</div>"
                                + "<div class='col-xs-2' style='text-align:right;'>"
                                    + ": "
                                + "</div>"
                                + "<div class='col-xs-5' style='text-align:right;'>"
                                    + "0"
                                 + "</div>"
                            + "</div>";
                            
			}


			//FOOTER CONTENT
			drawPrint+=drawFootrDefault(billMain, approot, oidMember);
			
			
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		 
		 
	    //PRINT CUSTOM
	     }else{
		 
		 drawPrint+=""
		 + "<div class='page' "+paperSetting+">"
		    + "<div class='subPage'>";
			//HEADER CONTENT
			drawPrint += "";
			//+ "<div style='height:"+headerHeight+";'>";
                        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
                        if (printOutType.equals("0")){
                            drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);
                        }
                        if(printType.equals("printpay") || printType.equals("printreturn")){  
                        ///PAYMENT 
                        double remind= this.paperHeights-heightTotalRow;
                        //if (remind<5){
                            drawPrint+="";
                            //+ "<div class='page' "+paperSetting+">"
                               //+ "<div class='subPage'>";
                                   //+ "<div style='height:"+headerHeight+";'>";
                                       //HEADER CONTENT
                                      // drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
                                        //       invoiceNumb, cashierName, datePrint, timePrint, 
                                          //     billMain, tableRoom);
                                   //drawPrint += ""
                                   //+ "</div>";


                                   PaymentSystem paymentSystem;
                                   try{
                                       paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
                                   }catch(Exception ex){
                                       paymentSystem = new PaymentSystem();
                                   }

                                   drawPrint+="";
                                   //+ "<div style='height:10cm;'>";

                                       drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
                                               paymentSystem, ccName, ccNumber, ccBank, 
                                               ccValid, ccCharge, payAmount, billMain, 
                                               location, printType);
                                   drawPrint+="";
                                   //+ "</div>";


                                   drawPrint+=""
                                   + "<div style='height:"+footerHeight+"'>";
                                       //FOOTER CONTENT
                                       drawPrint += drawFootrCustom(billMain);
                                   drawPrint +=""
                                   + "</div>";


                               //drawPrint+=""
                               //+ "</div>"
                           //+ "</div>";
                        //}

                    }
                        //drawPrint +=""
			///+ "</div>";

                        //drawPrint +=""
                        //+ "<div style='height:10cm;'>";
                        //DEFAULT :: WITHOUT SHORT BY CATEGORY
                        //if(printCategoryItem.equals("0")){
                            //drawPrint += drawContentDefault(billMain, printItemLine, location);
                       //CUSTOM :: SHORT BY CATEGORY
                        //}else{
                           // drawPrint += drawContentCustom(billMain, printItemLine, location);
                        //}
                        //drawPrint +=""
                        //+ "</div>";

                        //drawPrint += ""
			//+ "<div style='height:"+footerHeight+";'>";			
			//FOOTER CONTENT
			//drawPrint += drawFootrCustom(billMain);
			
			//drawPrint +=""
			//+ "</div>";
	     
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		    
		
	    }
		    
	    return drawPrint;
     }
     
     public String PrintTemplateNew(BillMainCostum billMainCostum, 
	     String nameInvoice, String invoiceNumb, String cashierName, 
	     BillMain billMain, TableRoom tableRoom, String printType,
	     String paymentType, String displayPaymentSystem, double payAmount, 
	     long oidPaymentSystem, String ccName, String ccNumber, String ccValid, 
	     String ccBank, double ccCharge, String approot, String oidMember,double tax, double service,double discPct){
	     
	     //DATE TYPE
	     Date dateNow = new Date();
	     
	     //STRING TYPE
	     String datePrint = Formater.formatDate(dateNow, "yyyy-MM-dd");
	     String timePrint = Formater.formatDate(dateNow, "kk:mm:ss");
	     String drawPrint = "";
	     
	     //CHECK SYSTEM PROPERTY :: PAPER TYPE
	     String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
	     
	     //CHECK SYSTEM PROPERTY :: ITEM LINE
	     String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");
	     
	     //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
	     String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");
	     
	     //CHECK SYSTEM PROPERTY :: PAPER WIDTH
	     String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");
	     
	     //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
	     String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");
	     
	     String paperWidth = "";
	     String paperHeight = "";
	     String headerHeight = "";
	     String footerHeight = "";
	     String bodyHeight = "";
	     String background = "";
	     //WIDTH SETTING
	     if(printPaperWidth.equals("0")){
		 paperWidth = "width:7.5cm;padding:0.1cm;";
	     }else{
		 paperWidth = "width:"+printPaperWidth+"cm;padding:0.5cm;";
	     }
	     
	     //HEIGHT SETTING
	     if(printPaperHeight.equals("0")){
		 paperHeight = "height:auto;";
	     }else{
		 paperHeight = "height:"+printPaperHeight+"cm;";
	     }
	     
	     //HEADER HEIGHT SETTING
	     if(printHeaderHeight.equals("0")){
		 headerHeight = "auto";
	     }else{
		 headerHeight = ""+printHeaderHeight+"cm";
	     }
	     
	     //FOOTER HEIGHT SETTING
	     if(printFooterHeight.equals("0")){
		 footerHeight = "auto";
	     }else{
		 footerHeight = ""+printFooterHeight+"cm";
	     }
	     
	     
	     if(printPapertType.equals("0")){
		 background = "";
	     }else{
		 background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url('"+approot+"/styles/cashier/img/bill.jpg');";
	     }
	     String paperSetting = "style=\""+background+""+paperWidth+""+paperHeight+"\"";
	     //background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");
	     
	     //OBJECT TYPE
	     Location location;
	     try{
		 location = PstLocation.fetchExc(billMain.getLocationId());
                 
                 //berapa kali pernah print
                 String whereLog= PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DOCUMENT_ID]+"='"+billMain.getOID()+"' "
                               + " AND "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DETAIL]+"='Print out bill'";
                 int count = PstLogSysHistory.getCount(whereLog);
                 invoiceNumb=invoiceNumb+" / "+count;
	     }catch(Exception ex){
		 location = new Location();
	     }
	     
	     //PRINT DEFAULT
	     if(printPapertType.equals("0")){
		 
		 drawPrint+= ""
		    + "<div class='page' "+paperSetting+">"
			+ "<div class='subPage'>";
		 
			//HEADER CONTENT
			drawPrint+=drawHeaderDefault(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);


			//BODY CONTENT


			//DEFAULT :: WITHOUT SHORT BY CATEGORY
			if(printCategoryItem.equals("0")){


			    drawPrint += drawContentDefaultNew(billMain, printItemLine, location,service,tax,discPct);

		       //CUSTOM :: SHORT BY CATEGORY
			}else{

			    drawPrint += drawContentCustomNew(billMain, printItemLine, location,tax,service,discPct);

			}
			
			if(printType.equals("printpay") || printType.equals("printreturn")){
			    PaymentSystem paymentSystem;
			    try{
				paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
			    }catch(Exception ex){
				paymentSystem = new PaymentSystem();
			    }
			    
			    drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
				    paymentSystem, ccName, ccNumber, ccBank, 
				    ccValid, ccCharge, payAmount, billMain, 
				    location, printType);
			}


			//FOOTER CONTENT
			drawPrint+=drawFootrDefault(billMain, approot, oidMember);
			
			
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		 
		 
	    //PRINT CUSTOM
	     }else{
		 
		 drawPrint+=""
		 + "<div class='page' "+paperSetting+">"
		    + "<div class='subPage'>";
			//HEADER CONTENT
			drawPrint += ""
			+ "<div style='height:"+headerHeight+";'>";
			drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);

			drawPrint +=""
			+ "</div>"
			+ "<div style='height:10cm;'>";
			//DEFAULT :: WITHOUT SHORT BY CATEGORY
			if(printCategoryItem.equals("0")){


			    drawPrint += drawContentDefaultNew(billMain, printItemLine, location,tax,service,discPct);

		       //CUSTOM :: SHORT BY CATEGORY
			}else{

			    drawPrint += drawContentCustomNew(billMain, printItemLine, location,tax,service,discPct);

			}
			
			drawPrint +=""
			+ "</div>"
			+ "<div style='height:"+footerHeight+";'>";
			
			//FOOTER CONTENT
			drawPrint += drawFootrCustom(billMain);
			
			drawPrint +=""
			+ "</div>";
	     
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		    
		if(printType.equals("printpay") || printType.equals("printreturn")){  
		    ///PAYMENT 
		    drawPrint+=""
		     + "<div class='page' "+paperSetting+">"
			+ "<div class='subPage'>"
			    + "<div style='height:"+headerHeight+";'>";
				//HEADER CONTENT
				drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
					invoiceNumb, cashierName, datePrint, timePrint, 
					billMain, tableRoom);
			    drawPrint += ""
			    + "</div>";

			    
			    PaymentSystem paymentSystem;
			    try{
				paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
			    }catch(Exception ex){
				paymentSystem = new PaymentSystem();
			    }
			    
			    drawPrint+=""
			    + "<div style='height:10cm;'>";

				drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
					paymentSystem, ccName, ccNumber, ccBank, 
					ccValid, ccCharge, payAmount, billMain, 
					location, printType);
			    drawPrint+=""
			    + "</div>";

			    
			    drawPrint+=""
			    + "<div style='height:"+footerHeight+"'>";
				//FOOTER CONTENT
				drawPrint += drawFootrCustom(billMain);
			    drawPrint +=""
			    + "</div>";


			drawPrint+=""
			+ "</div>"
		    + "</div>";
		}
	    }
		    
	    return drawPrint;
     }
     
     public String PrintTemplateGuidePrice(BillMainCostum billMainCostum, 
	     String nameInvoice, String invoiceNumb, String cashierName, 
	     BillMain billMain, TableRoom tableRoom, String printType,
	     String paymentType, String displayPaymentSystem, double payAmount, 
	     long oidPaymentSystem, String ccName, String ccNumber, String ccValid, 
	     String ccBank, double ccCharge, String approot, String oidMember,double tax, double service,double discPct){
	     
	     //DATE TYPE
	     Date dateNow = new Date();
	     
	     //STRING TYPE
	     String datePrint = Formater.formatDate(dateNow, "yyyy-MM-dd");
	     String timePrint = Formater.formatDate(dateNow, "kk:mm:ss");
	     String drawPrint = "";
	     
	     //CHECK SYSTEM PROPERTY :: PAPER TYPE
	     String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
	     
	     //CHECK SYSTEM PROPERTY :: ITEM LINE
	     String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");
	     
	     //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
	     String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");
	     
	     //CHECK SYSTEM PROPERTY :: PAPER WIDTH
	     String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");
	     
	     //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
	     String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");
	     
	     String paperWidth = "";
	     String paperHeight = "";
	     String headerHeight = "";
	     String footerHeight = "";
	     String bodyHeight = "";
	     String background = "";
	     //WIDTH SETTING
	     if(printPaperWidth.equals("0")){
		 paperWidth = "width:7.5cm;padding:0.1cm;";
	     }else{
		 paperWidth = "width:"+printPaperWidth+"cm;padding:0.5cm;";
	     }
	     
	     //HEIGHT SETTING
	     if(printPaperHeight.equals("0")){
		 paperHeight = "height:auto;";
	     }else{
		 paperHeight = "height:"+printPaperHeight+"cm;";
	     }
	     
	     //HEADER HEIGHT SETTING
	     if(printHeaderHeight.equals("0")){
		 headerHeight = "auto";
	     }else{
		 headerHeight = ""+printHeaderHeight+"cm";
	     }
	     
	     //FOOTER HEIGHT SETTING
	     if(printFooterHeight.equals("0")){
		 footerHeight = "auto";
	     }else{
		 footerHeight = ""+printFooterHeight+"cm";
	     }
	     
	     
	     if(printPapertType.equals("0")){
		 background = "";
	     }else{
		 background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url('"+approot+"/styles/cashier/img/bill.jpg');";
	     }
	     String paperSetting = "style=\""+background+""+paperWidth+""+paperHeight+"\"";
	     //background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");
	     
	     //OBJECT TYPE
	     Location location;
	     try{
		 location = PstLocation.fetchExc(billMain.getLocationId());
                 
                 //berapa kali pernah print
                 String whereLog= PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DOCUMENT_ID]+"='"+billMain.getOID()+"' "
                               + " AND "+PstLogSysHistory.fieldNames[PstLogSysHistory.FLD_LOG_DETAIL]+"='Print out bill'";
                 int count = PstLogSysHistory.getCount(whereLog);
                 invoiceNumb=invoiceNumb+" / "+count;
	     }catch(Exception ex){
		 location = new Location();
	     }
	     
	     //PRINT DEFAULT
	     if(printPapertType.equals("0")){
		 
		 drawPrint+= ""
		    + "<div class='page' "+paperSetting+">"
			+ "<div class='subPage'>";
		 
			//HEADER CONTENT
			drawPrint+=drawHeaderDefault(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);


			//BODY CONTENT


			//DEFAULT :: WITHOUT SHORT BY CATEGORY
			if(printCategoryItem.equals("0")){


			    drawPrint += drawContentGuidePrice(billMain, printItemLine, location,service,tax,discPct);

		       //CUSTOM :: SHORT BY CATEGORY
			}else{

			    drawPrint += drawContentCustomNew(billMain, printItemLine, location,tax,service,discPct);

			}
			
			if(printType.equals("printpay") || printType.equals("printreturn")){
			    PaymentSystem paymentSystem;
			    try{
				paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
			    }catch(Exception ex){
				paymentSystem = new PaymentSystem();
			    }
			    
			    drawPrint+=drawPaymentGuidePrice(paymentType, displayPaymentSystem, 
				    paymentSystem, ccName, ccNumber, ccBank, 
				    ccValid, ccCharge, payAmount, billMain, 
				    location, printType);
			}


			//FOOTER CONTENT
			drawPrint+=drawFootrDefault(billMain, approot, oidMember);
			
			
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		 
		 
	    //PRINT CUSTOM
	     }else{
		 
		 drawPrint+=""
		 + "<div class='page' "+paperSetting+">"
		    + "<div class='subPage'>";
			//HEADER CONTENT
			drawPrint += ""
			+ "<div style='height:"+headerHeight+";'>";
			drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);

			drawPrint +=""
			+ "</div>"
			+ "<div style='height:10cm;'>";
			//DEFAULT :: WITHOUT SHORT BY CATEGORY
			if(printCategoryItem.equals("0")){


			    drawPrint += drawContentDefaultNew(billMain, printItemLine, location,tax,service,discPct);

		       //CUSTOM :: SHORT BY CATEGORY
			}else{

			    drawPrint += drawContentCustomNew(billMain, printItemLine, location,tax,service,discPct);

			}
			
			drawPrint +=""
			+ "</div>"
			+ "<div style='height:"+footerHeight+";'>";
			
			//FOOTER CONTENT
			drawPrint += drawFootrCustom(billMain);
			
			drawPrint +=""
			+ "</div>";
	     
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		    
		if(printType.equals("printpay") || printType.equals("printreturn")){  
		    ///PAYMENT 
		    drawPrint+=""
		     + "<div class='page' "+paperSetting+">"
			+ "<div class='subPage'>"
			    + "<div style='height:"+headerHeight+";'>";
				//HEADER CONTENT
				drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
					invoiceNumb, cashierName, datePrint, timePrint, 
					billMain, tableRoom);
			    drawPrint += ""
			    + "</div>";

			    
			    PaymentSystem paymentSystem;
			    try{
				paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
			    }catch(Exception ex){
				paymentSystem = new PaymentSystem();
			    }
			    
			    drawPrint+=""
			    + "<div style='height:10cm;'>";

				drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
					paymentSystem, ccName, ccNumber, ccBank, 
					ccValid, ccCharge, payAmount, billMain, 
					location, printType);
			    drawPrint+=""
			    + "</div>";

			    
			    drawPrint+=""
			    + "<div style='height:"+footerHeight+"'>";
				//FOOTER CONTENT
				drawPrint += drawFootrCustom(billMain);
			    drawPrint +=""
			    + "</div>";


			drawPrint+=""
			+ "</div>"
		    + "</div>";
		}
	    }
		    
	    return drawPrint;
     }
     
     public String PrintTemplateFOC(MatCosting matCosting,String approot,int full,String transactionName, 
            String paymentType,String nameInvoice,String invoiceNumb,String cashierName){
	     
	     //DATE TYPE
            Date dateNow = new Date();

            //STRING TYPE
            String datePrint = Formater.formatDate(matCosting.getCostingDate(), "yyyy-MM-dd");
            String timePrint = Formater.formatDate(matCosting.getCostingDate(), "kk:mm:ss");
            String datePrinting = Formater.formatDate(new Date(), "yyyy-MM-dd");
            String timePrinting = Formater.formatDate(new Date(), "kk:mm:ss");
            String drawPrint = "";

            //CHECK SYSTEM PROPERTY :: PAPER TYPE
            String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");

            //CHECK SYSTEM PROPERTY :: ITEM LINE
            String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");

            //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
            String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");

            //CHECK SYSTEM PROPERTY :: PAPER WIDTH
            String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");

            //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
            String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");

            //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
            String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");

            //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
            String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");

            String paperWidth = "";
            String paperHeight = "";
            String headerHeight = "";
            String footerHeight = "";
            String bodyHeight = "";
            String background = "";

            //WIDTH SETTING
            if(printPaperWidth.equals("0")){
                paperWidth = "width:7.5cm;padding:0.1cm;";
            }else{
                paperWidth = "width:"+printPaperWidth+"cm;padding:0.5cm;";
            }

            //HEIGHT SETTING
            if(printPaperHeight.equals("0")){
                paperHeight = "height:auto;";
            }else{
                paperHeight = "height:"+printPaperHeight+"cm;";
            }

            //HEADER HEIGHT SETTING
            if(printHeaderHeight.equals("0")){
                headerHeight = "auto";
            }else{
                headerHeight = ""+printHeaderHeight+"cm";
            }

            //FOOTER HEIGHT SETTING
            if(printFooterHeight.equals("0")){
                footerHeight = "auto";
            }else{
                footerHeight = ""+printFooterHeight+"cm";
            }


            if(printPapertType.equals("0")){
                background = "";
            }else{
                background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url('"+approot+"/styles/cashier/img/bill.jpg');";
            }

            String paperSetting = "style=\""+background+""+paperWidth+""+paperHeight+"\"";

            //OBJECT TYPE
            Location location;
            try{
                location = PstLocation.fetchExc(matCosting.getLocationId());
            }catch(Exception ex){
                location = new Location();
            }
            
            //PRINT DEFAULT
            if(printPapertType.equals("0")){		 
                drawPrint+= ""
		    + "<div class='page' "+paperSetting+">"
			+ "<div class='subPage'>";
                
			//HEADER CONTENT
			drawPrint+=drawHeaderDefaultFoc(matCosting,location,nameInvoice,invoiceNumb,cashierName,datePrint,timePrint,datePrinting,timePrinting);

			//BODY CONTENT
			//DEFAULT :: WITHOUT SHORT BY CATEGORY
			if(printCategoryItem.equals("0")){
			    drawPrint += drawContentDefaultFoc(matCosting, printItemLine, location);
		       //CUSTOM :: SHORT BY CATEGORY
			}else{
			    drawPrint += drawContentCustomFOC(matCosting, printItemLine, location);
			}			
			//PAYMENT CONTENT

			//FOOTER CONTENT
			drawPrint+=drawFootrDefaultFoc(matCosting, approot);
						
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		 		 
	    //PRINT CUSTOM
            } else{

            drawPrint+=""
            + "<div class='page' "+paperSetting+">"
               + "<div class='subPage'>";
                   //HEADER CONTENT
                   drawPrint += ""
                   + "<div style='height:"+headerHeight+";'>";
                   //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
                   
                    drawPrint += drawHeaderCustomFoc(matCosting,location,nameInvoice,invoiceNumb,cashierName,datePrint,timePrint);
                  
                   drawPrint +=""
                   + "</div>";

                   drawPrint +=""
                   + "<div style='height:10cm;'>";
                   //DEFAULT :: WITHOUT SHORT BY CATEGORY
                   if(printCategoryItem.equals("0")){
                       drawPrint += drawContentDefaultFoc(matCosting, printItemLine, location);
                  //CUSTOM :: SHORT BY CATEGORY
                   }else{
                       drawPrint += drawContentCustomFOC(matCosting, printItemLine, location);
                   }
                   drawPrint +=""
                   + "</div>";

                   drawPrint += ""
                   + "<div style='height:"+footerHeight+";'>";			
                   //FOOTER CONTENT
                   drawPrint += drawFootrCustomFoc(matCosting);

                   drawPrint +=""
                   + "</div>";

               drawPrint+=""
               + "</div>"
           + "</div>";

           
       }                      		    
	    return drawPrint;
     }
     
     public String PrintReTemplate(BillMainCostum billMainCostum, 
	     String nameInvoice, String invoiceNumb, String cashierName, 
	     BillMain billMain, TableRoom tableRoom, String printType,
	     String paymentType, String displayPaymentSystem, double payAmount, 
	     long oidPaymentSystem, String ccName, String ccNumber, String ccValid, 
	     String ccBank, double ccCharge, String approot, String oidMember){
	     
	     //DATE TYPE
	     Date dateNow = new Date();
	     
	     //STRING TYPE
	     String datePrint = Formater.formatDate(dateNow, "yyyy-MM-dd");
	     String timePrint = Formater.formatDate(dateNow, "kk:mm:ss");
	     String drawPrint = "";
	     
	     //CHECK SYSTEM PROPERTY :: PAPER TYPE
	     String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
	     
	     //CHECK SYSTEM PROPERTY :: ITEM LINE
	     String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");
	     
	     //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
	     String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");
	     
	     //CHECK SYSTEM PROPERTY :: PAPER WIDTH
	     String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");
	     
	     //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
	     String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");
	     
	     //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	     String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");
	     
	     String paperWidth = "";
	     String paperHeight = "";
	     String headerHeight = "";
	     String footerHeight = "";
	     String bodyHeight = "";
	     String background = "";
	     //WIDTH SETTING
	     if(printPaperWidth.equals("0")){
		 paperWidth = "width:7.5cm;padding:0.1cm;";
	     }else{
		 paperWidth = "width:"+printPaperWidth+"cm;padding:0.5cm;";
	     }
	     
	     //HEIGHT SETTING
	     if(printPaperHeight.equals("0")){
		 paperHeight = "height:auto;";
	     }else{
		 paperHeight = "height:"+printPaperHeight+"cm;";
	     }
	     
	     //HEADER HEIGHT SETTING
	     if(printHeaderHeight.equals("0")){
		 headerHeight = "auto";
	     }else{
		 headerHeight = ""+printHeaderHeight+"cm";
	     }
	     
	     //FOOTER HEIGHT SETTING
	     if(printFooterHeight.equals("0")){
		 footerHeight = "auto";
	     }else{
		 footerHeight = ""+printFooterHeight+"cm";
	     }
	     
	     
	     if(printPapertType.equals("0")){
		 background = "";
	     }else{
		 background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url('"+approot+"/styles/cashier/img/bill.jpg');";
	     }
	     String paperSetting = "style='"+background+""+paperWidth+""+paperHeight+"'";
	     //background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");
	     
	     //OBJECT TYPE
	     Location location;
	     try{
		 location = PstLocation.fetchExc(billMain.getLocationId());
	     }catch(Exception ex){
		 location = new Location();
	     }
	     
	     //PRINT DEFAULT
	     if(printPapertType.equals("0")){
		 
		 drawPrint+= ""
		    + "<div class='page' "+paperSetting+">"
			+ "<div class='subPage'>";
		 
			//HEADER CONTENT
			drawPrint+=drawHeaderDefault(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);


			//BODY CONTENT


			//DEFAULT :: WITHOUT SHORT BY CATEGORY
			if(printCategoryItem.equals("0")){


			    drawPrint += drawContentDefault(billMain, printItemLine, location);

		       //CUSTOM :: SHORT BY CATEGORY
			}else{

			    drawPrint += drawContentCustom(billMain, printItemLine, location);

			}
			
			if(printType.equals("printpay") || printType.equals("printreturn")){
			    PaymentSystem paymentSystem;
			    try{
				paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
			    }catch(Exception ex){
				paymentSystem = new PaymentSystem();
			    }
			    
			    drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
				    paymentSystem, ccName, ccNumber, ccBank, 
				    ccValid, ccCharge, payAmount, billMain, 
				    location, printType);
			}


			//FOOTER CONTENT
			drawPrint+=drawFootrDefault(billMain, approot, oidMember);
			
			
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		 
		 
	    //PRINT CUSTOM
	     }else{
		 
		 drawPrint+=""
		 + "<div class='page' "+paperSetting+">"
		    + "<div class='subPage'>";
			//HEADER CONTENT
			drawPrint += ""
			+ "<div style='height:"+headerHeight+";'>";
			drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
				invoiceNumb, cashierName, datePrint, timePrint, 
				billMain, tableRoom);

			drawPrint +=""
			+ "</div>"
			+ "<div style='height:10cm;'>";
			//DEFAULT :: WITHOUT SHORT BY CATEGORY
			if(printCategoryItem.equals("0")){


			    drawPrint += drawContentDefault(billMain, printItemLine, location);

		       //CUSTOM :: SHORT BY CATEGORY
			}else{

			    drawPrint += drawContentCustom(billMain, printItemLine, location);

			}
			
			drawPrint +=""
			+ "</div>"
			+ "<div style='height:"+footerHeight+";'>";
			
			//FOOTER CONTENT
			drawPrint += drawFootrCustom(billMain);
			
			drawPrint +=""
			+ "</div>";
	     
		    drawPrint+=""
		    + "</div>"
		+ "</div>";
		    
		if(printType.equals("printpay") || printType.equals("printreturn")){  
		    ///PAYMENT 
		    drawPrint+=""
		     + "<div class='page' "+paperSetting+">"
			+ "<div class='subPage'>"
			    + "<div style='height:"+headerHeight+";'>";
				//HEADER CONTENT
				drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
					invoiceNumb, cashierName, datePrint, timePrint, 
					billMain, tableRoom);
			    drawPrint += ""
			    + "</div>";

			    
			    PaymentSystem paymentSystem;
			    try{
				paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
			    }catch(Exception ex){
				paymentSystem = new PaymentSystem();
			    }
			    
			    drawPrint+=""
			    + "<div style='height:10cm;'>";

				drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
					paymentSystem, ccName, ccNumber, ccBank, 
					ccValid, ccCharge, payAmount, billMain, 
					location, printType);
			    drawPrint+=""
			    + "</div>";

			    
			    drawPrint+=""
			    + "<div style='height:"+footerHeight+"'>";
				//FOOTER CONTENT
				drawPrint += drawFootrCustom(billMain);
			    drawPrint +=""
			    + "</div>";


			drawPrint+=""
			+ "</div>"
		    + "</div>";
		}
	    }
		    
	    return drawPrint;
     }
     
    
     //PRINT CLOSING
     public String PrintTemplateClosing(String userName, 
	     CashCashier cashCashier, BillMainCostum billMainCostum, Location location){
	 String drawPrint = "";
	 
	 //CHECK SYSTEM PROPERTY :: PAPER TYPE
	 String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");

	 //CHECK SYSTEM PROPERTY :: ITEM LINE
	 String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");

	 //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
	 String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");

	 //CHECK SYSTEM PROPERTY :: PAPER WIDTH
	 String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");

	 //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
	 String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");

	 //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	 String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");

	 //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	 String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");

	 String paperWidth = "";
	 String paperHeight = "";
	 String headerHeight = "";
	 String footerHeight = "";
	 String bodyHeight = "";
	 String background = "";
	 
	 //WIDTH SETTING
	 if(printPaperWidth.equals("1")){
	    paperWidth = "width:9.5cm;padding:0.5cm;";
	 }else{
	    paperWidth = "width:7.5cm;padding:0.1cm;";
	 }

	//HEIGHT SETTING
	if(printPaperHeight.equals("1")){
	    paperHeight = "height:29.7cm;";
	}else{
	    paperHeight = "height:auto;";
	}

	//HEADER HEIGHT SETTING
	if(printHeaderHeight.equals("1")){
	    //headerHeight = "5cm";
	    headerHeight = "auto";
	}else{
	    headerHeight = "auto";
	}

	//FOOTER HEIGHT SETTING
	if(printFooterHeight.equals("1")){
	    //footerHeight = "2.5cm";
	    footerHeight = "auto";
	}else{
	    footerHeight = "auto";
	}


	if(printPapertType.equals("0")){
	    background = "";
	}else{
	    background = "";
	    //background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");";
	}
	String paperSetting = "style='"+background+""+paperWidth+""+paperHeight+"'";
	//background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");

	//OBJECT TYPE
	
	/*Vector listBalance = PstBalance.list(0, 0, 
		  ""+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		  + "AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='0' "
		  + "AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"='1'" , "");*/
        Vector listBalance = PstBalance.list(0, 0, 
		  ""+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		  + "AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='0' "
		  + "" , "");
	Vector listBalanceEnd = PstBalance.list(0, 0, 
		  ""+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		  + "AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='1' "
		  + "" , "");
        /*Vector listBalanceEnd = PstBalance.list(0, 0, 
		  ""+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		  + "AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='1' "
		  + "AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"='1'" , "");*/
	
	if(printPapertType.equals("0")){
	    //OPENING & CLOSING
	    drawPrint+=""
	    + "<div class='page' "+paperSetting+">"
		+ "<div class='subPage'>";
		    drawPrint += drawHeaderClosingDefault(billMainCostum, "OPENING BALANCE");
		    drawPrint += drawContentOpeningBalance(location, userName, 
			    cashCashier, listBalance);
		    drawPrint += drawContentClosingBalance(userName, cashCashier, 
			    location, listBalance, listBalanceEnd);
		drawPrint += ""
		+ "</div>"
	    + "</div>";
	}else{
	    //OPENING
	    drawPrint+=""
	    + "<div class='page' "+paperSetting+">"
		+ "<div class='subPage'>";
		    drawPrint += drawHeaderClosingCustom(billMainCostum, "OPENING BALANCE");
		    drawPrint += drawContentOpeningBalance(location, userName, 
			    cashCashier, listBalance);
		drawPrint += ""
		+ "</div>"
	    + "</div>";
	    
		
	    //CLOSING
	    drawPrint+=""
	    + "<div class='page' "+paperSetting+">"
		+ "<div class='subPage'>";
		    drawPrint += drawHeaderClosingCustom(billMainCostum, "CLOSING BALANCE");
		    drawPrint += drawContentClosingBalance(userName, cashCashier, 
			    location, listBalance, listBalanceEnd);
		drawPrint += ""
		+ "</div>"
	    + "</div>";
	}
	 
	 return drawPrint;
     }
     
     public String PrintTemplateClosing2(String userName, 
	     CashCashier cashCashier, BillMainCostum billMainCostum, Location location,int type){
	 String drawPrint = "";
	 
	 //CHECK SYSTEM PROPERTY :: PAPER TYPE
	 String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");

	 //CHECK SYSTEM PROPERTY :: ITEM LINE
	 String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");

	 //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
	 String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");

	 //CHECK SYSTEM PROPERTY :: PAPER WIDTH
	 String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");

	 //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
	 String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");

	 //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	 String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");

	 //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	 String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");

	 String paperWidth = "";
	 String paperHeight = "";
	 String headerHeight = "";
	 String footerHeight = "";
	 String bodyHeight = "";
	 String background = "";
	 
	 //WIDTH SETTING
	 if(printPaperWidth.equals("1")){
	    paperWidth = "width:9.5cm;padding:0.5cm;";
	 }else{
	    paperWidth = "width:7.5cm;padding:0.1cm;";
	 }

	//HEIGHT SETTING
	if(printPaperHeight.equals("1")){
	    paperHeight = "height:29.7cm;";
	}else{
	    paperHeight = "height:auto;";
	}

	//HEADER HEIGHT SETTING
	if(printHeaderHeight.equals("1")){
	    //headerHeight = "5cm";
	    headerHeight = "auto";
	}else{
	    headerHeight = "auto";
	}

	//FOOTER HEIGHT SETTING
	if(printFooterHeight.equals("1")){
	    //footerHeight = "2.5cm";
	    footerHeight = "auto";
	}else{
	    footerHeight = "auto";
	}


	if(printPapertType.equals("0")){
	    background = "";
	}else{
	    background = "";
	    //background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");";
	}
	String paperSetting = "style='"+background+""+paperWidth+""+paperHeight+"'";
	//background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");

	//OBJECT TYPE
	
	Vector listBalance = PstBalance.list(0, 0, 
		  ""+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		  + "AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='0' ", "");
	Vector listBalanceEnd = PstBalance.list(0, 0, 
		  ""+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		  + "AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='1' " , "");
	
	if(type==1){
	    //CLOSING
	    drawPrint+=""
	    + "<div class='page' "+paperSetting+">"
		+ "<div class='subPage'>";
		    drawPrint += drawHeaderClosingDefault(billMainCostum, "REPRINT CLOSING BALANCE");
		    //drawPrint += drawContentOpeningBalance(location, userName, 
			    //cashCashier, listBalance);
		    drawPrint += drawContentClosingBalance(userName, cashCashier, 
			    location, listBalance, listBalanceEnd);
		drawPrint += ""
		+ "</div>"
	    + "</div>";
	}else{
	    //OPENING
	    drawPrint+=""
	    + "<div class='page' "+paperSetting+">"
		+ "<div class='subPage'>";
		    drawPrint += drawHeaderClosingCustom(billMainCostum, "REPRINT OPENING BALANCE");
		    drawPrint += drawContentOpeningBalance2(location, userName, 
			    cashCashier, listBalance);
		drawPrint += ""
		+ "</div>"
	    + "</div>";
	    
		
	    
	}
	 
	 return drawPrint;
     }
     
     public String PrintTemplateClosing3(String userName, 
	     CashCashier cashCashier, BillMainCostum billMainCostum, Location location,int type){
	 String drawPrint = "";
	 
	 //CHECK SYSTEM PROPERTY :: PAPER TYPE
	 String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");

	 //CHECK SYSTEM PROPERTY :: ITEM LINE
	 String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");

	 //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
	 String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");

	 //CHECK SYSTEM PROPERTY :: PAPER WIDTH
	 String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");

	 //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
	 String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");

	 //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	 String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");

	 //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
	 String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");

	 String paperWidth = "";
	 String paperHeight = "";
	 String headerHeight = "";
	 String footerHeight = "";
	 String bodyHeight = "";
	 String background = "";
	 
	 //WIDTH SETTING
	 if(printPaperWidth.equals("1")){
	    paperWidth = "width:9.5cm;padding:0.5cm;";
	 }else{
	    paperWidth = "width:7.5cm;padding:0.1cm;";
	 }

	//HEIGHT SETTING
	if(printPaperHeight.equals("1")){
	    paperHeight = "height:29.7cm;";
	}else{
	    paperHeight = "height:auto;";
	}

	//HEADER HEIGHT SETTING
	if(printHeaderHeight.equals("1")){
	    //headerHeight = "5cm";
	    headerHeight = "auto";
	}else{
	    headerHeight = "auto";
	}

	//FOOTER HEIGHT SETTING
	if(printFooterHeight.equals("1")){
	    //footerHeight = "2.5cm";
	    footerHeight = "auto";
	}else{
	    footerHeight = "auto";
	}


	if(printPapertType.equals("0")){
	    background = "";
	}else{
	    background = "";
	    //background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");";
	}
	String paperSetting = "style='"+background+""+paperWidth+""+paperHeight+"'";
	//background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");

	//OBJECT TYPE
	
	//Vector listBalance = PstBalance.list(0, 0, 
		  //""+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		  //+ "AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='0' "
		  //+ "AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"='1'" , "");
        Vector listBalance = PstBalance.list(0, 0, 
		  ""+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		  + "AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='0' "
		  + "" , "");
	Vector listBalanceEnd = PstBalance.list(0, 0, 
		  ""+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		  + "AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='1' "
		  + "" , "");
	
	if(type==1){
	    //CLOSING
	    drawPrint+=""
	    + "<div class='page' "+paperSetting+">"
		+ "<div class='subPage'>";
		    drawPrint += drawHeaderClosingDefault(billMainCostum, "CLOSING BALANCE");
		    //drawPrint += drawContentOpeningBalance(location, userName, 
			    //cashCashier, listBalance);
		    drawPrint += drawContentClosingBalance(userName, cashCashier, 
			    location, listBalance, listBalanceEnd);
		drawPrint += ""
		+ "</div>"
	    + "</div>";
	}else{
	    //OPENING
	    drawPrint+=""
	    + "<div class='page' "+paperSetting+">"
		+ "<div class='subPage'>";
		    drawPrint += drawHeaderClosingCustom(billMainCostum, "OPENING BALANCE");
		    drawPrint += drawContentOpeningBalance(location, userName, 
			    cashCashier, listBalance);
		drawPrint += ""
		+ "</div>"
	    + "</div>";
	    
		
	    
	}
	 
	 return drawPrint;
     }
     
         //PRINT HEADER DEFAULT
    public String drawHeaderDefault(BillMainCostum billMainCostum,
            String nameInvoice, String invoiceNumb, String cashierName,
            String datePrint, String timePrint, BillMain billMain,
            TableRoom tableRoom) {
        String drawPrint = "";
        //ini adalah fungsi untuk mengecek, apakah sistem kasir perlu sales 
        String cashierNeedSales = PstSystemProperty.getValueByName("CASHIER_SHOW_SALES_PERSON");
        String businessType = PstSystemProperty.getValueByName("TYPE_OF_BUSINESS_DETAIL");
        drawPrint += ""
                + "<div class='row'>"
                    + "<div class='col-xs-12'>"
                        + "<center>"
                            + "" + billMainCostum.getCompanyName() + "<br><br>"
                            + "" + billMainCostum.getLocationName() + "<br>"
                            + "" + billMainCostum.getLocationAddress() + "<br>"
                            + "Telp: " + billMainCostum.getLocationTelp() + ". Fax: " + billMainCostum.getLocationFax() + "<br>"
                            + "Email: " + billMainCostum.getLocationEmail() + "<br>"
                            + "Website: " + billMainCostum.getLocationWeb().replace("http://", "www.").replace("/", "")
                        + "</center>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                + "</div>"
                
                + "<div class='row'>"
                    + "<div class='col-xs-12'>"
                        + "<center>" + nameInvoice + "</center>"
                    + "</div>"
                    + "<div class='col-xs-12' style='font-size:12px;'>"
                        + "<center>NO : " + invoiceNumb + "</center>"
                    + "</div>"
                    + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                + "</div>"
                
                + "<div class='row'>"
                    + "<div class='col-xs-6'>"
                        + "Cashier: " + cashierName
                    + "</div>"
                    + "<div class='col-xs-6'>"
                        + "Date: " + datePrint + "<br>"
                        + "Time: " + timePrint
                    + "</div>"
                + "</div>";
        if (businessType.equals("1")) {
            drawPrint += "<div class='row'>"
                        + "<div class='col-xs-6'>";
            
            if (cashierNeedSales.equals("1")) {
                drawPrint += "Sales: " + billMain.getSalesCode() + "";
            } else {
                drawPrint += "Waiter: " + billMain.getSalesCode() + "";
            }
            
            drawPrint += ""
                        + "</div>"
                        + "<div class='col-xs-6'>"
                            + "Table: " + tableRoom.getTableNumber()
                        + "</div>"
                    + "</div>";
        } else {
            drawPrint += "<div class='row'>"
                        + "<div class='col-xs-12'>"
                            + "Sales: " + billMain.getSalesCode() + ""
                        + "</div>"
                    + "</div>";
        }
        drawPrint += "<div class='row'>"
                        + "<div class='col-xs-12'>"
                            + "<hr style='border:1px dashed #000;margin:0px;'>"
                        + "</div>"
                    + "</div>";
        return drawPrint;
    }

     //PRINT HEADER EXCHANGE
     public String drawHeaderExchange(BillMainCostum billMainCostum, 
	     String nameInvoice, String invoiceNumb, String cashierName, 
	     String datePrint, String timePrint, BillMain billMain, 
	     TableRoom tableRoom){
         
         BillMain billParent = new BillMain();
         try {
             billParent = PstBillMain.fetchExc(billMain.getParentId());
         } catch (Exception e) {
         }
        String drawPrint = "";
        //ini adalah fungsi untuk mengecek, apakah sistem kasir perlu sales 
        String cashierNeedSales = PstSystemProperty.getValueByName("CASHIER_SHOW_SALES_PERSON");
		String businessType = PstSystemProperty.getValueByName("TYPE_OF_BUSINESS_DETAIL");
	 drawPrint += ""
	+ "<div class='row'>"
	       + "<div class='col-xs-12'>"
		    + "<center>"
			+ ""+billMainCostum.getCompanyName()+"<br><br>"
			+ ""+billMainCostum.getLocationName()+"<br>"
			+ ""+billMainCostum.getLocationAddress()+"<br>"
			+ "Telp: "+billMainCostum.getLocationTelp()+". Fax: "+billMainCostum.getLocationFax()+"<br>"
			+ "Email: "+billMainCostum.getLocationEmail()+"<br>"
			+ "Website: "+billMainCostum.getLocationWeb().replace("http://", "www.").replace("/", "")
		    + "</center>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "</div>"
	   + "</div>"
	    + "<div class='row'>"
	       + "<div class='col-xs-12'>"
		   + "<center>"+nameInvoice+"</center>"
	       + "</div>"
	       + "<div class='col-xs-12' style='font-size:12px;'>"
		   + "<center>NO : "+invoiceNumb
                   + "<br>Exchange from : " + billParent.getInvoiceNumber()
                   + "</center>"
	       + "</div>"
		+ "<div class='col-xs-12'>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "</div>"
	   + "</div>"
	   + "<div class='row'>"
	       + "<div class='col-xs-6'>"
		   + "Cashier: "+cashierName
	       + "</div>"
	       + "<div class='col-xs-6'>"
		   + "Date: "+datePrint+"<br>"
		   + "Time: "+timePrint
	       + "</div>"
	   + "</div>";
	   if (businessType.equals("1")){
			drawPrint+= "<div class='row'>"
				+ "<div class='col-xs-6'>";
				// "Waiter: "+billMain.getSalesCode()
						if (cashierNeedSales.equals("1")){
							drawPrint += "Sales: "+billMain.getSalesCode()+"";
						}else{
							drawPrint += "Waiter: "+billMain.getSalesCode()+"";
						}
				drawPrint+= ""       
				+ "</div>"
				+ "<div class='col-xs-6'>"
				+ "Table: "+tableRoom.getTableNumber()
				+ "</div>"
			 + "<div class='col-xs-12'>"
				 + "<hr style='border:1px dashed #000;margin:0px;'>"
			 + "</div>"

			+ "</div>";
		} else {
			drawPrint+= "<div class='row'>"
				+ "<div class='col-xs-12'>"
				+ "Sales: "+billMain.getSalesCode()+""
				+ "</div>"
			 + "<div class='col-xs-12'>"
				 + "<hr style='border:1px dashed #000;margin:0px;'>"
			 + "</div>"

			+ "</div>";
		}
	 return drawPrint;
     }
     
     
     public String drawHeaderDefaultContinuesPayment(BillMainCostum billMainCostum, 
	     String nameInvoice, String invoiceNumb, String cashierName, 
	     String datePrint, String timePrint, BillMain billMain, 
	     TableRoom tableRoom){
        String drawPrint = "";
        //ini adalah fungsi untuk mengecek, apakah sistem kasir perlu sales 
        String cashierNeedSales = PstSystemProperty.getValueByName("CASHIER_SHOW_SALES_PERSON");
	 drawPrint += ""
	+ "<div class='row'>"
	       + "<div class='col-xs-12'>"
		    + "<center>"
			+ "<br><br>"
			+ "&nbsp;<br>"
			+ "<br>"
			+ "<br>"
			+ "<br>"
			+ ""
		    + "</center>"
		    + "&nbsp;"
		+ "</div>"
	   + "</div>"
	    + "<div class='row'>"
	       + "<div class='col-xs-12'>"
		   + "<center>&nbsp;</center>"
	       + "</div>"
	       + "<div class='col-xs-12' style='font-size:12px;'>"
		   + "<center>&nbsp;</center>"
	       + "</div>"
		+ "<div class='col-xs-12'>"
		    + "&nbsp;"
		+ "</div>"
	   + "</div>"
	   + "<div class='row'>"
	       + "<div class='col-xs-6'>"
		   + "&nbsp;"
	       + "</div>"
	       + "<div class='col-xs-6'>"
		   + "<br>"
		   + "&nbsp;"
	       + "</div>"
	   + "</div>"
	   + "<div class='row'>"
	       + "<div class='col-xs-6'>";
                   if (cashierNeedSales.equals("1")){
                       drawPrint += "&nbsp;";
                   }else{
                       drawPrint += "&nbsp;";
                   }
            drawPrint += ""
	       + "</div>"
	       + "<div class='col-xs-6'>"
		   + ""
	       + "</div>"
		+ "<div class='col-xs-12'>"
		    + "&nbsp;"
		+ "</div>"
	   + "</div>";
	 return drawPrint;
     }
     
     
     public String drawHeaderDefaultRe(BillMainCostum billMainCostum,
            String nameInvoice, String invoiceNumb, String cashierName,
            String datePrint, String timePrint, BillMain billMain,
            TableRoom tableRoom, String dateNow, String timeNow) {
         
        String drawPrint = "";
        String cashierNeedSales = PstSystemProperty.getValueByName("CASHIER_SHOW_SALES_PERSON");
        String businessType = PstSystemProperty.getValueByName("TYPE_OF_BUSINESS_DETAIL");
        
        drawPrint += ""
                + "<div class='row'>"
                + "     <div class='col-xs-12'>"
                + "         <center>" + billMainCostum.getCompanyName()
                + "         <br><br>" + billMainCostum.getLocationName()
                + "         <br>" + billMainCostum.getLocationAddress()
                + "         <br>Telp: " + billMainCostum.getLocationTelp() + ". Fax: " + billMainCostum.getLocationFax()
                + "         <br>Email: " + billMainCostum.getLocationEmail()
                + "         <br>Website: " + billMainCostum.getLocationWeb().replace("http://", "www.").replace("/", "")
                + "         </center>"
                + "         <hr style='border:1px dashed #000;margin:0px;'>"
                + "     </div>"
                + "</div>"
                
                + "<div class='row'>"
                + "     <div class='col-xs-12'>"
                + "         <center>" + nameInvoice + " (" + dateNow + " " + timeNow + ")</center>"
                + "     </div>"
                + "     <div class='col-xs-12' style='font-size:12px;'>"
                + "         <center>NO : " + invoiceNumb + "</center>"
                + "     </div>"
                + "     <div class='col-xs-12'>"
                + "         <hr style='border:1px dashed #000;margin:0px;'>"
                + "     </div>"
                + "</div>"
                
                + "<div class='row'>"
                + "     <div class='col-xs-6'>Cashier: " + cashierName + "</div>"
                + "     <div class='col-xs-6'>Date: " + datePrint + "<br>Time: " + timePrint + "</div>"
                + "</div>";
        if (businessType.equals("1")) {
            drawPrint += ""
                    + "<div class='row'>"
                    + "     <div class='col-xs-6'>";
            // "Waiter: "+billMain.getSalesCode()
            if (cashierNeedSales.equals("1")) {
                drawPrint += "Sales: " + billMain.getSalesCode() + "";
            } else {
                drawPrint += "Waiter: " + billMain.getSalesCode() + "";
            }
            drawPrint += ""
                    + "     </div>"
                    + "     <div class='col-xs-6'>Table: " + tableRoom.getTableNumber() + "</div>"
                    + "     <div class='col-xs-12'>"
                    + "         <hr style='border:1px dashed #000;margin:0px;'>"
                    + "     </div>"
                    + "</div>";
        } else {
            drawPrint += ""
                    + "<div class='row'>"
                    + "     <div class='col-xs-12'>Sales: " + billMain.getSalesCode() + "</div>"
                    + "     <div class='col-xs-12'>"
                    + "         <hr style='border:1px dashed #000;margin:0px;'>"
                    + "     </div>"
                    + "</div>";
        }
        return drawPrint;
    }

     public String drawHeaderReprintExchange(BillMainCostum billMainCostum, 
	     String nameInvoice, String invoiceNumb, String cashierName, 
	     String datePrint, String timePrint, BillMain billMain, 
	     TableRoom tableRoom,String dateNow,String timeNow){
         
         BillMain billParent = new BillMain();
         String infoBillChild = "";
         try {
             billParent = PstBillMain.fetchExc(billMain.getParentId());
             if ((billMain.getDocType() == 1 && billMain.getTransctionType() == 0 && billMain.getTransactionStatus() == 0 && billMain.getStatusInv() == 1)
                     || (billMain.getDocType() == 1 && billMain.getTransctionType() == 1 && billMain.getTransactionStatus() == 0 && billMain.getStatusInv() == 1)) {
                 infoBillChild = "Return from : " + billParent.getInvoiceNumber();
             } else {
                 infoBillChild = "Exchange from : " + billParent.getInvoiceNumber();
             }
         } catch (Exception e) {
         }
	 String drawPrint = "";
         String cashierNeedSales = PstSystemProperty.getValueByName("CASHIER_SHOW_SALES_PERSON");
		 String businessType = PstSystemProperty.getValueByName("TYPE_OF_BUSINESS_DETAIL");
	 drawPrint += ""
	+ "<div class='row'>"
	       + "<div class='col-xs-12'>"
		    + "<center>"
			+ ""+billMainCostum.getCompanyName()+"<br><br>"
			+ ""+billMainCostum.getLocationName()+"<br>"
			+ ""+billMainCostum.getLocationAddress()+"<br>"
			+ "Telp: "+billMainCostum.getLocationTelp()+". Fax: "+billMainCostum.getLocationFax()+"<br>"
			+ "Email: "+billMainCostum.getLocationEmail()+"<br>"
			+ "Website: "+billMainCostum.getLocationWeb().replace("http://", "www.").replace("/", "")
		    + "</center>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "</div>"
	   + "</div>"
	    + "<div class='row'>"
	       + "<div class='col-xs-12'>"
		   + "<center>"+nameInvoice+" ("+dateNow+" "+timeNow+")</center>"
	       + "</div>"
	       + "<div class='col-xs-12' style='font-size:12px;'>"
		   + "<center>NO : "+invoiceNumb
                   + "<br>" + infoBillChild
                   + "</center>"
	       + "</div>"
		+ "<div class='col-xs-12'>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "</div>"
	   + "</div>"
	   + "<div class='row'>"
	       + "<div class='col-xs-6'>"
		   + "Cashier: "+cashierName
	       + "</div>"
	       + "<div class='col-xs-6'>"
		   + "Date: "+datePrint+"<br>"
		   + "Time: "+timePrint
	       + "</div>"
	   + "</div>";
        if (businessType.equals("1")){
			drawPrint+= "<div class='row'>"
				+ "<div class='col-xs-6'>";
				// "Waiter: "+billMain.getSalesCode()
						if (cashierNeedSales.equals("1")){
							drawPrint += "Sales: "+billMain.getSalesCode()+"";
						}else{
							drawPrint += "Waiter: "+billMain.getSalesCode()+"";
						}
				drawPrint+= ""       
				+ "</div>"
				+ "<div class='col-xs-6'>"
				+ "Table: "+tableRoom.getTableNumber()
				+ "</div>"
			 + "<div class='col-xs-12'>"
				 + "<hr style='border:1px dashed #000;margin:0px;'>"
			 + "</div>"

			+ "</div>";
		} else {
			drawPrint+= "<div class='row'>"
				+ "<div class='col-xs-12'>"
				+ "Sales: "+billMain.getSalesCode()+""
				+ "</div>"
			 + "<div class='col-xs-12'>"
				 + "<hr style='border:1px dashed #000;margin:0px;'>"
			 + "</div>"

			+ "</div>";
		}
	 return drawPrint;
     }
     
     public String drawHeaderDefaultFoc(MatCosting matCosting,Location location, 
            String nameInvoice, String invoiceNumb, String cashierName, 
            String datePrint, String timePrint,String dateNow, String timeNow){
        
        String whereCompany = " "+PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+"="+location.getCompanyId()+"";
        Vector listCompany = PstCompany.list(0, 1, whereCompany, "");
        Company company = new Company();
        if (listCompany.size()>0){
            company = (Company)listCompany.get(0);
        }
        String drawPrint = "";
        drawPrint += ""
	+ "<div class='row'>"
	       + "<div class='col-xs-12'>"
		    + "<center>"
			+ ""+company.getCompanyName()+"<br><br>"
			+ ""+location.getName()+"<br>"
			+ ""+location.getAddress()+"<br>"
			+ "Telp: "+location.getTelephone()+". Fax: "+location.getFax()+"<br>"
			+ "Email: "+location.getEmail()+"<br>"
			+ "Website: "+location.getWebsite().replace("http://", "www.").replace("/", "")
		    + "</center>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "</div>"
	   + "</div>"
	    + "<div class='row'>"
	       + "<div class='col-xs-12'>"
		   + "<center>"+nameInvoice+" ("+dateNow+" "+timeNow+")</center>"
	       + "</div>"
	       + "<div class='col-xs-12' style='font-size:12px;'>"
		   + "<center>NO : "+invoiceNumb+"</center>"
	       + "</div>"
		+ "<div class='col-xs-12'>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "</div>"
	   + "</div>"
	   + "<div class='row'>"
	       + "<div class='col-xs-6'>"
		   + "Cashier: "+cashierName
	       + "</div>"
	       + "<div class='col-xs-6'>"
		   + "Date: "+datePrint+"<br>"
		   + "Time: "+timePrint
	       + "</div>"
	   + "</div>"
	   + "<div class='row'>"
	       + "<div class='col-xs-6'>"
		   + "Waiter: "+matCosting.getPersonName()
	       + "</div>"
	       + "<div class='col-xs-6'>"
		   + "Table: -"
	       + "</div>"
		+ "<div class='col-xs-12'>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "</div>"

	   + "</div>";
	 return drawPrint;
     }
     
     
     //PRINT HEADER CUSTOM
     public String drawHeaderCustom(BillMainCostum billMainCostum, 
	     String nameInvoice, String invoiceNumb, String cashierName, 
	     String datePrint, String timePrint, BillMain billMain, 
	     TableRoom tableRoom){
	 
	 String parentId = "";
         //CHECK SYSTEM PROPERTY :: PAPER WIDTH
         String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");
         AppUser ap = new AppUser();
	 if(billMain.getParentId() != 0){
	     BillMain parentBillMain;
	     try{
		 parentBillMain = PstBillMain.fetchExc(billMain.getParentId());
       ap = PstAppUser.fetch(billMain.getAppUserSalesId());
	     }catch(Exception ex){
		 parentBillMain = new BillMain();
	     }
	     parentId = "From : "+parentBillMain.getInvoiceNumber();
	 }
	 String drawPrint = "";
	 drawPrint += ""
	+ "<div style='margin-top:3cm;position:absolute;width:"+printPaperWidth+"cm;'>"
	   /* + "<table cellpadding='0' cellspacing='0' style='font-size:11px;width:12cm'>"
		+ "<tbody>"
		    + "<tr>"
			+ "<td style='text-align:center;width:1.4cm;'>&nbsp;</td>"
			+ "<td style='text-align:center;width:1.2cm;'>"+billMain.getSalesCode()+"</td>"
			+ "<td style='text-align:center;width:1.4cm;'>"+datePrint+"</td>"
			+ "<td style='text-align:center;width:1.4cm;'>"+tableRoom.getTableNumber()+"</td>"
			+ "<td style='text-align:center;width:0.4cm;'>"+billMain.getPaxNumber()+"</td>"
			+ "<td style='text-align:right;width:1.4cm;'>"+invoiceNumb+"</td>"
		    + "</tr>"
		    + "<tr>"
			+ "<td colspan='3'>"+nameInvoice+"</td>"
			+ "<td colspan='3' style='text-align:right;'>"+parentId+"</td>"
		    + "</tr>"
		+ "</tbody>"
	    + "</table>"*/
            + "<div class='row'>"
                + "<div class='col-xs-0'></div>"
                + "<div class='col-xs-2'>"+ap.getFullName()+"</div>"
//                + "<div class='col-xs-2'>"+billMain.getSalesCode()+"</div>"
                + "<div class='col-xs-3'>"+datePrint+" "+timePrint+"</div>"
                + "<div class='col-xs-1'>"+tableRoom.getTableNumber()+"</div>"
                + "<div class='col-xs-1'>"+billMain.getPaxNumber()+"</div>"
                + "<div class='col-xs-3'>"+invoiceNumb+"</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-6'>"+nameInvoice+"</div>"
                + "<div class='col-xs-6'>"+parentId+"</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-6'>&nbsp;</div>"
                + "<div class='col-xs-6'>&nbsp;</div>"
            + "</div>"     
	+ "</div>";
	   
	 return drawPrint;
     }
     
     
     public String drawHeaderCustomContinuesPayment(BillMainCostum billMainCostum, 
	     String nameInvoice, String invoiceNumb, String cashierName, 
	     String datePrint, String timePrint, BillMain billMain, 
	     TableRoom tableRoom){
	 
	 String parentId = "";
         //CHECK SYSTEM PROPERTY :: PAPER WIDTH
         String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");
    AppUser ap = new AppUser();
	 if(billMain.getParentId() != 0){
	     BillMain parentBillMain; 
	     try{
		 parentBillMain = PstBillMain.fetchExc(billMain.getParentId());
      ap = PstAppUser.fetch(billMain.getAppUserSalesId());
	     }catch(Exception ex){
		 parentBillMain = new BillMain();
	     }
	     parentId = "From : "+parentBillMain.getInvoiceNumber();
	 }
	 String drawPrint = "";
	 drawPrint += ""
	+ "<div style='margin-top:3cm;position:absolute;width:"+printPaperWidth+"cm;'>"
	   /* + "<table cellpadding='0' cellspacing='0' style='font-size:11px;width:12cm'>"
		+ "<tbody>"
		    + "<tr>"
			+ "<td style='text-align:center;width:1.4cm;'>&nbsp;</td>"
			+ "<td style='text-align:center;width:1.2cm;'>"+billMain.getSalesCode()+"</td>"
			+ "<td style='text-align:center;width:1.4cm;'>"+datePrint+"</td>"
			+ "<td style='text-align:center;width:1.4cm;'>"+tableRoom.getTableNumber()+"</td>"
			+ "<td style='text-align:center;width:0.4cm;'>"+billMain.getPaxNumber()+"</td>"
			+ "<td style='text-align:right;width:1.4cm;'>"+invoiceNumb+"</td>"
		    + "</tr>"
		    + "<tr>"
			+ "<td colspan='3'>"+nameInvoice+"</td>"
			+ "<td colspan='3' style='text-align:right;'>"+parentId+"</td>"
		    + "</tr>"
		+ "</tbody>"
	    + "</table>"*/
            + "<div class='row'>"
                + "<div class='col-xs-0'></div>"
                + "<div class='col-xs-2'>"+ap.getFullName()+"</div>"
//                + "<div class='col-xs-2'>"+billMain.getSalesCode().toUpperCase()+"</div>"
                + "<div class='col-xs-3'>"+datePrint+" "+timePrint+"</div>"
                + "<div class='col-xs-1'>"+tableRoom.getTableNumber()+"</div>"
                + "<div class='col-xs-1'>"+billMain.getPaxNumber()+"</div>"
                + "<div class='col-xs-3'>"+invoiceNumb+"</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-6'>"+nameInvoice+"</div>"
                + "<div class='col-xs-6'>"+parentId+"</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-6'>&nbsp;</div>"
                + "<div class='col-xs-6'>&nbsp;</div>"
            + "</div>"     
	+ "</div>";
	   
	 return drawPrint;
     }
     
     public String drawHeaderCustomFoc(MatCosting matCosting,Location location, 
            String nameInvoice, String invoiceNumb, String cashierName, 
            String datePrint, String timePrint){
	
        String whereCompany = " "+PstCompany.fieldNames[PstCompany.FLD_COMPANY_ID]+"="+location.getCompanyId()+"";
        Vector listCompany = PstCompany.list(0, 1, whereCompany, "");
        Company company = new Company();
        if (listCompany.size()>0){
            company = (Company)listCompany.get(0);
        }
	 
        String drawPrint = "";
        drawPrint += ""
	+ "<div style='margin-top:3cm;position:absolute;width:8.5cm;'>"
	    /*+ "<table cellpadding='0' cellspacing='0' style='font-size:11px;width:12cm'>"
		+ "<tbody>"
		    + "<tr>"
			+ "<td style='text-align:center;width:1.4cm;'>&nbsp;</td>"
			+ "<td style='text-align:center;width:1.2cm;'>"+cashierName+"</td>"
			+ "<td style='text-align:center;width:1.4cm;'>"+datePrint+"</td>"
			+ "<td style='text-align:center;width:1.4cm;'>-</td>"
			
			+ "<td colspan='2' style='text-align:right;width:1.8cm;'>"+invoiceNumb+"</td>"
		    + "</tr>"
		    + "<tr>"
			+ "<td colspan='3'>"+nameInvoice+"</td>"
			+ "<td colspan='3' style='text-align:right;'></td>"
		    + "</tr>"
		+ "</tbody>"
	    + "</table>"*/
            + "<div class='row'>"
                + "<div class='col-xs-1'></div>"
                + "<div class='col-xs-2'>"+cashierName+"</div>"
                + "<div class='col-xs-3'>"+datePrint+"</div>"
                + "<div class='col-xs-2'>-</div>"
                + "<div class='col-xs-1'>-</div>"
                + "<div class='col-xs-3'>"+invoiceNumb+"</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-6'>"+nameInvoice+"</div>"
                + "<div class='col-xs-6'></div>"
            + "</div>"
	+ "</div>";
	   
	 return drawPrint;
     }
     
    //PRINT FOOTER DEFAULT
    public String drawFootrDefault(BillMain billMain, String approot, String oidMember) {
        String guestNameTemp = "";
        String passportNumber = "-";
        String controlNumber = "-";
        String flightNumber = "-";
        String flightDate = "-";
        
        //ADDED BY DEWOK 20191002 FOR DUTY FREE
        String enableDutyFree = PstSystemProperty.getValueByName("ENABLE_DUTY_FREE");
        
        Reservation rsv = new Reservation();
        if (billMain.getReservasiId() != 0) {
            try {
                rsv = PstReservation.fetchExc(billMain.getReservasiId());
            } catch (Exception ex) {
            }
        }

        HotelRoom hotelRoom;
        try {
            hotelRoom = PstHotelRoom.fetchExc(rsv.getHotelRoomId());
        } catch (Exception ex) {
            hotelRoom = new HotelRoom();
        }

        //CEK guestName 
        if (billMain.getCustomerId() != 0 && billMain.getGuestName().length() == 0) {
            Contact entContact = new Contact();
            try {
                entContact = PstContact.fetchExc(billMain.getCustomerId());
            } catch (Exception e) {
            }

            if (entContact.getPersonName().length() == 0) {
                guestNameTemp = entContact.getCompName();
            } else {
                guestNameTemp = entContact.getPersonName() + " " + entContact.getPersonLastname();
            }
        } else {
            guestNameTemp = billMain.getGuestName();
        }
        
        if (enableDutyFree.equals("1")) {
            if (billMain.getOID() != 0) {
                try {
                    billMain = PstBillMain.fetchExc(billMain.getOID());
                    flightNumber = billMain.getFlightNumber().isEmpty() ? "-" : billMain.getFlightNumber();
                    flightDate = billMain.getShippingDate() == null ? "-" : Formater.formatDate(billMain.getShippingDate(), "yyyy-MM-dd HH:mm:ss");
                } catch (Exception ex) {
                }
            }
            if (billMain.getCustomerId() != 0) {
                try {
                    ContactList member = PstContactList.fetchExc(billMain.getCustomerId());
                    passportNumber = member.getPassportNo().isEmpty() ? "-" : member.getPassportNo();
                    controlNumber = member.getMemberIdCardNumber().isEmpty() ? "-" : member.getMemberIdCardNumber();
                } catch (Exception e) {
                }
            }
        }

        String drawPrint = "";
        drawPrint += ""
                + "<div class='row'>"
                + "     <div class='col-xs-12'>"
                + "         Guest / Member Name : " + guestNameTemp
                + "         <hr style='border:1px dashed #000;margin:0px;'>"
                + "     </div>"
                + "</div>";

        if (enableDutyFree.equals("1")) {
            drawPrint += ""
                    + "<div class='row'>"
                    + "     <div class='col-xs-6'>"
                    + "         <div>Passport : " + passportNumber + "</div>"
                    + "         <div>Control : " + controlNumber + "</div>"
                    + "     </div>"
                    + "     <div class='col-xs-6'>"
                    + "         <div>Flight : " + flightNumber + "</div>"
                    + "         <div>Flight Date : " + flightDate + "</div>"
                    + "     </div>"
                    + "     <div class='col-xs-12'>"
                    + "         <hr style='border:1px dashed #000;margin:0px;'>"
                    + "     </div>"
                    + "</div>";
        }

        //PENAMBAHAN FOOTER PADA BILL YANG BISA DI CUSTOM
        NotaSetting notaSetting = new NotaSetting();
        String whereNota = " " + PstNotaSetting.fieldNames[PstNotaSetting.FLD_LOCATION_ID] + "= " + billMain.getLocationId() + "";
        Vector listNota = PstNotaSetting.list(0, 0, whereNota, "");

        if (listNota.size() > 0) {
            try {
                notaSetting = (NotaSetting) listNota.get(0);
            } catch (Exception ex) {
            }
        }

        String path = PstSystemProperty.getValueByName("CASHIER_PATH_SIGNATURE");
        String design = PstSystemProperty.getValueByName("DESIGN_MATERIAL_FOR");
        if ((design.equals("1") && oidMember.indexOf("#") != -1) || hotelRoom.getOID() != 0) {
            drawPrint += ""
                    + "<div class='row'>"
                    + "     <div class='col-xs-12'>"
                    + "         <table border='0' cellspacing='0' cellpadding='0' width='100%' style='font-size:11px;'>"
                    + "             <tbody>"
                    + "                 <tr>"
                    + "                     <td>Room No</td>"
                    + "                     <td style='text-align:right;'>Signature</td>"
                    + "                 </tr>"
                    + "                 <tr>"
                    + "                     <td></td>"
                    + "                     <td></td>"
                    + "                 </tr>"
                    + "                 <tr>"
                    + "                     <td >" + hotelRoom.getRoomNumber() + "</td>";
            if (path.equals("0")) {
                drawPrint += "<td style='text-align:right;'></td>";
            } else {
                long oidSignaturebill = 0;
                if (billMain.getParentId() != 0) {
                    oidSignaturebill = billMain.getParentId();
                } else {
                    oidSignaturebill = billMain.getOID();
                }
                drawPrint += "<td style='text-align:right;'><img src='" + approot + "/signature/" + oidSignaturebill + ".png' width='45%'></td>";
            }
            drawPrint += "</tr>"
                    + "                 <tr>"
                    + "                     <td>&nbsp;</td>"
                    + "                     <td>&nbsp;</td>"
                    + "                 </tr>"
                    + "                 <tr>"
                    + "                     <td>&nbsp;</td>"
                    + "                     <td>&nbsp;</td>"
                    + "                 </tr>"
                    + "                 <tr>"
                    + "                     <td>&nbsp;</td>"
                    + "                     <td>&nbsp;</td>"
                    + "                 </tr>"
                    + "             </tbody>"
                    + "         </table>"
                    + "     </div>"
                    + "</div>";
        }
        drawPrint += ""
                + "<div class='row'>"
                + "     <div class='col-xs-12'>" + notaSetting.getFooterText() + "</div>"
                + "</div>";
        return drawPrint;
    }

    public String drawFootrDefaultFoc(MatCosting matCosting, String approot){
	String whereContactId="";
        whereContactId = ""+PstContact.fieldNames[PstContact.FLD_CONTACT_ID]+"="+matCosting.getContactId()+"";
        Vector listContact = PstContact.list(0, 1, whereContactId, "");
        Contact contact = new Contact();
        
        if (listContact.size()>0){
            contact = (Contact) listContact.get(0);
        }
        
	String drawPrint = "";
	drawPrint += ""
	+ "<div class='row'>"
	    + "<div class='col-xs-12'>"
		 + "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "Guest / Member Name : "+contact.getPersonName()
		 + "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>";
        
        //PENAMBAHAN FOOTER PADA BILL YANG BISA DI CUSTOM
        NotaSetting notaSetting = new NotaSetting();
        String whereNota = " "+PstNotaSetting.fieldNames[PstNotaSetting.FLD_LOCATION_ID]+"= "+matCosting.getLocationId()+"";
        Vector listNota = PstNotaSetting.list(0, 0, whereNota, "");
        
        if (listNota.size()>0){
            try{
                notaSetting = (NotaSetting)listNota.get(0);
            }catch(Exception ex){
            } 
        }
        
	String path = PstSystemProperty.getValueByName("CASHIER_PATH_SIGNATURE");
	String design = PstSystemProperty.getValueByName("DESIGN_MATERIAL_FOR");
	if(design.equals("1") && matCosting.getContactId()!=0 ){
            String des = matCosting.getRemark();
            String desTemp[] = des.split(";");
	    drawPrint+=""
	    + "<div class='row'>"
		+ "<div class='col-xs-12'>"
		    + "<table border='0' cellspacing='0' cellpadding='0' width='100%' style='font-size:11px;'>"
			+ "<tbody>" 			    
			    + "<tr>"
				+ "<td>"+contact.getPersonName()+"</td>"
				+ "<td style='text-align:center;'></td>";
                                if(path.equals("0")){
                                    drawPrint+="<td style='text-align:right;'></td>";
                                }else{
                                    long oidSignaturebill=0;
                                    oidSignaturebill = matCosting.getOID();
                                    drawPrint+="<td style='text-align:right;'><img src='"+approot+"/signature/"+desTemp[2]+".png' width='45%'></td>";
                                }
			    drawPrint+="</tr>"
			+ "</tbody>"
		    + "</table>"
		+ "</div>"
	    + "</div>";  
	}
        drawPrint+=""
        + "<div class='row'>"
            + "<div class='col-xs-12'>"
                + ""+notaSetting.getFooterText()+""
            + "</div>"
        + "</div>";
	return drawPrint;
     }
     
     //PRINT FOOTER CUSTOM
     public String drawFootrCustom(BillMain billMain){
        //CEK guestName 
         String guestNameTemp="";
        if (billMain.getCustomerId()!=0 && billMain.getGuestName().length()==0){
            Contact entContact = new Contact();
            try {
                entContact = PstContact.fetchExc(billMain.getCustomerId());
            } catch (Exception e) {
            }
            
            if (entContact.getPersonName().length()==0){
                guestNameTemp = entContact.getCompName();
            }else{
                guestNameTemp = entContact.getPersonName() + " " +entContact.getPersonLastname();
            }
        }else{
            guestNameTemp = billMain.getGuestName();
        }
	String drawPrint = ""
	+ "<div style='margin-left:3.5cm'>"
	    + ""+guestNameTemp
	+ "</div>";
	
        //PENAMBAHAN FOOTER PADA BILL YANG BISA DI CUSTOM
        NotaSetting notaSetting = new NotaSetting();
        String whereNota = " "+PstNotaSetting.fieldNames[PstNotaSetting.FLD_LOCATION_ID]+"= "+billMain.getLocationId()+"";
        Vector listNota = PstNotaSetting.list(0, 0, whereNota, "");
        
        if (listNota.size()>0){
            notaSetting = (NotaSetting)listNota.get(0);
        }
        
        drawPrint += ""
        + "<div class='row'>"
            + "<div class='col-xs-12'>"+notaSetting.getFooterText()+"</div>"
        + "</div>";
        heightTotalRow = heightTotalRow + heightRow;  
	return drawPrint;
     }
     
     public String drawFootrCustomFoc(MatCosting matCosting){
        String whereContactId="";
        whereContactId = ""+PstContact.fieldNames[PstContact.FLD_CONTACT_ID]+"="+matCosting.getContactId()+"";
        Vector listContact = PstContact.list(0, 1, whereContactId, "");
        Contact contact = new Contact();
        
        if (listContact.size()>0){
            contact = (Contact) listContact.get(0);
        }
	String drawPrint = ""
	+ "<div style='margin-left:3.5cm'>"
	    + ""+contact.getPersonName()
	+ "</div>";
	
        //PENAMBAHAN FOOTER PADA BILL YANG BISA DI CUSTOM
        NotaSetting notaSetting = new NotaSetting();
        String whereNota = " "+PstNotaSetting.fieldNames[PstNotaSetting.FLD_LOCATION_ID]+"= "+matCosting.getLocationId()+"";
        Vector listNota = PstNotaSetting.list(0, 0, whereNota, "");
        
        if (listNota.size()>0){
            notaSetting = (NotaSetting)listNota.get(0);
        }
        
        drawPrint += ""
        + "<div class='row'>"
            + "<div class='col-xs-12'>"+notaSetting.getFooterText()+"</div>"
        + "</div>";
        heightTotalRow = heightTotalRow + heightRow;  
	return drawPrint;
     }
     
     
     //PRINT BODY CONTENT DEFAULT :: WITHOUT SHORT BY CATEGORY
     public String drawContentDefault(BillMain billMain, String printItemLine, Location location){
	String drawPrint = "";
        Vector listItem = PstBillDetail.listMat(0, 0, 
                     "CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                    + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0'", "");
        
        String printOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");
        String useForRaditya = PstSystemProperty.getValueByName("USE_FOR_RADITYA");
        String printPaperType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
		String printSKU = PstSystemProperty.getValueByName("PRINT_PAPER_SKU");
        
        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
        if (printOutType.equals("0")){

            if(printItemLine.equals("0")){
				if (printSKU.equals("1")){
					drawPrint += drawItemLineWithSKU(listItem);
				} else {
					drawPrint += drawItemLineDefault(listItem);
				}
            }else{
                drawPrint += drawItemLineCustom(listItem);
            }
        
            double totalQty = PstBillDetail.getQty(billMain.getOID());
            double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
            double totalExpense = PstBillDetail.getTotalExpense(billMain.getOID());
            CurrencyType currencyType;
            
            try{
                currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
            }catch(Exception ex){
                currencyType = new CurrencyType();
            }
	 
            String displayDiscPct = "";
            if(billMain.getDiscPct() > 0){
               displayDiscPct = "("+billMain.getDiscPct()+"%) ";
            }
	 
	 
	 //billMain = PstBillMain.fetchExc(oidBillMain);
            double service = 0;
            double tax = 0;
            if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
                service = 0;
                tax = 0;
            }else{
                service = billMain.getServiceValue();
                tax = billMain.getTaxValue();
            }
            double total = 0;
            total = (totalPrice)-billMain.getDiscount()+service+tax+totalExpense;
                drawPrint+= ""
                + "<div class='row'>"
                   + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                   + "<div class='col-xs-4'>"
                       + "Total Qty"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+totalQty
                + "</div>";
         
         if(useForRaditya.equals("1")){
             drawPrint+= "<div class='row'>"
                + "<div class='col-xs-4'>"
                    + "Other Expense"
                + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+currencyType.getCode()
                   + "</div>"
                + "<div class='col-xs-4' style='text-align:right;'>"
                    + ""+totalExpense
                + "</div>"
             + "</div>";
         }
	 drawPrint+= "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Total Amount"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                        +" "+Formater.formatNumber(totalPrice,"#,###")
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Discount"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": <small>"+displayDiscPct+"</small>"+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + Formater.formatNumber(billMain.getDiscount(),"#,###")
                   + "</div>"
                + "</div>";
                if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
                }else{
                drawPrint += ""
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Total"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+currencyType.getCode()+""
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(totalPrice-billMain.getDiscount()+totalExpense,"#,###")+""
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Service"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": <small>("+billMain.getServicePct()+"%)</small> "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(service, "#,###")
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Tax"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": <small>("+billMain.getTaxPercentage()+"%)</small> "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(tax, "#,###")
                   + "</div>"
                + "</div>";
                }
                drawPrint += ""
                + "<div class='row'>"
                   + "<div class='col-xs-4' style='font-size: 13px;'>"
                       + "<b>Total</b>"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                       + ": <b>"+currencyType.getCode()+"</b>"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                       + "<b>"+Formater.formatNumber(total, "#,###")+"</b>"
                   + "</div>"
                   + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                + "</div>";
            }
        
            if (printPaperType.equals("1")){
                if ((paperHeights-heightTotalRow)>=5){
             
                    PaymentSystem paymentSystem;
                    try{
                        paymentSystem = PstPaymentSystem.fetchExc(this.oidPay);
                    }catch(Exception ex){
                        paymentSystem = new PaymentSystem();
                    }

                     drawPrint+=drawPayment(this.paymentTypes, this.displayPaymentTypes, 
                                paymentSystem, this.ccNames, this.ccNumbers, this.ccBanks, 
                                this.ccValids, this.ccCharges, this.payAmounts, billMain, 
                                location, this.printTypes);
                }
            }
            
        
            
           
	 return drawPrint;
     }
     
     public String drawContentGuidePrice(BillMain billMain, String printItemLine, Location location){
	String drawPrint = "";
        Vector listItem = PstBillDetail.listMat(0, 0, 
                     "CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                    + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0'", "");
        
        String printOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");
        String printPaperType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
		String printSKU = PstSystemProperty.getValueByName("PRINT_PAPER_SKU");
        
        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
        if (printOutType.equals("0")){

            if(printItemLine.equals("0")){
				if (printSKU.equals("1")){
					drawPrint += drawItemLineWithSKU(listItem);
				} else {
					drawPrint += drawItemLineGuidePrice(listItem);
				}
            }else{
                drawPrint += drawItemLineCustom(listItem);
            }
        
            double totalQty = PstBillDetail.getQty(billMain.getOID());
            double totalPrice = PstBillDetail.getTotalGuidePrice(billMain.getOID());
            CurrencyType currencyType;
            
            try{
                currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
            }catch(Exception ex){
                currencyType = new CurrencyType();
            }
	 
            String displayDiscPct = "";
            if(billMain.getDiscPct() > 0){
               displayDiscPct = "("+billMain.getDiscPct()+"%) ";
            }
	 
	 
	 //billMain = PstBillMain.fetchExc(oidBillMain);
            double service = 0;
            double tax = 0;
            if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
                service = 0;
                tax = 0;
            }else{
                service = billMain.getServiceValue();
                tax = billMain.getTaxValue();
            }
            double total = 0;
            total = (totalPrice)-billMain.getDiscount()+service+tax;
                drawPrint+= ""
                + "<div class='row'>"
                   + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                   + "<div class='col-xs-4'>"
                       + "Total Qty"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+totalQty
                   + "</div>"
                   + "<div class='col-xs-4'>"
                       + ""
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Total Amount"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                        +" "+Formater.formatNumber(totalPrice,"#,###")
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Discount"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": <small>"+displayDiscPct+"</small>"+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + Formater.formatNumber(billMain.getDiscount(),"#,###")
                   + "</div>"
                + "</div>";
                if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
                }else{
                drawPrint += ""
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Total"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+currencyType.getCode()+""
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(totalPrice-billMain.getDiscount(),"#,###")+""
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Service"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": <small>("+billMain.getServicePct()+"%)</small> "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(service, "#,###")
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Tax"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": <small>("+billMain.getTaxPercentage()+"%)</small> "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(tax, "#,###")
                   + "</div>"
                + "</div>";
                }
                drawPrint += ""
                + "<div class='row'>"
                   + "<div class='col-xs-4' style='font-size: 13px;'>"
                       + "<b>Total</b>"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                       + ": <b>"+currencyType.getCode()+"</b>"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                       + "<b>"+Formater.formatNumber(total, "#,###")+"</b>"
                   + "</div>"
                   + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                + "</div>";
            }
        
            if (printPaperType.equals("1")){
                if ((paperHeights-heightTotalRow)>=5){
             
                    PaymentSystem paymentSystem;
                    try{
                        paymentSystem = PstPaymentSystem.fetchExc(this.oidPay);
                    }catch(Exception ex){
                        paymentSystem = new PaymentSystem();
                    }

                     drawPrint+=drawPayment(this.paymentTypes, this.displayPaymentTypes, 
                                paymentSystem, this.ccNames, this.ccNumbers, this.ccBanks, 
                                this.ccValids, this.ccCharges, this.payAmounts, billMain, 
                                location, this.printTypes);
                }
            }
            
        
            
           
	 return drawPrint;
     }
     
     
     public String drawContentDefaultContinuesPayment(BillMain billMain, String printItemLine, Location location){
	String drawPrint = "";
        Vector listItem = PstBillDetail.listMat(0, 0, 
                     "CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                    + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0'", "");
        
        String printOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");
        String printPaperType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
        
        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
        if (printOutType.equals("0")){

            if(printItemLine.equals("0")){
                drawPrint += drawItemLineDefaultContinuesPayment(listItem);
            }else{
                drawPrint += drawItemLineCustomContinuesPayment(listItem);
            }
        
            double totalQty = PstBillDetail.getQty(billMain.getOID());
            double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
            CurrencyType currencyType;
	 
            try{
                currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
            }catch(Exception ex){
                currencyType = new CurrencyType();
            }
	 
            String displayDiscPct = "";
            if(billMain.getDiscPct() > 0){
               displayDiscPct = "("+billMain.getDiscPct()+"%) ";
            }
	 
	 
	 //billMain = PstBillMain.fetchExc(oidBillMain);
            double service = 0;
            double tax = 0;
            if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
                service = 0;
                tax = 0;
            }else{
                service = billMain.getServiceValue();
                tax = billMain.getTaxValue();
            }
            double total = 0;
            total = (totalPrice)-billMain.getDiscount()+service+tax;
                drawPrint+= ""
                + "<div class='row'>"
                   + "<div class='col-xs-12'>"
                        + "&nbsp;"
                    + "</div>"
                   + "<div class='col-xs-4'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-4'>"
                       + ""
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                        +"&nbsp;"
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + "&nbsp;"
                   + "</div>"
                + "</div>";
                if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
                }else{
                drawPrint += ""
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + "&nbsp;"
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Service"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + "&nbsp;"
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Tax"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + "&nbsp;"
                   + "</div>"
                + "</div>";
                }
                drawPrint += ""
                + "<div class='row'>"
                   + "<div class='col-xs-4' style='font-size: 13px;'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                       + "&nbsp;"
                   + "</div>"
                   + "<div class='col-xs-12'>"
                        + "&nbsp;"
                    + "</div>"
                + "</div>";
            }
        
            if (printPaperType.equals("1")){
                if ((paperHeights-heightTotalRow)>=5){
             
                    PaymentSystem paymentSystem;
                    try{
                        paymentSystem = PstPaymentSystem.fetchExc(this.oidPay);
                    }catch(Exception ex){
                        paymentSystem = new PaymentSystem();
                    }

                     drawPrint+=drawPaymentContinues(this.paymentTypes, this.displayPaymentTypes, 
                                paymentSystem, this.ccNames, this.ccNumbers, this.ccBanks, 
                                this.ccValids, this.ccCharges, this.payAmounts, billMain, 
                                location, this.printTypes);
                }
            }
            
        
            
           
	 return drawPrint;
     }
     
     public String drawContentDefaultCompliment(BillMain billMain, String printItemLine, Location location){
	String drawPrint = "";
        Vector listItem = PstBillDetail.listMat(0, 0, 
                     "CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                    + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0'", "");
        
        String printOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");
        String printPaperType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
        
        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
        if (printOutType.equals("0")){

            if(printItemLine.equals("0")){
                drawPrint += drawItemLineDefaultCompliment(listItem);
            }else{
                drawPrint += drawItemLineCustomCompliment(listItem);
            }
        
            double totalQty = PstBillDetail.getQty(billMain.getOID());
            double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
            CurrencyType currencyType;
	 
            try{
                currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
            }catch(Exception ex){
                currencyType = new CurrencyType();
            }
	 
            String displayDiscPct = "";
            if(billMain.getDiscPct() > 0){
               displayDiscPct = "("+billMain.getDiscPct()+"%) ";
            }
	 
	 
	 //billMain = PstBillMain.fetchExc(oidBillMain);
            double service = 0;
            double tax = 0;
            if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
                service = 0;
                tax = 0;
            }else{
                service = billMain.getServiceValue();
                tax = billMain.getTaxValue();
            }
            double total = 0;
            total = (totalPrice)-billMain.getDiscount()+service+tax;
                drawPrint+= ""
                + "<div class='row'>"
                   + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                   + "<div class='col-xs-4'>"
                       + "Total Qty"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+totalQty
                   + "</div>"
                   + "<div class='col-xs-4'>"
                       + ""
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Total Amount"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                        +" "+Formater.formatNumber(0,"#,###")
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Discount"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": <small>"+displayDiscPct+"</small>"+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + Formater.formatNumber(0,"#,###")
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Total"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(0,"#,###")
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Service"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": <small>("+billMain.getServicePct()+"%)</small> "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(0, "#,###")
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Tax"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": <small>("+billMain.getTaxPercentage()+"%)</small> "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(0, "#,###")
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Total"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(0, "#,###")
                   + "</div>"
                   + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                + "</div>";
            }
        
            if (printPaperType.equals("1")){
                if ((paperHeights-heightTotalRow)>=5){
             
                    PaymentSystem paymentSystem;
                    try{
                        paymentSystem = PstPaymentSystem.fetchExc(this.oidPay);
                    }catch(Exception ex){
                        paymentSystem = new PaymentSystem();
                    }

                     drawPrint+=drawPayment(this.paymentTypes, this.displayPaymentTypes, 
                                paymentSystem, this.ccNames, this.ccNumbers, this.ccBanks, 
                                this.ccValids, this.ccCharges, this.payAmounts, billMain, 
                                location, this.printTypes);
                }
            }
            
        
            
           
	 return drawPrint;
     }
     
     public String drawContentDefaultFoc(MatCosting matCosting, String printItemLine, Location location){
	String drawPrint = "";
        String whereItem ="";
        Vector listItem ;
        
        whereItem =""
            + " DFI."+PstMatCostingItem.fieldNames[PstMatCostingItem.FLD_PARENT_ID]+"=0";
        
        listItem = PstMatCostingItem.list( 0, 0,  matCosting.getOID(),  whereItem);
        
        if(printItemLine.equals("0")){
            drawPrint += drawItemLineDefaultFoc(listItem);
        }else{
            drawPrint += drawItemLineCustomFoc(listItem);
        }
        
        double totalQty = PstMatCostingItem.getQty(matCosting.getOID());
        double totalPrice = 0;
        
        double total = 0;     
        drawPrint+= ""
            + "<div class='row'>"
               + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
               + "<div class='col-xs-4'>"
                   + "Total Qty"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": "+totalQty
               + "</div>"
               + "<div class='col-xs-4'>"
                   + ""
               + "</div>"
            + "</div>";
          
	return drawPrint;
     }
     
     public String drawContentDefaultReprint(BillMain billMain, String printItemLine, Location location, String full){
	String drawPrint = "";
        Vector listItem = PstBillDetail.listMat(0, 0, 
                     "CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                    + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0'", "");
        
        String printOutType = full;
        
        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
        if (printOutType.equals("0")){

            if(printItemLine.equals("0")){
                drawPrint += drawItemLineDefault(listItem);
            }else{
                drawPrint += drawItemLineCustom(listItem);
            }
        
            double totalQty = PstBillDetail.getQty(billMain.getOID());
            double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
            CurrencyType currencyType;
	 
            try{
                currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
            }catch(Exception ex){
                currencyType = new CurrencyType();
            }
	 
            String displayDiscPct = "";
            if(billMain.getDiscPct() > 0){
               displayDiscPct = "("+billMain.getDiscPct()+"%) ";
            }
	 
	 
	 //billMain = PstBillMain.fetchExc(oidBillMain);
            double service = 0;
            double tax = 0;
            if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
                service = 0;
                tax = 0;
            }else{
                service = billMain.getServiceValue();
                tax = billMain.getTaxValue();
            }
            double total = 0;
            total = (totalPrice)-billMain.getDiscount()+service+tax;
                drawPrint+= ""
                + "<div class='row'>"
                   + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                   + "<div class='col-xs-4'>"
                       + "Total Qty"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+totalQty
                   + "</div>"
                   + "<div class='col-xs-4'>"
                       + ""
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Total Amount"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                        +" "+Formater.formatNumber(totalPrice,"#,###")
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Discount"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": <small>"+displayDiscPct+"</small>"+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + Formater.formatNumber(billMain.getDiscount(),"#,###")
                   + "</div>"
                + "</div>";
                if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){}
                else{
                drawPrint += ""
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Total"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(totalPrice-billMain.getDiscount(),"#,###")
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Service"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": <small>("+billMain.getServicePct()+"%)</small> "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(service, "#,###")
                   + "</div>"
                + "</div>"
                + "<div class='row'>"
                   + "<div class='col-xs-4'>"
                       + "Tax"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ": <small>("+billMain.getTaxPercentage()+"%)</small> "+currencyType.getCode()
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;'>"
                       + ""+Formater.formatNumber(tax, "#,###")
                   + "</div>"
                + "</div>";
                }
                drawPrint += ""
                + "<div class='row'>"
                   + "<div class='col-xs-4' style='font-size: 13px;'>"
                       + "<b>Total</b>"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                       + ": <b>"+currencyType.getCode() +"</b>"
                   + "</div>"
                   + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                       + "<b>"+Formater.formatNumber(total, "#,###")+"</b>"
                   + "</div>"
                   + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                + "</div>";
            }
            if ((paperHeights-heightTotalRow)>=5){
             
                PaymentSystem paymentSystem;
                try{
                    paymentSystem = PstPaymentSystem.fetchExc(this.oidPay);
                }catch(Exception ex){
                    paymentSystem = new PaymentSystem();
                }

                 drawPrint+=drawPayment(this.paymentTypes, this.displayPaymentTypes, 
                            paymentSystem, this.ccNames, this.ccNumbers, this.ccBanks, 
                            this.ccValids, this.ccCharges, this.payAmounts, billMain, 
                            location, this.printTypes);
            }
        
            
           
	 return drawPrint;
     }
     
     
     public String drawContentDefaultNew(BillMain billMain, String printItemLine, Location location,double services, double taxs, double discs){
	 String drawPrint = "";
            String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
            String useForRaditya = PstSystemProperty.getValueByName("USE_FOR_RADITYA");
            String formulaCash = PstSystemProperty.getValueByName("CASH_FORMULA");
        
            double AdditionalPrice = 0;
            double itemPrice = 0;
            Material material = new Material();
            Category cat = new Category();
            Vector listItem = new Vector();
            if(itemGroup.equals("1")){
                listItem = PstBillDetail.listMatDistinct(0, 0, 
			 "CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                        + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0' "
                        + "AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0", "");
            }else{
                listItem = PstBillDetail.listMat(0, 0, 
                        "CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                        + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0' "
                        + "AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0", "");
            }
              if (useForRaditya.equals("1")) {
                
				   for(int i = 0; i < listItem.size(); i++){
				       Vector data = (Vector) listItem.get(i);
				       Billdetail billdetail = (Billdetail) data.get(0);
           try {
                material = PstMaterial.fetchExc(billdetail.getMaterialId());
                cat = PstCategory.fetchExc(material.getOID());
                if (checkString(formulaCash, "HPP") > -1) {
                  formulaCash = formulaCash.replaceAll("HPP", "" + material.getAveragePrice());
                }
//                if (checkString(formulaCash, "INCREASE") > -1) {
//                  formulaCash = formulaCash.replaceAll("INCREASE", "" + cat.getKenaikanHarga());
//                }
                double price = getValue(formulaCash);
                itemPrice = price;
         } catch (Exception e) {
         }
              }
              }
	    
	    
	    if(printItemLine.equals("0")){
                drawPrint += drawItemLineDefault(listItem);
	    }else{
		drawPrint += drawItemLineCustom(listItem);
	    }
	     
	 double totalQty = PstBillDetail.getQty(billMain.getOID());
	 double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
	 double totalExpense = PstBillDetail.getTotalExpense(billMain.getOID());
	 CurrencyType currencyType;
	 
	 try{
	     currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
	 }catch(Exception ex){
	     currencyType = new CurrencyType();
	 }
	 
	 String displayDiscPct = "";
	 if(billMain.getDiscPct() > 0){
	    displayDiscPct = "("+discs+"%) ";
	 }
	 
	 
	 //billMain = PstBillMain.fetchExc(oidBillMain);
	 double service = 0;
	 double tax = 0;
	if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
	    service = 0;
	    tax = 0;
	}else{
	    service = services;
	    tax = taxs;
	}
        double nomDIsc = totalPrice * discs /100; 
        double totalPrice2 = 0;
         if(useForRaditya.equals("1")){
             totalPrice2 = totalPrice-nomDIsc + totalExpense;
         }else{
             totalPrice2 = totalPrice-nomDIsc;
         }
        double nomService = totalPrice2 * service /100;
        double nomTax = totalPrice2 * tax /100;
        
	double total = 0;
	total = totalPrice2+nomService+nomTax;
	 drawPrint+= ""
	 + "<div class='row'>"
	    + "<div class='col-xs-12'>"
		 + "<hr style='border:1px dashed #000;margin:0px;'>"
	     + "</div>"
	    + "<div class='col-xs-4'>"
		+ "Total Qty"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": "+totalQty
	    + "</div>"
	    + "<div class='col-xs-4'>"
		+ ""
	    + "</div>"
	 + "</div>";
         
         if(useForRaditya.equals("1")){
             drawPrint+= "<div class='row'>"
                + "<div class='col-xs-12'>"
                     + "<hr style='border:1px dashed #000;margin:0px;'>"
                 + "</div>"
                + "<div class='col-xs-4'>"
                    + "Other Expense"
                + "</div>"
                + "<div class='col-xs-4' style='text-align:right;'>"
                    + ": "+totalExpense
                + "</div>"
                + "<div class='col-xs-4'>"
                    + ""
                + "</div>"
             + "</div>";
         }
	 drawPrint+= "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Total Amount"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": "+currencyType.getCode()
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		 +" "+Formater.formatNumber(totalPrice,"#,###")
	    + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Discount"
	    + "</div>"
            + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": <small>"+displayDiscPct+"</small> "+currencyType.getCode()
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ Formater.formatNumber(nomDIsc,"#,###")
	    + "</div>"
	 + "</div>";
         if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
	 }else{
         drawPrint += ""
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Total</b>"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": "+currencyType.getCode()+""
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ""+Formater.formatNumber(totalPrice2,"#,###")+""
	    + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Service"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": <small>("+service+"%)</small> "+currencyType.getCode()
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ""+Formater.formatNumber(nomService, "#,###")
	    + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Tax"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": <small>("+tax+"%)</small> "+currencyType.getCode()
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ""+Formater.formatNumber(nomTax, "#,###")
	    + "</div>"
	 + "</div>";
         }
         drawPrint += ""
	 + "<div class='row'>"
	    + "<div class='col-xs-4' style='font-size: 13px;'>"
		+ "<b>Total</b>"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right; font-size: 13px;'>"
		+ ": <b>"+currencyType.getCode()+"</b>"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right; font-size: 13px;'>"
		+ "<b>"+Formater.formatNumber(total, "#,###")+"</b>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		 + "<hr style='border:1px dashed #000;margin:0px;'>"
	     + "</div>"
	 + "</div>";
	    
	    
	 return drawPrint;
     }
     
    public static int checkString(String strObject, String toCheck) {
        if (toCheck == null || strObject == null) {
            return -1;
        }
        if (strObject.startsWith("=")) {
            strObject = strObject.substring(1);
        }

        String[] parts = strObject.split(" ");
        if (parts.length > 0) {
            for (int i = 0; i < parts.length; i++) {
                String p = parts[i];
                if (toCheck.trim().equalsIgnoreCase(p.trim())) {
                    return i;
                };
            }
        }
        return -1;
    }
    
    public static double getValue(String formula) {
        DBResultSet dbrs = null;
        double compValueX = 0;
        try {
            String sql = "SELECT (" + formula + ")";
            dbrs = DBHandler.execQueryResult(sql);
            ResultSet rs = dbrs.getResultSet();
            while (rs.next()) {
                compValueX = rs.getDouble(1);
            }

            rs.close();
            return compValueX;
        } catch (Exception e) {
            return 0;            
        } finally {
            DBResultSet.close(dbrs);
        }
    }
    
    public int convertInteger(int scale, double val){
        BigDecimal bDecimal = new BigDecimal(val);
        bDecimal = bDecimal.setScale(scale, RoundingMode.HALF_UP);
        return bDecimal.intValue();
    }
     
     public String drawContentGuidePrice(BillMain billMain, String printItemLine, Location location,double services, double taxs, double discs){
	 String drawPrint = "";
            String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
            Vector listItem = new Vector();
            if(itemGroup.equals("1")){
                listItem = PstBillDetail.listMatDistinct(0, 0, 
			 "CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                        + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0' "
                        + "AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0", "");
            }else{
                listItem = PstBillDetail.listMat(0, 0, 
                        "CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                        + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0' "
                        + "AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0", "");
            }
	    
	    
	    if(printItemLine.equals("0")){
                drawPrint += drawItemLineGuidePrice(listItem);
	    }else{
		drawPrint += drawItemLineCustom(listItem);
	    }
	     
	 double totalQty = PstBillDetail.getQty(billMain.getOID());
	 double totalPrice = PstBillDetail.getTotalGuidePrice(billMain.getOID());
	 CurrencyType currencyType;
	 
	 try{
	     currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
	 }catch(Exception ex){
	     currencyType = new CurrencyType();
	 }
	 
	 String displayDiscPct = "";
	 if(billMain.getDiscPct() > 0){
	    displayDiscPct = "("+discs+"%) ";
	 }
	 
	 
	 //billMain = PstBillMain.fetchExc(oidBillMain);
	 double service = 0;
	 double tax = 0;
	if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
	    service = 0;
	    tax = 0;
	}else{
	    service = services;
	    tax = taxs;
	}
        double nomDIsc = totalPrice * discs /100; 
        double totalPrice2 = totalPrice-nomDIsc;
        double nomService = totalPrice2 * service /100;
        double nomTax = totalPrice2 * tax /100;
        
	double total = 0;
	total = totalPrice2+nomService+nomTax;
	 drawPrint+= ""
	 + "<div class='row'>"
	    + "<div class='col-xs-12'>"
		 + "<hr style='border:1px dashed #000;margin:0px;'>"
	     + "</div>"
	    + "<div class='col-xs-4'>"
		+ "Total Qty"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": "+totalQty
	    + "</div>"
	    + "<div class='col-xs-4'>"
		+ ""
	    + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Total Amount"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": "+currencyType.getCode()
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		 +" "+Formater.formatNumber(totalPrice,"#,###")
	    + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Discount"
	    + "</div>"
            + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": <small>"+displayDiscPct+"</small> "+currencyType.getCode()
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ Formater.formatNumber(nomDIsc,"#,###")
	    + "</div>"
	 + "</div>";
         if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
	 }else{
         drawPrint += ""
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Total</b>"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": "+currencyType.getCode()+""
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ""+Formater.formatNumber(totalPrice2,"#,###")+""
	    + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Service"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": <small>("+service+"%)</small> "+currencyType.getCode()
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ""+Formater.formatNumber(nomService, "#,###")
	    + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Tax"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": <small>("+tax+"%)</small> "+currencyType.getCode()
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ""+Formater.formatNumber(nomTax, "#,###")
	    + "</div>"
	 + "</div>";
         }
         drawPrint += ""
	 + "<div class='row'>"
	    + "<div class='col-xs-4' style='font-size: 13px;'>"
		+ "<b>Total</b>"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right; font-size: 13px;'>"
		+ ": <b>"+currencyType.getCode()+"</b>"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right; font-size: 13px;'>"
		+ "<b>"+Formater.formatNumber(total, "#,###")+"</b>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		 + "<hr style='border:1px dashed #000;margin:0px;'>"
	     + "</div>"
	 + "</div>";
	    
	    
	 return drawPrint;
     }
     
     
     //PRINT BODY CONTENT CUSTOM :: WITH SHORT BY CATEGORY
     public String drawContentCustomNew(BillMain billMain, String printItemLine, Location location,double taxs, double services, double discs){
	String drawPrint = "";
	heightTotalRow =0;
         
        double totalQtyPerCategory=0;
        double totalPricePerCategory=0;
        double totalQtyPerCategoryBeverage=0;
        double totalPricePerCategoryBeverage=0;      
        double totalQtyPerCategoryOther=0;
        double totalPricePerCategoryOther=0; 
        
        String printPaperType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
        
        Location locationList = new Location();
        double taxView=0;
        double serviceView=0;
        try{
            locationList = PstLocation.fetchExc(billMain.getLocationId());
            if(taxs==0){
                taxView =0;
            }else{
                taxView = locationList.getTaxView();
            }
            if(services==0){
                serviceView = 0;
            }else{
                serviceView = locationList.getServiceView();
            }
        }catch(Exception ex){
        }
        
        //FOOD 
        Vector listCategoryFood = PstCategory.list(0, 0, 
            ""//+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
            + " "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"='0'", "");
          
        if(listCategoryFood.size() != 0){
            int countData = 0;
            String whereCategory="";
            for(int i = 0; i< listCategoryFood.size(); i++){
                Category category = (Category) listCategoryFood.get(i);
                
                totalQtyPerCategory += PstBillDetail.getQtyPerCategory(billMain.getOID(), category.getOID());
		totalPricePerCategory += PstBillDetail.getTotalPricePerCategory(billMain.getOID(), category.getOID());
                
                if (whereCategory.length()>0){
                    whereCategory += ""
                    + " OR pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                }else{
                    whereCategory += ""
                    + " pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                }
                
            }
            
            String whereBillDetail = ""
                + " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0' "
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0";
            if (whereCategory.length()>0){
                whereBillDetail += " AND ( "+whereCategory+" )";
            }
            String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
            Vector listItem = new Vector();
            if(itemGroup.equals("1")){
                listItem = PstBillDetail.listMatDistinct(0, 0,whereBillDetail, "");
            }else{
                listItem = PstBillDetail.listMat(0, 0,whereBillDetail, "");
            }
 
            if(listItem.size() != 0){
                String margin="";
                if(countData > 0){
                    margin = "style='margin-top:10px;'";
                }
                drawPrint += ""
                + "<div class='row' "+margin+">"
                    + "<div class='col-xs-12'><br><b>FOOD</b></div>"
                + "</div>";
                heightTotalRow = heightTotalRow + heightRow;
                if(printItemLine.equals("0")){
                    drawPrint += drawItemLineDefault(listItem);
                }else{
                    drawPrint += drawItemLineCustom(listItem);
                }
                for (int d = 0 ; d<listItem.size();d++){
                    heightTotalRow = heightTotalRow + heightRow;
                }
                     
                drawPrint += ""
                    + "<div class='row'>"
                        + "<div class='col-xs-6'>"
                            + "<b>TOTAL FOOD</b>"
                        + "</div>"
                        + "<div class='col-xs-6' style='text-align:right;'>"
                            + ""+Formater.formatNumber(totalPricePerCategory,"#,###")
                        + "</div>"
                    + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    countData+=1;
            }

            drawPrint += ""
            + "<div class='row'>"
                + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
            + "</div>";
            heightTotalRow = heightTotalRow + heightRow;  
        }
          
        //BEVERAGE
        Vector listCategory = PstCategory.list(0, 0, 
            ""//+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
            + " "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"='1'", "");
	 
        if(listCategory.size() != 0){
            int countData = 0;
            String whereCategory="";
            for(int i = 0; i< listCategory.size(); i++){
                Category category = (Category) listCategory.get(i); 
                
                totalQtyPerCategoryBeverage += PstBillDetail.getQtyPerCategory(billMain.getOID(), category.getOID());
                totalPricePerCategoryBeverage += PstBillDetail.getTotalPricePerCategory(billMain.getOID(), category.getOID());
                
                if (whereCategory.length()>0){
                    whereCategory += ""
                    + " OR pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                }else{
                    whereCategory += ""
                    + " pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                }
            
            }
            
            String whereBillDetail = ""
                + " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0' "
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0";
            if (whereCategory.length()>0){
                whereBillDetail += " AND ( "+whereCategory+" )";
            }
		 
            String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
            Vector listItem = new Vector();
            if(itemGroup.equals("1")){
                listItem = PstBillDetail.listMatDistinct(0, 0,whereBillDetail, "");
            }else{
                listItem = PstBillDetail.listMat(0, 0,whereBillDetail, "");
            }
 
            if(listItem.size() != 0){
                String margin="";
                if(countData > 0){
                    margin = "style='margin-top:10px;'";
                }
                drawPrint += ""
                    + "<div class='row' "+margin+">"
                        + "<div class='col-xs-12'><b>BEVERAGE</b></div>"
                    + "</div>";
                heightTotalRow = heightTotalRow + heightRow;  
                if(printItemLine.equals("0")){
                    drawPrint += drawItemLineDefault(listItem);
                }else{
                    drawPrint += drawItemLineCustom(listItem);
                }
                for (int b=0;b<listItem.size();b++){
                   heightTotalRow = heightTotalRow + heightRow;
                }

                drawPrint += ""
                   + "<div class='row'>"
                        + "<div class='col-xs-6'>"
                           + "<b>TOTAL BEVERAGE</b> "
                        + "</div>"
                        + "<div class='col-xs-6' style='text-align:right;'>"
                           + ""+Formater.formatNumber(totalPricePerCategoryBeverage,"#,###")
                        + "</div>"
                   + "</div>";
                heightTotalRow = heightTotalRow + heightRow;  
                countData+=1;
             }

            drawPrint += ""
            + "<div class='row'>"
                + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
            + "</div>";
            heightTotalRow = heightTotalRow + heightRow;  
        }
         
        //OTHER       
        Vector listCategoryOther = PstCategory.list(0, 0, 
            ""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
            + "AND ("+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"<>'0' AND "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"<>'1' )", "");
          
        if(listCategoryOther.size() != 0){
            int countData = 0;
            String whereCategory="";
            for(int i = 0; i< listCategoryOther.size(); i++){
                Category category = (Category) listCategoryOther.get(i); 
                
                totalQtyPerCategoryOther += PstBillDetail.getQtyPerCategory(billMain.getOID(), category.getOID());
                totalPricePerCategoryOther += PstBillDetail.getTotalPricePerCategory(billMain.getOID(), category.getOID());
                
                if (whereCategory.length()>0){
                    whereCategory += ""
                    + " OR pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                }else{
                    whereCategory += ""
                    + " pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                }
            }
            
            String whereBillDetail = ""
                + " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0'";
            if (whereCategory.length()>0){
                whereBillDetail += " AND ( "+whereCategory+" )";
            }
		 
            String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
            Vector listItem = new Vector();
            if(itemGroup.equals("1")){
                listItem = PstBillDetail.listMatDistinct(0, 0,whereBillDetail, "");
            }else{
                listItem = PstBillDetail.listMat(0, 0,whereBillDetail, "");
            }	
		 
            if(listItem.size() != 0){
                String margin="";
                if(countData > 0){
                    margin = "style='margin-top:10px;'";
                }
                drawPrint += ""
                + "<div class='row' "+margin+">"
                    + "<div class='col-xs-12'><b>OTHER</b></div>"
                + "</div>";
                heightTotalRow = heightTotalRow + heightRow;  
                if(printItemLine.equals("0")){
                    drawPrint += drawItemLineDefault(listItem);
                }else{
                    drawPrint += drawItemLineCustom(listItem);
                }
                for (int j=0;j<listItem.size();j++){
                   heightTotalRow = heightTotalRow + heightRow;
                }
                       
                drawPrint += ""
                    + "<div class='row'>"
                         + "<div class='col-xs-6'>"
                            + "<b>TOTAL OTHER</b>"
                         + "</div>"
                         + "<div class='col-xs-6' style='text-align:right;'>"
                            + ""+Formater.formatNumber(totalPricePerCategoryOther,"#,###")
                         + "</div>"
                    + "</div>";
                heightTotalRow = heightTotalRow + heightRow;  
                countData+=1;
            }
        }
 
        double totalQty = PstBillDetail.getQty(billMain.getOID());
        double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
        CurrencyType currencyType;
	 
        try{
            currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
        }catch(Exception ex){
            currencyType = new CurrencyType();
        }
	 
        String displayDiscPct = "";
        if(billMain.getDiscPct() > 0){
           displayDiscPct = "("+discs+"%) ";
        }
	 
	 
	 //billMain = PstBillMain.fetchExc(oidBillMain);
         double nomDisc = discs * totalPrice /100;
         double totalPrice2 = totalPrice-nomDisc;
	 double service = services * totalPrice2 /100;
	 double tax = taxs * totalPrice2 /100;
        
	if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
	    service = 0;
	    tax = 0;
	}else{
	    service = service;
	    tax = tax;
	}
	double total = 0;
	total = totalPrice2+service+tax;
	 drawPrint+= ""
	 + "<div class='row'>"
	    + "<div class='col-xs-12'>" 
	     + "</div>"
	    + "<div class='col-xs-4'>"
		+ "Total Qty"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": "+totalQty
	    + "</div>"
	    + "<div class='col-xs-4'>"
		+ ""
	    + "</div>"
	 + "</div>"
         
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Total Amount"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": "+currencyType.getCode()
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		 +" "+Formater.formatNumber(totalPrice,"#,###")
	    + "</div>"
	 + "</div>"
                 
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Discount"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": <small>("+discs+"%)</small>" +currencyType.getCode()
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ Formater.formatNumber(nomDisc,"#,###")
	    + "</div>"
	 + "</div>"
                 
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Total"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": "+currencyType.getCode()+""
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ""+Formater.formatNumber(totalPrice2,"#,###")+""
	    + "</div>"
	 + "</div>"
                 
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Service"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": <small>("+serviceView+"%)</small> "+currencyType.getCode()
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ""+Formater.formatNumber(service, "#,###")
	    + "</div>"
	 + "</div>"
                 
	 + "<div class='row'>"
	    + "<div class='col-xs-4'>"
		+ "Tax"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ": <small>("+taxView+"%)</small> "+currencyType.getCode()
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;'>"
		+ ""+Formater.formatNumber(tax, "#,###")
	    + "</div>"
	 + "</div>"
                 
	 + "<div class='row'>"
	    + "<div class='col-xs-4' style='font-size: 13px;'>"
		+ "<b>Total</b>"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
		+ ": <b>"+currencyType.getCode()+"</b>"
	    + "</div>"
	    + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
		+ "<b>"+Formater.formatNumber(total, "#,###")+"</b>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		 + "<hr style='border:1px dashed #000;margin:0px;'>"
	     + "</div>"
	 + "</div>";
        heightTotalRow = heightTotalRow + (7*heightRow);  
         
        if ((paperHeights-heightTotalRow)>=5){
             
            PaymentSystem paymentSystem;
            try{
                paymentSystem = PstPaymentSystem.fetchExc(this.oidPay);
            }catch(Exception ex){
                paymentSystem = new PaymentSystem();
            }
             
             //drawPrint+=drawPayment(this.paymentTypes, this.displayPaymentTypes, 
             //                       paymentSystem, this.ccNames, this.ccNumbers, this.ccBanks, 
             //                       this.ccValids, this.ccCharges, this.payAmounts, billMain, 
             //                       location, this.printTypes);
         }
        return drawPrint;
     }
     
     //PRINT CONTENT CUSTOM NEW
     //PRINT BODY CONTENT CUSTOM :: WITH SHORT BY CATEGORY
     public String drawContentCustom(BillMain billMain, String printItemLine, Location location){
        String drawPrint = "";
        heightTotalRow =0;
        
        double totalQtyPerCategory=0;
        double totalPricePerCategory=0;
        
        double totalQtyPerCategoryBeverage=0;
        double totalPricePerCategoryBeverage=0;
        
        double totalQtyPerCategoryOther=0;
        double totalPricePerCategoryOther=0;
         
        String printOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");
        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
        
        String printPaperType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
            
        if (printOutType.equals("0")){
         //FOOD 
            Vector listCategoryFood = PstCategory.list(0, 0, 
		 //""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
		  " "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"='0'", "");
          
            if(listCategoryFood.size() != 0){
                int countData = 0;
                String whereCategory="";
                for(int i = 0; i< listCategoryFood.size(); i++){
                    Category category = (Category) listCategoryFood.get(i);
                    
                    totalQtyPerCategory += PstBillDetail.getQtyPerCategory(billMain.getOID(), category.getOID());
                    totalPricePerCategory += PstBillDetail.getTotalPricePerCategory(billMain.getOID(), category.getOID());

                    if (whereCategory.length()>0){
                        whereCategory += ""
                        + " OR pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }else{
                        whereCategory += ""
                        + " pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }
                
                }
                
                String whereBillDetail = ""
                    + " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                    + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0' "
                    + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0";
                if (whereCategory.length()>0){
                    whereBillDetail += " AND ( "+whereCategory+" )";
                }
		 
                String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
                Vector listItem = new Vector();
                if(itemGroup.equals("1")){
                    listItem = PstBillDetail.listMatDistinct(0, 0,whereBillDetail, "");
                }else{
                    listItem = PstBillDetail.listMat(0, 0,whereBillDetail, "");
                }		 

                if(listItem.size() != 0){
                    String margin="";
                    if(countData > 0){
                        margin = "style='margin-top:10px;'";
                    }
                    drawPrint += ""
                        + "<div class='row' "+margin+">"
                            + "<div class='col-xs-12'><br><b>FOOD</b></div>"
                        + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;
                    if(printItemLine.equals("0")){
                        drawPrint += drawItemLineDefault(listItem);
                    }else{
                        drawPrint += drawItemLineCustom(listItem);
                    }
                    for (int d = 0 ; d<listItem.size();d++){
                        heightTotalRow = heightTotalRow + heightRow;
                    }
                     
                    drawPrint += ""
                        + "<div class='row'>"
                            + "<div class='col-xs-6'>"
                               + "<b>TOTAL FOOD</b>"
                            + "</div>"
                            + "<div class='col-xs-6' style='text-align:right;'>"
                               + ""+Formater.formatNumber(totalPricePerCategory,"#,###")
                            + "</div>"
                       + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    countData+=1;
                }

                drawPrint += ""
                + "<div class='row'>"
                    + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                + "</div>";
                heightTotalRow = heightTotalRow + heightRow;  
            }
            
            //BEVERAGE
            Vector listCategory = PstCategory.list(0, 0, 
		 //""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
		  " "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"='1'", "");
	 
            if(listCategory.size() != 0){
                int countData = 0;
                String whereCategory="";
                for(int i = 0; i< listCategory.size(); i++){
                    Category category = (Category) listCategory.get(i);
                    totalQtyPerCategoryBeverage += PstBillDetail.getQtyPerCategory(billMain.getOID(), category.getOID());
                    totalPricePerCategoryBeverage += PstBillDetail.getTotalPricePerCategory(billMain.getOID(), category.getOID());

                    if (whereCategory.length()>0){
                        whereCategory += ""
                        + " OR pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }else{
                        whereCategory += ""
                        + " pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }
                
                }
                String whereBillDetail = ""
                + " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0' "
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0";
                if (whereCategory.length()>0){
                    whereBillDetail += " AND ( "+whereCategory+" )";
                }

                String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
                Vector listItem = new Vector();
                if(itemGroup.equals("1")){
                    listItem = PstBillDetail.listMatDistinct(0, 0,whereBillDetail, "");
                }else{
                    listItem = PstBillDetail.listMat(0, 0,whereBillDetail, "");
                }

                if(listItem.size() != 0){
                    String margin="";
                    if(countData > 0){
                        margin = "style='margin-top:10px;'";
                    }
                    drawPrint += ""
                        + "<div class='row' "+margin+">"
                            + "<div class='col-xs-12'><b>BEVERAGE</b></div>"
                        + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    if(printItemLine.equals("0")){
                        drawPrint += drawItemLineDefault(listItem);
                    }else{
                        drawPrint += drawItemLineCustom(listItem);
                    }
                    for (int b=0;b<listItem.size();b++){
                       heightTotalRow = heightTotalRow + heightRow;
                    }

                    drawPrint += ""
                       + "<div class='row'>"
                            + "<div class='col-xs-6'>"
                               + "<b>TOTAL BEVERAGE</b> "
                            + "</div>"
                            + "<div class='col-xs-6' style='text-align:right;'>"
                               + ""+Formater.formatNumber(totalPricePerCategoryBeverage,"#,###")
                            + "</div>"
                       + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    countData+=1;
                }

                drawPrint += ""
                    + "<div class='row'>"
                        + "<div class='col-xs-12'>"
                            + "<hr style='border:1px dashed #000;margin:0px;'>"
                        + "</div>"
                    + "</div>";
                heightTotalRow = heightTotalRow + heightRow;  
            }
         
            //OTHER
            
            Vector listCategoryOther = PstCategory.list(0, 0, 
                ""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
                + "AND ("+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"<>'0' AND "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"<>'1' )", "");
          
            if(listCategoryOther.size() != 0){
                int countData = 0;
                String whereCategory="";
                for(int i = 0; i< listCategoryOther.size(); i++){
                    Category category = (Category) listCategoryOther.get(i);
                    
                    totalQtyPerCategoryOther += PstBillDetail.getQtyPerCategory(billMain.getOID(), category.getOID());
                    totalPricePerCategoryOther += PstBillDetail.getTotalPricePerCategory(billMain.getOID(), category.getOID());

                    if (whereCategory.length()>0){
                        whereCategory += ""
                        + " OR pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }else{
                        whereCategory += ""
                        + " pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }
                
                }
                String whereBillDetail = ""
                + " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0' "
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0";
                if (whereCategory.length()>0){
                    whereBillDetail += " AND ( "+whereCategory+" )";
                }
		 
                String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
                Vector listItem = new Vector();
                if(itemGroup.equals("1")){
                    listItem = PstBillDetail.listMatDistinct(0, 0,whereBillDetail, "");
                }else{
                    listItem = PstBillDetail.listMat(0, 0,whereBillDetail, "");
                }
		
                if(listItem.size() != 0){
                    String margin="";
                    if(countData > 0){
                        margin = "style='margin-top:10px;'";
                    }
                    drawPrint += ""
                    + "<div class='row' "+margin+">"
                        + "<div class='col-xs-12'><b>OTHER</b></div>"
                    + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    if(printItemLine.equals("0")){
                        drawPrint += drawItemLineDefault(listItem);
                    }else{
                        drawPrint += drawItemLineCustom(listItem);
                    }
                    for (int j=0;j<listItem.size();j++){
                       heightTotalRow = heightTotalRow + heightRow;
                    }

                    drawPrint += ""
                        + "<div class='row'>"
                             + "<div class='col-xs-6'>"
                                + "<b>TOTAL OTHER</b>"
                             + "</div>"
                             + "<div class='col-xs-6' style='text-align:right;'>"
                                + ""+Formater.formatNumber(totalPricePerCategoryOther,"#,###")
                             + "</div>"
                        + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    countData+=1;
                }  
            }
            /////
         
            double totalQty = PstBillDetail.getQty(billMain.getOID());
            double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
            CurrencyType currencyType;

            try{
                currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
            }catch(Exception ex){
                currencyType = new CurrencyType();
            }

            String displayDiscPct = "";
            if(billMain.getDiscPct() > 0){
               displayDiscPct = "("+billMain.getDiscPct()+"%) ";
            }


            //billMain = PstBillMain.fetchExc(oidBillMain);
            double service = 0;
            double tax = 0;
            if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
                service = 0;
                tax = 0;
            }else{
                service = billMain.getServiceValue();
                tax = billMain.getTaxValue();
            }
            double total = 0;
            total = (totalPrice)-billMain.getDiscount()+service+tax;
            drawPrint+= ""
            + "<div class='row'>"
               + "<div class='col-xs-12'>" 
                + "</div>"
               + "<div class='col-xs-4'>"
                   + "Total Qty"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": "+totalQty
               + "</div>"
               + "<div class='col-xs-4'>"
                   + ""
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Total Amount"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": "+currencyType.getCode()
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                    +" "+Formater.formatNumber(totalPrice,"#,###")
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Discount"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": <small>"+displayDiscPct+"</small>"+currencyType.getCode()
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + Formater.formatNumber(billMain.getDiscount(),"#,###")
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Total"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": "+currencyType.getCode()+""
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ""+Formater.formatNumber(totalPrice-billMain.getDiscount(),"#,###")+""
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Service"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": <small>("+billMain.getServicePct()+"%)</small> "+currencyType.getCode()
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ""+Formater.formatNumber(service, "#,###")
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Tax"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": <small>("+billMain.getTaxPercentage()+"%)</small> "+currencyType.getCode()
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ""+Formater.formatNumber(tax, "#,###")
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4' style='font-size: 13px;'>"
                   + "<b>Total</b>"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                   + ": <b>"+currencyType.getCode()+"</b>"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                   + "<b>"+Formater.formatNumber(total, "#,###")+"</b>"
               + "</div>"
               + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
            + "</div>";
            heightTotalRow = heightTotalRow + (7*heightRow);  
        } 
        if (printPaperType.equals("1")){
            if ((paperHeights-heightTotalRow)>=5){
             
                PaymentSystem paymentSystem;
                try{
                    paymentSystem = PstPaymentSystem.fetchExc(this.oidPay);
                }catch(Exception ex){
                    paymentSystem = new PaymentSystem();
                }

                 drawPrint+=drawPayment(this.paymentTypes, this.displayPaymentTypes, 
                                        paymentSystem, this.ccNames, this.ccNumbers, this.ccBanks, 
                                        this.ccValids, this.ccCharges, this.payAmounts, billMain, 
                                        location, this.printTypes);
             }
        }
         
	 return drawPrint;
     }
     
     
     public String drawContentCustomContinuesPayment(BillMain billMain, String printItemLine, Location location){
        String drawPrint = "";
        heightTotalRow =0;
        
        double totalQtyPerCategory=0;
        double totalPricePerCategory=0;
        
        double totalQtyPerCategoryBeverage=0;
        double totalPricePerCategoryBeverage=0;
        
        double totalQtyPerCategoryOther=0;
        double totalPricePerCategoryOther=0;
         
        String printOutType = PstSystemProperty.getValueByName("CASHIER_PRINT_OUT_TYPE");
        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
        
        String printPaperType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
            
        if (printOutType.equals("0")){
         //FOOD 
            Vector listCategoryFood = PstCategory.list(0, 0, 
		 //""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
		  " "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"='0'", "");
          
            if(listCategoryFood.size() != 0){
                int countData = 0;
                String whereCategory="";
                for(int i = 0; i< listCategoryFood.size(); i++){
                    Category category = (Category) listCategoryFood.get(i);
                    
                    totalQtyPerCategory += PstBillDetail.getQtyPerCategory(billMain.getOID(), category.getOID());
                    totalPricePerCategory += PstBillDetail.getTotalPricePerCategory(billMain.getOID(), category.getOID());

                    if (whereCategory.length()>0){
                        whereCategory += ""
                        + " OR pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }else{
                        whereCategory += ""
                        + " pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }
                
                }
                
                String whereBillDetail = ""
                    + " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                    + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0' "
                    + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0";
                if (whereCategory.length()>0){
                    whereBillDetail += " AND ( "+whereCategory+" )";
                }
		 
                String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
                Vector listItem = new Vector();
                if(itemGroup.equals("1")){
                    listItem = PstBillDetail.listMatDistinct(0, 0,whereBillDetail, "");
                }else{
                    listItem = PstBillDetail.listMat(0, 0,whereBillDetail, "");
                }		 

                if(listItem.size() != 0){
                    String margin="";
                    if(countData > 0){
                        margin = "style='margin-top:10px;'";
                    }
                    drawPrint += ""
                        + "<div class='row' "+margin+">"
                            + "<div class='col-xs-12'><br><b>FOOD</b></div>"
                        + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;
                    if(printItemLine.equals("0")){
                        drawPrint += drawItemLineDefault(listItem);
                    }else{
                        drawPrint += drawItemLineCustom(listItem);
                    }
                    for (int d = 0 ; d<listItem.size();d++){
                        heightTotalRow = heightTotalRow + heightRow;
                    }
                     
                    drawPrint += ""
                        + "<div class='row'>"
                            + "<div class='col-xs-6'>"
                               + "<b>TOTAL FOOD</b>"
                            + "</div>"
                            + "<div class='col-xs-6' style='text-align:right;'>"
                               + ""+Formater.formatNumber(totalPricePerCategory,"#,###")
                            + "</div>"
                       + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    countData+=1;
                }

                drawPrint += ""
                + "<div class='row'>"
                    + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                + "</div>";
                heightTotalRow = heightTotalRow + heightRow;  
            }
            
            //BEVERAGE
            Vector listCategory = PstCategory.list(0, 0, 
		 //""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
		  " "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"='1'", "");
	 
            if(listCategory.size() != 0){
                int countData = 0;
                String whereCategory="";
                for(int i = 0; i< listCategory.size(); i++){
                    Category category = (Category) listCategory.get(i);
                    totalQtyPerCategoryBeverage += PstBillDetail.getQtyPerCategory(billMain.getOID(), category.getOID());
                    totalPricePerCategoryBeverage += PstBillDetail.getTotalPricePerCategory(billMain.getOID(), category.getOID());

                    if (whereCategory.length()>0){
                        whereCategory += ""
                        + " OR pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }else{
                        whereCategory += ""
                        + " pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }
                
                }
                String whereBillDetail = ""
                + " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0' "
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0";
                if (whereCategory.length()>0){
                    whereBillDetail += " AND ( "+whereCategory+" )";
                }

                String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
                Vector listItem = new Vector();
                if(itemGroup.equals("1")){
                    listItem = PstBillDetail.listMatDistinct(0, 0,whereBillDetail, "");
                }else{
                    listItem = PstBillDetail.listMat(0, 0,whereBillDetail, "");
                }

                if(listItem.size() != 0){
                    String margin="";
                    if(countData > 0){
                        margin = "style='margin-top:10px;'";
                    }
                    drawPrint += ""
                        + "<div class='row' "+margin+">"
                            + "<div class='col-xs-12'><b>BEVERAGE</b></div>"
                        + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    if(printItemLine.equals("0")){
                        drawPrint += drawItemLineDefault(listItem);
                    }else{
                        drawPrint += drawItemLineCustom(listItem);
                    }
                    for (int b=0;b<listItem.size();b++){
                       heightTotalRow = heightTotalRow + heightRow;
                    }

                    drawPrint += ""
                       + "<div class='row'>"
                            + "<div class='col-xs-6'>"
                               + "<b>TOTAL BEVERAGE</b> "
                            + "</div>"
                            + "<div class='col-xs-6' style='text-align:right;'>"
                               + ""+Formater.formatNumber(totalPricePerCategoryBeverage,"#,###")
                            + "</div>"
                       + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    countData+=1;
                }

                drawPrint += ""
                    + "<div class='row'>"
                        + "<div class='col-xs-12'>"
                            + "<hr style='border:1px dashed #000;margin:0px;'>"
                        + "</div>"
                    + "</div>";
                heightTotalRow = heightTotalRow + heightRow;  
            }
         
            //OTHER
            
            Vector listCategoryOther = PstCategory.list(0, 0, 
                ""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
                + "AND ("+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"<>'0' AND "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"<>'1' )", "");
          
            if(listCategoryOther.size() != 0){
                int countData = 0;
                String whereCategory="";
                for(int i = 0; i< listCategoryOther.size(); i++){
                    Category category = (Category) listCategoryOther.get(i);
                    
                    totalQtyPerCategoryOther += PstBillDetail.getQtyPerCategory(billMain.getOID(), category.getOID());
                    totalPricePerCategoryOther += PstBillDetail.getTotalPricePerCategory(billMain.getOID(), category.getOID());

                    if (whereCategory.length()>0){
                        whereCategory += ""
                        + " OR pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }else{
                        whereCategory += ""
                        + " pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }
                
                }
                String whereBillDetail = ""
                + " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0' "
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0";
                if (whereCategory.length()>0){
                    whereBillDetail += " AND ( "+whereCategory+" )";
                }
		 
                String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
                Vector listItem = new Vector();
                if(itemGroup.equals("1")){
                    listItem = PstBillDetail.listMatDistinct(0, 0,whereBillDetail, "");
                }else{
                    listItem = PstBillDetail.listMat(0, 0,whereBillDetail, "");
                }
		
                if(listItem.size() != 0){
                    String margin="";
                    if(countData > 0){
                        margin = "style='margin-top:10px;'";
                    }
                    drawPrint += ""
                    + "<div class='row' "+margin+">"
                        + "<div class='col-xs-12'><b>OTHER</b></div>"
                    + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    if(printItemLine.equals("0")){
                        drawPrint += drawItemLineDefault(listItem);
                    }else{
                        drawPrint += drawItemLineCustom(listItem);
                    }
                    for (int j=0;j<listItem.size();j++){
                       heightTotalRow = heightTotalRow + heightRow;
                    }

                    drawPrint += ""
                        + "<div class='row'>"
                             + "<div class='col-xs-6'>"
                                + "<b>TOTAL OTHER</b>"
                             + "</div>"
                             + "<div class='col-xs-6' style='text-align:right;'>"
                                + ""+Formater.formatNumber(totalPricePerCategoryOther,"#,###")
                             + "</div>"
                        + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    countData+=1;
                }  
            }
            /////
         
            double totalQty = PstBillDetail.getQty(billMain.getOID());
            double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
            CurrencyType currencyType;

            try{
                currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
            }catch(Exception ex){
                currencyType = new CurrencyType();
            }

            String displayDiscPct = "";
            if(billMain.getDiscPct() > 0){
               displayDiscPct = "("+billMain.getDiscPct()+"%) ";
            }


            //billMain = PstBillMain.fetchExc(oidBillMain);
            double service = 0;
            double tax = 0;
            if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
                service = 0;
                tax = 0;
            }else{
                service = billMain.getServiceValue();
                tax = billMain.getTaxValue();
            }
            double total = 0;
            total = (totalPrice)-billMain.getDiscount()+service+tax;
            drawPrint+= ""
            + "<div class='row'>"
               + "<div class='col-xs-12'>" 
                + "</div>"
               + "<div class='col-xs-4'>"
                   + "Total Qty"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": "+totalQty
               + "</div>"
               + "<div class='col-xs-4'>"
                   + ""
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Total Amount"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": "+currencyType.getCode()
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                    +" "+Formater.formatNumber(totalPrice,"#,###")
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Discount"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": <small>"+displayDiscPct+"</small>"+currencyType.getCode()
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + Formater.formatNumber(billMain.getDiscount(),"#,###")
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Total"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": "+currencyType.getCode()+""
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ""+Formater.formatNumber(totalPrice-billMain.getDiscount(),"#,###")+""
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Service"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": <small>("+billMain.getServicePct()+"%)</small> "+currencyType.getCode()
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ""+Formater.formatNumber(service, "#,###")
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Tax"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": <small>("+billMain.getTaxPercentage()+"%)</small> "+currencyType.getCode()
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ""+Formater.formatNumber(tax, "#,###")
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4' style='font-size: 13px;'>"
                   + "<b>Total</b>"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                   + ": <b>"+currencyType.getCode()+"</b>"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                   + "<b>"+Formater.formatNumber(total, "#,###")+"</b>"
               + "</div>"
               + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
            + "</div>";
            heightTotalRow = heightTotalRow + (7*heightRow);  
        } 
        if (printPaperType.equals("1")){
            if ((paperHeights-heightTotalRow)>=5){
             
                PaymentSystem paymentSystem;
                try{
                    paymentSystem = PstPaymentSystem.fetchExc(this.oidPay);
                }catch(Exception ex){
                    paymentSystem = new PaymentSystem();
                }

                 drawPrint+=drawPayment(this.paymentTypes, this.displayPaymentTypes, 
                                        paymentSystem, this.ccNames, this.ccNumbers, this.ccBanks, 
                                        this.ccValids, this.ccCharges, this.payAmounts, billMain, 
                                        location, this.printTypes);
             }
        }
         
	 return drawPrint;
     }
     
     public String drawContentCustomFOC(MatCosting matCosting, String printItemLine, Location location){
        String drawPrint = "";
        heightTotalRow =0;
        double totalQtyPerCategory=0;
        double totalPricePerCategory=0;
        
        double totalQtyPerCategoryBeverage=0;
        double totalPricePerCategoryBeverage=0;
        
        double totalQtyPerCategoryOther=0;
        double totalPricePerCategoryOther=0;
        long oidMatCosting = matCosting.getOID();
         
        Vector listCategoryFood = PstCategory.list(0, 0, 
             //""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
             " "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"='0'", "");
          
        if(listCategoryFood.size() != 0){
            int countData = 0;
            String whereCategory="";
            for(int i = 0; i< listCategoryFood.size(); i++){
                Category category = (Category) listCategoryFood.get(i);
                totalQtyPerCategory += PstMatCostingItem.getQtyByCategory(oidMatCosting, category.getOID());
                totalPricePerCategory += 0;
                
                if (whereCategory.length()>0){
                    whereCategory += ""
                    + " OR MAT."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                }else{
                    whereCategory += ""
                    + " MAT."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                }
            }
            String whereBillDetail = ""
               + " DFI."+PstMatCostingItem.fieldNames[PstMatCostingItem.FLD_PARENT_ID]+"=0";
            
            if (whereCategory.length()>0){
                whereBillDetail += " AND ( "+whereCategory+" )";
            }

            Vector listItem = PstMatCostingItem.list(0,0,oidMatCosting,whereBillDetail);

            if(listItem.size() != 0){
                String margin="";
                if(countData > 0){
                    margin = "style='margin-top:10px;'";
                }
                drawPrint += ""
                    + "<div class='row' "+margin+">"
                        + "<div class='col-xs-12'><br><b>FOOD</b></div>"
                    + "</div>";

                if(printItemLine.equals("0")){
                    drawPrint += drawItemLineDefaultFoc(listItem);
                }else{
                    drawPrint += drawItemLineCustomFoc(listItem);
                }

                drawPrint += ""
                    + "<div class='row'>"
                         + "<div class='col-xs-6'>"
                            + "<b>TOTAL FOOD</b>"
                         + "</div>"
                         + "<div class='col-xs-6' style='text-align:right;'>"
                            + ""+Formater.formatNumber(totalPricePerCategory,"#,###")
                         + "</div>"
                    + "</div>" ;                     
                countData+=1;
            }

            drawPrint += ""
            + "<div class='row'>"
                + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
            + "</div>";

        }
        
        //BEVERAGE
        Vector listCategory = PstCategory.list(0, 0, 
             //""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
              " "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"='1'", "");
	 
        if(listCategory.size() != 0){
            int countData = 0;
            String whereCategory="";
            for(int i = 0; i< listCategory.size(); i++){
                Category category = (Category) listCategory.get(i);
                totalQtyPerCategoryBeverage += PstMatCostingItem.getQtyByCategory(oidMatCosting, category.getOID());
                totalPricePerCategoryBeverage += 0;
                
                if (whereCategory.length()>0){
                    whereCategory += ""
                    + " OR MAT."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                }else{
                    whereCategory += ""
                    + " MAT."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                }
            }
            String whereBillDetail = ""
                + " DFI."+PstMatCostingItem.fieldNames[PstMatCostingItem.FLD_PARENT_ID]+"=0";
            if (whereCategory.length()>0){
                whereBillDetail += " AND ( "+whereCategory+" )";
            }
            Vector listItem = PstMatCostingItem.list(0,0,oidMatCosting,whereBillDetail);
 

            if(listItem.size() != 0){
                String margin="";
                if(countData > 0){
                    margin = "style='margin-top:10px;'";
                }
                drawPrint += ""
                    + "<div class='row' "+margin+">"
                        + "<div class='col-xs-12'><b>BEVERAGE</b></div>"
                    + "</div>";

                if(printItemLine.equals("0")){
                    drawPrint += drawItemLineDefaultFoc(listItem);
                }else{
                    drawPrint += drawItemLineCustomFoc(listItem);
                }

                drawPrint += ""
                   + "<div class='row'>"
                        + "<div class='col-xs-6'>"
                           + "<b>TOTAL BEVERAGE</b> "
                        + "</div>"
                        + "<div class='col-xs-6' style='text-align:right;'>"
                           + ""+Formater.formatNumber(totalPricePerCategoryBeverage,"#,###")
                        + "</div>"
                   + "</div>";                   
                countData+=1;
            }

            drawPrint += ""
                + "<div class='row'>"
                    + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                + "</div>";  
        }
         
        //OTHER
        
        Vector listCategoryOther = PstCategory.list(0, 0, 
            ""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
            + "AND ("+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"<>'0' AND "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"<>'1' )", "");

        if(listCategoryOther.size() != 0){
            int countData = 0;
            String whereCategory="";
            for(int i = 0; i< listCategoryOther.size(); i++){
                Category category = (Category) listCategoryOther.get(i);
                
                totalQtyPerCategoryOther += PstMatCostingItem.getQtyByCategory(oidMatCosting, category.getOID());
                totalPricePerCategoryOther += 0;
                
                if (whereCategory.length()>0){
                    whereCategory += ""
                    + " OR MAT."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                }else{
                    whereCategory += ""
                    + " MAT."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                }
            
            }
            String whereBillDetail = " "
                + " DFI."+PstMatCostingItem.fieldNames[PstMatCostingItem.FLD_PARENT_ID]+"=0";
            if (whereCategory.length()>0){
                whereBillDetail += " AND ( "+whereCategory+" )";
            }
            Vector listItem = PstMatCostingItem.list(0,0,oidMatCosting,whereBillDetail);
            
            if(listItem.size() != 0){
                String margin="";
                if(countData > 0){
                    margin = "style='margin-top:10px;'";
                }
                drawPrint += ""
                + "<div class='row' "+margin+">"
                    + "<div class='col-xs-12'><b>OTHER</b></div>"
                + "</div>";

                if(printItemLine.equals("0")){
                    drawPrint += drawItemLineDefaultFoc(listItem);
                }else{
                    drawPrint += drawItemLineDefaultFoc(listItem);
                }

                drawPrint += ""
                    + "<div class='row'>"
                         + "<div class='col-xs-6'>"
                            + "<b>TOTAL OTHER</b>"
                         + "</div>"
                         + "<div class='col-xs-6' style='text-align:right;'>"
                            + ""+Formater.formatNumber(totalPricePerCategoryOther,"#,###")
                         + "</div>"
                    + "</div>"; 
                countData+=1;
            }

            
        }
 
        double totalQty = PstMatCostingItem.getQty(oidMatCosting);
        double totalPrice = 0;
        CurrencyType currencyType;           
        String displayDiscPct = "(0%)";            
        double service = 0;
        double tax = 0;           
        double total = 0;
        
        drawPrint+= ""
            + "<div class='row'>"
               + "<div class='col-xs-12'>" 
                + "</div>"
               + "<div class='col-xs-4'>"
                   + "Total Qty"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": "+totalQty
               + "</div>"
               + "<div class='col-xs-4'>"
                   + ""
               + "</div>"
            + "</div>";
                      
                 
	 return drawPrint;
     }
     
     //PRINT ITEM REPRINT
     public String drawContentCustomReprint(BillMain billMain, String printItemLine, Location location, String full){
        String drawPrint = "";
        heightTotalRow =0;
        
        double totalQtyPerCategory=0;
        double totalPricePerCategory=0;
        
        double totalQtyPerCategoryBeverage=0;
        double totalPricePerCategoryBeverage=0;
        
        double totalQtyPerCategoryOther=0;
        double totalPricePerCategoryOther=0;
         
        String printOutType =full ;
        //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
   
        if (printOutType.equals("0")){
            //FOOD 
            Vector listCategoryFood = PstCategory.list(0, 0, 
		 //""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
		 " "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"='0'", "");
          
            if(listCategoryFood.size() != 0){
                int countData = 0;
                String whereCategory="";
                for(int i = 0; i< listCategoryFood.size(); i++){
                    Category category = (Category) listCategoryFood.get(i);
                    
                    totalQtyPerCategory += PstBillDetail.getQtyPerCategory(billMain.getOID(), category.getOID());
                    totalPricePerCategory += PstBillDetail.getTotalPricePerCategory(billMain.getOID(), category.getOID());

                    if (whereCategory.length()>0){
                        whereCategory += ""
                        + " OR pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }else{
                        whereCategory += ""
                        + " pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }
                
                }
                
                String whereBillDetail = ""
                + " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0";
                if (whereCategory.length()>0){
                    whereBillDetail += " AND ( "+whereCategory+" )";
                }
		 
                String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
                Vector listItem = new Vector();
                if(itemGroup.equals("1")){
                    listItem = PstBillDetail.listMatDistinct(0, 0,whereBillDetail, "");
                }else{
                    listItem = PstBillDetail.listMat(0, 0,whereBillDetail, "");
                }

                if(listItem.size() != 0){
                    String margin="";
                    if(countData > 0){
                        margin = "style='margin-top:10px;'";
                    }
                    drawPrint += ""
                        + "<div class='row' "+margin+">"
                            + "<div class='col-xs-12'><br><b>FOOD</b></div>"
                        + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;
                    if(printItemLine.equals("0")){
                        drawPrint += drawItemLineDefault(listItem);
                    }else{
                        drawPrint += drawItemLineCustom(listItem);
                    }
                    for (int d = 0 ; d<listItem.size();d++){
                        heightTotalRow = heightTotalRow + heightRow;
                    }

                    drawPrint += ""
                        + "<div class='row'>"
                             + "<div class='col-xs-6'>"
                                + "<b>TOTAL FOOD</b>"
                             + "</div>"
                             + "<div class='col-xs-6' style='text-align:right;'>"
                                + ""+Formater.formatNumber(totalPricePerCategory,"#,###")
                             + "</div>"
                        + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    countData+=1;
                }

                drawPrint += ""
                + "<div class='row'>"
                    + "<div class='col-xs-12'>"
                        + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "</div>"
                + "</div>";
                heightTotalRow = heightTotalRow + heightRow;  
            }
        
            //BEVERAGE
            Vector listCategory = PstCategory.list(0, 0, 
		 //""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
		 " "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"='1'", "");
	 
            if(listCategory.size() != 0){
                int countData = 0;
                String whereCategory="";
                for(int i = 0; i< listCategory.size(); i++){
                    Category category = (Category) listCategory.get(i);
                    
                    totalQtyPerCategoryBeverage += PstBillDetail.getQtyPerCategory(billMain.getOID(), category.getOID());
                    totalPricePerCategoryBeverage += PstBillDetail.getTotalPricePerCategory(billMain.getOID(), category.getOID());

                    if (whereCategory.length()>0){
                        whereCategory += ""
                        + " OR pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }else{
                        whereCategory += ""
                        + " pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }
                
                }
                
                String whereBillDetail = ""
                + " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0";
                if (whereCategory.length()>0){
                    whereBillDetail += " AND ( "+whereCategory+" )";
                }

                String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
                Vector listItem = new Vector();
                if(itemGroup.equals("1")){
                    listItem = PstBillDetail.listMatDistinct(0, 0,whereBillDetail, "");
                }else{
                    listItem = PstBillDetail.listMat(0, 0,whereBillDetail, "");
                }

                if(listItem.size() != 0){
                    String margin="";
                    if(countData > 0){
                        margin = "style='margin-top:10px;'";
                    }
                    drawPrint += ""
                        + "<div class='row' "+margin+">"
                            + "<div class='col-xs-12'><b>BEVERAGE</b></div>"
                        + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    if(printItemLine.equals("0")){
                        drawPrint += drawItemLineDefault(listItem);
                    }else{
                        drawPrint += drawItemLineCustom(listItem);
                    }
                    for (int b=0;b<listItem.size();b++){
                       heightTotalRow = heightTotalRow + heightRow;
                    }

                    drawPrint += ""
                       + "<div class='row'>"
                            + "<div class='col-xs-6'>"
                               + "<b>TOTAL BEVERAGE</b> "
                            + "</div>"
                            + "<div class='col-xs-6' style='text-align:right;'>"
                               + ""+Formater.formatNumber(totalPricePerCategoryBeverage,"#,###")
                            + "</div>"
                       + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    countData+=1;
                }
                drawPrint += ""
                    + "<div class='row'>"
                        + "<div class='col-xs-12'>"
                            + "<hr style='border:1px dashed #000;margin:0px;'>"
                        + "</div>"
                    + "</div>";
                heightTotalRow = heightTotalRow + heightRow;  
            }
         
            //OTHER
        
            Vector listCategoryOther = PstCategory.list(0, 0, 
		 ""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='0' "
		 + "AND ("+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"<>'0' AND "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"<>'1' )", "");
          
            if(listCategoryOther.size() != 0){
                int countData = 0;
                String whereCategory="";
                for(int i = 0; i< listCategoryOther.size(); i++){
                    Category category = (Category) listCategoryOther.get(i);
                    
                    totalQtyPerCategoryOther += PstBillDetail.getQtyPerCategory(billMain.getOID(), category.getOID());
                    totalPricePerCategoryOther += PstBillDetail.getTotalPricePerCategory(billMain.getOID(), category.getOID());

                    if (whereCategory.length()>0){
                        whereCategory += ""
                        + " OR pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }else{
                        whereCategory += ""
                        + " pm."+ PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+category.getOID()+"'";
                    }
                
                }
                String whereBillDetail = ""
                + " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"='"+billMain.getOID()+"'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_PARENT_ID]+"='0'"
                + " AND CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_TOTAL_PRICE]+">0";
                if (whereCategory.length()>0){
                    whereBillDetail += " AND ( "+whereCategory+" )";
                }
                String itemGroup = PstSystemProperty.getValueByName("CASHIER_PRINT_ITEM_GROUP");
                Vector listItem = new Vector();
                if(itemGroup.equals("1")){
                    listItem = PstBillDetail.listMatDistinct(0, 0,whereBillDetail, "");
                }else{
                    listItem = PstBillDetail.listMat(0, 0,whereBillDetail, "");
                }
		 
                if(listItem.size() != 0){
                    String margin="";
                    if(countData > 0){
                        margin = "style='margin-top:10px;'";
                    }
                    drawPrint += ""
                    + "<div class='row' "+margin+">"
                        + "<div class='col-xs-12'><b>OTHER</b></div>"
                    + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    if(printItemLine.equals("0")){
                        drawPrint += drawItemLineDefault(listItem);
                    }else{
                        drawPrint += drawItemLineCustom(listItem);
                    }
                    for (int j=0;j<listItem.size();j++){
                       heightTotalRow = heightTotalRow + heightRow;
                    }

                    drawPrint += ""
                        + "<div class='row'>"
                             + "<div class='col-xs-6'>"
                                + "<b>TOTAL OTHER</b>"
                             + "</div>"
                             + "<div class='col-xs-6' style='text-align:right;'>"
                                + ""+Formater.formatNumber(totalPricePerCategoryOther,"#,###")
                             + "</div>"
                        + "</div>";
                    heightTotalRow = heightTotalRow + heightRow;  
                    countData+=1;
                }
            }

          /////
         
            
            double totalQty = PstBillDetail.getQty(billMain.getOID());
            double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
            CurrencyType currencyType;

            try{
                currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
            }catch(Exception ex){
                currencyType = new CurrencyType();
            }

            String displayDiscPct = "";
            if(billMain.getDiscPct() > 0){
               displayDiscPct = "("+billMain.getDiscPct()+"%) ";
            }


            //billMain = PstBillMain.fetchExc(oidBillMain); update eyek 20180209
            double service = 0;
            double tax = 0;
            double taxView=0;
            double serviceView=0;
           if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
               service = 0;
               tax = 0;
           }else{
               service = billMain.getServiceValue();
               tax = billMain.getTaxValue();
               
                Location locationList = new Location();
                try{
                    locationList = PstLocation.fetchExc(billMain.getLocationId());
                    if(billMain.getTaxValue()==0){
                        taxView =0;
                    }else{
                        taxView = locationList.getTaxView();
                    }
                    if(billMain.getServiceValue()==0){
                        serviceView = 0;
                    }else{
                        serviceView = locationList.getServiceView();
                    }
                }catch(Exception ex){
                }
           }
           double total = 0;
           total = (totalPrice)-billMain.getDiscount()+service+tax;
            drawPrint+= ""
            + "<div class='row'>"
               + "<div class='col-xs-12'>" 
                + "</div>"
               + "<div class='col-xs-4'>"
                   + "Total Qty"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": "+totalQty
               + "</div>"
               + "<div class='col-xs-4'>"
                   + ""
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Total Amount"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": "+currencyType.getCode()
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                    +" "+Formater.formatNumber(totalPrice,"#,###")
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Discount"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": <small>"+displayDiscPct+"</small>"+currencyType.getCode()
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + Formater.formatNumber(billMain.getDiscount(),"#,###")
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Total"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ":"+currencyType.getCode()+""
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ""+Formater.formatNumber(totalPrice-billMain.getDiscount(),"#,###")+""
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Service"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": <small>("+serviceView+"%)</small> "+currencyType.getCode()
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ""+Formater.formatNumber(service, "#,###")
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4'>"
                   + "Tax"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ": <small>("+taxView+"%)</small> "+currencyType.getCode()
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;'>"
                   + ""+Formater.formatNumber(tax, "#,###")
               + "</div>"
            + "</div>"

            + "<div class='row'>"
               + "<div class='col-xs-4' style='font-size: 13px;'>"
                   + "<b>Total</b>"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                   + ": <b>"+currencyType.getCode()+"</b>"
               + "</div>"
               + "<div class='col-xs-4' style='text-align:right;font-size: 13px;'>"
                   + "<b>"+Formater.formatNumber(total, "#,###")+"</b>"
               + "</div>"
               + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
            + "</div>";
            heightTotalRow = heightTotalRow + (7*heightRow);
            this.heightTotalRowItem = heightTotalRow;
        } 
         if ((paperHeights-heightTotalRow)>=5){
             
            PaymentSystem paymentSystem;
            try{
                paymentSystem = PstPaymentSystem.fetchExc(this.oidPay);
            }catch(Exception ex){
                paymentSystem = new PaymentSystem();
            }
             
             drawPrint+=drawPayment(this.paymentTypes, this.displayPaymentTypes, 
                                    paymentSystem, this.ccNames, this.ccNumbers, this.ccBanks, 
                                    this.ccValids, this.ccCharges, this.payAmounts, billMain, 
                                    location, this.printTypes);
         }
	 return drawPrint;
     }
     
     
     //PRINT ITEM DEFAULT :: DEFAULT LINE
     public String drawItemLineDefault(Vector listItem){
	 String drawPrint = "";
	 
	 if(listItem.size() != 0){
	     for(int i = 0; i<listItem.size(); i++){
		 
		 Vector data = (Vector) listItem.get(i);
		 Billdetail billdetail = (Billdetail) data.get(0);
                 
		 drawPrint += ""
		 + "<div class='row'>"
		   + "<div class='col-xs-12'>"
		       + billdetail.getItemName()
		   + "</div>"
		 + "</div>"
		 + "<div class='row'>"
		   + "<div class='col-xs-3'>"
		       + Formater.formatNumber(billdetail.getItemPrice(),"#,###")
		   + "</div>"
		   + "<div class='col-xs-3'>"
		       + Formater.formatNumber(billdetail.getDisc(),"#,###")
		   + "</div>"
		   + "<div class='col-xs-3'>"
		       + ""+billdetail.getQty()
		   + "</div>"
		   + "<div class='col-xs-3' style='text-align:right;'>"
		       + Formater.formatNumber(((billdetail.getItemPrice())-billdetail.getDisc())*billdetail.getQty(),"#,###")
		   + "</div>"
		 + "</div>";
		 
	     }
	 }
	 
	 return drawPrint;
     }
	 
     public String drawItemLineGuidePrice(Vector listItem){
	 String drawPrint = "";
	 
	 if(listItem.size() != 0){
	     for(int i = 0; i<listItem.size(); i++){
		 
		 Vector data = (Vector) listItem.get(i);
		 Billdetail billdetail = (Billdetail) data.get(0);
                 
                 //added by dewok 20190218, for guide price (kalo pake)
                 if (TransactionCashierHandler.checkMemberGuidePrice(billdetail.getBillMainId())) {
                    billdetail.setItemPrice(billdetail.getGuidePrice());
                 }
                 
		 drawPrint += ""
		 + "<div class='row'>"
		   + "<div class='col-xs-12'>"
		       + billdetail.getItemName()
		   + "</div>"
		 + "</div>"
		 + "<div class='row'>"
		   + "<div class='col-xs-3'>"
		       + Formater.formatNumber(billdetail.getItemPrice(),"#,###")
		   + "</div>"
		   + "<div class='col-xs-3'>"
		       + Formater.formatNumber(billdetail.getDisc(),"#,###")
		   + "</div>"
		   + "<div class='col-xs-3'>"
		       + ""+billdetail.getQty()
		   + "</div>"
		   + "<div class='col-xs-3' style='text-align:right;'>"
		       + Formater.formatNumber(((billdetail.getItemPrice())-billdetail.getDisc())*billdetail.getQty(),"#,###")
		   + "</div>"
		 + "</div>";
		 
	     }
	 }
	 
	 return drawPrint;
     }
	 
	 //PRINT ITEM DEFAULT :: WITH SKU
     public String drawItemLineWithSKU(Vector listItem){
	 String drawPrint = "";
	 
	 if(listItem.size() != 0){
	     for(int i = 0; i<listItem.size(); i++){
		 
		 Vector data = (Vector) listItem.get(i);
		 Billdetail billdetail = (Billdetail) data.get(0);
		 
		 drawPrint += ""
		 + "<div class='row'>"
		   + "<div class='col-xs-12'>"
		       + billdetail.getSku()
		   + "</div>"
		   + "<div class='col-xs-12'>"
		       + billdetail.getItemName()
		   + "</div>"
		 + "</div>"
		 + "<div class='row'>"
		   + "<div class='col-xs-3'>"
		       + Formater.formatNumber(billdetail.getItemPrice(),"#,###")
		   + "</div>"
		   + "<div class='col-xs-3'>"
		       + Formater.formatNumber(billdetail.getDisc(),"#,###")
		   + "</div>"
		   + "<div class='col-xs-3'>"
		       + ""+billdetail.getQty()
		   + "</div>"
		   + "<div class='col-xs-3' style='text-align:right;'>"
		       + Formater.formatNumber(((billdetail.getItemPrice())-billdetail.getDisc())*billdetail.getQty(),"#,###")
		   + "</div>"
		 + "</div>";
		 
	     }
	 }
	 
	 return drawPrint;
     }
     
     public String drawItemLineDefaultContinuesPayment(Vector listItem){
	 String drawPrint = "";
	 
	 if(listItem.size() != 0){
	     for(int i = 0; i<listItem.size(); i++){
		 
		 Vector data = (Vector) listItem.get(i);
		 Billdetail billdetail = (Billdetail) data.get(0);
		 
		 drawPrint += ""
		 + "<div class='row'>"
		   + "<div class='col-xs-12'>"
		       + "&nbsp;"
		   + "</div>"
		 + "</div>"
		 + "<div class='row'>"
		   + "<div class='col-xs-3'>"
		       + "&nbsp;"
		   + "</div>"
		   + "<div class='col-xs-3'>"
		       + "&nbsp;"
		   + "</div>"
		   + "<div class='col-xs-3'>"
		       + ""+"&nbsp;"
		   + "</div>"
		   + "<div class='col-xs-3' style='text-align:right;'>"
		       + "&nbsp;"
		   + "</div>"
		 + "</div>";
		 
	     }
	 }
	 
	 return drawPrint;
     }
     
     public String drawItemLineDefaultCompliment(Vector listItem){
	 String drawPrint = "";
	 
	 if(listItem.size() != 0){
	     for(int i = 0; i<listItem.size(); i++){
		 
		 Vector data = (Vector) listItem.get(i);
		 Billdetail billdetail = (Billdetail) data.get(0);
		 
		 drawPrint += ""
		 + "<div class='row'>"
		   + "<div class='col-xs-12'>"
		       + billdetail.getItemName()
		   + "</div>"
		 + "</div>"
		 + "<div class='row'>"
		   + "<div class='col-xs-3'>"
		       + Formater.formatNumber(0,"#,###")
		   + "</div>"
		   + "<div class='col-xs-3'>"
		       + Formater.formatNumber(0,"#,###")
		   + "</div>"
		   + "<div class='col-xs-3'>"
		       + ""+billdetail.getQty()
		   + "</div>"
		   + "<div class='col-xs-3' style='text-align:right;'>"
		       + Formater.formatNumber(((0)-0)*billdetail.getQty(),"#,###")
		   + "</div>"
		 + "</div>";
		 
	     }
	 }
	 
	 return drawPrint;
     }
     
     public String drawItemLineDefaultFoc(Vector listItem){
	 String drawPrint = "";
	 
	 if(listItem.size() != 0){
	     for(int i = 0; i<listItem.size(); i++){
		 double qty = 0;
		 Vector tempData = (Vector) listItem.get(i);
                 MatCostingItem matCostingItem = (MatCostingItem)tempData.get(0);
		      Material material = (Material) tempData.get(1);
                 if (material.getMaterialType()==1){
                     qty = matCostingItem.getQtyComposite();
                 }else{
                     qty = matCostingItem.getQty();
                 }
                 
                String character="";
                character = material.getName();
                 
                long oidSpesialRequestFood=Long.parseLong(PstSystemProperty.getValueByName("SPESIAL_REQUEST_FOOD"));
                long oidSpesialRequestBeverage=Long.parseLong(PstSystemProperty.getValueByName("SPESIAL_REQUEST_BEVERAGE"));

                if (matCostingItem.getMaterialId()==oidSpesialRequestFood || matCostingItem.getMaterialId()==oidSpesialRequestBeverage){
                    character = matCostingItem.getSpesialNote();
                    
                }
                 
		 drawPrint += ""
		 + "<div class='row'>"
		   + "<div class='col-xs-12'>"
		       + character
		   + "</div>"
		 + "</div>"
		 + "<div class='row'>"
		   + "<div class='col-xs-3'>"
		       + "0"
		   + "</div>"
		   + "<div class='col-xs-3'>"
		       + "0"
		   + "</div>"
		   + "<div class='col-xs-3'>"
		       + ""+qty
		   + "</div>"
		   + "<div class='col-xs-3' style='text-align:right;'>"
		       + "0"
		   + "</div>"
		 + "</div>";
		 
	     }
	 }
	 
	 return drawPrint;
     }
     
     
     //PRINT ITEM CUSTOM :: CUSTOM LINE
     public String drawItemLineCustom(Vector listItem){
	 String drawPrint = "";
	 if(listItem.size() != 0){
	     
	     drawPrint += ""
		+ "<div class='row'>"
		     + "<div class='col-xs-12'>"
			+ "<table border='0' cellpadding='0' cellspacing='0' width='100%' style='font-size:11px;'>"
			   + "<tbody>";
				    
				   for(int i = 0; i < listItem.size(); i++){
				       Vector data = (Vector) listItem.get(i);
				       Billdetail billdetail = (Billdetail) data.get(0);
				       //split karakter
                                       String character="";
                                       String chr ="";
                                       if (billdetail.getItemName().length()>15){
                                           character=billdetail.getItemName();
                                           chr = character.substring(0,14);
                                           character = chr+"..";
                                       }else{
                                           character = billdetail.getItemName();
                                       }
                                       
				       drawPrint += ""
				       + "<tr>"
					   + "<td width='30%' ><span class='turn' style='margin-left: 3px;'>"+character+"</span></td>"
					   + "<td width='20%'>"+Formater.formatNumber(billdetail.getItemPrice(),"#,###")+"</td>"
					   + "<td width='20%'>"+Formater.formatNumber(billdetail.getDisc(),"#,###")+"</td>"
					   + "<td width='10%'>"+billdetail.getQty()+"</td>"
					   + "<td width='20%' style='text-align:right;'>"+Formater.formatNumber(((billdetail.getItemPrice())-billdetail.getDisc())*billdetail.getQty(),"#,###")+"</td>"
				       + "</tr>";
				   }
			       drawPrint += ""
			       + "</tbody>"
			  + "</table>"
		    + "</div>"
		+ "</div>";
	 }
	
	 return drawPrint;
     }
     
     public String drawItemLineCustomContinuesPayment(Vector listItem){
	 String drawPrint = "";
	 if(listItem.size() != 0){
	     
	     drawPrint += ""
		+ "<div class='row'>"
		     + "<div class='col-xs-12'>"
			+ "<table border='0' cellpadding='0' cellspacing='0' width='100%' style='font-size:11px;'>"
			   + "<tbody>";
				    
				   for(int i = 0; i < listItem.size(); i++){
				       Vector data = (Vector) listItem.get(i);
				       Billdetail billdetail = (Billdetail) data.get(0);
				       //split karakter
                                       String character="";
                                       String chr ="";
                                       if (billdetail.getItemName().length()>15){
                                           character=billdetail.getItemName();
                                           chr = character.substring(0,14);
                                           character = chr+"..";
                                       }else{
                                           character = billdetail.getItemName();
                                       }
                                       
				       drawPrint += ""
				       + "<tr>"
					   + "<td width='30%' >\"&nbsp;\"</td>"
					   + "<td width='20%'>"+"&nbsp;"+"</td>"
					   + "<td width='20%'>"+"&nbsp;"+"</td>"
					   + "<td width='10%'>"+"&nbsp;"+"</td>"
					   + "<td width='20%' style='text-align:right;'>"+"&nbsp;"+"</td>"
				       + "</tr>";
				   }
			       drawPrint += ""
			       + "</tbody>"
			  + "</table>"
		    + "</div>"
		+ "</div>";
	 }
	
	 return drawPrint;
     }
     
     public String drawItemLineCustomCompliment(Vector listItem){
	 String drawPrint = "";
	 if(listItem.size() != 0){
	     
	     drawPrint += ""
		+ "<div class='row'>"
		     + "<div class='col-xs-12'>"
			+ "<table border='0' cellpadding='0' cellspacing='0' width='100%' style='font-size:11px;'>"
			   + "<tbody>";
				    
				   for(int i = 0; i < listItem.size(); i++){
				       Vector data = (Vector) listItem.get(i);
				       Billdetail billdetail = (Billdetail) data.get(0);
				       //split karakter
                                       String character="";
                                       String chr ="";
                                       if (billdetail.getItemName().length()>15){
                                           character=billdetail.getItemName();
                                           chr = character.substring(0,14);
                                           character = chr+"..";
                                       }else{
                                           character = billdetail.getItemName();
                                       }
                                       
				       drawPrint += ""
				       + "<tr>"
					   + "<td width='30%' ><span class='turn' style='margin-left: 3px;'>"+character+"</span></td>"
					   + "<td width='20%'>"+Formater.formatNumber(0,"#,###")+"</td>"
					   + "<td width='20%'>"+Formater.formatNumber(0,"#,###")+"</td>"
					   + "<td width='10%'>"+billdetail.getQty()+"</td>"
					   + "<td width='20%' style='text-align:right;'>"+Formater.formatNumber(((0)-0)*billdetail.getQty(),"#,###")+"</td>"
				       + "</tr>";
				   }
			       drawPrint += ""
			       + "</tbody>"
			  + "</table>"
		    + "</div>"
		+ "</div>";
	 }
	
	 return drawPrint;
     }
     
     //PRINT ITEM CUSTOM :: CUSTOM LINE
     public String drawItemLineCustomFoc(Vector listItem){
	 String drawPrint = "";
	 if(listItem.size() != 0){	     
            drawPrint += ""
                + "<div class='row'>"
                    + "<div class='col-xs-12'>"
			+ "<table border='0' cellpadding='0' cellspacing='0' width='100%' style='font-size:11px;'>"
			   + "<tbody>";				    
                                    for(int i = 0; i < listItem.size(); i++){
                                        Vector data = (Vector) listItem.get(i);
                                        MatCostingItem matCostingItem = (MatCostingItem)data.get(0);
                                        Material material = (Material) data.get(1);
                                        //split karakter
                                        double qty=0;
                                        String character="";
                                        String chr ="";
                                        if (material.getName().length()>15){
                                            character=material.getName();
                                            chr = character.substring(0,14);
                                            character = chr+"..";
                                        }else{
                                            character = material.getName();
                                        }
                                        
                                        long oidSpesialRequestFood=Long.parseLong(PstSystemProperty.getValueByName("SPESIAL_REQUEST_FOOD"));
                                        long oidSpesialRequestBeverage=Long.parseLong(PstSystemProperty.getValueByName("SPESIAL_REQUEST_BEVERAGE"));
                                        
                                        if (matCostingItem.getMaterialId()==oidSpesialRequestFood || matCostingItem.getMaterialId()==oidSpesialRequestBeverage){
                                            
                                            if (matCostingItem.getSpesialNote().length()>15){
                                                character=matCostingItem.getSpesialNote();
                                                chr = character.substring(0,14);
                                                character = chr+"..";
                                            }else{
                                                character = matCostingItem.getSpesialNote();
                                            }
                                        }
                                        
                                        if (material.getMaterialType()==1){
                                            qty = matCostingItem.getQtyComposite();
                                        }else{
                                            qty = matCostingItem.getQty();
                                        }
                                        drawPrint += ""
                                        + "<tr>"
                                            + "<td width='30%' ><span class='turn' style='margin-left: 3px;'>"+character+"</span></td>"
                                            + "<td width='20%'>0</td>"
                                            + "<td width='20%'></td>"
                                            + "<td width='10%'>"+qty+"</td>"
                                            + "<td width='20%' style='text-align:right;'>0</td>"
                                        + "</tr>";
                                    }
                                drawPrint += ""
                                + "</tbody>"
                            + "</table>"
		    + "</div>"
		+ "</div>";
	}	
	return drawPrint;
     }
     
    public String drawPayment(String paymentType, String displayPaymentSystem, PaymentSystem paymentSystem, String ccName, String ccNumber, String ccBank, String ccValid, double ccCharge, double payAmount, BillMain billMain, Location location, String printType) {

        CurrencyType currencyType;
        try {
            currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
        } catch (Exception ex) {
            currencyType = new CurrencyType();
        }

        double service = 0;
        double tax = 0;
        if (location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE) {
            service = 0;
            tax = 0;
        } else {
            service = billMain.getServiceValue();
            tax = billMain.getTaxValue();
        }

        double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
        double totalExpanse = PstBillDetail.getTotalExpense(billMain.getOID());
        double total = 0;
        total = (totalPrice) - billMain.getDiscount() + service + tax + totalExpanse;

        //CEK APAKAH MULTI PAYMENT ATAU TIDAK
        String whereCashPayment = "";
        String drawPrint = "";
        whereCashPayment = " " + PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID] + "=" + billMain.getOID() + "";

        Vector listCashPayment = PstCashPayment.list(0, 0, whereCashPayment, "");
        if (listCashPayment.size() > 1) {
            //MULTI PAYMENT
            double totals = 0;
            for (int j = 0; j < listCashPayment.size(); j++) {
                CashPayments cashPayments = (CashPayments) listCashPayment.get(j);
                PaymentSystem paymentSystem1 = new PaymentSystem();
                try {
                    paymentSystem1 = PstPaymentSystem.fetchExc(cashPayments.getPaymentTypeLong());
                } catch (Exception e) {
                }
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'><b>Payment Type</b></div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'><b> " + paymentSystem1.getPaymentSystem() + "</b></div>"
                        + "</div>";
                //PAYMENT BY
                String ccName2 = "";
                String ccNumber2 = "";
                String ccBank2 = "";
                Date ccValid2 = null;
                double ccCharge2 = 0;

                String wherePaymentBy = " " + PstCashCreditCard.fieldNames[PstCashCreditCard.FLD_PAYMENT_ID] + "=" + cashPayments.getOID() + "";
                Vector listPaymentCreditCard = PstCashCreditCard.list(0, 0, wherePaymentBy, "");
                CashCreditCard cashCreditCard = new CashCreditCard();
                if (listPaymentCreditCard.size() > 0) {
                    cashCreditCard = (CashCreditCard) listPaymentCreditCard.get(0);
                    ccNumber2 = cashCreditCard.getCcNumber();
                    ccBank2 = cashCreditCard.getDebitBankName();
                    ccValid2 = cashCreditCard.getExpiredDate();
                    ccCharge2 = cashCreditCard.getBankCost();
                }
                
                if (paymentSystem1.isCardInfo() == true
                        && paymentSystem1.getPaymentType() == 0
                        && paymentSystem1.isBankInfoOut() == false
                        && paymentSystem1.isCheckBGInfo() == false) {

                    if (listPaymentCreditCard.size() > 0) {
                        ccName2 = cashCreditCard.getCcName();
                    }
                    drawPrint += ""
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Credit Card Name </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccName2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Credit Card No </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccNumber2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccBank2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Validity </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccValid2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Cost </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: <small>" + paymentSystem1.getBankCostPercent() + "%</small>" + "</div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + Formater.formatNumber(ccCharge2, "#,###") + "</div>"
                            + "</div>";
                    total += ccCharge;
                    /////BG PAYMENT
                } else if (paymentSystem1.isCardInfo() == false
                        && paymentSystem1.getPaymentType() == 2
                        && paymentSystem1.isBankInfoOut() == true
                        && paymentSystem1.isCheckBGInfo() == false) {
                    if (listPaymentCreditCard.size() > 0) {
                        ccName2 = cashCreditCard.getChequeAccountName();
                    }
                    drawPrint += ""
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Name </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccBank2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Account No </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccName2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>BG No </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccNumber2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Expired Date </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccValid2 + "</div>"
                            + "</div>";
                    /////CHECK PAYMENT
                } else if (paymentSystem1.isCardInfo() == true
                        && paymentSystem1.getPaymentType() == 0
                        && paymentSystem1.isBankInfoOut() == false
                        && paymentSystem1.isCheckBGInfo() == false) {
                    if (listPaymentCreditCard.size() > 0) {
                        ccName2 = cashCreditCard.getChequeAccountName();
                    }
                    drawPrint += ""
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Name </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccBank2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>" + "Bank Account No </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccName2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Cheque No </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccNumber2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Expired Date </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccValid2 + "</div>"
                            + "</div>";
                    /////DEBIT PAYMENT
                } else if (paymentSystem1.isCardInfo() == false
                        && paymentSystem1.getPaymentType() == 2
                        && paymentSystem1.isBankInfoOut() == false
                        && paymentSystem1.isCheckBGInfo() == false) {
                    if (listPaymentCreditCard.size() > 0) {
                        ccName2 = cashCreditCard.getDebitCardName();
                    }
                    drawPrint += ""
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Name </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccBank2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Card No </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccNumber2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Expired Date </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + ccValid2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Cost </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'> " + Formater.formatNumber(ccCharge2, "#,###") + "</div>"
                            + "</div>";
                    total += ccCharge2;

                }
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Amount</div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: " + currencyType.getCode() + "</div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + Formater.formatNumber(cashPayments.getAmount(), "#,###") + "</div>"
                        + "</div>";
                totals += cashPayments.getAmount();
            }
            drawPrint += ""
                    + "<div class='row'>"
                    + "     <div class='col-xs-5'>Total Payment</div>"
                    + "     <div class='col-xs-2' style='text-align:right;'>: " + currencyType.getCode() + "</div>"
                    + "     <div class='col-xs-5' style='text-align:right;'> " + Formater.formatNumber(totals, "#,###") + "</div>"
                    + "</div>";
            double changess = total - totals;
            drawPrint += ""
                    + "<div class='row'>"
                    + "     <div class='col-xs-5'>Change </div>"
                    + "     <div class='col-xs-2' style='text-align:right;'>: " + currencyType.getCode() + "</div>"
                    + "     <div class='col-xs-5' style='text-align:right;'> " + Formater.formatNumber(changess, "#,###") + "</div>"
                    + "</div>";
        } else if (listCashPayment.size() != 0) {
            //SINGLE PAYMENT

            drawPrint += ""
                    + "<div class='row'>"
                    + "     <div class='col-xs-5'>Payment Type</div>"
                    + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                    + "     <div class='col-xs-5' style='text-align:right;'> " + displayPaymentSystem + "</div>"
                    + "</div>";
            CashPayments cashPayments = (CashPayments) listCashPayment.get(0);
            PaymentSystem paymentSystem1 = new PaymentSystem();
            try {
                paymentSystem1 = PstPaymentSystem.fetchExc(cashPayments.getPaymentTypeLong());
            } catch (Exception e) {
            }

            if (paymentSystem.isCardInfo() == true
                    && paymentSystem.getPaymentType() == 0
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false) {
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Credit Card Name </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccName + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Credit Card No </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccNumber + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccBank + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Validity </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccValid + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Cost </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: <small>" + paymentSystem.getBankCostPercent() + "%</small></div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + Formater.formatNumber(ccCharge, "#,###") + "</div>"
                        + "</div>";
                total += ccCharge;
                /////BG PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.getPaymentType() == 2
                    && paymentSystem.isBankInfoOut() == true
                    && paymentSystem.isCheckBGInfo() == false) {
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Name </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccBank + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Account No </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccName + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>BG No </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccNumber + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Expired Date </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccValid + "</div>"
                        + "</div>";
                /////CHECK PAYMENT
            } else if (paymentSystem.isCardInfo() == true
                    && paymentSystem.getPaymentType() == 0
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false) {
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Name </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccBank + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Account No </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccName + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Cheque No </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccNumber + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Expired Date </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccValid + "</div>"
                        + "</div>";
                /////DEBIT PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.getPaymentType() == 2
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false) {
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Name </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccBank + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Card No </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccNumber + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Expired Date </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + ccValid + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Cost </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'> " + Formater.formatNumber(ccCharge, "#,###") + "</div>"
                        + "</div>";
                total += ccCharge;
            }
            if (paymentSystem.getCostingTo() != 0) {
                total = 0;
            }
            double change = 0;
            String paidDisplay = "";
            double paid = 0;

            if (printType.equals("printpay")) {
                paidDisplay = "Paid";

                paid = cashPayments.getAmount() * cashPayments.getRate();
                change = (cashPayments.getAmount() * cashPayments.getRate()) - total;
            } else {
                paidDisplay = "Return";
                paid = total;
                change = total - total;
            }

            if (billMain.getDocType() == 0 && billMain.getTransactionStatus() == 1 && billMain.getTransctionType() == 1) {
                
            } else {
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>" + paidDisplay + "</div>"
                        + "     <div class='col-xs-3' style='text-align:right; font-size:10px'>: " + currencyType.getCode() + "</div>"
                        + "     <div class='col-xs-4' style='text-align:right;'> " + Formater.formatNumber(paid, "#,###") + "</div>"
                        + "</div>";
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Amount </div>"
                        + "     <div class='col-xs-3' style='text-align:right; font-size:10px'>: " + currencyType.getCode() + "</div>"
                        + "     <div class='col-xs-4' style='text-align:right;'> " + Formater.formatNumber(total, "#,###") + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Change </div>"
                        + "     <div class='col-xs-3' style='text-align:right; font-size:10px;'>: " + currencyType.getCode() + "</div>"
                        + "     <div class='col-xs-4' style='text-align:right;'> " + Formater.formatNumber(change, "#,###") + "</div>"
                        + "</div>";
            }
        } else {
            drawPrint += ""
                    + "<div class='row'>"
                    + "     <div class='col-xs-5'><b>Transaction Type</b></div>"
                    + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                    + "     <div class='col-xs-5' style='text-align:right;'><b> Credit</b></div>"
                    + "</div>";
        }
        drawPrint += ""
                + "<div class='row'>"
                + "     <div class='col-xs-12'>"
                + "         <hr style='border:1px dashed #000;margin:0px;'>"
                + "     </div>"
                + "</div>";
        return drawPrint;
    }

    public String drawPaymentGuidePrice(String paymentType, String displayPaymentSystem,
            PaymentSystem paymentSystem, String ccName, String ccNumber,
            String ccBank, String ccValid, double ccCharge, double payAmount,
            BillMain billMain, Location location, String printType) {

        CurrencyType currencyType;
        try {
            currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
        } catch (Exception ex) {
            currencyType = new CurrencyType();
        }

        double service = 0;
        double tax = 0;
        if (location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE) {
            service = 0;
            tax = 0;
        } else {
            service = billMain.getServiceValue();
            tax = billMain.getTaxValue();
        }

        double totalPrice = PstBillDetail.getTotalGuidePrice(billMain.getOID());
        double total = 0;
        total = (totalPrice) - billMain.getDiscount() + service + tax;

        //CEK APAKAH MULTI PAYMENT ATAU TIDAK
        String whereCashPayment = "";
        String drawPrint = "";
        whereCashPayment = ""
                + " " + PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID] + "=" + billMain.getOID() + "";

        Vector listCashPayment = PstCashPayment1.list(0, 0, whereCashPayment, "");
        if (listCashPayment.size() > 1) {
            //MULTI PAYMENT
            double totals = 0;
            for (int j = 0; j < listCashPayment.size(); j++) {
                CashPayments1 cashPayments = (CashPayments1) listCashPayment.get(j);
                PaymentSystem paymentSystem1 = new PaymentSystem();
                try {
                    paymentSystem1 = PstPaymentSystem.fetchExc(cashPayments.getPaymentType());
                } catch (Exception e) {
                }
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'><b>Payment Type</b></div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'><b> " + paymentSystem1.getPaymentSystem() + "</b></div>"
                        + "</div>";
                //PAYMENT BY
                String ccName2 = "";
                String ccNumber2 = "";
                String ccBank2 = "";
                Date ccValid2 = null;
                double ccCharge2 = 0;

                String wherePaymentBy = " " + PstCashCreditCard.fieldNames[PstCashCreditCard.FLD_PAYMENT_ID] + "=" + cashPayments.getOID() + "";
                Vector listPaymentCreditCard = PstCashCreditCard.list(0, 0, wherePaymentBy, "");
                CashCreditCard cashCreditCard = new CashCreditCard();
                if (listPaymentCreditCard.size() > 0) {
                    cashCreditCard = (CashCreditCard) listPaymentCreditCard.get(0);
                    ccNumber2 = cashCreditCard.getCcNumber();
                    ccBank2 = cashCreditCard.getDebitBankName();
                    ccValid2 = cashCreditCard.getExpiredDate();
                    ccCharge2 = cashCreditCard.getBankCost();
                }
                if (paymentSystem1.isCardInfo() == true
                        && paymentSystem1.getPaymentType() == 0
                        && paymentSystem1.isBankInfoOut() == false
                        && paymentSystem1.isCheckBGInfo() == false) {

                    if (listPaymentCreditCard.size() > 0) {
                        ccName2 = cashCreditCard.getCcName();
                    }
                    drawPrint += ""
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Credit Card Name </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccName2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Credit Card No </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccNumber2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccBank2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Validity </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccValid2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Cost </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>"
                            + "         : <small>" + paymentSystem1.getBankCostPercent() + "%</small>"
                            + "     </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + Formater.formatNumber(ccCharge2, "#,###") + "</div>"
                            + "</div>";
                    total += ccCharge;
                    /////BG PAYMENT
                } else if (paymentSystem1.isCardInfo() == false
                        && paymentSystem1.getPaymentType() == 2
                        && paymentSystem1.isBankInfoOut() == true
                        && paymentSystem1.isCheckBGInfo() == false) {
                    if (listPaymentCreditCard.size() > 0) {
                        ccName2 = cashCreditCard.getChequeAccountName();
                    }
                    drawPrint += ""
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Name </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccBank2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Account No </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccName2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>BG No </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccNumber2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Expired Date </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccValid2 + "</div>"
                            + "</div>";
                    /////CHECK PAYMENT
                } else if (paymentSystem1.isCardInfo() == true
                        && paymentSystem1.getPaymentType() == 0
                        && paymentSystem1.isBankInfoOut() == false
                        && paymentSystem1.isCheckBGInfo() == false) {
                    if (listPaymentCreditCard.size() > 0) {
                        ccName2 = cashCreditCard.getChequeAccountName();
                    }
                    drawPrint += ""
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Name </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccBank2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Account No </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccName2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Cheque No </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccNumber2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Expired Date </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccValid2 + "</div>"
                            + "</div>";
                    /////DEBIT PAYMENT
                } else if (paymentSystem1.isCardInfo() == false
                        && paymentSystem1.getPaymentType() == 2
                        && paymentSystem1.isBankInfoOut() == false
                        && paymentSystem1.isCheckBGInfo() == false) {
                    if (listPaymentCreditCard.size() > 0) {
                        ccName2 = cashCreditCard.getDebitCardName();
                    }
                    drawPrint += ""
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Name </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccBank2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Card No </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccNumber2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Expired Date </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + ccValid2 + "</div>"
                            + "</div>"
                            
                            + "<div class='row'>"
                            + "     <div class='col-xs-5'>Bank Cost </div>"
                            + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                            + "     <div class='col-xs-5' style='text-align:right;'>" + Formater.formatNumber(ccCharge2, "#,###") + "</div>"
                            + "</div>";
                    total += ccCharge2;

                }
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Amount </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: " + currencyType.getCode() + "</div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + Formater.formatNumber(cashPayments.getAmount(), "#,###") + "</div>"
                        + "</div>";
                totals += cashPayments.getAmount();
            }
            drawPrint += ""
                    + "<div class='row'>"
                    + "     <div class='col-xs-5'>Total Payment </div>"
                    + "     <div class='col-xs-2' style='text-align:right;'>: " + currencyType.getCode() + "</div>"
                    + "     <div class='col-xs-5' style='text-align:right;'>" + Formater.formatNumber(totals, "#,###") + "</div>"
                    + "</div>";
            
            double changess = total - totals;
            drawPrint += ""
                    + "<div class='row'>"
                    + "     <div class='col-xs-5'>Change </div>"
                    + "     <div class='col-xs-2' style='text-align:right;'>: " + currencyType.getCode() + "</div>"
                    + "     <div class='col-xs-5' style='text-align:right;'>" + Formater.formatNumber(changess, "#,###") + "</div>"
                    + "</div>";
        } else if (listCashPayment.size() != 0) {
            //SINGLE PAYMENT

            drawPrint += ""
                    + "<div class='row'>"
                    + "     <div class='col-xs-5'>Payment Type </div>"
                    + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                    + "     <div class='col-xs-5' style='text-align:right;'>" + displayPaymentSystem + "</div>"
                    + "</div>";
            CashPayments1 cashPayments = (CashPayments1) listCashPayment.get(0);
            PaymentSystem paymentSystem1 = new PaymentSystem();
            try {
                paymentSystem1 = PstPaymentSystem.fetchExc(cashPayments.getPaymentType());
            } catch (Exception e) {
            }

            if (paymentSystem.isCardInfo() == true
                    && paymentSystem.getPaymentType() == 0
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false) {
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Credit Card Name </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccName + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Credit Card No </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccNumber + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccBank + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Validity </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccValid + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Cost </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>"
                        + "         : <small>" + paymentSystem.getBankCostPercent() + "%</small>"
                        + "     </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + Formater.formatNumber(ccCharge, "#,###") + "</div>"
                        + "</div>";
                total += ccCharge;
                /////BG PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.getPaymentType() == 2
                    && paymentSystem.isBankInfoOut() == true
                    && paymentSystem.isCheckBGInfo() == false) {
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Name </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccBank + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Account No </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccName + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>BG No </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccNumber + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Expired Date </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccValid + "</div>"
                        + "</div>";
                /////CHECK PAYMENT
            } else if (paymentSystem.isCardInfo() == true
                    && paymentSystem.getPaymentType() == 0
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false) {
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Name </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccBank + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Account No </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccName + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Cheque No </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccNumber + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Expired Date </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccValid + "</div>"
                        + "</div>";
                /////DEBIT PAYMENT
            } else if (paymentSystem.isCardInfo() == false
                    && paymentSystem.getPaymentType() == 2
                    && paymentSystem.isBankInfoOut() == false
                    && paymentSystem.isCheckBGInfo() == false) {
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Name </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccBank + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Card No </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccNumber + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Expired Date </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + ccValid + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Bank Cost </div>"
                        + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                        + "     <div class='col-xs-5' style='text-align:right;'>" + Formater.formatNumber(ccCharge, "#,###") + "</div>"
                        + "</div>";
                total += ccCharge;
            }
            if (paymentSystem.getCostingTo() != 0) {
                total = 0;
            }
            double change = 0;
            String paidDisplay = "";
            double paid = 0;

            if (printType.equals("printpay")) {
                paidDisplay = "Paid";

                paid = cashPayments.getAmountGuidePrice()* cashPayments.getRate();
                change = (cashPayments.getAmountGuidePrice() * cashPayments.getRate()) - total;
            } else {
                paidDisplay = "Return";
                paid = total;
                change = total - total;
            }

            if (billMain.getDocType() == 0 && billMain.getTransactionStatus() == 1 && billMain.getTransctionType() == 1) {
            } else {
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>" + paidDisplay + "</div>"
                        + "     <div class='col-xs-3' style='text-align:right; font-size:10px'>: " + currencyType.getCode() + "</div>"
                        + "     <div class='col-xs-4' style='text-align:right;'>" + Formater.formatNumber(paid, "#,###") + "</div>"
                        + "</div>";
                
                drawPrint += ""
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Amount </div>"
                        + "     <div class='col-xs-3' style='text-align:right; font-size:10px'>: " + currencyType.getCode() + "</div>"
                        + "     <div class='col-xs-4' style='text-align:right;'>" + Formater.formatNumber(total, "#,###") + "</div>"
                        + "</div>"
                        
                        + "<div class='row'>"
                        + "     <div class='col-xs-5'>Change </div>"
                        + "     <div class='col-xs-3' style='text-align:right; font-size:10px;'>: " + currencyType.getCode() + "</div>"
                        + "     <div class='col-xs-4' style='text-align:right;'>" + Formater.formatNumber(change, "#,###") + "</div>"
                        + "</div>";
            }
        } else {
            drawPrint += ""
                    + "<div class='row'>"
                    + "     <div class='col-xs-5'><b>Transaction Type</b></div>"
                    + "     <div class='col-xs-2' style='text-align:right;'>: </div>"
                    + "     <div class='col-xs-5' style='text-align:right;'><b> Credit</b></div>"
                    + "</div>";
        }
        drawPrint += ""
                + "<div class='row'>"
                + "     <div class='col-xs-12'>"
                + "         <hr style='border:1px dashed #000;margin:0px;'>"
                + "     </div>"
                + "</div>";
        return drawPrint;
    }


     public String drawPaymentContinues(String paymentType, String displayPaymentSystem, 
	     PaymentSystem paymentSystem, String ccName, String ccNumber,
	     String ccBank, String ccValid, double ccCharge, double payAmount, 
	     BillMain billMain, Location location, String printType){
	 
     CurrencyType currencyType;
	 try{
	     currencyType = PstCurrencyType.fetchExc(billMain.getCurrencyId());
	 }catch(Exception ex){
	     currencyType = new CurrencyType();
	 }
	 
	 double service = 0;
	 double tax = 0;
	if(location.getTaxSvcDefault() == PstBillMain.INC_CHANGEABLE || location.getTaxSvcDefault() == PstBillMain.INC_NOT_CHANGEABLE){
	    service = 0;
	    tax = 0;
	}else{
	    service = billMain.getServiceValue();
	    tax = billMain.getTaxValue();
	}
	
	double totalPrice = PstBillDetail.getTotalPrice(billMain.getOID());
	double total = 0;
	total = (totalPrice)-billMain.getDiscount()+service+tax;
        
        //CEK APAKAH MULTI PAYMENT ATAU TIDAK
        String whereCashPayment ="";
        String drawPrint = "";
        whereCashPayment =""
            + " "+PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID]+"="+billMain.getOID()+"";
        
        Vector listCashPayment = PstCashPayment.list(0, 0, whereCashPayment, "");
        if (listCashPayment.size()>1){
            //MULTI PAYMENT
            double totals=0;
            for (int j = 0; j<listCashPayment.size();j++){
                CashPayments cashPayments = (CashPayments)listCashPayment.get(j);
                PaymentSystem paymentSystem1 = new PaymentSystem();
                try {
                    paymentSystem1 = PstPaymentSystem.fetchExc(cashPayments.getPaymentTypeLong());
                } catch (Exception e) {
                }
                drawPrint+=""
                + "<div class='row'>"
                    + "<div class='col-xs-5'>"
                        + "<b>Invoice Number</b>"
                    + "</div>"
                    + "<div class='col-xs-2' style='text-align:right;'>"
                        + ": "
                    + "</div>"
                    + "<div class='col-xs-5' style='text-align:right;'>"
                        + "<b> "+billMain.getInvoiceNumber()+"</b>"
                     + "</div>"
                + "</div>"        
                + "<div class='row'>"
                    + "<div class='col-xs-5'>"
                        + "<b>Payment Type</b>"
                    + "</div>"
                    + "<div class='col-xs-2' style='text-align:right;'>"
                        + ": "
                    + "</div>"
                    + "<div class='col-xs-5' style='text-align:right;'>"
                        + "<b> "+paymentSystem1.getPaymentSystem()+"</b>"
                     + "</div>"
                + "</div>";
                //PAYMENT BY
                String ccName2 ="";
                String ccNumber2="";
                String ccBank2="";
                Date ccValid2=null;
                double ccCharge2=0;
                
                String wherePaymentBy = " "+PstCashCreditCard.fieldNames[PstCashCreditCard.FLD_PAYMENT_ID]+"="+cashPayments.getOID()+"";
                Vector listPaymentCreditCard = PstCashCreditCard.list(0, 0, wherePaymentBy, "");
                CashCreditCard cashCreditCard = new CashCreditCard();
                if (listPaymentCreditCard.size()>0){
                    cashCreditCard = (CashCreditCard)listPaymentCreditCard.get(0);                   
                    ccNumber2=cashCreditCard.getCcNumber();
                    ccBank2=cashCreditCard.getDebitBankName();
                    ccValid2=cashCreditCard.getExpiredDate();
                    ccCharge2=cashCreditCard.getBankCost();
                }
                if(paymentSystem1.isCardInfo() == true 
                    && paymentSystem1.getPaymentType() == 0
                    && paymentSystem1.isBankInfoOut() == false 
                    && paymentSystem1.isCheckBGInfo() == false){
                    
                    if (listPaymentCreditCard.size()>0){
                        ccName2 =cashCreditCard.getCcName();
                    }
                    drawPrint+=""
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Credit Card Name "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccName2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Credit Card No "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccNumber2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Bank "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccBank2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Validity "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccValid2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Bank Cost "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": <small>"+paymentSystem1.getBankCostPercent()+"%</small>"
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+Formater.formatNumber(ccCharge2,"#,###")
                        + "</div>"
                    + "</div>";
                    total+=ccCharge;
                    /////BG PAYMENT
                }else if(paymentSystem1.isCardInfo() == false 
                    && paymentSystem1.getPaymentType() == 2
                    && paymentSystem1.isBankInfoOut() == true 
                    && paymentSystem1.isCheckBGInfo() == false){
                    if (listPaymentCreditCard.size()>0){
                        ccName2 =cashCreditCard.getChequeAccountName();
                    }
                    drawPrint+=""
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Bank Name "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccBank2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Bank Account No "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccName2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "BG No "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccNumber2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Expired Date "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccValid2
                        + "</div>"
                    + "</div>";
                    /////CHECK PAYMENT
                    }else if(paymentSystem1.isCardInfo() == true 
                        && paymentSystem1.getPaymentType() == 0
                        && paymentSystem1.isBankInfoOut() == false 
                        && paymentSystem1.isCheckBGInfo() == false){
                        if (listPaymentCreditCard.size()>0){
                            ccName2 =cashCreditCard.getChequeAccountName();
                        }
                    drawPrint+=""
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Bank Name "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccBank2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Bank Account No "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccName2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Cheque No "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccNumber2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Expired Date "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccValid2
                        + "</div>"
                    + "</div>";
                    /////DEBIT PAYMENT
                    }else if(paymentSystem1.isCardInfo() == false 
                         && paymentSystem1.getPaymentType() == 2
                         && paymentSystem1.isBankInfoOut() == false 
                         && paymentSystem1.isCheckBGInfo() == false){
                        if (listPaymentCreditCard.size()>0){
                            ccName2 =cashCreditCard.getDebitCardName();
                        }
                    drawPrint+=""
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Bank Name "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccBank2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Card No "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccNumber2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Expired Date "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+ccValid2
                        + "</div>"
                    + "</div>"
                    + "<div class='row'>"
                        + "<div class='col-xs-5'>"
                            + "Bank Cost "
                        + "</div>"
                        + "<div class='col-xs-2' style='text-align:right;'>"
                            + ": "
                        + "</div>"
                        + "<div class='col-xs-5' style='text-align:right;'>"
                            + " "+Formater.formatNumber(ccCharge2,"#,###")
                        + "</div>"
                    + "</div>";
                    total+=ccCharge2;
                    
                } 
               drawPrint+=""
                + "<div class='row'>"
                    + "<div class='col-xs-5'>"
                        + "Amount"
                    + "</div>"
                    + "<div class='col-xs-2' style='text-align:right;'>"
                        + ": "+currencyType.getCode()
                    + "</div>"
                    + "<div class='col-xs-5' style='text-align:right;'>"
                        + " "+Formater.formatNumber(cashPayments.getAmount(),"#,###")
                    + "</div>"
                + "</div>";
               totals += cashPayments.getAmount();
            }
            drawPrint+=""
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Total Payment"
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "+currencyType.getCode()
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+Formater.formatNumber(totals,"#,###")
                + "</div>"
            + "</div>";
            double changess= total - totals;
            drawPrint+=""
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Change "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "+currencyType.getCode()
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+Formater.formatNumber(changess,"#,###")
                + "</div>"
            + "</div>";
        }else if (listCashPayment.size()!=0){
            //SINGLE PAYMENT
            
            drawPrint+=""
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Invoice Number"
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+billMain.getInvoiceNumber()
                 + "</div>"
            + "</div>"        
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Payment Type"
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+displayPaymentSystem
                 + "</div>"
            + "</div>";
            CashPayments cashPayments = (CashPayments)listCashPayment.get(0);
            PaymentSystem paymentSystem1 = new PaymentSystem();
            try {
                paymentSystem1 = PstPaymentSystem.fetchExc(cashPayments.getPaymentTypeLong());
            } catch (Exception e) {
            }
            
            if(paymentSystem.isCardInfo() == true 
                 && paymentSystem.getPaymentType() == 0
                 && paymentSystem.isBankInfoOut() == false 
                 && paymentSystem.isCheckBGInfo() == false){
            drawPrint+=""
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Credit Card Name "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccName
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Credit Card No "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccNumber
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Bank "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccBank
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Validity "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccValid
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Bank Cost "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": <small>"+paymentSystem.getBankCostPercent()+"%</small>"
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+Formater.formatNumber(ccCharge,"#,###")
                + "</div>"
            + "</div>";
            total+=ccCharge;
        /////BG PAYMENT
        }else if(paymentSystem.isCardInfo() == false 
	     && paymentSystem.getPaymentType() == 2
	     && paymentSystem.isBankInfoOut() == true 
	     && paymentSystem.isCheckBGInfo() == false){
            drawPrint+=""
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Bank Name "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccBank
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Bank Account No "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccName
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "BG No "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccNumber
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Expired Date "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccValid
                + "</div>"
            + "</div>";
            /////CHECK PAYMENT
            }else if(paymentSystem.isCardInfo() == true 
                && paymentSystem.getPaymentType() == 0
                && paymentSystem.isBankInfoOut() == false 
                && paymentSystem.isCheckBGInfo() == false){
            drawPrint+=""
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Bank Name "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccBank
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Bank Account No "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccName
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Cheque No "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccNumber
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Expired Date "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccValid
                + "</div>"
            + "</div>";
        /////DEBIT PAYMENT
        }else if(paymentSystem.isCardInfo() == false 
	     && paymentSystem.getPaymentType() == 2
	     && paymentSystem.isBankInfoOut() == false 
	     && paymentSystem.isCheckBGInfo() == false){
            drawPrint+=""
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Bank Name "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccBank
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Card No "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccNumber
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Expired Date "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+ccValid
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Bank Cost "
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + " "+Formater.formatNumber(ccCharge,"#,###")
                + "</div>"
            + "</div>";
            total+=ccCharge;
        }
        if (paymentSystem.getCostingTo()!=0){
            total = 0;
        }
        double change = 0;
        String paidDisplay = "";
        double paid = 0;
     
        if(printType.equals("printpay")){
            paidDisplay = "Paid";
            
            paid = cashPayments.getAmount() * cashPayments.getRate();
            change = (cashPayments.getAmount() * cashPayments.getRate()) -total;
        }else{
            paidDisplay = "Return";
            paid = total;
            change = total-total;
        }

        if(billMain.getDocType()==0 && billMain.getTransactionStatus()==1 && billMain.getTransctionType()==1){
        }else{
            drawPrint+=""
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + ""+paidDisplay+" "
                + "</div>"
                + "<div class='col-xs-3' style='text-align:right; font-size:10px'>"
                    + ": "+currencyType.getCode()
                + "</div>"
                + "<div class='col-xs-4' style='text-align:right;'>"
                    + " "+Formater.formatNumber(paid,"#,###")
                + "</div>"
            + "</div>";
            drawPrint+=""
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Amount "
                + "</div>"
                + "<div class='col-xs-3' style='text-align:right; font-size:10px'>"
                    + ": "+currencyType.getCode()
                + "</div>"
                + "<div class='col-xs-4' style='text-align:right;'>"
                    + " "+Formater.formatNumber(total,"#,###")
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "Change "
                + "</div>"
                + "<div class='col-xs-3' style='text-align:right; font-size:10px;'>"
                    + ": "+currencyType.getCode()
                + "</div>"
                + "<div class='col-xs-4' style='text-align:right;'>"
                    + " "+Formater.formatNumber(change,"#,###")
                + "</div>"
            + "</div>";
            }
        }else{
            drawPrint+=""
             + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "<b>Invoice Number</b>"
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + "<b>"+billMain.getInvoiceNumber()+"</b>"
                 + "</div>"
            + "</div>"        
            + "<div class='row'>"
                + "<div class='col-xs-5'>"
                    + "<b>Transaction Type</b>"
                + "</div>"
                + "<div class='col-xs-2' style='text-align:right;'>"
                    + ": "
                + "</div>"
                + "<div class='col-xs-5' style='text-align:right;'>"
                    + "<b> Credit</b>"
                 + "</div>"
            + "</div>";
        }
 
        return drawPrint;
     }
     
     public String drawHeaderClosingDefault(BillMainCostum billMainCostum, String headerTitle){
	 String drawPrint = "";
	    drawPrint+= ""
	    + "<div class='row'>"
		   + "<div class='col-xs-12'>"
			+ "<center>"
			    + ""+billMainCostum.getCompanyName()+"<br>"
			    + ""+billMainCostum.getLocationName()+"<br>"
			    + ""+billMainCostum.getLocationAddress()+"<br>"
			    + "Telp: "+billMainCostum.getLocationTelp()+". Fax: "+billMainCostum.getLocationFax()+"<br>"
			    + "Email: "+billMainCostum.getLocationEmail()+"<br>"
			    + "Website: "+billMainCostum.getLocationWeb().replace("http://", "www.").replace("/", "")
			+ "</center>"
			+ "<hr style='border:1px dashed #000;margin:0px;'>"
		    + "</div>"
	       + "</div>"
		+ "<div class='row'>"
		   + "<div class='col-xs-12'>"
		       + "<center>"+headerTitle+"</center>"
		   + "</div>"
		    + "<div class='col-xs-12'>"
			+ "<hr style='border:1px dashed #000;margin:0px;'>"
		    + "</div>"
	       + "</div>";
	 return drawPrint;
     }
     
     public String drawHeaderClosingCustom(BillMainCostum billMainCostum, String headerTitle){
	 String drawPrint = "";
	    drawPrint+= drawHeaderClosingDefault(billMainCostum, headerTitle);
	 return drawPrint;
     }
     
     public String drawContentOpeningBalance(Location location, String userName, 
	     CashCashier cashCashier, Vector listBalance){
	 String drawPrint = "";
	 drawPrint += ""
	 + "<div class='row'>"
	      + "<div class='col-xs-6'>"
		  + ""+location.getName()
	      + "</div>"
	      + "<div class='col-xs-6'>"
		  + "<font align='right'>Beginning Balance</font>"
	      + "</div>"
	  + "</div>"
	  + "<div class='row'>"
	      + "<div class='col-xs-6'>"
		  + "Date: "+Formater.formatDate(new Date(),"yyyy-MM-dd")
	      + "</div>"
	      + "<div class='col-xs-6'>"
		  + "Time: "+Formater.formatDate(new Date(),"kk:mm:ss")
	      + "</div>"
	   + "</div>"
	   + "<div class='row'>"
	      + "<div class='col-xs-6'>"
		  + "Cashier: "+userName
	      + "</div>"
	       + "<div class='col-xs-12'>"
		   + "<hr style='border:1px dashed #000;margin:0px;'>"
	       + "</div>"

	   + "</div>"
	   + "<div class='row'>"
	      + "<div class='col-xs-6'>"
		  + "Cash"
	      + "</div>"
	       + "<div class='col-xs-6'>"
		  + "<font align='right'>Total</font>"
	      + "</div>"
	       + "<div class='col-xs-12'>"
		   + "<hr style='border:1px dashed #000;margin:0px;'>"
	       + "</div>"

	   + "</div>";

	  
	   if(listBalance.size() != 0){
	       for(int i = 0; i<listBalance.size();i++){
		   Balance balance = (Balance) listBalance.get(i);
		   CurrencyType currencyType;
		   try{
		       currencyType = PstCurrencyType.fetchExc(balance.getCurrencyOid());
		   }catch(Exception ex){
		       currencyType = new CurrencyType();
		   }

		   drawPrint += ""
		   + "<div class='row'>"
		      + "<div class='col-xs-6'>"
			  + ""+currencyType.getCode()
		      + "</div>"
		       + "<div class='col-xs-6'>"
			  + "<font align='right'>"+Formater.formatNumber(balance.getBalanceValue(),"#,###")+"</font>"
		      + "</div>"

		   + "</div>";
	       }
	   }

       drawPrint += ""
	   + "<div class='row'>"
	       + "<div class='col-xs-12'>"
		   + "<hr style='border:1px dashed #000;margin:0px;'>"
	       + "</div>"
	   + "</div>"
	   + "<div class='row'>"
	       + "<div class='col-xs-12'>"
		   +"&nbsp;"
		   + "<br><br><br><br><br>"
	       + "</div>"
	   + "</div>";
	 return drawPrint;
     }
     
     public String drawContentOpeningBalance2(Location location, String userName, 
	     CashCashier cashCashier, Vector listBalance){
	 String drawPrint = "";
         Balance balance2 = new Balance();
         balance2 = (Balance) listBalance.get(0);
	 drawPrint += ""
         
	 + "<div class='row'>"
	      + "<div class='col-xs-6'>"
		  + ""+location.getName()
	      + "</div>"
	      + "<div class='col-xs-6'>"
		  + "<font align='right'>Beginning Balance</font>"
	      + "</div>"
	  + "</div>"
	  + "<div class='row'>"
	      + "<div class='col-xs-6'>"
		  + "Date: "+Formater.formatDate(new Date(),"yyyy-MM-dd")
	      + "</div>"
	      + "<div class='col-xs-6'>"
		  + "Time: "+Formater.formatDate(new Date(),"kk:mm:ss")
	      + "</div>"
	   + "</div>"
           + "<div class='row'>"
	      + "<div class='col-xs-6'>"
		  + "Open Date: "+Formater.formatDate(balance2.getBalanceDate(),"yyyy-MM-dd")
	      + "</div>"
	      + "<div class='col-xs-6'>"
		  + "Open Time: "+Formater.formatDate(balance2.getBalanceDate(),"kk:mm:ss")
	      + "</div>"
	   + "</div>"
	   + "<div class='row'>"
	      + "<div class='col-xs-6'>"
		  + "Cashier: "+userName
	      + "</div>"
	       + "<div class='col-xs-12'>"
		   + "<hr style='border:1px dashed #000;margin:0px;'>"
	       + "</div>"

	   + "</div>"
	   + "<div class='row'>"
	      + "<div class='col-xs-6'>"
		  + "Cash"
	      + "</div>"
	       + "<div class='col-xs-6'>"
		  + "<font align='right'>Total</font>"
	      + "</div>"
	       + "<div class='col-xs-12'>"
		   + "<hr style='border:1px dashed #000;margin:0px;'>"
	       + "</div>"

	   + "</div>";
           

	  
	   if(listBalance.size() != 0){
	       for(int i = 0; i<listBalance.size();i++){
		   Balance balance = (Balance) listBalance.get(i);
		   CurrencyType currencyType;
		   try{
		       currencyType = PstCurrencyType.fetchExc(balance.getCurrencyOid());
		   }catch(Exception ex){
		       currencyType = new CurrencyType();
		   }

		   drawPrint += ""
		   + "<div class='row'>"
		      + "<div class='col-xs-6'>"
			  + ""+currencyType.getCode()
		      + "</div>"
		       + "<div class='col-xs-6'>"
			  + "<font align='right'>"+Formater.formatNumber(balance.getBalanceValue(),"#,###")+"</font>"
		      + "</div>"

		   + "</div>";
	       }
	   }
           drawPrint += ""
            + "<div class='row'>"
                + "<div class='col-xs-6'>"
                     + "Receipt By"
                     + "<br>"
                     + "<br>"
                     + "<br>"
                     + "<br>"
                     + "<br>"
                     + "_______________"
                 + "</div>"
           + "</div>";

       drawPrint += ""
	   + "<div class='row'>"
	       + "<div class='col-xs-12'>"
		   + "<hr style='border:1px dashed #000;margin:0px;'>"
	       + "</div>"
	   + "</div>"
	   + "<div class='row'>"
	       + "<div class='col-xs-12'>"
		   +"&nbsp;"
		   + "<br><br><br><br><br>"
	       + "</div>"
	   + "</div>";
	 return drawPrint;
     }
     
     public String drawContentClosingBalance(String userName, CashCashier cashCashier, 
	     Location location, Vector listBalance, Vector listBalanceEnd){
	 
	 int cashSale = PstBillMain.getCount(""+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'");
	
	int creditSale = PstBillMain.getCount(""+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'");
	
	int cashReturn = PstBillMain.getCount(""+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='1' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'");
	
	int creditReturn = PstBillMain.getCount(""+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='1' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'");
	
	int totalSale = cashSale+creditSale;
	int totalReturn = cashReturn+creditReturn;
	
	double cashSaleAmount = PstCustomBillMain.getSummaryCash(cashCashier.getOID());
	double creditSaleAmount  = PstCustomBillMain.getSummarySalesCredit(cashCashier.getOID());
	double cashReturnAmount = PstCustomBillMain.getSummaryCashReturn(cashCashier.getOID());
	double creditReturnAmount  = PstCustomBillMain.getSummarySalesCreditReturn(cashCashier.getOID());
	double totalSaleAmount = cashSaleAmount + creditSaleAmount;
	double totalReturnAmount =  cashReturnAmount+creditReturnAmount;
	
	
	
	
	 String drawPrint = "";
	 drawPrint += ""
	 + "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+"<center>"+userName+"</center>"
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+"<center>"+cashCashier.getSpvName()+"</center>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-12'>"
		+"&nbsp;"
		+ "<br><br>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	   + "<div class='col-xs-6'>"
	       + ""+location.getName()
	   + "</div>"
	   + "<div class='col-xs-6'>"
	       + "<font align='right'>Closing Balance</font>"
	   + "</div>"
        + "</div>"
        + "<div class='row'>"
	   + "<div class='col-xs-6'>"
	       + "Date: "+Formater.formatDate(new Date(),"yyyy-MM-dd")
	   + "</div>"
	   + "<div class='col-xs-6'>"
	       + "Time: "+Formater.formatDate(new Date(),"kk:mm:ss")
	   + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ "Cashier: "+userName
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"

	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-12'>"
		+ "<center>TRANSACTION DETAIL</center>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"

	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Transaction"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>Qty</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>Total Rp</font>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"

	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Cash Sale"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>"+ cashSale +"</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>"+Formater.formatNumber(cashSaleAmount,"#,###")+"</font>"
	    + "</div>"

	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Credit Sale"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>"+ creditSale +"</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>"+Formater.formatNumber(creditSaleAmount,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Total Sale"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>"+ totalSale +"</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>"+Formater.formatNumber(totalSaleAmount,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>";

	Vector listCosting = PstCustomBillMain.listCosting(0, 0, 
		"costingMaterial."+PstMatCosting.fieldNames[PstMatCosting.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"'", 
		"costing."+ PstCosting.fieldNames[PstCosting.FLD_NAME]);
	if(listCosting.size() != 0){
	    drawPrint+=""
	    + "<div class='row'>"
		+ "<div class='col-xs-12'>"
		    + "<center>COMPLIMENT DETAIL</center>"
		+ "</div>"

		 + "<div class='col-xs-12'>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "</div>"
	    + "</div>"
	    + "<div class='row'>"
		+ "<div class='col-xs-3'>"
		    + "Compliment"
		+ "</div>"
		+ "<div class='col-xs-3'>"
		    + "<center>Qty</center>"
		+ "</div>"
		+ "<div class='col-xs-3'>"
		    + "<font align='right'>Total Rp</font>"
		+ "</div>"
		+ "<div class='col-xs-12'>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "</div>"

	    + "</div>";
	    double totalAmountCosting = 0;
	    double totalQtyCosting = 0;
	    for(int i=0;i<listCosting.size();i++){
		BillMainCostum billMainCostum1 = (BillMainCostum) listCosting.get(i);
		drawPrint+= ""
		+ "<div class='row'>"
		    + "<div class='col-xs-3'>"
			+ ""+billMainCostum1.getCostingName()
		    + "</div>"
		    + "<div class='col-xs-3'>"
			+ "<center>"+billMainCostum1.getCostingQty()+"</center>"
		    + "</div>"
		    + "<div class='col-xs-3'>"
			+ "<font align='right'>"+Formater.formatNumber(billMainCostum1.getAmountCosting(), "#,###")+"</font>"
		    + "</div>"
		+ "</div>";
		totalAmountCosting+=billMainCostum1.getAmountCosting();
		totalQtyCosting+=billMainCostum1.getCostingQty();
	    }
	    drawPrint += ""
	    + "<div class='row'>"
		+ "<div class='col-xs-3'>"
		    + "Total Costing"
		+ "</div>"
		+ "<div class='col-xs-3'>"
		    + "<center>"+totalQtyCosting+"</center>"
		+ "</div>"
		+ "<div class='col-xs-3'>"
		    + "<font align='right'>"+Formater.formatNumber(totalAmountCosting, "#,###")+"</font>"
		+ "</div>"
	    + "</div>";
	}

	drawPrint+= ""
	+ "<div class='row'>"
	    + "<div class='col-xs-12'>"
		+ "<center>RETURN DETAIL</center>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"

	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Return"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>Qty</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>Total Rp</font>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"

	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Cash Return"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>"+ cashReturn +"</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>"+Formater.formatNumber(cashReturnAmount,"#,###")+"</font>"
	    + "</div>"

	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Credit Return"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>"+ creditReturn +"</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>"+Formater.formatNumber(creditReturnAmount,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Total Sale"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>"+ totalReturn +"</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>"+Formater.formatNumber(totalReturnAmount,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>";
	drawPrint+=""
	+ "<div class='row'>"
	    + "<div class='col-xs-12'>"
		+ "<center>PAYMENT DETAIL</center>"
	    + "</div>"

	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-12'>"
		+ "Cash Sale"
	    + "</div>"

	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>";
	
        
	Vector listPaymentSystem = PstPaymentSystem.listAll();
        for (int j = 0; j<listBalance.size();j++){
            //PAYMENT DETAIL DATA
            double cashPayment = 0;
            double ccPayment = 0;
            double debitPayment = 0;
            double bgPayment = 0;
            double checkPayment = 0;
            Balance entBalance = new Balance();
            entBalance = (Balance)listBalance.get(j);
            for(int i=0; i<listPaymentSystem.size();i++){
		PaymentSystem paymentSystem = (PaymentSystem) listPaymentSystem.get(i);
		double payment = PstCashPayment.getSumListDetailBayar2("CBM."+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
			+ "AND CP."+PstCashPayment.fieldNames[PstCashPayment.FLD_PAY_TYPE]+"='"+paymentSystem.getOID()+"' "
                        + "AND CP."+PstCashPayment.fieldNames[PstCashPayment.FLD_CURRENCY_ID]+"='"+entBalance.getCurrencyOid()+"'"
			+ "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
			+ "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
			+ "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
			+ "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' ");
		if(paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCardInfo() == false 
			&& paymentSystem.isCheckBGInfo() == false 
			&& paymentSystem.getPaymentType() == 1){
		    cashPayment += payment;
		}else if(paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCardInfo() == true 
			&& paymentSystem.isCheckBGInfo() == false 
			&& paymentSystem.getPaymentType() == 0){
		    ccPayment += payment;
		}else if(paymentSystem.isBankInfoOut() == true 
			&& paymentSystem.isCardInfo() == false 
			&& paymentSystem.isCheckBGInfo() == false 
			&& paymentSystem.getPaymentType() == 2){
		    bgPayment += payment;
		}else if(paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCardInfo() == false 
			&& paymentSystem.isCheckBGInfo() == true 
			&& paymentSystem.getPaymentType() == 0){
		    checkPayment += payment;
		}else if(paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCardInfo() == false 
			&& paymentSystem.isCheckBGInfo() == false 
			&& paymentSystem.getPaymentType() == 2){
		    debitPayment += payment;
		}
	    }
            
            double change = PstCashReturn.getReturnSummary2(cashCashier.getOID(),entBalance.getCurrencyOid());
            double subTotal = cashPayment+ccPayment+debitPayment+bgPayment+checkPayment-change-totalReturnAmount;
            
            CurrencyType entCurrencyType = new CurrencyType();
            try {
                entCurrencyType = PstCurrencyType.fetchExc(entBalance.getCurrencyOid());
            } catch (Exception e) {
            }
            
            drawPrint +=" "
            + "<div class='row'>"
                + "<div class='col-xs-6'>"
                    + "Type"
                + "</div>"
                + "<div class='col-xs-6'>"
                    + "<font align='right'>Total "+entCurrencyType.getCode()+"</font>"
                + "</div>"
                 + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-6'>"
                    + ""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CASH]
                + "</div>"
                + "<div class='col-xs-6'>"
                    + "<font align='right'>"+Formater.formatNumber(cashPayment,"#,###")+"</font>"
                + "</div>"
                 + "<div class='col-xs-12'>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-6'>"
                    + ""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD]
                + "</div>"
                + "<div class='col-xs-6'>"
                    + "<font align='right'>"+Formater.formatNumber(ccPayment,"#,###")+"</font>"
                + "</div>"
                 + "<div class='col-xs-12'>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-6'>"
                    + ""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD]
                + "</div>"
                + "<div class='col-xs-6'>"
                    + "<font align='right'>"+Formater.formatNumber(debitPayment,"#,###")+"</font>"
                + "</div>"
                 + "<div class='col-xs-12'>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-6'>"
                    + ""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_BG]
                + "</div>"
                + "<div class='col-xs-6'>"
                    + "<font align='right'>"+Formater.formatNumber(bgPayment,"#,###")+"</font>"
                + "</div>"
                 + "<div class='col-xs-12'>"
                + "</div>"
            + "</div>"
            + "<div class='row'>"
                + "<div class='col-xs-6'>"
                    + ""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CHECK]
                + "</div>"
                + "<div class='col-xs-6'>"
                    + "<font align='right'>"+Formater.formatNumber(checkPayment,"#,###")+"</font>"
                + "</div>"
                 + "<div class='col-xs-12'>"
                + "</div>"
                + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
            + "</div>"
                ///payment detail here
            + "<div class='row'>"
                + "<div class='col-xs-6'>"
                    + "&Tab; Change"
                + "</div>"
                + "<div class='col-xs-6'>"
                    + "<font align='right'>"+Formater.formatNumber(change,"#,###")+"</font>"
                + "</div>"
                + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
            + "</div>"  
            + "<div class='row'>"
                + "<div class='col-xs-6'>"
                    + "&Tab; Sub Total"
                + "</div>"
                + "<div class='col-xs-6'>"
                    + "<font align='right'>"+Formater.formatNumber(subTotal,"#,###")+"</font>"
                + "</div>"
                + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
            + "</div>";
            
            //BEGINING BALANCE
            drawPrint += ""
            + "<div class='row'>"
               + "<div class='col-xs-6'>"
                   + "Beginning Balance"
               + "</div>"
                + "<div class='col-xs-6'>"
                   + "<font align='right'>"+Formater.formatNumber(entBalance.getBalanceValue(),"#,###")+"</font>"
               + "</div>"
               + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
               + "</div>"
            + "</div>";
            double sub = subTotal+ entBalance.getBalanceValue();
            //ENDING BALANCE
            drawPrint += ""
            + "<div class='row'>"
                + "<div class='col-xs-6'>"
                    + "&Tab; Ending Balance"
                + "</div>"
                + "<div class='col-xs-6'>"
                    + "<font align='right'>"+Formater.formatNumber(sub,"#,###")+"</font>"
                + "</div>"
                + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
            + "</div>"; 
            
            String whereListBalanceEnd = ""
                + " "+PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"='"+entBalance.getCashCashierId()+"'"
                + " AND "+PstBalance.fieldNames[PstBalance.FLD_CURRENCY_ID]+"='"+entBalance.getCurrencyOid()+"'"
                + " AND "+PstBalance.fieldNames[PstBalance.FLD_BALANCE_TYPE]+"='1'";
            Vector listBalanceEndTemp = PstBalance.list(0,0,whereListBalanceEnd,"");
            Balance entBalanceEnd = new Balance();
            try{
                entBalanceEnd = (Balance) listBalanceEndTemp.get(0);
            }catch(Exception ex){
            }
            double diference = 0; 
            //updated by dewok 20190513, hasil difference belum sesuai
            /*rumus lama*/ //diference =entBalanceEnd.getBalanceValue()-entBalanceEnd.getShouldValue();
            /*rumus baru*/ diference =entBalanceEnd.getBalanceValue()-sub;
            drawPrint += ""
            + "<div class='row'>"
               + "<div class='col-xs-6'>"
                   + "Cash In Cashier "
               + "</div>"
                + "<div class='col-xs-6'>"
                   + "<font align='right'>"+Formater.formatNumber(entBalanceEnd.getBalanceValue(),"#,###")+"</font>"
               + "</div>"
               + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
               + "</div>"
            + "</div>";
            drawPrint += ""
            + "<div class='row'>"
                + "<div class='col-xs-6'>"
                    + "&Tab; Difference"
                + "</div>"
                + "<div class='col-xs-6'>"
                    + "<font align='right'>"+Formater.formatNumber(diference,"#,###")+"</font>"
                + "</div>"
                + "<div class='col-xs-12'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                    + "<hr style='border:1px dashed #000;margin:0px;'>"
                + "</div>"
            + "</div>";   
        }
	
        drawPrint += ""
	+ "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ "Cashier"
		+ "<br>"
		+ "<br>"
		+ "<br>"
		+ "<br>"
		+ "<br>"
		+ ""+location.getName()
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+ "Receipt By"
		+ "<br>"
		+ "<br>"
		+ "<br>"
		+ "<br>"
		+ "<br>"
		+ "_______________"
	    + "</div>"
	+ "</div>";
	 return drawPrint;
     }
     
     public String drawContentClosingBalance2(String userName, CashCashier cashCashier, 
	     Location location, Vector listBalance, Vector listBalanceEnd){
	 
	 int cashSale = PstBillMain.getCount(""+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'");
	
	int creditSale = PstBillMain.getCount(""+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='1' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'");
	
	int cashReturn = PstBillMain.getCount(""+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='1' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'");
	
	int creditReturn = PstBillMain.getCount(""+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='1' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='1' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
		+ "AND "+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1'");
	
	int totalSale = cashSale+creditSale;
	int totalReturn = cashReturn+creditReturn;
	
	double cashSaleAmount = PstCustomBillMain.getSummaryCash(cashCashier.getOID());
	double creditSaleAmount  = PstCustomBillMain.getSummarySalesCredit(cashCashier.getOID());
	double cashReturnAmount = PstCustomBillMain.getSummaryCashReturn(cashCashier.getOID());
	double creditReturnAmount  = PstCustomBillMain.getSummarySalesCreditReturn(cashCashier.getOID());
	double totalSaleAmount = cashSaleAmount + creditSaleAmount;
	double totalReturnAmount =  cashReturnAmount+creditReturnAmount;
	
	
	//PAYMENT DETAIL DATA
	double cashPayment = 0;
	double ccPayment = 0;
	double debitPayment = 0;
	double bgPayment = 0;
	double checkPayment = 0;
	Vector listPaymentSystem = PstPaymentSystem.listAll();
	if(listPaymentSystem.size() != 0){
	    for(int i=0; i<listPaymentSystem.size();i++){
		PaymentSystem paymentSystem = (PaymentSystem) listPaymentSystem.get(i);
		double payment = PstCashPayment.getSumListDetailBayar("CBM."+PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"' "
			+ "AND CP."+PstCashPayment.fieldNames[PstCashPayment.FLD_PAY_TYPE]+"='"+paymentSystem.getOID()+"' "
			+ "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"='0' "
			+ "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE]+"='0' "
			+ "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS]+"='0' "
			+ "AND CBM."+PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING]+"='1' ");
		if(paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCardInfo() == false 
			&& paymentSystem.isCheckBGInfo() == false 
			&& paymentSystem.getPaymentType() == 1){
		    cashPayment += payment;
		}else if(paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCardInfo() == true 
			&& paymentSystem.isCheckBGInfo() == false 
			&& paymentSystem.getPaymentType() == 0){
		    ccPayment += payment;
		}else if(paymentSystem.isBankInfoOut() == true 
			&& paymentSystem.isCardInfo() == false 
			&& paymentSystem.isCheckBGInfo() == false 
			&& paymentSystem.getPaymentType() == 2){
		    bgPayment += payment;
		}else if(paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCardInfo() == false 
			&& paymentSystem.isCheckBGInfo() == true 
			&& paymentSystem.getPaymentType() == 0){
		    checkPayment += payment;
		}else if(paymentSystem.isBankInfoOut() == false 
			&& paymentSystem.isCardInfo() == false 
			&& paymentSystem.isCheckBGInfo() == false 
			&& paymentSystem.getPaymentType() == 2){
		    debitPayment += payment;
		}
	    }
	}
	
	
	//CHANGE
	double change = PstCashReturn.getReturnSummary(cashCashier.getOID());
	double subTotal = cashPayment+ccPayment+debitPayment+bgPayment+checkPayment-change-totalReturnAmount;
	
        //GET DATE
        Balance balance2 = new Balance();
        balance2 = (Balance)listBalance.get(0);
        
	 String drawPrint = "";
	 drawPrint += ""
	 + "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+"<center>"+userName+"</center>"
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+"<center>"+cashCashier.getSpvName()+"</center>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-12'>"
		+"&nbsp;"
		+ "<br><br>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	   + "<div class='col-xs-6'>"
	       + ""+location.getName()
	   + "</div>"
	   + "<div class='col-xs-6'>"
	       + "<font align='right'>Closing Balance</font>"
	   + "</div>"
        + "</div>"
        + "<div class='row'>"
	   + "<div class='col-xs-6'>"
	       + "Date: "+Formater.formatDate(new Date(),"yyyy-MM-dd")
	   + "</div>"
	   + "<div class='col-xs-6'>"
	       + "Time: "+Formater.formatDate(new Date(),"kk:mm:ss")
	   + "</div>"
	 + "</div>"
         + "<div class='row'>"
	   + "<div class='col-xs-6'>"
	       + "Closed Date: "+Formater.formatDate(balance2.getBalanceDate(),"yyyy-MM-dd")
	   + "</div>"
	   + "<div class='col-xs-6'>"
	       + "Closed Time: "+Formater.formatDate(balance2.getBalanceDate(),"kk:mm:ss")
	   + "</div>"
	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ "Cashier: "+userName
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"

	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-12'>"
		+ "<center>TRANSACTION DETAIL</center>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"

	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Transaction"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>Qty</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>Total Rp</font>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"

	 + "</div>"
	 + "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Cash Sale"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>"+ cashSale +"</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>"+Formater.formatNumber(cashSaleAmount,"#,###")+"</font>"
	    + "</div>"

	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Credit Sale"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>"+ creditSale +"</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>"+Formater.formatNumber(creditSaleAmount,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Total Sale"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>"+ totalSale +"</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>"+Formater.formatNumber(totalSaleAmount,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>";

	Vector listCosting = PstCustomBillMain.listCosting(0, 0, 
		"costingMaterial."+PstMatCosting.fieldNames[PstMatCosting.FLD_CASH_CASHIER_ID]+"='"+cashCashier.getOID()+"'", 
		"costing."+ PstCosting.fieldNames[PstCosting.FLD_NAME]);
	if(listCosting.size() != 0){
	    drawPrint+=""
	    + "<div class='row'>"
		+ "<div class='col-xs-12'>"
		    + "<center>COMPLIMENT DETAIL</center>"
		+ "</div>"

		 + "<div class='col-xs-12'>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "</div>"
	    + "</div>"
	    + "<div class='row'>"
		+ "<div class='col-xs-3'>"
		    + "Compliment"
		+ "</div>"
		+ "<div class='col-xs-3'>"
		    + "<center>Qty</center>"
		+ "</div>"
		+ "<div class='col-xs-3'>"
		    + "<font align='right'>Total Rp</font>"
		+ "</div>"
		+ "<div class='col-xs-12'>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		    + "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "</div>"

	    + "</div>";
	    double totalAmountCosting = 0;
	    double totalQtyCosting = 0;
	    for(int i=0;i<listCosting.size();i++){
		BillMainCostum billMainCostum1 = (BillMainCostum) listCosting.get(i);
		drawPrint+= ""
		+ "<div class='row'>"
		    + "<div class='col-xs-3'>"
			+ ""+billMainCostum1.getCostingName()
		    + "</div>"
		    + "<div class='col-xs-3'>"
			+ "<center>"+billMainCostum1.getCostingQty()+"</center>"
		    + "</div>"
		    + "<div class='col-xs-3'>"
			+ "<font align='right'>"+Formater.formatNumber(billMainCostum1.getAmountCosting(), "#,###")+"</font>"
		    + "</div>"
		+ "</div>";
		totalAmountCosting+=billMainCostum1.getAmountCosting();
		totalQtyCosting+=billMainCostum1.getCostingQty();
	    }
	    drawPrint += ""
	    + "<div class='row'>"
		+ "<div class='col-xs-3'>"
		    + "Total Costing"
		+ "</div>"
		+ "<div class='col-xs-3'>"
		    + "<center>"+totalQtyCosting+"</center>"
		+ "</div>"
		+ "<div class='col-xs-3'>"
		    + "<font align='right'>"+Formater.formatNumber(totalAmountCosting, "#,###")+"</font>"
		+ "</div>"
	    + "</div>";
	}

	drawPrint+= ""
	+ "<div class='row'>"
	    + "<div class='col-xs-12'>"
		+ "<center>RETURN DETAIL</center>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"

	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Return"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>Qty</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>Total Rp</font>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"

	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Cash Return"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>"+ cashReturn +"</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>"+Formater.formatNumber(cashReturnAmount,"#,###")+"</font>"
	    + "</div>"

	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Credit Return"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>"+ creditReturn +"</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>"+Formater.formatNumber(creditReturnAmount,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-3'>"
		+ "Total Sale"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<center>"+ totalReturn +"</center>"
	    + "</div>"
	    + "<div class='col-xs-3'>"
		+ "<font align='right'>"+Formater.formatNumber(totalReturnAmount,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>";
	drawPrint+=""
	+ "<div class='row'>"
	    + "<div class='col-xs-12'>"
		+ "<center>PAYMENT DETAIL</center>"
	    + "</div>"

	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-12'>"
		+ "Cash Sale"
	    + "</div>"

	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ "Type"
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+ "<font align='right'>Total Rp</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ ""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CASH]
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+ "<font align='right'>"+Formater.formatNumber(cashPayment,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ ""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CREDIT_CARD]
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+ "<font align='right'>"+Formater.formatNumber(ccPayment,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ ""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_DEBIT_CARD]
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+ "<font align='right'>"+Formater.formatNumber(debitPayment,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ ""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_BG]
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+ "<font align='right'>"+Formater.formatNumber(bgPayment,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ ""+PstCustomBillMain.paymentTypeNames[PstCustomBillMain.PAYMENT_TYPE_CHECK]
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+ "<font align='right'>"+Formater.formatNumber(checkPayment,"#,###")+"</font>"
	    + "</div>"
	     + "<div class='col-xs-12'>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>"
	    ///payment detail here


	+ "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ "&Tab; Change"
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+ "<font align='right'>"+Formater.formatNumber(change,"#,###")+"</font>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>"  
	+ "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ "&Tab; Sub Total"
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+ "<font align='right'>"+Formater.formatNumber(subTotal,"#,###")+"</font>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>";
	if(listBalance.size() != 0){
	    for(int i = 0; i<listBalance.size();i++){
		Balance balance = (Balance) listBalance.get(i);
		CurrencyType currencyType;
		try{
		    currencyType = PstCurrencyType.fetchExc(balance.getCurrencyOid());
		}catch(Exception ex){
		    currencyType = new CurrencyType();
		}

		drawPrint += ""
		+ "<div class='row'>"
		   + "<div class='col-xs-6'>"
		       + "Beginning Balance"
		   + "</div>"
		    + "<div class='col-xs-6'>"
		       + "<font align='right'>"+Formater.formatNumber(balance.getBalanceValue(),"#,###")+"</font>"
		   + "</div>"
		   + "<div class='col-xs-12'>"
			+ "<hr style='border:1px dashed #000;margin:0px;'>"
		   + "</div>"
		+ "</div>";

		subTotal+=balance.getBalanceValue();
	    }
	}
	drawPrint += ""
	+ "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ "&Tab; Ending Balance"
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+ "<font align='right'>"+Formater.formatNumber(subTotal,"#,###")+"</font>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>";
	double diference = 0;

	if(listBalanceEnd.size() != 0){
	    for(int i = 0; i<listBalanceEnd.size();i++){
		Balance balance = (Balance) listBalanceEnd.get(i);
		CurrencyType currencyType;
		try{
		    currencyType = PstCurrencyType.fetchExc(balance.getCurrencyOid());
		}catch(Exception ex){
		    currencyType = new CurrencyType();
		}

		drawPrint += ""
		+ "<div class='row'>"
		   + "<div class='col-xs-6'>"
		       + "Cash In Cashier"
		   + "</div>"
		    + "<div class='col-xs-6'>"
		       + "<font align='right'>"+Formater.formatNumber(balance.getBalanceValue(),"#,###")+"</font>"
		   + "</div>"
		   + "<div class='col-xs-12'>"
			+ "<hr style='border:1px dashed #000;margin:0px;'>"
		   + "</div>"
		+ "</div>";
		diference=balance.getBalanceValue()-balance.getShouldValue();
	    }
	}
	drawPrint += ""
	+ "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ "&Tab; Difference"
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+ "<font align='right'>"+Formater.formatNumber(diference,"#,###")+"</font>"
	    + "</div>"
	    + "<div class='col-xs-12'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
		+ "<hr style='border:1px dashed #000;margin:0px;'>"
	    + "</div>"
	+ "</div>"
	+ "<div class='row'>"
	    + "<div class='col-xs-6'>"
		+ "Cashier"
		+ "<br>"
		+ "<br>"
		+ "<br>"
		+ "<br>"
		+ "<br>"
		+ ""+location.getName()
	    + "</div>"
	    + "<div class='col-xs-6'>"
		+ "Receipt By"
		+ "<br>"
		+ "<br>"
		+ "<br>"
		+ "<br>"
		+ "<br>"
		+ "_______________"
	    + "</div>"
	+ "</div>";
	 return drawPrint;
     }
     
    public String PrintTemplateReprint(BillMainCostum billMainCostum, String nameInvoice, String invoiceNumb, String cashierName, BillMain billMain, TableRoom tableRoom, String printType, String paymentType, String displayPaymentSystem, double payAmount, long oidPaymentSystem, String ccName, String ccNumber, String ccValid, String ccBank, double ccCharge, String approot, String oidMember, int full) {

        //DATE TYPE
        Date dateNow = new Date();

        this.oidPay = oidPaymentSystem;
        this.paymentTypes = paymentType;
        this.displayPaymentTypes = displayPaymentSystem;
        this.ccNames = ccName;
        this.ccNumbers = ccNumber;
        this.ccBanks = ccBank;
        this.ccValids = ccValid;
        this.ccCharges = ccCharge;
        this.payAmounts = payAmount;
        this.printTypes = printType;

        //STRING TYPE
        String datePrint = Formater.formatDate(billMain.getBillDate(), "yyyy-MM-dd");
        String timePrint = Formater.formatDate(billMain.getBillDate(), "kk:mm:ss");

        String datePrinting = Formater.formatDate(new Date(), "yyyy-MM-dd");
        String timePrinting = Formater.formatDate(new Date(), "kk:mm:ss");

        String drawPrint = "";

        //CHECK SYSTEM PROPERTY :: PAPER TYPE
        String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");

        //CHECK SYSTEM PROPERTY :: ITEM LINE
        String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");

        //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
        String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");

        //CHECK SYSTEM PROPERTY :: PAPER WIDTH
        String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");

        //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
        String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");

        //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
        String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");

        //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
        String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");

        String printOutType = String.valueOf(full);

        String paperWidth = "";
        String paperHeight = "";
        String headerHeight = "";
        String footerHeight = "";
        String bodyHeight = "";
        String background = "";
        //WIDTH SETTING
        if (printPaperWidth.equals("0")) {
            paperWidth = "width:7.5cm;padding:0.1cm;";
        } else {
            paperWidth = "width:" + printPaperWidth + "cm;padding:0.5cm;";
        }

        //HEIGHT SETTING
        if (printPaperHeight.equals("0")) {
            paperHeight = "height:auto;";
        } else {
            paperHeight = "height:" + printPaperHeight + "cm;";
        }

        //HEADER HEIGHT SETTING
        if (printHeaderHeight.equals("0")) {
            headerHeight = "auto";
        } else {
            headerHeight = "" + printHeaderHeight + "cm";
        }

        //FOOTER HEIGHT SETTING
        if (printFooterHeight.equals("0")) {
            footerHeight = "auto";
        } else {
            footerHeight = "" + printFooterHeight + "cm";
        }

        if (printPapertType.equals("0")) {
            background = "";
        } else {
            background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url('" + approot + "/styles/cashier/img/bill.jpg');";
        }
        //String paperSetting = "style='"+background+""+paperWidth+""+paperHeight+"'";
        String paperSetting = " style=\"" + background + " " + paperWidth + " " + paperHeight + " \"";
        //background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");

        //OBJECT TYPE
        Location location;
        try {
            location = PstLocation.fetchExc(billMain.getLocationId());
        } catch (Exception ex) {
            location = new Location();
        }

        //PRINT DEFAULT
        if (printPapertType.equals("0")) {

            drawPrint += ""
                    + "<div class='page' " + paperSetting + ">"
                    + "     <div class='subPage'>";

            //HEADER CONTENT
            if (billMain.getParentId() != 0) {
                drawPrint += drawHeaderReprintExchange(billMainCostum, nameInvoice,
                        invoiceNumb, cashierName, datePrint, timePrint,
                        billMain, tableRoom, datePrinting, timePrinting);
            } else {
                drawPrint += drawHeaderDefaultRe(billMainCostum, nameInvoice,
                        invoiceNumb, cashierName, datePrint, timePrint,
                        billMain, tableRoom, datePrinting, timePrinting);
            }

                   //BODY CONTENT
            //DEFAULT :: WITHOUT SHORT BY CATEGORY
            if (printCategoryItem.equals("0")) {

                drawPrint += drawContentDefault(billMain, printItemLine, location);

                //CUSTOM :: SHORT BY CATEGORY
            } else {

                drawPrint += drawContentCustom(billMain, printItemLine, location);

            }

            if (printType.equals("printpay") || printType.equals("printreturn")) {
                PaymentSystem paymentSystem;
                try {
                    paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
                } catch (Exception ex) {
                    paymentSystem = new PaymentSystem();
                }

                drawPrint += drawPayment(paymentType, displayPaymentSystem,
                        paymentSystem, ccName, ccNumber, ccBank,
                        ccValid, ccCharge, payAmount, billMain,
                        location, printType);

            }

            //FOOTER CONTENT
            drawPrint += drawFootrDefault(billMain, approot, oidMember);

            drawPrint += ""
                    + "</div>"
                    + "</div>";

            //PRINT CUSTOM
        } else {

            drawPrint += ""
                    + "<div class='page' " + paperSetting + ">"
                    + "     <div class='subPage'>";
            //HEADER CONTENT
            drawPrint += "<div style='height:" + headerHeight + ";'>";
            //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
            if (printOutType.equals("0")) {
                drawPrint += drawHeaderCustom(billMainCostum, nameInvoice,
                        invoiceNumb, cashierName, datePrint, timePrint,
                        billMain, tableRoom);
            }
            drawPrint += ""
                    + "</div>";

            drawPrint += ""
                    + "<div style='height:10cm;'>";
            //DEFAULT :: WITHOUT SHORT BY CATEGORY
            if (printCategoryItem.equals("0")) {
                drawPrint += drawContentDefaultReprint(billMain, printItemLine, location, printOutType);
                //CUSTOM :: SHORT BY CATEGORY
            } else {
                drawPrint += drawContentCustomReprint(billMain, printItemLine, location, printOutType);
            }
            drawPrint += ""
                    + "</div>";

            drawPrint += ""
                    + "<div style='height:" + footerHeight + ";'>";
            //FOOTER CONTENT
            drawPrint += drawFootrCustom(billMain);

            drawPrint += ""
                    + "</div>";

            drawPrint += ""
                    + "</div>"
                    + "</div>";

            if (printType.equals("printpay") || printType.equals("printreturn")) {
                ///PAYMENT 
                double remind = this.paperHeights - this.heightTotalRowItem;
                if (remind < 5) {
                    drawPrint += ""
                            + "<div class='page' " + paperSetting + ">"
                            + "     <div class='subPage'>"
                            + "         <div style='height:" + headerHeight + ";'>";
                    //HEADER CONTENT
                    drawPrint += drawHeaderCustom(billMainCostum, nameInvoice,
                            invoiceNumb, cashierName, datePrint, timePrint,
                            billMain, tableRoom);
                    drawPrint += "</div>";

                    PaymentSystem paymentSystem;
                    try {
                        paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
                    } catch (Exception ex) {
                        paymentSystem = new PaymentSystem();
                    }

                    drawPrint += ""
                            + "<div style='height:10cm;'>";

                    drawPrint += drawPayment(paymentType, displayPaymentSystem,
                            paymentSystem, ccName, ccNumber, ccBank,
                            ccValid, ccCharge, payAmount, billMain,
                            location, printType);
                    drawPrint += ""
                            + "</div>";

                    drawPrint += ""
                            + "<div style='height:" + footerHeight + "'>";
                    //FOOTER CONTENT
                    drawPrint += drawFootrCustom(billMain);
                    drawPrint += ""
                            + "</div>";

                    drawPrint += ""
                            + "</div>"
                            + "</div>";
                }

            }
        }

        return drawPrint;
    }

     public String PrintTemplateReprintGuidePrice(BillMainCostum billMainCostum, 
        String nameInvoice, String invoiceNumb, String cashierName, 
        BillMain billMain, TableRoom tableRoom, String printType,
        String paymentType, String displayPaymentSystem, double payAmount, 
        long oidPaymentSystem, String ccName, String ccNumber, String ccValid, 
        String ccBank, double ccCharge, String approot, String oidMember,int full){
	     
        //DATE TYPE
        Date dateNow = new Date();

        this.oidPay = oidPaymentSystem;
        this.paymentTypes = paymentType;
        this.displayPaymentTypes = displayPaymentSystem;
        this.ccNames= ccName;  
        this.ccNumbers = ccNumber;
        this.ccBanks = ccBank;
        this.ccValids = ccValid;
        this.ccCharges = ccCharge;
        this.payAmounts = payAmount;
        this.printTypes = printType;
	     
        //STRING TYPE
        String datePrint = Formater.formatDate(billMain.getBillDate(), "yyyy-MM-dd");
        String timePrint = Formater.formatDate(billMain.getBillDate(), "kk:mm:ss");
        
        String datePrinting = Formater.formatDate(new Date(), "yyyy-MM-dd");
        String timePrinting = Formater.formatDate(new Date(), "kk:mm:ss");
        
        String drawPrint = "";
	     
        //CHECK SYSTEM PROPERTY :: PAPER TYPE
        String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");
	     
        //CHECK SYSTEM PROPERTY :: ITEM LINE
        String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");
	     
        //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
        String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");
	     
        //CHECK SYSTEM PROPERTY :: PAPER WIDTH
        String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");

        //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
        String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");

        //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
        String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");

        //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
        String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");

        String printOutType = String.valueOf(full);

        String paperWidth = "";
        String paperHeight = "";
        String headerHeight = "";
        String footerHeight = "";
        String bodyHeight = "";
        String background = "";
        //WIDTH SETTING
        if(printPaperWidth.equals("0")){
            paperWidth = "width:7.5cm;padding:0.1cm;";
        }else{
            paperWidth = "width:"+printPaperWidth+"cm;padding:0.5cm;";
        }
	     
        //HEIGHT SETTING
        if(printPaperHeight.equals("0")){
            paperHeight = "height:auto;";
        }else{
            paperHeight = "height:"+printPaperHeight+"cm;";
        }
	     
        //HEADER HEIGHT SETTING
        if(printHeaderHeight.equals("0")){
            headerHeight = "auto";
        }else{
            headerHeight = ""+printHeaderHeight+"cm";
        }
	     
        //FOOTER HEIGHT SETTING
        if(printFooterHeight.equals("0")){
            footerHeight = "auto";
        }else{
            footerHeight = ""+printFooterHeight+"cm";
        }


        if(printPapertType.equals("0")){
            background = "";
        }else{
            background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url('"+approot+"/styles/cashier/img/bill.jpg');";
        }
        //String paperSetting = "style='"+background+""+paperWidth+""+paperHeight+"'";
        String paperSetting = " style=\""+background+" "+paperWidth+" "+paperHeight+" \"";
        //background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");

        //OBJECT TYPE
        Location location;
        try{
            location = PstLocation.fetchExc(billMain.getLocationId());
        }catch(Exception ex){
            location = new Location();
        }
	     
	     //PRINT DEFAULT
        if(printPapertType.equals("0")){

            drawPrint+= ""
               + "<div class='page' "+paperSetting+">"
                   + "<div class='subPage'>";

                   //HEADER CONTENT
                   drawPrint+=drawHeaderDefaultRe(billMainCostum, nameInvoice, 
                           invoiceNumb, cashierName, datePrint, timePrint, 
                           billMain, tableRoom,datePrinting,timePrinting);


                   //BODY CONTENT


                   //DEFAULT :: WITHOUT SHORT BY CATEGORY
                   if(printCategoryItem.equals("0")){


                       drawPrint += drawContentGuidePrice(billMain, printItemLine, location);

                  //CUSTOM :: SHORT BY CATEGORY
                   }else{

                       drawPrint += drawContentCustom(billMain, printItemLine, location);

                   }

                   if(printType.equals("printpay") || printType.equals("printreturn")){
                       PaymentSystem paymentSystem;
                       try{
                           paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
                       }catch(Exception ex){
                           paymentSystem = new PaymentSystem();
                       }
                       
                       
                       drawPrint+=drawPaymentGuidePrice(paymentType, displayPaymentSystem, 
                               paymentSystem, ccName, ccNumber, ccBank, 
                               ccValid, ccCharge, payAmount, billMain, 
                               location, printType);
                       
                   }


                   //FOOTER CONTENT
                   drawPrint+=drawFootrDefault(billMain, approot, oidMember);


               drawPrint+=""
               + "</div>"
           + "</div>";


       //PRINT CUSTOM
        }else{

            drawPrint+=""
            + "<div class='page' "+paperSetting+">"
               + "<div class='subPage'>";
                   //HEADER CONTENT
                   drawPrint += ""
                   + "<div style='height:"+headerHeight+";'>";
                   //IF PAYMENT ONLY TYPE IS ACTIVE THEN THIS PART WILL NOT SHOW
                   if (printOutType.equals("0")){
                       drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
                           invoiceNumb, cashierName, datePrint, timePrint, 
                           billMain, tableRoom);
                   }
                   drawPrint +=""
                   + "</div>";

                   drawPrint +=""
                   + "<div style='height:10cm;'>";
                   //DEFAULT :: WITHOUT SHORT BY CATEGORY
                   if(printCategoryItem.equals("0")){
                       drawPrint += drawContentDefaultReprint(billMain, printItemLine, location,printOutType);
                  //CUSTOM :: SHORT BY CATEGORY
                   }else{
                       drawPrint += drawContentCustomReprint(billMain, printItemLine, location,printOutType);
                   }
                   drawPrint +=""
                   + "</div>";

                   drawPrint += ""
                   + "<div style='height:"+footerHeight+";'>";			
                   //FOOTER CONTENT
                   drawPrint += drawFootrCustom(billMain);

                   drawPrint +=""
                   + "</div>";

               drawPrint+=""
               + "</div>"
           + "</div>";

           if(printType.equals("printpay") || printType.equals("printreturn")){  
               ///PAYMENT 
               double remind= this.paperHeights-this.heightTotalRowItem;
               if (remind<5){
                   drawPrint+=""
                   + "<div class='page' "+paperSetting+">"
                      + "<div class='subPage'>"
                          + "<div style='height:"+headerHeight+";'>";
                              //HEADER CONTENT
                              drawPrint += drawHeaderCustom(billMainCostum, nameInvoice, 
                                      invoiceNumb, cashierName, datePrint, timePrint, 
                                      billMain, tableRoom);
                          drawPrint += ""
                          + "</div>";


                          PaymentSystem paymentSystem;
                          try{
                              paymentSystem = PstPaymentSystem.fetchExc(oidPaymentSystem);
                          }catch(Exception ex){
                              paymentSystem = new PaymentSystem();
                          }

                          drawPrint+=""
                          + "<div style='height:10cm;'>";

                              drawPrint+=drawPayment(paymentType, displayPaymentSystem, 
                                      paymentSystem, ccName, ccNumber, ccBank, 
                                      ccValid, ccCharge, payAmount, billMain, 
                                      location, printType);
                          drawPrint+=""
                          + "</div>";


                          drawPrint+=""
                          + "<div style='height:"+footerHeight+"'>";
                              //FOOTER CONTENT
                              drawPrint += drawFootrCustom(billMain);
                          drawPrint +=""
                          + "</div>";


                      drawPrint+=""
                      + "</div>"
                  + "</div>";
               }

           }
       }
		    
	    return drawPrint;
     }
	
    public String PrintTemplateGB(String userName, CashCashier cashCashier, BillMainCostum billMainCostum, Location location, CashMaster cashMaster) {
        String drawPrint = "";

        //CHECK SYSTEM PROPERTY :: PAPER TYPE
        String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");

        //CHECK SYSTEM PROPERTY :: ITEM LINE
        String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");

        //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
        String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");

        //CHECK SYSTEM PROPERTY :: PAPER WIDTH
        String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");

        //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
        String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");

        //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
        String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");

        //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
        String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");

        String shoppingBagCategory = PstSystemProperty.getValueByName("CASHIER_SHOPPING_BAG_CATEGORY_ID");

        String paperWidth = "";
        String paperHeight = "";
        String headerHeight = "";
        String footerHeight = "";
        String bodyHeight = "";
        String background = "";

        //WIDTH SETTING
        if (printPaperWidth.equals("1")) {
            paperWidth = "width:9.5cm;padding:0.5cm;";
        } else {
            paperWidth = "width:7.5cm;padding:0.1cm;";
        }

        //HEIGHT SETTING
        if (printPaperHeight.equals("1")) {
            paperHeight = "height:29.7cm;";
        } else {
            paperHeight = "height:auto;";
        }

        //HEADER HEIGHT SETTING
        if (printHeaderHeight.equals("1")) {
            //headerHeight = "5cm";
            headerHeight = "auto";
        } else {
            headerHeight = "auto";
        }

        //FOOTER HEIGHT SETTING
        if (printFooterHeight.equals("1")) {
            //footerHeight = "2.5cm";
            footerHeight = "auto";
        } else {
            footerHeight = "auto";
        }

        if (printPapertType.equals("0")) {
            background = "";
        } else {
            background = "";
            //background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");";
        }
        String paperSetting = "style='" + background + "" + paperWidth + "" + paperHeight + "'";
        //background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");

        String whereCash = ""
                + " PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "= 0 "
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "= 1"
                + " AND CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID] + " = 1";
        String whereCC = ""
                + " PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "= 1 "
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "= 0"
                + " AND CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID] + " = 1";
        String whereDebit = ""
                + " PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "= 0 "
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "= 2"
                + " AND CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID] + " = 1";
        String whereVoucher = ""
                + " PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "= 0 "
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "= 4"
                + " AND CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID] + " = 1";
        String whereRefund = ""
                + " PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "= 0 "
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "= 3"
                + " AND CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID] + " = 1"
				+ " AND CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID]+" <> "
				+ " CPM."+ PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID];

        /* daily report */
        String whereCashCashier = ""
                + PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE] + " BETWEEN '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 00:00:00' "
                + " AND '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 23:59:00' "
                + " AND " + PstCashCashier.fieldNames[PstCashCashier.FLD_CASHMASTER_ID] + "=" + cashMaster.getOID();
        Vector listDailyCashCashier = PstCashCashier.list(0, 0, whereCashCashier, "");
        String inCashCashierDaily = "";
        if (listDailyCashCashier.size() > 0) {
            for (int i = 0; i < listDailyCashCashier.size(); i++) {
                CashCashier cashCashier1 = (CashCashier) listDailyCashCashier.get(i);
                inCashCashierDaily += ",'" + cashCashier1.getOID() + "'";
            }
            if (inCashCashierDaily.length() > 0) {
                inCashCashierDaily = inCashCashierDaily.substring(1);
            }
        }
        
        String whereClauseDaily = ""
                + " CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID] + " IN (" + inCashCashierDaily + ")"
                + " AND ("
                + "("
                + "CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                + ") OR ("
                + "CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                + ") OR ("
                + "CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                + ")"
                + ")"
				+ " AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + " NOT IN (SELECT "+ PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] 
				+ " FROM "+PstBillMain.TBL_CASH_BILL_MAIN+" WHERE "+ PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + " = " + PstBillMain.TYPE_RETUR
				+ " AND "+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" BETWEEN '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd")
				+ " 00:00:00' AND '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 23:59:00')";
        int invoicesPerShiftDaily = PstBillMain.getCountPerCashier(whereClauseDaily);
        
        String whereDaily = ""
                + " CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_PAY_DATETIME] + " BETWEEN '"
                + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 00:00:00' AND '"
                + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 23:59:00'";
        
        String whereDailyReturn = ""
                + " DATE(CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_PAY_DATETIME] + ") = '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + "'"
                + " AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID] + " <> " + cashCashier.getOID();
        
        double cashDaily = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereDaily + " AND " + whereCash);
        double ccDaily = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereDaily + " AND " + whereCC);
        double debitDaily = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereDaily + " AND " + whereDebit);
        double voucherDaily = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereDaily + " AND " + whereVoucher);
        //>>>added by dewok 20190322, for payment type return
        double refundDaily = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereDaily + " AND " + whereRefund);
        //double refundInvoiceDailyOtherOpening = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereDailyReturn + " AND " + whereRefund);
        //double refundReturnDaily = PstCashPayment1.getSumPaymentReturn(whereDaily + " AND " + whereRefund);
        //double realReturnDaily = refundInvoiceDaily + refundInvoiceDailyOtherOpening - refundReturnDaily;
        //<<<
        double totalDaily = cashDaily + ccDaily + debitDaily + refundDaily;
        
        String whereGroupDaily = ""
                + " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " BETWEEN '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd")
                + " 00:00:00' AND '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 23:59:00' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + " != " + shoppingBagCategory
				+ " AND "
						+ " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_DETAIL_ID]+ " NOT IN "
						+ "(SELECT CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_DETAIL_ID]
						+ " FROM "+PstBillDetail.TBL_CASH_BILL_DETAIL +" CD "
						+ " INNER JOIN " + PstBillMain.TBL_CASH_BILL_MAIN+ " CBM "
						+ " ON CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+ " = CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]
						+ " INNER JOIN " + PstBillMain.TBL_CASH_BILL_MAIN+ " cbm1 "
						+ " ON CBM."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+ " = cbm1."+ PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]
						+ " INNER JOIN " + PstBillDetail.TBL_CASH_BILL_DETAIL +" cbd1 "
						+ " ON cbm1."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + " = cbd1."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]
						+ " AND cbd1."+ PstBillDetail.fieldNames[PstBillDetail.FLD_MATERIAL_ID] +" = CD."+ PstBillDetail.fieldNames[PstBillDetail.FLD_MATERIAL_ID]
						+ " WHERE CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]
						+ " BETWEEN '"+ Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd")
						+ " 00:00:00' AND '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 23:59:00' AND cbm1."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"=1"
						+ " AND DATE_FORMAT(cbm1."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+", \"%Y-%m-%d\") = DATE_FORMAT(CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+", \"%Y-%m-%d\"))";				
        Vector listGroupDaily = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupDaily, "CAT.CATEGORY_ID", 0);
        
        String whereGroupDailyShoppingBag = " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " BETWEEN '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd")
                + " 00:00:00' AND '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 23:59:00' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + " = " + shoppingBagCategory;
        Vector listGroupDailyShoppingBag = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupDailyShoppingBag, "PM.NAME", 0);
        
        Vector listSalesDaily = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupDaily, "SLS.SALES_CODE", 0);
        
        Vector listSupplierDaily = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupDaily, "CL.PERSON_NAME", 0);
        
        String whereDaysDaily = PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE] + " BETWEEN '"
                + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 00:00:00' AND '"
                + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 23:59:00'";
        int daysDaily = PstCashCashier.getDayOfSale(whereDaysDaily);
        /* end daily report */

        /* mtd report */
        String whereCashCashierMTD = PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE] + " BETWEEN '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM") + "-01 00:00:00' "
                + " AND '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 23:59:00' "
                + " AND " + PstCashCashier.fieldNames[PstCashCashier.FLD_CASHMASTER_ID] + "=" + cashMaster.getOID();
        Vector listDailyCashCashierMTD = PstCashCashier.list(0, 0, whereCashCashierMTD, "");
        String inCashCashierDailyMTD = "";
        if (listDailyCashCashierMTD.size() > 0) {
            for (int i = 0; i < listDailyCashCashierMTD.size(); i++) {
                CashCashier cashCashier1 = (CashCashier) listDailyCashCashierMTD.get(i);
                inCashCashierDailyMTD += ",'" + cashCashier1.getOID() + "'";
            }
            if (inCashCashierDaily.length() > 0) {
                inCashCashierDailyMTD = inCashCashierDailyMTD.substring(1);
            }
        }
        
        String whereClauseMTD = ""
                + " CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID] + " IN (" + inCashCashierDailyMTD + ")"
                + " AND ("
                + "("
                + "CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                + ") OR ("
                + "CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                + ") OR ("
                + "CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                + ")"
                + ")"
				+ " AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + " NOT IN (SELECT "+ PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] 
				+ " FROM "+PstBillMain.TBL_CASH_BILL_MAIN+" WHERE "+ PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + " = " + PstBillMain.TYPE_RETUR
				+ " AND "+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" BETWEEN '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM")
				+ "-01 00:00:00' AND '" + Formater.formatDate(new Date(), "yyyy-MM-dd") + " 23:59:00')";
        int invoicesMTD = PstBillMain.getCountPerCashier(whereClauseMTD);
        
        String whereMTD = ""
                + " CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_PAY_DATETIME] + " BETWEEN '"
                + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM") + "-01 00:00:00' AND '"
                + Formater.formatDate(new Date(), "yyyy-MM-dd") + " 23:59:00'";
        
        String whereMTDReturn = " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID] + " <> " + cashCashier.getOID();
        
        double cashMTD = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereMTD + " AND " + whereCash);
        double ccMTD = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereMTD + " AND " + whereCC);
        double debitMTD = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereMTD + " AND " + whereDebit);
        double voucherMTD = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereMTD + " AND " + whereVoucher);
        //>>>added by dewok 20190322, for payment type return
        double refundMTD = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereMTD + " AND " + whereRefund);
        //double refundInvoiceMTDOtherOpening = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(whereMTD + " AND " + whereRefund + " AND " + whereMTDReturn);
        //double refundReturnMTD = PstCashPayment1.getSumPaymentReturn(whereMTD + " AND " + whereRefund);
        //double realReturnMTD = refundInvoiceMTD + refundInvoiceMTDOtherOpening - refundReturnMTD;
        //<<<
        double totalMTD = cashMTD + ccMTD + debitMTD + refundMTD;
        
        String whereGroupMTD = " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " BETWEEN '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM")
                + "-01 00:00:00' AND '" + Formater.formatDate(new Date(), "yyyy-MM-dd") + " 23:59:00' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + " != " + shoppingBagCategory
				+ " AND "
				+ " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_DETAIL_ID]+ " NOT IN "
				+ "(SELECT CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_DETAIL_ID]
				+ " FROM "+PstBillDetail.TBL_CASH_BILL_DETAIL +" CD "
				+ " INNER JOIN " + PstBillMain.TBL_CASH_BILL_MAIN+ " CBM "
				+ " ON CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+ " = CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]
				+ " INNER JOIN " + PstBillMain.TBL_CASH_BILL_MAIN+ " cbm1 "
				+ " ON CBM."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+ " = cbm1."+ PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]
				+ " INNER JOIN " + PstBillDetail.TBL_CASH_BILL_DETAIL +" cbd1 "
				+ " ON cbm1."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + " = cbd1."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]
				+ " AND cbd1."+ PstBillDetail.fieldNames[PstBillDetail.FLD_MATERIAL_ID] +" = CD."+ PstBillDetail.fieldNames[PstBillDetail.FLD_MATERIAL_ID]
				+ " WHERE CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]
				+ " BETWEEN '"+ Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM")
				+ "-01 00:00:00' AND '"+ Formater.formatDate(new Date(), "yyyy-MM-dd") + " 23:59:00' AND cbm1."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"=1"
				+ " AND DATE_FORMAT(cbm1."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+", \"%Y-%m-%d\") = DATE_FORMAT(CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+", \"%Y-%m-%d\"))";
        Vector listGroupMTD = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupMTD, "CAT.CATEGORY_ID", 0);
        
        String whereGroupMTDShoppingBag = ""
                + " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " BETWEEN '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM")
                + "-01 00:00:00' AND '" + Formater.formatDate(new Date(), "yyyy-MM-dd") + " 23:59:00' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + " = " + shoppingBagCategory;
        Vector listGroupMTDShoppingBag = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupMTDShoppingBag, "PM.NAME", 0);
        Vector listSalesMTD = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupMTD, "SLS.SALES_CODE", 0);
        Vector listSupplierMTD = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupMTD, "CL.PERSON_NAME", 0);
        
        String whereDaysMTD = ""
                + PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE] + " BETWEEN '"
                + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM") + "-01 00:00:00' AND '"
                + Formater.formatDate(new Date(), "yyyy-MM-dd") + " 23:59:00'";
        int daysMTD = PstCashCashier.getDayOfSale(whereDaysMTD);

        /* end daily report */
        drawPrint = ""
                + "<table style=\"border: 0\">"
                + "<tr><td>*** Daily Sales ***</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td><div>**** SHIFT REPORT ****</div></td></tr>"
                + "";
        if (listDailyCashCashier.size() > 0) {
            for (int i = 0; i < listDailyCashCashier.size(); i++) {
                CashCashier cashCashier1 = (CashCashier) listDailyCashCashier.get(i);
                AppUser appUser = PstAppUser.fetch(cashCashier1.getAppUserId());

                /* per shift */
                String whereClause = ""
                        + " CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID] + "=" + cashCashier1.getOID()
                        + " AND ("
                        + "("
                        + "CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                        + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                        + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                        + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                        + ") OR ("
                        + "CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                        + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                        + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                        + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                        + ") OR ("
                        + "CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                        + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                        + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                        + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                        + ")"
                        + ")"
						+ " AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + " NOT IN (SELECT "+ PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID] 
						+ " FROM "+PstBillMain.TBL_CASH_BILL_MAIN+" WHERE "+ PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + " = " + PstBillMain.TYPE_RETUR
						+ " AND "+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+" BETWEEN '" + Formater.formatDate(cashCashier1.getOpenDate(), "yyyy-MM-dd HH:mm:ss")
                        + "' AND '" + Formater.formatDate(cashCashier1.getCloseDate(), "yyyy-MM-dd HH:mm:ss") + "')";
                int invoicesPerShift = PstBillMain.getCountPerCashier(whereClause);

                String wherePerShift = ""
                        + " CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_PAY_DATETIME] + " BETWEEN '"
                        + Formater.formatDate(cashCashier1.getOpenDate(), "yyyy-MM-dd HH:mm:ss") + "' AND '"
                        + Formater.formatDate(cashCashier1.getCloseDate(), "yyyy-MM-dd HH:mm:ss") + "'";
                
                String wherePerShiftReturn = ""
                        + " DATE(CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_PAY_DATETIME] + ") = '" + Formater.formatDate(cashCashier1.getOpenDate(), "yyyy-MM-dd") + "'"
                        + " AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_CASH_CASHIER_ID] + " <> " + cashCashier.getOID();

                double cashPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift + " AND " + whereCash);
                double ccPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift + " AND " + whereCC);
                double debitPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift + " AND " + whereDebit);
                double voucherPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift + " AND " + whereVoucher);
                //>>>added by dewok 20190322, for payment type return
                double refundPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift + " AND " + whereRefund);
                //double refundInvoicePerShiftOtherOpening = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShiftReturn + " AND " + whereRefund);
                //double refundReturnPerShift = PstCashPayment1.getSumPaymentReturn(wherePerShift + " AND " + whereRefund);
                //double realReturnPerShift = refundInvoicePerShift + refundInvoicePerShiftOtherOpening - refundReturnPerShift;
                //<<<
                double totalPerShift = cashPerShift + ccPerShift + debitPerShift + refundPerShift;
                
                String whereGroupPerShift = ""
                        + " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " BETWEEN '" + Formater.formatDate(cashCashier1.getOpenDate(), "yyyy-MM-dd HH:mm:ss")
                        + "' AND '" + Formater.formatDate(cashCashier1.getCloseDate(), "yyyy-MM-dd HH:mm:ss") + "' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + " != " + shoppingBagCategory
						+ " AND "
						+ " CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_DETAIL_ID]+ " NOT IN "
						+ "(SELECT CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_DETAIL_ID]
						+ " FROM "+PstBillDetail.TBL_CASH_BILL_DETAIL +" CD "
						+ " INNER JOIN " + PstBillMain.TBL_CASH_BILL_MAIN+ " CBM "
						+ " ON CD."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+ " = CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]
						+ " INNER JOIN " + PstBillMain.TBL_CASH_BILL_MAIN+ " cbm1 "
						+ " ON CBM."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID]+ " = cbm1."+ PstBillMain.fieldNames[PstBillMain.FLD_PARENT_ID]
						+ " INNER JOIN " + PstBillDetail.TBL_CASH_BILL_DETAIL +" cbd1 "
						+ " ON cbm1."+PstBillMain.fieldNames[PstBillMain.FLD_BILL_MAIN_ID] + " = cbd1."+PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]
						+ " AND cbd1."+ PstBillDetail.fieldNames[PstBillDetail.FLD_MATERIAL_ID] +" = CD."+ PstBillDetail.fieldNames[PstBillDetail.FLD_MATERIAL_ID]
						+ " WHERE CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]
						+ " BETWEEN '"+Formater.formatDate(cashCashier1.getOpenDate(), "yyyy-MM-dd HH:mm:ss")+"' AND '"+Formater.formatDate(cashCashier1.getCloseDate(), "yyyy-MM-dd HH:mm:ss")+"' AND cbm1."+PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE]+"=1"
						+ " AND DATE_FORMAT(cbm1."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+", \"%Y-%m-%d\") = DATE_FORMAT(CBM."+ PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE]+", \"%Y-%m-%d\"))";
						
				
                Vector listGroupPerShift = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupPerShift, "CAT.CATEGORY_ID", 0);
                
                String whereGroupPerShiftShoppingBag = ""
                        + " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " BETWEEN '" + Formater.formatDate(cashCashier1.getOpenDate(), "yyyy-MM-dd HH:mm:ss")
                        + "' AND '" + Formater.formatDate(cashCashier1.getCloseDate(), "yyyy-MM-dd HH:mm:ss") + "' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + " = " + shoppingBagCategory;
                Vector listGroupPerShiftShoppingBag = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupPerShiftShoppingBag, "PM.NAME", 0);

                /* end per shift */
                drawPrint += ""
                        + "<tr>"
                        + "<td><div>(" + (i + 1) + ") - " + appUser.getFullName() + "</div></td>"
                        + "</tr>"
                        + "<tr><td>Sales Time : " + Formater.formatDate(cashCashier1.getOpenDate(), "kk:mm:ss") + " - " + Formater.formatDate(cashCashier1.getCloseDate() != null ? cashCashier1.getCloseDate() : new Date(), "kk:mm:ss") + "</td></tr>"
                        + "<tr><td>Total Invoices : " + invoicesPerShift + "</td></tr>"
                        + "<tr><td>&nbsp;</td></tr>"
                        + "<tr><td>Payment Summary:</td></tr>"
                        + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                        + "<tr><td>Cash&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(cashPerShift, ".2f") + "</td></tr>"
                        + "<tr><td>Credit Card&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(ccPerShift, ".2f") + "</td></tr>"
                        + "<tr><td>Debit Card&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(debitPerShift, ".2f") + "</td></tr>"
                        + "<tr><td>Voucher&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(voucherPerShift, ".2f") + "</td></tr>"
                        + "<tr><td>Exchange Value&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(refundPerShift, ".2f") + "</td></tr>"
                        + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                        + "<tr><td>Total Payment&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalPerShift, ".2f") + "</td></tr>"
						+ "<tr><td>Total Real Payment&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalPerShift-refundPerShift, ".2f") + "</td></tr>"
                        + "<tr><td>&nbsp;</td></tr>"
                        + "<tr><td>Product Category Summary :</td></tr>"
                        + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                        + "";
                
                double totalGroup = 0;
                double totalQty = 0;
                if (listGroupPerShift.size() > 0) {
                    for (int x = 0; x < listGroupPerShift.size(); x++) {
                        Vector temp = (Vector) listGroupPerShift.get(x);
                        com.dimata.posbo.entity.masterdata.Category category = (com.dimata.posbo.entity.masterdata.Category) temp.get(3);
                        Billdetail billDetail = (Billdetail) temp.get(4);

                        totalGroup = totalGroup + billDetail.getTotalAmount();
                        totalQty = totalQty + billDetail.getQty();
                        drawPrint += ""
                                + "<tr>"
                                + "<td>" + category.getName() + "&nbsp;&nbsp;:&nbsp;&nbsp;(" + String.format("%.0f", billDetail.getQty()) + ")&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;" + Formater.formatNumber(billDetail.getTotalAmount(), ".2f") + "</td>"
                                + "</tr>";
                    }
                }
                drawPrint += ""
                        + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                        + "<tr><td>Total Product Qty&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", totalQty) + "</td></tr>"
                        + "<tr><td>Total Product Amount&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalGroup, ".2f") + "</td></tr>"
                        + "<tr><td>Average Sales&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber((totalGroup / invoicesPerShiftDaily), ".2f") + "</td></tr>"
                        + "<tr><td>&nbsp;</td></tr>"
                        + "<tr><td>Shopping Bag Summary:</td></tr>"
                        + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>";
                totalGroup = 0;
                totalQty = 0;
                if (listGroupPerShiftShoppingBag.size() > 0) {
                    for (int x = 0; x < listGroupPerShiftShoppingBag.size(); x++) {
                        Vector temp = (Vector) listGroupPerShiftShoppingBag.get(x);
                        Material material = (Material) temp.get(6);
                        Billdetail billDetail = (Billdetail) temp.get(4);

                        totalGroup = totalGroup + billDetail.getTotalAmount();
                        totalQty = totalQty + billDetail.getQty();
                        drawPrint += ""
                                + "<tr>"
                                + "<td>" + material.getName() + "&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", billDetail.getQty()) + "</td>"
                                + "</tr>";
                    }
                }
                drawPrint += ""
                        + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                        + "<tr><td>Total Shopping Bag Qty&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", totalQty) + "</td></tr>"
                        + "<tr><td>&nbsp;</td></tr>";

            }
        }

        drawPrint += ""
                + "<tr><td>***********************************************</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td><div>**** DAILY REPORT ****</div></td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>Total Invoices : " + invoicesPerShiftDaily + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>Cash&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(cashDaily, ".2f") + "</td></tr>"
                + "<tr><td>Credit Card&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(ccDaily, ".2f") + "</td></tr>"
                + "<tr><td>Debit Card&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(debitDaily, ".2f") + "</td></tr>"
                + "<tr><td>Voucher&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(voucherDaily, ".2f") + "</td></tr>"
                + "<tr><td>Exchange Value&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(refundDaily, ".2f") + "</td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td>Total Payment&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalDaily, ".2f") + "</td></tr>"
				+ "<tr><td>Total Real Payment&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalDaily-refundDaily, ".2f") + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>Daily Product Category Summary :</td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "";
        double totalGroup = 0;
        double totalQty = 0;
        if (listGroupDaily.size() > 0) {
            for (int i = 0; i < listGroupDaily.size(); i++) {
                Vector temp = (Vector) listGroupDaily.get(i);
                com.dimata.posbo.entity.masterdata.Category category = (com.dimata.posbo.entity.masterdata.Category) temp.get(3);
                Billdetail billDetail = (Billdetail) temp.get(4);

                totalGroup = totalGroup + billDetail.getTotalAmount();
                totalQty = totalQty + billDetail.getQty();
                drawPrint += ""
                        + "<tr>"
                        + "<td>" + category.getName() + "&nbsp;&nbsp;:&nbsp;&nbsp;(" + String.format("%.0f", billDetail.getQty()) + ")&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;" + Formater.formatNumber(billDetail.getTotalAmount(), ".2f") + "</td>"
                        + "</tr>";
            }
        }
        drawPrint += ""
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td>Total Product Qty&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", totalQty) + "</td></tr>"
                + "<tr><td>Total Product Amount&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalGroup, ".2f") + "</td></tr>"
                + "<tr><td>Average Daily Sales&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber((totalGroup / invoicesPerShiftDaily), ".2f") + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>Shopping Bag Summary:</td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>";
        totalGroup = 0;
        totalQty = 0;
        if (listGroupDailyShoppingBag.size() > 0) {
            for (int i = 0; i < listGroupDailyShoppingBag.size(); i++) {
                Vector temp = (Vector) listGroupDailyShoppingBag.get(i);
                Material material = (Material) temp.get(6);
                Billdetail billDetail = (Billdetail) temp.get(4);

                totalGroup = totalGroup + billDetail.getTotalAmount();
                totalQty = totalQty + billDetail.getQty();
                drawPrint += ""
                        + "<tr>"
                        + "<td>" + material.getName() + "&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", billDetail.getQty()) + "</td>"
                        + "</tr>";
            }
        }
        drawPrint += ""
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td>Total Daily Shopping Bag Qty&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", totalQty) + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>Daily Sales Person Summary:</td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>";
        totalGroup = 0;
        totalQty = 0;
        if (listSalesDaily.size() > 0) {
            for (int i = 0; i < listSalesDaily.size(); i++) {
                Vector temp = (Vector) listSalesDaily.get(i);
                Sales sales = (Sales) temp.get(1);
                Billdetail billDetail = (Billdetail) temp.get(4);

                totalGroup = totalGroup + billDetail.getTotalAmount();
                totalQty = totalQty + billDetail.getQty();
                drawPrint += ""
                        + "<tr>"
                        + "<td>" + sales.getName() + "&nbsp;&nbsp;:&nbsp;&nbsp;(" + String.format("%.0f", billDetail.getQty()) + ")&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;" + Formater.formatNumber(billDetail.getTotalAmount(), ".2f") + "</td>"
                        + "</tr>";
            }
        }
        drawPrint += ""
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td>Total Sales Person Qty&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", totalQty) + "</td></tr>"
                + "<tr><td>Total Sales Person&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalGroup, ".2f") + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>Daily Sales Time Summary :</td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td> Time / Inv# / Qty / Total</td></tr>";
        for (int i = 0; i <= 24; i++) {
            String whereInterval = " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " BETWEEN '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd")
                    + " " + (i < 10 ? "0" + i : i) + ":00:00' AND '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " " + (i + 1 < 10 ? "0" + (i + 1) : i + 1) + ":00:00' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + " != " + shoppingBagCategory;
            Vector listInterval = PstBillDetail.listBillDetailByGroup(0, 0, whereInterval, "CBM.CASH_BILL_MAIN_ID", 0);
            if (listInterval.size() > 0) {
                totalGroup = 0;
                totalQty = 0;
                double totalInv = 0;
                for (int x = 0; x < listInterval.size(); x++) {
                    Vector temp = (Vector) listInterval.get(x);
                    Billdetail billDetail = (Billdetail) temp.get(4);
                    totalGroup = totalGroup + billDetail.getTotalAmount();
                    totalQty = totalQty + billDetail.getQty();
                    totalInv += 1;
                }
                drawPrint += ""
                        + "<tr>"
                        + "<td>" + (i < 10 ? "0" + i : i) + ":00 - " + (i + 1 < 10 ? "0" + (i + 1) : i + 1) + ":00" + " :&nbsp;&nbsp;" + String.format("%.0f", totalInv) + "&nbsp;&nbsp;/&nbsp;&nbsp;" + String.format("%.0f", totalQty) + "&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;" + Formater.formatNumber(totalGroup, ".2f") + "</td>"
                        + "</tr>";
            }
        }
        drawPrint += ""
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>Daily Sales Supplier Summary :</td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>";
        totalGroup = 0;
        totalQty = 0;
        if (listSupplierDaily.size() > 0) {
            for (int i = 0; i < listSupplierDaily.size(); i++) {
                Vector temp = (Vector) listSupplierDaily.get(i);
                ContactList contact = (ContactList) temp.get(7);
                Billdetail billDetail = (Billdetail) temp.get(4);

                totalGroup = totalGroup + billDetail.getTotalAmount();
                totalQty = totalQty + billDetail.getQty();
                drawPrint += ""
                        + "<tr>"
                        + "<td>" + contact.getPersonName() + "&nbsp;&nbsp;:&nbsp;&nbsp;(" + String.format("%.0f", billDetail.getQty()) + ")&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;" + Formater.formatNumber(billDetail.getTotalAmount(), ".2f") + "</td>"
                        + "</tr>";
            }
        }
        drawPrint += ""
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td>Total Supplier Qty&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", totalQty) + "</td></tr>"
                + "<tr><td>Total Supplier Amount&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalGroup, ".2f") + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>***********************************************</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td><div>**** MONTH TO DATE REPORT ****</div></td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>MTD Invoices : " + invoicesMTD + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>Cash&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(cashMTD, ".2f") + "</td></tr>"
                + "<tr><td>Credit Card&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(ccMTD, ".2f") + "</td></tr>"
                + "<tr><td>Debit Card&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(debitMTD, ".2f") + "</td></tr>"
                + "<tr><td>Voucher&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(voucherMTD, ".2f") + "</td></tr>"
                + "<tr><td>Exchange Value&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(refundMTD, ".2f") + "</td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td>Total MTD Payment&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalMTD, ".2f") + "</td></tr>"
				+ "<tr><td>Total Real MTD Payment&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalMTD-refundMTD, ".2f") + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>MTD Product Category Summary :</td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>";
        totalGroup = 0;
        totalQty = 0;
        if (listGroupMTD.size() > 0) {
            for (int i = 0; i < listGroupMTD.size(); i++) {
                Vector temp = (Vector) listGroupMTD.get(i);
                com.dimata.posbo.entity.masterdata.Category category = (com.dimata.posbo.entity.masterdata.Category) temp.get(3);
                Billdetail billDetail = (Billdetail) temp.get(4);

                totalGroup = totalGroup + billDetail.getTotalAmount();
                totalQty = totalQty + billDetail.getQty();
                drawPrint += ""
                        + "<tr>"
                        + "<td>" + category.getName() + "&nbsp;&nbsp;:&nbsp;&nbsp;(" + String.format("%.0f", billDetail.getQty()) + ")&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;" + Formater.formatNumber(billDetail.getTotalAmount(), ".2f") + "</td>"
                        + "</tr>";
            }
        }
        drawPrint += ""
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td>MTD Product Qty&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", totalQty) + "</td></tr>"
                + "<tr><td>MTD Product Amount&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalGroup, ".2f") + "</td></tr>"
                + "<tr><td>Average MTD Sales&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber((totalGroup / daysMTD), ".2f") + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>MTD Shopping Bag Summary:</td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>";
        totalGroup = 0;
        totalQty = 0;
        if (listGroupMTDShoppingBag.size() > 0) {
            for (int i = 0; i < listGroupMTDShoppingBag.size(); i++) {
                Vector temp = (Vector) listGroupMTDShoppingBag.get(i);
                Material material = (Material) temp.get(6);
                Billdetail billDetail = (Billdetail) temp.get(4);

                totalGroup = totalGroup + billDetail.getTotalAmount();
                totalQty = totalQty + billDetail.getQty();
                drawPrint += ""
                        + "<tr>"
                        + "<td>" + material.getName() + "&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", billDetail.getQty()) + "</td>"
                        + "</tr>";
            }
        }
        drawPrint += ""
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td>Total MTD Shopping Bag Qty&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", totalQty) + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>MTD Sales Person Summary:</td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>";
        totalGroup = 0;
        totalQty = 0;
        if (listSalesMTD.size() > 0) {
            for (int i = 0; i < listSalesMTD.size(); i++) {
                Vector temp = (Vector) listSalesMTD.get(i);
                Sales sales = (Sales) temp.get(1);
                Billdetail billDetail = (Billdetail) temp.get(4);

                totalGroup = totalGroup + billDetail.getTotalAmount();
                totalQty = totalQty + billDetail.getQty();
                drawPrint += ""
                        + "<tr>"
                        + "<td>" + sales.getName() + "&nbsp;&nbsp;:&nbsp;&nbsp;(" + String.format("%.0f", billDetail.getQty()) + ")&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;" + Formater.formatNumber(billDetail.getTotalAmount(), ".2f") + "</td>"
                        + "</tr>";
            }
        }
        drawPrint += ""
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td>MTD Sales Person Qty&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", totalQty) + "</td></tr>"
                + "<tr><td>MTD Sales Person&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalGroup, ".2f") + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>MTD Sales Time Summary :</td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td> Time / Inv# / Qty / Total</td></tr>";
        for (int i = 0; i <= 24; i++) {
            String whereInterval = ""
                    + " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " BETWEEN '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM")
                    + "-01 00:00:00' AND '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd") + " 23:59:00'"
                    + " AND DATE_FORMAT(CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + ",'%H:%i:%s') BETWEEN '" + (i < 10 ? "0" + i : i) + ":00' AND '" + (i + 1 < 10 ? "0" + (i + 1) : i + 1) + ":00'"
                    + "AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + " != " + shoppingBagCategory;
            Vector listIntervalMTD = PstBillDetail.listBillDetailByGroup(0, 0, whereInterval, "CBM.CASH_BILL_MAIN_ID", 0);
            if (listIntervalMTD.size() > 0) {
                totalGroup = 0;
                totalQty = 0;
                double totalInv = 0;
                for (int x = 0; x < listIntervalMTD.size(); x++) {
                    Vector temp = (Vector) listIntervalMTD.get(x);
                    Billdetail billDetail = (Billdetail) temp.get(4);
                    totalGroup = totalGroup + billDetail.getTotalAmount();
                    totalQty = totalQty + billDetail.getQty();
                    totalInv += 1;
                }
                drawPrint += ""
                        + "<tr>"
                        + "<td>" + (i < 10 ? "0" + i : i) + ":00 - " + (i + 1 < 10 ? "0" + (i + 1) : i + 1) + ":00" + " :&nbsp;&nbsp;" + String.format("%.0f", totalInv) + "&nbsp;&nbsp;/&nbsp;&nbsp;" + String.format("%.0f", totalQty) + "&nbsp;&nbsp;/&nbsp;&nbsp;Rp.&nbsp;" + Formater.formatNumber(totalGroup, ".2f") + "</td>"
                        + "</tr>";
            }
        }
        drawPrint += ""
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>MTD Sales Supplier Summary :</td></tr>"
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>";
        totalGroup = 0;
        totalQty = 0;
        if (listSupplierMTD.size() > 0) {
            for (int i = 0; i < listSupplierMTD.size(); i++) {
                Vector temp = (Vector) listSupplierMTD.get(i);
                ContactList contact = (ContactList) temp.get(7);
                Billdetail billDetail = (Billdetail) temp.get(4);

                totalGroup = totalGroup + billDetail.getTotalAmount();
                totalQty = totalQty + billDetail.getQty();
                drawPrint += ""
                        + "<tr>"
                        + "<td>" + contact.getPersonName() + "&nbsp;&nbsp;:&nbsp;&nbsp;(" + String.format("%.0f", billDetail.getQty()) + ")&nbsp;&nbsp;-&nbsp;&nbsp;Rp.&nbsp;" + Formater.formatNumber(billDetail.getTotalAmount(), ".2f") + "</td>"
                        + "</tr>";
            }
        }
        drawPrint += ""
                + "<tr><td>= = = = = = = = = = = = = = = = = =</td></tr>"
                + "<tr><td>MTD Supplier Qty&nbsp;&nbsp;:&nbsp;&nbsp;" + String.format("%.0f", totalQty) + "</td></tr>"
                + "<tr><td>MTD Supplier Amount&nbsp;&nbsp;:&nbsp;&nbsp;" + Formater.formatNumber(totalGroup, ".2f") + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>Extra Messages:</td></tr>"
                + "<tr><td>" + cashCashier.getCloseNote() + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>Reported By&nbsp;&nbsp;&nbsp;:&nbsp;" + userName + "</td></tr>"
                + "<tr><td>&nbsp;</td></tr>"
                + "<tr><td>Thank You & Best Regards</td></tr>"
                + "<tr><td>" + userName + "</td></tr>"
                + "<tr><td>" + location.getName() + "</td></tr>"
                + "</table>";
        return drawPrint;
    }

    public String PrintTemplateGBClosing(String userName, CashCashier cashCashier, BillMainCostum billMainCostum, Location location, CashMaster cashMaster) {
        String drawPrint = "";

        //CHECK SYSTEM PROPERTY :: PAPER TYPE
        String printPapertType = PstSystemProperty.getValueByName("PRINT_PAPER_TYPE_CASHIER");

        //CHECK SYSTEM PROPERTY :: ITEM LINE
        String printItemLine = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_LINE");

        //CHECK SYSTEM PROPERTY :: CATEGORY ITEM
        String printCategoryItem = PstSystemProperty.getValueByName("PRINT_PAPER_ITEM_CATEGORY");

        //CHECK SYSTEM PROPERTY :: PAPER WIDTH
        String printPaperWidth = PstSystemProperty.getValueByName("PRINT_PAPER_WIDTH");

        //CHECK SYSTEM PROPERTY :: HEADER HEIGHT
        String printHeaderHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_HEADER");

        //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
        String printFooterHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT_FOOTER");

        //CHECK SYSTEM PROPERTY :: FOOTER HEIGHT
        String printPaperHeight = PstSystemProperty.getValueByName("PRINT_PAPER_HEIGHT");

        String shoppingBagCategory = PstSystemProperty.getValueByName("CASHIER_SHOPPING_BAG_CATEGORY_ID");

        String paperWidth = "";
        String paperHeight = "";
        String headerHeight = "";
        String footerHeight = "";
        String bodyHeight = "";
        String background = "";

        //WIDTH SETTING
        if (printPaperWidth.equals("1")) {
            paperWidth = "width:9.5cm;padding:0.5cm;";
        } else {
            paperWidth = "width:7.5cm;padding:0.1cm;";
        }

        //HEIGHT SETTING
        if (printPaperHeight.equals("1")) {
            paperHeight = "height:29.7cm;";
        } else {
            paperHeight = "height:auto;";
        }

        //HEADER HEIGHT SETTING
        if (printHeaderHeight.equals("1")) {
            //headerHeight = "5cm";
            headerHeight = "auto";
        } else {
            headerHeight = "auto";
        }

        //FOOTER HEIGHT SETTING
        if (printFooterHeight.equals("1")) {
            //footerHeight = "2.5cm";
            footerHeight = "auto";
        } else {
            footerHeight = "auto";
        }

        if (printPapertType.equals("0")) {
            background = "";
        } else {
            background = "";
            //background = "backround-repeat:no-repeat;background-size:9.5cm;18cm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");";
        }
        String paperSetting = "style='" + background + "" + paperWidth + "" + paperHeight + "'";
        //background-size:9.5cm;18xm;background-image:url(\"http://localhost:8080/D-Cashier-V1-2015New/styles/cashier/img/bill.jpg\");

        String whereCash = "PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "= 0 "
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "= 1"
                + " AND CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID] + " = 1";
        String whereCC = "PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "= 1 "
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "= 0"
                + " AND CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID] + " = 1";
        String whereDebit = "PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "= 0 "
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "= 2"
                + " AND CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID] + " = 1";
        String whereVoucher = "PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_BANK_INFO_OUT] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CARD_INFO] + "= 0 "
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_CHECK_BG_INFO] + "= 0"
                + " AND PS." + PstPaymentSystem.fieldNames[PstPaymentSystem.FLD_PAYMENT_TYPE] + "= 4"
                + " AND CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_CURRENCY_ID] + " = 1";

        String whereClause = "CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_CASH_CASHIER_ID] + "=" + cashCashier.getOID()
                + " AND ("
                + "("
                + "CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                + ") OR ("
                + "CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                + ") OR ("
                + "CBM." + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='0' "
                + "AND CBM." + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                + ")"
                + ")";
        int invoicesPerShift = PstBillMain.getCountPerCashier(whereClause);

        String wherePerShift = "CP." + PstCashPayment1.fieldNames[PstCashPayment1.FLD_PAY_DATETIME] + " BETWEEN '"
                + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd HH:mm:ss") + "' AND '"
                + Formater.formatDate(cashCashier.getCloseDate(), "yyyy-MM-dd HH:mm:ss") + "'";
        double cashPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift + " AND " + whereCash);
        double ccPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift + " AND " + whereCC);
        double debitPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift + " AND " + whereDebit);
        double voucherPerShift = PstCashPayment1.getSumSystemPaymentWithReturnAndBillStatus(wherePerShift + " AND " + whereVoucher);
        double totalPerShift = cashPerShift + ccPerShift + debitPerShift;

        String whereGroupPerShift = " CBM." + PstBillMain.fieldNames[PstBillMain.FLD_BILL_DATE] + " BETWEEN '" + Formater.formatDate(cashCashier.getOpenDate(), "yyyy-MM-dd HH:mm:ss")
                + "' AND '" + Formater.formatDate(cashCashier.getCloseDate(), "yyyy-MM-dd HH:mm:ss") + "' AND CAT." + PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID] + " != " + shoppingBagCategory;
        Vector listGroupPerShift = PstBillDetail.listBillDetailByGroup(0, 0, whereGroupPerShift, "CAT.CATEGORY_ID", 0);
        double totalQty = 0;
        if (listGroupPerShift.size() > 0) {
            for (int x = 0; x < listGroupPerShift.size(); x++) {
                Vector temp = (Vector) listGroupPerShift.get(x);
                com.dimata.posbo.entity.masterdata.Category category = (com.dimata.posbo.entity.masterdata.Category) temp.get(3);
                Billdetail billDetail = (Billdetail) temp.get(4);

                totalQty = totalQty + billDetail.getQty();
            }
        }
        /* end daily report */
        String cashier = "";
        AppUser appUserCashier = PstAppUser.fetch(cashCashier.getAppUserId());
        drawPrint = ""
                + "<table style=\"border: 0; border-collapse: collapse;\" cellspacing='1' cellpadding='1'>"
                + "     <tr>"
                + "         <td colspan='3' style='text-align:center'><b>" + location.getName() + "</b></td>"
                + "     </tr>"
                + "     <tr><td colspan='3'>&nbsp;</td></tr>"
                + "     <tr><td colspan='3'>&nbsp;</td></tr>"
                + "     <tr>"
                + "         <td colspan='3' style='text-align:center'><div><b><u>CLOSING SUMMARY</u></b></div></td>"
                + "     </tr>"
                + "     <tr>"
                + "         <td style='text-align:left'>Date</td>"
                + "         <td style='text-align:left' colspan='2'>: " + Formater.formatDate(cashCashier.getCloseDate(), "dd-MMM-yyyy") + "</td>"
                + "     </tr>"
                + "     <tr>"
                + "         <td style='text-align:left'>Cashier</td>"
                + "         <td style='text-align:left' colspan='2'>: " + appUserCashier.getFullName() + "</td>"
                + "     </tr>"
                + "     <tr style=\"border-bottom: 1px solid #000000;\">"
                + "         <td style='text-align:left'>Print Date / Time</td>"
                + "         <td style='text-align:left' colspan='2'>: " + Formater.formatDate(cashCashier.getCloseDate(), "dd-MMM-yyyy HH:mm") + "</td>"
                + "     </tr>"
                + "     <tr>"
                + "         <td style='text-align:left'>Cash</td>"
                + "         <td style='text-align:left'>:</td>"
                + "         <td style='text-align:right'>" + Formater.formatNumber(cashPerShift, ".2f") + "</td>"
                + "     </tr>"
                + "     <tr>"
                + "         <td style='text-align:left'>Credit Card</td>"
                + "         <td style='text-align:left'>:</td>"
                + "         <td style='text-align:right'>" + Formater.formatNumber(ccPerShift, ".2f") + "</td>"
                + "     </tr>"
                + "     <tr>"
                + "         <td style='text-align:left'>Debit Card</td>"
                + "         <td style='text-align:left'>:</td>"
                + "         <td style='text-align:right'>" + Formater.formatNumber(debitPerShift, ".2f") + "</td>"
                + "     </tr>"
                + "     <tr style=\"border-bottom: 1px solid #000000;\">"
                + "         <td style='text-align:left'>Voucher</td>"
                + "         <td style='text-align:left'>:</td>"
                + "         <td style='text-align:right'>" + Formater.formatNumber(voucherPerShift, ".2f") + "</td>"
                + "     </tr>"
                + "     <tr>"
                + "         <td style='text-align:left'>Grand Total</td>"
                + "         <td style='text-align:left'>:</td>"
                + "         <td style='text-align:right'>" + Formater.formatNumber(totalPerShift, ".2f") + "</td>"
                + "     </tr>"
                + "     <tr>"
                + "         <td style='text-align:left'>Invoice#</td>"
                + "         <td style='text-align:left'>:</td>"
                + "         <td style='text-align:right'>" + invoicesPerShift + "</td>"
                + "     </tr>"
                + "     <tr>"
                + "         <td style='text-align:left'>Total Qty</td>"
                + "         <td style='text-align:left'>:</td>"
                + "         <td style='text-align:right'>" + Formater.formatNumber(totalQty, ".0f") + "</td>"
                + "     </tr>"
                + "     <tr><td colspan='3'>&nbsp;</td></tr>"
                + "     <tr>"
                + "         <td style='text-align:center' colspan='3'><b>Reported By:</b></td>"
                + "     </tr>"
                + "     <tr><td colspan='3'>&nbsp;</td></tr>"
                + "     <tr><td colspan='3'>&nbsp;</td></tr>"
                + "     <tr><td colspan='3'>&nbsp;</td></tr>"
                + "     <tr>"
                + "         <td style='text-align:center' colspan='3'><b>( <u>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                + "             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</u> )</b>"
                + "         </td>"
                + "     </tr>"
                + "</table>";
        return drawPrint;
    }

}

