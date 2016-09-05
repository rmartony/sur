package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.MarcaAutomotor;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class MarcaService extends DataAccessService<MarcaAutomotor> {

    public MarcaService() {
        super(MarcaAutomotor.class);
    }
}
