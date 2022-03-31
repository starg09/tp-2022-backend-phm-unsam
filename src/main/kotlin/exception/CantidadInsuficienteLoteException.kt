import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "la cantidad disponible de este lote no essuficiente")
internal class CantidadInsuficienteLoteException() : RuntimeException()