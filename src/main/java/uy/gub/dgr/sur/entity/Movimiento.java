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
        @NamedQuery(name = Movimiento.ALL, query = "SELECT r FROM Movimiento r order by r.codigo"),
        @NamedQuery(name = Movimiento.BY_ID, query = "SELECT r FROM Movimiento r where r.id = :id"),
        @NamedQuery(name = Movimiento.BY_CODIGO, query = "SELECT r FROM Movimiento r where r.codigo = :codigo"),
        @NamedQuery(name = Movimiento.BY_NAME, query = "SELECT r FROM Movimiento r where r.descripcion = :descripcion"),
        @NamedQuery(name = Movimiento.TOTAL, query = "SELECT COUNT(r) FROM Movimiento r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class Movimiento extends BaseEntity implements Serializable {
    public final static String ALL = "Movimiento.all";
    public final static String BY_ID = "Movimiento.id";
    public final static String BY_NAME = "Movimiento.name";
    public final static String BY_CODIGO = "Movimiento.codigo";
    public final static String TOTAL = "Movimiento.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

    private String accion;

}
