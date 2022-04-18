package exchange.calcul.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import exchange.calcul.domain.Country;
import lombok.RequiredArgsConstructor;

@Repository
public interface CountryRepository extends JpaRepository {

}
