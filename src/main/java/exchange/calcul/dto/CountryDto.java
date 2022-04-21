package exchange.calcul.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CountryDto {

    private String countryName;
    private String currencyName;
    private String countryCurrencyName;

}
