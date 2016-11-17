package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:22 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"nombre", "inhabilitadoFechaDesde", "inhabilitadoFechaHasta"})
@NamedQueries({
        @NamedQuery(name = Escribano.ALL, query = "SELECT i FROM Escribano i"),
        @NamedQuery(name = Escribano.BY_ID, query = "SELECT z FROM Escribano z where z.id = :id"),
        @NamedQuery(name = Escribano.BY_STATUS, query = "SELECT e FROM Escribano e where not current_date between e.inhabilitadoFechaDesde and e.inhabilitadoFechaHasta"),
        @NamedQuery(name = Escribano.BY_CODIGO, query = "SELECT i FROM Escribano i where i.codigo = :codigo"),
        @NamedQuery(name = Escribano.TOTAL, query = "SELECT COUNT(z) FROM Escribano z")})
@Audited
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@SQLDelete(sql = "update Escribano SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class Escribano extends BaseEntity {
    public final static String ALL = "Escribano.all";
    public final static String BY_ID = "Escribano.id";
    public final static String BY_STATUS = "Escribano.estado";
    public final static String BY_CODIGO = "Escribano.codigo";
    public final static String TOTAL = "Escribano.countTotal";

    //@Pattern(regexp = REGEXP_IP_VALIDATE, message = "{msg.invalidIP}")
    @NotNull
    private Long codigo;
    @NotEmpty
    private String nombre;

    private Date inhabilitadoFechaDesde;
    private Date inhabilitadoFechaHasta;

}
