package difficult.domain

import YaEstaEnElCarritoException

class Carrito {
    var productosEnCarrito = mutableListOf<Triple<Producto, Int, Lote>>()


    fun agregarProducto(producto: Producto, cantidad: Int, lote: Lote){
        if (productosEnCarrito.contains(Triple(producto, cantidad, lote))){
            throw YaEstaEnElCarritoException("el producto seleccionado ya esta en el carrito")
        }
        lote.chequearCantidadDisponible(cantidad)
        productosEnCarrito.add(Triple(producto, cantidad, lote))
    }

    fun eliminarProducto(producto: Producto){
        val productos = productosEnCarrito.map { it.first }
        productosEnCarrito.removeIf { productos.contains(producto) }
    }

    fun vaciar(){
        productosEnCarrito.clear()
    }

    fun disminurLotes(){
        productosEnCarrito.forEach { it.third.disminuirCantidadDisponible(it.second) }
    }

    fun catidadProductos(): Int {
        return productosEnCarrito.fold(0){ acum, it -> acum + it.second }
    }

    fun precioTotal(): Double {
        return productosEnCarrito.fold(0.0) { acum, it -> acum + it.first.precioTotal() * it.second }
    }

}