package difficult.domain

import CarritoVacioException
import SaldoInsuficienteException
import java.time.LocalDate
import java.time.Period
import javax.persistence.*

@Entity
class Usuario {
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

    @Transient
    var carrito = Carrito()

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val compras = mutableSetOf<Compra>()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    fun agregarAlCarrito(producto: Producto, cantidad: Int, lote: Lote){
        carrito.agregarProducto(producto, cantidad, lote)
    }

    fun eliminarDelCarrito(producto: Producto){
        carrito.eliminarProducto(producto)
    }

    fun vaciarCarrito(){
        carrito.vaciar()
    }

    fun carritoVacio(){
        if (carrito.cantidadProductos() == 0) {
            throw CarritoVacioException("El carrito esta vacio")
        }
    }

    fun tamanioCarrito() = carrito.productosEnCarrito.size

    fun realizarCompra(){
        carritoVacio()
        saldoInsuficiente()
        carrito.productosDisponibles()
        val compra = Compra().apply {
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
        return carrito.cantidadProductos()
    }

    fun importeTotalCarrito(): Double{
        return carrito.precioTotal()
    }
}