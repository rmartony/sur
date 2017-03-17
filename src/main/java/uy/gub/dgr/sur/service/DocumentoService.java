package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Documento;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.Map;

/**
 * @author rmartony
 */

@Stateless
public class DocumentoService extends DataAccessService<Documento> {

    public DocumentoService() {
        super(Documento.class);
    }

    public boolean existsDocumento(Documento documento) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("registro", documento.getRegistro().getCodigo());
        parameters.put("sede", documento.getSede().getCodigo());
        parameters.put("anio", documento.getAnio());
        parameters.put("numero", documento.getNumero());
        parameters.put("bis", documento.getBis());

        return existsResultNamedQuery(Documento.BY_KEY, parameters);
    }
}
