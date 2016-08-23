package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

/**
 * User: rmartony
 * Date: 19/02/14
 * Time: 08:31 PM
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NamedQueries({
        @NamedQuery(name = Configuracion.ALL, query = "SELECT c FROM Configuracion c"),
        @NamedQuery(name = Configuracion.BY_PARAMETRO, query = "SELECT c FROM Configuracion c"),
        @NamedQuery(name = Configuracion.TOTAL, query = "SELECT COUNT(z) FROM Configuracion z")
})
public class Configuracion extends BaseEntity implements Serializable {
    public final static String ALL = "Configuracion.all";
    public final static String BY_PARAMETRO = "Configuracion.parametro";
    public final static String TOTAL = "Configuracion.id";

    private String prefijoRutaMontajeFotos; // por ejemplo /mnt/fotos
    private String prefijoUrlInternaFotos; // \\servidorInterno
    private String prefijoUrlExternaFotos; // \\servidorExterno
    private String subredesLocales; // Ejemplo: 192.168.0.1/16;128.251.166.252/24

}
