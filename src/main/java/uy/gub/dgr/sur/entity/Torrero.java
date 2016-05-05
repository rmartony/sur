package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
        @NamedQuery(name = Torrero.ALL, query = "SELECT r FROM Torrero r order by r.nombre"),
        @NamedQuery(name = Torrero.BY_ID, query = "SELECT r FROM Torrero r where r.id = :id"),
        @NamedQuery(name = Torrero.EXISTS_IN_PREVENTIVO, query = "SELECT p FROM Preventivo p where :torreroList member of p.torreros"),
        @NamedQuery(name = Torrero.TOTAL, query = "SELECT COUNT(r) FROM Torrero r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"nombre"}))
@Audited
public class Torrero extends BaseEntity implements Serializable {
    public final static String ALL = "Torrero.all";
    public final static String BY_ID = "Torrero.id";
    public final static String EXISTS_IN_PREVENTIVO = "Torrero.existsInUser";
    public final static String TOTAL = "Torrero.countTotal";
    @NotEmpty
    @Size(min = 2, max = 100)
    @Getter
    @Setter
    private String nombre;

}
