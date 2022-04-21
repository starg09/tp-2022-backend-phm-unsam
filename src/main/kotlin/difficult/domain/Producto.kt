package difficult.domain

import CantidadInsuficienteLoteException

abstract class Producto {
    var nombre: String = ""
    var descripcion: String = ""
    var puntaje: Int = 0
    var paisOrigen: String = ""
    var precioBase: Double = 0.0
    var urlImagen: String = ""
    var lotes = listOf<Lote>()
    var id: Int = 0

    open fun precioTotal(): Double {
        return precioBase
    }

    fun elegirUnLote(loteNumber: Int): Lote {
        return lotes.first { it.numeroLote == loteNumber }
    }



    abstract fun toProductoDTO() : ProductoDTO
}

class Combo : Producto() {
    var productos = mutableSetOf<Producto>()

    fun agregarProducto(unProducto: Producto){
        productos.add(unProducto)
    }

    override fun precioTotal(): Double {
        return ((productos.fold(0.0) { acum, producto -> acum + producto.precioTotal() + 20.0 })  * 0.85)
    }

    override fun toProductoDTO(): ComboDTO {
        return ComboDTO().apply {
            nombreDto = nombre
            descripcionDto = descripcion
            urlImagenDto = urlImagen
            puntajeDto = puntaje
            paisOrigenDto = paisOrigen
            precioDto = precioTotal()
            lotesDto = lotes.map { it.toLoteDTO() }
            idDto = id
            productosDto = productos.map { it.toProductoDTO() }
        }
    }

}

class Piso : Producto() {
    lateinit var tipo: TipoPiso
    lateinit var terminacion : String
    var medidaX: Int = 0
    var medidaZ: Int = 0
    // TODO: var pair: Pair<Int, Int> = 0 to 0

    override fun precioTotal(): Double {
        return super.precioTotal() * tipo.porcentajeIncremento
    }

    fun medidas(): String {
        return "$medidaX X $medidaZ"
    }

    override fun toProductoDTO(): PisoDTO {
        return PisoDTO().apply {
            nombreDto = nombre
            descripcionDto = descripcion
            urlImagenDto = urlImagen
            puntajeDto = puntaje
            paisOrigenDto = paisOrigen
            precioDto = precioTotal()
            lotesDto = lotes.map { it.toLoteDTO() }
            idDto = id
            terminacionDto = terminacion
            medidasDto = medidas()
            tipoDto = tipo.tipoNombre()
        }
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

    override fun toProductoDTO(): PinturaDTO {
        return PinturaDTO().apply {
            nombreDto = nombre
            descripcionDto = descripcion
            urlImagenDto = urlImagen
            puntajeDto = puntaje
            paisOrigenDto = paisOrigen
            precioDto = precioTotal()
            lotesDto = lotes.map { it.toLoteDTO() }
            idDto = id
            rendimientoDto = rendimiento
            colorDto = color
            litrosDto = litros
        }
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

abstract class ProductoDTO {
    var nombreDto: String = ""
    var descripcionDto: String = ""
    var urlImagenDto: String = ""
    var puntajeDto: Int = 0
    var paisOrigenDto: String = ""
    var precioDto: Double = 0.0
    var lotesDto = listOf<LoteDTO>()
    var idDto: Int = 0
}

class PinturaDTO : ProductoDTO() {
    var rendimientoDto: Int = 0
    var colorDto: String = ""
    var litrosDto: Int = 0
}

class PisoDTO : ProductoDTO() {
    var terminacionDto: String = ""
    var medidasDto: String = ""
    var tipoDto: String = ""
}

class ComboDTO : ProductoDTO() {
    var productosDto = listOf<ProductoDTO>()
}

