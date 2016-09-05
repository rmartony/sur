package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:22 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"fechaDua", "anio", "chasis", "combustible",
        "motor", "cilindros", "placaMunicipal", "matriculaRegistral", "dua", "tipoAutomotor", "hp"})
@NamedQueries({
        @NamedQuery(name = Automotor.ALL, query = "SELECT d FROM Automotor d order by d.matricula"),
        @NamedQuery(name = Automotor.BY_ID, query = "SELECT d FROM Automotor d where d.id = :id"),
        @NamedQuery(name = Automotor.BY_CODIGO, query = "SELECT s FROM Automotor s where s.codigo = :codigo"),
        @NamedQuery(name = Automotor.BY_DESCRIPCION, query = "SELECT d FROM Automotor d where d.matricula = :matricula"),
        @NamedQuery(name = Automotor.TOTAL, query = "SELECT COUNT(d) FROM Automotor d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"departamento_id", "localidad_id", "padron"}))
@Audited
public class Automotor extends BaseEntity {
    public final static String ALL = "Automotor.all";
    public final static String BY_ID = "Automotor.id";
    public final static String BY_CODIGO = "Automotor.codigo";
    public final static String BY_DESCRIPCION = "Automotor.descripcion";
    public final static String TOTAL = "Automotor.countTotal";

    @ManyToOne
    TipoAutomotor tipoAutomotor;
    @ManyToOne
    private Departamento departamento;
    @ManyToOne
    private Localidad localidad;
    @NotNull
    private Integer padron;
    private int dua;
    private Date fechaDua;
    private int anio;
    private String chasis;
    @ManyToOne
    private Combustible combustible;
    private String motor;
    private short cilindros;
    private String placaMunicipal; // matricula
    private int matriculaRegistral;

    private int hp; // caballos de fuerza

}
