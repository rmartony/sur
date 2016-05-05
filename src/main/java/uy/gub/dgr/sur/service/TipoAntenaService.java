package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.TipoAntena;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class TipoAntenaService extends DataAccessService<TipoAntena> {

    public TipoAntenaService() {
        super(TipoAntena.class);
    }
}
