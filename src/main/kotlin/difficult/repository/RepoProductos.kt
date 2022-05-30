package difficult.repository

import difficult.domain.Lote
import difficult.domain.Producto
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository

interface RepoProductos : MongoRepository<Producto, Int> {

    fun findAllByNombreIgnoreCaseContainsAndPaisOrigenInAndPuntajeGreaterThanEqual(nombre: String, paisOrigen: List<String>, puntaje: Int): List<Producto>

    fun findAllByNombreIgnoreCaseContainsAndPuntajeGreaterThanEqual(nombre: String, puntaje: Int): List<Producto>

    @Aggregation(pipeline = ["{ '\$group': { '_id' : '\$paisOrigen' } }"])
    fun findDistinctPaisOrigen(): List<String>

    @Aggregation(pipeline = [
        "{\$unwind: '\$lotes'}",
        "{\$project: {" +
            "_id: '\$lotes._id'," +
            "fechaIngreso: '\$lotes.fechaIngreso'," +
            "cantidadDisponible: '\$lotes.cantidadDisponible" +
        "}}"
    ])
    fun getLotes(): List<Lote>
}