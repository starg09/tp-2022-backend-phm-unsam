package difficult.repository

import LoginException
import difficult.domain.Usuario
import difficult.service.LoginDTO
import org.springframework.data.repository.CrudRepository
import java.util.*

interface RepoUsuarios : CrudRepository<Usuario, Int> {

    fun findByContraseniaAndEmail(password: String, email: String): Optional<Usuario>


}