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
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

//            TypedQuery<Member> query1 = em.createQuery("select m from Member as m", Member.class);
//            TypedQuery<String> query2 = em.createQuery("select m.username from Member as m", String.class);
//            Query query3 = em.createQuery("select m.username from Member as m");

//            List<Member> resultList = query1.getResultList();
//            for (Member m: resultList) {
//                System.out.println("m = " + m);
//            }

//            Member singleResult = query1.getSingleResult();
//            System.out.println("singleResult = " + singleResult);

            Member result = em.createQuery("select m from Member m where m.username =:username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("result = " + result);

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