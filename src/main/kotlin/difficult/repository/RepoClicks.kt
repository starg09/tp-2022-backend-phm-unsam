package difficult.repository

import difficult.domain.Click
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository

interface RepoClicks : MongoRepository<Click, Int> {

}

