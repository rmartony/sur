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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"cedulaIdentidad", "interviniente", "fecha", "fechaTipo", "parte", "inEx", "nacionalidad",
        "prefesion", "domicilio", "nupcias", "estadoCivil", "conyuge", "clase", "numeroEscribano"})
@Audited
@SQLDelete(sql = "update SujetoPersonaFisica SET fechaBaja = current_date where id = ?")
@Where(clause = "fechaBaja is null")
public class SujetoPersonaFisica extends Sujeto {

    @ManyToOne
    private PersonaFisica personaFisica;

    @Column(length = 9)
    @Size(min = 6, max = 9)
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
