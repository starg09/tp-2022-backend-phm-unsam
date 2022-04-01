
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Error al loguear")
internal class LoginException(mensaje: String) : RuntimeException(mensaje)