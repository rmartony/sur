package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;
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
        "observacionesPublico", "tecnico", "rutaFotos", "urlFotos", "torreros"})
@NamedQueries({
        @NamedQuery(name = Preventivo.BY_NODO_FECHA, query = "SELECT p FROM Preventivo p where p.nodo.id IN :idNodos GROUP BY p.fecha ORDER BY p.fecha DESC")
})
@Audited
public class Preventivo extends BaseEntity implements Serializable {
    public final static String BY_NODO_FECHA = "Preventivo.nodo.id";

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha = new Date();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @NotNull
    private Nodo nodo;

    //@NotNull // TODO: quitar comentario
    @ManyToOne
    private EstadoPreventivo alarmaPuerta;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo burletePuerta;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo cerraduraPuerta;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo baterias;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo cableadoYConectores;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo cableadoYConectorEth;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo estadoFiltro;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo limpieza;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo seguridad;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo balizas;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo cableFeederJumpers;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo conectores;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo aterramientos;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo tma;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo rrh;

    //@NotNull
    @ManyToOne
    private EstadoPreventivo encintados;

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


