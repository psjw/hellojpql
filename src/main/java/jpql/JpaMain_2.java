package jpql;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain_2 {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //code
        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            //영속성 컨텍스트 관리 됨
            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
            Member findMember = result.get(0);
            findMember.setAge(20);


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
