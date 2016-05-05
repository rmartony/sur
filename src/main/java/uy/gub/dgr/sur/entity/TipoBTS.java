package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:25 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"observaciones", "nombre"})
@NamedQueries({
        @NamedQuery(name = TipoBTS.ID, query = "SELECT t FROM TipoBTS t where t.id = :id"),
        @NamedQuery(name = TipoBTS.ALL, query = "SELECT t FROM TipoBTS t order by t.nombre"),
        @NamedQuery(name = TipoBTS.EXISTS_IN_NODO, query = "SELECT n.sigla FROM Nodo n where n.tipoBTS in (:tipoBTSList)")
})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"modelo"}))
@Audited
public class TipoBTS extends BaseEntity implements Serializable {
    public final static String ID = "TipoBTS.id";
    public final static String ALL = "TipoBTS.all";
    public final static String EXISTS_IN_NODO = "TipoBTS.exists";

    @NotEmpty
    private String modelo;

    @Size(max = 255)
    private String observaciones;

    @NotEmpty
    private String nombre;
}
