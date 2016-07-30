package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Combustible;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class CombustibleService extends DataAccessService<Combustible> {

    public CombustibleService() {
        super(Combustible.class);
    }
}
