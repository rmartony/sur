package uy.gub.dgr.sur.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.util.SubnetUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import uy.gub.dgr.sur.entity.Configuracion;
import uy.gub.dgr.sur.entity.Sitio;
import uy.gub.dgr.sur.entity.Zona;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * User: rafa
 * Date: 01/12/13
 * Time: 12:22 AM
 */
@Slf4j
public class SitioTest {
    /**
     * The factory that produces entity manager.
     */
    private static EntityManagerFactory mEmf;
    /**
     * The entity manager that persists and queries the DB.
     */
    private static EntityManager em;

    @BeforeClass
    public static void initTestFixture() throws Exception {
        // Get the entity manager for the tests.
/*
        mEmf = Persistence.createEntityManagerFactory("swnoc");
        em = mEmf.createEntityManager();
*/
    }

    /**
     * Cleans up the session.
     */
    @AfterClass
    public static void closeTestFixture() {
/*
        em.close();
        mEmf.close();
*/
    }

    //@Test
    public void pruebaSitio() {
        Sitio s = new Sitio();
        s.setFecha(new Date());
        s.setSigla("Prueba");
        Zona z = new Zona();
        z.setNombre("Zona 1");
        em.persist(z);
        s.setZona(z);
        em.persist(s);
    }

    @Test
    public void prueba() {
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName("128.251.166.252");
            System.out.println(inetAddress.getHostAddress());
            System.out.println("isLinkLocalAddress: " + inetAddress.isLinkLocalAddress());
            System.out.println("isAnyLocalAddress: " + inetAddress.isAnyLocalAddress());
            System.out.println("isSiteLocalAddress: " + inetAddress.isSiteLocalAddress());
            System.out.println("isLoopbackAddress: " + inetAddress.isLoopbackAddress());
            inetAddress = InetAddress.getByName("100.0.0.1");
            System.out.println(inetAddress);
            System.out.println("isLinkLocalAddress: " + inetAddress.isLinkLocalAddress());
            System.out.println("isAnyLocalAddress: " + inetAddress.isAnyLocalAddress());
            System.out.println("isSiteLocalAddress: " + inetAddress.isSiteLocalAddress());
            System.out.println("isLoopbackAddress: " + inetAddress.isLoopbackAddress());
            inetAddress = InetAddress.getByName("172.30.106.132");
            System.out.println(inetAddress);
            System.out.println("isLinkLocalAddress: " + inetAddress.isLinkLocalAddress());
            System.out.println("isAnyLocalAddress: " + inetAddress.isAnyLocalAddress());
            System.out.println("isSiteLocalAddress: " + inetAddress.isSiteLocalAddress());
            System.out.println("isLoopbackAddress: " + inetAddress.isLoopbackAddress());

            SubnetUtils subnetUtils = new SubnetUtils("128.251.166.252/24");
            SubnetUtils.SubnetInfo info = subnetUtils.getInfo();
            System.out.println("128.251.166.252 está en la red 128.251.166.252/24: " + info.isInRange("128.251.162.152"));

            inetAddress = inetAddress.getByName("128.251.166.252");
            Configuracion configuracion = new Configuracion();
            configuracion.setSubredesLocales("128.251.166.0/24");
            boolean isInRange = configuracion.isLocalIp(inetAddress);
            System.out.println("La IP: " + inetAddress.getHostAddress() + (!isInRange ? " no" : "") + " se encuentra en la red " + configuracion.getSubredesLocales());


        } catch (UnknownHostException e) {


        }
    }

    //@Test
    public void pruebaUsuario() {
        log.info("+++++++ Persistence test ++++++++++++++");
        //Get a new transaction
        EntityTransaction trx = em.getTransaction();

        //Start the transaction
        trx.begin();

        Zona z1 = new Zona();
        z1.setNombre("zona 1");
        em.persist(z1);
        Zona z2 = new Zona();
        z2.setNombre("zona 2");
        em.persist(z2);
/*
        Usuario u1 = new Usuario();
        u1.setNombre("Pepito");
        u1.setEmail("p@a.com");
        u1.setNombreUsuario("pepe");
        u1.setClave("pepe");
        u1.setZonas(new HashSet<Zona>(Arrays.asList(z1, z2)));
        em.persist(u1);

        Usuario u2 = new Usuario();
        u2.setNombre("Juan");
        u2.setEmail("j@a.com");
        u1.setNombreUsuario("juan");
        u1.setClave("juan");
        u2.setZonas(new HashSet<Zona>(Arrays.asList(z1)));
        em.persist(u1);
*/
        //Commit and end the transaction
        trx.commit();
    }
}
