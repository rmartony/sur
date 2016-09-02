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
        @NamedQuery(name = Combustible.ALL, query = "SELECT d FROM Combustible d order by d.descripcion"),
        @NamedQuery(name = Combustible.BY_ID, query = "SELECT d FROM Combustible d where d.id = :id"),
        @NamedQuery(name = Combustible.BY_CODIGO, query = "SELECT s FROM Combustible s where s.codigo = :codigo"),
        @NamedQuery(name = Combustible.BY_DESCRIPCION, query = "SELECT d FROM Combustible d where d.descripcion = :descripcion"),
        @NamedQuery(name = Combustible.TOTAL, query = "SELECT COUNT(d) FROM Combustible d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class Combustible extends BaseEntity {
    public final static String ALL = "Combustible.all";
    public final static String BY_ID = "Combustible.id";
    public final static String BY_CODIGO = "Combustible.codigo";
    public final static String BY_DESCRIPCION = "Combustible.descripcion";
    public final static String TOTAL = "Combustible.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
