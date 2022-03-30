package difficult.domain

import java.time.LocalDate
import java.time.Period

class Usuario(var nombre: String, var apellido: String, val fechaNacimiento: LocalDate, var saldo: Double, var contrasenia: String){

    val carrito = mutableMapOf<Producto, Int>()
    val compras = mutableSetOf<Compra>()
    var id = 0

    fun edad(): Int{
        return Period.between(fechaNacimiento, LocalDate.now()).years
    }

    fun aumentarSaldo(monto: Double){
        saldo += monto
    }

    fun disminuirSaldo(monto: Double){
        saldo -= monto
    }

    fun agregarAlCarrito(producto: Producto, cantidad: Int){
        carrito[producto] = cantidad
    }

    fun eliminarDelCarrito(producto: Producto){
        carrito.remove(producto)
    }

    fun vaciarCarrito(){
        carrito.clear()
    }

    fun realizarCompra(orden: Int){
        val compra = Compra().apply {
            ordenCompra = orden
            fechaCompra = LocalDate.now()
            cantidad = cantidadProductosCarrito()
            importe = importeTotalCarrito()
        }
        compras.add(compra)
        disminuirSaldo(importeTotalCarrito())
        carrito.keys.forEach(){ producto -> producto.disminuirLote(carrito[producto]!!) }
        vaciarCarrito()
    }

    fun cantidadProductosCarrito(): Int {
        return carrito.values.fold(0) { acum, cantidad -> acum + cantidad }
    }

    fun importeTotalCarrito(): Double{
        return carrito.keys.fold(0.0) { acum, producto -> acum + producto.precioTotal() * carrito[producto]!! }
    }
}