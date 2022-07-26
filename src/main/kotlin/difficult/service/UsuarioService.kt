package difficult.service

import LoginException
import UsuarioNoEncontradoException
import difficult.domain.*
import difficult.repository.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.Date

@Service
class UsuarioService {

    @Autowired
    private lateinit var repoUsuarios: RepoUsuarios

    @Autowired
    private lateinit var repoProductos: RepoProductos

    @Autowired
    private lateinit var repoCarrito: RepoCarrito

    @Autowired
    private lateinit var repoClicks: RepoClicks

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

    @Transactional(readOnly = true)
    fun agregarClick(click: Click) {
        //click.id = (repoClicks.findAll().size + 1).toString()
        repoClicks.save(click)
    }
    @Transactional(readOnly = true)
    fun getClicksUsuario(id: Int): List<Click> {
        return repoClicks.findAllByUsuario(id)
    }
    @Transactional
    fun agregarSaldo(cantidad: Double, id: Int){
        getById(id).aumentarSaldo(cantidad)
    }

    @Transactional(readOnly = true)
    fun carrito(usuarioId: Int): List<CarritoDTO> {
        val usuario =  getById(usuarioId)
        usuario.carrito = getCarrito(usuarioId)
        return usuario.carrito.productosEnCarrito.map { toCarritoDTO(it) }
    }

    fun toCarritoDTO(entry: ProductoCarrito): CarritoDTO {
        val producto = entry.producto
        return CarritoDTO().apply {
            nombre = producto.nombre
            descripcion = producto.descripcion
            lote = entry.lote.id
            cantidad = entry.cantidad
            precio = producto.precioTotal() * cantidad
            id = producto.id
        }
    }

    @Transactional
    fun agregarProductoCarrito(usuarioId: Int, productoId: Int, cantidad: Int, loteNumero: Int){
        val producto = getByProductoId(productoId)
        val usuario = getById(usuarioId)
        val lote = producto.lotes.first{ it.id == loteNumero }
        usuario.carrito = getCarrito(usuarioId)
        usuario.agregarAlCarrito(producto, cantidad, lote)
        saveCarrito(usuarioId, usuario.carrito)
    }

    @Transactional
    fun eliminarCarrito(usuarioId: Int, productoId: Int, loteNumero: Int){
        val usuario = getById(usuarioId)
        usuario.carrito = getCarrito(usuarioId)
        usuario.eliminarDelCarrito(productoId, loteNumero)
        saveCarrito(usuarioId, usuario.carrito)
    }

    @Transactional
    fun comprasUsuario(id: Int): List<Compra> {
        return repoUsuarios.findConComprasById(id).orElseThrow {
            UsuarioNoEncontradoException("No se ha encontrado el usuario con id $id")
        }.compras.sortedBy { it.ordenCompra }.take(5)
    }

    @Transactional
    fun vaciarCarrito(usuarioId: Int) {
        val usuario = getById(usuarioId)
        usuario.carrito = getCarrito(usuarioId)
        usuario.vaciarCarrito()
        saveCarrito(usuarioId, usuario.carrito)
    }

    @Transactional
    fun comprar(usuarioId: Int) {
        val usuario = getById(usuarioId)
        usuario.carrito = getCarrito(usuarioId)
        val productos = usuario.carrito.productosEnCarrito.map{it.producto}
        usuario.realizarCompra()
        repoProductos.saveAll(productos)
        repoUsuarios.save(usuario)
        saveCarrito(usuarioId, usuario.carrito)
    }

    fun tamanioCarrito(usuarioId: Int): Int {
        return repoCarrito.tamanioCarrito(usuarioId)
    }

    fun getById(id: Int): Usuario {
        return repoUsuarios.findById(id).orElseThrow {
            UsuarioNoEncontradoException("No se ha encontrado el usuario con id $id")
        }
    }

    fun getByProductoId(productoId: Int): Producto {
        return repoProductos.findById(productoId).get()
    }

    fun getCarrito(usuarioId: Int): Carrito {
        return repoCarrito.getById(usuarioId)
    }
    fun saveCarrito(usuarioId: Int, carrito: Carrito): Carrito {
        return repoCarrito.guardar(usuarioId, carrito)
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

class ClickDTO(var momento: Date, var nombreProducto: String, var usuario: Int )