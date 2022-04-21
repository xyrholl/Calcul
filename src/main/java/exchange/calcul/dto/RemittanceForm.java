package exchange.calcul.dto;

import lombok.Data;

@Data
public class RemittanceForm {

    private String benchCountry;
    private String transCountry;
    private float currencyRate;
    private Long remittancePrice;

}
