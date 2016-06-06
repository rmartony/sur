package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Estado;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class EstadoPreventivoService extends DataAccessService<Estado> {

    public EstadoPreventivoService() {
        super(Estado.class);
    }
}
