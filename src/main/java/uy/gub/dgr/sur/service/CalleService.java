package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Calle;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class CalleService extends DataAccessService<Calle> {

    public CalleService() {
        super(Calle.class);
    }
}
