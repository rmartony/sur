package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Inscripcion;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class InscripcionService extends DataAccessService<Inscripcion> {

    public InscripcionService() {
        super(Inscripcion.class);
    }
}
