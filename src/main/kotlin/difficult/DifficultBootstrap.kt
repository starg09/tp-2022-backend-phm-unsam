package difficult

import difficult.domain.*
import difficult.repository.*
import difficult.service.UsuarioService
import java.time.LocalDate
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired

const val RECREAR_LOTES_EN_MONGO = false
@Service
class DifficultBootstrap : InitializingBean {
    @Autowired
    private lateinit var repoProductos: RepoProductos

    @Autowired
    private lateinit var repoUsuarios: RepoUsuarios

    @Autowired
    private lateinit var repoCarrito: RepoCarrito

    @Autowired
    private lateinit var repoLotes: RepoLotes

    @Autowired
    private lateinit var usuarioService: UsuarioService

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
    private lateinit var loteCombo1: Lote
    private lateinit var loteCombo2: Lote
    private lateinit var loteCombo3: Lote
    private lateinit var loteCombo4: Lote

    private lateinit var tempLote1: Lote
    private lateinit var tempLote2: Lote
    private lateinit var tempLote3: Lote
    private lateinit var tempPiso: Piso
    private lateinit var tempPintura: Pintura


    fun guardarSiNoExiste(producto: Producto) {
        repoProductos.findById(producto.id)
            .orElseGet { repoProductos.save(producto) };
    }


    fun guardarLoteSiNoExiste(lote: Lote) {
        repoLotes.findById(lote.id).ifPresentOrElse(
            { if (RECREAR_LOTES_EN_MONGO) repoLotes.save(lote) },
            { repoLotes.save(lote) }
        )
    }


    //TODO: ¿Hace falta? ¿Es costoso? (consultas cada vez que se arranca spring)
    fun guardarSiNoExiste(usuario: Usuario): Usuario {
        return repoUsuarios.findByEmail(usuario.email)
            .orElseGet{ repoUsuarios.save(usuario); }
    }

    fun initUsuarios() {

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
        claire = Usuario().apply {
            nombre = "Claire"
            apellido = "Redfield"
            fechaNacimiento = LocalDate.of(1979, 6, 5)
            saldo = 1000000.0
            email = "claire@gmail.com"
            contrasenia = "1234567890"
        }


        dami = guardarSiNoExiste(dami)
        jill = guardarSiNoExiste(jill)
        leon = guardarSiNoExiste(leon)
        chris = guardarSiNoExiste(chris)
        claire = guardarSiNoExiste(claire)

    }

    fun initLotes() {
        lotePisoAltoTransito = Lote().apply {
            cantidadDisponible = 2
            fechaIngreso = LocalDate.now()
            id = 2222
        }
        lotePisoNormal = Lote().apply {
            cantidadDisponible = 6
            fechaIngreso = LocalDate.now()
            id = 1111
        }
        lotePisoNormal2 = Lote().apply {
            cantidadDisponible = 6
            fechaIngreso = LocalDate.now()
            id = 7777
        }
        lotePinturaMayorRendimiento = Lote().apply {
            cantidadDisponible = 5
            fechaIngreso = LocalDate.now()
            id = 3333
        }
        lotePinturaMenorRendimiento = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 4444
        }
        loteCombo1 = Lote().apply {
            cantidadDisponible = 7
            fechaIngreso = LocalDate.now()
            id = 5551
        }
        loteCombo2 = Lote().apply {
            cantidadDisponible = 4
            fechaIngreso = LocalDate.now()
            id = 5552
        }
        loteCombo3 = Lote().apply {
            cantidadDisponible = 11
            fechaIngreso = LocalDate.now()
            id = 5553
        }
        loteCombo4 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 5554
        }

        guardarLoteSiNoExiste(lotePisoNormal)
        guardarLoteSiNoExiste(lotePisoNormal2)
        guardarLoteSiNoExiste(lotePisoAltoTransito)
        guardarLoteSiNoExiste(lotePinturaMayorRendimiento)
        guardarLoteSiNoExiste(lotePinturaMenorRendimiento)
        listOf(loteCombo1, loteCombo2, loteCombo3, loteCombo4).forEach { guardarLoteSiNoExiste(it) }

    }

    fun initProductos() {


        pisoNormal = Piso().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            urlImagen = "productos/acme_rustico.webp"
            puntaje = 3
            paisOrigen = "Argentina"
            precioBase = 2500.0
            esAltoTransito = false
            medidaX = 50
            medidaZ = 30
            terminacion = "satinado"
            agregarLote(lotePisoNormal)
            agregarLote(lotePisoNormal2)
            id = 1
        }

        pisoAltoTransito = Piso().apply {
            nombre = "Acme arena"
            descripcion =
                "Una variable mas oscura, arenosa, de nuestro tipo principal de baldosa. Traídas exclusivamente desde Brasilia, en vuelo charter privado incluido en el precio."
            urlImagen = "productos/acme_arena.webp"
            puntaje = 4
            paisOrigen = "Brasil"
            precioBase = 1000.0
            esAltoTransito = true
            medidaX = 60
            medidaZ = 60
            terminacion = "no satinado"
            agregarLote(lotePisoAltoTransito)
            id = 2
        }

        pinturaMenorRendimiento = Pintura().apply {
            nombre = "adla blanco"
            descripcion = "una descripcion"
            urlImagen = "productos/adla_blanca.jpg"
            puntaje = 1
            paisOrigen = "Rand McNally"
            precioBase = 1000.0
            rendimiento = 4
            color = "Blanco"
            litros = 10
            agregarLote(lotePinturaMenorRendimiento)
            id = 3
        }

        pinturaMayorRendimiento = Pintura().apply {
            nombre = "Adla negro"
            descripcion = "una descripcion"
            urlImagen = "productos/adla_negra.jpg"
            puntaje = 3
            paisOrigen = "Argentina"
            precioBase = 1000.0
            rendimiento = 9
            color = "Negro"
            litros = 10
            agregarLote(lotePinturaMayorRendimiento)
            id = 4
        }

        combo = Combo().apply {
            nombre = "combo"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Urawey"
            precioBase = 0.0
            agregarProducto(pisoNormal)
            agregarProducto(pinturaMenorRendimiento)
            agregarLote(loteCombo1)
            agregarLote(loteCombo2)
            agregarLote(loteCombo3)
            agregarLote(loteCombo4)
            id = 5
        }

        guardarSiNoExiste(pisoNormal)
        guardarSiNoExiste(pisoAltoTransito)
        guardarSiNoExiste(pinturaMayorRendimiento)
        guardarSiNoExiste(pinturaMenorRendimiento)
        guardarSiNoExiste(combo)


//        -------------------------------------------------


        tempLote1 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 10101
        }
        tempLote2 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 10201
        }
        tempLote3 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 10301
        }
        listOf(tempLote1, tempLote2, tempLote3).forEach { guardarLoteSiNoExiste(it) }
        guardarSiNoExiste(
            Pintura().apply {
                id = 6
                nombre = "Tersuave Roja"
                descripcion = "Pintura marca Tersuave de color rojo"
                urlImagen = "productos/tersuave_roja.jpg"
                puntaje = 2
                paisOrigen = "Argentina"
                precioBase = 800.0
                rendimiento = 7
                color = "Rojo"
                litros = 12
                agregarLote(tempLote1)
                agregarLote(tempLote2)
                agregarLote(tempLote3)
            }
        )





        tempLote1 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 10102
        }
        tempLote2 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 10202
        }
        tempLote3 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 10302
        }
        listOf(tempLote1, tempLote2, tempLote3).forEach { guardarLoteSiNoExiste(it) }
        guardarSiNoExiste(
            Pintura().apply {
                id = 7
                nombre = "Tersuave Blanca"
                descripcion = "Pintura marca Tersuave de color blanco"
                urlImagen = "productos/tersuave_blanca.jpg"
                puntaje = 3
                paisOrigen = "Argentina"
                precioBase = 750.0
                rendimiento = 8
                color = "Blanco"
                litros = 12
                agregarLote(tempLote1)
                agregarLote(tempLote2)
                agregarLote(tempLote3)
            }
        )






        tempLote1 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 20101
        }
        tempLote2 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 20201
        }
        tempLote3 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 20301
        }
        listOf(tempLote1, tempLote2, tempLote3).forEach { guardarLoteSiNoExiste(it) }
        guardarSiNoExiste(
            Piso().apply {
                id = 8
                nombre = "Rizzo Ladrillo Hueco 27 Centavos"
                descripcion = "Buen revestimiento, excelente piso!"
                urlImagen = "productos/degoas.png"
                puntaje = 5
                paisOrigen = "Argentina"
                precioBase = 0.27
                esAltoTransito = false
                medidaX = 20
                medidaZ = 10
                terminacion = "no satinado"
                agregarLote(tempLote1)
                agregarLote(tempLote2)
                agregarLote(tempLote3)
            }
        )






        tempLote1 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 20102
        }
        tempLote2 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 20202
        }
        tempLote3 = Lote().apply {
            cantidadDisponible = 3
            fechaIngreso = LocalDate.now()
            id = 20302
        }
        listOf(tempLote1, tempLote2, tempLote3).forEach { guardarLoteSiNoExiste(it) }
        guardarSiNoExiste(
            Piso().apply {
                id = 9
                nombre = "Piso de Aluminio"
                descripcion = "Ultima tecnologia! Consultar promociones durante septiembre"
                urlImagen = "productos/aluminio.png"
                puntaje = 4
                paisOrigen = "China"
                precioBase = 1240.0
                esAltoTransito = true
                medidaX = 40
                medidaZ = 40
                terminacion = "satinado"
                agregarLote(tempLote1)
                agregarLote(tempLote2)
                agregarLote(tempLote3)
            }
        )


    }

    fun initCarrito() {
        if (usuarioService.comprasUsuario(dami.id).isEmpty()) {
            usuarioService.agregarProductoCarrito(dami.id, pisoNormal.id, 1, lotePisoNormal.id)
            usuarioService.agregarProductoCarrito(dami.id, pinturaMenorRendimiento.id, 1, lotePinturaMenorRendimiento.id)
            usuarioService.comprar(dami.id)
        }
    }

    override fun afterPropertiesSet() {
        this.initUsuarios()
        this.initLotes()
        this.initProductos()
        this.initCarrito()
    }


}