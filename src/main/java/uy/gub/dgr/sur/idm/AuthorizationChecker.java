package uy.gub.dgr.sur.idm;

/**
 * User: rmartony
 * Date: 16/12/13
 * Time: 07:13 PM
 */

import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Group;
import org.picketlink.idm.model.basic.Role;
import uy.gub.dgr.sur.service.UsuarioService;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import static org.picketlink.idm.model.basic.BasicModel.*;

/**
 * This is a utility bean that may be used by the view layer to determine whether the
 * current user has specific privileges.
 *
 * @author Shane Bryzak
 */
@SessionScoped
@Named
public class AuthorizationChecker implements Serializable {
    private ConcurrentHashMap<String, Boolean> hasRoleCache = new ConcurrentHashMap<>();

    @Inject
    private Identity identity;

    @Inject
    private IdentityManager identityManager;

    @Inject
    private RelationshipManager relationshipManager;

    public boolean hasApplicationRole(String roleName) {
        Boolean cachedRole = hasRoleCache.get(roleName);
        if (cachedRole == null) {
            if (this.identity == null) return false;
            final Account account = this.identity.getAccount();
            if (account == null) return false;

            Role role = getRole(this.identityManager, roleName);
            final boolean hasRole = hasRole(this.relationshipManager, account, role);
            hasRoleCache.put(roleName, hasRole);
            return hasRole;
        } else {
            return hasRoleCache.get(roleName);
        }
    }

    public boolean isMember(String groupName) {
        Group group = getGroup(this.identityManager, groupName);
        return BasicModel.isMember(this.relationshipManager, this.identity.getAccount(), group);
    }

    public boolean hasGroupRole(String roleName, String groupName) {
        Group group = getGroup(this.identityManager, groupName);
        Role role = getRole(this.identityManager, roleName);
        return BasicModel.hasGroupRole(this.relationshipManager, this.identity.getAccount(), role, group);
    }

    public boolean isAdmin() {
        return hasApplicationRole(UsuarioService.ROLE_ADMIN);
    }

    public boolean isVentanilla() {
        return hasApplicationRole(UsuarioService.ROLE_VENTANILLA);
    }

    public boolean isHistorico() {
        return hasApplicationRole(UsuarioService.ROLE_HISTORICO);
    }

    public boolean isCalificacion() {
        return hasApplicationRole(UsuarioService.ROLE_CALIFICACION);
    }

    public boolean isVerificacion() {
        return hasApplicationRole(UsuarioService.ROLE_VERIFICACION);
    }

    public boolean isMaestro() {
        return hasApplicationRole(UsuarioService.ROLE_MAESTRO);
    }

    public boolean isConsulta() {
        return hasApplicationRole(UsuarioService.ROLE_CONSULTA);
    }

    public boolean isCierre() {
        return hasApplicationRole(UsuarioService.ROLE_CIERRE);
    }

    public boolean isCaducar() {
        return hasApplicationRole(UsuarioService.ROLE_CADUCAR);
    }


}
