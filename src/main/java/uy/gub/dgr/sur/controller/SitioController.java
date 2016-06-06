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
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import uy.gub.dgr.sur.entity.*;
import uy.gub.dgr.sur.idm.annotations.Completado;
import uy.gub.dgr.sur.model.LazySitioDataModel;
import uy.gub.dgr.sur.service.SitioService;
import uy.gub.dgr.sur.util.ConstraintViolationInterceptor;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@Interceptors(value = ConstraintViolationInterceptor.class)
public class SitioController extends BaseController {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient SitioService das;
    @Getter
    @Setter
    private Sitio[] selectedItems;
    // Creating new zona
    @Getter
    @Setter
    private Sitio newItem = new Sitio();
    @Getter
    @Setter
    private Sitio selectedItem = new Sitio();
    // Lazy loading user list
    @Getter
    private LazyDataModel<Sitio> lazyModel;

    // Available departamento list
    @Getter
    private List<Sede> sedeList;
    @Getter
    private List<Estructura> estructuraList;

    @Getter
    private List<Nodo> nodosSitio;

    @Getter
    @Setter
    private Nodo selectedNodoItem;

    @Getter
    private List<Zona> zonaList;
    @Getter
    private SelectItem[] zonaListOptions;
    @Getter
    private SelectItem[] departamentoListOptions;

/*    private static List<String> tipoCoordenadas = Arrays.asList("WGS84", "GPS");*/

    @Inject
    private transient Nodo3GController nodo3GController;
    @Inject
    private transient NodoLteController nodoLteController;

    /**
     * Default constructor
     */
    public SitioController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "SitioController is initializing");
        lazyModel = new LazySitioDataModel(das);
        sedeList = das.findWithNamedQuery(Sede.ALL);
        estructuraList = das.findWithNamedQuery(Estructura.ALL);
        zonaList = das.findWithNamedQuery(Zona.ALL);
        zonaListOptions = createZonaFilterOptions(zonaList);
        departamentoListOptions = createDepartamentoFilterOptions(sedeList);
    }

    /**
     * Create, Update and Delete operations
     */
    @Completado
    public String doConfirmCreate() {
        das.create(newItem);
        newItem = new Sitio();
        return "success";
    }

    public String doConfirmUpdate() {
        das.update(selectedItem);
        Messages.addFlashInfo(null, "Sitio actualizado con éxito.");
        return "success";
    }

    public String doCreate() {
        setMode(ControllerMode.CREATE);
        setBackOutcome(null);
        return "create";
    }

    public String doUpdate() {
        setMode(ControllerMode.UPDATE);
        validateSelection();
        if (Faces.getContext().getMaximumSeverity() == null) {
            updateNodos4Sitio();
            return "update";
        } else {
            return null;
        }
    }

    public String doView() {
        validateSelection();
        if (Faces.getContext().getMaximumSeverity() == null) {
            updateNodos4Sitio();
            return super.doView();
        }
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

    public void doConfirmDelete() {
        if (selectedItems != null && selectedItems.length > 0) {
            das.deleteItems(selectedItems);
            Messages.addInfo(null, "Sitio(s) eliminado(s) con éxito.");
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }


/*
    public List<String> getTipoCoordenadas(String query) {
        List<String> results = new ArrayList<String>();
        for (String tipoCoordenada : tipoCoordenadas) {
            if (StringUtils.startsWithIgnoreCase(tipoCoordenada, query)) {
                results.add(tipoCoordenada);
            }
        }
        return results;
    }
*/

    private SelectItem[] createZonaFilterOptions(List<Zona> data) {
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

    private SelectItem[] createDepartamentoFilterOptions(List<Sede> data) {
        SelectItem[] options = null;
        if (CollectionUtils.isNotEmpty(data)) {
            options = new SelectItem[data.size() + 1];

            options[0] = new SelectItem("", "Seleccionar");
            for (int i = 0; i < data.size(); i++) {
                options[i + 1] = new SelectItem(data.get(i).getDescripcion(), data.get(i).getDescripcion());
            }
        }
        return options;
    }

    public String doCancel() {
        if (getBackOutcome() != null) {
            String outCome = getBackOutcome();
            setBackOutcome(null);
            return outCome;
        } else {
            return "cancel";
        }
    }

    void updateNodos4Sitio() {
        if (selectedItem == null) nodosSitio = null;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idSitio", selectedItem.getId());

        nodosSitio = das.findWithNamedQuery(Nodo.BY_SITIO_ID, parameters);
    }

    public String doNodoView() {
        if (selectedNodoItem != null) {
            if ("3G".equals(selectedNodoItem.getDiscriminatorValue())) {
                nodo3GController.setBackOutcome("sitioUpdate");
                nodo3GController.setItem((Nodo3G) selectedNodoItem);
                nodo3GController.setSelectedItem((Nodo3G) selectedNodoItem);
                nodo3GController.setMode(ControllerMode.VIEW);
                return "nodo3GUpdate";
            } else {
                nodoLteController.setBackOutcome("sitioUpdate");
                nodoLteController.setItem((NodoLte) selectedNodoItem);
                nodoLteController.setSelectedItem((NodoLte) selectedNodoItem);
                nodoLteController.setMode(ControllerMode.VIEW);
                return "nodoLteUpdate";
            }
        }
        return null;
    }
}