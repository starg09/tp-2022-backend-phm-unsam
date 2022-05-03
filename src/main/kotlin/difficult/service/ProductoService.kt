package difficult.service

import com.fasterxml.jackson.databind.ObjectMapper
import difficult.domain.*
import difficult.repository.Filtro
import difficult.repository.RepoProductos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ProductoService {

    @Autowired
    private lateinit var repoProductos: RepoProductos

    @Autowired
    private lateinit var mapperObject: ObjectMapper

    @Transactional(readOnly = true)
    fun getProductos(): List<ProductoDTO> {
        return toProductosDTO(repoProductos.findAll().toList())
    }

    @Transactional(readOnly = true)
    fun filtrar(filtrosDTO: List<FiltroDTO>): List<ProductoDTO> {
        val filtros = toFiltros(filtrosDTO)
        //return toProductosDTO(repoProductos.filtrar(filtros))
        return listOf()
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

    @Transactional(readOnly = true)
    fun getLotes(): List<Lote>{
        return repoProductos.findAll().map { it.lotes }.flatten()
    }

    @Transactional(readOnly = true)
    fun productoDetalles(id: Int): ProductoDTO {
        return getById(id).toProductoDTO()
    }

    fun getById(productoId: Int): Producto {
        return repoProductos.findById(productoId).get()
    }

}





class FiltroDTO {
    var type: String = ""
    var valor: String = ""
}