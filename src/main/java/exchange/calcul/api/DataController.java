package exchange.calcul.api;

import exchange.calcul.dto.ChangeCurrencyRate;
import exchange.calcul.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;

    @PostMapping("/currencyRate")
    public ChangeCurrencyRate currencyRate(@RequestBody ChangeCurrencyRate changeCurrency){
        return dataService.reqCurrencyRate(changeCurrency);
    }

    @GetMapping("/currencyRateAll")
    public ModelMap currencyRateAll(){
        return new ModelMap(dataService.CurrencyRateAll());
    }

    @GetMapping("/dataSave")
    public void dataSave(){
        dataService.rateDataSave();
    }

}
