package uy.gub.dgr.sur.controller;

/**
 * User: rmartony
 * Date: 19/12/13
 * Time: 04:53 PM
 */

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import org.primefaces.model.LazyDataModel;
import uy.gub.dgr.sur.entity.Celda3G;
import uy.gub.dgr.sur.entity.TipoAntena;
import uy.gub.dgr.sur.entity.TipoFeeder;
import uy.gub.dgr.sur.event.CeldaEvent;
import uy.gub.dgr.sur.service.Celda3GService;
import uy.gub.dgr.sur.util.ConstraintViolationInterceptor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
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
@Interceptors(value = ConstraintViolationInterceptor.class)
public class Celda3GController extends BaseController {
    @Inject
    Event<CeldaEvent> event;
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient Celda3GService das;
    // Selected celda that will be removed
    @Getter
    @Setter
    private Celda3G[] selectedItems;
    // Create or update celda
    @Getter
    @Setter
    private Celda3G item = new Celda3G();
    @Getter
    private List<TipoAntena> tipoAntenaList;
    @Getter
    private List<TipoFeeder> tipoFeederList;
    // Lazy loading user list
    @Getter
    private LazyDataModel<Celda3G> lazyModel;

    /**
     * Default constructor
     */
    public Celda3GController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "Celda3GController is initializing");
        tipoAntenaList = das.findWithNamedQuery(TipoAntena.ALL);
        tipoFeederList = das.findWithNamedQuery(TipoFeeder.ALL);
        //lazyModel = new LazyCelda3GDataModel(das);
    }

    /**
     * Create, Update and Delete operations
     */
    public String doCreate() {
        if (getBackOutcome() == null) {
            das.create(item);
        }
        event.fire(new CeldaEvent(item));
        item = new Celda3G();
        return resolveNavegation();
    }

    public String doConfirmUpdate() {
        if (getBackOutcome() == null && getMode() == ControllerMode.UPDATE) {
            das.update(item);
        }
        event.fire(new CeldaEvent(item));
        return resolveNavegation();
    }

    public String doUpdate() {
        if (selectedItems != null && selectedItems.length > 0) {
            item = selectedItems[0];
            return "update";
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
        return null;
    }

    public void doDelete() {
        if (selectedItems != null && selectedItems.length > 0) {
            das.deleteItems(selectedItems);
            Messages.addInfo(null, "Nodo(s) eliminado(s) con éxito.");
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

}