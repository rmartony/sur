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
import org.picketlink.idm.query.Condition;
import org.picketlink.idm.query.IdentityQuery;
import org.picketlink.idm.query.IdentityQueryBuilder;
import uy.gub.dgr.sur.service.InitService;
import uy.gub.dgr.sur.service.UsuarioService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

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
    private transient Logger log;
    @Inject
    private PartitionManager partitionManager;
    @Inject
    private transient InitService initService;
    @Inject
    private transient UsuarioService usuarioService;

    @PostConstruct
    public void create() {
        initDateFormat();
        initDB();
    }

    private boolean initialized() {
        // queries for admin user
        IdentityManager identityManager = this.partitionManager.createIdentityManager();

        // here we get the query builder
        IdentityQueryBuilder builder = identityManager.getQueryBuilder();

        // create a condition
        Condition condition = builder.equal(User.LOGIN_NAME, "admin");

        // create a query for a specific identity type using the previously created condition
        IdentityQuery query = builder.createIdentityQuery(User.class).where(condition);

        // execute the query
        List result = query.getResultList();

        return CollectionUtils.isNotEmpty(result);
    }

    private void initDB() {
        final boolean initialized = initialized();
        if (!initialized) {
            initUsersRoles();
            initService.initDB();
        }

/*
        List<AccountTypeEntity> listaUsuarios = usuarioService.findUserByRole("verificacion");
        for (AccountTypeEntity listaUsuario : listaUsuarios) {
            System.out.println(listaUsuario.getLoginName());
        }
*/
    }

    private void initDateFormat() {
        datePattern = msg.getString("datePattern");
        dateTimePattern = msg.getString("dateTimePattern");
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

        // Create user maestro
        User maestro = new User("maestro");
        maestro.setEmail("maestro@acme.com");
        maestro.setFirstName("Jane");
        maestro.setLastName("Doe");
        identityManager.add(maestro);
        identityManager.updateCredential(maestro, new Password("maestro"));

        // Create user cierre
        User cierre = new User("cierre");
        cierre.setEmail("cierre@acme.com");
        cierre.setFirstName("Jane");
        cierre.setLastName("Doe");
        identityManager.add(cierre);
        identityManager.updateCredential(cierre, new Password("cierre"));

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
            Role clienteRol = new Role(UsuarioService.ROLE_CALIFICACION);
            identityManager.add(clienteRol);

            // Create role "ventanilla"
            Role ventanillaRol = new Role(UsuarioService.ROLE_VENTANILLA);
            identityManager.add(ventanillaRol);

            // Create role "cierre"
            Role cierreRol = new Role(UsuarioService.ROLE_CIERRE);
            identityManager.add(cierreRol);

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

            // Create application role "caducar"
            Role caducarRol = new Role(UsuarioService.ROLE_CADUCAR);
            identityManager.add(caducarRol);

            // Grants
            // Grant the "admin" application role to admin user
            grantRole(relationshipManager, admin, adminRol);

            // Grant the "consulta" application role to consulta user
            grantRole(relationshipManager, consulta, consultaRol);

            // Grant the "maestro" application role to maestro user
            grantRole(relationshipManager, maestro, maestroRol);

            // Grant the "cierre" application role to cierre user
            grantRole(relationshipManager, cierre, cierreRol);

            // Grant the "ventanilla" application role to ventanilla user
            grantRole(relationshipManager, ventanilla, ventanillaRol);

            // Grant the "verificacion" application role to verificacion user
            grantRole(relationshipManager, verificacion, verificacionRol);

            // Grant the "historico" application role to historico user
            grantRole(relationshipManager, historico, historicoRol);

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

    private String getRandomString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(50, random).toString(10);
    }

}
