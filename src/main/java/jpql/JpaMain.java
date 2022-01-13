package jpql;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();// Ctrl Alt v'

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("관리자1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            em.persist(member2);

            em.flush();
            em.clear();
            //String query = "select 'a' || 'b' from Member m";

            //String query = "select substring(m.username,2,3) From Member m";//substring(타켓,시작지점,시작지점부터길이이)

            //String query = "select locate('de','abcdegf') From Member m";
            //검색위치부터 문자를 검색한다. 1부터 시작하고 못찾으면 0을 반환한다.

            //String query = "select size(t.members) From Team t";
            String query = "select group_concat(m.username) From Member m";

            List<String> result = em.createQuery(query, String.class).getResultList();

            for(String s: result){
                System.out.println("s = " + s);
            }

            tx.commit();
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
}