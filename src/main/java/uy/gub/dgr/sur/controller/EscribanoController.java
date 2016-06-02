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
import uy.gub.dgr.sur.entity.Escribano;
import uy.gub.dgr.sur.entity.Rnc;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.model.LazyEscribanoDataModel;
import uy.gub.dgr.sur.service.EscribanoService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
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
public class EscribanoController implements Serializable {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient EscribanoService das;
    // Selected users that will be removed
    @Getter
    @Setter
    private Escribano[] selectedItems;
    // Creating new zona
    @Getter
    @Setter
    private Escribano newItem = new Escribano();
    // Selected user that will be updated
    @Getter
    @Setter
    private Escribano selectedItem = new Escribano();
    // Lazy loading user list
    @Getter
    private LazyDataModel<Escribano> lazyModel;

    // Available RNC list
    @Getter
    private List<Rnc> rncList;
    @Getter
    private SelectItem[] rncListOptions;
    @Getter
    private SelectItem[] siNoListOptions;

    /**
     * Default constructor
     */
    public EscribanoController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "EscribanoController is initializing");
        lazyModel = new LazyEscribanoDataModel(das);
        rncList = das.findWithNamedQuery(Rnc.ALL);
        siNoListOptions = createSiNoFilterOptions();
        rncListOptions = createRncFilterOptions(rncList);
    }

    /**
     * Create, Update and Delete operations
     */
    @Admin
    public void doCreate() {
        das.create(newItem);
        newItem = new Escribano();
        Messages.addFlashInfo(null, "Escribano ingresado con éxito.");
    }

    /**
     * @param actionEvent
     */
    @Admin
    public void doConfirmUpdate() {
        das.update(selectedItem);
        Messages.addFlashInfo(null, "Escribano actualizado con éxito.");
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
            Messages.addInfo(null, "Escribano(s) quitado(s) con éxito.");
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

    private SelectItem[] createRncFilterOptions(List<Rnc> data) {
        SelectItem[] options = null;
        if (CollectionUtils.isNotEmpty(data)) {
            options = new SelectItem[data.size() + 1];

            options[0] = new SelectItem("", "Seleccionar");
            for (int i = 0; i < data.size(); i++) {
                options[i + 1] = new SelectItem(data.get(i).getNombre(), data.get(i).getNombre());
            }
        }
        return options;
    }

    private SelectItem[] createSiNoFilterOptions() {
        SelectItem[] options = new SelectItem[3];
        options[0] = new SelectItem("", "Seleccionar");
        options[1] = new SelectItem(Boolean.TRUE.toString(), "Sí");
        options[2] = new SelectItem(Boolean.FALSE.toString(), "No");
        return options;
    }

}