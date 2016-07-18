package uy.gub.dgr.sur.controller;

/**
 * User: rmartony
 * Date: 19/12/13
 * Time: 04:53 PM
 */

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import uy.gub.dgr.sur.entity.Interviniente;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.model.LazyIntervinienteDataModel;
import uy.gub.dgr.sur.service.IntervinienteService;
import uy.gub.dgr.sur.util.ConstraintViolationInterceptor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.util.*;
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
public class IntervinienteController extends BaseController {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient IntervinienteService das;
    // Selected users that will be removed
    @Getter
    @Setter
    private Interviniente[] selectedItems;
    // Creating new zona
    @Getter
    @Setter
    private Interviniente newItem = new Interviniente();
    // Selected user that will be updated
    @Getter
    @Setter
    private Interviniente selectedItem = new Interviniente();
    // Lazy loading user list
    @Getter
    private LazyDataModel<Interviniente> lazyModel;

    /**
     * Default constructor
     */
    public IntervinienteController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "IntervinienteController is initializing");
        lazyModel = new LazyIntervinienteDataModel(das);
    }

    /**
     * Create, Update and Delete operations
     */
    @Admin
    public void doCreate() {
        setMode(ControllerMode.CREATE);
        das.create(newItem);
        newItem = new Interviniente();
    }

    /**
     * @param actionEvent
     */
    @Admin
    public void doConfirmUpdate() {
        das.update(selectedItem);
        Messages.addFlashInfo(null, "Interviniente actualizado con éxito.");
    }

    public void doUpdate() {
        setMode(ControllerMode.UPDATE);
        validateSelection();
    }

    private void validateSelection() {
        if (selectedItem != null || selectedItems != null && selectedItems.length > 0) {
            if (selectedItems != null && selectedItem == null) {
                selectedItem = selectedItems[0];
            }
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.addCallbackParam("validationFailed", true);
        }
    }

    public String doView() {
        setMode(ControllerMode.VIEW);
        validateSelection();
        return null;
    }

    /**
     * @param actionEvent
     */
    @Admin
    public void doDelete() {
        if (selectedItems != null && selectedItems.length > 0) {
            if (isSafe2Delete(selectedItems)) {
                das.deleteItems(selectedItems);
                Messages.addInfo(null, "Tipos BTS(s) eliminada(s) con éxito.");
            }
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

    private boolean isSafe2Delete(Interviniente[] selectedItems) {
        List<Interviniente> intervinienteList = Arrays.asList(selectedItems);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("intervinienteList", intervinienteList);
        List<String> nodos = das.findWithNamedQuery(Interviniente.EXISTS_IN_NODO, parameters, 50);
        if (CollectionUtils.isNotEmpty(nodos)) {
            StringBuilder builder = new StringBuilder();
            for (String nodo : nodos) {
                builder.append(nodo).append(", ");
            }
            Messages.addError(null, "Los siguientes nodos tienen asociado el tipo BTS que se quiere eliminar: " + builder.toString());
            return false;
        } else {
            return true;
        }
    }

}