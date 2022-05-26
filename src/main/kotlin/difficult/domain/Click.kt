package difficult.domain

import org.springframework.data.annotation.Reference
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import org.springframework.web.bind.annotation.Mapping
import javax.persistence.*

@Document(collection = "clicks")
class Click {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    var id: Long
    var momento: Date = LocalDateTime.now()
    var nombreProducto: String= ""
    var usuario: Int = 0
    }
}
