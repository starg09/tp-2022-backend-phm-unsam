package difficult.repository

import difficult.domain.Carrito
import org.springframework.stereotype.Repository

@Repository
class RepoCarrito {
    val elementos = mutableSetOf<Carrito>()

    fun create(elemento: Carrito, idUsuario: Int){
        elemento.carritoId = idUsuario
        elementos.add(elemento)
    }

    fun getById(usuarioId: Int): Carrito {
        return elementos.first{it.carritoId == usuarioId}
    }
}