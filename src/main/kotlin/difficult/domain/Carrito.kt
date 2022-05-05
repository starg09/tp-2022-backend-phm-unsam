package difficult.domain

import YaEstaEnElCarritoException
import javax.persistence.*

@Entity
class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val carritoId: Long = 0

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var productosEnCarrito = mutableListOf<ProductoCarrito>()

    fun agregarProducto(producto: Producto, cantidad: Int, lote: Lote){
        val productoPorAgregar = ProductoCarrito().apply{
            this.producto = producto
            this.cantidad = cantidad
            this.lote = lote}
        if (this.getNumerosLote().contains(productoPorAgregar.lote.numeroLote)){
            throw YaEstaEnElCarritoException("el producto seleccionado ya esta en el carrito")
        }
        lote.chequearCantidadDisponible(cantidad)
        productosEnCarrito.add(productoPorAgregar)
    }

    fun getProductos(): List<Producto>{
        return productosEnCarrito.map{it.producto}
    }

    fun getNumerosLote(): List<Int>{
        return productosEnCarrito.map{it.lote.numeroLote}
    }

    fun eliminarProducto(productoABorrar: Producto){
        productosEnCarrito.removeIf { productoABorrar == it.producto }
    }

    fun vaciar(){
        productosEnCarrito.clear()
    }

    fun disminurLotes(){
        productosEnCarrito.forEach { it.lote.disminuirCantidadDisponible(it.cantidad) }
    }

    fun catidadProductos(): Int {
        return productosEnCarrito.fold(0){ acum, it -> acum + it.cantidad }
    }

    fun precioTotal(): Double {
        return productosEnCarrito.fold(0.0) { acum, it -> acum + it.producto.precioTotal() * it.cantidad }
    }

}

@Entity
class ProductoCarrito {

    //FIXME: Que no use id autogenerado, que se relacione al carritoId
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idProdCarrito: Long = 0

    @ManyToOne
    lateinit var producto : Producto
    @ManyToOne
    lateinit var lote : Lote
    @Column
    var cantidad : Int = 1
}