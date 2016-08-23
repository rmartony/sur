package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.LibroRubrica;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class LibroRubricaService extends DataAccessService<LibroRubrica> {

    public LibroRubricaService() {
        super(LibroRubrica.class);
    }
}
