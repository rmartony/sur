package uy.gub.dgr.sur.entity;//

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * User: rmartony
 * Date: 29/11/13
 * Time: 06:34 PM
 */

@Entity
@DiscriminatorValue("3G")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"celdas", "fechaAltaEthernet", "defaultGateway"})
@Audited
public class Nodo3G extends Nodo {
/*
    @ManyToOne(cascade = CascadeType.MERGE)
    private Escribano ip;

    @ManyToOne
    private Rnc rnc;
*/

    @Temporal(TemporalType.DATE)
    private Date fechaAltaEthernet;

    //@Pattern(regexp = Escribano.REGEXP_VALIDATE, message = "{msg.invalidIP}")
    private String defaultGateway;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "nodo3G")
    @OrderBy("idCelda ASC")
    private Set<Celda3G> celdas;


}
