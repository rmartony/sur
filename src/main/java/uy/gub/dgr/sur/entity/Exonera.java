package uy.gub.dgr.sur.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: rmartony
 * Date: 13/12/13
 * Time: 05:51 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"nombre", "descripcion"})
@NamedQueries({
        @NamedQuery(name = Exonera.ALL, query = "SELECT d FROM Exonera d order by d.descripcion"),
        @NamedQuery(name = Exonera.BY_ID, query = "SELECT d FROM Exonera d where d.id = :id"),
        @NamedQuery(name = Exonera.BY_CODIGO, query = "SELECT d FROM Exonera d where d.codigo = :codigo"),
        @NamedQuery(name = Exonera.TOTAL, query = "SELECT COUNT(d) FROM Exonera d")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class Exonera extends BaseEntity implements Serializable {
    public final static String ALL = "Exonera.all";
    public final static String BY_ID = "Exonera.id";
    public final static String BY_CODIGO = "Exonera.codigo";
    public final static String TOTAL = "Exonera.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String nombre;

    private String descripcion;

}
