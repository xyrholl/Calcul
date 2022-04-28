package exchange.calcul.service;

import exchange.calcul.domain.CurrencyRate;
import exchange.calcul.domain.Remittance;
import exchange.calcul.dto.CurrencyRateForm;
import exchange.calcul.dto.RemittanceForm;
import exchange.calcul.dao.jpa.RemittanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DataService {

    private final RemittanceRepository remittanceRepository;
    private final exchangeCompo exchangeCompo;

    public CurrencyRateForm reqCurrencyRateForm(CurrencyRateForm currencyRateForm){
        return  new CurrencyRateForm(reqCurrencyRate(currencyRateForm));
    }

    public CurrencyRate reqCurrencyRate(CurrencyRateForm currencyRateForm){
        List<CurrencyRate> CurrencyRates = exchangeCompo.currencyRatesExtraction(exchangeCompo.requestCurrencyApi());
        return findOneCurrencyRate(CurrencyRates, currencyRateForm);
    }

    public CurrencyRate findOneCurrencyRate(List<CurrencyRate> crList, CurrencyRateForm currencyRateForm){
        return crList.stream()
                .filter(cr -> cr.getBenchCountry().equals(currencyRateForm.getBenchCountry())
                        && cr.getTransCountry().equals(currencyRateForm.getTransCountry())
                ).collect(Collectors.toList()).get(0);
    }

    @Transactional
    public RemittanceForm reqRemittance(RemittanceForm form) {
        CurrencyRate currencyRate = CurrencyRate.createCurrencyRate(form);
        Remittance newRemittance = Remittance.createRemittance(form, currencyRate);
        remittanceRepository.save(newRemittance);
        return new RemittanceForm(newRemittance);
    }

}
