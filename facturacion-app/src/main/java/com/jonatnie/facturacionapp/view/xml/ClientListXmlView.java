package com.jonatnie.facturacionapp.view.xml;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jonatnie.facturacionapp.model.entity.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.xml.MarshallingView;

/**
 * ClientListXmlView
 */
@Component("list.xml")
public class ClientListXmlView extends MarshallingView {

    @Autowired
    public ClientListXmlView(Jaxb2Marshaller marshaller){
        super(marshaller);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        model.remove("title");
        List<Client> clientList = (List<Client>) model.get("clients");
        model.remove("clients");
        model.put("listClients", new ClientList(clientList));
        super.renderMergedOutputModel(model, request, response);
    }

}