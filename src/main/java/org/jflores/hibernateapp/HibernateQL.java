package org.jflores.hibernateapp;

import jakarta.persistence.EntityManager;
import org.jflores.hibernateapp.dominio.ClienteDto;
import org.jflores.hibernateapp.entity.Cliente;
import org.jflores.hibernateapp.util.JpaUtil;

import java.util.List;

public class HibernateQL {

    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        System.out.println("======== consultar todos =========");
        List<Cliente> clientes = em.createQuery("select c from Cliente c", Cliente.class).getResultList();

        clientes.forEach(System.out::println);

        System.out.println("======== consultar por id =========");
        Cliente cliente = em.createQuery("select c from Cliente c where c.id=:id", Cliente.class)
                .setParameter("id", 2L)
                .getSingleResult();
        System.out.println(cliente);

        System.out.println("======== consultar solo el nombre por el id =========");
        String nombreCliente = em.createQuery("select c.nombre from Cliente c where c.id=:id", String.class)
                .setParameter("id", 2L)
                .getSingleResult();
        System.out.println(nombreCliente);

        System.out.println("======== consultar por campos personalizados =========");
        Object[] objetoCliente = em.createQuery("select c.id, c.nombre, c.apellido from Cliente c where c.id=:id",
                        Object[].class)
                .setParameter("id", 1L)
                .getSingleResult();
        Long id = (Long) objetoCliente[0];
        String nombre = (String) objetoCliente[1];
        String apellido = (String) objetoCliente[2];
        System.out.println("id=" + id + ", nombre=" + nombre + ", apellido=" + apellido);

        System.out.println("======== consultar por campos personalizados lista =========");
        List<Object[]> registros = em.createQuery("select c.id, c.nombre, c.apellido from Cliente c",
                Object[].class).getResultList();

        registros.forEach(reg -> {
            Long idCliente = (Long) reg[0];
            String nombreCli = (String) reg[1];
            String apellidoCli = (String) reg[2];
            System.out.println("id=" + idCliente + ", nombre=" + nombreCli + ", apellido=" + apellidoCli);
        });

        System.out.println("======== consultar por cliente(Cliente) y forma de pago(String) =========");
        registros = em.createQuery("select c, c.formaPago from Cliente c", Object[].class)
                .getResultList();

        registros.forEach(reg -> {
            Cliente c = (Cliente) reg[0];
            String pago = (String) reg[1];
            System.out.println("forma de pago=" + pago + "," + c);
        });

        System.out.println("======== consulta que puebla y devuelve objeto de una clase personalizada =========");
        clientes = em.createQuery("select new Cliente(c.nombre, c.apellido) from Cliente c", Cliente.class)
                        .getResultList();
        clientes.forEach(System.out::println);

        System.out.println("======== consulta que puebla y devuelve objeto DTO de una clase personalizada =========");
        List<ClienteDto> clientesDto = em.createQuery("select new org.jflores.hibernateapp.dominio.ClienteDto(c" +
                                ".nombre, c.apellido) from Cliente c",
                        ClienteDto.class)
                .getResultList();
        clientesDto.forEach(System.out::println);

        em.close();
    }
}
