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
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
        @NamedQuery(name = Monto.ALL, query = "SELECT r FROM Monto r"),
        @NamedQuery(name = Monto.BY_ID, query = "SELECT r FROM Monto r where r.id = :id"),
        @NamedQuery(name = Monto.TOTAL, query = "SELECT COUNT(r) FROM Monto r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"moneda", "monto"}))
@Audited
public class Monto extends BaseEntity implements Serializable {
    public final static String ALL = "Monto.all";
    public final static String BY_ID = "Monto.id";
    public final static String TOTAL = "Monto.countTotal";

    @NotEmpty
    private double monto;

    @ManyToOne
    private Moneda moneda;

}