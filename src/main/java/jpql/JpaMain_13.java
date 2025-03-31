package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain_13 {
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

//            String query = "select concat('a','b') from Member m" ;
//            String query = "select 'a'||'b' from Member m" ;
//            String query = "select substring(m.username, 2, 3) from Member m" ;
//            String query = "select locate('de','abcdefg') from Member m" ;
//            String query = "select size(t.members) from Team t" ;
            //@OrderColumn 컬렉션 위치값 조회 -> 사용 X하는게 좋음
//            String query = "select function('group_concat', m.username) from Member m" ;
            String query = "select group_concat(m.username) from Member m" ;
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
