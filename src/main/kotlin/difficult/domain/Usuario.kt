package difficult.domain

import SaldoInsuficienteException
import java.time.LocalDate
import java.time.Period
import javax.persistence.*

@Entity
class Usuario(){
    @Column
    lateinit var nombre: String
    @Column
    lateinit var apellido: String
    @Column
    lateinit var fechaNacimiento: LocalDate
    @Column
    var saldo: Double = 0.0
    @Column
    lateinit var email: String
    @Column
    lateinit var contrasenia: String

    val carrito = Carrito()

    @OneToMany
    val compras = mutableSetOf<Compra>()

    @Id
    @GeneratedValue
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
}