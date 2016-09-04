package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.UsuarioRegistro;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class UsuarioRegistroService extends DataAccessService<UsuarioRegistro> {

    public UsuarioRegistroService() {
        super(UsuarioRegistro.class);
    }
}
