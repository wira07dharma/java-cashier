/**
 * User: gwawan
 * Date: Sep 6, 2007
 * Time: 8:50:13 PM
 * To change this template use Options | File Templates.
 */
package com.dimata.posbo.report.stock;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Vector;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.dimata.qdep.form.FRMHandler;
import com.dimata.qdep.form.FRMQueryString;

public class ReportStockPdf extends HttpServlet {
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
    public static Font fontListHeader = new Font(Font.TIMES_NEW_ROMAN, 8, Font.BOLD, border);
    public static Font fontLsContent = new Font(Font.TIMES_NEW_ROMAN, 8);
    
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
             String approot = "";
            HttpSession sess = request.getSession(true);
            try {
                list = (Vector) sess.getValue("REPORT_STOCK");
                approot = FRMQueryString.requestString(request, "approot");
            } catch (Exception e) {
                System.out.println("Exc : " + e.toString());
                list = new Vector();
            }
            //add logo di document pdf
             String pathImage = "http://"+request.getServerName()+":"+request.getServerPort()+approot+ "/images/company_pdf.jpg";
            System.out.println("approot = "+pathImage);
            com.lowagie.text.Image gambar = null;
            try{
                gambar = com.lowagie.text.Image.getInstance(pathImage);
            }catch(Exception ex){
                System.out.println("gambar >>>>>> = "+gambar.getImageMask());
            }

            Vector header = new Vector(1, 1);
            Vector vctContent = new Vector(1, 1);
            if ((list != null) && (list.size() > 0)) {
                document.add(getContent(list, document, writer, gambar));
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
    private static Table getHeader(Vector vct) throws BadElementException, DocumentException {
        
        if (vct != null && vct.size() > 0) {
            
            int ctnInt[] = {10,90};
            Table table = new Table(2);
            table.setBorderColor(new Color(255, 255, 255));
            table.setWidth(100);
            table.setWidths(ctnInt);
            table.setCellpadding(1);
            table.setCellspacing(0);
            
            //  nama company, alamat,telp
            table.setDefaultColspan(2);
            table.setDefaultCellBorder(Table.NO_BORDER);
            table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.addCell(new Phrase((String)vct.get(0), fontHeader));
            table.addCell(new Phrase((String)vct.get(1), fontHeader));
            table.addCell(new Phrase((String)vct.get(2), fontHeader));
            
            // judul report, periode report
            table.setDefaultCellBorder(table.NO_BORDER);
            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.addCell(new Phrase((String)vct.get(3), fontTitle));
            table.addCell(new Phrase("", fontTitle));
            
            // periode, lokasi, kategori, supplier
            table.setDefaultColspan(1);
            table.setDefaultCellBorder(table.NO_BORDER);
            table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.addCell(new Phrase((String)vct.get(4), fontListHeader));
            table.addCell(new Phrase((String)vct.get(5), fontListHeader));
            if((String)vct.get(7) != "") { //location
                table.addCell(new Phrase((String)vct.get(6), fontListHeader));
                table.addCell(new Phrase((String)vct.get(7), fontListHeader));
            }
            if((String)vct.get(9) != "") { //category or supplier
                table.addCell(new Phrase((String)vct.get(8), fontListHeader));
                table.addCell(new Phrase((String)vct.get(9), fontListHeader));
            }
            
            return table;
        }
        
        return new Table(1);
    }

     /* this method make table header */
    private static Table getHeaderImage(com.lowagie.text.Image gambar) throws BadElementException, DocumentException {
        Table table = new Table(2);
        try {
            int ctnInt[] = {40, 60};
            table.setBorderColor(new Color(255, 255, 255));
            table.setWidth(100);
            table.setWidths(ctnInt);
            table.setCellpadding(1);
            table.setCellspacing(0);

            gambar.setAlignment(com.lowagie.text.Image.MIDDLE);
            gambar.scalePercent(40);

            /** image, report title */
            table.setDefaultCellBorder(Table.NO_BORDER);
            table.setDefaultRowspan(2);
            table.setDefaultHorizontalAlignment(Table.ALIGN_TOP);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.addCell(new Phrase(new Chunk(gambar,0,0)));
            table.addCell("");
            //table.addCell(new Phrase(PurchaseOrderPrintPDF.textHeaderMain[SESS_LANGUAGE][0], PurchaseOrderPrintPDF.fontTitleUnderline));
            table.setDefaultRowspan(1);
            table.addCell(new Phrase(new Chunk("")));

        } catch (Exception e) {
        }
        return table;
    }
    
    private static Table getListHeader(Vector header) throws BadElementException, DocumentException {
        int ctnInt[] = {5, 15, 31, 13, 10, 13, 13};
        Table table = new Table(7);
        try {
            table.setBorderColor(new Color(255, 255, 255));
            table.setWidth(100);
            table.setWidths(ctnInt);
            table.setBorderWidth(0);
            table.setCellpadding(1);
            table.setCellspacing(0);
            
            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase((String)header.get(0), fontListHeader));
            
            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase((String)header.get(1), fontListHeader));
            
            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase((String)header.get(2),fontListHeader));
            
            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase((String)header.get(3), fontListHeader));
            
            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase((String)header.get(4), fontListHeader));
            
            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase((String)header.get(5), fontListHeader));
            
            table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.setDefaultCellBackgroundColor(bgColor);
            table.addCell(new Phrase((String)header.get(6), fontListHeader));
            
        }catch(Exception e){
            System.out.println("exc header"+e.toString());
        }
        
        return table;
    }
    
    private static Table getListFooter(Table table, Vector footer) throws BadElementException, DocumentException {
        try {
            /** SUB TOTAL */
            table.setDefaultColspan(6);
            
            // Title
            table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.addCell(new Phrase("SUB TOTAL", fontListHeader));
            
            table.setDefaultColspan(1);
            
            // Nilai Stock
            table.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.addCell(new Phrase((String)footer.get(0), fontListHeader));
            
            
            /** GRAND TOTAL */
            table.setDefaultColspan(6);
            
            // Title
            table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.addCell(new Phrase("GRAND TOTAL", fontListHeader));
            
            table.setDefaultColspan(1);
            
            // Nilai Stock
            table.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
            table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
            table.addCell(new Phrase((String)footer.get(1), fontListHeader));
            
        }catch(Exception e){
            System.out.println("exc footer"+e.toString());
        }
        
        return table;
    }
    
    private static Table getContent(Vector vct, Document document, PdfWriter writer,  com.lowagie.text.Image gambar) throws BadElementException, DocumentException {
        document.add(getHeaderImage(gambar));
        document.add(getHeader((Vector)vct.get(0)));
        
        Vector vctContent = (Vector)vct.get(1);
        Vector header = (Vector)vctContent.get(0);
        Vector body = (Vector)vctContent.get(1);
        
        Vector footer = (Vector)vct.get(2);
        
        Table table = getListHeader(header);
        
        boolean newPage = false;
        double nilaiStock = 0;
        double subTotalNilaiStock = 0;
        
        if (body != null && body.size() > 0) {
            try{
                for (int i = 0; i < body.size(); i++) {
                    Vector vctfrs = (Vector)body.get(i);
                    
                    nilaiStock = Double.parseDouble((String)vctfrs.get(6));
                    subTotalNilaiStock += nilaiStock;
                    
                    table.setDefaultCellBackgroundColor(Color.WHITE);
                    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase((String)vctfrs.get(0), fontLsContent));
                    
                    table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase((String)vctfrs.get(1), fontLsContent));
                    
                    table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase((String)vctfrs.get(2), fontLsContent));
                    
                    table.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase((String)vctfrs.get(3), fontLsContent));
                    
                    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase((String)vctfrs.get(4), fontLsContent));
                    
                    table.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase((String)vctfrs.get(5), fontLsContent));
                    
                    table.setDefaultHorizontalAlignment(Table.ALIGN_RIGHT);
                    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
                    table.addCell(new Phrase(FRMHandler.userFormatStringDecimal(nilaiStock), fontLsContent));
                    
                    /** buat baris kosong */
                    table.setDefaultColspan(7);
                    table.addCell(new Phrase("", fontLsContent));
                    table.addCell(new Phrase("", fontLsContent));
                    table.addCell(new Phrase("", fontLsContent));
                    table.addCell(new Phrase("", fontLsContent));
                    table.setDefaultColspan(1);
                    
                    if (!writer.fitsPage(table)) {
                        /** hapus baris kosong */
                        table.deleteLastRow();
                        table.deleteLastRow();
                        table.deleteLastRow();
                        table.deleteLastRow();
                        
                        Vector tempFooter = new Vector();
                        /** Sub Total*/
                        tempFooter.add(FRMHandler.userFormatStringDecimal(subTotalNilaiStock));
                        
                        /** Grand Total */
                        tempFooter.add((String)footer.get(0));
                        
                        document.add(getListFooter(table, tempFooter));
                        
                        /** set variabel sub* = 0 untuk next page */
                        subTotalNilaiStock = 0;
                        
                        document.newPage();
                        document.add(getHeaderImage(gambar));
                        document.add(getHeader((Vector)vct.get(0)));
                        table = getListHeader(header);
                        
                        newPage = true;
                    }
                    else {
                        /** hapus baris kosong */
                        table.deleteLastRow();
                        table.deleteLastRow();
                        table.deleteLastRow();
                        table.deleteLastRow();
                        
                        newPage = false;
                    }
                    
                }
                
                if(newPage == false) {
                    Vector tempFooter = new Vector();
                    /** Sub Total*/
                    tempFooter.add(FRMHandler.userFormatStringDecimal(subTotalNilaiStock));
                    
                    /** Grand Total */
                    tempFooter.add((String)footer.get(0));
                    
                    table = getListFooter(table, tempFooter);
                }
                
            }catch(Exception e){
                System.out.println("exc contenct"+e.toString());
            }
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
