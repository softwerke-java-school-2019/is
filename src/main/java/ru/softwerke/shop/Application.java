package ru.softwerke.shop;

import com.fasterxml.jackson.databind.util.JSONPObject;
import ru.softwerke.shop.controller.ClientController;
import ru.softwerke.shop.model.Client;

import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.softwerke.shop.service.ClientDataService;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;


public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        System.setProperty("org.eclipse.jetty.LEVEL", "INFO");
        System.setProperty("org.glassfish.jersey.LEVEL", "INFO");
        System.setProperty("ru.softwerke.LEVEL", "INFO");

        try {
            new Application().run();
        } catch (Throwable t) {
            logger.error(t.getMessage());
        }
    }

    private void run() throws URISyntaxException, MalformedURLException {

        Server server = new Server(8080);

        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        ctx.setContextPath("/");

        ServletHolder dynamicServletHolder = ctx.addServlet(ServletContainer.class, "/api/*");
        dynamicServletHolder.setInitOrder(0);
        dynamicServletHolder.setInitParameter("javax.ws.rs.Application", ShopApplication.class.getCanonicalName());



        server.setHandler(ctx);


        try {
            server.start();
            server.join();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            server.destroy();
        }
    }
}
