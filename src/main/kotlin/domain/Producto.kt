package ar.edu.unsam.phm.backendtp2022phmgrupo2.domain
class Producto(var nombre: String, var descripcion: String, var puntaje: Int, var paisOrigen: String) {

}

class Combo() {
    var productos = mutableSetOf<Producto>()

    fun agregarProducto(unProducto: Producto){
        productos.add(unProducto)
    }

    fun eliminarProducto(unProducto: Producto){
        productos.add(unProducto)
    }
}