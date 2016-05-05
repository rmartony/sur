package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Configuracion;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class ConfiguracionService extends DataAccessService<Configuracion> {

    public ConfiguracionService() {
        super(Configuracion.class);
    }
}
