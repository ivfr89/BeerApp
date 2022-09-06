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
        testInstrumentationRunner = SetupConfig.testRunner
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
    implementation(project(":androidbase"))
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation("androidx.navigation:navigation-testing:2.5.1")
    androidTestImplementation(project(":data"))
    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test:core:1.5.0-alpha02")
//    implementation(project(":testShared"))

    implementData()
    implementCore()
    implementFramework()
    implementCompose()
    testBase()
    androidTestCompose()
    debugImplementationCompose()
    androidTestFramework()
    kaptAndroidTestFramework()
    implementation ("com.google.dagger:hilt-android-testing:2.42@aar")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}