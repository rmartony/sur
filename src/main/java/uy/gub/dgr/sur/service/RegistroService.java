package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Registro;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class RegistroService extends DataAccessService<Registro> {

    public RegistroService() {
        super(Registro.class);
    }
}
