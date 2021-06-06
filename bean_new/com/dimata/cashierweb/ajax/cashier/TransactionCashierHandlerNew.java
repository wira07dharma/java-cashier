package com.dimata.cashierweb.ajax.cashier;

import com.dimata.cashierweb.entity.admin.AppUser;
import com.dimata.cashierweb.entity.admin.PstAppUser;
import com.dimata.cashierweb.entity.cashier.transaction.PstCustomBillMain;
import com.dimata.cashierweb.entity.cashier.transaction.PstQueensLocation;
import com.dimata.cashierweb.entity.cashier.transaction.QueensLocation;
import com.dimata.cashierweb.entity.masterdata.Category;
import com.dimata.cashierweb.entity.masterdata.Material;
import com.dimata.cashierweb.entity.masterdata.MemberReg;
import com.dimata.cashierweb.entity.masterdata.PriceTypeMapping;
import com.dimata.cashierweb.entity.masterdata.PstCategory;
import com.dimata.cashierweb.entity.masterdata.PstMaterial;
import com.dimata.cashierweb.entity.masterdata.PstMemberReg;
import com.dimata.cashierweb.entity.masterdata.PstSales;
import com.dimata.cashierweb.entity.masterdata.Sales;
import com.dimata.common.db.DBHandler;
import com.dimata.common.db.DBResultSet;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.system.PstSystemProperty;
import com.dimata.common.form.contact.CtrlContactList;
import com.dimata.gui.jsp.ControlList;
import com.dimata.hanoman.entity.masterdata.PstContactClass;
import com.dimata.pos.entity.billing.BillMain;
import com.dimata.pos.entity.billing.Billdetail;
import com.dimata.pos.entity.billing.PstBillDetail;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.form.billing.FrmBillDetail;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.services.WebServices;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TransactionCashierHandlerNew extends HttpServlet{
    //OBJECT
    private JSONObject jSONObject = new JSONObject();
    private JSONArray jSONArray = new JSONArray();
    //LONG
    private long oidReturn = 0;
    //STRING
    private String dataFor = "";
    private String approot = "";
    private String htmlReturn = "";
    private String message = "";
    //INT
    private int iCommand = 0;
    private int iErrCode = 0;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        this.dataFor = FRMQueryString.requestString(request, "FRM_FIELD_DATA_FOR");
        this.approot = FRMQueryString.requestString(request, "FRM_FIELD_APPROOT");
        this.iCommand = FRMQueryString.requestCommand(request);
        
        this.message = "";
        this.iErrCode = 0;
        this.jSONObject = new JSONObject();
        
        switch(this.iCommand){
	    case Command.SAVE :
		commandSave(request);
	    break;
                
            case Command.LIST :
                //commandList(request);
            break;
		
	    
	    default : commandNone(request);
	}
        
        try{
	    this.jSONObject.put("FRM_FIELD_HTML", this.htmlReturn);
      this.jSONObject.put("FRM_FIELD_RETURN_OID", "" + this.oidReturn);
	    this.jSONObject.put("FRM_FIELD_RETURN_ERROR", this.iErrCode);
	    this.jSONObject.put("FRM_FIELD_RETURN_MESSAGE", this.message);
	}catch(JSONException jSONException){
	    jSONException.printStackTrace();
	}
        
        response.getWriter().print(this.jSONObject);
        
    }
    
    public void commandSave(HttpServletRequest request){
        if (this.dataFor.equals("saveNewCustomer")){
            saveNewCustomer(request);
        }else if (this.dataFor.equals("production")){
            production(request);
        }
    }
    
    public void saveNewCustomer(HttpServletRequest request) {
        try {
            String urlSedana = PstSystemProperty.getValueByName("SEDANA_APP_URL");
            long contactType = FRMQueryString.requestLong(request, "FRM_FIELD_CLASS_TYPE");
            Vector vectContactClass = PstContactClass.list(0, 0, "" + PstContactClass.fieldNames[PstContactClass.FLD_CLASS_TYPE] + " = '" +  contactType + "'", "");
            CtrlContactList ctrlContactList = new CtrlContactList(request);
            this.iErrCode = ctrlContactList.action(Command.SAVE, 0, vectContactClass);
            ContactList contactList = ctrlContactList.getContactList();
            JSONObject jSONObject = PstContactList.fetchJSON(contactList.getOID());
            String urlPost = urlSedana+"/kredit/member/insert";
            JSONObject objStatus = WebServices.postAPI(jSONObject.toString(),urlPost);
            boolean status = objStatus.optBoolean("SUCCES", false);
            this.jSONObject.put("RETURN_OID_CONTACT", "" + contactList.getOID());
        } catch (Exception e) {
            this.iErrCode = 1;
            this.message = e.getMessage();
        }
    }
    public void production(HttpServletRequest request) {
            long oid = FRMQueryString.requestLong(request, "BILL_MAIN_ID");
            double admin = FRMQueryString.requestDouble(request, "BILL_ADMIN_PRICE");
            double transport = FRMQueryString.requestDouble(request, "BILL_TRANSPORT_PRICE");
        try {
          BillMain billMain = PstBillMain.fetchExc(oid);
          billMain.setStatus(PstBillMain.PETUGAS_DELIVERY_STATUS_ON_PRODUCTION);
          billMain.setAdminFee(admin);
          billMain.setShippingFee(transport);
          long oidNew = PstBillMain.updateExc(billMain);
          if(oidNew != 0){
            this.message = "Order ke Production Berhasil!!";
          }else{
            this.message = "Order ke Production Gagal!!";
          }
        } catch (Exception exc) {
        }
    }
    
    public void commandNone(HttpServletRequest request){
	if (this.dataFor.equals("loadMenu")){
            this.htmlReturn = loadMenuGlobal(request);
        }else if (this.dataFor.equals("checkOpenBill")){
            this.htmlReturn = checkOpenBill(request);
        }else if(this.dataFor.equals("loadMenuDinamis")){
            this.htmlReturn = loadSubCategoryDinamis(request);
        }else if(this.dataFor.equals("printGuideCommission")){
            this.htmlReturn = printGuideCommission(request);
        }else if(this.dataFor.equals("getSalesPerson")){
            this.htmlReturn = getSalesPerson(request);
        }else if(this.dataFor.equals("getUserSalesPerson")){
            this.htmlReturn = getUserSalesPerson(request);
        }
    }
    
    private String checkOpenBill(HttpServletRequest request) {
        String htmlReturn = "";

        long locationId = FRMQueryString.requestLong(request, "FRM_FIELD_LOCATION_ID");
        String whereBill = "";
        String creditType = PstSystemProperty.getValueByName("CREDIT_TYPE");
        String useProduction = PstSystemProperty.getValueByName("USE_PRODUCTION");
        if (creditType.equals("1") || useProduction.equals("1")) {
            whereBill = " OR (" + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1' "
                    + "AND " + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                    + ")";
        }
        String whereOpenBill = ""
                + " " + PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID] + "=" + locationId + ""
                + " AND ((" + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0'"
                + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0'"
                + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1'"
                + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_INVOICE_NO] + " like '%X%')" + whereBill + ")";

        int countBill = PstBillMain.getCount(whereOpenBill);

        String whereOpenBill2 = ""
                + " " + PstBillMain.fieldNames[PstBillMain.FLD_LOCATION_ID] + "=" + locationId + ""
                + " AND ((" + PstBillMain.fieldNames[PstBillMain.FLD_STATUS_INVOICING] + "='1'"
                + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_DOC_TYPE] + "='0'"
                + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSCATION_TYPE] + "='0'"
                + " AND " + PstBillMain.fieldNames[PstBillMain.FLD_TRANSACTION_STATUS] + "='1')" + whereBill + ")";

        int countBill2 = PstBillMain.getCount(whereOpenBill2);
        this.oidReturn = countBill2;

        htmlReturn = "" + countBill + "";
        return htmlReturn;
    }

    private String loadMenuGlobal (HttpServletRequest request){
        int menu = FRMQueryString.requestInt(request, "FRM_FIELD_MENU");
        String htmlReturn = "";
        switch (menu){
            case 1 : 
                htmlReturn = loadMainCategory(request);
            break;
            case 2 : 
                htmlReturn = loadMainMenu(request);
            break;
                
        }
        
        return htmlReturn;
    }
    
    private String loadMainCategory (HttpServletRequest request){
        String htmlReturn = "";
        
        int type = FRMQueryString.requestInt(request, "FRM_FIELD_TYPE");
        long parent = FRMQueryString.requestLong(request, "FRM_FIELD_PARENT");
        long location = FRMQueryString.requestLong(request, "FRM_FIELD_LOCATION");
        String priceType = FRMQueryString.requestString(request, "FRM_FIELD_PRICE_TYPE");
        String design = FRMQueryString.requestString(request, "FRM_FIELD_DESIGN");
        
        
        String whereCategory = ""
            + " "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"='"+type+"'"
            + " AND "+PstCategory.fieldNames[PstCategory.FLD_STATUS]+"='1'";
        
        String order = ""
            + ""+PstCategory.fieldNames[PstCategory.FLD_NAME]+"";
        
        Vector listCategory = PstCategory.list(0, 0, whereCategory, order);
        int countRow = 0;
        int jumlah = listCategory.size();
        String fbWrite = "";
        if (type==0){
            fbWrite = "Food";
        }else{
            fbWrite = "Beverage";
        }
        htmlReturn += ""
        + "<div class='row' style='margin-bottom:10px;'>"
            + "<div class='col-md-12'>"
                + "<div class='btn-group'>"
                    + "<button type ='button' class='btn btn-default mainMenuOutlet'><i class='fa fa-home'></i>&nbsp;</button>"
                    + "<button type ='button' class='btn btn-default active'><i class='fa fa-gg-circle'></i> "+fbWrite+"</button>"
                + "</div>"
            + "</div>"
        + "</div>";
        for (int i = 0; i<listCategory.size();i++){
            Category category = new Category();
            category = (Category) listCategory.get(i);
            if (countRow==0){
                htmlReturn += "<div class='row' style='margin-top:3px;'>";
            }
            
            String classes = "categoryMenu";
            Vector data1 = PstCategory.list(0,0,""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='"+category.getOID()+"'","");
            if(data1.size() == 0){
                classes = "menuClick";
            }
            
            
            htmlReturn += ""
            + "<div class='col-md-3'>"
                + "<button data-menu='1' data-parent='"+category.getOID()+"' data-location='"+location+"' data-pricetype='"+priceType+"' data-type='"+type+"' data-design='"+design+"' data-oidcategory='"+category.getOID()+"' type='button' class='btn btn-primary btn-block btn-lg "+classes+"' data-for='loadMenuDinamis'>"+category.getName()+"</button>"
            + "</div>";
            
            countRow = countRow + 1;
            
            if (countRow==4 || i ==(jumlah-1) ){
                htmlReturn += "</div>";
            }
            
            if (countRow==4){
                countRow = 0;
            }
        }
        
       
        
        return htmlReturn;
    }
    
    private String loadMainMenu (HttpServletRequest request){
        String htmlReturn="";
        QueensLocation queensLocation;
        
        long oidLocation = 0;
        long oidCategory = FRMQueryString.requestLong(request, "FRM_FIELD_CATEGORY");
        int type = FRMQueryString.requestInt(request, "FRM_FIELD_TYPE");
        int design = FRMQueryString.requestInt(request, "FRM_FIELD_DESIGN");
        String priceType = FRMQueryString.requestString(request, "FRM_FIELD_PRICE_TYPE");
        long location = FRMQueryString.requestLong(request, "FRM_FIELD_LOCATION");
        long parent = FRMQueryString.requestLong(request, "FRM_FIELD_PARENT");
        
        String multilocation = PstSystemProperty.getValueByName("OUTLET_MULTILOCATION");
        String defaultOidLocation = PstSystemProperty.getValueByName("OUTLET_DEFAULT_LOCATION");
        String defaultOidPriceType = PstSystemProperty.getValueByName("OUTLET_DEFAULT_PRICE_TYPE");
        
        if(multilocation.equals("1")){
            try{
                queensLocation = PstQueensLocation.fetchExc(parent);
            }catch(Exception ex){
                queensLocation = new QueensLocation();
            }

            oidLocation = queensLocation.getOID();
            priceType = ""+queensLocation.getPriceTypeId();
        }else{			
            oidLocation = Long.parseLong(defaultOidLocation);
            priceType = defaultOidPriceType;
            try{
                queensLocation = PstQueensLocation.fetchExc(oidLocation);
            }catch(Exception ex){
                queensLocation = new QueensLocation();
            }
        }
        
        Vector listItem = PstMaterial.listShopingCartUseLocation(0,0, 
                                "("
                                + "p."+PstMaterial.fieldNames[PstMaterial.FLD_CATEGORY_ID]+"='"+oidCategory+"' "                              
                                + ")", "", ""+queensLocation.getStandartRateId(), priceType, design, oidLocation);
        
        htmlReturn = drawListItem(iCommand, listItem,0);
        
        return htmlReturn;
    }
    
    public String drawListItem(int iCommand, Vector objectClass,int start) {
        //2
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("No", "20%");
        ctrlist.addHeader("SKU", "20%");
        ctrlist.addHeader("Barcode", "20%");
        ctrlist.addHeader("Name", "20%");
	ctrlist.addHeader("Price","20%");

        //membuat link dirow 0
        ctrlist.setLinkRow(0);
        ctrlist.setLinkSufix("");
        Vector lstData = ctrlist.getData();
        //membuat link menuju ke edit

        ctrlist.reset();

        int index = -1;

        Vector rowx = new Vector(1, 1);
	double grandTotal = 0;
	String addInner = "";
	int specialItem = 0;
	String fieldSpecial = "";
	int counter = start+1;
        String addClass="";
        String addClassSpecial ="";
        String oidSpesialRequestFood=PstSystemProperty.getValueByName("SPESIAL_REQUEST_FOOD");
	String oidSpesialRequestBeverage=PstSystemProperty.getValueByName("SPESIAL_REQUEST_BEVERAGE");
        
        for (int i = 0; i < objectClass.size(); i++) {
            //long famOid = 0;
	    Vector listItem = (Vector) objectClass.get(i);
	    
	    if(listItem.size() != 0){
		
		Material material = (Material) listItem.get(0);
		PriceTypeMapping priceTypeMapping = (PriceTypeMapping)listItem.get(1);
		String[] splits = material.getName().split(";");
		rowx = new Vector(1, 1);
                if (i==0){
                    addClass = "firstFocus";
                    
                }else{
                    addClass="";
                }
                
                if (oidSpesialRequestFood.equals(""+material.getMaterialId()+"")||oidSpesialRequestBeverage.equals(""+material.getMaterialId()+"")){
                    addClassSpecial="specialOrder";
                }
                
		rowx.add("<input type='hidden' class='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME]+"' value='"+splits[0]+"'>"
			+ "<input type='hidden' class='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_MATERIAL_ID]+"' value='"+material.getMaterialId()+"'>"
			+ "<input type='hidden' class='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_PRICE]+"' value='"+priceTypeMapping.getPrice()+"'>"
			+ ""+counter+"");
                rowx.add(""+material.getSku()+"");
                rowx.add(""+material.getBarcode()+"");
                rowx.add("<input readonly='readonly' style='cursor:pointer;' class='textual form-control FRM_FIELD_ITEM_NAME "+addClassSpecial+" "+addClass+"' data-oid='"+material.getMaterialId()+"' data-name='"+splits[0]+"'  data-price='"+priceTypeMapping.getPrice()+"' type='text' value='"+splits[0]+"'>");
		//rowx.add("<a href='javascript:' class='"+FrmBillDetail.fieldNames[FrmBillDetail.FRM_FIELD_ITEM_NAME]+"' data-oid='"+material.getMaterialId()+"' data-name='"+splits[0]+"' data-price='"+priceTypeMapping.getPrice()+"'>"+splits[0]+"</a>");
		rowx.add(""+Formater.formatNumber(priceTypeMapping.getPrice(),"#,###")+"");
		lstData.add(rowx);
		counter++;
	    }
	    
            
            
        }
        return ctrlist.drawBootstrapStripted();
    }
    
    private String loadSubCategoryDinamis(HttpServletRequest request){
        String htmlReturn = "";
        
        int type = FRMQueryString.requestInt(request, "FRM_FIELD_TYPE");
        long parent = FRMQueryString.requestLong(request, "FRM_FIELD_PARENT");
        long location = FRMQueryString.requestLong(request, "FRM_FIELD_LOCATION");
        String priceType = FRMQueryString.requestString(request, "FRM_FIELD_PRICE_TYPE");
        String design = FRMQueryString.requestString(request, "FRM_FIELD_DESIGN");
        
        Category activeCategory = new Category();
        if(parent != 0){
            try {
                activeCategory = PstCategory.fetchExc(parent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        String whereCategory = ""
            + " "+PstCategory.fieldNames[PstCategory.FLD_CATEGORY]+"='"+type+"'"
            + " AND "+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='"+parent+"'";
        
        Vector listCategory = PstCategory.list(0, 0, whereCategory, ""+PstCategory.fieldNames[PstCategory.FLD_NAME]+"");
        
        //CEK IS PARENT
        int count = PstCategory.getCount(PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='"+parent+"'");
        String fbWrite = "";
        if (type==0){
            fbWrite = "Food";
        }else{
            fbWrite = "Beverage";
        }
        htmlReturn += ""
        + "<div class='row' style='margin-bottom:10px;'>"
            + "<div class='col-md-12'>"
                + "<div class='btn-group'>"
                    + "<button type ='button' class='btn btn-default mainMenuOutlet'><i class='fa fa-home'></i>&nbsp;</button>"
                    + "<button data-menu='1' data-type='"+type+"' data-oidcategory='0' data-design='"+design+"' data-pricetype='"+priceType+"' data-location='"+location+"' data-parent='"+parent+"' data-for='loadMenu' type='button' class='btn btn-default categoryMenu'><i class='fa fa-gg-circle'></i> "+fbWrite+"</button>";
                   // + "<button type ='button' class='btn btn-default '><i class='fa fa-gg-circle'></i> "+fbWrite+"</button>";
                    Vector anotherMenu = new Vector(1,1);
                    anotherMenu = parentMenu(parent, anotherMenu, location, priceType, design, type);
                    if(anotherMenu.size() > 0){
                        for(int i = anotherMenu.size()-2; i >=1; i--){
                            String getHtml = (String) anotherMenu.get(i);
                            htmlReturn += getHtml;
                        }
                    }
                    htmlReturn+= ""
                    + "<button type ='button' class='btn btn-default active'><i class='fa fa-gg-circle'></i> "+activeCategory.getName()+"</button>"
                + "</div>"
            + "</div>"
        + "</div>";
        if(count > 0){
            htmlReturn += loadMainCategoryDinamis(request, listCategory);
        }else{
            htmlReturn += loadMainMenu(request);
        }
        
        
        
        return htmlReturn ;
    }
    
    private String loadMainCategoryDinamis(HttpServletRequest request, Vector listCategory){
        String htmlReturn = "";
        
        int type = FRMQueryString.requestInt(request, "FRM_FIELD_TYPE");
        long parent = FRMQueryString.requestLong(request, "FRM_FIELD_PARENT");
        long location = FRMQueryString.requestLong(request, "FRM_FIELD_LOCATION");
        String priceType = FRMQueryString.requestString(request, "FRM_FIELD_PRICE_TYPE");
        String design = FRMQueryString.requestString(request, "FRM_FIELD_DESIGN");
        int countRow = 0;
        int jumlah = listCategory.size();
        
        
       
        for (int i = 0; i<listCategory.size();i++){
            Category category = new Category();
            category = (Category) listCategory.get(i);
            if (countRow==0){
                htmlReturn += "<div class='row' style='margin-top:3px;'>";
            }
            //cek kategori
            String classes = "categoryMenu";
            Vector data1 = PstCategory.list(0,0,""+PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='"+category.getOID()+"'","");
            if(data1.size() == 0){
                classes = "menuClick";
            }
            
            htmlReturn += ""
            + "<div class='col-md-3'>"
                + "<button data-oidcategory='"+category.getOID()+"' data-menu='1' data-parent='"+category.getOID()+"' data-location='"+location+"' data-pricetype='"+priceType+"' data-type='"+type+"' data-design='"+design+"' data-oidcategory='"+category.getOID()+"' type='button' class='btn btn-primary btn-block btn-lg "+classes+"' data-for='loadMenuDinamis'>"+category.getName()+"</button>"
            + "</div>";
            
            countRow = countRow + 1;
            
            if (countRow==4 || i ==(jumlah-1) ){
                htmlReturn += "</div>";
            }
            
            if (countRow==4){
                countRow = 0;
            }
        }
        
       
        
        return htmlReturn;
    }
    
    public Vector parentMenu(long oid, Vector data, long location, String priceType, String design, int type){
        if(oid != 0){
            //CEK is sub category
            int count = PstCategory.getCount(PstCategory.fieldNames[PstCategory.FLD_CAT_PARENT_ID]+"='"+oid+"'");
            String whereClause = PstCategory.fieldNames[PstCategory.FLD_CATEGORY_ID]+"='"+oid+"'";
            
            Vector listCategory = PstCategory.list(0, 0, whereClause, "");
            if(listCategory.size() > 0){
                for(int i = 0; i < listCategory.size(); i++){
                    Category category = (Category) listCategory.get(i);
                    String html = "<button data-menu='1' data-type='"+type+"' data-oidcategory='0' data-design='"+design+"' data-pricetype='"+priceType+"' data-location='"+location+"' data-parent='"+category.getOID()+"' data-for='loadMenuDinamis' type='button' class='btn btn-default categoryMenu'><i class='fa fa-gg-circle'></i> "+category.getName()+"</button>";
                    data.add(html);
                    data = parentMenu(category.getCatParentId(), data, location, priceType, design, type);
                }
            }
        }
        
        return data;
    }
    
    public String printGuideCommission(HttpServletRequest request) {
        String html = "";
        long billMainId = FRMQueryString.requestLong(request, "BILL_MAIN_ID");
        int full = FRMQueryString.requestInt(request, "full");

        if (billMainId == 0) {
            this.iErrCode = 1;
            this.message = "Bill tidak ditemukan";
            return "";
        }

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

        String paperSetting = " style=\"" + background + " " + paperWidth + " " + paperHeight + " \"";

        String style = "style='padding: 3px'";

        try {
            BillMain billMain = PstBillMain.fetchExc(billMainId);
            Vector<Billdetail> listDetail = PstBillDetail.list(0, 0, PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID] + " = " + billMain.getOID(), "");
            String detail = "";
            double total = 0;
            for (Billdetail bd : listDetail) {
              double komisi = bd.getGuidePrice() - bd.getItemPrice();
              double totalKomisi = komisi * bd.getQty();
              
                
                total += totalKomisi;
                detail += "<tr>";
                detail += "<td " + style + ">" + bd.getItemName() + "</td>";
                detail += "<td " + style + ">" + bd.getQty() + "</td>";
                detail += "<td " + style + " class='text-right'>" + Formater.formatNumber(bd.getGuidePrice(), "#,###") + "</td>";
                detail += "<td " + style + " class='text-right'>" + Formater.formatNumber(komisi, "#,###") + "</td>";
                detail += "<td " + style + " class='text-right'>" + Formater.formatNumber(totalKomisi, "#,###") + "</td>";
                detail += "</tr>";
            }

            MemberReg member = PstMemberReg.fetchExc(billMain.getCustomerId());
            
            html += ""
                    + "<div class='page' " + paperSetting + ">"
                    + "     <div class='subPage'>"
                    + "         <div class='row'>"
                    + "             <div class='col-xs-12'>"
                    + "                 <p class='text-center'><b>Komisi Guide</b><br>" + Formater.formatDate(new java.util.Date(), "yyyy-MM-dd HH:mm:ss") + "</p>"
                    + "                 <span class='pull-left'>Guide : " + member.getPersonName() + "</span>"
                    + "                 <span class='pull-right'>Bill : " + billMain.getInvoiceNumber() + "</span>"
                    + "                 <br>"
                    + "                 <hr style='border:1px dashed #000;margin:0px;'>"
                    + "                 <table style='width:100%; font-size:10px;'>"
                    + "                     <tr>"
                    + "                         <th " + style + ">Item</th>"
                    + "                         <th " + style + ">Qty</th>"
                    + "                         <th " + style + " class='text-right'>Price</th>"
                    + "                         <th " + style + " class='text-right'>Komisi</th>"
                    + "                         <th " + style + " class='text-right'>Total</th>"
                    + "                     </tr>"
                    + detail
                    + "                 </table>"
                    + "                 <hr style='border:1px dashed #000;margin:0px;'>"
                    + "                 <span " + style + " class='text-bold pull-right'>Total komisi : " + Formater.formatNumber(total, "#,###") + "</span>"
                    + "             </div>"
                    + "         </div>"
                    + "     </div>"
                    + "</div>";

        } catch (Exception e) {
            this.iErrCode = 1;
            this.message = e.getMessage();
            return "";
        }

        return html;
    }
    
    public String getSalesPerson(HttpServletRequest request) {
        String html = "<option value=''>-- Select Sales Person --</option>";
        try {
            long locationId = FRMQueryString.requestLong(request, "FRM_FIELD_LOCATION_ID");
            Vector<Sales> listOfSales = PstSales.list(0, 0, PstSales.fieldNames[PstSales.FLD_ASSIGN_LOCATION_WAREHOUSE] + " = " + locationId, PstSales.fieldNames[PstSales.FLD_NAME]);
            for (Sales sales : listOfSales) {
                html += "<option value='" + sales.getCode() + "'>" + sales.getName() + "</option>";
            }
        } catch (Exception e) {
        }
        return html;
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
    
    public String getUserSalesPerson(HttpServletRequest request) {
        String html = "<option value=''>-- Select Sales Person --</option>";
        try {
            long locationId = FRMQueryString.requestLong(request, "FRM_FIELD_LOCATION_ID");
            Vector<AppUser> listOfSales = PstAppUser.getSalesByLocation(locationId);
            for (AppUser sales : listOfSales) {
                html += "<option value='" + sales.getOID() + "'>" + sales.getFullName() + "</option>";
            }
        } catch (Exception e) {
        }
        return html;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
}
