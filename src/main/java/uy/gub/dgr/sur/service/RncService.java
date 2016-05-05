package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Rnc;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class RncService extends DataAccessService<Rnc> {

    public RncService() {
        super(Rnc.class);
    }
}
