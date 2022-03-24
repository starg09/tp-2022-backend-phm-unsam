import ar.edu.unsam.phm.backendtp2022phmgrupo2.domain.Usuario
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class UsuarioTests: DescribeSpec( {

    describe("un usuario") {
        val unUsuario = Usuario("dami", "lescano", LocalDate.of(2000, 6, 5), 1000000.0)

        it("un test") {
            unUsuario.edad() shouldBe 21
        }
    }
})