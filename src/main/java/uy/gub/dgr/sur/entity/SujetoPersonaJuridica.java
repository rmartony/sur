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

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"interviniente", "fecha", "rut", "bps", "naturalezaJuridica",
        "tipo", "tipoSocial", "parte", "domicilio", "sede"})
@Table(indexes = {@Index(name = "perjur_rut", columnList = "rut", unique = true)})
@Audited
public class SujetoPersonaJuridica extends Sujeto {

    @ManyToOne
    private PersonaJuridica personaJuridica;

    @Column(length = 12)
    @Size(min = 12, max = 12)
    private String rut;


    @NotNull
    @ManyToOne
    private Interviniente interviniente;

    private int bps;
    private String naturalezaJuridica;

    @Temporal(TemporalType.DATE)
    private Date fecha;
    private String tipo; // Constitucion, fallecimiento, mandato, nacimiento
    @ManyToOne
    private PersonaJuridicaTipoSocial tipoSocial; //giro
    private String parte;

    @ManyToOne
    private Departamento domicilio;

    private String sede;

}
