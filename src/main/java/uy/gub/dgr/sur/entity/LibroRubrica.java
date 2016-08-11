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
        @NamedQuery(name = LibroRubrica.ALL, query = "SELECT r FROM LibroRubrica r order by r.codigo"),
        @NamedQuery(name = LibroRubrica.BY_ID, query = "SELECT r FROM LibroRubrica r where r.id = :id"),
        @NamedQuery(name = LibroRubrica.BY_CODIGO, query = "SELECT r FROM LibroRubrica r where r.codigo = :codigo"),
        @NamedQuery(name = LibroRubrica.BY_DESCRIPTION, query = "SELECT r FROM LibroRubrica r where r.descripcion = :descripcion"),
        @NamedQuery(name = LibroRubrica.TOTAL, query = "SELECT COUNT(r) FROM LibroRubrica r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class LibroRubrica extends BaseEntity implements Serializable {
    public final static String ALL = "LibroRubrica.all";
    public final static String BY_ID = "LibroRubrica.id";
    public final static String BY_DESCRIPTION = "LibroRubrica.descripcion";
    public final static String BY_CODIGO = "LibroRubrica.codigo";
    public final static String TOTAL = "LibroRubrica.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
