package difficult.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo


/*@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    @JsonSubTypes.Type(value = typeof(Pintura), name = "Pintura"),
    @JsonSubTypes.Type(value = typeof(Piso), name = "Piso"),
    @JsonSubTypes.Type(value = typeof(Combo), name = "Combo")
)*/
abstract class Producto {
    var nombre: String = ""
    var descripcion: String= ""
    var puntaje: Int = 0
    var paisOrigen: String = ""
    var precioBase: Double = 0.0
    var lotes = listOf<Lote>()
    var id: Int = 0

    open fun precioTotal(): Double {
        return precioBase
    }

    fun disminuirLote(cantidadYLote: List<Int>){
        val cantidad = cantidadYLote[0]!!
        val loteNumero = cantidadYLote[1]!!
        val lote = lotes.find { it.numeroLote == loteNumero }
        lote!!.cantidadDisponible -= cantidad
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

abstract class TipoPiso {
    open val porcentajeIncremento: Double = 1.0

    abstract fun tipoNombre(): String
}

class PisoAltoTransito : TipoPiso() {
    override val porcentajeIncremento = 1.20

    override fun tipoNombre(): String {
        return "Alto Transito"
    }
}

class PisoNormal : TipoPiso() {
    override fun tipoNombre(): String {
        return "Normal"
    }
}