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
 * Time: 03:25 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"descripcion", "seccion", "duracion", "periodo"})
@NamedQueries({
        @NamedQuery(name = Acto.ID, query = "SELECT d FROM Acto d where d.id = :id"),
        @NamedQuery(name = Acto.ALL, query = "SELECT d FROM Acto d")
})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo", "registro", "seccion"}))
@Audited
public class Acto extends BaseEntity implements Serializable {
    public final static String ID = "Acto.id";
    public final static String ALL = "Acto.all";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

    @ManyToOne
    private Seccion seccion;

    private String duracion;

    private short periodo;





}
