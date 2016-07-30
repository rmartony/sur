package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.SujetoAutomotor;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class SujetoAutomotorService extends DataAccessService<SujetoAutomotor> {

    public SujetoAutomotorService() {
        super(SujetoAutomotor.class);
    }
}
