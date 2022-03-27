package difficult.domain

import java.time.LocalDate

class Lote {
    var numeroLote: Int = 0
    lateinit var fechaIngreso: LocalDate
    var cantidadDisponible: Int = 0
}