package exchange.calcul.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Remittance {

    @Id @GeneratedValue
    @Column(name = "remittance_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "remittance_country")
    private Country remittanceCountry;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name = "reception_country")
    private Country receptionCountry;

    private LocalDateTime requestTime;
    private Float exchangeRate;
    private Long remittancePrice;


    /**
     * 잘모르겠다
     */
    //== 연관관계 메서드 ==//
    // public void setRemittanceCountry(Country remittanceCountry){
    //     this.remittanceCountry = remittanceCountry;
    //     remittanceCountry.getRemittanceCountry();
    // }

    // public void setReceptionCountry(Country receptionCountry){
    //     this.receptionCountry = receptionCountry;
    //     receptionCountry.getReceptionCountry();
    // }

    /**
     * 생성제한
     */
    public static Remittance createRemittance(Country remittanceCountry, Country receptionCountry){
        Remittance remittance = new Remittance();
        remittance.setRemittanceCountry(remittanceCountry);
        remittance.setReceptionCountry(receptionCountry);
        return remittance;
    }
    
}
