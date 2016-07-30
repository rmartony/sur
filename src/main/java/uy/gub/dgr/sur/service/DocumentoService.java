package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Documento;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class DocumentoService extends DataAccessService<Documento> {

    public DocumentoService() {
        super(Documento.class);
    }
}
