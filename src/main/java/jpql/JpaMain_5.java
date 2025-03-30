package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain_5 {
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
            List resultList = em.createQuery("select m.username, m.age from Member m").getResultList();

            Object o = resultList.get(0);
            Object[] result = (Object[]) o; //Object[]로 캐스팅
            System.out.println("username = " + result[0]);
            System.out.println("age = " + result[1]);


            List<Object[]> resultObjList = em.createQuery("select m.username, m.age from Member m").getResultList();

            Object[] resultObj = resultObjList.get(0);
            System.out.println("username = " + resultObj[0]);
            System.out.println("age = " + resultObj[1]);

            //영속성 컨텍스트 관리 됨
            List<MemberDTO> resultList1 = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class).getResultList();
            MemberDTO memberDTO = resultList1.get(0);
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            

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
