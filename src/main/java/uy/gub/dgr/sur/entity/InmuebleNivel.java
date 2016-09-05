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
        @NamedQuery(name = InmuebleNivel.ALL, query = "SELECT d FROM InmuebleNivel d order by d.descripcion"),
        @NamedQuery(name = InmuebleNivel.BY_ID, query = "SELECT d FROM InmuebleNivel d where d.id = :id"),
        @NamedQuery(name = InmuebleNivel.BY_CODIGO, query = "SELECT s FROM InmuebleNivel s where s.codigo = :codigo"),
        @NamedQuery(name = InmuebleNivel.BY_DESCRIPCION, query = "SELECT d FROM InmuebleNivel d where d.descripcion = :descripcion"),
        @NamedQuery(name = InmuebleNivel.TOTAL, query = "SELECT COUNT(d) FROM InmuebleNivel d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class InmuebleNivel extends BaseEntity {
    public final static String ALL = "InmuebleNivel.all";
    public final static String BY_ID = "InmuebleNivel.id";
    public final static String BY_CODIGO = "InmuebleNivel.codigo";
    public final static String BY_DESCRIPCION = "InmuebleNivel.descripcion";
    public final static String TOTAL = "InmuebleNivel.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
