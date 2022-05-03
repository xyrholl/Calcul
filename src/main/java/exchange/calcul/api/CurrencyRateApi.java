package exchange.calcul.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exchange.calcul.dto.CurrencyRateForm;
import exchange.calcul.service.RemittanceService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CurrencyRateApi {

    private final RemittanceService remittanceService;

    @GetMapping("/rate/{transCountry}")
    public CurrencyRateForm getRate(@PathVariable("transCountry") String transCountry){
        return remittanceService.getRate(transCountry);
    }
    
}
