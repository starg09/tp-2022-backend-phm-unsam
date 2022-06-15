package difficult.repository

import difficult.domain.Usuario
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RepoUsuarios : JpaRepository<Usuario, Int> {

    fun findByContraseniaAndEmail(password: String, email: String): Optional<Usuario>

    fun findByEmail(email: String): Optional<Usuario>

    @EntityGraph(attributePaths = ["compras", "compras.items"])
    fun findConComprasByEmail(email: String): Optional<Usuario>

    override fun findById(id: Int): Optional<Usuario>

    @EntityGraph(attributePaths = ["compras", "compras.items"])
    fun findConComprasById(id: Int): Optional<Usuario>
}