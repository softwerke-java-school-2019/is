package ru.softwerke.shop.controller;

import ru.softwerke.shop.model.Client;
import ru.softwerke.shop.utils.ModelUtils;
import ru.softwerke.shop.service.ClientDataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
    public Response getClient(@PathParam("id") long id) {
        Client client = data.getItemById(id);
        if (client == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No client with id: " + id ).build();
        }
        return Response.status(Response.Status.OK).entity(client).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Client createClient(Client client) throws RequestException {
        ModelUtils.checkClient(client);
        return data.addItem(client);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response filter (@Context UriInfo ui) throws RequestException {
        if (data.getItemsList().isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No clients in system").build();
        }

        List<Client> result = data.getList(ui.getQueryParameters());
        if (result.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No clients matching filters").build();
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }
}
