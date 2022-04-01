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

    fun filtrar(filtrosDTO: List<FiltroDTO>): List<ProductoDTO> {
        val filtros = toFiltros(filtrosDTO)
        return toProductosDTO(repoProductos.filtrar(filtros))
    }

    fun toProductosDTO(productos: List<Producto>): List<ProductoDTO> {
        return productos.map { it.toProductoDTO() }
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
        return repoProductos.getById(id).toProductoDTO()
    }

}





class FiltroDTO {
    var type: String = ""
    var valor: String = ""
}