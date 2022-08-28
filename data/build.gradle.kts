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
//    implementation(project(":testShared"))

    implementCore()
    kaptCore()
    implementFramework()
    implementData()
    kaptData()

}

java {
    sourceCompatibility = Versions.compilerJavaVersion
    targetCompatibility = Versions.compilerTargetCompatibility
}