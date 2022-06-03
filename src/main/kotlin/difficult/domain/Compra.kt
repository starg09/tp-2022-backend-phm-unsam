package difficult.domain

import org.springframework.data.neo4j.core.schema.RelationshipProperties
import java.time.LocalDate
import javax.persistence.*

@Entity
@RelationshipProperties
class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var ordenCompra: Int = 0
    lateinit var fechaCompra: LocalDate
    var productos : MutableList<ProductoCompra> = emptyList()
    var importe : Double = 0.0
}

class ProductoCompra {
    lateinit var producto: Producto
    lateinit var lote: Lote
    var cantidad = 0
}