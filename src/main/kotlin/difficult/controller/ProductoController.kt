package difficult.controller

import difficult.domain.Producto
import difficult.domain.Usuario
import difficult.service.ProductoService
import difficult.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.PUT, RequestMethod.GET])
class ProductoController {

    @Autowired
    private lateinit var productoService: ProductoService


    @GetMapping("/productos")
    fun getProductos(): MutableSet<Producto> {
        return productoService.getProductos()
    }

}