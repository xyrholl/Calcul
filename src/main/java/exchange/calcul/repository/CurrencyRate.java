package exchange.calcul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRate extends JpaRepository<CurrencyRate, Long> {
}
