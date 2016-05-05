package uy.gub.dgr.sur.controller;

/**
 * User: rmartony
 * Date: 19/12/13
 * Time: 04:53 PM
 */

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import uy.gub.dgr.sur.entity.Preventivo;
import uy.gub.dgr.sur.entity.Torrero;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.model.LazyTorreroDataModel;
import uy.gub.dgr.sur.service.TorreroService;
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
public class TorreroController extends BaseController {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient TorreroService das;
    // Selected users that will be removed
    @Getter
    @Setter
    private Torrero[] selectedItems;
    // Creating new zona
    @Getter
    @Setter
    private Torrero newItem = new Torrero();
    // Selected user that will be updated
    @Getter
    @Setter
    private Torrero selectedItem = new Torrero();
    // Lazy loading user list
    @Getter
    private LazyDataModel<Torrero> lazyModel;

    /**
     * Default constructor
     */
    public TorreroController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "TorreroController is initializing");
        lazyModel = new LazyTorreroDataModel(das);
    }

    /**
     * Create, Update and Delete operations
     */
    @Admin
    public void doCreate() {
        das.create(newItem);
        newItem = new Torrero();
    }

    /**
     * @param actionEvent
     */
    @Admin
    public void doConfirmUpdate() {
        das.update(selectedItem);
        Messages.addFlashInfo(null, "Torrero actualizado con éxito.");
    }

    @Admin
    public void doUpdate() {
        setMode(ControllerMode.UPDATE);
        validateSelection();
    }

    public String doView() {
        setMode(ControllerMode.VIEW);
        validateSelection();
        return null;
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

    /**
     * @param
     */
    @Admin
    public void doDelete() {
        if (selectedItems != null && selectedItems.length > 0) {
            if (isSafe2Delete(selectedItems)) {
                das.deleteItems(selectedItems);
                Messages.addInfo(null, "Torrero(s) eliminado(s) con éxito.");
            }
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

    private boolean isSafe2Delete(Torrero[] selectedItems) {
        Set<Torrero> torreroSet = new HashSet<>(Arrays.asList(selectedItems));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("torreroList", torreroSet);
        List<Preventivo> preventivoList = das.findWithNamedQuery(Torrero.EXISTS_IN_PREVENTIVO, parameters, 50);
        if (CollectionUtils.isNotEmpty(preventivoList)) {
            StringBuilder builder = new StringBuilder();
            for (Preventivo preventivo : preventivoList) {
                builder.append("Fecha: ").append(preventivo.getFecha()).append(", Nodo: ").append(preventivo.getNodo().getSigla()).append(", ");
            }
            Messages.addError(null, "Los siguientes preventivos tienen asociado un torrero que se quiere eliminar: " + builder.toString());
            return false;
        } else {
            return true;
        }
    }


}