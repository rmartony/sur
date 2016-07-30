package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Monto;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class MontoService extends DataAccessService<Monto> {

    public MontoService() {
        super(Monto.class);
    }
}
