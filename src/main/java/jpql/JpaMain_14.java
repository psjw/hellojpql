package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain_14 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //code
        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자1");
            member.setAge(10);
            member.changeTeam(team);
            member.setType(MemberType.ADMIN);
            em.persist(member);


            Member member1 = new Member();
            member1.setUsername("관리자2");
            member1.setAge(10);
            member1.changeTeam(team);
            member1.setType(MemberType.ADMIN);

            em.persist(member1);


            em.flush();
            em.clear();

//            String query = "select m.username from Member m" ;
//            String query = "select m.team from Member m" ; //묵시적 inner join (단일 값 연관 경로)
//            String query = "select t.members from Team t" ; //컬렉션 값 연관경로(탐색 X)
            String query = "select m.username from Team t join t.members m" ; // 명시적 Join을 사용하자

            List<Object> result = em.createQuery(query)
                    .getResultList();

            for (Object s : result) {
                System.out.println("s = " + s);
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
