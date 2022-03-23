package domain

import ar.edu.unsam.phm.backendtp2022phmgrupo2.domain.Usuario
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class Test: DescribeSpec( {

    describe("un usuario")
    {
        val unUsuario = Usuario("dami", "lescano", LocalDate.of(2000, 6, 5), 1.0E7F).apply{}

        it("un test") {
            unUsuario.edad() shouldBe 21
        }
    }
})