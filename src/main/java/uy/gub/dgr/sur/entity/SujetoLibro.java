package uy.gub.dgr.sur.entity;//
//
//  @ Project : Untitled
//  @ File Name : Celda3G.java
//  @ Date : 11/12/2013
//  @ Author : rmartony
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
@EqualsAndHashCode(callSuper = true, exclude = {"numeroInscripcion", "tipoLibro", "folio", "tomo", "nombreLibro", "fichas",
        "usuario", "fecha"})
@Audited
public class SujetoLibro extends Sujeto {

    @NotEmpty
    private Integer numeroInscripcion;
    @ManyToOne
    private LibroRubricaTipo tipoLibro;
    private Integer folio;
    private Integer tomo;
    @ManyToOne
    private LibroRubrica nombreLibro;
    private Integer fichas;
    private String usuario;
    private Date fecha;

}
