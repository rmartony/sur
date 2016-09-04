package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true, exclude = {"descripcion"})
@NamedQueries({
        @NamedQuery(name = PadronNivel.ALL, query = "SELECT d FROM PadronNivel d order by d.descripcion"),
        @NamedQuery(name = PadronNivel.BY_ID, query = "SELECT d FROM PadronNivel d where d.id = :id"),
        @NamedQuery(name = PadronNivel.BY_CODIGO, query = "SELECT s FROM PadronNivel s where s.codigo = :codigo"),
        @NamedQuery(name = PadronNivel.BY_DESCRIPCION, query = "SELECT d FROM PadronNivel d where d.descripcion = :descripcion"),
        @NamedQuery(name = PadronNivel.TOTAL, query = "SELECT COUNT(d) FROM PadronNivel d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class PadronNivel extends BaseEntity {
    public final static String ALL = "PadronNivel.all";
    public final static String BY_ID = "PadronNivel.id";
    public final static String BY_CODIGO = "PadronNivel.codigo";
    public final static String BY_DESCRIPCION = "PadronNivel.descripcion";
    public final static String TOTAL = "PadronNivel.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
