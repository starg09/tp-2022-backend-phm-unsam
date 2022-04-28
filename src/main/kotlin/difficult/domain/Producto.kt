package difficult.domain

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import javax.persistence.*


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Productos")
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

    @OneToMany
    var lotes = mutableListOf<Lote>()

    @Id
    @GeneratedValue
    var id: Int = 0

    open fun precioTotal(): Double {
        return precioBase
    }

    fun elegirUnLote(loteNumber: Int): Lote {
        return lotes.first { it.numeroLote == loteNumber }
    }



    abstract fun toProductoDTO() : ProductoDTO
}

@Entity
class Combo : Producto() {

    @ManyToMany
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
    // TODO: var pair: Pair<Int, Int> = 0 to 0

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

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipoPiso"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = PisoAltoTransito::class, name = "Alto transito"),
    JsonSubTypes.Type(value = PisoNormal::class, name = "Normal")
)
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
    var descripcionDto: String= ""
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

    private val mapper = ObjectMapper().apply {
        activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL)
    }
    val ptv = BasicPolymorphicTypeValidator.builder().allowIfSubType("com.baeldung.jackson.inheritance").allowIfSubType("java.util.ArrayList").build()

    override fun convertToDatabaseColumn(tipoPiso: TipoPiso): String {
        return tipoPiso.tipoNombre()
    }

    override fun convertToEntityAttribute(tipoPisoString: String): TipoPiso {
        return mapper.readValue(tipoPisoString, TipoPiso::class.java)
    }
}

