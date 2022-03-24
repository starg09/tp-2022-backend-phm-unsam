package difficult.repository

import difficult.domain.Usuario
import org.springframework.stereotype.Repository

@Repository
class RepoUsuarios {
    val elementos = mutableSetOf<Usuario>()
    var idAsignar: Int = 1

    fun create(elemento: Usuario){
        //validar(elemento)
        elementos.add(elemento)
        elemento.id = idAsignar
        idAsignar++
    }

    fun getCantidadElementos() { elementos.size }
}

/*class Repositorio<T : Entidad> {
    val elementos = mutableSetOf<T>()
    var idAsignar: Int = 1

    fun getCantidadElementos() { elementos.size }

    fun create(elemento: T){
        //validar(elemento)
        elementos.add(elemento)
        //elemento.setId(idAsignar)
        idAsignar++
    }

    fun delete(elemento: T){
        if(elementos.contains(elemento)){
            elementos.remove(elemento)
        }
        else{
            //throw //ElementoNoContenidoEnRepoException("El repositorio no contiene el elemento")
        }
    }

    /*fun update(elemento: T){
        validar(elemento)
        val actual = getById(elemento.getId)
        if (actual != null){
            delete(actual)
            elementos.add(elemento)
        }
        else {
            throw new ElementoInexistenteException("No existe un elemento con el mismo id en el repositorio")
        }
    }*/

    fun getById(id: Int){
        //return elementos.findFirst[elemento | elemento.getId == id]
    }

    fun search(value: String){
        //elementos.filter[it.coincideBusqueda(value)]
    }

    /*def void validar(elemento: T){
        if (!elemento.esValido){
            throw new ElementoNoValidoException("El elemento no es valido")
        }
    }*/

}*/
