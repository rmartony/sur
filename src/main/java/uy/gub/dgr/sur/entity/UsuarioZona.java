package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import uy.gub.dgr.sur.service.UsuarioService;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * User: rmartony
 * Date: 29/11/13
 * Time: 06:34 PM
 */
@Entity
@Cacheable
@NamedQueries({
        @NamedQuery(name = UsuarioZona.ALL, query = "SELECT z FROM UsuarioZona z"),
        @NamedQuery(name = UsuarioZona.BY_USUARIO_ID, query = "SELECT z FROM UsuarioZona z where z.userId = :userId"),
        @NamedQuery(name = UsuarioZona.BY_USUARIO_ZONA, query = "SELECT z FROM UsuarioZona z where z.zonas = :zonaList"),
        @NamedQuery(name = UsuarioZona.ZONAS_BY_USUARIO_ID, query = "SELECT z.zonas FROM UsuarioZona z where z.userId = :userId"),
        @NamedQuery(name = UsuarioZona.TOTAL, query = "SELECT COUNT(z) FROM UsuarioZona z"),

        @NamedQuery(name = UsuarioService.ALL, query = "SELECT a FROM AccountTypeEntity a,IdentityTypeEntity i " +
                "WHERE a.id = i.id and i.enabled = true"),
        @NamedQuery(name = UsuarioService.ROLE_ALL, query = "SELECT a FROM RoleTypeEntity a"),
        @NamedQuery(name = UsuarioService.USERS_ROLE, query = "SELECT distinct r FROM AccountTypeEntity a, IdentityTypeEntity i, RelationshipIdentityTypeEntity re1, RelationshipIdentityTypeEntity re2, RoleTypeEntity r " +
                "WHERE a.loginName = :loginName and i.id = a.id and i.enabled = true and re1.identityType.id = a.id and re1.owner.id = re2.owner.id and re2.descriptor = 'role' and r.id = re2.identityType.id"),
        @NamedQuery(name = UsuarioService.USERS_BY_ROLE, query = "SELECT a FROM AccountTypeEntity a,RelationshipIdentityTypeEntity re1, RelationshipIdentityTypeEntity re2, RoleTypeEntity r " +
                "WHERE r.name = :roleName and re1.identityType.id = a.id and re1.owner.id = re2.owner.id and re2.descriptor = 'role' and r.id = re2.identityType.id"),
        @NamedQuery(name = UsuarioService.TOTAL, query = "SELECT COUNT(r) FROM Torrero r")
})
@EqualsAndHashCode(callSuper = true, exclude = {"zonas"})
@Data
@Audited
public class UsuarioZona extends BaseEntity {
    public final static String ALL = "UsuarioZona.all";
    public final static String BY_USUARIO_ID = "UsuarioZona.usuario.id";
    public final static String BY_USUARIO_ZONA = "UsuarioZona.usuario.zona";
    public final static String ZONAS_BY_USUARIO_ID = "UsuarioZona.zonas.usuario.id";
    public final static String TOTAL = "UsuarioZona.countTotal";

    @NotNull
    private String userId;

    @ManyToMany
    private List<Zona> zonas;

}
