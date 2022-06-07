package domain

import difficult.domain.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class UsuarioTests: DescribeSpec( {

    describe("un usuario") {
        val unUsuario = Usuario().apply{
            nombre = "dami"
            apellido = "lescano"
            fechaNacimiento = LocalDate.of(2000, 6, 5)
            saldo = 1000000.0
            email = "a@c"
            contrasenia = "1234567890"
        }
        //FIXME (si se pudiera testear carrito en implementacion actual)
//        val unLotePintura = Lote().apply {
//            cantidadDisponible = 2
//        }
//        val unLotePiso = Lote().apply {
//            cantidadDisponible = 5
//        }
//        val producto1 = Pintura().apply{
//            precioBase = 5000.0
//            rendimiento = 3
//            lotes.add(unLotePintura)
//        }
//
//        val producto2 = Piso().apply{
//            precioBase = 8000.0
//            esAltoTransito = false
//            lotes.add(unLotePiso)
//        }
//
//        unUsuario.agregarAlCarrito(producto1, 2, 5)
//
//        unUsuario.agregarAlCarrito(producto2, 4, 5)


        it("un test") {
            unUsuario.edad() shouldBe 21
        }

        it("el saldo aumenta") {
            unUsuario.aumentarSaldo(100.0)
            unUsuario.saldo shouldBe 1000100.0
        }

        it("el saldo disminuye") {
            unUsuario.disminuirSaldo(100.0)
            unUsuario.saldo shouldBe 1000000.0
        }

        //FIXME: Usar un mock de carrito.
//        it("al agregar productos al carrito, estan en el carrito"){
//            unUsuario.carrito.productosEnCarrito.size shouldBe 2
//        }
//
//        it("la cantidad de productos del carrito"){
//            unUsuario.cantidadProductosCarrito() shouldBe 6
//        }
//
//        it("el precio total del carrito"){
//            unUsuario.importeTotalCarrito() shouldBe 42000.0
//        }
//
//
//        it("al comprar un producto, disminuye su saldo"){
//            unUsuario.realizarCompra(1)
//            unUsuario.saldo shouldBe 958000.0
//        }
//
//        it("la cantidad disponible del lote disminuye"){
//            unLotePiso.cantidadDisponible shouldBe 1
//        }


    }
})