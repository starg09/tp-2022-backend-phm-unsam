import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

        plugins {
            id("org.springframework.boot") version "2.6.4"
            id("io.spring.dependency-management") version "1.0.11.RELEASE"
            id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
            kotlin("jvm") version "1.6.10"
            kotlin("plugin.spring") version "1.6.10"
            jacoco
        }

        allOpen {
            annotation("javax.persistence.Entity")
        }

group = "ar.edu.unsam.phm"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_14

repositories {
    mavenCentral()
}

val kotestVersion = "5.0.0"
val springVersion = "2.6.4"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation(kotlin("stdlib"))
    testImplementation("io.mockk:mockk:1.12.2")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-rest:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-hateoas:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-web-services:$springVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springdoc:springdoc-openapi-ui:1.6.6")
    implementation("org.springframework.boot:spring-boot-devtools:$springVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.1")
    testImplementation("com.h2database:h2:2.1.210")
    testImplementation("org.springframework.boot:spring-boot-starter-test:$springVersion")
    runtimeOnly("com.microsoft.sqlserver:mssql-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:$springVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-neo4j:$springVersion")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "14"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.isEnabled = false
    }
}

tasks.register("runOnGitHub") {
    dependsOn("jacocoTestReport")
    group = "custom"
    description = "$ ./gradlew runOnGitHub # runs on GitHub Action"
}
