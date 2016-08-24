package uy.gub.dgr.sur.idm;

/**
 * User: rmartony
 * Date: 17/12/13
 * Time: 12:22 PM
 */

import org.apache.deltaspike.security.api.authorization.Secures;
import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.idm.annotations.Maestro;
import uy.gub.dgr.sur.idm.annotations.Verificacion;
import uy.gub.dgr.sur.service.UsuarioService;

import javax.enterprise.context.ApplicationScoped;

import static org.picketlink.idm.model.basic.BasicModel.getRole;
import static org.picketlink.idm.model.basic.BasicModel.hasRole;

/**
 * Defines the authorization logic for the @Employee and @Admin security binding types
 *
 * @author Shane Bryzak
 */
@ApplicationScoped
public class CustomAuthorizer {

    /**
     * This method is used to check if classes and methods annotated with {@link Admin} can perform
     * the operation or not
     *
     * @param identity        The Identity bean, representing the currently authenticated user
     * @param identityManager The IdentityManager provides methods for checking a user's roles
     * @return true if the user can execute the method or class
     * @throws Exception
     */
    @Secures
    @Admin
    public boolean doAdminCheck(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager) throws Exception {
        return hasRole(relationshipManager, identity.getAccount(), getRole(identityManager, "admin"));
    }

    /**
     * This method is used to check
     * if classes and methods annotated with {@link Maestro} can perform
     * the operation or not
     *
     * @param identity        The Identity bean, representing the currently authenticated user
     * @param identityManager The IdentityManager provides methods for checking a user's roles
     * @return true if the user can execute the method or class
     * @throws Exception
     */
    @Secures
    @Maestro
    public boolean doMaestroCheck(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager) throws Exception {
        return hasRole(relationshipManager, identity.getAccount(), getRole(identityManager, UsuarioService.ROLE_MAESTRO)) ||
                hasRole(relationshipManager, identity.getAccount(), getRole(identityManager, UsuarioService.ROLE_ADMIN));
    }

    @Secures
    @Verificacion
    public boolean doVerificacionCheck(Identity identity, IdentityManager identityManager, RelationshipManager relationshipManager) throws Exception {
        return hasRole(relationshipManager, identity.getAccount(), getRole(identityManager, UsuarioService.ROLE_VERIFICACION)) ||
                hasRole(relationshipManager, identity.getAccount(), getRole(identityManager, UsuarioService.ROLE_ADMIN));
    }

}
