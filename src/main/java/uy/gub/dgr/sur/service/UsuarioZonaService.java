package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.UsuarioZona;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class UsuarioZonaService extends DataAccessService<UsuarioZona> {

    public UsuarioZonaService() {
        super(UsuarioZona.class);
    }
}
