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
        @NamedQuery(name = MarcaAutomotor.ALL, query = "SELECT r FROM Marca r order by r.codigo"),
        @NamedQuery(name = MarcaAutomotor.BY_ID, query = "SELECT r FROM Marca r where r.id = :id"),
        @NamedQuery(name = MarcaAutomotor.BY_CODIGO, query = "SELECT r FROM Marca r where r.codigo = :codigo"),
        @NamedQuery(name = MarcaAutomotor.BY_DESCRIPTION, query = "SELECT r FROM Marca r where r.descripcion = :descripcion"),
        @NamedQuery(name = MarcaAutomotor.TOTAL, query = "SELECT COUNT(r) FROM Marca r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class MarcaAutomotor extends BaseEntity {
    public final static String ALL = "Marca.all";
    public final static String BY_ID = "Marca.id";
    public final static String BY_DESCRIPTION = "Marca.descripcion";
    public final static String BY_CODIGO = "Marca.codigo";
    public final static String TOTAL = "Marca.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
