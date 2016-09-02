package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:25 PM
 */
@Entity
@EqualsAndHashCode(callSuper = true, exclude = {"observaciones", "tiltElectricoFijo"})
@Data
@NamedQueries({
        @NamedQuery(name = TipoAntena.ALL, query = "SELECT r FROM TipoAntena r order by r.modelo"),
        @NamedQuery(name = TipoAntena.BY_ID, query = "SELECT r FROM TipoAntena r where r.id = :id"),
        @NamedQuery(name = TipoAntena.EXISTS_IN_CELDA_3G, query = "SELECT c3g.nodo3G FROM Celda3G c3g where c3g.tipoAntena in (:tipoAntenaList)"),
        @NamedQuery(name = TipoAntena.EXISTS_IN_CELDA_LTE, query = "SELECT clte.nodoLte FROM CeldaLte clte where clte.tipoAntena in (:tipoAntenaList)"),
        @NamedQuery(name = TipoAntena.TOTAL, query = "SELECT COUNT(r) FROM TipoAntena r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"modelo"}))
@Cacheable
@Audited
public class TipoAntena extends BaseEntity {
    public final static String ALL = "TipoAntena.all";
    public final static String BY_ID = "TipoAntena.id";
    public final static String BY_MODELO = "TipoAntena.modelo";
    public final static String EXISTS_IN_CELDA_3G = "TipoAntena.existsInCelda3g";
    public final static String EXISTS_IN_CELDA_LTE = "TipoAntena.existsInCeldaLte";
    public final static String TOTAL = "TipoAntena.countTotal";

    @NotEmpty
    private String modelo;

    @Size(max = 255)
    private String observaciones;

    private Float tiltElectricoFijo;

}
