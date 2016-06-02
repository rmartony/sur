package uy.gub.dgr.sur.controller;

/**
 * User: rmartony
 * Date: 19/12/13
 * Time: 04:53 PM
 */

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;
import uy.gub.dgr.sur.idm.AuthorizationChecker;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
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
public class PreventivoReportController extends BaseController {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private LoginController loginController;
    @Inject
    private transient AuthorizationChecker authorizationChecker;
    @Getter
    @Setter
    private Date fechaDesde = DateUtils.addMonths(new Date(), -1);

    @Getter
    @Setter
    private Date fechaHasta = new Date();

    @Getter
    @Setter
    private boolean rolTecnico = false;

    /**
     * Default constructor
     */
    public PreventivoReportController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        String username = null;
        log.log(Level.INFO, "PreventivoReportController is initializing");
        if (authorizationChecker.isVerificacion()) {
            rolTecnico = true;
            username = loginController.getLoginName();
        }
    }

}