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
import uy.gub.dgr.sur.entity.Nodo;
import uy.gub.dgr.sur.entity.TipoAntena;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.model.LazyTipoAntenaDataModel;
import uy.gub.dgr.sur.service.TipoAntenaService;
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
public class TipoAntenaController extends BaseController {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient TipoAntenaService das;
    // Selected users that will be removed
    @Getter
    @Setter
    private TipoAntena[] selectedItems;
    // Creating new zona
    @Getter
    @Setter
    private TipoAntena newItem = new TipoAntena();
    // Selected user that will be updated
    @Getter
    @Setter
    private TipoAntena selectedItem = new TipoAntena();
    // Lazy loading user list
    @Getter
    private LazyDataModel<TipoAntena> lazyModel;

    /**
     * Default constructor
     */
    public TipoAntenaController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "TipoAntenaController is initializing");
        lazyModel = new LazyTipoAntenaDataModel(das);
    }

    /**
     * Create, Update and Delete operations
     */
    @Admin
    public void doCreate() {
        das.create(newItem);
        newItem = new TipoAntena();
    }

    /**
     * @param actionEvent
     */
    @Admin
    public void doConfirmUpdate() {
        das.update(selectedItem);
        Messages.addFlashInfo(null, "Tipo antena actualizada con éxito.");
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
                Messages.addInfo(null, "Tipos antena(s) eliminada(s) con éxito.");
            }
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

    private boolean isSafe2Delete(TipoAntena[] selectedItems) {
        List<TipoAntena> intervinienteList = Arrays.asList(selectedItems);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tipoAntenaList", intervinienteList);
        List<Nodo> nodos = das.findWithNamedQuery(TipoAntena.EXISTS_IN_CELDA_3G, parameters, 50);
        if (CollectionUtils.isNotEmpty(nodos)) {
            StringBuilder builder = new StringBuilder();
            for (Nodo nodo : nodos) {
                builder.append(nodo.getSigla()).append(", ");
            }
            Messages.addError(null, "Las siguientes celdas 3G tienen asociado el tipo de antena que se quiere eliminar: " + builder.toString());
            return false;
        } else {
            nodos = das.findWithNamedQuery(TipoAntena.EXISTS_IN_CELDA_LTE, parameters, 50);
            if (CollectionUtils.isNotEmpty(nodos)) {
                StringBuilder builder = new StringBuilder();
                for (Nodo nodo : nodos) {
                    builder.append(nodo.getSigla()).append(", ");
                }
                Messages.addError(null, "Las siguientes celdas LTE tienen asociado el tipo de antena que se quiere eliminar: " + builder.toString());
                return false;
            } else {
                return true;
            }
        }

    }

}