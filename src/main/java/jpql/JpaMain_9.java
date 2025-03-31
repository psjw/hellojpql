package jpql;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {
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

            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
//            TypedQuery<String> query1 = em.createQuery("select m.username from Member m", String.class); //타입 이명확 TypeQuery사용
//            Query query3 = em.createQuery("select m.username, m.age from Member m"); //타입이 명확하지 않음 Query 사용
            Member result = query.getSingleResult();
            //Spring Data JPA -> Optionaexl 반환
            System.out.println("result = " + result);

            List<Member> resultList = query.getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
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
