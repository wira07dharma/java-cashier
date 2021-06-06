/**
 * Created by IntelliJ IDEA.
 * User: gadnyana
 * Date: May 20, 2004
 * Time: 9:13:37 AM
 * To change this template use Options | File Templates.
 */
package com.dimata.posbo.printing.purchasing;

import com.dimata.printman.DSJ_PrintObj;
import com.dimata.printman.DSJ_PrinterService;
import com.dimata.posbo.entity.purchasing.PurchaseOrder;
import com.dimata.posbo.entity.purchasing.PstPurchaseOrder;
import com.dimata.posbo.entity.purchasing.PstPurchaseOrderItem;
import com.dimata.posbo.entity.purchasing.PurchaseOrderItem;
import com.dimata.posbo.entity.masterdata.Material;
import com.dimata.posbo.entity.masterdata.Unit;
import com.dimata.posbo.entity.masterdata.MatCurrency;
import com.dimata.util.Formater;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.qdep.form.FRMHandler;

import java.util.Vector; 

public class InternalExternalPrinting {

    static int line = 0;
    public static final int PRINT_INTERNAL = 0;
    public static final int PRINT_EXTERNAL = 1;

    public static DSJ_PrintObj printForm(long oid, int type, Vector addressComp) {
        DSJ_PrintObj obj = new DSJ_PrintObj();
        line = 0;
        try {
            // main purchase oder 
            PurchaseOrder purchOrder = new PurchaseOrder();
            try {
                purchOrder = PstPurchaseOrder.fetchExc(oid);
            } catch (Exception e) {
            }

            DSJ_PrinterService prnSvc = DSJ_PrinterService.getInstance();
            obj.setPrnIndex(1);
            //obj.setPageLength(50);
            obj.setTopMargin(1);
            obj.setLeftMargin(0);
            obj.setRightMargin(0);
            obj.setObjDescription("===== xxxx > PRINT," + purchOrder.getPoCode());
            if (type == PRINT_INTERNAL) {
                obj.setCpiIndex(DSJ_PrintObj.PRINTER_CONDENSED_MODE); // 12 CPI = 96 char /line
            } else {
                obj.setCpiIndex(DSJ_PrintObj.PRINTER_12_CPI); // 12 CPI = 96 char /line
            }

            // start creater object value
            line++;
            obj.newColumn(1, "");
            obj.setColumnValue(0, line, addressComp.get(0).toString().toUpperCase(), DSJ_PrintObj.TEXT_LEFT);
            line++;
            obj.setColumnValue(0, line, addressComp.get(1).toString(), DSJ_PrintObj.TEXT_LEFT);

            int[] intCols = {10, 80};
            obj.newColumn(2, "", intCols);

	    System.out.println("addressComp >> : "+addressComp);
            Vector vt = (Vector) addressComp.get(2);
            line++;
            obj.setColumnValue(0, line, vt.get(0).toString(), DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(1, line, ": " + vt.get(1).toString(), DSJ_PrintObj.TEXT_LEFT);

            vt = (Vector) addressComp.get(3);
            line++;
            obj.setColumnValue(0, line, vt.get(0).toString(), DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(1, line, ": " + vt.get(1).toString(), DSJ_PrintObj.TEXT_LEFT);

            vt = (Vector) addressComp.get(4);
            line++;
            obj.setColumnValue(0, line, vt.get(0).toString(), DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(1, line, ": " + vt.get(1).toString(), DSJ_PrintObj.TEXT_LEFT);

            vt = (Vector) addressComp.get(5);
            line++;
            obj.setColumnValue(0, line, vt.get(0).toString(), DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(1, line, ": " + vt.get(1).toString(), DSJ_PrintObj.TEXT_LEFT);

            obj.newColumn(1, "");
            line++;
            obj.setColumnValue(0, line, "PURCHASE ORDER", DSJ_PrintObj.TEXT_CENTER);
            line++;
            obj.setColumnValue(0, line, "", DSJ_PrintObj.TEXT_CENTER);

            int[] intCols1 = new int[6];
            if (type == PRINT_INTERNAL) {
                intCols1[0] = 10;
                intCols1[1] = 40;
                intCols1[2] = 10;
                intCols1[3] = 40;
                intCols1[4] = 8;
                intCols1[5] = 20;
            } else {
                intCols1[0] = 10;
                intCols1[1] = 15;
                intCols1[2] = 10;
                intCols1[3] = 35;
                intCols1[4] = 8;
                intCols1[5] = 20;
            }

            obj.newColumn(6, "", intCols1);
            line++;
            obj.setColumnValue(0, line, "Tanggal", DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(1, line, ": " + Formater.formatDate(purchOrder.getPurchDate(), "dd/MM/yyyy"), DSJ_PrintObj.TEXT_LEFT);

            ContactList contactList = new ContactList();
            try {
                contactList = PstContactList.fetchExc(purchOrder.getSupplierId());
            } catch (Exception e) {
            }
            obj.setColumnValue(2, line, "Supplier", DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(3, line, ": " + contactList.getCompName(), DSJ_PrintObj.TEXT_LEFT);

            obj.setColumnValue(4, line, "No.PO", DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(5, line, ": " + purchOrder.getPoCode(), DSJ_PrintObj.TEXT_LEFT);

            line++;
            obj.setColumnValue(0, line, "Tipe", DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(1, line, ": " + PstPurchaseOrder.fieldsPaymentType[purchOrder.getTermOfPayment()], DSJ_PrintObj.TEXT_LEFT);

            obj.setColumnValue(2, line, "Alamat", DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(3, line, ": " + contactList.getBussAddress(), DSJ_PrintObj.TEXT_LEFT);

            obj.setColumnValue(4, line, "Kontak", DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(5, line, ": " + contactList.getPersonName(), DSJ_PrintObj.TEXT_LEFT);

            line++;
            obj.setColumnValue(0, line, "Hari", DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(1, line, ": " + purchOrder.getCreditTime(), DSJ_PrintObj.TEXT_LEFT);

            obj.setColumnValue(2, line, "Telp", DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(3, line, ": " + contactList.getTelpNr(), DSJ_PrintObj.TEXT_LEFT);

            obj.setColumnValue(4, line, "Ppn", DSJ_PrintObj.TEXT_LEFT);
            obj.setColumnValue(5, line, ": " + purchOrder.getPpn() + " %", DSJ_PrintObj.TEXT_LEFT);

            if (type == PRINT_INTERNAL) {
                obj = setInternalDetail(obj, oid);
            } else {
                obj = setExternalDetail(obj, oid);
            }
            obj = setLastHeader(obj, purchOrder.getRemark());

        } catch (Exception exc) {
            System.out.println("Exc : " + exc);
        }
        return obj;
    }

    /**
     * print header detail dan data detail
     * untuk ke supplier/external
     * @param obj
     * @return
     */
    public static DSJ_PrintObj setExternalDetail(DSJ_PrintObj obj, long oid) {
        try {
            // get purcase order item
            String whereClause = "" + PstPurchaseOrderItem.fieldNames[PstPurchaseOrderItem.FLD_PURCHASE_ORDER_ID] + "=" + oid;
            Vector list = PstPurchaseOrderItem.list(0, 0, whereClause);

            line++;
            obj.setLineRptStr(line, 0, "-", obj.getCharacterSelected());
            int[] intCols = {16, 55, 8, 8, 8};
            obj.newColumn(5, "|", intCols);
            line++;
            obj.setColumnValue(0, line, "SKU", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(1, line, "NAMA BARANG", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(2, line, "QTY", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(3, line, "STN", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(4, line, "UNIT", DSJ_PrintObj.TEXT_CENTER);

            line++;
            obj.setLineRptStr(line, 0, "-", obj.getCharacterSelected());

            double totQty = 0;
            if (list != null && list.size() > 0) {
                for (int k = 0; k < list.size(); k++) {
                    Vector temp = (Vector) list.get(k);
                    PurchaseOrderItem poItem = (PurchaseOrderItem) temp.get(0);
                    Material mat = (Material) temp.get(1);
                    Unit unit = (Unit) temp.get(2);
                    MatCurrency matCurrency = (MatCurrency) temp.get(3);

                    line++;
                    obj.setColumnValue(0, line, mat.getSku(), DSJ_PrintObj.TEXT_LEFT);
                    obj.setColumnValue(1, line, mat.getName(), DSJ_PrintObj.TEXT_LEFT);
                    obj.setColumnValue(2, line, "" + poItem.getQuantity(), DSJ_PrintObj.TEXT_CENTER);
                    obj.setColumnValue(3, line, unit.getCode(), DSJ_PrintObj.TEXT_CENTER);
                    obj.setColumnValue(4, line, "" + unit.getQtyPerBaseUnit(), DSJ_PrintObj.TEXT_CENTER);
                    totQty = totQty + poItem.getQuantity();
                }
            }
            line++;
            obj.setLineRptStr(line, 0, "-", obj.getCharacterSelected());

            // untuk total item
            line++;
            obj.newColumn(5, "", intCols);
            obj.setColumnValue(1, line, "TOTAL", DSJ_PrintObj.TEXT_RIGHT);
            obj.setColumnValue(2, line, "" + totQty, DSJ_PrintObj.TEXT_CENTER);

        } catch (Exception e) {
        }
        return obj;
    }

    /**
     * print header detail dan data detail
     * untuk ke supplier/external
     * @param obj
     * @return
     */
    public static DSJ_PrintObj setInternalDetail(DSJ_PrintObj obj, long oid) {
        try {
            // get purcase order item
            String whereClause = "" + PstPurchaseOrderItem.fieldNames[PstPurchaseOrderItem.FLD_PURCHASE_ORDER_ID] + "=" + oid;
            Vector list = PstPurchaseOrderItem.list(0, 0, whereClause);

            // main purchase oder untuk mencari jumlah ppn yang di dapt item
            PurchaseOrder purchOrder = new PurchaseOrder();
            try {
                purchOrder = PstPurchaseOrder.fetchExc(oid);
            } catch (Exception e) {
            }

            line++;
            obj.setLineRptStr(line, 0, "-", obj.getCharacterSelected());
            int[] intCols = {15, 40, 7, 7, 12, 8, 8, 12, 12, 15};
            obj.newColumn(10, "|", intCols);
            line++;
            obj.setColumnValue(0, line, "SKU", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(1, line, "NAMA BARANG", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(2, line, "QTY", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(3, line, "STN", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(4, line, "HRG.BELI", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(5, line, "DISC.1", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(6, line, "DISC.2", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(7, line, "DISC.NOM", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(8, line, "NET.BELI", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(9, line, "SUB TOTAL", DSJ_PrintObj.TEXT_CENTER);

            line++;
            obj.setLineRptStr(line, 0, "-", obj.getCharacterSelected());

            double totQty = 0;
            double total = 0.0;
            if (list != null && list.size() > 0) {
                for (int k = 0; k < list.size(); k++) {
                    Vector temp = (Vector) list.get(k);
                    PurchaseOrderItem poItem = (PurchaseOrderItem) temp.get(0);
                    Material mat = (Material) temp.get(1);
                    Unit unit = (Unit) temp.get(2);

                    line++;
                    obj.setColumnValue(0, line, mat.getSku(), DSJ_PrintObj.TEXT_LEFT);
                    obj.setColumnValue(1, line, mat.getName(), DSJ_PrintObj.TEXT_LEFT);
                    obj.setColumnValue(2, line, "" + poItem.getQuantity(), DSJ_PrintObj.TEXT_CENTER);
                    obj.setColumnValue(3, line, unit.getCode(), DSJ_PrintObj.TEXT_CENTER);
                    obj.setColumnValue(4, line, FRMHandler.userFormatStringDecimal(poItem.getOrgBuyingPrice()), DSJ_PrintObj.TEXT_RIGHT);
                    obj.setColumnValue(5, line, FRMHandler.userFormatStringDecimal(poItem.getDiscount1()), DSJ_PrintObj.TEXT_RIGHT);
                    obj.setColumnValue(6, line, FRMHandler.userFormatStringDecimal(poItem.getDiscount2()), DSJ_PrintObj.TEXT_RIGHT);
                    obj.setColumnValue(7, line, FRMHandler.userFormatStringDecimal(poItem.getDiscNominal()), DSJ_PrintObj.TEXT_RIGHT);
                    obj.setColumnValue(8, line, FRMHandler.userFormatStringDecimal(poItem.getCurBuyingPrice()), DSJ_PrintObj.TEXT_RIGHT);
                    obj.setColumnValue(9, line, FRMHandler.userFormatStringDecimal(poItem.getTotal()), DSJ_PrintObj.TEXT_RIGHT);

                    totQty = totQty + poItem.getQuantity();
                    total = total + poItem.getTotal();
                }
            }

            line++;
            obj.setLineRptStr(line, 0, "-", obj.getCharacterSelected());

            line++;
            int[] intColsx = {15, 40, 7, 7, 12, 8, 8, 12, 12, 15};
            obj.newColumn(10, "", intColsx);

            // line untuk sub total semua total item
            obj.setColumnValue(1, line, "SUB TOTAL", DSJ_PrintObj.TEXT_RIGHT);
            obj.setColumnValue(2, line, "" + totQty, DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(9, line, FRMHandler.userFormatStringDecimal(total), DSJ_PrintObj.TEXT_RIGHT);

            // line untuk total ppn untuk semua item
            line++;
            obj.setColumnValue(1, line, "PPN", DSJ_PrintObj.TEXT_RIGHT);
            obj.setColumnValue(2, line, "", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(9, line, FRMHandler.userFormatStringDecimal(getTotalPPn(purchOrder.getPpn(), total)), DSJ_PrintObj.TEXT_RIGHT);

            // line untuk total keseluruhan + ppn
            line++;
            obj.setColumnValue(1, line, "TOTAL", DSJ_PrintObj.TEXT_RIGHT);
            obj.setColumnValue(2, line, "", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(9, line, FRMHandler.userFormatStringDecimal(total + getTotalPPn(purchOrder.getPpn(), total)), DSJ_PrintObj.TEXT_RIGHT);


        } catch (Exception e) {
        }
        return obj;
    }

    /**
     * print terakhir header description dan approval
     * @param obj
     * @return
     */
    public static DSJ_PrintObj setLastHeader(DSJ_PrintObj obj, String ket) {
        try {
            line++;
            obj.newColumn(1, "");
            obj.setColumnValue(0, line, "KET : " + ket, DSJ_PrintObj.TEXT_LEFT);
            line++;
            obj.setColumnValue(0, line, "", DSJ_PrintObj.TEXT_LEFT);

            line++;
            obj.newColumn(4, "");
            obj.setColumnValue(0, line, "Apoteker", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(1, line, "Mengetahui", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(2, line, "Menyetujui", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(3, line, "Supplier", DSJ_PrintObj.TEXT_CENTER);

            line++;
            obj.setColumnValue(0, line, "", DSJ_PrintObj.TEXT_CENTER);
            line++;
            obj.setColumnValue(0, line, "", DSJ_PrintObj.TEXT_CENTER);
            line++;
            obj.setColumnValue(0, line, "", DSJ_PrintObj.TEXT_CENTER);

            line++;
            obj.setColumnValue(0, line, "--------------------", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(1, line, "--------------------", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(2, line, "--------------------", DSJ_PrintObj.TEXT_CENTER);
            obj.setColumnValue(3, line, "--------------------", DSJ_PrintObj.TEXT_CENTER);

            line = line + 2;
            obj.setPageLength(line);
        } catch (Exception e) {
        }
        return obj;
    }


    // untuk mencari jumlah total dari total item
    public static double getTotalPPn(double ppn, double totalitem) {
        double dblppn = 0.0;
        try {
            dblppn = totalitem * ppn / 100;
        } catch (Exception e) {
        }
        return dblppn;
    }

    public static void main(String[] args) {
        printForm(0, PRINT_INTERNAL, new Vector());
    }
}
