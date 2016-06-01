package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Sede;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class DepartamentoService extends DataAccessService<Sede> {

    public DepartamentoService() {
        super(Sede.class);
    }
}
