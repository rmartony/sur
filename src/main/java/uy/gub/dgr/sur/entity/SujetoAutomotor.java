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
@EqualsAndHashCode(callSuper = true, exclude = {"interviniente", "fecha", "anio", "chasis", "inEx", "combustible",
        "motor", "cilindros", "placaMunicipal", "matriculaRegistral", "dua", "tipoAutomotor", "hp"})
@Table(indexes = {@Index(name = "perfis_a1", columnList = "apellido1", unique = false),
        @Index(name = "perfis_a1n1", columnList = "apellido1, nombre1", unique = false)})
@Audited
public class SujetoAutomotor extends Sujeto {

    @ManyToOne
    TipoAutomotor tipoAutomotor;
    @ManyToOne
    private Departamento departamento;
    @ManyToOne
    private Localidad localidad;
    @NotEmpty
    private Integer padron;
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
    private int hp; // caballos de fuerza


}
