
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "El saldo es insuficiente")
internal class SaldoInsuficienteException(mensaje: String) : RuntimeException(mensaje)