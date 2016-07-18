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
import java.sql.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"interviniente", "fecha", "fechaTipo", "parte", "inEx", "nacionalidad",
        "prefesion", "domicilio", "nupcias", "estadoCivil", "conyuge", "clase", "numeroEscribano", "observaciones"})
@Audited
public class SujetoPersonaFisica extends Sujeto {

    private String interviniente;

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
