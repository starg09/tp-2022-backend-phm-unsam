package difficult.service

import difficult.domain.Compra
import difficult.domain.Producto
import difficult.domain.Usuario
import difficult.repository.RepoProductos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import difficult.repository.RepoUsuarios
import java.time.LocalDate

@Service
class UsuarioService {

    @Autowired
    private lateinit var repoUsuarios: RepoUsuarios

    @Autowired
    private lateinit var repoProductos: RepoProductos

    fun getUsuarios(): MutableSet<Usuario> {
        return repoUsuarios.elementos
    }

    fun getUsuario(id: Int): UsuarioDTO {
        return usuarioToDto(repoUsuarios.getById(id))
    }

    fun usuarioToDto(usuario: Usuario): UsuarioDTO {
        return UsuarioDTO(usuario.nombre, usuario.apellido, usuario.fechaNacimiento, usuario.saldo, usuario.id)
    }

    fun login(loginDTO: LoginDTO): Int {
        //try {
        var unUsuario: Usuario = repoUsuarios.loguear(loginDTO)
        /*}
        catch () {
            ResponseStatusException(HttpStatus.NOT_FOUND, "el nombre y/o la contraseña no son correctos")
        }*/
        return unUsuario.id
    }

    fun carrito(id: Int): MutableMap<Producto, Int> {
        val usuario =  repoUsuarios.getById(id)
        return usuario.carrito
    }

    fun agregarCarrito(usuarioId: Int, productoId: Int, cantidad: Int){
        val producto = repoProductos.getById(productoId)
        val usuario = repoUsuarios.getById(usuarioId)
        usuario.agregarAlCarrito(producto, cantidad)
    }

    fun eliminarCarrito(usuarioId: Int, productoId: Int){
        val producto = repoProductos.getById(productoId)
        val usuario = repoUsuarios.getById(usuarioId)
        usuario.eliminarDelCarrito(producto)
    }

    fun comprasUsuario(id: Int): MutableSet<Compra> {
        return repoUsuarios.getById(id).compras
    }

    fun vaciarCarrito(id: Int) {
        repoUsuarios.getById(id).vaciarCarrito()
    }

    fun comprar(id: Int) {
        repoUsuarios.getById(id).realizarCompra(numeroDeOrden())
    }

    fun numeroDeOrden(): Int {
        return repoUsuarios.elementos.map{it -> it.compras.size}.fold(0) { acum, it -> acum + it } + 1
    }

}

class LoginDTO {
    lateinit var username: String
    lateinit var password: String
}

class AgregarCarritoDTO(var idProducto: Int, var idUsuario: Int, var cantidad: Int) {}

class UsuarioDTO(var nombre: String, var apellido: String, val fechaNacimiento: LocalDate, var saldo: Double, var id:Int)