package uy.gub.dgr.sur.util;

import org.apache.deltaspike.security.api.authorization.AccessDeniedException;
import org.hibernate.exception.ConstraintViolationException;
import org.omnifaces.util.Messages;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: rmartony
 * Date: 19/02/14
 * Time: 06:04 PM
 */
public class ConstraintViolationInterceptor implements Serializable {
    @Inject
    transient Logger log;

    @AroundInvoke
    public Object monitorearErrores(InvocationContext ic) throws Exception {
        if (ic.getMethod().getName().startsWith("do")) {
            try {
                return ic.proceed();
            } catch (EJBException e) {
                log.log(Level.SEVERE, null, e);

                Throwable t = e.getCause();
                while ((t != null) && !(t instanceof ConstraintViolationException)) {
                    t = t.getCause();
                }
                if (t instanceof ConstraintViolationException) {
                    Messages.addFlashGlobalError("Error de integridad de base de datos. Verifique no exista previamente el ítem ingresado, si está eliminando un ítem verifique que no exista una referencia a este.");
                    return null;
                } else {
                    Messages.addFlashGlobalError("Error en persistencia de datos.");
                    throw e;
                }

            } catch (AccessDeniedException e) {
                Messages.addGlobalError("Acceso denegado para la acción realizada.");
                throw e;
            }
        }
        return ic.proceed();
    }
}
