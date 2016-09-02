package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * User: rmartony
 * Date: 12/02/14
 * Time: 09:33 AM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"nodo", "preventivo"})
@NamedQueries({
        @NamedQuery(name = UltimoPreventivo.BY_NODO_ID, query = "SELECT u FROM UltimoPreventivo u where u.nodo.id = :idNodo"),
        @NamedQuery(name = UltimoPreventivo.BY_PREVENTIVO_ID, query = "SELECT u FROM UltimoPreventivo u where u.preventivo.id = :idPreventivo")
})
public class UltimoPreventivo extends BaseEntity {
    public final static String BY_NODO_ID = "UltimoPreventivo.nodo.id";
    public final static String BY_PREVENTIVO_ID = "UltimoPreventivo.preventivo.id";
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Nodo nodo;
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Preventivo preventivo;
}
