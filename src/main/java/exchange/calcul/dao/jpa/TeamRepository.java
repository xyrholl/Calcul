package exchange.calcul.dao.jpa;

import exchange.calcul.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
