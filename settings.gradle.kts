plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "url-fuzzer"
include("instrumentation")
include("fuzzer")
include("search-based-fuzzer")
