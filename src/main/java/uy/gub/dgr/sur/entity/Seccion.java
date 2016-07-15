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
        @NamedQuery(name = Seccion.ALL, query = "SELECT r FROM Seccion r order by r.nombre"),
        @NamedQuery(name = Seccion.BY_ID, query = "SELECT r FROM Seccion r where r.id = :id"),
        @NamedQuery(name = Seccion.BY_CODIGO, query = "SELECT r FROM Seccion r where r.codigo = :codigo"),
        @NamedQuery(name = Seccion.BY_NAME, query = "SELECT r FROM Seccion r where r.descripcion = :descripcion"),
        @NamedQuery(name = Seccion.TOTAL, query = "SELECT COUNT(r) FROM Seccion r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class Seccion extends BaseEntity implements Serializable {
    public final static String ALL = "Seccion.all";
    public final static String BY_ID = "Seccion.id";
    public final static String BY_NAME = "Seccion.name";
    public final static String BY_CODIGO = "Seccion.codigo";
    public final static String TOTAL = "Seccion.countTotal";

    @NotEmpty
    private String codigo;

    @ManyToOne
    private Registro registro;

    @NotEmpty
    private String descripcion;

}
