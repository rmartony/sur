package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:22 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
        @NamedQuery(name = PersonaFisica.ALL, query = "SELECT d FROM PersonaFisica d order by d.apellido1"),
        @NamedQuery(name = PersonaFisica.BY_ID, query = "SELECT d FROM PersonaFisica d where d.id = :id"),
        @NamedQuery(name = PersonaFisica.BY_CODIGO, query = "SELECT s FROM PersonaFisica s where s.apellido1 = :apellido1"),
        @NamedQuery(name = PersonaFisica.TOTAL, query = "SELECT COUNT(d) FROM PersonaFisica d")})
@Cacheable
@Table(indexes = {@Index(name = "perfis_a1", columnList = "apellido1", unique = false),
        @Index(name = "perfis_a1n1", columnList = "apellido1, nombre1", unique = false),
        @Index(name = "perfis_uni", columnList = "apellido1, apellido2, nombre1, nombre2", unique = true)})
@Audited
@SQLDelete(sql = "update PersonaFisica SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class PersonaFisica extends BaseEntity {
    public final static String ALL = "PersonaFisica.all";
    public final static String BY_ID = "PersonaFisica.id";
    public final static String BY_CODIGO = "PersonaFisica.codigo";
    public final static String TOTAL = "PersonaFisica.countTotal";

    @NotEmpty
    private String apellido1;
    private String apellido2;
    @NotEmpty
    private String nombre1;
    private String nombre2;

}
