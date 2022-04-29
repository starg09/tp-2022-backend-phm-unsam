package difficult.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var ordenCompra: Int = 0
    lateinit var fechaCompra: LocalDate
    var cantidad : Int = 0
    var importe : Double = 0.0
}