package exchange.calcul.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exchange.calcul.domain.CurrencyRate;
import exchange.calcul.dto.CurrencyRateForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ApiConnect {

    @Value("${evn.accesskey}")
    private String key;
    private String req_url = "http://apilayer.net/api/live?currencies=KRW,JPY,PHP,&source=USD&format=1";

    public HttpEntity<String> requestCurrencyApi(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> requestMessage = new HttpEntity<>(headers);
        req_url += "&access_key="+ key;
        HttpEntity<String> response = restTemplate.postForEntity(req_url, requestMessage, String.class);
        return response;
    }

}
