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
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"numeroInscripcion", "tipoLibro", "folio", "tomo", "nombreLibro", "fichas",
        "usuario", "fecha"})
@Audited
@SQLDelete(sql = "update SujetoLibro SET fechaBaja = CURRENT_TIMESTAMP where id = ?")
@Where(clause = "fechaBaja is null")
public class SujetoLibro extends Sujeto {

    @NotNull
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
