package difficult.repository

import difficult.domain.Carrito
import org.springframework.stereotype.Repository

@Repository
class RepoCarrito {
    val elementos = mutableSetOf<Carrito>()
    var idAsignar: Int = 1

    fun getCantidadElementos() { elementos.size }

    fun create(elemento: Carrito){
        //validar(elemento)
        elemento.carritoId = idAsignar
        elementos.add(elemento)
        //elemento.setId(idAsignar)
        idAsignar++
    }

    fun getById(usuarioId: Int): Carrito {
        return elementos.first{it.carritoId == usuarioId}
    }
}