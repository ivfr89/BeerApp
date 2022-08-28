buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.43")
    }
}

allprojects {
    repositories {
        maven(url = "https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
        google()
        mavenCentral()
        maven(url =  "https://jitpack.io" )
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
