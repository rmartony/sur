package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Zona;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class ZonaService extends DataAccessService<Zona> {

    public ZonaService() {
        super(Zona.class);
    }
}
