package exchange.calcul.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@DynamicInsert
@ToString
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
        currencyRate.setBenchCountry(benchCountry);
        currencyRate.setTransCountry(transCountry);
        currencyRate.setApiReqTime(apiReqTime);
        currencyRate.setRate(rate);
        return currencyRate;
    }

}
