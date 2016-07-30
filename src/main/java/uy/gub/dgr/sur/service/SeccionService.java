package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Seccion;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class SeccionService extends DataAccessService<Seccion> {

    public SeccionService() {
        super(Seccion.class);
    }
}
