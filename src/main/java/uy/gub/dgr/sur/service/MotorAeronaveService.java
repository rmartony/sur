package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.MotorAeronave;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class MotorAeronaveService extends DataAccessService<MotorAeronave> {

    public MotorAeronaveService() {
        super(MotorAeronave.class);
    }
}
