plugins {
    kotlin("jvm") version "2.2.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "9.2.1"
    }
}

kotlin {
    jvmToolchain(24)
}

dependencies {
    implementation("tools.aqua:z3-turnkey:4.14.1")
}