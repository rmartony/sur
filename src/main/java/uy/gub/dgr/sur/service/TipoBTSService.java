package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.TipoBTS;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class TipoBTSService extends DataAccessService<TipoBTS> {

    public TipoBTSService() {
        super(TipoBTS.class);
    }
}
