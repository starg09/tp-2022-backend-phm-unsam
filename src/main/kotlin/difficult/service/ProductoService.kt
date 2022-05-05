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
    fun filtrar(filtrosDTO: FiltroDTO): List<ProductoDTO> {
        val productosFiltrados = mutableSetOf<Producto>()
        productosFiltrados.addAll(repoProductos.findAllByNombreContainsAndPaisOrigenAndPuntajeGreaterThanEqual(filtrosDTO.nombre, filtrosDTO.pais, filtrosDTO.puntaje))
        /*productosFiltrados.addAll(repoProductos.findAllByNombreContains(filtrosDTO.nombre))
        productosFiltrados.addAll(repoProductos.findAllByPaisOrigen(filtrosDTO.pais))
        productosFiltrados.addAll(repoProductos.findAllByPuntajeGreaterThanEqual(filtrosDTO.puntaje))*/
        return toProductosDTO(productosFiltrados.toList())
    }

    fun toProductosDTO(productos: List<Producto>): List<ProductoDTO> {
        return productos.map { it.toProductoDTO() }
    }

    /*fun toFiltros(filtrosDTO: List<FiltroDTO>): List<Filtro> {

        return filtrosDTO.map { toFiltro(it) }
    }*/

    /*fun toFiltro(filtroDTO: FiltroDTO): Filtro {
        val json = mapperObject.writeValueAsString(filtroDTO)
        val filtro = mapperObject.readValue(json, Filtro::class.java)
        filtro.valor = filtroDTO.valor
        return  filtro
    }*/

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

    fun encontrarNombre(nombres: String): List<ProductoDTO> {
        return toProductosDTO(repoProductos.findAllByNombreContains(nombres))
    }

}





class FiltroDTO {
    var nombre: String = ""
    var pais: String = ""
    var puntaje: Int = 5
}