package uy.gub.dgr.sur.controller;

/**
 * User: rmartony
 * Date: 19/12/13
 * Time: 04:53 PM
 */

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import uy.gub.dgr.sur.entity.Rnc;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.model.LazyRncDataModel;
import uy.gub.dgr.sur.service.RncService;
import uy.gub.dgr.sur.util.ConstraintViolationInterceptor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
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
public class RncController extends BaseController {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient RncService das;
    // Selected users that will be removed
    @Getter
    @Setter
    private Rnc[] selectedItems;
    // Creating new zona
    @Getter
    @Setter
    private Rnc newItem = new Rnc();
    // Selected user that will be updated
    @Getter
    @Setter
    private Rnc selectedItem = new Rnc();
    // Lazy loading user list
    @Getter
    private LazyDataModel<Rnc> lazyModel;

    /**
     * Default constructor
     */
    public RncController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "RncController is initializing");
        lazyModel = new LazyRncDataModel(das);
    }

    /**
     * Create, Update and Delete operations
     */
    @Admin
    public void doCreate() {
        das.create(newItem);
        newItem = new Rnc();
    }

    /**
     * @param actionEvent
     */
    @Admin
    public void doConfirmUpdate() {
        das.update(selectedItem);
        Messages.addFlashInfo(null, "Rnc actualizada con éxito.");
    }

    public void doUpdate() {
        setMode(ControllerMode.UPDATE);
        if (selectedItems != null && selectedItems.length > 0) {
            selectedItem = selectedItems[0];
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.addCallbackParam("validationFailed", true);
        }
    }

    /**
     * @param actionEvent
     */
    @Admin
    public void doDelete() {
        if (selectedItems != null && selectedItems.length > 0) {
            das.deleteItems(selectedItems);
            Messages.addInfo(null, "Rnc(s) eliminada(s) con éxito.");
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

}