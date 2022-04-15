package difficult.domain

import SaldoInsuficienteException
import java.time.LocalDate
import java.time.Period

class Usuario(var nombre: String, var apellido: String, val fechaNacimiento: LocalDate, var saldo: Double, var email: String, var contrasenia: String){

    val carrito = Carrito()
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

    fun agregarAlCarrito(producto: Producto, cantidad: Int, numeroLote: Int){
        val lote = producto.elegirUnLote(numeroLote)
        carrito.agregarProducto(producto, cantidad, lote)
    }

    fun eliminarDelCarrito(producto: Producto){
        carrito.eliminarProducto(producto)
    }

    fun vaciarCarrito(){
        carrito.vaciar()
    }

    fun realizarCompra(orden: Int){
        saldoInsuficiente()
        val compra = Compra().apply {
            ordenCompra = orden
            fechaCompra = LocalDate.now()
            cantidad = cantidadProductosCarrito()
            importe = importeTotalCarrito()
            productosComprados.addAll(carrito.productosEnCarrito.map { it.first })
        }
        compras.add(compra)
        disminuirSaldo(importeTotalCarrito())
        carrito.disminurLotes()
        vaciarCarrito()
    }

    fun saldoInsuficiente(){
        if (importeTotalCarrito() > saldo) {
            throw SaldoInsuficienteException("El saldo es insuficiente")
        }
    }

    fun cantidadProductosCarrito(): Int {
        return carrito.catidadProductos()
    }

    fun importeTotalCarrito(): Double{
        return carrito.precioTotal()
    }

    fun productosComprados(): Set<String> {
        return compras.map { it.nombreProductos() }.flatten().toSet()
    }
}