package uy.gub.dgr.sur.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * User: rmartony
 * Date: 13/12/13
 * Time: 05:51 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"nombre", "descripcion"})
@NamedQueries({
        @NamedQuery(name = Estado.ALL, query = "SELECT d FROM Estado d order by d.nombre"),
        @NamedQuery(name = Estado.BY_ID, query = "SELECT d FROM Estado d where d.id = :id"),
        @NamedQuery(name = Estado.BY_CODIGO, query = "SELECT d FROM Estado d where d.codigo = :codigo"),
        @NamedQuery(name = Estado.TOTAL, query = "SELECT COUNT(d) FROM Estado d")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class Estado extends BaseEntity {
    public final static String ALL = "Estado.all";
    public final static String BY_ID = "Estado.id";
    public final static String BY_CODIGO = "Estado.codigo";
    public final static String TOTAL = "Estado.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String nombre;

    private String descripcion;

}
