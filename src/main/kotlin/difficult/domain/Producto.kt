package difficult.domain

abstract class Producto {
    var nombre: String = ""
    var descripcion: String= ""
    var puntaje: Int = 0
    var paisOrigen: String = ""
    var precioBase: Double = 0.0
    lateinit var lote: Lote

    open fun precioTotal(): Double {
        return precioBase
    }

    fun disminuirLote(cantidad: Int){
        lote.cantidadDisponible -= cantidad
    }
}

class Combo(): Producto() {
    var productos = mutableSetOf<Producto>()

    fun agregarProducto(unProducto: Producto){
        productos.add(unProducto)
    }

    fun eliminarProducto(unProducto: Producto){
        productos.remove(unProducto)
    }

    override fun precioTotal(): Double {
        return ((productos.fold(0.0) { acum, producto -> acum + producto.precioTotal() }) + (productos.size * 20.0)) * 0.85
    }


}

class Piso : Producto() {
    lateinit var tipo: TipoPiso
    lateinit var terminacion : String
    var medidaX: Int = 0
    var medidaZ: Int = 0

    override fun precioTotal(): Double {
        return super.precioTotal() * tipo.porcentajeIncremento
    }

    fun medidas(): String {
        return "$medidaX X $medidaZ"
    }

}

class Pintura: Producto(){
    var litros = 0
    var rendimiento = 0
    lateinit var color: String

    override fun precioTotal(): Double {
        return super.precioTotal() * this.rendimientoMayor()
    }

    fun rendimientoMayor(): Double {
        return if (rendimiento > 8) {
            1.25
        } else 1.0
    }
}

open class TipoPiso {
    open val porcentajeIncremento: Double = 1.0
}

class PisoAltoTransito : TipoPiso() {
    override val porcentajeIncremento = 1.20
}

class PisoNormal : TipoPiso() {}