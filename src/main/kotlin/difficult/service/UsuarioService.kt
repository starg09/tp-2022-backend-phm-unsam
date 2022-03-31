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
        val unUsuario: Usuario = repoUsuarios.loguear(loginDTO)
        /*}
        catch () {
            ResponseStatusException(HttpStatus.NOT_FOUND, "el nombre y/o la contraseña no son correctos")
        }*/
        return unUsuario.id
    }

    fun agregarSaldo(cantidad: Double, id: Int){
        repoUsuarios.getById(id).aumentarSaldo(cantidad)
    }

    fun carrito(id: Int): List<CarritoDTO> {
        val usuario =  repoUsuarios.getById(id)
        return usuario.carrito.map { toCarritoDTO(it) }
    }

    fun toCarritoDTO(entry: Map.Entry<Producto, List<Int>>): CarritoDTO {
        val producto = entry.key
        return CarritoDTO().apply {
            nombre = producto.nombre
            descripcion = producto.descripcion
            lote = entry.value[1]
            cantidad = entry.value[0]
            precio = producto.precioTotal()
            id = producto.id
        }
    }

    fun agregarCarrito(usuarioId: Int, productoId: Int, cantidad: Int, loteNumero: Int){
        val producto = repoProductos.getById(productoId)
        val usuario = repoUsuarios.getById(usuarioId)
        usuario.agregarAlCarrito(producto, cantidad, loteNumero)
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
    lateinit var email: String
    lateinit var password: String
}

class AgregarCarritoDTO(var idProducto: Int, var idUsuario: Int, var cantidad: Int, var loteNumero: Int) {}

class CarritoDTO {
    lateinit var nombre: String
    lateinit var descripcion: String
    var lote: Int = 0
    var cantidad: Int = 0
    var precio: Double = 0.0
    var id: Int = 0
}

class UsuarioDTO(var nombre: String, var apellido: String, val fechaNacimiento: LocalDate, var saldo: Double, var id:Int)