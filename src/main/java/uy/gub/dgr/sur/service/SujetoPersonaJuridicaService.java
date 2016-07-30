package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.SujetoPersonaJuridica;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class SujetoPersonaJuridicaService extends DataAccessService<SujetoPersonaJuridica> {

    public SujetoPersonaJuridicaService() {
        super(SujetoPersonaJuridica.class);
    }
}
