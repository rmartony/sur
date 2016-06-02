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
import uy.gub.dgr.sur.model.LazyNodo3GDataModel;
import uy.gub.dgr.sur.service.Nodo3GService;
import uy.gub.dgr.sur.util.ConstraintViolationInterceptor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.model.SelectItem;
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
public class Nodo3GController extends BaseController {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient Nodo3GService das;
    /*
        @Inject
        private transient EscribanoService ipService;
    */
    @Inject
    private transient Celda3GController celda3GController;

    @Inject
    private transient SitioController sitioController;

    // Selected users that will be removed
    @Getter
    @Setter
    private Nodo3G[] selectedItems;
    // Create or update item
    @Getter
    @Setter
    private Nodo3G item;
    @Getter
    @Setter
    private Nodo3G selectedItem;
    // Lazy loading user list
    @Getter
    private LazyDataModel<Nodo3G> lazyModel;

    // Available RNC list
/*
    @Getter
    private List<Rnc> rncList;
    @Getter
    private List<Escribano> ipList;
*/

    @Getter
    private List<TipoBTS> tipoBTSList;
/*
    @Getter
    private SelectItem[] rncListOptions;
*/

    /**
     * Default constructor
     */
    public Nodo3GController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "Nodo3GController is initializing");
        lazyModel = new LazyNodo3GDataModel(das);
/*
        rncList = das.findWithNamedQuery(Rnc.ALL);
        rncListOptions = createRncFilterOptions(rncList);
*/
        tipoBTSList = das.findWithNamedQuery(TipoBTS.ALL);
    }

    /**
     * Create, Update and Delete operations
     */
    public String doCreate() {
/*        ipList = null;*/
        item = new Nodo3G();
        setMode(ControllerMode.CREATE);
        setBackOutcome(null);
        return "create";
    }

    public String doConfirmCreate() {
/*
        if (item.getIp() != null) {
            item.getIp().setOcupada(true);
            ipService.update(item.getIp());
        }
*/
        das.create(item);
        item = new Nodo3G();
        Messages.addFlashInfo(null, "Nodo creado con éxito.");
        return "success";
    }

    public String doConfirmUpdate() {
/*
        Nodo3G nodoOriginal = das.find(item.getId());
        Escribano ipOriginal = nodoOriginal.getIp();
        Escribano ipNueva = item.getIp();
        if (ipOriginal != null && ipNueva != null) {
            if (!ipOriginal.equals(ipNueva)) {
                ipOriginal.setOcupada(false);
                ipService.update(ipOriginal);
                ipNueva.setOcupada(true);
            }
        } else {
            if (ipNueva == null) {
                if (ipOriginal != null) {
                    ipOriginal.setOcupada(false);
                    ipService.update(ipOriginal);
                }
            }
        }
*/
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
            sitioController.setBackOutcome("nodo3GCreate");
        } else {
            sitioController.setBackOutcome("nodo3GUpdate");
        }
        return "sitioView";
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

    public String doUpdate() {
        validateSelection();
        if (Faces.getContext().getMaximumSeverity() == null) {
            setBackOutcome(null);
            setMode(ControllerMode.UPDATE);
            celda3GController.setMode(getMode());
            celda3GController.setItem(null);
/*
            ipList = findIp4Rnc(item.getRnc());

            if (item.getIp() != null && !ipList.contains(item.getIp())) {
                ipList.add(item.getIp());
            }
*/

            selectedItem = null;
            selectedItems = null;
            return "update";
        }
        return null;
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

    private boolean isSafe2Delete(Nodo3G[] selectedItems) {
        List<Nodo3G> nodo3GList = Arrays.asList(selectedItems);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nodoList", nodo3GList);
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

    public List<Sitio> completeSitio(String query) {
        List<Sitio> results;

        Map<String, String> parameters = new HashMap<>();
        parameters.put("nombre", query);
        results = das.findWithNamedQuery(Sitio.BY_SIGLA_NOMBRE, parameters, 100);

        return results;
    }

/*
    public void handleRncChange() {
        if (item.getRnc() != null) {
            ipList = findIp4Rnc(item.getRnc());
        }
    }
*/

    private List<Escribano> findIp4Rnc(Rnc rnc) {
        if (rnc == null) return null;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ocupada", false);
        parameters.put("idRnc", rnc.getId());

        return das.findWithNamedQuery(Escribano.BY_RNC_STATUS, parameters);
    }

    public String createCelda() {
        if (getMode() == ControllerMode.CREATE) {
            celda3GController.setBackOutcome("nodo3GCreate");
        } else if (getMode() == ControllerMode.UPDATE) {
            celda3GController.setBackOutcome("nodo3GUpdate");
        }
        celda3GController.setItem(new Celda3G());
        celda3GController.setMode(ControllerMode.CREATE);
        return "celda3GCreate";
    }

    public void deleteCelda(Celda3G celda3G) {
        if (item != null && CollectionUtils.isNotEmpty(item.getCeldas())) {
            item.getCeldas().remove(celda3G);
        }
    }

    public String updateCelda(Celda3G celda3G) {
        if (getMode() == ControllerMode.CREATE) {
            celda3GController.setBackOutcome("nodo3GCreate");
        } else if (getMode() == ControllerMode.UPDATE) {
            celda3GController.setBackOutcome("nodo3GUpdate");
        }
        celda3GController.setItem(celda3G);
        celda3GController.setMode(ControllerMode.UPDATE);
        return "celda3GUpdate";
    }

    public void listenToCeldaEvent(@Observes CeldaEvent celdaEvent) {
        final Celda celda = celdaEvent.getCelda();
        if (celda != null && celda instanceof Celda3G) {
            Celda3G celda3G = (Celda3G) celda;
            Set<Celda3G> celdas = item.getCeldas();
            if (celda3GController.getMode() == ControllerMode.CREATE) {
                // adds new celda
                celda3G.setNodo3G(item);

                if (celdas == null) {
                    celdas = new HashSet<>();
                }
                celdas.add(celda3G);
                item.setCeldas(celdas);
            } else {
                // updates celda
                //int index = celdas.indexOf(celda3G);
                //celdas.add(celda3G);
            }
        }
    }

}