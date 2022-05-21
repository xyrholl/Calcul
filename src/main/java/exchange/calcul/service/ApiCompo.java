package exchange.calcul.service;

import java.util.HashMap;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ApiCompo {

    private RestTemplate restTemplate;

    public ApiCompo(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public HashMap requestApiObject(String url){
        return restTemplate.getForObject(url, HashMap.class);
    }
    
}
