package difficult.controller

import difficult.domain.Producto
import difficult.domain.Usuario
import difficult.service.LoginDTO
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

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): Int {
        return usuarioService.login(loginDTO)
    }

    @GetMapping("/carrito/{id}")
    fun carrito(@PathVariable id: Int): MutableMap<Producto, Int> {
        return usuarioService.carrito(id)
    }

    @PutMapping("/carrito/{usuarioId}/agregar/{productoId}")
    fun agregarCarrito(@PathVariable usuarioId: Int, @PathVariable productoId: Int, @RequestBody cantidad: Int){
        usuarioService.agregarCarrito(usuarioId, productoId, cantidad)
    }
}