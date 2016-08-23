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
@EqualsAndHashCode(callSuper = true, exclude = {"descripcion"})
@NamedQueries({
        @NamedQuery(name = Localidad.ALL, query = "SELECT d FROM Localidad d order by d.descripcion"),
        @NamedQuery(name = Localidad.BY_ID, query = "SELECT d FROM Localidad d where d.id = :id"),
        @NamedQuery(name = Localidad.BY_CODIGO, query = "SELECT s FROM Localidad s where s.codigo = :codigo"),
        @NamedQuery(name = Localidad.BY_DESCRIPCION, query = "SELECT d FROM Localidad d where d.descripcion = :descripcion"),
        @NamedQuery(name = Localidad.TOTAL, query = "SELECT COUNT(d) FROM Localidad d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class Localidad extends BaseEntity implements Serializable {
    public final static String ALL = "Localidad.all";
    public final static String BY_ID = "Localidad.id";
    public final static String BY_CODIGO = "Localidad.codigo";
    public final static String BY_DESCRIPCION = "Localidad.descripcion";
    public final static String TOTAL = "Localidad.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}