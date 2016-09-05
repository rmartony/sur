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
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"cedulaIdentidad", "interviniente", "fecha", "fechaTipo", "parte", "inEx", "nacionalidad",
        "prefesion", "domicilio", "nupcias", "estadoCivil", "conyuge", "clase", "numeroEscribano"})
@Audited
public class SujetoPersonaFisica extends Sujeto {

    @ManyToOne
    private PersonaFisica personaFisica;

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


}
