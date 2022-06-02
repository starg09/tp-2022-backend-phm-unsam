package difficult.repository

import difficult.domain.Carrito
import org.springframework.data.repository.CrudRepository

interface RepoCarrito : CrudRepository<Carrito, Int>{

}