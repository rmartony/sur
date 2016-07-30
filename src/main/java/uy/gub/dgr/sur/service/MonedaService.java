package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Moneda;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class MonedaService extends DataAccessService<Moneda> {

    public MonedaService() {
        super(Moneda.class);
    }
}
