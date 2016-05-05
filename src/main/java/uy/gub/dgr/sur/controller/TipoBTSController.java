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
import uy.gub.dgr.sur.entity.TipoBTS;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.model.LazyTipoBTSDataModel;
import uy.gub.dgr.sur.service.TipoBTSService;
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
public class TipoBTSController extends BaseController {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient TipoBTSService das;
    // Selected users that will be removed
    @Getter
    @Setter
    private TipoBTS[] selectedItems;
    // Creating new zona
    @Getter
    @Setter
    private TipoBTS newItem = new TipoBTS();
    // Selected user that will be updated
    @Getter
    @Setter
    private TipoBTS selectedItem = new TipoBTS();
    // Lazy loading user list
    @Getter
    private LazyDataModel<TipoBTS> lazyModel;

    /**
     * Default constructor
     */
    public TipoBTSController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "TipoBTSController is initializing");
        lazyModel = new LazyTipoBTSDataModel(das);
    }

    /**
     * Create, Update and Delete operations
     */
    @Admin
    public void doCreate() {
        setMode(ControllerMode.CREATE);
        das.create(newItem);
        newItem = new TipoBTS();
    }

    /**
     * @param actionEvent
     */
    @Admin
    public void doConfirmUpdate() {
        das.update(selectedItem);
        Messages.addFlashInfo(null, "Tipo BTS actualizado con éxito.");
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

    private boolean isSafe2Delete(TipoBTS[] selectedItems) {
        List<TipoBTS> tipoBTSList = Arrays.asList(selectedItems);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tipoBTSList", tipoBTSList);
        List<String> nodos = das.findWithNamedQuery(TipoBTS.EXISTS_IN_NODO, parameters, 50);
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