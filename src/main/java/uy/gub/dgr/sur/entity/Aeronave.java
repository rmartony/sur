package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:22 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"fecha"})
@NamedQueries({
        @NamedQuery(name = Aeronave.ALL, query = "SELECT d FROM Aeronave d order by d.matricula"),
        @NamedQuery(name = Aeronave.BY_ID, query = "SELECT d FROM Aeronave d where d.id = :id"),
        @NamedQuery(name = Aeronave.BY_CODIGO, query = "SELECT s FROM Aeronave s where s.codigo = :codigo"),
        @NamedQuery(name = Aeronave.BY_DESCRIPCION, query = "SELECT d FROM Aeronave d where d.matricula = :matricula"),
        @NamedQuery(name = Aeronave.TOTAL, query = "SELECT COUNT(d) FROM Aeronave d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
@SQLDelete(sql = "update Aeronave SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class Aeronave extends BaseEntity {
    public final static String ALL = "Aeronave.all";
    public final static String BY_ID = "Aeronave.id";
    public final static String BY_CODIGO = "Aeronave.codigo";
    public final static String BY_DESCRIPCION = "Aeronave.descripcion";
    public final static String TOTAL = "Aeronave.countTotal";
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<MotorAeronave> motorAeronaveList;
    @NotEmpty
    private String codigo;
    @NotEmpty
    private String matricula;
    @NotEmpty
    private String pais;
    private String marca;
    private String modelo;
    private Integer serie;
    private Integer numero;
    private Integer folio;
    private Date fecha;

}
