package ar.edu.unsam.phm.backendtp2022phmgrupo2.domain

open class Producto {
    var nombre: String = ""
    var descripcion: String= ""
    var puntaje: Int = 0
    var paisOrigen: String = ""
    var precioBase: Double = 0.0

    open fun precioTotal(): Double {
        return precioBase
    }
}

class Combo() {
    var productos = mutableSetOf<Producto>()

    fun agregarProducto(unProducto: Producto){
        productos.add(unProducto)
    }

    fun eliminarProducto(unProducto: Producto){
        productos.add(unProducto)
    }

    fun precioTotal(): Double {
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
        return medidaX.toString() + "X" + medidaZ.toString()
    }

}

class Pintura: Producto(){
    var litros = 0
    var rendimiento = 0
    lateinit var color: String

    override fun precioTotal(): Double {
        return super.precioTotal()
    }

    fun rendimientoMayor(): Double {
        if (rendimiento > 8) {
            return 1.25
        }
        else return 1.0
    }
}

open class TipoPiso {
    open val porcentajeIncremento: Double = 1.0
}

class PisoAltoTransito : TipoPiso() {
    override val porcentajeIncremento = 1.20
}

class PisoNormal : TipoPiso() {}