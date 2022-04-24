package exchange.calcul.dto;

import exchange.calcul.domain.CurrencyRate;
import lombok.AllArgsConstructor;
import lombok.Data;

import static exchange.calcul.util.CurrencyUtil.roundTwo;

@Data
@AllArgsConstructor
public class CurrencyRateForm {

    private String benchCountry;
    private String transCountry;
    private String rate;

    public CurrencyRateForm(CurrencyRate currencyRate){
        this.benchCountry = currencyRate.getBenchCountry();
        this.transCountry = currencyRate.getTransCountry();
        this.rate = roundTwo(currencyRate.getRate());
    }

    public CurrencyRateForm(RemittanceForm form){
        this.benchCountry = form.getBenchCountry();
        this.transCountry = form.getTransCountry();
        this.rate = form.getRate();
    }


}
