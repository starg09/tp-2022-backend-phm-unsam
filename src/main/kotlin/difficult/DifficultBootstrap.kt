package difficult

import difficult.domain.*
import java.time.LocalDate
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import difficult.repository.RepoUsuarios


@Service
class DifficultBootstrap : InitializingBean {

    @Autowired
    private lateinit var repoUsuarios: RepoUsuarios

    private lateinit var dami: Usuario
    private lateinit var jill: Usuario
    private lateinit var chris: Usuario
    private lateinit var leon: Usuario
    private lateinit var claire: Usuario

    private lateinit var pisoNormal: Piso
    private lateinit var pisoAltoTransito: Piso
    private lateinit var pinturaMayorRendimiento: Pintura
    private lateinit var pinturaMenorRendimiento: Pintura

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
        pisoNormal = Piso().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Argentina"
            precioBase = 2500.0
            tipo = PisoNormal()
            medidaX = 50
            medidaZ = 30
        }

        pisoAltoTransito = Piso().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Argentina"
            precioBase = 1000.0
            tipo = PisoAltoTransito()
            medidaX = 60
            medidaZ = 60
        }

        pinturaMenorRendimiento = Pintura().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Argentina"
            precioBase = 1000.0
            rendimiento = 4
            color = "Blanco"
            litros = 10
        }

        pinturaMayorRendimiento = Pintura().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Argentina"
            precioBase = 1000.0
            rendimiento = 9
            color = "Blanco"
            litros = 10
        }
    }




    override fun afterPropertiesSet() {
        this.initUsuarios()
    }


}