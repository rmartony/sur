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
import uy.gub.dgr.sur.entity.Nodo3G;
import uy.gub.dgr.sur.entity.TipoFeeder;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.model.LazyTipoFeederDataModel;
import uy.gub.dgr.sur.service.TipoFeederService;
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
public class TipoFeederController extends BaseController {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient TipoFeederService das;
    // Selected users that will be removed
    @Getter
    @Setter
    private TipoFeeder[] selectedItems;
    // Creating new zona
    @Getter
    @Setter
    private TipoFeeder newItem = new TipoFeeder();
    // Selected user that will be updated
    @Getter
    @Setter
    private TipoFeeder selectedItem = new TipoFeeder();
    // Lazy loading user list
    @Getter
    private LazyDataModel<TipoFeeder> lazyModel;

    /**
     * Default constructor
     */
    public TipoFeederController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "TipoFeederController is initializing");
        lazyModel = new LazyTipoFeederDataModel(das);
    }

    /**
     * Create, Update and Delete operations
     */
    @Admin
    public void doCreate() {
        setMode(ControllerMode.CREATE);
        das.create(newItem);
        newItem = new TipoFeeder();
    }

    /**
     * @param actionEvent
     */
    @Admin
    public void doConfirmUpdate() {
        das.update(selectedItem);
        Messages.addFlashInfo(null, "Tipo feeder actualizado con éxito.");
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
                Messages.addInfo(null, "Tipos feeder(s) eliminado(s) con éxito.");
            }
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

    private boolean isSafe2Delete(TipoFeeder[] selectedItems) {
        List<TipoFeeder> tipoFeederList = Arrays.asList(selectedItems);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("tipoFeederList", tipoFeederList);
        List<Nodo3G> nodo3GList = das.findWithNamedQuery(TipoFeeder.EXISTS_IN_CELDA, parameters, 50);
        if (CollectionUtils.isNotEmpty(nodo3GList)) {
            StringBuilder builder = new StringBuilder();
            for (Nodo3G nodo3g : nodo3GList) {
                builder.append(nodo3g.getSigla()).append(", ");
            }
            Messages.addError(null, "Los siguientes nodos 3G contienen celdas que tienen asociado el tipo feeder que se quiere eliminar: " + builder.toString());
            return false;
        } else {
            return true;
        }
    }

}