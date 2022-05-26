package difficult.repository

import difficult.domain.Carrito
import org.springframework.stereotype.Repository

@Repository
class RepoCarrito {
    val elementos = hashMapOf<Int, Carrito>()

    fun create(idUsuario: Int): Carrito {
        val nuevoCarrito = Carrito()
        elementos.set(idUsuario, nuevoCarrito)
        return nuevoCarrito
    }

    fun getById(usuarioId: Int): Carrito {
        return elementos.getOrDefault(usuarioId, Carrito())
    }

    fun tamanioCarrito(usuarioId: Int): Int {
        return getById(usuarioId).productosEnCarrito.size
    }

    fun guardar(usuarioId: Int, carrito: Carrito): Carrito {
        elementos.set(usuarioId, carrito)
        return carrito
    }
}