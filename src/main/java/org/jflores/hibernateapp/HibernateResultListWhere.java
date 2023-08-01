package org.jflores.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.jflores.hibernateapp.entity.Cliente;
import org.jflores.hibernateapp.util.JpaUtil;

import java.util.List;
import java.util.Scanner;

public class HibernateResultListWhere {

    public static void main(String[] args){

        Scanner s = new Scanner(System.in);
        EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("select c from Cliente c where c.formaPago=?1", Cliente.class);
        System.out.println("ingrese una forma pago");
        String pago = s.next();
        query.setParameter(1,pago);
        List<Cliente> clientes = query.getResultList();
        clientes.forEach(System.out::println);
        em.close();
    }
}
