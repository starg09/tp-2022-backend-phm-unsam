package difficult.service

import LoginException
import UsuarioNoEncontradoException
import difficult.domain.*
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
    private lateinit var repoNeo4jUsuarios: RepoNeo4jUsuarios

    @Autowired
    private lateinit var repoProductos: RepoProductos

    @Autowired
    private lateinit var repoCarrito: RepoCarrito

    @Autowired
    private lateinit var repoClicks: RepoClicks

    @Transactional(readOnly = true)
    fun getUsuarios(): MutableIterable<Usuario> {
        return repoNeo4jUsuarios.findAll()
    }

    @Transactional(readOnly = true)
    //FIXME: Convertir a usuarioToDTO(usuarioId: Int)
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
        return getCarrito(usuarioId).productosEnCarrito.map { toCarritoDTO(it) }
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
        val carrito = getCarrito(usuarioId)
        carrito.agregarProducto(producto, cantidad, lote)
        saveCarrito(carrito)
    }

    @Transactional
    fun eliminarCarrito(usuarioId: Int, productoId: Int, loteNumero: Int){
        val carrito = getCarrito(usuarioId)
        carrito.eliminarProducto(productoId, loteNumero)
        saveCarrito(carrito)
    }

    @Transactional
    fun comprasUsuario(id: Int): List<Compra> {
        return repoUsuarios.findConComprasById(id).orElseThrow {
            UsuarioNoEncontradoException("No se ha encontrado el usuario con id $id")
        }.compras.sortedBy { it.ordenCompra }.take(5)
    }

    @Transactional
    fun vaciarCarrito(usuarioId: Int) {
        val carrito = getCarrito(usuarioId)
        carrito.vaciar()
        saveCarrito(carrito)
    }

    @Transactional
    fun comprar(usuarioId: Int) {
        val usuario = getById(usuarioId)
        val carrito = getCarrito(usuarioId)

        carrito.validarCarritoNoEstaVacio()
        carrito.validarAlcanzaSaldo(usuario.saldo)
        carrito.validarProductosDisponibles()

        val compra = Compra().apply {
            fechaCompra = LocalDate.now()
            productos.addAll(carrito.productosEnCarrito.map { productoCarrito ->
                ProductoCompra().apply{
                    nombreProducto = productoCarrito.producto.nombre
                    idProducto = productoCarrito.producto.id
                    numeroLote = productoCarrito.lote.id
                    precioUnitario = productoCarrito.producto.precioTotal()
                    cantidad = productoCarrito.cantidad
                }
            })
        }
        usuario.compras.add(compra)
        usuario.disminuirSaldo(compra.getImporteTotal())
        carrito.disminurLotes()
        val productosActualizados = carrito.productosEnCarrito.map { it.producto }
        carrito.vaciar()
        repoProductos.saveAll(productosActualizados)
        repoNeo4jUsuarios.save(usuario)
        repoUsuarios.save(usuario)
        saveCarrito(carrito)
    }

    fun tamanioCarrito(usuarioId: Int): Int {
        return repoCarrito.findById(usuarioId).get().tamanioCarrito()
    }

    // FIXME: Convertir a getUsuario(idUsuario: Int)
    fun getById(id: Int): Usuario {
        return repoNeo4jUsuarios.findById(id).orElseThrow {
            UsuarioNoEncontradoException("No se ha encontrado el usuario con id $id")
        }
    }

    fun getByProductoId(productoId: Int): Producto {
        return repoProductos.findById(productoId).get()
    }

    fun getCarrito(usuarioId: Int): Carrito {
        return repoCarrito.findById(usuarioId).get()
    }
    fun saveCarrito(carrito: Carrito): Carrito {
        return repoCarrito.save(carrito)
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