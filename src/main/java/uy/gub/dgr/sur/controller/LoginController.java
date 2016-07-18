package uy.gub.dgr.sur.controller;

/**
 * User: rmartony
 * Date: 16/12/13
 * Time: 07:07 PM
 */

import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.picketlink.Identity;
import org.picketlink.Identity.AuthenticationResult;
import org.picketlink.authentication.event.PostLoggedOutEvent;
import org.picketlink.idm.model.Account;
import org.picketlink.idm.model.basic.User;
import uy.gub.dgr.sur.entity.Auditoria;
import uy.gub.dgr.sur.entity.UsuarioZona;
import uy.gub.dgr.sur.entity.Zona;
import uy.gub.dgr.sur.service.AuditoriaService;
import uy.gub.dgr.sur.service.UsuarioZonaService;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * We control the authentication process from this action bean, so that in the event of a failed authentication we can add an
 * appropriate FacesMessage to the response.
 *
 * @author Shane Bryzak
 */

@Named
//@RequestScoped
@SessionScoped
public class LoginController implements Serializable {
    @Inject
    Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private Identity identity;

    @Inject
    private transient AuditoriaService auditoriaService;

    @Getter
    @Setter
    private List<Zona> zonasTecnico;

    @Inject
    private transient UsuarioZonaService usuarioZonaService;

    @Getter
    @Setter
    private boolean loginInterno;

    @Inject
    private ConfiguracionController configuracion;

/*
    @Inject
    private Editor editor;
*/

    public String login() {
        if (!identity.isLoggedIn()) {
            AuthenticationResult result = identity.login();
            if (AuthenticationResult.FAILED.equals(result)) {
                Messages.addError(null, msg.getString("authenticationFailed"));

                auditLogin("failed login");
                return null;
            } else {
                auditLogin("login");
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("userId", getLoginName());
                setZonasTecnico(usuarioZonaService.findWithNamedQuery(UsuarioZona.ZONAS_BY_USUARIO_ID, parameters));
                loginInterno = isLocalLogin();
                setAudit();
            }
        }

        return "success";
    }

    private boolean isLocalLogin() {
        HttpServletRequest request = Faces.getRequest();
        try {
            InetAddress inetAddress = InetAddress.getByName(request.getRemoteAddr());
            log.fine("Usuario ingresó desde dirección IP: " + inetAddress.getHostAddress());
            boolean isLocalIp = configuracion.getConfiguracion().isLocalIp(inetAddress);
            log.fine("La IP pertenece a red local: " + isLocalIp);
            return isLocalIp;
        } catch (UnknownHostException e) {
            log.log(Level.WARNING, "La dirección remota no es reconocida (" + request.getRemoteAddr() + ") " + e.getMessage(), e);
        }
        return false;
    }

    private Auditoria auditLogin(String operacion) {
        Auditoria auditoria = new Auditoria();
        auditoria.setTimestamp(new Date().getTime());
        auditoria.setOperacion(operacion);
        if (identity != null && identity.getAccount() != null) {
            String loginName = ((User) identity.getAccount()).getLoginName();
            auditoria.setUserName(loginName);
            auditoria.setTimestamp(new Date().getTime());
        }
        auditoria.setEntidad("Usuario");
        auditoriaService.create(auditoria);
        return auditoria;
    }

    public void afterLogout(@Observes final PostLoggedOutEvent event) throws IOException {
        auditLogin("logout");

//        Faces.invalidateSession();
//        Faces.redirect("app/init.xhtml");
    }

    public void keepSessionAlive() {
    }

    public String getLoginName() {
        User user = getCurrentUser();
        if (user != null) {
            return user.getLoginName();
        }
        return null;
    }

    public User getCurrentUser() {
        if (identity != null && identity.getAccount() != null) {
            Account account = identity.getAccount();
            User user = (User) account;
            return user;
        }
        return null;
    }

    public void setAudit() {
/*
        User user = getCurrentUser();

        editor.markEditable(user, Zona.class, Torrero.class, TipoFeeder.class, Interviniente.class, TipoAntena.class,
                Sitio.class, Registro.class, Escribano.class, Sede.class, Preventivo.class,
                Nodo3G.class, Celda3G.class, NodoLte.class, CeldaLte.class);
*/
    }
}
