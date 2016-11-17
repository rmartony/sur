package uy.gub.dgr.sur.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by rmartony on 01/11/2016.
 */
@Embeddable
@Data
public class DeleteInfo {
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaBaja;
    private String motivoBaja;

    public DeleteInfo() {
    }

    public DeleteInfo(Date fechaBaja, String motivoBaja) {
        this.fechaBaja = fechaBaja;
        this.motivoBaja = motivoBaja;
    }

}
