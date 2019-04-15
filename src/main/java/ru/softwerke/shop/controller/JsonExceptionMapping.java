package ru.softwerke.shop.controller;

import org.json.JSONException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class JsonExceptionMapping implements ExceptionMapper<JSONException> {

    @Override
    public Response toResponse(JSONException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage())
                .type( MediaType.TEXT_PLAIN)
                .build();
    }
}
