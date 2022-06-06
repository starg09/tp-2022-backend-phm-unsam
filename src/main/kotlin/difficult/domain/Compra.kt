package difficult.domain

import java.time.LocalDate
import javax.persistence.*

@Entity
class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var ordenCompra: Long = 0

    @Column
    lateinit var fechaCompra: LocalDate

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_compra")
    var items = mutableListOf<ItemCompra>()

    fun getCantidadItems() = items.size
    fun getImporteTotal() = items.sumOf{it.precioTotal()}
}

@Entity
class ItemCompra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    //TODO: Rehacer referenciando clase Producto
    @Column
    lateinit var nombreProducto: String
    @Column
    var idProducto: Int = -1
    @Column
    var numeroLote: Int = -1
    @Column
    var precioUnitario: Double = 0.0
    @Column
    var cantidad = 0

    fun precioTotal() = precioUnitario * cantidad
}