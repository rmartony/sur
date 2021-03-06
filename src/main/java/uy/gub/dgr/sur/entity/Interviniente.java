package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:25 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"observaciones", "descripcion", "seccion"})
@NamedQueries({
        @NamedQuery(name = Interviniente.ID, query = "SELECT t FROM Interviniente t where t.id = :id"),
        @NamedQuery(name = Interviniente.ALL, query = "SELECT t FROM Interviniente t order by t.descripcion"),
})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
@SQLDelete(sql = "update Interviniente SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class Interviniente extends BaseEntity {
    public final static String ID = "Interviniente.id";
    public final static String ALL = "Interviniente.all";
    public final static String EXISTS_IN_NODO = "Interviniente.exists";

    @NotEmpty
    private String codigo;

    @Size(max = 255)
    private String observaciones;

    @NotEmpty
    private String descripcion;

    @ManyToOne(cascade = CascadeType.ALL)
    private Seccion seccion;
}
