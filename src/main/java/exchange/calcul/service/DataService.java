package exchange.calcul.service;

import exchange.calcul.domain.CurrencyRate;
import exchange.calcul.domain.Remittance;
import exchange.calcul.dto.CurrencyRateForm;
import exchange.calcul.dto.RemittanceForm;
import exchange.calcul.repository.CurrencyRateRepository;
import exchange.calcul.repository.RemittanceRepository;
import exchange.calcul.util.ApiConnect;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
                .findTopByBenchCountryAndTransCountryAndApiReqTimeAfterOrderByApiReqTimeDesc(
                        currencyRateForm.getBenchCountry(), currencyRateForm.getTransCountry(), beforeOneHour
                )
                .orElseGet(() -> findOneCurrencyRate(
                        apiConnect.currencyRatesExtraction(apiConnect.requestCurrencyApi()), currencyRateForm)
                )
        );

        if(currencyRate.isPresent()){
            return currencyRate.get();
        }else{
            throw new RuntimeException("외부 API 호출에 실패하였습니다.");
        }
    }

    public CurrencyRate findOneCurrencyRate(List<CurrencyRate> crList, CurrencyRateForm currencyRateForm){
        currencyRateSave(crList);
        return crList.stream()
                .filter(cr -> cr.getBenchCountry().equals(currencyRateForm.getBenchCountry())
                        && cr.getTransCountry().equals(currencyRateForm.getTransCountry())
                ).collect(Collectors.toList()).get(0);
    }

    public void currencyRateSave(List<CurrencyRate> list){
        currencyRateRepository.saveAllAndFlush(list);
    }

    public RemittanceForm reqRemittance(RemittanceForm form) {

        CurrencyRate currencyRate = currencyRateRepository.findTopByBenchCountryAndTransCountryOrderByApiReqTimeDesc(
                form.getBenchCountry(),
                form.getTransCountry()
        );

        Remittance newRemittance = Remittance.createRemittance(form, currencyRate);
        remittanceRepository.save(newRemittance);
        return new RemittanceForm(newRemittance);
    }

}
