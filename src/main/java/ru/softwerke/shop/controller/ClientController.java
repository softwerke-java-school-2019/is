package ru.softwerke.shop.controller;

import ru.softwerke.shop.model.Client;
import ru.softwerke.shop.model.ModelUtils;
import ru.softwerke.shop.service.ClientDataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/client")
public class ClientController {
    private ClientDataService data;

    @Inject
    public ClientController(ClientDataService data){
        this.data = data;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> getItems() {
        return data.getItemsList();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClient(@PathParam("id") long id) {
        return data.getItemById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client createClient(Client client) throws RequestException {
        ModelUtils.checkClient(client);
        return data.addItem(client);
    }

    @GET
    @Path("filter")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> filter (@Context UriInfo ui) throws RequestException {
            return data.getList(ui.getQueryParameters());
    }
}
