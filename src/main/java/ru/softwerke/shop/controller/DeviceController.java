package ru.softwerke.shop.controller;

import ru.softwerke.shop.model.Device;
import ru.softwerke.shop.service.DeviceDataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Path("/device")
public class DeviceController {

    private DeviceDataService data;

    @Inject
    public DeviceController(DeviceDataService data) {
        this.data = data;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDevice(@PathParam("id") long id) throws IOException {
        if (data.getItemsList().isEmpty()) {
            throw new NotFoundException("No devices in system");
        }

        Device device = data.getItemById(id);
        if (device == null) {
            throw new NotFoundException("No client with id: " + id);
        }
        return Response.status(Response.Status.OK).entity(device).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Device createDevice(Device device) throws IOException {
        return data.addItem(device);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response filter (@Context UriInfo ui) throws IOException {
        if (data.getItemsList().isEmpty()) {
            throw new NotFoundException("No devices in system");
        }

        List<Device> result = data.getList(ui.getQueryParameters());
        if (result.isEmpty()) {
            throw new NotFoundException("No devices matching filters");
        }
        return Response.status(Response.Status.OK).entity(result).build();
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response patchDevice(String jsonDevice) throws IOException {
        if (data.getItemsList().isEmpty()) {
            throw new NotFoundException("No devices in system");
        }

        Device device = data.patchDevice(jsonDevice);

        return Response.status(Response.Status.OK).entity(device).build();
    }

    @POST
    @Path("color")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createColor(String jsonStr) throws RequestException {
        DeviceDataService.addColor(jsonStr);
    }

    @GET
    @Path("color")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getColors() {
        return DeviceDataService.colors.keySet();
    }

    @POST
    @Path("type")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addType(String jsonStr) throws RequestException {
        DeviceDataService.addType(jsonStr);
    }

    @GET
    @Path("type")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTypes() {
        return DeviceDataService.types;
    }

}
