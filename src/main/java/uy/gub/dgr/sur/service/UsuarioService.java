package uy.gub.dgr.sur.service;

import org.apache.commons.collections4.CollectionUtils;
import org.picketlink.annotations.PicketLink;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.jpa.model.sample.simple.AccountTypeEntity;
import org.picketlink.idm.jpa.model.sample.simple.RoleTypeEntity;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Role;
import org.picketlink.idm.model.basic.User;
import org.picketlink.idm.query.IdentityQuery;
import org.picketlink.idm.query.IdentityQueryBuilder;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.picketlink.idm.model.basic.BasicModel.*;

/**
 * User: rmartony
 * Date: 13/02/14
 * Time: 12:34 PM
 */
@Stateless
public class UsuarioService {
    public final static String ALL = "UsuarioService.all";
    public final static String USERS_ROLE = "UsuarioService.usersRole";
    public final static String USERS_BY_ROLE = "UsuarioService.usersByRole";
    public final static String ROLE_ALL = "UsuarioService.roleAll";
    public final static String TOTAL = "UsuarioService.countTotal";

    public final static String ROLE_ADMIN = "admin";
    public final static String ROLE_ADMIN_LECTURA = "adminLectura";
    public final static String ROLE_CONSOLA = "consola";
    public final static String ROLE_CLIENTE = "cliente";
    public final static String ROLE_LECTURA = "lectura";
    public final static String ROLE_TECNICO = "tecnico";

    @Inject
    @PicketLink
    private EntityManager em;

    @Inject
    private PartitionManager partitionManager;

    @Inject
    private IdentityManager identityManager;

    public void create(User user, Password password) {
        identityManager.add(user);
        identityManager.updateCredential(user, password);
    }

    public void update(User user, Password password) {
        identityManager.update(user);
        if (password != null) {
            identityManager.updateCredential(user, password);
        }
    }

    public void deleteItems(User[] selectedItems) {
        if (selectedItems != null && selectedItems.length > 0) {
            for (User selectedItem : selectedItems) {
                identityManager.remove(selectedItem);
            }
        }
    }

    public IdentityQueryBuilder createIdentityQueryBuilder() {
        IdentityQueryBuilder builder = identityManager.getQueryBuilder();
        return builder;
    }

    public List<User> findUser(IdentityQuery<User> query) {
        return query.getResultList();
    }

    public int count(IdentityQuery<User> query) {
        return query.getResultCount();
    }

    public void changePassword(User user, String password) {
        identityManager.updateCredential(user, new Password(password));
    }

    public List<AccountTypeEntity> findUserByRole(String role) {
        Query query = em.createNamedQuery(UsuarioService.USERS_BY_ROLE).
                setMaxResults(100);
        query.setParameter("roleName", role);

        return query.getResultList();
    }

    public List<AccountTypeEntity> findUsers() {
        Query query = em.createNamedQuery(UsuarioService.ALL).
                setMaxResults(100);

        return query.getResultList();
    }


    public List<RoleTypeEntity> findRole() {
        Query query = em.createNamedQuery(UsuarioService.ROLE_ALL).setMaxResults(100);

        return query.getResultList();
    }

    public User findUser(String loginName) {
        return BasicModel.getUser(identityManager, loginName);
    }

    public List<RoleTypeEntity> getUserRoles(String loginName) {
        Query query = em.createNamedQuery(UsuarioService.USERS_ROLE)
                .setParameter("loginName", loginName).setMaxResults(100);

        return query.getResultList();
    }

    public boolean hasUserRole(String loginName, String roleName) {
        List<RoleTypeEntity> roleList = getUserRoles(loginName);
        if (CollectionUtils.isEmpty(roleList)) {
            return false;
        } else {
            for (RoleTypeEntity roleTypeEntity : roleList) {
                if (roleTypeEntity.getName().equals(roleName)) return true;
            }
        }
        return false;
    }

    public void grantRoles(User user, List<RoleTypeEntity> roleList) {
        IdentityManager identityManager = this.partitionManager.createIdentityManager();
        RelationshipManager relationshipManager = this.partitionManager.createRelationshipManager();

        if (CollectionUtils.isEmpty(roleList)) {
            List<RoleTypeEntity> allRoleList = findRole();
            for (RoleTypeEntity roleTypeEntity : allRoleList) {
                Role role = new Role(roleTypeEntity.getName());
                revokeRole(relationshipManager, user, role);
            }
        } else {
            for (RoleTypeEntity roleTypeEntity : roleList) {
                Role role = BasicModel.getRole(identityManager, roleTypeEntity.getName());
                if (!hasRole(relationshipManager, user, role)) {
                    grantRole(relationshipManager, user, role);
                }
            }

        }
    }

    public void updateRoles(User user, List<RoleTypeEntity> grantRoleList, List<RoleTypeEntity> revokeRoleList) {
        RelationshipManager relationshipManager = this.partitionManager.createRelationshipManager();

        if (CollectionUtils.isEmpty(grantRoleList)) {
            List<RoleTypeEntity> allRoleList = findRole();
            for (RoleTypeEntity roleTypeEntity : allRoleList) {
                Role role = new Role(roleTypeEntity.getName());
                revokeRole(relationshipManager, user, role);
            }
        } else {
            for (RoleTypeEntity roleTypeEntity : revokeRoleList) {
                Role role = BasicModel.getRole(identityManager, roleTypeEntity.getName());
                if (hasRole(relationshipManager, user, role)) {
                    revokeRole(relationshipManager, user, role);
                }
            }
            for (RoleTypeEntity roleTypeEntity : grantRoleList) {
                Role role = BasicModel.getRole(identityManager, roleTypeEntity.getName());
                if (!hasRole(relationshipManager, user, role)) {
                    grantRole(relationshipManager, user, role);
                }
            }

        }
    }

    public Role getRole(String roleName) {
        IdentityManager identityManager = this.partitionManager.createIdentityManager();
        return BasicModel.getRole(identityManager, roleName);
    }

}
