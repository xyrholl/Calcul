package exchange.calcul.domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString(of = {"id", "username", "age"})
/**
 * JPA는 proxy를 통해해 객체를 생성하기 때문에 private 생성자로는 사용이 불가능함.
 * protected 생성자 사용을 권장.
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username){
        this.username = username;
    }

    public Member(String username, int age, Team team){
        this.username = username;
        this.age = age;
        if(team != null){
            changeTeam(team);
        }
    }

    /**
     * 연관관계 메서드
     * Member가 Team을 변경할때 변경된 팀에도 Member를 추가해주어야함.
     */
    public void changeTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }




}
