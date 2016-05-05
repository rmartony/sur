package uy.gub.dgr.sur.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * User: rafa
 * Date: 13/02/14
 * Time: 12:22 AM
 */
@WebListener
public class ConfigListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        System.setProperty("org.apache.el.parser.COERCE_TO_ZERO", "false");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // NOOP
    }

}
