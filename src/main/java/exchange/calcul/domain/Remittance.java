package exchange.calcul.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import exchange.calcul.dto.RemittanceForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Remittance {
    /**
     * 송금 영수 엔티티
     * 숭금 submit 후에 insert 되는 엔티티
     */
    @Id @GeneratedValue
    @Column(name = "remittance_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_rate_id")
    private CurrencyRate currencyRate;

    private Double remittancePrice;
    private Double receptionPrice;

    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private LocalDateTime requestTime;

    public static Remittance createRemittance(RemittanceForm form, CurrencyRate currencyRate){
        Remittance remittance = new Remittance();
        remittance.setRemittancePrice(form.getRemittancePrice());
        remittance.currencyRate = currencyRate;
        remittance.setReceptionPrice();
        return remittance;
    }

     //== 비즈니스 로직==//
     public void setReceptionPrice(){
        this.receptionPrice = this.currencyRate.getRate()*this.remittancePrice;
     }

     //== 엔티티 예외처리 ==//
     public void setRemittancePrice(Double remittancePrice){
         if(remittancePrice <= 0){
             throw new IllegalArgumentException("need more remittancePrice");
         }
         this.remittancePrice =  remittancePrice;
     }




    
}
