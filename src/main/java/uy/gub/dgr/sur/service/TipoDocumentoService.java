package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.TipoDocumento;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class TipoDocumentoService extends DataAccessService<TipoDocumento> {

    public TipoDocumentoService() {
        super(TipoDocumento.class);
    }
}
