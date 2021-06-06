<%-- 
    Document   : cashier-stocksearch
    Created on : Dec 1, 2018, 9:22:09 PM
    Author     : IanRizky
--%>

<%@page import="com.dimata.posbo.entity.masterdata.Ksg"%>
<%@page import="com.dimata.posbo.entity.masterdata.MatMappKsg"%>
<%@page import="com.dimata.posbo.entity.masterdata.PstMatMappKsg"%>
<%@page import="com.dimata.posbo.entity.masterdata.PstKsg"%>
<%@page import="com.dimata.posbo.session.warehouse.SessMatCostingStokFisik"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.PriceTypeMapping"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.PstQueensLocation"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.QueensLocation"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.PstSubCategory"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.SubCategory"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.PstCategory"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.Category"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.PstMerk"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.Merk"%>
<%@page import="com.dimata.hanoman.entity.masterdata.MasterType"%>
<%@page import="com.dimata.hanoman.entity.masterdata.PstMasterType"%>
<%@page import="com.dimata.posbo.entity.masterdata.PstMaterialMappingType"%>
<%@page import="com.dimata.posbo.entity.masterdata.PstColor"%>
<%@page import="com.dimata.posbo.entity.masterdata.Color"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.Material"%>
<%@page import="com.dimata.gui.jsp.ControlList"%>
<%@page import="com.dimata.cashierweb.entity.masterdata.PstMaterial"%>
<%@page import="com.dimata.qdep.form.FRMQueryString"%>
<%@page import="java.util.Date"%>
<%@page import="com.dimata.util.Formater"%>
<%@page import="java.util.Formatter"%>
<%@page import="com.dimata.common.entity.location.PstLocation"%>
<%@page import="com.dimata.util.Command"%>
<%@page import="java.util.Vector"%>
<%@page import="com.dimata.common.entity.location.Location"%>
<%@page import="com.dimata.pos.entity.balance.PstCashCashier"%>
<%@page import="com.dimata.pos.entity.balance.CashCashier"%>
<%@page import="com.dimata.cashierweb.entity.cashier.transaction.BillMainJSON"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@include file="../main/javainit_cashier.jsp" %>

<%!//
    public String drawList(Vector objectClass, long locationId, long parentLocationId) {
        //2
        ControlList ctrlist = new ControlList();

        ctrlist.setAreaWidth("100%"); //untuk mengatur width(lebar) table
        ctrlist.setAreaStyle("listgen"); //untuk mengatur nama class table
        ctrlist.setTitleStyle("tableheader"); //untuk mengatur nama class didalam kolom dalam baris table
        ctrlist.setCellStyle("cellStyle");
        ctrlist.setHeaderStyle("tableheader"); //mengatur nama class baris table
        ctrlist.addHeader("SKU");
        ctrlist.addHeader("Barcode");
        ctrlist.addHeader("Name");
        ctrlist.addHeader("Brand");
        ctrlist.addHeader("Category");
        ctrlist.addHeader("Sub Category");
        ctrlist.addHeader("Size");
        ctrlist.addHeader("Color");
        ctrlist.addHeader("Price");
        ctrlist.addHeader("Disc%");
        ctrlist.addHeader("Qty");
        ctrlist.addHeader("Gudang");
        ctrlist.addHeader("Rak");

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
        String addClass = "";
        int specialItem = 0;
        String fieldSpecial = "";

        if (objectClass.size() > 0) {
            double totalQtyStore = 0;
            double totalQtyGudang = 0;
            for (int i = 0; i < objectClass.size(); i++) {
                try {
                    Vector temp = (Vector) objectClass.get(i);
                    Material material = (Material) temp.get(0);
                    Merk merk = (Merk) temp.get(1);
                    Category cat = (Category) temp.get(2);
                    SubCategory subCat = (SubCategory) temp.get(3);
                    PriceTypeMapping map = (PriceTypeMapping) temp.get(4);

                    rowx = new Vector(1, 1);

                    Color color = new Color();
                    try {
                        color = PstColor.fetchExc(material.getPosColor());
                    } catch (Exception exc) {
                    }

                    long oidMappingSize = PstMaterialMappingType.getSelectedTypeId(1, Long.valueOf(material.getMaterialId()));
                    long oidPromotion = PstMaterialMappingType.getSelectedTypeId(5, Long.valueOf(material.getMaterialId()));
                    String size = "-";
                    String promotion = "-";

                    try {
                        MasterType type = PstMasterType.fetchExc(oidMappingSize);
                        size = type.getMasterName();
                    } catch (Exception exc) {
                    }

                    try {
                        MasterType type = PstMasterType.fetchExc(oidPromotion);
                        promotion = type.getMasterName();
                    } catch (Exception exc) {
                    }

                    double qtyStockStockCardStore = SessMatCostingStokFisik.qtyMaterialBasedOnStockCard(Long.valueOf(material.getMaterialId()), locationId, new Date(), 0);
                    //double qtyStockRealTimeStoreInvoice = SessMatCostingStokFisik.qtyMaterialBasedOnOpeningCashierInvoice(Long.valueOf(material.getMaterialId()), new Date());
                    //double qtyStockRealTimeStoreRetur = SessMatCostingStokFisik.qtyMaterialBasedOnOpeningCashierReturn(Long.valueOf(material.getMaterialId()), new Date());
                    double qtyStockStore = qtyStockStockCardStore/* - (qtyStockRealTimeStoreInvoice - qtyStockRealTimeStoreRetur)*/;
                    totalQtyStore += qtyStockStore;
                    
                    double qtyStockStockCardWH = SessMatCostingStokFisik.qtyMaterialBasedOnStockCard(Long.valueOf(material.getMaterialId()), parentLocationId, new Date(), 0);
                    totalQtyGudang += qtyStockStockCardWH;
                    String whereEtalase = "KSG.LOCATION_ID=" + parentLocationId + " AND MAP." + PstMatMappKsg.fieldNames[PstMatMappKsg.FLD_MATERIAL_ID] + "=" + material.getMaterialId();
                    Vector listEtalaseLoc = PstMatMappKsg.listJoinKsg(0, 0, whereEtalase, "");
                    String rak = "";
                    if (listEtalaseLoc.size() > 0) {
                        MatMappKsg mapKsg = (MatMappKsg) listEtalaseLoc.get(0);
                        Ksg etalase = new Ksg();
                        try {
                            etalase = PstKsg.fetchExc(mapKsg.getKsgId());
                            rak = etalase.getName();
                        } catch (Exception exc) {
                        }
                    }

                    rowx.add("" + material.getSku());
                    rowx.add("" + material.getBarcode());
                    rowx.add("" + material.getName());
                    rowx.add("" + merk.getName());
                    rowx.add("" + cat.getName());
                    rowx.add("" + subCat.getName());
                    rowx.add("" + size);
                    rowx.add("" + color.getColorName());
                    rowx.add("" + map.getPrice());
                    rowx.add("0");
                    rowx.add("" + qtyStockStore);
                    rowx.add("" + qtyStockStockCardWH);
                    rowx.add("" + rak);
                    lstData.add(rowx);
                } catch (Exception exc) {
                    System.out.println(exc.toString());
                }
            }
            rowx = new Vector(1, 1);
            rowx.add("TOTAL");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("");
            rowx.add("" + totalQtyStore);
            rowx.add("" + totalQtyGudang);
            rowx.add("");
            lstData.add(rowx);
        }

        return ctrlist.drawBootstrapStripted();
    }
%>

<%//
    int searchType = FRMQueryString.requestInt(request, "filter");
    int iCommand = FRMQueryString.requestCommand(request);
    int sameStyle = FRMQueryString.requestInt(request, "sameStyle");
    String searchValue = FRMQueryString.requestString(request, "FRM_FIELD_ITEM_NAME");

    if (iCommand == Command.NONE) {
        sameStyle = 2;
    }
    
    CashCashier cashCashier = new CashCashier();
    Location location = new Location();

    Vector listOpeningCashier = PstCashCashier.listCashOpening(0, 0,
            "CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID] + "='" + userId + "' "
            + "AND CSH." + PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID] + "='1'", "");

    Vector openingCashier = new Vector(1, 1);
    if (listOpeningCashier.size() != 0) {
        openingCashier = (Vector) listOpeningCashier.get(0);
        if (openingCashier.size() != 0) {
            cashCashier = (CashCashier) openingCashier.get(0);
            location = (Location) openingCashier.get(2);
        }
    }

    QueensLocation queensLocation = new QueensLocation();
    try {
        queensLocation = PstQueensLocation.fetchExc(location.getOID());
    } catch (Exception exc) {

    }

    Location location1 = new Location();
    try {
        location1 = PstLocation.fetchExc(location.getOID());
    } catch (Exception exc) {

    }

    Vector listMaterial = new Vector();
    if (iCommand == Command.LIST) {
        if (searchType == 1) {
            Material material = new Material();
            try {
                material = PstMaterial.fetchBySkuBarcode(searchValue);
            } catch (Exception exc) {
            }
            long oidMappingStyle = PstMaterialMappingType.getSelectedTypeId(4, material.getOID());

            String style = "";
            try {
                MasterType type = PstMasterType.fetchExc(oidMappingStyle);
                style = type.getMasterName();
            } catch (Exception exc) {
            }
            if (sameStyle > 0) {
                listMaterial = PstMaterial.listJoinMasterType(4, style, queensLocation.getStandartRateId(), queensLocation.getPriceTypeId(), sameStyle, material.getPosColor());
            } else {
                listMaterial = PstMaterial.listJoinMasterType(4, searchValue, queensLocation.getStandartRateId(), queensLocation.getPriceTypeId(), 1, material.getPosColor());
            }
        } else if (searchType == 2) {
            listMaterial = PstMaterial.listJoinMasterType(4, searchValue, queensLocation.getStandartRateId(), queensLocation.getPriceTypeId(), searchType, 0);
        }
    }

%>

<html>
    <head>
        <meta charset="UTF-8">
        <title>Stock Search - Dimata Cashier</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <%@include file="cashier-css.jsp" %>
        <script type="text/javascript" src="../styles/cashier/dist/js/numberformat.js"></script>
        <script type="text/javascript" src="../styles/jquery.simplePagination.js"></script>
        <style>
            .textual:focus {
                background-color: yellow;
            }
            .button-wrapper .btn {
                margin-bottom:5px;
            }
            
            #example1 th {background-color: #EEE; white-space: nowrap}
            #example1 tr:hover {background-color: yellow}
            
        </style>
    </head>
    <body class="skin-queentandoor sidebar-mini wysihtml5-supported sidebar-collapse fixed">
        <input type="hidden" id="approotsystem" value="<%= approot%>"><input type="hidden" id="getPaymentType" >
        <div class="wrapper nonprint">
            <header class="main-header">
                <%@include file="cashier-header.jsp" %>
            </header>
            <!-- Left side column. contains the logo and sidebar -->
            <aside class="main-sidebar">
                <%
                    String home = "";
                    String transaction = "active";
                    String log = "";
                    String maintenance = "";
                %>
                <%@include file="cashier-sidebar.jsp" %>           
            </aside>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>Dimata Cashier<small>Stock Search</small></h1>
                    <ol class="breadcrumb">
                        <li><a href="<%= approot%>/cashier/cashier-home.jsp"><i class="fa fa-home"></i> Home</a></li><li class="active">Transaction</li>
                    </ol>
                </section>
                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box box-primary">
                                <div class="box-header with-border ">
                                    <div class="col-md-12">
                                        <form id="formSearch" class="form-inline" name="frm" method="post">
                                            <label for="rg-from">Filter By: </label>
                                            <div class="form-group">
                                                <select id='filterBy' name="filter" class='form-control'>
                                                    <option <%=(searchType == 1 ? "selected" : "")%> value="1">Barcode</option>
                                                    <option <%=(searchType == 2 ? "selected" : "")%> value="2">Style</option>
                                                </select>
                                                <input type="hidden" name="command" value="<%=Command.LIST%>">
                                                <input id="inputSearch" type="text" required="" placeholder="Barcode" name="FRM_FIELD_ITEM_NAME" class="form-control itemsearch">
                                                <button id="btnSearch" type="submit" class="btn btn-primary"><i class="fa fa-search"></i> Search</button>
                                                <label class="radio-inline"><input type="radio" value="2" name="sameStyle" <%=(sameStyle == 2 ? "checked" : "")%>> Barcode Same Style</label>
                                                <label class="radio-inline"><input type="radio" value="1" name="sameStyle" <%=(sameStyle == 1 ? "checked" : "")%>> Barcode Same Style & Color</label>
                                            </div>
                                            <!-- rest of form -->
                                        </form>
                                    </div>       
                                </div>
                                
                                <% if (iCommand == Command.LIST) {%>
                                <div class="box-body" id="CONTENT_LOAD">    
                                <%
                                    if (listMaterial.size() > 0) {
                                        out.println(drawList(listMaterial, location.getOID(), location1.getParentLocationId()));
                                    } else {
                                %>
                                <h4>No Item Found</h4>
                                <%
                                    }
                                %>
                                </div>    
                                <% } %>
                                
                                <div class="overlay" style="display:none;" id="CONTENT_ANIMATE_CHECK">
                                    <i class="fa fa-refresh fa-spin"></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
            <footer class="main-footer" style="bottom: 0;position: fixed;width: 100%">
                <div id="CONTENT_TOTAL" style="display:none;" class="col-md-12">
                    <div class="col-md-1 pull-right">&nbsp;</div>
                    <div class="col-md-4 pull-right">

                        <table style="background-color: #222D32;border-radius: 5px;">
                            <tr>
                                <td style="padding:4px;" width="10%"><b style="color:#a1a1a1;">GRAND TOTAL</b></td>
                                <td style="padding:4px;" width="80%"><center><font size="5" id="grandTotal" color="white"></font></center></td>
                            <td style="padding:4px;" width="10%"><button class="btn btn-success" id="btnPay"><b>PAY (F8)</b></button></td>
                            </tr>
                        </table>
                    </div>
                </div>    
            </footer>
        </div>
        <!-- JQUERY & BOOTSTRAP COMPONEN -->
        <%@include file="cashier-jquery-bootstrap.jsp" %>
        <script type="text/javascript" src="../styles/cashier/signature/flashcanvas.js"></script>
        <script type="text/javascript" src="../styles/cashier/signature/jSignature.min.js"></script>
        <script type="text/javascript" src="../styles/dimata-app.js"></script>
        <script src="../styles/src/moment.js"></script>  
        <script src="../styles/src/js/bootstrap-datetimepicker.min.js"></script>
        <link href="../styles/src/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/> 
        <%@include file="cashier-plugin.jsp" %>
        <script type="text/javascript">
            
            $(".datePicker").datepicker({
                format: 'yyyy-mm-dd'
            }).on("changeDate", function (ev) {
                $(this).datepicker('hide');
            });

            $('#example1 tr:last').css({
                'background-color':'#EEE',
                'font-weight':'bold'
            });
            
            $('#filterBy').change(function() {
                var text = $('option:selected', this).text();
                $('#inputSearch').attr('placeholder',text);
                $('#inputSearch').focus();
            });
            
            $('.radio-inline').click(function() {
                $('#inputSearch').focus();
            });
            
            $('#inputSearch').attr('placeholder',$('option:selected', '#filterBy').text());
            $('#inputSearch').focus();
            
            $('#formSearch').submit(function() {
                $('#btnSearch').attr({'disabled':true}).html('<i class="fa fa-spinner fa-pulse"></i> Tunggu...');
            });
            
        </script>
        <div id="newOrderConfirmation" class="modal nonprint">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="CONTENT_TITLE"><b>Transfer</b></h4>
                    </div>
                    <div class="modal-body" id="CONTENT_ITEM">
                    </div>
                    <div class="modal-footer">
                        <div id="cancelButton">
                            <button id="btnPostTransaction" class="btn btn-primary" type="button"><i class="fa fa-check"></i> Post</button>
                        </div>
                    </div>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div>
    </body>
</html>
