package com.jonatnie.facturacionapp.view.pdf;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jonatnie.facturacionapp.model.entity.Invoice;
import com.jonatnie.facturacionapp.model.entity.ItemInvoice;
import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
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
    
        PdfPCell cell = new PdfPCell(new Phrase("Datos del cliente"));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(8f);
        pdfPTable.addCell(cell);
    
        pdfPTable.addCell(invoice.getClient().getName() + " " + invoice.getClient().getLastname());
        pdfPTable.addCell(invoice.getClient().getEmail());

        PdfPTable pdfPTable2 = new PdfPTable(1);
        pdfPTable2.setSpacingAfter(20);
        cell = new PdfPCell(new Phrase("Datos de la factura"));
        cell.setBackgroundColor(new Color(195, 230, 203));
        cell.setPadding(8f);
        pdfPTable2.addCell(cell);

        pdfPTable2.addCell("Folio: " + invoice.getId());
        pdfPTable2.addCell("Descripción: " + invoice.getDescription());
        pdfPTable2.addCell("Fecha: " + invoice.getCreateAt());

        document.add(pdfPTable);
        document.add(pdfPTable2);

        PdfPTable pdfPTable3 = new PdfPTable(4);
        pdfPTable3.setWidths(new float []{3.5f, 1, 1, 1});
        pdfPTable3.addCell("Producto");
        pdfPTable3.addCell("Precio");
        pdfPTable3.addCell("Cantidad");
        pdfPTable3.addCell("Total");

        for (ItemInvoice item : invoice.getItemList()) {
            pdfPTable3.addCell(item.getProduct().getName());
            pdfPTable3.addCell("$ " + item.getProduct().getCost().toString());
            cell = new PdfPCell(new Phrase(item.getQuantity().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pdfPTable3.addCell(cell);
            pdfPTable3.addCell("$ " + item.calculateAmount().toString());
        }

        cell = new PdfPCell(new Phrase("Total: "));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        pdfPTable3.addCell(cell);
        pdfPTable3.addCell("$ " + invoice.getTotal().toString());

        document.add(pdfPTable3);

    }

    
}