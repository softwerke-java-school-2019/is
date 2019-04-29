package ru.softwerke.shop;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import ru.softwerke.shop.model.Client;
import ru.softwerke.shop.service.BillDataService;
import ru.softwerke.shop.service.ClientDataService;
import ru.softwerke.shop.service.DataService;
import ru.softwerke.shop.service.DeviceDataService;

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

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(deviceService()).to(DeviceDataService.class);
            }
        });

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(billService()).to(BillDataService.class);
            }
        });
    }

    private ClientDataService clientService(){
        return new ClientDataService();
    }

    private DeviceDataService deviceService(){
        return new DeviceDataService();
    }

    private BillDataService billService() {
        return new BillDataService();
    }

}