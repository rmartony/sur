package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.SujetoLibro;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class SujetoLibroService extends DataAccessService<SujetoLibro> {

    public SujetoLibroService() {
        super(SujetoLibro.class);
    }
}
