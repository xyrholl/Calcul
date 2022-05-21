package exchange.calcul.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exchange.calcul.domain.CurrencyRate;

import exchange.calcul.exception.ApiLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Apilayer{

    @Autowired
    private ApiCompo apiCompo;

    @Value("${evn.accessKey}")
    private String APILAYER_KEY;
    private final String APILAYER_LIVE = "apilayer.net";

    public List<CurrencyRate> getCurrencyRates(){
        String currencyRateURL = apiLayerURL();
        String data = apiCompo.requestApiObject(currencyRateURL);
        return apiLayerExtraction(data);
    }

    // apiLayer url
    private String apiLayerURL(){

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String,String>();
        params.add("access_key", APILAYER_KEY);
        params.add("source", "USD");
        params.add("format", "1");

        UriComponents uri =  UriComponentsBuilder.newInstance()
        .scheme("http")
        .host(APILAYER_LIVE)
        .path("/api/live")
        .queryParams(params)
        .build();
        return uri.toUriString();
    }

    // apiLayer data 추출
    private List<CurrencyRate> apiLayerExtraction(String data){

        List<CurrencyRate> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> mapData;
        Map<String, Double> quotesMap;

        try {
            mapData = objectMapper.readValue(data, new TypeReference<Map<String, Object>>(){});
            if(String.valueOf(mapData.get("success")).equals("true")) {
                String benchCountry = String.valueOf(mapData.get("source"));

                quotesMap = objectMapper
                        .convertValue(mapData.get("quotes"), new TypeReference<>(){});

                quotesMap.forEach((key, value) -> list.add(
                        CurrencyRate.createCurrencyRate(
                                benchCountry,
                                key.replace(benchCountry, ""),
                                value
                        ))
                );

            }else{
                throw new ApiLayerException(String.valueOf(mapData.get("error")));
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return list;
    }

}
