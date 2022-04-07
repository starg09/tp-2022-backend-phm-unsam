package difficult.domain

import CantidadInsuficienteLoteException
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

    fun chequearCantidadDisponible(cantidad: Int){
        if (cantidadDisponible < cantidad){
            throw CantidadInsuficienteLoteException("la cantidad disponible de este lote no essuficiente")
        }
    }

    fun disminuirCantidadDisponible(cantidad: Int){
        cantidadDisponible -= cantidad
    }
}

class LoteDTO {
    var cantidadDisponibleDto: Int = 0
    var numeroLoteDto: Int = 0
}