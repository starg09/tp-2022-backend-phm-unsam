package difficult.controller

import difficult.domain.Compra
import difficult.domain.Producto
import difficult.domain.Usuario
import difficult.service.*
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

    @GetMapping("/usuarios/{id}")
    fun getUsuario(@PathVariable id: Int): UsuarioDTO {
        return usuarioService.getUsuario(id)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): Int {
        return usuarioService.login(loginDTO)
    }

    @PutMapping("/usuarios/{id}/agregarSaldo")
    fun agregarSaldo(@RequestBody cantidad: Double, @PathVariable id: Int){
        usuarioService.agregarSaldo(cantidad, id)
    }

    @PutMapping("/usuarios/{id}/comprar")
    fun comprar(@PathVariable id: Int){
        usuarioService.comprar(id)
    }

    @GetMapping("/usuarios/{id}/compras")
    fun comprasUsuario(@PathVariable id: Int): MutableSet<Compra> {
        return usuarioService.comprasUsuario(id)
    }

    @GetMapping("/usuarios/carrito/{id}")
    fun carrito(@PathVariable id: Int): List<CarritoDTO> {
        return usuarioService.carrito(id)
    }

    @PutMapping("/usuarios/carrito/agregar")
    fun agregarCarrito(@RequestBody agregarCarritoDTO: AgregarCarritoDTO){
        usuarioService.agregarCarrito(agregarCarritoDTO.idUsuario, agregarCarritoDTO.idProducto, agregarCarritoDTO.cantidad, agregarCarritoDTO.loteNumero)
    }

    @DeleteMapping("/usuarios/carrito/eliminar")
    fun eliminarCarrito(@RequestBody agregarCarritoDTO: AgregarCarritoDTO){
        usuarioService.eliminarCarrito(agregarCarritoDTO.idUsuario, agregarCarritoDTO.idProducto)
    }

    @DeleteMapping("/usuarios/{id}/carrito/vaciar")
    fun vaciarCarrito(@PathVariable id: Int){
        usuarioService.vaciarCarrito(id)
    }
}