package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:25 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"descripcion", "seccion", "duracion", "periodo"})
@NamedQueries({
        @NamedQuery(name = Acto.ID, query = "SELECT d FROM Acto d where d.id = :id"),
        @NamedQuery(name = Acto.BY_CODIGO, query = "SELECT d FROM Acto d where d.codigo = :codigo"),
        @NamedQuery(name = Acto.ALL, query = "SELECT d FROM Acto d")
})
@Cacheable
@Table(indexes = {@Index(name = "codActo", columnList = "codigo, seccion_id", unique = true)})
@Audited
public class Acto extends BaseEntity {
    public final static String ID = "Acto.id";
    public final static String BY_CODIGO = "Acto.codigo";
    public final static String ALL = "Acto.all";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Seccion seccion;

    private String duracion;

    private short periodo;


}
