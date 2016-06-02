package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Escribano;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class EscribanoService extends DataAccessService<Escribano> {

    public EscribanoService() {
        super(Escribano.class);
    }
}
