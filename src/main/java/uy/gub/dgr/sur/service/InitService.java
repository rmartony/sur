package uy.gub.dgr.sur.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import uy.gub.dgr.sur.entity.*;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by rafa on 22/8/2016.
 */
@Stateless
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
    @Inject
    private transient InscripcionService inscripcionService;
    @Inject
    private transient LibroRubricaService libroRubricaService;
    @Inject
    private transient LibroRubricaTipoService libroRubricaTipoService;
    @Inject
    private transient LocalidadService localidadService;
    @Inject
    private transient MarcaService marcaService;
    @Inject
    private transient ModeloService modeloService;
    @Inject
    private transient MonedaService monedaService;
    @Inject
    private transient MontoService montoService;
    @Inject
    private transient MovimientoService movimientoService;
    @Inject
    private transient TasaService tasaService;
    @Inject
    private transient TipoAutomotorService tipoAutomotorService;
    @Inject
    private transient TipoDocumentoService tipoDocumentoService;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void initDB() {
        final boolean initialized = initialized();
        if (!initialized) {
            initSedes();
            initRegistros();
            initDepartamentos();
            initEstado();
            initConfiguracion();
            initEscribanos();
            initCombustible();
            initEmisor();
            initLibroRubrica();
            initLibroRubricaTipo();
            initLocalidad();
            initMarca();
            initMoneda();
            initTipoDocumento();
            initTasa();
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
        sede.setCodigo("1");
        sede.setDescripcion("Montevideo");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("2");
        sede.setDescripcion("Paysandú");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("3");
        sede.setDescripcion("Colonia");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("4");
        sede.setDescripcion("Durazno");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("5");
        sede.setDescripcion("Maldonado");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("6");
        sede.setDescripcion("Canelones");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("7");
        sede.setDescripcion("Pando");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("8");
        sede.setDescripcion("San José");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("9");
        sede.setDescripcion("Rocha");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("10");
        sede.setDescripcion("Soriano");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("11");
        sede.setDescripcion("Salto");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("12");
        sede.setDescripcion("Artigas");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("13");
        sede.setDescripcion("Florida");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("14");
        sede.setDescripcion("Río Negro");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("15");
        sede.setDescripcion("Rivera");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("16");
        sede.setDescripcion("Flores");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("17");
        sede.setDescripcion("Cerro Largo");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("18");
        sede.setDescripcion("Treinta y Tres");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("19");
        sede.setDescripcion("Lavalleja");
        sede.setAnio(2016);
        sedeService.update(sede);
        sede = new Sede();
        sede.setCodigo("20");
        sede.setDescripcion("Tacuarembó");
        sede.setAnio(2016);
        sedeService.update(sede);
    }

    private void initDepartamentos() {
        Departamento departamento = new Departamento();
        departamento.setCodigo("1");
        departamento.setDescripcion("Montevideo");
        departamentoService.update(departamento);
        departamento.setCodigo("2");
        departamento.setDescripcion("Paysandú");
        departamentoService.update(departamento);
        departamento.setCodigo("3");
        departamento.setDescripcion("Colonia");
        departamentoService.update(departamento);
        departamento.setCodigo("4");
        departamento.setDescripcion("Durazno");
        departamentoService.update(departamento);
        departamento.setCodigo("5");
        departamento.setDescripcion("Maldonado");
        departamentoService.update(departamento);
        departamento.setCodigo("6");
        departamento.setDescripcion("La Costa");
        departamentoService.update(departamento);
        departamento.setCodigo("7");
        departamento.setDescripcion("San José");
        departamentoService.update(departamento);
        departamento.setCodigo("8");
        departamento.setDescripcion("Rocha");
        departamentoService.update(departamento);
        departamento.setCodigo("9");
        departamento.setDescripcion("Soriano");
        departamentoService.update(departamento);
        departamento.setCodigo("10");
        departamento.setDescripcion("Salto");
        departamentoService.update(departamento);
        departamento.setCodigo("11");
        departamento.setDescripcion("Artigas");
        departamentoService.update(departamento);
        departamento.setCodigo("12");
        departamento.setDescripcion("Florida");
        departamentoService.update(departamento);
        departamento.setCodigo("13");
        departamento.setDescripcion("Río Negro");
        departamentoService.update(departamento);
        departamento.setCodigo("14");
        departamento.setDescripcion("Rivera");
        departamentoService.update(departamento);
        departamento.setCodigo("15");
        departamento.setDescripcion("Flores");
        departamentoService.update(departamento);
        departamento.setCodigo("16");
        departamento.setDescripcion("Cerro Largo");
        departamentoService.update(departamento);
        departamento.setCodigo("17");
        departamento.setDescripcion("Treinta y Tres");
        departamentoService.update(departamento);
        departamento.setCodigo("18");
        departamento.setDescripcion("Lavalleja");
        departamentoService.update(departamento);
        departamento.setCodigo("19");
        departamento.setDescripcion("Tacuarembó");
        departamentoService.update(departamento);
    }

    private Interviniente initInterviniente(String id, Seccion s) {
        Interviniente interviniente = new Interviniente();
        interviniente.setCodigo(id);
        interviniente.setDescripcion("Interviniente " + id);
        interviniente.setSeccion(s);
        return intervinienteService.update(interviniente);
    }

    private void initEstado() {
        Estado estado = new Estado();
        estado.setNombre("Ventanilla");
        estado.setCodigo("VV");
        estadoService.update(estado);
        estado = new Estado();
        estado.setNombre("Verificación");
        estado.setCodigo("VF");
        estadoService.update(estado);
        estado = new Estado();
        estado.setNombre("Calificación");
        estado.setCodigo("CF");
        estadoService.update(estado);
        estado = new Estado();
        estado.setNombre("Estado e1");
        estado.setCodigo("e1");
        estadoService.update(estado);
    }

    private void initEscribanos() {
        Escribano escribano = new Escribano();
        escribano.setCodigo(35L);
        escribano.setNombre("Escribano 1");
        escribanoService.update(escribano);
        escribano = new Escribano();
        escribano.setCodigo(3L);
        escribano.setNombre("Escribano 2");
        escribanoService.update(escribano);
        escribano = new Escribano();
        escribano.setCodigo(5L);
        escribano.setNombre("Escribano 3");
        escribanoService.update(escribano);
    }

    private void initRegistros() {
        Registro registro = new Registro();
        registro.setCodigo("RPI");
        registro.setDescripcion("Registro Propiedad Seccion Inmobiliario");
        registro = registroService.update(registro);
        Seccion seccion = new Seccion();
        seccion.setCodigo("Sec1RPI");
        seccion.setDescripcion("Seccion 1 RPI");
        seccion.setRegistro(registro);
        seccion = seccionService.update(seccion);
        initInterviniente("1", seccion);
        Acto acto = new Acto();
        acto.setCodigo("act1Sec1RPI");
        acto.setDescripcion("Acto 1 Seccion 1 RPI");
        acto.setDuracion("duracion");
        acto.setPeriodo((short) 1);
        acto.setSeccion(seccion);
        initInterviniente("2", seccion);
        actoService.update(acto);
        seccion = new Seccion();
        seccion.setCodigo("Sec2RPI");
        seccion.setDescripcion("Seccion 2 RPI");
        seccion.setRegistro(registro);
        seccion = seccionService.update(seccion);
        initInterviniente("3", seccion);
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
        registro = registroService.update(registro);
        seccion = new Seccion();
        seccion.setCodigo("Sec1RA");
        seccion.setDescripcion("Seccion 1 RA");
        seccion.setRegistro(registro);
        seccion = seccionService.update(seccion);
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
        seccion = seccionService.update(seccion);

        registro = new Registro();
        registro.setCodigo("RAE");
        registro.setDescripcion("Registro Aeronaves");
        registro = registroService.update(registro);
        seccion = new Seccion();
        seccion.setCodigo("Sec1RAE");
        seccion.setDescripcion("Seccion 1 RAE");
        seccion.setRegistro(registro);
        seccion = seccionService.update(seccion);
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
        registro = registroService.update(registro);
        seccion = new Seccion();
        seccion.setCodigo("Sec1RCO");
        seccion.setDescripcion("Seccion 1 RCO");
        seccion.setRegistro(registro);
        seccion = seccionService.update(seccion);

        registro = new Registro();
        registro.setCodigo("RGI");
        registro.setDescripcion("Registro Actos Personales");
        registro = registroService.update(registro);
        seccion = new Seccion();
        seccion.setCodigo("Sec1RGI");
        seccion.setDescripcion("Seccion 1 RGI");
        seccion.setRegistro(registro);
        seccion = seccionService.update(seccion);

        registro = new Registro();
        registro.setCodigo("ACF");
        registro.setDescripcion("Registro Asociaciones Civiles");
        registro = registroService.update(registro);
        seccion = new Seccion();
        seccion.setCodigo("Sec1ACF");
        seccion.setDescripcion("Seccion 1 ACF");
        seccion.setRegistro(registro);
        seccion = seccionService.update(seccion);

        registro = new Registro();
        registro.setCodigo("PSD");
        registro.setDescripcion("Prenda sin Desplazamiento");
        registro = registroService.update(registro);
        seccion = new Seccion();
        seccion.setCodigo("Sec1PDF");
        seccion.setDescripcion("Seccion 1 PSD");
        seccion.setRegistro(registro);
        seccion = seccionService.update(seccion);
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

    private void initLibroRubrica() {
        LibroRubrica libroRubrica = new LibroRubrica();
        libroRubrica.setCodigo("lib1");
        libroRubrica.setDescripcion("Libro rúbrica 1");
        libroRubricaService.update(libroRubrica);
        libroRubrica = new LibroRubrica();
        libroRubrica.setCodigo("lib2");
        libroRubrica.setDescripcion("Libro rúbrica 2");
        libroRubricaService.update(libroRubrica);
    }

    private void initLibroRubricaTipo() {
        LibroRubricaTipo libroRubricaTipo = new LibroRubricaTipo();
        libroRubricaTipo.setCodigo("libTipo1");
        libroRubricaTipo.setDescripcion("Libro rúbrica tipo 1");
        libroRubricaTipoService.update(libroRubricaTipo);
        libroRubricaTipo = new LibroRubricaTipo();
        libroRubricaTipo.setCodigo("libTipo2");
        libroRubricaTipo.setDescripcion("Libro rúbrica tipo 2");
        libroRubricaTipoService.update(libroRubricaTipo);
    }

    private void initLocalidad() {
        Localidad localidad = new Localidad();
        localidad.setCodigo("loc1");
        localidad.setDescripcion("Localidad 1");
        localidadService.update(localidad);
        localidad = new Localidad();
        localidad.setCodigo("loc2");
        localidad.setDescripcion("Localidad 2");
        localidadService.update(localidad);
    }

    private void initMarca() {
        Marca marca = new Marca();
        marca.setCodigo("marca1");
        marca.setDescripcion("Marca 1");
        marca = marcaService.update(marca);
        initModelo("modelo1", marca);
        marca = new Marca();
        marca.setCodigo("marca2");
        marca.setDescripcion("Marca 2");
        marcaService.update(marca);
        initModelo("modelo2", marca);
    }

    private Modelo initModelo(String codigo, Marca marca) {
        Modelo modelo = new Modelo();
        modelo.setCodigo(codigo);
        modelo.setDescripcion("Modelo " + codigo);
        modelo.setMarca(marca);
        modelo = modeloService.update(modelo);
        return modelo;
    }

    private void initMoneda() {
        Moneda moneda = new Moneda();
        moneda.setCodigo("USD");
        moneda.setDescripcion("Dólares americanos");
        monedaService.update(moneda);
        moneda = new Moneda();
        moneda.setCodigo("UYU");
        moneda.setDescripcion("Pesos uruguayos");
        monedaService.update(moneda);
    }

    private void initTasa() {
        Tasa tasa = new Tasa();
        tasa.setCodigo(Tasa.DOC_COMUN);
        tasa.setNombre("Tasa documento común");
        tasa.setMonto(new BigDecimal(4000));
        tasa.setFechaVencimiento(DateUtils.addDays(new Date(), 5));
        tasaService.update(tasa);
        tasa = new Tasa();
        tasa.setNombre("Tasa documento urgente");
        tasa.setCodigo(Tasa.DOC_URGENTE);
        tasa.setMonto(new BigDecimal(2300));
        tasa.setFechaVencimiento(DateUtils.addDays(new Date(), 15));

    }

    private void initTipoDocumento() {
        TipoDocumento tipoDocumento = new TipoDocumento();
        tipoDocumento.setCodigo("tipoDoc1");
        tipoDocumento.setDescripcion("Tipo Documento 1");
        tipoDocumento = tipoDocumentoService.update(tipoDocumento);
    }

}
