package com.jonatnie.facturacionapp.view.pdf;

import java.awt.Color;
import java.util.Locale;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

/**
 * InvoicePdfView
 */
@Component("invoice/detail")
public class InvoicePdfView extends AbstractPdfView {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        Locale locale = localeResolver.resolveLocale(request);
        MessageSourceAccessor messages = getMessageSourceAccessor();

        Invoice invoice = (Invoice) model.get("invoice");
        String filename = messages.getMessage("txt.client.list.invoice") + "_" + invoice.getDescription() + ".pdf";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        
        
        PdfPTable pdfPTable =  new PdfPTable(1);
        pdfPTable.setSpacingAfter(20);
    
        PdfPCell cell = new PdfPCell(new Phrase(messageSource.getMessage("txt.invoice.detail.view.client.detail", null, locale)));
        cell.setBackgroundColor(new Color(184, 218, 255));
        cell.setPadding(8f);
        pdfPTable.addCell(cell);
    
        pdfPTable.addCell(invoice.getClient().getName() + " " + invoice.getClient().getLastname());
        pdfPTable.addCell(invoice.getClient().getEmail());

        PdfPTable pdfPTable2 = new PdfPTable(1);
        pdfPTable2.setSpacingAfter(20);
        cell = new PdfPCell(new Phrase(messageSource.getMessage("txt.invoice.detail.view.invoice.data", null, locale)));
        cell.setBackgroundColor(new Color(195, 230, 203));
        cell.setPadding(8f);
        pdfPTable2.addCell(cell);

        pdfPTable2.addCell(messages.getMessage("txt.detail.view.folio") + ": " + invoice.getId());
        pdfPTable2.addCell(messages.getMessage("txt.detail.view.description") +": " + invoice.getDescription());
        pdfPTable2.addCell(messages.getMessage("txt.detail.view.createAt") +": " + invoice.getCreateAt());

        document.add(pdfPTable);
        document.add(pdfPTable2);

        PdfPTable pdfPTable3 = new PdfPTable(4);
        pdfPTable3.setWidths(new float []{3.5f, 1, 1, 1});
        pdfPTable3.addCell(messageSource.getMessage("txt.invoice.detail.view.product", null, locale));
        pdfPTable3.addCell(messageSource.getMessage("txt.invoice.detail.view.price", null, locale));
        pdfPTable3.addCell(messageSource.getMessage("txt.invoice.detail.view.quantity", null, locale));
        pdfPTable3.addCell(messageSource.getMessage("txt.invoice.detail.view.total", null, locale));

        for (ItemInvoice item : invoice.getItemList()) {
            pdfPTable3.addCell(item.getProduct().getName());
            pdfPTable3.addCell("$ " + item.getProduct().getCost().toString());
            cell = new PdfPCell(new Phrase(item.getQuantity().toString()));
            cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            pdfPTable3.addCell(cell);
            pdfPTable3.addCell("$ " + item.calculateAmount().toString());
        }

        cell = new PdfPCell(new Phrase(messages.getMessage("txt.invoice.detail.view.price.purchase")));
        cell.setColspan(3);
        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        pdfPTable3.addCell(cell);
        pdfPTable3.addCell("$ " + invoice.getTotal().toString());

        document.add(pdfPTable3);

    }

    
}