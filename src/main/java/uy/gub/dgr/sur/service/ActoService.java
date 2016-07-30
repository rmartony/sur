package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Acto;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class ActoService extends DataAccessService<Acto> {

    public ActoService() {
        super(Acto.class);
    }
}
