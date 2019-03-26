package com.jonatnie.facturacionapp.view.pdf;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jonatnie.facturacionapp.model.entity.Invoice;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

/**
 * InvoicePdfView
 */
@Component("invoice/detail")
public class InvoicePdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        Invoice invoice = (Invoice) model.get("invoice");
        
        PdfPTable pdfPTable =  new PdfPTable(1);
        pdfPTable.setSpacingAfter(20);
        pdfPTable.addCell("Datos del cliente");
        pdfPTable.addCell(invoice.getClient().getName() + " " + invoice.getClient().getLastname());
        pdfPTable.addCell(invoice.getClient().getEmail());

        PdfPTable pdfPTable2 = new PdfPTable(1);
        pdfPTable2.setSpacingAfter(20);
        pdfPTable2.addCell("Datos de la factura");
        pdfPTable2.addCell("Folio: " + invoice.getId());
        pdfPTable2.addCell("Descripción: " + invoice.getDescription());
        pdfPTable2.addCell("Fecha: " + invoice.getCreateAt());

        document.add(pdfPTable);
        document.add(pdfPTable2);
    }

    
}