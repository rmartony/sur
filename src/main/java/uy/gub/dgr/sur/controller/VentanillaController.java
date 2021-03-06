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
import uy.gub.dgr.sur.entity.*;
import uy.gub.dgr.sur.idm.AuthorizationChecker;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.idm.annotations.Ventanilla;
import uy.gub.dgr.sur.service.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
public class VentanillaController extends BaseController {
    @Getter
    @Setter
    List<AccountTypeEntity> usuarioList = new ArrayList<>();
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient DocumentoService das;
    @Inject
    private transient UsuarioService usuarioService;
    @Inject
    private EstadoService estadoService;
    @Inject
    private LoginController loginController;
    @Inject
    private transient AuthorizationChecker authorizationChecker;
    @Inject
    private EmisorService emisorService;
    @Inject
    private EscribanoService escribanoService;
    @Inject
    private TasaService tasaService;
    @Inject
    private MovimientoService movimientoService;
    @Inject
    private IntervinienteService intervinienteService;


    @Getter
    @Setter
    private Documento item;

    @Getter
    @Setter
    private Inscripcion inscripcion;

    @Getter
    @Setter
    private List<Emisor> emisorList;

    @Getter
    @Setter
    private List<Escribano> escribanoList;

    @Getter
    @Setter
    private List<Tasa> tasaList;

    @Getter
    @Setter
    private List<Seccion> seccionList;

    @Getter
    @Setter
    private List<Movimiento> movimientoList;

    @Getter
    @Setter
    private Acto acto;

    @Getter
    @Setter
    private List<Acto> actoList;

    private ListIterator<Inscripcion> inscripcionListIterator;

    @Getter
    @Setter
    private List<Interviniente> intervinienteList;

    /**
     * Default constructor
     */
    public VentanillaController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "VentanillaController is initializing");

        emisorList = emisorService.findWithNamedQuery(Emisor.ALL);
        escribanoList = escribanoService.findWithNamedQuery(Escribano.ALL);
        tasaList = tasaService.findWithNamedQuery(Tasa.ALL);
        movimientoList = movimientoService.findWithNamedQuery(Movimiento.ALL);
        intervinienteList = intervinienteService.findWithNamedQuery(Interviniente.ALL);

    }

    /**
     * Create, Update and Delete operations
     */
    public String doCreate() {
        String viewId = Faces.getContext().getViewRoot().getViewId();
        setBackOutcome(viewId);

        item = new Documento();
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int anio = now.get(Calendar.YEAR);       // The current year

        item.setAnio(anio);
        item.setFechaEmision(now.getTime());

        inscripcion = new Inscripcion();
        inscripcion.setActo(new Acto());
        inscripcion.setDocumento(item);
        inscripcion.setOrdinal(1);

        final List<Registro> registrosUsuario = loginController.getRegistrosUsuario();

        final ArrayList<Inscripcion> inscripcionList = new ArrayList<>();
        inscripcionList.add(inscripcion);
        item.setInscripcionList(inscripcionList);
        inscripcionListIterator = item.getInscripcionList().listIterator();

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("codigo", Estado.VENTANILLA);
        Estado estado = (Estado) estadoService.findSingleResultNamedQuery(Estado.BY_CODIGO, parameters);
        item.setEstado(estado);

        init();

        setMode(ControllerMode.CREATE);
        return "createVentanilla";
    }

    public void repetirInscripcion() {
        int ordinal = inscripcion.getOrdinal() + 1;
        inscripcion = new Inscripcion();
        inscripcion.setActo(new Acto());
        inscripcion.setDocumento(item);
        inscripcion.setOrdinal(ordinal);

        while (inscripcionListIterator.hasNext()) {
            inscripcionListIterator.next();
        }
        inscripcionListIterator.add(inscripcion);
    }

    public void previousInscripcion() {
        if (inscripcionListIterator.hasPrevious()) {
            inscripcion = inscripcionListIterator.previous();
            if (inscripcionListIterator.hasPrevious()) inscripcion = inscripcionListIterator.previous();
        } else {
            Messages.addFlashGlobalWarn("Ya se encuentra en la primera inscripción.");
        }

    }

    public void nextInscripcion() {
        if (inscripcionListIterator.hasNext()) {
            inscripcion = inscripcionListIterator.next();
            if (inscripcionListIterator.hasNext()) inscripcion = inscripcionListIterator.next();
        } else {
            Messages.addFlashGlobalWarn("Ya se encuentra en la última inscripción.");
        }

    }

    @Ventanilla
    public String doConfirmCreate() {
        if (das.existsDocumento(item)) {
            Messages.addFlashGlobalError("Atención, no es posible almacenar el documento en el sistema debido a que ya existe.");
            return null;
        }
        //das.update(item);
        Messages.addFlashGlobalInfo("Documento ventanilla creado con éxito.");

        item = new Documento();
        return "success";
    }

    @Ventanilla
    public String doConfirmUpdate() {
        //das.update(item);
        Messages.addFlashGlobalInfo("Documento ventanilla actualizado con éxito.");
        return "success";
    }

    public String doView() {
        String viewId = Faces.getContext().getViewRoot().getViewId();
        setBackOutcome(viewId);

        if (Faces.getContext().getMaximumSeverity() == null) {
            return super.doView();
        }
        return null;
    }

    public String doUpdate() {
        String viewId = Faces.getContext().getViewRoot().getViewId();
        setBackOutcome(viewId);

        if (Faces.getContext().getMaximumSeverity() == null) {
            setMode(ControllerMode.UPDATE);

            return "update";
        }
        return null;
    }


    @Admin
    public void doDelete() {

    }

    public List<Emisor> findEmisor(String query) {
        List<Emisor> results = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(emisorList)) {
            for (Emisor emisor : emisorList) {
                if (emisor.getCodigo().contains(query) || emisor.getDescripcion().contains(query)) {
                    results.add(emisor);
                }
            }
        }
        return results;

    }

    public List<Escribano> findEscribano(String query) {
        List<Escribano> results = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(escribanoList)) {
            for (Escribano escribano : escribanoList) {
                if (escribano.getCodigo().toString().contains(query) || escribano.getNombre().contains(query)) {
                    results.add(escribano);
                }
            }
        }
        return results;

    }

    public List<Tasa> findTasa(String query) {
        List<Tasa> results = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(tasaList)) {
            for (Tasa tasa : tasaList) {
                if (tasa.getCodigo().contains(query) || tasa.getNombre().contains(query)) {
                    results.add(tasa);
                }
            }
        }
        return results;

    }

    public List<Seccion> findSeccion(String query) {
        List<Seccion> results = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(seccionList)) {
            for (Seccion seccion : seccionList) {
                if (seccion.getCodigo().contains(query) || seccion.getDescripcion().contains(query)) {
                    results.add(seccion);
                }
            }
        }
        return results;

    }

    public List<Interviniente> findInterviniente(String query) {
        List<Interviniente> results = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(intervinienteList)) {
            for (Interviniente interviniente : intervinienteList) {
                if (interviniente.getCodigo().contains(query) || interviniente.getDescripcion().contains(query)) {
                    results.add(interviniente);
                }
            }
        }
        return results;

    }

    public void handleRegistroChange() {
        if (item.getRegistro() != null) {
            seccionList = findSeccion4Registro(item.getRegistro());
        }
    }

    private List<Seccion> findSeccion4Registro(Registro registro) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("codigo", registro.getCodigo());

        return das.findWithNamedQuery(Seccion.BY_REGISTRO, parameters);
    }

    public void handleSeccionChange() {
        if (inscripcion.getActo().getSeccion() != null) {
            actoList = findActo4Seccion(inscripcion.getActo().getSeccion());
        }
    }

    private List<Acto> findActo4Seccion(Seccion seccion) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("seccion", seccion);

        return das.findWithNamedQuery(Acto.BY_SECCION, parameters);
    }

    public List<Acto> findActo(String query) {
        List<Acto> results = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(actoList)) {
            for (Acto acto : actoList) {
                if (acto.getCodigo().contains(query) || acto.getDescripcion().contains(query)) {
                    results.add(acto);
                }
            }
        }
        return results;

    }

    public List<Movimiento> findMovimiento(String query) {
        List<Movimiento> results = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(movimientoList)) {
            for (Movimiento movimiento : movimientoList) {
                if (movimiento.getCodigo().contains(query) || movimiento.getDescripcion().contains(query)) {
                    results.add(movimiento);
                }
            }
        }
        return results;

    }

}