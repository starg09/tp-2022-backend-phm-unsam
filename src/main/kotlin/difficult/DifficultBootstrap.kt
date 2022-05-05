package difficult

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator
import difficult.domain.*
import difficult.repository.*
import java.time.LocalDate
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired


@Service
class DifficultBootstrap : InitializingBean {
    @Autowired
    private lateinit var repoProductos: RepoProductos
    @Autowired
    private lateinit var repoUsuarios: RepoUsuarios
    @Autowired
    private lateinit var repoLotes: RepoLotes



    private lateinit var mapperObject: ObjectMapper
    private lateinit var ptv: PolymorphicTypeValidator

    /*private lateinit var compra1: Compra
    private lateinit var compra2: Compra
    private lateinit var compra3: Compra*/

    private lateinit var dami: Usuario
    private lateinit var jill: Usuario
    private lateinit var chris: Usuario
    private lateinit var leon: Usuario
    private lateinit var claire: Usuario

    private lateinit var pisoNormal: Piso
    private lateinit var pisoAltoTransito: Piso
    private lateinit var pinturaMayorRendimiento: Pintura
    private lateinit var pinturaMenorRendimiento: Pintura

    private lateinit var combo: Combo

    private lateinit var lotePisoNormal: Lote
    private lateinit var lotePisoNormal2: Lote
    private lateinit var lotePisoAltoTransito: Lote
    private lateinit var lotePinturaMayorRendimiento: Lote
    private lateinit var lotePinturaMenorRendimiento: Lote
    private lateinit var loteCombo: Lote

    fun initUsuarios(){

        dami = Usuario().apply {
            nombre = "Dami"
            apellido = "Lescano"
            fechaNacimiento = LocalDate.of(2000, 6, 5)
            saldo = 1000000.0
            email = "lescano5600@gmail.com"
            contrasenia = "1234567890"
        }
        jill = Usuario().apply {
            nombre = "Jill"
            apellido = "Valentine"
            fechaNacimiento = LocalDate.of(1974, 6, 5)
            saldo = 1000000.0
            email = "jill@gmail.com"
            contrasenia = "1234567890"
        }
        chris = Usuario().apply {
            nombre = "Chris"
            apellido = "Redfield"
            fechaNacimiento = LocalDate.of(1973, 6, 5)
            saldo = 1000000.0
            email = "chris@gmail.com"
            contrasenia = "1234567890"
        }
        leon = Usuario().apply {
            nombre = "Leon Scott"
            apellido = "Kennedy"
            fechaNacimiento = LocalDate.of(1977, 6, 5)
            saldo = 1000000.0
            email = "leon@gmail.com"
            contrasenia = "1234567890"
        }
        claire = Usuario().apply{
            nombre = "Claire"
            apellido = "Redfield"
            fechaNacimiento = LocalDate.of(1979, 6, 5)
            saldo = 1000000.0
            email = "claire@gmail.com"
            contrasenia = "1234567890"
        }

        /*compra1 = Compra().apply {
            fechaCompra = LocalDate.now()
            cantidad = 1
            importe = 2000.0
        }
        compra2 = Compra().apply {
            fechaCompra = LocalDate.now()
            cantidad = 2
            importe = 3600.0
        }
        compra3 = Compra().apply {
            fechaCompra = LocalDate.now()
            cantidad = 1
            importe = 2400.0
        }
        
        dami.compras.add(compra1)
        dami.compras.add(compra2)
        dami.compras.add(compra3)*/


        repoUsuarios.save(dami)
        repoUsuarios.save(jill)
        repoUsuarios.save(leon)
        repoUsuarios.save(chris)
        repoUsuarios.save(claire)

    }

    fun initLotes(){
        lotePisoAltoTransito = Lote().apply {
            cantidadDisponible = 2
            fechaIngreso = LocalDate.now()
            numeroLote = 2222
        }
        lotePisoNormal = Lote().apply {
            cantidadDisponible = 6
            fechaIngreso = LocalDate.now()
            numeroLote = 1111
        }
        lotePisoNormal2 = Lote().apply {
            cantidadDisponible = 6
            fechaIngreso = LocalDate.now()
            numeroLote = 7777
        }
        lotePinturaMayorRendimiento = Lote().apply {
            cantidadDisponible = 5
            fechaIngreso = LocalDate.now()
            numeroLote = 3333
        }
        lotePinturaMenorRendimiento = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            numeroLote = 4444
        }
        loteCombo = Lote().apply {
            cantidadDisponible = 2
            fechaIngreso = LocalDate.now()
            numeroLote = 5555
        }


//        repoLotes.saveAll(listOf(
//            lotePisoNormal,
//            lotePisoNormal2,
//            lotePisoAltoTransito,
//            lotePinturaMayorRendimiento,
//            lotePinturaMenorRendimiento,
//            loteCombo
//        ))
    }

    fun initProductos(){



        pisoNormal = Piso().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            puntaje = 3
            paisOrigen = "Argentina"
            precioBase = 2500.0
            tipo = PisoNormal()
            medidaX = 50
            medidaZ = 30
            terminacion = "satinado"
            agregarLote(lotePisoNormal)
            agregarLote(lotePisoNormal2)
        }

        pisoAltoTransito = Piso().apply {
            nombre = "Acme arena"
            descripcion = "una descripcion"
            puntaje = 4
            paisOrigen = "Brasil"
            precioBase = 1000.0
            tipo = PisoAltoTransito()
            medidaX = 60
            medidaZ = 60
            terminacion = "no satinado"
            agregarLote(lotePisoAltoTransito)
        }

        pinturaMenorRendimiento = Pintura().apply {
            nombre = "adla blanco"
            descripcion = "una descripcion"
            puntaje = 1
            paisOrigen = "Rand McNally"
            precioBase = 1000.0
            rendimiento = 4
            color = "Blanco"
            litros = 10
            agregarLote(lotePinturaMenorRendimiento)
        }

        pinturaMayorRendimiento = Pintura().apply {
            nombre = "Adla negro"
            descripcion = "una descripcion"
            puntaje = 3
            paisOrigen = "Argentina"
            precioBase = 1000.0
            rendimiento = 9
            color = "Negro"
            litros = 10
            agregarLote(lotePinturaMayorRendimiento)
        }

        combo = Combo().apply {
            nombre = "combo"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Urawey"
            precioBase = 0.0
            agregarProducto(pisoNormal)
            agregarProducto(pinturaMenorRendimiento)
            agregarLote(loteCombo)
        }

        repoProductos.save(pisoNormal)
        repoProductos.save(pisoAltoTransito)
        repoProductos.save(pinturaMayorRendimiento)
        repoProductos.save(pinturaMenorRendimiento)
        repoProductos.save(combo)

        //repoProductos.establecerFiltros(listOf(FiltroPuntuacion(5)))
    }

    fun initCarrito(){
        dami.agregarAlCarrito(pisoNormal, 1, lotePisoNormal)
        dami.agregarAlCarrito(pinturaMenorRendimiento, 1, lotePinturaMenorRendimiento)
        val productos = dami.carrito.getProductos()
        dami.realizarCompra()
        repoUsuarios.save(dami)
        productos.forEach { repoProductos.save(it) }
    }

    fun crearMapperObject(){
        ptv = BasicPolymorphicTypeValidator.builder().allowIfSubType("com.baeldung.jackson.inheritance").allowIfSubType("java.util.ArrayList").build()
        mapperObject = ObjectMapper()
                mapperObject.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL)
    }



    override fun afterPropertiesSet() {
        this.initUsuarios()
        this.initLotes()
        this.initProductos()
        this.crearMapperObject()
        this.initCarrito()
    }


}