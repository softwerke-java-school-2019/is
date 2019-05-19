package ru.softwerke.shop.controller;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import org.json.JSONException;
import ru.softwerke.shop.utils.ServiceUtils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.StringWriter;

@Provider
public class JsonExceptionMapping implements ExceptionMapper<JsonParseException> {

    @Override
    public Response toResponse(JsonParseException e) {
        StringWriter w = new StringWriter();
        ServiceUtils.exceptionToJson(w, "Bad request", "Wrong json format");
        return Response.status(Response.Status.BAD_REQUEST).entity(w.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}