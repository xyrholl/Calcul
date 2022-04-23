package exchange.calcul.repository;

import exchange.calcul.domain.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    public Optional<CurrencyRate> findTopByBenchCountryAndTransCountry(String benchCountry, String transCountry);
}
