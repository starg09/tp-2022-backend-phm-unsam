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
    fun filtrar(
        @RequestParam(value="nombre", required = false, defaultValue = "") nombre: String,
        @RequestParam(value="paises", required = false, defaultValue = "") paises: List<String>,
        @RequestParam(value="puntajeMinimo", required = false, defaultValue = "0") puntajeMinimo: Int
    ): List<ProductoDTO> {
        val filtrosDTO = FiltroDTO().apply {
            this.nombre = nombre
            this.paises = paises
            this.puntaje = puntajeMinimo
        }
        return productoService.filtrar(filtrosDTO)
    }

    @GetMapping("/productos/{id}/detalles")
    fun productoDetalles(@PathVariable id: String): ProductoDTO {
        return productoService.productoDetalles(id)
    }

}