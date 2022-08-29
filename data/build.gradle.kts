plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}

apply(plugin = "dagger.hilt.android.plugin")

android {

    defaultConfig {
        compileSdk = SetupConfig.compileSdkVersion
        minSdk = SetupConfig.minSdkVersion
    }
}
dependencies {

    implementation(fileTree(mapOf(Pair("dir", "libs"), Pair("include", listOf("*.jar")))))

    implementation(project(":domain"))

    implementCore()
    implementFramework()
    implementData()
    kaptCore()
    kaptData()
    testBase()
    testData()

}

java {
    sourceCompatibility = Versions.compilerJavaVersion
    targetCompatibility = Versions.compilerTargetCompatibility
}