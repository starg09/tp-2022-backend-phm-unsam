package domain

import difficult.domain.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ProductoTests: DescribeSpec( {

    describe("dado un piso normal...") {
        val pisoNormal = Piso().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Argentina"
            precioBase = 2500.0
            esAltoTransito = false
            medidaX = 50
            medidaZ = 30
        }
        it("su precio es el mismo qu el precio base"){
            pisoNormal.precioTotal() shouldBe 2500.0
        }

        it("sus medidas son 50 X 30"){
            pisoNormal.medidas() shouldBe "50 X 30"
        }
    }

    describe("dado un piso alto transito...") {
        val pisoAltoTransito = Piso().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Argentina"
            precioBase = 1000.0
            esAltoTransito = true
            medidaX = 60
            medidaZ = 60
        }

        it("su precio es el mismo qu el precio base"){
            pisoAltoTransito.precioTotal() shouldBe 1200.0
        }

        it("sus medidas son 50 X 30"){
            pisoAltoTransito.medidas() shouldBe "60 X 60"
        }
    }

    describe("dadas dos pinturas..."){
        val pinturaMenorRendimiento = Pintura().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Argentina"
            precioBase = 1000.0
            rendimiento = 4
            color = "Blanco"
            litros = 10
        }
        val pinturaMayorRendimiento = Pintura().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Argentina"
            precioBase = 1000.0
            rendimiento = 9
            color = "Blanco"
            litros = 10
        }

        it("la de menor rendimiento su precio es el mismo qu el precio base"){
            pinturaMenorRendimiento.precioTotal() shouldBe 1000.0
        }

        it("la de mayor rendimiento su precio es mayor"){
            pinturaMayorRendimiento.precioTotal() shouldBe 1250.0
        }
    }

    describe("dado un combo..."){
        val pinturaMenorRendimiento = Pintura().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Argentina"
            precioBase = 1000.0
            rendimiento = 4
            color = "Blanco"
            litros = 10
        }
        val pisoNormal = Piso().apply {
            nombre = "Acme rustico"
            descripcion = "una descripcion"
            puntaje = 5
            paisOrigen = "Argentina"
            precioBase = 2500.0
            esAltoTransito = false
            medidaX = 50
            medidaZ = 30
        }
        val combo = Combo().apply {
            agregarProducto(pisoNormal)
            agregarProducto(pinturaMenorRendimiento)
        }
        it("el precio es "){
            combo.precioTotal() shouldBe 3009.0
        }
    }
})