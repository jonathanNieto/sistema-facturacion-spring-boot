package com.jonatnie.facturacionapp.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import com.jonatnie.facturacionapp.model.entity.Client;
import com.jonatnie.facturacionapp.model.service.IClientService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * ClientController
 */
@Controller
@SessionAttributes("client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    /* This is only for developing  TODO: remove before deploy*/
    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping(value="/detail/{id}")
    public String detail(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes redirectAttributes) {

        Client client = clientService.findOne(id);
        if (client == null) {
            redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/list";
        }

        model.put("client", client);
        model.put("title", "Detalle del cliente: " + client.getName());
        return "detail";
    }

    @GetMapping(value="/upload/{filename:.+}")
    public ResponseEntity<Resource> viewPhoto(@PathVariable String filename) {
        Path photoPath = Paths.get("upload").resolve(filename).toAbsolutePath();
        Resource resource = null;
        try {
            resource = new UrlResource(photoPath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Error: no se puede cargar la image => " + photoPath.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
               .body(resource);
    }
    
    

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<Client> clientList = clientService.findAll();
        model.addAttribute("title", "Listado de clientes");
        model.addAttribute("clients", clientList);
        return "list";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String create(Map<String, Object> model) {
        Client client = new Client();
        model.put("client", client);
        model.put("title", "Formulario Cliente");
        return "form";
    }

    @RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model,
            RedirectAttributes redirectAttributes) {

        Client client = null;

        if (id > 0) {
            client = clientService.findOne(id);
            if (client == null) {
                redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
                return "redirect:/list";
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/list";
        }
        model.put("title", "Editando Cliente");
        model.put("client", client);
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String save(@Valid Client client, BindingResult bindingResult, Model model,
            @RequestParam("file") MultipartFile photo, RedirectAttributes redirectAttributes,
            SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Formulario Cliente");
            return "form";
        }
        if (!photo.isEmpty()) {
            String uniqueFilename = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
            Path rootPath = Paths.get("upload").resolve(uniqueFilename);
            Path rootAbsolutePath = rootPath.toAbsolutePath();
            /* This is only for developing TODO: remove before deploy */
            log.info("rootPath: " + rootPath);
            log.info("rootAbsolutePath: "+ rootAbsolutePath);
                try {
                Files.copy(photo.getInputStream(), rootAbsolutePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
                redirectAttributes.addFlashAttribute("info", "Ha subido correctamente '" + uniqueFilename + "'");
            
            client.setPhoto(uniqueFilename);
        }
        String messageFlash = (client.getId() == null) ? "Cliente creado con éxito." : "Cliente editado con éxito.";
        clientService.save(client);
        sessionStatus.setComplete();
        redirectAttributes.addFlashAttribute("success", messageFlash);
        return "redirect:/list";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        if (id > 0) {
            clientService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Cliente eliminado con éxito");
        }
        return "redirect:/list";
    }

}