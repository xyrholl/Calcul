package exchange.calcul.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exchange.calcul.domain.CurrencyRate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExchangeRateService {

    private final RestTemplate restTemplate;

    public ExchangeRateService(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder.build();
    }

    @Value("${evn.accessKey}")
    private String key;
    private String req_url = "http://apilayer.net/api/live?currencies=KRW,JPY,PHP,&source=USD&format=1";

    public HashMap requestCurrencyApi(){
        req_url += "&access_key="+ key;
        return restTemplate.getForObject(req_url, HashMap.class);
    }

    public List<CurrencyRate> currencyRatesExtraction(HashMap hashMapData){

        List<CurrencyRate> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> quotesMap;

        if(String.valueOf(hashMapData.get("success")).equals("true")) {
            String benchCountry = String.valueOf(hashMapData.get("source"));

            quotesMap = objectMapper
                    .convertValue(hashMapData.get("quotes"), new TypeReference<>(){});

            quotesMap.forEach((key, value) -> list.add(
                    CurrencyRate.createCurrencyRate(
                            benchCountry,
                            key.replace(benchCountry, ""),
                            (Double) value
                    ))
            );

            return list;
        }else{
            throw new RuntimeException("외부 API 호출에 실패하였습니다.");
        }
    }

}
