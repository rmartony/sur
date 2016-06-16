package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"registro", "sede", "anio", "numero", "bis"}))
@Audited
public class Documento extends BaseEntity implements Serializable {
    public final static String ID = "Documento.id";
    public final static String ALL = "Documento.all";
    String ficha;
    Date fechaResolucion;
    String autos;
    String observaciones;
    String observacionesDgr;
    @ManyToOne
    @NotNull
    Tasa tasa;
    @ManyToOne
    Exoneracion exoneracion;
    @ManyToOne
    private Registro registro;
    @ManyToOne
    private Sede sede;
    private int anio;
    private int numero;
    private short bis;
    private Date fecha;
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

}
