package uy.gub.dgr.sur.util;

import uy.gub.dgr.sur.entity.*;
import uy.gub.dgr.sur.service.*;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafa on 29/7/2016.
 */
@Singleton
public class EntityInitializer {

    @Inject
    private transient SedeService sedeService;
    @Inject
    private transient ActoService actoService;
    @Inject
    private transient SeccionService seccionService;
    @Inject
    private transient RegistroService registroService;
    @Inject
    private transient EscribanoService escribanoService;
    @Inject
    private transient IntervinienteService intervinienteService;
    @Inject
    private transient CalleService calleService;
    @Inject
    private transient CombustibleService combustibleService;
    @Inject
    private transient DepartamentoService departamentoService;
    @Inject
    private transient ExoneracionService exoneracionService;
    @Inject
    private transient LocalidadService localidadService;
    @Inject
    private transient EstadoService estadoService;
    @Inject
    private transient MarcaService marcaService;
    @Inject
    private transient MovimientoService movimientoService;
    @Inject
    private transient MontoService montoService;
    @Inject
    private transient MonedaService monedaService;
    @Inject
    private transient TasaService tasaService;
    @Inject
    private transient TipoAntenaService tipoAntenaService;
    @Inject
    private transient TipoAutomotorService tipoAutomotorService;
    @Inject
    private transient TipoDocumentoService tipoDocumentoService;
    @Inject
    private transient UsuarioRegistroService usuarioRegistroService;
    @Inject
    private transient ConfiguracionService configuracionService;

    public void init() {
        initSede();
        initRegistroSeccion();
        initEscribano();

    }

    private void initSede() {
        Sede z = new Sede();
        for (int i = 0; i < 5; i++) {
            z.setCodigo(String.valueOf(i));
            z.setDescripcion(z.getClass().getSimpleName() + i);
            sedeService.update(z);
            z = new Sede();
        }
    }

    private void initMarca() {
        Marca z = new Marca();
        for (int i = 0; i < 5; i++) {
            z.setCodigo(String.valueOf(i));
            z.setDescripcion(z.getClass().getSimpleName() + i);
            marcaService.update(z);
            z = new Marca();
        }
    }

    private void initEscribano() {
        Escribano z = new Escribano();
        for (long i = 0; i < 5; i++) {
            z.setCodigo(i);
            z.setNombre(z.getClass().getSimpleName() + i);
            escribanoService.update(z);
            z = new Escribano();
        }
    }

    private void initRegistroSeccion() {
        Registro z = new Registro();
        List<Registro> list = new ArrayList<Registro>(5);

        for (int i = 0; i < 5; i++) {
            z.setCodigo(String.valueOf(i));
            z.setDescripcion(z.getClass().getSimpleName() + i);
            list.add(z);
            z = new Registro();
        }
        registroService.update(list);

        Seccion s = new Seccion();
        s.setCodigo("Seccion 1");
        s.setDescripcion("Descripcion Seccion 1");
        s.setRegistro(list.get(1));
        seccionService.update(s);

        s = new Seccion();
        s.setCodigo("Seccion 2");
        s.setDescripcion("Descripcion Seccion 2");
        s.setRegistro(list.get(4));
        seccionService.update(s);


    }

}
