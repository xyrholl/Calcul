package exchange.calcul.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exchange.calcul.domain.CurrencyRate;
import exchange.calcul.dto.ChangeCurrencyRate;
import exchange.calcul.repository.CurrencyRateRepository;
import exchange.calcul.repository.RemittanceRepository;
import exchange.calcul.util.ApiConnect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataService {

    private final CurrencyRateRepository currencyRateRepository;
    private final RemittanceRepository remittanceRepository;
    private final ApiConnect apiConnect;

    public ChangeCurrencyRate reqCurrencyRate(ChangeCurrencyRate changeCurrency){
        // 데이터 검색 후 없으면 api 요청
        Optional<CurrencyRate> currencyRate = currencyRateRepository.findTopByBenchCountryAndTransCountry(changeCurrency.getBenchCountry(), changeCurrency.getTransCountry());
        return new ChangeCurrencyRate(
                currencyRate.orElse(
                currencyRateSave(changeCurrency)
        ));
    }

    public void rateDataSave(){
        HttpEntity<String> str = apiConnect.requestCurrencyApi();
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            JsonNode apiData = objectMapper.readTree(str.getBody());
            String benchCountry = apiData.get("source").asText();
            JsonNode quotes = apiData.get("quotes");
            LocalDateTime time = LocalDateTime.now();

            Map<String, Object> map = objectMapper.convertValue(quotes, new TypeReference<>() {
            });
            List<CurrencyRate> list = new ArrayList<>();
            map.forEach((key, value) -> list.add(
                    CurrencyRate.createCurrencyRate(
                            benchCountry,
                            key.replace(benchCountry, ""),
                            time,
                            (Double) value
                    ))
            );
            currencyRateRepository.saveAll(list);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public CurrencyRate currencyRateSave(ChangeCurrencyRate changeCurrency) {
        rateDataSave();
        return currencyRateRepository.findTopByBenchCountryAndTransCountry(changeCurrency.getBenchCountry(), changeCurrency.getTransCountry()).get();
    }


    public List<ChangeCurrencyRate> CurrencyRateAll() {
        List<ChangeCurrencyRate> collect = currencyRateRepository.findAll().stream().map(ChangeCurrencyRate::new).collect(Collectors.toList());
        return collect;
    }
}
