import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "el producto seleccionado ya esta en el carrito")
internal class YaEstaEnElCarritoException() : RuntimeException()