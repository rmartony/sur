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
@EqualsAndHashCode(callSuper = true, exclude = {"interviniente", "fecha", "fechaTipo", "parte", "inEx", "nacionalidad",
        "prefesion", "domicilio", "nupcias", "estadoCivil", "conyuge", "clase", "numeroEscribano", "observaciones"})
@Table(indexes = {@Index(name = "perfis_a1", columnList = "apellido1", unique = false),
        @Index(name = "perfis_a1n1", columnList = "apellido1, nombre1", unique = false)})
@Audited
public class SujetoPersonaFisica extends Sujeto {

    @NotEmpty
    private String apellido1;
    private String apellido2;
    @NotEmpty
    private String nombre1;
    private String nombre2;

    @NotEmpty
    private String cedulaIdentidad;

    @ManyToOne
    private Interviniente interviniente;

    private Date fecha;
    private String fechaTipo;

    private String parte;
    private String inEx;
    private String nacionalidad;
    private String prefesion;
    private boolean domicilio;

    private short nupcias;
    private String estadoCivil;
    private String conyuge;

    private String clase;
    private int numeroEscribano;

    private String observaciones;


}
