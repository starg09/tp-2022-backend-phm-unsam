package difficult.repository

import difficult.domain.Usuario
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import java.util.*

interface RepoNeo4jUsuarios : Neo4jRepository<Usuario, Int>{

    fun findByContraseniaAndEmail(password: String, email: String): Optional<Usuario>

    fun findByEmail(email: String): Optional<Usuario>

    override fun findById(id: Int): Optional<Usuario>

}