package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

public class JpaMain_19 {
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
            member1.setAge(0);
            em.persist(member1);


            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.changeTeam(teamA);
            member2.setAge(0);
            em.persist(member2);


            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.changeTeam(teamB);
            member3.setAge(0);
            em.persist(member3);




//            em.flush();
//            em.clear();
            //flush 자동 호출
            int resultCount = em.createQuery("update Member m set m.age=20")
                    .executeUpdate();
            System.out.println("resultCount = " + resultCount);
            em.clear();
            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember.getAge() = " + findMember.getAge());


            System.out.println("member1.getAge() = " + member1.getAge());
            System.out.println("member2.getAge() = " + member2.getAge());
            System.out.println("member3.getAge() = " + member3.getAge());
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
