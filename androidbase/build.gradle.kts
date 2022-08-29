plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlinx-serialization")
}
apply(plugin = "dagger.hilt.android.plugin")

@Suppress("UnstableApiUsage")
android {

    defaultConfig {
        compileSdk = SetupConfig.compileSdkVersion
        minSdk = SetupConfig.minSdkVersion
    }

    kotlinOptions {
        jvmTarget = Versions.jvmTargetVersion
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.Compose.compilerVersion
    }
}

dependencies {
    implementCore()
    implementFramework()
    implementCompose()
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}