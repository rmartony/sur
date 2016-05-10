package uy.gub.dgr.sur.idm;

import org.picketlink.config.SecurityConfigurationBuilder;
import org.picketlink.event.SecurityConfigurationEvent;

import javax.enterprise.event.Observes;

/**
 * <p>A simple CDI observer for the {@link org.picketlink.event.SecurityConfigurationEvent}.</p>
 * <p>
 * <p>The event is fired during application startup and allows you to provide any configuration to PicketLink
 * before it is initialized.</p>
 * <p>
 * <p>All the configuration related with Http Security is provided from this bean.</p>
 * <p>
 * Created by rmartony on 09/05/2016.
 */
public class HttpSecurityConfiguration {

    public void onInit(@Observes SecurityConfigurationEvent event) {
        SecurityConfigurationBuilder builder = event.getBuilder();

        builder
                .http()
                .forPath("/app/*")
                .authenticateWith()
                .form()
                .authenticationUri("/app/login.xhtml")
                .loginPage("/app/login.xhtml")
                .errorPage("/app/login.xhtml")
                .restoreOriginalRequest()
                .forPath("/logout")
                .logout()
                .redirectTo("/index.html");
    }

}