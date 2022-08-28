object SetupConfig {
    const val compileSdkVersion = 32
    const val buildToolsVersion = "30.0.3"
    const val minSdkVersion = 23
    const val targetSdkVersion = 32
    const val applicationId = "com.developer.ivan.beerapp"
    const val testApplicationId = "test.nextchance.billionhands.webview"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val objenesis_version = "2.6"
    val versionCode: Int
        get() = (System.currentTimeMillis() / 1000).toInt()
}
