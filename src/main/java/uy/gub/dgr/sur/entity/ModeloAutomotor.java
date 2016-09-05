package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:22 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"descripcion"})
@NamedQueries({
        @NamedQuery(name = ModeloAutomotor.ALL, query = "SELECT d FROM ModeloAutomotor d order by d.descripcion"),
        @NamedQuery(name = ModeloAutomotor.BY_ID, query = "SELECT d FROM ModeloAutomotor d where d.id = :id"),
        @NamedQuery(name = ModeloAutomotor.BY_CODIGO, query = "SELECT s FROM ModeloAutomotor s where s.codigo = :codigo"),
        @NamedQuery(name = ModeloAutomotor.BY_DESCRIPCION, query = "SELECT d FROM ModeloAutomotor d where d.descripcion = :descripcion"),
        @NamedQuery(name = ModeloAutomotor.TOTAL, query = "SELECT COUNT(d) FROM ModeloAutomotor d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class ModeloAutomotor extends BaseEntity {
    public final static String ALL = "ModeloAutomotor.all";
    public final static String BY_ID = "ModeloAutomotor.id";
    public final static String BY_CODIGO = "ModeloAutomotor.codigo";
    public final static String BY_DESCRIPCION = "ModeloAutomotor.descripcion";
    public final static String TOTAL = "ModeloAutomotor.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

    @ManyToOne(cascade = CascadeType.ALL)
    private MarcaAutomotor marcaAutomotor;

}
