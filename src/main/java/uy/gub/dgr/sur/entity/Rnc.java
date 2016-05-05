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
        @NamedQuery(name = Rnc.ALL, query = "SELECT r FROM Rnc r order by r.nombre"),
        @NamedQuery(name = Rnc.BY_ID, query = "SELECT r FROM Rnc r where r.id = :id"),
        @NamedQuery(name = Rnc.BY_NAME, query = "SELECT r FROM Rnc r where r.nombre = :nombre"),
        @NamedQuery(name = Rnc.BY_SIGLA, query = "SELECT r FROM Rnc r where r.sigla = :sigla"),
        @NamedQuery(name = Rnc.TOTAL, query = "SELECT COUNT(r) FROM Rnc r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"sigla"}))
@Audited
public class Rnc extends BaseEntity implements Serializable {
    public final static String ALL = "Rnc.all";
    public final static String BY_ID = "Rnc.id";
    public final static String BY_NAME = "Rnc.name";
    public final static String BY_SIGLA = "Rnc.sigla";
    public final static String TOTAL = "Rnc.countTotal";

    @NotEmpty

    private String sigla;
    @NotEmpty

    private String nombre;

}
