package com.jonatnie.facturacionapp.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.jonatnie.facturacionapp.model.entity.Client;
import com.jonatnie.facturacionapp.model.entity.Invoice;
import com.jonatnie.facturacionapp.model.entity.ItemInvoice;
import com.jonatnie.facturacionapp.model.entity.Product;
import com.jonatnie.facturacionapp.model.service.IClientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @GetMapping(value="/detail/{id}")
    public String detail(@PathVariable(value = "id") Long id, Model model, RedirectAttributes redirectAttributes) {

        Invoice invoice = clientService.fetchByIdWithClientWithItemInvoiceWithProduct(id);
        if (invoice == null) {
            redirectAttributes.addFlashAttribute("error", "Factura no encontrada");
            return "redirect:/list";
        }

        model.addAttribute("invoice", invoice);
        model.addAttribute("title", "Factura: " + invoice.getDescription());
        return "invoice/detail";
    }
    

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
    
    @PostMapping("/form")
    public String save(@Valid Invoice invoice, 
                        BindingResult bindingResult,
                        Model model,
                        @RequestParam(name = "item_id[]", required = false) Long[] itemIds,
                        @RequestParam(name = "quantity[]", required = false) Integer[] quantities,
                        RedirectAttributes redirectAttributes,
                        SessionStatus sessionStatus) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Crear Factura");
            return "invoice/form";
        }
        if (itemIds == null || itemIds.length == 0) {
            model.addAttribute("title", "Crear Factura");
            model.addAttribute("error", "La factura no puede crearse sin productos");
            /* return "redirect:/invoice/form/" + invoice.getClient().getId(); */
            return "invoice/form";
        }
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

    @GetMapping(value="/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        Invoice invoice = clientService.findInvoiceById(id);
        if (invoice != null) {
            clientService.deleteInvoice(id);
            redirectAttributes.addFlashAttribute("success", "Factura eliminada con éxito");
            return "redirect:/detail/" + invoice.getClient().getId();
        }
        redirectAttributes.addFlashAttribute("error", "La factura no fue encontrada, no se puede eliminar");

        return "redirect:/list";
    }
    
    
    
}