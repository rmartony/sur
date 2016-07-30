package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Movimiento;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class MovimientoService extends DataAccessService<Movimiento> {

    public MovimientoService() {
        super(Movimiento.class);
    }
}
