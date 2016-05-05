package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
        @NamedQuery(name = Estructura.ALL, query = "SELECT r FROM Estructura r order by r.nombre"),
        @NamedQuery(name = Estructura.BY_ID, query = "SELECT r FROM Estructura r where r.id = :id"),
        @NamedQuery(name = Estructura.BY_NAME, query = "SELECT r FROM Estructura r where r.nombre = :nombre"),
        @NamedQuery(name = Estructura.EXISTS_IN_SITIO, query = "SELECT s FROM Sitio s where s.estructura in (:estructuraList)"),
        @NamedQuery(name = Estructura.TOTAL, query = "SELECT COUNT(r) FROM Estructura r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nombre"}))
@Audited
public class Estructura extends BaseEntity implements Serializable {
    public final static String ALL = "Estructura.all";
    public final static String BY_ID = "Estructura.id";
    public final static String BY_NAME = "Estructura.name";
    public final static String EXISTS_IN_SITIO = "Estructura.existsInSitio";
    public final static String TOTAL = "Estructura.countTotal";

    @NotEmpty
    @Size(min = 1, max = 60)
    private String nombre;

}
