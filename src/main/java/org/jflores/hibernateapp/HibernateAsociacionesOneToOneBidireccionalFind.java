package org.jflores.hibernateapp;

import jakarta.persistence.EntityManager;
import org.jflores.hibernateapp.entity.Cliente;
import org.jflores.hibernateapp.entity.ClienteDetalle;
import org.jflores.hibernateapp.util.JpaUtil;

public class HibernateAsociacionesOneToOneBidireccionalFind {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, 2L);

            ClienteDetalle detalle = new ClienteDetalle(true, 8000L);
            cliente.addDetalle(detalle);

            em.getTransaction().commit();
            System.out.println(cliente);
        }catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
}
