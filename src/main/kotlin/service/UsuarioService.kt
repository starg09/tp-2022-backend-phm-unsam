package ar.edu.unsam.phm.backendtp2022phmgrupo2.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.server.ResponseStatusException

@Service
class UsuarioService {

    /*@Autowired
    lateinit var candidateRepository: CandidateRepository
*/
    fun getUsuarios(): Int{
        return 200
    }

}