package difficult

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
    private lateinit var repoCarrito: RepoCarrito
    @Autowired
    private lateinit var repoLotes: RepoLotes

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
            carrito = Carrito().apply {  }
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

        repoLotes.save(lotePisoNormal)
        repoLotes.save(lotePisoNormal2)
        repoLotes.save(lotePisoAltoTransito)
        repoLotes.save(lotePinturaMayorRendimiento)
        repoLotes.save(lotePinturaMenorRendimiento)
        repoLotes.save(loteCombo)

    }

    fun initProductos(){



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
//            id = 1
        }

        pisoAltoTransito = Piso().apply {
            nombre = "Acme arena"
            descripcion = "Una variable mas oscura, arenosa, de nuestro tipo principal de baldosa. Traídas exclusivamente desde Brasilia, en vuelo charter privado incluido en el precio."
            urlImagen = "productos/acme_arena.webp"
            puntaje = 4
            paisOrigen = "Brasil"
            precioBase = 1000.0
            esAltoTransito = true
            medidaX = 60
            medidaZ = 60
            terminacion = "no satinado"
            agregarLote(lotePisoAltoTransito)
//            id = 2
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
//            id = 3
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
//            id = 4
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
//            id = 5
        }

        repoProductos.save(pisoNormal)
        repoProductos.save(pisoAltoTransito)
        repoProductos.save(pinturaMayorRendimiento)
        repoProductos.save(pinturaMenorRendimiento)
        repoProductos.save(combo)
    }

    fun initCarrito(){
        repoCarrito.create(Carrito(), dami.id)
        repoCarrito.create(Carrito(), leon.id)
        repoCarrito.create(Carrito(), jill.id)
        repoCarrito.create(Carrito(), claire.id)
        repoCarrito.create(Carrito(), chris.id)

        dami.carrito = repoCarrito.getById(dami.id)

        dami.agregarAlCarrito(pisoNormal, 1, lotePisoNormal)
        dami.agregarAlCarrito(pinturaMenorRendimiento, 1, lotePinturaMenorRendimiento)
        val productos = dami.carrito.getProductos()
        dami.realizarCompra()
        repoUsuarios.save(dami)
        productos.forEach { repoProductos.save(it) }
    }

    override fun afterPropertiesSet() {
        this.initUsuarios()
        this.initLotes()
        this.initProductos()
        this.initCarrito()
    }


}