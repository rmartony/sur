package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Sede;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class SedeService extends DataAccessService<Sede> {

    public SedeService() {
        super(Sede.class);
    }
}
