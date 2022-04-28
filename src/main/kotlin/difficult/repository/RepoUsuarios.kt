package difficult.repository

import LoginException
import difficult.domain.Usuario
import difficult.service.LoginDTO
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RepoUsuarios : CrudRepository<Usuario, Int> {


    fun loguear(loginDTO: LoginDTO): Usuario {
        return findByContraseniaAndEmail(loginDTO.password, loginDTO.email).orElseThrow{
            LoginException("Error al loguear")
        }
    }

    fun findByContraseniaAndEmail(password: String, email: String): Optional<Usuario>


}