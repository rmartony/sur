package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;

/**
 * User: rmartony
 * Date: 29/11/13
 * Time: 06:34 PM
 */

@Entity
@DiscriminatorValue("LTE")
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"celdas", "mme", "ePipe", "ipPTP", "maskPTP", "nServicio"})
@Audited
public class NodoLte extends Nodo {

    private String mme;

    private String ePipe;

    private String ipPTP;

    private String maskPTP;

    private String nServicio;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "nodoLte")
    @OrderBy("idCelda ASC")
    private Set<CeldaLte> celdas;

}
