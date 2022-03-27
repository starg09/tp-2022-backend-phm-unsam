package difficult.domain

import java.time.LocalDate

class Compra {
    var ordenCompra: Int = 0
    lateinit var fechaCompra: LocalDate
    var cantidad : Int = 0
    var importe : Double = 0.0
}