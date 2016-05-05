package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Torrero;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class TorreroService extends DataAccessService<Torrero> {

    public TorreroService() {
        super(Torrero.class);
    }
}
