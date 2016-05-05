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

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:22 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
        @NamedQuery(name = Ip.ALL, query = "SELECT i FROM Ip i"),
        @NamedQuery(name = Ip.BY_ID, query = "SELECT z FROM Ip z where z.id = :id"),
        @NamedQuery(name = Ip.BY_STATUS, query = "SELECT i FROM Ip i where i.ocupada = :ocupada"),
        @NamedQuery(name = Ip.BY_RNC_STATUS, query = "SELECT i FROM Ip i where i.ocupada = :ocupada and i.rnc.id = :idRnc"),
        @NamedQuery(name = Ip.TOTAL, query = "SELECT COUNT(z) FROM Ip z")})
@Audited
public class Ip extends BaseEntity implements Serializable {
    public final static String ALL = "Ip.all";
    public final static String BY_ID = "Ip.id";
    public final static String BY_STATUS = "Ip.estado";
    public final static String BY_RNC_STATUS = "Ip.rnc.estado";
    public final static String TOTAL = "Ip.countTotal";

    public final static String REGEXP_VALIDATE = "\\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\b";

    @NotNull
    //@Pattern(regexp = REGEXP_VALIDATE, message = "{msg.invalidIP}")

    private String ip;

    private boolean ocupada;
    @ManyToOne

    private Rnc rnc;

}
