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
import uy.gub.dgr.sur.entity.Sitio;
import uy.gub.dgr.sur.entity.Zona;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.model.LazyZonaDataModel;
import uy.gub.dgr.sur.service.ZonaService;
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
public class ZonaController extends BaseController {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient ZonaService das;
    // Selected users that will be removed
    @Getter
    @Setter
    private Zona[] selectedItems;
    // Creating new zona
    @Getter
    @Setter
    private Zona newZona = new Zona();
    // Selected user that will be updated
    @Getter
    @Setter
    private Zona selectedItem = new Zona();
    // Lazy loading user list
    @Getter
    private LazyDataModel<Zona> lazyModel;


    /**
     * Default constructor
     */
    public ZonaController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "ZonaController is initializing");
        lazyModel = new LazyZonaDataModel(das);
    }

    /**
     * Create, Update and Delete operations
     */
    @Admin
    public void doConfirmCreate() {
        das.create(newZona);
        newZona = new Zona();
        selectedItems = null;
        selectedItem = null;
    }

    /**
     * @param
     */
    @Admin
    public void doConfirmUpdate() {
        das.update(selectedItem);
        Messages.addFlashInfo(null, "Zona actualizada con éxito.");
        selectedItem = null;
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
        validateSelection();
        setMode(ControllerMode.VIEW);
        return null;
    }

    /**
     * @param
     */
    @Admin
    public void doDeleteZonas() {
        if (selectedItems != null && selectedItems.length > 0) {
            if (isSafe2Delete(selectedItems)) {
                das.deleteItems(selectedItems);
                selectedItems = null;
                Messages.addInfo(null, "Zona(s) eliminada(s) con éxito.");
            }
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

    private boolean isSafe2Delete(Zona[] selectedItems) {
        List<Zona> zonaList = Arrays.asList(selectedItems);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("zonaList", zonaList);
        List<Sitio> sitios = das.findWithNamedQuery(Zona.EXISTS_IN_SITIO, parameters, 50);
        if (CollectionUtils.isNotEmpty(sitios)) {
            StringBuilder builder = new StringBuilder();
            for (Sitio sitio : sitios) {
                builder.append(sitio.getSigla()).append(", ");
            }
            Messages.addError(null, "Los siguientes sitios tienen asociado la zona que se quiere eliminar: " + builder.toString());
            return false;
        } else {
            return true;
        }
    }


}