package difficult.controller

import difficult.domain.Lote
import difficult.domain.ProductoDTO
import difficult.service.FiltroDTO
import difficult.service.ProductoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], methods = [RequestMethod.PUT, RequestMethod.GET])
class ProductoController {

    @Autowired
    private lateinit var productoService: ProductoService

    @GetMapping("/productos")
    fun getProductos(): List<ProductoDTO> {
        return productoService.getProductos()
    }

    @GetMapping("/productos/lotes")
    fun getLotes(): List<Lote> {
        return productoService.getLotes()
    }

    @GetMapping("/productos/filtrar")
    fun filtrar(@RequestBody filtrosDTO: List<FiltroDTO>): List<ProductoDTO> {
        return productoService.filtrar(filtrosDTO)
    }

    @GetMapping("/productos/{id}/detalles")
    fun productoDetalles(@PathVariable id: Int): ProductoDTO {
        return productoService.productoDetalles(id)
    }

}