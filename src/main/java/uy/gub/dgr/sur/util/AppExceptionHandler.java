package uy.gub.dgr.sur.util;

/**
 * User: rmartony
 * Date: 10/01/14
 * Time: 12:19 PM
 */

import org.apache.deltaspike.security.api.authorization.AccessDeniedException;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import java.util.Iterator;

public class AppExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    public AppExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterable<ExceptionQueuedEvent> events = this.wrapped.getUnhandledExceptionQueuedEvents();
        for (Iterator<ExceptionQueuedEvent> it = events.iterator(); it.hasNext(); ) {
            ExceptionQueuedEvent event = it.next();
            ExceptionQueuedEventContext eqec = event.getContext();

            if (eqec.getException() instanceof ViewExpiredException) {
                FacesContext context = eqec.getContext();
                NavigationHandler navHandler = context.getApplication().getNavigationHandler();

                try {
                    navHandler.handleNavigation(context, null, "/app/init?faces-redirect=true&expired=true");
                } finally {
                    it.remove();
                }
            } else if (eqec.getException() instanceof AbortProcessingException && eqec.getException().getCause() instanceof AccessDeniedException) {
                FacesContext context = eqec.getContext();
                NavigationHandler navHandler = context.getApplication().getNavigationHandler();
                try {
                    navHandler.handleNavigation(context, null, "/errorAccessDenied");
                } finally {
                    it.remove();
                }
            }
        }

        this.wrapped.handle();
    }
}
