package exchange.calcul.service;

import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import exchange.calcul.exception.ApiCallException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;


@Component
public class ApiCompo {

    private RestTemplate restTemplate;

    public ApiCompo(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    public String requestApiObject(String url){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(headers);
        try{
            ResponseEntity<String> repEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
            return repEntity.getBody();
        }catch (HttpStatusCodeException e){
            throw new ApiCallException(e.getMessage(), e.getCause());
        }

    }
    
}
