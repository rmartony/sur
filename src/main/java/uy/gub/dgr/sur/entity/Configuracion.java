package uy.gub.dgr.sur.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.SubnetUtils;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.net.InetAddress;

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

    /**
     * Dada una dirección IP verifica si pertenece al rango especificado en subredesLocales
     *
     * @param inetAddress dirección IP
     * @return true si inetAddress pertenece al rango especificado en subredesLocales
     */
    public boolean isLocalIp(InetAddress inetAddress) {
        if (inetAddress.isSiteLocalAddress() || inetAddress.isLoopbackAddress()) {
            return true;
        } else {
            if (StringUtils.isNotBlank(subredesLocales)) {
                String[] localSubnetList = subredesLocales.split(";");
                for (String subnet : localSubnetList) {
                    SubnetUtils subnetUtils = new SubnetUtils(subnet);
                    SubnetUtils.SubnetInfo info = subnetUtils.getInfo();

                    if (info.isInRange(inetAddress.getHostAddress())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
