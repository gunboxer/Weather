package com.weather.web.controller;

import java.util.concurrent.Callable;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author ryagudin
 */
public class CallableThread implements Callable<String> {

    private String filter;
    
    private static final Logger logger = LoggerFactory.getLogger(CallableThread.class);

    public CallableThread(String filter) {
        this.filter = filter;
    }

    @Override
    public String call() throws InterruptedException, Exception {

        // Путь полностью или appid можно вывести в отдельный файл настроек
        ClientRequest request = new ClientRequest("http://api.openweathermap.org/data/2.5/weather?appid=550827a5f5d846ef5f18551d0252d2d9&q=" + filter);
        request.accept("application/json");
        ClientResponse<String> response = request.get(String.class);
        
        if (response.getStatus() != 200) {
            logger.error("CallableThread Failed : HTTP error code : " + response.getStatus());
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        logger.info("CallableThread Success!");
        return response.getEntity().toString();
    }
}
