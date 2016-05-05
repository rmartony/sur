package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.EstadoPreventivo;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class EstadoPreventivoService extends DataAccessService<EstadoPreventivo> {

    public EstadoPreventivoService() {
        super(EstadoPreventivo.class);
    }
}
