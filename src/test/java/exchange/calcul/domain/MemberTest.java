package exchange.calcul.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void Member엔티티_테스트(){
        //given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, null);
        Member member2 = new Member("member2", 11, null);
        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();

        //when
        List<Member> members = em.createQuery("select m from Member m ", Member.class).getResultList();

        //then
        assertThat(members).extracting("username", "age")
                .containsExactly(tuple("member1", 10),
                        tuple("member2", 11)
                );
    }
}
