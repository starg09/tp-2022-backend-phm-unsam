package difficult

import difficult.domain.*
import difficult.repository.RepoProductos
import java.time.LocalDate
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import difficult.repository.RepoUsuarios


@Service
class DifficultBootstrap : InitializingBean {

    @Autowired
    private lateinit var repoUsuarios: RepoUsuarios

    @Autowired
    private lateinit var repoProductos: RepoProductos

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
    private lateinit var lotePisoAltoTransito: Lote
    private lateinit var lotePinturaMayorRendimiento: Lote
    private lateinit var lotePinturaMenorRendimiento: Lote
    private lateinit var loteCombo: Lote

    fun initUsuarios(){

        dami = Usuario("Dami", "Lescano", LocalDate.of(2000, 6, 5), 1000000.0, "1234567890")
        jill = Usuario("Jill", "Valentine", LocalDate.of(1974, 6, 5), 1000000.0, "1234567890")
        chris = Usuario("Chris", "Redfield", LocalDate.of(1973, 6, 5), 1000000.0, "1234567890")
        leon = Usuario("Leon Scott", "Kennedy", LocalDate.of(1977, 6, 5), 1000000.0, "1234567890")
        claire = Usuario("Claire", "Redfield", LocalDate.of(1979, 6, 5), 1000000.0, "1234567890")


        repoUsuarios.create(dami)
        repoUsuarios.create(jill)
        repoUsuarios.create(leon)
        repoUsuarios.create(chris)
        repoUsuarios.create(claire)

    }

    fun initProductos(){

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

        pisoNormal = Piso().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Argentina"
            precioBase = 2500.0
            tipo = PisoNormal()
            medidaX = 50
            medidaZ = 30
            terminacion = "satinado"
            lote = lotePisoNormal
        }

        pisoAltoTransito = Piso().apply {
            nombre = "Acme arena"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Brasil"
            precioBase = 1000.0
            tipo = PisoAltoTransito()
            medidaX = 60
            medidaZ = 60
            terminacion = "no satinado"
            lote = lotePisoAltoTransito
        }

        pinturaMenorRendimiento = Pintura().apply {
            nombre = "adla blanco"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Argentina"
            precioBase = 1000.0
            rendimiento = 4
            color = "Blanco"
            litros = 10
            lote = lotePinturaMenorRendimiento
        }

        pinturaMayorRendimiento = Pintura().apply {
            nombre = "Adla negro"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Chile"
            precioBase = 1000.0
            rendimiento = 9
            color = "Negro"
            litros = 10
            lote = lotePinturaMayorRendimiento
        }

        combo = Combo().apply {
            agregarProducto(pisoNormal)
            agregarProducto(pinturaMenorRendimiento)
            lote = loteCombo
        }

        repoProductos.create(pisoNormal)
        repoProductos.create(pisoAltoTransito)
        repoProductos.create(pinturaMayorRendimiento)
        repoProductos.create(pinturaMenorRendimiento)
        repoProductos.create(combo)

        dami.agregarAlCarrito(combo, 2)
        dami.realizarCompra(1)
    }




    override fun afterPropertiesSet() {
        this.initUsuarios()
        this.initProductos()
    }


}