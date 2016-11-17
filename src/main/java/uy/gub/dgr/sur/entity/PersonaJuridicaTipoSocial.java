package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
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
@EqualsAndHashCode(callSuper = true, exclude = {"descripcion"})
@NamedQueries({
        @NamedQuery(name = PersonaJuridicaTipoSocial.ALL, query = "SELECT d FROM PersonaJuridicaTipoSocial d order by d.descripcion"),
        @NamedQuery(name = PersonaJuridicaTipoSocial.BY_ID, query = "SELECT d FROM PersonaJuridicaTipoSocial d where d.id = :id"),
        @NamedQuery(name = PersonaJuridicaTipoSocial.BY_CODIGO, query = "SELECT s FROM PersonaJuridicaTipoSocial s where s.codigo = :codigo"),
        @NamedQuery(name = PersonaJuridicaTipoSocial.BY_DESCRIPCION, query = "SELECT d FROM PersonaJuridicaTipoSocial d where d.descripcion = :descripcion"),
        @NamedQuery(name = PersonaJuridicaTipoSocial.TOTAL, query = "SELECT COUNT(d) FROM PersonaJuridicaTipoSocial d")})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"codigo"}))
@Audited
@SQLDelete(sql = "update PersonaJuridicaTipoSocial SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class PersonaJuridicaTipoSocial extends BaseEntity {
    public final static String ALL = "PersonaJuridicaTipoSocial.all";
    public final static String BY_ID = "PersonaJuridicaTipoSocial.id";
    public final static String BY_CODIGO = "PersonaJuridicaTipoSocial.codigo";
    public final static String BY_DESCRIPCION = "PersonaJuridicaTipoSocial.descripcion";
    public final static String TOTAL = "PersonaJuridicaTipoSocial.countTotal";

    @NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

}
