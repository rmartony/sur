package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Celda3G;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class Celda3GService extends DataAccessService<Celda3G> {

    public Celda3GService() {
        super(Celda3G.class);
    }
}
