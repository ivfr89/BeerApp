plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("kotlinx-serialization")
    id("kotlin-parcelize")
}
apply(plugin = "dagger.hilt.android.plugin")

@Suppress("UnstableApiUsage")
android {
    defaultConfig {
        compileSdk = SetupConfig.compileSdkVersion
        applicationId = SetupConfig.applicationId
        minSdk = SetupConfig.minSdkVersion
        targetSdk = SetupConfig.targetSdkVersion
        versionCode = SetupConfig.versionCode
        versionName = VersionName.getCustomVersionName()
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false

        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility(Versions.compilerJavaVersion)
        targetCompatibility(Versions.compilerTargetCompatibility)
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
    namespace = "com.developer.ivan.beerapp"
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))
//    implementation(project(":testShared"))

    implementCore()
    implementFramework()
    implementCompose()
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}