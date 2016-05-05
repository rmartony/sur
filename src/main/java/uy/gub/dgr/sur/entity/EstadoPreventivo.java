package uy.gub.dgr.sur.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

/**
 * User: rmartony
 * Date: 13/12/13
 * Time: 05:51 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"nombre", "descripcion"})
@NamedQueries({
        @NamedQuery(name = EstadoPreventivo.ALL, query = "SELECT d FROM EstadoPreventivo d order by d.nombre"),
        @NamedQuery(name = EstadoPreventivo.BY_ID, query = "SELECT d FROM EstadoPreventivo d where d.id = :id"),
        @NamedQuery(name = EstadoPreventivo.TOTAL, query = "SELECT COUNT(d) FROM EstadoPreventivo d")})
@Audited
public class EstadoPreventivo extends BaseEntity implements Serializable {
    public final static String ALL = "EstadoPreventivo.all";
    public final static String BY_ID = "EstadoPreventivo.id";
    public final static String TOTAL = "EstadoPreventivo.countTotal";
    public static final Integer BIEN = 1;
    public static final Integer MAL = 2;
    public static final Integer REPARADO = 3;
    private String nombre;
    private String descripcion;
    private String sigla;

}
