package difficult.repository

import difficult.domain.Producto
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.util.Optional

interface RepoProductos : MongoRepository<Producto, Int> {

    @Query("{ 'id' : ?0 }")
    fun findById(id: String): Optional<Producto>

    fun findAllByNombreContainsAndPaisOrigenInAndPuntajeGreaterThanEqual(nombre: String, paisOrigen: List<String>, puntaje: Int): List<Producto>

    fun findAllByNombreContainsAndPuntajeGreaterThanEqual(nombre: String, puntaje: Int): List<Producto>
}