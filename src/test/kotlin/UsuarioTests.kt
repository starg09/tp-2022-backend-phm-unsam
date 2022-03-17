import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.util.*

class Test: DescribeSpec( {

    describe("un usuario")
    {
        val unUsuario = Usuario("dami", "lescano", Date(2000,6,5), 1.0E7F).apply {



        }
        it("un test") {
            unUsuario.edad() shouldBe 21
        }
    }
})