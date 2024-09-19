plugins {
    kotlin("jvm")
}

group = "org.plan.research"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.ow2.asm:asm:9.7")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

task<Jar>("coverage-agent") {
    archiveBaseName.set("coverage")
    archiveClassifier.set("agent")
    from(sourceSets.main.get().output)
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    manifest {
        attributes(
            "Premain-Class" to "org.plan.research.coverage.CoverageAgent",
            "Can-Redefine-Classes" to true,
            "Can-Retransform-Classes" to true
        )
    }
    exclude("**/META-INF/**")
}
