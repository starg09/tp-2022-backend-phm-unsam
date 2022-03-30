package difficult.repository

import difficult.domain.Producto
import difficult.domain.Usuario
import difficult.service.LoginDTO
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

    fun loguear(loginDTO: LoginDTO): Usuario {
        return elementos.first { it -> it.nombre + " " + it.apellido == loginDTO.userName && it.contrasenia == loginDTO.password }
    }

    fun getById(id:Int): Usuario {
        return elementos.first {it -> it.id == id}
    }



    fun getCantidadElementos() { elementos.size }
}

@Repository
class RepoProductos {
    val elementos = mutableSetOf<Producto>()
    val filtros = mutableListOf<Filtro>()
    var idAsignar: Int = 1

    fun create(elemento: Producto){
        //validar(elemento)
        elementos.add(elemento)
        elemento.id = idAsignar
        idAsignar++
    }

    fun getById(id:Int): Producto {
        return elementos.first {it -> it.id == id}
    }

    fun filtrar(): List<Producto> {
        return elementos.filter { producto ->  filtros.all { filtro -> filtro.cumpleCondicion(producto) } || filtros.isEmpty()}
    }

    fun getCantidadElementos() { elementos.size }
}

abstract class Filtro {
    open fun cumpleCondicion(producto: Producto): Boolean {
        return true
    }
}

class FiltroPais : Filtro() {
    lateinit var paisFiltro: String

    override fun cumpleCondicion(producto: Producto): Boolean {
        return paisFiltro.lowercase() == producto.paisOrigen.lowercase()
    }
}

class FiltroPuntuacion : Filtro() {
    var puntuacionFiltro : Int = 0

    override fun cumpleCondicion(producto: Producto): Boolean {
        return producto.puntaje >= puntuacionFiltro
    }
}

class FiltroBusqueda : Filtro() {
    lateinit var textoFiltro : String

    override fun cumpleCondicion(producto: Producto): Boolean {
        return producto.nombre.contains(textoFiltro)
    }
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
