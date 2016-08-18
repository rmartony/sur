package uy.gub.dgr.sur.idm;

/**
 * User: rmartony
 * Date: 16/12/13
 * Time: 07:10 PM
 */

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.model.basic.User;
import uy.gub.dgr.sur.entity.*;
import uy.gub.dgr.sur.service.*;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;

import static org.picketlink.idm.model.basic.BasicModel.grantRole;

/**
 * This startup bean creates a number of default users, groups and roles when the application is started.
 *
 * @author Shane Bryzak
 */

@Singleton
@Startup
public class IDMInitializer {
    public static String datePattern;
    public static String dateTimePattern;

    @Inject
    ResourceBundle msg;
    @Inject
    private PartitionManager partitionManager;
    @Inject
    private transient ZonaService zonaService;
    @Inject
    private transient SedeService sedeService;
    @Inject
    private transient EstructuraService estructuraService;
    @Inject
    private transient SitioService sitioService;
    @Inject
    private transient RegistroService registroService;
    @Inject
    private transient EscribanoService escribanoService;
    @Inject
    private transient IntervinienteService intervinienteService;
    @Inject
    private transient TipoAntenaService tipoAntenaService;
    @Inject
    private transient TipoFeederService tipoFeederService;
    @Inject
    private transient TorreroService torreroService;
    @Inject
    private transient Nodo3GService nodo3GService;
    @Inject
    private transient NodoLteService nodoLteService;
    @Inject
    private transient EstadoService estadoService;
    @Inject
    private transient UsuarioZonaService usuarioZonaService;
    @Inject
    private transient ConfiguracionService configuracionService;
    @Inject
    private transient UsuarioService usuarioService;

    @PostConstruct
    public void create() {
        initDateFormat();
        initDB();
    }

    private boolean initialized() {
        final List zonaList = zonaService.findWithNamedQuery(Zona.ALL, 1);
        return CollectionUtils.isNotEmpty(zonaList);
    }

    private void initDB() {
        final boolean initialized = initialized();
        if (!initialized) {
            initZonas();
            initUsersRoles();
            initSedes();
            initEstructura();
            initRegistros();
            initTorrero();
            initIp();
            initInterviniente();
            initTipoAntena();
            initTipoFeeder();
            initEstado();
            initConfiguracion();

            initDatosPrueba();
        }

/*
        List<AccountTypeEntity> listaUsuarios = usuarioService.findUserByRole("verificacion");
        for (AccountTypeEntity listaUsuario : listaUsuarios) {
            System.out.println(listaUsuario.getLoginName());
        }
*/
    }

    private void initDatosPrueba() {
        Sitio s1 = new Sitio();
        s1.setNombre("Sitio 1");
        s1.setSigla("S01");
        s1.setDireccion("Una dir");
        s1.setLocalidad("Una localidad");
        Map<String, Integer> estructuraParameters = new HashMap<>();
        estructuraParameters.put("id", 1);
        Estructura e1 = (Estructura) (estructuraService.findWithNamedQuery(Estructura.BY_ID, estructuraParameters).get(0));
        s1.setEstructura(e1);
        Map<String, Integer> zonaParameters = new HashMap<>();
        zonaParameters.put("id", 2);
        Zona z1 = (Zona) (zonaService.findWithNamedQuery(Zona.BY_ID, zonaParameters).get(0));
        s1.setZona(z1);
        Map<String, Integer> departamentoParameters = new HashMap<>();
        departamentoParameters.put("id", 5);
        Sede d1 = (Sede) (sedeService.findWithNamedQuery(Sede.BY_ID, departamentoParameters).get(0));
        s1.setSede(d1);

        Sitio s2 = new Sitio();
        s2.setNombre("Sitio 2");
        s2.setSigla("S02");
        s2.setDireccion("Una dir 2");
        s2.setLocalidad("Una localidad 2");
        zonaParameters.clear();
        zonaParameters.put("id", 4);
        Zona z2 = (Zona) (zonaService.findWithNamedQuery(Zona.BY_ID, zonaParameters).get(0));
        s2.setZona(z2);
        departamentoParameters.clear();
        departamentoParameters.put("id", 8);
        Sede d2 = (Sede) (sedeService.findWithNamedQuery(Sede.BY_ID, departamentoParameters).get(0));
        s2.setSede(d2);
        s2.setEstructura(e1);

        sitioService.create(s1);
        sitioService.create(s2);

        Nodo3G nodo3G = new Nodo3G();
        nodo3G.setSitio(s1);
        nodo3G.setFechaAlta(new Date());
        nodo3G.setFechaInstalacion(new Date());
        nodo3G.setFechaAltaEthernet(new Date());
        String ip = "200.40.20.30";
        nodo3G.setDefaultGateway(ip);
        nodo3G.setIpOAM(ip);
        nodo3G.setMaskOAM(ip);
        nodo3G.setGwOAM(ip);
        nodo3G.setVlanOAM(1);
        nodo3G.setIpTelecom(ip);
        nodo3G.setMaskTelecom(ip);
        nodo3G.setGwTelecom(ip);
        nodo3G.setVlanTelecom(5);
        nodo3G.setIpGrandMaster(ip);
        nodo3G.setIpSecondaryMaster(ip);
        nodo3G.setTinco("21");
        nodo3G.setEPLFlujoDABT("a21");
        nodo3G.setAcceso("prueba");
        nodo3G.setNumTorreros("5");
        nodo3G.setAlimentacion(220);
        nodo3G.setSigla("Nodo 3G prueba");
        nodo3G.setNumContadorUTE("a2");
/*
        nodo3G.setRnc(registroService.find(2));
        nodo3G.setIp(escribanoService.find(1));
*/
        Map<String, Integer> params = new HashMap<>();
        params.put("id", 1);
        Interviniente interviniente = (Interviniente) intervinienteService.findSingleResultNamedQuery(Interviniente.ID, params);
        nodo3G.setInterviniente(interviniente);
        nodo3GService.create(nodo3G);

        NodoLte nodoLte = new NodoLte();
        nodoLte.setSitio(s2);
        nodoLte.setFechaAlta(new Date());
        nodoLte.setSigla("NodoLTE prueba");
        nodoLte.setInterviniente(interviniente);
        nodoLte.setIpPTP(ip);
        nodoLte.setMaskPTP(ip);
        nodoLte.setNServicio("s12345");
        nodoLte.setMme("mme");
        nodoLte.setIpOAM(ip);
        nodoLte.setMaskOAM(ip);
        nodoLte.setGwOAM(ip);
        nodoLte.setVlanOAM(6);
        nodoLte.setIpTelecom(ip);
        nodoLte.setMaskTelecom(ip);
        nodoLte.setGwTelecom(ip);
        nodoLte.setVlanTelecom(3);
        nodoLte.setIpGrandMaster(ip);
        nodoLte.setIpSecondaryMaster(ip);
        nodoLte.setTinco("21");
        nodoLte.setEPLFlujoDABT("a21");
        nodoLte.setAcceso("prueba");
        nodoLte.setNumTorreros("2");
        nodoLte.setAlimentacion(220);

        nodoLteService.create(nodoLte);
    }

    private void initDateFormat() {
        datePattern = msg.getString("datePattern");
        dateTimePattern = msg.getString("dateTimePattern");
    }

    private void initConfiguracion() {
        Configuracion configuracion = new Configuracion();
        configuracion.setPrefijoRutaMontajeFotos("\\\\servidor");
        configuracion.setPrefijoUrlExternaFotos("\\\\servidor.alcatel.com.uy");
        configuracion.setPrefijoRutaMontajeFotos("\\\\servidor");
        configuracionService.create(configuracion);
    }

    private void initUsersRoles() {
        IdentityManager identityManager = this.partitionManager.createIdentityManager();

        // Create user admin
        User admin = new User("admin");
        admin.setEmail("admin@acme.com");
        admin.setFirstName("John");
        admin.setLastName("Smith");
        identityManager.add(admin);
        identityManager.updateCredential(admin, new Password("admin"));

        // admin lectura
        User consulta = new User("consulta");
        consulta.setEmail("consulta@acme.com");
        consulta.setFirstName("John");
        consulta.setLastName("Smith");
        identityManager.add(consulta);
        identityManager.updateCredential(consulta, new Password("consulta"));

        // Create user calificacion
        User calificacion = new User("calificacion");
        calificacion.setEmail("calificacion@acme.com");
        calificacion.setFirstName("Mary");
        calificacion.setLastName("Jones");
        identityManager.add(calificacion);
        identityManager.updateCredential(calificacion, new Password("calificacion"));

        // Create user completado
        User completado = new User("maestro");
        completado.setEmail("maestro@acme.com");
        completado.setFirstName("Jane");
        completado.setLastName("Doe");
        identityManager.add(completado);
        identityManager.updateCredential(completado, new Password("maestro"));

        // Create user ventanilla
        User ventanilla = new User("ventanilla");
        ventanilla.setEmail("ventanilla@acme.com");
        ventanilla.setFirstName("Harry");
        ventanilla.setLastName("Jones");
        identityManager.add(ventanilla);
        identityManager.updateCredential(ventanilla, new Password("ventanilla"));

        // Create user verificacion
        User verificacion = new User("verificacion");
        verificacion.setEmail("verificacion@acme.com");
        verificacion.setFirstName("Marry");
        verificacion.setLastName("Jones");
        identityManager.add(verificacion);
        identityManager.updateCredential(verificacion, new Password("verificacion"));

        // Create user historico
        User historico = new User("historico");
        historico.setEmail("historico@acme.com");
        historico.setFirstName("Alex");
        historico.setLastName("Jones");
        identityManager.add(historico);
        identityManager.updateCredential(historico, new Password("historico"));

        RelationshipManager relationshipManager = this.partitionManager.createRelationshipManager();

        if (usuarioService.getRole(UsuarioService.ROLE_CALIFICACION) == null) {
            // Create role "calificacion"
            Role calificacionRol = new Role(UsuarioService.ROLE_CALIFICACION);
            identityManager.add(calificacionRol);

            // Create role "ventanilla"
            Role ventanillaRol = new Role(UsuarioService.ROLE_VENTANILLA);
            identityManager.add(ventanillaRol);

            // Create role "maestro"
            Role maestroRol = new Role(UsuarioService.ROLE_MAESTRO);
            identityManager.add(maestroRol);

            // Create role "verificacion"
            Role verificacionRol = new Role(UsuarioService.ROLE_VERIFICACION);
            identityManager.add(verificacionRol);

            // Create application role "admin"
            Role adminRol = new Role(UsuarioService.ROLE_ADMIN);
            identityManager.add(adminRol);

            // Create application role "historico"
            Role historicoRol = new Role(UsuarioService.ROLE_HISTORICO);
            identityManager.add(historicoRol);

            // Create application role "consulta"
            Role consultaRol = new Role(UsuarioService.ROLE_CONSULTA);
            identityManager.add(consultaRol);


            // Grant the "admin" application role to admin user
            grantRole(relationshipManager, admin, adminRol);

            // Grant the "consulta" application role to consulta user
            grantRole(relationshipManager, consulta, consultaRol);

            // Grant the "completado" application role to completado user
            grantRole(relationshipManager, completado, maestroRol);

            // Grant the "ventanilla" application role to ventanilla user
            grantRole(relationshipManager, ventanilla, ventanillaRol);

            // Grant the "verificacion" application role to verificacion user
            grantRole(relationshipManager, verificacion, verificacionRol);

            // Grant the "historico" application role to historico user
            grantRole(relationshipManager, historico, historicoRol);

            // Al usuario ventanilla le asigna zona 1 y 2
            Zona z1 = zonaService.find(1);
            Zona z2 = zonaService.find(2);
            Zona z3 = zonaService.find(3);

            // ventanilla esta en z1 y z2
            UsuarioZona usuarioZona = new UsuarioZona();
            usuarioZona.setUserId(ventanilla.getLoginName());
            usuarioZona.setZonas(Arrays.asList(z1, z2));
            usuarioZonaService.create(usuarioZona);

            // historico esta en z2 y z3
            UsuarioZona usuarioZona2 = new UsuarioZona();
            usuarioZona2.setUserId(historico.getLoginName());
            usuarioZona2.setZonas(Arrays.asList(z2, z3));
            usuarioZonaService.create(usuarioZona2);

        }


/*
        // Create group "clientes"
        Group clientes = new Group("clientes");
        identityManager.add(clientes);

        // Make admin a member of the "clientes" group
        addToGroup(relationshipManager, calificacion, clientes);

        // Make calificacion a manager of the "clientes" group
        grantGroupRole(relationshipManager, calificacion, clienteRol, clientes);
*/


    }

    public void initZonas() {
        Zona z = new Zona();
        z.setId(1);
        z.setNombre("Litoral");
        zonaService.update(z);
        z = new Zona();
        z.setId(2);
        z.setNombre("Norte");
        zonaService.update(z);
        z = new Zona();
        z.setId(3);
        z.setNombre("Sur");
        zonaService.update(z);
        z = new Zona();
        z.setId(4);
        z.setNombre("Este");
        zonaService.update(z);
        z = new Zona();
        z.setId(5);
        z.setNombre("Montevideo");
        zonaService.update(z);
    }

    public void initSedes() {
        Sede sede = new Sede();
        sede.setId(1);
        sede.setDescripcion("Montevideo");
        sedeService.update(sede);
        sede.setId(2);
        sede.setDescripcion("Paysandú");
        sedeService.update(sede);
        sede.setId(3);
        sede.setDescripcion("Colonia");
        sedeService.update(sede);
        sede.setId(4);
        sede.setDescripcion("Durazno");
        sedeService.update(sede);
        sede.setId(5);
        sede.setDescripcion("Maldonado");
        sedeService.update(sede);
        sede.setId(6);
        sede.setDescripcion("Canelones");
        sedeService.update(sede);
        sede.setId(7);
        sede.setDescripcion("San José");
        sedeService.update(sede);
        sede.setId(8);
        sede.setDescripcion("Rocha");
        sedeService.update(sede);
        sede.setId(9);
        sede.setDescripcion("Soriano");
        sedeService.update(sede);
        sede.setId(10);
        sede.setDescripcion("Salto");
        sedeService.update(sede);
        sede.setId(11);
        sede.setDescripcion("Artigas");
        sedeService.update(sede);
        sede.setId(12);
        sede.setDescripcion("Florida");
        sedeService.update(sede);
        sede.setId(13);
        sede.setDescripcion("Río Negro");
        sedeService.update(sede);
        sede.setId(14);
        sede.setDescripcion("Rivera");
        sedeService.update(sede);
        sede.setId(15);
        sede.setDescripcion("Flores");
        sedeService.update(sede);
        sede.setId(16);
        sede.setDescripcion("Cerro Largo");
        sedeService.update(sede);
        sede.setId(17);
        sede.setDescripcion("Treinta y Tres");
        sedeService.update(sede);
        sede.setId(18);
        sede.setDescripcion("Lavalleja");
        sedeService.update(sede);
        sede.setId(19);
        sede.setDescripcion("Tacuarembó");
        sedeService.update(sede);
    }

    public void initEstructura() {
        Estructura estructura = new Estructura();
        estructura.setId(1);
        estructura.setNombre("Monopolo");
        estructuraService.update(estructura);
        estructura.setId(2);
        estructura.setNombre("Torre");
        estructuraService.update(estructura);
        estructura.setId(3);
        estructura.setNombre("Azotea");
        estructuraService.update(estructura);
        estructura.setId(4);
        estructura.setNombre("Mástil");
        estructuraService.update(estructura);
        estructura.setId(5);
        estructura.setNombre("Tanque de agua");
        estructuraService.update(estructura);
        estructura.setId(6);
        estructura.setNombre("Columna");
        estructuraService.update(estructura);
        estructura.setId(7);
        estructura.setNombre("Indoor");
        estructuraService.update(estructura);

    }

    public void initRegistros() {
        Registro registro = new Registro();
        //registro.setId(2);
        registro.setCodigo("RNCUNI");
        registro.setDescripcion("Registro de Inmuebles");
//        registroService.update(registro);
        registroService.create(registro);
        registro = new Registro();
        //registro.setId(3);
        registro.setCodigo("RNCAGU");
        registro.setDescripcion("Aguada");
//        registroService.update(registro);
        registroService.create(registro);
        registro = new Registro();
        //registro.setId(6);
        registro.setCodigo("RNCCEN");
        registro.setDescripcion("Centro");
//        registroService.update(registro);
        registroService.create(registro);
        registro = new Registro();
        //registro.setId(7);
        registro.setCodigo("RNCCOR");
        registro.setDescripcion("Cordón");
//        registroService.update(registro);
        registroService.create(registro);
        registro = new Registro();
        //registro.setId(8);
        registro.setCodigo("RNCMLD");
        registro.setDescripcion("Maldonado");
//        registroService.update(registro);
        registroService.create(registro);
        registro = new Registro();
        //registro.setId(9);
        registro.setCodigo("RNCPAY");
        registro.setDescripcion("Paysandú");
//        registroService.update(registro);
        registroService.create(registro);
        registro = new Registro();
        //registro.setId(10);
        registro.setCodigo("RNCPAY2");
        registro.setDescripcion("Paysandú2");
//        registroService.update(registro);
        registroService.create(registro);
        registro = new Registro();
        //registro.setId(11);
        registro.setCodigo("RNCAGU2");
        registro.setDescripcion("Aguada2");
//        registroService.update(registro);
        registroService.create(registro);
        registro = new Registro();
        //registro.setId(12);
        registro.setCodigo("RNCMLD2");
        registro.setDescripcion("Maldonado2");
//        registroService.update(registro);
        registroService.create(registro);
        registro = new Registro();
        //registro.setId(13);
        registro.setCodigo("RNCCOR2");
        registro.setDescripcion("Cordon2");
//        registroService.update(registro);
        registroService.create(registro);
    }

    private void initIp() {
        Registro registro = new Registro();
        registro.setId(2);

        Escribano escribano = new Escribano();
        escribano.setCodigo(10);
        escribano.setNombre("Escribano 1");
        escribanoService.update(escribano);


        escribano = new Escribano();
        escribano.setCodigo(23);
        escribano.setNombre("Escribano 2 (deshabilitado)");
        escribano.setInhabilitadoFechaDesde(DateUtils.addDays(new Date(), -80));
        escribano.setInhabilitadoFechaHasta(new Date());
        escribanoService.update(escribano);

        escribano = new Escribano();
        escribano.setCodigo(26);
        escribano.setNombre("Escribano 3");
        escribanoService.update(escribano);

        escribano = new Escribano();
        escribano.setCodigo(30);
        escribano.setNombre("Escribano 4");
        escribanoService.update(escribano);

        registro = new Registro();
        registro.setId(3);
        escribano = new Escribano();

        escribano.setCodigo(35);
        escribano.setNombre("Escribano 5");
        escribanoService.update(escribano);


        escribano = new Escribano();
        escribano.setCodigo(38);
        escribano.setNombre("Escribano 6");
        escribanoService.update(escribano);
    }

    public void initInterviniente() {
        Interviniente interviniente = new Interviniente();
        interviniente.setCodigo("12020");
        interviniente.setDescripcion("Macro Outdoor");
        intervinienteService.create(interviniente);
        interviniente = new Interviniente();
        interviniente.setCodigo("12010");
        interviniente.setDescripcion("Macro Indoor");
        intervinienteService.create(interviniente);
        interviniente = new Interviniente();
        interviniente.setCodigo("D2U");
        interviniente.setDescripcion("D2U");
        intervinienteService.create(interviniente);
        interviniente = new Interviniente();
        interviniente.setCodigo("1120");
        interviniente.setDescripcion("Micro BTS");
        intervinienteService.create(interviniente);
        interviniente = new Interviniente();
        interviniente.setCodigo("6120");
        interviniente.setDescripcion("Distribuida Outdoor");
        intervinienteService.create(interviniente);
        interviniente = new Interviniente();
        interviniente.setCodigo("Md6c");
        interviniente.setDescripcion("Md6c");
        intervinienteService.create(interviniente);
        interviniente = new Interviniente();
        interviniente.setCodigo("S6 -- d2u");
        interviniente.setDescripcion("S6 -- d2u");
        intervinienteService.create(interviniente);
        interviniente = new Interviniente();
        interviniente.setCodigo("MD4");
        interviniente.setDescripcion("MD4");
        intervinienteService.create(interviniente);
        interviniente = new Interviniente();
        interviniente.setCodigo("BBU");
        interviniente.setDescripcion("BBU");
        intervinienteService.create(interviniente);
    }

    public void initTipoAntena() {
        TipoAntena tipoAntena = new TipoAntena();
        tipoAntena.setId(1);
        tipoAntena.setModelo("UMWD-06516-2DH");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(2);
        tipoAntena.setModelo("UMWD-06517-2DH");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(4);
        tipoAntena.setModelo("820 807Q");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(5);
        tipoAntena.setModelo("800 10293");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(6);
        tipoAntena.setModelo("CTSDG-06513-0DM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(7);
        tipoAntena.setModelo("CTSDG-06513-4DM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(9);
        tipoAntena.setModelo("DBXLH-6565C-T0M");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(10);
        tipoAntena.setModelo("CELLMAX-O-25");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(11);
        tipoAntena.setModelo("CELLMAX-D-25");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(12);
        tipoAntena.setModelo("741 794");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(13);
        tipoAntena.setModelo("CELLMAX D-CPUS");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(14);
        tipoAntena.setModelo("DBXLH-6565B-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(15);
        tipoAntena.setModelo("CTSDG-06515-XDM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(16);
        tipoAntena.setModelo("CTSDG-06513-XDM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(17);
        tipoAntena.setModelo("LBX-3316DS-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(19);
        tipoAntena.setModelo("LBX-6515DS-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(20);
        tipoAntena.setModelo("HBX-3319DS-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(21);
        tipoAntena.setModelo("HBX-6515DS-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(22);
        tipoAntena.setModelo("LBX-6513DS-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(23);
        tipoAntena.setModelo("HBX-6516DS-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(24);
        tipoAntena.setModelo("LDX-6515DS-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(25);
        tipoAntena.setModelo("LDX-6513DS-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(26);
        tipoAntena.setModelo("HBX-3316DS-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(28);
        tipoAntena.setModelo("DBLX-6565B-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(29);
        tipoAntena.setModelo("TBXLHB-6565A-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(30);
        tipoAntena.setModelo("2CPX208R-V1");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(31);
        tipoAntena.setModelo("HBXX-3817TB-VTM");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(32);
        tipoAntena.setModelo("DBXLH-6565B-T4M");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(33);
        tipoAntena.setModelo("VVPX303F1");
        tipoAntenaService.update(tipoAntena);
        tipoAntena = new TipoAntena();
        tipoAntena.setId(34);
        tipoAntena.setModelo("APXVERR20X");
        tipoAntenaService.update(tipoAntena);
    }

    public void initTipoFeeder() {
        TipoFeeder tipoFeeder = new TipoFeeder();
        tipoFeeder.setModelo("AVA5-50");
        tipoFeeder.setAtenuacion(0F);
        tipoFeederService.create(tipoFeeder);
        tipoFeeder = new TipoFeeder();
        tipoFeeder.setModelo("LDF5-50");
        tipoFeeder.setAtenuacion(0F);
        tipoFeederService.create(tipoFeeder);
        tipoFeeder = new TipoFeeder();
        tipoFeeder.setModelo("LDF7-50");
        tipoFeeder.setAtenuacion(0F);
        tipoFeederService.create(tipoFeeder);
        tipoFeeder = new TipoFeeder();
        tipoFeeder.setModelo("AVA7-50");
        tipoFeeder.setAtenuacion(0F);
        tipoFeederService.create(tipoFeeder);
    }

    public void initTorrero() {
        Torrero t = new Torrero();
        t.setNombre("Larry");
        torreroService.create(t);
        t = new Torrero();
        t.setNombre("Martinez");
        torreroService.create(t);
        t = new Torrero();
        t.setNombre("Gonzalez");
        torreroService.create(t);
        t = new Torrero();
        t.setNombre("Sebastian de Melo");
        torreroService.create(t);
        t = new Torrero();
        t.setNombre("Leonardo Carvallo");
        torreroService.create(t);
        t = new Torrero();
        t.setNombre("Gonzalo Duarte");
        torreroService.create(t);
        t = new Torrero();
        t.setNombre("Jose Hernandez");
        torreroService.create(t);
        t = new Torrero();
        t.setNombre("Javier Brun");
        torreroService.create(t);
        t = new Torrero();
        t.setNombre("Rodrigo");
        torreroService.create(t);
    }

    public void initEstado() {
        Estado estado = new Estado();
        estado.setNombre("Bien");
        estado.setCodigo("B");
        estadoService.create(estado);
        estado = new Estado();
        estado.setNombre("Mal");
        estado.setCodigo("M");
        estadoService.create(estado);
        estado = new Estado();
        estado.setNombre("Reparado");
        estado.setCodigo("R");
        estadoService.create(estado);
    }

    private String getRandomString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(50, random).toString(10);
    }

}
