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

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {
        "seccionJudicial", "cuotaParte", "interviniente", "inEx", "cota", "area", "extension",
        "domicilio", "calle", "numeroCalle", "naturalezaJuridica"})
@Table(indexes = {@Index(name = "dep_pad", columnList = "inmueble_id", unique = true)})
@Audited
@SQLDelete(sql = "update SujetoInmueble SET fechaBaja = current_date where id = ?")
@Where(clause = "fechaBaja is null")
public class SujetoInmueble extends Sujeto {

    @ManyToOne
    private Inmueble inmueble;

    private String seccionJudicial;
    private String cuotaParte;

    @ManyToOne
    private Interviniente interviniente;

    private String inEx;

    private String cota;
    private Double area;
    private String extension;

    private String domicilio;
    @ManyToOne
    private Calle calle;
    private short numeroCalle;
    private String naturalezaJuridica;

}
