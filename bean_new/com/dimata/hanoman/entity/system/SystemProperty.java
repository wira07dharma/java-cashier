/*

SystemProperty



update by Eka Ds at 8th jan 2003

inserting page properties



*/





package com.dimata.hanoman.entity.system;





import java.util.*;





public class SystemProperty extends com.dimata.qdep.entity.Entity

{

    

    private String name = "";

    private String value = "";

    

    

    /** value type: 

     * e.g. TEXT, INTEGER

     */

    private String valType = "";    



    

    /** display type : this type defind how this property will be displayed

     * to editing the data

     * e.g. SINGLE TEXT, MULTY TEXT, DROP DOWN. etc.

     */

    private String disType = "";    

    private String group = "";

    private String note = "";

    

    



    public SystemProperty()

    {

    }







    public String getName(){

        return name;

    }



    public void setName(String name){

        name = name.toUpperCase();        

        this.name = name.replace(' ','_');        

    }



    public String getValue(){

        return value;

    }



    public void setValue(String value){

        this.value = value;

    }



    public String getValueType(){

        return valType;

    }



    public void setValueType(String type){

        this.valType = type;

    }



    

    public String getDisplayType(){

        return disType;

    }



    public void setDisplayType(String type){

        this.disType = type;

    }



    public String getGroup(){

        return group;

    }



    public void setGroup(String gr){

        this.group = gr;

    }



    public String getNote(){

        return note;

    }



    public void setNote(String note){

        this.note = note;

    }



    /*

    @ eka

    this contanta used to define either this application will implement

    dispatch for sub contract or not

    */

    public static final boolean SYS_PROP_USED_SUBCONTRACT = false;



    /*  room number available for current system  */



    public static final int SYS_PROP_NUM_ROOM_LEVEL_01 = 25;

    public static final int SYS_PROP_NUM_ROOM_LEVEL_02 = 50;

    public static final int SYS_PROP_NUM_ROOM_LEVEL_03 = 75;

    public static final int SYS_PROP_NUM_ROOM_LEVEL_04 = 100;

    public static final int SYS_PROP_NUM_ROOM_LEVEL_05 = 125;



    /* percentage for tax print out => untuk orang pajak */

    public static final int SYS_PROP_TAX_PERCENTAGE_PRINT_OUT = 50;//50% adalah harga yang ditampilkan di print out tax



    public static final int AKHYATI     =   0;

    public static final int MASARI      =   1;

    public static final int SULUBAN     =   2;

    public static final int TEGALSARI   =   3;

    public static final int MARIO       =   4;









    /* jsp page displayed */



    private static final boolean DISABLED 	= false;  //will be disabled on jsp view

    private static final boolean ENABLED 	= true;   //will be enableed on jsp view





    /* billing cost  */

    public static final int SYS_PROP_COST_REGULAR 		= 0;

    public static final int SYS_PROP_COST_BREAK_DOWN 	= 1;



    /* billing currency */

    public static final int SYS_PROP_CURRENCY_USD 		= 0;

    public static final int SYS_PROP_CURRENCY_RP 		= 1;

    public static final int SYS_PROP_CURRENCY_MULTY 	= 2;



    /* inv with tax / service */

    public static final boolean SYS_PROP_WITH_TAX_AND_SERVICE  = true;

    public static final boolean SYS_PROP_WITHOUT_TAX_AND_SERVICE   = false;



    /* app exchange rate used */

    public static final int SYS_PROP_BOOKEEPING				= 0; //only use average exchange rate

    public static final int SYS_PROP_SELLING_AND_BUYING		= 1; //use selling and buying rate.









    	/* ---------------- DEFAULT PAGE FOR DEMO ----------------------------  */

/*

// WARNING .... DON'T DARE TO DELETE THIS PAGE PROPERTY, WITHOUT CONFIRMATION FROM EKA .... :))



    public static final String HEADER_HOTEL_NAME_IMAGE 	= "demo/hotelname.jpg";

    public static final String HEADER_HOTEL_LOGO			= "demo/headlogo.jpg";

    public static final String MAIN_LOGIN_IMAGE				= "demo/mainlogin.jpg";

    public static final String WELCOME_IMAGE				= "demo/welcome.jpg";

    public static final String HOTEL_NAME_HEADER_OFFLINE_TOP  	= "Your Hotel";

    public static final String HOTEL_NAME_HEADER_OFFLINE_BOTTOM	= "Accommodation";



    // reservation

    public static final boolean SYS_PROP_MODULE_RESERVATION  	= ENABLED;

    	// sub module reservation

    	public static final boolean SYS_PROP_SUB_RSV_ROOM_BLOCKING 		= ENABLED;

		public static final boolean SYS_PROP_SUB_RSV_RESERVATION_LIST 	= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_CHECK_IN 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_CHECK_OUT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_ROOM_TRANSFER 		= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_ROOM_RATE 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_PACKAGE 			= ENABLED;



    // in house

    public static final boolean SYS_PROP_MODULE_INHOUSE  	= ENABLED;

    	// sub module in house

        public static final boolean SYS_PROP_SUB_INH_CHECK_IN 			= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_CHECK_OUT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_ROOM_TRANSFER 		= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_EXPECT_DEPARTURE 	= ENABLED;



    // cashiering

    public static final boolean SYS_PROP_MODULE_CHASIERING	 	= ENABLED;

    	// sub module cashiering

    	public static final boolean SYS_PROP_SUB_CHS_BILLING_REC 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_INVOICE_LIST 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OUTSIDE_GUEST_BILLING 	= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OPERATOR_CALL 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OUTLET_SYSTEM 			= ENABLED;





    // housekeeping

    public static final boolean SYS_PROP_MODULE_HOUSEKEEPING  	= ENABLED;

    	// sub module housekeeping

    	public static final boolean SYS_PROP_SUB_HK_ROOM_STATUS  	= ENABLED;

        public static final boolean SYS_PROP_SUB_HK_HOTEL_PROJECT  	= ENABLED;

        public static final boolean SYS_PROP_SUB_HK_ROOM_REPORT  	= ENABLED;





    // membership

    public static final boolean SYS_PROP_MODULE_MEMBERSHIP  	= ENABLED;

    	// sub module membership

    	public static final boolean SYS_PROP_SUB_MEM_MEMBERSHIP_DATA  		= ENABLED;

        public static final boolean SYS_PROP_SUB_MEM_INSTALLMENT  			= ENABLED;

        public static final boolean SYS_PROP_SUB_MEM_MINTENANCE_FEE  		= ENABLED;

        public static final boolean SYS_PROP_SUB_MEM_MEMBER_EXCHANGE  		= ENABLED;

        public static final boolean SYS_PROP_SUB_MEM_MEMBER_RCI  			= ENABLED;

        public static final boolean SYS_PROP_SUB_MEM_YEARLY_ANNOUNCEMENT  	= ENABLED;





    // payroll

    public static final boolean SYS_PROP_MODULE_PAYROLL  		= ENABLED;

    	// sub module payroll

        public static final boolean  SYS_PROP_SUB_MODULE_PAYROLL_PPH21_PERCENT 	= ENABLED;





    // purchasing

    public static final boolean SYS_PROP_MODULE_PURCHASING  	= ENABLED;

    	// sub module purchasing

    	public static final boolean SYS_PROP_SUB_PUR_PR  			= ENABLED;

        	// sub sub module pr

        	public static final boolean SYS_PROP_SUB_PUR_PR_REGULAR  			= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_PR_KITCHEN  			= ENABLED;



        public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO  	= ENABLED;

        	// sub sub module set pr to po

        	public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO_REGULAR  	= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO_KITCHEN  	= ENABLED;



        public static final boolean SYS_PROP_SUB_PUR_PO  			= ENABLED;

            // sub sub module po

        	public static final boolean SYS_PROP_SUB_PUR_PO_REGULAR  			= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_PO_KITCHEN  			= ENABLED;



        public static final boolean SYS_PROP_SUB_PUR_REMAINDER  	= ENABLED;

        	// sub sub module remainder

        	public static final boolean SYS_PROP_SUB_PUR_REMAINDER_PR  	= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_REMAINDER_PO  	= ENABLED;



        public static final boolean SYS_PROP_SUB_PUR_RECEIVING  	= ENABLED;

        	// sub sub module receiving

        	public static final boolean SYS_PROP_SUB_PUR_RECEIVING_REGULAR  	= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_RECEIVING_KITCHEN  	= ENABLED;





    // material

    public static final boolean SYS_PROP_MODULE_MATERIAL 		= ENABLED;

    	// sub module material

    	public static final boolean SYS_PROP_SUB_MAT_DISPATCH_REQUEST 		= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_DISPATCH 				= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_DISPATCH_RECEIVE 		= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_MATERIAL_STOCK_LIST 	= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_STOCK_OPNAME 			= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_STOCK_REPORT 			= ENABLED;





    // datamanagement

    public static final boolean SYS_PROP_MODULE_DATA_MANAGEMENT = ENABLED;

    	// sub module data management

    	public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_DATA 		= ENABLED;

        	// sub sub module hotel data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_PROFILE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_SUR_COM 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_ROOM 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_ROOM_CLASS 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_GUEST_DATA 		= ENABLED;

        	// sub sub guest data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_GUEST_LIST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_NEW_GUEST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_PRINT_GUEST_DATA 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MEMBERSHIP 		= ENABLED;

        	// sub sub module membership

        	public static final boolean SYS_PROP_SUB_DATA_MAN_PACKAGE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BONUS_WEEK 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL 			= ENABLED;

        	// sub sub module material

        	public static final boolean SYS_PROP_SUB_DATA_MATERIAL_GROUP 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_LIST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_PERIODE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_LOCATION 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_PAYROLL 			= ENABLED;

        	// sub sub module payroll

        	public static final boolean SYS_PROP_SUB_DATA_MAN_EMPLOYEE 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_RELIGION 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MARITAL 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_DEPARTMENT 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_POSITION 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_INIT_ALLOWANCE 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MASTER_DATA 		= ENABLED;

        	// sub sub module master data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_COUNTRY 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_EXCHANGE_RATE 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BILLING_TYPE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MASTER_TYPE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BILLING_GROUP			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_TRAVEL_AGENT	= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_CORPORATE		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_GUIDE			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_SUPPLIER		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_OUTLET_SYSTEM 	= ENABLED;

        	// sub sub module outlet system

        	public static final boolean SYS_PROP_SUB_DATA_MAN_OUTET_COVER 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_OTHER_PAYMENT 	= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_SYSTEM_ADMIN 		= ENABLED;

        	// sub sub module system admin

        	public static final boolean SYS_PROP_SUB_DATA_MAN_USER 					= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_GROUP 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_PRIVILEGE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_SYSTEM_PROPERTY 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MAN_ACTIVITY 		= ENABLED;

        	// sub sub module system admin

        	public static final boolean SYS_PROP_SUB_DATA_MAN_BACK_UP_SERVICE				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MONITORING_SERVICE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_QUEST_QUERY		 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_RESERVATION_STATISTIC 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_PORT_INTERFACE 	= ENABLED;

        	// sub sub module port & interface

        	public static final boolean SYS_PROP_SUB_DATA_MAN_SETUP 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_TELEPHONE_CHARGE 	= ENABLED;



    // ledger

    public static final boolean SYS_PROP_MODULE_LEDGER 			= ENABLED;

    	// sub module ledger

    	public static final boolean SYS_PROP_SUB_ROOM_LEDGER 					= ENABLED;

        public static final boolean SYS_PROP_SUB_BILLNG_LEDGER 					= ENABLED;

        public static final boolean SYS_PROP_SUB_EXPENSE_LEDGER 				= ENABLED;

        public static final boolean SYS_PROP_SUB_DAILY_SALES_REPORT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_DAILY_SUMMARY_REPORT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_SALES_POTENSIAL_REPORT 		= ENABLED;

        public static final boolean SYS_PROP_SUB_ROOM_REPORT 		= ENABLED;



    // is ADDNEW PO directly without PR permitted, if permit --> ENABLED, otherwise --> DISABLED

    public static final boolean SYS_PROP_ADDPO_DIRECTLY			= ENABLED;



    // what kind of billing cost calculation on the application (regular, or break down)

	public static final int SYS_PROP_APP_BILLING_ITEM_COST 					=  SYS_PROP_COST_BREAK_DOWN;



	// what kind of billing currency used on the application (USD only, RP only, use both)

    public static final int SYS_PROP_APP_BILLING_CURRENCY 		=  SYS_PROP_CURRENCY_MULTY;



	// invoice with tax / service view

    public static final boolean SYS_PROP_INVOICE_TAX_AND_SERVICE  = SYS_PROP_WITH_TAX_AND_SERVICE;



	//exchange rate type

    public static final int SYS_PROP_EXCHANGE_RATE_TYPE 		= SYS_PROP_SELLING_AND_BUYING;



	//billing price is changeable

    public static final boolean SYS_PROP_BILLING_PRICE_CHANGEABLE 		= true;



	//print out payment history

    public static final boolean SYS_PROP_PRINT_OUT_PAYMENT_HISTORY 		= true;



	//input billing dilakukan secara gabungan & random(gabungan/per billing)

    public static final boolean SYS_PROP_INPUT_BILLING_RANDOM 		= true;



	//print invoice murni hanya Rp. tanpa USD

    public static final boolean SYS_PROP_INVOICE_PURELY_RP 		= false;



*/





    	/* ---------------- DEFAULT PAGE FOR AKHYATI ----------------------------  */



// WARNING .... DON'T DARE TO DELETE THIS PAGE PROPERTY, WITHOUT CONFIRMATION FROM EKA .... :))



    public static final String HEADER_HOTEL_NAME_IMAGE 	= "akhyati/hotelname.jpg";

    public static final String HEADER_HOTEL_LOGO			= "akhyati/headlogo.jpg";

    public static final String MAIN_LOGIN_IMAGE				= "akhyati/mainlogin.jpg";

    public static final String WELCOME_IMAGE				= "akhyati/welcome.jpg";

    public static final String HOTEL_NAME_HEADER_OFFLINE_TOP  	= "Akhyati";

    public static final String HOTEL_NAME_HEADER_OFFLINE_BOTTOM	= "Villas & SPA";



    // reservation

    public static final boolean SYS_PROP_MODULE_RESERVATION  	= ENABLED;

    	// sub module reservation

    	public static final boolean SYS_PROP_SUB_RSV_ROOM_BLOCKING 		= ENABLED;

		public static final boolean SYS_PROP_SUB_RSV_RESERVATION_LIST 	= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_CHECK_IN 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_CHECK_OUT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_ROOM_TRANSFER 		= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_ROOM_RATE 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_PACKAGE 			= ENABLED;



    // in house

    public static final boolean SYS_PROP_MODULE_INHOUSE  	= ENABLED;

    	// sub module in house

        public static final boolean SYS_PROP_SUB_INH_CHECK_IN 			= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_CHECK_OUT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_ROOM_TRANSFER 		= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_EXPECT_DEPARTURE 	= ENABLED;





    // cashiering

    public static final boolean SYS_PROP_MODULE_CHASIERING	 	= ENABLED;

    	// sub module cashiering 

    	public static final boolean SYS_PROP_SUB_CHS_BILLING_REC 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_INVOICE_LIST 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OUTSIDE_GUEST_BILLING 	= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OPERATOR_CALL 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OUTLET_SYSTEM 			= DISABLED;





    // housekeeping 

    public static final boolean SYS_PROP_MODULE_HOUSEKEEPING  	= ENABLED;

    	// sub module housekeeping 

    	public static final boolean SYS_PROP_SUB_HK_ROOM_STATUS  	= ENABLED;

        public static final boolean SYS_PROP_SUB_HK_HOTEL_PROJECT  	= ENABLED;

        public static final boolean SYS_PROP_SUB_HK_ROOM_REPORT  	= ENABLED;





    // membership 

    public static final boolean SYS_PROP_MODULE_MEMBERSHIP  	= DISABLED;

    	// sub module membership 

    	public static final boolean SYS_PROP_SUB_MEM_MEMBERSHIP_DATA  		= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_INSTALLMENT  			= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_MINTENANCE_FEE  		= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_MEMBER_EXCHANGE  		= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_MEMBER_RCI  			= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_YEARLY_ANNOUNCEMENT  	= DISABLED;





    // payroll 

    public static final boolean SYS_PROP_MODULE_PAYROLL  		= DISABLED;

    	// sub module payroll

        public static final boolean  SYS_PROP_SUB_MODULE_PAYROLL_PPH21_PERCENT 	= DISABLED;





    // purchasing 

    public static final boolean SYS_PROP_MODULE_PURCHASING  	= DISABLED;

    	// sub module purchasing 

    	public static final boolean SYS_PROP_SUB_PUR_PR  			= DISABLED;

        	// sub sub module pr 

        	public static final boolean SYS_PROP_SUB_PUR_PR_REGULAR  			= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_PR_KITCHEN  			= DISABLED;



        public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO  	= DISABLED;

        	// sub sub module set pr to po 

        	public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO_REGULAR  	= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO_KITCHEN  	= DISABLED;



        public static final boolean SYS_PROP_SUB_PUR_PO  			= DISABLED;

            // sub sub module po 

        	public static final boolean SYS_PROP_SUB_PUR_PO_REGULAR  			= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_PO_KITCHEN  			= DISABLED;



        public static final boolean SYS_PROP_SUB_PUR_REMAINDER  	= DISABLED;

        	// sub sub module remainder 

        	public static final boolean SYS_PROP_SUB_PUR_REMAINDER_PR  	= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_REMAINDER_PO  	= DISABLED;



        public static final boolean SYS_PROP_SUB_PUR_RECEIVING  	= DISABLED;

        	// sub sub module receiving 

        	public static final boolean SYS_PROP_SUB_PUR_RECEIVING_REGULAR  	= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_RECEIVING_KITCHEN  	= DISABLED;





    // material 

    public static final boolean SYS_PROP_MODULE_MATERIAL 		= DISABLED;

    	// sub module material 

    	public static final boolean SYS_PROP_SUB_MAT_DISPATCH_REQUEST 		= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_DISPATCH 				= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_DISPATCH_RECEIVE 		= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_MATERIAL_STOCK_LIST 	= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_STOCK_OPNAME 			= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_STOCK_REPORT 			= DISABLED;





    // datamanagement 

    public static final boolean SYS_PROP_MODULE_DATA_MANAGEMENT = ENABLED;

    	// sub module data management 

    	public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_DATA 		= ENABLED;

        	// sub sub module hotel data 

        	public static final boolean SYS_PROP_SUB_DATA_MAN_PROFILE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_SUR_COM 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_ROOM 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_ROOM_CLASS 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_GUEST_DATA 		= ENABLED;

        	// sub sub guest data 

        	public static final boolean SYS_PROP_SUB_DATA_MAN_GUEST_LIST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_NEW_GUEST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_PRINT_GUEST_DATA 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MEMBERSHIP 		= DISABLED;

        	// sub sub module membership 

        	public static final boolean SYS_PROP_SUB_DATA_MAN_PACKAGE 			= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BONUS_WEEK 		= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL 			= DISABLED;

        	// sub sub module material 

        	public static final boolean SYS_PROP_SUB_DATA_MATERIAL_GROUP 				= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_LIST 			= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_PERIODE 			= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_LOCATION 		= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_PAYROLL 			= ENABLED;

        	// sub sub module payroll 

        	public static final boolean SYS_PROP_SUB_DATA_MAN_EMPLOYEE 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_RELIGION 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MARITAL 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_DEPARTMENT 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_POSITION 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_INIT_ALLOWANCE 		= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MASTER_DATA 		= ENABLED;

        	// sub sub module master data 

        	public static final boolean SYS_PROP_SUB_DATA_MAN_COUNTRY 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_EXCHANGE_RATE 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BILLING_TYPE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MASTER_TYPE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BILLING_GROUP			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_TRAVEL_AGENT	= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_CORPORATE		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_GUIDE			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_SUPPLIER		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_OUTLET_SYSTEM 	= DISABLED;

        	// sub sub module outlet system 

        	public static final boolean SYS_PROP_SUB_DATA_MAN_OUTET_COVER 		= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_OTHER_PAYMENT 	= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_SYSTEM_ADMIN 		= ENABLED;

        	// sub sub module system admin 

        	public static final boolean SYS_PROP_SUB_DATA_MAN_USER 					= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_GROUP 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_PRIVILEGE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_SYSTEM_PROPERTY 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MAN_ACTIVITY 		= ENABLED;

        	// sub sub module system admin 

        	public static final boolean SYS_PROP_SUB_DATA_MAN_BACK_UP_SERVICE				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MONITORING_SERVICE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_QUEST_QUERY		 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_RESERVATION_STATISTIC 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_PORT_INTERFACE 	= ENABLED;

        	// sub sub module port & interface 

        	public static final boolean SYS_PROP_SUB_DATA_MAN_SETUP 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_TELEPHONE_CHARGE 	= ENABLED;



    // ledger 

    public static final boolean SYS_PROP_MODULE_LEDGER 			= ENABLED;

    	// sub module ledger 

    	public static final boolean SYS_PROP_SUB_ROOM_LEDGER 					= ENABLED;

        public static final boolean SYS_PROP_SUB_BILLNG_LEDGER 					= ENABLED;

        public static final boolean SYS_PROP_SUB_EXPENSE_LEDGER 				= DISABLED;

        public static final boolean SYS_PROP_SUB_DAILY_SALES_REPORT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_DAILY_SUMMARY_REPORT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_SALES_POTENSIAL_REPORT 		= ENABLED;

        public static final boolean SYS_PROP_SUB_ROOM_REPORT 		= ENABLED;



    public static final boolean SYS_PROP_MODULE_NIGHT_AUDIT = ENABLED;



    // is ADDNEW PO directly without PR permitted, if permit --> ENABLED, otherwise --> DISABLED

    public static final boolean SYS_PROP_ADDPO_DIRECTLY			= ENABLED;



    // what kind of billing cost calculation on the application (regular, or break down)

	public static final int SYS_PROP_APP_BILLING_ITEM_COST 					=  SYS_PROP_COST_BREAK_DOWN;



	// what kind of billing currency used on the application (USD only, RP only, use both)

    //public static final int SYS_PROP_APP_BILLING_CURRENCY 		=  SYS_PROP_CURRENCY_RP;



    public static final int SYS_PROP_APP_BILLING_CURRENCY 		=  SYS_PROP_CURRENCY_MULTY;



	// invoice with tax / service view

    public static final boolean SYS_PROP_INVOICE_TAX_AND_SERVICE  = SYS_PROP_WITHOUT_TAX_AND_SERVICE;



	//exchange rate type

    public static final int SYS_PROP_EXCHANGE_RATE_TYPE 		= SYS_PROP_BOOKEEPING;



    //billing price is changeable

    public static final boolean SYS_PROP_BILLING_PRICE_CHANGEABLE 	= true;



    public static final int SYS_PROP_REGISTRATION_FORM = AKHYATI;



	//print out payment history

    public static final boolean SYS_PROP_PRINT_OUT_PAYMENT_HISTORY 		= false;



	//input billing random(gabungan/per billing)

    public static final boolean SYS_PROP_INPUT_BILLING_RANDOM 		= false;



	//print invoice murni hanya Rp. tanpa USD

    public static final boolean SYS_PROP_INVOICE_PURELY_RP 		= false;



    //print invoice ditampilkan nama hotelnya

    public static final boolean SYS_PROP_INVOICE_HOTEL_NAME_VIEW 		= false;



 	/* ---------------------------- END OF DEFAULT PAGE -------------------------------- */













    /*--------------------------PAGE PROPERTY FOR BALI MASARI RESORT ----------------------*/



/*

    public static final String HEADER_HOTEL_NAME_IMAGE 		= "masari/hotelname.jpg";

    public static final String HEADER_HOTEL_LOGO			= "masari/headlogo.jpg";

    public static final String MAIN_LOGIN_IMAGE				= "masari/mainlogin.jpg";

    public static final String WELCOME_IMAGE				= "masari/welcome.jpg";

    public static final String HOTEL_NAME_HEADER_OFFLINE_TOP  	= "Bali Masari";

    public static final String HOTEL_NAME_HEADER_OFFLINE_BOTTOM	= "Resort";



	// reservation

    public static final boolean SYS_PROP_MODULE_RESERVATION  	= ENABLED;

    	// sub module reservation

    	public static final boolean SYS_PROP_SUB_RSV_ROOM_BLOCKING 		= ENABLED;

		public static final boolean SYS_PROP_SUB_RSV_RESERVATION_LIST 	= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_CHECK_IN 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_CHECK_OUT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_ROOM_TRANSFER 		= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_ROOM_RATE 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_PACKAGE 			= DISABLED;



    // in house

    public static final boolean SYS_PROP_MODULE_INHOUSE  	= ENABLED;

    	// sub module in house

        public static final boolean SYS_PROP_SUB_INH_CHECK_IN 			= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_CHECK_OUT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_ROOM_TRANSFER 		= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_EXPECT_DEPARTURE 	= ENABLED;



    // cashiering

    public static final boolean SYS_PROP_MODULE_CHASIERING	 	= ENABLED;

    	// sub module cashiering

    	public static final boolean SYS_PROP_SUB_CHS_BILLING_REC 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_INVOICE_LIST 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OUTSIDE_GUEST_BILLING 	= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OPERATOR_CALL 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OUTLET_SYSTEM 						= DISABLED;





    // housekeeping

    public static final boolean SYS_PROP_MODULE_HOUSEKEEPING  	= ENABLED;

    	// sub module housekeeping

    	public static final boolean SYS_PROP_SUB_HK_ROOM_STATUS  	= ENABLED;

        public static final boolean SYS_PROP_SUB_HK_HOTEL_PROJECT  	= ENABLED;

        public static final boolean SYS_PROP_SUB_HK_ROOM_REPORT  	= ENABLED;





    // membership

    public static final boolean SYS_PROP_MODULE_MEMBERSHIP  	= ENABLED;

    	// sub module membership

    	public static final boolean SYS_PROP_SUB_MEM_MEMBERSHIP_DATA  		= ENABLED;

        public static final boolean SYS_PROP_SUB_MEM_INSTALLMENT  			= ENABLED;

        public static final boolean SYS_PROP_SUB_MEM_MINTENANCE_FEE  		= ENABLED;

        public static final boolean SYS_PROP_SUB_MEM_MEMBER_EXCHANGE  		= ENABLED;

        public static final boolean SYS_PROP_SUB_MEM_MEMBER_RCI  			= ENABLED;

        public static final boolean SYS_PROP_SUB_MEM_YEARLY_ANNOUNCEMENT  	= ENABLED;





    // payroll

    public static final boolean SYS_PROP_MODULE_PAYROLL  		= ENABLED;

    	// sub module payroll

        public static final boolean  SYS_PROP_SUB_MODULE_PAYROLL_PPH21_PERCENT = ENABLED;





    // purchasing

    public static final boolean SYS_PROP_MODULE_PURCHASING  	= ENABLED;

    	// sub module purchasing

    	public static final boolean SYS_PROP_SUB_PUR_PR  			= ENABLED;

        	// sub sub module pr

        	public static final boolean SYS_PROP_SUB_PUR_PR_REGULAR  			= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_PR_KITCHEN  			= ENABLED;



        public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO  	= ENABLED;

        	// sub sub module set pr to po

        	public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO_REGULAR  	= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO_KITCHEN  	= ENABLED;



        public static final boolean SYS_PROP_SUB_PUR_PO  			= ENABLED;

            // sub sub module po

        	public static final boolean SYS_PROP_SUB_PUR_PO_REGULAR  			= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_PO_KITCHEN  			= ENABLED;



        public static final boolean SYS_PROP_SUB_PUR_REMAINDER  	= ENABLED;

        	// sub sub module remainder

        	public static final boolean SYS_PROP_SUB_PUR_REMAINDER_PR  	= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_REMAINDER_PO  	= ENABLED;



        public static final boolean SYS_PROP_SUB_PUR_RECEIVING  	= ENABLED;

        	// sub sub module receiving

        	public static final boolean SYS_PROP_SUB_PUR_RECEIVING_REGULAR  	= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_RECEIVING_KITCHEN  	= ENABLED;





    // material

    public static final boolean SYS_PROP_MODULE_MATERIAL 		= ENABLED;

    	// sub module material

    	public static final boolean SYS_PROP_SUB_MAT_DISPATCH_REQUEST 		= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_DISPATCH 				= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_DISPATCH_RECEIVE 		= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_MATERIAL_STOCK_LIST 	= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_STOCK_OPNAME 			= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_STOCK_REPORT 			= ENABLED;





    // datamanagement

    public static final boolean SYS_PROP_MODULE_DATA_MANAGEMENT = ENABLED;

    	// sub module data management

    	public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_DATA 		= ENABLED;

        	// sub sub module hotel data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_PROFILE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_SUR_COM 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_ROOM 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_ROOM_CLASS 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_GUEST_DATA 		= ENABLED;

        	// sub sub guest data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_GUEST_LIST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_NEW_GUEST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_PRINT_GUEST_DATA 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MEMBERSHIP 		= ENABLED;

        	// sub sub module membership

        	public static final boolean SYS_PROP_SUB_DATA_MAN_PACKAGE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BONUS_WEEK 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL 			= ENABLED;

        	// sub sub module material

        	public static final boolean SYS_PROP_SUB_DATA_MATERIAL_GROUP 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_LIST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_PERIODE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_LOCATION 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_PAYROLL 			= ENABLED;

        	// sub sub module payroll

        	public static final boolean SYS_PROP_SUB_DATA_MAN_EMPLOYEE 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_RELIGION 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MARITAL 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_DEPARTMENT 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_POSITION 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_INIT_ALLOWANCE 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MASTER_DATA 		= ENABLED;

        	// sub sub module master data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_COUNTRY 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_EXCHANGE_RATE 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BILLING_TYPE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MASTER_TYPE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BILLING_GROUP			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_TRAVEL_AGENT	= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_CORPORATE		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_GUIDE			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_SUPPLIER		= ENABLED;





        public static final boolean SYS_PROP_SUB_DATA_MAN_OUTLET_SYSTEM 							= DISABLED;

        	// sub sub module outlet system

        	public static final boolean SYS_PROP_SUB_DATA_MAN_OUTET_COVER 							= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_OTHER_PAYMENT 						= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_SYSTEM_ADMIN 		= ENABLED;

        	// sub sub module system admin

        	public static final boolean SYS_PROP_SUB_DATA_MAN_USER 					= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_GROUP 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_PRIVILEGE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_SYSTEM_PROPERTY 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MAN_ACTIVITY 		= ENABLED;

        	// sub sub module system admin

        	public static final boolean SYS_PROP_SUB_DATA_MAN_BACK_UP_SERVICE				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MONITORING_SERVICE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_QUEST_QUERY		 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_RESERVATION_STATISTIC 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_PORT_INTERFACE 	= ENABLED;

        	// sub sub module port & interface

        	public static final boolean SYS_PROP_SUB_DATA_MAN_SETUP 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_TELEPHONE_CHARGE 	= ENABLED;



    // ledger

    public static final boolean SYS_PROP_MODULE_LEDGER 			= ENABLED;

    	// sub module ledger

    	public static final boolean SYS_PROP_SUB_ROOM_LEDGER 					= ENABLED;

        public static final boolean SYS_PROP_SUB_BILLNG_LEDGER 					= ENABLED;

        public static final boolean SYS_PROP_SUB_EXPENSE_LEDGER 				= ENABLED;

        public static final boolean SYS_PROP_SUB_DAILY_SALES_REPORT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_DAILY_SUMMARY_REPORT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_SALES_POTENSIAL_REPORT 		= DISABLED;

        public static final boolean SYS_PROP_SUB_ROOM_REPORT 		= ENABLED;



	public static final boolean SYS_PROP_MODULE_NIGHT_AUDIT = ENABLED;



    // is ADDNEW PO directly without PR permitted, if permit --> ENABLED, otherwise --> DISABLED

    public static final boolean SYS_PROP_ADDPO_DIRECTLY 			= ENABLED;



    // what kind of billing cost calculation on the application (regular, or break down)

	public static final int SYS_PROP_APP_BILLING_ITEM_COST 					=  SYS_PROP_COST_REGULAR;



	// what kind of billing currency used on the application (USD only, RP only, use both)

    public static final int SYS_PROP_APP_BILLING_CURRENCY 		=  SYS_PROP_CURRENCY_RP;



    // invoice with tax / service view

    public static final boolean SYS_PROP_INVOICE_TAX_AND_SERVICE  = SYS_PROP_WITHOUT_TAX_AND_SERVICE;



    //exchange rate type

    public static final int SYS_PROP_EXCHANGE_RATE_TYPE 		= SYS_PROP_BOOKEEPING;



	//billing price is changeable

    public static final boolean SYS_PROP_BILLING_PRICE_CHANGEABLE 		= false;



    public static final int SYS_PROP_REGISTRATION_FORM = MASARI;



	//print out payment history

	public static final boolean SYS_PROP_PRINT_OUT_PAYMENT_HISTORY 		= true;



	//input billing random(gabungan/per billing)

    public static final boolean SYS_PROP_INPUT_BILLING_RANDOM 		= true;



	//print invoice murni hanya Rp. tanpa USD

    public static final boolean SYS_PROP_INVOICE_PURELY_RP 		= false;



	//print invoice ditampilkan nama hotelnya

    public static final boolean SYS_PROP_INVOICE_HOTEL_NAME_VIEW 		= false;



*/

    /* -------------------------------- END OF BALI MASARI PAGE PROPERTY ------------------------- */

















    /* ----------------------------SULUBAN PAGE PROPERTY ------------------------------ */



/*



	public static final String HEADER_HOTEL_NAME_IMAGE 		= "suluban/hotelname.jpg";

    public static final String HEADER_HOTEL_LOGO			= "suluban/headlogo.jpg";

    public static final String MAIN_LOGIN_IMAGE				= "suluban/mainlogin.jpg";

    public static final String WELCOME_IMAGE				= "suluban/welcome.jpg";

    public static final String HOTEL_NAME_HEADER_OFFLINE_TOP  	= "Blue Point Bay";

    public static final String HOTEL_NAME_HEADER_OFFLINE_BOTTOM	= "The Villas & Spa";



  	// reservation

    public static final boolean SYS_PROP_MODULE_RESERVATION  	= ENABLED;

    	// sub module reservation

    	public static final boolean SYS_PROP_SUB_RSV_ROOM_BLOCKING 		= ENABLED;

		public static final boolean SYS_PROP_SUB_RSV_RESERVATION_LIST 	= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_CHECK_IN 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_CHECK_OUT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_ROOM_TRANSFER 		= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_ROOM_RATE 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_PACKAGE 			= DISABLED;



    // in house

    public static final boolean SYS_PROP_MODULE_INHOUSE  	= ENABLED;

    	// sub module in house

        public static final boolean SYS_PROP_SUB_INH_CHECK_IN 			= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_CHECK_OUT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_ROOM_TRANSFER 		= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_EXPECT_DEPARTURE 	= ENABLED;



    // cashiering

    public static final boolean SYS_PROP_MODULE_CHASIERING	 	= ENABLED;

    	// sub module cashiering

    	public static final boolean SYS_PROP_SUB_CHS_BILLING_REC 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_INVOICE_LIST 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OUTSIDE_GUEST_BILLING 	= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OPERATOR_CALL 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OUTLET_SYSTEM 			= ENABLED;





    // housekeeping

    public static final boolean SYS_PROP_MODULE_HOUSEKEEPING  	= ENABLED;

    	// sub module housekeeping

    	public static final boolean SYS_PROP_SUB_HK_ROOM_STATUS  	= ENABLED;

        public static final boolean SYS_PROP_SUB_HK_HOTEL_PROJECT  	= ENABLED;

        public static final boolean SYS_PROP_SUB_HK_ROOM_REPORT  	= ENABLED;





    // membership

    public static final boolean SYS_PROP_MODULE_MEMBERSHIP  							= DISABLED;

    	// sub module membership

    	public static final boolean SYS_PROP_SUB_MEM_MEMBERSHIP_DATA  					= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_INSTALLMENT  						= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_MINTENANCE_FEE  					= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_MEMBER_EXCHANGE  					= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_MEMBER_RCI  						= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_YEARLY_ANNOUNCEMENT  				= DISABLED;





    // payroll

    public static final boolean SYS_PROP_MODULE_PAYROLL  		= ENABLED;

    	// sub module payroll

        public static final boolean  SYS_PROP_SUB_MODULE_PAYROLL_PPH21_PERCENT 			= DISABLED;



    // purchasing

    public static final boolean SYS_PROP_MODULE_PURCHASING  	= ENABLED;

    	// sub module purchasing

    	public static final boolean SYS_PROP_SUB_PUR_PR  			= ENABLED;

        	// sub sub module pr

        	public static final boolean SYS_PROP_SUB_PUR_PR_REGULAR  			= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_PR_KITCHEN  			= ENABLED;



        public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO  	= ENABLED;

        	// sub sub module set pr to po

        	public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO_REGULAR  	= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO_KITCHEN  	= ENABLED;



        public static final boolean SYS_PROP_SUB_PUR_PO  			= ENABLED;

            // sub sub module po

        	public static final boolean SYS_PROP_SUB_PUR_PO_REGULAR  			= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_PO_KITCHEN  			= ENABLED;



        public static final boolean SYS_PROP_SUB_PUR_REMAINDER  	= ENABLED;

        	// sub sub module remainder

        	public static final boolean SYS_PROP_SUB_PUR_REMAINDER_PR  	= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_REMAINDER_PO  	= ENABLED;



        public static final boolean SYS_PROP_SUB_PUR_RECEIVING  	= ENABLED;

        	// sub sub module receiving

        	public static final boolean SYS_PROP_SUB_PUR_RECEIVING_REGULAR  	= ENABLED;

            public static final boolean SYS_PROP_SUB_PUR_RECEIVING_KITCHEN  	= ENABLED;





    // material

    public static final boolean SYS_PROP_MODULE_MATERIAL 		= ENABLED;

    	// sub module material

    	public static final boolean SYS_PROP_SUB_MAT_DISPATCH_REQUEST 		= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_DISPATCH 				= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_DISPATCH_RECEIVE 		= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_MATERIAL_STOCK_LIST 	= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_STOCK_OPNAME 			= ENABLED;

        public static final boolean SYS_PROP_SUB_MAT_STOCK_REPORT 			= ENABLED;





    // datamanagement

    public static final boolean SYS_PROP_MODULE_DATA_MANAGEMENT = ENABLED;

    	// sub module data management

    	public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_DATA 		= ENABLED;

        	// sub sub module hotel data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_PROFILE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_SUR_COM 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_ROOM 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_ROOM_CLASS 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_GUEST_DATA 		= ENABLED;

        	// sub sub guest data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_GUEST_LIST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_NEW_GUEST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_PRINT_GUEST_DATA 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MEMBERSHIP 								= DISABLED;

        	// sub sub module membership

        	public static final boolean SYS_PROP_SUB_DATA_MAN_PACKAGE 								= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BONUS_WEEK 							= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL 			= ENABLED;

        	// sub sub module material

        	public static final boolean SYS_PROP_SUB_DATA_MATERIAL_GROUP 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_LIST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_PERIODE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_LOCATION 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_PAYROLL 			= ENABLED;

        	// sub sub module payroll

        	public static final boolean SYS_PROP_SUB_DATA_MAN_EMPLOYEE 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_RELIGION 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MARITAL 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_DEPARTMENT 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_POSITION 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_INIT_ALLOWANCE 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MASTER_DATA 		= ENABLED;

        	// sub sub module master data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_COUNTRY 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_EXCHANGE_RATE 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BILLING_TYPE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MASTER_TYPE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BILLING_GROUP			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_TRAVEL_AGENT	= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_CORPORATE		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_GUIDE						= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_SUPPLIER		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_OUTLET_SYSTEM 	= ENABLED;

        	// sub sub module outlet system

        	public static final boolean SYS_PROP_SUB_DATA_MAN_OUTET_COVER 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_OTHER_PAYMENT 	= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_SYSTEM_ADMIN 		= ENABLED;

        	// sub sub module system admin

        	public static final boolean SYS_PROP_SUB_DATA_MAN_USER 					= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_GROUP 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_PRIVILEGE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_SYSTEM_PROPERTY 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MAN_ACTIVITY 		= ENABLED;

        	// sub sub module system admin

        	public static final boolean SYS_PROP_SUB_DATA_MAN_BACK_UP_SERVICE				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MONITORING_SERVICE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_QUEST_QUERY		 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_RESERVATION_STATISTIC 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_PORT_INTERFACE 	= ENABLED;

        	// sub sub module port & interface

        	public static final boolean SYS_PROP_SUB_DATA_MAN_SETUP 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_TELEPHONE_CHARGE 	= ENABLED;



    // ledger

    public static final boolean SYS_PROP_MODULE_LEDGER 			= ENABLED;

    	// sub module ledger

    	public static final boolean SYS_PROP_SUB_ROOM_LEDGER 					= ENABLED;

        public static final boolean SYS_PROP_SUB_BILLNG_LEDGER 					= ENABLED;

        public static final boolean SYS_PROP_SUB_EXPENSE_LEDGER 				= ENABLED;

        public static final boolean SYS_PROP_SUB_DAILY_SALES_REPORT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_DAILY_SUMMARY_REPORT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_SALES_POTENSIAL_REPORT 		= ENABLED;

        public static final boolean SYS_PROP_SUB_ROOM_REPORT 		= ENABLED;



	public static final boolean SYS_PROP_MODULE_NIGHT_AUDIT = ENABLED;



    // is ADDNEW PO directly without PR permitted, if permit --> ENABLED, otherwise --> DISABLED

/*    public static final boolean SYS_PROP_ADDPO_DIRECTLY 			= DISABLED;



    // what kind of billing cost calculation on the application (regular, or break down)

	public static final int SYS_PROP_APP_BILLING_ITEM_COST 					=  SYS_PROP_COST_BREAK_DOWN;



	// what kind of billing currency used on the application (USD only, RP only, use both)

    public static final int SYS_PROP_APP_BILLING_CURRENCY 		=  SYS_PROP_CURRENCY_MULTY;



	// invoice with tax / service view

    public static final boolean SYS_PROP_INVOICE_TAX_AND_SERVICE  = SYS_PROP_WITHOUT_TAX_AND_SERVICE;



	//exchange rate type

    public static final int SYS_PROP_EXCHANGE_RATE_TYPE 		= SYS_PROP_SELLING_AND_BUYING;



    public static final int SYS_PROP_REGISTRATION_FORM = SULUBAN;



	//print out payment history

    public static final boolean SYS_PROP_PRINT_OUT_PAYMENT_HISTORY 		= true;



	//billing price is changeable

    public static final boolean SYS_PROP_BILLING_PRICE_CHANGEABLE 		= false;



	//input billing random(gabungan/per billing)

    public static final boolean SYS_PROP_INPUT_BILLING_RANDOM 		= true;



	//print invoice murni hanya Rp. tanpa USD

    public static final boolean SYS_PROP_INVOICE_PURELY_RP 		= false;



	//print invoice ditampilkan nama hotelnya

    public static final boolean SYS_PROP_INVOICE_HOTEL_NAME_VIEW 		= false;



*/







    /* -------------sementara contek punya masari--------- */

/*

    // is ADDNEW PO directly without PR permitted, if permit --> ENABLED, otherwise --> DISABLED

    public static final boolean SYS_PROP_ADDPO_DIRECTLY 			= ENABLED;



    // what kind of billing cost calculation on the application (regular, or break down)

	public static final int SYS_PROP_APP_BILLING_ITEM_COST 					=  SYS_PROP_COST_REGULAR;



	// what kind of billing currency used on the application (USD only, RP only, use both)

    public static final int SYS_PROP_APP_BILLING_CURRENCY 		=  SYS_PROP_CURRENCY_RP;



    // invoice with tax / service view

    public static final boolean SYS_PROP_INVOICE_TAX_AND_SERVICE  = SYS_PROP_WITHOUT_TAX_AND_SERVICE;



    //exchange rate type

    public static final int SYS_PROP_EXCHANGE_RATE_TYPE 		= SYS_PROP_BOOKEEPING;

*/



    /* -------------------------------- END OF SULUBAN PAGE PROPERTY ------------------------- */























        	/* ---------------- DEFAULT PAGE FOR TEGALSARI ACCOMMODATION ----------------------------  */



	// WARNING .... DON'T DARE TO DELETE THIS PAGE PROPERTY, WITHOUT CONFIRMATION FROM EKA .... :))

   /*

    public static final String HEADER_HOTEL_NAME_IMAGE 	= "tegalsari/hotelname.jpg";

    public static final String HEADER_HOTEL_LOGO			= "tegalsari/headlogo.jpg";

    public static final String MAIN_LOGIN_IMAGE				= "tegalsari/mainlogin.jpg";

    public static final String WELCOME_IMAGE				= "tegalsari/welcome.jpg";

    public static final String HOTEL_NAME_HEADER_OFFLINE_TOP  	= "Tegalsari";

    public static final String HOTEL_NAME_HEADER_OFFLINE_BOTTOM	= "Accomodation";



    // reservation

    public static final boolean SYS_PROP_MODULE_RESERVATION  	= ENABLED;

    	// sub module reservation

    	public static final boolean SYS_PROP_SUB_RSV_ROOM_BLOCKING 		= ENABLED;

		public static final boolean SYS_PROP_SUB_RSV_RESERVATION_LIST 	= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_CHECK_IN 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_CHECK_OUT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_ROOM_TRANSFER 		= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_ROOM_RATE 			= ENABLED;

        public static final boolean SYS_PROP_SUB_RSV_PACKAGE 			= DISABLED;



    // in house

    public static final boolean SYS_PROP_MODULE_INHOUSE  	= ENABLED;

    	// sub module in house

        public static final boolean SYS_PROP_SUB_INH_CHECK_IN 			= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_CHECK_OUT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_ROOM_TRANSFER 		= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_EXPECT_DEPARTURE 	= ENABLED;







    // cashiering

    public static final boolean SYS_PROP_MODULE_CHASIERING	 	= ENABLED;

    	// sub module cashiering

    	public static final boolean SYS_PROP_SUB_CHS_BILLING_REC 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_INVOICE_LIST 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OUTSIDE_GUEST_BILLING 	= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OPERATOR_CALL 			= ENABLED;

        public static final boolean SYS_PROP_SUB_CHS_OUTLET_SYSTEM 			= DISABLED;





    // housekeeping

    public static final boolean SYS_PROP_MODULE_HOUSEKEEPING  	= ENABLED;

    	// sub module housekeeping

    	public static final boolean SYS_PROP_SUB_HK_ROOM_STATUS  	= ENABLED;

        public static final boolean SYS_PROP_SUB_HK_HOTEL_PROJECT  	= ENABLED;

        public static final boolean SYS_PROP_SUB_HK_ROOM_REPORT  	= ENABLED;





    // membership

    public static final boolean SYS_PROP_MODULE_MEMBERSHIP  	= DISABLED;

    	// sub module membership

    	public static final boolean SYS_PROP_SUB_MEM_MEMBERSHIP_DATA  		= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_INSTALLMENT  			= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_MINTENANCE_FEE  		= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_MEMBER_EXCHANGE  		= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_MEMBER_RCI  			= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_YEARLY_ANNOUNCEMENT  	= DISABLED;





    // payroll

    public static final boolean SYS_PROP_MODULE_PAYROLL  		= DISABLED;

    	// sub module payroll

        public static final boolean  SYS_PROP_SUB_MODULE_PAYROLL_PPH21_PERCENT 	= DISABLED;





    // purchasing

    public static final boolean SYS_PROP_MODULE_PURCHASING  	= DISABLED;

    	// sub module purchasing

    	public static final boolean SYS_PROP_SUB_PUR_PR  			= DISABLED;

        	// sub sub module pr

        	public static final boolean SYS_PROP_SUB_PUR_PR_REGULAR  			= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_PR_KITCHEN  			= DISABLED;



        public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO  	= DISABLED;

        	// sub sub module set pr to po

        	public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO_REGULAR  	= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO_KITCHEN  	= DISABLED;



        public static final boolean SYS_PROP_SUB_PUR_PO  			= DISABLED;

            // sub sub module po

        	public static final boolean SYS_PROP_SUB_PUR_PO_REGULAR  			= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_PO_KITCHEN  			= DISABLED;



        public static final boolean SYS_PROP_SUB_PUR_REMAINDER  	= DISABLED;

        	// sub sub module remainder

        	public static final boolean SYS_PROP_SUB_PUR_REMAINDER_PR  	= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_REMAINDER_PO  	= DISABLED;



        public static final boolean SYS_PROP_SUB_PUR_RECEIVING  	= DISABLED;

        	// sub sub module receiving

        	public static final boolean SYS_PROP_SUB_PUR_RECEIVING_REGULAR  	= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_RECEIVING_KITCHEN  	= DISABLED;





    // material

    public static final boolean SYS_PROP_MODULE_MATERIAL 		= DISABLED;

    	// sub module material

    	public static final boolean SYS_PROP_SUB_MAT_DISPATCH_REQUEST 		= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_DISPATCH 				= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_DISPATCH_RECEIVE 		= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_MATERIAL_STOCK_LIST 	= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_STOCK_OPNAME 			= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_STOCK_REPORT 			= DISABLED;





    // datamanagement

    public static final boolean SYS_PROP_MODULE_DATA_MANAGEMENT = ENABLED;

    	// sub module data management

    	public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_DATA 		= ENABLED;

        	// sub sub module hotel data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_PROFILE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_SUR_COM 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_ROOM 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_ROOM_CLASS 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_GUEST_DATA 		= ENABLED;

        	// sub sub guest data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_GUEST_LIST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_NEW_GUEST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_PRINT_GUEST_DATA 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MEMBERSHIP 		= DISABLED;

        	// sub sub module membership

        	public static final boolean SYS_PROP_SUB_DATA_MAN_PACKAGE 			= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BONUS_WEEK 		= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL 			= DISABLED;

        	// sub sub module material

        	public static final boolean SYS_PROP_SUB_DATA_MATERIAL_GROUP 				= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_LIST 			= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_PERIODE 			= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_LOCATION 		= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_PAYROLL 			= ENABLED;

        	// sub sub module payroll

        	public static final boolean SYS_PROP_SUB_DATA_MAN_EMPLOYEE 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_RELIGION 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MARITAL 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_DEPARTMENT 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_POSITION 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_INIT_ALLOWANCE 		= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MASTER_DATA 		= ENABLED;

        	// sub sub module master data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_COUNTRY 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_EXCHANGE_RATE 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BILLING_TYPE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MASTER_TYPE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BILLING_GROUP			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_TRAVEL_AGENT	= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_CORPORATE		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_GUIDE			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_SUPPLIER		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_OUTLET_SYSTEM 	= DISABLED;

        	// sub sub module outlet system

        	public static final boolean SYS_PROP_SUB_DATA_MAN_OUTET_COVER 		= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_OTHER_PAYMENT 	= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_SYSTEM_ADMIN 		= ENABLED;

        	// sub sub module system admin

        	public static final boolean SYS_PROP_SUB_DATA_MAN_USER 					= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_GROUP 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_PRIVILEGE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_SYSTEM_PROPERTY 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MAN_ACTIVITY 		= ENABLED;

        	// sub sub module system admin

        	public static final boolean SYS_PROP_SUB_DATA_MAN_BACK_UP_SERVICE				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MONITORING_SERVICE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_QUEST_QUERY		 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_RESERVATION_STATISTIC 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_PORT_INTERFACE 	= ENABLED;

        	// sub sub module port & interface

        	public static final boolean SYS_PROP_SUB_DATA_MAN_SETUP 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_TELEPHONE_CHARGE 	= ENABLED;



    // ledger

    public static final boolean SYS_PROP_MODULE_LEDGER 			= ENABLED;

    	// sub module ledger

    	public static final boolean SYS_PROP_SUB_ROOM_LEDGER 					= ENABLED;

        public static final boolean SYS_PROP_SUB_BILLNG_LEDGER 					= ENABLED;

        public static final boolean SYS_PROP_SUB_EXPENSE_LEDGER 				= DISABLED;

        public static final boolean SYS_PROP_SUB_DAILY_SALES_REPORT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_DAILY_SUMMARY_REPORT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_SALES_POTENSIAL_REPORT 		= ENABLED;

        public static final boolean SYS_PROP_SUB_ROOM_REPORT 		= ENABLED;



	public static final boolean SYS_PROP_MODULE_NIGHT_AUDIT = ENABLED;



    // is ADDNEW PO directly without PR permitted, if permit --> ENABLED, otherwise --> DISABLED

    public static final boolean SYS_PROP_ADDPO_DIRECTLY			= ENABLED;



    // what kind of billing cost calculation on the application (regular, or break down)

	public static final int SYS_PROP_APP_BILLING_ITEM_COST 					=  SYS_PROP_COST_BREAK_DOWN;



	// what kind of billing currency used on the application (USD only, RP only, use both)

    public static final int SYS_PROP_APP_BILLING_CURRENCY 		=  SYS_PROP_CURRENCY_RP;



	// invoice with tax / service view

    public static final boolean SYS_PROP_INVOICE_TAX_AND_SERVICE  = SYS_PROP_WITHOUT_TAX_AND_SERVICE;



	//exchange rate type

    public static final int SYS_PROP_EXCHANGE_RATE_TYPE 		= SYS_PROP_BOOKEEPING;



    public static final int SYS_PROP_REGISTRATION_FORM = TEGALSARI;



    //billing price is changeable

    public static final boolean SYS_PROP_BILLING_PRICE_CHANGEABLE 		= true;



    //print out payment history

    public static final boolean SYS_PROP_PRINT_OUT_PAYMENT_HISTORY 		= false;



	//input billing random(gabungan/per billing)

    public static final boolean SYS_PROP_INPUT_BILLING_RANDOM 		= true;



    //print invoice murni hanya Rp. tanpa USD

    public static final boolean SYS_PROP_INVOICE_PURELY_RP 		= true;



	//print invoice ditampilkan nama hotelnya

    public static final boolean SYS_PROP_INVOICE_HOTEL_NAME_VIEW 		= false;





        	/* ---------------- DEFAULT PAGE FOR MARIO SILVER ACCOMMODATION ----------------------------  */

   /*

	// WARNING .... DON'T DARE TO DELETE THIS PAGE PROPERTY, WITHOUT CONFIRMATION FROM EKA .... :))



    public static final String HEADER_HOTEL_NAME_IMAGE 	= "tegalsari/hotelname.jpg";

    public static final String HEADER_HOTEL_LOGO			= "tegalsari/headlogo.jpg";

    public static final String MAIN_LOGIN_IMAGE				= "tegalsari/mainlogin.jpg";

    public static final String WELCOME_IMAGE				= "tegalsari/welcome.jpg";

    public static final String HOTEL_NAME_HEADER_OFFLINE_TOP  	= "MARIO SILVER";

    public static final String HOTEL_NAME_HEADER_OFFLINE_BOTTOM	= "";



    // reservation

    public static final boolean SYS_PROP_MODULE_RESERVATION  	= DISABLED;

    	// sub module reservation

    	public static final boolean SYS_PROP_SUB_RSV_ROOM_BLOCKING 		= DISABLED;

		public static final boolean SYS_PROP_SUB_RSV_RESERVATION_LIST 	= DISABLED;

        public static final boolean SYS_PROP_SUB_RSV_CHECK_IN 			= DISABLED;

        public static final boolean SYS_PROP_SUB_RSV_CHECK_OUT 			= DISABLED;

        public static final boolean SYS_PROP_SUB_RSV_ROOM_TRANSFER 		= DISABLED;

        public static final boolean SYS_PROP_SUB_RSV_ROOM_RATE 			= DISABLED;



    // in house

    public static final boolean SYS_PROP_MODULE_INHOUSE  	= ENABLED;

    	// sub module in house

        public static final boolean SYS_PROP_SUB_INH_CHECK_IN 			= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_CHECK_OUT 			= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_ROOM_TRANSFER 		= ENABLED;

        public static final boolean SYS_PROP_SUB_INH_EXPECT_DEPARTURE 	= ENABLED;



    // cashiering

    public static final boolean SYS_PROP_MODULE_CHASIERING	 	= DISABLED;

    	// sub module cashiering

    	public static final boolean SYS_PROP_SUB_CHS_BILLING_REC 			= DISABLED;

        public static final boolean SYS_PROP_SUB_CHS_INVOICE_LIST 			= DISABLED;

        public static final boolean SYS_PROP_SUB_CHS_OUTSIDE_GUEST_BILLING 	= DISABLED;

        public static final boolean SYS_PROP_SUB_CHS_OPERATOR_CALL 			= DISABLED;

        public static final boolean SYS_PROP_SUB_CHS_OUTLET_SYSTEM 			= DISABLED;





    // housekeeping

    public static final boolean SYS_PROP_MODULE_HOUSEKEEPING  	= DISABLED;

    	// sub module housekeeping

    	public static final boolean SYS_PROP_SUB_HK_ROOM_STATUS  	= DISABLED;

        public static final boolean SYS_PROP_SUB_HK_HOTEL_PROJECT  	= DISABLED;

        public static final boolean SYS_PROP_SUB_HK_ROOM_REPORT  	= DISABLED;





    // membership

    public static final boolean SYS_PROP_MODULE_MEMBERSHIP  	= DISABLED;

    	// sub module membership

    	public static final boolean SYS_PROP_SUB_MEM_MEMBERSHIP_DATA  		= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_INSTALLMENT  			= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_MINTENANCE_FEE  		= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_MEMBER_EXCHANGE  		= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_MEMBER_RCI  			= DISABLED;

        public static final boolean SYS_PROP_SUB_MEM_YEARLY_ANNOUNCEMENT  	= DISABLED;





    // payroll

    public static final boolean SYS_PROP_MODULE_PAYROLL  		= ENABLED;

    	// sub module payroll

        public static final boolean  SYS_PROP_SUB_MODULE_PAYROLL_PPH21_PERCENT 	= ENABLED;





    // purchasing

    public static final boolean SYS_PROP_MODULE_PURCHASING  	= DISABLED;

    	// sub module purchasing

    	public static final boolean SYS_PROP_SUB_PUR_PR  			= DISABLED;

        	// sub sub module pr

        	public static final boolean SYS_PROP_SUB_PUR_PR_REGULAR  			= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_PR_KITCHEN  			= DISABLED;



        public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO  	= DISABLED;

        	// sub sub module set pr to po

        	public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO_REGULAR  	= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_SET_PR_TO_PO_KITCHEN  	= DISABLED;



        public static final boolean SYS_PROP_SUB_PUR_PO  			= DISABLED;

            // sub sub module po

        	public static final boolean SYS_PROP_SUB_PUR_PO_REGULAR  			= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_PO_KITCHEN  			= DISABLED;



        public static final boolean SYS_PROP_SUB_PUR_REMAINDER  	= DISABLED;

        	// sub sub module remainder

        	public static final boolean SYS_PROP_SUB_PUR_REMAINDER_PR  	= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_REMAINDER_PO  	= DISABLED;



        public static final boolean SYS_PROP_SUB_PUR_RECEIVING  	= DISABLED;

        	// sub sub module receiving

        	public static final boolean SYS_PROP_SUB_PUR_RECEIVING_REGULAR  	= DISABLED;

            public static final boolean SYS_PROP_SUB_PUR_RECEIVING_KITCHEN  	= DISABLED;





    // material

    public static final boolean SYS_PROP_MODULE_MATERIAL 		= DISABLED;

    	// sub module material

    	public static final boolean SYS_PROP_SUB_MAT_DISPATCH_REQUEST 		= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_DISPATCH 				= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_DISPATCH_RECEIVE 		= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_MATERIAL_STOCK_LIST 	= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_STOCK_OPNAME 			= DISABLED;

        public static final boolean SYS_PROP_SUB_MAT_STOCK_REPORT 			= DISABLED;





    // datamanagement

    public static final boolean SYS_PROP_MODULE_DATA_MANAGEMENT = ENABLED;

    	// sub module data management

    	public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_DATA 		= ENABLED;

        	// sub sub module hotel data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_PROFILE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_SUR_COM 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_ROOM 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_ROOM_CLASS 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_GUEST_DATA 		= ENABLED;

        	// sub sub guest data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_GUEST_LIST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_NEW_GUEST 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_PRINT_GUEST_DATA 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MEMBERSHIP 		= DISABLED;

        	// sub sub module membership

        	public static final boolean SYS_PROP_SUB_DATA_MAN_PACKAGE 			= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BONUS_WEEK 		= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL 			= DISABLED;

        	// sub sub module material

        	public static final boolean SYS_PROP_SUB_DATA_MATERIAL_GROUP 				= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_LIST 			= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_PERIODE 			= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MATERIAL_LOCATION 		= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_PAYROLL 			= ENABLED;

        	// sub sub module payroll

        	public static final boolean SYS_PROP_SUB_DATA_MAN_EMPLOYEE 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_RELIGION 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MARITAL 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_HOTEL_DEPARTMENT 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_POSITION 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_INIT_ALLOWANCE 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MASTER_DATA 		= ENABLED;

        	// sub sub module master data

        	public static final boolean SYS_PROP_SUB_DATA_MAN_COUNTRY 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_EXCHANGE_RATE 		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BILLING_TYPE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MASTER_TYPE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_BILLING_GROUP			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_TRAVEL_AGENT	= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_CORPORATE		= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_GUIDE			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_CONTACT_SUPPLIER		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_OUTLET_SYSTEM 	= DISABLED;

        	// sub sub module outlet system

        	public static final boolean SYS_PROP_SUB_DATA_MAN_OUTET_COVER 		= DISABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_OTHER_PAYMENT 	= DISABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_SYSTEM_ADMIN 		= ENABLED;

        	// sub sub module system admin

        	public static final boolean SYS_PROP_SUB_DATA_MAN_USER 					= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_GROUP 				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_PRIVILEGE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_SYSTEM_PROPERTY 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_MAN_ACTIVITY 		= ENABLED;

        	// sub sub module system admin

        	public static final boolean SYS_PROP_SUB_DATA_MAN_BACK_UP_SERVICE				= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_MONITORING_SERVICE 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_QUEST_QUERY		 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_RESERVATION_STATISTIC 		= ENABLED;



        public static final boolean SYS_PROP_SUB_DATA_MAN_PORT_INTERFACE 	= ENABLED;

        	// sub sub module port & interface

        	public static final boolean SYS_PROP_SUB_DATA_MAN_SETUP 			= ENABLED;

            public static final boolean SYS_PROP_SUB_DATA_MAN_TELEPHONE_CHARGE 	= ENABLED;



    // ledger

    public static final boolean SYS_PROP_MODULE_LEDGER 			= DISABLED;

    	// sub module ledger

    	public static final boolean SYS_PROP_SUB_ROOM_LEDGER 					= DISABLED;

        public static final boolean SYS_PROP_SUB_BILLNG_LEDGER 					= DISABLED;

        public static final boolean SYS_PROP_SUB_EXPENSE_LEDGER 				= DISABLED;

        public static final boolean SYS_PROP_SUB_DAILY_SALES_REPORT 			= DISABLED;

        public static final boolean SYS_PROP_SUB_DAILY_SUMMARY_REPORT 			= DISABLED;

        public static final boolean SYS_PROP_SUB_SALES_POTENSIAL_REPORT 		= DISABLED;

        public static final boolean SYS_PROP_SUB_ROOM_REPORT 		= DISABLED;



	public static final boolean SYS_PROP_MODULE_NIGHT_AUDIT = ENABLED;



    // is ADDNEW PO directly without PR permitted, if permit --> ENABLED, otherwise --> DISABLED

    public static final boolean SYS_PROP_ADDPO_DIRECTLY			= ENABLED;



    // what kind of billing cost calculation on the application (regular, or break down)

	public static final int SYS_PROP_APP_BILLING_ITEM_COST 					=  SYS_PROP_COST_BREAK_DOWN;



	// what kind of billing currency used on the application (USD only, RP only, use both)

    public static final int SYS_PROP_APP_BILLING_CURRENCY 		=  SYS_PROP_CURRENCY_RP;



	// invoice with tax / service view

    public static final boolean SYS_PROP_INVOICE_TAX_AND_SERVICE  = SYS_PROP_WITHOUT_TAX_AND_SERVICE;



	//exchange rate type

    public static final int SYS_PROP_EXCHANGE_RATE_TYPE 		= SYS_PROP_BOOKEEPING;



    public static final int SYS_PROP_REGISTRATION_FORM = MARIO;



	//billing price is changeable

    public static final boolean SYS_PROP_BILLING_PRICE_CHANGEABLE 		= false;



	//print out payment history

    public static final boolean SYS_PROP_PRINT_OUT_PAYMENT_HISTORY 		= true;



	//input billing random(gabungan/per billing)

    public static final boolean SYS_PROP_INPUT_BILLING_RANDOM 		= true;



	//print invoice murni hanya Rp. tanpa USD

    public static final boolean SYS_PROP_INVOICE_PURELY_RP 		= false;



    */





} // end of SystemProperty

