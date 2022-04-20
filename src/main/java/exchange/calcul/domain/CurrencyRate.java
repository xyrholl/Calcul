package exchange.calcul.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter @Setter
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

    private float currencyRate;

    @OneToOne(mappedBy = "currencyRate")
    private Remittance remittance;
}
