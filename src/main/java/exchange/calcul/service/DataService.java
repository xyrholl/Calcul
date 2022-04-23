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

    public CurrencyRateForm reqCurrencyRate(CurrencyRateForm currencyRateForm){
        // 데이터 검색 후 없으면 api 요청
        Optional<CurrencyRate> currencyRate = Optional.ofNullable(currencyRateRepository
                .findByBenchCountryAndTransCountry(currencyRateForm.getBenchCountry(), currencyRateForm.getTransCountry())
                .orElseGet(() -> apiData(apiConnect.requestCurrencyApi(), currencyRateForm)));
        return new CurrencyRateForm(currencyRate.get());
    }

    public CurrencyRate apiData(HttpEntity<String> response, CurrencyRateForm currencyRateForm){
        List<CurrencyRate> list = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map =  objectMapper.readValue(response.getBody(), new TypeReference<>() {});
            if(String.valueOf(map.get("success")).equals("true")){
                Map<String, Object> quotesMap = objectMapper.convertValue(map.get("quotes"), new TypeReference<>() {});
                String benchCountry = String.valueOf(map.get("source"));

                quotesMap.forEach((key, value) -> list.add(
                        CurrencyRate.createCurrencyRate(
                                benchCountry,
                                key.replace(benchCountry, ""),
                                LocalDateTime.now(),
                                (Double) value
                        ))
                );

                return CurrencyRate.createCurrencyRate(
                        benchCountry,
                        currencyRateForm.getTransCountry(),
                        LocalDateTime.now(),
                        (Double)quotesMap.get(benchCountry+currencyRateForm.getTransCountry())
                );

            }else{
                throw new RuntimeException("외부 API 호출에 실패하였습니다.");
            }
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }finally {
            currencyRateSave(list);
        }
    }

    @Transactional
    public void currencyRateSave(List<CurrencyRate> list){
        currencyRateRepository.saveAll(list);
    }

    public RemittanceForm reqRemittance(RemittanceForm form) {
        Optional<CurrencyRate> currencyRate = currencyRateRepository.findByBenchCountryAndTransCountry(form.getBenchCountry(), form.getTransCountry());
        Remittance newRemittance = Remittance.createRemittance(form, currencyRate.get());
        remittanceRepository.save(newRemittance);
        return new RemittanceForm(newRemittance);
    }

    public List<CurrencyRateForm> CurrencyRateAll() {
        List<CurrencyRateForm> collect = currencyRateRepository.findAll().stream().map(CurrencyRateForm::new).collect(Collectors.toList());
        return collect;
    }


}
