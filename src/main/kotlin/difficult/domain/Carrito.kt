package difficult.domain
import AgregarCeroUnidadesCarritoException
import CarritoVacioException
import SaldoInsuficienteException
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.annotation.Id

@RedisHash
class Carrito {

    @Id
    var id = 0

    var items = mutableListOf<ItemCarrito>()

    fun agregarProducto(producto: Producto, cantidad: Int, lote: Lote){
        if (cantidad == 0) {
            throw AgregarCeroUnidadesCarritoException("¿Para que queres agregar 0 unidades al carrito?")
        }
        var cantidadFinal = cantidad
        if (this.getNumerosLote().contains(lote.id) && getProductos().contains(producto.id) && lote.cantidadDisponible >= cantidad){
            cantidadFinal += (items.find { it.producto.id == producto.id && it.lote.id == lote.id}?.cantidad ?: 0)
            lote.chequearCantidadDisponible(cantidadFinal)
            eliminarProducto(producto.id, lote.id)

            //throw YaEstaEnElCarritoException("el producto seleccionado ya esta en el carrito")
        }
        lote.chequearCantidadDisponible(cantidadFinal)
        val productoPorAgregar = ItemCarrito().apply{
            this.producto = producto
            this.cantidad = cantidadFinal
            this.lote = lote
        }
        items.add(productoPorAgregar)
    }

    fun getProductos(): List<Int> {
        return items.map{it.producto.id}
    }

    fun getNumerosLote(): List<Int>{
        return items.map{it.lote.id}
    }

    fun eliminarProducto(productoABorrarId: Int, loteABorrarId: Int){
        items.removeIf { productoABorrarId == it.producto.id && it.lote.id == loteABorrarId}
    }

    fun vaciar(){
        items.clear()
    }

    fun disminurLotes(){
        items.forEach { it.disminuirCantidadDisponible() } // TODO: preguntar sobre llamar al service desde aca
    }

    fun cantidadProductos(): Int {
        return items.sumOf { it.cantidad }
    }

    fun precioTotal(): Double {
        return items.sumOf { it.producto.precioTotal() * it.cantidad }
    }

    fun validarProductosDisponibles() {
        items.forEach { it.loteDisponible() }
    }

    fun tamanioCarrito(): Int {
        return items.size
    }

    fun validarCarritoNoEstaVacio(){
        if (cantidadProductos() == 0) {
            throw CarritoVacioException("El carrito esta vacio")
        }
    }

    fun validarAlcanzaSaldo(saldoUsuario: Double){
        if (precioTotal() > saldoUsuario) {
            throw SaldoInsuficienteException("El saldo es insuficiente")
        }
    }

}

class ItemCarrito {

    lateinit var producto : Producto

    lateinit var lote : Lote

    var cantidad : Int = 1

    fun disminuirCantidadDisponible(){
        producto.lotes.first{loteP -> loteP.id == lote.id}.disminuirCantidadDisponible(cantidad)
    }

    fun loteDisponible(){
        lote.chequearCantidadDisponible(cantidad)
    }

    fun toItemCompra(): ItemCompra {
        return ItemCompra().apply{
            nombreProducto = producto.nombre
            idProducto = producto.id
            numeroLote = lote.id
            precioUnitario = producto.precioTotal()
            cantidad = cantidad
        }
    }
}