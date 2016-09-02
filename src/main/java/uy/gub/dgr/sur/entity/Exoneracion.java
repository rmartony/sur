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
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
        @NamedQuery(name = Exoneracion.ALL, query = "SELECT r FROM Exoneracion r order by r.descripcion"),
        @NamedQuery(name = Exoneracion.BY_ID, query = "SELECT r FROM Exoneracion r where r.id = :id"),
        @NamedQuery(name = Exoneracion.BY_CODIGO, query = "SELECT r FROM Exoneracion r where r.codigo = :codigo"),
        @NamedQuery(name = Exoneracion.BY_DESCRIPTION, query = "SELECT r FROM Exoneracion r where r.descripcion = :descripcion"),
        @NamedQuery(name = Exoneracion.TOTAL, query = "SELECT COUNT(r) FROM Exoneracion r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class Exoneracion extends BaseEntity {
    public final static String ALL = "Exoneracion.all";
    public final static String BY_ID = "Exoneracion.id";
    public final static String BY_DESCRIPTION = "Exoneracion.description";
    public final static String BY_CODIGO = "Exoneracion.codigo";
    public final static String TOTAL = "Exoneracion.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
