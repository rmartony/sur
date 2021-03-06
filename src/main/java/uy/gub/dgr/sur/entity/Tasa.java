package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:25 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"nombre", "monto"})
@NamedQueries({
        @NamedQuery(name = Tasa.ID, query = "SELECT t FROM Tasa t where t.id = :id"),
        @NamedQuery(name = Tasa.BY_CODIGO, query = "SELECT s FROM Tasa s where s.codigo = :codigo"),
        @NamedQuery(name = Tasa.ALL, query = "SELECT t FROM Tasa t order by t.nombre")
})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo", "fechaVencimiento"}))
@Audited
@SQLDelete(sql = "update Tasa SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class Tasa extends BaseEntity {
    public final static String ID = "Tasa.id";
    public final static String BY_CODIGO = "Tasa.codigo";
    public final static String ALL = "Tasa.all";

    public static final String DOC_COMUN = "5";
    public static final String DOC_COOPERATIVA = "6";
    public static final String DOC_RESERVA = "7";
    public static final String DOC_EXONERADO = "8";
    public static final String DOC_URGENTE = "9";
    public static final String RUBRICA_COMUN = "10";
    public static final String RUBRICA_COOPERATIVAS = "11";
    public static final String RUBRICA_EXONERADO = "12";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String nombre;

    /**
     * El monto actual de la tasa
     */
    @NotNull
    private BigDecimal monto;

    /**
     * La fecha de vencimiento del valor de la tasa
     */
    @NotNull
    private Date fechaVencimiento;

    public boolean isExonerada() {
        return DOC_EXONERADO.equals(this.codigo) || RUBRICA_EXONERADO.equals(this.codigo);
    }

}
