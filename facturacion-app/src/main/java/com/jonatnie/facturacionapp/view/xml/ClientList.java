package com.jonatnie.facturacionapp.view.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.jonatnie.facturacionapp.model.entity.Client;

/**
 * ClientList
 */
@XmlRootElement(name = "clients")
public class ClientList {

    @XmlElement(name = "client")
    public List<Client> clientList;

    public ClientList(){}

    public ClientList(List<Client> clientList ) {
        this.clientList = clientList;
    }

    /**
     * @return the clientList
     */
    public List<Client> getClientList() {
        return clientList;
    }

    /**
     * @param clientList the clientList to set
     */
    public void setClientList(List<Client> clientList) {
        this.clientList = clientList;
    }

}