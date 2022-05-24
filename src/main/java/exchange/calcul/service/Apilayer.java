package exchange.calcul.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exchange.calcul.domain.CurrencyRate;

import exchange.calcul.exception.ApiLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
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
        String data = apiCompo.requestGet(requestCurrencyRatesAPI());
        return currencyRateExtraction(data);
    }

    private String requestCurrencyRatesAPI(){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String,String>();
        params.add("access_key", APILAYER_KEY);
        params.add("source", "USD");
        params.add("format", "1");
        return apiCompo.buildURI("http", APILAYER_LIVE, "/api/live", params);
    }

    private List<CurrencyRate> currencyRateExtraction(String data){
        List<CurrencyRate> list = new ArrayList<>();

        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        System.out.println(data);
        Map<String, Object> mapData =  jsonParser.parseMap(data);
        System.out.println(mapData.toString());
        if(mapData.get("success").equals(true)) {
            String benchCountry = mapData.get("source").toString();
            Map<String, Object> quotesMap = (Map<String, Object>) mapData.get("quotes");
            quotesMap.forEach((key, value) ->
                    list.add(
                        CurrencyRate.createCurrencyRate(
                                benchCountry,
                                key.replace(benchCountry, ""),
                                Double.valueOf(String.valueOf(value))
                        )
                    )
            );
        }else{
            throw new ApiLayerException(String.valueOf(mapData.get("error")));
        }
        return list;
    }

}
