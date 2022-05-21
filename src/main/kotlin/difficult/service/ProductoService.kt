package difficult.service

import difficult.domain.*
import difficult.repository.RepoLotes
import difficult.repository.RepoProductos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class ProductoService {

    @Autowired
    private lateinit var repoProductos: RepoProductos
    @Autowired
    private lateinit var repoLotes: RepoLotes

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    @Transactional(readOnly = true)
    fun getProductos(): List<ProductoDTO> {
        return toProductosDTO(repoProductos.findAll().toList())
    }

    @Transactional(readOnly = true)
    fun filtrar(filtrosDTO: FiltroDTO): List<ProductoDTO> {
        val productosFiltrados = mutableSetOf<Producto>()
        val metodoFiltro = if (filtrosDTO.paises.isEmpty())
            { repo: RepoProductos ->
                repo.findAllByNombreIgnoreCaseContainsAndPuntajeGreaterThanEqual(
                    filtrosDTO.nombre,
                    filtrosDTO.puntaje
                )
            }
        else
            { repo: RepoProductos ->
                repo.findAllByNombreIgnoreCaseContainsAndPaisOrigenInAndPuntajeGreaterThanEqual(
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

    @Transactional(readOnly = true)
    fun getLotes(): List<Lote>{
        return repoLotes.findAll().toList()
    }

    @Transactional(readOnly = true)
    fun productoDetalles(id: Int): ProductoDTO {
        return getById(id).toProductoDTO()
    }

    fun getById(productoId: Int): Producto {
        return repoProductos.findById(productoId).get()
    }

    fun obtenerPaisesOrigen(): List<String> {
        return repoProductos.findDistinctPaisOrigen()
    }


}





class FiltroDTO {
    var nombre: String = ""
    var paises: List<String> = listOf()
    var puntaje: Int = 5
}