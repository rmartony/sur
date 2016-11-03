package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:25 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"estado", "acto", "movimiento", "montoList", "observacion", "sujetoList"})
@NamedQueries({
        @NamedQuery(name = Inscripcion.ID, query = "SELECT d FROM Inscripcion d where d.id = :id"),
        @NamedQuery(name = Inscripcion.ALL, query = "SELECT d FROM Inscripcion d")
})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"ordinal"}))
@Audited
@SQLDelete(sql = "update Inscripcion SET fechaBaja = current_date where id = ?")
@Where(clause = "fechaBaja is null")
public class Inscripcion extends BaseEntity {
    public final static String ID = "Inscripcion.id";
    public final static String ALL = "Inscripcion.all";

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Monto> montoList;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<Sujeto> sujetoList;

    private int ordinal;

    @NotNull
    @ManyToOne
    private Acto acto;

    @ManyToOne
    private Estado estado; // calificacion

    @NotNull
    @ManyToOne
    private Movimiento movimiento;

    private String observacion; // observacion del acto

    @ManyToOne
    private Documento documento; // es para poder acceder a un documento dada la inscripción




}
