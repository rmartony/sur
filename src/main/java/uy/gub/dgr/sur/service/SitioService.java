package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Sitio;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class SitioService extends DataAccessService<Sitio> {

    public SitioService() {
        super(Sitio.class);
    }
}
