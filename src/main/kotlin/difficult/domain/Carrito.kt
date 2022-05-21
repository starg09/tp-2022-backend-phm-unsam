package difficult.domain

import YaEstaEnElCarritoException

class Carrito {


    var carritoId: Int = 0
    var productosEnCarrito = mutableListOf<ProductoCarrito>()

    fun agregarProducto(producto: Producto, cantidad: Int, lote: Lote){
        if (this.getNumerosLote().contains(lote.id)){
            throw YaEstaEnElCarritoException("el producto seleccionado ya esta en el carrito")
        }
        lote.chequearCantidadDisponible(cantidad)
        val productoPorAgregar = ProductoCarrito().apply{
            this.producto = producto
            this.cantidad = cantidad
            this.lote = lote
        }
        productosEnCarrito.add(productoPorAgregar)
    }

    fun getProductos(): List<Producto>{
        return productosEnCarrito.map{it.producto}
    }

    fun getNumerosLote(): List<Int>{
        return productosEnCarrito.map{it.lote.id}
    }

    fun eliminarProducto(productoABorrar: Producto){
        productosEnCarrito.removeIf { productoABorrar.id == it.producto.id }
    }

    fun vaciar(){
        productosEnCarrito.clear()
    }

    fun disminurLotes(){
        productosEnCarrito.forEach { it.disminuirCantidadDisponible() }
    }

    fun cantidadProductos(): Int {
        return productosEnCarrito.sumOf { it.cantidad }
    }

    fun precioTotal(): Double {
        return productosEnCarrito.sumOf { it.producto.precioTotal() * it.cantidad }
    }

    fun productosDisponibles() {
        productosEnCarrito.forEach { it.loteDisponible() }
    }

}

class ProductoCarrito {

    lateinit var producto : Producto

    lateinit var lote : Lote

    var cantidad : Int = 1

    fun disminuirCantidadDisponible(){
        lote.disminuirCantidadDisponible(cantidad)
    }

    fun loteDisponible(){
        lote.chequearCantidadDisponible(cantidad)
    }
}