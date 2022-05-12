package difficult.domain

import javax.persistence.*


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Producto {
    @Column
    var nombre: String = ""
    @Column
    var descripcion: String= ""
    @Column
    var puntaje: Int = 0
    @Column
    var paisOrigen: String = ""
    @Column
    var precioBase: Double = 0.0
    @Column
    var urlImagen: String = ""

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    var lotes = mutableListOf<Lote>()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0

    fun precioTotal(): Double {
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

@Entity
class Combo : Producto() {

    @ManyToMany(fetch = FetchType.EAGER)
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

@Entity
class Piso : Producto() {
    @Column
    @Convert(converter = TipoPisoConverter::class)
    lateinit var tipo: TipoPiso
    @Column
    lateinit var terminacion : String
    var medidaX: Int = 0
    var medidaZ: Int = 0

    override fun precioTotal(): Double {
        return super.precioTotal() * tipo.porcentajeIncremento
    }

    @Column
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

@Entity
class Pintura: Producto(){
    @Column
    var litros = 0
    @Column
    var rendimiento = 0
    @Column
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

@Converter
class TipoPisoConverter : AttributeConverter<TipoPiso, String>{

    override fun convertToDatabaseColumn(tipoPiso: TipoPiso): String {
        return tipoPiso.tipoNombre()
    }

    override fun convertToEntityAttribute(tipoPisoString: String): TipoPiso {
        return if (tipoPisoString == "Normal") {
            PisoNormal()
        } else{
            PisoAltoTransito()
        }
    }
}

