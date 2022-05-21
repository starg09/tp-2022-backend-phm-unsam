package difficult.domain

import org.springframework.data.annotation.Reference
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.web.bind.annotation.Mapping
import javax.persistence.*

@Document(collection = "productos")
abstract class Producto {
    @Id
    var id: Int = 0
    var nombre: String = ""
    var descripcion: String= ""
    var puntaje: Int = 0
    var paisOrigen: String = ""
    var precioBase: Double = 0.0
    var urlImagen: String = ""
    //@Reference
    //@DBRef
    var lotes = mutableListOf<Lote>()

    open fun precioTotal(): Double {
        return precioBase * descuentoPorLote()
    }

    fun agregarLote(lote: Lote){
        lotes.add(lote)
    }

    fun descuentoPorLote(): Double {
        return if (lotes.any { it.loteDeMasDeCuatroMeses() }){
            0.9
        } else {
            1.0
        }
    }

    abstract fun toProductoDTO() : ProductoDTO
}

@Document(collection = "productos")
class Piso : Producto() {
    var esAltoTransito: Boolean = false
    lateinit var terminacion : String
    var medidaX: Int = 0
    var medidaZ: Int = 0

    override fun precioTotal(): Double {
        return super.precioTotal() * porcentajeIncremento()
    }

    fun medidas(): String {
        return "$medidaX X $medidaZ"
    }

    fun porcentajeIncremento(): Double {
        return if (esAltoTransito){
            1.20
        }
        else {
            1.0
        }
    }

    fun tipoPiso(): String {
        return if (esAltoTransito){
            "Alto transito"
        }
        else {
            "Normal"
        }
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
            tipoDto = tipoPiso()
        }
    }

}

@Document(collection = "productos")
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

@Document(collection = "productos")
class Combo : Producto() {

    @Embedded
    var productos = mutableSetOf<Producto>()

    fun agregarProducto(unProducto: Producto){
        productos.add(unProducto)
    }

    override fun precioTotal(): Double {
        return (productos.sumOf { it.precioTotal() + 20.0 })  * 0.85
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