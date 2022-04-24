package exchange.calcul.repository;

import exchange.calcul.domain.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {

    Optional<CurrencyRate> findTopByBenchCountryAndTransCountryAndApiReqTimeAfterOrderByApiReqTimeDesc(String benchCountry, String transCountry, LocalDateTime beforeOneHour);

    CurrencyRate findTopByBenchCountryAndTransCountryOrderByApiReqTimeDesc(String benchCountry, String transCountry);
}
