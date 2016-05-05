package uy.gub.dgr.sur.event;

import lombok.Getter;
import lombok.Setter;
import uy.gub.dgr.sur.entity.Celda;

/**
 * User: rmartony
 * Date: 14/01/14
 * Time: 09:48 AM
 */
public class CeldaEvent {
    @Getter
    @Setter
    private Celda celda;

    public CeldaEvent(Celda celda) {
        this.celda = celda;
    }
}
