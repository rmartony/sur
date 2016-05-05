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
import org.picketlink.idm.jpa.model.sample.simple.AccountTypeEntity;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import uy.gub.dgr.sur.entity.*;
import uy.gub.dgr.sur.idm.AuthorizationChecker;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.idm.annotations.Tecnico;
import uy.gub.dgr.sur.model.LazyPreventivoDataModel;
import uy.gub.dgr.sur.model.LazyUltimoPreventivoDataModel;
import uy.gub.dgr.sur.service.PreventivoService;
import uy.gub.dgr.sur.service.UltimoPreventivoService;
import uy.gub.dgr.sur.service.UsuarioService;
import uy.gub.dgr.sur.service.ZonaService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
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
public class PreventivoController extends BaseController {
    @Getter
    @Setter
    List<AccountTypeEntity> usuarioList = new ArrayList<>();
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient PreventivoService das;
    @Inject
    private transient UsuarioService usuarioService;
    @Inject
    private LoginController loginController;
    @Inject
    private transient AuthorizationChecker authorizationChecker;
    @Inject
    private ZonaService zonaService;
    @Getter
    private SelectItem[] zonaListOptions;
    // Selected users that will be removed
    @Getter
    @Setter
    private Preventivo[] selectedItems;
    @Getter
    @Setter
    private UltimoPreventivo[] selectedUltimosPreventivosItems;

    // Creating new zona
    @Getter
    @Setter
    private Preventivo item;
    // Selected user that will be updated
    @Getter
    @Setter
    private Preventivo selectedItem = new Preventivo();
    // Lazy loading preventivo list
    @Getter
    private LazyDataModel<Preventivo> lazyModel;
    // Lazy loading ultimo preventivo list
    @Getter
    private LazyDataModel<UltimoPreventivo> ultimoPreventivoLazyModel;
    // Available RNC list
    @Getter
    private List<EstadoPreventivo> estadoPreventivoList;
    @Getter
    @Setter
    private DualListModel<Torrero> pickList = new DualListModel<>(new ArrayList<Torrero>(), new ArrayList<Torrero>());
    @Getter
    @Setter
    private List<Torrero> torreroList;
    @Getter
    @Setter
    private Sitio sitio;
    // lista de nodos de un sitio seleccionado
    @Getter
    private List<Nodo> nodoList;
    // zona seleccionada para mostrar últimos preventivos
    @Getter
    @Setter
    private Zona selectedZona;
    // zonas del técnico
    @Getter
    @Setter
    private List<Zona> zonaList;
    @Inject
    private UltimoPreventivoService ultimoPreventivoService;
    @Inject
    private ConfiguracionController configuracionController;
    @Getter
    @Setter
    private boolean rolTecnico = false;

    /**
     * Default constructor
     */
    public PreventivoController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        String username = null;
        log.log(Level.INFO, "PreventivoController is initializing");
        zonaList = loginController.getZonasTecnico();
        if (authorizationChecker.isTecnico()) {
            rolTecnico = true;
            username = loginController.getLoginName();
            zonaListOptions = createZonaFilterOptions(zonaList);
        } else {
            zonaList = zonaService.findWithNamedQuery(Zona.ALL);
            zonaListOptions = createZonaFilterOptions(zonaList);
        }

        if (zonaListOptions == null) zonaListOptions = new SelectItem[0];

        lazyModel = new LazyPreventivoDataModel(das, username, zonaList, getPrefijoRutaFotos());
        ultimoPreventivoLazyModel = new LazyUltimoPreventivoDataModel(das, username, zonaList);
        estadoPreventivoList = das.findWithNamedQuery(EstadoPreventivo.ALL);
        torreroList = das.findWithNamedQuery(Torrero.ALL);

        initPickList();
        handleSitioChange();
    }

    private String getPrefijoRutaFotos() {
        if (loginController.isLoginInterno()) {
            return configuracionController.getConfiguracion().getPrefijoUrlInternaFotos();
        } else {
            return configuracionController.getConfiguracion().getPrefijoUrlExternaFotos();
        }
    }

    private void initPickList() {
        List<Torrero> sourceTorreros = new ArrayList<>();
        List<Torrero> targetTorreros = new ArrayList<>();
        sourceTorreros.addAll(torreroList);
        if (item != null && CollectionUtils.isNotEmpty(item.getTorreros())) {
            sourceTorreros.removeAll(item.getTorreros());
            targetTorreros.addAll(item.getTorreros());
        }
        pickList = new DualListModel<>(sourceTorreros, targetTorreros);
    }

    /**
     * Create, Update and Delete operations
     */
    public String doCreate() {
        String viewId = Faces.getContext().getViewRoot().getViewId();
        setBackOutcome(viewId);

        item = new Preventivo();
        initPickList();
        if (rolTecnico) {
            item.setTecnico(loginController.getLoginName());
        }
        usuarioList = usuarioService.findUserByRole("tecnico");

        setMode(ControllerMode.CREATE);
        return "createPreventivo";
    }

    @Tecnico
    public String doConfirmCreate() {
        //das.update(item);
        updateUltimoPreventivo();
        Messages.addFlashGlobalInfo("Mantenimiento preventivo creado con éxito.");

        item = new Preventivo();
        return "success";
    }

    private void updateUltimoPreventivo() {
        updateTorreros();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idNodo", item.getNodo().getId());
        List<UltimoPreventivo> ultimoPreventivoList = ultimoPreventivoService.findWithNamedQuery(UltimoPreventivo.BY_NODO_ID, parameters);

        item.setRutaFotos(item.getPosfijoRutaFotos());
        createFolder(configuracionController.getConfiguracion().getPrefijoRutaMontajeFotos() + item.getRutaFotos());

        // no existe ultimo preventivo para el nodo?
        if (CollectionUtils.isEmpty(ultimoPreventivoList)) {
            UltimoPreventivo ultimoPreventivo = new UltimoPreventivo();
            ultimoPreventivo.setNodo(item.getNodo());
            ultimoPreventivo.setPreventivo(item);
            ultimoPreventivoService.update(ultimoPreventivo);
        } else {
            UltimoPreventivo ultimoPreventivo = ultimoPreventivoList.get(0);
            if (item.getFecha().after(ultimoPreventivo.getPreventivo().getFecha())) {
                // existe ultimo preventivo, se actualiza fecha y preventivo
                ultimoPreventivo.setPreventivo(item);
                ultimoPreventivo.setNodo(item.getNodo());
                ultimoPreventivoService.sessionClear();
                ultimoPreventivoService.update(ultimoPreventivo);
            } else {
                das.update(item);
            }
        }

    }

    private void createFolder(String folder) {
        boolean ok = false;
        File file = new File(folder);
        if (!file.exists()) {
            try {
                ok = file.mkdirs();
            } catch (Throwable e) {
                Messages.addFlashGlobalFatal("No es posible crear la carpeta " + folder);
                log.log(Level.SEVERE, e.getMessage(), e);
            }
        }
        if (!ok) {
            Messages.addFlashGlobalFatal("No es posible crear la carpeta " + folder);
        }

    }

    private void updateTorreros() {
        final List<Torrero> pickListTarget = pickList.getTarget();
        if (CollectionUtils.isNotEmpty(pickListTarget)) {
            Set<Torrero> hashSet = new HashSet<>(pickListTarget.size());
            hashSet.addAll(pickListTarget);
            item.setTorreros(hashSet);
        } else {
            item.setTorreros(new HashSet<Torrero>());

        }
    }

    @Tecnico
    public String doConfirmUpdate() {
        //das.update(item);
        updateUltimoPreventivo();
        Messages.addFlashGlobalInfo("Mantenimiento preventivo actualizado con éxito.");
        return "success";
    }

    public String doView() {
        String viewId = Faces.getContext().getViewRoot().getViewId();
        setBackOutcome(viewId);

        validateSelection();
        if (Faces.getContext().getMaximumSeverity() == null) {
            return super.doView();
        }
        return null;
    }

    public String doUpdate() {
        String viewId = Faces.getContext().getViewRoot().getViewId();
        setBackOutcome(viewId);

        validateSelection();
        if (Faces.getContext().getMaximumSeverity() == null) {
            setMode(ControllerMode.UPDATE);

            selectedItem = null;
            selectedItems = null;
/*
            if (item != null && item.getNodo() != null && item.getNodo().getSitio() != null) {
                setSitio(item.getNodo().getSitio());
            }
*/
            initPickList();
            if (rolTecnico) {
                item.setTecnico(loginController.getLoginName());
            }
            usuarioList = usuarioService.findUserByRole("tecnico");

            //handleSitioChange();
            return "update";
        }
        return null;
    }

    private Preventivo[] getPreventivosFromUltimosPreventivos(UltimoPreventivo[] ultimosPreventivos) {
        Preventivo[] preventivos = null;
        if (ultimosPreventivos != null && ultimosPreventivos.length > 0) {
            preventivos = new Preventivo[ultimosPreventivos.length];
            int i = 0;
            for (UltimoPreventivo ultimoPreventivo : ultimosPreventivos) {
                preventivos[i] = ultimoPreventivo.getPreventivo();
            }
        }
        return preventivos;
    }

    public String doUltimoPreventivoView() {
        selectedItems = getPreventivosFromUltimosPreventivos(selectedUltimosPreventivosItems);
        return doView();
    }

    public String doUltimoPreventivoUpdate() {
        selectedItems = getPreventivosFromUltimosPreventivos(selectedUltimosPreventivosItems);
        return doUpdate();
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
            for (Preventivo preventivo : selectedItems) {
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("idPreventivo", preventivo.getId());
                List<UltimoPreventivo> ultimoPreventivoList = ultimoPreventivoService.findWithNamedQuery(UltimoPreventivo.BY_PREVENTIVO_ID, parameters);
                if (CollectionUtils.isNotEmpty(ultimoPreventivoList)) {
                    ultimoPreventivoService.delete(ultimoPreventivoList.get(0).getId());
                }
            }
            das.deleteItems(selectedItems);
            Messages.addInfo(null, "Mantenimiento(s) preventivo(s) eliminado(s) con éxito.");
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

    public List<Sitio> completeSitio(String query) {
        List<Sitio> results;

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("nombre", query);
        if (rolTecnico) {
            List<Integer> idZonaList = new ArrayList<>(zonaList.size());
            for (Zona zona : zonaList) {
                idZonaList.add(zona.getId());
            }
            if (CollectionUtils.isNotEmpty(idZonaList)) {
                parameters.put("idZonaList", idZonaList);
                results = das.findWithNamedQuery(Sitio.BY_SIGLA_ZONA, parameters, 100);
            } else {
                results = das.findWithNamedQuery(Sitio.BY_SIGLA_NOMBRE, parameters, 100);
            }
        } else {
            results = das.findWithNamedQuery(Sitio.BY_SIGLA_NOMBRE, parameters, 100);
        }


        return results;
    }

    private List<Nodo> findNodo4Sitio(Sitio sitio) {
        if (sitio == null) return null;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idSitio", sitio.getId());

        return das.findWithNamedQuery(Nodo.BY_SITIO_ID, parameters);
    }

    private List<Nodo> findNodo4Zona(List<Integer> idZonaList) {
        if (CollectionUtils.isEmpty(idZonaList)) return null;
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("idZonas", idZonaList);

        return das.findWithNamedQuery(Nodo.BY_SITIO_ZONA_ID, parameters);
    }

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

    public void handleSitioChange() {
        if (getSitio() != null) {
            nodoList = findNodo4Sitio(getSitio());
        }
    }

    public void handleZonaChange(final AjaxBehaviorEvent event) {
        LazyUltimoPreventivoDataModel lazyDataModel = (LazyUltimoPreventivoDataModel) ultimoPreventivoLazyModel;
        lazyDataModel.setSelectedZona(selectedZona);
    }

}