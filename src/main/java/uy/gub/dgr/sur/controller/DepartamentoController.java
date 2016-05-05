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
import uy.gub.dgr.sur.entity.Departamento;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.model.LazyDepartamentoDataModel;
import uy.gub.dgr.sur.service.DepartamentoService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
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
public class DepartamentoController implements Serializable {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient DepartamentoService das;
    // Selected users that will be removed
    @Getter
    @Setter
    private Departamento[] selectedItems;
    // Creating new zona
    @Getter
    @Setter
    private Departamento newItem = new Departamento();
    // Selected user that will be updated
    @Getter
    @Setter
    private Departamento selectedItem = new Departamento();
    // Lazy loading user list
    @Getter
    private LazyDataModel<Departamento> lazyModel;

    /**
     * Default constructor
     */
    public DepartamentoController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "DepartamentoController is initializing");
        lazyModel = new LazyDepartamentoDataModel(das);
    }

    /**
     * Create, Update and Delete operations
     */
    @Admin
    public void doCreate() {
        das.create(newItem);
        newItem = new Departamento();
    }

    /**
     * @param actionEvent
     */
    @Admin
    public void doConfirmUpdate() {
        das.update(selectedItem);
        Messages.addFlashInfo(null, "Departamento actualizado con éxito.");
    }

    public void doUpdate() {
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
            Messages.addInfo(null, "Departamento(s) eliminada(s) con éxito.");
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

}