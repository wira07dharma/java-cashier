/**
 * Created by IntelliJ IDEA.
 * User: gadnyana
 * Date: Feb 25, 2005
 * Time: 12:00:34 PM
 * To change this template use Options | File Templates.
 */
package com.dimata.posbo.session.warehouse;

import com.dimata.posbo.entity.warehouse.PstStockCardReport;
import com.dimata.posbo.entity.warehouse.StockCardReport;
import com.dimata.posbo.entity.search.SrcStockCard;
import com.dimata.qdep.entity.I_DocType;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.posbo.entity.masterdata.*;
import com.dimata.posbo.form.search.*;
import com.dimata.posbo.entity.search.*;

import java.util.Vector;
import java.util.Date;

public class SessStockCard {

    public static final String SESS_STOCK_CARD = "SESS_STOCK_CARD";


    public static final int  CHECKING_STOCK_ALLOW_MINUS = 0;
    public static final int  CHECKING_STOCK_NOT_ALLOW_MINUS = 1;

    public static final String[] fieldNamesChecking = {
      "Allow Minus",
      "Not Allow Minus"
    };

    /**
     * untuk mencari tanggal ke belakang sesuaiK
     * dengan maksimal hari yang di tentukan
     * @param start
     * @param max
     * @return
     */
    public static Date parserDatePrevious(Date start, int max) {
        Date endDate = start;
        try {
            endDate.setDate(endDate.getDate() - max);
        } catch (Exception e) {
            System.out.println("error parserDatePrevious : " + e.toString());
        }
        return endDate;
    }


    /**
     * untuk mencari maksimal hari
     * sesuai parameter tanggal start dan end
     * @param start
     * @param endDate
     * @return
     */
    public static long parserDate(Date start, Date endDate) {
        long max = 0;
        try {
            long stime = endDate.getTime() - start.getTime();
            max = stime / (24 * 3600 * 1000);
            if (max == 0) {
                max = 1;
            } else {
                max = max + 1;
            }
        } catch (Exception e) {
        }
        return max;
    }

    /**
     * ini proses pengambilan data untuk laporan
     * stock card per barang
     * @param srcStockCard
     * @return
     */
    
//    synchronized public static Vector createHistoryStockCard(SrcStockCard srcStockCard){
//        return createHistoryStockCard(srcStockCard);
//    }
            
    
    synchronized public static Vector createHistoryStockCard(SrcStockCard srcStockCard) {
        Vector listAll = new Vector(1, 1);
        try {
            PstStockCardReport.deleteExc();
            if (srcStockCard.getStardDate() != null) {
                //System.out.println(srcStockCard.getDocStatus());
                Vector list = new Vector(1, 1);                
                // date untuk penyimpanan sementara setelah selesai pencarian
                // data sebelum rentang waktu
                Date endDate = new Date(srcStockCard.getEndDate().getTime());
                srcStockCard.setEndDate(null);
                
                // pencarian stock sebelum rentang waktu pencarian
                SessMatReceive.getDataMaterial(srcStockCard);
                SessMatReturn.getDataMaterial(srcStockCard);
                SessMatDispatch.getDataMaterial(srcStockCard);
                SessMatStockOpname.getDataMaterial(srcStockCard);
                SessReportSale.getDataMaterial(srcStockCard);
                SessMatCosting.getDataMaterial(srcStockCard);

                String order = PstStockCardReport.fieldNames[PstStockCardReport.FLD_TANGGAL];
                list = PstStockCardReport.list(0, 0, "", order);
                
                double qtyawal = prosesGetPrivousDataStockCard(list);
                StockCardReport stockCardReportPrev = new StockCardReport();
                Date startDate = new Date(srcStockCard.getStardDate().getTime());
                startDate.setDate(startDate.getDate());
                
                // tgl untuk view awal
                Date dateView = new Date(startDate.getTime());
                dateView.setDate(dateView.getDate() - 1);
                stockCardReportPrev.setDate(dateView);
                stockCardReportPrev.setDocCode("-");
                stockCardReportPrev.setKeterangan("Stok Awal ...");
                stockCardReportPrev.setQty(qtyawal);
                
                listAll.add(stockCardReportPrev); 
                PstStockCardReport.deleteExc();
                
                // pencarian stock card sesuai rentang waktu pencarian
                srcStockCard.setEndDate(endDate);
                SessMatReceive.getDataMaterial(srcStockCard);
                SessMatReturn.getDataMaterial(srcStockCard);
                SessMatDispatch.getDataMaterial(srcStockCard);
                SessMatStockOpname.getDataMaterial(srcStockCard);
                SessReportSale.getDataMaterial(srcStockCard);
                SessMatCosting.getDataMaterial(srcStockCard);

                order = PstStockCardReport.fieldNames[PstStockCardReport.FLD_TANGGAL];
                list = PstStockCardReport.list(0, 0, "", order);
                PstStockCardReport.deleteExc();
                listAll.add(list);
            }
        } catch (Exception e) {
            System.out.println("err>>> createHistoryStockCard : " + e.toString());
        }
        return listAll;
    }

    /**
     * ini proses pengambilan data untuk laporan
     * stock card per barang
     * @param srcStockCard
     * @return
     */
    synchronized public static Vector createHistoryStockCardWithTime(
        SrcStockCard srcStockCard) {
        Vector listAll = new Vector(1, 1);
        try {
            PstStockCardReport.deleteExc();
            if (srcStockCard.getStardDate() != null) {
                //System.out.println(srcStockCard.getDocStatus());
                Vector list = new Vector(1, 1);
                // date untuk penyimpanan sementara setelah selesai pencarian
                // data sebelum rentang waktu
                //Date endDate = new Date(srcStockCard.getEndDate().getTime());
                //srcStockCard.setEndDate(null);

                // pencarian stock sebelum rentang waktu pencarian
                

                SessMatReceive.getDataMaterialTime(srcStockCard);
                SessMatReturn.getDataMaterialTime(srcStockCard);
                SessMatDispatch.getDataMaterialTime(srcStockCard);
                SessMatStockOpname.getDataMaterialTime(srcStockCard);
                SessReportSale.getDataMaterialTime(srcStockCard);
                SessMatCosting.getDataMaterialTime(srcStockCard);

                double qtyOpname = 0.0;
                double qtyReceive = 0.0;
                double qtyDispatch = 0.0;
                double qtyReturn = 0.0;
                double qtySale =0.0;
                double qtyCosting = 0.0;

                SessMatReceive.getQtyStockMaterial(srcStockCard);
                qtyReceive = srcStockCard.getQty();
                SessMatReturn.getQtyStockMaterial(srcStockCard);
                SessMatDispatch.getQtyStockMaterial(srcStockCard);
                SessMatStockOpname.getQtyStockMaterial(srcStockCard);
                SessReportSale.getQtyStockMaterial(srcStockCard);
                qtySale = srcStockCard.getQty();
                SessMatCosting.getQtyStockMaterial(srcStockCard);

                String order = PstStockCardReport.fieldNames[PstStockCardReport.FLD_TANGGAL];
                list = PstStockCardReport.list(0, 0, "", order);
                //StockCardReport stockCardReportPrev = new StockCardReport();


               double qtyawal = prosesGetPrivousDataStockCard(list);
                StockCardReport stockCardReportPrev = new StockCardReport();
                Date startDate = new Date(srcStockCard.getStardDate().getTime());
                startDate.setDate(startDate.getDate());

                // tgl untuk view awal
                Date dateView = new Date(startDate.getTime());
                dateView.setDate(dateView.getDate() - 1);
                stockCardReportPrev.setDate(dateView);
                stockCardReportPrev.setDocCode("-");
                stockCardReportPrev.setKeterangan("Stok Awal ...");
                stockCardReportPrev.setQty(qtyawal);

                listAll.add(stockCardReportPrev);
                PstStockCardReport.deleteExc();

                // pencarian stock card sesuai rentang waktu pencarian
                //srcStockCard.setEndDate(endDate);
                SessMatReceive.getDataMaterialTime(srcStockCard);
                SessMatReturn.getDataMaterialTime(srcStockCard);
                SessMatDispatch.getDataMaterialTime(srcStockCard);
                SessMatStockOpname.getDataMaterialTime(srcStockCard);
                SessReportSale.getDataMaterialTime(srcStockCard);
                SessMatCosting.getDataMaterialTime(srcStockCard);

                order = PstStockCardReport.fieldNames[PstStockCardReport.FLD_TANGGAL];
                list = PstStockCardReport.list(0, 0, "", order);
                PstStockCardReport.deleteExc();
                listAll.add(list);
            }
        } catch (Exception e) {
            System.out.println("err>>> createHistoryStockCard : " + e.toString());
        }
        return listAll;
    }
    
        /**
     * ini proses pengambilan qtyStok untuk RepostingStok
     * stock card per barang
     * @param srcStockCard
     * @return
     * by Mirahu 20120530
     */
    synchronized public static Vector createHistoryStockCardRePosting(
        SrcMaterialRepostingStock srcMaterialRepostingStock) {
        Vector listAll = new Vector(1, 1);
        try {
            PstStockCardReport.deleteExc();
            if (srcMaterialRepostingStock.getDateTo() != null) {
                //System.out.println(srcStockCard.getDocStatus());
                Vector list = new Vector(1, 1);                
                // date untuk penyimpanan sementara setelah selesai pencarian
                // data sebelum rentang waktu
                Date endDate = new Date(srcMaterialRepostingStock.getDateTo().getTime());
                srcMaterialRepostingStock.setDateFrom(null);
                
                // pencarian stock sebelum rentang waktu pencarian
                //SessMatReceive.getDataMaterialReposting(srcMaterialRepostingStock);
                //SessMatReturn.getDataMaterialReposting(srcMaterialRepostingStock);
                //SessMatDispatch.getDataMaterialReposting(srcMaterialRepostingStock);
                //SessMatStockOpname.getDataMaterialReposting(srcMaterialRepostingStock);
                //SessReportSale.getDataMaterialReposting(srcMaterialRepostingStock);
                //SessMatCosting.getDataMaterialReposting(srcMaterialRepostingStock);

                String order = PstStockCardReport.fieldNames[PstStockCardReport.FLD_TANGGAL];
                list = PstStockCardReport.list(0, 0, "", order);
                
                double qtyawal = prosesGetPrivousDataStockCard(list);
                StockCardReport stockCardReportPrev = new StockCardReport();
                Date startDate = new Date(srcMaterialRepostingStock.getDateFrom().getTime());
                startDate.setDate(startDate.getDate());
                
                // tgl untuk view awal
                Date dateView = new Date(startDate.getTime());
                dateView.setDate(dateView.getDate() - 1);
                stockCardReportPrev.setDate(dateView);
                stockCardReportPrev.setDocCode("-");
                stockCardReportPrev.setKeterangan("Stok Awal ...");
                stockCardReportPrev.setQty(qtyawal);
                
                listAll.add(stockCardReportPrev); 
                PstStockCardReport.deleteExc();
                
                // pencarian stock card sesuai rentang waktu pencarian
                srcMaterialRepostingStock.setDateTo(endDate);
                //SessMatReceive.getDataMaterialReposting(srcMaterialRepostingStock);
                //SessMatReturn.getDataMaterialReposting(srcMaterialRepostingStock);
                //SessMatDispatch.getDataMaterialReposting(srcMaterialRepostingStock);
                //SessMatStockOpname.getDataMaterialReposting(srcMaterialRepostingStock);
                //SessReportSale.getDataMaterialReposting(srcMaterialRepostingStock);
                //SessMatCosting.getDataMaterialReposting(srcMaterialRepostingStock);
            

                order = PstStockCardReport.fieldNames[PstStockCardReport.FLD_TANGGAL];
                list = PstStockCardReport.list(0, 0, "", order);
                PstStockCardReport.deleteExc();
                listAll.add(list);
            }
        } catch (Exception e) {
            System.out.println("err>>> createHistoryStockCard : " + e.toString());
        }
        return listAll;
    }

    /**
     * ini proses pengambilan data untuk laporan
     * stock card per barang
     * @param srcStockCard
     * @return
     */
    synchronized public static Vector createHistoryConsigmentStockCard(
        SrcStockCard srcStockCard) {
        Vector listAll = new Vector(1, 1);
        try {
            PstStockCardReport.deleteExc();
            if (srcStockCard.getStardDate() != null) {
                Vector list = new Vector(1, 1);
                // date untuk penyimpanan sementara setelah selesai pencarian
                // data sebelum rentang waktu
                Date endDate = new Date(srcStockCard.getEndDate().getTime());
                srcStockCard.setEndDate(null); 
                // pencarian stock sebelum rentang waktu pencarian
                SessMatConReceive.getDataMaterial(srcStockCard);
                SessMatConReturn.getDataMaterial(srcStockCard);
                SessMatConDispatch.getDataMaterial(srcStockCard);
                //SessMatStockOpname.getDataMaterial(srcStockCard);
                SessReportSale.getDataMaterial(srcStockCard);
                //SessMatCosting.getDataMaterial(srcStockCard);
                System.out.println("Selesai get data pertama ************** ");

                String order = PstStockCardReport.fieldNames[PstStockCardReport.FLD_TANGGAL];
                list = PstStockCardReport.list(0, 0, "", order);
                double qtyawal = prosesGetPrivousDataStockCard(list);
                StockCardReport stockCardReportPrev = new StockCardReport();
                Date startDate = new Date(srcStockCard.getStardDate().getTime());
                startDate.setDate(startDate.getDate());
                // tgl untuk view awal
                Date dateView = new Date(startDate.getTime());
                dateView.setDate(dateView.getDate() - 1);
                stockCardReportPrev.setDate(dateView);
                stockCardReportPrev.setDocCode("-");
                stockCardReportPrev.setKeterangan("Stok Awal ...");
                stockCardReportPrev.setQty(qtyawal);

                listAll.add(stockCardReportPrev);
                PstStockCardReport.deleteExc();

                // pencarian stock card sesuai rentang waktu pencarian
                srcStockCard.setEndDate(endDate);
                SessMatConReceive.getDataMaterial(srcStockCard);
                SessMatConReturn.getDataMaterial(srcStockCard);
                SessMatConDispatch.getDataMaterial(srcStockCard);
                //SessMatStockOpname.getDataMaterial(srcStockCard);
                SessReportSale.getDataMaterial(srcStockCard);
                //SessMatCosting.getDataMaterial(srcStockCard);

                order = PstStockCardReport.fieldNames[PstStockCardReport.FLD_TANGGAL];
                list = PstStockCardReport.list(0, 0, "", order);
                PstStockCardReport.deleteExc();
                listAll.add(list);
            }
        } catch (Exception e) {
            System.out.println("err>>> createHistoryStockCard : " + e.toString());
        }
        return listAll;
    }

    /**
     * untuk filter qty
     * @param objectClass
     * @return
     */
    public static double prosesGetPrivousDataStockCard(Vector objectClass) {
        double qtyawal = 0;
        try {
            for (int i = 0; i < objectClass.size(); i++) {
                StockCardReport stockCardReport = (StockCardReport) objectClass.get(i);
                switch (stockCardReport.getDocType()) {
                    case I_DocType.MAT_DOC_TYPE_LMRR:
                        qtyawal = qtyawal + stockCardReport.getQty();
                        break;
                    case I_DocType.MAT_DOC_TYPE_ROMR:
                        qtyawal = qtyawal - stockCardReport.getQty();
                        break;
                    case I_DocType.MAT_DOC_TYPE_DF:
                        qtyawal = qtyawal - stockCardReport.getQty();
                        break;
                    case I_DocType.MAT_DOC_TYPE_OPN:
                        qtyawal = stockCardReport.getQty();
                        break;
                    case I_DocType.MAT_DOC_TYPE_COS:
                        qtyawal = qtyawal - stockCardReport.getQty();
                        break;
                    case I_DocType.MAT_DOC_TYPE_SALE:
                        switch (stockCardReport.getTransaction_type()) {
                            case PstBillMain.TYPE_INVOICE:
                                qtyawal = qtyawal - stockCardReport.getQty();
                                break;
                            case PstBillMain.TYPE_RETUR:
                                qtyawal = qtyawal + stockCardReport.getQty();
                                break;
                            case PstBillMain.TYPE_GIFT:

                                break;
                            case PstBillMain.TYPE_COST:

                                break;
                            case PstBillMain.TYPE_COMPLIMENT:

                                break;
                        }
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("prosesGetPrivousDataStockCard : " + e.toString());
        }
        return qtyawal;
    }
}
