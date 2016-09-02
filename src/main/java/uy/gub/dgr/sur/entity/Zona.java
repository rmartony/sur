package uy.gub.dgr.sur.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * User: rmartony
 * Date: 29/11/13
 * Time: 06:34 PM
 */
@Entity
@Cacheable
@NamedQueries({
        @NamedQuery(name = Zona.ALL, query = "SELECT z FROM Zona z order by z.nombre"),
        @NamedQuery(name = Zona.BY_ID, query = "SELECT z FROM Zona z where z.id = :id"),
        @NamedQuery(name = Zona.BY_NAME, query = "SELECT z FROM Zona z where z.nombre = :nombre"),
        @NamedQuery(name = Zona.TOTAL, query = "SELECT COUNT(z) FROM Zona z"),
        @NamedQuery(name = Zona.EXISTS_IN_SITIO, query = "SELECT s FROM Sitio s where s.zona in (:zonaList)")
})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nombre"}))
@Audited
public class Zona extends BaseEntity {
    public final static String ALL = "Zona.all";
    public final static String BY_ID = "Zona.id";
    public final static String BY_NAME = "Zona.name";
    public final static String EXISTS_IN_SITIO = "Zona.existsInSitio";
    public final static String TOTAL = "Zona.countTotal";

    @NotEmpty
    @Getter
    @Setter
    @Size(min = 2, max = 50)
    private String nombre;

}
