package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Registro;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class RncService extends DataAccessService<Registro> {

    public RncService() {
        super(Registro.class);
    }
}
