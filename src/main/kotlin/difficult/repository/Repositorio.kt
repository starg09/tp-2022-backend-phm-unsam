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

    fun getCarritoUsuario(id:Int): MutableMap<Producto, Int> {
        val usuario = getById(id)
        return usuario.carrito
    }

    fun agregarCarritoUsuario(id: Int, producto: Producto, cantidad: Int){
        val usuario = getById(id)
        usuario.carrito[producto] = cantidad
    }

    fun getCantidadElementos() { elementos.size }
}

@Repository
class RepoProductos {
    val elementos = mutableSetOf<Producto>()
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
