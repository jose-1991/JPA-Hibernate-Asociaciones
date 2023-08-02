package org.jflores.hibernateapp;

import jakarta.persistence.EntityManager;
import org.jflores.hibernateapp.dominio.ClienteDto;
import org.jflores.hibernateapp.entity.Cliente;
import org.jflores.hibernateapp.util.JpaUtil;

import java.util.Arrays;
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

        System.out.println("======== consulta con nombres de clientes =========");
        List<String> nombres = em.createQuery("select c.nombre from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("======== consulta con nombres unicos de clientes =========");
        nombres = em.createQuery("select distinct(c.nombre) from Cliente c", String.class).getResultList();
        nombres.forEach(System.out::println);

        System.out.println("======== consulta con forma de pago unicos =========");
        List<String> pagos =
                em.createQuery("select distinct(c.formaPago) from Cliente c", String.class).getResultList();
        pagos.forEach(System.out::println);

        System.out.println("======== consulta con numero de forma de pago unicos =========");
        Long totalFormasPago =
                em.createQuery("select count(distinct(c.formaPago)) from Cliente c", Long.class).getSingleResult();
        System.out.println(totalFormasPago);

        System.out.println("======== consulta con nombre y apellido concatenados =========");
//        nombres = em.createQuery("select concat(c.nombre, ' ', c.apellido) as nombreCompleto from Cliente c",
//                        String.class)
//                        .getResultList();

        nombres = em.createQuery("select c.nombre || ' ' || c.apellido as nombreCompleto from Cliente c",
                        String.class)
                .getResultList();
        nombres.forEach(System.out::println);


        System.out.println("======== consulta con nombre y apellido concatenados en mayuscula =========");
        nombres = em.createQuery("select upper(concat(c.nombre, ' ', c.apellido)) as nombreCompleto from Cliente c",
                        String.class)
                .getResultList();

        nombres.forEach(System.out::println);

        System.out.println("======== consulta para buscar por nombre =========");
        String param = "ON";
        clientes = em.createQuery("select c from Cliente c where c.nombre like :parametro",
                        Cliente.class)
                .setParameter("parametro", "%" + param + "%")
                .getResultList();

        clientes.forEach(System.out::println);

        System.out.println("======== consulta por rangos(BETWEEN) =========");
//        clientes = em.createQuery("select c from Cliente c where c.id between 2 and 5",
//                        Cliente.class)
//                .getResultList();

        clientes = em.createQuery("select c from Cliente c where c.nombre between 'J' and 'P'",
                        Cliente.class)
                .getResultList();

        clientes.forEach(System.out::println);

        System.out.println("======== consulta por ordenamiento(ORDER BY) =========");
        clientes = em.createQuery("select c from Cliente c order by c.nombre asc , c.apellido asc",
                        Cliente.class)
                .getResultList();

        clientes.forEach(System.out::println);

        System.out.println("======== consulta por el total de registros(COUNT) =========");
        Long total = em.createQuery("select count(c) as total from Cliente c", Long.class).getSingleResult();
        System.out.println(total);

        System.out.println("======== consulta por el valor minimo del id (MIN) =========");
        Long minId = em.createQuery("select min(c.id) as total from Cliente c", Long.class).getSingleResult();
        System.out.println(minId);

        System.out.println("======== consulta por el valor maximo del id (MAX) =========");
        Long maxId = em.createQuery("select max(c.id) as total from Cliente c", Long.class).getSingleResult();
        System.out.println(maxId);

        System.out.println("======== consulta con nombre y su largo (LENGTH) =========");
        registros = em.createQuery("select c.nombre, length(c.nombre) from Cliente c", Object[].class).getResultList();
        registros.forEach(reg -> {
            String nom = (String) reg[0];
            Integer largo = (Integer) reg[1];
            System.out.println("nombre=" + nom + ", largo=" + largo);
        });

        System.out.println("======== consulta por el nombre mas corto (MIN, LENGTH) =========");
        Integer minLargoNombre = em.createQuery("select min(length(c.nombre)) from Cliente c", Integer.class)
                .getSingleResult();
        System.out.println(minLargoNombre);

        System.out.println("======== consulta por el nombre mas largo (MAX, LENGTH) =========");
        Integer maxLargoNombre = em.createQuery("select max(length(c.nombre)) from Cliente c", Integer.class)
                .getSingleResult();
        System.out.println(maxLargoNombre);

        System.out.println("======== consultas resumen funciones agregaciones count, min, max, avg, sum=========");
        Object[] estadisticas = em.createQuery("select min(c.id), max(c.id), sum(c.id), count(c.id), avg(length(c" +
                ".nombre)) from Cliente c", Object[].class).getSingleResult();
        Long min = (Long) estadisticas[0];
        Long max = (Long) estadisticas[1];
        Long sum = (Long) estadisticas[2];
        Long count = (Long) estadisticas[3];
        Double avg = (Double) estadisticas[4];

        System.out.println("min=" + min + ", max=" + max + ", sum=" + sum + ",count=" + count + ", avg=" + avg);


        System.out.println("======== consulta con el nombre mas corto y su largo (SUBCONSULTAS)=========");
        registros = em.createQuery("select c.nombre, length(c.nombre) from Cliente c where " +
                "length(c.nombre) = (select min(length(c.nombre)) from Cliente c)", Object[].class)
                        .getResultList();
        registros.forEach(reg -> {
            String nom = (String) reg[0];
            Integer largo = (Integer) reg[1];
            System.out.println("nombre=" + nom + ", largo=" + largo);
        });

        System.out.println("======== consulta para obtener el ultimo registro (SUBCONSULTAS)=========");
        Cliente ultimoCliente= em.createQuery("select c from Cliente c where c.id = (select max(c.id) from Cliente c)", Cliente.class)
                        .getSingleResult();
        System.out.println(ultimoCliente);

        System.out.println("======== consulta where in (SUBCONSULTAS, IN)=========");
        clientes = em.createQuery("select c from Cliente c where c.id in :ids", Cliente.class)
                .setParameter("ids", Arrays.asList(1L,2L,9L, 12L))
                .getResultList();
        clientes.forEach(System.out::println);
        em.close();

    }
}
