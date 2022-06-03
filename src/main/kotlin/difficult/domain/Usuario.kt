package difficult.domain

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import org.springframework.data.neo4j.core.schema.Relationship
import java.time.LocalDate
import java.time.Period
import javax.persistence.*

@Entity
@Node("Usuario")
class Usuario {
    @Column
    @Property
    lateinit var nombre: String
    @Column
    @Property
    lateinit var apellido: String
    @Column
    @Property
    lateinit var fechaNacimiento: LocalDate
    @Column
    @Property
    var saldo: Double = 0.0
    @Column
    @Property
    lateinit var email: String
    @Column
    @Property
    lateinit var contrasenia: String

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_usuario")
    @Relationship(type = "COMPRO", direction = Relationship.Direction.OUTGOING)
    val compras = mutableSetOf<Compra>()

//    @Id
//    @GeneratedValue()
//    var id4j: Long? = null

    @Id //Neo4j
    @javax.persistence.Id
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