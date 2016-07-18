package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Interviniente;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class IntervinienteService extends DataAccessService<Interviniente> {

    public IntervinienteService() {
        super(Interviniente.class);
    }
}
