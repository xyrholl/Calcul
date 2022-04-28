package exchange.calcul.dao.jpa;

import exchange.calcul.domain.Remittance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemittanceRepository extends JpaRepository<Remittance, Long> {
}
