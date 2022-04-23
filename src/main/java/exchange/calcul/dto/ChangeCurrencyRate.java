package exchange.calcul.dto;

import exchange.calcul.domain.CurrencyRate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangeCurrencyRate {

    private String benchCountry;
    private String transCountry;
    private Double currencyRate;

    public ChangeCurrencyRate(CurrencyRate currencyRate){
        this.benchCountry = currencyRate.getBenchCountry();
        this.transCountry = currencyRate.getTransCountry();
        this.currencyRate = currencyRate.getRate();
    }

}
