package difficult.domain

import YaEstaEnElCarritoException
import java.time.LocalDate
import java.time.Period

class Usuario(var nombre: String, var apellido: String, val fechaNacimiento: LocalDate, var saldo: Double, var email: String, var contrasenia: String){

    val carrito = mutableMapOf<Producto, List<Int>>() // el primer entero es para la cantidad y el segundo para el lote
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

    fun agregarAlCarrito(producto: Producto, cantidad: Int, lote: Int){
        if (carrito.containsKey(producto)){
            throw YaEstaEnElCarritoException(" ")
        }
        chequearCantidad(producto, cantidad, lote)
        carrito[producto] = listOf(cantidad, lote)
    }

    fun chequearCantidad(producto: Producto, cantidad: Int, lote: Int){
        val unLote = producto.elegirUnLote(lote)
        producto.cantidadLoteSuficiente(unLote!!, cantidad)
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
        //TODO agregar excepcion de saldo
    }

    fun cantidadProductosCarrito(): Int {
        return carrito.values.fold(0) { acum, it -> acum + it[0] }
    }

    fun importeTotalCarrito(): Double{
        return carrito.keys.fold(0.0) { acum, producto -> acum + producto.precioTotal() * carrito[producto]!![0] }
    }
}