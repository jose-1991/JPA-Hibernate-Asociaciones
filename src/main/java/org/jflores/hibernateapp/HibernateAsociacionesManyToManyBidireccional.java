package org.jflores.hibernateapp;

import jakarta.persistence.EntityManager;
import org.jflores.hibernateapp.entity.Alumno;
import org.jflores.hibernateapp.entity.Curso;
import org.jflores.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesManyToManyBidireccional {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();

        try {
            em.getTransaction().begin();
            Alumno alumno1 = new Alumno("Oscar", "Mayer");
            Alumno alumno2 = new Alumno("Boris", "Almanza");

            Curso curso1 = new Curso("curso java", "Andres");
            Curso curso2 = new Curso("curso hibernate y jpa", "Andres");

            alumno1.addCurso(curso1);
            alumno1.addCurso(curso2);

            alumno2.addCurso(curso1);

            em.persist(alumno1);
            em.persist(alumno2);
            em.getTransaction().commit();

            System.out.println(alumno1);
            System.out.println(alumno2);

            em.getTransaction().begin();
            Curso c2 = new Curso("curso java", "Andres");//Curso c2 = em.find(Curso.class, 3L);
            c2.setId(3L);
            alumno1.removeCurso(c2);
            em.getTransaction().commit();
            System.out.println(alumno1);

        }catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
}
