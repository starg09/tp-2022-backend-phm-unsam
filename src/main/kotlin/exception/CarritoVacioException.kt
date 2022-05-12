import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El carrito esta vacio")
internal class CarritoVacioException(mensaje: String) : RuntimeException(mensaje)