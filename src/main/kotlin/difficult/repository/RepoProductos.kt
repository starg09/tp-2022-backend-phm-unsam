package difficult.repository

import difficult.domain.Producto
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository

interface RepoProductos : MongoRepository<Producto, Int> {


    fun findAllByNombreContainsAndPaisOrigenInAndPuntajeGreaterThanEqual(nombre: String, paisOrigen: List<String>, puntaje: Int): List<Producto>

    fun findAllByNombreContainsAndPuntajeGreaterThanEqual(nombre: String, puntaje: Int): List<Producto>

    @Aggregation(pipeline = ["{ '\$group': { '_id' : '\$paisOrigen' } }"])
    fun findDistinctPaisOrigens(): List<String>
}