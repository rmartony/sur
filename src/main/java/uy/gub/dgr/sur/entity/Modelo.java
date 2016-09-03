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
        @NamedQuery(name = Modelo.ALL, query = "SELECT d FROM Modelo d order by d.descripcion"),
        @NamedQuery(name = Modelo.BY_ID, query = "SELECT d FROM Modelo d where d.id = :id"),
        @NamedQuery(name = Modelo.BY_CODIGO, query = "SELECT s FROM Modelo s where s.codigo = :codigo"),
        @NamedQuery(name = Modelo.BY_DESCRIPCION, query = "SELECT d FROM Modelo d where d.descripcion = :descripcion"),
        @NamedQuery(name = Modelo.TOTAL, query = "SELECT COUNT(d) FROM Modelo d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class Modelo extends BaseEntity {
    public final static String ALL = "Modelo.all";
    public final static String BY_ID = "Modelo.id";
    public final static String BY_CODIGO = "Modelo.codigo";
    public final static String BY_DESCRIPCION = "Modelo.descripcion";
    public final static String TOTAL = "Modelo.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

    @ManyToOne(cascade = CascadeType.ALL)
    private Marca marca;

}
