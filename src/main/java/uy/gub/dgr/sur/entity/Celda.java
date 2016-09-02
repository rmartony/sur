package uy.gub.dgr.sur.entity;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Celda.java
//  @ Date : 11/12/2013
//  @ Author : 
//
//


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_celda")
@EqualsAndHashCode(callSuper = true, exclude = {"sector", "numAntenas", "altura", "azimut", "tiltM", "tiltE", "tipoFO", "ubicacion", "antenaCompartida", "tipoAntena"})
@Data
@Audited
public abstract class Celda extends BaseEntity {

    private Integer idCelda;

    private String sector;

    private Integer numAntenas;

    private Double altura;

    private Double azimut;

    private Integer tiltM;

    private Integer tiltE;

    private String tipoFO;

    private String ubicacion;

    private String antenaCompartida;
    @ManyToOne

    private TipoAntena tipoAntena;

}
