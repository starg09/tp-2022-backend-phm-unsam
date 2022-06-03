package difficult

import difficult.repository.RepoNeo4jUsuarios
import difficult.repository.RepoUsuarios
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = [RepoUsuarios::class])
@EnableNeo4jRepositories(basePackageClasses = [RepoNeo4jUsuarios::class])
class BackendTp2022PhmGrupo2Application

fun main(args: Array<String>) {
	runApplication<BackendTp2022PhmGrupo2Application>(*args)
}
