package exchange.calcul.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import exchange.calcul.domain.Remittance;


@Repository
public interface RemittanceRepository extends JpaRepository<Remittance, Integer> {

}
