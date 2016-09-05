package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.ModeloAutomotor;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class ModeloService extends DataAccessService<ModeloAutomotor> {

    public ModeloService() {
        super(ModeloAutomotor.class);
    }
}
