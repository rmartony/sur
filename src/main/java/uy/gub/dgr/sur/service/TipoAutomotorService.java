package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.TipoAutomotor;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class TipoAutomotorService extends DataAccessService<TipoAutomotor> {

    public TipoAutomotorService() {
        super(TipoAutomotor.class);
    }
}
