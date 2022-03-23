package ar.edu.unsam.phm.backendtp2022phmgrupo2.controller

import ar.edu.unsam.phm.backendtp2022phmgrupo2.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.PUT, RequestMethod.GET])
class CandidateController {

    @Autowired
    private lateinit var usuarioService: UsuarioService


    @GetMapping("/usuarios")
    fun getUsuarios(): Int{
        return usuarioService.getUsuarios()
    }
}