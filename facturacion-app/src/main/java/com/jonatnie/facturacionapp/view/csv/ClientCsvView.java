package com.jonatnie.facturacionapp.view.csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jonatnie.facturacionapp.model.entity.Client;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

/**
 * ClientCsvView
 */
@Component("list")
public class ClientCsvView extends AbstractView {

    public ClientCsvView() {
        setContentType("text/csv");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        MessageSourceAccessor messages = getMessageSourceAccessor();

        Client client = (Client) model.get("client");
        /* String filename = messages.getMessage("txt.client.list.invoice") + "_" + client.getName() + ".xlsx"; */
        String filename = "clients.csv";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setContentType(getContentType());
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

}