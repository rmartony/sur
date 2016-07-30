package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Marca;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class MarcaService extends DataAccessService<Marca> {

    public MarcaService() {
        super(Marca.class);
    }
}
