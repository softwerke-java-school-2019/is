package ru.softwerke.shop.controller;

import org.json.JSONException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RequestException extends Exception implements ExceptionMapper<RequestException> {
    private static final long serialVersionUID = 1L;

    public RequestException() {
        super();
    }

    public RequestException(String message){
        super(message);
    }

    public RequestException(String msg, Exception ex) {
        super(msg, ex);
    }

    @Override
    public Response toResponse(RequestException e) {
        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
    }
}
