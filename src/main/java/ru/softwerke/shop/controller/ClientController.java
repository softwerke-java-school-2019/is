package ru.softwerke.shop.controller;

import ru.softwerke.shop.model.Client;
import ru.softwerke.shop.service.ClientDataService;
import ru.softwerke.shop.service.DataService;
import ru.softwerke.shop.service.QueryParamsException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
    public List<Client> getItem() {
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
    public Response createClient(Client client) {
        if (client == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).entity(data.addItem(client)).build();
    }

    @GET
    @Path("filter")
    @Produces(MediaType.APPLICATION_JSON)
    public Response filter (@Context UriInfo ui) {
        try {
            return Response.status(Response.Status.OK).entity(data.getList(ui.getQueryParameters())).build();
        } catch (QueryParamsException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
    }
}
