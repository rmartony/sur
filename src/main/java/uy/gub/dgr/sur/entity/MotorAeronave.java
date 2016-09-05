package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@NamedQueries({
        @NamedQuery(name = MotorAeronave.ALL, query = "SELECT r FROM MotorAeronave r"),
        @NamedQuery(name = MotorAeronave.BY_ID, query = "SELECT r FROM MotorAeronave r where r.id = :id"),
        @NamedQuery(name = MotorAeronave.TOTAL, query = "SELECT COUNT(r) FROM MotorAeronave r")})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"serie", "marca"}))
@Audited
public class MotorAeronave extends BaseEntity {
    public final static String ALL = "MotorAeronave.all";
    public final static String BY_ID = "MotorAeronave.id";
    public final static String TOTAL = "MotorAeronave.countTotal";

    @NotEmpty
    private String serie;

    @NotEmpty
    private String marca;

}
