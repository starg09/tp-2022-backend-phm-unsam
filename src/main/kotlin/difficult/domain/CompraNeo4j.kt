package difficult.domain

import org.springframework.data.neo4j.core.schema.GeneratedValue
import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import org.springframework.data.neo4j.core.schema.RelationshipId
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode
import java.time.LocalDate

@Node("Compra")
class CompraNeo4j {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Property
    lateinit var fechaCompra: LocalDate

    @Property
    var importeTotal: Double = -1.0
    
    companion object {
        fun fromCompra(compra: Compra): CompraNeo4j {
            return CompraNeo4j().apply {
                this.fechaCompra = compra.fechaCompra
                this.importeTotal = compra.getImporteTotal()
            }
        }

    }
}


@RelationshipProperties
class ItemCompraNeo4j {
    @RelationshipId
    @GeneratedValue
    var id: Long? = null

    @Property
    lateinit var nombreProducto: String
    @Property
    var numeroLote: Int = -1
    @Property
    var precioUnitario: Double = -1.0
    @Property
    var cantidad = -1

    @TargetNode
    lateinit var compra: CompraNeo4j

    companion object {
        fun fromItemCompra (itemCompra: ItemCompra, compra: CompraNeo4j): ItemCompraNeo4j {
            return ItemCompraNeo4j().apply {
                this.nombreProducto = itemCompra.nombreProducto
                this.numeroLote = itemCompra.numeroLote
                this.precioUnitario = itemCompra.precioUnitario
                this.cantidad = itemCompra.cantidad
                this.compra = compra
            }
        }
        fun getItemsFromCompraForNeo4j(compra: Compra): List<ItemCompraNeo4j> {
            val compraNeo4j = CompraNeo4j.fromCompra(compra);
            return compra.items.map{item -> ItemCompraNeo4j.fromItemCompra(item, compraNeo4j)}
        }
    }
}