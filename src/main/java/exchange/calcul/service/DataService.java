package exchange.calcul.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exchange.calcul.domain.CurrencyRate;
import exchange.calcul.domain.Remittance;
import exchange.calcul.dto.CurrencyRateForm;
import exchange.calcul.dto.RemittanceForm;
import exchange.calcul.repository.CurrencyRateRepository;
import exchange.calcul.repository.RemittanceRepository;
import exchange.calcul.util.ApiConnect;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

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

    public CurrencyRateForm reqCurrencyRate(CurrencyRateForm changeCurrency){
        // 데이터 검색 후 없으면 api 요청
        Optional<CurrencyRate> currencyRate = currencyRateRepository.findByBenchCountryAndTransCountry(changeCurrency.getBenchCountry(), changeCurrency.getTransCountry());               ;
//                        .orElseThrow(()->
//                        new EntityNotFoundException()
//                ));
        return new CurrencyRateForm(currencyRate.get());
    }

    public void rateDataSave(){
        HttpEntity<String> str = apiConnect.requestCurrencyApi();
        ObjectMapper objectMapper = new ObjectMapper();

        try {

            JsonNode apiData = objectMapper.readTree(str.getBody());
            String benchCountry = apiData.get("source").asText();
            JsonNode quotes = apiData.get("quotes");
            LocalDateTime time = LocalDateTime.now();

            Map<String, Object> map = objectMapper.convertValue(quotes, new TypeReference<>() {});
            List<exchange.calcul.domain.CurrencyRate> list = new ArrayList<>();
            map.forEach((key, value) -> list.add(
                    exchange.calcul.domain.CurrencyRate.createCurrencyRate(
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

    public exchange.calcul.domain.CurrencyRate currencyRateSave(CurrencyRateForm changeCurrency) {
        rateDataSave();
        return currencyRateRepository.findByBenchCountryAndTransCountry(changeCurrency.getBenchCountry(), changeCurrency.getTransCountry()).get();
    }


    public List<CurrencyRateForm> CurrencyRateAll() {
        List<CurrencyRateForm> collect = currencyRateRepository.findAll().stream().map(CurrencyRateForm::new).collect(Collectors.toList());
        return collect;
    }

    public RemittanceForm reqRemittance(RemittanceForm form) {
        Optional<CurrencyRate> currencyRate = currencyRateRepository.findByBenchCountryAndTransCountry(form.getBenchCountry(), form.getTransCountry());
        Remittance newRemittance = Remittance.createRemittance(form, currencyRate.get());
        remittanceRepository.save(newRemittance);
        return new RemittanceForm(newRemittance);
    }
}
