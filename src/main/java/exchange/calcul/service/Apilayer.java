package exchange.calcul.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exchange.calcul.domain.CurrencyRate;

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
        HashMap<String, Object> data = apiCompo.requestApiObject(currencyRateURL);
        return apiLayerExtraction(data);
    }

    // apiLayer url
    private String apiLayerURL(){

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String,String>();
        params.add("accesskey", APILAYER_KEY);
        params.add("source", "USD");
        params.add("format", "1");

        UriComponents uri =  UriComponentsBuilder.newInstance()
        .scheme("http").path(APILAYER_LIVE)
        .host(APILAYER_LIVE)
        .path("/api/live")
        .queryParams(params)
        .build();
        return uri.toUriString();
    }

    // apiLayer data 추출
    private List<CurrencyRate> apiLayerExtraction(HashMap<String, Object> hashMapData){

        List<CurrencyRate> list = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Double> quotesMap;

        if(String.valueOf(hashMapData.get("success")).equals("true")) {
            String benchCountry = String.valueOf(hashMapData.get("source"));

            quotesMap = objectMapper
                    .convertValue(hashMapData.get("quotes"), new TypeReference<>(){});

            quotesMap.forEach((key, value) -> list.add(
                    CurrencyRate.createCurrencyRate(
                            benchCountry,
                            key.replace(benchCountry, ""),
                            value
                    ))
            );

            return list;
        }else{
            throw new RuntimeException("apiLayer API 호출에 실패하였습니다.");
        }
    }

}
