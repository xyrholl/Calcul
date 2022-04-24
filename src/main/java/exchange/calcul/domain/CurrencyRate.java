package exchange.calcul.domain;

import exchange.calcul.dto.RemittanceForm;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurrencyRate {
    /**
     * CurrencyApi 호출 후 insert 되는 엔티티
     */
    @Id @GeneratedValue
    @Column(name = "currency_rate_id")
    private Long id;

    private String benchCountry;
    private String transCountry;

    private LocalDateTime apiReqTime;

    private Double rate;

    public static CurrencyRate createCurrencyRate(String benchCountry, String transCountry, LocalDateTime apiReqTime, Double rate){
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.benchCountry = benchCountry;
        currencyRate.transCountry =transCountry;
        currencyRate.apiReqTime = apiReqTime;
        currencyRate.rate = rate;
        return currencyRate;
    }

    public static CurrencyRate createCurrencyRate(RemittanceForm form){
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.benchCountry = form.getBenchCountry();
        currencyRate.transCountry = form.getTransCountry();
        currencyRate.rate = Double.valueOf(form.getRate());
        return currencyRate;
    }

}
