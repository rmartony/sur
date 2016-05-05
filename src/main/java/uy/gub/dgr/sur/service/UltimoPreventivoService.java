package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.UltimoPreventivo;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class UltimoPreventivoService extends DataAccessService<UltimoPreventivo> {

    public UltimoPreventivoService() {
        super(UltimoPreventivo.class);
    }
}
