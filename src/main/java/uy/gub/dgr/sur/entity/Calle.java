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
@EqualsAndHashCode(callSuper = true, exclude = {"descripcion"})
@NamedQueries({
        @NamedQuery(name = Calle.ALL, query = "SELECT d FROM Calle d order by d.descripcion"),
        @NamedQuery(name = Calle.BY_ID, query = "SELECT d FROM Calle d where d.id = :id"),
        @NamedQuery(name = Calle.BY_CODIGO, query = "SELECT s FROM Calle s where s.codigo = :codigo"),
        @NamedQuery(name = Calle.BY_DESCRIPCION, query = "SELECT d FROM Calle d where d.descripcion = :descripcion"),
        @NamedQuery(name = Calle.TOTAL, query = "SELECT COUNT(d) FROM Calle d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
@SQLDelete(sql = "update Calle SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class Calle extends BaseEntity {
    public final static String ALL = "Calle.all";
    public final static String BY_ID = "Calle.id";
    public final static String BY_CODIGO = "Calle.codigo";
    public final static String BY_DESCRIPCION = "Calle.descripcion";
    public final static String TOTAL = "Calle.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
