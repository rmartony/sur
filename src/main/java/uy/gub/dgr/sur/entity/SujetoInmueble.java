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
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {
        "seccionJudicial", "cuotaParte", "interviniente", "fecha", "anio", "chasis", "inEx", "combustible",
        "motor", "cilindros", "placaMunicipal", "matriculaRegistral", "dua", "calle", "numeroCalle", "naturalezaJuridica"})
@Table(indexes = {@Index(name = "dep_pad", columnList = "padron, departamento", unique = true)})
@Audited
public class SujetoInmueble extends Sujeto {

    @ManyToOne
    private Departamento departamento;

    @ManyToOne
    private Localidad localidad;

    @NotEmpty
    private Integer padron;

    private String block;
    private String nivel; //ep, ss, es
    private String unidad;

    private String seccionJudicial;
    private String cuotaParte;

    @ManyToOne
    private Interviniente interviniente;

    private Date fecha;
    private int anio;
    private String chasis;
    private String inEx;

    @ManyToOne
    private Combustible combustible;

    private String motor;
    private short cilindros;
    private String placaMunicipal; // matricula
    private int matriculaRegistral;
    private int dua;

    private String domicilio;
    @ManyToOne
    private Calle calle;
    private short numeroCalle;
    private String naturalezaJuridica;

}
