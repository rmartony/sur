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
        @NamedQuery(name = Acto.ID, query = "SELECT d FROM Inscripcion d where d.id = :id"),
        @NamedQuery(name = Acto.ALL, query = "SELECT d FROM Inscripcion d")
})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"registro", "sede", "anio", "numero", "bis"}))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TIPO_INSCRIPCION")
@Audited
public class Acto extends BaseEntity implements Serializable {
    public final static String ID = "Inscripcion.id";
    public final static String ALL = "Inscripcion.all";

    private int ordinal;
    @ManyToOne
    private Acto acto;
    @ManyToOne
    private Estado estado; // calificacion
    @ManyToOne
    private Movimiento movimiento;
    @ManyToOne
    private Monto monto;

    @ManyToOne
    private Documento documento; // es para poder acceder a un documento dada la inscripción




}
