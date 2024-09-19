plugins {
    kotlin("jvm")
}

group = "org.plan.research"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(project(":instrumentation"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

val fuzzerJar = task<Jar>("fuzzerJar") {
    manifest {
        attributes["Main-Class"] = "org.plan.research.MainKt"
    }
    archiveFileName.set("fuzzer.jar")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get() as CopySpec)
}

tasks {
    build {
        dependsOn(fuzzerJar)
    }
}
