package difficult.repository

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import difficult.domain.Producto
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface RepoProductos : CrudRepository<Producto, Int> {

    /*fun filtrar(filtros : List<Filtro>): List<Producto> {
        return this.findAll().filter { producto ->  filtros.all { filtro -> filtro.cumpleCondicion(producto) } || filtros.isEmpty()}
    }*/

    fun findAllByNombreContainsAndPaisOrigenAndPuntajeGreaterThanEqual(nombre: String, paisOrigen: String, puntaje: Int): List<Producto>

    fun findAllByNombreContains(nombre: String): List<Producto>

    /*fun findAllByPaisOrigen(pais: String): List<Producto>

    fun findAllByPuntajeGreaterThanEqual(puntaje: Int): List<Producto>*/

    /*@Query("SELECT * FROM producto WHERE nombre LIKE '%?1%'", nativeQuery = true)
    fun findSegunNombre(nombre: String): Producto*/

}


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = FiltroPais::class, name = "pais"),
    JsonSubTypes.Type(value = FiltroPuntuacion::class, name = "puntuacion"),
    JsonSubTypes.Type(value = FiltroBusqueda::class, name = "busqueda")
)
abstract class Filtro {
    var valor: String = ""

    open fun cumpleCondicion(producto: Producto): Boolean {
        return true
    }
}

class FiltroPais : Filtro()  {
    override fun cumpleCondicion(producto: Producto): Boolean {
        return valor.contentEquals(producto.paisOrigen, ignoreCase = true)
    }
}

class FiltroPuntuacion : Filtro() {
    override fun cumpleCondicion(producto: Producto): Boolean {
        return producto.puntaje.toString() == valor
    }
}

class FiltroBusqueda : Filtro() {
    override fun cumpleCondicion(producto: Producto): Boolean {
        return producto.nombre.contains(valor, ignoreCase = true)
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
