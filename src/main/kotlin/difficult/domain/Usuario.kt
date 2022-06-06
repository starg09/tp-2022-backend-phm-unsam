package difficult.domain

import java.time.LocalDate
import java.time.Period
import javax.persistence.*

@Entity
class Usuario {
    @Column
    lateinit var nombre: String
    @Column
    lateinit var apellido: String
    @Column
    lateinit var fechaNacimiento: LocalDate
    @Column
    var saldo: Double = 0.0
    @Column
    lateinit var email: String
    @Column
    lateinit var contrasenia: String

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_usuario")
    val compras = mutableSetOf<Compra>()

//    @Id
//    @GeneratedValue()
//    var id4j: Long? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    fun edad(): Int{
        return Period.between(fechaNacimiento, LocalDate.now()).years
    }

    fun aumentarSaldo(monto: Double){
        saldo += monto
    }

    fun disminuirSaldo(monto: Double){
        saldo -= monto
    }
}