package org.jflores.hibernateapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.jflores.hibernateapp.entity.Cliente;
import org.jflores.hibernateapp.util.JpaUtil;

import java.util.Scanner;

public class HibernatePorId {

    public static void main(String[] args){

        //1era forma de buscar por id
        Scanner s = new Scanner(System.in);
        EntityManager em = JpaUtil.getEntityManager();
        Query query = em.createQuery("select c from Cliente c where c.id=?1", Cliente.class);
        System.out.println("ingrese un ID: ");
        Long pago = s.nextLong();
        query.setParameter(1,pago);
        Cliente c = (Cliente) query.getSingleResult();
        System.out.println(c);


        // 2da forma de buscar por id solo funciona con la primaryKey
        //guarda el objeto en la memoria de hibernate
        System.out.println("ingrese un ID: ");
        Long id = s.nextLong();
        Cliente cliente = em.find(Cliente.class, id);
        System.out.println(cliente);


        Cliente cliente2 = em.find(Cliente.class, 2L);
        System.out.println(cliente2);
        em.close();
    }
}
