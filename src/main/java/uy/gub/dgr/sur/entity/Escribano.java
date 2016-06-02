package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:22 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
        @NamedQuery(name = Escribano.ALL, query = "SELECT i FROM Escribano i"),
        @NamedQuery(name = Escribano.BY_ID, query = "SELECT z FROM Escribano z where z.id = :id"),
        @NamedQuery(name = Escribano.BY_STATUS, query = "SELECT i FROM Escribano i where not current_date between :inhabilitadoFechaDesde and :inhabilitadoFechaHasta"),
        @NamedQuery(name = Escribano.BY_RNC_STATUS, query = "SELECT i FROM Escribano i where i.ocupada = :ocupada and i.rnc.id = :idRnc"),
        @NamedQuery(name = Escribano.TOTAL, query = "SELECT COUNT(z) FROM Escribano z")})
@Audited
public class Escribano extends BaseEntity implements Serializable {
    public final static String ALL = "Escribano.all";
    public final static String BY_ID = "Escribano.id";
    public final static String BY_STATUS = "Escribano.estado";
    public final static String BY_RNC_STATUS = "Escribano.rnc.estado";
    public final static String TOTAL = "Escribano.countTotal";

    public final static String REGEXP_VALIDATE = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";

    @NotNull
    //@Pattern(regexp = REGEXP_VALIDATE, message = "{msg.invalidIP}")
    private long codigo;
    private String nombre;

    private boolean ocupada;

    private Date inhabilitadoFechaDesde;
    private Date inhabilitadoFechaHasta;

    @ManyToOne
    private Rnc rnc;

}
