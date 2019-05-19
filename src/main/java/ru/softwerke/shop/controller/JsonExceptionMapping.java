package ru.softwerke.shop.controller;

import com.fasterxml.jackson.core.JsonParseException;
import ru.softwerke.shop.utils.Utils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.StringWriter;

@Provider
public class JsonExceptionMapping implements ExceptionMapper<JsonParseException> {

    @Override
    public Response toResponse(JsonParseException e) {
        StringWriter w = Utils.exceptionToJson("Bad request", "Wrong json format");
        return Response.status(Response.Status.BAD_REQUEST).entity(w.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}