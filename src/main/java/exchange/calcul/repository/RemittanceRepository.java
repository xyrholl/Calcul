package exchange.calcul.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import exchange.calcul.domain.Remittance;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RemittanceRepository {

    private final EntityManager em;

    public void save(Remittance remittance){
        em.persist(remittance);
    }


    
}
