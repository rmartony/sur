package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Emisor;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class EmisorService extends DataAccessService<Emisor> {

    public EmisorService() {
        super(Emisor.class);
    }
}
