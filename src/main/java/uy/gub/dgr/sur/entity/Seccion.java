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
        @NamedQuery(name = Seccion.ALL, query = "SELECT r FROM Seccion r order by r.descripcion"),
        @NamedQuery(name = Seccion.BY_ID, query = "SELECT r FROM Seccion r where r.id = :id"),
        @NamedQuery(name = Seccion.BY_CODIGO, query = "SELECT r FROM Seccion r where r.codigo = :codigo"),
        @NamedQuery(name = Seccion.BY_DESCRIPCION, query = "SELECT r FROM Seccion r where r.descripcion = :descripcion"),
        @NamedQuery(name = Seccion.TOTAL, query = "SELECT COUNT(r) FROM Seccion r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo", "registro_id"}))
@Audited
@SQLDelete(sql = "update Seccion SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class Seccion extends BaseEntity {
    public final static String ALL = "Seccion.all";
    public final static String BY_ID = "Seccion.id";
    public final static String BY_DESCRIPCION = "Seccion.descripcion";
    public final static String BY_CODIGO = "Seccion.codigo";
    public final static String TOTAL = "Seccion.countTotal";

    @NotEmpty
    private String codigo;

    @ManyToOne(cascade = CascadeType.ALL)
    private Registro registro;

    @NotEmpty
    private String descripcion;

}
