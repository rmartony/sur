package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Preventivo;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class PreventivoService extends DataAccessService<Preventivo> {

    public PreventivoService() {
        super(Preventivo.class);
    }
}
