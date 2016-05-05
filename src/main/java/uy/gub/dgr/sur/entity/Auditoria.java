package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.DefaultTrackingModifiedEntitiesRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import uy.gub.dgr.sur.audit.AppRevisionListener;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
        @NamedQuery(name = Auditoria.ALL, query = "SELECT r FROM Auditoria r"),
        @NamedQuery(name = Auditoria.BY_ID, query = "SELECT r FROM Auditoria r where r.id = :id"),
        @NamedQuery(name = Auditoria.TOTAL, query = "SELECT COUNT(r) FROM Auditoria r")})
@RevisionEntity(AppRevisionListener.class)
public class Auditoria extends DefaultTrackingModifiedEntitiesRevisionEntity implements Serializable {
    public final static String ALL = "Auditoria.all";
    public final static String BY_ID = "Auditoria.id";
    public final static String TOTAL = "Auditoria.countTotal";

    private String userName;
    private String entidad;
    private String operacion;


}
