package uy.gub.dgr.sur.controller;

import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: rmartony
 * Date: 17/12/13
 * Time: 06:39 PM
 */
@Named
@SessionScoped
public class LocaleController implements Serializable {
    private Locale locale = new Locale.Builder().setLanguage("es").build();

    private
    @Inject
    transient Logger log;

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void setLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

    @PostConstruct
    public void init() {
        try {
            locale = Faces.getLocale();
        } catch (Exception e) {
            log.log(Level.WARNING, "Error al obtener Faces.getLocale()" + e.getMessage(), e);
        }
    }
}