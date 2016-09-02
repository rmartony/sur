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
        @NamedQuery(name = Moneda.ALL, query = "SELECT r FROM Moneda r order by r.codigo"),
        @NamedQuery(name = Moneda.BY_ID, query = "SELECT r FROM Moneda r where r.id = :id"),
        @NamedQuery(name = Moneda.BY_CODIGO, query = "SELECT r FROM Moneda r where r.codigo = :codigo"),
        @NamedQuery(name = Moneda.BY_DESCRIPTION, query = "SELECT r FROM Moneda r where r.descripcion = :descripcion"),
        @NamedQuery(name = Moneda.TOTAL, query = "SELECT COUNT(r) FROM Moneda r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class Moneda extends BaseEntity {
    public final static String ALL = "Moneda.all";
    public final static String BY_ID = "Moneda.id";
    public final static String BY_DESCRIPTION = "Moneda.descripcion";
    public final static String BY_CODIGO = "Moneda.codigo";
    public final static String TOTAL = "Moneda.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
