package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
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
@EqualsAndHashCode(callSuper = true, exclude = "descripcion")
@NamedQueries({
        @NamedQuery(name = Registro.ALL, query = "SELECT r FROM Registro r order by r.descripcion"),
        @NamedQuery(name = Registro.BY_ID, query = "SELECT r FROM Registro r where r.id = :id"),
        @NamedQuery(name = Registro.BY_CODIGO, query = "SELECT r FROM Registro r where r.codigo = :codigo"),
        @NamedQuery(name = Registro.BY_DESCRIPCION, query = "SELECT r FROM Registro r where r.descripcion = :descripcion"),
        @NamedQuery(name = Registro.TOTAL, query = "SELECT COUNT(r) FROM Registro r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
@SQLDelete(sql = "update Registro SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class Registro extends BaseEntity {
    public final static String ALL = "Registro.all";
    public final static String BY_ID = "Registro.id";
    public final static String BY_DESCRIPCION = "Registro.descripcion";
    public final static String BY_CODIGO = "Registro.codigo";
    public final static String TOTAL = "Registro.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
