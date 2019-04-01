package ru.softwerke.shop.controller;

import ru.softwerke.shop.model.BeanComparator;
import ru.softwerke.shop.model.Client;
import ru.softwerke.shop.model.DateDeserializer;
import ru.softwerke.shop.service.ClientDataService;
import ru.softwerke.shop.service.ClientFilter;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Path("/client")
public class ClientController {
    private ClientDataService data = new ClientDataService();

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
    public Client createClient(Client client) {
        return data.addClient(client);
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
            Collections.sort(ans, new BeanComparator(parameter).reversed());
        }
        else {
            Collections.sort(ans, new BeanComparator(parameter));
        }
        return ans;
    }
}
