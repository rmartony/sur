package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:22 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"id", "descripcion"})
@NamedQueries({
        @NamedQuery(name = Sede.ALL, query = "SELECT d FROM Sede d order by d.descripcion"),
        @NamedQuery(name = Sede.BY_ID, query = "SELECT d FROM Sede d where d.id = :id"),
        @NamedQuery(name = Sede.BY_CODIGO, query = "SELECT s FROM Sede s where s.codigo = :codigo"),
        @NamedQuery(name = Sede.BY_DESCRIPCION, query = "SELECT d FROM Sede d where d.descripcion = :descripcion"),
        @NamedQuery(name = Sede.TOTAL, query = "SELECT COUNT(d) FROM Sede d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class Sede extends BaseEntity implements Serializable {
    public final static String ALL = "Sede.all";
    public final static String BY_ID = "Sede.id";
    public final static String BY_CODIGO = "Sede.codigo";
    public final static String BY_DESCRIPCION = "Sede.descripcion";
    public final static String TOTAL = "Sede.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

    @NotEmpty
    private int anio;

}
