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
        @NamedQuery(name = Registro.ALL, query = "SELECT r FROM Registro r order by r.nombre"),
        @NamedQuery(name = Registro.BY_ID, query = "SELECT r FROM Registro r where r.id = :id"),
        @NamedQuery(name = Registro.BY_CODIGO, query = "SELECT r FROM Registro r where r.codigo = :codigo"),
        @NamedQuery(name = Registro.BY_NAME, query = "SELECT r FROM Registro r where r.descripcion = :descripcion"),
        @NamedQuery(name = Registro.TOTAL, query = "SELECT COUNT(r) FROM Registro r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class Registro extends BaseEntity implements Serializable {
    public final static String ALL = "Registro.all";
    public final static String BY_ID = "Registro.id";
    public final static String BY_NAME = "Registro.name";
    public final static String BY_CODIGO = "Registro.codigo";
    public final static String TOTAL = "Registro.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
