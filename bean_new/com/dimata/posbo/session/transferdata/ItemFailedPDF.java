package com.dimata.posbo.session.transferdata;

import com.dimata.posbo.entity.masterdata.*;
import com.dimata.qdep.form.FRMQueryString;
import com.dimata.qdep.form.FRMHandler;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.util.Vector;
import java.awt.*;
import java.awt.Color;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class ItemFailedPDF extends HttpServlet {

    public static final String textHeaderMain[][] =
	    {
	{"DAFTAR PERBAIKAN PRODUK", "Kode/Barcode", "Nama"},
	{"LIST of CATALOG TO FIXED", "Code/Barcode", "Name"}};
    public static final String textHeaderItem[][] =
	    {
	{"No", "Kode","Barcode" ,"Nama Barang", "Qty"},
	{"No", "Code","Barcode" ,"Description", "Qty"}};

    /* Generated by Together */
    public void init(ServletConfig config) throws ServletException {
	super.init(config);
    }

    /**
     * Destroys the servlet.
     */
    public void destroy() {

    }

    // setting the color values
    public static Color border = new Color(0x00, 0x00, 0x00);
    public static Color bgColor = new Color(220, 220, 220);

    // setting some fonts in the color chosen by the user
    public static com.lowagie.text.Font fontTitle = new com.lowagie.text.Font(com.lowagie.text.Font.TIMES_NEW_ROMAN, 13, com.lowagie.text.Font.BOLD, ItemFailedPDF.border);
    public static com.lowagie.text.Font fontTitleUnderline = new com.lowagie.text.Font(com.lowagie.text.Font.TIMES_NEW_ROMAN, 13, com.lowagie.text.Font.BOLD + com.lowagie.text.Font.UNDERLINE, ItemFailedPDF.border);
    public static com.lowagie.text.Font fontMainHeader = new com.lowagie.text.Font(com.lowagie.text.Font.TIMES_NEW_ROMAN, 10, com.lowagie.text.Font.BOLD, ItemFailedPDF.border);
    public static com.lowagie.text.Font fontHeader = new com.lowagie.text.Font(com.lowagie.text.Font.TIMES_NEW_ROMAN, 10, com.lowagie.text.Font.ITALIC, ItemFailedPDF.border);
    public static com.lowagie.text.Font fontHeaderUnderline = new com.lowagie.text.Font(com.lowagie.text.Font.TIMES_NEW_ROMAN, 10, com.lowagie.text.Font.ITALIC + com.lowagie.text.Font.UNDERLINE, ItemFailedPDF.border);
    public static com.lowagie.text.Font fontListHeader = new com.lowagie.text.Font(com.lowagie.text.Font.TIMES_NEW_ROMAN, 10, com.lowagie.text.Font.BOLD, ItemFailedPDF.border);
    public static com.lowagie.text.Font fontLsContent = new com.lowagie.text.Font(com.lowagie.text.Font.TIMES_NEW_ROMAN, 8);
    public static com.lowagie.text.Font fontLsContentUnderline = new com.lowagie.text.Font(com.lowagie.text.Font.TIMES_NEW_ROMAN, 8, com.lowagie.text.Font.UNDERLINE, ItemFailedPDF.border);

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	Color bgColor = new Color(200, 200, 200);
	com.lowagie.text.Rectangle rectangle = new com.lowagie.text.Rectangle(20, 20, 20, 20);
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
	    HeaderFooter footer = new HeaderFooter(new Phrase(new Chunk("", ItemFailedPDF.fontLsContent)), false);
	    footer.setAlignment(Element.ALIGN_CENTER);
	    footer.setBorder(HeaderFooter.NO_BORDER);
	    //document.setHeader(header);
	    document.setFooter(footer);

	    document.open();

	    /* get data from session */
	    Vector list = new Vector();
	    HttpSession sess = request.getSession(true);
	    //SrcMaterial srcMaterial = new SrcMaterial();
	    //try{
	    //    srcMaterial = (SrcMaterial)sess.getValue();
	    //}catch(Exception e){}
	    long oidMemberReg = 0;
	    int SESS_LANGUAGE = 0;
	    long oidCateg = 0;
	    long oidMerk = 0;
	    String code = "";
	    String name = "";
	    String approot = "";
	    try {
		list = (Vector)sess.getValue("barcode_session_failed");
		SESS_LANGUAGE = FRMQueryString.requestInt(request, "sess_language");
	    } catch (Exception e) {
		System.out.println("Exc : " + e.toString());
	    }

	    if (list.size()>0) {
		document.add(ItemFailedPDF.getHeaderImage(SESS_LANGUAGE));
		document = ItemFailedPDF.getContent(list, document, SESS_LANGUAGE);
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
    private static Table getHeaderImage(int SESS_LANGUAGE) throws BadElementException, DocumentException {
	Table table = new Table(1);
	try {
	    int ctnInt[] = {100};
	    table.setBorderColor(new Color(255, 255, 255));
	    table.setWidth(100);
	    table.setWidths(ctnInt);
	    table.setCellpadding(1);
	    table.setCellspacing(0);

	    //  nama company, alamat,telp
	    table.setDefaultCellBorder(Table.NO_BORDER);
	    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
	    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
	    table.addCell(new Phrase(ItemFailedPDF.textHeaderMain[SESS_LANGUAGE][0], ItemFailedPDF.fontTitleUnderline));
	    table.addCell(new Phrase("", ItemFailedPDF.fontTitle));

	} catch (Exception e) {
	}
	return table;
    }

    private static Table getListHeader(int SESS_LANGUAGE) throws BadElementException, DocumentException {
	int ctnInt[] = {3, 11, 35, 10, 10};
	Table table = new Table(5);
	try {
	    table.setBorderColor(new Color(255, 255, 255));
	    table.setWidth(100);
	    table.setWidths(ctnInt);
	    table.setBorderWidth(0);
	    table.setCellpadding(1);
	    table.setCellspacing(1);

	    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
	    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
	    table.setDefaultCellBackgroundColor(ItemFailedPDF.bgColor);
	    table.addCell(new Phrase(ItemFailedPDF.textHeaderItem[SESS_LANGUAGE][0], ItemFailedPDF.fontListHeader));

	    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
	    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
	    table.setDefaultCellBackgroundColor(ItemFailedPDF.bgColor);
	    table.addCell(new Phrase(ItemFailedPDF.textHeaderItem[SESS_LANGUAGE][1], ItemFailedPDF.fontListHeader));

	    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
	    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
	    table.setDefaultCellBackgroundColor(ItemFailedPDF.bgColor);
	    table.addCell(new Phrase(ItemFailedPDF.textHeaderItem[SESS_LANGUAGE][2], ItemFailedPDF.fontListHeader));

	    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
	    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
	    table.setDefaultCellBackgroundColor(ItemFailedPDF.bgColor);
	    table.addCell(new Phrase(ItemFailedPDF.textHeaderItem[SESS_LANGUAGE][3], ItemFailedPDF.fontListHeader));

	    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
	    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
	    table.setDefaultCellBackgroundColor(ItemFailedPDF.bgColor);
	    table.addCell(new Phrase(ItemFailedPDF.textHeaderItem[SESS_LANGUAGE][4], ItemFailedPDF.fontListHeader));

	} catch (Exception e) {
	    System.out.println("exc header" + e.toString());
	}

	return table;
    }

    private static Document getContent(Vector vct, Document document, int SESS_LANGUAGE) throws BadElementException, DocumentException {
	Table table = ItemFailedPDF.getListHeader(SESS_LANGUAGE);
	if (vct != null && vct.size() > 0) {
	    try {
		double total = 0;
		for (int i = 0; i < vct.size(); i++) {
		    //Material mat = (Material) vct.get(i);
                    Material matPdf = (Material) vct.get(i);

		    table.setDefaultCellBackgroundColor(Color.WHITE);
		    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
		    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
		    table.addCell(new Phrase(String.valueOf(i + 1), ItemFailedPDF.fontLsContent));

		    table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
		    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
		    //table.addCell(new Phrase(mat.getSku(), ItemFailedPDF.fontLsContent));
                    table.addCell(new Phrase(matPdf.getSku(), ItemFailedPDF.fontLsContent));

		    table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
		    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
		    //table.addCell(new Phrase(mat.getBarCode(), ItemFailedPDF.fontLsContent));
                    table.addCell(new Phrase(matPdf.getBarCode(), ItemFailedPDF.fontLsContent));

		    table.setDefaultHorizontalAlignment(Table.ALIGN_LEFT);
		    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
		    //table.addCell(new Phrase(mat.getName(), ItemFailedPDF.fontLsContent));
                    table.addCell(new Phrase(matPdf.getName(), ItemFailedPDF.fontLsContent));

		    table.setDefaultHorizontalAlignment(Table.ALIGN_CENTER);
		    table.setDefaultVerticalAlignment(Table.ALIGN_MIDDLE);
		    //table.addCell(new Phrase(String.valueOf(mat.getCurrBuyPrice()), ItemFailedPDF.fontLsContent));
                    table.addCell(new Phrase(String.valueOf(matPdf.getCurrBuyPrice()), ItemFailedPDF.fontLsContent));

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
	    } catch (Exception e) {
		System.out.println("exc contenct" + e.toString());
	    }
	}

	return document;
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
	return "Short description";
    }
}
