package exchange.calcul.api;

import exchange.calcul.dto.CurrencyRateForm;
import exchange.calcul.dto.RemittanceForm;
import exchange.calcul.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping("/currencyRate")
    public CurrencyRateForm currencyRate(@RequestBody CurrencyRateForm changeCurrency){
        return dataService.reqCurrencyRateForm(changeCurrency);
    }

    @PostMapping("/remittance")
    public RemittanceForm remittance(@Validated @RequestBody RemittanceForm form){
        return dataService.reqRemittance(form);
    }

}
