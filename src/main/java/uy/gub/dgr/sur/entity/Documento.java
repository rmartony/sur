package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * User: rmartony
 * Date: 11/12/13
 * Time: 03:25 PM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"estado", "tipoDocumento"})
@NamedQueries({
        @NamedQuery(name = Documento.ID, query = "SELECT d FROM Documento d where d.id = :id"),
        @NamedQuery(name = Documento.ALL, query = "SELECT d FROM Documento d order by d.fecha desc")
})
@Cacheable
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"registro_id", "sede_id", "anio", "numero", "bis"}))
@Audited
public class Documento extends BaseEntity {
    public final static String ID = "Documento.id";
    public final static String ALL = "Documento.all";
    private String ficha;
    @Temporal(TemporalType.DATE)
    private Date fechaResolucion;
    private String autos;
    private String observacion;
    private String observacionDgr;
    @ManyToOne
    @NotNull
    private Tasa tasa;
    @ManyToOne
    private Exoneracion exoneracion;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "documento")
    private List<Inscripcion> inscripcionList;
    @NotNull
    @ManyToOne
    private Registro registro;
    @NotNull
    @ManyToOne
    private Sede sede;
    private int anio;
    private int numero;
    private short bis;
    @Temporal(TemporalType.DATE)
    private Date fecha; // por omisión fecha actual
    @ManyToOne
    private Estado estado;
    @ManyToOne
    private TipoDocumento tipoDocumento;
    private short libro;
    private int folio;
    private short fb;
    private int otroNumero;
    @ManyToOne
    private Emisor emisor;
    @ManyToOne
    private Escribano escribano;
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date fechaEmision;



}
