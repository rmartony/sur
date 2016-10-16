package uy.gub.dgr.sur.test;

import lombok.extern.slf4j.Slf4j;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import uy.gub.dgr.sur.entity.Documento;
import uy.gub.dgr.sur.entity.Estado;
import uy.gub.dgr.sur.entity.Registro;
import uy.gub.dgr.sur.entity.Tasa;
import uy.gub.dgr.sur.service.DocumentoService;
import uy.gub.dgr.sur.service.EstadoService;
import uy.gub.dgr.sur.service.RegistroService;
import uy.gub.dgr.sur.service.TasaService;

import javax.inject.Inject;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rafa
 * Date: 01/12/13
 * Time: 12:22 AM
 */
@Slf4j
@RunWith(Arquillian.class)
public class SitioTest {
    public static final String SRC = "src/main";
    public static final String WEBAPP_SRC = SRC + "/webapp";

    /**
     * The factory that produces entity manager.
     */
    //private static EntityManagerFactory mEmf;
    /**
     * The entity manager that persists and queries the DB.
     */
    //private static EntityManager em;

    @Inject
    private RegistroService registroService;
    @Inject
    private EstadoService estadoService;
    @Inject
    private TasaService tasaService;
    @Inject
    private DocumentoService documentoService;

    @Deployment
    public static Archive<?> createDeployment() {
        File[] files = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve().withTransitivity().asFile();

        WebArchive webArchive = ShrinkWrap.create(WebArchive.class, "sur.war")
                .addPackages(true, "uy")
                .addAsLibraries(files)
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebInfResource(new File(WEBAPP_SRC + "/WEB-INF/sur-ds.xml"))
                .addAsWebInfResource(new File(WEBAPP_SRC + "/WEB-INF/faces-config.xml"))
                .addAsWebInfResource(new File(WEBAPP_SRC + "/WEB-INF/jboss-web.xml"))
                .addAsResource("uy/gub/dgr/sur/util/messages.properties")
                .setWebXML(new File(WEBAPP_SRC + "/WEB-INF/web.xml"))
                .addAsResource("META-INF/persistence.xml");

        System.out.println(webArchive.toString(true));
        return webArchive;
    }

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

/*
    @Test
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
*/
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
*//*

        //Commit and end the transaction
        trx.commit();
    }
*/

    @Test
    public void testAgregarDocumento() {
        Documento documento = new Documento();

        documento.setAnio(2016);
        documento.setFecha(new Date());
        documento.setAnio(2016);
        documento.setLibro((short) 4);
        documento.setNumero(123);
        documento.setBis((short) 0);
        documento.setAutos("autos doc");
        documento.setFicha("ficha doc");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("codigo", "VV");
        Estado estado = (Estado) estadoService.findSingleResultNamedQuery(Estado.BY_CODIGO, parameters);
        documento.setEstado(estado);

        parameters = new HashMap<>();
        parameters.put("codigo", Tasa.DOC_COMUN);
        Tasa tasa = (Tasa) tasaService.findSingleResultNamedQuery(Tasa.BY_CODIGO, parameters);
        documento.setTasa(tasa);

        parameters = new HashMap<>();
        parameters.put("codigo", "RPI");
        Registro registro = (Registro) registroService.findSingleResultNamedQuery(Registro.BY_CODIGO, parameters);
        documento.setRegistro(registro);

        documentoService.update(documento);

    }
}
