/**
 * Created by IntelliJ IDEA.
 * User: gadnyana
 * Date: Apr 27, 2006
 * Time: 1:33:49 PM
 * To change this template use Options | File Templates.
 */
package com.dimata.posbo.report;
  
import com.lowagie.text.*; 
import com.lowagie.text.Font;
import com.lowagie.text.Rectangle;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import com.dimata.posbo.entity.warehouse.MatDispatch;
import com.dimata.posbo.entity.warehouse.MatDispatchItem;
import com.dimata.posbo.entity.warehouse.PstMatDispatch;
import com.dimata.posbo.entity.warehouse.PstMatDispatchItem;
import com.dimata.posbo.entity.masterdata.Material;
import com.dimata.posbo.entity.masterdata.Unit;
import com.dimata.util.Formater;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.location.PstLocation;
import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.FRMQueryString;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import java.awt.*;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.Vector;
import java.util.Properties;
import java.net.URL;

public class TransferPrintPDF extends HttpServlet {

    public static final String textHeaderMain[][] =
            {
        {"TRANSFER FORM", "Nomor", "Tanggal", "Jam", "Lokasi Asal", "Lokasi Tujuan", "Keterangan"},
        {"TRANSFER FORM", "Number", "Date", "Time", "From Location", "To Location", "Description"}};
    public static final String textHeaderItem[][] =
            {
        {"No", "Sku/Kode", "Nama Barang", "Unit", "Qty", "Harga Jual", "Total Jual", "Gondola"},
        {"No", "Code", "Name", "Unit", "Qty", "Price", "Total Price", "Brand"}};

    /* Generated by Together */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /** Destroys the servlet.
     */
    public void destroy() {

    }

    // setting the color values
    public static Color border = new Color(0x00, 0x00, 0x00);
    public static Color bgColor = new Color(220, 220, 220);

    // setting some fonts in the color chosen by the user
    public static Font fontTitle = new Font(Font.TIMES_NEW_ROMAN, 13, Font.BOLD, border);
    public static Font fontMainHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, border);
    public static Font fontHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.ITALIC, border);
    public static Font fontListHeader = new Font(Font.TIMES_NEW_ROMAN, 10, Font.BOLD, border);
    public static Font fontLsContent = new Font(Font.TIMES_NEW_ROMAN, 10);

    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Color bgColor = new Color(200, 200, 200);
        Rectangle rectangle = new Rectangle(20, 20, 20, 20);
        rectangle.rotate();
        Document document = new Document(PageSize.A4, 20, 20, 30, 30);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            //step2.2: creating an instance of the writer
            PdfWriter writer = PdfWriter.getInstance(document, baos);
            // step 3.1: adding some metadata to the document
            document.addSubject("This is a subject.");
            document.addSubject("This is a subject two.");

            //HeaderFooter header = new HeaderFooter(new Phrase("This is a header."), false);
            HeaderFooter footer = new HeaderFooter(new Phrase(new Chunk("", fontLsContent)), false);
            footer.setAlignment(Element.ALIGN_CENTER);
            footer.setBorder(HeaderFooter.NO_BORDER);
            //document.setHeader(header);
            document.setFooter(footer);

            document.open();

            /* get data from session */
            Vector list = new Vector();
            HttpSession sess = request.getSession(true);
            long oiddf = 0;
            int SESS_LANGUAGE = 0;
            String approot = "";
            int brandInUse = 0;
            try {
                SESS_LANGUAGE = FRMQueryString.requestInt(request, "sess_language");
                oiddf = FRMQueryString.requestLong(request, "hidden_dispatch_id");
                approot = FRMQueryString.requestString(request, "approot");
                brandInUse = FRMQueryString.requestInt(request, "brandinuse");
            } catch (Exception e) {
                System.out.println("Exc : " + e.toString());
            }

            System.out.println("brandInUse : " + brandInUse);

            MatDispatch matDispatch = new MatDispatch();
            Vector vctContent = new Vector();
            if (oiddf != 0) {
                try {
                    matDispatch = PstMatDispatch.fetchExc(oiddf);
                    vctContent = PstMatDispatchItem.list(0, 0, oiddf);
                } catch (Exception e) {
                    System.out.println("err : " + e.toString());
                }

                Vector vect = new Vector();
                vect.add("Mengetahui");
                vect.add("Penerima");
                vect.add("Pengirim");
                //vect.add("Inventory");

                String pathImage = "http://" + request.getServerName() + ":" + request.getServerPort() + approot + "/images/company.jpg";
                System.out.println("approot = " + pathImage);
                Image gambar = null;
                try {
                    gambar = Image.getInstance(pathImage);
                } catch (Exception ex) {
                    System.out.println("gambar >>>>>> = " + gambar.getImageMask());
                }

                document.add(getHeaderImage(SESS_LANGUAGE, gambar));
                document.add(getHeader(SESS_LANGUAGE, matDispatch));
                document = getContent(vctContent, document, writer, SESS_LANGUAGE, matDispatch, brandInUse);
                document.add(getHeaderApprove(vect));

            }

        } catch (Exception e) {
            System.out.println("Exception e : " + e.toString());
        }

        // step 5: closing the document
        document.close();

        // we have written the pdfstream to a ByteArrayOutputStream,
        // now we are going to write this outputStream to the ServletOutputStream
        // after we have set the contentlength (see http://www.lowagie.com/iText/faq.html#msie)
        response.setContentType("application/pdf");
        response.setContentLength(baos.size());
        ServletOutputStream out = response.getOutputStream();
        baos.writeTo(out);
        out.flush();
    }


    /* this method make table header */
    private static Table getHeaderImage(int SESS_LANGUAGE, Image gambar) throws BadElementException, DocumentException {
        Table table = new Table(2);
        try {
            int ctnInt[] = {40, 60};
            table.setBorderColor(new Color(255, 255, 255));
            table.setWidth(100);
            table.setWidths(ctnInt);
            table.setCellpadding(1);
            table.setCellspacing(0);

            //  nama company, alamat,telp
            table.setDefaultCellBorder(Table.NO_BORDER);
            table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            //URL url = new URL("");

            gambar.setAlignment(Image.MIDDLE);
            gambar.scalePercent(40);
            table.addCell(new Phrase(new Chunk(gambar, 0, 0)));
            table.addCell(new Phrase(textHeaderMain[SESS_LANGUAGE][0], fontTitle));

            table.addCell(new Phrase("", fontHeader));
            table.addCell(new Phrase("", fontTitle));

        } catch (Exception e) {
        }
        return table;
    }


    /* this method make table header */
    private static Table getHeader(int SESS_LANGUAGE, MatDispatch matDispatch) throws BadElementException, DocumentException {

        if (matDispatch.getOID() != 0) {

            int ctnInt[] = {10, 3, 39, 15, 3, 30};
            Table table = new Table(6);
            table.setBorderColor(new Color(255, 255, 255));
            table.setWidth(100);
            table.setWidths(ctnInt);
            table.setCellpadding(1);
            table.setCellspacing(0);

            //  nama company, alamat,telp
            table.setDefaultCellBorder(Table.NO_BORDER);
            table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.addCell(new Phrase(textHeaderMain[SESS_LANGUAGE][1], fontHeader));
            table.addCell(new Phrase(" :", fontHeader));
            table.addCell(new Phrase(matDispatch.getDispatchCode(), fontHeader));
            table.addCell(new Phrase(textHeaderMain[SESS_LANGUAGE][4], fontHeader));
            table.addCell(new Phrase(" :", fontHeader));
            Location loc = new Location();
            try {
                loc = PstLocation.fetchExc(matDispatch.getLocationId());
            } catch (Exception e) {
            }
            table.addCell(new Phrase(loc.getName(), fontHeader));

            // tanggal transaksi, lokasi tujuan
            table.setDefaultCellBorder(Table.NO_BORDER);
            table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.addCell(new Phrase(textHeaderMain[SESS_LANGUAGE][2], fontHeader));
            table.addCell(new Phrase(" :", fontHeader));
            table.addCell(new Phrase(Formater.formatDate(matDispatch.getDispatchDate(), "dd-MM-yyyy"), fontHeader));
            table.addCell(new Phrase(textHeaderMain[SESS_LANGUAGE][5], fontHeader));
            table.addCell(new Phrase(" :", fontHeader));
            Location loc2 = new Location();
            try {
                loc2 = PstLocation.fetchExc(matDispatch.getDispatchTo());
            } catch (Exception e) {
            }
            table.addCell(new Phrase(loc2.getName(), fontHeader));

            // time/jam ,
            table.setDefaultCellBorder(Table.NO_BORDER);
            table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.addCell(new Phrase(textHeaderMain[SESS_LANGUAGE][3], fontHeader));
            table.addCell(new Phrase(" :", fontHeader));
            table.addCell(new Phrase(Formater.formatDate(matDispatch.getDispatchDate(), "HH:mm:ss"), fontHeader));
            table.addCell(new Phrase("", fontHeader));
            table.addCell(new Phrase("", fontHeader));
            table.addCell(new Phrase("", fontHeader));

            return table;
        }

        return new Table(1);
    }

    private static Table getListHeader(int SESS_LANGUAGE, int brandInUse) throws BadElementException, DocumentException {
        //int ctnInt[] = {5, 15, 40, /*15,15,*/8,10,/*15,*/15/*,10*/};
        int ctnInt[] = { 5, 15, 30, 5, 5, 10, 10 };
        int ctnIntx[] = { 5, 15, 15, 30, 5, 5, 10, 10 };

        //Table table = new Table(10);
        Table table = new Table(7);
        try {
            if(brandInUse==1){
                table = new Table(8);
            }
            table.setBorderColor(new Color(255, 255, 255));
            table.setWidth(100);
            if(brandInUse==1){
                table.setWidths(ctnIntx);
            }else{
                table.setWidths(ctnInt);
            }
            table.setBorderWidth(0);
            table.setCellpadding(1);
            table.setCellspacing(1);

            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase(textHeaderItem[SESS_LANGUAGE][0], fontListHeader));

            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase(textHeaderItem[SESS_LANGUAGE][1], fontListHeader));

            if (brandInUse == 1) {
                table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
                table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                table.setDefaultCellBackgroundColor(bgColor);
                table.addCell(new Phrase(textHeaderItem[SESS_LANGUAGE][7], fontListHeader));
            }

            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase(textHeaderItem[SESS_LANGUAGE][2], fontListHeader));

            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase(textHeaderItem[SESS_LANGUAGE][3], fontListHeader));

            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase(textHeaderItem[SESS_LANGUAGE][4], fontListHeader));

            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase(textHeaderItem[SESS_LANGUAGE][5], fontListHeader));

            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase(textHeaderItem[SESS_LANGUAGE][6], fontListHeader));

        } catch (Exception e) {
            System.out.println("exc header" + e.toString());
        }

        return table;
    }

    private static Document getContent(Vector vct, Document document, PdfWriter writer,
            int SESS_LANGUAGE, MatDispatch matDispatch, int brandInUse) throws BadElementException, DocumentException {
        Table table = getListHeader(SESS_LANGUAGE, brandInUse);
        if (vct != null && vct.size() > 0) {
            try {
                double total = 0;
                for (int i = 0; i < vct.size(); i++) {
                    Vector temp = (Vector) vct.get(i);
                    MatDispatchItem dfItemx = (MatDispatchItem) temp.get(0);
                    Material mat = (Material) temp.get(1);
                    Unit unit = (Unit) temp.get(2);
                    total += dfItemx.getHppTotal();

                    table.setDefaultCellBackgroundColor(Color.WHITE);
                    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase(String.valueOf(i + 1), fontLsContent));

                    table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase(mat.getSku(), fontLsContent));

                    if (brandInUse == 1) {
                        table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
                        table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                        table.addCell(new Phrase(mat.getGondolaCode(), fontLsContent));
                    }

                    table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase(mat.getName(), fontLsContent));

                    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase(unit.getCode(), fontLsContent));

                    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase(FRMHandler.userFormatStringDecimal(dfItemx.getQty()), fontLsContent));

                    table.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase(FRMHandler.userFormatStringDecimal(dfItemx.getHpp()), fontLsContent));

                    table.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase(FRMHandler.userFormatStringDecimal(dfItemx.getHppTotal()), fontLsContent));

                /*if (!writer.fitsPage(table)) {
                table.deleteLastRow();
                i--;
                table.deleteLastRow();
                i--;
                document.add(table);
                document.newPage();
                table = getListHeader(SESS_LANGUAGE);
                }*/
                }
                document.add(table);
                document.add(getHeaderLast(SESS_LANGUAGE, matDispatch, total));
            } catch (Exception e) {
                System.out.println("exc contenct" + e.toString());
            }
        }

        return document;
    }

    private static Table getHeaderLast(int SESS_LANGUAGE, MatDispatch matDispatch, double total) throws BadElementException, DocumentException {
        int ctnInt[] = {10, 1, 60, 10, 1, 15};
        //Table table = new Table(10);
        Table table = new Table(6);
        try {
            table.setBorderColor(new Color(255, 255, 255));
            table.setWidth(100);
            table.setWidths(ctnInt);
            table.setBorderWidth(0);
            table.setCellpadding(0);
            table.setCellspacing(0);

            table.setDefaultCellBorder(Table.NO_BORDER);
            table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Table.ALIGN_TOP);
            //table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase(textHeaderMain[SESS_LANGUAGE][6], fontListHeader));

            table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Table.ALIGN_TOP);
            //table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase(":", fontListHeader));

            table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Table.ALIGN_TOP);
            //table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase(matDispatch.getRemark(), fontLsContent));

            table.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
            table.setDefaultVerticalAlignment(Table.ALIGN_TOP);
            //table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase("TOTAL", fontListHeader));

            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_TOP);
            //table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase(":", fontListHeader));

            table.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
            table.setDefaultVerticalAlignment(Table.ALIGN_TOP);
            //table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase(FRMHandler.userFormatStringDecimal(total), fontListHeader));

        } catch (Exception e) {
            System.out.println("exc header" + e.toString());
        }
        return table;
    }

    private static Table getHeaderApprove(Vector vect) throws BadElementException, DocumentException {
        Table table = new Table(vect.size());
        try {
            table.setBorderColor(new Color(255, 255, 255));
            table.setWidth(100);
            table.setBorderWidth(0);
            table.setCellpadding(1);
            table.setCellspacing(1);
            table.setDefaultCellBorder(Table.NO_BORDER);
            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_TOP);
            for (int k = 0; k < vect.size(); k++) {
                table.addCell("");
            }
            for (int k = 0; k < vect.size(); k++) {
                table.addCell(new Phrase(String.valueOf(vect.get(k)), fontLsContent));
            }
            for (int k = 0; k < vect.size(); k++) {
                table.addCell("");
                table.addCell("");
            }
            for (int k = 0; k < vect.size(); k++) {
                table.addCell(new Phrase(String.valueOf("(....................................)"), fontLsContent));
            }
        } catch (Exception e) {
            System.out.println("exc header" + e.toString());
        }
        return table;
    }

    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}
