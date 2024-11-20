plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {

    defaultConfig {
        compileSdk = SetupConfig.compileSdkVersion
        minSdk = SetupConfig.minSdkVersion
    }
    hilt.enableExperimentalClasspathAggregation = true
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.compilerVersion
    }
}
dependencies {

    implementation(fileTree(mapOf(Pair("dir", "libs"), Pair("include", listOf("*.jar")))))

    implementation(project(":androidbase"))
    implementation(project(":beer:di"))
    implementation(project(":beer:domain"))
    implementation(project(":beer:data"))

    implementData()
    implementCore()
    implementFramework()
    implementCompose()
    testBase()
    androidTestCompose()
    debugImplementationCompose()
    kaptAndroidTestFramework()
    implementation ("com.google.dagger:hilt-android-testing:2.42@aar")
}

java {
    sourceCompatibility = Versions.compilerJavaVersion
    targetCompatibility = Versions.compilerTargetCompatibility
}