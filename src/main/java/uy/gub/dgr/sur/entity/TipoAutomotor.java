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
        @NamedQuery(name = TipoAutomotor.ALL, query = "SELECT r FROM TipoAutomotor r"),
        @NamedQuery(name = TipoAutomotor.BY_ID, query = "SELECT r FROM TipoAutomotor r where r.id = :id"),
        @NamedQuery(name = TipoAutomotor.TOTAL, query = "SELECT COUNT(r) FROM TipoAutomotor r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
@SQLDelete(sql = "update TipoAutomotor SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class TipoAutomotor extends BaseEntity {
    public final static String ALL = "TipoAutomotor.all";
    public final static String BY_ID = "TipoAutomotor.id";
    public final static String TOTAL = "TipoAutomotor.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
