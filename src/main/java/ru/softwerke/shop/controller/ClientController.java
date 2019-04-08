package ru.softwerke.shop.controller;

import ru.softwerke.shop.model.BeanComparator;
import ru.softwerke.shop.model.Client;
import ru.softwerke.shop.model.DateDeserializer;
import ru.softwerke.shop.service.ClientDataService;
import ru.softwerke.shop.service.ClientFilter;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Collections;
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
        return data.getClientsList();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Client getClient(@PathParam("id") long id) {
        return data.getClientByID(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createClient(Client client) {
        if (client == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.OK).entity(data.addClient(client)).build();
    }

    @GET
    @Path("filter")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Client> filter (@QueryParam("second name") String secondName,
                                @QueryParam("name") String name,
                                @QueryParam("patronymic") String patronymic,
                                @QueryParam("birthday from") String dateFromStr,
                                @QueryParam("birthday to") String dateToStr,
                                @QueryParam("order by") String parameter,
                                @QueryParam("reverse") boolean reverse) {
        LocalDate dateFrom = null;
        LocalDate dateTo = null;

        if (dateFromStr != null) {
            dateFrom = LocalDate.parse(dateFromStr, DateDeserializer.formatter);
        }

        if (dateToStr != null) {
            dateTo = LocalDate.parse(dateToStr, DateDeserializer.formatter);
        }

        List<Client> ans = data.filter(new ClientFilter(name, secondName, patronymic, dateFrom, dateTo));

        if (reverse) {
            ans.sort(new BeanComparator(parameter).reversed());
        }
        else {
            ans.sort(new BeanComparator(parameter));
        }
        return ans;
    }
}
