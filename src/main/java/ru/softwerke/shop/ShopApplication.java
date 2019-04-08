package ru.softwerke.shop;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import ru.softwerke.shop.service.ClientDataService;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class ShopApplication extends ResourceConfig {
    public ShopApplication() {
        packages("ru.softwerke.shop;com.fasterxml.jackson.jaxrs");

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(clientService()).to(ClientDataService.class);
            }
        });
    }

    private ClientDataService clientService(){
        return new ClientDataService();
    }

}