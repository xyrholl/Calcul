package exchange.calcul.domain;

import exchange.calcul.dao.jpa.MemberRepository;
import exchange.calcul.dao.jpa.TeamRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @Test
    void DataJPA_동작_테스트(){

        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        teamRepository.save(teamA);
        teamRepository.save(teamB);


        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 11, teamB);
        memberRepository.save(member1);
        memberRepository.save(member2);


        List<Member> members =  memberRepository.findAll();

        assertThat(members).extracting("username", "age")
                .containsExactly(tuple("member1", 10),
                        tuple("member2", 11)
                );
    }

}
