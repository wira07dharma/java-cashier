

/* Created on 	:  [date] [time] AM/PM

 *

 * @author  	:  [authorName]

 * @version  	:  [version]

 */



/*******************************************************************

 * Class Description 	: [project description ... ]

 * Imput Parameters 	: [input parameter ...]

 * Output 		: [output ...]

 *******************************************************************/



package com.dimata.cashierweb.entity.cashier.transaction;



/* package java */
import java.io.*;

import java.sql.*;
import java.util.*;

import java.util.Date;



/* package qdep */

import com.dimata.util.lang.I_Language;

import com.dimata.common.db.*;

import com.dimata.qdep.entity.*;

import com.dimata.util.*;



/* package hanoman */

//import com.dimata.qdep.db.DBHandler;

//import com.dimata.qdep.db.DBException;

//import com.dimata.qdep.db.DBLogger;

import com.dimata.hanoman.entity.masterdata.*;

import com.dimata.harisma.entity.masterdata.*;
import com.dimata.qdep.form.FRMMessage;





public class PstReservation extends  DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {



    //public static final  String TBL_RESERVATION = "reservation";

    public static final  String TBL_RESERVATION = "reservation";



    public static final  int FLD_RESERVATION_ID = 0;

    public static final  int FLD_TRAVEL_AGENT_ID = 1;

    public static final  int FLD_MEMBERSHIP_ID = 2;

    public static final  int FLD_ROOM_CLASS_ID = 3;

    public static final  int FLD_HOTEL_ROOM_ID = 4;

    public static final  int FLD_CUSTOMER_ID = 5;

    public static final  int FLD_SOURCE_OF_BOOKING = 6;

    public static final  int FLD_SOURCE_OF_BOOKING_SPEC = 7;

    public static final  int FLD_CHECK_IN_DATE = 8;

    public static final  int FLD_CHEK_OUT_DATE = 9;

    public static final  int FLD_NUMBER_OF_NIGHT = 10;

    public static final  int FLD_NUM_ADULT = 11;

    public static final  int FLD_NUM_CHILD = 12;

    public static final  int FLD_NUM_INFANT = 13;

    public static final  int FLD_SPECIAL_REQUIREMENT = 14;

    public static final  int FLD_DISCOUNT_RP = 15;

    public static final  int FLD_DISCOUNT_$ = 16;

    public static final  int FLD_DEPOSIT_RP = 17;

    public static final  int FLD_DEPOSIT_$ = 18;

    public static final  int FLD_RESERVATION_STATUS = 19;

    public static final  int FLD_CANCEL_REASON = 20;

    public static final  int FLD_EXTRA_BED = 21;

    public static final  int FLD_ADDITIONAL_CHARGE_TYPE = 22;

    public static final  int FLD_ROOM_RATE_RP = 23;

    public static final  int FLD_SURCHARGE_RP = 24;

    public static final  int FLD_COMPULSORY_RP = 25;

    public static final  int FLD_EXTRA_BED_RATE_RP = 26;

    public static final  int FLD_ADDITIONAL_CHARGE_RP = 27;

    public static final  int FLD_TOTAL_ALL_RATE_RP = 28;

    public static final  int FLD_ROOM_RATE_$ = 29;

    public static final  int FLD_SURCHARGE_$ = 30;

    public static final  int FLD_COMPULSORY_$ = 31;

    public static final  int FLD_EXTRA_BED_RATE_$ = 32;

    public static final  int FLD_ADDITIONAL_CHARGE_$ = 33;

    public static final  int FLD_TOTAL_ALL_RATE_$ = 34;

    public static final  int FLD_INCOME_RATE_RP = 35;

    public static final  int FLD_TAX_RP = 36;

    public static final  int FLD_SERVICE_RP = 37;

    public static final  int FLD_INCOME_RATE_$ = 38;

    public static final  int FLD_TAX_$ = 39;

    public static final  int FLD_SERVICE_$ = 40;

    public static final  int FLD_PAID_RP = 41;

    public static final  int FLD_PAID_$ = 42;

    public static final  int FLD_INCOME_CALCULATION_TYPE = 43;

    public static final  int FLD_EXCHANGE_RATE = 44;

    public static final  int FLD_NOTE = 45;

    public static final  int FLD_CHARGE_CURRENCY = 46;

    public static final  int FLD_PARENT_ID = 47;

    public static final  int FLD_CUSTOMER_TYPE = 48;

    public static final  int FLD_MEMBERSHIP_UPGRADE_ID = 49;

    public static final  int FLD_MEMBERSHIP_CUSTOMER_ID = 50;

    public static final  int FLD_CIN = 51;

    public static final  int FLD_EXCHANGE_RCI_ID = 52;

    public static final  int FLD_CANCEL_FEE_RP = 53;

    public static final  int FLD_CANCEL_FEE_USD = 54;

    public static final  int FLD_TAKE_OVER_FROM_TRAVEL = 55;

    public static final  int FLD_RESV_CLASSIFICATION = 56;



    public static final  int FLD_BILLING_BREAKFAST_ID 			= 57;

    public static final  int FLD_INCLUDE_BREAKFAST 		= 58;

    public static final  int FLD_BREAKFAST_PRICE_RP 			= 59;

    public static final  int FLD_BREAKFAST_PRICE_USD 			= 60;

    public static final  int FLD_WITH_COMMISION 			= 61;

    public static final  int FLD_COMMISION_AMOUNT_RP 			= 62;

    public static final  int FLD_COMMISION_AMOUNT_USD 			= 63;

    public static final  int FLD_COMMISION_CONTACT_ID 			= 64;

    public static final  int FLD_COMMISION_STATUS 			= 65;

    public static final  int FLD_INV_NUMBER 			= 66;

    public static final  int FLD_ROOM_CHARGE_PER_NIGHT_RP 			= 67;

    public static final  int FLD_ROOM_CHARGE_PER_NIGHT_USD			= 68;

    public static final  int FLD_BRF_CHARGE_INCLUDE_IN_ROOM 			= 69;

    public static final  int FLD_USE_TRAVEL_CONTRACT			= 70;



    public static final  int FLD_RSV_TYPE			= 71;

    public static final  int FLD_RSV_GROUP_TYPE			= 72;

    public static final  int FLD_RSV_PARENT_GROUP_ID		= 73;



    public static final int FLD_TRAVEL_PACKAGE_ID 		= 74;

    public static final int FLD_TRAVEL_PACK_TYPE_ID		= 75;

    public static final int FLD_RESERVATION_NUM			= 76;



    public static final int FLD_CONTRACT_ID				= 77;

    public static final int FLD_DISCOUNT_TYPE			= 78;

    public static final int FLD_DISCOUNT_PROCENT		= 79;

    public static final int FLD_DISCOUNT_SCOPE			= 80;

    public static final int FLD_TAX_INCLUDE				= 81;

    public static final int FLD_SERVICE_INCLUDE			= 82;

    public static final int FLD_NUM_NIGHT_DISCOUNT		= 83;

    public static final int FLD_TOTAL_DISCOUNT_RP		= 84;

    public static final int FLD_TOTAL_DISCOUNT_USD		= 85;

    public static final int FLD_NUMBER_COUNTER			= 86;



    public static final int FLD_SURCHARGE_INCLUDE			= 87;

    public static final int FLD_COMPULSORY_INCLUDE			= 88;

    public static final int FLD_NUM_NIGHT_COMPLIMENT			= 89;

    public static final int FLD_COMPLIMENT_TYPE			= 90;

    public static final int FLD_EXCHANGE_TYPE			= 91;

    public static final int FLD_TYPE_INVOICE			= 92;

    public static final int FLD_REG_DATE				= 93;

    public static final int FLD_IS_TRANSFERED               = 94;

   

    public static final  int FLD_CUSTOM_TYPE = 95;

    public static final  int FLD_CUSTOM_TYPE_SPEC = 96;

    //edited by Yuny

    public static final  int FLD_NUMBER_NIGHT_REPORTED = 97;

    public static final  int FLD_CHECKOUT_DATE_REPORTED = 98;

    public static final  int FLD_STATUS_REPORTED = 99;

    

    //update ayu
    public static final  int FLD_CHECKIN_TIME = 100;  
    public static final  int FLD_CHECKOUT_TIME = 101;
    public static final int FLD_GUEST_NAME =102;
    //update by kartika
    public static final int FLD_CANCEL_DATE =103;
    public static final int FLD_CANCEL_INPUT_ON =104;
    public static final int FLD_STATUS_DOC =105;
    public static final int FLD_BOOKING_CODE =106;

    //update opie-eyek 20130603
    public static final int FLD_TERM_OF_PAYMENT          =  107 ;
    public static final int FLD_GUARANTED_BY          =  108 ;
    public static final int FLD_STATUS_CUT_OF_DAYS          =  109 ;
    public static final int FLD_TYPE_OF_BOOKING          =  110 ;
    
    public static final  String[] fieldNames = {
        "RESERVATION_ID",
        "TRAVEL_AGENT_ID",
        "MEMBERSHIP_ID",
        "ROOM_CLASS_ID",
        "HOTEL_ROOM_ID",
        "CONTACT_ID",
        "SOURCE_OF_BOOKING",
        "SOURCE_OF_BOOKING_SPEC",
        "CHECK_IN_DATE",
        "CHEK_OUT_DATE",
        "NUMBER_OF_NIGHT",
        "NUM_ADULT",
        "NUM_CHILD",
        "NUM_INFANT",
        "SPECIAL_REQUIREMENT",//14
        "DISCOUNT_RP",
        "DISCOUNT_$",
        "DEPOSIT_RP",
        "DEPOSIT_$",
        "RESERVATION_STATUS",
        "CANCEL_REASON", //20
        "EXTRA_BED",
        "ADDITIONAL_CHARGE_TYPE",
        "ROOM_RATE_RP",
        "SURCHARGE_RP",
        "COMPULSORY_RP",     //25
        "EXTRA_BED_RATE_RP",
        "ADDITIONAL_CHARGE_RP",
        "TOTAL_ALL_RATE_RP",
        "ROOM_RATE_$",
        "SURCHARGE_$",           //30
        "COMPULSORY_$",
        "EXTRA_BED_RATE_$",
        "ADDITIONAL_CHARGE_$",
        "TOTAL_ALL_RATE_$",
        "INCOME_RATE_RP",          //35
        "TAX_RP",
        "SERVICE_RP",
        "INCOME_RATE_$",
        "TAX_$",
        "SERVICE_$",            //40
        "PAID_RP",
        "PAID_$",
        "INCOME_CALCULATION_TYPE",	//43
        "EXCHANGE_RATE",
        "NOTE",                  //45
        "CHARGE_CURRENCY",

        "PARENT_ID",          //47

        "CUSTOMER_TYPE",

        "MEMBERSHIP_UPGRADE_ID",  //49

        "MEMBERSHIP_CUSTOMER_ID",

        "CIN",                    //51

        "EXCHANGE_RCI_ID",

        "CANCEL_FEE_RP",              // 53

        "CANCEL_FEE_USD",

        "TAKE_OVER_FROM_TRAVEL",   //55

        "RESV_CLASSIFICATION",



        "BILLING_BREAKFAST_ID",

        "INCLUDE_BREAKFAST",

        "BREAKFAST_PRICE_RP",

        "BREAKFAST_PRICE_USD",

        "WITH_COMMISION",

        "COMMISION_AMOUNT_RP",

        "COMMISION_AMOUNT_USD",

        "COMMISION_CONTACT_ID",

        "COMMISION_STATUS",

        "INV_NUMBER",

        "ROOM_CHARGE_PER_NIGHT_RP",

        "ROOM_CHARGE_PER_NIGHT_USD",

        "BRF_CHARGE_INCLUDE_IN_ROOM",

        "USE_TRAVEL_CONTRACT",



        "RSV_TYPE",

        "RSV_GROUP_TYPE",

        "RSV_PARENT_GROUP_ID",



        "TRAVEL_PACKAGE_ID",

    	"TRAVEL_PACK_TYPE_ID",

        "RESERVATION_NUM",



        "CONTRACT_ID",

        "DISCOUNT_TYPE",

        "DISCOUNT_PROCENT",

        "DISCOUNT_SCOPE",

        "TAX_INCLUDE",

        "SERVICE_INCLUDE",

        "NUM_NIGHT_DISCOUNT",

        "TOTAL_DISCOUNT_RP",

        "TOTAL_DISCOUNT_USD",

        "NUMBER_COUNTER",



        "SURCHARGE_INCLUDE",

        "COMPULSORY_INCLUDE",

        "NUM_NIGHT_COMPLIMENT",

        "COMPLIMENT_TYPE",

        "EXCHANGE_TYPE",

        "TYPE_INVOICE",

        "REG_DATE",

        "IS_TRANSFERED",

        "CUSTOM_TYPE",

        "CUSTOM_TYPE_SPEC",

        "NUMBER_NIGHT_REPORTED",

        "CHECKOUT_DATE_REPORTED",

        "STATUS_REPORTED",

        "CHECKIN_TIME",

        "CHECKOUT_TIME",
        "GUEST_NAME",
        "CANCEL_DATE",
        "CANCEL_INPUT_ON",
        "STATUS_DOC",
        "BOOKING_CODE",
        //update opie-eyek
        "TERMS_OF_PAYMENT",
        "GUARANTED_BY",
        "STATUS_CUT_OF_DAYS",
        "TYPE_OF_BOOKING"
    };



    public static final  int[] fieldTypes = {

        TYPE_LONG + TYPE_PK + TYPE_ID,

        TYPE_LONG + TYPE_FK,

        TYPE_LONG + TYPE_FK,

        TYPE_LONG + TYPE_FK,

        TYPE_LONG + TYPE_FK,

        TYPE_LONG + TYPE_FK,

        TYPE_LONG,

        TYPE_STRING,

        TYPE_DATE,

        TYPE_DATE,

        TYPE_INT,

        TYPE_INT,

        TYPE_INT,

        TYPE_INT,

        TYPE_STRING,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_INT,

        TYPE_STRING,

        TYPE_BOOL,

        TYPE_INT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_INT,

        TYPE_FLOAT,

        TYPE_STRING,

        TYPE_INT,

        TYPE_LONG,

        TYPE_INT,

        TYPE_LONG,

        TYPE_LONG,

        TYPE_STRING,

        TYPE_LONG,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_BOOL,

        TYPE_INT,

        TYPE_LONG,

        TYPE_BOOL,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_BOOL,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_LONG + TYPE_FK,

        TYPE_INT,

        TYPE_STRING,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_BOOL,

        TYPE_BOOL,

        TYPE_INT,

        TYPE_INT,

        TYPE_LONG + TYPE_FK,

        TYPE_LONG + TYPE_FK,

        TYPE_LONG + TYPE_FK,

        TYPE_STRING,

        TYPE_LONG + TYPE_FK,

        TYPE_INT,

        TYPE_FLOAT,

        TYPE_INT,

        TYPE_INT,

        TYPE_INT,

        TYPE_INT,

        TYPE_FLOAT,

        TYPE_FLOAT,

        TYPE_INT,

        TYPE_BOOL,

        TYPE_BOOL,

        TYPE_INT,

        TYPE_INT,

        TYPE_INT,

        TYPE_INT,

        TYPE_DATE,      

        TYPE_BOOL,        

        TYPE_LONG,

        TYPE_STRING,

        TYPE_INT,

        TYPE_DATE,

        TYPE_INT,

        TYPE_DATE,

        TYPE_DATE,
        TYPE_STRING,
        TYPE_DATE,
        TYPE_DATE,
        TYPE_INT,
        TYPE_STRING,
        //update opie-eyek 20130603
        TYPE_LONG,
        TYPE_STRING,
        TYPE_INT,
        TYPE_INT
    };



    public static final String SESS_AUTO_SERVICE = "AUTO_SERVICE";



    public static final int CHARGE_CUSTOMER			= 0;

    public static final int CHARGE_TRAVEL_AGENT		= 1;

    public static final int CHARGE_MEMBERSHIP		= 2;



    //define customer type

    public static final int TYPE_CUSTOMER			= 0;

    public static final int TYPE_COMPLIMENTARY		= 1;

    public static final int TYPE_DAY_USED_OWNER		= 2;

    public static final int TYPE_DAY_USED_EMP		= 3;

    public static final int TYPE_MEMBERSHIP			= 4;

    public static final int TYPE_MEMBERSHIP_RCI		= 5;



    public static final String allCustomerTypeKey[] = {

        "Regular", "Complimentary",

        "House Use(Owner)", "House Use(Empl)",        

        "Membership","Membership RCI"

    };



    public static final String allCustomerTypeCode[] = {

        "PAY", "CMP",

        "HU(O)", "HU(E)",       

        "MEM","RCI"

    };

    

    

    

    public static final String customerTypeKey[] = {

        "Regular", "Complimentary",

        "House Use(Owner)", "House Use(Empl.)"

       

    };



    public static final int allCustomerTypeValue[] = {

        TYPE_CUSTOMER, TYPE_COMPLIMENTARY,

        TYPE_DAY_USED_OWNER, TYPE_DAY_USED_EMP,        

        TYPE_MEMBERSHIP,TYPE_MEMBERSHIP_RCI

    };



    public static final int customerTypeValue[] = {

        TYPE_CUSTOMER, TYPE_COMPLIMENTARY,

        TYPE_DAY_USED_OWNER, TYPE_DAY_USED_EMP,

        

    }; 





    public static final int REGULAR =0;

    public static final int GROUP = 1;

    public static final int PACKAGE = 2;   



    public static final String rsvTypeKey[]={

        "REGULAR", "GROUP", "PACKAGE"

    };



    public static final int rsvTypeVal[]={

        REGULAR, GROUP

    };

    

    public static final String taxList[] = {

        "Number of Night to Reported"

    };

    

    public static final int DISCOUNT_TYPE_PROCENT	= 0;

    public static final int DISCOUNT_TYPE_VALUE	= 1;



    public static final String[] strDiscType = {"Procent", "Value"};



    public static final int COMPLIMENT_TYPE_FULL	= 0;

    public static final int COMPLIMENT_TYPE_PARTIAL	= 1;



    public static final String[] strComplimentType = {"Full", "Partial"};



    public static final int DISCOUNT_SCOPE_PER_NIGHT	= 0;

    public static final int DISCOUNT_SCOPE_WHOLE		= 1;



    public static final String[] strDiscScope = {"Per Night", "Whole Rsv."};



    public static final int EXCHANGE_TYPE_FIXED = 0;

    public static final int EXCHANGE_TYPE_DAILY = 1;

    public static final int EXCHANGE_TYPE_ON_PAYMENT = 2;



    public static final String[] exchangeTypeStr = {"Fixed", "Daily", "On Payment"};





    public static final int CHILD = 0;

    public static final int PARENT = 1;

    



    public static final String rsvGroupTypeKey[]={

        "Child", "Parent"

    };



    public static final int rsvGroupTypeVal[]={

        CHILD, PARENT

    };





    public static Vector getCustTypeKey(boolean allCust){

        Vector result = new Vector(1,1);

        if(allCust){

            for(int i = 0; i < allCustomerTypeKey.length;i++){

                result.add(""+allCustomerTypeKey[i]);

            }

        }else{

            for(int i = 0; i < customerTypeKey.length;i++){

                result.add(""+allCustomerTypeKey[i]);

            }

        }

        return result;



    }



    public static Vector getCustTypeValue(boolean allCust){

        Vector result = new Vector(1,1);

        if(allCust){

            for(int i = 0; i < allCustomerTypeValue.length;i++){

                result.add(""+allCustomerTypeValue[i]);

            }

        }else{

            for(int i = 0; i < customerTypeValue.length;i++){

                result.add(""+customerTypeValue[i]);

            }

        }

        return result;



    }



    public static Vector getCustTypeKey(boolean allCust, long rsvMemberOID){

        Vector result = new Vector(1,1);

        if(allCust){

            for(int i = 0; i < allCustomerTypeKey.length;i++){

                if(rsvMemberOID!=0){

                    result.add(""+allCustomerTypeKey[i]);

                }

                else{

                    if(i!=PstReservation.TYPE_MEMBERSHIP && i!=PstReservation.TYPE_MEMBERSHIP_RCI){

                        result.add(""+allCustomerTypeKey[i]);

                    }

                }

            }

        }else{

            for(int i = 0; i < customerTypeKey.length;i++){

                if(rsvMemberOID!=0){

                    result.add(""+allCustomerTypeKey[i]);

                }

                else{

                    if(i!=PstReservation.TYPE_MEMBERSHIP && i!=PstReservation.TYPE_MEMBERSHIP_RCI){

                        result.add(""+allCustomerTypeKey[i]);

                    }

                }

            }

        }

        return result;



    }



    public static Vector getCustTypeValue(boolean allCust, long rsvMemberOID){

        Vector result = new Vector(1,1);

        if(allCust){

            for(int i = 0; i < allCustomerTypeValue.length;i++){

                if(rsvMemberOID!=0){

                    result.add(""+allCustomerTypeValue[i]);

                }

                else{

                    if(i!=PstReservation.TYPE_MEMBERSHIP && i!=PstReservation.TYPE_MEMBERSHIP_RCI){

                        result.add(""+allCustomerTypeValue[i]);

                    }

                }

            }

        }else{

            for(int i = 0; i < customerTypeValue.length;i++){

                if(rsvMemberOID!=0){

                    result.add(""+allCustomerTypeValue[i]);

                }

                else{

                    if(i!=PstReservation.TYPE_MEMBERSHIP && i!=PstReservation.TYPE_MEMBERSHIP_RCI){

                        result.add(""+allCustomerTypeValue[i]);

                    }

                }

            }

        }

        return result;



    }



    public static final String RSV_TASK = "RESERV_TASK";

    // Reservation Task



    public static final int RSV_TASK_RESERVE 	= 0; // neutral task  for general editing

    public static final int RSV_TASK_RSV_NEW 	= 1;

    public static final int RSV_TASK_CHECK_IN 	= 2;

    public static final int RSV_TASK_CHECK_OUT	= 3;

    public static final int RSV_TASK_TRANSFER 	= 4;

    public static final int RSV_TASK_INHOUSE 	= 5;

    public static final int RSV_TASK_INHOUSE_NEW = 6;



     public static final int RSV_TASK_NEW_CUST 	= 7;



    public static final String rsvTaskCapList[] = {

        "Reservation List", "Add New Reservation",

        "Check In", "Check Out","Room Transfer","Guest In House", "Add New Registration"

    };



    public static final String rsvTaskCapEdit[] = {

        "Entry Reservation Data", "Reservation - Add New", "Check In",

        "Check Out","Room Transfer","Entry Registration Data", "Registration - Add New"

    };



        // Reservation Status

        public static final int RESERVATION_STATUS_NOT_GUARANTED = 0;

        public static final int RESERVATION_STATUS_GUARANTED = 1;

        public static final int RESERVATION_STATUS_DEPOSIT_GUARANTED = 2;

        public static final int RESERVATION_STATUS_CHECK_IN = 3;

        public static final int RESERVATION_STATUS_CHECK_OUT = 4;

        public static final int RESERVATION_STATUS_DELAYED_CHECK_OUT = 5;

        public static final int RESERVATION_STATUS_CANCELED = 6;

        public static final int RESERVATION_STATUS_TRANSFER = 7;



        public static String[] rsvStatus = {"Not Guaranted","Guaranted",

        "Guaranted by Deposit","In House","Checked Out", "Delayed Check Out",  "Canceled","Transfered"};

        public static int[] rsvStatusValue = {0,1,2,3,4,5,6,7};



        public static String[] rsvStatusCode = {"N","G","$","I","O","D","C","T"};


        public static String[] rsvStatusCodeRoomLadger = {"Room","","$","I","O","D","C","T"};

       /**
       * add opie-eyek
       */

       public static final int LEDGER_ROOM=0;
       public static final int LEDGER_BREAKFAST=1;
       public static final int LEDGER_ADJSCOMPLROOM=2;
       public static final int LEDGER_ADDBILL=3;
       public static final int LEDGER_TLPBILL=4;
       public static final int LEDGER_ADJUSMENT=5;
       public static final int LEDGER_SPESIALDISC=6;
       public static final int LEDGER_TAXSERVICE=7;

       public static final String [] searchPaketKey = {

            "ROOM","BREAKFAST","ADJ & COMPL ROOM", "ADDITIONAL BILL","TELEPHONE BILL","ADJUSMENT","SPESIAL DISCOUNT","TAX AND SERVICE"

        };

        public static final String[] searchPaketValue = {

            "0","1","2","3","4","5","6","7"

        };

         public static Vector searchPaketKey()

        {

            Vector result = new Vector(1,1);

            for(int r=0;r < searchPaketKey.length;r++){

                    result.add(searchPaketKey[r]);

            }

            return result;

        }

        public static Vector searchPaketValue()

        {

            Vector result = new Vector(1,1);

            for(int r=0;r < searchPaketValue.length;r++){

                    result.add(searchPaketValue[r]);

            }

            return result;

        }
         //finis


        /*public static String[] rsvStatusCode = {"I","D",

        "T","O"};*/

        

         public static String[] rsvResStatusCode = {"N","G",

        "$","C", "O"};



        public static String[] rsvInStatusCode = {"N","G",

        "$"};

        public static String[] rsvOutStatusCode = {"I","D","T"};



        public static String[] rsvStatusTaskInv = {"N","G",

        "$","I","O", "D", "T","C"};



        public static String[] InhStatusCode = {"I", "D","T"};//{"I","O", "D","T"};

        

        //Edited By Yunny

        public static String[] taxStatus = {"I", "O","D","T"};

        

       

        //mpe sini

    

        public static int[] rsvOutStatusValue = {RESERVATION_STATUS_CHECK_IN,RESERVATION_STATUS_DELAYED_CHECK_OUT,RESERVATION_STATUS_TRANSFER};

        public static int[] rsvResOutStatusValue = {RESERVATION_STATUS_NOT_GUARANTED,RESERVATION_STATUS_GUARANTED,RESERVATION_STATUS_DEPOSIT_GUARANTED,RESERVATION_STATUS_CANCELED, RESERVATION_STATUS_CHECK_OUT};

        public static int[] inhOutStatusValue = {RESERVATION_STATUS_CHECK_IN,RESERVATION_STATUS_CHECK_OUT,RESERVATION_STATUS_DELAYED_CHECK_OUT,RESERVATION_STATUS_TRANSFER};

        

        public static int[] inhStatusValue = {RESERVATION_STATUS_CHECK_IN, RESERVATION_STATUS_DELAYED_CHECK_OUT,RESERVATION_STATUS_TRANSFER};//{RESERVATION_STATUS_CHECK_IN,RESERVATION_STATUS_CHECK_OUT,RESERVATION_STATUS_DELAYED_CHECK_OUT,RESERVATION_STATUS_TRANSFER};

        public static int[] rsvStatusTaskValue = {RESERVATION_STATUS_NOT_GUARANTED,RESERVATION_STATUS_GUARANTED,RESERVATION_STATUS_DEPOSIT_GUARANTED,RESERVATION_STATUS_CHECK_IN,RESERVATION_STATUS_CHECK_OUT,RESERVATION_STATUS_DELAYED_CHECK_OUT,RESERVATION_STATUS_TRANSFER,RESERVATION_STATUS_CANCELED};//{RESERVATION_STATUS_CHECK_IN,RESERVATION_STATUS_CHECK_OUT,RESERVATION_STATUS_DELAYED_CHECK_OUT,RESERVATION_STATUS_TRANSFER};

        

        //public static int[] inhOutTaxValue = {RESERVATION_STATUS_CHECK_IN, RESERVATION_STATUS_DELAYED_CHECK_OUT,RESERVATION_STATUS_TRANSFER,RESERVATION_STATUS_CHECK_OUT};;//{RESERVATION_STATUS_CHECK_IN,RESERVATION_STATUS_CHECK_OUT,RESERVATION_STATUS_DELAYED_CHECK_OUT,RESERVATION_STATUS_TRANSFER};

        public static Vector getStatusRsv(int rsvTask) {

            Vector result = new Vector(1,1);

            Vector temp = new Vector(1,1);

            if(rsvTask == RSV_TASK_CHECK_IN){

              

                for(int r = 0; r < rsvInStatusCode.length; r++){



                    temp = new Vector(1,1);

                    temp.add(rsvInStatusCode[r]);

                    temp.add(String.valueOf(r));

                    result.add(temp);

                }

            }else{

                if(rsvTask == RSV_TASK_CHECK_OUT || rsvTask == RSV_TASK_TRANSFER){

                    for(int r = 0; r < rsvOutStatusCode.length; r++){

                        temp = new Vector(1,1);

                        temp.add(rsvOutStatusCode[r]);

                        temp.add(String.valueOf(rsvOutStatusValue[r]));

                        result.add(temp);

                    }



                /*}else if(rsvTask == RSV_TASK_RSV_NEW){

                    for(int r = 0; r < rsvResStatusCode.length; r++){

                        temp = new Vector(1,1);

                        temp.add(rsvResStatusCode[r]);

                        temp.add(String.valueOf(rsvResOutStatusValue[r]));

                        result.add(temp);

                    }  */

                }else if(rsvTask == RSV_TASK_INHOUSE){

                        for(int r = 0; r < InhStatusCode.length; r++){

                        temp = new Vector(1,1);

                        temp.add(InhStatusCode[r]);

                        temp.add(String.valueOf(inhStatusValue[r]));

                        result.add(temp);

                        }



                }else {

                    

                    for(int r = 0; r < rsvStatusCode.length; r++){

                        temp = new Vector(1,1);

                        temp.add(rsvStatusCode[r]);

                        temp.add(String.valueOf(r));

                        result.add(temp);

                    }

                }



            }







            return result;



        }

        

        //method khudus untuk tax

        //editeb by Yunny

        //yunny yang edit khusus untuk tax

       // public static int[] inhStatusValue = {RESERVATION_STATUS_CHECK_IN, RESERVATION_STATUS_DELAYED_CHECK_OUT,RESERVATION_STATUS_TRANSFER};;//{RESERVATION_STATUS_CHECK_IN,RESERVATION_STATUS_CHECK_OUT,RESERVATION_STATUS_DELAYED_CHECK_OUT,RESERVATION_STATUS_TRANSFER};

        public static Vector getStatusInHouse(int rsvTask) {

            Vector result = new Vector(1,1);

            Vector temp = new Vector(1,1);

               for(int r = 0; r < InhStatusCode.length; r++){

                        

                        temp = new Vector(1,1);

                        temp.add(InhStatusCode[r]);

                        String nilai=(String.valueOf(inhStatusValue[r]));

                        temp.add(nilai);

                        result.add(temp);

                    }

          return result;



        }

        //sampai sini

        

        //method khudus untuk guest in house tax

        //editeb by Yunny

        //yunny yang edit khusus untuk tax

        public static int[] inhOutTaxValue = {RESERVATION_STATUS_CHECK_IN,RESERVATION_STATUS_CHECK_OUT,RESERVATION_STATUS_DELAYED_CHECK_OUT,RESERVATION_STATUS_TRANSFER};

        public static Vector getStatusTax(int rsvTask) {

            Vector result = new Vector(1,1);

            Vector temp = new Vector(1,1);

               for(int r = 0; r < taxStatus.length; r++){

                        

                        temp = new Vector(1,1);

                        temp.add(taxStatus[r]);

                        String nilai=(String.valueOf(inhOutTaxValue[r]));

                        temp.add(nilai);

                        result.add(temp);

                    }

          return result;



        }

        //sampai sini

        

        

        //khusus untuk nationality

        public static final String NATIONALITY_TASK = "NATIONALITY_TASK";

        public static int FOREIGNER 	= 0;

        public static int LOCAL         = 1;

        public static String[] guestNationality = {"Foreigner", "Local"};

        

       

        public static int[] nationalityValue = {FOREIGNER,LOCAL};;

        public static Vector getGuestNationality() {

            Vector result = new Vector(1,1);

            Vector tempNationality = new Vector(1,1);

            int lengthOfBoth = guestNationality.length + rsvStatusCode.length ;

               for(int n = 0; n < guestNationality.length; n++){

                        tempNationality = new Vector(1,1);

                        tempNationality.add(guestNationality[n]);

                        tempNationality.add(String.valueOf(nationalityValue[n]));

                        result.add(tempNationality);

               }

            

          return result;



        }

        //----------------

        

        //khusus untuk Tax Report Status

        public static final String REPORT_STATUS_TASK = "REPORT_STATUS_TASK";

        public static int REPORTED 	= 0;

        public static int NOT_REPORTED         = 1;

        public static String[] guestReportStatus = {"Reported", "Not Reported"};

        public static int[] reportStatusValue = {REPORTED,NOT_REPORTED};;

        public static Vector getGuestReportStatus() {

            Vector result = new Vector(1,1);

            Vector tempReportStatus = new Vector(1,1);

                for(int t = 0; t < guestReportStatus.length; t++){

                        tempReportStatus = new Vector(1,1);

                        tempReportStatus.add(guestReportStatus[t]);

                        tempReportStatus.add(String.valueOf(reportStatusValue[t]));

                        result.add(tempReportStatus);
                }

            

          return result;



        }

        //----------------



        public static int STYLE_RESERV_NOT_GUA 	= 0;

        public static int STYLE_GUARANTED 			= 1;

        public static int STYLE_GUA_BY_DEPOSIT 	= 2;

        public static int STYLE_CHEK_IN 			= 3;

        public static int STYLE_CHEK_OUT 			= 4;

        public static int STYLE_DELAYED_CHECK_OUT  = 5;

        public static int STYLE_AV 				= 6;

        public static int STYLE_TRANSFER 			= 7;

        public static int STYLE_EDITED 			= 8;

        public static int STYLE_RELEASED 			= 9;

        public static int STYLE_CANCELED 			= 10;

        public static int STYLE_NOT_AV 			= 11;

        public static int STYLE_USE_BY_OWNER		= 12;



        public static String[] statusStyle ={

            "styleRsvNotGua","styleRsvGua","styleRsvGuaByDep",

            "styleCheckIn","styleCheckOut","styleDelayedCheckOut",

            "styleAv","styleTransfer","styleEdited","styleReleased",

            "styleCanceled","styleNotAv","styleUseByOwner"

        };





        public static final int NO_CHARGE					= 0;

        public static final int QUARTER_DAY_USE				= 1;

        public static final int DAY_USE						= 2;

        public static final int ADDITIONAL_DAY				= 3;



        public static String[] additionalTypeKey = {"No Charge", "Quarter Day Use",

        "Day Use","Additional Room Night"};



        public static String[] additionalTypeValue ={"0","1","2","3"};



        public static Vector addTypeKey(){

            Vector result = new Vector(1,1);

            for(int r=0;r<additionalTypeKey.length;r++){

                result.add(additionalTypeKey[r]);

            }

            return result;

        }





        public static Vector addTypeValue(){

            Vector result = new Vector(1,1);

            for(int r=0;r<additionalTypeValue.length;r++){

                result.add(additionalTypeValue[r]);

            }

            return result;

        }



        public static final int ONE_WEEK 	= 0;





        //hidden Field

        public static final String SELL_OID						=  "SELL_ID" ;

        public static final String HIDDEN_ROOM_CLASS_OID		=  "ROOM_CLASS_ID" ;

        public static final String HIDDEN_HOTEL_ROOM_OID		=  "HOTEL_ROOM_ID" ;

        public static final String HIDDEN_CUSTOMER_OID			=  "CONTACT_ID" ;

        public static final String HIDDEN_ROOM_NUMBER		    =  "ROOM_NUMBER" ;

        public static final String HIDDEN_ROOM_NIGHT		    =  "ROOM_NIGHT" ;

        public static final String HIDDEN_CHECK_IN		    	=  "CHECK_IN" ;

        public static final String HIDDEN_CUSTOMER_TYPE		    =  "CUSTOMER_TYPE" ;





        //if(SELL_OID.equals(anObject)

        // reservation classification

        public static final int CLF_PERSONAL	= 0;

        public static final int CLF_CORPORATE	= 1;



        public static final String[] classificationKey = {"Personal", "Corporate"};

        public static final int[] classificationValue = {CLF_PERSONAL,CLF_CORPORATE};





        public static Vector getClassificationKey() {

            Vector result = new Vector(1,1);

            for(int i =0;i< classificationKey.length;i++){

                result.add(""+classificationKey[i]);

            }

            return result;

        }





        public static Vector getClassificationValue() {

            Vector result = new Vector(1,1);

            for(int i =0;i< classificationValue.length;i++){

                result.add(""+classificationValue[i]);

            }

            return result;

        }



        public PstReservation(){

        }



        public PstReservation(int i) throws DBException {

            super(new PstReservation());

        }



        public PstReservation(String sOid) throws DBException {

            super(new PstReservation(0));

            if(!locate(sOid))

                throw new DBException(this,DBException.RECORD_NOT_FOUND);

            else

                return;

        }



        public PstReservation(long lOid) throws DBException {

            super(new PstReservation(0));

            String sOid = "0";

            try {

                sOid = String.valueOf(lOid);

            }catch(Exception e) {

                throw new DBException(this,DBException.RECORD_NOT_FOUND);

            }

            if(!locate(sOid))

                throw new DBException(this,DBException.RECORD_NOT_FOUND);

            else

                return;

        }



        public int getFieldSize(){

            return fieldNames.length;

        }



        public String getTableName(){

            return TBL_RESERVATION;

        }



        public String[] getFieldNames(){

            return fieldNames;

        }



        public int[] getFieldTypes(){

            return fieldTypes;

        }



        public String getPersistentName(){

            return new PstReservation().getClass().getName();

        }



        public long fetchExc(Entity ent) throws Exception{

            Reservation reservation = fetchExc(ent.getOID());

            ent = (Entity)reservation;

            return reservation.getOID();

        }



        public long insertExc(Entity ent) throws Exception{

            return insertExc((Reservation) ent);

        }



        public long updateExc(Entity ent) throws Exception{

            return updateExc((Reservation) ent);

        }



        public long deleteExc(Entity ent) throws Exception{

            if(ent==null){

                throw new DBException(this,DBException.RECORD_NOT_FOUND);

            }

            return deleteExc(ent.getOID());

        }



        public static Reservation fetchExc(long oid) throws DBException{

            try{



                Reservation reservation = new Reservation();

                PstReservation pstReservation = new PstReservation(oid);

                reservation.setOID(oid);



                reservation.setTravelAgentId(pstReservation.getlong(FLD_TRAVEL_AGENT_ID));

                reservation.setMembershipId(pstReservation.getlong(FLD_MEMBERSHIP_ID));

                reservation.setRoomClassId(pstReservation.getlong(FLD_ROOM_CLASS_ID));



                reservation.setHotelRoomId(pstReservation.getlong(FLD_HOTEL_ROOM_ID));

                reservation.setCustomerId(pstReservation.getlong(FLD_CUSTOMER_ID));

                reservation.setSourceOfBooking(pstReservation.getlong(FLD_SOURCE_OF_BOOKING));

                reservation.setSourceOfBookingSpec(pstReservation.getString(FLD_SOURCE_OF_BOOKING_SPEC));

                reservation.setCheckInDate(pstReservation.getDate(FLD_CHECK_IN_DATE));

                reservation.setChekOutDate(pstReservation.getDate(FLD_CHEK_OUT_DATE));

                reservation.setNumberOfNight(pstReservation.getInt(FLD_NUMBER_OF_NIGHT));

                reservation.setNumAdult(pstReservation.getInt(FLD_NUM_ADULT));

                reservation.setNumChild(pstReservation.getInt(FLD_NUM_CHILD));

                reservation.setNumInfant(pstReservation.getInt(FLD_NUM_INFANT));

                reservation.setSpecialRequirement(pstReservation.getString(FLD_SPECIAL_REQUIREMENT));

                reservation.setDiscountRp(pstReservation.getdouble(FLD_DISCOUNT_RP));

                reservation.setDiscount$(pstReservation.getdouble(FLD_DISCOUNT_$));

                reservation.setDepositRp(pstReservation.getdouble(FLD_DEPOSIT_RP));

                reservation.setDeposit$(pstReservation.getdouble(FLD_DEPOSIT_$));

                reservation.setReservationStatus(pstReservation.getInt(FLD_RESERVATION_STATUS));

                reservation.setCancelReason(pstReservation.getString(FLD_CANCEL_REASON));

                reservation.setExtraBed(pstReservation.getboolean(FLD_EXTRA_BED));

                reservation.setAdditionalChargeType(pstReservation.getInt(FLD_ADDITIONAL_CHARGE_TYPE));

                reservation.setRoomRateRp(pstReservation.getdouble(FLD_ROOM_RATE_RP));

                reservation.setSurchargeRp(pstReservation.getdouble(FLD_SURCHARGE_RP));

                reservation.setCompulsoryRp(pstReservation.getdouble(FLD_COMPULSORY_RP));

                reservation.setExtraBedRateRp(pstReservation.getdouble(FLD_EXTRA_BED_RATE_RP));

                reservation.setAdditionalChargeRp(pstReservation.getdouble(FLD_ADDITIONAL_CHARGE_RP));

                reservation.setTotalAllRateRp(pstReservation.getdouble(FLD_TOTAL_ALL_RATE_RP));

                reservation.setRoomRate$(pstReservation.getdouble(FLD_ROOM_RATE_$));

                reservation.setSurcharge$(pstReservation.getdouble(FLD_SURCHARGE_$));

                reservation.setCompulsory$(pstReservation.getdouble(FLD_COMPULSORY_$));

                reservation.setExtraBedRate$(pstReservation.getdouble(FLD_EXTRA_BED_RATE_$));

                reservation.setAdditionalCharge$(pstReservation.getdouble(FLD_ADDITIONAL_CHARGE_$));

                reservation.setTotalAllRate$(pstReservation.getdouble(FLD_TOTAL_ALL_RATE_$));

                reservation.setIncomeRateRp(pstReservation.getdouble(FLD_INCOME_RATE_RP));

                reservation.setTaxRp(pstReservation.getdouble(FLD_TAX_RP));

                reservation.setServiceRp(pstReservation.getdouble(FLD_SERVICE_RP));

                reservation.setIncomeRate$(pstReservation.getdouble(FLD_INCOME_RATE_$));

                reservation.setTax$(pstReservation.getdouble(FLD_TAX_$));

                reservation.setService$(pstReservation.getdouble(FLD_SERVICE_$));

                reservation.setPaidRp(pstReservation.getdouble(FLD_PAID_RP));

                reservation.setPaid$(pstReservation.getdouble(FLD_PAID_$));

                reservation.setIncomeCalculationType(pstReservation.getInt(FLD_INCOME_CALCULATION_TYPE));

                reservation.setExchangeRate(pstReservation.getdouble(FLD_EXCHANGE_RATE));

                reservation.setNote(pstReservation.getString(FLD_NOTE));

                reservation.setChargeCurrency(pstReservation.getInt(FLD_CHARGE_CURRENCY));

                reservation.setParentId(pstReservation.getlong(FLD_PARENT_ID));

                reservation.setCustomerType(pstReservation.getInt(FLD_CUSTOMER_TYPE));

                reservation.setMembershipUpgradeId(pstReservation.getlong(FLD_MEMBERSHIP_UPGRADE_ID));

                reservation.setMembershipCustomerId(pstReservation.getlong(FLD_MEMBERSHIP_CUSTOMER_ID));

                //reservation.setCIN(pstReservation.getString(FLD_CIN));

                reservation.setExchangeRciId(pstReservation.getlong(FLD_EXCHANGE_RCI_ID));

                reservation.setCancelFeeRp(pstReservation.getdouble(FLD_CANCEL_FEE_RP));

                reservation.setCancelFeeUsd(pstReservation.getdouble(FLD_CANCEL_FEE_USD));

                reservation.setTakeOverFromTravel(pstReservation.getboolean(FLD_TAKE_OVER_FROM_TRAVEL));

                reservation.setResvClassification(pstReservation.getInt(FLD_RESV_CLASSIFICATION));

                reservation.setInvNumber(pstReservation.getString(FLD_INV_NUMBER));

                reservation.setBillingBreakfastId(pstReservation.getlong(FLD_BILLING_BREAKFAST_ID));

                reservation.setIncludeBreakfast(pstReservation.getboolean(FLD_INCLUDE_BREAKFAST));

                reservation.setBreakfastPriceRp(pstReservation.getdouble(FLD_BREAKFAST_PRICE_RP));

                reservation.setBreakfastPriceUsd(pstReservation.getdouble(FLD_BREAKFAST_PRICE_USD));

                reservation.setWithCommision(pstReservation.getboolean(FLD_WITH_COMMISION));

                reservation.setCommisionAmountRp(pstReservation.getdouble(FLD_COMMISION_AMOUNT_RP));

                reservation.setCommisionAmountUsd(pstReservation.getdouble(FLD_COMMISION_AMOUNT_USD));

                reservation.setCommisionContactId(pstReservation.getlong(FLD_COMMISION_CONTACT_ID));

                reservation.setCommisionStatus(pstReservation.getInt(FLD_COMMISION_STATUS));

                reservation.setRoomChargePerNightRp(pstReservation.getdouble(FLD_ROOM_CHARGE_PER_NIGHT_RP));

                reservation.setRoomChargePerNightUsd(pstReservation.getdouble(FLD_ROOM_CHARGE_PER_NIGHT_USD));

                reservation.setBrfChargeIncludeInRoom(pstReservation.getboolean(FLD_BRF_CHARGE_INCLUDE_IN_ROOM));

                reservation.setUseTravelContract(pstReservation.getboolean(FLD_USE_TRAVEL_CONTRACT));



                reservation.setRsvGroupType(pstReservation.getInt(FLD_RSV_GROUP_TYPE));

                reservation.setRsvType(pstReservation.getInt(FLD_RSV_TYPE));

                reservation.setRsvParentGroupId(pstReservation.getlong(FLD_RSV_PARENT_GROUP_ID));



                reservation.setTravelPackageId(pstReservation.getlong(FLD_TRAVEL_PACKAGE_ID));

                reservation.setTravelPackTypeId(pstReservation.getlong(FLD_TRAVEL_PACK_TYPE_ID));

                reservation.setReservationNum(pstReservation.getString(FLD_RESERVATION_NUM));



                reservation.setContractId(pstReservation.getlong(FLD_CONTRACT_ID));

                reservation.setDiscountType(pstReservation.getInt(FLD_DISCOUNT_TYPE));

                reservation.setDiscountProcent(pstReservation.getdouble(FLD_DISCOUNT_PROCENT));

                reservation.setDiscountScope(pstReservation.getInt(FLD_DISCOUNT_SCOPE));

                reservation.setTaxInclude(pstReservation.getInt(FLD_TAX_INCLUDE));

                reservation.setServiceInclude(pstReservation.getInt(FLD_SERVICE_INCLUDE));

                reservation.setNumNightDiscount(pstReservation.getInt(FLD_NUM_NIGHT_DISCOUNT));

                reservation.setTotalDiscountRp(pstReservation.getdouble(FLD_TOTAL_DISCOUNT_RP));

                reservation.setTotalDiscountUsd(pstReservation.getdouble(FLD_TOTAL_DISCOUNT_USD));

                reservation.setNumberCounter(pstReservation.getInt(FLD_NUMBER_COUNTER));



				reservation.setSurchargeInclude(pstReservation.getboolean(FLD_SURCHARGE_INCLUDE));

                reservation.setCompulsoryInclude(pstReservation.getboolean(FLD_COMPULSORY_INCLUDE));

                reservation.setNumNightCompliment(pstReservation.getInt(FLD_NUM_NIGHT_COMPLIMENT));

                reservation.setComplimentType(pstReservation.getInt(FLD_COMPLIMENT_TYPE));

				reservation.setExchangeType(pstReservation.getInt(FLD_EXCHANGE_TYPE));

                reservation.setTypeInvoice(pstReservation.getInt(FLD_TYPE_INVOICE));

                reservation.setRegDate(pstReservation.getDate(FLD_REG_DATE));

                reservation.setIsTransfered(pstReservation.getboolean(FLD_IS_TRANSFERED));

                

                reservation.setCustomType(pstReservation.getlong(FLD_CUSTOM_TYPE));

                reservation.setCustomTypeSpec(pstReservation.getString(FLD_CUSTOM_TYPE_SPEC));

                //tax government

                reservation.setNumberNightReported(pstReservation.getInt(FLD_NUMBER_NIGHT_REPORTED));

                reservation.setCheckoutDateReported(pstReservation.getDate(FLD_CHECKOUT_DATE_REPORTED));

                reservation.setStatusReported(pstReservation.getInt(FLD_STATUS_REPORTED));

                reservation.setCheckInTime(pstReservation.getDate(FLD_CHECKIN_TIME));

                reservation.setCheckOutTime(pstReservation.getDate(FLD_CHECKOUT_TIME));
                reservation.setGuestName(pstReservation.getString(FLD_GUEST_NAME));
                if(1==2){
                    try{
                        reservation.setCancelDate(pstReservation.getDate(FLD_CANCEL_DATE));
                        reservation.setCancelInputOn(pstReservation.getDate(FLD_CANCEL_INPUT_ON));
                        }
                    catch(Exception exc1){}
                try{reservation.setStatusDoc(pstReservation.getInt(FLD_STATUS_DOC));} catch(Exception exc1){}}
                reservation.setBookingCode(pstReservation.getString(FLD_BOOKING_CODE));
                //add opie-eyek 20130603
                reservation.setTermsOfPayment(pstReservation.getlong(FLD_TERM_OF_PAYMENT));
                reservation.setGuarantedBy(pstReservation.getString(FLD_GUARANTED_BY));
                reservation.setStatusCutOfDay(pstReservation.getInt(FLD_STATUS_CUT_OF_DAYS));
                reservation.setTypeOfBooking(pstReservation.getInt(FLD_TYPE_OF_BOOKING));
                return reservation;

            }catch(DBException dbe){ 

                throw dbe;

            }catch(Exception e){

                throw new DBException(new PstReservation(0),DBException.UNKNOWN);

            }

        }





        public static long updateExc(Reservation reservation) throws DBException{

            try{

                if(reservation.getOID() != 0){
                    PstReservation pstReservation = new PstReservation(reservation.getOID());
                    pstReservation.setLong(FLD_TRAVEL_AGENT_ID, reservation.getTravelAgentId());
                    pstReservation.setLong(FLD_MEMBERSHIP_ID, reservation.getMembershipId());
                    pstReservation.setLong(FLD_ROOM_CLASS_ID, reservation.getRoomClassId());
                    pstReservation.setLong(FLD_HOTEL_ROOM_ID, reservation.getHotelRoomId());
                    pstReservation.setLong(FLD_CUSTOMER_ID, reservation.getCustomerId());
                    pstReservation.setLong(FLD_SOURCE_OF_BOOKING, reservation.getSourceOfBooking());
                    pstReservation.setString(FLD_SOURCE_OF_BOOKING_SPEC, reservation.getSourceOfBookingSpec());
                    pstReservation.setDate(FLD_CHECK_IN_DATE, reservation.getCheckInDate());
                    pstReservation.setDate(FLD_CHEK_OUT_DATE, reservation.getChekOutDate());
                    pstReservation.setInt(FLD_NUMBER_OF_NIGHT, reservation.getNumberOfNight());
                    pstReservation.setInt(FLD_NUM_ADULT, reservation.getNumAdult());
                    pstReservation.setInt(FLD_NUM_CHILD, reservation.getNumChild());
                    pstReservation.setInt(FLD_NUM_INFANT, reservation.getNumInfant());
                    pstReservation.setString(FLD_SPECIAL_REQUIREMENT, reservation.getSpecialRequirement());
                    pstReservation.setDouble(FLD_DISCOUNT_RP, reservation.getDiscountRp());

                    pstReservation.setDouble(FLD_DISCOUNT_$, reservation.getDiscount$());

                    pstReservation.setDouble(FLD_DEPOSIT_RP, reservation.getDepositRp());

                    pstReservation.setDouble(FLD_DEPOSIT_$, reservation.getDeposit$());

                    pstReservation.setInt(FLD_RESERVATION_STATUS, reservation.getReservationStatus());

                    pstReservation.setString(FLD_CANCEL_REASON, reservation.getCancelReason());

                    pstReservation.setboolean(FLD_EXTRA_BED, reservation.getExtraBed());

                    pstReservation.setInt(FLD_ADDITIONAL_CHARGE_TYPE, reservation.getAdditionalChargeType());

                    pstReservation.setDouble(FLD_ROOM_RATE_RP, reservation.getRoomRateRp());

                    pstReservation.setDouble(FLD_SURCHARGE_RP, reservation.getSurchargeRp());

                    pstReservation.setDouble(FLD_COMPULSORY_RP, reservation.getCompulsoryRp());

                    pstReservation.setDouble(FLD_EXTRA_BED_RATE_RP, reservation.getExtraBedRateRp());

                    pstReservation.setDouble(FLD_ADDITIONAL_CHARGE_RP, reservation.getAdditionalChargeRp());

                    pstReservation.setDouble(FLD_TOTAL_ALL_RATE_RP, reservation.getTotalAllRateRp());

                    pstReservation.setDouble(FLD_ROOM_RATE_$, reservation.getRoomRate$());

                    pstReservation.setDouble(FLD_SURCHARGE_$, reservation.getSurcharge$());

                    pstReservation.setDouble(FLD_COMPULSORY_$, reservation.getCompulsory$());

                    pstReservation.setDouble(FLD_EXTRA_BED_RATE_$, reservation.getExtraBedRate$());

                    pstReservation.setDouble(FLD_ADDITIONAL_CHARGE_$, reservation.getAdditionalCharge$());

                    pstReservation.setDouble(FLD_TOTAL_ALL_RATE_$, reservation.getTotalAllRate$());

                    pstReservation.setDouble(FLD_INCOME_RATE_RP, reservation.getIncomeRateRp());

                    pstReservation.setDouble(FLD_TAX_RP, reservation.getTaxRp());

                    pstReservation.setDouble(FLD_SERVICE_RP, reservation.getServiceRp());

                    pstReservation.setDouble(FLD_INCOME_RATE_$, reservation.getIncomeRate$());

                    pstReservation.setDouble(FLD_TAX_$, reservation.getTax$());

                    pstReservation.setDouble(FLD_SERVICE_$, reservation.getService$());

                    pstReservation.setDouble(FLD_PAID_RP, reservation.getPaidRp());

                    pstReservation.setDouble(FLD_PAID_$, reservation.getPaid$());

                    pstReservation.setInt(FLD_INCOME_CALCULATION_TYPE, reservation.getIncomeCalculationType());

                    pstReservation.setDouble(FLD_EXCHANGE_RATE, reservation.getExchangeRate());

                    pstReservation.setString(FLD_NOTE, reservation.getNote());

                    pstReservation.setInt(FLD_CHARGE_CURRENCY, reservation.getChargeCurrency());

                    pstReservation.setLong(FLD_PARENT_ID, reservation.getParentId());

                    pstReservation.setInt(FLD_CUSTOMER_TYPE, reservation.getCustomerType());

                    pstReservation.setLong(FLD_MEMBERSHIP_UPGRADE_ID, reservation.getMembershipUpgradeId());

                    pstReservation.setLong(FLD_MEMBERSHIP_CUSTOMER_ID, reservation.getMembershipCustomerId());

                    // pstReservation.setString(FLD_CIN, reservation.getCIN());

                    pstReservation.setLong(FLD_EXCHANGE_RCI_ID, reservation.getExchangeRciId());

                    pstReservation.setDouble(FLD_CANCEL_FEE_RP, reservation.getCancelFeeRp());

                    pstReservation.setDouble(FLD_CANCEL_FEE_USD, reservation.getCancelFeeUsd());

                    pstReservation.setboolean(FLD_TAKE_OVER_FROM_TRAVEL, reservation.getTakeOverFromTravel());

                    pstReservation.setInt(FLD_RESV_CLASSIFICATION, reservation.getResvClassification());





                    pstReservation.setLong(FLD_BILLING_BREAKFAST_ID, reservation.getBillingBreakfastId());

                    pstReservation.setboolean(FLD_INCLUDE_BREAKFAST, reservation.getIncludeBreakfast());

                    pstReservation.setDouble(FLD_BREAKFAST_PRICE_RP, reservation.getBreakfastPriceRp());

                    pstReservation.setDouble(FLD_BREAKFAST_PRICE_USD, reservation.getBreakfastPriceUsd());

                    pstReservation.setboolean(FLD_WITH_COMMISION, reservation.getWithCommision());

                    pstReservation.setDouble(FLD_COMMISION_AMOUNT_RP, reservation.getCommisionAmountRp());

                    pstReservation.setDouble(FLD_COMMISION_AMOUNT_USD, reservation.getCommisionAmountUsd());

                    pstReservation.setLong(FLD_COMMISION_CONTACT_ID, reservation.getCommisionContactId());

                    pstReservation.setInt(FLD_COMMISION_STATUS, reservation.getCommisionStatus());

                    pstReservation.setString(FLD_INV_NUMBER, reservation.getInvNumber());



                    pstReservation.setDouble(FLD_ROOM_CHARGE_PER_NIGHT_RP, reservation.getRoomChargePerNightRp());

                    pstReservation.setDouble(FLD_ROOM_CHARGE_PER_NIGHT_USD, reservation.getRoomChargePerNightUsd());

                    pstReservation.setboolean(FLD_BRF_CHARGE_INCLUDE_IN_ROOM, reservation.getBrfChargeIncludeInRoom());

                    pstReservation.setboolean(FLD_USE_TRAVEL_CONTRACT, reservation.getUseTravelContract());



                    pstReservation.setInt(FLD_RSV_GROUP_TYPE, reservation.getRsvGroupType());

                    pstReservation.setInt(FLD_RSV_TYPE, reservation.getRsvType());

                    pstReservation.setLong(FLD_RSV_PARENT_GROUP_ID, reservation.getRsvParentGroupId());



                    pstReservation.setLong(FLD_TRAVEL_PACKAGE_ID, reservation.getTravelPackageId());

	                pstReservation.setLong(FLD_TRAVEL_PACK_TYPE_ID, reservation.getTravelPackTypeId());

                    pstReservation.setString(FLD_RESERVATION_NUM,reservation.getReservationNum());
                    pstReservation.setString(FLD_BOOKING_CODE,reservation.getBookingCode());



                    pstReservation.setLong(FLD_CONTRACT_ID,reservation.getContractId());

                    pstReservation.setInt(FLD_DISCOUNT_TYPE,reservation.getDiscountType());

                    pstReservation.setDouble(FLD_DISCOUNT_PROCENT,reservation.getDiscountProcent());

                    pstReservation.setInt(FLD_DISCOUNT_SCOPE,reservation.getDiscountScope());

                    pstReservation.setInt(FLD_TAX_INCLUDE,reservation.getTaxInclude());

                    pstReservation.setInt(FLD_SERVICE_INCLUDE,reservation.getServiceInclude());

                    pstReservation.setInt(FLD_NUM_NIGHT_DISCOUNT,reservation.getNumNightDiscount());

                    pstReservation.setDouble(FLD_TOTAL_DISCOUNT_RP,reservation.getTotalDiscountRp());

                    pstReservation.setDouble(FLD_TOTAL_DISCOUNT_USD,reservation.getTotalDiscountUsd());

                    pstReservation.setInt(FLD_NUMBER_COUNTER,reservation.getNumberCounter());



                    pstReservation.setboolean(FLD_SURCHARGE_INCLUDE,reservation.getSurchargeInclude());

                    pstReservation.setboolean(FLD_COMPULSORY_INCLUDE,reservation.getCompulsoryInclude());

                    pstReservation.setInt(FLD_NUM_NIGHT_COMPLIMENT,reservation.getNumNightCompliment());

                    pstReservation.setInt(FLD_COMPLIMENT_TYPE,reservation.getComplimentType());

                    pstReservation.setInt(FLD_EXCHANGE_TYPE,reservation.getExchangeType());

                    pstReservation.setInt(FLD_TYPE_INVOICE,reservation.getTypeInvoice());

                    pstReservation.setDate(FLD_REG_DATE,reservation.getRegDate());

                    pstReservation.setboolean(FLD_IS_TRANSFERED,reservation.getIsTransfered());

                    

                    pstReservation.setLong(FLD_CUSTOM_TYPE, reservation.getCustomType());

                    pstReservation.setString(FLD_CUSTOM_TYPE_SPEC, reservation.getCustomTypeSpec());
                    pstReservation.setString(FLD_GUEST_NAME, reservation.getGuestName());
                    pstReservation.setDate(FLD_CANCEL_DATE,reservation.getCancelDate());
                    pstReservation.setDate(FLD_CANCEL_INPUT_ON,reservation.getCancelInputOn());
                    pstReservation.setInt(FLD_STATUS_DOC,reservation.getStatusDoc());

                    //tax n Reported

                    pstReservation.setInt(FLD_NUMBER_NIGHT_REPORTED,reservation.getNumberNightReported());

                    pstReservation.setDate(FLD_CHECKOUT_DATE_REPORTED,reservation.getCheckoutDateReported());

                    pstReservation.setInt(FLD_STATUS_REPORTED,reservation.getStatusReported());

                    //add opie-eyek 20130603
                    pstReservation.setLong(FLD_TERM_OF_PAYMENT, reservation.getTermsOfPayment());
                    pstReservation.setString(FLD_GUARANTED_BY, reservation.getGuarantedBy());
                    pstReservation.setInt(FLD_STATUS_CUT_OF_DAYS, reservation.getStatusCutOfDay());
                    pstReservation.setInt(FLD_TYPE_OF_BOOKING, reservation.getTypeOfBooking());

                    pstReservation.update();

                    return reservation.getOID();



                }

            }catch(DBException dbe){

                throw dbe;

            }catch(Exception e){

                throw new DBException(new PstReservation(0),DBException.UNKNOWN);

            }

            return 0;

        }



        public static long deleteExc(long oid) throws DBException{

            try{

                PstReservation pstReservation = new PstReservation(oid);

                pstReservation.delete();

            }catch(DBException dbe){

                throw dbe;

            }catch(Exception e){

                throw new DBException(new PstReservation(0),DBException.UNKNOWN);

            }

            return oid;

        }



        public static Vector listAll(){

            return list(0, 500, "","");

        }



        public static Vector list(int limitStart,int recordToGet, String whereClause, String order){

            Vector lists = new Vector();

            DBResultSet dbrs = null;

            try {

                String sql = "SELECT * FROM " + TBL_RESERVATION;

		    if(recordToGet<=0)

			recordToGet=9000; // maximum of list is 200

                if(whereClause != null && whereClause.length() > 0)

                    sql = sql + " WHERE " + whereClause;

                if(order != null && order.length() > 0)

                    sql = sql + " ORDER BY " + order;

                if(limitStart == 0 && recordToGet == 0)

                    sql = sql + "";

                else

                    sql = sql + " LIMIT " + limitStart + ","+ recordToGet ;

                dbrs = DBHandler.execQueryResult(sql);

                ResultSet rs = dbrs.getResultSet();

                while(rs.next()) {

                    Reservation reservation = new Reservation();

                    resultToObject(rs, reservation);

                    lists.add(reservation);

                }

                

                

                

                rs.close();

                return lists;



            }catch(Exception e) {

                System.out.println(e);

            }finally {

                DBResultSet.close(dbrs);

            }

            return new Vector();

        }



        public static void resultToObject(ResultSet rs, Reservation reservation){

            try{

                reservation.setOID(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_RESERVATION_ID]));

                reservation.setTravelAgentId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_TRAVEL_AGENT_ID]));

                reservation.setMembershipId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_MEMBERSHIP_ID]));

                reservation.setRoomClassId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_ROOM_CLASS_ID]));

                reservation.setHotelRoomId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_HOTEL_ROOM_ID]));

                reservation.setCustomerId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_CUSTOMER_ID]));

                reservation.setSourceOfBooking(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_SOURCE_OF_BOOKING]));

                reservation.setSourceOfBookingSpec(rs.getString(PstReservation.fieldNames[PstReservation.FLD_SOURCE_OF_BOOKING_SPEC]));

                reservation.setCheckInDate(rs.getTimestamp(PstReservation.fieldNames[PstReservation.FLD_CHECK_IN_DATE]));

                reservation.setChekOutDate(rs.getTimestamp(PstReservation.fieldNames[PstReservation.FLD_CHEK_OUT_DATE]));

                reservation.setNumberOfNight(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_NUMBER_OF_NIGHT]));

                reservation.setNumAdult(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_NUM_ADULT]));

                reservation.setNumChild(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_NUM_CHILD]));

                reservation.setNumInfant(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_NUM_INFANT]));

                reservation.setSpecialRequirement(rs.getString(PstReservation.fieldNames[PstReservation.FLD_SPECIAL_REQUIREMENT]));

                reservation.setDiscountRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_DISCOUNT_RP]));

                reservation.setDiscount$(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_DISCOUNT_$]));

                reservation.setDepositRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_DEPOSIT_RP]));

                reservation.setDeposit$(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_DEPOSIT_$]));

                reservation.setReservationStatus(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_RESERVATION_STATUS]));

                reservation.setCancelReason(rs.getString(PstReservation.fieldNames[PstReservation.FLD_CANCEL_REASON]));

                reservation.setExtraBed(rs.getBoolean(PstReservation.fieldNames[PstReservation.FLD_EXTRA_BED]));

                reservation.setAdditionalChargeType(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_ADDITIONAL_CHARGE_TYPE]));

                reservation.setRoomRateRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_ROOM_RATE_RP]));

                reservation.setSurchargeRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_SURCHARGE_RP]));

                reservation.setCompulsoryRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_COMPULSORY_RP]));

                reservation.setExtraBedRateRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_EXTRA_BED_RATE_RP]));

                reservation.setAdditionalChargeRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_ADDITIONAL_CHARGE_RP]));

                reservation.setTotalAllRateRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_TOTAL_ALL_RATE_RP]));

                reservation.setRoomRate$(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_ROOM_RATE_$]));

                reservation.setSurcharge$(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_SURCHARGE_$]));

                reservation.setCompulsory$(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_COMPULSORY_$]));

                reservation.setExtraBedRate$(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_EXTRA_BED_RATE_$]));

                reservation.setAdditionalCharge$(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_ADDITIONAL_CHARGE_$]));

                reservation.setTotalAllRate$(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_TOTAL_ALL_RATE_$]));

                reservation.setIncomeRateRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_INCOME_RATE_RP]));

                reservation.setTaxRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_TAX_RP]));

                reservation.setServiceRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_SERVICE_RP]));

                reservation.setIncomeRate$(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_INCOME_RATE_$]));

                reservation.setTax$(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_TAX_$]));

                reservation.setService$(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_SERVICE_$]));

                reservation.setPaidRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_PAID_RP]));

                reservation.setPaid$(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_PAID_$]));

                reservation.setIncomeCalculationType(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_INCOME_CALCULATION_TYPE]));

                reservation.setExchangeRate(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_EXCHANGE_RATE]));

                reservation.setNote(rs.getString(PstReservation.fieldNames[PstReservation.FLD_NOTE]));

                reservation.setChargeCurrency(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_CHARGE_CURRENCY]));

                reservation.setParentId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_PARENT_ID]));

                reservation.setCustomerType(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_CUSTOMER_TYPE]));

                reservation.setMembershipUpgradeId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_MEMBERSHIP_UPGRADE_ID]));

                reservation.setMembershipCustomerId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_MEMBERSHIP_CUSTOMER_ID]));

                //reservation.setCIN(rs.getString(PstReservation.fieldNames[PstReservation.FLD_CIN]));

                reservation.setExchangeRciId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_EXCHANGE_RCI_ID]));

                reservation.setCancelFeeRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_CANCEL_FEE_RP]));

                reservation.setCancelFeeUsd(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_CANCEL_FEE_USD]));

                reservation.setTakeOverFromTravel(rs.getBoolean(PstReservation.fieldNames[PstReservation.FLD_TAKE_OVER_FROM_TRAVEL]));

                reservation.setResvClassification(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_RESV_CLASSIFICATION]));



                reservation.setBillingBreakfastId(rs.getLong(PstReservation.fieldNames[FLD_BILLING_BREAKFAST_ID]));

                reservation.setIncludeBreakfast(rs.getBoolean(PstReservation.fieldNames[FLD_INCLUDE_BREAKFAST]));

                reservation.setBreakfastPriceRp(rs.getDouble(PstReservation.fieldNames[FLD_BREAKFAST_PRICE_RP]));

                reservation.setBreakfastPriceUsd(rs.getDouble(PstReservation.fieldNames[FLD_BREAKFAST_PRICE_USD]));

                reservation.setWithCommision(rs.getBoolean(PstReservation.fieldNames[FLD_WITH_COMMISION]));

                reservation.setCommisionAmountRp(rs.getDouble(PstReservation.fieldNames[FLD_COMMISION_AMOUNT_RP]));

                reservation.setCommisionAmountUsd(rs.getDouble(PstReservation.fieldNames[FLD_COMMISION_AMOUNT_USD]));

                reservation.setCommisionContactId(rs.getLong(PstReservation.fieldNames[FLD_COMMISION_CONTACT_ID]));

                reservation.setCommisionStatus(rs.getInt(PstReservation.fieldNames[FLD_COMMISION_STATUS]));

                reservation.setInvNumber(rs.getString(PstReservation.fieldNames[FLD_INV_NUMBER]));



                reservation.setRoomChargePerNightRp(rs.getDouble(PstReservation.fieldNames[FLD_ROOM_CHARGE_PER_NIGHT_RP]));

                reservation.setRoomChargePerNightUsd(rs.getDouble(PstReservation.fieldNames[FLD_ROOM_CHARGE_PER_NIGHT_USD]));

                reservation.setBrfChargeIncludeInRoom(rs.getBoolean(PstReservation.fieldNames[FLD_BRF_CHARGE_INCLUDE_IN_ROOM]));

                reservation.setUseTravelContract(rs.getBoolean(PstReservation.fieldNames[FLD_USE_TRAVEL_CONTRACT]));



                reservation.setRsvGroupType(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_RSV_GROUP_TYPE]));

                reservation.setRsvType(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_RSV_TYPE]));

                reservation.setRsvParentGroupId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_RSV_PARENT_GROUP_ID]));



                reservation.setTravelPackageId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_TRAVEL_PACKAGE_ID]));

                reservation.setTravelPackTypeId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_TRAVEL_PACK_TYPE_ID]));

                reservation.setReservationNum(rs.getString(PstReservation.fieldNames[PstReservation.FLD_RESERVATION_NUM]));



                reservation.setContractId(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_CONTRACT_ID]));

                reservation.setDiscountType(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_DISCOUNT_TYPE]));

                reservation.setDiscountProcent(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_DISCOUNT_PROCENT]));

                reservation.setDiscountScope(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_DISCOUNT_SCOPE]));

                reservation.setTaxInclude(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_TAX_INCLUDE]));

                reservation.setServiceInclude(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_SERVICE_INCLUDE]));

                reservation.setNumNightDiscount(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_NUM_NIGHT_DISCOUNT]));

                reservation.setTotalDiscountRp(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_TOTAL_DISCOUNT_RP]));

                reservation.setTotalDiscountUsd(rs.getDouble(PstReservation.fieldNames[PstReservation.FLD_TOTAL_DISCOUNT_USD]));

                reservation.setNumberCounter(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_NUMBER_COUNTER]));



                reservation.setSurchargeInclude(rs.getBoolean(PstReservation.fieldNames[PstReservation.FLD_SURCHARGE_INCLUDE]));

                reservation.setCompulsoryInclude(rs.getBoolean(PstReservation.fieldNames[PstReservation.FLD_COMPULSORY_INCLUDE]));

                reservation.setNumNightCompliment(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_NUM_NIGHT_COMPLIMENT]));

                reservation.setComplimentType(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_COMPLIMENT_TYPE]));

                reservation.setExchangeType(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_EXCHANGE_TYPE]));

                reservation.setTypeInvoice(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_TYPE_INVOICE]));

                reservation.setIsTransfered(rs.getBoolean(PstReservation.fieldNames[PstReservation.FLD_IS_TRANSFERED]));

                

                reservation.setCustomType(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_CUSTOM_TYPE]));

                reservation.setCustomTypeSpec(rs.getString(PstReservation.fieldNames[PstReservation.FLD_CUSTOM_TYPE_SPEC]));
                reservation.setGuestName(rs.getString(PstReservation.fieldNames[PstReservation.FLD_GUEST_NAME]));
                if(1==2){try{reservation.setCancelDate(rs.getTimestamp(fieldNames[FLD_CANCEL_DATE]));
                reservation.setCancelInputOn(rs.getTimestamp(fieldNames[FLD_CANCEL_INPUT_ON]));}catch(Exception exc){}
                try{reservation.setStatusDoc(rs.getInt(fieldNames[FLD_STATUS_DOC]));}catch(Exception exc){}}
                reservation.setBookingCode(rs.getString(PstReservation.fieldNames[PstReservation.FLD_BOOKING_CODE]));

                //add opie-eyek20130603
                reservation.setTermsOfPayment(rs.getLong(PstReservation.fieldNames[PstReservation.FLD_TERM_OF_PAYMENT]));
                reservation.setGuarantedBy(rs.getString(PstReservation.fieldNames[PstReservation.FLD_GUARANTED_BY]));
                reservation.setStatusCutOfDay(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_STATUS_CUT_OF_DAYS]));
                reservation.setTypeOfBooking(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_TYPE_OF_BOOKING]));

                 //tax government

                reservation.setNumberNightReported(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_NUMBER_NIGHT_REPORTED]));

                reservation.setCheckoutDateReported(rs.getDate(PstReservation.fieldNames[PstReservation.FLD_CHECKOUT_DATE_REPORTED]));

                reservation.setStatusReported(rs.getInt(PstReservation.fieldNames[PstReservation.FLD_STATUS_REPORTED]));

                

                    Date intime = rs.getDate(PstReservation.fieldNames[PstReservation.FLD_CHECKIN_TIME]);

	            Date in = rs.getTime(PstReservation.fieldNames[PstReservation.FLD_CHECKIN_TIME]);

	            Date xin = new Date(intime.getYear(),intime.getMonth(),intime.getDate(),in.getHours(),in.getMinutes());                  

                reservation.setCheckInTime(xin);

                

                Date outtime = rs.getDate(PstReservation.fieldNames[PstReservation.FLD_CHECKOUT_TIME]);

	            Date out = rs.getTime(PstReservation.fieldNames[PstReservation.FLD_CHECKOUT_TIME]);

	            Date xout = new Date(outtime.getYear(),outtime.getMonth(),outtime.getDate(),out.getHours(),out.getMinutes());                    

                reservation.setCheckOutTime(xout);



                Date dt = rs.getDate(PstReservation.fieldNames[PstReservation.FLD_REG_DATE]);

	            Date tm = rs.getTime(PstReservation.fieldNames[PstReservation.FLD_REG_DATE]);

	            Date xDt = new Date(dt.getYear(),dt.getMonth(),dt.getDate(),tm.getHours(),tm.getMinutes());



                reservation.setRegDate(xDt);



            }catch(Exception e){ }

        }



        public static boolean checkOID(long reservationId){

            DBResultSet dbrs = null;

            boolean result = false;

            try{

                String sql = "SELECT * FROM " + TBL_RESERVATION + " WHERE " +

                PstReservation.fieldNames[PstReservation.FLD_RESERVATION_ID] + " = " + reservationId;



                dbrs = DBHandler.execQueryResult(sql);

                ResultSet rs = dbrs.getResultSet();



                while(rs.next()) { result = true; }

                rs.close();

            }catch(Exception e){

                System.out.println("err : "+e.toString());

            }finally{

                DBResultSet.close(dbrs);

                return result;

            }

        }



        public static int getCount(String whereClause){

            DBResultSet dbrs = null;

            try {

                String sql = "SELECT COUNT("+ PstReservation.fieldNames[PstReservation.FLD_RESERVATION_ID] + ") FROM " + TBL_RESERVATION;

                if(whereClause != null && whereClause.length() > 0)

                    sql = sql + " WHERE " + whereClause;

                dbrs = DBHandler.execQueryResult(sql);

                ResultSet rs = dbrs.getResultSet();



                int count = 0;

                while(rs.next()) { count = rs.getInt(1); }



                rs.close();

                return count;

            }catch(Exception e) {

                return 0;

            }finally {

                DBResultSet.close(dbrs);

            }

        }
	
	public static int getNextRsvNumber(){

            DBResultSet dbrs = null;

            int result = 0;

            try{

                String sql = "SELECT MAX("+fieldNames[FLD_NUMBER_COUNTER]+") FROM "+

                    TBL_RESERVATION;



                dbrs = DBHandler.execQueryResult(sql);

                ResultSet rs = dbrs.getResultSet();

                while(rs.next()){

                   	result = rs.getInt(1);

                }



                result = result + 1;

            }

            catch(Exception e){

                ;

            }

            finally{

                DBResultSet.close(dbrs);

            }



            return result;

        }


}
