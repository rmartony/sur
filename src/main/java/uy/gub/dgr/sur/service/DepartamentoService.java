package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Departamento;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class DepartamentoService extends DataAccessService<Departamento> {

    public DepartamentoService() {
        super(Departamento.class);
    }
}
