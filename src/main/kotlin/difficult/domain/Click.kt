package difficult.domain

import org.springframework.data.annotation.Reference
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import org.springframework.web.bind.annotation.Mapping
import java.time.LocalDateTime
import javax.persistence.*

@Document(collection = "clicks")
class Click {
    @Id
    lateinit var id: String
    var momento: LocalDateTime = LocalDateTime.now()
    var nombreProducto: String = ""
    var usuario: Int = 0
}

