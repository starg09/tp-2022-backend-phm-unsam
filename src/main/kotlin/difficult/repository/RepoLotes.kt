package difficult.repository

import difficult.domain.Lote
import org.springframework.data.mongodb.repository.MongoRepository

interface RepoLotes : MongoRepository<Lote, Int> {


}