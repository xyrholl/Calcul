package exchange.calcul.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Country {

    @Id @GeneratedValue
    @Column(name = "country_id")
    private Long id;
    private String name;
    private String code;

    @OneToMany(mappedBy = "remittanceCountry", fetch = FetchType.LAZY)
    private List<Remittance> remittanceCountry = new ArrayList<>();

    @OneToMany(mappedBy = "receptionCountry", fetch = FetchType.LAZY)
    private List<Remittance> receptionCountry = new ArrayList<>();

    
}
