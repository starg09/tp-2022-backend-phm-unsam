package difficult

import difficult.domain.Usuario
import java.time.LocalDate
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import difficult.repository.RepoUsuarios


@Service
class DifficultBootstrap : InitializingBean {

    @Autowired
    private lateinit var repoUsuarios: RepoUsuarios

    private lateinit var dami: Usuario
    private lateinit var jill: Usuario
    private lateinit var chris: Usuario
    private lateinit var leon: Usuario
    private lateinit var claire: Usuario

    fun initUsuarios(){

        dami = Usuario("Dami", "Lescano", LocalDate.of(2000, 6, 5), 1000000.0)
        jill = Usuario("Jill", "Valentine", LocalDate.of(1974, 6, 5), 1000000.0)
        chris = Usuario("Chris", "Redfield", LocalDate.of(1973, 6, 5), 1000000.0)
        leon = Usuario("Leon Scott", "Kennedy", LocalDate.of(1977, 6, 5), 1000000.0)
        claire = Usuario("Claire", "Redfield", LocalDate.of(1979, 6, 5), 1000000.0)


        repoUsuarios.create(dami)
        repoUsuarios.create(jill)
        repoUsuarios.create(leon)
        repoUsuarios.create(chris)
        repoUsuarios.create(claire)

    }




    override fun afterPropertiesSet() {
        this.initUsuarios()
    }


}