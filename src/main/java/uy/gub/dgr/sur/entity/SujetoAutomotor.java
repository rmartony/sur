package uy.gub.dgr.sur.entity;//
//
//  @ Project : Untitled
//  @ File Name : Celda3G.java
//  @ Date : 11/12/2013
//  @ Author : 
//
//


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"inEx"})
@Audited
@SQLDelete(sql = "update SujetoAutomotor SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class SujetoAutomotor extends Sujeto {

    @ManyToOne(cascade = CascadeType.ALL)
    Automotor automotor;

    private String inEx;

}
