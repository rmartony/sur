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
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {
        "nivel"})
@Table(indexes = {@Index(name = "dep_pad", columnList = "padron, departamento_id, localidad_id, unidad", unique = true)})
@Audited
public class Padron extends BaseEntity {

    @ManyToOne
    private Departamento departamento;

    @ManyToOne
    private Localidad localidad;

    @NotNull
    private Integer padron;

    private String block;

    @ManyToOne
    private PadronNivel nivel; //ep, ss, es

    private String unidad;

}
