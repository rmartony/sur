package uy.gub.dgr.sur.util;

/**
 * User: rmartony
 * Date: 10/01/14
 * Time: 12:17 PM
 */

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;


public class AppExceptionHandlerFactory extends ExceptionHandlerFactory {

    private javax.faces.context.ExceptionHandlerFactory base;

    public AppExceptionHandlerFactory(ExceptionHandlerFactory base) {
        this.base = base;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new AppExceptionHandler(base.getExceptionHandler());
    }

}
