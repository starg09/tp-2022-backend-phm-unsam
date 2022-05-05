package difficult.service

import LoginException
import difficult.domain.Compra
import difficult.domain.Lote
import difficult.domain.Producto
import difficult.domain.Usuario
import difficult.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class UsuarioService {

    @Autowired
    private lateinit var repoUsuarios: RepoUsuarios

    @Autowired
    private lateinit var repoProductos: RepoProductos

    @Transactional(readOnly = true)
    fun getUsuarios(): MutableIterable<Usuario> {
        return repoUsuarios.findAll()
    }

    @Transactional(readOnly = true)
    fun getUsuario(id: Int): UsuarioDTO {
        return usuarioToDto(getById(id))
    }

    fun usuarioToDto(usuario: Usuario): UsuarioDTO {
        return UsuarioDTO(usuario.nombre, usuario.apellido, usuario.fechaNacimiento, usuario.saldo, usuario.id)
    }

    @Transactional(readOnly = true)
    fun login(loginDTO: LoginDTO): Int {
        val unUsuario: Usuario = repoUsuarios.findByContraseniaAndEmail(loginDTO.password, loginDTO.email).orElseThrow{
            LoginException("Error al loguear")
        }
        return unUsuario.id
    }

    @Transactional
    fun agregarSaldo(cantidad: Double, id: Int){
        getById(id).aumentarSaldo(cantidad)
    }

    //@Transactional(readOnly = true)
    fun carrito(id: Int): List<CarritoDTO> {
        val usuario =  getById(id)
        return usuario.carrito.productosEnCarrito.map { toCarritoDTO(it) }
    }

    fun toCarritoDTO(entry: Triple<Producto, Int, Lote>): CarritoDTO {
        val producto = entry.first
        return CarritoDTO().apply {
            nombre = producto.nombre
            descripcion = producto.descripcion
            lote = entry.third.numeroLote
            cantidad = entry.second
            precio = producto.precioTotal()
            id = producto.id
        }
    }

    //@Transactional
    fun agregarCarrito(usuarioId: Int, productoId: Int, cantidad: Int, loteNumero: Int){
        val producto = getByProductoId(productoId)
        val usuario = getById(usuarioId)
        usuario.agregarAlCarrito(producto, cantidad, loteNumero)
    }

    //@Transactional
    fun eliminarCarrito(usuarioId: Int, productoId: Int){
        val producto = getByProductoId(productoId)
        val usuario = getById(usuarioId)
        usuario.eliminarDelCarrito(producto)
    }

    @Transactional
    fun comprasUsuario(id: Int): MutableSet<Compra> {
        return getById(id).compras
    }

    //@Transactional
    fun vaciarCarrito(id: Int) {
        getById(id).vaciarCarrito()
    }

    @Transactional
    fun comprar(id: Int) {
        getById(id).realizarCompra()
    }

    fun numeroDeOrden(): Int {
        return repoUsuarios.findAll().map{ it.compras.size}.fold(0) { acum, it -> acum + it } + 1
    }

    fun getById(id: Int): Usuario {
        return repoUsuarios.findById(id).get()
    }

    fun getByProductoId(productoId: Int): Producto {
        return repoProductos.findById(productoId).get()
    }

}

class LoginDTO {
    lateinit var email: String
    lateinit var password: String
}

class AgregarCarritoDTO(var idProducto: Int, var idUsuario: Int, var cantidad: Int, var loteNumero: Int)

class CarritoDTO {
    lateinit var nombre: String
    lateinit var descripcion: String
    var lote: Int = 0
    var cantidad: Int = 0
    var precio: Double = 0.0
    var id: Int = 0
}

class UsuarioDTO(var nombre: String, var apellido: String, val fechaNacimiento: LocalDate, var saldo: Double, var id:Int)