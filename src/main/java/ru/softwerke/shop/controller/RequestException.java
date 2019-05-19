package ru.softwerke.shop.controller;

import ru.softwerke.shop.utils.Utils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.StringWriter;

@Provider
public class RequestException extends IOException implements ExceptionMapper<RequestException> {
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
        StringWriter w = Utils.exceptionToJson("Bad Request", e.getMessage());
        return Response.status(Response.Status.BAD_REQUEST).entity(w.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
