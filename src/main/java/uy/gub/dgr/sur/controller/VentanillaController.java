package uy.gub.dgr.sur.controller;

/**
 * User: rmartony
 * Date: 19/12/13
 * Time: 04:53 PM
 */

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.picketlink.idm.jpa.model.sample.simple.AccountTypeEntity;
import uy.gub.dgr.sur.entity.Documento;
import uy.gub.dgr.sur.idm.AuthorizationChecker;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.idm.annotations.Ventanilla;
import uy.gub.dgr.sur.service.DocumentoService;
import uy.gub.dgr.sur.service.UsuarioService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
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
    private LoginController loginController;
    @Inject
    private transient AuthorizationChecker authorizationChecker;


    @Getter
    @Setter
    private Documento item;

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

    }

    /**
     * Create, Update and Delete operations
     */
    public String doCreate() {
        String viewId = Faces.getContext().getViewRoot().getViewId();
        setBackOutcome(viewId);

        item = new Documento();

        setMode(ControllerMode.CREATE);
        return "createVentanilla";
    }

    @Ventanilla
    public String doConfirmCreate() {
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


}