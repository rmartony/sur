package uy.gub.dgr.sur.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.util.Date;

/**
 * Created by rmartony on 01/11/2016.
 */
@Embeddable
@Data
public class DeleteInfo {
    private Date fechaBaja;
    private String motivoBaja;

    public DeleteInfo() {
    }

    public DeleteInfo(Date fechaBaja, String motivoBaja) {
        this.fechaBaja = fechaBaja;
        this.motivoBaja = motivoBaja;
    }

}
