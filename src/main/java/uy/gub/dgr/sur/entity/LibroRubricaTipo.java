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
        @NamedQuery(name = LibroRubricaTipo.ALL, query = "SELECT r FROM LibroRubricaTipo r order by r.codigo"),
        @NamedQuery(name = LibroRubricaTipo.BY_ID, query = "SELECT r FROM LibroRubricaTipo r where r.id = :id"),
        @NamedQuery(name = LibroRubricaTipo.BY_CODIGO, query = "SELECT r FROM LibroRubricaTipo r where r.codigo = :codigo"),
        @NamedQuery(name = LibroRubricaTipo.BY_DESCRIPTION, query = "SELECT r FROM LibroRubricaTipo r where r.descripcion = :descripcion"),
        @NamedQuery(name = LibroRubricaTipo.TOTAL, query = "SELECT COUNT(r) FROM LibroRubricaTipo r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
@SQLDelete(sql = "update LibroRubricaTipo SET fechaBaja = current_date where id = ?")
@Where(clause = "fechaBaja is null")
public class LibroRubricaTipo extends BaseEntity {
    public final static String ALL = "LibroRubricaTipo.all";
    public final static String BY_ID = "LibroRubricaTipo.id";
    public final static String BY_DESCRIPTION = "LibroRubricaTipo.descripcion";
    public final static String BY_CODIGO = "LibroRubricaTipo.codigo";
    public final static String TOTAL = "LibroRubricaTipo.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
