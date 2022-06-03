package difficult.domain

import org.springframework.data.neo4j.core.schema.Id
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import org.springframework.data.neo4j.core.schema.RelationshipId
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode
import java.time.LocalDate
import javax.persistence.*

@Entity
@RelationshipProperties
class Compra {
    @RelationshipId //Neo4j
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var ordenCompra: Long = 0

    @Column
    @Property
    lateinit var fechaCompra: LocalDate

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_compra")
    @TargetNode
    var productos = mutableListOf<ProductoCompra>()

    fun getCantidadProductos() = productos.size
    fun getImporteTotal() = productos.sumOf{it.precioTotal()}
}

@Entity
@Node("ProductoCompra")
class ProductoCompra {
    @Id //Neo4j
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Property
    @Column
    lateinit var nombreProducto: String
    @Property
    @Column
    var idProducto: Int = -1
    @Property
    @Column
    var numeroLote: Int = -1
    @Property
    @Column
    var precioUnitario: Double = 0.0
    @Property
    @Column
    var cantidad = 0

    fun precioTotal() = precioUnitario * cantidad
}