package uy.gub.dgr.sur.controller;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Messages;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.jpa.model.sample.simple.RoleTypeEntity;
import org.picketlink.idm.model.basic.User;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DualListModel;
import org.primefaces.model.LazyDataModel;
import uy.gub.dgr.sur.entity.Registro;
import uy.gub.dgr.sur.entity.UsuarioRegistro;
import uy.gub.dgr.sur.entity.Zona;
import uy.gub.dgr.sur.idm.AuthorizationChecker;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.model.LazyUsuarioDataModel;
import uy.gub.dgr.sur.service.UsuarioRegistroService;
import uy.gub.dgr.sur.service.UsuarioService;
import uy.gub.dgr.sur.service.ZonaService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: rmartony
 * Date: 13/02/14
 * Time: 12:18 PM
 */
@Named
@SessionScoped
public class UsuarioController extends BaseController {

    @Getter
    @Setter
    List<RoleTypeEntity> sourceRoles = new ArrayList<RoleTypeEntity>();
    @Getter
    @Setter
    List<RoleTypeEntity> selectedRoles = new ArrayList<RoleTypeEntity>();
    @Inject
    private transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient AuthorizationChecker authorizationChecker;
    @Inject
    private LoginController loginController;
    @Inject
    private transient UsuarioService das;
    @Inject
    private transient ZonaService zonaService;
    @Inject
    private transient UsuarioRegistroService usuarioRegistroService;
    // Selected users that will be removed
    @Getter
    @Setter
    private User[] selectedItems;
    // Create or update item
    @Getter
    @Setter
    private User newItem = new User();
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private User selectedItem = new User();
    // Lazy loading user list
    @Getter
    private LazyDataModel<User> lazyModel;
    @Getter
    private SelectItem[] siNoListOptions;
    @Getter
    @Setter
    private DualListModel<Zona> zonaPickList = new DualListModel<>(new ArrayList<Zona>(), new ArrayList<Zona>());

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "UsuarioController is initializing");
        lazyModel = new LazyUsuarioDataModel(das);
        siNoListOptions = createSiNoFilterOptions();
        initRolesPickList();
    }

    /**
     * Create, Update and Delete operations
     */
    public String doCreate() {
        newItem = new User();
        selectedItem = new User();
        setMode(ControllerMode.CREATE);
        initZonaPickList();
        initRolesPickList();
        return "create";
    }

    @Admin
    public void doConfirmCreate() {
        if (das.findUser(newItem.getLoginName()) != null) {
            RequestContext requestContext = RequestContext.getCurrentInstance();
            requestContext.addCallbackParam("validationFailed", true);
            Messages.addFlashError(null, "El usuario ingresado ya existe.");
            return;
        }
        das.create(newItem, new Password(password));
        List<RoleTypeEntity> revokeRoleList = new ArrayList<>(sourceRoles);
        revokeRoleList.removeAll(selectedRoles);
        das.updateRoles(newItem, selectedRoles, revokeRoleList);
        //updateUsuarioZona(newItem.getLoginName(), zonaPickList.getTarget());
        newItem = new User();
        Messages.addFlashInfo(null, "Usuario ingresado con éxito.");
    }

    @Admin
    public void doConfirmUpdate() {
        List<RoleTypeEntity> revokeRoleList = new ArrayList<>(sourceRoles);
        revokeRoleList.removeAll(selectedRoles);

        das.updateRoles(selectedItem, selectedRoles, revokeRoleList);
        //updateUsuarioZona(selectedItem.getLoginName(), zonaPickList.getTarget());

        if (!StringUtils.isBlank(password)) {
            das.update(selectedItem, new Password(password));
        } else {
            das.update(selectedItem, null);
        }
        selectedItems = null;
        selectedItem = new User();
        Messages.addFlashGlobalInfo("Usuario modificado con éxito.");
    }

    private void updateUsuarioZona(String loginName, List<Registro> registroList) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("userId", loginName);
        UsuarioRegistro usuarioRegistro = (UsuarioRegistro) usuarioRegistroService.findSingleResultNamedQuery(UsuarioRegistro.BY_USUARIO_ID, parameters);
        if (usuarioRegistro != null) {
            usuarioRegistroService.delete(usuarioRegistro.getId());
        } else {
            usuarioRegistro = new UsuarioRegistro();
            usuarioRegistro.setUserId(loginName);
        }

        if (CollectionUtils.isNotEmpty(registroList)) {
            usuarioRegistro.setRegistros(registroList);
            usuarioRegistroService.update(usuarioRegistro);
        }
    }

    public void doUpdate() {
        setMode(ControllerMode.UPDATE);
        if (validateSelection()) {

            initRolesPickList();
            List<RoleTypeEntity> roleList = das.getUserRoles(selectedItem.getLoginName());

            if (CollectionUtils.isNotEmpty(roleList)) {
                for (RoleTypeEntity roleTypeEntity : roleList) {
                    if (roleTypeEntity.getName().equalsIgnoreCase("verificacion")) {
                        initZonaPickList();
                        return;
                    }
                }
                zonaPickList = new DualListModel<>(new ArrayList<Zona>(), new ArrayList<Zona>());
            }
        }
    }

    @Admin
    public void doDelete() {
        if (selectedItems != null && selectedItems.length > 0) {
            das.deleteItems(selectedItems);
            Messages.addInfo(null, "Usuario(s) eliminado(s) con éxito.");
        } else {
            Messages.addError(null, msg.getString("noRecordSelected"));
        }
    }

    public String doView() {
        validateSelection();
        setMode(ControllerMode.VIEW);
        return null;
    }

    private SelectItem[] createSiNoFilterOptions() {
        SelectItem[] options = new SelectItem[3];
        options[0] = new SelectItem("", "Seleccionar");
        options[1] = new SelectItem(Boolean.TRUE.toString(), "Sí");
        options[2] = new SelectItem(Boolean.FALSE.toString(), "No");
        return options;
    }

    public void doReset() {
        password = null;
    }

    public String doConfirmChangePassword() {
        das.changePassword(selectedItem, password);
        Messages.addFlashGlobalInfo("Contraseña modificada con éxito.");
        return "success";
    }

    public void initRolesPickList() {
        sourceRoles = das.findRole();
        if (selectedItem != null) {
            selectedRoles = das.getUserRoles(selectedItem.getLoginName());
        } else {
            selectedRoles = new ArrayList<>();
        }
    }

    public void initZonaPickList() {
        boolean isRolTecnico = false;
        List<Zona> sourceZonas = new ArrayList<>();
        for (RoleTypeEntity targetRole : selectedRoles) {
            if (targetRole.getName().equalsIgnoreCase("verificacion")) {
                sourceZonas = zonaService.findWithNamedQuery(Zona.ALL);
                isRolTecnico = true;
                break;
            }
        }
        List<Zona> targetZonas = null;

        if (selectedItem != null && selectedItem.getEmail() != null && isRolTecnico) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("userId", selectedItem.getLoginName());
            //targetZonas = usuarioRegistroService.findWithNamedQuery(UsuarioRegistro.ZONAS_BY_USUARIO_ID, parameters);
        } else {
            targetZonas = new ArrayList<>();
        }
        if (CollectionUtils.isNotEmpty(targetZonas)) {
            sourceZonas.removeAll(targetZonas);
        }

        zonaPickList = new DualListModel<>(sourceZonas, targetZonas);
    }

    public void handleSelectedRoles() {
        initZonaPickList();
/*
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.update("@form");
*/
    }

    public String doCancel() {
        selectedItems = null;
        newItem = new User();
        selectedItem = new User();
        return super.doCancel();
    }

    private boolean validateSelection() {
        if (selectedItem != null && selectedItem.getEmail() != null) {
            return true;
        } else {
            if (selectedItems != null && selectedItems.length > 0) {
                selectedItem = selectedItems[0];
                return true;
            } else {
                if (selectedItem != null && selectedItem.getEmail() == null) {
                    returnError();
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    private void returnError() {
        Messages.addError(null, msg.getString("noRecordSelected"));
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.addCallbackParam("validationFailed", true);
    }
}
