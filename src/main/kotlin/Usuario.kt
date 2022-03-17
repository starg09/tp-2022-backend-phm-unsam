import java.util.*

class Usuario(var nombre: String, var apellido: String, val fechaNacimiento: Date, var saldo: Float) {




    fun edad(): Int{
        return 21
    }

    fun aumentarSaldo(monto: Int){
        saldo += monto
    }

    fun disminuirSaldo(monto: Int){
        saldo -= monto
    }
}