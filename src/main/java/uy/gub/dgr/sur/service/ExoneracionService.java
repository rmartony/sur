package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Exoneracion;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class ExoneracionService extends DataAccessService<Exoneracion> {

    public ExoneracionService() {
        super(Exoneracion.class);
    }
}
