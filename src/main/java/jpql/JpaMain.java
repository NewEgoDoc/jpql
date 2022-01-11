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

            em.flush();
            em.clear();

            //List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class).getResultList();

            //Member findMember = result.get(0);
            //findMember.setAge(20);

//            List resultList = em.createQuery("select m.username, m.age from Member m").getResultList();
//
//            Object o = resultList.get(0);
//            Object[] result = (Object[])o;
//            System.out.println("result = " + result[0]);
//            System.out.println("result = " + result[1]);

//            List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m").getResultList();
//
//            Object[] result = resultList.get(0);
//            System.out.println("result = " + result[0]);
//            System.out.println("result = " + result[1]);

            List<Object[]> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m",MemberDTO.class).getResultList();

            MemberDTO memberDTO = resultList.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());

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