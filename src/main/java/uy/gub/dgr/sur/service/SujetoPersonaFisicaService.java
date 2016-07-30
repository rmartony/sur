package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.SujetoPersonaFisica;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class SujetoPersonaFisicaService extends DataAccessService<SujetoPersonaFisica> {

    public SujetoPersonaFisicaService() {
        super(SujetoPersonaFisica.class);
    }
}
