package difficult.service

import difficult.domain.Producto
import difficult.domain.Usuario
import difficult.repository.RepoProductos
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.server.ResponseStatusException
import difficult.repository.RepoUsuarios
import org.springframework.web.bind.annotation.ResponseStatus

@Service
class UsuarioService {

    @Autowired
    private lateinit var repoUsuarios: RepoUsuarios

    @Autowired
    private lateinit var repoProductos: RepoProductos

    fun getUsuarios(): MutableSet<Usuario> {
        return repoUsuarios.elementos
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
        return repoUsuarios.getCarritoUsuario(id)
    }

    fun agregarCarrito(usuarioId: Int, productoId: Int, cantidad: Int){
        val producto = repoProductos.getById(productoId)
        repoUsuarios.agregarCarritoUsuario(usuarioId, producto, cantidad)
    }

}

class LoginDTO {
    lateinit var userName: String
    lateinit var password: String
}

//TODO AgregarCarritoDTO