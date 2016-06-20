package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:25 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"estado", "tipoDocumento"})
@NamedQueries({
        @NamedQuery(name = Inscripcion.ID, query = "SELECT d FROM Inscripcion d where d.id = :id"),
        @NamedQuery(name = Inscripcion.ALL, query = "SELECT d FROM Inscripcion d")
})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"registro", "sede", "anio", "numero", "bis"}))
@Audited
public class Inscripcion extends BaseEntity implements Serializable {
    public final static String ID = "Inscripcion.id";
    public final static String ALL = "Inscripcion.all";

    int ordinal;
    @ManyToOne
    Acto acto;
    @ManyToOne
    private Estado estado; // calificacion
    @ManyToOne
    private Movimiento movimiento;




}
