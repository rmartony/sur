package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
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
        @NamedQuery(name = Automotor.ALL, query = "SELECT d FROM Automotor d order by d.placaMunicipal"),
        @NamedQuery(name = Automotor.BY_ID, query = "SELECT d FROM Automotor d where d.id = :id"),
        @NamedQuery(name = Automotor.BY_PADRON, query = "SELECT s FROM Automotor s where s.padron = :padron"),
        @NamedQuery(name = Automotor.BY_DESCRIPCION, query = "SELECT d FROM Automotor d where d.placaMunicipal = :placaMunicipal"),
        @NamedQuery(name = Automotor.TOTAL, query = "SELECT COUNT(d) FROM Automotor d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"departamento_id", "localidad_id", "padron"}))
@Audited
@SQLDelete(sql = "update Automotor SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class Automotor extends BaseEntity {
    public final static String ALL = "Automotor.all";
    public final static String BY_ID = "Automotor.id";
    public final static String BY_PADRON = "Automotor.padron";
    public final static String BY_DESCRIPCION = "Automotor.descripcion";
    public final static String TOTAL = "Automotor.countTotal";

    @ManyToOne
    TipoAutomotor tipoAutomotor;
    @NotNull
    @ManyToOne
    private Departamento departamento;
    @NotNull
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
