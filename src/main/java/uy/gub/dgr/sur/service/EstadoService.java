package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Estado;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class EstadoService extends DataAccessService<Estado> {

    public EstadoService() {
        super(Estado.class);
    }
}
