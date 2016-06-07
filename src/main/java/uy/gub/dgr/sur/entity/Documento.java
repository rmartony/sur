package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
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
        @NamedQuery(name = Documento.ID, query = "SELECT d FROM Documento d where d.id = :id"),
        @NamedQuery(name = Documento.BY_CODIGO, query = "SELECT d FROM Documento d where d.codigo = :codigo"),
        @NamedQuery(name = Documento.ALL, query = "SELECT d FROM Documento d order by d.nombre")
})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo", "fechaVencimiento"}))
@Audited
public class Documento extends BaseEntity implements Serializable {
    public final static String ID = "Tasa.id";
    public final static String BY_CODIGO = "Tasa.codigo";
    public final static String ALL = "Tasa.all";

    @ManyToOne
    private Registro registro;

    @NotEmpty
    private String nombre;

    /**
     * El monto actual de la tasa
     */
    @NotEmpty
    private BigDecimal monto;

    /**
     * La fecha de vencimiento del valor de la tasa
     */
    @NotEmpty
    private Date fechaVencimiento;

}
