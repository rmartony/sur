package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:25 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"atenuacion"})
@NamedQueries({
        @NamedQuery(name = TipoFeeder.ALL, query = "SELECT r FROM TipoFeeder r order by r.modelo"),
        @NamedQuery(name = TipoFeeder.BY_ID, query = "SELECT r FROM TipoFeeder r where r.id = :id"),
        @NamedQuery(name = TipoFeeder.EXISTS_IN_CELDA, query = "SELECT c3g.nodo3G FROM Celda3G c3g where c3g.tipoFeeder in (:tipoFeederList)"),
        @NamedQuery(name = TipoFeeder.TOTAL, query = "SELECT COUNT(r) FROM TipoFeeder r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"modelo"}))
@Cacheable
@Audited
public class TipoFeeder extends BaseEntity {
    public final static String ALL = "TipoFeeder.all";
    public final static String BY_ID = "TipoFeeder.id";
    public final static String EXISTS_IN_CELDA = "TipoFeeder.existsInCelda";
    public final static String TOTAL = "TipoFeeder.countTotal";

    @NotEmpty

    private String modelo;

    private Float atenuacion;

}
