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
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
        @NamedQuery(name = Emisor.ALL, query = "SELECT d FROM Emisor d order by d.descripcion"),
        @NamedQuery(name = Emisor.BY_ID, query = "SELECT d FROM Emisor d where d.id = :id"),
        @NamedQuery(name = Emisor.BY_CODIGO, query = "SELECT s FROM Emisor s where s.codigo = :codigo"),
        @NamedQuery(name = Emisor.BY_DESCRIPTION, query = "SELECT d FROM Emisor d where d.descripcion = :descripcion"),
        @NamedQuery(name = Emisor.TOTAL, query = "SELECT COUNT(d) FROM Emisor d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class Emisor extends BaseEntity implements Serializable {
    public final static String ALL = "Emisor.all";
    public final static String BY_ID = "Emisor.id";
    public final static String BY_CODIGO = "Emisor.codigo";
    public final static String BY_DESCRIPTION = "Emisor.name";
    public final static String TOTAL = "Emisor.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
