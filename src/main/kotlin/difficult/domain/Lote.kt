package difficult.domain

import java.time.LocalDate

class Lote {
    var numeroLote: Int = 0
    lateinit var fechaIngreso: LocalDate
    var cantidadDisponible: Int = 0

    fun toLoteDTO(): LoteDTO {
        return LoteDTO().apply {
            cantidadDisponibleDto = cantidadDisponible
            numeroLoteDto = numeroLote
        }
    }
}

class LoteDTO {
    var cantidadDisponibleDto: Int = 0
    var numeroLoteDto: Int = 0
}