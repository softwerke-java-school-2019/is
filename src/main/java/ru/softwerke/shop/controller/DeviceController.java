package ru.softwerke.shop.controller;

import ru.softwerke.shop.model.Device;
import ru.softwerke.shop.model.ModelUtils;
import ru.softwerke.shop.service.DeviceDataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.awt.*;
import java.util.List;
import java.util.Set;
import org.json.*;

@Path("/device")
public class DeviceController {
    private DeviceDataService data;

    @Inject
    public DeviceController(DeviceDataService data) {
        this.data = data;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> getItems() {
        return data.getItemsList();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Device getDevice(@PathParam("id") long id) {
        return data.getItemById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Device createDevice(Device device) throws RequestException {
        ModelUtils.checkDevice(device);
        return data.addItem(device);
    }

    @GET
    @Path("filter")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Device> filter (@Context UriInfo ui) throws RequestException {
            return data.getList(ui.getQueryParameters());
    }

    @POST
    @Path("color")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createColor(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
        DeviceDataService.addColor(json.getString("name"),
                new Color(json.getInt("r"), json.getInt("g"), json.getInt("b")));
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
    public void addType(String jsonStr) throws JSONException {
        JSONObject json = new JSONObject(jsonStr);
        DeviceDataService.addType(json.getString("name"));
    }

    @GET
    @Path("type")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getTypes() {
        return DeviceDataService.types;
    }
}
