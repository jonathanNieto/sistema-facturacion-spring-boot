package com.jonatnie.facturacionapp.view.csv;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jonatnie.facturacionapp.model.entity.Client;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

/**
 * ClientCsvView
 */
@Component("list.csv")
public class ClientCsvView extends AbstractView {

    public ClientCsvView() {
        setContentType("text/csv");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        List<Client> clientList = (List<Client>) model.get("clients");
        String filename = "clients.csv";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.setContentType(getContentType());

        ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String header[] = {"id", "name", "lastname", "email", "createAt"};

        beanWriter.writeHeader(header);
        for (Client client : clientList) {
            beanWriter.write(client, header);
        }
        beanWriter.close();
    }

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

}