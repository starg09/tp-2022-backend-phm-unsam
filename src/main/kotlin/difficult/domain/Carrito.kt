package difficult.domain

class Carrito {

    var productosEnCarrito = mutableListOf<ProductoCarrito>()

    fun agregarProducto(producto: Producto, cantidad: Int, lote: Lote){
        var cantidadFinal = cantidad
        if (this.getNumerosLote().contains(lote.id) && getProductos().contains(producto.id) && lote.cantidadDisponible >= cantidad){
            cantidadFinal += (productosEnCarrito.find { it.producto.id == producto.id && it.lote.id == lote.id}?.cantidad ?: 0)
            lote.chequearCantidadDisponible(cantidadFinal)
            eliminarProducto(producto.id, lote.id)

            //throw YaEstaEnElCarritoException("el producto seleccionado ya esta en el carrito")
        }
        lote.chequearCantidadDisponible(cantidadFinal)
        val productoPorAgregar = ProductoCarrito().apply{
            this.producto = producto
            this.cantidad = cantidadFinal
            this.lote = lote
        }
        productosEnCarrito.add(productoPorAgregar)
    }

    fun getProductos(): List<Int> {
        return productosEnCarrito.map{it.producto.id}
    }

    fun getNumerosLote(): List<Int>{
        return productosEnCarrito.map{it.lote.id}
    }

    fun eliminarProducto(productoABorrarId: Int, loteABorrarId: Int){
        productosEnCarrito.removeIf { productoABorrarId == it.producto.id && it.lote.id == loteABorrarId}
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