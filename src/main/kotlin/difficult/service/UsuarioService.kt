package difficult.service

import difficult.domain.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.server.ResponseStatusException
import difficult.repository.RepoUsuarios

@Service
class UsuarioService {

    @Autowired
    private lateinit var repoUsuarios: RepoUsuarios

    fun getUsuarios(): MutableSet<Usuario> {
        return repoUsuarios.elementos
    }

}