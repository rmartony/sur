package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Localidad;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class LocalidadService extends DataAccessService<Localidad> {

    public LocalidadService() {
        super(Localidad.class);
    }
}
