package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.CeldaLte;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class CeldaLteService extends DataAccessService<CeldaLte> {

    public CeldaLteService() {
        super(CeldaLte.class);
    }
}
