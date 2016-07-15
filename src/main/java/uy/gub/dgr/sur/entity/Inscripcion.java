package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:25 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"estado", "tipoDocumento", "montoList", "observacion", "documento"})
@NamedQueries({
        @NamedQuery(name = Inscripcion.ID, query = "SELECT d FROM Inscripcion d where d.id = :id"),
        @NamedQuery(name = Inscripcion.ALL, query = "SELECT d FROM Inscripcion d")
})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"ordinal"}))
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TIPO_INSCRIPCION")
@Audited
public class Inscripcion extends BaseEntity implements Serializable {
    public final static String ID = "Inscripcion.id";
    public final static String ALL = "Inscripcion.all";
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Monto> montoList;
    private int ordinal;
    @ManyToOne
    private Acto acto;
    @ManyToOne
    private Estado estado; // calificacion
    @ManyToOne
    private Movimiento movimiento;
    private String observacion; // observacion del acto

    @ManyToOne
    private Documento documento; // es para poder acceder a un documento dada la inscripción




}
