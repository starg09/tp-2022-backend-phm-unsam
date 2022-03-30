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

@Service
class ProductoService {

    @Autowired
    private lateinit var repoProductos: RepoProductos

    fun getProductos(): MutableSet<Producto> {
        return repoProductos.elementos
    }

    fun getProductosFiltrados(): List<Producto> {
        return repoProductos.filtrar()
    }

}