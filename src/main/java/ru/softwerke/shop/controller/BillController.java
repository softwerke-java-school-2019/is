package ru.softwerke.shop.controller;

import ru.softwerke.shop.model.Bill;
import ru.softwerke.shop.service.BillDataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;

@Path("/bill")
public class BillController {
    private BillDataService data;

    @Inject
    public BillController(BillDataService data) {
        this.data = data;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBill(@PathParam("id") long id) throws IOException {
        if (data.getItemsList().isEmpty()) {
            throw new NotFoundException("No bills in system");
        }
        Bill bill = data.getItemById(id);
        if (bill == null) {
            throw new NotFoundException("No bill with id: " + id);
        }
        return Response.status(Response.Status.OK).entity(bill).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bill createBill(Bill bill) throws IOException {
        return data.addItem(bill);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response filter (@Context UriInfo ui) throws IOException {
        if (data.getItemsList().isEmpty()) {
            throw new NotFoundException("No bills in system");
        }

        List<Bill> result = data.getList(ui.getQueryParameters());
        if (result.isEmpty()) {
            throw new NotFoundException("No bills matching filters");
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }


}
