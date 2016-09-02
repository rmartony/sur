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
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
        @NamedQuery(name = TipoDocumento.ALL, query = "SELECT r FROM TipoDocumento r order by r.descripcion"),
        @NamedQuery(name = TipoDocumento.BY_ID, query = "SELECT r FROM TipoDocumento r where r.id = :id"),
        @NamedQuery(name = TipoDocumento.BY_CODIGO, query = "SELECT r FROM TipoDocumento r where r.codigo = :codigo"),
        @NamedQuery(name = TipoDocumento.BY_DESCRICPCION, query = "SELECT r FROM TipoDocumento r where r.descripcion = :descripcion"),
        @NamedQuery(name = TipoDocumento.TOTAL, query = "SELECT COUNT(r) FROM TipoDocumento r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
public class TipoDocumento extends BaseEntity {
    public final static String ALL = "TipoDocumento.all";
    public final static String BY_ID = "TipoDocumento.id";
    public final static String BY_DESCRICPCION = "TipoDocumento.descripcion";
    public final static String BY_CODIGO = "TipoDocumento.codigo";
    public final static String TOTAL = "TipoDocumento.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
