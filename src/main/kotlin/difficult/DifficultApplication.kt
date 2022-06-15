package difficult

import difficult.repository.RepoUsuariosNeo4j
import difficult.repository.RepoUsuarios
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories

@SpringBootApplication
//@EnableNeo4jRepositories(basePackageClasses = [RepoUsuariosNeo4j::class])
//@EnableJpaRepositories(basePackageClasses = [RepoUsuarios::class])
class BackendTp2022PhmGrupo2Application

fun main(args: Array<String>) {
	runApplication<BackendTp2022PhmGrupo2Application>(*args)
}
