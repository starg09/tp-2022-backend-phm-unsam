package difficult.repository

import difficult.domain.UsuarioNeo4j
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RepoUsuariosNeo4j : Neo4jRepository<UsuarioNeo4j, Long>{

//    fun findByContraseniaAndEmail(password: String, email: String): Optional<UsuarioNeo4j>
//
    @Query("MATCH (n) DETACH DELETE n")
    fun wipeEverything()
    fun findByEmail(email: String): UsuarioNeo4j
//
//    override fun findById(id: Int): Optional<UsuarioNeo4j>

}