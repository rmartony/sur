package uy.gub.dgr.sur.service;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditQuery;
import uy.gub.dgr.sur.entity.Auditoria;
import uy.gub.dgr.sur.entity.Nodo3G;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author rmartony
 */

@Stateless
public class AuditoriaService extends DataAccessService<Auditoria> {

    public AuditoriaService() {
        super(Auditoria.class);
    }

    public List<Object> queryAudit() {
        AuditReader reader = AuditReaderFactory.get(getEntityManager());
        AuditQuery query = reader.createQuery().forRevisionsOfEntity(Nodo3G.class, true, true);
        return query.getResultList();

    }
}
