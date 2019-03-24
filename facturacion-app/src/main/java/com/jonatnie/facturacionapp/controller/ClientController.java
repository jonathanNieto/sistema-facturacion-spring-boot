package com.jonatnie.facturacionapp.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.jonatnie.facturacionapp.model.entity.Client;
import com.jonatnie.facturacionapp.model.service.IClientService;
import com.jonatnie.facturacionapp.model.service.IUploadFileService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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
    @Autowired
    private IUploadFileService uploadFileService;

	protected final Log logger = LogFactory.getLog(this.getClass());

    /* @Secured("ROLE_USER") */
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/upload/{filename:.+}")
    public ResponseEntity<Resource> viewPhoto(@PathVariable String filename) {
        Resource resource = null;
        try {
            resource = uploadFileService.load(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Secured("ROLE_USER")
    @GetMapping(value = "/detail/{id}")
    public String detail(@PathVariable(value = "id") Long id, Map<String, Object> model,
            RedirectAttributes redirectAttributes) {

        Client client = clientService.fetchByIdWithInvoices(id);
        if (client == null) {
            redirectAttributes.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/list";
        }

        model.put("client", client);
        model.put("title", "Detalle del cliente: " + client.getName() + " " + client.getLastname());
        return "detail";
    }

    @RequestMapping(value = {"/list", "/"}, method = RequestMethod.GET)
    public String list(Model model, Authentication authentication, HttpServletRequest request) {

        /**
         * ways to get authenticated user in the controller.
         * use Authentication from org.springframework.security.core.Authentication;.
         * 
         */
        if (authentication != null) {
            logger.info("Usuario autenticado: " + authentication.getName());
        }
        /* static way using SecurityContextHolder */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            logger.info("using SecurityContextHolder => Usuario autenticado: " + auth.getName());            
        }

        /**
         * ways to get roles
         */
        if (this.hasRole("ROLE_ADMIN")) {
            logger.info(auth.getName() + " tienes role de admin");
        }else{
            logger.info(auth.getName() + " NO tienes role de admin");
        }

        /* validate authorization */
        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "");
        if (securityContext.isUserInRole("ROLE_ADMIN")) {
            logger.info("USING SecurityContextHolderAwareRequestWrapper " + auth.getName() + " tienes role de admin");            
        }else{
            logger.info("USING SecurityContextHolderAwareRequestWrapper " + auth.getName() + " NO tienes role de admin");
        }

        if (request.isUserInRole("ROLE_ADMIN")) {
            logger.info("USING HttpServletRequest: " + auth.getName() + " tienes role de admin");
        } else {
            logger.info(
                    "USING HttpServletRequest: " + auth.getName() + " NO tienes role de admin");
        }


        
        List<Client> clientList = clientService.findAll();
        model.addAttribute("title", "Listado de clientes");
        model.addAttribute("clients", clientList);
        return "list";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String create(Map<String, Object> model) {
        Client client = new Client();
        model.put("client", client);
        model.put("title", "Formulario Cliente");
        return "form";
    }

    /* @Secured("ROLE_ADMIN") */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String save(@Valid Client client, BindingResult bindingResult, Model model,
            @RequestParam("file") MultipartFile photo, RedirectAttributes redirectAttributes,
            SessionStatus sessionStatus) {
        if (bindingResult.hasErrors()) {
            System.out.println(client.getCreateAt());
            model.addAttribute("title", "Formulario Cliente");
            return "form";
        }
        if (!photo.isEmpty()) {

            if (client.getId() != null && client.getId() > 0 && client.getPhoto() != null
                    && client.getPhoto().length() > 0) {

                uploadFileService.delete(client.getPhoto());
            }
            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(photo);
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

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable(value = "id") Long id, RedirectAttributes redirectAttributes) {
        if (id > 0) {
            Client client = clientService.findOne(id);
            clientService.delete(id);
            redirectAttributes.addFlashAttribute("success", "Cliente eliminado con éxito");

            if (uploadFileService.delete(client.getPhoto())) {
                if (client.getPhoto().length() > 0) {
                    redirectAttributes.addFlashAttribute("info", "Foto " + client.getPhoto() + " de " + client.getName() + " eliminada con éxito");
                }
            }
        }
        return "redirect:/list";
    }

    private boolean hasRole(String role){
        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null) {
            return false;
        }

        Authentication auth = context.getAuthentication();
        if (auth == null) {
            return false;
        }

        Collection<? extends  GrantedAuthority> authorities = auth.getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority(role));
        /* for (GrantedAuthority authority : authorities) {
            if (role.equals(authority.getAuthority())) {
                return true;
            }
        } 
        return false;*/
    }

}