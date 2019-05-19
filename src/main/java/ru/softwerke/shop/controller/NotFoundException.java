package ru.softwerke.shop.controller;

import ru.softwerke.shop.utils.Utils;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.StringWriter;

@Provider
public class NotFoundException extends IOException implements ExceptionMapper<NotFoundException> {
    private static final long serialVersionUID = 2L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message){
        super(message);
    }

    public NotFoundException(String msg, Exception ex) {
        super(msg, ex);
    }

    @Override
    public Response toResponse(NotFoundException e) {
        StringWriter w = Utils.exceptionToJson("Not Found", e.getMessage());
        return Response.status(Response.Status.NOT_FOUND).entity(w.toString()).type(MediaType.APPLICATION_JSON).build();
    }
}
