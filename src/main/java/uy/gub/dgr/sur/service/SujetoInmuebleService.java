package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.SujetoInmueble;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class SujetoInmuebleService extends DataAccessService<SujetoInmueble> {

    public SujetoInmuebleService() {
        super(SujetoInmueble.class);
    }
}
