package exchange.calcul.dao.jpa;

import exchange.calcul.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
