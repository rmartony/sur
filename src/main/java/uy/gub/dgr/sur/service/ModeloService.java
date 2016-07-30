package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Modelo;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class ModeloService extends DataAccessService<Modelo> {

    public ModeloService() {
        super(Modelo.class);
    }
}
