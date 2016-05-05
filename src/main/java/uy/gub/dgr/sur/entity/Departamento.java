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
 * Time: 03:22 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
        @NamedQuery(name = Departamento.ALL, query = "SELECT d FROM Departamento d order by d.nombre"),
        @NamedQuery(name = Departamento.BY_ID, query = "SELECT d FROM Departamento d where d.id = :id"),
        @NamedQuery(name = Departamento.BY_NAME, query = "SELECT d FROM Departamento d where d.nombre = :nombre"),
        @NamedQuery(name = Departamento.TOTAL, query = "SELECT COUNT(d) FROM Departamento d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nombre"}))
@Audited
public class Departamento extends BaseEntity implements Serializable {
    public final static String ALL = "Departamento.all";
    public final static String BY_ID = "Departamento.id";
    public final static String BY_NAME = "Departamento.name";
    public final static String TOTAL = "Departamento.countTotal";

    @NotEmpty

    private String nombre;

}
