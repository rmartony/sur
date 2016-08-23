package uy.gub.dgr.sur.service;

import org.apache.commons.collections.CollectionUtils;
import uy.gub.dgr.sur.entity.*;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.List;

/**
 * Created by rafa on 22/8/2016.
 */
@Singleton
public class InitService {
    @Inject
    private transient SedeService sedeService;
    @Inject
    private transient DepartamentoService departamentoService;
    @Inject
    private transient EstructuraService estructuraService;
    @Inject
    private transient RegistroService registroService;
    @Inject
    private transient SeccionService seccionService;
    @Inject
    private transient ActoService actoService;
    @Inject
    private transient EscribanoService escribanoService;
    @Inject
    private transient IntervinienteService intervinienteService;
    @Inject
    private transient EstadoService estadoService;
    @Inject
    private transient ConfiguracionService configuracionService;
    @Inject
    private transient CombustibleService combustibleService;
    @Inject
    private transient EmisorService emisorService;

    public void initDB() {
        final boolean initialized = initialized();
        if (!initialized) {
            initSedes();
            initDepartamentos();
            initInterviniente();
            initEstado();
            initConfiguracion();
            initRegistros();
            initEscribanos();
            initCombustible();
            initEmisor();
        }
    }

    private boolean initialized() {
        final List sedeList = sedeService.findWithNamedQuery(Sede.ALL, 1);
        return CollectionUtils.isNotEmpty(sedeList);
    }

    private void initConfiguracion() {
        Configuracion configuracion = new Configuracion();
        configuracion.setPrefijoRutaMontajeFotos("prueba");
        configuracion.setPrefijoUrlExternaFotos("dgr.gub.uy");
        configuracion.setPrefijoRutaMontajeFotos("fotos");
        configuracionService.create(configuracion);
    }

    private void initSedes() {
        Sede sede = new Sede();
        sede.setId(1);
        sede.setDescripcion("Montevideo");
        sedeService.update(sede);
        sede.setId(2);
        sede.setDescripcion("Paysandú");
        sedeService.update(sede);
        sede.setId(3);
        sede.setDescripcion("Colonia");
        sedeService.update(sede);
        sede.setId(4);
        sede.setDescripcion("Durazno");
        sedeService.update(sede);
        sede.setId(5);
        sede.setDescripcion("Maldonado");
        sedeService.update(sede);
        sede.setId(6);
        sede.setDescripcion("Canelones");
        sedeService.update(sede);
        sede.setId(7);
        sede.setDescripcion("Pando");
        sedeService.update(sede);
        sede.setId(8);
        sede.setDescripcion("San José");
        sedeService.update(sede);
        sede.setId(9);
        sede.setDescripcion("Rocha");
        sedeService.update(sede);
        sede.setId(10);
        sede.setDescripcion("Soriano");
        sedeService.update(sede);
        sede.setId(11);
        sede.setDescripcion("Salto");
        sedeService.update(sede);
        sede.setId(12);
        sede.setDescripcion("Artigas");
        sedeService.update(sede);
        sede.setId(13);
        sede.setDescripcion("Florida");
        sedeService.update(sede);
        sede.setId(14);
        sede.setDescripcion("Río Negro");
        sedeService.update(sede);
        sede.setId(15);
        sede.setDescripcion("Rivera");
        sedeService.update(sede);
        sede.setId(16);
        sede.setDescripcion("Flores");
        sedeService.update(sede);
        sede.setId(17);
        sede.setDescripcion("Cerro Largo");
        sedeService.update(sede);
        sede.setId(18);
        sede.setDescripcion("Treinta y Tres");
        sedeService.update(sede);
        sede.setId(19);
        sede.setDescripcion("Lavalleja");
        sedeService.update(sede);
        sede.setId(20);
        sede.setDescripcion("Tacuarembó");
        sedeService.update(sede);
    }

    private void initDepartamentos() {
        Departamento departamento = new Departamento();
        departamento.setId(1);
        departamento.setDescripcion("Montevideo");
        departamentoService.update(departamento);
        departamento.setId(2);
        departamento.setDescripcion("Paysandú");
        departamentoService.update(departamento);
        departamento.setId(3);
        departamento.setDescripcion("Colonia");
        departamentoService.update(departamento);
        departamento.setId(4);
        departamento.setDescripcion("Durazno");
        departamentoService.update(departamento);
        departamento.setId(5);
        departamento.setDescripcion("Maldonado");
        departamentoService.update(departamento);
        departamento.setId(6);
        departamento.setDescripcion("La Costa");
        departamentoService.update(departamento);
        departamento.setId(7);
        departamento.setDescripcion("San José");
        departamentoService.update(departamento);
        departamento.setId(8);
        departamento.setDescripcion("Rocha");
        departamentoService.update(departamento);
        departamento.setId(9);
        departamento.setDescripcion("Soriano");
        departamentoService.update(departamento);
        departamento.setId(10);
        departamento.setDescripcion("Salto");
        departamentoService.update(departamento);
        departamento.setId(11);
        departamento.setDescripcion("Artigas");
        departamentoService.update(departamento);
        departamento.setId(12);
        departamento.setDescripcion("Florida");
        departamentoService.update(departamento);
        departamento.setId(13);
        departamento.setDescripcion("Río Negro");
        departamentoService.update(departamento);
        departamento.setId(14);
        departamento.setDescripcion("Rivera");
        departamentoService.update(departamento);
        departamento.setId(15);
        departamento.setDescripcion("Flores");
        departamentoService.update(departamento);
        departamento.setId(16);
        departamento.setDescripcion("Cerro Largo");
        departamentoService.update(departamento);
        departamento.setId(17);
        departamento.setDescripcion("Treinta y Tres");
        departamentoService.update(departamento);
        departamento.setId(18);
        departamento.setDescripcion("Lavalleja");
        departamentoService.update(departamento);
        departamento.setId(19);
        departamento.setDescripcion("Tacuarembó");
        departamentoService.update(departamento);
    }

    private void initInterviniente() {
        Interviniente interviniente = new Interviniente();
        interviniente.setCodigo("1");
        interviniente.setDescripcion("Interviniente 1");
        intervinienteService.create(interviniente);
        interviniente = new Interviniente();
        interviniente.setCodigo("2");
        interviniente.setDescripcion("Interviniente 2");
        intervinienteService.create(interviniente);
        interviniente = new Interviniente();
        interviniente.setCodigo("3");
        interviniente.setDescripcion("Interviniente 3");
        intervinienteService.create(interviniente);
    }

    private void initEstado() {
        Estado estado = new Estado();
        estado.setNombre("Ventanilla");
        estado.setCodigo("VV");
        estadoService.create(estado);
        estado = new Estado();
        estado.setNombre("Verificación");
        estado.setCodigo("VF");
        estadoService.create(estado);
        estado = new Estado();
        estado.setNombre("Calificación");
        estado.setCodigo("CF");
        estadoService.create(estado);
        estado = new Estado();
        estado.setNombre("Estado e1");
        estado.setCodigo("e1");
        estadoService.create(estado);
    }

    private void initEscribanos() {
        Escribano escribano = new Escribano();
        escribano.setCodigo(35);
        escribano.setNombre("Escribano 1");
        escribanoService.update(escribano);
        escribano = new Escribano();
        escribano.setCodigo(3);
        escribano.setNombre("Escribano 2");
        escribanoService.update(escribano);
        escribano = new Escribano();
        escribano.setCodigo(5);
        escribano.setNombre("Escribano 3");
        escribanoService.update(escribano);
    }

    private void initRegistros() {
        Registro registro = new Registro();
        registro.setCodigo("RPI");
        registro.setDescripcion("Registro Propiedad Seccion Inmobiliario");
        registroService.update(registro);
        Seccion seccion = new Seccion();
        seccion.setCodigo("Sec1RPI");
        seccion.setDescripcion("Seccion 1 RPI");
        seccion.setRegistro(registro);
        seccionService.update(seccion);
        Acto acto = new Acto();
        acto.setCodigo("act1Sec1RPI");
        acto.setDescripcion("Acto 1 Seccion 1 RPI");
        acto.setDuracion("duracion");
        acto.setPeriodo((short) 1);
        acto.setSeccion(seccion);
        actoService.update(acto);
        seccion = new Seccion();
        seccion.setCodigo("Sec2RPI");
        seccion.setDescripcion("Seccion 2 RPI");
        seccion.setRegistro(registro);
        seccionService.update(seccion);
        acto = new Acto();
        acto.setCodigo("act1Sec2RPI");
        acto.setDescripcion("Acto 1 Seccion 2 RPI");
        acto.setDuracion("duracion");
        acto.setPeriodo((short) 3);
        acto.setSeccion(seccion);
        actoService.update(acto);

        registro = new Registro();
        registro.setCodigo("RA");
        registro.setDescripcion("Registro Propiedad Seccion Mobiliario");
        registroService.update(registro);
        seccion = new Seccion();
        seccion.setCodigo("Sec1RA");
        seccion.setDescripcion("Seccion 1 RA");
        seccion.setRegistro(registro);
        seccionService.update(seccion);
        acto = new Acto();
        acto.setCodigo("act1Sec1RA");
        acto.setDescripcion("Acto 1 Seccion 1 RA");
        acto.setDuracion("duracion");
        acto.setPeriodo((short) 3);
        acto.setSeccion(seccion);
        actoService.update(acto);
        seccion = new Seccion();
        seccion.setCodigo("Sec2RA");
        seccion.setDescripcion("Seccion 2 RA");
        seccion.setRegistro(registro);
        seccionService.update(seccion);

        registro = new Registro();
        registro.setCodigo("RAE");
        registro.setDescripcion("Registro Aeronaves");
        registroService.update(registro);
        seccion = new Seccion();
        seccion.setCodigo("Sec1RAE");
        seccion.setDescripcion("Seccion 1 RAE");
        seccion.setRegistro(registro);
        seccionService.update(seccion);
        acto = new Acto();
        acto.setCodigo("act1Sec1RAE");
        acto.setDescripcion("Acto 1 Seccion 1 RAE");
        acto.setDuracion("duracion");
        acto.setPeriodo((short) 3);
        acto.setSeccion(seccion);
        actoService.update(acto);

        registro = new Registro();
        registro.setCodigo("RCO");
        registro.setDescripcion("Registro Nacional Comercio");
        registroService.update(registro);
        seccion = new Seccion();
        seccion.setCodigo("Sec1RCO");
        seccion.setDescripcion("Seccion 1 RCO");
        seccion.setRegistro(registro);
        seccionService.update(seccion);

        registro = new Registro();
        registro.setCodigo("RGI");
        registro.setDescripcion("Registro Actos Personales");
        registroService.update(registro);
        seccion = new Seccion();
        seccion.setCodigo("Sec1RGI");
        seccion.setDescripcion("Seccion 1 RGI");
        seccion.setRegistro(registro);
        seccionService.update(seccion);

        registro = new Registro();
        registro.setCodigo("ACF");
        registro.setDescripcion("Registro Asociaciones Civiles");
        registroService.update(registro);
        seccion = new Seccion();
        seccion.setCodigo("Sec1ACF");
        seccion.setDescripcion("Seccion 1 ACF");
        seccion.setRegistro(registro);
        seccionService.update(seccion);

        registro = new Registro();
        registro.setCodigo("PSD");
        registro.setDescripcion("Prenda sin Desplazamiento");
        registroService.update(registro);
        seccion = new Seccion();
        seccion.setCodigo("Sec1PDF");
        seccion.setDescripcion("Seccion 1 PSD");
        seccion.setRegistro(registro);
        seccionService.update(seccion);
    }

    private void initCombustible() {
        Combustible combustible = new Combustible();
        combustible.setCodigo("nafta");
        combustible.setDescripcion("Nafta");
        combustibleService.update(combustible);
        combustible = new Combustible();
        combustible.setCodigo("gasoil");
        combustible.setDescripcion("Gasoil");
        combustibleService.update(combustible);
    }

    private void initEmisor() {
        Emisor emisor = new Emisor();
        emisor.setCodigo("e1");
        emisor.setDescripcion("Emisor 1");
        emisorService.update(emisor);
        emisor = new Emisor();
        emisor.setCodigo("e2");
        emisor.setDescripcion("Emisor 2");
        emisorService.update(emisor);
    }

}