package difficult.controller

import difficult.domain.Usuario
import difficult.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.PUT, RequestMethod.GET])
class UsuarioController {

    @Autowired
    private lateinit var usuarioService: UsuarioService


    @GetMapping("/usuarios")
    fun getUsuarios(): MutableSet<Usuario> {
        return usuarioService.getUsuarios()
    }
}