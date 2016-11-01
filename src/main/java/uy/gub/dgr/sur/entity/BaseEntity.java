package uy.gub.dgr.sur.entity;

/**
 * User: rmartony
 * Date: 18/12/13
 * Time: 11:14 AM
 */

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Super Entity class
 *
 * @author rmartony
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    @Embedded
    private DeleteInfo deleteInfo;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        } else if (!(obj instanceof BaseEntity)) {
            return false;
        } else {
            final BaseEntity baseEntity = (BaseEntity) obj;
            if (id != null && baseEntity.id.equals(this.id)) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public String toString() {
        return "entity." + this.getClass() + "[ id=" + id + " ] ";
    }
}
