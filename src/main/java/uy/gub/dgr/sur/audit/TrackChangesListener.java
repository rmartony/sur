package uy.gub.dgr.sur.audit;

import org.apache.deltaspike.core.api.provider.BeanManagerProvider;
import uy.gub.dgr.sur.entity.Auditoria;
import uy.gub.dgr.sur.entity.BaseEntity;
import uy.gub.dgr.sur.service.AuditoriaService;

import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.persistence.Embedded;
import javax.persistence.PostLoad;
import javax.persistence.PreUpdate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: rmartony
 * Date: 16/01/14
 * Time: 10:20 AM
 */
public class TrackChangesListener {
    private static final ThreadLocal<Boolean> insideListener = new ThreadLocal<>();
    // Injection does not work because this class is not managed by CDI
    // NOTE: JPA 2.1 will support CDI Injection in EntityListener - in Java EE 7
    private AuditoriaService auditoriaService;

    /**
     * This methos gets the {@link BeanManager} from the {@link BeanManagerProvider} and uses it to get a instance of
     * {@link AuditoriaService} {@link Stateless} EJB
     *
     * @return EJB reference
     */
    public AuditoriaService getAuditoriaServiceInstance() {
        BeanManager bm = BeanManagerProvider.getInstance().getBeanManager();
        Bean<?> bean = bm.resolve(bm.getBeans(AuditoriaService.class));
        CreationalContext cc = bm.createCreationalContext(bean);
        return (AuditoriaService) bm.getReference(bean, AuditoriaService.class, cc);
    }

    public Editor getEditorInstance() {
        BeanManager bm = BeanManagerProvider.getInstance().getBeanManager();
        Bean<?> bean = bm.resolve(bm.getBeans(Editor.class));
        CreationalContext cc = bm.createCreationalContext(bean);
        return (Editor) bm.getReference(bean, Editor.class, cc);
    }


    @PostLoad
    public void loadOldData(Object he) {
        BaseEntity be = (BaseEntity) he;
        try {
            if (!getEditorInstance().isEdited(be)) {
                return;
            }
        } catch (ContextNotActiveException e) {
            return;
        }

        Map<String, Object> oldData = new HashMap<>();

        collectOldValues(oldData, "", he);

        getEditorInstance().setOldData(be, oldData);
    }

    private void collectOldValues(Map<String, Object> oldData, String prefix, Object he) {
        List<Field> historisedFields = getHistorisedFields(he);

        for (Field field : historisedFields) {
            field.setAccessible(true);
            try {
                if (field.getAnnotation(Embedded.class) != null) {
                    Object value = field.get(he);
                    if (value != null) {
                        // recurse into the Embedded field
                        collectOldValues(oldData, prefix + field.getName() + ".", value);
                    }
                } else {
                    oldData.put(prefix + field.getName(), field.get(he));
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @PreUpdate
    public void updateChangeLog(Object he) {
        if (insideListener.get() != null) {
            insideListener.set(null);
            return;
        }
        BaseEntity be = (BaseEntity) he;
        final Editor editorInstance = getEditorInstance();
        if (!editorInstance.isEdited(be)) {
            return;
        }

        auditoriaService = getAuditoriaServiceInstance();

        Auditoria auditoria = new Auditoria();
        auditoria.setUserName(editorInstance.getUser().getLoginName());
        auditoria.setEntidad(he.getClass().getSimpleName());
        auditoria.setOperacion("update");

        boolean changed = collectChanges(auditoria, editorInstance.getOldData(be), "", he);

        // update the changeLog field if a trackable change was detected
        if (changed) {
            insideListener.set(true);
            auditoriaService.create(auditoria);
        }
    }

    private boolean collectChanges(Auditoria auditoria, Map oldData, String prefix, Object he) {
        boolean changed = false;
        List<Field> historisedFields = getHistorisedFields(he);
        for (Field field : historisedFields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            try {
                if (field.getAnnotation(Embedded.class) != null) {
                    Object value = field.get(he);
                    if (value != null) {
                        // recurse into the Embedded field
                        changed |= collectChanges(auditoria, oldData, prefix + field.getName() + ".", value);
                    }
                } else {
                    Object newValue = field.get(he);
                    Object oldValue = oldData.get(prefix + fieldName);
                    changed |= addDetail(auditoria, prefix + fieldName, oldValue, newValue);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return changed;
    }

    private boolean addDetail(Auditoria auditoria, String fieldName, Object oldValue, Object newValue) {
        if (oldValue == null && newValue == null) {
            return false;
        }
        if (oldValue != null && oldValue.equals(newValue)) {
            // no change
            return false;
        }

/*
        AuditoriaDetalle detalle = new AuditoriaDetalle();
        detalle.setAuditoria(auditoria);
        detalle.setCampo(fieldName);
        if (newValue != null) detalle.setValorNuevo(newValue.toString());
        if (oldValue != null) detalle.setValorAnterior(oldValue.toString());

        auditoria.agregarDetalle(detalle);
*/
        return true;
    }


    private List<Field> getHistorisedFields(Object he) {
        Field[] fields = he.getClass().getDeclaredFields();
        List<Field> histFields = new ArrayList<>();

        for (Field field : fields) {
            if (field.getAnnotation(TrackChanges.class) != null) {
                histFields.add(field);
            }
        }

        return histFields;
    }

}