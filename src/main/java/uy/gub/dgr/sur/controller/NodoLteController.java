package uy.gub.dgr.sur.controller;

/**
 * User: rmartony
 * Date: 19/12/13
 * Time: 04:53 PM
 */

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.model.LazyDataModel;
import uy.gub.dgr.sur.entity.*;
import uy.gub.dgr.sur.event.CeldaEvent;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.model.LazyNodoLteDataModel;
import uy.gub.dgr.sur.service.EscribanoService;
import uy.gub.dgr.sur.service.NodoLteService;
import uy.gub.dgr.sur.util.ConstraintViolationInterceptor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
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
public class NodoLteController extends BaseController {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient NodoLteService das;
    @Inject
    private transient EscribanoService escribanoService;
    @Inject
    private transient CeldaLteController celdaLteController;

    @Inject
    private transient SitioController sitioController;

    // Selected users that will be removed
    @Getter
    @Setter
    private NodoLte[] selectedItems;
    // Create or update item
    @Getter
    @Setter
    private NodoLte item;
    @Getter
    @Setter
    private NodoLte selectedItem;
    // Lazy loading user list
    @Getter
    private LazyDataModel<NodoLte> lazyModel;

    @Getter
    private List<Interviniente> intervinienteList;

    /**
     * Default constructor
     */
    public NodoLteController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "NodoLteController is initializing");
        lazyModel = new LazyNodoLteDataModel(das);
        intervinienteList = das.findWithNamedQuery(Interviniente.ALL);
    }

    /**
     * Create, Update and Delete operations
     */
    public String doCreate() {
        item = new NodoLte();
        setMode(ControllerMode.CREATE);
        setBackOutcome(null);
        return "create";
    }

    public String doConfirmCreate() {
        das.create(item);
        Messages.addFlashInfo(null, "Nodo creado con éxito.");
        item = new NodoLte();
        return "success";
    }

    public String doConfirmUpdate() {
        das.update(item);
        Messages.addFlashInfo(null, "Nodo actualizado con éxito.");
        return "success";
    }

    public String doView() {
        validateSelection();
        if (Faces.getContext().getMaximumSeverity() == null) {
            return super.doView();
        }
        return null;
    }

    public String doViewSitio() {
        sitioController.setMode(ControllerMode.VIEW);
        sitioController.updateNodos4Sitio();
        if (ControllerMode.CREATE.equals(getMode())) {
            sitioController.setBackOutcome("nodoLteCreate");
        } else {
            sitioController.setBackOutcome("nodoLteUpdate");
        }
        return "sitioView";
    }

    public String doUpdate() {
        validateSelection();
        if (Faces.getContext().getMaximumSeverity() == null) {
            setBackOutcome(null);
            setMode(ControllerMode.UPDATE);
            celdaLteController.setMode(getMode());
            celdaLteController.setItem(null);

            selectedItem = null;
            selectedItems = null;
            return "update";
        } else {
            return null;
        }
    }

    private void validateSelection() {
        if (selectedItem != null || selectedItems != null && selectedItems.length > 0) {
            if (selectedItem != null) {
                item = selectedItem;
            } else {
                item = selectedItems[0];
            }
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

    @Admin
    public void doDelete() {
        if (selectedItems != null && selectedItems.length > 0) {
            if (isSafe2Delete(selectedItems)) {
                das.deleteItems(selectedItems);
                Messages.addInfo(null, "Nodo(s) eliminado(s) con éxito.");
            }
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

    private boolean isSafe2Delete(NodoLte[] selectedItems) {
        List<NodoLte> nodoLteList = Arrays.asList(selectedItems);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nodoList", nodoLteList);
        List<Preventivo> preventivoList = das.findWithNamedQuery(Nodo.EXISTS_IN_PREVENTIVO, parameters, 50);
        if (CollectionUtils.isNotEmpty(preventivoList)) {
            StringBuilder builder = new StringBuilder();
            for (Preventivo preventivo : preventivoList) {
                builder.append("Fecha: ").append(preventivo.getFecha()).append(", Nodo: ").append(preventivo.getNodo().getSigla());
            }
            Messages.addError(null, "Los siguientes Preventivos tienen asociado un nodo que se quiere eliminar: " + builder.toString());
            return false;
        } else {
            return true;
        }
    }

    public List<Sitio> completeSitio(String query) {
        List<Sitio> results;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("nombre", query);
        results = das.findWithNamedQuery(Sitio.BY_SIGLA_NOMBRE, parameters, 100);

        return results;
    }

    public String createCelda() {
        if (getMode() == ControllerMode.CREATE) {
            celdaLteController.setBackOutcome("nodoLteCreate");
        } else if (getMode() == ControllerMode.UPDATE) {
            celdaLteController.setBackOutcome("nodoLteUpdate");
        }
        celdaLteController.setItem(new CeldaLte());
        celdaLteController.setMode(ControllerMode.CREATE);
        return "celdaLteCreate";
    }

    public void deleteCelda(CeldaLte celdaLte) {
        if (item != null && CollectionUtils.isNotEmpty(item.getCeldas())) {
            item.getCeldas().remove(celdaLte);
        }
    }

    public String updateCelda(CeldaLte celdaLte) {
        if (getMode() == ControllerMode.CREATE) {
            celdaLteController.setBackOutcome("nodoLteCreate");
        } else if (getMode() == ControllerMode.UPDATE) {
            celdaLteController.setBackOutcome("nodoLteUpdate");
        }
        celdaLteController.setItem(celdaLte);
        celdaLteController.setMode(ControllerMode.UPDATE);
        return "celdaLteUpdate";
    }

    public void listenToCeldaEvent(@Observes CeldaEvent celdaEvent) {
        final Celda celda = celdaEvent.getCelda();
        if (celda != null && celda instanceof CeldaLte) {
            CeldaLte celdaLte = (CeldaLte) celda;
            Set<CeldaLte> celdas = item.getCeldas();
            if (celdaLteController.getMode() == ControllerMode.CREATE) {
                // adds new celda
                celdaLte.setNodoLte(item);

                if (celdas == null) {
                    celdas = new HashSet<>();
                }
                celdas.add(celdaLte);
                item.setCeldas(celdas);
            } else {
                // updates celda
                //int index = celdas.indexOf(celdaLte);
                //celdas.add(celdaLte);
            }
        }
    }

}