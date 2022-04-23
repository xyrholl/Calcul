package exchange.calcul.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrencyRateForm {

    private String benchCountry;
    private String transCountry;
    private Double rate;

    public CurrencyRateForm(exchange.calcul.domain.CurrencyRate currencyRate){
        this.benchCountry = currencyRate.getBenchCountry();
        this.transCountry = currencyRate.getTransCountry();
        this.rate = currencyRate.getRate();
    }

}
