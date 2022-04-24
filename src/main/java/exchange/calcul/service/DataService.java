package exchange.calcul.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DataService {

    private final CurrencyRateRepository currencyRateRepository;
    private final RemittanceRepository remittanceRepository;
    private final ApiConnect apiConnect;

    public CurrencyRateForm reqCurrencyRateForm(CurrencyRateForm currencyRateForm){
        return  new CurrencyRateForm(reqCurrencyRate(currencyRateForm));
    }

    public CurrencyRate reqCurrencyRate(CurrencyRateForm currencyRateForm){
        LocalDateTime beforeOneHour = LocalDateTime.now().minusHours(1);
        Optional<CurrencyRate> currencyRate = Optional.ofNullable(currencyRateRepository
                .findTopByBenchCountryAndTransCountryAndApiReqTimeAfterOrderByApiReqTimeDesc(currencyRateForm.getBenchCountry(), currencyRateForm.getTransCountry(), beforeOneHour)
                .orElseGet(() -> apiData(apiConnect.requestCurrencyApi(), currencyRateForm)));
        if(currencyRate.isPresent()){
            return currencyRate.get();
        }else{
            throw new RuntimeException("외부 API 호출에 실패하였습니다.");
        }
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

    public void currencyRateSave(List<CurrencyRate> list){
        currencyRateRepository.saveAllAndFlush(list);
    }

    public RemittanceForm reqRemittance(RemittanceForm form) {
        CurrencyRate currencyRate = reqCurrencyRate(new CurrencyRateForm(form));
        Remittance newRemittance = Remittance.createRemittance(form, currencyRate);
        remittanceRepository.saveAndFlush(newRemittance);
        return new RemittanceForm(newRemittance);
    }

    public List<CurrencyRateForm> CurrencyRateAll() {
        return currencyRateRepository.findAll().stream().map(CurrencyRateForm::new).collect(Collectors.toList());
    }


}
