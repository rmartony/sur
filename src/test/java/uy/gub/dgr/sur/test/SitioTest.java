package uy.gub.dgr.sur.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import uy.gub.dgr.sur.entity.Registro;
import uy.gub.dgr.sur.entity.Zona;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

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
        mEmf = Persistence.createEntityManagerFactory("sur");
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

    @Test
    public void pruebaRegistro() {
        Registro s = new Registro();
        s.setCodigo("cod1");
        s.setDescripcion("Prueba");
        em.persist(s);
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
