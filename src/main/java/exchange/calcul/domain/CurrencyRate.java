package exchange.calcul.domain;

import exchange.calcul.dto.RemittanceForm;
import exchange.calcul.util.CurrencyUtil;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Objects;

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

    private Double rate;

    @OneToOne(mappedBy = "currencyRate")
    private Remittance remittance;

    public static CurrencyRate createCurrencyRate(String benchCountry, String transCountry, Double rate){
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.benchCountry = benchCountry;
        currencyRate.transCountry =transCountry;
        currencyRate.rate = rate;
        return currencyRate;
    }

    public static CurrencyRate createCurrencyRate(RemittanceForm form){
        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.benchCountry = form.getBenchCountry();
        currencyRate.transCountry = form.getTransCountry();
        currencyRate.rate = CurrencyUtil.StringToNumber(form.getRate());
        return currencyRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyRate that = (CurrencyRate) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
