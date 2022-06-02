import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "no se puede agregar cero unidades al carrito")
internal class AgregarCeroUnidadesCarritoException(mensaje: String) : RuntimeException(mensaje)