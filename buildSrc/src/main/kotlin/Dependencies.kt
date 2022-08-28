import Dependencies.Compose.activity
import Dependencies.Compose.appCompat
import Dependencies.Compose.coil
import Dependencies.Compose.foundation
import Dependencies.Compose.fragment
import Dependencies.Compose.hiltNavigationCompose
import Dependencies.Compose.layout
import Dependencies.Compose.material
import Dependencies.Compose.materialApp
import Dependencies.Compose.materialIconsExtended
import Dependencies.Compose.navigation
import Dependencies.Compose.navigationApp
import Dependencies.Compose.runtime
import Dependencies.Compose.runtimeLivedata
import Dependencies.Compose.shimmer
import Dependencies.Compose.tooling
import Dependencies.Compose.toolingPreview
import Dependencies.Compose.uiText
import Dependencies.Compose.uiUtil
import Dependencies.Compose.viewBinding
import Dependencies.Compose.viewModel
import Dependencies.Core.coroutines
import Dependencies.Core.coroutinesCore
import Dependencies.Core.moshi
import Dependencies.Core.moshiCompiler
import Dependencies.Core.serializationCore
import Dependencies.Core.serializationJson
import Dependencies.Data.moshiConverter
import Dependencies.Data.retrofit
import Dependencies.Data.retrofitSerializer
import Dependencies.Data.room
import Dependencies.Data.roomKapt
import Dependencies.Data.roomKtx
import Dependencies.Data.scalarsConverter
import Dependencies.Framework.hilt
import Dependencies.Framework.hiltKapt
import Dependencies.Framework.lifecycle
import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    object Compose {
        internal const val foundation =
            "androidx.compose.foundation:foundation:${Versions.Compose.version}"
        internal const val layout =
            "androidx.compose.foundation:foundation-layout:${Versions.Compose.version}"
        internal const val material =
            "androidx.compose.material:material:${Versions.Compose.version}"
        internal const val materialIconsExtended =
            "androidx.compose.material:material-icons-extended:${Versions.Compose.version}"
        internal const val runtime = "androidx.compose.runtime:runtime:${Versions.Compose.version}"
        internal const val runtimeLivedata =
            "androidx.compose.runtime:runtime-livedata:${Versions.Compose.version}"
        internal const val tooling = "androidx.compose.ui:ui-tooling:${Versions.Compose.version}"
        internal const val toolingPreview =
            "androidx.compose.ui:ui-tooling-preview:${Versions.Compose.version}"
        internal const val uiText =
            "androidx.compose.ui:ui-text-google-fonts:${Versions.Compose.version}"
        internal const val uiUtil = "androidx.compose.ui:ui-util:${Versions.Compose.version}"
        internal const val viewBinding =
            "androidx.compose.ui:ui-viewbinding:${Versions.Compose.version}"
        internal const val viewModel =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.Compose.viewmodel}"
        internal const val coil = "com.github.skydoves:landscapist-coil:${Versions.Compose.coil}"
        internal const val shimmer =
            "com.valentinilk.shimmer:compose-shimmer:${Versions.Compose.shimmer}"
        internal const val activity =
            "androidx.activity:activity-compose:${Versions.Compose.activity}"

        internal const val fragment =
            "androidx.fragment:fragment-ktx:${Versions.fragment}"
        internal const val materialApp =
            "com.google.android.material:material:${Versions.material}"

        internal const val navigationApp =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"

        internal const val appCompat =
            "androidx.appcompat:appcompat:${Versions.appCompat}"

        internal const val navigation =
            "androidx.navigation:navigation-compose:${Versions.navigation}"

        internal const val hiltNavigationCompose =
            "androidx.hilt:hilt-navigation-compose:${Versions.Compose.hilt}"

    }

    object Framework {

        internal const val lifecycle =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        internal const val hilt = "com.google.dagger:hilt-android:${Versions.hilt}"
        internal const val hiltKapt = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"

        val implementation = listOf(
            lifecycle,
            hilt
        )

        val kapt = listOf(
            hiltKapt
        )
    }

    object Core {

        internal const val serializationCore =
            "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlin_serialization_core}"
        internal const val serializationJson =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlin_serialization}"
        internal const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        internal const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        internal const val coroutinesTest =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        internal const val moshi =
            "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
        internal const val moshiCompiler =
            "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"

        val test = listOf(
            coroutinesTest
        )
    }

    object Data {
        internal const val moshiConverter =
            "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        internal const val scalarsConverter =
            "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
        internal const val retrofitSerializer =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofitConverter}"
        internal const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        internal const val room = "androidx.room:room-runtime:${Versions.room}"
        internal const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
        internal const val roomKapt = "androidx.room:room-compiler:${Versions.room}"
        internal const val ktorTest = "io.ktor:ktor-client-mock:${Versions.ktor}"
        internal const val roomAndroidTest = "androidx.room:room-testing:${Versions.room}"

        val test = listOf(
            ktorTest
        )

        val androidTest = listOf(
            roomAndroidTest
        )
    }
}

fun DependencyHandler.implementCompose() {
    add("implementation", foundation)
    add("implementation", layout)
    add("implementation", material)
    add("implementation", materialIconsExtended)
    add("implementation", runtime)
    add("implementation", runtimeLivedata)
    add("implementation", tooling)
    add("implementation", toolingPreview)
    add("implementation", uiText)
    add("implementation", uiUtil)
    add("implementation", viewBinding)
    add("implementation", viewModel)
    add("implementation", coil)
    add("implementation", shimmer)
    add("implementation", activity)
    add("implementation", fragment)
    add("implementation", materialApp)
    add("implementation", navigationApp)
    add("implementation", appCompat)
    add("implementation", navigation)
    add("implementation", hiltNavigationCompose)
}

fun DependencyHandler.implementCore() {
    add("implementation", serializationCore)
    add("implementation", serializationJson)
    add("implementation", moshi)
    add("implementation", coroutines)
    add("implementation", coroutinesCore)
}

fun DependencyHandler.kaptCore() {
    add("kapt", moshiCompiler)
}

fun DependencyHandler.implementFramework() {
    add("implementation", lifecycle)
    add("implementation", hilt)
    add("kapt", hiltKapt)
}

fun DependencyHandler.kaptntFramework() {
    add("kapt", lifecycle)
    add("kapt", coroutines)
}

fun DependencyHandler.implementData() {
    add("implementation", retrofit)
    add("implementation", room)
    add("implementation", roomKtx)
    add("implementation", moshiConverter)
    add("implementation", scalarsConverter)
    add("implementation", retrofitSerializer)
}

fun DependencyHandler.kaptData() {
    add("kapt", roomKapt)
}
