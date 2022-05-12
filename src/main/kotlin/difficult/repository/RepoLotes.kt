package difficult.repository

import difficult.domain.Lote
import org.springframework.data.repository.CrudRepository

interface RepoLotes : CrudRepository<Lote, Int> {

    fun findByNumeroLote(id: Int) : Lote

}