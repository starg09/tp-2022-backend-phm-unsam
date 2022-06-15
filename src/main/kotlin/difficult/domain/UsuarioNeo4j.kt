package difficult.domain


import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.Relationship.Direction
import java.time.LocalDate

@Node("Usuario")
class UsuarioNeo4j {
    fun agregarItemsDeCompra(compra: Compra) {
        itemsComprados.addAll(ItemCompraNeo4j.getItemsFromCompraForNeo4j(compra))
    }

    @Id
    @GeneratedValue
    var id: Long? = null

    @Property
    var idSQL: Int = -1

    @Property
    lateinit var nombre: String
    @Property
    lateinit var apellido: String
    @Property
    lateinit var fechaNacimiento: LocalDate
    @Property
    var saldo: Double = -1.0
    @Property
    lateinit var email: String

    @Relationship(type = "COMPRO", direction = Direction.OUTGOING)
    var itemsComprados = mutableListOf<ItemCompraNeo4j>()

    companion object {
        fun fromUsuario(usuario: Usuario): UsuarioNeo4j {
            val retorno = UsuarioNeo4j().apply {
                this.idSQL = usuario.id
                this.nombre = usuario.nombre
                this.apellido = usuario.apellido
                this.email = usuario.email
                this.fechaNacimiento = usuario.fechaNacimiento
                this.saldo = usuario.saldo
                usuario.compras.forEach{
                    this.itemsComprados.addAll( ItemCompraNeo4j.getItemsFromCompraForNeo4j(it) )
                }
            }
            return retorno
        }
    }
}