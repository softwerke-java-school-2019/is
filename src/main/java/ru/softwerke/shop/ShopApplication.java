package ru.softwerke.shop;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import ru.softwerke.shop.model.Client;
import ru.softwerke.shop.service.BillDataService;
import ru.softwerke.shop.service.ClientDataService;
import ru.softwerke.shop.service.DataService;
import ru.softwerke.shop.service.DeviceDataService;

import javax.ws.rs.ApplicationPath;
import java.util.LinkedHashMap;

@ApplicationPath("/")
public class ShopApplication extends ResourceConfig {

    public ShopApplication() {
        packages("ru.softwerke.shop;com.fasterxml.jackson.jaxrs");

        ClientDataService clientData = new ClientDataService();
        DeviceDataService deviceData = new DeviceDataService();
        BillDataService billData = new BillDataService(deviceData, clientData);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(clientService(clientData)).to(ClientDataService.class);
            }
        });

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(deviceService(deviceData)).to(DeviceDataService.class);
            }
        });

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(billService(billData)).to(BillDataService.class);
            }
        });

        setProperties(new LinkedHashMap<String, Object>() {{
            put(org.glassfish.jersey.server.ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);
        }});
    }

    private ClientDataService clientService(ClientDataService clientData){
        return clientData;
    }

    private DeviceDataService deviceService(DeviceDataService deviceData){
        return deviceData;
    }

    private BillDataService billService(BillDataService billData) {
        return billData;
    }

}