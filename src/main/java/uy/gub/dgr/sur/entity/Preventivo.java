package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * User: rmartony
 * Date: 13/12/13
 * Time: 05:21 PM
 */

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"alarmaPuerta", "burletePuerta", "cerraduraPuerta", "baterias",
        "cableadoYConectores", "cableadoYConectorEth", "estadoFiltro", "limpieza", "seguridad", "balizas",
        "cableFeederJumpers", "conectores", "aterramientos", "tma", "rrh", "encintados", "observacionesPrivado",
        "observacionesPublico", "rutaFotos", "urlFotos", "torreros"})
@NamedQueries({
        @NamedQuery(name = Preventivo.BY_NODO_FECHA, query = "SELECT p FROM Preventivo p where p.nodo.id IN :idNodos GROUP BY p.fecha ORDER BY p.fecha DESC")
})
@Audited
public class Preventivo extends BaseEntity {
    public final static String BY_NODO_FECHA = "Preventivo.nodo.id";

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha = new Date();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @NotNull
    private Nodo nodo;

    //@NotNull // TODO: quitar comentario
    @ManyToOne
    private Estado alarmaPuerta;

    //@NotNull
    @ManyToOne
    private Estado burletePuerta;

    //@NotNull
    @ManyToOne
    private Estado cerraduraPuerta;

    //@NotNull
    @ManyToOne
    private Estado baterias;

    //@NotNull
    @ManyToOne
    private Estado cableadoYConectores;

    //@NotNull
    @ManyToOne
    private Estado cableadoYConectorEth;

    //@NotNull
    @ManyToOne
    private Estado estadoFiltro;

    //@NotNull
    @ManyToOne
    private Estado limpieza;

    //@NotNull
    @ManyToOne
    private Estado seguridad;

    //@NotNull
    @ManyToOne
    private Estado balizas;

    //@NotNull
    @ManyToOne
    private Estado cableFeederJumpers;

    //@NotNull
    @ManyToOne
    private Estado conectores;

    //@NotNull
    @ManyToOne
    private Estado aterramientos;

    //@NotNull
    @ManyToOne
    private Estado tma;

    //@NotNull
    @ManyToOne
    private Estado rrh;

    //@NotNull
    @ManyToOne
    private Estado encintados;

    @Lob
    @Column(columnDefinition = "text")
    private String observacionesPrivado;

    @Lob
    @Column(columnDefinition = "text")
    private String observacionesPublico;

    private String tecnico;
    private String rutaFotos;
    @Transient
    private String urlFotos;

    //    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Torrero> torreros;

    public String getFormattedFecha() {
        if (fecha != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");
            return simpleDateFormat.format(fecha);
        }
        return null;
    }

    public int getDaysFromFecha() {

        if (fecha != null) {
            return daysBetween(new Date(), fecha);
        } else {
            return 0;
        }
    }

    private int daysBetween(Date d1, Date d2) {
        return (int) (Math.abs((d2.getTime() - d1.getTime())) / (1000 * 60 * 60 * 24));
    }

    public String getPosfijoRutaFotos() {
        return File.separator + nodo.getSitio().getSigla() + File.separator + "Alcatel" + File.separator + getFormattedFecha() + File.separator + "Fotos" + File.separator + nodo.getSigla();
    }

}


