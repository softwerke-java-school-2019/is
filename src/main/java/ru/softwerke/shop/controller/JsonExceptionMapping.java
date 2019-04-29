package ru.softwerke.shop.controller;

import com.fasterxml.jackson.core.JsonParseException;
import org.json.JSONException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonExceptionMapping implements ExceptionMapper<JsonParseException> {

    @Override
    public Response toResponse(JsonParseException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("Wrong json format")
                .type( MediaType.TEXT_PLAIN)
                .build();
    }
}

