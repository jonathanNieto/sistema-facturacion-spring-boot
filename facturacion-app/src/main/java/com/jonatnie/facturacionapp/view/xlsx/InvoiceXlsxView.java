package com.jonatnie.facturacionapp.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jonatnie.facturacionapp.model.entity.Invoice;
import com.jonatnie.facturacionapp.model.entity.ItemInvoice;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

/**
 * InvoiceXlsxView
 */
@Component("invoice/detail.xlsx")
public class InvoiceXlsxView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        MessageSourceAccessor messages = getMessageSourceAccessor();

        Invoice invoice = (Invoice) model.get("invoice");
        String filename = messages.getMessage("txt.client.list.invoice") + "_" + invoice.getDescription() + ".xlsx" ;
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        Sheet sheet = workbook.createSheet(messages.getMessage("txt.client.list.invoice"));

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue(messages.getMessage("txt.invoice.detail.view.client.detail"));

        row = sheet.createRow(1);
        cell = row.createCell(0);
        cell.setCellValue(invoice.getClient().getName() + " " + invoice.getClient().getLastname());

        row = sheet.createRow(2);
        cell = row.createCell(0);
        cell.setCellValue(invoice.getClient().getEmail());

        sheet.createRow(4).createCell(0).setCellValue(messages.getMessage("txt.invoice.detail.view.invoice.data"));
        sheet.createRow(5).createCell(0).setCellValue(messages.getMessage("txt.detail.view.folio") + ": " + invoice.getId());
        sheet.createRow(6).createCell(0).setCellValue(messages.getMessage("txt.detail.view.description") +": " + invoice.getDescription());
        sheet.createRow(7).createCell(0).setCellValue(messages.getMessage("txt.detail.view.createAt") +": " + invoice.getCreateAt());

        CellStyle theaderStyle = workbook.createCellStyle();
        theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
        theaderStyle.setBorderTop(BorderStyle.MEDIUM);
        theaderStyle.setBorderRight(BorderStyle.MEDIUM);
        theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
        theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
        theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle tbodyStyle = workbook.createCellStyle();
        tbodyStyle.setBorderBottom(BorderStyle.THIN);
        tbodyStyle.setBorderTop(BorderStyle.THIN);
        tbodyStyle.setBorderRight(BorderStyle.THIN);
        tbodyStyle.setBorderLeft(BorderStyle.THIN);

        Row header = sheet.createRow(9);
        header.createCell(0).setCellValue(messages.getMessage("txt.invoice.detail.view.product"));
        header.createCell(1).setCellValue(messages.getMessage("txt.invoice.detail.view.price"));
        header.createCell(2).setCellValue(messages.getMessage("txt.invoice.detail.view.quantity"));
        header.createCell(3).setCellValue(messages.getMessage("txt.invoice.detail.view.total"));

        header.getCell(0).setCellStyle(theaderStyle);
        header.getCell(1).setCellStyle(theaderStyle);
        header.getCell(2).setCellStyle(theaderStyle);
        header.getCell(3).setCellStyle(theaderStyle);
        
        int rownum = 10;
        for (ItemInvoice item : invoice.getItemList()) {
            Row rowItem = sheet.createRow(rownum++);
            cell = rowItem.createCell(0);
            cell.setCellValue(item.getProduct().getName());
            cell.setCellStyle(tbodyStyle);
            cell = rowItem.createCell(1);
            cell.setCellValue("$ " + item.getProduct().getCost().toString());
            cell.setCellStyle(tbodyStyle);
            cell = rowItem.createCell(2);
            cell.setCellValue(item.getQuantity().toString());
            cell.setCellStyle(tbodyStyle);
            cell = rowItem.createCell(3);
            cell.setCellValue("$ " + item.calculateAmount().toString());
            cell.setCellStyle(tbodyStyle);
        }
        Row rowTotal = sheet.createRow(rownum);
        cell = rowTotal.createCell(2);
        cell.setCellValue(messages.getMessage("txt.invoice.detail.view.price.purchase"));
        cell.setCellStyle(tbodyStyle);
        cell = rowTotal.createCell(3);
        cell.setCellValue("$ " + invoice.getTotal().toString());
        cell.setCellStyle(tbodyStyle);

    }

}