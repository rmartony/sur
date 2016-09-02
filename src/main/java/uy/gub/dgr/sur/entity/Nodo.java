package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * User: rmartony
 * Date: 29/11/13
 * Time: 06:34 PM
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_nodo")
@EqualsAndHashCode(callSuper = true,
        exclude = {"fechaAlta", "fechaInstalacion", "alimentacion", "ipOAM", "maskOAM", "gwOAM", "vlanOAM",
                "ipTelecom", "maskTelecom", "gwTelecom", "vlanTelecom", "ipGrandMaster", "ipSecondaryMaster",
                "tinco", "EPLFlujoDABT", "observaciones", "numTorreros", "acceso", "llaveMovistar",
                "llaveUnificada", "llaveExteriorAlcatel", "llaveExteriorEricsson", "llaveSitio", "tarjetaProximidad",
                "accesoTormenta", "numContadorUTE", "sitio", "interviniente"})
@Data
@NamedQueries({
        @NamedQuery(name = Nodo.BY_SITIO_ID, query = "SELECT n FROM Nodo n where n.sitio.id = :idSitio"),
        @NamedQuery(name = Nodo.BY_SITIO_ZONA_ID, query = "SELECT n FROM Nodo n where n.sitio.zona.id IN :idZonas"),
        @NamedQuery(name = Nodo.EXISTS_IN_PREVENTIVO, query = "SELECT p FROM Preventivo p where p.nodo IN :nodoList")
})
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"sigla"}))
@Audited
public abstract class Nodo extends BaseEntity {
    public final static String BY_SITIO_ID = "Nodo.sitio.id";
    public final static String BY_SITIO_ZONA_ID = "Nodo.sitio.zona.id";
    public final static String EXISTS_IN_PREVENTIVO = "existsInPreventivo";
    @NotEmpty
    private String sigla;
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlta = new Date();
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInstalacion = new Date();
    private Integer alimentacion;
    //@Pattern(regexp = Escribano.REGEXP_VALIDATE, message = "{msg.invalidIP}")
    private String ipOAM;
    private String maskOAM;
    //@Pattern(regexp = Escribano.REGEXP_VALIDATE, message = "{msg.invalidIP}")
    private String gwOAM;
    //@Pattern(regexp = Escribano.REGEXP_VALIDATE, message = "{msg.invalidIP}")
    private Integer vlanOAM;
    private String ipTelecom;
    //@Pattern(regexp = Escribano.REGEXP_VALIDATE, message = "{msg.invalidIP}")
    private String maskTelecom;
    //@Pattern(regexp = Escribano.REGEXP_VALIDATE, message = "{msg.invalidIP}")
    private String gwTelecom;
    //@Pattern(regexp = Escribano.REGEXP_VALIDATE, message = "{msg.invalidIP}")
    private Integer vlanTelecom;
    private String ipGrandMaster;
    //@Pattern(regexp = Escribano.REGEXP_VALIDATE, message = "{msg.invalidIP}")
    private String ipSecondaryMaster;
    //@Pattern(regexp = Escribano.REGEXP_VALIDATE, message = "{msg.invalidIP}")
    private String tinco;
    private String EPLFlujoDABT;
    @Lob
    @Column(columnDefinition = "text")
    private String observaciones;
    private String numTorreros;
    @Lob
    @Column(columnDefinition = "text")
    private String acceso;
    private boolean llaveMovistar;
    private boolean llaveUnificada; // debería ser String?
    private boolean llaveExteriorAlcatel;
    private boolean llaveExteriorEricsson;
    private boolean llaveSitio;
    private boolean tarjetaSitio;
    private boolean tarjetaProximidad;
    private boolean accesoTormenta;
    private String numContadorUTE;

    @ManyToOne
    //@NotNull // TODO: quitar comentario
    private Sitio sitio;

    @ManyToOne
    //@NotNull
    private Interviniente interviniente;

    @Transient
    public String getDiscriminatorValue() {
        DiscriminatorValue val = this.getClass().getAnnotation(DiscriminatorValue.class);

        return val == null ? null : val.value();
    }

}
