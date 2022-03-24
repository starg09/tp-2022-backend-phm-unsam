package difficult.domain

interface Entidad {
    var id: Int

    fun coincideBusqueda(value: String): Boolean
}
