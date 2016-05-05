package uy.gub.dgr.sur.util;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.logging.Logger;

/**
 * User: rmartony
 * Date: 18/12/13
 * Time: 12:02 PM
 */
public class LogFactory {
    @Produces
    Logger createLogger(InjectionPoint injectionPoint) {
        return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());

    }
}
