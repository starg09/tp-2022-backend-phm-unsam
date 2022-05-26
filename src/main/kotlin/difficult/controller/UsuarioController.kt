package difficult.controller

import difficult.domain.Compra
import difficult.domain.Usuario
import difficult.service.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
class UsuarioController {

    @Autowired
    private lateinit var usuarioService: UsuarioService


    @GetMapping("/usuarios")
    fun getUsuarios(): MutableIterable<Usuario> {
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

    @GetMapping("/usuarios/{id}/tamanioCarrito")
    fun tamanioCarrito(@PathVariable id: Int): Int {
        return usuarioService.tamanioCarrito(id)
    }

    @PutMapping("/usuarios/{id}/carrito/agregar")
    fun agregarCarrito(@RequestBody agregarCarritoDTO: AgregarCarritoDTO, @PathVariable id: Int){
        usuarioService.agregarProductoCarrito(id, agregarCarritoDTO.idProducto, agregarCarritoDTO.cantidad, agregarCarritoDTO.loteNumero)
    }

    @DeleteMapping("/usuarios/carrito/eliminar")
    fun eliminarCarrito(@RequestBody agregarCarritoDTO: AgregarCarritoDTO){
        usuarioService.eliminarCarrito(agregarCarritoDTO.idUsuario, agregarCarritoDTO.idProducto, agregarCarritoDTO.loteNumero)
    }

    @DeleteMapping("/usuarios/{id}/carrito/vaciar")
    fun vaciarCarrito(@PathVariable id: Int){
        usuarioService.vaciarCarrito(id)
    }
}