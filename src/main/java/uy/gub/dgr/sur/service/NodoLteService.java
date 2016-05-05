package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.NodoLte;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class NodoLteService extends DataAccessService<NodoLte> {

    public NodoLteService() {
        super(NodoLte.class);
    }
}
