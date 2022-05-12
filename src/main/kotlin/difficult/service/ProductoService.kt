package difficult.service

import difficult.domain.*
import difficult.repository.RepoProductos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ProductoService {

    @Autowired
    private lateinit var repoProductos: RepoProductos

    @Transactional(readOnly = true)
    fun getProductos(): List<ProductoDTO> {
        return toProductosDTO(repoProductos.findAll().toList())
    }

    @Transactional(readOnly = true)
    fun filtrar(filtrosDTO: FiltroDTO): List<ProductoDTO> {
        val productosFiltrados = mutableSetOf<Producto>()
        val metodoFiltro = if (filtrosDTO.paises.isEmpty())
            { repo: RepoProductos ->
                repo.findAllByNombreContainsAndPuntajeGreaterThanEqual(
                    filtrosDTO.nombre,
                    filtrosDTO.puntaje
                )
            }
        else
            { repo: RepoProductos ->
                repo.findAllByNombreContainsAndPaisOrigenInAndPuntajeGreaterThanEqual(
                    filtrosDTO.nombre,
                    filtrosDTO.paises,
                    filtrosDTO.puntaje

                )
            }
        productosFiltrados.addAll(metodoFiltro(repoProductos))
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

}





class FiltroDTO {
    var nombre: String = ""
    var paises: List<String> = listOf()
    var puntaje: Int = 5
}