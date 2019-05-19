package ru.softwerke.shop.controller;

import ru.softwerke.shop.model.Client;
import ru.softwerke.shop.service.ClientDataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;

@Path("/customer")
public class ClientController {
    private ClientDataService data;

    @Inject
    public ClientController(ClientDataService data){
        this.data = data;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClient(@PathParam("id") long id) throws IOException {
        if (data.getItemsList().isEmpty()) {
            throw new NotFoundException("No clients in system");
        }

        Client client = data.getItemById(id);
        if (client == null) {
            throw new NotFoundException("No client with id: " + id);
        }
        return Response.status(Response.Status.OK).entity(client).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client createClient(Client client) throws IOException {
        return data.addItem(client);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response filter (@Context UriInfo ui) throws IOException {
        if (data.getItemsList().isEmpty()) {
            throw new NotFoundException("No clients in system");
        }

        List<Client> result = data.getList(ui.getQueryParameters());
        if (result.isEmpty()) {
            throw new NotFoundException("No clients matching filters");
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
