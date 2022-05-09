package exchange.calcul.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import exchange.calcul.repository.MemberRepository;
import exchange.calcul.repository.TeamRepository;

import java.util.List;

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

        /**
         * 영속성 컨텍스트내에서 jpql이 실행될때 flush()가 발생하여
         *  아래 findAll() 실행시 flush()가 이미 실행되어있는 상태이며,
         *  db에 직접 commit 하는 시기는 트랙잭션이 종료되고 나서이므로
         *  flush()와 commit은 다른개념이다.
         */
        List<Member> members =  memberRepository.findAll();

        assertThat(members).extracting("username", "age")
                .containsExactly(tuple("member1", 10),
                        tuple("member2", 11)
                );
    }

}
