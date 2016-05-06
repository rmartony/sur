package uy.gub.dgr.sur.util;

/**
 * User: rmartony
 * Date: 16/12/13
 * Time: 07:09 PM
 */

import org.picketlink.annotations.PicketLink;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * This class uses CDI to alias Java EE resources, such as the {@link FacesContext}, to CDI beans
 * <p/>
 * <p>
 * Example injection on a managed bean field:
 * </p>
 * <p>
 * <pre>
 * &#064;Inject
 * private FacesContext facesContext;
 * </pre>
 */
@Stateless
public class Resources {

    @Resource(mappedName = "java:jboss/datasources/surDS")
    DataSource datasource;
    @PersistenceContext(unitName = "sur")
    private EntityManager em;

    @Produces
    public DataSource getDatasource() {
        return datasource;
    }

    @Produces
    public EntityManager getEntityManager() {
        return em;
    }

    /*
     * Since we are using JPAIdentityStore to store identity-related data, we must provide it with an EntityManager via a
     * producer method or field annotated with the @PicketLink qualifier.
     */
    @Produces
    @PicketLink
    public EntityManager getPicketLinkEntityManager() {
        return em;
    }

    @Produces
    @RequestScoped
    public FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    @Produces
    public ResourceBundle getResourceBundle() {
        FacesContext facesContext = produceFacesContext();
        if (facesContext == null) {
            return ResourceBundle.getBundle("uy.gub.dgr.sur.util.messages", Locale.getDefault());
        } else {
            return ResourceBundle.getBundle("uy.gub.dgr.sur.util.messages", facesContext.getViewRoot().getLocale());
        }
    }

    @Produces
    @Named
    public TimeZone applicationTimezone() {
        return TimeZone.getDefault();
    }

}
