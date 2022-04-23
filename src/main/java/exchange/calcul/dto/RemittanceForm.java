package exchange.calcul.dto;

import exchange.calcul.domain.Remittance;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RemittanceForm {

    private String benchCountry;
    private String transCountry;
    private Double rate;
    private Double remittancePrice;
    private Double receptionPrice;

    public RemittanceForm(Remittance remittance){
        this.benchCountry = remittance.getCurrencyRate().getBenchCountry();
        this.transCountry = remittance.getCurrencyRate().getTransCountry();
        this.remittancePrice = remittance.getRemittancePrice();
        this.receptionPrice = remittance.getReceptionPrice();
    }


}
