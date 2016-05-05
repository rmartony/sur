package uy.gub.dgr.sur.service;

import uy.gub.dgr.sur.entity.Ip;

import javax.ejb.Stateless;

/**
 * @author rmartony
 */

@Stateless
public class IpService extends DataAccessService<Ip> {

    public IpService() {
        super(Ip.class);
    }
}
