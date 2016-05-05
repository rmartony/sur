package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Nodo3G;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class Nodo3GService extends DataAccessService<Nodo3G> {

    public Nodo3GService() {
        super(Nodo3G.class);
    }
}
