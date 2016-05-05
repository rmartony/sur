package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.TipoFeeder;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class TipoFeederService extends DataAccessService<TipoFeeder> {

    public TipoFeederService() {
        super(TipoFeeder.class);
    }
}
