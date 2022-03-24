package difficult.domain

import java.time.LocalDate
import java.time.Period

class Usuario(var nombre: String, var apellido: String, val fechaNacimiento: LocalDate, var saldo: Double){

    public var id = 0

    fun edad(): Int{
        return Period.between(fechaNacimiento, LocalDate.now()).years
    }

    fun aumentarSaldo(monto: Int){
        saldo += monto
    }

    fun disminuirSaldo(monto: Int){
        saldo -= monto
    }
}