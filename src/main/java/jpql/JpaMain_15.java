package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

public class JpaMain_15 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //code
        try {
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);


            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.changeTeam(teamA);
            em.persist(member1);


            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.changeTeam(teamA);
            em.persist(member2);


            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.changeTeam(teamB);
            em.persist(member3);




            em.flush();
            em.clear();


//            String query = "select m from Member m" ;
//            String query = "select m from Member m join fetch m.team" ;
//            String query = "select t from Team t join fetch t.members" ;
//            String query = "select t from Team t join fetch t.members" ;
//            String query = "select distinct t from Team t join fetch t.members" ; //컬렉션에서의 중복도 제거
//            String query = "select t from Team t join t.members" ;
//            String query = "select t from Team t fetch join t.members as m" ; //패치 조인 대상  alias 지양
            String query = "select t from Team t" ; //패치 조인 대상  alias 지양

//            List<Member> result = em.createQuery(query, Member.class)
//                    .getResultList();

//            List<Team> result = em.createQuery(query, Team.class)
//                    .getResultList();
            List<Team> result = em.createQuery(query, Team.class) //BatchSize로 조회(in)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            System.out.println("result.size() = " + result.size());
//            for (Member m : result) {
//                System.out.println("member = " + m.getUsername() + " "+
//                        "team = " + m.getTeam().getName());
//                //회원1, 팀A(SQL)
//                //회원2, 팀A(1차캐시)
//                //회원3, 팀B(SQL)
//
//                //회원 100명 -> N + 1
//            }

            for (Team team : result) {
                System.out.println("team = " + team.getName() + " " +
                        "|member = " + team.getMembers().size());
                for (Member m : team.getMembers()) {
                    System.out.println("-> member = " + m);
                }
                //팀A, 2
                //팀B, 1
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

}
