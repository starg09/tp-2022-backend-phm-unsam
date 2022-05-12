package difficult.repository

import difficult.domain.Producto
import org.springframework.data.repository.CrudRepository

interface RepoProductos : CrudRepository<Producto, Int> {

    fun findAllByNombreContainsAndPaisOrigenInAndPuntajeGreaterThanEqual(nombre: String, paisOrigen: List<String>, puntaje: Int): List<Producto>

    fun findAllByNombreContainsAndPuntajeGreaterThanEqual(nombre: String, puntaje: Int): List<Producto>
}