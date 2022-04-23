package exchange.calcul.repository;

import exchange.calcul.domain.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    Optional<CurrencyRate> findByBenchCountryAndTransCountry(String benchCountry, String transCountry);
}
