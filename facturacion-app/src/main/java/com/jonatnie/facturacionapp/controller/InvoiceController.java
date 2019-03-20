package com.jonatnie.facturacionapp.controller;

import java.util.List;
import java.util.Map;

import com.jonatnie.facturacionapp.model.entity.Client;
import com.jonatnie.facturacionapp.model.entity.Invoice;
import com.jonatnie.facturacionapp.model.entity.ItemInvoice;
import com.jonatnie.facturacionapp.model.entity.Product;
import com.jonatnie.facturacionapp.model.service.IClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * InvoiceController
 */
@Controller
@RequestMapping("/invoice")
@SessionAttributes("invoice")
public class InvoiceController {

    @Autowired
    private IClientService clientService;

    @GetMapping(value = "/form/{clientId}")
    public String create(@PathVariable(value = "clientId") Long id, Map<String, Object> model, RedirectAttributes redirectAttributes) {
        Client client = clientService.findOne(id);
        if (client == null) {
            redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/list";
        }

        Invoice invoice = new Invoice();
        invoice.setClient(client);

        model.put("invoice", invoice);
        model.put("title", "Formulario Factura");

        return "invoice/form";
    }

    @GetMapping(value = "/load-products/{term}", produces = { "application/json" })
    public @ResponseBody List<Product> loadProducts(@PathVariable String term) {
        return clientService.findByName(term);
    }
    
    @PostMapping(value="/form")
    public String save(Invoice invoice, @RequestParam(name = "item_id[]") Long[] itemIds,
                        @RequestParam(name = "quantity[]") Integer[] quantities,
                        RedirectAttributes redirectAttributes,
                        SessionStatus sessionStatus) {

        for (int i = 0; i < itemIds.length; i++) {
            Product product = clientService.findProductById(itemIds[i]);

            ItemInvoice itemInvoice = new ItemInvoice();
            itemInvoice.setQuantity(quantities[i]);
            itemInvoice.setProduct(product);
            invoice.addItemInvoice(itemInvoice);
        }

        clientService.saveInvoice(invoice);
        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute("success", "Factura creada con éxito");

        return "redirect:/detail/" + invoice.getClient().getId();
    }
    
    
}