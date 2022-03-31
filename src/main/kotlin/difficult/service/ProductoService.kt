package difficult.service

import com.fasterxml.jackson.databind.ObjectMapper
import difficult.domain.*
import difficult.repository.Filtro
import difficult.repository.RepoProductos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ProductoService {

    @Autowired
    private lateinit var repoProductos: RepoProductos

    @Autowired
    private lateinit var mapperObject: ObjectMapper

    fun getProductos(): List<ProductoDTO> {
        return toProductosDTO(repoProductos.elementos.toList())
    }

    fun getProductosFiltrados(): List<ProductoDTO> {
        return toProductosDTO(repoProductos.filtrar())
    }

    fun toProductosDTO(productos: List<Producto>): List<ProductoDTO> {
        return productos.map { toProductoDTO(it) }
    }

    fun toProductoDTO(producto: Producto): ProductoDTO{

        //TODO: arreglar esta aberracion

        return if (producto::class == Pintura::class) {
            toPinturaDTO(producto as Pintura)
        } else if (producto::class == Piso::class){
            toPisoDTO(producto as Piso)
        } else {
            toComboDTO(producto as Combo)
        }
    }

    fun toPinturaDTO(pintura: Pintura): PinturaDTO {
        return PinturaDTO().apply {
            nombre = pintura.nombre
            descripcion = pintura.descripcion
            puntaje = pintura.puntaje
            paisOrigen = pintura.paisOrigen
            precio = pintura.precioTotal()
            lotes = pintura.lotes.map { toLoteDTO(it) }
            id = pintura.id
            rendimiento = pintura.rendimiento
            color = pintura.color
            litros = pintura.litros
        }
    }

    fun toPisoDTO(piso: Piso): PisoDTO {
        return PisoDTO().apply {
            nombre = piso.nombre
            descripcion = piso.descripcion
            puntaje = piso.puntaje
            paisOrigen = piso.paisOrigen
            precio = piso.precioTotal()
            lotes = piso.lotes.map { toLoteDTO(it) }
            id = piso.id
            terminacion = piso.terminacion
            medidas = piso.medidas()
            tipo = piso.tipo.tipoNombre()
        }
    }

    fun toComboDTO(combo: Combo): ComboDTO {
        return ComboDTO().apply {
            nombre = combo.nombre
            descripcion = combo.descripcion
            puntaje = combo.puntaje
            paisOrigen = combo.paisOrigen
            precio = combo.precioTotal()
            lotes = combo.lotes.map { toLoteDTO(it) }
            id = combo.id
            productos = combo.productos.map { toProductoDTO(it) }
        }
    }

    fun toLoteDTO(lote: Lote): LoteDTO {
        return LoteDTO().apply {
            cantidadDisponible = lote.cantidadDisponible
            numeroLote = lote.numeroLote
        }
    }

    fun establecerFiltros(filtrosDTO: List<FiltroDTO>) {
        val filtros = toFiltros(filtrosDTO)
        repoProductos.filtros = filtros
    }

    fun toFiltros(filtrosDTO: List<FiltroDTO>): List<Filtro> {

        return filtrosDTO.map { toFiltro(it) }
    }

    fun toFiltro(filtroDTO: FiltroDTO): Filtro {
        val json = mapperObject.writeValueAsString(filtroDTO)
        val filtro = mapperObject.readValue(json, Filtro::class.java)
        filtro.valor = filtroDTO.valor
        return  filtro
    }

    fun getLotes(): List<Lote>{
        return repoProductos.elementos.map { it.lotes }.flatten()
    }

    fun productoDetalles(id: Int): ProductoDTO {
        return toProductoDTO(repoProductos.getById(id))
    }

}

abstract class ProductoDTO {
    var nombre: String = ""
    var descripcion: String= ""
    var puntaje: Int = 0
    var paisOrigen: String = ""
    var precio: Double = 0.0
    var lotes = listOf<LoteDTO>()
    var id: Int = 0
}

class PinturaDTO : ProductoDTO() {
    var rendimiento: Int = 0
    var color: String = ""
    var litros: Int = 0
}

class PisoDTO : ProductoDTO() {
    var terminacion: String = ""
    var medidas: String = ""
    var tipo: String = ""
}

class ComboDTO : ProductoDTO() {
    var productos = listOf<ProductoDTO>()
}

class LoteDTO {
    var cantidadDisponible: Int = 0
    var numeroLote: Int = 0
}

class FiltroDTO {
    var type: String = ""
    var valor: String = ""
}