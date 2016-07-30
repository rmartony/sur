package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Tasa;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class TasaService extends DataAccessService<Tasa> {

    public TasaService() {
        super(Tasa.class);
    }
}
