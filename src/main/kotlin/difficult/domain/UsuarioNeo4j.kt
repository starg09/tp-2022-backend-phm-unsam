package difficult.domain

import org.springframework.data.neo4j.core.schema.*
import java.time.LocalDate

@Node("Usuario")
class UsuarioNeo4j {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Property
    lateinit var nombre: String
    @Property
    lateinit var apellido: String
//    @Property
//    lateinit var fechaNacimiento: LocalDate
//    @Property
//    var saldo: Double = 0.0
//    @Property
//    lateinit var email: String

    @Relationship(type = "COMPRO", direction = Relationship.Direction.OUTGOING)
    val compras = mutableSetOf<Compra>()
}