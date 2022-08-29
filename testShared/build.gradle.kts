plugins {
    id("kotlin")
    id("kotlinx-serialization")
}

dependencies {
    implementation(fileTree(mapOf(Pair("dir", "libs"), Pair("include", listOf("*.jar")))))
    implementCore()
}

java {
    sourceCompatibility = Versions.compilerJavaVersion
    targetCompatibility = Versions.compilerTargetCompatibility
}
