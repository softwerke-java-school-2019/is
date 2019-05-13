package ru.softwerke.shop.controller;

import ru.softwerke.shop.utils.ModelUtils;
import ru.softwerke.shop.model.Bill;
import ru.softwerke.shop.service.BillDataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
    public Response getBill(@PathParam("id") long id) {
        Bill bill = data.getItemById(id);
        if (bill == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("No bill with id: " + id).build();
        }
        return Response.status(Response.Status.OK).entity(bill).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Bill createBill(Bill bill) throws RequestException {
        ModelUtils.checkBill(bill, data.getClientData(), data.getDeviceData());
        return data.addItem(bill);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response filter (@Context UriInfo ui) throws RequestException {
        if (data.getItemsList().isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No bills in system").build();
        }

        List<Bill> result = data.getList(ui.getQueryParameters());
        if (result.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("No bills matching filters").build();
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }


}
