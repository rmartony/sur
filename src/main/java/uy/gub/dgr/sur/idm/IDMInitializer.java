package uy.gub.dgr.sur.idm;

/**
 * User: rmartony
 * Date: 16/12/13
 * Time: 07:10 PM
 */

import org.apache.commons.collections.CollectionUtils;
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
    private transient RncService rncService;
    @Inject
    private transient IpService ipService;
    @Inject
    private transient TipoBTSService tipoBTSService;
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
    private transient EstadoPreventivoService estadoPreventivoService;
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
            initRnc();
            initTorrero();
            initIp();
            initTipoBTS();
            initTipoAntena();
            initTipoFeeder();
            initEstadoPreventivo();
            initConfiguracion();

            initDatosPrueba();
        }

/*
        List<AccountTypeEntity> listaUsuarios = usuarioService.findUserByRole("tecnico");
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
        nodo3G.setRnc(rncService.find(2));
        nodo3G.setIp(ipService.find(1));
*/
        Map<String, Integer> params = new HashMap<>();
        params.put("id", 1);
        TipoBTS tipoBTS = (TipoBTS) tipoBTSService.findSingleResultNamedQuery(TipoBTS.ID, params);
        nodo3G.setTipoBTS(tipoBTS);
        nodo3GService.create(nodo3G);

        NodoLte nodoLte = new NodoLte();
        nodoLte.setSitio(s2);
        nodoLte.setFechaAlta(new Date());
        nodoLte.setSigla("NodoLTE prueba");
        nodoLte.setTipoBTS(tipoBTS);
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
        identityManager.updateCredential(admin, new Password("demo"));

        // admin lectura
        User adminLectura = new User("adminlectura");
        adminLectura.setEmail("adminlectura@acme.com");
        adminLectura.setFirstName("John");
        adminLectura.setLastName("Smith");
        identityManager.add(adminLectura);
        identityManager.updateCredential(adminLectura, new Password("demo"));

        // Create user cliente
        User cliente = new User("cliente");
        cliente.setEmail("cliente@acme.com");
        cliente.setFirstName("Mary");
        cliente.setLastName("Jones");
        identityManager.add(cliente);
        identityManager.updateCredential(cliente, new Password("demo"));

        // Create user consola
        User consola = new User("consola");
        consola.setEmail("consola1@acme.com");
        consola.setFirstName("Jane");
        consola.setLastName("Doe");
        identityManager.add(consola);
        identityManager.updateCredential(consola, new Password("demo"));

        // Create user cliente
        User tecnico1 = new User("tecnico1");
        tecnico1.setEmail("tecnico1@acme.com");
        tecnico1.setFirstName("Harry");
        tecnico1.setLastName("Jones");
        identityManager.add(tecnico1);
        identityManager.updateCredential(tecnico1, new Password("demo"));

        User tecnico2 = new User("tecnico2");
        tecnico2.setEmail("tecnico2@acme.com");
        tecnico2.setFirstName("Alex");
        tecnico2.setLastName("Jones");
        identityManager.add(tecnico2);
        identityManager.updateCredential(tecnico2, new Password("demo"));

        RelationshipManager relationshipManager = this.partitionManager.createRelationshipManager();

        if (usuarioService.getRole(UsuarioService.ROLE_CLIENTE) == null) {
            // Create role "cliente"
            Role clienteRol = new Role(UsuarioService.ROLE_CLIENTE);
            identityManager.add(clienteRol);

            // Create role "consola"
            Role consolaRol = new Role(UsuarioService.ROLE_CONSOLA);
            identityManager.add(consolaRol);

            // Create role "tecnico"
            Role tecnicoRol = new Role(UsuarioService.ROLE_TECNICO);
            identityManager.add(tecnicoRol);

            // Create application role "admin"
            Role adminRol = new Role(UsuarioService.ROLE_ADMIN);
            identityManager.add(adminRol);

            // Create application role "adminlectura"
            Role adminLecturaRol = new Role(UsuarioService.ROLE_ADMIN_LECTURA);
            identityManager.add(adminLecturaRol);

            // Create application role "lectura"
            Role lecturaRol = new Role(UsuarioService.ROLE_LECTURA);
            identityManager.add(lecturaRol);

            // Grant the "admin" application role to admin
            grantRole(relationshipManager, admin, adminRol);
            // Grant the "adminLecturaRol" application role to adminlecura
            grantRole(relationshipManager, adminLectura, adminLecturaRol);

            // Grant the "consola" application role to consola
            grantRole(relationshipManager, consola, consolaRol);

            // Grant the "tecnicoRol" application role to tecnico1
            grantRole(relationshipManager, tecnico1, tecnicoRol);
            grantRole(relationshipManager, tecnico2, tecnicoRol);

            // Al usuario tecnico1 le asigna zona 1 y 2
            Zona z1 = zonaService.find(1);
            Zona z2 = zonaService.find(2);
            Zona z3 = zonaService.find(3);

            // tecnico1 esta en z1 y z2
            UsuarioZona usuarioZona = new UsuarioZona();
            usuarioZona.setUserId(tecnico1.getLoginName());
            usuarioZona.setZonas(Arrays.asList(z1, z2));
            usuarioZonaService.create(usuarioZona);

            // tecnico2 esta en z2 y z3
            UsuarioZona usuarioZona2 = new UsuarioZona();
            usuarioZona2.setUserId(tecnico2.getLoginName());
            usuarioZona2.setZonas(Arrays.asList(z2, z3));
            usuarioZonaService.create(usuarioZona2);

        }


/*
        // Create group "clientes"
        Group clientes = new Group("clientes");
        identityManager.add(clientes);

        // Make admin a member of the "clientes" group
        addToGroup(relationshipManager, cliente, clientes);

        // Make cliente a manager of the "clientes" group
        grantGroupRole(relationshipManager, cliente, clienteRol, clientes);
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
        sede.setNombre("Montevideo");
        sedeService.update(sede);
        sede.setId(2);
        sede.setNombre("Paysandú");
        sedeService.update(sede);
        sede.setId(3);
        sede.setNombre("Colonia");
        sedeService.update(sede);
        sede.setId(4);
        sede.setNombre("Durazno");
        sedeService.update(sede);
        sede.setId(5);
        sede.setNombre("Maldonado");
        sedeService.update(sede);
        sede.setId(6);
        sede.setNombre("Canelones");
        sedeService.update(sede);
        sede.setId(7);
        sede.setNombre("San José");
        sedeService.update(sede);
        sede.setId(8);
        sede.setNombre("Rocha");
        sedeService.update(sede);
        sede.setId(9);
        sede.setNombre("Soriano");
        sedeService.update(sede);
        sede.setId(10);
        sede.setNombre("Salto");
        sedeService.update(sede);
        sede.setId(11);
        sede.setNombre("Artigas");
        sedeService.update(sede);
        sede.setId(12);
        sede.setNombre("Florida");
        sedeService.update(sede);
        sede.setId(13);
        sede.setNombre("Río Negro");
        sedeService.update(sede);
        sede.setId(14);
        sede.setNombre("Rivera");
        sedeService.update(sede);
        sede.setId(15);
        sede.setNombre("Flores");
        sedeService.update(sede);
        sede.setId(16);
        sede.setNombre("Cerro Largo");
        sedeService.update(sede);
        sede.setId(17);
        sede.setNombre("Treinta y Tres");
        sedeService.update(sede);
        sede.setId(18);
        sede.setNombre("Lavalleja");
        sedeService.update(sede);
        sede.setId(19);
        sede.setNombre("Tacuarembó");
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

    public void initRnc() {
        Rnc rnc = new Rnc();
        //rnc.setId(2);
        rnc.setSigla("RNCUNI");
        rnc.setNombre("Unión");
//        rncService.update(rnc);
        rncService.create(rnc);
        rnc = new Rnc();
        //rnc.setId(3);
        rnc.setSigla("RNCAGU");
        rnc.setNombre("Aguada");
//        rncService.update(rnc);
        rncService.create(rnc);
        rnc = new Rnc();
        //rnc.setId(6);
        rnc.setSigla("RNCCEN");
        rnc.setNombre("Centro");
//        rncService.update(rnc);
        rncService.create(rnc);
        rnc = new Rnc();
        //rnc.setId(7);
        rnc.setSigla("RNCCOR");
        rnc.setNombre("Cordón");
//        rncService.update(rnc);
        rncService.create(rnc);
        rnc = new Rnc();
        //rnc.setId(8);
        rnc.setSigla("RNCMLD");
        rnc.setNombre("Maldonado");
//        rncService.update(rnc);
        rncService.create(rnc);
        rnc = new Rnc();
        //rnc.setId(9);
        rnc.setSigla("RNCPAY");
        rnc.setNombre("Paysandú");
//        rncService.update(rnc);
        rncService.create(rnc);
        rnc = new Rnc();
        //rnc.setId(10);
        rnc.setSigla("RNCPAY2");
        rnc.setNombre("Paysandú2");
//        rncService.update(rnc);
        rncService.create(rnc);
        rnc = new Rnc();
        //rnc.setId(11);
        rnc.setSigla("RNCAGU2");
        rnc.setNombre("Aguada2");
//        rncService.update(rnc);
        rncService.create(rnc);
        rnc = new Rnc();
        //rnc.setId(12);
        rnc.setSigla("RNCMLD2");
        rnc.setNombre("Maldonado2");
//        rncService.update(rnc);
        rncService.create(rnc);
        rnc = new Rnc();
        //rnc.setId(13);
        rnc.setSigla("RNCCOR2");
        rnc.setNombre("Cordon2");
//        rncService.update(rnc);
        rncService.create(rnc);
    }

    private void initIp() {
        Rnc rnc = new Rnc();
        rnc.setId(2);

        Ip ip = new Ip();
        ip.setIp("10.67.104.2");
        ip.setRnc(rnc);
        ip.setOcupada(false);
        ipService.update(ip);


        ip = new Ip();
        ip.setIp("10.67.104.3");
        ip.setRnc(rnc);
        ip.setOcupada(false);
        ipService.update(ip);

        ip = new Ip();
        ip.setIp("10.67.104.4");
        ip.setRnc(rnc);
        ip.setOcupada(false);
        ipService.update(ip);

        ip = new Ip();
        ip.setIp("10.67.104.5");
        ip.setRnc(rnc);
        ip.setOcupada(false);
        ipService.update(ip);

        rnc = new Rnc();
        rnc.setId(3);
        ip = new Ip();

        ip.setIp("10.67.125.2");
        ip.setRnc(rnc);
        ip.setOcupada(false);
        ipService.update(ip);


        ip = new Ip();
        ip.setIp("10.67.125.3");
        ip.setRnc(rnc);
        ip.setOcupada(false);
        ipService.update(ip);
    }

    public void initTipoBTS() {
        TipoBTS tipoBTS = new TipoBTS();
        tipoBTS.setModelo("12020");
        tipoBTS.setNombre("Macro Outdoor");
        tipoBTSService.create(tipoBTS);
        tipoBTS = new TipoBTS();
        tipoBTS.setModelo("12010");
        tipoBTS.setNombre("Macro Indoor");
        tipoBTSService.create(tipoBTS);
        tipoBTS = new TipoBTS();
        tipoBTS.setModelo("D2U");
        tipoBTS.setNombre("D2U");
        tipoBTSService.create(tipoBTS);
        tipoBTS = new TipoBTS();
        tipoBTS.setModelo("1120");
        tipoBTS.setNombre("Micro BTS");
        tipoBTSService.create(tipoBTS);
        tipoBTS = new TipoBTS();
        tipoBTS.setModelo("6120");
        tipoBTS.setNombre("Distribuida Outdoor");
        tipoBTSService.create(tipoBTS);
        tipoBTS = new TipoBTS();
        tipoBTS.setModelo("Md6c");
        tipoBTS.setNombre("Md6c");
        tipoBTSService.create(tipoBTS);
        tipoBTS = new TipoBTS();
        tipoBTS.setModelo("S6 -- d2u");
        tipoBTS.setNombre("S6 -- d2u");
        tipoBTSService.create(tipoBTS);
        tipoBTS = new TipoBTS();
        tipoBTS.setModelo("MD4");
        tipoBTS.setNombre("MD4");
        tipoBTSService.create(tipoBTS);
        tipoBTS = new TipoBTS();
        tipoBTS.setModelo("BBU");
        tipoBTS.setNombre("BBU");
        tipoBTSService.create(tipoBTS);
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

    public void initEstadoPreventivo() {
        EstadoPreventivo estadoPreventivo = new EstadoPreventivo();
        estadoPreventivo.setNombre("Bien");
        estadoPreventivo.setSigla("B");
        estadoPreventivoService.create(estadoPreventivo);
        estadoPreventivo = new EstadoPreventivo();
        estadoPreventivo.setNombre("Mal");
        estadoPreventivo.setSigla("M");
        estadoPreventivoService.create(estadoPreventivo);
        estadoPreventivo = new EstadoPreventivo();
        estadoPreventivo.setNombre("Reparado");
        estadoPreventivo.setSigla("R");
        estadoPreventivoService.create(estadoPreventivo);
    }

    private String getRandomString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(50, random).toString(10);
    }

}
