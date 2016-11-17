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
        @NamedQuery(name = MarcaAutomotor.ALL, query = "SELECT r FROM MarcaAutomotor r order by r.codigo"),
        @NamedQuery(name = MarcaAutomotor.BY_ID, query = "SELECT r FROM MarcaAutomotor r where r.id = :id"),
        @NamedQuery(name = MarcaAutomotor.BY_CODIGO, query = "SELECT r FROM MarcaAutomotor r where r.codigo = :codigo"),
        @NamedQuery(name = MarcaAutomotor.BY_DESCRIPTION, query = "SELECT r FROM MarcaAutomotor r where r.descripcion = :descripcion"),
        @NamedQuery(name = MarcaAutomotor.TOTAL, query = "SELECT COUNT(r) FROM MarcaAutomotor r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
@SQLDelete(sql = "update MarcaAutomotor SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class MarcaAutomotor extends BaseEntity {
    public final static String ALL = "MarcaAutomotor.all";
    public final static String BY_ID = "MarcaAutomotor.id";
    public final static String BY_DESCRIPTION = "MarcaAutomotor.descripcion";
    public final static String BY_CODIGO = "MarcaAutomotor.codigo";
    public final static String TOTAL = "MarcaAutomotor.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
