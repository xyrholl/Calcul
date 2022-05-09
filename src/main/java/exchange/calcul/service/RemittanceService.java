package exchange.calcul.service;

import exchange.calcul.domain.CurrencyRate;
import exchange.calcul.domain.Remittance;
import exchange.calcul.dto.CurrencyRateForm;
import exchange.calcul.dto.RemittanceForm;
import exchange.calcul.repository.RemittanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RemittanceService {

    private final RemittanceRepository remittanceRepository;
    private final ExchangeRateService ExchangeRateService;

    public CurrencyRateForm reqCurrencyRateForm(CurrencyRateForm currencyRateForm){
        return  new CurrencyRateForm(reqCurrencyRate(currencyRateForm));
    }

    public CurrencyRate reqCurrencyRate(CurrencyRateForm currencyRateForm){
        List<CurrencyRate> CurrencyRates = ExchangeRateService.currencyRatesExtraction(ExchangeRateService.requestCurrencyApi());
        return findOneCurrencyRate(CurrencyRates, currencyRateForm);
    }

    public CurrencyRate findOneCurrencyRate(List<CurrencyRate> crList, CurrencyRateForm currencyRateForm){
        List<CurrencyRate> find = crList.stream()
                .filter(cr -> cr.getBenchCountry().equals(currencyRateForm.getBenchCountry())
                        && cr.getTransCountry().equals(currencyRateForm.getTransCountry())
                ).collect(Collectors.toList());
        if(find.size() > 0){
            return find.get(0);
        }else{
            throw new NoSuchElementException("환율 대상국가 정보가 유효하지 않습니다.");
        }
    }

    @Transactional
    public RemittanceForm reqRemittance(RemittanceForm form) {
        CurrencyRate currencyRate = CurrencyRate.createCurrencyRate(form);
        Remittance newRemittance = Remittance.createRemittance(form, currencyRate);
        remittanceRepository.save(newRemittance);
        return new RemittanceForm(newRemittance);
    }

    public CurrencyRateForm getRate(String transCountry) {
        CurrencyRateForm currencyRateForm = new CurrencyRateForm("USD", transCountry);
        return new CurrencyRateForm(reqCurrencyRate(currencyRateForm));
    }

}
