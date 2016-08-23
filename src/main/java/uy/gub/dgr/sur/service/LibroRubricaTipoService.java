package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.LibroRubricaTipo;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class LibroRubricaTipoService extends DataAccessService<LibroRubricaTipo> {

    public LibroRubricaTipoService() {
        super(LibroRubricaTipo.class);
    }
}
