package uy.gub.dgr.sur.controller;

/**
 * User: rmartony
 * Date: 19/12/13
 * Time: 04:53 PM
 */

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Messages;
import uy.gub.dgr.sur.entity.Configuracion;
import uy.gub.dgr.sur.idm.annotations.Admin;
import uy.gub.dgr.sur.service.ConfiguracionService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * User Controller class allows users to do CRUD operations
 *
 * @author rmartony
 */

@Named
@ApplicationScoped
public class ConfiguracionController implements Serializable {
    private
    @Inject
    transient Logger log;
    @Inject
    private transient ResourceBundle msg;
    @Inject
    private transient ConfiguracionService das;
    // Selected users that will be removed

    @Getter
    @Setter
    private Configuracion configuracion = new Configuracion();

    /**
     * Default constructor
     */
    public ConfiguracionController() {
    }

    /**
     * Initializing Data Access Service for LazyUserDataModel class
     * role list for UserContoller class
     */
    @PostConstruct
    public void init() {
        log.log(Level.INFO, "ConfiguracionController is initializing");
        List<Configuracion> configuracionList = das.findWithNamedQuery(Configuracion.ALL);
        if (CollectionUtils.isNotEmpty(configuracionList)) {
            configuracion = configuracionList.get(0);
        }
    }

    /**
     * @param actionEvent
     */
    @Admin
    public void doConfirmUpdate() {
        String subredesLocales = configuracion.getSubredesLocales();
        if (StringUtils.isNotEmpty(subredesLocales)) {
            String[] localSubnetList = subredesLocales.split(";");

            Pattern pattern = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])(\\/(\\d|[1-2]\\d|3[0-2]))?$");
            for (String subnet : localSubnetList) {
                Matcher matcher = pattern.matcher(subnet);
                if (!matcher.find()) {
                    Messages.addFlashError("redLocal", "Error al interpretar el campo de subredes " + subredesLocales);
                    return;
                }
            }

        }
        das.update(configuracion);
        Messages.addFlashInfo(null, "Configuración actualizada con éxito.");
    }

    public String getCarpetaAlmacenajeFotos() {
        return configuracion.getPrefijoRutaMontajeFotos() + File.separator + "<sitio>" + File.separator + "ddmmaaaa" + File.separator + "Fotos" + File.separator + "<nodo>";
    }

}