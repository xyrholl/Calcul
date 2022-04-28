package exchange.calcul.dto;

import exchange.calcul.domain.CurrencyRate;
import exchange.calcul.domain.Remittance;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static exchange.calcul.dto.util.CurrencyUtil.roundTwo;

@Data
@AllArgsConstructor
public class RemittanceForm {

    @NotEmpty(message = "송금국가가 바르지 않습니다.")
    private String benchCountry;
    @NotEmpty(message = "수취국가가 바르지 않습니다.")
    private String transCountry;
    private String rate;
    @NotNull(message = "송금액이 바르지 않습니다.")
    @Range(min = 10, max = 10000, message = "송금액이 바르지 않습니다.")
    private String remittancePrice;
    private String receptionPrice;

    public RemittanceForm(Remittance remittance){
        CurrencyRate currencyRate = remittance.getCurrencyRate();
        this.benchCountry = currencyRate.getBenchCountry();
        this.transCountry = currencyRate.getTransCountry();
        this.rate = roundTwo(currencyRate.getRate());
        this.remittancePrice = roundTwo(remittance.getRemittancePrice());
        this.receptionPrice = roundTwo(remittance.getReceptionPrice());
    }


}
