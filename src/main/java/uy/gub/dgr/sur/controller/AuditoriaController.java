package uy.gub.dgr.sur.controller;

/**
 * User: rmartony
 * Date: 19/12/13
 * Time: 04:53 PM
 */

import lombok.Getter;
import org.primefaces.model.LazyDataModel;
import uy.gub.dgr.sur.service.AuditoriaService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * User Controller class allows users to do CRUD operations
 *
 * @author rmartony
 */

@Named
@SessionScoped
public class AuditoriaController implements Serializable {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient AuditoriaService das;
    // Lazy loading list
    @Getter
    private LazyDataModel<Object> lazyModel;

    /**
     * Default constructor
     */
    public AuditoriaController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "AuditoriaController is initializing");
        //lazyModel = new LazySitioDataModel(das);
    }

    public void queryAudit() {
        List<Object> historyLog = das.queryAudit();
        log.info("Query audit");
    }
}