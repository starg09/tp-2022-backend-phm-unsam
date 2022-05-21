package difficult.repository

import difficult.domain.Producto
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository

interface RepoProductos : MongoRepository<Producto, Int> {

    fun findAllByNombreIgnoreCaseContainsAndPaisOrigenInAndPuntajeGreaterThanEqual(nombre: String, paisOrigen: List<String>, puntaje: Int): List<Producto>

    fun findAllByNombreIgnoreCaseContainsAndPuntajeGreaterThanEqual(nombre: String, puntaje: Int): List<Producto>

    @Aggregation(pipeline = ["{ '\$group': { '_id' : '\$paisOrigen' } }"])
    fun findDistinctPaisOrigen(): List<String>
}