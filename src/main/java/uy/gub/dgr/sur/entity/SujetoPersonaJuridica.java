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
@EqualsAndHashCode(callSuper = true, exclude = {"interviniente", "fecha", "nombre", "bps", "naturalezaJuridica",
        "tipo", "tipoSocial", "parte", "domicilio", "sede"})
@Table(indexes = {@Index(name = "perjur_rut", columnList = "rut", unique = true)})
@Audited
public class SujetoPersonaJuridica extends Sujeto {

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String rut;

    @ManyToOne
    private Interviniente interviniente;

    private int bps;
    private String naturalezaJuridica;

    private Date fecha;
    private String tipo; // Constitucion, fallecimiento, mandato, nacimiento
    private String tipoSocial;
    private String parte;
    private boolean domicilio;

    private short sede;

}
