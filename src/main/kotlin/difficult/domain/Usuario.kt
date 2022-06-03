package difficult.domain

import CarritoVacioException
import SaldoInsuficienteException
import org.neo4j.cypherdsl.core.Relationship.Direction
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Property
import org.springframework.data.neo4j.core.schema.Relationship
import java.time.LocalDate
import java.time.Period
import javax.persistence.*

@Entity
@Node("Usuario")
class Usuario {
    @Column
    @Property
    lateinit var nombre: String
    @Column
    @Property
    lateinit var apellido: String
    @Column
    @Property
    lateinit var fechaNacimiento: LocalDate
    @Column
    @Property
    var saldo: Double = 0.0
    @Column
    @Property
    lateinit var email: String
    @Column
    @Property
    lateinit var contrasenia: String

    @Transient
    var carrito = Carrito()

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "id_usuario")
    @Relationship(type = "COMPRO", direction = Relationship.Direction.OUTGOING)
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

    fun eliminarDelCarrito(productoId: Int, loteId: Int){
        carrito.eliminarProducto(productoId, loteId)
    }

    fun vaciarCarrito(){
        carrito.vaciar()
    }

    fun carritoVacio(){
        if (carrito.cantidadProductos() == 0) {
            throw CarritoVacioException("El carrito esta vacio")
        }
    }


    fun realizarCompra(): List<Producto>{
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
        val productosActualizados = carrito.productosEnCarrito.map { it.producto }
        vaciarCarrito()
        return  productosActualizados
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