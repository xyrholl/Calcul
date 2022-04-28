package exchange.calcul.api;

import exchange.calcul.dto.CurrencyRateForm;
import exchange.calcul.dto.RemittanceForm;
import exchange.calcul.service.RemittanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RemittanceApi {

    private final RemittanceService remittanceService;

    @PostMapping("/currencyRate")
    public CurrencyRateForm currencyRate(@RequestBody CurrencyRateForm changeCurrency){
        return remittanceService.reqCurrencyRateForm(changeCurrency);
    }

    @PostMapping("/remittance")
    public RemittanceForm remittance(@Validated @RequestBody RemittanceForm form){
        return remittanceService.reqRemittance(form);
    }

}
