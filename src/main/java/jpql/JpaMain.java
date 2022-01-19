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
            member1.setTeam(teamB);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("관리자3");
            member1.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            String query = "select distinct  t from Team t join fetch t.members";
            List<Team> result = em.createQuery(query, Team.class).getResultList();

            for(Team team: result){
                System.out.println("member = " + team.getName()+ ", " + team.getMembers().size());
                for(Member member: team.getMembers()){
                    System.out.println("-> member = " + member);
                }
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