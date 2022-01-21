package jpql;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collections;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();// Ctrl Alt v'

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);


            Member member1 = new Member();
            member1.setUsername("관리자1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("관리자2");
            member2.setTeam(teamB);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("관리자3");
            member3.setTeam(teamB);
            em.persist(member3);

            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            em.clear();
            System.out.println("resultCount = " + resultCount);

            System.out.println("member1 = " + member1.getAge());
            System.out.println("member1 = " + member2.getAge());
            System.out.println("member1 = " + member3.getAge());

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