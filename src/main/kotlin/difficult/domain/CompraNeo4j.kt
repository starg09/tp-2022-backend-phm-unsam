package difficult.domain

import org.springframework.data.neo4j.core.schema.*
import java.time.LocalDate

@RelationshipProperties
class CompraNeo4j {
    @RelationshipId
    @GeneratedValue
    var ordenCompra: Long? = null

    @Property
    lateinit var fechaCompra: LocalDate

    @TargetNode
    var items = mutableListOf<ItemCompra>()
}

@Node("ItemCompra")
class ItemCompraNeo4j {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Property
    lateinit var nombreProducto: String
    @Property
    var numeroLote: Int = -1
    @Property
    var precioUnitario: Double = 0.0
    @Property
    var cantidad = 0
}