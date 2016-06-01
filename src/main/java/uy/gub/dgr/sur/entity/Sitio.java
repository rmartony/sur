package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * User: rmartony
 * Date: 20/11/13
 * Time: 08:32 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"nombre", "direccion", "latitud", "longitud", "sede",
        "estructura", "alturaEstructura", "perteneceA", "observaciones", "fecha", "zona"})
@NamedQueries({
        @NamedQuery(name = Sitio.ALL, query = "SELECT z FROM Sitio z order by z.nombre"),
        @NamedQuery(name = Sitio.BY_ID, query = "SELECT z FROM Sitio z where z.id = :id"),
        @NamedQuery(name = Sitio.BY_SIGLA, query = "SELECT z FROM Sitio z where z.sigla = :sigla"),
        @NamedQuery(name = Sitio.BY_SIGLA_NOMBRE, query = "SELECT z FROM Sitio z where lower(z.sigla) like concat(concat('%',lower(:nombre)), '%') or lower(z.nombre) like concat(concat('%',lower(:nombre)),'%')"),
        @NamedQuery(name = Sitio.BY_SIGLA_ZONA, query = "SELECT z FROM Sitio z where lower(z.sigla) like concat(concat('%',lower(:nombre)), '%') or lower(z.nombre) like concat(concat('%',lower(:nombre)),'%') and z.zona.id in :idZonaList"),
        @NamedQuery(name = Sitio.TOTAL, query = "SELECT COUNT(z) FROM Sitio z")})
@Audited
public class Sitio extends BaseEntity implements Serializable {
    public final static String ALL = "Sitio.all";
    public final static String BY_ID = "Sitio.id";
    public final static String BY_SIGLA = "Sitio.sigla";
    public final static String BY_SIGLA_NOMBRE = "Sitio.sigla.nombre";
    public final static String BY_SIGLA_ZONA = "Sitio.sigla.zona";
    public final static String TOTAL = "Sitio.countTotal";

    @NotBlank
    private String sigla;

    @NotBlank
    private String nombre;

    @NotBlank
    private String direccion;

    @Range(min = -90, max = 90)
    private Double latitud;

    @Range(min = -180, max = 180)
    private Double longitud;

    private String localidad;

    @ManyToOne
    @NotNull
    private Sede sede;

    @ManyToOne
//    @NotNull // Esto deberia descomentarse, por ahora se deja por un tema de inconsistencia de datos
    private Estructura estructura;

    private Double alturaEstructura;

    private String perteneceA;

    @Size(max = 255)
    private String observaciones;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne
//    @NotNull // Esto deberia descomentarse, por ahora se deja por un tema de inconsistencia de datos
    private Zona zona;

    public Sitio() {
        fecha = new Date();
    }
}
