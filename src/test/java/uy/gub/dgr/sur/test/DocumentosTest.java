package uy.gub.dgr.sur.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
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
import uy.gub.dgr.sur.entity.*;
import uy.gub.dgr.sur.service.*;

import javax.inject.Inject;
import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * User: rafa
 * Date: 01/12/13
 * Time: 12:22 AM
 */
@Slf4j
@RunWith(Arquillian.class)
public class DocumentosTest {
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
    private DocumentoService documentoService;
    @Inject
    private RegistroService registroService;
    @Inject
    private EstadoService estadoService;
    @Inject
    private TasaService tasaService;
    @Inject
    private EmisorService emisorService;
    @Inject
    private EscribanoService escribanoService;
    @Inject
    private ActoService actoService;
    @Inject
    private MovimientoService movimientoService;
    @Inject
    private InscripcionService inscripcionService;
    @Inject
    private DepartamentoService departamentoService;
    @Inject
    private LocalidadService localidadService;

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

    //@Test
    public void testAgregarDocumentoSujetoAutomotor() {
        Documento documento = new Documento();

        documento.setAnio(2016);
        documento.setFecha(new Date());
        documento.setFechaEmision(new Date());
        documento.setAnio(2016);
        documento.setLibro((short) 4);
        documento.setNumero(123);
        documento.setBis((short) 0);
        documento.setAutos("autos doc");
        documento.setFicha("ficha doc");
        documento.setFechaResolucion(new Date());
        documento.setObservacion("Una obs");
        documento.setObservacionDgr("Una obs DGR");

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

        parameters = new HashMap<>();
        parameters.put("codigo", "1"); // Sede Montevideo
        Sede sede = (Sede) registroService.findSingleResultNamedQuery(Sede.BY_CODIGO, parameters);
        documento.setSede(sede);

        parameters = new HashMap<>();
        parameters.put("codigo", 35L); // Escribano 1
        Escribano escribano = (Escribano) escribanoService.findSingleResultNamedQuery(Escribano.BY_CODIGO, parameters);
        documento.setEscribano(escribano);

        documento = documentoService.update(documento);

        // Inscripcion
        parameters = new HashMap<>();
        parameters.put("codigo", "act1Sec1RPI"); // Acto "act1Sec1RPI"
        Acto acto = (Acto) actoService.findSingleResultNamedQuery(Acto.BY_CODIGO, parameters);

        parameters = new HashMap<>();
        parameters.put("codigo", "mov1"); // Acto "act1Sec1RPI"
        Movimiento movimiento = (Movimiento) movimientoService.findSingleResultNamedQuery(Movimiento.BY_CODIGO, parameters);

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setDocumento(documento);
        inscripcion.setEstado(estado);
        inscripcion.setOrdinal(1);
        inscripcion.setActo(acto);
        inscripcion.setMovimiento(movimiento);
        inscripcion.setObservacion("Una obs inscripcion");

        // Sujeto automotor
        SujetoAutomotor sujetoAutomotor = new SujetoAutomotor();
        sujetoAutomotor.setSujeto("sujeto1");
        Automotor automotor = new Automotor();
        automotor.setAnio(1995);
        automotor.setPlacaMunicipal("abcd123");
        automotor.setPadron(12345);

        parameters = new HashMap<>();
        parameters.put("codigo", "1"); // Montevideo
        Departamento departamento = (Departamento) departamentoService.findSingleResultNamedQuery(Departamento.BY_CODIGO, parameters);
        automotor.setDepartamento(departamento);
        parameters = new HashMap<>();
        parameters.put("codigo", "loc1"); // Localidad 1
        Localidad localidad = (Localidad) localidadService.findSingleResultNamedQuery(Localidad.BY_CODIGO, parameters);
        automotor.setLocalidad(localidad);

        sujetoAutomotor.setAutomotor(automotor);

        List<Sujeto> sujetoList = new ArrayList<>();
        sujetoList.add(sujetoAutomotor);
        inscripcion.setSujetoList(sujetoList);

        inscripcion = inscripcionService.update(inscripcion);

    }

    //@Test
    public void testEliminarTasa() {
        Tasa tasa = new Tasa();
        tasa.setCodigo("codTasa");
        tasa.setNombre("Prueba tasa");
        tasa.setMonto(new BigDecimal(1000));
        tasa.setFechaVencimiento(DateUtils.addDays(new Date(), 505));
        tasa = tasaService.update(tasa);

        tasaService.delete(tasa.getId());
    }

    @Test
    public void testEliminarEscribano() {
        Escribano escribano = new Escribano();
        escribano.setCodigo(99L);
        escribano.setNombre("Escribano 99");
        escribano = escribanoService.update(escribano);

        escribanoService.delete(escribano.getId());
    }

}
