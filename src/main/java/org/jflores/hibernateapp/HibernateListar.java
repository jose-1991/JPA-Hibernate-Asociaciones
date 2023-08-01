package org.jflores.hibernateapp;

import jakarta.persistence.EntityManager;
import org.jflores.hibernateapp.entity.Cliente;
import org.jflores.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateListar {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManager();
        List<Cliente> clientes = em.createQuery("select c from Cliente c", Cliente.class).getResultList();

        clientes.forEach(System.out::println);
        em.close();
    }
}
