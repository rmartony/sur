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
        @NamedQuery(name = PersonaJuridica.ALL, query = "SELECT d FROM PersonaJuridica d order by d.nombre"),
        @NamedQuery(name = PersonaJuridica.BY_ID, query = "SELECT d FROM PersonaJuridica d where d.id = :id"),
        @NamedQuery(name = PersonaJuridica.BY_CODIGO, query = "SELECT s FROM PersonaJuridica s where s.nombre = :nombre"),
        @NamedQuery(name = PersonaJuridica.TOTAL, query = "SELECT COUNT(d) FROM PersonaJuridica d")})
@Cacheable
@Table(indexes = {@Index(name = "perjur_uni", columnList = "nombre", unique = true)})
@Audited
@SQLDelete(sql = "update PersonaJuridica SET fechaBaja = current_date where id = ?")
@Where(clause = "fechaBaja is null")
public class PersonaJuridica extends BaseEntity {
    public final static String ALL = "PersonaJuridica.all";
    public final static String BY_ID = "PersonaJuridica.id";
    public final static String BY_CODIGO = "PersonaJuridica.codigo";
    public final static String TOTAL = "PersonaJuridica.countTotal";

    @NotEmpty
    private String nombre;

}
