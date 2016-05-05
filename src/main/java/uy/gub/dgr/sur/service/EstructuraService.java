package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Estructura;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class EstructuraService extends DataAccessService<Estructura> {

    public EstructuraService() {
        super(Estructura.class);
    }
}
